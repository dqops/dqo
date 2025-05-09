---
title: List of table level custom sql data quality checks
---
# List of table level custom sql data quality checks

This is a list of custom_sql table data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## table-level custom_sql checks
Validate data against user-defined SQL queries at the table level. Checks in this group allow for validation that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

### [sql condition failed on table](./sql-condition-failed-on-table.md)
A table-level check that uses a custom SQL expression on each row to verify (assert) that all rows pass a custom condition defined as an SQL condition.
 Use the {alias} token to reference the tested table. This data quality check can be used to compare columns on the same table.
 For example, the condition can verify that the value in the *col_price* column is higher than the *col_tax* column using an SQL expression: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;.
 Use an SQL expression that returns a *true* value for valid values and a *false* one for invalid values, because it is an assertion.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sql_condition_failed_on_table`</span>](./sql-condition-failed-on-table.md#profile-sql-condition-failed-on-table)|Maximum count of rows that failed SQL conditions|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_sql_condition_failed_on_table`</span>](./sql-condition-failed-on-table.md#daily-sql-condition-failed-on-table)|Maximum count of rows that failed SQL conditions|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent count of failed rows for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_sql_condition_failed_on_table`</span>](./sql-condition-failed-on-table.md#monthly-sql-condition-failed-on-table)|Maximum count of rows that failed SQL conditions|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent count of failed rows for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_sql_condition_failed_on_table`</span>](./sql-condition-failed-on-table.md#daily-partition-sql-condition-failed-on-table)|Maximum count of rows that failed SQL conditions|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_sql_condition_failed_on_table`</span>](./sql-condition-failed-on-table.md#monthly-partition-sql-condition-failed-on-table)|Maximum count of rows that failed SQL conditions|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [sql condition passed percent on table](./sql-condition-passed-percent-on-table.md)
A table-level check that ensures that a minimum percentage of rows passed a custom SQL condition (expression). Measures the percentage of rows passing the condition.
 Raises a data quality issue when the percent of valid rows is below the *min_percent* parameter.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sql_condition_passed_percent_on_table`</span>](./sql-condition-passed-percent-on-table.md#profile-sql-condition-passed-percent-on-table)|Minimum percentage of rows that passed SQL condition|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;.| |
|[<span class="no-wrap-code">`daily_sql_condition_passed_percent_on_table`</span>](./sql-condition-passed-percent-on-table.md#daily-sql-condition-passed-percent-on-table)|Minimum percentage of rows that passed SQL condition|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent captured percentage for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_sql_condition_passed_percent_on_table`</span>](./sql-condition-passed-percent-on-table.md#monthly-sql-condition-passed-percent-on-table)|Minimum percentage of rows that passed SQL condition|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_sql_condition_passed_percent_on_table`</span>](./sql-condition-passed-percent-on-table.md#daily-partition-sql-condition-passed-percent-on-table)|Minimum percentage of rows that passed SQL condition|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_sql_condition_passed_percent_on_table`</span>](./sql-condition-passed-percent-on-table.md#monthly-partition-sql-condition-passed-percent-on-table)|Minimum percentage of rows that passed SQL condition|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each monthly partition.| |



### [sql aggregate expression on table](./sql-aggregate-expression-on-table.md)
A table-level check that calculates a given SQL aggregate expression on a table and verifies if the value is within a range of accepted values.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sql_aggregate_expression_on_table`</span>](./sql-aggregate-expression-on-table.md#profile-sql-aggregate-expression-on-table)|Custom aggregated SQL expression within range|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_sql_aggregate_expression_on_table`</span>](./sql-aggregate-expression-on-table.md#daily-sql-aggregate-expression-on-table)|Custom aggregated SQL expression within range|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_sql_aggregate_expression_on_table`</span>](./sql-aggregate-expression-on-table.md#monthly-sql-aggregate-expression-on-table)|Custom aggregated SQL expression within range|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_sql_aggregate_expression_on_table`</span>](./sql-aggregate-expression-on-table.md#daily-partition-sql-aggregate-expression-on-table)|Custom aggregated SQL expression within range|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_sql_aggregate_expression_on_table`</span>](./sql-aggregate-expression-on-table.md#monthly-partition-sql-aggregate-expression-on-table)|Custom aggregated SQL expression within range|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |



### [sql invalid record count on table](./sql-invalid-record-count-on-table.md)
A table-level check that uses a custom SQL query that return invalid values from column.
 Use the {table} token to reference the tested table. This data quality check can be used to compare columns on the same table.
 For example, when this check is applied on a *age* column, the condition can find invalid records in which the *age* is lower than 18 using an SQL query: &#x60;SELECT age FROM {table} WHERE age &lt; 18&#x60;.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sql_invalid_record_count_on_table`</span>](./sql-invalid-record-count-on-table.md#profile-sql-invalid-record-count-on-table)|Custom SELECT SQL that returns invalid records|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Runs a custom query that retrieves invalid records found in a table and returns the number of them, and raises an issue if too many failures were detected. This check is used for setting testing queries or ready queries used by users in their own systems (legacy SQL queries). For example, when this check is applied on a *age* column, the condition can find invalid records in which the *age* is lower than 18 using an SQL query: &#x60;SELECT age FROM {table} WHERE age &lt; 18&#x60;.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_sql_invalid_record_count_on_table`</span>](./sql-invalid-record-count-on-table.md#daily-sql-invalid-record-count-on-table)|Custom SELECT SQL that returns invalid records|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Runs a custom query that retrieves invalid records found in a table and returns the number of them, and raises an issue if too many failures were detected. This check is used for setting testing queries or ready queries used by users in their own systems (legacy SQL queries). For example, when this check is applied on a *age* column, the condition can find invalid records in which the *age* is lower than 18 using an SQL query: &#x60;SELECT age FROM {table} WHERE age &lt; 18&#x60;.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_sql_invalid_record_count_on_table`</span>](./sql-invalid-record-count-on-table.md#monthly-sql-invalid-record-count-on-table)|Custom SELECT SQL that returns invalid records|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Runs a custom query that retrieves invalid records found in a table and returns the number of them, and raises an issue if too many failures were detected. This check is used for setting testing queries or ready queries used by users in their own systems (legacy SQL queries). For example, when this check is applied on a *age* column, the condition can find invalid records in which the *age* is lower than 18 using an SQL query: &#x60;SELECT age FROM {table} WHERE age &lt; 18&#x60;.|:material-check-bold:|



### [import custom result on table](./import-custom-result-on-table.md)
A table-level check that uses a custom SQL SELECT statement to retrieve a result of running a custom data quality check that was hardcoded
 in the data pipeline, and the result was stored in a separate table. The SQL query that is configured in this external data quality results importer must be
 a complete SELECT statement that queries a dedicated table (created by the data engineers) that stores the results of custom data quality checks.
 The SQL query must return a *severity* column with values: 0 - data quality check passed, 1 - warning issue, 2 - error severity issue, 3 - fatal severity issue.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_import_custom_result_on_table`</span>](./import-custom-result-on-table.md#profile-import-custom-result-on-table)|Import custom data quality results on table|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.| |
|[<span class="no-wrap-code">`daily_import_custom_result_on_table`</span>](./import-custom-result-on-table.md#daily-import-custom-result-on-table)|Import custom data quality results on table|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.| |
|[<span class="no-wrap-code">`monthly_import_custom_result_on_table`</span>](./import-custom-result-on-table.md#monthly-import-custom-result-on-table)|Import custom data quality results on table|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.| |







