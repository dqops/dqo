Returns the complete results of executed checks on tables and columns.  


___  
## get_column_monitoring_checks_results  
Returns a complete view of the recent column level monitoring executions for the monitoring at a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_column_monitoring_checks_results.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/results  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_list_model||List[[CheckResultsListModel](../../models/check_results/#checkresultslistmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|
|data_group|Data group|string| |
|month_start|Month start boundary|string| |
|month_end|Month end boundary|string| |
|check_name|Check name|string| |
|category|Check category name|string| |
|table_comparison|Table comparison name|string| |
|max_results_per_check|Maximum number of results per check, the default is 100|long| |






**Usage examples**  

=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily/results^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_monitoring_checks_results
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_monitoring_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_monitoring_checks_results
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_monitoring_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_monitoring_checks_results
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_column_monitoring_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_monitoring_checks_results
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_monitoring_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    [ {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	}, {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	}, {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	} ]
    ```


___  
## get_column_partitioned_checks_results  
Returns an overview of the most recent column level partitioned checks executions for a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_column_partitioned_checks_results.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/results  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_list_model||List[[CheckResultsListModel](../../models/check_results/#checkresultslistmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|
|data_group|Data group|string| |
|month_start|Month start boundary|string| |
|month_end|Month end boundary|string| |
|check_name|Check name|string| |
|category|Check category name|string| |
|table_comparison|Table comparison name|string| |
|max_results_per_check|Maximum number of results per check, the default is 100|long| |






**Usage examples**  

=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily/results^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_partitioned_checks_results
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_partitioned_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_partitioned_checks_results
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_partitioned_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_partitioned_checks_results
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_column_partitioned_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_partitioned_checks_results
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_partitioned_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    [ {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	}, {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	}, {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	} ]
    ```


___  
## get_column_profiling_checks_results  
Returns an overview of the most recent check executions for all column level data quality profiling checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_column_profiling_checks_results.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/results  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_list_model||List[[CheckResultsListModel](../../models/check_results/#checkresultslistmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|data_group|Data group|string| |
|month_start|Month start boundary|string| |
|month_end|Month end boundary|string| |
|check_name|Check name|string| |
|category|Check category name|string| |
|table_comparison|Table comparison name|string| |
|max_results_per_check|Maximum number of results per check, the default is 100|long| |






**Usage examples**  

=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/results^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_profiling_checks_results
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_column_profiling_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_profiling_checks_results
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_profiling_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_profiling_checks_results
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_column_profiling_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_column_profiling_checks_results
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_column_profiling_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    [ {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	}, {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	}, {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	} ]
    ```


___  
## get_table_data_quality_status  
Read the most recent results of executed data quality checks on the table and return the current table&#x27;s data quality status - the number of failed data quality checks if the table has active data quality issues. Also returns the names of data quality checks that did not pass most recently. This operation verifies only the status of the most recently executed data quality checks. Previous data quality issues are not counted.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_table_data_quality_status.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/status  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_current_data_quality_status_model](../../models/check_results/#tablecurrentdataqualitystatusmodel)||[TableCurrentDataQualityStatusModel](../../models/check_results/#tablecurrentdataqualitystatusmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|months|Optional filter - the number of months to review the data quality check results. For partitioned checks, it is the number of months to analyze. The default value is 1 (which is the current month and 1 previous month).|long| |
|since|Optional filter that accepts an UTC timestamp to read only data quality check results captured since that timestamp.|string| |
|profiling|Optional check type filter to detect the current status of the profiling checks results. The default value is false, excluding profiling checks from the current table status detection. If enabled, only the status of the most recent check result is retrieved.|boolean| |
|monitoring|Optional check type filter to detect the current status of the monitoring checks results. The default value is true, including monitoring checks in the current table status detection. If enabled, only the status of the most recent check result is retrieved.|boolean| |
|partitioned|Optional check type filter to detect the current status of the partitioned checks results. The default value is true, including partitioned checks in the current table status detection. Detection of the status of partitioned checks is different. When enabled, DQOps checks the highest severity status of all partitions since the **since** date or within the last **months**.|boolean| |
|[check_time_scale](../../models/Common/#checktimescale)|Optional time scale filter for monitoring and partitioned checks (values: daily or monthly).|[CheckTimeScale](../../models/Common/#checktimescale)| |
|data_group|Optional data group|string| |
|check_name|Optional check name|string| |
|category|Optional check category name|string| |
|table_comparison|Optional table comparison name|string| |
|quality_dimension|Optional data quality dimension|string| |






**Usage examples**  

=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/status^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_data_quality_status
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_table_data_quality_status.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_data_quality_status
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_table_data_quality_status.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_data_quality_status
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_table_data_quality_status.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_data_quality_status
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_table_data_quality_status.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    {
	  "connection_name" : "sample_connection",
	  "schema_name" : "sample_schema",
	  "table_name" : "sample_table",
	  "last_check_executed_at" : "2007-10-14T16:42:42Z",
	  "executed_checks" : 8,
	  "valid_results" : 3,
	  "warnings" : 5,
	  "errors" : 0,
	  "fatals" : 0,
	  "execution_errors" : 0,
	  "checks" : { },
	  "columns" : { }
	}
    ```


___  
## get_table_monitoring_checks_results  
Returns the complete results of the most recent table level monitoring executions for the monitoring at a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_table_monitoring_checks_results.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/results  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_list_model||List[[CheckResultsListModel](../../models/check_results/#checkresultslistmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|
|data_group|Data group|string| |
|month_start|Month start boundary|string| |
|month_end|Month end boundary|string| |
|check_name|Check name|string| |
|category|Check category name|string| |
|table_comparison|Table comparison name|string| |
|max_results_per_check|Maximum number of results per check, the default is 100|long| |






**Usage examples**  

=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/daily/results^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_monitoring_checks_results
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_table_monitoring_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_monitoring_checks_results
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_table_monitoring_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_monitoring_checks_results
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_table_monitoring_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_monitoring_checks_results
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_table_monitoring_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    [ {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	}, {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	}, {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	} ]
    ```


___  
## get_table_partitioned_checks_results  
Returns a complete view of the recent table level partitioned checks executions for a requested time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_table_partitioned_checks_results.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/results  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_list_model||List[[CheckResultsListModel](../../models/check_results/#checkresultslistmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|
|data_group|Data group|string| |
|month_start|Month start boundary|string| |
|month_end|Month end boundary|string| |
|check_name|Check name|string| |
|category|Check category name|string| |
|table_comparison|Table comparison name|string| |
|max_results_per_check|Maximum number of results per check, the default is 100|long| |






**Usage examples**  

=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/daily/results^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_partitioned_checks_results
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_table_partitioned_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_partitioned_checks_results
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_table_partitioned_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_partitioned_checks_results
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_table_partitioned_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_partitioned_checks_results
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_table_partitioned_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    [ {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	}, {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	}, {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	} ]
    ```


___  
## get_table_profiling_checks_results  
Returns the complete results of the most recent check executions for all table level data quality profiling checks on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results/get_table_profiling_checks_results.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/results  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_results_list_model||List[[CheckResultsListModel](../../models/check_results/#checkresultslistmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|data_group|Data group|string| |
|month_start|Month start boundary|string| |
|month_end|Month end boundary|string| |
|check_name|Check name|string| |
|category|Check category name|string| |
|table_comparison|Table comparison name|string| |
|max_results_per_check|Maximum number of results per check, the default is 100|long| |






**Usage examples**  

=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/results^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_profiling_checks_results
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_table_profiling_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

=== "Python async client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_profiling_checks_results
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_table_profiling_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_profiling_checks_results
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_table_profiling_checks_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"
      
    ```python
    from dqops import client
	from dqops.client.api.check_results import get_table_profiling_checks_results
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_table_profiling_checks_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"  
    ```js
    [ {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	}, {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	}, {
	  "checkHash" : 0,
	  "checkCategory" : "sample_category",
	  "checkName" : "sample_check",
	  "checkDisplayName" : "sample_target/sample_category/sample_check",
	  "checkType" : "profiling",
	  "dataGroup" : "sample_data_grouping",
	  "checkResultEntries" : [ {
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
	    "id" : "-8597741925434587255",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 110.0,
	    "expectedValue" : 120.0,
	    "warningLowerBound" : 115.0,
	    "warningUpperBound" : 125.0,
	    "errorLowerBound" : 105.0,
	    "errorUpperBound" : 135.0,
	    "fatalLowerBound" : 95.0,
	    "fatalUpperBound" : 145.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T15:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T15:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "8520797026624525526",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 120.0,
	    "expectedValue" : 130.0,
	    "warningLowerBound" : 125.0,
	    "warningUpperBound" : 135.0,
	    "errorLowerBound" : 115.0,
	    "errorUpperBound" : 145.0,
	    "fatalLowerBound" : 105.0,
	    "fatalUpperBound" : 155.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T16:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T16:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  }, {
	    "id" : "879763789071004098",
	    "checkHash" : 0,
	    "checkCategory" : "sample_category",
	    "checkName" : "sample_check",
	    "checkDisplayName" : "sample_target/sample_category/sample_check",
	    "checkType" : "profiling",
	    "actualValue" : 130.0,
	    "expectedValue" : 140.0,
	    "warningLowerBound" : 135.0,
	    "warningUpperBound" : 145.0,
	    "errorLowerBound" : 125.0,
	    "errorUpperBound" : 155.0,
	    "fatalLowerBound" : 115.0,
	    "fatalUpperBound" : 165.0,
	    "severity" : 2,
	    "columnName" : "sample_column",
	    "dataGroup" : "sample_data_grouping",
	    "durationMs" : 142,
	    "executedAt" : "2023-10-01T17:00:00Z",
	    "timeGradient" : "hour",
	    "timePeriod" : "2023-10-01T17:00:00",
	    "includeInKpi" : true,
	    "includeInSla" : true,
	    "provider" : "BigQuery",
	    "qualityDimension" : "sample_quality_dimension",
	    "sensorName" : "sample_target/sample_category/sample_sensor"
	  } ]
	} ]
    ```


