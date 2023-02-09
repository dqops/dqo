package ai.dqo.data.errors.factory;

import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;

/**
 * Constants with the column names in the "errors" parquet tables.
 */
public class ErrorsColumnNames extends SensorReadoutsColumnNames {
    /**
     * Column name that stores the sensor readout ID.
     */
    public static final String READOUT_ID_COLUMN_NAME = "readout_id";

    /**
     * Column name that stores the error message.
     */
    public static final String ERROR_MESSAGE_COLUMN_NAME = "error_message";

    /**
     * Column name that stores the error source, which is the component that raised an error (sensor or rule).
     */
    public static final String ERROR_SOURCE_COLUMN_NAME = "error_source";

    /**
     * Column name that stores the error timestamp using the local timestamp.
     */
    public static final String ERROR_TIMESTAMP_COLUMN_NAME = "error_timestamp";

    /**
     * List of column names that should be loaded from the parquet files when the recent error overview is needed.
     */
    public static final String[] COLUMN_NAMES_FOR_ERRORS_OVERVIEW = new String[] {
            COLUMN_NAME_COLUMN_NAME,
            CHECK_TYPE_COLUMN_NAME,
            TIME_GRADIENT_COLUMN_NAME,
            CHECK_HASH_COLUMN_NAME,
            CHECK_CATEGORY_COLUMN_NAME,
            CHECK_NAME_COLUMN_NAME,
            DATA_STREAM_NAME_COLUMN_NAME,
            ACTUAL_VALUE_COLUMN_NAME,
            TIME_PERIOD_COLUMN_NAME,
            EXECUTED_AT_COLUMN_NAME
    };

    /**
     * List of column names that should be loaded from the parquet files when the recent error detailed view is needed.
     */
    public static final String[] COLUMN_NAMES_FOR_ERRORS_DETAILED = new String[] {
            ACTUAL_VALUE_COLUMN_NAME,
            EXPECTED_VALUE_COLUMN_NAME,

            CHECK_CATEGORY_COLUMN_NAME,
            CHECK_DISPLAY_NAME_COLUMN_NAME,
            CHECK_HASH_COLUMN_NAME,
            CHECK_NAME_COLUMN_NAME,
            CHECK_TYPE_COLUMN_NAME,

            COLUMN_NAME_COLUMN_NAME,
            DATA_STREAM_NAME_COLUMN_NAME,

            DURATION_MS_COLUMN_NAME,
            EXECUTED_AT_COLUMN_NAME,
            TIME_GRADIENT_COLUMN_NAME,
            TIME_PERIOD_COLUMN_NAME,

            PROVIDER_COLUMN_NAME,
            QUALITY_DIMENSION_COLUMN_NAME,
            SENSOR_NAME_COLUMN_NAME,

            READOUT_ID_COLUMN_NAME,
            ERROR_MESSAGE_COLUMN_NAME,
            ERROR_SOURCE_COLUMN_NAME,
            ERROR_TIMESTAMP_COLUMN_NAME
    };
}
