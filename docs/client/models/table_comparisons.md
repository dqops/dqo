
## PhysicalTableName  
Physical table name that is a combination of a schema name and a physical table name (without any quoting or escaping).  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|schema_name|Schema name|string|
|table_name|Table name|string|


___  

## TableComparisonConfigurationModel  
Model that contains the basic information about a table comparison configuration that specifies how the current table could be compared to another table that is a source of truth for comparison.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|table_comparison_configuration_name|The name of the table comparison configuration that is defined in the &#x27;table_comparisons&#x27; node on the table specification.|string|
|compared_connection|Compared connection name - the connection name to the data source that is compared (verified).|string|
|[compared_table](\docs\client\models\#physicaltablename)|The schema and table name of the compared table that is verified.|[PhysicalTableName](\docs\client\models\#physicaltablename)|
|reference_connection|Reference connection name - the connection name to the data source that has the reference data to compare to.|string|
|[reference_table](\docs\client\models\#physicaltablename)|The schema and table name of the reference table that has the expected data.|[PhysicalTableName](\docs\client\models\#physicaltablename)|
|[check_type](\docs\client\models\#checktype)|The type of checks (profiling, monitoring, partitioned) that this check comparison configuration is applicable. The default value is &#x27;profiling&#x27;.|[CheckType](\docs\client\models\#checktype)|
|[time_scale](\docs\client\models\#checktimescale)|The time scale that this check comparison configuration is applicable. Supported values are &#x27;daily&#x27; and &#x27;monthly&#x27; for monitoring and partitioned checks or an empty value for profiling checks.|[CheckTimeScale](\docs\client\models\#checktimescale)|
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

## TableComparisonModel  
Model that contains the all editable information about a table-to-table comparison defined on a compared table.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|table_comparison_configuration_name|The name of the table comparison configuration that is defined in the &#x27;table_comparisons&#x27; node on the table specification.|string|
|compared_connection|Compared connection name - the connection name to the data source that is compared (verified).|string|
|[compared_table](\docs\client\models\#physicaltablename)|The schema and table name of the compared table that is verified.|[PhysicalTableName](\docs\client\models\#physicaltablename)|
|reference_connection|Reference connection name - the connection name to the data source that has the reference data to compare to.|string|
|[reference_table](\docs\client\models\#physicaltablename)|The schema and table name of the reference table that has the expected data.|[PhysicalTableName](\docs\client\models\#physicaltablename)|
|[default_compare_thresholds](\docs\client\models\#comparethresholdsmodel)|The template of the compare thresholds that should be applied to all comparisons when the comparison is enabled.|[CompareThresholdsModel](\docs\client\models\#comparethresholdsmodel)|
|[compare_row_count](\docs\client\models\#comparethresholdsmodel)|The row count comparison configuration.|[CompareThresholdsModel](\docs\client\models\#comparethresholdsmodel)|
|[compare_column_count](\docs\client\models\#comparethresholdsmodel)|The column count comparison configuration.|[CompareThresholdsModel](\docs\client\models\#comparethresholdsmodel)|
|supports_compare_column_count|Boolean flag that decides if this comparison type supports comparing the column count between tables. Partitioned table comparisons do not support comparing the column counts.|boolean|
|[compare_table_run_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run the table comparison checks for this table, using checks selected in this model.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|[compare_table_clean_data_job_template](\docs\client\models\#deletestoreddataqueuejobparameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored check results for this table comparison.|[DeleteStoredDataQueueJobParameters](\docs\client\models\#deletestoreddataqueuejobparameters)|
|can_edit|Boolean flag that decides if the current user can update or delete the table comparison.|boolean|
|can_run_compare_checks|Boolean flag that decides if the current user can run comparison checks.|boolean|
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean|


___  

