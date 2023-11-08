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
|check_results_list_model||List[[CheckResultsListModel](../../models/check_results/#checkresultslistmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../../models/#checktimescale)|Time scale|[CheckTimeScale](../../models/#checktimescale)|:material-check-bold:|
|data_group|Data group|string| |
|month_start|Month start boundary|string| |
|month_end|Month end boundary|string| |
|check_name|Check name|string| |
|category|Check category name|string| |
|table_comparison|Table comparison name|string| |
|max_results_per_check|Maximum number of results per check, the default is 100|long| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/"daily"/results^
		-H "Accept: application/json"

    ```


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
|check_results_list_model||List[[CheckResultsListModel](../../models/check_results/#checkresultslistmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../../models/#checktimescale)|Time scale|[CheckTimeScale](../../models/#checktimescale)|:material-check-bold:|
|data_group|Data group|string| |
|month_start|Month start boundary|string| |
|month_end|Month end boundary|string| |
|check_name|Check name|string| |
|category|Check category name|string| |
|table_comparison|Table comparison name|string| |
|max_results_per_check|Maximum number of results per check, the default is 100|long| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/"daily"/results^
		-H "Accept: application/json"

    ```


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
|check_results_list_model||List[[CheckResultsListModel](../../models/check_results/#checkresultslistmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|data_group|Data group|string| |
|month_start|Month start boundary|string| |
|month_end|Month end boundary|string| |
|check_name|Check name|string| |
|category|Check category name|string| |
|table_comparison|Table comparison name|string| |
|max_results_per_check|Maximum number of results per check, the default is 100|long| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/results^
		-H "Accept: application/json"

    ```


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
|[table_data_quality_status_model](../../models/check_results/#tabledataqualitystatusmodel)||[TableDataQualityStatusModel](../../models/check_results/#tabledataqualitystatusmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|months|Optional filter - the number of months to review the data quality check results. For partitioned checks, it is the number of months to analyze. The default value is 1 (which is the current month and 1 previous month).|long| |
|[check_type](../../models/#checktype)|Optional check type filter (profiling, monitoring, partitioned).|[CheckType](../../models/#checktype)| |
|[check_time_scale](../../models/#checktimescale)|Optional time scale filter for monitoring and partitioned checks (values: daily or monthly).|[CheckTimeScale](../../models/#checktimescale)| |
|data_group|Optional data group|string| |
|check_name|Optional check name|string| |
|category|Optional check category name|string| |
|table_comparison|Optional table comparison name|string| |
|quality_dimension|Optional data quality dimension|string| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/status^
		-H "Accept: application/json"

    ```


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
|check_results_list_model||List[[CheckResultsListModel](../../models/check_results/#checkresultslistmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/#checktimescale)|Time scale|[CheckTimeScale](../../models/#checktimescale)|:material-check-bold:|
|data_group|Data group|string| |
|month_start|Month start boundary|string| |
|month_end|Month end boundary|string| |
|check_name|Check name|string| |
|category|Check category name|string| |
|table_comparison|Table comparison name|string| |
|max_results_per_check|Maximum number of results per check, the default is 100|long| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/"daily"/results^
		-H "Accept: application/json"

    ```


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
|check_results_list_model||List[[CheckResultsListModel](../../models/check_results/#checkresultslistmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/#checktimescale)|Time scale|[CheckTimeScale](../../models/#checktimescale)|:material-check-bold:|
|data_group|Data group|string| |
|month_start|Month start boundary|string| |
|month_end|Month end boundary|string| |
|check_name|Check name|string| |
|category|Check category name|string| |
|table_comparison|Table comparison name|string| |
|max_results_per_check|Maximum number of results per check, the default is 100|long| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/"daily"/results^
		-H "Accept: application/json"

    ```


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
|check_results_list_model||List[[CheckResultsListModel](../../models/check_results/#checkresultslistmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|data_group|Data group|string| |
|month_start|Month start boundary|string| |
|month_end|Month end boundary|string| |
|check_name|Check name|string| |
|category|Check category name|string| |
|table_comparison|Table comparison name|string| |
|max_results_per_check|Maximum number of results per check, the default is 100|long| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/results^
		-H "Accept: application/json"

    ```


