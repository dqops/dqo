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

import com.dqops.data.checkresults.statuscache.DomainConnectionTableKey;

import java.util.LinkedHashSet;
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
            return this.upstreamSourceTables;
        }
    }

    /**
     * Adds a downstream (target) table.
     * @param downstreamTable Downstream table.
     */
    public void addDownstreamTargetTable(DomainConnectionTableKey downstreamTable) {
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
            return this.downstreamTargetTables;
        }
    }

    /**
     * Adds an upstream (source) table.
     * @param upstreamTable Upstream table.
     */
    public void addUpstreamSourceTable(DomainConnectionTableKey upstreamTable) {
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
