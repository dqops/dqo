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
     * Retrieves the current table status for a requested table and sends the combined monitoring + partition check status
     * to the data catalog.
     *
     * @param tableStatusKey Table status key.
     */
    @Override
    public void sendCurrentTableStatusToDataCatalog(DomainConnectionTableKey tableStatusKey) {

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
