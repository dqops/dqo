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
package com.dqops.metadata.groupings;

import com.dqops.checks.CheckTimeScale;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.timeseries.TimeSeriesMode;

/**
 * Object mother for TimeSeriesConfigurationSpec.
 */
public class TimeSeriesConfigurationSpecObjectMother {
    /**
     * Creates a current time time series for a given gradient.
     * @param gradient Gradient.
     * @return Time series.
     */
    public static TimeSeriesConfigurationSpec createCurrentTimeSeries(TimePeriodGradient gradient) {
        TimeSeriesConfigurationSpec timeSeries = new TimeSeriesConfigurationSpec();
        timeSeries.setMode(TimeSeriesMode.current_time);
        timeSeries.setTimeGradient(gradient);
        return timeSeries;
    }

    /**
     * Creates a timestamp column time series for a given gradient.
     * @param timestampColumnName Timestamp column name.
     * @param gradient Gradient.
     * @return Time series.
     */
    public static TimeSeriesConfigurationSpec createTimestampColumnTimeSeries(String timestampColumnName, TimePeriodGradient gradient) {
        TimeSeriesConfigurationSpec timeSeries = new TimeSeriesConfigurationSpec();
        timeSeries.setMode(TimeSeriesMode.timestamp_column);
        timeSeries.setTimestampColumn(timestampColumnName);
        timeSeries.setTimeGradient(gradient);
        return timeSeries;
    }

    /**
     * Creates a current time time series for a profiling check.
     * @return Time series.
     */
    public static TimeSeriesConfigurationSpec createTimeSeriesForProfiling() {
        TimeSeriesConfigurationSpec timeSeries = new TimeSeriesConfigurationSpec();
        timeSeries.setMode(TimeSeriesMode.current_time);
        timeSeries.setTimeGradient(TimePeriodGradient.millisecond);
        return timeSeries;
    }

    /**
     * Creates a current time time series for a monitoring (daily or monthly).
     * @param checkTimeScale Check time scale.
     * @return Time series.
     */
    public static TimeSeriesConfigurationSpec createTimeSeriesForMonitoring(CheckTimeScale checkTimeScale) {
        TimeSeriesConfigurationSpec timeSeries = new TimeSeriesConfigurationSpec();
        timeSeries.setMode(TimeSeriesMode.current_time);
        switch (checkTimeScale) {
            case daily:
                timeSeries.setTimeGradient(TimePeriodGradient.day);
                break;
            case monthly:
                timeSeries.setTimeGradient(TimePeriodGradient.month);
                break;
        }
        return timeSeries;
    }

    /**
     * Creates a current time time series for a date partitioned check (daily or monthly).
     * @param checkTimeScale Check time scale.
     * @param datePartitioningColumn Column name with a date that is used for date partitioning.
     * @return Time series.
     */
    public static TimeSeriesConfigurationSpec createTimeSeriesForPartitionedCheck(CheckTimeScale checkTimeScale, String datePartitioningColumn) {
        TimeSeriesConfigurationSpec timeSeries = new TimeSeriesConfigurationSpec();
        timeSeries.setMode(TimeSeriesMode.timestamp_column);
        timeSeries.setTimestampColumn(datePartitioningColumn);
        switch (checkTimeScale) {
            case daily:
                timeSeries.setTimeGradient(TimePeriodGradient.day);
                break;
            case monthly:
                timeSeries.setTimeGradient(TimePeriodGradient.month);
                break;
        }
        return timeSeries;
    }
}
