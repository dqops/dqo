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
 * Acquired exclusive write lock that is a write lock only on the deepest level lock, all parent locks are
 * locked in shared read mode.
 */
public class AcquiredExclusiveWriteLock implements Closeable {
    private final ReentrantReadWriteLock.WriteLock writeLock;
    private boolean released;

    /**
     * Creates an exclusive read lock that is already in a "locked" status.
     * @param writeLock Exclusive write lock on the deepest level (the child only).
     */
    public AcquiredExclusiveWriteLock(ReentrantReadWriteLock.WriteLock writeLock) {
        this.writeLock = writeLock;
    }

    /**
     * Releases the read lock in the reverse order of acquisition.
     */
    @Override
    public void close() {
        if (this.released) {
            return;
        }

        this.writeLock.unlock();
        this.released = true;
    }
}
