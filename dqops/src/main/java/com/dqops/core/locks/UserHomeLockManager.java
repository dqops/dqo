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
package com.dqops.core.locks;

import com.dqops.core.synchronization.contract.DqoRoot;

/**
 * Lock manager that controls access to the user home in a parallel environment.
 */
public interface UserHomeLockManager {
    /**
     * Sets up lock roots for a given data domain.
     * @param dataDomain Data domain name.
     */
    void createLocksForDataDomain(String dataDomain);

    /**
     * Obtains a shared read lock on a whole folder.
     * @param scope Lock scope that identifies a folder in the user home that is locked.
     * @param dataDomain Data domain name.
     * @return Shared read lock that must be released by calling the {@link AcquiredSharedReadLock#close()}
     * @throws LockWaitTimeoutException When the lock was not acquired because the wait time has exceeded.
     */
    AcquiredSharedReadLock lockSharedRead(DqoRoot scope, String dataDomain);

    /**
     * Obtains an exclusive lock on a whole folder.
     * @param scope Lock scope that identifies a folder in the user home that is locked.
     * @param dataDomain Data domain name.
     * @return Exclusive write lock that must be released by calling the {@link AcquiredExclusiveWriteLock#close()}
     * @throws LockWaitTimeoutException When the lock was not acquired because the wait time has exceeded.
     */
    AcquiredExclusiveWriteLock lockExclusiveWrite(DqoRoot scope, String dataDomain);
}
