
## get_column_monitoring_checks_results  
Returns a complete view of the recent column level monitoring executions for the monitoring at a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_column_monitoring_checks_results.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/results  
```





___  

## get_column_partitioned_checks_results  
Returns an overview of the most recent column level partitioned checks executions for a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_column_partitioned_checks_results.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/results  
```





___  

## get_column_profiling_checks_results  
Returns an overview of the most recent check executions for all column level data quality profiling checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_column_profiling_checks_results.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/results  
```





___  

## get_table_data_quality_status  
Read the most recent results of executed data quality checks on the table and return the current table&#x27;s data quality status - the number of failed data quality checks if the table has active data quality issues. Also returns the names of data quality checks that did not pass most recently. This operation verifies only the status of the most recently executed data quality checks. Previous data quality issues are not counted.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_table_data_quality_status.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/status  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_data_quality_status_model](\docs\client\operations\check_results\#tabledataqualitystatusmodel)||[TableDataQualityStatusModel](\docs\client\operations\check_results\#tabledataqualitystatusmodel)|






___  

## get_table_monitoring_checks_results  
Returns the complete results of the most recent table level monitoring executions for the monitoring at a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_table_monitoring_checks_results.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/results  
```





___  

## get_table_partitioned_checks_results  
Returns a complete view of the recent table level partitioned checks executions for a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_table_partitioned_checks_results.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/results  
```





___  

## get_table_profiling_checks_results  
Returns the complete results of the most recent check executions for all table level data quality profiling checks on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_table_profiling_checks_results.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/results  
```





___  

___  

## CheckResultsListModel  
Check detailed results. Returned in the context of a single data group, with a supplied list of other data groups.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|check_hash|Check hash.|long| | | |
|check_category|Check category name.|string| | | |
|check_name|Check name.|string| | | |
|check_display_name|Check display name.|string| | | |
|check_type|Check type.|string| | | |
|data_groups|Data groups list.|string_list| | | |
|data_group|Selected data group.|string| | | |

___  

## TableDataQualityStatusModel  
The table validity status. It is a summary of the results of the most recently executed data quality checks on the table.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name|The connection name in DQO.|string| | | |
|schema_name|The schema name.|string| | | |
|table_name|The table name.|string| | | |
|highest_severity_issue|The severity of the highest identified data quality issue (1 &#x3D; warning, 2 &#x3D; error, 3 &#x3D; fatal) or 0 when no data quality issues were identified. This field will be null if no data quality checks were executed on the table.|integer| | | |
|executed_checks|The total number of most recent checks that were executed on the table. Table comparison checks that are comparing groups of data are counted as the number of compared data groups.|integer| | | |
|valid_results|The number of most recent valid data quality checks that passed without raising any issues.|integer| | | |
|warnings|The number of most recent data quality checks that failed by raising a warning severity data quality issue.|integer| | | |
|errors|The number of most recent data quality checks that failed by raising an error severity data quality issue.|integer| | | |
|fatals|The number of most recent data quality checks that failed by raising a fatal severity data quality issue.|integer| | | |
|execution_errors|The number of data quality check execution errors that were reported due to access issues to the data source, invalid mapping in DQO, invalid queries in data quality sensors or invalid python rules. When an execution error is reported, the configuration of a data quality check on a table must be updated.|integer| | | |

___  

