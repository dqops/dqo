/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.services.locking;

import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Service for locking operations (save operations mostly) on the same object, such as a connection or table.
 */
@Component
public class RestApiLockServiceImpl implements RestApiLockService {
    private final Cache<ConnectionLockKey, RestApiObjectLock> connectionWriteLocks =
            CacheBuilder.newBuilder()
                    .maximumSize(50000)
                    .expireAfterWrite(30, TimeUnit.MINUTES)
                    .build();

    private final Cache<ConnectionTableLockKey, RestApiObjectLock> tableWriteLocks =
            CacheBuilder.newBuilder()
                    .maximumSize(50000)
                    .expireAfterWrite(30, TimeUnit.MINUTES)
                    .build();

    private final Cache<CheckPatternLockKey, RestApiObjectLock> patternWriteLocks =
            CacheBuilder.newBuilder()
                    .maximumSize(50000)
                    .expireAfterWrite(30, TimeUnit.MINUTES)
                    .build();

    /**
     * Runs an operation synchronously on a connection.
     * @param connectionName Connection (data source) name.
     * @param operation Operation to run.
     */
    @Override
    public void runSynchronouslyOnConnection(String connectionName, Runnable operation) {
        ConnectionLockKey key = new ConnectionLockKey(connectionName);
        try {
            RestApiObjectLock restApiObjectLock = this.connectionWriteLocks.get(key, () -> new RestApiObjectLock(key));
            restApiObjectLock.runSynchronized(operation);
        }
        catch (ExecutionException eex) {
            throw new DqoRuntimeException("Cannot get a lock, error: " + eex.getMessage(), eex);
        }
    }

    /**
     * Calls an operation synchronously on a connection and returns the result returned by the operation.
     * @param connectionName Connection (data source) name.
     * @param operation Operation to run that returns a result.
     */
    @Override
    public <T> T callSynchronouslyOnConnection(String connectionName, Supplier<T> operation) {
        ConnectionLockKey key = new ConnectionLockKey(connectionName);
        try {
            RestApiObjectLock restApiObjectLock = this.connectionWriteLocks.get(key, () -> new RestApiObjectLock(key));
            return restApiObjectLock.callSynchronized(operation);
        }
        catch (ExecutionException eex) {
            throw new DqoRuntimeException("Cannot get a lock, error: " + eex.getMessage(), eex);
        }
    }

    /**
     * Runs an operation synchronously on a table.
     * @param connectionName Connection (data source) name.
     * @param physicalTableName Physical table name.
     * @param operation Operation to run.
     */
    @Override
    public void runSynchronouslyOnTable(String connectionName, PhysicalTableName physicalTableName, Runnable operation) {
        ConnectionTableLockKey key = new ConnectionTableLockKey(connectionName, physicalTableName);
        try {
            RestApiObjectLock restApiObjectLock = this.tableWriteLocks.get(key, () -> new RestApiObjectLock(key));
            restApiObjectLock.runSynchronized(operation);
        }
        catch (ExecutionException eex) {
            throw new DqoRuntimeException("Cannot get a lock, error: " + eex.getMessage(), eex);
        }
    }

    /**
     * Calls an operation synchronously on a table and returns the result returned by the operation.
     * @param connectionName Connection (data source) name.
     * @param physicalTableName Physical table name.
     * @param operation Operation to run that returns a result.
     */
    @Override
    public <T> T callSynchronouslyOnTable(String connectionName, PhysicalTableName physicalTableName, Supplier<T> operation) {
        ConnectionTableLockKey key = new ConnectionTableLockKey(connectionName, physicalTableName);
        try {
            RestApiObjectLock restApiObjectLock = this.tableWriteLocks.get(key, () -> new RestApiObjectLock(key));
            return restApiObjectLock.callSynchronized(operation);
        }
        catch (ExecutionException eex) {
            throw new DqoRuntimeException("Cannot get a lock, error: " + eex.getMessage(), eex);
        }
    }

    /**
     * Calls an operation synchronously on a table or column default check pattern and returns the result returned by the operation.
     *
     * @param patternName Default check pattern name.
     * @param operation   Operation to run that returns a result.
     */
    @Override
    public <T> T callSynchronouslyOnCheckPattern(String patternName, Supplier<T> operation) {
        CheckPatternLockKey key = new CheckPatternLockKey(patternName);
        try {
            RestApiObjectLock restApiObjectLock = this.patternWriteLocks.get(key, () -> new RestApiObjectLock(key));
            return restApiObjectLock.callSynchronized(operation);
        }
        catch (ExecutionException eex) {
            throw new DqoRuntimeException("Cannot get a lock, error: " + eex.getMessage(), eex);
        }
    }
}
