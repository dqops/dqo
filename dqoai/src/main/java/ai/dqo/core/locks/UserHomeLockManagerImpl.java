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

import ai.dqo.core.configuration.DqoCoreConfigurationProperties;
import ai.dqo.metadata.sources.PhysicalTableName;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Lock manager that controls access to the user home in a parallel environment.
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserHomeLockManagerImpl implements UserHomeLockManager {
    private final DqoCoreConfigurationProperties coreConfigurationProperties;
    private final ReaderWriterLockHolder rootLockHolder;

    /**
     * Creates an instance of the lock manager.
     * @param coreConfigurationProperties Configuration properties with the lock wait timeout setting.
     */
    @Autowired
    public UserHomeLockManagerImpl(DqoCoreConfigurationProperties coreConfigurationProperties) {
        this.coreConfigurationProperties = coreConfigurationProperties;
        this.rootLockHolder = new ReaderWriterLockHolder(0L, null, coreConfigurationProperties.getLockWaitTimeoutSeconds());
    }

    /**
     * Acquires an exclusive write lock on the whole user home.
     * @return Exclusive write lock on the user home.
     * @exception LockWaitTimeoutException When the lock was not acquired because the wait time has exceeded.
     */
    @Override
    public AcquiredExclusiveWriteLock lockUserHomeForWrite() {
        return this.rootLockHolder.lockExclusiveWrite();
    }

    /**
     * Acquires an exclusive write lock on a source level.
     * @param sourceName Source (connection) name.
     * @return Exclusive write lock for all data relevant for the connection.
     * @exception LockWaitTimeoutException When the lock was not acquired because the wait time has exceeded.
     */
    @Override
    public AcquiredExclusiveWriteLock lockSourceForWrite(String sourceName) {
        long sourceHash = Hashing.farmHashFingerprint64().hashString(sourceName, StandardCharsets.UTF_8).asLong();
        ReaderWriterLockHolder connectionLockHolder = this.rootLockHolder.getChildLock(sourceHash);
        return connectionLockHolder.lockExclusiveWrite();
    }

    /**
     * Acquires an exclusive write lock on a table level.
     * @param sourceName Source name (connection name).
     * @param physicalTableName Physical table name.
     * @return Acquired exclusive write lock on a table level.
     * @exception LockWaitTimeoutException When the lock was not acquired because the wait time has exceeded.
     */
    @Override
    public AcquiredExclusiveWriteLock lockTableForWrite(String sourceName, PhysicalTableName physicalTableName) {
        long sourceHash = Hashing.farmHashFingerprint64().hashString(sourceName, StandardCharsets.UTF_8).asLong();
        ReaderWriterLockHolder connectionLockHolder = this.rootLockHolder.getChildLock(sourceHash);

        long tableHash = Hashing.farmHashFingerprint64().hashString(physicalTableName.toString(), StandardCharsets.UTF_8).asLong();
        ReaderWriterLockHolder tableLockHolder = connectionLockHolder.getChildLock(tableHash);
        return tableLockHolder.lockExclusiveWrite();
    }
}
