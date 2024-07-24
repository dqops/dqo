---
title: DQOps REST API columns operations
---
# DQOps REST API columns operations
Operations related to manage the metadata of columns, and managing the configuration of column-level data quality checks.


___
## create_column
Creates a new column (adds a column metadata to the table)

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/create_column.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Column specification|*[ColumnSpec](../../reference/yaml/TableYaml.md#columnspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"type_snapshot\":{\"column_type\":\"string\",\"nullable\":false,\"length\":256},\"profiling_checks\":{\"nulls\":{\"profile_nulls_count\":{\"error\":{\"max_count\":0}}}}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import create_column
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                ColumnSpec, \
	                                ColumnTypeSnapshotSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ColumnSpec(
		disabled=False,
		type_snapshot=ColumnTypeSnapshotSpec(
			column_type='string',
			nullable=False,
			length=256
		),
		id=False,
		profiling_checks=ColumnProfilingCheckCategoriesSpec(
			nulls=ColumnNullsProfilingChecksSpec(
				profile_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonProfilingChecksSpecMap()
		)
	)
	
	call_result = create_column.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import create_column
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                ColumnSpec, \
	                                ColumnTypeSnapshotSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ColumnSpec(
		disabled=False,
		type_snapshot=ColumnTypeSnapshotSpec(
			column_type='string',
			nullable=False,
			length=256
		),
		id=False,
		profiling_checks=ColumnProfilingCheckCategoriesSpec(
			nulls=ColumnNullsProfilingChecksSpec(
				profile_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonProfilingChecksSpecMap()
		)
	)
	
	call_result = await create_column.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import create_column
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                ColumnSpec, \
	                                ColumnTypeSnapshotSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ColumnSpec(
		disabled=False,
		type_snapshot=ColumnTypeSnapshotSpec(
			column_type='string',
			nullable=False,
			length=256
		),
		id=False,
		profiling_checks=ColumnProfilingCheckCategoriesSpec(
			nulls=ColumnNullsProfilingChecksSpec(
				profile_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonProfilingChecksSpecMap()
		)
	)
	
	call_result = create_column.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import create_column
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                ColumnSpec, \
	                                ColumnTypeSnapshotSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ColumnSpec(
		disabled=False,
		type_snapshot=ColumnTypeSnapshotSpec(
			column_type='string',
			nullable=False,
			length=256
		),
		id=False,
		profiling_checks=ColumnProfilingCheckCategoriesSpec(
			nulls=ColumnNullsProfilingChecksSpec(
				profile_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonProfilingChecksSpecMap()
		)
	)
	
	call_result = await create_column.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## delete_column
Deletes a column from the table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/delete_column.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}
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
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X DELETE http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column^
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
	from dqops.client.api.columns import delete_column
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = delete_column.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import delete_column
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await delete_column.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import delete_column
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = delete_column.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import delete_column
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await delete_column.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
## get_column
Returns the full column specification

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`column_model`](../models/columns.md#columnmodel)</span>||*[ColumnModel](../models/columns.md#columnmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "connection_name" : "sample_connection",
		  "table" : {
		    "schema_name" : "sample_schema",
		    "table_name" : "sample_table"
		  },
		  "column_name" : "sample_column",
		  "spec" : {
		    "type_snapshot" : {
		      "column_type" : "string",
		      "nullable" : false,
		      "length" : 256
		    },
		    "profiling_checks" : {
		      "nulls" : {
		        "profile_nulls_count" : {
		          "error" : {
		            "max_count" : 0
		          }
		        }
		      }
		    }
		  },
		  "can_edit" : true
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnModel(
			connection_name='sample_connection',
			table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			column_name='sample_column',
			spec=ColumnSpec(
				disabled=False,
				type_snapshot=ColumnTypeSnapshotSpec(
					column_type='string',
					nullable=False,
					length=256
				),
				id=False,
				profiling_checks=ColumnProfilingCheckCategoriesSpec(
					nulls=ColumnNullsProfilingChecksSpec(
						profile_nulls_count=ColumnNullsCountCheckSpec(
							parameters=ColumnNullsNullsCountSensorParametersSpec(),
							error=MaxCountRule0ErrorParametersSpec(max_count=0),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False
						)
					),
					comparisons=ColumnComparisonProfilingChecksSpecMap()
				)
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnModel(
			connection_name='sample_connection',
			table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			column_name='sample_column',
			spec=ColumnSpec(
				disabled=False,
				type_snapshot=ColumnTypeSnapshotSpec(
					column_type='string',
					nullable=False,
					length=256
				),
				id=False,
				profiling_checks=ColumnProfilingCheckCategoriesSpec(
					nulls=ColumnNullsProfilingChecksSpec(
						profile_nulls_count=ColumnNullsCountCheckSpec(
							parameters=ColumnNullsNullsCountSensorParametersSpec(),
							error=MaxCountRule0ErrorParametersSpec(max_count=0),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False
						)
					),
					comparisons=ColumnComparisonProfilingChecksSpecMap()
				)
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnModel(
			connection_name='sample_connection',
			table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			column_name='sample_column',
			spec=ColumnSpec(
				disabled=False,
				type_snapshot=ColumnTypeSnapshotSpec(
					column_type='string',
					nullable=False,
					length=256
				),
				id=False,
				profiling_checks=ColumnProfilingCheckCategoriesSpec(
					nulls=ColumnNullsProfilingChecksSpec(
						profile_nulls_count=ColumnNullsCountCheckSpec(
							parameters=ColumnNullsNullsCountSensorParametersSpec(),
							error=MaxCountRule0ErrorParametersSpec(max_count=0),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False
						)
					),
					comparisons=ColumnComparisonProfilingChecksSpecMap()
				)
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnModel(
			connection_name='sample_connection',
			table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			column_name='sample_column',
			spec=ColumnSpec(
				disabled=False,
				type_snapshot=ColumnTypeSnapshotSpec(
					column_type='string',
					nullable=False,
					length=256
				),
				id=False,
				profiling_checks=ColumnProfilingCheckCategoriesSpec(
					nulls=ColumnNullsProfilingChecksSpec(
						profile_nulls_count=ColumnNullsCountCheckSpec(
							parameters=ColumnNullsNullsCountSensorParametersSpec(),
							error=MaxCountRule0ErrorParametersSpec(max_count=0),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False
						)
					),
					comparisons=ColumnComparisonProfilingChecksSpecMap()
				)
			),
			can_edit=True
		)
        ```
    
    
    



___
## get_column_basic
Returns the column specification

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_basic.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/basic
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`column_list_model`](../models/common.md#columnlistmodel)</span>||*[ColumnListModel](../models/common.md#columnlistmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/basic^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
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
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_basic
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_basic.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
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
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_basic
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_basic.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
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
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_basic
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_basic.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
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
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_basic
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_basic.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
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
        ```
    
    
    



___
## get_column_comments
Return the list of comments assigned to a column

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_comments.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/comments
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
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/comments^
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
	from dqops.client.api.columns import get_column_comments
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_comments.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import get_column_comments
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_comments.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import get_column_comments
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_comments.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import get_column_comments
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_comments.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
## get_column_labels
Return the list of labels assigned to a column

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_labels.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/labels
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
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/labels^
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
	from dqops.client.api.columns import get_column_labels
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_labels.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import get_column_labels
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_labels.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import get_column_labels
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_labels.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import get_column_labels
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_labels.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
## get_column_monitoring_checks_basic_model
Return a simplistic UI friendly model of column level data quality monitoring on a column

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_monitoring_checks_basic_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/model/basic
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
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily/model/basic^
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
	from dqops.client.api.columns import get_column_monitoring_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import get_column_monitoring_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import get_column_monitoring_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import get_column_monitoring_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
## get_column_monitoring_checks_daily
Return the configuration of daily column level data quality monitoring on a column

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_monitoring_checks_daily.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/daily
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`column_daily_monitoring_check_categories_spec`](../models/columns.md#columndailymonitoringcheckcategoriesspec)</span>||*[ColumnDailyMonitoringCheckCategoriesSpec](../models/columns.md#columndailymonitoringcheckcategoriesspec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "nulls" : {
		    "daily_nulls_count" : {
		      "error" : {
		        "max_count" : 0
		      }
		    }
		  }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_daily
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_checks_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnDailyMonitoringCheckCategoriesSpec(
			nulls=ColumnNullsDailyMonitoringChecksSpec(
				daily_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_daily
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_checks_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnDailyMonitoringCheckCategoriesSpec(
			nulls=ColumnNullsDailyMonitoringChecksSpec(
				daily_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_daily
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_checks_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnDailyMonitoringCheckCategoriesSpec(
			nulls=ColumnNullsDailyMonitoringChecksSpec(
				daily_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_daily
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_checks_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnDailyMonitoringCheckCategoriesSpec(
			nulls=ColumnNullsDailyMonitoringChecksSpec(
				daily_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
		)
        ```
    
    
    



___
## get_column_monitoring_checks_model
Return a UI friendly model of column level data quality monitoring on a column

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_monitoring_checks_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/model
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
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily/model^
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
		      "sensor_name" : "sample_target/sample_category/table/volume/row_count",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_error_sampling" : false,
		      "supports_grouping" : false,
		      "default_severity" : "error",
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
	from dqops.client.api.columns import get_column_monitoring_checks_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_monitoring_checks_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_monitoring_checks_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_monitoring_checks_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
## get_column_monitoring_checks_model_filter
Return a UI friendly model of column level data quality monitoring on a column filtered by category and check name

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_monitoring_checks_model_filter.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/model/filter/{checkCategory}/{checkName}
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
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*|:material-check-bold:|
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily/model/filter/sample_category/sample_check^
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
		      "sensor_name" : "sample_target/sample_category/table/volume/row_count",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_error_sampling" : false,
		      "supports_grouping" : false,
		      "default_severity" : "error",
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
	from dqops.client.api.columns import get_column_monitoring_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_checks_model_filter.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_monitoring_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_monitoring_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_checks_model_filter.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_monitoring_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
## get_column_monitoring_checks_monthly
Return the configuration of monthly column level data quality monitoring on a column

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_monitoring_checks_monthly.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/monthly
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`column_monthly_monitoring_check_categories_spec`](../models/columns.md#columnmonthlymonitoringcheckcategoriesspec)</span>||*[ColumnMonthlyMonitoringCheckCategoriesSpec](../models/columns.md#columnmonthlymonitoringcheckcategoriesspec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/monthly^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "nulls" : {
		    "monthly_nulls_count" : {
		      "error" : {
		        "max_count" : 0
		      }
		    }
		  }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_monthly
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnMonthlyMonitoringCheckCategoriesSpec(
			nulls=ColumnNullsMonthlyMonitoringChecksSpec(
				monthly_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonMonthlyMonitoringChecksSpecMap()
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_monthly
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnMonthlyMonitoringCheckCategoriesSpec(
			nulls=ColumnNullsMonthlyMonitoringChecksSpec(
				monthly_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonMonthlyMonitoringChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_monthly
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnMonthlyMonitoringCheckCategoriesSpec(
			nulls=ColumnNullsMonthlyMonitoringChecksSpec(
				monthly_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonMonthlyMonitoringChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_monthly
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnMonthlyMonitoringCheckCategoriesSpec(
			nulls=ColumnNullsMonthlyMonitoringChecksSpec(
				monthly_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonMonthlyMonitoringChecksSpecMap()
		)
        ```
    
    
    



___
## get_column_partitioned_checks_basic_model
Return a simplistic UI friendly model of column level data quality partitioned checks on a column

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_partitioned_checks_basic_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model/basic
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
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily/model/basic^
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
	from dqops.client.api.columns import get_column_partitioned_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import get_column_partitioned_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import get_column_partitioned_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import get_column_partitioned_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
## get_column_partitioned_checks_daily
Return the configuration of daily column level data quality partitioned checks on a column

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_partitioned_checks_daily.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/daily
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`column_daily_partitioned_check_categories_spec`](../models/columns.md#columndailypartitionedcheckcategoriesspec)</span>||*[ColumnDailyPartitionedCheckCategoriesSpec](../models/columns.md#columndailypartitionedcheckcategoriesspec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "nulls" : {
		    "daily_partition_nulls_count" : {
		      "error" : {
		        "max_count" : 0
		      }
		    }
		  }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_daily
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_checks_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnDailyPartitionedCheckCategoriesSpec(
			nulls=ColumnNullsDailyPartitionedChecksSpec(
				daily_partition_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonDailyPartitionedChecksSpecMap()
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_daily
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_checks_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnDailyPartitionedCheckCategoriesSpec(
			nulls=ColumnNullsDailyPartitionedChecksSpec(
				daily_partition_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonDailyPartitionedChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_daily
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_checks_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnDailyPartitionedCheckCategoriesSpec(
			nulls=ColumnNullsDailyPartitionedChecksSpec(
				daily_partition_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonDailyPartitionedChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_daily
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_checks_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnDailyPartitionedCheckCategoriesSpec(
			nulls=ColumnNullsDailyPartitionedChecksSpec(
				daily_partition_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonDailyPartitionedChecksSpecMap()
		)
        ```
    
    
    



___
## get_column_partitioned_checks_model
Return a UI friendly model of column level data quality partitioned checks on a column

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_partitioned_checks_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model
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
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily/model^
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
		      "sensor_name" : "sample_target/sample_category/table/volume/row_count",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_error_sampling" : false,
		      "supports_grouping" : false,
		      "default_severity" : "error",
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
	from dqops.client.api.columns import get_column_partitioned_checks_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_partitioned_checks_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_partitioned_checks_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_partitioned_checks_model
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
## get_column_partitioned_checks_model_filter
Return a UI friendly model of column level data quality partitioned checks on a column, filtered by category and check name

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_partitioned_checks_model_filter.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model/filter/{checkCategory}/{checkName}
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
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*|:material-check-bold:|
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily/model/filter/sample_category/sample_check^
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
		      "sensor_name" : "sample_target/sample_category/table/volume/row_count",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_error_sampling" : false,
		      "supports_grouping" : false,
		      "default_severity" : "error",
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
	from dqops.client.api.columns import get_column_partitioned_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_checks_model_filter.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_partitioned_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_partitioned_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_checks_model_filter.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_partitioned_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
## get_column_partitioned_checks_monthly
Return the configuration of monthly column level data quality partitioned checks on a column

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_partitioned_checks_monthly.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/monthly
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`column_monthly_partitioned_check_categories_spec`](../models/columns.md#columnmonthlypartitionedcheckcategoriesspec)</span>||*[ColumnMonthlyPartitionedCheckCategoriesSpec](../models/columns.md#columnmonthlypartitionedcheckcategoriesspec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/monthly^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "nulls" : {
		    "monthly_partition_nulls_count" : {
		      "error" : {
		        "max_count" : 0
		      }
		    }
		  }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_monthly
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnMonthlyPartitionedCheckCategoriesSpec(
			nulls=ColumnNullsMonthlyPartitionedChecksSpec(
				monthly_partition_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonMonthlyPartitionedChecksSpecMap()
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_monthly
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnMonthlyPartitionedCheckCategoriesSpec(
			nulls=ColumnNullsMonthlyPartitionedChecksSpec(
				monthly_partition_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonMonthlyPartitionedChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_monthly
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnMonthlyPartitionedCheckCategoriesSpec(
			nulls=ColumnNullsMonthlyPartitionedChecksSpec(
				monthly_partition_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonMonthlyPartitionedChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_monthly
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnMonthlyPartitionedCheckCategoriesSpec(
			nulls=ColumnNullsMonthlyPartitionedChecksSpec(
				monthly_partition_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonMonthlyPartitionedChecksSpecMap()
		)
        ```
    
    
    



___
## get_column_profiling_checks
Return the configuration of column level data quality profiling checks on a column

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_profiling_checks.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`column_profiling_check_categories_spec`](../models/columns.md#columnprofilingcheckcategoriesspec)</span>||*[ColumnProfilingCheckCategoriesSpec](../models/columns.md#columnprofilingcheckcategoriesspec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "nulls" : {
		    "profile_nulls_count" : {
		      "error" : {
		        "max_count" : 0
		      }
		    }
		  }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_profiling_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnProfilingCheckCategoriesSpec(
			nulls=ColumnNullsProfilingChecksSpec(
				profile_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonProfilingChecksSpecMap()
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_profiling_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnProfilingCheckCategoriesSpec(
			nulls=ColumnNullsProfilingChecksSpec(
				profile_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonProfilingChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_profiling_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnProfilingCheckCategoriesSpec(
			nulls=ColumnNullsProfilingChecksSpec(
				profile_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonProfilingChecksSpecMap()
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_profiling_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnProfilingCheckCategoriesSpec(
			nulls=ColumnNullsProfilingChecksSpec(
				profile_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonProfilingChecksSpecMap()
		)
        ```
    
    
    



___
## get_column_profiling_checks_basic_model
Return a simplistic UI friendly model of column level data quality profiling checks on a column

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_profiling_checks_basic_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/model/basic
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
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/model/basic^
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
	from dqops.client.api.columns import get_column_profiling_checks_basic_model
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_profiling_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import get_column_profiling_checks_basic_model
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_profiling_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import get_column_profiling_checks_basic_model
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_profiling_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
	from dqops.client.api.columns import get_column_profiling_checks_basic_model
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_profiling_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
## get_column_profiling_checks_model
Return a UI friendly model of data quality profiling checks on a column

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_profiling_checks_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/model
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
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/model^
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
		      "sensor_name" : "sample_target/sample_category/table/volume/row_count",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_error_sampling" : false,
		      "supports_grouping" : false,
		      "default_severity" : "error",
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
	from dqops.client.api.columns import get_column_profiling_checks_model
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_profiling_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_profiling_checks_model
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_profiling_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_profiling_checks_model
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_profiling_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_profiling_checks_model
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_profiling_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
## get_column_profiling_checks_model_filter
Return a UI friendly model of data quality profiling checks on a column filtered by category and check name

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_profiling_checks_model_filter.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/model/filter/{checkCategory}/{checkName}
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
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*|:material-check-bold:|
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/model/filter/sample_category/sample_check^
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
		      "sensor_name" : "sample_target/sample_category/table/volume/row_count",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_error_sampling" : false,
		      "supports_grouping" : false,
		      "default_severity" : "error",
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
	from dqops.client.api.columns import get_column_profiling_checks_model_filter
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_profiling_checks_model_filter.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_profiling_checks_model_filter
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_profiling_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_profiling_checks_model_filter
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_profiling_checks_model_filter.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
	from dqops.client.api.columns import get_column_profiling_checks_model_filter
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_profiling_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=RuleSeverityLevel.ERROR,
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
## get_column_statistics
Returns the column specification with the metrics captured by the most recent statistics collection.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_statistics.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/statistics
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`column_statistics_model`](../models/columns.md#columnstatisticsmodel)</span>||*[ColumnStatisticsModel](../models/columns.md#columnstatisticsmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/statistics^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "connection_name" : "sample_connection",
		  "table" : {
		    "schema_name" : "sample_schema",
		    "table_name" : "sample_table"
		  },
		  "column_name" : "sample_column",
		  "has_any_configured_checks" : true,
		  "type_snapshot" : {
		    "column_type" : "string",
		    "nullable" : false,
		    "length" : 256
		  },
		  "statistics" : [ {
		    "category" : "sample_category",
		    "collector" : "sample_collector",
		    "sensorName" : "table/volume/row_count",
		    "resultDataType" : "integer",
		    "result" : 4372,
		    "collectedAt" : "2007-10-11T18:00:00"
		  }, {
		    "category" : "sample_category",
		    "collector" : "sample_collector",
		    "sensorName" : "table/volume/row_count",
		    "resultDataType" : "integer",
		    "result" : 9624,
		    "collectedAt" : "2007-10-12T18:00:00"
		  }, {
		    "category" : "sample_category",
		    "collector" : "sample_collector",
		    "sensorName" : "table/volume/row_count",
		    "resultDataType" : "integer",
		    "result" : 1575,
		    "collectedAt" : "2007-10-13T18:00:00"
		  }, {
		    "category" : "sample_category",
		    "collector" : "sample_collector",
		    "sensorName" : "table/volume/row_count",
		    "resultDataType" : "integer",
		    "result" : 5099,
		    "collectedAt" : "2007-10-14T18:00:00"
		  }, {
		    "category" : "sample_category",
		    "collector" : "sample_collector",
		    "sensorName" : "table/volume/row_count",
		    "resultDataType" : "integer",
		    "result" : 9922,
		    "collectedAt" : "2007-10-15T18:00:00"
		  } ],
		  "collect_column_statistics_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true,
		    "columnNames" : [ "sample_column" ]
		  },
		  "can_collect_statistics" : true
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_statistics
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_statistics.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnStatisticsModel(
			connection_name='sample_connection',
			table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			column_name='sample_column',
			disabled=False,
			has_any_configured_checks=True,
			type_snapshot=ColumnTypeSnapshotSpec(
				column_type='string',
				nullable=False,
				length=256
			),
			statistics=[
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=4372,
					collected_at=Some date/time value: [2007-10-11T18:00]
				),
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=9624,
					collected_at=Some date/time value: [2007-10-12T18:00]
				),
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=1575,
					collected_at=Some date/time value: [2007-10-13T18:00]
				),
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=5099,
					collected_at=Some date/time value: [2007-10-14T18:00]
				),
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=9922,
					collected_at=Some date/time value: [2007-10-15T18:00]
				)
			],
			collect_column_statistics_job_template=StatisticsCollectorSearchFilters(
				column_names=[
					'sample_column'
				],
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			can_collect_statistics=True
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_statistics
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_statistics.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnStatisticsModel(
			connection_name='sample_connection',
			table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			column_name='sample_column',
			disabled=False,
			has_any_configured_checks=True,
			type_snapshot=ColumnTypeSnapshotSpec(
				column_type='string',
				nullable=False,
				length=256
			),
			statistics=[
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=4372,
					collected_at=Some date/time value: [2007-10-11T18:00]
				),
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=9624,
					collected_at=Some date/time value: [2007-10-12T18:00]
				),
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=1575,
					collected_at=Some date/time value: [2007-10-13T18:00]
				),
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=5099,
					collected_at=Some date/time value: [2007-10-14T18:00]
				),
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=9922,
					collected_at=Some date/time value: [2007-10-15T18:00]
				)
			],
			collect_column_statistics_job_template=StatisticsCollectorSearchFilters(
				column_names=[
					'sample_column'
				],
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			can_collect_statistics=True
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_statistics
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_statistics.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnStatisticsModel(
			connection_name='sample_connection',
			table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			column_name='sample_column',
			disabled=False,
			has_any_configured_checks=True,
			type_snapshot=ColumnTypeSnapshotSpec(
				column_type='string',
				nullable=False,
				length=256
			),
			statistics=[
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=4372,
					collected_at=Some date/time value: [2007-10-11T18:00]
				),
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=9624,
					collected_at=Some date/time value: [2007-10-12T18:00]
				),
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=1575,
					collected_at=Some date/time value: [2007-10-13T18:00]
				),
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=5099,
					collected_at=Some date/time value: [2007-10-14T18:00]
				),
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=9922,
					collected_at=Some date/time value: [2007-10-15T18:00]
				)
			],
			collect_column_statistics_job_template=StatisticsCollectorSearchFilters(
				column_names=[
					'sample_column'
				],
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			can_collect_statistics=True
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_statistics
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_statistics.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        ColumnStatisticsModel(
			connection_name='sample_connection',
			table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			column_name='sample_column',
			disabled=False,
			has_any_configured_checks=True,
			type_snapshot=ColumnTypeSnapshotSpec(
				column_type='string',
				nullable=False,
				length=256
			),
			statistics=[
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=4372,
					collected_at=Some date/time value: [2007-10-11T18:00]
				),
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=9624,
					collected_at=Some date/time value: [2007-10-12T18:00]
				),
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=1575,
					collected_at=Some date/time value: [2007-10-13T18:00]
				),
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=5099,
					collected_at=Some date/time value: [2007-10-14T18:00]
				),
				StatisticsMetricModel(
					category='sample_category',
					collector='sample_collector',
					sensor_name='table/volume/row_count',
					result_data_type=StatisticsResultDataType.INTEGER,
					result=9922,
					collected_at=Some date/time value: [2007-10-15T18:00]
				)
			],
			collect_column_statistics_job_template=StatisticsCollectorSearchFilters(
				column_names=[
					'sample_column'
				],
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			can_collect_statistics=True
		)
        ```
    
    
    



___
## get_columns
Returns a list of columns inside a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_columns.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`column_list_model`</span>||*List[[ColumnListModel](../models/common.md#columnlistmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`data_quality_status`</span>|Optional parameter to opt out from retrieving the most recent data quality status for the column. By default, DQOps calculates the data quality status from the data quality results.|*boolean*| |
|<span class="no-wrap-code">[`check_type`](../models/common.md#checktype)</span>|Optional parameter for the check type, when provided, returns the results for data quality dimensions for the data quality checks of that type|*[CheckType](../models/common.md#checktype)*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns^
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
	from dqops.client.api.columns import get_columns
	from dqops.client.models import CheckType
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_columns.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.columns import get_columns
	from dqops.client.models import CheckType
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_columns.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.columns import get_columns
	from dqops.client.models import CheckType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_columns.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.columns import get_columns
	from dqops.client.models import CheckType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_columns.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
## get_columns_statistics
Returns a list of columns inside a table with the metrics captured by the most recent statistics collection.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_columns_statistics.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/statistics
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_columns_statistics_model`](../models/columns.md#tablecolumnsstatisticsmodel)</span>||*[TableColumnsStatisticsModel](../models/columns.md#tablecolumnsstatisticsmodel)*|




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
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/statistics^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "connection_name" : "sample_connection",
		  "table" : {
		    "schema_name" : "sample_schema",
		    "table_name" : "sample_table"
		  },
		  "column_statistics" : [ {
		    "connection_name" : "sample_connection",
		    "table" : {
		      "schema_name" : "sample_schema",
		      "table_name" : "sample_table"
		    },
		    "column_name" : "sample_column",
		    "has_any_configured_checks" : true,
		    "type_snapshot" : {
		      "column_type" : "string",
		      "nullable" : false,
		      "length" : 256
		    },
		    "statistics" : [ {
		      "category" : "sample_category",
		      "collector" : "sample_collector",
		      "sensorName" : "table/volume/row_count",
		      "resultDataType" : "integer",
		      "result" : 4372,
		      "collectedAt" : "2007-10-11T18:00:00"
		    }, {
		      "category" : "sample_category",
		      "collector" : "sample_collector",
		      "sensorName" : "table/volume/row_count",
		      "resultDataType" : "integer",
		      "result" : 9624,
		      "collectedAt" : "2007-10-12T18:00:00"
		    }, {
		      "category" : "sample_category",
		      "collector" : "sample_collector",
		      "sensorName" : "table/volume/row_count",
		      "resultDataType" : "integer",
		      "result" : 1575,
		      "collectedAt" : "2007-10-13T18:00:00"
		    }, {
		      "category" : "sample_category",
		      "collector" : "sample_collector",
		      "sensorName" : "table/volume/row_count",
		      "resultDataType" : "integer",
		      "result" : 5099,
		      "collectedAt" : "2007-10-14T18:00:00"
		    }, {
		      "category" : "sample_category",
		      "collector" : "sample_collector",
		      "sensorName" : "table/volume/row_count",
		      "resultDataType" : "integer",
		      "result" : 9922,
		      "collectedAt" : "2007-10-15T18:00:00"
		    } ],
		    "collect_column_statistics_job_template" : {
		      "connection" : "sample_connection",
		      "fullTableName" : "sample_schema.sample_table",
		      "enabled" : true,
		      "columnNames" : [ "sample_column" ]
		    },
		    "can_collect_statistics" : true
		  }, {
		    "connection_name" : "sample_connection",
		    "table" : {
		      "schema_name" : "sample_schema",
		      "table_name" : "sample_table"
		    },
		    "column_name" : "sample_column_1",
		    "has_any_configured_checks" : true,
		    "type_snapshot" : {
		      "column_type" : "string",
		      "nullable" : false,
		      "length" : 256
		    },
		    "statistics" : [ {
		      "category" : "sample_category",
		      "collector" : "sample_collector",
		      "sensorName" : "table/volume/row_count",
		      "resultDataType" : "integer",
		      "result" : 4372,
		      "collectedAt" : "2007-10-11T18:00:00"
		    }, {
		      "category" : "sample_category",
		      "collector" : "sample_collector",
		      "sensorName" : "table/volume/row_count",
		      "resultDataType" : "integer",
		      "result" : 9624,
		      "collectedAt" : "2007-10-12T18:00:00"
		    }, {
		      "category" : "sample_category",
		      "collector" : "sample_collector",
		      "sensorName" : "table/volume/row_count",
		      "resultDataType" : "integer",
		      "result" : 1575,
		      "collectedAt" : "2007-10-13T18:00:00"
		    }, {
		      "category" : "sample_category",
		      "collector" : "sample_collector",
		      "sensorName" : "table/volume/row_count",
		      "resultDataType" : "integer",
		      "result" : 5099,
		      "collectedAt" : "2007-10-14T18:00:00"
		    }, {
		      "category" : "sample_category",
		      "collector" : "sample_collector",
		      "sensorName" : "table/volume/row_count",
		      "resultDataType" : "integer",
		      "result" : 9922,
		      "collectedAt" : "2007-10-15T18:00:00"
		    } ],
		    "collect_column_statistics_job_template" : {
		      "connection" : "sample_connection",
		      "fullTableName" : "sample_schema.sample_table",
		      "enabled" : true,
		      "columnNames" : [ "sample_column" ]
		    },
		    "can_collect_statistics" : true
		  } ],
		  "collect_column_statistics_job_template" : {
		    "connection" : "sample_connection",
		    "fullTableName" : "sample_schema.sample_table",
		    "enabled" : true,
		    "columnNames" : [ "sample_column" ],
		    "collectorCategory" : "sample_category"
		  },
		  "can_collect_statistics" : true
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_columns_statistics
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_columns_statistics.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableColumnsStatisticsModel(
			connection_name='sample_connection',
			table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			column_statistics=[
				ColumnStatisticsModel(
					connection_name='sample_connection',
					table=PhysicalTableName(
						schema_name='sample_schema',
						table_name='sample_table'
					),
					column_name='sample_column',
					disabled=False,
					has_any_configured_checks=True,
					type_snapshot=ColumnTypeSnapshotSpec(
						column_type='string',
						nullable=False,
						length=256
					),
					statistics=[
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=4372,
							collected_at=Some date/time value: [2007-10-11T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=9624,
							collected_at=Some date/time value: [2007-10-12T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=1575,
							collected_at=Some date/time value: [2007-10-13T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=5099,
							collected_at=Some date/time value: [2007-10-14T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=9922,
							collected_at=Some date/time value: [2007-10-15T18:00]
						)
					],
					collect_column_statistics_job_template=StatisticsCollectorSearchFilters(
						column_names=[
							'sample_column'
						],
						connection='sample_connection',
						full_table_name='sample_schema.sample_table',
						enabled=True
					),
					can_collect_statistics=True
				),
				ColumnStatisticsModel(
					connection_name='sample_connection',
					table=PhysicalTableName(
						schema_name='sample_schema',
						table_name='sample_table'
					),
					column_name='sample_column_1',
					disabled=False,
					has_any_configured_checks=True,
					type_snapshot=ColumnTypeSnapshotSpec(
						column_type='string',
						nullable=False,
						length=256
					),
					statistics=[
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=4372,
							collected_at=Some date/time value: [2007-10-11T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=9624,
							collected_at=Some date/time value: [2007-10-12T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=1575,
							collected_at=Some date/time value: [2007-10-13T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=5099,
							collected_at=Some date/time value: [2007-10-14T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=9922,
							collected_at=Some date/time value: [2007-10-15T18:00]
						)
					],
					collect_column_statistics_job_template=StatisticsCollectorSearchFilters(
						column_names=[
							'sample_column'
						],
						connection='sample_connection',
						full_table_name='sample_schema.sample_table',
						enabled=True
					),
					can_collect_statistics=True
				)
			],
			collect_column_statistics_job_template=StatisticsCollectorSearchFilters(
				column_names=[
					'sample_column'
				],
				collector_category='sample_category',
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			can_collect_statistics=True
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_columns_statistics
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_columns_statistics.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableColumnsStatisticsModel(
			connection_name='sample_connection',
			table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			column_statistics=[
				ColumnStatisticsModel(
					connection_name='sample_connection',
					table=PhysicalTableName(
						schema_name='sample_schema',
						table_name='sample_table'
					),
					column_name='sample_column',
					disabled=False,
					has_any_configured_checks=True,
					type_snapshot=ColumnTypeSnapshotSpec(
						column_type='string',
						nullable=False,
						length=256
					),
					statistics=[
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=4372,
							collected_at=Some date/time value: [2007-10-11T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=9624,
							collected_at=Some date/time value: [2007-10-12T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=1575,
							collected_at=Some date/time value: [2007-10-13T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=5099,
							collected_at=Some date/time value: [2007-10-14T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=9922,
							collected_at=Some date/time value: [2007-10-15T18:00]
						)
					],
					collect_column_statistics_job_template=StatisticsCollectorSearchFilters(
						column_names=[
							'sample_column'
						],
						connection='sample_connection',
						full_table_name='sample_schema.sample_table',
						enabled=True
					),
					can_collect_statistics=True
				),
				ColumnStatisticsModel(
					connection_name='sample_connection',
					table=PhysicalTableName(
						schema_name='sample_schema',
						table_name='sample_table'
					),
					column_name='sample_column_1',
					disabled=False,
					has_any_configured_checks=True,
					type_snapshot=ColumnTypeSnapshotSpec(
						column_type='string',
						nullable=False,
						length=256
					),
					statistics=[
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=4372,
							collected_at=Some date/time value: [2007-10-11T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=9624,
							collected_at=Some date/time value: [2007-10-12T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=1575,
							collected_at=Some date/time value: [2007-10-13T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=5099,
							collected_at=Some date/time value: [2007-10-14T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=9922,
							collected_at=Some date/time value: [2007-10-15T18:00]
						)
					],
					collect_column_statistics_job_template=StatisticsCollectorSearchFilters(
						column_names=[
							'sample_column'
						],
						connection='sample_connection',
						full_table_name='sample_schema.sample_table',
						enabled=True
					),
					can_collect_statistics=True
				)
			],
			collect_column_statistics_job_template=StatisticsCollectorSearchFilters(
				column_names=[
					'sample_column'
				],
				collector_category='sample_category',
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			can_collect_statistics=True
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_columns_statistics
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_columns_statistics.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableColumnsStatisticsModel(
			connection_name='sample_connection',
			table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			column_statistics=[
				ColumnStatisticsModel(
					connection_name='sample_connection',
					table=PhysicalTableName(
						schema_name='sample_schema',
						table_name='sample_table'
					),
					column_name='sample_column',
					disabled=False,
					has_any_configured_checks=True,
					type_snapshot=ColumnTypeSnapshotSpec(
						column_type='string',
						nullable=False,
						length=256
					),
					statistics=[
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=4372,
							collected_at=Some date/time value: [2007-10-11T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=9624,
							collected_at=Some date/time value: [2007-10-12T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=1575,
							collected_at=Some date/time value: [2007-10-13T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=5099,
							collected_at=Some date/time value: [2007-10-14T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=9922,
							collected_at=Some date/time value: [2007-10-15T18:00]
						)
					],
					collect_column_statistics_job_template=StatisticsCollectorSearchFilters(
						column_names=[
							'sample_column'
						],
						connection='sample_connection',
						full_table_name='sample_schema.sample_table',
						enabled=True
					),
					can_collect_statistics=True
				),
				ColumnStatisticsModel(
					connection_name='sample_connection',
					table=PhysicalTableName(
						schema_name='sample_schema',
						table_name='sample_table'
					),
					column_name='sample_column_1',
					disabled=False,
					has_any_configured_checks=True,
					type_snapshot=ColumnTypeSnapshotSpec(
						column_type='string',
						nullable=False,
						length=256
					),
					statistics=[
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=4372,
							collected_at=Some date/time value: [2007-10-11T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=9624,
							collected_at=Some date/time value: [2007-10-12T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=1575,
							collected_at=Some date/time value: [2007-10-13T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=5099,
							collected_at=Some date/time value: [2007-10-14T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=9922,
							collected_at=Some date/time value: [2007-10-15T18:00]
						)
					],
					collect_column_statistics_job_template=StatisticsCollectorSearchFilters(
						column_names=[
							'sample_column'
						],
						connection='sample_connection',
						full_table_name='sample_schema.sample_table',
						enabled=True
					),
					can_collect_statistics=True
				)
			],
			collect_column_statistics_job_template=StatisticsCollectorSearchFilters(
				column_names=[
					'sample_column'
				],
				collector_category='sample_category',
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			can_collect_statistics=True
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import get_columns_statistics
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_columns_statistics.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableColumnsStatisticsModel(
			connection_name='sample_connection',
			table=PhysicalTableName(
				schema_name='sample_schema',
				table_name='sample_table'
			),
			column_statistics=[
				ColumnStatisticsModel(
					connection_name='sample_connection',
					table=PhysicalTableName(
						schema_name='sample_schema',
						table_name='sample_table'
					),
					column_name='sample_column',
					disabled=False,
					has_any_configured_checks=True,
					type_snapshot=ColumnTypeSnapshotSpec(
						column_type='string',
						nullable=False,
						length=256
					),
					statistics=[
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=4372,
							collected_at=Some date/time value: [2007-10-11T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=9624,
							collected_at=Some date/time value: [2007-10-12T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=1575,
							collected_at=Some date/time value: [2007-10-13T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=5099,
							collected_at=Some date/time value: [2007-10-14T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=9922,
							collected_at=Some date/time value: [2007-10-15T18:00]
						)
					],
					collect_column_statistics_job_template=StatisticsCollectorSearchFilters(
						column_names=[
							'sample_column'
						],
						connection='sample_connection',
						full_table_name='sample_schema.sample_table',
						enabled=True
					),
					can_collect_statistics=True
				),
				ColumnStatisticsModel(
					connection_name='sample_connection',
					table=PhysicalTableName(
						schema_name='sample_schema',
						table_name='sample_table'
					),
					column_name='sample_column_1',
					disabled=False,
					has_any_configured_checks=True,
					type_snapshot=ColumnTypeSnapshotSpec(
						column_type='string',
						nullable=False,
						length=256
					),
					statistics=[
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=4372,
							collected_at=Some date/time value: [2007-10-11T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=9624,
							collected_at=Some date/time value: [2007-10-12T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=1575,
							collected_at=Some date/time value: [2007-10-13T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=5099,
							collected_at=Some date/time value: [2007-10-14T18:00]
						),
						StatisticsMetricModel(
							category='sample_category',
							collector='sample_collector',
							sensor_name='table/volume/row_count',
							result_data_type=StatisticsResultDataType.INTEGER,
							result=9922,
							collected_at=Some date/time value: [2007-10-15T18:00]
						)
					],
					collect_column_statistics_job_template=StatisticsCollectorSearchFilters(
						column_names=[
							'sample_column'
						],
						connection='sample_connection',
						full_table_name='sample_schema.sample_table',
						enabled=True
					),
					can_collect_statistics=True
				)
			],
			collect_column_statistics_job_template=StatisticsCollectorSearchFilters(
				column_names=[
					'sample_column'
				],
				collector_category='sample_category',
				connection='sample_connection',
				full_table_name='sample_schema.sample_table',
				enabled=True
			),
			can_collect_statistics=True
		)
        ```
    
    
    



___
## update_column
Updates an existing column specification, changing all the fields (even the column level data quality checks).

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Column specification|*[ColumnSpec](../../reference/yaml/TableYaml.md#columnspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"type_snapshot\":{\"column_type\":\"string\",\"nullable\":false,\"length\":256},\"profiling_checks\":{\"nulls\":{\"profile_nulls_count\":{\"error\":{\"max_count\":0}}}}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                ColumnSpec, \
	                                ColumnTypeSnapshotSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ColumnSpec(
		disabled=False,
		type_snapshot=ColumnTypeSnapshotSpec(
			column_type='string',
			nullable=False,
			length=256
		),
		id=False,
		profiling_checks=ColumnProfilingCheckCategoriesSpec(
			nulls=ColumnNullsProfilingChecksSpec(
				profile_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonProfilingChecksSpecMap()
		)
	)
	
	call_result = update_column.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                ColumnSpec, \
	                                ColumnTypeSnapshotSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ColumnSpec(
		disabled=False,
		type_snapshot=ColumnTypeSnapshotSpec(
			column_type='string',
			nullable=False,
			length=256
		),
		id=False,
		profiling_checks=ColumnProfilingCheckCategoriesSpec(
			nulls=ColumnNullsProfilingChecksSpec(
				profile_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonProfilingChecksSpecMap()
		)
	)
	
	call_result = await update_column.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                ColumnSpec, \
	                                ColumnTypeSnapshotSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ColumnSpec(
		disabled=False,
		type_snapshot=ColumnTypeSnapshotSpec(
			column_type='string',
			nullable=False,
			length=256
		),
		id=False,
		profiling_checks=ColumnProfilingCheckCategoriesSpec(
			nulls=ColumnNullsProfilingChecksSpec(
				profile_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonProfilingChecksSpecMap()
		)
	)
	
	call_result = update_column.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                ColumnSpec, \
	                                ColumnTypeSnapshotSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ColumnSpec(
		disabled=False,
		type_snapshot=ColumnTypeSnapshotSpec(
			column_type='string',
			nullable=False,
			length=256
		),
		id=False,
		profiling_checks=ColumnProfilingCheckCategoriesSpec(
			nulls=ColumnNullsProfilingChecksSpec(
				profile_nulls_count=ColumnNullsCountCheckSpec(
					parameters=ColumnNullsNullsCountSensorParametersSpec(),
					error=MaxCountRule0ErrorParametersSpec(max_count=0),
					disabled=False,
					exclude_from_kpi=False,
					include_in_sla=False
				)
			),
			comparisons=ColumnComparisonProfilingChecksSpecMap()
		)
	)
	
	call_result = await update_column.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_column_basic
Updates an existing column, changing only the basic information like the expected data type (the data type snapshot).

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_basic.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/basic
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Basic column information to store|*[ColumnListModel](../models/common.md#columnlistmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/basic^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection_name\":\"sample_connection\",\"table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"column_name\":\"sample_column\",\"has_any_configured_checks\":true,\"has_any_configured_profiling_checks\":true,\"type_snapshot\":{\"column_type\":\"string\",\"nullable\":false,\"length\":256},\"can_edit\":false,\"can_collect_statistics\":true,\"can_run_checks\":true,\"can_delete_data\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_basic
	from dqops.client.models import ColumnListModel, \
	                                ColumnTypeSnapshotSpec, \
	                                PhysicalTableName
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ColumnListModel(
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
	
	call_result = update_column_basic.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_basic
	from dqops.client.models import ColumnListModel, \
	                                ColumnTypeSnapshotSpec, \
	                                PhysicalTableName
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ColumnListModel(
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
	
	call_result = await update_column_basic.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_basic
	from dqops.client.models import ColumnListModel, \
	                                ColumnTypeSnapshotSpec, \
	                                PhysicalTableName
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ColumnListModel(
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
	
	call_result = update_column_basic.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_basic
	from dqops.client.models import ColumnListModel, \
	                                ColumnTypeSnapshotSpec, \
	                                PhysicalTableName
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ColumnListModel(
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
	
	call_result = await update_column_basic.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_column_comments
Updates the list of comments assigned to a column.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_comments.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/comments
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|List of comments to stored (replaced) on the column or an empty object to clear the list of assigned comments on the column|*List[[CommentSpec](../models/common.md#commentspec)]*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/comments^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"[{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"},{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"},{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"}]"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_comments
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
	
	call_result = update_column_comments.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_comments
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
	
	call_result = await update_column_comments.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_comments
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
	
	call_result = update_column_comments.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_comments
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
	
	call_result = await update_column_comments.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_column_labels
Updates the list of labels assigned to a column.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_labels.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/labels
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|List of labels to stored (replaced) on the column or an empty object to clear the list of assigned labels on the column|*List[string]*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/labels^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"[\"sampleString_1\",\"sampleString_2\",\"sampleString_3\"]"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_labels
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = [
		'sampleString_1',
		'sampleString_2',
		'sampleString_3'
	]
	
	call_result = update_column_labels.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_labels
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = [
		'sampleString_1',
		'sampleString_2',
		'sampleString_3'
	]
	
	call_result = await update_column_labels.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_labels
	
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
	
	call_result = update_column_labels.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_labels
	
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
	
	call_result = await update_column_labels.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_column_monitoring_checks_daily
Updates configuration of daily column level data quality monitoring on a column.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_monitoring_checks_daily.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/daily
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of daily column level data quality monitoring to configure on a column or an empty object to clear the list of assigned daily data quality monitoring on the column|*[ColumnDailyMonitoringCheckCategoriesSpec](../models/columns.md#columndailymonitoringcheckcategoriesspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"nulls\":{\"daily_nulls_count\":{\"error\":{\"max_count\":0}}}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_monitoring_checks_daily
	from dqops.client.models import ColumnComparisonDailyMonitoringChecksSpecMap, \
	                                ColumnDailyMonitoringCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsDailyMonitoringChecksSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ColumnDailyMonitoringCheckCategoriesSpec(
		nulls=ColumnNullsDailyMonitoringChecksSpec(
			daily_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
	)
	
	call_result = update_column_monitoring_checks_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_monitoring_checks_daily
	from dqops.client.models import ColumnComparisonDailyMonitoringChecksSpecMap, \
	                                ColumnDailyMonitoringCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsDailyMonitoringChecksSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ColumnDailyMonitoringCheckCategoriesSpec(
		nulls=ColumnNullsDailyMonitoringChecksSpec(
			daily_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
	)
	
	call_result = await update_column_monitoring_checks_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_monitoring_checks_daily
	from dqops.client.models import ColumnComparisonDailyMonitoringChecksSpecMap, \
	                                ColumnDailyMonitoringCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsDailyMonitoringChecksSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ColumnDailyMonitoringCheckCategoriesSpec(
		nulls=ColumnNullsDailyMonitoringChecksSpec(
			daily_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
	)
	
	call_result = update_column_monitoring_checks_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_monitoring_checks_daily
	from dqops.client.models import ColumnComparisonDailyMonitoringChecksSpecMap, \
	                                ColumnDailyMonitoringCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsDailyMonitoringChecksSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ColumnDailyMonitoringCheckCategoriesSpec(
		nulls=ColumnNullsDailyMonitoringChecksSpec(
			daily_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
	)
	
	call_result = await update_column_monitoring_checks_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_column_monitoring_checks_model
Updates configuration of column level data quality monitoring on a column, for a given time scale, from a UI friendly model.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_monitoring_checks_model.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/model
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality monitoring configuration|*[CheckContainerModel](../models/common.md#checkcontainermodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily/model^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/table/volume/row_count\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_error_sampling\":false,\"supports_grouping\":false,\"default_severity\":\"error\",\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_monitoring_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                FieldModel, \
	                                QualityCategoryModel, \
	                                RuleSeverityLevel
	
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=RuleSeverityLevel.ERROR,
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
	
	call_result = update_column_monitoring_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_monitoring_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                FieldModel, \
	                                QualityCategoryModel, \
	                                RuleSeverityLevel
	
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=RuleSeverityLevel.ERROR,
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
	
	call_result = await update_column_monitoring_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_monitoring_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                FieldModel, \
	                                QualityCategoryModel, \
	                                RuleSeverityLevel
	
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=RuleSeverityLevel.ERROR,
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
	
	call_result = update_column_monitoring_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_monitoring_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                FieldModel, \
	                                QualityCategoryModel, \
	                                RuleSeverityLevel
	
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=RuleSeverityLevel.ERROR,
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
	
	call_result = await update_column_monitoring_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_column_monitoring_checks_monthly
Updates configuration of monthly column level data quality monitoring checks on a column.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_monitoring_checks_monthly.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/monthly
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of monthly column level data quality monitoring to configure on a column or an empty object to clear the list of assigned monthly data quality monitoring on the column|*[ColumnMonthlyMonitoringCheckCategoriesSpec](../models/columns.md#columnmonthlymonitoringcheckcategoriesspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"nulls\":{\"monthly_nulls_count\":{\"error\":{\"max_count\":0}}}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_monitoring_checks_monthly
	from dqops.client.models import ColumnComparisonMonthlyMonitoringChecksSpecMap, \
	                                ColumnMonthlyMonitoringCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsMonthlyMonitoringChecksSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ColumnMonthlyMonitoringCheckCategoriesSpec(
		nulls=ColumnNullsMonthlyMonitoringChecksSpec(
			monthly_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonMonthlyMonitoringChecksSpecMap()
	)
	
	call_result = update_column_monitoring_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_monitoring_checks_monthly
	from dqops.client.models import ColumnComparisonMonthlyMonitoringChecksSpecMap, \
	                                ColumnMonthlyMonitoringCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsMonthlyMonitoringChecksSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ColumnMonthlyMonitoringCheckCategoriesSpec(
		nulls=ColumnNullsMonthlyMonitoringChecksSpec(
			monthly_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonMonthlyMonitoringChecksSpecMap()
	)
	
	call_result = await update_column_monitoring_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_monitoring_checks_monthly
	from dqops.client.models import ColumnComparisonMonthlyMonitoringChecksSpecMap, \
	                                ColumnMonthlyMonitoringCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsMonthlyMonitoringChecksSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ColumnMonthlyMonitoringCheckCategoriesSpec(
		nulls=ColumnNullsMonthlyMonitoringChecksSpec(
			monthly_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonMonthlyMonitoringChecksSpecMap()
	)
	
	call_result = update_column_monitoring_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_monitoring_checks_monthly
	from dqops.client.models import ColumnComparisonMonthlyMonitoringChecksSpecMap, \
	                                ColumnMonthlyMonitoringCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsMonthlyMonitoringChecksSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ColumnMonthlyMonitoringCheckCategoriesSpec(
		nulls=ColumnNullsMonthlyMonitoringChecksSpec(
			monthly_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonMonthlyMonitoringChecksSpecMap()
	)
	
	call_result = await update_column_monitoring_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_column_partitioned_checks_daily
Updates configuration of daily column level data quality partitioned checks on a column.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_partitioned_checks_daily.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/daily
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of daily column level data quality partitioned checks to configure on a column or an empty object to clear the list of assigned data quality partitioned checks on the column|*[ColumnDailyPartitionedCheckCategoriesSpec](../models/columns.md#columndailypartitionedcheckcategoriesspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"nulls\":{\"daily_partition_nulls_count\":{\"error\":{\"max_count\":0}}}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_partitioned_checks_daily
	from dqops.client.models import ColumnComparisonDailyPartitionedChecksSpecMap, \
	                                ColumnDailyPartitionedCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsDailyPartitionedChecksSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ColumnDailyPartitionedCheckCategoriesSpec(
		nulls=ColumnNullsDailyPartitionedChecksSpec(
			daily_partition_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonDailyPartitionedChecksSpecMap()
	)
	
	call_result = update_column_partitioned_checks_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_partitioned_checks_daily
	from dqops.client.models import ColumnComparisonDailyPartitionedChecksSpecMap, \
	                                ColumnDailyPartitionedCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsDailyPartitionedChecksSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ColumnDailyPartitionedCheckCategoriesSpec(
		nulls=ColumnNullsDailyPartitionedChecksSpec(
			daily_partition_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonDailyPartitionedChecksSpecMap()
	)
	
	call_result = await update_column_partitioned_checks_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_partitioned_checks_daily
	from dqops.client.models import ColumnComparisonDailyPartitionedChecksSpecMap, \
	                                ColumnDailyPartitionedCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsDailyPartitionedChecksSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ColumnDailyPartitionedCheckCategoriesSpec(
		nulls=ColumnNullsDailyPartitionedChecksSpec(
			daily_partition_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonDailyPartitionedChecksSpecMap()
	)
	
	call_result = update_column_partitioned_checks_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_partitioned_checks_daily
	from dqops.client.models import ColumnComparisonDailyPartitionedChecksSpecMap, \
	                                ColumnDailyPartitionedCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsDailyPartitionedChecksSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ColumnDailyPartitionedCheckCategoriesSpec(
		nulls=ColumnNullsDailyPartitionedChecksSpec(
			daily_partition_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonDailyPartitionedChecksSpecMap()
	)
	
	call_result = await update_column_partitioned_checks_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_column_partitioned_checks_model
Updates configuration of column level data quality partitioned checks on a column, for a given time scale, from a UI friendly model.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_partitioned_checks_model.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality partitioned checks configuration|*[CheckContainerModel](../models/common.md#checkcontainermodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily/model^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/table/volume/row_count\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_error_sampling\":false,\"supports_grouping\":false,\"default_severity\":\"error\",\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_partitioned_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                FieldModel, \
	                                QualityCategoryModel, \
	                                RuleSeverityLevel
	
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=RuleSeverityLevel.ERROR,
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
	
	call_result = update_column_partitioned_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_partitioned_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                FieldModel, \
	                                QualityCategoryModel, \
	                                RuleSeverityLevel
	
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=RuleSeverityLevel.ERROR,
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
	
	call_result = await update_column_partitioned_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_partitioned_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                FieldModel, \
	                                QualityCategoryModel, \
	                                RuleSeverityLevel
	
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=RuleSeverityLevel.ERROR,
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
	
	call_result = update_column_partitioned_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_partitioned_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                FieldModel, \
	                                QualityCategoryModel, \
	                                RuleSeverityLevel
	
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=RuleSeverityLevel.ERROR,
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
	
	call_result = await update_column_partitioned_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_column_partitioned_checks_monthly
Updates configuration of monthly column level data quality partitioned checks on a column.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_partitioned_checks_monthly.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/monthly
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of monthly column level data quality partitioned checks to configure on a column or an empty object to clear the list of assigned data quality partitioned checks on the column|*[ColumnMonthlyPartitionedCheckCategoriesSpec](../models/columns.md#columnmonthlypartitionedcheckcategoriesspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"nulls\":{\"monthly_partition_nulls_count\":{\"error\":{\"max_count\":0}}}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_partitioned_checks_monthly
	from dqops.client.models import ColumnComparisonMonthlyPartitionedChecksSpecMap, \
	                                ColumnMonthlyPartitionedCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsMonthlyPartitionedChecksSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ColumnMonthlyPartitionedCheckCategoriesSpec(
		nulls=ColumnNullsMonthlyPartitionedChecksSpec(
			monthly_partition_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonMonthlyPartitionedChecksSpecMap()
	)
	
	call_result = update_column_partitioned_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_partitioned_checks_monthly
	from dqops.client.models import ColumnComparisonMonthlyPartitionedChecksSpecMap, \
	                                ColumnMonthlyPartitionedCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsMonthlyPartitionedChecksSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ColumnMonthlyPartitionedCheckCategoriesSpec(
		nulls=ColumnNullsMonthlyPartitionedChecksSpec(
			monthly_partition_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonMonthlyPartitionedChecksSpecMap()
	)
	
	call_result = await update_column_partitioned_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_partitioned_checks_monthly
	from dqops.client.models import ColumnComparisonMonthlyPartitionedChecksSpecMap, \
	                                ColumnMonthlyPartitionedCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsMonthlyPartitionedChecksSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ColumnMonthlyPartitionedCheckCategoriesSpec(
		nulls=ColumnNullsMonthlyPartitionedChecksSpec(
			monthly_partition_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonMonthlyPartitionedChecksSpecMap()
	)
	
	call_result = update_column_partitioned_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_partitioned_checks_monthly
	from dqops.client.models import ColumnComparisonMonthlyPartitionedChecksSpecMap, \
	                                ColumnMonthlyPartitionedCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsMonthlyPartitionedChecksSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ColumnMonthlyPartitionedCheckCategoriesSpec(
		nulls=ColumnNullsMonthlyPartitionedChecksSpec(
			monthly_partition_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonMonthlyPartitionedChecksSpecMap()
	)
	
	call_result = await update_column_partitioned_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_column_profiling_checks
Updates configuration of column level data quality profiling checks on a column.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_profiling_checks.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of column level data quality profiling checks to configure on a column or an empty object to clear the list of assigned data quality profiling checks on the column|*[ColumnProfilingCheckCategoriesSpec](../models/columns.md#columnprofilingcheckcategoriesspec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"nulls\":{\"profile_nulls_count\":{\"error\":{\"max_count\":0}}}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_profiling_checks
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ColumnProfilingCheckCategoriesSpec(
		nulls=ColumnNullsProfilingChecksSpec(
			profile_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonProfilingChecksSpecMap()
	)
	
	call_result = update_column_profiling_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_profiling_checks
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = ColumnProfilingCheckCategoriesSpec(
		nulls=ColumnNullsProfilingChecksSpec(
			profile_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonProfilingChecksSpecMap()
	)
	
	call_result = await update_column_profiling_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_profiling_checks
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ColumnProfilingCheckCategoriesSpec(
		nulls=ColumnNullsProfilingChecksSpec(
			profile_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonProfilingChecksSpecMap()
	)
	
	call_result = update_column_profiling_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_profiling_checks
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                MaxCountRule0ErrorParametersSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = ColumnProfilingCheckCategoriesSpec(
		nulls=ColumnNullsProfilingChecksSpec(
			profile_nulls_count=ColumnNullsCountCheckSpec(
				parameters=ColumnNullsNullsCountSensorParametersSpec(),
				error=MaxCountRule0ErrorParametersSpec(max_count=0),
				disabled=False,
				exclude_from_kpi=False,
				include_in_sla=False
			)
		),
		comparisons=ColumnComparisonProfilingChecksSpecMap()
	)
	
	call_result = await update_column_profiling_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_column_profiling_checks_model
Updates configuration of column level data quality profiling checks on a column from a UI friendly model.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_profiling_checks_model.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/model
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality profiling checks configuration|*[CheckContainerModel](../models/common.md#checkcontainermodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/model^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/table/volume/row_count\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_error_sampling\":false,\"supports_grouping\":false,\"default_severity\":\"error\",\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_profiling_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel, \
	                                RuleSeverityLevel
	
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=RuleSeverityLevel.ERROR,
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
	
	call_result = update_column_profiling_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_profiling_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel, \
	                                RuleSeverityLevel
	
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=RuleSeverityLevel.ERROR,
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
	
	call_result = await update_column_profiling_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_profiling_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel, \
	                                RuleSeverityLevel
	
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=RuleSeverityLevel.ERROR,
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
	
	call_result = update_column_profiling_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_profiling_checks_model
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel, \
	                                RuleSeverityLevel
	
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=RuleSeverityLevel.ERROR,
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
	
	call_result = await update_column_profiling_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



