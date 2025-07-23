/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.labels.labelloader;

import java.util.concurrent.CompletableFuture;

/**
 * Dummy labels indexer that does nothing.
 */
public class DummyLabelsIndexer implements LabelsIndexer {
    /**
     * Notifies the label indexer that a connection or a table yaml files were updated (or loaded into the cache) and should be scanned to load labels.
     *
     * @param targetLabelsKey     The target key that identifies an object that should be scanned for labels.
     * @param replacingCachedFile True when we are replacing a file that was already in a file system cache, false when a file is just placed into a cache,
     *                            and it is not a real invalidation, but just a notification that a file was just cached.
     */
    @Override
    public void invalidateObject(LabelRefreshKey targetLabelsKey, boolean replacingCachedFile) {

    }

    /**
     * Returns a future that is completed when there are no queued label load reload operations.
     *
     * @param waitTimeoutMilliseconds Optional timeout to wait for the completion of the future. If the timeout elapses, the future is completed with a value <code>false</code>.
     * @return Future that is completed when the labels from all requested tables and connections were loaded.
     */
    @Override
    public CompletableFuture<Boolean> getQueueEmptyFuture(Long waitTimeoutMilliseconds) {
        return CompletableFuture.completedFuture(true);
    }

    /**
     * Starts a service that loads and refreshes the labels from connections and tables.
     */
    @Override
    public void start() {

    }

    /**
     * Stops the labels indexer service.
     */
    @Override
    public void stop() {

    }
}
