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
 * Acquired shared read lock that must be returned.
 */
public class AcquiredSharedReadLock implements Closeable {
    private boolean released;
    private final ReentrantReadWriteLock.ReadLock readLock;
    private final ThreadLocksCounter threadLocksCounter;

    /**
     * Creates a shared read lock that is already in a "locked" status.
     * @param readLock Read lock that was locked.
     * @param threadLocksCounter Active locks counter.
     */
    public AcquiredSharedReadLock(
            ReentrantReadWriteLock.ReadLock readLock,
            ThreadLocksCounter threadLocksCounter) {
        this.readLock = readLock;
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

        this.threadLocksCounter.decrementReadLock();
        this.readLock.unlock();
        this.released = true;
    }
}
