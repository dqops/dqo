---
title: DQOps REST API incidents operations
---
# DQOps REST API incidents operations
Data quality incidents controller that supports reading and updating data quality incidents, such as changing the incident status or assigning an external ticket number.


___
## disable_checks_for_incident
Disables all data quality checks that caused a given data quality incident.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/disable_checks_for_incident.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/incidents/{connectionName}/{year}/{month}/{incidentId}/checks/disable
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`year`</span>|Year when the incident was first seen|*long*|:material-check-bold:|
|<span class="no-wrap-code">`month`</span>|Month when the incident was first seen|*long*|:material-check-bold:|
|<span class="no-wrap-code">`incident_id`</span>|Incident id|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/incidents/sample_connection/2007/10/sample_incident/checks/disable^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import disable_checks_for_incident
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = disable_checks_for_incident.sync(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import disable_checks_for_incident
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await disable_checks_for_incident.asyncio(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import disable_checks_for_incident
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = disable_checks_for_incident.sync(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import disable_checks_for_incident
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await disable_checks_for_incident.asyncio(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    



___
## find_connection_incident_stats
Returns a list of connection names with incident statistics - the count of recent open incidents.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/find_connection_incident_stats.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/incidentstat
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`incidents_per_connection_model`</span>||*List[[IncidentsPerConnectionModel](../models/incidents.md#incidentsperconnectionmodel)]*|








**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/incidentstat^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "connection" : "datalake",
		  "openIncidents" : 40,
		  "mostRecentFirstSeen" : "2024-06-01T11:45:22Z"
		}, {
		  "connection" : "datalake",
		  "openIncidents" : 40,
		  "mostRecentFirstSeen" : "2024-06-01T11:45:22Z"
		}, {
		  "connection" : "datalake",
		  "openIncidents" : 40,
		  "mostRecentFirstSeen" : "2024-06-01T11:45:22Z"
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import find_connection_incident_stats
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = find_connection_incident_stats.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			IncidentsPerConnectionModel(
				connection='datalake',
				open_incidents=40,
				most_recent_first_seen='2024-06-01T11:45:22Z'
			),
			IncidentsPerConnectionModel(
				connection='datalake',
				open_incidents=40,
				most_recent_first_seen='2024-06-01T11:45:22Z'
			),
			IncidentsPerConnectionModel(
				connection='datalake',
				open_incidents=40,
				most_recent_first_seen='2024-06-01T11:45:22Z'
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import find_connection_incident_stats
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await find_connection_incident_stats.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			IncidentsPerConnectionModel(
				connection='datalake',
				open_incidents=40,
				most_recent_first_seen='2024-06-01T11:45:22Z'
			),
			IncidentsPerConnectionModel(
				connection='datalake',
				open_incidents=40,
				most_recent_first_seen='2024-06-01T11:45:22Z'
			),
			IncidentsPerConnectionModel(
				connection='datalake',
				open_incidents=40,
				most_recent_first_seen='2024-06-01T11:45:22Z'
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import find_connection_incident_stats
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = find_connection_incident_stats.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			IncidentsPerConnectionModel(
				connection='datalake',
				open_incidents=40,
				most_recent_first_seen='2024-06-01T11:45:22Z'
			),
			IncidentsPerConnectionModel(
				connection='datalake',
				open_incidents=40,
				most_recent_first_seen='2024-06-01T11:45:22Z'
			),
			IncidentsPerConnectionModel(
				connection='datalake',
				open_incidents=40,
				most_recent_first_seen='2024-06-01T11:45:22Z'
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import find_connection_incident_stats
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await find_connection_incident_stats.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			IncidentsPerConnectionModel(
				connection='datalake',
				open_incidents=40,
				most_recent_first_seen='2024-06-01T11:45:22Z'
			),
			IncidentsPerConnectionModel(
				connection='datalake',
				open_incidents=40,
				most_recent_first_seen='2024-06-01T11:45:22Z'
			),
			IncidentsPerConnectionModel(
				connection='datalake',
				open_incidents=40,
				most_recent_first_seen='2024-06-01T11:45:22Z'
			)
		]
        ```
    
    
    



___
## find_recent_incidents_on_connection
Returns a list of recent data quality incidents.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/find_recent_incidents_on_connection.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/incidents/{connectionName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`incident_model`</span>||*List[[IncidentModel](../models/incidents.md#incidentmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`months`</span>|Number of recent months to load, the default is 3 months|*long*| |
|<span class="no-wrap-code">`open`</span>|Returns open incidents, when the parameter is missing, the default value is true|*boolean*| |
|<span class="no-wrap-code">`acknowledged`</span>|Returns acknowledged incidents, when the parameter is missing, the default value is true|*boolean*| |
|<span class="no-wrap-code">`resolved`</span>|Returns resolved incidents, when the parameter is missing, the default value is false|*boolean*| |
|<span class="no-wrap-code">`muted`</span>|Returns muted incidents, when the parameter is missing, the default value is false|*boolean*| |
|<span class="no-wrap-code">`page`</span>|Page number, the first page is 1|*long*| |
|<span class="no-wrap-code">`limit`</span>|Page size, the default is 50 rows|*long*| |
|<span class="no-wrap-code">`filter`</span>|Optional full text search filter that supports *prefix, suffix* and nest*ed filter expressions|*string*| |
|<span class="no-wrap-code">[`order`](../models/incidents.md#incidentsortorder)</span>|Optional sort order, the default sort order is by the number of failed data quality checks|*[IncidentSortOrder](../models/incidents.md#incidentsortorder)*| |
|<span class="no-wrap-code">[`direction`](../models/incidents.md#sortdirection)</span>|Optional sort direction, the default sort direction is ascending|*[SortDirection](../models/incidents.md#sortdirection)*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/incidents/sample_connection^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "incidentId" : "c05e6544-46e5-47ed-b8c2-b72927199976",
		  "connection" : "datalake",
		  "year" : 2024,
		  "month" : 6,
		  "schema" : "public",
		  "table" : "fact_sales",
		  "firstSeen" : "2024-06-01T11:45:22Z",
		  "qualityDimension" : "Completeness",
		  "highestSeverity" : 2,
		  "minimumSeverity" : 0,
		  "failedChecksCount" : 5,
		  "status" : "open"
		}, {
		  "incidentId" : "c05e6544-46e5-47ed-b8c2-b72927199976",
		  "connection" : "datalake",
		  "year" : 2024,
		  "month" : 6,
		  "schema" : "public",
		  "table" : "fact_sales",
		  "firstSeen" : "2024-06-01T11:45:22Z",
		  "qualityDimension" : "Completeness",
		  "highestSeverity" : 2,
		  "minimumSeverity" : 0,
		  "failedChecksCount" : 5,
		  "status" : "open"
		}, {
		  "incidentId" : "c05e6544-46e5-47ed-b8c2-b72927199976",
		  "connection" : "datalake",
		  "year" : 2024,
		  "month" : 6,
		  "schema" : "public",
		  "table" : "fact_sales",
		  "firstSeen" : "2024-06-01T11:45:22Z",
		  "qualityDimension" : "Completeness",
		  "highestSeverity" : 2,
		  "minimumSeverity" : 0,
		  "failedChecksCount" : 5,
		  "status" : "open"
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import find_recent_incidents_on_connection
	from dqops.client.models import IncidentSortOrder, \
	                                SortDirection
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = find_recent_incidents_on_connection.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			IncidentModel(
				incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
				connection='datalake',
				year=2024,
				month=6,
				schema='public',
				table='fact_sales',
				first_seen='2024-06-01T11:45:22Z',
				quality_dimension='Completeness',
				highest_severity=2,
				minimum_severity=0,
				failed_checks_count=5,
				status=IncidentStatus.OPEN
			),
			IncidentModel(
				incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
				connection='datalake',
				year=2024,
				month=6,
				schema='public',
				table='fact_sales',
				first_seen='2024-06-01T11:45:22Z',
				quality_dimension='Completeness',
				highest_severity=2,
				minimum_severity=0,
				failed_checks_count=5,
				status=IncidentStatus.OPEN
			),
			IncidentModel(
				incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
				connection='datalake',
				year=2024,
				month=6,
				schema='public',
				table='fact_sales',
				first_seen='2024-06-01T11:45:22Z',
				quality_dimension='Completeness',
				highest_severity=2,
				minimum_severity=0,
				failed_checks_count=5,
				status=IncidentStatus.OPEN
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import find_recent_incidents_on_connection
	from dqops.client.models import IncidentSortOrder, \
	                                SortDirection
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await find_recent_incidents_on_connection.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			IncidentModel(
				incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
				connection='datalake',
				year=2024,
				month=6,
				schema='public',
				table='fact_sales',
				first_seen='2024-06-01T11:45:22Z',
				quality_dimension='Completeness',
				highest_severity=2,
				minimum_severity=0,
				failed_checks_count=5,
				status=IncidentStatus.OPEN
			),
			IncidentModel(
				incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
				connection='datalake',
				year=2024,
				month=6,
				schema='public',
				table='fact_sales',
				first_seen='2024-06-01T11:45:22Z',
				quality_dimension='Completeness',
				highest_severity=2,
				minimum_severity=0,
				failed_checks_count=5,
				status=IncidentStatus.OPEN
			),
			IncidentModel(
				incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
				connection='datalake',
				year=2024,
				month=6,
				schema='public',
				table='fact_sales',
				first_seen='2024-06-01T11:45:22Z',
				quality_dimension='Completeness',
				highest_severity=2,
				minimum_severity=0,
				failed_checks_count=5,
				status=IncidentStatus.OPEN
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import find_recent_incidents_on_connection
	from dqops.client.models import IncidentSortOrder, \
	                                SortDirection
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = find_recent_incidents_on_connection.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			IncidentModel(
				incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
				connection='datalake',
				year=2024,
				month=6,
				schema='public',
				table='fact_sales',
				first_seen='2024-06-01T11:45:22Z',
				quality_dimension='Completeness',
				highest_severity=2,
				minimum_severity=0,
				failed_checks_count=5,
				status=IncidentStatus.OPEN
			),
			IncidentModel(
				incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
				connection='datalake',
				year=2024,
				month=6,
				schema='public',
				table='fact_sales',
				first_seen='2024-06-01T11:45:22Z',
				quality_dimension='Completeness',
				highest_severity=2,
				minimum_severity=0,
				failed_checks_count=5,
				status=IncidentStatus.OPEN
			),
			IncidentModel(
				incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
				connection='datalake',
				year=2024,
				month=6,
				schema='public',
				table='fact_sales',
				first_seen='2024-06-01T11:45:22Z',
				quality_dimension='Completeness',
				highest_severity=2,
				minimum_severity=0,
				failed_checks_count=5,
				status=IncidentStatus.OPEN
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import find_recent_incidents_on_connection
	from dqops.client.models import IncidentSortOrder, \
	                                SortDirection
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await find_recent_incidents_on_connection.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			IncidentModel(
				incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
				connection='datalake',
				year=2024,
				month=6,
				schema='public',
				table='fact_sales',
				first_seen='2024-06-01T11:45:22Z',
				quality_dimension='Completeness',
				highest_severity=2,
				minimum_severity=0,
				failed_checks_count=5,
				status=IncidentStatus.OPEN
			),
			IncidentModel(
				incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
				connection='datalake',
				year=2024,
				month=6,
				schema='public',
				table='fact_sales',
				first_seen='2024-06-01T11:45:22Z',
				quality_dimension='Completeness',
				highest_severity=2,
				minimum_severity=0,
				failed_checks_count=5,
				status=IncidentStatus.OPEN
			),
			IncidentModel(
				incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
				connection='datalake',
				year=2024,
				month=6,
				schema='public',
				table='fact_sales',
				first_seen='2024-06-01T11:45:22Z',
				quality_dimension='Completeness',
				highest_severity=2,
				minimum_severity=0,
				failed_checks_count=5,
				status=IncidentStatus.OPEN
			)
		]
        ```
    
    
    



___
## find_top_incidents_grouped
Finds the most recent incidents grouped by one of the incident&#x27;s attribute, such as a data quality dimension, a data quality check category or the connection name.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/find_top_incidents_grouped.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/topincidents
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`top_incidents_model`](../models/incidents.md#topincidentsmodel)</span>||*[TopIncidentsModel](../models/incidents.md#topincidentsmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">[`status`](../models/incidents.md#incidentstatus)</span>|Incident status to group. When this parameter is missing, the &#x27;open&#x27; (new) incidents are grouped by default.|*[IncidentStatus](../models/incidents.md#incidentstatus)*| |
|<span class="no-wrap-code">[`group_by`](../models/incidents.md#topincidentgrouping)</span>|Incident grouping key. When this parameter is missing, returns incidents grouped by the data quality check category.|*[TopIncidentGrouping](../models/incidents.md#topincidentgrouping)*| |
|<span class="no-wrap-code">`limit`</span>|The result limit for each group. When this parameter is missing, returns the default limit of 10|*long*| |
|<span class="no-wrap-code">`limit`</span>|Optional filter to configure a time window before now to scan for incidents based on the incident&#x27;s first seen attribute.|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/topincidents^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "grouping" : "dimension",
		  "status" : "open",
		  "topIncidents" : {
		    "Completeness" : [ {
		      "incidentId" : "c05e6544-46e5-47ed-b8c2-b72927199976",
		      "connection" : "datalake",
		      "year" : 2024,
		      "month" : 6,
		      "schema" : "public",
		      "table" : "fact_sales",
		      "firstSeen" : "2024-06-01T11:45:22Z",
		      "qualityDimension" : "Completeness",
		      "highestSeverity" : 2,
		      "minimumSeverity" : 0,
		      "failedChecksCount" : 5,
		      "status" : "open"
		    } ]
		  }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import find_top_incidents_grouped
	from dqops.client.models import IncidentStatus, \
	                                TopIncidentGrouping
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = find_top_incidents_grouped.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TopIncidentsModel(
			grouping=TopIncidentGrouping.DIMENSION,
			status=IncidentStatus.OPEN,
			top_incidents={
				'Completeness': [
					IncidentModel(
						incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
						connection='datalake',
						year=2024,
						month=6,
						schema='public',
						table='fact_sales',
						first_seen='2024-06-01T11:45:22Z',
						quality_dimension='Completeness',
						highest_severity=2,
						minimum_severity=0,
						failed_checks_count=5,
						status=IncidentStatus.OPEN
					)
				]
			}
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import find_top_incidents_grouped
	from dqops.client.models import IncidentStatus, \
	                                TopIncidentGrouping
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await find_top_incidents_grouped.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TopIncidentsModel(
			grouping=TopIncidentGrouping.DIMENSION,
			status=IncidentStatus.OPEN,
			top_incidents={
				'Completeness': [
					IncidentModel(
						incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
						connection='datalake',
						year=2024,
						month=6,
						schema='public',
						table='fact_sales',
						first_seen='2024-06-01T11:45:22Z',
						quality_dimension='Completeness',
						highest_severity=2,
						minimum_severity=0,
						failed_checks_count=5,
						status=IncidentStatus.OPEN
					)
				]
			}
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import find_top_incidents_grouped
	from dqops.client.models import IncidentStatus, \
	                                TopIncidentGrouping
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = find_top_incidents_grouped.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TopIncidentsModel(
			grouping=TopIncidentGrouping.DIMENSION,
			status=IncidentStatus.OPEN,
			top_incidents={
				'Completeness': [
					IncidentModel(
						incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
						connection='datalake',
						year=2024,
						month=6,
						schema='public',
						table='fact_sales',
						first_seen='2024-06-01T11:45:22Z',
						quality_dimension='Completeness',
						highest_severity=2,
						minimum_severity=0,
						failed_checks_count=5,
						status=IncidentStatus.OPEN
					)
				]
			}
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import find_top_incidents_grouped
	from dqops.client.models import IncidentStatus, \
	                                TopIncidentGrouping
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await find_top_incidents_grouped.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TopIncidentsModel(
			grouping=TopIncidentGrouping.DIMENSION,
			status=IncidentStatus.OPEN,
			top_incidents={
				'Completeness': [
					IncidentModel(
						incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
						connection='datalake',
						year=2024,
						month=6,
						schema='public',
						table='fact_sales',
						first_seen='2024-06-01T11:45:22Z',
						quality_dimension='Completeness',
						highest_severity=2,
						minimum_severity=0,
						failed_checks_count=5,
						status=IncidentStatus.OPEN
					)
				]
			}
		)
        ```
    
    
    



___
## get_incident
Return a single data quality incident&#x27;s details.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/get_incident.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/incidents/{connectionName}/{year}/{month}/{incidentId}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`incident_model`](../models/incidents.md#incidentmodel)</span>||*[IncidentModel](../models/incidents.md#incidentmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`year`</span>|Year when the incident was first seen|*long*|:material-check-bold:|
|<span class="no-wrap-code">`month`</span>|Month when the incident was first seen|*long*|:material-check-bold:|
|<span class="no-wrap-code">`incident_id`</span>|Incident id|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/incidents/sample_connection/2007/10/sample_incident^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "incidentId" : "c05e6544-46e5-47ed-b8c2-b72927199976",
		  "connection" : "datalake",
		  "year" : 2024,
		  "month" : 6,
		  "schema" : "public",
		  "table" : "fact_sales",
		  "firstSeen" : "2024-06-01T11:45:22Z",
		  "qualityDimension" : "Completeness",
		  "highestSeverity" : 2,
		  "minimumSeverity" : 0,
		  "failedChecksCount" : 5,
		  "status" : "open"
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import get_incident
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_incident.sync(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        IncidentModel(
			incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
			connection='datalake',
			year=2024,
			month=6,
			schema='public',
			table='fact_sales',
			first_seen='2024-06-01T11:45:22Z',
			quality_dimension='Completeness',
			highest_severity=2,
			minimum_severity=0,
			failed_checks_count=5,
			status=IncidentStatus.OPEN
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import get_incident
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_incident.asyncio(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        IncidentModel(
			incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
			connection='datalake',
			year=2024,
			month=6,
			schema='public',
			table='fact_sales',
			first_seen='2024-06-01T11:45:22Z',
			quality_dimension='Completeness',
			highest_severity=2,
			minimum_severity=0,
			failed_checks_count=5,
			status=IncidentStatus.OPEN
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import get_incident
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_incident.sync(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        IncidentModel(
			incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
			connection='datalake',
			year=2024,
			month=6,
			schema='public',
			table='fact_sales',
			first_seen='2024-06-01T11:45:22Z',
			quality_dimension='Completeness',
			highest_severity=2,
			minimum_severity=0,
			failed_checks_count=5,
			status=IncidentStatus.OPEN
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import get_incident
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_incident.asyncio(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        IncidentModel(
			incident_id='c05e6544-46e5-47ed-b8c2-b72927199976',
			connection='datalake',
			year=2024,
			month=6,
			schema='public',
			table='fact_sales',
			first_seen='2024-06-01T11:45:22Z',
			quality_dimension='Completeness',
			highest_severity=2,
			minimum_severity=0,
			failed_checks_count=5,
			status=IncidentStatus.OPEN
		)
        ```
    
    
    



___
## get_incident_histogram
Generates histograms of data quality issues for each day, returning the number of data quality issues on that day. The other histograms are by a column name and by a check name.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/get_incident_histogram.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/incidents/{connectionName}/{year}/{month}/{incidentId}/histogram
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`incident_issue_histogram_model`](../models/incidents.md#incidentissuehistogrammodel)</span>||*[IncidentIssueHistogramModel](../models/incidents.md#incidentissuehistogrammodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`year`</span>|Year when the incident was first seen|*long*|:material-check-bold:|
|<span class="no-wrap-code">`month`</span>|Month when the incident was first seen|*long*|:material-check-bold:|
|<span class="no-wrap-code">`incident_id`</span>|Incident id|*string*|:material-check-bold:|
|<span class="no-wrap-code">`filter`</span>|Optional full text search filter that supports *prefix, suffix* and nest*ed filter expressions|*string*| |
|<span class="no-wrap-code">`days`</span>|Optional filter for a number of recent days to read the related issues|*long*| |
|<span class="no-wrap-code">`date`</span>|Optional date filter|*string*| |
|<span class="no-wrap-code">`column`</span>|Optional column name filter|*string*| |
|<span class="no-wrap-code">`check`</span>|Optional check name filter|*string*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/incidents/sample_connection/2007/10/sample_incident/histogram^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "hasProfilingIssues" : false,
		  "hasDailyMonitoringIssues" : false,
		  "hasMonthlyMonitoringIssues" : false,
		  "hasDailyPartitionedIssues" : false,
		  "hasMonthlyPartitionedIssues" : false,
		  "days" : { },
		  "columns" : { },
		  "checks" : { }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import get_incident_histogram
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_incident_histogram.sync(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        IncidentIssueHistogramModel(
			has_profiling_issues=False,
			has_daily_monitoring_issues=False,
			has_monthly_monitoring_issues=False,
			has_daily_partitioned_issues=False,
			has_monthly_partitioned_issues=False,
			days={
			
			},
			columns={
			
			},
			checks={
			
			}
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import get_incident_histogram
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_incident_histogram.asyncio(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        IncidentIssueHistogramModel(
			has_profiling_issues=False,
			has_daily_monitoring_issues=False,
			has_monthly_monitoring_issues=False,
			has_daily_partitioned_issues=False,
			has_monthly_partitioned_issues=False,
			days={
			
			},
			columns={
			
			},
			checks={
			
			}
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import get_incident_histogram
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_incident_histogram.sync(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        IncidentIssueHistogramModel(
			has_profiling_issues=False,
			has_daily_monitoring_issues=False,
			has_monthly_monitoring_issues=False,
			has_daily_partitioned_issues=False,
			has_monthly_partitioned_issues=False,
			days={
			
			},
			columns={
			
			},
			checks={
			
			}
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import get_incident_histogram
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_incident_histogram.asyncio(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        IncidentIssueHistogramModel(
			has_profiling_issues=False,
			has_daily_monitoring_issues=False,
			has_monthly_monitoring_issues=False,
			has_daily_partitioned_issues=False,
			has_monthly_partitioned_issues=False,
			days={
			
			},
			columns={
			
			},
			checks={
			
			}
		)
        ```
    
    
    



___
## get_incident_issues
Return a paged list of failed data quality check results that are related to an incident.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/get_incident_issues.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/incidents/{connectionName}/{year}/{month}/{incidentId}/issues
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_result_entry_model`</span>||*List[[CheckResultEntryModel](../models/incidents.md#checkresultentrymodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`year`</span>|Year when the incident was first seen|*long*|:material-check-bold:|
|<span class="no-wrap-code">`month`</span>|Month when the incident was first seen|*long*|:material-check-bold:|
|<span class="no-wrap-code">`incident_id`</span>|Incident id|*string*|:material-check-bold:|
|<span class="no-wrap-code">`page`</span>|Page number, the first page is 1|*long*| |
|<span class="no-wrap-code">`limit`</span>|Page size, the default is 50 rows|*long*| |
|<span class="no-wrap-code">`filter`</span>|Optional filter|*string*| |
|<span class="no-wrap-code">`days`</span>|Optional filter for a number of recent days to read the related issues|*long*| |
|<span class="no-wrap-code">`date`</span>|Optional filter to return data quality issues only for a given date. The date should be an ISO8601 formatted date, it is treated as the timezone of the DQOps server.|*string*| |
|<span class="no-wrap-code">`column`</span>|Optional column name filter|*string*| |
|<span class="no-wrap-code">`check`</span>|Optional check name filter|*string*| |
|<span class="no-wrap-code">[`order`](../models/incidents.md#checkresultsortorder)</span>|Optional sort order, the default sort order is by the execution date|*[CheckResultSortOrder](../models/incidents.md#checkresultsortorder)*| |
|<span class="no-wrap-code">[`direction`](../models/incidents.md#sortdirection)</span>|Optional sort direction, the default sort direction is ascending|*[SortDirection](../models/incidents.md#sortdirection)*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/incidents/sample_connection/2007/10/sample_incident/issues^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "id" : "3854372",
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "actualValue" : 100.0,
		  "expectedValue" : 110.0,
		  "warningLowerBound" : 105.0,
		  "warningUpperBound" : 115.0,
		  "errorLowerBound" : 95.0,
		  "errorUpperBound" : 125.0,
		  "fatalLowerBound" : 85.0,
		  "fatalUpperBound" : 135.0,
		  "severity" : 2,
		  "columnName" : "sample_column",
		  "dataGroup" : "sample_data_grouping",
		  "durationMs" : 142,
		  "executedAt" : "2023-10-01T14:00:00Z",
		  "timeGradient" : "hour",
		  "timePeriod" : "2023-10-01T14:00:00",
		  "includeInKpi" : true,
		  "includeInSla" : true,
		  "provider" : "BigQuery",
		  "qualityDimension" : "sample_quality_dimension",
		  "sensorName" : "sample_target/sample_category/sample_sensor"
		}, {
		  "id" : "3854372",
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "actualValue" : 100.0,
		  "expectedValue" : 110.0,
		  "warningLowerBound" : 105.0,
		  "warningUpperBound" : 115.0,
		  "errorLowerBound" : 95.0,
		  "errorUpperBound" : 125.0,
		  "fatalLowerBound" : 85.0,
		  "fatalUpperBound" : 135.0,
		  "severity" : 2,
		  "columnName" : "sample_column",
		  "dataGroup" : "sample_data_grouping",
		  "durationMs" : 142,
		  "executedAt" : "2023-10-01T14:00:00Z",
		  "timeGradient" : "hour",
		  "timePeriod" : "2023-10-01T14:00:00",
		  "includeInKpi" : true,
		  "includeInSla" : true,
		  "provider" : "BigQuery",
		  "qualityDimension" : "sample_quality_dimension",
		  "sensorName" : "sample_target/sample_category/sample_sensor"
		}, {
		  "id" : "3854372",
		  "checkHash" : 0,
		  "checkCategory" : "sample_category",
		  "checkName" : "sample_check",
		  "checkDisplayName" : "sample_target/sample_category/sample_check",
		  "checkType" : "profiling",
		  "actualValue" : 100.0,
		  "expectedValue" : 110.0,
		  "warningLowerBound" : 105.0,
		  "warningUpperBound" : 115.0,
		  "errorLowerBound" : 95.0,
		  "errorUpperBound" : 125.0,
		  "fatalLowerBound" : 85.0,
		  "fatalUpperBound" : 135.0,
		  "severity" : 2,
		  "columnName" : "sample_column",
		  "dataGroup" : "sample_data_grouping",
		  "durationMs" : 142,
		  "executedAt" : "2023-10-01T14:00:00Z",
		  "timeGradient" : "hour",
		  "timePeriod" : "2023-10-01T14:00:00",
		  "includeInKpi" : true,
		  "includeInSla" : true,
		  "provider" : "BigQuery",
		  "qualityDimension" : "sample_quality_dimension",
		  "sensorName" : "sample_target/sample_category/sample_sensor"
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import get_incident_issues
	from dqops.client.models import CheckResultSortOrder, \
	                                SortDirection
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_incident_issues.sync(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultEntryModel(
				id='3854372',
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				actual_value=100.0,
				expected_value=110.0,
				warning_lower_bound=105.0,
				warning_upper_bound=115.0,
				error_lower_bound=95.0,
				error_upper_bound=125.0,
				fatal_lower_bound=85.0,
				fatal_upper_bound=135.0,
				severity=2,
				column_name='sample_column',
				data_group='sample_data_grouping',
				duration_ms=142,
				executed_at='2023-10-01T14:00:00Z',
				time_gradient=TimePeriodGradient.HOUR,
				time_period=Some date/time value: [2023-10-01T14:00],
				include_in_kpi=True,
				include_in_sla=True,
				provider='BigQuery',
				quality_dimension='sample_quality_dimension',
				sensor_name='sample_target/sample_category/sample_sensor'
			),
			CheckResultEntryModel(
				id='3854372',
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				actual_value=100.0,
				expected_value=110.0,
				warning_lower_bound=105.0,
				warning_upper_bound=115.0,
				error_lower_bound=95.0,
				error_upper_bound=125.0,
				fatal_lower_bound=85.0,
				fatal_upper_bound=135.0,
				severity=2,
				column_name='sample_column',
				data_group='sample_data_grouping',
				duration_ms=142,
				executed_at='2023-10-01T14:00:00Z',
				time_gradient=TimePeriodGradient.HOUR,
				time_period=Some date/time value: [2023-10-01T14:00],
				include_in_kpi=True,
				include_in_sla=True,
				provider='BigQuery',
				quality_dimension='sample_quality_dimension',
				sensor_name='sample_target/sample_category/sample_sensor'
			),
			CheckResultEntryModel(
				id='3854372',
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				actual_value=100.0,
				expected_value=110.0,
				warning_lower_bound=105.0,
				warning_upper_bound=115.0,
				error_lower_bound=95.0,
				error_upper_bound=125.0,
				fatal_lower_bound=85.0,
				fatal_upper_bound=135.0,
				severity=2,
				column_name='sample_column',
				data_group='sample_data_grouping',
				duration_ms=142,
				executed_at='2023-10-01T14:00:00Z',
				time_gradient=TimePeriodGradient.HOUR,
				time_period=Some date/time value: [2023-10-01T14:00],
				include_in_kpi=True,
				include_in_sla=True,
				provider='BigQuery',
				quality_dimension='sample_quality_dimension',
				sensor_name='sample_target/sample_category/sample_sensor'
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import get_incident_issues
	from dqops.client.models import CheckResultSortOrder, \
	                                SortDirection
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_incident_issues.asyncio(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultEntryModel(
				id='3854372',
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				actual_value=100.0,
				expected_value=110.0,
				warning_lower_bound=105.0,
				warning_upper_bound=115.0,
				error_lower_bound=95.0,
				error_upper_bound=125.0,
				fatal_lower_bound=85.0,
				fatal_upper_bound=135.0,
				severity=2,
				column_name='sample_column',
				data_group='sample_data_grouping',
				duration_ms=142,
				executed_at='2023-10-01T14:00:00Z',
				time_gradient=TimePeriodGradient.HOUR,
				time_period=Some date/time value: [2023-10-01T14:00],
				include_in_kpi=True,
				include_in_sla=True,
				provider='BigQuery',
				quality_dimension='sample_quality_dimension',
				sensor_name='sample_target/sample_category/sample_sensor'
			),
			CheckResultEntryModel(
				id='3854372',
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				actual_value=100.0,
				expected_value=110.0,
				warning_lower_bound=105.0,
				warning_upper_bound=115.0,
				error_lower_bound=95.0,
				error_upper_bound=125.0,
				fatal_lower_bound=85.0,
				fatal_upper_bound=135.0,
				severity=2,
				column_name='sample_column',
				data_group='sample_data_grouping',
				duration_ms=142,
				executed_at='2023-10-01T14:00:00Z',
				time_gradient=TimePeriodGradient.HOUR,
				time_period=Some date/time value: [2023-10-01T14:00],
				include_in_kpi=True,
				include_in_sla=True,
				provider='BigQuery',
				quality_dimension='sample_quality_dimension',
				sensor_name='sample_target/sample_category/sample_sensor'
			),
			CheckResultEntryModel(
				id='3854372',
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				actual_value=100.0,
				expected_value=110.0,
				warning_lower_bound=105.0,
				warning_upper_bound=115.0,
				error_lower_bound=95.0,
				error_upper_bound=125.0,
				fatal_lower_bound=85.0,
				fatal_upper_bound=135.0,
				severity=2,
				column_name='sample_column',
				data_group='sample_data_grouping',
				duration_ms=142,
				executed_at='2023-10-01T14:00:00Z',
				time_gradient=TimePeriodGradient.HOUR,
				time_period=Some date/time value: [2023-10-01T14:00],
				include_in_kpi=True,
				include_in_sla=True,
				provider='BigQuery',
				quality_dimension='sample_quality_dimension',
				sensor_name='sample_target/sample_category/sample_sensor'
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import get_incident_issues
	from dqops.client.models import CheckResultSortOrder, \
	                                SortDirection
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_incident_issues.sync(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultEntryModel(
				id='3854372',
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				actual_value=100.0,
				expected_value=110.0,
				warning_lower_bound=105.0,
				warning_upper_bound=115.0,
				error_lower_bound=95.0,
				error_upper_bound=125.0,
				fatal_lower_bound=85.0,
				fatal_upper_bound=135.0,
				severity=2,
				column_name='sample_column',
				data_group='sample_data_grouping',
				duration_ms=142,
				executed_at='2023-10-01T14:00:00Z',
				time_gradient=TimePeriodGradient.HOUR,
				time_period=Some date/time value: [2023-10-01T14:00],
				include_in_kpi=True,
				include_in_sla=True,
				provider='BigQuery',
				quality_dimension='sample_quality_dimension',
				sensor_name='sample_target/sample_category/sample_sensor'
			),
			CheckResultEntryModel(
				id='3854372',
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				actual_value=100.0,
				expected_value=110.0,
				warning_lower_bound=105.0,
				warning_upper_bound=115.0,
				error_lower_bound=95.0,
				error_upper_bound=125.0,
				fatal_lower_bound=85.0,
				fatal_upper_bound=135.0,
				severity=2,
				column_name='sample_column',
				data_group='sample_data_grouping',
				duration_ms=142,
				executed_at='2023-10-01T14:00:00Z',
				time_gradient=TimePeriodGradient.HOUR,
				time_period=Some date/time value: [2023-10-01T14:00],
				include_in_kpi=True,
				include_in_sla=True,
				provider='BigQuery',
				quality_dimension='sample_quality_dimension',
				sensor_name='sample_target/sample_category/sample_sensor'
			),
			CheckResultEntryModel(
				id='3854372',
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				actual_value=100.0,
				expected_value=110.0,
				warning_lower_bound=105.0,
				warning_upper_bound=115.0,
				error_lower_bound=95.0,
				error_upper_bound=125.0,
				fatal_lower_bound=85.0,
				fatal_upper_bound=135.0,
				severity=2,
				column_name='sample_column',
				data_group='sample_data_grouping',
				duration_ms=142,
				executed_at='2023-10-01T14:00:00Z',
				time_gradient=TimePeriodGradient.HOUR,
				time_period=Some date/time value: [2023-10-01T14:00],
				include_in_kpi=True,
				include_in_sla=True,
				provider='BigQuery',
				quality_dimension='sample_quality_dimension',
				sensor_name='sample_target/sample_category/sample_sensor'
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import get_incident_issues
	from dqops.client.models import CheckResultSortOrder, \
	                                SortDirection
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_incident_issues.asyncio(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckResultEntryModel(
				id='3854372',
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				actual_value=100.0,
				expected_value=110.0,
				warning_lower_bound=105.0,
				warning_upper_bound=115.0,
				error_lower_bound=95.0,
				error_upper_bound=125.0,
				fatal_lower_bound=85.0,
				fatal_upper_bound=135.0,
				severity=2,
				column_name='sample_column',
				data_group='sample_data_grouping',
				duration_ms=142,
				executed_at='2023-10-01T14:00:00Z',
				time_gradient=TimePeriodGradient.HOUR,
				time_period=Some date/time value: [2023-10-01T14:00],
				include_in_kpi=True,
				include_in_sla=True,
				provider='BigQuery',
				quality_dimension='sample_quality_dimension',
				sensor_name='sample_target/sample_category/sample_sensor'
			),
			CheckResultEntryModel(
				id='3854372',
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				actual_value=100.0,
				expected_value=110.0,
				warning_lower_bound=105.0,
				warning_upper_bound=115.0,
				error_lower_bound=95.0,
				error_upper_bound=125.0,
				fatal_lower_bound=85.0,
				fatal_upper_bound=135.0,
				severity=2,
				column_name='sample_column',
				data_group='sample_data_grouping',
				duration_ms=142,
				executed_at='2023-10-01T14:00:00Z',
				time_gradient=TimePeriodGradient.HOUR,
				time_period=Some date/time value: [2023-10-01T14:00],
				include_in_kpi=True,
				include_in_sla=True,
				provider='BigQuery',
				quality_dimension='sample_quality_dimension',
				sensor_name='sample_target/sample_category/sample_sensor'
			),
			CheckResultEntryModel(
				id='3854372',
				check_hash=0,
				check_category='sample_category',
				check_name='sample_check',
				check_display_name='sample_target/sample_category/sample_check',
				check_type=CheckType.PROFILING,
				actual_value=100.0,
				expected_value=110.0,
				warning_lower_bound=105.0,
				warning_upper_bound=115.0,
				error_lower_bound=95.0,
				error_upper_bound=125.0,
				fatal_lower_bound=85.0,
				fatal_upper_bound=135.0,
				severity=2,
				column_name='sample_column',
				data_group='sample_data_grouping',
				duration_ms=142,
				executed_at='2023-10-01T14:00:00Z',
				time_gradient=TimePeriodGradient.HOUR,
				time_period=Some date/time value: [2023-10-01T14:00],
				include_in_kpi=True,
				include_in_sla=True,
				provider='BigQuery',
				quality_dimension='sample_quality_dimension',
				sensor_name='sample_target/sample_category/sample_sensor'
			)
		]
        ```
    
    
    



___
## recalibrate_checks_for_incident
Recalibrates all data quality checks that caused a given data quality incident to generate less issues by changing the data quality rule parameters.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/recalibrate_checks_for_incident.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/incidents/{connectionName}/{year}/{month}/{incidentId}/checks/recalibrate
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`year`</span>|Year when the incident was first seen|*long*|:material-check-bold:|
|<span class="no-wrap-code">`month`</span>|Month when the incident was first seen|*long*|:material-check-bold:|
|<span class="no-wrap-code">`incident_id`</span>|Incident id|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/incidents/sample_connection/2007/10/sample_incident/checks/recalibrate^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import recalibrate_checks_for_incident
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = recalibrate_checks_for_incident.sync(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import recalibrate_checks_for_incident
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await recalibrate_checks_for_incident.asyncio(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import recalibrate_checks_for_incident
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = recalibrate_checks_for_incident.sync(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import recalibrate_checks_for_incident
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await recalibrate_checks_for_incident.asyncio(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    



___
## set_incident_issue_url
Changes the incident&#x27;s issueUrl to a new status.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/set_incident_issue_url.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/incidents/{connectionName}/{year}/{month}/{incidentId}/issueurl
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`year`</span>|Year when the incident was first seen|*long*|:material-check-bold:|
|<span class="no-wrap-code">`month`</span>|Month when the incident was first seen|*long*|:material-check-bold:|
|<span class="no-wrap-code">`incident_id`</span>|Incident id|*string*|:material-check-bold:|
|<span class="no-wrap-code">`issue_url`</span>|New incident&#x27;s issueUrl|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/incidents/sample_connection/2007/10/sample_incident/issueurl^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import set_incident_issue_url
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = set_incident_issue_url.sync(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import set_incident_issue_url
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await set_incident_issue_url.asyncio(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import set_incident_issue_url
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = set_incident_issue_url.sync(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import set_incident_issue_url
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await set_incident_issue_url.asyncio(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    



___
## set_incident_status
Changes the incident&#x27;s status to a new status.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/incidents/set_incident_status.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/incidents/{connectionName}/{year}/{month}/{incidentId}/status
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`year`</span>|Year when the incident was first seen|*long*|:material-check-bold:|
|<span class="no-wrap-code">`month`</span>|Month when the incident was first seen|*long*|:material-check-bold:|
|<span class="no-wrap-code">`incident_id`</span>|Incident id|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`status`](../models/incidents.md#incidentstatus)</span>|New incident status, supported values: open, acknowledged, resolved, muted|*[IncidentStatus](../models/incidents.md#incidentstatus)*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/incidents/sample_connection/2007/10/sample_incident/status^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import set_incident_status
	from dqops.client.models import IncidentStatus
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = set_incident_status.sync(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import set_incident_status
	from dqops.client.models import IncidentStatus
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await set_incident_status.asyncio(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import set_incident_status
	from dqops.client.models import IncidentStatus
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = set_incident_status.sync(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.incidents import set_incident_status
	from dqops.client.models import IncidentStatus
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await set_incident_status.asyncio(
	    'sample_connection',
	    2007,
	    10,
	    'sample_incident',
	    client=dqops_client
	)
	
    ```

    



