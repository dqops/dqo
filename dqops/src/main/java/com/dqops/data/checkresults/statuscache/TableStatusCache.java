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

import com.dqops.checks.CheckType;
import com.dqops.data.checkresults.models.currentstatus.TableCurrentDataQualityStatusModel;

import java.util.concurrent.CompletableFuture;

/**
 * Service that keeps a cache of last known table statuses. It starts loading a table status when
 * the table status is requested. This service reloads the last known table status when the parquet files are updated.
 */
public interface TableStatusCache {
    /**
     * The number of milliseconds that the service waits to collect a list of changes, before the next batch of table status update is started.
     */
    long BATCH_COLLECTION_TIMEOUT_MS = 50L;

    /**
     * The default delay to wait until the queue of awaiting table status load operations finish.
     */
    long EMPTY_QUEUE_WAIT_TIMEOUT_MS = BATCH_COLLECTION_TIMEOUT_MS + 100L;

    /**
     * Retrieves the current table status for a requested table.
     *
     * @param tableStatusKey Table status key.
     * @param checkType Check type. When a check type is given, the operation returns the status model only for one check type. The returned model does not contain columns.
     *                  When the <code>checkType</code> is null, this method returns a model for monitoring and partitioned checks combined.
     * @return Table status model or null when it is not yet loaded.
     */
    TableCurrentDataQualityStatusModel getCurrentTableStatus(CurrentTableStatusKey tableStatusKey, CheckType checkType);

    /**
     * Retrieves the current table status for a requested table including all columns.
     *
     * @param tableStatusKey Table status key.
     * @return Table status model or null when it is not yet loaded.
     */
    TableCurrentDataQualityStatusModel getCurrentTableStatusWithColumns(CurrentTableStatusKey tableStatusKey);

    /**
     * Notifies the table status cache that the table result were updated and should be invalidated.
     *
     * @param tableStatusKey Table status key.
     * @param replacingCachedFile True when we are replacing a file that was already in a cache, false when a file is just placed into a cache,
     *                            and it is not a real invalidation, but just a notification that a file was just cached.
     */
    void invalidateTableStatus(CurrentTableStatusKey tableStatusKey, boolean replacingCachedFile);

    /**
     * Returns a future that is completed when there are no queued table status reload operations.
     * @param waitTimeoutMilliseconds Optional timeout to wait for the completion of the future. If the timeout elapses, the future is completed with a value <code>false</code>.
     * @return Future that is completed when the status of all requested tables was loaded.
     */
    CompletableFuture<Boolean> getQueueEmptyFuture(Long waitTimeoutMilliseconds);

    /**
     * Starts a service that loads table statuses of requested tables.
     */
    void start();

    /**
     * Stops a table current status loader cache.
     */
    void stop();
}
