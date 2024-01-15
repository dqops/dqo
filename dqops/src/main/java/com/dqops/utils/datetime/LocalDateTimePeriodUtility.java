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
package com.dqops.utils.datetime;

import com.dqops.metadata.timeseries.TimePeriodGradient;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Utility classes that perform calculation on {@link java.time.LocalDateTime} using time gradients.
 */
public class LocalDateTimePeriodUtility {
    /**
     * Calculates the number of time periods between the <code>start</code> timestamp of a previous readout
     * and the current sensor readout that is the <code>end</code> timestamp.
     * @param start Start timestamp.
     * @param end End timestamp.
     * @param gradient Time gradient.
     * @return Number of time periods. The type of the time period (weeks, days, etc.) is configured as the time gradient.
     */
    public static long calculateDifferenceInPeriodsCount(LocalDateTime start, LocalDateTime end, TimePeriodGradient gradient) {
        switch (gradient) {
            case year:
                return start.until(end, ChronoUnit.YEARS);
            case quarter:
                return start.until(end, ChronoUnit.MONTHS) / 3;
            case month:
                return start.until(end, ChronoUnit.MONTHS);
            case week:
                return start.until(end, ChronoUnit.WEEKS);
            case day:
                return start.until(end, ChronoUnit.DAYS);
            case hour:
                return start.until(end, ChronoUnit.HOURS);
            default:
                throw new RuntimeException("Unsupported gradient: " + gradient);
        }
    }

    /**
     * Calculates a date that is <code>timePeriodsCount</code> time series gradients (months, days, etc.) before <code>readoutTimestamp</code>.
     * @param readoutTimestamp Reference date.
     * @param timePeriodsCount Number of time periods.
     * @param gradient Time gradient.
     * @return Date before the given number of periods.
     */
    public static LocalDateTime calculateLocalDateTimeMinusTimePeriods(LocalDateTime readoutTimestamp, int timePeriodsCount, TimePeriodGradient gradient) {
        switch (gradient) {
            case year:
                return readoutTimestamp.minus(timePeriodsCount, ChronoUnit.YEARS);
            case quarter:
                return readoutTimestamp.minus(timePeriodsCount * 3, ChronoUnit.MONTHS);
            case month:
                return readoutTimestamp.minus(timePeriodsCount, ChronoUnit.MONTHS);
            case week:
                return readoutTimestamp.minus(timePeriodsCount, ChronoUnit.WEEKS);
            case day:
                return readoutTimestamp.minus(timePeriodsCount, ChronoUnit.DAYS);
            case hour:
                return readoutTimestamp.minus(timePeriodsCount, ChronoUnit.HOURS);
            default:
                throw new RuntimeException("Unsupported gradient: " + gradient);
        }
    }

    /**
     * Finds the minimum (earliest) date time.
     * @param first First date time (to ensure that there is at least one).
     * @param other The other time periods.
     * @return Earliest date time.
     */
    public static LocalDateTime min(LocalDateTime first, LocalDateTime... other) {
        LocalDateTime result = first;
        for (int i = 0; i < other.length; i++) {
            LocalDateTime otherDateTime = other[i];
            if (otherDateTime != null) {
                if (result != null ) {
                    if (otherDateTime.isBefore(result)) {
                        result = otherDateTime;
                    }
                } else {
                    result = otherDateTime;
                }
            }
        }

        return result;
    }

    /**
     * Finds the maximum (latest) date time.
     * @param first First date time (to ensure that there is at least one).
     * @param other The other time periods.
     * @return Latest date time.
     */
    public static LocalDateTime max(LocalDateTime first, LocalDateTime... other) {
        LocalDateTime result = first;
        for (int i = 0; i < other.length; i++) {
            LocalDateTime otherDateTime = other[i];
            if (otherDateTime != null) {
                if (result != null ) {
                    if (otherDateTime.isAfter(result)) {
                        result = otherDateTime;
                    }
                } else {
                    result = otherDateTime;
                }
            }
        }

        return result;
    }
}
