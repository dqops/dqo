package ai.dqo.data.readouts.factory;

import ai.dqo.data.normalization.CommonColumnNames;

/**
 * Constants with the column names in the sensor readout parquet tables.
 */
public class SensorReadoutsColumnNames {
    /**
     * Column name for a sensor executed at timestamp.
     */
    public static final String EXECUTED_AT_COLUMN_NAME = CommonColumnNames.EXECUTED_AT_COLUMN_NAME;

    /**
     * Column name for a check result id (primary key), it is a uuid of the check hash, time period and the data stream id. This value identifies a single row.
     */
    public static final String ID_COLUMN_NAME = CommonColumnNames.ID_COLUMN_NAME;

    /**
     * Column name that stores the actual value: actual_value.
     */
    public static final String ACTUAL_VALUE_COLUMN_NAME = "actual_value";

    /**
     * Column name that stores the expected value (expected_value). It is an optional column used when the sensor will also retrieve a comparison value (for accuracy checks).
     */
    public static final String EXPECTED_VALUE_COLUMN_NAME = "expected_value";

    /**
     * Column name that stores the time period of the sensor readout (timestamp): timestamp_period.
     */
    public static final String TIME_PERIOD_COLUMN_NAME = "time_period";

    /**
     * Column name for a time gradient.
     */
    public static final String TIME_GRADIENT_COLUMN_NAME = "time_gradient";

    /**
     * Column name prefix for the data stream columns: stream_level_.
     */
    public static final String DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX = CommonColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX;

    /**
     * Column name for a data stream hash, it is a hash of the data stream level names.
     */
    public static final String DATA_STREAM_HASH_COLUMN_NAME = CommonColumnNames.DATA_STREAM_HASH_COLUMN_NAME;

    /**
     * Column name for a data stream name, it is a concatenated name of the data stream created from [stream_level_1] / [stream_level_2] / ...
     */
    public static final String DATA_STREAM_NAME_COLUMN_NAME = CommonColumnNames.DATA_STREAM_NAME_COLUMN_NAME;

    /**
     * Column name for a connection hash.
     */
    public static final String CONNECTION_HASH_COLUMN_NAME = CommonColumnNames.CONNECTION_HASH_COLUMN_NAME;

    /**
     * Column name for a connection name.
     */
    public static final String CONNECTION_NAME_COLUMN_NAME = CommonColumnNames.CONNECTION_NAME_COLUMN_NAME;

    /**
     * Column name for a provider name.
     */
    public static final String PROVIDER_COLUMN_NAME = CommonColumnNames.PROVIDER_COLUMN_NAME;

    /**
     * Column name for a table hash.
     */
    public static final String TABLE_HASH_COLUMN_NAME = CommonColumnNames.TABLE_HASH_COLUMN_NAME;

    /**
     * Column name for a table schema.
     */
    public static final String SCHEMA_NAME_COLUMN_NAME = CommonColumnNames.SCHEMA_NAME_COLUMN_NAME;

    /**
     * Column name for a table name.
     */
    public static final String TABLE_NAME_COLUMN_NAME = CommonColumnNames.TABLE_NAME_COLUMN_NAME;

    /**
     * Column name for a table stage.
     */
    public static final String TABLE_STAGE_COLUMN_NAME = CommonColumnNames.TABLE_STAGE_COLUMN_NAME;

    /**
     * Column name for a column hash.
     */
    public static final String COLUMN_HASH_COLUMN_NAME = CommonColumnNames.COLUMN_HASH_COLUMN_NAME;

    /**
     * Column name for a column name.
     */
    public static final String COLUMN_NAME_COLUMN_NAME = CommonColumnNames.COLUMN_NAME_COLUMN_NAME;

    /**
     * Column name for a check hash.
     */
    public static final String CHECK_HASH_COLUMN_NAME = "check_hash";

    /**
     * Column name for a check name.
     */
    public static final String CHECK_NAME_COLUMN_NAME = "check_name";

    /**
     * Column name for a check display name.
     */
    public static final String CHECK_DISPLAY_NAME_COLUMN_NAME = "check_display_name";

    /**
     * Column name for a check type (adhoc, checkpoint, partitioned).
     */
    public static final String CHECK_TYPE_COLUMN_NAME = "check_type";

    /**
     * Column name for a check category.
     */
    public static final String CHECK_CATEGORY_COLUMN_NAME = "check_category";

    /**
     * Column name for a data quality dimension.
     */
    public static final String QUALITY_DIMENSION_COLUMN_NAME = "quality_dimension";

    /**
     * Column name for a sensor name.
     */
    public static final String SENSOR_NAME_COLUMN_NAME = CommonColumnNames.SENSOR_NAME_COLUMN_NAME;

    /**
     * Column name for a time series id (uuid). Identifies a single time series. A time series is a combination of the check_hash and data_stream_hash.
     */
    public static final String TIME_SERIES_ID_COLUMN_NAME = CommonColumnNames.TIME_SERIES_ID_COLUMN_NAME;

    /**
     * Column name for a sensor duration in milliseconds.
     */
    public static final String DURATION_MS_COLUMN_NAME = CommonColumnNames.DURATION_MS_COLUMN_NAME;
}
