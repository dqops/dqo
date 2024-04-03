# DQOps REST API table_comparison_results models reference
The references of all objects used by [table_comparison_results](../operations/table_comparison_results.md) REST API operations are listed below.


## ComparisonCheckResultModel
The table comparison check result model for the most recent data comparison run. Identifies the check name and the number of data groupings that passed or failed the comparison.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_name`</span>|DQOps data quality check name.|*string*|
|<span class="no-wrap-code">`valid_results`</span>|The number of data groups that were compared and the values matched within the accepted error margin for all check severity levels.|*integer*|
|<span class="no-wrap-code">`warnings`</span>|The number of data groups that were compared and the values did not match, raising a warning severity level data quality issue.|*integer*|
|<span class="no-wrap-code">`errors`</span>|The number of data groups that were compared and the values did not match, raising an error severity level data quality issue.|*integer*|
|<span class="no-wrap-code">`fatals`</span>|The number of data groups that were compared and the values did not match, raising a fatal severity level data quality issue.|*integer*|
|<span class="no-wrap-code">`execution_errors`</span>|The number of execution errors in the check or rule that prevented comparing the tables.|*integer*|
|<span class="no-wrap-code">`not_matching_data_groups`</span>|A list of not matching data grouping names.|*List[string]*|


___

## TableComparisonColumnResultsModel
The table comparison column results model with the information about the most recent table comparison relating to a single compared column.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|
|<span class="no-wrap-code">`column_comparison_results`</span>|The dictionary of comparison results between the tables for the specific column. The keys for the dictionary are check names. The values are summaries of the most recent comparison on this column.|*Dict[string, [ComparisonCheckResultModel](./table_comparison_results.md#comparisoncheckresultmodel)]*|


___

## TableComparisonResultsModel
The table comparison results model with the summary information about the most recent table comparison that was performed.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`table_comparison_results`</span>|The dictionary of comparison results between the tables for table level comparisons (e.g. row count). The keys for the dictionary are the check names. The value in the dictionary is a summary information about the most recent comparison.|*Dict[string, [ComparisonCheckResultModel](#comparisoncheckresultmodel)]*|
|<span class="no-wrap-code">`column_comparison_results`</span>|The dictionary of comparison results between the tables for each compared column. The keys for the dictionary are the column names. The values are dictionaries of the data quality check names and their results.|*Dict[string, [TableComparisonColumnResultsModel](#tablecomparisoncolumnresultsmodel)]*|


___

