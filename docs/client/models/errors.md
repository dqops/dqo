# DQOps REST API errors models reference
The references of all objects used by [errors](../operations/errors.md) REST API operations are listed below.


## TimePeriodGradient
Time series gradient type (daily, monthly, quarterly, monthly, weekly, hourly).


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|week<br/>month<br/>hour<br/>year<br/>millisecond<br/>day<br/>quarter<br/>|

___

## ErrorEntryModel
Detailed error statuses for a single check. Represent one row in the errors table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|actual_value|Actual value|double|
|expected_value|Expected value|double|
|column_name|Column name|string|
|data_group|Data group|string|
|[check_type](./table_comparisons.md#CheckType)|Check type|[CheckType](./table_comparisons.md#CheckType)|
|duration_ms|Duration (ms)|integer|
|[time_gradient](#timeperiodgradient)|Time gradient|[TimePeriodGradient](#timeperiodgradient)|
|time_period|Time period|datetime|
|provider|Provider name|string|
|quality_dimension|Data quality dimension|string|
|sensor_name|Sensor name|string|
|readout_id|Sensor readout ID|string|
|error_message|Error message|string|
|error_source|Error source|string|
|error_timestamp|Error timestamp|datetime|
|table_comparison|Table comparison name|string|


___

## ErrorsListModel
Error detailed statuses. Returned in the context of a single data group, with a supplied list of other data groups.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_name|Check name|string|
|check_display_name|Check display name|string|
|[check_type](./table_comparisons.md#CheckType)|Check type|[CheckType](./table_comparisons.md#CheckType)|
|check_hash|Check hash|long|
|check_category|Check category name|string|
|data_groups_names|Data groups list|List[string]|
|data_group|Selected data group|string|
|error_entries|Error entries|List[[ErrorEntryModel](#errorentrymodel)]|


___

