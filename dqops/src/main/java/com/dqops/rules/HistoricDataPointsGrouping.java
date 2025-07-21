/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.rules;

import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.NoSuchElementException;

/**
 * Enumeration of historic data point result grouping that are provided to a rule that uses historic data for prediction.
 */
public enum HistoricDataPointsGrouping {
    @JsonProperty("year")
    year,

    @JsonProperty("quarter")
    quarter,

    @JsonProperty("month")
    month,

    @JsonProperty("week")
    week,

    @JsonProperty("day")
    day,

    @JsonProperty("hour")
    hour,

    @JsonProperty("last_n_readouts")
    last_n_readouts;

    /**
     * Converts the grouping type to a matching time period gradient.
     * @return Matching time period gradient. Returns null for {@link HistoricDataPointsGrouping#last_n_readouts}.
     */
    public TimePeriodGradient toTimePeriodGradient() {
        switch (this) {
            case year:
                return TimePeriodGradient.year;
            case quarter:
                return TimePeriodGradient.quarter;
            case month:
                return TimePeriodGradient.month;
            case week:
                return TimePeriodGradient.week;
            case day:
                return TimePeriodGradient.day;
            case hour:
                return TimePeriodGradient.hour;
            case last_n_readouts:
                return null;
            default:
                throw new NoSuchElementException("Time period grouping not supported.");
        }
    }
}
