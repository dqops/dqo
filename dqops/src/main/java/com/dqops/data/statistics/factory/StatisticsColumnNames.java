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
package com.dqops.data.statistics.factory;

import com.dqops.data.normalization.CommonColumnNames;

/**
 * The basic profiling results (statistics) table that stores basic profiling statistical values.
 * The statistics are stored in the errors table is located in the $DQO_USER_HOME/.data/statistics folder that contains uncompressed parquet files.
 * The table is partitioned using a Hive compatible partitioning folder structure. When the $DQO_USER_HOME is not configured, it is the folder where DQOps was started (the DQOps user's home folder).
 *
 * The folder partitioning structure for this table is:
 * c=[connection_name]/t=[schema_name.table_name]/m=[first_day_of_month]/, for example: c=myconnection/t=public.analyzedtable/m=2023-01-01/.
 */
public final class StatisticsColumnNames {
    /**
     * Column for a statistics result id (primary key), it is a uuid of the statistics collector hash, executed at and the data stream id. This value identifies a single row.
     */
    public static final String ID_COLUMN_NAME = CommonColumnNames.ID_COLUMN_NAME;

    /**
     * Column for the time when the statistics were captured. All statistics results started as part of the same statistics collection session will share the same time. The parquet files are time partitioned by this column.
     */
    public static final String COLLECTED_AT_COLUMN_NAME = "collected_at";

    /**
     * Column for a statistics collector status ('success' or 'error').
     */
    public static final String STATUS_COLUMN_NAME = "status";

    /**
     * Column for a statistics collector result data type.
     */
    public static final String RESULT_TYPE_COLUMN_NAME = "result_type";

    /**
     * Column for a statistics collector result when it is a string value.
     */
    public static final String RESULT_STRING_COLUMN_NAME = "result_string";

    /**
     * Column for a statistics collector result when it is an integer value. It is a long (64 bit) value where we store all short, integer, long values.
     */
    public static final String RESULT_INTEGER_COLUMN_NAME = "result_integer";

    /**
     * Column for a statistics collector result when it is a numeric value with. It is a double value where we store all double, float, numeric and decimal values.
     */
    public static final String RESULT_FLOAT_COLUMN_NAME = "result_float";

    /**
     * Column for a statistics collector result when it is a boolean value.
     */
    public static final String RESULT_BOOLEAN_COLUMN_NAME = "result_boolean";

    /**
     * Column for a statistics collector result when it is a local date value.
     */
    public static final String RESULT_DATE_COLUMN_NAME = "result_date";

    /**
     * Column for a statistics collector result when it is a local date time value.
     */
    public static final String RESULT_DATE_TIME_COLUMN_NAME = "result_date_time";

    /**
     * Column for a statistics collector result when it is an absolute (UTC timezone) instant.
     */
    public static final String RESULT_INSTANT_COLUMN_NAME = "result_instant";

    /**
     * Column for a statistics collector result when it is time value.
     */
    public static final String RESULT_TIME_COLUMN_NAME = "result_time";

    /**
     * The index of the sample for statistics collector that collect data samples.
     */
    public static final String SAMPLE_INDEX_COLUMN_NAME = "sample_index";

    /**
     * The count of the samples for statistics collector that collect data samples.
     */
    public static final String SAMPLE_COUNT_COLUMN_NAME = "sample_count";

    /**
     * Column for a statistics collector executed at timestamp.
     */
    public static final String EXECUTED_AT_COLUMN_NAME = CommonColumnNames.EXECUTED_AT_COLUMN_NAME;

    /**
     * String column that says if the result is for a whole table ("table") or for each data stream separately ("data_stream")
     */
    public static final String SCOPE_COLUMN_NAME = "scope";

    /**
     * Column prefix for the data group dimension level columns: grouping_level_.
     */
    public static final String DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX = CommonColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX;

    /**
     * Column for a data group hash, it is a hash of the data group level values.
     */
    public static final String DATA_GROUP_HASH_COLUMN_NAME = CommonColumnNames.DATA_GROUP_HASH_COLUMN_NAME;

    /**
     * The data group name, it is a concatenated name of the data group dimensions, created from [grouping_level_1] / [grouping_level_2] / ...
     */
    public static final String DATA_GROUP_COLUMN_NAME = CommonColumnNames.DATA_GROUP_NAME_COLUMN_NAME;

    /**
     * The data grouping configuration name, it is a name of the named data grouping configuration that was used to run the data quality check.
     */
    public static final String DATA_GROUPING_CONFIGURATION_COLUMN_NAME = CommonColumnNames.DATA_GROUPING_CONFIGURATION_COLUMN_NAME;

    /**
     * Column for a connection hash.
     */
    public static final String CONNECTION_HASH_COLUMN_NAME = CommonColumnNames.CONNECTION_HASH_COLUMN_NAME;

    /**
     * Column for a connection name.
     */
    public static final String CONNECTION_NAME_COLUMN_NAME = CommonColumnNames.CONNECTION_NAME_COLUMN_NAME;

    /**
     * Column for a provider name.
     */
    public static final String PROVIDER_COLUMN_NAME = CommonColumnNames.PROVIDER_COLUMN_NAME;

    /**
     * Column for a table hash.
     */
    public static final String TABLE_HASH_COLUMN_NAME = CommonColumnNames.TABLE_HASH_COLUMN_NAME;

    /**
     * Column for a table schema.
     */
    public static final String SCHEMA_NAME_COLUMN_NAME = CommonColumnNames.SCHEMA_NAME_COLUMN_NAME;

    /**
     * Column for a table name.
     */
    public static final String TABLE_NAME_COLUMN_NAME = CommonColumnNames.TABLE_NAME_COLUMN_NAME;

    /**
     * Column for a table stage.
     */
    public static final String TABLE_STAGE_COLUMN_NAME = CommonColumnNames.TABLE_STAGE_COLUMN_NAME;

    /**
     * Column for a column hash.
     */
    public static final String COLUMN_HASH_COLUMN_NAME = CommonColumnNames.COLUMN_HASH_COLUMN_NAME;

    /**
     * Column for a column name.
     */
    public static final String COLUMN_NAME_COLUMN_NAME = CommonColumnNames.COLUMN_NAME_COLUMN_NAME;

    /**
     * Column for a statistics collector hash.
     */
    public static final String COLLECTOR_HASH_COLUMN_NAME = "collector_hash";

    /**
     * Column for a statistics collector name.
     */
    public static final String COLLECTOR_NAME_COLUMN_NAME = "collector_name";

    /**
     * Column for a statistics collector target (table, column).
     */
    public static final String COLLECTOR_TARGET_COLUMN_NAME = "collector_target";

    /**
     * Column for a statistics collector category.
     */
    public static final String COLLECTOR_CATEGORY_COLUMN_NAME = "collector_category";

    /**
     * Column for a sensor name.
     */
    public static final String SENSOR_NAME_COLUMN_NAME = CommonColumnNames.SENSOR_NAME_COLUMN_NAME;

    /**
     * Column for a time series id (uuid). Identifies a single time series. A time series is a combination of the profiler_hash and data_stream_hash.
     */
    public static final String TIME_SERIES_ID_COLUMN_NAME = CommonColumnNames.TIME_SERIES_ID_COLUMN_NAME;

    /**
     * Column for a sensor duration in milliseconds.
     */
    public static final String DURATION_MS_COLUMN_NAME = CommonColumnNames.DURATION_MS_COLUMN_NAME;

    /**
     * Column for an optional error message when the status is 'error'.
     */
    public static final String ERROR_MESSAGE_COLUMN_NAME = CommonColumnNames.ERROR_MESSAGE_COLUMN_NAME;

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
