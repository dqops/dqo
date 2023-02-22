/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.core.filesystem.synchronization;

import ai.dqo.core.configuration.DqoCloudConfigurationProperties;
import ai.dqo.core.filesystem.filesystemservice.contract.*;
import ai.dqo.core.filesystem.metadata.FileDifference;
import ai.dqo.core.filesystem.metadata.FolderMetadata;
import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationListener;
import ai.dqo.core.locks.AcquiredExclusiveWriteLock;
import ai.dqo.core.locks.AcquiredSharedReadLock;
import ai.dqo.core.locks.UserHomeLockManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.file.Path;
import java.time.Duration;
import java.util.*;

/**
 * File system synchronization service that synchronizes files between two file systems. It could synchronize local files
 * with the target file system.
 */
@Component
public class FileSystemSynchronizationServiceImpl implements FileSystemSynchronizationService {
    private final UserHomeLockManager userHomeLockManager;
    private final DqoCloudConfigurationProperties dqoCloudConfigurationProperties;

    /**
     * Creates a new file system synchronization service.
     * @param userHomeLockManager User home lock manager.
     * @param dqoCloudConfigurationProperties dqo.cloud configuration parameters.
     */
    @Autowired
    public FileSystemSynchronizationServiceImpl(UserHomeLockManager userHomeLockManager,
                                                DqoCloudConfigurationProperties dqoCloudConfigurationProperties) {
        this.userHomeLockManager = userHomeLockManager;
        this.dqoCloudConfigurationProperties = dqoCloudConfigurationProperties;
    }

    /**
     * Synchronizes changes between two file systems.
     * @param local Source file system, the changes on the source (the local files) will overwrite changes in the target (remote DQO Cloud or similar).
     * @param remote Target file system to send the changes in the source and download new changes.
     * @param dqoRoot User Home folder type to synchronize.
     * @param synchronizationDirection File synchronization direction (full, download, upload).
     * @param synchronizationListener Synchronization listener that is informed about the progress.
     * @return Synchronization result with two new file indexes after the file synchronization.
     */
    public SynchronizationResult synchronize(FileSystemChangeSet local,
                                             FileSystemChangeSet remote,
                                             DqoRoot dqoRoot,
                                             FileSynchronizationDirection synchronizationDirection,
                                             FileSystemSynchronizationListener synchronizationListener) {
        DqoFileSystem sourceFileSystem = local.getFileSystem();
        FolderMetadata lastSourceFolderIndex = local.getStoredFileIndex();
        DqoFileSystem targetFileSystem = remote.getFileSystem();
        FolderMetadata lastTargetFolderIndex = remote.getStoredFileIndex();

        assert Objects.equals(lastSourceFolderIndex.getRelativePath(), lastTargetFolderIndex.getRelativePath());

        synchronizationListener.onSynchronizationBegin(dqoRoot, sourceFileSystem, targetFileSystem);

        FileSystemService targetFileSystemService = targetFileSystem.getFileSystemService();
        AbstractFileSystemRoot targetFileSystemRoot = targetFileSystem.getFileSystemRoot();
        FolderMetadata currentTargetFolderIndex = remote.getCurrentFileIndex()
                .orElseGet(() -> targetFileSystemService.listFilesInFolder(
                        targetFileSystemRoot, lastTargetFolderIndex.getRelativePath(), lastTargetFolderIndex));
        currentTargetFolderIndex.freeze();
        FolderMetadata newTargetFolderIndex = currentTargetFolderIndex.cloneUnfrozen();


        FileSystemService sourceFileSystemService = sourceFileSystem.getFileSystemService();
        AbstractFileSystemRoot sourceFileSystemRoot = sourceFileSystem.getFileSystemRoot();
        FolderMetadata currentSourceFolderIndex;

        Collection<FileDifference> unsyncedTargetChanges;
        Set<Path> synchronizedSourceChanges = new HashSet<>();
        FolderMetadata newSourceFolderIndex = null;

        try (AcquiredSharedReadLock acquiredSharedReadLock = this.userHomeLockManager.lockSharedRead(dqoRoot)) {
            currentSourceFolderIndex = local.getCurrentFileIndex()
                    .orElseGet(() -> sourceFileSystemService.listFilesInFolder(
                            sourceFileSystemRoot, lastSourceFolderIndex.getRelativePath(), lastSourceFolderIndex));
            currentSourceFolderIndex.freeze();
            newSourceFolderIndex = currentSourceFolderIndex.cloneUnfrozen();

            if (synchronizationDirection == FileSynchronizationDirection.full || synchronizationDirection == FileSynchronizationDirection.upload) {
                Collection<FileDifference> localChanges = lastSourceFolderIndex.findFileDifferences(currentSourceFolderIndex);

                if (localChanges != null) {
                    // upload source (local) changes to the remote file system
                    synchronizedSourceChanges = uploadLocalToRemoteAsync(dqoRoot, synchronizationListener, sourceFileSystem, targetFileSystem, targetFileSystemService,
                            targetFileSystemRoot, newTargetFolderIndex, sourceFileSystemService, sourceFileSystemRoot, localChanges)
                            .subscribeOn(Schedulers.parallel())
                            .block(Duration.ofSeconds(this.dqoCloudConfigurationProperties.getFileSynchronizationTimeLimitSeconds()));
                }
            }
        }

        Collection<FolderMetadata> emptySourceFolders =
                (synchronizationDirection == FileSynchronizationDirection.full || synchronizationDirection == FileSynchronizationDirection.download)
                        ? newSourceFolderIndex.detachEmptyFolders() : null;
        Collection<FolderMetadata> emptyTargetFolders =
                (synchronizationDirection == FileSynchronizationDirection.full || synchronizationDirection == FileSynchronizationDirection.upload)
                        ? newTargetFolderIndex.detachEmptyFolders() : null;
        unsyncedTargetChanges = lastTargetFolderIndex.findFileDifferences(currentTargetFolderIndex);

        if (unsyncedTargetChanges != null || emptySourceFolders != null || emptyTargetFolders != null) {
            try (AcquiredExclusiveWriteLock acquiredExclusiveWriteLock = this.userHomeLockManager.lockExclusiveWrite(dqoRoot)) {
                // download changes from the remote file system
                if (unsyncedTargetChanges != null && (synchronizationDirection == FileSynchronizationDirection.full || synchronizationDirection == FileSynchronizationDirection.download)) {
                    downloadRemoteToLocalAsync(dqoRoot, synchronizationListener, sourceFileSystem, targetFileSystem, targetFileSystemService, targetFileSystemRoot,
                            sourceFileSystemService, sourceFileSystemRoot, unsyncedTargetChanges, synchronizedSourceChanges, newSourceFolderIndex)
                            .subscribeOn(Schedulers.parallel())
                            .block(Duration.ofSeconds(this.dqoCloudConfigurationProperties.getFileSynchronizationTimeLimitSeconds()));
                }

                ///// the code below is a version that will perform a full refresh from the source... not using the local knowledge, it is "just in case" if we don't trust our merge..
//        FolderMetadata sourceFileIndexAfterChanges = sourceFileSystemService.listFilesInFolder(
//                sourceFileSystemRoot, lastSourceFolderIndex.getRelativePath(), newSourceFolderIndex);
//        FolderMetadata targetFileIndexAfterChanges = targetFileSystemService.listFilesInFolder(
//                targetFileSystemRoot, lastTargetFolderIndex.getRelativePath(), newTargetFolderIndex);
//
//        Collection<FolderMetadata> emptySourceFolders = sourceFileIndexAfterChanges.detachEmptyFolders();
//        if (emptySourceFolders != null) {
//            for (FolderMetadata emptySourceFolder : emptySourceFolders) {
//                sourceFileSystemService.deleteFolder(sourceFileSystemRoot, emptySourceFolder.getRelativePath(), false);
//            }
//        }
//
//        Collection<FolderMetadata> emptyTargetFolders = targetFileIndexAfterChanges.detachEmptyFolders();
//        if (emptyTargetFolders != null) {
//            for (FolderMetadata emptyTargetFolder : emptyTargetFolders) {
//                targetFileSystemService.deleteFolder(targetFileSystemRoot, emptyTargetFolder.getRelativePath(), false);
//            }
//        }

                if (emptySourceFolders != null) {
                    for (FolderMetadata emptySourceFolder : emptySourceFolders) {
                        sourceFileSystemService.deleteFolderAsync(sourceFileSystemRoot, emptySourceFolder.getRelativePath(), false)
                                .subscribeOn(Schedulers.parallel())
                                .block(Duration.ofSeconds(this.dqoCloudConfigurationProperties.getFileSynchronizationTimeLimitSeconds()));
                    }
                }
                newSourceFolderIndex.freeze();

                if (emptyTargetFolders != null) {
                    for (FolderMetadata emptyTargetFolder : emptyTargetFolders) {
                        targetFileSystemService.deleteFolderAsync(targetFileSystemRoot, emptyTargetFolder.getRelativePath(), false)
                                .subscribeOn(Schedulers.parallel())
                                .block(Duration.ofSeconds(this.dqoCloudConfigurationProperties.getFileSynchronizationTimeLimitSeconds()));
                    }
                }
                newTargetFolderIndex.freeze();
            }
        }

        synchronizationListener.onSynchronizationFinished(dqoRoot, sourceFileSystem, targetFileSystem);

        return new SynchronizationResult(newSourceFolderIndex, newTargetFolderIndex);
//        return new SynchronizationResult(sourceFileIndexAfterChanges, targetFileIndexAfterChanges);
    }

    /**
     * Uploads local changes to DQO Cloud (remote file system).
     * @param dqoRoot DQO root type.
     * @param synchronizationListener Synchronization listener notified about the progress.
     * @param sourceFileSystem Source file system (local).
     * @param targetFileSystem Target file system (remote, DQO Cloud).
     * @param targetFileSystemService Target file system service.
     * @param targetFileSystemRoot Target file system root.
     * @param newTargetFolderIndex New target index updated with uploaded files.
     * @param sourceFileSystemService Source file system service.
     * @param sourceFileSystemRoot Source file system root.
     * @param localChanges Collection of local changes to be uploaded.
     * @return Dictionary of changes that were uploaded and should be ignored during a reverse synchronization (we will override remote changes).
     */
    public Mono<Set<Path>> uploadLocalToRemoteAsync(DqoRoot dqoRoot,
                                                    FileSystemSynchronizationListener synchronizationListener,
                                                    DqoFileSystem sourceFileSystem,
                                                    DqoFileSystem targetFileSystem,
                                                    FileSystemService targetFileSystemService,
                                                    AbstractFileSystemRoot targetFileSystemRoot,
                                                    FolderMetadata newTargetFolderIndex,
                                                    FileSystemService sourceFileSystemService,
                                                    AbstractFileSystemRoot sourceFileSystemRoot,
                                                    Collection<FileDifference> localChanges) {
        Set<Path> synchronizedSourceChanges = Collections.synchronizedSet(new HashSet<>());

        Mono<Set<Path>> monoResult = Flux.fromIterable(localChanges)
                .flatMap((FileDifference localChange) -> {
                    Mono<Path> fileExchangeOperationMono = null;

                    if (localChange.isCurrentNew() || localChange.isCurrentChanged()) {
                        // send the current file to the remote
                        Mono<DownloadFileResponse> downloadFileResponseMono = sourceFileSystemService.downloadFileAsync(
                                sourceFileSystemRoot, localChange.getNewFile().getRelativePath(), localChange.getNewFile());
                        fileExchangeOperationMono = downloadFileResponseMono.flatMap((DownloadFileResponse downloadFileResponse) -> {
                            return targetFileSystemService.uploadFileAsync(targetFileSystemRoot, localChange.getNewFile().getRelativePath(),
                                    downloadFileResponse.getByteBufFlux(), downloadFileResponse.getMetadata());
                        });
                    } else if (localChange.isCurrentDeleted()) {
                        fileExchangeOperationMono = targetFileSystemService.deleteFileAsync(targetFileSystemRoot, localChange.getRelativePath());
                    }

                    Mono<Void> finishedMono = fileExchangeOperationMono
                            .flatMap((Path uploadedPath) -> {
                                synchronizationListener.onSourceChangeAppliedToTarget(dqoRoot, sourceFileSystem, targetFileSystem, localChange);
                                synchronizedSourceChanges.add(localChange.getRelativePath());
                                newTargetFolderIndex.applyChange(localChange.getRelativePath(), localChange.getNewFile());
                                return Mono.empty();
                            })
                            .then();

                    return finishedMono;
                }, this.dqoCloudConfigurationProperties.getParallelFileUploads(), this.dqoCloudConfigurationProperties.getParallelFileUploads())
                .then(Mono.just(synchronizedSourceChanges));

        return monoResult;
    }

    /**
     * Downloads remote changes to the local file system.
     * @param dqoRoot Root file system.
     * @param synchronizationListener Synchronization listener.
     * @param sourceFileSystem Source file system.
     * @param targetFileSystem Target file system.
     * @param targetFileSystemService Target file system service.
     * @param targetFileSystemRoot Target file system root.
     * @param sourceFileSystemService Source file system service.
     * @param sourceFileSystemRoot Source file system root.
     * @param unsyncedTargetChanges Collection of changes to synchronize.
     * @param synchronizedSourceChanges Paths of files that were already uploaded from local to target, so we don't want to download them again.
     * @param newSourceFolderIndex New source folder index to add changes.
     */
    public Mono<Void> downloadRemoteToLocalAsync(DqoRoot dqoRoot,
                                                 FileSystemSynchronizationListener synchronizationListener,
                                                 DqoFileSystem sourceFileSystem, DqoFileSystem targetFileSystem,
                                                 FileSystemService targetFileSystemService,
                                                 AbstractFileSystemRoot targetFileSystemRoot,
                                                 FileSystemService sourceFileSystemService,
                                                 AbstractFileSystemRoot sourceFileSystemRoot,
                                                 Collection<FileDifference> unsyncedTargetChanges,
                                                 Set<Path> synchronizedSourceChanges,
                                                 FolderMetadata newSourceFolderIndex) {
        Mono<Void> monoResult = Flux.fromIterable(unsyncedTargetChanges)
                .flatMap((FileDifference otherChange) -> {
                    Path otherChangePath = otherChange.getRelativePath();
                    if (synchronizedSourceChanges.contains(otherChangePath)) {
                        return Mono.empty(); // source changes pushed to the target take priority, we ignore remote (target file system) changes
                    }
                    
                    Mono<Path> fileExchangeOperationMono = null;

                    if (otherChange.isCurrentNew() || otherChange.isCurrentChanged()) {
                        // download the change from the remote file system.
                        Mono<DownloadFileResponse> downloadFileResponseMono = targetFileSystemService.downloadFileAsync(
                                targetFileSystemRoot, otherChange.getNewFile().getRelativePath(), otherChange.getNewFile());
                        fileExchangeOperationMono = downloadFileResponseMono.flatMap((DownloadFileResponse downloadFileResponse) -> {
                            return sourceFileSystemService.uploadFileAsync(sourceFileSystemRoot, otherChange.getRelativePath(),
                                    downloadFileResponse.getByteBufFlux(), downloadFileResponse.getMetadata());
                        });
                    } else if (otherChange.isCurrentDeleted()) {
                        fileExchangeOperationMono = sourceFileSystemService.deleteFileAsync(sourceFileSystemRoot, otherChange.getRelativePath());
                    }

                    Mono<Void> finishedMono = fileExchangeOperationMono
                            .flatMap((Path downloadedPath) -> {
                                synchronizationListener.onTargetChangeAppliedToSource(dqoRoot, sourceFileSystem, targetFileSystem, otherChange);
                                newSourceFolderIndex.applyChange(otherChange.getRelativePath(), otherChange.getNewFile());
                                return Mono.empty();
                            })
                            .then();
                    return finishedMono;
                }, this.dqoCloudConfigurationProperties.getParallelFileDownloads(), this.dqoCloudConfigurationProperties.getParallelFileDownloads())
                .then();

        return monoResult;
    }
}
