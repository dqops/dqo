---
title: DQOps REST API error_samples models reference
---
# DQOps REST API error_samples models reference
The references of all objects used by [error_samples](../operations/error_samples.md) REST API operations are listed below.


## ErrorSampleResultDataType
Enumeration of data types that were detected as the error sample collector result.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|null<br/>boolean<br/>string<br/>integer<br/>float<br/>date<br/>datetime<br/>instant<br/>time<br/>|

___

## TimePeriodGradient
Time series gradient type (daily, monthly, quarterly, monthly, weekly, hourly).


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|year<br/>quarter<br/>month<br/>week<br/>day<br/>hour<br/>millisecond<br/>|

___

## ErrorSampleEntryModel
Detailed error samples captured for a single check. Represent one row in the error_samples table.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`id`</span>|Unique ID of the error sample value|*string*|
|<span class="no-wrap-code">[`result_data_type`](#errorsampleresultdatatype)</span>|Error sample result data type|*[ErrorSampleResultDataType](#errorsampleresultdatatype)*|
|<span class="no-wrap-code">`collected_at`</span>|The local timestamp when the error sample was collected|*datetime*|
|<span class="no-wrap-code">`sample_index`</span>|The index of the result that was returned. Identifies a single error sample within a list.|*integer*|
|<span class="no-wrap-code">[`check_type`](./common.md#checktype)</span>|Check type|*[CheckType](./common.md#checktype)*|
|<span class="no-wrap-code">[`time_gradient`](#timeperiodgradient)</span>|Time gradient|*[TimePeriodGradient](#timeperiodgradient)*|
|<span class="no-wrap-code">`duration_ms`</span>|Execution duration (ms)|*integer*|
|<span class="no-wrap-code">`row_id1`</span>|The value of the first column of the unique identifier that identifies a row containing the error sample.|*string*|
|<span class="no-wrap-code">`row_id2`</span>|The value of the second column of the unique identifier that identifies a row containing the error sample.|*string*|
|<span class="no-wrap-code">`row_id3`</span>|The value of the third column of the unique identifier that identifies a row containing the error sample.|*string*|
|<span class="no-wrap-code">`row_id4`</span>|The value of the fourth column of the unique identifier that identifies a row containing the error sample.|*string*|
|<span class="no-wrap-code">`row_id5`</span>|The value of the fifth column of the unique identifier that identifies a row containing the error sample.|*string*|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|
|<span class="no-wrap-code">`data_group`</span>|Data group|*string*|
|<span class="no-wrap-code">`provider`</span>|Provider name|*string*|
|<span class="no-wrap-code">`quality_dimension`</span>|Data quality dimension|*string*|
|<span class="no-wrap-code">`sensor_name`</span>|Sensor name|*string*|
|<span class="no-wrap-code">`table_comparison`</span>|Table comparison name|*string*|


___

## ErrorSamplesListModel
List of error samples for a single check. Returned in the context of a single data group, with a supplied list of other data groups.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*|
|<span class="no-wrap-code">`check_display_name`</span>|Check display name|*string*|
|<span class="no-wrap-code">[`check_type`](./common.md#checktype)</span>|Check type|*[CheckType](./common.md#checktype)*|
|<span class="no-wrap-code">`check_hash`</span>|Check hash|*long*|
|<span class="no-wrap-code">`check_category`</span>|Check category name|*string*|
|<span class="no-wrap-code">`data_groups_names`</span>|Data groups list|*List[string]*|
|<span class="no-wrap-code">`data_group`</span>|Selected data group|*string*|
|<span class="no-wrap-code">`error_samples_entries`</span>|Error samples entries|*List[[ErrorSampleEntryModel](#errorsampleentrymodel)]*|


___

