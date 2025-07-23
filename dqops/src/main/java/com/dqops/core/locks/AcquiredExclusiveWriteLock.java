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

import java.io.Closeable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Acquired exclusive write lock that is a write lock only on the deepest level lock, all parent locks are
 * locked in shared read mode.
 */
public class AcquiredExclusiveWriteLock implements Closeable {
    private final ReentrantReadWriteLock.WriteLock writeLock;
    private final ThreadLocksCounter threadLocksCounter;
    private boolean released;

    /**
     * Creates an exclusive read lock that is already in a "locked" status.
     * @param writeLock Exclusive write lock on the deepest level (the child only).
     * @param threadLocksCounter Threads lock counter.
     */
    public AcquiredExclusiveWriteLock(
            ReentrantReadWriteLock.WriteLock writeLock,
            ThreadLocksCounter threadLocksCounter) {
        this.writeLock = writeLock;
        this.threadLocksCounter = threadLocksCounter;
    }

    /**
     * Releases the read lock in the reverse order of acquisition.
     */
    @Override
    public void close() {
        if (this.released) {
            return;
        }

        this.threadLocksCounter.decrementWriteLock();
        this.writeLock.unlock();
        this.released = true;
    }
}
