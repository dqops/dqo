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
package com.dqops.data.readouts.factory;

import com.dqops.data.normalization.CommonColumnNames;

/**
 * The data quality sensor readouts table that stores readouts (measures) captured by DQOps sensors, before the value are evaluated by the data quality rules.
 * The sensor readouts are stored in the sensor_readouts table is located in the *$DQO_USER_HOME/.data/sensor_readouts* folder that contains uncompressed parquet files.
 * The table is partitioned using a Hive compatible partitioning folder structure. When the *$DQO_USER_HOME* is not configured, it is the folder where DQOps was started (the DQOps user's home folder).
 *
 * The folder partitioning structure for this table is:
 * *c=[connection_name]/t=[schema_name.table_name]/m=[first_day_of_month]/*, for example: *c=myconnection/t=public.testedtable/m=2023-01-01/*.
 */
public class SensorReadoutsColumnNames {
    /**
     * The check result id (primary key), it is a uuid of the check hash, time period and the data stream id. This value identifies a single row.
     */
    public static final String ID_COLUMN_NAME = CommonColumnNames.ID_COLUMN_NAME;

    /**
     * The actual sensor value that was captured.
     */
    public static final String ACTUAL_VALUE_COLUMN_NAME = "actual_value";

    /**
     * The expected value (expected_value). It is an optional column used when the sensor will also retrieve a comparison value (for accuracy checks).
     */
    public static final String EXPECTED_VALUE_COLUMN_NAME = "expected_value";

    /**
     * The time period of the sensor readout (timestamp), using a local timezone from the data source.
     */
    public static final String TIME_PERIOD_COLUMN_NAME = "time_period";

    /**
     * The time period of the sensor readout (timestamp) as a UTC timestamp.
     */
    public static final String TIME_PERIOD_UTC_COLUMN_NAME = "time_period_utc";

    /**
     * The time gradient (daily, monthly) for monitoring checks (checkpoints) and partition checks. It is a "milliseconds" for profiling checks. When the time gradient is daily or monthly, the time_period is truncated at the beginning of the time gradient.
     */
    public static final String TIME_GRADIENT_COLUMN_NAME = "time_gradient";

    /**
     * Column name prefix for the data grouping dimension level columns: grouping_level_.
     */
    public static final String DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX = CommonColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX;

    /**
     * The data group hash, it is a hash of the data group levels' values.
     */
    public static final String DATA_GROUP_HASH_COLUMN_NAME = CommonColumnNames.DATA_GROUP_HASH_COLUMN_NAME;

    /**
     * The data group name, it is a concatenated name of the data group dimension values, created from [grouping_level_1] / [grouping_level_2] / ...
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
     * The table name pattern, in case that a data quality check targets multiple tables.
     */
    public static final String TABLE_NAME_PATTERN_COLUMN_NAME = CommonColumnNames.TABLE_NAME_PATTERN_COLUMN_NAME;

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
     * The column for which the results are stored.
     */
    public static final String COLUMN_NAME_COLUMN_NAME = CommonColumnNames.COLUMN_NAME_COLUMN_NAME;

    /**
     * The column pattern, in case that a data quality check targets multiple columns.
     */
    public static final String COLUMN_NAME_PATTERN_COLUMN_NAME = CommonColumnNames.COLUMN_NAME_PATTERN_COLUMN_NAME;

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
     * List of column names that should be loaded from the parquet files when the recent readouts detailed view is needed.
     */
    public static final String[] COLUMN_NAMES_FOR_READOUTS_DETAILED = new String[] {
            ID_COLUMN_NAME,

            ACTUAL_VALUE_COLUMN_NAME,
            EXPECTED_VALUE_COLUMN_NAME,
            CHECK_CATEGORY_COLUMN_NAME,
            CHECK_DISPLAY_NAME_COLUMN_NAME,
            CHECK_HASH_COLUMN_NAME,
            CHECK_NAME_COLUMN_NAME,
            CHECK_TYPE_COLUMN_NAME,

            COLUMN_NAME_COLUMN_NAME,
            DATA_GROUP_NAME_COLUMN_NAME,
            TABLE_COMPARISON_NAME_COLUMN_NAME,

            DURATION_MS_COLUMN_NAME,
            EXECUTED_AT_COLUMN_NAME,
            TIME_GRADIENT_COLUMN_NAME,
            TIME_PERIOD_COLUMN_NAME,
            TIME_PERIOD_UTC_COLUMN_NAME,

            PROVIDER_COLUMN_NAME,
            QUALITY_DIMENSION_COLUMN_NAME,
            SENSOR_NAME_COLUMN_NAME
    };
}
