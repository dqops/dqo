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

import com.dqops.checks.CheckTimeScale;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.parquet.Strings;

/**
 * Time series gradient type (daily, monthly, quarterly, monthly, weekly, hourly).
 */
public enum TimePeriodGradient {
    /**
     * One value per year.
     */
    @JsonProperty("year")
    year(1),

    /**
     * One value per quarter.
     */
    @JsonProperty("quarter")
    quarter(2),

    /**
     * One value per month.
     */
    @JsonProperty("month")
    month(3),

    /**
     * One value per week.
     */
    @JsonProperty("week")
    week(4),

    /**
     * One value per day.
     */
    @JsonProperty("day")
    day(5),

    /**
     * One value per hour.
     */
    @JsonProperty("hour")
    hour(6),

    /**
     * One value per millisecond, which effectively means collecting all values.
     */
    @JsonProperty("millisecond")
    millisecond(7);

    private int rank;

    /**
     * Defines an enum with a sortable rank. Smaller values are for more coarse scales (monthly, etc).
     * @param rank Sortable rank.
     */
    TimePeriodGradient(int rank) {
        this.rank = rank;
    }

    /**
     * Returns the rank of the value.
     * @return Rank, smaller value means a more coarse time period. Bigger values - more precise values.
     */
    public int getRank() {
        return rank;
    }

    /**
     * Converts a string to a time gradient.
     * @param value String value
     * @return Time gradient.
     */
    public static TimePeriodGradient fromString(String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }

        switch (value) {
            case "day":
            case "daily":
                return day;

            case "month":
            case "monthly": // to allow compatibility with time monitoring and partitioned time gradients
                return month;

            case "hour":
                return hour;

            case "year":
                return year;

            case "quarter":
                return quarter;

            case "week":
                return week;

            case "millisecond":
                return millisecond;

            default:
                throw new EnumConstantNotPresentException(TimePeriodGradient.class, value);
        }
    }

    /**
     * Converts the time gradient to a time scale for matching gradients. Returns null when there is no match.
     * @return Time scale or null.
     */
    public CheckTimeScale toTimeScale() {
        if (this == day) {
            return CheckTimeScale.daily;
        }

        if (this == month) {
            return CheckTimeScale.monthly;
        }

        return null;
    }
}
