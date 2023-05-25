
## TableYaml  
Table and column definition file that defines a list of tables and columns that are covered by data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|table<br/>dashboards<br/>source<br/>sensor<br/>check<br/>rule<br/>file_index<br/>settings<br/>provider_sensor<br/>| | |
|[spec](#tablespec)||[TableSpec](#tablespec)| | | |









___  

## TableSpec  
Table specification that defines data quality tests that are enabled on a table and columns.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|disabled|Disables all data quality checks on the table. Data quality checks will not be executed.|boolean| | | |
|stage|Stage name.|string| | | |
|priority|Table priority (1, 2, 3, 4, ...). The tables could be assigned a priority level. The table priority is copied into each data quality check result and a sensor result, enabling efficient grouping of more and less important tables during a data quality improvement project, when the data quality issues on higher priority tables are fixed before data quality issues on less important tables.|integer| | | |
|filter|SQL WHERE clause added to the sensor queries. Use replacement tokens {table} to replace the content with the full table name, {alias} to replace the content with the table alias of an analyzed table or {column} to replace the content with the analyzed column name.|string| | | |
|[timestamp_columns](#timestampcolumnsspec)|Column names that store the timestamps that identify the event (transaction) timestamp and the ingestion (inserted / loaded at) timestamps. Also configures the timestamp source for the date/time partitioned data quality checks (event timestamp or ingestion timestamp).|[TimestampColumnsSpec](#timestampcolumnsspec)| | | |
|[incremental_time_window](#partitionincrementaltimewindowspec)|Configuration of the time window for analyzing daily or monthly partitions. Specifies the number of recent days and recent months that are analyzed when the partitioned data quality checks are run in an incremental mode (the default mode).|[PartitionIncrementalTimeWindowSpec](#partitionincrementaltimewindowspec)| | | |
|[data_streams](#datastreammappingspecmap)|Data stream mappings list. Data streams are configured in two cases: (1) a tag is assigned to a table (within a data stream level hierarchy), when the data is segmented at a table level (similar tables store the same information, but for different countries, etc.). (2) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a &#x27;country&#x27; column used for partitioning.|[DataStreamMappingSpecMap](#datastreammappingspecmap)| | | |
|[incident_grouping](#tableincidentgroupingspec)|Incident grouping configuration with the overridden configuration at a table level. The field value in this object that are configured will override the default configuration from the connection level. The incident grouping level could be changed or incident creation could be disabled.|[TableIncidentGroupingSpec](#tableincidentgroupingspec)| | | |
|[owner](#tableownerspec)|Table owner information like the data steward name or the business application name.|[TableOwnerSpec](#tableownerspec)| | | |
|[profiling_checks](#tableprofilingcheckcategoriesspec)|Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.|[TableProfilingCheckCategoriesSpec](#tableprofilingcheckcategoriesspec)| | | |
|[recurring_checks](#tablerecurringchecksspec)|Configuration of table level recurring checks. Recurring checks are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A recurring check stores only the most recent data quality check result for each period of time.|[TableRecurringChecksSpec](#tablerecurringchecksspec)| | | |
|[partitioned_checks](#tablepartitionedchecksrootspec)|Configuration of table level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.|[TablePartitionedChecksRootSpec](#tablepartitionedchecksrootspec)| | | |
|[statistics](#tablestatisticscollectorsrootcategoriesspec)|Configuration of table level data statistics collector (a basic profiler). Configures which statistics collectors are enabled and how they are configured.|[TableStatisticsCollectorsRootCategoriesSpec](#tablestatisticscollectorsrootcategoriesspec)| | | |
|[schedules_override](#recurringschedulesspec)|Configuration of the job scheduler that runs data quality checks. The scheduler configuration is divided into types of checks that have different schedules.|[RecurringSchedulesSpec](#recurringschedulesspec)| | | |
|[columns](#columnspecmap)|Dictionary of columns, indexed by a physical column name. Column specification contains the expected column data type and a list of column level data quality checks that are enabled for a column.|[ColumnSpecMap](#columnspecmap)| | | |
|[labels](#labelsetspec)|Custom labels that were assigned to the table. Labels are used for searching for tables when filtered data quality checks are executed.|[LabelSetSpec](#labelsetspec)| | | |
|[comments](#commentslistspec)|Comments used for change tracking and documenting changes directly in the table data quality specification file.|[CommentsListSpec](#commentslistspec)| | | |









___  

## TimestampColumnsSpec  
Configuration of timestamp related columns on a table level.
 Timestamp columns are used for timeliness data quality checks and for date/time partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|event_timestamp_column|Column name that identifies an event timestamp (date/time), such as a transaction timestamp, impression timestamp, event timestamp.|column_name| | | |
|ingestion_timestamp_column|Column name that contains the timestamp (or date/time) when the row was ingested (loaded, inserted) into the table. Use a column that is filled by the data pipeline or ETL process at the time of the data loading.|column_name| | | |
|partition_by_column|Column name that contains the date, datetime or timestamp column for date/time partitioned data. Partition checks (daily partition checks and monthly partition checks) use this column in a GROUP BY clause in order to detect data quality issues in each partition separately. It should be a DATE type, DATETIME type (using a local server time zone) or a TIMESTAMP type (a UTC absolute time).|column_name| | | |









___  

## PartitionIncrementalTimeWindowSpec  
Configuration of the time window for running incremental partition checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|daily_partitioning_recent_days|Number of recent days that are analyzed by daily partitioned checks in incremental mode. The default value is 7 days back.|integer| | | |
|daily_partitioning_include_today|Analyze also today&#x27;s data by daily partitioned checks in incremental mode. The default value is false, which means that the today&#x27;s and the future partitions are not analyzed, only yesterday&#x27;s partition and earlier daily partitions are analyzed because today&#x27;s data could be still incomplete. Change the value to &#x27;true&#x27; if the current day should be also analyzed. The change may require configuring the schedule for daily checks correctly, to run after the data load.|boolean| | | |
|monthly_partitioning_recent_months|Number of recent months that are analyzed by monthly partitioned checks in incremental mode. The default value is 1 month back which means the previous calendar month.|integer| | | |
|monthly_partitioning_include_current_month|Analyze also this month&#x27;s data by monthly partitioned checks in incremental mode. The default value is false, which means that the current month is not analyzed and future data is also filtered out because the current month could be incomplete. Set the value to &#x27;true&#x27; if the current month should be analyzed before the end of the month. The schedule for running monthly checks should be also configured to run more frequently (daily, hourly, etc.).|boolean| | | |









___  

## DataStreamMappingSpecMap  
Dictionary of named data stream mappings defined on a table level.  
  










___  

## DataStreamMappingSpec  
Configuration of the data stream that is used for a table.
 Data streams levels may be hardcoded if we have different (but similar) tables for different business areas (countries, product groups).
 We can also pull data stream levels directly from the database if a table has a column that identifies a business area.
 Data streams dynamically identified in the database are added to the GROUP BY clause. Sensor values are extracted for each data stream separately,
 a time series is build for each data stream separately.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[level_1](#datastreamlevelspec)|Data stream level 1 configuration.|[DataStreamLevelSpec](#datastreamlevelspec)| | | |
|[level_2](#datastreamlevelspec)|Data stream level 2 configuration.|[DataStreamLevelSpec](#datastreamlevelspec)| | | |
|[level_3](#datastreamlevelspec)|Data stream level 3 configuration.|[DataStreamLevelSpec](#datastreamlevelspec)| | | |
|[level_4](#datastreamlevelspec)|Data stream level 4 configuration.|[DataStreamLevelSpec](#datastreamlevelspec)| | | |
|[level_5](#datastreamlevelspec)|Data stream level 5 configuration.|[DataStreamLevelSpec](#datastreamlevelspec)| | | |
|[level_6](#datastreamlevelspec)|Data stream level 6 configuration.|[DataStreamLevelSpec](#datastreamlevelspec)| | | |
|[level_7](#datastreamlevelspec)|Data stream level 7 configuration.|[DataStreamLevelSpec](#datastreamlevelspec)| | | |
|[level_8](#datastreamlevelspec)|Data stream level 8 configuration.|[DataStreamLevelSpec](#datastreamlevelspec)| | | |
|[level_9](#datastreamlevelspec)|Data stream level 9 configuration.|[DataStreamLevelSpec](#datastreamlevelspec)| | | |









___  

## DataStreamLevelSpec  
Single data stream level configuration. A data stream level may be configured as a hardcoded value or a mapping to a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|source|The source of the data stream level value. The default stream level source is a tag. Assign a tag when there are multiple similar tables that store the same data for different areas (countries, etc.). This could be a country name if a table or partition stores information for that country.|enum|tag<br/>column_value<br/>| | |
|tag|Data stream tag. Assign a hardcoded (static) data stream level value (tag) when there are multiple similar tables that store the same data for different areas (countries, etc.). This could be a country name if a table or partition stores information for that country.|string| | | |
|column|Column name that contains a dynamic data stream level value (for dynamic data-driven data streams). Sensor queries will be extended with a GROUP BY {data stream level colum name}, sensors (and alerts) will be calculated for each unique value of the specified column. Also a separate time series will be tracked for each value.|column_name| | | |
|name|Data stream level name.|string| | | |









___  

## TableIncidentGroupingSpec  
Configuration of data quality incident grouping on a table level. Defines how similar data quality issues are grouped into incidents.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|grouping_level|Grouping level of failed data quality checks for creating higher level data quality incidents. The default grouping level is by a table, a data quality dimension and a check category (i.e. a consistency data quality incident detected on a table X in the numeric checks category).|enum|table_dimension_category_type<br/>table_dimension<br/>table<br/>table_dimension_category<br/>table_dimension_category_name<br/>| | |
|minimum_severity|Minimum severity level of data quality issues that are grouped into incidents. The default minimum severity level is &#x27;warning&#x27;. Other supported severity levels are &#x27;error&#x27; and &#x27;fatal&#x27;.|enum|warning<br/>error<br/>fatal<br/>| | |
|divide_by_data_stream|Create separate data quality incidents for each data stream, creating different incidents for different data streams. By default, data streams are ignored for grouping data quality issues into data quality incidents.|boolean| | | |
|disabled|Disables data quality incident creation for failed data quality checks on the table.|boolean| | | |









___  

## TableOwnerSpec  
Table owner information.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|data_steward|Data steward name|string| | | |
|application|Business application name|string| | | |









___  

## TableProfilingCheckCategoriesSpec  
Container of table level checks that are activated on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](#tableprofilingvolumechecksspec)|Configuration of volume data quality checks on a table level.|[TableProfilingVolumeChecksSpec](#tableprofilingvolumechecksspec)| | | |
|[timeliness](#tableprofilingtimelinesschecksspec)|Configuration of timeliness checks on a table level. Timeliness checks detect anomalies like rapid row count changes.|[TableProfilingTimelinessChecksSpec](#tableprofilingtimelinesschecksspec)| | | |
|[accuracy](#tableprofilingaccuracychecksspec)|Configuration of accuracy checks on a table level. Accuracy checks compare the tested table with another reference table.|[TableProfilingAccuracyChecksSpec](#tableprofilingaccuracychecksspec)| | | |
|[sql](#tableprofilingsqlchecksspec)|Configuration of data quality checks that are evaluating custom SQL conditions and aggregated expressions.|[TableProfilingSqlChecksSpec](#tableprofilingsqlchecksspec)| | | |
|[availability](#tableprofilingavailabilitychecksspec)|Configuration of volume data quality checks on a table level.|[TableProfilingAvailabilityChecksSpec](#tableprofilingavailabilitychecksspec)| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## TableProfilingVolumeChecksSpec  
Container of built-in preconfigured volume data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[row_count](#tablerowcountcheckspec)|Verifies that the number of rows in a table does not exceed the minimum accepted count.|[TableRowCountCheckSpec](#tablerowcountcheckspec)| | | |
|[row_count_anomaly_7_days](#tableanomalyrowcountchange7dayscheckspec)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 7 days.|[TableAnomalyRowCountChange7DaysCheckSpec](#tableanomalyrowcountchange7dayscheckspec)| | | |
|[row_count_anomaly_30_days](#tableanomalyrowcountchange30dayscheckspec)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.|[TableAnomalyRowCountChange30DaysCheckSpec](#tableanomalyrowcountchange30dayscheckspec)| | | |
|[row_count_anomaly_60_days](#tableanomalyrowcountchange60dayscheckspec)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 60 days.|[TableAnomalyRowCountChange60DaysCheckSpec](#tableanomalyrowcountchange60dayscheckspec)| | | |









___  

## TableRowCountCheckSpec  
Row count (select count(*) from ...) test that runs a row_count check, obtains a count of rows and verifies the number by calling the row count rule.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablevolumerowcountsensorparametersspec)|Row count sensor parameters|[TableVolumeRowCountSensorParametersSpec](#tablevolumerowcountsensorparametersspec)| | | |
|[warning](#mincountrulewarningparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinCountRuleWarningParametersSpec](#mincountrulewarningparametersspec)| | | |
|[error](#mincountrule0parametersspec)|Default alerting threshold for a row count that raises a data quality error (alert)|[MinCountRule0ParametersSpec](#mincountrule0parametersspec)| | | |
|[fatal](#mincountrulefatalparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinCountRuleFatalParametersSpec](#mincountrulefatalparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableVolumeRowCountSensorParametersSpec  
Table sensor that executes a row count query.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MinCountRuleWarningParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_count|Minimum accepted value for the actual_value returned by the sensor (inclusive).|long| | |0<br/>|









___  

## MinCountRule0ParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_count|Minimum accepted value for the actual_value returned by the sensor (inclusive).|long| | |5<br/>|









___  

## MinCountRuleFatalParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_count|Minimum accepted value for the actual_value returned by the sensor (inclusive).|long| | |100<br/>|









___  

## RecurringScheduleSpec  
Recurring job schedule specification.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|cron_expression|Unix style cron expression that specifies when to execute scheduled operations like running data quality checks or synchronizing the configuration with the cloud.|string| | | |
|disabled|Disables the schedule. When the value of this &#x27;disable&#x27; field is false, the schedule is stored in the metadata but it is not activated to run data quality checks.|boolean| | | |









___  

## CommentsListSpec  
List of comments.  
  


___  

## CommentSpec  
Comment entry. Comments are added when a change was made and the change should be recorded in a persisted format.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|comment_by|Commented by|string| | | |
|comment|Comment text|string| | | |









___  

## TableAnomalyRowCountChange7DaysCheckSpec  
Table level check that ensures that the row count changes in a rate within a two-tailed percentile during last 7 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablevolumerowcountsensorparametersspec)|Data quality check parameters|[TableVolumeRowCountSensorParametersSpec](#tablevolumerowcountsensorparametersspec)| | | |
|[warning](#changepercentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ChangePercentileMovingWithin7DaysRuleParametersSpec](#changepercentilemovingwithin7daysruleparametersspec)| | | |
|[error](#changepercentilemovingwithin7daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[ChangePercentileMovingWithin7DaysRuleParametersSpec](#changepercentilemovingwithin7daysruleparametersspec)| | | |
|[fatal](#changepercentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ChangePercentileMovingWithin7DaysRuleParametersSpec](#changepercentilemovingwithin7daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ChangePercentileMovingWithin7DaysRuleParametersSpec  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the increments of previous values gathered
 within a time window.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_within|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days, etc.) time window, but at least 3 readouts must exist to run the calculation.|double| | |95<br/>|









___  

## TableAnomalyRowCountChange30DaysCheckSpec  
Table level check that ensures that the row count changes in a rate within a two-tailed percentile during last 30 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablevolumerowcountsensorparametersspec)|Data quality check parameters|[TableVolumeRowCountSensorParametersSpec](#tablevolumerowcountsensorparametersspec)| | | |
|[warning](#changepercentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ChangePercentileMovingWithin30DaysRuleParametersSpec](#changepercentilemovingwithin30daysruleparametersspec)| | | |
|[error](#changepercentilemovingwithin30daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[ChangePercentileMovingWithin30DaysRuleParametersSpec](#changepercentilemovingwithin30daysruleparametersspec)| | | |
|[fatal](#changepercentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ChangePercentileMovingWithin30DaysRuleParametersSpec](#changepercentilemovingwithin30daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ChangePercentileMovingWithin30DaysRuleParametersSpec  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the increments of previous values gathered
 within a time window.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_within|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|double| | |95<br/>|









___  

## TableAnomalyRowCountChange60DaysCheckSpec  
Table level check that ensures that the row count changes in a rate within a two-tailed percentile during last 60 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablevolumerowcountsensorparametersspec)|Data quality check parameters|[TableVolumeRowCountSensorParametersSpec](#tablevolumerowcountsensorparametersspec)| | | |
|[warning](#changepercentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ChangePercentileMovingWithin60DaysRuleParametersSpec](#changepercentilemovingwithin60daysruleparametersspec)| | | |
|[error](#changepercentilemovingwithin60daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[ChangePercentileMovingWithin60DaysRuleParametersSpec](#changepercentilemovingwithin60daysruleparametersspec)| | | |
|[fatal](#changepercentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ChangePercentileMovingWithin60DaysRuleParametersSpec](#changepercentilemovingwithin60daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ChangePercentileMovingWithin60DaysRuleParametersSpec  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the increments of previous values gathered
 within a time window.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_within|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.) time window, but at least 20 readouts must exist to run the calculation.|double| | |95<br/>|









___  

## TableProfilingTimelinessChecksSpec  
Container of timeliness data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[days_since_most_recent_event](#tabledayssincemostrecenteventcheckspec)|Calculates the number of days since the most recent event timestamp (freshness)|[TableDaysSinceMostRecentEventCheckSpec](#tabledayssincemostrecenteventcheckspec)| | | |
|[data_ingestion_delay](#tabledataingestiondelaycheckspec)|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|[TableDataIngestionDelayCheckSpec](#tabledataingestiondelaycheckspec)| | | |
|[days_since_most_recent_ingestion](#tabledayssincemostrecentingestioncheckspec)|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|[TableDaysSinceMostRecentIngestionCheckSpec](#tabledayssincemostrecentingestioncheckspec)| | | |









___  

## TableDaysSinceMostRecentEventCheckSpec  
Table level check that calculates the maximal number of days since the most recent event timestamp.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tabletimelinessdayssincemostrecenteventsensorparametersspec)|Max days since most recent event sensor parameters|[TableTimelinessDaysSinceMostRecentEventSensorParametersSpec](#tabletimelinessdayssincemostrecenteventsensorparametersspec)| | | |
|[warning](#maxdaysrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDaysRule1ParametersSpec](#maxdaysrule1parametersspec)| | | |
|[error](#maxdaysrule2parametersspec)|Default alerting threshold for max days since most recent event that raises a data quality error (alert)|[MaxDaysRule2ParametersSpec](#maxdaysrule2parametersspec)| | | |
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

## TableTimelinessDaysSinceMostRecentEventSensorParametersSpec  
Table sensor that runs a query calculating maximum days since the most recent event.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MaxDaysRule1ParametersSpec  
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_days|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MaxDaysRule2ParametersSpec  
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_days|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MaxDaysRule7ParametersSpec  
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_days|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## TableDataIngestionDelayCheckSpec  
Table level check that calculates the maximal number of days between event timestamp and ingestion timestamp. .  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tabletimelinessdataingestiondelaysensorparametersspec)|Max number of days between event and ingestion sensor parameters|[TableTimelinessDataIngestionDelaySensorParametersSpec](#tabletimelinessdataingestiondelaysensorparametersspec)| | | |
|[warning](#maxdaysrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDaysRule1ParametersSpec](#maxdaysrule1parametersspec)| | | |
|[error](#maxdaysrule2parametersspec)|Default alerting threshold for a max number of days between event and ingestion check that raises a data quality error (alert)|[MaxDaysRule2ParametersSpec](#maxdaysrule2parametersspec)| | | |
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

## TableTimelinessDataIngestionDelaySensorParametersSpec  
Table sensor that runs a query calculating the time difference in days between the most recent transaction timestamp and the most recent data loading timestamp.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## TableDaysSinceMostRecentIngestionCheckSpec  
Table level check that calculates the maximum number of days between event timestamp and ingestion timestamp.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tabletimelinessdayssincemostrecentingestionsensorparametersspec)|Min number of days between event and ingestion sensor parameters|[TableTimelinessDaysSinceMostRecentIngestionSensorParametersSpec](#tabletimelinessdayssincemostrecentingestionsensorparametersspec)| | | |
|[warning](#maxdaysrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDaysRule1ParametersSpec](#maxdaysrule1parametersspec)| | | |
|[error](#maxdaysrule2parametersspec)|Default alerting threshold for a min number of days between event and ingestion check that raises a data quality error (alert)|[MaxDaysRule2ParametersSpec](#maxdaysrule2parametersspec)| | | |
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

## TableTimelinessDaysSinceMostRecentIngestionSensorParametersSpec  
Table sensor that runs a query calculating the time difference in days between the current date and most recent data loading timestamp (staleness).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## TableProfilingAccuracyChecksSpec  
Container of built-in preconfigured accuracy data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[row_count_match_percent](#tableaccuracyrowcountmatchpercentcheckspec)|Verifies that the total row count of the tested table matches the total row count of another (reference) table.|[TableAccuracyRowCountMatchPercentCheckSpec](#tableaccuracyrowcountmatchpercentcheckspec)| | | |









___  

## TableAccuracyRowCountMatchPercentCheckSpec  
Table level check that ensures that there are no more than a maximum percentage of difference of row count of a tested table and of an row count of another (reference) table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tableaccuracyrowcountmatchpercentsensorparametersspec)|Data quality check parameters|[TableAccuracyRowCountMatchPercentSensorParametersSpec](#tableaccuracyrowcountmatchpercentsensorparametersspec)| | | |
|[warning](#maxdiffpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDiffPercentRule1ParametersSpec](#maxdiffpercentrule1parametersspec)| | | |
|[error](#maxdiffpercentrule2parametersspec)|Default alerting threshold for a maximum percentage of difference of row count of a table column and of a row count of another table column that raises a data quality error (alert).|[MaxDiffPercentRule2ParametersSpec](#maxdiffpercentrule2parametersspec)| | | |
|[fatal](#maxdiffpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxDiffPercentRule5ParametersSpec](#maxdiffpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableAccuracyRowCountMatchPercentSensorParametersSpec  
Table level sensor that calculates percentage of the difference of the total row count of all rows in the tested table and the total row count of the other (reference) table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| | |dim_customer<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MaxDiffPercentRule1ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_diff_percent|Maximum accepted value for the percentage of difference between expected_value and actual_value returned by the sensor (inclusive).|double| | | |









___  

## MaxDiffPercentRule2ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_diff_percent|Maximum accepted value for the percentage of difference between expected_value and actual_value returned by the sensor (inclusive).|double| | | |









___  

## MaxDiffPercentRule5ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_diff_percent|Maximum accepted value for the percentage of difference between expected_value and actual_value returned by the sensor (inclusive).|double| | | |









___  

## TableProfilingSqlChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[sql_condition_passed_percent_on_table](#tablesqlconditionpassedpercentcheckspec)|Verifies that a set percentage of rows passed a custom SQL condition (expression).|[TableSqlConditionPassedPercentCheckSpec](#tablesqlconditionpassedpercentcheckspec)| | | |
|[sql_condition_failed_count_on_table](#tablesqlconditionfailedcountcheckspec)|Verifies that a set number of rows failed a custom SQL condition (expression).|[TableSqlConditionFailedCountCheckSpec](#tablesqlconditionfailedcountcheckspec)| | | |
|[sql_aggregate_expr_table](#tablesqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|[TableSqlAggregateExprCheckSpec](#tablesqlaggregateexprcheckspec)| | | |









___  

## TableSqlConditionPassedPercentCheckSpec  
Table level check that ensures that a minimum percentage of rows passed a custom SQL condition (expression).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablesqlconditionpassedpercentsensorparametersspec)|Sensor parameters with the custom SQL condition (an expression that returns true/false) which is evaluated on a each row|[TableSqlConditionPassedPercentSensorParametersSpec](#tablesqlconditionpassedpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning when a minimum acceptable percentage of rows did not pass the custom SQL condition (expression). The warning is considered as a passed data quality check.|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum acceptable percentage of rows passing the custom SQL condition (expression) that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue when a minimum acceptable percentage of rows did not pass the custom SQL condition (expression). A fatal issue indicates a serious data quality problem that should result in stopping the data pipelines.|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableSqlConditionPassedPercentSensorParametersSpec  
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.|string| | |SUM(col_total_impressions) &gt; SUM(col_total_clicks)<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MinPercentRule100ParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_percent|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MinPercentRule99ParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_percent|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MinPercentRule95ParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_percent|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## TableSqlConditionFailedCountCheckSpec  
Table level check that ensures that there are no more than a maximum number of rows fail a custom SQL condition (expression).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablesqlconditionfailedcountsensorparametersspec)|Sensor parameters with the custom SQL condition (an expression that returns true/false) which is evaluated on a each row|[TableSqlConditionFailedCountSensorParametersSpec](#tablesqlconditionfailedcountsensorparametersspec)| | | |
|[warning](#maxcountrule15parametersspec)|Alerting threshold that raises a data quality warning when a given number of rows failed the custom SQL condition (expression). The warning is considered as a passed data quality check.|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows failing the custom SQL condition (expression) that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule0parametersspec)|Alerting threshold that raises a fatal data quality issue when a given number of rows failed the custom SQL condition (expression). A fatal issue indicates a serious data quality problem that should result in stopping the data pipelines.|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableSqlConditionFailedCountSensorParametersSpec  
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.|string| | |SUM(col_total_impressions) &gt; SUM(col_total_clicks)<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MaxCountRule15ParametersSpec  
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_count|Maximum accepted value for the actual_value returned by the sensor (inclusive).|long| | | |









___  

## MaxCountRule10ParametersSpec  
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_count|Maximum accepted value for the actual_value returned by the sensor (inclusive).|long| | | |









___  

## MaxCountRule0ParametersSpec  
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_count|Maximum accepted value for the actual_value returned by the sensor (inclusive).|long| | | |









___  

## TableSqlAggregateExprCheckSpec  
Table level check that calculates a given SQL aggregate expression and compares it with a maximum accepted value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablesqlaggregatedexpressionsensorparametersspec)|Sensor parameters with the custom SQL aggregate expression that is evaluated on a table|[TableSqlAggregatedExpressionSensorParametersSpec](#tablesqlaggregatedexpressionsensorparametersspec)| | | |
|[warning](#maxvalueruleparametersspec)|Default alerting threshold for warnings raised when the aggregated value is above the maximum accepted value.|[MaxValueRuleParametersSpec](#maxvalueruleparametersspec)| | | |
|[error](#maxvalueruleparametersspec)|Default alerting threshold for errors raised when the aggregated value is above the maximum accepted value.|[MaxValueRuleParametersSpec](#maxvalueruleparametersspec)| | | |
|[fatal](#maxvalueruleparametersspec)|Default alerting threshold for fatal data quality issues raised when the aggregated value is above the maximum accepted value.|[MaxValueRuleParametersSpec](#maxvalueruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableSqlAggregatedExpressionSensorParametersSpec  
Table level sensor that executes a given SQL expression on a table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sql_expression|SQL aggregate expression that returns a numeric value calculated from rows. The expression is evaluated on a whole table or withing a GROUP BY clause for daily partitions and/or data streams. The expression can use {table} placeholder that is replaced with a full table name.|string| | |SUM(col_net_price) + SUM(col_tax)<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MaxValueRuleParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_value|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | |1.5<br/>|









___  

## TableProfilingAvailabilityChecksSpec  
Container of built-in preconfigured volume data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table_availability](#tableavailabilitycheckspec)|Verifies availability of the table in a database using a simple row count.|[TableAvailabilityCheckSpec](#tableavailabilitycheckspec)| | | |









___  

## TableAvailabilityCheckSpec  
Table level check that verifies that a query can be executed on a table and that the server does not return errors, that the table exists, and that there are accesses to it.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tableavailabilitysensorparametersspec)|Row count sensor parameters|[TableAvailabilitySensorParametersSpec](#tableavailabilitysensorparametersspec)| | | |
|[warning](#maxfailuresrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxFailuresRule0ParametersSpec](#maxfailuresrule0parametersspec)| | | |
|[error](#maxfailuresrule5parametersspec)|Default alerting threshold for a row count that raises a data quality error (alert)|[MaxFailuresRule5ParametersSpec](#maxfailuresrule5parametersspec)| | | |
|[fatal](#maxfailuresrule10parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxFailuresRule10ParametersSpec](#maxfailuresrule10parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableAvailabilitySensorParametersSpec  
Table availability sensor that executes a row count query.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MaxFailuresRule0ParametersSpec  
Data quality rule that verifies if the number of executive failures (the sensor returned 0) is below the max_failures. The default maximum failures is 0 failures (the first failure is reported).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_failures|Maximum number of consecutive check failures, a check is failed when the sensor&#x27;s query failed to execute due to a connection error, missing table or a corrupted table.|long| | | |









___  

## MaxFailuresRule5ParametersSpec  
Data quality rule that verifies if the number of executive failures (the sensor returned 0) is below the max_failures. The default maximum failures is 5 failures (the 6th failure is reported).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_failures|Maximum number of consecutive check failures, a check is failed when the sensor&#x27;s query failed to execute due to a connection error, missing table or a corrupted table.|long| | | |









___  

## MaxFailuresRule10ParametersSpec  
Data quality rule that verifies if the number of executive failures (the sensor returned 0) is below the max_failures. The default maximum failures is 10 failures (the 11th failure is reported).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_failures|Maximum number of consecutive check failures, a check is failed when the sensor&#x27;s query failed to execute due to a connection error, missing table or a corrupted table.|long| | | |









___  

## CustomCheckSpecMap  
Dictionary of custom checks indexed by a check name.  
  












___  

## CustomCheckSpec  
Custom check specification. This check is usable only when there is a matching custom check definition that identifies
 the sensor definition and the rule definition.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sensor_name|Optional custom sensor name. It is a folder name inside the user&#x27;s home &#x27;sensors&#x27; folder or the DQO Home (DQO distribution) home/sensors folder. Sample sensor name: table/volume/row_count. When this value is set, it overrides the default sensor definition defined for the named check definition.|string| | | |
|rule_name|Optional custom rule name. It is a path to a custom rule python module that starts at the user&#x27;s home &#x27;rules&#x27; folder. The path should not end with the .py file extension. Sample rule: myrules/my_custom_rule. When this value is set, it overrides the default rule definition defined for the named check definition.|string| | | |
|[parameters](#customsensorparametersspec)|Custom sensor parameters|[CustomSensorParametersSpec](#customsensorparametersspec)| | | |
|[warning](#customruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[CustomRuleParametersSpec](#customruleparametersspec)| | | |
|[error](#customruleparametersspec)|Default alerting threshold for a row count that raises a data quality error (alert)|[CustomRuleParametersSpec](#customruleparametersspec)| | | |
|[fatal](#customruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[CustomRuleParametersSpec](#customruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## CustomSensorParametersSpec  
Custom sensor parameters for custom checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## CustomRuleParametersSpec  
Custom data quality rule.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|









___  

## TableRecurringChecksSpec  
Container of table level recurring, divided by the time window (daily, monthly, etc.)  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](#tabledailyrecurringcategoriesspec)|Configuration of daily recurring evaluated at a table level.|[TableDailyRecurringCategoriesSpec](#tabledailyrecurringcategoriesspec)| | | |
|[monthly](#tablemonthlyrecurringcheckcategoriesspec)|Configuration of monthly recurring evaluated at a table level.|[TableMonthlyRecurringCheckCategoriesSpec](#tablemonthlyrecurringcheckcategoriesspec)| | | |









___  

## TableDailyRecurringCategoriesSpec  
Container of table level daily recurring. Contains categories of daily recurring.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](#tablevolumedailyrecurringchecksspec)|Daily recurring volume data quality checks|[TableVolumeDailyRecurringChecksSpec](#tablevolumedailyrecurringchecksspec)| | | |
|[timeliness](#tabletimelinessdailyrecurringchecksspec)|Daily recurring timeliness checks|[TableTimelinessDailyRecurringChecksSpec](#tabletimelinessdailyrecurringchecksspec)| | | |
|[accuracy](#tableaccuracydailyrecurringchecksspec)|Daily recurring accuracy checks|[TableAccuracyDailyRecurringChecksSpec](#tableaccuracydailyrecurringchecksspec)| | | |
|[sql](#tablesqldailyrecurringchecksspec)|Daily recurring custom SQL checks|[TableSqlDailyRecurringChecksSpec](#tablesqldailyrecurringchecksspec)| | | |
|[availability](#tableavailabilitydailyrecurringchecksspec)|Daily recurring availability checks|[TableAvailabilityDailyRecurringChecksSpec](#tableavailabilitydailyrecurringchecksspec)| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## TableVolumeDailyRecurringChecksSpec  
Container of table level daily recurring for volume data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_row_count](#tablerowcountcheckspec)|Verifies that the number of rows in a table does not exceed the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[TableRowCountCheckSpec](#tablerowcountcheckspec)| | | |
|[daily_row_count_anomaly_7_days](#tableanomalyrowcountchange7dayscheckspec)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 7 days.|[TableAnomalyRowCountChange7DaysCheckSpec](#tableanomalyrowcountchange7dayscheckspec)| | | |
|[daily_row_count_anomaly_30_days](#tableanomalyrowcountchange30dayscheckspec)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.|[TableAnomalyRowCountChange30DaysCheckSpec](#tableanomalyrowcountchange30dayscheckspec)| | | |
|[daily_row_count_anomaly_60_days](#tableanomalyrowcountchange60dayscheckspec)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 60 days.|[TableAnomalyRowCountChange60DaysCheckSpec](#tableanomalyrowcountchange60dayscheckspec)| | | |









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
|[daily_row_count_match_percent](#tableaccuracyrowcountmatchpercentcheckspec)|Verifies the row count of a tested table and compares it to a row count of a reference table. Stores the most recent row count for each day when the data quality check was evaluated.|[TableAccuracyRowCountMatchPercentCheckSpec](#tableaccuracyrowcountmatchpercentcheckspec)| | | |









___  

## TableSqlDailyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_sql_condition_passed_percent_on_table](#tablesqlconditionpassedpercentcheckspec)|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each day when the data quality check was evaluated.|[TableSqlConditionPassedPercentCheckSpec](#tablesqlconditionpassedpercentcheckspec)| | | |
|[daily_sql_condition_failed_count_on_table](#tablesqlconditionfailedcountcheckspec)|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent row count for each day when the data quality check was evaluated.|[TableSqlConditionFailedCountCheckSpec](#tablesqlconditionfailedcountcheckspec)| | | |
|[daily_sql_aggregate_expr_table](#tablesqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|[TableSqlAggregateExprCheckSpec](#tablesqlaggregateexprcheckspec)| | | |









___  

## TableAvailabilityDailyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are detecting the table availability.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_table_availability](#tableavailabilitycheckspec)|Verifies availability on table in database using simple row count. Stores the most recent row count for each day when the data quality check was evaluated.|[TableAvailabilityCheckSpec](#tableavailabilitycheckspec)| | | |









___  

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
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## TableVolumeMonthlyRecurringChecksSpec  
Container of table level monthly recurring for volume data quality checks  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_row_count](#tablerowcountcheckspec)|Verifies that the number of rows in a table does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[TableRowCountCheckSpec](#tablerowcountcheckspec)| | | |









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
|[monthly_table_availability](#tableavailabilitycheckspec)|Verifies availability on table in database using simple row count. Stores the most recent row count for each month when the data quality check was evaluated.|[TableAvailabilityCheckSpec](#tableavailabilitycheckspec)| | | |









___  

## TablePartitionedChecksRootSpec  
Container of table level partitioned checks, divided by the time window (daily, monthly, etc.)  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](#tabledailypartitionedcheckcategoriesspec)|Configuration of day partitioned data quality checks evaluated at a table level.|[TableDailyPartitionedCheckCategoriesSpec](#tabledailypartitionedcheckcategoriesspec)| | | |
|[monthly](#tablemonthlypartitionedcheckcategoriesspec)|Configuration of monthly partitioned data quality checks evaluated at a table level..|[TableMonthlyPartitionedCheckCategoriesSpec](#tablemonthlypartitionedcheckcategoriesspec)| | | |









___  

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

## TableStatisticsCollectorsRootCategoriesSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](#tablevolumestatisticscollectorsspec)|Configuration of volume statistics collectors on a table level.|[TableVolumeStatisticsCollectorsSpec](#tablevolumestatisticscollectorsspec)| | | |









___  

## TableVolumeStatisticsCollectorsSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[row_count](#tablevolumerowcountstatisticscollectorspec)|Configuration of the row count profiler.|[TableVolumeRowCountStatisticsCollectorSpec](#tablevolumerowcountstatisticscollectorspec)| | | |









___  

## TableVolumeRowCountStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablevolumerowcountsensorparametersspec)|Profiler parameters|[TableVolumeRowCountSensorParametersSpec](#tablevolumerowcountsensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## RecurringSchedulesSpec  
Container of all recurring schedules (cron expressions) for each type of checks.
 Data quality checks are grouped by type (profiling, whole table checks, time period partitioned checks).
 Each group of checks could be divided additionally by time scale (daily, monthly, etc).
 Each time scale has a different recurring schedule used by the job scheduler to run the checks.
 These schedules are defined in this object.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profiling](#recurringschedulespec)|Schedule for running profiling data quality checks.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[recurring_daily](#recurringschedulespec)|Schedule for running daily recurring checks.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[recurring_monthly](#recurringschedulespec)|Schedule for running monthly recurring checks.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[partitioned_daily](#recurringschedulespec)|Schedule for running daily partitioned checks.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[partitioned_monthly](#recurringschedulespec)|Schedule for running monthly partitioned checks.|[RecurringScheduleSpec](#recurringschedulespec)| | | |









___  

## ColumnSpecMap  
Dictionary of columns indexed by a physical column name.  
  














___  

## ColumnSpec  
Column specification that identifies a single column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|disabled|Disables all data quality checks on the column. Data quality checks will not be executed.|boolean| | | |
|sql_expression|SQL expression used for calculated fields or when additional column value transformation is required before the column could be used analyzed in data quality checks (data type conversion, transformation). It should be an SQL expression using the SQL language of the analyzed database type. Use replacement tokens {table} to replace the content with the full table name, {alias} to replace the content with the table alias of an analyzed table or {column} to replace the content with the analyzed column name. An example to extract a value from a string column that stores a JSON in PostgreSQL: &quot;{column}::json-&gt;&#x27;address&#x27;-&gt;&#x27;zip&#x27;&quot;.|string| | | |
|[type_snapshot](#columntypesnapshotspec)|Column data type that was retrieved when the table metadata was imported.|[ColumnTypeSnapshotSpec](#columntypesnapshotspec)| | | |
|[profiling_checks](#columnprofilingcheckcategoriesspec)|Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.|[ColumnProfilingCheckCategoriesSpec](#columnprofilingcheckcategoriesspec)| | | |
|[recurring_checks](#columnrecurringchecksrootspec)|Configuration of column level recurring checks. Recurring are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A recurring stores only the most recent data quality check result for each period of time.|[ColumnRecurringChecksRootSpec](#columnrecurringchecksrootspec)| | | |
|[partitioned_checks](#columnpartitionedchecksrootspec)|Configuration of column level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.|[ColumnPartitionedChecksRootSpec](#columnpartitionedchecksrootspec)| | | |
|[statistics](#columnstatisticscollectorsrootcategoriesspec)|Custom configuration of a column level statistics collector (a basic profiler). Enables customization of the statistics collector settings when the collector is analysing this column.|[ColumnStatisticsCollectorsRootCategoriesSpec](#columnstatisticscollectorsrootcategoriesspec)| | | |
|[labels](#labelsetspec)|Custom labels that were assigned to the column. Labels are used for searching for columns when filtered data quality checks are executed.|[LabelSetSpec](#labelsetspec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |









___  

## ColumnTypeSnapshotSpec  
Stores the column data type captured at the time of the table metadata import.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|column_type|Column data type using the monitored database type names.|string| | | |
|nullable|Column is nullable.|boolean| | | |
|length|Maximum length of text and binary columns.|integer| | | |
|scale|Scale of a numeric (decimal) data type.|integer| | | |
|precision|Precision of a numeric (decimal) data type.|integer| | | |









___  

## ColumnProfilingCheckCategoriesSpec  
Container of column level, preconfigured checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](#columnprofilingnullschecksspec)|Configuration of column level checks that verify nulls and blanks.|[ColumnProfilingNullsChecksSpec](#columnprofilingnullschecksspec)| | | |
|[numeric](#columnprofilingnumericchecksspec)|Configuration of column level checks that verify negative values.|[ColumnProfilingNumericChecksSpec](#columnprofilingnumericchecksspec)| | | |
|[strings](#columnprofilingstringschecksspec)|Configuration of strings checks on a column level.|[ColumnProfilingStringsChecksSpec](#columnprofilingstringschecksspec)| | | |
|[uniqueness](#columnprofilinguniquenesschecksspec)|Configuration of uniqueness checks on a column level.|[ColumnProfilingUniquenessChecksSpec](#columnprofilinguniquenesschecksspec)| | | |
|[datetime](#columnprofilingdatetimechecksspec)|Configuration of datetime checks on a column level.|[ColumnProfilingDatetimeChecksSpec](#columnprofilingdatetimechecksspec)| | | |
|[pii](#columnprofilingpiichecksspec)|Configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnProfilingPiiChecksSpec](#columnprofilingpiichecksspec)| | | |
|[sql](#columnprofilingsqlchecksspec)|Configuration of SQL checks that use custom SQL aggregated expressions and SQL conditions in data quality checks.|[ColumnProfilingSqlChecksSpec](#columnprofilingsqlchecksspec)| | | |
|[bool](#columnprofilingboolchecksspec)|Configuration of booleans checks on a column level.|[ColumnProfilingBoolChecksSpec](#columnprofilingboolchecksspec)| | | |
|[integrity](#columnprofilingintegritychecksspec)|Configuration of integrity checks on a column level.|[ColumnProfilingIntegrityChecksSpec](#columnprofilingintegritychecksspec)| | | |
|[accuracy](#columnprofilingaccuracychecksspec)|Configuration of accuracy checks on a column level.|[ColumnProfilingAccuracyChecksSpec](#columnprofilingaccuracychecksspec)| | | |
|[consistency](#columnprofilingconsistencychecksspec)|Configuration of consistency checks on a column level.|[ColumnProfilingConsistencyChecksSpec](#columnprofilingconsistencychecksspec)| | | |
|[anomaly](#columnprofilinganomalychecksspec)|Configuration of anomaly checks on a column level.|[ColumnProfilingAnomalyChecksSpec](#columnprofilinganomalychecksspec)| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## ColumnProfilingNullsChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for nulls.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls_count](#columnnullscountcheckspec)|Verifies that the number of null values in a column does not exceed the maximum accepted count.|[ColumnNullsCountCheckSpec](#columnnullscountcheckspec)| | | |
|[nulls_percent](#columnnullspercentcheckspec)|Verifies that the percent of null values in a column does not exceed the maximum accepted percentage.|[ColumnNullsPercentCheckSpec](#columnnullspercentcheckspec)| | | |
|[not_nulls_count](#columnnotnullscountcheckspec)|Verifies that the number of not null values in a column does not exceed the minimum accepted count.|[ColumnNotNullsCountCheckSpec](#columnnotnullscountcheckspec)| | | |
|[not_nulls_percent](#columnnotnullspercentcheckspec)|Verifies that the percent of not null values in a column does not exceed the minimum accepted percentage.|[ColumnNotNullsPercentCheckSpec](#columnnotnullspercentcheckspec)| | | |









___  

## ColumnNullsCountCheckSpec  
Column-level check that ensures that there are no more than a set number of null values in the monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnullsnullscountsensorparametersspec)|Data quality check parameters|[ColumnNullsNullsCountSensorParametersSpec](#columnnullsnullscountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a set number of rows with null values in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNullsNullsCountSensorParametersSpec  
Column-level sensor that calculates the number of rows with null values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnNullsPercentCheckSpec  
Column-level check that ensures that there are no more than a set percentage of null values in the monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnullsnullspercentsensorparametersspec)|Data quality check parameters|[ColumnNullsNullsPercentSensorParametersSpec](#columnnullsnullspercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for a set percentage of rows with null values in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNullsNullsPercentSensorParametersSpec  
Column-level sensor that calculates the percentage of rows with null values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MaxPercentRule1ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_percent|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MaxPercentRule2ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_percent|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MaxPercentRule5ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_percent|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## ColumnNotNullsCountCheckSpec  
Column-level check that ensures that there are no more than a set number of null values in the monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnullsnotnullscountsensorparametersspec)|Data quality check parameters|[ColumnNullsNotNullsCountSensorParametersSpec](#columnnullsnotnullscountsensorparametersspec)| | | |
|[warning](#mincountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinCountRule0ParametersSpec](#mincountrule0parametersspec)| | | |
|[error](#mincountrulewarningparametersspec)|Default alerting threshold for a set number of rows with not null values in a column that raises a data quality error (alert).|[MinCountRuleWarningParametersSpec](#mincountrulewarningparametersspec)| | | |
|[fatal](#mincountrulefatalparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinCountRuleFatalParametersSpec](#mincountrulefatalparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNullsNotNullsCountSensorParametersSpec  
Column-level sensor that calculates the number of rows with not null values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnNotNullsPercentCheckSpec  
Column-level check that ensures that there are no more than a set percentage of not null values in the monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnullsnotnullspercentsensorparametersspec)|Data quality check parameters|[ColumnNullsNotNullsPercentSensorParametersSpec](#columnnullsnotnullspercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a set percentage of rows with null values in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNullsNotNullsPercentSensorParametersSpec  
Column level sensor that calculates the percentage of not null values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MinPercentRule98ParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_percent|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## ColumnProfilingNumericChecksSpec  
Container of built-in preconfigured data quality checks on a column level for numeric values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[negative_count](#columnnegativecountcheckspec)|Verifies that the number of negative values in a column does not exceed the maximum accepted count.|[ColumnNegativeCountCheckSpec](#columnnegativecountcheckspec)| | | |
|[negative_percent](#columnnegativepercentcheckspec)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.|[ColumnNegativePercentCheckSpec](#columnnegativepercentcheckspec)| | | |
|[non_negative_count](#columnnonnegativecountcheckspec)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count.|[ColumnNonNegativeCountCheckSpec](#columnnonnegativecountcheckspec)| | | |
|[non_negative_percent](#columnnonnegativepercentcheckspec)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage.|[ColumnNonNegativePercentCheckSpec](#columnnonnegativepercentcheckspec)| | | |
|[numbers_found_count](#columnnumbersfoundcountcheckspec)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|[ColumnNumbersFoundCountCheckSpec](#columnnumbersfoundcountcheckspec)| | | |
|[has_valid_numbers_percent](#columnhasvalidnumberspercentcheckspec)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|[ColumnHasValidNumbersPercentCheckSpec](#columnhasvalidnumberspercentcheckspec)| | | |
|[values_in_range_numeric_percent](#columnvaluesinrangenumericpercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|[ColumnValuesInRangeNumericPercentCheckSpec](#columnvaluesinrangenumericpercentcheckspec)| | | |
|[values_in_range_integers_percent](#columnvaluesinrangeintegerspercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|[ColumnValuesInRangeIntegersPercentCheckSpec](#columnvaluesinrangeintegerspercentcheckspec)| | | |
|[value_below_min_value_count](#columnvaluebelowminvaluecountcheckspec)|The check counts the number of values in the column that is below the value defined by the user as a parameter.|[ColumnValueBelowMinValueCountCheckSpec](#columnvaluebelowminvaluecountcheckspec)| | | |
|[value_below_min_value_percent](#columnvaluebelowminvaluepercentcheckspec)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter.|[ColumnValueBelowMinValuePercentCheckSpec](#columnvaluebelowminvaluepercentcheckspec)| | | |
|[value_above_max_value_count](#columnvalueabovemaxvaluecountcheckspec)|The check counts the number of values in the column that is above the value defined by the user as a parameter.|[ColumnValueAboveMaxValueCountCheckSpec](#columnvalueabovemaxvaluecountcheckspec)| | | |
|[value_above_max_value_percent](#columnvalueabovemaxvaluepercentcheckspec)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter.|[ColumnValueAboveMaxValuePercentCheckSpec](#columnvalueabovemaxvaluepercentcheckspec)| | | |
|[max_in_range](#columnmaxinrangecheckspec)|Verifies that the maximal value in a column is not outside the set range.|[ColumnMaxInRangeCheckSpec](#columnmaxinrangecheckspec)| | | |
|[min_in_range](#columnmininrangecheckspec)|Verifies that the minimal value in a column is not outside the set range.|[ColumnMinInRangeCheckSpec](#columnmininrangecheckspec)| | | |
|[mean_in_range](#columnmeaninrangecheckspec)|Verifies that the average (mean) of all values in a column is not outside the set range.|[ColumnMeanInRangeCheckSpec](#columnmeaninrangecheckspec)| | | |
|[percentile_in_range](#columnpercentileinrangecheckspec)|Verifies that the percentile of all values in a column is not outside the set range.|[ColumnPercentileInRangeCheckSpec](#columnpercentileinrangecheckspec)| | | |
|[median_in_range](#columnmedianinrangecheckspec)|Verifies that the median of all values in a column is not outside the set range.|[ColumnMedianInRangeCheckSpec](#columnmedianinrangecheckspec)| | | |
|[percentile_10_in_range](#columnpercentile10inrangecheckspec)|Verifies that the percentile 10 of all values in a column is not outside the set range.|[ColumnPercentile10InRangeCheckSpec](#columnpercentile10inrangecheckspec)| | | |
|[percentile_25_in_range](#columnpercentile25inrangecheckspec)|Verifies that the percentile 25 of all values in a column is not outside the set range.|[ColumnPercentile25InRangeCheckSpec](#columnpercentile25inrangecheckspec)| | | |
|[percentile_75_in_range](#columnpercentile75inrangecheckspec)|Verifies that the percentile 75 of all values in a column is not outside the set range.|[ColumnPercentile75InRangeCheckSpec](#columnpercentile75inrangecheckspec)| | | |
|[percentile_90_in_range](#columnpercentile90inrangecheckspec)|Verifies that the percentile 90 of all values in a column is not outside the set range.|[ColumnPercentile90InRangeCheckSpec](#columnpercentile90inrangecheckspec)| | | |
|[sample_stddev_in_range](#columnsamplestddevinrangecheckspec)|Verifies that the sample standard deviation of all values in a column is not outside the set range.|[ColumnSampleStddevInRangeCheckSpec](#columnsamplestddevinrangecheckspec)| | | |
|[population_stddev_in_range](#columnpopulationstddevinrangecheckspec)|Verifies that the population standard deviation of all values in a column is not outside the set range.|[ColumnPopulationStddevInRangeCheckSpec](#columnpopulationstddevinrangecheckspec)| | | |
|[sample_variance_in_range](#columnsamplevarianceinrangecheckspec)|Verifies that the sample variance of all values in a column is not outside the set range.|[ColumnSampleVarianceInRangeCheckSpec](#columnsamplevarianceinrangecheckspec)| | | |
|[population_variance_in_range](#columnpopulationvarianceinrangecheckspec)|Verifies that the population variance of all values in a column is not outside the set range.|[ColumnPopulationVarianceInRangeCheckSpec](#columnpopulationvarianceinrangecheckspec)| | | |
|[sum_in_range](#columnsuminrangecheckspec)|Verifies that the sum of all values in a column is not outside the set range.|[ColumnSumInRangeCheckSpec](#columnsuminrangecheckspec)| | | |
|[invalid_latitude_count](#columninvalidlatitudecountcheckspec)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count.|[ColumnInvalidLatitudeCountCheckSpec](#columninvalidlatitudecountcheckspec)| | | |
|[valid_latitude_percent](#columnvalidlatitudepercentcheckspec)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage.|[ColumnValidLatitudePercentCheckSpec](#columnvalidlatitudepercentcheckspec)| | | |
|[invalid_longitude_count](#columninvalidlongitudecountcheckspec)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count.|[ColumnInvalidLongitudeCountCheckSpec](#columninvalidlongitudecountcheckspec)| | | |
|[valid_longitude_percent](#columnvalidlongitudepercentcheckspec)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage.|[ColumnValidLongitudePercentCheckSpec](#columnvalidlongitudepercentcheckspec)| | | |









___  

## ColumnNegativeCountCheckSpec  
Column level check that ensures that there are no more than a set number of negative values in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericnegativecountsensorparametersspec)|Data quality check parameters|[ColumnNumericNegativeCountSensorParametersSpec](#columnnumericnegativecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericNegativeCountSensorParametersSpec  
Column level sensor that counts negative values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnNegativePercentCheckSpec  
Column level check that ensures that there are no more than a set percentage of negative values in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericnegativepercentsensorparametersspec)|Data quality check parameters|[ColumnNumericNegativePercentSensorParametersSpec](#columnnumericnegativepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule99ParametersSpec](#maxpercentrule99parametersspec)| | | |
|[error](#maxpercentrule98parametersspec)|Default alerting threshold for a set percentage of rows with negative value in a column that raises a data quality alert|[MaxPercentRule98ParametersSpec](#maxpercentrule98parametersspec)| | | |
|[fatal](#maxpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule95ParametersSpec](#maxpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericNegativePercentSensorParametersSpec  
Column level sensor that counts percentage of negative values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MaxPercentRule99ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_percent|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MaxPercentRule98ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_percent|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MaxPercentRule95ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_percent|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## ColumnNonNegativeCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of non-negative values in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericnonnegativecountsensorparametersspec)|Data quality check parameters|[ColumnNumericNonNegativeCountSensorParametersSpec](#columnnumericnonnegativecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with non-negative values in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericNonNegativeCountSensorParametersSpec  
Column level sensor that counts non negative values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnNonNegativePercentCheckSpec  
Column level check that ensures that there are no more than a set percentage of negative values in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericnonnegativepercentsensorparametersspec)|Data quality check parameters|[ColumnNumericNonNegativePercentSensorParametersSpec](#columnnumericnonnegativepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule99ParametersSpec](#maxpercentrule99parametersspec)| | | |
|[error](#maxpercentrule98parametersspec)|Default alerting threshold for a set percentage of rows with non-negative value in a column that raises a data quality alert|[MaxPercentRule98ParametersSpec](#maxpercentrule98parametersspec)| | | |
|[fatal](#maxpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule95ParametersSpec](#maxpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericNonNegativePercentSensorParametersSpec  
Column level sensor that calculates the percent of non-negative values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnNumbersFoundCountCheckSpec  
Column level check that counts unique values in a numeric column and counts how many values out of a list of expected numeric values were found in the column.
 The check raises a data quality issue when the threshold of maximum number of missing values was exceeded (too many expected values were not found in the column).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericnumbersfoundcountsensorparametersspec)|Data quality check parameters that specify a list of expected numeric values to find in the column.|[ColumnNumericNumbersFoundCountSensorParametersSpec](#columnnumericnumbersfoundcountsensorparametersspec)| | | |
|[warning](#maxmissingrule0parametersspec)|Alerting threshold that raises a data quality warning when too many expected values were not found in the column.|[MaxMissingRule0ParametersSpec](#maxmissingrule0parametersspec)| | | |
|[error](#maxmissingrule1parametersspec)|Alerting threshold that raises a data quality error when too many expected values were not found in the column.|[MaxMissingRule1ParametersSpec](#maxmissingrule1parametersspec)| | | |
|[fatal](#maxmissingrule2parametersspec)|Alerting threshold that raises a data quality fatal issue when too many expected values were not found in the column.|[MaxMissingRule2ParametersSpec](#maxmissingrule2parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericNumbersFoundCountSensorParametersSpec  
Column level sensor that counts unique values in a numeric column and counts how many values out of a list of expected numeric values were found in the column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|expected_values|List of expected numeric values that the sensor is trying to find in the column.|integer_list| | |2<br/>3<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MaxMissingRule0ParametersSpec  
Data quality rule that verifies the results of the data quality checks that count the number of values
 present in a column, comparing it to a list of expected values. The rule compares the count of expected values (received as expected_value)
 to the count of values found in the column (as the actual_value). The rule fails when the difference is higher than
 the expected max_missing, which is the maximum difference between the expected_value (the count of values in the expected_values list)
 and the actual number of values found in the column that match the list.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_missing|The maximum number of values from the expected_values list that were not found in the column (inclusive).|long| | |1<br/>|









___  

## MaxMissingRule1ParametersSpec  
Data quality rule that verifies the results of the data quality checks that count the number of values
 present in a column, comparing it to a list of expected values. The rule compares the count of expected values (received as expected_value)
 to the count of values found in the column (as the actual_value). The rule fails when the difference is higher than
 the expected max_missing, which is the maximum difference between the expected_value (the count of values in the expected_values list)
 and the actual number of values found in the column that match the list.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_missing|The maximum number of values from the expected_values list that were not found in the column (inclusive).|long| | |1<br/>|









___  

## MaxMissingRule2ParametersSpec  
Data quality rule that verifies the results of the data quality checks that count the number of values
 present in a column, comparing it to a list of expected values. The rule compares the count of expected values (received as expected_value)
 to the count of values found in the column (as the actual_value). The rule fails when the difference is higher than
 the expected max_missing, which is the maximum difference between the expected_value (the count of values in the expected_values list)
 and the actual number of values found in the column that match the list.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_missing|The maximum number of values from the expected_values list that were not found in the column (inclusive).|long| | |2<br/>|









___  

## ColumnHasValidNumbersPercentCheckSpec  
Column level check that calculates the percentage of rows for which the tested numeric column contains a value from the list of expected values.
 Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value).
 The check raises a data quality issue when the percentage of rows with a not null column value that is not expected (not one of the values in the expected_values list)
 is below an expected threshold, for example 99% of rows should have values from the defined domain.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumerichasvalidnumberpercentsensorparametersspec)|Data quality check parameters that specify a list of expected values that are compared to the values in the tested numeric column.|[ColumnNumericHasValidNumberPercentSensorParametersSpec](#columnnumerichasvalidnumberpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Default alerting threshold for a percentage of rows with invalid (not expected) values in a column that raises a data quality issue at a warning severity level.|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a percentage of rows with invalid (not expected) values in a column that raises a data quality issue at an error severity level.|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Default alerting threshold for a percentage of rows with invalid (not expected) values in a column that raises a data quality fatal issue.|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericHasValidNumberPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows for which the tested numeric column contains a value from the list of expected values.
 Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|expected_values|A list of expected values that must be present in a numeric column, only values from this list are accepted.|integer_list| | |2<br/>3<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnValuesInRangeNumericPercentCheckSpec  
Column level check that ensures that there are no more than a set percentage of values from range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericvaluesinrangenumericpercentsensorparametersspec)|Data quality check parameters|[ColumnNumericValuesInRangeNumericPercentSensorParametersSpec](#columnnumericvaluesinrangenumericpercentsensorparametersspec)| | | |
|[warning](#minpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule1ParametersSpec](#minpercentrule1parametersspec)| | | |
|[error](#minpercentrule2parametersspec)|Default alerting threshold for set percentage of values from range in a column that raises a data quality error (alert).|[MinPercentRule2ParametersSpec](#minpercentrule2parametersspec)| | | |
|[fatal](#minpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule5ParametersSpec](#minpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericValuesInRangeNumericPercentSensorParametersSpec  
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_value|Minimal value range variable.|double| | | |
|max_value|Maximal value range variable.|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MinPercentRule1ParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_percent|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MinPercentRule2ParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_percent|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MinPercentRule5ParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_percent|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## ColumnValuesInRangeIntegersPercentCheckSpec  
Column level check that ensures that there are no more than a set number of values from range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericvaluesinrangeintegerspercentsensorparametersspec)|Data quality check parameters|[ColumnNumericValuesInRangeIntegersPercentSensorParametersSpec](#columnnumericvaluesinrangeintegerspercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a set number of values from range in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericValuesInRangeIntegersPercentSensorParametersSpec  
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_value|Minimal value range variable.|long| | | |
|max_value|Maximal value range variable.|long| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnValueBelowMinValueCountCheckSpec  
Column level check that ensures that the number of values in the monitored column with a value below the value defined by the user as a parameter does not exceed set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericvaluebelowminvaluecountsensorparametersspec)|Data quality check parameters|[ColumnNumericValueBelowMinValueCountSensorParametersSpec](#columnnumericvaluebelowminvaluecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with values with a value below the indicated by the user value in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericValueBelowMinValueCountSensorParametersSpec  
Column level sensor that calculates the count of values that are below than a given value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_value|This field can be used to define custom value. In order to define custom value, user should write correct value as an integer. If value is not defined by user then default value is 0|integer| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnValueBelowMinValuePercentCheckSpec  
Column level check that ensures that the percentage of values in the monitored column with a value below the value defined by the user as a parameter does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericvaluebelowminvaluepercentsensorparametersspec)|Data quality check parameters|[ColumnNumericValueBelowMinValuePercentSensorParametersSpec](#columnnumericvaluebelowminvaluepercentsensorparametersspec)| | | |
|[warning](#minpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule1ParametersSpec](#minpercentrule1parametersspec)| | | |
|[error](#minpercentrule2parametersspec)|Default alerting threshold for a maximum number of rows with values with a value below the indicated by the user value in a column that raises a data quality error (alert).|[MinPercentRule2ParametersSpec](#minpercentrule2parametersspec)| | | |
|[fatal](#minpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule5ParametersSpec](#minpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericValueBelowMinValuePercentSensorParametersSpec  
Column level sensor that calculates the percentage of values that are below than a given value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_value|This field can be used to define custom value. In order to define custom value, user should write correct value as an integer. If value is not defined by user then default value is 0|integer| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnValueAboveMaxValueCountCheckSpec  
Column level check that ensures that the number of values in the monitored column with a value above the value defined by the user as a parameter does not exceed set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericvalueabovemaxvaluecountsensorparametersspec)|Data quality check parameters|[ColumnNumericValueAboveMaxValueCountSensorParametersSpec](#columnnumericvalueabovemaxvaluecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with values with a value above the indicated by the user value in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericValueAboveMaxValueCountSensorParametersSpec  
Column level sensor that calculates the count of values that are above than a given value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_value|This field can be used to define custom value. In order to define custom value, user should write correct value as an integer. If value is not defined by user then default value is 0|integer| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnValueAboveMaxValuePercentCheckSpec  
Column level check that ensures that the percentage of values in the monitored column with a value above the value defined by the user as a parameter does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericvalueabovemaxvaluepercentsensorparametersspec)|Data quality check parameters|[ColumnNumericValueAboveMaxValuePercentSensorParametersSpec](#columnnumericvalueabovemaxvaluepercentsensorparametersspec)| | | |
|[warning](#minpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule1ParametersSpec](#minpercentrule1parametersspec)| | | |
|[error](#minpercentrule2parametersspec)|Default alerting threshold for a maximum number of rows with values with a value above the indicated by the user value in a column that raises a data quality error (alert).|[MinPercentRule2ParametersSpec](#minpercentrule2parametersspec)| | | |
|[fatal](#minpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule5ParametersSpec](#minpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericValueAboveMaxValuePercentSensorParametersSpec  
Column level sensor that calculates the percentage of values that are above than a given value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_value|This field can be used to define custom value. In order to define custom value, user should write correct value as an integer. If value is not defined by user then default value is 0|integer| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnMaxInRangeCheckSpec  
Column level check that ensures that the maximal values are in a set range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmaxsensorparametersspec)|Data quality check parameters|[ColumnNumericMaxSensorParametersSpec](#columnnumericmaxsensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a maximal values in range in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericMaxSensorParametersSpec  
Column level sensor that counts maximum value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## BetweenFloatsRuleParametersSpec  
Data quality rule that verifies if a data quality check readout is between from and to values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|from|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | |10.0<br/>|
|to|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | |20.5<br/>|









___  

## ColumnMinInRangeCheckSpec  
Column level check that ensures that the minimal values are in a set range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericminsensorparametersspec)|Data quality check parameters|[ColumnNumericMinSensorParametersSpec](#columnnumericminsensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a minimal values in range in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericMinSensorParametersSpec  
Column level sensor that counts minimum value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnMeanInRangeCheckSpec  
Column level check that ensures that the average (mean) of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a mean in range in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericMeanSensorParametersSpec  
Column level sensor that counts the average (mean) of values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPercentileInRangeCheckSpec  
Column level check that ensures that the percentile of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericpercentilesensorparametersspec)|Data quality check parameters|[ColumnNumericPercentileSensorParametersSpec](#columnnumericpercentilesensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a percentile in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericPercentileSensorParametersSpec  
Column level sensor that finds the percentile in a given column. The percentile value is defined by the user. It works only on numeric, big numeric and float data types.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_value|Must be a literal in the range [0, 1].|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnMedianInRangeCheckSpec  
Column level check that ensures that the median of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a median in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericMedianSensorParametersSpec  
Column level sensor that finds the median in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_value|Median (50th percentile), must equal 0.5|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPercentile10InRangeCheckSpec  
Column level check that ensures that the percentile 10 of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericpercentile10sensorparametersspec)|Data quality check parameters|[ColumnNumericPercentile10SensorParametersSpec](#columnnumericpercentile10sensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a percentile 10 in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericPercentile10SensorParametersSpec  
Column level sensor that finds the percentile 10 in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_value|10th percentile, must equal 0.1|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPercentile25InRangeCheckSpec  
Column level check that ensures that the percentile 25 of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericpercentile25sensorparametersspec)|Data quality check parameters|[ColumnNumericPercentile25SensorParametersSpec](#columnnumericpercentile25sensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a percentile 25 in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericPercentile25SensorParametersSpec  
Column level sensor that finds the percentile 25 in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_value|25th percentile, must equal 0.25|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPercentile75InRangeCheckSpec  
Column level check that ensures that the percentile 75 of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericpercentile75sensorparametersspec)|Data quality check parameters|[ColumnNumericPercentile75SensorParametersSpec](#columnnumericpercentile75sensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a percentile 75 in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericPercentile75SensorParametersSpec  
Column level sensor that finds the percentile 75 in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_value|75th percentile, must equal 0.75|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPercentile90InRangeCheckSpec  
Column level check that ensures that the percentile 90 of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericpercentile90sensorparametersspec)|Data quality check parameters|[ColumnNumericPercentile90SensorParametersSpec](#columnnumericpercentile90sensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a percentile 90 in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericPercentile90SensorParametersSpec  
Column level sensor that finds the percentile 90 in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_value|90th percentile, must equal 0.9|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnSampleStddevInRangeCheckSpec  
Column level check that ensures the sample standard deviation is in a set range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsamplestddevsensorparametersspec)|Data quality check parameters|[ColumnNumericSampleStddevSensorParametersSpec](#columnnumericsamplestddevsensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a sample (unbiased) maximal values in range in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericSampleStddevSensorParametersSpec  
Column level sensor that calculates sample standard deviation in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPopulationStddevInRangeCheckSpec  
Column level check that ensures that the population standard deviation is in a set range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericpopulationstddevsensorparametersspec)|Data quality check parameters|[ColumnNumericPopulationStddevSensorParametersSpec](#columnnumericpopulationstddevsensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a population (biased) standard deviation in range in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericPopulationStddevSensorParametersSpec  
Column level sensor that calculates population standard deviation in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnSampleVarianceInRangeCheckSpec  
Column level check that ensures the sample variance is in a set range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsamplevariancesensorparametersspec)|Data quality check parameters|[ColumnNumericSampleVarianceSensorParametersSpec](#columnnumericsamplevariancesensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a sample (unbiased) maximal values in range in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericSampleVarianceSensorParametersSpec  
Column level sensor that calculates sample variance in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPopulationVarianceInRangeCheckSpec  
Column level check that ensures that the population variance is in a set range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericpopulationvariancesensorparametersspec)|Data quality check parameters|[ColumnNumericPopulationVarianceSensorParametersSpec](#columnnumericpopulationvariancesensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a population (biased) standard deviation in range in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericPopulationVarianceSensorParametersSpec  
Column level sensor that calculates population variance in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnSumInRangeCheckSpec  
Column level check that ensures that the sum of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a sum in range in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericSumSensorParametersSpec  
Column level sensor that counts the sum of values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnInvalidLatitudeCountCheckSpec  
Column level check that ensures that there are no more than a set number of invalid latitude values in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericinvalidlatitudecountsensorparametersspec)|Data quality check parameters|[ColumnNumericInvalidLatitudeCountSensorParametersSpec](#columnnumericinvalidlatitudecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a set number of rows with invalid latitude value in a column that raises a data quality alert|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericInvalidLatitudeCountSensorParametersSpec  
Column level sensor that counts invalid latitude in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnValidLatitudePercentCheckSpec  
Column level check that ensures that there are no more than a set percentage of valid latitude values in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericvalidlatitudepercentsensorparametersspec)|Data quality check parameters|[ColumnNumericValidLatitudePercentSensorParametersSpec](#columnnumericvalidlatitudepercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a set percentage of rows with valid latitude value in a column that raises a data quality alert|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericValidLatitudePercentSensorParametersSpec  
Column level sensor that counts percentage of valid latitude in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnInvalidLongitudeCountCheckSpec  
Column level check that ensures that there are no more than a set number of invalid longitude values in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericinvalidlongitudecountsensorparametersspec)|Data quality check parameters|[ColumnNumericInvalidLongitudeCountSensorParametersSpec](#columnnumericinvalidlongitudecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a set number of rows with invalid longitude value in a column that raises a data quality alert|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericInvalidLongitudeCountSensorParametersSpec  
Column level sensor that counts invalid longitude in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnValidLongitudePercentCheckSpec  
Column level check that ensures that there are no more than a set percentage of valid longitude values in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericvalidlongitudepercentsensorparametersspec)|Data quality check parameters|[ColumnNumericValidLongitudePercentSensorParametersSpec](#columnnumericvalidlongitudepercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a set percentage of rows with valid longitude value in a column that raises a data quality alert|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericValidLongitudePercentSensorParametersSpec  
Column level sensor that counts percentage of valid longitude in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnProfilingStringsChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for string.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[string_max_length](#columnstringmaxlengthcheckspec)|Verifies that the length of string in a column does not exceed the maximum accepted length.|[ColumnStringMaxLengthCheckSpec](#columnstringmaxlengthcheckspec)| | | |
|[string_min_length](#columnstringminlengthcheckspec)|Verifies that the length of string in a column does not fall below the minimum accepted length.|[ColumnStringMinLengthCheckSpec](#columnstringminlengthcheckspec)| | | |
|[string_mean_length](#columnstringmeanlengthcheckspec)|Verifies that the length of string in a column does not exceed the mean accepted length.|[ColumnStringMeanLengthCheckSpec](#columnstringmeanlengthcheckspec)| | | |
|[string_length_below_min_length_count](#columnstringlengthbelowminlengthcountcheckspec)|The check counts the number of strings in the column that is below the length defined by the user as a parameter.|[ColumnStringLengthBelowMinLengthCountCheckSpec](#columnstringlengthbelowminlengthcountcheckspec)| | | |
|[string_length_below_min_length_percent](#columnstringlengthbelowminlengthpercentcheckspec)|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](#columnstringlengthbelowminlengthpercentcheckspec)| | | |
|[string_length_above_max_length_count](#columnstringlengthabovemaxlengthcountcheckspec)|The check counts the number of strings in the column that is above the length defined by the user as a parameter.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](#columnstringlengthabovemaxlengthcountcheckspec)| | | |
|[string_length_above_max_length_percent](#columnstringlengthabovemaxlengthpercentcheckspec)|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](#columnstringlengthabovemaxlengthpercentcheckspec)| | | |
|[string_length_in_range_percent](#columnstringlengthinrangepercentcheckspec)|The check counts the percentage of those strings with length in the range provided by the user in the column. |[ColumnStringLengthInRangePercentCheckSpec](#columnstringlengthinrangepercentcheckspec)| | | |
|[string_empty_count](#columnstringemptycountcheckspec)|Verifies that empty strings in a column does not exceed the maximum accepted count.|[ColumnStringEmptyCountCheckSpec](#columnstringemptycountcheckspec)| | | |
|[string_empty_percent](#columnstringemptypercentcheckspec)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.|[ColumnStringEmptyPercentCheckSpec](#columnstringemptypercentcheckspec)| | | |
|[string_whitespace_count](#columnstringwhitespacecountcheckspec)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count.|[ColumnStringWhitespaceCountCheckSpec](#columnstringwhitespacecountcheckspec)| | | |
|[string_whitespace_percent](#columnstringwhitespacepercentcheckspec)|Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.|[ColumnStringWhitespacePercentCheckSpec](#columnstringwhitespacepercentcheckspec)| | | |
|[string_surrounded_by_whitespace_count](#columnstringsurroundedbywhitespacecountcheckspec)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](#columnstringsurroundedbywhitespacecountcheckspec)| | | |
|[string_surrounded_by_whitespace_percent](#columnstringsurroundedbywhitespacepercentcheckspec)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](#columnstringsurroundedbywhitespacepercentcheckspec)| | | |
|[string_null_placeholder_count](#columnstringnullplaceholdercountcheckspec)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count.|[ColumnStringNullPlaceholderCountCheckSpec](#columnstringnullplaceholdercountcheckspec)| | | |
|[string_null_placeholder_percent](#columnstringnullplaceholderpercentcheckspec)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.|[ColumnStringNullPlaceholderPercentCheckSpec](#columnstringnullplaceholderpercentcheckspec)| | | |
|[string_boolean_placeholder_percent](#columnstringbooleanplaceholderpercentcheckspec)|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage.|[ColumnStringBooleanPlaceholderPercentCheckSpec](#columnstringbooleanplaceholderpercentcheckspec)| | | |
|[string_parsable_to_integer_percent](#columnstringparsabletointegerpercentcheckspec)|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage.|[ColumnStringParsableToIntegerPercentCheckSpec](#columnstringparsabletointegerpercentcheckspec)| | | |
|[string_parsable_to_float_percent](#columnstringparsabletofloatpercentcheckspec)|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage.|[ColumnStringParsableToFloatPercentCheckSpec](#columnstringparsabletofloatpercentcheckspec)| | | |
|[string_in_set_count](#columnstringinsetcountcheckspec)|Verifies that the number of strings from a set in a column does not fall below the minimum accepted count.|[ColumnStringInSetCountCheckSpec](#columnstringinsetcountcheckspec)| | | |
|[string_in_set_percent](#columnstringinsetpercentcheckspec)|Verifies that the percentage of strings from a set in a column does not fall below the minimum accepted percentage.|[ColumnStringInSetPercentCheckSpec](#columnstringinsetpercentcheckspec)| | | |
|[string_valid_dates_percent](#columnstringvaliddatespercentcheckspec)|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage.|[ColumnStringValidDatesPercentCheckSpec](#columnstringvaliddatespercentcheckspec)| | | |
|[string_valid_country_code_percent](#columnstringvalidcountrycodepercentcheckspec)|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage.|[ColumnStringValidCountryCodePercentCheckSpec](#columnstringvalidcountrycodepercentcheckspec)| | | |
|[string_valid_currency_code_percent](#columnstringvalidcurrencycodepercentcheckspec)|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage.|[ColumnStringValidCurrencyCodePercentCheckSpec](#columnstringvalidcurrencycodepercentcheckspec)| | | |
|[string_invalid_email_count](#columnstringinvalidemailcountcheckspec)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count.|[ColumnStringInvalidEmailCountCheckSpec](#columnstringinvalidemailcountcheckspec)| | | |
|[string_invalid_uuid_count](#columnstringinvaliduuidcountcheckspec)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count.|[ColumnStringInvalidUuidCountCheckSpec](#columnstringinvaliduuidcountcheckspec)| | | |
|[string_valid_uuid_percent](#columnstringvaliduuidpercentcheckspec)|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage.|[ColumnStringValidUuidPercentCheckSpec](#columnstringvaliduuidpercentcheckspec)| | | |
|[string_invalid_ip4_address_count](#columnstringinvalidip4addresscountcheckspec)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count.|[ColumnStringInvalidIp4AddressCountCheckSpec](#columnstringinvalidip4addresscountcheckspec)| | | |
|[string_invalid_ip6_address_count](#columnstringinvalidip6addresscountcheckspec)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count.|[ColumnStringInvalidIp6AddressCountCheckSpec](#columnstringinvalidip6addresscountcheckspec)| | | |
|[string_not_match_regex_count](#columnstringnotmatchregexcountcheckspec)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count.|[ColumnStringNotMatchRegexCountCheckSpec](#columnstringnotmatchregexcountcheckspec)| | | |
|[string_match_regex_percent](#columnstringmatchregexpercentcheckspec)|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage.|[ColumnStringMatchRegexPercentCheckSpec](#columnstringmatchregexpercentcheckspec)| | | |
|[string_not_match_date_regex_count](#columnstringnotmatchdateregexcountcheckspec)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count.|[ColumnStringNotMatchDateRegexCountCheckSpec](#columnstringnotmatchdateregexcountcheckspec)| | | |
|[string_match_date_regex_percent](#columnstringmatchdateregexpercentcheckspec)|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage.|[ColumnStringMatchDateRegexPercentCheckSpec](#columnstringmatchdateregexpercentcheckspec)| | | |
|[string_match_name_regex_percent](#columnstringmatchnameregexpercentcheckspec)|Verifies that the percentage of strings matching the name regex in a column does not fall below the minimum accepted percentage.|[ColumnStringMatchNameRegexPercentCheckSpec](#columnstringmatchnameregexpercentcheckspec)| | | |
|[string_most_popular_values](#columnstringmostpopularvaluescheckspec)|Verifies that the number of top values from a set in a column does not fall below the minimum accepted count.|[ColumnStringMostPopularValuesCheckSpec](#columnstringmostpopularvaluescheckspec)| | | |
|[string_datatype_detected](#columnstringdatatypedetectedcheckspec)|Detects the data type of text values stored in the column. The sensor returns the code of the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type.|[ColumnStringDatatypeDetectedCheckSpec](#columnstringdatatypedetectedcheckspec)| | | |









___  

## ColumnStringMaxLengthCheckSpec  
Column level check that ensures that the length of string in a column does not exceed the maximum accepted length.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringmaxlengthsensorparametersspec)|Data quality check parameters|[ColumnStringsStringMaxLengthSensorParametersSpec](#columnstringsstringmaxlengthsensorparametersspec)| | | |
|[warning](#maxvalueruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxValueRuleParametersSpec](#maxvalueruleparametersspec)| | | |
|[error](#maxvalueruleparametersspec)|Default alerting threshold for a maximum number of rows with nulls in a column that raises a data quality error (alert).|[MaxValueRuleParametersSpec](#maxvalueruleparametersspec)| | | |
|[fatal](#maxvalueruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxValueRuleParametersSpec](#maxvalueruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringMaxLengthSensorParametersSpec  
Column level sensor that ensures that the length of string in a column does not exceed the maximum accepted length.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringMinLengthCheckSpec  
Column level check that ensures that the length of string in a column does not fall below the minimum accepted length.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringminlengthsensorparametersspec)|Data quality check parameters|[ColumnStringsStringMinLengthSensorParametersSpec](#columnstringsstringminlengthsensorparametersspec)| | | |
|[warning](#minvalueruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinValueRuleParametersSpec](#minvalueruleparametersspec)| | | |
|[error](#minvalueruleparametersspec)|Default alerting threshold for a minimum length of string in a column that raises a data quality error (alert).|[MinValueRuleParametersSpec](#minvalueruleparametersspec)| | | |
|[fatal](#minvalueruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinValueRuleParametersSpec](#minvalueruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringMinLengthSensorParametersSpec  
Column level sensor that ensures that the length of string in a column does not exceed the minimum accepted length.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MinValueRuleParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_value|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | |1.5<br/>|









___  

## ColumnStringMeanLengthCheckSpec  
Column level check that ensures that the length of string in a column does not exceed the mean accepted length.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringmeanlengthsensorparametersspec)|Data quality check parameters|[ColumnStringsStringMeanLengthSensorParametersSpec](#columnstringsstringmeanlengthsensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a maximum number of rows with nulls in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringMeanLengthSensorParametersSpec  
Column level sensor that ensures that the length of string in a column does not exceed the mean accepted length.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringLengthBelowMinLengthCountCheckSpec  
Column level check that ensures that the number of strings in the monitored column with a length below the length defined by the user as a parameter does not exceed set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringlengthbelowminlengthcountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringLengthBelowMinLengthCountSensorParametersSpec](#columnstringsstringlengthbelowminlengthcountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with strings with a length below the indicated by the user length in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringLengthBelowMinLengthCountSensorParametersSpec  
Column level sensor that calculates the count of values that are shorter than a given length in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer| | |5<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringLengthBelowMinLengthPercentCheckSpec  
Column level check that ensures that the percentage of strings in the monitored column with a length below the length defined by the user as a parameter does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringlengthbelowminlengthpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringLengthBelowMinLengthPercentSensorParametersSpec](#columnstringsstringlengthbelowminlengthpercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a maximum percentage of rows with strings with a length below the indicated by the user length in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringLengthBelowMinLengthPercentSensorParametersSpec  
Column level sensor that calculates the percentage of values that are shorter than a given length in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer| | |5<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringLengthAboveMaxLengthCountCheckSpec  
Column level check that ensures that the number of strings in the monitored column with a length above the length defined by the user as a parameter does not exceed set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringlengthabovemaxlengthcountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringLengthAboveMaxLengthCountSensorParametersSpec](#columnstringsstringlengthabovemaxlengthcountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with strings with a length above the indicated by the user length in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringLengthAboveMaxLengthCountSensorParametersSpec  
Column level sensor that calculates the count of values that are longer than a given length in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer| | |5<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringLengthAboveMaxLengthPercentCheckSpec  
Column level check that ensures that the percentage of strings in the monitored column with a length above the length defined by the user as a parameter does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringlengthabovemaxlengthpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringLengthAboveMaxLengthPercentSensorParametersSpec](#columnstringsstringlengthabovemaxlengthpercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a maximum percentage of rows with strings with a length above the indicated by the user length in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringLengthAboveMaxLengthPercentSensorParametersSpec  
Column level sensor that calculates the percentage of values that are longer than a given length in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer| | |5<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringLengthInRangePercentCheckSpec  
Column check that calculates percentage of strings with a length below the indicated by the user length in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringlengthinrangepercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringLengthInRangePercentSensorParametersSpec](#columnstringsstringlengthinrangepercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a maximum percentage of rows with strings with a length in the range indicated by the user in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringLengthInRangePercentSensorParametersSpec  
Column level sensor that calculates percentage of strings with a length below the indicated length in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_length|Sets a minimal string length|integer| | |5<br/>|
|max_length|Sets a maximal string length.|integer| | |10<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringEmptyCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of empty strings in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringemptycountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringEmptyCountSensorParametersSpec](#columnstringsstringemptycountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with empty strings in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringEmptyCountSensorParametersSpec  
Column level sensor that calculates the number of rows with an empty string.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringEmptyPercentCheckSpec  
Column level check that ensures that there are no more than a maximum percent of empty strings in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringemptypercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringEmptyPercentSensorParametersSpec](#columnstringsstringemptypercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for a maximum number of rows with empty strings in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringEmptyPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with an empty string.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringWhitespaceCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of whitespace strings in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringwhitespacecountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringWhitespaceCountSensorParametersSpec](#columnstringsstringwhitespacecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with whitespace strings in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringWhitespaceCountSensorParametersSpec  
Column level sensor that calculates the number of rows with an whitespace string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringWhitespacePercentCheckSpec  
Column level check that ensures that there are no more than a maximum percent of whitespace strings in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringwhitespacepercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringWhitespacePercentSensorParametersSpec](#columnstringsstringwhitespacepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for a maximum number of rows with whitespace strings in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringWhitespacePercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a whitespace string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringSurroundedByWhitespaceCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of surrounded by whitespace strings in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringsurroundedbywhitespacecountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringSurroundedByWhitespaceCountSensorParametersSpec](#columnstringsstringsurroundedbywhitespacecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with surrounded by whitespace strings in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringSurroundedByWhitespaceCountSensorParametersSpec  
Column level sensor that calculates the number of rows with string surrounded by whitespace column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringSurroundedByWhitespacePercentCheckSpec  
Column level check that ensures that there are no more than a maximum percent of surrounded by whitespace strings in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringsurroundedbywhitespacepercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringSurroundedByWhitespacePercentSensorParametersSpec](#columnstringsstringsurroundedbywhitespacepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for a maximum number of rows with surrounded by whitespace strings in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringSurroundedByWhitespacePercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with string surrounded by whitespace column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringNullPlaceholderCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of rows with a null placeholder strings in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringnullplaceholdercountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringNullPlaceholderCountSensorParametersSpec](#columnstringsstringnullplaceholdercountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with a null placeholder strings in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringNullPlaceholderCountSensorParametersSpec  
Column level sensor that calculates the number of rows with a null placeholder string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringNullPlaceholderPercentCheckSpec  
Column level check that ensures that there are no more than a maximum percent of rows with a null placeholder strings in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringnullplaceholderpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringNullPlaceholderPercentSensorParametersSpec](#columnstringsstringnullplaceholderpercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for a maximum percentage of rows with a null placeholder strings in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringNullPlaceholderPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a null placeholder string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringBooleanPlaceholderPercentCheckSpec  
Column level check that ensures that the percentage of boolean placeholder strings in the monitored column does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringbooleanplaceholderpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringBooleanPlaceholderPercentSensorParametersSpec](#columnstringsstringbooleanplaceholderpercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a minimum percentage of rows with a boolean placeholder strings in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringBooleanPlaceholderPercentSensorParametersSpec  
Column level sensor that calculates the number of rows with a boolean placeholder string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringParsableToIntegerPercentCheckSpec  
Column level check that ensures that the percentage of parsable to integer strings in the monitored column does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringparsabletointegerpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringParsableToIntegerPercentSensorParametersSpec](#columnstringsstringparsabletointegerpercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a minimum percentage of rows with a parsable to integer strings in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringParsableToIntegerPercentSensorParametersSpec  
Column level sensor that calculates the number of rows with parsable to integer string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringParsableToFloatPercentCheckSpec  
Column level check that ensures that the percentage of parsable to float strings in the monitored column does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringparsabletofloatpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringParsableToFloatPercentSensorParametersSpec](#columnstringsstringparsabletofloatpercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a minimum percentage of rows with a parsable to float strings in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringParsableToFloatPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with parsable to float string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringInSetCountCheckSpec  
Column level check that ensures that the number of strings from a set in a column does not fall below the minimum accepted count.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringinsetcountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringInSetCountSensorParametersSpec](#columnstringsstringinsetcountsensorparametersspec)| | | |
|[warning](#mincountrulewarningparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinCountRuleWarningParametersSpec](#mincountrulewarningparametersspec)| | | |
|[error](#mincountrule0parametersspec)|Default alerting threshold for a maximum number of rows with empty strings in a column that raises a data quality error (alert).|[MinCountRule0ParametersSpec](#mincountrule0parametersspec)| | | |
|[fatal](#mincountrulefatalparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinCountRuleFatalParametersSpec](#mincountrulefatalparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringInSetCountSensorParametersSpec  
Column level sensor that calculates the number of strings from a set in a column does not exceed the minimum accepted count.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|values|Provided list of values to match the data.|string_list| | |USD<br/>GBP<br/>EUR<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringInSetPercentCheckSpec  
Column level check that ensures that the percentage of strings from a set in a column does not the fall below accepted count.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringinsetpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringInSetPercentSensorParametersSpec](#columnstringsstringinsetpercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a maximum number of rows with empty strings in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringInSetPercentSensorParametersSpec  
Column level sensor that calculates the percent of strings from a set in a column does not exceed the minimum accepted percentage.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|values|Provided list of values to match the data.|string_list| | |USD<br/>GBP<br/>EUR<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringValidDatesPercentCheckSpec  
Column level check that ensures that there is at least a minimum percentage of valid dates in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringvaliddatepercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringValidDatePercentSensorParametersSpec](#columnstringsstringvaliddatepercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a maximum number of rows with nulls in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringValidDatePercentSensorParametersSpec  
Column level sensor that ensures that there is at least a minimum percentage of valid dates in a monitored column..  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringValidCountryCodePercentCheckSpec  
Column level check that ensures that the percentage of valid country code strings in the monitored column does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringvalidcountrycodepercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringValidCountryCodePercentSensorParametersSpec](#columnstringsstringvalidcountrycodepercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a maximum percentage of rows with a valid country code strings in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringValidCountryCodePercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid country code string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringValidCurrencyCodePercentCheckSpec  
Column level check that ensures that the percentage of valid currency code strings in the monitored column does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringvalidcurrencycodepercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringValidCurrencyCodePercentSensorParametersSpec](#columnstringsstringvalidcurrencycodepercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a maximum percentage of rows with a valid currency code strings in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringValidCurrencyCodePercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid currency code string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringInvalidEmailCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of invalid email in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringinvalidemailcountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringInvalidEmailCountSensorParametersSpec](#columnstringsstringinvalidemailcountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with invalid emails in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringInvalidEmailCountSensorParametersSpec  
Column level sensor that calculates the number of rows with an invalid emails value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringInvalidUuidCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of invalid UUID in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringinvaliduuidcountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringInvalidUuidCountSensorParametersSpec](#columnstringsstringinvaliduuidcountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with invalid uuid in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringInvalidUuidCountSensorParametersSpec  
Column level sensor that calculates the number of rows with an invalid uuid value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringValidUuidPercentCheckSpec  
Column level check that ensures that the percentage of valid UUID strings in the monitored column does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringvaliduuidpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringValidUuidPercentSensorParametersSpec](#columnstringsstringvaliduuidpercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a minimum percentage of rows with a valid UUID in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringValidUuidPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid UUID value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringInvalidIp4AddressCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of invalid IP4 address in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringinvalidip4addresscountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringInvalidIp4AddressCountSensorParametersSpec](#columnstringsstringinvalidip4addresscountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with invalid IP4 address in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringInvalidIp4AddressCountSensorParametersSpec  
Column level sensor that calculates the number of rows with an invalid IP4 address value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringInvalidIp6AddressCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of invalid IP6 address in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringinvalidip6addresscountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringInvalidIp6AddressCountSensorParametersSpec](#columnstringsstringinvalidip6addresscountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with invalid IP6 address in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringInvalidIp6AddressCountSensorParametersSpec  
Column level sensor that calculates the number of rows with an invalid IP6 address value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringNotMatchRegexCountCheckSpec  
Column check that calculates quantity of values that does not match the custom regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringnotmatchregexcountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringNotMatchRegexCountSensorParametersSpec](#columnstringsstringnotmatchregexcountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with not matching regex in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringNotMatchRegexCountSensorParametersSpec  
Column level sensor that calculates the number of values that does not fit to a regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|regex|This field can be used to define custom regex. In order to define custom regex, user should write correct regex as a string. If regex is not defined by user then default regex is null|string| | |^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringMatchRegexPercentCheckSpec  
Column check that calculates percentage of values that matches the custom regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringmatchregexpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringMatchRegexPercentSensorParametersSpec](#columnstringsstringmatchregexpercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a minimum percentage of rows with matching regex in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringMatchRegexPercentSensorParametersSpec  
Column level sensor that calculates the percent of values that fit to a regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|regex|This field can be used to define custom regex. In order to define custom regex, user should write correct regex as a string. If regex is not defined by user then default regex is null|string| | |^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringNotMatchDateRegexCountCheckSpec  
Column check that calculates quantity of values that does not match the date regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringnotmatchdateregexcountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringNotMatchDateRegexCountSensorParametersSpec](#columnstringsstringnotmatchdateregexcountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with not matching date regex in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringNotMatchDateRegexCountSensorParametersSpec  
Column level sensor that calculates the number of values that does not fit to a date regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|date_formats|Desired date format. Sensor will try to parse the column records and cast the data using this format.|enum|YYYY-MM-DD<br/>DD/MM/YYYY<br/>Month D, YYYY<br/>YYYY/MM/DD<br/>MM/DD/YYYY<br/>| | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringMatchDateRegexPercentCheckSpec  
Column check that calculates percentage of values that match the date regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringmatchdateregexpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringMatchDateRegexPercentSensorParametersSpec](#columnstringsstringmatchdateregexpercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a maximum percentage of rows with matching date regex in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringMatchDateRegexPercentSensorParametersSpec  
Column level sensor that calculates the percentage of values that does fit a given date regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|date_formats|Desired date format. Sensor will try to parse the column records and cast the data using this format.|enum|YYYY-MM-DD<br/>DD/MM/YYYY<br/>Month D, YYYY<br/>YYYY/MM/DD<br/>MM/DD/YYYY<br/>| | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringMatchNameRegexPercentCheckSpec  
Column check that calculates percentage of values that match the name regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringmatchnameregexpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringMatchNameRegexPercentSensorParametersSpec](#columnstringsstringmatchnameregexpercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a maximum percentage of rows with matching name regex in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringMatchNameRegexPercentSensorParametersSpec  
Column level sensor that calculates the percentage of values that does fit a given name regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringMostPopularValuesCheckSpec  
Column level check that ensures that the number of top values from a set in a column does not fall below the minimum accepted count.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringmostpopularvaluessensorparametersspec)|Data quality check parameters|[ColumnStringsStringMostPopularValuesSensorParametersSpec](#columnstringsstringmostpopularvaluessensorparametersspec)| | | |
|[warning](#mincountrulewarningparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinCountRuleWarningParametersSpec](#mincountrulewarningparametersspec)| | | |
|[error](#mincountrule0parametersspec)|Default alerting threshold for a maximum number of rows with empty strings in a column that raises a data quality error (alert).|[MinCountRule0ParametersSpec](#mincountrule0parametersspec)| | | |
|[fatal](#mincountrulefatalparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinCountRuleFatalParametersSpec](#mincountrulefatalparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringMostPopularValuesSensorParametersSpec  
Column level sensor that counts how many expected values are present in the top most popular values in the column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|expected_values|Provided list of values to match the data.|string_list| | |USD<br/>GBP<br/>EUR<br/>|
|top_values|Provided limit of top popular values.|long| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringDatatypeDetectedCheckSpec  
Table level check that scans all values in a string column and detects the data type of all values in a column. The actual_value returned from the sensor is one of: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.
 The check compares the data type detected in all non-null columns to an expected data type. The rule compares the value using equals and requires values in the range 1..7, which are the codes of detected data types.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringdatatypedetectsensorparametersspec)|The sensor parameters for a sensor that returns a value that identifies the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|[ColumnStringsStringDatatypeDetectSensorParametersSpec](#columnstringsstringdatatypedetectsensorparametersspec)| | | |
|[warning](#datatypeequalsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check, detects that the data type of values stored in a column matches an expected data type code (1..7).|[DatatypeEqualsRuleParametersSpec](#datatypeequalsruleparametersspec)| | | |
|[error](#datatypeequalsruleparametersspec)|Default alerting threshold for a row count that raises a data quality error (alert), detects that the data type of values stored in a column matches an expected data type code (1..7).|[DatatypeEqualsRuleParametersSpec](#datatypeequalsruleparametersspec)| | | |
|[fatal](#datatypeequalsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem, detects that the data type of values stored in a column matches an expected data type code (1..7).|[DatatypeEqualsRuleParametersSpec](#datatypeequalsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringDatatypeDetectSensorParametersSpec  
Column level sensor that analyzes all values in a text column and detects the data type of the values.
 The sensor returns a value that identifies the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## DatatypeEqualsRuleParametersSpec  
Data quality rule that verifies that a data quality check readout of a string_datatype_detect (the data type detection) matches an expected data type.
 The supported values are in the range 1..7, which are: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|expected_datatype|Expected data type code, the data type codes are: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|integer| | |1<br/>|









___  

## ColumnProfilingUniquenessChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for negative values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[unique_count](#columnuniquecountcheckspec)|Verifies that the number of unique values in a column does not fall below the minimum accepted count.|[ColumnUniqueCountCheckSpec](#columnuniquecountcheckspec)| | | |
|[unique_percent](#columnuniquepercentcheckspec)|Verifies that the percentage of unique values in a column does not fall below the minimum accepted percent.|[ColumnUniquePercentCheckSpec](#columnuniquepercentcheckspec)| | | |
|[duplicate_count](#columnduplicatecountcheckspec)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.|[ColumnDuplicateCountCheckSpec](#columnduplicatecountcheckspec)| | | |
|[duplicate_percent](#columnduplicatepercentcheckspec)|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.|[ColumnDuplicatePercentCheckSpec](#columnduplicatepercentcheckspec)| | | |









___  

## ColumnUniqueCountCheckSpec  
Column level check that ensures that the number of unique values in a column does not fall below the minimum accepted count.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnuniquenessuniquecountsensorparametersspec)|Data quality check parameters|[ColumnUniquenessUniqueCountSensorParametersSpec](#columnuniquenessuniquecountsensorparametersspec)| | | |
|[warning](#mincountrulewarningparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinCountRuleWarningParametersSpec](#mincountrulewarningparametersspec)| | | |
|[error](#mincountrule0parametersspec)|Default alerting threshold for a maximum number of rows with nulls in a column that raises a data quality error (alert).|[MinCountRule0ParametersSpec](#mincountrule0parametersspec)| | | |
|[fatal](#mincountrulefatalparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinCountRuleFatalParametersSpec](#mincountrulefatalparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnUniquenessUniqueCountSensorParametersSpec  
Column level sensor that calculates the number of unique non-null values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnUniquePercentCheckSpec  
Column level check that ensures that the percentage of unique values in a column does not fall below the minimum accepted percentage.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnuniquenessuniquepercentsensorparametersspec)|Data quality check parameters|[ColumnUniquenessUniquePercentSensorParametersSpec](#columnuniquenessuniquepercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a minimum percentage of rows with unique value in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnUniquenessUniquePercentSensorParametersSpec  
Column level sensor that calculates the percentage of unique values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnDuplicateCountCheckSpec  
Column level check that ensures that the number of duplicate values in a column does not exceed the maximum accepted count.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnuniquenessduplicatecountsensorparametersspec)|Data quality check parameters|[ColumnUniquenessDuplicateCountSensorParametersSpec](#columnuniquenessduplicatecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with nulls in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnUniquenessDuplicateCountSensorParametersSpec  
Column level sensor that calculates the number of duplicate values in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnDuplicatePercentCheckSpec  
Column level check that ensures that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnuniquenessduplicatepercentsensorparametersspec)|Data quality check parameters|[ColumnUniquenessDuplicatePercentSensorParametersSpec](#columnuniquenessduplicatepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for a maximum number of rows with nulls in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnUniquenessDuplicatePercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows that are duplicates.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnProfilingDatetimeChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for datetime.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[date_values_in_future_percent](#columndatevaluesinfuturepercentcheckspec)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage.|[ColumnDateValuesInFuturePercentCheckSpec](#columndatevaluesinfuturepercentcheckspec)| | | |
|[datetime_value_in_range_date_percent](#columndatetimevalueinrangedatepercentcheckspec)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](#columndatetimevalueinrangedatepercentcheckspec)| | | |









___  

## ColumnDateValuesInFuturePercentCheckSpec  
Column level check that ensures that there are no more than a set percentage of date values in future in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columndatetimedatevaluesinfuturepercentsensorparametersspec)|Data quality check parameters|[ColumnDatetimeDateValuesInFuturePercentSensorParametersSpec](#columndatetimedatevaluesinfuturepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for a set percentage of date values in future in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnDatetimeDateValuesInFuturePercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a date value in the future, compared with the current date.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnDatetimeValueInRangeDatePercentCheckSpec  
Column level check that ensures that there are no more than a set percentage of date values in given range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columndatetimevalueinrangedatepercentsensorparametersspec)|Data quality check parameters|[ColumnDatetimeValueInRangeDatePercentSensorParametersSpec](#columndatetimevalueinrangedatepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for a set percentage of date values in the range defined by the user in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnDatetimeValueInRangeDatePercentSensorParametersSpec  
Column level sensor that calculates the percent of non-negative values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_value|Lower bound range variable.|date| | | |
|max_value|Upper bound range variable.|date| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnProfilingPiiChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for Personal Identifiable Information (PII).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[valid_usa_phone_percent](#columnpiivalidusaphonepercentcheckspec)|Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage.|[ColumnPiiValidUsaPhonePercentCheckSpec](#columnpiivalidusaphonepercentcheckspec)| | | |
|[contains_usa_phone_percent](#columnpiicontainsusaphonepercentcheckspec)|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage.|[ColumnPiiContainsUsaPhonePercentCheckSpec](#columnpiicontainsusaphonepercentcheckspec)| | | |
|[valid_usa_zipcode_percent](#columnpiivalidusazipcodepercentcheckspec)|Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage.|[ColumnPiiValidUsaZipcodePercentCheckSpec](#columnpiivalidusazipcodepercentcheckspec)| | | |
|[contains_usa_zipcode_percent](#columnpiicontainsusazipcodepercentcheckspec)|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](#columnpiicontainsusazipcodepercentcheckspec)| | | |
|[valid_email_percent](#columnpiivalidemailpercentcheckspec)|Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage.|[ColumnPiiValidEmailPercentCheckSpec](#columnpiivalidemailpercentcheckspec)| | | |
|[contains_email_percent](#columnpiicontainsemailpercentcheckspec)|Verifies that the percentage of rows that contains valid emails in a column does not exceed the minimum accepted percentage.|[ColumnPiiContainsEmailPercentCheckSpec](#columnpiicontainsemailpercentcheckspec)| | | |
|[valid_ip4_address_percent](#columnpiivalidip4addresspercentcheckspec)|Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage.|[ColumnPiiValidIp4AddressPercentCheckSpec](#columnpiivalidip4addresspercentcheckspec)| | | |
|[contains_ip4_percent](#columnpiicontainsip4percentcheckspec)|Verifies that the percentage of rows that contains valid IP4 address values in a column does not fall below the minimum accepted percentage.|[ColumnPiiContainsIp4PercentCheckSpec](#columnpiicontainsip4percentcheckspec)| | | |
|[valid_ip6_address_percent](#columnpiivalidip6addresspercentcheckspec)|Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage.|[ColumnPiiValidIp6AddressPercentCheckSpec](#columnpiivalidip6addresspercentcheckspec)| | | |
|[contains_ip6_percent](#columnpiicontainsip6percentcheckspec)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage.|[ColumnPiiContainsIp6PercentCheckSpec](#columnpiicontainsip6percentcheckspec)| | | |









___  

## ColumnPiiValidUsaPhonePercentCheckSpec  
Column check that calculates percent of valid USA phone values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiivalidusaphonepercentsensorparametersspec)|Numerical value in range percent sensor parameters|[ColumnPiiValidUsaPhonePercentSensorParametersSpec](#columnpiivalidusaphonepercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a minimum percentage of rows with a parsable to integer strings in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiValidUsaPhonePercentSensorParametersSpec  
Column level sensor that calculates the percent of values that fit to a USA phone regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiContainsUsaPhonePercentCheckSpec  
Column check that calculates the percentage of rows that contains USA phone number values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiicontainsusaphonepercentsensorparametersspec)|Numerical value in range percent sensor parameters|[ColumnPiiContainsUsaPhonePercentSensorParametersSpec](#columnpiicontainsusaphonepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for the minimum percentage of rows that contains a USA phone number in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiContainsUsaPhonePercentSensorParametersSpec  
Column level sensor that calculates the percent of values that contains a USA phone number in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiValidUsaZipcodePercentCheckSpec  
Column check that calculates percent of valid USA Zip code values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiivalidusazipcodepercentsensorparametersspec)|Data quality check parameters|[ColumnPiiValidUsaZipcodePercentSensorParametersSpec](#columnpiivalidusazipcodepercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a minimum percentage of rows with a parsable to integer strings in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiValidUsaZipcodePercentSensorParametersSpec  
Column level sensor that calculates the percent of values that fit to a USA ZIP code regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiContainsUsaZipcodePercentCheckSpec  
Column check that calculates the percentage of rows that contains USA zip code values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiicontainsusazipcodepercentsensorparametersspec)|Numerical value in range percent sensor parameters|[ColumnPiiContainsUsaZipcodePercentSensorParametersSpec](#columnpiicontainsusazipcodepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for the minimum percentage of rows that contains a USA zip code number in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiContainsUsaZipcodePercentSensorParametersSpec  
Column level sensor that calculates the percent of values that contain a USA ZIP code number in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiValidEmailPercentCheckSpec  
Column check that calculates the percentage of rows that contains valid email values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiivalidemailpercentsensorparametersspec)|Data quality check parameters|[ColumnPiiValidEmailPercentSensorParametersSpec](#columnpiivalidemailpercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a minimum percentage of rows with a valid email in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiValidEmailPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid email value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiContainsEmailPercentCheckSpec  
Column check that calculates the percentage of rows that contains valid email values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiicontainsemailpercentsensorparametersspec)|Data quality check parameters|[ColumnPiiContainsEmailPercentSensorParametersSpec](#columnpiicontainsemailpercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a minimum percentage of rows that contains email values in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiContainsEmailPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid email value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiValidIp4AddressPercentCheckSpec  
Column check that calculates percent of valid IP4 address values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiivalidip4addresspercentsensorparametersspec)|Data quality check parameters|[ColumnPiiValidIp4AddressPercentSensorParametersSpec](#columnpiivalidip4addresspercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a minimum percentage of rows with a valid IP4 addresses in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiValidIp4AddressPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid IP4 address value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiContainsIp4PercentCheckSpec  
Column check that calculates the percentage of rows that contains valid IP4 address values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiicontainsip4percentsensorparametersspec)|Data quality check parameters|[ColumnPiiContainsIp4PercentSensorParametersSpec](#columnpiicontainsip4percentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a minimum percentage of rows that contains IP4 values in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiContainsIp4PercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid IP4 value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiValidIp6AddressPercentCheckSpec  
Column check that calculates the percent of valid IP6 address values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiivalidip6addresspercentsensorparametersspec)|Data quality check parameters|[ColumnPiiValidIp6AddressPercentSensorParametersSpec](#columnpiivalidip6addresspercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a minimum percentage of rows with a valid IP6 addresses in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiValidIp6AddressPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid IP6 address value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiContainsIp6PercentCheckSpec  
Column check that calculates the percentage of rows that contains valid IP6 address values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiicontainsip6percentsensorparametersspec)|Data quality check parameters|[ColumnPiiContainsIp6PercentSensorParametersSpec](#columnpiicontainsip6percentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a minimum percentage of rows that contains IP6 values in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiContainsIp6PercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid IP6 value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnProfilingSqlChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[sql_condition_passed_percent_on_column](#columnsqlconditionpassedpercentcheckspec)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression).|[ColumnSqlConditionPassedPercentCheckSpec](#columnsqlconditionpassedpercentcheckspec)| | | |
|[sql_condition_failed_count_on_column](#columnsqlconditionfailedcountcheckspec)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count.|[ColumnSqlConditionFailedCountCheckSpec](#columnsqlconditionfailedcountcheckspec)| | | |
|[sql_aggregate_expr_column](#columnsqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|[ColumnSqlAggregateExprCheckSpec](#columnsqlaggregateexprcheckspec)| | | |









___  

## ColumnSqlConditionPassedPercentCheckSpec  
Column level check that ensures that a set percentage of rows passed a custom SQL condition (expression).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnsqlconditionpassedpercentsensorparametersspec)|Sensor parameters with the custom SQL condition (an expression that returns true/false) which is evaluated on a each row for the given column, using a {column} placeholder to reference the current column.|[ColumnSqlConditionPassedPercentSensorParametersSpec](#columnsqlconditionpassedpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning when a minimum acceptable percentage of rows did not pass the custom SQL condition (expression). The warning is considered as a passed data quality check.|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum acceptable percentage of rows passing the custom SQL condition (expression) that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue when a minimum acceptable percentage of rows did not pass the custom SQL condition (expression). A fatal issue indicates a serious data quality problem that should result in stopping the data pipelines.|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnSqlConditionPassedPercentSensorParametersSpec  
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string| | |{column} + col_tax &#x3D; col_total_price_with_tax<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnSqlConditionFailedCountCheckSpec  
Column level check that ensures that there are no more than a set number of rows fail a custom SQL condition (expression) evaluated for a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnsqlconditionfailedcountsensorparametersspec)|Sensor parameters with the custom SQL condition (an expression that returns true/false) which is evaluated on a each row, using a {column} placeholder to reference the current column.|[ColumnSqlConditionFailedCountSensorParametersSpec](#columnsqlconditionfailedcountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning when a given number of rows failed the custom SQL condition (expression). The warning is considered as a passed data quality check.|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows failing the custom SQL condition (expression) that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue when a given number of rows failed the custom SQL condition (expression). A fatal issue indicates a serious data quality problem that should result in stopping the data pipelines.|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnSqlConditionFailedCountSensorParametersSpec  
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string| | |{column} + col_tax &#x3D; col_total_price_with_tax<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnSqlAggregateExprCheckSpec  
Column level check that calculates a given SQL aggregate expression on a column and compares it with a maximum accepted value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnsqlaggregatedexpressionsensorparametersspec)|Sensor parameters with the custom SQL aggregate expression that is evaluated on a column|[ColumnSqlAggregatedExpressionSensorParametersSpec](#columnsqlaggregatedexpressionsensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Default alerting threshold for warnings raised when the aggregated value is above the maximum accepted value.|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for errors raised when the aggregated value is above the maximum accepted value.|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Default alerting threshold for fatal data quality issues raised when the aggregated value is above the maximum accepted value.|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnSqlAggregatedExpressionSensorParametersSpec  
Column level sensor that executes a given SQL expression on a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sql_expression|SQL aggregate expression that returns a numeric value calculated from rows. The expression is evaluated on a whole table or withing a GROUP BY clause for daily partitions and/or data streams. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string| | |MAX({column})<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnProfilingBoolChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for booleans.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[true_percent](#columntruepercentcheckspec)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage.|[ColumnTruePercentCheckSpec](#columntruepercentcheckspec)| | | |
|[false_percent](#columnfalsepercentcheckspec)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage.|[ColumnFalsePercentCheckSpec](#columnfalsepercentcheckspec)| | | |









___  

## ColumnTruePercentCheckSpec  
Column level check that ensures that there are at least percentage of rows with a true value in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnbooltruepercentsensorparametersspec)|Data quality check parameters|[ColumnBoolTruePercentSensorParametersSpec](#columnbooltruepercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a set percentage of true value in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnBoolTruePercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a true value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnFalsePercentCheckSpec  
Column level check that ensures that there are at least a minimum percentage of rows with a false value in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnboolfalsepercentsensorparametersspec)|Data quality check parameters|[ColumnBoolFalsePercentSensorParametersSpec](#columnboolfalsepercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a set percentage of false value in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnBoolFalsePercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a false value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnProfilingIntegrityChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for integrity.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[foreign_key_not_match_count](#columnintegrityforeignkeynotmatchcountcheckspec)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](#columnintegrityforeignkeynotmatchcountcheckspec)| | | |
|[foreign_key_match_percent](#columnintegrityforeignkeymatchpercentcheckspec)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](#columnintegrityforeignkeymatchpercentcheckspec)| | | |









___  

## ColumnIntegrityForeignKeyNotMatchCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of values not matching values in another table column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnintegrityforeignkeynotmatchcountsensorparametersspec)|Data quality check parameters|[ColumnIntegrityForeignKeyNotMatchCountSensorParametersSpec](#columnintegrityforeignkeynotmatchcountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with values not matching values in another table column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnIntegrityForeignKeyNotMatchCountSensorParametersSpec  
Column level sensor that calculates the count of values that does not match values in column of another table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|foreign_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| | |dim_customer<br/>|
|foreign_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| | |customer_id<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnIntegrityForeignKeyMatchPercentCheckSpec  
Column level check that ensures that there are no more than a minimum percentage of values matching values in another table column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnintegrityforeignkeymatchpercentsensorparametersspec)|Data quality check parameters|[ColumnIntegrityForeignKeyMatchPercentSensorParametersSpec](#columnintegrityforeignkeymatchpercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a minimum percentage of rows with values matching values in another table column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnIntegrityForeignKeyMatchPercentSensorParametersSpec  
Column level sensor that calculates the percentage of values that match values in column of another table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|foreign_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| | |dim_customer<br/>|
|foreign_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| | |customer_id<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnProfilingAccuracyChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for accuracy.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[total_sum_match_percent](#columnaccuracytotalsummatchpercentcheckspec)|Verifies that percentage of the difference in sum of a column in a table and sum of a column of another table does not exceed the set number.|[ColumnAccuracyTotalSumMatchPercentCheckSpec](#columnaccuracytotalsummatchpercentcheckspec)| | | |
|[min_match_percent](#columnaccuracyminmatchpercentcheckspec)|Verifies that the percentage of difference in min of a column in a table and min of a column of another table does not exceed the set number.|[ColumnAccuracyMinMatchPercentCheckSpec](#columnaccuracyminmatchpercentcheckspec)| | | |
|[max_match_percent](#columnaccuracymaxmatchpercentcheckspec)|Verifies that the percentage of difference in max of a column in a table and max of a column of another table does not exceed the set number.|[ColumnAccuracyMaxMatchPercentCheckSpec](#columnaccuracymaxmatchpercentcheckspec)| | | |
|[average_match_percent](#columnaccuracyaveragematchpercentcheckspec)|Verifies that the percentage of difference in average of a column in a table and average of a column of another table does not exceed the set number.|[ColumnAccuracyAverageMatchPercentCheckSpec](#columnaccuracyaveragematchpercentcheckspec)| | | |
|[not_null_count_match_percent](#columnaccuracynotnullcountmatchpercentcheckspec)|Verifies that the percentage of difference in row count of a column in a table and row count of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnAccuracyNotNullCountMatchPercentCheckSpec](#columnaccuracynotnullcountmatchpercentcheckspec)| | | |









___  

## ColumnAccuracyTotalSumMatchPercentCheckSpec  
Column level check that ensures that there are no more than a maximum percentage of difference of sum of a table column and of a sum of another table column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnaccuracytotalsummatchpercentsensorparametersspec)|Data quality check parameters|[ColumnAccuracyTotalSumMatchPercentSensorParametersSpec](#columnaccuracytotalsummatchpercentsensorparametersspec)| | | |
|[warning](#maxdiffpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDiffPercentRule1ParametersSpec](#maxdiffpercentrule1parametersspec)| | | |
|[error](#maxdiffpercentrule2parametersspec)|Default alerting threshold for a maximum percentage of difference of sum of a table column and of a sum of another table column that raises a data quality error (alert).|[MaxDiffPercentRule2ParametersSpec](#maxdiffpercentrule2parametersspec)| | | |
|[fatal](#maxdiffpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxDiffPercentRule5ParametersSpec](#maxdiffpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAccuracyTotalSumMatchPercentSensorParametersSpec  
Column level sensor that calculates percentage of the difference in sum of a column in a table and sum of a column of another table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| | |dim_customer<br/>|
|referenced_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| | |customer_id<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnAccuracyMinMatchPercentCheckSpec  
Column level check that ensures that there are no more than a maximum percentage of difference of min of a table column and of a min of another table column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnaccuracyminmatchpercentsensorparametersspec)|Data quality check parameters|[ColumnAccuracyMinMatchPercentSensorParametersSpec](#columnaccuracyminmatchpercentsensorparametersspec)| | | |
|[warning](#maxdiffpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDiffPercentRule1ParametersSpec](#maxdiffpercentrule1parametersspec)| | | |
|[error](#maxdiffpercentrule2parametersspec)|Default alerting threshold for a maximum percentage of difference of min of a table column and of a min of another table column that raises a data quality error (alert).|[MaxDiffPercentRule2ParametersSpec](#maxdiffpercentrule2parametersspec)| | | |
|[fatal](#maxdiffpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxDiffPercentRule5ParametersSpec](#maxdiffpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAccuracyMinMatchPercentSensorParametersSpec  
Column level sensor that calculates percentage of the difference in min of a column in a table and min of a column of another table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| | |dim_customer<br/>|
|referenced_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| | |customer_id<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnAccuracyMaxMatchPercentCheckSpec  
Column level check that ensures that there are no more than a maximum percentage of difference of max of a table column and of a max of another table column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnaccuracymaxmatchpercentsensorparametersspec)|Data quality check parameters|[ColumnAccuracyMaxMatchPercentSensorParametersSpec](#columnaccuracymaxmatchpercentsensorparametersspec)| | | |
|[warning](#maxdiffpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDiffPercentRule1ParametersSpec](#maxdiffpercentrule1parametersspec)| | | |
|[error](#maxdiffpercentrule2parametersspec)|Default alerting threshold for a maximum percentage of difference of max of a table column and of a max of another table column that raises a data quality error (alert).|[MaxDiffPercentRule2ParametersSpec](#maxdiffpercentrule2parametersspec)| | | |
|[fatal](#maxdiffpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxDiffPercentRule5ParametersSpec](#maxdiffpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAccuracyMaxMatchPercentSensorParametersSpec  
Column level sensor that calculates percentage of the difference in max of a column in a table and max of a column of another table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| | |dim_customer<br/>|
|referenced_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| | |customer_id<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnAccuracyAverageMatchPercentCheckSpec  
Column level check that ensures that there are no more than a maximum percentage of difference of average of a table column and of an average of another table column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnaccuracyaveragematchpercentsensorparametersspec)|Data quality check parameters|[ColumnAccuracyAverageMatchPercentSensorParametersSpec](#columnaccuracyaveragematchpercentsensorparametersspec)| | | |
|[warning](#maxdiffpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDiffPercentRule1ParametersSpec](#maxdiffpercentrule1parametersspec)| | | |
|[error](#maxdiffpercentrule2parametersspec)|Default alerting threshold for a maximum percentage of difference of average of a table column and of a average of another table column that raises a data quality error (alert).|[MaxDiffPercentRule2ParametersSpec](#maxdiffpercentrule2parametersspec)| | | |
|[fatal](#maxdiffpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxDiffPercentRule5ParametersSpec](#maxdiffpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAccuracyAverageMatchPercentSensorParametersSpec  
Column level sensor that calculates percentage of the difference in average of a column in a table and average of a column of another table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| | |dim_customer<br/>|
|referenced_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| | |customer_id<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnAccuracyNotNullCountMatchPercentCheckSpec  
Column level check that ensures that there are no more than a maximum percentage of difference of the row count of a tested table&#x27;s column (counting the not null values) and of an row count of another (reference) table, also counting all rows with not null values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnaccuracynotnullcountmatchpercentsensorparametersspec)|Data quality check parameters|[ColumnAccuracyNotNullCountMatchPercentSensorParametersSpec](#columnaccuracynotnullcountmatchpercentsensorparametersspec)| | | |
|[warning](#maxdiffpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDiffPercentRule1ParametersSpec](#maxdiffpercentrule1parametersspec)| | | |
|[error](#maxdiffpercentrule2parametersspec)|Default alerting threshold for a maximum percentage of difference of row count of a table column and of a row count of another table column that raises a data quality error (alert).|[MaxDiffPercentRule2ParametersSpec](#maxdiffpercentrule2parametersspec)| | | |
|[fatal](#maxdiffpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxDiffPercentRule5ParametersSpec](#maxdiffpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAccuracyNotNullCountMatchPercentSensorParametersSpec  
Column level sensor that calculates percentage of the difference in row count of a column in a table and row count of a column of another table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| | |dim_customer<br/>|
|referenced_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| | |customer_id<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnProfilingConsistencyChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for consistency.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[date_match_format_percent](#columnconsistencydatematchformatpercentcheckspec)|Verifies that the percentage of date values matching the given format in a column does not exceed the minimum accepted percentage.|[ColumnConsistencyDateMatchFormatPercentCheckSpec](#columnconsistencydatematchformatpercentcheckspec)| | | |
|[string_datatype_changed](#columnstringdatatypechangedcheckspec)|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|[ColumnStringDatatypeChangedCheckSpec](#columnstringdatatypechangedcheckspec)| | | |









___  

## ColumnConsistencyDateMatchFormatPercentCheckSpec  
Column check that calculates percentage of values that match the date format in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnconsistencydatematchformatpercentsensorparametersspec)|Data quality check parameters|[ColumnConsistencyDateMatchFormatPercentSensorParametersSpec](#columnconsistencydatematchformatpercentsensorparametersspec)| | | |
|[warning](#minpercentrule99parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[error](#minpercentrule98parametersspec)|Default alerting threshold for a maximum percentage of rows with matching date format in a column that raises a data quality error (alert).|[MinPercentRule98ParametersSpec](#minpercentrule98parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnConsistencyDateMatchFormatPercentSensorParametersSpec  
Column level sensor that calculates the percentage of values that does fit a given date regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|date_formats|Desired date format. Sensor will try to parse the column records and cast the data using this format.|enum|DD.MM.YYYY<br/>DD-MM-YYYY<br/>YYYY-MM-DD<br/>DD/MM/YYYY<br/>| | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringDatatypeChangedCheckSpec  
Table level check that scans all values in a string column and detects the data type of all values in a column. The actual_value returned from the sensor is one of: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.
 The check compares the data type detected during the current run to the last known data type detected during a previous run. For daily recurring checks, it will compare the value to yesterday&#x27;s value (or an earlier date).
 For partitioned checks, it will compare the current data type to the data type in the previous daily or monthly partition. The last partition with data is used for comparison.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringdatatypedetectsensorparametersspec)|The sensor parameters for a sensor that returns a value that identifies the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|[ColumnStringsStringDatatypeDetectSensorParametersSpec](#columnstringsstringdatatypedetectsensorparametersspec)| | | |
|[warning](#valuechangedparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check, detects that the data type of values stored in a column has changed since the last time it was evaluated or the data type in the current daily/monthly partition differs from the data type in the previous partition.|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[error](#valuechangedparametersspec)|Default alerting threshold for a row count that raises a data quality error (alert), detects that the data type of values stored in a column has changed since the last time it was evaluated or the data type in the current daily/monthly partition differs from the data type in the previous partition.|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[fatal](#valuechangedparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem, detects that the data type of values stored in a column has changed since the last time it was evaluated or the data type in the current daily/monthly partition differs from the data type in the previous partition.|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ValueChangedParametersSpec  
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|









___  

## ColumnProfilingAnomalyChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[mean_anomaly_7_days](#columnanomalymeanchange7dayscheckspec)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 7 days.|[ColumnAnomalyMeanChange7DaysCheckSpec](#columnanomalymeanchange7dayscheckspec)| | | |
|[mean_anomaly_30_days](#columnanomalymeanchange30dayscheckspec)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyMeanChange30DaysCheckSpec](#columnanomalymeanchange30dayscheckspec)| | | |
|[mean_anomaly_60_days](#columnanomalymeanchange60dayscheckspec)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 60 days.|[ColumnAnomalyMeanChange60DaysCheckSpec](#columnanomalymeanchange60dayscheckspec)| | | |
|[median_anomaly_7_days](#columnanomalymedianchange7dayscheckspec)|Verifies that the median in a column changes in a rate within a percentile boundary during last 7 days.|[ColumnAnomalyMedianChange7DaysCheckSpec](#columnanomalymedianchange7dayscheckspec)| | | |
|[median_anomaly_30_days](#columnanomalymedianchange30dayscheckspec)|Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyMedianChange30DaysCheckSpec](#columnanomalymedianchange30dayscheckspec)| | | |
|[median_anomaly_60_days](#columnanomalymedianchange60dayscheckspec)|Verifies that the median in a column changes in a rate within a percentile boundary during last 60 days.|[ColumnAnomalyMedianChange60DaysCheckSpec](#columnanomalymedianchange60dayscheckspec)| | | |
|[sum_anomaly_7_days](#columnanomalysumchange7dayscheckspec)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 7 days.|[ColumnAnomalySumChange7DaysCheckSpec](#columnanomalysumchange7dayscheckspec)| | | |
|[sum_anomaly_30_days](#columnanomalysumchange30dayscheckspec)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalySumChange30DaysCheckSpec](#columnanomalysumchange30dayscheckspec)| | | |
|[sum_anomaly_60_days](#columnanomalysumchange60dayscheckspec)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 60 days.|[ColumnAnomalySumChange60DaysCheckSpec](#columnanomalysumchange60dayscheckspec)| | | |









___  

## ColumnAnomalyMeanChange7DaysCheckSpec  
Column level check that ensures that the mean value in a monitored column changes in a rate within a two-tailed percentile during last 7 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
|[warning](#changepercentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ChangePercentileMovingWithin7DaysRuleParametersSpec](#changepercentilemovingwithin7daysruleparametersspec)| | | |
|[error](#changepercentilemovingwithin7daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[ChangePercentileMovingWithin7DaysRuleParametersSpec](#changepercentilemovingwithin7daysruleparametersspec)| | | |
|[fatal](#changepercentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ChangePercentileMovingWithin7DaysRuleParametersSpec](#changepercentilemovingwithin7daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAnomalyMeanChange30DaysCheckSpec  
Column level check that ensures that the mean value in a monitored column changes in a rate within a two-tailed percentile during last 30 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
|[warning](#changepercentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ChangePercentileMovingWithin30DaysRuleParametersSpec](#changepercentilemovingwithin30daysruleparametersspec)| | | |
|[error](#changepercentilemovingwithin30daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[ChangePercentileMovingWithin30DaysRuleParametersSpec](#changepercentilemovingwithin30daysruleparametersspec)| | | |
|[fatal](#changepercentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ChangePercentileMovingWithin30DaysRuleParametersSpec](#changepercentilemovingwithin30daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAnomalyMeanChange60DaysCheckSpec  
Column level check that ensures that the mean value in a monitored column changes in a rate within a two-tailed percentile during last 60 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
|[warning](#changepercentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ChangePercentileMovingWithin60DaysRuleParametersSpec](#changepercentilemovingwithin60daysruleparametersspec)| | | |
|[error](#changepercentilemovingwithin60daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[ChangePercentileMovingWithin60DaysRuleParametersSpec](#changepercentilemovingwithin60daysruleparametersspec)| | | |
|[fatal](#changepercentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ChangePercentileMovingWithin60DaysRuleParametersSpec](#changepercentilemovingwithin60daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAnomalyMedianChange7DaysCheckSpec  
Column level check that ensures that the median in a monitored column changes in a rate within a two-tailed percentile during last 7 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
|[warning](#changepercentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ChangePercentileMovingWithin7DaysRuleParametersSpec](#changepercentilemovingwithin7daysruleparametersspec)| | | |
|[error](#changepercentilemovingwithin7daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[ChangePercentileMovingWithin7DaysRuleParametersSpec](#changepercentilemovingwithin7daysruleparametersspec)| | | |
|[fatal](#changepercentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ChangePercentileMovingWithin7DaysRuleParametersSpec](#changepercentilemovingwithin7daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAnomalyMedianChange30DaysCheckSpec  
Column level check that ensures that the median in a monitored column changes in a rate within a two-tailed percentile during last 30 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
|[warning](#changepercentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ChangePercentileMovingWithin30DaysRuleParametersSpec](#changepercentilemovingwithin30daysruleparametersspec)| | | |
|[error](#changepercentilemovingwithin30daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[ChangePercentileMovingWithin30DaysRuleParametersSpec](#changepercentilemovingwithin30daysruleparametersspec)| | | |
|[fatal](#changepercentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ChangePercentileMovingWithin30DaysRuleParametersSpec](#changepercentilemovingwithin30daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAnomalyMedianChange60DaysCheckSpec  
Column level check that ensures that the median in a monitored column changes in a rate within a two-tailed percentile during last 60 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
|[warning](#changepercentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ChangePercentileMovingWithin60DaysRuleParametersSpec](#changepercentilemovingwithin60daysruleparametersspec)| | | |
|[error](#changepercentilemovingwithin60daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[ChangePercentileMovingWithin60DaysRuleParametersSpec](#changepercentilemovingwithin60daysruleparametersspec)| | | |
|[fatal](#changepercentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ChangePercentileMovingWithin60DaysRuleParametersSpec](#changepercentilemovingwithin60daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAnomalySumChange7DaysCheckSpec  
Column level check that ensures that the sum in a monitored column changes in a rate within a two-tailed percentile during last 7 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
|[warning](#changepercentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ChangePercentileMovingWithin7DaysRuleParametersSpec](#changepercentilemovingwithin7daysruleparametersspec)| | | |
|[error](#changepercentilemovingwithin7daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[ChangePercentileMovingWithin7DaysRuleParametersSpec](#changepercentilemovingwithin7daysruleparametersspec)| | | |
|[fatal](#changepercentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ChangePercentileMovingWithin7DaysRuleParametersSpec](#changepercentilemovingwithin7daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAnomalySumChange30DaysCheckSpec  
Column level check that ensures that the sum in a monitored column changes in a rate within a two-tailed percentile during last 30 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
|[warning](#changepercentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ChangePercentileMovingWithin30DaysRuleParametersSpec](#changepercentilemovingwithin30daysruleparametersspec)| | | |
|[error](#changepercentilemovingwithin30daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[ChangePercentileMovingWithin30DaysRuleParametersSpec](#changepercentilemovingwithin30daysruleparametersspec)| | | |
|[fatal](#changepercentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ChangePercentileMovingWithin30DaysRuleParametersSpec](#changepercentilemovingwithin30daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAnomalySumChange60DaysCheckSpec  
Column level check that ensures that the sum in a monitored column changes in a rate within a two-tailed percentile during last 60 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
|[warning](#changepercentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ChangePercentileMovingWithin60DaysRuleParametersSpec](#changepercentilemovingwithin60daysruleparametersspec)| | | |
|[error](#changepercentilemovingwithin60daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[ChangePercentileMovingWithin60DaysRuleParametersSpec](#changepercentilemovingwithin60daysruleparametersspec)| | | |
|[fatal](#changepercentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ChangePercentileMovingWithin60DaysRuleParametersSpec](#changepercentilemovingwithin60daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnRecurringChecksRootSpec  
Container of column level recurring, divided by the time window (daily, monthly, etc.)  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](#columndailyrecurringcheckcategoriesspec)|Configuration of daily recurring evaluated at a column level.|[ColumnDailyRecurringCheckCategoriesSpec](#columndailyrecurringcheckcategoriesspec)| | | |
|[monthly](#columnmonthlyrecurringcheckcategoriesspec)|Configuration of monthly recurring evaluated at a column level.|[ColumnMonthlyRecurringCheckCategoriesSpec](#columnmonthlyrecurringcheckcategoriesspec)| | | |









___  

## ColumnDailyRecurringCheckCategoriesSpec  
Container of column level daily recurring checks. Contains categories of daily recurring checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](#columnnullsdailyrecurringchecksspec)|Daily recurring of nulls in the column|[ColumnNullsDailyRecurringChecksSpec](#columnnullsdailyrecurringchecksspec)| | | |
|[numeric](#columnnumericdailyrecurringchecksspec)|Daily recurring of numeric in the column|[ColumnNumericDailyRecurringChecksSpec](#columnnumericdailyrecurringchecksspec)| | | |
|[strings](#columnstringsdailyrecurringchecksspec)|Daily recurring of strings in the column|[ColumnStringsDailyRecurringChecksSpec](#columnstringsdailyrecurringchecksspec)| | | |
|[uniqueness](#columnuniquenessdailyrecurringchecksspec)|Daily recurring of uniqueness in the column|[ColumnUniquenessDailyRecurringChecksSpec](#columnuniquenessdailyrecurringchecksspec)| | | |
|[datetime](#columndatetimedailyrecurringchecksspec)|Daily recurring of datetime in the column|[ColumnDatetimeDailyRecurringChecksSpec](#columndatetimedailyrecurringchecksspec)| | | |
|[pii](#columnpiidailyrecurringchecksspec)|Daily recurring of Personal Identifiable Information (PII) in the column|[ColumnPiiDailyRecurringChecksSpec](#columnpiidailyrecurringchecksspec)| | | |
|[sql](#columnsqldailyrecurringchecksspec)|Daily recurring of custom SQL checks in the column|[ColumnSqlDailyRecurringChecksSpec](#columnsqldailyrecurringchecksspec)| | | |
|[bool](#columnbooldailyrecurringchecksspec)|Daily recurring of booleans in the column|[ColumnBoolDailyRecurringChecksSpec](#columnbooldailyrecurringchecksspec)| | | |
|[integrity](#columnintegritydailyrecurringchecksspec)|Daily recurring of integrity in the column|[ColumnIntegrityDailyRecurringChecksSpec](#columnintegritydailyrecurringchecksspec)| | | |
|[accuracy](#columnaccuracydailyrecurringchecksspec)|Daily recurring of accuracy in the column|[ColumnAccuracyDailyRecurringChecksSpec](#columnaccuracydailyrecurringchecksspec)| | | |
|[consistency](#columnconsistencydailyrecurringchecksspec)|Daily recurring of consistency in the column|[ColumnConsistencyDailyRecurringChecksSpec](#columnconsistencydailyrecurringchecksspec)| | | |
|[anomaly](#columnanomalydailyrecurringchecksspec)|Daily recurring of anomaly in the column|[ColumnAnomalyDailyRecurringChecksSpec](#columnanomalydailyrecurringchecksspec)| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## ColumnNullsDailyRecurringChecksSpec  
Container of nulls data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_nulls_count](#columnnullscountcheckspec)|Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnNullsCountCheckSpec](#columnnullscountcheckspec)| | | |
|[daily_nulls_percent](#columnnullspercentcheckspec)|Verifies that the percentage of nulls in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnNullsPercentCheckSpec](#columnnullspercentcheckspec)| | | |
|[daily_not_nulls_count](#columnnotnullscountcheckspec)|Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnNotNullsCountCheckSpec](#columnnotnullscountcheckspec)| | | |
|[daily_not_nulls_percent](#columnnotnullspercentcheckspec)|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnNotNullsPercentCheckSpec](#columnnotnullspercentcheckspec)| | | |









___  

## ColumnNumericDailyRecurringChecksSpec  
Container of built-in preconfigured data quality recurring on a column level that are checking numeric values at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_negative_count](#columnnegativecountcheckspec)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnNegativeCountCheckSpec](#columnnegativecountcheckspec)| | | |
|[daily_negative_percent](#columnnegativepercentcheckspec)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnNegativePercentCheckSpec](#columnnegativepercentcheckspec)| | | |
|[daily_non_negative_count](#columnnonnegativecountcheckspec)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnNonNegativeCountCheckSpec](#columnnonnegativecountcheckspec)| | | |
|[daily_non_negative_percent](#columnnonnegativepercentcheckspec)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnNonNegativePercentCheckSpec](#columnnonnegativepercentcheckspec)| | | |
|[daily_numbers_found_count](#columnnumbersfoundcountcheckspec)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnNumbersFoundCountCheckSpec](#columnnumbersfoundcountcheckspec)| | | |
|[daily_has_valid_numbers_percent](#columnhasvalidnumberspercentcheckspec)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnHasValidNumbersPercentCheckSpec](#columnhasvalidnumberspercentcheckspec)| | | |
|[daily_values_in_range_numeric_percent](#columnvaluesinrangenumericpercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnValuesInRangeNumericPercentCheckSpec](#columnvaluesinrangenumericpercentcheckspec)| | | |
|[daily_values_in_range_integers_percent](#columnvaluesinrangeintegerspercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnValuesInRangeIntegersPercentCheckSpec](#columnvaluesinrangeintegerspercentcheckspec)| | | |
|[daily_value_below_min_value_count](#columnvaluebelowminvaluecountcheckspec)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnValueBelowMinValueCountCheckSpec](#columnvaluebelowminvaluecountcheckspec)| | | |
|[daily_value_below_min_value_percent](#columnvaluebelowminvaluepercentcheckspec)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnValueBelowMinValuePercentCheckSpec](#columnvaluebelowminvaluepercentcheckspec)| | | |
|[daily_value_above_max_value_count](#columnvalueabovemaxvaluecountcheckspec)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnValueAboveMaxValueCountCheckSpec](#columnvalueabovemaxvaluecountcheckspec)| | | |
|[daily_value_above_max_value_percent](#columnvalueabovemaxvaluepercentcheckspec)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnValueAboveMaxValuePercentCheckSpec](#columnvalueabovemaxvaluepercentcheckspec)| | | |
|[daily_max_in_range](#columnmaxinrangecheckspec)|Verifies that the maximal value in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnMaxInRangeCheckSpec](#columnmaxinrangecheckspec)| | | |
|[daily_min_in_range](#columnmininrangecheckspec)|Verifies that the minimal value in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnMinInRangeCheckSpec](#columnmininrangecheckspec)| | | |
|[daily_mean_in_range](#columnmeaninrangecheckspec)|Verifies that the average (mean) of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnMeanInRangeCheckSpec](#columnmeaninrangecheckspec)| | | |
|[daily_percentile_in_range](#columnpercentileinrangecheckspec)|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnPercentileInRangeCheckSpec](#columnpercentileinrangecheckspec)| | | |
|[daily_median_in_range](#columnmedianinrangecheckspec)|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMedianInRangeCheckSpec](#columnmedianinrangecheckspec)| | | |
|[daily_percentile_10_in_range](#columnpercentile10inrangecheckspec)|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnPercentile10InRangeCheckSpec](#columnpercentile10inrangecheckspec)| | | |
|[daily_percentile_25_in_range](#columnpercentile25inrangecheckspec)|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnPercentile25InRangeCheckSpec](#columnpercentile25inrangecheckspec)| | | |
|[daily_percentile_75_in_range](#columnpercentile75inrangecheckspec)|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnPercentile75InRangeCheckSpec](#columnpercentile75inrangecheckspec)| | | |
|[daily_percentile_90_in_range](#columnpercentile90inrangecheckspec)|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnPercentile90InRangeCheckSpec](#columnpercentile90inrangecheckspec)| | | |
|[daily_sample_stddev_in_range](#columnsamplestddevinrangecheckspec)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnSampleStddevInRangeCheckSpec](#columnsamplestddevinrangecheckspec)| | | |
|[daily_population_stddev_in_range](#columnpopulationstddevinrangecheckspec)|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnPopulationStddevInRangeCheckSpec](#columnpopulationstddevinrangecheckspec)| | | |
|[daily_sample_variance_in_range](#columnsamplevarianceinrangecheckspec)|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnSampleVarianceInRangeCheckSpec](#columnsamplevarianceinrangecheckspec)| | | |
|[daily_population_variance_in_range](#columnpopulationvarianceinrangecheckspec)|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnPopulationVarianceInRangeCheckSpec](#columnpopulationvarianceinrangecheckspec)| | | |
|[daily_sum_in_range](#columnsuminrangecheckspec)|Verifies that the sum of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnSumInRangeCheckSpec](#columnsuminrangecheckspec)| | | |
|[daily_invalid_latitude_count](#columninvalidlatitudecountcheckspec)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnInvalidLatitudeCountCheckSpec](#columninvalidlatitudecountcheckspec)| | | |
|[daily_valid_latitude_percent](#columnvalidlatitudepercentcheckspec)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnValidLatitudePercentCheckSpec](#columnvalidlatitudepercentcheckspec)| | | |
|[daily_invalid_longitude_count](#columninvalidlongitudecountcheckspec)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnInvalidLongitudeCountCheckSpec](#columninvalidlongitudecountcheckspec)| | | |
|[daily_valid_longitude_percent](#columnvalidlongitudepercentcheckspec)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnValidLongitudePercentCheckSpec](#columnvalidlongitudepercentcheckspec)| | | |









___  

## ColumnStringsDailyRecurringChecksSpec  
Container of strings data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_string_max_length](#columnstringmaxlengthcheckspec)|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringMaxLengthCheckSpec](#columnstringmaxlengthcheckspec)| | | |
|[daily_string_min_length](#columnstringminlengthcheckspec)|Verifies that the length of string in a column does not fall below the minimum accepted length. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringMinLengthCheckSpec](#columnstringminlengthcheckspec)| | | |
|[daily_string_mean_length](#columnstringmeanlengthcheckspec)|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringMeanLengthCheckSpec](#columnstringmeanlengthcheckspec)| | | |
|[daily_string_length_below_min_length_count](#columnstringlengthbelowminlengthcountcheckspec)|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringLengthBelowMinLengthCountCheckSpec](#columnstringlengthbelowminlengthcountcheckspec)| | | |
|[daily_string_length_below_min_length_percent](#columnstringlengthbelowminlengthpercentcheckspec)|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](#columnstringlengthbelowminlengthpercentcheckspec)| | | |
|[daily_string_length_above_max_length_count](#columnstringlengthabovemaxlengthcountcheckspec)|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](#columnstringlengthabovemaxlengthcountcheckspec)| | | |
|[daily_string_length_above_max_length_percent](#columnstringlengthabovemaxlengthpercentcheckspec)|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](#columnstringlengthabovemaxlengthpercentcheckspec)| | | |
|[daily_string_length_in_range_percent](#columnstringlengthinrangepercentcheckspec)|The check counts the percentage of those strings with length in the range provided by the user in the column. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringLengthInRangePercentCheckSpec](#columnstringlengthinrangepercentcheckspec)| | | |
|[daily_string_empty_count](#columnstringemptycountcheckspec)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringEmptyCountCheckSpec](#columnstringemptycountcheckspec)| | | |
|[daily_string_empty_percent](#columnstringemptypercentcheckspec)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringEmptyPercentCheckSpec](#columnstringemptypercentcheckspec)| | | |
|[daily_string_whitespace_count](#columnstringwhitespacecountcheckspec)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringWhitespaceCountCheckSpec](#columnstringwhitespacecountcheckspec)| | | |
|[daily_string_whitespace_percent](#columnstringwhitespacepercentcheckspec)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringWhitespacePercentCheckSpec](#columnstringwhitespacepercentcheckspec)| | | |
|[daily_string_surrounded_by_whitespace_count](#columnstringsurroundedbywhitespacecountcheckspec)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](#columnstringsurroundedbywhitespacecountcheckspec)| | | |
|[daily_string_surrounded_by_whitespace_percent](#columnstringsurroundedbywhitespacepercentcheckspec)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](#columnstringsurroundedbywhitespacepercentcheckspec)| | | |
|[daily_string_null_placeholder_count](#columnstringnullplaceholdercountcheckspec)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringNullPlaceholderCountCheckSpec](#columnstringnullplaceholdercountcheckspec)| | | |
|[daily_string_null_placeholder_percent](#columnstringnullplaceholderpercentcheckspec)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringNullPlaceholderPercentCheckSpec](#columnstringnullplaceholderpercentcheckspec)| | | |
|[daily_string_boolean_placeholder_percent](#columnstringbooleanplaceholderpercentcheckspec)|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringBooleanPlaceholderPercentCheckSpec](#columnstringbooleanplaceholderpercentcheckspec)| | | |
|[daily_string_parsable_to_integer_percent](#columnstringparsabletointegerpercentcheckspec)|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringParsableToIntegerPercentCheckSpec](#columnstringparsabletointegerpercentcheckspec)| | | |
|[daily_string_parsable_to_float_percent](#columnstringparsabletofloatpercentcheckspec)|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringParsableToFloatPercentCheckSpec](#columnstringparsabletofloatpercentcheckspec)| | | |
|[daily_string_in_set_count](#columnstringinsetcountcheckspec)|Verifies that the number of strings from set in a column does not fall below the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringInSetCountCheckSpec](#columnstringinsetcountcheckspec)| | | |
|[daily_string_in_set_percent](#columnstringinsetpercentcheckspec)|Verifies that the percentage of strings from a set in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringInSetPercentCheckSpec](#columnstringinsetpercentcheckspec)| | | |
|[daily_string_valid_dates_percent](#columnstringvaliddatespercentcheckspec)|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringValidDatesPercentCheckSpec](#columnstringvaliddatespercentcheckspec)| | | |
|[daily_string_valid_country_code_percent](#columnstringvalidcountrycodepercentcheckspec)|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringValidCountryCodePercentCheckSpec](#columnstringvalidcountrycodepercentcheckspec)| | | |
|[daily_string_valid_currency_code_percent](#columnstringvalidcurrencycodepercentcheckspec)|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringValidCurrencyCodePercentCheckSpec](#columnstringvalidcurrencycodepercentcheckspec)| | | |
|[daily_string_invalid_email_count](#columnstringinvalidemailcountcheckspec)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringInvalidEmailCountCheckSpec](#columnstringinvalidemailcountcheckspec)| | | |
|[daily_string_invalid_uuid_count](#columnstringinvaliduuidcountcheckspec)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringInvalidUuidCountCheckSpec](#columnstringinvaliduuidcountcheckspec)| | | |
|[daily_string_valid_uuid_percent](#columnstringvaliduuidpercentcheckspec)|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringValidUuidPercentCheckSpec](#columnstringvaliduuidpercentcheckspec)| | | |
|[daily_string_invalid_ip4_address_count](#columnstringinvalidip4addresscountcheckspec)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringInvalidIp4AddressCountCheckSpec](#columnstringinvalidip4addresscountcheckspec)| | | |
|[daily_string_invalid_ip6_address_count](#columnstringinvalidip6addresscountcheckspec)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringInvalidIp6AddressCountCheckSpec](#columnstringinvalidip6addresscountcheckspec)| | | |
|[daily_string_not_match_regex_count](#columnstringnotmatchregexcountcheckspec)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringNotMatchRegexCountCheckSpec](#columnstringnotmatchregexcountcheckspec)| | | |
|[daily_string_match_regex_percent](#columnstringmatchregexpercentcheckspec)|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringMatchRegexPercentCheckSpec](#columnstringmatchregexpercentcheckspec)| | | |
|[daily_string_not_match_date_regex_count](#columnstringnotmatchdateregexcountcheckspec)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringNotMatchDateRegexCountCheckSpec](#columnstringnotmatchdateregexcountcheckspec)| | | |
|[daily_string_match_date_regex_percent](#columnstringmatchdateregexpercentcheckspec)|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringMatchDateRegexPercentCheckSpec](#columnstringmatchdateregexpercentcheckspec)| | | |
|[daily_string_match_name_regex_percent](#columnstringmatchnameregexpercentcheckspec)|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringMatchNameRegexPercentCheckSpec](#columnstringmatchnameregexpercentcheckspec)| | | |
|[daily_string_most_popular_values](#columnstringmostpopularvaluescheckspec)|Verifies that the number of top values from a set in a column does not fall below the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringMostPopularValuesCheckSpec](#columnstringmostpopularvaluescheckspec)| | | |
|[daily_string_datatype_detected](#columnstringdatatypedetectedcheckspec)|Detects the data type of text values stored in the column. The sensor returns the code of the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringDatatypeDetectedCheckSpec](#columnstringdatatypedetectedcheckspec)| | | |









___  

## ColumnUniquenessDailyRecurringChecksSpec  
Container of uniqueness data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_unique_count](#columnuniquecountcheckspec)|Verifies that the number of unique values in a column does not fall below the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnUniqueCountCheckSpec](#columnuniquecountcheckspec)| | | |
|[daily_unique_percent](#columnuniquepercentcheckspec)|Verifies that the percentage of unique values in a column does not fall below the minimum accepted percent. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnUniquePercentCheckSpec](#columnuniquepercentcheckspec)| | | |
|[daily_duplicate_count](#columnduplicatecountcheckspec)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnDuplicateCountCheckSpec](#columnduplicatecountcheckspec)| | | |
|[daily_duplicate_percent](#columnduplicatepercentcheckspec)|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnDuplicatePercentCheckSpec](#columnduplicatepercentcheckspec)| | | |









___  

## ColumnDatetimeDailyRecurringChecksSpec  
Container of date-time data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_date_values_in_future_percent](#columndatevaluesinfuturepercentcheckspec)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnDateValuesInFuturePercentCheckSpec](#columndatevaluesinfuturepercentcheckspec)| | | |
|[daily_datetime_value_in_range_date_percent](#columndatetimevalueinrangedatepercentcheckspec)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](#columndatetimevalueinrangedatepercentcheckspec)| | | |









___  

## ColumnPiiDailyRecurringChecksSpec  
Container of PII data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_valid_usa_phone_percent](#columnpiivalidusaphonepercentcheckspec)|Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnPiiValidUsaPhonePercentCheckSpec](#columnpiivalidusaphonepercentcheckspec)| | | |
|[daily_contains_usa_phone_percent](#columnpiicontainsusaphonepercentcheckspec)|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnPiiContainsUsaPhonePercentCheckSpec](#columnpiicontainsusaphonepercentcheckspec)| | | |
|[daily_valid_usa_zipcode_percent](#columnpiivalidusazipcodepercentcheckspec)|Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnPiiValidUsaZipcodePercentCheckSpec](#columnpiivalidusazipcodepercentcheckspec)| | | |
|[daily_contains_usa_zipcode_percent](#columnpiicontainsusazipcodepercentcheckspec)|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](#columnpiicontainsusazipcodepercentcheckspec)| | | |
|[daily_valid_email_percent](#columnpiivalidemailpercentcheckspec)|Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnPiiValidEmailPercentCheckSpec](#columnpiivalidemailpercentcheckspec)| | | |
|[daily_contains_email_percent](#columnpiicontainsemailpercentcheckspec)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnPiiContainsEmailPercentCheckSpec](#columnpiicontainsemailpercentcheckspec)| | | |
|[daily_valid_ip4_address_percent](#columnpiivalidip4addresspercentcheckspec)|Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnPiiValidIp4AddressPercentCheckSpec](#columnpiivalidip4addresspercentcheckspec)| | | |
|[daily_contains_ip4_percent](#columnpiicontainsip4percentcheckspec)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnPiiContainsIp4PercentCheckSpec](#columnpiicontainsip4percentcheckspec)| | | |
|[daily_valid_ip6_address_percent](#columnpiivalidip6addresspercentcheckspec)|Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnPiiValidIp6AddressPercentCheckSpec](#columnpiivalidip6addresspercentcheckspec)| | | |
|[daily_contains_ip6_percent](#columnpiicontainsip6percentcheckspec)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnPiiContainsIp6PercentCheckSpec](#columnpiicontainsip6percentcheckspec)| | | |









___  

## ColumnSqlDailyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_sql_condition_passed_percent_on_column](#columnsqlconditionpassedpercentcheckspec)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnSqlConditionPassedPercentCheckSpec](#columnsqlconditionpassedpercentcheckspec)| | | |
|[daily_sql_condition_failed_count_on_column](#columnsqlconditionfailedcountcheckspec)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnSqlConditionFailedCountCheckSpec](#columnsqlconditionfailedcountcheckspec)| | | |
|[daily_sql_aggregate_expr_column](#columnsqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnSqlAggregateExprCheckSpec](#columnsqlaggregateexprcheckspec)| | | |









___  

## ColumnBoolDailyRecurringChecksSpec  
Container of boolean data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_true_percent](#columntruepercentcheckspec)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnTruePercentCheckSpec](#columntruepercentcheckspec)| | | |
|[daily_false_percent](#columnfalsepercentcheckspec)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnFalsePercentCheckSpec](#columnfalsepercentcheckspec)| | | |









___  

## ColumnIntegrityDailyRecurringChecksSpec  
Container of integrity data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_foreign_key_not_match_count](#columnintegrityforeignkeynotmatchcountcheckspec)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](#columnintegrityforeignkeynotmatchcountcheckspec)| | | |
|[daily_foreign_key_match_percent](#columnintegrityforeignkeymatchpercentcheckspec)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](#columnintegrityforeignkeymatchpercentcheckspec)| | | |









___  

## ColumnAccuracyDailyRecurringChecksSpec  
Container of accuracy data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_total_sum_match_percent](#columnaccuracytotalsummatchpercentcheckspec)|Verifies that the percentage of difference in sum of a column in a table and sum of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnAccuracyTotalSumMatchPercentCheckSpec](#columnaccuracytotalsummatchpercentcheckspec)| | | |
|[daily_min_match_percent](#columnaccuracyminmatchpercentcheckspec)|Verifies that the percentage of difference in min of a column in a table and min of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnAccuracyMinMatchPercentCheckSpec](#columnaccuracyminmatchpercentcheckspec)| | | |
|[daily_max_match_percent](#columnaccuracymaxmatchpercentcheckspec)|Verifies that the percentage of difference in max of a column in a table and max of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnAccuracyMaxMatchPercentCheckSpec](#columnaccuracymaxmatchpercentcheckspec)| | | |
|[daily_average_match_percent](#columnaccuracyaveragematchpercentcheckspec)|Verifies that the percentage of difference in average of a column in a table and average of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnAccuracyAverageMatchPercentCheckSpec](#columnaccuracyaveragematchpercentcheckspec)| | | |
|[daily_not_null_count_match_percent](#columnaccuracynotnullcountmatchpercentcheckspec)|Verifies that the percentage of difference in row count of a column in a table and row count of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnAccuracyNotNullCountMatchPercentCheckSpec](#columnaccuracynotnullcountmatchpercentcheckspec)| | | |









___  

## ColumnConsistencyDailyRecurringChecksSpec  
Container of consistency data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_date_match_format_percent](#columnconsistencydatematchformatpercentcheckspec)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily recurring.|[ColumnConsistencyDateMatchFormatPercentCheckSpec](#columnconsistencydatematchformatpercentcheckspec)| | | |
|[daily_string_datatype_changed](#columnstringdatatypechangedcheckspec)|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringDatatypeChangedCheckSpec](#columnstringdatatypechangedcheckspec)| | | |









___  

## ColumnAnomalyDailyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_mean_anomaly_7_days](#columnanomalymeanchange7dayscheckspec)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 7 days.|[ColumnAnomalyMeanChange7DaysCheckSpec](#columnanomalymeanchange7dayscheckspec)| | | |
|[daily_mean_anomaly_30_days](#columnanomalymeanchange30dayscheckspec)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyMeanChange30DaysCheckSpec](#columnanomalymeanchange30dayscheckspec)| | | |
|[daily_mean_anomaly_60_days](#columnanomalymeanchange60dayscheckspec)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 60 days.|[ColumnAnomalyMeanChange60DaysCheckSpec](#columnanomalymeanchange60dayscheckspec)| | | |
|[daily_median_anomaly_7_days](#columnanomalymedianchange7dayscheckspec)|Verifies that the median in a column changes in a rate within a percentile boundary during last 7 days.|[ColumnAnomalyMedianChange7DaysCheckSpec](#columnanomalymedianchange7dayscheckspec)| | | |
|[daily_median_anomaly_30_days](#columnanomalymedianchange30dayscheckspec)|Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyMedianChange30DaysCheckSpec](#columnanomalymedianchange30dayscheckspec)| | | |
|[daily_median_anomaly_60_days](#columnanomalymedianchange60dayscheckspec)|Verifies that the median in a column changes in a rate within a percentile boundary during last 60 days.|[ColumnAnomalyMedianChange60DaysCheckSpec](#columnanomalymedianchange60dayscheckspec)| | | |
|[daily_sum_anomaly_7_days](#columnanomalysumchange7dayscheckspec)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 7 days.|[ColumnAnomalySumChange7DaysCheckSpec](#columnanomalysumchange7dayscheckspec)| | | |
|[daily_sum_anomaly_30_days](#columnanomalysumchange30dayscheckspec)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalySumChange30DaysCheckSpec](#columnanomalysumchange30dayscheckspec)| | | |
|[daily_sum_anomaly_60_days](#columnanomalysumchange60dayscheckspec)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 60 days.|[ColumnAnomalySumChange60DaysCheckSpec](#columnanomalysumchange60dayscheckspec)| | | |









___  

## ColumnMonthlyRecurringCheckCategoriesSpec  
Container of column level monthly recurring checks. Contains categories of monthly recurring checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](#columnnullsmonthlyrecurringchecksspec)|Monthly recurring of nulls in the column|[ColumnNullsMonthlyRecurringChecksSpec](#columnnullsmonthlyrecurringchecksspec)| | | |
|[numeric](#columnnumericmonthlyrecurringchecksspec)|Monthly recurring of numeric in the column|[ColumnNumericMonthlyRecurringChecksSpec](#columnnumericmonthlyrecurringchecksspec)| | | |
|[strings](#columnstringsmonthlyrecurringchecksspec)|Monthly recurring of strings in the column|[ColumnStringsMonthlyRecurringChecksSpec](#columnstringsmonthlyrecurringchecksspec)| | | |
|[uniqueness](#columnuniquenessmonthlyrecurringchecksspec)|Monthly recurring of uniqueness in the column|[ColumnUniquenessMonthlyRecurringChecksSpec](#columnuniquenessmonthlyrecurringchecksspec)| | | |
|[datetime](#columndatetimemonthlyrecurringchecksspec)|Monthly recurring of datetime in the column|[ColumnDatetimeMonthlyRecurringChecksSpec](#columndatetimemonthlyrecurringchecksspec)| | | |
|[pii](#columnpiimonthlyrecurringchecksspec)|Monthly recurring of Personal Identifiable Information (PII) in the column|[ColumnPiiMonthlyRecurringChecksSpec](#columnpiimonthlyrecurringchecksspec)| | | |
|[sql](#columnsqlmonthlyrecurringchecksspec)|Monthly recurring of custom SQL checks in the column|[ColumnSqlMonthlyRecurringChecksSpec](#columnsqlmonthlyrecurringchecksspec)| | | |
|[bool](#columnboolmonthlyrecurringchecksspec)|Monthly recurring of booleans in the column|[ColumnBoolMonthlyRecurringChecksSpec](#columnboolmonthlyrecurringchecksspec)| | | |
|[integrity](#columnintegritymonthlyrecurringchecksspec)|Monthly recurring of integrity in the column|[ColumnIntegrityMonthlyRecurringChecksSpec](#columnintegritymonthlyrecurringchecksspec)| | | |
|[accuracy](#columnaccuracymonthlyrecurringchecksspec)|Monthly recurring of accuracy in the column|[ColumnAccuracyMonthlyRecurringChecksSpec](#columnaccuracymonthlyrecurringchecksspec)| | | |
|[consistency](#columnconsistencymonthlyrecurringchecksspec)|Monthly recurring of consistency in the column|[ColumnConsistencyMonthlyRecurringChecksSpec](#columnconsistencymonthlyrecurringchecksspec)| | | |
|[anomaly](#columnanomalymonthlyrecurringchecksspec)|Monthly recurring of anomaly in the column|[ColumnAnomalyMonthlyRecurringChecksSpec](#columnanomalymonthlyrecurringchecksspec)| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## ColumnNullsMonthlyRecurringChecksSpec  
Container of nulls data quality recurring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_nulls_count](#columnnullscountcheckspec)|Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNullsCountCheckSpec](#columnnullscountcheckspec)| | | |
|[monthly_nulls_percent](#columnnullspercentcheckspec)|Verifies that the percentage of null values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNullsPercentCheckSpec](#columnnullspercentcheckspec)| | | |
|[monthly_not_nulls_count](#columnnotnullscountcheckspec)|Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNotNullsCountCheckSpec](#columnnotnullscountcheckspec)| | | |
|[monthly_not_nulls_percent](#columnnotnullspercentcheckspec)|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNotNullsPercentCheckSpec](#columnnotnullspercentcheckspec)| | | |









___  

## ColumnNumericMonthlyRecurringChecksSpec  
Container of built-in preconfigured data quality recurring on a column level that are checking numeric values at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_negative_count](#columnnegativecountcheckspec)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNegativeCountCheckSpec](#columnnegativecountcheckspec)| | | |
|[monthly_negative_percent](#columnnegativepercentcheckspec)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNegativePercentCheckSpec](#columnnegativepercentcheckspec)| | | |
|[monthly_non_negative_count](#columnnonnegativecountcheckspec)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNonNegativeCountCheckSpec](#columnnonnegativecountcheckspec)| | | |
|[monthly_non_negative_percent](#columnnonnegativepercentcheckspec)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNonNegativePercentCheckSpec](#columnnonnegativepercentcheckspec)| | | |
|[monthly_numbers_found_count](#columnnumbersfoundcountcheckspec)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNumbersFoundCountCheckSpec](#columnnumbersfoundcountcheckspec)| | | |
|[monthly_has_valid_numbers_percent](#columnhasvalidnumberspercentcheckspec)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnHasValidNumbersPercentCheckSpec](#columnhasvalidnumberspercentcheckspec)| | | |
|[monthly_values_in_range_numeric_percent](#columnvaluesinrangenumericpercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValuesInRangeNumericPercentCheckSpec](#columnvaluesinrangenumericpercentcheckspec)| | | |
|[monthly_values_in_range_integers_percent](#columnvaluesinrangeintegerspercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValuesInRangeIntegersPercentCheckSpec](#columnvaluesinrangeintegerspercentcheckspec)| | | |
|[monthly_value_below_min_value_count](#columnvaluebelowminvaluecountcheckspec)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValueBelowMinValueCountCheckSpec](#columnvaluebelowminvaluecountcheckspec)| | | |
|[monthly_value_below_min_value_percent](#columnvaluebelowminvaluepercentcheckspec)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValueBelowMinValuePercentCheckSpec](#columnvaluebelowminvaluepercentcheckspec)| | | |
|[monthly_value_above_max_value_count](#columnvalueabovemaxvaluecountcheckspec)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValueAboveMaxValueCountCheckSpec](#columnvalueabovemaxvaluecountcheckspec)| | | |
|[monthly_value_above_max_value_percent](#columnvalueabovemaxvaluepercentcheckspec)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValueAboveMaxValuePercentCheckSpec](#columnvalueabovemaxvaluepercentcheckspec)| | | |
|[monthly_max_in_range](#columnmaxinrangecheckspec)|Verifies that the maximal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMaxInRangeCheckSpec](#columnmaxinrangecheckspec)| | | |
|[monthly_min_in_range](#columnmininrangecheckspec)|Verifies that the minimal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMinInRangeCheckSpec](#columnmininrangecheckspec)| | | |
|[monthly_mean_in_range](#columnmeaninrangecheckspec)|Verifies that the average (mean) of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMeanInRangeCheckSpec](#columnmeaninrangecheckspec)| | | |
|[monthly_percentile_in_range](#columnpercentileinrangecheckspec)|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentileInRangeCheckSpec](#columnpercentileinrangecheckspec)| | | |
|[monthly_median_in_range](#columnmedianinrangecheckspec)|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMedianInRangeCheckSpec](#columnmedianinrangecheckspec)| | | |
|[monthly_percentile_10_in_range](#columnpercentile10inrangecheckspec)|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentile10InRangeCheckSpec](#columnpercentile10inrangecheckspec)| | | |
|[monthly_percentile_25_in_range](#columnpercentile25inrangecheckspec)|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentile25InRangeCheckSpec](#columnpercentile25inrangecheckspec)| | | |
|[monthly_percentile_75_in_range](#columnpercentile75inrangecheckspec)|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentile75InRangeCheckSpec](#columnpercentile75inrangecheckspec)| | | |
|[monthly_percentile_90_in_range](#columnpercentile90inrangecheckspec)|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentile90InRangeCheckSpec](#columnpercentile90inrangecheckspec)| | | |
|[monthly_sample_stddev_in_range](#columnsamplestddevinrangecheckspec)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSampleStddevInRangeCheckSpec](#columnsamplestddevinrangecheckspec)| | | |
|[monthly_population_stddev_in_range](#columnpopulationstddevinrangecheckspec)|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPopulationStddevInRangeCheckSpec](#columnpopulationstddevinrangecheckspec)| | | |
|[monthly_sample_variance_in_range](#columnsamplevarianceinrangecheckspec)|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSampleVarianceInRangeCheckSpec](#columnsamplevarianceinrangecheckspec)| | | |
|[monthly_population_variance_in_range](#columnpopulationvarianceinrangecheckspec)|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPopulationVarianceInRangeCheckSpec](#columnpopulationvarianceinrangecheckspec)| | | |
|[monthly_sum_in_range](#columnsuminrangecheckspec)|Verifies that the sum of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSumInRangeCheckSpec](#columnsuminrangecheckspec)| | | |
|[monthly_invalid_latitude_count](#columninvalidlatitudecountcheckspec)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnInvalidLatitudeCountCheckSpec](#columninvalidlatitudecountcheckspec)| | | |
|[monthly_valid_latitude_percent](#columnvalidlatitudepercentcheckspec)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValidLatitudePercentCheckSpec](#columnvalidlatitudepercentcheckspec)| | | |
|[monthly_invalid_longitude_count](#columninvalidlongitudecountcheckspec)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnInvalidLongitudeCountCheckSpec](#columninvalidlongitudecountcheckspec)| | | |
|[monthly_valid_longitude_percent](#columnvalidlongitudepercentcheckspec)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValidLongitudePercentCheckSpec](#columnvalidlongitudepercentcheckspec)| | | |









___  

## ColumnStringsMonthlyRecurringChecksSpec  
Container of strings data quality recurring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_string_max_length](#columnstringmaxlengthcheckspec)|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMaxLengthCheckSpec](#columnstringmaxlengthcheckspec)| | | |
|[monthly_string_min_length](#columnstringminlengthcheckspec)|Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMinLengthCheckSpec](#columnstringminlengthcheckspec)| | | |
|[monthly_string_mean_length](#columnstringmeanlengthcheckspec)|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMeanLengthCheckSpec](#columnstringmeanlengthcheckspec)| | | |
|[monthly_string_length_below_min_length_count](#columnstringlengthbelowminlengthcountcheckspec)|The check counts those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthBelowMinLengthCountCheckSpec](#columnstringlengthbelowminlengthcountcheckspec)| | | |
|[monthly_string_length_below_min_length_percent](#columnstringlengthbelowminlengthpercentcheckspec)|The check counts percentage of those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](#columnstringlengthbelowminlengthpercentcheckspec)| | | |
|[monthly_string_length_above_max_length_count](#columnstringlengthabovemaxlengthcountcheckspec)|The check counts those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](#columnstringlengthabovemaxlengthcountcheckspec)| | | |
|[monthly_string_length_above_max_length_percent](#columnstringlengthabovemaxlengthpercentcheckspec)|The check counts percentage of those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](#columnstringlengthabovemaxlengthpercentcheckspec)| | | |
|[monthly_string_length_in_range_percent](#columnstringlengthinrangepercentcheckspec)|The check counts percentage of those strings with length in the range provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthInRangePercentCheckSpec](#columnstringlengthinrangepercentcheckspec)| | | |
|[monthly_string_empty_count](#columnstringemptycountcheckspec)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringEmptyCountCheckSpec](#columnstringemptycountcheckspec)| | | |
|[monthly_string_empty_percent](#columnstringemptypercentcheckspec)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringEmptyPercentCheckSpec](#columnstringemptypercentcheckspec)| | | |
|[monthly_string_valid_dates_percent](#columnstringvaliddatespercentcheckspec)|Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValidDatesPercentCheckSpec](#columnstringvaliddatespercentcheckspec)| | | |
|[monthly_string_whitespace_count](#columnstringwhitespacecountcheckspec)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringWhitespaceCountCheckSpec](#columnstringwhitespacecountcheckspec)| | | |
|[monthly_string_whitespace_percent](#columnstringwhitespacepercentcheckspec)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringWhitespacePercentCheckSpec](#columnstringwhitespacepercentcheckspec)| | | |
|[monthly_string_surrounded_by_whitespace_count](#columnstringsurroundedbywhitespacecountcheckspec)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](#columnstringsurroundedbywhitespacecountcheckspec)| | | |
|[monthly_string_surrounded_by_whitespace_percent](#columnstringsurroundedbywhitespacepercentcheckspec)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](#columnstringsurroundedbywhitespacepercentcheckspec)| | | |
|[monthly_string_null_placeholder_count](#columnstringnullplaceholdercountcheckspec)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringNullPlaceholderCountCheckSpec](#columnstringnullplaceholdercountcheckspec)| | | |
|[monthly_string_null_placeholder_percent](#columnstringnullplaceholderpercentcheckspec)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringNullPlaceholderPercentCheckSpec](#columnstringnullplaceholderpercentcheckspec)| | | |
|[monthly_string_boolean_placeholder_percent](#columnstringbooleanplaceholderpercentcheckspec)|Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringBooleanPlaceholderPercentCheckSpec](#columnstringbooleanplaceholderpercentcheckspec)| | | |
|[monthly_string_parsable_to_integer_percent](#columnstringparsabletointegerpercentcheckspec)|Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringParsableToIntegerPercentCheckSpec](#columnstringparsabletointegerpercentcheckspec)| | | |
|[monthly_string_parsable_to_float_percent](#columnstringparsabletofloatpercentcheckspec)|Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringParsableToFloatPercentCheckSpec](#columnstringparsabletofloatpercentcheckspec)| | | |
|[monthly_string_in_set_count](#columnstringinsetcountcheckspec)|Verifies that the number of strings from set in a column does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInSetCountCheckSpec](#columnstringinsetcountcheckspec)| | | |
|[monthly_string_in_set_percent](#columnstringinsetpercentcheckspec)|Verifies that the percentage of strings from set in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInSetPercentCheckSpec](#columnstringinsetpercentcheckspec)| | | |
|[monthly_string_valid_country_code_percent](#columnstringvalidcountrycodepercentcheckspec)|Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValidCountryCodePercentCheckSpec](#columnstringvalidcountrycodepercentcheckspec)| | | |
|[monthly_string_valid_currency_code_percent](#columnstringvalidcurrencycodepercentcheckspec)|Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValidCurrencyCodePercentCheckSpec](#columnstringvalidcurrencycodepercentcheckspec)| | | |
|[monthly_string_invalid_email_count](#columnstringinvalidemailcountcheckspec)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInvalidEmailCountCheckSpec](#columnstringinvalidemailcountcheckspec)| | | |
|[monthly_string_invalid_uuid_count](#columnstringinvaliduuidcountcheckspec)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInvalidUuidCountCheckSpec](#columnstringinvaliduuidcountcheckspec)| | | |
|[monthly_string_valid_uuid_percent](#columnstringvaliduuidpercentcheckspec)|Verifies that the percentage of valid UUID in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValidUuidPercentCheckSpec](#columnstringvaliduuidpercentcheckspec)| | | |
|[monthly_string_invalid_ip4_address_count](#columnstringinvalidip4addresscountcheckspec)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInvalidIp4AddressCountCheckSpec](#columnstringinvalidip4addresscountcheckspec)| | | |
|[monthly_string_invalid_ip6_address_count](#columnstringinvalidip6addresscountcheckspec)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInvalidIp6AddressCountCheckSpec](#columnstringinvalidip6addresscountcheckspec)| | | |
|[monthly_string_not_match_regex_count](#columnstringnotmatchregexcountcheckspec)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringNotMatchRegexCountCheckSpec](#columnstringnotmatchregexcountcheckspec)| | | |
|[monthly_string_match_regex_percent](#columnstringmatchregexpercentcheckspec)|Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMatchRegexPercentCheckSpec](#columnstringmatchregexpercentcheckspec)| | | |
|[monthly_string_not_match_date_regex_count](#columnstringnotmatchdateregexcountcheckspec)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringNotMatchDateRegexCountCheckSpec](#columnstringnotmatchdateregexcountcheckspec)| | | |
|[monthly_string_match_date_regex_percent](#columnstringmatchdateregexpercentcheckspec)|Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMatchDateRegexPercentCheckSpec](#columnstringmatchdateregexpercentcheckspec)| | | |
|[monthly_string_match_name_regex_percent](#columnstringmatchnameregexpercentcheckspec)|Verifies that the percentage of strings matching the name regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMatchNameRegexPercentCheckSpec](#columnstringmatchnameregexpercentcheckspec)| | | |
|[monthly_string_most_popular_values](#columnstringmostpopularvaluescheckspec)|Verifies that the number of top values from a set in a column does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMostPopularValuesCheckSpec](#columnstringmostpopularvaluescheckspec)| | | |
|[monthly_string_datatype_detected](#columnstringdatatypedetectedcheckspec)|Detects the data type of text values stored in the column. The sensor returns the code of the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringDatatypeDetectedCheckSpec](#columnstringdatatypedetectedcheckspec)| | | |









___  

## ColumnUniquenessMonthlyRecurringChecksSpec  
Container of uniqueness data quality recurring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_unique_count](#columnuniquecountcheckspec)|Verifies that the number of unique values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnUniqueCountCheckSpec](#columnuniquecountcheckspec)| | | |
|[monthly_unique_percent](#columnuniquepercentcheckspec)|Verifies that the percentage of unique values in a column does not fall below the minimum accepted percent. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnUniquePercentCheckSpec](#columnuniquepercentcheckspec)| | | |
|[monthly_duplicate_count](#columnduplicatecountcheckspec)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDuplicateCountCheckSpec](#columnduplicatecountcheckspec)| | | |
|[monthly_duplicate_percent](#columnduplicatepercentcheckspec)|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDuplicatePercentCheckSpec](#columnduplicatepercentcheckspec)| | | |









___  

## ColumnDatetimeMonthlyRecurringChecksSpec  
Container of date-time data quality recurring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_date_values_in_future_percent](#columndatevaluesinfuturepercentcheckspec)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDateValuesInFuturePercentCheckSpec](#columndatevaluesinfuturepercentcheckspec)| | | |
|[monthly_datetime_value_in_range_date_percent](#columndatetimevalueinrangedatepercentcheckspec)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](#columndatetimevalueinrangedatepercentcheckspec)| | | |









___  

## ColumnPiiMonthlyRecurringChecksSpec  
Container of PII data quality recurring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_valid_usa_phone_percent](#columnpiivalidusaphonepercentcheckspec)|Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiValidUsaPhonePercentCheckSpec](#columnpiivalidusaphonepercentcheckspec)| | | |
|[monthly_contains_usa_phone_percent](#columnpiicontainsusaphonepercentcheckspec)|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsUsaPhonePercentCheckSpec](#columnpiicontainsusaphonepercentcheckspec)| | | |
|[monthly_valid_usa_zipcode_percent](#columnpiivalidusazipcodepercentcheckspec)|Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiValidUsaZipcodePercentCheckSpec](#columnpiivalidusazipcodepercentcheckspec)| | | |
|[monthly_contains_usa_zipcode_percent](#columnpiicontainsusazipcodepercentcheckspec)|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](#columnpiicontainsusazipcodepercentcheckspec)| | | |
|[monthly_valid_email_percent](#columnpiivalidemailpercentcheckspec)|Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiValidEmailPercentCheckSpec](#columnpiivalidemailpercentcheckspec)| | | |
|[monthly_contains_email_percent](#columnpiicontainsemailpercentcheckspec)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsEmailPercentCheckSpec](#columnpiicontainsemailpercentcheckspec)| | | |
|[monthly_valid_ip4_address_percent](#columnpiivalidip4addresspercentcheckspec)|Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiValidIp4AddressPercentCheckSpec](#columnpiivalidip4addresspercentcheckspec)| | | |
|[monthly_contains_ip4_percent](#columnpiicontainsip4percentcheckspec)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsIp4PercentCheckSpec](#columnpiicontainsip4percentcheckspec)| | | |
|[monthly_valid_ip6_address_percent](#columnpiivalidip6addresspercentcheckspec)|Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiValidIp6AddressPercentCheckSpec](#columnpiivalidip6addresspercentcheckspec)| | | |
|[monthly_contains_ip6_percent](#columnpiicontainsip6percentcheckspec)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsIp6PercentCheckSpec](#columnpiicontainsip6percentcheckspec)| | | |









___  

## ColumnSqlMonthlyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_sql_condition_passed_percent_on_column](#columnsqlconditionpassedpercentcheckspec)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSqlConditionPassedPercentCheckSpec](#columnsqlconditionpassedpercentcheckspec)| | | |
|[monthly_sql_condition_failed_count_on_column](#columnsqlconditionfailedcountcheckspec)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSqlConditionFailedCountCheckSpec](#columnsqlconditionfailedcountcheckspec)| | | |
|[monthly_sql_aggregate_expr_column](#columnsqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSqlAggregateExprCheckSpec](#columnsqlaggregateexprcheckspec)| | | |









___  

## ColumnBoolMonthlyRecurringChecksSpec  
Container of boolean recurring data quality checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_true_percent](#columntruepercentcheckspec)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnTruePercentCheckSpec](#columntruepercentcheckspec)| | | |
|[monthly_false_percent](#columnfalsepercentcheckspec)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnFalsePercentCheckSpec](#columnfalsepercentcheckspec)| | | |









___  

## ColumnIntegrityMonthlyRecurringChecksSpec  
Container of integrity data quality recurring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_foreign_key_not_match_count](#columnintegrityforeignkeynotmatchcountcheckspec)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](#columnintegrityforeignkeynotmatchcountcheckspec)| | | |
|[monthly_foreign_key_match_percent](#columnintegrityforeignkeymatchpercentcheckspec)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](#columnintegrityforeignkeymatchpercentcheckspec)| | | |









___  

## ColumnAccuracyMonthlyRecurringChecksSpec  
Container of accuracy data quality recurring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_total_sum_match_percent](#columnaccuracytotalsummatchpercentcheckspec)|Verifies that the percentage of difference in sum of a column in a table and sum of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyTotalSumMatchPercentCheckSpec](#columnaccuracytotalsummatchpercentcheckspec)| | | |
|[monthly_min_match_percent](#columnaccuracyminmatchpercentcheckspec)|Verifies that the percentage of difference in min of a column in a table and min of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyMinMatchPercentCheckSpec](#columnaccuracyminmatchpercentcheckspec)| | | |
|[monthly_max_match_percent](#columnaccuracymaxmatchpercentcheckspec)|Verifies that the percentage of difference in max of a column in a table and max of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyMaxMatchPercentCheckSpec](#columnaccuracymaxmatchpercentcheckspec)| | | |
|[monthly_average_match_percent](#columnaccuracyaveragematchpercentcheckspec)|Verifies that the percentage of difference in average of a column in a table and average of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyAverageMatchPercentCheckSpec](#columnaccuracyaveragematchpercentcheckspec)| | | |
|[monthly_not_null_count_match_percent](#columnaccuracynotnullcountmatchpercentcheckspec)|Verifies that the percentage of difference in row count of a column in a table and row count of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyNotNullCountMatchPercentCheckSpec](#columnaccuracynotnullcountmatchpercentcheckspec)| | | |









___  

## ColumnConsistencyMonthlyRecurringChecksSpec  
Container of consistency data quality recurring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_date_match_format_percent](#columnconsistencydatematchformatpercentcheckspec)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly recurring.|[ColumnConsistencyDateMatchFormatPercentCheckSpec](#columnconsistencydatematchformatpercentcheckspec)| | | |
|[monthly_string_datatype_changed](#columnstringdatatypechangedcheckspec)|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent row count for each day when the data quality check was evaluated.|[ColumnStringDatatypeChangedCheckSpec](#columnstringdatatypechangedcheckspec)| | | |









___  

## ColumnAnomalyMonthlyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|









___  

## ColumnPartitionedChecksRootSpec  
Container of column level partitioned checks, divided by the time window (daily, monthly, etc.)  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](#columndailypartitionedcheckcategoriesspec)|Configuration of day partitioned data quality checks evaluated at a column level.|[ColumnDailyPartitionedCheckCategoriesSpec](#columndailypartitionedcheckcategoriesspec)| | | |
|[monthly](#columnmonthlypartitionedcheckcategoriesspec)|Configuration of monthly partitioned data quality checks evaluated at a column level.|[ColumnMonthlyPartitionedCheckCategoriesSpec](#columnmonthlypartitionedcheckcategoriesspec)| | | |









___  

## ColumnDailyPartitionedCheckCategoriesSpec  
Container of data quality partitioned checks on a column level that are checking numeric values at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](#columnnullsdailypartitionedchecksspec)|Daily partitioned checks of nulls in the column|[ColumnNullsDailyPartitionedChecksSpec](#columnnullsdailypartitionedchecksspec)| | | |
|[numeric](#columnnumericdailypartitionedchecksspec)|Daily partitioned checks of numeric in the column|[ColumnNumericDailyPartitionedChecksSpec](#columnnumericdailypartitionedchecksspec)| | | |
|[strings](#columnstringsdailypartitionedchecksspec)|Daily partitioned checks of strings in the column|[ColumnStringsDailyPartitionedChecksSpec](#columnstringsdailypartitionedchecksspec)| | | |
|[uniqueness](#columnuniquenessdailypartitionedchecksspec)|Daily partitioned checks of uniqueness in the column|[ColumnUniquenessDailyPartitionedChecksSpec](#columnuniquenessdailypartitionedchecksspec)| | | |
|[datetime](#columndatetimedailypartitionedchecksspec)|Daily partitioned checks of datetime in the column|[ColumnDatetimeDailyPartitionedChecksSpec](#columndatetimedailypartitionedchecksspec)| | | |
|[pii](#columnpiidailypartitionedchecksspec)|Daily partitioned checks of Personal Identifiable Information (PII) in the column|[ColumnPiiDailyPartitionedChecksSpec](#columnpiidailypartitionedchecksspec)| | | |
|[sql](#columnsqldailypartitionedchecksspec)|Daily partitioned checks using custom SQL expressions evaluated on the column|[ColumnSqlDailyPartitionedChecksSpec](#columnsqldailypartitionedchecksspec)| | | |
|[bool](#columnbooldailypartitionedchecksspec)|Daily partitioned checks for booleans in the column|[ColumnBoolDailyPartitionedChecksSpec](#columnbooldailypartitionedchecksspec)| | | |
|[integrity](#columnintegritydailypartitionedchecksspec)|Daily partitioned checks for integrity in the column|[ColumnIntegrityDailyPartitionedChecksSpec](#columnintegritydailypartitionedchecksspec)| | | |
|[accuracy](#columnaccuracydailypartitionedchecksspec)|Daily partitioned checks for accuracy in the column|[ColumnAccuracyDailyPartitionedChecksSpec](#columnaccuracydailypartitionedchecksspec)| | | |
|[consistency](#columnconsistencydailypartitionedchecksspec)|Daily partitioned checks for consistency in the column|[ColumnConsistencyDailyPartitionedChecksSpec](#columnconsistencydailypartitionedchecksspec)| | | |
|[anomaly](#columnanomalydailypartitionedchecksspec)|Daily partitioned checks for anomaly in the column|[ColumnAnomalyDailyPartitionedChecksSpec](#columnanomalydailypartitionedchecksspec)| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## ColumnNullsDailyPartitionedChecksSpec  
Container of nulls data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_nulls_count](#columnnullscountcheckspec)|Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNullsCountCheckSpec](#columnnullscountcheckspec)| | | |
|[daily_partition_nulls_percent](#columnnullspercentcheckspec)|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNullsPercentCheckSpec](#columnnullspercentcheckspec)| | | |
|[daily_partition_not_nulls_count](#columnnotnullscountcheckspec)|Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNotNullsCountCheckSpec](#columnnotnullscountcheckspec)| | | |
|[daily_partition_not_nulls_percent](#columnnotnullspercentcheckspec)|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNotNullsPercentCheckSpec](#columnnotnullspercentcheckspec)| | | |









___  

## ColumnNumericDailyPartitionedChecksSpec  
Container of numeric data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_negative_count](#columnnegativecountcheckspec)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNegativeCountCheckSpec](#columnnegativecountcheckspec)| | | |
|[daily_partition_negative_percent](#columnnegativepercentcheckspec)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNegativePercentCheckSpec](#columnnegativepercentcheckspec)| | | |
|[daily_partition_non_negative_count](#columnnonnegativecountcheckspec)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNonNegativeCountCheckSpec](#columnnonnegativecountcheckspec)| | | |
|[daily_partition_non_negative_percent](#columnnonnegativepercentcheckspec)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNonNegativePercentCheckSpec](#columnnonnegativepercentcheckspec)| | | |
|[daily_partition_numbers_found_count](#columnnumbersfoundcountcheckspec)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNumbersFoundCountCheckSpec](#columnnumbersfoundcountcheckspec)| | | |
|[daily_partition_has_valid_numbers_percent](#columnhasvalidnumberspercentcheckspec)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnHasValidNumbersPercentCheckSpec](#columnhasvalidnumberspercentcheckspec)| | | |
|[daily_partition_values_in_range_numeric_percent](#columnvaluesinrangenumericpercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValuesInRangeNumericPercentCheckSpec](#columnvaluesinrangenumericpercentcheckspec)| | | |
|[daily_partition_values_in_range_integers_percent](#columnvaluesinrangeintegerspercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValuesInRangeIntegersPercentCheckSpec](#columnvaluesinrangeintegerspercentcheckspec)| | | |
|[daily_partition_value_below_min_value_count](#columnvaluebelowminvaluecountcheckspec)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValueBelowMinValueCountCheckSpec](#columnvaluebelowminvaluecountcheckspec)| | | |
|[daily_partition_value_below_min_value_percent](#columnvaluebelowminvaluepercentcheckspec)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValueBelowMinValuePercentCheckSpec](#columnvaluebelowminvaluepercentcheckspec)| | | |
|[daily_partition_value_above_max_value_count](#columnvalueabovemaxvaluecountcheckspec)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValueAboveMaxValueCountCheckSpec](#columnvalueabovemaxvaluecountcheckspec)| | | |
|[daily_partition_value_above_max_value_percent](#columnvalueabovemaxvaluepercentcheckspec)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValueAboveMaxValuePercentCheckSpec](#columnvalueabovemaxvaluepercentcheckspec)| | | |
|[daily_partition_max_in_range](#columnmaxinrangecheckspec)|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMaxInRangeCheckSpec](#columnmaxinrangecheckspec)| | | |
|[daily_partition_min_in_range](#columnmininrangecheckspec)|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMinInRangeCheckSpec](#columnmininrangecheckspec)| | | |
|[daily_partition_mean_in_range](#columnmeaninrangecheckspec)|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMeanInRangeCheckSpec](#columnmeaninrangecheckspec)| | | |
|[daily_partition_percentile_in_range](#columnpercentileinrangecheckspec)|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentileInRangeCheckSpec](#columnpercentileinrangecheckspec)| | | |
|[daily_partition_median_in_range](#columnmedianinrangecheckspec)|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMedianInRangeCheckSpec](#columnmedianinrangecheckspec)| | | |
|[daily_partition_percentile_10_in_range](#columnpercentile10inrangecheckspec)|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile10InRangeCheckSpec](#columnpercentile10inrangecheckspec)| | | |
|[daily_partition_percentile_25_in_range](#columnpercentile25inrangecheckspec)|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile25InRangeCheckSpec](#columnpercentile25inrangecheckspec)| | | |
|[daily_partition_percentile_75_in_range](#columnpercentile75inrangecheckspec)|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile75InRangeCheckSpec](#columnpercentile75inrangecheckspec)| | | |
|[daily_partition_percentile_90_in_range](#columnpercentile90inrangecheckspec)|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile90InRangeCheckSpec](#columnpercentile90inrangecheckspec)| | | |
|[daily_partition_sample_stddev_in_range](#columnsamplestddevinrangecheckspec)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSampleStddevInRangeCheckSpec](#columnsamplestddevinrangecheckspec)| | | |
|[daily_partition_population_stddev_in_range](#columnpopulationstddevinrangecheckspec)|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPopulationStddevInRangeCheckSpec](#columnpopulationstddevinrangecheckspec)| | | |
|[daily_partition_sample_variance_in_range](#columnsamplevarianceinrangecheckspec)|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSampleVarianceInRangeCheckSpec](#columnsamplevarianceinrangecheckspec)| | | |
|[daily_partition_population_variance_in_range](#columnpopulationvarianceinrangecheckspec)|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPopulationVarianceInRangeCheckSpec](#columnpopulationvarianceinrangecheckspec)| | | |
|[daily_partition_sum_in_range](#columnsuminrangecheckspec)|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSumInRangeCheckSpec](#columnsuminrangecheckspec)| | | |
|[daily_partition_invalid_latitude_count](#columninvalidlatitudecountcheckspec)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnInvalidLatitudeCountCheckSpec](#columninvalidlatitudecountcheckspec)| | | |
|[daily_partition_valid_latitude_percent](#columnvalidlatitudepercentcheckspec)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValidLatitudePercentCheckSpec](#columnvalidlatitudepercentcheckspec)| | | |
|[daily_partition_invalid_longitude_count](#columninvalidlongitudecountcheckspec)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnInvalidLongitudeCountCheckSpec](#columninvalidlongitudecountcheckspec)| | | |
|[daily_partition_valid_longitude_percent](#columnvalidlongitudepercentcheckspec)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValidLongitudePercentCheckSpec](#columnvalidlongitudepercentcheckspec)| | | |









___  

## ColumnStringsDailyPartitionedChecksSpec  
Container of strings data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_string_max_length](#columnstringmaxlengthcheckspec)|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMaxLengthCheckSpec](#columnstringmaxlengthcheckspec)| | | |
|[daily_partition_string_min_length](#columnstringminlengthcheckspec)|Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMinLengthCheckSpec](#columnstringminlengthcheckspec)| | | |
|[daily_partition_string_mean_length](#columnstringmeanlengthcheckspec)|Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMeanLengthCheckSpec](#columnstringmeanlengthcheckspec)| | | |
|[daily_partition_string_length_below_min_length_count](#columnstringlengthbelowminlengthcountcheckspec)|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthBelowMinLengthCountCheckSpec](#columnstringlengthbelowminlengthcountcheckspec)| | | |
|[daily_partition_string_length_below_min_length_percent](#columnstringlengthbelowminlengthpercentcheckspec)|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](#columnstringlengthbelowminlengthpercentcheckspec)| | | |
|[daily_partition_string_length_above_max_length_count](#columnstringlengthabovemaxlengthcountcheckspec)|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](#columnstringlengthabovemaxlengthcountcheckspec)| | | |
|[daily_partition_string_length_above_max_length_percent](#columnstringlengthabovemaxlengthpercentcheckspec)|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](#columnstringlengthabovemaxlengthpercentcheckspec)| | | |
|[daily_partition_string_length_in_range_percent](#columnstringlengthinrangepercentcheckspec)|The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthInRangePercentCheckSpec](#columnstringlengthinrangepercentcheckspec)| | | |
|[daily_partition_string_empty_count](#columnstringemptycountcheckspec)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringEmptyCountCheckSpec](#columnstringemptycountcheckspec)| | | |
|[daily_partition_string_empty_percent](#columnstringemptypercentcheckspec)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringEmptyPercentCheckSpec](#columnstringemptypercentcheckspec)| | | |
|[daily_partition_string_whitespace_count](#columnstringwhitespacecountcheckspec)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringWhitespaceCountCheckSpec](#columnstringwhitespacecountcheckspec)| | | |
|[daily_partition_string_whitespace_percent](#columnstringwhitespacepercentcheckspec)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringWhitespacePercentCheckSpec](#columnstringwhitespacepercentcheckspec)| | | |
|[daily_partition_string_surrounded_by_whitespace_count](#columnstringsurroundedbywhitespacecountcheckspec)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](#columnstringsurroundedbywhitespacecountcheckspec)| | | |
|[daily_partition_string_surrounded_by_whitespace_percent](#columnstringsurroundedbywhitespacepercentcheckspec)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](#columnstringsurroundedbywhitespacepercentcheckspec)| | | |
|[daily_partition_string_null_placeholder_count](#columnstringnullplaceholdercountcheckspec)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringNullPlaceholderCountCheckSpec](#columnstringnullplaceholdercountcheckspec)| | | |
|[daily_partition_string_null_placeholder_percent](#columnstringnullplaceholderpercentcheckspec)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringNullPlaceholderPercentCheckSpec](#columnstringnullplaceholderpercentcheckspec)| | | |
|[daily_partition_string_boolean_placeholder_percent](#columnstringbooleanplaceholderpercentcheckspec)|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringBooleanPlaceholderPercentCheckSpec](#columnstringbooleanplaceholderpercentcheckspec)| | | |
|[daily_partition_string_parsable_to_integer_percent](#columnstringparsabletointegerpercentcheckspec)|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringParsableToIntegerPercentCheckSpec](#columnstringparsabletointegerpercentcheckspec)| | | |
|[daily_partition_string_parsable_to_float_percent](#columnstringparsabletofloatpercentcheckspec)|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringParsableToFloatPercentCheckSpec](#columnstringparsabletofloatpercentcheckspec)| | | |
|[daily_partition_string_in_set_count](#columnstringinsetcountcheckspec)|Verifies that the number of strings from set in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInSetCountCheckSpec](#columnstringinsetcountcheckspec)| | | |
|[daily_partition_string_in_set_percent](#columnstringinsetpercentcheckspec)|Verifies that the percentage of strings from set in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInSetPercentCheckSpec](#columnstringinsetpercentcheckspec)| | | |
|[daily_partition_string_valid_dates_percent](#columnstringvaliddatespercentcheckspec)|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValidDatesPercentCheckSpec](#columnstringvaliddatespercentcheckspec)| | | |
|[daily_partition_string_valid_country_code_percent](#columnstringvalidcountrycodepercentcheckspec)|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValidCountryCodePercentCheckSpec](#columnstringvalidcountrycodepercentcheckspec)| | | |
|[daily_partition_string_valid_currency_code_percent](#columnstringvalidcurrencycodepercentcheckspec)|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValidCurrencyCodePercentCheckSpec](#columnstringvalidcurrencycodepercentcheckspec)| | | |
|[daily_partition_string_invalid_email_count](#columnstringinvalidemailcountcheckspec)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInvalidEmailCountCheckSpec](#columnstringinvalidemailcountcheckspec)| | | |
|[daily_partition_string_invalid_uuid_count](#columnstringinvaliduuidcountcheckspec)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInvalidUuidCountCheckSpec](#columnstringinvaliduuidcountcheckspec)| | | |
|[daily_partition_valid_uuid_percent](#columnstringvaliduuidpercentcheckspec)|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValidUuidPercentCheckSpec](#columnstringvaliduuidpercentcheckspec)| | | |
|[daily_partition_string_invalid_ip4_address_count](#columnstringinvalidip4addresscountcheckspec)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInvalidIp4AddressCountCheckSpec](#columnstringinvalidip4addresscountcheckspec)| | | |
|[daily_partition_string_invalid_ip6_address_count](#columnstringinvalidip6addresscountcheckspec)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInvalidIp6AddressCountCheckSpec](#columnstringinvalidip6addresscountcheckspec)| | | |
|[daily_partition_string_not_match_regex_count](#columnstringnotmatchregexcountcheckspec)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringNotMatchRegexCountCheckSpec](#columnstringnotmatchregexcountcheckspec)| | | |
|[daily_partition_string_match_regex_percent](#columnstringmatchregexpercentcheckspec)|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMatchRegexPercentCheckSpec](#columnstringmatchregexpercentcheckspec)| | | |
|[daily_partition_string_not_match_date_regex_count](#columnstringnotmatchdateregexcountcheckspec)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringNotMatchDateRegexCountCheckSpec](#columnstringnotmatchdateregexcountcheckspec)| | | |
|[daily_partition_string_match_date_regex_percent](#columnstringmatchdateregexpercentcheckspec)|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMatchDateRegexPercentCheckSpec](#columnstringmatchdateregexpercentcheckspec)| | | |
|[daily_partition_string_match_name_regex_percent](#columnstringmatchnameregexpercentcheckspec)|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMatchNameRegexPercentCheckSpec](#columnstringmatchnameregexpercentcheckspec)| | | |
|[daily_partition_string_most_popular_values](#columnstringmostpopularvaluescheckspec)|Verifies that the number of top values from a set in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMostPopularValuesCheckSpec](#columnstringmostpopularvaluescheckspec)| | | |
|[daily_partition_string_datatype_detected](#columnstringdatatypedetectedcheckspec)|Detects the data type of text values stored in the column. The sensor returns the code of the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringDatatypeDetectedCheckSpec](#columnstringdatatypedetectedcheckspec)| | | |









___  

## ColumnUniquenessDailyPartitionedChecksSpec  
Container of uniqueness data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_unique_count](#columnuniquecountcheckspec)|Verifies that the number of unique values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnUniqueCountCheckSpec](#columnuniquecountcheckspec)| | | |
|[daily_partition_unique_percent](#columnuniquepercentcheckspec)|Verifies that the percentage of unique values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnUniquePercentCheckSpec](#columnuniquepercentcheckspec)| | | |
|[daily_partition_duplicate_count](#columnduplicatecountcheckspec)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDuplicateCountCheckSpec](#columnduplicatecountcheckspec)| | | |
|[daily_partition_duplicate_percent](#columnduplicatepercentcheckspec)|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDuplicatePercentCheckSpec](#columnduplicatepercentcheckspec)| | | |









___  

## ColumnDatetimeDailyPartitionedChecksSpec  
Container of date-time data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_date_values_in_future_percent](#columndatevaluesinfuturepercentcheckspec)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDateValuesInFuturePercentCheckSpec](#columndatevaluesinfuturepercentcheckspec)| | | |
|[daily_partition_datetime_value_in_range_date_percent](#columndatetimevalueinrangedatepercentcheckspec)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](#columndatetimevalueinrangedatepercentcheckspec)| | | |









___  

## ColumnPiiDailyPartitionedChecksSpec  
Container of PII data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_valid_usa_phone_percent](#columnpiivalidusaphonepercentcheckspec)|Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiValidUsaPhonePercentCheckSpec](#columnpiivalidusaphonepercentcheckspec)| | | |
|[daily_partition_contains_usa_phone_percent](#columnpiicontainsusaphonepercentcheckspec)|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsUsaPhonePercentCheckSpec](#columnpiicontainsusaphonepercentcheckspec)| | | |
|[daily_partition_valid_usa_zipcode_percent](#columnpiivalidusazipcodepercentcheckspec)|Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiValidUsaZipcodePercentCheckSpec](#columnpiivalidusazipcodepercentcheckspec)| | | |
|[daily_partition_contains_usa_zipcode_percent](#columnpiicontainsusazipcodepercentcheckspec)|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](#columnpiicontainsusazipcodepercentcheckspec)| | | |
|[daily_partition_valid_email_percent](#columnpiivalidemailpercentcheckspec)|Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiValidEmailPercentCheckSpec](#columnpiivalidemailpercentcheckspec)| | | |
|[daily_partition_contains_email_percent](#columnpiicontainsemailpercentcheckspec)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsEmailPercentCheckSpec](#columnpiicontainsemailpercentcheckspec)| | | |
|[daily_partition_valid_ip4_address_percent](#columnpiivalidip4addresspercentcheckspec)|Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiValidIp4AddressPercentCheckSpec](#columnpiivalidip4addresspercentcheckspec)| | | |
|[daily_partition_contains_ip4_percent](#columnpiicontainsip4percentcheckspec)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsIp4PercentCheckSpec](#columnpiicontainsip4percentcheckspec)| | | |
|[daily_partition_valid_ip6_address_percent](#columnpiivalidip6addresspercentcheckspec)|Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiValidIp6AddressPercentCheckSpec](#columnpiivalidip6addresspercentcheckspec)| | | |
|[daily_partition_contains_ip6_percent](#columnpiicontainsip6percentcheckspec)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsIp6PercentCheckSpec](#columnpiicontainsip6percentcheckspec)| | | |









___  

## ColumnSqlDailyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_sql_condition_passed_percent_on_column](#columnsqlconditionpassedpercentcheckspec)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSqlConditionPassedPercentCheckSpec](#columnsqlconditionpassedpercentcheckspec)| | | |
|[daily_partition_sql_condition_failed_count_on_column](#columnsqlconditionfailedcountcheckspec)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSqlConditionFailedCountCheckSpec](#columnsqlconditionfailedcountcheckspec)| | | |
|[daily_partition_sql_aggregate_expr_column](#columnsqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSqlAggregateExprCheckSpec](#columnsqlaggregateexprcheckspec)| | | |









___  

## ColumnBoolDailyPartitionedChecksSpec  
Container of boolean data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_true_percent](#columntruepercentcheckspec)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnTruePercentCheckSpec](#columntruepercentcheckspec)| | | |
|[daily_partition_false_percent](#columnfalsepercentcheckspec)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnFalsePercentCheckSpec](#columnfalsepercentcheckspec)| | | |









___  

## ColumnIntegrityDailyPartitionedChecksSpec  
Container of integrity data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_foreign_key_not_match_count](#columnintegrityforeignkeynotmatchcountcheckspec)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](#columnintegrityforeignkeynotmatchcountcheckspec)| | | |
|[daily_partition_foreign_key_match_percent](#columnintegrityforeignkeymatchpercentcheckspec)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](#columnintegrityforeignkeymatchpercentcheckspec)| | | |









___  

## ColumnAccuracyDailyPartitionedChecksSpec  
Container of accuracy data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|









___  

## ColumnConsistencyDailyPartitionedChecksSpec  
Container of consistency data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_date_match_format_percent](#columnconsistencydatematchformatpercentcheckspec)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnConsistencyDateMatchFormatPercentCheckSpec](#columnconsistencydatematchformatpercentcheckspec)| | | |
|[daily_partition_string_datatype_changed](#columnstringdatatypechangedcheckspec)|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringDatatypeChangedCheckSpec](#columnstringdatatypechangedcheckspec)| | | |









___  

## ColumnAnomalyDailyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_mean_anomaly_7_days](#columnanomalymean7dayscheckspec)|Verifies that the mean value in a column is within a percentile from measurements made during the last 7 days.|[ColumnAnomalyMean7DaysCheckSpec](#columnanomalymean7dayscheckspec)| | | |
|[daily_partition_mean_anomaly_30_days](#columnanomalymean30dayscheckspec)|Verifies that the mean value in a column is within a percentile from measurements made during the last 30 days.|[ColumnAnomalyMean30DaysCheckSpec](#columnanomalymean30dayscheckspec)| | | |
|[daily_partition_mean_anomaly_60_days](#columnanomalymean60dayscheckspec)|Verifies that the mean value in a column is within a percentile from measurements made during the last 60 days.|[ColumnAnomalyMean60DaysCheckSpec](#columnanomalymean60dayscheckspec)| | | |
|[daily_partition_median_anomaly_7_days](#columnanomalymedian7dayscheckspec)|Verifies that the median in a column is within a percentile from measurements made during the last 7 days.|[ColumnAnomalyMedian7DaysCheckSpec](#columnanomalymedian7dayscheckspec)| | | |
|[daily_partition_median_anomaly_30_days](#columnanomalymedian30dayscheckspec)|Verifies that the median in a column is within a percentile from measurements made during the last 30 days.|[ColumnAnomalyMedian30DaysCheckSpec](#columnanomalymedian30dayscheckspec)| | | |
|[daily_partition_median_anomaly_60_days](#columnanomalymedian60dayscheckspec)|Verifies that the median in a column is within a percentile from measurements made during the last 60 days.|[ColumnAnomalyMedian60DaysCheckSpec](#columnanomalymedian60dayscheckspec)| | | |
|[daily_partition_sum_anomaly_7_days](#columnanomalysum7dayscheckspec)|Verifies that the sum in a column is within a percentile from measurements made during the last 7 days.|[ColumnAnomalySum7DaysCheckSpec](#columnanomalysum7dayscheckspec)| | | |
|[daily_partition_sum_anomaly_30_days](#columnanomalysum30dayscheckspec)|Verifies that the sum in a column is within a percentile from measurements made during the last 30 days.|[ColumnAnomalySum30DaysCheckSpec](#columnanomalysum30dayscheckspec)| | | |
|[daily_partition_sum_anomaly_60_days](#columnanomalysum60dayscheckspec)|Verifies that the sum in a column is within a percentile from measurements made during the last 60 days.|[ColumnAnomalySum60DaysCheckSpec](#columnanomalysum60dayscheckspec)| | | |









___  

## ColumnAnomalyMean7DaysCheckSpec  
Column level check that ensures that the mean value in a monitored column is within a two-tailed percentile from measurements made during the last 7 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
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

## ColumnAnomalyMean30DaysCheckSpec  
Column level check that ensures that the mean value in a monitored column is within a two-tailed percentile from measurements made during the last 30 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
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

## ColumnAnomalyMean60DaysCheckSpec  
Column level check that ensures that the mean value in a monitored column is within a two-tailed percentile from measurements made during the last 60 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
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

## ColumnAnomalyMedian7DaysCheckSpec  
Column level check that ensures that the median in a monitored column is within a two-tailed percentile from measurements made during the last 7 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
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

## ColumnAnomalyMedian30DaysCheckSpec  
Column level check that ensures that the median in a monitored column is within a two-tailed percentile from measurements made during the last 30 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
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

## ColumnAnomalyMedian60DaysCheckSpec  
Column level check that ensures that the median in a monitored column is within a two-tailed percentile from measurements made during the last 60 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
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

## ColumnAnomalySum7DaysCheckSpec  
Column level check that ensures that the sum in a monitored column is within a two-tailed percentile from measurements made during the last 7 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
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

## ColumnAnomalySum30DaysCheckSpec  
Column level check that ensures that the sum in a monitored column is within a two-tailed percentile from measurements made during the last 30 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
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

## ColumnAnomalySum60DaysCheckSpec  
Column level check that ensures that the sum in a monitored column is within a two-tailed percentile from measurements made during the last 60 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
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

## ColumnMonthlyPartitionedCheckCategoriesSpec  
Container of data quality partitioned checks on a column level that are checking numeric values at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](#columnnullsmonthlypartitionedchecksspec)|Monthly partitioned checks of nulls values in the column|[ColumnNullsMonthlyPartitionedChecksSpec](#columnnullsmonthlypartitionedchecksspec)| | | |
|[numeric](#columnnumericmonthlypartitionedchecksspec)|Monthly partitioned checks of numeric values in the column|[ColumnNumericMonthlyPartitionedChecksSpec](#columnnumericmonthlypartitionedchecksspec)| | | |
|[strings](#columnstringsmonthlypartitionedchecksspec)|Monthly partitioned checks of strings values in the column|[ColumnStringsMonthlyPartitionedChecksSpec](#columnstringsmonthlypartitionedchecksspec)| | | |
|[uniqueness](#columnuniquenessmonthlypartitionedchecksspec)|Monthly partitioned checks of uniqueness values in the column|[ColumnUniquenessMonthlyPartitionedChecksSpec](#columnuniquenessmonthlypartitionedchecksspec)| | | |
|[datetime](#columndatetimemonthlypartitionedchecksspec)|Monthly partitioned checks of datetime values in the column|[ColumnDatetimeMonthlyPartitionedChecksSpec](#columndatetimemonthlypartitionedchecksspec)| | | |
|[pii](#columnpiimonthlypartitionedchecksspec)|Monthly partitioned checks of Personal Identifiable Information (PII) in the column|[ColumnPiiMonthlyPartitionedChecksSpec](#columnpiimonthlypartitionedchecksspec)| | | |
|[sql](#columnsqlmonthlypartitionedchecksspec)|Monthly partitioned checks using custom SQL expressions and conditions on the column|[ColumnSqlMonthlyPartitionedChecksSpec](#columnsqlmonthlypartitionedchecksspec)| | | |
|[bool](#columnboolmonthlypartitionedchecksspec)|Monthly partitioned checks for booleans in the column|[ColumnBoolMonthlyPartitionedChecksSpec](#columnboolmonthlypartitionedchecksspec)| | | |
|[integrity](#columnintegritymonthlypartitionedchecksspec)|Monthly partitioned checks for integrity in the column|[ColumnIntegrityMonthlyPartitionedChecksSpec](#columnintegritymonthlypartitionedchecksspec)| | | |
|[accuracy](#columnaccuracymonthlypartitionedchecksspec)|Monthly partitioned checks for accuracy in the column|[ColumnAccuracyMonthlyPartitionedChecksSpec](#columnaccuracymonthlypartitionedchecksspec)| | | |
|[consistency](#columnconsistencymonthlypartitionedchecksspec)|Monthly partitioned checks for consistency in the column|[ColumnConsistencyMonthlyPartitionedChecksSpec](#columnconsistencymonthlypartitionedchecksspec)| | | |
|[anomaly](#columnanomalymonthlypartitionedchecksspec)|Monthly partitioned checks for anomaly in the column|[ColumnAnomalyMonthlyPartitionedChecksSpec](#columnanomalymonthlypartitionedchecksspec)| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## ColumnNullsMonthlyPartitionedChecksSpec  
Container of nulls data quality partitioned checks on a column level that are checking monthly partitions or rows for each day of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_nulls_count](#columnnullscountcheckspec)|Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNullsCountCheckSpec](#columnnullscountcheckspec)| | | |
|[monthly_partition_nulls_percent](#columnnullspercentcheckspec)|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNullsPercentCheckSpec](#columnnullspercentcheckspec)| | | |
|[monthly_partition_not_nulls_count](#columnnotnullscountcheckspec)|Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNotNullsCountCheckSpec](#columnnotnullscountcheckspec)| | | |
|[monthly_partition_not_nulls_percent](#columnnotnullspercentcheckspec)|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNotNullsPercentCheckSpec](#columnnotnullspercentcheckspec)| | | |









___  

## ColumnNumericMonthlyPartitionedChecksSpec  
Container of numeric data quality partitioned checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_negative_count](#columnnegativecountcheckspec)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNegativeCountCheckSpec](#columnnegativecountcheckspec)| | | |
|[monthly_partition_negative_percent](#columnnegativepercentcheckspec)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNegativePercentCheckSpec](#columnnegativepercentcheckspec)| | | |
|[monthly_partition_non_negative_count](#columnnonnegativecountcheckspec)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNonNegativeCountCheckSpec](#columnnonnegativecountcheckspec)| | | |
|[monthly_partition_non_negative_percent](#columnnonnegativepercentcheckspec)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNonNegativePercentCheckSpec](#columnnonnegativepercentcheckspec)| | | |
|[monthly_partition_numbers_found_count](#columnnumbersfoundcountcheckspec)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNumbersFoundCountCheckSpec](#columnnumbersfoundcountcheckspec)| | | |
|[monthly_partition_has_valid_numbers_percent](#columnhasvalidnumberspercentcheckspec)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnHasValidNumbersPercentCheckSpec](#columnhasvalidnumberspercentcheckspec)| | | |
|[monthly_partition_values_in_range_numeric_percent](#columnvaluesinrangenumericpercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValuesInRangeNumericPercentCheckSpec](#columnvaluesinrangenumericpercentcheckspec)| | | |
|[monthly_partition_values_in_range_integers_percent](#columnvaluesinrangeintegerspercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValuesInRangeIntegersPercentCheckSpec](#columnvaluesinrangeintegerspercentcheckspec)| | | |
|[monthly_partition_value_below_min_value_count](#columnvaluebelowminvaluecountcheckspec)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValueBelowMinValueCountCheckSpec](#columnvaluebelowminvaluecountcheckspec)| | | |
|[monthly_partition_value_below_min_value_percent](#columnvaluebelowminvaluepercentcheckspec)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValueBelowMinValuePercentCheckSpec](#columnvaluebelowminvaluepercentcheckspec)| | | |
|[monthly_partition_value_above_max_value_count](#columnvalueabovemaxvaluecountcheckspec)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValueAboveMaxValueCountCheckSpec](#columnvalueabovemaxvaluecountcheckspec)| | | |
|[monthly_partition_value_above_max_value_percent](#columnvalueabovemaxvaluepercentcheckspec)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValueAboveMaxValuePercentCheckSpec](#columnvalueabovemaxvaluepercentcheckspec)| | | |
|[monthly_partition_max_in_range](#columnmaxinrangecheckspec)|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMaxInRangeCheckSpec](#columnmaxinrangecheckspec)| | | |
|[monthly_partition_min_in_range](#columnmininrangecheckspec)|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMinInRangeCheckSpec](#columnmininrangecheckspec)| | | |
|[monthly_partition_mean_in_range](#columnmeaninrangecheckspec)|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMeanInRangeCheckSpec](#columnmeaninrangecheckspec)| | | |
|[monthly_partition_percentile_in_range](#columnpercentileinrangecheckspec)|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentileInRangeCheckSpec](#columnpercentileinrangecheckspec)| | | |
|[monthly_partition_median_in_range](#columnmedianinrangecheckspec)|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMedianInRangeCheckSpec](#columnmedianinrangecheckspec)| | | |
|[monthly_partition_percentile_10_in_range](#columnpercentile10inrangecheckspec)|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile10InRangeCheckSpec](#columnpercentile10inrangecheckspec)| | | |
|[monthly_partition_percentile_25_in_range](#columnpercentile25inrangecheckspec)|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile25InRangeCheckSpec](#columnpercentile25inrangecheckspec)| | | |
|[monthly_partition_percentile_75_in_range](#columnpercentile75inrangecheckspec)|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile75InRangeCheckSpec](#columnpercentile75inrangecheckspec)| | | |
|[monthly_partition_percentile_90_in_range](#columnpercentile90inrangecheckspec)|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile90InRangeCheckSpec](#columnpercentile90inrangecheckspec)| | | |
|[monthly_partition_sample_stddev_in_range](#columnsamplestddevinrangecheckspec)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSampleStddevInRangeCheckSpec](#columnsamplestddevinrangecheckspec)| | | |
|[monthly_partition_population_stddev_in_range](#columnpopulationstddevinrangecheckspec)|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPopulationStddevInRangeCheckSpec](#columnpopulationstddevinrangecheckspec)| | | |
|[monthly_partition_sample_variance_in_range](#columnsamplevarianceinrangecheckspec)|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSampleVarianceInRangeCheckSpec](#columnsamplevarianceinrangecheckspec)| | | |
|[monthly_partition_population_variance_in_range](#columnpopulationvarianceinrangecheckspec)|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPopulationVarianceInRangeCheckSpec](#columnpopulationvarianceinrangecheckspec)| | | |
|[monthly_partition_sum_in_range](#columnsuminrangecheckspec)|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSumInRangeCheckSpec](#columnsuminrangecheckspec)| | | |
|[monthly_partition_invalid_latitude_count](#columninvalidlatitudecountcheckspec)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnInvalidLatitudeCountCheckSpec](#columninvalidlatitudecountcheckspec)| | | |
|[monthly_partition_valid_latitude_percent](#columnvalidlatitudepercentcheckspec)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValidLatitudePercentCheckSpec](#columnvalidlatitudepercentcheckspec)| | | |
|[monthly_partition_invalid_longitude_count](#columninvalidlongitudecountcheckspec)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnInvalidLongitudeCountCheckSpec](#columninvalidlongitudecountcheckspec)| | | |
|[monthly_partition_valid_longitude_percent](#columnvalidlongitudepercentcheckspec)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValidLongitudePercentCheckSpec](#columnvalidlongitudepercentcheckspec)| | | |









___  

## ColumnStringsMonthlyPartitionedChecksSpec  
Container of strings data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_string_max_length](#columnstringmaxlengthcheckspec)|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMaxLengthCheckSpec](#columnstringmaxlengthcheckspec)| | | |
|[monthly_partition_string_min_length](#columnstringminlengthcheckspec)|Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMinLengthCheckSpec](#columnstringminlengthcheckspec)| | | |
|[monthly_partition_string_mean_length](#columnstringmeanlengthcheckspec)|Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMeanLengthCheckSpec](#columnstringmeanlengthcheckspec)| | | |
|[monthly_partition_string_length_below_min_length_count](#columnstringlengthbelowminlengthcountcheckspec)|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthBelowMinLengthCountCheckSpec](#columnstringlengthbelowminlengthcountcheckspec)| | | |
|[monthly_partition_string_length_below_min_length_percent](#columnstringlengthbelowminlengthpercentcheckspec)|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](#columnstringlengthbelowminlengthpercentcheckspec)| | | |
|[monthly_partition_string_length_above_max_length_count](#columnstringlengthabovemaxlengthcountcheckspec)|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](#columnstringlengthabovemaxlengthcountcheckspec)| | | |
|[monthly_partition_string_length_above_max_length_percent](#columnstringlengthabovemaxlengthpercentcheckspec)|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](#columnstringlengthabovemaxlengthpercentcheckspec)| | | |
|[monthly_partition_string_length_in_range_percent](#columnstringlengthinrangepercentcheckspec)|The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthInRangePercentCheckSpec](#columnstringlengthinrangepercentcheckspec)| | | |
|[monthly_partition_string_empty_count](#columnstringemptycountcheckspec)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringEmptyCountCheckSpec](#columnstringemptycountcheckspec)| | | |
|[monthly_partition_string_empty_percent](#columnstringemptypercentcheckspec)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringEmptyPercentCheckSpec](#columnstringemptypercentcheckspec)| | | |
|[monthly_partition_string_whitespace_count](#columnstringwhitespacecountcheckspec)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringWhitespaceCountCheckSpec](#columnstringwhitespacecountcheckspec)| | | |
|[monthly_partition_string_whitespace_percent](#columnstringwhitespacepercentcheckspec)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringWhitespacePercentCheckSpec](#columnstringwhitespacepercentcheckspec)| | | |
|[monthly_partition_string_surrounded_by_whitespace_count](#columnstringsurroundedbywhitespacecountcheckspec)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](#columnstringsurroundedbywhitespacecountcheckspec)| | | |
|[monthly_partition_string_surrounded_by_whitespace_percent](#columnstringsurroundedbywhitespacepercentcheckspec)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](#columnstringsurroundedbywhitespacepercentcheckspec)| | | |
|[monthly_partition_string_null_placeholder_count](#columnstringnullplaceholdercountcheckspec)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringNullPlaceholderCountCheckSpec](#columnstringnullplaceholdercountcheckspec)| | | |
|[monthly_partition_string_null_placeholder_percent](#columnstringnullplaceholderpercentcheckspec)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringNullPlaceholderPercentCheckSpec](#columnstringnullplaceholderpercentcheckspec)| | | |
|[monthly_partition_string_boolean_placeholder_percent](#columnstringbooleanplaceholderpercentcheckspec)|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringBooleanPlaceholderPercentCheckSpec](#columnstringbooleanplaceholderpercentcheckspec)| | | |
|[monthly_partition_string_parsable_to_integer_percent](#columnstringparsabletointegerpercentcheckspec)|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringParsableToIntegerPercentCheckSpec](#columnstringparsabletointegerpercentcheckspec)| | | |
|[monthly_partition_string_parsable_to_float_percent](#columnstringparsabletofloatpercentcheckspec)|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringParsableToFloatPercentCheckSpec](#columnstringparsabletofloatpercentcheckspec)| | | |
|[monthly_partition_string_in_set_count](#columnstringinsetcountcheckspec)|Verifies that the number of strings from set in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInSetCountCheckSpec](#columnstringinsetcountcheckspec)| | | |
|[monthly_partition_string_in_set_percent](#columnstringinsetpercentcheckspec)|Verifies that the percentage of strings from set in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInSetPercentCheckSpec](#columnstringinsetpercentcheckspec)| | | |
|[monthly_partition_string_valid_dates_percent](#columnstringvaliddatespercentcheckspec)|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValidDatesPercentCheckSpec](#columnstringvaliddatespercentcheckspec)| | | |
|[monthly_partition_string_valid_country_code_percent](#columnstringvalidcountrycodepercentcheckspec)|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValidCountryCodePercentCheckSpec](#columnstringvalidcountrycodepercentcheckspec)| | | |
|[monthly_partition_string_valid_currency_code_percent](#columnstringvalidcurrencycodepercentcheckspec)|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValidCurrencyCodePercentCheckSpec](#columnstringvalidcurrencycodepercentcheckspec)| | | |
|[monthly_partition_string_invalid_email_count](#columnstringinvalidemailcountcheckspec)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInvalidEmailCountCheckSpec](#columnstringinvalidemailcountcheckspec)| | | |
|[monthly_partition_string_invalid_uuid_count](#columnstringinvaliduuidcountcheckspec)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInvalidUuidCountCheckSpec](#columnstringinvaliduuidcountcheckspec)| | | |
|[monthly_partition_valid_uuid_percent](#columnstringvaliduuidpercentcheckspec)|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValidUuidPercentCheckSpec](#columnstringvaliduuidpercentcheckspec)| | | |
|[monthly_partition_string_invalid_ip4_address_count](#columnstringinvalidip4addresscountcheckspec)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInvalidIp4AddressCountCheckSpec](#columnstringinvalidip4addresscountcheckspec)| | | |
|[monthly_partition_string_invalid_ip6_address_count](#columnstringinvalidip6addresscountcheckspec)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInvalidIp6AddressCountCheckSpec](#columnstringinvalidip6addresscountcheckspec)| | | |
|[monthly_partition_string_not_match_regex_count](#columnstringnotmatchregexcountcheckspec)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringNotMatchRegexCountCheckSpec](#columnstringnotmatchregexcountcheckspec)| | | |
|[monthly_partition_string_match_regex_percent](#columnstringmatchregexpercentcheckspec)|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMatchRegexPercentCheckSpec](#columnstringmatchregexpercentcheckspec)| | | |
|[monthly_partition_string_not_match_date_regex_count](#columnstringnotmatchdateregexcountcheckspec)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringNotMatchDateRegexCountCheckSpec](#columnstringnotmatchdateregexcountcheckspec)| | | |
|[monthly_partition_string_match_date_regex_percent](#columnstringmatchdateregexpercentcheckspec)|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMatchDateRegexPercentCheckSpec](#columnstringmatchdateregexpercentcheckspec)| | | |
|[monthly_partition_string_match_name_regex_percent](#columnstringmatchnameregexpercentcheckspec)|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMatchNameRegexPercentCheckSpec](#columnstringmatchnameregexpercentcheckspec)| | | |
|[monthly_partition_string_most_popular_values](#columnstringmostpopularvaluescheckspec)|Verifies that the number of top values from a set in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMostPopularValuesCheckSpec](#columnstringmostpopularvaluescheckspec)| | | |
|[monthly_partition_string_datatype_detected](#columnstringdatatypedetectedcheckspec)|Detects the data type of text values stored in the column. The sensor returns the code of the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringDatatypeDetectedCheckSpec](#columnstringdatatypedetectedcheckspec)| | | |









___  

## ColumnUniquenessMonthlyPartitionedChecksSpec  
Container of uniqueness data quality partitioned checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_unique_count](#columnuniquecountcheckspec)|Verifies that the number of unique values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnUniqueCountCheckSpec](#columnuniquecountcheckspec)| | | |
|[monthly_partition_unique_percent](#columnuniquepercentcheckspec)|Verifies that the percentage of unique values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnUniquePercentCheckSpec](#columnuniquepercentcheckspec)| | | |
|[monthly_partition_duplicate_count](#columnduplicatecountcheckspec)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDuplicateCountCheckSpec](#columnduplicatecountcheckspec)| | | |
|[monthly_partition_duplicate_percent](#columnduplicatepercentcheckspec)|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDuplicatePercentCheckSpec](#columnduplicatepercentcheckspec)| | | |









___  

## ColumnDatetimeMonthlyPartitionedChecksSpec  
Container of date-time data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_date_values_in_future_percent](#columndatevaluesinfuturepercentcheckspec)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDateValuesInFuturePercentCheckSpec](#columndatevaluesinfuturepercentcheckspec)| | | |
|[monthly_partition_datetime_value_in_range_date_percent](#columndatetimevalueinrangedatepercentcheckspec)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](#columndatetimevalueinrangedatepercentcheckspec)| | | |









___  

## ColumnPiiMonthlyPartitionedChecksSpec  
Container of PII data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_valid_usa_phone_percent](#columnpiivalidusaphonepercentcheckspec)|Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiValidUsaPhonePercentCheckSpec](#columnpiivalidusaphonepercentcheckspec)| | | |
|[monthly_partition_contains_usa_phone_percent](#columnpiicontainsusaphonepercentcheckspec)|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsUsaPhonePercentCheckSpec](#columnpiicontainsusaphonepercentcheckspec)| | | |
|[monthly_partition_valid_usa_zipcode_percent](#columnpiivalidusazipcodepercentcheckspec)|Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiValidUsaZipcodePercentCheckSpec](#columnpiivalidusazipcodepercentcheckspec)| | | |
|[monthly_partition_contains_usa_zipcode_percent](#columnpiicontainsusazipcodepercentcheckspec)|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](#columnpiicontainsusazipcodepercentcheckspec)| | | |
|[monthly_partition_valid_email_percent](#columnpiivalidemailpercentcheckspec)|Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiValidEmailPercentCheckSpec](#columnpiivalidemailpercentcheckspec)| | | |
|[monthly_partition_contains_email_percent](#columnpiicontainsemailpercentcheckspec)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsEmailPercentCheckSpec](#columnpiicontainsemailpercentcheckspec)| | | |
|[monthly_partition_valid_ip4_address_percent](#columnpiivalidip4addresspercentcheckspec)|Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiValidIp4AddressPercentCheckSpec](#columnpiivalidip4addresspercentcheckspec)| | | |
|[monthly_partition_contains_ip4_percent](#columnpiicontainsip4percentcheckspec)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsIp4PercentCheckSpec](#columnpiicontainsip4percentcheckspec)| | | |
|[monthly_partition_valid_ip6_address_percent](#columnpiivalidip6addresspercentcheckspec)|Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiValidIp6AddressPercentCheckSpec](#columnpiivalidip6addresspercentcheckspec)| | | |
|[monthly_partition_contains_ip6_percent](#columnpiicontainsip6percentcheckspec)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsIp6PercentCheckSpec](#columnpiicontainsip6percentcheckspec)| | | |









___  

## ColumnSqlMonthlyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_sql_condition_passed_percent_on_column](#columnsqlconditionpassedpercentcheckspec)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSqlConditionPassedPercentCheckSpec](#columnsqlconditionpassedpercentcheckspec)| | | |
|[monthly_partition_sql_condition_failed_count_on_column](#columnsqlconditionfailedcountcheckspec)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSqlConditionFailedCountCheckSpec](#columnsqlconditionfailedcountcheckspec)| | | |
|[monthly_partition_sql_aggregate_expr_column](#columnsqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSqlAggregateExprCheckSpec](#columnsqlaggregateexprcheckspec)| | | |









___  

## ColumnBoolMonthlyPartitionedChecksSpec  
Container of boolean data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_true_percent](#columntruepercentcheckspec)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnTruePercentCheckSpec](#columntruepercentcheckspec)| | | |
|[monthly_partition_false_percent](#columnfalsepercentcheckspec)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnFalsePercentCheckSpec](#columnfalsepercentcheckspec)| | | |









___  

## ColumnIntegrityMonthlyPartitionedChecksSpec  
Container of integrity data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_foreign_key_not_match_count](#columnintegrityforeignkeynotmatchcountcheckspec)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](#columnintegrityforeignkeynotmatchcountcheckspec)| | | |
|[monthly_partition_foreign_key_match_percent](#columnintegrityforeignkeymatchpercentcheckspec)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](#columnintegrityforeignkeymatchpercentcheckspec)| | | |









___  

## ColumnAccuracyMonthlyPartitionedChecksSpec  
Container of accuracy data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|









___  

## ColumnConsistencyMonthlyPartitionedChecksSpec  
Container of consistency data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_date_match_format_percent](#columnconsistencydatematchformatpercentcheckspec)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnConsistencyDateMatchFormatPercentCheckSpec](#columnconsistencydatematchformatpercentcheckspec)| | | |
|[monthly_partition_string_datatype_changed](#columnstringdatatypechangedcheckspec)|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringDatatypeChangedCheckSpec](#columnstringdatatypechangedcheckspec)| | | |









___  

## ColumnAnomalyMonthlyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|









___  

## ColumnStatisticsCollectorsRootCategoriesSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](#columnnullsstatisticscollectorsspec)|Configuration of null values profilers on a column level.|[ColumnNullsStatisticsCollectorsSpec](#columnnullsstatisticscollectorsspec)| | | |
|[strings](#columnstringsstatisticscollectorsspec)|Configuration of string (text) profilers on a column level.|[ColumnStringsStatisticsCollectorsSpec](#columnstringsstatisticscollectorsspec)| | | |
|[uniqueness](#columnuniquenessstatisticscollectorsspec)|Configuration of profilers that analyse uniqueness of values (distinct count).|[ColumnUniquenessStatisticsCollectorsSpec](#columnuniquenessstatisticscollectorsspec)| | | |
|[range](#columnrangestatisticscollectorsspec)|Configuration of profilers that analyse the range of values (min, max).|[ColumnRangeStatisticsCollectorsSpec](#columnrangestatisticscollectorsspec)| | | |









___  

## ColumnNullsStatisticsCollectorsSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls_count](#columnnullsnullscountstatisticscollectorspec)|Configuration of the profiler that counts null column values.|[ColumnNullsNullsCountStatisticsCollectorSpec](#columnnullsnullscountstatisticscollectorspec)| | | |
|[nulls_percent](#columnnullsnullspercentstatisticscollectorspec)|Configuration of the profiler that measures the percentage of null values.|[ColumnNullsNullsPercentStatisticsCollectorSpec](#columnnullsnullspercentstatisticscollectorspec)| | | |
|[not_nulls_count](#columnnullsnotnullscountstatisticscollectorspec)|Configuration of the profiler that counts not null column values.|[ColumnNullsNotNullsCountStatisticsCollectorSpec](#columnnullsnotnullscountstatisticscollectorspec)| | | |
|[not_nulls_percent](#columnnullsnotnullspercentstatisticscollectorspec)|Configuration of the profiler that measures the percentage of not null values.|[ColumnNullsNotNullsPercentStatisticsCollectorSpec](#columnnullsnotnullspercentstatisticscollectorspec)| | | |









___  

## ColumnNullsNullsCountStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnullsnullscountsensorparametersspec)|Profiler parameters|[ColumnNullsNullsCountSensorParametersSpec](#columnnullsnullscountsensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnNullsNullsPercentStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnullsnullspercentsensorparametersspec)|Profiler parameters|[ColumnNullsNullsPercentSensorParametersSpec](#columnnullsnullspercentsensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnNullsNotNullsCountStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnullsnotnullscountsensorparametersspec)|Profiler parameters|[ColumnNullsNotNullsCountSensorParametersSpec](#columnnullsnotnullscountsensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnNullsNotNullsPercentStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnullsnotnullspercentsensorparametersspec)|Profiler parameters|[ColumnNullsNotNullsPercentSensorParametersSpec](#columnnullsnotnullspercentsensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnStringsStatisticsCollectorsSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[string_max_length](#columnstringsstringmaxlengthstatisticscollectorspec)|Configuration of the profiler that finds the maximum string length.|[ColumnStringsStringMaxLengthStatisticsCollectorSpec](#columnstringsstringmaxlengthstatisticscollectorspec)| | | |
|[string_mean_length](#columnstringsstringmeanlengthstatisticscollectorspec)|Configuration of the profiler that finds the mean string length.|[ColumnStringsStringMeanLengthStatisticsCollectorSpec](#columnstringsstringmeanlengthstatisticscollectorspec)| | | |
|[string_min_length](#columnstringsstringminlengthstatisticscollectorspec)|Configuration of the profiler that finds the min string length.|[ColumnStringsStringMinLengthStatisticsCollectorSpec](#columnstringsstringminlengthstatisticscollectorspec)| | | |
|[string_datatype_detect](#columnstringsstringdatatypedetectstatisticscollectorspec)|Configuration of the profiler that detects datatype.|[ColumnStringsStringDatatypeDetectStatisticsCollectorSpec](#columnstringsstringdatatypedetectstatisticscollectorspec)| | | |









___  

## ColumnStringsStringMaxLengthStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringmaxlengthsensorparametersspec)|Profiler parameters|[ColumnStringsStringMaxLengthSensorParametersSpec](#columnstringsstringmaxlengthsensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnStringsStringMeanLengthStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringmeanlengthsensorparametersspec)|Profiler parameters|[ColumnStringsStringMeanLengthSensorParametersSpec](#columnstringsstringmeanlengthsensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnStringsStringMinLengthStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringminlengthsensorparametersspec)|Profiler parameters|[ColumnStringsStringMinLengthSensorParametersSpec](#columnstringsstringminlengthsensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnStringsStringDatatypeDetectStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringdatatypedetectsensorparametersspec)|Profiler parameters|[ColumnStringsStringDatatypeDetectSensorParametersSpec](#columnstringsstringdatatypedetectsensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnUniquenessStatisticsCollectorsSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[unique_count](#columnuniquenessuniquecountstatisticscollectorspec)|Configuration of the profiler that counts unique (distinct) column values.|[ColumnUniquenessUniqueCountStatisticsCollectorSpec](#columnuniquenessuniquecountstatisticscollectorspec)| | | |
|[unique_percent](#columnuniquenessuniquepercentstatisticscollectorspec)|Configuration of the profiler that measure the percentage of unique (distinct) column values.|[ColumnUniquenessUniquePercentStatisticsCollectorSpec](#columnuniquenessuniquepercentstatisticscollectorspec)| | | |
|[duplicate_count](#columnuniquenessduplicatecountstatisticscollectorspec)|Configuration of the profiler that counts duplicate column values.|[ColumnUniquenessDuplicateCountStatisticsCollectorSpec](#columnuniquenessduplicatecountstatisticscollectorspec)| | | |
|[duplicate_percent](#columnuniquenessduplicatepercentstatisticscollectorspec)|Configuration of the profiler that measure the percentage of duplicate column values.|[ColumnUniquenessDuplicatePercentStatisticsCollectorSpec](#columnuniquenessduplicatepercentstatisticscollectorspec)| | | |









___  

## ColumnUniquenessUniqueCountStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnuniquenessuniquecountsensorparametersspec)|Profiler parameters|[ColumnUniquenessUniqueCountSensorParametersSpec](#columnuniquenessuniquecountsensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnUniquenessUniquePercentStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnuniquenessuniquepercentsensorparametersspec)|Profiler parameters|[ColumnUniquenessUniquePercentSensorParametersSpec](#columnuniquenessuniquepercentsensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnUniquenessDuplicateCountStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnuniquenessduplicatecountsensorparametersspec)|Profiler parameters|[ColumnUniquenessDuplicateCountSensorParametersSpec](#columnuniquenessduplicatecountsensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnUniquenessDuplicatePercentStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnuniquenessduplicatepercentsensorparametersspec)|Profiler parameters|[ColumnUniquenessDuplicatePercentSensorParametersSpec](#columnuniquenessduplicatepercentsensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnRangeStatisticsCollectorsSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[min_value](#columnrangeminvaluestatisticscollectorspec)|Configuration of the profiler that finds the minimum value in the column.|[ColumnRangeMinValueStatisticsCollectorSpec](#columnrangeminvaluestatisticscollectorspec)| | | |
|[median_value](#columnrangemedianvaluestatisticscollectorspec)|Configuration of the profiler that finds the median value in the column.|[ColumnRangeMedianValueStatisticsCollectorSpec](#columnrangemedianvaluestatisticscollectorspec)| | | |
|[max_value](#columnrangemaxvaluestatisticscollectorspec)|Configuration of the profiler that finds the maximum value in the column.|[ColumnRangeMaxValueStatisticsCollectorSpec](#columnrangemaxvaluestatisticscollectorspec)| | | |
|[sum_value](#columnrangesumvaluestatisticscollectorspec)|Configuration of the profiler that finds the sum value in the column.|[ColumnRangeSumValueStatisticsCollectorSpec](#columnrangesumvaluestatisticscollectorspec)| | | |









___  

## ColumnRangeMinValueStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnrangeminvaluesensorparametersspec)|Profiler parameters|[ColumnRangeMinValueSensorParametersSpec](#columnrangeminvaluesensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnRangeMinValueSensorParametersSpec  
Column level sensor that finds the minimum value. It works on any data type that supports the MIN functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnRangeMedianValueStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Profiler parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnRangeMaxValueStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnrangemaxvaluesensorparametersspec)|Profiler parameters|[ColumnRangeMaxValueSensorParametersSpec](#columnrangemaxvaluesensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnRangeMaxValueSensorParametersSpec  
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnRangeSumValueStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Profiler parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## LabelSetSpec  
Collection of unique labels assigned to items (tables, columns, checks) that could be targeted for a data quality check execution.  
  




___  

