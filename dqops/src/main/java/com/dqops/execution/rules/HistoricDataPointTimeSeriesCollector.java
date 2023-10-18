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
package com.dqops.execution.rules;

import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.utils.datetime.LocalDateTimePeriodUtility;
import com.dqops.utils.datetime.LocalDateTimeTruncateUtility;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.InstantColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.index.LongIndex;
import tech.tablesaw.selection.Selection;

import java.time.*;
import java.util.Arrays;

/**
 * Helper class that is wrapping a normalized sensor result dataset for a single time series dataset.
 */
public class HistoricDataPointTimeSeriesCollector {
    private final Table timeSeriesData;
    private final DateTimeColumn timePeriodColumn;
    private final InstantColumn timePeriodUtcColumn;
    private final DoubleColumn actualValueColumn;
    private final TimePeriodGradient gradient;
    private final ZoneId timeZoneId;
    private LongIndex timePeriodIndex;

    /**
     * Creates a time series historic data point collector, given a table with normalized sensor results for a single time series.
     * The results in the table must be sorted ascending by the time series.
     * @param timeSeriesData Time series data.
     * @param gradient Time series gradient.
     * @param timeZoneId Time zone of the data source to convert the local time to the absolute instants.
     */
    public HistoricDataPointTimeSeriesCollector(Table timeSeriesData,
												TimePeriodGradient gradient,
												ZoneId timeZoneId) {
        this.timeSeriesData = timeSeriesData;
		this.timePeriodColumn = (DateTimeColumn) timeSeriesData.column(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME);
        this.timePeriodUtcColumn = (InstantColumn) timeSeriesData.column(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME);
		this.actualValueColumn = (DoubleColumn) timeSeriesData.column(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
        this.gradient = gradient;
        this.timeZoneId = timeZoneId;
    }

    /**
     * Returns an array of historic data points. Data points are ordered by the time period timestamp and are before
     * the <code>readoutTimestamp</code>. The array may contain null entries if there is no value for a past data point.
     * @param readoutTimestamp Sensor reading data point.
     * @param timePeriodsCount Time periods count to return.
     * @return An array of time periods within the given range.
     */
    public HistoricDataPoint[] getHistoricDataPointsBefore(LocalDateTime readoutTimestamp, int timePeriodsCount) {
        assert timePeriodsCount >= 0;
        HistoricDataPoint[] historicDataPoints = new HistoricDataPoint[timePeriodsCount];
        if (timePeriodsCount == 0) {
            return historicDataPoints;
        }

        if (this.timePeriodIndex == null) {
			this.timePeriodIndex = new LongIndex(this.timePeriodColumn);
        }

        LocalDateTime startTimePeriod = LocalDateTimePeriodUtility.calculateLocalDateTimeMinusTimePeriods(readoutTimestamp, timePeriodsCount, this.gradient);
        LocalDateTime endTimePeriod = LocalDateTimeTruncateUtility.truncateTimePeriod(readoutTimestamp, this.gradient);
        Selection readoutsSinceStartTime = this.timePeriodIndex.atLeast(startTimePeriod);  // inclusive
        Selection readoutBeforeReadoutTimestamp = this.timePeriodIndex.lessThan(endTimePeriod); // exclusive
        Selection readoutsInRange = readoutsSinceStartTime.and(readoutBeforeReadoutTimestamp);
        int[] rowIndexes = readoutsInRange.toArray();
        Arrays.sort(rowIndexes); // just in case...

        for (int rowIndex : rowIndexes) {
            LocalDateTime rowTimePeriod = this.timePeriodColumn.get(rowIndex);
            Instant rowTimePeriodInDefaultTz = rowTimePeriod.toInstant(this.timeZoneId.getRules().getOffset(rowTimePeriod));
            Instant rowTimePeriodUtc = this.timePeriodUtcColumn.get(rowIndex);
            if (rowTimePeriodUtc == null) {
                rowTimePeriodUtc = rowTimePeriodInDefaultTz;
            }
            Duration timeZoneOffsetDuration = Duration.between(rowTimePeriodUtc, rowTimePeriodInDefaultTz); // this is the difference between the database default timezone and the DQOps instance default time zone

            LocalDateTime rowTruncatedTimePeriod = LocalDateTimeTruncateUtility.truncateTimePeriod(rowTimePeriod, this.gradient);
            Double rowActualValue = this.actualValueColumn.get(rowIndex);
            int timePeriodsDifference = (int)LocalDateTimePeriodUtility.calculateDifferenceInPeriodsCount(rowTruncatedTimePeriod, readoutTimestamp, this.gradient);
            ZoneOffset zoneOffsetFix = ZoneOffset.ofTotalSeconds((int) timeZoneOffsetDuration.getSeconds());
            Instant rowTimePeriodInstant = rowTruncatedTimePeriod.toInstant(zoneOffsetFix);

            HistoricDataPoint historicDataPoint = new HistoricDataPoint(rowTimePeriodInstant, rowTruncatedTimePeriod, -timePeriodsDifference, rowActualValue);
            historicDataPoints[timePeriodsCount - timePeriodsDifference] = historicDataPoint;
        }

        return historicDataPoints;
    }

    /**
     * Returns an array of recent results, without inserting them into valid indexes. It should be used to receive just the last <code>readoutsCount</code> results.
     * @param readoutTimestamp Reference timestamp to find results before.
     * @param readoutsCount Maximum number of previous results to return.
     * @return Array of results, without aligning results to a proper index.
     */
    public HistoricDataPoint[] getHistoricContinuousResultsBefore(LocalDateTime readoutTimestamp, int readoutsCount) {
        HistoricDataPoint[] historicDataPoints = new HistoricDataPoint[readoutsCount];
        if (readoutsCount == 0) {
            return historicDataPoints;
        }

        if (this.timePeriodIndex == null) {
            this.timePeriodIndex = new LongIndex(this.timePeriodColumn);
        }

        Selection readoutBeforeReadoutTimestamp = this.timePeriodIndex.lessThan(readoutTimestamp);
        int[] rowIndexes = readoutBeforeReadoutTimestamp.toArray();
        Arrays.sort(rowIndexes); // just in case...

        for (int i = 0; i < readoutsCount && i < rowIndexes.length; i++) {
            int rowIndex = rowIndexes[rowIndexes.length - i - 1];
            LocalDateTime rowTimePeriod = this.timePeriodColumn.get(rowIndex);
            Instant rowTimePeriodInDefaultTz = rowTimePeriod.toInstant(this.timeZoneId.getRules().getOffset(rowTimePeriod));
            Instant rowTimePeriodUtc = this.timePeriodUtcColumn.get(rowIndex);
            if (rowTimePeriodUtc == null) {
                rowTimePeriodUtc = rowTimePeriodInDefaultTz;
            }

            Double rowActualValue = this.actualValueColumn.get(rowIndex);

            HistoricDataPoint historicDataPoint = new HistoricDataPoint(rowTimePeriodUtc, rowTimePeriod, -i - 1, rowActualValue);
            historicDataPoints[readoutsCount - i - 1] = historicDataPoint;
        }

        return historicDataPoints;
    }
}
