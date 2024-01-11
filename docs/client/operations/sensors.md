# DQOps REST API sensors operations
Operations for managing custom data quality sensor definitions in DQOps. The custom sensors are stored in the DQOps user home folder.


___
## create_sensor
Creates (adds) a new sensor given sensor information.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/create_sensor.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/sensors/{fullSensorName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_sensor_name|Full sensor name|string|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Dictionary of sensor definitions|[SensorModel](../models/sensors.md#sensormodel)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X POST http://localhost:8888/api/sensors/sample_target/sample_category/sample_sensor^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"full_sensor_name\":\"sample_target/sample_category/sample_sensor\",\"sensor_definition_spec\":{\"fields\":[{\"field_name\":\"sample_string_param\",\"data_type\":\"string\"},{\"field_name\":\"sample_double_param\",\"data_type\":\"double\"}],\"default_value\":0.0},\"provider_sensor_list\":[],\"custom\":false,\"built_in\":false,\"can_edit\":true}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import create_sensor
	from dqops.client.models import ParameterDefinitionsListSpec, \
	                                ProviderSensorModel, \
	                                SensorDefinitionSpec, \
	                                SensorModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = SensorModel(
		full_sensor_name='sample_target/sample_category/sample_sensor',
		sensor_definition_spec=SensorDefinitionSpec(
			fields=ParameterDefinitionsListSpec(),
			requires_event_timestamp=False,
			requires_ingestion_timestamp=False,
			default_value=0.0
		),
		provider_sensor_list=[
		
		],
		custom=False,
		built_in=False,
		can_edit=True
	)
	
	create_sensor.sync(
	    'sample_target/sample_category/sample_sensor',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import create_sensor
	from dqops.client.models import ParameterDefinitionsListSpec, \
	                                ProviderSensorModel, \
	                                SensorDefinitionSpec, \
	                                SensorModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = SensorModel(
		full_sensor_name='sample_target/sample_category/sample_sensor',
		sensor_definition_spec=SensorDefinitionSpec(
			fields=ParameterDefinitionsListSpec(),
			requires_event_timestamp=False,
			requires_ingestion_timestamp=False,
			default_value=0.0
		),
		provider_sensor_list=[
		
		],
		custom=False,
		built_in=False,
		can_edit=True
	)
	
	async_result = create_sensor.asyncio(
	    'sample_target/sample_category/sample_sensor',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import create_sensor
	from dqops.client.models import ParameterDefinitionsListSpec, \
	                                ProviderSensorModel, \
	                                SensorDefinitionSpec, \
	                                SensorModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = SensorModel(
		full_sensor_name='sample_target/sample_category/sample_sensor',
		sensor_definition_spec=SensorDefinitionSpec(
			fields=ParameterDefinitionsListSpec(),
			requires_event_timestamp=False,
			requires_ingestion_timestamp=False,
			default_value=0.0
		),
		provider_sensor_list=[
		
		],
		custom=False,
		built_in=False,
		can_edit=True
	)
	
	create_sensor.sync(
	    'sample_target/sample_category/sample_sensor',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import create_sensor
	from dqops.client.models import ParameterDefinitionsListSpec, \
	                                ProviderSensorModel, \
	                                SensorDefinitionSpec, \
	                                SensorModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = SensorModel(
		full_sensor_name='sample_target/sample_category/sample_sensor',
		sensor_definition_spec=SensorDefinitionSpec(
			fields=ParameterDefinitionsListSpec(),
			requires_event_timestamp=False,
			requires_ingestion_timestamp=False,
			default_value=0.0
		),
		provider_sensor_list=[
		
		],
		custom=False,
		built_in=False,
		can_edit=True
	)
	
	async_result = create_sensor.asyncio(
	    'sample_target/sample_category/sample_sensor',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





___
## delete_sensor
Deletes a custom sensor definition
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/delete_sensor.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/sensors/{fullSensorName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_sensor_name|Full sensor name|string|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl -X DELETE http://localhost:8888/api/sensors/sample_target/sample_category/sample_sensor^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import delete_sensor
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	delete_sensor.sync(
	    'sample_target/sample_category/sample_sensor',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import delete_sensor
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	async_result = delete_sensor.asyncio(
	    'sample_target/sample_category/sample_sensor',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import delete_sensor
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	delete_sensor.sync(
	    'sample_target/sample_category/sample_sensor',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import delete_sensor
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	async_result = delete_sensor.asyncio(
	    'sample_target/sample_category/sample_sensor',
	    client=dqops_client
	)
	
	await async_result
	
    ```





___
## get_all_sensors
Returns a flat list of all sensors available in DQOps, both built-in sensors and user defined or customized sensors.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/get_all_sensors.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/sensors
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|sensor_list_model||List[[SensorListModel](../models/sensors.md#sensorlistmodel)]|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/sensors^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import get_all_sensors
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_all_sensors.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import get_all_sensors
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_all_sensors.asyncio(
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import get_all_sensors
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_all_sensors.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import get_all_sensors
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_all_sensors.asyncio(
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
    [ {
	  "custom" : false,
	  "built_in" : false,
	  "can_edit" : false
	}, {
	  "custom" : false,
	  "built_in" : false,
	  "can_edit" : false
	}, {
	  "custom" : false,
	  "built_in" : false,
	  "can_edit" : false
	} ]
    ```


___
## get_sensor
Returns a sensor model
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/get_sensor.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/sensors/{fullSensorName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[sensor_model](../models/sensors.md#sensormodel)||[SensorModel](../models/sensors.md#sensormodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_sensor_name|Full sensor name|string|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/sensors/sample_target/sample_category/sample_sensor^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import get_sensor
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_sensor.sync(
	    'sample_target/sample_category/sample_sensor',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import get_sensor
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_sensor.asyncio(
	    'sample_target/sample_category/sample_sensor',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import get_sensor
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_sensor.sync(
	    'sample_target/sample_category/sample_sensor',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import get_sensor
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_sensor.asyncio(
	    'sample_target/sample_category/sample_sensor',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
    {
	  "full_sensor_name" : "sample_target/sample_category/sample_sensor",
	  "sensor_definition_spec" : {
	    "fields" : [ {
	      "field_name" : "sample_string_param",
	      "data_type" : "string"
	    }, {
	      "field_name" : "sample_double_param",
	      "data_type" : "double"
	    } ],
	    "default_value" : 0.0
	  },
	  "provider_sensor_list" : [ ],
	  "custom" : false,
	  "built_in" : false,
	  "can_edit" : true
	}
    ```


___
## get_sensor_folder_tree
Returns a tree of all sensors available in DQOps, both built-in sensors and user defined or customized sensors.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/get_sensor_folder_tree.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/definitions/sensors
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[sensor_folder_model](../models/sensors.md#sensorfoldermodel)||[SensorFolderModel](../models/sensors.md#sensorfoldermodel)|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/definitions/sensors^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import get_sensor_folder_tree
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_sensor_folder_tree.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import get_sensor_folder_tree
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_sensor_folder_tree.asyncio(
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import get_sensor_folder_tree
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_sensor_folder_tree.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import get_sensor_folder_tree
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_sensor_folder_tree.asyncio(
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
    {
	  "all_sensors" : [ ]
	}
    ```


___
## update_sensor
Updates an existing sensor, making a custom sensor definition if it is not present. 
Removes sensor if custom definition is same as Dqo Home sensor
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/update_sensor.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/sensors/{fullSensorName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_sensor_name|Full sensor name|string|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Dictionary of sensor definitions|[SensorModel](../models/sensors.md#sensormodel)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/sensors/sample_target/sample_category/sample_sensor^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"full_sensor_name\":\"sample_target/sample_category/sample_sensor\",\"sensor_definition_spec\":{\"fields\":[{\"field_name\":\"sample_string_param\",\"data_type\":\"string\"},{\"field_name\":\"sample_double_param\",\"data_type\":\"double\"}],\"default_value\":0.0},\"provider_sensor_list\":[],\"custom\":false,\"built_in\":false,\"can_edit\":true}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import update_sensor
	from dqops.client.models import ParameterDefinitionsListSpec, \
	                                ProviderSensorModel, \
	                                SensorDefinitionSpec, \
	                                SensorModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = SensorModel(
		full_sensor_name='sample_target/sample_category/sample_sensor',
		sensor_definition_spec=SensorDefinitionSpec(
			fields=ParameterDefinitionsListSpec(),
			requires_event_timestamp=False,
			requires_ingestion_timestamp=False,
			default_value=0.0
		),
		provider_sensor_list=[
		
		],
		custom=False,
		built_in=False,
		can_edit=True
	)
	
	update_sensor.sync(
	    'sample_target/sample_category/sample_sensor',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import update_sensor
	from dqops.client.models import ParameterDefinitionsListSpec, \
	                                ProviderSensorModel, \
	                                SensorDefinitionSpec, \
	                                SensorModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = SensorModel(
		full_sensor_name='sample_target/sample_category/sample_sensor',
		sensor_definition_spec=SensorDefinitionSpec(
			fields=ParameterDefinitionsListSpec(),
			requires_event_timestamp=False,
			requires_ingestion_timestamp=False,
			default_value=0.0
		),
		provider_sensor_list=[
		
		],
		custom=False,
		built_in=False,
		can_edit=True
	)
	
	async_result = update_sensor.asyncio(
	    'sample_target/sample_category/sample_sensor',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import update_sensor
	from dqops.client.models import ParameterDefinitionsListSpec, \
	                                ProviderSensorModel, \
	                                SensorDefinitionSpec, \
	                                SensorModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = SensorModel(
		full_sensor_name='sample_target/sample_category/sample_sensor',
		sensor_definition_spec=SensorDefinitionSpec(
			fields=ParameterDefinitionsListSpec(),
			requires_event_timestamp=False,
			requires_ingestion_timestamp=False,
			default_value=0.0
		),
		provider_sensor_list=[
		
		],
		custom=False,
		built_in=False,
		can_edit=True
	)
	
	update_sensor.sync(
	    'sample_target/sample_category/sample_sensor',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.sensors import update_sensor
	from dqops.client.models import ParameterDefinitionsListSpec, \
	                                ProviderSensorModel, \
	                                SensorDefinitionSpec, \
	                                SensorModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = SensorModel(
		full_sensor_name='sample_target/sample_category/sample_sensor',
		sensor_definition_spec=SensorDefinitionSpec(
			fields=ParameterDefinitionsListSpec(),
			requires_event_timestamp=False,
			requires_ingestion_timestamp=False,
			default_value=0.0
		),
		provider_sensor_list=[
		
		],
		custom=False,
		built_in=False,
		can_edit=True
	)
	
	async_result = update_sensor.asyncio(
	    'sample_target/sample_category/sample_sensor',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





