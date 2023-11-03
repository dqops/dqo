Sensors definition management  


___  
## create_sensor  
Creates (adds) a new sensor given sensor information.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/create_sensor.py)
  

**POST**
```
http://localhost:8888/api/sensors/{fullSensorName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_sensor_name|Full sensor name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Dictionary of sensor definitions|[SensorModel](\docs\client\models\sensors\#sensormodel)|false|




**Usage examples**  
=== "curl"
      
    ```
    curl -X POST http://localhost:8888/api/sensors/sample_target/sample_category/sample_sensor
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


___  
## delete_sensor  
Deletes a custom sensor definition  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/delete_sensor.py)
  

**DELETE**
```
http://localhost:8888/api/sensors/{fullSensorName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_sensor_name|Full sensor name|string|true|






**Usage examples**  
=== "curl"
      
    ```
    curl -X DELETE http://localhost:8888/api/sensors/sample_target/sample_category/sample_sensor
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


___  
## get_all_sensors  
Returns a flat list of all sensors available in DQOps, both built-in sensors and user defined or customized sensors.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/get_all_sensors.py)
  

**GET**
```
http://localhost:8888/api/sensors  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|sensor_list_model||List[[SensorListModel](\docs\client\models\sensors\#sensorlistmodel)]|








**Usage examples**  
=== "curl"
      
    ```
    curl http://localhost:8888/api/sensors
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


___  
## get_sensor  
Returns a sensor model  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/get_sensor.py)
  

**GET**
```
http://localhost:8888/api/sensors/{fullSensorName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[sensor_model](\docs\client\models\sensors\#sensormodel)||[SensorModel](\docs\client\models\sensors\#sensormodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_sensor_name|Full sensor name|string|true|






**Usage examples**  
=== "curl"
      
    ```
    curl http://localhost:8888/api/sensors/sample_target/sample_category/sample_sensor
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


___  
## get_sensor_folder_tree  
Returns a tree of all sensors available in DQOps, both built-in sensors and user defined or customized sensors.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/get_sensor_folder_tree.py)
  

**GET**
```
http://localhost:8888/api/definitions/sensors  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[sensor_folder_model](\docs\client\models\sensors\#sensorfoldermodel)||[SensorFolderModel](\docs\client\models\sensors\#sensorfoldermodel)|








**Usage examples**  
=== "curl"
      
    ```
    curl http://localhost:8888/api/definitions/sensors
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


___  
## update_sensor  
Updates an existing sensor, making a custom sensor definition if it is not present. 
Removes sensor if custom definition is same as Dqo Home sensor  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensors/update_sensor.py)
  

**PUT**
```
http://localhost:8888/api/sensors/{fullSensorName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_sensor_name|Full sensor name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Dictionary of sensor definitions|[SensorModel](\docs\client\models\sensors\#sensormodel)|false|




**Usage examples**  
=== "curl"
      
    ```
    curl -X PUT http://localhost:8888/api/sensors/sample_target/sample_category/sample_sensor
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


