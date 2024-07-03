---
title: DQOps REST API tables operations
---
# DQOps REST API tables operations
Operations related to manage the metadata of imported tables, and managing the configuration of table-level data quality checks.


___
## create_table
Creates a new table (adds a table metadata)

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/create_table.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}
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
|Table specification|*[TableSpec](../../reference/yaml/TableYaml.md#tablespec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"timestamp_columns\":{\"event_timestamp_column\":\"col1\",\"ingestion_timestamp_column\":\"col2\",\"partition_by_column\":\"col3\"},\"incremental_time_window\":{\"daily_partitioning_recent_days\":7,\"daily_partitioning_include_today\":true},\"profiling_checks\":{\"volume\":{\"profile_row_count\":{\"error\":{\"min_count\":1}}}},\"columns\":{}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import create_table
	from dqops.client.models import ColumnSpecMap, \
	                                DataGroupingConfigurationSpecMap, \
	                                MinCountRule1ParametersSpec, \
	                                PartitionIncrementalTimeWindowSpec, \
	                                TableComparisonConfigurationSpecMap, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TimestampColumnsSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableSpec(
		disabled=False,
		timestamp_columns=TimestampColumnsSpec(
			event_timestamp_column='col1',
			ingestion_timestamp_column='col2',
			partition_by_column='col3'
		),
		incremental_time_window=PartitionIncrementalTimeWindowSpec(
			daily_partitioning_recent_days=7,
			daily_partitioning_include_today=True,
			monthly_partitioning_include_current_month=False
		),
		groupings=DataGroupingConfigurationSpecMap(),
		table_comparisons=TableComparisonConfigurationSpecMap(),
		profiling_checks=TableProfilingCheckCategoriesSpec(
			volume=TableVolumeProfilingChecksSpec(
				profile_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			)
		),
		monitoring_checks=TableMonitoringCheckCategoriesSpec(),
		partitioned_checks=TablePartitionedCheckCategoriesSpec(),
		columns=ColumnSpecMap()
	)
	
	call_result = create_table.sync(
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
	from dqops.client.api.tables import create_table
	from dqops.client.models import ColumnSpecMap, \
	                                DataGroupingConfigurationSpecMap, \
	                                MinCountRule1ParametersSpec, \
	                                PartitionIncrementalTimeWindowSpec, \
	                                TableComparisonConfigurationSpecMap, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TimestampColumnsSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableSpec(
		disabled=False,
		timestamp_columns=TimestampColumnsSpec(
			event_timestamp_column='col1',
			ingestion_timestamp_column='col2',
			partition_by_column='col3'
		),
		incremental_time_window=PartitionIncrementalTimeWindowSpec(
			daily_partitioning_recent_days=7,
			daily_partitioning_include_today=True,
			monthly_partitioning_include_current_month=False
		),
		groupings=DataGroupingConfigurationSpecMap(),
		table_comparisons=TableComparisonConfigurationSpecMap(),
		profiling_checks=TableProfilingCheckCategoriesSpec(
			volume=TableVolumeProfilingChecksSpec(
				profile_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			)
		),
		monitoring_checks=TableMonitoringCheckCategoriesSpec(),
		partitioned_checks=TablePartitionedCheckCategoriesSpec(),
		columns=ColumnSpecMap()
	)
	
	call_result = await create_table.asyncio(
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
	from dqops.client.api.tables import create_table
	from dqops.client.models import ColumnSpecMap, \
	                                DataGroupingConfigurationSpecMap, \
	                                MinCountRule1ParametersSpec, \
	                                PartitionIncrementalTimeWindowSpec, \
	                                TableComparisonConfigurationSpecMap, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TimestampColumnsSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableSpec(
		disabled=False,
		timestamp_columns=TimestampColumnsSpec(
			event_timestamp_column='col1',
			ingestion_timestamp_column='col2',
			partition_by_column='col3'
		),
		incremental_time_window=PartitionIncrementalTimeWindowSpec(
			daily_partitioning_recent_days=7,
			daily_partitioning_include_today=True,
			monthly_partitioning_include_current_month=False
		),
		groupings=DataGroupingConfigurationSpecMap(),
		table_comparisons=TableComparisonConfigurationSpecMap(),
		profiling_checks=TableProfilingCheckCategoriesSpec(
			volume=TableVolumeProfilingChecksSpec(
				profile_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			)
		),
		monitoring_checks=TableMonitoringCheckCategoriesSpec(),
		partitioned_checks=TablePartitionedCheckCategoriesSpec(),
		columns=ColumnSpecMap()
	)
	
	call_result = create_table.sync(
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
	from dqops.client.api.tables import create_table
	from dqops.client.models import ColumnSpecMap, \
	                                DataGroupingConfigurationSpecMap, \
	                                MinCountRule1ParametersSpec, \
	                                PartitionIncrementalTimeWindowSpec, \
	                                TableComparisonConfigurationSpecMap, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TimestampColumnsSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableSpec(
		disabled=False,
		timestamp_columns=TimestampColumnsSpec(
			event_timestamp_column='col1',
			ingestion_timestamp_column='col2',
			partition_by_column='col3'
		),
		incremental_time_window=PartitionIncrementalTimeWindowSpec(
			daily_partitioning_recent_days=7,
			daily_partitioning_include_today=True,
			monthly_partitioning_include_current_month=False
		),
		groupings=DataGroupingConfigurationSpecMap(),
		table_comparisons=TableComparisonConfigurationSpecMap(),
		profiling_checks=TableProfilingCheckCategoriesSpec(
			volume=TableVolumeProfilingChecksSpec(
				profile_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			)
		),
		monitoring_checks=TableMonitoringCheckCategoriesSpec(),
		partitioned_checks=TablePartitionedCheckCategoriesSpec(),
		columns=ColumnSpecMap()
	)
	
	call_result = await create_table.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## delete_table
Deletes a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/delete_table.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`dqo_queue_job_id`](../models/common.md#dqoqueuejobid)</span>||*[DqoQueueJobId](../models/common.md#dqoqueuejobid)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X DELETE http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table^
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
	from dqops.client.api.tables import delete_table
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = delete_table.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.tables import delete_table
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await delete_table.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.tables import delete_table
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = delete_table.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.tables import delete_table
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await delete_table.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
## get_table
Return the table specification

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_model`](../models/tables.md#tablemodel)</span>||*[TableModel](../models/tables.md#tablemodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table^
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
	from dqops.client.api.tables import get_table
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableModel(can_edit=False)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableModel(can_edit=False)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableModel(can_edit=False)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableModel(can_edit=False)
        ```
    
    
    



___
## get_table_basic
Return the basic table information

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_basic.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/basic
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_list_model`](../models/common.md#tablelistmodel)</span>||*[TableListModel](../models/common.md#tablelistmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/basic^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "connection_name" : "sample_connection",
		  "table_hash" : 2314522140819107818,
		  "target" : {
		    "schema_name" : "sample_schema",
		    "table_name" : "sample_table"
		  },
		  "has_any_configured_checks" : true,
		  "has_any_configured_profiling_checks" : true,
		  "run_checks_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true
		  },
		  "run_profiling_checks_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true,
		    "checkType" : "profiling"
		  },
		  "run_monitoring_checks_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true,
		    "checkType" : "monitoring"
		  },
		  "run_partition_checks_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true,
		    "checkType" : "partitioned"
		  },
		  "data_clean_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "deleteErrors" : true,
		    "deleteStatistics" : true,
		    "deleteCheckResults" : true,
		    "deleteSensorReadouts" : true,
		    "deleteErrorSamples" : false
		  },
		  "can_edit" : true,
		  "can_collect_statistics" : true,
		  "can_run_checks" : true,
		  "can_delete_data" : true
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_basic
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_basic.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableListModel(
			connection_name='sample_connection',
			table_hash=2314522140819107818,
			target=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			disabled=False,
			has_any_configured_checks=True,
			has_any_configured_profiling_checks=True,
			has_any_configured_monitoring_checks=False,
			has_any_configured_partition_checks=False,
			partitioning_configuration_missing=False,
			run_checks_job_template=CheckSearchFilters(
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			run_profiling_checks_job_template=CheckSearchFilters(
				check_type=CheckType.PROFILING,
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			run_monitoring_checks_job_template=CheckSearchFilters(
				check_type=CheckType.MONITORING,
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			run_partition_checks_job_template=CheckSearchFilters(
				check_type=CheckType.PARTITIONED,
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			data_clean_job_template=DeleteStoredDataQueueJobParameters(
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				delete_errors=True,
				delete_statistics=True,
				delete_check_results=True,
				delete_sensor_readouts=True,
				delete_error_samples=False
			),
			can_edit=True,
			can_collect_statistics=True,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_basic
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_basic.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableListModel(
			connection_name='sample_connection',
			table_hash=2314522140819107818,
			target=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			disabled=False,
			has_any_configured_checks=True,
			has_any_configured_profiling_checks=True,
			has_any_configured_monitoring_checks=False,
			has_any_configured_partition_checks=False,
			partitioning_configuration_missing=False,
			run_checks_job_template=CheckSearchFilters(
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			run_profiling_checks_job_template=CheckSearchFilters(
				check_type=CheckType.PROFILING,
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			run_monitoring_checks_job_template=CheckSearchFilters(
				check_type=CheckType.MONITORING,
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			run_partition_checks_job_template=CheckSearchFilters(
				check_type=CheckType.PARTITIONED,
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			data_clean_job_template=DeleteStoredDataQueueJobParameters(
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				delete_errors=True,
				delete_statistics=True,
				delete_check_results=True,
				delete_sensor_readouts=True,
				delete_error_samples=False
			),
			can_edit=True,
			can_collect_statistics=True,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_basic
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_basic.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableListModel(
			connection_name='sample_connection',
			table_hash=2314522140819107818,
			target=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			disabled=False,
			has_any_configured_checks=True,
			has_any_configured_profiling_checks=True,
			has_any_configured_monitoring_checks=False,
			has_any_configured_partition_checks=False,
			partitioning_configuration_missing=False,
			run_checks_job_template=CheckSearchFilters(
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			run_profiling_checks_job_template=CheckSearchFilters(
				check_type=CheckType.PROFILING,
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			run_monitoring_checks_job_template=CheckSearchFilters(
				check_type=CheckType.MONITORING,
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			run_partition_checks_job_template=CheckSearchFilters(
				check_type=CheckType.PARTITIONED,
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			data_clean_job_template=DeleteStoredDataQueueJobParameters(
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				delete_errors=True,
				delete_statistics=True,
				delete_check_results=True,
				delete_sensor_readouts=True,
				delete_error_samples=False
			),
			can_edit=True,
			can_collect_statistics=True,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_basic
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_basic.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableListModel(
			connection_name='sample_connection',
			table_hash=2314522140819107818,
			target=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			disabled=False,
			has_any_configured_checks=True,
			has_any_configured_profiling_checks=True,
			has_any_configured_monitoring_checks=False,
			has_any_configured_partition_checks=False,
			partitioning_configuration_missing=False,
			run_checks_job_template=CheckSearchFilters(
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			run_profiling_checks_job_template=CheckSearchFilters(
				check_type=CheckType.PROFILING,
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			run_monitoring_checks_job_template=CheckSearchFilters(
				check_type=CheckType.MONITORING,
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			run_partition_checks_job_template=CheckSearchFilters(
				check_type=CheckType.PARTITIONED,
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			data_clean_job_template=DeleteStoredDataQueueJobParameters(
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				delete_errors=True,
				delete_statistics=True,
				delete_check_results=True,
				delete_sensor_readouts=True,
				delete_error_samples=False
			),
			can_edit=True,
			can_collect_statistics=True,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    



___
## get_table_columns_monitoring_checks_model
Return a UI friendly model of configurations for column-level data quality monitoring checks on a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_columns_monitoring_checks_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columnchecks/monitoring/{timeScale}/model
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_configuration_model`</span>||*List[[CheckConfigurationModel](../models/common.md#checkconfigurationmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Check time-scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`column_name_pattern`</span>|Column name pattern|*string*| |
|<span class="no-wrap-code">`column_data_type`</span>|Column data-type|*string*| |
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`check_enabled`</span>|Check enabled|*boolean*| |
|<span class="no-wrap-code">`check_configured`</span>|Check configured|*boolean*| |
|<span class="no-wrap-code">`limit`</span>|Limit of results, the default value is 1000|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columnchecks/monitoring/daily/model^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "sensor_parameters" : [ ],
		  "disabled" : false,
		  "configured" : false
		}, {
		  "sensor_parameters" : [ ],
		  "disabled" : false,
		  "configured" : false
		}, {
		  "sensor_parameters" : [ ],
		  "disabled" : false,
		  "configured" : false
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_columns_monitoring_checks_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_columns_monitoring_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_columns_monitoring_checks_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_columns_monitoring_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_columns_monitoring_checks_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_columns_monitoring_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_columns_monitoring_checks_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_columns_monitoring_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			)
		]
        ```
    
    
    



___
## get_table_columns_partitioned_checks_model
Return a UI friendly model of configurations for column-level data quality partitioned checks on a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_columns_partitioned_checks_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columnchecks/partitioned/{timeScale}/model
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_configuration_model`</span>||*List[[CheckConfigurationModel](../models/common.md#checkconfigurationmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Check time-scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`column_name_pattern`</span>|Column name pattern|*string*| |
|<span class="no-wrap-code">`column_data_type`</span>|Column data-type|*string*| |
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`check_enabled`</span>|Check enabled|*boolean*| |
|<span class="no-wrap-code">`check_configured`</span>|Check configured|*boolean*| |
|<span class="no-wrap-code">`limit`</span>|Limit of results, the default value is 1000|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columnchecks/partitioned/daily/model^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "sensor_parameters" : [ ],
		  "disabled" : false,
		  "configured" : false
		}, {
		  "sensor_parameters" : [ ],
		  "disabled" : false,
		  "configured" : false
		}, {
		  "sensor_parameters" : [ ],
		  "disabled" : false,
		  "configured" : false
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_columns_partitioned_checks_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_columns_partitioned_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_columns_partitioned_checks_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_columns_partitioned_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_columns_partitioned_checks_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_columns_partitioned_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_columns_partitioned_checks_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_columns_partitioned_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			)
		]
        ```
    
    
    



___
## get_table_columns_profiling_checks_model
Return a UI friendly model of configurations for column-level data quality profiling checks on a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_columns_profiling_checks_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columnchecks/profiling/model
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_configuration_model`</span>||*List[[CheckConfigurationModel](../models/common.md#checkconfigurationmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name_pattern`</span>|Column name pattern|*string*| |
|<span class="no-wrap-code">`column_data_type`</span>|Column data-type|*string*| |
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`check_enabled`</span>|Check enabled|*boolean*| |
|<span class="no-wrap-code">`check_configured`</span>|Check configured|*boolean*| |
|<span class="no-wrap-code">`limit`</span>|Limit of results, the default value is 1000|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columnchecks/profiling/model^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "sensor_parameters" : [ ],
		  "disabled" : false,
		  "configured" : false
		}, {
		  "sensor_parameters" : [ ],
		  "disabled" : false,
		  "configured" : false
		}, {
		  "sensor_parameters" : [ ],
		  "disabled" : false,
		  "configured" : false
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_columns_profiling_checks_model
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_columns_profiling_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_columns_profiling_checks_model
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_columns_profiling_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_columns_profiling_checks_model
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_columns_profiling_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_columns_profiling_checks_model
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_columns_profiling_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			),
			CheckConfigurationModel(
				sensor_parameters=[
				
				],
				disabled=False,
				configured=False
			)
		]
        ```
    
    
    



___
## get_table_comments
Return the list of comments added to a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_comments.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/comments
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`comment_spec`</span>||*List[[CommentSpec](../models/common.md#commentspec)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/comments^
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
	from dqops.client.api.tables import get_table_comments
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comments.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.tables import get_table_comments
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comments.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.tables import get_table_comments
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comments.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.tables import get_table_comments
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comments.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
## get_table_daily_monitoring_checks
Return the configuration of daily table level data quality monitoring on a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_daily_monitoring_checks.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/daily
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_daily_monitoring_check_categories_spec`](../models/tables.md#tabledailymonitoringcheckcategoriesspec)</span>||*[TableDailyMonitoringCheckCategoriesSpec](../models/tables.md#tabledailymonitoringcheckcategoriesspec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/daily^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "volume" : {
		    "daily_row_count" : {
		      "error" : {
		        "min_count" : 1
		      }
		    }
		  }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_daily_monitoring_checks
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_daily_monitoring_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableDailyMonitoringCheckCategoriesSpec(
			volume=TableVolumeDailyMonitoringChecksSpec(
				daily_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=TableComparisonDailyMonitoringChecksSpecMap()
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_daily_monitoring_checks
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_daily_monitoring_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableDailyMonitoringCheckCategoriesSpec(
			volume=TableVolumeDailyMonitoringChecksSpec(
				daily_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=TableComparisonDailyMonitoringChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_daily_monitoring_checks
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_daily_monitoring_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableDailyMonitoringCheckCategoriesSpec(
			volume=TableVolumeDailyMonitoringChecksSpec(
				daily_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=TableComparisonDailyMonitoringChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_daily_monitoring_checks
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_daily_monitoring_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableDailyMonitoringCheckCategoriesSpec(
			volume=TableVolumeDailyMonitoringChecksSpec(
				daily_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=TableComparisonDailyMonitoringChecksSpecMap()
		)
        ```
    
    
    



___
## get_table_daily_partitioned_checks
Return the configuration of daily table level data quality partitioned checks on a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_daily_partitioned_checks.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/daily
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_daily_partitioned_check_categories_spec`](../models/tables.md#tabledailypartitionedcheckcategoriesspec)</span>||*[TableDailyPartitionedCheckCategoriesSpec](../models/tables.md#tabledailypartitionedcheckcategoriesspec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/daily^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "volume" : {
		    "daily_partition_row_count" : {
		      "error" : {
		        "min_count" : 1
		      }
		    }
		  }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_daily_partitioned_checks
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_daily_partitioned_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableDailyPartitionedCheckCategoriesSpec(
			volume=TableVolumeDailyPartitionedChecksSpec(
				daily_partition_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=TableComparisonDailyPartitionedChecksSpecMap()
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_daily_partitioned_checks
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_daily_partitioned_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableDailyPartitionedCheckCategoriesSpec(
			volume=TableVolumeDailyPartitionedChecksSpec(
				daily_partition_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=TableComparisonDailyPartitionedChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_daily_partitioned_checks
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_daily_partitioned_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableDailyPartitionedCheckCategoriesSpec(
			volume=TableVolumeDailyPartitionedChecksSpec(
				daily_partition_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=TableComparisonDailyPartitionedChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_daily_partitioned_checks
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_daily_partitioned_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableDailyPartitionedCheckCategoriesSpec(
			volume=TableVolumeDailyPartitionedChecksSpec(
				daily_partition_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=TableComparisonDailyPartitionedChecksSpecMap()
		)
        ```
    
    
    



___
## get_table_default_grouping_configuration
Return the default data grouping configuration for a table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_default_grouping_configuration.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/defaultgroupingconfiguration
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`data_grouping_configuration_spec`](../../reference/yaml/ConnectionYaml.md#datagroupingconfigurationspec)</span>||*[DataGroupingConfigurationSpec](../../reference/yaml/ConnectionYaml.md#datagroupingconfigurationspec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/defaultgroupingconfiguration^
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
	from dqops.client.api.tables import get_table_default_grouping_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_default_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.tables import get_table_default_grouping_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_default_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.tables import get_table_default_grouping_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_default_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.tables import get_table_default_grouping_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_default_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
## get_table_incident_grouping
Return the configuration of incident grouping on a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_incident_grouping.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/incidentgrouping
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_incident_grouping_spec`](../../reference/yaml/TableYaml.md#tableincidentgroupingspec)</span>||*[TableIncidentGroupingSpec](../../reference/yaml/TableYaml.md#tableincidentgroupingspec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/incidentgrouping^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "grouping_level" : "table_dimension",
		  "minimum_severity" : "warning",
		  "divide_by_data_group" : true,
		  "disabled" : false
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_incident_grouping
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_incident_grouping.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.TABLE_DIMENSION,
			minimum_severity=MinimumGroupingSeverityLevel.WARNING,
			divide_by_data_group=True,
			disabled=False
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_incident_grouping
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_incident_grouping.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.TABLE_DIMENSION,
			minimum_severity=MinimumGroupingSeverityLevel.WARNING,
			divide_by_data_group=True,
			disabled=False
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_incident_grouping
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_incident_grouping.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.TABLE_DIMENSION,
			minimum_severity=MinimumGroupingSeverityLevel.WARNING,
			divide_by_data_group=True,
			disabled=False
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_incident_grouping
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_incident_grouping.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableIncidentGroupingSpec(
			grouping_level=IncidentGroupingLevel.TABLE_DIMENSION,
			minimum_severity=MinimumGroupingSeverityLevel.WARNING,
			divide_by_data_group=True,
			disabled=False
		)
        ```
    
    
    



___
## get_table_labels
Return the list of labels assigned to a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_labels.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/labels
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`string`</span>||*List[string]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/labels^
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
	from dqops.client.api.tables import get_table_labels
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_labels.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.tables import get_table_labels
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_labels.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.tables import get_table_labels
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_labels.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.tables import get_table_labels
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_labels.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
## get_table_monitoring_checks_basic_model
Return a simplistic UI friendly model of table level data quality monitoring on a table for a given time scale

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_monitoring_checks_basic_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/model/basic
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_list_model`](../models/common.md#checkcontainerlistmodel)</span>||*[CheckContainerListModel](../models/common.md#checkcontainerlistmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/daily/model/basic^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "checks" : [ {
		    "check_category" : "sample_category_1",
		    "check_name" : "sample_check_1",
		    "help_text" : "Sample help text",
		    "configured" : true
		  }, {
		    "check_category" : "sample_category_2",
		    "check_name" : "sample_check_1",
		    "help_text" : "Sample help text",
		    "configured" : true
		  }, {
		    "check_category" : "sample_category_1",
		    "check_name" : "sample_check_2",
		    "help_text" : "Sample help text",
		    "configured" : true
		  }, {
		    "check_category" : "sample_category_2",
		    "check_name" : "sample_check_2",
		    "help_text" : "Sample help text",
		    "configured" : true
		  }, {
		    "check_category" : "sample_category_1",
		    "check_name" : "sample_check_3",
		    "help_text" : "Sample help text",
		    "configured" : true
		  }, {
		    "check_category" : "sample_category_2",
		    "check_name" : "sample_check_3",
		    "help_text" : "Sample help text",
		    "configured" : true
		  } ],
		  "can_edit" : false,
		  "can_run_checks" : true,
		  "can_delete_data" : true
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerListModel(
			checks=[
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				)
			],
			can_edit=False,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerListModel(
			checks=[
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				)
			],
			can_edit=False,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerListModel(
			checks=[
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				)
			],
			can_edit=False,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerListModel(
			checks=[
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				)
			],
			can_edit=False,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    



___
## get_table_monitoring_checks_model
Return a UI friendly model of configurations for table level data quality monitoring on a table for a given time scale

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_monitoring_checks_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/model
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_model`](../models/common.md#checkcontainermodel)</span>||*[CheckContainerModel](../models/common.md#checkcontainermodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/daily/model^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "categories" : [ {
		    "category" : "sample_category",
		    "help_text" : "Sample help text",
		    "checks" : [ {
		      "check_name" : "sample_check",
		      "help_text" : "Sample help text",
		      "sensor_parameters" : [ ],
		      "sensor_name" : "sample_target/sample_category/sample_sensor",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_error_sampling" : false,
		      "supports_grouping" : false,
		      "disabled" : false,
		      "exclude_from_kpi" : false,
		      "include_in_sla" : false,
		      "configured" : false,
		      "can_edit" : false,
		      "can_run_checks" : false,
		      "can_delete_data" : false
		    } ]
		  } ],
		  "can_edit" : false,
		  "can_run_checks" : false,
		  "can_delete_data" : false
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    



___
## get_table_monitoring_checks_model_filter
Return a UI friendly model of configurations for table level data quality monitoring on a table for a given time scale, filtered by category and check name.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_monitoring_checks_model_filter.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/model/filter/{checkCategory}/{checkName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_model`](../models/common.md#checkcontainermodel)</span>||*[CheckContainerModel](../models/common.md#checkcontainermodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*|:material-check-bold:|
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/daily/model/filter/sample_category/sample_check^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "categories" : [ {
		    "category" : "sample_category",
		    "help_text" : "Sample help text",
		    "checks" : [ {
		      "check_name" : "sample_check",
		      "help_text" : "Sample help text",
		      "sensor_parameters" : [ ],
		      "sensor_name" : "sample_target/sample_category/sample_sensor",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_error_sampling" : false,
		      "supports_grouping" : false,
		      "disabled" : false,
		      "exclude_from_kpi" : false,
		      "include_in_sla" : false,
		      "configured" : false,
		      "can_edit" : false,
		      "can_run_checks" : false,
		      "can_delete_data" : false
		    } ]
		  } ],
		  "can_edit" : false,
		  "can_run_checks" : false,
		  "can_delete_data" : false
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_checks_model_filter.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_checks_model_filter.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    



___
## get_table_monitoring_checks_monthly
Return the configuration of monthly table level data quality monitoring on a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_monitoring_checks_monthly.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/monthly
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_monthly_monitoring_check_categories_spec`](../models/tables.md#tablemonthlymonitoringcheckcategoriesspec)</span>||*[TableMonthlyMonitoringCheckCategoriesSpec](../models/tables.md#tablemonthlymonitoringcheckcategoriesspec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/monthly^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "volume" : {
		    "monthly_row_count" : {
		      "error" : {
		        "min_count" : 1
		      }
		    }
		  }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_monthly
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableMonthlyMonitoringCheckCategoriesSpec(
			volume=TableVolumeMonthlyMonitoringChecksSpec(
				monthly_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=TableComparisonMonthlyMonitoringChecksSpecMap()
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_monthly
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableMonthlyMonitoringCheckCategoriesSpec(
			volume=TableVolumeMonthlyMonitoringChecksSpec(
				monthly_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=TableComparisonMonthlyMonitoringChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_monthly
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableMonthlyMonitoringCheckCategoriesSpec(
			volume=TableVolumeMonthlyMonitoringChecksSpec(
				monthly_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=TableComparisonMonthlyMonitoringChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_monthly
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableMonthlyMonitoringCheckCategoriesSpec(
			volume=TableVolumeMonthlyMonitoringChecksSpec(
				monthly_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=TableComparisonMonthlyMonitoringChecksSpecMap()
		)
        ```
    
    
    



___
## get_table_monitoring_checks_templates
Return available data quality checks on a requested table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_monitoring_checks_templates.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/bulkenable/monitoring/{timeScale}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_template`</span>||*List[[CheckTemplate](../models/common.md#checktemplate)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/bulkenable/monitoring/daily^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "sensor_parameters_definitions" : [ ]
		}, {
		  "sensor_parameters_definitions" : [ ]
		}, {
		  "sensor_parameters_definitions" : [ ]
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_templates
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_checks_templates.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_templates
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_checks_templates.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_templates
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_checks_templates.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_monitoring_checks_templates
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_checks_templates.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			)
		]
        ```
    
    
    



___
## get_table_partitioned_checks_basic_model
Return a simplistic UI friendly model of table level data quality partitioned checks on a table for a given time scale

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_partitioned_checks_basic_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model/basic
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_list_model`](../models/common.md#checkcontainerlistmodel)</span>||*[CheckContainerListModel](../models/common.md#checkcontainerlistmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/daily/model/basic^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "checks" : [ {
		    "check_category" : "sample_category_1",
		    "check_name" : "sample_check_1",
		    "help_text" : "Sample help text",
		    "configured" : true
		  }, {
		    "check_category" : "sample_category_2",
		    "check_name" : "sample_check_1",
		    "help_text" : "Sample help text",
		    "configured" : true
		  }, {
		    "check_category" : "sample_category_1",
		    "check_name" : "sample_check_2",
		    "help_text" : "Sample help text",
		    "configured" : true
		  }, {
		    "check_category" : "sample_category_2",
		    "check_name" : "sample_check_2",
		    "help_text" : "Sample help text",
		    "configured" : true
		  }, {
		    "check_category" : "sample_category_1",
		    "check_name" : "sample_check_3",
		    "help_text" : "Sample help text",
		    "configured" : true
		  }, {
		    "check_category" : "sample_category_2",
		    "check_name" : "sample_check_3",
		    "help_text" : "Sample help text",
		    "configured" : true
		  } ],
		  "can_edit" : false,
		  "can_run_checks" : true,
		  "can_delete_data" : true
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerListModel(
			checks=[
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				)
			],
			can_edit=False,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerListModel(
			checks=[
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				)
			],
			can_edit=False,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerListModel(
			checks=[
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				)
			],
			can_edit=False,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerListModel(
			checks=[
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				)
			],
			can_edit=False,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    



___
## get_table_partitioned_checks_model
Return a UI friendly model of configurations for table level data quality partitioned checks on a table for a given time scale

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_partitioned_checks_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_model`](../models/common.md#checkcontainermodel)</span>||*[CheckContainerModel](../models/common.md#checkcontainermodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/daily/model^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "categories" : [ {
		    "category" : "sample_category",
		    "help_text" : "Sample help text",
		    "checks" : [ {
		      "check_name" : "sample_check",
		      "help_text" : "Sample help text",
		      "sensor_parameters" : [ ],
		      "sensor_name" : "sample_target/sample_category/sample_sensor",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_error_sampling" : false,
		      "supports_grouping" : false,
		      "disabled" : false,
		      "exclude_from_kpi" : false,
		      "include_in_sla" : false,
		      "configured" : false,
		      "can_edit" : false,
		      "can_run_checks" : false,
		      "can_delete_data" : false
		    } ]
		  } ],
		  "can_edit" : false,
		  "can_run_checks" : false,
		  "can_delete_data" : false
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    



___
## get_table_partitioned_checks_model_filter
Return a UI friendly model of configurations for table level data quality partitioned checks on a table for a given time scale, filtered by category and check name.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_partitioned_checks_model_filter.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model/filter/{checkCategory}/{checkName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_model`](../models/common.md#checkcontainermodel)</span>||*[CheckContainerModel](../models/common.md#checkcontainermodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*|:material-check-bold:|
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/daily/model/filter/sample_category/sample_check^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "categories" : [ {
		    "category" : "sample_category",
		    "help_text" : "Sample help text",
		    "checks" : [ {
		      "check_name" : "sample_check",
		      "help_text" : "Sample help text",
		      "sensor_parameters" : [ ],
		      "sensor_name" : "sample_target/sample_category/sample_sensor",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_error_sampling" : false,
		      "supports_grouping" : false,
		      "disabled" : false,
		      "exclude_from_kpi" : false,
		      "include_in_sla" : false,
		      "configured" : false,
		      "can_edit" : false,
		      "can_run_checks" : false,
		      "can_delete_data" : false
		    } ]
		  } ],
		  "can_edit" : false,
		  "can_run_checks" : false,
		  "can_delete_data" : false
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_checks_model_filter.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_checks_model_filter.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    



___
## get_table_partitioned_checks_monthly
Return the configuration of monthly table level data quality partitioned checks on a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_partitioned_checks_monthly.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/monthly
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_monthly_partitioned_check_categories_spec`](../models/tables.md#tablemonthlypartitionedcheckcategoriesspec)</span>||*[TableMonthlyPartitionedCheckCategoriesSpec](../models/tables.md#tablemonthlypartitionedcheckcategoriesspec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/monthly^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "volume" : {
		    "monthly_partition_row_count" : {
		      "error" : {
		        "min_count" : 1
		      }
		    }
		  }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_monthly
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableMonthlyPartitionedCheckCategoriesSpec(
			volume=TableVolumeMonthlyPartitionedChecksSpec(
				monthly_partition_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=TableComparisonMonthlyPartitionedChecksSpecMap()
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_monthly
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableMonthlyPartitionedCheckCategoriesSpec(
			volume=TableVolumeMonthlyPartitionedChecksSpec(
				monthly_partition_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=TableComparisonMonthlyPartitionedChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_monthly
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableMonthlyPartitionedCheckCategoriesSpec(
			volume=TableVolumeMonthlyPartitionedChecksSpec(
				monthly_partition_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=TableComparisonMonthlyPartitionedChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_monthly
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableMonthlyPartitionedCheckCategoriesSpec(
			volume=TableVolumeMonthlyPartitionedChecksSpec(
				monthly_partition_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=TableComparisonMonthlyPartitionedChecksSpecMap()
		)
        ```
    
    
    



___
## get_table_partitioned_checks_templates
Return available data quality checks on a requested table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_partitioned_checks_templates.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/bulkenable/partitioned/{timeScale}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_template`</span>||*List[[CheckTemplate](../models/common.md#checktemplate)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/bulkenable/partitioned/daily^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "sensor_parameters_definitions" : [ ]
		}, {
		  "sensor_parameters_definitions" : [ ]
		}, {
		  "sensor_parameters_definitions" : [ ]
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_templates
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_checks_templates.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_templates
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_checks_templates.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_templates
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_checks_templates.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioned_checks_templates
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_checks_templates.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			)
		]
        ```
    
    
    



___
## get_table_partitioning
Return the table partitioning information

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_partitioning.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioning
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_partitioning_model`](../models/tables.md#tablepartitioningmodel)</span>||*[TablePartitioningModel](../models/tables.md#tablepartitioningmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioning^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "connection_name" : "sample_connection",
		  "target" : {
		    "schema_name" : "sample_schema",
		    "table_name" : "sample_table"
		  },
		  "timestamp_columns" : {
		    "event_timestamp_column" : "col1",
		    "ingestion_timestamp_column" : "col2",
		    "partition_by_column" : "col3"
		  },
		  "incremental_time_window" : {
		    "daily_partitioning_recent_days" : 7,
		    "daily_partitioning_include_today" : true
		  },
		  "can_edit" : true
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioning
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioning.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TablePartitioningModel(
			connection_name='sample_connection',
			target=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			timestamp_columns=TimestampColumnsSpec(
				event_timestamp_column='col1',
				ingestion_timestamp_column='col2',
				partition_by_column='col3'
			),
			incremental_time_window=PartitionIncrementalTimeWindowSpec(
				daily_partitioning_recent_days=7,
				daily_partitioning_include_today=True,
				monthly_partitioning_include_current_month=False
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioning
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioning.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TablePartitioningModel(
			connection_name='sample_connection',
			target=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			timestamp_columns=TimestampColumnsSpec(
				event_timestamp_column='col1',
				ingestion_timestamp_column='col2',
				partition_by_column='col3'
			),
			incremental_time_window=PartitionIncrementalTimeWindowSpec(
				daily_partitioning_recent_days=7,
				daily_partitioning_include_today=True,
				monthly_partitioning_include_current_month=False
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioning
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioning.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TablePartitioningModel(
			connection_name='sample_connection',
			target=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			timestamp_columns=TimestampColumnsSpec(
				event_timestamp_column='col1',
				ingestion_timestamp_column='col2',
				partition_by_column='col3'
			),
			incremental_time_window=PartitionIncrementalTimeWindowSpec(
				daily_partitioning_recent_days=7,
				daily_partitioning_include_today=True,
				monthly_partitioning_include_current_month=False
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_partitioning
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioning.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TablePartitioningModel(
			connection_name='sample_connection',
			target=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			timestamp_columns=TimestampColumnsSpec(
				event_timestamp_column='col1',
				ingestion_timestamp_column='col2',
				partition_by_column='col3'
			),
			incremental_time_window=PartitionIncrementalTimeWindowSpec(
				daily_partitioning_recent_days=7,
				daily_partitioning_include_today=True,
				monthly_partitioning_include_current_month=False
			),
			can_edit=True
		)
        ```
    
    
    



___
## get_table_profiling_checks
Return the configuration of table level data quality checks on a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_profiling_checks.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_profiling_check_categories_spec`](../models/tables.md#tableprofilingcheckcategoriesspec)</span>||*[TableProfilingCheckCategoriesSpec](../models/tables.md#tableprofilingcheckcategoriesspec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "volume" : {
		    "profile_row_count" : {
		      "error" : {
		        "min_count" : 1
		      }
		    }
		  }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableProfilingCheckCategoriesSpec(
			volume=TableVolumeProfilingChecksSpec(
				profile_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			)
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableProfilingCheckCategoriesSpec(
			volume=TableVolumeProfilingChecksSpec(
				profile_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			)
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableProfilingCheckCategoriesSpec(
			volume=TableVolumeProfilingChecksSpec(
				profile_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			)
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableProfilingCheckCategoriesSpec(
			volume=TableVolumeProfilingChecksSpec(
				profile_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			)
		)
        ```
    
    
    



___
## get_table_profiling_checks_basic_model
Return a simplistic UI friendly model of all table level data quality profiling checks on a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_profiling_checks_basic_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/model/basic
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_list_model`](../models/common.md#checkcontainerlistmodel)</span>||*[CheckContainerListModel](../models/common.md#checkcontainerlistmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/model/basic^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "checks" : [ {
		    "check_category" : "sample_category_1",
		    "check_name" : "sample_check_1",
		    "help_text" : "Sample help text",
		    "configured" : true
		  }, {
		    "check_category" : "sample_category_2",
		    "check_name" : "sample_check_1",
		    "help_text" : "Sample help text",
		    "configured" : true
		  }, {
		    "check_category" : "sample_category_1",
		    "check_name" : "sample_check_2",
		    "help_text" : "Sample help text",
		    "configured" : true
		  }, {
		    "check_category" : "sample_category_2",
		    "check_name" : "sample_check_2",
		    "help_text" : "Sample help text",
		    "configured" : true
		  }, {
		    "check_category" : "sample_category_1",
		    "check_name" : "sample_check_3",
		    "help_text" : "Sample help text",
		    "configured" : true
		  }, {
		    "check_category" : "sample_category_2",
		    "check_name" : "sample_check_3",
		    "help_text" : "Sample help text",
		    "configured" : true
		  } ],
		  "can_edit" : false,
		  "can_run_checks" : true,
		  "can_delete_data" : true
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks_basic_model
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerListModel(
			checks=[
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				)
			],
			can_edit=False,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks_basic_model
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerListModel(
			checks=[
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				)
			],
			can_edit=False,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks_basic_model
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerListModel(
			checks=[
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				)
			],
			can_edit=False,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks_basic_model
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerListModel(
			checks=[
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_1',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_2',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_1',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				),
				CheckListModel(
					check_category='sample_category_2',
					check_name='sample_check_3',
					help_text='Sample help text',
					configured=True
				)
			],
			can_edit=False,
			can_run_checks=True,
			can_delete_data=True
		)
        ```
    
    
    



___
## get_table_profiling_checks_model
Return a UI friendly model of configurations for all table level data quality profiling checks on a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_profiling_checks_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/model
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_model`](../models/common.md#checkcontainermodel)</span>||*[CheckContainerModel](../models/common.md#checkcontainermodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/model^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "categories" : [ {
		    "category" : "sample_category",
		    "help_text" : "Sample help text",
		    "checks" : [ {
		      "check_name" : "sample_check",
		      "help_text" : "Sample help text",
		      "sensor_parameters" : [ ],
		      "sensor_name" : "sample_target/sample_category/sample_sensor",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_error_sampling" : false,
		      "supports_grouping" : false,
		      "disabled" : false,
		      "exclude_from_kpi" : false,
		      "include_in_sla" : false,
		      "configured" : false,
		      "can_edit" : false,
		      "can_run_checks" : false,
		      "can_delete_data" : false
		    } ]
		  } ],
		  "can_edit" : false,
		  "can_run_checks" : false,
		  "can_delete_data" : false
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks_model
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks_model
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks_model
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks_model
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    



___
## get_table_profiling_checks_model_filter
Return a UI friendly model of configurations for all table level data quality profiling checks on a table passing a filter

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_profiling_checks_model_filter.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/model/filter/{checkCategory}/{checkName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_model`](../models/common.md#checkcontainermodel)</span>||*[CheckContainerModel](../models/common.md#checkcontainermodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*|:material-check-bold:|
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/model/filter/sample_category/sample_check^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "categories" : [ {
		    "category" : "sample_category",
		    "help_text" : "Sample help text",
		    "checks" : [ {
		      "check_name" : "sample_check",
		      "help_text" : "Sample help text",
		      "sensor_parameters" : [ ],
		      "sensor_name" : "sample_target/sample_category/sample_sensor",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_error_sampling" : false,
		      "supports_grouping" : false,
		      "disabled" : false,
		      "exclude_from_kpi" : false,
		      "include_in_sla" : false,
		      "configured" : false,
		      "can_edit" : false,
		      "can_run_checks" : false,
		      "can_delete_data" : false
		    } ]
		  } ],
		  "can_edit" : false,
		  "can_run_checks" : false,
		  "can_delete_data" : false
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks_model_filter
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_checks_model_filter.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks_model_filter
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks_model_filter
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_checks_model_filter.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks_model_filter
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
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
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    



___
## get_table_profiling_checks_templates
Return available data quality checks on a requested table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_profiling_checks_templates.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/bulkenable/profiling
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_template`</span>||*List[[CheckTemplate](../models/common.md#checktemplate)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/bulkenable/profiling^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "sensor_parameters_definitions" : [ ]
		}, {
		  "sensor_parameters_definitions" : [ ]
		}, {
		  "sensor_parameters_definitions" : [ ]
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks_templates
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_checks_templates.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks_templates
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_checks_templates.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks_templates
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_checks_templates.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_profiling_checks_templates
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_checks_templates.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			),
			CheckTemplate(
				sensor_parameters_definitions=[
				
				]
			)
		]
        ```
    
    
    



___
## get_table_scheduling_group_override
Return the schedule override configuration for a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_scheduling_group_override.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/schedulesoverride/{schedulingGroup}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`monitoring_schedule_spec`](../models/common.md#monitoringschedulespec)</span>||*[MonitoringScheduleSpec](../models/common.md#monitoringschedulespec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`scheduling_group`](../models/common.md#checkrunschedulegroup)</span>|Check scheduling group (named schedule)|*[CheckRunScheduleGroup](../models/common.md#checkrunschedulegroup)*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/schedulesoverride/partitioned_daily^
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
	from dqops.client.api.tables import get_table_scheduling_group_override
	from dqops.client.models import CheckRunScheduleGroup
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_scheduling_group_override.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.tables import get_table_scheduling_group_override
	from dqops.client.models import CheckRunScheduleGroup
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_scheduling_group_override.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.tables import get_table_scheduling_group_override
	from dqops.client.models import CheckRunScheduleGroup
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_scheduling_group_override.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.tables import get_table_scheduling_group_override
	from dqops.client.models import CheckRunScheduleGroup
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_scheduling_group_override.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
## get_table_statistics
Returns a list of the profiler (statistics) metrics on a chosen table captured during the most recent statistics collection.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_statistics.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/statistics
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_statistics_model`](../models/tables.md#tablestatisticsmodel)</span>||*[TableStatisticsModel](../models/tables.md#tablestatisticsmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/statistics^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "can_collect_statistics" : false
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_statistics
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_statistics.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableStatisticsModel(can_collect_statistics=False)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_statistics
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_statistics.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableStatisticsModel(can_collect_statistics=False)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_statistics
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_statistics.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableStatisticsModel(can_collect_statistics=False)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_table_statistics
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_statistics.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableStatisticsModel(can_collect_statistics=False)
        ```
    
    
    



___
## get_tables
Returns a list of tables inside a connection/schema

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_tables.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`table_list_model`</span>||*List[[TableListModel](../models/common.md#tablelistmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`label`</span>|Optional labels to filter the tables|*List[string]*| |
|<span class="no-wrap-code">`page`</span>|Page number, the first page is 1|*long*| |
|<span class="no-wrap-code">`limit`</span>|Page size, the default is 100 rows, but paging is disabled is neither page and limit parameters are provided|*long*| |
|<span class="no-wrap-code">`filter`</span>|Optional table name filter|*string*| |
|<span class="no-wrap-code">[`check_type`](../models/common.md#checktype)</span>|Optional parameter for the check type, when provided, returns the results for data quality dimensions for the data quality checks of that type|*[CheckType](../models/common.md#checktype)*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "connection_name" : "sample_connection",
		  "table_hash" : 2314522140819107818,
		  "target" : {
		    "schema_name" : "sample_schema",
		    "table_name" : "sample_table"
		  },
		  "has_any_configured_checks" : true,
		  "has_any_configured_profiling_checks" : true,
		  "run_checks_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true
		  },
		  "run_profiling_checks_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true,
		    "checkType" : "profiling"
		  },
		  "run_monitoring_checks_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true,
		    "checkType" : "monitoring"
		  },
		  "run_partition_checks_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true,
		    "checkType" : "partitioned"
		  },
		  "data_clean_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "deleteErrors" : true,
		    "deleteStatistics" : true,
		    "deleteCheckResults" : true,
		    "deleteSensorReadouts" : true,
		    "deleteErrorSamples" : false
		  },
		  "can_edit" : true,
		  "can_collect_statistics" : true,
		  "can_run_checks" : true,
		  "can_delete_data" : true
		}, {
		  "connection_name" : "sample_connection",
		  "table_hash" : 2314522140819107818,
		  "target" : {
		    "schema_name" : "sample_schema",
		    "table_name" : "sample_table"
		  },
		  "has_any_configured_checks" : true,
		  "has_any_configured_profiling_checks" : true,
		  "run_checks_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true
		  },
		  "run_profiling_checks_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true,
		    "checkType" : "profiling"
		  },
		  "run_monitoring_checks_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true,
		    "checkType" : "monitoring"
		  },
		  "run_partition_checks_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true,
		    "checkType" : "partitioned"
		  },
		  "data_clean_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "deleteErrors" : true,
		    "deleteStatistics" : true,
		    "deleteCheckResults" : true,
		    "deleteSensorReadouts" : true,
		    "deleteErrorSamples" : false
		  },
		  "can_edit" : true,
		  "can_collect_statistics" : true,
		  "can_run_checks" : true,
		  "can_delete_data" : true
		}, {
		  "connection_name" : "sample_connection",
		  "table_hash" : 2314522140819107818,
		  "target" : {
		    "schema_name" : "sample_schema",
		    "table_name" : "sample_table"
		  },
		  "has_any_configured_checks" : true,
		  "has_any_configured_profiling_checks" : true,
		  "run_checks_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true
		  },
		  "run_profiling_checks_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true,
		    "checkType" : "profiling"
		  },
		  "run_monitoring_checks_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true,
		    "checkType" : "monitoring"
		  },
		  "run_partition_checks_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true,
		    "checkType" : "partitioned"
		  },
		  "data_clean_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "deleteErrors" : true,
		    "deleteStatistics" : true,
		    "deleteCheckResults" : true,
		    "deleteSensorReadouts" : true,
		    "deleteErrorSamples" : false
		  },
		  "can_edit" : true,
		  "can_collect_statistics" : true,
		  "can_run_checks" : true,
		  "can_delete_data" : true
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import get_tables
	from dqops.client.models import CheckType
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_tables.sync(
	    'sample_connection',
	    'sample_schema',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			TableListModel(
				connection_name='sample_connection',
				table_hash=2314522140819107818,
				target=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				disabled=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				partitioning_configuration_missing=False,
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=True,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			TableListModel(
				connection_name='sample_connection',
				table_hash=2314522140819107818,
				target=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				disabled=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				partitioning_configuration_missing=False,
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=True,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			TableListModel(
				connection_name='sample_connection',
				table_hash=2314522140819107818,
				target=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				disabled=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				partitioning_configuration_missing=False,
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=True,
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
	from dqops.client.api.tables import get_tables
	from dqops.client.models import CheckType
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_tables.asyncio(
	    'sample_connection',
	    'sample_schema',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			TableListModel(
				connection_name='sample_connection',
				table_hash=2314522140819107818,
				target=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				disabled=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				partitioning_configuration_missing=False,
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=True,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			TableListModel(
				connection_name='sample_connection',
				table_hash=2314522140819107818,
				target=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				disabled=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				partitioning_configuration_missing=False,
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=True,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			TableListModel(
				connection_name='sample_connection',
				table_hash=2314522140819107818,
				target=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				disabled=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				partitioning_configuration_missing=False,
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=True,
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
	from dqops.client.api.tables import get_tables
	from dqops.client.models import CheckType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_tables.sync(
	    'sample_connection',
	    'sample_schema',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			TableListModel(
				connection_name='sample_connection',
				table_hash=2314522140819107818,
				target=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				disabled=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				partitioning_configuration_missing=False,
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=True,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			TableListModel(
				connection_name='sample_connection',
				table_hash=2314522140819107818,
				target=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				disabled=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				partitioning_configuration_missing=False,
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=True,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			TableListModel(
				connection_name='sample_connection',
				table_hash=2314522140819107818,
				target=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				disabled=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				partitioning_configuration_missing=False,
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=True,
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
	from dqops.client.api.tables import get_tables
	from dqops.client.models import CheckType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_tables.asyncio(
	    'sample_connection',
	    'sample_schema',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			TableListModel(
				connection_name='sample_connection',
				table_hash=2314522140819107818,
				target=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				disabled=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				partitioning_configuration_missing=False,
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=True,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			TableListModel(
				connection_name='sample_connection',
				table_hash=2314522140819107818,
				target=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				disabled=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				partitioning_configuration_missing=False,
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=True,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			TableListModel(
				connection_name='sample_connection',
				table_hash=2314522140819107818,
				target=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				disabled=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				partitioning_configuration_missing=False,
				run_checks_job_template=CheckSearchFilters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_profiling_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PROFILING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_monitoring_checks_job_template=CheckSearchFilters(
					check_type=CheckType.MONITORING,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				run_partition_checks_job_template=CheckSearchFilters(
					check_type=CheckType.PARTITIONED,
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					enabled=True
				),
				data_clean_job_template=DeleteStoredDataQueueJobParameters(
					connection='sample_connection',
					full_table_name='sample_schema.sample_table',
					delete_errors=True,
					delete_statistics=True,
					delete_check_results=True,
					delete_sensor_readouts=True,
					delete_error_samples=False
				),
				can_edit=True,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			)
		]
        ```
    
    
    



___
## update_table
Updates an existing table specification, changing all the fields

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}
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
|Full table specification|*[TableSpec](../../reference/yaml/TableYaml.md#tablespec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"timestamp_columns\":{\"event_timestamp_column\":\"col1\",\"ingestion_timestamp_column\":\"col2\",\"partition_by_column\":\"col3\"},\"incremental_time_window\":{\"daily_partitioning_recent_days\":7,\"daily_partitioning_include_today\":true},\"profiling_checks\":{\"volume\":{\"profile_row_count\":{\"error\":{\"min_count\":1}}}},\"columns\":{}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table
	from dqops.client.models import ColumnSpecMap, \
	                                DataGroupingConfigurationSpecMap, \
	                                MinCountRule1ParametersSpec, \
	                                PartitionIncrementalTimeWindowSpec, \
	                                TableComparisonConfigurationSpecMap, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TimestampColumnsSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableSpec(
		disabled=False,
		timestamp_columns=TimestampColumnsSpec(
			event_timestamp_column='col1',
			ingestion_timestamp_column='col2',
			partition_by_column='col3'
		),
		incremental_time_window=PartitionIncrementalTimeWindowSpec(
			daily_partitioning_recent_days=7,
			daily_partitioning_include_today=True,
			monthly_partitioning_include_current_month=False
		),
		groupings=DataGroupingConfigurationSpecMap(),
		table_comparisons=TableComparisonConfigurationSpecMap(),
		profiling_checks=TableProfilingCheckCategoriesSpec(
			volume=TableVolumeProfilingChecksSpec(
				profile_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			)
		),
		monitoring_checks=TableMonitoringCheckCategoriesSpec(),
		partitioned_checks=TablePartitionedCheckCategoriesSpec(),
		columns=ColumnSpecMap()
	)
	
	call_result = update_table.sync(
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
	from dqops.client.api.tables import update_table
	from dqops.client.models import ColumnSpecMap, \
	                                DataGroupingConfigurationSpecMap, \
	                                MinCountRule1ParametersSpec, \
	                                PartitionIncrementalTimeWindowSpec, \
	                                TableComparisonConfigurationSpecMap, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TimestampColumnsSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableSpec(
		disabled=False,
		timestamp_columns=TimestampColumnsSpec(
			event_timestamp_column='col1',
			ingestion_timestamp_column='col2',
			partition_by_column='col3'
		),
		incremental_time_window=PartitionIncrementalTimeWindowSpec(
			daily_partitioning_recent_days=7,
			daily_partitioning_include_today=True,
			monthly_partitioning_include_current_month=False
		),
		groupings=DataGroupingConfigurationSpecMap(),
		table_comparisons=TableComparisonConfigurationSpecMap(),
		profiling_checks=TableProfilingCheckCategoriesSpec(
			volume=TableVolumeProfilingChecksSpec(
				profile_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			)
		),
		monitoring_checks=TableMonitoringCheckCategoriesSpec(),
		partitioned_checks=TablePartitionedCheckCategoriesSpec(),
		columns=ColumnSpecMap()
	)
	
	call_result = await update_table.asyncio(
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
	from dqops.client.api.tables import update_table
	from dqops.client.models import ColumnSpecMap, \
	                                DataGroupingConfigurationSpecMap, \
	                                MinCountRule1ParametersSpec, \
	                                PartitionIncrementalTimeWindowSpec, \
	                                TableComparisonConfigurationSpecMap, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TimestampColumnsSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableSpec(
		disabled=False,
		timestamp_columns=TimestampColumnsSpec(
			event_timestamp_column='col1',
			ingestion_timestamp_column='col2',
			partition_by_column='col3'
		),
		incremental_time_window=PartitionIncrementalTimeWindowSpec(
			daily_partitioning_recent_days=7,
			daily_partitioning_include_today=True,
			monthly_partitioning_include_current_month=False
		),
		groupings=DataGroupingConfigurationSpecMap(),
		table_comparisons=TableComparisonConfigurationSpecMap(),
		profiling_checks=TableProfilingCheckCategoriesSpec(
			volume=TableVolumeProfilingChecksSpec(
				profile_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			)
		),
		monitoring_checks=TableMonitoringCheckCategoriesSpec(),
		partitioned_checks=TablePartitionedCheckCategoriesSpec(),
		columns=ColumnSpecMap()
	)
	
	call_result = update_table.sync(
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
	from dqops.client.api.tables import update_table
	from dqops.client.models import ColumnSpecMap, \
	                                DataGroupingConfigurationSpecMap, \
	                                MinCountRule1ParametersSpec, \
	                                PartitionIncrementalTimeWindowSpec, \
	                                TableComparisonConfigurationSpecMap, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TimestampColumnsSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableSpec(
		disabled=False,
		timestamp_columns=TimestampColumnsSpec(
			event_timestamp_column='col1',
			ingestion_timestamp_column='col2',
			partition_by_column='col3'
		),
		incremental_time_window=PartitionIncrementalTimeWindowSpec(
			daily_partitioning_recent_days=7,
			daily_partitioning_include_today=True,
			monthly_partitioning_include_current_month=False
		),
		groupings=DataGroupingConfigurationSpecMap(),
		table_comparisons=TableComparisonConfigurationSpecMap(),
		profiling_checks=TableProfilingCheckCategoriesSpec(
			volume=TableVolumeProfilingChecksSpec(
				profile_row_count=TableRowCountCheckSpec(
					parameters=TableVolumeRowCountSensorParametersSpec(),
					error=MinCountRule1ParametersSpec(min_count=1),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			)
		),
		monitoring_checks=TableMonitoringCheckCategoriesSpec(),
		partitioned_checks=TablePartitionedCheckCategoriesSpec(),
		columns=ColumnSpecMap()
	)
	
	call_result = await update_table.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_basic
Updates the basic field of an existing table, changing only the most important fields.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_basic.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/basic
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
|Table basic model with the updated settings|*[TableListModel](../models/common.md#tablelistmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/basic^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection_name\":\"sample_connection\",\"table_hash\":2314522140819107818,\"target\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"has_any_configured_checks\":true,\"has_any_configured_profiling_checks\":true,\"run_checks_job_template\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true},\"run_profiling_checks_job_template\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"profiling\"},\"run_monitoring_checks_job_template\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"monitoring\"},\"run_partition_checks_job_template\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"partitioned\"},\"data_clean_job_template\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"deleteErrorSamples\":false},\"can_edit\":true,\"can_collect_statistics\":true,\"can_run_checks\":true,\"can_delete_data\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_basic
	from dqops.client.models import CheckSearchFilters, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableListModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableListModel(
		connection_name='sample_connection',
		table_hash=2314522140819107818,
		target=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		disabled=False,
		has_any_configured_checks=True,
		has_any_configured_profiling_checks=True,
		has_any_configured_monitoring_checks=False,
		has_any_configured_partition_checks=False,
		partitioning_configuration_missing=False,
		run_checks_job_template=CheckSearchFilters(
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		run_profiling_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PROFILING,
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		run_monitoring_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		run_partition_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PARTITIONED,
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		data_clean_job_template=DeleteStoredDataQueueJobParameters(
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=True,
			delete_check_results=True,
			delete_sensor_readouts=True,
			delete_error_samples=False
		),
		can_edit=True,
		can_collect_statistics=True,
		can_run_checks=True,
		can_delete_data=True
	)
	
	call_result = update_table_basic.sync(
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
	from dqops.client.api.tables import update_table_basic
	from dqops.client.models import CheckSearchFilters, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableListModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableListModel(
		connection_name='sample_connection',
		table_hash=2314522140819107818,
		target=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		disabled=False,
		has_any_configured_checks=True,
		has_any_configured_profiling_checks=True,
		has_any_configured_monitoring_checks=False,
		has_any_configured_partition_checks=False,
		partitioning_configuration_missing=False,
		run_checks_job_template=CheckSearchFilters(
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		run_profiling_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PROFILING,
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		run_monitoring_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		run_partition_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PARTITIONED,
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		data_clean_job_template=DeleteStoredDataQueueJobParameters(
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=True,
			delete_check_results=True,
			delete_sensor_readouts=True,
			delete_error_samples=False
		),
		can_edit=True,
		can_collect_statistics=True,
		can_run_checks=True,
		can_delete_data=True
	)
	
	call_result = await update_table_basic.asyncio(
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
	from dqops.client.api.tables import update_table_basic
	from dqops.client.models import CheckSearchFilters, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableListModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableListModel(
		connection_name='sample_connection',
		table_hash=2314522140819107818,
		target=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		disabled=False,
		has_any_configured_checks=True,
		has_any_configured_profiling_checks=True,
		has_any_configured_monitoring_checks=False,
		has_any_configured_partition_checks=False,
		partitioning_configuration_missing=False,
		run_checks_job_template=CheckSearchFilters(
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		run_profiling_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PROFILING,
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		run_monitoring_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		run_partition_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PARTITIONED,
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		data_clean_job_template=DeleteStoredDataQueueJobParameters(
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=True,
			delete_check_results=True,
			delete_sensor_readouts=True,
			delete_error_samples=False
		),
		can_edit=True,
		can_collect_statistics=True,
		can_run_checks=True,
		can_delete_data=True
	)
	
	call_result = update_table_basic.sync(
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
	from dqops.client.api.tables import update_table_basic
	from dqops.client.models import CheckSearchFilters, \
	                                DeleteStoredDataQueueJobParameters, \
	                                PhysicalTableName, \
	                                TableListModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableListModel(
		connection_name='sample_connection',
		table_hash=2314522140819107818,
		target=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		disabled=False,
		has_any_configured_checks=True,
		has_any_configured_profiling_checks=True,
		has_any_configured_monitoring_checks=False,
		has_any_configured_partition_checks=False,
		partitioning_configuration_missing=False,
		run_checks_job_template=CheckSearchFilters(
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		run_profiling_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PROFILING,
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		run_monitoring_checks_job_template=CheckSearchFilters(
			check_type=CheckType.MONITORING,
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		run_partition_checks_job_template=CheckSearchFilters(
			check_type=CheckType.PARTITIONED,
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			enabled=True
		),
		data_clean_job_template=DeleteStoredDataQueueJobParameters(
			connection='sample_connection',
			full_table_name='sample_schema.sample_table',
			delete_errors=True,
			delete_statistics=True,
			delete_check_results=True,
			delete_sensor_readouts=True,
			delete_error_samples=False
		),
		can_edit=True,
		can_collect_statistics=True,
		can_run_checks=True,
		can_delete_data=True
	)
	
	call_result = await update_table_basic.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_comments
Updates the list of comments on an existing table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_comments.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/comments
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
|List of comments to attach (replace) on a table or an empty object to clear the list of comments on a table|*List[[CommentSpec](../models/common.md#commentspec)]*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/comments^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"[{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"},{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"},{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"}]"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_comments
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
	
	call_result = update_table_comments.sync(
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
	from dqops.client.api.tables import update_table_comments
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
	
	call_result = await update_table_comments.asyncio(
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
	from dqops.client.api.tables import update_table_comments
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
	
	call_result = update_table_comments.sync(
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
	from dqops.client.api.tables import update_table_comments
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
	
	call_result = await update_table_comments.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_daily_monitoring_checks
Updates the list of daily table level data quality monitoring on an existing table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_daily_monitoring_checks.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/daily
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
|Configuration of daily table level data quality monitoring to store or an empty object to remove all data quality monitoring on the table level (column level monitoring are preserved).|*[TableDailyMonitoringCheckCategoriesSpec](../models/tables.md#tabledailymonitoringcheckcategoriesspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"volume\":{\"daily_row_count\":{\"error\":{\"min_count\":1}}}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_daily_monitoring_checks
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableComparisonDailyMonitoringChecksSpecMap, \
	                                TableDailyMonitoringCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeDailyMonitoringChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableDailyMonitoringCheckCategoriesSpec(
		volume=TableVolumeDailyMonitoringChecksSpec(
			daily_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=TableComparisonDailyMonitoringChecksSpecMap()
	)
	
	call_result = update_table_daily_monitoring_checks.sync(
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
	from dqops.client.api.tables import update_table_daily_monitoring_checks
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableComparisonDailyMonitoringChecksSpecMap, \
	                                TableDailyMonitoringCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeDailyMonitoringChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableDailyMonitoringCheckCategoriesSpec(
		volume=TableVolumeDailyMonitoringChecksSpec(
			daily_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=TableComparisonDailyMonitoringChecksSpecMap()
	)
	
	call_result = await update_table_daily_monitoring_checks.asyncio(
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
	from dqops.client.api.tables import update_table_daily_monitoring_checks
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableComparisonDailyMonitoringChecksSpecMap, \
	                                TableDailyMonitoringCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeDailyMonitoringChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableDailyMonitoringCheckCategoriesSpec(
		volume=TableVolumeDailyMonitoringChecksSpec(
			daily_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=TableComparisonDailyMonitoringChecksSpecMap()
	)
	
	call_result = update_table_daily_monitoring_checks.sync(
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
	from dqops.client.api.tables import update_table_daily_monitoring_checks
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableComparisonDailyMonitoringChecksSpecMap, \
	                                TableDailyMonitoringCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeDailyMonitoringChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableDailyMonitoringCheckCategoriesSpec(
		volume=TableVolumeDailyMonitoringChecksSpec(
			daily_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=TableComparisonDailyMonitoringChecksSpecMap()
	)
	
	call_result = await update_table_daily_monitoring_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_default_grouping_configuration
Updates the default data grouping configuration at a table level.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_default_grouping_configuration.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/defaultgroupingconfiguration
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
|Default data grouping configuration to store or an empty object to clear the data grouping configuration on a table level|*[DataGroupingConfigurationSpec](../../reference/yaml/ConnectionYaml.md#datagroupingconfigurationspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/defaultgroupingconfiguration^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"level_3\":{\"source\":\"column_value\",\"column\":\"sample_column\"}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_default_grouping_configuration
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
	
	call_result = update_table_default_grouping_configuration.sync(
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
	from dqops.client.api.tables import update_table_default_grouping_configuration
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
	
	call_result = await update_table_default_grouping_configuration.asyncio(
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
	from dqops.client.api.tables import update_table_default_grouping_configuration
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
	
	call_result = update_table_default_grouping_configuration.sync(
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
	from dqops.client.api.tables import update_table_default_grouping_configuration
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
	
	call_result = await update_table_default_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_incident_grouping
Updates the configuration of incident grouping on a table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_incident_grouping.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/incidentgrouping
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
|New configuration of the table&#x27;s incident grouping|*[TableIncidentGroupingSpec](../../reference/yaml/TableYaml.md#tableincidentgroupingspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/incidentgrouping^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"grouping_level\":\"table_dimension\",\"minimum_severity\":\"warning\",\"divide_by_data_group\":true,\"disabled\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_incident_grouping
	from dqops.client.models import IncidentGroupingLevel, \
	                                MinimumGroupingSeverityLevel, \
	                                TableIncidentGroupingSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableIncidentGroupingSpec(
		grouping_level=IncidentGroupingLevel.TABLE_DIMENSION,
		minimum_severity=MinimumGroupingSeverityLevel.WARNING,
		divide_by_data_group=True,
		disabled=False
	)
	
	call_result = update_table_incident_grouping.sync(
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
	from dqops.client.api.tables import update_table_incident_grouping
	from dqops.client.models import IncidentGroupingLevel, \
	                                MinimumGroupingSeverityLevel, \
	                                TableIncidentGroupingSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableIncidentGroupingSpec(
		grouping_level=IncidentGroupingLevel.TABLE_DIMENSION,
		minimum_severity=MinimumGroupingSeverityLevel.WARNING,
		divide_by_data_group=True,
		disabled=False
	)
	
	call_result = await update_table_incident_grouping.asyncio(
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
	from dqops.client.api.tables import update_table_incident_grouping
	from dqops.client.models import IncidentGroupingLevel, \
	                                MinimumGroupingSeverityLevel, \
	                                TableIncidentGroupingSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableIncidentGroupingSpec(
		grouping_level=IncidentGroupingLevel.TABLE_DIMENSION,
		minimum_severity=MinimumGroupingSeverityLevel.WARNING,
		divide_by_data_group=True,
		disabled=False
	)
	
	call_result = update_table_incident_grouping.sync(
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
	from dqops.client.api.tables import update_table_incident_grouping
	from dqops.client.models import IncidentGroupingLevel, \
	                                MinimumGroupingSeverityLevel, \
	                                TableIncidentGroupingSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableIncidentGroupingSpec(
		grouping_level=IncidentGroupingLevel.TABLE_DIMENSION,
		minimum_severity=MinimumGroupingSeverityLevel.WARNING,
		divide_by_data_group=True,
		disabled=False
	)
	
	call_result = await update_table_incident_grouping.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_labels
Updates the list of assigned labels of an existing table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_labels.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/labels
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
|List of labels to attach (replace) on a table or an empty object to clear the list of labels on a table|*List[string]*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/labels^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"[\"sampleString_1\",\"sampleString_2\",\"sampleString_3\"]"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_labels
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = [
		'sampleString_1',
		'sampleString_2',
		'sampleString_3'
	]
	
	call_result = update_table_labels.sync(
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
	from dqops.client.api.tables import update_table_labels
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = [
		'sampleString_1',
		'sampleString_2',
		'sampleString_3'
	]
	
	call_result = await update_table_labels.asyncio(
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
	from dqops.client.api.tables import update_table_labels
	
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
	
	call_result = update_table_labels.sync(
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
	from dqops.client.api.tables import update_table_labels
	
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
	
	call_result = await update_table_labels.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_monitoring_checks_model
Updates the data quality monitoring from a model that contains a patch with changes.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_monitoring_checks_model.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/model
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality monitoring configuration.|*[CheckContainerModel](../models/common.md#checkcontainermodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/daily/model^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_error_sampling\":false,\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_monitoring_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
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
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = update_table_monitoring_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_monitoring_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
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
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = await update_table_monitoring_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_monitoring_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
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
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = update_table_monitoring_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_monitoring_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
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
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = await update_table_monitoring_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_monitoring_checks_monthly
Updates the list of monthly table level data quality monitoring on an existing table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_monitoring_checks_monthly.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/monthly
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
|Configuration of monthly table level data quality monitoring to store or an empty object to remove all data quality monitoring on the table level (column level monitoring are preserved).|*[TableMonthlyMonitoringCheckCategoriesSpec](../models/tables.md#tablemonthlymonitoringcheckcategoriesspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"volume\":{\"monthly_row_count\":{\"error\":{\"min_count\":1}}}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_monitoring_checks_monthly
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableComparisonMonthlyMonitoringChecksSpecMap, \
	                                TableMonthlyMonitoringCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeMonthlyMonitoringChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableMonthlyMonitoringCheckCategoriesSpec(
		volume=TableVolumeMonthlyMonitoringChecksSpec(
			monthly_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=TableComparisonMonthlyMonitoringChecksSpecMap()
	)
	
	call_result = update_table_monitoring_checks_monthly.sync(
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
	from dqops.client.api.tables import update_table_monitoring_checks_monthly
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableComparisonMonthlyMonitoringChecksSpecMap, \
	                                TableMonthlyMonitoringCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeMonthlyMonitoringChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableMonthlyMonitoringCheckCategoriesSpec(
		volume=TableVolumeMonthlyMonitoringChecksSpec(
			monthly_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=TableComparisonMonthlyMonitoringChecksSpecMap()
	)
	
	call_result = await update_table_monitoring_checks_monthly.asyncio(
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
	from dqops.client.api.tables import update_table_monitoring_checks_monthly
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableComparisonMonthlyMonitoringChecksSpecMap, \
	                                TableMonthlyMonitoringCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeMonthlyMonitoringChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableMonthlyMonitoringCheckCategoriesSpec(
		volume=TableVolumeMonthlyMonitoringChecksSpec(
			monthly_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=TableComparisonMonthlyMonitoringChecksSpecMap()
	)
	
	call_result = update_table_monitoring_checks_monthly.sync(
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
	from dqops.client.api.tables import update_table_monitoring_checks_monthly
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableComparisonMonthlyMonitoringChecksSpecMap, \
	                                TableMonthlyMonitoringCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeMonthlyMonitoringChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableMonthlyMonitoringCheckCategoriesSpec(
		volume=TableVolumeMonthlyMonitoringChecksSpec(
			monthly_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=TableComparisonMonthlyMonitoringChecksSpecMap()
	)
	
	call_result = await update_table_monitoring_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_partitioned_checks_daily
Updates the list of daily table level data quality partitioned checks on an existing table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_partitioned_checks_daily.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/daily
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
|Configuration of daily table level data quality partitioned checks to store or an empty object to remove all data quality partitioned checks on the table level (column level partitioned checks are preserved).|*[TableDailyPartitionedCheckCategoriesSpec](../models/tables.md#tabledailypartitionedcheckcategoriesspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"volume\":{\"daily_partition_row_count\":{\"error\":{\"min_count\":1}}}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_partitioned_checks_daily
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableComparisonDailyPartitionedChecksSpecMap, \
	                                TableDailyPartitionedCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeDailyPartitionedChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableDailyPartitionedCheckCategoriesSpec(
		volume=TableVolumeDailyPartitionedChecksSpec(
			daily_partition_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=TableComparisonDailyPartitionedChecksSpecMap()
	)
	
	call_result = update_table_partitioned_checks_daily.sync(
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
	from dqops.client.api.tables import update_table_partitioned_checks_daily
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableComparisonDailyPartitionedChecksSpecMap, \
	                                TableDailyPartitionedCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeDailyPartitionedChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableDailyPartitionedCheckCategoriesSpec(
		volume=TableVolumeDailyPartitionedChecksSpec(
			daily_partition_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=TableComparisonDailyPartitionedChecksSpecMap()
	)
	
	call_result = await update_table_partitioned_checks_daily.asyncio(
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
	from dqops.client.api.tables import update_table_partitioned_checks_daily
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableComparisonDailyPartitionedChecksSpecMap, \
	                                TableDailyPartitionedCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeDailyPartitionedChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableDailyPartitionedCheckCategoriesSpec(
		volume=TableVolumeDailyPartitionedChecksSpec(
			daily_partition_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=TableComparisonDailyPartitionedChecksSpecMap()
	)
	
	call_result = update_table_partitioned_checks_daily.sync(
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
	from dqops.client.api.tables import update_table_partitioned_checks_daily
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableComparisonDailyPartitionedChecksSpecMap, \
	                                TableDailyPartitionedCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeDailyPartitionedChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableDailyPartitionedCheckCategoriesSpec(
		volume=TableVolumeDailyPartitionedChecksSpec(
			daily_partition_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=TableComparisonDailyPartitionedChecksSpecMap()
	)
	
	call_result = await update_table_partitioned_checks_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_partitioned_checks_model
Updates the data quality partitioned checks from a model that contains a patch with changes.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_partitioned_checks_model.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality partitioned checks configuration.|*[CheckContainerModel](../models/common.md#checkcontainermodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/daily/model^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_error_sampling\":false,\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_partitioned_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
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
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = update_table_partitioned_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_partitioned_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
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
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = await update_table_partitioned_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_partitioned_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
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
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = update_table_partitioned_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_partitioned_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
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
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = await update_table_partitioned_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_partitioned_checks_monthly
Updates the list of monthly table level data quality partitioned checks on an existing table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_partitioned_checks_monthly.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/monthly
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
|Configuration of monthly table level data quality partitioned checks to store or an empty object to remove all data quality partitioned checks on the table level (column level partitioned checks are preserved).|*[TableMonthlyPartitionedCheckCategoriesSpec](../models/tables.md#tablemonthlypartitionedcheckcategoriesspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"volume\":{\"monthly_partition_row_count\":{\"error\":{\"min_count\":1}}}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_partitioned_checks_monthly
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableComparisonMonthlyPartitionedChecksSpecMap, \
	                                TableMonthlyPartitionedCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeMonthlyPartitionedChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableMonthlyPartitionedCheckCategoriesSpec(
		volume=TableVolumeMonthlyPartitionedChecksSpec(
			monthly_partition_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=TableComparisonMonthlyPartitionedChecksSpecMap()
	)
	
	call_result = update_table_partitioned_checks_monthly.sync(
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
	from dqops.client.api.tables import update_table_partitioned_checks_monthly
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableComparisonMonthlyPartitionedChecksSpecMap, \
	                                TableMonthlyPartitionedCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeMonthlyPartitionedChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableMonthlyPartitionedCheckCategoriesSpec(
		volume=TableVolumeMonthlyPartitionedChecksSpec(
			monthly_partition_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=TableComparisonMonthlyPartitionedChecksSpecMap()
	)
	
	call_result = await update_table_partitioned_checks_monthly.asyncio(
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
	from dqops.client.api.tables import update_table_partitioned_checks_monthly
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableComparisonMonthlyPartitionedChecksSpecMap, \
	                                TableMonthlyPartitionedCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeMonthlyPartitionedChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableMonthlyPartitionedCheckCategoriesSpec(
		volume=TableVolumeMonthlyPartitionedChecksSpec(
			monthly_partition_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=TableComparisonMonthlyPartitionedChecksSpecMap()
	)
	
	call_result = update_table_partitioned_checks_monthly.sync(
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
	from dqops.client.api.tables import update_table_partitioned_checks_monthly
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableComparisonMonthlyPartitionedChecksSpecMap, \
	                                TableMonthlyPartitionedCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeMonthlyPartitionedChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableMonthlyPartitionedCheckCategoriesSpec(
		volume=TableVolumeMonthlyPartitionedChecksSpec(
			monthly_partition_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=TableComparisonMonthlyPartitionedChecksSpecMap()
	)
	
	call_result = await update_table_partitioned_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_partitioning
Updates the table partitioning configuration of an existing table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_partitioning.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioning
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
|Table partitioning model with the updated settings|*[TablePartitioningModel](../models/tables.md#tablepartitioningmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioning^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection_name\":\"sample_connection\",\"target\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"timestamp_columns\":{\"event_timestamp_column\":\"col1\",\"ingestion_timestamp_column\":\"col2\",\"partition_by_column\":\"col3\"},\"incremental_time_window\":{\"daily_partitioning_recent_days\":7,\"daily_partitioning_include_today\":true},\"can_edit\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_partitioning
	from dqops.client.models import PartitionIncrementalTimeWindowSpec, \
	                                PhysicalTableName, \
	                                TablePartitioningModel, \
	                                TimestampColumnsSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TablePartitioningModel(
		connection_name='sample_connection',
		target=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		timestamp_columns=TimestampColumnsSpec(
			event_timestamp_column='col1',
			ingestion_timestamp_column='col2',
			partition_by_column='col3'
		),
		incremental_time_window=PartitionIncrementalTimeWindowSpec(
			daily_partitioning_recent_days=7,
			daily_partitioning_include_today=True,
			monthly_partitioning_include_current_month=False
		),
		can_edit=True
	)
	
	call_result = update_table_partitioning.sync(
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
	from dqops.client.api.tables import update_table_partitioning
	from dqops.client.models import PartitionIncrementalTimeWindowSpec, \
	                                PhysicalTableName, \
	                                TablePartitioningModel, \
	                                TimestampColumnsSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TablePartitioningModel(
		connection_name='sample_connection',
		target=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		timestamp_columns=TimestampColumnsSpec(
			event_timestamp_column='col1',
			ingestion_timestamp_column='col2',
			partition_by_column='col3'
		),
		incremental_time_window=PartitionIncrementalTimeWindowSpec(
			daily_partitioning_recent_days=7,
			daily_partitioning_include_today=True,
			monthly_partitioning_include_current_month=False
		),
		can_edit=True
	)
	
	call_result = await update_table_partitioning.asyncio(
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
	from dqops.client.api.tables import update_table_partitioning
	from dqops.client.models import PartitionIncrementalTimeWindowSpec, \
	                                PhysicalTableName, \
	                                TablePartitioningModel, \
	                                TimestampColumnsSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TablePartitioningModel(
		connection_name='sample_connection',
		target=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		timestamp_columns=TimestampColumnsSpec(
			event_timestamp_column='col1',
			ingestion_timestamp_column='col2',
			partition_by_column='col3'
		),
		incremental_time_window=PartitionIncrementalTimeWindowSpec(
			daily_partitioning_recent_days=7,
			daily_partitioning_include_today=True,
			monthly_partitioning_include_current_month=False
		),
		can_edit=True
	)
	
	call_result = update_table_partitioning.sync(
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
	from dqops.client.api.tables import update_table_partitioning
	from dqops.client.models import PartitionIncrementalTimeWindowSpec, \
	                                PhysicalTableName, \
	                                TablePartitioningModel, \
	                                TimestampColumnsSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TablePartitioningModel(
		connection_name='sample_connection',
		target=PhysicalTableName(
			schema_name='sample_schema',
			table_name='sample_table'
		),
		timestamp_columns=TimestampColumnsSpec(
			event_timestamp_column='col1',
			ingestion_timestamp_column='col2',
			partition_by_column='col3'
		),
		incremental_time_window=PartitionIncrementalTimeWindowSpec(
			daily_partitioning_recent_days=7,
			daily_partitioning_include_today=True,
			monthly_partitioning_include_current_month=False
		),
		can_edit=True
	)
	
	call_result = await update_table_partitioning.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_profiling_checks
Updates the list of table level data quality profiling checks on an existing table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_profiling_checks.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling
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
|Configuration of table level data quality profiling checks to store or an empty object to remove all data quality profiling checks on the table level (column level profiling checks are preserved).|*[TableProfilingCheckCategoriesSpec](../models/tables.md#tableprofilingcheckcategoriesspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"volume\":{\"profile_row_count\":{\"error\":{\"min_count\":1}}}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_profiling_checks
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableProfilingCheckCategoriesSpec(
		volume=TableVolumeProfilingChecksSpec(
			profile_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		)
	)
	
	call_result = update_table_profiling_checks.sync(
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
	from dqops.client.api.tables import update_table_profiling_checks
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableProfilingCheckCategoriesSpec(
		volume=TableVolumeProfilingChecksSpec(
			profile_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		)
	)
	
	call_result = await update_table_profiling_checks.asyncio(
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
	from dqops.client.api.tables import update_table_profiling_checks
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableProfilingCheckCategoriesSpec(
		volume=TableVolumeProfilingChecksSpec(
			profile_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		)
	)
	
	call_result = update_table_profiling_checks.sync(
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
	from dqops.client.api.tables import update_table_profiling_checks
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableProfilingCheckCategoriesSpec(
		volume=TableVolumeProfilingChecksSpec(
			profile_row_count=TableRowCountCheckSpec(
				parameters=TableVolumeRowCountSensorParametersSpec(),
				error=MinCountRule1ParametersSpec(min_count=1),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		)
	)
	
	call_result = await update_table_profiling_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_profiling_checks_model
Updates the data quality profiling checks from a model that contains a patch with changes.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_profiling_checks_model.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/model
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
|Model with the changes to be applied to the data quality profiling checks configuration.|*[CheckContainerModel](../models/common.md#checkcontainermodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/model^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_error_sampling\":false,\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_profiling_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
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
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = update_table_profiling_checks_model.sync(
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
	from dqops.client.api.tables import update_table_profiling_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
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
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = await update_table_profiling_checks_model.asyncio(
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
	from dqops.client.api.tables import update_table_profiling_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
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
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = update_table_profiling_checks_model.sync(
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
	from dqops.client.api.tables import update_table_profiling_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
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
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = await update_table_profiling_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_scheduling_group_override
Updates the overridden schedule configuration of an existing table for a named schedule group (named schedule for checks using the same time scale).

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_scheduling_group_override.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/schedulesoverride/{schedulingGroup}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`scheduling_group`](../models/common.md#checkrunschedulegroup)</span>|Check scheduling group (named schedule)|*[CheckRunScheduleGroup](../models/common.md#checkrunschedulegroup)*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table&#x27;s overridden schedule configuration to store or an empty object to clear the schedule configuration on a table|*[MonitoringScheduleSpec](../models/common.md#monitoringschedulespec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/schedulesoverride/partitioned_daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"cron_expression\":\"0 12 1 * *\"}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_scheduling_group_override
	from dqops.client.models import CheckRunScheduleGroup, \
	                                MonitoringScheduleSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = MonitoringScheduleSpec(
		cron_expression='0 12 1 * *',
		disabled=False
	)
	
	call_result = update_table_scheduling_group_override.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_scheduling_group_override
	from dqops.client.models import CheckRunScheduleGroup, \
	                                MonitoringScheduleSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = MonitoringScheduleSpec(
		cron_expression='0 12 1 * *',
		disabled=False
	)
	
	call_result = await update_table_scheduling_group_override.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_scheduling_group_override
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
	
	call_result = update_table_scheduling_group_override.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.tables import update_table_scheduling_group_override
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
	
	call_result = await update_table_scheduling_group_override.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



