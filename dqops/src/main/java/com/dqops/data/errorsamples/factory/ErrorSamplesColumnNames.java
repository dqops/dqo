/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.data.errorsamples.factory;

import com.dqops.data.normalization.CommonColumnNames;

/**
 * The error samples table that stores sample column values that failed data quality checks that operate on rows (mostly Validity and Consistency checks).
 * The error samples are stored in the errors table is located in the *$DQO_USER_HOME/.data/error_samples* folder that contains uncompressed parquet files.
 * The table is partitioned using a Hive compatible partitioning folder structure. When the *$DQO_USER_HOME* is not configured, it is the folder where DQOps was started (the DQOps user's home folder).
 *
 * The folder partitioning structure for this table is:
 * *c=[connection_name]/t=[schema_name.table_name]/m=[first_day_of_month]/*, for example: *c=myconnection/t=public.analyzedtable/m=2023-01-01/*.
 * The date used for monthly partitioning is calculated from the *executed_at* column value.
 */
public class ErrorSamplesColumnNames {
    /**
     * The check result id (primary key), it is a uuid of the check hash, collected at, sample index and the data grouping id. This value identifies a single row.
     */
    public static final String ID_COLUMN_NAME = CommonColumnNames.ID_COLUMN_NAME;

    /**
     * Column for the time when the error samples were captured. All error samples results started as part of the same error sampling session will share the same time. The parquet files are time partitioned by this column.
     */
    public static final String COLLECTED_AT_COLUMN_NAME = "collected_at";

    /**
     * String column that says if the result is for a whole table (the "table" value) or for each data group separately (the "data_group" value).
     */
    public static final String SCOPE_COLUMN_NAME = "scope";

    /**
     * Column prefix for the data group dimension level columns: grouping_level_.
     */
    public static final String DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX = CommonColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX;

    /**
     * The data grouping hash, it is a hash of the data grouping level values.
     */
    public static final String DATA_GROUP_HASH_COLUMN_NAME = CommonColumnNames.DATA_GROUP_HASH_COLUMN_NAME;

    /**
     * The data grouping name, it is a concatenated name of the data grouping dimension values, created from [grouping_level_1] / [grouping_level_2] / ...
     */
    public static final String DATA_GROUP_NAME_COLUMN_NAME = CommonColumnNames.DATA_GROUP_NAME_COLUMN_NAME;

    /**
     * The data grouping configuration name, it is a name of the named data grouping configuration that was used to run the data quality check.
     */
    public static final String DATA_GROUPING_CONFIGURATION_COLUMN_NAME = CommonColumnNames.DATA_GROUPING_CONFIGURATION_COLUMN_NAME;

    /**
     * A hash calculated from the connection name (the data source name).
     */
    public static final String CONNECTION_HASH_COLUMN_NAME = CommonColumnNames.CONNECTION_HASH_COLUMN_NAME;

    /**
     * The connection name (the data source name).
     */
    public static final String CONNECTION_NAME_COLUMN_NAME = CommonColumnNames.CONNECTION_NAME_COLUMN_NAME;

    /**
     * The provider name, which is the type of the data source.
     */
    public static final String PROVIDER_COLUMN_NAME = CommonColumnNames.PROVIDER_COLUMN_NAME;

    /**
     * The table name hash.
     */
    public static final String TABLE_HASH_COLUMN_NAME = CommonColumnNames.TABLE_HASH_COLUMN_NAME;

    /**
     * The database schema name.
     */
    public static final String SCHEMA_NAME_COLUMN_NAME = CommonColumnNames.SCHEMA_NAME_COLUMN_NAME;

    /**
     * The monitored table name.
     */
    public static final String TABLE_NAME_COLUMN_NAME = CommonColumnNames.TABLE_NAME_COLUMN_NAME;

    /**
     * The stage name of the table. This is a free-form text configured at the table level that can identify  the layers of the data warehouse or a data lake, for example: "landing", "staging", "cleansing", etc.
     */
    public static final String TABLE_STAGE_COLUMN_NAME = CommonColumnNames.TABLE_STAGE_COLUMN_NAME;

    /**
     * The table priority value copied from the table's definition. The table priority can be used to sort tables according to their importance.
     */
    public static final String TABLE_PRIORITY_COLUMN_NAME = CommonColumnNames.TABLE_PRIORITY_COLUMN_NAME;

    /**
     * The hash of a column.
     */
    public static final String COLUMN_HASH_COLUMN_NAME = CommonColumnNames.COLUMN_HASH_COLUMN_NAME;

    /**
     * The column name for which the results are stored.
     */
    public static final String COLUMN_NAME_COLUMN_NAME = CommonColumnNames.COLUMN_NAME_COLUMN_NAME;

    /**
     * The hash of a data quality check.
     */
    public static final String CHECK_HASH_COLUMN_NAME = "check_hash";

    /**
     * The data quality check name.
     */
    public static final String CHECK_NAME_COLUMN_NAME = "check_name";

    /**
     * The user configured display name for a data quality check, used when the user wants to use custom, user-friendly data quality check names.
     */
    public static final String CHECK_DISPLAY_NAME_COLUMN_NAME = "check_display_name";

    /**
     * The data quality check type (profiling, monitoring, partitioned).
     */
    public static final String CHECK_TYPE_COLUMN_NAME = "check_type";

    /**
     * The time gradient (daily, monthly) for monitoring checks (checkpoints) and partition checks. It is a "milliseconds" for profiling checks. When the time gradient is daily or monthly, the time_period is truncated at the beginning of the time gradient.
     */
    public static final String TIME_GRADIENT_COLUMN_NAME = "time_gradient";

    /**
     * The data quality check category name.
     */
    public static final String CHECK_CATEGORY_COLUMN_NAME = "check_category";

    /**
     * The name of a table comparison configuration used for a data comparison (accuracy) check.
     */
    public static final String TABLE_COMPARISON_NAME_COLUMN_NAME = "table_comparison";

    /**
     * The data quality dimension name. The popular dimensions are: Timeliness, Completeness, Consistency, Validity, Reasonableness, Uniqueness.
     */
    public static final String QUALITY_DIMENSION_COLUMN_NAME = "quality_dimension";

    /**
     * The data quality sensor name.
     */
    public static final String SENSOR_NAME_COLUMN_NAME = CommonColumnNames.SENSOR_NAME_COLUMN_NAME;

    /**
     * The time series id (uuid). Identifies a single time series. A time series is a combination of the check_hash and data_group_hash.
     */
    public static final String TIME_SERIES_ID_COLUMN_NAME = CommonColumnNames.TIME_SERIES_ID_COLUMN_NAME;

    /**
     * The sensor (query) execution duration in milliseconds.
     */
    public static final String DURATION_MS_COLUMN_NAME = CommonColumnNames.DURATION_MS_COLUMN_NAME;

    /**
     * The UTC timestamp, when the data sensor was executed.
     */
    public static final String EXECUTED_AT_COLUMN_NAME = CommonColumnNames.EXECUTED_AT_COLUMN_NAME;

    /**
     * The sample's result data type.
     */
    public static final String RESULT_TYPE_COLUMN_NAME = "result_type";

    /**
     * The sample value when it is a string value.
     */
    public static final String RESULT_STRING_COLUMN_NAME = "result_string";

    /**
     * The sample value when it is an integer value. It is a long (64 bit) value where we store all short, integer, long values.
     */
    public static final String RESULT_INTEGER_COLUMN_NAME = "result_integer";

    /**
     * The sample value when it is a numeric value with. It is a double value where we store all double, float, numeric and decimal values.
     */
    public static final String RESULT_FLOAT_COLUMN_NAME = "result_float";

    /**
     * The sample value when it is a boolean value.
     */
    public static final String RESULT_BOOLEAN_COLUMN_NAME = "result_boolean";

    /**
     * The sample value when it is a local date value.
     */
    public static final String RESULT_DATE_COLUMN_NAME = "result_date";

    /**
     * The sample value when it is a local date time value.
     */
    public static final String RESULT_DATE_TIME_COLUMN_NAME = "result_date_time";

    /**
     * The sample value when it is an absolute (UTC timezone) instant.
     */
    public static final String RESULT_INSTANT_COLUMN_NAME = "result_instant";

    /**
     * The sample value when it is time value.
     */
    public static final String RESULT_TIME_COLUMN_NAME = "result_time";

    /**
     * The 1-based index of the collected sample.
     */
    public static final String SAMPLE_INDEX_COLUMN_NAME = "sample_index";

    /**
     * The sample filtering formula that was used in the where filter.
     */
    public static final String SAMPLE_FILTER_COLUMN_NAME = "sample_filter";

    /**
     * Prefix added to the first 5 columns that identify the row. DQOps uses columns that are identifiers, or are part of a unique key to identify collected samples.
     * The column names from the analyzed table are not stored and must be matched to the list of columns in the monitored table, according to their order in DQOps metadata.
     */
    public static final String ROW_ID_COLUMN_NAME_PREFIX = "row_id_";

    /**
     * The timestamp when the row was created at.
     */
    public static final String CREATED_AT_COLUMN_NAME = CommonColumnNames.CREATED_AT_COLUMN_NAME;

    /**
     * The timestamp when the row was updated at.
     */
    public static final String UPDATED_AT_COLUMN_NAME = CommonColumnNames.UPDATED_AT_COLUMN_NAME;

    /**
     * The login of the user that created the row.
     */
    public static final String CREATED_BY_COLUMN_NAME = CommonColumnNames.CREATED_BY_COLUMN_NAME;

    /**
     * The login of the user that updated the row.
     */
    public static final String UPDATED_BY_COLUMN_NAME = CommonColumnNames.UPDATED_BY_COLUMN_NAME;

    /**
     * List of column names that should be loaded from the parquet files when we will be only reading, not updating.
     */
    public static final String[] COLUMN_NAMES_FOR_READ_ONLY_ACCESS = new String[] {
            ID_COLUMN_NAME,
            COLLECTED_AT_COLUMN_NAME,

            RESULT_TYPE_COLUMN_NAME,
            RESULT_STRING_COLUMN_NAME,
            RESULT_INTEGER_COLUMN_NAME,
            RESULT_FLOAT_COLUMN_NAME,
            RESULT_BOOLEAN_COLUMN_NAME,
            RESULT_DATE_COLUMN_NAME,
            RESULT_DATE_TIME_COLUMN_NAME,
            RESULT_INSTANT_COLUMN_NAME,
            RESULT_TIME_COLUMN_NAME,
            SAMPLE_INDEX_COLUMN_NAME,

            CHECK_CATEGORY_COLUMN_NAME,
            CHECK_DISPLAY_NAME_COLUMN_NAME,
            CHECK_HASH_COLUMN_NAME,
            CHECK_NAME_COLUMN_NAME,
            CHECK_TYPE_COLUMN_NAME,
            TIME_GRADIENT_COLUMN_NAME,

            COLUMN_NAME_COLUMN_NAME,
            DATA_GROUP_NAME_COLUMN_NAME,
            TABLE_COMPARISON_NAME_COLUMN_NAME,

            DURATION_MS_COLUMN_NAME,
            EXECUTED_AT_COLUMN_NAME,

            PROVIDER_COLUMN_NAME,
            QUALITY_DIMENSION_COLUMN_NAME,
            SENSOR_NAME_COLUMN_NAME,

            ROW_ID_COLUMN_NAME_PREFIX + "1",
            ROW_ID_COLUMN_NAME_PREFIX + "2",
            ROW_ID_COLUMN_NAME_PREFIX + "3",
            ROW_ID_COLUMN_NAME_PREFIX + "4",
            ROW_ID_COLUMN_NAME_PREFIX + "5",
    };
}
