
## TableSqlMonthlyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_sql_condition_passed_percent_on_table](../../../../checks/table/sql/sql-condition-passed-percent-on-table/)|Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|[TableSqlConditionPassedPercentCheckSpec](../../../../checks/table/sql/sql-condition-passed-percent-on-table/)| | | |
|[monthly_partition_sql_condition_failed_count_on_table](../../../../checks/table/sql/sql-condition-failed-count-on-table/)|Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|[TableSqlConditionFailedCountCheckSpec](../../../../checks/table/sql/sql-condition-failed-count-on-table/)| | | |
|[monthly_partition_sql_aggregate_expr_table](../../../../checks/table/sql/sql-aggregate-expr-table/)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[TableSqlAggregateExprCheckSpec](../../../../checks/table/sql/sql-aggregate-expr-table/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## TableMonthlyPartitionedCheckCategoriesSpec  
Container of table level monthly partitioned checks. Contains categories of monthly partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](../table-monthly-partitioned-checks/#TableVolumeMonthlyPartitionedChecksSpec)|Volume monthly partitioned data quality checks that verify the quality of every month of data separately|[TableVolumeMonthlyPartitionedChecksSpec](../table-monthly-partitioned-checks/#TableVolumeMonthlyPartitionedChecksSpec)| | | |
|[timeliness](../table-monthly-partitioned-checks/#TableTimelinessMonthlyPartitionedChecksSpec)|Monthly partitioned timeliness checks|[TableTimelinessMonthlyPartitionedChecksSpec](../table-monthly-partitioned-checks/#TableTimelinessMonthlyPartitionedChecksSpec)| | | |
|[sql](../table-monthly-partitioned-checks/#TableSqlMonthlyPartitionedChecksSpec)|Custom SQL monthly partitioned data quality checks that verify the quality of every month of data separately|[TableSqlMonthlyPartitionedChecksSpec](../table-monthly-partitioned-checks/#TableSqlMonthlyPartitionedChecksSpec)| | | |
|[comparisons](../table-monthly-partitioned-checks/#TableComparisonMonthlyPartitionedChecksSpecMap)|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[TableComparisonMonthlyPartitionedChecksSpecMap](../table-monthly-partitioned-checks/#TableComparisonMonthlyPartitionedChecksSpecMap)| | | |
|[custom](../../profiling_checks/table-profiling-checks/#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCheckSpecMap)| | | |









___  

## TableComparisonMonthlyPartitionedChecksSpec  
Container of built-in comparison (accuracy) checks on a table level that are using a defined comparison to identify the reference table and the data grouping configuration.
 Contains the monthly partitioned comparison checks, comparing each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_row_count_match](../../../../checks/table/comparisons/row-count-match/)|Verifies that the row count of the tested (parent) table matches the row count of the reference table, for each monthly partition (grouping rows by the time period, truncated to the month). Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each monthly partition and optionally data groups.|[TableComparisonRowCountMatchCheckSpec](../../../../checks/table/comparisons/row-count-match/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## TableTimelinessMonthlyPartitionedChecksSpec  
Container of table level monthly partitioned timeliness data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_data_freshness](../../../../checks/table/timeliness/data-freshness/)|Monthly partitioned check calculating the number of days since the most recent event (freshness)|[TableDataFreshnessCheckSpec](../../../../checks/table/timeliness/data-freshness/)| | | |
|[monthly_partition_data_staleness](../../../../checks/table/timeliness/data-staleness/)|Monthly partitioned check calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)|[TableDataStalenessCheckSpec](../../../../checks/table/timeliness/data-staleness/)| | | |
|[monthly_partition_data_ingestion_delay](../../../../checks/table/timeliness/data-ingestion-delay/)|Monthly partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|[TableDataIngestionDelayCheckSpec](../../../../checks/table/timeliness/data-ingestion-delay/)| | | |
|[monthly_partition_reload_lag](../../../../checks/table/timeliness/reload-lag/)|Monthly partitioned check calculating the longest time a row waited to be load|[TablePartitionReloadLagCheckSpec](../../../../checks/table/timeliness/reload-lag/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## TableVolumeMonthlyPartitionedChecksSpec  
Container of table level monthly partitioned volume data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_row_count](../../../../checks/table/volume/row-count/)|Verifies that each monthly partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|[TableRowCountCheckSpec](../../../../checks/table/volume/row-count/)| | | |
|[monthly_partition_row_count_change](../../../../checks/table/volume/row-count-change/)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|[TableChangeRowCountCheckSpec](../../../../checks/table/volume/row-count-change/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## TableComparisonMonthlyPartitionedChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the monthly partitioned comparison checks for each configured reference table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [TableComparisonMonthlyPartitionedChecksSpec](../table-monthly-partitioned-checks/#TableComparisonMonthlyPartitionedChecksSpec)]| | | |









___  
