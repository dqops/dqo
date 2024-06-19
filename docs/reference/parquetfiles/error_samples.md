---
title: DQOps error_samples parquet table schema
---
# DQOps error_samples parquet table schema
The parquet file schema for the error_samples table stored in the *[$DQO_USER_HOME](../../dqo-concepts/dqops-user-home-folder.md)/.data/error_samples* folder in DQOps.

## Table description

The error samples table that stores sample column values that failed data quality checks that operate on rows (mostly Validity and Consistency checks).
 The error samples are stored in the errors table is located in the *[$DQO_USER_HOME](../../dqo-concepts/dqops-user-home-folder.md)/.data/error_samples* folder that contains uncompressed parquet files.
 The table is partitioned using a Hive compatible partitioning folder structure. When the *[$DQO_USER_HOME](../../dqo-concepts/dqops-user-home-folder.md)* is not configured, it is the folder where DQOps was started (the DQOps user&#x27;s home folder).

 The folder partitioning structure for this table is:
 *c&#x3D;[connection_name]/t&#x3D;[schema_name.table_name]/m&#x3D;[first_day_of_month]/*, for example: *c&#x3D;myconnection/t&#x3D;public.analyzedtable/m&#x3D;2023-01-01/*.
 The date used for monthly partitioning is calculated from the *executed_at* column value.


## Parquet table schema
The columns of this table are described below.

| Column&nbsp;name | Description | Hive&nbsp;data&nbsp;type |
|------------------|-------------|--------------------------|
 | <span class="no-wrap-code">`id`</span> | The check result id (primary key), it is a uuid of the check hash, collected at, sample index and the data grouping id. This value identifies a single row. | *STRING* |
 | <span class="no-wrap-code">`collected_at`</span> | Column for the time when the error samples were captured. All error samples results started as part of the same error sampling session will share the same time.
 The parquet files are time partitioned by this column. | *TIMESTAMP* |
 | <span class="no-wrap-code">`scope`</span> | String column that says if the result is for a whole table (the &quot;table&quot; value) or for each data group separately (the &quot;data_group&quot; value). | *STRING* |
 | <span class="no-wrap-code">`grouping_level_1`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`grouping_level_2`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`grouping_level_3`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`grouping_level_4`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`grouping_level_5`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`grouping_level_6`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`grouping_level_7`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`grouping_level_8`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`grouping_level_9`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`data_group_hash`</span> | The data grouping hash, it is a hash of the data grouping level values. | *BIGINT* |
 | <span class="no-wrap-code">`data_group_name`</span> | The data grouping name, it is a concatenated name of the data grouping dimension values, created from [grouping_level_1] / [grouping_level_2] / ... | *STRING* |
 | <span class="no-wrap-code">`data_grouping_configuration`</span> | The data grouping configuration name, it is a name of the named data grouping configuration that was used to run the data quality check. | *STRING* |
 | <span class="no-wrap-code">`connection_hash`</span> | A hash calculated from the connection name (the data source name). | *BIGINT* |
 | <span class="no-wrap-code">`connection_name`</span> | The connection name (the data source name). | *STRING* |
 | <span class="no-wrap-code">`provider`</span> | The provider name, which is the type of the data source. | *STRING* |
 | <span class="no-wrap-code">`table_hash`</span> | The table name hash. | *BIGINT* |
 | <span class="no-wrap-code">`schema_name`</span> | The database schema name. | *STRING* |
 | <span class="no-wrap-code">`table_name`</span> | The monitored table name. | *STRING* |
 | <span class="no-wrap-code">`table_stage`</span> | The stage name of the table. This is a free-form text configured at the table level that can identify  the layers of the data warehouse or a data lake, for example: &quot;landing&quot;, &quot;staging&quot;, &quot;cleansing&quot;, etc. | *STRING* |
 | <span class="no-wrap-code">`table_priority`</span> | The table priority value copied from the table&#x27;s definition. The table priority can be used to sort tables according to their importance. | *INTEGER* |
 | <span class="no-wrap-code">`column_hash`</span> | The hash of a column. | *BIGINT* |
 | <span class="no-wrap-code">`column_name`</span> | The column name for which the results are stored. | *STRING* |
 | <span class="no-wrap-code">`check_hash`</span> | The hash of a data quality check. | *BIGINT* |
 | <span class="no-wrap-code">`check_name`</span> | The data quality check name. | *STRING* |
 | <span class="no-wrap-code">`check_display_name`</span> | The user configured display name for a data quality check, used when the user wants to use custom, user-friendly data quality check names. | *STRING* |
 | <span class="no-wrap-code">`check_type`</span> | The data quality check type (profiling, monitoring, partitioned). | *STRING* |
 | <span class="no-wrap-code">`check_category`</span> | The data quality check category name. | *STRING* |
 | <span class="no-wrap-code">`quality_dimension`</span> | The data quality dimension name. The popular dimensions are: Timeliness, Completeness, Consistency, Validity, Reasonableness, Uniqueness. | *STRING* |
 | <span class="no-wrap-code">`sensor_name`</span> | The data quality sensor name. | *STRING* |
 | <span class="no-wrap-code">`time_series_id`</span> | The time series id (uuid). Identifies a single time series. A time series is a combination of the check_hash and data_group_hash. | *STRING* |
 | <span class="no-wrap-code">`result_type`</span> | The sample&#x27;s result data type. | *STRING* |
 | <span class="no-wrap-code">`result_string`</span> | The sample value when it is a string value. | *STRING* |
 | <span class="no-wrap-code">`result_integer`</span> | The sample value when it is an integer value. It is a long (64 bit) value where we store all short, integer, long values. | *BIGINT* |
 | <span class="no-wrap-code">`result_float`</span> | The sample value when it is a numeric value with. It is a double value where we store all double, float, numeric and decimal values. | *DOUBLE* |
 | <span class="no-wrap-code">`result_boolean`</span> | The sample value when it is a boolean value. | *BOOLEAN* |
 | <span class="no-wrap-code">`result_date`</span> | The sample value when it is a local date value. | *DATE* |
 | <span class="no-wrap-code">`result_date_time`</span> | The sample value when it is a local date time value. | *TIMESTAMP* |
 | <span class="no-wrap-code">`result_instant`</span> | The sample value when it is an absolute (UTC timezone) instant. | *TIMESTAMP* |
 | <span class="no-wrap-code">`result_time`</span> | The sample value when it is time value. | *INTERVAL* |
 | <span class="no-wrap-code">`sample_index`</span> | The 1-based index of the collected sample. | *INTEGER* |
 | <span class="no-wrap-code">`sample_filter`</span> | The sample filtering formula that was used in the where filter. | *STRING* |
 | <span class="no-wrap-code">`row_id_1`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`row_id_2`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`row_id_3`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`row_id_4`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`row_id_5`</span> | Data group value at a single level. | *STRING* |
 | <span class="no-wrap-code">`executed_at`</span> | The UTC timestamp, when the data sensor was executed. | *TIMESTAMP* |
 | <span class="no-wrap-code">`duration_ms`</span> | The sensor (query) execution duration in milliseconds. | *INTEGER* |
 | <span class="no-wrap-code">`created_at`</span> | The timestamp when the row was created at. | *TIMESTAMP* |
 | <span class="no-wrap-code">`updated_at`</span> | The timestamp when the row was updated at. | *TIMESTAMP* |
 | <span class="no-wrap-code">`created_by`</span> | The login of the user that created the row. | *STRING* |
 | <span class="no-wrap-code">`updated_by`</span> | The login of the user that updated the row. | *STRING* |


## What's more
- You can find more information on how the Parquet files are partitioned in the [data quality results storage concept](../../dqo-concepts/data-storage-of-data-quality-results.md).
