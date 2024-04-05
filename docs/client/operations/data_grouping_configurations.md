---
title: DQOps REST API data_grouping_configurations operations
---
# DQOps REST API data_grouping_configurations operations
Operations for managing the configuration of data groupings on a table level in DQOps.


___
## create_table_grouping_configuration
Creates a new data grouping configuration on a table level

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/create_table_grouping_configuration.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings
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
|Data grouping configuration simplified model|*[DataGroupingConfigurationTrimmedModel](../models/data_grouping_configurations.md#datagroupingconfigurationtrimmedmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"data_grouping_configuration_name\":\"sample_data_grouping\",\"spec\":{\"level_3\":{\"source\":\"column_value\",\"column\":\"sample_column\"}},\"can_edit\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import create_table_grouping_configuration
	from dqops.client.models import DataGroupingConfigurationSpec, \
	                                DataGroupingConfigurationTrimmedModel, \
	                                DataGroupingDimensionSource, \
	                                DataGroupingDimensionSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DataGroupingConfigurationTrimmedModel(
		data_grouping_configuration_name='sample_data_grouping',
		spec=DataGroupingConfigurationSpec(
			level_3=DataGroupingDimensionSpec(
				source=DataGroupingDimensionSource.COLUMN_VALUE,
				column='sample_column'
			)
		),
		can_edit=True
	)
	
	call_result = create_table_grouping_configuration.sync(
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
	from dqops.client.api.data_grouping_configurations import create_table_grouping_configuration
	from dqops.client.models import DataGroupingConfigurationSpec, \
	                                DataGroupingConfigurationTrimmedModel, \
	                                DataGroupingDimensionSource, \
	                                DataGroupingDimensionSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DataGroupingConfigurationTrimmedModel(
		data_grouping_configuration_name='sample_data_grouping',
		spec=DataGroupingConfigurationSpec(
			level_3=DataGroupingDimensionSpec(
				source=DataGroupingDimensionSource.COLUMN_VALUE,
				column='sample_column'
			)
		),
		can_edit=True
	)
	
	call_result = await create_table_grouping_configuration.asyncio(
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
	from dqops.client.api.data_grouping_configurations import create_table_grouping_configuration
	from dqops.client.models import DataGroupingConfigurationSpec, \
	                                DataGroupingConfigurationTrimmedModel, \
	                                DataGroupingDimensionSource, \
	                                DataGroupingDimensionSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DataGroupingConfigurationTrimmedModel(
		data_grouping_configuration_name='sample_data_grouping',
		spec=DataGroupingConfigurationSpec(
			level_3=DataGroupingDimensionSpec(
				source=DataGroupingDimensionSource.COLUMN_VALUE,
				column='sample_column'
			)
		),
		can_edit=True
	)
	
	call_result = create_table_grouping_configuration.sync(
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
	from dqops.client.api.data_grouping_configurations import create_table_grouping_configuration
	from dqops.client.models import DataGroupingConfigurationSpec, \
	                                DataGroupingConfigurationTrimmedModel, \
	                                DataGroupingDimensionSource, \
	                                DataGroupingDimensionSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DataGroupingConfigurationTrimmedModel(
		data_grouping_configuration_name='sample_data_grouping',
		spec=DataGroupingConfigurationSpec(
			level_3=DataGroupingDimensionSpec(
				source=DataGroupingDimensionSource.COLUMN_VALUE,
				column='sample_column'
			)
		),
		can_edit=True
	)
	
	call_result = await create_table_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## delete_table_grouping_configuration
Deletes a data grouping configuration from a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/delete_table_grouping_configuration.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{dataGroupingConfigurationName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`data_grouping_configuration_name`</span>|Data grouping configuration name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X DELETE http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings/sample_data_grouping^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import delete_table_grouping_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = delete_table_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import delete_table_grouping_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await delete_table_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import delete_table_grouping_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = delete_table_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import delete_table_grouping_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await delete_table_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client
	)
	
    ```

    



___
## get_table_grouping_configuration
Returns a model of the data grouping configuration

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/get_table_grouping_configuration.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{groupingConfigurationName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`data_grouping_configuration_model`](../models/data_grouping_configurations.md#datagroupingconfigurationmodel)</span>||*[DataGroupingConfigurationModel](../models/data_grouping_configurations.md#datagroupingconfigurationmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`grouping_configuration_name`</span>|Data grouping configuration name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings/sample_data_grouping^
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
	from dqops.client.api.data_grouping_configurations import get_table_grouping_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DataGroupingConfigurationModel(can_edit=False)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import get_table_grouping_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DataGroupingConfigurationModel(can_edit=False)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import get_table_grouping_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DataGroupingConfigurationModel(can_edit=False)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import get_table_grouping_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DataGroupingConfigurationModel(can_edit=False)
        ```
    
    
    



___
## get_table_grouping_configurations
Returns the list of data grouping configurations on a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/get_table_grouping_configurations.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`data_grouping_configuration_list_model`</span>||*List[[DataGroupingConfigurationListModel](../models/data_grouping_configurations.md#datagroupingconfigurationlistmodel)]*|




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
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "default_data_grouping_configuration" : false,
		  "can_edit" : false
		}, {
		  "default_data_grouping_configuration" : false,
		  "can_edit" : false
		}, {
		  "default_data_grouping_configuration" : false,
		  "can_edit" : false
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import get_table_grouping_configurations
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_grouping_configurations.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			DataGroupingConfigurationListModel(
				default_data_grouping_configuration=False,
				can_edit=False
			),
			DataGroupingConfigurationListModel(
				default_data_grouping_configuration=False,
				can_edit=False
			),
			DataGroupingConfigurationListModel(
				default_data_grouping_configuration=False,
				can_edit=False
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import get_table_grouping_configurations
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_grouping_configurations.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			DataGroupingConfigurationListModel(
				default_data_grouping_configuration=False,
				can_edit=False
			),
			DataGroupingConfigurationListModel(
				default_data_grouping_configuration=False,
				can_edit=False
			),
			DataGroupingConfigurationListModel(
				default_data_grouping_configuration=False,
				can_edit=False
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import get_table_grouping_configurations
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_grouping_configurations.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			DataGroupingConfigurationListModel(
				default_data_grouping_configuration=False,
				can_edit=False
			),
			DataGroupingConfigurationListModel(
				default_data_grouping_configuration=False,
				can_edit=False
			),
			DataGroupingConfigurationListModel(
				default_data_grouping_configuration=False,
				can_edit=False
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import get_table_grouping_configurations
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_grouping_configurations.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			DataGroupingConfigurationListModel(
				default_data_grouping_configuration=False,
				can_edit=False
			),
			DataGroupingConfigurationListModel(
				default_data_grouping_configuration=False,
				can_edit=False
			),
			DataGroupingConfigurationListModel(
				default_data_grouping_configuration=False,
				can_edit=False
			)
		]
        ```
    
    
    



___
## set_table_default_grouping_configuration
Sets a table&#x27;s grouping configuration as the default or disables data grouping

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/set_table_default_grouping_configuration.py) to see the source code on GitHub.


**PATCH**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/setdefault
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`data_grouping_configuration_name`</span>|Data grouping configuration name or empty to disable data grouping|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PATCH http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings/setdefault^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import set_table_default_grouping_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = set_table_default_grouping_configuration.sync(
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
	from dqops.client.api.data_grouping_configurations import set_table_default_grouping_configuration
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await set_table_default_grouping_configuration.asyncio(
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
	from dqops.client.api.data_grouping_configurations import set_table_default_grouping_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = set_table_default_grouping_configuration.sync(
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
	from dqops.client.api.data_grouping_configurations import set_table_default_grouping_configuration
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await set_table_default_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    



___
## update_table_grouping_configuration
Updates a data grouping configuration according to the provided model

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/update_table_grouping_configuration.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{dataGroupingConfigurationName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`data_grouping_configuration_name`</span>|Data grouping configuration name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Data grouping configuration simplified model|*[DataGroupingConfigurationTrimmedModel](../models/data_grouping_configurations.md#datagroupingconfigurationtrimmedmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings/sample_data_grouping^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"data_grouping_configuration_name\":\"sample_data_grouping\",\"spec\":{\"level_3\":{\"source\":\"column_value\",\"column\":\"sample_column\"}},\"can_edit\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import update_table_grouping_configuration
	from dqops.client.models import DataGroupingConfigurationSpec, \
	                                DataGroupingConfigurationTrimmedModel, \
	                                DataGroupingDimensionSource, \
	                                DataGroupingDimensionSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DataGroupingConfigurationTrimmedModel(
		data_grouping_configuration_name='sample_data_grouping',
		spec=DataGroupingConfigurationSpec(
			level_3=DataGroupingDimensionSpec(
				source=DataGroupingDimensionSource.COLUMN_VALUE,
				column='sample_column'
			)
		),
		can_edit=True
	)
	
	call_result = update_table_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import update_table_grouping_configuration
	from dqops.client.models import DataGroupingConfigurationSpec, \
	                                DataGroupingConfigurationTrimmedModel, \
	                                DataGroupingDimensionSource, \
	                                DataGroupingDimensionSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DataGroupingConfigurationTrimmedModel(
		data_grouping_configuration_name='sample_data_grouping',
		spec=DataGroupingConfigurationSpec(
			level_3=DataGroupingDimensionSpec(
				source=DataGroupingDimensionSource.COLUMN_VALUE,
				column='sample_column'
			)
		),
		can_edit=True
	)
	
	call_result = await update_table_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import update_table_grouping_configuration
	from dqops.client.models import DataGroupingConfigurationSpec, \
	                                DataGroupingConfigurationTrimmedModel, \
	                                DataGroupingDimensionSource, \
	                                DataGroupingDimensionSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DataGroupingConfigurationTrimmedModel(
		data_grouping_configuration_name='sample_data_grouping',
		spec=DataGroupingConfigurationSpec(
			level_3=DataGroupingDimensionSpec(
				source=DataGroupingDimensionSource.COLUMN_VALUE,
				column='sample_column'
			)
		),
		can_edit=True
	)
	
	call_result = update_table_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import update_table_grouping_configuration
	from dqops.client.models import DataGroupingConfigurationSpec, \
	                                DataGroupingConfigurationTrimmedModel, \
	                                DataGroupingDimensionSource, \
	                                DataGroupingDimensionSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DataGroupingConfigurationTrimmedModel(
		data_grouping_configuration_name='sample_data_grouping',
		spec=DataGroupingConfigurationSpec(
			level_3=DataGroupingDimensionSpec(
				source=DataGroupingDimensionSource.COLUMN_VALUE,
				column='sample_column'
			)
		),
		can_edit=True
	)
	
	call_result = await update_table_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



