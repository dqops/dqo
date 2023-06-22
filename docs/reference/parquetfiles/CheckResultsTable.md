##CheckResultsTable  
Class with constants - the column names in the check_results parquet table.  
  
**The columns of this table is described below**  
  
| Column name | Description | Data type |
|-------------|-------------|-----------|
 | id | The check result id (primary key), it is a uuid of the check hash, time period and the data stream id. This value identifies a single row. | text |
 | actual_value | The actual sensor value that was captured. | double |
 | expected_value | The expected value (expected_value). It is an optional column used when the sensor will also retrieve a comparison value (for accuracy checks). | double |
 | time_period | The time period of the sensor readout (timestamp), using a local timezone from the data source. | local_date_time |
 | time_period_utc | The time period of the sensor readout (timestamp) as a UTC timestamp. | instant |
 | time_gradient | The time gradient (daily, monthly) for recurring checks (checkpoints) and partition checks. It is a &quot;milliseconds&quot; for profiling checks.
 When the time gradient is daily or monthly, the time_period is truncated at the beginning of the time gradient. | text |
 | grouping_level_1 | Column name for the data stream. | text |
 | grouping_level_2 | Column name for the data stream. | text |
 | grouping_level_3 | Column name for the data stream. | text |
 | grouping_level_4 | Column name for the data stream. | text |
 | grouping_level_5 | Column name for the data stream. | text |
 | grouping_level_6 | Column name for the data stream. | text |
 | grouping_level_7 | Column name for the data stream. | text |
 | grouping_level_8 | Column name for the data stream. | text |
 | grouping_level_9 | Column name for the data stream. | text |
 | data_group_hash | The data group hash, it is a hash of the data group levels&#x27; values. | long |
 | data_group_name | The data group name, it is a concatenated name of the data group dimension values, created from [grouping_level_1] / [grouping_level_2] / ... | text |
 | data_grouping_configuration | The data grouping configuration name, it is a name of the named data grouping configuration that was used to run the data quality check. | text |
 | connection_hash | A hash calculated from the connection name (the data source name). | long |
 | connection_name | The connection name (the data source name). | text |
 | provider | The provider name, which is the type of the data source. | text |
 | table_hash | The table name hash. | long |
 | schema_name | The database schema name. | text |
 | table_name | The monitored table name. | text |
 | table_name_pattern | The table name pattern, in case that a data quality check targets multiple tables. | text |
 | table_stage | The stage name of the table. It is a free-form text configured on the table level that could identify the layers of the data warehouse or a data lake, for example: &quot;landing&quot;, &quot;staging&quot;, &quot;cleansing&quot;, etc. | text |
 | table_priority | The table priority value copied from the table&#x27;s definition. The table priority could be used for sorting tables by their importance. | integer |
 | column_hash | The hash of a column. | long |
 | column_name | The column name for which the results are stored. | text |
 | column_name_pattern | The column name pattern, in case that a data quality check targets multiple columns. | text |
 | check_hash | The hash of a data quality check. | long |
 | check_name | The data quality check name. | text |
 | check_display_name | The user configured display name for a data quality check, used when the user wants to use custom, user-friendly data quality check names. | text |
 | check_type | The data quality check type (profiling, recurring, partitioned). | text |
 | check_category | The data quality check category name. | text |
 | quality_dimension | The data quality dimension name. The popular dimensions are: Timeliness, Completeness, Consistency, Validity, Reasonableness, Uniqueness. | text |
 | sensor_name | The data quality sensor name. | text |
 | time_series_id | The time series id (uuid). Identifies a single time series. A time series is a combination of the check_hash and data_stream_hash. | text |
 | executed_at | The UTC timestamp, when the data sensor was executed. | instant |
 | duration_ms | The sensor (query) execution duration in milliseconds. | integer |
 | created_at | The timestamp when the row was created at. | instant |
 | updated_at | The timestamp when the row was updated at. | instant |
 | created_by | The login of the user that created the row. | text |
 | updated_by | The login of the user that updated the row. | text |
 | severity | Check (rule) severity (0, 1, 2, 3) for none, warning, error and fatal severity failed data quality checks. | integer |
 | incident_hash | Column name for a matching data quality incident hash. The value is used to map a failed data quality check to an incident. | long |
 | reference_connection | The name of a connection to another data source that contains the reference data used as the expected values for accuracy checks. | text |
 | reference_table | The table name in another data source that contains the reference data used as the expected values for accuracy checks. | text |
 | reference_schema | The schema in another data source that contains the reference data used as the expected values for accuracy checks. | text |
 | include_in_kpi | Column name for a boolean column that identifies data quality rule results that should be counted in the data quality KPI. | boolean |
 | include_in_sla | Column name for a boolean column that identifies data quality rule results that should be counted in the data quality SLA. | boolean |
 | fatal_lower_bound | Column name for the warning lower bound, returned by the fatal severity rule. | double |
 | fatal_upper_bound | Column name for the fatal upper bound, returned by the fatal severity rule. | double |
 | error_lower_bound | Column name for the error lower bound, returned by the error (medium) severity rule. | double |
 | error_upper_bound | Column name for the error upper bound, returned by the error severity rule. | double |
 | warning_lower_bound | Column name for the warning lower bound, returned by the warning severity rule. | double |
 | warning_upper_bound | Column name for the warning upper bound, returned by the warning severity rule. | double |
