
## CheckResultsOverviewDataModel  
Check recent results overview. Returns the highest severity for the last several runs.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_hash|Check hash.|long|
|check_category|Check category name.|string|
|check_name|Check name.|string|
|time_period_display_texts|List of time periods, sorted descending, returned as a text with a possible time zone.|string_list|
|data_groups|List of data group names. Identifies the data group with the highest severity or error result.|string_list|


___  

