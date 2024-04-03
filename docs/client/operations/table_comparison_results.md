---
title: DQOps REST API table_comparison_results operations
---
# DQOps REST API table_comparison_results operations
Operations that returns the results of the most recent table comparison that was performed between the compared table and the reference table (the source of truth).


___
## get_table_comparison_monitoring_results
Retrieves the results of the most table comparison performed using the monitoring comparison checks.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparison_results/get_table_comparison_monitoring_results.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/comparisons/{tableComparisonConfigurationName}/results
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_comparison_results_model`](../models/table_comparison_results.md#tablecomparisonresultsmodel)</span>||*[TableComparisonResultsModel](../models/table_comparison_results.md#tablecomparisonresultsmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|Table comparison configuration name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/daily/comparisons/sample_table_comparison/results^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "table_comparison_results" : { },
		  "column_comparison_results" : { }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparison_results import get_table_comparison_monitoring_results
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_monitoring_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableComparisonResultsModel(
			table_comparison_results={
			
			},
			column_comparison_results={
			
			}
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparison_results import get_table_comparison_monitoring_results
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_monitoring_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableComparisonResultsModel(
			table_comparison_results={
			
			},
			column_comparison_results={
			
			}
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparison_results import get_table_comparison_monitoring_results
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_monitoring_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableComparisonResultsModel(
			table_comparison_results={
			
			},
			column_comparison_results={
			
			}
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparison_results import get_table_comparison_monitoring_results
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_monitoring_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableComparisonResultsModel(
			table_comparison_results={
			
			},
			column_comparison_results={
			
			}
		)
        ```
    
    
    



___
## get_table_comparison_partitioned_results
Retrieves the results of the most table comparison performed using the partitioned comparison checks, comparing days or months of data.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparison_results/get_table_comparison_partitioned_results.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/comparisons/{tableComparisonConfigurationName}/results
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_comparison_results_model`](../models/table_comparison_results.md#tablecomparisonresultsmodel)</span>||*[TableComparisonResultsModel](../models/table_comparison_results.md#tablecomparisonresultsmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|Table comparison configuration name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/daily/comparisons/sample_table_comparison/results^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "table_comparison_results" : { },
		  "column_comparison_results" : { }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparison_results import get_table_comparison_partitioned_results
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_partitioned_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableComparisonResultsModel(
			table_comparison_results={
			
			},
			column_comparison_results={
			
			}
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparison_results import get_table_comparison_partitioned_results
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_partitioned_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableComparisonResultsModel(
			table_comparison_results={
			
			},
			column_comparison_results={
			
			}
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparison_results import get_table_comparison_partitioned_results
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_partitioned_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableComparisonResultsModel(
			table_comparison_results={
			
			},
			column_comparison_results={
			
			}
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparison_results import get_table_comparison_partitioned_results
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_partitioned_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableComparisonResultsModel(
			table_comparison_results={
			
			},
			column_comparison_results={
			
			}
		)
        ```
    
    
    



___
## get_table_comparison_profiling_results
Retrieves the results of the most table comparison performed using the profiling checks comparison checks.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparison_results/get_table_comparison_profiling_results.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/comparisons/{tableComparisonConfigurationName}/results
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_comparison_results_model`](../models/table_comparison_results.md#tablecomparisonresultsmodel)</span>||*[TableComparisonResultsModel](../models/table_comparison_results.md#tablecomparisonresultsmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_comparison_configuration_name`</span>|Table comparison configuration name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/comparisons/sample_table_comparison/results^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "table_comparison_results" : { },
		  "column_comparison_results" : { }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparison_results import get_table_comparison_profiling_results
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_profiling_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableComparisonResultsModel(
			table_comparison_results={
			
			},
			column_comparison_results={
			
			}
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparison_results import get_table_comparison_profiling_results
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_profiling_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableComparisonResultsModel(
			table_comparison_results={
			
			},
			column_comparison_results={
			
			}
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparison_results import get_table_comparison_profiling_results
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_comparison_profiling_results.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableComparisonResultsModel(
			table_comparison_results={
			
			},
			column_comparison_results={
			
			}
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_comparison_results import get_table_comparison_profiling_results
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_comparison_profiling_results.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_table_comparison',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableComparisonResultsModel(
			table_comparison_results={
			
			},
			column_comparison_results={
			
			}
		)
        ```
    
    
    



