##ErrorsTable  
Constants with the column names in the &quot;errors&quot; parquet tables.  
  
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
 | readout_id | Column name that stores the sensor readout ID. | text |
 | error_message | Column name that stores the error message. | text |
 | error_source | Column name that stores the error source, which is the component that raised an error (sensor or rule). | text |
 | error_timestamp | Column name that stores the error timestamp using the local timestamp. | local_date_time |
