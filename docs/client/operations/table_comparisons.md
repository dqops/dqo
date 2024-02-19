# DQOps REST API table_comparisons operations
Operations for managing the configurations of table comparisons between tables on the same or different data sources


___
## create_table_comparison_configuration
Creates a new table comparison configuration added to the compared table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_configuration.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model|*[TableComparisonConfigurationModel](../models/table_comparisons.md#tablecomparisonconfigurationmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisonconfigurations^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_schema.sample_table\",\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"check_type\":\"profiling\",\"grouping_columns\":[],\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_configuration
	from dqops.client.models import CheckType, \
	                                PhysicalTableName, \
	                                TableComparisonConfigurationModel, \
	                                TableComparisonGroupingColumnPairModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonConfigurationModel(
		table_comparison_configuration_name='sample_schema.sample_table',
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		check_type=CheckType.PROFILING,
		grouping_columns=[
		
		],
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = create_table_comparison_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_configuration
	from dqops.client.models import CheckType, \
	                                PhysicalTableName, \
	                                TableComparisonConfigurationModel, \
	                                TableComparisonGroupingColumnPairModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonConfigurationModel(
		table_comparison_configuration_name='sample_schema.sample_table',
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		check_type=CheckType.PROFILING,
		grouping_columns=[
		
		],
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await create_table_comparison_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_configuration
	from dqops.client.models import CheckType, \
	                                PhysicalTableName, \
	                                TableComparisonConfigurationModel, \
	                                TableComparisonGroupingColumnPairModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonConfigurationModel(
		table_comparison_configuration_name='sample_schema.sample_table',
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		check_type=CheckType.PROFILING,
		grouping_columns=[
		
		],
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = create_table_comparison_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_configuration
	from dqops.client.models import CheckType, \
	                                PhysicalTableName, \
	                                TableComparisonConfigurationModel, \
	                                TableComparisonGroupingColumnPairModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonConfigurationModel(
		table_comparison_configuration_name='sample_schema.sample_table',
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		check_type=CheckType.PROFILING,
		grouping_columns=[
		
		],
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await create_table_comparison_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## create_table_comparison_monitoring_daily
Creates a table comparison configuration using daily monitoring checks

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_monitoring_daily.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/monitoring/daily
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|*[TableComparisonModel](../models/table_comparisons.md#tablecomparisonmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/monitoring/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0},\"supports_compare_column_count\":true,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"monitoring\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":false,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"monitoring\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_monitoring_daily
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = create_table_comparison_monitoring_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_monitoring_daily
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await create_table_comparison_monitoring_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_monitoring_daily
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = create_table_comparison_monitoring_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_monitoring_daily
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await create_table_comparison_monitoring_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## create_table_comparison_monitoring_monthly
Creates a table comparison configuration using monthly monitoring checks

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_monitoring_monthly.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/monitoring/monthly
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|*[TableComparisonModel](../models/table_comparisons.md#tablecomparisonmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/monitoring/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0},\"supports_compare_column_count\":true,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"monitoring\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":false,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"monitoring\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_monitoring_monthly
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = create_table_comparison_monitoring_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_monitoring_monthly
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await create_table_comparison_monitoring_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_monitoring_monthly
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = create_table_comparison_monitoring_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_monitoring_monthly
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await create_table_comparison_monitoring_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## create_table_comparison_partitioned_daily
Creates a table comparison configuration using daily partitioned checks

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_partitioned_daily.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/partitioned/daily
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|*[TableComparisonModel](../models/table_comparisons.md#tablecomparisonmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/partitioned/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0},\"supports_compare_column_count\":true,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"monitoring\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":false,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"monitoring\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_partitioned_daily
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = create_table_comparison_partitioned_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_partitioned_daily
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await create_table_comparison_partitioned_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_partitioned_daily
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = create_table_comparison_partitioned_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_partitioned_daily
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await create_table_comparison_partitioned_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## create_table_comparison_partitioned_monthly
Creates a table comparison configuration using monthly partitioned checks

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_partitioned_monthly.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/partitioned/monthly
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|*[TableComparisonModel](../models/table_comparisons.md#tablecomparisonmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/partitioned/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0},\"supports_compare_column_count\":true,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"monitoring\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":false,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"monitoring\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_partitioned_monthly
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = create_table_comparison_partitioned_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_partitioned_monthly
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await create_table_comparison_partitioned_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_partitioned_monthly
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = create_table_comparison_partitioned_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_partitioned_monthly
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await create_table_comparison_partitioned_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## create_table_comparison_profiling
Creates a table comparison configuration using profiling checks

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_profiling.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/profiling
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|*[TableComparisonModel](../models/table_comparisons.md#tablecomparisonmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/profiling^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0},\"supports_compare_column_count\":true,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"monitoring\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":false,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"monitoring\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_profiling
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = create_table_comparison_profiling.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_profiling
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await create_table_comparison_profiling.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_profiling
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = create_table_comparison_profiling.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import create_table_comparison_profiling
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await create_table_comparison_profiling.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## delete_table_comparison_configuration
Deletes a table comparison configuration from a compared table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/delete_table_comparison_configuration.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations/{tableComparisonConfigurationName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|Reference table configuration name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X DELETE http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisonconfigurations/sample_table_comparison^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import delete_table_comparison_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = delete_table_comparison_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import delete_table_comparison_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await delete_table_comparison_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import delete_table_comparison_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = delete_table_comparison_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import delete_table_comparison_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await delete_table_comparison_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    



___
## get_table_comparison_configuration
Returns a model of the table comparison configuration

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/get_table_comparison_configuration.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations/{tableComparisonConfigurationName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_comparison_configuration_model`](../models/table_comparisons.md#tablecomparisonconfigurationmodel)</span>||*[TableComparisonConfigurationModel](../models/table_comparisons.md#tablecomparisonconfigurationmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|Reference table configuration name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisonconfigurations/sample_table_comparison^
		-H "Accept: application/json"
	
    ```

    
    **Return value sample**
    
    
    ```js
    {
	  "table_comparison_configuration_name" : "sample_schema.sample_table",
	  "reference_connection" : "sample_connection",
	  "reference_table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "check_type" : "profiling",
	  "grouping_columns" : [ ],
	  "can_edit" : true,
	  "can_run_compare_checks" : true,
	  "can_delete_data" : true
	}
    ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonConfigurationModel(
		table_comparison_configuration_name='sample_schema.sample_table',
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		check_type=CheckType.PROFILING,
		grouping_columns=[
		
		],
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonConfigurationModel(
		table_comparison_configuration_name='sample_schema.sample_table',
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		check_type=CheckType.PROFILING,
		grouping_columns=[
		
		],
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonConfigurationModel(
		table_comparison_configuration_name='sample_schema.sample_table',
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		check_type=CheckType.PROFILING,
		grouping_columns=[
		
		],
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonConfigurationModel(
		table_comparison_configuration_name='sample_schema.sample_table',
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		check_type=CheckType.PROFILING,
		grouping_columns=[
		
		],
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    



___
## get_table_comparison_configurations
Returns the list of table comparison configurations on a compared table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/get_table_comparison_configurations.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`table_comparison_configuration_model`</span>||*List[[TableComparisonConfigurationModel](../models/table_comparisons.md#tablecomparisonconfigurationmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`check_type`](../models/table_comparisons.md#checktype)</span>|Optional check type filter (profiling, monitoring, partitioned).|*[CheckType](../models/table_comparisons.md#checktype)*| |
|<span class="no-wrap-code">[`check_time_scale`](../models/common.md#checktimescale)</span>|Optional time scale filter for table comparisons specific to the monitoring and partitioned checks (values: daily or monthly).|*[CheckTimeScale](../models/common.md#checktimescale)*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisonconfigurations^
		-H "Accept: application/json"
	
    ```

    
    **Return value sample**
    
    
    ```js
    [ {
	  "table_comparison_configuration_name" : "sample_schema.sample_table",
	  "reference_connection" : "sample_connection",
	  "reference_table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "check_type" : "profiling",
	  "grouping_columns" : [ ],
	  "can_edit" : true,
	  "can_run_compare_checks" : true,
	  "can_delete_data" : true
	}, {
	  "table_comparison_configuration_name" : "sample_schema.sample_table",
	  "reference_connection" : "sample_connection",
	  "reference_table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "check_type" : "profiling",
	  "grouping_columns" : [ ],
	  "can_edit" : true,
	  "can_run_compare_checks" : true,
	  "can_delete_data" : true
	}, {
	  "table_comparison_configuration_name" : "sample_schema.sample_table",
	  "reference_connection" : "sample_connection",
	  "reference_table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "check_type" : "profiling",
	  "grouping_columns" : [ ],
	  "can_edit" : true,
	  "can_run_compare_checks" : true,
	  "can_delete_data" : true
	} ]
    ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_configurations
	from dqops.client.models import CheckTimeScale, \
	                                CheckType
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_configurations.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		TableComparisonConfigurationModel(
			table_comparison_configuration_name='sample_schema.sample_table',
			reference_connection='sample_connection',
			reference_table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			check_type=CheckType.PROFILING,
			grouping_columns=[
			
			],
			can_edit=True,
			can_run_compare_checks=True,
			can_delete_data=True
		),
		TableComparisonConfigurationModel(
			table_comparison_configuration_name='sample_schema.sample_table',
			reference_connection='sample_connection',
			reference_table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			check_type=CheckType.PROFILING,
			grouping_columns=[
			
			],
			can_edit=True,
			can_run_compare_checks=True,
			can_delete_data=True
		),
		TableComparisonConfigurationModel(
			table_comparison_configuration_name='sample_schema.sample_table',
			reference_connection='sample_connection',
			reference_table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			check_type=CheckType.PROFILING,
			grouping_columns=[
			
			],
			can_edit=True,
			can_run_compare_checks=True,
			can_delete_data=True
		)
	]
    ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_configurations
	from dqops.client.models import CheckTimeScale, \
	                                CheckType
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_configurations.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		TableComparisonConfigurationModel(
			table_comparison_configuration_name='sample_schema.sample_table',
			reference_connection='sample_connection',
			reference_table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			check_type=CheckType.PROFILING,
			grouping_columns=[
			
			],
			can_edit=True,
			can_run_compare_checks=True,
			can_delete_data=True
		),
		TableComparisonConfigurationModel(
			table_comparison_configuration_name='sample_schema.sample_table',
			reference_connection='sample_connection',
			reference_table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			check_type=CheckType.PROFILING,
			grouping_columns=[
			
			],
			can_edit=True,
			can_run_compare_checks=True,
			can_delete_data=True
		),
		TableComparisonConfigurationModel(
			table_comparison_configuration_name='sample_schema.sample_table',
			reference_connection='sample_connection',
			reference_table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			check_type=CheckType.PROFILING,
			grouping_columns=[
			
			],
			can_edit=True,
			can_run_compare_checks=True,
			can_delete_data=True
		)
	]
    ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_configurations
	from dqops.client.models import CheckTimeScale, \
	                                CheckType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_configurations.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		TableComparisonConfigurationModel(
			table_comparison_configuration_name='sample_schema.sample_table',
			reference_connection='sample_connection',
			reference_table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			check_type=CheckType.PROFILING,
			grouping_columns=[
			
			],
			can_edit=True,
			can_run_compare_checks=True,
			can_delete_data=True
		),
		TableComparisonConfigurationModel(
			table_comparison_configuration_name='sample_schema.sample_table',
			reference_connection='sample_connection',
			reference_table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			check_type=CheckType.PROFILING,
			grouping_columns=[
			
			],
			can_edit=True,
			can_run_compare_checks=True,
			can_delete_data=True
		),
		TableComparisonConfigurationModel(
			table_comparison_configuration_name='sample_schema.sample_table',
			reference_connection='sample_connection',
			reference_table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			check_type=CheckType.PROFILING,
			grouping_columns=[
			
			],
			can_edit=True,
			can_run_compare_checks=True,
			can_delete_data=True
		)
	]
    ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_configurations
	from dqops.client.models import CheckTimeScale, \
	                                CheckType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_configurations.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		TableComparisonConfigurationModel(
			table_comparison_configuration_name='sample_schema.sample_table',
			reference_connection='sample_connection',
			reference_table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			check_type=CheckType.PROFILING,
			grouping_columns=[
			
			],
			can_edit=True,
			can_run_compare_checks=True,
			can_delete_data=True
		),
		TableComparisonConfigurationModel(
			table_comparison_configuration_name='sample_schema.sample_table',
			reference_connection='sample_connection',
			reference_table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			check_type=CheckType.PROFILING,
			grouping_columns=[
			
			],
			can_edit=True,
			can_run_compare_checks=True,
			can_delete_data=True
		),
		TableComparisonConfigurationModel(
			table_comparison_configuration_name='sample_schema.sample_table',
			reference_connection='sample_connection',
			reference_table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			check_type=CheckType.PROFILING,
			grouping_columns=[
			
			],
			can_edit=True,
			can_run_compare_checks=True,
			can_delete_data=True
		)
	]
    ```
    
    
    



___
## get_table_comparison_monitoring_daily
Returns a model of the table comparison using daily monitoring checks (comparison once a day)

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/get_table_comparison_monitoring_daily.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}/monitoring/daily
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_comparison_model`](../models/table_comparisons.md#tablecomparisonmodel)</span>||*[TableComparisonModel](../models/table_comparisons.md#tablecomparisonmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|Table comparison configuration name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/sample_table_comparison/monitoring/daily^
		-H "Accept: application/json"
	
    ```

    
    **Return value sample**
    
    
    ```js
    {
	  "table_comparison_configuration_name" : "sample_table_comparison",
	  "compared_connection" : "unknown",
	  "compared_table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "reference_connection" : "sample_connection",
	  "reference_table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "grouping_columns" : [ ],
	  "default_compare_thresholds" : {
	    "warning_difference_percent" : 0.0,
	    "error_difference_percent" : 1.0
	  },
	  "supports_compare_column_count" : true,
	  "columns" : [ ],
	  "compare_table_run_checks_job_template" : {
	    "connection" : "unknown",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "checkType" : "monitoring",
	    "timeScale" : "daily",
	    "checkCategory" : "comparisons",
	    "tableComparisonName" : "sample_table_comparison"
	  },
	  "compare_table_clean_data_job_template" : {
	    "connection" : "unknown",
	    "fullTableName" : "sample_schema.sample_table",
	    "deleteErrors" : true,
	    "deleteStatistics" : false,
	    "deleteCheckResults" : true,
	    "deleteSensorReadouts" : true,
	    "checkCategory" : "comparisons",
	    "tableComparisonName" : "sample_table_comparison",
	    "checkType" : "monitoring",
	    "timeGradient" : "day"
	  },
	  "can_edit" : true,
	  "can_run_compare_checks" : true,
	  "can_delete_data" : true
	}
    ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_monitoring_daily
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_monitoring_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_monitoring_daily
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_monitoring_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_monitoring_daily
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_monitoring_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_monitoring_daily
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_monitoring_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    



___
## get_table_comparison_monitoring_monthly
Returns a model of the table comparison using monthly monitoring checks (comparison once a month)

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/get_table_comparison_monitoring_monthly.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}/monitoring/monthly
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_comparison_model`](../models/table_comparisons.md#tablecomparisonmodel)</span>||*[TableComparisonModel](../models/table_comparisons.md#tablecomparisonmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|Table comparison configuration name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/sample_table_comparison/monitoring/monthly^
		-H "Accept: application/json"
	
    ```

    
    **Return value sample**
    
    
    ```js
    {
	  "table_comparison_configuration_name" : "sample_table_comparison",
	  "compared_connection" : "unknown",
	  "compared_table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "reference_connection" : "sample_connection",
	  "reference_table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "grouping_columns" : [ ],
	  "default_compare_thresholds" : {
	    "warning_difference_percent" : 0.0,
	    "error_difference_percent" : 1.0
	  },
	  "supports_compare_column_count" : true,
	  "columns" : [ ],
	  "compare_table_run_checks_job_template" : {
	    "connection" : "unknown",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "checkType" : "monitoring",
	    "timeScale" : "daily",
	    "checkCategory" : "comparisons",
	    "tableComparisonName" : "sample_table_comparison"
	  },
	  "compare_table_clean_data_job_template" : {
	    "connection" : "unknown",
	    "fullTableName" : "sample_schema.sample_table",
	    "deleteErrors" : true,
	    "deleteStatistics" : false,
	    "deleteCheckResults" : true,
	    "deleteSensorReadouts" : true,
	    "checkCategory" : "comparisons",
	    "tableComparisonName" : "sample_table_comparison",
	    "checkType" : "monitoring",
	    "timeGradient" : "day"
	  },
	  "can_edit" : true,
	  "can_run_compare_checks" : true,
	  "can_delete_data" : true
	}
    ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_monitoring_monthly
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_monitoring_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_monitoring_monthly
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_monitoring_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_monitoring_monthly
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_monitoring_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_monitoring_monthly
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_monitoring_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    



___
## get_table_comparison_partitioned_daily
Returns a model of the table comparison using daily partition checks (comparing day to day)

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/get_table_comparison_partitioned_daily.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}/partitioned/daily
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_comparison_model`](../models/table_comparisons.md#tablecomparisonmodel)</span>||*[TableComparisonModel](../models/table_comparisons.md#tablecomparisonmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|Table comparison configuration name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/sample_table_comparison/partitioned/daily^
		-H "Accept: application/json"
	
    ```

    
    **Return value sample**
    
    
    ```js
    {
	  "table_comparison_configuration_name" : "sample_table_comparison",
	  "compared_connection" : "unknown",
	  "compared_table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "reference_connection" : "sample_connection",
	  "reference_table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "grouping_columns" : [ ],
	  "default_compare_thresholds" : {
	    "warning_difference_percent" : 0.0,
	    "error_difference_percent" : 1.0
	  },
	  "supports_compare_column_count" : true,
	  "columns" : [ ],
	  "compare_table_run_checks_job_template" : {
	    "connection" : "unknown",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "checkType" : "monitoring",
	    "timeScale" : "daily",
	    "checkCategory" : "comparisons",
	    "tableComparisonName" : "sample_table_comparison"
	  },
	  "compare_table_clean_data_job_template" : {
	    "connection" : "unknown",
	    "fullTableName" : "sample_schema.sample_table",
	    "deleteErrors" : true,
	    "deleteStatistics" : false,
	    "deleteCheckResults" : true,
	    "deleteSensorReadouts" : true,
	    "checkCategory" : "comparisons",
	    "tableComparisonName" : "sample_table_comparison",
	    "checkType" : "monitoring",
	    "timeGradient" : "day"
	  },
	  "can_edit" : true,
	  "can_run_compare_checks" : true,
	  "can_delete_data" : true
	}
    ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_partitioned_daily
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_partitioned_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_partitioned_daily
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_partitioned_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_partitioned_daily
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_partitioned_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_partitioned_daily
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_partitioned_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    



___
## get_table_comparison_partitioned_monthly
Returns a model of the table comparison using monthly partition checks (comparing month to month)

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/get_table_comparison_partitioned_monthly.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}/partitioned/monthly
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_comparison_model`](../models/table_comparisons.md#tablecomparisonmodel)</span>||*[TableComparisonModel](../models/table_comparisons.md#tablecomparisonmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|Table comparison configuration name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/sample_table_comparison/partitioned/monthly^
		-H "Accept: application/json"
	
    ```

    
    **Return value sample**
    
    
    ```js
    {
	  "table_comparison_configuration_name" : "sample_table_comparison",
	  "compared_connection" : "unknown",
	  "compared_table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "reference_connection" : "sample_connection",
	  "reference_table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "grouping_columns" : [ ],
	  "default_compare_thresholds" : {
	    "warning_difference_percent" : 0.0,
	    "error_difference_percent" : 1.0
	  },
	  "supports_compare_column_count" : true,
	  "columns" : [ ],
	  "compare_table_run_checks_job_template" : {
	    "connection" : "unknown",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "checkType" : "monitoring",
	    "timeScale" : "daily",
	    "checkCategory" : "comparisons",
	    "tableComparisonName" : "sample_table_comparison"
	  },
	  "compare_table_clean_data_job_template" : {
	    "connection" : "unknown",
	    "fullTableName" : "sample_schema.sample_table",
	    "deleteErrors" : true,
	    "deleteStatistics" : false,
	    "deleteCheckResults" : true,
	    "deleteSensorReadouts" : true,
	    "checkCategory" : "comparisons",
	    "tableComparisonName" : "sample_table_comparison",
	    "checkType" : "monitoring",
	    "timeGradient" : "day"
	  },
	  "can_edit" : true,
	  "can_run_compare_checks" : true,
	  "can_delete_data" : true
	}
    ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_partitioned_monthly
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_partitioned_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_partitioned_monthly
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_partitioned_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_partitioned_monthly
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_partitioned_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_partitioned_monthly
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_partitioned_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    



___
## get_table_comparison_profiling
Returns a model of the table comparison using profiling checks (comparison at any time)

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/get_table_comparison_profiling.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}/profiling
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_comparison_model`](../models/table_comparisons.md#tablecomparisonmodel)</span>||*[TableComparisonModel](../models/table_comparisons.md#tablecomparisonmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|Table comparison configuration name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/sample_table_comparison/profiling^
		-H "Accept: application/json"
	
    ```

    
    **Return value sample**
    
    
    ```js
    {
	  "table_comparison_configuration_name" : "sample_table_comparison",
	  "compared_connection" : "unknown",
	  "compared_table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "reference_connection" : "sample_connection",
	  "reference_table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "grouping_columns" : [ ],
	  "default_compare_thresholds" : {
	    "warning_difference_percent" : 0.0,
	    "error_difference_percent" : 1.0
	  },
	  "supports_compare_column_count" : true,
	  "columns" : [ ],
	  "compare_table_run_checks_job_template" : {
	    "connection" : "unknown",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "checkType" : "monitoring",
	    "timeScale" : "daily",
	    "checkCategory" : "comparisons",
	    "tableComparisonName" : "sample_table_comparison"
	  },
	  "compare_table_clean_data_job_template" : {
	    "connection" : "unknown",
	    "fullTableName" : "sample_schema.sample_table",
	    "deleteErrors" : true,
	    "deleteStatistics" : false,
	    "deleteCheckResults" : true,
	    "deleteSensorReadouts" : true,
	    "checkCategory" : "comparisons",
	    "tableComparisonName" : "sample_table_comparison",
	    "checkType" : "monitoring",
	    "timeGradient" : "day"
	  },
	  "can_edit" : true,
	  "can_run_compare_checks" : true,
	  "can_delete_data" : true
	}
    ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_profiling
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_profiling.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_profiling
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_profiling.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_profiling
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_profiling.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import get_table_comparison_profiling
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_profiling.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
    ```
    
    
    



___
## update_table_comparison_configuration
Updates a table configuration configuration

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_configuration.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations/{tableComparisonConfigurationName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|Table comparison configuration name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison model with the configuration of the tables to compare|*[TableComparisonConfigurationModel](../models/table_comparisons.md#tablecomparisonconfigurationmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisonconfigurations/sample_table_comparison^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_schema.sample_table\",\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"check_type\":\"profiling\",\"grouping_columns\":[],\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_configuration
	from dqops.client.models import CheckType, \
	                                PhysicalTableName, \
	                                TableComparisonConfigurationModel, \
	                                TableComparisonGroupingColumnPairModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonConfigurationModel(
		table_comparison_configuration_name='sample_schema.sample_table',
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		check_type=CheckType.PROFILING,
		grouping_columns=[
		
		],
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = update_table_comparison_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_configuration
	from dqops.client.models import CheckType, \
	                                PhysicalTableName, \
	                                TableComparisonConfigurationModel, \
	                                TableComparisonGroupingColumnPairModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonConfigurationModel(
		table_comparison_configuration_name='sample_schema.sample_table',
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		check_type=CheckType.PROFILING,
		grouping_columns=[
		
		],
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await update_table_comparison_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_configuration
	from dqops.client.models import CheckType, \
	                                PhysicalTableName, \
	                                TableComparisonConfigurationModel, \
	                                TableComparisonGroupingColumnPairModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonConfigurationModel(
		table_comparison_configuration_name='sample_schema.sample_table',
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		check_type=CheckType.PROFILING,
		grouping_columns=[
		
		],
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = update_table_comparison_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_configuration
	from dqops.client.models import CheckType, \
	                                PhysicalTableName, \
	                                TableComparisonConfigurationModel, \
	                                TableComparisonGroupingColumnPairModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonConfigurationModel(
		table_comparison_configuration_name='sample_schema.sample_table',
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		check_type=CheckType.PROFILING,
		grouping_columns=[
		
		],
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await update_table_comparison_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_comparison_monitoring_daily
Updates a table comparison checks monitoring daily

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_monitoring_daily.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/monitoring/daily/{tableComparisonConfigurationName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|Table comparison configuration name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|*[TableComparisonModel](../models/table_comparisons.md#tablecomparisonmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/monitoring/daily/sample_table_comparison^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0},\"supports_compare_column_count\":true,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"monitoring\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":false,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"monitoring\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_monitoring_daily
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = update_table_comparison_monitoring_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_monitoring_daily
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await update_table_comparison_monitoring_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_monitoring_daily
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = update_table_comparison_monitoring_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_monitoring_daily
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await update_table_comparison_monitoring_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_comparison_monitoring_monthly
Updates a table comparison checks monitoring monthly

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_monitoring_monthly.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/monitoring/monthly/{tableComparisonConfigurationName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|Table comparison configuration name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|*[TableComparisonModel](../models/table_comparisons.md#tablecomparisonmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/monitoring/monthly/sample_table_comparison^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0},\"supports_compare_column_count\":true,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"monitoring\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":false,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"monitoring\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_monitoring_monthly
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = update_table_comparison_monitoring_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_monitoring_monthly
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await update_table_comparison_monitoring_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_monitoring_monthly
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = update_table_comparison_monitoring_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_monitoring_monthly
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await update_table_comparison_monitoring_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_comparison_partitioned_daily
Updates a table comparison checks partitioned daily (comparing day to day)

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_partitioned_daily.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/partitioned/daily/{tableComparisonConfigurationName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|Table comparison configuration name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|*[TableComparisonModel](../models/table_comparisons.md#tablecomparisonmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/partitioned/daily/sample_table_comparison^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0},\"supports_compare_column_count\":true,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"monitoring\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":false,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"monitoring\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_partitioned_daily
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = update_table_comparison_partitioned_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_partitioned_daily
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await update_table_comparison_partitioned_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_partitioned_daily
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = update_table_comparison_partitioned_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_partitioned_daily
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await update_table_comparison_partitioned_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_comparison_partitioned_monthly
Updates a table comparison checks partitioned monthly (comparing month to month)

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_partitioned_monthly.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/partitioned/monthly/{tableComparisonConfigurationName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|Table comparison configuration name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|*[TableComparisonModel](../models/table_comparisons.md#tablecomparisonmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/partitioned/monthly/sample_table_comparison^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0},\"supports_compare_column_count\":true,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"monitoring\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":false,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"monitoring\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_partitioned_monthly
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = update_table_comparison_partitioned_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_partitioned_monthly
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await update_table_comparison_partitioned_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_partitioned_monthly
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = update_table_comparison_partitioned_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_partitioned_monthly
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await update_table_comparison_partitioned_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_comparison_profiling
Updates a table comparison profiling checks

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_profiling.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/profiling/{tableComparisonConfigurationName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|Table comparison configuration name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|*[TableComparisonModel](../models/table_comparisons.md#tablecomparisonmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/profiling/sample_table_comparison^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0},\"supports_compare_column_count\":true,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"monitoring\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":false,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"monitoring\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_profiling
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = update_table_comparison_profiling.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_profiling
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await update_table_comparison_profiling.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_profiling
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = update_table_comparison_profiling.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparisons import update_table_comparison_profiling
	from dqops.client.models import CheckSearchFilters, \
	                                ColumnComparisonModel, \
	                                CompareThresholdsModel, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableComparisonGroupingColumnPairModel, \
	                                TableComparisonModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableComparisonModel(
		table_comparison_configuration_name='sample_table_comparison',
		compared_connection='unknown',
		compared_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		reference_connection='sample_connection',
		reference_table=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		grouping_columns=[
		
		],
		default_compare_thresholds=CompareThresholdsModel(
			warning_difference_percent=0.0,
			error_difference_percent=1.0
		),
		supports_compare_column_count=True,
		columns=[
		
		],
		compare_table_run_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			time_scale=CheckTimeScale.DAILY,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		compare_table_clean_data_job_template=DeleteStoredDataQueueJobParameters(
			connection='unknown',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=False,
			delete_check_results=True,
			delete_sensor_readouts=True,
			check_category='comparisons',
			table_comparison_name='sample_table_comparison',
			check_type='monitoring',
			time_gradient='day'
		),
		can_edit=True,
		can_run_compare_checks=True,
		can_delete_data=True
	)
	
	call_result = await update_table_comparison_profiling.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



