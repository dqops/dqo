
## SensorReadoutEntryModel  
Detailed results for a single sensor. Represent one row in the sensor readouts table.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|id|Sensor readout primary key|string|
|check_name|Check name|string|
|check_display_name|Check display name|string|
|[check_type](../#CheckType)|Check type|[CheckType](../#CheckType)|
|actual_value|Actual value|double|
|expected_value|Expected value|double|
|column_name|Column name|string|
|data_group|Data group|string|
|duration_ms|Duration (ms)|integer|
|[time_gradient](../errors/#TimePeriodGradient)|Time gradient|[TimePeriodGradient](../errors/#TimePeriodGradient)|
|time_period|Time period|datetime|
|provider|Provider name|string|
|quality_dimension|Data quality dimension|string|
|table_comparison|Table comparison name|string|


___  

## SensorReadoutsListModel  
Sensor readout detailed results. Returned in the context of a single data group, with a supplied list of other data groups.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_name|Check name|string|
|check_display_name|Check display name|string|
|[check_type](../#CheckType)|Check type|[CheckType](../#CheckType)|
|check_hash|Check hash|long|
|check_category|Check category name|string|
|sensor_name|Sensor name|string|
|data_group_names|List of data groups that have values for this sensor readout (list of time series)|List[string]|
|data_group|Selected data group|string|
|sensor_readout_entries|Sensor readout entries|List[[SensorReadoutEntryModel](#SensorReadoutEntryModel)]|


___  

