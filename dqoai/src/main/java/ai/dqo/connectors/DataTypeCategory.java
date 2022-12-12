package ai.dqo.connectors;

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
     * String column that accepts short values, typically: string, varchar, nvarchar, etc.
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
}
