/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue.concurrency;

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
