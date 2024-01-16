# column level custom sql data quality checks

This is a list of custom_sql column data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **custom_sql**
Validate data against user-defined SQL queries at the column level. Checks in this group allows to validate that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sql_condition_failed_on_column`</span>](./sql-condition-failed-on-column.md#profile-sql-condition-failed-on-column)|profiling|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; col_tax&#x60;.|*standard*|
|[<span class="no-wrap-code">`daily_sql_condition_failed_on_column`</span>](./sql-condition-failed-on-column.md#daily-sql-condition-failed-on-column)|monitoring|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; col_tax&#x60;. Stores the most recent captured count of failed rows for each day when the data quality check was evaluated.|*standard*|
|[<span class="no-wrap-code">`monthly_sql_condition_failed_on_column`</span>](./sql-condition-failed-on-column.md#monthly-sql-condition-failed-on-column)|monitoring|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores the most recent captured count of failed rows for each month when the data quality check was evaluated.|*standard*|
|[<span class="no-wrap-code">`daily_partition_sql_condition_failed_on_column`</span>](./sql-condition-failed-on-column.md#daily-partition-sql-condition-failed-on-column)|partitioned|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.|*standard*|
|[<span class="no-wrap-code">`monthly_partition_sql_condition_failed_on_column`</span>](./sql-condition-failed-on-column.md#monthly-partition-sql-condition-failed-on-column)|partitioned|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each monthly partition.|*standard*|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sql_condition_passed_percent_on_column`</span>](./sql-condition-passed-percent-on-column.md#profile-sql-condition-passed-percent-on-column)|profiling|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;.|*advanced*|
|[<span class="no-wrap-code">`daily_sql_condition_passed_percent_on_column`</span>](./sql-condition-passed-percent-on-column.md#daily-sql-condition-passed-percent-on-column)|monitoring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores the most recent captured value for each day when the data quality check was evaluated.|*advanced*|
|[<span class="no-wrap-code">`monthly_sql_condition_passed_percent_on_column`</span>](./sql-condition-passed-percent-on-column.md#monthly-sql-condition-passed-percent-on-column)|monitoring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;.  Stores the most recent check result for each month when the data quality check was evaluated.|*advanced*|
|[<span class="no-wrap-code">`daily_partition_sql_condition_passed_percent_on_column`</span>](./sql-condition-passed-percent-on-column.md#daily-partition-sql-condition-passed-percent-on-column)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.|*advanced*|
|[<span class="no-wrap-code">`monthly_partition_sql_condition_passed_percent_on_column`</span>](./sql-condition-passed-percent-on-column.md#monthly-partition-sql-condition-passed-percent-on-column)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each monthly partition.|*advanced*|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sql_aggregate_expression_on_column`</span>](./sql-aggregate-expression-on-column.md#profile-sql-aggregate-expression-on-column)|profiling|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range.|*advanced*|
|[<span class="no-wrap-code">`daily_sql_aggregate_expression_on_column`</span>](./sql-aggregate-expression-on-column.md#daily-sql-aggregate-expression-on-column)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.|*advanced*|
|[<span class="no-wrap-code">`monthly_sql_aggregate_expression_on_column`</span>](./sql-aggregate-expression-on-column.md#monthly-sql-aggregate-expression-on-column)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent check result for each month when the data quality check was evaluated.|*advanced*|
|[<span class="no-wrap-code">`daily_partition_sql_aggregate_expression_on_column`</span>](./sql-aggregate-expression-on-column.md#daily-partition-sql-aggregate-expression-on-column)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each daily partition.|*advanced*|
|[<span class="no-wrap-code">`monthly_partition_sql_aggregate_expression_on_column`</span>](./sql-aggregate-expression-on-column.md#monthly-partition-sql-aggregate-expression-on-column)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each monthly partition.|*advanced*|







