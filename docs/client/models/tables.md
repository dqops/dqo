
## TableComparisonDailyMonitoringChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the daily monitoring comparison checks for each configured reference table.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|this||Map&lt;string, [TableComparisonDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tablecomparisondailymonitoringchecksspec)&gt;|


___  

## CustomCheckSpecMap  
Dictionary of custom checks indexed by a check name.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|this||Map&lt;string, [CustomCheckSpec](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspec)&gt;|
|this||Map&lt;string, [CustomCheckSpec](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspec)&gt;|


___  

## TableDailyMonitoringCheckCategoriesSpec  
Container of table level daily monitoring. Contains categories of daily monitoring.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[volume](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tablevolumedailymonitoringchecksspec)|Daily monitoring volume data quality checks|[TableVolumeDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tablevolumedailymonitoringchecksspec)|
|[timeliness](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tabletimelinessdailymonitoringchecksspec)|Daily monitoring timeliness checks|[TableTimelinessDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tabletimelinessdailymonitoringchecksspec)|
|[accuracy](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tableaccuracydailymonitoringchecksspec)|Daily monitoring accuracy checks|[TableAccuracyDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tableaccuracydailymonitoringchecksspec)|
|[sql](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tablesqldailymonitoringchecksspec)|Daily monitoring custom SQL checks|[TableSqlDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tablesqldailymonitoringchecksspec)|
|[availability](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tableavailabilitydailymonitoringchecksspec)|Daily monitoring table availability checks|[TableAvailabilityDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tableavailabilitydailymonitoringchecksspec)|
|[schema](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tableschemadailymonitoringchecksspec)|Daily monitoring table schema checks|[TableSchemaDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tableschemadailymonitoringchecksspec)|
|[comparisons](#tablecomparisondailymonitoringchecksspecmap)|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[TableComparisonDailyMonitoringChecksSpecMap](#tablecomparisondailymonitoringchecksspecmap)|
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#customcheckspecmap)|


___  

## TableComparisonDailyPartitionedChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the daily partitioned comparison checks for each configured reference table.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|this||Map&lt;string, [TableComparisonDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\table-daily-partitioned-checks\#tablecomparisondailypartitionedchecksspec)&gt;|


___  

## TableDailyPartitionedCheckCategoriesSpec  
Container of table level daily partitioned checks. Contains categories of daily partitioned checks.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[volume](\docs\reference\yaml\partitioned\table-daily-partitioned-checks\#tablevolumedailypartitionedchecksspec)|Volume daily partitioned data quality checks that verify the quality of every day of data separately|[TableVolumeDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\table-daily-partitioned-checks\#tablevolumedailypartitionedchecksspec)|
|[timeliness](\docs\reference\yaml\partitioned\table-daily-partitioned-checks\#tabletimelinessdailypartitionedchecksspec)|Daily partitioned timeliness checks|[TableTimelinessDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\table-daily-partitioned-checks\#tabletimelinessdailypartitionedchecksspec)|
|[sql](\docs\reference\yaml\partitioned\table-daily-partitioned-checks\#tablesqldailypartitionedchecksspec)|Custom SQL daily partitioned data quality checks that verify the quality of every day of data separately|[TableSqlDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\table-daily-partitioned-checks\#tablesqldailypartitionedchecksspec)|
|[comparisons](#tablecomparisondailypartitionedchecksspecmap)|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[TableComparisonDailyPartitionedChecksSpecMap](#tablecomparisondailypartitionedchecksspecmap)|
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#customcheckspecmap)|


___  

## ProfilingTimePeriod  
The time period for profiling checks (millisecond, daily, monthly, weekly, hourly).
 The default profiling check stores one value per month. When profiling checks is re-executed during the month,
 the previous profiling checks value is overwritten and only the most recent value is stored.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|one_per_week<br/>all_results<br/>one_per_hour<br/>one_per_month<br/>one_per_day<br/>|

___  

## TableListModel  
Table list model returned by the rest api that is limited only to the basic fields, excluding nested nodes.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|table_hash|Table hash that identifies the table using a unique hash code.|long|
|[target](\docs\client\models\columns\#physicaltablename)|Physical table details (a physical schema name and a physical table name).|[PhysicalTableName](\docs\client\models\columns\#physicaltablename)|
|disabled|Disables all data quality checks on the table. Data quality checks will not be executed.|boolean|
|stage|Stage name.|string|
|filter|SQL WHERE clause added to the sensor queries.|string|
|priority|Table priority (1, 2, 3, 4, ...). The tables could be assigned a priority level. The table priority is copied into each data quality check result and a sensor result, enabling efficient grouping of more and less important tables during a data quality improvement project, when the data quality issues on higher priority tables are fixed before data quality issues on less important tables.|integer|
|[owner](\docs\reference\yaml\tableyaml\#tableownerspec)|Table owner information like the data steward name or the business application name.|[TableOwnerSpec](\docs\reference\yaml\tableyaml\#tableownerspec)|
|[profiling_checks_result_truncation](#profilingtimeperiod)|Defines how many profiling checks results are stored for the table monthly. By default, DQOps will use the &#x27;one_per_month&#x27; configuration and store only the most recent profiling checks result executed during the month. By changing this value, it is possible to store one value per day or even store all profiling checks results.|[ProfilingTimePeriod](#profilingtimeperiod)|
|has_any_configured_checks|True when the table has any checks configured.|boolean|
|has_any_configured_profiling_checks|True when the table has any profiling checks configured.|boolean|
|has_any_configured_monitoring_checks|True when the table has any monitoring checks configured.|boolean|
|has_any_configured_partition_checks|True when the table has any partition checks configured.|boolean|
|partitioning_configuration_missing|True when the table has missing configuration of the &quot;partition_by_column&quot; column, making any partition checks fail when executed.|boolean|
|[run_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run all checks within this table.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|[run_profiling_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run profiling checks within this table.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|[run_monitoring_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run monitoring checks within this table.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|[run_partition_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run partition partitioned checks within this table.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|[collect_statistics_job_template](\docs\client\models\#statisticscollectorsearchfilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors within this table.|[StatisticsCollectorSearchFilters](\docs\client\models\#statisticscollectorsearchfilters)|
|[data_clean_job_template](\docs\client\models\#deletestoreddataqueuejobparameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this table.|[DeleteStoredDataQueueJobParameters](\docs\client\models\#deletestoreddataqueuejobparameters)|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean|
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean|
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean|


___  

## TableModel  
Full table model that returns the specification of a single table in the REST Api.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|table_hash|Table hash that identifies the table using a unique hash code.|long|
|[spec](\docs\reference\yaml\tableyaml\#tablespec)|Full table specification including all nested information, the table name is inside the &#x27;target&#x27; property.|[TableSpec](\docs\reference\yaml\tableyaml\#tablespec)|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|


___  

## TableComparisonMonthlyMonitoringChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the monthly monitoring comparison checks for each configured reference table.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|this||Map&lt;string, [TableComparisonMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tablecomparisonmonthlymonitoringchecksspec)&gt;|


___  

## TableMonthlyMonitoringCheckCategoriesSpec  
Container of table level monthly monitoring checks. Contains categories of monthly monitoring checks.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[volume](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tablevolumemonthlymonitoringchecksspec)|Monthly monitoring of volume data quality checks|[TableVolumeMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tablevolumemonthlymonitoringchecksspec)|
|[timeliness](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tabletimelinessmonthlymonitoringchecksspec)|Monthly monitoring of timeliness checks|[TableTimelinessMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tabletimelinessmonthlymonitoringchecksspec)|
|[accuracy](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tableaccuracymonthlymonitoringchecksspec)|Monthly monitoring accuracy checks|[TableAccuracyMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tableaccuracymonthlymonitoringchecksspec)|
|[sql](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tablesqlmonthlymonitoringchecksspec)|Monthly monitoring of custom SQL checks|[TableSqlMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tablesqlmonthlymonitoringchecksspec)|
|[availability](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tableavailabilitymonthlymonitoringchecksspec)|Daily partitioned availability checks|[TableAvailabilityMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tableavailabilitymonthlymonitoringchecksspec)|
|[schema](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tableschemamonthlymonitoringchecksspec)|Monthly monitoring table schema checks|[TableSchemaMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tableschemamonthlymonitoringchecksspec)|
|[comparisons](#tablecomparisonmonthlymonitoringchecksspecmap)|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[TableComparisonMonthlyMonitoringChecksSpecMap](#tablecomparisonmonthlymonitoringchecksspecmap)|
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#customcheckspecmap)|


___  

## TableComparisonMonthlyPartitionedChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the monthly partitioned comparison checks for each configured reference table.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|this||Map&lt;string, [TableComparisonMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\table-monthly-partitioned-checks\#tablecomparisonmonthlypartitionedchecksspec)&gt;|


___  

## TableMonthlyPartitionedCheckCategoriesSpec  
Container of table level monthly partitioned checks. Contains categories of monthly partitioned checks.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[volume](\docs\reference\yaml\partitioned\table-monthly-partitioned-checks\#tablevolumemonthlypartitionedchecksspec)|Volume monthly partitioned data quality checks that verify the quality of every month of data separately|[TableVolumeMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\table-monthly-partitioned-checks\#tablevolumemonthlypartitionedchecksspec)|
|[timeliness](\docs\reference\yaml\partitioned\table-monthly-partitioned-checks\#tabletimelinessmonthlypartitionedchecksspec)|Monthly partitioned timeliness checks|[TableTimelinessMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\table-monthly-partitioned-checks\#tabletimelinessmonthlypartitionedchecksspec)|
|[sql](\docs\reference\yaml\partitioned\table-monthly-partitioned-checks\#tablesqlmonthlypartitionedchecksspec)|Custom SQL monthly partitioned data quality checks that verify the quality of every month of data separately|[TableSqlMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\table-monthly-partitioned-checks\#tablesqlmonthlypartitionedchecksspec)|
|[comparisons](#tablecomparisonmonthlypartitionedchecksspecmap)|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[TableComparisonMonthlyPartitionedChecksSpecMap](#tablecomparisonmonthlypartitionedchecksspecmap)|
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#customcheckspecmap)|


___  

## TablePartitioningModel  
Rest model that returns the configuration of table partitioning information.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|[target](\docs\client\models\columns\#physicaltablename)|Physical table details (a physical schema name and a physical table name)|[PhysicalTableName](\docs\client\models\columns\#physicaltablename)|
|[timestamp_columns](\docs\reference\yaml\tableyaml\#timestampcolumnsspec)|Column names that store the timestamps that identify the event (transaction) timestamp and the ingestion (inserted / loaded at) timestamps. Also configures the timestamp source for the date/time partitioned data quality checks (event timestamp or ingestion timestamp).|[TimestampColumnsSpec](\docs\reference\yaml\tableyaml\#timestampcolumnsspec)|
|[incremental_time_window](\docs\reference\yaml\tableyaml\#partitionincrementaltimewindowspec)|Configuration of time windows for executing partition checks incrementally, configures the number of recent days to analyze for daily partitioned tables or the number of recent months for monthly partitioned data.|[PartitionIncrementalTimeWindowSpec](\docs\reference\yaml\tableyaml\#partitionincrementaltimewindowspec)|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|


___  

## TableComparisonProfilingChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|this||Map&lt;string, [TableComparisonProfilingChecksSpec](\docs\reference\yaml\profiling\table-profiling-checks\#tablecomparisonprofilingchecksspec)&gt;|


___  

## TableProfilingCheckCategoriesSpec  
Container of table level checks that are activated on a table level.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[result_truncation](\docs\client\models\tables\#profilingtimeperiod)|Defines how many profiling checks results are stored for the table monthly. By default, DQOps will use the &#x27;one_per_month&#x27; configuration and store only the most recent profiling checks result executed during the month. By changing this value, it is possible to store one value per day or even store all profiling checks results.|[ProfilingTimePeriod](\docs\client\models\tables\#profilingtimeperiod)|
|[volume](\docs\reference\yaml\profiling\table-profiling-checks\#tablevolumeprofilingchecksspec)|Configuration of volume data quality checks on a table level.|[TableVolumeProfilingChecksSpec](\docs\reference\yaml\profiling\table-profiling-checks\#tablevolumeprofilingchecksspec)|
|[timeliness](\docs\reference\yaml\profiling\table-profiling-checks\#tabletimelinessprofilingchecksspec)|Configuration of timeliness checks on a table level. Timeliness checks detect anomalies like rapid row count changes.|[TableTimelinessProfilingChecksSpec](\docs\reference\yaml\profiling\table-profiling-checks\#tabletimelinessprofilingchecksspec)|
|[accuracy](\docs\reference\yaml\profiling\table-profiling-checks\#tableaccuracyprofilingchecksspec)|Configuration of accuracy checks on a table level. Accuracy checks compare the tested table with another reference table.|[TableAccuracyProfilingChecksSpec](\docs\reference\yaml\profiling\table-profiling-checks\#tableaccuracyprofilingchecksspec)|
|[sql](\docs\reference\yaml\profiling\table-profiling-checks\#tablesqlprofilingchecksspec)|Configuration of data quality checks that are evaluating custom SQL conditions and aggregated expressions.|[TableSqlProfilingChecksSpec](\docs\reference\yaml\profiling\table-profiling-checks\#tablesqlprofilingchecksspec)|
|[availability](\docs\reference\yaml\profiling\table-profiling-checks\#tableavailabilityprofilingchecksspec)|Configuration of the table availability data quality checks on a table level.|[TableAvailabilityProfilingChecksSpec](\docs\reference\yaml\profiling\table-profiling-checks\#tableavailabilityprofilingchecksspec)|
|[schema](\docs\reference\yaml\profiling\table-profiling-checks\#tableschemaprofilingchecksspec)|Configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaProfilingChecksSpec](\docs\reference\yaml\profiling\table-profiling-checks\#tableschemaprofilingchecksspec)|
|[comparisons](#tablecomparisonprofilingchecksspecmap)|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[TableComparisonProfilingChecksSpecMap](#tablecomparisonprofilingchecksspecmap)|
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#customcheckspecmap)|


___  

## TableStatisticsModel  
Model that returns a summary of the table level statistics (the basic profiling results).  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|[table](\docs\client\models\columns\#physicaltablename)|Physical table name including the schema and table names.|[PhysicalTableName](\docs\client\models\columns\#physicaltablename)|
|[collect_table_statistics_job_template](\docs\client\models\#statisticscollectorsearchfilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors within this table, limited only to the table level statistics (row count, etc).|[StatisticsCollectorSearchFilters](\docs\client\models\#statisticscollectorsearchfilters)|
|[collect_table_and_column_statistics_job_template](\docs\client\models\#statisticscollectorsearchfilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors within this table, including statistics for all columns.|[StatisticsCollectorSearchFilters](\docs\client\models\#statisticscollectorsearchfilters)|
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean|


___  

