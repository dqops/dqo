/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.lineage.lineagecache;

/**
 * Enumeration of statuses that tell us when if a refresh of the table lineage on a table or connection was scheduled or is in progress.
 */
public enum TableLineageRefreshStatus {
    /**
     * The table or object has no table lineage loaded, but a load job is in the queue.
     */
    LOADING_QUEUED,

    /**
     * The table or connection data lineage are loading right now.
     */
    LOADING,

    /**
     * The data lineage was loaded.
     */
    LOADED,

    /**
     * The data lineage were loaded, but the file was modified and a new refresh is queued.
     */
    REFRESH_QUEUED
}
