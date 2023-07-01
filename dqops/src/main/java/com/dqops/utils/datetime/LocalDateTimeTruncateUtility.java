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

import com.dqops.data.readouts.normalization.SensorResultNormalizeException;
import com.dqops.metadata.timeseries.TimePeriodGradient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * Helper class that performs truncation of a {@link java.time.LocalDateTime} to the beginning of a requested time gradient.
 */
public class LocalDateTimeTruncateUtility {
    /**
     * Truncates a value of a time period to the beginning of the requested time series gradient.
     * @param dateTime Date time to truncate.
     * @param timePeriodGradient Time series gradient.
     * @return Truncated date time, at the beginning of the requested period.
     */
    public static LocalDateTime truncateTimePeriod(LocalDateTime dateTime, TimePeriodGradient timePeriodGradient) {
        switch (timePeriodGradient) {
            case year:
                return LocalDateTime.of(LocalDate.of(dateTime.getYear(), 1, 1), LocalTime.MIDNIGHT);
            case quarter:
                int monthValue = dateTime.getMonthValue();
                return LocalDateTime.of(LocalDate.of(dateTime.getYear(), monthValue - ((monthValue - 1) % 3), 1), LocalTime.MIDNIGHT);
            case month:
                return LocalDateTime.of(LocalDate.of(dateTime.getYear(), dateTime.getMonth(), 1), LocalTime.MIDNIGHT);
            case week:
                return LocalDateTime.of(dateTime.toLocalDate().with(WeekFields.of(Locale.US).dayOfWeek(), 1), LocalTime.MIDNIGHT);
            case day:
                return dateTime.truncatedTo(ChronoUnit.DAYS);
            case hour:
                return dateTime.truncatedTo(ChronoUnit.HOURS);
            case millisecond:
                return dateTime; // no truncation
            default:
                throw new SensorResultNormalizeException(null, "Unsupported time series gradient: " + timePeriodGradient);
        }
    }

    /**
     * Returns a date that is truncated to the beginning (1st day) of the month.
     * @param date Date to be truncated.
     * @return Truncated date.
     */
    public static LocalDate truncateMonth(LocalDate date) {
        return LocalDate.of(date.getYear(), date.getMonth(), 1);
    }
}
