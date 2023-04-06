# Checks

**This is a list of the checks in DQO broken down by category and a brief description of what they do.**

## **Table checks**


###availability  
Checks whether the table is accessible and available for use.

| Check name | Check type | Description |
|------------|------------|-------------|
|[table_availability](./table/availability/table-availability/#table-availability)|profiling|Verifies that the number of rows in a table does not exceed the minimum accepted count.|
|[daily_table_availability](./table/availability/table-availability/#daily-table-availability)|recurring|Verifies availability on table in database using simple row count|
|[monthly_table_availability](./table/availability/table-availability/#monthly-table-availability)|recurring|Verifies availability on table in database using simple row count|





###sql  
Validate data against user-defined SQL queries at the table level. Checks in this group allow for validation that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

| Check name | Check type | Description |
|------------|------------|-------------|
|[sql_condition_passed_percent_on_table](./table/sql/sql-condition-passed-percent-on-table/#sql-condition-passed-percent-on-table)|profiling|Verifies that a set percentage of rows passed a custom SQL condition (expression).|
|[daily_sql_condition_passed_percent_on_table](./table/sql/sql-condition-passed-percent-on-table/#daily-sql-condition-passed-percent-on-table)|recurring|Verifies that a set percentage of rows passed a custom SQL condition (expression).|
|[monthly_sql_condition_passed_percent_on_table](./table/sql/sql-condition-passed-percent-on-table/#monthly-sql-condition-passed-percent-on-table)|recurring|Verifies that a set percentage of rows passed a custom SQL condition (expression).|
|[daily_partition_sql_condition_passed_percent_on_table](./table/sql/sql-condition-passed-percent-on-table/#daily-partition-sql-condition-passed-percent-on-table)|partitioned|Verifies that a set percentage of rows passed a custom SQL condition (expression).|
|[monthly_partition_sql_condition_passed_percent_on_table](./table/sql/sql-condition-passed-percent-on-table/#monthly-partition-sql-condition-passed-percent-on-table)|partitioned|Verifies that a set percentage of rows passed a custom SQL condition (expression).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sql_condition_failed_count_on_table](./table/sql/sql-condition-failed-count-on-table/#sql-condition-failed-count-on-table)|profiling|Verifies that a set number of rows failed a custom SQL condition (expression).|
|[daily_sql_condition_failed_count_on_table](./table/sql/sql-condition-failed-count-on-table/#daily-sql-condition-failed-count-on-table)|recurring|Verifies that a set number of rows failed a custom SQL condition (expression).|
|[monthly_sql_condition_failed_count_on_table](./table/sql/sql-condition-failed-count-on-table/#monthly-sql-condition-failed-count-on-table)|recurring|Verifies that a set number of rows failed a custom SQL condition (expression).|
|[daily_partition_sql_condition_failed_count_on_table](./table/sql/sql-condition-failed-count-on-table/#daily-partition-sql-condition-failed-count-on-table)|partitioned|Verifies that a set number of rows failed a custom SQL condition (expression).|
|[monthly_partition_sql_condition_failed_count_on_table](./table/sql/sql-condition-failed-count-on-table/#monthly-partition-sql-condition-failed-count-on-table)|partitioned|Verifies that a set number of rows failed a custom SQL condition (expression).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sql_aggregate_expr_table](./table/sql/sql-aggregate-expr-table/#sql-aggregate-expr-table)|profiling|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|
|[daily_sql_aggregate_expr_table](./table/sql/sql-aggregate-expr-table/#daily-sql-aggregate-expr-table)|recurring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|
|[monthly_sql_aggregate_expr_table](./table/sql/sql-aggregate-expr-table/#monthly-sql-aggregate-expr-table)|recurring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) returns a given minimum accepted value.|
|[daily_partition_sql_aggregate_expr_table](./table/sql/sql-aggregate-expr-table/#daily-partition-sql-aggregate-expr-table)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|
|[monthly_partition_sql_aggregate_expr_table](./table/sql/sql-aggregate-expr-table/#monthly-partition-sql-aggregate-expr-table)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|





###standard  
Evaluates the overall quality of the table by verifying the number of rows.

| Check name | Check type | Description |
|------------|------------|-------------|
|[row_count](./table/standard/row-count/#row-count)|profiling|Verifies that the number of rows in a table does not exceed the minimum accepted count.|
|[daily_row_count](./table/standard/row-count/#daily-row-count)|recurring|Verifies that the number of rows in a table does not exceed the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_row_count](./table/standard/row-count/#monthly-row-count)|recurring|Verifies that the number of rows in a table does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_row_count](./table/standard/row-count/#daily-partition-row-count)|partitioned|Verifies that the number of rows in a table does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_min_row_count](./table/standard/row-count/#monthly-partition-min-row-count)|partitioned|Verifies that the number of rows in a table does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|





###timeliness  
Assesses the freshness and staleness of data, as well as data ingestion delay and reload lag for partitioned data.

| Check name | Check type | Description |
|------------|------------|-------------|
|[days_since_most_recent_event](./table/timeliness/days-since-most-recent-event/#days-since-most-recent-event)|profiling|Calculates the number of days since the most recent event timestamp (freshness)|
|[daily_days_since_most_recent_event](./table/timeliness/days-since-most-recent-event/#daily-days-since-most-recent-event)|recurring|Daily  calculating the number of days since the most recent event timestamp (freshness)|
|[monthly_days_since_most_recent_event](./table/timeliness/days-since-most-recent-event/#monthly-days-since-most-recent-event)|recurring|Monthly recurring calculating the number of days since the most recent event timestamp (freshness)|
|[daily_partition_days_since_most_recent_event](./table/timeliness/days-since-most-recent-event/#daily-partition-days-since-most-recent-event)|partitioned|Daily partition checkpoint calculating the number of days since the most recent event timestamp (freshness)|
|[monthly_partition_days_since_most_recent_event](./table/timeliness/days-since-most-recent-event/#monthly-partition-days-since-most-recent-event)|partitioned|Monthly partition checkpoint calculating the number of days since the most recent event (freshness)|


| Check name | Check type | Description |
|------------|------------|-------------|
|[data_ingestion_delay](./table/timeliness/data-ingestion-delay/#data-ingestion-delay)|profiling|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[daily_data_ingestion_delay](./table/timeliness/data-ingestion-delay/#daily-data-ingestion-delay)|recurring|Daily  calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[monthly_data_ingestion_delay](./table/timeliness/data-ingestion-delay/#monthly-data-ingestion-delay)|recurring|Monthly recurring calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[daily_partition_data_ingestion_delay](./table/timeliness/data-ingestion-delay/#daily-partition-data-ingestion-delay)|partitioned|Daily partition checkpoint calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[monthly_partition_data_ingestion_delay](./table/timeliness/data-ingestion-delay/#monthly-partition-data-ingestion-delay)|partitioned|Monthly partition checkpoint calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|


| Check name | Check type | Description |
|------------|------------|-------------|
|[days_since_most_recent_ingestion](./table/timeliness/days-since-most-recent-ingestion/#days-since-most-recent-ingestion)|profiling|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[daily_days_since_most_recent_ingestion](./table/timeliness/days-since-most-recent-ingestion/#daily-days-since-most-recent-ingestion)|recurring|Daily  calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[monthly_days_since_most_recent_ingestion](./table/timeliness/days-since-most-recent-ingestion/#monthly-days-since-most-recent-ingestion)|recurring|Monthly recurring calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[daily_partition_days_since_most_recent_ingestion](./table/timeliness/days-since-most-recent-ingestion/#daily-partition-days-since-most-recent-ingestion)|partitioned|Daily partition checkpoint calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[monthly_partition_days_since_most_recent_ingestion](./table/timeliness/days-since-most-recent-ingestion/#monthly-partition-days-since-most-recent-ingestion)|partitioned|Monthly partition checkpoint calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_reload_lag](./table/timeliness/daily-partition-reload-lag/#daily-partition-reload-lag)|partitioned|Daily partition checkpoint calculating the longest time a row waited to be load|
|[monthly_partition_reload_lag](./table/timeliness/daily-partition-reload-lag/#monthly-partition-reload-lag)|partitioned|Monthly partition checkpoint calculating the longest time a row waited to be load|






































## **Column checks**














###accuracy  


| Check name | Check type | Description |
|------------|------------|-------------|
|[total_sum_match_percent](./column/accuracy/total-sum-match-percent/#total-sum-match-percent)|profiling|Verifies that percentage of the difference in sum of a column in a table and sum of a column of another table does not exceed the set number.|
|[daily_total_sum_match_percent](./column/accuracy/total-sum-match-percent/#daily-total-sum-match-percent)|recurring|Verifies that the percentage of difference in sum of a column in a table and sum of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_total_sum_match_percent](./column/accuracy/total-sum-match-percent/#monthly-total-sum-match-percent)|recurring|Verifies that the percentage of difference in sum of a column in a table and sum of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[min_match_percent](./column/accuracy/min-match-percent/#min-match-percent)|profiling|Verifies that the percentage of difference in min of a column in a table and min of a column of another table does not exceed the set number.|
|[daily_min_match_percent](./column/accuracy/min-match-percent/#daily-min-match-percent)|recurring|Verifies that the percentage of difference in min of a column in a table and min of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_min_match_percent](./column/accuracy/min-match-percent/#monthly-min-match-percent)|recurring|Verifies that the percentage of difference in min of a column in a table and min of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[max_match_percent](./column/accuracy/max-match-percent/#max-match-percent)|profiling|Verifies that the percentage of difference in max of a column in a table and max of a column of another table does not exceed the set number.|
|[daily_max_match_percent](./column/accuracy/max-match-percent/#daily-max-match-percent)|recurring|Verifies that the percentage of difference in max of a column in a table and max of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_max_match_percent](./column/accuracy/max-match-percent/#monthly-max-match-percent)|recurring|Verifies that the percentage of difference in max of a column in a table and max of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[average_match_percent](./column/accuracy/average-match-percent/#average-match-percent)|profiling|Verifies that the percentage of difference in average of a column in a table and average of a column of another table does not exceed the set number.|
|[daily_average_match_percent](./column/accuracy/average-match-percent/#daily-average-match-percent)|recurring|Verifies that the percentage of difference in average of a column in a table and average of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_average_match_percent](./column/accuracy/average-match-percent/#monthly-average-match-percent)|recurring|Verifies that the percentage of difference in average of a column in a table and average of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|





###bool  
Calculates the percentage of data in a Boolean format.

| Check name | Check type | Description |
|------------|------------|-------------|
|[true_percent](./column/bool/true-percent/#true-percent)|profiling|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage.|
|[daily_true_percent](./column/bool/true-percent/#daily-true-percent)|recurring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_true_percent](./column/bool/true-percent/#monthly-true-percent)|recurring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_true_percent](./column/bool/true-percent/#daily-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_true_percent](./column/bool/true-percent/#monthly-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[false_percent](./column/bool/false-percent/#false-percent)|profiling|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage.|
|[daily_false_percent](./column/bool/false-percent/#daily-false-percent)|recurring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_false_percent](./column/bool/false-percent/#monthly-false-percent)|recurring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_false_percent](./column/bool/false-percent/#daily-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_false_percent](./column/bool/false-percent/#monthly-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





###consistency  


| Check name | Check type | Description |
|------------|------------|-------------|
|[date_match_format_percent](./column/consistency/date-match-format-percent/#date-match-format-percent)|profiling|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage.|
|[daily_date_match_format_percent](./column/consistency/date-match-format-percent/#daily-date-match-format-percent)|recurring|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily recurring.|
|[monthly_date_match_format_percent](./column/consistency/date-match-format-percent/#monthly-date-match-format-percent)|recurring|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly recurring.|
|[daily_partition_date_match_format_percent](./column/consistency/date-match-format-percent/#daily-partition-date-match-format-percent)|partitioned|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_date_match_format_percent](./column/consistency/date-match-format-percent/#monthly-partition-date-match-format-percent)|partitioned|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





###datetime  
Validates that the data in a date or time column is in the expected format and within predefined ranges.

| Check name | Check type | Description |
|------------|------------|-------------|
|[date_values_in_future_percent](./column/datetime/date-values-in-future-percent/#date-values-in-future-percent)|profiling|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage.|
|[daily_date_values_in_future_percent](./column/datetime/date-values-in-future-percent/#daily-date-values-in-future-percent)|recurring|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_date_values_in_future_percent](./column/datetime/date-values-in-future-percent/#monthly-date-values-in-future-percent)|recurring|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_date_values_in_future_percent](./column/datetime/date-values-in-future-percent/#daily-partition-date-values-in-future-percent)|partitioned|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_date_values_in_future_percent](./column/datetime/date-values-in-future-percent/#monthly-partition-date-values-in-future-percent)|partitioned|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[datetime_value_in_range_date_percent](./column/datetime/datetime-value-in-range-date-percent/#datetime-value-in-range-date-percent)|profiling|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage.|
|[daily_datetime_value_in_range_date_percent](./column/datetime/datetime-value-in-range-date-percent/#daily-datetime-value-in-range-date-percent)|recurring|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_datetime_value_in_range_date_percent](./column/datetime/datetime-value-in-range-date-percent/#monthly-datetime-value-in-range-date-percent)|recurring|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_datetime_value_in_range_date_percent](./column/datetime/datetime-value-in-range-date-percent/#daily-partition-datetime-value-in-range-date-percent)|partitioned|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_datetime_value_in_range_date_percent](./column/datetime/datetime-value-in-range-date-percent/#monthly-partition-datetime-value-in-range-date-percent)|partitioned|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





###integrity  
Checks the referential integrity of a column against a column in another table.

| Check name | Check type | Description |
|------------|------------|-------------|
|[foreign_key_not_match_count](./column/integrity/foreign-key-not-match-count/#foreign-key-not-match-count)|profiling|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count.|
|[daily_foreign_key_not_match_count](./column/integrity/foreign-key-not-match-count/#daily-foreign-key-not-match-count)|recurring|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_foreign_key_not_match_count](./column/integrity/foreign-key-not-match-count/#monthly-foreign-key-not-match-count)|recurring|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[daily_partition_foreign_key_not_match_count](./column/integrity/foreign-key-not-match-count/#daily-partition-foreign-key-not-match-count)|partitioned|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_foreign_key_not_match_count](./column/integrity/foreign-key-not-match-count/#monthly-partition-foreign-key-not-match-count)|partitioned|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[foreign_key_match_percent](./column/integrity/foreign-key-match-percent/#foreign-key-match-percent)|profiling|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count.|
|[daily_foreign_key_match_percent](./column/integrity/foreign-key-match-percent/#daily-foreign-key-match-percent)|recurring|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_foreign_key_match_percent](./column/integrity/foreign-key-match-percent/#monthly-foreign-key-match-percent)|recurring|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[daily_partition_foreign_key_match_percent](./column/integrity/foreign-key-match-percent/#daily-partition-foreign-key-match-percent)|partitioned|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_foreign_key_match_percent](./column/integrity/foreign-key-match-percent/#monthly-partition-foreign-key-match-percent)|partitioned|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|





###nulls  
Checks for the presence of null or missing values in a column.

| Check name | Check type | Description |
|------------|------------|-------------|
|[nulls_count](./column/nulls/nulls-count/#nulls-count)|profiling|Verifies that the number of null values in a column does not exceed the maximum accepted count.|
|[daily_nulls_count](./column/nulls/nulls-count/#daily-nulls-count)|recurring|Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_nulls_count](./column/nulls/nulls-count/#monthly-nulls-count)|recurring|Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_nulls_count](./column/nulls/nulls-count/#daily-partition-nulls-count)|partitioned|Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_nulls_count](./column/nulls/nulls-count/#monthly-partition-nulls-count)|partitioned|Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[nulls_percent](./column/nulls/nulls-percent/#nulls-percent)|profiling|Verifies that the percent of null values in a column does not exceed the maximum accepted percentage.|
|[daily_nulls_percent](./column/nulls/nulls-percent/#daily-nulls-percent)|recurring|Verifies that the percentage of nulls in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_nulls_percent](./column/nulls/nulls-percent/#monthly-nulls-percent)|recurring|Verifies that the percentage of null values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_nulls_percent](./column/nulls/nulls-percent/#daily-partition-nulls-percent)|partitioned|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_nulls_percent](./column/nulls/nulls-percent/#monthly-partition-nulls-percent)|partitioned|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[not_nulls_count](./column/nulls/not-nulls-count/#not-nulls-count)|profiling|Verifies that the number of not null values in a column does not exceed the maximum accepted count.|
|[daily_not_nulls_count](./column/nulls/not-nulls-count/#daily-not-nulls-count)|recurring|Verifies that the number of not null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_not_nulls_count](./column/nulls/not-nulls-count/#monthly-not-nulls-count)|recurring|Verifies that the number of not null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_not_nulls_count](./column/nulls/not-nulls-count/#daily-partition-not-nulls-count)|partitioned|Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_not_nulls_count](./column/nulls/not-nulls-count/#monthly-partition-not-nulls-count)|partitioned|Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[not_nulls_percent](./column/nulls/not-nulls-percent/#not-nulls-percent)|profiling|Verifies that the percent of not null values in a column does not exceed the maximum accepted percentage.|
|[daily_not_nulls_percent](./column/nulls/not-nulls-percent/#daily-not-nulls-percent)|recurring|Verifies that the percentage of not nulls in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_not_nulls_percent](./column/nulls/not-nulls-percent/#monthly-not-nulls-percent)|recurring|Verifies that the percentage of not nulls in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_not_nulls_percent](./column/nulls/not-nulls-percent/#daily-partition-not-nulls-percent)|partitioned|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_not_nulls_percent](./column/nulls/not-nulls-percent/#monthly-partition-not-nulls-percent)|partitioned|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





###numeric  
Validates that the data in a numeric column is in the expected format or within predefined ranges.

| Check name | Check type | Description |
|------------|------------|-------------|
|[negative_count](./column/numeric/negative-count/#negative-count)|profiling|Verifies that the number of negative values in a column does not exceed the maximum accepted count.|
|[daily_negative_count](./column/numeric/negative-count/#daily-negative-count)|recurring|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_negative_count](./column/numeric/negative-count/#monthly-negative-count)|recurring|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_negative_count](./column/numeric/negative-count/#daily-partition-negative-count)|partitioned|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_negative_count](./column/numeric/negative-count/#monthly-partition-negative-count)|partitioned|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[negative_percent](./column/numeric/negative-percent/#negative-percent)|profiling|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.|
|[daily_negative_percent](./column/numeric/negative-percent/#daily-negative-percent)|recurring|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_negative_percent](./column/numeric/negative-percent/#monthly-negative-percent)|recurring|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_negative_percent](./column/numeric/negative-percent/#daily-partition-negative-percent)|partitioned|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_negative_percent](./column/numeric/negative-percent/#monthly-partition-negative-percent)|partitioned|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[non_negative_count](./column/numeric/non-negative-count/#non-negative-count)|profiling|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count.|
|[daily_non_negative_count](./column/numeric/non-negative-count/#daily-non-negative-count)|recurring|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_non_negative_count](./column/numeric/non-negative-count/#monthly-non-negative-count)|recurring|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_non_negative_count](./column/numeric/non-negative-count/#daily-partition-non-negative-count)|partitioned|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_non_negative_count](./column/numeric/non-negative-count/#monthly-partition-non-negative-count)|partitioned|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[non_negative_percent](./column/numeric/non-negative-percent/#non-negative-percent)|profiling|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage.|
|[daily_non_negative_percent](./column/numeric/non-negative-percent/#daily-non-negative-percent)|recurring|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_non_negative_percent](./column/numeric/non-negative-percent/#monthly-non-negative-percent)|recurring|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_non_negative_percent](./column/numeric/non-negative-percent/#daily-partition-non-negative-percent)|partitioned|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_non_negative_percent](./column/numeric/non-negative-percent/#monthly-partition-non-negative-percent)|partitioned|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[numbers_in_set_count](./column/numeric/numbers-in-set-count/#numbers-in-set-count)|profiling|Verifies that the number of numbers from set in a column does not exceed the minimum accepted count.|
|[daily_numbers_in_set_count](./column/numeric/numbers-in-set-count/#daily-numbers-in-set-count)|recurring|Verifies that the number of Numbers from set in a column does not exceed the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_numbers_in_set_count](./column/numeric/numbers-in-set-count/#monthly-numbers-in-set-count)|recurring|Verifies that the number of Numbers from set in a column does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_numbers_in_set_count](./column/numeric/numbers-in-set-count/#daily-partition-numbers-in-set-count)|partitioned|Verifies that the number of Numbers from set in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_numbers_in_set_count](./column/numeric/numbers-in-set-count/#monthly-partition-numbers-in-set-count)|partitioned|Verifies that the number of Numbers from set in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[numbers_in_set_percent](./column/numeric/numbers-in-set-percent/#numbers-in-set-percent)|profiling|Verifies that the percentage of numbers from set in a column does not exceed the minimum accepted percentage.|
|[daily_numbers_in_set_percent](./column/numeric/numbers-in-set-percent/#daily-numbers-in-set-percent)|recurring|Verifies that the percentage of Numbers from set in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_numbers_in_set_percent](./column/numeric/numbers-in-set-percent/#monthly-numbers-in-set-percent)|recurring|Verifies that the percentage of Numbers from set in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_numbers_in_set_percent](./column/numeric/numbers-in-set-percent/#daily-partition-numbers-in-set-percent)|partitioned|Verifies that the percentage of Numbers from set in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_numbers_in_set_percent](./column/numeric/numbers-in-set-percent/#monthly-partition-numbers-in-set-percent)|partitioned|Verifies that the percentage of Numbers from set in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[values_in_range_numeric_percent](./column/numeric/values-in-range-numeric-percent/#values-in-range-numeric-percent)|profiling|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|
|[daily_values_in_range_numeric_percent](./column/numeric/values-in-range-numeric-percent/#daily-values-in-range-numeric-percent)|recurring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_values_in_range_numeric_percent](./column/numeric/values-in-range-numeric-percent/#monthly-values-in-range-numeric-percent)|recurring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_values_in_range_numeric_percent](./column/numeric/values-in-range-numeric-percent/#daily-partition-values-in-range-numeric-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_values_in_range_numeric_percent](./column/numeric/values-in-range-numeric-percent/#monthly-partition-values-in-range-numeric-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[values_in_range_integers_percent](./column/numeric/values-in-range-integers-percent/#values-in-range-integers-percent)|profiling|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|
|[daily_values_in_range_integers_percent](./column/numeric/values-in-range-integers-percent/#daily-values-in-range-integers-percent)|recurring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_values_in_range_integers_percent](./column/numeric/values-in-range-integers-percent/#monthly-values-in-range-integers-percent)|recurring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_values_in_range_integers_percent](./column/numeric/values-in-range-integers-percent/#daily-partition-values-in-range-integers-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_values_in_range_integers_percent](./column/numeric/values-in-range-integers-percent/#monthly-partition-values-in-range-integers-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[value_below_min_value_count](./column/numeric/value-below-min-value-count/#value-below-min-value-count)|profiling|The check counts those values with value below the one provided by the user in a column.|
|[daily_value_below_min_value_count](./column/numeric/value-below-min-value-count/#daily-value-below-min-value-count)|recurring|The check counts those values with value below the one provided by the user in a column. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_value_below_min_value_count](./column/numeric/value-below-min-value-count/#monthly-value-below-min-value-count)|recurring|The check counts those values with value below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_below_min_value_count](./column/numeric/value-below-min-value-count/#daily-partition-value-below-min-value-count)|partitioned|The check counts those values with value below the one provided by the user in a column. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_below_min_value_count](./column/numeric/value-below-min-value-count/#monthly-partition-value-below-min-value-count)|partitioned|The check counts those values with value below the one provided by the user in a column. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[value_below_min_value_percent](./column/numeric/value-below-min-value-percent/#value-below-min-value-percent)|profiling|The check percentage of those values with value below the one provided by the user in a column.|
|[daily_value_below_min_value_percent](./column/numeric/value-below-min-value-percent/#daily-value-below-min-value-percent)|recurring|The check percentage of those values with value below the one provided by the user in a column. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_value_below_min_value_percent](./column/numeric/value-below-min-value-percent/#monthly-value-below-min-value-percent)|recurring|The check percentage of those values with value below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_below_min_value_percent](./column/numeric/value-below-min-value-percent/#daily-partition-value-below-min-value-percent)|partitioned|The check percentage of those values with value below the one provided by the user in a column. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_below_min_value_percent](./column/numeric/value-below-min-value-percent/#monthly-partition-value-below-min-value-percent)|partitioned|The check percentage of those values with value below the one provided by the user in a column. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[value_above_max_value_count](./column/numeric/value-above-max-value-count/#value-above-max-value-count)|profiling|The check counts those values with value above the one provided by the user in a column.|
|[daily_value_above_max_value_count](./column/numeric/value-above-max-value-count/#daily-value-above-max-value-count)|recurring|The check counts those values with value above the one provided by the user in a column. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_value_above_max_value_count](./column/numeric/value-above-max-value-count/#monthly-value-above-max-value-count)|recurring|The check counts those values with value above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_above_max_value_count](./column/numeric/value-above-max-value-count/#daily-partition-value-above-max-value-count)|partitioned|The check counts those values with value above the one provided by the user in a column. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_above_max_value_count](./column/numeric/value-above-max-value-count/#monthly-partition-value-above-max-value-count)|partitioned|The check counts those values with value above the one provided by the user in a column. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[value_above_max_value_percent](./column/numeric/value-above-max-value-percent/#value-above-max-value-percent)|profiling|The check percentage of those values with value above the one provided by the user in a column.|
|[daily_value_above_max_value_percent](./column/numeric/value-above-max-value-percent/#daily-value-above-max-value-percent)|recurring|The check percentage of those values with value below the one provided by the user in a column. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_value_above_max_value_percent](./column/numeric/value-above-max-value-percent/#monthly-value-above-max-value-percent)|recurring|The check percentage of those values with value below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_above_max_value_percent](./column/numeric/value-above-max-value-percent/#daily-partition-value-above-max-value-percent)|partitioned|The check percentage of those values with value above the one provided by the user in a column. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_above_max_value_percent](./column/numeric/value-above-max-value-percent/#monthly-partition-value-above-max-value-percent)|partitioned|The check percentage of those values with value above the one provided by the user in a column. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[max_in_range](./column/numeric/max-in-range/#max-in-range)|profiling|Verifies that the maximal value in a column is not outside the set range.|
|[daily_max_in_range](./column/numeric/max-in-range/#daily-max-in-range)|recurring|Verifies that the maximal value in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_max_in_range](./column/numeric/max-in-range/#monthly-max-in-range)|recurring|Verifies that the maximal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_max_in_range](./column/numeric/max-in-range/#daily-partition-max-in-range)|partitioned|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_max_in_range](./column/numeric/max-in-range/#monthly-partition-max-in-range)|partitioned|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[min_in_range](./column/numeric/min-in-range/#min-in-range)|profiling|Verifies that the minimal value in a column is not outside the set range.|
|[daily_min_in_range](./column/numeric/min-in-range/#daily-min-in-range)|recurring|Verifies that the minimal value in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_min_in_range](./column/numeric/min-in-range/#monthly-min-in-range)|recurring|Verifies that the minimal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_min_in_range](./column/numeric/min-in-range/#daily-partition-min-in-range)|partitioned|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_min_in_range](./column/numeric/min-in-range/#monthly-partition-min-in-range)|partitioned|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[mean_in_range](./column/numeric/mean-in-range/#mean-in-range)|profiling|Verifies that the average (mean) of all values in a column is not outside the set range.|
|[daily_mean_in_range](./column/numeric/mean-in-range/#daily-mean-in-range)|recurring|Verifies that the average (mean) of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_mean_in_range](./column/numeric/mean-in-range/#monthly-mean-in-range)|recurring|Verifies that the average (mean) of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_mean_in_range](./column/numeric/mean-in-range/#daily-partition-mean-in-range)|partitioned|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_mean_in_range](./column/numeric/mean-in-range/#monthly-partition-mean-in-range)|partitioned|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[percentile_in_range](./column/numeric/percentile-in-range/#percentile-in-range)|profiling|Verifies that the percentile of all values in a column is not outside the set range.|
|[median_in_range](./column/numeric/percentile-in-range/#median-in-range)|profiling|Verifies that the median of all values in a column is not outside the set range.|
|[percentile_10_in_range](./column/numeric/percentile-in-range/#percentile-10-in-range)|profiling|Verifies that the percentile 10 of all values in a column is not outside the set range.|
|[percentile_25_in_range](./column/numeric/percentile-in-range/#percentile-25-in-range)|profiling|Verifies that the percentile 25 of all values in a column is not outside the set range.|
|[percentile_75_in_range](./column/numeric/percentile-in-range/#percentile-75-in-range)|profiling|Verifies that the percentile 75 of all values in a column is not outside the set range.|
|[percentile_90_in_range](./column/numeric/percentile-in-range/#percentile-90-in-range)|profiling|Verifies that the percentile 90 of all values in a column is not outside the set range.|
|[daily_percentile_in_range](./column/numeric/percentile-in-range/#daily-percentile-in-range)|recurring|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|
|[daily_median_in_range](./column/numeric/percentile-in-range/#daily-median-in-range)|recurring|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_percentile_10_in_range](./column/numeric/percentile-in-range/#daily-percentile-10-in-range)|recurring|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_percentile_25_in_range](./column/numeric/percentile-in-range/#daily-percentile-25-in-range)|recurring|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_percentile_75_in_range](./column/numeric/percentile-in-range/#daily-percentile-75-in-range)|recurring|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_percentile_90_in_range](./column/numeric/percentile-in-range/#daily-percentile-90-in-range)|recurring|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[monthly_percentile_in_range](./column/numeric/percentile-in-range/#monthly-percentile-in-range)|recurring|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[monthly_median_in_range](./column/numeric/percentile-in-range/#monthly-median-in-range)|recurring|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[monthly_percentile_10_in_range](./column/numeric/percentile-in-range/#monthly-percentile-10-in-range)|recurring|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[monthly_percentile_25_in_range](./column/numeric/percentile-in-range/#monthly-percentile-25-in-range)|recurring|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[monthly_percentile_75_in_range](./column/numeric/percentile-in-range/#monthly-percentile-75-in-range)|recurring|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[monthly_percentile_90_in_range](./column/numeric/percentile-in-range/#monthly-percentile-90-in-range)|recurring|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_percentile_in_range](./column/numeric/percentile-in-range/#daily-partition-percentile-in-range)|partitioned|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[daily_partition_median_in_range](./column/numeric/percentile-in-range/#daily-partition-median-in-range)|partitioned|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[daily_partition_percentile_10_in_range](./column/numeric/percentile-in-range/#daily-partition-percentile-10-in-range)|partitioned|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[daily_partition_percentile_25_in_range](./column/numeric/percentile-in-range/#daily-partition-percentile-25-in-range)|partitioned|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[daily_partition_percentile_75_in_range](./column/numeric/percentile-in-range/#daily-partition-percentile-75-in-range)|partitioned|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[daily_partition_percentile_90_in_range](./column/numeric/percentile-in-range/#daily-partition-percentile-90-in-range)|partitioned|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_percentile_in_range](./column/numeric/percentile-in-range/#monthly-partition-percentile-in-range)|partitioned|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|
|[monthly_partition_median_in_range](./column/numeric/percentile-in-range/#monthly-partition-median-in-range)|partitioned|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|
|[monthly_partition_percentile_10_in_range](./column/numeric/percentile-in-range/#monthly-partition-percentile-10-in-range)|partitioned|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|
|[monthly_partition_percentile_25_in_range](./column/numeric/percentile-in-range/#monthly-partition-percentile-25-in-range)|partitioned|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|
|[monthly_partition_percentile_75_in_range](./column/numeric/percentile-in-range/#monthly-partition-percentile-75-in-range)|partitioned|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|
|[monthly_partition_percentile_90_in_range](./column/numeric/percentile-in-range/#monthly-partition-percentile-90-in-range)|partitioned|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sample_stddev_in_range](./column/numeric/sample-stddev-in-range/#sample-stddev-in-range)|profiling|Verifies that the sample standard deviation of all values in a column is not outside the set range.|
|[daily_sample_stddev_in_range](./column/numeric/sample-stddev-in-range/#daily-sample-stddev-in-range)|recurring|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_sample_stddev_in_range](./column/numeric/sample-stddev-in-range/#monthly-sample-stddev-in-range)|recurring|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sample_stddev_in_range](./column/numeric/sample-stddev-in-range/#daily-partition-sample-stddev-in-range)|partitioned|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sample_stddev_in_range](./column/numeric/sample-stddev-in-range/#monthly-partition-sample-stddev-in-range)|partitioned|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[population_stddev_in_range](./column/numeric/population-stddev-in-range/#population-stddev-in-range)|profiling|Verifies that the population standard deviation of all values in a column is not outside the set range.|
|[daily_population_stddev_in_range](./column/numeric/population-stddev-in-range/#daily-population-stddev-in-range)|recurring|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_population_stddev_in_range](./column/numeric/population-stddev-in-range/#monthly-population-stddev-in-range)|recurring|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_population_stddev_in_range](./column/numeric/population-stddev-in-range/#daily-partition-population-stddev-in-range)|partitioned|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_population_stddev_in_range](./column/numeric/population-stddev-in-range/#monthly-partition-population-stddev-in-range)|partitioned|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sample_variance_in_range](./column/numeric/sample-variance-in-range/#sample-variance-in-range)|profiling|Verifies that the sample variance of all values in a column is not outside the set range.|
|[daily_sample_variance_in_range](./column/numeric/sample-variance-in-range/#daily-sample-variance-in-range)|recurring|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_sample_variance_in_range](./column/numeric/sample-variance-in-range/#monthly-sample-variance-in-range)|recurring|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sample_variance_in_range](./column/numeric/sample-variance-in-range/#daily-partition-sample-variance-in-range)|partitioned|Verifies that the sample Variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sample_variance_in_range](./column/numeric/sample-variance-in-range/#monthly-partition-sample-variance-in-range)|partitioned|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[population_variance_in_range](./column/numeric/population-variance-in-range/#population-variance-in-range)|profiling|Verifies that the population variance of all values in a column is not outside the set range.|
|[daily_population_variance_in_range](./column/numeric/population-variance-in-range/#daily-population-variance-in-range)|recurring|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_population_variance_in_range](./column/numeric/population-variance-in-range/#monthly-population-variance-in-range)|recurring|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_population_variance_in_range](./column/numeric/population-variance-in-range/#daily-partition-population-variance-in-range)|partitioned|Verifies that the population Variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_population_variance_in_range](./column/numeric/population-variance-in-range/#monthly-partition-population-variance-in-range)|partitioned|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sum_in_range](./column/numeric/sum-in-range/#sum-in-range)|profiling|Verifies that the sum of all values in a column is not outside the set range.|
|[daily_sum_in_range](./column/numeric/sum-in-range/#daily-sum-in-range)|recurring|Verifies that the sum of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_sum_in_range](./column/numeric/sum-in-range/#monthly-sum-in-range)|recurring|Verifies that the sum of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sum_in_range](./column/numeric/sum-in-range/#daily-partition-sum-in-range)|partitioned|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sum_in_range](./column/numeric/sum-in-range/#monthly-partition-sum-in-range)|partitioned|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[invalid_latitude_count](./column/numeric/invalid-latitude-count/#invalid-latitude-count)|profiling|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count.|
|[daily_invalid_latitude_count](./column/numeric/invalid-latitude-count/#daily-invalid-latitude-count)|recurring|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_invalid_latitude_count](./column/numeric/invalid-latitude-count/#monthly-invalid-latitude-count)|recurring|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_invalid_latitude_count](./column/numeric/invalid-latitude-count/#daily-partition-invalid-latitude-count)|partitioned|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_invalid_latitude_count](./column/numeric/invalid-latitude-count/#monthly-partition-invalid-latitude-count)|partitioned|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[valid_latitude_percent](./column/numeric/valid-latitude-percent/#valid-latitude-percent)|profiling|Verifies that the percentage of valid latitude values in a column does not exceed the minimum accepted percentage.|
|[daily_valid_latitude_percent](./column/numeric/valid-latitude-percent/#daily-valid-latitude-percent)|recurring|Verifies that the percentage of valid latitude values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_valid_latitude_percent](./column/numeric/valid-latitude-percent/#monthly-valid-latitude-percent)|recurring|Verifies that the percentage of valid latitude values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_latitude_percent](./column/numeric/valid-latitude-percent/#daily-partition-valid-latitude-percent)|partitioned|Verifies that the percentage of valid latitude values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_latitude_percent](./column/numeric/valid-latitude-percent/#monthly-partition-valid-latitude-percent)|partitioned|Verifies that the percentage of valid latitude values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[invalid_longitude_count](./column/numeric/invalid-longitude-count/#invalid-longitude-count)|profiling|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count.|
|[daily_invalid_longitude_count](./column/numeric/invalid-longitude-count/#daily-invalid-longitude-count)|recurring|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_invalid_longitude_count](./column/numeric/invalid-longitude-count/#monthly-invalid-longitude-count)|recurring|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_invalid_longitude_count](./column/numeric/invalid-longitude-count/#daily-partition-invalid-longitude-count)|partitioned|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_invalid_longitude_count](./column/numeric/invalid-longitude-count/#monthly-partition-invalid-longitude-count)|partitioned|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[valid_longitude_percent](./column/numeric/valid-longitude-percent/#valid-longitude-percent)|profiling|Verifies that the percentage of valid longitude values in a column does not exceed the minimum accepted percentage.|
|[daily_valid_longitude_percent](./column/numeric/valid-longitude-percent/#daily-valid-longitude-percent)|recurring|Verifies that the percentage of valid longitude values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_valid_longitude_percent](./column/numeric/valid-longitude-percent/#monthly-valid-longitude-percent)|recurring|Verifies that the percentage of valid longitude values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_longitude_percent](./column/numeric/valid-longitude-percent/#daily-partition-valid-longitude-percent)|partitioned|Verifies that the percentage of valid longitude values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_longitude_percent](./column/numeric/valid-longitude-percent/#monthly-partition-valid-longitude-percent)|partitioned|Verifies that the percentage of valid longitude values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





###pii  
Checks for the presence of sensitive or personally identifiable information (PII) in a column such as email, phone, zip code, IP4 and IP6 addresses.

| Check name | Check type | Description |
|------------|------------|-------------|
|[valid_usa_phone_percent](./column/pii/valid-usa-phone-percent/#valid-usa-phone-percent)|profiling|Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage.|
|[daily_valid_usa_phone_percent](./column/pii/valid-usa-phone-percent/#daily-valid-usa-phone-percent)|recurring|Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_valid_usa_phone_percent](./column/pii/valid-usa-phone-percent/#monthly-valid-usa-phone-percent)|recurring|Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_usa_phone_percent](./column/pii/valid-usa-phone-percent/#daily-partition-valid-usa-phone-percent)|partitioned|Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_usa_phone_percent](./column/pii/valid-usa-phone-percent/#monthly-partition-valid-usa-phone-percent)|partitioned|Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[contains_usa_phone_percent](./column/pii/contains-usa-phone-percent/#contains-usa-phone-percent)|profiling|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage.|
|[daily_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent/#daily-contains-usa-phone-percent)|recurring|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent/#monthly-contains-usa-phone-percent)|recurring|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent/#daily-partition-contains-usa-phone-percent)|partitioned|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_usa_phone_percent](./column/pii/contains-usa-phone-percent/#monthly-partition-contains-usa-phone-percent)|partitioned|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[valid_usa_zipcode_percent](./column/pii/valid-usa-zipcode-percent/#valid-usa-zipcode-percent)|profiling|Verifies that the percentage of valid USA zip code in a column does not exceed the minimum accepted percentage.|
|[daily_valid_usa_zipcode_percent](./column/pii/valid-usa-zipcode-percent/#daily-valid-usa-zipcode-percent)|recurring|Verifies that the percentage of valid USA zip code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_valid_usa_zipcode_percent](./column/pii/valid-usa-zipcode-percent/#monthly-valid-usa-zipcode-percent)|recurring|Verifies that the percentage of valid USA zip code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_usa_zipcode_percent](./column/pii/valid-usa-zipcode-percent/#daily-partition-valid-usa-zipcode-percent)|partitioned|Verifies that the percentage of valid USA zip code in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_usa_zipcode_percent](./column/pii/valid-usa-zipcode-percent/#monthly-partition-valid-usa-zipcode-percent)|partitioned|Verifies that the percentage of valid USA zip code in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent/#contains-usa-zipcode-percent)|profiling|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.|
|[daily_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent/#daily-contains-usa-zipcode-percent)|recurring|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent/#monthly-contains-usa-zipcode-percent)|recurring|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent/#daily-partition-contains-usa-zipcode-percent)|partitioned|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_usa_zipcode_percent](./column/pii/contains-usa-zipcode-percent/#monthly-partition-contains-usa-zipcode-percent)|partitioned|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[valid_email_percent](./column/pii/valid-email-percent/#valid-email-percent)|profiling|Verifies that the percentage of valid emails in a column does not exceed the minimum accepted percentage.|
|[daily_valid_email_percent](./column/pii/valid-email-percent/#daily-valid-email-percent)|recurring|Verifies that the percentage of valid emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_valid_email_percent](./column/pii/valid-email-percent/#monthly-valid-email-percent)|recurring|Verifies that the percentage of valid emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_email_percent](./column/pii/valid-email-percent/#daily-partition-valid-email-percent)|partitioned|Verifies that the percentage of valid emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_email_percent](./column/pii/valid-email-percent/#monthly-partition-valid-email-percent)|partitioned|Verifies that the percentage of valid emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[contains_email_percent](./column/pii/contains-email-percent/#contains-email-percent)|profiling|Verifies that the percentage of rows that contains valid emails in a column does not exceed the minimum accepted percentage.|
|[daily_contains_email_percent](./column/pii/contains-email-percent/#daily-contains-email-percent)|recurring|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_contains_email_percent](./column/pii/contains-email-percent/#monthly-contains-email-percent)|recurring|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_email_percent](./column/pii/contains-email-percent/#daily-partition-contains-email-percent)|partitioned|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_email_percent](./column/pii/contains-email-percent/#monthly-partition-contains-email-percent)|partitioned|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[valid_ip4_address_percent](./column/pii/valid-ip4-address-percent/#valid-ip4-address-percent)|profiling|Verifies that the percentage of valid IP4 address in a column does not exceed the maximum accepted percentage.|
|[daily_valid_ip4_address_percent](./column/pii/valid-ip4-address-percent/#daily-valid-ip4-address-percent)|recurring|Verifies that the percentage of valid IP4 address in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_valid_ip4_address_percent](./column/pii/valid-ip4-address-percent/#monthly-valid-ip4-address-percent)|recurring|Verifies that the percentage of valid IP4 address in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_ip4_address_percent](./column/pii/valid-ip4-address-percent/#daily-partition-valid-ip4-address-percent)|partitioned|Verifies that the percentage of valid IP4 address in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_ip4_address_percent](./column/pii/valid-ip4-address-percent/#monthly-partition-valid-ip4-address-percent)|partitioned|Verifies that the percentage of valid IP4 address in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[contains_ip4_percent](./column/pii/contains-ip4-percent/#contains-ip4-percent)|profiling|Verifies that the percentage of rows that contains valid IP4 address in a column does not exceed the minimum accepted percentage.|
|[daily_contains_ip4_percent](./column/pii/contains-ip4-percent/#daily-contains-ip4-percent)|recurring|Verifies that the percentage of rows that contains IP4 address in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_contains_ip4_percent](./column/pii/contains-ip4-percent/#monthly-contains-ip4-percent)|recurring|Verifies that the percentage of rows that contains IP4 address in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_ip4_percent](./column/pii/contains-ip4-percent/#daily-partition-contains-ip4-percent)|partitioned|Verifies that the percentage of rows that contains IP4 address in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_ip4_percent](./column/pii/contains-ip4-percent/#monthly-partition-contains-ip4-percent)|partitioned|Verifies that the percentage of rows that contains IP4 address in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[valid_ip6_address_percent](./column/pii/valid-ip6-address-percent/#valid-ip6-address-percent)|profiling|Verifies that the percentage of valid IP6 address in a column does not exceed the maximum accepted percentage.|
|[daily_valid_ip6_address_percent](./column/pii/valid-ip6-address-percent/#daily-valid-ip6-address-percent)|recurring|Verifies that the percentage of valid IP6 address in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_valid_ip6_address_percent](./column/pii/valid-ip6-address-percent/#monthly-valid-ip6-address-percent)|recurring|Verifies that the percentage of valid IP6 address in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_ip6_address_percent](./column/pii/valid-ip6-address-percent/#daily-partition-valid-ip6-address-percent)|partitioned|Verifies that the percentage of valid IP6 address in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_ip6_address_percent](./column/pii/valid-ip6-address-percent/#monthly-partition-valid-ip6-address-percent)|partitioned|Verifies that the percentage of valid IP6 address in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[contains_ip6_percent](./column/pii/contains-ip6-percent/#contains-ip6-percent)|profiling|Verifies that the percentage of rows that contains valid IP6 address in a column does not exceed the minimum accepted percentage.|
|[daily_contains_ip6_percent](./column/pii/contains-ip6-percent/#daily-contains-ip6-percent)|recurring|Verifies that the percentage of rows that contains IP6 address in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_contains_ip6_percent](./column/pii/contains-ip6-percent/#monthly-contains-ip6-percent)|recurring|Verifies that the percentage of rows that contains IP6 address in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_ip6_percent](./column/pii/contains-ip6-percent/#daily-partition-contains-ip6-percent)|partitioned|Verifies that the percentage of rows that contains IP6 address in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_ip6_percent](./column/pii/contains-ip6-percent/#monthly-partition-contains-ip6-percent)|partitioned|Verifies that the percentage of rows that contains IP6 address in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





###sql  
Validate data against user-defined SQL queries at the column level. Checks in this group allows to validate that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

| Check name | Check type | Description |
|------------|------------|-------------|
|[sql_condition_passed_percent_on_column](./column/sql/sql-condition-passed-percent-on-column/#sql-condition-passed-percent-on-column)|profiling|Verifies that a minimum percentage of rows passed a custom SQL condition (expression).|
|[daily_sql_condition_passed_percent_on_column](./column/sql/sql-condition-passed-percent-on-column/#daily-sql-condition-passed-percent-on-column)|recurring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression).|
|[monthly_sql_condition_passed_percent_on_column](./column/sql/sql-condition-passed-percent-on-column/#monthly-sql-condition-passed-percent-on-column)|recurring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression).|
|[daily_partition_sql_condition_passed_percent_on_column](./column/sql/sql-condition-passed-percent-on-column/#daily-partition-sql-condition-passed-percent-on-column)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression).|
|[monthly_partition_sql_condition_passed_percent_on_column](./column/sql/sql-condition-passed-percent-on-column/#monthly-partition-sql-condition-passed-percent-on-column)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sql_condition_failed_count_on_column](./column/sql/sql-condition-failed-count-on-column/#sql-condition-failed-count-on-column)|profiling|Verifies that a maximum number of rows failed a custom SQL condition (expression).|
|[daily_sql_condition_failed_count_on_column](./column/sql/sql-condition-failed-count-on-column/#daily-sql-condition-failed-count-on-column)|recurring|Verifies that a maximum number of rows failed a custom SQL condition (expression).|
|[monthly_sql_condition_failed_count_on_column](./column/sql/sql-condition-failed-count-on-column/#monthly-sql-condition-failed-count-on-column)|recurring|Verifies that a maximum number of rows failed a custom SQL condition (expression).|
|[daily_partition_sql_condition_failed_count_on_column](./column/sql/sql-condition-failed-count-on-column/#daily-partition-sql-condition-failed-count-on-column)|partitioned|Verifies that a maximum number of rows failed a custom SQL condition (expression).|
|[monthly_partition_sql_condition_failed_count_on_column](./column/sql/sql-condition-failed-count-on-column/#monthly-partition-sql-condition-failed-count-on-column)|partitioned|Verifies that a maximum number of rows failed a custom SQL condition (expression).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[sql_aggregate_expr_column](./column/sql/sql-aggregate-expr-column/#sql-aggregate-expr-column)|profiling|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|
|[daily_sql_aggregate_expr_column](./column/sql/sql-aggregate-expr-column/#daily-sql-aggregate-expr-column)|recurring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|
|[monthly_sql_aggregate_expr_column](./column/sql/sql-aggregate-expr-column/#monthly-sql-aggregate-expr-column)|recurring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|
|[daily_partition_sql_aggregate_expr_column](./column/sql/sql-aggregate-expr-column/#daily-partition-sql-aggregate-expr-column)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|
|[monthly_partition_sql_aggregate_expr_column](./column/sql/sql-aggregate-expr-column/#monthly-partition-sql-aggregate-expr-column)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|





###strings  
Validates that the data in a string column match the expected format or pattern.

| Check name | Check type | Description |
|------------|------------|-------------|
|[string_max_length](./column/strings/string-max-length/#string-max-length)|profiling|Verifies that the length of string in a column does not exceed the maximum accepted length.|
|[daily_string_max_length](./column/strings/string-max-length/#daily-string-max-length)|recurring|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_max_length](./column/strings/string-max-length/#monthly-string-max-length)|recurring|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_max_length](./column/strings/string-max-length/#daily-partition-string-max-length)|partitioned|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_max_length](./column/strings/string-max-length/#monthly-partition-string-max-length)|partitioned|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_min_length](./column/strings/string-min-length/#string-min-length)|profiling|Verifies that the length of string in a column does not exceed the minimum accepted length.|
|[daily_string_min_length](./column/strings/string-min-length/#daily-string-min-length)|recurring|Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_min_length](./column/strings/string-min-length/#monthly-string-min-length)|recurring|Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_min_length](./column/strings/string-min-length/#daily-partition-string-min-length)|partitioned|Verifies that the length of string in a column does not exceed the minimum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_min_length](./column/strings/string-min-length/#monthly-partition-string-min-length)|partitioned|Verifies that the length of string in a column does not exceed the minimum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_mean_length](./column/strings/string-mean-length/#string-mean-length)|profiling|Verifies that the length of string in a column does not exceed the mean accepted length.|
|[daily_string_mean_length](./column/strings/string-mean-length/#daily-string-mean-length)|recurring|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_mean_length](./column/strings/string-mean-length/#monthly-string-mean-length)|recurring|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_mean_length](./column/strings/string-mean-length/#daily-partition-string-mean-length)|partitioned|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_mean_length](./column/strings/string-mean-length/#monthly-partition-string-mean-length)|partitioned|Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_length_below_min_length_count](./column/strings/string-length-below-min-length-count/#string-length-below-min-length-count)|profiling|The check counts those strings with length below the one provided by the user in a column.|
|[daily_string_length_below_min_length_count](./column/strings/string-length-below-min-length-count/#daily-string-length-below-min-length-count)|recurring|The check counts those strings with length below the one provided by the user in a column. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_length_below_min_length_count](./column/strings/string-length-below-min-length-count/#monthly-string-length-below-min-length-count)|recurring|The check counts those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_below_min_length_count](./column/strings/string-length-below-min-length-count/#daily-partition-string-length-below-min-length-count)|partitioned|The check counts those strings with length below the one provided by the user in a column. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_below_min_length_count](./column/strings/string-length-below-min-length-count/#monthly-partition-string-length-below-min-length-count)|partitioned|The check counts those strings with length below the one provided by the user in a column. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_length_below_min_length_percent](./column/strings/string-length-below-min-length-percent/#string-length-below-min-length-percent)|profiling|The check counts percentage of those strings with length below the one provided by the user in a column.|
|[daily_string_length_below_min_length_percent](./column/strings/string-length-below-min-length-percent/#daily-string-length-below-min-length-percent)|recurring|The check counts percentage of those strings with length below the one provided by the user in a column. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_length_below_min_length_percent](./column/strings/string-length-below-min-length-percent/#monthly-string-length-below-min-length-percent)|recurring|The check counts percentage of those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_below_min_length_percent](./column/strings/string-length-below-min-length-percent/#daily-partition-string-length-below-min-length-percent)|partitioned|The check counts percentage of those strings with length below the one provided by the user in a column. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_below_min_length_percent](./column/strings/string-length-below-min-length-percent/#monthly-partition-string-length-below-min-length-percent)|partitioned|The check counts percentage of those strings with length below the one provided by the user in a column. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_length_above_max_length_count](./column/strings/string-length-above-max-length-count/#string-length-above-max-length-count)|profiling|The check counts those strings with length above the one provided by the user in a column. |
|[daily_string_length_above_max_length_count](./column/strings/string-length-above-max-length-count/#daily-string-length-above-max-length-count)|recurring|The check counts those strings with length above the one provided by the user in a column. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_length_above_max_length_count](./column/strings/string-length-above-max-length-count/#monthly-string-length-above-max-length-count)|recurring|The check counts those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_above_max_length_count](./column/strings/string-length-above-max-length-count/#daily-partition-string-length-above-max-length-count)|partitioned|The check counts those strings with length above the one provided by the user in a column. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_above_max_length_count](./column/strings/string-length-above-max-length-count/#monthly-partition-string-length-above-max-length-count)|partitioned|The check counts those strings with length above the one provided by the user in a column. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_length_above_max_length_percent](./column/strings/string-length-above-max-length-percent/#string-length-above-max-length-percent)|profiling|The check counts percentage of those strings with length above the one provided by the user in a column. |
|[daily_string_length_above_max_length_percent](./column/strings/string-length-above-max-length-percent/#daily-string-length-above-max-length-percent)|recurring|The check counts percentage of those strings with length above the one provided by the user in a column. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_length_above_max_length_percent](./column/strings/string-length-above-max-length-percent/#monthly-string-length-above-max-length-percent)|recurring|The check counts percentage of those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_above_max_length_percent](./column/strings/string-length-above-max-length-percent/#daily-partition-string-length-above-max-length-percent)|partitioned|The check counts percentage of those strings with length above the one provided by the user in a column. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_above_max_length_percent](./column/strings/string-length-above-max-length-percent/#monthly-partition-string-length-above-max-length-percent)|partitioned|The check counts percentage of those strings with length above the one provided by the user in a column. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_length_in_range_percent](./column/strings/string-length-in-range-percent/#string-length-in-range-percent)|profiling|The check counts percentage of those strings with length in the range provided by the user in a column. |
|[daily_string_length_in_range_percent](./column/strings/string-length-in-range-percent/#daily-string-length-in-range-percent)|recurring|The check counts percentage of those strings with length in the range provided by the user in a column. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_length_in_range_percent](./column/strings/string-length-in-range-percent/#monthly-string-length-in-range-percent)|recurring|The check counts percentage of those strings with length in the range provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_in_range_percent](./column/strings/string-length-in-range-percent/#daily-partition-string-length-in-range-percent)|partitioned|The check counts percentage of those strings with length in the range provided by the user in a column. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_in_range_percent](./column/strings/string-length-in-range-percent/#monthly-partition-string-length-in-range-percent)|partitioned|The check counts percentage of those strings with length in the range provided by the user in a column. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_empty_count](./column/strings/string-empty-count/#string-empty-count)|profiling|Verifies that empty strings in a column does not exceed the maximum accepted count.|
|[daily_string_empty_count](./column/strings/string-empty-count/#daily-string-empty-count)|recurring|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_empty_count](./column/strings/string-empty-count/#monthly-string-empty-count)|recurring|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_empty_count](./column/strings/string-empty-count/#daily-partition-string-empty-count)|partitioned|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_empty_count](./column/strings/string-empty-count/#monthly-partition-string-empty-count)|partitioned|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_empty_percent](./column/strings/string-empty-percent/#string-empty-percent)|profiling|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.|
|[daily_string_empty_percent](./column/strings/string-empty-percent/#daily-string-empty-percent)|recurring|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_empty_percent](./column/strings/string-empty-percent/#monthly-string-empty-percent)|recurring|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_empty_percent](./column/strings/string-empty-percent/#daily-partition-string-empty-percent)|partitioned|Verifies that the percentage of string in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_empty_percent](./column/strings/string-empty-percent/#monthly-partition-string-empty-percent)|partitioned|Verifies that the percentage of string in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_whitespace_count](./column/strings/string-whitespace-count/#string-whitespace-count)|profiling|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count.|
|[daily_string_whitespace_count](./column/strings/string-whitespace-count/#daily-string-whitespace-count)|recurring|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_whitespace_count](./column/strings/string-whitespace-count/#monthly-string-whitespace-count)|recurring|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_whitespace_count](./column/strings/string-whitespace-count/#daily-partition-string-whitespace-count)|partitioned|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_whitespace_count](./column/strings/string-whitespace-count/#monthly-partition-string-whitespace-count)|partitioned|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_whitespace_percent](./column/strings/string-whitespace-percent/#string-whitespace-percent)|profiling|Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.|
|[daily_string_whitespace_percent](./column/strings/string-whitespace-percent/#daily-string-whitespace-percent)|recurring|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_whitespace_percent](./column/strings/string-whitespace-percent/#monthly-string-whitespace-percent)|recurring|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_whitespace_percent](./column/strings/string-whitespace-percent/#daily-partition-string-whitespace-percent)|partitioned|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_whitespace_percent](./column/strings/string-whitespace-percent/#monthly-partition-string-whitespace-percent)|partitioned|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_surrounded_by_whitespace_count](./column/strings/string-surrounded-by-whitespace-count/#string-surrounded-by-whitespace-count)|profiling|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count.|
|[daily_string_surrounded_by_whitespace_count](./column/strings/string-surrounded-by-whitespace-count/#daily-string-surrounded-by-whitespace-count)|recurring|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_surrounded_by_whitespace_count](./column/strings/string-surrounded-by-whitespace-count/#monthly-string-surrounded-by-whitespace-count)|recurring|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_surrounded_by_whitespace_count](./column/strings/string-surrounded-by-whitespace-count/#daily-partition-string-surrounded-by-whitespace-count)|partitioned|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_surrounded_by_whitespace_count](./column/strings/string-surrounded-by-whitespace-count/#monthly-partition-string-surrounded-by-whitespace-count)|partitioned|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_surrounded_by_whitespace_percent](./column/strings/string-surrounded-by-whitespace-percent/#string-surrounded-by-whitespace-percent)|profiling|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage.|
|[daily_string_surrounded_by_whitespace_percent](./column/strings/string-surrounded-by-whitespace-percent/#daily-string-surrounded-by-whitespace-percent)|recurring|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_surrounded_by_whitespace_percent](./column/strings/string-surrounded-by-whitespace-percent/#monthly-string-surrounded-by-whitespace-percent)|recurring|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_surrounded_by_whitespace_percent](./column/strings/string-surrounded-by-whitespace-percent/#daily-partition-string-surrounded-by-whitespace-percent)|partitioned|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_surrounded_by_whitespace_percent](./column/strings/string-surrounded-by-whitespace-percent/#monthly-partition-string-surrounded-by-whitespace-percent)|partitioned|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_null_placeholder_count](./column/strings/string-null-placeholder-count/#string-null-placeholder-count)|profiling|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count.|
|[daily_string_null_placeholder_count](./column/strings/string-null-placeholder-count/#daily-string-null-placeholder-count)|recurring|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_null_placeholder_count](./column/strings/string-null-placeholder-count/#monthly-string-null-placeholder-count)|recurring|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_null_placeholder_count](./column/strings/string-null-placeholder-count/#daily-partition-string-null-placeholder-count)|partitioned|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_null_placeholder_count](./column/strings/string-null-placeholder-count/#monthly-partition-string-null-placeholder-count)|partitioned|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_null_placeholder_percent](./column/strings/string-null-placeholder-percent/#string-null-placeholder-percent)|profiling|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.|
|[daily_string_null_placeholder_percent](./column/strings/string-null-placeholder-percent/#daily-string-null-placeholder-percent)|recurring|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_null_placeholder_percent](./column/strings/string-null-placeholder-percent/#monthly-string-null-placeholder-percent)|recurring|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_null_placeholder_percent](./column/strings/string-null-placeholder-percent/#daily-partition-string-null-placeholder-percent)|partitioned|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_null_placeholder_percent](./column/strings/string-null-placeholder-percent/#monthly-partition-string-null-placeholder-percent)|partitioned|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_boolean_placeholder_percent](./column/strings/string-boolean-placeholder-percent/#string-boolean-placeholder-percent)|profiling|Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage.|
|[daily_string_boolean_placeholder_percent](./column/strings/string-boolean-placeholder-percent/#daily-string-boolean-placeholder-percent)|recurring|Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_boolean_placeholder_percent](./column/strings/string-boolean-placeholder-percent/#monthly-string-boolean-placeholder-percent)|recurring|Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_boolean_placeholder_percent](./column/strings/string-boolean-placeholder-percent/#daily-partition-string-boolean-placeholder-percent)|partitioned|Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_boolean_placeholder_percent](./column/strings/string-boolean-placeholder-percent/#monthly-partition-string-boolean-placeholder-percent)|partitioned|Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_parsable_to_integer_percent](./column/strings/string-parsable-to-integer-percent/#string-parsable-to-integer-percent)|profiling|Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage.|
|[daily_string_parsable_to_integer_percent](./column/strings/string-parsable-to-integer-percent/#daily-string-parsable-to-integer-percent)|recurring|Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_parsable_to_integer_percent](./column/strings/string-parsable-to-integer-percent/#monthly-string-parsable-to-integer-percent)|recurring|Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_parsable_to_integer_percent](./column/strings/string-parsable-to-integer-percent/#daily-partition-string-parsable-to-integer-percent)|partitioned|Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_parsable_to_integer_percent](./column/strings/string-parsable-to-integer-percent/#monthly-partition-string-parsable-to-integer-percent)|partitioned|Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_parsable_to_float_percent](./column/strings/string-parsable-to-float-percent/#string-parsable-to-float-percent)|profiling|Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage.|
|[daily_string_parsable_to_float_percent](./column/strings/string-parsable-to-float-percent/#daily-string-parsable-to-float-percent)|recurring|Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_parsable_to_float_percent](./column/strings/string-parsable-to-float-percent/#monthly-string-parsable-to-float-percent)|recurring|Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_parsable_to_float_percent](./column/strings/string-parsable-to-float-percent/#daily-partition-string-parsable-to-float-percent)|partitioned|Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_parsable_to_float_percent](./column/strings/string-parsable-to-float-percent/#monthly-partition-string-parsable-to-float-percent)|partitioned|Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_in_set_count](./column/strings/string-in-set-count/#string-in-set-count)|profiling|Verifies that the number of strings from a set in a column does not exceed the minimum accepted count.|
|[daily_string_in_set_count](./column/strings/string-in-set-count/#daily-string-in-set-count)|recurring|Verifies that the number of strings from set in a column does not exceed the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_in_set_count](./column/strings/string-in-set-count/#monthly-string-in-set-count)|recurring|Verifies that the number of strings from set in a column does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_in_set_count](./column/strings/string-in-set-count/#daily-partition-string-in-set-count)|partitioned|Verifies that the number of strings from set in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_in_set_count](./column/strings/string-in-set-count/#monthly-partition-string-in-set-count)|partitioned|Verifies that the number of strings from set in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_in_set_percent](./column/strings/string-in-set-percent/#string-in-set-percent)|profiling|Verifies that the percentage of strings from a set in a column does not exceed the minimum accepted percentage.|
|[daily_string_in_set_percent](./column/strings/string-in-set-percent/#daily-string-in-set-percent)|recurring|Verifies that the percentage of strings from a set in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_in_set_percent](./column/strings/string-in-set-percent/#monthly-string-in-set-percent)|recurring|Verifies that the percentage of strings from set in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_in_set_percent](./column/strings/string-in-set-percent/#daily-partition-string-in-set-percent)|partitioned|Verifies that the percentage of strings from set in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_in_set_percent](./column/strings/string-in-set-percent/#monthly-partition-string-in-set-percent)|partitioned|Verifies that the percentage of strings from set in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_valid_dates_percent](./column/strings/string-valid-dates-percent/#string-valid-dates-percent)|profiling|Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage.|
|[daily_string_valid_dates_percent](./column/strings/string-valid-dates-percent/#daily-string-valid-dates-percent)|recurring|Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_valid_dates_percent](./column/strings/string-valid-dates-percent/#monthly-string-valid-dates-percent)|recurring|Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_valid_dates_percent](./column/strings/string-valid-dates-percent/#daily-partition-string-valid-dates-percent)|partitioned|Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_valid_dates_percent](./column/strings/string-valid-dates-percent/#monthly-partition-string-valid-dates-percent)|partitioned|Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_valid_country_code_percent](./column/strings/string-valid-country-code-percent/#string-valid-country-code-percent)|profiling|Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage.|
|[daily_string_valid_country_code_percent](./column/strings/string-valid-country-code-percent/#daily-string-valid-country-code-percent)|recurring|Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_valid_country_code_percent](./column/strings/string-valid-country-code-percent/#monthly-string-valid-country-code-percent)|recurring|Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_valid_country_code_percent](./column/strings/string-valid-country-code-percent/#daily-partition-string-valid-country-code-percent)|partitioned|Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_valid_country_code_percent](./column/strings/string-valid-country-code-percent/#monthly-partition-string-valid-country-code-percent)|partitioned|Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_valid_currency_code_percent](./column/strings/string-valid-currency-code-percent/#string-valid-currency-code-percent)|profiling|Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage.|
|[daily_string_valid_currency_code_percent](./column/strings/string-valid-currency-code-percent/#daily-string-valid-currency-code-percent)|recurring|Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_valid_currency_code_percent](./column/strings/string-valid-currency-code-percent/#monthly-string-valid-currency-code-percent)|recurring|Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_valid_currency_code_percent](./column/strings/string-valid-currency-code-percent/#daily-partition-string-valid-currency-code-percent)|partitioned|Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_valid_currency_code_percent](./column/strings/string-valid-currency-code-percent/#monthly-partition-string-valid-currency-code-percent)|partitioned|Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_invalid_email_count](./column/strings/string-invalid-email-count/#string-invalid-email-count)|profiling|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_email_count](./column/strings/string-invalid-email-count/#daily-string-invalid-email-count)|recurring|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_invalid_email_count](./column/strings/string-invalid-email-count/#monthly-string-invalid-email-count)|recurring|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_email_count](./column/strings/string-invalid-email-count/#daily-partition-string-invalid-email-count)|partitioned|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_email_count](./column/strings/string-invalid-email-count/#monthly-partition-string-invalid-email-count)|partitioned|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_invalid_uuid_count](./column/strings/string-invalid-uuid-count/#string-invalid-uuid-count)|profiling|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_uuid_count](./column/strings/string-invalid-uuid-count/#daily-string-invalid-uuid-count)|recurring|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_invalid_uuid_count](./column/strings/string-invalid-uuid-count/#monthly-string-invalid-uuid-count)|recurring|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_uuid_count](./column/strings/string-invalid-uuid-count/#daily-partition-string-invalid-uuid-count)|partitioned|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_uuid_count](./column/strings/string-invalid-uuid-count/#monthly-partition-string-invalid-uuid-count)|partitioned|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_valid_uuid_percent](./column/strings/string-valid-uuid-percent/#string-valid-uuid-percent)|profiling|Verifies that the percentage of valid UUID in a column does not exceed the minimum accepted percentage.|
|[daily_string_valid_uuid_percent](./column/strings/string-valid-uuid-percent/#daily-string-valid-uuid-percent)|recurring|Verifies that the percentage of valid UUID in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_valid_uuid_percent](./column/strings/string-valid-uuid-percent/#monthly-string-valid-uuid-percent)|recurring|Verifies that the percentage of valid UUID in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_uuid_percent](./column/strings/string-valid-uuid-percent/#daily-partition-valid-uuid-percent)|partitioned|Verifies that the percentage of valid UUID in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_uuid_percent](./column/strings/string-valid-uuid-percent/#monthly-partition-valid-uuid-percent)|partitioned|Verifies that the percentage of valid UUID in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_invalid_ip4_address_count](./column/strings/string-invalid-ip4-address-count/#string-invalid-ip4-address-count)|profiling|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_ip4_address_count](./column/strings/string-invalid-ip4-address-count/#daily-string-invalid-ip4-address-count)|recurring|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_invalid_ip4_address_count](./column/strings/string-invalid-ip4-address-count/#monthly-string-invalid-ip4-address-count)|recurring|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_ip4_address_count](./column/strings/string-invalid-ip4-address-count/#daily-partition-string-invalid-ip4-address-count)|partitioned|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_ip4_address_count](./column/strings/string-invalid-ip4-address-count/#monthly-partition-string-invalid-ip4-address-count)|partitioned|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_invalid_ip6_address_count](./column/strings/string-invalid-ip6-address-count/#string-invalid-ip6-address-count)|profiling|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_ip6_address_count](./column/strings/string-invalid-ip6-address-count/#daily-string-invalid-ip6-address-count)|recurring|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_invalid_ip6_address_count](./column/strings/string-invalid-ip6-address-count/#monthly-string-invalid-ip6-address-count)|recurring|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_ip6_address_count](./column/strings/string-invalid-ip6-address-count/#daily-partition-string-invalid-ip6-address-count)|partitioned|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_ip6_address_count](./column/strings/string-invalid-ip6-address-count/#monthly-partition-string-invalid-ip6-address-count)|partitioned|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_not_match_regex_count](./column/strings/string-not-match-regex-count/#string-not-match-regex-count)|profiling|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count.|
|[daily_string_not_match_regex_count](./column/strings/string-not-match-regex-count/#daily-string-not-match-regex-count)|recurring|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_not_match_regex_count](./column/strings/string-not-match-regex-count/#monthly-string-not-match-regex-count)|recurring|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_not_match_regex_count](./column/strings/string-not-match-regex-count/#daily-partition-string-not-match-regex-count)|partitioned|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_not_match_regex_count](./column/strings/string-not-match-regex-count/#monthly-partition-string-not-match-regex-count)|partitioned|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_match_regex_percent](./column/strings/string-match-regex-percent/#string-match-regex-percent)|profiling|Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted percentage.|
|[daily_string_match_regex_percent](./column/strings/string-match-regex-percent/#daily-string-match-regex-percent)|recurring|Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_match_regex_percent](./column/strings/string-match-regex-percent/#monthly-string-match-regex-percent)|recurring|Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_match_regex_percent](./column/strings/string-match-regex-percent/#daily-partition-string-match-regex-percent)|partitioned|Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_match_regex_percent](./column/strings/string-match-regex-percent/#monthly-partition-string-match-regex-percent)|partitioned|Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_not_match_date_regex_count](./column/strings/string-not-match-date-regex-count/#string-not-match-date-regex-count)|profiling|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count.|
|[daily_string_not_match_date_regex_count](./column/strings/string-not-match-date-regex-count/#daily-string-not-match-date-regex-count)|recurring|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_not_match_date_regex_count](./column/strings/string-not-match-date-regex-count/#monthly-string-not-match-date-regex-count)|recurring|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_not_match_date_regex_count](./column/strings/string-not-match-date-regex-count/#daily-partition-string-not-match-date-regex-count)|partitioned|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_not_match_date_regex_count](./column/strings/string-not-match-date-regex-count/#monthly-partition-string-not-match-date-regex-count)|partitioned|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_match_date_regex_percent](./column/strings/string-match-date-regex-percent/#string-match-date-regex-percent)|profiling|Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage.|
|[daily_string_match_date_regex_percent](./column/strings/string-match-date-regex-percent/#daily-string-match-date-regex-percent)|recurring|Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_match_date_regex_percent](./column/strings/string-match-date-regex-percent/#monthly-string-match-date-regex-percent)|recurring|Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_match_date_regex_percent](./column/strings/string-match-date-regex-percent/#daily-partition-string-match-date-regex-percent)|partitioned|Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_match_date_regex_percent](./column/strings/string-match-date-regex-percent/#monthly-partition-string-match-date-regex-percent)|partitioned|Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_match_name_regex_percent](./column/strings/string-match-name-regex-percent/#string-match-name-regex-percent)|profiling|Verifies that the percentage of strings matching the name regex in a column does not exceed the maximum accepted percentage.|
|[daily_string_match_name_regex_percent](./column/strings/string-match-name-regex-percent/#daily-string-match-name-regex-percent)|recurring|Verifies that the percentage of strings matching the name format regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_match_name_regex_percent](./column/strings/string-match-name-regex-percent/#monthly-string-match-name-regex-percent)|recurring|Verifies that the percentage of strings matching the name regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_match_name_regex_percent](./column/strings/string-match-name-regex-percent/#daily-partition-string-match-name-regex-percent)|partitioned|Verifies that the percentage of strings matching the name format regex in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_match_name_regex_percent](./column/strings/string-match-name-regex-percent/#monthly-partition-string-match-name-regex-percent)|partitioned|Verifies that the percentage of strings matching the name format regex in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_most_popular_values](./column/strings/string-most-popular-values/#string-most-popular-values)|profiling|Verifies that the number of top values from a set in a column does not exceed the minimum accepted count.|
|[daily_string_most_popular_values](./column/strings/string-most-popular-values/#daily-string-most-popular-values)|recurring|Verifies that the number of top values from a set in a column does not exceed the minimum accepted count.|
|[monthly_string_most_popular_values](./column/strings/string-most-popular-values/#monthly-string-most-popular-values)|recurring|Verifies that the number of top values from a set in a column does not exceed the minimum accepted count.|
|[daily_partition_string_most_popular_values](./column/strings/string-most-popular-values/#daily-partition-string-most-popular-values)|partitioned|Verifies that the number of top values from a set in a column does not exceed the minimum accepted count.|
|[monthly_partition_string_most_popular_values](./column/strings/string-most-popular-values/#monthly-partition-string-most-popular-values)|partitioned|Verifies that the number of top values from a set in a column does not exceed the minimum accepted count.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[string_datatype_detect](./column/strings/string-datatype-detect/#string-datatype-detect)|profiling|Returns the datatype of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 mixed datatype.|
|[daily_string_datatype_detect](./column/strings/string-datatype-detect/#daily-string-datatype-detect)|recurring|Returns the datatype of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 mixed datatype. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_string_datatype_detect](./column/strings/string-datatype-detect/#monthly-string-datatype-detect)|recurring|Returns the datatype of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 mixed datatype. Stores the most recent row count for each day when the data quality check was evaluated.|
|[daily_partition_string_datatype_detect](./column/strings/string-datatype-detect/#daily-partition-string-datatype-detect)|partitioned|Returns the datatype of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 mixed datatype. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_datatype_detect](./column/strings/string-datatype-detect/#monthly-partition-string-datatype-detect)|partitioned|Returns the datatype of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 mixed datatype. Creates a separate data quality check (and an alert) for each monthly partition.|





###uniqueness  
Counts the number or percent of duplicate or unique values in a column.

| Check name | Check type | Description |
|------------|------------|-------------|
|[unique_count](./column/uniqueness/unique-count/#unique-count)|profiling|Verifies that the number of unique values in a column does not exceed the minimum accepted count.|
|[daily_unique_count](./column/uniqueness/unique-count/#daily-unique-count)|recurring|Verifies that the number of unique values in a column does not exceed the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_unique_count](./column/uniqueness/unique-count/#monthly-unique-count)|recurring|Verifies that the number of unique values in a column does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_unique_count](./column/uniqueness/unique-count/#daily-partition-unique-count)|partitioned|Verifies that the number of unique values in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_unique_count](./column/uniqueness/unique-count/#monthly-partition-unique-count)|partitioned|Verifies that the number of unique values in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[unique_percent](./column/uniqueness/unique-percent/#unique-percent)|profiling|Verifies that the percentage of unique values in a column does not exceed the minimum accepted count.|
|[daily_unique_percent](./column/uniqueness/unique-percent/#daily-unique-percent)|recurring|Verifies that the percentage of unique values in a column does not exceed the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_unique_percent](./column/uniqueness/unique-percent/#monthly-unique-percent)|recurring|Verifies that the percentage of unique values in a column does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_unique_percent](./column/uniqueness/unique-percent/#daily-partition-unique-percent)|partitioned|Verifies that the percentage of unique values in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_unique_percent](./column/uniqueness/unique-percent/#monthly-partition-unique-percent)|partitioned|Verifies that the percentage of unique values in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[duplicate_count](./column/uniqueness/duplicate-count/#duplicate-count)|profiling|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.|
|[daily_duplicate_count](./column/uniqueness/duplicate-count/#daily-duplicate-count)|recurring|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_duplicate_count](./column/uniqueness/duplicate-count/#monthly-duplicate-count)|recurring|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_duplicate_count](./column/uniqueness/duplicate-count/#daily-partition-duplicate-count)|partitioned|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_duplicate_count](./column/uniqueness/duplicate-count/#monthly-partition-duplicate-count)|partitioned|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[duplicate_percent](./column/uniqueness/duplicate-percent/#duplicate-percent)|profiling|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.|
|[daily_duplicate_percent](./column/uniqueness/duplicate-percent/#daily-duplicate-percent)|recurring|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|
|[monthly_duplicate_percent](./column/uniqueness/duplicate-percent/#monthly-duplicate-percent)|recurring|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_duplicate_percent](./column/uniqueness/duplicate-percent/#daily-partition-duplicate-percent)|partitioned|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_duplicate_percent](./column/uniqueness/duplicate-percent/#monthly-partition-duplicate-percent)|partitioned|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|



