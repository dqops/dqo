Returns the overview of the recently executed checks on tables and columns.  


___  
## get_column_monitoring_checks_overview  
Returns an overview of the most recent column level monitoring executions for the monitoring at a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_column_monitoring_checks_overview.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/overview  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_overview_data_model||List[[CheckResultsOverviewDataModel](../../models/check_results_overview/#checkresultsoverviewdatamodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/"daily"/overview^
		-H "Accept: application/json"

    ```


___  
## get_column_partitioned_checks_overview  
Returns an overview of the most recent column level partitioned checks executions for a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_column_partitioned_checks_overview.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/overview  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_overview_data_model||List[[CheckResultsOverviewDataModel](../../models/check_results_overview/#checkresultsoverviewdatamodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/"daily"/overview^
		-H "Accept: application/json"

    ```


___  
## get_column_profiling_checks_overview  
Returns an overview of the most recent check executions for all column level data quality profiling checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_column_profiling_checks_overview.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/overview  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_overview_data_model||List[[CheckResultsOverviewDataModel](../../models/check_results_overview/#checkresultsoverviewdatamodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/overview^
		-H "Accept: application/json"

    ```


___  
## get_table_monitoring_checks_overview  
Returns an overview of the most recent table level monitoring executions for the monitoring at a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_table_monitoring_checks_overview.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/overview  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_overview_data_model||List[[CheckResultsOverviewDataModel](../../models/check_results_overview/#checkresultsoverviewdatamodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/"daily"/overview^
		-H "Accept: application/json"

    ```


___  
## get_table_partitioned_checks_overview  
Returns an overview of the most recent table level partitioned checks executions for a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_table_partitioned_checks_overview.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/overview  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_overview_data_model||List[[CheckResultsOverviewDataModel](../../models/check_results_overview/#checkresultsoverviewdatamodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/"daily"/overview^
		-H "Accept: application/json"

    ```


___  
## get_table_profiling_checks_overview  
Returns an overview of the most recent check executions for all table level data quality profiling checks on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_table_profiling_checks_overview.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/overview  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_overview_data_model||List[[CheckResultsOverviewDataModel](../../models/check_results_overview/#checkresultsoverviewdatamodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/overview^
		-H "Accept: application/json"

    ```


