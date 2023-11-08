
## IncidentNotificationMessage  
Notification message payload that is posted (HTTP POST) to a notification endpoint with the details of a new or updated data quality incident.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|incident_id|Incident ID - the primary key that identifies each data quality incident.|string| | | |
|connection|Connection name affected by a data quality incident.|string| | | |
|schema|Schema name affected by a data quality incident.|string| | | |
|table|Table name affected by a data quality incident.|string| | | |
|table_priority|Table priority of the table that was affected by a data quality incident.|integer| | | |
|incident_hash|Data quality incident hash that identifies similar incidents on the same incident grouping level.|long| | | |
|data_stream_name|The data stream name that was affected by a data quality incident.|string| | | |
|quality_dimension|The data quality dimension that was affected by a data quality incident.|string| | | |
|check_category|The data quality check category that was affected by a data quality incident.|string| | | |
|check_type|The data quality check type that was affected by a data quality incident.|string| | | |
|check_name|The data quality check name that was affected by a data quality incident.|string| | | |
|highest_severity|The highest failed check severity that was detected as part of this data quality incident. Possible values are: 1 - warning, 2 - error, 3 - fatal.|integer| | | |
|failed_checks_count|The total number of failed data quality checks that were seen when the incident was raised for the first time.|integer| | | |
|issue_url|The link (url) to a ticket in an external system that is tracking this incident.|string| | | |
|status|Incident status.|enum|acknowledged<br/>muted<br/>open<br/>resolved<br/>| | |
|text|Notification text in Markdown format that contains the most important fields from the class.|string| | | |









___  

