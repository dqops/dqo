# table level custom sql data quality checks

This is a list of custom_sql table data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **custom_sql**
Validate data against user-defined SQL queries at the table level. Checks in this group allow for validation that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sql_condition_failed_on_table`</span>](./sql-condition-failed-on-table.md#profile-sql-condition-failed-on-table)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_sql_condition_failed_on_table`</span>](./sql-condition-failed-on-table.md#daily-sql-condition-failed-on-table)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent count of failed rows for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_sql_condition_failed_on_table`</span>](./sql-condition-failed-on-table.md#monthly-sql-condition-failed-on-table)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent count of failed rows for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_sql_condition_failed_on_table`</span>](./sql-condition-failed-on-table.md#daily-partition-sql-condition-failed-on-table)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_sql_condition_failed_on_table`</span>](./sql-condition-failed-on-table.md#monthly-partition-sql-condition-failed-on-table)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sql_condition_passed_percent_on_table`</span>](./sql-condition-passed-percent-on-table.md#profile-sql-condition-passed-percent-on-table)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;.| |
|[<span class="no-wrap-code">`daily_sql_condition_passed_percent_on_table`</span>](./sql-condition-passed-percent-on-table.md#daily-sql-condition-passed-percent-on-table)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent captured percentage for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_sql_condition_passed_percent_on_table`</span>](./sql-condition-passed-percent-on-table.md#monthly-sql-condition-passed-percent-on-table)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_sql_condition_passed_percent_on_table`</span>](./sql-condition-passed-percent-on-table.md#daily-partition-sql-condition-passed-percent-on-table)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_sql_condition_passed_percent_on_table`</span>](./sql-condition-passed-percent-on-table.md#monthly-partition-sql-condition-passed-percent-on-table)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each monthly partition.| |



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sql_aggregate_expression_on_table`</span>](./sql-aggregate-expression-on-table.md#profile-sql-aggregate-expression-on-table)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_sql_aggregate_expression_on_table`</span>](./sql-aggregate-expression-on-table.md#daily-sql-aggregate-expression-on-table)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_sql_aggregate_expression_on_table`</span>](./sql-aggregate-expression-on-table.md#monthly-sql-aggregate-expression-on-table)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_sql_aggregate_expression_on_table`</span>](./sql-aggregate-expression-on-table.md#daily-partition-sql-aggregate-expression-on-table)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_sql_aggregate_expression_on_table`</span>](./sql-aggregate-expression-on-table.md#monthly-partition-sql-aggregate-expression-on-table)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |







