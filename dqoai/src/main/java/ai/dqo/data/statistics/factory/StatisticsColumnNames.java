/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.data.statistics.factory;

import ai.dqo.data.normalization.CommonColumnNames;

/**
 * Constants with the column names of the "statistics" table that contains the results of table and column statistics (basic profiling).
 */
public final class StatisticsColumnNames {
    /**
     * Column name for a statistics result id (primary key), it is a uuid of the statistics collector hash, executed at and the data stream id. This value identifies a single row.
     */
    public static final String ID_COLUMN_NAME = CommonColumnNames.ID_COLUMN_NAME;

    /**
     * Column name for the time when the statistics were captured. All statistics results started as part of the same statistics collection session will share the same time. The parquet files are time partitioned by this column.
     */
    public static final String COLLECTED_AT_COLUMN_NAME = "collected_at";

    /**
     * Column name for a statistics collector status ('success' or 'error').
     */
    public static final String STATUS_COLUMN_NAME = "status";

    /**
     * Column name for a statistics collector result data type.
     */
    public static final String RESULT_TYPE_COLUMN_NAME = "result_type";

    /**
     * Column name for a statistics collector result when it is a string value.
     */
    public static final String RESULT_STRING_COLUMN_NAME = "result_string";

    /**
     * Column name for a statistics collector result when it is an integer value. It is a long (64 bit) value where we store all short, integer, long values.
     */
    public static final String RESULT_INTEGER_COLUMN_NAME = "result_integer";

    /**
     * Column name for a statistics collector result when it is a numeric value with. It is a double value where we store all double, float, numeric and decimal values.
     */
    public static final String RESULT_FLOAT_COLUMN_NAME = "result_float";

    /**
     * Column name for a statistics collector result when it is a boolean value.
     */
    public static final String RESULT_BOOLEAN_COLUMN_NAME = "result_boolean";

    /**
     * Column name for a statistics collector result when it is a local date value.
     */
    public static final String RESULT_DATE_COLUMN_NAME = "result_date";

    /**
     * Column name for a statistics collector result when it is a local date time value.
     */
    public static final String RESULT_DATE_TIME_COLUMN_NAME = "result_date_time";

    /**
     * Column name for a statistics collector result when it is an absolute (UTC timezone) instant.
     */
    public static final String RESULT_INSTANT_COLUMN_NAME = "result_instant";

    /**
     * Column name for a statistics collector result when it is time value.
     */
    public static final String RESULT_TIME_COLUMN_NAME = "result_time";

    /**
     * Column name for a statistics collector executed at timestamp.
     */
    public static final String EXECUTED_AT_COLUMN_NAME = CommonColumnNames.EXECUTED_AT_COLUMN_NAME;

    /**
     * Column name for string column that says if the result is for a whole table ("table") or for each data stream separately ("data_stream")
     */
    public static final String SCOPE_COLUMN_NAME = "scope";

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
     * Column name for a statistics collector hash.
     */
    public static final String COLLECTOR_HASH_COLUMN_NAME = "collector_hash";

    /**
     * Column name for a statistics collector name.
     */
    public static final String COLLECTOR_NAME_COLUMN_NAME = "collector_name";

    /**
     * Column name for a statistics collector target (table, column).
     */
    public static final String COLLECTOR_TARGET_COLUMN_NAME = "collector_target";

    /**
     * Column name for a statistics collector category.
     */
    public static final String COLLECTOR_CATEGORY_COLUMN_NAME = "collector_category";

    /**
     * Column name for a sensor name.
     */
    public static final String SENSOR_NAME_COLUMN_NAME = CommonColumnNames.SENSOR_NAME_COLUMN_NAME;

    /**
     * Column name for a time series id (uuid). Identifies a single time series. A time series is a combination of the profiler_hash and data_stream_hash.
     */
    public static final String TIME_SERIES_ID_COLUMN_NAME = CommonColumnNames.TIME_SERIES_ID_COLUMN_NAME;

    /**
     * Column name for a sensor duration in milliseconds.
     */
    public static final String DURATION_MS_COLUMN_NAME = CommonColumnNames.DURATION_MS_COLUMN_NAME;

    /**
     * Column name for an optional error message when the status is 'error'.
     */
    public static final String ERROR_MESSAGE_COLUMN_NAME = CommonColumnNames.ERROR_MESSAGE_COLUMN_NAME;
}
