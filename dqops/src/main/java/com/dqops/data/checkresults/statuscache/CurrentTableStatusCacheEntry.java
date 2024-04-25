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

import com.dqops.data.checkresults.models.currentstatus.TableCurrentDataQualityStatusModel;

/**
 * Object that stores the current table status for a table. It also knows when the status was loaded, if it was loaded,
 * or a load was queued.
 */
public class CurrentTableStatusCacheEntry {
    private CurrentTableStatusKey key;
    private final Object lock = new Object();
    private CurrentTableStatusEntryStatus status;
    private TableCurrentDataQualityStatusModel allCheckTypesWithColumns;
    private TableCurrentDataQualityStatusModel monitoringAndPartitionedTableOnly;
    private TableCurrentDataQualityStatusModel profilingTableOnly;
    private TableCurrentDataQualityStatusModel monitoringTableOnly;
    private TableCurrentDataQualityStatusModel partitionedTableOnly;

    /**
     * Creates a new entry.
     * @param key Key that identifies the table.
     * @param status Current status.
     */
    public CurrentTableStatusCacheEntry(CurrentTableStatusKey key, CurrentTableStatusEntryStatus status) {
        this.key = key;
        this.status = status;
    }

    /**
     * Returns the key that identifies the table.
     * @return Key.
     */
    public CurrentTableStatusKey getKey() {
        return key;
    }

    /**
     * Returns the current status of the entry - is it loading or loaded.
     * @return Current status.
     */
    public CurrentTableStatusEntryStatus getStatus() {
        synchronized (this.lock) {
            return status;
        }
    }

    /**
     * Sets the new status.
     * @param status New status.
     */
    public void setStatus(CurrentTableStatusEntryStatus status) {
        synchronized (this.lock) {
            this.status = status;
        }
    }

    /**
     * Returns the current model with the table status for all check types. This status contains also information for all columns.
     * @return Current table status for all check types.
     */
    public TableCurrentDataQualityStatusModel getAllCheckTypesWithColumns() {
        return allCheckTypesWithColumns;
    }

    /**
     * Returns a table data quality status that covers only monitoring and partitioned checks, but excludes the profiling status.
     * @return The quality status for monitoring and partitioned checks only.
     */
    public TableCurrentDataQualityStatusModel getMonitoringAndPartitionedTableOnly() {
        return monitoringAndPartitionedTableOnly;
    }

    /**
     * Returns the status for the table related only to profiling checks. The status does not contain a list of column.
     * @return The status of the table, for profiling checks only.
     */
    public TableCurrentDataQualityStatusModel getProfilingTableOnly() {
        return profilingTableOnly;
    }

    /**
     * Returns the status for the table related only to monitoring checks. The status does not contain a list of column.
     * @return The status of the table, for monitoring checks only.
     */
    public TableCurrentDataQualityStatusModel getMonitoringTableOnly() {
        return monitoringTableOnly;
    }

    /**
     * Returns the status for the table related only to partitioned checks. The status does not contain a list of column.
     * @return The status of the table, for partitioned checks only.
     */
    public TableCurrentDataQualityStatusModel getPartitionedTableOnly() {
        return partitionedTableOnly;
    }

    /**
     * Sets the current model with a detailed status for all check types. If it is not empty, the status is also changed to loaded.
     * @param allCheckTypesWithColumns Status model with all checks and columns.
     * @param monitoringAndPartitionedTableOnly Status model with monitoring and partitioned checks, without columns.
     * @param profilingTableOnly Status model with only profiling checks and no columns.
     * @param monitoringTableOnly Status model with only monitoring checks and no columns.
     * @param partitionedTableOnly Status model with only partitioned checks and no columns.
     */
    public void setStatusModels(
            TableCurrentDataQualityStatusModel allCheckTypesWithColumns,
            TableCurrentDataQualityStatusModel monitoringAndPartitionedTableOnly,
            TableCurrentDataQualityStatusModel profilingTableOnly,
            TableCurrentDataQualityStatusModel monitoringTableOnly,
            TableCurrentDataQualityStatusModel partitionedTableOnly) {
        synchronized (this.lock) {
            this.allCheckTypesWithColumns = allCheckTypesWithColumns;
            this.monitoringAndPartitionedTableOnly = monitoringAndPartitionedTableOnly;
            this.profilingTableOnly = profilingTableOnly;
            this.monitoringTableOnly = monitoringTableOnly;
            this.partitionedTableOnly = partitionedTableOnly;
            this.status = CurrentTableStatusEntryStatus.LOADED;
        }
    }
}
