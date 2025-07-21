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

/**
 * Counter of active locks, to detect nested locking that can lead to deadlocks.
 */
public interface ThreadLocksCounter {
    /**
     * Increments the number of read locks for the current thread.
     */
    void incrementReadLock();

    /**
     * Decrements the number of read locks for the current thread.
     */
    void decrementReadLock();

    /**
     * Increments the number of write locks for the current thread.
     */
    void incrementWriteLock();

    /**
     * Decrements the number of write locks for the current thread.
     */
    void decrementWriteLock();
}
