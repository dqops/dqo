# Checks/table/sql

**This is a list of sql table data quality checks supported by DQOps and a brief description of what they do.**





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





