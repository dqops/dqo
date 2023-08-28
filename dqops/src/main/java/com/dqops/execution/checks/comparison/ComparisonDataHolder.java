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

package com.dqops.execution.checks.comparison;

import com.dqops.data.readouts.normalization.SensorReadoutsNormalizedResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.metadata.timeseries.TimeSeriesMode;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntBigList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntLists;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.LongColumn;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Holds the data for comparison, indexed by the data group hash and optionally by the time period.
 */
public class ComparisonDataHolder {
    private final TimeSeriesMode timeSeriesMode;
    private final DoubleColumn valueColumn;
    private ComparisonDataGroupHashRowIndexes timeIndependentIndexes;
    private LinkedHashMap<LocalDateTime, ComparisonDataGroupHashRowIndexes> byTimePeriodIndexes;

    /**
     * Creates a table data holder.
     * @param timeSeriesMode Time series mode.
     */
    public ComparisonDataHolder(TimeSeriesMode timeSeriesMode, DoubleColumn valueColumn) {
        this.timeSeriesMode = timeSeriesMode;
        this.valueColumn = valueColumn;
    }

    /**
     * Creates a comparison data holder, indexing all results by the data group hash and optionally by the time period, when the data is additionally grouped by a time period.
     * @param sensorRunParameters Sensor run parameters.
     * @param normalizedSensorResults Normalized sensor results.
     * @param valueColumn Value column.
     * @return Rows indexed by data group hashes.
     */
    public static ComparisonDataHolder create(SensorExecutionRunParameters sensorRunParameters,
                                              SensorReadoutsNormalizedResult normalizedSensorResults,
                                              DoubleColumn valueColumn) {
        TimeSeriesMode timeSeriesMode = sensorRunParameters.getTimeSeries().getMode();
        LongColumn dataGroupHashColumn = normalizedSensorResults.getDataGroupHashColumn();
        DateTimeColumn timePeriodColumn = normalizedSensorResults.getTimePeriodColumn();
        int rowCount = normalizedSensorResults.getTable().rowCount();

        ComparisonDataHolder comparisonDataHolder = new ComparisonDataHolder(timeSeriesMode, valueColumn);

        if (timeSeriesMode == TimeSeriesMode.current_time) {
            ComparisonDataGroupHashRowIndexes indexes = new ComparisonDataGroupHashRowIndexes(null, valueColumn);
            comparisonDataHolder.timeIndependentIndexes = indexes;
            for (int i = 0; i < rowCount; i++) {
                long dataGroupHash = dataGroupHashColumn.getLong(i);
                indexes.addRowIndex(dataGroupHash, i);
            }
        } else {
            assert timeSeriesMode == TimeSeriesMode.timestamp_column;
            LinkedHashMap<LocalDateTime, ComparisonDataGroupHashRowIndexes> timePeriodsMap = new LinkedHashMap<>();
            comparisonDataHolder.byTimePeriodIndexes = timePeriodsMap;
            for (int i = 0; i < rowCount; i++) {
                long dataGroupHash = dataGroupHashColumn.getLong(i);
                LocalDateTime timePeriod = timePeriodColumn.get(i);
                ComparisonDataGroupHashRowIndexes timePeriodIndexes = timePeriodsMap.get(timePeriod);
                if (timePeriodIndexes == null) {
                    timePeriodIndexes = new ComparisonDataGroupHashRowIndexes(timePeriod, valueColumn);
                    timePeriodsMap.put(timePeriod, timePeriodIndexes);
                }
                timePeriodIndexes.addRowIndex(dataGroupHash, i);
            }
        }

        return comparisonDataHolder;
    }

    /**
     * Checks if there is the result for the time period and the data group hash.
     * @param timePeriod Time period. Does not matter if the data is not date partitioned and is ignored (for profiling and monitoring checks).
     * @param dataGroupHash Data group hash.
     * @return True when the result is present, false otherwise.
     */
    public boolean containsValue(LocalDateTime timePeriod, long dataGroupHash) {
        if (this.timeSeriesMode == TimeSeriesMode.current_time) {
            return this.timeIndependentIndexes.containsDataGroup(dataGroupHash);
        }
        else {
            ComparisonDataGroupHashRowIndexes comparisonDataGroupHashRowIndexes = this.byTimePeriodIndexes.get(timePeriod);
            if (comparisonDataGroupHashRowIndexes == null) {
                return false;
            }

            return comparisonDataGroupHashRowIndexes.containsDataGroup(dataGroupHash);
        }
    }

    /**
     * Returns the value for the time period and data group hash.
     * @param timePeriod Time period. Does not matter if the data is not date partitioned and is ignored (for profiling and monitoring checks).
     * @param dataGroupHash Data group hash.
     * @return The value retrieved from the actual values. The value could be null when we do not have the value or the sensor returned null.
     */
    public Double lookupValue(LocalDateTime timePeriod, long dataGroupHash) {
        if (this.timeSeriesMode == TimeSeriesMode.current_time) {
            int rowIndexForDataGroup = this.timeIndependentIndexes.getRowIndexForDataGroup(dataGroupHash);
            if (rowIndexForDataGroup < 0) {
                return null;
            }

            return this.valueColumn.get(rowIndexForDataGroup);
        }
        else {
            ComparisonDataGroupHashRowIndexes comparisonDataGroupHashRowIndexes = this.byTimePeriodIndexes.get(timePeriod);
            if (comparisonDataGroupHashRowIndexes == null) {
                return null;
            }

            int rowIndexForDataGroup = comparisonDataGroupHashRowIndexes.getRowIndexForDataGroup(dataGroupHash);
            if (rowIndexForDataGroup < 0) {
                return null;
            }

            return this.valueColumn.get(rowIndexForDataGroup);
        }
    }

    /**
     * Streams all values from the list.
     * @return Stream of values.
     */
    public Stream<ComparedValue> allValuesStream() {
        if (this.timeSeriesMode == TimeSeriesMode.current_time) {
            return this.timeIndependentIndexes.streamValues();
        }

        assert this.timeSeriesMode == TimeSeriesMode.timestamp_column;

        return this.byTimePeriodIndexes.entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().streamValues());
    }

    /**
     * Find row indexes in the current table that do not have matching rows in the other table.
     * @param comparedTableHolder Compared table holder.
     * @return Row indexes with lookup miss.
     */
    public int[] findRowIndexesNotInOtherDataHolder(ComparisonDataHolder comparedTableHolder) {
        IntArrayList indexesList = new IntArrayList();

        if (this.timeSeriesMode == TimeSeriesMode.current_time) {
            this.timeIndependentIndexes.collectRowIndexesNotMatching(comparedTableHolder, indexesList);
        } else {
            assert this.timeSeriesMode == TimeSeriesMode.timestamp_column;
            for (ComparisonDataGroupHashRowIndexes dayIndexes : this.byTimePeriodIndexes.values()) {
                dayIndexes.collectRowIndexesNotMatching(comparedTableHolder, indexesList);
            }
        }

        int[] rowIndexesWithLookupMiss = indexesList.toIntArray();
        Arrays.sort(rowIndexesWithLookupMiss);
        return rowIndexesWithLookupMiss;
    }
}
