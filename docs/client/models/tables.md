# DQOps REST API tables models reference
The references of all objects used by [tables](../operations/tables.md) REST API operations are listed below.


## TableComparisonDailyMonitoringChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the daily monitoring comparison checks for each configured reference table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|self|:mm|Dict[string, [TableComparisonDailyMonitoringChecksSpec](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tablecomparisondailymonitoringchecksspec)]|


___

## CustomCheckSpecMap
Dictionary of custom checks indexed by a check name.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|self|:mm|Dict[string, [CustomCheckSpec](../../reference/yaml/profiling/table-profiling-checks.md#customcheckspec)]|
|self|:mm|Dict[string, [CustomCheckSpec](../../reference/yaml/profiling/table-profiling-checks.md#customcheckspec)]|


___

## TableDailyMonitoringCheckCategoriesSpec
Container of table level daily monitoring. Contains categories of daily monitoring.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[volume](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tablevolumedailymonitoringchecksspec)|Daily monitoring volume data quality checks|[TableVolumeDailyMonitoringChecksSpec](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tablevolumedailymonitoringchecksspec)|
|[timeliness](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tabletimelinessdailymonitoringchecksspec)|Daily monitoring timeliness checks|[TableTimelinessDailyMonitoringChecksSpec](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tabletimelinessdailymonitoringchecksspec)|
|[accuracy](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tableaccuracydailymonitoringchecksspec)|Daily monitoring accuracy checks|[TableAccuracyDailyMonitoringChecksSpec](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tableaccuracydailymonitoringchecksspec)|
|[custom_sql](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tablecustomsqldailymonitoringchecksspec)|Daily monitoring custom SQL checks|[TableCustomSqlDailyMonitoringChecksSpec](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tablecustomsqldailymonitoringchecksspec)|
|[availability](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tableavailabilitydailymonitoringchecksspec)|Daily monitoring table availability checks|[TableAvailabilityDailyMonitoringChecksSpec](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tableavailabilitydailymonitoringchecksspec)|
|[schema](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tableschemadailymonitoringchecksspec)|Daily monitoring table schema checks|[TableSchemaDailyMonitoringChecksSpec](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tableschemadailymonitoringchecksspec)|
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
|self|:mm|Dict[string, [TableComparisonDailyPartitionedChecksSpec](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tablecomparisondailypartitionedchecksspec)]|


___

## TableDailyPartitionedCheckCategoriesSpec
Container of table level daily partitioned checks. Contains categories of daily partitioned checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[volume](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tablevolumedailypartitionedchecksspec)|Volume daily partitioned data quality checks that verify the quality of every day of data separately|[TableVolumeDailyPartitionedChecksSpec](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tablevolumedailypartitionedchecksspec)|
|[timeliness](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tabletimelinessdailypartitionedchecksspec)|Daily partitioned timeliness checks|[TableTimelinessDailyPartitionedChecksSpec](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tabletimelinessdailypartitionedchecksspec)|
|[custom_sql](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tablecustomsqldailypartitionedchecksspec)|Custom SQL daily partitioned data quality checks that verify the quality of every day of data separately|[TableCustomSqlDailyPartitionedChecksSpec](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tablecustomsqldailypartitionedchecksspec)|
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
|[target](./columns.md#physicaltablename)|Physical table details (a physical schema name and a physical table name).|[PhysicalTableName](./columns.md#physicaltablename)|
|disabled|Disables all data quality checks on the table. Data quality checks will not be executed.|boolean|
|stage|Stage name.|string|
|filter|SQL WHERE clause added to the sensor queries.|string|
|priority|Table priority (1, 2, 3, 4, ...). The tables could be assigned a priority level. The table priority is copied into each data quality check result and a sensor result, enabling efficient grouping of more and less important tables during a data quality improvement project, when the data quality issues on higher priority tables are fixed before data quality issues on less important tables.|integer|
|[owner](../../reference/yaml/TableYaml.md#tableownerspec)|Table owner information like the data steward name or the business application name.|[TableOwnerSpec](../../reference/yaml/TableYaml.md#tableownerspec)|
|[profiling_checks_result_truncation](#profilingtimeperiod)|Defines how many profiling checks results are stored for the table monthly. By default, DQOps will use the &#x27;one_per_month&#x27; configuration and store only the most recent profiling checks result executed during the month. By changing this value, it is possible to store one value per day or even store all profiling checks results.|[ProfilingTimePeriod](#profilingtimeperiod)|
|has_any_configured_checks|True when the table has any checks configured.|boolean|
|has_any_configured_profiling_checks|True when the table has any profiling checks configured.|boolean|
|has_any_configured_monitoring_checks|True when the table has any monitoring checks configured.|boolean|
|has_any_configured_partition_checks|True when the table has any partition checks configured.|boolean|
|partitioning_configuration_missing|True when the table has missing configuration of the &quot;partition_by_column&quot; column, making any partition checks fail when executed.|boolean|
|[run_checks_job_template](./common.md#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run all checks within this table.|[CheckSearchFilters](./common.md#checksearchfilters)|
|[run_profiling_checks_job_template](./common.md#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run profiling checks within this table.|[CheckSearchFilters](./common.md#checksearchfilters)|
|[run_monitoring_checks_job_template](./common.md#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run monitoring checks within this table.|[CheckSearchFilters](./common.md#checksearchfilters)|
|[run_partition_checks_job_template](./common.md#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run partition partitioned checks within this table.|[CheckSearchFilters](./common.md#checksearchfilters)|
|[collect_statistics_job_template](./jobs.md#statisticscollectorsearchfilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors within this table.|[StatisticsCollectorSearchFilters](./jobs.md#statisticscollectorsearchfilters)|
|[data_clean_job_template](./jobs.md#deletestoreddataqueuejobparameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this table.|[DeleteStoredDataQueueJobParameters](./jobs.md#deletestoreddataqueuejobparameters)|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean|
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean|
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean|
|yaml_parsing_error|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|string|


___

## TableModel
Full table model that returns the specification of a single table in the REST Api.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|table_hash|Table hash that identifies the table using a unique hash code.|long|
|[spec](../../reference/yaml/TableYaml.md#tablespec)|Full table specification including all nested information, the table name is inside the &#x27;target&#x27; property.|[TableSpec](../../reference/yaml/TableYaml.md#tablespec)|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|
|yaml_parsing_error|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|string|


___

## TableComparisonMonthlyMonitoringChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the monthly monitoring comparison checks for each configured reference table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|self|:mm|Dict[string, [TableComparisonMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tablecomparisonmonthlymonitoringchecksspec)]|


___

## TableMonthlyMonitoringCheckCategoriesSpec
Container of table level monthly monitoring checks. Contains categories of monthly monitoring checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[volume](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tablevolumemonthlymonitoringchecksspec)|Monthly monitoring of volume data quality checks|[TableVolumeMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tablevolumemonthlymonitoringchecksspec)|
|[timeliness](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tabletimelinessmonthlymonitoringchecksspec)|Monthly monitoring of timeliness checks|[TableTimelinessMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tabletimelinessmonthlymonitoringchecksspec)|
|[accuracy](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tableaccuracymonthlymonitoringchecksspec)|Monthly monitoring accuracy checks|[TableAccuracyMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tableaccuracymonthlymonitoringchecksspec)|
|[custom_sql](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tablecustomsqlmonthlymonitoringchecksspec)|Monthly monitoring of custom SQL checks|[TableCustomSqlMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tablecustomsqlmonthlymonitoringchecksspec)|
|[availability](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tableavailabilitymonthlymonitoringchecksspec)|Daily partitioned availability checks|[TableAvailabilityMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tableavailabilitymonthlymonitoringchecksspec)|
|[schema](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tableschemamonthlymonitoringchecksspec)|Monthly monitoring table schema checks|[TableSchemaMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tableschemamonthlymonitoringchecksspec)|
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
|self|:mm|Dict[string, [TableComparisonMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tablecomparisonmonthlypartitionedchecksspec)]|


___

## TableMonthlyPartitionedCheckCategoriesSpec
Container of table level monthly partitioned checks. Contains categories of monthly partitioned checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[volume](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tablevolumemonthlypartitionedchecksspec)|Volume monthly partitioned data quality checks that verify the quality of every month of data separately|[TableVolumeMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tablevolumemonthlypartitionedchecksspec)|
|[timeliness](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tabletimelinessmonthlypartitionedchecksspec)|Monthly partitioned timeliness checks|[TableTimelinessMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tabletimelinessmonthlypartitionedchecksspec)|
|[custom_sql](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tablecustomsqlmonthlypartitionedchecksspec)|Custom SQL monthly partitioned data quality checks that verify the quality of every month of data separately|[TableCustomSqlMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tablecustomsqlmonthlypartitionedchecksspec)|
|[comparisons](#tablecomparisonmonthlypartitionedchecksspecmap)|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[TableComparisonMonthlyPartitionedChecksSpecMap](#tablecomparisonmonthlypartitionedchecksspecmap)|
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#customcheckspecmap)|


___

## TablePartitioningModel
Rest model that returns the configuration of table partitioning information.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|[target](./columns.md#physicaltablename)|Physical table details (a physical schema name and a physical table name)|[PhysicalTableName](./columns.md#physicaltablename)|
|[timestamp_columns](../../reference/yaml/TableYaml.md#timestampcolumnsspec)|Column names that store the timestamps that identify the event (transaction) timestamp and the ingestion (inserted / loaded at) timestamps. Also configures the timestamp source for the date/time partitioned data quality checks (event timestamp or ingestion timestamp).|[TimestampColumnsSpec](../../reference/yaml/TableYaml.md#timestampcolumnsspec)|
|[incremental_time_window](../../reference/yaml/TableYaml.md#partitionincrementaltimewindowspec)|Configuration of time windows for executing partition checks incrementally, configures the number of recent days to analyze for daily partitioned tables or the number of recent months for monthly partitioned data.|[PartitionIncrementalTimeWindowSpec](../../reference/yaml/TableYaml.md#partitionincrementaltimewindowspec)|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|


___

## TableComparisonProfilingChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|self|:mm|Dict[string, [TableComparisonProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tablecomparisonprofilingchecksspec)]|


___

## TableProfilingCheckCategoriesSpec
Container of table level checks that are activated on a table level.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[result_truncation](./tables.md#profilingtimeperiod)|Defines how many profiling checks results are stored for the table monthly. By default, DQOps will use the &#x27;one_per_month&#x27; configuration and store only the most recent profiling checks result executed during the month. By changing this value, it is possible to store one value per day or even store all profiling checks results.|[ProfilingTimePeriod](./tables.md#profilingtimeperiod)|
|[volume](../../reference/yaml/profiling/table-profiling-checks.md#tablevolumeprofilingchecksspec)|Configuration of volume data quality checks on a table level.|[TableVolumeProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tablevolumeprofilingchecksspec)|
|[timeliness](../../reference/yaml/profiling/table-profiling-checks.md#tabletimelinessprofilingchecksspec)|Configuration of timeliness checks on a table level. Timeliness checks detect anomalies like rapid row count changes.|[TableTimelinessProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tabletimelinessprofilingchecksspec)|
|[accuracy](../../reference/yaml/profiling/table-profiling-checks.md#tableaccuracyprofilingchecksspec)|Configuration of accuracy checks on a table level. Accuracy checks compare the tested table with another reference table.|[TableAccuracyProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tableaccuracyprofilingchecksspec)|
|[custom_sql](../../reference/yaml/profiling/table-profiling-checks.md#tablecustomsqlprofilingchecksspec)|Configuration of data quality checks that are evaluating custom SQL conditions and aggregated expressions.|[TableCustomSqlProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tablecustomsqlprofilingchecksspec)|
|[availability](../../reference/yaml/profiling/table-profiling-checks.md#tableavailabilityprofilingchecksspec)|Configuration of the table availability data quality checks on a table level.|[TableAvailabilityProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tableavailabilityprofilingchecksspec)|
|[schema](../../reference/yaml/profiling/table-profiling-checks.md#tableschemaprofilingchecksspec)|Configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tableschemaprofilingchecksspec)|
|[comparisons](#tablecomparisonprofilingchecksspecmap)|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[TableComparisonProfilingChecksSpecMap](#tablecomparisonprofilingchecksspecmap)|
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](#customcheckspecmap)|


___

## TableStatisticsModel
Model that returns a summary of the table level statistics (the basic profiling results).


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|[table](./columns.md#physicaltablename)|Physical table name including the schema and table names.|[PhysicalTableName](./columns.md#physicaltablename)|
|[collect_table_statistics_job_template](./jobs.md#statisticscollectorsearchfilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors within this table, limited only to the table level statistics (row count, etc).|[StatisticsCollectorSearchFilters](./jobs.md#statisticscollectorsearchfilters)|
|[collect_table_and_column_statistics_job_template](./jobs.md#statisticscollectorsearchfilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors within this table, including statistics for all columns.|[StatisticsCollectorSearchFilters](./jobs.md#statisticscollectorsearchfilters)|
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean|


___

