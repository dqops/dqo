
## TableDailyPartitionedCheckCategoriesSpec  
Container of table level daily partitioned checks. Contains categories of daily partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](../table-daily-partitioned-checks/#TableVolumeDailyPartitionedChecksSpec)|Volume daily partitioned data quality checks that verify the quality of every day of data separately|[TableVolumeDailyPartitionedChecksSpec](../table-daily-partitioned-checks/#TableVolumeDailyPartitionedChecksSpec)| | | |
|[timeliness](../table-daily-partitioned-checks/#TableTimelinessDailyPartitionedChecksSpec)|Daily partitioned timeliness checks|[TableTimelinessDailyPartitionedChecksSpec](../table-daily-partitioned-checks/#TableTimelinessDailyPartitionedChecksSpec)| | | |
|[sql](../table-daily-partitioned-checks/#TableSqlDailyPartitionedChecksSpec)|Custom SQL daily partitioned data quality checks that verify the quality of every day of data separately|[TableSqlDailyPartitionedChecksSpec](../table-daily-partitioned-checks/#TableSqlDailyPartitionedChecksSpec)| | | |
|[comparisons](../table-daily-partitioned-checks/#TableComparisonDailyPartitionedChecksSpecMap)|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[TableComparisonDailyPartitionedChecksSpecMap](../table-daily-partitioned-checks/#TableComparisonDailyPartitionedChecksSpecMap)| | | |
|[custom](../../profiling_checks/table-profiling-checks/#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCheckSpecMap)| | | |









___  

## TableComparisonDailyPartitionedChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the daily partitioned comparison checks for each configured reference table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [TableComparisonDailyPartitionedChecksSpec](../table-daily-partitioned-checks/#TableComparisonDailyPartitionedChecksSpec)]| | | |









___  

## TableTimelinessDailyPartitionedChecksSpec  
Container of table level date partitioned timeliness data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_data_freshness](../../../../checks/table/timeliness/data-freshness/)|Daily partitioned check calculating the number of days since the most recent event timestamp (freshness)|[TableDataFreshnessCheckSpec](../../../../checks/table/timeliness/data-freshness/)| | | |
|[daily_partition_data_staleness](../../../../checks/table/timeliness/data-staleness/)|Daily partitioned check calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|[TableDataStalenessCheckSpec](../../../../checks/table/timeliness/data-staleness/)| | | |
|[daily_partition_data_ingestion_delay](../../../../checks/table/timeliness/data-ingestion-delay/)|Daily partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|[TableDataIngestionDelayCheckSpec](../../../../checks/table/timeliness/data-ingestion-delay/)| | | |
|[daily_partition_reload_lag](../../../../checks/table/timeliness/reload-lag/)|Daily partitioned check calculating the longest time a row waited to be load|[TablePartitionReloadLagCheckSpec](../../../../checks/table/timeliness/reload-lag/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## TableSqlDailyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_sql_condition_passed_percent_on_table](../../../../checks/table/sql/sql-condition-passed-percent-on-table/)|Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|[TableSqlConditionPassedPercentCheckSpec](../../../../checks/table/sql/sql-condition-passed-percent-on-table/)| | | |
|[daily_partition_sql_condition_failed_count_on_table](../../../../checks/table/sql/sql-condition-failed-count-on-table/)|Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|[TableSqlConditionFailedCountCheckSpec](../../../../checks/table/sql/sql-condition-failed-count-on-table/)| | | |
|[daily_partition_sql_aggregate_expr_table](../../../../checks/table/sql/sql-aggregate-expr-table/)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[TableSqlAggregateExprCheckSpec](../../../../checks/table/sql/sql-aggregate-expr-table/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## TableComparisonDailyPartitionedChecksSpec  
Container of built-in comparison (accuracy) checks on a table level that are using a defined comparison to identify the reference table and the data grouping configuration.
 Contains the daily partitioned comparison checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_row_count_match](../../../../checks/table/comparisons/row-count-match/)|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause on the time period (the daily partition) and all other data grouping columns. Stores the most recent captured value for each daily partition that was analyzed.|[TableComparisonRowCountMatchCheckSpec](../../../../checks/table/comparisons/row-count-match/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## TableVolumeDailyPartitionedChecksSpec  
Container of table level date partitioned volume data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_row_count](../../../../checks/table/volume/row-count/)|Verifies that each daily partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|[TableRowCountCheckSpec](../../../../checks/table/volume/row-count/)| | | |
|[daily_partition_row_count_anomaly_stationary_30_days](../../../../checks/table/volume/row-count-anomaly-stationary-30-days/)|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 30 days.|[TableAnomalyStationaryPartitionRowCount30DaysCheckSpec](../../../../checks/table/volume/row-count-anomaly-stationary-30-days/)| | | |
|[daily_partition_row_count_anomaly_stationary](../../../../checks/table/volume/row-count-anomaly-stationary/)|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 90 days.|[TableAnomalyStationaryPartitionRowCountCheckSpec](../../../../checks/table/volume/row-count-anomaly-stationary/)| | | |
|[daily_partition_row_count_change](../../../../checks/table/volume/row-count-change/)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|[TableChangeRowCountCheckSpec](../../../../checks/table/volume/row-count-change/)| | | |
|[daily_partition_row_count_change_yesterday](../../../../checks/table/volume/row-count-change-yesterday/)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|[TableChangeRowCountSinceYesterdayCheckSpec](../../../../checks/table/volume/row-count-change-yesterday/)| | | |
|[daily_partition_row_count_change_7_days](../../../../checks/table/volume/row-count-change-7-days/)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|[TableChangeRowCountSince7DaysCheckSpec](../../../../checks/table/volume/row-count-change-7-days/)| | | |
|[daily_partition_row_count_change_30_days](../../../../checks/table/volume/row-count-change-30-days/)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|[TableChangeRowCountSince30DaysCheckSpec](../../../../checks/table/volume/row-count-change-30-days/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  
