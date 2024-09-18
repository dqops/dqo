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
