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
package ai.dqo.utils.datetime;

import ai.dqo.data.readings.normalization.SensorResultNormalizeException;
import ai.dqo.metadata.groupings.TimeSeriesGradient;

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
     * @param timeSeriesGradient Time series gradient.
     * @return Truncated date time, at the beginning of the requested period.
     */
    public static LocalDateTime truncateTimePeriod(LocalDateTime dateTime, TimeSeriesGradient timeSeriesGradient) {
        switch (timeSeriesGradient) {
            case YEAR:
                return LocalDateTime.of(LocalDate.of(dateTime.getYear(), 1, 1), LocalTime.MIDNIGHT);
            case QUARTER:
                int monthValue = dateTime.getMonthValue();
                return LocalDateTime.of(LocalDate.of(dateTime.getYear(), monthValue - ((monthValue - 1) % 3), 1), LocalTime.MIDNIGHT);
            case MONTH:
                return LocalDateTime.of(LocalDate.of(dateTime.getYear(), dateTime.getMonth(), 1), LocalTime.MIDNIGHT);
            case WEEK:
                return LocalDateTime.of(dateTime.toLocalDate().with(WeekFields.of(Locale.US).dayOfWeek(), 1), LocalTime.MIDNIGHT);
            case DAY:
                return dateTime.truncatedTo(ChronoUnit.DAYS);
            case HOUR:
                return dateTime.truncatedTo(ChronoUnit.HOURS);
            default:
                throw new SensorResultNormalizeException(null, "Unsupported time series gradient: " + timeSeriesGradient);
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
