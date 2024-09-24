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
 * Test stub for TableStatusCache.
 */
public class TableStatusCacheStub implements TableStatusCache {
    /**
     * Retrieves the current table status for a requested table.
     *
     * @param tableStatusKey Table status key.
     * @param checkType      Check type. When a check type is given, the operation returns the status model only for one check type. The returned model does not contain columns.
     *                       When the <code>checkType</code> is null, this method returns a full model for all checks, including all columns.
     * @return Table status model or null when it is not yet loaded.
     */
    @Override
    public TableCurrentDataQualityStatusModel getCurrentTableStatus(DomainConnectionTableKey tableStatusKey, CheckType checkType) {
        TableCurrentDataQualityStatusModel statusModel = new TableCurrentDataQualityStatusModel();
        return statusModel;
    }

    /**
     * Retrieves the current table status for a requested table including all columns.
     *
     * @param tableStatusKey Table status key.
     * @return Table status model or null when it is not yet loaded.
     */
    @Override
    public TableCurrentDataQualityStatusModel getCurrentTableStatusWithColumns(DomainConnectionTableKey tableStatusKey) {
        TableCurrentDataQualityStatusModel statusModel = new TableCurrentDataQualityStatusModel();
        return statusModel;
    }

    /**
     * Notifies the table status cache that the table result were updated and should be invalidated.
     *
     * @param tableStatusKey      Table status key.
     * @param replacingCachedFile True when we are replacing a file that was already in a cache, false when a file is just placed into a cache,
     *                            and it is not a real invalidation, but just a notification that a file was just cached.
     */
    @Override
    public void invalidateTableStatus(DomainConnectionTableKey tableStatusKey, boolean replacingCachedFile) {

    }

    /**
     * Returns a future that is completed when there are no queued table status reload operations.
     *
     * @return Future that is completed when the status of all requested tables was loaded.
     */
    @Override
    public CompletableFuture<Boolean> getQueueEmptyFuture(Long timeout) {
        return CompletableFuture.completedFuture(true);
    }

    /**
     * Starts a service that loads table statuses of requested tables.
     */
    @Override
    public void start() {

    }

    /**
     * Stops a table current status loader cache.
     */
    @Override
    public void stop() {

    }
}
