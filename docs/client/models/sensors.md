---
title: DQOps REST API sensors models reference
---
# DQOps REST API sensors models reference
The references of all objects used by [sensors](../operations/sensors.md) REST API operations are listed below.


## ProviderSensorListModel
Provider sensor list model that is returned by the REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`provider_type`](./common.md#providertype)</span>|Provider type.|*[ProviderType](./common.md#providertype)*|
|<span class="no-wrap-code">`custom`</span>|This connection specific template is a custom sensor template or was customized by the user.|*boolean*|
|<span class="no-wrap-code">`built_in`</span>|This connection specific template is provided with DQOps as a built-in sensor.|*boolean*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|


___

## SensorListModel
Sensor list model that is returned by the REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`sensor_name`</span>|Sensor name, excluding the parent folder.|*string*|
|<span class="no-wrap-code">`full_sensor_name`</span>|Full sensor name, including the folder path within the "sensors" folder where the sensor definitions are stored. This is the unique identifier of the sensor.|*string*|
|<span class="no-wrap-code">`custom`</span>|This sensor has is a custom sensor or was customized by the user. This is a read-only flag.|*boolean*|
|<span class="no-wrap-code">`built_in`</span>|This sensor is provided with DQOps as a built-in sensor. This is a read-only flag.|*boolean*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`provider_sensors`</span>|List of provider (database) specific models.|*List[[ProviderSensorListModel](#providersensorlistmodel)]*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

## SensorFolderModel
Sensor folder model that is returned by the REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`folders`</span>|A dictionary of nested folders with sensors, the keys are the folder names.|*Dict[string, [SensorFolderModel](./sensors.md#sensorfoldermodel)]*|
|<span class="no-wrap-code">`sensors`</span>|List of sensors defined in this folder.|*List[[SensorListModel](./sensors.md#sensorlistmodel)]*|


___

## ProviderSensorModel
Provider sensor model returned from REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`provider_type`](./common.md#providertype)</span>|Provider type.|*[ProviderType](./common.md#providertype)*|
|<span class="no-wrap-code">[`provider_sensor_definition_spec`](../../reference/yaml/ProviderSensorYaml.md#providersensordefinitionspec)</span>|Provider specific sensor definition specification|*[ProviderSensorDefinitionSpec](../../reference/yaml/ProviderSensorYaml.md#providersensordefinitionspec)*|
|<span class="no-wrap-code">`sql_template`</span>|Provider specific Jinja2 SQL template|*string*|
|<span class="no-wrap-code">`error_sampling_template`</span>|Provider specific Jinja2 SQL template for capturing error samples|*string*|
|<span class="no-wrap-code">`custom`</span>|Whether the provider sensor is a User Home provider sensor|*boolean*|
|<span class="no-wrap-code">`built_in`</span>|This is a DQOps built-in provider sensor, whose parameters cannot be changed.|*boolean*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

## SensorModel
Sensor model returned from REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`full_sensor_name`</span>|Full sensor name.|*string*|
|<span class="no-wrap-code">[`sensor_definition_spec`](../../reference/yaml/SensorDefinitionYaml.md#sensordefinitionspec)</span>|Sensor definition specification.|*[SensorDefinitionSpec](../../reference/yaml/SensorDefinitionYaml.md#sensordefinitionspec)*|
|<span class="no-wrap-code">`provider_sensor_list`</span>|Provider sensors list with provider specific sensor definitions.|*List[[ProviderSensorModel](#providersensormodel)]*|
|<span class="no-wrap-code">`custom`</span>|Whether the sensor is a User Home sensor|*boolean*|
|<span class="no-wrap-code">`built_in`</span>|This is a DQOps built-in sensor, whose parameters cannot be changed.|*boolean*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

