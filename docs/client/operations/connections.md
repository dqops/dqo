---
title: DQOps REST API connections operations
---
# DQOps REST API connections operations
Operations for adding/updating/deleting the configuration of data sources managed by DQOps.


___
## bulk_activate_connection_checks
Activates all named check on this connection in the locations specified by filter

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/bulk_activate_connection_checks.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/checks/{checkName}/bulkactivate
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Check search filters and rules configuration|*[AllChecksPatchParameters](../models/connections.md#allcheckspatchparameters)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/checks/sample_check/bulkactivate^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"check_search_filters\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"column\":\"sample_column\",\"columnDataType\":\"string\"},\"check_model_patch\":{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_error_sampling\":false,\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false},\"override_conflicts\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

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
			supports_error_sampling=False,
			supports_grouping=False,
			standard=False,
			default_check=False,
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
	
	call_result = bulk_activate_connection_checks.sync(
	    'sample_connection',
	    'sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

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
			supports_error_sampling=False,
			supports_grouping=False,
			standard=False,
			default_check=False,
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
	
	call_result = await bulk_activate_connection_checks.asyncio(
	    'sample_connection',
	    'sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

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
			supports_error_sampling=False,
			supports_grouping=False,
			standard=False,
			default_check=False,
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
	
	call_result = bulk_activate_connection_checks.sync(
	    'sample_connection',
	    'sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

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
			supports_error_sampling=False,
			supports_grouping=False,
			standard=False,
			default_check=False,
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
	
	call_result = await bulk_activate_connection_checks.asyncio(
	    'sample_connection',
	    'sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## bulk_deactivate_connection_checks
Deactivates (deletes) all named check on this connection in the locations specified by filter

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/bulk_deactivate_connection_checks.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/checks/{checkName}/bulkdeactivate
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Check search filters and table/column selectors.|*[BulkCheckDeactivateParameters](../models/connections.md#bulkcheckdeactivateparameters)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/checks/sample_check/bulkdeactivate^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"check_search_filters\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"column\":\"sample_column\",\"columnDataType\":\"string\"}}"
	
    ```

    


=== "Python sync client"
    **Execution**

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
	
	call_result = bulk_deactivate_connection_checks.sync(
	    'sample_connection',
	    'sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

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
	
	call_result = await bulk_deactivate_connection_checks.asyncio(
	    'sample_connection',
	    'sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

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
	
	call_result = bulk_deactivate_connection_checks.sync(
	    'sample_connection',
	    'sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

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
	
	call_result = await bulk_deactivate_connection_checks.asyncio(
	    'sample_connection',
	    'sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## create_connection
Creates a new connection

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/create_connection.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/connections/{connectionName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Connection specification|*[ConnectionSpec](../../reference/yaml/ConnectionYaml.md#connectionspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"provider_type\":\"postgresql\",\"postgresql\":{\"host\":\"localhost\",\"port\":\"5432\",\"database\":\"db\",\"user\":\"PASSWD\",\"sslmode\":\"disable\"},\"parallel_jobs_limit\":4,\"incident_grouping\":{\"grouping_level\":\"table_dimension_category\",\"minimum_severity\":\"warning\",\"max_incident_length_days\":60,\"mute_for_days\":60}}"
	
    ```

    


=== "Python sync client"
    **Execution**

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
		provider_type=ProviderType.POSTGRESQL,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.DISABLE
		),
		parallel_jobs_limit=4,
		incident_grouping=ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.TABLE_DIMENSION_CATEGORY,
			minimum_severity=MinimumGroupingSeverityLevel.WARNING,
			divide_by_data_groups=False,
			max_incident_length_days=60,
			mute_for_days=60,
			disabled=False
		)
	)
	
	call_result = create_connection.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

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
		provider_type=ProviderType.POSTGRESQL,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.DISABLE
		),
		parallel_jobs_limit=4,
		incident_grouping=ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.TABLE_DIMENSION_CATEGORY,
			minimum_severity=MinimumGroupingSeverityLevel.WARNING,
			divide_by_data_groups=False,
			max_incident_length_days=60,
			mute_for_days=60,
			disabled=False
		)
	)
	
	call_result = await create_connection.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

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
		provider_type=ProviderType.POSTGRESQL,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.DISABLE
		),
		parallel_jobs_limit=4,
		incident_grouping=ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.TABLE_DIMENSION_CATEGORY,
			minimum_severity=MinimumGroupingSeverityLevel.WARNING,
			divide_by_data_groups=False,
			max_incident_length_days=60,
			mute_for_days=60,
			disabled=False
		)
	)
	
	call_result = create_connection.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

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
		provider_type=ProviderType.POSTGRESQL,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.DISABLE
		),
		parallel_jobs_limit=4,
		incident_grouping=ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.TABLE_DIMENSION_CATEGORY,
			minimum_severity=MinimumGroupingSeverityLevel.WARNING,
			divide_by_data_groups=False,
			max_incident_length_days=60,
			mute_for_days=60,
			disabled=False
		)
	)
	
	call_result = await create_connection.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## create_connection_basic
Creates a new connection given the basic information.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/create_connection_basic.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/connections/{connectionName}/basic
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Basic connection model|*[ConnectionModel](../models/common.md#connectionmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/basic^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection_name\":\"sample_connection\",\"parallel_jobs_limit\":4,\"provider_type\":\"postgresql\",\"postgresql\":{\"host\":\"localhost\",\"port\":\"5432\",\"database\":\"db\",\"user\":\"PASSWD\",\"sslmode\":\"disable\"},\"run_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true},\"run_profiling_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"profiling\"},\"run_monitoring_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"monitoring\"},\"run_partition_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"partitioned\"},\"collect_statistics_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"columnNames\":[]},\"data_clean_job_template\":{\"connection\":\"sample_connection\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"deleteErrorSamples\":false},\"can_edit\":false,\"can_collect_statistics\":true,\"can_run_checks\":true,\"can_delete_data\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

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
		provider_type=ProviderType.POSTGRESQL,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.DISABLE
		),
		run_checks_job_template=CheckSearchFilters(
			connection='sample_connection',
			enabled=True
		),
		run_profiling_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PROFILING,
			connection='sample_connection',
			enabled=True
		),
		run_monitoring_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			connection='sample_connection',
			enabled=True
		),
		run_partition_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PARTITIONED,
			connection='sample_connection',
			enabled=True
		),
		collect_statistics_job_template=StatisticsCollectorSearchFilters(
			column_names=[
			
			],
			connection='sample_connection',
			enabled=True
		),
		data_clean_job_template=DeleteStoredDataQueueJobParameters(
			connection='sample_connection',
			delete_errors=True,
			delete_statistics=True,
			delete_check_results=True,
			delete_sensor_readouts=True,
			delete_error_samples=False
		),
		can_edit=False,
		can_collect_statistics=True,
		can_run_checks=True,
		can_delete_data=True
	)
	
	call_result = create_connection_basic.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

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
		provider_type=ProviderType.POSTGRESQL,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.DISABLE
		),
		run_checks_job_template=CheckSearchFilters(
			connection='sample_connection',
			enabled=True
		),
		run_profiling_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PROFILING,
			connection='sample_connection',
			enabled=True
		),
		run_monitoring_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			connection='sample_connection',
			enabled=True
		),
		run_partition_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PARTITIONED,
			connection='sample_connection',
			enabled=True
		),
		collect_statistics_job_template=StatisticsCollectorSearchFilters(
			column_names=[
			
			],
			connection='sample_connection',
			enabled=True
		),
		data_clean_job_template=DeleteStoredDataQueueJobParameters(
			connection='sample_connection',
			delete_errors=True,
			delete_statistics=True,
			delete_check_results=True,
			delete_sensor_readouts=True,
			delete_error_samples=False
		),
		can_edit=False,
		can_collect_statistics=True,
		can_run_checks=True,
		can_delete_data=True
	)
	
	call_result = await create_connection_basic.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

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
		provider_type=ProviderType.POSTGRESQL,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.DISABLE
		),
		run_checks_job_template=CheckSearchFilters(
			connection='sample_connection',
			enabled=True
		),
		run_profiling_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PROFILING,
			connection='sample_connection',
			enabled=True
		),
		run_monitoring_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			connection='sample_connection',
			enabled=True
		),
		run_partition_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PARTITIONED,
			connection='sample_connection',
			enabled=True
		),
		collect_statistics_job_template=StatisticsCollectorSearchFilters(
			column_names=[
			
			],
			connection='sample_connection',
			enabled=True
		),
		data_clean_job_template=DeleteStoredDataQueueJobParameters(
			connection='sample_connection',
			delete_errors=True,
			delete_statistics=True,
			delete_check_results=True,
			delete_sensor_readouts=True,
			delete_error_samples=False
		),
		can_edit=False,
		can_collect_statistics=True,
		can_run_checks=True,
		can_delete_data=True
	)
	
	call_result = create_connection_basic.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

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
		provider_type=ProviderType.POSTGRESQL,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.DISABLE
		),
		run_checks_job_template=CheckSearchFilters(
			connection='sample_connection',
			enabled=True
		),
		run_profiling_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PROFILING,
			connection='sample_connection',
			enabled=True
		),
		run_monitoring_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			connection='sample_connection',
			enabled=True
		),
		run_partition_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PARTITIONED,
			connection='sample_connection',
			enabled=True
		),
		collect_statistics_job_template=StatisticsCollectorSearchFilters(
			column_names=[
			
			],
			connection='sample_connection',
			enabled=True
		),
		data_clean_job_template=DeleteStoredDataQueueJobParameters(
			connection='sample_connection',
			delete_errors=True,
			delete_statistics=True,
			delete_check_results=True,
			delete_sensor_readouts=True,
			delete_error_samples=False
		),
		can_edit=False,
		can_collect_statistics=True,
		can_run_checks=True,
		can_delete_data=True
	)
	
	call_result = await create_connection_basic.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## delete_connection
Deletes a connection

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/delete_connection.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/connections/{connectionName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`dqo_queue_job_id`](../models/common.md#dqoqueuejobid)</span>||*[DqoQueueJobId](../models/common.md#dqoqueuejobid)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X DELETE http://localhost:8888/api/connections/sample_connection^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "jobId" : 123456789,
		  "createdAt" : "2007-10-11T13:42:00Z"
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import delete_connection
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = delete_connection.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoQueueJobId(
			job_id=123456789,
			created_at='2007-10-11T13:42:00Z'
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import delete_connection
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await delete_connection.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoQueueJobId(
			job_id=123456789,
			created_at='2007-10-11T13:42:00Z'
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import delete_connection
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = delete_connection.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoQueueJobId(
			job_id=123456789,
			created_at='2007-10-11T13:42:00Z'
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import delete_connection
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await delete_connection.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DqoQueueJobId(
			job_id=123456789,
			created_at='2007-10-11T13:42:00Z'
		)
        ```
    
    
    



___
## get_all_connections
Returns a list of connections (data sources)

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_all_connections.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_model`</span>||*List[[ConnectionModel](../models/common.md#connectionmodel)]*|








**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
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
		    "deleteSensorReadouts" : true,
		    "deleteErrorSamples" : false
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
		    "deleteSensorReadouts" : true,
		    "deleteErrorSamples" : false
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
		    "deleteSensorReadouts" : true,
		    "deleteErrorSamples" : false
		  },
		  "can_edit" : false,
		  "can_collect_statistics" : true,
		  "can_run_checks" : true,
		  "can_delete_data" : true
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_all_connections
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_connections.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ConnectionModel(
				connection_name='sample_connection',
				parallel_jobs_limit=4,
				provider_type=ProviderType.POSTGRESQL,
				postgresql=PostgresqlParametersSpec(
					host='localhost',
					port='5432',
					database='db',
					user='PASSWD',
					sslmode=PostgresqlSslMode.DISABLE
				),
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					enabled=True
				),
				collect_statistics_job_template=StatisticsCollectorSearchFilters(
					column_names=[
					
					],
					connection='sample_connection',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			ConnectionModel(
				connection_name='sample_connection',
				parallel_jobs_limit=4,
				provider_type=ProviderType.POSTGRESQL,
				postgresql=PostgresqlParametersSpec(
					host='localhost',
					port='5432',
					database='db',
					user='PASSWD',
					sslmode=PostgresqlSslMode.DISABLE
				),
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					enabled=True
				),
				collect_statistics_job_template=StatisticsCollectorSearchFilters(
					column_names=[
					
					],
					connection='sample_connection',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			ConnectionModel(
				connection_name='sample_connection',
				parallel_jobs_limit=4,
				provider_type=ProviderType.POSTGRESQL,
				postgresql=PostgresqlParametersSpec(
					host='localhost',
					port='5432',
					database='db',
					user='PASSWD',
					sslmode=PostgresqlSslMode.DISABLE
				),
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					enabled=True
				),
				collect_statistics_job_template=StatisticsCollectorSearchFilters(
					column_names=[
					
					],
					connection='sample_connection',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_all_connections
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_connections.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ConnectionModel(
				connection_name='sample_connection',
				parallel_jobs_limit=4,
				provider_type=ProviderType.POSTGRESQL,
				postgresql=PostgresqlParametersSpec(
					host='localhost',
					port='5432',
					database='db',
					user='PASSWD',
					sslmode=PostgresqlSslMode.DISABLE
				),
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					enabled=True
				),
				collect_statistics_job_template=StatisticsCollectorSearchFilters(
					column_names=[
					
					],
					connection='sample_connection',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			ConnectionModel(
				connection_name='sample_connection',
				parallel_jobs_limit=4,
				provider_type=ProviderType.POSTGRESQL,
				postgresql=PostgresqlParametersSpec(
					host='localhost',
					port='5432',
					database='db',
					user='PASSWD',
					sslmode=PostgresqlSslMode.DISABLE
				),
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					enabled=True
				),
				collect_statistics_job_template=StatisticsCollectorSearchFilters(
					column_names=[
					
					],
					connection='sample_connection',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			ConnectionModel(
				connection_name='sample_connection',
				parallel_jobs_limit=4,
				provider_type=ProviderType.POSTGRESQL,
				postgresql=PostgresqlParametersSpec(
					host='localhost',
					port='5432',
					database='db',
					user='PASSWD',
					sslmode=PostgresqlSslMode.DISABLE
				),
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					enabled=True
				),
				collect_statistics_job_template=StatisticsCollectorSearchFilters(
					column_names=[
					
					],
					connection='sample_connection',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_all_connections
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_connections.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ConnectionModel(
				connection_name='sample_connection',
				parallel_jobs_limit=4,
				provider_type=ProviderType.POSTGRESQL,
				postgresql=PostgresqlParametersSpec(
					host='localhost',
					port='5432',
					database='db',
					user='PASSWD',
					sslmode=PostgresqlSslMode.DISABLE
				),
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					enabled=True
				),
				collect_statistics_job_template=StatisticsCollectorSearchFilters(
					column_names=[
					
					],
					connection='sample_connection',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			ConnectionModel(
				connection_name='sample_connection',
				parallel_jobs_limit=4,
				provider_type=ProviderType.POSTGRESQL,
				postgresql=PostgresqlParametersSpec(
					host='localhost',
					port='5432',
					database='db',
					user='PASSWD',
					sslmode=PostgresqlSslMode.DISABLE
				),
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					enabled=True
				),
				collect_statistics_job_template=StatisticsCollectorSearchFilters(
					column_names=[
					
					],
					connection='sample_connection',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			ConnectionModel(
				connection_name='sample_connection',
				parallel_jobs_limit=4,
				provider_type=ProviderType.POSTGRESQL,
				postgresql=PostgresqlParametersSpec(
					host='localhost',
					port='5432',
					database='db',
					user='PASSWD',
					sslmode=PostgresqlSslMode.DISABLE
				),
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					enabled=True
				),
				collect_statistics_job_template=StatisticsCollectorSearchFilters(
					column_names=[
					
					],
					connection='sample_connection',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_all_connections
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_connections.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ConnectionModel(
				connection_name='sample_connection',
				parallel_jobs_limit=4,
				provider_type=ProviderType.POSTGRESQL,
				postgresql=PostgresqlParametersSpec(
					host='localhost',
					port='5432',
					database='db',
					user='PASSWD',
					sslmode=PostgresqlSslMode.DISABLE
				),
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					enabled=True
				),
				collect_statistics_job_template=StatisticsCollectorSearchFilters(
					column_names=[
					
					],
					connection='sample_connection',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			ConnectionModel(
				connection_name='sample_connection',
				parallel_jobs_limit=4,
				provider_type=ProviderType.POSTGRESQL,
				postgresql=PostgresqlParametersSpec(
					host='localhost',
					port='5432',
					database='db',
					user='PASSWD',
					sslmode=PostgresqlSslMode.DISABLE
				),
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					enabled=True
				),
				collect_statistics_job_template=StatisticsCollectorSearchFilters(
					column_names=[
					
					],
					connection='sample_connection',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			ConnectionModel(
				connection_name='sample_connection',
				parallel_jobs_limit=4,
				provider_type=ProviderType.POSTGRESQL,
				postgresql=PostgresqlParametersSpec(
					host='localhost',
					port='5432',
					database='db',
					user='PASSWD',
					sslmode=PostgresqlSslMode.DISABLE
				),
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					enabled=True
				),
				collect_statistics_job_template=StatisticsCollectorSearchFilters(
					column_names=[
					
					],
					connection='sample_connection',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			)
		]
        ```
    
    
    



___
## get_connection
Return the full details of a connection given the connection name

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`connection_specification_model`](../models/connections.md#connectionspecificationmodel)</span>||*[ConnectionSpecificationModel](../models/connections.md#connectionspecificationmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "can_edit" : false
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ConnectionSpecificationModel(can_edit=False)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ConnectionSpecificationModel(can_edit=False)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ConnectionSpecificationModel(can_edit=False)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ConnectionSpecificationModel(can_edit=False)
        ```
    
    
    



___
## get_connection_basic
Return the basic details of a connection given the connection name

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_basic.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/basic
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`connection_model`](../models/common.md#connectionmodel)</span>||*[ConnectionModel](../models/common.md#connectionmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/basic^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
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
		    "deleteSensorReadouts" : true,
		    "deleteErrorSamples" : false
		  },
		  "can_edit" : false,
		  "can_collect_statistics" : true,
		  "can_run_checks" : true,
		  "can_delete_data" : true
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_basic
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection_basic.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ConnectionModel(
			connection_name='sample_connection',
			parallel_jobs_limit=4,
			provider_type=ProviderType.POSTGRESQL,
			postgresql=PostgresqlParametersSpec(
				host='localhost',
				port='5432',
				database='db',
				user='PASSWD',
				sslmode=PostgresqlSslMode.DISABLE
			),
			run_checks_job_template=CheckSearchFilters(
				connection='sample_connection',
				enabled=True
			),
			run_profiling_checks_job_template=CheckSearchFilters(
				check_type=CheckType.PROFILING,
				connection='sample_connection',
				enabled=True
			),
			run_monitoring_checks_job_template=CheckSearchFilters(
				check_type=CheckType.MONITORING,
				connection='sample_connection',
				enabled=True
			),
			run_partition_checks_job_template=CheckSearchFilters(
				check_type=CheckType.PARTITIONED,
				connection='sample_connection',
				enabled=True
			),
			collect_statistics_job_template=StatisticsCollectorSearchFilters(
				column_names=[
				
				],
				connection='sample_connection',
				enabled=True
			),
			data_clean_job_template=DeleteStoredDataQueueJobParameters(
				connection='sample_connection',
				delete_errors=True,
				delete_statistics=True,
				delete_check_results=True,
				delete_sensor_readouts=True,
				delete_error_samples=False
			),
			can_edit=False,
			can_collect_statistics=True,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_basic
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection_basic.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ConnectionModel(
			connection_name='sample_connection',
			parallel_jobs_limit=4,
			provider_type=ProviderType.POSTGRESQL,
			postgresql=PostgresqlParametersSpec(
				host='localhost',
				port='5432',
				database='db',
				user='PASSWD',
				sslmode=PostgresqlSslMode.DISABLE
			),
			run_checks_job_template=CheckSearchFilters(
				connection='sample_connection',
				enabled=True
			),
			run_profiling_checks_job_template=CheckSearchFilters(
				check_type=CheckType.PROFILING,
				connection='sample_connection',
				enabled=True
			),
			run_monitoring_checks_job_template=CheckSearchFilters(
				check_type=CheckType.MONITORING,
				connection='sample_connection',
				enabled=True
			),
			run_partition_checks_job_template=CheckSearchFilters(
				check_type=CheckType.PARTITIONED,
				connection='sample_connection',
				enabled=True
			),
			collect_statistics_job_template=StatisticsCollectorSearchFilters(
				column_names=[
				
				],
				connection='sample_connection',
				enabled=True
			),
			data_clean_job_template=DeleteStoredDataQueueJobParameters(
				connection='sample_connection',
				delete_errors=True,
				delete_statistics=True,
				delete_check_results=True,
				delete_sensor_readouts=True,
				delete_error_samples=False
			),
			can_edit=False,
			can_collect_statistics=True,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_basic
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection_basic.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ConnectionModel(
			connection_name='sample_connection',
			parallel_jobs_limit=4,
			provider_type=ProviderType.POSTGRESQL,
			postgresql=PostgresqlParametersSpec(
				host='localhost',
				port='5432',
				database='db',
				user='PASSWD',
				sslmode=PostgresqlSslMode.DISABLE
			),
			run_checks_job_template=CheckSearchFilters(
				connection='sample_connection',
				enabled=True
			),
			run_profiling_checks_job_template=CheckSearchFilters(
				check_type=CheckType.PROFILING,
				connection='sample_connection',
				enabled=True
			),
			run_monitoring_checks_job_template=CheckSearchFilters(
				check_type=CheckType.MONITORING,
				connection='sample_connection',
				enabled=True
			),
			run_partition_checks_job_template=CheckSearchFilters(
				check_type=CheckType.PARTITIONED,
				connection='sample_connection',
				enabled=True
			),
			collect_statistics_job_template=StatisticsCollectorSearchFilters(
				column_names=[
				
				],
				connection='sample_connection',
				enabled=True
			),
			data_clean_job_template=DeleteStoredDataQueueJobParameters(
				connection='sample_connection',
				delete_errors=True,
				delete_statistics=True,
				delete_check_results=True,
				delete_sensor_readouts=True,
				delete_error_samples=False
			),
			can_edit=False,
			can_collect_statistics=True,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_basic
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection_basic.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ConnectionModel(
			connection_name='sample_connection',
			parallel_jobs_limit=4,
			provider_type=ProviderType.POSTGRESQL,
			postgresql=PostgresqlParametersSpec(
				host='localhost',
				port='5432',
				database='db',
				user='PASSWD',
				sslmode=PostgresqlSslMode.DISABLE
			),
			run_checks_job_template=CheckSearchFilters(
				connection='sample_connection',
				enabled=True
			),
			run_profiling_checks_job_template=CheckSearchFilters(
				check_type=CheckType.PROFILING,
				connection='sample_connection',
				enabled=True
			),
			run_monitoring_checks_job_template=CheckSearchFilters(
				check_type=CheckType.MONITORING,
				connection='sample_connection',
				enabled=True
			),
			run_partition_checks_job_template=CheckSearchFilters(
				check_type=CheckType.PARTITIONED,
				connection='sample_connection',
				enabled=True
			),
			collect_statistics_job_template=StatisticsCollectorSearchFilters(
				column_names=[
				
				],
				connection='sample_connection',
				enabled=True
			),
			data_clean_job_template=DeleteStoredDataQueueJobParameters(
				connection='sample_connection',
				delete_errors=True,
				delete_statistics=True,
				delete_check_results=True,
				delete_sensor_readouts=True,
				delete_error_samples=False
			),
			can_edit=False,
			can_collect_statistics=True,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    



___
## get_connection_comments
Return the comments for a connection

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_comments.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/comments
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`comment_spec`</span>||*List[[CommentSpec](../models/common.md#commentspec)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/comments^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
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
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_comments
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection_comments.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
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
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_comments
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection_comments.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
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
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_comments
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection_comments.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
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
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_comments
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection_comments.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
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
        ```
    
    
    



___
## get_connection_common_columns
Finds common column names that are used on one or more tables. The list of columns is sorted in descending order by column name.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_common_columns.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/commoncolumns
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`common_column_model`</span>||*List[[CommonColumnModel](../models/connections.md#commoncolumnmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/commoncolumns^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "tables_count" : 0
		}, {
		  "tables_count" : 0
		}, {
		  "tables_count" : 0
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_common_columns
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection_common_columns.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CommonColumnModel(tables_count=0),
			CommonColumnModel(tables_count=0),
			CommonColumnModel(tables_count=0)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_common_columns
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection_common_columns.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CommonColumnModel(tables_count=0),
			CommonColumnModel(tables_count=0),
			CommonColumnModel(tables_count=0)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_common_columns
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection_common_columns.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CommonColumnModel(tables_count=0),
			CommonColumnModel(tables_count=0),
			CommonColumnModel(tables_count=0)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_common_columns
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection_common_columns.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CommonColumnModel(tables_count=0),
			CommonColumnModel(tables_count=0),
			CommonColumnModel(tables_count=0)
		]
        ```
    
    
    



___
## get_connection_default_grouping_configuration
Return the default data grouping configuration for a connection

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_default_grouping_configuration.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/defaultgroupingconfiguration
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`data_grouping_configuration_spec`](../../reference/yaml/ConnectionYaml.md#datagroupingconfigurationspec)</span>||*[DataGroupingConfigurationSpec](../../reference/yaml/ConnectionYaml.md#datagroupingconfigurationspec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/defaultgroupingconfiguration^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "level_3" : {
		    "source" : "column_value",
		    "column" : "sample_column"
		  }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_default_grouping_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection_default_grouping_configuration.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DataGroupingConfigurationSpec(
			level_3=DataGroupingDimensionSpec(
				source=DataGroupingDimensionSource.COLUMN_VALUE,
				column='sample_column'
			)
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_default_grouping_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection_default_grouping_configuration.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DataGroupingConfigurationSpec(
			level_3=DataGroupingDimensionSpec(
				source=DataGroupingDimensionSource.COLUMN_VALUE,
				column='sample_column'
			)
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_default_grouping_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection_default_grouping_configuration.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DataGroupingConfigurationSpec(
			level_3=DataGroupingDimensionSpec(
				source=DataGroupingDimensionSource.COLUMN_VALUE,
				column='sample_column'
			)
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_default_grouping_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection_default_grouping_configuration.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DataGroupingConfigurationSpec(
			level_3=DataGroupingDimensionSpec(
				source=DataGroupingDimensionSource.COLUMN_VALUE,
				column='sample_column'
			)
		)
        ```
    
    
    



___
## get_connection_incident_grouping
Retrieves the configuration of data quality incident grouping and incident notifications

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_incident_grouping.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/incidentgrouping
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`connection_incident_grouping_spec`](../../reference/yaml/ConnectionYaml.md#connectionincidentgroupingspec)</span>||*[ConnectionIncidentGroupingSpec](../../reference/yaml/ConnectionYaml.md#connectionincidentgroupingspec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/incidentgrouping^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
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
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_incident_grouping
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection_incident_grouping.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.TABLE_DIMENSION,
			minimum_severity=MinimumGroupingSeverityLevel.WARNING,
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
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_incident_grouping
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection_incident_grouping.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.TABLE_DIMENSION,
			minimum_severity=MinimumGroupingSeverityLevel.WARNING,
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
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_incident_grouping
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection_incident_grouping.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.TABLE_DIMENSION,
			minimum_severity=MinimumGroupingSeverityLevel.WARNING,
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
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_incident_grouping
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection_incident_grouping.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.TABLE_DIMENSION,
			minimum_severity=MinimumGroupingSeverityLevel.WARNING,
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
        ```
    
    
    



___
## get_connection_labels
Return the labels for a connection

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_labels.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/labels
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`string`</span>||*List[string]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/labels^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ "sampleString_1", "sampleString_2", "sampleString_3" ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_labels
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection_labels.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			'sampleString_1',
			'sampleString_2',
			'sampleString_3'
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_labels
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection_labels.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			'sampleString_1',
			'sampleString_2',
			'sampleString_3'
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_labels
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection_labels.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			'sampleString_1',
			'sampleString_2',
			'sampleString_3'
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_labels
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection_labels.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			'sampleString_1',
			'sampleString_2',
			'sampleString_3'
		]
        ```
    
    
    



___
## get_connection_scheduling_group
Return the schedule for a connection for a scheduling group

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_scheduling_group.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schedules/{schedulingGroup}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`monitoring_schedule_spec`](../models/common.md#monitoringschedulespec)</span>||*[MonitoringScheduleSpec](../models/common.md#monitoringschedulespec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`scheduling_group`](../models/common.md#checkrunschedulegroup)</span>|Check scheduling group (named schedule)|*[CheckRunScheduleGroup](../models/common.md#checkrunschedulegroup)*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schedules/partitioned_daily^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "cron_expression" : "0 12 1 * *"
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_scheduling_group
	from dqops.client.models import CheckRunScheduleGroup
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_connection_scheduling_group.sync(
	    'sample_connection',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        MonitoringScheduleSpec(
			cron_expression='0 12 1 * *',
			disabled=False
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.connections import get_connection_scheduling_group
	from dqops.client.models import CheckRunScheduleGroup
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_connection_scheduling_group.asyncio(
	    'sample_connection',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        MonitoringScheduleSpec(
			cron_expression='0 12 1 * *',
			disabled=False
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

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
	
	call_result = get_connection_scheduling_group.sync(
	    'sample_connection',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        MonitoringScheduleSpec(
			cron_expression='0 12 1 * *',
			disabled=False
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

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
	
	call_result = await get_connection_scheduling_group.asyncio(
	    'sample_connection',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        MonitoringScheduleSpec(
			cron_expression='0 12 1 * *',
			disabled=False
		)
        ```
    
    
    



___
## update_connection
Updates an existing connection

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Connection specification|*[ConnectionSpec](../../reference/yaml/ConnectionYaml.md#connectionspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"provider_type\":\"postgresql\",\"postgresql\":{\"host\":\"localhost\",\"port\":\"5432\",\"database\":\"db\",\"user\":\"PASSWD\",\"sslmode\":\"disable\"},\"parallel_jobs_limit\":4,\"incident_grouping\":{\"grouping_level\":\"table_dimension_category\",\"minimum_severity\":\"warning\",\"max_incident_length_days\":60,\"mute_for_days\":60}}"
	
    ```

    


=== "Python sync client"
    **Execution**

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
		provider_type=ProviderType.POSTGRESQL,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.DISABLE
		),
		parallel_jobs_limit=4,
		incident_grouping=ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.TABLE_DIMENSION_CATEGORY,
			minimum_severity=MinimumGroupingSeverityLevel.WARNING,
			divide_by_data_groups=False,
			max_incident_length_days=60,
			mute_for_days=60,
			disabled=False
		)
	)
	
	call_result = update_connection.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

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
		provider_type=ProviderType.POSTGRESQL,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.DISABLE
		),
		parallel_jobs_limit=4,
		incident_grouping=ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.TABLE_DIMENSION_CATEGORY,
			minimum_severity=MinimumGroupingSeverityLevel.WARNING,
			divide_by_data_groups=False,
			max_incident_length_days=60,
			mute_for_days=60,
			disabled=False
		)
	)
	
	call_result = await update_connection.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

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
		provider_type=ProviderType.POSTGRESQL,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.DISABLE
		),
		parallel_jobs_limit=4,
		incident_grouping=ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.TABLE_DIMENSION_CATEGORY,
			minimum_severity=MinimumGroupingSeverityLevel.WARNING,
			divide_by_data_groups=False,
			max_incident_length_days=60,
			mute_for_days=60,
			disabled=False
		)
	)
	
	call_result = update_connection.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

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
		provider_type=ProviderType.POSTGRESQL,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.DISABLE
		),
		parallel_jobs_limit=4,
		incident_grouping=ConnectionIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.TABLE_DIMENSION_CATEGORY,
			minimum_severity=MinimumGroupingSeverityLevel.WARNING,
			divide_by_data_groups=False,
			max_incident_length_days=60,
			mute_for_days=60,
			disabled=False
		)
	)
	
	call_result = await update_connection.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_connection_basic
Updates the basic information of a connection

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_basic.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/basic
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Connection basic details|*[ConnectionModel](../models/common.md#connectionmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/basic^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection_name\":\"sample_connection\",\"parallel_jobs_limit\":4,\"provider_type\":\"postgresql\",\"postgresql\":{\"host\":\"localhost\",\"port\":\"5432\",\"database\":\"db\",\"user\":\"PASSWD\",\"sslmode\":\"disable\"},\"run_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true},\"run_profiling_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"profiling\"},\"run_monitoring_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"monitoring\"},\"run_partition_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"partitioned\"},\"collect_statistics_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"columnNames\":[]},\"data_clean_job_template\":{\"connection\":\"sample_connection\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"deleteErrorSamples\":false},\"can_edit\":false,\"can_collect_statistics\":true,\"can_run_checks\":true,\"can_delete_data\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

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
		provider_type=ProviderType.POSTGRESQL,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.DISABLE
		),
		run_checks_job_template=CheckSearchFilters(
			connection='sample_connection',
			enabled=True
		),
		run_profiling_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PROFILING,
			connection='sample_connection',
			enabled=True
		),
		run_monitoring_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			connection='sample_connection',
			enabled=True
		),
		run_partition_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PARTITIONED,
			connection='sample_connection',
			enabled=True
		),
		collect_statistics_job_template=StatisticsCollectorSearchFilters(
			column_names=[
			
			],
			connection='sample_connection',
			enabled=True
		),
		data_clean_job_template=DeleteStoredDataQueueJobParameters(
			connection='sample_connection',
			delete_errors=True,
			delete_statistics=True,
			delete_check_results=True,
			delete_sensor_readouts=True,
			delete_error_samples=False
		),
		can_edit=False,
		can_collect_statistics=True,
		can_run_checks=True,
		can_delete_data=True
	)
	
	call_result = update_connection_basic.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

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
		provider_type=ProviderType.POSTGRESQL,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.DISABLE
		),
		run_checks_job_template=CheckSearchFilters(
			connection='sample_connection',
			enabled=True
		),
		run_profiling_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PROFILING,
			connection='sample_connection',
			enabled=True
		),
		run_monitoring_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			connection='sample_connection',
			enabled=True
		),
		run_partition_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PARTITIONED,
			connection='sample_connection',
			enabled=True
		),
		collect_statistics_job_template=StatisticsCollectorSearchFilters(
			column_names=[
			
			],
			connection='sample_connection',
			enabled=True
		),
		data_clean_job_template=DeleteStoredDataQueueJobParameters(
			connection='sample_connection',
			delete_errors=True,
			delete_statistics=True,
			delete_check_results=True,
			delete_sensor_readouts=True,
			delete_error_samples=False
		),
		can_edit=False,
		can_collect_statistics=True,
		can_run_checks=True,
		can_delete_data=True
	)
	
	call_result = await update_connection_basic.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

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
		provider_type=ProviderType.POSTGRESQL,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.DISABLE
		),
		run_checks_job_template=CheckSearchFilters(
			connection='sample_connection',
			enabled=True
		),
		run_profiling_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PROFILING,
			connection='sample_connection',
			enabled=True
		),
		run_monitoring_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			connection='sample_connection',
			enabled=True
		),
		run_partition_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PARTITIONED,
			connection='sample_connection',
			enabled=True
		),
		collect_statistics_job_template=StatisticsCollectorSearchFilters(
			column_names=[
			
			],
			connection='sample_connection',
			enabled=True
		),
		data_clean_job_template=DeleteStoredDataQueueJobParameters(
			connection='sample_connection',
			delete_errors=True,
			delete_statistics=True,
			delete_check_results=True,
			delete_sensor_readouts=True,
			delete_error_samples=False
		),
		can_edit=False,
		can_collect_statistics=True,
		can_run_checks=True,
		can_delete_data=True
	)
	
	call_result = update_connection_basic.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

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
		provider_type=ProviderType.POSTGRESQL,
		postgresql=PostgresqlParametersSpec(
			host='localhost',
			port='5432',
			database='db',
			user='PASSWD',
			sslmode=PostgresqlSslMode.DISABLE
		),
		run_checks_job_template=CheckSearchFilters(
			connection='sample_connection',
			enabled=True
		),
		run_profiling_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PROFILING,
			connection='sample_connection',
			enabled=True
		),
		run_monitoring_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			connection='sample_connection',
			enabled=True
		),
		run_partition_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PARTITIONED,
			connection='sample_connection',
			enabled=True
		),
		collect_statistics_job_template=StatisticsCollectorSearchFilters(
			column_names=[
			
			],
			connection='sample_connection',
			enabled=True
		),
		data_clean_job_template=DeleteStoredDataQueueJobParameters(
			connection='sample_connection',
			delete_errors=True,
			delete_statistics=True,
			delete_check_results=True,
			delete_sensor_readouts=True,
			delete_error_samples=False
		),
		can_edit=False,
		can_collect_statistics=True,
		can_run_checks=True,
		can_delete_data=True
	)
	
	call_result = await update_connection_basic.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_connection_comments
Updates (replaces) the list of comments of a connection

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_comments.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/comments
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|List of comments|*List[[CommentSpec](../models/common.md#commentspec)]*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/comments^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"[{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"},{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"},{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"}]"
	
    ```

    


=== "Python sync client"
    **Execution**

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
	
	call_result = update_connection_comments.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

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
	
	call_result = await update_connection_comments.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

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
	
	call_result = update_connection_comments.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

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
	
	call_result = await update_connection_comments.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_connection_default_grouping_configuration
Updates the default data grouping connection of a connection

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_default_grouping_configuration.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/defaultgroupingconfiguration
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Default data grouping configuration to be assigned to a connection|*[DataGroupingConfigurationSpec](../../reference/yaml/ConnectionYaml.md#datagroupingconfigurationspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/defaultgroupingconfiguration^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"level_3\":{\"source\":\"column_value\",\"column\":\"sample_column\"}}"
	
    ```

    


=== "Python sync client"
    **Execution**

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
			source=DataGroupingDimensionSource.COLUMN_VALUE,
			column='sample_column'
		)
	)
	
	call_result = update_connection_default_grouping_configuration.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

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
			source=DataGroupingDimensionSource.COLUMN_VALUE,
			column='sample_column'
		)
	)
	
	call_result = await update_connection_default_grouping_configuration.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

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
			source=DataGroupingDimensionSource.COLUMN_VALUE,
			column='sample_column'
		)
	)
	
	call_result = update_connection_default_grouping_configuration.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

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
			source=DataGroupingDimensionSource.COLUMN_VALUE,
			column='sample_column'
		)
	)
	
	call_result = await update_connection_default_grouping_configuration.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_connection_incident_grouping
Updates (replaces) configuration of incident grouping and notifications on a connection (data source) level.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_incident_grouping.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/incidentgrouping
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Incident grouping and notification configuration|*[ConnectionIncidentGroupingSpec](../../reference/yaml/ConnectionYaml.md#connectionincidentgroupingspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/incidentgrouping^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"grouping_level\":\"table_dimension\",\"minimum_severity\":\"warning\",\"divide_by_data_groups\":true,\"max_incident_length_days\":60,\"mute_for_days\":60,\"webhooks\":{\"incident_opened_webhook_url\":\"https://sample_url.com/opened\",\"incident_acknowledged_webhook_url\":\"https://sample_url.com/acknowledged\",\"incident_resolved_webhook_url\":\"https://sample_url.com/resolved\",\"incident_muted_webhook_url\":\"https://sample_url.com/muted\"}}"
	
    ```

    


=== "Python sync client"
    **Execution**

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
		grouping_level=IncidentGroupingLevel.TABLE_DIMENSION,
		minimum_severity=MinimumGroupingSeverityLevel.WARNING,
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
	
	call_result = update_connection_incident_grouping.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

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
		grouping_level=IncidentGroupingLevel.TABLE_DIMENSION,
		minimum_severity=MinimumGroupingSeverityLevel.WARNING,
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
	
	call_result = await update_connection_incident_grouping.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

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
		grouping_level=IncidentGroupingLevel.TABLE_DIMENSION,
		minimum_severity=MinimumGroupingSeverityLevel.WARNING,
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
	
	call_result = update_connection_incident_grouping.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

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
		grouping_level=IncidentGroupingLevel.TABLE_DIMENSION,
		minimum_severity=MinimumGroupingSeverityLevel.WARNING,
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
	
	call_result = await update_connection_incident_grouping.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_connection_labels
Updates the list of labels of a connection

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_labels.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/labels
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|List of labels|*List[string]*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/labels^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"[\"sampleString_1\",\"sampleString_2\",\"sampleString_3\"]"
	
    ```

    


=== "Python sync client"
    **Execution**

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
	
	call_result = update_connection_labels.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

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
	
	call_result = await update_connection_labels.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

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
	
	call_result = update_connection_labels.sync(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

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
	
	call_result = await update_connection_labels.asyncio(
	    'sample_connection',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_connection_scheduling_group
Updates the schedule of a connection for a scheduling group (named schedule for checks with a similar time series configuration)

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_scheduling_group.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schedules/{schedulingGroup}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`scheduling_group`](../models/common.md#checkrunschedulegroup)</span>|Check scheduling group (named schedule)|*[CheckRunScheduleGroup](../models/common.md#checkrunschedulegroup)*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Monitoring schedule definition to store|*[MonitoringScheduleSpec](../models/common.md#monitoringschedulespec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schedules/partitioned_daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"cron_expression\":\"0 12 1 * *\"}"
	
    ```

    


=== "Python sync client"
    **Execution**

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
	
	call_result = update_connection_scheduling_group.sync(
	    'sample_connection',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

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
	
	call_result = await update_connection_scheduling_group.asyncio(
	    'sample_connection',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

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
	
	call_result = update_connection_scheduling_group.sync(
	    'sample_connection',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

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
	
	call_result = await update_connection_scheduling_group.asyncio(
	    'sample_connection',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



