/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.storage;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.sources.PhysicalTableName;

import java.time.LocalDate;
import java.util.Map;

/**
 * Service that supports reading and writing parquet file partitions from a local file system.
 */
public interface ParquetPartitionStorageService {
    /**
     * Reads the data of one monthly partition.
     *
     * @param partitionId     Partition id.
     * @param storageSettings Storage settings that identify the target table type that is loaded.
     * @param columnNames     Optional array of requested column names. All columns are loaded without filtering when the argument is null.
     * @param userIdentity    User identity, specifies the data domain.
     * @return Returns a dataset table with the content of the partition. The table (data) is null if the parquet file was not found.
     */
    LoadedMonthlyPartition loadPartition(
            ParquetPartitionId partitionId,
            FileStorageSettings storageSettings,
            String[] columnNames,
            UserDomainIdentity userIdentity);

    /**
     * Loads multiple monthly partitions that cover the time period between <code>start</code> and <code>end</code>.
     * This method may read more rows than expected, because it operates on full months.
     * @param connectionName  Connection name.
     * @param tableName       Table name (schema.table).
     * @param start           Start date, that is truncated to the beginning of the first loaded month.
     * @param end             End date, the whole month of the given date is loaded.
     * @param storageSettings Storage settings to identify the parquet stored table to load.
     * @param columnNames     Optional array of requested column names. All columns are loaded without filtering when the argument is null.
     * @param userIdentity    User identity, specifies the data domain.
     * @return Dictionary of loaded partitions, keyed by the partition id (that identifies a loaded month).
     */
    Map<ParquetPartitionId, LoadedMonthlyPartition> loadPartitionsForMonthsRange(
            String connectionName,
            PhysicalTableName tableName,
            LocalDate start,
            LocalDate end,
            FileStorageSettings storageSettings,
            String[] columnNames,
            UserDomainIdentity userIdentity);

    /**
     * Loads multiple monthly partitions that cover the time period between <code>start</code> and <code>end</code>,
     * limited to the <code>maxRecentMonthsToLoad</code> most recent partitions.
     * This method may read more rows than expected, because it operates on full months.
     * @param connectionName  Connection name.
     * @param tableName       Table name (schema.table).
     * @param startBoundary   Start date, that is truncated to the beginning of the first loaded month. If null, then the oldest loaded partition marks the limit.
     * @param endBoundary     End date, the whole month of the given date is loaded. If null, then the current month is taken.
     * @param storageSettings Storage settings to identify the parquet stored table to load.
     * @param columnNames     Optional array of requested column names. All columns are loaded without filtering when the argument is null.
     * @param maxRecentMonthsToLoad     Limit of partitions loaded, with the preference of the most recent ones.
     * @param userIdentity    User identity, specifies the data domain.
     * @return Dictionary of loaded partitions, keyed by the partition id (that identifies a loaded month).
     */
    Map<ParquetPartitionId, LoadedMonthlyPartition> loadRecentPartitionsForMonthsRange(
            String connectionName,
            PhysicalTableName tableName,
            LocalDate startBoundary,
            LocalDate endBoundary,
            FileStorageSettings storageSettings,
            String[] columnNames,
            int maxRecentMonthsToLoad,
            UserDomainIdentity userIdentity);

    /**
     * Looks up multiple monthly partitions that cover the time period between <code>start</code> and <code>end</code>.
     * This method does not read the partitions. It only returns empty partitions for the time range when data was found.
     * @param connectionName  Connection name.
     * @param tableName       Table name (schema.table).
     * @param start           Start date, that is truncated to the beginning of the first loaded month.
     * @param end             End date, the whole month of the given date is loaded.
     * @param storageSettings Storage settings to identify the parquet stored table to load.
     * @param userIdentity    User identity, specifies the data domain.
     * @return Dictionary of loaded partitions, keyed by the partition id (that identifies a loaded month).
     */
    Map<ParquetPartitionId, LoadedMonthlyPartition> lookupPartitionsForMonthsRange(
            String connectionName,
            PhysicalTableName tableName,
            LocalDate start,
            LocalDate end,
            FileStorageSettings storageSettings,
            UserDomainIdentity userIdentity);

    /**
     * Saves the data for a single monthly partition. Finds the range of data for that month in the <code>tableDataChanges</code>.
     * Also deletes rows that should be deleted. In case that the file was modified since it was loaded into the loaded partition
     * snapshot (parameter: <code>loadedPartition</code>), the partition is reloaded using an exclusive write lock and the changes
     * are applied to the most recent data.
     *
     * @param loadedPartition  Loaded partition, identifies the partition id. The loaded partition may contain no data.
     * @param tableDataChanges Table data changes to be applied.
     * @param storageSettings  Storage settings to identify the target folder, file names and column names used for matching.
     * @param userIdentity     User identity, specifies the data domain.
     */
    void savePartition(LoadedMonthlyPartition loadedPartition,
                       TableDataChanges tableDataChanges,
                       FileStorageSettings storageSettings,
                       UserDomainIdentity userIdentity);

    /**
     * Deletes a partition file.
     * @param loadedPartitionId Partition id to delete.
     * @param storageSettings   Storage settings.
     * @param userIdentity      User identity, specifies the data domain.
     * @return True when the file was removed, false otherwise.
     */
    boolean deletePartitionFile(ParquetPartitionId loadedPartitionId,
                                FileStorageSettings storageSettings,
                                UserDomainIdentity userIdentity);
}
