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
package com.dqops.data.errors.factory;

import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;

/**
 * The data quality execution errors table that stores execution errors captured during the sensor execution or the rule evaluation.
 * The sensor execution errors are error messages received from the data source when the tested table does not exist or the sensor's SQL query is invalid.
 * The rule execution errors are exceptions raised during the Python rule evaluation.
 * The errors are stored in the errors table is located in the *$DQO_USER_HOME/.data/errors* folder that contains uncompressed parquet files.
 * The table is partitioned using a Hive compatible partitioning folder structure. When the *$DQO_USER_HOME* is not configured, it is the folder where DQOps was started (the DQOps user's home folder).
 *
 * The folder partitioning structure for this table is:
 * *c=[connection_name]/t=[schema_name.table_name]/m=[first_day_of_month]/*, for example: *c=myconnection/t=public.testedtable/m=2023-01-01/*.
 */
public class ErrorsColumnNames extends SensorReadoutsColumnNames {
    /**
     * Column that stores the sensor readout ID.
     */
    public static final String READOUT_ID_COLUMN_NAME = "readout_id";

    /**
     * Column that stores the error message.
     */
    public static final String ERROR_MESSAGE_COLUMN_NAME = "error_message";

    /**
     * Column that stores the error source, which is the component that raised an error (sensor or rule).
     */
    public static final String ERROR_SOURCE_COLUMN_NAME = "error_source";

    /**
     * Column that stores the error timestamp using the local timestamp.
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
            QUALITY_DIMENSION_COLUMN_NAME,
            CHECK_NAME_COLUMN_NAME,
            DATA_GROUP_NAME_COLUMN_NAME,
            ACTUAL_VALUE_COLUMN_NAME,
            TIME_PERIOD_COLUMN_NAME,
            TIME_PERIOD_UTC_COLUMN_NAME,
            EXECUTED_AT_COLUMN_NAME,
            TABLE_COMPARISON_NAME_COLUMN_NAME
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
            DATA_GROUP_NAME_COLUMN_NAME,
            TABLE_COMPARISON_NAME_COLUMN_NAME,

            DURATION_MS_COLUMN_NAME,
            EXECUTED_AT_COLUMN_NAME,
            TIME_GRADIENT_COLUMN_NAME,
            TIME_PERIOD_COLUMN_NAME,
            TIME_PERIOD_UTC_COLUMN_NAME,

            PROVIDER_COLUMN_NAME,
            QUALITY_DIMENSION_COLUMN_NAME,
            SENSOR_NAME_COLUMN_NAME,

            READOUT_ID_COLUMN_NAME,
            ERROR_MESSAGE_COLUMN_NAME,
            ERROR_SOURCE_COLUMN_NAME,
            ERROR_TIMESTAMP_COLUMN_NAME
    };
}
