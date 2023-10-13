
## SensorModel  
Sensor model returned from REST API.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|full_sensor_name|Full sensor name.|string|
|[sensor_definition_spec](\docs\reference\yaml\sensordefinitionyaml\#sensordefinitionspec)|Sensor definition specification.|[SensorDefinitionSpec](\docs\reference\yaml\sensordefinitionyaml\#sensordefinitionspec)|
|custom|Whether the sensor is a User Home sensor|boolean|
|built_in|This is a DQOps built-in sensor, whose parameters cannot be changed.|boolean|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|


___  

