
## get_column_monitoring_checks_overview  
Returns an overview of the most recent column level monitoring executions for the monitoring at a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_column_monitoring_checks_overview.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/overview  
```





___  

## get_column_partitioned_checks_overview  
Returns an overview of the most recent column level partitioned checks executions for a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_column_partitioned_checks_overview.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/overview  
```





___  

## get_column_profiling_checks_overview  
Returns an overview of the most recent check executions for all column level data quality profiling checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_column_profiling_checks_overview.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/overview  
```





___  

## get_table_monitoring_checks_overview  
Returns an overview of the most recent table level monitoring executions for the monitoring at a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_table_monitoring_checks_overview.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/overview  
```





___  

## get_table_partitioned_checks_overview  
Returns an overview of the most recent table level partitioned checks executions for a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_table_partitioned_checks_overview.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/overview  
```





___  

## get_table_profiling_checks_overview  
Returns an overview of the most recent check executions for all table level data quality profiling checks on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_table_profiling_checks_overview.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/overview  
```





___  

___  

## CheckResultsOverviewDataModel  
Check recent results overview. Returns the highest severity for the last several runs.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|check_hash|Check hash.|long| | | |
|check_category|Check category name.|string| | | |
|check_name|Check name.|string| | | |
|time_period_display_texts|List of time periods, sorted descending, returned as a text with a possible time zone.|string_list| | | |
|data_groups|List of data group names. Identifies the data group with the highest severity or error result.|string_list| | | |

___  

