/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
