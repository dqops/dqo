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
public enum ProfilingTimePeriodTruncation {
    /**
     * Monthly.
     */
    @JsonProperty("store_the_most_recent_result_per_month")
    store_the_most_recent_result_per_month,

    /**
     * Weekly.
     */
    @JsonProperty("store_the_most_recent_result_per_week")
    store_the_most_recent_result_per_week,

    /**
     * Daily.
     */
    @JsonProperty("store_the_most_recent_result_per_day")
    store_the_most_recent_result_per_day,

    /**
     * Hourly.
     */
    @JsonProperty("store_the_most_recent_result_per_hour")
    store_the_most_recent_result_per_hour,

    /**
     * Store all results at a millisecond scale.
     */
    @JsonProperty("store_all_results_without_date_truncation")
    store_all_results_without_date_truncation;

    /**
     * Converts the profiling time period to a supported time scale.
     * @return Time period gradient used to align (truncate) the dates.
     */
    public TimePeriodGradient toTimePeriodGradient() {
        switch (this) {
            case store_the_most_recent_result_per_month:
                return TimePeriodGradient.month;
            case store_the_most_recent_result_per_week:
                return TimePeriodGradient.week;
            case store_the_most_recent_result_per_day:
                return TimePeriodGradient.day;
            case store_the_most_recent_result_per_hour:
                return TimePeriodGradient.hour;
            case store_all_results_without_date_truncation:
                return TimePeriodGradient.millisecond;
            default:
                throw new DqoRuntimeException("Unsupported profiling time period: " + this);
        }
    }
}
