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
package com.dqops.core.filesystem.localfiles;

import com.dqops.core.filesystem.cache.LocalFileSystemCacheObjectMother;
import com.dqops.core.synchronization.status.SynchronizationStatusTrackerStub;
import com.dqops.core.locks.UserHomeLockManager;
import com.dqops.core.locks.UserHomeLockManagerObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.LocalUserHomeCreatorObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.LocalUserHomeFileStorageServiceImpl;

/**
 * Object mother to create a local storage service.
 */
public final class LocalHomeStorageServiceObjectMother {
    /**
     * Creates a local home storage service that uses a temporary directory (recreated on each call).
     * @param recreateHomeDirectory Recreate the target/temporary-user-home directory.
     * @return Local home storage service.
     */
    public static LocalUserHomeFileStorageServiceImpl createLocalUserHomeStorageServiceForTestableHome(boolean recreateHomeDirectory) {
        LocalFileSystemCacheObjectMother.invalidateAll();

        try {
            HomeLocationFindServiceImpl homeLocationFindService = HomeLocationFindServiceObjectMother.getWithTestUserHome(recreateHomeDirectory);
            UserHomeLockManager newLockManager = UserHomeLockManagerObjectMother.createNewLockManager();
            LocalUserHomeFileStorageServiceImpl localHomeStorageService = new LocalUserHomeFileStorageServiceImpl(
                    homeLocationFindService, newLockManager, new SynchronizationStatusTrackerStub(),
                    LocalFileSystemCacheObjectMother.getRealCache());
            LocalUserHomeCreatorObjectMother.initializeDqoUserHomeAt(localHomeStorageService.getHomeRootDirectory());

            return localHomeStorageService;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Creates a local home storage service that uses a test home directory (recreated on each call). This is the same instance that will be returned
     * by creating a user home from the IoC container (Spring), but we can recreate the folder.
     * @param recreateHomeDirectory Recreate the target/test-user-home directory.
     * @return Local home storage service.
     */
    public static LocalUserHomeFileStorageServiceImpl createDefaultHomeStorageService(boolean recreateHomeDirectory) {
        LocalFileSystemCacheObjectMother.invalidateAll();

        try {
            HomeLocationFindService homeLocationFindService = HomeLocationFindServiceObjectMother.getDefaultHomeFinder(recreateHomeDirectory);
            UserHomeLockManager newLockManager = UserHomeLockManagerObjectMother.createNewLockManager();
            LocalUserHomeFileStorageServiceImpl localHomeStorageService = new LocalUserHomeFileStorageServiceImpl(
                    homeLocationFindService, newLockManager, new SynchronizationStatusTrackerStub(),
                    LocalFileSystemCacheObjectMother.getRealCache());
            LocalUserHomeCreatorObjectMother.initializeDqoUserHomeAt(localHomeStorageService.getHomeRootDirectory());

            return localHomeStorageService;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
