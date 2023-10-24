
## Instant  
  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|seconds||long|
|nanos||integer|


___  

## CheckResultsOverviewDataModel  
Check recent results overview. Returns the highest severity for the last several runs.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_hash|Check hash.|long|
|check_category|Check category name.|string|
|check_name|Check name.|string|
|time_periods|List of time periods for the results, returned as a local time, sorted from the newest to the oldest.|List[datetime]|
|time_periods_utc|List of time periods for the results, returned as absolute UTC time.|List[[Instant](#instant)]|
|executed_at_timestamps|List of absolute timestamp (UTC) when the check was executed or an error was raised.|List[[Instant](#instant)]|
|time_period_display_texts|List of time periods, sorted descending, returned as a text with a possible time zone.|string_list|
|statuses|List of check severity levels or an error status, indexes with the severity levels match the time periods.|List[[CheckResultStatus](\docs\client\models\check_results\#checkresultstatus)]|
|data_groups|List of data group names. Identifies the data group with the highest severity or error result.|string_list|
|results|List of sensor results. Returns the data quality result readout for the data group with the alert of the highest severity level.|List[double]|


___  

