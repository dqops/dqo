##CheckResultsTable  
Class with constants - the column names in the check_results parquet table.  
  
**The columns of this table is described below**  
  
| Column name | Description | Data type |
|-------------|-------------|-----------|
 | id | Column name for a check result id (primary key), it is a uuid of the check hash, time period and the data stream id. This value identifies a single row. | text |
 | actual_value | Column name that stores the actual value: actual_value. | double |
 | expected_value | Column name that stores the expected value (expected_value). It is an optional column used when the sensor will also retrieve a comparison value (for accuracy checks). | double |
 | time_period | Column name that stores the time period of the sensor readout (timestamp): timestamp_period. | local_date_time |
 | time_period_utc | Column name that stores the time period of the sensor readout (timestamp) as a UTC timestamp: timestamp_period_utc. | instant |
 | time_gradient | Column name for a time gradient. | text |
 | stream_level_1 | Column name for the data stream. | text |
 | stream_level_2 | Column name for the data stream. | text |
 | stream_level_3 | Column name for the data stream. | text |
 | stream_level_4 | Column name for the data stream. | text |
 | stream_level_5 | Column name for the data stream. | text |
 | stream_level_6 | Column name for the data stream. | text |
 | stream_level_7 | Column name for the data stream. | text |
 | stream_level_8 | Column name for the data stream. | text |
 | stream_level_9 | Column name for the data stream. | text |
 | data_stream_hash | Column name for a data stream hash, it is a hash of the data stream level names. | long |
 | data_stream_name | Column name for a data stream name, it is a concatenated name of the data stream created from [stream_level_1] / [stream_level_2] / ... | text |
 | connection_hash | Column name for a connection hash. | long |
 | connection_name | Column name for a connection name. | text |
 | provider | Column name for a provider name. | text |
 | table_hash | Column name for a table hash. | long |
 | schema_name | Column name for a table schema. | text |
 | table_name | Column name for a table name. | text |
 | table_name_pattern | Column name for a table name pattern. | text |
 | table_stage | Column name for a table stage. | text |
 | table_priority | Column name for a table priority. | integer |
 | column_hash | Column name for a column hash. | long |
 | column_name | Column name for a column name. | text |
 | column_name_pattern | Column name for a column name pattern. | text |
 | check_hash | Column name for a check hash. | long |
 | check_name | Column name for a check name. | text |
 | check_display_name | Column name for a check display name. | text |
 | check_type | Column name for a check type (profiling, recurring, partitioned). | text |
 | check_category | Column name for a check category. | text |
 | quality_dimension | Column name for a data quality dimension. | text |
 | sensor_name | Column name for a sensor name. | text |
 | time_series_id | Column name for a time series id (uuid). Identifies a single time series. A time series is a combination of the check_hash and data_stream_hash. | text |
 | executed_at | Column name for a sensor executed at timestamp. | instant |
 | duration_ms | Column name for a sensor duration in milliseconds. | integer |
 | severity | Check (rule) severity (0, 1, 2, 3) for none, warning, error and fatal severity failed data quality checks. | integer |
 | incident_hash | Column name for a matching data quality incident hash. The value is used to map a failed data quality check to an incident. | long |
 | include_in_kpi | Column name for a boolean column that identifies data quality rule results that should be counted in the data quality KPI. | boolean |
 | include_in_sla | Column name for a boolean column that identifies data quality rule results that should be counted in the data quality SLA. | boolean |
 | fatal_lower_bound | Column name for the warning lower bound, returned by the fatal severity rule. | double |
 | fatal_upper_bound | Column name for the fatal upper bound, returned by the fatal severity rule. | double |
 | error_lower_bound | Column name for the error lower bound, returned by the error (medium) severity rule. | double |
 | error_upper_bound | Column name for the error upper bound, returned by the error severity rule. | double |
 | warning_lower_bound | Column name for the warning lower bound, returned by the warning severity rule. | double |
 | warning_upper_bound | Column name for the warning upper bound, returned by the warning severity rule. | double |
