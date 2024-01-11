# Data quality checks

This is a list of data quality checks supported by DQOps, broken down by a category and a brief description of what they do.


## Table checks


### **accuracy**
Compares the tested table with another (reference) table.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_total_row_count_match_percent](./table/accuracy/total-row-count-match-percent.md#profile-total-row-count-match-percent)|profiling|Verifies that the total row count of the tested table matches the total row count of another (reference) table.|standard|
|[daily_total_row_count_match_percent](./table/accuracy/total-row-count-match-percent.md#daily-total-row-count-match-percent)|monitoring|Verifies the total ow count of a tested table and compares it to a row count of a reference table. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_total_row_count_match_percent](./table/accuracy/total-row-count-match-percent.md#monthly-total-row-count-match-percent)|monitoring|Verifies the total row count of a tested table and compares it to a row count of a reference table. Stores the most recent row count for each month when the data quality check was evaluated.|standard|






### **availability**
Checks whether the table is accessible and available for use.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_table_availability](./table/availability/table-availability.md#profile-table-availability)|profiling|Verifies availability of a table in a monitored database using a simple query.|standard|
|[daily_table_availability](./table/availability/table-availability.md#daily-table-availability)|monitoring|Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each day when the data quality check was evaluated.|standard|
|[monthly_table_availability](./table/availability/table-availability.md#monthly-table-availability)|monitoring|Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each month when the data quality check was evaluated.|standard|






### **comparisons**


| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_row_count_match](./table/comparisons/row-count-match.md#profile-row-count-match)|profiling|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause.|standard|
|[daily_row_count_match](./table/comparisons/row-count-match.md#daily-row-count-match)|monitoring|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_row_count_match](./table/comparisons/row-count-match.md#monthly-row-count-match)|monitoring|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each month when the data quality check was evaluated.|standard|
|[daily_partition_row_count_match](./table/comparisons/row-count-match.md#daily-partition-row-count-match)|partitioned|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause on the time period (the daily partition) and all other data grouping columns. Stores the most recent captured value for each daily partition that was analyzed.|standard|
|[monthly_partition_row_count_match](./table/comparisons/row-count-match.md#monthly-partition-row-count-match)|partitioned|Verifies that the row count of the tested (parent) table matches the row count of the reference table, for each monthly partition (grouping rows by the time period, truncated to the month). Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each monthly partition and optionally data groups.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_column_count_match](./table/comparisons/column-count-match.md#profile-column-count-match)|profiling|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping.|standard|
|[daily_column_count_match](./table/comparisons/column-count-match.md#daily-column-count-match)|monitoring|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_column_count_match](./table/comparisons/column-count-match.md#monthly-column-count-match)|monitoring|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each month when the data quality check was evaluated.|standard|






### **custom_sql**
Validate data against user-defined SQL queries at the table level. Checks in this group allow for validation that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sql_condition_passed_percent_on_table](./table/custom_sql/sql-condition-passed-percent-on-table.md#profile-sql-condition-passed-percent-on-table)|profiling|Verifies that a set percentage of rows passed a custom SQL condition (expression).|advanced|
|[daily_sql_condition_passed_percent_on_table](./table/custom_sql/sql-condition-passed-percent-on-table.md#daily-sql-condition-passed-percent-on-table)|monitoring|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_sql_condition_passed_percent_on_table](./table/custom_sql/sql-condition-passed-percent-on-table.md#monthly-sql-condition-passed-percent-on-table)|monitoring|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|advanced|
|[daily_partition_sql_condition_passed_percent_on_table](./table/custom_sql/sql-condition-passed-percent-on-table.md#daily-partition-sql-condition-passed-percent-on-table)|partitioned|Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_sql_condition_passed_percent_on_table](./table/custom_sql/sql-condition-passed-percent-on-table.md#monthly-partition-sql-condition-passed-percent-on-table)|partitioned|Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sql_condition_failed_count_on_table](./table/custom_sql/sql-condition-failed-count-on-table.md#profile-sql-condition-failed-count-on-table)|profiling|Verifies that a set number of rows failed a custom SQL condition (expression).|standard|
|[daily_sql_condition_failed_count_on_table](./table/custom_sql/sql-condition-failed-count-on-table.md#daily-sql-condition-failed-count-on-table)|monitoring|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_sql_condition_failed_count_on_table](./table/custom_sql/sql-condition-failed-count-on-table.md#monthly-sql-condition-failed-count-on-table)|monitoring|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_sql_condition_failed_count_on_table](./table/custom_sql/sql-condition-failed-count-on-table.md#daily-partition-sql-condition-failed-count-on-table)|partitioned|Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_sql_condition_failed_count_on_table](./table/custom_sql/sql-condition-failed-count-on-table.md#monthly-partition-sql-condition-failed-count-on-table)|partitioned|Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sql_aggregate_expression_on_table](./table/custom_sql/sql-aggregate-expression-on-table.md#profile-sql-aggregate-expression-on-table)|profiling|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|advanced|
|[daily_sql_aggregate_expression_on_table](./table/custom_sql/sql-aggregate-expression-on-table.md#daily-sql-aggregate-expression-on-table)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_sql_aggregate_expression_on_table](./table/custom_sql/sql-aggregate-expression-on-table.md#monthly-sql-aggregate-expression-on-table)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|advanced|
|[daily_partition_sql_aggregate_expression_on_table](./table/custom_sql/sql-aggregate-expression-on-table.md#daily-partition-sql-aggregate-expression-on-table)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_sql_aggregate_expression_on_table](./table/custom_sql/sql-aggregate-expression-on-table.md#monthly-partition-sql-aggregate-expression-on-table)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|






### **schema**
Detects schema drifts such as columns added, removed, reordered or the data types of columns have changed.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_column_count](./table/schema/column-count.md#profile-column-count)|profiling|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).|standard|
|[daily_column_count](./table/schema/column-count.md#daily-column-count)|monitoring|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each day when the data quality check was evaluated.|standard|
|[monthly_column_count](./table/schema/column-count.md#monthly-column-count)|monitoring|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each month when the data quality check was evaluated.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_column_count_changed](./table/schema/column-count-changed.md#profile-column-count-changed)|profiling|Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.|standard|
|[daily_column_count_changed](./table/schema/column-count-changed.md#daily-column-count-changed)|monitoring|Detects if the count of columns has changed since the most recent day. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each day when the data quality check was evaluated.|standard|
|[monthly_column_count_changed](./table/schema/column-count-changed.md#monthly-column-count-changed)|monitoring|Detects if the count of columns has changed since the last month. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each month when the data quality check was evaluated.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_column_list_changed](./table/schema/column-list-changed.md#profile-column-list-changed)|profiling|Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|advanced|
|[daily_column_list_changed](./table/schema/column-list-changed.md#daily-column-list-changed)|monitoring|Detects if new columns were added or existing columns were removed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|advanced|
|[monthly_column_list_changed](./table/schema/column-list-changed.md#monthly-column-list-changed)|monitoring|Detects if new columns were added or existing columns were removed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_column_list_or_order_changed](./table/schema/column-list-or-order-changed.md#profile-column-list-or-order-changed)|profiling|Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|advanced|
|[daily_column_list_or_order_changed](./table/schema/column-list-or-order-changed.md#daily-column-list-or-order-changed)|monitoring|Detects if new columns were added, existing columns were removed or the columns were reordered since the most recent day. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|advanced|
|[monthly_column_list_or_order_changed](./table/schema/column-list-or-order-changed.md#monthly-column-list-or-order-changed)|monitoring|Detects if new columns were added, existing columns were removed or the columns were reordered since the last month. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_column_types_changed](./table/schema/column-types-changed.md#profile-column-types-changed)|profiling|Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|advanced|
|[daily_column_types_changed](./table/schema/column-types-changed.md#daily-column-types-changed)|monitoring|Detects if new columns were added, removed or their data types have changed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|advanced|
|[monthly_column_types_changed](./table/schema/column-types-changed.md#monthly-column-types-changed)|monitoring|Detects if new columns were added, removed or their data types have changed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|advanced|






### **timeliness**
Assesses the freshness and staleness of data, as well as data ingestion delay and reload lag for partitioned data.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_data_freshness](./table/timeliness/data-freshness.md#profile-data-freshness)|profiling|Calculates the number of days since the most recent event timestamp (freshness)|standard|
|[daily_data_freshness](./table/timeliness/data-freshness.md#daily-data-freshness)|monitoring|Daily  calculating the number of days since the most recent event timestamp (freshness)|standard|
|[monthly_data_freshness](./table/timeliness/data-freshness.md#monthly-data-freshness)|monitoring|Monthly monitoring calculating the number of days since the most recent event timestamp (freshness)|standard|
|[daily_partition_data_freshness](./table/timeliness/data-freshness.md#daily-partition-data-freshness)|partitioned|Daily partitioned check calculating the number of days since the most recent event timestamp (freshness)|standard|
|[monthly_partition_data_freshness](./table/timeliness/data-freshness.md#monthly-partition-data-freshness)|partitioned|Monthly partitioned check calculating the number of days since the most recent event (freshness)|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_data_staleness](./table/timeliness/data-staleness.md#profile-data-staleness)|profiling|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|advanced|
|[daily_data_staleness](./table/timeliness/data-staleness.md#daily-data-staleness)|monitoring|Daily  calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|advanced|
|[monthly_data_staleness](./table/timeliness/data-staleness.md#monthly-data-staleness)|monitoring|Monthly monitoring calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|advanced|
|[daily_partition_data_staleness](./table/timeliness/data-staleness.md#daily-partition-data-staleness)|partitioned|Daily partitioned check calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|advanced|
|[monthly_partition_data_staleness](./table/timeliness/data-staleness.md#monthly-partition-data-staleness)|partitioned|Monthly partitioned check calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_data_ingestion_delay](./table/timeliness/data-ingestion-delay.md#profile-data-ingestion-delay)|profiling|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|advanced|
|[daily_data_ingestion_delay](./table/timeliness/data-ingestion-delay.md#daily-data-ingestion-delay)|monitoring|Daily  calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|advanced|
|[monthly_data_ingestion_delay](./table/timeliness/data-ingestion-delay.md#monthly-data-ingestion-delay)|monitoring|Monthly monitoring calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|advanced|
|[daily_partition_data_ingestion_delay](./table/timeliness/data-ingestion-delay.md#daily-partition-data-ingestion-delay)|partitioned|Daily partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|advanced|
|[monthly_partition_data_ingestion_delay](./table/timeliness/data-ingestion-delay.md#monthly-partition-data-ingestion-delay)|partitioned|Monthly partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[daily_partition_reload_lag](./table/timeliness/reload-lag.md#daily-partition-reload-lag)|partitioned|Daily partitioned check calculating the longest time a row waited to be load|advanced|
|[monthly_partition_reload_lag](./table/timeliness/reload-lag.md#monthly-partition-reload-lag)|partitioned|Monthly partitioned check calculating the longest time a row waited to be load|advanced|






### **volume**
Evaluates the overall quality of the table by verifying the number of rows.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_row_count](./table/volume/row-count.md#profile-row-count)|profiling|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|standard|
|[daily_row_count](./table/volume/row-count.md#daily-row-count)|monitoring|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.Stores the most recent captured row count value for each day when the row count was evaluated.|standard|
|[monthly_row_count](./table/volume/row-count.md#monthly-row-count)|monitoring|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.Stores the most recent captured row count value for each month when the row count was evaluated.|standard|
|[daily_partition_row_count](./table/volume/row-count.md#daily-partition-row-count)|partitioned|Verifies that each daily partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|standard|
|[monthly_partition_row_count](./table/volume/row-count.md#monthly-partition-row-count)|partitioned|Verifies that each monthly partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_row_count_anomaly](./table/volume/row-count-anomaly.md#profile-row-count-anomaly)|profiling|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.|advanced|
|[daily_row_count_anomaly](./table/volume/row-count-anomaly.md#daily-row-count-anomaly)|monitoring|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during the last 90 days.|advanced|
|[daily_partition_row_count_anomaly](./table/volume/row-count-anomaly.md#daily-partition-row-count-anomaly)|partitioned|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 90 days.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_row_count_change](./table/volume/row-count-change.md#profile-row-count-change)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|advanced|
|[daily_row_count_change](./table/volume/row-count-change.md#daily-row-count-change)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last day with a row count captured.|advanced|
|[monthly_row_count_change](./table/volume/row-count-change.md#monthly-row-count-change)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last month.|advanced|
|[daily_partition_row_count_change](./table/volume/row-count-change.md#daily-partition-row-count-change)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|advanced|
|[monthly_partition_row_count_change](./table/volume/row-count-change.md#monthly-partition-row-count-change)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_row_count_change_1_day](./table/volume/row-count-change-1-day.md#profile-row-count-change-1-day)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|advanced|
|[daily_row_count_change_1_day](./table/volume/row-count-change-1-day.md#daily-row-count-change-1-day)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|advanced|
|[daily_partition_row_count_change_1_day](./table/volume/row-count-change-1-day.md#daily-partition-row-count-change-1-day)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_row_count_change_7_days](./table/volume/row-count-change-7-days.md#profile-row-count-change-7-days)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|advanced|
|[daily_row_count_change_7_days](./table/volume/row-count-change-7-days.md#daily-row-count-change-7-days)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from the last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|advanced|
|[daily_partition_row_count_change_7_days](./table/volume/row-count-change-7-days.md#daily-partition-row-count-change-7-days)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_row_count_change_30_days](./table/volume/row-count-change-30-days.md#profile-row-count-change-30-days)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|advanced|
|[daily_row_count_change_30_days](./table/volume/row-count-change-30-days.md#daily-row-count-change-30-days)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from the last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|advanced|
|[daily_partition_row_count_change_30_days](./table/volume/row-count-change-30-days.md#daily-partition-row-count-change-30-days)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|advanced|


























































## Column checks























### **accepted_values**


| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_found_in_set_percent](./column/accepted_values/text-found-in-set-percent.md#profile-text-found-in-set-percent)|profiling|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|standard|
|[daily_text_found_in_set_percent](./column/accepted_values/text-found-in-set-percent.md#daily-text-found-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_text_found_in_set_percent](./column/accepted_values/text-found-in-set-percent.md#monthly-text-found-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|standard|
|[daily_partition_text_found_in_set_percent](./column/accepted_values/text-found-in-set-percent.md#daily-partition-text-found-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_text_found_in_set_percent](./column/accepted_values/text-found-in-set-percent.md#monthly-partition-text-found-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_number_found_in_set_percent](./column/accepted_values/number-found-in-set-percent.md#profile-number-found-in-set-percent)|profiling|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|standard|
|[daily_number_found_in_set_percent](./column/accepted_values/number-found-in-set-percent.md#daily-number-found-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_number_found_in_set_percent](./column/accepted_values/number-found-in-set-percent.md#monthly-number-found-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|standard|
|[daily_partition_number_found_in_set_percent](./column/accepted_values/number-found-in-set-percent.md#daily-partition-number-found-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_number_found_in_set_percent](./column/accepted_values/number-found-in-set-percent.md#monthly-partition-number-found-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_expected_text_values_in_use_count](./column/accepted_values/expected-text-values-in-use-count.md#profile-expected-text-values-in-use-count)|profiling|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|advanced|
|[daily_expected_text_values_in_use_count](./column/accepted_values/expected-text-values-in-use-count.md#daily-expected-text-values-in-use-count)|monitoring|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_expected_text_values_in_use_count](./column/accepted_values/expected-text-values-in-use-count.md#monthly-expected-text-values-in-use-count)|monitoring|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_expected_text_values_in_use_count](./column/accepted_values/expected-text-values-in-use-count.md#daily-partition-expected-text-values-in-use-count)|partitioned|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_expected_text_values_in_use_count](./column/accepted_values/expected-text-values-in-use-count.md#monthly-partition-expected-text-values-in-use-count)|partitioned|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_expected_texts_in_top_values_count](./column/accepted_values/expected-texts-in-top-values-count.md#profile-expected-texts-in-top-values-count)|profiling|Verifies that the top X most popular column values contain all values from a list of expected values.|advanced|
|[daily_expected_texts_in_top_values_count](./column/accepted_values/expected-texts-in-top-values-count.md#daily-expected-texts-in-top-values-count)|monitoring|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_expected_texts_in_top_values_count](./column/accepted_values/expected-texts-in-top-values-count.md#monthly-expected-texts-in-top-values-count)|monitoring|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_expected_texts_in_top_values_count](./column/accepted_values/expected-texts-in-top-values-count.md#daily-partition-expected-texts-in-top-values-count)|partitioned|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_expected_texts_in_top_values_count](./column/accepted_values/expected-texts-in-top-values-count.md#monthly-partition-expected-texts-in-top-values-count)|partitioned|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_expected_numbers_in_use_count](./column/accepted_values/expected-numbers-in-use-count.md#profile-expected-numbers-in-use-count)|profiling|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|advanced|
|[daily_expected_numbers_in_use_count](./column/accepted_values/expected-numbers-in-use-count.md#daily-expected-numbers-in-use-count)|monitoring|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_expected_numbers_in_use_count](./column/accepted_values/expected-numbers-in-use-count.md#monthly-expected-numbers-in-use-count)|monitoring|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_expected_numbers_in_use_count](./column/accepted_values/expected-numbers-in-use-count.md#daily-partition-expected-numbers-in-use-count)|partitioned|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_expected_numbers_in_use_count](./column/accepted_values/expected-numbers-in-use-count.md#monthly-partition-expected-numbers-in-use-count)|partitioned|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|advanced|






### **accuracy**


| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_total_sum_match_percent](./column/accuracy/total-sum-match-percent.md#profile-total-sum-match-percent)|profiling|Verifies that percentage of the difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number.|standard|
|[daily_total_sum_match_percent](./column/accuracy/total-sum-match-percent.md#daily-total-sum-match-percent)|monitoring|Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_total_sum_match_percent](./column/accuracy/total-sum-match-percent.md#monthly-total-sum-match-percent)|monitoring|Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_total_min_match_percent](./column/accuracy/total-min-match-percent.md#profile-total-min-match-percent)|profiling|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number.|standard|
|[daily_total_min_match_percent](./column/accuracy/total-min-match-percent.md#daily-total-min-match-percent)|monitoring|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_total_min_match_percent](./column/accuracy/total-min-match-percent.md#monthly-total-min-match-percent)|monitoring|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_total_max_match_percent](./column/accuracy/total-max-match-percent.md#profile-total-max-match-percent)|profiling|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number.|standard|
|[daily_total_max_match_percent](./column/accuracy/total-max-match-percent.md#daily-total-max-match-percent)|monitoring|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_total_max_match_percent](./column/accuracy/total-max-match-percent.md#monthly-total-max-match-percent)|monitoring|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_total_average_match_percent](./column/accuracy/total-average-match-percent.md#profile-total-average-match-percent)|profiling|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number.|standard|
|[daily_total_average_match_percent](./column/accuracy/total-average-match-percent.md#daily-total-average-match-percent)|monitoring|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_total_average_match_percent](./column/accuracy/total-average-match-percent.md#monthly-total-average-match-percent)|monitoring|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_total_not_null_count_match_percent](./column/accuracy/total-not-null-count-match-percent.md#profile-total-not-null-count-match-percent)|profiling|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[daily_total_not_null_count_match_percent](./column/accuracy/total-not-null-count-match-percent.md#daily-total-not-null-count-match-percent)|monitoring|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_total_not_null_count_match_percent](./column/accuracy/total-not-null-count-match-percent.md#monthly-total-not-null-count-match-percent)|monitoring|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|standard|






### **anomaly**
Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_mean_anomaly](./column/anomaly/mean-anomaly.md#profile-mean-anomaly)|profiling|Verifies that the mean value in a column changes in a rate within a percentile boundary during the last 90 days.|advanced|
|[daily_mean_anomaly](./column/anomaly/mean-anomaly.md#daily-mean-anomaly)|monitoring|Verifies that the mean value in a column changes in a rate within a percentile boundary during the last 90 days.|advanced|
|[daily_partition_mean_anomaly](./column/anomaly/mean-anomaly.md#daily-partition-mean-anomaly)|partitioned|Verifies that the mean value in a column is within a percentile from measurements made during the last 90 days.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_median_anomaly](./column/anomaly/median-anomaly.md#profile-median-anomaly)|profiling|Verifies that the median in a column changes in a rate within a percentile boundary during the last 90 days.|advanced|
|[daily_median_anomaly](./column/anomaly/median-anomaly.md#daily-median-anomaly)|monitoring|Verifies that the median in a column changes in a rate within a percentile boundary during the last 90 days.|advanced|
|[daily_partition_median_anomaly](./column/anomaly/median-anomaly.md#daily-partition-median-anomaly)|partitioned|Verifies that the median in a column is within a percentile from measurements made during the last 90 days.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sum_anomaly](./column/anomaly/sum-anomaly.md#profile-sum-anomaly)|profiling|Verifies that the sum in a column changes in a rate within a percentile boundary during the last 90 days.|standard|
|[daily_sum_anomaly](./column/anomaly/sum-anomaly.md#daily-sum-anomaly)|monitoring|Verifies that the sum in a column changes in a rate within a percentile boundary during the last 90 days.|standard|
|[daily_partition_sum_anomaly](./column/anomaly/sum-anomaly.md#daily-partition-sum-anomaly)|partitioned|Verifies that the sum in a column is within a percentile from measurements made during the last 90 days.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_mean_change](./column/anomaly/mean-change.md#profile-mean-change)|profiling|Verifies that the mean value in a column changed in a fixed rate since the last readout.|advanced|
|[daily_mean_change](./column/anomaly/mean-change.md#daily-mean-change)|monitoring|Verifies that the mean value in a column changed in a fixed rate since the last readout.|advanced|
|[monthly_mean_change](./column/anomaly/mean-change.md#monthly-mean-change)|monitoring|Verifies that the mean value in a column changed in a fixed rate since the last readout.|advanced|
|[daily_partition_mean_change](./column/anomaly/mean-change.md#daily-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout.|advanced|
|[monthly_partition_mean_change](./column/anomaly/mean-change.md#monthly-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since the last readout.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_mean_change_1_day](./column/anomaly/mean-change-1-day.md#profile-mean-change-1-day)|profiling|Verifies that the mean value in a column changed in a fixed rate since the last readout from yesterday.|advanced|
|[daily_mean_change_1_day](./column/anomaly/mean-change-1-day.md#daily-mean-change-1-day)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|advanced|
|[daily_partition_mean_change_1_day](./column/anomaly/mean-change-1-day.md#daily-partition-mean-change-1-day)|partitioned|Verifies that the mean value in a column changed in a fixed rate since the last readout from yesterday.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_mean_change_7_days](./column/anomaly/mean-change-7-days.md#profile-mean-change-7-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last week.|advanced|
|[daily_mean_change_7_days](./column/anomaly/mean-change-7-days.md#daily-mean-change-7-days)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|advanced|
|[daily_partition_mean_change_7_days](./column/anomaly/mean-change-7-days.md#daily-partition-mean-change-7-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last week.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_mean_change_30_days](./column/anomaly/mean-change-30-days.md#profile-mean-change-30-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last month.|advanced|
|[daily_mean_change_30_days](./column/anomaly/mean-change-30-days.md#daily-mean-change-30-days)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|advanced|
|[daily_partition_mean_change_30_days](./column/anomaly/mean-change-30-days.md#daily-partition-mean-change-30-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last month.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_median_change](./column/anomaly/median-change.md#profile-median-change)|profiling|Verifies that the median in a column changed in a fixed rate since the last readout.|advanced|
|[daily_median_change](./column/anomaly/median-change.md#daily-median-change)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout.|advanced|
|[monthly_median_change](./column/anomaly/median-change.md#monthly-median-change)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout.|advanced|
|[daily_partition_median_change](./column/anomaly/median-change.md#daily-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout.|advanced|
|[monthly_partition_median_change](./column/anomaly/median-change.md#monthly-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_median_change_1_day](./column/anomaly/median-change-1-day.md#profile-median-change-1-day)|profiling|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.|advanced|
|[daily_median_change_1_day](./column/anomaly/median-change-1-day.md#daily-median-change-1-day)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.|advanced|
|[daily_partition_median_change_1_day](./column/anomaly/median-change-1-day.md#daily-partition-median-change-1-day)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_median_change_7_days](./column/anomaly/median-change-7-days.md#profile-median-change-7-days)|profiling|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.|advanced|
|[daily_median_change_7_days](./column/anomaly/median-change-7-days.md#daily-median-change-7-days)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.|advanced|
|[daily_partition_median_change_7_days](./column/anomaly/median-change-7-days.md#daily-partition-median-change-7-days)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_median_change_30_days](./column/anomaly/median-change-30-days.md#profile-median-change-30-days)|profiling|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.|advanced|
|[daily_median_change_30_days](./column/anomaly/median-change-30-days.md#daily-median-change-30-days)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.|advanced|
|[daily_partition_median_change_30_days](./column/anomaly/median-change-30-days.md#daily-partition-median-change-30-days)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sum_change](./column/anomaly/sum-change.md#profile-sum-change)|profiling|Verifies that the sum in a column changed in a fixed rate since the last readout.|standard|
|[daily_sum_change](./column/anomaly/sum-change.md#daily-sum-change)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout.|standard|
|[monthly_sum_change](./column/anomaly/sum-change.md#monthly-sum-change)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout.|standard|
|[daily_partition_sum_change](./column/anomaly/sum-change.md#daily-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout.|standard|
|[monthly_partition_sum_change](./column/anomaly/sum-change.md#monthly-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sum_change_1_day](./column/anomaly/sum-change-1-day.md#profile-sum-change-1-day)|profiling|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.|advanced|
|[daily_sum_change_1_day](./column/anomaly/sum-change-1-day.md#daily-sum-change-1-day)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.|advanced|
|[daily_partition_sum_change_1_day](./column/anomaly/sum-change-1-day.md#daily-partition-sum-change-1-day)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sum_change_7_days](./column/anomaly/sum-change-7-days.md#profile-sum-change-7-days)|profiling|Verifies that the sum in a column changed in a fixed rate since the last readout from last week.|advanced|
|[daily_sum_change_7_days](./column/anomaly/sum-change-7-days.md#daily-sum-change-7-days)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout from the last week.|advanced|
|[daily_partition_sum_change_7_days](./column/anomaly/sum-change-7-days.md#daily-partition-sum-change-7-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout from the last week.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sum_change_30_days](./column/anomaly/sum-change-30-days.md#profile-sum-change-30-days)|profiling|Verifies that the sum in a column changed in a fixed rate since the last readout from last month.|advanced|
|[daily_sum_change_30_days](./column/anomaly/sum-change-30-days.md#daily-sum-change-30-days)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout from the last month.|advanced|
|[daily_partition_sum_change_30_days](./column/anomaly/sum-change-30-days.md#daily-partition-sum-change-30-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout from the last month.|advanced|






### **blanks**


| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_empty_text_found](./column/blanks/empty-text-found.md#profile-empty-text-found)|profiling|Verifies that empty strings in a column does not exceed the maximum accepted count.|standard|
|[daily_empty_text_found](./column/blanks/empty-text-found.md#daily-empty-text-found)|monitoring|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_empty_text_found](./column/blanks/empty-text-found.md#monthly-empty-text-found)|monitoring|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[daily_partition_empty_text_found](./column/blanks/empty-text-found.md#daily-partition-empty-text-found)|partitioned|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_empty_text_found](./column/blanks/empty-text-found.md#monthly-partition-empty-text-found)|partitioned|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_whitespace_text_found](./column/blanks/whitespace-text-found.md#profile-whitespace-text-found)|profiling|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count.|standard|
|[daily_whitespace_text_found](./column/blanks/whitespace-text-found.md#daily-whitespace-text-found)|monitoring|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_whitespace_text_found](./column/blanks/whitespace-text-found.md#monthly-whitespace-text-found)|monitoring|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[daily_partition_whitespace_text_found](./column/blanks/whitespace-text-found.md#daily-partition-whitespace-text-found)|partitioned|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_whitespace_text_found](./column/blanks/whitespace-text-found.md#monthly-partition-whitespace-text-found)|partitioned|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_null_placeholder_text_found](./column/blanks/null-placeholder-text-found.md#profile-null-placeholder-text-found)|profiling|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count.|standard|
|[daily_null_placeholder_text_found](./column/blanks/null-placeholder-text-found.md#daily-null-placeholder-text-found)|monitoring|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_null_placeholder_text_found](./column/blanks/null-placeholder-text-found.md#monthly-null-placeholder-text-found)|monitoring|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[daily_partition_null_placeholder_text_found](./column/blanks/null-placeholder-text-found.md#daily-partition-null-placeholder-text-found)|partitioned|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_null_placeholder_text_found](./column/blanks/null-placeholder-text-found.md#monthly-partition-null-placeholder-text-found)|partitioned|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_empty_text_percent](./column/blanks/empty-text-percent.md#profile-empty-text-percent)|profiling|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.|advanced|
|[daily_empty_text_percent](./column/blanks/empty-text-percent.md#daily-empty-text-percent)|monitoring|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_empty_text_percent](./column/blanks/empty-text-percent.md#monthly-empty-text-percent)|monitoring|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[daily_partition_empty_text_percent](./column/blanks/empty-text-percent.md#daily-partition-empty-text-percent)|partitioned|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_empty_text_percent](./column/blanks/empty-text-percent.md#monthly-partition-empty-text-percent)|partitioned|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_whitespace_text_percent](./column/blanks/whitespace-text-percent.md#profile-whitespace-text-percent)|profiling|Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.|advanced|
|[daily_whitespace_text_percent](./column/blanks/whitespace-text-percent.md#daily-whitespace-text-percent)|monitoring|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_whitespace_text_percent](./column/blanks/whitespace-text-percent.md#monthly-whitespace-text-percent)|monitoring|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[daily_partition_whitespace_text_percent](./column/blanks/whitespace-text-percent.md#daily-partition-whitespace-text-percent)|partitioned|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_whitespace_text_percent](./column/blanks/whitespace-text-percent.md#monthly-partition-whitespace-text-percent)|partitioned|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_null_placeholder_text_percent](./column/blanks/null-placeholder-text-percent.md#profile-null-placeholder-text-percent)|profiling|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.|advanced|
|[daily_null_placeholder_text_percent](./column/blanks/null-placeholder-text-percent.md#daily-null-placeholder-text-percent)|monitoring|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_null_placeholder_text_percent](./column/blanks/null-placeholder-text-percent.md#monthly-null-placeholder-text-percent)|monitoring|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[daily_partition_null_placeholder_text_percent](./column/blanks/null-placeholder-text-percent.md#daily-partition-null-placeholder-text-percent)|partitioned|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_null_placeholder_text_percent](./column/blanks/null-placeholder-text-percent.md#monthly-partition-null-placeholder-text-percent)|partitioned|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|






### **bool**
Calculates the percentage of data in a Boolean format.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_true_percent](./column/bool/true-percent.md#profile-true-percent)|profiling|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage.|standard|
|[daily_true_percent](./column/bool/true-percent.md#daily-true-percent)|monitoring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_true_percent](./column/bool/true-percent.md#monthly-true-percent)|monitoring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_true_percent](./column/bool/true-percent.md#daily-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_true_percent](./column/bool/true-percent.md#monthly-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_false_percent](./column/bool/false-percent.md#profile-false-percent)|profiling|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage.|standard|
|[daily_false_percent](./column/bool/false-percent.md#daily-false-percent)|monitoring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_false_percent](./column/bool/false-percent.md#monthly-false-percent)|monitoring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_false_percent](./column/bool/false-percent.md#daily-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_false_percent](./column/bool/false-percent.md#monthly-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|standard|






### **comparisons**


| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sum_match](./column/comparisons/sum-match.md#profile-sum-match)|profiling|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds.|standard|
|[daily_sum_match](./column/comparisons/sum-match.md#daily-sum-match)|monitoring|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_sum_match](./column/comparisons/sum-match.md#monthly-sum-match)|monitoring|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|standard|
|[daily_partition_sum_match](./column/comparisons/sum-match.md#daily-partition-sum-match)|partitioned|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|standard|
|[monthly_partition_sum_match](./column/comparisons/sum-match.md#monthly-partition-sum-match)|partitioned|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_min_match](./column/comparisons/min-match.md#profile-min-match)|profiling|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds.|standard|
|[daily_min_match](./column/comparisons/min-match.md#daily-min-match)|monitoring|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_min_match](./column/comparisons/min-match.md#monthly-min-match)|monitoring|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|standard|
|[daily_partition_min_match](./column/comparisons/min-match.md#daily-partition-min-match)|partitioned|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|standard|
|[monthly_partition_min_match](./column/comparisons/min-match.md#monthly-partition-min-match)|partitioned|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_max_match](./column/comparisons/max-match.md#profile-max-match)|profiling|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds.|standard|
|[daily_max_match](./column/comparisons/max-match.md#daily-max-match)|monitoring|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_max_match](./column/comparisons/max-match.md#monthly-max-match)|monitoring|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|standard|
|[daily_partition_max_match](./column/comparisons/max-match.md#daily-partition-max-match)|partitioned|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|standard|
|[monthly_partition_max_match](./column/comparisons/max-match.md#monthly-partition-max-match)|partitioned|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_mean_match](./column/comparisons/mean-match.md#profile-mean-match)|profiling|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds.|standard|
|[daily_mean_match](./column/comparisons/mean-match.md#daily-mean-match)|monitoring|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_mean_match](./column/comparisons/mean-match.md#monthly-mean-match)|monitoring|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|standard|
|[daily_partition_mean_match](./column/comparisons/mean-match.md#daily-partition-mean-match)|partitioned|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|standard|
|[monthly_partition_mean_match](./column/comparisons/mean-match.md#monthly-partition-mean-match)|partitioned|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_not_null_count_match](./column/comparisons/not-null-count-match.md#profile-not-null-count-match)|profiling|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds.|standard|
|[daily_not_null_count_match](./column/comparisons/not-null-count-match.md#daily-not-null-count-match)|monitoring|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_not_null_count_match](./column/comparisons/not-null-count-match.md#monthly-not-null-count-match)|monitoring|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|standard|
|[daily_partition_not_null_count_match](./column/comparisons/not-null-count-match.md#daily-partition-not-null-count-match)|partitioned|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|standard|
|[monthly_partition_not_null_count_match](./column/comparisons/not-null-count-match.md#monthly-partition-not-null-count-match)|partitioned|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_null_count_match](./column/comparisons/null-count-match.md#profile-null-count-match)|profiling|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds.|standard|
|[daily_null_count_match](./column/comparisons/null-count-match.md#daily-null-count-match)|monitoring|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_null_count_match](./column/comparisons/null-count-match.md#monthly-null-count-match)|monitoring|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|standard|
|[daily_partition_null_count_match](./column/comparisons/null-count-match.md#daily-partition-null-count-match)|partitioned|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|standard|
|[monthly_partition_null_count_match](./column/comparisons/null-count-match.md#monthly-partition-null-count-match)|partitioned|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|standard|






### **custom_sql**
Validate data against user-defined SQL queries at the column level. Checks in this group allows to validate that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sql_condition_passed_percent_on_column](./column/custom_sql/sql-condition-passed-percent-on-column.md#profile-sql-condition-passed-percent-on-column)|profiling|Verifies that a minimum percentage of rows passed a custom SQL condition (expression).|advanced|
|[daily_sql_condition_passed_percent_on_column](./column/custom_sql/sql-condition-passed-percent-on-column.md#daily-sql-condition-passed-percent-on-column)|monitoring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_sql_condition_passed_percent_on_column](./column/custom_sql/sql-condition-passed-percent-on-column.md#monthly-sql-condition-passed-percent-on-column)|monitoring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|advanced|
|[daily_partition_sql_condition_passed_percent_on_column](./column/custom_sql/sql-condition-passed-percent-on-column.md#daily-partition-sql-condition-passed-percent-on-column)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_sql_condition_passed_percent_on_column](./column/custom_sql/sql-condition-passed-percent-on-column.md#monthly-partition-sql-condition-passed-percent-on-column)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sql_condition_failed_count_on_column](./column/custom_sql/sql-condition-failed-count-on-column.md#profile-sql-condition-failed-count-on-column)|profiling|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count.|standard|
|[daily_sql_condition_failed_count_on_column](./column/custom_sql/sql-condition-failed-count-on-column.md#daily-sql-condition-failed-count-on-column)|monitoring|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_sql_condition_failed_count_on_column](./column/custom_sql/sql-condition-failed-count-on-column.md#monthly-sql-condition-failed-count-on-column)|monitoring|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_sql_condition_failed_count_on_column](./column/custom_sql/sql-condition-failed-count-on-column.md#daily-partition-sql-condition-failed-count-on-column)|partitioned|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_sql_condition_failed_count_on_column](./column/custom_sql/sql-condition-failed-count-on-column.md#monthly-partition-sql-condition-failed-count-on-column)|partitioned|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sql_aggregate_expression_on_column](./column/custom_sql/sql-aggregate-expression-on-column.md#profile-sql-aggregate-expression-on-column)|profiling|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|advanced|
|[daily_sql_aggregate_expression_on_column](./column/custom_sql/sql-aggregate-expression-on-column.md#daily-sql-aggregate-expression-on-column)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_sql_aggregate_expression_on_column](./column/custom_sql/sql-aggregate-expression-on-column.md#monthly-sql-aggregate-expression-on-column)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|advanced|
|[daily_partition_sql_aggregate_expression_on_column](./column/custom_sql/sql-aggregate-expression-on-column.md#daily-partition-sql-aggregate-expression-on-column)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_sql_aggregate_expression_on_column](./column/custom_sql/sql-aggregate-expression-on-column.md#monthly-partition-sql-aggregate-expression-on-column)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|






### **datatype**


| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_detected_datatype_in_text](./column/datatype/detected-datatype-in-text.md#profile-detected-datatype-in-text)|profiling|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type.|standard|
|[daily_detected_datatype_in_text](./column/datatype/detected-datatype-in-text.md#daily-detected-datatype-in-text)|monitoring|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_detected_datatype_in_text](./column/datatype/detected-datatype-in-text.md#monthly-detected-datatype-in-text)|monitoring|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_detected_datatype_in_text](./column/datatype/detected-datatype-in-text.md#daily-partition-detected-datatype-in-text)|partitioned|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_detected_datatype_in_text](./column/datatype/detected-datatype-in-text.md#monthly-partition-detected-datatype-in-text)|partitioned|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_detected_datatype_in_text_changed](./column/datatype/detected-datatype-in-text-changed.md#profile-detected-datatype-in-text-changed)|profiling|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|standard|
|[daily_detected_datatype_in_text_changed](./column/datatype/detected-datatype-in-text-changed.md#daily-detected-datatype-in-text-changed)|monitoring|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_detected_datatype_in_text_changed](./column/datatype/detected-datatype-in-text-changed.md#monthly-detected-datatype-in-text-changed)|monitoring|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[daily_partition_detected_datatype_in_text_changed](./column/datatype/detected-datatype-in-text-changed.md#daily-partition-detected-datatype-in-text-changed)|partitioned|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_detected_datatype_in_text_changed](./column/datatype/detected-datatype-in-text-changed.md#monthly-partition-detected-datatype-in-text-changed)|partitioned|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each monthly partition.|standard|






### **datetime**
Validates that the data in a date or time column is in the expected format and within predefined ranges.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_date_match_format_percent](./column/datetime/date-match-format-percent.md#profile-date-match-format-percent)|profiling|Verifies that the percentage of date values matching the given format in a column does not exceed the minimum accepted percentage.|advanced|
|[daily_date_match_format_percent](./column/datetime/date-match-format-percent.md#daily-date-match-format-percent)|monitoring|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily monitoring.|advanced|
|[monthly_date_match_format_percent](./column/datetime/date-match-format-percent.md#monthly-date-match-format-percent)|monitoring|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly monitoring.|advanced|
|[daily_partition_date_match_format_percent](./column/datetime/date-match-format-percent.md#daily-partition-date-match-format-percent)|partitioned|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_date_match_format_percent](./column/datetime/date-match-format-percent.md#monthly-partition-date-match-format-percent)|partitioned|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_date_values_in_future_percent](./column/datetime/date-values-in-future-percent.md#profile-date-values-in-future-percent)|profiling|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage.|standard|
|[daily_date_values_in_future_percent](./column/datetime/date-values-in-future-percent.md#daily-date-values-in-future-percent)|monitoring|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_date_values_in_future_percent](./column/datetime/date-values-in-future-percent.md#monthly-date-values-in-future-percent)|monitoring|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_date_values_in_future_percent](./column/datetime/date-values-in-future-percent.md#daily-partition-date-values-in-future-percent)|partitioned|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_date_values_in_future_percent](./column/datetime/date-values-in-future-percent.md#monthly-partition-date-values-in-future-percent)|partitioned|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_datetime_value_in_range_date_percent](./column/datetime/datetime-value-in-range-date-percent.md#profile-datetime-value-in-range-date-percent)|profiling|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage.|standard|
|[daily_datetime_value_in_range_date_percent](./column/datetime/datetime-value-in-range-date-percent.md#daily-datetime-value-in-range-date-percent)|monitoring|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_datetime_value_in_range_date_percent](./column/datetime/datetime-value-in-range-date-percent.md#monthly-datetime-value-in-range-date-percent)|monitoring|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_datetime_value_in_range_date_percent](./column/datetime/datetime-value-in-range-date-percent.md#daily-partition-datetime-value-in-range-date-percent)|partitioned|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_datetime_value_in_range_date_percent](./column/datetime/datetime-value-in-range-date-percent.md#monthly-partition-datetime-value-in-range-date-percent)|partitioned|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|standard|






### **integrity**
Checks the referential integrity of a column against a column in another table.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_lookup_key_not_found](./column/integrity/lookup-key-not-found.md#profile-lookup-key-not-found)|profiling|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count.|standard|
|[daily_lookup_key_not_found](./column/integrity/lookup-key-not-found.md#daily-lookup-key-not-found)|monitoring|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_lookup_key_not_found](./column/integrity/lookup-key-not-found.md#monthly-lookup-key-not-found)|monitoring|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_lookup_key_not_found](./column/integrity/lookup-key-not-found.md#daily-partition-lookup-key-not-found)|partitioned|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_lookup_key_not_found](./column/integrity/lookup-key-not-found.md#monthly-partition-lookup-key-not-found)|partitioned|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_lookup_key_found_percent](./column/integrity/lookup-key-found-percent.md#profile-lookup-key-found-percent)|profiling|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count.|advanced|
|[daily_lookup_key_found_percent](./column/integrity/lookup-key-found-percent.md#daily-lookup-key-found-percent)|monitoring|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_lookup_key_found_percent](./column/integrity/lookup-key-found-percent.md#monthly-lookup-key-found-percent)|monitoring|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|advanced|
|[daily_partition_lookup_key_found_percent](./column/integrity/lookup-key-found-percent.md#daily-partition-lookup-key-found-percent)|partitioned|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_lookup_key_found_percent](./column/integrity/lookup-key-found-percent.md#monthly-partition-lookup-key-found-percent)|partitioned|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|






### **nulls**
Checks for the presence of null or missing values in a column.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_nulls_count](./column/nulls/nulls-count.md#profile-nulls-count)|profiling|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count.|standard|
|[daily_nulls_count](./column/nulls/nulls-count.md#daily-nulls-count)|monitoring|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_nulls_count](./column/nulls/nulls-count.md#monthly-nulls-count)|monitoring|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_nulls_count](./column/nulls/nulls-count.md#daily-partition-nulls-count)|partitioned|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_nulls_count](./column/nulls/nulls-count.md#monthly-partition-nulls-count)|partitioned|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_nulls_percent](./column/nulls/nulls-percent.md#profile-nulls-percent)|profiling|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage.|advanced|
|[daily_nulls_percent](./column/nulls/nulls-percent.md#daily-nulls-percent)|monitoring|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_nulls_percent](./column/nulls/nulls-percent.md#monthly-nulls-percent)|monitoring|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|advanced|
|[daily_partition_nulls_percent](./column/nulls/nulls-percent.md#daily-partition-nulls-percent)|partitioned|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_nulls_percent](./column/nulls/nulls-percent.md#monthly-partition-nulls-percent)|partitioned|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_not_nulls_count](./column/nulls/not-nulls-count.md#profile-not-nulls-count)|profiling|Detects empty columns. The default rule min_count&#x3D;1 verifies that the column has any values. Verifies that the number of not null values in a column does not exceed the minimum accepted count.|standard|
|[daily_not_nulls_count](./column/nulls/not-nulls-count.md#daily-not-nulls-count)|monitoring|Detects empty columns. The default rule min_count&#x3D;1 verifies that the column has any values. Verifies that the number of not null values in a column does not exceed the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_not_nulls_count](./column/nulls/not-nulls-count.md#monthly-not-nulls-count)|monitoring|Detects columns that are empty and have no values (with the rule threshold min_count&#x3D;1). Verifies that the number of not null values in a column does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_not_nulls_count](./column/nulls/not-nulls-count.md#daily-partition-not-nulls-count)|partitioned|Detects empty columns. The default rule min_count&#x3D;1 verifies that the column has any values. Verifies that the number of not null values in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_not_nulls_count](./column/nulls/not-nulls-count.md#monthly-partition-not-nulls-count)|partitioned|Detects empty columns. The default rule min_count&#x3D;1 verifies that the column has any values. Verifies that the number of not null values in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_not_nulls_percent](./column/nulls/not-nulls-percent.md#profile-not-nulls-percent)|profiling|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage.|standard|
|[daily_not_nulls_percent](./column/nulls/not-nulls-percent.md#daily-not-nulls-percent)|monitoring|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_not_nulls_percent](./column/nulls/not-nulls-percent.md#monthly-not-nulls-percent)|monitoring|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_not_nulls_percent](./column/nulls/not-nulls-percent.md#daily-partition-not-nulls-percent)|partitioned|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_not_nulls_percent](./column/nulls/not-nulls-percent.md#monthly-partition-not-nulls-percent)|partitioned|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_nulls_percent_anomaly](./column/nulls/nulls-percent-anomaly.md#profile-nulls-percent-anomaly)|profiling|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|advanced|
|[daily_nulls_percent_anomaly](./column/nulls/nulls-percent-anomaly.md#daily-nulls-percent-anomaly)|monitoring|Verifies that the null percent value in a column changes in a rate within a percentile boundary during the last 90 days.|advanced|
|[daily_partition_nulls_percent_anomaly](./column/nulls/nulls-percent-anomaly.md#daily-partition-nulls-percent-anomaly)|partitioned|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_nulls_percent_change](./column/nulls/nulls-percent-change.md#profile-nulls-percent-change)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout.|advanced|
|[daily_nulls_percent_change](./column/nulls/nulls-percent-change.md#daily-nulls-percent-change)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since the last readout.|advanced|
|[daily_partition_nulls_percent_change](./column/nulls/nulls-percent-change.md#daily-partition-nulls-percent-change)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_nulls_percent_change_1_day](./column/nulls/nulls-percent-change-1-day.md#profile-nulls-percent-change-1-day)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|advanced|
|[daily_nulls_percent_change_1_day](./column/nulls/nulls-percent-change-1-day.md#daily-nulls-percent-change-1-day)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since the last readout from yesterday.|advanced|
|[daily_partition_nulls_percent_change_1_day](./column/nulls/nulls-percent-change-1-day.md#daily-partition-nulls-percent-change-1-day)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since the last readout from yesterday.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_nulls_percent_change_7_days](./column/nulls/nulls-percent-change-7-days.md#profile-nulls-percent-change-7-days)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|advanced|
|[daily_nulls_percent_change_7_days](./column/nulls/nulls-percent-change-7-days.md#daily-nulls-percent-change-7-days)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last week.|advanced|
|[daily_partition_nulls_percent_change_7_days](./column/nulls/nulls-percent-change-7-days.md#daily-partition-nulls-percent-change-7-days)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last week.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_nulls_percent_change_30_days](./column/nulls/nulls-percent-change-30-days.md#profile-nulls-percent-change-30-days)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|advanced|
|[daily_nulls_percent_change_30_days](./column/nulls/nulls-percent-change-30-days.md#daily-nulls-percent-change-30-days)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last month.|advanced|
|[daily_partition_nulls_percent_change_30_days](./column/nulls/nulls-percent-change-30-days.md#daily-partition-nulls-percent-change-30-days)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last month.|advanced|






### **numeric**
Validates that the data in a numeric column is in the expected format or within predefined ranges.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_number_below_min_value](./column/numeric/number-below-min-value.md#profile-number-below-min-value)|profiling|The check counts the number of values in the column that is below the value defined by the user as a parameter.|standard|
|[daily_number_below_min_value](./column/numeric/number-below-min-value.md#daily-number-below-min-value)|monitoring|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_number_below_min_value](./column/numeric/number-below-min-value.md#monthly-number-below-min-value)|monitoring|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|standard|
|[daily_partition_number_below_min_value](./column/numeric/number-below-min-value.md#daily-partition-number-below-min-value)|partitioned|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_number_below_min_value](./column/numeric/number-below-min-value.md#monthly-partition-number-below-min-value)|partitioned|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_number_above_max_value](./column/numeric/number-above-max-value.md#profile-number-above-max-value)|profiling|The check counts the number of values in the column that is above the value defined by the user as a parameter.|standard|
|[daily_number_above_max_value](./column/numeric/number-above-max-value.md#daily-number-above-max-value)|monitoring|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_number_above_max_value](./column/numeric/number-above-max-value.md#monthly-number-above-max-value)|monitoring|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|standard|
|[daily_partition_number_above_max_value](./column/numeric/number-above-max-value.md#daily-partition-number-above-max-value)|partitioned|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_number_above_max_value](./column/numeric/number-above-max-value.md#monthly-partition-number-above-max-value)|partitioned|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_negative_values](./column/numeric/negative-values.md#profile-negative-values)|profiling|Verifies that the number of negative values in a column does not exceed the maximum accepted count.|standard|
|[daily_negative_values](./column/numeric/negative-values.md#daily-negative-values)|monitoring|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_negative_values](./column/numeric/negative-values.md#monthly-negative-values)|monitoring|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.|standard|
|[daily_partition_negative_values](./column/numeric/negative-values.md#daily-partition-negative-values)|partitioned|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_negative_values](./column/numeric/negative-values.md#monthly-partition-negative-values)|partitioned|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_negative_values_percent](./column/numeric/negative-values-percent.md#profile-negative-values-percent)|profiling|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.|advanced|
|[daily_negative_values_percent](./column/numeric/negative-values-percent.md#daily-negative-values-percent)|monitoring|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_negative_values_percent](./column/numeric/negative-values-percent.md#monthly-negative-values-percent)|monitoring|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_negative_values_percent](./column/numeric/negative-values-percent.md#daily-partition-negative-values-percent)|partitioned|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_negative_values_percent](./column/numeric/negative-values-percent.md#monthly-partition-negative-values-percent)|partitioned|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_number_below_min_value_percent](./column/numeric/number-below-min-value-percent.md#profile-number-below-min-value-percent)|profiling|The check counts the percentage of values in the column that is below the value defined by the user as a parameter.|advanced|
|[daily_number_below_min_value_percent](./column/numeric/number-below-min-value-percent.md#daily-number-below-min-value-percent)|monitoring|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_number_below_min_value_percent](./column/numeric/number-below-min-value-percent.md#monthly-number-below-min-value-percent)|monitoring|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_number_below_min_value_percent](./column/numeric/number-below-min-value-percent.md#daily-partition-number-below-min-value-percent)|partitioned|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_number_below_min_value_percent](./column/numeric/number-below-min-value-percent.md#monthly-partition-number-below-min-value-percent)|partitioned|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_number_above_max_value_percent](./column/numeric/number-above-max-value-percent.md#profile-number-above-max-value-percent)|profiling|The check counts the percentage of values in the column that is above the value defined by the user as a parameter.|advanced|
|[daily_number_above_max_value_percent](./column/numeric/number-above-max-value-percent.md#daily-number-above-max-value-percent)|monitoring|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_number_above_max_value_percent](./column/numeric/number-above-max-value-percent.md#monthly-number-above-max-value-percent)|monitoring|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_number_above_max_value_percent](./column/numeric/number-above-max-value-percent.md#daily-partition-number-above-max-value-percent)|partitioned|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_number_above_max_value_percent](./column/numeric/number-above-max-value-percent.md#monthly-partition-number-above-max-value-percent)|partitioned|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_number_in_range_percent](./column/numeric/number-in-range-percent.md#profile-number-in-range-percent)|profiling|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|advanced|
|[daily_number_in_range_percent](./column/numeric/number-in-range-percent.md#daily-number-in-range-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_number_in_range_percent](./column/numeric/number-in-range-percent.md#monthly-number-in-range-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_number_in_range_percent](./column/numeric/number-in-range-percent.md#daily-partition-number-in-range-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_number_in_range_percent](./column/numeric/number-in-range-percent.md#monthly-partition-number-in-range-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_integer_in_range_percent](./column/numeric/integer-in-range-percent.md#profile-integer-in-range-percent)|profiling|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|advanced|
|[daily_integer_in_range_percent](./column/numeric/integer-in-range-percent.md#daily-integer-in-range-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_integer_in_range_percent](./column/numeric/integer-in-range-percent.md#monthly-integer-in-range-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_integer_in_range_percent](./column/numeric/integer-in-range-percent.md#daily-partition-integer-in-range-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_integer_in_range_percent](./column/numeric/integer-in-range-percent.md#monthly-partition-integer-in-range-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_min_in_range](./column/numeric/min-in-range.md#profile-min-in-range)|profiling|Verifies that the minimal value in a column is not outside the set range.|standard|
|[daily_min_in_range](./column/numeric/min-in-range.md#daily-min-in-range)|monitoring|Verifies that the minimal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_min_in_range](./column/numeric/min-in-range.md#monthly-min-in-range)|monitoring|Verifies that the minimal value in a column does not exceed the set range. Stores the most recent value for each month when the data quality check was evaluated.|standard|
|[daily_partition_min_in_range](./column/numeric/min-in-range.md#daily-partition-min-in-range)|partitioned|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_min_in_range](./column/numeric/min-in-range.md#monthly-partition-min-in-range)|partitioned|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_max_in_range](./column/numeric/max-in-range.md#profile-max-in-range)|profiling|Verifies that the maximal value in a column is not outside the set range.|standard|
|[daily_max_in_range](./column/numeric/max-in-range.md#daily-max-in-range)|monitoring|Verifies that the maximal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_max_in_range](./column/numeric/max-in-range.md#monthly-max-in-range)|monitoring|Verifies that the maximal value in a column does not exceed the set range. Stores the most recent value for each month when the data quality check was evaluated.|standard|
|[daily_partition_max_in_range](./column/numeric/max-in-range.md#daily-partition-max-in-range)|partitioned|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_max_in_range](./column/numeric/max-in-range.md#monthly-partition-max-in-range)|partitioned|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sum_in_range](./column/numeric/sum-in-range.md#profile-sum-in-range)|profiling|Verifies that the sum of all values in a column is not outside the set range.|standard|
|[daily_sum_in_range](./column/numeric/sum-in-range.md#daily-sum-in-range)|monitoring|Verifies that the sum of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_sum_in_range](./column/numeric/sum-in-range.md#monthly-sum-in-range)|monitoring|Verifies that the sum of all values in a column does not exceed the set range. Stores the most recent value for each month when the data quality check was evaluated.|standard|
|[daily_partition_sum_in_range](./column/numeric/sum-in-range.md#daily-partition-sum-in-range)|partitioned|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_sum_in_range](./column/numeric/sum-in-range.md#monthly-partition-sum-in-range)|partitioned|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_mean_in_range](./column/numeric/mean-in-range.md#profile-mean-in-range)|profiling|Verifies that the average (mean) of all values in a column is not outside the set range.|advanced|
|[daily_mean_in_range](./column/numeric/mean-in-range.md#daily-mean-in-range)|monitoring|Verifies that the average (mean) of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_mean_in_range](./column/numeric/mean-in-range.md#monthly-mean-in-range)|monitoring|Verifies that the average (mean) of all values in a column does not exceed the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_mean_in_range](./column/numeric/mean-in-range.md#daily-partition-mean-in-range)|partitioned|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_mean_in_range](./column/numeric/mean-in-range.md#monthly-partition-mean-in-range)|partitioned|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_median_in_range](./column/numeric/median-in-range.md#profile-median-in-range)|profiling|Verifies that the median of all values in a column is not outside the set range.|advanced|
|[daily_median_in_range](./column/numeric/median-in-range.md#daily-median-in-range)|monitoring|Verifies that the median of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[monthly_median_in_range](./column/numeric/median-in-range.md#monthly-median-in-range)|monitoring|Verifies that the median of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_median_in_range](./column/numeric/median-in-range.md#daily-partition-median-in-range)|partitioned|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_median_in_range](./column/numeric/median-in-range.md#monthly-partition-median-in-range)|partitioned|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_percentile_in_range](./column/numeric/percentile-in-range.md#profile-percentile-in-range)|profiling|Verifies that the percentile of all values in a column is not outside the set range.|advanced|
|[daily_percentile_in_range](./column/numeric/percentile-in-range.md#daily-percentile-in-range)|monitoring|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_percentile_in_range](./column/numeric/percentile-in-range.md#monthly-percentile-in-range)|monitoring|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_percentile_in_range](./column/numeric/percentile-in-range.md#daily-partition-percentile-in-range)|partitioned|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_percentile_in_range](./column/numeric/percentile-in-range.md#monthly-partition-percentile-in-range)|partitioned|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_percentile_10_in_range](./column/numeric/percentile-10-in-range.md#profile-percentile-10-in-range)|profiling|Verifies that the percentile 10 of all values in a column is not outside the set range.|advanced|
|[daily_percentile_10_in_range](./column/numeric/percentile-10-in-range.md#daily-percentile-10-in-range)|monitoring|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_percentile_10_in_range](./column/numeric/percentile-10-in-range.md#monthly-percentile-10-in-range)|monitoring|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_percentile_10_in_range](./column/numeric/percentile-10-in-range.md#daily-partition-percentile-10-in-range)|partitioned|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_percentile_10_in_range](./column/numeric/percentile-10-in-range.md#monthly-partition-percentile-10-in-range)|partitioned|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_percentile_25_in_range](./column/numeric/percentile-25-in-range.md#profile-percentile-25-in-range)|profiling|Verifies that the percentile 25 of all values in a column is not outside the set range.|advanced|
|[daily_percentile_25_in_range](./column/numeric/percentile-25-in-range.md#daily-percentile-25-in-range)|monitoring|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_percentile_25_in_range](./column/numeric/percentile-25-in-range.md#monthly-percentile-25-in-range)|monitoring|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_percentile_25_in_range](./column/numeric/percentile-25-in-range.md#daily-partition-percentile-25-in-range)|partitioned|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_percentile_25_in_range](./column/numeric/percentile-25-in-range.md#monthly-partition-percentile-25-in-range)|partitioned|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_percentile_75_in_range](./column/numeric/percentile-75-in-range.md#profile-percentile-75-in-range)|profiling|Verifies that the percentile 75 of all values in a column is not outside the set range.|advanced|
|[daily_percentile_75_in_range](./column/numeric/percentile-75-in-range.md#daily-percentile-75-in-range)|monitoring|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_percentile_75_in_range](./column/numeric/percentile-75-in-range.md#monthly-percentile-75-in-range)|monitoring|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_percentile_75_in_range](./column/numeric/percentile-75-in-range.md#daily-partition-percentile-75-in-range)|partitioned|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_percentile_75_in_range](./column/numeric/percentile-75-in-range.md#monthly-partition-percentile-75-in-range)|partitioned|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_percentile_90_in_range](./column/numeric/percentile-90-in-range.md#profile-percentile-90-in-range)|profiling|Verifies that the percentile 90 of all values in a column is not outside the set range.|advanced|
|[daily_percentile_90_in_range](./column/numeric/percentile-90-in-range.md#daily-percentile-90-in-range)|monitoring|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_percentile_90_in_range](./column/numeric/percentile-90-in-range.md#monthly-percentile-90-in-range)|monitoring|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_percentile_90_in_range](./column/numeric/percentile-90-in-range.md#daily-partition-percentile-90-in-range)|partitioned|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_percentile_90_in_range](./column/numeric/percentile-90-in-range.md#monthly-partition-percentile-90-in-range)|partitioned|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sample_stddev_in_range](./column/numeric/sample-stddev-in-range.md#profile-sample-stddev-in-range)|profiling|Verifies that the sample standard deviation of all values in a column is not outside the set range.|advanced|
|[daily_sample_stddev_in_range](./column/numeric/sample-stddev-in-range.md#daily-sample-stddev-in-range)|monitoring|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_sample_stddev_in_range](./column/numeric/sample-stddev-in-range.md#monthly-sample-stddev-in-range)|monitoring|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_sample_stddev_in_range](./column/numeric/sample-stddev-in-range.md#daily-partition-sample-stddev-in-range)|partitioned|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_sample_stddev_in_range](./column/numeric/sample-stddev-in-range.md#monthly-partition-sample-stddev-in-range)|partitioned|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_population_stddev_in_range](./column/numeric/population-stddev-in-range.md#profile-population-stddev-in-range)|profiling|Verifies that the population standard deviation of all values in a column is not outside the set range.|advanced|
|[daily_population_stddev_in_range](./column/numeric/population-stddev-in-range.md#daily-population-stddev-in-range)|monitoring|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_population_stddev_in_range](./column/numeric/population-stddev-in-range.md#monthly-population-stddev-in-range)|monitoring|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_population_stddev_in_range](./column/numeric/population-stddev-in-range.md#daily-partition-population-stddev-in-range)|partitioned|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_population_stddev_in_range](./column/numeric/population-stddev-in-range.md#monthly-partition-population-stddev-in-range)|partitioned|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sample_variance_in_range](./column/numeric/sample-variance-in-range.md#profile-sample-variance-in-range)|profiling|Verifies that the sample variance of all values in a column is not outside the set range.|advanced|
|[daily_sample_variance_in_range](./column/numeric/sample-variance-in-range.md#daily-sample-variance-in-range)|monitoring|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_sample_variance_in_range](./column/numeric/sample-variance-in-range.md#monthly-sample-variance-in-range)|monitoring|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_sample_variance_in_range](./column/numeric/sample-variance-in-range.md#daily-partition-sample-variance-in-range)|partitioned|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_sample_variance_in_range](./column/numeric/sample-variance-in-range.md#monthly-partition-sample-variance-in-range)|partitioned|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_population_variance_in_range](./column/numeric/population-variance-in-range.md#profile-population-variance-in-range)|profiling|Verifies that the population variance of all values in a column is not outside the set range.|advanced|
|[daily_population_variance_in_range](./column/numeric/population-variance-in-range.md#daily-population-variance-in-range)|monitoring|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_population_variance_in_range](./column/numeric/population-variance-in-range.md#monthly-population-variance-in-range)|monitoring|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_population_variance_in_range](./column/numeric/population-variance-in-range.md#daily-partition-population-variance-in-range)|partitioned|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_population_variance_in_range](./column/numeric/population-variance-in-range.md#monthly-partition-population-variance-in-range)|partitioned|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_invalid_latitude](./column/numeric/invalid-latitude.md#profile-invalid-latitude)|profiling|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count.|advanced|
|[daily_invalid_latitude](./column/numeric/invalid-latitude.md#daily-invalid-latitude)|monitoring|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_invalid_latitude](./column/numeric/invalid-latitude.md#monthly-invalid-latitude)|monitoring|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_invalid_latitude](./column/numeric/invalid-latitude.md#daily-partition-invalid-latitude)|partitioned|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_invalid_latitude](./column/numeric/invalid-latitude.md#monthly-partition-invalid-latitude)|partitioned|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_valid_latitude_percent](./column/numeric/valid-latitude-percent.md#profile-valid-latitude-percent)|profiling|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage.|advanced|
|[daily_valid_latitude_percent](./column/numeric/valid-latitude-percent.md#daily-valid-latitude-percent)|monitoring|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_valid_latitude_percent](./column/numeric/valid-latitude-percent.md#monthly-valid-latitude-percent)|monitoring|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_valid_latitude_percent](./column/numeric/valid-latitude-percent.md#daily-partition-valid-latitude-percent)|partitioned|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_valid_latitude_percent](./column/numeric/valid-latitude-percent.md#monthly-partition-valid-latitude-percent)|partitioned|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_invalid_longitude](./column/numeric/invalid-longitude.md#profile-invalid-longitude)|profiling|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count.|advanced|
|[daily_invalid_longitude](./column/numeric/invalid-longitude.md#daily-invalid-longitude)|monitoring|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_invalid_longitude](./column/numeric/invalid-longitude.md#monthly-invalid-longitude)|monitoring|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_invalid_longitude](./column/numeric/invalid-longitude.md#daily-partition-invalid-longitude)|partitioned|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_invalid_longitude](./column/numeric/invalid-longitude.md#monthly-partition-invalid-longitude)|partitioned|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_valid_longitude_percent](./column/numeric/valid-longitude-percent.md#profile-valid-longitude-percent)|profiling|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage.|advanced|
|[daily_valid_longitude_percent](./column/numeric/valid-longitude-percent.md#daily-valid-longitude-percent)|monitoring|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_valid_longitude_percent](./column/numeric/valid-longitude-percent.md#monthly-valid-longitude-percent)|monitoring|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_valid_longitude_percent](./column/numeric/valid-longitude-percent.md#daily-partition-valid-longitude-percent)|partitioned|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_valid_longitude_percent](./column/numeric/valid-longitude-percent.md#monthly-partition-valid-longitude-percent)|partitioned|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_non_negative_values](./column/numeric/non-negative-values.md#profile-non-negative-values)|profiling|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count.|advanced|
|[daily_non_negative_values](./column/numeric/non-negative-values.md#daily-non-negative-values)|monitoring|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_non_negative_values](./column/numeric/non-negative-values.md#monthly-non-negative-values)|monitoring|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_non_negative_values](./column/numeric/non-negative-values.md#daily-partition-non-negative-values)|partitioned|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_non_negative_values](./column/numeric/non-negative-values.md#monthly-partition-non-negative-values)|partitioned|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_non_negative_values_percent](./column/numeric/non-negative-values-percent.md#profile-non-negative-values-percent)|profiling|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage.|advanced|
|[daily_non_negative_values_percent](./column/numeric/non-negative-values-percent.md#daily-non-negative-values-percent)|monitoring|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_non_negative_values_percent](./column/numeric/non-negative-values-percent.md#monthly-non-negative-values-percent)|monitoring|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_non_negative_values_percent](./column/numeric/non-negative-values-percent.md#daily-partition-non-negative-values-percent)|partitioned|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_non_negative_values_percent](./column/numeric/non-negative-values-percent.md#monthly-partition-non-negative-values-percent)|partitioned|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|






### **patterns**


| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_not_matching_regex_found](./column/patterns/text-not-matching-regex-found.md#profile-text-not-matching-regex-found)|profiling|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|standard|
|[daily_text_not_matching_regex_found](./column/patterns/text-not-matching-regex-found.md#daily-text-not-matching-regex-found)|monitoring|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|standard|
|[monthly_text_not_matching_regex_found](./column/patterns/text-not-matching-regex-found.md#monthly-text-not-matching-regex-found)|monitoring|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|standard|
|[daily_partition_text_not_matching_regex_found](./column/patterns/text-not-matching-regex-found.md#daily-partition-text-not-matching-regex-found)|partitioned|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|standard|
|[monthly_partition_text_not_matching_regex_found](./column/patterns/text-not-matching-regex-found.md#monthly-partition-text-not-matching-regex-found)|partitioned|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_texts_matching_regex_percent](./column/patterns/texts-matching-regex-percent.md#profile-texts-matching-regex-percent)|profiling|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|standard|
|[daily_texts_matching_regex_percent](./column/patterns/texts-matching-regex-percent.md#daily-texts-matching-regex-percent)|monitoring|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|standard|
|[monthly_texts_matching_regex_percent](./column/patterns/texts-matching-regex-percent.md#monthly-texts-matching-regex-percent)|monitoring|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|standard|
|[daily_partition_texts_matching_regex_percent](./column/patterns/texts-matching-regex-percent.md#daily-partition-texts-matching-regex-percent)|partitioned|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|standard|
|[monthly_partition_texts_matching_regex_percent](./column/patterns/texts-matching-regex-percent.md#monthly-partition-texts-matching-regex-percent)|partitioned|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_invalid_email_format_found](./column/patterns/invalid-email-format-found.md#profile-invalid-email-format-found)|profiling|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|standard|
|[daily_invalid_email_format_found](./column/patterns/invalid-email-format-found.md#daily-invalid-email-format-found)|monitoring|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|standard|
|[monthly_invalid_email_format_found](./column/patterns/invalid-email-format-found.md#monthly-invalid-email-format-found)|monitoring|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|standard|
|[daily_partition_invalid_email_format_found](./column/patterns/invalid-email-format-found.md#daily-partition-invalid-email-format-found)|partitioned|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|standard|
|[monthly_partition_invalid_email_format_found](./column/patterns/invalid-email-format-found.md#monthly-partition-invalid-email-format-found)|partitioned|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_not_matching_date_pattern_found](./column/patterns/text-not-matching-date-pattern-found.md#profile-text-not-matching-date-pattern-found)|profiling|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|advanced|
|[daily_text_not_matching_date_pattern_found](./column/patterns/text-not-matching-date-pattern-found.md#daily-text-not-matching-date-pattern-found)|monitoring|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|advanced|
|[monthly_text_not_matching_date_pattern_found](./column/patterns/text-not-matching-date-pattern-found.md#monthly-text-not-matching-date-pattern-found)|monitoring|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|advanced|
|[daily_partition_text_not_matching_date_pattern_found](./column/patterns/text-not-matching-date-pattern-found.md#daily-partition-text-not-matching-date-pattern-found)|partitioned|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|advanced|
|[monthly_partition_text_not_matching_date_pattern_found](./column/patterns/text-not-matching-date-pattern-found.md#monthly-partition-text-not-matching-date-pattern-found)|partitioned|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_matching_date_pattern_percent](./column/patterns/text-matching-date-pattern-percent.md#profile-text-matching-date-pattern-percent)|profiling|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|advanced|
|[daily_text_matching_date_pattern_percent](./column/patterns/text-matching-date-pattern-percent.md#daily-text-matching-date-pattern-percent)|monitoring|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|advanced|
|[monthly_text_matching_date_pattern_percent](./column/patterns/text-matching-date-pattern-percent.md#monthly-text-matching-date-pattern-percent)|monitoring|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|advanced|
|[daily_partition_text_matching_date_pattern_percent](./column/patterns/text-matching-date-pattern-percent.md#daily-partition-text-matching-date-pattern-percent)|partitioned|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|advanced|
|[monthly_partition_text_matching_date_pattern_percent](./column/patterns/text-matching-date-pattern-percent.md#monthly-partition-text-matching-date-pattern-percent)|partitioned|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_matching_name_pattern_percent](./column/patterns/text-matching-name-pattern-percent.md#profile-text-matching-name-pattern-percent)|profiling|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|advanced|
|[daily_text_matching_name_pattern_percent](./column/patterns/text-matching-name-pattern-percent.md#daily-text-matching-name-pattern-percent)|monitoring|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|advanced|
|[monthly_text_matching_name_pattern_percent](./column/patterns/text-matching-name-pattern-percent.md#monthly-text-matching-name-pattern-percent)|monitoring|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|advanced|
|[daily_partition_text_matching_name_pattern_percent](./column/patterns/text-matching-name-pattern-percent.md#daily-partition-text-matching-name-pattern-percent)|partitioned|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|advanced|
|[monthly_partition_text_matching_name_pattern_percent](./column/patterns/text-matching-name-pattern-percent.md#monthly-partition-text-matching-name-pattern-percent)|partitioned|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_invalid_uuid_format_found](./column/patterns/invalid-uuid-format-found.md#profile-invalid-uuid-format-found)|profiling|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|advanced|
|[daily_invalid_uuid_format_found](./column/patterns/invalid-uuid-format-found.md#daily-invalid-uuid-format-found)|monitoring|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|advanced|
|[monthly_invalid_uuid_format_found](./column/patterns/invalid-uuid-format-found.md#monthly-invalid-uuid-format-found)|monitoring|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|advanced|
|[daily_partition_invalid_uuid_format_found](./column/patterns/invalid-uuid-format-found.md#daily-partition-invalid-uuid-format-found)|partitioned|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|advanced|
|[monthly_partition_invalid_uuid_format_found](./column/patterns/invalid-uuid-format-found.md#monthly-partition-invalid-uuid-format-found)|partitioned|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_valid_uuid_format_percent](./column/patterns/valid-uuid-format-percent.md#profile-valid-uuid-format-percent)|profiling|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|advanced|
|[daily_valid_uuid_format_percent](./column/patterns/valid-uuid-format-percent.md#daily-valid-uuid-format-percent)|monitoring|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|advanced|
|[monthly_valid_uuid_format_percent](./column/patterns/valid-uuid-format-percent.md#monthly-valid-uuid-format-percent)|monitoring|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|advanced|
|[daily_partition_valid_uuid_format_percent](./column/patterns/valid-uuid-format-percent.md#daily-partition-valid-uuid-format-percent)|partitioned|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|advanced|
|[monthly_partition_valid_uuid_format_percent](./column/patterns/valid-uuid-format-percent.md#monthly-partition-valid-uuid-format-percent)|partitioned|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_invalid_ip4_address_format_found](./column/patterns/invalid-ip4-address-format-found.md#profile-invalid-ip4-address-format-found)|profiling|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|advanced|
|[daily_invalid_ip4_address_format_found](./column/patterns/invalid-ip4-address-format-found.md#daily-invalid-ip4-address-format-found)|monitoring|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|advanced|
|[monthly_invalid_ip4_address_format_found](./column/patterns/invalid-ip4-address-format-found.md#monthly-invalid-ip4-address-format-found)|monitoring|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|advanced|
|[daily_partition_invalid_ip4_address_format_found](./column/patterns/invalid-ip4-address-format-found.md#daily-partition-invalid-ip4-address-format-found)|partitioned|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|advanced|
|[monthly_partition_invalid_ip4_address_format_found](./column/patterns/invalid-ip4-address-format-found.md#monthly-partition-invalid-ip4-address-format-found)|partitioned|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_invalid_ip6_address_format_found](./column/patterns/invalid-ip6-address-format-found.md#profile-invalid-ip6-address-format-found)|profiling|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|advanced|
|[daily_invalid_ip6_address_format_found](./column/patterns/invalid-ip6-address-format-found.md#daily-invalid-ip6-address-format-found)|monitoring|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|advanced|
|[monthly_invalid_ip6_address_format_found](./column/patterns/invalid-ip6-address-format-found.md#monthly-invalid-ip6-address-format-found)|monitoring|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|advanced|
|[daily_partition_invalid_ip6_address_format_found](./column/patterns/invalid-ip6-address-format-found.md#daily-partition-invalid-ip6-address-format-found)|partitioned|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|advanced|
|[monthly_partition_invalid_ip6_address_format_found](./column/patterns/invalid-ip6-address-format-found.md#monthly-partition-invalid-ip6-address-format-found)|partitioned|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|advanced|






### **pii**
Checks for the presence of sensitive or personally identifiable information (PII) in a column such as email, phone, zip code, IP4 and IP6 addresses.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent.md#profile-contains-usa-phone-percent)|profiling|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage.|standard|
|[daily_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent.md#daily-contains-usa-phone-percent)|monitoring|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent.md#monthly-contains-usa-phone-percent)|monitoring|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent.md#daily-partition-contains-usa-phone-percent)|partitioned|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent.md#monthly-partition-contains-usa-phone-percent)|partitioned|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent.md#profile-contains-usa-zipcode-percent)|profiling|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.|advanced|
|[daily_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent.md#daily-contains-usa-zipcode-percent)|monitoring|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent.md#monthly-contains-usa-zipcode-percent)|monitoring|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|advanced|
|[daily_partition_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent.md#daily-partition-contains-usa-zipcode-percent)|partitioned|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent.md#monthly-partition-contains-usa-zipcode-percent)|partitioned|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_contains_email_percent](./column/pii/contains-email-percent.md#profile-contains-email-percent)|profiling|Verifies that the percentage of rows that contains valid emails in a column does not exceed the minimum accepted percentage.|standard|
|[daily_contains_email_percent](./column/pii/contains-email-percent.md#daily-contains-email-percent)|monitoring|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_contains_email_percent](./column/pii/contains-email-percent.md#monthly-contains-email-percent)|monitoring|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_contains_email_percent](./column/pii/contains-email-percent.md#daily-partition-contains-email-percent)|partitioned|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_contains_email_percent](./column/pii/contains-email-percent.md#monthly-partition-contains-email-percent)|partitioned|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_contains_ip4_percent](./column/pii/contains-ip4-percent.md#profile-contains-ip4-percent)|profiling|Verifies that the percentage of rows that contains valid IP4 address values in a column does not fall below the minimum accepted percentage.|advanced|
|[daily_contains_ip4_percent](./column/pii/contains-ip4-percent.md#daily-contains-ip4-percent)|monitoring|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_contains_ip4_percent](./column/pii/contains-ip4-percent.md#monthly-contains-ip4-percent)|monitoring|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|advanced|
|[daily_partition_contains_ip4_percent](./column/pii/contains-ip4-percent.md#daily-partition-contains-ip4-percent)|partitioned|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_contains_ip4_percent](./column/pii/contains-ip4-percent.md#monthly-partition-contains-ip4-percent)|partitioned|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_contains_ip6_percent](./column/pii/contains-ip6-percent.md#profile-contains-ip6-percent)|profiling|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage.|advanced|
|[daily_contains_ip6_percent](./column/pii/contains-ip6-percent.md#daily-contains-ip6-percent)|monitoring|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_contains_ip6_percent](./column/pii/contains-ip6-percent.md#monthly-contains-ip6-percent)|monitoring|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|advanced|
|[daily_partition_contains_ip6_percent](./column/pii/contains-ip6-percent.md#daily-partition-contains-ip6-percent)|partitioned|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_contains_ip6_percent](./column/pii/contains-ip6-percent.md#monthly-partition-contains-ip6-percent)|partitioned|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|






### **schema**
Detects schema drifts such as a column is missing or the data type has changed.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_column_exists](./column/schema/column-exists.md#profile-column-exists)|profiling|Checks the metadata of the monitored table and verifies if the column exists.|standard|
|[daily_column_exists](./column/schema/column-exists.md#daily-column-exists)|monitoring|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each day when the data quality check was evaluated.|standard|
|[monthly_column_exists](./column/schema/column-exists.md#monthly-column-exists)|monitoring|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each month when the data quality check was evaluated.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_column_type_changed](./column/schema/column-type-changed.md#profile-column-type-changed)|profiling|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed.|standard|
|[daily_column_type_changed](./column/schema/column-type-changed.md#daily-column-type-changed)|monitoring|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last day. Stores the most recent hash for each day when the data quality check was evaluated.|standard|
|[monthly_column_type_changed](./column/schema/column-type-changed.md#monthly-column-type-changed)|monitoring|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last month. Stores the most recent hash for each month when the data quality check was evaluated.|standard|






### **text**
Validates that the data in a string column match the expected format or pattern.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_max_length](./column/text/text-max-length.md#profile-text-max-length)|profiling|Verifies that the length of a text in a column does not exceed the maximum accepted length|standard|
|[daily_text_max_length](./column/text/text-max-length.md#daily-text-max-length)|monitoring|Verifies that the length of a text in a column does not exceed the maximum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_text_max_length](./column/text/text-max-length.md#monthly-text-max-length)|monitoring|Verifies that the length of a text in a column does not exceed the maximum accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.|standard|
|[daily_partition_text_max_length](./column/text/text-max-length.md#daily-partition-text-max-length)|partitioned|Verifies that the length of a text in a column does not exceed the maximum accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|standard|
|[monthly_partition_text_max_length](./column/text/text-max-length.md#monthly-partition-text-max-length)|partitioned|Verifies that the length of a text in a column does not exceed the maximum accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_min_length](./column/text/text-min-length.md#profile-text-min-length)|profiling|Verifies that the length of a text in a column does not fall below the minimum accepted length|standard|
|[daily_text_min_length](./column/text/text-min-length.md#daily-text-min-length)|monitoring|Verifies that the length of a text in a column does not fall below the minimum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_text_min_length](./column/text/text-min-length.md#monthly-text-min-length)|monitoring|Verifies that the length of a text in a column does not fall below the minimum accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.|standard|
|[daily_partition_text_min_length](./column/text/text-min-length.md#daily-partition-text-min-length)|partitioned|Verifies that the length of a text in a column does not fall below the minimum accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|standard|
|[monthly_partition_text_min_length](./column/text/text-min-length.md#monthly-partition-text-min-length)|partitioned|Verifies that the length of a text in a column does not fall below the minimum accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_mean_length](./column/text/text-mean-length.md#profile-text-mean-length)|profiling|Verifies that the length of a text in a column does not exceed the mean accepted length|advanced|
|[daily_text_mean_length](./column/text/text-mean-length.md#daily-text-mean-length)|monitoring|Verifies that the length of a text in a column does not exceed the mean accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_mean_length](./column/text/text-mean-length.md#monthly-text-mean-length)|monitoring|Verifies that the length of a text in a column does not exceed the mean accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_mean_length](./column/text/text-mean-length.md#daily-partition-text-mean-length)|partitioned|Verifies that the length of a text in a column does not exceed the mean accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_mean_length](./column/text/text-mean-length.md#monthly-partition-text-mean-length)|partitioned|Verifies that the length of a text in a column does not exceed the mean accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_length_below_min_length](./column/text/text-length-below-min-length.md#profile-text-length-below-min-length)|profiling|The check counts the number of text values in the column that is below the length defined by the user as a parameter|advanced|
|[daily_text_length_below_min_length](./column/text/text-length-below-min-length.md#daily-text-length-below-min-length)|monitoring|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_length_below_min_length](./column/text/text-length-below-min-length.md#monthly-text-length-below-min-length)|monitoring|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_length_below_min_length](./column/text/text-length-below-min-length.md#daily-partition-text-length-below-min-length)|partitioned|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_length_below_min_length](./column/text/text-length-below-min-length.md#monthly-partition-text-length-below-min-length)|partitioned|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_length_below_min_length_percent](./column/text/text-length-below-min-length-percent.md#profile-text-length-below-min-length-percent)|profiling|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter|advanced|
|[daily_text_length_below_min_length_percent](./column/text/text-length-below-min-length-percent.md#daily-text-length-below-min-length-percent)|monitoring|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_length_below_min_length_percent](./column/text/text-length-below-min-length-percent.md#monthly-text-length-below-min-length-percent)|monitoring|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_length_below_min_length_percent](./column/text/text-length-below-min-length-percent.md#daily-partition-text-length-below-min-length-percent)|partitioned|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_length_below_min_length_percent](./column/text/text-length-below-min-length-percent.md#monthly-partition-text-length-below-min-length-percent)|partitioned|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_length_above_max_length](./column/text/text-length-above-max-length.md#profile-text-length-above-max-length)|profiling|The check counts the number of text values in the column that is above the length defined by the user as a parameter|advanced|
|[daily_text_length_above_max_length](./column/text/text-length-above-max-length.md#daily-text-length-above-max-length)|monitoring|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_length_above_max_length](./column/text/text-length-above-max-length.md#monthly-text-length-above-max-length)|monitoring|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_length_above_max_length](./column/text/text-length-above-max-length.md#daily-partition-text-length-above-max-length)|partitioned|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_length_above_max_length](./column/text/text-length-above-max-length.md#monthly-partition-text-length-above-max-length)|partitioned|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_length_above_max_length_percent](./column/text/text-length-above-max-length-percent.md#profile-text-length-above-max-length-percent)|profiling|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter|advanced|
|[daily_text_length_above_max_length_percent](./column/text/text-length-above-max-length-percent.md#daily-text-length-above-max-length-percent)|monitoring|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_length_above_max_length_percent](./column/text/text-length-above-max-length-percent.md#monthly-text-length-above-max-length-percent)|monitoring|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_length_above_max_length_percent](./column/text/text-length-above-max-length-percent.md#daily-partition-text-length-above-max-length-percent)|partitioned|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_length_above_max_length_percent](./column/text/text-length-above-max-length-percent.md#monthly-partition-text-length-above-max-length-percent)|partitioned|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_length_in_range_percent](./column/text/text-length-in-range-percent.md#profile-text-length-in-range-percent)|profiling|The check measures the percentage of those text values with length in the range provided by the user in the column|advanced|
|[daily_text_length_in_range_percent](./column/text/text-length-in-range-percent.md#daily-text-length-in-range-percent)|monitoring|The check measures the percentage of those text values with length in the range provided by the user in the column. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_length_in_range_percent](./column/text/text-length-in-range-percent.md#monthly-text-length-in-range-percent)|monitoring|The check measures the percentage of those text values with length in the range provided by the user in the column. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_length_in_range_percent](./column/text/text-length-in-range-percent.md#daily-partition-text-length-in-range-percent)|partitioned|The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_length_in_range_percent](./column/text/text-length-in-range-percent.md#monthly-partition-text-length-in-range-percent)|partitioned|The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_parsable_to_boolean_percent](./column/text/text-parsable-to-boolean-percent.md#profile-text-parsable-to-boolean-percent)|profiling|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n.|advanced|
|[daily_text_parsable_to_boolean_percent](./column/text/text-parsable-to-boolean-percent.md#daily-text-parsable-to-boolean-percent)|monitoring|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_parsable_to_boolean_percent](./column/text/text-parsable-to-boolean-percent.md#monthly-text-parsable-to-boolean-percent)|monitoring|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_parsable_to_boolean_percent](./column/text/text-parsable-to-boolean-percent.md#daily-partition-text-parsable-to-boolean-percent)|partitioned|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_parsable_to_boolean_percent](./column/text/text-parsable-to-boolean-percent.md#monthly-partition-text-parsable-to-boolean-percent)|partitioned|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_parsable_to_integer_percent](./column/text/text-parsable-to-integer-percent.md#profile-text-parsable-to-integer-percent)|profiling|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage|advanced|
|[daily_text_parsable_to_integer_percent](./column/text/text-parsable-to-integer-percent.md#daily-text-parsable-to-integer-percent)|monitoring|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_parsable_to_integer_percent](./column/text/text-parsable-to-integer-percent.md#monthly-text-parsable-to-integer-percent)|monitoring|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_parsable_to_integer_percent](./column/text/text-parsable-to-integer-percent.md#daily-partition-text-parsable-to-integer-percent)|partitioned|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_parsable_to_integer_percent](./column/text/text-parsable-to-integer-percent.md#monthly-partition-text-parsable-to-integer-percent)|partitioned|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_parsable_to_float_percent](./column/text/text-parsable-to-float-percent.md#profile-text-parsable-to-float-percent)|profiling|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage|advanced|
|[daily_text_parsable_to_float_percent](./column/text/text-parsable-to-float-percent.md#daily-text-parsable-to-float-percent)|monitoring|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_parsable_to_float_percent](./column/text/text-parsable-to-float-percent.md#monthly-text-parsable-to-float-percent)|monitoring|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_parsable_to_float_percent](./column/text/text-parsable-to-float-percent.md#daily-partition-text-parsable-to-float-percent)|partitioned|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_parsable_to_float_percent](./column/text/text-parsable-to-float-percent.md#monthly-partition-text-parsable-to-float-percent)|partitioned|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_parsable_to_date_percent](./column/text/text-parsable-to-date-percent.md#profile-text-parsable-to-date-percent)|profiling|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression|advanced|
|[daily_text_parsable_to_date_percent](./column/text/text-parsable-to-date-percent.md#daily-text-parsable-to-date-percent)|monitoring|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_parsable_to_date_percent](./column/text/text-parsable-to-date-percent.md#monthly-text-parsable-to-date-percent)|monitoring|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_parsable_to_date_percent](./column/text/text-parsable-to-date-percent.md#daily-partition-text-parsable-to-date-percent)|partitioned|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_parsable_to_date_percent](./column/text/text-parsable-to-date-percent.md#monthly-partition-text-parsable-to-date-percent)|partitioned|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_surrounded_by_whitespace](./column/text/text-surrounded-by-whitespace.md#profile-text-surrounded-by-whitespace)|profiling|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table|advanced|
|[daily_text_surrounded_by_whitespace](./column/text/text-surrounded-by-whitespace.md#daily-text-surrounded-by-whitespace)|monitoring|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_surrounded_by_whitespace](./column/text/text-surrounded-by-whitespace.md#monthly-text-surrounded-by-whitespace)|monitoring|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_surrounded_by_whitespace](./column/text/text-surrounded-by-whitespace.md#daily-partition-text-surrounded-by-whitespace)|partitioned|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_surrounded_by_whitespace](./column/text/text-surrounded-by-whitespace.md#monthly-partition-text-surrounded-by-whitespace)|partitioned|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_surrounded_by_whitespace_percent](./column/text/text-surrounded-by-whitespace-percent.md#profile-text-surrounded-by-whitespace-percent)|profiling|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage|advanced|
|[daily_text_surrounded_by_whitespace_percent](./column/text/text-surrounded-by-whitespace-percent.md#daily-text-surrounded-by-whitespace-percent)|monitoring|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_surrounded_by_whitespace_percent](./column/text/text-surrounded-by-whitespace-percent.md#monthly-text-surrounded-by-whitespace-percent)|monitoring|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_surrounded_by_whitespace_percent](./column/text/text-surrounded-by-whitespace-percent.md#daily-partition-text-surrounded-by-whitespace-percent)|partitioned|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_surrounded_by_whitespace_percent](./column/text/text-surrounded-by-whitespace-percent.md#monthly-partition-text-surrounded-by-whitespace-percent)|partitioned|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_valid_country_code_percent](./column/text/text-valid-country-code-percent.md#profile-text-valid-country-code-percent)|profiling|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage|advanced|
|[daily_text_valid_country_code_percent](./column/text/text-valid-country-code-percent.md#daily-text-valid-country-code-percent)|monitoring|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_valid_country_code_percent](./column/text/text-valid-country-code-percent.md#monthly-text-valid-country-code-percent)|monitoring|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_valid_country_code_percent](./column/text/text-valid-country-code-percent.md#daily-partition-text-valid-country-code-percent)|partitioned|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_valid_country_code_percent](./column/text/text-valid-country-code-percent.md#monthly-partition-text-valid-country-code-percent)|partitioned|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_text_valid_currency_code_percent](./column/text/text-valid-currency-code-percent.md#profile-text-valid-currency-code-percent)|profiling|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage|advanced|
|[daily_text_valid_currency_code_percent](./column/text/text-valid-currency-code-percent.md#daily-text-valid-currency-code-percent)|monitoring|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_valid_currency_code_percent](./column/text/text-valid-currency-code-percent.md#monthly-text-valid-currency-code-percent)|monitoring|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_valid_currency_code_percent](./column/text/text-valid-currency-code-percent.md#daily-partition-text-valid-currency-code-percent)|partitioned|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_valid_currency_code_percent](./column/text/text-valid-currency-code-percent.md#monthly-partition-text-valid-currency-code-percent)|partitioned|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|






### **uniqueness**
Counts the number or percent of duplicate or unique values in a column.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_distinct_count](./column/uniqueness/distinct-count.md#profile-distinct-count)|profiling|Verifies that the number of distinct values in a column does not fall below the minimum accepted count.|standard|
|[daily_distinct_count](./column/uniqueness/distinct-count.md#daily-distinct-count)|monitoring|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_distinct_count](./column/uniqueness/distinct-count.md#monthly-distinct-count)|monitoring|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_distinct_count](./column/uniqueness/distinct-count.md#daily-partition-distinct-count)|partitioned|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_distinct_count](./column/uniqueness/distinct-count.md#monthly-partition-distinct-count)|partitioned|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_distinct_percent](./column/uniqueness/distinct-percent.md#profile-distinct-percent)|profiling|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent.|standard|
|[daily_distinct_percent](./column/uniqueness/distinct-percent.md#daily-distinct-percent)|monitoring|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_distinct_percent](./column/uniqueness/distinct-percent.md#monthly-distinct-percent)|monitoring|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_distinct_percent](./column/uniqueness/distinct-percent.md#daily-partition-distinct-percent)|partitioned|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_distinct_percent](./column/uniqueness/distinct-percent.md#monthly-partition-distinct-percent)|partitioned|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_duplicate_count](./column/uniqueness/duplicate-count.md#profile-duplicate-count)|profiling|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.|standard|
|[daily_duplicate_count](./column/uniqueness/duplicate-count.md#daily-duplicate-count)|monitoring|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_duplicate_count](./column/uniqueness/duplicate-count.md#monthly-duplicate-count)|monitoring|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_duplicate_count](./column/uniqueness/duplicate-count.md#daily-partition-duplicate-count)|partitioned|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_duplicate_count](./column/uniqueness/duplicate-count.md#monthly-partition-duplicate-count)|partitioned|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_duplicate_percent](./column/uniqueness/duplicate-percent.md#profile-duplicate-percent)|profiling|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.|advanced|
|[daily_duplicate_percent](./column/uniqueness/duplicate-percent.md#daily-duplicate-percent)|monitoring|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_duplicate_percent](./column/uniqueness/duplicate-percent.md#monthly-duplicate-percent)|monitoring|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|advanced|
|[daily_partition_duplicate_percent](./column/uniqueness/duplicate-percent.md#daily-partition-duplicate-percent)|partitioned|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_duplicate_percent](./column/uniqueness/duplicate-percent.md#monthly-partition-duplicate-percent)|partitioned|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_distinct_count_anomaly](./column/uniqueness/distinct-count-anomaly.md#profile-distinct-count-anomaly)|profiling|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|standard|
|[daily_distinct_count_anomaly](./column/uniqueness/distinct-count-anomaly.md#daily-distinct-count-anomaly)|monitoring|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|standard|
|[daily_partition_distinct_count_anomaly](./column/uniqueness/distinct-count-anomaly.md#daily-partition-distinct-count-anomaly)|partitioned|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_distinct_percent_anomaly](./column/uniqueness/distinct-percent-anomaly.md#profile-distinct-percent-anomaly)|profiling|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|advanced|
|[daily_distinct_percent_anomaly](./column/uniqueness/distinct-percent-anomaly.md#daily-distinct-percent-anomaly)|monitoring|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|advanced|
|[daily_partition_distinct_percent_anomaly](./column/uniqueness/distinct-percent-anomaly.md#daily-partition-distinct-percent-anomaly)|partitioned|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_distinct_count_change](./column/uniqueness/distinct-count-change.md#profile-distinct-count-change)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|advanced|
|[daily_distinct_count_change](./column/uniqueness/distinct-count-change.md#daily-distinct-count-change)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|advanced|
|[monthly_distinct_count_change](./column/uniqueness/distinct-count-change.md#monthly-distinct-count-change)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|advanced|
|[daily_partition_distinct_count_change](./column/uniqueness/distinct-count-change.md#daily-partition-distinct-count-change)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|advanced|
|[monthly_partition_distinct_count_change](./column/uniqueness/distinct-count-change.md#monthly-partition-distinct-count-change)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_distinct_count_change_1_day](./column/uniqueness/distinct-count-change-1-day.md#profile-distinct-count-change-1-day)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|advanced|
|[daily_distinct_count_change_1_day](./column/uniqueness/distinct-count-change-1-day.md#daily-distinct-count-change-1-day)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|advanced|
|[daily_partition_distinct_count_change_1_day](./column/uniqueness/distinct-count-change-1-day.md#daily-partition-distinct-count-change-1-day)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_distinct_count_change_7_days](./column/uniqueness/distinct-count-change-7-days.md#profile-distinct-count-change-7-days)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|advanced|
|[daily_distinct_count_change_7_days](./column/uniqueness/distinct-count-change-7-days.md#daily-distinct-count-change-7-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|advanced|
|[daily_partition_distinct_count_change_7_days](./column/uniqueness/distinct-count-change-7-days.md#daily-partition-distinct-count-change-7-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from the last week.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_distinct_count_change_30_days](./column/uniqueness/distinct-count-change-30-days.md#profile-distinct-count-change-30-days)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|advanced|
|[daily_distinct_count_change_30_days](./column/uniqueness/distinct-count-change-30-days.md#daily-distinct-count-change-30-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|advanced|
|[daily_partition_distinct_count_change_30_days](./column/uniqueness/distinct-count-change-30-days.md#daily-partition-distinct-count-change-30-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from the last month.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_distinct_percent_change](./column/uniqueness/distinct-percent-change.md#profile-distinct-percent-change)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|advanced|
|[daily_distinct_percent_change](./column/uniqueness/distinct-percent-change.md#daily-distinct-percent-change)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|advanced|
|[monthly_distinct_percent_change](./column/uniqueness/distinct-percent-change.md#monthly-distinct-percent-change)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|advanced|
|[daily_partition_distinct_percent_change](./column/uniqueness/distinct-percent-change.md#daily-partition-distinct-percent-change)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|advanced|
|[monthly_partition_distinct_percent_change](./column/uniqueness/distinct-percent-change.md#monthly-partition-distinct-percent-change)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_distinct_percent_change_1_day](./column/uniqueness/distinct-percent-change-1-day.md#profile-distinct-percent-change-1-day)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|advanced|
|[daily_distinct_percent_change_1_day](./column/uniqueness/distinct-percent-change-1-day.md#daily-distinct-percent-change-1-day)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|advanced|
|[daily_partition_distinct_percent_change_1_day](./column/uniqueness/distinct-percent-change-1-day.md#daily-partition-distinct-percent-change-1-day)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_distinct_percent_change_7_days](./column/uniqueness/distinct-percent-change-7-days.md#profile-distinct-percent-change-7-days)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|advanced|
|[daily_distinct_percent_change_7_days](./column/uniqueness/distinct-percent-change-7-days.md#daily-distinct-percent-change-7-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|advanced|
|[daily_partition_distinct_percent_change_7_days](./column/uniqueness/distinct-percent-change-7-days.md#daily-partition-distinct-percent-change-7-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from the last week.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_distinct_percent_change_30_days](./column/uniqueness/distinct-percent-change-30-days.md#profile-distinct-percent-change-30-days)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|advanced|
|[daily_distinct_percent_change_30_days](./column/uniqueness/distinct-percent-change-30-days.md#daily-distinct-percent-change-30-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|advanced|
|[daily_partition_distinct_percent_change_30_days](./column/uniqueness/distinct-percent-change-30-days.md#daily-partition-distinct-percent-change-30-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from the last month.|advanced|







