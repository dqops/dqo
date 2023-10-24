
## SensorReadoutEntryModel  
Detailed results for a single sensor. Represent one row in the sensor readouts table.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|id|Sensor readout ID.|string|
|check_name|Check name.|string|
|check_display_name|Check display name.|string|
|check_type|Check type.|string|
|actual_value|Actual value.|double|
|expected_value|Expected value.|double|
|column_name|Column name.|string|
|data_group|Data group.|string|
|duration_ms|Duration (ms).|integer|
|time_gradient|Time gradient.|string|
|time_period|Time period.|datetime|
|provider|Provider.|string|
|quality_dimension|Quality dimension.|string|


___  

## SensorReadoutsListModel  
Sensor readout detailed results. Returned in the context of a single data group, with a supplied list of other data groups.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_name|Check name.|string|
|check_display_name|Check display name.|string|
|check_type|Check type.|string|
|check_hash|Check hash.|long|
|check_category|Check category name.|string|
|sensor_name|Sensor name.|string|
|data_group_names|Data groups list.|string_list|
|data_group|Selected data group.|string|
|sensor_readout_entries|Sensor readout entries|List[[SensorReadoutEntryModel](#sensorreadoutentrymodel)]|


___  

