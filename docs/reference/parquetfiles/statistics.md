##statistics  
The basic profiling results (statistics) table that stores basic profiling statistical values.
 The statistics are stored in the errors table is located in the $DQO_USER_HOME/.data/statistics folder that contains uncompressed parquet files.
 The table is partitioned using a Hive compatible partitioning folder structure. When the $DQO_USER_HOME is not configured, it is the folder where DQOps was started (the DQOps user&#x27;s home folder).

 The folder partitioning structure for this table is:
 c&#x3D;[connection_name]/t&#x3D;[schema_name.table_name]/m&#x3D;[first_day_of_month]/, for example: c&#x3D;myconnection/t&#x3D;public.analyzedtable/m&#x3D;2023-01-01/.  
  
**The columns of this table is described below**  
  
| Column name | Description | Data type |
|-------------|-------------|-----------|
 | id | Column for a statistics result id (primary key), it is a uuid of the statistics collector hash, executed at and the data stream id. This value identifies a single row. | text |
 | collected_at | Column for the time when the statistics were captured. All statistics results started as part of the same statistics collection session will share the same time. The parquet files are time partitioned by this column. | local_date_time |
 | status | Column for a statistics collector status (&#x27;success&#x27; or &#x27;error&#x27;). | text |
 | result_type | Column for a statistics collector result data type. | text |
 | result_string | Column for a statistics collector result when it is a string value. | text |
 | result_integer | Column for a statistics collector result when it is an integer value. It is a long (64 bit) value where we store all short, integer, long values. | long |
 | result_float | Column for a statistics collector result when it is a numeric value with. It is a double value where we store all double, float, numeric and decimal values. | double |
 | result_boolean | Column for a statistics collector result when it is a boolean value. | boolean |
 | result_date | Column for a statistics collector result when it is a local date value. | local_date |
 | result_date_time | Column for a statistics collector result when it is a local date time value. | local_date_time |
 | result_instant | Column for a statistics collector result when it is an absolute (UTC timezone) instant. | instant |
 | result_time | Column for a statistics collector result when it is time value. | local_time |
 | sample_index | The index of the sample for statistics collector that collect data samples. | integer |
 | sample_count | The count of the samples for statistics collector that collect data samples. | long |
 | scope | String column that says if the result is for a whole table (&quot;table&quot;) or for each data stream separately (&quot;data_stream&quot;) | text |
 | grouping_level_1 | Column name for the data stream. | text |
 | grouping_level_2 | Column name for the data stream. | text |
 | grouping_level_3 | Column name for the data stream. | text |
 | grouping_level_4 | Column name for the data stream. | text |
 | grouping_level_5 | Column name for the data stream. | text |
 | grouping_level_6 | Column name for the data stream. | text |
 | grouping_level_7 | Column name for the data stream. | text |
 | grouping_level_8 | Column name for the data stream. | text |
 | grouping_level_9 | Column name for the data stream. | text |
 | data_group_hash | Column for a data group hash, it is a hash of the data group level values. | long |
 | data_group_name | The data group name, it is a concatenated name of the data group dimensions, created from [grouping_level_1] / [grouping_level_2] / ... | text |
 | data_grouping_configuration | The data grouping configuration name, it is a name of the named data grouping configuration that was used to run the data quality check. | text |
 | connection_hash | Column for a connection hash. | long |
 | connection_name | Column for a connection name. | text |
 | provider | Column for a provider name. | text |
 | table_hash | Column for a table hash. | long |
 | schema_name | Column for a table schema. | text |
 | table_name | Column for a table name. | text |
 | table_stage | Column for a table stage. | text |
 | column_hash | Column for a column hash. | long |
 | column_name | Column for a column name. | text |
 | collector_hash | Column for a statistics collector hash. | long |
 | collector_name | Column for a statistics collector name. | text |
 | collector_target | Column for a statistics collector target (table, column). | text |
 | collector_category | Column for a statistics collector category. | text |
 | sensor_name | Column for a sensor name. | text |
 | time_series_id | Column for a time series id (uuid). Identifies a single time series. A time series is a combination of the profiler_hash and data_stream_hash. | text |
 | executed_at | Column for a statistics collector executed at timestamp. | instant |
 | duration_ms | Column for a sensor duration in milliseconds. | integer |
 | error_message | Column for an optional error message when the status is &#x27;error&#x27;. | text |
 | created_at | The timestamp when the row was created at. | instant |
 | updated_at | The timestamp when the row was updated at. | instant |
 | created_by | The login of the user that created the row. | text |
 | updated_by | The login of the user that updated the row. | text |
