Manages data grouping configurations on a table


___
## create_table_grouping_configuration
Creates a new data grouping configuration on a table level
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/create_table_grouping_configuration.py)


**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Data grouping configuration simplified model|[DataGroupingConfigurationTrimmedModel](../models/data_grouping_configurations.md#datagroupingconfigurationtrimmedmodel)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"data_grouping_configuration_name\":\"sample_data_grouping\",\"spec\":{\"level_3\":{\"source\":\"column_value\",\"column\":\"sample_column\"}},\"can_edit\":true}"
	
    ```

=== "Python sync client"

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
				source=DataGroupingDimensionSource.column_value,
				column='sample_column'
			)
		),
		can_edit=True
	)
	
	create_table_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

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
				source=DataGroupingDimensionSource.column_value,
				column='sample_column'
			)
		),
		can_edit=True
	)
	
	async_result = create_table_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
				source=DataGroupingDimensionSource.column_value,
				column='sample_column'
			)
		),
		can_edit=True
	)
	
	create_table_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

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
				source=DataGroupingDimensionSource.column_value,
				column='sample_column'
			)
		),
		can_edit=True
	)
	
	async_result = create_table_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





___
## delete_table_grouping_configuration
Deletes a data grouping configuration from a table
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/delete_table_grouping_configuration.py)


**DELETE**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{dataGroupingConfigurationName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|data_grouping_configuration_name|Data grouping configuration name|string|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl -X DELETE http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings/sample_data_grouping^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import delete_table_grouping_configuration
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	delete_table_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import delete_table_grouping_configuration
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	async_result = delete_table_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import delete_table_grouping_configuration
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	delete_table_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import delete_table_grouping_configuration
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	async_result = delete_table_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client
	)
	
	await async_result
	
    ```





___
## get_table_grouping_configuration
Returns a model of the data grouping configuration
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/get_table_grouping_configuration.py)


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{groupingConfigurationName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[data_grouping_configuration_model](../models/data_grouping_configurations.md#datagroupingconfigurationmodel)||[DataGroupingConfigurationModel](../models/data_grouping_configurations.md#datagroupingconfigurationmodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|grouping_configuration_name|Data grouping configuration name|string|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings/sample_data_grouping^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import get_table_grouping_configuration
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_table_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import get_table_grouping_configuration
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_table_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import get_table_grouping_configuration
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_table_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import get_table_grouping_configuration
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_table_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
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
## get_table_grouping_configurations
Returns the list of data grouping configurations on a table
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/get_table_grouping_configurations.py)


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|data_grouping_configuration_list_model||List[[DataGroupingConfigurationListModel](../models/data_grouping_configurations.md#datagroupingconfigurationlistmodel)]|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import get_table_grouping_configurations
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_table_grouping_configurations.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import get_table_grouping_configurations
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_table_grouping_configurations.asyncio(
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
	from dqops.client.api.data_grouping_configurations import get_table_grouping_configurations
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_table_grouping_configurations.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import get_table_grouping_configurations
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_table_grouping_configurations.asyncio(
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


___
## set_table_default_grouping_configuration
Sets a table&#x27;s grouping configuration as the default or disables data grouping
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/set_table_default_grouping_configuration.py)


**PATCH**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/setdefault
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|data_grouping_configuration_name|Data grouping configuration name or empty to disable data grouping|string|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl -X PATCH http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings/setdefault^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import set_table_default_grouping_configuration
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	set_table_default_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import set_table_default_grouping_configuration
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	async_result = set_table_default_grouping_configuration.asyncio(
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
	from dqops.client.api.data_grouping_configurations import set_table_default_grouping_configuration
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	set_table_default_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.data_grouping_configurations import set_table_default_grouping_configuration
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	async_result = set_table_default_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
	await async_result
	
    ```





___
## update_table_grouping_configuration
Updates a data grouping configuration according to the provided model
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/update_table_grouping_configuration.py)


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{dataGroupingConfigurationName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|data_grouping_configuration_name|Data grouping configuration name|string|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Data grouping configuration simplified model|[DataGroupingConfigurationTrimmedModel](../models/data_grouping_configurations.md#datagroupingconfigurationtrimmedmodel)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings/sample_data_grouping^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"data_grouping_configuration_name\":\"sample_data_grouping\",\"spec\":{\"level_3\":{\"source\":\"column_value\",\"column\":\"sample_column\"}},\"can_edit\":true}"
	
    ```

=== "Python sync client"

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
				source=DataGroupingDimensionSource.column_value,
				column='sample_column'
			)
		),
		can_edit=True
	)
	
	update_table_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

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
				source=DataGroupingDimensionSource.column_value,
				column='sample_column'
			)
		),
		can_edit=True
	)
	
	async_result = update_table_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

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
				source=DataGroupingDimensionSource.column_value,
				column='sample_column'
			)
		),
		can_edit=True
	)
	
	update_table_grouping_configuration.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

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
				source=DataGroupingDimensionSource.column_value,
				column='sample_column'
			)
		),
		can_edit=True
	)
	
	async_result = update_table_grouping_configuration.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_data_grouping',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





