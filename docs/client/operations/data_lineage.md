---
title: DQOps REST API data_lineage operations
---
# DQOps REST API data_lineage operations
Operations related to managing and inspecting table and column lineage.


___
## create_table_source_table
Creates a new source table of the table&#x27;s data lineage.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_lineage/create_table_source_table.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/lineage/sources/{sourceConnection}/schemas/{sourceSchema}/tables/{sourceTable}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`source_connection`</span>|Source connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`source_schema`</span>|Source schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`source_table`</span>|Source table name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table lineage source list model|*[TableLineageSourceSpec](../models/data_lineage.md#tablelineagesourcespec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/lineage/sources/sample_connection/schemas/sample_schema/tables/sample_table^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"source_connection\":\"\",\"source_schema\":\"\",\"source_table\":\"\"}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import create_table_source_table
	from dqops.client.models import ColumnLineageSourceSpecMap, \
	                                TableLineageSourceSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableLineageSourceSpec(
		source_connection='',
		source_schema='',
		source_table='',
		columns=ColumnLineageSourceSpecMap()
	)
	
	call_result = create_table_source_table.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.data_lineage import create_table_source_table
	from dqops.client.models import ColumnLineageSourceSpecMap, \
	                                TableLineageSourceSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableLineageSourceSpec(
		source_connection='',
		source_schema='',
		source_table='',
		columns=ColumnLineageSourceSpecMap()
	)
	
	call_result = await create_table_source_table.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.data_lineage import create_table_source_table
	from dqops.client.models import ColumnLineageSourceSpecMap, \
	                                TableLineageSourceSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableLineageSourceSpec(
		source_connection='',
		source_schema='',
		source_table='',
		columns=ColumnLineageSourceSpecMap()
	)
	
	call_result = create_table_source_table.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.data_lineage import create_table_source_table
	from dqops.client.models import ColumnLineageSourceSpecMap, \
	                                TableLineageSourceSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableLineageSourceSpec(
		source_connection='',
		source_schema='',
		source_table='',
		columns=ColumnLineageSourceSpecMap()
	)
	
	call_result = await create_table_source_table.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## delete_table_source_table
Deletes a specific data lineage source table of the given table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_lineage/delete_table_source_table.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/lineage/sources/{sourceConnection}/schemas/{sourceSchema}/tables/{sourceTable}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`source_connection`</span>|Source connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`source_schema`</span>|Source schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`source_table`</span>|Source table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X DELETE http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/lineage/sources/sample_connection/schemas/sample_schema/tables/sample_table^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import delete_table_source_table
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = delete_table_source_table.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import delete_table_source_table
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await delete_table_source_table.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import delete_table_source_table
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = delete_table_source_table.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import delete_table_source_table
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await delete_table_source_table.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    



___
## get_table_data_lineage_graph
Returns a data lineage graph around the given table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_lineage/get_table_data_lineage_graph.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/lineage/tree
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_lineage_model`](../models/data_lineage.md#tablelineagemodel)</span>||*[TableLineageModel](../models/data_lineage.md#tablelineagemodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`upstream`</span>|Optional parameter to request upstream tables. By default, upstream tables are collected unless it is disabled by passing &#x27;false&#x27;.|*boolean*| |
|<span class="no-wrap-code">`downstream`</span>|Optional parameter to request downstream tables. By default, downstream tables are collected unless it is disabled by passing &#x27;false&#x27;.|*boolean*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/lineage/tree^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "flows" : [ ]
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import get_table_data_lineage_graph
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_data_lineage_graph.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableLineageModel(
			flows=[
			
			]
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import get_table_data_lineage_graph
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_data_lineage_graph.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableLineageModel(
			flows=[
			
			]
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import get_table_data_lineage_graph
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_data_lineage_graph.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableLineageModel(
			flows=[
			
			]
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import get_table_data_lineage_graph
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_data_lineage_graph.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableLineageModel(
			flows=[
			
			]
		)
        ```
    
    
    



___
## get_table_source_table
Reads a specific data lineage source table defined on a target tale.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_lineage/get_table_source_table.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/lineage/sources/{sourceConnection}/schemas/{sourceSchema}/tables/{sourceTable}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_lineage_source_spec`](../models/data_lineage.md#tablelineagesourcespec)</span>||*[TableLineageSourceSpec](../models/data_lineage.md#tablelineagesourcespec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`source_connection`</span>|Source connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`source_schema`</span>|Source schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`source_table`</span>|Source table name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/lineage/sources/sample_connection/schemas/sample_schema/tables/sample_table^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "source_connection" : "",
		  "source_schema" : "",
		  "source_table" : ""
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import get_table_source_table
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_source_table.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableLineageSourceSpec(
			source_connection='',
			source_schema='',
			source_table='',
			columns=ColumnLineageSourceSpecMap()
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import get_table_source_table
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_source_table.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableLineageSourceSpec(
			source_connection='',
			source_schema='',
			source_table='',
			columns=ColumnLineageSourceSpecMap()
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import get_table_source_table
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_source_table.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableLineageSourceSpec(
			source_connection='',
			source_schema='',
			source_table='',
			columns=ColumnLineageSourceSpecMap()
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import get_table_source_table
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_source_table.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableLineageSourceSpec(
			source_connection='',
			source_schema='',
			source_table='',
			columns=ColumnLineageSourceSpecMap()
		)
        ```
    
    
    



___
## get_table_source_tables
Returns a list of source tables on the data lineage that are sources of the given table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_lineage/get_table_source_tables.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/lineage/sources
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`table_lineage_source_list_model`</span>||*List[[TableLineageSourceListModel](../models/data_lineage.md#tablelineagesourcelistmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`check_type`](../models/common.md#checktype)</span>|Optional parameter for the check type, when provided, returns the results for data quality dimensions for the data quality checks of that type|*[CheckType](../models/common.md#checktype)*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/lineage/sources^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "target_connection" : "datalake",
		  "target_schema" : "landing_app",
		  "target_table" : "customers_landing",
		  "source_connection" : "sourcedb",
		  "source_schema" : "app",
		  "source_table" : "t_customers",
		  "can_edit" : false
		}, {
		  "target_connection" : "datalake",
		  "target_schema" : "landing_app",
		  "target_table" : "customers_landing",
		  "source_connection" : "sourcedb",
		  "source_schema" : "app",
		  "source_table" : "t_customers",
		  "can_edit" : false
		}, {
		  "target_connection" : "datalake",
		  "target_schema" : "landing_app",
		  "target_table" : "customers_landing",
		  "source_connection" : "sourcedb",
		  "source_schema" : "app",
		  "source_table" : "t_customers",
		  "can_edit" : false
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import get_table_source_tables
	from dqops.client.models import CheckType
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_source_tables.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			TableLineageSourceListModel(
				target_connection='datalake',
				target_schema='landing_app',
				target_table='customers_landing',
				source_connection='sourcedb',
				source_schema='app',
				source_table='t_customers',
				can_edit=False
			),
			TableLineageSourceListModel(
				target_connection='datalake',
				target_schema='landing_app',
				target_table='customers_landing',
				source_connection='sourcedb',
				source_schema='app',
				source_table='t_customers',
				can_edit=False
			),
			TableLineageSourceListModel(
				target_connection='datalake',
				target_schema='landing_app',
				target_table='customers_landing',
				source_connection='sourcedb',
				source_schema='app',
				source_table='t_customers',
				can_edit=False
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import get_table_source_tables
	from dqops.client.models import CheckType
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_source_tables.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			TableLineageSourceListModel(
				target_connection='datalake',
				target_schema='landing_app',
				target_table='customers_landing',
				source_connection='sourcedb',
				source_schema='app',
				source_table='t_customers',
				can_edit=False
			),
			TableLineageSourceListModel(
				target_connection='datalake',
				target_schema='landing_app',
				target_table='customers_landing',
				source_connection='sourcedb',
				source_schema='app',
				source_table='t_customers',
				can_edit=False
			),
			TableLineageSourceListModel(
				target_connection='datalake',
				target_schema='landing_app',
				target_table='customers_landing',
				source_connection='sourcedb',
				source_schema='app',
				source_table='t_customers',
				can_edit=False
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import get_table_source_tables
	from dqops.client.models import CheckType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_source_tables.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			TableLineageSourceListModel(
				target_connection='datalake',
				target_schema='landing_app',
				target_table='customers_landing',
				source_connection='sourcedb',
				source_schema='app',
				source_table='t_customers',
				can_edit=False
			),
			TableLineageSourceListModel(
				target_connection='datalake',
				target_schema='landing_app',
				target_table='customers_landing',
				source_connection='sourcedb',
				source_schema='app',
				source_table='t_customers',
				can_edit=False
			),
			TableLineageSourceListModel(
				target_connection='datalake',
				target_schema='landing_app',
				target_table='customers_landing',
				source_connection='sourcedb',
				source_schema='app',
				source_table='t_customers',
				can_edit=False
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import get_table_source_tables
	from dqops.client.models import CheckType
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_source_tables.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			TableLineageSourceListModel(
				target_connection='datalake',
				target_schema='landing_app',
				target_table='customers_landing',
				source_connection='sourcedb',
				source_schema='app',
				source_table='t_customers',
				can_edit=False
			),
			TableLineageSourceListModel(
				target_connection='datalake',
				target_schema='landing_app',
				target_table='customers_landing',
				source_connection='sourcedb',
				source_schema='app',
				source_table='t_customers',
				can_edit=False
			),
			TableLineageSourceListModel(
				target_connection='datalake',
				target_schema='landing_app',
				target_table='customers_landing',
				source_connection='sourcedb',
				source_schema='app',
				source_table='t_customers',
				can_edit=False
			)
		]
        ```
    
    
    



___
## update_table_source_table
Update a specific data lineage source table using a new model.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_lineage/update_table_source_table.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/lineage/sources/{sourceConnection}/schemas/{sourceSchema}/tables/{sourceTable}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`source_connection`</span>|Source connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`source_schema`</span>|Source schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`source_table`</span>|Source table name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table lineage source list model|*[TableLineageSourceSpec](../models/data_lineage.md#tablelineagesourcespec)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/lineage/sources/sample_connection/schemas/sample_schema/tables/sample_table^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"source_connection\":\"\",\"source_schema\":\"\",\"source_table\":\"\"}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_lineage import update_table_source_table
	from dqops.client.models import ColumnLineageSourceSpecMap, \
	                                TableLineageSourceSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableLineageSourceSpec(
		source_connection='',
		source_schema='',
		source_table='',
		columns=ColumnLineageSourceSpecMap()
	)
	
	call_result = update_table_source_table.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.data_lineage import update_table_source_table
	from dqops.client.models import ColumnLineageSourceSpecMap, \
	                                TableLineageSourceSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableLineageSourceSpec(
		source_connection='',
		source_schema='',
		source_table='',
		columns=ColumnLineageSourceSpecMap()
	)
	
	call_result = await update_table_source_table.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.data_lineage import update_table_source_table
	from dqops.client.models import ColumnLineageSourceSpecMap, \
	                                TableLineageSourceSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableLineageSourceSpec(
		source_connection='',
		source_schema='',
		source_table='',
		columns=ColumnLineageSourceSpecMap()
	)
	
	call_result = update_table_source_table.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
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
	from dqops.client.api.data_lineage import update_table_source_table
	from dqops.client.models import ColumnLineageSourceSpecMap, \
	                                TableLineageSourceSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableLineageSourceSpec(
		source_connection='',
		source_schema='',
		source_table='',
		columns=ColumnLineageSourceSpecMap()
	)
	
	call_result = await update_table_source_table.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



