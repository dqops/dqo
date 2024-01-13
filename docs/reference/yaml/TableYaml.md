# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## TableIncidentGroupingSpec
Configuration of data quality incident grouping on a table level. Defines how similar data quality issues are grouped into incidents.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|grouping_level|Grouping level of failed data quality checks for creating higher level data quality incidents. The default grouping level is by a table, a data quality dimension and a check category (i.e. a datatype data quality incident detected on a table X in the numeric checks category).|enum|table_dimension_category_name<br/>table_dimension<br/>table_dimension_category_type<br/>table<br/>table_dimension_category<br/>| | |
|minimum_severity|Minimum severity level of data quality issues that are grouped into incidents. The default minimum severity level is &#x27;warning&#x27;. Other supported severity levels are &#x27;error&#x27; and &#x27;fatal&#x27;.|enum|warning<br/>error<br/>fatal<br/>| | |
|divide_by_data_group|Create separate data quality incidents for each data group, creating different incidents for different groups of rows. By default, data groups are ignored for grouping data quality issues into data quality incidents.|boolean| | | |
|disabled|Disables data quality incident creation for failed data quality checks on the table.|boolean| | | |









___


## ColumnRangeMinValueStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](./TableYaml.md#ColumnRangeMinValueSensorParametersSpec)|Profiler parameters|[ColumnRangeMinValueSensorParametersSpec](./TableYaml.md#ColumnRangeMinValueSensorParametersSpec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## ColumnRangeMaxValueStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](./TableYaml.md#ColumnRangeMaxValueSensorParametersSpec)|Profiler parameters|[ColumnRangeMaxValueSensorParametersSpec](./TableYaml.md#ColumnRangeMaxValueSensorParametersSpec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## TableMonitoringChecksSpec
Container of table level monitoring, divided by the time window (daily, monthly, etc.)









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](./monitoring/table-daily-monitoring-checks.md#TableDailyMonitoringCheckCategoriesSpec)|Configuration of daily monitoring evaluated at a table level.|[TableDailyMonitoringCheckCategoriesSpec](./monitoring/table-daily-monitoring-checks.md#TableDailyMonitoringCheckCategoriesSpec)| | | |
|[monthly](./monitoring/table-monthly-monitoring-checks.md#TableMonthlyMonitoringCheckCategoriesSpec)|Configuration of monthly monitoring evaluated at a table level.|[TableMonthlyMonitoringCheckCategoriesSpec](./monitoring/table-monthly-monitoring-checks.md#TableMonthlyMonitoringCheckCategoriesSpec)| | | |









___


## TableSchemaColumnCountStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../sensors/table/schema-table-sensors.md#column-count)|Profiler parameters|[TableColumnCountSensorParametersSpec](../sensors/table/schema-table-sensors.md#column-count)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## ColumnUniquenessStatisticsCollectorsSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[distinct_count](./TableYaml.md#ColumnUniquenessDistinctCountStatisticsCollectorSpec)|Configuration of the profiler that counts distinct column values.|[ColumnUniquenessDistinctCountStatisticsCollectorSpec](./TableYaml.md#ColumnUniquenessDistinctCountStatisticsCollectorSpec)| | | |
|[distinct_percent](./TableYaml.md#ColumnUniquenessDistinctPercentStatisticsCollectorSpec)|Configuration of the profiler that measure the percentage of distinct column values.|[ColumnUniquenessDistinctPercentStatisticsCollectorSpec](./TableYaml.md#ColumnUniquenessDistinctPercentStatisticsCollectorSpec)| | | |
|[duplicate_count](./TableYaml.md#ColumnUniquenessDuplicateCountStatisticsCollectorSpec)|Configuration of the profiler that counts duplicate column values.|[ColumnUniquenessDuplicateCountStatisticsCollectorSpec](./TableYaml.md#ColumnUniquenessDuplicateCountStatisticsCollectorSpec)| | | |
|[duplicate_percent](./TableYaml.md#ColumnUniquenessDuplicatePercentStatisticsCollectorSpec)|Configuration of the profiler that measure the percentage of duplicate column values.|[ColumnUniquenessDuplicatePercentStatisticsCollectorSpec](./TableYaml.md#ColumnUniquenessDuplicatePercentStatisticsCollectorSpec)| | | |









___


## ColumnTextStatisticsCollectorsSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[text_max_length](./TableYaml.md#ColumnTextTextMaxLengthStatisticsCollectorSpec)|Configuration of the profiler that finds the maximum text length.|[ColumnTextTextMaxLengthStatisticsCollectorSpec](./TableYaml.md#ColumnTextTextMaxLengthStatisticsCollectorSpec)| | | |
|[text_mean_length](./TableYaml.md#ColumnTextTextMeanLengthStatisticsCollectorSpec)|Configuration of the profiler that finds the mean text length.|[ColumnTextTextMeanLengthStatisticsCollectorSpec](./TableYaml.md#ColumnTextTextMeanLengthStatisticsCollectorSpec)| | | |
|[text_min_length](./TableYaml.md#ColumnTextTextMinLengthStatisticsCollectorSpec)|Configuration of the profiler that finds the min text length.|[ColumnTextTextMinLengthStatisticsCollectorSpec](./TableYaml.md#ColumnTextTextMinLengthStatisticsCollectorSpec)| | | |
|[text_datatype_detect](./TableYaml.md#ColumnTextTextDatatypeDetectStatisticsCollectorSpec)|Configuration of the profiler that detects datatype.|[ColumnTextTextDatatypeDetectStatisticsCollectorSpec](./TableYaml.md#ColumnTextTextDatatypeDetectStatisticsCollectorSpec)| | | |









___


## ColumnUniquenessDuplicateCountStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../sensors/column/uniqueness-column-sensors.md#duplicate-count)|Profiler parameters|[ColumnUniquenessDuplicateCountSensorParametersSpec](../sensors/column/uniqueness-column-sensors.md#duplicate-count)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## ColumnStatisticsCollectorsRootCategoriesSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](./TableYaml.md#ColumnNullsStatisticsCollectorsSpec)|Configuration of null values profilers on a column level.|[ColumnNullsStatisticsCollectorsSpec](./TableYaml.md#ColumnNullsStatisticsCollectorsSpec)| | | |
|[text](./TableYaml.md#ColumnTextStatisticsCollectorsSpec)|Configuration of text column profilers on a column level.|[ColumnTextStatisticsCollectorsSpec](./TableYaml.md#ColumnTextStatisticsCollectorsSpec)| | | |
|[uniqueness](./TableYaml.md#ColumnUniquenessStatisticsCollectorsSpec)|Configuration of profilers that analyse uniqueness of values (distinct count).|[ColumnUniquenessStatisticsCollectorsSpec](./TableYaml.md#ColumnUniquenessStatisticsCollectorsSpec)| | | |
|[range](./TableYaml.md#ColumnRangeStatisticsCollectorsSpec)|Configuration of profilers that analyse the range of values (min, max).|[ColumnRangeStatisticsCollectorsSpec](./TableYaml.md#ColumnRangeStatisticsCollectorsSpec)| | | |
|[sampling](./TableYaml.md#ColumnSamplingStatisticsCollectorsSpec)|Configuration of profilers that collect the column samples.|[ColumnSamplingStatisticsCollectorsSpec](./TableYaml.md#ColumnSamplingStatisticsCollectorsSpec)| | | |









___


## ColumnRangeStatisticsCollectorsSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[min_value](./TableYaml.md#ColumnRangeMinValueStatisticsCollectorSpec)|Configuration of the profiler that finds the minimum value in the column.|[ColumnRangeMinValueStatisticsCollectorSpec](./TableYaml.md#ColumnRangeMinValueStatisticsCollectorSpec)| | | |
|[median_value](./TableYaml.md#ColumnRangeMedianValueStatisticsCollectorSpec)|Configuration of the profiler that finds the median value in the column.|[ColumnRangeMedianValueStatisticsCollectorSpec](./TableYaml.md#ColumnRangeMedianValueStatisticsCollectorSpec)| | | |
|[max_value](./TableYaml.md#ColumnRangeMaxValueStatisticsCollectorSpec)|Configuration of the profiler that finds the maximum value in the column.|[ColumnRangeMaxValueStatisticsCollectorSpec](./TableYaml.md#ColumnRangeMaxValueStatisticsCollectorSpec)| | | |
|[sum_value](./TableYaml.md#ColumnRangeSumValueStatisticsCollectorSpec)|Configuration of the profiler that finds the sum value in the column.|[ColumnRangeSumValueStatisticsCollectorSpec](./TableYaml.md#ColumnRangeSumValueStatisticsCollectorSpec)| | | |









___


## ColumnRangeMedianValueStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../sensors/column/numeric-column-sensors.md#percentile)|Profiler parameters|[ColumnNumericMedianSensorParametersSpec](../sensors/column/numeric-column-sensors.md#percentile)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## ColumnNullsStatisticsCollectorsSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls_count](./TableYaml.md#ColumnNullsNullsCountStatisticsCollectorSpec)|Configuration of the profiler that counts null column values.|[ColumnNullsNullsCountStatisticsCollectorSpec](./TableYaml.md#ColumnNullsNullsCountStatisticsCollectorSpec)| | | |
|[nulls_percent](./TableYaml.md#ColumnNullsNullsPercentStatisticsCollectorSpec)|Configuration of the profiler that measures the percentage of null values.|[ColumnNullsNullsPercentStatisticsCollectorSpec](./TableYaml.md#ColumnNullsNullsPercentStatisticsCollectorSpec)| | | |
|[not_nulls_count](./TableYaml.md#ColumnNullsNotNullsCountStatisticsCollectorSpec)|Configuration of the profiler that counts not null column values.|[ColumnNullsNotNullsCountStatisticsCollectorSpec](./TableYaml.md#ColumnNullsNotNullsCountStatisticsCollectorSpec)| | | |
|[not_nulls_percent](./TableYaml.md#ColumnNullsNotNullsPercentStatisticsCollectorSpec)|Configuration of the profiler that measures the percentage of not null values.|[ColumnNullsNotNullsPercentStatisticsCollectorSpec](./TableYaml.md#ColumnNullsNotNullsPercentStatisticsCollectorSpec)| | | |









___


## ColumnTextTextMinLengthStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../sensors/column/text-column-sensors.md#text-min-length)|Profiler parameters|[ColumnTextTextMinLengthSensorParametersSpec](../sensors/column/text-column-sensors.md#text-min-length)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## TableComparisonGroupingColumnsPairsListSpec
List of column pairs used for grouping and joining in the table comparison checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||List[[TableComparisonGroupingColumnsPairSpec](./TableYaml.md#TableComparisonGroupingColumnsPairSpec)]| | | |









___


## ColumnTextTextMeanLengthStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../sensors/column/text-column-sensors.md#text-mean-length)|Profiler parameters|[ColumnTextTextMeanLengthSensorParametersSpec](../sensors/column/text-column-sensors.md#text-mean-length)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## ColumnRangeMaxValueSensorParametersSpec
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___


## ColumnNullsNotNullsCountStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../sensors/column/nulls-column-sensors.md#not-null-count)|Profiler parameters|[ColumnNullsNotNullsCountSensorParametersSpec](../sensors/column/nulls-column-sensors.md#not-null-count)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## ColumnMonitoringChecksRootSpec
Container of column level monitoring, divided by the time window (daily, monthly, etc.)









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](./monitoring/column-daily-monitoring-checks.md#ColumnDailyMonitoringCheckCategoriesSpec)|Configuration of daily monitoring evaluated at a column level.|[ColumnDailyMonitoringCheckCategoriesSpec](./monitoring/column-daily-monitoring-checks.md#ColumnDailyMonitoringCheckCategoriesSpec)| | | |
|[monthly](./monitoring/column-monthly-monitoring-checks.md#ColumnMonthlyMonitoringCheckCategoriesSpec)|Configuration of monthly monitoring evaluated at a column level.|[ColumnMonthlyMonitoringCheckCategoriesSpec](./monitoring/column-monthly-monitoring-checks.md#ColumnMonthlyMonitoringCheckCategoriesSpec)| | | |









___


## TablePartitionedChecksRootSpec
Container of table level partitioned checks, divided by the time window (daily, monthly, etc.)









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](./partitioned/table-daily-partitioned-checks.md#TableDailyPartitionedCheckCategoriesSpec)|Configuration of day partitioned data quality checks evaluated at a table level.|[TableDailyPartitionedCheckCategoriesSpec](./partitioned/table-daily-partitioned-checks.md#TableDailyPartitionedCheckCategoriesSpec)| | | |
|[monthly](./partitioned/table-monthly-partitioned-checks.md#TableMonthlyPartitionedCheckCategoriesSpec)|Configuration of monthly partitioned data quality checks evaluated at a table level..|[TableMonthlyPartitionedCheckCategoriesSpec](./partitioned/table-monthly-partitioned-checks.md#TableMonthlyPartitionedCheckCategoriesSpec)| | | |









___


## TableSpec
Table specification that defines data quality tests that are enabled on a table and its columns.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|disabled|Disables all data quality checks on the table. Data quality checks will not be executed.|boolean| | | |
|stage|Stage name.|string| | | |
|priority|Table priority (1, 2, 3, 4, ...). The tables could be assigned a priority level. The table priority is copied into each data quality check result and a sensor result, enabling efficient grouping of more and less important tables during a data quality improvement project, when the data quality issues on higher priority tables are fixed before data quality issues on less important tables.|integer| | | |
|filter|SQL WHERE clause added to the sensor queries. Use replacement tokens {table} to replace the content with the full table name, {alias} to replace the content with the table alias of an analyzed table or {column} to replace the content with the analyzed column name.|string| | | |
|[timestamp_columns](./TableYaml.md#TimestampColumnsSpec)|Column names that store the timestamps that identify the event (transaction) timestamp and the ingestion (inserted / loaded at) timestamps. Also configures the timestamp source for the date/time partitioned data quality checks (event timestamp or ingestion timestamp).|[TimestampColumnsSpec](./TableYaml.md#TimestampColumnsSpec)| | | |
|[incremental_time_window](./TableYaml.md#PartitionIncrementalTimeWindowSpec)|Configuration of the time window for analyzing daily or monthly partitions. Specifies the number of recent days and recent months that are analyzed when the partitioned data quality checks are run in an incremental mode (the default mode).|[PartitionIncrementalTimeWindowSpec](./TableYaml.md#PartitionIncrementalTimeWindowSpec)| | | |
|default_grouping_name|The name of the default data grouping configuration that is applied on data quality checks. When a default data grouping is selected, all data quality checks run SQL queries with a GROUP BY clause, calculating separate data quality checks for each group of data. The data groupings are defined in the &#x27;groupings&#x27; dictionary (indexed by the data grouping name).|string| | | |
|[groupings](./TableYaml.md#DataGroupingConfigurationSpecMap)|Data grouping configurations list. Data grouping configurations are configured in two cases: (1) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a &#x27;country&#x27; column used for partitioning. (2) a tag is assigned to a table (within a data grouping level hierarchy), when the data is segmented at a table level (similar tables store the same information, but for different countries, etc.).|[DataGroupingConfigurationSpecMap](./TableYaml.md#DataGroupingConfigurationSpecMap)| | | |
|[table_comparisons](./TableYaml.md#TableComparisonConfigurationSpecMap)|Dictionary of data comparison configurations. Data comparison configurations are used for cross data-source comparisons to compare this table (called the compared table) with other reference tables (the source of truth). The reference table&#x27;s metadata must be imported into DQOps, but the reference table could be located on a different data source. DQOps will compare metrics calculated for groups of rows (using a GROUP BY clause). For each comparison, the user must specify a name of a data grouping. The number of data grouping dimensions on the parent table and the reference table defined in selected data grouping configurations must match. DQOps will run the same data quality sensors on both the parent table (tested table) and the reference table (the source of truth), comparing the measures (sensor readouts) captured from both the tables.|[TableComparisonConfigurationSpecMap](./TableYaml.md#TableComparisonConfigurationSpecMap)| | | |
|[incident_grouping](./TableYaml.md#TableIncidentGroupingSpec)|Incident grouping configuration with the overridden configuration at a table level. The field value in this object that are configured will override the default configuration from the connection level. The incident grouping level could be changed or incident creation could be disabled.|[TableIncidentGroupingSpec](./TableYaml.md#TableIncidentGroupingSpec)| | | |
|[owner](./TableYaml.md#TableOwnerSpec)|Table owner information like the data steward name or the business application name.|[TableOwnerSpec](./TableYaml.md#TableOwnerSpec)| | | |
|[profiling_checks](./profiling/table-profiling-checks.md#TableProfilingCheckCategoriesSpec)|Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.|[TableProfilingCheckCategoriesSpec](./profiling/table-profiling-checks.md#TableProfilingCheckCategoriesSpec)| | | |
|[monitoring_checks](./TableYaml.md#TableMonitoringChecksSpec)|Configuration of table level monitoring checks. Monitoring checks are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A monitoring check stores only the most recent data quality check result for each period of time.|[TableMonitoringChecksSpec](./TableYaml.md#TableMonitoringChecksSpec)| | | |
|[partitioned_checks](./TableYaml.md#TablePartitionedChecksRootSpec)|Configuration of table level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.|[TablePartitionedChecksRootSpec](./TableYaml.md#TablePartitionedChecksRootSpec)| | | |
|[statistics](./TableYaml.md#TableStatisticsCollectorsRootCategoriesSpec)|Configuration of table level data statistics collector (a basic profiler). Configures which statistics collectors are enabled and how they are configured.|[TableStatisticsCollectorsRootCategoriesSpec](./TableYaml.md#TableStatisticsCollectorsRootCategoriesSpec)| | | |
|[schedules_override](./ConnectionYaml.md#DefaultSchedulesSpec)|Configuration of the job scheduler that runs data quality checks. The scheduler configuration is divided into types of checks that have different schedules.|[DefaultSchedulesSpec](./ConnectionYaml.md#DefaultSchedulesSpec)| | | |
|[columns](./TableYaml.md#ColumnSpecMap)|Dictionary of columns, indexed by a physical column name. Column specification contains the expected column data type and a list of column level data quality checks that are enabled for a column.|[ColumnSpecMap](./TableYaml.md#ColumnSpecMap)| | | |
|[labels](./ConnectionYaml.md#LabelSetSpec)|Custom labels that were assigned to the table. Labels are used for searching for tables when filtered data quality checks are executed.|[LabelSetSpec](./ConnectionYaml.md#LabelSetSpec)| | | |
|[comments](./profiling/table-profiling-checks.md#CommentsListSpec)|Comments used for change tracking and documenting changes directly in the table data quality specification file.|[CommentsListSpec](./profiling/table-profiling-checks.md#CommentsListSpec)| | | |









___


## ColumnTextTextDatatypeDetectStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../sensors/column/datatype-column-sensors.md#string-datatype-detect)|Profiler parameters|[ColumnDatatypeStringDatatypeDetectSensorParametersSpec](../sensors/column/datatype-column-sensors.md#string-datatype-detect)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## ColumnUniquenessDistinctPercentStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../sensors/column/uniqueness-column-sensors.md#distinct-percent)|Profiler parameters|[ColumnUniquenessDistinctPercentSensorParametersSpec](../sensors/column/uniqueness-column-sensors.md#distinct-percent)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## TableVolumeStatisticsCollectorsSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[row_count](./TableYaml.md#TableVolumeRowCountStatisticsCollectorSpec)|Configuration of the row count profiler.|[TableVolumeRowCountStatisticsCollectorSpec](./TableYaml.md#TableVolumeRowCountStatisticsCollectorSpec)| | | |









___


## TableSchemaStatisticsCollectorsSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[column_count](./TableYaml.md#TableSchemaColumnCountStatisticsCollectorSpec)|Configuration of the column count profiler.|[TableSchemaColumnCountStatisticsCollectorSpec](./TableYaml.md#TableSchemaColumnCountStatisticsCollectorSpec)| | | |









___


## TableComparisonConfigurationSpecMap
Dictionary of data comparison configurations between the current table (the parent of this node) and another reference table (the source of truth)
 to which we are comparing the tables to measure the accuracy of the data.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [TableComparisonConfigurationSpec](./TableYaml.md#TableComparisonConfigurationSpec)]| | | |









___


## ColumnNullsNullsCountStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../sensors/column/nulls-column-sensors.md#null-count)|Profiler parameters|[ColumnNullsNullsCountSensorParametersSpec](../sensors/column/nulls-column-sensors.md#null-count)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## ColumnSpec
Column specification that identifies a single column.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|disabled|Disables all data quality checks on the column. Data quality checks will not be executed.|boolean| | | |
|sql_expression|SQL expression used for calculated fields or when additional column value transformation is required before the column could be used analyzed in data quality checks (data type conversion, transformation). It should be an SQL expression using the SQL language of the analyzed database type. Use replacement tokens {table} to replace the content with the full table name, {alias} to replace the content with the table alias of an analyzed table or {column} to replace the content with the analyzed column name. An example to extract a value from a string column that stores a JSON in PostgreSQL: &quot;{column}::json-&gt;&#x27;address&#x27;-&gt;&#x27;zip&#x27;&quot;.|string| | | |
|[type_snapshot](./TableYaml.md#ColumnTypeSnapshotSpec)|Column data type that was retrieved when the table metadata was imported.|[ColumnTypeSnapshotSpec](./TableYaml.md#ColumnTypeSnapshotSpec)| | | |
|[profiling_checks](./profiling/column-profiling-checks.md#ColumnProfilingCheckCategoriesSpec)|Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.|[ColumnProfilingCheckCategoriesSpec](./profiling/column-profiling-checks.md#ColumnProfilingCheckCategoriesSpec)| | | |
|[monitoring_checks](./TableYaml.md#ColumnMonitoringChecksRootSpec)|Configuration of column level monitoring checks. Monitoring are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A monitoring stores only the most recent data quality check result for each period of time.|[ColumnMonitoringChecksRootSpec](./TableYaml.md#ColumnMonitoringChecksRootSpec)| | | |
|[partitioned_checks](./TableYaml.md#ColumnPartitionedChecksRootSpec)|Configuration of column level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.|[ColumnPartitionedChecksRootSpec](./TableYaml.md#ColumnPartitionedChecksRootSpec)| | | |
|[statistics](./TableYaml.md#ColumnStatisticsCollectorsRootCategoriesSpec)|Custom configuration of a column level statistics collector (a basic profiler). Enables customization of the statistics collector settings when the collector is analysing this column.|[ColumnStatisticsCollectorsRootCategoriesSpec](./TableYaml.md#ColumnStatisticsCollectorsRootCategoriesSpec)| | | |
|[labels](./ConnectionYaml.md#LabelSetSpec)|Custom labels that were assigned to the column. Labels are used for searching for columns when filtered data quality checks are executed.|[LabelSetSpec](./ConnectionYaml.md#LabelSetSpec)| | | |
|[comments](./profiling/table-profiling-checks.md#CommentsListSpec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](./profiling/table-profiling-checks.md#CommentsListSpec)| | | |









___


## ColumnUniquenessDistinctCountStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../sensors/column/uniqueness-column-sensors.md#distinct-count)|Profiler parameters|[ColumnUniquenessDistinctCountSensorParametersSpec](../sensors/column/uniqueness-column-sensors.md#distinct-count)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## TableYaml
Table and column definition file that defines a list of tables and columns that are covered by data quality checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|default_schedules<br/>settings<br/>default_notifications<br/>rule<br/>sensor<br/>source<br/>check<br/>dashboards<br/>default_checks<br/>table<br/>provider_sensor<br/>file_index<br/>| | |
|[spec](./TableYaml.md#TableSpec)||[TableSpec](./TableYaml.md#TableSpec)| | | |









___


## ColumnTypeSnapshotSpec
Stores the column data type captured at the time of the table metadata import.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|column_type|Column data type using the monitored database type names.|string| | | |
|nullable|Column is nullable.|boolean| | | |
|length|Maximum length of text and binary columns.|integer| | | |
|precision|Precision of a numeric (decimal) data type.|integer| | | |
|scale|Scale of a numeric (decimal) data type.|integer| | | |









___


## ColumnNullsNullsPercentStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../sensors/column/nulls-column-sensors.md#null-percent)|Profiler parameters|[ColumnNullsNullsPercentSensorParametersSpec](../sensors/column/nulls-column-sensors.md#null-percent)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## ColumnTextTextMaxLengthStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../sensors/column/text-column-sensors.md#text-max-length)|Profiler parameters|[ColumnTextTextMaxLengthSensorParametersSpec](../sensors/column/text-column-sensors.md#text-max-length)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## ColumnSamplingColumnSamplesStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../sensors/column/sampling-column-sensors.md#column-samples)|Profiler parameters|[ColumnSamplingColumnSamplesSensorParametersSpec](../sensors/column/sampling-column-sensors.md#column-samples)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## DataGroupingConfigurationSpecMap
Dictionary of named data grouping configurations defined on a table level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [DataGroupingConfigurationSpec](./ConnectionYaml.md#DataGroupingConfigurationSpec)]| | | |









___


## ColumnNullsNotNullsPercentStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../sensors/column/nulls-column-sensors.md#not-null-percent)|Profiler parameters|[ColumnNullsNotNullsPercentSensorParametersSpec](../sensors/column/nulls-column-sensors.md#not-null-percent)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## ColumnRangeMinValueSensorParametersSpec
Column level sensor that finds the minimum value. It works on any data type that supports the MIN functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___


## TableComparisonGroupingColumnsPairSpec
Configuration of a pair of columns on the compared table and the reference table (the source of truth) that are joined
 and used for grouping to perform data comparison of aggregated results (sums of columns, row counts, etc.).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|compared_table_column_name|The name of the column on the compared table (the parent table) that is used in the GROUP BY clause to group rows before compared aggregates (row counts, sums, etc.) are calculated. This column is also used to join (match) results to the reference table.|string| | | |
|reference_table_column_name|The name of the column on the reference table (the source of truth) that is used in the GROUP BY clause to group rows before compared aggregates (row counts, sums, etc.) are calculated. This column is also used to join (match) results to the compared table.|string| | | |









___


## TableOwnerSpec
Table owner information.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|data_steward|Data steward name|string| | | |
|application|Business application name|string| | | |









___


## ColumnSamplingStatisticsCollectorsSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[column_samples](./TableYaml.md#ColumnSamplingColumnSamplesStatisticsCollectorSpec)|Configuration of the profiler that finds the maximum string length.|[ColumnSamplingColumnSamplesStatisticsCollectorSpec](./TableYaml.md#ColumnSamplingColumnSamplesStatisticsCollectorSpec)| | | |









___


## PartitionIncrementalTimeWindowSpec
Configuration of the time window for running incremental partition checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|daily_partitioning_recent_days|Number of recent days that are analyzed by daily partitioned checks in incremental mode. The default value is 7 days back.|integer| | | |
|daily_partitioning_include_today|Analyze also today&#x27;s data by daily partitioned checks in incremental mode. The default value is false, which means that the today&#x27;s and the future partitions are not analyzed, only yesterday&#x27;s partition and earlier daily partitions are analyzed because today&#x27;s data could be still incomplete. Change the value to &#x27;true&#x27; if the current day should be also analyzed. The change may require configuring the schedule for daily checks correctly, to run after the data load.|boolean| | | |
|monthly_partitioning_recent_months|Number of recent months that are analyzed by monthly partitioned checks in incremental mode. The default value is 1 month back which means the previous calendar month.|integer| | | |
|monthly_partitioning_include_current_month|Analyze also this month&#x27;s data by monthly partitioned checks in incremental mode. The default value is false, which means that the current month is not analyzed and future data is also filtered out because the current month could be incomplete. Set the value to &#x27;true&#x27; if the current month should be analyzed before the end of the month. The schedule for running monthly checks should be also configured to run more frequently (daily, hourly, etc.).|boolean| | | |









___


## TableVolumeRowCountStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../sensors/table/volume-table-sensors.md#row-count)|Profiler parameters|[TableVolumeRowCountSensorParametersSpec](../sensors/table/volume-table-sensors.md#row-count)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## ColumnSpecMap
Dictionary of columns indexed by a physical column name.















___


## ColumnPartitionedChecksRootSpec
Container of column level partitioned checks, divided by the time window (daily, monthly, etc.)









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](./partitioned/column-daily-partitioned-checks.md#ColumnDailyPartitionedCheckCategoriesSpec)|Configuration of day partitioned data quality checks evaluated at a column level.|[ColumnDailyPartitionedCheckCategoriesSpec](./partitioned/column-daily-partitioned-checks.md#ColumnDailyPartitionedCheckCategoriesSpec)| | | |
|[monthly](./partitioned/column-monthly-partitioned-checks.md#ColumnMonthlyPartitionedCheckCategoriesSpec)|Configuration of monthly partitioned data quality checks evaluated at a column level.|[ColumnMonthlyPartitionedCheckCategoriesSpec](./partitioned/column-monthly-partitioned-checks.md#ColumnMonthlyPartitionedCheckCategoriesSpec)| | | |









___


## TableComparisonConfigurationSpec
Identifies a data comparison configuration between a parent table (the compared table) and the target table from another data source (a reference table).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|reference_table_connection_name|The name of the connection in DQOp where the reference table (the source of truth) is configured. When the connection name is not provided, DQOps will find the reference table on the connection of the parent table.|string| | | |
|reference_table_schema_name|The name of the schema where the reference table is imported into DQOps. The reference table&#x27;s metadata must be imported into DQOps.|string| | | |
|reference_table_name|The name of the reference table that is imported into DQOps. The reference table&#x27;s metadata must be imported into DQOps.|string| | | |
|compared_table_filter|Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the compared table. This expression must be a SQL expression that will be added to the WHERE clause when querying the compared table.|string| | | |
|reference_table_filter|Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the reference table (the source of truth). This expression must be a SQL expression that will be added to the WHERE clause when querying the reference table.|string| | | |
|check_type|The type of checks (profiling, monitoring, partitioned) that this check comparison configuration is applicable. The default value is &#x27;profiling&#x27;.|enum|profiling<br/>partitioned<br/>monitoring<br/>| | |
|time_scale|The time scale that this check comparison configuration is applicable. Supported values are &#x27;daily&#x27; and &#x27;monthly&#x27; for monitoring and partitioned checks or an empty value for profiling checks.|enum|daily<br/>monthly<br/>| | |
|[grouping_columns](./TableYaml.md#TableComparisonGroupingColumnsPairsListSpec)|List of column pairs from both the compared table and the reference table that are used in a GROUP BY clause  for grouping both the compared table and the reference table (the source of truth). The columns are used in the next of the table comparison to join the results of data groups (row counts, sums of columns) between the compared table and the reference table to compare the differences.|[TableComparisonGroupingColumnsPairsListSpec](./TableYaml.md#TableComparisonGroupingColumnsPairsListSpec)| | | |









___


## TimestampColumnsSpec
Configuration of timestamp related columns on a table level.
 Timestamp columns are used for timeliness data quality checks and for date/time partitioned checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|event_timestamp_column|Column name that identifies an event timestamp (date/time), such as a transaction timestamp, impression timestamp, event timestamp.|string| | | |
|ingestion_timestamp_column|Column name that contains the timestamp (or date/time) when the row was ingested (loaded, inserted) into the table. Use a column that is filled by the data pipeline or ETL process at the time of the data loading.|string| | | |
|partition_by_column|Column name that contains the date, datetime or timestamp column for date/time partitioned data. Partition checks (daily partition checks and monthly partition checks) use this column in a GROUP BY clause in order to detect data quality issues in each partition separately. It should be a DATE type, DATETIME type (using a local server time zone) or a TIMESTAMP type (a UTC absolute time).|string| | | |









___


## ColumnUniquenessDuplicatePercentStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../sensors/column/uniqueness-column-sensors.md#duplicate-percent)|Profiler parameters|[ColumnUniquenessDuplicatePercentSensorParametersSpec](../sensors/column/uniqueness-column-sensors.md#duplicate-percent)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## ColumnRangeSumValueStatisticsCollectorSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../sensors/column/numeric-column-sensors.md#sum)|Profiler parameters|[ColumnNumericSumSensorParametersSpec](../sensors/column/numeric-column-sensors.md#sum)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___


## TableStatisticsCollectorsRootCategoriesSpec










The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](./TableYaml.md#TableVolumeStatisticsCollectorsSpec)|Configuration of volume statistics collectors on a table level.|[TableVolumeStatisticsCollectorsSpec](./TableYaml.md#TableVolumeStatisticsCollectorsSpec)| | | |
|[schema](./TableYaml.md#TableSchemaStatisticsCollectorsSpec)||[TableSchemaStatisticsCollectorsSpec](./TableYaml.md#TableSchemaStatisticsCollectorsSpec)| | | |









___


