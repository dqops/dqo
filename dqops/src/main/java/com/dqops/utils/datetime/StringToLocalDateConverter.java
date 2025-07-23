/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.datetime;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StringToLocalDateConverter {

    /**
     * Converts a date string to a local date.
     * @param value Date string in format "yyyy.MM.dd".
     * @return The given value as a local date, or null if parsing failed.
     */
    public static LocalDate convertFromYearMonthDay(String value) {
        try {
            return LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Converts a date string to a local date.
     * @param value Date string in format "yyyy.MM".
     * @param useMonthEndDay Should the resulting local date be from the end of the month instead of the beginning.
     * @return The given value as a local date on the first (or alternatively last) day of the month, or null if parsing failed.
     */
    public static LocalDate convertFromYearMonth(String value, boolean useMonthEndDay) {
        try {
            YearMonth parsedValue = YearMonth.parse(value, DateTimeFormatter.ofPattern("yyyy.MM"));
            return useMonthEndDay ? parsedValue.atEndOfMonth() : parsedValue.atDay(1);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
