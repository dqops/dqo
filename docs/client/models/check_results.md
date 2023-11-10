
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
|data_groups|Data groups list.|List[string]|
|data_group|Selected data group.|string|
|check_result_entries|Single check results|List[[CheckResultEntryModel](../incidents/#CheckResultEntryModel)]|


___  

## CheckResultStatus  
Enumeration of check execution statuses. It is the highest severity or an error if the sensor could not be executed due to a configuration issue.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|valid<br/>warning<br/>execution_error<br/>error<br/>fatal<br/>|

___  

## RuleSeverityLevel  
Rule severity levels. Matches the severity level name (warning - 1, alert - 2, fatal - 3) with a numeric level.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|valid<br/>warning<br/>error<br/>fatal<br/>|

___  

## CheckCurrentDataQualityStatusModel  
The most recent data quality status for a single data quality check.
 If data grouping is enabled, this model will return the highest data quality issue status from all
 data quality results for all data groups.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[current_severity](../check_results/#CheckResultStatus)|The data quality issue severity for this data quality check. An additional value *execution_error* is used to tell that the check, sensor or rule failed to execute due to insufficient  permissions to the table or an error in the sensor&#x27;s template or a Python rule. For partitioned checks, it is the highest severity of all results for all partitions (time periods) in the analyzed time range.|[CheckResultStatus](../check_results/#CheckResultStatus)|
|[highest_historical_severity](#RuleSeverityLevel)|The highest severity of previous executions of this data quality issue in the analyzed time range. It can be different from the *current_severity* if the data quality issue was solved and the most recently data quality issue did not detect it anymore. For partitioned checks, this field returns the same value as the *current_severity*, because data quality issues in older partitions are still valid.|[RuleSeverityLevel](#RuleSeverityLevel)|
|[check_type](../#CheckType)|The check type: profiling, monitoring, partitioned.|[CheckType](../#CheckType)|
|[time_scale](../#CheckTimeScale)|The check time scale for *monitoring* and *partitioned* check types. The time scales are *daily* and *monthly*. The profiling checks do not have a time scale.|[CheckTimeScale](../#CheckTimeScale)|
|category|Check category name, such as nulls, schema, strings, volume.|string|
|quality_dimension|Data quality dimension, such as Completeness, Uniqueness, Validity.|string|


___  

## ColumnCurrentDataQualityStatusModel  
The column validity status. It is a summary of the results of the most recently executed data quality checks on the column.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[highest_severity_level](../check_results/#CheckResultStatus)|The severity of the highest identified data quality issue. This field will be null if no data quality checks were executed on the column.|[CheckResultStatus](../check_results/#CheckResultStatus)|
|executed_checks|The total number of most recent checks that were executed on the column. Table comparison checks that are comparing groups of data are counted as the number of compared data groups.|integer|
|valid_results|The number of most recent valid data quality checks that passed without raising any issues.|integer|
|warnings|The number of most recent data quality checks that failed by raising a warning severity data quality issue.|integer|
|errors|The number of most recent data quality checks that failed by raising an error severity data quality issue.|integer|
|fatals|The number of most recent data quality checks that failed by raising a fatal severity data quality issue.|integer|
|execution_errors|The number of data quality check execution errors that were reported due to access issues to the data source, invalid mapping in DQOps, invalid queries in data quality sensors or invalid python rules. When an execution error is reported, the configuration of a data quality check on a column must be updated.|integer|
|checks|The dictionary of statuses for data quality checks. The keys are data quality check names, the values are the current data quality check statuses that describe the most current status.|Dict[string, [CheckCurrentDataQualityStatusModel](../check_results/#CheckCurrentDataQualityStatusModel)]|


___  

## TableCurrentDataQualityStatusModel  
The table validity status. It is a summary of the results of the most recently executed data quality checks on the table.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|The connection name in DQOps.|string|
|schema_name|The schema name.|string|
|table_name|The table name.|string|
|[highest_severity_level](#CheckResultStatus)|The severity of the highest identified data quality issue. This field will be null if no data quality checks were executed on the table.|[CheckResultStatus](#CheckResultStatus)|
|executed_checks|The total number of most recent checks that were executed on the table. Table comparison checks that are comparing groups of data are counted as the number of compared data groups.|integer|
|valid_results|The number of most recent valid data quality checks that passed without raising any issues.|integer|
|warnings|The number of most recent data quality checks that failed by raising a warning severity data quality issue.|integer|
|errors|The number of most recent data quality checks that failed by raising an error severity data quality issue.|integer|
|fatals|The number of most recent data quality checks that failed by raising a fatal severity data quality issue.|integer|
|execution_errors|The number of data quality check execution errors that were reported due to access issues to the data source, invalid mapping in DQOps, invalid queries in data quality sensors or invalid python rules. When an execution error is reported, the configuration of a data quality check on a table must be updated.|integer|
|checks|The dictionary of statuses for data quality checks. The keys are data quality check names, the values are the current data quality check statuses that describe the most current status.|Dict[string, [CheckCurrentDataQualityStatusModel](#CheckCurrentDataQualityStatusModel)]|
|columns|Dictionary of data statues for all columns that have any known data quality results. The keys in the dictionary are the column names.|Dict[string, [ColumnCurrentDataQualityStatusModel](#ColumnCurrentDataQualityStatusModel)]|


___  

