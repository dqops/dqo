
## create_sensor  
Creates (adds) a new sensor given sensor information.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/create_sensor.py)
  

**POST**
```
api/sensors/{fullSensorName}  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Dictionary of sensor definitions|[SensorModel](\docs\client\operations\sensors\#sensormodel)|false|


___  

## delete_sensor  
Deletes a custom sensor definition  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/delete_sensor.py)
  

**DELETE**
```
api/sensors/{fullSensorName}  
```





___  

## get_all_sensors  
Returns a flat list of all sensors available in DQO, both built-in sensors and user defined or customized sensors.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/get_all_sensors.py)
  

**GET**
```
api/sensors  
```





___  

## get_sensor  
Returns a sensor model  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/get_sensor.py)
  

**GET**
```
api/sensors/{fullSensorName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[sensor_model](\docs\client\operations\sensors\#sensormodel)||[SensorModel](\docs\client\operations\sensors\#sensormodel)|






___  

## get_sensor_folder_tree  
Returns a tree of all sensors available in DQO, both built-in sensors and user defined or customized sensors.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/get_sensor_folder_tree.py)
  

**GET**
```
api/definitions/sensors  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[sensor_basic_folder_model]()||[SensorBasicFolderModel]()|






___  

## update_sensor  
Updates an existing sensor, making a custom sensor definition if it is not present. 
Removes sensor if custom definition is same as Dqo Home sensor  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/update_sensor.py)
  

**PUT**
```
api/sensors/{fullSensorName}  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Dictionary of sensor definitions|[SensorModel](\docs\client\operations\sensors\#sensormodel)|false|


___  

___  

## SensorModel  
Sensor model returned from REST API.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|full_sensor_name|Full sensor name.|string| | | |
|[sensor_definition_spec](\docs\reference\yaml\sensordefinitionyaml\#sensordefinitionspec)|Sensor definition specification.|[sensorDefinitionSpec](\docs\reference\yaml\sensordefinitionyaml\#sensordefinitionspec)| | | |
|custom|Whether the sensor is a User Home sensor|boolean| | | |
|built_in|This is a DQO built-in sensor, whose parameters cannot be changed.|boolean| | | |
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean| | | |

___  

