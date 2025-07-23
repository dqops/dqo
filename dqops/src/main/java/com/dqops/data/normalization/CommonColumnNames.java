/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.normalization;

/**
 * Constants with the column names of common columns that are present across different parquet tables, have the same meaning
 * and should have the same name.
 */
public final class CommonColumnNames {
    /**
     * The row's primary key, it is a uuid of the connection_name, table_name, column_name, check_hash, time_period, and the data_group_hash. This value identifies a single row.
     */
    public static final String ID_COLUMN_NAME = "id";

    /**
     * The timestamp when the sensor was executed at (UTC).
     */
    public static final String EXECUTED_AT_COLUMN_NAME = "executed_at";

    /**
     * The prefix for the data grouping columns: grouping_level_.
     */
    public static final String DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX = "grouping_level_";

    /**
     * The data group hash, it is a hash of the data grouping level names.
     */
    public static final String DATA_GROUP_HASH_COLUMN_NAME = "data_group_hash";

    /**
     * The data group name, it is a concatenated name of the data grouping dimensions, created from [grouping_level_1] / [grouping_level_2] / ...
     */
    public static final String DATA_GROUP_NAME_COLUMN_NAME = "data_group_name";

    /**
     * The data grouping configuration name, it is a name of the named data grouping mapping configuration that was used to run the data quality check.
     */
    public static final String DATA_GROUPING_CONFIGURATION_COLUMN_NAME = "data_grouping_configuration";

    /**
     * Column for a connection hash.
     */
    public static final String CONNECTION_HASH_COLUMN_NAME = "connection_hash";

    /**
     * Column for a connection name.
     */
    public static final String CONNECTION_NAME_COLUMN_NAME = "connection_name";

    /**
     * Column for a provider name.
     */
    public static final String PROVIDER_COLUMN_NAME = "provider";

    /**
     * Column for a table hash.
     */
    public static final String TABLE_HASH_COLUMN_NAME = "table_hash";

    /**
     * Column for a table schema.
     */
    public static final String SCHEMA_NAME_COLUMN_NAME = "schema_name";

    /**
     * Column for a table name.
     */
    public static final String TABLE_NAME_COLUMN_NAME = "table_name";

    /**
     * Column for a table name pattern.
     */
    public static final String TABLE_NAME_PATTERN_COLUMN_NAME = "table_name_pattern";

    /**
     * Column for a table stage.
     */
    public static final String TABLE_STAGE_COLUMN_NAME = "table_stage";

    /**
     * Column for a table priority.
     */
    public static final String TABLE_PRIORITY_COLUMN_NAME = "table_priority";

    /**
     * Column for a column hash.
     */
    public static final String COLUMN_HASH_COLUMN_NAME = "column_hash";

    /**
     * Column for a column name.
     */
    public static final String COLUMN_NAME_COLUMN_NAME = "column_name";

    /**
     * Column for a column name pattern.
     */
    public static final String COLUMN_NAME_PATTERN_COLUMN_NAME = "column_name_pattern";

    /**
     * Column for a sensor name.
     */
    public static final String SENSOR_NAME_COLUMN_NAME = "sensor_name";

    /**
     * Column for a time series id (uuid). Identifies a single time series. A time series is a combination of the check_hash and data_group_hash.
     */
    public static final String TIME_SERIES_ID_COLUMN_NAME = "time_series_id";

    /**
     * Column for a sensor duration in milliseconds.
     */
    public static final String DURATION_MS_COLUMN_NAME = "duration_ms";

    /**
     * Column for an error message.
     */
    public static final String ERROR_MESSAGE_COLUMN_NAME = "error_message";

    /**
     * Timestamp when the row was created at.
     */
    public static final String CREATED_AT_COLUMN_NAME = "created_at";

    /**
     * Timestamp when the row was updated at.
     */
    public static final String UPDATED_AT_COLUMN_NAME = "updated_at";

    /**
     * User that created the row.
     */
    public static final String CREATED_BY_COLUMN_NAME = "created_by";

    /**
     * User that updated the row.
     */
    public static final String UPDATED_BY_COLUMN_NAME = "updated_by";
}
