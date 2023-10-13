
## CheckResultEntryModel  
Detailed results for a single check. Represent one row in the check results table.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|id|Check result ID.|string|
|check_hash|Check hash.|long|
|check_category|Check category name.|string|
|check_name|Check name.|string|
|check_display_name|Check display name.|string|
|check_type|Check type.|string|
|actual_value|Actual value.|double|
|expected_value|Expected value.|double|
|warning_lower_bound|Warning lower bound.|double|
|warning_upper_bound|Warning upper bound.|double|
|error_lower_bound|Error lower bound.|double|
|error_upper_bound|Error upper bound.|double|
|fatal_lower_bound|Fatal lower bound.|double|
|fatal_upper_bound|Fatal upper bound.|double|
|severity|Severity.|integer|
|column_name|Column name.|string|
|data_group|Data group.|string|
|duration_ms|Duration (ms).|integer|
|time_gradient|Time gradient.|string|
|time_period|Time period.|datetime|
|include_in_kpi|Include in KPI.|boolean|
|include_in_sla|Include in SLA.|boolean|
|provider|Provider.|string|
|quality_dimension|Quality dimension.|string|
|sensor_name|Sensor name.|string|


___  

## CheckResultSortOrder  
Enumeration of columns names on a {@link CheckResultEntryModel CheckResultEntryModel} that could be sorted.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|checkType<br/>severity<br/>executedAt<br/>sensorName<br/>checkDisplayName<br/>actualValue<br/>checkCategory<br/>checkHash<br/>checkName<br/>dataGroup<br/>expectedValue<br/>timePeriod<br/>qualityDimension<br/>timeGradient<br/>columnName<br/>|

___  

## IncidentIssueHistogramModel  
Model that returns histograms of the data quality issue occurrences related to a data quality incident.
 The dates in the daily histogram are using the default timezone of the DQOps server.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|


___  

## IncidentStatus  
Enumeration of the statuses used in the &quot;status&quot; field of the &quot;incidents&quot; table.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|acknowledged<br/>muted<br/>open<br/>resolved<br/>|

___  

## IncidentModel  
Data quality incident model shown on an incident details screen.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|incident_id|Incident ID - the primary key that identifies each data quality incident.|string|
|connection|Connection name affected by a data quality incident.|string|
|year|The year when the incident was first seen. This value is required to load an incident&#x27;s monthly partition.|integer|
|month|The month when the incident was first seen. This value is required to load an incident&#x27;s monthly partition.|integer|
|schema|Schema name affected by a data quality incident.|string|
|table|Table name affected by a data quality incident.|string|
|table_priority|Table priority of the table that was affected by a data quality incident.|integer|
|incident_hash|Data quality incident hash that identifies similar incidents on the same incident grouping level.|long|
|data_group|The data group that was affected by a data quality incident.|string|
|quality_dimension|The data quality dimension that was affected by a data quality incident.|string|
|check_category|The data quality check category that was affected by a data quality incident.|string|
|check_type|The data quality check type that was affected by a data quality incident.|string|
|check_name|The data quality check name that was affected by a data quality incident.|string|
|highest_severity|The highest failed check severity that was detected as part of this data quality incident. Possible values are: 1 - warning, 2 - error, 3 - fatal.|integer|
|minimum_severity|The minimum severity of the data quality incident, copied from the incident configuration at a connection or table at the time when the incident was first seen. Possible values are: 1 - warning, 2 - error, 3 - fatal.|integer|
|failed_checks_count|The total number of failed data quality checks that were seen when the incident was raised for the first time.|integer|
|issue_url|The link (url) to a ticket in an external system that is tracking this incident.|string|
|[status](\docs\client\models\incidents\#incidentstatus)|Incident status.|[IncidentStatus](\docs\client\models\incidents\#incidentstatus)|


___  

## IncidentSortOrder  
Incident sort order columns.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|dataGroup<br/>lastSeen<br/>firstSeen<br/>tablePriority<br/>qualityDimension<br/>checkName<br/>failedChecksCount<br/>table<br/>highestSeverity<br/>|

___  

## SortDirection  
REST api model sort direction.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|asc<br/>desc<br/>|

___  

