
## ProviderSensorListModel  
Provider sensor list model that is returned by the REST API.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[provider_type](\docs\client\models\#providertype)|Provider type.|[ProviderType](\docs\client\models\#providertype)|
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


___  

## SensorFolderModel  
Sensor folder model that is returned by the REST API.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|folders|A dictionary of nested folders with sensors, the keys are the folder names.|Dict[string, [SensorFolderModel](\docs\client\models\sensors\#sensorfoldermodel)]|
|sensors|List of sensors defined in this folder.|List[[SensorListModel](\docs\client\models\sensors\#sensorlistmodel)]|


___  

## ProviderSensorModel  
Provider sensor model returned from REST API.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[provider_type](\docs\client\models\#providertype)|Provider type.|[ProviderType](\docs\client\models\#providertype)|
|[provider_sensor_definition_spec](\docs\reference\yaml\providersensoryaml\#providersensordefinitionspec)|Provider specific sensor definition specification|[ProviderSensorDefinitionSpec](\docs\reference\yaml\providersensoryaml\#providersensordefinitionspec)|
|sql_template|Provider specific Jinja2 SQL template|string|
|custom|Whether the provider sensor is a User Home provider sensor|boolean|
|built_in|This is a DQOps built-in provider sensor, whose parameters cannot be changed.|boolean|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|


___  

## SensorModel  
Sensor model returned from REST API.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|full_sensor_name|Full sensor name.|string|
|[sensor_definition_spec](\docs\reference\yaml\sensordefinitionyaml\#sensordefinitionspec)|Sensor definition specification.|[SensorDefinitionSpec](\docs\reference\yaml\sensordefinitionyaml\#sensordefinitionspec)|
|provider_sensor_list|Provider sensors list with provider specific sensor definitions.|List[[ProviderSensorModel](#providersensormodel)]|
|custom|Whether the sensor is a User Home sensor|boolean|
|built_in|This is a DQOps built-in sensor, whose parameters cannot be changed.|boolean|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|


___  
