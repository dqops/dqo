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

import ai.dqo.metadata.sources.PhysicalTableName;

/**
 * Lock manager that controls access to the user home in a parallel environment.
 */
public interface UserHomeLockManager {
    /**
     * Acquires an exclusive write lock on the whole user home.
     *
     * @return Exclusive write lock on the user home.
     * @throws LockWaitTimeoutException When the lock was not acquired because the wait time has exceeded.
     */
    AcquiredExclusiveWriteLock lockUserHomeForWrite();

    /**
     * Acquires an exclusive write lock on a source level.
     *
     * @param sourceName Source (connection) name.
     * @return Exclusive write lock for all data relevant for the connection.
     * @throws LockWaitTimeoutException When the lock was not acquired because the wait time has exceeded.
     */
    AcquiredExclusiveWriteLock lockSourceForWrite(String sourceName);

    /**
     * Acquires an exclusive write lock on a table level.
     *
     * @param sourceName        Source name (connection name).
     * @param physicalTableName Physical table name.
     * @return Acquired exclusive write lock on a table level.
     * @throws LockWaitTimeoutException When the lock was not acquired because the wait time has exceeded.
     */
    AcquiredExclusiveWriteLock lockTableForWrite(String sourceName, PhysicalTableName physicalTableName);
}
