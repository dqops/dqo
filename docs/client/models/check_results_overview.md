# DQOps REST API check_results_overview models reference
The references of all objects used by [check_results_overview](../operations/check_results_overview.md) REST API operations are listed below.


## Instant



**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`seconds`</span>|:mm|*long*|
|<span class="no-wrap-code">`nanos`</span>|:mm|*integer*|


___

## CheckResultsOverviewDataModel
Check recent results overview. Returns the highest severity for the last several runs.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_hash`</span>|Check hash.|*long*|
|<span class="no-wrap-code">`check_category`</span>|Check category name.|*string*|
|<span class="no-wrap-code">`check_name`</span>|Check name.|*string*|
|<span class="no-wrap-code">`comparison_name`</span>|Optional table comparison name for table comparison checks only.|*string*|
|<span class="no-wrap-code">`time_periods`</span>|List of time periods for the results, returned as a local time, sorted from the newest to the oldest.|*List[datetime]*|
|<span class="no-wrap-code">`time_periods_utc`</span>|List of time periods for the results, returned as absolute UTC time.|*List[[Instant](#instant)]*|
|<span class="no-wrap-code">`executed_at_timestamps`</span>|List of absolute timestamp (UTC) when the check was executed or an error was raised.|*List[[Instant](#instant)]*|
|<span class="no-wrap-code">`time_period_display_texts`</span>|List of time periods, sorted descending, returned as a text with a possible time zone.|*List[string]*|
|<span class="no-wrap-code">`statuses`</span>|List of check severity levels or an error status, indexes with the severity levels match the time periods.|*List[[CheckResultStatus](./check_results.md#checkresultstatus)]*|
|<span class="no-wrap-code">`data_groups`</span>|List of data group names. Identifies the data group with the highest severity or error result.|*List[string]*|
|<span class="no-wrap-code">`results`</span>|List of sensor results. Returns the data quality result readout for the data group with the alert of the highest severity level.|*List[double]*|


___

