---
title: DQOps check_results parquet table schema
---
# DQOps check_results parquet table schema
The parquet file schema for the check_results table stored in the *[$DQO_USER_HOME](../../dqo-concepts/dqops-user-home-folder.md)/.data/check_results* folder in DQOps.

## Table description

The data quality check results table that stores the data quality check results - a copy of sensor readouts (copied from the sensor_readouts table) and evaluated by the data quality rules.
 This table differs from the *sensor_readouts* by adding also the result of the rule evaluation. The additional columns are the &#x27;severity&#x27; which says if the check passed (the severity is 0)
 or the data quality check raised a data quality issue with a severity warning - 1, error - 2 or fatal - 3.
 The check results are stored in the check_results table is located in the *[$DQO_USER_HOME](../../dqo-concepts/dqops-user-home-folder.md)/.data/check_results* folder that contains uncompressed parquet files.
 The table is partitioned using a Hive compatible partitioning folder structure. When the *[$DQO_USER_HOME](../../dqo-concepts/dqops-user-home-folder.md)* is not configured, it is the folder where DQOps was started (the DQOps user&#x27;s home folder).

 The folder partitioning structure for this table is:
 *c&#x3D;[connection_name]/t&#x3D;[schema_name.table_name]/m&#x3D;[first_day_of_month]/*, for example: *c&#x3D;myconnection/t&#x3D;public.testedtable/m&#x3D;2023-01-01/*.


## Parquet table schema
The columns of this table are described below.

| Column&nbsp;name | Description | Hive&nbsp;data&nbsp;type |
|------------------|-------------|--------------------------|
 | <span class="no-wrap-code">`id`</span> | The check result id (primary key), it is a uuid of the check hash, time period and the data stream id. This value identifies a single row. | *STRING* |
 | <span class="no-wrap-code">`actual_value`</span> | The actual sensor value that was captured. | *DOUBLE* |
 | <span class="no-wrap-code">`expected_value`</span> | The expected value (expected_value). It is an optional column used when the sensor will also retrieve a comparison value (for accuracy checks). | *DOUBLE* |
 | <span class="no-wrap-code">`time_period`</span> | The time period of the sensor readout (timestamp), using a local timezone from the data source. | *TIMESTAMP* |
 | <span class="no-wrap-code">`time_period_utc`</span> | The time period of the sensor readout (timestamp) as a UTC timestamp. | *TIMESTAMP* |
 | <span class="no-wrap-code">`time_gradient`</span> | The time gradient (daily, monthly) for monitoring checks (checkpoints) and partition checks. It is a &quot;milliseconds&quot; for profiling checks. When the time gradient is daily or monthly, the time_period is truncated at the beginning of the time gradient. | *STRING* |
 | <span class="no-wrap-code">`grouping_level_1`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`grouping_level_2`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`grouping_level_3`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`grouping_level_4`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`grouping_level_5`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`grouping_level_6`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`grouping_level_7`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`grouping_level_8`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`grouping_level_9`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`data_group_hash`</span> | The data group hash, it is a hash of the data group levels&#x27; values. | *BIGINT* |
 | <span class="no-wrap-code">`data_group_name`</span> | The data group name, it is a concatenated name of the data group dimension values, created from [grouping_level_1] / [grouping_level_2] / ... | *STRING* |
 | <span class="no-wrap-code">`data_grouping_configuration`</span> | The data grouping configuration name, it is a name of the named data grouping configuration that was used to run the data quality check. | *STRING* |
 | <span class="no-wrap-code">`connection_hash`</span> | A hash calculated from the connection name (the data source name). | *BIGINT* |
 | <span class="no-wrap-code">`connection_name`</span> | The connection name (the data source name). | *STRING* |
 | <span class="no-wrap-code">`provider`</span> | The provider name, which is the type of the data source. | *STRING* |
 | <span class="no-wrap-code">`table_hash`</span> | The table name hash. | *BIGINT* |
 | <span class="no-wrap-code">`schema_name`</span> | The database schema name. | *STRING* |
 | <span class="no-wrap-code">`table_name`</span> | The monitored table name. | *STRING* |
 | <span class="no-wrap-code">`table_name_pattern`</span> | The table name pattern, in case that a data quality check targets multiple tables. | *STRING* |
 | <span class="no-wrap-code">`table_stage`</span> | The stage name of the table. This is a free-form text configured at the table level that can identify  the layers of the data warehouse or a data lake, for example: &quot;landing&quot;, &quot;staging&quot;, &quot;cleansing&quot;, etc. | *STRING* |
 | <span class="no-wrap-code">`table_priority`</span> | The table priority value copied from the table&#x27;s definition. The table priority can be used to sort tables according to their importance. | *INTEGER* |
 | <span class="no-wrap-code">`column_hash`</span> | The hash of a column. | *BIGINT* |
 | <span class="no-wrap-code">`column_name`</span> | The column for which the results are stored. | *STRING* |
 | <span class="no-wrap-code">`column_name_pattern`</span> | The column pattern, in case that a data quality check targets multiple columns. | *STRING* |
 | <span class="no-wrap-code">`check_hash`</span> | The hash of a data quality check. | *BIGINT* |
 | <span class="no-wrap-code">`check_name`</span> | The data quality check name. | *STRING* |
 | <span class="no-wrap-code">`check_display_name`</span> | The user configured display name for a data quality check, used when the user wants to use custom, user-friendly data quality check names. | *STRING* |
 | <span class="no-wrap-code">`check_type`</span> | The data quality check type (profiling, monitoring, partitioned). | *STRING* |
 | <span class="no-wrap-code">`check_category`</span> | The data quality check category name. | *STRING* |
 | <span class="no-wrap-code">`table_comparison`</span> | The name of a table comparison configuration used for a data comparison (accuracy) check. | *STRING* |
 | <span class="no-wrap-code">`quality_dimension`</span> | The data quality dimension name. The popular dimensions are: Timeliness, Completeness, Consistency, Validity, Reasonableness, Uniqueness. | *STRING* |
 | <span class="no-wrap-code">`sensor_name`</span> | The data quality sensor name. | *STRING* |
 | <span class="no-wrap-code">`time_series_id`</span> | The time series id (uuid). Identifies a single time series. A time series is a combination of the check_hash and data_group_hash. | *STRING* |
 | <span class="no-wrap-code">`executed_at`</span> | The UTC timestamp, when the data sensor was executed. | *TIMESTAMP* |
 | <span class="no-wrap-code">`duration_ms`</span> | The sensor (query) execution duration in milliseconds. | *INTEGER* |
 | <span class="no-wrap-code">`created_at`</span> | The timestamp when the row was created at. | *TIMESTAMP* |
 | <span class="no-wrap-code">`updated_at`</span> | The timestamp when the row was updated at. | *TIMESTAMP* |
 | <span class="no-wrap-code">`created_by`</span> | The login of the user that created the row. | *STRING* |
 | <span class="no-wrap-code">`updated_by`</span> | The login of the user that updated the row. | *STRING* |
 | <span class="no-wrap-code">`severity`</span> | Check (rule) severity (0, 1, 2, 3) for none, warning, error and fatal severity failed data quality checks. | *INTEGER* |
 | <span class="no-wrap-code">`incident_hash`</span> | The matching data quality incident hash. The value is used to map a failed data quality check to an incident. | *BIGINT* |
 | <span class="no-wrap-code">`reference_connection`</span> | The name of a connection to another data source that contains the reference data used as the expected values for accuracy checks. | *STRING* |
 | <span class="no-wrap-code">`reference_schema`</span> | The schema in another data source that contains the reference data used as the expected values for accuracy checks. | *STRING* |
 | <span class="no-wrap-code">`reference_table`</span> | The table name in another data source that contains the reference data used as the expected values for accuracy checks. | *STRING* |
 | <span class="no-wrap-code">`reference_column`</span> | The column name in another data source that contains the reference data used as the expected values for accuracy checks. | *STRING* |
 | <span class="no-wrap-code">`include_in_kpi`</span> | The boolean column that identifies data quality rule results that should be counted in the data quality KPI. | *BOOLEAN* |
 | <span class="no-wrap-code">`include_in_sla`</span> | The boolean column that identifies data quality rule results that should be counted in the data quality SLA (Data Contract). | *BOOLEAN* |
 | <span class="no-wrap-code">`fatal_lower_bound`</span> | The warning lower bound, returned by the fatal severity rule. | *DOUBLE* |
 | <span class="no-wrap-code">`fatal_upper_bound`</span> | The fatal upper bound, returned by the fatal severity rule. | *DOUBLE* |
 | <span class="no-wrap-code">`error_lower_bound`</span> | The error lower bound, returned by the error (medium) severity rule. | *DOUBLE* |
 | <span class="no-wrap-code">`error_upper_bound`</span> | The error upper bound, returned by the error severity rule. | *DOUBLE* |
 | <span class="no-wrap-code">`warning_lower_bound`</span> | The warning lower bound, returned by the warning severity rule. | *DOUBLE* |
 | <span class="no-wrap-code">`warning_upper_bound`</span> | The warning upper bound, returned by the warning severity rule. | *DOUBLE* |


## What's more
- You can find more information on how the Parquet files are partitioned in the [data quality results storage concept](../../dqo-concepts/data-storage-of-data-quality-results.md).
