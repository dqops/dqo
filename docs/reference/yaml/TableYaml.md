
## ColumnNullsNotNullsPercentStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\column\nulls-column-sensors\#not-null-percent)|Profiler parameters|[ColumnNullsNotNullsPercentSensorParametersSpec](\docs\reference\sensors\column\nulls-column-sensors\#not-null-percent)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnStringsStringMeanLengthStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\column\strings-column-sensors\#string-mean-length)|Profiler parameters|[ColumnStringsStringMeanLengthSensorParametersSpec](\docs\reference\sensors\column\strings-column-sensors\#string-mean-length)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## TableYaml  
Table and column definition file that defines a list of tables and columns that are covered by data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|table<br/>default_schedules<br/>dashboards<br/>source<br/>sensor<br/>check<br/>default_checks<br/>rule<br/>file_index<br/>settings<br/>default_notifications<br/>provider_sensor<br/>| | |
|[spec](#tablespec)||[TableSpec](#tablespec)| | | |









___  

## TableComparisonGroupingColumnsPairsListSpec  
List of column pairs used for grouping and joining in the table comparison checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|mod_count||integer| | | |









___  

## ColumnSamplingStatisticsCollectorsSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[column_samples](#columnsamplingcolumnsamplesstatisticscollectorspec)|Configuration of the profiler that finds the maximum string length.|[ColumnSamplingColumnSamplesStatisticsCollectorSpec](#columnsamplingcolumnsamplesstatisticscollectorspec)| | | |









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

## ColumnUniquenessDuplicateCountStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\column\uniqueness-column-sensors\#duplicate-count)|Profiler parameters|[ColumnUniquenessDuplicateCountSensorParametersSpec](\docs\reference\sensors\column\uniqueness-column-sensors\#duplicate-count)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnStringsStringMinLengthStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\column\strings-column-sensors\#string-min-length)|Profiler parameters|[ColumnStringsStringMinLengthSensorParametersSpec](\docs\reference\sensors\column\strings-column-sensors\#string-min-length)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnStringsStringMaxLengthStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\column\strings-column-sensors\#string-max-length)|Profiler parameters|[ColumnStringsStringMaxLengthSensorParametersSpec](\docs\reference\sensors\column\strings-column-sensors\#string-max-length)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnUniquenessDistinctPercentStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\column\uniqueness-column-sensors\#distinct-percent)|Profiler parameters|[ColumnUniquenessDistinctPercentSensorParametersSpec](\docs\reference\sensors\column\uniqueness-column-sensors\#distinct-percent)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## TablePartitionedChecksRootSpec  
Container of table level partitioned checks, divided by the time window (daily, monthly, etc.)  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](\docs\reference\yaml\partitioned\table-daily-partitioned-checks\#tabledailypartitionedcheckcategoriesspec)|Configuration of day partitioned data quality checks evaluated at a table level.|[TableDailyPartitionedCheckCategoriesSpec](\docs\reference\yaml\partitioned\table-daily-partitioned-checks\#tabledailypartitionedcheckcategoriesspec)| | | |
|[monthly](\docs\reference\yaml\partitioned\table-monthly-partitioned-checks\#tablemonthlypartitionedcheckcategoriesspec)|Configuration of monthly partitioned data quality checks evaluated at a table level..|[TableMonthlyPartitionedCheckCategoriesSpec](\docs\reference\yaml\partitioned\table-monthly-partitioned-checks\#tablemonthlypartitionedcheckcategoriesspec)| | | |









___  

## ColumnNullsNullsPercentStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\column\nulls-column-sensors\#null-percent)|Profiler parameters|[ColumnNullsNullsPercentSensorParametersSpec](\docs\reference\sensors\column\nulls-column-sensors\#null-percent)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnUniquenessDistinctCountStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\column\uniqueness-column-sensors\#distinct-count)|Profiler parameters|[ColumnUniquenessDistinctCountSensorParametersSpec](\docs\reference\sensors\column\uniqueness-column-sensors\#distinct-count)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnSpecMap  
Dictionary of columns indexed by a physical column name.  
  














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

## ColumnSamplingColumnSamplesStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\column\sampling-column-sensors\#column-samples)|Profiler parameters|[ColumnSamplingColumnSamplesSensorParametersSpec](\docs\reference\sensors\column\sampling-column-sensors\#column-samples)| | | |
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

## DataGroupingConfigurationSpecMap  
Dictionary of named data grouping configurations defined on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|access_order||boolean| | | |
|size||integer| | | |
|mod_count||integer| | | |
|threshold||integer| | | |









___  

## TableVolumeRowCountStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\table\volume-table-sensors\#row-count)|Profiler parameters|[TableVolumeRowCountSensorParametersSpec](\docs\reference\sensors\table\volume-table-sensors\#row-count)| | | |
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

## TableComparisonConfigurationSpec  
Identifies a data comparison configuration between a parent table (the compared table) and the target table from another data source (a reference table).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|reference_table_connection_name|The name of the connection in DQO where the reference table (the source of truth) is configured. When the connection name is not provided, DQO will find the reference table on the connection of the parent table.|string| | | |
|reference_table_schema_name|The name of the schema where the reference table is imported into DQO. The reference table&#x27;s metadata must be imported into DQO.|string| | | |
|reference_table_name|The name of the reference table that is imported into DQO. The reference table&#x27;s metadata must be imported into DQO.|string| | | |
|compared_table_filter|Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the compared table. This expression must be a SQL expression that will be added to the WHERE clause when querying the compared table.|string| | | |
|reference_table_filter|Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the reference table (the source of truth). This expression must be a SQL expression that will be added to the WHERE clause when querying the reference table.|string| | | |
|check_type|The type of checks (profiling, monitoring, partitioned) that this check comparison configuration is applicable. The default value is &#x27;profiling&#x27;.|enum|profiling<br/>partitioned<br/>monitoring<br/>| | |
|time_scale|The time scale that this check comparison configuration is applicable. Supported values are &#x27;daily&#x27; and &#x27;monthly&#x27; for monitoring and partitioned checks or an empty value for profiling checks.|enum|daily<br/>monthly<br/>| | |
|[grouping_columns](#tablecomparisongroupingcolumnspairslistspec)|List of column pairs from both the compared table and the reference table that are used in a GROUP BY clause  for grouping both the compared table and the reference table (the source of truth). The columns are used in the next of the table comparison to join the results of data groups (row counts, sums of columns) between the compared table and the reference table to compare the differences.|[TableComparisonGroupingColumnsPairsListSpec](#tablecomparisongroupingcolumnspairslistspec)| | | |









___  

## TableOwnerSpec  
Table owner information.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|data_steward|Data steward name|string| | | |
|application|Business application name|string| | | |









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

## ColumnRangeSumValueStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\column\numeric-column-sensors\#sum)|Profiler parameters|[ColumnNumericSumSensorParametersSpec](\docs\reference\sensors\column\numeric-column-sensors\#sum)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









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

## ColumnRangeMedianValueStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\column\numeric-column-sensors\#percentile)|Profiler parameters|[ColumnNumericMedianSensorParametersSpec](\docs\reference\sensors\column\numeric-column-sensors\#percentile)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnNullsNullsCountStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\column\nulls-column-sensors\#null-count)|Profiler parameters|[ColumnNullsNullsCountSensorParametersSpec](\docs\reference\sensors\column\nulls-column-sensors\#null-count)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## ColumnSpec  
Column specification that identifies a single column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|disabled|Disables all data quality checks on the column. Data quality checks will not be executed.|boolean| | | |
|sql_expression|SQL expression used for calculated fields or when additional column value transformation is required before the column could be used analyzed in data quality checks (data type conversion, transformation). It should be an SQL expression using the SQL language of the analyzed database type. Use replacement tokens {table} to replace the content with the full table name, {alias} to replace the content with the table alias of an analyzed table or {column} to replace the content with the analyzed column name. An example to extract a value from a string column that stores a JSON in PostgreSQL: &quot;{column}::json-&gt;&#x27;address&#x27;-&gt;&#x27;zip&#x27;&quot;.|string| | | |
|[type_snapshot](#columntypesnapshotspec)|Column data type that was retrieved when the table metadata was imported.|[ColumnTypeSnapshotSpec](#columntypesnapshotspec)| | | |
|[profiling_checks](\docs\reference\yaml\profiling\column-profiling-checks\#columnprofilingcheckcategoriesspec)|Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.|[ColumnProfilingCheckCategoriesSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnprofilingcheckcategoriesspec)| | | |
|[monitoring_checks](#columnmonitoringchecksrootspec)|Configuration of column level monitoring checks. Monitoring are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A monitoring stores only the most recent data quality check result for each period of time.|[ColumnMonitoringChecksRootSpec](#columnmonitoringchecksrootspec)| | | |
|[partitioned_checks](#columnpartitionedchecksrootspec)|Configuration of column level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.|[ColumnPartitionedChecksRootSpec](#columnpartitionedchecksrootspec)| | | |
|[statistics](#columnstatisticscollectorsrootcategoriesspec)|Custom configuration of a column level statistics collector (a basic profiler). Enables customization of the statistics collector settings when the collector is analysing this column.|[ColumnStatisticsCollectorsRootCategoriesSpec](#columnstatisticscollectorsrootcategoriesspec)| | | |
|[labels](\docs\reference\yaml\connectionyaml\#labelsetspec)|Custom labels that were assigned to the column. Labels are used for searching for columns when filtered data quality checks are executed.|[LabelSetSpec](\docs\reference\yaml\connectionyaml\#labelsetspec)| | | |
|[comments](\docs\reference\yaml\profiling\table-profiling-checks\#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](\docs\reference\yaml\profiling\table-profiling-checks\#commentslistspec)| | | |









___  

## TableStatisticsCollectorsRootCategoriesSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](#tablevolumestatisticscollectorsspec)|Configuration of volume statistics collectors on a table level.|[TableVolumeStatisticsCollectorsSpec](#tablevolumestatisticscollectorsspec)| | | |









___  

## ColumnPartitionedChecksRootSpec  
Container of column level partitioned checks, divided by the time window (daily, monthly, etc.)  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columndailypartitionedcheckcategoriesspec)|Configuration of day partitioned data quality checks evaluated at a column level.|[ColumnDailyPartitionedCheckCategoriesSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columndailypartitionedcheckcategoriesspec)| | | |
|[monthly](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnmonthlypartitionedcheckcategoriesspec)|Configuration of monthly partitioned data quality checks evaluated at a column level.|[ColumnMonthlyPartitionedCheckCategoriesSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnmonthlypartitionedcheckcategoriesspec)| | | |









___  

## ColumnMonitoringChecksRootSpec  
Container of column level monitoring, divided by the time window (daily, monthly, etc.)  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columndailymonitoringcheckcategoriesspec)|Configuration of daily monitoring evaluated at a column level.|[ColumnDailyMonitoringCheckCategoriesSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columndailymonitoringcheckcategoriesspec)| | | |
|[monthly](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnmonthlymonitoringcheckcategoriesspec)|Configuration of monthly monitoring evaluated at a column level.|[ColumnMonthlyMonitoringCheckCategoriesSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnmonthlymonitoringcheckcategoriesspec)| | | |









___  

## TableMonitoringChecksSpec  
Container of table level monitoring, divided by the time window (daily, monthly, etc.)  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tabledailymonitoringcheckcategoriesspec)|Configuration of daily monitoring evaluated at a table level.|[TableDailyMonitoringCheckCategoriesSpec](\docs\reference\yaml\monitoring\table-daily-monitoring-checks\#tabledailymonitoringcheckcategoriesspec)| | | |
|[monthly](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tablemonthlymonitoringcheckcategoriesspec)|Configuration of monthly monitoring evaluated at a table level.|[TableMonthlyMonitoringCheckCategoriesSpec](\docs\reference\yaml\monitoring\table-monthly-monitoring-checks\#tablemonthlymonitoringcheckcategoriesspec)| | | |









___  

## ColumnStringsStringDatatypeDetectStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\column\datatype-column-sensors\#string-datatype-detect)|Profiler parameters|[ColumnDatatypeStringDatatypeDetectSensorParametersSpec](\docs\reference\sensors\column\datatype-column-sensors\#string-datatype-detect)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









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

## TableIncidentGroupingSpec  
Configuration of data quality incident grouping on a table level. Defines how similar data quality issues are grouped into incidents.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|grouping_level|Grouping level of failed data quality checks for creating higher level data quality incidents. The default grouping level is by a table, a data quality dimension and a check category (i.e. a datatype data quality incident detected on a table X in the numeric checks category).|enum|table_dimension_category_type<br/>table_dimension<br/>table<br/>table_dimension_category<br/>table_dimension_category_name<br/>| | |
|minimum_severity|Minimum severity level of data quality issues that are grouped into incidents. The default minimum severity level is &#x27;warning&#x27;. Other supported severity levels are &#x27;error&#x27; and &#x27;fatal&#x27;.|enum|warning<br/>error<br/>fatal<br/>| | |
|divide_by_data_group|Create separate data quality incidents for each data group, creating different incidents for different groups of rows. By default, data groups are ignored for grouping data quality issues into data quality incidents.|boolean| | | |
|disabled|Disables data quality incident creation for failed data quality checks on the table.|boolean| | | |









___  

## ColumnRangeMinValueStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnrangeminvaluesensorparametersspec)|Profiler parameters|[ColumnRangeMinValueSensorParametersSpec](#columnrangeminvaluesensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









___  

## TableComparisonConfigurationSpecMap  
Dictionary of data comparison configurations between the current table (the parent of this node) and another reference table (the source of truth)
 to which we are comparing the tables to measure the accuracy of the data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|access_order||boolean| | | |
|size||integer| | | |
|mod_count||integer| | | |
|threshold||integer| | | |









___  

## ColumnNullsNotNullsCountStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\column\nulls-column-sensors\#not-null-count)|Profiler parameters|[ColumnNullsNotNullsCountSensorParametersSpec](\docs\reference\sensors\column\nulls-column-sensors\#not-null-count)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









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
|[profiling_checks](\docs\reference\yaml\profiling\table-profiling-checks\#tableprofilingcheckcategoriesspec)|Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.|[TableProfilingCheckCategoriesSpec](\docs\reference\yaml\profiling\table-profiling-checks\#tableprofilingcheckcategoriesspec)| | | |
|[monitoring_checks](#tablemonitoringchecksspec)|Configuration of table level monitoring checks. Monitoring checks are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A monitoring check stores only the most recent data quality check result for each period of time.|[TableMonitoringChecksSpec](#tablemonitoringchecksspec)| | | |
|[partitioned_checks](#tablepartitionedchecksrootspec)|Configuration of table level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.|[TablePartitionedChecksRootSpec](#tablepartitionedchecksrootspec)| | | |
|[statistics](#tablestatisticscollectorsrootcategoriesspec)|Configuration of table level data statistics collector (a basic profiler). Configures which statistics collectors are enabled and how they are configured.|[TableStatisticsCollectorsRootCategoriesSpec](#tablestatisticscollectorsrootcategoriesspec)| | | |
|[schedules_override](\docs\reference\yaml\connectionyaml\#monitoringschedulesspec)|Configuration of the job scheduler that runs data quality checks. The scheduler configuration is divided into types of checks that have different schedules.|[MonitoringSchedulesSpec](\docs\reference\yaml\connectionyaml\#monitoringschedulesspec)| | | |
|[columns](#columnspecmap)|Dictionary of columns, indexed by a physical column name. Column specification contains the expected column data type and a list of column level data quality checks that are enabled for a column.|[ColumnSpecMap](#columnspecmap)| | | |
|[labels](\docs\reference\yaml\connectionyaml\#labelsetspec)|Custom labels that were assigned to the table. Labels are used for searching for tables when filtered data quality checks are executed.|[LabelSetSpec](\docs\reference\yaml\connectionyaml\#labelsetspec)| | | |
|[comments](\docs\reference\yaml\profiling\table-profiling-checks\#commentslistspec)|Comments used for change tracking and documenting changes directly in the table data quality specification file.|[CommentsListSpec](\docs\reference\yaml\profiling\table-profiling-checks\#commentslistspec)| | | |









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

## ColumnUniquenessDuplicatePercentStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\column\uniqueness-column-sensors\#duplicate-percent)|Profiler parameters|[ColumnUniquenessDuplicatePercentSensorParametersSpec](\docs\reference\sensors\column\uniqueness-column-sensors\#duplicate-percent)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









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

## TableVolumeStatisticsCollectorsSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[row_count](#tablevolumerowcountstatisticscollectorspec)|Configuration of the row count profiler.|[TableVolumeRowCountStatisticsCollectorSpec](#tablevolumerowcountstatisticscollectorspec)| | | |









___  

## ColumnRangeMaxValueStatisticsCollectorSpec  
  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnrangemaxvaluesensorparametersspec)|Profiler parameters|[ColumnRangeMaxValueSensorParametersSpec](#columnrangemaxvaluesensorparametersspec)| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |









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

