
## find_connection_incident_stats  
Returns a list of connection names with incident statistics - the count of recent open incidents.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/find_connection_incident_stats.py)
  

**GET**
```
api/incidentstat  
```





___  

## find_recent_incidents_on_connection  
Returns a list of recent data quality incidents.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/find_recent_incidents_on_connection.py)
  

**GET**
```
api/incidents/{connectionName}  
```





___  

## get_incident  
Return a single data quality incident&#x27;s details.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/get_incident.py)
  

**GET**
```
api/incidents/{connectionName}/{year}/{month}/{incidentId}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[incident_model](\docs\client\operations\incidents\#incidentmodel)||[IncidentModel](\docs\client\operations\incidents\#incidentmodel)|






___  

## get_incident_histogram  
Generates histograms of data quality issues for each day, returning the number of data quality issues on that day. The other histograms are by a column name and by a check name.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/get_incident_histogram.py)
  

**GET**
```
api/incidents/{connectionName}/{year}/{month}/{incidentId}/histogram  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[incident_issue_histogram_model](\docs\client\operations\incidents\#incidentissuehistogrammodel)||[IncidentIssueHistogramModel](\docs\client\operations\incidents\#incidentissuehistogrammodel)|






___  

## get_incident_issues  
Return a paged list of failed data quality check results that are related to an incident.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/get_incident_issues.py)
  

**GET**
```
api/incidents/{connectionName}/{year}/{month}/{incidentId}/issues  
```





___  

## set_incident_issue_url  
Changes the incident&#x27;s issueUrl to a new status.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/set_incident_issue_url.py)
  

**POST**
```
api/incidents/{connectionName}/{year}/{month}/{incidentId}/issueurl  
```





___  

## set_incident_status  
Changes the incident&#x27;s status to a new status.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/set_incident_status.py)
  

**POST**
```
api/incidents/{connectionName}/{year}/{month}/{incidentId}/status  
```





___  

___  

## IncidentStatus  
Enumeration of the statuses used in the &quot;status&quot; field of the &quot;incidents&quot; table.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|name||string| | | |
|ordinal||integer| | | |

___  

## IncidentIssueHistogramModel  
Model that returns histograms of the data quality issue occurrences related to a data quality incident.
 The dates in the daily histogram are using the default timezone of the DQO server.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|

___  

## CheckResultEntryModel  
Detailed results for a single check. Represent one row in the check results table.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|id|Check result ID.|string| | | |
|check_hash|Check hash.|long| | | |
|check_category|Check category name.|string| | | |
|check_name|Check name.|string| | | |
|check_display_name|Check display name.|string| | | |
|check_type|Check type.|string| | | |
|actual_value|Actual value.|double| | | |
|expected_value|Expected value.|double| | | |
|warning_lower_bound|Warning lower bound.|double| | | |
|warning_upper_bound|Warning upper bound.|double| | | |
|error_lower_bound|Error lower bound.|double| | | |
|error_upper_bound|Error upper bound.|double| | | |
|fatal_lower_bound|Fatal lower bound.|double| | | |
|fatal_upper_bound|Fatal upper bound.|double| | | |
|severity|Severity.|integer| | | |
|column_name|Column name.|string| | | |
|data_group|Data group.|string| | | |
|duration_ms|Duration (ms).|integer| | | |
|time_gradient|Time gradient.|string| | | |
|time_period|Time period.|datetime| | | |
|include_in_kpi|Include in KPI.|boolean| | | |
|include_in_sla|Include in SLA.|boolean| | | |
|provider|Provider.|string| | | |
|quality_dimension|Quality dimension.|string| | | |
|sensor_name|Sensor name.|string| | | |

___  

## CheckResultSortOrder  
Enumeration of columns names on a {@link CheckResultEntryModel CheckResultEntryModel} that could be sorted.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|name||string| | | |
|ordinal||integer| | | |

___  

## SortDirection  
REST api model sort direction.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|name||string| | | |
|ordinal||integer| | | |

___  

## IncidentSortOrder  
Incident sort order columns.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|name||string| | | |
|ordinal||integer| | | |

___  

## IncidentModel  
Data quality incident model shown on an incident details screen.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|incident_id|Incident ID - the primary key that identifies each data quality incident.|string| | | |
|connection|Connection name affected by a data quality incident.|string| | | |
|year|The year when the incident was first seen. This value is required to load an incident&#x27;s monthly partition.|integer| | | |
|month|The month when the incident was first seen. This value is required to load an incident&#x27;s monthly partition.|integer| | | |
|schema|Schema name affected by a data quality incident.|string| | | |
|table|Table name affected by a data quality incident.|string| | | |
|table_priority|Table priority of the table that was affected by a data quality incident.|integer| | | |
|incident_hash|Data quality incident hash that identifies similar incidents on the same incident grouping level.|long| | | |
|data_group|The data group that was affected by a data quality incident.|string| | | |
|quality_dimension|The data quality dimension that was affected by a data quality incident.|string| | | |
|check_category|The data quality check category that was affected by a data quality incident.|string| | | |
|check_type|The data quality check type that was affected by a data quality incident.|string| | | |
|check_name|The data quality check name that was affected by a data quality incident.|string| | | |
|highest_severity|The highest failed check severity that was detected as part of this data quality incident. Possible values are: 1 - warning, 2 - error, 3 - fatal.|integer| | | |
|minimum_severity|The minimum severity of the data quality incident, copied from the incident configuration at a connection or table at the time when the incident was first seen. Possible values are: 1 - warning, 2 - error, 3 - fatal.|integer| | | |
|failed_checks_count|The total number of failed data quality checks that were seen when the incident was raised for the first time.|integer| | | |
|issue_url|The link (url) to a ticket in an external system that is tracking this incident.|string| | | |
|status|Incident status.|enum|acknowledged<br/>muted<br/>open<br/>resolved<br/>| | |

___  

