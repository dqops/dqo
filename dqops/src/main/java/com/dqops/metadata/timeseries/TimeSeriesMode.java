/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
