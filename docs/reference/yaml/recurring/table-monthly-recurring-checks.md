
## TableComparisonMonthlyRecurringChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the monthly recurring comparison checks for each configured reference table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|access_order||boolean| | | |
|size||integer| | | |
|mod_count||integer| | | |
|threshold||integer| | | |









___  

## TableVolumeMonthlyRecurringChecksSpec  
Container of table level monthly recurring for volume data quality checks  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_row_count](\docs\checks\table\volume\row-count)|Verifies that the number of rows in a table does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[TableRowCountCheckSpec](\docs\checks\table\volume\row-count)| | | |
|[monthly_row_count_change](\docs\checks\table\volume\row-count-change)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|[TableChangeRowCountCheckSpec](\docs\checks\table\volume\row-count-change)| | | |









___  

## TableSqlMonthlyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_sql_condition_passed_percent_on_table](\docs\checks\table\sql\sql-condition-passed-percent-on-table)|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|[TableSqlConditionPassedPercentCheckSpec](\docs\checks\table\sql\sql-condition-passed-percent-on-table)| | | |
|[monthly_sql_condition_failed_count_on_table](\docs\checks\table\sql\sql-condition-failed-count-on-table)|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|[TableSqlConditionFailedCountCheckSpec](\docs\checks\table\sql\sql-condition-failed-count-on-table)| | | |
|[monthly_sql_aggregate_expr_table](\docs\checks\table\sql\sql-aggregate-expr-table)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[TableSqlAggregateExprCheckSpec](\docs\checks\table\sql\sql-aggregate-expr-table)| | | |









___  

## TableAccuracyMonthlyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are verifying the accuracy of the table, comparing it with another reference table, on a monthly basis.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_total_row_count_match_percent](\docs\checks\table\accuracy\total-row-count-match-percent)|Verifies the total row count of a tested table and compares it to a row count of a reference table. Stores the most recent row count for each month when the data quality check was evaluated.|[TableAccuracyTotalRowCountMatchPercentCheckSpec](\docs\checks\table\accuracy\total-row-count-match-percent)| | | |









___  

## TableSchemaMonthlyRecurringChecksSpec  
Container of built-in preconfigured volume data quality checks on a table level that are executed as a monthly recurring (checkpoint) checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_column_count](\docs\checks\table\schema\column-count)|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each month when the data quality check was evaluated.|[TableSchemaColumnCountCheckSpec](\docs\checks\table\schema\column-count)| | | |
|[monthly_column_count_changed](\docs\checks\table\schema\column-count-changed)|Detects if the count of columns has changed since the last month. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each month when the data quality check was evaluated.|[TableSchemaColumnCountChangedCheckSpec](\docs\checks\table\schema\column-count-changed)| | | |
|[monthly_column_list_changed](\docs\checks\table\schema\column-list-changed)|Detects if new columns were added or existing columns were removed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|[TableSchemaColumnListChangedCheckSpec](\docs\checks\table\schema\column-list-changed)| | | |
|[monthly_column_list_or_order_changed](\docs\checks\table\schema\column-list-or-order-changed)|Detects if new columns were added, existing columns were removed or the columns were reordered since the last month. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|[TableSchemaColumnListOrOrderChangedCheckSpec](\docs\checks\table\schema\column-list-or-order-changed)| | | |
|[monthly_column_types_changed](\docs\checks\table\schema\column-types-changed)|Detects if new columns were added, removed or their data types have changed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|[TableSchemaColumnTypesChangedCheckSpec](\docs\checks\table\schema\column-types-changed)| | | |









___  

## TableMonthlyRecurringCheckCategoriesSpec  
Container of table level monthly recurring checks. Contains categories of monthly recurring checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](#tablevolumemonthlyrecurringchecksspec)|Monthly recurring of volume data quality checks|[TableVolumeMonthlyRecurringChecksSpec](#tablevolumemonthlyrecurringchecksspec)| | | |
|[timeliness](#tabletimelinessmonthlyrecurringchecksspec)|Monthly recurring of timeliness checks|[TableTimelinessMonthlyRecurringChecksSpec](#tabletimelinessmonthlyrecurringchecksspec)| | | |
|[accuracy](#tableaccuracymonthlyrecurringchecksspec)|Monthly recurring accuracy checks|[TableAccuracyMonthlyRecurringChecksSpec](#tableaccuracymonthlyrecurringchecksspec)| | | |
|[sql](#tablesqlmonthlyrecurringchecksspec)|Monthly recurring of custom SQL checks|[TableSqlMonthlyRecurringChecksSpec](#tablesqlmonthlyrecurringchecksspec)| | | |
|[availability](#tableavailabilitymonthlyrecurringchecksspec)|Daily partitioned availability checks|[TableAvailabilityMonthlyRecurringChecksSpec](#tableavailabilitymonthlyrecurringchecksspec)| | | |
|[schema](#tableschemamonthlyrecurringchecksspec)|Monthly recurring table schema checks|[TableSchemaMonthlyRecurringChecksSpec](#tableschemamonthlyrecurringchecksspec)| | | |
|[comparisons](#tablecomparisonmonthlyrecurringchecksspecmap)|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[TableComparisonMonthlyRecurringChecksSpecMap](#tablecomparisonmonthlyrecurringchecksspecmap)| | | |
|[custom](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)| | | |









___  

## TableTimelinessMonthlyRecurringChecksSpec  
Container of table level monthly recurring for timeliness data quality checks  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_data_freshness](\docs\checks\table\timeliness\data-freshness)|Monthly recurring calculating the number of days since the most recent event timestamp (freshness)|[TableDataFreshnessCheckSpec](\docs\checks\table\timeliness\data-freshness)| | | |
|[monthly_data_staleness](\docs\checks\table\timeliness\data-staleness)|Monthly recurring calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|[TableDataStalenessCheckSpec](\docs\checks\table\timeliness\data-staleness)| | | |
|[monthly_data_ingestion_delay](\docs\checks\table\timeliness\data-ingestion-delay)|Monthly recurring calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|[TableDataIngestionDelayCheckSpec](\docs\checks\table\timeliness\data-ingestion-delay)| | | |









___  

## TableAvailabilityMonthlyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are detecting the table availability.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_table_availability](\docs\checks\table\availability\table-availability)|Verifies availability on table in database using simple row count. Stores the most recent table availability status for each month when the data quality check was evaluated.|[TableAvailabilityCheckSpec](\docs\checks\table\availability\table-availability)| | | |









___  

## TableComparisonMonthlyRecurringChecksSpec  
Container of built-in comparison (accuracy) checks on a table level that are using a defined comparison to identify the reference table and the data grouping configuration.
 Contains the monthly recurring comparison checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_row_count_match](\docs\checks\table\comparisons\row-count-match)|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each month when the data quality check was evaluated.|[TableComparisonRowCountMatchCheckSpec](\docs\checks\table\comparisons\row-count-match)| | | |









___  

