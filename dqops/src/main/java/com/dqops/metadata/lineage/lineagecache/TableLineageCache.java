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

import com.dqops.data.checkresults.statuscache.DomainConnectionTableKey;

import java.util.concurrent.CompletableFuture;

/**
 * Service that manages the cache of data lineage and updates it when it receives notifications about loaded or updated connections and tables.
 */
public interface TableLineageCache {
    /**
     * Returns the information about the given table, its upstream tables and downstream tables.
     *
     * @param tableLineageCacheKey Table entry cache.
     * @return Table entry node or null when the lineage for the table was not retrieved.
     */
    TableLineageCacheEntry getTableLineageEntry(DomainConnectionTableKey tableLineageCacheKey);

    /**
     * Notifies the data lineage cache that a table yaml files were updated (or loaded into the cache) and should be scanned to load the data lineage.
     *
     * @param tableLineageKey     The target key that identifies an object that should be scanned for data lineage.
     * @param replacingCachedFile True when we are replacing a file that was already in a file system cache, false when a file is just placed into a cache,
     *                            and it is not a real invalidation, but just a notification that a file was just cached.
     */
    void invalidateObject(DomainConnectionTableKey tableLineageKey, boolean replacingCachedFile);

    /**
     * Returns a future that is completed when there are no queued data lineage load/reload operations.
     *
     * @param waitTimeoutMilliseconds Optional timeout to wait for the completion of the future. If the timeout elapses, the future is completed with a value <code>false</code>.
     * @return Future that is completed when the data lineage from all requested tables were loaded.
     */
    CompletableFuture<Boolean> getQueueEmptyFuture(Long waitTimeoutMilliseconds);

    /**
     * Starts a service that loads and refreshes the data lineage from tables.
     */
    void start();

    /**
     * Stops the data lineage cache service.
     */
    void stop();
}
