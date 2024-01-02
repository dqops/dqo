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
|schema_remote_model||List[[SchemaRemoteModel](../../models/data_sources/#schemaremotemodel)]|




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

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.data_sources import get_remote_data_source_schemas
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_remote_data_source_schemas.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.data_sources import get_remote_data_source_schemas
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_remote_data_source_schemas.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.data_sources import get_remote_data_source_schemas
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_remote_data_source_schemas.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.data_sources import get_remote_data_source_schemas
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_remote_data_source_schemas.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    [ {
	  "alreadyImported" : false
	}, {
	  "alreadyImported" : false
	}, {
	  "alreadyImported" : false
	} ]
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
|remote_table_list_model||List[[RemoteTableListModel](../../models/data_sources/#remotetablelistmodel)]|




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

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.data_sources import get_remote_data_source_tables
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_remote_data_source_tables.sync(
	    'sample_connection',
	    'sample_schema',
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.data_sources import get_remote_data_source_tables
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_remote_data_source_tables.asyncio(
	    'sample_connection',
	    'sample_schema',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.data_sources import get_remote_data_source_tables
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_remote_data_source_tables.sync(
	    'sample_connection',
	    'sample_schema',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.data_sources import get_remote_data_source_tables
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_remote_data_source_tables.asyncio(
	    'sample_connection',
	    'sample_schema',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    [ {
	  "alreadyImported" : false
	}, {
	  "alreadyImported" : false
	}, {
	  "alreadyImported" : false
	} ]
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
|[connection_test_model](../../models/data_sources/#connectiontestmodel)||[ConnectionTestModel](../../models/data_sources/#connectiontestmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|verify_name_uniqueness|Verify if the connection name is unique, the default value is true|boolean| |




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Basic connection model|[ConnectionModel](../../models/Common/#connectionmodel)| |




**Usage examples**  

=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/datasource/testconnection^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection_name\":\"sample_connection\",\"parallel_jobs_limit\":4,\"provider_type\":\"postgresql\",\"postgresql\":{\"host\":\"localhost\",\"port\":\"5432\",\"database\":\"db\",\"user\":\"PASSWD\",\"sslmode\":\"disable\"},\"run_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true},\"run_profiling_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"profiling\"},\"run_monitoring_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"monitoring\"},\"run_partition_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"partitioned\"},\"collect_statistics_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"columnNames\":[]},\"data_clean_job_template\":{\"connection\":\"sample_connection\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true},\"can_edit\":false,\"can_collect_statistics\":true,\"can_run_checks\":true,\"can_delete_data\":true}"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.data_sources import test_connection
	from dqops.client.models import CheckSearchFilters, \
	                                ConnectionModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PostgresqlParametersSpec, \
	                                PostgresqlSslMode, \
	                                ProviderType, \
	                                StatisticsCollectorSearchFilters
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ConnectionModel(
		connection_name='sample_connection',
		parallel_jobs_limit=4,
		provider_type=ProviderType.postgresql,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.disable
		),
		run_checks_job_template=ConnectionModel(
			connection='sample_connection',
			enabled=True
		),
		run_profiling_checks_job_template=ConnectionModel(
			check_type=CheckType.profiling,
			connection='sample_connection',
			enabled=True
		),
		run_monitoring_checks_job_template=ConnectionModel(
			check_type=CheckType.monitoring,
			connection='sample_connection',
			enabled=True
		),
		run_partition_checks_job_template=ConnectionModel(
			check_type=CheckType.partitioned,
			connection='sample_connection',
			enabled=True
		),
		collect_statistics_job_template=ConnectionModel(
			column_names=[
			
			],
			connection='sample_connection',
			enabled=True
		),
		data_clean_job_template=ConnectionModel(
			connection='sample_connection',
			delete_errors=True,
			delete_statistics=True,
			delete_check_results=True,
			delete_sensor_readouts=True
		),
		can_edit=False,
		can_collect_statistics=True,
		can_run_checks=True,
		can_delete_data=True
	)
	
	test_connection.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.data_sources import test_connection
	from dqops.client.models import CheckSearchFilters, \
	                                ConnectionModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PostgresqlParametersSpec, \
	                                PostgresqlSslMode, \
	                                ProviderType, \
	                                StatisticsCollectorSearchFilters
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ConnectionModel(
		connection_name='sample_connection',
		parallel_jobs_limit=4,
		provider_type=ProviderType.postgresql,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.disable
		),
		run_checks_job_template=ConnectionModel(
			connection='sample_connection',
			enabled=True
		),
		run_profiling_checks_job_template=ConnectionModel(
			check_type=CheckType.profiling,
			connection='sample_connection',
			enabled=True
		),
		run_monitoring_checks_job_template=ConnectionModel(
			check_type=CheckType.monitoring,
			connection='sample_connection',
			enabled=True
		),
		run_partition_checks_job_template=ConnectionModel(
			check_type=CheckType.partitioned,
			connection='sample_connection',
			enabled=True
		),
		collect_statistics_job_template=ConnectionModel(
			column_names=[
			
			],
			connection='sample_connection',
			enabled=True
		),
		data_clean_job_template=ConnectionModel(
			connection='sample_connection',
			delete_errors=True,
			delete_statistics=True,
			delete_check_results=True,
			delete_sensor_readouts=True
		),
		can_edit=False,
		can_collect_statistics=True,
		can_run_checks=True,
		can_delete_data=True
	)
	
	async_result = test_connection.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.data_sources import test_connection
	from dqops.client.models import CheckSearchFilters, \
	                                ConnectionModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PostgresqlParametersSpec, \
	                                PostgresqlSslMode, \
	                                ProviderType, \
	                                StatisticsCollectorSearchFilters
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ConnectionModel(
		connection_name='sample_connection',
		parallel_jobs_limit=4,
		provider_type=ProviderType.postgresql,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.disable
		),
		run_checks_job_template=ConnectionModel(
			connection='sample_connection',
			enabled=True
		),
		run_profiling_checks_job_template=ConnectionModel(
			check_type=CheckType.profiling,
			connection='sample_connection',
			enabled=True
		),
		run_monitoring_checks_job_template=ConnectionModel(
			check_type=CheckType.monitoring,
			connection='sample_connection',
			enabled=True
		),
		run_partition_checks_job_template=ConnectionModel(
			check_type=CheckType.partitioned,
			connection='sample_connection',
			enabled=True
		),
		collect_statistics_job_template=ConnectionModel(
			column_names=[
			
			],
			connection='sample_connection',
			enabled=True
		),
		data_clean_job_template=ConnectionModel(
			connection='sample_connection',
			delete_errors=True,
			delete_statistics=True,
			delete_check_results=True,
			delete_sensor_readouts=True
		),
		can_edit=False,
		can_collect_statistics=True,
		can_run_checks=True,
		can_delete_data=True
	)
	
	test_connection.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.data_sources import test_connection
	from dqops.client.models import CheckSearchFilters, \
	                                ConnectionModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PostgresqlParametersSpec, \
	                                PostgresqlSslMode, \
	                                ProviderType, \
	                                StatisticsCollectorSearchFilters
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ConnectionModel(
		connection_name='sample_connection',
		parallel_jobs_limit=4,
		provider_type=ProviderType.postgresql,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.disable
		),
		run_checks_job_template=ConnectionModel(
			connection='sample_connection',
			enabled=True
		),
		run_profiling_checks_job_template=ConnectionModel(
			check_type=CheckType.profiling,
			connection='sample_connection',
			enabled=True
		),
		run_monitoring_checks_job_template=ConnectionModel(
			check_type=CheckType.monitoring,
			connection='sample_connection',
			enabled=True
		),
		run_partition_checks_job_template=ConnectionModel(
			check_type=CheckType.partitioned,
			connection='sample_connection',
			enabled=True
		),
		collect_statistics_job_template=ConnectionModel(
			column_names=[
			
			],
			connection='sample_connection',
			enabled=True
		),
		data_clean_job_template=ConnectionModel(
			connection='sample_connection',
			delete_errors=True,
			delete_statistics=True,
			delete_check_results=True,
			delete_sensor_readouts=True
		),
		can_edit=False,
		can_collect_statistics=True,
		can_run_checks=True,
		can_delete_data=True
	)
	
	async_result = test_connection.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    { }
    ```


