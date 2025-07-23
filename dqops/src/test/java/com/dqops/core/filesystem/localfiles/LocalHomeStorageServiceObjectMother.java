/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
