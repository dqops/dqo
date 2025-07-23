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

import java.util.function.Supplier;

/**
 * Service for locking operations (save operations mostly) on the same object, such as a connection or table.
 */
public interface RestApiLockService {
    /**
     * Runs an operation synchronously on a connection.
     *
     * @param connectionName Connection (data source) name.
     * @param operation      Operation to run.
     */
    void runSynchronouslyOnConnection(String connectionName, Runnable operation);

    /**
     * Calls an operation synchronously on a connection and returns the result returned by the operation.
     *
     * @param connectionName Connection (data source) name.
     * @param operation      Operation to run that returns a result.
     */
    <T> T callSynchronouslyOnConnection(String connectionName, Supplier<T> operation);

    /**
     * Runs an operation synchronously on a table.
     *
     * @param connectionName    Connection (data source) name.
     * @param physicalTableName Physical table name.
     * @param operation         Operation to run.
     */
    void runSynchronouslyOnTable(String connectionName, PhysicalTableName physicalTableName, Runnable operation);

    /**
     * Calls an operation synchronously on a table and returns the result returned by the operation.
     *
     * @param connectionName    Connection (data source) name.
     * @param physicalTableName Physical table name.
     * @param operation         Operation to run that returns a result.
     */
    <T> T callSynchronouslyOnTable(String connectionName, PhysicalTableName physicalTableName, Supplier<T> operation);

    /**
     * Calls an operation synchronously on a table or column default check pattern and returns the result returned by the operation.
     *
     * @param patternName       Default check pattern name.
     * @param operation         Operation to run that returns a result.
     */
    <T> T callSynchronouslyOnCheckPattern(String patternName, Supplier<T> operation);
}
