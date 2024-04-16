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

import com.dqops.data.checkresults.services.models.currentstatus.TableCurrentDataQualityStatusModel;

import java.util.concurrent.CompletableFuture;

/**
 * Service that keeps a cache of last known table statuses. It starts loading a table status when
 * the table status is requested. This service reloads the last known table status when the parquet files are updated.
 */
public interface TableStatusCache {
    /**
     * Retrieves the current table status for a requested table.
     *
     * @param tableStatusKey Table status key.
     * @return Table status model or null when it is not yet loaded.
     */
    TableCurrentDataQualityStatusModel getCurrentTableStatus(CurrentTableStatusKey tableStatusKey);

    /**
     * Notifies the table status cache that the table result were updated and should be invalidated.
     *
     * @param tableStatusKey Table status key.
     */
    void invalidateTableStatus(CurrentTableStatusKey tableStatusKey);

    /**
     * Returns a future that is completed when there are no queued table status reload operations.
     * @return Future that is completed when the status of all requested tables was loaded.
     */
    CompletableFuture<Integer> getQueueEmptyFuture();

    /**
     * Starts a service that loads table statuses of requested tables.
     */
    void start();

    /**
     * Stops a table current status loader cache.
     */
    void stop();
}
