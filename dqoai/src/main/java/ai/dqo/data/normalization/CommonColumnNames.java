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
package ai.dqo.data.normalization;

/**
 * Constants with the column names of common columns that are present across different parquet tables, have the same meaning
 * and should have the same name.
 */
public final class CommonColumnNames {
    /**
     * Column name for a primary key, it is a uuid of the check hash, time period and the data stream id. This value identifies a single row.
     */
    public static final String ID_COLUMN_NAME = "id";

    /**
     * Column name for a sensor executed at timestamp.
     */
    public static final String EXECUTED_AT_COLUMN_NAME = "executed_at";

    /**
     * Column name prefix for the data stream columns: stream_level_.
     */
    public static final String DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX = "stream_level_";

    /**
     * Column name for a data stream hash, it is a hash of the data stream level names.
     */
    public static final String DATA_STREAM_HASH_COLUMN_NAME = "data_stream_hash";

    /**
     * Column name for a data stream name, it is a concatenated name of the data stream created from [stream_level_1] / [stream_level_2] / ...
     */
    public static final String DATA_STREAM_NAME_COLUMN_NAME = "data_stream_name";

    /**
     * Column name for a connection hash.
     */
    public static final String CONNECTION_HASH_COLUMN_NAME = "connection_hash";

    /**
     * Column name for a connection name.
     */
    public static final String CONNECTION_NAME_COLUMN_NAME = "connection_name";

    /**
     * Column name for a provider name.
     */
    public static final String PROVIDER_COLUMN_NAME = "provider";

    /**
     * Column name for a table hash.
     */
    public static final String TABLE_HASH_COLUMN_NAME = "table_hash";

    /**
     * Column name for a table schema.
     */
    public static final String SCHEMA_NAME_COLUMN_NAME = "schema_name";

    /**
     * Column name for a table name.
     */
    public static final String TABLE_NAME_COLUMN_NAME = "table_name";

    /**
     * Column name for a table name pattern.
     */
    public static final String TABLE_NAME_PATTERN_COLUMN_NAME = "table_name_pattern";

    /**
     * Column name for a table stage.
     */
    public static final String TABLE_STAGE_COLUMN_NAME = "table_stage";

    /**
     * Column name for a column hash.
     */
    public static final String COLUMN_HASH_COLUMN_NAME = "column_hash";

    /**
     * Column name for a column name.
     */
    public static final String COLUMN_NAME_COLUMN_NAME = "column_name";

    /**
     * Column name for a column name pattern.
     */
    public static final String COLUMN_NAME_PATTERN_COLUMN_NAME = "column_name_pattern";

    /**
     * Column name for a sensor name.
     */
    public static final String SENSOR_NAME_COLUMN_NAME = "sensor_name";

    /**
     * Column name for a time series id (uuid). Identifies a single time series. A time series is a combination of the check_hash and data_stream_hash.
     */
    public static final String TIME_SERIES_ID_COLUMN_NAME = "time_series_id";

    /**
     * Column name for a sensor duration in milliseconds.
     */
    public static final String DURATION_MS_COLUMN_NAME = "duration_ms";

    /**
     * Column name for an error message.
     */
    public static final String ERROR_MESSAGE_COLUMN_NAME = "error_message";
}
