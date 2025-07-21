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

import com.dqops.data.checkresults.models.currentstatus.TableCurrentDataQualityStatusModel;

/**
 * Object that stores the current table status for a table. It also knows when the status was loaded, if it was loaded,
 * or a load was queued.
 */
public class CurrentTableStatusCacheEntry {
    private DomainConnectionTableKey key;
    private boolean sendDataQualityStatusToDataCatalog;
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
     * @param sendDataQualityStatusToDataCatalog When this entry is loaded, send it to a data catalog service.
     */
    public CurrentTableStatusCacheEntry(DomainConnectionTableKey key,
                                        CurrentTableStatusEntryStatus status,
                                        boolean sendDataQualityStatusToDataCatalog) {
        this.key = key;
        this.status = status;
        this.sendDataQualityStatusToDataCatalog = sendDataQualityStatusToDataCatalog;
    }

    /**
     * Returns the key that identifies the table.
     * @return Key.
     */
    public DomainConnectionTableKey getKey() {
        return key;
    }

    /**
     * Returns a flag that this data quality status entry should be sent to a data catalog after it was loaded.
     * @return True when the entry should be sent to a data catalog.
     */
    public boolean isSendDataQualityStatusToDataCatalog() {
        return sendDataQualityStatusToDataCatalog;
    }

    /**
     * Set the field that the data should be sent to a data quality catalog.
     * @param sendDataQualityStatusToDataCatalog True when the status should be sent to a data quality catalog or it was already sent.
     */
    public void setSendDataQualityStatusToDataCatalog(boolean sendDataQualityStatusToDataCatalog) {
        this.sendDataQualityStatusToDataCatalog = sendDataQualityStatusToDataCatalog;
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
        synchronized (this.lock) {
            return allCheckTypesWithColumns;
        }
    }

    /**
     * Returns a table data quality status that covers only monitoring and partitioned checks, but excludes the profiling status.
     * @return The quality status for monitoring and partitioned checks only.
     */
    public TableCurrentDataQualityStatusModel getMonitoringAndPartitionedTableOnly() {
        synchronized (this.lock) {
            return monitoringAndPartitionedTableOnly;
        }
    }

    /**
     * Returns the status for the table related only to profiling checks. The status does not contain a list of column.
     * @return The status of the table, for profiling checks only.
     */
    public TableCurrentDataQualityStatusModel getProfilingTableOnly() {
        synchronized (this.lock) {
            return profilingTableOnly;
        }
    }

    /**
     * Returns the status for the table related only to monitoring checks. The status does not contain a list of column.
     * @return The status of the table, for monitoring checks only.
     */
    public TableCurrentDataQualityStatusModel getMonitoringTableOnly() {
        synchronized (this.lock) {
            return monitoringTableOnly;
        }
    }

    /**
     * Returns the status for the table related only to partitioned checks. The status does not contain a list of column.
     * @return The status of the table, for partitioned checks only.
     */
    public TableCurrentDataQualityStatusModel getPartitionedTableOnly() {
        synchronized (this.lock) {
            return partitionedTableOnly;
        }
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
