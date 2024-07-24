---
title: DQOps REST API search operations
---
# DQOps REST API search operations
Search operations for finding data assets, such as tables.


___
## find_columns
Finds columns in any data source and schema

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/search/find_columns.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/search/columns
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`column_list_model`</span>||*List[[ColumnListModel](../models/common.md#columnlistmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection`</span>|Optional connection name filter, accepts filters in the form: fullname, *suffix, prefix*, *contains*.|*string*| |
|<span class="no-wrap-code">`schema`</span>|Optional schema name filter, accepts filters in the form: fullname, *suffix, prefix*, *contains*.|*string*| |
|<span class="no-wrap-code">`table`</span>|Optional table name filter|*string*| |
|<span class="no-wrap-code">`column`</span>|Optional column name filter|*string*| |
|<span class="no-wrap-code">`column_type`</span>|Optional physical column&#x27;s data type filter|*string*| |
|<span class="no-wrap-code">`label`</span>|Optional labels to filter the columns|*List[string]*| |
|<span class="no-wrap-code">`page`</span>|Page number, the first page is 1|*long*| |
|<span class="no-wrap-code">`limit`</span>|Page size, the default is 100 rows, but paging is disabled is neither page and limit parameters are provided|*long*| |
|<span class="no-wrap-code">[`check_type`](../models/common.md#checktype)</span>|Optional parameter for the check type, when provided, returns the results for data quality dimensions for the data quality checks of that type|*[CheckType](../models/common.md#checktype)*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/search/columns^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "connection_name" : "sample_connection",
		  "table" : {
		    "schema_name" : "sample_schema",
		    "table_name" : "sample_table"
		  },
		  "column_name" : "sample_column",
		  "has_any_configured_checks" : true,
		  "has_any_configured_profiling_checks" : true,
		  "type_snapshot" : {
		    "column_type" : "string",
		    "nullable" : false,
		    "length" : 256
		  },
		  "can_edit" : false,
		  "can_collect_statistics" : true,
		  "can_run_checks" : true,
		  "can_delete_data" : true
		}, {
		  "connection_name" : "sample_connection",
		  "table" : {
		    "schema_name" : "sample_schema",
		    "table_name" : "sample_table"
		  },
		  "column_name" : "sample_column",
		  "has_any_configured_checks" : true,
		  "has_any_configured_profiling_checks" : true,
		  "type_snapshot" : {
		    "column_type" : "string",
		    "nullable" : false,
		    "length" : 256
		  },
		  "can_edit" : false,
		  "can_collect_statistics" : true,
		  "can_run_checks" : true,
		  "can_delete_data" : true
		}, {
		  "connection_name" : "sample_connection",
		  "table" : {
		    "schema_name" : "sample_schema",
		    "table_name" : "sample_table"
		  },
		  "column_name" : "sample_column",
		  "has_any_configured_checks" : true,
		  "has_any_configured_profiling_checks" : true,
		  "type_snapshot" : {
		    "column_type" : "string",
		    "nullable" : false,
		    "length" : 256
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
	from dqops.client.api.search import find_columns
	from dqops.client.models import CheckType
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = find_columns.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ColumnListModel(
				connection_name='sample_connection',
				table=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				column_name='sample_column',
				disabled=False,
				id=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				type_snapshot=ColumnTypeSnapshotSpec(
					column_type='string',
					nullable=False,
					length=256
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			ColumnListModel(
				connection_name='sample_connection',
				table=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				column_name='sample_column',
				disabled=False,
				id=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				type_snapshot=ColumnTypeSnapshotSpec(
					column_type='string',
					nullable=False,
					length=256
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			ColumnListModel(
				connection_name='sample_connection',
				table=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				column_name='sample_column',
				disabled=False,
				id=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				type_snapshot=ColumnTypeSnapshotSpec(
					column_type='string',
					nullable=False,
					length=256
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
	from dqops.client.api.search import find_columns
	from dqops.client.models import CheckType
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await find_columns.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ColumnListModel(
				connection_name='sample_connection',
				table=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				column_name='sample_column',
				disabled=False,
				id=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				type_snapshot=ColumnTypeSnapshotSpec(
					column_type='string',
					nullable=False,
					length=256
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			ColumnListModel(
				connection_name='sample_connection',
				table=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				column_name='sample_column',
				disabled=False,
				id=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				type_snapshot=ColumnTypeSnapshotSpec(
					column_type='string',
					nullable=False,
					length=256
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			ColumnListModel(
				connection_name='sample_connection',
				table=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				column_name='sample_column',
				disabled=False,
				id=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				type_snapshot=ColumnTypeSnapshotSpec(
					column_type='string',
					nullable=False,
					length=256
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
	from dqops.client.api.search import find_columns
	from dqops.client.models import CheckType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = find_columns.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ColumnListModel(
				connection_name='sample_connection',
				table=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				column_name='sample_column',
				disabled=False,
				id=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				type_snapshot=ColumnTypeSnapshotSpec(
					column_type='string',
					nullable=False,
					length=256
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			ColumnListModel(
				connection_name='sample_connection',
				table=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				column_name='sample_column',
				disabled=False,
				id=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				type_snapshot=ColumnTypeSnapshotSpec(
					column_type='string',
					nullable=False,
					length=256
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			ColumnListModel(
				connection_name='sample_connection',
				table=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				column_name='sample_column',
				disabled=False,
				id=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				type_snapshot=ColumnTypeSnapshotSpec(
					column_type='string',
					nullable=False,
					length=256
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
	from dqops.client.api.search import find_columns
	from dqops.client.models import CheckType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await find_columns.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ColumnListModel(
				connection_name='sample_connection',
				table=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				column_name='sample_column',
				disabled=False,
				id=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				type_snapshot=ColumnTypeSnapshotSpec(
					column_type='string',
					nullable=False,
					length=256
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			ColumnListModel(
				connection_name='sample_connection',
				table=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				column_name='sample_column',
				disabled=False,
				id=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				type_snapshot=ColumnTypeSnapshotSpec(
					column_type='string',
					nullable=False,
					length=256
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			),
			ColumnListModel(
				connection_name='sample_connection',
				table=PhysicalTableName(
					schema_name='sample_schema',
					table_name='sample_table'
				),
				column_name='sample_column',
				disabled=False,
				id=False,
				has_any_configured_checks=True,
				has_any_configured_profiling_checks=True,
				has_any_configured_monitoring_checks=False,
				has_any_configured_partition_checks=False,
				type_snapshot=ColumnTypeSnapshotSpec(
					column_type='string',
					nullable=False,
					length=256
				),
				can_edit=False,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			)
		]
        ```
    
    
    



___
## find_tables
Finds tables in any data source and schema

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/search/find_tables.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/search/tables
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`table_list_model`</span>||*List[[TableListModel](../models/common.md#tablelistmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection`</span>|Optional connection name filter, accepts filters in the form: fullname, *suffix, prefix*, *contains*.|*string*| |
|<span class="no-wrap-code">`schema`</span>|Optional schema name filter, accepts filters in the form: fullname, *suffix, prefix*, *contains*.|*string*| |
|<span class="no-wrap-code">`table`</span>|Optional table name filter|*string*| |
|<span class="no-wrap-code">`label`</span>|Optional labels to filter the tables|*List[string]*| |
|<span class="no-wrap-code">`page`</span>|Page number, the first page is 1|*long*| |
|<span class="no-wrap-code">`limit`</span>|Page size, the default is 100 rows, but paging is disabled is neither page and limit parameters are provided|*long*| |
|<span class="no-wrap-code">[`check_type`](../models/common.md#checktype)</span>|Optional parameter for the check type, when provided, returns the results for data quality dimensions for the data quality checks of that type|*[CheckType](../models/common.md#checktype)*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/search/tables^
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
		    "deleteErrorSamples" : false,
		    "deleteIncidents" : false
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
		    "deleteErrorSamples" : false,
		    "deleteIncidents" : false
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
		    "deleteErrorSamples" : false,
		    "deleteIncidents" : false
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
	from dqops.client.api.search import find_tables
	from dqops.client.models import CheckType
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = find_tables.sync(
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
					delete_error_samples=False,
					delete_incidents=False
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
					delete_error_samples=False,
					delete_incidents=False
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
					delete_error_samples=False,
					delete_incidents=False
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
	from dqops.client.api.search import find_tables
	from dqops.client.models import CheckType
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await find_tables.asyncio(
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
					delete_error_samples=False,
					delete_incidents=False
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
					delete_error_samples=False,
					delete_incidents=False
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
					delete_error_samples=False,
					delete_incidents=False
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
	from dqops.client.api.search import find_tables
	from dqops.client.models import CheckType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = find_tables.sync(
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
					delete_error_samples=False,
					delete_incidents=False
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
					delete_error_samples=False,
					delete_incidents=False
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
					delete_error_samples=False,
					delete_incidents=False
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
	from dqops.client.api.search import find_tables
	from dqops.client.models import CheckType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await find_tables.asyncio(
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
					delete_error_samples=False,
					delete_incidents=False
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
					delete_error_samples=False,
					delete_incidents=False
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
					delete_error_samples=False,
					delete_incidents=False
				),
				can_edit=True,
				can_collect_statistics=True,
				can_run_checks=True,
				can_delete_data=True
			)
		]
        ```
    
    
    



