---
title: List of column level custom sql data quality checks
---
# List of column level custom sql data quality checks

This is a list of custom_sql column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level custom_sql checks
Validate data against user-defined SQL queries at the column level. Checks in this group allow to validate whether a set percentage of rows has passed a custom SQL expression or whether the custom SQL expression is not outside the set range.

### [sql condition failed on column](./sql-condition-failed-on-column.md)
A column-level check that uses a custom SQL expression on each column to verify (assert) that all rows pass a custom condition defined as an SQL expression.
 Use the {alias} token to reference the tested table, and the {column} to reference the column that is tested. This data quality check can be used to compare columns on the same table.
 For example, when this check is applied on a *col_price* column, the condition can verify that the *col_price* is higher than the *col_tax* using an SQL expression: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;
 Use an SQL expression that returns a *true* value for valid values and *false* for invalid values, because it is an assertion.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sql_condition_failed_on_column`</span>](./sql-condition-failed-on-column.md#profile-sql-condition-failed-on-column)|Maximum count of rows that failed SQL conditions|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; col_tax&#x60;.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_sql_condition_failed_on_column`</span>](./sql-condition-failed-on-column.md#daily-sql-condition-failed-on-column)|Maximum count of rows that failed SQL conditions|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; col_tax&#x60;. Stores the most recent captured count of failed rows for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_sql_condition_failed_on_column`</span>](./sql-condition-failed-on-column.md#monthly-sql-condition-failed-on-column)|Maximum count of rows that failed SQL conditions|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores the most recent captured count of failed rows for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_sql_condition_failed_on_column`</span>](./sql-condition-failed-on-column.md#daily-partition-sql-condition-failed-on-column)|Maximum count of rows that failed SQL conditions|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_sql_condition_failed_on_column`</span>](./sql-condition-failed-on-column.md#monthly-partition-sql-condition-failed-on-column)|Maximum count of rows that failed SQL conditions|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [sql condition passed percent on column](./sql-condition-passed-percent-on-column.md)
A table-level check that ensures that a minimum percentage of rows passed a custom SQL condition (expression). Measures the percentage of rows passing the condition.
 Raises a data quality issue when the percent of valid rows is below the *min_percent* parameter.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sql_condition_passed_percent_on_column`</span>](./sql-condition-passed-percent-on-column.md#profile-sql-condition-passed-percent-on-column)|Minimum percentage of rows that passed SQL condition|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;.| |
|[<span class="no-wrap-code">`daily_sql_condition_passed_percent_on_column`</span>](./sql-condition-passed-percent-on-column.md#daily-sql-condition-passed-percent-on-column)|Minimum percentage of rows that passed SQL condition|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_sql_condition_passed_percent_on_column`</span>](./sql-condition-passed-percent-on-column.md#monthly-sql-condition-passed-percent-on-column)|Minimum percentage of rows that passed SQL condition|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;.  Stores the most recent check result for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_sql_condition_passed_percent_on_column`</span>](./sql-condition-passed-percent-on-column.md#daily-partition-sql-condition-passed-percent-on-column)|Minimum percentage of rows that passed SQL condition|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_sql_condition_passed_percent_on_column`</span>](./sql-condition-passed-percent-on-column.md#monthly-partition-sql-condition-passed-percent-on-column)|Minimum percentage of rows that passed SQL condition|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each monthly partition.| |



### [sql aggregate expression on column](./sql-aggregate-expression-on-column.md)
A column-level check that calculates a given SQL aggregate expression on a column and verifies if the value is within a range of accepted values.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sql_aggregate_expression_on_column`</span>](./sql-aggregate-expression-on-column.md#profile-sql-aggregate-expression-on-column)|Custom aggregated SQL expression within range |[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_sql_aggregate_expression_on_column`</span>](./sql-aggregate-expression-on-column.md#daily-sql-aggregate-expression-on-column)|Custom aggregated SQL expression within range |[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_sql_aggregate_expression_on_column`</span>](./sql-aggregate-expression-on-column.md#monthly-sql-aggregate-expression-on-column)|Custom aggregated SQL expression within range |[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent check result for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_sql_aggregate_expression_on_column`</span>](./sql-aggregate-expression-on-column.md#daily-partition-sql-aggregate-expression-on-column)|Custom aggregated SQL expression within range |[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_sql_aggregate_expression_on_column`</span>](./sql-aggregate-expression-on-column.md#monthly-partition-sql-aggregate-expression-on-column)|Custom aggregated SQL expression within range |[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |



### [sql invalid value count on column](./sql-invalid-value-count-on-column.md)
A column-level check that uses a custom SQL query that return invalid values from column.
 This check is used for setting testing queries or ready queries used by users in their own systems (legacy SQL queries).
 Use the {table} token to reference the tested table, and the {column} to reference the column that is tested.
 For example, when this check is applied on a column. The condition can find invalid values in the column which have values lower than 18 using an SQL query: &#x60;SELECT {column} FROM {table} WHERE {column} &lt; 18&#x60;.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sql_invalid_value_count_on_column`</span>](./sql-invalid-value-count-on-column.md#profile-sql-invalid-value-count-on-column)|Custom SELECT SQL that returns invalid values|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Runs a custom query that retrieves invalid values found in a column and returns the number of them, and raises an issue if too many failures were detected. This check is used for setting testing queries or ready queries used by users in their own systems (legacy SQL queries). For example, when this check is applied on a column. The condition can find invalid values in the column which have values lower than 18 using an SQL query: &#x60;SELECT {column} FROM {table} WHERE {column} &lt; 18&#x60;.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_sql_invalid_value_count_on_column`</span>](./sql-invalid-value-count-on-column.md#daily-sql-invalid-value-count-on-column)|Custom SELECT SQL that returns invalid values|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Runs a custom query that retrieves invalid values found in a column and returns the number of them, and raises an issue if too many failures were detected. This check is used for setting testing queries or ready queries used by users in their own systems (legacy SQL queries). For example, when this check is applied on a column. The condition can find invalid values in the column which have values lower than 18 using an SQL query: &#x60;SELECT {column} FROM {table} WHERE {column} &lt; 18&#x60;.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_sql_invalid_value_count_on_column`</span>](./sql-invalid-value-count-on-column.md#monthly-sql-invalid-value-count-on-column)|Custom SELECT SQL that returns invalid values|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Runs a custom query that retrieves invalid values found in a column and returns the number of them, and raises an issue if too many failures were detected. This check is used for setting testing queries or ready queries used by users in their own systems (legacy SQL queries). For example, when this check is applied on a column. The condition can find invalid values in the column which have values lower than 18 using an SQL query: &#x60;SELECT {column} FROM {table} WHERE {column} &lt; 18&#x60;.|:material-check-bold:|



### [import custom result on column](./import-custom-result-on-column.md)
Column level check that uses a custom SQL SELECT statement to retrieve a result of running a custom data quality check on a column by a custom
 data quality check, hardcoded in the data pipeline. The result is retrieved by querying a separate **logging table**, whose schema is not fixed.
 The logging table should have columns that identify a table and a column for which they store custom data quality check results, and a *severity* column of the data quality issue.
 The SQL query that is configured in this external data quality results importer must be
 a complete SELECT statement that queries a dedicated logging table, created by the data engineering team.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_import_custom_result_on_column`</span>](./import-custom-result-on-column.md#profile-import-custom-result-on-column)|Import custom data quality results on column|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.| |
|[<span class="no-wrap-code">`daily_import_custom_result_on_column`</span>](./import-custom-result-on-column.md#daily-import-custom-result-on-column)|Import custom data quality results on column|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.| |
|[<span class="no-wrap-code">`monthly_import_custom_result_on_column`</span>](./import-custom-result-on-column.md#monthly-import-custom-result-on-column)|Import custom data quality results on column|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.| |







