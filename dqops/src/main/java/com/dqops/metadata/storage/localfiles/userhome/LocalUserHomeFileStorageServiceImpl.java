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
package com.dqops.metadata.storage.localfiles.userhome;

import com.dqops.core.filesystem.cache.LocalFileSystemCache;
import com.dqops.core.filesystem.localfiles.HomeLocationFindService;
import com.dqops.core.filesystem.localfiles.LocalFileStorageServiceImpl;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.core.filesystem.virtual.HomeFilePath;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.core.locks.AcquiredExclusiveWriteLock;
import com.dqops.core.locks.AcquiredSharedReadLock;
import com.dqops.core.locks.UserHomeLockManager;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.status.FolderSynchronizationStatus;
import com.dqops.core.synchronization.status.SynchronizationStatusTracker;
import com.dqops.metadata.storage.localfiles.HomeType;
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
    private final LocalFileSystemCache localFileSystemCache;

    /**
     * Creates a local file storage service that uses the DQO_USER_HOME folder.
     * @param homeLocationFindService User home location finder.
     * @param userHomeLockManager User home lock manager.
     * @param synchronizationStatusTracker Synchronization status tracker.
     * @param localFileSystemCache Local file system cache.
     */
    @Autowired
    public LocalUserHomeFileStorageServiceImpl(HomeLocationFindService homeLocationFindService,
                                               UserHomeLockManager userHomeLockManager,
                                               SynchronizationStatusTracker synchronizationStatusTracker,
                                               LocalFileSystemCache localFileSystemCache) {
        super(homeLocationFindService.getUserHomePath(), HomeType.USER_HOME, localFileSystemCache);
        this.userHomeLockManager = userHomeLockManager;
        this.synchronizationStatusTracker = synchronizationStatusTracker;
        this.localFileSystemCache = localFileSystemCache;
    }

    @Override
    public boolean fileExists(HomeFilePath filePath, boolean useLocking) {
        DqoRoot lockFolderScope = useLocking ? DqoRoot.fromHomeFolderPath(filePath.getFolder()) : null;
        if (lockFolderScope != null) {
            try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(lockFolderScope, filePath.getFolder().getDataDomain())) {
                return super.fileExists(filePath, useLocking);
            }
        }
        else {
            return super.fileExists(filePath, useLocking);
        }
    }

    @Override
    public boolean folderExists(HomeFolderPath folderPath, boolean useLocking) {
        DqoRoot lockFolderScope = useLocking ? DqoRoot.fromHomeFolderPath(folderPath) : null;
        if (lockFolderScope != null) {
            try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(lockFolderScope, folderPath.getDataDomain())) {
                return super.folderExists(folderPath, useLocking);
            }
        }
        else {
            return super.folderExists(folderPath, useLocking);
        }
    }

    @Override
    public boolean tryDeleteFolder(HomeFolderPath folderPath, boolean useLocking) {
        DqoRoot lockFolderScope = DqoRoot.fromHomeFolderPath(folderPath);
        if (lockFolderScope != null) {
            try {
                if (useLocking) {
                    try (AcquiredExclusiveWriteLock lock = this.userHomeLockManager.lockExclusiveWrite(lockFolderScope, folderPath.getDataDomain())) {
                        return super.tryDeleteFolder(folderPath, useLocking);
                    }
                } else {
                    return super.tryDeleteFolder(folderPath, useLocking);
                }
            } finally {
                this.synchronizationStatusTracker.changeFolderSynchronizationStatus(lockFolderScope, folderPath.getDataDomain(), FolderSynchronizationStatus.changed);
            }
        }
        else {
            return super.tryDeleteFolder(folderPath, useLocking);
        }
    }

    @Override
    public FileContent readFile(HomeFilePath filePath, boolean useLocking) {
        DqoRoot lockFolderScope = useLocking ? DqoRoot.fromHomeFolderPath(filePath.getFolder()) : null;
        if (lockFolderScope != null) {
            try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(lockFolderScope, filePath.getFolder().getDataDomain())) {
                return super.readFile(filePath, useLocking);
            }
        }
        else {
            return super.readFile(filePath, useLocking);
        }
    }

    @Override
    public void saveFile(HomeFilePath filePath, FileContent fileContent, boolean useLocking) {
        DqoRoot lockFolderScope = useLocking ? DqoRoot.fromHomeFolderPath(filePath.getFolder()) : null;
        if (lockFolderScope != null) {
            try {
                if (useLocking) {
                    try (AcquiredExclusiveWriteLock lock = this.userHomeLockManager.lockExclusiveWrite(lockFolderScope, filePath.getFolder().getDataDomain())) {
                        super.saveFile(filePath, fileContent, useLocking);
                    }
                }
                else {
                    super.saveFile(filePath, fileContent, useLocking);
                }
            }
            finally {
                this.synchronizationStatusTracker.changeFolderSynchronizationStatus(lockFolderScope,
                        filePath.getFolder().getDataDomain(), FolderSynchronizationStatus.changed);
            }
        }
        else {
            super.saveFile(filePath, fileContent, useLocking);
        }
    }

    @Override
    public boolean deleteFile(HomeFilePath filePath, boolean useLocking) {
        DqoRoot lockFolderScope = DqoRoot.fromHomeFolderPath(filePath.getFolder());
        if (lockFolderScope != null) {
            try {
                if (useLocking) {
                    try (AcquiredExclusiveWriteLock lock = this.userHomeLockManager.lockExclusiveWrite(lockFolderScope, filePath.getFolder().getDataDomain())) {
                        return super.deleteFile(filePath, useLocking);
                    }
                } else {
                    return super.deleteFile(filePath, useLocking);
                }
            } finally {
                this.synchronizationStatusTracker.changeFolderSynchronizationStatus(lockFolderScope,
                        filePath.getFolder().getDataDomain(), FolderSynchronizationStatus.changed);
            }
        }
        else {
            return super.deleteFile(filePath, useLocking);
        }
    }

    @Override
    public List<HomeFolderPath> listFolders(HomeFolderPath folderPath, boolean useLocking) {
        DqoRoot lockFolderScope = useLocking ? DqoRoot.fromHomeFolderPath(folderPath) : null;
        if (lockFolderScope != null) {
            try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(lockFolderScope, folderPath.getDataDomain())) {
                return super.listFolders(folderPath, useLocking);
            }
        }
        else {
            return super.listFolders(folderPath, useLocking);
        }
    }

    @Override
    public List<HomeFilePath> listFiles(HomeFolderPath folderPath, boolean useLocking) {
        DqoRoot lockFolderScope = useLocking ? DqoRoot.fromHomeFolderPath(folderPath) : null;
        if (lockFolderScope != null) {
            try (AcquiredSharedReadLock lock = this.userHomeLockManager.lockSharedRead(lockFolderScope, folderPath.getDataDomain())) {
                return super.listFiles(folderPath, useLocking);
            }
        }
        else {
            return super.listFiles(folderPath, useLocking);
        }
    }
}
