# Checks/table/custom_sql

This is a list of custom_sql table data quality checks supported by DQOps and a brief description of what they do.





## **custom_sql**
Validate data against user-defined SQL queries at the table level. Checks in this group allow for validation that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sql_condition_passed_percent_on_table](./sql-condition-passed-percent-on-table.md#profile-sql-condition-passed-percent-on-table)|profiling|Verifies that a set percentage of rows passed a custom SQL condition (expression).|advanced|
|[daily_sql_condition_passed_percent_on_table](./sql-condition-passed-percent-on-table.md#daily-sql-condition-passed-percent-on-table)|monitoring|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_sql_condition_passed_percent_on_table](./sql-condition-passed-percent-on-table.md#monthly-sql-condition-passed-percent-on-table)|monitoring|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|advanced|
|[daily_partition_sql_condition_passed_percent_on_table](./sql-condition-passed-percent-on-table.md#daily-partition-sql-condition-passed-percent-on-table)|partitioned|Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_sql_condition_passed_percent_on_table](./sql-condition-passed-percent-on-table.md#monthly-partition-sql-condition-passed-percent-on-table)|partitioned|Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sql_condition_failed_count_on_table](./sql-condition-failed-count-on-table.md#profile-sql-condition-failed-count-on-table)|profiling|Verifies that a set number of rows failed a custom SQL condition (expression).|standard|
|[daily_sql_condition_failed_count_on_table](./sql-condition-failed-count-on-table.md#daily-sql-condition-failed-count-on-table)|monitoring|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_sql_condition_failed_count_on_table](./sql-condition-failed-count-on-table.md#monthly-sql-condition-failed-count-on-table)|monitoring|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_sql_condition_failed_count_on_table](./sql-condition-failed-count-on-table.md#daily-partition-sql-condition-failed-count-on-table)|partitioned|Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_sql_condition_failed_count_on_table](./sql-condition-failed-count-on-table.md#monthly-partition-sql-condition-failed-count-on-table)|partitioned|Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sql_aggregate_expression_on_table](./sql-aggregate-expression-on-table.md#profile-sql-aggregate-expression-on-table)|profiling|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|advanced|
|[daily_sql_aggregate_expression_on_table](./sql-aggregate-expression-on-table.md#daily-sql-aggregate-expression-on-table)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_sql_aggregate_expression_on_table](./sql-aggregate-expression-on-table.md#monthly-sql-aggregate-expression-on-table)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|advanced|
|[daily_partition_sql_aggregate_expression_on_table](./sql-aggregate-expression-on-table.md#daily-partition-sql-aggregate-expression-on-table)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_sql_aggregate_expression_on_table](./sql-aggregate-expression-on-table.md#monthly-partition-sql-aggregate-expression-on-table)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|







