
## TableDailyRecurringCheckCategoriesSpec  
Container of table level daily recurring. Contains categories of daily recurring.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](#tablevolumedailyrecurringchecksspec)|Daily recurring volume data quality checks|[TableVolumeDailyRecurringChecksSpec](#tablevolumedailyrecurringchecksspec)| | | |
|[timeliness](#tabletimelinessdailyrecurringchecksspec)|Daily recurring timeliness checks|[TableTimelinessDailyRecurringChecksSpec](#tabletimelinessdailyrecurringchecksspec)| | | |
|[accuracy](#tableaccuracydailyrecurringchecksspec)|Daily recurring accuracy checks|[TableAccuracyDailyRecurringChecksSpec](#tableaccuracydailyrecurringchecksspec)| | | |
|[sql](#tablesqldailyrecurringchecksspec)|Daily recurring custom SQL checks|[TableSqlDailyRecurringChecksSpec](#tablesqldailyrecurringchecksspec)| | | |
|[availability](#tableavailabilitydailyrecurringchecksspec)|Daily recurring table availability checks|[TableAvailabilityDailyRecurringChecksSpec](#tableavailabilitydailyrecurringchecksspec)| | | |
|[schema](#tableschemadailyrecurringchecksspec)|Daily recurring table schema checks|[TableSchemaDailyRecurringChecksSpec](#tableschemadailyrecurringchecksspec)| | | |
|[comparisons](#tablecomparisondailyrecurringchecksspecmap)|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[TableComparisonDailyRecurringChecksSpecMap](#tablecomparisondailyrecurringchecksspecmap)| | | |
|[custom](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)| | | |









___  

## TableComparisonDailyRecurringChecksSpec  
Container of built-in comparison (accuracy) checks on a table level that are using a defined comparison to identify the reference table and the data grouping configuration.
 Contains the daily recurring comparison checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_row_count_match](\docs\checks\table\comparisons\row-count-match)|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each day when the data quality check was evaluated.|[TableComparisonRowCountMatchCheckSpec](\docs\checks\table\comparisons\row-count-match)| | | |









___  

## TableTimelinessDailyRecurringChecksSpec  
Container of table level daily recurring for timeliness data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_data_freshness](\docs\checks\table\timeliness\data-freshness)|Daily  calculating the number of days since the most recent event timestamp (freshness)|[TableDataFreshnessCheckSpec](\docs\checks\table\timeliness\data-freshness)| | | |
|[daily_data_staleness](\docs\checks\table\timeliness\data-staleness)|Daily  calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|[TableDataStalenessCheckSpec](\docs\checks\table\timeliness\data-staleness)| | | |
|[daily_data_ingestion_delay](\docs\checks\table\timeliness\data-ingestion-delay)|Daily  calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|[TableDataIngestionDelayCheckSpec](\docs\checks\table\timeliness\data-ingestion-delay)| | | |









___  

## TableAccuracyDailyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are verifying the accuracy of the table, comparing it with another reference table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_total_row_count_match_percent](\docs\checks\table\accuracy\total-row-count-match-percent)|Verifies the total ow count of a tested table and compares it to a row count of a reference table. Stores the most recent captured value for each day when the data quality check was evaluated.|[TableAccuracyTotalRowCountMatchPercentCheckSpec](\docs\checks\table\accuracy\total-row-count-match-percent)| | | |









___  

## TableSchemaDailyRecurringChecksSpec  
Container of built-in preconfigured volume data quality checks on a table level that are executed as a daily recurring (checkpoint) checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_column_count](\docs\checks\table\schema\column-count)|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each day when the data quality check was evaluated.|[TableSchemaColumnCountCheckSpec](\docs\checks\table\schema\column-count)| | | |
|[daily_column_count_changed](\docs\checks\table\schema\column-count-changed)|Detects if the count of columns has changed since the most recent day. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each day when the data quality check was evaluated.|[TableSchemaColumnCountChangedCheckSpec](\docs\checks\table\schema\column-count-changed)| | | |
|[daily_column_list_changed](\docs\checks\table\schema\column-list-changed)|Detects if new columns were added or existing columns were removed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|[TableSchemaColumnListChangedCheckSpec](\docs\checks\table\schema\column-list-changed)| | | |
|[daily_column_list_or_order_changed](\docs\checks\table\schema\column-list-or-order-changed)|Detects if new columns were added, existing columns were removed or the columns were reordered since the most recent day. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|[TableSchemaColumnListOrOrderChangedCheckSpec](\docs\checks\table\schema\column-list-or-order-changed)| | | |
|[daily_column_types_changed](\docs\checks\table\schema\column-types-changed)|Detects if new columns were added, removed or their data types have changed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|[TableSchemaColumnTypesChangedCheckSpec](\docs\checks\table\schema\column-types-changed)| | | |









___  

## TableComparisonDailyRecurringChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the daily recurring comparison checks for each configured reference table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|access_order||boolean| | | |
|size||integer| | | |
|mod_count||integer| | | |
|threshold||integer| | | |









___  

## TableSqlDailyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_sql_condition_passed_percent_on_table](\docs\checks\table\sql\sql-condition-passed-percent-on-table)|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|[TableSqlConditionPassedPercentCheckSpec](\docs\checks\table\sql\sql-condition-passed-percent-on-table)| | | |
|[daily_sql_condition_failed_count_on_table](\docs\checks\table\sql\sql-condition-failed-count-on-table)|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|[TableSqlConditionFailedCountCheckSpec](\docs\checks\table\sql\sql-condition-failed-count-on-table)| | | |
|[daily_sql_aggregate_expr_table](\docs\checks\table\sql\sql-aggregate-expr-table)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[TableSqlAggregateExprCheckSpec](\docs\checks\table\sql\sql-aggregate-expr-table)| | | |









___  

## TableAvailabilityDailyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are detecting the table availability.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_table_availability](\docs\checks\table\availability\table-availability)|Verifies availability on table in database using simple row count. Stores the most recent table availability status for each day when the data quality check was evaluated.|[TableAvailabilityCheckSpec](\docs\checks\table\availability\table-availability)| | | |









___  

## TableVolumeDailyRecurringChecksSpec  
Container of table level daily recurring for volume data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_row_count](\docs\checks\table\volume\row-count)|Verifies that the number of rows in a table does not exceed the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[TableRowCountCheckSpec](\docs\checks\table\volume\row-count)| | | |
|[daily_row_count_anomaly_differencing_30_days](\docs\checks\table\volume\row-count-anomaly-differencing-30-days)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.|[TableAnomalyDifferencingRowCount30DaysCheckSpec](\docs\checks\table\volume\row-count-anomaly-differencing-30-days)| | | |
|[daily_row_count_anomaly_differencing](\docs\checks\table\volume\row-count-anomaly-differencing)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.|[TableAnomalyDifferencingRowCountCheckSpec](\docs\checks\table\volume\row-count-anomaly-differencing)| | | |
|[daily_row_count_change](\docs\checks\table\volume\row-count-change)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|[TableChangeRowCountCheckSpec](\docs\checks\table\volume\row-count-change)| | | |
|[daily_row_count_change_yesterday](\docs\checks\table\volume\row-count-change-yesterday)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|[TableChangeRowCountSinceYesterdayCheckSpec](\docs\checks\table\volume\row-count-change-yesterday)| | | |
|[daily_row_count_change_7_days](\docs\checks\table\volume\row-count-change-7-days)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|[TableChangeRowCountSince7DaysCheckSpec](\docs\checks\table\volume\row-count-change-7-days)| | | |
|[daily_row_count_change_30_days](\docs\checks\table\volume\row-count-change-30-days)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|[TableChangeRowCountSince30DaysCheckSpec](\docs\checks\table\volume\row-count-change-30-days)| | | |









___  

