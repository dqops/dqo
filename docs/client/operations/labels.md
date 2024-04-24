---
title: DQOps REST API labels operations
---
# DQOps REST API labels operations
Operations that returns all labels that are assigned to data assets. Labels serve the purpose of a lazy business glossary.


___
## get_all_labels_for_columns
Returns a list of all labels applied to columns, including the count of assignments to these data assets.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/labels/get_all_labels_for_columns.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/labels/columns
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`label_model`</span>||*List[[LabelModel](../models/labels.md#labelmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`page`</span>|Page number, the first page is 1|*long*| |
|<span class="no-wrap-code">`limit`</span>|Page size, the default is 100 rows, but paging is disabled is neither page and limit parameters are provided|*long*| |
|<span class="no-wrap-code">`prefix`</span>|Optional filter for the prefix of labels. For example, when the prefix is &quot;address&quot;, this operation will return labels &quot;address/city&quot; and &quot;address/country&quot;, but not &quot;address&quot;.|*string*| |
|<span class="no-wrap-code">`filter`</span>|Optional table name filter|*string*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/labels/columns^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "label" : "address/city",
		  "labels_count" : 10,
		  "nested_labels_count" : 2
		}, {
		  "label" : "address/city",
		  "labels_count" : 10,
		  "nested_labels_count" : 2
		}, {
		  "label" : "address/city",
		  "labels_count" : 10,
		  "nested_labels_count" : 2
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.labels import get_all_labels_for_columns
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_labels_for_columns.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.labels import get_all_labels_for_columns
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_labels_for_columns.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.labels import get_all_labels_for_columns
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_labels_for_columns.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.labels import get_all_labels_for_columns
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_labels_for_columns.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			)
		]
        ```
    
    
    



___
## get_all_labels_for_connections
Returns a list of all labels applied to the connections to data sources, including the count of assignments to these data assets.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/labels/get_all_labels_for_connections.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/labels/connections
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`label_model`</span>||*List[[LabelModel](../models/labels.md#labelmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`page`</span>|Page number, the first page is 1|*long*| |
|<span class="no-wrap-code">`limit`</span>|Page size, the default is 100 rows, but paging is disabled is neither page and limit parameters are provided|*long*| |
|<span class="no-wrap-code">`prefix`</span>|Optional filter for the prefix of labels. For example, when the prefix is &quot;address&quot;, this operation will return labels &quot;address/city&quot; and &quot;address/country&quot;, but not &quot;address&quot;.|*string*| |
|<span class="no-wrap-code">`filter`</span>|Optional table name filter|*string*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/labels/connections^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "label" : "address/city",
		  "labels_count" : 10,
		  "nested_labels_count" : 2
		}, {
		  "label" : "address/city",
		  "labels_count" : 10,
		  "nested_labels_count" : 2
		}, {
		  "label" : "address/city",
		  "labels_count" : 10,
		  "nested_labels_count" : 2
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.labels import get_all_labels_for_connections
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_labels_for_connections.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.labels import get_all_labels_for_connections
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_labels_for_connections.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.labels import get_all_labels_for_connections
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_labels_for_connections.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.labels import get_all_labels_for_connections
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_labels_for_connections.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			)
		]
        ```
    
    
    



___
## get_all_labels_for_tables
Returns a list of all labels applied to tables, including the count of assignments to these data assets.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/labels/get_all_labels_for_tables.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/labels/tables
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`label_model`</span>||*List[[LabelModel](../models/labels.md#labelmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`page`</span>|Page number, the first page is 1|*long*| |
|<span class="no-wrap-code">`limit`</span>|Page size, the default is 100 rows, but paging is disabled is neither page and limit parameters are provided|*long*| |
|<span class="no-wrap-code">`prefix`</span>|Optional filter for the prefix of labels. For example, when the prefix is &quot;address&quot;, this operation will return labels &quot;address/city&quot; and &quot;address/country&quot;, but not &quot;address&quot;.|*string*| |
|<span class="no-wrap-code">`filter`</span>|Optional table name filter|*string*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/labels/tables^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "label" : "address/city",
		  "labels_count" : 10,
		  "nested_labels_count" : 2
		}, {
		  "label" : "address/city",
		  "labels_count" : 10,
		  "nested_labels_count" : 2
		}, {
		  "label" : "address/city",
		  "labels_count" : 10,
		  "nested_labels_count" : 2
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.labels import get_all_labels_for_tables
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_labels_for_tables.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.labels import get_all_labels_for_tables
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_labels_for_tables.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.labels import get_all_labels_for_tables
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_labels_for_tables.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.labels import get_all_labels_for_tables
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_labels_for_tables.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			),
			LabelModel(
				label='address/city',
				labels_count=10,
				nested_labels_count=2
			)
		]
        ```
    
    
    



