
## ErrorEntryModel  
Detailed error statuses for a single check. Represent one row in the errors table.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|actual_value|Actual value.|double|
|expected_value|Expected value.|double|
|column_name|Column name.|string|
|data_group|Data group.|string|
|duration_ms|Duration (ms).|integer|
|time_gradient|Time gradient.|string|
|time_period|Time period.|datetime|
|provider|Provider.|string|
|quality_dimension|Quality dimension.|string|
|sensor_name|Sensor name.|string|
|readout_id|Sensor readout id.|string|
|error_message|Error message.|string|
|error_source|Error source.|string|
|error_timestamp|Error timestamp.|datetime|
|table_comparison|Table comparison name|string|


___  

## ErrorsListModel  
Error detailed statuses. Returned in the context of a single data group, with a supplied list of other data groups.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_name|Check name.|string|
|check_display_name|Check display name.|string|
|check_type|Check type.|string|
|check_hash|Check hash.|long|
|check_category|Check category name.|string|
|data_groups_names|Data groups list.|string_list|
|data_group|Selected data group.|string|
|error_entries|Error entries|List[[ErrorEntryModel](#ErrorEntryModel)]|


___  

