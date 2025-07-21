/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
