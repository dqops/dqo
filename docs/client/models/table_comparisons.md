---
title: DQOps REST API table_comparisons models reference
---
# DQOps REST API table_comparisons models reference
The references of all objects used by [table_comparisons](../operations/table_comparisons.md) REST API operations are listed below.


## TableComparisonGroupingColumnPairModel
Model that identifies a pair of column names used for grouping the data on both the compared table and the reference table. The groups are then matched (joined) by DQOps to compare aggregated results.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`compared_table_column_name`</span>|The name of the column on the compared table (the parent table) that is used in the GROUP BY clause to group rows before compared aggregates (row counts, sums, etc.) are calculated. This column is also used to join (match) results to the reference table.|*string*|
|<span class="no-wrap-code">`reference_table_column_name`</span>|The name of the column on the reference table (the source of truth) that is used in the GROUP BY clause to group rows before compared aggregates (row counts, sums, etc.) are calculated. This column is also used to join (match) results to the compared table.|*string*|


___

## TableComparisonConfigurationModel
Model that contains the basic information about a table comparison configuration that specifies how the current table can be compared with another table that is a source of truth for comparison.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|The name of the table comparison configuration that is defined in the 'table_comparisons' node on the table specification.|*string*|
|<span class="no-wrap-code">`compared_connection`</span>|Compared connection name - the connection name to the data source that is compared (verified).|*string*|
|<span class="no-wrap-code">[`compared_table`](./common.md#physicaltablename)</span>|The schema and table name of the compared table that is verified.|*[PhysicalTableName](./common.md#physicaltablename)*|
|<span class="no-wrap-code">`reference_connection`</span>|Reference connection name - the connection name to the data source that has the reference data to compare to.|*string*|
|<span class="no-wrap-code">[`reference_table`](./common.md#physicaltablename)</span>|The schema and table name of the reference table that has the expected data.|*[PhysicalTableName](./common.md#physicaltablename)*|
|<span class="no-wrap-code">[`check_type`](./common.md#checktype)</span>|The type of checks (profiling, monitoring, partitioned) that this check comparison configuration is applicable. The default value is 'profiling'.|*[CheckType](./common.md#checktype)*|
|<span class="no-wrap-code">[`time_scale`](./common.md#checktimescale)</span>|The time scale that this check comparison configuration is applicable. Supported values are 'daily' and 'monthly' for monitoring and partitioned checks or an empty value for profiling checks.|*[CheckTimeScale](./common.md#checktimescale)*|
|<span class="no-wrap-code">`compared_table_filter`</span>|Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the compared table. This expression must be a SQL expression that will be added to the WHERE clause when querying the compared table.|*string*|
|<span class="no-wrap-code">`reference_table_filter`</span>|Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the reference table (the source of truth). This expression must be a SQL expression that will be added to the WHERE clause when querying the reference table.|*string*|
|<span class="no-wrap-code">`grouping_columns`</span>|List of column pairs from both the compared table and the reference table that are used in a GROUP BY clause  for grouping both the compared table and the reference table (the source of truth). The columns are used in the next of the table comparison to join the results of data groups (row counts, sums of columns) between the compared table and the reference table to compare the differences.|*List[[TableComparisonGroupingColumnPairModel](#tablecomparisongroupingcolumnpairmodel)]*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete the table comparison.|*boolean*|
|<span class="no-wrap-code">`can_run_compare_checks`</span>|Boolean flag that decides if the current user can run comparison checks.|*boolean*|
|<span class="no-wrap-code">`can_delete_data`</span>|Boolean flag that decides if the current user can delete data (results).|*boolean*|


___

## CompareThresholdsModel
Model with the custom compare threshold levels for raising data quality issues at different severity levels
 when the difference between the compared (tested) table and the reference table (the source of truth) exceed given
 thresholds as a percentage of difference between the actual value and the expected value from the reference table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`warning_difference_percent`</span>|The percentage difference between the measure value on the compared table and the reference table that raises a warning severity data quality issue when the difference is bigger.|*double*|
|<span class="no-wrap-code">`error_difference_percent`</span>|The percentage difference between the measure value on the compared table and the reference table that raises an error severity data quality issue when the difference is bigger.|*double*|
|<span class="no-wrap-code">`fatal_difference_percent`</span>|The percentage difference between the measure value on the compared table and the reference table that raises a fatal severity data quality issue when the difference is bigger.|*double*|


___

## ColumnComparisonModel
The column to column comparison model used to select which measures (min, max, sum, mean, null count, not nul count) are compared
 for this column between the compared (tested) column and the reference column from the reference table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`compared_column_name`</span>|The name of the compared column in the compared table (the tested table). The REST API returns all columns defined in the metadata.|*string*|
|<span class="no-wrap-code">`reference_column_name`</span>|The name of the reference column in the reference table (the source of truth). Set the name of the reference column to enable comparison between the compared and the reference columns.|*string*|
|<span class="no-wrap-code">[`compare_min`](./table_comparisons.md#comparethresholdsmodel)</span>|The column compare configuration for comparing the minimum value between the compared (tested) column and the reference column. Leave null when the measure is not compared.|*[CompareThresholdsModel](./table_comparisons.md#comparethresholdsmodel)*|
|<span class="no-wrap-code">[`compare_max`](./table_comparisons.md#comparethresholdsmodel)</span>|The column compare configuration for comparing the maximum value between the compared (tested) column and the reference column. Leave null when the measure is not compared.|*[CompareThresholdsModel](./table_comparisons.md#comparethresholdsmodel)*|
|<span class="no-wrap-code">[`compare_sum`](./table_comparisons.md#comparethresholdsmodel)</span>|The column compare configuration for comparing the sum of values between the compared (tested) column and the reference column. Leave null when the measure is not compared.|*[CompareThresholdsModel](./table_comparisons.md#comparethresholdsmodel)*|
|<span class="no-wrap-code">[`compare_mean`](./table_comparisons.md#comparethresholdsmodel)</span>|The column compare configuration for comparing the mean (average) value between the compared (tested) column and the reference column. Leave null when the measure is not compared.|*[CompareThresholdsModel](./table_comparisons.md#comparethresholdsmodel)*|
|<span class="no-wrap-code">[`compare_null_count`](./table_comparisons.md#comparethresholdsmodel)</span>|The column compare configuration for comparing the count of null values between the compared (tested) column and the reference column. Leave null when the measure is not compared.|*[CompareThresholdsModel](./table_comparisons.md#comparethresholdsmodel)*|
|<span class="no-wrap-code">[`compare_not_null_count`](./table_comparisons.md#comparethresholdsmodel)</span>|The column compare configuration for comparing the count of not null values between the compared (tested) column and the reference column. Leave null when the measure is not compared.|*[CompareThresholdsModel](./table_comparisons.md#comparethresholdsmodel)*|


___

## TableComparisonModel
Model that contains the all editable information about a table-to-table comparison defined on a compared table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|The name of the table comparison configuration that is defined in the 'table_comparisons' node on the table specification.|*string*|
|<span class="no-wrap-code">`compared_connection`</span>|Compared connection name - the connection name to the data source that is compared (verified).|*string*|
|<span class="no-wrap-code">[`compared_table`](./common.md#physicaltablename)</span>|The schema and table name of the compared table that is verified.|*[PhysicalTableName](./common.md#physicaltablename)*|
|<span class="no-wrap-code">`reference_connection`</span>|Reference connection name - the connection name to the data source that has the reference data to compare to.|*string*|
|<span class="no-wrap-code">[`reference_table`](./common.md#physicaltablename)</span>|The schema and table name of the reference table that has the expected data.|*[PhysicalTableName](./common.md#physicaltablename)*|
|<span class="no-wrap-code">`compared_table_filter`</span>|Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the compared table. This expression must be a SQL expression that will be added to the WHERE clause when querying the compared table.|*string*|
|<span class="no-wrap-code">`reference_table_filter`</span>|Optional custom SQL filter expression that is added to the SQL query that retrieves the data from the reference table (the source of truth). This expression must be a SQL expression that will be added to the WHERE clause when querying the reference table.|*string*|
|<span class="no-wrap-code">`grouping_columns`</span>|List of column pairs from both the compared table and the reference table that are used in a GROUP BY clause  for grouping both the compared table and the reference table (the source of truth). The columns are used in the next of the table comparison to join the results of data groups (row counts, sums of columns) between the compared table and the reference table to compare the differences.|*List[[TableComparisonGroupingColumnPairModel](./table_comparisons.md#tablecomparisongroupingcolumnpairmodel)]*|
|<span class="no-wrap-code">[`default_compare_thresholds`](#comparethresholdsmodel)</span>|The template of the compare thresholds that should be applied to all comparisons when the comparison is enabled.|*[CompareThresholdsModel](#comparethresholdsmodel)*|
|<span class="no-wrap-code">[`compare_row_count`](./table_comparisons.md#comparethresholdsmodel)</span>|The row count comparison configuration.|*[CompareThresholdsModel](./table_comparisons.md#comparethresholdsmodel)*|
|<span class="no-wrap-code">[`compare_column_count`](./table_comparisons.md#comparethresholdsmodel)</span>|The column count comparison configuration.|*[CompareThresholdsModel](./table_comparisons.md#comparethresholdsmodel)*|
|<span class="no-wrap-code">`supports_compare_column_count`</span>|Boolean flag that decides if this comparison type supports comparing the column count between tables. Partitioned table comparisons do not support comparing the column counts.|*boolean*|
|<span class="no-wrap-code">`columns`</span>|The list of compared columns, their matching reference column and the enabled comparisons.|*List[[ColumnComparisonModel](#columncomparisonmodel)]*|
|<span class="no-wrap-code">[`compare_table_run_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run the table comparison checks for this table, using checks selected in this model.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`compare_table_clean_data_job_template`](./jobs.md#deletestoreddataqueuejobparameters)</span>|Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored check results for this table comparison.|*[DeleteStoredDataQueueJobParameters](./jobs.md#deletestoreddataqueuejobparameters)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete the table comparison.|*boolean*|
|<span class="no-wrap-code">`can_run_compare_checks`</span>|Boolean flag that decides if the current user can run comparison checks.|*boolean*|
|<span class="no-wrap-code">`can_delete_data`</span>|Boolean flag that decides if the current user can delete data (results).|*boolean*|


___

