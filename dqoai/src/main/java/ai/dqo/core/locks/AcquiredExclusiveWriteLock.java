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

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Acquired exclusive write lock that is a write lock only on the deepest level lock, all parent locks are
 * locked in shared read mode.
 */
public class AcquiredExclusiveWriteLock implements AutoCloseable {
    private final List<ReentrantReadWriteLock.ReadLock> readLocks;
    private final ReentrantReadWriteLock.WriteLock writeLock;
    private boolean released;

    /**
     * Creates an exclusive read lock that is already in a "locked" status.
     * @param readLocks List of read locks in the order from the parent to the deepest child.
     * @param writeLock Exclusive write lock on the deepest level (the child only).
     */
    public AcquiredExclusiveWriteLock(List<ReentrantReadWriteLock.ReadLock> readLocks,
                                      ReentrantReadWriteLock.WriteLock writeLock) {
        this.readLocks = readLocks;
        this.writeLock = writeLock;
    }

    /**
     * Releases the read lock in the reverse order of acquisition.
     */
    @Override
    public void close() throws Exception {
        if (this.released) {
            return;
        }

        this.writeLock.unlock();

        for (int i = this.readLocks.size() - 1; i >= 0 ; i--) {
            ReentrantReadWriteLock.ReadLock readLock = this.readLocks.get(i);
            readLock.unlock();
        }

        this.released = true;
    }
}
