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
import com.dqops.utils.tables.TableColumnUtility;
import tech.tablesaw.api.LongColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.index.LongIndex;
import tech.tablesaw.selection.Selection;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Dictionary of identified time series in the historic sensor readout results.
 */
public class SensorReadoutsTimeSeriesMap {
    private final Map<SensorReadoutTimeSeriesKey, SensorReadoutsTimeSeriesData> entries = new LinkedHashMap<>();
    private LocalDate firstLoadedMonth;
    private LocalDate lastLoadedMonth;
    private Table allLoadedData;
    private LongColumn checkHashColumn;
    private LongColumn dataStreamHashColumn;
    private LongIndex checkHashIndex;
    private LongIndex dataStreamHashIndex;

    /**
     * Create a time series map.
     * @param firstLoadedMonth The date of the first loaded month.
     * @param lastLoadedMonth The date of the last loaded month.
     */
    public SensorReadoutsTimeSeriesMap(LocalDate firstLoadedMonth, LocalDate lastLoadedMonth, Table allLoadedData) {
        this.firstLoadedMonth = firstLoadedMonth;
        this.lastLoadedMonth = lastLoadedMonth;
        this.allLoadedData = allLoadedData;
        this.checkHashColumn = (LongColumn) allLoadedData.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME);
        this.dataStreamHashColumn = (LongColumn) TableColumnUtility.findColumn(allLoadedData,
                SensorReadoutsColumnNames.DATA_GROUP_HASH_COLUMN_NAME);
        this.checkHashIndex = new LongIndex(this.checkHashColumn);
        this.dataStreamHashIndex = new LongIndex(this.dataStreamHashColumn);
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

//    /**
//     * Returns a known time series for the given key or null when no historic data for this time series is present.
//     * @param key Time series key.
//     * @return Time series data or null.
//     */
//    public SensorReadoutsTimeSeriesData findTimeSeriesData(SensorReadoutTimeSeriesKey key) {
//        return this.entries.get(key);
//    }

    /**
     * Returns a known time series for the given key (check and dimension) or null when no historic data for this time series is present.
     * @param checkHashId Check hash code id.
     * @param dimensionId Dimension hash code id.
     * @return Time series data or null.
     */
    public SensorReadoutsTimeSeriesData findTimeSeriesData(long checkHashId, long dimensionId) {
        SensorReadoutTimeSeriesKey key = new SensorReadoutTimeSeriesKey(checkHashId, dimensionId);
        SensorReadoutsTimeSeriesData sensorReadoutsTimeSeriesData = this.entries.get(key);
        if (sensorReadoutsTimeSeriesData != null) {
            return sensorReadoutsTimeSeriesData;
        }

        Selection checkHashRows = this.checkHashIndex.get(checkHashId);
        Selection groupHashRows = this.dataStreamHashIndex.get(dimensionId);

        Table filteredRows = this.allLoadedData.where(checkHashRows.and(groupHashRows));
        Table sortedTimeSeriesTable = filteredRows.sortOn(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME);

        SensorReadoutsTimeSeriesData newSubset = new SensorReadoutsTimeSeriesData(key, sortedTimeSeriesTable);
        return newSubset;
    }

    /**
     * Adds a time series object to the dictionary.
     * @param timeSeries Time series object.
     */
    public void add(SensorReadoutsTimeSeriesData timeSeries) {
		this.entries.put(timeSeries.getKey(), timeSeries);
    }
}
