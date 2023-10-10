
## get_column_monitoring_sensor_readouts  
Returns a complete view of the sensor readouts for recent column level monitoring executions for the monitoring at a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensor_readouts/get_column_monitoring_sensor_readouts.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/readouts  
```





___  

## get_column_partitioned_sensor_readouts  
Returns a view of the sensor readouts for recent column level partitioned checks executions for a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensor_readouts/get_column_partitioned_sensor_readouts.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/readouts  
```





___  

## get_column_profiling_sensor_readouts  
Returns sensor results of the recent check executions for all column level data quality profiling checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensor_readouts/get_column_profiling_sensor_readouts.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/readouts  
```





___  

## get_table_monitoring_sensor_readouts  
Returns the complete results of the most recent table level monitoring executions for the monitoring at a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensor_readouts/get_table_monitoring_sensor_readouts.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/readouts  
```





___  

## get_table_partitioned_sensor_readouts  
Returns a complete view of sensor readouts for recent table level partitioned checks executions for a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensor_readouts/get_table_partitioned_sensor_readouts.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/readouts  
```





___  

## get_table_profiling_sensor_readouts  
Returns the complete results of the most recent check executions for all table level data quality profiling checks on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensor_readouts/get_table_profiling_sensor_readouts.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/readouts  
```





___  

___  

## SensorReadoutsListModel  
Sensor readout detailed results. Returned in the context of a single data group, with a supplied list of other data groups.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|check_name|Check name.|string| | | |
|check_display_name|Check display name.|string| | | |
|check_type|Check type.|string| | | |
|check_hash|Check hash.|long| | | |
|check_category|Check category name.|string| | | |
|sensor_name|Sensor name.|string| | | |
|data_group_names|Data groups list.|string_list| | | |
|data_group|Selected data group.|string| | | |

___  

