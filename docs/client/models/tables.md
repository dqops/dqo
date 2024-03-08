# DQOps REST API tables models reference
The references of all objects used by [tables](/docs/client/operations/tables.md) REST API operations are listed below.


## TableComparisonDailyMonitoringChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the daily monitoring comparison checks for each configured reference table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [TableComparisonDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/table-daily-monitoring-checks/#tablecomparisondailymonitoringchecksspec)]*|


___

## CustomCheckSpecMap
Dictionary of custom checks indexed by a check name.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [CustomCheckSpec](/docs/reference/yaml/profiling/table-profiling-checks/#customcheckspec)]*|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [CustomCheckSpec](/docs/reference/yaml/profiling/table-profiling-checks/#customcheckspec)]*|


___

## TableDailyMonitoringCheckCategoriesSpec
Container of table level daily monitoring. Contains categories of daily monitoring.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`volume`](/docs/reference/yaml/monitoring/table-daily-monitoring-checks/#tablevolumedailymonitoringchecksspec)</span>|Daily monitoring volume data quality checks|*[TableVolumeDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/table-daily-monitoring-checks/#tablevolumedailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`timeliness`](/docs/reference/yaml/monitoring/table-daily-monitoring-checks/#tabletimelinessdailymonitoringchecksspec)</span>|Daily monitoring timeliness checks|*[TableTimelinessDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/table-daily-monitoring-checks/#tabletimelinessdailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`accuracy`](/docs/reference/yaml/monitoring/table-daily-monitoring-checks/#tableaccuracydailymonitoringchecksspec)</span>|Daily monitoring accuracy checks|*[TableAccuracyDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/table-daily-monitoring-checks/#tableaccuracydailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](/docs/reference/yaml/monitoring/table-daily-monitoring-checks/#tablecustomsqldailymonitoringchecksspec)</span>|Daily monitoring custom SQL checks|*[TableCustomSqlDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/table-daily-monitoring-checks/#tablecustomsqldailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`availability`](/docs/reference/yaml/monitoring/table-daily-monitoring-checks/#tableavailabilitydailymonitoringchecksspec)</span>|Daily monitoring table availability checks|*[TableAvailabilityDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/table-daily-monitoring-checks/#tableavailabilitydailymonitoringchecksspec)*|
|<span class="no-wrap-code">[`schema`](/docs/reference/yaml/monitoring/table-daily-monitoring-checks/#tableschemadailymonitoringchecksspec)</span>|Daily monitoring table schema checks|*[TableSchemaDailyMonitoringChecksSpec](/docs/reference/yaml/monitoring/table-daily-monitoring-checks/#tableschemadailymonitoringchecksspec)*|
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
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [TableComparisonDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/table-daily-partitioned-checks/#tablecomparisondailypartitionedchecksspec)]*|


___

## TableDailyPartitionedCheckCategoriesSpec
Container of table level daily partitioned checks. Contains categories of daily partitioned checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`volume`](/docs/reference/yaml/partitioned/table-daily-partitioned-checks/#tablevolumedailypartitionedchecksspec)</span>|Volume daily partitioned data quality checks that verify the quality of every day of data separately|*[TableVolumeDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/table-daily-partitioned-checks/#tablevolumedailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`timeliness`](/docs/reference/yaml/partitioned/table-daily-partitioned-checks/#tabletimelinessdailypartitionedchecksspec)</span>|Daily partitioned timeliness checks|*[TableTimelinessDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/table-daily-partitioned-checks/#tabletimelinessdailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](/docs/reference/yaml/partitioned/table-daily-partitioned-checks/#tablecustomsqldailypartitionedchecksspec)</span>|Custom SQL daily partitioned data quality checks that verify the quality of every day of data separately|*[TableCustomSqlDailyPartitionedChecksSpec](/docs/reference/yaml/partitioned/table-daily-partitioned-checks/#tablecustomsqldailypartitionedchecksspec)*|
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
|<span class="no-wrap-code">[`target`](/docs/client/models/columns.md#physicaltablename)</span>|Physical table details (a physical schema name and a physical table name).|*[PhysicalTableName](/docs/client/models/columns.md#physicaltablename)*|
|<span class="no-wrap-code">`disabled`</span>|Disables all data quality checks on the table. Data quality checks will not be executed.|*boolean*|
|<span class="no-wrap-code">`stage`</span>|Stage name.|*string*|
|<span class="no-wrap-code">`filter`</span>|SQL WHERE clause added to the sensor queries.|*string*|
|<span class="no-wrap-code">`priority`</span>|Table priority (1, 2, 3, 4, ...). The tables can be assigned a priority level. The table priority is copied into each data quality check result and a sensor result, enabling efficient grouping of more and less important tables during a data quality improvement project, when the data quality issues on higher priority tables are fixed before data quality issues on less important tables.|*integer*|
|<span class="no-wrap-code">[`owner`](/docs/reference/yaml/TableYaml/#tableownerspec)</span>|Table owner information like the data steward name or the business application name.|*[TableOwnerSpec](/docs/reference/yaml/TableYaml/#tableownerspec)*|
|<span class="no-wrap-code">[`profiling_checks_result_truncation`](#profilingtimeperiod)</span>|Defines how many profiling checks results are stored for the table monthly. By default, DQOps will use the 'one_per_month' configuration and store only the most recent profiling checks result executed during the month. By changing this value, it is possible to store one value per day or even store all profiling checks results.|*[ProfilingTimePeriod](#profilingtimeperiod)*|
|<span class="no-wrap-code">[`file_format`](/docs/reference/yaml/TableYaml/#fileformatspec)</span>|File format for a file based table, such as a CSV or Parquet file.|*[FileFormatSpec](/docs/reference/yaml/TableYaml/#fileformatspec)*|
|<span class="no-wrap-code">`has_any_configured_checks`</span>|True when the table has any checks configured.|*boolean*|
|<span class="no-wrap-code">`has_any_configured_profiling_checks`</span>|True when the table has any profiling checks configured.|*boolean*|
|<span class="no-wrap-code">`has_any_configured_monitoring_checks`</span>|True when the table has any monitoring checks configured.|*boolean*|
|<span class="no-wrap-code">`has_any_configured_partition_checks`</span>|True when the table has any partition checks configured.|*boolean*|
|<span class="no-wrap-code">`partitioning_configuration_missing`</span>|True when the table has missing configuration of the "partition_by_column" column, making any partition checks fail when executed.|*boolean*|
|<span class="no-wrap-code">[`run_checks_job_template`](/docs/client/models/common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run all checks within this table.|*[CheckSearchFilters](/docs/client/models/common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_profiling_checks_job_template`](/docs/client/models/common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run profiling checks within this table.|*[CheckSearchFilters](/docs/client/models/common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_monitoring_checks_job_template`](/docs/client/models/common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run monitoring checks within this table.|*[CheckSearchFilters](/docs/client/models/common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_partition_checks_job_template`](/docs/client/models/common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run partition partitioned checks within this table.|*[CheckSearchFilters](/docs/client/models/common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`collect_statistics_job_template`](/docs/client/models/jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors within this table.|*[StatisticsCollectorSearchFilters](/docs/client/models/jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">[`data_clean_job_template`](/docs/client/models/jobs.md#deletestoreddataqueuejobparameters)</span>|Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this table.|*[DeleteStoredDataQueueJobParameters](/docs/client/models/jobs.md#deletestoreddataqueuejobparameters)*|
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
|<span class="no-wrap-code">[`spec`](/docs/reference/yaml/TableYaml/#tablespec)</span>|Full table specification including all nested information, the table name is inside the 'target' property.|*[TableSpec](/docs/reference/yaml/TableYaml/#tablespec)*|
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
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [TableComparisonMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/table-monthly-monitoring-checks/#tablecomparisonmonthlymonitoringchecksspec)]*|


___

## TableMonthlyMonitoringCheckCategoriesSpec
Container of table level monthly monitoring checks. Contains categories of monthly monitoring checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`volume`](/docs/reference/yaml/monitoring/table-monthly-monitoring-checks/#tablevolumemonthlymonitoringchecksspec)</span>|Monthly monitoring of volume data quality checks|*[TableVolumeMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/table-monthly-monitoring-checks/#tablevolumemonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`timeliness`](/docs/reference/yaml/monitoring/table-monthly-monitoring-checks/#tabletimelinessmonthlymonitoringchecksspec)</span>|Monthly monitoring of timeliness checks|*[TableTimelinessMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/table-monthly-monitoring-checks/#tabletimelinessmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`accuracy`](/docs/reference/yaml/monitoring/table-monthly-monitoring-checks/#tableaccuracymonthlymonitoringchecksspec)</span>|Monthly monitoring accuracy checks|*[TableAccuracyMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/table-monthly-monitoring-checks/#tableaccuracymonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](/docs/reference/yaml/monitoring/table-monthly-monitoring-checks/#tablecustomsqlmonthlymonitoringchecksspec)</span>|Monthly monitoring of custom SQL checks|*[TableCustomSqlMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/table-monthly-monitoring-checks/#tablecustomsqlmonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`availability`](/docs/reference/yaml/monitoring/table-monthly-monitoring-checks/#tableavailabilitymonthlymonitoringchecksspec)</span>|Daily partitioned availability checks|*[TableAvailabilityMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/table-monthly-monitoring-checks/#tableavailabilitymonthlymonitoringchecksspec)*|
|<span class="no-wrap-code">[`schema`](/docs/reference/yaml/monitoring/table-monthly-monitoring-checks/#tableschemamonthlymonitoringchecksspec)</span>|Monthly monitoring table schema checks|*[TableSchemaMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/table-monthly-monitoring-checks/#tableschemamonthlymonitoringchecksspec)*|
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
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [TableComparisonMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/table-monthly-partitioned-checks/#tablecomparisonmonthlypartitionedchecksspec)]*|


___

## TableMonthlyPartitionedCheckCategoriesSpec
Container of table level monthly partitioned checks. Contains categories of monthly partitioned checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`volume`](/docs/reference/yaml/partitioned/table-monthly-partitioned-checks/#tablevolumemonthlypartitionedchecksspec)</span>|Volume monthly partitioned data quality checks that verify the quality of every month of data separately|*[TableVolumeMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/table-monthly-partitioned-checks/#tablevolumemonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`timeliness`](/docs/reference/yaml/partitioned/table-monthly-partitioned-checks/#tabletimelinessmonthlypartitionedchecksspec)</span>|Monthly partitioned timeliness checks|*[TableTimelinessMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/table-monthly-partitioned-checks/#tabletimelinessmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](/docs/reference/yaml/partitioned/table-monthly-partitioned-checks/#tablecustomsqlmonthlypartitionedchecksspec)</span>|Custom SQL monthly partitioned data quality checks that verify the quality of every month of data separately|*[TableCustomSqlMonthlyPartitionedChecksSpec](/docs/reference/yaml/partitioned/table-monthly-partitioned-checks/#tablecustomsqlmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`comparisons`](#tablecomparisonmonthlypartitionedchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[TableComparisonMonthlyPartitionedChecksSpecMap](#tablecomparisonmonthlypartitionedchecksspecmap)*|
|<span class="no-wrap-code">[`custom`](#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](#customcheckspecmap)*|


___

## TablePartitioningModel
Rest model that returns the configuration of table partitioning information.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">[`target`](/docs/client/models/columns.md#physicaltablename)</span>|Physical table details (a physical schema name and a physical table name)|*[PhysicalTableName](/docs/client/models/columns.md#physicaltablename)*|
|<span class="no-wrap-code">[`timestamp_columns`](/docs/reference/yaml/TableYaml/#timestampcolumnsspec)</span>|Column names that store the timestamps that identify the event (transaction) timestamp and the ingestion (inserted / loaded at) timestamps. Also configures the timestamp source for the date/time partitioned data quality checks (event timestamp or ingestion timestamp).|*[TimestampColumnsSpec](/docs/reference/yaml/TableYaml/#timestampcolumnsspec)*|
|<span class="no-wrap-code">[`incremental_time_window`](/docs/reference/yaml/TableYaml/#partitionincrementaltimewindowspec)</span>|Configuration of time windows for executing partition checks incrementally, configures the number of recent days to analyze for daily partitioned tables or the number of recent months for monthly partitioned data.|*[PartitionIncrementalTimeWindowSpec](/docs/reference/yaml/TableYaml/#partitionincrementaltimewindowspec)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|


___

## TableComparisonProfilingChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*Dict[string, [TableComparisonProfilingChecksSpec](/docs/reference/yaml/profiling/table-profiling-checks/#tablecomparisonprofilingchecksspec)]*|


___

## TableProfilingCheckCategoriesSpec
Container of table level checks that are activated on a table level.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`result_truncation`](/docs/client/models/tables.md#profilingtimeperiod)</span>|Defines how many profiling checks results are stored for the table monthly. By default, DQOps will use the 'one_per_month' configuration and store only the most recent profiling checks result executed during the month. By changing this value, it is possible to store one value per day or even store all profiling checks results.|*[ProfilingTimePeriod](/docs/client/models/tables.md#profilingtimeperiod)*|
|<span class="no-wrap-code">[`volume`](/docs/reference/yaml/profiling/table-profiling-checks/#tablevolumeprofilingchecksspec)</span>|Configuration of volume data quality checks on a table level.|*[TableVolumeProfilingChecksSpec](/docs/reference/yaml/profiling/table-profiling-checks/#tablevolumeprofilingchecksspec)*|
|<span class="no-wrap-code">[`timeliness`](/docs/reference/yaml/profiling/table-profiling-checks/#tabletimelinessprofilingchecksspec)</span>|Configuration of timeliness checks on a table level. Timeliness checks detect anomalies like rapid row count changes.|*[TableTimelinessProfilingChecksSpec](/docs/reference/yaml/profiling/table-profiling-checks/#tabletimelinessprofilingchecksspec)*|
|<span class="no-wrap-code">[`accuracy`](/docs/reference/yaml/profiling/table-profiling-checks/#tableaccuracyprofilingchecksspec)</span>|Configuration of accuracy checks on a table level. Accuracy checks compare the tested table with another reference table.|*[TableAccuracyProfilingChecksSpec](/docs/reference/yaml/profiling/table-profiling-checks/#tableaccuracyprofilingchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](/docs/reference/yaml/profiling/table-profiling-checks/#tablecustomsqlprofilingchecksspec)</span>|Configuration of data quality checks that are evaluating custom SQL conditions and aggregated expressions.|*[TableCustomSqlProfilingChecksSpec](/docs/reference/yaml/profiling/table-profiling-checks/#tablecustomsqlprofilingchecksspec)*|
|<span class="no-wrap-code">[`availability`](/docs/reference/yaml/profiling/table-profiling-checks/#tableavailabilityprofilingchecksspec)</span>|Configuration of the table availability data quality checks on a table level.|*[TableAvailabilityProfilingChecksSpec](/docs/reference/yaml/profiling/table-profiling-checks/#tableavailabilityprofilingchecksspec)*|
|<span class="no-wrap-code">[`schema`](/docs/reference/yaml/profiling/table-profiling-checks/#tableschemaprofilingchecksspec)</span>|Configuration of schema (column count and schema) data quality checks on a table level.|*[TableSchemaProfilingChecksSpec](/docs/reference/yaml/profiling/table-profiling-checks/#tableschemaprofilingchecksspec)*|
|<span class="no-wrap-code">[`comparisons`](#tablecomparisonprofilingchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[TableComparisonProfilingChecksSpecMap](#tablecomparisonprofilingchecksspecmap)*|
|<span class="no-wrap-code">[`custom`](#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](#customcheckspecmap)*|


___

## TableStatisticsModel
Model that returns a summary of the table level statistics (the basic profiling results).


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">[`table`](/docs/client/models/columns.md#physicaltablename)</span>|Physical table name including the schema and table names.|*[PhysicalTableName](/docs/client/models/columns.md#physicaltablename)*|
|<span class="no-wrap-code">[`collect_table_statistics_job_template`](/docs/client/models/jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors within this table, limited only to the table level statistics (row count, etc).|*[StatisticsCollectorSearchFilters](/docs/client/models/jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">[`collect_table_and_column_statistics_job_template`](/docs/client/models/jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors within this table, including statistics for all columns.|*[StatisticsCollectorSearchFilters](/docs/client/models/jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">`can_collect_statistics`</span>|Boolean flag that decides if the current user can collect statistics.|*boolean*|


___

