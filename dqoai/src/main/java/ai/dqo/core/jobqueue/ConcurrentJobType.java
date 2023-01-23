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
package ai.dqo.core.jobqueue;

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
     * Synchronizes the metadata that the scheduler knows. Detects new or disabled schedules (cron expressions).
     */
    SYNCHRONIZE_SCHEDULER_METADATA,

    /**
     * Import tables from one schema on a source connection.
     */
    IMPORT_SCHEMA,

    /**
     * Import tables from a source connection, given a list of table names.
     */
    IMPORT_TABLES,

//    /**
//     * Import a table from a source connection.
//     */
//    IMPORT_TABLE,

    /**
     * Delete data from user's ".data" folders, given some specifying parameters.
     */
    DELETE_STORED_DATA,
}
