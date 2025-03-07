---
title: DQOps REST API tables models reference
---
# DQOps REST API tables models reference
The references of all objects used by [tables](../operations/tables.md) REST API operations are listed below.


## SimilarTableModel
Model that describes a table that is similar to a reference table. Similar tables are used to build the data lineage graph.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`difference`</span>|Table similarity score. Lower numbers indicate higher similarity.|*integer*|
|<span class="no-wrap-code">`similarity_pct`</span>|A similarity score as a percentage. A value 100.0 means that the tables are probably equal.|*double*|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|


___

## TableComparisonDailyMonitoringChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the daily monitoring comparison checks for each configured reference table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>||*Dict[string, [TableComparisonDailyMonitoringChecksSpec](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tablecomparisondailymonitoringchecksspec)]*|


___

## CustomCheckSpecMap
Dictionary of custom checks indexed by a check name.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>||*Dict[string, [CustomCheckSpec](../../reference/yaml/profiling/table-profiling-checks.md#customcheckspec)]*|


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
|<span class="no-wrap-code">[`uniqueness`](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tableuniquenessdailymonitoringchecksspec)</span>|Daily monitoring uniqueness checks on a table level.|*[TableUniquenessDailyMonitoringChecksSpec](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tableuniquenessdailymonitoringchecksspec)*|
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
|<span class="no-wrap-code">`self`</span>||*Dict[string, [TableComparisonDailyPartitionedChecksSpec](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tablecomparisondailypartitionedchecksspec)]*|


___

## TableDailyPartitionedCheckCategoriesSpec
Container of table level daily partitioned checks. Contains categories of daily partitioned checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`volume`](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tablevolumedailypartitionedchecksspec)</span>|Volume daily partitioned data quality checks that verify the quality of every day of data separately|*[TableVolumeDailyPartitionedChecksSpec](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tablevolumedailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`timeliness`](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tabletimelinessdailypartitionedchecksspec)</span>|Daily partitioned timeliness checks|*[TableTimelinessDailyPartitionedChecksSpec](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tabletimelinessdailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tablecustomsqldailypartitionedchecksspec)</span>|Custom SQL daily partitioned data quality checks that verify the quality of every day of data separately|*[TableCustomSqlDailyPartitionedChecksSpec](../../reference/yaml/partitioned/table-daily-partitioned-checks.md#tablecustomsqldailypartitionedchecksspec)*|
|<span class="no-wrap-code">[`uniqueness`](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tableuniquenessdailypartitionchecksspec)</span>|Daily partitioned uniqueness checks on a table level.|*[TableUniquenessDailyPartitionChecksSpec](../../reference/yaml/monitoring/table-daily-monitoring-checks.md#tableuniquenessdailypartitionchecksspec)*|
|<span class="no-wrap-code">[`comparisons`](#tablecomparisondailypartitionedchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[TableComparisonDailyPartitionedChecksSpecMap](#tablecomparisondailypartitionedchecksspecmap)*|
|<span class="no-wrap-code">[`custom`](#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](#customcheckspecmap)*|


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
|<span class="no-wrap-code">`self`</span>||*Dict[string, [TableComparisonMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tablecomparisonmonthlymonitoringchecksspec)]*|


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
|<span class="no-wrap-code">[`uniqueness`](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tableuniquenessmonthlymonitoringchecksspec)</span>|Monthly monitoring uniqueness checks on a table level.|*[TableUniquenessMonthlyMonitoringChecksSpec](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tableuniquenessmonthlymonitoringchecksspec)*|
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
|<span class="no-wrap-code">`self`</span>||*Dict[string, [TableComparisonMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tablecomparisonmonthlypartitionedchecksspec)]*|


___

## TableMonthlyPartitionedCheckCategoriesSpec
Container of table level monthly partitioned checks. Contains categories of monthly partitioned checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`volume`](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tablevolumemonthlypartitionedchecksspec)</span>|Volume monthly partitioned data quality checks that verify the quality of every month of data separately|*[TableVolumeMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tablevolumemonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`timeliness`](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tabletimelinessmonthlypartitionedchecksspec)</span>|Monthly partitioned timeliness checks|*[TableTimelinessMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tabletimelinessmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tablecustomsqlmonthlypartitionedchecksspec)</span>|Custom SQL monthly partitioned data quality checks that verify the quality of every month of data separately|*[TableCustomSqlMonthlyPartitionedChecksSpec](../../reference/yaml/partitioned/table-monthly-partitioned-checks.md#tablecustomsqlmonthlypartitionedchecksspec)*|
|<span class="no-wrap-code">[`uniqueness`](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tableuniquenessmonthlypartitionchecksspec)</span>|Monthly partitioned uniqueness checks on a table level.|*[TableUniquenessMonthlyPartitionChecksSpec](../../reference/yaml/monitoring/table-monthly-monitoring-checks.md#tableuniquenessmonthlypartitionchecksspec)*|
|<span class="no-wrap-code">[`comparisons`](#tablecomparisonmonthlypartitionedchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[TableComparisonMonthlyPartitionedChecksSpecMap](#tablecomparisonmonthlypartitionedchecksspecmap)*|
|<span class="no-wrap-code">[`custom`](#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](#customcheckspecmap)*|


___

## TablePartitioningModel
Rest model that returns the configuration of table partitioning information.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">[`target`](./common.md#physicaltablename)</span>|Physical table details (a physical schema name and a physical table name)|*[PhysicalTableName](./common.md#physicaltablename)*|
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
|<span class="no-wrap-code">`self`</span>||*Dict[string, [TableComparisonProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tablecomparisonprofilingchecksspec)]*|


___

## TableProfilingCheckCategoriesSpec
Container of table level checks that are activated on a table level.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`result_truncation`](./common.md#profilingtimeperiodtruncation)</span>|Defines how many profiling checks results are stored for the table monthly. By default, DQOps will use the 'one_per_month' configuration and store only the most recent profiling checks result executed during the month. By changing this value, it is possible to store one value per day or even store all profiling checks results.|*[ProfilingTimePeriodTruncation](./common.md#profilingtimeperiodtruncation)*|
|<span class="no-wrap-code">[`volume`](../../reference/yaml/profiling/table-profiling-checks.md#tablevolumeprofilingchecksspec)</span>|Configuration of volume data quality checks on a table level.|*[TableVolumeProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tablevolumeprofilingchecksspec)*|
|<span class="no-wrap-code">[`timeliness`](../../reference/yaml/profiling/table-profiling-checks.md#tabletimelinessprofilingchecksspec)</span>|Configuration of timeliness checks on a table level. Timeliness checks detect anomalies like rapid row count changes.|*[TableTimelinessProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tabletimelinessprofilingchecksspec)*|
|<span class="no-wrap-code">[`accuracy`](../../reference/yaml/profiling/table-profiling-checks.md#tableaccuracyprofilingchecksspec)</span>|Configuration of accuracy checks on a table level. Accuracy checks compare the tested table with another reference table.|*[TableAccuracyProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tableaccuracyprofilingchecksspec)*|
|<span class="no-wrap-code">[`custom_sql`](../../reference/yaml/profiling/table-profiling-checks.md#tablecustomsqlprofilingchecksspec)</span>|Configuration of data quality checks that are evaluating custom SQL conditions and aggregated expressions.|*[TableCustomSqlProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tablecustomsqlprofilingchecksspec)*|
|<span class="no-wrap-code">[`availability`](../../reference/yaml/profiling/table-profiling-checks.md#tableavailabilityprofilingchecksspec)</span>|Configuration of the table availability data quality checks on a table level.|*[TableAvailabilityProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tableavailabilityprofilingchecksspec)*|
|<span class="no-wrap-code">[`schema`](../../reference/yaml/profiling/table-profiling-checks.md#tableschemaprofilingchecksspec)</span>|Configuration of schema (column count and schema) data quality checks on a table level.|*[TableSchemaProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tableschemaprofilingchecksspec)*|
|<span class="no-wrap-code">[`uniqueness`](../../reference/yaml/profiling/table-profiling-checks.md#tableuniquenessprofilingchecksspec)</span>|Configuration of uniqueness checks on a table level.|*[TableUniquenessProfilingChecksSpec](../../reference/yaml/profiling/table-profiling-checks.md#tableuniquenessprofilingchecksspec)*|
|<span class="no-wrap-code">[`comparisons`](#tablecomparisonprofilingchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[TableComparisonProfilingChecksSpecMap](#tablecomparisonprofilingchecksspecmap)*|
|<span class="no-wrap-code">[`custom`](#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](#customcheckspecmap)*|


___

## TableProfilingSetupStatusModel
Table status model that identifies which type of information is already collected, such as data quality checks are configured, or statistics collected.
 DQOps user interface uses this information to activate red borders to highlight tabs in the user interface that must be clicked to continue profiling the table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">`table_hash`</span>|Table hash that identifies the table using a unique hash code.|*long*|
|<span class="no-wrap-code">[`target`](./common.md#physicaltablename)</span>|Physical table details (a physical schema name and a physical table name).|*[PhysicalTableName](./common.md#physicaltablename)*|
|<span class="no-wrap-code">`basic_statistics_collected`</span>|The basic statistics were collected for this table. If this field returns false, the statistics were not collected and the user should collect basic statistics again.|*boolean*|
|<span class="no-wrap-code">`profiling_checks_configured`</span>|Returns true if the table has any profiling checks configured on the table, or any of its column. Returns false when the user should first generate a configuration of the profiling checks using the rule miner.|*boolean*|
|<span class="no-wrap-code">`monitoring_checks_configured`</span>|Returns true if the table has any monitoring checks configured on the table, or any of its column. Returns false when the user should first generate a configuration of the monitoring checks using the rule miner.|*boolean*|
|<span class="no-wrap-code">`partition_checks_configured`</span>|Returns true if the table has any partition checks configured on the table, or any of its column. The value is true also when the table is not configured to support partitioned checks, so asking the user to configure partition checks is useless. Returns false when the user should first generate a configuration of the partition checks using the rule miner.|*boolean*|
|<span class="no-wrap-code">`check_results_present`</span>|Returns true if the table has any recent data quality check results. Returns false when the user should run any checks to get any results.|*boolean*|


___

## TableStatisticsModel
Model that returns a summary of the table level statistics (the basic profiling results).


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">[`table`](./common.md#physicaltablename)</span>|Physical table name including the schema and table names.|*[PhysicalTableName](./common.md#physicaltablename)*|
|<span class="no-wrap-code">[`collect_table_statistics_job_template`](./jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors within this table, limited only to the table level statistics (row count, etc).|*[StatisticsCollectorSearchFilters](./jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">[`collect_table_and_column_statistics_job_template`](./jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors within this table, including statistics for all columns.|*[StatisticsCollectorSearchFilters](./jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">`can_collect_statistics`</span>|Boolean flag that decides if the current user can collect statistics.|*boolean*|


___

