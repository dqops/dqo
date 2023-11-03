Returns the errors related to check executions on tables and columns.  


___  
## get_column_monitoring_errors  
Returns errors related to the recent column level monitoring executions for the monitoring at a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/errors/get_column_monitoring_errors.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/errors  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|errors_list_model||List[[ErrorsListModel](\docs\client\models\errors\#errorslistmodel)]|




**Parameters of this method are described below**  
  
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






**Usage examples**  
=== "curl"
      
    ```
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily/errors
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


___  
## get_column_partitioned_errors  
Returns the errors related to the recent column level partitioned checks executions for a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/errors/get_column_partitioned_errors.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/errors  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|errors_list_model||List[[ErrorsListModel](\docs\client\models\errors\#errorslistmodel)]|




**Parameters of this method are described below**  
  
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






**Usage examples**  
=== "curl"
      
    ```
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily/errors
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


___  
## get_column_profiling_errors  
Returns the errors related to the recent check executions for all column level data quality profiling checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/errors/get_column_profiling_errors.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/errors  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|errors_list_model||List[[ErrorsListModel](\docs\client\models\errors\#errorslistmodel)]|




**Parameters of this method are described below**  
  
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






**Usage examples**  
=== "curl"
      
    ```
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/errors
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


___  
## get_table_monitoring_errors  
Returns the errors related to the most recent table level monitoring executions for the monitoring at a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/errors/get_table_monitoring_errors.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/errors  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|errors_list_model||List[[ErrorsListModel](\docs\client\models\errors\#errorslistmodel)]|




**Parameters of this method are described below**  
  
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






**Usage examples**  
=== "curl"
      
    ```
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/daily/errors
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


___  
## get_table_partitioned_errors  
Returns errors related to the recent table level partitioned checks executions for a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/errors/get_table_partitioned_errors.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/errors  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|errors_list_model||List[[ErrorsListModel](\docs\client\models\errors\#errorslistmodel)]|




**Parameters of this method are described below**  
  
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






**Usage examples**  
=== "curl"
      
    ```
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/daily/errors
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


___  
## get_table_profiling_errors  
Returns the errors related to the most recent check executions for all table level data quality profiling checks on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/errors/get_table_profiling_errors.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/errors  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|errors_list_model||List[[ErrorsListModel](\docs\client\models\errors\#errorslistmodel)]|




**Parameters of this method are described below**  
  
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






**Usage examples**  
=== "curl"
      
    ```
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/errors
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


