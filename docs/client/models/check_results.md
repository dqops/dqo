
## CheckResultsListModel  
Check detailed results. Returned in the context of a single data group, with a supplied list of other data groups.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_hash|Check hash.|long|
|check_category|Check category name.|string|
|check_name|Check name.|string|
|check_display_name|Check display name.|string|
|check_type|Check type.|string|
|data_groups|Data groups list.|string_list|
|data_group|Selected data group.|string|
|check_result_entries|Single check results|List&lt;[CheckResultEntryModel](\docs\client\models\incidents\#checkresultentrymodel)&gt;|


___  

## TableDataQualityStatusModel  
The table validity status. It is a summary of the results of the most recently executed data quality checks on the table.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|The connection name in DQOps.|string|
|schema_name|The schema name.|string|
|table_name|The table name.|string|
|highest_severity_issue|The severity of the highest identified data quality issue (1 &#x3D; warning, 2 &#x3D; error, 3 &#x3D; fatal) or 0 when no data quality issues were identified. This field will be null if no data quality checks were executed on the table.|integer|
|executed_checks|The total number of most recent checks that were executed on the table. Table comparison checks that are comparing groups of data are counted as the number of compared data groups.|integer|
|valid_results|The number of most recent valid data quality checks that passed without raising any issues.|integer|
|warnings|The number of most recent data quality checks that failed by raising a warning severity data quality issue.|integer|
|errors|The number of most recent data quality checks that failed by raising an error severity data quality issue.|integer|
|fatals|The number of most recent data quality checks that failed by raising a fatal severity data quality issue.|integer|
|execution_errors|The number of data quality check execution errors that were reported due to access issues to the data source, invalid mapping in DQOps, invalid queries in data quality sensors or invalid python rules. When an execution error is reported, the configuration of a data quality check on a table must be updated.|integer|
|failed_checks_statuses|The paths to all failed data quality checks (keys) and severity of the highest data quality issue that was detected. Table-level checks are identified by the check name. Column-level checks are identified as a check_name[column_name].|Map&lt;string, [CheckResultStatus](#null)&gt;|


___  

