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
