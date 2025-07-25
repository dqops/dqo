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

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Object that stores the collection of downstream tables below a given table.
 */
public class TableLineageCacheEntry {
    private DomainConnectionTableKey tableKey;
    private TableLineageRefreshStatus status;
    private final Object lock = new Object();
    private Set<DomainConnectionTableKey> upstreamSourceTables = new LinkedHashSet<>();
    private Set<DomainConnectionTableKey> downstreamTargetTables = new LinkedHashSet<>();

    /**
     * Creates a new entry.
     * @param tableKey Key that identifies the table.
     * @param status Data lineage load status.
     */
    public TableLineageCacheEntry(DomainConnectionTableKey tableKey, TableLineageRefreshStatus status) {
        this.tableKey = tableKey;
        this.status = status;
    }

    /**
     * Returns the key that identifies the table in the cache.
     * @return Key.
     */
    public DomainConnectionTableKey getTableKey() {
        return tableKey;
    }

    /**
     * Gets the data lineage loading status.
     * @return The current status.
     */
    public TableLineageRefreshStatus getStatus() {
        synchronized (this.lock) {
            return this.status;
        }
    }

    /**
     * Sets a new data lineage loading status.
     * @param status Status.
     */
    public void setStatus(TableLineageRefreshStatus status) {
        synchronized (this.lock) {
            this.status = status;
        }
    }

    /**
     * Returns the last known upstream (source) tables.
     * @return Upstream (source) tables.
     */
    public Set<DomainConnectionTableKey> getUpstreamSourceTables() {
        synchronized (this.lock) {
            return Collections.unmodifiableSet(this.upstreamSourceTables);
        }
    }

    /**
     * Adds a downstream (target) table.
     * @param downstreamTable Downstream table.
     */
    public void addDownstreamTargetTable(DomainConnectionTableKey downstreamTable) {
        if (Objects.equals(this.tableKey, downstreamTable)) {
            return; // user error, cannot add self
        }

        synchronized (this.lock) {
            LinkedHashSet<DomainConnectionTableKey> newSet = new LinkedHashSet<>(this.downstreamTargetTables);
            newSet.add(downstreamTable);

            this.downstreamTargetTables = newSet;
        }
    }

    /**
     * Removes a downstream (target) table.
     * @param downstreamTable Downstream table.
     */
    public void removeDownstreamTargetTable(DomainConnectionTableKey downstreamTable) {
        synchronized (this.lock) {
            LinkedHashSet<DomainConnectionTableKey> newSet = new LinkedHashSet<>(this.downstreamTargetTables);
            newSet.remove(downstreamTable);

            this.downstreamTargetTables = newSet;
        }
    }

    /**
     * Returns the downstream (target) tables.
     * @return Downstream (target) tables.
     */
    public Set<DomainConnectionTableKey> getDownstreamTargetTables() {
        synchronized (this.lock) {
            return Collections.unmodifiableSet(this.downstreamTargetTables);
        }
    }

    /**
     * Adds an upstream (source) table.
     * @param upstreamTable Upstream table.
     */
    public void addUpstreamSourceTable(DomainConnectionTableKey upstreamTable) {
        if (Objects.equals(this.tableKey, upstreamTable)) {
            return; // user error, cannot add self
        }

        synchronized (this.lock) {
            LinkedHashSet<DomainConnectionTableKey> newSet = new LinkedHashSet<>(this.upstreamSourceTables);
            newSet.add(upstreamTable);

            this.upstreamSourceTables = newSet;
        }
    }

    /**
     * Removes an upstream (source) table.
     * @param upstreamTable Upstream table.
     */
    public void removeUpstreamSourceTable(DomainConnectionTableKey upstreamTable) {
        synchronized (this.lock) {
            LinkedHashSet<DomainConnectionTableKey> newSet = new LinkedHashSet<>(this.upstreamSourceTables);
            newSet.remove(upstreamTable);

            this.upstreamSourceTables = newSet;
        }
    }
}
