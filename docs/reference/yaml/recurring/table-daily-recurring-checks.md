
## TableDailyRecurringCategoriesSpec  
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
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## TableVolumeDailyRecurringChecksSpec  
Container of table level daily recurring for volume data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_row_count](#tablerowcountcheckspec)|Verifies that the number of rows in a table does not exceed the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[TableRowCountCheckSpec](#tablerowcountcheckspec)| | | |
|[daily_row_count_anomaly_7_days](#tableanomalyrowcountchange7dayscheckspec)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 7 days.|[TableAnomalyRowCountChange7DaysCheckSpec](#tableanomalyrowcountchange7dayscheckspec)| | | |
|[daily_row_count_anomaly_30_days](#tableanomalyrowcountchange30dayscheckspec)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.|[TableAnomalyRowCountChange30DaysCheckSpec](#tableanomalyrowcountchange30dayscheckspec)| | | |
|[daily_row_count_anomaly_60_days](#tableanomalyrowcountchange60dayscheckspec)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 60 days.|[TableAnomalyRowCountChange60DaysCheckSpec](#tableanomalyrowcountchange60dayscheckspec)| | | |
|[daily_row_count_change](#tablechangerowcountcheckspec)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|[TableChangeRowCountCheckSpec](#tablechangerowcountcheckspec)| | | |
|[daily_row_count_change_yesterday](#tablechangerowcountsinceyesterdaycheckspec)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|[TableChangeRowCountSinceYesterdayCheckSpec](#tablechangerowcountsinceyesterdaycheckspec)| | | |
|[daily_row_count_change_7_days](#tablechangerowcountsince7dayscheckspec)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|[TableChangeRowCountSince7DaysCheckSpec](#tablechangerowcountsince7dayscheckspec)| | | |
|[daily_row_count_change_30_days](#tablechangerowcountsince30dayscheckspec)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|[TableChangeRowCountSince30DaysCheckSpec](#tablechangerowcountsince30dayscheckspec)| | | |









___  

## TableTimelinessDailyRecurringChecksSpec  
Container of table level daily recurring for timeliness data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_days_since_most_recent_event](#tabledayssincemostrecenteventcheckspec)|Daily  calculating the number of days since the most recent event timestamp (freshness)|[TableDaysSinceMostRecentEventCheckSpec](#tabledayssincemostrecenteventcheckspec)| | | |
|[daily_data_ingestion_delay](#tabledataingestiondelaycheckspec)|Daily  calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|[TableDataIngestionDelayCheckSpec](#tabledataingestiondelaycheckspec)| | | |
|[daily_days_since_most_recent_ingestion](#tabledayssincemostrecentingestioncheckspec)|Daily  calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|[TableDaysSinceMostRecentIngestionCheckSpec](#tabledayssincemostrecentingestioncheckspec)| | | |









___  

## TableAccuracyDailyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are verifying the accuracy of the table, comparing it with another reference table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_row_count_match_percent](#tableaccuracyrowcountmatchpercentcheckspec)|Verifies the row count of a tested table and compares it to a row count of a reference table. Stores the most recent captured value for each day when the data quality check was evaluated.|[TableAccuracyRowCountMatchPercentCheckSpec](#tableaccuracyrowcountmatchpercentcheckspec)| | | |









___  

## TableSqlDailyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_sql_condition_passed_percent_on_table](#tablesqlconditionpassedpercentcheckspec)|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|[TableSqlConditionPassedPercentCheckSpec](#tablesqlconditionpassedpercentcheckspec)| | | |
|[daily_sql_condition_failed_count_on_table](#tablesqlconditionfailedcountcheckspec)|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|[TableSqlConditionFailedCountCheckSpec](#tablesqlconditionfailedcountcheckspec)| | | |
|[daily_sql_aggregate_expr_table](#tablesqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[TableSqlAggregateExprCheckSpec](#tablesqlaggregateexprcheckspec)| | | |









___  

## TableAvailabilityDailyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are detecting the table availability.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_table_availability](#tableavailabilitycheckspec)|Verifies availability on table in database using simple row count. Stores the most recent table availability status for each day when the data quality check was evaluated.|[TableAvailabilityCheckSpec](#tableavailabilitycheckspec)| | | |









___  

## TableSchemaDailyRecurringChecksSpec  
Container of built-in preconfigured volume data quality checks on a table level that are executed as a daily recurring (checkpoint) checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_column_count](#tableschemacolumncountcheckspec)|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each day when the data quality check was evaluated.|[TableSchemaColumnCountCheckSpec](#tableschemacolumncountcheckspec)| | | |
|[daily_column_count_changed](#tableschemacolumncountchangedcheckspec)|Detects if the count of columns has changed since the most recent day. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each day when the data quality check was evaluated.|[TableSchemaColumnCountChangedCheckSpec](#tableschemacolumncountchangedcheckspec)| | | |
|[daily_column_list_changed](#tableschemacolumnlistchangedcheckspec)|Detects if new columns were added or existing columns were removed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|[TableSchemaColumnListChangedCheckSpec](#tableschemacolumnlistchangedcheckspec)| | | |
|[daily_column_list_or_order_changed](#tableschemacolumnlistororderchangedcheckspec)|Detects if new columns were added, existing columns were removed or the columns were reordered since the most recent day. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|[TableSchemaColumnListOrOrderChangedCheckSpec](#tableschemacolumnlistororderchangedcheckspec)| | | |
|[daily_column_types_changed](#tableschemacolumntypeschangedcheckspec)|Detects if new columns were added, removed or their data types have changed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|[TableSchemaColumnTypesChangedCheckSpec](#tableschemacolumntypeschangedcheckspec)| | | |









___  

