
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
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## TableVolumeMonthlyRecurringChecksSpec  
Container of table level monthly recurring for volume data quality checks  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_row_count](#tablerowcountcheckspec)|Verifies that the number of rows in a table does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[TableRowCountCheckSpec](#tablerowcountcheckspec)| | | |
|[monthly_row_count_change](#tablechangerowcountcheckspec)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|[TableChangeRowCountCheckSpec](#tablechangerowcountcheckspec)| | | |









___  

## TableTimelinessMonthlyRecurringChecksSpec  
Container of table level monthly recurring for timeliness data quality checks  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_days_since_most_recent_event](#tabledayssincemostrecenteventcheckspec)|Monthly recurring calculating the number of days since the most recent event timestamp (freshness)|[TableDaysSinceMostRecentEventCheckSpec](#tabledayssincemostrecenteventcheckspec)| | | |
|[monthly_data_ingestion_delay](#tabledataingestiondelaycheckspec)|Monthly recurring calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|[TableDataIngestionDelayCheckSpec](#tabledataingestiondelaycheckspec)| | | |
|[monthly_days_since_most_recent_ingestion](#tabledayssincemostrecentingestioncheckspec)|Monthly recurring calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|[TableDaysSinceMostRecentIngestionCheckSpec](#tabledayssincemostrecentingestioncheckspec)| | | |









___  

## TableAccuracyMonthlyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are verifying the accuracy of the table, comparing it with another reference table, on a monthly basis.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_row_count_match_percent](#tableaccuracyrowcountmatchpercentcheckspec)|Verifies the row count of a tested table and compares it to a row count of a reference table. Stores the most recent row count for each month when the data quality check was evaluated.|[TableAccuracyRowCountMatchPercentCheckSpec](#tableaccuracyrowcountmatchpercentcheckspec)| | | |









___  

## TableSqlMonthlyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_sql_condition_passed_percent_on_table](#tablesqlconditionpassedpercentcheckspec)|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|[TableSqlConditionPassedPercentCheckSpec](#tablesqlconditionpassedpercentcheckspec)| | | |
|[monthly_sql_condition_failed_count_on_table](#tablesqlconditionfailedcountcheckspec)|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|[TableSqlConditionFailedCountCheckSpec](#tablesqlconditionfailedcountcheckspec)| | | |
|[monthly_sql_aggregate_expr_table](#tablesqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[TableSqlAggregateExprCheckSpec](#tablesqlaggregateexprcheckspec)| | | |









___  

## TableAvailabilityMonthlyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are detecting the table availability.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_table_availability](#tableavailabilitycheckspec)|Verifies availability on table in database using simple row count. Stores the most recent table availability status for each month when the data quality check was evaluated.|[TableAvailabilityCheckSpec](#tableavailabilitycheckspec)| | | |









___  

## TableSchemaMonthlyRecurringChecksSpec  
Container of built-in preconfigured volume data quality checks on a table level that are executed as a monthly recurring (checkpoint) checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_column_count](#tableschemacolumncountcheckspec)|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each month when the data quality check was evaluated.|[TableSchemaColumnCountCheckSpec](#tableschemacolumncountcheckspec)| | | |
|[monthly_column_count_changed](#tableschemacolumncountchangedcheckspec)|Detects if the count of columns has changed since the last month. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each month when the data quality check was evaluated.|[TableSchemaColumnCountChangedCheckSpec](#tableschemacolumncountchangedcheckspec)| | | |
|[monthly_column_list_changed](#tableschemacolumnlistchangedcheckspec)|Detects if new columns were added or existing columns were removed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|[TableSchemaColumnListChangedCheckSpec](#tableschemacolumnlistchangedcheckspec)| | | |
|[monthly_column_list_or_order_changed](#tableschemacolumnlistororderchangedcheckspec)|Detects if new columns were added, existing columns were removed or the columns were reordered since the last month. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|[TableSchemaColumnListOrOrderChangedCheckSpec](#tableschemacolumnlistororderchangedcheckspec)| | | |
|[monthly_column_types_changed](#tableschemacolumntypeschangedcheckspec)|Detects if new columns were added, removed or their data types have changed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|[TableSchemaColumnTypesChangedCheckSpec](#tableschemacolumntypeschangedcheckspec)| | | |









___  

