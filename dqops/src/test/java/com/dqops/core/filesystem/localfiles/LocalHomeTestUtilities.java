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
