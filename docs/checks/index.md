# Data quality checks

**This is a list of data quality checks supported by DQOps, broken down by a category and a brief description of what they do.**


## Table checks


### **accuracy**
Compares the tested table with another (reference) table.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_total_row_count_match_percent](../table/accuracy/total-row-count-match-percent.md#profile-total-row-count-match-percent)|profiling|Verifies that the total row count of the tested table matches the total row count of another (reference) table.|
|[daily_total_row_count_match_percent](../table/accuracy/total-row-count-match-percent.md#daily-total-row-count-match-percent)|monitoring|Verifies the total ow count of a tested table and compares it to a row count of a reference table. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_row_count_match_percent](../table/accuracy/total-row-count-match-percent.md#monthly-total-row-count-match-percent)|monitoring|Verifies the total row count of a tested table and compares it to a row count of a reference table. Stores the most recent row count for each month when the data quality check was evaluated.|





### **availability**
Checks whether the table is accessible and available for use.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_table_availability](../table/availability/table-availability.md#profile-table-availability)|profiling|Verifies availability of a table in a monitored database using a simple query.|
|[daily_table_availability](../table/availability/table-availability.md#daily-table-availability)|monitoring|Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each day when the data quality check was evaluated.|
|[monthly_table_availability](../table/availability/table-availability.md#monthly-table-availability)|monitoring|Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each month when the data quality check was evaluated.|





### **comparisons**


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_match](../table/comparisons/row-count-match.md#profile-row-count-match)|profiling|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause.|
|[daily_row_count_match](../table/comparisons/row-count-match.md#daily-row-count-match)|monitoring|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_row_count_match](../table/comparisons/row-count-match.md#monthly-row-count-match)|monitoring|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_row_count_match](../table/comparisons/row-count-match.md#daily-partition-row-count-match)|partitioned|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause on the time period (the daily partition) and all other data grouping columns. Stores the most recent captured value for each daily partition that was analyzed.|
|[monthly_partition_row_count_match](../table/comparisons/row-count-match.md#monthly-partition-row-count-match)|partitioned|Verifies that the row count of the tested (parent) table matches the row count of the reference table, for each monthly partition (grouping rows by the time period, truncated to the month). Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each monthly partition and optionally data groups.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_count_match](../table/comparisons/column-count-match.md#profile-column-count-match)|profiling|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping.|
|[daily_column_count_match](../table/comparisons/column-count-match.md#daily-column-count-match)|monitoring|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_column_count_match](../table/comparisons/column-count-match.md#monthly-column-count-match)|monitoring|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each month when the data quality check was evaluated.|





### **schema**
Detects schema drifts such as columns added, removed, reordered or the data types of columns have changed.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_count](../table/schema/column-count.md#profile-column-count)|profiling|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).|
|[daily_column_count](../table/schema/column-count.md#daily-column-count)|monitoring|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each day when the data quality check was evaluated.|
|[monthly_column_count](../table/schema/column-count.md#monthly-column-count)|monitoring|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_count_changed](../table/schema/column-count-changed.md#profile-column-count-changed)|profiling|Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.|
|[daily_column_count_changed](../table/schema/column-count-changed.md#daily-column-count-changed)|monitoring|Detects if the count of columns has changed since the most recent day. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each day when the data quality check was evaluated.|
|[monthly_column_count_changed](../table/schema/column-count-changed.md#monthly-column-count-changed)|monitoring|Detects if the count of columns has changed since the last month. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_list_changed](../table/schema/column-list-changed.md#profile-column-list-changed)|profiling|Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|
|[daily_column_list_changed](../table/schema/column-list-changed.md#daily-column-list-changed)|monitoring|Detects if new columns were added or existing columns were removed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|
|[monthly_column_list_changed](../table/schema/column-list-changed.md#monthly-column-list-changed)|monitoring|Detects if new columns were added or existing columns were removed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_list_or_order_changed](../table/schema/column-list-or-order-changed.md#profile-column-list-or-order-changed)|profiling|Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|
|[daily_column_list_or_order_changed](../table/schema/column-list-or-order-changed.md#daily-column-list-or-order-changed)|monitoring|Detects if new columns were added, existing columns were removed or the columns were reordered since the most recent day. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|
|[monthly_column_list_or_order_changed](../table/schema/column-list-or-order-changed.md#monthly-column-list-or-order-changed)|monitoring|Detects if new columns were added, existing columns were removed or the columns were reordered since the last month. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_types_changed](../table/schema/column-types-changed.md#profile-column-types-changed)|profiling|Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|
|[daily_column_types_changed](../table/schema/column-types-changed.md#daily-column-types-changed)|monitoring|Detects if new columns were added, removed or their data types have changed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|
|[monthly_column_types_changed](../table/schema/column-types-changed.md#monthly-column-types-changed)|monitoring|Detects if new columns were added, removed or their data types have changed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|





### **sql**
Validate data against user-defined SQL queries at the table level. Checks in this group allow for validation that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sql_condition_passed_percent_on_table](../table/sql/sql-condition-passed-percent-on-table.md#profile-sql-condition-passed-percent-on-table)|profiling|Verifies that a set percentage of rows passed a custom SQL condition (expression).|
|[daily_sql_condition_passed_percent_on_table](../table/sql/sql-condition-passed-percent-on-table.md#daily-sql-condition-passed-percent-on-table)|monitoring|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_condition_passed_percent_on_table](../table/sql/sql-condition-passed-percent-on-table.md#monthly-sql-condition-passed-percent-on-table)|monitoring|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_condition_passed_percent_on_table](../table/sql/sql-condition-passed-percent-on-table.md#daily-partition-sql-condition-passed-percent-on-table)|partitioned|Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_condition_passed_percent_on_table](../table/sql/sql-condition-passed-percent-on-table.md#monthly-partition-sql-condition-passed-percent-on-table)|partitioned|Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sql_condition_failed_count_on_table](../table/sql/sql-condition-failed-count-on-table.md#profile-sql-condition-failed-count-on-table)|profiling|Verifies that a set number of rows failed a custom SQL condition (expression).|
|[daily_sql_condition_failed_count_on_table](../table/sql/sql-condition-failed-count-on-table.md#daily-sql-condition-failed-count-on-table)|monitoring|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_condition_failed_count_on_table](../table/sql/sql-condition-failed-count-on-table.md#monthly-sql-condition-failed-count-on-table)|monitoring|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_condition_failed_count_on_table](../table/sql/sql-condition-failed-count-on-table.md#daily-partition-sql-condition-failed-count-on-table)|partitioned|Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_condition_failed_count_on_table](../table/sql/sql-condition-failed-count-on-table.md#monthly-partition-sql-condition-failed-count-on-table)|partitioned|Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sql_aggregate_expr_table](../table/sql/sql-aggregate-expr-table.md#profile-sql-aggregate-expr-table)|profiling|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|
|[daily_sql_aggregate_expr_table](../table/sql/sql-aggregate-expr-table.md#daily-sql-aggregate-expr-table)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_aggregate_expr_table](../table/sql/sql-aggregate-expr-table.md#monthly-sql-aggregate-expr-table)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_aggregate_expr_table](../table/sql/sql-aggregate-expr-table.md#daily-partition-sql-aggregate-expr-table)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_aggregate_expr_table](../table/sql/sql-aggregate-expr-table.md#monthly-partition-sql-aggregate-expr-table)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|





### **timeliness**
Assesses the freshness and staleness of data, as well as data ingestion delay and reload lag for partitioned data.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_data_freshness](../table/timeliness/data-freshness.md#profile-data-freshness)|profiling|Calculates the number of days since the most recent event timestamp (freshness)|
|[daily_data_freshness](../table/timeliness/data-freshness.md#daily-data-freshness)|monitoring|Daily  calculating the number of days since the most recent event timestamp (freshness)|
|[monthly_data_freshness](../table/timeliness/data-freshness.md#monthly-data-freshness)|monitoring|Monthly monitoring calculating the number of days since the most recent event timestamp (freshness)|
|[daily_partition_data_freshness](../table/timeliness/data-freshness.md#daily-partition-data-freshness)|partitioned|Daily partitioned check calculating the number of days since the most recent event timestamp (freshness)|
|[monthly_partition_data_freshness](../table/timeliness/data-freshness.md#monthly-partition-data-freshness)|partitioned|Monthly partitioned check calculating the number of days since the most recent event (freshness)|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_data_staleness](../table/timeliness/data-staleness.md#profile-data-staleness)|profiling|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[daily_data_staleness](../table/timeliness/data-staleness.md#daily-data-staleness)|monitoring|Daily  calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[monthly_data_staleness](../table/timeliness/data-staleness.md#monthly-data-staleness)|monitoring|Monthly monitoring calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[daily_partition_data_staleness](../table/timeliness/data-staleness.md#daily-partition-data-staleness)|partitioned|Daily partitioned check calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[monthly_partition_data_staleness](../table/timeliness/data-staleness.md#monthly-partition-data-staleness)|partitioned|Monthly partitioned check calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_data_ingestion_delay](../table/timeliness/data-ingestion-delay.md#profile-data-ingestion-delay)|profiling|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[daily_data_ingestion_delay](../table/timeliness/data-ingestion-delay.md#daily-data-ingestion-delay)|monitoring|Daily  calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[monthly_data_ingestion_delay](../table/timeliness/data-ingestion-delay.md#monthly-data-ingestion-delay)|monitoring|Monthly monitoring calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[daily_partition_data_ingestion_delay](../table/timeliness/data-ingestion-delay.md#daily-partition-data-ingestion-delay)|partitioned|Daily partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[monthly_partition_data_ingestion_delay](../table/timeliness/data-ingestion-delay.md#monthly-partition-data-ingestion-delay)|partitioned|Monthly partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_reload_lag](../table/timeliness/reload-lag.md#daily-partition-reload-lag)|partitioned|Daily partitioned check calculating the longest time a row waited to be load|
|[monthly_partition_reload_lag](../table/timeliness/reload-lag.md#monthly-partition-reload-lag)|partitioned|Monthly partitioned check calculating the longest time a row waited to be load|





### **volume**
Evaluates the overall quality of the table by verifying the number of rows.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count](../table/volume/row-count.md#profile-row-count)|profiling|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|
|[daily_row_count](../table/volume/row-count.md#daily-row-count)|monitoring|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.Stores the most recent captured row count value for each day when the row count was evaluated.|
|[monthly_row_count](../table/volume/row-count.md#monthly-row-count)|monitoring|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.Stores the most recent captured row count value for each month when the row count was evaluated.|
|[daily_partition_row_count](../table/volume/row-count.md#daily-partition-row-count)|partitioned|Verifies that each daily partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|
|[monthly_partition_row_count](../table/volume/row-count.md#monthly-partition-row-count)|partitioned|Verifies that each monthly partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_anomaly_differencing_30_days](../table/volume/row-count-anomaly-differencing-30-days.md#profile-row-count-anomaly-differencing-30-days)|profiling|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.|
|[daily_row_count_anomaly_differencing_30_days](../table/volume/row-count-anomaly-differencing-30-days.md#daily-row-count-anomaly-differencing-30-days)|monitoring|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_anomaly_differencing](../table/volume/row-count-anomaly-differencing.md#profile-row-count-anomaly-differencing)|profiling|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.|
|[daily_row_count_anomaly_differencing](../table/volume/row-count-anomaly-differencing.md#daily-row-count-anomaly-differencing)|monitoring|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_change](../table/volume/row-count-change.md#profile-row-count-change)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|
|[daily_row_count_change](../table/volume/row-count-change.md#daily-row-count-change)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last day with a row count captured.|
|[monthly_row_count_change](../table/volume/row-count-change.md#monthly-row-count-change)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last month.|
|[daily_partition_row_count_change](../table/volume/row-count-change.md#daily-partition-row-count-change)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|
|[monthly_partition_row_count_change](../table/volume/row-count-change.md#monthly-partition-row-count-change)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_change_yesterday](../table/volume/row-count-change-yesterday.md#profile-row-count-change-yesterday)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|
|[daily_row_count_change_yesterday](../table/volume/row-count-change-yesterday.md#daily-row-count-change-yesterday)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|
|[daily_partition_row_count_change_yesterday](../table/volume/row-count-change-yesterday.md#daily-partition-row-count-change-yesterday)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_change_7_days](../table/volume/row-count-change-7-days.md#profile-row-count-change-7-days)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|
|[daily_row_count_change_7_days](../table/volume/row-count-change-7-days.md#daily-row-count-change-7-days)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|
|[daily_partition_row_count_change_7_days](../table/volume/row-count-change-7-days.md#daily-partition-row-count-change-7-days)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_change_30_days](../table/volume/row-count-change-30-days.md#profile-row-count-change-30-days)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|
|[daily_row_count_change_30_days](../table/volume/row-count-change-30-days.md#daily-row-count-change-30-days)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|
|[daily_partition_row_count_change_30_days](../table/volume/row-count-change-30-days.md#daily-partition-row-count-change-30-days)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_row_count_anomaly_stationary_30_days](../table/volume/row-count-anomaly-stationary-30-days.md#daily-partition-row-count-anomaly-stationary-30-days)|partitioned|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_row_count_anomaly_stationary](../table/volume/row-count-anomaly-stationary.md#daily-partition-row-count-anomaly-stationary)|partitioned|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 90 days.|















































## Column checks























### **accuracy**


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_total_sum_match_percent](../column/accuracy/total-sum-match-percent.md#profile-total-sum-match-percent)|profiling|Verifies that percentage of the difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number.|
|[daily_total_sum_match_percent](../column/accuracy/total-sum-match-percent.md#daily-total-sum-match-percent)|monitoring|Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_sum_match_percent](../column/accuracy/total-sum-match-percent.md#monthly-total-sum-match-percent)|monitoring|Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_total_min_match_percent](../column/accuracy/total-min-match-percent.md#profile-total-min-match-percent)|profiling|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number.|
|[daily_total_min_match_percent](../column/accuracy/total-min-match-percent.md#daily-total-min-match-percent)|monitoring|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_min_match_percent](../column/accuracy/total-min-match-percent.md#monthly-total-min-match-percent)|monitoring|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_total_max_match_percent](../column/accuracy/total-max-match-percent.md#profile-total-max-match-percent)|profiling|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number.|
|[daily_total_max_match_percent](../column/accuracy/total-max-match-percent.md#daily-total-max-match-percent)|monitoring|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_max_match_percent](../column/accuracy/total-max-match-percent.md#monthly-total-max-match-percent)|monitoring|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_total_average_match_percent](../column/accuracy/total-average-match-percent.md#profile-total-average-match-percent)|profiling|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number.|
|[daily_total_average_match_percent](../column/accuracy/total-average-match-percent.md#daily-total-average-match-percent)|monitoring|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_average_match_percent](../column/accuracy/total-average-match-percent.md#monthly-total-average-match-percent)|monitoring|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_total_not_null_count_match_percent](../column/accuracy/total-not-null-count-match-percent.md#profile-total-not-null-count-match-percent)|profiling|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[daily_total_not_null_count_match_percent](../column/accuracy/total-not-null-count-match-percent.md#daily-total-not-null-count-match-percent)|monitoring|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_not_null_count_match_percent](../column/accuracy/total-not-null-count-match-percent.md#monthly-total-not-null-count-match-percent)|monitoring|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|





### **anomaly**
Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_anomaly_stationary_30_days](../column/anomaly/mean-anomaly-stationary-30-days.md#profile-mean-anomaly-stationary-30-days)|profiling|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_mean_anomaly_stationary_30_days](../column/anomaly/mean-anomaly-stationary-30-days.md#daily-mean-anomaly-stationary-30-days)|monitoring|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_partition_mean_anomaly_stationary_30_days](../column/anomaly/mean-anomaly-stationary-30-days.md#daily-partition-mean-anomaly-stationary-30-days)|partitioned|Verifies that the mean value in a column is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_anomaly_stationary](../column/anomaly/mean-anomaly-stationary.md#profile-mean-anomaly-stationary)|profiling|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_mean_anomaly_stationary](../column/anomaly/mean-anomaly-stationary.md#daily-mean-anomaly-stationary)|monitoring|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_partition_mean_anomaly_stationary](../column/anomaly/mean-anomaly-stationary.md#daily-partition-mean-anomaly-stationary)|partitioned|Verifies that the mean value in a column is within a percentile from measurements made during the last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_anomaly_stationary_30_days](../column/anomaly/median-anomaly-stationary-30-days.md#profile-median-anomaly-stationary-30-days)|profiling|Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_median_anomaly_stationary_30_days](../column/anomaly/median-anomaly-stationary-30-days.md#daily-median-anomaly-stationary-30-days)|monitoring|Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_partition_median_anomaly_stationary_30_days](../column/anomaly/median-anomaly-stationary-30-days.md#daily-partition-median-anomaly-stationary-30-days)|partitioned|Verifies that the median in a column is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_anomaly_stationary](../column/anomaly/median-anomaly-stationary.md#profile-median-anomaly-stationary)|profiling|Verifies that the median in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_median_anomaly_stationary](../column/anomaly/median-anomaly-stationary.md#daily-median-anomaly-stationary)|monitoring|Verifies that the median in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_partition_median_anomaly_stationary](../column/anomaly/median-anomaly-stationary.md#daily-partition-median-anomaly-stationary)|partitioned|Verifies that the median in a column is within a percentile from measurements made during the last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_anomaly_differencing_30_days](../column/anomaly/sum-anomaly-differencing-30-days.md#profile-sum-anomaly-differencing-30-days)|profiling|Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_sum_anomaly_differencing_30_days](../column/anomaly/sum-anomaly-differencing-30-days.md#daily-sum-anomaly-differencing-30-days)|monitoring|Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_anomaly_differencing](../column/anomaly/sum-anomaly-differencing.md#profile-sum-anomaly-differencing)|profiling|Verifies that the sum in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_sum_anomaly_differencing](../column/anomaly/sum-anomaly-differencing.md#daily-sum-anomaly-differencing)|monitoring|Verifies that the sum in a column changes in a rate within a percentile boundary during last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_change](../column/anomaly/mean-change.md#profile-mean-change)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[daily_mean_change](../column/anomaly/mean-change.md#daily-mean-change)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[monthly_mean_change](../column/anomaly/mean-change.md#monthly-mean-change)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[daily_partition_mean_change](../column/anomaly/mean-change.md#daily-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[monthly_partition_mean_change](../column/anomaly/mean-change.md#monthly-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_change_yesterday](../column/anomaly/mean-change-yesterday.md#profile-mean-change-yesterday)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_mean_change_yesterday](../column/anomaly/mean-change-yesterday.md#daily-mean-change-yesterday)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_mean_change_yesterday](../column/anomaly/mean-change-yesterday.md#daily-partition-mean-change-yesterday)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_change_7_days](../column/anomaly/mean-change-7-days.md#profile-mean-change-7-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|
|[daily_mean_change_7_days](../column/anomaly/mean-change-7-days.md#daily-mean-change-7-days)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_mean_change_7_days](../column/anomaly/mean-change-7-days.md#daily-partition-mean-change-7-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_change_30_days](../column/anomaly/mean-change-30-days.md#profile-mean-change-30-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|
|[daily_mean_change_30_days](../column/anomaly/mean-change-30-days.md#daily-mean-change-30-days)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_mean_change_30_days](../column/anomaly/mean-change-30-days.md#daily-partition-mean-change-30-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_change](../column/anomaly/median-change.md#profile-median-change)|profiling|Verifies that the median in a column changed in a fixed rate since last readout.|
|[daily_median_change](../column/anomaly/median-change.md#daily-median-change)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout.|
|[monthly_median_change](../column/anomaly/median-change.md#monthly-median-change)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout.|
|[daily_partition_median_change](../column/anomaly/median-change.md#daily-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout.|
|[monthly_partition_median_change](../column/anomaly/median-change.md#monthly-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_change_yesterday](../column/anomaly/median-change-yesterday.md#profile-median-change-yesterday)|profiling|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|
|[daily_median_change_yesterday](../column/anomaly/median-change-yesterday.md#daily-median-change-yesterday)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_median_change_yesterday](../column/anomaly/median-change-yesterday.md#daily-partition-median-change-yesterday)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_change_7_days](../column/anomaly/median-change-7-days.md#profile-median-change-7-days)|profiling|Verifies that the median in a column changed in a fixed rate since last readout from last week.|
|[daily_median_change_7_days](../column/anomaly/median-change-7-days.md#daily-median-change-7-days)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_median_change_7_days](../column/anomaly/median-change-7-days.md#daily-partition-median-change-7-days)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_change_30_days](../column/anomaly/median-change-30-days.md#profile-median-change-30-days)|profiling|Verifies that the median in a column changed in a fixed rate since last readout from last month.|
|[daily_median_change_30_days](../column/anomaly/median-change-30-days.md#daily-median-change-30-days)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_median_change_30_days](../column/anomaly/median-change-30-days.md#daily-partition-median-change-30-days)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_change](../column/anomaly/sum-change.md#profile-sum-change)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[daily_sum_change](../column/anomaly/sum-change.md#daily-sum-change)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[monthly_sum_change](../column/anomaly/sum-change.md#monthly-sum-change)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[daily_partition_sum_change](../column/anomaly/sum-change.md#daily-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[monthly_partition_sum_change](../column/anomaly/sum-change.md#monthly-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_change_yesterday](../column/anomaly/sum-change-yesterday.md#profile-sum-change-yesterday)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|
|[daily_sum_change_yesterday](../column/anomaly/sum-change-yesterday.md#daily-sum-change-yesterday)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_sum_change_yesterday](../column/anomaly/sum-change-yesterday.md#daily-partition-sum-change-yesterday)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_change_7_days](../column/anomaly/sum-change-7-days.md#profile-sum-change-7-days)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|
|[daily_sum_change_7_days](../column/anomaly/sum-change-7-days.md#daily-sum-change-7-days)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_sum_change_7_days](../column/anomaly/sum-change-7-days.md#daily-partition-sum-change-7-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_change_30_days](../column/anomaly/sum-change-30-days.md#profile-sum-change-30-days)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|
|[daily_sum_change_30_days](../column/anomaly/sum-change-30-days.md#daily-sum-change-30-days)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_sum_change_30_days](../column/anomaly/sum-change-30-days.md#daily-partition-sum-change-30-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_sum_anomaly_stationary_30_days](../column/anomaly/sum-anomaly-stationary-30-days.md#daily-partition-sum-anomaly-stationary-30-days)|partitioned|Verifies that the sum in a column is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_sum_anomaly_stationary](../column/anomaly/sum-anomaly-stationary.md#daily-partition-sum-anomaly-stationary)|partitioned|Verifies that the sum in a column is within a percentile from measurements made during the last 90 days.|





### **bool**
Calculates the percentage of data in a Boolean format.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_true_percent](../column/bool/true-percent.md#profile-true-percent)|profiling|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage.|
|[daily_true_percent](../column/bool/true-percent.md#daily-true-percent)|monitoring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_true_percent](../column/bool/true-percent.md#monthly-true-percent)|monitoring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_true_percent](../column/bool/true-percent.md#daily-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_true_percent](../column/bool/true-percent.md#monthly-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_false_percent](../column/bool/false-percent.md#profile-false-percent)|profiling|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage.|
|[daily_false_percent](../column/bool/false-percent.md#daily-false-percent)|monitoring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_false_percent](../column/bool/false-percent.md#monthly-false-percent)|monitoring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_false_percent](../column/bool/false-percent.md#daily-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_false_percent](../column/bool/false-percent.md#monthly-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





### **comparisons**


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_match](../column/comparisons/sum-match.md#profile-sum-match)|profiling|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_sum_match](../column/comparisons/sum-match.md#daily-sum-match)|monitoring|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sum_match](../column/comparisons/sum-match.md#monthly-sum-match)|monitoring|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_sum_match](../column/comparisons/sum-match.md#daily-partition-sum-match)|partitioned|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_sum_match](../column/comparisons/sum-match.md#monthly-partition-sum-match)|partitioned|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_min_match](../column/comparisons/min-match.md#profile-min-match)|profiling|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_min_match](../column/comparisons/min-match.md#daily-min-match)|monitoring|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_min_match](../column/comparisons/min-match.md#monthly-min-match)|monitoring|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_min_match](../column/comparisons/min-match.md#daily-partition-min-match)|partitioned|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_min_match](../column/comparisons/min-match.md#monthly-partition-min-match)|partitioned|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_max_match](../column/comparisons/max-match.md#profile-max-match)|profiling|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_max_match](../column/comparisons/max-match.md#daily-max-match)|monitoring|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_max_match](../column/comparisons/max-match.md#monthly-max-match)|monitoring|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_max_match](../column/comparisons/max-match.md#daily-partition-max-match)|partitioned|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_max_match](../column/comparisons/max-match.md#monthly-partition-max-match)|partitioned|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_match](../column/comparisons/mean-match.md#profile-mean-match)|profiling|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_mean_match](../column/comparisons/mean-match.md#daily-mean-match)|monitoring|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_mean_match](../column/comparisons/mean-match.md#monthly-mean-match)|monitoring|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_mean_match](../column/comparisons/mean-match.md#daily-partition-mean-match)|partitioned|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_mean_match](../column/comparisons/mean-match.md#monthly-partition-mean-match)|partitioned|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_not_null_count_match](../column/comparisons/not-null-count-match.md#profile-not-null-count-match)|profiling|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_not_null_count_match](../column/comparisons/not-null-count-match.md#daily-not-null-count-match)|monitoring|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_not_null_count_match](../column/comparisons/not-null-count-match.md#monthly-not-null-count-match)|monitoring|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_not_null_count_match](../column/comparisons/not-null-count-match.md#daily-partition-not-null-count-match)|partitioned|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_not_null_count_match](../column/comparisons/not-null-count-match.md#monthly-partition-not-null-count-match)|partitioned|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_null_count_match](../column/comparisons/null-count-match.md#profile-null-count-match)|profiling|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_null_count_match](../column/comparisons/null-count-match.md#daily-null-count-match)|monitoring|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_null_count_match](../column/comparisons/null-count-match.md#monthly-null-count-match)|monitoring|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_null_count_match](../column/comparisons/null-count-match.md#daily-partition-null-count-match)|partitioned|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_null_count_match](../column/comparisons/null-count-match.md#monthly-partition-null-count-match)|partitioned|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|





### **datatype**


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_datatype_detected](../column/datatype/string-datatype-detected.md#profile-string-datatype-detected)|profiling|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type.|
|[daily_string_datatype_detected](../column/datatype/string-datatype-detected.md#daily-string-datatype-detected)|monitoring|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_datatype_detected](../column/datatype/string-datatype-detected.md#monthly-string-datatype-detected)|monitoring|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_datatype_detected](../column/datatype/string-datatype-detected.md#daily-partition-string-datatype-detected)|partitioned|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_datatype_detected](../column/datatype/string-datatype-detected.md#monthly-partition-string-datatype-detected)|partitioned|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_datatype_changed](../column/datatype/string-datatype-changed.md#profile-string-datatype-changed)|profiling|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|
|[daily_string_datatype_changed](../column/datatype/string-datatype-changed.md#daily-string-datatype-changed)|monitoring|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_datatype_changed](../column/datatype/string-datatype-changed.md#monthly-string-datatype-changed)|monitoring|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[daily_partition_string_datatype_changed](../column/datatype/string-datatype-changed.md#daily-partition-string-datatype-changed)|partitioned|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_datatype_changed](../column/datatype/string-datatype-changed.md#monthly-partition-string-datatype-changed)|partitioned|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each monthly partition.|





### **datetime**
Validates that the data in a date or time column is in the expected format and within predefined ranges.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_date_match_format_percent](../column/datetime/date-match-format-percent.md#profile-date-match-format-percent)|profiling|Verifies that the percentage of date values matching the given format in a column does not exceed the minimum accepted percentage.|
|[daily_date_match_format_percent](../column/datetime/date-match-format-percent.md#daily-date-match-format-percent)|monitoring|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily monitoring.|
|[monthly_date_match_format_percent](../column/datetime/date-match-format-percent.md#monthly-date-match-format-percent)|monitoring|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly monitoring.|
|[daily_partition_date_match_format_percent](../column/datetime/date-match-format-percent.md#daily-partition-date-match-format-percent)|partitioned|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_date_match_format_percent](../column/datetime/date-match-format-percent.md#monthly-partition-date-match-format-percent)|partitioned|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_date_values_in_future_percent](../column/datetime/date-values-in-future-percent.md#profile-date-values-in-future-percent)|profiling|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage.|
|[daily_date_values_in_future_percent](../column/datetime/date-values-in-future-percent.md#daily-date-values-in-future-percent)|monitoring|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_date_values_in_future_percent](../column/datetime/date-values-in-future-percent.md#monthly-date-values-in-future-percent)|monitoring|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_date_values_in_future_percent](../column/datetime/date-values-in-future-percent.md#daily-partition-date-values-in-future-percent)|partitioned|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_date_values_in_future_percent](../column/datetime/date-values-in-future-percent.md#monthly-partition-date-values-in-future-percent)|partitioned|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_datetime_value_in_range_date_percent](../column/datetime/datetime-value-in-range-date-percent.md#profile-datetime-value-in-range-date-percent)|profiling|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage.|
|[daily_datetime_value_in_range_date_percent](../column/datetime/datetime-value-in-range-date-percent.md#daily-datetime-value-in-range-date-percent)|monitoring|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_datetime_value_in_range_date_percent](../column/datetime/datetime-value-in-range-date-percent.md#monthly-datetime-value-in-range-date-percent)|monitoring|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_datetime_value_in_range_date_percent](../column/datetime/datetime-value-in-range-date-percent.md#daily-partition-datetime-value-in-range-date-percent)|partitioned|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_datetime_value_in_range_date_percent](../column/datetime/datetime-value-in-range-date-percent.md#monthly-partition-datetime-value-in-range-date-percent)|partitioned|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





### **integrity**
Checks the referential integrity of a column against a column in another table.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_foreign_key_not_match_count](../column/integrity/foreign-key-not-match-count.md#profile-foreign-key-not-match-count)|profiling|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count.|
|[daily_foreign_key_not_match_count](../column/integrity/foreign-key-not-match-count.md#daily-foreign-key-not-match-count)|monitoring|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_foreign_key_not_match_count](../column/integrity/foreign-key-not-match-count.md#monthly-foreign-key-not-match-count)|monitoring|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_foreign_key_not_match_count](../column/integrity/foreign-key-not-match-count.md#daily-partition-foreign-key-not-match-count)|partitioned|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_foreign_key_not_match_count](../column/integrity/foreign-key-not-match-count.md#monthly-partition-foreign-key-not-match-count)|partitioned|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_foreign_key_match_percent](../column/integrity/foreign-key-match-percent.md#profile-foreign-key-match-percent)|profiling|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count.|
|[daily_foreign_key_match_percent](../column/integrity/foreign-key-match-percent.md#daily-foreign-key-match-percent)|monitoring|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_foreign_key_match_percent](../column/integrity/foreign-key-match-percent.md#monthly-foreign-key-match-percent)|monitoring|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_foreign_key_match_percent](../column/integrity/foreign-key-match-percent.md#daily-partition-foreign-key-match-percent)|partitioned|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_foreign_key_match_percent](../column/integrity/foreign-key-match-percent.md#monthly-partition-foreign-key-match-percent)|partitioned|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|





### **nulls**
Checks for the presence of null or missing values in a column.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_count](../column/nulls/nulls-count.md#profile-nulls-count)|profiling|Detects columns with any null values when the max_count&#x3D;0. Verifies that the number of null values in a column does not exceed the maximum accepted count.|
|[daily_nulls_count](../column/nulls/nulls-count.md#daily-nulls-count)|monitoring|Detects columns with any null values when the max_count&#x3D;0. Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_nulls_count](../column/nulls/nulls-count.md#monthly-nulls-count)|monitoring|Detects columns with any null values when the max_count&#x3D;0. Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_nulls_count](../column/nulls/nulls-count.md#daily-partition-nulls-count)|partitioned|Detects columns with any null values when the max_count&#x3D;0. Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_nulls_count](../column/nulls/nulls-count.md#monthly-partition-nulls-count)|partitioned|Detects columns with any null values when the max_count&#x3D;0. Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent](../column/nulls/nulls-percent.md#profile-nulls-percent)|profiling|Verifies that the percent of null values in a column does not exceed the maximum accepted percentage.|
|[daily_nulls_percent](../column/nulls/nulls-percent.md#daily-nulls-percent)|monitoring|Verifies that the percentage of nulls in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_nulls_percent](../column/nulls/nulls-percent.md#monthly-nulls-percent)|monitoring|Verifies that the percentage of null values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_nulls_percent](../column/nulls/nulls-percent.md#daily-partition-nulls-percent)|partitioned|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_nulls_percent](../column/nulls/nulls-percent.md#monthly-partition-nulls-percent)|partitioned|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_anomaly_stationary_30_days](../column/nulls/nulls-percent-anomaly-stationary-30-days.md#profile-nulls-percent-anomaly-stationary-30-days)|profiling|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_nulls_percent_anomaly_stationary_30_days](../column/nulls/nulls-percent-anomaly-stationary-30-days.md#daily-nulls-percent-anomaly-stationary-30-days)|monitoring|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_partition_nulls_percent_anomaly_stationary_30_days](../column/nulls/nulls-percent-anomaly-stationary-30-days.md#daily-partition-nulls-percent-anomaly-stationary-30-days)|partitioned|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_anomaly_stationary](../column/nulls/nulls-percent-anomaly-stationary.md#profile-nulls-percent-anomaly-stationary)|profiling|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_nulls_percent_anomaly_stationary](../column/nulls/nulls-percent-anomaly-stationary.md#daily-nulls-percent-anomaly-stationary)|monitoring|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_partition_nulls_percent_anomaly_stationary](../column/nulls/nulls-percent-anomaly-stationary.md#daily-partition-nulls-percent-anomaly-stationary)|partitioned|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_change](../column/nulls/nulls-percent-change.md#profile-nulls-percent-change)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout.|
|[daily_nulls_percent_change](../column/nulls/nulls-percent-change.md#daily-nulls-percent-change)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since last readout.|
|[daily_partition_nulls_percent_change](../column/nulls/nulls-percent-change.md#daily-partition-nulls-percent-change)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_change_yesterday](../column/nulls/nulls-percent-change-yesterday.md#profile-nulls-percent-change-yesterday)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_nulls_percent_change_yesterday](../column/nulls/nulls-percent-change-yesterday.md#daily-nulls-percent-change-yesterday)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_nulls_percent_change_yesterday](../column/nulls/nulls-percent-change-yesterday.md#daily-partition-nulls-percent-change-yesterday)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_change_7_days](../column/nulls/nulls-percent-change-7-days.md#profile-nulls-percent-change-7-days)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|
|[daily_nulls_percent_change_7_days](../column/nulls/nulls-percent-change-7-days.md#daily-nulls-percent-change-7-days)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_nulls_percent_change_7_days](../column/nulls/nulls-percent-change-7-days.md#daily-partition-nulls-percent-change-7-days)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_change_30_days](../column/nulls/nulls-percent-change-30-days.md#profile-nulls-percent-change-30-days)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|
|[daily_nulls_percent_change_30_days](../column/nulls/nulls-percent-change-30-days.md#daily-nulls-percent-change-30-days)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_nulls_percent_change_30_days](../column/nulls/nulls-percent-change-30-days.md#daily-partition-nulls-percent-change-30-days)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_not_nulls_count](../column/nulls/not-nulls-count.md#profile-not-nulls-count)|profiling|Detects empty columns with the min_count&#x3D;0 rule. Verifies that the number of not null values in a column does not exceed the minimum accepted count.|
|[daily_not_nulls_count](../column/nulls/not-nulls-count.md#daily-not-nulls-count)|monitoring|Detects empty columns with the min_count&#x3D;0 rule. Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_not_nulls_count](../column/nulls/not-nulls-count.md#monthly-not-nulls-count)|monitoring|Detects empty columns with the min_count&#x3D;0 rule. Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_not_nulls_count](../column/nulls/not-nulls-count.md#daily-partition-not-nulls-count)|partitioned|Detects empty columns with the min_count&#x3D;0 rule. Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_not_nulls_count](../column/nulls/not-nulls-count.md#monthly-partition-not-nulls-count)|partitioned|Detects empty columns with the min_count&#x3D;0 rule. Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_not_nulls_percent](../column/nulls/not-nulls-percent.md#profile-not-nulls-percent)|profiling|Verifies that the percent of not null values in a column does not exceed the minimum accepted percentage.|
|[daily_not_nulls_percent](../column/nulls/not-nulls-percent.md#daily-not-nulls-percent)|monitoring|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_not_nulls_percent](../column/nulls/not-nulls-percent.md#monthly-not-nulls-percent)|monitoring|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_not_nulls_percent](../column/nulls/not-nulls-percent.md#daily-partition-not-nulls-percent)|partitioned|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_not_nulls_percent](../column/nulls/not-nulls-percent.md#monthly-partition-not-nulls-percent)|partitioned|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





### **numeric**
Validates that the data in a numeric column is in the expected format or within predefined ranges.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_negative_count](../column/numeric/negative-count.md#profile-negative-count)|profiling|Verifies that the number of negative values in a column does not exceed the maximum accepted count.|
|[daily_negative_count](../column/numeric/negative-count.md#daily-negative-count)|monitoring|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_negative_count](../column/numeric/negative-count.md#monthly-negative-count)|monitoring|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_negative_count](../column/numeric/negative-count.md#daily-partition-negative-count)|partitioned|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_negative_count](../column/numeric/negative-count.md#monthly-partition-negative-count)|partitioned|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_negative_percent](../column/numeric/negative-percent.md#profile-negative-percent)|profiling|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.|
|[daily_negative_percent](../column/numeric/negative-percent.md#daily-negative-percent)|monitoring|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_negative_percent](../column/numeric/negative-percent.md#monthly-negative-percent)|monitoring|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_negative_percent](../column/numeric/negative-percent.md#daily-partition-negative-percent)|partitioned|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_negative_percent](../column/numeric/negative-percent.md#monthly-partition-negative-percent)|partitioned|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_non_negative_count](../column/numeric/non-negative-count.md#profile-non-negative-count)|profiling|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count.|
|[daily_non_negative_count](../column/numeric/non-negative-count.md#daily-non-negative-count)|monitoring|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_non_negative_count](../column/numeric/non-negative-count.md#monthly-non-negative-count)|monitoring|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_non_negative_count](../column/numeric/non-negative-count.md#daily-partition-non-negative-count)|partitioned|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_non_negative_count](../column/numeric/non-negative-count.md#monthly-partition-non-negative-count)|partitioned|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_non_negative_percent](../column/numeric/non-negative-percent.md#profile-non-negative-percent)|profiling|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage.|
|[daily_non_negative_percent](../column/numeric/non-negative-percent.md#daily-non-negative-percent)|monitoring|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_non_negative_percent](../column/numeric/non-negative-percent.md#monthly-non-negative-percent)|monitoring|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_non_negative_percent](../column/numeric/non-negative-percent.md#daily-partition-non-negative-percent)|partitioned|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_non_negative_percent](../column/numeric/non-negative-percent.md#monthly-partition-non-negative-percent)|partitioned|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_expected_numbers_in_use_count](../column/numeric/expected-numbers-in-use-count.md#profile-expected-numbers-in-use-count)|profiling|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|
|[daily_expected_numbers_in_use_count](../column/numeric/expected-numbers-in-use-count.md#daily-expected-numbers-in-use-count)|monitoring|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_expected_numbers_in_use_count](../column/numeric/expected-numbers-in-use-count.md#monthly-expected-numbers-in-use-count)|monitoring|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_expected_numbers_in_use_count](../column/numeric/expected-numbers-in-use-count.md#daily-partition-expected-numbers-in-use-count)|partitioned|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_expected_numbers_in_use_count](../column/numeric/expected-numbers-in-use-count.md#monthly-partition-expected-numbers-in-use-count)|partitioned|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_number_value_in_set_percent](../column/numeric/number-value-in-set-percent.md#profile-number-value-in-set-percent)|profiling|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|
|[daily_number_value_in_set_percent](../column/numeric/number-value-in-set-percent.md#daily-number-value-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_number_value_in_set_percent](../column/numeric/number-value-in-set-percent.md#monthly-number-value-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_number_value_in_set_percent](../column/numeric/number-value-in-set-percent.md#daily-partition-number-value-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_number_value_in_set_percent](../column/numeric/number-value-in-set-percent.md#monthly-partition-number-value-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_values_in_range_numeric_percent](../column/numeric/values-in-range-numeric-percent.md#profile-values-in-range-numeric-percent)|profiling|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|
|[daily_values_in_range_numeric_percent](../column/numeric/values-in-range-numeric-percent.md#daily-values-in-range-numeric-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_values_in_range_numeric_percent](../column/numeric/values-in-range-numeric-percent.md#monthly-values-in-range-numeric-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_values_in_range_numeric_percent](../column/numeric/values-in-range-numeric-percent.md#daily-partition-values-in-range-numeric-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_values_in_range_numeric_percent](../column/numeric/values-in-range-numeric-percent.md#monthly-partition-values-in-range-numeric-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_values_in_range_integers_percent](../column/numeric/values-in-range-integers-percent.md#profile-values-in-range-integers-percent)|profiling|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|
|[daily_values_in_range_integers_percent](../column/numeric/values-in-range-integers-percent.md#daily-values-in-range-integers-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_values_in_range_integers_percent](../column/numeric/values-in-range-integers-percent.md#monthly-values-in-range-integers-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_values_in_range_integers_percent](../column/numeric/values-in-range-integers-percent.md#daily-partition-values-in-range-integers-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_values_in_range_integers_percent](../column/numeric/values-in-range-integers-percent.md#monthly-partition-values-in-range-integers-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_value_below_min_value_count](../column/numeric/value-below-min-value-count.md#profile-value-below-min-value-count)|profiling|The check counts the number of values in the column that is below the value defined by the user as a parameter.|
|[daily_value_below_min_value_count](../column/numeric/value-below-min-value-count.md#daily-value-below-min-value-count)|monitoring|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_value_below_min_value_count](../column/numeric/value-below-min-value-count.md#monthly-value-below-min-value-count)|monitoring|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_below_min_value_count](../column/numeric/value-below-min-value-count.md#daily-partition-value-below-min-value-count)|partitioned|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_below_min_value_count](../column/numeric/value-below-min-value-count.md#monthly-partition-value-below-min-value-count)|partitioned|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_value_below_min_value_percent](../column/numeric/value-below-min-value-percent.md#profile-value-below-min-value-percent)|profiling|The check counts the percentage of values in the column that is below the value defined by the user as a parameter.|
|[daily_value_below_min_value_percent](../column/numeric/value-below-min-value-percent.md#daily-value-below-min-value-percent)|monitoring|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_value_below_min_value_percent](../column/numeric/value-below-min-value-percent.md#monthly-value-below-min-value-percent)|monitoring|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_below_min_value_percent](../column/numeric/value-below-min-value-percent.md#daily-partition-value-below-min-value-percent)|partitioned|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_below_min_value_percent](../column/numeric/value-below-min-value-percent.md#monthly-partition-value-below-min-value-percent)|partitioned|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_value_above_max_value_count](../column/numeric/value-above-max-value-count.md#profile-value-above-max-value-count)|profiling|The check counts the number of values in the column that is above the value defined by the user as a parameter.|
|[daily_value_above_max_value_count](../column/numeric/value-above-max-value-count.md#daily-value-above-max-value-count)|monitoring|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_value_above_max_value_count](../column/numeric/value-above-max-value-count.md#monthly-value-above-max-value-count)|monitoring|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_above_max_value_count](../column/numeric/value-above-max-value-count.md#daily-partition-value-above-max-value-count)|partitioned|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_above_max_value_count](../column/numeric/value-above-max-value-count.md#monthly-partition-value-above-max-value-count)|partitioned|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_value_above_max_value_percent](../column/numeric/value-above-max-value-percent.md#profile-value-above-max-value-percent)|profiling|The check counts the percentage of values in the column that is above the value defined by the user as a parameter.|
|[daily_value_above_max_value_percent](../column/numeric/value-above-max-value-percent.md#daily-value-above-max-value-percent)|monitoring|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_value_above_max_value_percent](../column/numeric/value-above-max-value-percent.md#monthly-value-above-max-value-percent)|monitoring|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_above_max_value_percent](../column/numeric/value-above-max-value-percent.md#daily-partition-value-above-max-value-percent)|partitioned|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_above_max_value_percent](../column/numeric/value-above-max-value-percent.md#monthly-partition-value-above-max-value-percent)|partitioned|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_max_in_range](../column/numeric/max-in-range.md#profile-max-in-range)|profiling|Verifies that the maximal value in a column is not outside the set range.|
|[daily_max_in_range](../column/numeric/max-in-range.md#daily-max-in-range)|monitoring|Verifies that the maximal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_max_in_range](../column/numeric/max-in-range.md#monthly-max-in-range)|monitoring|Verifies that the maximal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_max_in_range](../column/numeric/max-in-range.md#daily-partition-max-in-range)|partitioned|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_max_in_range](../column/numeric/max-in-range.md#monthly-partition-max-in-range)|partitioned|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_min_in_range](../column/numeric/min-in-range.md#profile-min-in-range)|profiling|Verifies that the minimal value in a column is not outside the set range.|
|[daily_min_in_range](../column/numeric/min-in-range.md#daily-min-in-range)|monitoring|Verifies that the minimal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_min_in_range](../column/numeric/min-in-range.md#monthly-min-in-range)|monitoring|Verifies that the minimal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_min_in_range](../column/numeric/min-in-range.md#daily-partition-min-in-range)|partitioned|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_min_in_range](../column/numeric/min-in-range.md#monthly-partition-min-in-range)|partitioned|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_in_range](../column/numeric/mean-in-range.md#profile-mean-in-range)|profiling|Verifies that the average (mean) of all values in a column is not outside the set range.|
|[daily_mean_in_range](../column/numeric/mean-in-range.md#daily-mean-in-range)|monitoring|Verifies that the average (mean) of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_mean_in_range](../column/numeric/mean-in-range.md#monthly-mean-in-range)|monitoring|Verifies that the average (mean) of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_mean_in_range](../column/numeric/mean-in-range.md#daily-partition-mean-in-range)|partitioned|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_mean_in_range](../column/numeric/mean-in-range.md#monthly-partition-mean-in-range)|partitioned|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_percentile_in_range](../column/numeric/percentile-in-range.md#profile-percentile-in-range)|profiling|Verifies that the percentile of all values in a column is not outside the set range.|
|[daily_percentile_in_range](../column/numeric/percentile-in-range.md#daily-percentile-in-range)|monitoring|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_percentile_in_range](../column/numeric/percentile-in-range.md#monthly-percentile-in-range)|monitoring|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_percentile_in_range](../column/numeric/percentile-in-range.md#daily-partition-percentile-in-range)|partitioned|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_percentile_in_range](../column/numeric/percentile-in-range.md#monthly-partition-percentile-in-range)|partitioned|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_in_range](../column/numeric/median-in-range.md#profile-median-in-range)|profiling|Verifies that the median of all values in a column is not outside the set range.|
|[daily_median_in_range](../column/numeric/median-in-range.md#daily-median-in-range)|monitoring|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[monthly_median_in_range](../column/numeric/median-in-range.md#monthly-median-in-range)|monitoring|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_median_in_range](../column/numeric/median-in-range.md#daily-partition-median-in-range)|partitioned|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_median_in_range](../column/numeric/median-in-range.md#monthly-partition-median-in-range)|partitioned|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_percentile_10_in_range](../column/numeric/percentile-10-in-range.md#profile-percentile-10-in-range)|profiling|Verifies that the percentile 10 of all values in a column is not outside the set range.|
|[daily_percentile_10_in_range](../column/numeric/percentile-10-in-range.md#daily-percentile-10-in-range)|monitoring|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_percentile_10_in_range](../column/numeric/percentile-10-in-range.md#monthly-percentile-10-in-range)|monitoring|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_percentile_10_in_range](../column/numeric/percentile-10-in-range.md#daily-partition-percentile-10-in-range)|partitioned|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_percentile_10_in_range](../column/numeric/percentile-10-in-range.md#monthly-partition-percentile-10-in-range)|partitioned|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_percentile_25_in_range](../column/numeric/percentile-25-in-range.md#profile-percentile-25-in-range)|profiling|Verifies that the percentile 25 of all values in a column is not outside the set range.|
|[daily_percentile_25_in_range](../column/numeric/percentile-25-in-range.md#daily-percentile-25-in-range)|monitoring|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_percentile_25_in_range](../column/numeric/percentile-25-in-range.md#monthly-percentile-25-in-range)|monitoring|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_percentile_25_in_range](../column/numeric/percentile-25-in-range.md#daily-partition-percentile-25-in-range)|partitioned|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_percentile_25_in_range](../column/numeric/percentile-25-in-range.md#monthly-partition-percentile-25-in-range)|partitioned|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_percentile_75_in_range](../column/numeric/percentile-75-in-range.md#profile-percentile-75-in-range)|profiling|Verifies that the percentile 75 of all values in a column is not outside the set range.|
|[daily_percentile_75_in_range](../column/numeric/percentile-75-in-range.md#daily-percentile-75-in-range)|monitoring|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_percentile_75_in_range](../column/numeric/percentile-75-in-range.md#monthly-percentile-75-in-range)|monitoring|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_percentile_75_in_range](../column/numeric/percentile-75-in-range.md#daily-partition-percentile-75-in-range)|partitioned|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_percentile_75_in_range](../column/numeric/percentile-75-in-range.md#monthly-partition-percentile-75-in-range)|partitioned|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_percentile_90_in_range](../column/numeric/percentile-90-in-range.md#profile-percentile-90-in-range)|profiling|Verifies that the percentile 90 of all values in a column is not outside the set range.|
|[daily_percentile_90_in_range](../column/numeric/percentile-90-in-range.md#daily-percentile-90-in-range)|monitoring|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_percentile_90_in_range](../column/numeric/percentile-90-in-range.md#monthly-percentile-90-in-range)|monitoring|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_percentile_90_in_range](../column/numeric/percentile-90-in-range.md#daily-partition-percentile-90-in-range)|partitioned|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_percentile_90_in_range](../column/numeric/percentile-90-in-range.md#monthly-partition-percentile-90-in-range)|partitioned|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sample_stddev_in_range](../column/numeric/sample-stddev-in-range.md#profile-sample-stddev-in-range)|profiling|Verifies that the sample standard deviation of all values in a column is not outside the set range.|
|[daily_sample_stddev_in_range](../column/numeric/sample-stddev-in-range.md#daily-sample-stddev-in-range)|monitoring|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sample_stddev_in_range](../column/numeric/sample-stddev-in-range.md#monthly-sample-stddev-in-range)|monitoring|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sample_stddev_in_range](../column/numeric/sample-stddev-in-range.md#daily-partition-sample-stddev-in-range)|partitioned|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sample_stddev_in_range](../column/numeric/sample-stddev-in-range.md#monthly-partition-sample-stddev-in-range)|partitioned|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_population_stddev_in_range](../column/numeric/population-stddev-in-range.md#profile-population-stddev-in-range)|profiling|Verifies that the population standard deviation of all values in a column is not outside the set range.|
|[daily_population_stddev_in_range](../column/numeric/population-stddev-in-range.md#daily-population-stddev-in-range)|monitoring|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_population_stddev_in_range](../column/numeric/population-stddev-in-range.md#monthly-population-stddev-in-range)|monitoring|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_population_stddev_in_range](../column/numeric/population-stddev-in-range.md#daily-partition-population-stddev-in-range)|partitioned|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_population_stddev_in_range](../column/numeric/population-stddev-in-range.md#monthly-partition-population-stddev-in-range)|partitioned|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sample_variance_in_range](../column/numeric/sample-variance-in-range.md#profile-sample-variance-in-range)|profiling|Verifies that the sample variance of all values in a column is not outside the set range.|
|[daily_sample_variance_in_range](../column/numeric/sample-variance-in-range.md#daily-sample-variance-in-range)|monitoring|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sample_variance_in_range](../column/numeric/sample-variance-in-range.md#monthly-sample-variance-in-range)|monitoring|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sample_variance_in_range](../column/numeric/sample-variance-in-range.md#daily-partition-sample-variance-in-range)|partitioned|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sample_variance_in_range](../column/numeric/sample-variance-in-range.md#monthly-partition-sample-variance-in-range)|partitioned|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_population_variance_in_range](../column/numeric/population-variance-in-range.md#profile-population-variance-in-range)|profiling|Verifies that the population variance of all values in a column is not outside the set range.|
|[daily_population_variance_in_range](../column/numeric/population-variance-in-range.md#daily-population-variance-in-range)|monitoring|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_population_variance_in_range](../column/numeric/population-variance-in-range.md#monthly-population-variance-in-range)|monitoring|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_population_variance_in_range](../column/numeric/population-variance-in-range.md#daily-partition-population-variance-in-range)|partitioned|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_population_variance_in_range](../column/numeric/population-variance-in-range.md#monthly-partition-population-variance-in-range)|partitioned|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_in_range](../column/numeric/sum-in-range.md#profile-sum-in-range)|profiling|Verifies that the sum of all values in a column is not outside the set range.|
|[daily_sum_in_range](../column/numeric/sum-in-range.md#daily-sum-in-range)|monitoring|Verifies that the sum of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sum_in_range](../column/numeric/sum-in-range.md#monthly-sum-in-range)|monitoring|Verifies that the sum of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sum_in_range](../column/numeric/sum-in-range.md#daily-partition-sum-in-range)|partitioned|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sum_in_range](../column/numeric/sum-in-range.md#monthly-partition-sum-in-range)|partitioned|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_invalid_latitude_count](../column/numeric/invalid-latitude-count.md#profile-invalid-latitude-count)|profiling|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count.|
|[daily_invalid_latitude_count](../column/numeric/invalid-latitude-count.md#daily-invalid-latitude-count)|monitoring|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_invalid_latitude_count](../column/numeric/invalid-latitude-count.md#monthly-invalid-latitude-count)|monitoring|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_invalid_latitude_count](../column/numeric/invalid-latitude-count.md#daily-partition-invalid-latitude-count)|partitioned|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_invalid_latitude_count](../column/numeric/invalid-latitude-count.md#monthly-partition-invalid-latitude-count)|partitioned|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_valid_latitude_percent](../column/numeric/valid-latitude-percent.md#profile-valid-latitude-percent)|profiling|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage.|
|[daily_valid_latitude_percent](../column/numeric/valid-latitude-percent.md#daily-valid-latitude-percent)|monitoring|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_valid_latitude_percent](../column/numeric/valid-latitude-percent.md#monthly-valid-latitude-percent)|monitoring|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_latitude_percent](../column/numeric/valid-latitude-percent.md#daily-partition-valid-latitude-percent)|partitioned|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_latitude_percent](../column/numeric/valid-latitude-percent.md#monthly-partition-valid-latitude-percent)|partitioned|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_invalid_longitude_count](../column/numeric/invalid-longitude-count.md#profile-invalid-longitude-count)|profiling|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count.|
|[daily_invalid_longitude_count](../column/numeric/invalid-longitude-count.md#daily-invalid-longitude-count)|monitoring|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_invalid_longitude_count](../column/numeric/invalid-longitude-count.md#monthly-invalid-longitude-count)|monitoring|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_invalid_longitude_count](../column/numeric/invalid-longitude-count.md#daily-partition-invalid-longitude-count)|partitioned|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_invalid_longitude_count](../column/numeric/invalid-longitude-count.md#monthly-partition-invalid-longitude-count)|partitioned|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_valid_longitude_percent](../column/numeric/valid-longitude-percent.md#profile-valid-longitude-percent)|profiling|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage.|
|[daily_valid_longitude_percent](../column/numeric/valid-longitude-percent.md#daily-valid-longitude-percent)|monitoring|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_valid_longitude_percent](../column/numeric/valid-longitude-percent.md#monthly-valid-longitude-percent)|monitoring|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_longitude_percent](../column/numeric/valid-longitude-percent.md#daily-partition-valid-longitude-percent)|partitioned|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_longitude_percent](../column/numeric/valid-longitude-percent.md#monthly-partition-valid-longitude-percent)|partitioned|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





### **pii**
Checks for the presence of sensitive or personally identifiable information (PII) in a column such as email, phone, zip code, IP4 and IP6 addresses.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_contains_usa_phone_percent](../column/pii/contains-usa-phone-percent.md#profile-contains-usa-phone-percent)|profiling|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage.|
|[daily_contains_usa_phone_percent](../column/pii/contains-usa-phone-percent.md#daily-contains-usa-phone-percent)|monitoring|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_usa_phone_percent](../column/pii/contains-usa-phone-percent.md#monthly-contains-usa-phone-percent)|monitoring|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_usa_phone_percent](../column/pii/contains-usa-phone-percent.md#daily-partition-contains-usa-phone-percent)|partitioned|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_usa_phone_percent](../column/pii/contains-usa-phone-percent.md#monthly-partition-contains-usa-phone-percent)|partitioned|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_contains_usa_zipcode_percent](../column/pii/contains-usa-zipcode-percent.md#profile-contains-usa-zipcode-percent)|profiling|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.|
|[daily_contains_usa_zipcode_percent](../column/pii/contains-usa-zipcode-percent.md#daily-contains-usa-zipcode-percent)|monitoring|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_usa_zipcode_percent](../column/pii/contains-usa-zipcode-percent.md#monthly-contains-usa-zipcode-percent)|monitoring|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_usa_zipcode_percent](../column/pii/contains-usa-zipcode-percent.md#daily-partition-contains-usa-zipcode-percent)|partitioned|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_usa_zipcode_percent](../column/pii/contains-usa-zipcode-percent.md#monthly-partition-contains-usa-zipcode-percent)|partitioned|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_contains_email_percent](../column/pii/contains-email-percent.md#profile-contains-email-percent)|profiling|Verifies that the percentage of rows that contains valid emails in a column does not exceed the minimum accepted percentage.|
|[daily_contains_email_percent](../column/pii/contains-email-percent.md#daily-contains-email-percent)|monitoring|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_email_percent](../column/pii/contains-email-percent.md#monthly-contains-email-percent)|monitoring|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_email_percent](../column/pii/contains-email-percent.md#daily-partition-contains-email-percent)|partitioned|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_email_percent](../column/pii/contains-email-percent.md#monthly-partition-contains-email-percent)|partitioned|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_contains_ip4_percent](../column/pii/contains-ip4-percent.md#profile-contains-ip4-percent)|profiling|Verifies that the percentage of rows that contains valid IP4 address values in a column does not fall below the minimum accepted percentage.|
|[daily_contains_ip4_percent](../column/pii/contains-ip4-percent.md#daily-contains-ip4-percent)|monitoring|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_ip4_percent](../column/pii/contains-ip4-percent.md#monthly-contains-ip4-percent)|monitoring|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_ip4_percent](../column/pii/contains-ip4-percent.md#daily-partition-contains-ip4-percent)|partitioned|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_ip4_percent](../column/pii/contains-ip4-percent.md#monthly-partition-contains-ip4-percent)|partitioned|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_contains_ip6_percent](../column/pii/contains-ip6-percent.md#profile-contains-ip6-percent)|profiling|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage.|
|[daily_contains_ip6_percent](../column/pii/contains-ip6-percent.md#daily-contains-ip6-percent)|monitoring|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_ip6_percent](../column/pii/contains-ip6-percent.md#monthly-contains-ip6-percent)|monitoring|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_ip6_percent](../column/pii/contains-ip6-percent.md#daily-partition-contains-ip6-percent)|partitioned|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_ip6_percent](../column/pii/contains-ip6-percent.md#monthly-partition-contains-ip6-percent)|partitioned|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





### **schema**
Detects schema drifts such as a column is missing or the data type has changed.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_exists](../column/schema/column-exists.md#profile-column-exists)|profiling|Checks the metadata of the monitored table and verifies if the column exists.|
|[daily_column_exists](../column/schema/column-exists.md#daily-column-exists)|monitoring|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each day when the data quality check was evaluated.|
|[monthly_column_exists](../column/schema/column-exists.md#monthly-column-exists)|monitoring|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_type_changed](../column/schema/column-type-changed.md#profile-column-type-changed)|profiling|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed.|
|[daily_column_type_changed](../column/schema/column-type-changed.md#daily-column-type-changed)|monitoring|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last day. Stores the most recent hash for each day when the data quality check was evaluated.|
|[monthly_column_type_changed](../column/schema/column-type-changed.md#monthly-column-type-changed)|monitoring|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last month. Stores the most recent hash for each month when the data quality check was evaluated.|





### **sql**
Validate data against user-defined SQL queries at the column level. Checks in this group allows to validate that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sql_condition_passed_percent_on_column](../column/sql/sql-condition-passed-percent-on-column.md#profile-sql-condition-passed-percent-on-column)|profiling|Verifies that a minimum percentage of rows passed a custom SQL condition (expression).|
|[daily_sql_condition_passed_percent_on_column](../column/sql/sql-condition-passed-percent-on-column.md#daily-sql-condition-passed-percent-on-column)|monitoring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_condition_passed_percent_on_column](../column/sql/sql-condition-passed-percent-on-column.md#monthly-sql-condition-passed-percent-on-column)|monitoring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_condition_passed_percent_on_column](../column/sql/sql-condition-passed-percent-on-column.md#daily-partition-sql-condition-passed-percent-on-column)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_condition_passed_percent_on_column](../column/sql/sql-condition-passed-percent-on-column.md#monthly-partition-sql-condition-passed-percent-on-column)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sql_condition_failed_count_on_column](../column/sql/sql-condition-failed-count-on-column.md#profile-sql-condition-failed-count-on-column)|profiling|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count.|
|[daily_sql_condition_failed_count_on_column](../column/sql/sql-condition-failed-count-on-column.md#daily-sql-condition-failed-count-on-column)|monitoring|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_condition_failed_count_on_column](../column/sql/sql-condition-failed-count-on-column.md#monthly-sql-condition-failed-count-on-column)|monitoring|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_condition_failed_count_on_column](../column/sql/sql-condition-failed-count-on-column.md#daily-partition-sql-condition-failed-count-on-column)|partitioned|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_condition_failed_count_on_column](../column/sql/sql-condition-failed-count-on-column.md#monthly-partition-sql-condition-failed-count-on-column)|partitioned|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sql_aggregate_expr_column](../column/sql/sql-aggregate-expr-column.md#profile-sql-aggregate-expr-column)|profiling|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|
|[daily_sql_aggregate_expr_column](../column/sql/sql-aggregate-expr-column.md#daily-sql-aggregate-expr-column)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_aggregate_expr_column](../column/sql/sql-aggregate-expr-column.md#monthly-sql-aggregate-expr-column)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_aggregate_expr_column](../column/sql/sql-aggregate-expr-column.md#daily-partition-sql-aggregate-expr-column)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_aggregate_expr_column](../column/sql/sql-aggregate-expr-column.md#monthly-partition-sql-aggregate-expr-column)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|





### **strings**
Validates that the data in a string column match the expected format or pattern.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_max_length](../column/strings/string-max-length.md#profile-string-max-length)|profiling|Verifies that the length of string in a column does not exceed the maximum accepted length.|
|[daily_string_max_length](../column/strings/string-max-length.md#daily-string-max-length)|monitoring|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_max_length](../column/strings/string-max-length.md#monthly-string-max-length)|monitoring|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_max_length](../column/strings/string-max-length.md#daily-partition-string-max-length)|partitioned|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_max_length](../column/strings/string-max-length.md#monthly-partition-string-max-length)|partitioned|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_min_length](../column/strings/string-min-length.md#profile-string-min-length)|profiling|Verifies that the length of string in a column does not fall below the minimum accepted length.|
|[daily_string_min_length](../column/strings/string-min-length.md#daily-string-min-length)|monitoring|Verifies that the length of string in a column does not fall below the minimum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_min_length](../column/strings/string-min-length.md#monthly-string-min-length)|monitoring|Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_min_length](../column/strings/string-min-length.md#daily-partition-string-min-length)|partitioned|Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_min_length](../column/strings/string-min-length.md#monthly-partition-string-min-length)|partitioned|Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_mean_length](../column/strings/string-mean-length.md#profile-string-mean-length)|profiling|Verifies that the length of string in a column does not exceed the mean accepted length.|
|[daily_string_mean_length](../column/strings/string-mean-length.md#daily-string-mean-length)|monitoring|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_mean_length](../column/strings/string-mean-length.md#monthly-string-mean-length)|monitoring|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_mean_length](../column/strings/string-mean-length.md#daily-partition-string-mean-length)|partitioned|Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_mean_length](../column/strings/string-mean-length.md#monthly-partition-string-mean-length)|partitioned|Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_length_below_min_length_count](../column/strings/string-length-below-min-length-count.md#profile-string-length-below-min-length-count)|profiling|The check counts the number of strings in the column that is below the length defined by the user as a parameter.|
|[daily_string_length_below_min_length_count](../column/strings/string-length-below-min-length-count.md#daily-string-length-below-min-length-count)|monitoring|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_below_min_length_count](../column/strings/string-length-below-min-length-count.md#monthly-string-length-below-min-length-count)|monitoring|The check counts those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_below_min_length_count](../column/strings/string-length-below-min-length-count.md#daily-partition-string-length-below-min-length-count)|partitioned|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_below_min_length_count](../column/strings/string-length-below-min-length-count.md#monthly-partition-string-length-below-min-length-count)|partitioned|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_length_below_min_length_percent](../column/strings/string-length-below-min-length-percent.md#profile-string-length-below-min-length-percent)|profiling|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter.|
|[daily_string_length_below_min_length_percent](../column/strings/string-length-below-min-length-percent.md#daily-string-length-below-min-length-percent)|monitoring|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_below_min_length_percent](../column/strings/string-length-below-min-length-percent.md#monthly-string-length-below-min-length-percent)|monitoring|The check counts percentage of those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_below_min_length_percent](../column/strings/string-length-below-min-length-percent.md#daily-partition-string-length-below-min-length-percent)|partitioned|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_below_min_length_percent](../column/strings/string-length-below-min-length-percent.md#monthly-partition-string-length-below-min-length-percent)|partitioned|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_length_above_max_length_count](../column/strings/string-length-above-max-length-count.md#profile-string-length-above-max-length-count)|profiling|The check counts the number of strings in the column that is above the length defined by the user as a parameter.|
|[daily_string_length_above_max_length_count](../column/strings/string-length-above-max-length-count.md#daily-string-length-above-max-length-count)|monitoring|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_above_max_length_count](../column/strings/string-length-above-max-length-count.md#monthly-string-length-above-max-length-count)|monitoring|The check counts those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_above_max_length_count](../column/strings/string-length-above-max-length-count.md#daily-partition-string-length-above-max-length-count)|partitioned|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_above_max_length_count](../column/strings/string-length-above-max-length-count.md#monthly-partition-string-length-above-max-length-count)|partitioned|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_length_above_max_length_percent](../column/strings/string-length-above-max-length-percent.md#profile-string-length-above-max-length-percent)|profiling|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter.|
|[daily_string_length_above_max_length_percent](../column/strings/string-length-above-max-length-percent.md#daily-string-length-above-max-length-percent)|monitoring|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_above_max_length_percent](../column/strings/string-length-above-max-length-percent.md#monthly-string-length-above-max-length-percent)|monitoring|The check counts percentage of those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_above_max_length_percent](../column/strings/string-length-above-max-length-percent.md#daily-partition-string-length-above-max-length-percent)|partitioned|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_above_max_length_percent](../column/strings/string-length-above-max-length-percent.md#monthly-partition-string-length-above-max-length-percent)|partitioned|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_length_in_range_percent](../column/strings/string-length-in-range-percent.md#profile-string-length-in-range-percent)|profiling|The check counts the percentage of those strings with length in the range provided by the user in the column. |
|[daily_string_length_in_range_percent](../column/strings/string-length-in-range-percent.md#daily-string-length-in-range-percent)|monitoring|The check counts the percentage of those strings with length in the range provided by the user in the column. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_in_range_percent](../column/strings/string-length-in-range-percent.md#monthly-string-length-in-range-percent)|monitoring|The check counts percentage of those strings with length in the range provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_in_range_percent](../column/strings/string-length-in-range-percent.md#daily-partition-string-length-in-range-percent)|partitioned|The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_in_range_percent](../column/strings/string-length-in-range-percent.md#monthly-partition-string-length-in-range-percent)|partitioned|The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_empty_count](../column/strings/string-empty-count.md#profile-string-empty-count)|profiling|Verifies that empty strings in a column does not exceed the maximum accepted count.|
|[daily_string_empty_count](../column/strings/string-empty-count.md#daily-string-empty-count)|monitoring|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_empty_count](../column/strings/string-empty-count.md#monthly-string-empty-count)|monitoring|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_empty_count](../column/strings/string-empty-count.md#daily-partition-string-empty-count)|partitioned|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_empty_count](../column/strings/string-empty-count.md#monthly-partition-string-empty-count)|partitioned|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_empty_percent](../column/strings/string-empty-percent.md#profile-string-empty-percent)|profiling|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.|
|[daily_string_empty_percent](../column/strings/string-empty-percent.md#daily-string-empty-percent)|monitoring|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_empty_percent](../column/strings/string-empty-percent.md#monthly-string-empty-percent)|monitoring|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_empty_percent](../column/strings/string-empty-percent.md#daily-partition-string-empty-percent)|partitioned|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_empty_percent](../column/strings/string-empty-percent.md#monthly-partition-string-empty-percent)|partitioned|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_whitespace_count](../column/strings/string-whitespace-count.md#profile-string-whitespace-count)|profiling|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count.|
|[daily_string_whitespace_count](../column/strings/string-whitespace-count.md#daily-string-whitespace-count)|monitoring|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_whitespace_count](../column/strings/string-whitespace-count.md#monthly-string-whitespace-count)|monitoring|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_whitespace_count](../column/strings/string-whitespace-count.md#daily-partition-string-whitespace-count)|partitioned|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_whitespace_count](../column/strings/string-whitespace-count.md#monthly-partition-string-whitespace-count)|partitioned|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_whitespace_percent](../column/strings/string-whitespace-percent.md#profile-string-whitespace-percent)|profiling|Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.|
|[daily_string_whitespace_percent](../column/strings/string-whitespace-percent.md#daily-string-whitespace-percent)|monitoring|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_whitespace_percent](../column/strings/string-whitespace-percent.md#monthly-string-whitespace-percent)|monitoring|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_whitespace_percent](../column/strings/string-whitespace-percent.md#daily-partition-string-whitespace-percent)|partitioned|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_whitespace_percent](../column/strings/string-whitespace-percent.md#monthly-partition-string-whitespace-percent)|partitioned|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_surrounded_by_whitespace_count](../column/strings/string-surrounded-by-whitespace-count.md#profile-string-surrounded-by-whitespace-count)|profiling|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count.|
|[daily_string_surrounded_by_whitespace_count](../column/strings/string-surrounded-by-whitespace-count.md#daily-string-surrounded-by-whitespace-count)|monitoring|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_surrounded_by_whitespace_count](../column/strings/string-surrounded-by-whitespace-count.md#monthly-string-surrounded-by-whitespace-count)|monitoring|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_surrounded_by_whitespace_count](../column/strings/string-surrounded-by-whitespace-count.md#daily-partition-string-surrounded-by-whitespace-count)|partitioned|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_surrounded_by_whitespace_count](../column/strings/string-surrounded-by-whitespace-count.md#monthly-partition-string-surrounded-by-whitespace-count)|partitioned|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_surrounded_by_whitespace_percent](../column/strings/string-surrounded-by-whitespace-percent.md#profile-string-surrounded-by-whitespace-percent)|profiling|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage.|
|[daily_string_surrounded_by_whitespace_percent](../column/strings/string-surrounded-by-whitespace-percent.md#daily-string-surrounded-by-whitespace-percent)|monitoring|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_surrounded_by_whitespace_percent](../column/strings/string-surrounded-by-whitespace-percent.md#monthly-string-surrounded-by-whitespace-percent)|monitoring|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_surrounded_by_whitespace_percent](../column/strings/string-surrounded-by-whitespace-percent.md#daily-partition-string-surrounded-by-whitespace-percent)|partitioned|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_surrounded_by_whitespace_percent](../column/strings/string-surrounded-by-whitespace-percent.md#monthly-partition-string-surrounded-by-whitespace-percent)|partitioned|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_null_placeholder_count](../column/strings/string-null-placeholder-count.md#profile-string-null-placeholder-count)|profiling|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count.|
|[daily_string_null_placeholder_count](../column/strings/string-null-placeholder-count.md#daily-string-null-placeholder-count)|monitoring|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_null_placeholder_count](../column/strings/string-null-placeholder-count.md#monthly-string-null-placeholder-count)|monitoring|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_null_placeholder_count](../column/strings/string-null-placeholder-count.md#daily-partition-string-null-placeholder-count)|partitioned|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_null_placeholder_count](../column/strings/string-null-placeholder-count.md#monthly-partition-string-null-placeholder-count)|partitioned|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_null_placeholder_percent](../column/strings/string-null-placeholder-percent.md#profile-string-null-placeholder-percent)|profiling|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.|
|[daily_string_null_placeholder_percent](../column/strings/string-null-placeholder-percent.md#daily-string-null-placeholder-percent)|monitoring|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_null_placeholder_percent](../column/strings/string-null-placeholder-percent.md#monthly-string-null-placeholder-percent)|monitoring|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_null_placeholder_percent](../column/strings/string-null-placeholder-percent.md#daily-partition-string-null-placeholder-percent)|partitioned|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_null_placeholder_percent](../column/strings/string-null-placeholder-percent.md#monthly-partition-string-null-placeholder-percent)|partitioned|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_boolean_placeholder_percent](../column/strings/string-boolean-placeholder-percent.md#profile-string-boolean-placeholder-percent)|profiling|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage.|
|[daily_string_boolean_placeholder_percent](../column/strings/string-boolean-placeholder-percent.md#daily-string-boolean-placeholder-percent)|monitoring|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_boolean_placeholder_percent](../column/strings/string-boolean-placeholder-percent.md#monthly-string-boolean-placeholder-percent)|monitoring|Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_boolean_placeholder_percent](../column/strings/string-boolean-placeholder-percent.md#daily-partition-string-boolean-placeholder-percent)|partitioned|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_boolean_placeholder_percent](../column/strings/string-boolean-placeholder-percent.md#monthly-partition-string-boolean-placeholder-percent)|partitioned|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_parsable_to_integer_percent](../column/strings/string-parsable-to-integer-percent.md#profile-string-parsable-to-integer-percent)|profiling|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage.|
|[daily_string_parsable_to_integer_percent](../column/strings/string-parsable-to-integer-percent.md#daily-string-parsable-to-integer-percent)|monitoring|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_parsable_to_integer_percent](../column/strings/string-parsable-to-integer-percent.md#monthly-string-parsable-to-integer-percent)|monitoring|Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_parsable_to_integer_percent](../column/strings/string-parsable-to-integer-percent.md#daily-partition-string-parsable-to-integer-percent)|partitioned|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_parsable_to_integer_percent](../column/strings/string-parsable-to-integer-percent.md#monthly-partition-string-parsable-to-integer-percent)|partitioned|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_parsable_to_float_percent](../column/strings/string-parsable-to-float-percent.md#profile-string-parsable-to-float-percent)|profiling|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage.|
|[daily_string_parsable_to_float_percent](../column/strings/string-parsable-to-float-percent.md#daily-string-parsable-to-float-percent)|monitoring|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_parsable_to_float_percent](../column/strings/string-parsable-to-float-percent.md#monthly-string-parsable-to-float-percent)|monitoring|Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_parsable_to_float_percent](../column/strings/string-parsable-to-float-percent.md#daily-partition-string-parsable-to-float-percent)|partitioned|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_parsable_to_float_percent](../column/strings/string-parsable-to-float-percent.md#monthly-partition-string-parsable-to-float-percent)|partitioned|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_expected_strings_in_use_count](../column/strings/expected-strings-in-use-count.md#profile-expected-strings-in-use-count)|profiling|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|
|[daily_expected_strings_in_use_count](../column/strings/expected-strings-in-use-count.md#daily-expected-strings-in-use-count)|monitoring|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_expected_strings_in_use_count](../column/strings/expected-strings-in-use-count.md#monthly-expected-strings-in-use-count)|monitoring|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_expected_strings_in_use_count](../column/strings/expected-strings-in-use-count.md#daily-partition-expected-strings-in-use-count)|partitioned|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_expected_strings_in_use_count](../column/strings/expected-strings-in-use-count.md#monthly-partition-expected-strings-in-use-count)|partitioned|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_value_in_set_percent](../column/strings/string-value-in-set-percent.md#profile-string-value-in-set-percent)|profiling|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|
|[daily_string_value_in_set_percent](../column/strings/string-value-in-set-percent.md#daily-string-value-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_value_in_set_percent](../column/strings/string-value-in-set-percent.md#monthly-string-value-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_value_in_set_percent](../column/strings/string-value-in-set-percent.md#daily-partition-string-value-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_value_in_set_percent](../column/strings/string-value-in-set-percent.md#monthly-partition-string-value-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_valid_dates_percent](../column/strings/string-valid-dates-percent.md#profile-string-valid-dates-percent)|profiling|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage.|
|[daily_string_valid_dates_percent](../column/strings/string-valid-dates-percent.md#daily-string-valid-dates-percent)|monitoring|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_valid_dates_percent](../column/strings/string-valid-dates-percent.md#monthly-string-valid-dates-percent)|monitoring|Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_valid_dates_percent](../column/strings/string-valid-dates-percent.md#daily-partition-string-valid-dates-percent)|partitioned|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_valid_dates_percent](../column/strings/string-valid-dates-percent.md#monthly-partition-string-valid-dates-percent)|partitioned|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_valid_country_code_percent](../column/strings/string-valid-country-code-percent.md#profile-string-valid-country-code-percent)|profiling|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage.|
|[daily_string_valid_country_code_percent](../column/strings/string-valid-country-code-percent.md#daily-string-valid-country-code-percent)|monitoring|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_valid_country_code_percent](../column/strings/string-valid-country-code-percent.md#monthly-string-valid-country-code-percent)|monitoring|Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_valid_country_code_percent](../column/strings/string-valid-country-code-percent.md#daily-partition-string-valid-country-code-percent)|partitioned|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_valid_country_code_percent](../column/strings/string-valid-country-code-percent.md#monthly-partition-string-valid-country-code-percent)|partitioned|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_valid_currency_code_percent](../column/strings/string-valid-currency-code-percent.md#profile-string-valid-currency-code-percent)|profiling|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage.|
|[daily_string_valid_currency_code_percent](../column/strings/string-valid-currency-code-percent.md#daily-string-valid-currency-code-percent)|monitoring|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_valid_currency_code_percent](../column/strings/string-valid-currency-code-percent.md#monthly-string-valid-currency-code-percent)|monitoring|Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_valid_currency_code_percent](../column/strings/string-valid-currency-code-percent.md#daily-partition-string-valid-currency-code-percent)|partitioned|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_valid_currency_code_percent](../column/strings/string-valid-currency-code-percent.md#monthly-partition-string-valid-currency-code-percent)|partitioned|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_invalid_email_count](../column/strings/string-invalid-email-count.md#profile-string-invalid-email-count)|profiling|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_email_count](../column/strings/string-invalid-email-count.md#daily-string-invalid-email-count)|monitoring|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_invalid_email_count](../column/strings/string-invalid-email-count.md#monthly-string-invalid-email-count)|monitoring|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_email_count](../column/strings/string-invalid-email-count.md#daily-partition-string-invalid-email-count)|partitioned|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_email_count](../column/strings/string-invalid-email-count.md#monthly-partition-string-invalid-email-count)|partitioned|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_invalid_uuid_count](../column/strings/string-invalid-uuid-count.md#profile-string-invalid-uuid-count)|profiling|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_uuid_count](../column/strings/string-invalid-uuid-count.md#daily-string-invalid-uuid-count)|monitoring|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_invalid_uuid_count](../column/strings/string-invalid-uuid-count.md#monthly-string-invalid-uuid-count)|monitoring|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_uuid_count](../column/strings/string-invalid-uuid-count.md#daily-partition-string-invalid-uuid-count)|partitioned|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_uuid_count](../column/strings/string-invalid-uuid-count.md#monthly-partition-string-invalid-uuid-count)|partitioned|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_valid_uuid_percent](../column/strings/string-valid-uuid-percent.md#profile-string-valid-uuid-percent)|profiling|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage.|
|[daily_string_valid_uuid_percent](../column/strings/string-valid-uuid-percent.md#daily-string-valid-uuid-percent)|monitoring|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_valid_uuid_percent](../column/strings/string-valid-uuid-percent.md#monthly-string-valid-uuid-percent)|monitoring|Verifies that the percentage of valid UUID in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_uuid_percent](../column/strings/string-valid-uuid-percent.md#daily-partition-valid-uuid-percent)|partitioned|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_uuid_percent](../column/strings/string-valid-uuid-percent.md#monthly-partition-valid-uuid-percent)|partitioned|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_invalid_ip4_address_count](../column/strings/string-invalid-ip4-address-count.md#profile-string-invalid-ip4-address-count)|profiling|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_ip4_address_count](../column/strings/string-invalid-ip4-address-count.md#daily-string-invalid-ip4-address-count)|monitoring|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_invalid_ip4_address_count](../column/strings/string-invalid-ip4-address-count.md#monthly-string-invalid-ip4-address-count)|monitoring|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_ip4_address_count](../column/strings/string-invalid-ip4-address-count.md#daily-partition-string-invalid-ip4-address-count)|partitioned|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_ip4_address_count](../column/strings/string-invalid-ip4-address-count.md#monthly-partition-string-invalid-ip4-address-count)|partitioned|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_invalid_ip6_address_count](../column/strings/string-invalid-ip6-address-count.md#profile-string-invalid-ip6-address-count)|profiling|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_ip6_address_count](../column/strings/string-invalid-ip6-address-count.md#daily-string-invalid-ip6-address-count)|monitoring|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_invalid_ip6_address_count](../column/strings/string-invalid-ip6-address-count.md#monthly-string-invalid-ip6-address-count)|monitoring|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_ip6_address_count](../column/strings/string-invalid-ip6-address-count.md#daily-partition-string-invalid-ip6-address-count)|partitioned|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_ip6_address_count](../column/strings/string-invalid-ip6-address-count.md#monthly-partition-string-invalid-ip6-address-count)|partitioned|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_not_match_regex_count](../column/strings/string-not-match-regex-count.md#profile-string-not-match-regex-count)|profiling|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count.|
|[daily_string_not_match_regex_count](../column/strings/string-not-match-regex-count.md#daily-string-not-match-regex-count)|monitoring|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_not_match_regex_count](../column/strings/string-not-match-regex-count.md#monthly-string-not-match-regex-count)|monitoring|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_not_match_regex_count](../column/strings/string-not-match-regex-count.md#daily-partition-string-not-match-regex-count)|partitioned|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_not_match_regex_count](../column/strings/string-not-match-regex-count.md#monthly-partition-string-not-match-regex-count)|partitioned|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_match_regex_percent](../column/strings/string-match-regex-percent.md#profile-string-match-regex-percent)|profiling|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage.|
|[daily_string_match_regex_percent](../column/strings/string-match-regex-percent.md#daily-string-match-regex-percent)|monitoring|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_match_regex_percent](../column/strings/string-match-regex-percent.md#monthly-string-match-regex-percent)|monitoring|Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_match_regex_percent](../column/strings/string-match-regex-percent.md#daily-partition-string-match-regex-percent)|partitioned|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_match_regex_percent](../column/strings/string-match-regex-percent.md#monthly-partition-string-match-regex-percent)|partitioned|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_not_match_date_regex_count](../column/strings/string-not-match-date-regex-count.md#profile-string-not-match-date-regex-count)|profiling|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count.|
|[daily_string_not_match_date_regex_count](../column/strings/string-not-match-date-regex-count.md#daily-string-not-match-date-regex-count)|monitoring|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_not_match_date_regex_count](../column/strings/string-not-match-date-regex-count.md#monthly-string-not-match-date-regex-count)|monitoring|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_not_match_date_regex_count](../column/strings/string-not-match-date-regex-count.md#daily-partition-string-not-match-date-regex-count)|partitioned|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_not_match_date_regex_count](../column/strings/string-not-match-date-regex-count.md#monthly-partition-string-not-match-date-regex-count)|partitioned|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_match_date_regex_percent](../column/strings/string-match-date-regex-percent.md#profile-string-match-date-regex-percent)|profiling|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage.|
|[daily_string_match_date_regex_percent](../column/strings/string-match-date-regex-percent.md#daily-string-match-date-regex-percent)|monitoring|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_match_date_regex_percent](../column/strings/string-match-date-regex-percent.md#monthly-string-match-date-regex-percent)|monitoring|Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_match_date_regex_percent](../column/strings/string-match-date-regex-percent.md#daily-partition-string-match-date-regex-percent)|partitioned|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_match_date_regex_percent](../column/strings/string-match-date-regex-percent.md#monthly-partition-string-match-date-regex-percent)|partitioned|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_match_name_regex_percent](../column/strings/string-match-name-regex-percent.md#profile-string-match-name-regex-percent)|profiling|Verifies that the percentage of strings matching the name regex in a column does not fall below the minimum accepted percentage.|
|[daily_string_match_name_regex_percent](../column/strings/string-match-name-regex-percent.md#daily-string-match-name-regex-percent)|monitoring|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_match_name_regex_percent](../column/strings/string-match-name-regex-percent.md#monthly-string-match-name-regex-percent)|monitoring|Verifies that the percentage of strings matching the name regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_match_name_regex_percent](../column/strings/string-match-name-regex-percent.md#daily-partition-string-match-name-regex-percent)|partitioned|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_match_name_regex_percent](../column/strings/string-match-name-regex-percent.md#monthly-partition-string-match-name-regex-percent)|partitioned|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_expected_strings_in_top_values_count](../column/strings/expected-strings-in-top-values-count.md#profile-expected-strings-in-top-values-count)|profiling|Verifies that the top X most popular column values contain all values from a list of expected values.|
|[daily_expected_strings_in_top_values_count](../column/strings/expected-strings-in-top-values-count.md#daily-expected-strings-in-top-values-count)|monitoring|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_expected_strings_in_top_values_count](../column/strings/expected-strings-in-top-values-count.md#monthly-expected-strings-in-top-values-count)|monitoring|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_expected_strings_in_top_values_count](../column/strings/expected-strings-in-top-values-count.md#daily-partition-expected-strings-in-top-values-count)|partitioned|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_expected_strings_in_top_values_count](../column/strings/expected-strings-in-top-values-count.md#monthly-partition-expected-strings-in-top-values-count)|partitioned|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each monthly partition.|





### **uniqueness**
Counts the number or percent of duplicate or unique values in a column.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_distinct_count](../column/uniqueness/distinct-count.md#profile-distinct-count)|profiling|Verifies that the number of distinct values in a column does not fall below the minimum accepted count.|
|[daily_distinct_count](../column/uniqueness/distinct-count.md#daily-distinct-count)|monitoring|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_distinct_count](../column/uniqueness/distinct-count.md#monthly-distinct-count)|monitoring|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_distinct_count](../column/uniqueness/distinct-count.md#daily-partition-distinct-count)|partitioned|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_distinct_count](../column/uniqueness/distinct-count.md#monthly-partition-distinct-count)|partitioned|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_distinct_percent](../column/uniqueness/distinct-percent.md#profile-distinct-percent)|profiling|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent.|
|[daily_distinct_percent](../column/uniqueness/distinct-percent.md#daily-distinct-percent)|monitoring|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_distinct_percent](../column/uniqueness/distinct-percent.md#monthly-distinct-percent)|monitoring|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_distinct_percent](../column/uniqueness/distinct-percent.md#daily-partition-distinct-percent)|partitioned|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_distinct_percent](../column/uniqueness/distinct-percent.md#monthly-partition-distinct-percent)|partitioned|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_duplicate_count](../column/uniqueness/duplicate-count.md#profile-duplicate-count)|profiling|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.|
|[daily_duplicate_count](../column/uniqueness/duplicate-count.md#daily-duplicate-count)|monitoring|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_duplicate_count](../column/uniqueness/duplicate-count.md#monthly-duplicate-count)|monitoring|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_duplicate_count](../column/uniqueness/duplicate-count.md#daily-partition-duplicate-count)|partitioned|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_duplicate_count](../column/uniqueness/duplicate-count.md#monthly-partition-duplicate-count)|partitioned|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_duplicate_percent](../column/uniqueness/duplicate-percent.md#profile-duplicate-percent)|profiling|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.|
|[daily_duplicate_percent](../column/uniqueness/duplicate-percent.md#daily-duplicate-percent)|monitoring|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_duplicate_percent](../column/uniqueness/duplicate-percent.md#monthly-duplicate-percent)|monitoring|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_duplicate_percent](../column/uniqueness/duplicate-percent.md#daily-partition-duplicate-percent)|partitioned|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_duplicate_percent](../column/uniqueness/duplicate-percent.md#monthly-partition-duplicate-percent)|partitioned|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_anomaly_differencing_distinct_count_30_days](../column/uniqueness/anomaly-differencing-distinct-count-30-days.md#profile-anomaly-differencing-distinct-count-30-days)|profiling|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[daily_anomaly_differencing_distinct_count_30_days](../column/uniqueness/anomaly-differencing-distinct-count-30-days.md#daily-anomaly-differencing-distinct-count-30-days)|monitoring|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[monthly_anomaly_differencing_distinct_count_30_days](../column/uniqueness/anomaly-differencing-distinct-count-30-days.md#monthly-anomaly-differencing-distinct-count-30-days)|monitoring|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_anomaly_differencing_distinct_count](../column/uniqueness/anomaly-differencing-distinct-count.md#profile-anomaly-differencing-distinct-count)|profiling|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[daily_anomaly_differencing_distinct_count](../column/uniqueness/anomaly-differencing-distinct-count.md#daily-anomaly-differencing-distinct-count)|monitoring|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[monthly_anomaly_differencing_distinct_count](../column/uniqueness/anomaly-differencing-distinct-count.md#monthly-anomaly-differencing-distinct-count)|monitoring|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_anomaly_stationary_distinct_percent_30_days](../column/uniqueness/anomaly-stationary-distinct-percent-30-days.md#profile-anomaly-stationary-distinct-percent-30-days)|profiling|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[daily_anomaly_stationary_distinct_percent_30_days](../column/uniqueness/anomaly-stationary-distinct-percent-30-days.md#daily-anomaly-stationary-distinct-percent-30-days)|monitoring|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[monthly_anomaly_stationary_distinct_percent_30_days](../column/uniqueness/anomaly-stationary-distinct-percent-30-days.md#monthly-anomaly-stationary-distinct-percent-30-days)|monitoring|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[daily_partition_anomaly_stationary_distinct_percent_30_days](../column/uniqueness/anomaly-stationary-distinct-percent-30-days.md#daily-partition-anomaly-stationary-distinct-percent-30-days)|partitioned|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[monthly_partition_anomaly_stationary_distinct_percent_30_days](../column/uniqueness/anomaly-stationary-distinct-percent-30-days.md#monthly-partition-anomaly-stationary-distinct-percent-30-days)|partitioned|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_anomaly_stationary_distinct_percent](../column/uniqueness/anomaly-stationary-distinct-percent.md#profile-anomaly-stationary-distinct-percent)|profiling|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[daily_anomaly_stationary_distinct_percent](../column/uniqueness/anomaly-stationary-distinct-percent.md#daily-anomaly-stationary-distinct-percent)|monitoring|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[monthly_anomaly_stationary_distinct_percent](../column/uniqueness/anomaly-stationary-distinct-percent.md#monthly-anomaly-stationary-distinct-percent)|monitoring|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[daily_partition_anomaly_stationary_distinct_percent](../column/uniqueness/anomaly-stationary-distinct-percent.md#daily-partition-anomaly-stationary-distinct-percent)|partitioned|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[monthly_partition_anomaly_stationary_distinct_percent](../column/uniqueness/anomaly-stationary-distinct-percent.md#monthly-partition-anomaly-stationary-distinct-percent)|partitioned|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_count](../column/uniqueness/change-distinct-count.md#profile-change-distinct-count)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|
|[daily_change_distinct_count](../column/uniqueness/change-distinct-count.md#daily-change-distinct-count)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|
|[monthly_change_distinct_count](../column/uniqueness/change-distinct-count.md#monthly-change-distinct-count)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|
|[daily_partition_change_distinct_count](../column/uniqueness/change-distinct-count.md#daily-partition-change-distinct-count)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|
|[monthly_partition_change_distinct_count](../column/uniqueness/change-distinct-count.md#monthly-partition-change-distinct-count)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_count_since_7_days](../column/uniqueness/change-distinct-count-since-7-days.md#profile-change-distinct-count-since-7-days)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|
|[daily_change_distinct_count_since_7_days](../column/uniqueness/change-distinct-count-since-7-days.md#daily-change-distinct-count-since-7-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|
|[monthly_change_distinct_count_since_7_days](../column/uniqueness/change-distinct-count-since-7-days.md#monthly-change-distinct-count-since-7-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|
|[daily_partition_change_distinct_count_since_7_days](../column/uniqueness/change-distinct-count-since-7-days.md#daily-partition-change-distinct-count-since-7-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|
|[monthly_partition_change_distinct_count_since_7_days](../column/uniqueness/change-distinct-count-since-7-days.md#monthly-partition-change-distinct-count-since-7-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_count_since_30_days](../column/uniqueness/change-distinct-count-since-30-days.md#profile-change-distinct-count-since-30-days)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|
|[daily_change_distinct_count_since_30_days](../column/uniqueness/change-distinct-count-since-30-days.md#daily-change-distinct-count-since-30-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|
|[monthly_change_distinct_count_since_30_days](../column/uniqueness/change-distinct-count-since-30-days.md#monthly-change-distinct-count-since-30-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|
|[daily_partition_change_distinct_count_since_30_days](../column/uniqueness/change-distinct-count-since-30-days.md#daily-partition-change-distinct-count-since-30-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|
|[monthly_partition_change_distinct_count_since_30_days](../column/uniqueness/change-distinct-count-since-30-days.md#monthly-partition-change-distinct-count-since-30-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_count_since_yesterday](../column/uniqueness/change-distinct-count-since-yesterday.md#profile-change-distinct-count-since-yesterday)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[daily_change_distinct_count_since_yesterday](../column/uniqueness/change-distinct-count-since-yesterday.md#daily-change-distinct-count-since-yesterday)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[monthly_change_distinct_count_since_yesterday](../column/uniqueness/change-distinct-count-since-yesterday.md#monthly-change-distinct-count-since-yesterday)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[daily_partition_change_distinct_count_since_yesterday](../column/uniqueness/change-distinct-count-since-yesterday.md#daily-partition-change-distinct-count-since-yesterday)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[monthly_partition_change_distinct_count_since_yesterday](../column/uniqueness/change-distinct-count-since-yesterday.md#monthly-partition-change-distinct-count-since-yesterday)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_percent](../column/uniqueness/change-distinct-percent.md#profile-change-distinct-percent)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|
|[daily_change_distinct_percent](../column/uniqueness/change-distinct-percent.md#daily-change-distinct-percent)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|
|[monthly_change_distinct_percent](../column/uniqueness/change-distinct-percent.md#monthly-change-distinct-percent)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|
|[daily_partition_change_distinct_percent](../column/uniqueness/change-distinct-percent.md#daily-partition-change-distinct-percent)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|
|[monthly_partition_change_distinct_percent](../column/uniqueness/change-distinct-percent.md#monthly-partition-change-distinct-percent)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_percent_since_7_days](../column/uniqueness/change-distinct-percent-since-7-days.md#profile-change-distinct-percent-since-7-days)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|
|[daily_change_distinct_percent_since_7_days](../column/uniqueness/change-distinct-percent-since-7-days.md#daily-change-distinct-percent-since-7-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|
|[monthly_change_distinct_percent_since_7_days](../column/uniqueness/change-distinct-percent-since-7-days.md#monthly-change-distinct-percent-since-7-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|
|[daily_partition_change_distinct_percent_since_7_days](../column/uniqueness/change-distinct-percent-since-7-days.md#daily-partition-change-distinct-percent-since-7-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|
|[monthly_partition_change_distinct_percent_since_7_days](../column/uniqueness/change-distinct-percent-since-7-days.md#monthly-partition-change-distinct-percent-since-7-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_percent_since_30_days](../column/uniqueness/change-distinct-percent-since-30-days.md#profile-change-distinct-percent-since-30-days)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|
|[daily_change_distinct_percent_since_30_days](../column/uniqueness/change-distinct-percent-since-30-days.md#daily-change-distinct-percent-since-30-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|
|[monthly_change_distinct_percent_since_30_days](../column/uniqueness/change-distinct-percent-since-30-days.md#monthly-change-distinct-percent-since-30-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|
|[daily_partition_change_distinct_percent_since_30_days](../column/uniqueness/change-distinct-percent-since-30-days.md#daily-partition-change-distinct-percent-since-30-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|
|[monthly_partition_change_distinct_percent_since_30_days](../column/uniqueness/change-distinct-percent-since-30-days.md#monthly-partition-change-distinct-percent-since-30-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_percent_since_yesterday](../column/uniqueness/change-distinct-percent-since-yesterday.md#profile-change-distinct-percent-since-yesterday)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[daily_change_distinct_percent_since_yesterday](../column/uniqueness/change-distinct-percent-since-yesterday.md#daily-change-distinct-percent-since-yesterday)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[monthly_change_distinct_percent_since_yesterday](../column/uniqueness/change-distinct-percent-since-yesterday.md#monthly-change-distinct-percent-since-yesterday)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[daily_partition_change_distinct_percent_since_yesterday](../column/uniqueness/change-distinct-percent-since-yesterday.md#daily-partition-change-distinct-percent-since-yesterday)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[monthly_partition_change_distinct_percent_since_yesterday](../column/uniqueness/change-distinct-percent-since-yesterday.md#monthly-partition-change-distinct-percent-since-yesterday)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_anomaly_stationary_distinct_count_30_days](../column/uniqueness/anomaly-stationary-distinct-count-30-days.md#daily-partition-anomaly-stationary-distinct-count-30-days)|partitioned|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[monthly_partition_anomaly_stationary_distinct_count_30_days](../column/uniqueness/anomaly-stationary-distinct-count-30-days.md#monthly-partition-anomaly-stationary-distinct-count-30-days)|partitioned|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_anomaly_stationary_distinct_count](../column/uniqueness/anomaly-stationary-distinct-count.md#daily-partition-anomaly-stationary-distinct-count)|partitioned|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[monthly_partition_anomaly_stationary_distinct_count](../column/uniqueness/anomaly-stationary-distinct-count.md#monthly-partition-anomaly-stationary-distinct-count)|partitioned|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|





