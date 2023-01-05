/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.utils.datetime;

import java.time.LocalDate;
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
     * @return The given value as a local date on the first day of the month, or null if parsing failed.
     */
    public static LocalDate convertFromYearMonth(String value) {
        try {
            LocalDate result = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy.MM"));
            return result.withDayOfMonth(1);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Converts a date string to a local date.
     * @param value Date string in format "yyyy.MM.dd", or "yyyy.MM" (the day is then 01).
     * @return The given value as a local date.
     */
    public static LocalDate convert(String value) {
        LocalDate dateYearMonthDay = convertFromYearMonthDay(value);
        if (dateYearMonthDay != null) {
            return dateYearMonthDay;
        }

        return convertFromYearMonth(value);
    }
}
