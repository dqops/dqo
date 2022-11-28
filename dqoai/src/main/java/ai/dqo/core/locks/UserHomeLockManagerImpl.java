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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Lock manager that controls access to the user home in a parallel environment.
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserHomeLockManagerImpl implements UserHomeLockManager {
    private final DqoCoreConfigurationProperties coreConfigurationProperties;
    private final Map<LockFolderScope, ReaderWriterLockHolder> locks;

    /**
     * Creates an instance of the lock manager.
     * @param coreConfigurationProperties Configuration properties with the lock wait timeout setting.
     */
    @Autowired
    public UserHomeLockManagerImpl(DqoCoreConfigurationProperties coreConfigurationProperties) {
        this.coreConfigurationProperties = coreConfigurationProperties;
        long lockWaitTimeoutSeconds = coreConfigurationProperties.getLockWaitTimeoutSeconds();
        this.locks = new LinkedHashMap<>() {{
            put(LockFolderScope.SOURCES, new ReaderWriterLockHolder(lockWaitTimeoutSeconds));
            put(LockFolderScope.SENSORS, new ReaderWriterLockHolder(lockWaitTimeoutSeconds));
            put(LockFolderScope.RULES, new ReaderWriterLockHolder(lockWaitTimeoutSeconds));
            put(LockFolderScope.SENSOR_READOUTS, new ReaderWriterLockHolder(lockWaitTimeoutSeconds));
            put(LockFolderScope.RULE_RESULTS, new ReaderWriterLockHolder(lockWaitTimeoutSeconds));
        }};
    }

    /**
     * Obtains a shared read lock on a whole folder.
     *
     * @param scope Lock scope that identifies a folder in the user home that is locked.
     * @return Shared read lock that must be released by calling the {@link AcquiredSharedReadLock#close()}
     * @throws LockWaitTimeoutException When the lock was not acquired because the wait time has exceeded.
     */
    @Override
    public AcquiredSharedReadLock lockSharedRead(LockFolderScope scope) {
        ReaderWriterLockHolder readerWriterLockHolder = this.locks.get(scope);
        return readerWriterLockHolder.lockSharedRead();
    }

    /**
     * Obtains an exclusive lock on a whole folder.
     *
     * @param scope Lock scope that identifies a folder in the user home that is locked.
     * @return Exclusive write lock that must be released by calling the {@link AcquiredExclusiveWriteLock#close()}
     * @throws LockWaitTimeoutException When the lock was not acquired because the wait time has exceeded.
     */
    @Override
    public AcquiredExclusiveWriteLock lockExclusiveWrite(LockFolderScope scope) {
        ReaderWriterLockHolder readerWriterLockHolder = this.locks.get(scope);
        return readerWriterLockHolder.lockExclusiveWrite();
    }
}
