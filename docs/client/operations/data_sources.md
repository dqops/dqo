Rest API controller that operates on data sources that are not yet imported, testing connections or retrieving the metadata (schemas and tables).  


___  
## get_remote_data_source_schemas  
Introspects a list of schemas inside a remote data source, identified by an already imported connection.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_sources/get_remote_data_source_schemas.py)
  

**GET**
```
http://localhost:8888/api/datasource/connections/{connectionName}/schemas  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|schema_remote_model||List[[SchemaRemoteModel](\docs\client\models\data_sources\#schemaremotemodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/datasource/connections/sample_connection/schemas
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
## get_remote_data_source_tables  
Introspects the list of columns inside a schema on a remote data source that is identified by a connection that was added to DQOps.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_sources/get_remote_data_source_tables.py)
  

**GET**
```
http://localhost:8888/api/datasource/connections/{connectionName}/schemas/{schemaName}/tables  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|remote_table_list_model||List[[RemoteTableListModel](\docs\client\models\data_sources\#remotetablelistmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/datasource/connections/sample_connection/schemas/sample_schema/tables
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
## test_connection  
Checks if the given remote connection could be opened and the credentials are valid  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_sources/test_connection.py)
  

**POST**
```
http://localhost:8888/api/datasource/testconnection  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[connection_test_model](\docs\client\models\data_sources\#connectiontestmodel)||[ConnectionTestModel](\docs\client\models\data_sources\#connectiontestmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|verify_name_uniqueness|Verify if the connection name is unique, the default value is true|boolean|false|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Basic connection model|[ConnectionModel](\docs\client\models\#connectionmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/datasource/testconnection
		-H "Accept: application/json"
		-H "Content-Type: application/json"
		-d '{
		  "connection_name" : "sample_connection",
		  "parallel_runs_limit" : 4,
		  "provider_type" : "postgresql",
		  "postgresql" : {
		    "host" : "localhost",
		    "port" : "5432",
		    "database" : "db",
		    "user" : "PASSWD",
		    "sslmode" : "disable"
		  },
		  "run_checks_job_template" : {
		    "connectionName" : "sample_connection",
		    "enabled" : true
		  },
		  "run_profiling_checks_job_template" : {
		    "connectionName" : "sample_connection",
		    "enabled" : true,
		    "checkType" : "profiling"
		  },
		  "run_monitoring_checks_job_template" : {
		    "connectionName" : "sample_connection",
		    "enabled" : true,
		    "checkType" : "monitoring"
		  },
		  "run_partition_checks_job_template" : {
		    "connectionName" : "sample_connection",
		    "enabled" : true,
		    "checkType" : "partitioned"
		  },
		  "collect_statistics_job_template" : {
		    "connectionName" : "sample_connection",
		    "enabled" : true,
		    "columnNames" : [ ]
		  },
		  "data_clean_job_template" : {
		    "connectionName" : "sample_connection",
		    "deleteErrors" : true,
		    "deleteStatistics" : true,
		    "deleteCheckResults" : true,
		    "deleteSensorReadouts" : true
		  },
		  "can_edit" : false,
		  "can_collect_statistics" : true,
		  "can_run_checks" : true,
		  "can_delete_data" : true
		}'
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


