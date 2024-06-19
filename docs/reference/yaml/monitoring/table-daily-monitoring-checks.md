---
title: DQOps YAML file definitions
---
# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## TableDailyMonitoringCheckCategoriesSpec
Container of table level daily monitoring. Contains categories of daily monitoring.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`volume`](./table-daily-monitoring-checks.md#tablevolumedailymonitoringchecksspec)</span>|Daily monitoring volume data quality checks|*[TableVolumeDailyMonitoringChecksSpec](./table-daily-monitoring-checks.md#tablevolumedailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`timeliness`](./table-daily-monitoring-checks.md#tabletimelinessdailymonitoringchecksspec)</span>|Daily monitoring timeliness checks|*[TableTimelinessDailyMonitoringChecksSpec](./table-daily-monitoring-checks.md#tabletimelinessdailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`accuracy`](./table-daily-monitoring-checks.md#tableaccuracydailymonitoringchecksspec)</span>|Daily monitoring accuracy checks|*[TableAccuracyDailyMonitoringChecksSpec](./table-daily-monitoring-checks.md#tableaccuracydailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`custom_sql`](./table-daily-monitoring-checks.md#tablecustomsqldailymonitoringchecksspec)</span>|Daily monitoring custom SQL checks|*[TableCustomSqlDailyMonitoringChecksSpec](./table-daily-monitoring-checks.md#tablecustomsqldailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`availability`](./table-daily-monitoring-checks.md#tableavailabilitydailymonitoringchecksspec)</span>|Daily monitoring table availability checks|*[TableAvailabilityDailyMonitoringChecksSpec](./table-daily-monitoring-checks.md#tableavailabilitydailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`schema`](./table-daily-monitoring-checks.md#tableschemadailymonitoringchecksspec)</span>|Daily monitoring table schema checks|*[TableSchemaDailyMonitoringChecksSpec](./table-daily-monitoring-checks.md#tableschemadailymonitoringchecksspec)*| | | |
|<span class="no-wrap-code ">[`comparisons`](./table-daily-monitoring-checks.md#tablecomparisondailymonitoringchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[TableComparisonDailyMonitoringChecksSpecMap](./table-daily-monitoring-checks.md#tablecomparisondailymonitoringchecksspecmap)*| | | |
|<span class="no-wrap-code ">[`custom`](../profiling/table-profiling-checks.md#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](../profiling/table-profiling-checks.md#customcheckspecmap)*| | | |









___


## TableVolumeDailyMonitoringChecksSpec
Container of table level daily monitoring for volume data quality checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_row_count`](../../../checks/table/volume/row-count.md)</span>|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which ensures that the table is not empty. Stores the most recent captured row count value for each day when the row count was evaluated.|*[TableRowCountCheckSpec](../../../checks/table/volume/row-count.md)*| | | |
|<span class="no-wrap-code ">[`daily_row_count_anomaly`](../../../checks/table/volume/row-count-anomaly.md)</span>|Detects when the row count has changed too much since the previous day. It uses time series anomaly detection to find the biggest volume changes during the last 90 days.|*[TableRowCountAnomalyDifferencingCheckSpec](../../../checks/table/volume/row-count-anomaly.md)*| | | |
|<span class="no-wrap-code ">[`daily_row_count_change`](../../../checks/table/volume/row-count-change.md)</span>|Detects when the volume&#x27;s (row count) change since the last known row count exceeds the maximum accepted change percentage.|*[TableRowCountChangeCheckSpec](../../../checks/table/volume/row-count-change.md)*| | | |
|<span class="no-wrap-code ">[`daily_row_count_change_1_day`](../../../checks/table/volume/row-count-change-1-day.md)</span>|Detects when the volume&#x27;s change (increase or decrease of the row count) since the previous day exceeds the maximum accepted change percentage. |*[TableRowCountChange1DayCheckSpec](../../../checks/table/volume/row-count-change-1-day.md)*| | | |
|<span class="no-wrap-code ">[`daily_row_count_change_7_days`](../../../checks/table/volume/row-count-change-7-days.md)</span>|This check verifies that the percentage of change in the table&#x27;s volume (row count) since seven days ago is below the maximum accepted percentage. Verifying a volume change since a value a week ago overcomes the effect of weekly seasonability.|*[TableRowCountChange7DaysCheckSpec](../../../checks/table/volume/row-count-change-7-days.md)*| | | |
|<span class="no-wrap-code ">[`daily_row_count_change_30_days`](../../../checks/table/volume/row-count-change-30-days.md)</span>|This check verifies that the percentage of change in the table&#x27;s volume (row count) since thirty days ago is below the maximum accepted percentage. Comparing the current row count to a value 30 days ago overcomes the effect of monthly seasonability.|*[TableRowCountChange30DaysCheckSpec](../../../checks/table/volume/row-count-change-30-days.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## TableTimelinessDailyMonitoringChecksSpec
Container of table level daily monitoring for timeliness data quality checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_data_freshness`](../../../checks/table/timeliness/data-freshness.md)</span>|Daily calculating the number of days since the most recent event timestamp (freshness)|*[TableDataFreshnessCheckSpec](../../../checks/table/timeliness/data-freshness.md)*| | | |
|<span class="no-wrap-code ">[`daily_data_freshness_anomaly`](../../../checks/table/timeliness/data-freshness-anomaly.md)</span>|Verifies that the number of days since the most recent event timestamp (freshness) changes in a rate within a percentile boundary during the last 90 days.|*[TableDataFreshnessAnomalyCheckSpec](../../../checks/table/timeliness/data-freshness-anomaly.md)*| | | |
|<span class="no-wrap-code ">[`daily_data_staleness`](../../../checks/table/timeliness/data-staleness.md)</span>|Daily calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|*[TableDataStalenessCheckSpec](../../../checks/table/timeliness/data-staleness.md)*| | | |
|<span class="no-wrap-code ">[`daily_data_ingestion_delay`](../../../checks/table/timeliness/data-ingestion-delay.md)</span>|Daily calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|*[TableDataIngestionDelayCheckSpec](../../../checks/table/timeliness/data-ingestion-delay.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## TableAccuracyDailyMonitoringChecksSpec
Container of built-in preconfigured data quality checks on a table level that are verifying the accuracy of the table, comparing it with another reference table.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_total_row_count_match_percent`](../../../checks/table/accuracy/total-row-count-match-percent.md)</span>|Verifies the total ow count of a tested table and compares it to a row count of a reference table. Stores the most recent captured value for each day when the data quality check was evaluated.|*[TableAccuracyTotalRowCountMatchPercentCheckSpec](../../../checks/table/accuracy/total-row-count-match-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## TableCustomSqlDailyMonitoringChecksSpec
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_sql_condition_failed_on_table`](../../../checks/table/custom_sql/sql-condition-failed-on-table.md)</span>|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent count of failed rows for each day when the data quality check was evaluated.|*[TableSqlConditionFailedCheckSpec](../../../checks/table/custom_sql/sql-condition-failed-on-table.md)*| | | |
|<span class="no-wrap-code ">[`daily_sql_condition_passed_percent_on_table`](../../../checks/table/custom_sql/sql-condition-passed-percent-on-table.md)</span>|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores the most recent captured percentage for each day when the data quality check was evaluated.|*[TableSqlConditionPassedPercentCheckSpec](../../../checks/table/custom_sql/sql-condition-passed-percent-on-table.md)*| | | |
|<span class="no-wrap-code ">[`daily_sql_aggregate_expression_on_table`](../../../checks/table/custom_sql/sql-aggregate-expression-on-table.md)</span>|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.|*[TableSqlAggregateExpressionCheckSpec](../../../checks/table/custom_sql/sql-aggregate-expression-on-table.md)*| | | |
|<span class="no-wrap-code ">[`daily_import_custom_result_on_table`](../../../checks/table/custom_sql/import-custom-result-on-table.md)</span>|Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.|*[TableSqlImportCustomResultCheckSpec](../../../checks/table/custom_sql/import-custom-result-on-table.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## TableAvailabilityDailyMonitoringChecksSpec
Container of built-in preconfigured data quality checks on a table level that are detecting the table availability.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_table_availability`](../../../checks/table/availability/table-availability.md)</span>|Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each day when the data quality check was evaluated.|*[TableAvailabilityCheckSpec](../../../checks/table/availability/table-availability.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## TableSchemaDailyMonitoringChecksSpec
Container of built-in preconfigured volume data quality checks on a table level that are executed as a daily monitoring (checkpoint) checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_column_count`](../../../checks/table/schema/column-count.md)</span>|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each day when the data quality check was evaluated.|*[TableSchemaColumnCountCheckSpec](../../../checks/table/schema/column-count.md)*| | | |
|<span class="no-wrap-code ">[`daily_column_count_changed`](../../../checks/table/schema/column-count-changed.md)</span>|Detects if the count of columns has changed since the most recent day. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each day when the data quality check was evaluated.|*[TableSchemaColumnCountChangedCheckSpec](../../../checks/table/schema/column-count-changed.md)*| | | |
|<span class="no-wrap-code ">[`daily_column_list_changed`](../../../checks/table/schema/column-list-changed.md)</span>|Detects if new columns were added or existing columns were removed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|*[TableSchemaColumnListChangedCheckSpec](../../../checks/table/schema/column-list-changed.md)*| | | |
|<span class="no-wrap-code ">[`daily_column_list_or_order_changed`](../../../checks/table/schema/column-list-or-order-changed.md)</span>|Detects if new columns were added, existing columns were removed or the columns were reordered since the most recent day. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|*[TableSchemaColumnListOrOrderChangedCheckSpec](../../../checks/table/schema/column-list-or-order-changed.md)*| | | |
|<span class="no-wrap-code ">[`daily_column_types_changed`](../../../checks/table/schema/column-types-changed.md)</span>|Detects if new columns were added, removed or their data types have changed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|*[TableSchemaColumnTypesChangedCheckSpec](../../../checks/table/schema/column-types-changed.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## TableComparisonDailyMonitoringChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the daily monitoring comparison checks for each configured reference table.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`self`</span>||*Dict[string, [TableComparisonDailyMonitoringChecksSpec](./table-daily-monitoring-checks.md#tablecomparisondailymonitoringchecksspec)]*| | | |









___


## TableComparisonDailyMonitoringChecksSpec
Container of built-in comparison (accuracy) checks on a table level that are using a defined comparison to identify the reference table and the data grouping configuration.
 Contains the daily monitoring comparison checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_row_count_match`](../../../checks/table/comparisons/row-count-match.md)</span>|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each day when the data quality check was evaluated.|*[TableComparisonRowCountMatchCheckSpec](../../../checks/table/comparisons/row-count-match.md)*| | | |
|<span class="no-wrap-code ">[`daily_column_count_match`](../../../checks/table/comparisons/column-count-match.md)</span>|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each day when the data quality check was evaluated.|*[TableComparisonColumnCountMatchCheckSpec](../../../checks/table/comparisons/column-count-match.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


