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
}
