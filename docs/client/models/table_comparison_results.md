
## ComparisonCheckResultModel  
The table comparison check result model for the most recent data comparison run. Identifies the check name and the number of data groupings that passed or failed the comparison.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_name|DQOps data quality check name.|string|
|valid_results|The number of data groups that were compared and the values matched within the accepted error margin for all check severity levels.|integer|
|warnings|The number of data groups that were compared and the values did not match, raising a warning severity level data quality issue.|integer|
|errors|The number of data groups that were compared and the values did not match, raising an error severity level data quality issue.|integer|
|fatals|The number of data groups that were compared and the values did not match, raising a fatal severity level data quality issue.|integer|
|execution_errors|The number of execution errors in the check or rule that prevented comparing the tables.|integer|
|not_matching_data_groups|A list of not matching data grouping names.|string_list|


___  

## TableComparisonColumnResultsModel  
The table comparison column results model with the information about the most recent table comparison relating to a single compared column.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|column_name|Column name|string|
|column_comparison_results|The dictionary of comparison results between the tables for the specific column. The keys for the dictionary are check names. The values are summaries of the most recent comparison on this column.|Dict[string, [ComparisonCheckResultModel](../table_comparison_results/#ComparisonCheckResultModel)]|


___  

## TableComparisonResultsModel  
The table comparison results model with the summary information about the most recent table comparison that was performed.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|table_comparison_results|The dictionary of comparison results between the tables for table level comparisons (e.g. row count). The keys for the dictionary are the check names. The value in the dictionary is a summary information about the most recent comparison.|Dict[string, [ComparisonCheckResultModel](#ComparisonCheckResultModel)]|
|column_comparison_results|The dictionary of comparison results between the tables for each compared column. The keys for the dictionary are the column names. The values are dictionaries of the data quality check names and their results.|Dict[string, [TableComparisonColumnResultsModel](#TableComparisonColumnResultsModel)]|


___  

