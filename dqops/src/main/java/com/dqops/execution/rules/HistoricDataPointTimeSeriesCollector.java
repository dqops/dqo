/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
    private final DoubleColumn expectedValueColumn;
    private final InstantColumn executedAtColumn;
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
		this.timePeriodColumn = timeSeriesData.dateTimeColumn(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME);
        this.timePeriodUtcColumn = timeSeriesData.instantColumn(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME);
		this.actualValueColumn = timeSeriesData.doubleColumn(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
        this.expectedValueColumn = timeSeriesData.doubleColumn(SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME);
        this.executedAtColumn = timeSeriesData.instantColumn(SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME);
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
            Double rowActualValue = !this.actualValueColumn.isMissing(rowIndex) ? this.actualValueColumn.get(rowIndex) : null;
            Double rowExpectedValue = !this.expectedValueColumn.isMissing(rowIndex) ? this.expectedValueColumn.get(rowIndex) : null;
            int timePeriodsDifference = (int)LocalDateTimePeriodUtility.calculateDifferenceInPeriodsCount(rowTruncatedTimePeriod, readoutTimestamp, this.gradient);
            ZoneOffset zoneOffsetFix = ZoneOffset.ofTotalSeconds((int) timeZoneOffsetDuration.getSeconds());
            Instant rowTimePeriodInstant = rowTruncatedTimePeriod.toInstant(zoneOffsetFix);

            HistoricDataPoint historicDataPoint = new HistoricDataPoint(rowTimePeriodInstant, rowTruncatedTimePeriod, -timePeriodsDifference, rowActualValue, rowExpectedValue);
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

            Double rowActualValue = !this.actualValueColumn.isMissing(rowIndex) ? this.actualValueColumn.get(rowIndex) : null;
            Double rowExpectedValue = !this.expectedValueColumn.isMissing(rowIndex) ? this.expectedValueColumn.get(rowIndex) : null;

            HistoricDataPoint historicDataPoint = new HistoricDataPoint(rowTimePeriodUtc, rowTimePeriod, -i - 1, rowActualValue, rowExpectedValue);
            historicDataPoints[readoutsCount - i - 1] = historicDataPoint;
        }

        return historicDataPoints;
    }

    /**
     * Looks up the previous result for the same time period.
     * @param readoutTimestamp The time period to find.
     * @return Previous result with its execution time, or null when no value was found.
     */
    public HistoricResultPreviousRun getPreviousResult(LocalDateTime readoutTimestamp) {
        if (this.timePeriodIndex == null) {
            this.timePeriodIndex = new LongIndex(this.timePeriodColumn);
        }

        Selection rowSelection = this.timePeriodIndex.get(readoutTimestamp);
        if (rowSelection.isEmpty()) {
            return null;
        }

        int[] rowIndexes = rowSelection.toArray();
        int rowIndex = rowIndexes[0];

        if (this.executedAtColumn.isMissing(rowIndex)) {
            return  null;
        }

        Double previousActualValue = this.actualValueColumn.get(rowIndex);
        Double previousExpectedValue = this.expectedValueColumn.get(rowIndex);
        Instant previousExecutedAt = this.executedAtColumn.get(rowIndex);

        return new HistoricResultPreviousRun(previousActualValue, previousExpectedValue, previousExecutedAt);
    }
}
