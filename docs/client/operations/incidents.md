Data quality incidents controller that supports loading incidents and changing the status of an incident.  


___  
## find_connection_incident_stats  
Returns a list of connection names with incident statistics - the count of recent open incidents.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/find_connection_incident_stats.py)
  

**GET**
```
http://localhost:8888/api/incidentstat  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[incidents_per_connection_model]()||[IncidentsPerConnectionModel]()|







___  
## find_recent_incidents_on_connection  
Returns a list of recent data quality incidents.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/find_recent_incidents_on_connection.py)
  

**GET**
```
http://localhost:8888/api/incidents/{connectionName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[incident_model](\docs\client\models\incidents\#incidentmodel)||[IncidentModel](\docs\client\models\incidents\#incidentmodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|months|Number of recent months to load, the default is 3 months|long|false|
|open|Returns open incidents, when the parameter is missing, the default value is true|boolean|false|
|acknowledged|Returns acknowledged incidents, when the parameter is missing, the default value is true|boolean|false|
|resolved|Returns resolved incidents, when the parameter is missing, the default value is false|boolean|false|
|muted|Returns muted incidents, when the parameter is missing, the default value is false|boolean|false|
|page|Page number, the first page is 1|long|false|
|limit|Page size, the default is 50 rows|long|false|
|filter|Optional full text search filter that supports *prefix, suffix* and nest*ed filter expressions|string|false|
|[order](\docs\client\models\incidents\#incidentsortorder)|Optional sort order, the default sort order is by the number of failed data quality checks|[IncidentSortOrder](\docs\client\models\incidents\#incidentsortorder)|false|
|[direction](\docs\client\models\incidents\#sortdirection)|Optional sort direction, the default sort direction is ascending|[SortDirection](\docs\client\models\incidents\#sortdirection)|false|





___  
## get_incident  
Return a single data quality incident&#x27;s details.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/get_incident.py)
  

**GET**
```
http://localhost:8888/api/incidents/{connectionName}/{year}/{month}/{incidentId}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[incident_model](\docs\client\models\incidents\#incidentmodel)||[IncidentModel](\docs\client\models\incidents\#incidentmodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|year|Year when the incident was first seen|long|true|
|month|Month when the incident was first seen|long|true|
|incident_id|Incident id|string|true|





___  
## get_incident_histogram  
Generates histograms of data quality issues for each day, returning the number of data quality issues on that day. The other histograms are by a column name and by a check name.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/get_incident_histogram.py)
  

**GET**
```
http://localhost:8888/api/incidents/{connectionName}/{year}/{month}/{incidentId}/histogram  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[incident_issue_histogram_model](\docs\client\models\incidents\#incidentissuehistogrammodel)||[IncidentIssueHistogramModel](\docs\client\models\incidents\#incidentissuehistogrammodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|year|Year when the incident was first seen|long|true|
|month|Month when the incident was first seen|long|true|
|incident_id|Incident id|string|true|
|filter|Optional full text search filter that supports *prefix, suffix* and nest*ed filter expressions|string|false|
|days|Optional filter for a number of recent days to read the related issues|long|false|
|date|Optional date filter|string|false|
|column|Optional column name filter|string|false|
|check|Optional check name filter|string|false|





___  
## get_incident_issues  
Return a paged list of failed data quality check results that are related to an incident.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/get_incident_issues.py)
  

**GET**
```
http://localhost:8888/api/incidents/{connectionName}/{year}/{month}/{incidentId}/issues  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_result_entry_model](\docs\client\models\incidents\#checkresultentrymodel)||[CheckResultEntryModel](\docs\client\models\incidents\#checkresultentrymodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|year|Year when the incident was first seen|long|true|
|month|Month when the incident was first seen|long|true|
|incident_id|Incident id|string|true|
|page|Page number, the first page is 1|long|false|
|limit|Page size, the default is 50 rows|long|false|
|filter|Optional filter|string|false|
|days|Optional filter for a number of recent days to read the related issues|long|false|
|date|Optional filter to return data quality issues only for a given date. The date should be an ISO8601 formatted date, it is treated as the timezone of the DQOps server.|string|false|
|column|Optional column name filter|string|false|
|check|Optional check name filter|string|false|
|[order](\docs\client\models\incidents\#checkresultsortorder)|Optional sort order, the default sort order is by the execution date|[CheckResultSortOrder](\docs\client\models\incidents\#checkresultsortorder)|false|
|[direction](\docs\client\models\incidents\#sortdirection)|Optional sort direction, the default sort direction is ascending|[SortDirection](\docs\client\models\incidents\#sortdirection)|false|





___  
## set_incident_issue_url  
Changes the incident&#x27;s issueUrl to a new status.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/set_incident_issue_url.py)
  

**POST**
```
http://localhost:8888/api/incidents/{connectionName}/{year}/{month}/{incidentId}/issueurl  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|year|Year when the incident was first seen|long|true|
|month|Month when the incident was first seen|long|true|
|incident_id|Incident id|string|true|
|issue_url|New incident&#x27;s issueUrl|string|true|





___  
## set_incident_status  
Changes the incident&#x27;s status to a new status.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/set_incident_status.py)
  

**POST**
```
http://localhost:8888/api/incidents/{connectionName}/{year}/{month}/{incidentId}/status  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|year|Year when the incident was first seen|long|true|
|month|Month when the incident was first seen|long|true|
|incident_id|Incident id|string|true|
|[status](\docs\client\models\incidents\#incidentstatus)|New incident status, supported values: open, acknowledged, resolved, muted|[IncidentStatus](\docs\client\models\incidents\#incidentstatus)|true|





