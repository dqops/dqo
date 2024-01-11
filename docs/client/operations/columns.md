# DQOps REST API columns operations
Manages columns inside a table


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
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Column specification|[ColumnSpec](../../reference/yaml/TableYaml.md#columnspec)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"type_snapshot\":{\"column_type\":\"string\",\"nullable\":false,\"length\":256},\"profiling_checks\":{\"nulls\":{\"profile_nulls_count\":{\"error\":{\"max_count\":0}}}}}"
	
    ```

=== "Python sync client"

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
	
	create_column.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

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
	
	async_result = create_column.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
	
	create_column.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

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
	
	async_result = create_column.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
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
|[dqo_queue_job_id](../models/common.md#dqoqueuejobid)||[DqoQueueJobId](../models/common.md#dqoqueuejobid)|




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
    curl -X DELETE http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import delete_column
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	delete_column.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import delete_column
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	async_result = delete_column.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import delete_column
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	delete_column.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import delete_column
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	async_result = delete_column.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
|[column_model](../models/columns.md#columnmodel)||[ColumnModel](../models/columns.md#columnmodel)|




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
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_column.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
|[column_list_model](../models/columns.md#columnlistmodel)||[ColumnListModel](../models/columns.md#columnlistmodel)|




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
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/basic^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_basic
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_basic.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_basic
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_basic.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_basic
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_column_basic.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_basic
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_basic.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
|comment_spec||List[[CommentSpec](../models/common.md#commentspec)]|




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
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/comments^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_comments
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_comments.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_comments
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_comments.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_comments
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_column_comments.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_comments
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_comments.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
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
|string||List[string]|




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
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/labels^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_labels
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_labels.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_labels
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_labels.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_labels
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_column_labels.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_labels
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_labels.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
    [ "sampleString_1", "sampleString_2", "sampleString_3" ]
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
|[check_container_list_model](../models/common.md#checkcontainerlistmodel)||[CheckContainerListModel](../models/common.md#checkcontainerlistmodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../models/common.md#checktimescale)|Time scale|[CheckTimeScale](../models/common.md#checktimescale)|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily/model/basic^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_monitoring_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_monitoring_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
	
	get_column_monitoring_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

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
	
	async_result = get_column_monitoring_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
|[column_daily_monitoring_check_categories_spec](../models/columns.md#columndailymonitoringcheckcategoriesspec)||[ColumnDailyMonitoringCheckCategoriesSpec](../models/columns.md#columndailymonitoringcheckcategoriesspec)|




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
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_daily
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_monitoring_checks_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_daily
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_monitoring_checks_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_daily
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_column_monitoring_checks_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_daily
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_monitoring_checks_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
|[check_container_model](../models/common.md#checkcontainermodel)||[CheckContainerModel](../models/common.md#checkcontainermodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../models/common.md#checktimescale)|Time scale|[CheckTimeScale](../models/common.md#checktimescale)|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily/model^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_monitoring_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_monitoring_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
	
	get_column_monitoring_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

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
	
	async_result = get_column_monitoring_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
|[check_container_model](../models/common.md#checkcontainermodel)||[CheckContainerModel](../models/common.md#checkcontainermodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../models/common.md#checktimescale)|Time scale|[CheckTimeScale](../models/common.md#checktimescale)|:material-check-bold:|
|check_category|Check category|string|:material-check-bold:|
|check_name|Check name|string|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily/model/filter/sample_category/sample_check^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_monitoring_checks_model_filter.sync(
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

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_monitoring_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
	
	get_column_monitoring_checks_model_filter.sync(
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

=== "Python auth async client"

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
	
	async_result = get_column_monitoring_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
|[column_monthly_monitoring_check_categories_spec](../models/columns.md#columnmonthlymonitoringcheckcategoriesspec)||[ColumnMonthlyMonitoringCheckCategoriesSpec](../models/columns.md#columnmonthlymonitoringcheckcategoriesspec)|




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
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/monthly^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_monthly
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_monitoring_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_monthly
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_monitoring_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_monthly
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_column_monitoring_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_monitoring_checks_monthly
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_monitoring_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
|[check_container_list_model](../models/common.md#checkcontainerlistmodel)||[CheckContainerListModel](../models/common.md#checkcontainerlistmodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../models/common.md#checktimescale)|Time scale|[CheckTimeScale](../models/common.md#checktimescale)|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily/model/basic^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_partitioned_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_basic_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_partitioned_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
	
	get_column_partitioned_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

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
	
	async_result = get_column_partitioned_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
|[column_daily_partitioned_check_categories_spec](../models/columns.md#columndailypartitionedcheckcategoriesspec)||[ColumnDailyPartitionedCheckCategoriesSpec](../models/columns.md#columndailypartitionedcheckcategoriesspec)|




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
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_daily
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_partitioned_checks_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_daily
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_partitioned_checks_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_daily
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_column_partitioned_checks_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_daily
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_partitioned_checks_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
|[check_container_model](../models/common.md#checkcontainermodel)||[CheckContainerModel](../models/common.md#checkcontainermodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../models/common.md#checktimescale)|Time scale|[CheckTimeScale](../models/common.md#checktimescale)|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily/model^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_partitioned_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_model
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_partitioned_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
	
	get_column_partitioned_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

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
	
	async_result = get_column_partitioned_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
|[check_container_model](../models/common.md#checkcontainermodel)||[CheckContainerModel](../models/common.md#checkcontainermodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../models/common.md#checktimescale)|Time scale|[CheckTimeScale](../models/common.md#checktimescale)|:material-check-bold:|
|check_category|Check category|string|:material-check-bold:|
|check_name|Check name|string|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily/model/filter/sample_category/sample_check^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_partitioned_checks_model_filter.sync(
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

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_model_filter
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_partitioned_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
	
	get_column_partitioned_checks_model_filter.sync(
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

=== "Python auth async client"

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
	
	async_result = get_column_partitioned_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
|[column_monthly_partitioned_check_categories_spec](../models/columns.md#columnmonthlypartitionedcheckcategoriesspec)||[ColumnMonthlyPartitionedCheckCategoriesSpec](../models/columns.md#columnmonthlypartitionedcheckcategoriesspec)|




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
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/monthly^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_monthly
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_partitioned_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_monthly
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_partitioned_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_monthly
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_column_partitioned_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_partitioned_checks_monthly
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_partitioned_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
|[column_profiling_check_categories_spec](../models/columns.md#columnprofilingcheckcategoriesspec)||[ColumnProfilingCheckCategoriesSpec](../models/columns.md#columnprofilingcheckcategoriesspec)|




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
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_profiling_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_profiling_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_column_profiling_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_profiling_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
|[check_container_list_model](../models/common.md#checkcontainerlistmodel)||[CheckContainerListModel](../models/common.md#checkcontainerlistmodel)|




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
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/model/basic^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks_basic_model
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_profiling_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks_basic_model
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_profiling_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks_basic_model
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_column_profiling_checks_basic_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks_basic_model
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_profiling_checks_basic_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
|[check_container_model](../models/common.md#checkcontainermodel)||[CheckContainerModel](../models/common.md#checkcontainermodel)|




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
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/model^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks_model
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_profiling_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks_model
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_profiling_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks_model
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_column_profiling_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks_model
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_profiling_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
|[check_container_model](../models/common.md#checkcontainermodel)||[CheckContainerModel](../models/common.md#checkcontainermodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|check_category|Check category|string|:material-check-bold:|
|check_name|Check name|string|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/model/filter/sample_category/sample_check^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks_model_filter
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_profiling_checks_model_filter.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks_model_filter
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_profiling_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks_model_filter
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_column_profiling_checks_model_filter.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_profiling_checks_model_filter
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_profiling_checks_model_filter.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    'sample_category',
	    'sample_check',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
|[column_statistics_model](../models/columns.md#columnstatisticsmodel)||[ColumnStatisticsModel](../models/columns.md#columnstatisticsmodel)|




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
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/statistics^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_statistics
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_statistics.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_statistics
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_statistics.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_statistics
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_column_statistics.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_column_statistics
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_statistics.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
	    "resultDataType" : "integer",
	    "result" : 4372,
	    "collectedAt" : "2007-10-11T18:00:00"
	  }, {
	    "category" : "sample_category",
	    "collector" : "sample_collector",
	    "resultDataType" : "integer",
	    "result" : 9624,
	    "collectedAt" : "2007-10-12T18:00:00"
	  }, {
	    "category" : "sample_category",
	    "collector" : "sample_collector",
	    "resultDataType" : "integer",
	    "result" : 1575,
	    "collectedAt" : "2007-10-13T18:00:00"
	  }, {
	    "category" : "sample_category",
	    "collector" : "sample_collector",
	    "resultDataType" : "integer",
	    "result" : 5099,
	    "collectedAt" : "2007-10-14T18:00:00"
	  }, {
	    "category" : "sample_category",
	    "collector" : "sample_collector",
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
|column_list_model||List[[ColumnListModel](../models/columns.md#columnlistmodel)]|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_columns
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_columns.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_columns
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_columns.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_columns
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_columns.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_columns
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_columns.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
|[table_columns_statistics_model](../models/columns.md#tablecolumnsstatisticsmodel)||[TableColumnsStatisticsModel](../models/columns.md#tablecolumnsstatisticsmodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/statistics^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_columns_statistics
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_columns_statistics.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_columns_statistics
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_columns_statistics.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_columns_statistics
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_columns_statistics.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import get_columns_statistics
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_columns_statistics.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
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
	      "resultDataType" : "integer",
	      "result" : 4372,
	      "collectedAt" : "2007-10-11T18:00:00"
	    }, {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
	      "resultDataType" : "integer",
	      "result" : 9624,
	      "collectedAt" : "2007-10-12T18:00:00"
	    }, {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
	      "resultDataType" : "integer",
	      "result" : 1575,
	      "collectedAt" : "2007-10-13T18:00:00"
	    }, {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
	      "resultDataType" : "integer",
	      "result" : 5099,
	      "collectedAt" : "2007-10-14T18:00:00"
	    }, {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
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
	      "resultDataType" : "integer",
	      "result" : 4372,
	      "collectedAt" : "2007-10-11T18:00:00"
	    }, {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
	      "resultDataType" : "integer",
	      "result" : 9624,
	      "collectedAt" : "2007-10-12T18:00:00"
	    }, {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
	      "resultDataType" : "integer",
	      "result" : 1575,
	      "collectedAt" : "2007-10-13T18:00:00"
	    }, {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
	      "resultDataType" : "integer",
	      "result" : 5099,
	      "collectedAt" : "2007-10-14T18:00:00"
	    }, {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
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
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Column specification|[ColumnSpec](../../reference/yaml/TableYaml.md#columnspec)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"type_snapshot\":{\"column_type\":\"string\",\"nullable\":false,\"length\":256},\"profiling_checks\":{\"nulls\":{\"profile_nulls_count\":{\"error\":{\"max_count\":0}}}}}"
	
    ```

=== "Python sync client"

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
	
	update_column.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

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
	
	async_result = update_column.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
	
	update_column.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

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
	
	async_result = update_column.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
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
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Basic column information to store|[ColumnListModel](../models/columns.md#columnlistmodel)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/basic^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection_name\":\"sample_connection\",\"table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"column_name\":\"sample_column\",\"has_any_configured_checks\":true,\"has_any_configured_profiling_checks\":true,\"type_snapshot\":{\"column_type\":\"string\",\"nullable\":false,\"length\":256},\"can_edit\":false,\"can_collect_statistics\":true,\"can_run_checks\":true,\"can_delete_data\":true}"
	
    ```

=== "Python sync client"

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
	
	update_column_basic.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

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
	
	async_result = update_column_basic.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
	
	update_column_basic.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

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
	
	async_result = update_column_basic.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
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
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|List of comments to stored (replaced) on the column or an empty object to clear the list of assigned comments on the column|List[[CommentSpec](../models/common.md#commentspec)]| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/comments^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"[{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"},{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"},{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"}]"
	
    ```

=== "Python sync client"

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
	
	update_column_comments.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

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
	
	async_result = update_column_comments.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
	
	update_column_comments.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

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
	
	async_result = update_column_comments.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
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
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|List of labels to stored (replaced) on the column or an empty object to clear the list of assigned labels on the column|List[string]| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/labels^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"[\"sampleString_1\",\"sampleString_2\",\"sampleString_3\"]"
	
    ```

=== "Python sync client"

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
	
	update_column_labels.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

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
	
	async_result = update_column_labels.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
	
	update_column_labels.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

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
	
	async_result = update_column_labels.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
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
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of daily column level data quality monitoring to configure on a column or an empty object to clear the list of assigned daily data quality monitoring on the column|[ColumnDailyMonitoringCheckCategoriesSpec](../models/columns.md#columndailymonitoringcheckcategoriesspec)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"nulls\":{\"daily_nulls_count\":{\"error\":{\"max_count\":0}}}}"
	
    ```

=== "Python sync client"

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
	
	update_column_monitoring_checks_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

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
	
	async_result = update_column_monitoring_checks_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
	
	update_column_monitoring_checks_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

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
	
	async_result = update_column_monitoring_checks_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
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
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../models/common.md#checktimescale)|Time scale|[CheckTimeScale](../models/common.md#checktimescale)|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality monitoring configuration|[CheckContainerModel](../models/common.md#checkcontainermodel)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily/model^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_monitoring_checks_model
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
						supports_grouping=False,
						standard=False,
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
	
	update_column_monitoring_checks_model.sync(
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

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_monitoring_checks_model
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
						supports_grouping=False,
						standard=False,
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
	
	async_result = update_column_monitoring_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_monitoring_checks_model
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
						supports_grouping=False,
						standard=False,
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
	
	update_column_monitoring_checks_model.sync(
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

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_monitoring_checks_model
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
						supports_grouping=False,
						standard=False,
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
	
	async_result = update_column_monitoring_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
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
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of monthly column level data quality monitoring to configure on a column or an empty object to clear the list of assigned monthly data quality monitoring on the column|[ColumnMonthlyMonitoringCheckCategoriesSpec](../models/columns.md#columnmonthlymonitoringcheckcategoriesspec)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"nulls\":{\"monthly_nulls_count\":{\"error\":{\"max_count\":0}}}}"
	
    ```

=== "Python sync client"

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
	
	update_column_monitoring_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

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
	
	async_result = update_column_monitoring_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
	
	update_column_monitoring_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

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
	
	async_result = update_column_monitoring_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
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
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of daily column level data quality partitioned checks to configure on a column or an empty object to clear the list of assigned data quality partitioned checks on the column|[ColumnDailyPartitionedCheckCategoriesSpec](../models/columns.md#columndailypartitionedcheckcategoriesspec)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"nulls\":{\"daily_partition_nulls_count\":{\"error\":{\"max_count\":0}}}}"
	
    ```

=== "Python sync client"

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
	
	update_column_partitioned_checks_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

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
	
	async_result = update_column_partitioned_checks_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
	
	update_column_partitioned_checks_daily.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

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
	
	async_result = update_column_partitioned_checks_daily.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
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
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../models/common.md#checktimescale)|Time scale|[CheckTimeScale](../models/common.md#checktimescale)|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality partitioned checks configuration|[CheckContainerModel](../models/common.md#checkcontainermodel)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily/model^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_partitioned_checks_model
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
						supports_grouping=False,
						standard=False,
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
	
	update_column_partitioned_checks_model.sync(
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

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_partitioned_checks_model
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
						supports_grouping=False,
						standard=False,
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
	
	async_result = update_column_partitioned_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_partitioned_checks_model
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
						supports_grouping=False,
						standard=False,
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
	
	update_column_partitioned_checks_model.sync(
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

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_partitioned_checks_model
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
						supports_grouping=False,
						standard=False,
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
	
	async_result = update_column_partitioned_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
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
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of monthly column level data quality partitioned checks to configure on a column or an empty object to clear the list of assigned data quality partitioned checks on the column|[ColumnMonthlyPartitionedCheckCategoriesSpec](../models/columns.md#columnmonthlypartitionedcheckcategoriesspec)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"nulls\":{\"monthly_partition_nulls_count\":{\"error\":{\"max_count\":0}}}}"
	
    ```

=== "Python sync client"

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
	
	update_column_partitioned_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

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
	
	async_result = update_column_partitioned_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
	
	update_column_partitioned_checks_monthly.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

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
	
	async_result = update_column_partitioned_checks_monthly.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
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
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of column level data quality profiling checks to configure on a column or an empty object to clear the list of assigned data quality profiling checks on the column|[ColumnProfilingCheckCategoriesSpec](../models/columns.md#columnprofilingcheckcategoriesspec)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"nulls\":{\"profile_nulls_count\":{\"error\":{\"max_count\":0}}}}"
	
    ```

=== "Python sync client"

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
	
	update_column_profiling_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

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
	
	async_result = update_column_profiling_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
	
	update_column_profiling_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

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
	
	async_result = update_column_profiling_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
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
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality profiling checks configuration|[CheckContainerModel](../models/common.md#checkcontainermodel)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/model^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_profiling_checks_model
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
						supports_grouping=False,
						standard=False,
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
	
	update_column_profiling_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_profiling_checks_model
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
						supports_grouping=False,
						standard=False,
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
	
	async_result = update_column_profiling_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_profiling_checks_model
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
						supports_grouping=False,
						standard=False,
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
	
	update_column_profiling_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.columns import update_column_profiling_checks_model
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
						supports_grouping=False,
						standard=False,
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
	
	async_result = update_column_profiling_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





