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
package com.dqops.checks;

import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The time period for profiling checks (millisecond, daily, monthly, weekly, hourly).
 * The default profiling check stores one value per month. When profiling checks is re-executed during the month,
 * the previous profiling checks value is overwritten and only the most recent value is stored.
 */
public enum ProfilingTimePeriod {
    /**
     * Monthly.
     */
    @JsonProperty("one_per_month")
    one_per_month,

    /**
     * Weekly.
     */
    @JsonProperty("one_per_week")
    one_per_week,

    /**
     * Daily.
     */
    @JsonProperty("one_per_day")
    one_per_day,

    /**
     * Hourly.
     */
    @JsonProperty("one_per_hour")
    one_per_hour,

    /**
     * Store all results at a millisecond scale.
     */
    @JsonProperty("all_results")
    all_results;

    /**
     * Converts the profiling time period to a supported time scale.
     * @return Time period gradient used to align (truncate) the dates.
     */
    public TimePeriodGradient toTimePeriodGradient() {
        switch (this) {
            case one_per_month:
                return TimePeriodGradient.month;
            case one_per_week:
                return TimePeriodGradient.week;
            case one_per_day:
                return TimePeriodGradient.day;
            case one_per_hour:
                return TimePeriodGradient.hour;
            case all_results:
                return TimePeriodGradient.millisecond;
            default:
                throw new DqoRuntimeException("Unsupported profiling time period: " + this);
        }
    }
}
