
## TableMonthlyPartitionedCheckCategoriesSpec  
Container of table level monthly partitioned checks. Contains categories of monthly partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](#tablevolumemonthlypartitionedchecksspec)|Volume monthly partitioned data quality checks that verify the quality of every month of data separately|[TableVolumeMonthlyPartitionedChecksSpec](#tablevolumemonthlypartitionedchecksspec)| | | |
|[timeliness](#tabletimelinessmonthlypartitionedchecksspec)|Monthly partitioned timeliness checks|[TableTimelinessMonthlyPartitionedChecksSpec](#tabletimelinessmonthlypartitionedchecksspec)| | | |
|[sql](#tablesqlmonthlypartitionedchecksspec)|Custom SQL monthly partitioned data quality checks that verify the quality of every month of data separately|[TableSqlMonthlyPartitionedChecksSpec](#tablesqlmonthlypartitionedchecksspec)| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## TableVolumeMonthlyPartitionedChecksSpec  
Container of table level monthly partitioned volume data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_min_row_count](#tablerowcountcheckspec)|Verifies that the number of rows in a table does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[TableRowCountCheckSpec](#tablerowcountcheckspec)| | | |
|[monthly_partition_row_count_change](#tablechangerowcountcheckspec)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|[TableChangeRowCountCheckSpec](#tablechangerowcountcheckspec)| | | |









___  

## TableTimelinessMonthlyPartitionedChecksSpec  
Container of table level monthly partitioned timeliness data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_days_since_most_recent_event](#tabledayssincemostrecenteventcheckspec)|Monthly partitioned check calculating the number of days since the most recent event (freshness)|[TableDaysSinceMostRecentEventCheckSpec](#tabledayssincemostrecenteventcheckspec)| | | |
|[monthly_partition_data_ingestion_delay](#tabledataingestiondelaycheckspec)|Monthly partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|[TableDataIngestionDelayCheckSpec](#tabledataingestiondelaycheckspec)| | | |
|[monthly_partition_days_since_most_recent_ingestion](#tabledayssincemostrecentingestioncheckspec)|Monthly partitioned check calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)|[TableDaysSinceMostRecentIngestionCheckSpec](#tabledayssincemostrecentingestioncheckspec)| | | |
|[monthly_partition_reload_lag](#tablepartitionreloadlagcheckspec)|Monthly partitioned check calculating the longest time a row waited to be load|[TablePartitionReloadLagCheckSpec](#tablepartitionreloadlagcheckspec)| | | |









___  

## TableSqlMonthlyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_sql_condition_passed_percent_on_table](#tablesqlconditionpassedpercentcheckspec)|Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|[TableSqlConditionPassedPercentCheckSpec](#tablesqlconditionpassedpercentcheckspec)| | | |
|[monthly_partition_sql_condition_failed_count_on_table](#tablesqlconditionfailedcountcheckspec)|Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|[TableSqlConditionFailedCountCheckSpec](#tablesqlconditionfailedcountcheckspec)| | | |
|[monthly_partition_sql_aggregate_expr_table](#tablesqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[TableSqlAggregateExprCheckSpec](#tablesqlaggregateexprcheckspec)| | | |









___  

