# DQOps REST API incidents operations
Data quality incidents controller that supports reading and updating data quality incidents, such as changing the incident status or assigning an external ticket number.


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
|incidents_per_connection_model||List[[IncidentsPerConnectionModel](../models/incidents.md#incidentsperconnectionmodel)]|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/incidentstat^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

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

=== "Python async client"

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

=== "Python auth sync client"

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

=== "Python auth async client"

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




??? "Return value sample"
    ```js
    [ {
	  "openIncidents" : 0
	}, {
	  "openIncidents" : 0
	}, {
	  "openIncidents" : 0
	} ]
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
|incident_model||List[[IncidentModel](../models/incidents.md#incidentmodel)]|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|months|Number of recent months to load, the default is 3 months|long| |
|open|Returns open incidents, when the parameter is missing, the default value is true|boolean| |
|acknowledged|Returns acknowledged incidents, when the parameter is missing, the default value is true|boolean| |
|resolved|Returns resolved incidents, when the parameter is missing, the default value is false|boolean| |
|muted|Returns muted incidents, when the parameter is missing, the default value is false|boolean| |
|page|Page number, the first page is 1|long| |
|limit|Page size, the default is 50 rows|long| |
|filter|Optional full text search filter that supports *prefix, suffix* and nest*ed filter expressions|string| |
|[order](../models/incidents.md#incidentsortorder)|Optional sort order, the default sort order is by the number of failed data quality checks|[IncidentSortOrder](../models/incidents.md#incidentsortorder)| |
|[direction](../models/incidents.md#sortdirection)|Optional sort direction, the default sort direction is ascending|[SortDirection](../models/incidents.md#sortdirection)| |






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/incidents/sample_connection^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

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

=== "Python async client"

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

=== "Python auth sync client"

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

=== "Python auth async client"

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




??? "Return value sample"
    ```js
    [ {
	  "year" : 0,
	  "month" : 0,
	  "highestSeverity" : 0,
	  "minimumSeverity" : 0,
	  "failedChecksCount" : 0
	}, {
	  "year" : 0,
	  "month" : 0,
	  "highestSeverity" : 0,
	  "minimumSeverity" : 0,
	  "failedChecksCount" : 0
	}, {
	  "year" : 0,
	  "month" : 0,
	  "highestSeverity" : 0,
	  "minimumSeverity" : 0,
	  "failedChecksCount" : 0
	} ]
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
|[incident_model](../models/incidents.md#incidentmodel)||[IncidentModel](../models/incidents.md#incidentmodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|year|Year when the incident was first seen|long|:material-check-bold:|
|month|Month when the incident was first seen|long|:material-check-bold:|
|incident_id|Incident id|string|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/incidents/sample_connection/2007/10/sample_incident^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

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

=== "Python async client"

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

=== "Python auth sync client"

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

=== "Python auth async client"

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




??? "Return value sample"
    ```js
    {
	  "year" : 0,
	  "month" : 0,
	  "highestSeverity" : 0,
	  "minimumSeverity" : 0,
	  "failedChecksCount" : 0
	}
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
|[incident_issue_histogram_model](../models/incidents.md#incidentissuehistogrammodel)||[IncidentIssueHistogramModel](../models/incidents.md#incidentissuehistogrammodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|year|Year when the incident was first seen|long|:material-check-bold:|
|month|Month when the incident was first seen|long|:material-check-bold:|
|incident_id|Incident id|string|:material-check-bold:|
|filter|Optional full text search filter that supports *prefix, suffix* and nest*ed filter expressions|string| |
|days|Optional filter for a number of recent days to read the related issues|long| |
|date|Optional date filter|string| |
|column|Optional column name filter|string| |
|check|Optional check name filter|string| |






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/incidents/sample_connection/2007/10/sample_incident/histogram^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

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

=== "Python async client"

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

=== "Python auth sync client"

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

=== "Python auth async client"

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




??? "Return value sample"
    ```js
    {
	  "hasProfilingIssues" : false,
	  "hasMonitoringIssues" : false,
	  "hasPartitionedIssues" : false,
	  "days" : { },
	  "columns" : { },
	  "checks" : { }
	}
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
|check_result_entry_model||List[[CheckResultEntryModel](../models/incidents.md#checkresultentrymodel)]|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|year|Year when the incident was first seen|long|:material-check-bold:|
|month|Month when the incident was first seen|long|:material-check-bold:|
|incident_id|Incident id|string|:material-check-bold:|
|page|Page number, the first page is 1|long| |
|limit|Page size, the default is 50 rows|long| |
|filter|Optional filter|string| |
|days|Optional filter for a number of recent days to read the related issues|long| |
|date|Optional filter to return data quality issues only for a given date. The date should be an ISO8601 formatted date, it is treated as the timezone of the DQOps server.|string| |
|column|Optional column name filter|string| |
|check|Optional check name filter|string| |
|[order](../models/incidents.md#checkresultsortorder)|Optional sort order, the default sort order is by the execution date|[CheckResultSortOrder](../models/incidents.md#checkresultsortorder)| |
|[direction](../models/incidents.md#sortdirection)|Optional sort direction, the default sort direction is ascending|[SortDirection](../models/incidents.md#sortdirection)| |






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/incidents/sample_connection/2007/10/sample_incident/issues^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

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

=== "Python async client"

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

=== "Python auth sync client"

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

=== "Python auth async client"

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




??? "Return value sample"
    ```js
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
|connection_name|Connection name|string|:material-check-bold:|
|year|Year when the incident was first seen|long|:material-check-bold:|
|month|Month when the incident was first seen|long|:material-check-bold:|
|incident_id|Incident id|string|:material-check-bold:|
|issue_url|New incident&#x27;s issueUrl|string|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl -X POST http://localhost:8888/api/incidents/sample_connection/2007/10/sample_incident/issueurl^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

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
|connection_name|Connection name|string|:material-check-bold:|
|year|Year when the incident was first seen|long|:material-check-bold:|
|month|Month when the incident was first seen|long|:material-check-bold:|
|incident_id|Incident id|string|:material-check-bold:|
|[status](../models/incidents.md#incidentstatus)|New incident status, supported values: open, acknowledged, resolved, muted|[IncidentStatus](../models/incidents.md#incidentstatus)|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl -X POST http://localhost:8888/api/incidents/sample_connection/2007/10/sample_incident/status^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

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





