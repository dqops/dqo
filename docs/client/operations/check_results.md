Returns the complete results of executed checks on tables and columns.  


___  
## get_column_monitoring_checks_results  
Returns a complete view of the recent column level monitoring executions for the monitoring at a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_column_monitoring_checks_results.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/results  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_list_model||List[[CheckResultsListModel](\docs\client\models\check_results\#checkresultslistmodel)]|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|
|[time_scale](\docs\client\models\#checktimescale)|Time scale|[CheckTimeScale](\docs\client\models\#checktimescale)|true|
|data_group|Data group|string|false|
|month_start|Month start boundary|string|false|
|month_end|Month end boundary|string|false|
|check_name|Check name|string|false|
|category|Check category name|string|false|
|table_comparison|Table comparison name|string|false|
|max_results_per_check|Maximum number of results per check, the default is 100|long|false|





___  
## get_column_partitioned_checks_results  
Returns an overview of the most recent column level partitioned checks executions for a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_column_partitioned_checks_results.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/results  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_list_model||List[[CheckResultsListModel](\docs\client\models\check_results\#checkresultslistmodel)]|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|
|[time_scale](\docs\client\models\#checktimescale)|Time scale|[CheckTimeScale](\docs\client\models\#checktimescale)|true|
|data_group|Data group|string|false|
|month_start|Month start boundary|string|false|
|month_end|Month end boundary|string|false|
|check_name|Check name|string|false|
|category|Check category name|string|false|
|table_comparison|Table comparison name|string|false|
|max_results_per_check|Maximum number of results per check, the default is 100|long|false|





___  
## get_column_profiling_checks_results  
Returns an overview of the most recent check executions for all column level data quality profiling checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_column_profiling_checks_results.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/results  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_list_model||List[[CheckResultsListModel](\docs\client\models\check_results\#checkresultslistmodel)]|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|
|data_group|Data group|string|false|
|month_start|Month start boundary|string|false|
|month_end|Month end boundary|string|false|
|check_name|Check name|string|false|
|category|Check category name|string|false|
|table_comparison|Table comparison name|string|false|
|max_results_per_check|Maximum number of results per check, the default is 100|long|false|





___  
## get_table_data_quality_status  
Read the most recent results of executed data quality checks on the table and return the current table&#x27;s data quality status - the number of failed data quality checks if the table has active data quality issues. Also returns the names of data quality checks that did not pass most recently. This operation verifies only the status of the most recently executed data quality checks. Previous data quality issues are not counted.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_table_data_quality_status.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/status  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_data_quality_status_model](\docs\client\models\check_results\#tabledataqualitystatusmodel)||[TableDataQualityStatusModel](\docs\client\models\check_results\#tabledataqualitystatusmodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|months|Optional filter - the number of months to review the data quality check results. For partitioned checks, it is the number of months to analyze. The default value is 1 (which is the current month and 1 previous month).|long|false|
|[check_type](\docs\client\models\#checktype)|Optional check type filter (profiling, monitoring, partitioned).|[CheckType](\docs\client\models\#checktype)|false|
|[check_time_scale](\docs\client\models\#checktimescale)|Optional time scale filter for monitoring and partitioned checks (values: daily or monthly).|[CheckTimeScale](\docs\client\models\#checktimescale)|false|
|data_group|Optional data group|string|false|
|check_name|Optional check name|string|false|
|category|Optional check category name|string|false|
|table_comparison|Optional table comparison name|string|false|
|quality_dimension|Optional data quality dimension|string|false|





___  
## get_table_monitoring_checks_results  
Returns the complete results of the most recent table level monitoring executions for the monitoring at a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_table_monitoring_checks_results.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/results  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_list_model||List[[CheckResultsListModel](\docs\client\models\check_results\#checkresultslistmodel)]|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|[time_scale](\docs\client\models\#checktimescale)|Time scale|[CheckTimeScale](\docs\client\models\#checktimescale)|true|
|data_group|Data group|string|false|
|month_start|Month start boundary|string|false|
|month_end|Month end boundary|string|false|
|check_name|Check name|string|false|
|category|Check category name|string|false|
|table_comparison|Table comparison name|string|false|
|max_results_per_check|Maximum number of results per check, the default is 100|long|false|





___  
## get_table_partitioned_checks_results  
Returns a complete view of the recent table level partitioned checks executions for a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_table_partitioned_checks_results.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/results  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_list_model||List[[CheckResultsListModel](\docs\client\models\check_results\#checkresultslistmodel)]|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|[time_scale](\docs\client\models\#checktimescale)|Time scale|[CheckTimeScale](\docs\client\models\#checktimescale)|true|
|data_group|Data group|string|false|
|month_start|Month start boundary|string|false|
|month_end|Month end boundary|string|false|
|check_name|Check name|string|false|
|category|Check category name|string|false|
|table_comparison|Table comparison name|string|false|
|max_results_per_check|Maximum number of results per check, the default is 100|long|false|





___  
## get_table_profiling_checks_results  
Returns the complete results of the most recent check executions for all table level data quality profiling checks on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_table_profiling_checks_results.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/results  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_list_model||List[[CheckResultsListModel](\docs\client\models\check_results\#checkresultslistmodel)]|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|data_group|Data group|string|false|
|month_start|Month start boundary|string|false|
|month_end|Month end boundary|string|false|
|check_name|Check name|string|false|
|category|Check category name|string|false|
|table_comparison|Table comparison name|string|false|
|max_results_per_check|Maximum number of results per check, the default is 100|long|false|




