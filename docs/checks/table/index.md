# table level

This is a list of table data quality checks supported by DQOps, broken down by a category and a brief description of what quality issued they detect.





## **accuracy**
Compares the tested table with another (reference) table.

| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_total_row_count_match_percent`</span>](./accuracy/total-row-count-match-percent.md#profile-total-row-count-match-percent)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the total row count of the tested table matches the total row count of another (reference) table.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_total_row_count_match_percent`</span>](./accuracy/total-row-count-match-percent.md#daily-total-row-count-match-percent)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies the total ow count of a tested table and compares it to a row count of a reference table. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_total_row_count_match_percent`</span>](./accuracy/total-row-count-match-percent.md#monthly-total-row-count-match-percent)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies the total row count of a tested table and compares it to a row count of a reference table. Stores the most recent check result for each month when the data quality check was evaluated.|:material-check-bold:|






## **availability**
Checks whether the table is accessible and available for use.

| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_table_availability`</span>](./availability/table-availability.md#profile-table-availability)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies availability of a table in a monitored database using a simple query.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_table_availability`</span>](./availability/table-availability.md#daily-table-availability)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_table_availability`</span>](./availability/table-availability.md#monthly-table-availability)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each month when the data quality check was evaluated.|:material-check-bold:|






## **comparisons**
Compares the table (the row count, and the column count) to another table in a different data source.

| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count_match`</span>](./comparisons/row-count-match.md#profile-row-count-match)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_row_count_match`</span>](./comparisons/row-count-match.md#daily-row-count-match)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_row_count_match`</span>](./comparisons/row-count-match.md#monthly-row-count-match)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_row_count_match`</span>](./comparisons/row-count-match.md#daily-partition-row-count-match)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause on the time period (the daily partition) and all other data grouping columns. Stores the most recent captured value for each daily partition that was analyzed.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_row_count_match`</span>](./comparisons/row-count-match.md#monthly-partition-row-count-match)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the row count of the tested (parent) table matches the row count of the reference table, for each monthly partition (grouping rows by the time period, truncated to the month). Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each monthly partition and optionally data groups.|:material-check-bold:|



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_column_count_match`</span>](./comparisons/column-count-match.md#profile-column-count-match)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_column_count_match`</span>](./comparisons/column-count-match.md#daily-column-count-match)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_column_count_match`</span>](./comparisons/column-count-match.md#monthly-column-count-match)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|






## **custom_sql**
Validate data against user-defined SQL queries at the table level. Checks in this group allow for validation that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sql_condition_failed_on_table`</span>](./custom_sql/sql-condition-failed-on-table.md#profile-sql-condition-failed-on-table)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_sql_condition_failed_on_table`</span>](./custom_sql/sql-condition-failed-on-table.md#daily-sql-condition-failed-on-table)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent count of failed rows for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_sql_condition_failed_on_table`</span>](./custom_sql/sql-condition-failed-on-table.md#monthly-sql-condition-failed-on-table)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent count of failed rows for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_sql_condition_failed_on_table`</span>](./custom_sql/sql-condition-failed-on-table.md#daily-partition-sql-condition-failed-on-table)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_sql_condition_failed_on_table`</span>](./custom_sql/sql-condition-failed-on-table.md#monthly-partition-sql-condition-failed-on-table)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sql_condition_passed_percent_on_table`</span>](./custom_sql/sql-condition-passed-percent-on-table.md#profile-sql-condition-passed-percent-on-table)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;.| |
|[<span class="no-wrap-code">`daily_sql_condition_passed_percent_on_table`</span>](./custom_sql/sql-condition-passed-percent-on-table.md#daily-sql-condition-passed-percent-on-table)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent captured percentage for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_sql_condition_passed_percent_on_table`</span>](./custom_sql/sql-condition-passed-percent-on-table.md#monthly-sql-condition-passed-percent-on-table)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_sql_condition_passed_percent_on_table`</span>](./custom_sql/sql-condition-passed-percent-on-table.md#daily-partition-sql-condition-passed-percent-on-table)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_sql_condition_passed_percent_on_table`</span>](./custom_sql/sql-condition-passed-percent-on-table.md#monthly-partition-sql-condition-passed-percent-on-table)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each monthly partition.| |



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sql_aggregate_expression_on_table`</span>](./custom_sql/sql-aggregate-expression-on-table.md#profile-sql-aggregate-expression-on-table)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_sql_aggregate_expression_on_table`</span>](./custom_sql/sql-aggregate-expression-on-table.md#daily-sql-aggregate-expression-on-table)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_sql_aggregate_expression_on_table`</span>](./custom_sql/sql-aggregate-expression-on-table.md#monthly-sql-aggregate-expression-on-table)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_sql_aggregate_expression_on_table`</span>](./custom_sql/sql-aggregate-expression-on-table.md#daily-partition-sql-aggregate-expression-on-table)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_sql_aggregate_expression_on_table`</span>](./custom_sql/sql-aggregate-expression-on-table.md#monthly-partition-sql-aggregate-expression-on-table)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |






## **schema**
Detects schema drifts such as columns added, removed, reordered or the data types of columns have changed.

| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_column_count`</span>](./schema/column-count.md#profile-column-count)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).|:material-check-bold:|
|[<span class="no-wrap-code">`daily_column_count`</span>](./schema/column-count.md#daily-column-count)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_column_count`</span>](./schema/column-count.md#monthly-column-count)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each month when the data quality check was evaluated.|:material-check-bold:|



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_column_count_changed`</span>](./schema/column-count-changed.md#profile-column-count-changed)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_column_count_changed`</span>](./schema/column-count-changed.md#daily-column-count-changed)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if the count of columns has changed since the most recent day. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_column_count_changed`</span>](./schema/column-count-changed.md#monthly-column-count-changed)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if the count of columns has changed since the last month. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each month when the data quality check was evaluated.|:material-check-bold:|



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_column_list_changed`</span>](./schema/column-list-changed.md#profile-column-list-changed)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.| |
|[<span class="no-wrap-code">`daily_column_list_changed`</span>](./schema/column-list-changed.md#daily-column-list-changed)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if new columns were added or existing columns were removed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.| |
|[<span class="no-wrap-code">`monthly_column_list_changed`</span>](./schema/column-list-changed.md#monthly-column-list-changed)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if new columns were added or existing columns were removed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.| |



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_column_list_or_order_changed`</span>](./schema/column-list-or-order-changed.md#profile-column-list-or-order-changed)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.| |
|[<span class="no-wrap-code">`daily_column_list_or_order_changed`</span>](./schema/column-list-or-order-changed.md#daily-column-list-or-order-changed)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if new columns were added, existing columns were removed or the columns were reordered since the most recent day. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.| |
|[<span class="no-wrap-code">`monthly_column_list_or_order_changed`</span>](./schema/column-list-or-order-changed.md#monthly-column-list-or-order-changed)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if new columns were added, existing columns were removed or the columns were reordered since the last month. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.| |



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_column_types_changed`</span>](./schema/column-types-changed.md#profile-column-types-changed)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.| |
|[<span class="no-wrap-code">`daily_column_types_changed`</span>](./schema/column-types-changed.md#daily-column-types-changed)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if new columns were added, removed or their data types have changed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.| |
|[<span class="no-wrap-code">`monthly_column_types_changed`</span>](./schema/column-types-changed.md#monthly-column-types-changed)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if new columns were added, removed or their data types have changed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.| |






## **timeliness**
Assesses the freshness and staleness of data, as well as data ingestion delay and reload lag for partitioned data.

| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_data_freshness`</span>](./timeliness/data-freshness.md#profile-data-freshness)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Calculates the number of days since the most recent event timestamp (freshness)|:material-check-bold:|
|[<span class="no-wrap-code">`daily_data_freshness`</span>](./timeliness/data-freshness.md#daily-data-freshness)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Daily  calculating the number of days since the most recent event timestamp (freshness)|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_data_freshness`</span>](./timeliness/data-freshness.md#monthly-data-freshness)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Monthly monitoring calculating the number of days since the most recent event timestamp (freshness)|:material-check-bold:|



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_data_staleness`</span>](./timeliness/data-staleness.md#profile-data-staleness)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)| |
|[<span class="no-wrap-code">`daily_data_staleness`</span>](./timeliness/data-staleness.md#daily-data-staleness)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Daily  calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)| |
|[<span class="no-wrap-code">`monthly_data_staleness`</span>](./timeliness/data-staleness.md#monthly-data-staleness)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Monthly monitoring calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)| |



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_data_ingestion_delay`</span>](./timeliness/data-ingestion-delay.md#profile-data-ingestion-delay)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp| |
|[<span class="no-wrap-code">`daily_data_ingestion_delay`</span>](./timeliness/data-ingestion-delay.md#daily-data-ingestion-delay)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Daily  calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp| |
|[<span class="no-wrap-code">`monthly_data_ingestion_delay`</span>](./timeliness/data-ingestion-delay.md#monthly-data-ingestion-delay)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Monthly monitoring calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp| |
|[<span class="no-wrap-code">`daily_partition_data_ingestion_delay`</span>](./timeliness/data-ingestion-delay.md#daily-partition-data-ingestion-delay)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Daily partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp| |
|[<span class="no-wrap-code">`monthly_partition_data_ingestion_delay`</span>](./timeliness/data-ingestion-delay.md#monthly-partition-data-ingestion-delay)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Monthly partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp| |



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`daily_partition_reload_lag`</span>](./timeliness/reload-lag.md#daily-partition-reload-lag)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Daily partitioned check calculating the longest time a row waited to be loaded, it is the maximum difference in days between the ingestion timestamp and the event timestamp column on any row in the monitored partition| |
|[<span class="no-wrap-code">`monthly_partition_reload_lag`</span>](./timeliness/reload-lag.md#monthly-partition-reload-lag)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Monthly partitioned check calculating the longest time a row waited to be loaded, it is the maximum difference in days between the ingestion timestamp and the event timestamp column on any row in the monitored partition| |






## **volume**
Evaluates the overall quality of the table by verifying the number of rows.

| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count`</span>](./volume/row-count.md#profile-row-count)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_row_count`</span>](./volume/row-count.md#daily-row-count)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.Stores the most recent captured row count value for each day when the row count was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_row_count`</span>](./volume/row-count.md#monthly-row-count)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.Stores the most recent captured row count value for each month when the row count was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_row_count`</span>](./volume/row-count.md#daily-partition-row-count)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that each daily partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_row_count`</span>](./volume/row-count.md#monthly-partition-row-count)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that each monthly partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|:material-check-bold:|



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count_anomaly`</span>](./volume/row-count-anomaly.md#profile-row-count-anomaly)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.| |
|[<span class="no-wrap-code">`daily_row_count_anomaly`</span>](./volume/row-count-anomaly.md#daily-row-count-anomaly)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during the last 90 days.| |
|[<span class="no-wrap-code">`daily_partition_row_count_anomaly`</span>](./volume/row-count-anomaly.md#daily-partition-row-count-anomaly)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 90 days.| |



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count_change`</span>](./volume/row-count-change.md#profile-row-count-change)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_row_count_change`</span>](./volume/row-count-change.md#daily-row-count-change)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last day with a row count captured.| |
|[<span class="no-wrap-code">`monthly_row_count_change`</span>](./volume/row-count-change.md#monthly-row-count-change)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last month.| |
|[<span class="no-wrap-code">`daily_partition_row_count_change`</span>](./volume/row-count-change.md#daily-partition-row-count-change)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`monthly_partition_row_count_change`</span>](./volume/row-count-change.md#monthly-partition-row-count-change)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.| |



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count_change_1_day`</span>](./volume/row-count-change-1-day.md#profile-row-count-change-1-day)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.| |
|[<span class="no-wrap-code">`daily_row_count_change_1_day`</span>](./volume/row-count-change-1-day.md#daily-row-count-change-1-day)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.| |
|[<span class="no-wrap-code">`daily_partition_row_count_change_1_day`</span>](./volume/row-count-change-1-day.md#daily-partition-row-count-change-1-day)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.| |



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count_change_7_days`</span>](./volume/row-count-change-7-days.md#profile-row-count-change-7-days)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.| |
|[<span class="no-wrap-code">`daily_row_count_change_7_days`</span>](./volume/row-count-change-7-days.md#daily-row-count-change-7-days)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from the last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.| |
|[<span class="no-wrap-code">`daily_partition_row_count_change_7_days`</span>](./volume/row-count-change-7-days.md#daily-partition-row-count-change-7-days)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.| |



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count_change_30_days`</span>](./volume/row-count-change-30-days.md#profile-row-count-change-30-days)|[profiling](../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.| |
|[<span class="no-wrap-code">`daily_row_count_change_30_days`</span>](./volume/row-count-change-30-days.md#daily-row-count-change-30-days)|[monitoring](../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from the last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.| |
|[<span class="no-wrap-code">`daily_partition_row_count_change_30_days`</span>](./volume/row-count-change-30-days.md#daily-partition-row-count-change-30-days)|[partitioned](../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.| |







