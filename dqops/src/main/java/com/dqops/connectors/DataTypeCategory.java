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
package com.dqops.connectors;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of common data type categories of data types. The providers will use this information to answer
 * which of their native data types matches a category. Some sensors (and profilers) cannot operate on some data types.
 */
public enum DataTypeCategory {
    /**
     * Integer data types, such as: short, int, long, int16, int32, int64, etc.
     */
    @JsonProperty("numeric_integer")
    numeric_integer,

    /**
     * Fixed precision numbers, such as DECIMAL or NUMERIC.
     */
    @JsonProperty("numeric_decimal")
    numeric_decimal,

    /**
     * Variable precision float/double numbers.
     */
    @JsonProperty("numeric_float")
    numeric_float,

    /**
     * Instant time using UTC timezone (with no time zone).
     */
    @JsonProperty("datetime_instant")
    datetime_instant,

    /**
     * Local date time.
     */
    @JsonProperty("datetime_datetime")
    datetime_datetime,

    /**
     * Local date.
     */
    @JsonProperty("datetime_date")
    datetime_date,

    /**
     * Local time.
     */
    @JsonProperty("datetime_time")
    datetime_time,

    /**
     * Short (limited) string column that accepts short values, typically: string, varchar, nvarchar, etc.
     */
    @JsonProperty("string")
    string,

    /**
     * Long column (clob, text, etc).
     */
    @JsonProperty("text")
    text,

    /**
     * Json columns.
     */
    @JsonProperty("json")
    json,

    /**
     * Boolean data type.
     */
    @JsonProperty("bool")
    bool,

    /**
     * Bytes (binary)
     */
    @JsonProperty("binary")
    binary,

    /**
     * Array of something.
     */
    @JsonProperty("array")
    array,

    /**
     * Other column type (not detected).
     */
    @JsonProperty("other")
    other;


    /**
     * All data types without even checking the data type.
     */
    public static DataTypeCategory[] ANY = new DataTypeCategory[0];

    /**
     * All data types that could be detected (they are not "other"). Null checks support these data types.
     */
    public static DataTypeCategory[] ALL_KNOWN = new DataTypeCategory[] {
            numeric_integer,
            numeric_decimal,
            numeric_float,
            datetime_instant,
            datetime_datetime,
            datetime_date,
            datetime_time,
            string,
            text,
            json,
            bool,
            binary,
            array
    };

    /**
     * All data types for a single value, that are detected (not other, not arrays).
     */
    public static DataTypeCategory[] SINGLE_VALUE = new DataTypeCategory[] {
            numeric_integer,
            numeric_decimal,
            numeric_float,
            datetime_instant,
            datetime_datetime,
            datetime_date,
            datetime_time,
            string,
            text,
            json,
            bool,
            binary,
         };

    /**
     * All numeric data types.
     */
    public static DataTypeCategory[] NUMERIC = new DataTypeCategory[] {
            numeric_integer,
            numeric_decimal,
            numeric_float,
    };

    /**
     * All comparable data types that support comparison and sorting. Long text, binary and json types are not included.
     * Also array or other types are excluded.
     */
    public static DataTypeCategory[] COMPARABLE = new DataTypeCategory[] {
            numeric_integer,
            numeric_decimal,
            numeric_float,
            datetime_instant,
            datetime_datetime,
            datetime_date,
            datetime_time,
            string,
            bool,
    };

    /**
     * Data types that contain a date inside (date, datetime, timestamp).
     */
    public static DataTypeCategory[] CONTAINS_DATE = new DataTypeCategory[] {
            datetime_instant,
            datetime_datetime,
            datetime_date,
    };

    /**
     * Just a simple string type (not clob).
     */
    public static DataTypeCategory[] STRING = new DataTypeCategory[] {
            string
    };

    /**
     * Just a boolean type or an integer as a fallback.
     */
    public static DataTypeCategory[] BOOLEAN = new DataTypeCategory[] {
            bool,
            numeric_integer
    };

    /**
     * Checks if this data type is one of numeric data types.
     * @param dataTypeCategory Data type category to test.
     * @return True when it is a numeric data type.
     */
    public static boolean isNumericType(DataTypeCategory dataTypeCategory) {
        return dataTypeCategory == numeric_integer || dataTypeCategory == numeric_decimal || dataTypeCategory == numeric_float;
    }

    /**
     * Checks if this data type is one of date(time) types that has a date. For {@link DataTypeCategory#datetime_time} this method returns false, because it has no tme.
     * @param dataTypeCategory Data type category to test.
     * @return True when it is a date(time) data type with a date part.
     */
    public static boolean hasDate(DataTypeCategory dataTypeCategory) {
        return dataTypeCategory == datetime_date || dataTypeCategory == datetime_datetime || dataTypeCategory == datetime_instant;
    }

    /**
     * Verifies if the <code>dataTypeCategory</code> is on the list of accepted categories.
     * @param dataTypeCategory Tested data type category.
     * @param dataTypeCategories Array of accepted data types. If the array is null, we assume that all types match and we return true.
     * @return True when the <code>dataTypeCategory</code> is on the list.
     */
    public static boolean isDataTypeInList(DataTypeCategory dataTypeCategory, DataTypeCategory[] dataTypeCategories) {
        if (dataTypeCategories == null || dataTypeCategories.length == 0) {
            return true; // no data type category means that all categories are valid
        }

        if (dataTypeCategory == null) {
            return false; // data type not detected, but a list of required type categories is given
        }

        for (DataTypeCategory expectedCategory : dataTypeCategories) {
            if (expectedCategory == dataTypeCategory) {
                return true;
            }
        }

        return false;
    }
}
