# Checks/column/custom_sql

This is a list of custom_sql column data quality checks supported by DQOps and a brief description of what they do.





## **custom_sql**
Validate data against user-defined SQL queries at the column level. Checks in this group allows to validate that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sql_condition_passed_percent_on_column](./sql-condition-passed-percent-on-column.md#profile-sql-condition-passed-percent-on-column)|profiling|Verifies that a minimum percentage of rows passed a custom SQL condition (expression).|advanced|
|[daily_sql_condition_passed_percent_on_column](./sql-condition-passed-percent-on-column.md#daily-sql-condition-passed-percent-on-column)|monitoring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_sql_condition_passed_percent_on_column](./sql-condition-passed-percent-on-column.md#monthly-sql-condition-passed-percent-on-column)|monitoring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|advanced|
|[daily_partition_sql_condition_passed_percent_on_column](./sql-condition-passed-percent-on-column.md#daily-partition-sql-condition-passed-percent-on-column)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_sql_condition_passed_percent_on_column](./sql-condition-passed-percent-on-column.md#monthly-partition-sql-condition-passed-percent-on-column)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sql_condition_failed_count_on_column](./sql-condition-failed-count-on-column.md#profile-sql-condition-failed-count-on-column)|profiling|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count.|standard|
|[daily_sql_condition_failed_count_on_column](./sql-condition-failed-count-on-column.md#daily-sql-condition-failed-count-on-column)|monitoring|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_sql_condition_failed_count_on_column](./sql-condition-failed-count-on-column.md#monthly-sql-condition-failed-count-on-column)|monitoring|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_sql_condition_failed_count_on_column](./sql-condition-failed-count-on-column.md#daily-partition-sql-condition-failed-count-on-column)|partitioned|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_sql_condition_failed_count_on_column](./sql-condition-failed-count-on-column.md#monthly-partition-sql-condition-failed-count-on-column)|partitioned|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sql_aggregate_expression_on_column](./sql-aggregate-expression-on-column.md#profile-sql-aggregate-expression-on-column)|profiling|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|advanced|
|[daily_sql_aggregate_expression_on_column](./sql-aggregate-expression-on-column.md#daily-sql-aggregate-expression-on-column)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_sql_aggregate_expression_on_column](./sql-aggregate-expression-on-column.md#monthly-sql-aggregate-expression-on-column)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|advanced|
|[daily_partition_sql_aggregate_expression_on_column](./sql-aggregate-expression-on-column.md#daily-partition-sql-aggregate-expression-on-column)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_sql_aggregate_expression_on_column](./sql-aggregate-expression-on-column.md#monthly-partition-sql-aggregate-expression-on-column)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|







