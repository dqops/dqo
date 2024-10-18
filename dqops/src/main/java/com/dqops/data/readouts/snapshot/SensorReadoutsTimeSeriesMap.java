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
package com.dqops.data.readouts.snapshot;

import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.storage.LoadedMonthlyPartition;
import com.dqops.data.storage.ParquetPartitionId;
import com.dqops.utils.tables.TableColumnUtility;
import lombok.Data;
import tech.tablesaw.api.LongColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.index.LongIndex;
import tech.tablesaw.selection.Selection;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Dictionary of identified time series in the historic sensor readout results.
 */
public class SensorReadoutsTimeSeriesMap {
    private final Map<SensorReadoutTimeSeriesKey, WeakReference<SensorReadoutsTimeSeriesData>> entries = new LinkedHashMap<>();
    private final Map<ParquetPartitionId, LoadedMonthlyPartition> partitionMap;
    private final Map<ParquetPartitionId, PartitionIndexes> partitionIndexes = new TreeMap<>();
    private LocalDate firstLoadedMonth;
    private LocalDate lastLoadedMonth;

    /**
     * Create a time series map.
     * @param firstLoadedMonth The date of the first loaded month.
     * @param lastLoadedMonth The date of the last loaded month.
     * @param partitionMap Dictionary of loaded partitions.
     */
    public SensorReadoutsTimeSeriesMap(LocalDate firstLoadedMonth, LocalDate lastLoadedMonth,
                                       Map<ParquetPartitionId, LoadedMonthlyPartition> partitionMap) {
        this.firstLoadedMonth = firstLoadedMonth;
        this.lastLoadedMonth = lastLoadedMonth;
        this.partitionMap = partitionMap;
        if (partitionMap != null) {
            for (Map.Entry<ParquetPartitionId, LoadedMonthlyPartition> partitionKeyValue : partitionMap.entrySet()) {
                Table partitionData = partitionKeyValue.getValue().getData();
                if (partitionData == null) {
                    return;
                }

                LongColumn checkHashColumn = (LongColumn) partitionData.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME);
                LongColumn dataStreamHashColumn = (LongColumn) TableColumnUtility.findColumn(partitionData,
                            SensorReadoutsColumnNames.DATA_GROUP_HASH_COLUMN_NAME);
                LongIndex checkHashIndex = new LongIndex(checkHashColumn);
                LongIndex dataStreamHashIndex = new LongIndex(dataStreamHashColumn);

                PartitionIndexes partitionIndexesEntry = new PartitionIndexes(checkHashIndex, dataStreamHashIndex, partitionKeyValue.getValue());
                this.partitionIndexes.put(partitionKeyValue.getKey(), partitionIndexesEntry);
            }
        }
    }

    /**
     * Returns the date of the first loaded month.
     * @return The date of the first loaded month.
     */
    public LocalDate getFirstLoadedMonth() {
        return firstLoadedMonth;
    }

    /**
     * Returns the date of the last loaded month.
     * @return The date of the last loaded month.
     */
    public LocalDate getLastLoadedMonth() {
        return lastLoadedMonth;
    }

    /**
     * Returns a known time series for the given key (check and dimension) or null when no historic data for this time series is present.
     * @param checkHashId Check hash code id.
     * @param dimensionId Dimension hash code id.
     * @return Time series data or null.
     */
    public SensorReadoutsTimeSeriesData findTimeSeriesData(long checkHashId, long dimensionId) {
        SensorReadoutTimeSeriesKey key = new SensorReadoutTimeSeriesKey(checkHashId, dimensionId);
        WeakReference<SensorReadoutsTimeSeriesData> sensorReadoutsTimeSeriesDataRef = this.entries.get(key);
        SensorReadoutsTimeSeriesData sensorReadoutsTimeSeriesData = sensorReadoutsTimeSeriesDataRef != null ?
                sensorReadoutsTimeSeriesDataRef.get() : null;

        if (sensorReadoutsTimeSeriesData != null) {
            return sensorReadoutsTimeSeriesData;
        }

        Table allTimeSeriesData = null;

        for (Map.Entry<ParquetPartitionId, PartitionIndexes> partitionIndexesKeyValue : this.partitionIndexes.entrySet()) {
            PartitionIndexes partitionIndexesEntry = partitionIndexesKeyValue.getValue();
            Selection checkHashRows = partitionIndexesEntry.checkHashIndex.get(checkHashId);
            Selection groupHashRows = partitionIndexesEntry.dataStreamHashIndex.get(dimensionId);

            Table partitionDataTable = partitionIndexesEntry.partitionData.getData();
            if (partitionDataTable == null) {
                continue;
            }

            Table filteredPartitionRows = partitionDataTable.where(checkHashRows.and(groupHashRows));
            Table sortedTimeSeriesTable = filteredPartitionRows.sortOn(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME);

            if (allTimeSeriesData == null) {
                allTimeSeriesData = sortedTimeSeriesTable;
            } else {
                allTimeSeriesData.append(sortedTimeSeriesTable);
            }
        }

        if (allTimeSeriesData == null) {
            return null;
        }

        SensorReadoutsTimeSeriesData timeSeriesDataSlice = new SensorReadoutsTimeSeriesData(key, allTimeSeriesData);  // TODO: we could store it in the cache.. but not for the moment, maybe for a different use case
        return timeSeriesDataSlice;
    }

    /**
     * Partition indexes container.
     */
    @Data
    public static class PartitionIndexes {
        /**
         * Check hash index.
         */
        private final LongIndex checkHashIndex;

        /**
         * Data stream (data group) hash index.
         */
        private final LongIndex dataStreamHashIndex;

        /**
         * The partition data.
         */
        private final LoadedMonthlyPartition partitionData;

        public PartitionIndexes(LongIndex checkHashIndex, LongIndex dataStreamHashIndex, LoadedMonthlyPartition monthlyPartition) {
            this.checkHashIndex = checkHashIndex;
            this.dataStreamHashIndex = dataStreamHashIndex;
            this.partitionData = monthlyPartition;
        }
    }
}
