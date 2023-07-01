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
package com.dqops.metadata.timeseries;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of time series modes.
 */
public enum TimeSeriesMode {
    /**
     * The time series is build by taking the time of the sensor capture. When the sensor is executed, a sensor query will process the whole table and will calculate a metrics for the time of the sensor capture.
     */
    @JsonProperty("current_time")
    current_time,

    /**
     * The time series is calculated by grouping the data by a truncated value of the timestamp column. The truncation (truncate to a day, hour, month) is configurable as a time gradient.
     * This mode supports analysis of event data, when all rows (facts) are assigned to a data point and could be measured separately.
     */
    @JsonProperty("timestamp_column")
    timestamp_column
}
