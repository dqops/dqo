/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.checkresults.statuscache;

/**
 * Enumeration of statuses that tell us when the current table status was requested
 * to be loaded, or a refresh is pending.
 */
public enum CurrentTableStatusEntryStatus {
    /**
     * The table has no status loaded, but a load job is in the queue.
     */
    LOADING_QUEUED,

    /**
     * Table status was loaded.
     */
    LOADED,

    /**
     * The table status is loading right now.
     */
    LOADING,

    /**
     * The current table status is loaded, but it is outdated (some checks just finished and parquet files were updated).
     * The status will be refreshed.
     */
    REFRESH_QUEUED
}
