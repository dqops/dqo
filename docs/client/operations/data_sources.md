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
|schema_remote_model||List[[SchemaRemoteModel](../../models/data_sources/#SchemaRemoteModel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/datasource/connections/sample_connection/schemas^
		-H "Accept: application/json"

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
|remote_table_list_model||List[[RemoteTableListModel](../../models/data_sources/#RemoteTableListModel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/datasource/connections/sample_connection/schemas/sample_schema/tables^
		-H "Accept: application/json"

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
|[connection_test_model](../../models/data_sources/#ConnectionTestModel)||[ConnectionTestModel](../../models/data_sources/#ConnectionTestModel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|verify_name_uniqueness|Verify if the connection name is unique, the default value is true|boolean| |




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Basic connection model|[ConnectionModel](../../models/#ConnectionModel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/datasource/testconnection^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection_name\":\"sample_connection\",\"parallel_runs_limit\":4,\"provider_type\":\"postgresql\",\"postgresql\":{\"host\":\"localhost\",\"port\":\"5432\",\"database\":\"db\",\"user\":\"PASSWD\",\"sslmode\":\"disable\"},\"run_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true},\"run_profiling_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"profiling\"},\"run_monitoring_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"monitoring\"},\"run_partition_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"partitioned\"},\"collect_statistics_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"columnNames\":[]},\"data_clean_job_template\":{\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true},\"can_edit\":false,\"can_collect_statistics\":true,\"can_run_checks\":true,\"can_delete_data\":true}"

    ```


