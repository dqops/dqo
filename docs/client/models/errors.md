# DQOps REST API errors models reference
The references of all objects used by [errors](/docs/client/operations/errors.md) REST API operations are listed below.


## TimePeriodGradient
Time series gradient type (daily, monthly, quarterly, monthly, weekly, hourly).


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|year<br/>quarter<br/>month<br/>week<br/>day<br/>hour<br/>millisecond<br/>|

___

## ErrorEntryModel
Detailed error statuses for a single check. Represent one row in the errors table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`actual_value`</span>|Actual value|*double*|
|<span class="no-wrap-code">`expected_value`</span>|Expected value|*double*|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|
|<span class="no-wrap-code">`data_group`</span>|Data group|*string*|
|<span class="no-wrap-code">[`check_type`](/docs/client/models/table_comparisons.md#checktype)</span>|Check type|*[CheckType](/docs/client/models/table_comparisons.md#checktype)*|
|<span class="no-wrap-code">`duration_ms`</span>|Duration (ms)|*integer*|
|<span class="no-wrap-code">[`time_gradient`](#timeperiodgradient)</span>|Time gradient|*[TimePeriodGradient](#timeperiodgradient)*|
|<span class="no-wrap-code">`time_period`</span>|Time period|*datetime*|
|<span class="no-wrap-code">`provider`</span>|Provider name|*string*|
|<span class="no-wrap-code">`quality_dimension`</span>|Data quality dimension|*string*|
|<span class="no-wrap-code">`sensor_name`</span>|Sensor name|*string*|
|<span class="no-wrap-code">`readout_id`</span>|Sensor readout ID|*string*|
|<span class="no-wrap-code">`error_message`</span>|Error message|*string*|
|<span class="no-wrap-code">`error_source`</span>|Error source|*string*|
|<span class="no-wrap-code">`error_timestamp`</span>|Error timestamp|*datetime*|
|<span class="no-wrap-code">`table_comparison`</span>|Table comparison name|*string*|


___

## ErrorsListModel
Error detailed statuses. Returned in the context of a single data group, with a supplied list of other data groups.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*|
|<span class="no-wrap-code">`check_display_name`</span>|Check display name|*string*|
|<span class="no-wrap-code">[`check_type`](/docs/client/models/table_comparisons.md#checktype)</span>|Check type|*[CheckType](/docs/client/models/table_comparisons.md#checktype)*|
|<span class="no-wrap-code">`check_hash`</span>|Check hash|*long*|
|<span class="no-wrap-code">`check_category`</span>|Check category name|*string*|
|<span class="no-wrap-code">`data_groups_names`</span>|Data groups list|*List[string]*|
|<span class="no-wrap-code">`data_group`</span>|Selected data group|*string*|
|<span class="no-wrap-code">`error_entries`</span>|Error entries|*List[[ErrorEntryModel](#errorentrymodel)]*|


___

