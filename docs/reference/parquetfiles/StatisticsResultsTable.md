##StatisticsResultsTable  
Constants with the column names of the &quot;statistics&quot; table that contains the results of table and column statistics (basic profiling).  
  
**The columns of this table is described below**  
  
| Column name | Description | Data type |
|-------------|-------------|-----------|
 | id | Column name for a statistics result id (primary key), it is a uuid of the statistics collector hash, executed at and the data stream id. This value identifies a single row. | text |
 | collected_at | Column name for the time when the statistics were captured. All statistics results started as part of the same statistics collection session will share the same time. The parquet files are time partitioned by this column. | local_date_time |
 | status | Column name for a statistics collector status (&#x27;success&#x27; or &#x27;error&#x27;). | text |
 | result_type | Column name for a statistics collector result data type. | text |
 | result_string | Column name for a statistics collector result when it is a string value. | text |
 | result_integer | Column name for a statistics collector result when it is an integer value. It is a long (64 bit) value where we store all short, integer, long values. | long |
 | result_float | Column name for a statistics collector result when it is a numeric value with. It is a double value where we store all double, float, numeric and decimal values. | double |
 | result_boolean | Column name for a statistics collector result when it is a boolean value. | boolean |
 | result_date | Column name for a statistics collector result when it is a local date value. | local_date |
 | result_date_time | Column name for a statistics collector result when it is a local date time value. | local_date_time |
 | result_instant | Column name for a statistics collector result when it is an absolute (UTC timezone) instant. | instant |
 | result_time | Column name for a statistics collector result when it is time value. | local_time |
 | sample_index | The index of the sample for statistics collector that collect data samples. | integer |
 | sample_count | The count of the samples for statistics collector that collect data samples. | long |
 | scope | Column name for string column that says if the result is for a whole table (&quot;table&quot;) or for each data stream separately (&quot;data_stream&quot;) | text |
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
 | data_stream_mapping_name | The data stream configuration name, it is a name of the named data stream mapping configuration that was used to run the data quality check. | text |
 | connection_hash | Column name for a connection hash. | long |
 | connection_name | Column name for a connection name. | text |
 | provider | Column name for a provider name. | text |
 | table_hash | Column name for a table hash. | long |
 | schema_name | Column name for a table schema. | text |
 | table_name | Column name for a table name. | text |
 | table_stage | Column name for a table stage. | text |
 | column_hash | Column name for a column hash. | long |
 | column_name | Column name for a column name. | text |
 | collector_hash | Column name for a statistics collector hash. | long |
 | collector_name | Column name for a statistics collector name. | text |
 | collector_target | Column name for a statistics collector target (table, column). | text |
 | collector_category | Column name for a statistics collector category. | text |
 | sensor_name | Column name for a sensor name. | text |
 | time_series_id | Column name for a time series id (uuid). Identifies a single time series. A time series is a combination of the profiler_hash and data_stream_hash. | text |
 | executed_at | Column name for a statistics collector executed at timestamp. | instant |
 | duration_ms | Column name for a sensor duration in milliseconds. | integer |
 | error_message | Column name for an optional error message when the status is &#x27;error&#x27;. | text |
 | created_at | The timestamp when the row was created at. | instant |
 | updated_at | The timestamp when the row was updated at. | instant |
 | created_by | The login of the user that created the row. | text |
 | updated_by | The login of the user that updated the row. | text |
