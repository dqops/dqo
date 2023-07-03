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
