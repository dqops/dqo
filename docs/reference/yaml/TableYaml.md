---
title: DQOps YAML file definitions
---
# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## TableYaml
Table and column definition file that defines a list of tables and columns that are covered by data quality checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`api_version`</span>|DQOps YAML schema version|*string*| |dqo/v1| |
|<span class="no-wrap-code ">`kind`</span>|File type|*enum*|*source*<br/>*table*<br/>*sensor*<br/>*provider_sensor*<br/>*rule*<br/>*check*<br/>*settings*<br/>*file_index*<br/>*dashboards*<br/>*default_schedules*<br/>*default_checks*<br/>*default_table_checks*<br/>*default_column_checks*<br/>*default_notifications*<br/>|table| |
|<span class="no-wrap-code ">[`spec`](./TableYaml.md#tablespec)</span>|Table specification object with the table metadata and the configuration of data quality checks|*[TableSpec](./TableYaml.md#tablespec)*| | | |









___


## TableSpec
Table specification that defines data quality tests that are enabled on a table and its columns.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`disabled`</span>|Disables all data quality checks on the table. Data quality checks will not be executed.|*boolean*| | | |
|<span class="no-wrap-code ">`stage`</span>|Stage name.|*string*| | | |
|<span class="no-wrap-code ">`priority`</span>|Table priority (1, 2, 3, 4, ...). The tables can be assigned a priority level. The table priority is copied into each data quality check result and a sensor result, enabling efficient grouping of more and less important tables during a data quality improvement project, when the data quality issues on higher priority tables are fixed before data quality issues on less important tables.|*integer*| | | |
|<span class="no-wrap-code ">`filter`</span>|SQL WHERE clause added to the sensor queries. Use replacement tokens {table} to replace the content with the full table name, {alias} to replace the content with the table alias of an analyzed table or {column} to replace the content with the analyzed column name.|*string*| | | |
|<span class="no-wrap-code ">[`timestamp_columns`](./TableYaml.md#timestampcolumnsspec)</span>|Column names that store the timestamps that identify the event (transaction) timestamp and the ingestion (inserted / loaded at) timestamps. Also configures the timestamp source for the date/time partitioned data quality checks (event timestamp or ingestion timestamp).|*[TimestampColumnsSpec](./TableYaml.md#timestampcolumnsspec)*| | | |
|<span class="no-wrap-code ">[`incremental_time_window`](./TableYaml.md#partitionincrementaltimewindowspec)</span>|Configuration of the time window for analyzing daily or monthly partitions. Specifies the number of recent days and recent months that are analyzed when the partitioned data quality checks are run in an incremental mode (the default mode).|*[PartitionIncrementalTimeWindowSpec](./TableYaml.md#partitionincrementaltimewindowspec)*| | | |
|<span class="no-wrap-code ">`default_grouping_name`</span>|The name of the default data grouping configuration that is applied on data quality checks. When a default data grouping is selected, all data quality checks run SQL queries with a GROUP BY clause, calculating separate data quality checks for each group of data. The data groupings are defined in the &#x27;groupings&#x27; dictionary (indexed by the data grouping name).|*string*| | | |
|<span class="no-wrap-code ">[`groupings`](./TableYaml.md#datagroupingconfigurationspecmap)</span>|Data grouping configurations list. Data grouping configurations are configured in two cases: (1) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a &#x27;country&#x27; column used for partitioning. (2) a tag is assigned to a table (within a data grouping level hierarchy), when the data is segmented at a table level (similar tables store the same information, but for different countries, etc.).|*[DataGroupingConfigurationSpecMap](./TableYaml.md#datagroupingconfigurationspecmap)*| | | |
|<span class="no-wrap-code ">[`table_comparisons`](./TableYaml.md#tablecomparisonconfigurationspecmap)</span>|Dictionary of data comparison configurations. Data comparison configurations are used for comparisons between data sources to compare this table (called the compared table) with other reference tables (the source of truth). The reference table&#x27;s metadata must be imported into DQOps, but the reference table may be located in another data source. DQOps will compare metrics calculated for groups of rows (using the GROUP BY clause). For each comparison, the user must specify a name of a data grouping. The number of data grouping dimensions in the parent table and the reference table defined in the selected data grouping configurations must match. DQOps will run the same data quality sensors on both the parent table (table under test) and the reference table (the source of truth), comparing the measures (sensor readouts) captured from both tables.|*[TableComparisonConfigurationSpecMap](./TableYaml.md#tablecomparisonconfigurationspecmap)*| | | |
|<span class="no-wrap-code ">[`incident_grouping`](./TableYaml.md#tableincidentgroupingspec)</span>|Incident grouping configuration with the overridden configuration at a table level. The configured field value in this object will override the default configuration from the connection level. Incident grouping level can be changed or incident creation can be disabled.|*[TableIncidentGroupingSpec](./TableYaml.md#tableincidentgroupingspec)*| | | |
|<span class="no-wrap-code ">[`owner`](./TableYaml.md#tableownerspec)</span>|Table owner information like the data steward name or the business application name.|*[TableOwnerSpec](./TableYaml.md#tableownerspec)*| | | |
|<span class="no-wrap-code ">[`profiling_checks`](./profiling/table-profiling-checks.md#tableprofilingcheckcategoriesspec)</span>|Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.|*[TableProfilingCheckCategoriesSpec](./profiling/table-profiling-checks.md#tableprofilingcheckcategoriesspec)*| | | |
|<span class="no-wrap-code ">[`monitoring_checks`](./TableYaml.md#tablemonitoringcheckcategoriesspec)</span>|Configuration of table level monitoring checks. Monitoring checks are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A monitoring check stores only the most recent data quality check result for each period of time.|*[TableMonitoringCheckCategoriesSpec](./TableYaml.md#tablemonitoringcheckcategoriesspec)*| | | |
|<span class="no-wrap-code ">[`partitioned_checks`](./TableYaml.md#tablepartitionedcheckcategoriesspec)</span>|Configuration of table level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.|*[TablePartitionedCheckCategoriesSpec](./TableYaml.md#tablepartitionedcheckcategoriesspec)*| | | |
|<span class="no-wrap-code ">[`statistics`](./TableYaml.md#tablestatisticscollectorsrootcategoriesspec)</span>|Configuration of table level data statistics collector (a basic profiler). Configures which statistics collectors are enabled and how they are configured.|*[TableStatisticsCollectorsRootCategoriesSpec](./TableYaml.md#tablestatisticscollectorsrootcategoriesspec)*| | | |
|<span class="no-wrap-code ">[`schedules_override`](./ConnectionYaml.md#defaultschedulesspec)</span>|Configuration of the job scheduler that runs data quality checks. The scheduler configuration is divided into types of checks that have different schedules.|*[DefaultSchedulesSpec](./ConnectionYaml.md#defaultschedulesspec)*| | | |
|<span class="no-wrap-code ">[`columns`](./TableYaml.md#columnspecmap)</span>|Dictionary of columns, indexed by a physical column name. Column specification contains the expected column data type and a list of column level data quality checks that are enabled for a column.|*[ColumnSpecMap](./TableYaml.md#columnspecmap)*| | | |
|<span class="no-wrap-code ">[`labels`](./ConnectionYaml.md#labelsetspec)</span>|Custom labels that were assigned to the table. Labels are used for searching for tables when filtered data quality checks are executed.|*[LabelSetSpec](./ConnectionYaml.md#labelsetspec)*| | | |
|<span class="no-wrap-code ">[`comments`](./profiling/table-profiling-checks.md#commentslistspec)</span>|Comments used for change tracking and documenting changes directly in the table data quality specification file.|*[CommentsListSpec](./profiling/table-profiling-checks.md#commentslistspec)*| | | |
|<span class="no-wrap-code ">[`file_format`](./TableYaml.md#fileformatspec)</span>|File format with the specification used as a source data. It overrides the connection spec&#x27;s file format when it is set|*[FileFormatSpec](./TableYaml.md#fileformatspec)*| | | |









___


## TimestampColumnsSpec
Configuration of timestamp related columns on a table level.
 Timestamp columns are used for timeliness data quality checks and for date/time partitioned checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`event_timestamp_column`</span>|Column name that identifies an event timestamp (date/time), such as a transaction timestamp, impression timestamp, event timestamp.|*string*| | | |
|<span class="no-wrap-code ">`ingestion_timestamp_column`</span>|Column name that contains the timestamp (or date/time) when the row was ingested (loaded, inserted) into the table. Use a column that is filled by the data pipeline or ETL process at the time of the data loading.|*string*| | | |
|<span class="no-wrap-code ">`partition_by_column`</span>|Column name that contains the date, datetime or timestamp column for date/time partitioned data. Partition checks (daily partition checks and monthly partition checks) use this column in a GROUP BY clause in order to detect data quality issues in each partition separately. It should be a DATE type, DATETIME type (using a local server time zone) or a TIMESTAMP type (a UTC absolute time).|*string*| | | |









___


## PartitionIncrementalTimeWindowSpec
Configuration of the time window for running incremental partition checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`daily_partitioning_recent_days`</span>|The number of recent days analyzed by daily partition checks in incremental mode. The default value is 7 last days.|*integer*| | | |
|<span class="no-wrap-code ">`daily_partitioning_include_today`</span>|Analyze also today&#x27;s data by daily partition checks in incremental mode. The default value is false, which means that the today&#x27;s and the future partitions are not analyzed. Only yesterday&#x27;s partition and previous daily partitions are analyzed because today&#x27;s data may still be incomplete. Change the value to &#x27;true&#x27; if the current day should also be analyzed. This change may require you to configure the schedule for daily checks correctly. The checks must run after the data load.|*boolean*| | | |
|<span class="no-wrap-code ">`monthly_partitioning_recent_months`</span>|The number of recent days analyzed by monthly partition checks in incremental mode. The default value is the previous calendar month.|*integer*| | | |
|<span class="no-wrap-code ">`monthly_partitioning_include_current_month`</span>|Analyze also this month&#x27;s data by monthly partition checks in incremental mode. The default value is false, which means that the current month is not analyzed. Future data is also filtered out because the current month may be incomplete. Change the value to &#x27;true&#x27; if the current month should also be analyzed before the end of the month. This change may require you to configure the schedule to run monthly checks more frequently (daily, hourly, etc.).|*boolean*| | | |









___


## DataGroupingConfigurationSpecMap
Dictionary of named data grouping configurations defined on a table level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`self`</span>||*Dict[string, [DataGroupingConfigurationSpec](./ConnectionYaml.md#datagroupingconfigurationspec)]*| | | |









___


## TableComparisonConfigurationSpecMap
Dictionary of data comparison configurations between the current table (the parent of this node) and another reference table (the source of truth)
 to which we are comparing the tables to measure the accuracy of the data.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`self`</span>||*Dict[string, [TableComparisonConfigurationSpec](./TableYaml.md#tablecomparisonconfigurationspec)]*| | | |









___


## TableComparisonConfigurationSpec
Identifies a data comparison configuration between a parent table (the compared table) and the target table from another data source (a reference table).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`reference_table_connection_name`</span>|The name of the connection in DQOp where the reference table (the source of truth) is configured. When the connection name is not provided, DQOps will find the reference table on the connection of the parent table.|*string*| | | |
|<span class="no-wrap-code ">`reference_table_schema_name`</span>|The name of the schema where the reference table is imported into DQOps. The reference table&#x27;s metadata must be imported into DQOps.|*string*| | | |
|<span class="no-wrap-code ">`reference_table_name`</span>|The name of the reference table that is imported into DQOps. The reference table&#x27;s metadata must be imported into DQOps.|*string*| | | |
|<span class="no-wrap-code ">`compared_table_filter`</span>|Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the compared table. This expression must be a SQL expression that will be added to the WHERE clause when querying the compared table.|*string*| | | |
|<span class="no-wrap-code ">`reference_table_filter`</span>|Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the reference table (the source of truth). This expression must be a SQL expression that will be added to the WHERE clause when querying the reference table.|*string*| | | |
|<span class="no-wrap-code ">`check_type`</span>|The type of checks (profiling, monitoring, partitioned) that this check comparison configuration is applicable. The default value is &#x27;profiling&#x27;.|*enum*|*profiling*<br/>*monitoring*<br/>*partitioned*<br/>| | |
|<span class="no-wrap-code ">`time_scale`</span>|The time scale that this check comparison configuration is applicable. Supported values are &#x27;daily&#x27; and &#x27;monthly&#x27; for monitoring and partitioned checks or an empty value for profiling checks.|*enum*|*daily*<br/>*monthly*<br/>| | |
|<span class="no-wrap-code ">[`grouping_columns`](./TableYaml.md#tablecomparisongroupingcolumnspairslistspec)</span>|List of column pairs from both the compared table and the reference table that are used in a GROUP BY clause  for grouping both the compared table and the reference table (the source of truth). The columns are used in the next of the table comparison to join the results of data groups (row counts, sums of columns) between the compared table and the reference table to compare the differences.|*[TableComparisonGroupingColumnsPairsListSpec](./TableYaml.md#tablecomparisongroupingcolumnspairslistspec)*| | | |









___


## TableComparisonGroupingColumnsPairsListSpec
List of column pairs used for grouping and joining in the table comparison checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`self`</span>||*List[[TableComparisonGroupingColumnsPairSpec](./TableYaml.md#tablecomparisongroupingcolumnspairspec)]*| | | |









___


## TableComparisonGroupingColumnsPairSpec
Configuration of a pair of columns on the compared table and the reference table (the source of truth) that are joined
 and used for grouping to perform data comparison of aggregated results (sums of columns, row counts, etc.).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`compared_table_column_name`</span>|The name of the column on the compared table (the parent table) that is used in the GROUP BY clause to group rows before compared aggregates (row counts, sums, etc.) are calculated. This column is also used to join (match) results to the reference table.|*string*| | | |
|<span class="no-wrap-code ">`reference_table_column_name`</span>|The name of the column on the reference table (the source of truth) that is used in the GROUP BY clause to group rows before compared aggregates (row counts, sums, etc.) are calculated. This column is also used to join (match) results to the compared table.|*string*| | | |









___


## TableIncidentGroupingSpec
Configuration of data quality incident grouping on a table level. Defines how similar data quality issues are grouped into incidents.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`grouping_level`</span>|Grouping level of failed data quality checks for creating higher level data quality incidents. The default grouping level is by a table, a data quality dimension and a check category (i.e. a datatype data quality incident detected on a table X in the numeric checks category).|*enum*|*table*<br/>*table_dimension*<br/>*table_dimension_category*<br/>*table_dimension_category_type*<br/>*table_dimension_category_name*<br/>| | |
|<span class="no-wrap-code ">`minimum_severity`</span>|Minimum severity level of data quality issues that are grouped into incidents. The default minimum severity level is &#x27;warning&#x27;. Other supported severity levels are &#x27;error&#x27; and &#x27;fatal&#x27;.|*enum*|*warning*<br/>*error*<br/>*fatal*<br/>| | |
|<span class="no-wrap-code ">`divide_by_data_group`</span>|Create separate data quality incidents for each data group, creating different incidents for different groups of rows. By default, data groups are ignored for grouping data quality issues into data quality incidents.|*boolean*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables data quality incident creation for failed data quality checks on the table.|*boolean*| | | |









___


## TableOwnerSpec
Table owner information.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`data_steward`</span>|Data steward name|*string*| | | |
|<span class="no-wrap-code ">`application`</span>|Business application name|*string*| | | |









___


## TableMonitoringCheckCategoriesSpec
Container of table level monitoring, divided by the time window (daily, monthly, etc.)









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily`](./monitoring/table-daily-monitoring-checks.md#tabledailymonitoringcheckcategoriesspec)</span>|Configuration of daily monitoring evaluated at a table level.|*[TableDailyMonitoringCheckCategoriesSpec](./monitoring/table-daily-monitoring-checks.md#tabledailymonitoringcheckcategoriesspec)*| | | |
|<span class="no-wrap-code ">[`monthly`](./monitoring/table-monthly-monitoring-checks.md#tablemonthlymonitoringcheckcategoriesspec)</span>|Configuration of monthly monitoring evaluated at a table level.|*[TableMonthlyMonitoringCheckCategoriesSpec](./monitoring/table-monthly-monitoring-checks.md#tablemonthlymonitoringcheckcategoriesspec)*| | | |









___


## TablePartitionedCheckCategoriesSpec
Container of table level partitioned checks, divided by the time window (daily, monthly, etc.)









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily`](./partitioned/table-daily-partitioned-checks.md#tabledailypartitionedcheckcategoriesspec)</span>|Configuration of day partitioned data quality checks evaluated at a table level.|*[TableDailyPartitionedCheckCategoriesSpec](./partitioned/table-daily-partitioned-checks.md#tabledailypartitionedcheckcategoriesspec)*| | | |
|<span class="no-wrap-code ">[`monthly`](./partitioned/table-monthly-partitioned-checks.md#tablemonthlypartitionedcheckcategoriesspec)</span>|Configuration of monthly partitioned data quality checks evaluated at a table level..|*[TableMonthlyPartitionedCheckCategoriesSpec](./partitioned/table-monthly-partitioned-checks.md#tablemonthlypartitionedcheckcategoriesspec)*| | | |









___


## TableStatisticsCollectorsRootCategoriesSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`volume`](./TableYaml.md#tablevolumestatisticscollectorsspec)</span>|Configuration of volume statistics collectors on a table level.|*[TableVolumeStatisticsCollectorsSpec](./TableYaml.md#tablevolumestatisticscollectorsspec)*| | | |
|<span class="no-wrap-code ">[`schema`](./TableYaml.md#tableschemastatisticscollectorsspec)</span>||*[TableSchemaStatisticsCollectorsSpec](./TableYaml.md#tableschemastatisticscollectorsspec)*| | | |









___


## TableVolumeStatisticsCollectorsSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`row_count`](./TableYaml.md#tablevolumerowcountstatisticscollectorspec)</span>|Configuration of the row count profiler.|*[TableVolumeRowCountStatisticsCollectorSpec](./TableYaml.md#tablevolumerowcountstatisticscollectorspec)*| | | |









___


## TableVolumeRowCountStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../sensors/table/volume-table-sensors.md#row-count)</span>|Profiler parameters|*[TableVolumeRowCountSensorParametersSpec](../sensors/table/volume-table-sensors.md#row-count)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## TableSchemaStatisticsCollectorsSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`column_count`](./TableYaml.md#tableschemacolumncountstatisticscollectorspec)</span>|Configuration of the column count profiler.|*[TableSchemaColumnCountStatisticsCollectorSpec](./TableYaml.md#tableschemacolumncountstatisticscollectorspec)*| | | |









___


## TableSchemaColumnCountStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../sensors/table/schema-table-sensors.md#column-count)</span>|Profiler parameters|*[TableColumnCountSensorParametersSpec](../sensors/table/schema-table-sensors.md#column-count)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## ColumnSpecMap
Dictionary of columns indexed by a physical column name.















___


## ColumnSpec
Column specification that identifies a single column.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`disabled`</span>|Disables all data quality checks on the column. Data quality checks will not be executed.|*boolean*| | | |
|<span class="no-wrap-code ">`sql_expression`</span>|SQL expression used for calculated fields or when additional column value transformation is required before the column can be used for analysis with data quality checks (data type conversion, transformation). It should be an SQL expression that uses the SQL language of the analyzed database type. Use the replacement tokens {table} to replace the content with the full table name, {alias} to replace the content with the table alias of the table under analysis, or {column} to replace the content with the analyzed column name. An example of extracting a value from a string column storing JSON in PostgreSQL: &quot;{column}::json-&gt;&#x27;address&#x27;-&gt;&#x27;zip&#x27;&quot;.|*string*| | | |
|<span class="no-wrap-code ">[`type_snapshot`](./TableYaml.md#columntypesnapshotspec)</span>|Column data type that was retrieved when the table metadata was imported.|*[ColumnTypeSnapshotSpec](./TableYaml.md#columntypesnapshotspec)*| | | |
|<span class="no-wrap-code ">`id`</span>|True when this column is a part of the primary key or a business key that identifies a row. Error sampling captures values of id columns to identify the row where the error sample was found.|*boolean*| | | |
|<span class="no-wrap-code ">[`profiling_checks`](./profiling/column-profiling-checks.md#columnprofilingcheckcategoriesspec)</span>|Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.|*[ColumnProfilingCheckCategoriesSpec](./profiling/column-profiling-checks.md#columnprofilingcheckcategoriesspec)*| | | |
|<span class="no-wrap-code ">[`monitoring_checks`](./TableYaml.md#columnmonitoringcheckcategoriesspec)</span>|Configuration of column level monitoring checks. Monitoring are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A monitoring stores only the most recent data quality check result for each period of time.|*[ColumnMonitoringCheckCategoriesSpec](./TableYaml.md#columnmonitoringcheckcategoriesspec)*| | | |
|<span class="no-wrap-code ">[`partitioned_checks`](./TableYaml.md#columnpartitionedcheckcategoriesspec)</span>|Configuration of column level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.|*[ColumnPartitionedCheckCategoriesSpec](./TableYaml.md#columnpartitionedcheckcategoriesspec)*| | | |
|<span class="no-wrap-code ">[`statistics`](./TableYaml.md#columnstatisticscollectorsrootcategoriesspec)</span>|Custom configuration of a column level statistics collector (a basic profiler). Enables customization of the statistics collector settings when the collector is analysing this column.|*[ColumnStatisticsCollectorsRootCategoriesSpec](./TableYaml.md#columnstatisticscollectorsrootcategoriesspec)*| | | |
|<span class="no-wrap-code ">[`labels`](./ConnectionYaml.md#labelsetspec)</span>|Custom labels that were assigned to the column. Labels are used for searching for columns when filtered data quality checks are executed.|*[LabelSetSpec](./ConnectionYaml.md#labelsetspec)*| | | |
|<span class="no-wrap-code ">[`comments`](./profiling/table-profiling-checks.md#commentslistspec)</span>|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|*[CommentsListSpec](./profiling/table-profiling-checks.md#commentslistspec)*| | | |









___


## ColumnTypeSnapshotSpec
Stores the column data type captured at the time of the table metadata import.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`column_type`</span>|Column data type using the monitored database type names.|*string*| | | |
|<span class="no-wrap-code ">`nullable`</span>|Column is nullable.|*boolean*| | | |
|<span class="no-wrap-code ">`length`</span>|Maximum length of text and binary columns.|*integer*| | | |
|<span class="no-wrap-code ">`precision`</span>|Precision of a numeric (decimal) data type.|*integer*| | | |
|<span class="no-wrap-code ">`scale`</span>|Scale of a numeric (decimal) data type.|*integer*| | | |
|<span class="no-wrap-code ">`nested`</span>|This field is a nested field inside another STRUCT. It is used to identify nested fields in JSON files.|*boolean*| | | |









___


## ColumnMonitoringCheckCategoriesSpec
Container of column level monitoring, divided by the time window (daily, monthly, etc.)









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily`](./monitoring/column-daily-monitoring-checks.md#columndailymonitoringcheckcategoriesspec)</span>|Configuration of daily monitoring evaluated at a column level.|*[ColumnDailyMonitoringCheckCategoriesSpec](./monitoring/column-daily-monitoring-checks.md#columndailymonitoringcheckcategoriesspec)*| | | |
|<span class="no-wrap-code ">[`monthly`](./monitoring/column-monthly-monitoring-checks.md#columnmonthlymonitoringcheckcategoriesspec)</span>|Configuration of monthly monitoring evaluated at a column level.|*[ColumnMonthlyMonitoringCheckCategoriesSpec](./monitoring/column-monthly-monitoring-checks.md#columnmonthlymonitoringcheckcategoriesspec)*| | | |









___


## ColumnPartitionedCheckCategoriesSpec
Container of column level partitioned checks, divided by the time window (daily, monthly, etc.)









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily`](./partitioned/column-daily-partitioned-checks.md#columndailypartitionedcheckcategoriesspec)</span>|Configuration of day partitioned data quality checks evaluated at a column level.|*[ColumnDailyPartitionedCheckCategoriesSpec](./partitioned/column-daily-partitioned-checks.md#columndailypartitionedcheckcategoriesspec)*| | | |
|<span class="no-wrap-code ">[`monthly`](./partitioned/column-monthly-partitioned-checks.md#columnmonthlypartitionedcheckcategoriesspec)</span>|Configuration of monthly partitioned data quality checks evaluated at a column level.|*[ColumnMonthlyPartitionedCheckCategoriesSpec](./partitioned/column-monthly-partitioned-checks.md#columnmonthlypartitionedcheckcategoriesspec)*| | | |









___


## ColumnStatisticsCollectorsRootCategoriesSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`nulls`](./TableYaml.md#columnnullsstatisticscollectorsspec)</span>|Configuration of null values profilers on a column level.|*[ColumnNullsStatisticsCollectorsSpec](./TableYaml.md#columnnullsstatisticscollectorsspec)*| | | |
|<span class="no-wrap-code ">[`text`](./TableYaml.md#columntextstatisticscollectorsspec)</span>|Configuration of text column profilers on a column level.|*[ColumnTextStatisticsCollectorsSpec](./TableYaml.md#columntextstatisticscollectorsspec)*| | | |
|<span class="no-wrap-code ">[`uniqueness`](./TableYaml.md#columnuniquenessstatisticscollectorsspec)</span>|Configuration of profilers that analyse uniqueness of values (distinct count).|*[ColumnUniquenessStatisticsCollectorsSpec](./TableYaml.md#columnuniquenessstatisticscollectorsspec)*| | | |
|<span class="no-wrap-code ">[`range`](./TableYaml.md#columnrangestatisticscollectorsspec)</span>|Configuration of profilers that analyse the range of values (min, max).|*[ColumnRangeStatisticsCollectorsSpec](./TableYaml.md#columnrangestatisticscollectorsspec)*| | | |
|<span class="no-wrap-code ">[`sampling`](./TableYaml.md#columnsamplingstatisticscollectorsspec)</span>|Configuration of profilers that collect the column samples.|*[ColumnSamplingStatisticsCollectorsSpec](./TableYaml.md#columnsamplingstatisticscollectorsspec)*| | | |









___


## ColumnNullsStatisticsCollectorsSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`nulls_count`](./TableYaml.md#columnnullsnullscountstatisticscollectorspec)</span>|Configuration of the profiler that counts null column values.|*[ColumnNullsNullsCountStatisticsCollectorSpec](./TableYaml.md#columnnullsnullscountstatisticscollectorspec)*| | | |
|<span class="no-wrap-code ">[`nulls_percent`](./TableYaml.md#columnnullsnullspercentstatisticscollectorspec)</span>|Configuration of the profiler that measures the percentage of null values.|*[ColumnNullsNullsPercentStatisticsCollectorSpec](./TableYaml.md#columnnullsnullspercentstatisticscollectorspec)*| | | |
|<span class="no-wrap-code ">[`not_nulls_count`](./TableYaml.md#columnnullsnotnullscountstatisticscollectorspec)</span>|Configuration of the profiler that counts not null column values.|*[ColumnNullsNotNullsCountStatisticsCollectorSpec](./TableYaml.md#columnnullsnotnullscountstatisticscollectorspec)*| | | |
|<span class="no-wrap-code ">[`not_nulls_percent`](./TableYaml.md#columnnullsnotnullspercentstatisticscollectorspec)</span>|Configuration of the profiler that measures the percentage of not null values.|*[ColumnNullsNotNullsPercentStatisticsCollectorSpec](./TableYaml.md#columnnullsnotnullspercentstatisticscollectorspec)*| | | |









___


## ColumnNullsNullsCountStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../sensors/column/nulls-column-sensors.md#null-count)</span>|Profiler parameters|*[ColumnNullsNullsCountSensorParametersSpec](../sensors/column/nulls-column-sensors.md#null-count)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## ColumnNullsNullsPercentStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../sensors/column/nulls-column-sensors.md#null-percent)</span>|Profiler parameters|*[ColumnNullsNullsPercentSensorParametersSpec](../sensors/column/nulls-column-sensors.md#null-percent)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## ColumnNullsNotNullsCountStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../sensors/column/nulls-column-sensors.md#not-null-count)</span>|Profiler parameters|*[ColumnNullsNotNullsCountSensorParametersSpec](../sensors/column/nulls-column-sensors.md#not-null-count)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## ColumnNullsNotNullsPercentStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../sensors/column/nulls-column-sensors.md#not-null-percent)</span>|Profiler parameters|*[ColumnNullsNotNullsPercentSensorParametersSpec](../sensors/column/nulls-column-sensors.md#not-null-percent)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## ColumnTextStatisticsCollectorsSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`text_max_length`](./TableYaml.md#columntexttextmaxlengthstatisticscollectorspec)</span>|Configuration of the profiler that finds the maximum text length.|*[ColumnTextTextMaxLengthStatisticsCollectorSpec](./TableYaml.md#columntexttextmaxlengthstatisticscollectorspec)*| | | |
|<span class="no-wrap-code ">[`text_mean_length`](./TableYaml.md#columntexttextmeanlengthstatisticscollectorspec)</span>|Configuration of the profiler that finds the mean text length.|*[ColumnTextTextMeanLengthStatisticsCollectorSpec](./TableYaml.md#columntexttextmeanlengthstatisticscollectorspec)*| | | |
|<span class="no-wrap-code ">[`text_min_length`](./TableYaml.md#columntexttextminlengthstatisticscollectorspec)</span>|Configuration of the profiler that finds the min text length.|*[ColumnTextTextMinLengthStatisticsCollectorSpec](./TableYaml.md#columntexttextminlengthstatisticscollectorspec)*| | | |
|<span class="no-wrap-code ">[`text_datatype_detect`](./TableYaml.md#columntexttextdatatypedetectstatisticscollectorspec)</span>|Configuration of the profiler that detects datatype.|*[ColumnTextTextDatatypeDetectStatisticsCollectorSpec](./TableYaml.md#columntexttextdatatypedetectstatisticscollectorspec)*| | | |









___


## ColumnTextTextMaxLengthStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../sensors/column/text-column-sensors.md#text-max-length)</span>|Profiler parameters|*[ColumnTextTextMaxLengthSensorParametersSpec](../sensors/column/text-column-sensors.md#text-max-length)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## ColumnTextTextMeanLengthStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../sensors/column/text-column-sensors.md#text-mean-length)</span>|Profiler parameters|*[ColumnTextTextMeanLengthSensorParametersSpec](../sensors/column/text-column-sensors.md#text-mean-length)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## ColumnTextTextMinLengthStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../sensors/column/text-column-sensors.md#text-min-length)</span>|Profiler parameters|*[ColumnTextTextMinLengthSensorParametersSpec](../sensors/column/text-column-sensors.md#text-min-length)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## ColumnTextTextDatatypeDetectStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../sensors/column/datatype-column-sensors.md#string-datatype-detect)</span>|Profiler parameters|*[ColumnDatatypeStringDatatypeDetectSensorParametersSpec](../sensors/column/datatype-column-sensors.md#string-datatype-detect)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## ColumnUniquenessStatisticsCollectorsSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`distinct_count`](./TableYaml.md#columnuniquenessdistinctcountstatisticscollectorspec)</span>|Configuration of the profiler that counts distinct column values.|*[ColumnUniquenessDistinctCountStatisticsCollectorSpec](./TableYaml.md#columnuniquenessdistinctcountstatisticscollectorspec)*| | | |
|<span class="no-wrap-code ">[`distinct_percent`](./TableYaml.md#columnuniquenessdistinctpercentstatisticscollectorspec)</span>|Configuration of the profiler that measure the percentage of distinct column values.|*[ColumnUniquenessDistinctPercentStatisticsCollectorSpec](./TableYaml.md#columnuniquenessdistinctpercentstatisticscollectorspec)*| | | |
|<span class="no-wrap-code ">[`duplicate_count`](./TableYaml.md#columnuniquenessduplicatecountstatisticscollectorspec)</span>|Configuration of the profiler that counts duplicate column values.|*[ColumnUniquenessDuplicateCountStatisticsCollectorSpec](./TableYaml.md#columnuniquenessduplicatecountstatisticscollectorspec)*| | | |
|<span class="no-wrap-code ">[`duplicate_percent`](./TableYaml.md#columnuniquenessduplicatepercentstatisticscollectorspec)</span>|Configuration of the profiler that measure the percentage of duplicate column values.|*[ColumnUniquenessDuplicatePercentStatisticsCollectorSpec](./TableYaml.md#columnuniquenessduplicatepercentstatisticscollectorspec)*| | | |









___


## ColumnUniquenessDistinctCountStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../sensors/column/uniqueness-column-sensors.md#distinct-count)</span>|Profiler parameters|*[ColumnUniquenessDistinctCountSensorParametersSpec](../sensors/column/uniqueness-column-sensors.md#distinct-count)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## ColumnUniquenessDistinctPercentStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../sensors/column/uniqueness-column-sensors.md#distinct-percent)</span>|Profiler parameters|*[ColumnUniquenessDistinctPercentSensorParametersSpec](../sensors/column/uniqueness-column-sensors.md#distinct-percent)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## ColumnUniquenessDuplicateCountStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../sensors/column/uniqueness-column-sensors.md#duplicate-count)</span>|Profiler parameters|*[ColumnUniquenessDuplicateCountSensorParametersSpec](../sensors/column/uniqueness-column-sensors.md#duplicate-count)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## ColumnUniquenessDuplicatePercentStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../sensors/column/uniqueness-column-sensors.md#duplicate-percent)</span>|Profiler parameters|*[ColumnUniquenessDuplicatePercentSensorParametersSpec](../sensors/column/uniqueness-column-sensors.md#duplicate-percent)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## ColumnRangeStatisticsCollectorsSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`min_value`](./TableYaml.md#columnrangeminvaluestatisticscollectorspec)</span>|Configuration of the profiler that finds the minimum value in the column.|*[ColumnRangeMinValueStatisticsCollectorSpec](./TableYaml.md#columnrangeminvaluestatisticscollectorspec)*| | | |
|<span class="no-wrap-code ">[`median_value`](./TableYaml.md#columnrangemedianvaluestatisticscollectorspec)</span>|Configuration of the profiler that finds the median value in the column.|*[ColumnRangeMedianValueStatisticsCollectorSpec](./TableYaml.md#columnrangemedianvaluestatisticscollectorspec)*| | | |
|<span class="no-wrap-code ">[`max_value`](./TableYaml.md#columnrangemaxvaluestatisticscollectorspec)</span>|Configuration of the profiler that finds the maximum value in the column.|*[ColumnRangeMaxValueStatisticsCollectorSpec](./TableYaml.md#columnrangemaxvaluestatisticscollectorspec)*| | | |
|<span class="no-wrap-code ">[`sum_value`](./TableYaml.md#columnrangesumvaluestatisticscollectorspec)</span>|Configuration of the profiler that finds the sum value in the column.|*[ColumnRangeSumValueStatisticsCollectorSpec](./TableYaml.md#columnrangesumvaluestatisticscollectorspec)*| | | |









___


## ColumnRangeMinValueStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](./TableYaml.md#columnrangeminvaluesensorparametersspec)</span>|Profiler parameters|*[ColumnRangeMinValueSensorParametersSpec](./TableYaml.md#columnrangeminvaluesensorparametersspec)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## ColumnRangeMinValueSensorParametersSpec
Column level sensor that finds the minimum value. It works on any data type that supports the MIN functions.
 The returned data type matches the data type of the column (can return date, integer, string, datetime, etc.).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`filter`</span>|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|*string*| | | |









___


## ColumnRangeMedianValueStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../sensors/column/numeric-column-sensors.md#percentile)</span>|Profiler parameters|*[ColumnNumericMedianSensorParametersSpec](../sensors/column/numeric-column-sensors.md#percentile)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## ColumnRangeMaxValueStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](./TableYaml.md#columnrangemaxvaluesensorparametersspec)</span>|Profiler parameters|*[ColumnRangeMaxValueSensorParametersSpec](./TableYaml.md#columnrangemaxvaluesensorparametersspec)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## ColumnRangeMaxValueSensorParametersSpec
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (can return date, integer, string, datetime, etc.).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`filter`</span>|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|*string*| | | |









___


## ColumnRangeSumValueStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../sensors/column/numeric-column-sensors.md#sum)</span>|Profiler parameters|*[ColumnNumericSumSensorParametersSpec](../sensors/column/numeric-column-sensors.md#sum)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## ColumnSamplingStatisticsCollectorsSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`column_samples`](./TableYaml.md#columnsamplingcolumnsamplesstatisticscollectorspec)</span>|Configuration of the profiler that finds the maximum string length.|*[ColumnSamplingColumnSamplesStatisticsCollectorSpec](./TableYaml.md#columnsamplingcolumnsamplesstatisticscollectorspec)*| | | |









___


## ColumnSamplingColumnSamplesStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../sensors/column/sampling-column-sensors.md#column-samples)</span>|Profiler parameters|*[ColumnSamplingColumnSamplesSensorParametersSpec](../sensors/column/sampling-column-sensors.md#column-samples)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables this profiler. Only enabled profilers are executed during a profiling process.|*boolean*| | | |









___


## FileFormatSpec
File format specification for data loaded from the physical files of one of supported formats.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`csv`](./ConnectionYaml.md#csvfileformatspec)</span>|Csv file format specification.|*[CsvFileFormatSpec](./ConnectionYaml.md#csvfileformatspec)*| | | |
|<span class="no-wrap-code ">[`json`](./ConnectionYaml.md#jsonfileformatspec)</span>|Json file format specification.|*[JsonFileFormatSpec](./ConnectionYaml.md#jsonfileformatspec)*| | | |
|<span class="no-wrap-code ">[`parquet`](./ConnectionYaml.md#parquetfileformatspec)</span>|Parquet file format specification.|*[ParquetFileFormatSpec](./ConnectionYaml.md#parquetfileformatspec)*| | | |
|<span class="no-wrap-code ">[`file_paths`](./TableYaml.md#filepathlistspec)</span>|The list of paths to files with data that are used as a source.|*[FilePathListSpec](./TableYaml.md#filepathlistspec)*| | | |









___


## FilePathListSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`self`</span>||*List[string]*| | | |









___


