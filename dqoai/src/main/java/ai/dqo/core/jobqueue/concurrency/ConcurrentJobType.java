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
package ai.dqo.core.jobqueue.concurrency;

/**
 * The type of concurrent job on which concurrency limits are applied.
 */
public enum ConcurrentJobType {
    /**
     * All sorts of operations that run checks, executed instantly or scheduled.
     */
    RUN_CHECKS,

    /**
     * Cloud sync operations on a folder level.
     */
    SYNCHRONIZE_FOLDER,

    /**
     * Limits the number of parallel run checks operations that are executed on a single connection. Each job executes on one table.
     */
    RUN_SENSORS_ON_CONNECTION,

    /**
     * Cloud sync operation that synchronizes multiple folders.
     */
    SYNCHRONIZE_MULTIPLE_FOLDERS,

    /**
     * Import tables from one schema on a source connection.
     */
    IMPORT_SCHEMA,

    /**
     * Import tables from a source connection, given a list of table names.
     */
    IMPORT_TABLES,

    /**
     * Delete data from user's ".data" folders, given some specifying parameters.
     */
    DELETE_STORED_DATA,

    /**
     * Repair data from user's ".data" folders, given connection name and optional table name.
     */
    REPAIR_STORED_DATA,
}
