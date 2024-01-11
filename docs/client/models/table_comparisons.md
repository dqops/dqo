
## CheckType
Enumeration of data quality check types: profiling, monitoring, partitioned.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|profiling<br/>partitioned<br/>monitoring<br/>|

___

## TableComparisonGroupingColumnPairModel
Model that identifies a pair of column names used for grouping the data on both the compared table and the reference table. The groups are then matched (joined) by DQOps to compare aggregated results.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|compared_table_column_name|The name of the column on the compared table (the parent table) that is used in the GROUP BY clause to group rows before compared aggregates (row counts, sums, etc.) are calculated. This column is also used to join (match) results to the reference table.|string|
|reference_table_column_name|The name of the column on the reference table (the source of truth) that is used in the GROUP BY clause to group rows before compared aggregates (row counts, sums, etc.) are calculated. This column is also used to join (match) results to the compared table.|string|


___

## TableComparisonConfigurationModel
Model that contains the basic information about a table comparison configuration that specifies how the current table could be compared to another table that is a source of truth for comparison.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|table_comparison_configuration_name|The name of the table comparison configuration that is defined in the &#x27;table_comparisons&#x27; node on the table specification.|string|
|compared_connection|Compared connection name - the connection name to the data source that is compared (verified).|string|
|[compared_table](./columns.md#physicaltablename)|The schema and table name of the compared table that is verified.|[PhysicalTableName](./columns.md#physicaltablename)|
|reference_connection|Reference connection name - the connection name to the data source that has the reference data to compare to.|string|
|[reference_table](./columns.md#physicaltablename)|The schema and table name of the reference table that has the expected data.|[PhysicalTableName](./columns.md#physicaltablename)|
|[check_type](./table_comparisons.md#checktype)|The type of checks (profiling, monitoring, partitioned) that this check comparison configuration is applicable. The default value is &#x27;profiling&#x27;.|[CheckType](./table_comparisons.md#checktype)|
|[time_scale](./Common.md#checktimescale)|The time scale that this check comparison configuration is applicable. Supported values are &#x27;daily&#x27; and &#x27;monthly&#x27; for monitoring and partitioned checks or an empty value for profiling checks.|[CheckTimeScale](./Common.md#checktimescale)|
|grouping_columns|List of column pairs from both the compared table and the reference table that are used in a GROUP BY clause  for grouping both the compared table and the reference table (the source of truth). The columns are used in the next of the table comparison to join the results of data groups (row counts, sums of columns) between the compared table and the reference table to compare the differences.|List[[TableComparisonGroupingColumnPairModel](#tablecomparisongroupingcolumnpairmodel)]|
|can_edit|Boolean flag that decides if the current user can update or delete the table comparison.|boolean|
|can_run_compare_checks|Boolean flag that decides if the current user can run comparison checks.|boolean|
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean|


___

## CompareThresholdsModel
Model with the custom compare threshold levels for raising data quality issues at different severity levels
 when the difference between the compared (tested) table and the reference table (the source of truth) exceed given
 thresholds as a percentage of difference between the actual value and the expected value from the reference table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|warning_difference_percent|The percentage difference between the measure value on the compared table and the reference table that raises a warning severity data quality issue when the difference is bigger.|double|
|error_difference_percent|The percentage difference between the measure value on the compared table and the reference table that raises an error severity data quality issue when the difference is bigger.|double|
|fatal_difference_percent|The percentage difference between the measure value on the compared table and the reference table that raises a fatal severity data quality issue when the difference is bigger.|double|


___

## ColumnComparisonModel
The column to column comparison model used to select which measures (min, max, sum, mean, null count, not nul count) are compared
 for this column between the compared (tested) column and the reference column from the reference table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|compared_column_name|The name of the compared column in the compared table (the tested table). The REST API returns all columns defined in the metadata.|string|
|reference_column_name|The name of the reference column in the reference table (the source of truth). Set the name of the reference column to enable comparison between the compared and the reference columns.|string|
|[compare_min](./table_comparisons.md#comparethresholdsmodel)|The column compare configuration for comparing the minimum value between the compared (tested) column and the reference column. Leave null when the measure is not compared.|[CompareThresholdsModel](./table_comparisons.md#comparethresholdsmodel)|
|[compare_max](./table_comparisons.md#comparethresholdsmodel)|The column compare configuration for comparing the maximum value between the compared (tested) column and the reference column. Leave null when the measure is not compared.|[CompareThresholdsModel](./table_comparisons.md#comparethresholdsmodel)|
|[compare_sum](./table_comparisons.md#comparethresholdsmodel)|The column compare configuration for comparing the sum of values between the compared (tested) column and the reference column. Leave null when the measure is not compared.|[CompareThresholdsModel](./table_comparisons.md#comparethresholdsmodel)|
|[compare_mean](./table_comparisons.md#comparethresholdsmodel)|The column compare configuration for comparing the mean (average) value between the compared (tested) column and the reference column. Leave null when the measure is not compared.|[CompareThresholdsModel](./table_comparisons.md#comparethresholdsmodel)|
|[compare_null_count](./table_comparisons.md#comparethresholdsmodel)|The column compare configuration for comparing the count of null values between the compared (tested) column and the reference column. Leave null when the measure is not compared.|[CompareThresholdsModel](./table_comparisons.md#comparethresholdsmodel)|
|[compare_not_null_count](./table_comparisons.md#comparethresholdsmodel)|The column compare configuration for comparing the count of not null values between the compared (tested) column and the reference column. Leave null when the measure is not compared.|[CompareThresholdsModel](./table_comparisons.md#comparethresholdsmodel)|


___

## TableComparisonModel
Model that contains the all editable information about a table-to-table comparison defined on a compared table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|table_comparison_configuration_name|The name of the table comparison configuration that is defined in the &#x27;table_comparisons&#x27; node on the table specification.|string|
|compared_connection|Compared connection name - the connection name to the data source that is compared (verified).|string|
|[compared_table](./columns.md#physicaltablename)|The schema and table name of the compared table that is verified.|[PhysicalTableName](./columns.md#physicaltablename)|
|reference_connection|Reference connection name - the connection name to the data source that has the reference data to compare to.|string|
|[reference_table](./columns.md#physicaltablename)|The schema and table name of the reference table that has the expected data.|[PhysicalTableName](./columns.md#physicaltablename)|
|grouping_columns|List of column pairs from both the compared table and the reference table that are used in a GROUP BY clause  for grouping both the compared table and the reference table (the source of truth). The columns are used in the next of the table comparison to join the results of data groups (row counts, sums of columns) between the compared table and the reference table to compare the differences.|List[[TableComparisonGroupingColumnPairModel](./table_comparisons.md#tablecomparisongroupingcolumnpairmodel)]|
|[default_compare_thresholds](#comparethresholdsmodel)|The template of the compare thresholds that should be applied to all comparisons when the comparison is enabled.|[CompareThresholdsModel](#comparethresholdsmodel)|
|[compare_row_count](./table_comparisons.md#comparethresholdsmodel)|The row count comparison configuration.|[CompareThresholdsModel](./table_comparisons.md#comparethresholdsmodel)|
|[compare_column_count](./table_comparisons.md#comparethresholdsmodel)|The column count comparison configuration.|[CompareThresholdsModel](./table_comparisons.md#comparethresholdsmodel)|
|supports_compare_column_count|Boolean flag that decides if this comparison type supports comparing the column count between tables. Partitioned table comparisons do not support comparing the column counts.|boolean|
|columns|The list of compared columns, their matching reference column and the enabled comparisons.|List[[ColumnComparisonModel](#columncomparisonmodel)]|
|[compare_table_run_checks_job_template](./Common.md#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run the table comparison checks for this table, using checks selected in this model.|[CheckSearchFilters](./Common.md#checksearchfilters)|
|[compare_table_clean_data_job_template](./jobs.md#deletestoreddataqueuejobparameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored check results for this table comparison.|[DeleteStoredDataQueueJobParameters](./jobs.md#deletestoreddataqueuejobparameters)|
|can_edit|Boolean flag that decides if the current user can update or delete the table comparison.|boolean|
|can_run_compare_checks|Boolean flag that decides if the current user can run comparison checks.|boolean|
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean|


___

