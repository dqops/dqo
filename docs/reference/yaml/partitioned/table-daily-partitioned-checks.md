
## TableDailyPartitionedCheckCategoriesSpec  
Container of table level daily partitioned checks. Contains categories of daily partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](#tablevolumedailypartitionedchecksspec)|Volume daily partitioned data quality checks that verify the quality of every day of data separately|[TableVolumeDailyPartitionedChecksSpec](#tablevolumedailypartitionedchecksspec)| | | |
|[timeliness](#tabletimelinessdailypartitionedchecksspec)|Daily partitioned timeliness checks|[TableTimelinessDailyPartitionedChecksSpec](#tabletimelinessdailypartitionedchecksspec)| | | |
|[sql](#tablesqldailypartitionedchecksspec)|Custom SQL daily partitioned data quality checks that verify the quality of every day of data separately|[TableSqlDailyPartitionedChecksSpec](#tablesqldailypartitionedchecksspec)| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## TableVolumeDailyPartitionedChecksSpec  
Container of table level date partitioned volume data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_row_count](#tablerowcountcheckspec)|Verifies that the number of rows in a table does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[TableRowCountCheckSpec](#tablerowcountcheckspec)| | | |
|[daily_partition_row_count_anomaly_7_days](#tableanomalyrowcount7dayscheckspec)|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 7 days.|[TableAnomalyRowCount7DaysCheckSpec](#tableanomalyrowcount7dayscheckspec)| | | |
|[daily_partition_row_count_anomaly_30_days](#tableanomalyrowcount30dayscheckspec)|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 30 days.|[TableAnomalyRowCount30DaysCheckSpec](#tableanomalyrowcount30dayscheckspec)| | | |
|[daily_partition_row_count_anomaly_60_days](#tableanomalyrowcount60dayscheckspec)|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 60 days.|[TableAnomalyRowCount60DaysCheckSpec](#tableanomalyrowcount60dayscheckspec)| | | |
|[daily_partition_row_count_change](#tablechangerowcountcheckspec)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|[TableChangeRowCountCheckSpec](#tablechangerowcountcheckspec)| | | |
|[daily_partition_row_count_change_yesterday](#tablechangerowcountsinceyesterdaycheckspec)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|[TableChangeRowCountSinceYesterdayCheckSpec](#tablechangerowcountsinceyesterdaycheckspec)| | | |
|[daily_partition_row_count_change_7_days](#tablechangerowcountsince7dayscheckspec)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|[TableChangeRowCountSince7DaysCheckSpec](#tablechangerowcountsince7dayscheckspec)| | | |
|[daily_partition_row_count_change_30_days](#tablechangerowcountsince30dayscheckspec)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|[TableChangeRowCountSince30DaysCheckSpec](#tablechangerowcountsince30dayscheckspec)| | | |









___  

## TableAnomalyRowCount7DaysCheckSpec  
Table level check that ensures that the row count is within a two-tailed percentile from measurements made during the last 7 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablevolumerowcountsensorparametersspec)|Data quality check parameters|[TableVolumeRowCountSensorParametersSpec](#tablevolumerowcountsensorparametersspec)| | | |
|[warning](#percentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[PercentileMovingWithin7DaysRuleParametersSpec](#percentilemovingwithin7daysruleparametersspec)| | | |
|[error](#percentilemovingwithin7daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[PercentileMovingWithin7DaysRuleParametersSpec](#percentilemovingwithin7daysruleparametersspec)| | | |
|[fatal](#percentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[PercentileMovingWithin7DaysRuleParametersSpec](#percentilemovingwithin7daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## PercentileMovingWithin7DaysRuleParametersSpec  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_within|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days, etc.) time window, but at least 3 readouts must exist to run the calculation.|double| | |95<br/>|









___  

## TableAnomalyRowCount30DaysCheckSpec  
Table level check that ensures that the row count is within a two-tailed percentile from measurements made during the last 30 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablevolumerowcountsensorparametersspec)|Data quality check parameters|[TableVolumeRowCountSensorParametersSpec](#tablevolumerowcountsensorparametersspec)| | | |
|[warning](#percentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[PercentileMovingWithin30DaysRuleParametersSpec](#percentilemovingwithin30daysruleparametersspec)| | | |
|[error](#percentilemovingwithin30daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[PercentileMovingWithin30DaysRuleParametersSpec](#percentilemovingwithin30daysruleparametersspec)| | | |
|[fatal](#percentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[PercentileMovingWithin30DaysRuleParametersSpec](#percentilemovingwithin30daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## PercentileMovingWithin30DaysRuleParametersSpec  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_within|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|double| | |95<br/>|









___  

## TableAnomalyRowCount60DaysCheckSpec  
Table level check that ensures that the row count is within a two-tailed percentile from measurements made during the last 60 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablevolumerowcountsensorparametersspec)|Data quality check parameters|[TableVolumeRowCountSensorParametersSpec](#tablevolumerowcountsensorparametersspec)| | | |
|[warning](#percentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[PercentileMovingWithin60DaysRuleParametersSpec](#percentilemovingwithin60daysruleparametersspec)| | | |
|[error](#percentilemovingwithin60daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[PercentileMovingWithin60DaysRuleParametersSpec](#percentilemovingwithin60daysruleparametersspec)| | | |
|[fatal](#percentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[PercentileMovingWithin60DaysRuleParametersSpec](#percentilemovingwithin60daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## PercentileMovingWithin60DaysRuleParametersSpec  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_within|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.) time window, but at least 20 readouts must exist to run the calculation.|double| | |95<br/>|









___  

## TableTimelinessDailyPartitionedChecksSpec  
Container of table level date partitioned timeliness data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_days_since_most_recent_event](#tabledayssincemostrecenteventcheckspec)|Daily partitioned check calculating the number of days since the most recent event timestamp (freshness)|[TableDaysSinceMostRecentEventCheckSpec](#tabledayssincemostrecenteventcheckspec)| | | |
|[daily_partition_data_ingestion_delay](#tabledataingestiondelaycheckspec)|Daily partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|[TableDataIngestionDelayCheckSpec](#tabledataingestiondelaycheckspec)| | | |
|[daily_partition_days_since_most_recent_ingestion](#tabledayssincemostrecentingestioncheckspec)|Daily partitioned check calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|[TableDaysSinceMostRecentIngestionCheckSpec](#tabledayssincemostrecentingestioncheckspec)| | | |
|[daily_partition_reload_lag](#tablepartitionreloadlagcheckspec)|Daily partitioned check calculating the longest time a row waited to be load|[TablePartitionReloadLagCheckSpec](#tablepartitionreloadlagcheckspec)| | | |









___  

## TablePartitionReloadLagCheckSpec  
Table level check that calculates maximum difference in days between ingestion timestamp and event timestamp rows.
 This check should be executed only as a partitioned check because this check finds the longest delay between the time that the row was created
 in the data source and the timestamp when the row was loaded into its daily or monthly partition.
 This check will detect that a daily or monthly partition was reloaded, setting also the most recent timestamps in the created_at, loaded_at, inserted_at or other similar columns
 filled by the data pipeline or an ETL process during data loading.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tabletimelinesspartitionreloadlagsensorparametersspec)|Partition reload lag sensor parameters|[TableTimelinessPartitionReloadLagSensorParametersSpec](#tabletimelinesspartitionreloadlagsensorparametersspec)| | | |
|[warning](#maxdaysrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDaysRule1ParametersSpec](#maxdaysrule1parametersspec)| | | |
|[error](#maxdaysrule2parametersspec)|Default alerting threshold for partition reload lag that raises a data quality error (alert)|[MaxDaysRule2ParametersSpec](#maxdaysrule2parametersspec)| | | |
|[fatal](#maxdaysrule7parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxDaysRule7ParametersSpec](#maxdaysrule7parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableTimelinessPartitionReloadLagSensorParametersSpec  
Table sensor that runs a query calculating maximum difference in days between ingestion timestamp and event timestamp rows.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## TableSqlDailyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_sql_condition_passed_percent_on_table](#tablesqlconditionpassedpercentcheckspec)|Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|[TableSqlConditionPassedPercentCheckSpec](#tablesqlconditionpassedpercentcheckspec)| | | |
|[daily_partition_sql_condition_failed_count_on_table](#tablesqlconditionfailedcountcheckspec)|Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|[TableSqlConditionFailedCountCheckSpec](#tablesqlconditionfailedcountcheckspec)| | | |
|[daily_partition_sql_aggregate_expr_table](#tablesqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[TableSqlAggregateExprCheckSpec](#tablesqlaggregateexprcheckspec)| | | |









___  

