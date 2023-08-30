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
package com.dqops.data.storage;

import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.utils.datetime.LocalDateTimeTruncateUtility;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Dummy parquet storage service.
 */
public class DummyParquetPartitionStorageService implements ParquetPartitionStorageService {
    /**
     * Reads the data of one monthly partition.
     *
     * @param partitionId     Partition id.
     * @param storageSettings Storage settings that identify the target table type that is loaded.
     * @param columnNames     Optional array of requested column names. All columns are loaded without filtering when the argument is null.
     * @return Returns a dataset table with the content of the partition. The table (data) is null if the parquet file was not found.
     */
    @Override
    public LoadedMonthlyPartition loadPartition(ParquetPartitionId partitionId, FileStorageSettings storageSettings, String[] columnNames) {
        return new LoadedMonthlyPartition(partitionId, 0L, null);
    }

    /**
     * Loads multiple monthly partitions that cover the time period between <code>start</code> and <code>end</code>.
     * This method may read more rows than expected, because it operates on full months.
     *
     * @param connectionName  Connection name.
     * @param tableName       Table name (schema.table).
     * @param start           Start date, that is truncated to the beginning of the first loaded month.
     * @param end             End date, the whole month of the given date is loaded.
     * @param storageSettings Storage settings to identify the parquet stored table to load.
     * @param columnNames     Optional array of requested column names. All columns are loaded without filtering when the argument is null.
     * @return Dictionary of loaded partitions, keyed by the partition id (that identifies a loaded month).
     */
    @Override
    public Map<ParquetPartitionId, LoadedMonthlyPartition> loadPartitionsForMonthsRange(
            String connectionName, PhysicalTableName tableName, LocalDate start, LocalDate end, FileStorageSettings storageSettings, String[] columnNames) {
        LocalDate startMonth = LocalDateTimeTruncateUtility.truncateMonth(start);
        LocalDate endMonth = LocalDateTimeTruncateUtility.truncateMonth(end);

        Map<ParquetPartitionId, LoadedMonthlyPartition> resultPartitions = new LinkedHashMap<>();

        for (LocalDate currentMonth = startMonth; !currentMonth.isAfter(endMonth);
             currentMonth = currentMonth.plus(1L, ChronoUnit.MONTHS)) {
            ParquetPartitionId partitionId = new ParquetPartitionId(storageSettings.getTableType(), connectionName, tableName, currentMonth);
            LoadedMonthlyPartition currentMonthPartition = loadPartition(partitionId, storageSettings, columnNames);
            if (currentMonthPartition != null) {
                resultPartitions.put(partitionId, currentMonthPartition);
            }
        }

        return resultPartitions;
    }

    /**
     * Loads multiple monthly partitions that cover the time period between <code>start</code> and <code>end</code>,
     * limited to the <code>monthsCount</code> most recent partitions.
     * This method may read more rows than expected, because it operates on full months.
     *
     * @param connectionName  Connection name.
     * @param tableName       Table name (schema.table).
     * @param startBoundary   Start date, that is truncated to the beginning of the first loaded month. If null, then the oldest loaded partition marks the limit.
     * @param endBoundary     End date, the whole month of the given date is loaded. If null, then the current month is taken.
     * @param storageSettings Storage settings to identify the parquet stored table to load.
     * @param columnNames     Optional array of requested column names. All columns are loaded without filtering when the argument is null.
     * @param maxRecentMonthsToLoad     Limit of partitions loaded, with the preference of the most recent ones.
     * @return Dictionary of loaded partitions, keyed by the partition id (that identifies a loaded month).
     */
    @Override
    public Map<ParquetPartitionId, LoadedMonthlyPartition> loadRecentPartitionsForMonthsRange(String connectionName, PhysicalTableName tableName, LocalDate startBoundary, LocalDate endBoundary, FileStorageSettings storageSettings, String[] columnNames, int maxRecentMonthsToLoad) {
        LocalDate startMonth = LocalDateTimeTruncateUtility.truncateMonth(startBoundary);
        LocalDate endMonth = LocalDateTimeTruncateUtility.truncateMonth(endBoundary);

        Map<ParquetPartitionId, LoadedMonthlyPartition> resultPartitions = new LinkedHashMap<>();
        int currentMonthsCount = maxRecentMonthsToLoad;

        for (LocalDate currentMonth = endMonth; !currentMonth.isBefore(startMonth) && currentMonthsCount > 0;
             currentMonth = currentMonth.minusMonths(1L)) {
            ParquetPartitionId partitionId = new ParquetPartitionId(storageSettings.getTableType(), connectionName, tableName, currentMonth);
            LoadedMonthlyPartition currentMonthPartition = loadPartition(partitionId, storageSettings, columnNames);
            if (currentMonthPartition != null) {
                resultPartitions.put(partitionId, currentMonthPartition);
                --currentMonthsCount;
            }
        }

        return resultPartitions;
    }

    /**
     * Saves the data for a single monthly partition. Finds the range of data for that month in the <code>tableDataChanges</code>.
     * Also deletes rows that should be deleted. In case that the file was modified since it was loaded into the loaded partition
     * snapshot (parameter: <code>loadedPartition</code>), the partition is reloaded using an exclusive write lock and the changes
     * are applied to the most recent data.
     *
     * @param loadedPartition  Loaded partition, identifies the partition id. The loaded partition may contain no data.
     * @param tableDataChanges Table data changes to be applied.
     * @param storageSettings  Storage settings to identify the target folder, file names and column names used for matching.
     */
    @Override
    public void savePartition(LoadedMonthlyPartition loadedPartition, TableDataChanges tableDataChanges, FileStorageSettings storageSettings) {
        // do nothing
    }

    /**
     * Deletes a partition file.
     *
     * @param loadedPartitionId Partition id to delete.
     * @param storageSettings   Storage settings.
     * @return True when the file was removed, false otherwise.
     */
    @Override
    public boolean deletePartitionFile(ParquetPartitionId loadedPartitionId, FileStorageSettings storageSettings) {
        return false;
    }
}
