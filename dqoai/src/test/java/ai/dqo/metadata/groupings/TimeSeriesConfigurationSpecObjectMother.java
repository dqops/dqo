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
package ai.dqo.metadata.groupings;

/**
 * Object mother for TimeSeriesConfigurationSpec.
 */
public class TimeSeriesConfigurationSpecObjectMother {
    /**
     * Creates a current time time series for a given gradient.
     * @param gradient Gradient.
     * @return Time series.
     */
    public static TimeSeriesConfigurationSpec createCurrentTimeSeries(TimeSeriesGradient gradient) {
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
    public static TimeSeriesConfigurationSpec createTimestampColumnTimeSeries(String timestampColumnName, TimeSeriesGradient gradient) {
        TimeSeriesConfigurationSpec timeSeries = new TimeSeriesConfigurationSpec();
        timeSeries.setMode(TimeSeriesMode.timestamp_column);
        timeSeries.setTimestampColumn(timestampColumnName);
        timeSeries.setTimeGradient(gradient);
        return timeSeries;
    }
}
