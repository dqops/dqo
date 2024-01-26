# DQOps REST API sensor_readouts models reference
The references of all objects used by [sensor_readouts](/docs/client/operations/sensor_readouts.md) REST API operations are listed below.


## SensorReadoutEntryModel
Detailed results for a single sensor. Represent one row in the sensor readouts table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`id`</span>|Sensor readout primary key|*string*|
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*|
|<span class="no-wrap-code">`check_display_name`</span>|Check display name|*string*|
|<span class="no-wrap-code">[`check_type`](\docs\client\models\table_comparisons.md#checktype)</span>|Check type|*[CheckType](\docs\client\models\table_comparisons.md#checktype)*|
|<span class="no-wrap-code">`actual_value`</span>|Actual value|*double*|
|<span class="no-wrap-code">`expected_value`</span>|Expected value|*double*|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|
|<span class="no-wrap-code">`data_group`</span>|Data group|*string*|
|<span class="no-wrap-code">`duration_ms`</span>|Duration (ms)|*integer*|
|<span class="no-wrap-code">[`time_gradient`](\docs\client\models\errors.md#timeperiodgradient)</span>|Time gradient|*[TimePeriodGradient](\docs\client\models\errors.md#timeperiodgradient)*|
|<span class="no-wrap-code">`time_period`</span>|Time period|*datetime*|
|<span class="no-wrap-code">`provider`</span>|Provider name|*string*|
|<span class="no-wrap-code">`quality_dimension`</span>|Data quality dimension|*string*|
|<span class="no-wrap-code">`table_comparison`</span>|Table comparison name|*string*|


___

## SensorReadoutsListModel
Sensor readout detailed results. Returned in the context of a single data group, with a supplied list of other data groups.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*|
|<span class="no-wrap-code">`check_display_name`</span>|Check display name|*string*|
|<span class="no-wrap-code">[`check_type`](\docs\client\models\table_comparisons.md#checktype)</span>|Check type|*[CheckType](\docs\client\models\table_comparisons.md#checktype)*|
|<span class="no-wrap-code">`check_hash`</span>|Check hash|*long*|
|<span class="no-wrap-code">`check_category`</span>|Check category name|*string*|
|<span class="no-wrap-code">`sensor_name`</span>|Sensor name|*string*|
|<span class="no-wrap-code">`data_group_names`</span>|List of data groups that have values for this sensor readout (list of time series)|*List[string]*|
|<span class="no-wrap-code">`data_group`</span>|Selected data group|*string*|
|<span class="no-wrap-code">`sensor_readout_entries`</span>|Sensor readout entries|*List[[SensorReadoutEntryModel](#sensorreadoutentrymodel)]*|


___

