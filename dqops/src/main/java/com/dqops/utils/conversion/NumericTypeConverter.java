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

import org.apache.parquet.Strings;

/**
 * Static class to cast or convert objects to a desired numeric type.
 */
public class NumericTypeConverter {
    /**
     * Converts a given object to an integer value.
     * @param obj Object that could be of any numeric type or a string.
     * @return Integer value.
     */
    public static Integer toInt(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof Integer) {
            return (Integer) obj;
        }

        if (obj instanceof Long) {
            return ((Long) obj).intValue();
        }

        if (obj instanceof Short) {
            return ((Short) obj).intValue();
        }

        if (obj instanceof Byte) {
            return ((Byte) obj).intValue();
        }

        if (obj instanceof Boolean) {
            return ((Boolean) obj) ? 1 : 0;
        }

        if (obj instanceof Double) {
            return ((Double) obj).intValue();
        }

        if (obj instanceof Float) {
            return ((Float) obj).intValue();
        }

        String asString = obj.toString();
        return Integer.valueOf(asString);
    }

    /**
     * Converts a given object to a long (integer 64 bit) value.
     * @param obj Object that could be of any numeric type or a string.
     * @return Long value.
     */
    public static Long toLong(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof Long) {
            return (Long) obj;
        }

        if (obj instanceof Integer) {
            return ((Integer) obj).longValue();
        }

        if (obj instanceof Short) {
            return ((Short) obj).longValue();
        }

        if (obj instanceof Byte) {
            return ((Byte) obj).longValue();
        }

        if (obj instanceof Boolean) {
            return ((Boolean) obj) ? 1L : 0L;
        }

        if (obj instanceof Double) {
            return ((Double) obj).longValue();
        }

        if (obj instanceof Float) {
            return ((Float) obj).longValue();
        }

        String asString = obj.toString();
        return Long.valueOf(asString);
    }

    /**
     * Converts a given object to a double value.
     * @param obj Object that could be of any numeric type or a string.
     * @return Double value.
     */
    public static Double toDouble(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof Double) {
            return (Double) obj;
        }

        if (obj instanceof Integer) {
            return ((Integer) obj).doubleValue();
        }

        if (obj instanceof Long) {
            return ((Long) obj).doubleValue();
        }

        if (obj instanceof Short) {
            return ((Short) obj).doubleValue();
        }

        if (obj instanceof Byte) {
            return ((Byte) obj).doubleValue();
        }

        if (obj instanceof Boolean) {
            return ((Boolean) obj) ? 1.0 : 0.0;
        }

        if (obj instanceof Float) {
            return ((Float) obj).doubleValue();
        }

        String asString = obj.toString();
        return Double.valueOf(asString);
    }

    /**
     * Tries to parse a string value to an integer (int 32 bit) value.
     * Captures exceptions and returns null when the value is not parsable to an integer.
     * @param value String value to parse.
     * @return Parsed integer value when it is parsable or null when the value is not a valid integer value.
     */
    public static Integer tryParseInteger(String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }

        try {
            int parsedLong = Integer.parseInt(value);
            return parsedLong;
        }
        catch (NumberFormatException ex) {
            return null;
        }
    }

    /**
     * Tries to parse a string value to a long (int 64 bit) value.
     * Captures exceptions and returns null when the value is not parsable to a long integer.
     * @param value String value to parse.
     * @return Parsed long value when it is parsable or null when the value is not a valid long value.
     */
    public static Long tryParseLong(String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }

        try {
            long parsedLong = Long.parseLong(value);
            return parsedLong;
        }
        catch (NumberFormatException ex) {
            return null;
        }
    }
}
