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

import ai.dqo.core.filesystem.filesystemservice.contract.AbstractFileSystemRoot;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoFileSystem;
import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import ai.dqo.core.filesystem.filesystemservice.contract.FileSystemService;
import ai.dqo.core.filesystem.metadata.FileDifference;
import ai.dqo.core.filesystem.metadata.FolderMetadata;
import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationListener;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * File system synchronization service that synchronizes files between two file systems. It could synchronize local files
 * with the target file system.
 */
@Component
public class FileSystemSynchronizationServiceImpl implements FileSystemSynchronizationService {
    /**
     * Synchronizes changes between two file systems.
     * @param source Source file system, the changes on the source (the local files) will overwrite changes in the target (remote DQO Cloud or similar).
     * @param target Target file system to send the changes in the source and download new changes.
     * @param dqoRoot User Home folder type to synchronize.
     * @param synchronizationListener Synchronization listener that is informed about the progress.
     * @return Synchronization result with two new file indexes after the file synchronization.
     */
    public SynchronizationResult synchronize(FileSystemChangeSet source,
                                             FileSystemChangeSet target,
                                             DqoRoot dqoRoot,
                                             FileSystemSynchronizationListener synchronizationListener) {
        DqoFileSystem sourceFileSystem = source.getFileSystem();
        FolderMetadata lastSourceFolderIndex = source.getStoredFileIndex();
        DqoFileSystem targetFileSystem = target.getFileSystem();
        FolderMetadata lastTargetFolderIndex = target.getStoredFileIndex();

        assert Objects.equals(lastSourceFolderIndex.getRelativePath(), lastTargetFolderIndex.getRelativePath());

        synchronizationListener.onSynchronizationBegin(dqoRoot, sourceFileSystem, targetFileSystem);

        FileSystemService sourceFileSystemService = sourceFileSystem.getFileSystemService();
        AbstractFileSystemRoot sourceFileSystemRoot = sourceFileSystem.getFileSystemRoot();
        FolderMetadata currentSourceFolderIndex = source.getCurrentFileIndex()
                .orElseGet(() -> sourceFileSystemService.listFilesInFolder(
                                sourceFileSystemRoot, lastSourceFolderIndex.getRelativePath(), lastSourceFolderIndex));
        currentSourceFolderIndex.freeze();
        FolderMetadata newSourceFolderIndex = currentSourceFolderIndex.cloneUnfrozen();

        FileSystemService targetFileSystemService = targetFileSystem.getFileSystemService();
        AbstractFileSystemRoot targetFileSystemRoot = targetFileSystem.getFileSystemRoot();
        FolderMetadata currentTargetFolderIndex = target.getCurrentFileIndex()
                .orElseGet(() -> targetFileSystemService.listFilesInFolder(
                targetFileSystemRoot, lastTargetFolderIndex.getRelativePath(), lastTargetFolderIndex));
        currentTargetFolderIndex.freeze();
        FolderMetadata newTargetFolderIndex = currentTargetFolderIndex.cloneUnfrozen();

        Collection<FileDifference> localChanges = lastSourceFolderIndex.findFileDifferences(currentSourceFolderIndex);
        Collection<FileDifference> unsyncedTargetChanges = lastTargetFolderIndex.findFileDifferences(currentTargetFolderIndex);
        HashSet<Path> synchronizedSourceChanges = new HashSet<>();

        if (localChanges != null) {
            // upload source (local) changes to the remote file system
            for (FileDifference localChange : localChanges) {
                if (localChange.isCurrentNew() || localChange.isCurrentChanged()) {
                    // send the current file to the remote
                    InputStream inputStream = sourceFileSystemService.downloadFile(sourceFileSystemRoot, localChange.getNewFile().getRelativePath());
                    targetFileSystemService.uploadFile(targetFileSystemRoot, localChange.getNewFile().getRelativePath(), inputStream, localChange.getNewFile().getFileHash());
                }
                else if (localChange.isCurrentDeleted()) {
                    targetFileSystemService.deleteFile(targetFileSystemRoot, localChange.getRelativePath());
                }

                synchronizationListener.onSourceChangeAppliedToTarget(dqoRoot, sourceFileSystem, targetFileSystem, localChange);
                synchronizedSourceChanges.add(localChange.getRelativePath());
                newTargetFolderIndex.applyChange(localChange.getRelativePath(), localChange.getNewFile());
            }
        }

        if (unsyncedTargetChanges != null) {
            // download changes from the remote file system
            for (FileDifference otherChange : unsyncedTargetChanges) {
                Path otherChangePath = otherChange.getRelativePath();
                if (synchronizedSourceChanges.contains(otherChangePath)) {
                    continue; // source changes pushed to the target take priority, we ignore remote (target file system) changes
                }

                if (otherChange.isCurrentNew() || otherChange.isCurrentChanged()) {
                    // download the change from the remote file system.
                    InputStream inputStream = targetFileSystemService.downloadFile(targetFileSystemRoot, otherChange.getNewFile().getRelativePath());
                    sourceFileSystemService.uploadFile(sourceFileSystemRoot, otherChange.getRelativePath(), inputStream, otherChange.getNewFile().getFileHash());
                }
                else if (otherChange.isCurrentDeleted()) {
                    sourceFileSystemService.deleteFile(sourceFileSystemRoot, otherChange.getRelativePath());
                }
                synchronizationListener.onTargetChangeAppliedToSource(dqoRoot, sourceFileSystem, targetFileSystem, otherChange);
                newSourceFolderIndex.applyChange(otherChange.getRelativePath(), otherChange.getNewFile());
            }
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

        Collection<FolderMetadata> emptySourceFolders = newSourceFolderIndex.detachEmptyFolders();
        if (emptySourceFolders != null) {
            for (FolderMetadata emptySourceFolder : emptySourceFolders) {
                sourceFileSystemService.deleteFolder(sourceFileSystemRoot, emptySourceFolder.getRelativePath(), false);
            }
        }
        newSourceFolderIndex.freeze();

        Collection<FolderMetadata> emptyTargetFolders = newTargetFolderIndex.detachEmptyFolders();
        if (emptyTargetFolders != null) {
            for (FolderMetadata emptyTargetFolder : emptyTargetFolders) {
                targetFileSystemService.deleteFolder(targetFileSystemRoot, emptyTargetFolder.getRelativePath(), false);
            }
        }
        newTargetFolderIndex.freeze();

        synchronizationListener.onSynchronizationFinished(dqoRoot, sourceFileSystem, targetFileSystem);

        return new SynchronizationResult(newSourceFolderIndex, newTargetFolderIndex);
//        return new SynchronizationResult(sourceFileIndexAfterChanges, targetFileIndexAfterChanges);
    }
}
