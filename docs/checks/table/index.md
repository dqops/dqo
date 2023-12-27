# Checks/table

**This is a list of table data quality checks supported by DQOps, broken down by a category and a brief description of what they do.**





## **accuracy**  
Compares the tested table with another (reference) table.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_total_row_count_match_percent](./table/accuracy/total-row-count-match-percent/#profile-total-row-count-match-percent)|profiling|Verifies that the total row count of the tested table matches the total row count of another (reference) table.|
|[daily_total_row_count_match_percent](./table/accuracy/total-row-count-match-percent/#daily-total-row-count-match-percent)|monitoring|Verifies the total ow count of a tested table and compares it to a row count of a reference table. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_row_count_match_percent](./table/accuracy/total-row-count-match-percent/#monthly-total-row-count-match-percent)|monitoring|Verifies the total row count of a tested table and compares it to a row count of a reference table. Stores the most recent row count for each month when the data quality check was evaluated.|





## **availability**  
Checks whether the table is accessible and available for use.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_table_availability](./table/availability/table-availability/#profile-table-availability)|profiling|Verifies availability of the table in a database using a simple row count.|
|[daily_table_availability](./table/availability/table-availability/#daily-table-availability)|monitoring|Verifies availability on table in database using simple row count. Stores the most recent table availability status for each day when the data quality check was evaluated.|
|[monthly_table_availability](./table/availability/table-availability/#monthly-table-availability)|monitoring|Verifies availability on table in database using simple row count. Stores the most recent table availability status for each month when the data quality check was evaluated.|





## **comparisons**  


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_match](./table/comparisons/row-count-match/#profile-row-count-match)|profiling|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause.|
|[daily_row_count_match](./table/comparisons/row-count-match/#daily-row-count-match)|monitoring|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_row_count_match](./table/comparisons/row-count-match/#monthly-row-count-match)|monitoring|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_row_count_match](./table/comparisons/row-count-match/#daily-partition-row-count-match)|partitioned|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause on the time period (the daily partition) and all other data grouping columns. Stores the most recent captured value for each daily partition that was analyzed.|
|[monthly_partition_row_count_match](./table/comparisons/row-count-match/#monthly-partition-row-count-match)|partitioned|Verifies that the row count of the tested (parent) table matches the row count of the reference table, for each monthly partition (grouping rows by the time period, truncated to the month). Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each monthly partition and optionally data groups.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_count_match](./table/comparisons/column-count-match/#profile-column-count-match)|profiling|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping.|
|[daily_column_count_match](./table/comparisons/column-count-match/#daily-column-count-match)|monitoring|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_column_count_match](./table/comparisons/column-count-match/#monthly-column-count-match)|monitoring|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each month when the data quality check was evaluated.|





## **schema**  
Detects schema drifts such as columns added, removed, reordered or the data types of columns have changed.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_count](./table/schema/column-count/#profile-column-count)|profiling|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).|
|[daily_column_count](./table/schema/column-count/#daily-column-count)|monitoring|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each day when the data quality check was evaluated.|
|[monthly_column_count](./table/schema/column-count/#monthly-column-count)|monitoring|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_count_changed](./table/schema/column-count-changed/#profile-column-count-changed)|profiling|Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.|
|[daily_column_count_changed](./table/schema/column-count-changed/#daily-column-count-changed)|monitoring|Detects if the count of columns has changed since the most recent day. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each day when the data quality check was evaluated.|
|[monthly_column_count_changed](./table/schema/column-count-changed/#monthly-column-count-changed)|monitoring|Detects if the count of columns has changed since the last month. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_list_changed](./table/schema/column-list-changed/#profile-column-list-changed)|profiling|Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|
|[daily_column_list_changed](./table/schema/column-list-changed/#daily-column-list-changed)|monitoring|Detects if new columns were added or existing columns were removed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|
|[monthly_column_list_changed](./table/schema/column-list-changed/#monthly-column-list-changed)|monitoring|Detects if new columns were added or existing columns were removed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_list_or_order_changed](./table/schema/column-list-or-order-changed/#profile-column-list-or-order-changed)|profiling|Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|
|[daily_column_list_or_order_changed](./table/schema/column-list-or-order-changed/#daily-column-list-or-order-changed)|monitoring|Detects if new columns were added, existing columns were removed or the columns were reordered since the most recent day. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|
|[monthly_column_list_or_order_changed](./table/schema/column-list-or-order-changed/#monthly-column-list-or-order-changed)|monitoring|Detects if new columns were added, existing columns were removed or the columns were reordered since the last month. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_types_changed](./table/schema/column-types-changed/#profile-column-types-changed)|profiling|Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|
|[daily_column_types_changed](./table/schema/column-types-changed/#daily-column-types-changed)|monitoring|Detects if new columns were added, removed or their data types have changed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|
|[monthly_column_types_changed](./table/schema/column-types-changed/#monthly-column-types-changed)|monitoring|Detects if new columns were added, removed or their data types have changed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|





## **sql**  
Validate data against user-defined SQL queries at the table level. Checks in this group allow for validation that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sql_condition_passed_percent_on_table](./table/sql/sql-condition-passed-percent-on-table/#profile-sql-condition-passed-percent-on-table)|profiling|Verifies that a set percentage of rows passed a custom SQL condition (expression).|
|[daily_sql_condition_passed_percent_on_table](./table/sql/sql-condition-passed-percent-on-table/#daily-sql-condition-passed-percent-on-table)|monitoring|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_condition_passed_percent_on_table](./table/sql/sql-condition-passed-percent-on-table/#monthly-sql-condition-passed-percent-on-table)|monitoring|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_condition_passed_percent_on_table](./table/sql/sql-condition-passed-percent-on-table/#daily-partition-sql-condition-passed-percent-on-table)|partitioned|Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_condition_passed_percent_on_table](./table/sql/sql-condition-passed-percent-on-table/#monthly-partition-sql-condition-passed-percent-on-table)|partitioned|Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sql_condition_failed_count_on_table](./table/sql/sql-condition-failed-count-on-table/#profile-sql-condition-failed-count-on-table)|profiling|Verifies that a set number of rows failed a custom SQL condition (expression).|
|[daily_sql_condition_failed_count_on_table](./table/sql/sql-condition-failed-count-on-table/#daily-sql-condition-failed-count-on-table)|monitoring|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_condition_failed_count_on_table](./table/sql/sql-condition-failed-count-on-table/#monthly-sql-condition-failed-count-on-table)|monitoring|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_condition_failed_count_on_table](./table/sql/sql-condition-failed-count-on-table/#daily-partition-sql-condition-failed-count-on-table)|partitioned|Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_condition_failed_count_on_table](./table/sql/sql-condition-failed-count-on-table/#monthly-partition-sql-condition-failed-count-on-table)|partitioned|Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sql_aggregate_expr_table](./table/sql/sql-aggregate-expr-table/#profile-sql-aggregate-expr-table)|profiling|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|
|[daily_sql_aggregate_expr_table](./table/sql/sql-aggregate-expr-table/#daily-sql-aggregate-expr-table)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_aggregate_expr_table](./table/sql/sql-aggregate-expr-table/#monthly-sql-aggregate-expr-table)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_aggregate_expr_table](./table/sql/sql-aggregate-expr-table/#daily-partition-sql-aggregate-expr-table)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_aggregate_expr_table](./table/sql/sql-aggregate-expr-table/#monthly-partition-sql-aggregate-expr-table)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|





## **timeliness**  
Assesses the freshness and staleness of data, as well as data ingestion delay and reload lag for partitioned data.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_data_freshness](./table/timeliness/data-freshness/#profile-data-freshness)|profiling|Calculates the number of days since the most recent event timestamp (freshness)|
|[daily_data_freshness](./table/timeliness/data-freshness/#daily-data-freshness)|monitoring|Daily  calculating the number of days since the most recent event timestamp (freshness)|
|[monthly_data_freshness](./table/timeliness/data-freshness/#monthly-data-freshness)|monitoring|Monthly monitoring calculating the number of days since the most recent event timestamp (freshness)|
|[daily_partition_data_freshness](./table/timeliness/data-freshness/#daily-partition-data-freshness)|partitioned|Daily partitioned check calculating the number of days since the most recent event timestamp (freshness)|
|[monthly_partition_data_freshness](./table/timeliness/data-freshness/#monthly-partition-data-freshness)|partitioned|Monthly partitioned check calculating the number of days since the most recent event (freshness)|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_data_staleness](./table/timeliness/data-staleness/#profile-data-staleness)|profiling|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[daily_data_staleness](./table/timeliness/data-staleness/#daily-data-staleness)|monitoring|Daily  calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[monthly_data_staleness](./table/timeliness/data-staleness/#monthly-data-staleness)|monitoring|Monthly monitoring calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[daily_partition_data_staleness](./table/timeliness/data-staleness/#daily-partition-data-staleness)|partitioned|Daily partitioned check calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[monthly_partition_data_staleness](./table/timeliness/data-staleness/#monthly-partition-data-staleness)|partitioned|Monthly partitioned check calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_data_ingestion_delay](./table/timeliness/data-ingestion-delay/#profile-data-ingestion-delay)|profiling|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[daily_data_ingestion_delay](./table/timeliness/data-ingestion-delay/#daily-data-ingestion-delay)|monitoring|Daily  calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[monthly_data_ingestion_delay](./table/timeliness/data-ingestion-delay/#monthly-data-ingestion-delay)|monitoring|Monthly monitoring calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[daily_partition_data_ingestion_delay](./table/timeliness/data-ingestion-delay/#daily-partition-data-ingestion-delay)|partitioned|Daily partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[monthly_partition_data_ingestion_delay](./table/timeliness/data-ingestion-delay/#monthly-partition-data-ingestion-delay)|partitioned|Monthly partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_reload_lag](./table/timeliness/reload-lag/#daily-partition-reload-lag)|partitioned|Daily partitioned check calculating the longest time a row waited to be load|
|[monthly_partition_reload_lag](./table/timeliness/reload-lag/#monthly-partition-reload-lag)|partitioned|Monthly partitioned check calculating the longest time a row waited to be load|





## **volume**  
Evaluates the overall quality of the table by verifying the number of rows.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count](./table/volume/row-count/#profile-row-count)|profiling|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|
|[daily_row_count](./table/volume/row-count/#daily-row-count)|monitoring|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.Stores the most recent captured row count value for each day when the row count was evaluated.|
|[monthly_row_count](./table/volume/row-count/#monthly-row-count)|monitoring|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.Stores the most recent captured row count value for each month when the row count was evaluated.|
|[daily_partition_row_count](./table/volume/row-count/#daily-partition-row-count)|partitioned|Verifies that each daily partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|
|[monthly_partition_row_count](./table/volume/row-count/#monthly-partition-row-count)|partitioned|Verifies that each monthly partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_anomaly_differencing_30_days](./table/volume/row-count-anomaly-differencing-30-days/#profile-row-count-anomaly-differencing-30-days)|profiling|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.|
|[daily_row_count_anomaly_differencing_30_days](./table/volume/row-count-anomaly-differencing-30-days/#daily-row-count-anomaly-differencing-30-days)|monitoring|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_anomaly_differencing](./table/volume/row-count-anomaly-differencing/#profile-row-count-anomaly-differencing)|profiling|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.|
|[daily_row_count_anomaly_differencing](./table/volume/row-count-anomaly-differencing/#daily-row-count-anomaly-differencing)|monitoring|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_change](./table/volume/row-count-change/#profile-row-count-change)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|
|[daily_row_count_change](./table/volume/row-count-change/#daily-row-count-change)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last day with a row count captured.|
|[monthly_row_count_change](./table/volume/row-count-change/#monthly-row-count-change)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last month.|
|[daily_partition_row_count_change](./table/volume/row-count-change/#daily-partition-row-count-change)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|
|[monthly_partition_row_count_change](./table/volume/row-count-change/#monthly-partition-row-count-change)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_change_yesterday](./table/volume/row-count-change-yesterday/#profile-row-count-change-yesterday)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|
|[daily_row_count_change_yesterday](./table/volume/row-count-change-yesterday/#daily-row-count-change-yesterday)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|
|[daily_partition_row_count_change_yesterday](./table/volume/row-count-change-yesterday/#daily-partition-row-count-change-yesterday)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_change_7_days](./table/volume/row-count-change-7-days/#profile-row-count-change-7-days)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|
|[daily_row_count_change_7_days](./table/volume/row-count-change-7-days/#daily-row-count-change-7-days)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|
|[daily_partition_row_count_change_7_days](./table/volume/row-count-change-7-days/#daily-partition-row-count-change-7-days)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_change_30_days](./table/volume/row-count-change-30-days/#profile-row-count-change-30-days)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|
|[daily_row_count_change_30_days](./table/volume/row-count-change-30-days/#daily-row-count-change-30-days)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|
|[daily_partition_row_count_change_30_days](./table/volume/row-count-change-30-days/#daily-partition-row-count-change-30-days)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_row_count_anomaly_stationary_30_days](./table/volume/row-count-anomaly-stationary-30-days/#daily-partition-row-count-anomaly-stationary-30-days)|partitioned|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_row_count_anomaly_stationary](./table/volume/row-count-anomaly-stationary/#daily-partition-row-count-anomaly-stationary)|partitioned|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 90 days.|





