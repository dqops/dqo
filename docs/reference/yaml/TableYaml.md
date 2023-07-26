
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
|default_grouping_name|The name of the default data grouping configuration that is applied on data quality checks. When a default data grouping is selected, all data quality checks run SQL queries with a GROUP BY clause, calculating separate data quality checks for each group of data. The data groupings are defined in the &#x27;groupings&#x27; dictionary (indexed by the data grouping name).|string| | | |
|[groupings](#datagroupingconfigurationspecmap)|Data grouping configurations list. Data grouping configurations are configured in two cases: (1) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a &#x27;country&#x27; column used for partitioning. (2) a tag is assigned to a table (within a data grouping level hierarchy), when the data is segmented at a table level (similar tables store the same information, but for different countries, etc.).|[DataGroupingConfigurationSpecMap](#datagroupingconfigurationspecmap)| | | |
|[table_comparisons](#tablecomparisonconfigurationspecmap)|Dictionary of data comparison configurations. Data comparison configurations are used for cross data-source comparisons to compare this table (called the compared table) with other reference tables (the source of truth). The reference table&#x27;s metadata must be imported into DQO, but the reference table could be located on a different data source. DQO will compare metrics calculated for groups of rows (using a GROUP BY clause). For each comparison, the user must specify a name of a data grouping. The number of data grouping dimensions on the parent table and the reference table defined in selected data grouping configurations must match. DQO will run the same data quality sensors on both the parent table (tested table) and the reference table (the source of truth), comparing the measures (sensor readouts) captured from both the tables.|[TableComparisonConfigurationSpecMap](#tablecomparisonconfigurationspecmap)| | | |
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

## DataGroupingConfigurationSpecMap  
Dictionary of named data grouping configurations defined on a table level.  
  









___  

## TableIncidentGroupingSpec  
Configuration of data quality incident grouping on a table level. Defines how similar data quality issues are grouped into incidents.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|grouping_level|Grouping level of failed data quality checks for creating higher level data quality incidents. The default grouping level is by a table, a data quality dimension and a check category (i.e. a consistency data quality incident detected on a table X in the numeric checks category).|enum|table_dimension_category_type<br/>table_dimension<br/>table<br/>table_dimension_category<br/>table_dimension_category_name<br/>| | |
|minimum_severity|Minimum severity level of data quality issues that are grouped into incidents. The default minimum severity level is &#x27;warning&#x27;. Other supported severity levels are &#x27;error&#x27; and &#x27;fatal&#x27;.|enum|warning<br/>error<br/>fatal<br/>| | |
|divide_by_data_group|Create separate data quality incidents for each data group, creating different incidents for different groups of rows. By default, data groups are ignored for grouping data quality issues into data quality incidents.|boolean| | | |
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


## TableRecurringChecksSpec  
Container of table level recurring, divided by the time window (daily, monthly, etc.)  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](#tabledailyrecurringcheckcategoriesspec)|Configuration of daily recurring evaluated at a table level.|[TableDailyRecurringCheckCategoriesSpec](#tabledailyrecurringcheckcategoriesspec)| | | |
|[monthly](#tablemonthlyrecurringcheckcategoriesspec)|Configuration of monthly recurring evaluated at a table level.|[TableMonthlyRecurringCheckCategoriesSpec](#tablemonthlyrecurringcheckcategoriesspec)| | | |









___  
_

## TableComparisonMonthlyRecurringChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the monthly recurring comparison checks for each configured reference table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|access_order||boolean| | | |
|size||integer| | | |
|mod_count||integer| | | |
|threshold||integer| | | |









___  

## TableComparisonMonthlyRecurringChecksSpec  
Container of built-in comparison (accuracy) checks on a table level that are using a defined comparison to identify the reference table and the data grouping configuration.
 Contains the monthly recurring comparison checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_row_count_match](#tablecomparisonrowcountmatchcheckspec)|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each month when the data quality check was evaluated.|[TableComparisonRowCountMatchCheckSpec](#tablecomparisonrowcountmatchcheckspec)| | | |









___  

## TablePartitionedChecksRootSpec  
Container of table level partitioned checks, divided by the time window (daily, monthly, etc.)  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](#tabledailypartitionedcheckcategoriesspec)|Configuration of day partitioned data quality checks evaluated at a table level.|[TableDailyPartitionedCheckCategoriesSpec](#tabledailypartitionedcheckcategoriesspec)| | | |
|[monthly](#tablemonthlypartitionedcheckcategoriesspec)|Configuration of monthly partitioned data quality checks evaluated at a table level..|[TableMonthlyPartitionedCheckCategoriesSpec](#tablemonthlypartitionedcheckcategoriesspec)| | | |









___  


## TableComparisonMonthlyPartitionedChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the monthly partitioned comparison checks for each configured reference table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|access_order||boolean| | | |
|size||integer| | | |
|mod_count||integer| | | |
|threshold||integer| | | |









___  

## TableComparisonMonthlyPartitionedChecksSpec  
Container of built-in comparison (accuracy) checks on a table level that are using a defined comparison to identify the reference table and the data grouping configuration.
 Contains the monthly partitioned comparison checks, comparing each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_row_count_match](#tablecomparisonrowcountmatchcheckspec)|Verifies that the row count of the tested (parent) table matches the row count of the reference table, for each monthly partition (grouping rows by the time period, truncated to the month). Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each monthly partition and optionally data groups.|[TableComparisonRowCountMatchCheckSpec](#tablecomparisonrowcountmatchcheckspec)| | | |









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
|precision|Precision of a numeric (decimal) data type.|integer| | | |
|scale|Scale of a numeric (decimal) data type.|integer| | | |









___  


## ColumnRecurringChecksRootSpec  
Container of column level recurring, divided by the time window (daily, monthly, etc.)  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](#columndailyrecurringcheckcategoriesspec)|Configuration of daily recurring evaluated at a column level.|[ColumnDailyRecurringCheckCategoriesSpec](#columndailyrecurringcheckcategoriesspec)| | | |
|[monthly](#columnmonthlyrecurringcheckcategoriesspec)|Configuration of monthly recurring evaluated at a column level.|[ColumnMonthlyRecurringCheckCategoriesSpec](#columnmonthlyrecurringcheckcategoriesspec)| | | |









___  

## ColumnPartitionedChecksRootSpec  
Container of column level partitioned checks, divided by the time window (daily, monthly, etc.)  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](#columndailypartitionedcheckcategoriesspec)|Configuration of day partitioned data quality checks evaluated at a column level.|[ColumnDailyPartitionedCheckCategoriesSpec](#columndailypartitionedcheckcategoriesspec)| | | |
|[monthly](#columnmonthlypartitionedcheckcategoriesspec)|Configuration of monthly partitioned data quality checks evaluated at a column level.|[ColumnMonthlyPartitionedCheckCategoriesSpec](#columnmonthlypartitionedcheckcategoriesspec)| | | |









___  

## ColumnStatisticsCollectorsRootCategoriesSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](#columnnullsstatisticscollectorsspec)|Configuration of null values profilers on a column level.|[ColumnNullsStatisticsCollectorsSpec](#columnnullsstatisticscollectorsspec)| | | |
|[strings](#columnstringsstatisticscollectorsspec)|Configuration of string (text) profilers on a column level.|[ColumnStringsStatisticsCollectorsSpec](#columnstringsstatisticscollectorsspec)| | | |
|[uniqueness](#columnuniquenessstatisticscollectorsspec)|Configuration of profilers that analyse uniqueness of values (distinct count).|[ColumnUniquenessStatisticsCollectorsSpec](#columnuniquenessstatisticscollectorsspec)| | | |
|[range](#columnrangestatisticscollectorsspec)|Configuration of profilers that analyse the range of values (min, max).|[ColumnRangeStatisticsCollectorsSpec](#columnrangestatisticscollectorsspec)| | | |
|[sampling](#columnsamplingstatisticscollectorsspec)|Configuration of profilers that collect the column samples.|[ColumnSamplingStatisticsCollectorsSpec](#columnsamplingstatisticscollectorsspec)| | | |









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
|[distinct_count](#columnuniquenessdistinctcountstatisticscollectorspec)|Configuration of the profiler that counts distinct column values.|[ColumnUniquenessDistinctCountStatisticsCollectorSpec](#columnuniquenessdistinctcountstatisticscollectorspec)| | | |
|[distinct_percent](#columnuniquenessdistinctpercentstatisticscollectorspec)|Configuration of the profiler that measure the percentage of distinct column values.|[ColumnUniquenessDistinctPercentStatisticsCollectorSpec](#columnuniquenessdistinctpercentstatisticscollectorspec)| | | |
|[duplicate_count](#columnuniquenessduplicatecountstatisticscollectorspec)|Configuration of the profiler that counts duplicate column values.|[ColumnUniquenessDuplicateCountStatisticsCollectorSpec](#columnuniquenessduplicatecountstatisticscollectorspec)| | | |
|[duplicate_percent](#columnuniquenessduplicatepercentstatisticscollectorspec)|Configuration of the profiler that measure the percentage of duplicate column values.|[ColumnUniquenessDuplicatePercentStatisticsCollectorSpec](#columnuniquenessduplicatepercentstatisticscollectorspec)| | | |









___  

## ColumnUniquenessDistinctCountStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnuniquenessdistinctcountsensorparametersspec)|Profiler parameters|[ColumnUniquenessDistinctCountSensorParametersSpec](#columnuniquenessdistinctcountsensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnUniquenessDistinctPercentStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnuniquenessdistinctpercentsensorparametersspec)|Profiler parameters|[ColumnUniquenessDistinctPercentSensorParametersSpec](#columnuniquenessdistinctpercentsensorparametersspec)| | | |
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

## ColumnSamplingStatisticsCollectorsSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[column_samples](#columnsamplingcolumnsamplesstatisticscollectorspec)|Configuration of the profiler that finds the maximum string length.|[ColumnSamplingColumnSamplesStatisticsCollectorSpec](#columnsamplingcolumnsamplesstatisticscollectorspec)| | | |









___  

## ColumnSamplingColumnSamplesStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnsamplingcolumnsamplessensorparametersspec)|Profiler parameters|[ColumnSamplingColumnSamplesSensorParametersSpec](#columnsamplingcolumnsamplessensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnSamplingColumnSamplesSensorParametersSpec  
Column level sensor that retrieves a column value samples. Column value sampling is used in profiling and in capturing error samples for failed data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|limit|The limit of results that are returned. The default value is 10 sample values with the highest count (the most popular).|integer| | |10<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

