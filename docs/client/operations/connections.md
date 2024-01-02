Manages connections to monitored data sources  


___  
## bulk_activate_connection_checks  
Activates all named check on this connection in the locations specified by filter  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/bulk_activate_connection_checks.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/checks/{checkName}/bulkactivate  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|check_name|Check name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Check search filters and rules configuration|[AllChecksPatchParameters](../../models/connections/#allcheckspatchparameters)| |




**Usage examples**  

=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/checks/sample_check/bulkactivate^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"check_search_filters\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"column\":\"sample_column\",\"columnDataType\":\"string\"},\"check_model_patch\":{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false},\"override_conflicts\":true}"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import bulk_activate_connection_checks
	from dqops.client.models import AllChecksPatchParameters, \
	                                CheckModel, \
	                                CheckSearchFilters, \
	                                FieldModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = AllChecksPatchParameters(
		check_search_filters=CheckSearchFilters(
			column='sample_column',
			column_data_type='string',
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		check_model_patch=CheckModel(
			check_name='sample_check',
			help_text='Sample help text',
			sensor_parameters=[
			
			],
			sensor_name='sample_target/sample_category/sample_sensor',
			quality_dimension='sample_quality_dimension',
			supports_grouping=False,
			disabled=False,
			exclude_from_kpi=False,
			include_in_sla=False,
			configured=False,
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		),
		override_conflicts=True
	)
	
	bulk_activate_connection_checks.sync(
	    'sample_connection',
	    'sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import bulk_activate_connection_checks
	from dqops.client.models import AllChecksPatchParameters, \
	                                CheckModel, \
	                                CheckSearchFilters, \
	                                FieldModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = AllChecksPatchParameters(
		check_search_filters=CheckSearchFilters(
			column='sample_column',
			column_data_type='string',
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		check_model_patch=CheckModel(
			check_name='sample_check',
			help_text='Sample help text',
			sensor_parameters=[
			
			],
			sensor_name='sample_target/sample_category/sample_sensor',
			quality_dimension='sample_quality_dimension',
			supports_grouping=False,
			disabled=False,
			exclude_from_kpi=False,
			include_in_sla=False,
			configured=False,
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		),
		override_conflicts=True
	)
	
	async_result = bulk_activate_connection_checks.asyncio(
	    'sample_connection',
	    'sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import bulk_activate_connection_checks
	from dqops.client.models import AllChecksPatchParameters, \
	                                CheckModel, \
	                                CheckSearchFilters, \
	                                FieldModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = AllChecksPatchParameters(
		check_search_filters=CheckSearchFilters(
			column='sample_column',
			column_data_type='string',
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		check_model_patch=CheckModel(
			check_name='sample_check',
			help_text='Sample help text',
			sensor_parameters=[
			
			],
			sensor_name='sample_target/sample_category/sample_sensor',
			quality_dimension='sample_quality_dimension',
			supports_grouping=False,
			disabled=False,
			exclude_from_kpi=False,
			include_in_sla=False,
			configured=False,
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		),
		override_conflicts=True
	)
	
	bulk_activate_connection_checks.sync(
	    'sample_connection',
	    'sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import bulk_activate_connection_checks
	from dqops.client.models import AllChecksPatchParameters, \
	                                CheckModel, \
	                                CheckSearchFilters, \
	                                FieldModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = AllChecksPatchParameters(
		check_search_filters=CheckSearchFilters(
			column='sample_column',
			column_data_type='string',
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		check_model_patch=CheckModel(
			check_name='sample_check',
			help_text='Sample help text',
			sensor_parameters=[
			
			],
			sensor_name='sample_target/sample_category/sample_sensor',
			quality_dimension='sample_quality_dimension',
			supports_grouping=False,
			disabled=False,
			exclude_from_kpi=False,
			include_in_sla=False,
			configured=False,
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		),
		override_conflicts=True
	)
	
	async_result = bulk_activate_connection_checks.asyncio(
	    'sample_connection',
	    'sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





___  
## bulk_deactivate_connection_checks  
Deactivates (deletes) all named check on this connection in the locations specified by filter  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/bulk_deactivate_connection_checks.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/checks/{checkName}/bulkdeactivate  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|check_name|Check name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Check search filters and table/column selectors.|[BulkCheckDeactivateParameters](../../models/connections/#bulkcheckdeactivateparameters)| |




**Usage examples**  

=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/checks/sample_check/bulkdeactivate^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"check_search_filters\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"column\":\"sample_column\",\"columnDataType\":\"string\"}}"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import bulk_deactivate_connection_checks
	from dqops.client.models import BulkCheckDeactivateParameters, \
	                                CheckSearchFilters
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = BulkCheckDeactivateParameters(
		check_search_filters=CheckSearchFilters(
			column='sample_column',
			column_data_type='string',
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		)
	)
	
	bulk_deactivate_connection_checks.sync(
	    'sample_connection',
	    'sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import bulk_deactivate_connection_checks
	from dqops.client.models import BulkCheckDeactivateParameters, \
	                                CheckSearchFilters
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = BulkCheckDeactivateParameters(
		check_search_filters=CheckSearchFilters(
			column='sample_column',
			column_data_type='string',
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		)
	)
	
	async_result = bulk_deactivate_connection_checks.asyncio(
	    'sample_connection',
	    'sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import bulk_deactivate_connection_checks
	from dqops.client.models import BulkCheckDeactivateParameters, \
	                                CheckSearchFilters
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = BulkCheckDeactivateParameters(
		check_search_filters=CheckSearchFilters(
			column='sample_column',
			column_data_type='string',
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		)
	)
	
	bulk_deactivate_connection_checks.sync(
	    'sample_connection',
	    'sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import bulk_deactivate_connection_checks
	from dqops.client.models import BulkCheckDeactivateParameters, \
	                                CheckSearchFilters
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = BulkCheckDeactivateParameters(
		check_search_filters=CheckSearchFilters(
			column='sample_column',
			column_data_type='string',
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		)
	)
	
	async_result = bulk_deactivate_connection_checks.asyncio(
	    'sample_connection',
	    'sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





___  
## create_connection  
Creates a new connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/create_connection.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Connection specification|[ConnectionSpec](../../../reference/yaml/ConnectionYaml/#connectionspec)| |




**Usage examples**  

=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"provider_type\":\"postgresql\",\"postgresql\":{\"host\":\"localhost\",\"port\":\"5432\",\"database\":\"db\",\"user\":\"PASSWD\",\"sslmode\":\"disable\"},\"parallel_jobs_limit\":4,\"incident_grouping\":{\"grouping_level\":\"table_dimension_category\",\"minimum_severity\":\"warning\",\"max_incident_length_days\":60,\"mute_for_days\":60}}"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import create_connection
	from dqops.client.models import ConnectionIncidentGroupingSpec, \
	                                ConnectionSpec, \
	                                IncidentGroupingLevel, \
	                                IncidentWebhookNotificationsSpec, \
	                                MinimumGroupingSeverityLevel, \
	                                PostgresqlParametersSpec, \
	                                PostgresqlSslMode, \
	                                ProviderType
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ConnectionSpec(
		provider_type=ProviderType.postgresql,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.disable
		),
		parallel_jobs_limit=4,
		incident_grouping=ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.table_dimension_category,
			minimum_severity=MinimumGroupingSeverityLevel.warning,
			divide_by_data_groups=False,
			max_incident_length_days=60,
			mute_for_days=60,
			disabled=False
		)
	)
	
	create_connection.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import create_connection
	from dqops.client.models import ConnectionIncidentGroupingSpec, \
	                                ConnectionSpec, \
	                                IncidentGroupingLevel, \
	                                IncidentWebhookNotificationsSpec, \
	                                MinimumGroupingSeverityLevel, \
	                                PostgresqlParametersSpec, \
	                                PostgresqlSslMode, \
	                                ProviderType
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ConnectionSpec(
		provider_type=ProviderType.postgresql,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.disable
		),
		parallel_jobs_limit=4,
		incident_grouping=ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.table_dimension_category,
			minimum_severity=MinimumGroupingSeverityLevel.warning,
			divide_by_data_groups=False,
			max_incident_length_days=60,
			mute_for_days=60,
			disabled=False
		)
	)
	
	async_result = create_connection.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import create_connection
	from dqops.client.models import ConnectionIncidentGroupingSpec, \
	                                ConnectionSpec, \
	                                IncidentGroupingLevel, \
	                                IncidentWebhookNotificationsSpec, \
	                                MinimumGroupingSeverityLevel, \
	                                PostgresqlParametersSpec, \
	                                PostgresqlSslMode, \
	                                ProviderType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ConnectionSpec(
		provider_type=ProviderType.postgresql,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.disable
		),
		parallel_jobs_limit=4,
		incident_grouping=ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.table_dimension_category,
			minimum_severity=MinimumGroupingSeverityLevel.warning,
			divide_by_data_groups=False,
			max_incident_length_days=60,
			mute_for_days=60,
			disabled=False
		)
	)
	
	create_connection.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import create_connection
	from dqops.client.models import ConnectionIncidentGroupingSpec, \
	                                ConnectionSpec, \
	                                IncidentGroupingLevel, \
	                                IncidentWebhookNotificationsSpec, \
	                                MinimumGroupingSeverityLevel, \
	                                PostgresqlParametersSpec, \
	                                PostgresqlSslMode, \
	                                ProviderType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ConnectionSpec(
		provider_type=ProviderType.postgresql,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.disable
		),
		parallel_jobs_limit=4,
		incident_grouping=ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.table_dimension_category,
			minimum_severity=MinimumGroupingSeverityLevel.warning,
			divide_by_data_groups=False,
			max_incident_length_days=60,
			mute_for_days=60,
			disabled=False
		)
	)
	
	async_result = create_connection.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





___  
## create_connection_basic  
Creates a new connection given the basic information.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/create_connection_basic.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}/basic  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Basic connection model|[ConnectionModel](../../models/Common/#connectionmodel)| |




**Usage examples**  

=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/basic^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection_name\":\"sample_connection\",\"parallel_jobs_limit\":4,\"provider_type\":\"postgresql\",\"postgresql\":{\"host\":\"localhost\",\"port\":\"5432\",\"database\":\"db\",\"user\":\"PASSWD\",\"sslmode\":\"disable\"},\"run_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true},\"run_profiling_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"profiling\"},\"run_monitoring_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"monitoring\"},\"run_partition_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"partitioned\"},\"collect_statistics_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"columnNames\":[]},\"data_clean_job_template\":{\"connection\":\"sample_connection\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true},\"can_edit\":false,\"can_collect_statistics\":true,\"can_run_checks\":true,\"can_delete_data\":true}"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import create_connection_basic
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
	
	create_connection_basic.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import create_connection_basic
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
	
	async_result = create_connection_basic.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import create_connection_basic
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
	
	create_connection_basic.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import create_connection_basic
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
	
	async_result = create_connection_basic.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





___  
## delete_connection  
Deletes a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/delete_connection.py)
  

**DELETE**
```
http://localhost:8888/api/connections/{connectionName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_queue_job_id](../../models/Common/#dqoqueuejobid)||[DqoQueueJobId](../../models/Common/#dqoqueuejobid)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  

=== "curl"
      
    ```bash
    curl -X DELETE http://localhost:8888/api/connections/sample_connection^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import delete_connection
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	delete_connection.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import delete_connection
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	async_result = delete_connection.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import delete_connection
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	delete_connection.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import delete_connection
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	async_result = delete_connection.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    {
	  "jobId" : 10832,
	  "createdAt" : "2007-10-11T13:42:00Z"
	}
    ```


___  
## get_all_connections  
Returns a list of connections (data sources)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_all_connections.py)
  

**GET**
```
http://localhost:8888/api/connections  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_model||List[[ConnectionModel](../../models/Common/#connectionmodel)]|








**Usage examples**  

=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_all_connections
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_all_connections.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_all_connections
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_all_connections.asyncio(
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_all_connections
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_all_connections.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_all_connections
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_all_connections.asyncio(
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    [ {
	  "connection_name" : "sample_connection",
	  "parallel_jobs_limit" : 4,
	  "provider_type" : "postgresql",
	  "postgresql" : {
	    "host" : "localhost",
	    "port" : "5432",
	    "database" : "db",
	    "user" : "PASSWD",
	    "sslmode" : "disable"
	  },
	  "run_checks_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true
	  },
	  "run_profiling_checks_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true,
	    "checkType" : "profiling"
	  },
	  "run_monitoring_checks_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true,
	    "checkType" : "monitoring"
	  },
	  "run_partition_checks_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true,
	    "checkType" : "partitioned"
	  },
	  "collect_statistics_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true,
	    "columnNames" : [ ]
	  },
	  "data_clean_job_template" : {
	    "connection" : "sample_connection",
	    "deleteErrors" : true,
	    "deleteStatistics" : true,
	    "deleteCheckResults" : true,
	    "deleteSensorReadouts" : true
	  },
	  "can_edit" : false,
	  "can_collect_statistics" : true,
	  "can_run_checks" : true,
	  "can_delete_data" : true
	}, {
	  "connection_name" : "sample_connection",
	  "parallel_jobs_limit" : 4,
	  "provider_type" : "postgresql",
	  "postgresql" : {
	    "host" : "localhost",
	    "port" : "5432",
	    "database" : "db",
	    "user" : "PASSWD",
	    "sslmode" : "disable"
	  },
	  "run_checks_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true
	  },
	  "run_profiling_checks_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true,
	    "checkType" : "profiling"
	  },
	  "run_monitoring_checks_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true,
	    "checkType" : "monitoring"
	  },
	  "run_partition_checks_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true,
	    "checkType" : "partitioned"
	  },
	  "collect_statistics_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true,
	    "columnNames" : [ ]
	  },
	  "data_clean_job_template" : {
	    "connection" : "sample_connection",
	    "deleteErrors" : true,
	    "deleteStatistics" : true,
	    "deleteCheckResults" : true,
	    "deleteSensorReadouts" : true
	  },
	  "can_edit" : false,
	  "can_collect_statistics" : true,
	  "can_run_checks" : true,
	  "can_delete_data" : true
	}, {
	  "connection_name" : "sample_connection",
	  "parallel_jobs_limit" : 4,
	  "provider_type" : "postgresql",
	  "postgresql" : {
	    "host" : "localhost",
	    "port" : "5432",
	    "database" : "db",
	    "user" : "PASSWD",
	    "sslmode" : "disable"
	  },
	  "run_checks_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true
	  },
	  "run_profiling_checks_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true,
	    "checkType" : "profiling"
	  },
	  "run_monitoring_checks_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true,
	    "checkType" : "monitoring"
	  },
	  "run_partition_checks_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true,
	    "checkType" : "partitioned"
	  },
	  "collect_statistics_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true,
	    "columnNames" : [ ]
	  },
	  "data_clean_job_template" : {
	    "connection" : "sample_connection",
	    "deleteErrors" : true,
	    "deleteStatistics" : true,
	    "deleteCheckResults" : true,
	    "deleteSensorReadouts" : true
	  },
	  "can_edit" : false,
	  "can_collect_statistics" : true,
	  "can_run_checks" : true,
	  "can_delete_data" : true
	} ]
    ```


___  
## get_connection  
Return the full details of a connection given the connection name  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[connection_specification_model](../../models/connections/#connectionspecificationmodel)||[ConnectionSpecificationModel](../../models/connections/#connectionspecificationmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  

=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_connection.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_connection.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_connection.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_connection.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    {
	  "can_edit" : false
	}
    ```


___  
## get_connection_basic  
Return the basic details of a connection given the connection name  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_basic.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/basic  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[connection_model](../../models/Common/#connectionmodel)||[ConnectionModel](../../models/Common/#connectionmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  

=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/basic^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_basic
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_connection_basic.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_basic
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_connection_basic.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_basic
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_connection_basic.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_basic
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_connection_basic.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    {
	  "connection_name" : "sample_connection",
	  "parallel_jobs_limit" : 4,
	  "provider_type" : "postgresql",
	  "postgresql" : {
	    "host" : "localhost",
	    "port" : "5432",
	    "database" : "db",
	    "user" : "PASSWD",
	    "sslmode" : "disable"
	  },
	  "run_checks_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true
	  },
	  "run_profiling_checks_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true,
	    "checkType" : "profiling"
	  },
	  "run_monitoring_checks_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true,
	    "checkType" : "monitoring"
	  },
	  "run_partition_checks_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true,
	    "checkType" : "partitioned"
	  },
	  "collect_statistics_job_template" : {
	    "connection" : "sample_connection",
	    "enabled" : true,
	    "columnNames" : [ ]
	  },
	  "data_clean_job_template" : {
	    "connection" : "sample_connection",
	    "deleteErrors" : true,
	    "deleteStatistics" : true,
	    "deleteCheckResults" : true,
	    "deleteSensorReadouts" : true
	  },
	  "can_edit" : false,
	  "can_collect_statistics" : true,
	  "can_run_checks" : true,
	  "can_delete_data" : true
	}
    ```


___  
## get_connection_comments  
Return the comments for a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_comments.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/comments  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|comment_spec||List[[CommentSpec](../../models/Common/#commentspec)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  

=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/comments^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_comments
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_connection_comments.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_comments
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_connection_comments.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_comments
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_connection_comments.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_comments
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_connection_comments.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    [ {
	  "date" : "2007-12-03T10:15:30",
	  "comment_by" : "sample_user",
	  "comment" : "Sample comment"
	}, {
	  "date" : "2007-12-03T10:15:30",
	  "comment_by" : "sample_user",
	  "comment" : "Sample comment"
	}, {
	  "date" : "2007-12-03T10:15:30",
	  "comment_by" : "sample_user",
	  "comment" : "Sample comment"
	} ]
    ```


___  
## get_connection_common_columns  
Finds common column names that are used on one or more tables. The list of columns is sorted in descending order by column name.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_common_columns.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/commoncolumns  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|common_column_model||List[[CommonColumnModel](../../models/connections/#CommonColumnModel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  

=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/commoncolumns^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_common_columns
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_connection_common_columns.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_common_columns
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_connection_common_columns.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_common_columns
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_connection_common_columns.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_common_columns
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_connection_common_columns.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    [ {
	  "tables_count" : 0
	}, {
	  "tables_count" : 0
	}, {
	  "tables_count" : 0
	} ]
    ```


___  
## get_connection_default_grouping_configuration  
Return the default data grouping configuration for a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_default_grouping_configuration.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/defaultgroupingconfiguration  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[data_grouping_configuration_spec](../../../reference/yaml/ConnectionYaml/#datagroupingconfigurationspec)||[DataGroupingConfigurationSpec](../../../reference/yaml/ConnectionYaml/#datagroupingconfigurationspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  

=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/defaultgroupingconfiguration^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_default_grouping_configuration
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_connection_default_grouping_configuration.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_default_grouping_configuration
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_connection_default_grouping_configuration.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_default_grouping_configuration
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_connection_default_grouping_configuration.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_default_grouping_configuration
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_connection_default_grouping_configuration.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    {
	  "level_3" : {
	    "source" : "column_value",
	    "column" : "sample_column"
	  }
	}
    ```


___  
## get_connection_incident_grouping  
Retrieves the configuration of data quality incident grouping and incident notifications  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_incident_grouping.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/incidentgrouping  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[connection_incident_grouping_spec](../../../reference/yaml/ConnectionYaml/#connectionincidentgroupingspec)||[ConnectionIncidentGroupingSpec](../../../reference/yaml/ConnectionYaml/#connectionincidentgroupingspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  

=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/incidentgrouping^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_incident_grouping
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_connection_incident_grouping.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_incident_grouping
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_connection_incident_grouping.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_incident_grouping
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_connection_incident_grouping.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_incident_grouping
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_connection_incident_grouping.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    {
	  "grouping_level" : "table_dimension",
	  "minimum_severity" : "warning",
	  "divide_by_data_groups" : true,
	  "max_incident_length_days" : 60,
	  "mute_for_days" : 60,
	  "webhooks" : {
	    "incident_opened_webhook_url" : "https://sample_url.com/opened",
	    "incident_acknowledged_webhook_url" : "https://sample_url.com/acknowledged",
	    "incident_resolved_webhook_url" : "https://sample_url.com/resolved",
	    "incident_muted_webhook_url" : "https://sample_url.com/muted"
	  }
	}
    ```


___  
## get_connection_labels  
Return the labels for a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_labels.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/labels  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|string||List[string]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  

=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/labels^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_labels
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_connection_labels.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_labels
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_connection_labels.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_labels
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_connection_labels.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_labels
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_connection_labels.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    [ "sampleString_1", "sampleString_2", "sampleString_3" ]
    ```


___  
## get_connection_scheduling_group  
Return the schedule for a connection for a scheduling group  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_scheduling_group.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schedules/{schedulingGroup}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[monitoring_schedule_spec](../../models/Common/#monitoringschedulespec)||[MonitoringScheduleSpec](../../models/Common/#monitoringschedulespec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|[scheduling_group](../../models/Common/#checkrunschedulegroup)|Check scheduling group (named schedule)|[CheckRunScheduleGroup](../../models/Common/#checkrunschedulegroup)|:material-check-bold:|






**Usage examples**  

=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schedules/partitioned_daily^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_scheduling_group
	from dqops.client.models import CheckRunScheduleGroup
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_connection_scheduling_group.sync(
	    'sample_connection',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_scheduling_group
	from dqops.client.models import CheckRunScheduleGroup
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_connection_scheduling_group.asyncio(
	    'sample_connection',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_scheduling_group
	from dqops.client.models import CheckRunScheduleGroup
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_connection_scheduling_group.sync(
	    'sample_connection',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_scheduling_group
	from dqops.client.models import CheckRunScheduleGroup
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_connection_scheduling_group.asyncio(
	    'sample_connection',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    {
	  "cron_expression" : "0 12 1 * *"
	}
    ```


___  
## update_connection  
Updates an existing connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Connection specification|[ConnectionSpec](../../../reference/yaml/ConnectionYaml/#connectionspec)| |




**Usage examples**  

=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"provider_type\":\"postgresql\",\"postgresql\":{\"host\":\"localhost\",\"port\":\"5432\",\"database\":\"db\",\"user\":\"PASSWD\",\"sslmode\":\"disable\"},\"parallel_jobs_limit\":4,\"incident_grouping\":{\"grouping_level\":\"table_dimension_category\",\"minimum_severity\":\"warning\",\"max_incident_length_days\":60,\"mute_for_days\":60}}"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection
	from dqops.client.models import ConnectionIncidentGroupingSpec, \
	                                ConnectionSpec, \
	                                IncidentGroupingLevel, \
	                                IncidentWebhookNotificationsSpec, \
	                                MinimumGroupingSeverityLevel, \
	                                PostgresqlParametersSpec, \
	                                PostgresqlSslMode, \
	                                ProviderType
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ConnectionSpec(
		provider_type=ProviderType.postgresql,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.disable
		),
		parallel_jobs_limit=4,
		incident_grouping=ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.table_dimension_category,
			minimum_severity=MinimumGroupingSeverityLevel.warning,
			divide_by_data_groups=False,
			max_incident_length_days=60,
			mute_for_days=60,
			disabled=False
		)
	)
	
	update_connection.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection
	from dqops.client.models import ConnectionIncidentGroupingSpec, \
	                                ConnectionSpec, \
	                                IncidentGroupingLevel, \
	                                IncidentWebhookNotificationsSpec, \
	                                MinimumGroupingSeverityLevel, \
	                                PostgresqlParametersSpec, \
	                                PostgresqlSslMode, \
	                                ProviderType
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ConnectionSpec(
		provider_type=ProviderType.postgresql,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.disable
		),
		parallel_jobs_limit=4,
		incident_grouping=ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.table_dimension_category,
			minimum_severity=MinimumGroupingSeverityLevel.warning,
			divide_by_data_groups=False,
			max_incident_length_days=60,
			mute_for_days=60,
			disabled=False
		)
	)
	
	async_result = update_connection.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection
	from dqops.client.models import ConnectionIncidentGroupingSpec, \
	                                ConnectionSpec, \
	                                IncidentGroupingLevel, \
	                                IncidentWebhookNotificationsSpec, \
	                                MinimumGroupingSeverityLevel, \
	                                PostgresqlParametersSpec, \
	                                PostgresqlSslMode, \
	                                ProviderType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ConnectionSpec(
		provider_type=ProviderType.postgresql,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.disable
		),
		parallel_jobs_limit=4,
		incident_grouping=ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.table_dimension_category,
			minimum_severity=MinimumGroupingSeverityLevel.warning,
			divide_by_data_groups=False,
			max_incident_length_days=60,
			mute_for_days=60,
			disabled=False
		)
	)
	
	update_connection.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection
	from dqops.client.models import ConnectionIncidentGroupingSpec, \
	                                ConnectionSpec, \
	                                IncidentGroupingLevel, \
	                                IncidentWebhookNotificationsSpec, \
	                                MinimumGroupingSeverityLevel, \
	                                PostgresqlParametersSpec, \
	                                PostgresqlSslMode, \
	                                ProviderType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ConnectionSpec(
		provider_type=ProviderType.postgresql,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.disable
		),
		parallel_jobs_limit=4,
		incident_grouping=ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.table_dimension_category,
			minimum_severity=MinimumGroupingSeverityLevel.warning,
			divide_by_data_groups=False,
			max_incident_length_days=60,
			mute_for_days=60,
			disabled=False
		)
	)
	
	async_result = update_connection.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





___  
## update_connection_basic  
Updates the basic information of a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_basic.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/basic  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Connection basic details|[ConnectionModel](../../models/Common/#connectionmodel)| |




**Usage examples**  

=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/basic^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection_name\":\"sample_connection\",\"parallel_jobs_limit\":4,\"provider_type\":\"postgresql\",\"postgresql\":{\"host\":\"localhost\",\"port\":\"5432\",\"database\":\"db\",\"user\":\"PASSWD\",\"sslmode\":\"disable\"},\"run_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true},\"run_profiling_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"profiling\"},\"run_monitoring_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"monitoring\"},\"run_partition_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"partitioned\"},\"collect_statistics_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"columnNames\":[]},\"data_clean_job_template\":{\"connection\":\"sample_connection\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true},\"can_edit\":false,\"can_collect_statistics\":true,\"can_run_checks\":true,\"can_delete_data\":true}"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_basic
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
	
	update_connection_basic.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_basic
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
	
	async_result = update_connection_basic.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_basic
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
	
	update_connection_basic.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_basic
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
	
	async_result = update_connection_basic.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





___  
## update_connection_comments  
Updates (replaces) the list of comments of a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_comments.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/comments  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|List of comments|List[[CommentSpec](../../models/Common/#commentspec)]| |




**Usage examples**  

=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/comments^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"[{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"},{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"},{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"}]"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_comments
	from dqops.client.models import CommentSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = [
		CommentSpec(
			date=Some date/time value: [2007-12-03T10:15:30],
			comment_by='sample_user',
			comment='Sample comment'
		),
		CommentSpec(
			date=Some date/time value: [2007-12-03T10:15:30],
			comment_by='sample_user',
			comment='Sample comment'
		),
		CommentSpec(
			date=Some date/time value: [2007-12-03T10:15:30],
			comment_by='sample_user',
			comment='Sample comment'
		)
	]
	
	update_connection_comments.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_comments
	from dqops.client.models import CommentSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = [
		CommentSpec(
			date=Some date/time value: [2007-12-03T10:15:30],
			comment_by='sample_user',
			comment='Sample comment'
		),
		CommentSpec(
			date=Some date/time value: [2007-12-03T10:15:30],
			comment_by='sample_user',
			comment='Sample comment'
		),
		CommentSpec(
			date=Some date/time value: [2007-12-03T10:15:30],
			comment_by='sample_user',
			comment='Sample comment'
		)
	]
	
	async_result = update_connection_comments.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_comments
	from dqops.client.models import CommentSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = [
		CommentSpec(
			date=Some date/time value: [2007-12-03T10:15:30],
			comment_by='sample_user',
			comment='Sample comment'
		),
		CommentSpec(
			date=Some date/time value: [2007-12-03T10:15:30],
			comment_by='sample_user',
			comment='Sample comment'
		),
		CommentSpec(
			date=Some date/time value: [2007-12-03T10:15:30],
			comment_by='sample_user',
			comment='Sample comment'
		)
	]
	
	update_connection_comments.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_comments
	from dqops.client.models import CommentSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = [
		CommentSpec(
			date=Some date/time value: [2007-12-03T10:15:30],
			comment_by='sample_user',
			comment='Sample comment'
		),
		CommentSpec(
			date=Some date/time value: [2007-12-03T10:15:30],
			comment_by='sample_user',
			comment='Sample comment'
		),
		CommentSpec(
			date=Some date/time value: [2007-12-03T10:15:30],
			comment_by='sample_user',
			comment='Sample comment'
		)
	]
	
	async_result = update_connection_comments.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





___  
## update_connection_default_grouping_configuration  
Updates the default data grouping connection of a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_default_grouping_configuration.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/defaultgroupingconfiguration  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Default data grouping configuration to be assigned to a connection|[DataGroupingConfigurationSpec](../../../reference/yaml/ConnectionYaml/#datagroupingconfigurationspec)| |




**Usage examples**  

=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/defaultgroupingconfiguration^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"level_3\":{\"source\":\"column_value\",\"column\":\"sample_column\"}}"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_default_grouping_configuration
	from dqops.client.models import DataGroupingConfigurationSpec, \
	                                DataGroupingDimensionSource, \
	                                DataGroupingDimensionSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DataGroupingConfigurationSpec(
		level_3=DataGroupingDimensionSpec(
			source=DataGroupingDimensionSource.column_value,
			column='sample_column'
		)
	)
	
	update_connection_default_grouping_configuration.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_default_grouping_configuration
	from dqops.client.models import DataGroupingConfigurationSpec, \
	                                DataGroupingDimensionSource, \
	                                DataGroupingDimensionSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DataGroupingConfigurationSpec(
		level_3=DataGroupingDimensionSpec(
			source=DataGroupingDimensionSource.column_value,
			column='sample_column'
		)
	)
	
	async_result = update_connection_default_grouping_configuration.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_default_grouping_configuration
	from dqops.client.models import DataGroupingConfigurationSpec, \
	                                DataGroupingDimensionSource, \
	                                DataGroupingDimensionSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DataGroupingConfigurationSpec(
		level_3=DataGroupingDimensionSpec(
			source=DataGroupingDimensionSource.column_value,
			column='sample_column'
		)
	)
	
	update_connection_default_grouping_configuration.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_default_grouping_configuration
	from dqops.client.models import DataGroupingConfigurationSpec, \
	                                DataGroupingDimensionSource, \
	                                DataGroupingDimensionSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DataGroupingConfigurationSpec(
		level_3=DataGroupingDimensionSpec(
			source=DataGroupingDimensionSource.column_value,
			column='sample_column'
		)
	)
	
	async_result = update_connection_default_grouping_configuration.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





___  
## update_connection_incident_grouping  
Updates (replaces) configuration of incident grouping and notifications on a connection (data source) level.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_incident_grouping.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/incidentgrouping  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Incident grouping and notification configuration|[ConnectionIncidentGroupingSpec](../../../reference/yaml/ConnectionYaml/#connectionincidentgroupingspec)| |




**Usage examples**  

=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/incidentgrouping^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"grouping_level\":\"table_dimension\",\"minimum_severity\":\"warning\",\"divide_by_data_groups\":true,\"max_incident_length_days\":60,\"mute_for_days\":60,\"webhooks\":{\"incident_opened_webhook_url\":\"https://sample_url.com/opened\",\"incident_acknowledged_webhook_url\":\"https://sample_url.com/acknowledged\",\"incident_resolved_webhook_url\":\"https://sample_url.com/resolved\",\"incident_muted_webhook_url\":\"https://sample_url.com/muted\"}}"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_incident_grouping
	from dqops.client.models import ConnectionIncidentGroupingSpec, \
	                                IncidentGroupingLevel, \
	                                IncidentWebhookNotificationsSpec, \
	                                MinimumGroupingSeverityLevel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ConnectionIncidentGroupingSpec(
		grouping_level=IncidentGroupingLevel.table_dimension,
		minimum_severity=MinimumGroupingSeverityLevel.warning,
		divide_by_data_groups=True,
		max_incident_length_days=60,
		mute_for_days=60,
		disabled=False,
		webhooks=IncidentWebhookNotificationsSpec(
			incident_opened_webhook_url='https://sample_url.com/opened',
			incident_acknowledged_webhook_url='https://sample_url.com/acknowledged',
			incident_resolved_webhook_url='https://sample_url.com/resolved',
			incident_muted_webhook_url='https://sample_url.com/muted'
		)
	)
	
	update_connection_incident_grouping.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_incident_grouping
	from dqops.client.models import ConnectionIncidentGroupingSpec, \
	                                IncidentGroupingLevel, \
	                                IncidentWebhookNotificationsSpec, \
	                                MinimumGroupingSeverityLevel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ConnectionIncidentGroupingSpec(
		grouping_level=IncidentGroupingLevel.table_dimension,
		minimum_severity=MinimumGroupingSeverityLevel.warning,
		divide_by_data_groups=True,
		max_incident_length_days=60,
		mute_for_days=60,
		disabled=False,
		webhooks=IncidentWebhookNotificationsSpec(
			incident_opened_webhook_url='https://sample_url.com/opened',
			incident_acknowledged_webhook_url='https://sample_url.com/acknowledged',
			incident_resolved_webhook_url='https://sample_url.com/resolved',
			incident_muted_webhook_url='https://sample_url.com/muted'
		)
	)
	
	async_result = update_connection_incident_grouping.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_incident_grouping
	from dqops.client.models import ConnectionIncidentGroupingSpec, \
	                                IncidentGroupingLevel, \
	                                IncidentWebhookNotificationsSpec, \
	                                MinimumGroupingSeverityLevel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ConnectionIncidentGroupingSpec(
		grouping_level=IncidentGroupingLevel.table_dimension,
		minimum_severity=MinimumGroupingSeverityLevel.warning,
		divide_by_data_groups=True,
		max_incident_length_days=60,
		mute_for_days=60,
		disabled=False,
		webhooks=IncidentWebhookNotificationsSpec(
			incident_opened_webhook_url='https://sample_url.com/opened',
			incident_acknowledged_webhook_url='https://sample_url.com/acknowledged',
			incident_resolved_webhook_url='https://sample_url.com/resolved',
			incident_muted_webhook_url='https://sample_url.com/muted'
		)
	)
	
	update_connection_incident_grouping.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_incident_grouping
	from dqops.client.models import ConnectionIncidentGroupingSpec, \
	                                IncidentGroupingLevel, \
	                                IncidentWebhookNotificationsSpec, \
	                                MinimumGroupingSeverityLevel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ConnectionIncidentGroupingSpec(
		grouping_level=IncidentGroupingLevel.table_dimension,
		minimum_severity=MinimumGroupingSeverityLevel.warning,
		divide_by_data_groups=True,
		max_incident_length_days=60,
		mute_for_days=60,
		disabled=False,
		webhooks=IncidentWebhookNotificationsSpec(
			incident_opened_webhook_url='https://sample_url.com/opened',
			incident_acknowledged_webhook_url='https://sample_url.com/acknowledged',
			incident_resolved_webhook_url='https://sample_url.com/resolved',
			incident_muted_webhook_url='https://sample_url.com/muted'
		)
	)
	
	async_result = update_connection_incident_grouping.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





___  
## update_connection_labels  
Updates the list of labels of a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_labels.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/labels  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|List of labels|List[string]| |




**Usage examples**  

=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/labels^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"[\"sampleString_1\",\"sampleString_2\",\"sampleString_3\"]"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_labels
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = [
		'sampleString_1',
		'sampleString_2',
		'sampleString_3'
	]
	
	update_connection_labels.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_labels
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = [
		'sampleString_1',
		'sampleString_2',
		'sampleString_3'
	]
	
	async_result = update_connection_labels.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_labels
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = [
		'sampleString_1',
		'sampleString_2',
		'sampleString_3'
	]
	
	update_connection_labels.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_labels
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = [
		'sampleString_1',
		'sampleString_2',
		'sampleString_3'
	]
	
	async_result = update_connection_labels.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





___  
## update_connection_scheduling_group  
Updates the schedule of a connection for a scheduling group (named schedule for checks with a similar time series configuration)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_scheduling_group.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schedules/{schedulingGroup}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|[scheduling_group](../../models/Common/#checkrunschedulegroup)|Check scheduling group (named schedule)|[CheckRunScheduleGroup](../../models/Common/#checkrunschedulegroup)|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Monitoring schedule definition to store|[MonitoringScheduleSpec](../../models/Common/#monitoringschedulespec)| |




**Usage examples**  

=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schedules/partitioned_daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"cron_expression\":\"0 12 1 * *\"}"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_scheduling_group
	from dqops.client.models import CheckRunScheduleGroup, \
	                                MonitoringScheduleSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = MonitoringScheduleSpec(
		cron_expression='0 12 1 * *',
		disabled=False
	)
	
	update_connection_scheduling_group.sync(
	    'sample_connection',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_scheduling_group
	from dqops.client.models import CheckRunScheduleGroup, \
	                                MonitoringScheduleSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = MonitoringScheduleSpec(
		cron_expression='0 12 1 * *',
		disabled=False
	)
	
	async_result = update_connection_scheduling_group.asyncio(
	    'sample_connection',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_scheduling_group
	from dqops.client.models import CheckRunScheduleGroup, \
	                                MonitoringScheduleSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = MonitoringScheduleSpec(
		cron_expression='0 12 1 * *',
		disabled=False
	)
	
	update_connection_scheduling_group.sync(
	    'sample_connection',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.connections import update_connection_scheduling_group
	from dqops.client.models import CheckRunScheduleGroup, \
	                                MonitoringScheduleSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = MonitoringScheduleSpec(
		cron_expression='0 12 1 * *',
		disabled=False
	)
	
	async_result = update_connection_scheduling_group.asyncio(
	    'sample_connection',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





