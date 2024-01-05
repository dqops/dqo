
## TableVolumeDailyPartitionedChecksSpec
Container of table level date partitioned volume data quality checks.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_row_count](../../../../checks/table/volume/row-count.md)|Verifies that each daily partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|[TableRowCountCheckSpec](../../../../checks/table/volume/row-count.md)| | | |
|[daily_partition_row_count_anomaly_stationary_30_days](../../../../checks/table/volume/row-count-anomaly-stationary-30-days.md)|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 30 days.|[TableAnomalyStationaryPartitionRowCount30DaysCheckSpec](../../../../checks/table/volume/row-count-anomaly-stationary-30-days.md)| | | |
|[daily_partition_row_count_anomaly_stationary](../../../../checks/table/volume/row-count-anomaly-stationary.md)|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 90 days.|[TableAnomalyStationaryPartitionRowCountCheckSpec](../../../../checks/table/volume/row-count-anomaly-stationary.md)| | | |
|[daily_partition_row_count_change](../../../../checks/table/volume/row-count-change.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|[TableChangeRowCountCheckSpec](../../../../checks/table/volume/row-count-change.md)| | | |
|[daily_partition_row_count_change_yesterday](../../../../checks/table/volume/row-count-change-yesterday.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|[TableChangeRowCountSinceYesterdayCheckSpec](../../../../checks/table/volume/row-count-change-yesterday.md)| | | |
|[daily_partition_row_count_change_7_days](../../../../checks/table/volume/row-count-change-7-days.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|[TableChangeRowCountSince7DaysCheckSpec](../../../../checks/table/volume/row-count-change-7-days.md)| | | |
|[daily_partition_row_count_change_30_days](../../../../checks/table/volume/row-count-change-30-days.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|[TableChangeRowCountSince30DaysCheckSpec](../../../../checks/table/volume/row-count-change-30-days.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## TableTimelinessDailyPartitionedChecksSpec
Container of table level date partitioned timeliness data quality checks.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_data_freshness](../../../../checks/table/timeliness/data-freshness.md)|Daily partitioned check calculating the number of days since the most recent event timestamp (freshness)|[TableDataFreshnessCheckSpec](../../../../checks/table/timeliness/data-freshness.md)| | | |
|[daily_partition_data_staleness](../../../../checks/table/timeliness/data-staleness.md)|Daily partitioned check calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|[TableDataStalenessCheckSpec](../../../../checks/table/timeliness/data-staleness.md)| | | |
|[daily_partition_data_ingestion_delay](../../../../checks/table/timeliness/data-ingestion-delay.md)|Daily partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|[TableDataIngestionDelayCheckSpec](../../../../checks/table/timeliness/data-ingestion-delay.md)| | | |
|[daily_partition_reload_lag](../../../../checks/table/timeliness/reload-lag.md)|Daily partitioned check calculating the longest time a row waited to be load|[TablePartitionReloadLagCheckSpec](../../../../checks/table/timeliness/reload-lag.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## TableSqlDailyPartitionedChecksSpec
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_sql_condition_passed_percent_on_table](../../../../checks/table/sql/sql-condition-passed-percent-on-table.md)|Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|[TableSqlConditionPassedPercentCheckSpec](../../../../checks/table/sql/sql-condition-passed-percent-on-table.md)| | | |
|[daily_partition_sql_condition_failed_count_on_table](../../../../checks/table/sql/sql-condition-failed-count-on-table.md)|Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|[TableSqlConditionFailedCountCheckSpec](../../../../checks/table/sql/sql-condition-failed-count-on-table.md)| | | |
|[daily_partition_sql_aggregate_expr_table](../../../../checks/table/sql/sql-aggregate-expr-table.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[TableSqlAggregateExprCheckSpec](../../../../checks/table/sql/sql-aggregate-expr-table.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## TableComparisonDailyPartitionedChecksSpec
Container of built-in comparison (accuracy) checks on a table level that are using a defined comparison to identify the reference table and the data grouping configuration.
 Contains the daily partitioned comparison checks.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_row_count_match](../../../../checks/table/comparisons/row-count-match.md)|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause on the time period (the daily partition) and all other data grouping columns. Stores the most recent captured value for each daily partition that was analyzed.|[TableComparisonRowCountMatchCheckSpec](../../../../checks/table/comparisons/row-count-match.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## TableComparisonDailyPartitionedChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the daily partitioned comparison checks for each configured reference table.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [TableComparisonDailyPartitionedChecksSpec](#TableComparisonDailyPartitionedChecksSpec)]| | | |









___

## TableDailyPartitionedCheckCategoriesSpec
Container of table level daily partitioned checks. Contains categories of daily partitioned checks.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](#TableVolumeDailyPartitionedChecksSpec)|Volume daily partitioned data quality checks that verify the quality of every day of data separately|[TableVolumeDailyPartitionedChecksSpec](#TableVolumeDailyPartitionedChecksSpec)| | | |
|[timeliness](#TableTimelinessDailyPartitionedChecksSpec)|Daily partitioned timeliness checks|[TableTimelinessDailyPartitionedChecksSpec](#TableTimelinessDailyPartitionedChecksSpec)| | | |
|[sql](#TableSqlDailyPartitionedChecksSpec)|Custom SQL daily partitioned data quality checks that verify the quality of every day of data separately|[TableSqlDailyPartitionedChecksSpec](#TableSqlDailyPartitionedChecksSpec)| | | |
|[comparisons](#TableComparisonDailyPartitionedChecksSpecMap)|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[TableComparisonDailyPartitionedChecksSpecMap](#TableComparisonDailyPartitionedChecksSpecMap)| | | |
|[custom](../../profiling/table-profiling-checks.md#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCheckSpecMap)| | | |









___

