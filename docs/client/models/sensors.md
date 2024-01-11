
## ProviderSensorListModel
Provider sensor list model that is returned by the REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[provider_type](./Common.md#providertype)|Provider type.|[ProviderType](./Common.md#providertype)|
|custom|This connection specific template is a custom sensor template or was customized by the user.|boolean|
|built_in|This connection specific template is provided with DQOps as a built-in sensor.|boolean|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|


___

## SensorListModel
Sensor list model that is returned by the REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|sensor_name|Sensor name, excluding the parent folder.|string|
|full_sensor_name|Full sensor name, including the folder path within the &quot;sensors&quot; folder where the sensor definitions are stored. This is the unique identifier of the sensor.|string|
|custom|This sensor has is a custom sensor or was customized by the user. This is a read-only flag.|boolean|
|built_in|This sensor is provided with DQOps as a built-in sensor. This is a read-only flag.|boolean|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|
|provider_sensors|List of provider (database) specific models.|List[[ProviderSensorListModel](#providersensorlistmodel)]|
|yaml_parsing_error|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|string|


___

## SensorFolderModel
Sensor folder model that is returned by the REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|folders|A dictionary of nested folders with sensors, the keys are the folder names.|Dict[string, [SensorFolderModel](./sensors.md#SensorFolderModel)]|
|sensors|List of sensors defined in this folder.|List[[SensorListModel](./sensors.md#SensorListModel)]|


___

## ProviderSensorModel
Provider sensor model returned from REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[provider_type](./Common.md#providertype)|Provider type.|[ProviderType](./Common.md#providertype)|
|[provider_sensor_definition_spec](../../reference/yaml/ProviderSensorYaml.md#providersensordefinitionspec)|Provider specific sensor definition specification|[ProviderSensorDefinitionSpec](../../reference/yaml/ProviderSensorYaml.md#providersensordefinitionspec)|
|sql_template|Provider specific Jinja2 SQL template|string|
|custom|Whether the provider sensor is a User Home provider sensor|boolean|
|built_in|This is a DQOps built-in provider sensor, whose parameters cannot be changed.|boolean|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|
|yaml_parsing_error|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|string|


___

## SensorModel
Sensor model returned from REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|full_sensor_name|Full sensor name.|string|
|[sensor_definition_spec](../../reference/yaml/SensorDefinitionYaml.md#sensordefinitionspec)|Sensor definition specification.|[SensorDefinitionSpec](../../reference/yaml/SensorDefinitionYaml.md#sensordefinitionspec)|
|provider_sensor_list|Provider sensors list with provider specific sensor definitions.|List[[ProviderSensorModel](#providersensormodel)]|
|custom|Whether the sensor is a User Home sensor|boolean|
|built_in|This is a DQOps built-in sensor, whose parameters cannot be changed.|boolean|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|
|yaml_parsing_error|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|string|


___

