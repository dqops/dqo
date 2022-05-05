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

import ai.dqo.metadata.groupings.TimeSeriesGradient;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Utility classes that perform calculation on {@link java.time.LocalDateTime} using time gradients.
 */
public class LocalDateTimePeriodUtility {
    /**
     * Calculates the number of time periods between the <code>start</code> timestamp of a previous reading
     * and the current sensor reading that is the <code>end</code> timestamp.
     * @param start Start timestamp.
     * @param end End timestamp.
     * @param gradient Time gradient.
     * @return Number of time periods. The type of the time period (weeks, days, etc.) is configured as the time gradient.
     */
    public static long calculateDifferenceInPeriodsCount(LocalDateTime start, LocalDateTime end, TimeSeriesGradient gradient) {
        switch (gradient) {
            case YEAR:
                return start.until(end, ChronoUnit.YEARS);
            case QUARTER:
                return start.until(end, ChronoUnit.MONTHS) / 3;
            case MONTH:
                return start.until(end, ChronoUnit.MONTHS);
            case WEEK:
                return start.until(end, ChronoUnit.WEEKS);
            case DAY:
                return start.until(end, ChronoUnit.DAYS);
            case HOUR:
                return start.until(end, ChronoUnit.HOURS);
            default:
                throw new RuntimeException("Unsupported gradient: " + gradient);
        }
    }

    /**
     * Calculates a date that is <code>timePeriodsCount</code> time series gradients (months, days, etc.) before <code>readingTimestamp</code>.
     * @param readingTimestamp Reference date.
     * @param timePeriodsCount Number of time periods.
     * @param gradient Time gradient.
     * @return Date before the given number of periods.
     */
    public static LocalDateTime calculateLocalDateTimeMinusTimePeriods(LocalDateTime readingTimestamp, int timePeriodsCount, TimeSeriesGradient gradient) {
        switch (gradient) {
            case YEAR:
                return readingTimestamp.minus(timePeriodsCount, ChronoUnit.YEARS);
            case QUARTER:
                return readingTimestamp.minus(timePeriodsCount * 3, ChronoUnit.MONTHS);
            case MONTH:
                return readingTimestamp.minus(timePeriodsCount, ChronoUnit.MONTHS);
            case WEEK:
                return readingTimestamp.minus(timePeriodsCount, ChronoUnit.WEEKS);
            case DAY:
                return readingTimestamp.minus(timePeriodsCount, ChronoUnit.DAYS);
            case HOUR:
                return readingTimestamp.minus(timePeriodsCount, ChronoUnit.HOURS);
            default:
                throw new RuntimeException("Unsupported gradient: " + gradient);
        }
    }
}
