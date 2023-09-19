/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.core.synchronization.status;

import com.dqops.core.filesystem.metadata.FileDifference;
import com.dqops.core.filesystem.metadata.FolderMetadata;
import com.dqops.core.locks.AcquiredSharedReadLock;
import com.dqops.core.locks.UserHomeLockManager;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.contract.FileSystemSynchronizationOperations;
import com.dqops.core.synchronization.contract.SynchronizationRoot;
import com.dqops.core.synchronization.filesystems.local.LocalSynchronizationFileSystemFactory;
import com.dqops.metadata.fileindices.FileIndexName;
import com.dqops.metadata.fileindices.FileIndexWrapper;
import com.dqops.metadata.fileindices.FileLocation;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

import java.util.Collection;

/**
 * Service that is called on startup, detects if there are any unsynchronized chages in any DQO User Home folder that should be synchronized to the DQO Cloud.
 */
@Component
@Slf4j
public class FileSynchronizationChangeDetectionServiceImpl implements FileSynchronizationChangeDetectionService {
    private UserHomeContextFactory userHomeContextFactory;
    private LocalSynchronizationFileSystemFactory localSynchronizationFileSystemFactory;
    private UserHomeLockManager userHomeLockManager;
    private SynchronizationStatusTracker synchronizationStatusTracker;

    /**
     * Creates a local file change detection service.
     * @param userHomeContextFactory User home context factory, used to read the synchronization file index.
     * @param localSynchronizationFileSystemFactory Local file system factory, used to read local files to detect changes.
     * @param userHomeLockManager User home lock manager, used to acquire a shared read lock.
     * @param synchronizationStatusTracker Synchronization status tracker, notified about detected changes.
     */
    @Autowired
    public FileSynchronizationChangeDetectionServiceImpl(
            UserHomeContextFactory userHomeContextFactory,
            LocalSynchronizationFileSystemFactory localSynchronizationFileSystemFactory,
            UserHomeLockManager userHomeLockManager,
            SynchronizationStatusTracker synchronizationStatusTracker) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.localSynchronizationFileSystemFactory = localSynchronizationFileSystemFactory;
        this.userHomeLockManager = userHomeLockManager;
        this.synchronizationStatusTracker = synchronizationStatusTracker;
    }

    /**
     * Detects if there are any not synchronized changes in a given DQO User home folder.
     * @param dqoRoot User home folder to be analyzed.
     * @return True when there are local not synchronized changes, false otherwise.
     */
    @Override
    public boolean detectNotSynchronizedChangesInFolder(DqoRoot dqoRoot) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        FileIndexName localIndexName = new FileIndexName(dqoRoot, FileLocation.LOCAL);
        FileIndexWrapper localFileIndexWrapper = userHome.getFileIndices().getByObjectName(localIndexName, true);
        if (localFileIndexWrapper == null) {
            localFileIndexWrapper = userHome.getFileIndices().createAndAddNew(localIndexName);
        }

        SynchronizationRoot userHomeFolderFileSystem = this.localSynchronizationFileSystemFactory.createUserHomeFolderFileSystem(dqoRoot);
        FolderMetadata lastSourceFolderIndex = localFileIndexWrapper.getSpec().getFolder();
        FileSystemSynchronizationOperations localFileSystemSynchronizationOperations = userHomeFolderFileSystem.getFileSystemService();

        try (AcquiredSharedReadLock acquiredSharedReadLock = this.userHomeLockManager.lockSharedRead(dqoRoot)) {
            FolderMetadata mostCurrentFolderMetadata = localFileSystemSynchronizationOperations.listFilesInFolder(
                    userHomeFolderFileSystem.getFileSystemRoot(),
                    lastSourceFolderIndex.getRelativePath(), lastSourceFolderIndex);

            Collection<FileDifference> localChanges = lastSourceFolderIndex.findFileDifferences(mostCurrentFolderMetadata);
            if (localChanges != null && localChanges.size() > 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * Detects changes in a folder. Optionally publishes a folder change status to "changed" if a change is detected.
     * @param dqoRoot Folder to be analyzed.
     */
    @Override
    public void detectAndPublishLocalFolderStatus(DqoRoot dqoRoot) {
        try {
            boolean hasLocalChanges = detectNotSynchronizedChangesInFolder(dqoRoot);
            if (hasLocalChanges) {
                this.synchronizationStatusTracker.changeFolderSynchronizationStatus(dqoRoot, FolderSynchronizationStatus.changed);
            }
        }
        catch (BeanCreationNotAllowedException ex) {
            return; // shutdown was started before synchronization finished
        }
        catch (Exception ex) {
            log.error("Failed to detect changes in a folder " + dqoRoot + ", error: " + ex.getMessage(), ex);
        }
    }

    /**
     * Starts a background job that checks all folders and tries to detect local changes that were not yet synchronized to DQO Cloud.
     */
    @Override
    public void detectNotSynchronizedChangesInBackground() {
        Schedulers.boundedElastic().schedule(() -> {
            detectAndPublishLocalFolderStatus(DqoRoot.sources);
            detectAndPublishLocalFolderStatus(DqoRoot.sensors);
            detectAndPublishLocalFolderStatus(DqoRoot.rules);
            detectAndPublishLocalFolderStatus(DqoRoot.checks);
            detectAndPublishLocalFolderStatus(DqoRoot.data_sensor_readouts);
            detectAndPublishLocalFolderStatus(DqoRoot.data_check_results);
            detectAndPublishLocalFolderStatus(DqoRoot.data_errors);
            detectAndPublishLocalFolderStatus(DqoRoot.data_statistics);
            detectAndPublishLocalFolderStatus(DqoRoot.data_incidents);
        });
    }
}
