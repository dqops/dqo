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
