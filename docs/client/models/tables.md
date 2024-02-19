# DQOps REST API tables models reference
The references of all objects used by [tables](../operations/tables.md) REST API operations are listed below.


## TableComparisonDailyMonitoringChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the daily monitoring comparison checks for each configured reference table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [TableComparisonDailyMonitoringChecksSpec](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tablecomparisondailymonitoringchecksspec)]*|


___

## CustomCheckSpecMap
Dictionary of custom checks indexed by a check name.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [CustomCheckSpec](../../reference/yaml/profiling/table-profiling-checks.md#customcheckspec)]*|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [CustomCheckSpec](../../reference/yaml/profiling/table-profiling-checks.md#customcheckspec)]*|


___

## TableDailyMonitoringCheckCategoriesSpec
Container of table level daily monitoring. Contains categories of daily monitoring.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`volume`](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tablevolumedailymonitoringchecksspec)</span>|Daily monitoring volume data quality checks|*[TableVolumeDailyMonitoringChecksSpec](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tablevolumedailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`timeliness`](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tabletimelinessdailymonitoringchecksspec)</span>|Daily monitoring timeliness checks|*[TableTimelinessDailyMonitoringChecksSpec](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tabletimelinessdailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`accuracy`](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tableaccuracydailymonitoringchecksspec)</span>|Daily monitoring accuracy checks|*[TableAccuracyDailyMonitoringChecksSpec](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tableaccuracydailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tablecustomsqldailymonitoringchecksspec)</span>|Daily monitoring custom SQL checks|*[TableCustomSqlDailyMonitoringChecksSpec](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tablecustomsqldailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`availability`](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tableavailabilitydailymonitoringchecksspec)</span>|Daily monitoring table availability checks|*[TableAvailabilityDailyMonitoringChecksSpec](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tableavailabilitydailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`schema`](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tableschemadailymonitoringchecksspec)</span>|Daily monitoring table schema checks|*[TableSchemaDailyMonitoringChecksSpec](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tableschemadailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`comparisons`](#tablecomparisondailymonitoringchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[TableComparisonDailyMonitoringChecksSpecMap](#tablecomparisondailymonitoringchecksspecmap)*|
|<span class="no-wrap-code">[`custom`](#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](#customcheckspecmap)*|


___

## TableComparisonDailyPartitionedChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the daily partitioned comparison checks for each configured reference table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [TableComparisonDailyPartitionedChecksSpec](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tablecomparisondailypartitionedchecksspec)]*|


___

## TableDailyPartitionedCheckCategoriesSpec
Container of table level daily partitioned checks. Contains categories of daily partitioned checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`volume`](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tablevolumedailypartitionedchecksspec)</span>|Volume daily partitioned data quality checks that verify the quality of every day of data separately|*[TableVolumeDailyPartitionedChecksSpec](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tablevolumedailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`timeliness`](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tabletimelinessdailypartitionedchecksspec)</span>|Daily partitioned timeliness checks|*[TableTimelinessDailyPartitionedChecksSpec](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tabletimelinessdailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tablecustomsqldailypartitionedchecksspec)</span>|Custom SQL daily partitioned data quality checks that verify the quality of every day of data separately|*[TableCustomSqlDailyPartitionedChecksSpec](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tablecustomsqldailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`comparisons`](#tablecomparisondailypartitionedchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[TableComparisonDailyPartitionedChecksSpecMap](#tablecomparisondailypartitionedchecksspecmap)*|
|<span class="no-wrap-code">[`custom`](#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](#customcheckspecmap)*|


___

## ProfilingTimePeriod
The time period for profiling checks (millisecond, daily, monthly, weekly, hourly).
 The default profiling check stores one value per month. When profiling checks is re-executed during the month,
 the previous profiling checks value is overwritten and only the most recent value is stored.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|one_per_month<br/>one_per_week<br/>one_per_day<br/>one_per_hour<br/>all_results<br/>|

___

## TableListModel
Table list model returned by the rest api that is limited only to the basic fields, excluding nested nodes.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">`table_hash`</span>|Table hash that identifies the table using a unique hash code.|*long*|
|<span class="no-wrap-code">[`target`](./columns.md#physicaltablename)</span>|Physical table details (a physical schema name and a physical table name).|*[PhysicalTableName](./columns.md#physicaltablename)*|
|<span class="no-wrap-code">`disabled`</span>|Disables all data quality checks on the table. Data quality checks will not be executed.|*boolean*|
|<span class="no-wrap-code">`stage`</span>|Stage name.|*string*|
|<span class="no-wrap-code">`filter`</span>|SQL WHERE clause added to the sensor queries.|*string*|
|<span class="no-wrap-code">`priority`</span>|Table priority (1, 2, 3, 4, ...). The tables can be assigned a priority level. The table priority is copied into each data quality check result and a sensor result, enabling efficient grouping of more and less important tables during a data quality improvement project, when the data quality issues on higher priority tables are fixed before data quality issues on less important tables.|*integer*|
|<span class="no-wrap-code">[`owner`](../../reference/yaml/TableYaml.md#tableownerspec)</span>|Table owner information like the data steward name or the business application name.|*[TableOwnerSpec](../../reference/yaml/TableYaml.md#tableownerspec)*|
|<span class="no-wrap-code">[`profiling_checks_result_truncation`](#profilingtimeperiod)</span>|Defines how many profiling checks results are stored for the table monthly. By default, DQOps will use the 'one_per_month' configuration and store only the most recent profiling checks result executed during the month. By changing this value, it is possible to store one value per day or even store all profiling checks results.|*[ProfilingTimePeriod](#profilingtimeperiod)*|
|<span class="no-wrap-code">[`file_format`](../../reference/yaml/TableYaml.md#fileformatspec)</span>|File format for a file based table, such as a CSV or Parquet file.|*[FileFormatSpec](../../reference/yaml/TableYaml.md#fileformatspec)*|
|<span class="no-wrap-code">`has_any_configured_checks`</span>|True when the table has any checks configured.|*boolean*|
|<span class="no-wrap-code">`has_any_configured_profiling_checks`</span>|True when the table has any profiling checks configured.|*boolean*|
|<span class="no-wrap-code">`has_any_configured_monitoring_checks`</span>|True when the table has any monitoring checks configured.|*boolean*|
|<span class="no-wrap-code">`has_any_configured_partition_checks`</span>|True when the table has any partition checks configured.|*boolean*|
|<span class="no-wrap-code">`partitioning_configuration_missing`</span>|True when the table has missing configuration of the "partition_by_column" column, making any partition checks fail when executed.|*boolean*|
|<span class="no-wrap-code">[`run_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run all checks within this table.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_profiling_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run profiling checks within this table.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_monitoring_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run monitoring checks within this table.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_partition_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run partition partitioned checks within this table.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`collect_statistics_job_template`](./jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors within this table.|*[StatisticsCollectorSearchFilters](./jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">[`data_clean_job_template`](./jobs.md#deletestoreddataqueuejobparameters)</span>|Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this table.|*[DeleteStoredDataQueueJobParameters](./jobs.md#deletestoreddataqueuejobparameters)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`can_collect_statistics`</span>|Boolean flag that decides if the current user can collect statistics.|*boolean*|
|<span class="no-wrap-code">`can_run_checks`</span>|Boolean flag that decides if the current user can run checks.|*boolean*|
|<span class="no-wrap-code">`can_delete_data`</span>|Boolean flag that decides if the current user can delete data (results).|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

## TableModel
Full table model that returns the specification of a single table in the REST Api.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">`table_hash`</span>|Table hash that identifies the table using a unique hash code.|*long*|
|<span class="no-wrap-code">[`spec`](../../reference/yaml/TableYaml.md#tablespec)</span>|Full table specification including all nested information, the table name is inside the 'target' property.|*[TableSpec](../../reference/yaml/TableYaml.md#tablespec)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

## TableComparisonMonthlyMonitoringChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the monthly monitoring comparison checks for each configured reference table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [TableComparisonMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tablecomparisonmonthlymonitoringchecksspec)]*|


___

## TableMonthlyMonitoringCheckCategoriesSpec
Container of table level monthly monitoring checks. Contains categories of monthly monitoring checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`volume`](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tablevolumemonthlymonitoringchecksspec)</span>|Monthly monitoring of volume data quality checks|*[TableVolumeMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tablevolumemonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`timeliness`](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tabletimelinessmonthlymonitoringchecksspec)</span>|Monthly monitoring of timeliness checks|*[TableTimelinessMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tabletimelinessmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`accuracy`](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tableaccuracymonthlymonitoringchecksspec)</span>|Monthly monitoring accuracy checks|*[TableAccuracyMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tableaccuracymonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tablecustomsqlmonthlymonitoringchecksspec)</span>|Monthly monitoring of custom SQL checks|*[TableCustomSqlMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tablecustomsqlmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`availability`](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tableavailabilitymonthlymonitoringchecksspec)</span>|Daily partitioned availability checks|*[TableAvailabilityMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tableavailabilitymonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`schema`](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tableschemamonthlymonitoringchecksspec)</span>|Monthly monitoring table schema checks|*[TableSchemaMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tableschemamonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`comparisons`](#tablecomparisonmonthlymonitoringchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[TableComparisonMonthlyMonitoringChecksSpecMap](#tablecomparisonmonthlymonitoringchecksspecmap)*|
|<span class="no-wrap-code">[`custom`](#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](#customcheckspecmap)*|


___

## TableComparisonMonthlyPartitionedChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the monthly partitioned comparison checks for each configured reference table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [TableComparisonMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tablecomparisonmonthlypartitionedchecksspec)]*|


___

## TableMonthlyPartitionedCheckCategoriesSpec
Container of table level monthly partitioned checks. Contains categories of monthly partitioned checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`volume`](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tablevolumemonthlypartitionedchecksspec)</span>|Volume monthly partitioned data quality checks that verify the quality of every month of data separately|*[TableVolumeMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tablevolumemonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`timeliness`](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tabletimelinessmonthlypartitionedchecksspec)</span>|Monthly partitioned timeliness checks|*[TableTimelinessMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tabletimelinessmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tablecustomsqlmonthlypartitionedchecksspec)</span>|Custom SQL monthly partitioned data quality checks that verify the quality of every month of data separately|*[TableCustomSqlMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tablecustomsqlmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`comparisons`](#tablecomparisonmonthlypartitionedchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[TableComparisonMonthlyPartitionedChecksSpecMap](#tablecomparisonmonthlypartitionedchecksspecmap)*|
|<span class="no-wrap-code">[`custom`](#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](#customcheckspecmap)*|


___

## TablePartitioningModel
Rest model that returns the configuration of table partitioning information.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">[`target`](./columns.md#physicaltablename)</span>|Physical table details (a physical schema name and a physical table name)|*[PhysicalTableName](./columns.md#physicaltablename)*|
|<span class="no-wrap-code">[`timestamp_columns`](../../reference/yaml/TableYaml.md#timestampcolumnsspec)</span>|Column names that store the timestamps that identify the event (transaction) timestamp and the ingestion (inserted / loaded at) timestamps. Also configures the timestamp source for the date/time partitioned data quality checks (event timestamp or ingestion timestamp).|*[TimestampColumnsSpec](../../reference/yaml/TableYaml.md#timestampcolumnsspec)*|
|<span class="no-wrap-code">[`incremental_time_window`](../../reference/yaml/TableYaml.md#partitionincrementaltimewindowspec)</span>|Configuration of time windows for executing partition checks incrementally, configures the number of recent days to analyze for daily partitioned tables or the number of recent months for monthly partitioned data.|*[PartitionIncrementalTimeWindowSpec](../../reference/yaml/TableYaml.md#partitionincrementaltimewindowspec)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|


___

## TableComparisonProfilingChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [TableComparisonProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tablecomparisonprofilingchecksspec)]*|


___

## TableProfilingCheckCategoriesSpec
Container of table level checks that are activated on a table level.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`result_truncation`](./tables.md#profilingtimeperiod)</span>|Defines how many profiling checks results are stored for the table monthly. By default, DQOps will use the 'one_per_month' configuration and store only the most recent profiling checks result executed during the month. By changing this value, it is possible to store one value per day or even store all profiling checks results.|*[ProfilingTimePeriod](./tables.md#profilingtimeperiod)*|
|<span class="no-wrap-code">[`volume`](../../reference/yaml/profiling/table-profiling-checks.md#tablevolumeprofilingchecksspec)</span>|Configuration of volume data quality checks on a table level.|*[TableVolumeProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tablevolumeprofilingchecksspec)*|
|<span class="no-wrap-code">[`timeliness`](../../reference/yaml/profiling/table-profiling-checks.md#tabletimelinessprofilingchecksspec)</span>|Configuration of timeliness checks on a table level. Timeliness checks detect anomalies like rapid row count changes.|*[TableTimelinessProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tabletimelinessprofilingchecksspec)*|
|<span class="no-wrap-code">[`accuracy`](../../reference/yaml/profiling/table-profiling-checks.md#tableaccuracyprofilingchecksspec)</span>|Configuration of accuracy checks on a table level. Accuracy checks compare the tested table with another reference table.|*[TableAccuracyProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tableaccuracyprofilingchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](../../reference/yaml/profiling/table-profiling-checks.md#tablecustomsqlprofilingchecksspec)</span>|Configuration of data quality checks that are evaluating custom SQL conditions and aggregated expressions.|*[TableCustomSqlProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tablecustomsqlprofilingchecksspec)*|
|<span class="no-wrap-code">[`availability`](../../reference/yaml/profiling/table-profiling-checks.md#tableavailabilityprofilingchecksspec)</span>|Configuration of the table availability data quality checks on a table level.|*[TableAvailabilityProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tableavailabilityprofilingchecksspec)*|
|<span class="no-wrap-code">[`schema`](../../reference/yaml/profiling/table-profiling-checks.md#tableschemaprofilingchecksspec)</span>|Configuration of schema (column count and schema) data quality checks on a table level.|*[TableSchemaProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tableschemaprofilingchecksspec)*|
|<span class="no-wrap-code">[`comparisons`](#tablecomparisonprofilingchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[TableComparisonProfilingChecksSpecMap](#tablecomparisonprofilingchecksspecmap)*|
|<span class="no-wrap-code">[`custom`](#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](#customcheckspecmap)*|


___

## TableStatisticsModel
Model that returns a summary of the table level statistics (the basic profiling results).


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">[`table`](./columns.md#physicaltablename)</span>|Physical table name including the schema and table names.|*[PhysicalTableName](./columns.md#physicaltablename)*|
|<span class="no-wrap-code">[`collect_table_statistics_job_template`](./jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors within this table, limited only to the table level statistics (row count, etc).|*[StatisticsCollectorSearchFilters](./jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">[`collect_table_and_column_statistics_job_template`](./jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors within this table, including statistics for all columns.|*[StatisticsCollectorSearchFilters](./jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">`can_collect_statistics`</span>|Boolean flag that decides if the current user can collect statistics.|*boolean*|


___

