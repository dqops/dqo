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
