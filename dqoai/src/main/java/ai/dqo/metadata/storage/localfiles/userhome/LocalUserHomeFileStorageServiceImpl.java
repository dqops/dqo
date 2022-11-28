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

import ai.dqo.core.filesystem.localfiles.HomeLocationFindService;
import ai.dqo.core.filesystem.localfiles.LocalFileStorageServiceImpl;
import ai.dqo.core.filesystem.virtual.FileContent;
import ai.dqo.core.filesystem.virtual.HomeFilePath;
import ai.dqo.core.filesystem.virtual.HomeFolderPath;
import ai.dqo.core.locks.AcquiredExclusiveWriteLock;
import ai.dqo.core.locks.AcquiredSharedReadLock;
import ai.dqo.core.locks.LockFolderScope;
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

    /**
     * Creates a local file storage service that uses the DQO_USER_HOME folder.
     * @param homeLocationFindService User home location finder.
     * @param userHomeLockManager User home lock manager.
     */
    @Autowired
    public LocalUserHomeFileStorageServiceImpl(HomeLocationFindService homeLocationFindService,
                                               UserHomeLockManager userHomeLockManager) {
        super(homeLocationFindService.getUserHomePath());
        this.userHomeLockManager = userHomeLockManager;
    }

    @Override
    public boolean fileExists(HomeFilePath filePath) {
        LockFolderScope lockFolderScope = LockFolderScope.fromHomeFolderPath(filePath.getFolder());
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
        LockFolderScope lockFolderScope = LockFolderScope.fromHomeFolderPath(folderPath);
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
        LockFolderScope lockFolderScope = LockFolderScope.fromHomeFolderPath(folderPath);
        if (lockFolderScope != null) {
            try (AcquiredExclusiveWriteLock lock = this.userHomeLockManager.lockExclusiveWrite(lockFolderScope)) {
                return super.tryDeleteFolder(folderPath);
            }
        }
        else {
            return super.tryDeleteFolder(folderPath);
        }
    }

    @Override
    public FileContent readTextFile(HomeFilePath filePath) {
        LockFolderScope lockFolderScope = LockFolderScope.fromHomeFolderPath(filePath.getFolder());
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
        LockFolderScope lockFolderScope = LockFolderScope.fromHomeFolderPath(filePath.getFolder());
        if (lockFolderScope != null) {
            try (AcquiredExclusiveWriteLock lock = this.userHomeLockManager.lockExclusiveWrite(lockFolderScope)) {
                super.saveFile(filePath, fileContent);
            }
        }
        else {
            super.saveFile(filePath, fileContent);
        }
    }

    @Override
    public boolean deleteFile(HomeFilePath filePath) {
        LockFolderScope lockFolderScope = LockFolderScope.fromHomeFolderPath(filePath.getFolder());
        if (lockFolderScope != null) {
            try (AcquiredExclusiveWriteLock lock = this.userHomeLockManager.lockExclusiveWrite(lockFolderScope)) {
                return super.deleteFile(filePath);
            }
        }
        else {
            return super.deleteFile(filePath);
        }
    }

    @Override
    public List<HomeFolderPath> listFolders(HomeFolderPath folderPath) {
        LockFolderScope lockFolderScope = LockFolderScope.fromHomeFolderPath(folderPath);
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
        LockFolderScope lockFolderScope = LockFolderScope.fromHomeFolderPath(folderPath);
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
