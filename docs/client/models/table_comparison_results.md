
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

## TableComparisonResultsModel  
The table comparison result model with the summary information about the most recent table comparison that was performed.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|table_comparison_results|The dictionary of comparison results between the tables for table level comparisons (the row count). The keys for the dictionary are the check names. The value in the dictionary is a summary information about the most recent comparison.|Map&lt;string, [ComparisonCheckResultModel](#comparisoncheckresultmodel)&gt;|
|column_comparison_results|The dictionary of comparison results between the tables for each compared column. The keys for the dictionary are the column names. The values are dictionaries of the data quality check names and their results.|Map&lt;string, Map&lt;string, [ComparisonCheckResultModel](\docs\client\models\table_comparison_results\#comparisoncheckresultmodel)&gt;&gt;|


___  

