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
package com.dqops.data.errorsamples.factory;

import com.dqops.data.normalization.CommonColumnNames;

/**
 * The error samples table that stores sample column values that failed data quality checks that operate on rows (mostly Validity and Consistency checks).
 * The error samples are stored in the errors table is located in the *$DQO_USER_HOME/.data/error_samples* folder that contains uncompressed parquet files.
 * The table is partitioned using a Hive compatible partitioning folder structure. When the *$DQO_USER_HOME* is not configured, it is the folder where DQOps was started (the DQOps user's home folder).
 *
 * The folder partitioning structure for this table is:
 * *c=[connection_name]/t=[schema_name.table_name]/m=[first_day_of_month]/*, for example: *c=myconnection/t=public.analyzedtable/m=2023-01-01/*.
 */
public class ErrorSamplesColumnNames {
    /**
     * The check result id (primary key), it is a uuid of the check hash, time period and the data stream id. This value identifies a single row.
     */
    public static final String ID_COLUMN_NAME = CommonColumnNames.ID_COLUMN_NAME;

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
     * The stage name of the table. It is a free-form text configured on the table level that could identify the layers of the data warehouse or a data lake, for example: "landing", "staging", "cleansing", etc.
     */
    public static final String TABLE_STAGE_COLUMN_NAME = CommonColumnNames.TABLE_STAGE_COLUMN_NAME;

    /**
     * The table priority value copied from the table's definition. The table priority could be used for sorting tables by their importance.
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
     * The data quality check category name.
     */
    public static final String CHECK_CATEGORY_COLUMN_NAME = "check_category";

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
}
