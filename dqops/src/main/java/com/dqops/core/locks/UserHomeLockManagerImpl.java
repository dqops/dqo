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

import com.dqops.core.configuration.DqoCoreConfigurationProperties;
import com.dqops.core.dqocloud.accesskey.DqoDomainRootPair;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.synchronization.contract.DqoRoot;
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
    private final ThreadLocksCounter threadLocksCounter;
    private final Map<DqoDomainRootPair, ReaderWriterLockHolder> locks;
    private final Object dictionaryLock = new Object();

    /**
     * Creates an instance of the lock manager.
     * @param coreConfigurationProperties Configuration properties with the lock wait timeout setting.
     * @param threadLocksCounter Threads lock counter.
     */
    @Autowired
    public UserHomeLockManagerImpl(
            DqoCoreConfigurationProperties coreConfigurationProperties,
            ThreadLocksCounter threadLocksCounter) {
        this.coreConfigurationProperties = coreConfigurationProperties;
        this.threadLocksCounter = threadLocksCounter;
        this.locks = new LinkedHashMap<>();
        this.createLocksForDataDomain(UserDomainIdentity.ROOT_DATA_DOMAIN);
    }

    /**
     * Sets up lock roots for a given data domain.
     * @param dataDomain Data domain name.
     */
    @Override
    public void createLocksForDataDomain(String dataDomain) {
        long lockWaitTimeoutSeconds = this.coreConfigurationProperties.getLockWaitTimeoutSeconds();

        synchronized (this.dictionaryLock) {
            this.locks.put(new DqoDomainRootPair(dataDomain, DqoRoot.sources), new ReaderWriterLockHolder(lockWaitTimeoutSeconds, this.threadLocksCounter));
            this.locks.put(new DqoDomainRootPair(dataDomain, DqoRoot.sensors), new ReaderWriterLockHolder(lockWaitTimeoutSeconds, this.threadLocksCounter));
            this.locks.put(new DqoDomainRootPair(dataDomain, DqoRoot.rules), new ReaderWriterLockHolder(lockWaitTimeoutSeconds, this.threadLocksCounter));
            this.locks.put(new DqoDomainRootPair(dataDomain, DqoRoot.checks), new ReaderWriterLockHolder(lockWaitTimeoutSeconds, this.threadLocksCounter));
            this.locks.put(new DqoDomainRootPair(dataDomain, DqoRoot.settings), new ReaderWriterLockHolder(lockWaitTimeoutSeconds, this.threadLocksCounter));
            this.locks.put(new DqoDomainRootPair(dataDomain, DqoRoot.credentials), new ReaderWriterLockHolder(lockWaitTimeoutSeconds, this.threadLocksCounter));
            this.locks.put(new DqoDomainRootPair(dataDomain, DqoRoot.dictionaries), new ReaderWriterLockHolder(lockWaitTimeoutSeconds, this.threadLocksCounter));
            this.locks.put(new DqoDomainRootPair(dataDomain, DqoRoot.patterns), new ReaderWriterLockHolder(lockWaitTimeoutSeconds, this.threadLocksCounter));
            this.locks.put(new DqoDomainRootPair(dataDomain, DqoRoot.data_sensor_readouts), new ReaderWriterLockHolder(lockWaitTimeoutSeconds, this.threadLocksCounter));
            this.locks.put(new DqoDomainRootPair(dataDomain, DqoRoot.data_check_results), new ReaderWriterLockHolder(lockWaitTimeoutSeconds, this.threadLocksCounter));
            this.locks.put(new DqoDomainRootPair(dataDomain, DqoRoot.data_statistics), new ReaderWriterLockHolder(lockWaitTimeoutSeconds, this.threadLocksCounter));
            this.locks.put(new DqoDomainRootPair(dataDomain, DqoRoot.data_errors), new ReaderWriterLockHolder(lockWaitTimeoutSeconds, this.threadLocksCounter));
            this.locks.put(new DqoDomainRootPair(dataDomain, DqoRoot.data_error_samples), new ReaderWriterLockHolder(lockWaitTimeoutSeconds, this.threadLocksCounter));
            this.locks.put(new DqoDomainRootPair(dataDomain, DqoRoot.data_incidents), new ReaderWriterLockHolder(lockWaitTimeoutSeconds, this.threadLocksCounter));
            this.locks.put(new DqoDomainRootPair(dataDomain, DqoRoot._indexes), new ReaderWriterLockHolder(lockWaitTimeoutSeconds, this.threadLocksCounter));
            this.locks.put(new DqoDomainRootPair(dataDomain, DqoRoot._indexes_sources), new ReaderWriterLockHolder(lockWaitTimeoutSeconds, this.threadLocksCounter));
        }
    }

    /**
     * Obtains a shared read lock on a whole folder.
     *
     * @param scope Lock scope that identifies a folder in the user home that is locked.
     * @param dataDomain Data domain name.
     * @return Shared read lock that must be released by calling the {@link AcquiredSharedReadLock#close()}
     * @throws LockWaitTimeoutException When the lock was not acquired because the wait time has exceeded.
     */
    @Override
    public AcquiredSharedReadLock lockSharedRead(DqoRoot scope, String dataDomain) {
        DqoDomainRootPair lockKey = new DqoDomainRootPair(dataDomain, scope);
        ReaderWriterLockHolder readerWriterLockHolder;

        synchronized (this.dictionaryLock) {
            readerWriterLockHolder = this.locks.get(lockKey);
            if (readerWriterLockHolder == null) {
                throw new UnsupportedOperationException("Locking for DQOps Root " + scope + " is not supported.");
            }
        }

        return readerWriterLockHolder.lockSharedRead();
    }

    /**
     * Obtains an exclusive lock on a whole folder.
     *
     * @param scope Lock scope that identifies a folder in the user home that is locked.
     * @param dataDomain Data domain name.
     * @return Exclusive write lock that must be released by calling the {@link AcquiredExclusiveWriteLock#close()}
     * @throws LockWaitTimeoutException When the lock was not acquired because the wait time has exceeded.
     */
    @Override
    public AcquiredExclusiveWriteLock lockExclusiveWrite(DqoRoot scope, String dataDomain) {
        DqoDomainRootPair lockKey = new DqoDomainRootPair(dataDomain, scope);
        ReaderWriterLockHolder readerWriterLockHolder;

        synchronized (this.dictionaryLock) {
            readerWriterLockHolder = this.locks.get(lockKey);
            if (readerWriterLockHolder == null) {
                throw new UnsupportedOperationException("Locking for DQOps Root " + scope + " is not supported.");
            }
        }

        return readerWriterLockHolder.lockExclusiveWrite();
    }
}
