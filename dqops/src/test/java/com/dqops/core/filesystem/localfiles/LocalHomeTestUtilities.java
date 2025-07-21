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
import org.springframework.stereotype.Component;

/**
 * Local user home test utilities. Used to recreate the user home for testing.
 */
@Component
public class LocalHomeTestUtilities {
    /**
     * Recreates the testable user home, cleaning it back to the default structure.
     */
    public void recreateTestUserHome() {
        try {
            HomeLocationFindServiceImpl homeLocationFindService = HomeLocationFindServiceObjectMother.getWithTestUserHome(true);
            UserHomeLockManager newLockManager = UserHomeLockManagerObjectMother.createNewLockManager();
            LocalUserHomeFileStorageServiceImpl localHomeStorageService = new LocalUserHomeFileStorageServiceImpl(
                    homeLocationFindService, newLockManager, new SynchronizationStatusTrackerStub(), LocalFileSystemCacheObjectMother.createNewWithCachingDisabled());
            LocalUserHomeCreatorObjectMother.initializeDqoUserHomeAt(localHomeStorageService.getHomeRootDirectory());
        }
        catch( Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
