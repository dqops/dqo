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
package com.dqops.data.checkresults.factory;

import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;

/**
 * The data quality check results table that stores the data quality check results - a copy of sensor readouts (copied from the sensor_readouts table) and evaluated by the data quality rules.
 * This table differs from the *sensor_readouts* by adding also the result of the rule evaluation. The additional columns are the 'severity' which says if the check passed (the severity is 0)
 * or the data quality check raised a data quality issue with a severity warning - 1, error - 2 or fatal - 3.
 * The check results are stored in the check_results table is located in the *$DQO_USER_HOME/.data/check_results* folder that contains uncompressed parquet files.
 * The table is partitioned using a Hive compatible partitioning folder structure. When the *$DQO_USER_HOME* is not configured, it is the folder where DQOps was started (the DQOps user's home folder).
 *
 * The folder partitioning structure for this table is:
 * *c=[connection_name]/t=[schema_name.table_name]/m=[first_day_of_month]/*, for example: *c=myconnection/t=public.testedtable/m=2023-01-01/*.
 */
public class CheckResultsColumnNames extends SensorReadoutsColumnNames {
    /**
     * Check (rule) severity (0, 1, 2, 3) for none, warning, error and fatal severity failed data quality checks.
     */
    public static final String SEVERITY_COLUMN_NAME = "severity";

    /**
     * The matching data quality incident hash. The value is used to map a failed data quality check to an incident.
     */
    public static final String INCIDENT_HASH_COLUMN_NAME = "incident_hash";

    /**
     * The name of a connection to another data source that contains the reference data used as the expected values for accuracy checks.
     */
    public static final String REFERENCE_CONNECTION_COLUMN_NAME = "reference_connection";

    /**
     * The schema in another data source that contains the reference data used as the expected values for accuracy checks.
     */
    public static final String REFERENCE_SCHEMA_COLUMN_NAME = "reference_schema";

    /**
     * The table name in another data source that contains the reference data used as the expected values for accuracy checks.
     */
    public static final String REFERENCE_TABLE_COLUMN_NAME = "reference_table";

    /**
     * The column name in another data source that contains the reference data used as the expected values for accuracy checks.
     */
    public static final String REFERENCE_COLUMN_COLUMN_NAME = "reference_column";

    /**
     * The boolean column that identifies data quality rule results that should be counted in the data quality KPI.
     */
    public static final String INCLUDE_IN_KPI_COLUMN_NAME = "include_in_kpi";

    /**
     * The boolean column that identifies data quality rule results that should be counted in the data quality SLA (Data Contract).
     */
    public static final String INCLUDE_IN_SLA_COLUMN_NAME = "include_in_sla";

    /**
     * The warning lower bound, returned by the fatal severity rule.
     */
    public static final String FATAL_LOWER_BOUND_COLUMN_NAME = "fatal_lower_bound";

    /**
     * The fatal upper bound, returned by the fatal severity rule.
     */
    public static final String FATAL_UPPER_BOUND_COLUMN_NAME = "fatal_upper_bound";

    /**
     * The error lower bound, returned by the error (medium) severity rule.
     */
    public static final String ERROR_LOWER_BOUND_COLUMN_NAME = "error_lower_bound";

    /**
     * The error upper bound, returned by the error severity rule.
     */
    public static final String ERROR_UPPER_BOUND_COLUMN_NAME = "error_upper_bound";

    /**
     * The warning lower bound, returned by the warning severity rule.
     */
    public static final String WARNING_LOWER_BOUND_COLUMN_NAME = "warning_lower_bound";

    /**
     * The warning upper bound, returned by the warning severity rule.
     */
    public static final String WARNING_UPPER_BOUND_COLUMN_NAME = "warning_upper_bound";

    /**
     * List of column names that should be loaded from the parquet files when the recent result overview is needed.
     * We only want to read the statuses.
     */
    public static final String[] COLUMN_NAMES_FOR_RESULTS_OVERVIEW = new String[] {
            COLUMN_NAME_COLUMN_NAME,
            CHECK_TYPE_COLUMN_NAME,
            TIME_GRADIENT_COLUMN_NAME,
            CHECK_HASH_COLUMN_NAME,
            CHECK_CATEGORY_COLUMN_NAME,
            CHECK_NAME_COLUMN_NAME,
            DATA_GROUP_NAME_COLUMN_NAME,
            ACTUAL_VALUE_COLUMN_NAME,
            SEVERITY_COLUMN_NAME,
            TIME_PERIOD_COLUMN_NAME,
            TIME_PERIOD_UTC_COLUMN_NAME,
            EXECUTED_AT_COLUMN_NAME,
            TABLE_COMPARISON_NAME_COLUMN_NAME,
            TIME_SERIES_ID_COLUMN_NAME,
            QUALITY_DIMENSION_COLUMN_NAME
    };

    /**
     * List of column names that should be loaded from the parquet files when we are not planning to make any updates.
     * We only want to read the statuses.
     */
    public static final String[] CHECK_RESULTS_COLUMN_NAMES_FOR_READ_ONLY_ACCESS = new String[] {
            ID_COLUMN_NAME,

            ACTUAL_VALUE_COLUMN_NAME,
            EXPECTED_VALUE_COLUMN_NAME,
            WARNING_LOWER_BOUND_COLUMN_NAME,
            WARNING_UPPER_BOUND_COLUMN_NAME,
            ERROR_LOWER_BOUND_COLUMN_NAME,
            ERROR_UPPER_BOUND_COLUMN_NAME,
            FATAL_LOWER_BOUND_COLUMN_NAME,
            FATAL_UPPER_BOUND_COLUMN_NAME,
            SEVERITY_COLUMN_NAME,

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

            INCLUDE_IN_KPI_COLUMN_NAME,
            INCLUDE_IN_SLA_COLUMN_NAME,
            PROVIDER_COLUMN_NAME,
            QUALITY_DIMENSION_COLUMN_NAME,
            SENSOR_NAME_COLUMN_NAME,
            TIME_SERIES_ID_COLUMN_NAME,

            INCIDENT_HASH_COLUMN_NAME
    };
}
