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
