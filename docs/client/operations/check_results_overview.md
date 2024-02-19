# DQOps REST API check_results_overview operations
Returns the overview of the recently executed checks on tables and columns, returning a summary of the last 5 runs.


___
## get_column_monitoring_checks_overview
Returns an overview of the most recent column level monitoring executions for the monitoring at a requested time scale

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_column_monitoring_checks_overview.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/overview
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_results_overview_data_model`</span>||*List[[CheckResultsOverviewDataModel](../models/check_results_overview.md#checkresultsoverviewdatamodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`category`</span>|Optional check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Optional check name|*string*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily/overview^
		-H "Accept: application/json"
	
    ```

    
    **Return value sample**
    
    
    ```js
    [ {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	}, {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	}, {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	} ]
    ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_column_monitoring_checks_overview
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_checks_overview.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_column_monitoring_checks_overview
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_checks_overview.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_column_monitoring_checks_overview
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_checks_overview.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_column_monitoring_checks_overview
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_checks_overview.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    



___
## get_column_partitioned_checks_overview
Returns an overview of the most recent column level partitioned checks executions for a requested time scale

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_column_partitioned_checks_overview.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/overview
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_results_overview_data_model`</span>||*List[[CheckResultsOverviewDataModel](../models/check_results_overview.md#checkresultsoverviewdatamodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`category`</span>|Optional check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Optional check name|*string*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily/overview^
		-H "Accept: application/json"
	
    ```

    
    **Return value sample**
    
    
    ```js
    [ {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	}, {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	}, {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	} ]
    ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_column_partitioned_checks_overview
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_checks_overview.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_column_partitioned_checks_overview
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_checks_overview.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_column_partitioned_checks_overview
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_checks_overview.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_column_partitioned_checks_overview
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_checks_overview.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    



___
## get_column_profiling_checks_overview
Returns an overview of the most recent check executions for all column level data quality profiling checks on a column

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_column_profiling_checks_overview.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/overview
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_results_overview_data_model`</span>||*List[[CheckResultsOverviewDataModel](../models/check_results_overview.md#checkresultsoverviewdatamodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`category`</span>|Optional check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Optional check name|*string*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/overview^
		-H "Accept: application/json"
	
    ```

    
    **Return value sample**
    
    
    ```js
    [ {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	}, {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	}, {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	} ]
    ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_column_profiling_checks_overview
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_profiling_checks_overview.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_column_profiling_checks_overview
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_profiling_checks_overview.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_column_profiling_checks_overview
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_profiling_checks_overview.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_column_profiling_checks_overview
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_profiling_checks_overview.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    



___
## get_table_monitoring_checks_overview
Returns an overview of the most recent table level monitoring executions for the monitoring at a requested time scale

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_table_monitoring_checks_overview.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/overview
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_results_overview_data_model`</span>||*List[[CheckResultsOverviewDataModel](../models/check_results_overview.md#checkresultsoverviewdatamodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`category`</span>|Optional check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Optional check name|*string*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/daily/overview^
		-H "Accept: application/json"
	
    ```

    
    **Return value sample**
    
    
    ```js
    [ {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	}, {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	}, {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	} ]
    ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_table_monitoring_checks_overview
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_checks_overview.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_table_monitoring_checks_overview
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_checks_overview.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_table_monitoring_checks_overview
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_checks_overview.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_table_monitoring_checks_overview
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_checks_overview.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    



___
## get_table_partitioned_checks_overview
Returns an overview of the most recent table level partitioned checks executions for a requested time scale

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_table_partitioned_checks_overview.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/overview
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_results_overview_data_model`</span>||*List[[CheckResultsOverviewDataModel](../models/check_results_overview.md#checkresultsoverviewdatamodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`category`</span>|Optional check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Optional check name|*string*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/daily/overview^
		-H "Accept: application/json"
	
    ```

    
    **Return value sample**
    
    
    ```js
    [ {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	}, {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	}, {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	} ]
    ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_table_partitioned_checks_overview
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_checks_overview.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_table_partitioned_checks_overview
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_checks_overview.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_table_partitioned_checks_overview
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_checks_overview.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_table_partitioned_checks_overview
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_checks_overview.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    



___
## get_table_profiling_checks_overview
Returns an overview of the most recent check executions for all table level data quality profiling checks on a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/check_results_overview/get_table_profiling_checks_overview.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/overview
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_results_overview_data_model`</span>||*List[[CheckResultsOverviewDataModel](../models/check_results_overview.md#checkresultsoverviewdatamodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`category`</span>|Optional check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Optional check name|*string*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/overview^
		-H "Accept: application/json"
	
    ```

    
    **Return value sample**
    
    
    ```js
    [ {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	}, {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	}, {
	  "checkHash" : 0,
	  "timePeriods" : [ ],
	  "timePeriodsUtc" : [ ],
	  "executedAtTimestamps" : [ ],
	  "timePeriodDisplayTexts" : [ ],
	  "statuses" : [ ],
	  "dataGroups" : [ ],
	  "results" : [ ]
	} ]
    ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_table_profiling_checks_overview
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_checks_overview.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_table_profiling_checks_overview
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_checks_overview.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_table_profiling_checks_overview
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_checks_overview.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.check_results_overview import get_table_profiling_checks_overview
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_checks_overview.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    **Return value sample**
    
    ```python
    [
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		),
		CheckResultsOverviewDataModel(
			check_hash=0,
			time_periods=[
			
			],
			time_periods_utc=[
			
			],
			executed_at_timestamps=[
			
			],
			time_period_display_texts=[
			
			],
			statuses=[
			
			],
			data_groups=[
			
			],
			results=[
			
			]
		)
	]
    ```
    
    
    



