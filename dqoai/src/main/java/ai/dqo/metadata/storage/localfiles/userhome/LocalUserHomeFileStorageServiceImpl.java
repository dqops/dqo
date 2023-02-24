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
package ai.dqo.metadata.storage.localfiles.userhome;

import ai.dqo.core.filesystem.filesystemservice.contract.DqoRoot;
import ai.dqo.core.filesystem.localfiles.HomeLocationFindService;
import ai.dqo.core.filesystem.localfiles.LocalFileStorageServiceImpl;
import ai.dqo.core.filesystem.synchronization.status.FolderSynchronizationStatus;
import ai.dqo.core.filesystem.synchronization.status.SynchronizationStatusTracker;
import ai.dqo.core.filesystem.virtual.FileContent;
import ai.dqo.core.filesystem.virtual.HomeFilePath;
import ai.dqo.core.filesystem.virtual.HomeFolderPath;
import ai.dqo.core.locks.AcquiredExclusiveWriteLock;
import ai.dqo.core.locks.AcquiredSharedReadLock;
import ai.dqo.core.locks.UserHomeLockManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Local user home (DQO_USER_HOME) file storage service that manages files in the user home folder.
 */
@Service
public class LocalUserHomeFileStorageServiceImpl extends LocalFileStorageServiceImpl implements LocalUserHomeFileStorageService {
    private final UserHomeLockManager userHomeLockManager;
    private final SynchronizationStatusTracker synchronizationStatusTracker;

    /**
     * Creates a local file storage service that uses the DQO_USER_HOME folder.
     * @param homeLocationFindService User home location finder.
     * @param userHomeLockManager User home lock manager.
     * @param synchronizationStatusTracker Synchronization status tracker.
     */
    @Autowired
    public LocalUserHomeFileStorageServiceImpl(HomeLocationFindService homeLocationFindService,
                                               UserHomeLockManager userHomeLockManager,
                                               SynchronizationStatusTracker synchronizationStatusTracker) {
        super(homeLocationFindService.getUserHomePath());
        this.userHomeLockManager = userHomeLockManager;
        this.synchronizationStatusTracker = synchronizationStatusTracker;
    }

    @Override
    public boolean fileExists(HomeFilePath filePath) {
        DqoRoot lockFolderScope = DqoRoot.fromHomeFolderPath(filePath.getFolder());
        if (lockFolderScope != null) {
            try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(lockFolderScope)) {
                return super.fileExists(filePath);
            }
        }
        else {
            return super.fileExists(filePath);
        }
    }

    @Override
    public boolean folderExists(HomeFolderPath folderPath) {
        DqoRoot lockFolderScope = DqoRoot.fromHomeFolderPath(folderPath);
        if (lockFolderScope != null) {
            try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(lockFolderScope)) {
                return super.folderExists(folderPath);
            }
        }
        else {
            return super.folderExists(folderPath);
        }
    }

    @Override
    public boolean tryDeleteFolder(HomeFolderPath folderPath) {
        DqoRoot lockFolderScope = DqoRoot.fromHomeFolderPath(folderPath);
        if (lockFolderScope != null) {
            try (AcquiredExclusiveWriteLock lock = this.userHomeLockManager.lockExclusiveWrite(lockFolderScope)) {
                return super.tryDeleteFolder(folderPath);
            }
            finally {
                this.synchronizationStatusTracker.changeFolderSynchronizationStatus(lockFolderScope, FolderSynchronizationStatus.changed);
            }
        }
        else {
            return super.tryDeleteFolder(folderPath);
        }
    }

    @Override
    public FileContent readTextFile(HomeFilePath filePath) {
        DqoRoot lockFolderScope = DqoRoot.fromHomeFolderPath(filePath.getFolder());
        if (lockFolderScope != null) {
            try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(lockFolderScope)) {
                return super.readTextFile(filePath);
            }
        }
        else {
            return super.readTextFile(filePath);
        }
    }

    @Override
    public void saveFile(HomeFilePath filePath, FileContent fileContent) {
        DqoRoot lockFolderScope = DqoRoot.fromHomeFolderPath(filePath.getFolder());
        if (lockFolderScope != null) {
            try (AcquiredExclusiveWriteLock lock = this.userHomeLockManager.lockExclusiveWrite(lockFolderScope)) {
                super.saveFile(filePath, fileContent);
            }
            finally {
                this.synchronizationStatusTracker.changeFolderSynchronizationStatus(lockFolderScope, FolderSynchronizationStatus.changed);
            }
        }
        else {
            super.saveFile(filePath, fileContent);
        }
    }

    @Override
    public boolean deleteFile(HomeFilePath filePath) {
        DqoRoot lockFolderScope = DqoRoot.fromHomeFolderPath(filePath.getFolder());
        if (lockFolderScope != null) {
            try (AcquiredExclusiveWriteLock lock = this.userHomeLockManager.lockExclusiveWrite(lockFolderScope)) {
                return super.deleteFile(filePath);
            }
            finally {
                this.synchronizationStatusTracker.changeFolderSynchronizationStatus(lockFolderScope, FolderSynchronizationStatus.changed);
            }
        }
        else {
            return super.deleteFile(filePath);
        }
    }

    @Override
    public List<HomeFolderPath> listFolders(HomeFolderPath folderPath) {
        DqoRoot lockFolderScope = DqoRoot.fromHomeFolderPath(folderPath);
        if (lockFolderScope != null) {
            try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(lockFolderScope)) {
                return super.listFolders(folderPath);
            }
        }
        else {
            return super.listFolders(folderPath);
        }
    }

    @Override
    public List<HomeFilePath> listFiles(HomeFolderPath folderPath) {
        DqoRoot lockFolderScope = DqoRoot.fromHomeFolderPath(folderPath);
        if (lockFolderScope != null) {
            try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(lockFolderScope)) {
                return super.listFiles(folderPath);
            }
        }
        else {
            return super.listFiles(folderPath);
        }
    }
}
