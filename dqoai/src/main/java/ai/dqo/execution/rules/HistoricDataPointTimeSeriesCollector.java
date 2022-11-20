/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.execution.rules;

import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.utils.datetime.LocalDateTimePeriodUtility;
import ai.dqo.utils.datetime.LocalDateTimeTruncateUtility;
import tech.tablesaw.api.DateTimeColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.index.LongIndex;
import tech.tablesaw.selection.Selection;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

/**
 * Helper class that is wrapping a normalized sensor result dataset for a single time series dataset.
 */
public class HistoricDataPointTimeSeriesCollector {
    private final Table timeSeriesData;
    private final DateTimeColumn timePeriodColumn;
    private final DoubleColumn actualValueColumn;
    private final TimeSeriesGradient gradient;
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
												TimeSeriesGradient gradient,
												ZoneId timeZoneId) {
        this.timeSeriesData = timeSeriesData;
		this.timePeriodColumn = (DateTimeColumn) timeSeriesData.column(SensorReadoutsNormalizedResult.TIME_PERIOD_COLUMN_NAME);
		this.actualValueColumn = (DoubleColumn) timeSeriesData.column(SensorReadoutsNormalizedResult.ACTUAL_VALUE_COLUMN_NAME);
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
        Selection readoutsSinceStartTime = this.timePeriodIndex.atLeast(startTimePeriod);  // inclusive
        Selection readoutBeforeReadoutTimestamp = this.timePeriodIndex.lessThan(readoutTimestamp); // exclusive
        Selection readoutsInRange = readoutsSinceStartTime.and(readoutBeforeReadoutTimestamp);
        int[] rowIndexes = readoutsInRange.toArray();
        Arrays.sort(rowIndexes); // just in case...

        for (int rowIndex : rowIndexes) {
            LocalDateTime rowTimePeriod = this.timePeriodColumn.get(rowIndex);
            LocalDateTime rowTruncatedTimePeriod = LocalDateTimeTruncateUtility.truncateTimePeriod(rowTimePeriod, this.gradient);
            Double rowActualValue = this.actualValueColumn.get(rowIndex);
            int timePeriodsDifference = (int)LocalDateTimePeriodUtility.calculateDifferenceInPeriodsCount(rowTruncatedTimePeriod, readoutTimestamp, this.gradient);
            Instant rowTimePeriodInstant = rowTruncatedTimePeriod.toInstant(this.timeZoneId.getRules().getOffset(rowTruncatedTimePeriod));

            HistoricDataPoint historicDataPoint = new HistoricDataPoint(rowTimePeriodInstant, rowTruncatedTimePeriod, -timePeriodsDifference, rowActualValue);
            historicDataPoints[timePeriodsCount - timePeriodsDifference] = historicDataPoint;
        }

        return historicDataPoints;
    }
}
