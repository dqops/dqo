# table level

This is a list of table data quality checks supported by DQOps, broken down by a category and a brief description of what quality issued they detect.





## **accuracy**
Compares the tested table with another (reference) table.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_total_row_count_match_percent](./accuracy/total-row-count-match-percent.md#profile-total-row-count-match-percent)|profiling|Verifies that the total row count of the tested table matches the total row count of another (reference) table.|standard|
|[daily_total_row_count_match_percent](./accuracy/total-row-count-match-percent.md#daily-total-row-count-match-percent)|monitoring|Verifies the total ow count of a tested table and compares it to a row count of a reference table. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_total_row_count_match_percent](./accuracy/total-row-count-match-percent.md#monthly-total-row-count-match-percent)|monitoring|Verifies the total row count of a tested table and compares it to a row count of a reference table. Stores the most recent check result for each month when the data quality check was evaluated.|standard|






## **availability**
Checks whether the table is accessible and available for use.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_table_availability](./availability/table-availability.md#profile-table-availability)|profiling|Verifies availability of a table in a monitored database using a simple query.|standard|
|[daily_table_availability](./availability/table-availability.md#daily-table-availability)|monitoring|Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each day when the data quality check was evaluated.|standard|
|[monthly_table_availability](./availability/table-availability.md#monthly-table-availability)|monitoring|Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each month when the data quality check was evaluated.|standard|






## **comparisons**


| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_row_count_match](./comparisons/row-count-match.md#profile-row-count-match)|profiling|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause.|standard|
|[daily_row_count_match](./comparisons/row-count-match.md#daily-row-count-match)|monitoring|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_row_count_match](./comparisons/row-count-match.md#monthly-row-count-match)|monitoring|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each month when the data quality check was evaluated.|standard|
|[daily_partition_row_count_match](./comparisons/row-count-match.md#daily-partition-row-count-match)|partitioned|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause on the time period (the daily partition) and all other data grouping columns. Stores the most recent captured value for each daily partition that was analyzed.|standard|
|[monthly_partition_row_count_match](./comparisons/row-count-match.md#monthly-partition-row-count-match)|partitioned|Verifies that the row count of the tested (parent) table matches the row count of the reference table, for each monthly partition (grouping rows by the time period, truncated to the month). Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each monthly partition and optionally data groups.|standard|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_column_count_match](./comparisons/column-count-match.md#profile-column-count-match)|profiling|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping.|standard|
|[daily_column_count_match](./comparisons/column-count-match.md#daily-column-count-match)|monitoring|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_column_count_match](./comparisons/column-count-match.md#monthly-column-count-match)|monitoring|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each month when the data quality check was evaluated.|standard|






## **custom_sql**
Validate data against user-defined SQL queries at the table level. Checks in this group allow for validation that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_sql_condition_passed_percent_on_table](./custom_sql/sql-condition-passed-percent-on-table.md#profile-sql-condition-passed-percent-on-table)|profiling|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;.|advanced|
|[daily_sql_condition_passed_percent_on_table](./custom_sql/sql-condition-passed-percent-on-table.md#daily-sql-condition-passed-percent-on-table)|monitoring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent captured percentage for each day when the data quality check was evaluated.|advanced|
|[monthly_sql_condition_passed_percent_on_table](./custom_sql/sql-condition-passed-percent-on-table.md#monthly-sql-condition-passed-percent-on-table)|monitoring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_sql_condition_passed_percent_on_table](./custom_sql/sql-condition-passed-percent-on-table.md#daily-partition-sql-condition-passed-percent-on-table)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.|advanced|
|[monthly_partition_sql_condition_passed_percent_on_table](./custom_sql/sql-condition-passed-percent-on-table.md#monthly-partition-sql-condition-passed-percent-on-table)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each monthly partition.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_sql_condition_failed_on_table](./custom_sql/sql-condition-failed-on-table.md#profile-sql-condition-failed-on-table)|profiling|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;.|standard|
|[daily_sql_condition_failed_on_table](./custom_sql/sql-condition-failed-on-table.md#daily-sql-condition-failed-on-table)|monitoring|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent count of failed rows for each day when the data quality check was evaluated.|standard|
|[monthly_sql_condition_failed_on_table](./custom_sql/sql-condition-failed-on-table.md#monthly-sql-condition-failed-on-table)|monitoring|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent count of failed rows for each month when the data quality check was evaluated.|standard|
|[daily_partition_sql_condition_failed_on_table](./custom_sql/sql-condition-failed-on-table.md#daily-partition-sql-condition-failed-on-table)|partitioned|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.|standard|
|[monthly_partition_sql_condition_failed_on_table](./custom_sql/sql-condition-failed-on-table.md#monthly-partition-sql-condition-failed-on-table)|partitioned|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each monthly partition.|standard|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_sql_aggregate_expression_on_table](./custom_sql/sql-aggregate-expression-on-table.md#profile-sql-aggregate-expression-on-table)|profiling|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range.|advanced|
|[daily_sql_aggregate_expression_on_table](./custom_sql/sql-aggregate-expression-on-table.md#daily-sql-aggregate-expression-on-table)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_sql_aggregate_expression_on_table](./custom_sql/sql-aggregate-expression-on-table.md#monthly-sql-aggregate-expression-on-table)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_sql_aggregate_expression_on_table](./custom_sql/sql-aggregate-expression-on-table.md#daily-partition-sql-aggregate-expression-on-table)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each daily partition.|advanced|
|[monthly_partition_sql_aggregate_expression_on_table](./custom_sql/sql-aggregate-expression-on-table.md#monthly-partition-sql-aggregate-expression-on-table)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each monthly partition.|advanced|






## **schema**
Detects schema drifts such as columns added, removed, reordered or the data types of columns have changed.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_column_count](./schema/column-count.md#profile-column-count)|profiling|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).|standard|
|[daily_column_count](./schema/column-count.md#daily-column-count)|monitoring|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each day when the data quality check was evaluated.|standard|
|[monthly_column_count](./schema/column-count.md#monthly-column-count)|monitoring|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each month when the data quality check was evaluated.|standard|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_column_count_changed](./schema/column-count-changed.md#profile-column-count-changed)|profiling|Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.|standard|
|[daily_column_count_changed](./schema/column-count-changed.md#daily-column-count-changed)|monitoring|Detects if the count of columns has changed since the most recent day. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each day when the data quality check was evaluated.|standard|
|[monthly_column_count_changed](./schema/column-count-changed.md#monthly-column-count-changed)|monitoring|Detects if the count of columns has changed since the last month. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each month when the data quality check was evaluated.|standard|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_column_list_changed](./schema/column-list-changed.md#profile-column-list-changed)|profiling|Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|advanced|
|[daily_column_list_changed](./schema/column-list-changed.md#daily-column-list-changed)|monitoring|Detects if new columns were added or existing columns were removed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|advanced|
|[monthly_column_list_changed](./schema/column-list-changed.md#monthly-column-list-changed)|monitoring|Detects if new columns were added or existing columns were removed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_column_list_or_order_changed](./schema/column-list-or-order-changed.md#profile-column-list-or-order-changed)|profiling|Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|advanced|
|[daily_column_list_or_order_changed](./schema/column-list-or-order-changed.md#daily-column-list-or-order-changed)|monitoring|Detects if new columns were added, existing columns were removed or the columns were reordered since the most recent day. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|advanced|
|[monthly_column_list_or_order_changed](./schema/column-list-or-order-changed.md#monthly-column-list-or-order-changed)|monitoring|Detects if new columns were added, existing columns were removed or the columns were reordered since the last month. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_column_types_changed](./schema/column-types-changed.md#profile-column-types-changed)|profiling|Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|advanced|
|[daily_column_types_changed](./schema/column-types-changed.md#daily-column-types-changed)|monitoring|Detects if new columns were added, removed or their data types have changed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|advanced|
|[monthly_column_types_changed](./schema/column-types-changed.md#monthly-column-types-changed)|monitoring|Detects if new columns were added, removed or their data types have changed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|advanced|






## **timeliness**
Assesses the freshness and staleness of data, as well as data ingestion delay and reload lag for partitioned data.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_data_freshness](./timeliness/data-freshness.md#profile-data-freshness)|profiling|Calculates the number of days since the most recent event timestamp (freshness)|standard|
|[daily_data_freshness](./timeliness/data-freshness.md#daily-data-freshness)|monitoring|Daily  calculating the number of days since the most recent event timestamp (freshness)|standard|
|[monthly_data_freshness](./timeliness/data-freshness.md#monthly-data-freshness)|monitoring|Monthly monitoring calculating the number of days since the most recent event timestamp (freshness)|standard|
|[daily_partition_data_freshness](./timeliness/data-freshness.md#daily-partition-data-freshness)|partitioned|Daily partitioned check calculating the number of days since the most recent event timestamp (freshness)|standard|
|[monthly_partition_data_freshness](./timeliness/data-freshness.md#monthly-partition-data-freshness)|partitioned|Monthly partitioned check calculating the number of days since the most recent event (freshness)|standard|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_data_staleness](./timeliness/data-staleness.md#profile-data-staleness)|profiling|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|advanced|
|[daily_data_staleness](./timeliness/data-staleness.md#daily-data-staleness)|monitoring|Daily  calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|advanced|
|[monthly_data_staleness](./timeliness/data-staleness.md#monthly-data-staleness)|monitoring|Monthly monitoring calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|advanced|
|[daily_partition_data_staleness](./timeliness/data-staleness.md#daily-partition-data-staleness)|partitioned|Daily partitioned check calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|advanced|
|[monthly_partition_data_staleness](./timeliness/data-staleness.md#monthly-partition-data-staleness)|partitioned|Monthly partitioned check calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_data_ingestion_delay](./timeliness/data-ingestion-delay.md#profile-data-ingestion-delay)|profiling|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|advanced|
|[daily_data_ingestion_delay](./timeliness/data-ingestion-delay.md#daily-data-ingestion-delay)|monitoring|Daily  calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|advanced|
|[monthly_data_ingestion_delay](./timeliness/data-ingestion-delay.md#monthly-data-ingestion-delay)|monitoring|Monthly monitoring calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|advanced|
|[daily_partition_data_ingestion_delay](./timeliness/data-ingestion-delay.md#daily-partition-data-ingestion-delay)|partitioned|Daily partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|advanced|
|[monthly_partition_data_ingestion_delay](./timeliness/data-ingestion-delay.md#monthly-partition-data-ingestion-delay)|partitioned|Monthly partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[daily_partition_reload_lag](./timeliness/reload-lag.md#daily-partition-reload-lag)|partitioned|Daily partitioned check calculating the longest time a row waited to be load|advanced|
|[monthly_partition_reload_lag](./timeliness/reload-lag.md#monthly-partition-reload-lag)|partitioned|Monthly partitioned check calculating the longest time a row waited to be load|advanced|






## **volume**
Evaluates the overall quality of the table by verifying the number of rows.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_row_count](./volume/row-count.md#profile-row-count)|profiling|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|standard|
|[daily_row_count](./volume/row-count.md#daily-row-count)|monitoring|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.Stores the most recent captured row count value for each day when the row count was evaluated.|standard|
|[monthly_row_count](./volume/row-count.md#monthly-row-count)|monitoring|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.Stores the most recent captured row count value for each month when the row count was evaluated.|standard|
|[daily_partition_row_count](./volume/row-count.md#daily-partition-row-count)|partitioned|Verifies that each daily partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|standard|
|[monthly_partition_row_count](./volume/row-count.md#monthly-partition-row-count)|partitioned|Verifies that each monthly partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|standard|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_row_count_anomaly](./volume/row-count-anomaly.md#profile-row-count-anomaly)|profiling|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.|advanced|
|[daily_row_count_anomaly](./volume/row-count-anomaly.md#daily-row-count-anomaly)|monitoring|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during the last 90 days.|advanced|
|[daily_partition_row_count_anomaly](./volume/row-count-anomaly.md#daily-partition-row-count-anomaly)|partitioned|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 90 days.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_row_count_change](./volume/row-count-change.md#profile-row-count-change)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|advanced|
|[daily_row_count_change](./volume/row-count-change.md#daily-row-count-change)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last day with a row count captured.|advanced|
|[monthly_row_count_change](./volume/row-count-change.md#monthly-row-count-change)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last month.|advanced|
|[daily_partition_row_count_change](./volume/row-count-change.md#daily-partition-row-count-change)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|advanced|
|[monthly_partition_row_count_change](./volume/row-count-change.md#monthly-partition-row-count-change)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_row_count_change_1_day](./volume/row-count-change-1-day.md#profile-row-count-change-1-day)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|advanced|
|[daily_row_count_change_1_day](./volume/row-count-change-1-day.md#daily-row-count-change-1-day)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|advanced|
|[daily_partition_row_count_change_1_day](./volume/row-count-change-1-day.md#daily-partition-row-count-change-1-day)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_row_count_change_7_days](./volume/row-count-change-7-days.md#profile-row-count-change-7-days)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|advanced|
|[daily_row_count_change_7_days](./volume/row-count-change-7-days.md#daily-row-count-change-7-days)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from the last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|advanced|
|[daily_partition_row_count_change_7_days](./volume/row-count-change-7-days.md#daily-partition-row-count-change-7-days)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_row_count_change_30_days](./volume/row-count-change-30-days.md#profile-row-count-change-30-days)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|advanced|
|[daily_row_count_change_30_days](./volume/row-count-change-30-days.md#daily-row-count-change-30-days)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from the last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|advanced|
|[daily_partition_row_count_change_30_days](./volume/row-count-change-30-days.md#daily-partition-row-count-change-30-days)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|advanced|







