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

package com.dqops.utils.conversion;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;

/**
 * Helper class to convert to date, datetime and instant data types.
 */
public final class DateTypesConverter {
    /**
     * Converts a value to a java {@link Instant} data type. Also parses dates in ISO format.
     * @param value Value of a possible timestamp.
     * @param timeZoneId Default timezone to apply to local dates.
     * @return Parsed instant value or null, when the value is not an instant.
     */
    public static Instant toInstant(Object value, ZoneId timeZoneId) {
        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            try {
                value = LocalDate.parse(value.toString());
            }
            catch (DateTimeParseException ex1) {
                try {
                    value = LocalDateTime.parse(value.toString());
                }
                catch (DateTimeParseException ex2) {
                    try {
                        value = Instant.parse(value.toString());
                    }
                    catch (DateTimeParseException ex3) {
                        // ignore
                    }
                }
            }
        }

        Instant instantValue =
                (value instanceof Instant) ? (Instant)value :
                        ((value instanceof LocalDateTime) ? ((LocalDateTime)value).atZone(timeZoneId).toInstant() :
                        ((value instanceof LocalDate) ? ((LocalDate)value).atStartOfDay().atZone(timeZoneId).toInstant() : null));

        return instantValue;
    }

    /**
     * Converts a value to a java {@link LocalDate} data type. Also parses dates in ISO format.
     * @param value Value of a possible timestamp.
     * @param timeZoneId Default timezone to apply to local dates.
     * @return Parsed local date value or null, when the value is not a local date, or cannot be converted to such.
     */
    public static LocalDate toLocalDate(Object value, ZoneId timeZoneId) {
        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            try {
                value = LocalDate.parse(value.toString());
            }
            catch (DateTimeParseException ex1) {
                try {
                    value = LocalDateTime.parse(value.toString());
                }
                catch (DateTimeParseException ex2) {
                    try {
                        value = Instant.parse(value.toString());
                    }
                    catch (DateTimeParseException ex3) {
                        // ignore
                    }
                }
            }
        }

        LocalDate dateValue =
                (value instanceof LocalDate) ? (LocalDate)value :
                ((value instanceof LocalDateTime) ? ((LocalDateTime)value).toLocalDate() :
                ((value instanceof Instant) ? ((Instant)value).atZone(timeZoneId).toLocalDate() : null));

        return dateValue;
    }
}
