
## TableComparisonConfigurationSpecMap  
Dictionary of data comparison configurations between the current table (the parent of this node) and another reference table (the source of truth)
 to which we are comparing the tables to measure the accuracy of the data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [TableComparisonConfigurationSpec](/docs/reference/yaml/tableyaml/#tablecomparisonconfigurationspec)]| | | |









___  

## ColumnSpec  
Column specification that identifies a single column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|disabled|Disables all data quality checks on the column. Data quality checks will not be executed.|boolean| | | |
|sql_expression|SQL expression used for calculated fields or when additional column value transformation is required before the column could be used analyzed in data quality checks (data type conversion, transformation). It should be an SQL expression using the SQL language of the analyzed database type. Use replacement tokens {table} to replace the content with the full table name, {alias} to replace the content with the table alias of an analyzed table or {column} to replace the content with the analyzed column name. An example to extract a value from a string column that stores a JSON in PostgreSQL: &quot;{column}::json-&gt;&#x27;address&#x27;-&gt;&#x27;zip&#x27;&quot;.|string| | | |
|[type_snapshot](/docs/reference/yaml/tableyaml/#columntypesnapshotspec)|Column data type that was retrieved when the table metadata was imported.|[ColumnTypeSnapshotSpec](/docs/reference/yaml/tableyaml/#columntypesnapshotspec)| | | |
|[profiling_checks](/docs/reference/yaml/profiling/column-profiling-checks/#columnprofilingcheckcategoriesspec)|Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.|[ColumnProfilingCheckCategoriesSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnprofilingcheckcategoriesspec)| | | |
|[monitoring_checks](/docs/reference/yaml/tableyaml/#columnmonitoringchecksrootspec)|Configuration of column level monitoring checks. Monitoring are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A monitoring stores only the most recent data quality check result for each period of time.|[ColumnMonitoringChecksRootSpec](/docs/reference/yaml/tableyaml/#columnmonitoringchecksrootspec)| | | |
|[partitioned_checks](/docs/reference/yaml/tableyaml/#columnpartitionedchecksrootspec)|Configuration of column level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.|[ColumnPartitionedChecksRootSpec](/docs/reference/yaml/tableyaml/#columnpartitionedchecksrootspec)| | | |
|[statistics](/docs/reference/yaml/tableyaml/#columnstatisticscollectorsrootcategoriesspec)|Custom configuration of a column level statistics collector (a basic profiler). Enables customization of the statistics collector settings when the collector is analysing this column.|[ColumnStatisticsCollectorsRootCategoriesSpec](/docs/reference/yaml/tableyaml/#columnstatisticscollectorsrootcategoriesspec)| | | |
|[labels](/docs/reference/yaml/connectionyaml/#labelsetspec)|Custom labels that were assigned to the column. Labels are used for searching for columns when filtered data quality checks are executed.|[LabelSetSpec](/docs/reference/yaml/connectionyaml/#labelsetspec)| | | |
|[comments](/docs/reference/yaml/profiling/table-profiling-checks/#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](/docs/reference/yaml/profiling/table-profiling-checks/#commentslistspec)| | | |









___  

## TableStatisticsCollectorsRootCategoriesSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](/docs/reference/yaml/tableyaml/#tablevolumestatisticscollectorsspec)|Configuration of volume statistics collectors on a table level.|[TableVolumeStatisticsCollectorsSpec](/docs/reference/yaml/tableyaml/#tablevolumestatisticscollectorsspec)| | | |
|[schema](/docs/reference/yaml/tableyaml/#tableschemastatisticscollectorsspec)||[TableSchemaStatisticsCollectorsSpec](/docs/reference/yaml/tableyaml/#tableschemastatisticscollectorsspec)| | | |









___  

## ColumnMonitoringChecksRootSpec  
Container of column level monitoring, divided by the time window (daily, monthly, etc.)  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columndailymonitoringcheckcategoriesspec)|Configuration of daily monitoring evaluated at a column level.|[ColumnDailyMonitoringCheckCategoriesSpec](/docs/reference/yaml/monitoring/column-daily-monitoring-checks/#columndailymonitoringcheckcategoriesspec)| | | |
|[monthly](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnmonthlymonitoringcheckcategoriesspec)|Configuration of monthly monitoring evaluated at a column level.|[ColumnMonthlyMonitoringCheckCategoriesSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnmonthlymonitoringcheckcategoriesspec)| | | |









___  

## ColumnSamplingStatisticsCollectorsSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[column_samples](/docs/reference/yaml/tableyaml/#columnsamplingcolumnsamplesstatisticscollectorspec)|Configuration of the profiler that finds the maximum string length.|[ColumnSamplingColumnSamplesStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#columnsamplingcolumnsamplesstatisticscollectorspec)| | | |









___  

## TableComparisonGroupingColumnsPairSpec  
Configuration of a pair of columns on the compared table and the reference table (the source of truth) that are joined
 and used for grouping to perform data comparison of aggregated results (sums of columns, row counts, etc.).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|compared_table_column_name|The name of the column on the compared table (the parent table) that is used in the GROUP BY clause to group rows before compared aggregates (row counts, sums, etc.) are calculated. This column is also used to join (match) results to the reference table.|string| | | |
|reference_table_column_name|The name of the column on the reference table (the source of truth) that is used in the GROUP BY clause to group rows before compared aggregates (row counts, sums, etc.) are calculated. This column is also used to join (match) results to the compared table.|string| | | |









___  

## TableOwnerSpec  
Table owner information.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|data_steward|Data steward name|string| | | |
|application|Business application name|string| | | |









___  

## ColumnStringsStringDatatypeDetectStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/sensors/column/datatype-column-sensors/#string-datatype-detect)|Profiler parameters|[ColumnDatatypeStringDatatypeDetectSensorParametersSpec](/docs/reference/sensors/column/datatype-column-sensors/#string-datatype-detect)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnRangeMaxValueStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/yaml/tableyaml/#columnrangemaxvaluesensorparametersspec)|Profiler parameters|[ColumnRangeMaxValueSensorParametersSpec](/docs/reference/yaml/tableyaml/#columnrangemaxvaluesensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## TimestampColumnsSpec  
Configuration of timestamp related columns on a table level.
 Timestamp columns are used for timeliness data quality checks and for date/time partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|event_timestamp_column|Column name that identifies an event timestamp (date/time), such as a transaction timestamp, impression timestamp, event timestamp.|string| | | |
|ingestion_timestamp_column|Column name that contains the timestamp (or date/time) when the row was ingested (loaded, inserted) into the table. Use a column that is filled by the data pipeline or ETL process at the time of the data loading.|string| | | |
|partition_by_column|Column name that contains the date, datetime or timestamp column for date/time partitioned data. Partition checks (daily partition checks and monthly partition checks) use this column in a GROUP BY clause in order to detect data quality issues in each partition separately. It should be a DATE type, DATETIME type (using a local server time zone) or a TIMESTAMP type (a UTC absolute time).|string| | | |









___  

## ColumnRangeMinValueSensorParametersSpec  
Column level sensor that finds the minimum value. It works on any data type that supports the MIN functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## TableIncidentGroupingSpec  
Configuration of data quality incident grouping on a table level. Defines how similar data quality issues are grouped into incidents.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|grouping_level|Grouping level of failed data quality checks for creating higher level data quality incidents. The default grouping level is by a table, a data quality dimension and a check category (i.e. a datatype data quality incident detected on a table X in the numeric checks category).|enum|table_dimension_category_name<br/>table_dimension<br/>table_dimension_category_type<br/>table<br/>table_dimension_category<br/>| | |
|minimum_severity|Minimum severity level of data quality issues that are grouped into incidents. The default minimum severity level is &#x27;warning&#x27;. Other supported severity levels are &#x27;error&#x27; and &#x27;fatal&#x27;.|enum|warning<br/>error<br/>fatal<br/>| | |
|divide_by_data_group|Create separate data quality incidents for each data group, creating different incidents for different groups of rows. By default, data groups are ignored for grouping data quality issues into data quality incidents.|boolean| | | |
|disabled|Disables data quality incident creation for failed data quality checks on the table.|boolean| | | |









___  

## TableVolumeRowCountStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/sensors/table/volume-table-sensors/#row-count)|Profiler parameters|[TableVolumeRowCountSensorParametersSpec](/docs/reference/sensors/table/volume-table-sensors/#row-count)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnNullsStatisticsCollectorsSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls_count](/docs/reference/yaml/tableyaml/#columnnullsnullscountstatisticscollectorspec)|Configuration of the profiler that counts null column values.|[ColumnNullsNullsCountStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#columnnullsnullscountstatisticscollectorspec)| | | |
|[nulls_percent](/docs/reference/yaml/tableyaml/#columnnullsnullspercentstatisticscollectorspec)|Configuration of the profiler that measures the percentage of null values.|[ColumnNullsNullsPercentStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#columnnullsnullspercentstatisticscollectorspec)| | | |
|[not_nulls_count](/docs/reference/yaml/tableyaml/#columnnullsnotnullscountstatisticscollectorspec)|Configuration of the profiler that counts not null column values.|[ColumnNullsNotNullsCountStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#columnnullsnotnullscountstatisticscollectorspec)| | | |
|[not_nulls_percent](/docs/reference/yaml/tableyaml/#columnnullsnotnullspercentstatisticscollectorspec)|Configuration of the profiler that measures the percentage of not null values.|[ColumnNullsNotNullsPercentStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#columnnullsnotnullspercentstatisticscollectorspec)| | | |









___  

## ColumnUniquenessDuplicateCountStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/sensors/column/uniqueness-column-sensors/#duplicate-count)|Profiler parameters|[ColumnUniquenessDuplicateCountSensorParametersSpec](/docs/reference/sensors/column/uniqueness-column-sensors/#duplicate-count)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnUniquenessDuplicatePercentStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/sensors/column/uniqueness-column-sensors/#duplicate-percent)|Profiler parameters|[ColumnUniquenessDuplicatePercentSensorParametersSpec](/docs/reference/sensors/column/uniqueness-column-sensors/#duplicate-percent)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnUniquenessDistinctCountStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/sensors/column/uniqueness-column-sensors/#distinct-count)|Profiler parameters|[ColumnUniquenessDistinctCountSensorParametersSpec](/docs/reference/sensors/column/uniqueness-column-sensors/#distinct-count)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## TableVolumeStatisticsCollectorsSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[row_count](/docs/reference/yaml/tableyaml/#tablevolumerowcountstatisticscollectorspec)|Configuration of the row count profiler.|[TableVolumeRowCountStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#tablevolumerowcountstatisticscollectorspec)| | | |









___  

## ColumnRangeMaxValueSensorParametersSpec  
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









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

## TableComparisonConfigurationSpec  
Identifies a data comparison configuration between a parent table (the compared table) and the target table from another data source (a reference table).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|reference_table_connection_name|The name of the connection in DQOp where the reference table (the source of truth) is configured. When the connection name is not provided, DQOps will find the reference table on the connection of the parent table.|string| | | |
|reference_table_schema_name|The name of the schema where the reference table is imported into DQOps. The reference table&#x27;s metadata must be imported into DQOps.|string| | | |
|reference_table_name|The name of the reference table that is imported into DQOps. The reference table&#x27;s metadata must be imported into DQOps.|string| | | |
|compared_table_filter|Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the compared table. This expression must be a SQL expression that will be added to the WHERE clause when querying the compared table.|string| | | |
|reference_table_filter|Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the reference table (the source of truth). This expression must be a SQL expression that will be added to the WHERE clause when querying the reference table.|string| | | |
|check_type|The type of checks (profiling, monitoring, partitioned) that this check comparison configuration is applicable. The default value is &#x27;profiling&#x27;.|enum|profiling<br/>partitioned<br/>monitoring<br/>| | |
|time_scale|The time scale that this check comparison configuration is applicable. Supported values are &#x27;daily&#x27; and &#x27;monthly&#x27; for monitoring and partitioned checks or an empty value for profiling checks.|enum|daily<br/>monthly<br/>| | |
|[grouping_columns](/docs/reference/yaml/tableyaml/#tablecomparisongroupingcolumnspairslistspec)|List of column pairs from both the compared table and the reference table that are used in a GROUP BY clause  for grouping both the compared table and the reference table (the source of truth). The columns are used in the next of the table comparison to join the results of data groups (row counts, sums of columns) between the compared table and the reference table to compare the differences.|[TableComparisonGroupingColumnsPairsListSpec](/docs/reference/yaml/tableyaml/#tablecomparisongroupingcolumnspairslistspec)| | | |









___  

## ColumnNullsNullsPercentStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/sensors/column/nulls-column-sensors/#null-percent)|Profiler parameters|[ColumnNullsNullsPercentSensorParametersSpec](/docs/reference/sensors/column/nulls-column-sensors/#null-percent)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnUniquenessDistinctPercentStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/sensors/column/uniqueness-column-sensors/#distinct-percent)|Profiler parameters|[ColumnUniquenessDistinctPercentSensorParametersSpec](/docs/reference/sensors/column/uniqueness-column-sensors/#distinct-percent)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## TablePartitionedChecksRootSpec  
Container of table level partitioned checks, divided by the time window (daily, monthly, etc.)  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](/docs/reference/yaml/partitioned/table-daily-partitioned-checks/#tabledailypartitionedcheckcategoriesspec)|Configuration of day partitioned data quality checks evaluated at a table level.|[TableDailyPartitionedCheckCategoriesSpec](/docs/reference/yaml/partitioned/table-daily-partitioned-checks/#tabledailypartitionedcheckcategoriesspec)| | | |
|[monthly](/docs/reference/yaml/partitioned/table-monthly-partitioned-checks/#tablemonthlypartitionedcheckcategoriesspec)|Configuration of monthly partitioned data quality checks evaluated at a table level..|[TableMonthlyPartitionedCheckCategoriesSpec](/docs/reference/yaml/partitioned/table-monthly-partitioned-checks/#tablemonthlypartitionedcheckcategoriesspec)| | | |









___  

## TableYaml  
Table and column definition file that defines a list of tables and columns that are covered by data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|default_schedules<br/>settings<br/>default_notifications<br/>rule<br/>sensor<br/>source<br/>check<br/>dashboards<br/>default_checks<br/>table<br/>provider_sensor<br/>file_index<br/>| | |
|[spec](/docs/reference/yaml/tableyaml/#tablespec)||[TableSpec](/docs/reference/yaml/tableyaml/#tablespec)| | | |









___  

## TableSpec  
Table specification that defines data quality tests that are enabled on a table and its columns.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|disabled|Disables all data quality checks on the table. Data quality checks will not be executed.|boolean| | | |
|stage|Stage name.|string| | | |
|priority|Table priority (1, 2, 3, 4, ...). The tables could be assigned a priority level. The table priority is copied into each data quality check result and a sensor result, enabling efficient grouping of more and less important tables during a data quality improvement project, when the data quality issues on higher priority tables are fixed before data quality issues on less important tables.|integer| | | |
|filter|SQL WHERE clause added to the sensor queries. Use replacement tokens {table} to replace the content with the full table name, {alias} to replace the content with the table alias of an analyzed table or {column} to replace the content with the analyzed column name.|string| | | |
|[timestamp_columns](/docs/reference/yaml/tableyaml/#timestampcolumnsspec)|Column names that store the timestamps that identify the event (transaction) timestamp and the ingestion (inserted / loaded at) timestamps. Also configures the timestamp source for the date/time partitioned data quality checks (event timestamp or ingestion timestamp).|[TimestampColumnsSpec](/docs/reference/yaml/tableyaml/#timestampcolumnsspec)| | | |
|[incremental_time_window](/docs/reference/yaml/tableyaml/#partitionincrementaltimewindowspec)|Configuration of the time window for analyzing daily or monthly partitions. Specifies the number of recent days and recent months that are analyzed when the partitioned data quality checks are run in an incremental mode (the default mode).|[PartitionIncrementalTimeWindowSpec](/docs/reference/yaml/tableyaml/#partitionincrementaltimewindowspec)| | | |
|default_grouping_name|The name of the default data grouping configuration that is applied on data quality checks. When a default data grouping is selected, all data quality checks run SQL queries with a GROUP BY clause, calculating separate data quality checks for each group of data. The data groupings are defined in the &#x27;groupings&#x27; dictionary (indexed by the data grouping name).|string| | | |
|[groupings](/docs/reference/yaml/tableyaml/#datagroupingconfigurationspecmap)|Data grouping configurations list. Data grouping configurations are configured in two cases: (1) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a &#x27;country&#x27; column used for partitioning. (2) a tag is assigned to a table (within a data grouping level hierarchy), when the data is segmented at a table level (similar tables store the same information, but for different countries, etc.).|[DataGroupingConfigurationSpecMap](/docs/reference/yaml/tableyaml/#datagroupingconfigurationspecmap)| | | |
|[table_comparisons](/docs/reference/yaml/tableyaml/#tablecomparisonconfigurationspecmap)|Dictionary of data comparison configurations. Data comparison configurations are used for cross data-source comparisons to compare this table (called the compared table) with other reference tables (the source of truth). The reference table&#x27;s metadata must be imported into DQOps, but the reference table could be located on a different data source. DQOps will compare metrics calculated for groups of rows (using a GROUP BY clause). For each comparison, the user must specify a name of a data grouping. The number of data grouping dimensions on the parent table and the reference table defined in selected data grouping configurations must match. DQOps will run the same data quality sensors on both the parent table (tested table) and the reference table (the source of truth), comparing the measures (sensor readouts) captured from both the tables.|[TableComparisonConfigurationSpecMap](/docs/reference/yaml/tableyaml/#tablecomparisonconfigurationspecmap)| | | |
|[incident_grouping](/docs/reference/yaml/tableyaml/#tableincidentgroupingspec)|Incident grouping configuration with the overridden configuration at a table level. The field value in this object that are configured will override the default configuration from the connection level. The incident grouping level could be changed or incident creation could be disabled.|[TableIncidentGroupingSpec](/docs/reference/yaml/tableyaml/#tableincidentgroupingspec)| | | |
|[owner](/docs/reference/yaml/tableyaml/#tableownerspec)|Table owner information like the data steward name or the business application name.|[TableOwnerSpec](/docs/reference/yaml/tableyaml/#tableownerspec)| | | |
|[profiling_checks](/docs/reference/yaml/profiling/table-profiling-checks/#tableprofilingcheckcategoriesspec)|Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.|[TableProfilingCheckCategoriesSpec](/docs/reference/yaml/profiling/table-profiling-checks/#tableprofilingcheckcategoriesspec)| | | |
|[monitoring_checks](/docs/reference/yaml/tableyaml/#tablemonitoringchecksspec)|Configuration of table level monitoring checks. Monitoring checks are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A monitoring check stores only the most recent data quality check result for each period of time.|[TableMonitoringChecksSpec](/docs/reference/yaml/tableyaml/#tablemonitoringchecksspec)| | | |
|[partitioned_checks](/docs/reference/yaml/tableyaml/#tablepartitionedchecksrootspec)|Configuration of table level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.|[TablePartitionedChecksRootSpec](/docs/reference/yaml/tableyaml/#tablepartitionedchecksrootspec)| | | |
|[statistics](/docs/reference/yaml/tableyaml/#tablestatisticscollectorsrootcategoriesspec)|Configuration of table level data statistics collector (a basic profiler). Configures which statistics collectors are enabled and how they are configured.|[TableStatisticsCollectorsRootCategoriesSpec](/docs/reference/yaml/tableyaml/#tablestatisticscollectorsrootcategoriesspec)| | | |
|[schedules_override](/docs/reference/yaml/connectionyaml/#defaultschedulesspec)|Configuration of the job scheduler that runs data quality checks. The scheduler configuration is divided into types of checks that have different schedules.|[DefaultSchedulesSpec](/docs/reference/yaml/connectionyaml/#defaultschedulesspec)| | | |
|[columns](/docs/reference/yaml/tableyaml/#columnspecmap)|Dictionary of columns, indexed by a physical column name. Column specification contains the expected column data type and a list of column level data quality checks that are enabled for a column.|[ColumnSpecMap](/docs/reference/yaml/tableyaml/#columnspecmap)| | | |
|[labels](/docs/reference/yaml/connectionyaml/#labelsetspec)|Custom labels that were assigned to the table. Labels are used for searching for tables when filtered data quality checks are executed.|[LabelSetSpec](/docs/reference/yaml/connectionyaml/#labelsetspec)| | | |
|[comments](/docs/reference/yaml/profiling/table-profiling-checks/#commentslistspec)|Comments used for change tracking and documenting changes directly in the table data quality specification file.|[CommentsListSpec](/docs/reference/yaml/profiling/table-profiling-checks/#commentslistspec)| | | |









___  

## TableSchemaStatisticsCollectorsSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[column_count](/docs/reference/yaml/tableyaml/#tableschemacolumncountstatisticscollectorspec)|Configuration of the column count profiler.|[TableSchemaColumnCountStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#tableschemacolumncountstatisticscollectorspec)| | | |









___  

## TableSchemaColumnCountStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/sensors/table/schema-table-sensors/#column-count)|Profiler parameters|[TableColumnCountSensorParametersSpec](/docs/reference/sensors/table/schema-table-sensors/#column-count)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnSpecMap  
Dictionary of columns indexed by a physical column name.  
  














___  

## ColumnStringsStringMeanLengthStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/sensors/column/strings-column-sensors/#string-mean-length)|Profiler parameters|[ColumnStringsStringMeanLengthSensorParametersSpec](/docs/reference/sensors/column/strings-column-sensors/#string-mean-length)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnPartitionedChecksRootSpec  
Container of column level partitioned checks, divided by the time window (daily, monthly, etc.)  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columndailypartitionedcheckcategoriesspec)|Configuration of day partitioned data quality checks evaluated at a column level.|[ColumnDailyPartitionedCheckCategoriesSpec](/docs/reference/yaml/partitioned/column-daily-partitioned-checks/#columndailypartitionedcheckcategoriesspec)| | | |
|[monthly](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnmonthlypartitionedcheckcategoriesspec)|Configuration of monthly partitioned data quality checks evaluated at a column level.|[ColumnMonthlyPartitionedCheckCategoriesSpec](/docs/reference/yaml/partitioned/column-monthly-partitioned-checks/#columnmonthlypartitionedcheckcategoriesspec)| | | |









___  

## DataGroupingConfigurationSpecMap  
Dictionary of named data grouping configurations defined on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [DataGroupingConfigurationSpec](/docs/reference/yaml/connectionyaml/#datagroupingconfigurationspec)]| | | |









___  

## ColumnUniquenessStatisticsCollectorsSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[distinct_count](/docs/reference/yaml/tableyaml/#columnuniquenessdistinctcountstatisticscollectorspec)|Configuration of the profiler that counts distinct column values.|[ColumnUniquenessDistinctCountStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#columnuniquenessdistinctcountstatisticscollectorspec)| | | |
|[distinct_percent](/docs/reference/yaml/tableyaml/#columnuniquenessdistinctpercentstatisticscollectorspec)|Configuration of the profiler that measure the percentage of distinct column values.|[ColumnUniquenessDistinctPercentStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#columnuniquenessdistinctpercentstatisticscollectorspec)| | | |
|[duplicate_count](/docs/reference/yaml/tableyaml/#columnuniquenessduplicatecountstatisticscollectorspec)|Configuration of the profiler that counts duplicate column values.|[ColumnUniquenessDuplicateCountStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#columnuniquenessduplicatecountstatisticscollectorspec)| | | |
|[duplicate_percent](/docs/reference/yaml/tableyaml/#columnuniquenessduplicatepercentstatisticscollectorspec)|Configuration of the profiler that measure the percentage of duplicate column values.|[ColumnUniquenessDuplicatePercentStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#columnuniquenessduplicatepercentstatisticscollectorspec)| | | |









___  

## ColumnStatisticsCollectorsRootCategoriesSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](/docs/reference/yaml/tableyaml/#columnnullsstatisticscollectorsspec)|Configuration of null values profilers on a column level.|[ColumnNullsStatisticsCollectorsSpec](/docs/reference/yaml/tableyaml/#columnnullsstatisticscollectorsspec)| | | |
|[strings](/docs/reference/yaml/tableyaml/#columnstringsstatisticscollectorsspec)|Configuration of string (text) profilers on a column level.|[ColumnStringsStatisticsCollectorsSpec](/docs/reference/yaml/tableyaml/#columnstringsstatisticscollectorsspec)| | | |
|[uniqueness](/docs/reference/yaml/tableyaml/#columnuniquenessstatisticscollectorsspec)|Configuration of profilers that analyse uniqueness of values (distinct count).|[ColumnUniquenessStatisticsCollectorsSpec](/docs/reference/yaml/tableyaml/#columnuniquenessstatisticscollectorsspec)| | | |
|[range](/docs/reference/yaml/tableyaml/#columnrangestatisticscollectorsspec)|Configuration of profilers that analyse the range of values (min, max).|[ColumnRangeStatisticsCollectorsSpec](/docs/reference/yaml/tableyaml/#columnrangestatisticscollectorsspec)| | | |
|[sampling](/docs/reference/yaml/tableyaml/#columnsamplingstatisticscollectorsspec)|Configuration of profilers that collect the column samples.|[ColumnSamplingStatisticsCollectorsSpec](/docs/reference/yaml/tableyaml/#columnsamplingstatisticscollectorsspec)| | | |









___  

## ColumnStringsStringMinLengthStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/sensors/column/strings-column-sensors/#string-min-length)|Profiler parameters|[ColumnStringsStringMinLengthSensorParametersSpec](/docs/reference/sensors/column/strings-column-sensors/#string-min-length)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnStringsStatisticsCollectorsSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[string_max_length](/docs/reference/yaml/tableyaml/#columnstringsstringmaxlengthstatisticscollectorspec)|Configuration of the profiler that finds the maximum string length.|[ColumnStringsStringMaxLengthStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#columnstringsstringmaxlengthstatisticscollectorspec)| | | |
|[string_mean_length](/docs/reference/yaml/tableyaml/#columnstringsstringmeanlengthstatisticscollectorspec)|Configuration of the profiler that finds the mean string length.|[ColumnStringsStringMeanLengthStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#columnstringsstringmeanlengthstatisticscollectorspec)| | | |
|[string_min_length](/docs/reference/yaml/tableyaml/#columnstringsstringminlengthstatisticscollectorspec)|Configuration of the profiler that finds the min string length.|[ColumnStringsStringMinLengthStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#columnstringsstringminlengthstatisticscollectorspec)| | | |
|[string_datatype_detect](/docs/reference/yaml/tableyaml/#columnstringsstringdatatypedetectstatisticscollectorspec)|Configuration of the profiler that detects datatype.|[ColumnStringsStringDatatypeDetectStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#columnstringsstringdatatypedetectstatisticscollectorspec)| | | |









___  

## TableComparisonGroupingColumnsPairsListSpec  
List of column pairs used for grouping and joining in the table comparison checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||List[[TableComparisonGroupingColumnsPairSpec](/docs/reference/yaml/tableyaml/#tablecomparisongroupingcolumnspairspec)]| | | |









___  

## ColumnStringsStringMaxLengthStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/sensors/column/strings-column-sensors/#string-max-length)|Profiler parameters|[ColumnStringsStringMaxLengthSensorParametersSpec](/docs/reference/sensors/column/strings-column-sensors/#string-max-length)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnRangeMinValueStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/yaml/tableyaml/#columnrangeminvaluesensorparametersspec)|Profiler parameters|[ColumnRangeMinValueSensorParametersSpec](/docs/reference/yaml/tableyaml/#columnrangeminvaluesensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnNullsNotNullsCountStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/sensors/column/nulls-column-sensors/#not-null-count)|Profiler parameters|[ColumnNullsNotNullsCountSensorParametersSpec](/docs/reference/sensors/column/nulls-column-sensors/#not-null-count)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnSamplingColumnSamplesStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/sensors/column/sampling-column-sensors/#column-samples)|Profiler parameters|[ColumnSamplingColumnSamplesSensorParametersSpec](/docs/reference/sensors/column/sampling-column-sensors/#column-samples)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnRangeStatisticsCollectorsSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[min_value](/docs/reference/yaml/tableyaml/#columnrangeminvaluestatisticscollectorspec)|Configuration of the profiler that finds the minimum value in the column.|[ColumnRangeMinValueStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#columnrangeminvaluestatisticscollectorspec)| | | |
|[median_value](/docs/reference/yaml/tableyaml/#columnrangemedianvaluestatisticscollectorspec)|Configuration of the profiler that finds the median value in the column.|[ColumnRangeMedianValueStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#columnrangemedianvaluestatisticscollectorspec)| | | |
|[max_value](/docs/reference/yaml/tableyaml/#columnrangemaxvaluestatisticscollectorspec)|Configuration of the profiler that finds the maximum value in the column.|[ColumnRangeMaxValueStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#columnrangemaxvaluestatisticscollectorspec)| | | |
|[sum_value](/docs/reference/yaml/tableyaml/#columnrangesumvaluestatisticscollectorspec)|Configuration of the profiler that finds the sum value in the column.|[ColumnRangeSumValueStatisticsCollectorSpec](/docs/reference/yaml/tableyaml/#columnrangesumvaluestatisticscollectorspec)| | | |









___  

## TableMonitoringChecksSpec  
Container of table level monitoring, divided by the time window (daily, monthly, etc.)  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](/docs/reference/yaml/monitoring/table-daily-monitoring-checks/#tabledailymonitoringcheckcategoriesspec)|Configuration of daily monitoring evaluated at a table level.|[TableDailyMonitoringCheckCategoriesSpec](/docs/reference/yaml/monitoring/table-daily-monitoring-checks/#tabledailymonitoringcheckcategoriesspec)| | | |
|[monthly](/docs/reference/yaml/monitoring/table-monthly-monitoring-checks/#tablemonthlymonitoringcheckcategoriesspec)|Configuration of monthly monitoring evaluated at a table level.|[TableMonthlyMonitoringCheckCategoriesSpec](/docs/reference/yaml/monitoring/table-monthly-monitoring-checks/#tablemonthlymonitoringcheckcategoriesspec)| | | |









___  

## ColumnRangeMedianValueStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/sensors/column/numeric-column-sensors/#percentile)|Profiler parameters|[ColumnNumericMedianSensorParametersSpec](/docs/reference/sensors/column/numeric-column-sensors/#percentile)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









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

## ColumnNullsNotNullsPercentStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/sensors/column/nulls-column-sensors/#not-null-percent)|Profiler parameters|[ColumnNullsNotNullsPercentSensorParametersSpec](/docs/reference/sensors/column/nulls-column-sensors/#not-null-percent)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnRangeSumValueStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/sensors/column/numeric-column-sensors/#sum)|Profiler parameters|[ColumnNumericSumSensorParametersSpec](/docs/reference/sensors/column/numeric-column-sensors/#sum)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnNullsNullsCountStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](/docs/reference/sensors/column/nulls-column-sensors/#null-count)|Profiler parameters|[ColumnNullsNullsCountSensorParametersSpec](/docs/reference/sensors/column/nulls-column-sensors/#null-count)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

