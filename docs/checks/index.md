# Checks

**This is a list of the checks in DQO broken down by category and a brief description of what they do.**

## **Table checks**


###accuracy  
Compares the tested table with another (reference) table.

| Check name | Check type | Description |
|------------|------------|-------------|
|[total_row_count_match_percent](./table/accuracy/total-row-count-match-percent/#total-row-count-match-percent)|profiling|Verifies that the total row count of the tested table matches the total row count of another (reference) table.|
|[daily_total_row_count_match_percent](./table/accuracy/total-row-count-match-percent/#daily-total-row-count-match-percent)|recurring|Verifies the total ow count of a tested table and compares it to a row count of a reference table. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_row_count_match_percent](./table/accuracy/total-row-count-match-percent/#monthly-total-row-count-match-percent)|recurring|Verifies the total row count of a tested table and compares it to a row count of a reference table. Stores the most recent row count for each month when the data quality check was evaluated.|





###availability  
Checks whether the table is accessible and available for use.

| Check name | Check type | Description |
|------------|------------|-------------|
|[table_availability](./table/availability/table-availability/#table-availability)|profiling|Verifies availability of the table in a database using a simple row count.|
|[daily_table_availability](./table/availability/table-availability/#daily-table-availability)|recurring|Verifies availability on table in database using simple row count. Stores the most recent table availability status for each day when the data quality check was evaluated.|
|[monthly_table_availability](./table/availability/table-availability/#monthly-table-availability)|recurring|Verifies availability on table in database using simple row count. Stores the most recent table availability status for each month when the data quality check was evaluated.|





###schema  
Detects schema drifts such as columns added, removed, reordered or the data types of columns have changed.

| Check name | Check type | Description |
|------------|------------|-------------|
|[column_count](./table/schema/column-count/#column-count)|profiling|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).|
|[daily_column_count](./table/schema/column-count/#daily-column-count)|recurring|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each day when the data quality check was evaluated.|
|[monthly_column_count](./table/schema/column-count/#monthly-column-count)|recurring|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[column_count_changed](./table/schema/column-count-changed/#column-count-changed)|profiling|Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.|
|[daily_column_count_changed](./table/schema/column-count-changed/#daily-column-count-changed)|recurring|Detects if the count of columns has changed since the most recent day. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each day when the data quality check was evaluated.|
|[monthly_column_count_changed](./table/schema/column-count-changed/#monthly-column-count-changed)|recurring|Detects if the count of columns has changed since the last month. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[column_list_changed](./table/schema/column-list-changed/#column-list-changed)|profiling|Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|
|[daily_column_list_changed](./table/schema/column-list-changed/#daily-column-list-changed)|recurring|Detects if new columns were added or existing columns were removed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|
|[monthly_column_list_changed](./table/schema/column-list-changed/#monthly-column-list-changed)|recurring|Detects if new columns were added or existing columns were removed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[column_list_or_order_changed](./table/schema/column-list-or-order-changed/#column-list-or-order-changed)|profiling|Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|
|[daily_column_list_or_order_changed](./table/schema/column-list-or-order-changed/#daily-column-list-or-order-changed)|recurring|Detects if new columns were added, existing columns were removed or the columns were reordered since the most recent day. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|
|[monthly_column_list_or_order_changed](./table/schema/column-list-or-order-changed/#monthly-column-list-or-order-changed)|recurring|Detects if new columns were added, existing columns were removed or the columns were reordered since the last month. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[column_types_changed](./table/schema/column-types-changed/#column-types-changed)|profiling|Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|
|[daily_column_types_changed](./table/schema/column-types-changed/#daily-column-types-changed)|recurring|Detects if new columns were added, removed or their data types have changed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|
|[monthly_column_types_changed](./table/schema/column-types-changed/#monthly-column-types-changed)|recurring|Detects if new columns were added, removed or their data types have changed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|





###sql  
Validate data against user-defined SQL queries at the table level. Checks in this group allow for validation that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

| Check name | Check type | Description |
|------------|------------|-------------|
|[sql_condition_passed_percent_on_table](./table/sql/sql-condition-passed-percent-on-table/#sql-condition-passed-percent-on-table)|profiling|Verifies that a set percentage of rows passed a custom SQL condition (expression).|
|[daily_sql_condition_passed_percent_on_table](./table/sql/sql-condition-passed-percent-on-table/#daily-sql-condition-passed-percent-on-table)|recurring|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_condition_passed_percent_on_table](./table/sql/sql-condition-passed-percent-on-table/#monthly-sql-condition-passed-percent-on-table)|recurring|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_condition_passed_percent_on_table](./table/sql/sql-condition-passed-percent-on-table/#daily-partition-sql-condition-passed-percent-on-table)|partitioned|Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_condition_passed_percent_on_table](./table/sql/sql-condition-passed-percent-on-table/#monthly-partition-sql-condition-passed-percent-on-table)|partitioned|Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sql_condition_failed_count_on_table](./table/sql/sql-condition-failed-count-on-table/#sql-condition-failed-count-on-table)|profiling|Verifies that a set number of rows failed a custom SQL condition (expression).|
|[daily_sql_condition_failed_count_on_table](./table/sql/sql-condition-failed-count-on-table/#daily-sql-condition-failed-count-on-table)|recurring|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_condition_failed_count_on_table](./table/sql/sql-condition-failed-count-on-table/#monthly-sql-condition-failed-count-on-table)|recurring|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_condition_failed_count_on_table](./table/sql/sql-condition-failed-count-on-table/#daily-partition-sql-condition-failed-count-on-table)|partitioned|Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_condition_failed_count_on_table](./table/sql/sql-condition-failed-count-on-table/#monthly-partition-sql-condition-failed-count-on-table)|partitioned|Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sql_aggregate_expr_table](./table/sql/sql-aggregate-expr-table/#sql-aggregate-expr-table)|profiling|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|
|[daily_sql_aggregate_expr_table](./table/sql/sql-aggregate-expr-table/#daily-sql-aggregate-expr-table)|recurring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_aggregate_expr_table](./table/sql/sql-aggregate-expr-table/#monthly-sql-aggregate-expr-table)|recurring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_aggregate_expr_table](./table/sql/sql-aggregate-expr-table/#daily-partition-sql-aggregate-expr-table)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_aggregate_expr_table](./table/sql/sql-aggregate-expr-table/#monthly-partition-sql-aggregate-expr-table)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|





###timeliness  
Assesses the freshness and staleness of data, as well as data ingestion delay and reload lag for partitioned data.

| Check name | Check type | Description |
|------------|------------|-------------|
|[data_freshness](./table/timeliness/data-freshness/#data-freshness)|profiling|Calculates the number of days since the most recent event timestamp (freshness)|
|[daily_data_freshness](./table/timeliness/data-freshness/#daily-data-freshness)|recurring|Daily  calculating the number of days since the most recent event timestamp (freshness)|
|[monthly_data_freshness](./table/timeliness/data-freshness/#monthly-data-freshness)|recurring|Monthly recurring calculating the number of days since the most recent event timestamp (freshness)|
|[daily_partition_data_freshness](./table/timeliness/data-freshness/#daily-partition-data-freshness)|partitioned|Daily partitioned check calculating the number of days since the most recent event timestamp (freshness)|
|[monthly_partition_data_freshness](./table/timeliness/data-freshness/#monthly-partition-data-freshness)|partitioned|Monthly partitioned check calculating the number of days since the most recent event (freshness)|


| Check name | Check type | Description |
|------------|------------|-------------|
|[data_staleness](./table/timeliness/data-staleness/#data-staleness)|profiling|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[daily_data_staleness](./table/timeliness/data-staleness/#daily-data-staleness)|recurring|Daily  calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[monthly_data_staleness](./table/timeliness/data-staleness/#monthly-data-staleness)|recurring|Monthly recurring calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[daily_partition_data_staleness](./table/timeliness/data-staleness/#daily-partition-data-staleness)|partitioned|Daily partitioned check calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[monthly_partition_data_staleness](./table/timeliness/data-staleness/#monthly-partition-data-staleness)|partitioned|Monthly partitioned check calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)|


| Check name | Check type | Description |
|------------|------------|-------------|
|[data_ingestion_delay](./table/timeliness/data-ingestion-delay/#data-ingestion-delay)|profiling|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[daily_data_ingestion_delay](./table/timeliness/data-ingestion-delay/#daily-data-ingestion-delay)|recurring|Daily  calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[monthly_data_ingestion_delay](./table/timeliness/data-ingestion-delay/#monthly-data-ingestion-delay)|recurring|Monthly recurring calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[daily_partition_data_ingestion_delay](./table/timeliness/data-ingestion-delay/#daily-partition-data-ingestion-delay)|partitioned|Daily partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[monthly_partition_data_ingestion_delay](./table/timeliness/data-ingestion-delay/#monthly-partition-data-ingestion-delay)|partitioned|Monthly partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_reload_lag](./table/timeliness/daily-partition-reload-lag/#daily-partition-reload-lag)|partitioned|Daily partitioned check calculating the longest time a row waited to be load|
|[monthly_partition_reload_lag](./table/timeliness/daily-partition-reload-lag/#monthly-partition-reload-lag)|partitioned|Monthly partitioned check calculating the longest time a row waited to be load|





###volume  
Evaluates the overall quality of the table by verifying the number of rows.

| Check name | Check type | Description |
|------------|------------|-------------|
|[row_count](./table/volume/row-count/#row-count)|profiling|Verifies that the number of rows in a table does not exceed the minimum accepted count.|
|[daily_row_count](./table/volume/row-count/#daily-row-count)|recurring|Verifies that the number of rows in a table does not exceed the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_row_count](./table/volume/row-count/#monthly-row-count)|recurring|Verifies that the number of rows in a table does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_row_count](./table/volume/row-count/#daily-partition-row-count)|partitioned|Verifies that the number of rows in a table does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_min_row_count](./table/volume/row-count/#monthly-partition-min-row-count)|partitioned|Verifies that the number of rows in a table does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[row_count_anomaly_7_days](./table/volume/row-count-anomaly-7-days/#row-count-anomaly-7-days)|profiling|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 7 days.|
|[daily_row_count_anomaly_7_days](./table/volume/row-count-anomaly-7-days/#daily-row-count-anomaly-7-days)|recurring|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 7 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[row_count_anomaly_30_days](./table/volume/row-count-anomaly-30-days/#row-count-anomaly-30-days)|profiling|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.|
|[daily_row_count_anomaly_30_days](./table/volume/row-count-anomaly-30-days/#daily-row-count-anomaly-30-days)|recurring|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[row_count_anomaly_60_days](./table/volume/row-count-anomaly-60-days/#row-count-anomaly-60-days)|profiling|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 60 days.|
|[daily_row_count_anomaly_60_days](./table/volume/row-count-anomaly-60-days/#daily-row-count-anomaly-60-days)|recurring|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 60 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[row_count_change](./table/volume/row-count-change/#row-count-change)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|
|[daily_row_count_change](./table/volume/row-count-change/#daily-row-count-change)|recurring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|
|[monthly_row_count_change](./table/volume/row-count-change/#monthly-row-count-change)|recurring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|
|[daily_partition_row_count_change](./table/volume/row-count-change/#daily-partition-row-count-change)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|
|[monthly_partition_row_count_change](./table/volume/row-count-change/#monthly-partition-row-count-change)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[row_count_change_yesterday](./table/volume/row-count-change-yesterday/#row-count-change-yesterday)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|
|[daily_row_count_change_yesterday](./table/volume/row-count-change-yesterday/#daily-row-count-change-yesterday)|recurring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|
|[daily_partition_row_count_change_yesterday](./table/volume/row-count-change-yesterday/#daily-partition-row-count-change-yesterday)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[row_count_change_7_days](./table/volume/row-count-change-7-days/#row-count-change-7-days)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|
|[daily_row_count_change_7_days](./table/volume/row-count-change-7-days/#daily-row-count-change-7-days)|recurring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|
|[daily_partition_row_count_change_7_days](./table/volume/row-count-change-7-days/#daily-partition-row-count-change-7-days)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[row_count_change_30_days](./table/volume/row-count-change-30-days/#row-count-change-30-days)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|
|[daily_row_count_change_30_days](./table/volume/row-count-change-30-days/#daily-row-count-change-30-days)|recurring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|
|[daily_partition_row_count_change_30_days](./table/volume/row-count-change-30-days/#daily-partition-row-count-change-30-days)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_row_count_anomaly_7_days](./table/volume/daily-partition-row-count-anomaly-7-days/#daily-partition-row-count-anomaly-7-days)|partitioned|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 7 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_row_count_anomaly_30_days](./table/volume/daily-partition-row-count-anomaly-30-days/#daily-partition-row-count-anomaly-30-days)|partitioned|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_row_count_anomaly_60_days](./table/volume/daily-partition-row-count-anomaly-60-days/#daily-partition-row-count-anomaly-60-days)|partitioned|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 60 days.|












































## **Column checks**




















###accuracy  


| Check name | Check type | Description |
|------------|------------|-------------|
|[total_sum_match_percent](./column/accuracy/total-sum-match-percent/#total-sum-match-percent)|profiling|Verifies that percentage of the difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number.|
|[daily_total_sum_match_percent](./column/accuracy/total-sum-match-percent/#daily-total-sum-match-percent)|recurring|Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_sum_match_percent](./column/accuracy/total-sum-match-percent/#monthly-total-sum-match-percent)|recurring|Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[total_min_match_percent](./column/accuracy/total-min-match-percent/#total-min-match-percent)|profiling|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number.|
|[daily_total_min_match_percent](./column/accuracy/total-min-match-percent/#daily-total-min-match-percent)|recurring|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_min_match_percent](./column/accuracy/total-min-match-percent/#monthly-total-min-match-percent)|recurring|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[total_max_match_percent](./column/accuracy/total-max-match-percent/#total-max-match-percent)|profiling|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number.|
|[daily_total_max_match_percent](./column/accuracy/total-max-match-percent/#daily-total-max-match-percent)|recurring|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_max_match_percent](./column/accuracy/total-max-match-percent/#monthly-total-max-match-percent)|recurring|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[total_average_match_percent](./column/accuracy/total-average-match-percent/#total-average-match-percent)|profiling|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number.|
|[daily_total_average_match_percent](./column/accuracy/total-average-match-percent/#daily-total-average-match-percent)|recurring|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_average_match_percent](./column/accuracy/total-average-match-percent/#monthly-total-average-match-percent)|recurring|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[total_not_null_count_match_percent](./column/accuracy/total-not-null-count-match-percent/#total-not-null-count-match-percent)|profiling|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[daily_total_not_null_count_match_percent](./column/accuracy/total-not-null-count-match-percent/#daily-total-not-null-count-match-percent)|recurring|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_not_null_count_match_percent](./column/accuracy/total-not-null-count-match-percent/#monthly-total-not-null-count-match-percent)|recurring|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|





###anomaly  
Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.

| Check name | Check type | Description |
|------------|------------|-------------|
|[mean_anomaly_7_days](./column/anomaly/mean-anomaly-7-days/#mean-anomaly-7-days)|profiling|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 7 days.|
|[daily_mean_anomaly_7_days](./column/anomaly/mean-anomaly-7-days/#daily-mean-anomaly-7-days)|recurring|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 7 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[mean_anomaly_30_days](./column/anomaly/mean-anomaly-30-days/#mean-anomaly-30-days)|profiling|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_mean_anomaly_30_days](./column/anomaly/mean-anomaly-30-days/#daily-mean-anomaly-30-days)|recurring|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[mean_anomaly_60_days](./column/anomaly/mean-anomaly-60-days/#mean-anomaly-60-days)|profiling|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 60 days.|
|[daily_mean_anomaly_60_days](./column/anomaly/mean-anomaly-60-days/#daily-mean-anomaly-60-days)|recurring|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 60 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[median_anomaly_7_days](./column/anomaly/median-anomaly-7-days/#median-anomaly-7-days)|profiling|Verifies that the median in a column changes in a rate within a percentile boundary during last 7 days.|
|[daily_median_anomaly_7_days](./column/anomaly/median-anomaly-7-days/#daily-median-anomaly-7-days)|recurring|Verifies that the median in a column changes in a rate within a percentile boundary during last 7 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[median_anomaly_30_days](./column/anomaly/median-anomaly-30-days/#median-anomaly-30-days)|profiling|Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_median_anomaly_30_days](./column/anomaly/median-anomaly-30-days/#daily-median-anomaly-30-days)|recurring|Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[median_anomaly_60_days](./column/anomaly/median-anomaly-60-days/#median-anomaly-60-days)|profiling|Verifies that the median in a column changes in a rate within a percentile boundary during last 60 days.|
|[daily_median_anomaly_60_days](./column/anomaly/median-anomaly-60-days/#daily-median-anomaly-60-days)|recurring|Verifies that the median in a column changes in a rate within a percentile boundary during last 60 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sum_anomaly_7_days](./column/anomaly/sum-anomaly-7-days/#sum-anomaly-7-days)|profiling|Verifies that the sum in a column changes in a rate within a percentile boundary during last 7 days.|
|[daily_sum_anomaly_7_days](./column/anomaly/sum-anomaly-7-days/#daily-sum-anomaly-7-days)|recurring|Verifies that the sum in a column changes in a rate within a percentile boundary during last 7 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sum_anomaly_30_days](./column/anomaly/sum-anomaly-30-days/#sum-anomaly-30-days)|profiling|Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_sum_anomaly_30_days](./column/anomaly/sum-anomaly-30-days/#daily-sum-anomaly-30-days)|recurring|Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sum_anomaly_60_days](./column/anomaly/sum-anomaly-60-days/#sum-anomaly-60-days)|profiling|Verifies that the sum in a column changes in a rate within a percentile boundary during last 60 days.|
|[daily_sum_anomaly_60_days](./column/anomaly/sum-anomaly-60-days/#daily-sum-anomaly-60-days)|recurring|Verifies that the sum in a column changes in a rate within a percentile boundary during last 60 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[mean_change](./column/anomaly/mean-change/#mean-change)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[daily_mean_change](./column/anomaly/mean-change/#daily-mean-change)|recurring|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[monthly_mean_change](./column/anomaly/mean-change/#monthly-mean-change)|recurring|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[daily_partition_mean_change](./column/anomaly/mean-change/#daily-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[monthly_partition_mean_change](./column/anomaly/mean-change/#monthly-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[mean_change_yesterday](./column/anomaly/mean-change-yesterday/#mean-change-yesterday)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_mean_change_yesterday](./column/anomaly/mean-change-yesterday/#daily-mean-change-yesterday)|recurring|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_mean_change_yesterday](./column/anomaly/mean-change-yesterday/#daily-partition-mean-change-yesterday)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[mean_change_7_days](./column/anomaly/mean-change-7-days/#mean-change-7-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|
|[daily_mean_change_7_days](./column/anomaly/mean-change-7-days/#daily-mean-change-7-days)|recurring|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_mean_change_7_days](./column/anomaly/mean-change-7-days/#daily-partition-mean-change-7-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[mean_change_30_days](./column/anomaly/mean-change-30-days/#mean-change-30-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|
|[daily_mean_change_30_days](./column/anomaly/mean-change-30-days/#daily-mean-change-30-days)|recurring|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_mean_change_30_days](./column/anomaly/mean-change-30-days/#daily-partition-mean-change-30-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[median_change](./column/anomaly/median-change/#median-change)|profiling|Verifies that the median in a column changed in a fixed rate since last readout.|
|[daily_median_change](./column/anomaly/median-change/#daily-median-change)|recurring|Verifies that the median in a column changed in a fixed rate since last readout.|
|[monthly_median_change](./column/anomaly/median-change/#monthly-median-change)|recurring|Verifies that the median in a column changed in a fixed rate since last readout.|
|[daily_partition_median_change](./column/anomaly/median-change/#daily-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout.|
|[monthly_partition_median_change](./column/anomaly/median-change/#monthly-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[median_change_yesterday](./column/anomaly/median-change-yesterday/#median-change-yesterday)|profiling|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|
|[daily_median_change_yesterday](./column/anomaly/median-change-yesterday/#daily-median-change-yesterday)|recurring|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_median_change_yesterday](./column/anomaly/median-change-yesterday/#daily-partition-median-change-yesterday)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[median_change_7_days](./column/anomaly/median-change-7-days/#median-change-7-days)|profiling|Verifies that the median in a column changed in a fixed rate since last readout from last week.|
|[daily_median_change_7_days](./column/anomaly/median-change-7-days/#daily-median-change-7-days)|recurring|Verifies that the median in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_median_change_7_days](./column/anomaly/median-change-7-days/#daily-partition-median-change-7-days)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[median_change_30_days](./column/anomaly/median-change-30-days/#median-change-30-days)|profiling|Verifies that the median in a column changed in a fixed rate since last readout from last month.|
|[daily_median_change_30_days](./column/anomaly/median-change-30-days/#daily-median-change-30-days)|recurring|Verifies that the median in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_median_change_30_days](./column/anomaly/median-change-30-days/#daily-partition-median-change-30-days)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sum_change](./column/anomaly/sum-change/#sum-change)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[daily_sum_change](./column/anomaly/sum-change/#daily-sum-change)|recurring|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[monthly_sum_change](./column/anomaly/sum-change/#monthly-sum-change)|recurring|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[daily_partition_sum_change](./column/anomaly/sum-change/#daily-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[monthly_partition_sum_change](./column/anomaly/sum-change/#monthly-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sum_change_yesterday](./column/anomaly/sum-change-yesterday/#sum-change-yesterday)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|
|[daily_sum_change_yesterday](./column/anomaly/sum-change-yesterday/#daily-sum-change-yesterday)|recurring|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_sum_change_yesterday](./column/anomaly/sum-change-yesterday/#daily-partition-sum-change-yesterday)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sum_change_7_days](./column/anomaly/sum-change-7-days/#sum-change-7-days)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|
|[daily_sum_change_7_days](./column/anomaly/sum-change-7-days/#daily-sum-change-7-days)|recurring|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_sum_change_7_days](./column/anomaly/sum-change-7-days/#daily-partition-sum-change-7-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sum_change_30_days](./column/anomaly/sum-change-30-days/#sum-change-30-days)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|
|[daily_sum_change_30_days](./column/anomaly/sum-change-30-days/#daily-sum-change-30-days)|recurring|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_sum_change_30_days](./column/anomaly/sum-change-30-days/#daily-partition-sum-change-30-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_mean_anomaly_7_days](./column/anomaly/daily-partition-mean-anomaly-7-days/#daily-partition-mean-anomaly-7-days)|partitioned|Verifies that the mean value in a column is within a percentile from measurements made during the last 7 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_mean_anomaly_30_days](./column/anomaly/daily-partition-mean-anomaly-30-days/#daily-partition-mean-anomaly-30-days)|partitioned|Verifies that the mean value in a column is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_mean_anomaly_60_days](./column/anomaly/daily-partition-mean-anomaly-60-days/#daily-partition-mean-anomaly-60-days)|partitioned|Verifies that the mean value in a column is within a percentile from measurements made during the last 60 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_median_anomaly_7_days](./column/anomaly/daily-partition-median-anomaly-7-days/#daily-partition-median-anomaly-7-days)|partitioned|Verifies that the median in a column is within a percentile from measurements made during the last 7 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_median_anomaly_30_days](./column/anomaly/daily-partition-median-anomaly-30-days/#daily-partition-median-anomaly-30-days)|partitioned|Verifies that the median in a column is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_median_anomaly_60_days](./column/anomaly/daily-partition-median-anomaly-60-days/#daily-partition-median-anomaly-60-days)|partitioned|Verifies that the median in a column is within a percentile from measurements made during the last 60 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_sum_anomaly_7_days](./column/anomaly/daily-partition-sum-anomaly-7-days/#daily-partition-sum-anomaly-7-days)|partitioned|Verifies that the sum in a column is within a percentile from measurements made during the last 7 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_sum_anomaly_30_days](./column/anomaly/daily-partition-sum-anomaly-30-days/#daily-partition-sum-anomaly-30-days)|partitioned|Verifies that the sum in a column is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_sum_anomaly_60_days](./column/anomaly/daily-partition-sum-anomaly-60-days/#daily-partition-sum-anomaly-60-days)|partitioned|Verifies that the sum in a column is within a percentile from measurements made during the last 60 days.|





###bool  
Calculates the percentage of data in a Boolean format.

| Check name | Check type | Description |
|------------|------------|-------------|
|[true_percent](./column/bool/true-percent/#true-percent)|profiling|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage.|
|[daily_true_percent](./column/bool/true-percent/#daily-true-percent)|recurring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_true_percent](./column/bool/true-percent/#monthly-true-percent)|recurring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_true_percent](./column/bool/true-percent/#daily-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_true_percent](./column/bool/true-percent/#monthly-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[false_percent](./column/bool/false-percent/#false-percent)|profiling|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage.|
|[daily_false_percent](./column/bool/false-percent/#daily-false-percent)|recurring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_false_percent](./column/bool/false-percent/#monthly-false-percent)|recurring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_false_percent](./column/bool/false-percent/#daily-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_false_percent](./column/bool/false-percent/#monthly-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





###consistency  


| Check name | Check type | Description |
|------------|------------|-------------|
|[date_match_format_percent](./column/consistency/date-match-format-percent/#date-match-format-percent)|profiling|Verifies that the percentage of date values matching the given format in a column does not exceed the minimum accepted percentage.|
|[daily_date_match_format_percent](./column/consistency/date-match-format-percent/#daily-date-match-format-percent)|recurring|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily recurring.|
|[monthly_date_match_format_percent](./column/consistency/date-match-format-percent/#monthly-date-match-format-percent)|recurring|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly recurring.|
|[daily_partition_date_match_format_percent](./column/consistency/date-match-format-percent/#daily-partition-date-match-format-percent)|partitioned|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_date_match_format_percent](./column/consistency/date-match-format-percent/#monthly-partition-date-match-format-percent)|partitioned|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_datatype_changed](./column/consistency/string-datatype-changed/#string-datatype-changed)|profiling|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|
|[daily_string_datatype_changed](./column/consistency/string-datatype-changed/#daily-string-datatype-changed)|recurring|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_datatype_changed](./column/consistency/string-datatype-changed/#monthly-string-datatype-changed)|recurring|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[daily_partition_string_datatype_changed](./column/consistency/string-datatype-changed/#daily-partition-string-datatype-changed)|partitioned|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_datatype_changed](./column/consistency/string-datatype-changed/#monthly-partition-string-datatype-changed)|partitioned|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each monthly partition.|





###datetime  
Validates that the data in a date or time column is in the expected format and within predefined ranges.

| Check name | Check type | Description |
|------------|------------|-------------|
|[date_values_in_future_percent](./column/datetime/date-values-in-future-percent/#date-values-in-future-percent)|profiling|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage.|
|[daily_date_values_in_future_percent](./column/datetime/date-values-in-future-percent/#daily-date-values-in-future-percent)|recurring|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_date_values_in_future_percent](./column/datetime/date-values-in-future-percent/#monthly-date-values-in-future-percent)|recurring|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_date_values_in_future_percent](./column/datetime/date-values-in-future-percent/#daily-partition-date-values-in-future-percent)|partitioned|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_date_values_in_future_percent](./column/datetime/date-values-in-future-percent/#monthly-partition-date-values-in-future-percent)|partitioned|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[datetime_value_in_range_date_percent](./column/datetime/datetime-value-in-range-date-percent/#datetime-value-in-range-date-percent)|profiling|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage.|
|[daily_datetime_value_in_range_date_percent](./column/datetime/datetime-value-in-range-date-percent/#daily-datetime-value-in-range-date-percent)|recurring|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_datetime_value_in_range_date_percent](./column/datetime/datetime-value-in-range-date-percent/#monthly-datetime-value-in-range-date-percent)|recurring|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_datetime_value_in_range_date_percent](./column/datetime/datetime-value-in-range-date-percent/#daily-partition-datetime-value-in-range-date-percent)|partitioned|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_datetime_value_in_range_date_percent](./column/datetime/datetime-value-in-range-date-percent/#monthly-partition-datetime-value-in-range-date-percent)|partitioned|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





###integrity  
Checks the referential integrity of a column against a column in another table.

| Check name | Check type | Description |
|------------|------------|-------------|
|[foreign_key_not_match_count](./column/integrity/foreign-key-not-match-count/#foreign-key-not-match-count)|profiling|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count.|
|[daily_foreign_key_not_match_count](./column/integrity/foreign-key-not-match-count/#daily-foreign-key-not-match-count)|recurring|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_foreign_key_not_match_count](./column/integrity/foreign-key-not-match-count/#monthly-foreign-key-not-match-count)|recurring|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_foreign_key_not_match_count](./column/integrity/foreign-key-not-match-count/#daily-partition-foreign-key-not-match-count)|partitioned|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_foreign_key_not_match_count](./column/integrity/foreign-key-not-match-count/#monthly-partition-foreign-key-not-match-count)|partitioned|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[foreign_key_match_percent](./column/integrity/foreign-key-match-percent/#foreign-key-match-percent)|profiling|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count.|
|[daily_foreign_key_match_percent](./column/integrity/foreign-key-match-percent/#daily-foreign-key-match-percent)|recurring|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_foreign_key_match_percent](./column/integrity/foreign-key-match-percent/#monthly-foreign-key-match-percent)|recurring|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_foreign_key_match_percent](./column/integrity/foreign-key-match-percent/#daily-partition-foreign-key-match-percent)|partitioned|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_foreign_key_match_percent](./column/integrity/foreign-key-match-percent/#monthly-partition-foreign-key-match-percent)|partitioned|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|





###nulls  
Checks for the presence of null or missing values in a column.

| Check name | Check type | Description |
|------------|------------|-------------|
|[nulls_count](./column/nulls/nulls-count/#nulls-count)|profiling|Verifies that the number of null values in a column does not exceed the maximum accepted count.|
|[daily_nulls_count](./column/nulls/nulls-count/#daily-nulls-count)|recurring|Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_nulls_count](./column/nulls/nulls-count/#monthly-nulls-count)|recurring|Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_nulls_count](./column/nulls/nulls-count/#daily-partition-nulls-count)|partitioned|Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_nulls_count](./column/nulls/nulls-count/#monthly-partition-nulls-count)|partitioned|Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[nulls_percent](./column/nulls/nulls-percent/#nulls-percent)|profiling|Verifies that the percent of null values in a column does not exceed the maximum accepted percentage.|
|[daily_nulls_percent](./column/nulls/nulls-percent/#daily-nulls-percent)|recurring|Verifies that the percentage of nulls in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_nulls_percent](./column/nulls/nulls-percent/#monthly-nulls-percent)|recurring|Verifies that the percentage of null values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_nulls_percent](./column/nulls/nulls-percent/#daily-partition-nulls-percent)|partitioned|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_nulls_percent](./column/nulls/nulls-percent/#monthly-partition-nulls-percent)|partitioned|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[not_nulls_count](./column/nulls/not-nulls-count/#not-nulls-count)|profiling|Verifies that the number of not null values in a column does not exceed the minimum accepted count.|
|[daily_not_nulls_count](./column/nulls/not-nulls-count/#daily-not-nulls-count)|recurring|Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_not_nulls_count](./column/nulls/not-nulls-count/#monthly-not-nulls-count)|recurring|Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_not_nulls_count](./column/nulls/not-nulls-count/#daily-partition-not-nulls-count)|partitioned|Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_not_nulls_count](./column/nulls/not-nulls-count/#monthly-partition-not-nulls-count)|partitioned|Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[not_nulls_percent](./column/nulls/not-nulls-percent/#not-nulls-percent)|profiling|Verifies that the percent of not null values in a column does not exceed the minimum accepted percentage.|
|[daily_not_nulls_percent](./column/nulls/not-nulls-percent/#daily-not-nulls-percent)|recurring|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_not_nulls_percent](./column/nulls/not-nulls-percent/#monthly-not-nulls-percent)|recurring|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_not_nulls_percent](./column/nulls/not-nulls-percent/#daily-partition-not-nulls-percent)|partitioned|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_not_nulls_percent](./column/nulls/not-nulls-percent/#monthly-partition-not-nulls-percent)|partitioned|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





###numeric  
Validates that the data in a numeric column is in the expected format or within predefined ranges.

| Check name | Check type | Description |
|------------|------------|-------------|
|[negative_count](./column/numeric/negative-count/#negative-count)|profiling|Verifies that the number of negative values in a column does not exceed the maximum accepted count.|
|[daily_negative_count](./column/numeric/negative-count/#daily-negative-count)|recurring|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_negative_count](./column/numeric/negative-count/#monthly-negative-count)|recurring|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_negative_count](./column/numeric/negative-count/#daily-partition-negative-count)|partitioned|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_negative_count](./column/numeric/negative-count/#monthly-partition-negative-count)|partitioned|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[negative_percent](./column/numeric/negative-percent/#negative-percent)|profiling|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.|
|[daily_negative_percent](./column/numeric/negative-percent/#daily-negative-percent)|recurring|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_negative_percent](./column/numeric/negative-percent/#monthly-negative-percent)|recurring|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_negative_percent](./column/numeric/negative-percent/#daily-partition-negative-percent)|partitioned|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_negative_percent](./column/numeric/negative-percent/#monthly-partition-negative-percent)|partitioned|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[non_negative_count](./column/numeric/non-negative-count/#non-negative-count)|profiling|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count.|
|[daily_non_negative_count](./column/numeric/non-negative-count/#daily-non-negative-count)|recurring|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_non_negative_count](./column/numeric/non-negative-count/#monthly-non-negative-count)|recurring|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_non_negative_count](./column/numeric/non-negative-count/#daily-partition-non-negative-count)|partitioned|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_non_negative_count](./column/numeric/non-negative-count/#monthly-partition-non-negative-count)|partitioned|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[non_negative_percent](./column/numeric/non-negative-percent/#non-negative-percent)|profiling|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage.|
|[daily_non_negative_percent](./column/numeric/non-negative-percent/#daily-non-negative-percent)|recurring|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_non_negative_percent](./column/numeric/non-negative-percent/#monthly-non-negative-percent)|recurring|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_non_negative_percent](./column/numeric/non-negative-percent/#daily-partition-non-negative-percent)|partitioned|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_non_negative_percent](./column/numeric/non-negative-percent/#monthly-partition-non-negative-percent)|partitioned|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[expected_numbers_in_use_count](./column/numeric/expected-numbers-in-use-count/#expected-numbers-in-use-count)|profiling|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|
|[daily_expected_numbers_in_use_count](./column/numeric/expected-numbers-in-use-count/#daily-expected-numbers-in-use-count)|recurring|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_expected_numbers_in_use_count](./column/numeric/expected-numbers-in-use-count/#monthly-expected-numbers-in-use-count)|recurring|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_expected_numbers_in_use_count](./column/numeric/expected-numbers-in-use-count/#daily-partition-expected-numbers-in-use-count)|partitioned|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_expected_numbers_in_use_count](./column/numeric/expected-numbers-in-use-count/#monthly-partition-expected-numbers-in-use-count)|partitioned|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[number_value_in_set_percent](./column/numeric/number-value-in-set-percent/#number-value-in-set-percent)|profiling|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|
|[daily_number_value_in_set_percent](./column/numeric/number-value-in-set-percent/#daily-number-value-in-set-percent)|recurring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_number_value_in_set_percent](./column/numeric/number-value-in-set-percent/#monthly-number-value-in-set-percent)|recurring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_number_value_in_set_percent](./column/numeric/number-value-in-set-percent/#daily-partition-number-value-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_number_value_in_set_percent](./column/numeric/number-value-in-set-percent/#monthly-partition-number-value-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[values_in_range_numeric_percent](./column/numeric/values-in-range-numeric-percent/#values-in-range-numeric-percent)|profiling|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|
|[daily_values_in_range_numeric_percent](./column/numeric/values-in-range-numeric-percent/#daily-values-in-range-numeric-percent)|recurring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_values_in_range_numeric_percent](./column/numeric/values-in-range-numeric-percent/#monthly-values-in-range-numeric-percent)|recurring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_values_in_range_numeric_percent](./column/numeric/values-in-range-numeric-percent/#daily-partition-values-in-range-numeric-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_values_in_range_numeric_percent](./column/numeric/values-in-range-numeric-percent/#monthly-partition-values-in-range-numeric-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[values_in_range_integers_percent](./column/numeric/values-in-range-integers-percent/#values-in-range-integers-percent)|profiling|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|
|[daily_values_in_range_integers_percent](./column/numeric/values-in-range-integers-percent/#daily-values-in-range-integers-percent)|recurring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_values_in_range_integers_percent](./column/numeric/values-in-range-integers-percent/#monthly-values-in-range-integers-percent)|recurring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_values_in_range_integers_percent](./column/numeric/values-in-range-integers-percent/#daily-partition-values-in-range-integers-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_values_in_range_integers_percent](./column/numeric/values-in-range-integers-percent/#monthly-partition-values-in-range-integers-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[value_below_min_value_count](./column/numeric/value-below-min-value-count/#value-below-min-value-count)|profiling|The check counts the number of values in the column that is below the value defined by the user as a parameter.|
|[daily_value_below_min_value_count](./column/numeric/value-below-min-value-count/#daily-value-below-min-value-count)|recurring|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_value_below_min_value_count](./column/numeric/value-below-min-value-count/#monthly-value-below-min-value-count)|recurring|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_below_min_value_count](./column/numeric/value-below-min-value-count/#daily-partition-value-below-min-value-count)|partitioned|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_below_min_value_count](./column/numeric/value-below-min-value-count/#monthly-partition-value-below-min-value-count)|partitioned|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[value_below_min_value_percent](./column/numeric/value-below-min-value-percent/#value-below-min-value-percent)|profiling|The check counts the percentage of values in the column that is below the value defined by the user as a parameter.|
|[daily_value_below_min_value_percent](./column/numeric/value-below-min-value-percent/#daily-value-below-min-value-percent)|recurring|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_value_below_min_value_percent](./column/numeric/value-below-min-value-percent/#monthly-value-below-min-value-percent)|recurring|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_below_min_value_percent](./column/numeric/value-below-min-value-percent/#daily-partition-value-below-min-value-percent)|partitioned|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_below_min_value_percent](./column/numeric/value-below-min-value-percent/#monthly-partition-value-below-min-value-percent)|partitioned|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[value_above_max_value_count](./column/numeric/value-above-max-value-count/#value-above-max-value-count)|profiling|The check counts the number of values in the column that is above the value defined by the user as a parameter.|
|[daily_value_above_max_value_count](./column/numeric/value-above-max-value-count/#daily-value-above-max-value-count)|recurring|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_value_above_max_value_count](./column/numeric/value-above-max-value-count/#monthly-value-above-max-value-count)|recurring|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_above_max_value_count](./column/numeric/value-above-max-value-count/#daily-partition-value-above-max-value-count)|partitioned|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_above_max_value_count](./column/numeric/value-above-max-value-count/#monthly-partition-value-above-max-value-count)|partitioned|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[value_above_max_value_percent](./column/numeric/value-above-max-value-percent/#value-above-max-value-percent)|profiling|The check counts the percentage of values in the column that is above the value defined by the user as a parameter.|
|[daily_value_above_max_value_percent](./column/numeric/value-above-max-value-percent/#daily-value-above-max-value-percent)|recurring|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_value_above_max_value_percent](./column/numeric/value-above-max-value-percent/#monthly-value-above-max-value-percent)|recurring|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_above_max_value_percent](./column/numeric/value-above-max-value-percent/#daily-partition-value-above-max-value-percent)|partitioned|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_above_max_value_percent](./column/numeric/value-above-max-value-percent/#monthly-partition-value-above-max-value-percent)|partitioned|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[max_in_range](./column/numeric/max-in-range/#max-in-range)|profiling|Verifies that the maximal value in a column is not outside the set range.|
|[daily_max_in_range](./column/numeric/max-in-range/#daily-max-in-range)|recurring|Verifies that the maximal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_max_in_range](./column/numeric/max-in-range/#monthly-max-in-range)|recurring|Verifies that the maximal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_max_in_range](./column/numeric/max-in-range/#daily-partition-max-in-range)|partitioned|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_max_in_range](./column/numeric/max-in-range/#monthly-partition-max-in-range)|partitioned|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[min_in_range](./column/numeric/min-in-range/#min-in-range)|profiling|Verifies that the minimal value in a column is not outside the set range.|
|[daily_min_in_range](./column/numeric/min-in-range/#daily-min-in-range)|recurring|Verifies that the minimal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_min_in_range](./column/numeric/min-in-range/#monthly-min-in-range)|recurring|Verifies that the minimal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_min_in_range](./column/numeric/min-in-range/#daily-partition-min-in-range)|partitioned|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_min_in_range](./column/numeric/min-in-range/#monthly-partition-min-in-range)|partitioned|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[mean_in_range](./column/numeric/mean-in-range/#mean-in-range)|profiling|Verifies that the average (mean) of all values in a column is not outside the set range.|
|[daily_mean_in_range](./column/numeric/mean-in-range/#daily-mean-in-range)|recurring|Verifies that the average (mean) of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_mean_in_range](./column/numeric/mean-in-range/#monthly-mean-in-range)|recurring|Verifies that the average (mean) of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_mean_in_range](./column/numeric/mean-in-range/#daily-partition-mean-in-range)|partitioned|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_mean_in_range](./column/numeric/mean-in-range/#monthly-partition-mean-in-range)|partitioned|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[percentile_in_range](./column/numeric/percentile-in-range/#percentile-in-range)|profiling|Verifies that the percentile of all values in a column is not outside the set range.|
|[median_in_range](./column/numeric/percentile-in-range/#median-in-range)|profiling|Verifies that the median of all values in a column is not outside the set range.|
|[percentile_10_in_range](./column/numeric/percentile-in-range/#percentile-10-in-range)|profiling|Verifies that the percentile 10 of all values in a column is not outside the set range.|
|[percentile_25_in_range](./column/numeric/percentile-in-range/#percentile-25-in-range)|profiling|Verifies that the percentile 25 of all values in a column is not outside the set range.|
|[percentile_75_in_range](./column/numeric/percentile-in-range/#percentile-75-in-range)|profiling|Verifies that the percentile 75 of all values in a column is not outside the set range.|
|[percentile_90_in_range](./column/numeric/percentile-in-range/#percentile-90-in-range)|profiling|Verifies that the percentile 90 of all values in a column is not outside the set range.|
|[daily_percentile_in_range](./column/numeric/percentile-in-range/#daily-percentile-in-range)|recurring|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[daily_median_in_range](./column/numeric/percentile-in-range/#daily-median-in-range)|recurring|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_percentile_10_in_range](./column/numeric/percentile-in-range/#daily-percentile-10-in-range)|recurring|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[daily_percentile_25_in_range](./column/numeric/percentile-in-range/#daily-percentile-25-in-range)|recurring|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[daily_percentile_75_in_range](./column/numeric/percentile-in-range/#daily-percentile-75-in-range)|recurring|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[daily_percentile_90_in_range](./column/numeric/percentile-in-range/#daily-percentile-90-in-range)|recurring|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_percentile_in_range](./column/numeric/percentile-in-range/#monthly-percentile-in-range)|recurring|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[monthly_median_in_range](./column/numeric/percentile-in-range/#monthly-median-in-range)|recurring|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[monthly_percentile_10_in_range](./column/numeric/percentile-in-range/#monthly-percentile-10-in-range)|recurring|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[monthly_percentile_25_in_range](./column/numeric/percentile-in-range/#monthly-percentile-25-in-range)|recurring|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[monthly_percentile_75_in_range](./column/numeric/percentile-in-range/#monthly-percentile-75-in-range)|recurring|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[monthly_percentile_90_in_range](./column/numeric/percentile-in-range/#monthly-percentile-90-in-range)|recurring|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_percentile_in_range](./column/numeric/percentile-in-range/#daily-partition-percentile-in-range)|partitioned|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[daily_partition_median_in_range](./column/numeric/percentile-in-range/#daily-partition-median-in-range)|partitioned|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[daily_partition_percentile_10_in_range](./column/numeric/percentile-in-range/#daily-partition-percentile-10-in-range)|partitioned|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[daily_partition_percentile_25_in_range](./column/numeric/percentile-in-range/#daily-partition-percentile-25-in-range)|partitioned|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[daily_partition_percentile_75_in_range](./column/numeric/percentile-in-range/#daily-partition-percentile-75-in-range)|partitioned|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[daily_partition_percentile_90_in_range](./column/numeric/percentile-in-range/#daily-partition-percentile-90-in-range)|partitioned|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_percentile_in_range](./column/numeric/percentile-in-range/#monthly-partition-percentile-in-range)|partitioned|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|
|[monthly_partition_median_in_range](./column/numeric/percentile-in-range/#monthly-partition-median-in-range)|partitioned|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|
|[monthly_partition_percentile_10_in_range](./column/numeric/percentile-in-range/#monthly-partition-percentile-10-in-range)|partitioned|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|
|[monthly_partition_percentile_25_in_range](./column/numeric/percentile-in-range/#monthly-partition-percentile-25-in-range)|partitioned|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|
|[monthly_partition_percentile_75_in_range](./column/numeric/percentile-in-range/#monthly-partition-percentile-75-in-range)|partitioned|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|
|[monthly_partition_percentile_90_in_range](./column/numeric/percentile-in-range/#monthly-partition-percentile-90-in-range)|partitioned|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sample_stddev_in_range](./column/numeric/sample-stddev-in-range/#sample-stddev-in-range)|profiling|Verifies that the sample standard deviation of all values in a column is not outside the set range.|
|[daily_sample_stddev_in_range](./column/numeric/sample-stddev-in-range/#daily-sample-stddev-in-range)|recurring|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sample_stddev_in_range](./column/numeric/sample-stddev-in-range/#monthly-sample-stddev-in-range)|recurring|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sample_stddev_in_range](./column/numeric/sample-stddev-in-range/#daily-partition-sample-stddev-in-range)|partitioned|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sample_stddev_in_range](./column/numeric/sample-stddev-in-range/#monthly-partition-sample-stddev-in-range)|partitioned|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[population_stddev_in_range](./column/numeric/population-stddev-in-range/#population-stddev-in-range)|profiling|Verifies that the population standard deviation of all values in a column is not outside the set range.|
|[daily_population_stddev_in_range](./column/numeric/population-stddev-in-range/#daily-population-stddev-in-range)|recurring|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_population_stddev_in_range](./column/numeric/population-stddev-in-range/#monthly-population-stddev-in-range)|recurring|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_population_stddev_in_range](./column/numeric/population-stddev-in-range/#daily-partition-population-stddev-in-range)|partitioned|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_population_stddev_in_range](./column/numeric/population-stddev-in-range/#monthly-partition-population-stddev-in-range)|partitioned|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sample_variance_in_range](./column/numeric/sample-variance-in-range/#sample-variance-in-range)|profiling|Verifies that the sample variance of all values in a column is not outside the set range.|
|[daily_sample_variance_in_range](./column/numeric/sample-variance-in-range/#daily-sample-variance-in-range)|recurring|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sample_variance_in_range](./column/numeric/sample-variance-in-range/#monthly-sample-variance-in-range)|recurring|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sample_variance_in_range](./column/numeric/sample-variance-in-range/#daily-partition-sample-variance-in-range)|partitioned|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sample_variance_in_range](./column/numeric/sample-variance-in-range/#monthly-partition-sample-variance-in-range)|partitioned|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[population_variance_in_range](./column/numeric/population-variance-in-range/#population-variance-in-range)|profiling|Verifies that the population variance of all values in a column is not outside the set range.|
|[daily_population_variance_in_range](./column/numeric/population-variance-in-range/#daily-population-variance-in-range)|recurring|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_population_variance_in_range](./column/numeric/population-variance-in-range/#monthly-population-variance-in-range)|recurring|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_population_variance_in_range](./column/numeric/population-variance-in-range/#daily-partition-population-variance-in-range)|partitioned|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_population_variance_in_range](./column/numeric/population-variance-in-range/#monthly-partition-population-variance-in-range)|partitioned|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sum_in_range](./column/numeric/sum-in-range/#sum-in-range)|profiling|Verifies that the sum of all values in a column is not outside the set range.|
|[daily_sum_in_range](./column/numeric/sum-in-range/#daily-sum-in-range)|recurring|Verifies that the sum of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sum_in_range](./column/numeric/sum-in-range/#monthly-sum-in-range)|recurring|Verifies that the sum of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sum_in_range](./column/numeric/sum-in-range/#daily-partition-sum-in-range)|partitioned|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sum_in_range](./column/numeric/sum-in-range/#monthly-partition-sum-in-range)|partitioned|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[invalid_latitude_count](./column/numeric/invalid-latitude-count/#invalid-latitude-count)|profiling|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count.|
|[daily_invalid_latitude_count](./column/numeric/invalid-latitude-count/#daily-invalid-latitude-count)|recurring|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_invalid_latitude_count](./column/numeric/invalid-latitude-count/#monthly-invalid-latitude-count)|recurring|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_invalid_latitude_count](./column/numeric/invalid-latitude-count/#daily-partition-invalid-latitude-count)|partitioned|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_invalid_latitude_count](./column/numeric/invalid-latitude-count/#monthly-partition-invalid-latitude-count)|partitioned|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[valid_latitude_percent](./column/numeric/valid-latitude-percent/#valid-latitude-percent)|profiling|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage.|
|[daily_valid_latitude_percent](./column/numeric/valid-latitude-percent/#daily-valid-latitude-percent)|recurring|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_valid_latitude_percent](./column/numeric/valid-latitude-percent/#monthly-valid-latitude-percent)|recurring|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_latitude_percent](./column/numeric/valid-latitude-percent/#daily-partition-valid-latitude-percent)|partitioned|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_latitude_percent](./column/numeric/valid-latitude-percent/#monthly-partition-valid-latitude-percent)|partitioned|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[invalid_longitude_count](./column/numeric/invalid-longitude-count/#invalid-longitude-count)|profiling|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count.|
|[daily_invalid_longitude_count](./column/numeric/invalid-longitude-count/#daily-invalid-longitude-count)|recurring|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_invalid_longitude_count](./column/numeric/invalid-longitude-count/#monthly-invalid-longitude-count)|recurring|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_invalid_longitude_count](./column/numeric/invalid-longitude-count/#daily-partition-invalid-longitude-count)|partitioned|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_invalid_longitude_count](./column/numeric/invalid-longitude-count/#monthly-partition-invalid-longitude-count)|partitioned|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[valid_longitude_percent](./column/numeric/valid-longitude-percent/#valid-longitude-percent)|profiling|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage.|
|[daily_valid_longitude_percent](./column/numeric/valid-longitude-percent/#daily-valid-longitude-percent)|recurring|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_valid_longitude_percent](./column/numeric/valid-longitude-percent/#monthly-valid-longitude-percent)|recurring|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_longitude_percent](./column/numeric/valid-longitude-percent/#daily-partition-valid-longitude-percent)|partitioned|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_longitude_percent](./column/numeric/valid-longitude-percent/#monthly-partition-valid-longitude-percent)|partitioned|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





###pii  
Checks for the presence of sensitive or personally identifiable information (PII) in a column such as email, phone, zip code, IP4 and IP6 addresses.

| Check name | Check type | Description |
|------------|------------|-------------|
|[valid_usa_phone_percent](./column/pii/valid-usa-phone-percent/#valid-usa-phone-percent)|profiling|Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage.|
|[daily_valid_usa_phone_percent](./column/pii/valid-usa-phone-percent/#daily-valid-usa-phone-percent)|recurring|Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_valid_usa_phone_percent](./column/pii/valid-usa-phone-percent/#monthly-valid-usa-phone-percent)|recurring|Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_usa_phone_percent](./column/pii/valid-usa-phone-percent/#daily-partition-valid-usa-phone-percent)|partitioned|Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_usa_phone_percent](./column/pii/valid-usa-phone-percent/#monthly-partition-valid-usa-phone-percent)|partitioned|Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[contains_usa_phone_percent](./column/pii/contains-usa-phone-percent/#contains-usa-phone-percent)|profiling|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage.|
|[daily_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent/#daily-contains-usa-phone-percent)|recurring|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent/#monthly-contains-usa-phone-percent)|recurring|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent/#daily-partition-contains-usa-phone-percent)|partitioned|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent/#monthly-partition-contains-usa-phone-percent)|partitioned|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[valid_usa_zipcode_percent](./column/pii/valid-usa-zipcode-percent/#valid-usa-zipcode-percent)|profiling|Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage.|
|[daily_valid_usa_zipcode_percent](./column/pii/valid-usa-zipcode-percent/#daily-valid-usa-zipcode-percent)|recurring|Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_valid_usa_zipcode_percent](./column/pii/valid-usa-zipcode-percent/#monthly-valid-usa-zipcode-percent)|recurring|Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_usa_zipcode_percent](./column/pii/valid-usa-zipcode-percent/#daily-partition-valid-usa-zipcode-percent)|partitioned|Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_usa_zipcode_percent](./column/pii/valid-usa-zipcode-percent/#monthly-partition-valid-usa-zipcode-percent)|partitioned|Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent/#contains-usa-zipcode-percent)|profiling|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.|
|[daily_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent/#daily-contains-usa-zipcode-percent)|recurring|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent/#monthly-contains-usa-zipcode-percent)|recurring|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent/#daily-partition-contains-usa-zipcode-percent)|partitioned|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent/#monthly-partition-contains-usa-zipcode-percent)|partitioned|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[valid_email_percent](./column/pii/valid-email-percent/#valid-email-percent)|profiling|Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage.|
|[daily_valid_email_percent](./column/pii/valid-email-percent/#daily-valid-email-percent)|recurring|Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_valid_email_percent](./column/pii/valid-email-percent/#monthly-valid-email-percent)|recurring|Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_email_percent](./column/pii/valid-email-percent/#daily-partition-valid-email-percent)|partitioned|Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_email_percent](./column/pii/valid-email-percent/#monthly-partition-valid-email-percent)|partitioned|Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[contains_email_percent](./column/pii/contains-email-percent/#contains-email-percent)|profiling|Verifies that the percentage of rows that contains valid emails in a column does not exceed the minimum accepted percentage.|
|[daily_contains_email_percent](./column/pii/contains-email-percent/#daily-contains-email-percent)|recurring|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_email_percent](./column/pii/contains-email-percent/#monthly-contains-email-percent)|recurring|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_email_percent](./column/pii/contains-email-percent/#daily-partition-contains-email-percent)|partitioned|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_email_percent](./column/pii/contains-email-percent/#monthly-partition-contains-email-percent)|partitioned|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[valid_ip4_address_percent](./column/pii/valid-ip4-address-percent/#valid-ip4-address-percent)|profiling|Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage.|
|[daily_valid_ip4_address_percent](./column/pii/valid-ip4-address-percent/#daily-valid-ip4-address-percent)|recurring|Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_valid_ip4_address_percent](./column/pii/valid-ip4-address-percent/#monthly-valid-ip4-address-percent)|recurring|Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_ip4_address_percent](./column/pii/valid-ip4-address-percent/#daily-partition-valid-ip4-address-percent)|partitioned|Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_ip4_address_percent](./column/pii/valid-ip4-address-percent/#monthly-partition-valid-ip4-address-percent)|partitioned|Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[contains_ip4_percent](./column/pii/contains-ip4-percent/#contains-ip4-percent)|profiling|Verifies that the percentage of rows that contains valid IP4 address values in a column does not fall below the minimum accepted percentage.|
|[daily_contains_ip4_percent](./column/pii/contains-ip4-percent/#daily-contains-ip4-percent)|recurring|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_ip4_percent](./column/pii/contains-ip4-percent/#monthly-contains-ip4-percent)|recurring|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_ip4_percent](./column/pii/contains-ip4-percent/#daily-partition-contains-ip4-percent)|partitioned|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_ip4_percent](./column/pii/contains-ip4-percent/#monthly-partition-contains-ip4-percent)|partitioned|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[valid_ip6_address_percent](./column/pii/valid-ip6-address-percent/#valid-ip6-address-percent)|profiling|Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage.|
|[daily_valid_ip6_address_percent](./column/pii/valid-ip6-address-percent/#daily-valid-ip6-address-percent)|recurring|Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_valid_ip6_address_percent](./column/pii/valid-ip6-address-percent/#monthly-valid-ip6-address-percent)|recurring|Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_ip6_address_percent](./column/pii/valid-ip6-address-percent/#daily-partition-valid-ip6-address-percent)|partitioned|Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_ip6_address_percent](./column/pii/valid-ip6-address-percent/#monthly-partition-valid-ip6-address-percent)|partitioned|Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[contains_ip6_percent](./column/pii/contains-ip6-percent/#contains-ip6-percent)|profiling|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage.|
|[daily_contains_ip6_percent](./column/pii/contains-ip6-percent/#daily-contains-ip6-percent)|recurring|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_ip6_percent](./column/pii/contains-ip6-percent/#monthly-contains-ip6-percent)|recurring|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_ip6_percent](./column/pii/contains-ip6-percent/#daily-partition-contains-ip6-percent)|partitioned|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_ip6_percent](./column/pii/contains-ip6-percent/#monthly-partition-contains-ip6-percent)|partitioned|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





###schema  
Detects schema drifts such as a column is missing or the data type has changed.

| Check name | Check type | Description |
|------------|------------|-------------|
|[column_exists](./column/schema/column-exists/#column-exists)|profiling|Checks the metadata of the monitored table and verifies if the column exists.|
|[daily_column_exists](./column/schema/column-exists/#daily-column-exists)|recurring|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each day when the data quality check was evaluated.|
|[monthly_column_exists](./column/schema/column-exists/#monthly-column-exists)|recurring|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[column_type_changed](./column/schema/column-type-changed/#column-type-changed)|profiling|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed.|
|[daily_column_type_changed](./column/schema/column-type-changed/#daily-column-type-changed)|recurring|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last day. Stores the most recent hash for each day when the data quality check was evaluated.|
|[monthly_column_type_changed](./column/schema/column-type-changed/#monthly-column-type-changed)|recurring|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last month. Stores the most recent hash for each month when the data quality check was evaluated.|





###sql  
Validate data against user-defined SQL queries at the column level. Checks in this group allows to validate that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

| Check name | Check type | Description |
|------------|------------|-------------|
|[sql_condition_passed_percent_on_column](./column/sql/sql-condition-passed-percent-on-column/#sql-condition-passed-percent-on-column)|profiling|Verifies that a minimum percentage of rows passed a custom SQL condition (expression).|
|[daily_sql_condition_passed_percent_on_column](./column/sql/sql-condition-passed-percent-on-column/#daily-sql-condition-passed-percent-on-column)|recurring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_condition_passed_percent_on_column](./column/sql/sql-condition-passed-percent-on-column/#monthly-sql-condition-passed-percent-on-column)|recurring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_condition_passed_percent_on_column](./column/sql/sql-condition-passed-percent-on-column/#daily-partition-sql-condition-passed-percent-on-column)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_condition_passed_percent_on_column](./column/sql/sql-condition-passed-percent-on-column/#monthly-partition-sql-condition-passed-percent-on-column)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sql_condition_failed_count_on_column](./column/sql/sql-condition-failed-count-on-column/#sql-condition-failed-count-on-column)|profiling|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count.|
|[daily_sql_condition_failed_count_on_column](./column/sql/sql-condition-failed-count-on-column/#daily-sql-condition-failed-count-on-column)|recurring|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_condition_failed_count_on_column](./column/sql/sql-condition-failed-count-on-column/#monthly-sql-condition-failed-count-on-column)|recurring|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_condition_failed_count_on_column](./column/sql/sql-condition-failed-count-on-column/#daily-partition-sql-condition-failed-count-on-column)|partitioned|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_condition_failed_count_on_column](./column/sql/sql-condition-failed-count-on-column/#monthly-partition-sql-condition-failed-count-on-column)|partitioned|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sql_aggregate_expr_column](./column/sql/sql-aggregate-expr-column/#sql-aggregate-expr-column)|profiling|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|
|[daily_sql_aggregate_expr_column](./column/sql/sql-aggregate-expr-column/#daily-sql-aggregate-expr-column)|recurring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_aggregate_expr_column](./column/sql/sql-aggregate-expr-column/#monthly-sql-aggregate-expr-column)|recurring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_aggregate_expr_column](./column/sql/sql-aggregate-expr-column/#daily-partition-sql-aggregate-expr-column)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_aggregate_expr_column](./column/sql/sql-aggregate-expr-column/#monthly-partition-sql-aggregate-expr-column)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|





###strings  
Validates that the data in a string column match the expected format or pattern.

| Check name | Check type | Description |
|------------|------------|-------------|
|[string_max_length](./column/strings/string-max-length/#string-max-length)|profiling|Verifies that the length of string in a column does not exceed the maximum accepted length.|
|[daily_string_max_length](./column/strings/string-max-length/#daily-string-max-length)|recurring|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_max_length](./column/strings/string-max-length/#monthly-string-max-length)|recurring|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_max_length](./column/strings/string-max-length/#daily-partition-string-max-length)|partitioned|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_max_length](./column/strings/string-max-length/#monthly-partition-string-max-length)|partitioned|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_min_length](./column/strings/string-min-length/#string-min-length)|profiling|Verifies that the length of string in a column does not fall below the minimum accepted length.|
|[daily_string_min_length](./column/strings/string-min-length/#daily-string-min-length)|recurring|Verifies that the length of string in a column does not fall below the minimum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_min_length](./column/strings/string-min-length/#monthly-string-min-length)|recurring|Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_min_length](./column/strings/string-min-length/#daily-partition-string-min-length)|partitioned|Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_min_length](./column/strings/string-min-length/#monthly-partition-string-min-length)|partitioned|Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_mean_length](./column/strings/string-mean-length/#string-mean-length)|profiling|Verifies that the length of string in a column does not exceed the mean accepted length.|
|[daily_string_mean_length](./column/strings/string-mean-length/#daily-string-mean-length)|recurring|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_mean_length](./column/strings/string-mean-length/#monthly-string-mean-length)|recurring|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_mean_length](./column/strings/string-mean-length/#daily-partition-string-mean-length)|partitioned|Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_mean_length](./column/strings/string-mean-length/#monthly-partition-string-mean-length)|partitioned|Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_length_below_min_length_count](./column/strings/string-length-below-min-length-count/#string-length-below-min-length-count)|profiling|The check counts the number of strings in the column that is below the length defined by the user as a parameter.|
|[daily_string_length_below_min_length_count](./column/strings/string-length-below-min-length-count/#daily-string-length-below-min-length-count)|recurring|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_below_min_length_count](./column/strings/string-length-below-min-length-count/#monthly-string-length-below-min-length-count)|recurring|The check counts those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_below_min_length_count](./column/strings/string-length-below-min-length-count/#daily-partition-string-length-below-min-length-count)|partitioned|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_below_min_length_count](./column/strings/string-length-below-min-length-count/#monthly-partition-string-length-below-min-length-count)|partitioned|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_length_below_min_length_percent](./column/strings/string-length-below-min-length-percent/#string-length-below-min-length-percent)|profiling|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter.|
|[daily_string_length_below_min_length_percent](./column/strings/string-length-below-min-length-percent/#daily-string-length-below-min-length-percent)|recurring|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_below_min_length_percent](./column/strings/string-length-below-min-length-percent/#monthly-string-length-below-min-length-percent)|recurring|The check counts percentage of those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_below_min_length_percent](./column/strings/string-length-below-min-length-percent/#daily-partition-string-length-below-min-length-percent)|partitioned|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_below_min_length_percent](./column/strings/string-length-below-min-length-percent/#monthly-partition-string-length-below-min-length-percent)|partitioned|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_length_above_max_length_count](./column/strings/string-length-above-max-length-count/#string-length-above-max-length-count)|profiling|The check counts the number of strings in the column that is above the length defined by the user as a parameter.|
|[daily_string_length_above_max_length_count](./column/strings/string-length-above-max-length-count/#daily-string-length-above-max-length-count)|recurring|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_above_max_length_count](./column/strings/string-length-above-max-length-count/#monthly-string-length-above-max-length-count)|recurring|The check counts those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_above_max_length_count](./column/strings/string-length-above-max-length-count/#daily-partition-string-length-above-max-length-count)|partitioned|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_above_max_length_count](./column/strings/string-length-above-max-length-count/#monthly-partition-string-length-above-max-length-count)|partitioned|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_length_above_max_length_percent](./column/strings/string-length-above-max-length-percent/#string-length-above-max-length-percent)|profiling|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter.|
|[daily_string_length_above_max_length_percent](./column/strings/string-length-above-max-length-percent/#daily-string-length-above-max-length-percent)|recurring|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_above_max_length_percent](./column/strings/string-length-above-max-length-percent/#monthly-string-length-above-max-length-percent)|recurring|The check counts percentage of those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_above_max_length_percent](./column/strings/string-length-above-max-length-percent/#daily-partition-string-length-above-max-length-percent)|partitioned|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_above_max_length_percent](./column/strings/string-length-above-max-length-percent/#monthly-partition-string-length-above-max-length-percent)|partitioned|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_length_in_range_percent](./column/strings/string-length-in-range-percent/#string-length-in-range-percent)|profiling|The check counts the percentage of those strings with length in the range provided by the user in the column. |
|[daily_string_length_in_range_percent](./column/strings/string-length-in-range-percent/#daily-string-length-in-range-percent)|recurring|The check counts the percentage of those strings with length in the range provided by the user in the column. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_in_range_percent](./column/strings/string-length-in-range-percent/#monthly-string-length-in-range-percent)|recurring|The check counts percentage of those strings with length in the range provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_in_range_percent](./column/strings/string-length-in-range-percent/#daily-partition-string-length-in-range-percent)|partitioned|The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_in_range_percent](./column/strings/string-length-in-range-percent/#monthly-partition-string-length-in-range-percent)|partitioned|The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_empty_count](./column/strings/string-empty-count/#string-empty-count)|profiling|Verifies that empty strings in a column does not exceed the maximum accepted count.|
|[daily_string_empty_count](./column/strings/string-empty-count/#daily-string-empty-count)|recurring|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_empty_count](./column/strings/string-empty-count/#monthly-string-empty-count)|recurring|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_empty_count](./column/strings/string-empty-count/#daily-partition-string-empty-count)|partitioned|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_empty_count](./column/strings/string-empty-count/#monthly-partition-string-empty-count)|partitioned|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_empty_percent](./column/strings/string-empty-percent/#string-empty-percent)|profiling|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.|
|[daily_string_empty_percent](./column/strings/string-empty-percent/#daily-string-empty-percent)|recurring|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_empty_percent](./column/strings/string-empty-percent/#monthly-string-empty-percent)|recurring|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_empty_percent](./column/strings/string-empty-percent/#daily-partition-string-empty-percent)|partitioned|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_empty_percent](./column/strings/string-empty-percent/#monthly-partition-string-empty-percent)|partitioned|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_whitespace_count](./column/strings/string-whitespace-count/#string-whitespace-count)|profiling|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count.|
|[daily_string_whitespace_count](./column/strings/string-whitespace-count/#daily-string-whitespace-count)|recurring|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_whitespace_count](./column/strings/string-whitespace-count/#monthly-string-whitespace-count)|recurring|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_whitespace_count](./column/strings/string-whitespace-count/#daily-partition-string-whitespace-count)|partitioned|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_whitespace_count](./column/strings/string-whitespace-count/#monthly-partition-string-whitespace-count)|partitioned|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_whitespace_percent](./column/strings/string-whitespace-percent/#string-whitespace-percent)|profiling|Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.|
|[daily_string_whitespace_percent](./column/strings/string-whitespace-percent/#daily-string-whitespace-percent)|recurring|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_whitespace_percent](./column/strings/string-whitespace-percent/#monthly-string-whitespace-percent)|recurring|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_whitespace_percent](./column/strings/string-whitespace-percent/#daily-partition-string-whitespace-percent)|partitioned|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_whitespace_percent](./column/strings/string-whitespace-percent/#monthly-partition-string-whitespace-percent)|partitioned|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_surrounded_by_whitespace_count](./column/strings/string-surrounded-by-whitespace-count/#string-surrounded-by-whitespace-count)|profiling|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count.|
|[daily_string_surrounded_by_whitespace_count](./column/strings/string-surrounded-by-whitespace-count/#daily-string-surrounded-by-whitespace-count)|recurring|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_surrounded_by_whitespace_count](./column/strings/string-surrounded-by-whitespace-count/#monthly-string-surrounded-by-whitespace-count)|recurring|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_surrounded_by_whitespace_count](./column/strings/string-surrounded-by-whitespace-count/#daily-partition-string-surrounded-by-whitespace-count)|partitioned|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_surrounded_by_whitespace_count](./column/strings/string-surrounded-by-whitespace-count/#monthly-partition-string-surrounded-by-whitespace-count)|partitioned|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_surrounded_by_whitespace_percent](./column/strings/string-surrounded-by-whitespace-percent/#string-surrounded-by-whitespace-percent)|profiling|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage.|
|[daily_string_surrounded_by_whitespace_percent](./column/strings/string-surrounded-by-whitespace-percent/#daily-string-surrounded-by-whitespace-percent)|recurring|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_surrounded_by_whitespace_percent](./column/strings/string-surrounded-by-whitespace-percent/#monthly-string-surrounded-by-whitespace-percent)|recurring|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_surrounded_by_whitespace_percent](./column/strings/string-surrounded-by-whitespace-percent/#daily-partition-string-surrounded-by-whitespace-percent)|partitioned|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_surrounded_by_whitespace_percent](./column/strings/string-surrounded-by-whitespace-percent/#monthly-partition-string-surrounded-by-whitespace-percent)|partitioned|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_null_placeholder_count](./column/strings/string-null-placeholder-count/#string-null-placeholder-count)|profiling|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count.|
|[daily_string_null_placeholder_count](./column/strings/string-null-placeholder-count/#daily-string-null-placeholder-count)|recurring|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_null_placeholder_count](./column/strings/string-null-placeholder-count/#monthly-string-null-placeholder-count)|recurring|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_null_placeholder_count](./column/strings/string-null-placeholder-count/#daily-partition-string-null-placeholder-count)|partitioned|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_null_placeholder_count](./column/strings/string-null-placeholder-count/#monthly-partition-string-null-placeholder-count)|partitioned|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_null_placeholder_percent](./column/strings/string-null-placeholder-percent/#string-null-placeholder-percent)|profiling|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.|
|[daily_string_null_placeholder_percent](./column/strings/string-null-placeholder-percent/#daily-string-null-placeholder-percent)|recurring|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_null_placeholder_percent](./column/strings/string-null-placeholder-percent/#monthly-string-null-placeholder-percent)|recurring|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_null_placeholder_percent](./column/strings/string-null-placeholder-percent/#daily-partition-string-null-placeholder-percent)|partitioned|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_null_placeholder_percent](./column/strings/string-null-placeholder-percent/#monthly-partition-string-null-placeholder-percent)|partitioned|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_boolean_placeholder_percent](./column/strings/string-boolean-placeholder-percent/#string-boolean-placeholder-percent)|profiling|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage.|
|[daily_string_boolean_placeholder_percent](./column/strings/string-boolean-placeholder-percent/#daily-string-boolean-placeholder-percent)|recurring|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_boolean_placeholder_percent](./column/strings/string-boolean-placeholder-percent/#monthly-string-boolean-placeholder-percent)|recurring|Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_boolean_placeholder_percent](./column/strings/string-boolean-placeholder-percent/#daily-partition-string-boolean-placeholder-percent)|partitioned|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_boolean_placeholder_percent](./column/strings/string-boolean-placeholder-percent/#monthly-partition-string-boolean-placeholder-percent)|partitioned|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_parsable_to_integer_percent](./column/strings/string-parsable-to-integer-percent/#string-parsable-to-integer-percent)|profiling|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage.|
|[daily_string_parsable_to_integer_percent](./column/strings/string-parsable-to-integer-percent/#daily-string-parsable-to-integer-percent)|recurring|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_parsable_to_integer_percent](./column/strings/string-parsable-to-integer-percent/#monthly-string-parsable-to-integer-percent)|recurring|Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_parsable_to_integer_percent](./column/strings/string-parsable-to-integer-percent/#daily-partition-string-parsable-to-integer-percent)|partitioned|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_parsable_to_integer_percent](./column/strings/string-parsable-to-integer-percent/#monthly-partition-string-parsable-to-integer-percent)|partitioned|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_parsable_to_float_percent](./column/strings/string-parsable-to-float-percent/#string-parsable-to-float-percent)|profiling|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage.|
|[daily_string_parsable_to_float_percent](./column/strings/string-parsable-to-float-percent/#daily-string-parsable-to-float-percent)|recurring|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_parsable_to_float_percent](./column/strings/string-parsable-to-float-percent/#monthly-string-parsable-to-float-percent)|recurring|Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_parsable_to_float_percent](./column/strings/string-parsable-to-float-percent/#daily-partition-string-parsable-to-float-percent)|partitioned|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_parsable_to_float_percent](./column/strings/string-parsable-to-float-percent/#monthly-partition-string-parsable-to-float-percent)|partitioned|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[expected_strings_in_use_count](./column/strings/expected-strings-in-use-count/#expected-strings-in-use-count)|profiling|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|
|[daily_expected_strings_in_use_count](./column/strings/expected-strings-in-use-count/#daily-expected-strings-in-use-count)|recurring|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_expected_strings_in_use_count](./column/strings/expected-strings-in-use-count/#monthly-expected-strings-in-use-count)|recurring|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_expected_strings_in_use_count](./column/strings/expected-strings-in-use-count/#daily-partition-expected-strings-in-use-count)|partitioned|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_expected_strings_in_use_count](./column/strings/expected-strings-in-use-count/#monthly-partition-expected-strings-in-use-count)|partitioned|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_value_in_set_percent](./column/strings/string-value-in-set-percent/#string-value-in-set-percent)|profiling|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|
|[daily_string_value_in_set_percent](./column/strings/string-value-in-set-percent/#daily-string-value-in-set-percent)|recurring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_value_in_set_percent](./column/strings/string-value-in-set-percent/#monthly-string-value-in-set-percent)|recurring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_value_in_set_percent](./column/strings/string-value-in-set-percent/#daily-partition-string-value-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_value_in_set_percent](./column/strings/string-value-in-set-percent/#monthly-partition-string-value-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_valid_dates_percent](./column/strings/string-valid-dates-percent/#string-valid-dates-percent)|profiling|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage.|
|[daily_string_valid_dates_percent](./column/strings/string-valid-dates-percent/#daily-string-valid-dates-percent)|recurring|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_valid_dates_percent](./column/strings/string-valid-dates-percent/#monthly-string-valid-dates-percent)|recurring|Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_valid_dates_percent](./column/strings/string-valid-dates-percent/#daily-partition-string-valid-dates-percent)|partitioned|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_valid_dates_percent](./column/strings/string-valid-dates-percent/#monthly-partition-string-valid-dates-percent)|partitioned|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_valid_country_code_percent](./column/strings/string-valid-country-code-percent/#string-valid-country-code-percent)|profiling|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage.|
|[daily_string_valid_country_code_percent](./column/strings/string-valid-country-code-percent/#daily-string-valid-country-code-percent)|recurring|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_valid_country_code_percent](./column/strings/string-valid-country-code-percent/#monthly-string-valid-country-code-percent)|recurring|Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_valid_country_code_percent](./column/strings/string-valid-country-code-percent/#daily-partition-string-valid-country-code-percent)|partitioned|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_valid_country_code_percent](./column/strings/string-valid-country-code-percent/#monthly-partition-string-valid-country-code-percent)|partitioned|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_valid_currency_code_percent](./column/strings/string-valid-currency-code-percent/#string-valid-currency-code-percent)|profiling|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage.|
|[daily_string_valid_currency_code_percent](./column/strings/string-valid-currency-code-percent/#daily-string-valid-currency-code-percent)|recurring|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_valid_currency_code_percent](./column/strings/string-valid-currency-code-percent/#monthly-string-valid-currency-code-percent)|recurring|Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_valid_currency_code_percent](./column/strings/string-valid-currency-code-percent/#daily-partition-string-valid-currency-code-percent)|partitioned|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_valid_currency_code_percent](./column/strings/string-valid-currency-code-percent/#monthly-partition-string-valid-currency-code-percent)|partitioned|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_invalid_email_count](./column/strings/string-invalid-email-count/#string-invalid-email-count)|profiling|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_email_count](./column/strings/string-invalid-email-count/#daily-string-invalid-email-count)|recurring|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_invalid_email_count](./column/strings/string-invalid-email-count/#monthly-string-invalid-email-count)|recurring|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_email_count](./column/strings/string-invalid-email-count/#daily-partition-string-invalid-email-count)|partitioned|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_email_count](./column/strings/string-invalid-email-count/#monthly-partition-string-invalid-email-count)|partitioned|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_invalid_uuid_count](./column/strings/string-invalid-uuid-count/#string-invalid-uuid-count)|profiling|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_uuid_count](./column/strings/string-invalid-uuid-count/#daily-string-invalid-uuid-count)|recurring|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_invalid_uuid_count](./column/strings/string-invalid-uuid-count/#monthly-string-invalid-uuid-count)|recurring|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_uuid_count](./column/strings/string-invalid-uuid-count/#daily-partition-string-invalid-uuid-count)|partitioned|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_uuid_count](./column/strings/string-invalid-uuid-count/#monthly-partition-string-invalid-uuid-count)|partitioned|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_valid_uuid_percent](./column/strings/string-valid-uuid-percent/#string-valid-uuid-percent)|profiling|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage.|
|[daily_string_valid_uuid_percent](./column/strings/string-valid-uuid-percent/#daily-string-valid-uuid-percent)|recurring|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_valid_uuid_percent](./column/strings/string-valid-uuid-percent/#monthly-string-valid-uuid-percent)|recurring|Verifies that the percentage of valid UUID in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_uuid_percent](./column/strings/string-valid-uuid-percent/#daily-partition-valid-uuid-percent)|partitioned|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_uuid_percent](./column/strings/string-valid-uuid-percent/#monthly-partition-valid-uuid-percent)|partitioned|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_invalid_ip4_address_count](./column/strings/string-invalid-ip4-address-count/#string-invalid-ip4-address-count)|profiling|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_ip4_address_count](./column/strings/string-invalid-ip4-address-count/#daily-string-invalid-ip4-address-count)|recurring|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_invalid_ip4_address_count](./column/strings/string-invalid-ip4-address-count/#monthly-string-invalid-ip4-address-count)|recurring|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_ip4_address_count](./column/strings/string-invalid-ip4-address-count/#daily-partition-string-invalid-ip4-address-count)|partitioned|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_ip4_address_count](./column/strings/string-invalid-ip4-address-count/#monthly-partition-string-invalid-ip4-address-count)|partitioned|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_invalid_ip6_address_count](./column/strings/string-invalid-ip6-address-count/#string-invalid-ip6-address-count)|profiling|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_ip6_address_count](./column/strings/string-invalid-ip6-address-count/#daily-string-invalid-ip6-address-count)|recurring|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_invalid_ip6_address_count](./column/strings/string-invalid-ip6-address-count/#monthly-string-invalid-ip6-address-count)|recurring|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_ip6_address_count](./column/strings/string-invalid-ip6-address-count/#daily-partition-string-invalid-ip6-address-count)|partitioned|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_ip6_address_count](./column/strings/string-invalid-ip6-address-count/#monthly-partition-string-invalid-ip6-address-count)|partitioned|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_not_match_regex_count](./column/strings/string-not-match-regex-count/#string-not-match-regex-count)|profiling|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count.|
|[daily_string_not_match_regex_count](./column/strings/string-not-match-regex-count/#daily-string-not-match-regex-count)|recurring|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_not_match_regex_count](./column/strings/string-not-match-regex-count/#monthly-string-not-match-regex-count)|recurring|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_not_match_regex_count](./column/strings/string-not-match-regex-count/#daily-partition-string-not-match-regex-count)|partitioned|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_not_match_regex_count](./column/strings/string-not-match-regex-count/#monthly-partition-string-not-match-regex-count)|partitioned|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_match_regex_percent](./column/strings/string-match-regex-percent/#string-match-regex-percent)|profiling|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage.|
|[daily_string_match_regex_percent](./column/strings/string-match-regex-percent/#daily-string-match-regex-percent)|recurring|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_match_regex_percent](./column/strings/string-match-regex-percent/#monthly-string-match-regex-percent)|recurring|Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_match_regex_percent](./column/strings/string-match-regex-percent/#daily-partition-string-match-regex-percent)|partitioned|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_match_regex_percent](./column/strings/string-match-regex-percent/#monthly-partition-string-match-regex-percent)|partitioned|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_not_match_date_regex_count](./column/strings/string-not-match-date-regex-count/#string-not-match-date-regex-count)|profiling|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count.|
|[daily_string_not_match_date_regex_count](./column/strings/string-not-match-date-regex-count/#daily-string-not-match-date-regex-count)|recurring|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_not_match_date_regex_count](./column/strings/string-not-match-date-regex-count/#monthly-string-not-match-date-regex-count)|recurring|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_not_match_date_regex_count](./column/strings/string-not-match-date-regex-count/#daily-partition-string-not-match-date-regex-count)|partitioned|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_not_match_date_regex_count](./column/strings/string-not-match-date-regex-count/#monthly-partition-string-not-match-date-regex-count)|partitioned|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_match_date_regex_percent](./column/strings/string-match-date-regex-percent/#string-match-date-regex-percent)|profiling|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage.|
|[daily_string_match_date_regex_percent](./column/strings/string-match-date-regex-percent/#daily-string-match-date-regex-percent)|recurring|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_match_date_regex_percent](./column/strings/string-match-date-regex-percent/#monthly-string-match-date-regex-percent)|recurring|Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_match_date_regex_percent](./column/strings/string-match-date-regex-percent/#daily-partition-string-match-date-regex-percent)|partitioned|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_match_date_regex_percent](./column/strings/string-match-date-regex-percent/#monthly-partition-string-match-date-regex-percent)|partitioned|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_match_name_regex_percent](./column/strings/string-match-name-regex-percent/#string-match-name-regex-percent)|profiling|Verifies that the percentage of strings matching the name regex in a column does not fall below the minimum accepted percentage.|
|[daily_string_match_name_regex_percent](./column/strings/string-match-name-regex-percent/#daily-string-match-name-regex-percent)|recurring|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_match_name_regex_percent](./column/strings/string-match-name-regex-percent/#monthly-string-match-name-regex-percent)|recurring|Verifies that the percentage of strings matching the name regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_match_name_regex_percent](./column/strings/string-match-name-regex-percent/#daily-partition-string-match-name-regex-percent)|partitioned|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_match_name_regex_percent](./column/strings/string-match-name-regex-percent/#monthly-partition-string-match-name-regex-percent)|partitioned|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[expected_strings_in_top_values_count](./column/strings/expected-strings-in-top-values-count/#expected-strings-in-top-values-count)|profiling|Verifies that the top X most popular column values contain all values from a list of expected values.|
|[daily_expected_strings_in_top_values_count](./column/strings/expected-strings-in-top-values-count/#daily-expected-strings-in-top-values-count)|recurring|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_expected_strings_in_top_values_count](./column/strings/expected-strings-in-top-values-count/#monthly-expected-strings-in-top-values-count)|recurring|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_expected_strings_in_top_values_count](./column/strings/expected-strings-in-top-values-count/#daily-partition-expected-strings-in-top-values-count)|partitioned|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_expected_strings_in_top_values_count](./column/strings/expected-strings-in-top-values-count/#monthly-partition-expected-strings-in-top-values-count)|partitioned|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_datatype_detected](./column/strings/string-datatype-detected/#string-datatype-detected)|profiling|Detects the data type of text values stored in the column. The sensor returns the code of the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type.|
|[daily_string_datatype_detected](./column/strings/string-datatype-detected/#daily-string-datatype-detected)|recurring|Detects the data type of text values stored in the column. The sensor returns the code of the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_datatype_detected](./column/strings/string-datatype-detected/#monthly-string-datatype-detected)|recurring|Detects the data type of text values stored in the column. The sensor returns the code of the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_datatype_detected](./column/strings/string-datatype-detected/#daily-partition-string-datatype-detected)|partitioned|Detects the data type of text values stored in the column. The sensor returns the code of the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_datatype_detected](./column/strings/string-datatype-detected/#monthly-partition-string-datatype-detected)|partitioned|Detects the data type of text values stored in the column. The sensor returns the code of the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each monthly partition.|





###uniqueness  
Counts the number or percent of duplicate or unique values in a column.

| Check name | Check type | Description |
|------------|------------|-------------|
|[distinct_count](./column/uniqueness/distinct-count/#distinct-count)|profiling|Verifies that the number of distinct values in a column does not fall below the minimum accepted count.|
|[daily_distinct_count](./column/uniqueness/distinct-count/#daily-distinct-count)|recurring|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_distinct_count](./column/uniqueness/distinct-count/#monthly-distinct-count)|recurring|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_distinct_count](./column/uniqueness/distinct-count/#daily-partition-distinct-count)|partitioned|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_distinct_count](./column/uniqueness/distinct-count/#monthly-partition-distinct-count)|partitioned|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[distinct_percent](./column/uniqueness/distinct-percent/#distinct-percent)|profiling|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent.|
|[daily_distinct_percent](./column/uniqueness/distinct-percent/#daily-distinct-percent)|recurring|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_distinct_percent](./column/uniqueness/distinct-percent/#monthly-distinct-percent)|recurring|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_distinct_percent](./column/uniqueness/distinct-percent/#daily-partition-distinct-percent)|partitioned|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_distinct_percent](./column/uniqueness/distinct-percent/#monthly-partition-distinct-percent)|partitioned|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[duplicate_count](./column/uniqueness/duplicate-count/#duplicate-count)|profiling|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.|
|[daily_duplicate_count](./column/uniqueness/duplicate-count/#daily-duplicate-count)|recurring|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_duplicate_count](./column/uniqueness/duplicate-count/#monthly-duplicate-count)|recurring|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_duplicate_count](./column/uniqueness/duplicate-count/#daily-partition-duplicate-count)|partitioned|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_duplicate_count](./column/uniqueness/duplicate-count/#monthly-partition-duplicate-count)|partitioned|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[duplicate_percent](./column/uniqueness/duplicate-percent/#duplicate-percent)|profiling|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.|
|[daily_duplicate_percent](./column/uniqueness/duplicate-percent/#daily-duplicate-percent)|recurring|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_duplicate_percent](./column/uniqueness/duplicate-percent/#monthly-duplicate-percent)|recurring|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_duplicate_percent](./column/uniqueness/duplicate-percent/#daily-partition-duplicate-percent)|partitioned|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_duplicate_percent](./column/uniqueness/duplicate-percent/#monthly-partition-duplicate-percent)|partitioned|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|



