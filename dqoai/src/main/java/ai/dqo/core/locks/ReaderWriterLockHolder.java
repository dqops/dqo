/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.locks;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Reader writer lock holder (manager) that manages a single read/write lock and a dictionary of child lock holders.
 */
public class ReaderWriterLockHolder {
    /**
     * A real instance of a multiple readers / single writer lock that is managed by this instance.
     */
    private final ReentrantReadWriteLock managedLock = new ReentrantReadWriteLock(true);

    /**
     * the hash value of the lock used to identify the lock within the parent lock's map of child locks.
     * The has of the root lock is 0.
     */
    private final long lockId;

    /**
     * Reference to the parent lock. The lock on the parent must be obtained before locking the child object
     * in order to avoid a deadlock condition. Also, locks are released in the reverse order (child first).
     */
    private final ReaderWriterLockHolder parentLock;

    /**
     * Lock object used to protect access to the dictionary of child locks.
     */
    private final Object childrenLock = new Object();

    /**
     * Dictionary of child locks, indexed by hashes of protected objects. Any read and write access to this map
     * must be executed when a lock on <code>childrenLock</code> is obtained.
     */
    private final Map<Long, ReaderWriterLockHolder> children = new LinkedHashMap<>();

    /**
     * Timeout on operations to obtain a lock.
     */
    private final long timeoutSeconds;

    /**
     * Create an instance of a child lock.
     * @param lockId Lock id - a has code that identifies the lock. This lock will protect objects that match the hash value.
     * @param parentLock Reference to the parent lock.
     * @param timeoutSeconds Timeout for tryLock operations in seconds.
     */
    public ReaderWriterLockHolder(long lockId, ReaderWriterLockHolder parentLock, long timeoutSeconds) {
        this.lockId = lockId;
        this.parentLock = parentLock;
        this.timeoutSeconds = timeoutSeconds;
    }

    /**
     * Creates a root lock that has no parent lock.
     * @param timeoutSeconds Timeout for tryLock operations in seconds.
     */
    public ReaderWriterLockHolder(long timeoutSeconds) {
        this.lockId = 0L;
        this.parentLock = null;
        this.timeoutSeconds = timeoutSeconds;
    }

    /**
     * Returns the lock ID - it is a hash that is matched with the value of the target object.
     * @return Lock id.
     */
    public long getLockId() {
        return lockId;
    }

    /**
     * Returns a reference to the parent lock.
     * @return Parent lock.
     */
    public ReaderWriterLockHolder getParentLock() {
        return parentLock;
    }

    /**
     * Returns a child lock instance given the hash code of the child.
     * @param childHash Hash of the child node for which the lock is obtained.
     * @return Reader writer lock for the child node.
     */
    public ReaderWriterLockHolder getChildLock(long childHash) {
        synchronized (this.childrenLock) {
            ReaderWriterLockHolder childLock = this.children.get(childHash);
            if (childLock == null) {
                childLock = new ReaderWriterLockHolder(childHash, this, this.timeoutSeconds);
                this.children.put(childHash, childLock);
            }

            return childLock;
        }
    }

    /**
     * Obtains all shared read locks from the parent to the current node.
     * @param readLocksObtained Target list to add obtained reader lock in the order of lock acquisition (the root lock is the first, at index 0).
     * @return true when the lock was acquired, false when the wait time for the lock acquisition was exceeded.
     */
    protected boolean obtainAndCollectReaderLocks(List<ReentrantReadWriteLock.ReadLock> readLocksObtained) {
        boolean lockObtained = true;

        if (this.parentLock != null) {
            lockObtained = this.parentLock.obtainAndCollectReaderLocks(readLocksObtained);
            if (!lockObtained) {
                return false;
            }
        }

        ReentrantReadWriteLock.ReadLock myReadLock = this.managedLock.readLock();
        if (lockObtained) {
            try {
                lockObtained = myReadLock.tryLock(this.timeoutSeconds, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                lockObtained = false;
            }
        }

        if (!lockObtained) {
            // release already obtained locks
            for (int i = readLocksObtained.size() - 1; i >= 0 ; i--) {
                ReentrantReadWriteLock.ReadLock readLockToRelease = readLocksObtained.get(i);
                readLockToRelease.unlock();
            }
            readLocksObtained.clear();
            return false;
        }

        readLocksObtained.add(myReadLock);
        return true;
    }

    /**
     * Acquires a shared lock on the lock tree.
     * @return Acquired shared read lock that should be released.
     * @exception LockWaitTimeoutException When the wait time to acquire the lock was exceeded.
     */
    public AcquiredSharedReadLock lockSharedRead() {
        List<ReentrantReadWriteLock.ReadLock> readLocksObtained = new ArrayList<>();
        boolean lockWasAcquired = obtainAndCollectReaderLocks(readLocksObtained);

        if (lockWasAcquired) {
            return new AcquiredSharedReadLock(readLocksObtained);
        }

        throw new LockWaitTimeoutException();
    }

    /**
     * Acquires an exclusive write lock. The write lock is acquired only on the current node. Shared read locks are acquired
     * on all parent lock holders.
     * @return Acquired write lock.
     */
    public AcquiredExclusiveWriteLock lockExclusiveWrite() {
        List<ReentrantReadWriteLock.ReadLock> readLocksObtained = new ArrayList<>();
        boolean lockObtained = obtainAndCollectReaderLocks(readLocksObtained);

        if (lockObtained) {
            ReentrantReadWriteLock.WriteLock myWriteLock = this.managedLock.writeLock();

            try {
                lockObtained = myWriteLock.tryLock(this.timeoutSeconds, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                lockObtained = false;
            }

            if (!lockObtained) {
                // release already obtained read locks
                for (int i = readLocksObtained.size() - 1; i >= 0 ; i--) {
                    ReentrantReadWriteLock.ReadLock readLockToRelease = readLocksObtained.get(i);
                    readLockToRelease.unlock();
                }
                throw new LockWaitTimeoutException();
            }

            return new AcquiredExclusiveWriteLock(readLocksObtained, myWriteLock);
        }

        throw new LockWaitTimeoutException();
    }
}
