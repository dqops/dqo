
## TableTimelinessDailyMonitoringChecksSpec
Container of table level daily monitoring for timeliness data quality checks.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_data_freshness](../../../../checks/table/timeliness/data-freshness.md)|Daily  calculating the number of days since the most recent event timestamp (freshness)|[TableDataFreshnessCheckSpec](../../../../checks/table/timeliness/data-freshness.md)| | | |
|[daily_data_staleness](../../../../checks/table/timeliness/data-staleness.md)|Daily  calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|[TableDataStalenessCheckSpec](../../../../checks/table/timeliness/data-staleness.md)| | | |
|[daily_data_ingestion_delay](../../../../checks/table/timeliness/data-ingestion-delay.md)|Daily  calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|[TableDataIngestionDelayCheckSpec](../../../../checks/table/timeliness/data-ingestion-delay.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## TableSqlDailyMonitoringChecksSpec
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_sql_condition_passed_percent_on_table](../../../../checks/table/sql/sql-condition-passed-percent-on-table.md)|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|[TableSqlConditionPassedPercentCheckSpec](../../../../checks/table/sql/sql-condition-passed-percent-on-table.md)| | | |
|[daily_sql_condition_failed_count_on_table](../../../../checks/table/sql/sql-condition-failed-count-on-table.md)|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|[TableSqlConditionFailedCountCheckSpec](../../../../checks/table/sql/sql-condition-failed-count-on-table.md)| | | |
|[daily_sql_aggregate_expr_table](../../../../checks/table/sql/sql-aggregate-expr-table.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[TableSqlAggregateExprCheckSpec](../../../../checks/table/sql/sql-aggregate-expr-table.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## TableAvailabilityDailyMonitoringChecksSpec
Container of built-in preconfigured data quality checks on a table level that are detecting the table availability.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_table_availability](../../../../checks/table/availability/table-availability.md)|Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each day when the data quality check was evaluated.|[TableAvailabilityCheckSpec](../../../../checks/table/availability/table-availability.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## TableAccuracyDailyMonitoringChecksSpec
Container of built-in preconfigured data quality checks on a table level that are verifying the accuracy of the table, comparing it with another reference table.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_total_row_count_match_percent](../../../../checks/table/accuracy/total-row-count-match-percent.md)|Verifies the total ow count of a tested table and compares it to a row count of a reference table. Stores the most recent captured value for each day when the data quality check was evaluated.|[TableAccuracyTotalRowCountMatchPercentCheckSpec](../../../../checks/table/accuracy/total-row-count-match-percent.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## TableVolumeDailyMonitoringChecksSpec
Container of table level daily monitoring for volume data quality checks.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_row_count](../../../../checks/table/volume/row-count.md)|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.Stores the most recent captured row count value for each day when the row count was evaluated.|[TableRowCountCheckSpec](../../../../checks/table/volume/row-count.md)| | | |
|[daily_row_count_anomaly_differencing_30_days](../../../../checks/table/volume/row-count-anomaly-differencing-30-days.md)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.|[TableAnomalyDifferencingRowCount30DaysCheckSpec](../../../../checks/table/volume/row-count-anomaly-differencing-30-days.md)| | | |
|[daily_row_count_anomaly_differencing](../../../../checks/table/volume/row-count-anomaly-differencing.md)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.|[TableAnomalyDifferencingRowCountCheckSpec](../../../../checks/table/volume/row-count-anomaly-differencing.md)| | | |
|[daily_row_count_change](../../../../checks/table/volume/row-count-change.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last day with a row count captured.|[TableChangeRowCountCheckSpec](../../../../checks/table/volume/row-count-change.md)| | | |
|[daily_row_count_change_yesterday](../../../../checks/table/volume/row-count-change-yesterday.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|[TableChangeRowCountSinceYesterdayCheckSpec](../../../../checks/table/volume/row-count-change-yesterday.md)| | | |
|[daily_row_count_change_7_days](../../../../checks/table/volume/row-count-change-7-days.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|[TableChangeRowCountSince7DaysCheckSpec](../../../../checks/table/volume/row-count-change-7-days.md)| | | |
|[daily_row_count_change_30_days](../../../../checks/table/volume/row-count-change-30-days.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|[TableChangeRowCountSince30DaysCheckSpec](../../../../checks/table/volume/row-count-change-30-days.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## TableSchemaDailyMonitoringChecksSpec
Container of built-in preconfigured volume data quality checks on a table level that are executed as a daily monitoring (checkpoint) checks.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_column_count](../../../../checks/table/schema/column-count.md)|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each day when the data quality check was evaluated.|[TableSchemaColumnCountCheckSpec](../../../../checks/table/schema/column-count.md)| | | |
|[daily_column_count_changed](../../../../checks/table/schema/column-count-changed.md)|Detects if the count of columns has changed since the most recent day. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each day when the data quality check was evaluated.|[TableSchemaColumnCountChangedCheckSpec](../../../../checks/table/schema/column-count-changed.md)| | | |
|[daily_column_list_changed](../../../../checks/table/schema/column-list-changed.md)|Detects if new columns were added or existing columns were removed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|[TableSchemaColumnListChangedCheckSpec](../../../../checks/table/schema/column-list-changed.md)| | | |
|[daily_column_list_or_order_changed](../../../../checks/table/schema/column-list-or-order-changed.md)|Detects if new columns were added, existing columns were removed or the columns were reordered since the most recent day. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|[TableSchemaColumnListOrOrderChangedCheckSpec](../../../../checks/table/schema/column-list-or-order-changed.md)| | | |
|[daily_column_types_changed](../../../../checks/table/schema/column-types-changed.md)|Detects if new columns were added, removed or their data types have changed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|[TableSchemaColumnTypesChangedCheckSpec](../../../../checks/table/schema/column-types-changed.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## TableComparisonDailyMonitoringChecksSpec
Container of built-in comparison (accuracy) checks on a table level that are using a defined comparison to identify the reference table and the data grouping configuration.
 Contains the daily monitoring comparison checks.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_row_count_match](../../../../checks/table/comparisons/row-count-match.md)|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each day when the data quality check was evaluated.|[TableComparisonRowCountMatchCheckSpec](../../../../checks/table/comparisons/row-count-match.md)| | | |
|[daily_column_count_match](../../../../checks/table/comparisons/column-count-match.md)|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each day when the data quality check was evaluated.|[TableComparisonColumnCountMatchCheckSpec](../../../../checks/table/comparisons/column-count-match.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## TableComparisonDailyMonitoringChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the daily monitoring comparison checks for each configured reference table.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [TableComparisonDailyMonitoringChecksSpec](#TableComparisonDailyMonitoringChecksSpec)]| | | |









___

## TableDailyMonitoringCheckCategoriesSpec
Container of table level daily monitoring. Contains categories of daily monitoring.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](#TableVolumeDailyMonitoringChecksSpec)|Daily monitoring volume data quality checks|[TableVolumeDailyMonitoringChecksSpec](#TableVolumeDailyMonitoringChecksSpec)| | | |
|[timeliness](#TableTimelinessDailyMonitoringChecksSpec)|Daily monitoring timeliness checks|[TableTimelinessDailyMonitoringChecksSpec](#TableTimelinessDailyMonitoringChecksSpec)| | | |
|[accuracy](#TableAccuracyDailyMonitoringChecksSpec)|Daily monitoring accuracy checks|[TableAccuracyDailyMonitoringChecksSpec](#TableAccuracyDailyMonitoringChecksSpec)| | | |
|[sql](#TableSqlDailyMonitoringChecksSpec)|Daily monitoring custom SQL checks|[TableSqlDailyMonitoringChecksSpec](#TableSqlDailyMonitoringChecksSpec)| | | |
|[availability](#TableAvailabilityDailyMonitoringChecksSpec)|Daily monitoring table availability checks|[TableAvailabilityDailyMonitoringChecksSpec](#TableAvailabilityDailyMonitoringChecksSpec)| | | |
|[schema](#TableSchemaDailyMonitoringChecksSpec)|Daily monitoring table schema checks|[TableSchemaDailyMonitoringChecksSpec](#TableSchemaDailyMonitoringChecksSpec)| | | |
|[comparisons](#TableComparisonDailyMonitoringChecksSpecMap)|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[TableComparisonDailyMonitoringChecksSpecMap](#TableComparisonDailyMonitoringChecksSpecMap)| | | |
|[custom](../../profiling/table-profiling-checks.md#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCheckSpecMap)| | | |









___

