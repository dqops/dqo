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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Reader writer lock holder (manager) that manages a single read/write lock.
 */
public class ReaderWriterLockHolder {
    /**
     * A real instance of a multiple readers / single writer lock that is managed by this instance.
     */
    private final ReentrantReadWriteLock managedLock = new ReentrantReadWriteLock(true);

    /**
     * Timeout on operations to obtain a lock.
     */
    private final long timeoutSeconds;
    private final ThreadLocksCounter threadLocksCounter;

    /**
     * Creates a root lock that has no parent lock.
     * @param timeoutSeconds Timeout for tryLock operations in seconds.
     * @param threadLocksCounter Threads lock counter.
     */
    public ReaderWriterLockHolder(long timeoutSeconds,
                                  ThreadLocksCounter threadLocksCounter) {
        this.timeoutSeconds = timeoutSeconds;
        this.threadLocksCounter = threadLocksCounter;
    }

    /**
     * Acquires a shared lock on the lock tree.
     * @return Acquired shared read lock that should be released.
     * @exception LockWaitTimeoutException When the wait time to acquire the lock was exceeded.
     */
    public AcquiredSharedReadLock lockSharedRead() {
        ReentrantReadWriteLock.ReadLock myReadLock = this.managedLock.readLock();
        try {
            boolean lockObtained = myReadLock.tryLock(this.timeoutSeconds, TimeUnit.SECONDS);
            if (lockObtained) {
                this.threadLocksCounter.incrementReadLock();
                return new AcquiredSharedReadLock(myReadLock, this.threadLocksCounter);
            } else {
                throw new LockWaitTimeoutException();
            }
        } catch (InterruptedException e) {
            throw new LockWaitTimeoutException(e);
        }
    }

    /**
     * Acquires an exclusive write lock. The write lock is acquired only on the current node.
     * @return Acquired write lock.
     */
    public AcquiredExclusiveWriteLock lockExclusiveWrite() {
        ReentrantReadWriteLock.WriteLock myWriteLock = this.managedLock.writeLock();
        try {
            boolean lockObtained = myWriteLock.tryLock(this.timeoutSeconds, TimeUnit.SECONDS);
            if (lockObtained) {
                this.threadLocksCounter.incrementWriteLock();
                return new AcquiredExclusiveWriteLock(myWriteLock, this.threadLocksCounter);
            } else {
                throw new LockWaitTimeoutException();
            }
        } catch (InterruptedException e) {
            throw new LockWaitTimeoutException(e);
        }
    }
}
