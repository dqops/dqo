/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.fileexchange;

import com.dqops.core.configuration.DqoCloudConfigurationProperties;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.filesystem.metadata.FileDifference;
import com.dqops.core.filesystem.metadata.FileMetadata;
import com.dqops.core.filesystem.metadata.FolderMetadata;
import com.dqops.core.locks.AcquiredExclusiveWriteLock;
import com.dqops.core.locks.AcquiredSharedReadLock;
import com.dqops.core.locks.UserHomeLockManager;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.*;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationListener;
import com.dqops.core.synchronization.status.FolderSynchronizationStatus;
import com.dqops.core.synchronization.status.SynchronizationStatusTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * File system synchronization service that synchronizes files between two file systems. It could synchronize local files
 * with the target file system.
 */
@Component
public class FileSystemSynchronizationServiceImpl implements FileSystemSynchronizationService {
    private final UserHomeLockManager userHomeLockManager;
    private final SynchronizationStatusTracker synchronizationStatusTracker;
    private final DqoCloudConfigurationProperties dqoCloudConfigurationProperties;

    /**
     * Creates a new file system synchronization service.
     * @param userHomeLockManager User home lock manager.
     * @param synchronizationStatusTracker Folder synchronization status tracker, notified when the synchronization starts and when it finishes.
     * @param dqoCloudConfigurationProperties dqo.cloud configuration parameters.
     */
    @Autowired
    public FileSystemSynchronizationServiceImpl(UserHomeLockManager userHomeLockManager,
                                                SynchronizationStatusTracker synchronizationStatusTracker,
                                                DqoCloudConfigurationProperties dqoCloudConfigurationProperties) {
        this.userHomeLockManager = userHomeLockManager;
        this.synchronizationStatusTracker = synchronizationStatusTracker;
        this.dqoCloudConfigurationProperties = dqoCloudConfigurationProperties;
    }

    /**
     * Synchronizes changes between two file systems.
     * @param local Source file system, the changes on the source (the local files) will overwrite changes in the target (remote DQOps Cloud or similar).
     * @param remote Target file system to send the changes in the source and download new changes.
     * @param dqoRoot User Home folder type to synchronize.
     * @param userIdentity User identity, also specified the data domain.
     * @param synchronizationDirection File synchronization direction (full, download, upload).
     * @param apiKey API Key with the license limits.
     * @param synchronizationListener Synchronization listener that is informed about the progress.
     * @return Synchronization result with two new file indexes after the file synchronization.
     */
    public SynchronizationResult synchronize(FileSystemChangeSet local,
                                             FileSystemChangeSet remote,
                                             DqoRoot dqoRoot,
                                             UserDomainIdentity userIdentity,
                                             FileSynchronizationDirection synchronizationDirection,
                                             DqoCloudApiKey apiKey,
                                             FileSystemSynchronizationListener synchronizationListener) {
        SynchronizationRoot localFileSystem = local.getFileSystem();
        FolderMetadata lastLocalFolderIndex = local.getStoredFileIndex();
        SynchronizationRoot remoteFileSystem = remote.getFileSystem();
        FolderMetadata lastRemoteFolderIndex = remote.getStoredFileIndex();
        TargetTableModifiedPartitions targetTableModifiedPartitions = new TargetTableModifiedPartitions(dqoRoot);

        assert Objects.equals(lastLocalFolderIndex.getRelativePath(), lastRemoteFolderIndex.getRelativePath());

        synchronizationListener.onSynchronizationBegin(dqoRoot, userIdentity, localFileSystem, remoteFileSystem);

        FileSystemSynchronizationOperations targetFileSystemSynchronizationOperations = remoteFileSystem.getFileSystemService();
        FileSystemSynchronizationRoot targetFileSystemRoot = remoteFileSystem.getFileSystemRoot();

        assert remote.getCurrentFileIndex().isEmpty() || remote.getCurrentFileIndex().get().isFrozen();
        FolderMetadata currentTargetFolderIndex = remote.getCurrentFileIndex()
                .orElseGet(() -> targetFileSystemSynchronizationOperations.listFilesInFolder(
                        targetFileSystemRoot, lastRemoteFolderIndex.getRelativePath(), lastRemoteFolderIndex));
        FolderMetadata newTargetFolderIndex = currentTargetFolderIndex.isFrozen() ?
                currentTargetFolderIndex.cloneUnfrozen() : currentTargetFolderIndex;


        FileSystemSynchronizationOperations sourceFileSystemSynchronizationOperations = localFileSystem.getFileSystemService();
        FileSystemSynchronizationRoot sourceFileSystemRoot = localFileSystem.getFileSystemRoot();
        FolderMetadata currentLocalFolderIndex;

        Collection<FileDifference> unsyncedTargetChanges = lastRemoteFolderIndex.findFileDifferences(currentTargetFolderIndex);
        Set<Path> synchronizedLocalChanges = new LinkedHashSet<>();
        Set<Path> filesChangedOnTarget = new HashSet<>();
        FolderMetadata newLocalFolderIndex = null;

        if (unsyncedTargetChanges != null)
        {
            filesChangedOnTarget.addAll(unsyncedTargetChanges.stream().map(fileDifference -> fileDifference.getRelativePath()).collect(Collectors.toList()));
        }

        this.synchronizationStatusTracker.changeFolderSynchronizationStatus(dqoRoot, userIdentity.getDataDomainFolder(), FolderSynchronizationStatus.synchronizing);
        try (AcquiredSharedReadLock acquiredSharedReadLock = this.userHomeLockManager.lockSharedRead(dqoRoot, userIdentity.getDataDomainFolder())) {
            assert local.getCurrentFileIndex().isEmpty() || local.getCurrentFileIndex().get().isFrozen();
            currentLocalFolderIndex = local.getCurrentFileIndex()
                    .orElseGet(() -> sourceFileSystemSynchronizationOperations.listFilesInFolder(
                            sourceFileSystemRoot, lastLocalFolderIndex.getRelativePath(), lastLocalFolderIndex));
            newLocalFolderIndex = currentLocalFolderIndex.isFrozen() ? currentLocalFolderIndex.cloneUnfrozen() : currentLocalFolderIndex;
            if(dqoRoot.isDataFolder()) {
                newLocalFolderIndex.truncateToLicenseLimits(apiKey.getApiKeyPayload());
            }

            if (synchronizationDirection == FileSynchronizationDirection.full || synchronizationDirection == FileSynchronizationDirection.upload) {
                Collection<FileDifference> localChanges = lastLocalFolderIndex.findFileDifferences(newLocalFolderIndex);

                if (localChanges != null) {
                    List<FileDifference> localChangesNotChangedOnTarget =
                            localChanges.stream()
                                    .filter(localFileDifference -> !filesChangedOnTarget.contains(localFileDifference.getRelativePath()))
                                    .collect(Collectors.toList());

                    targetTableModifiedPartitions.addModifications(localChangesNotChangedOnTarget);

                    // upload source (local) changes to the remote file system
                    synchronizedLocalChanges = uploadLocalToRemoteAsync(dqoRoot, userIdentity, synchronizationListener,
                            localFileSystem, remoteFileSystem, targetFileSystemSynchronizationOperations,
                            targetFileSystemRoot, newTargetFolderIndex, sourceFileSystemSynchronizationOperations, sourceFileSystemRoot, localChangesNotChangedOnTarget)
                            .subscribeOn(Schedulers.parallel())
                            .block(Duration.ofSeconds(this.dqoCloudConfigurationProperties.getFileSynchronizationTimeLimitSeconds()));
                }
            }
        }
        this.synchronizationStatusTracker.changeFolderSynchronizationStatus(dqoRoot, userIdentity.getDataDomainFolder(), FolderSynchronizationStatus.unchanged);

        Collection<FolderMetadata> emptyRemoteFolders =
                (synchronizationDirection == FileSynchronizationDirection.full || synchronizationDirection == FileSynchronizationDirection.upload)
                        ? newTargetFolderIndex.detachEmptyFolders() : null;

        if (unsyncedTargetChanges != null || emptyRemoteFolders != null) {
            try (AcquiredExclusiveWriteLock acquiredExclusiveWriteLock = this.userHomeLockManager.lockExclusiveWrite(dqoRoot, userIdentity.getDataDomainFolder())) {
                // download changes from the remote file system
                if (unsyncedTargetChanges != null && (synchronizationDirection == FileSynchronizationDirection.full || synchronizationDirection == FileSynchronizationDirection.download)) {
                    downloadRemoteToLocalAsync(dqoRoot, userIdentity, synchronizationListener, localFileSystem,
                            remoteFileSystem, targetFileSystemSynchronizationOperations, targetFileSystemRoot,
                            sourceFileSystemSynchronizationOperations, sourceFileSystemRoot, unsyncedTargetChanges, synchronizedLocalChanges, newLocalFolderIndex)
                            .subscribeOn(Schedulers.parallel())
                            .block(Duration.ofSeconds(this.dqoCloudConfigurationProperties.getFileSynchronizationTimeLimitSeconds()));
                }

                ///// the code below is a version that will perform a full refresh from the source... not using the local knowledge, it is "just in case" if we don't trust our merge..
//        FolderMetadata sourceFileIndexAfterChanges = sourceFileSystemService.listFilesInFolder(
//                sourceFileSystemRoot, lastLocalFolderIndex.getRelativePath(), newLocalFolderIndex);
//        FolderMetadata targetFileIndexAfterChanges = targetFileSystemService.listFilesInFolder(
//                targetFileSystemRoot, lastRemoteFolderIndex.getRelativePath(), newTargetFolderIndex);
//
//        Collection<FolderMetadata> emptyLocalFolders = sourceFileIndexAfterChanges.detachEmptyFolders();
//        if (emptyLocalFolders != null) {
//            for (FolderMetadata emptySourceFolder : emptyLocalFolders) {
//                sourceFileSystemService.deleteFolder(sourceFileSystemRoot, emptySourceFolder.getRelativePath(), false);
//            }
//        }
//
//        Collection<FolderMetadata> emptyRemoteFolders = targetFileIndexAfterChanges.detachEmptyFolders();
//        if (emptyRemoteFolders != null) {
//            for (FolderMetadata emptyTargetFolder : emptyRemoteFolders) {
//                targetFileSystemService.deleteFolder(targetFileSystemRoot, emptyTargetFolder.getRelativePath(), false);
//            }
//        }

                Collection<FolderMetadata> emptyLocalFolders =
                        (synchronizationDirection == FileSynchronizationDirection.full || synchronizationDirection == FileSynchronizationDirection.download)
                                ? newLocalFolderIndex.detachEmptyFolders() : null;

                if (emptyLocalFolders != null) {
                    Flux.fromIterable(emptyLocalFolders)
                            .parallel(this.dqoCloudConfigurationProperties.getParallelFileUploads())
                            .flatMap(emptySourceFolder ->sourceFileSystemSynchronizationOperations.deleteFolderAsync(
                                    sourceFileSystemRoot, emptySourceFolder.getRelativePath(), false))
                            .then()
                            .block(Duration.ofSeconds(this.dqoCloudConfigurationProperties.getFileSynchronizationTimeLimitSeconds()));
                }
                newLocalFolderIndex.freeze();

                if (emptyRemoteFolders != null) {
                    Flux.fromIterable(emptyRemoteFolders)
                            .parallel(this.dqoCloudConfigurationProperties.getParallelFileUploads())
                            .flatMap(emptyTargetFolder -> targetFileSystemSynchronizationOperations.deleteFolderAsync(
                                    targetFileSystemRoot, emptyTargetFolder.getRelativePath(), false))
                            .then()
                            .block(Duration.ofSeconds(this.dqoCloudConfigurationProperties.getFileSynchronizationTimeLimitSeconds()));
                }
                newTargetFolderIndex.freeze();
            }
        }

        synchronizationListener.onSynchronizationFinished(dqoRoot, userIdentity, localFileSystem, remoteFileSystem);

        return new SynchronizationResult(newLocalFolderIndex, newTargetFolderIndex, targetTableModifiedPartitions);
//        return new SynchronizationResult(sourceFileIndexAfterChanges, targetFileIndexAfterChanges);
    }

    /**
     * Uploads local changes to DQOps Cloud (remote file system).
     * @param dqoRoot DQOps root type.
     * @param userDomainIdentity Identity of the user who triggered synchronization and the data domain that is synchronized.
     * @param synchronizationListener Synchronization listener notified about the progress.
     * @param localFileSystem Source file system (local).
     * @param remoteFileSystem Target file system (remote, DQOps Cloud).
     * @param remoteFileSystemSynchronizationOperations Target file system service.
     * @param remoteFileSystemRoot Target file system root.
     * @param newRemoteFolderIndex New target index updated with uploaded files.
     * @param localFileSystemSynchronizationOperations Source file system service.
     * @param localFileSystemRoot Source file system root.
     * @param localChanges Collection of local changes to be uploaded.
     * @return Dictionary of changes that were uploaded and should be ignored during a reverse synchronization (we will override remote changes).
     */
    public Mono<Set<Path>> uploadLocalToRemoteAsync(DqoRoot dqoRoot,
                                                    UserDomainIdentity userDomainIdentity,
                                                    FileSystemSynchronizationListener synchronizationListener,
                                                    SynchronizationRoot localFileSystem,
                                                    SynchronizationRoot remoteFileSystem,
                                                    FileSystemSynchronizationOperations remoteFileSystemSynchronizationOperations,
                                                    FileSystemSynchronizationRoot remoteFileSystemRoot,
                                                    FolderMetadata newRemoteFolderIndex,
                                                    FileSystemSynchronizationOperations localFileSystemSynchronizationOperations,
                                                    FileSystemSynchronizationRoot localFileSystemRoot,
                                                    Collection<FileDifference> localChanges) {
        Set<Path> synchronizedSourceChanges = Collections.synchronizedSet(new LinkedHashSet<>());

        Mono<Set<Path>> monoResult = Flux.fromIterable(localChanges)
                .parallel(this.dqoCloudConfigurationProperties.getParallelFileUploads())
                .flatMap((FileDifference localChange) -> {
                    Mono<FileMetadata> fileExchangeOperationMono = null;

                    if (localChange.isCurrentNew() || localChange.isCurrentChanged()) {
                        // send the current file to the remote
                        Mono<DownloadFileResponse> downloadFileResponseMono = localFileSystemSynchronizationOperations.downloadFileAsync(
                                localFileSystemRoot, localChange.getNewFile().getRelativePath(), localChange.getNewFile())
                                .retryWhen(Retry.backoff(this.dqoCloudConfigurationProperties.getMaxRetries(),
                                        Duration.of(this.dqoCloudConfigurationProperties.getRetryBackoffMillis(), ChronoUnit.MILLIS)));

                        fileExchangeOperationMono = remoteFileSystemSynchronizationOperations
                                .uploadFileAsync(
                                        remoteFileSystemRoot, localChange.getNewFile().getRelativePath(), downloadFileResponseMono)
                                .retryWhen(Retry.backoff(this.dqoCloudConfigurationProperties.getMaxRetries(),
                                        Duration.of(this.dqoCloudConfigurationProperties.getRetryBackoffMillis(), ChronoUnit.MILLIS)));
                    } else if (localChange.isCurrentDeleted()) {
                        fileExchangeOperationMono = remoteFileSystemSynchronizationOperations.deleteFileAsync(remoteFileSystemRoot, localChange.getRelativePath());
                    }

                    Mono<Void> finishedMono = fileExchangeOperationMono
                            .flatMap((FileMetadata uploadedFileMetadata) -> {
                                synchronizationListener.onSourceChangeAppliedToTarget(dqoRoot, userDomainIdentity, localFileSystem, remoteFileSystem, localChange);
                                synchronizedSourceChanges.add(localChange.getRelativePath());
                                newRemoteFolderIndex.applyChange(localChange.getRelativePath(),
                                        !uploadedFileMetadata.isDeleted() ? uploadedFileMetadata : null);
                                return Mono.empty();
                            })
                            .then();

                    return finishedMono;
                })
                .then()
                .then(Mono.just(synchronizedSourceChanges));

        return monoResult;
    }

    /**
     * Downloads remote changes to the local file system.
     * @param dqoRoot Root file system.
     * @param userDomainIdentity Identity of the user who triggered synchronization and the data domain that is synchronized.
     * @param synchronizationListener Synchronization listener.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     * @param remoteFileSystemSynchronizationOperations Target file system service.
     * @param remoteFileSystemRoot Target file system root.
     * @param localFileSystemSynchronizationOperations Source file system service.
     * @param localFileSystemRoot Source file system root.
     * @param unsyncedTargetChanges Collection of changes to synchronize.
     * @param synchronizedSourceChanges Paths of files that were already uploaded from local to target, so we don't want to download them again.
     * @param newLocalFolderIndex New source folder index to add changes.
     */
    public Mono<Void> downloadRemoteToLocalAsync(DqoRoot dqoRoot,
                                                 UserDomainIdentity userDomainIdentity,
                                                 FileSystemSynchronizationListener synchronizationListener,
                                                 SynchronizationRoot sourceFileSystem, SynchronizationRoot targetFileSystem,
                                                 FileSystemSynchronizationOperations remoteFileSystemSynchronizationOperations,
                                                 FileSystemSynchronizationRoot remoteFileSystemRoot,
                                                 FileSystemSynchronizationOperations localFileSystemSynchronizationOperations,
                                                 FileSystemSynchronizationRoot localFileSystemRoot,
                                                 Collection<FileDifference> unsyncedTargetChanges,
                                                 Set<Path> synchronizedSourceChanges,
                                                 FolderMetadata newLocalFolderIndex) {
        Mono<Void> monoResult = Flux.fromIterable(unsyncedTargetChanges)
                .parallel(this.dqoCloudConfigurationProperties.getParallelFileDownloads())
                .flatMap((FileDifference otherChange) -> {
                    Path otherChangePath = otherChange.getRelativePath();
                    if (synchronizedSourceChanges.contains(otherChangePath)) {
                        return Mono.empty(); // source changes pushed to the target take priority, we ignore remote (target file system) changes
                    }
                    
                    Mono<FileMetadata> fileExchangeOperationMono = null;

                    if (otherChange.isCurrentNew() || otherChange.isCurrentChanged()) {
                        // download the change from the remote file system.
                        Mono<DownloadFileResponse> downloadFileResponseMono = remoteFileSystemSynchronizationOperations.downloadFileAsync(
                                remoteFileSystemRoot, otherChange.getNewFile().getRelativePath(), otherChange.getNewFile())
                                .retryWhen(Retry.backoff(this.dqoCloudConfigurationProperties.getMaxRetries(),
                                        Duration.of(this.dqoCloudConfigurationProperties.getRetryBackoffMillis(), ChronoUnit.MILLIS)));

                        fileExchangeOperationMono = localFileSystemSynchronizationOperations
                                .uploadFileAsync(
                                        localFileSystemRoot, otherChange.getRelativePath(), downloadFileResponseMono)
                                .retryWhen(Retry.backoff(this.dqoCloudConfigurationProperties.getMaxRetries(),
                                        Duration.of(this.dqoCloudConfigurationProperties.getRetryBackoffMillis(), ChronoUnit.MILLIS)));
                    } else if (otherChange.isCurrentDeleted()) {
                        fileExchangeOperationMono = localFileSystemSynchronizationOperations.deleteFileAsync(localFileSystemRoot, otherChange.getRelativePath());
                    }

                    Mono<Void> finishedMono = fileExchangeOperationMono
                            .flatMap((FileMetadata downloadedFileMetadata) -> {
                                synchronizationListener.onTargetChangeAppliedToSource(dqoRoot, userDomainIdentity, sourceFileSystem, targetFileSystem, otherChange);
                                newLocalFolderIndex.applyChange(otherChange.getRelativePath(),
                                        !downloadedFileMetadata.isDeleted() ? downloadedFileMetadata : null);
                                return Mono.empty();
                            })
                            .then();
                    return finishedMono;
                })
                .then();

        return monoResult;
    }
}
