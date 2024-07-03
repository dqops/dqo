---
title: DQOps REST API error_samples operations
---
# DQOps REST API error_samples operations
Operations that return the error samples collected when data quality checks were executed on data sources from the check editor, and rules failed with an error.


___
## get_column_monitoring_error_samples
Returns error samples related to a column level monitoring checks at a requested time scale

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/error_samples/get_column_monitoring_error_samples.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/errorsamples
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`error_samples_list_model`</span>||*List[[ErrorSamplesListModel](../models/error_samples.md#errorsampleslistmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`data_group`</span>|Data group|*string*| |
|<span class="no-wrap-code">`month_start`</span>|Month start boundary|*string*| |
|<span class="no-wrap-code">`month_end`</span>|Month end boundary|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`category`</span>|Check category name|*string*| |
|<span class="no-wrap-code">`table_comparison`</span>|Table comparison name|*string*| |
|<span class="no-wrap-code">`max_results_per_check`</span>|Maximum number of results per check, the default is 100|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily/errorsamples^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "errorSamplesEntries" : [ ]
		}, {
		  "errorSamplesEntries" : [ ]
		}, {
		  "errorSamplesEntries" : [ ]
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_column_monitoring_error_samples
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_error_samples.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_column_monitoring_error_samples
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_error_samples.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_column_monitoring_error_samples
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_error_samples.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_column_monitoring_error_samples
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_error_samples.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    



___
## get_column_partitioned_error_samples
Returns the error samples related to column level partitioned checks for a requested time scale

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/error_samples/get_column_partitioned_error_samples.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/errorsamples
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`error_samples_list_model`</span>||*List[[ErrorSamplesListModel](../models/error_samples.md#errorsampleslistmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`data_group`</span>|Data group|*string*| |
|<span class="no-wrap-code">`month_start`</span>|Month start boundary|*string*| |
|<span class="no-wrap-code">`month_end`</span>|Month end boundary|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`category`</span>|Check category name|*string*| |
|<span class="no-wrap-code">`table_comparison`</span>|Table comparison name|*string*| |
|<span class="no-wrap-code">`max_results_per_check`</span>|Maximum number of results per check, the default is 100|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily/errorsamples^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "errorSamplesEntries" : [ ]
		}, {
		  "errorSamplesEntries" : [ ]
		}, {
		  "errorSamplesEntries" : [ ]
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_column_partitioned_error_samples
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_error_samples.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_column_partitioned_error_samples
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_error_samples.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_column_partitioned_error_samples
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_error_samples.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_column_partitioned_error_samples
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_error_samples.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    



___
## get_column_profiling_error_samples
Returns the error samples related to a profiling check for all column level data quality profiling checks on a column

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/error_samples/get_column_profiling_error_samples.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/errorsamples
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`error_samples_list_model`</span>||*List[[ErrorSamplesListModel](../models/error_samples.md#errorsampleslistmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`column_name`</span>|Column name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`data_group`</span>|Data group|*string*| |
|<span class="no-wrap-code">`month_start`</span>|Month start boundary|*string*| |
|<span class="no-wrap-code">`month_end`</span>|Month end boundary|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`category`</span>|Check category name|*string*| |
|<span class="no-wrap-code">`table_comparison`</span>|Table comparison name|*string*| |
|<span class="no-wrap-code">`max_results_per_check`</span>|Maximum number of results per check, the default is 100|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/errorsamples^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "errorSamplesEntries" : [ ]
		}, {
		  "errorSamplesEntries" : [ ]
		}, {
		  "errorSamplesEntries" : [ ]
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_column_profiling_error_samples
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_profiling_error_samples.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_column_profiling_error_samples
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_profiling_error_samples.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_column_profiling_error_samples
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_profiling_error_samples.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_column_profiling_error_samples
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_profiling_error_samples.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    



___
## get_table_monitoring_error_samples
Returns the error samples related to a table level monitoring check a requested time scale

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/error_samples/get_table_monitoring_error_samples.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/errorsamples
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`error_samples_list_model`</span>||*List[[ErrorSamplesListModel](../models/error_samples.md#errorsampleslistmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`data_group`</span>|Data group|*string*| |
|<span class="no-wrap-code">`month_start`</span>|Month start boundary|*string*| |
|<span class="no-wrap-code">`month_end`</span>|Month end boundary|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`category`</span>|Check category name|*string*| |
|<span class="no-wrap-code">`table_comparison`</span>|Table comparison name|*string*| |
|<span class="no-wrap-code">`max_results_per_check`</span>|Maximum number of results per check, the default is 100|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/daily/errorsamples^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "errorSamplesEntries" : [ ]
		}, {
		  "errorSamplesEntries" : [ ]
		}, {
		  "errorSamplesEntries" : [ ]
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_table_monitoring_error_samples
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_error_samples.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_table_monitoring_error_samples
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_error_samples.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_table_monitoring_error_samples
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_error_samples.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_table_monitoring_error_samples
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_error_samples.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    



___
## get_table_partitioned_error_samples
Returns error samples related to a table level partitioned check for a requested time scale

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/error_samples/get_table_partitioned_error_samples.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/errorsamples
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`error_samples_list_model`</span>||*List[[ErrorSamplesListModel](../models/error_samples.md#errorsampleslistmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`data_group`</span>|Data group|*string*| |
|<span class="no-wrap-code">`month_start`</span>|Month start boundary|*string*| |
|<span class="no-wrap-code">`month_end`</span>|Month end boundary|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`category`</span>|Check category name|*string*| |
|<span class="no-wrap-code">`table_comparison`</span>|Table comparison name|*string*| |
|<span class="no-wrap-code">`max_results_per_check`</span>|Maximum number of results per check, the default is 100|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/daily/errorsamples^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "errorSamplesEntries" : [ ]
		}, {
		  "errorSamplesEntries" : [ ]
		}, {
		  "errorSamplesEntries" : [ ]
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_table_partitioned_error_samples
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_error_samples.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_table_partitioned_error_samples
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_error_samples.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_table_partitioned_error_samples
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_error_samples.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_table_partitioned_error_samples
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_error_samples.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    



___
## get_table_profiling_error_samples
Returns the error samples related to a check for all table level data quality profiling checks on a table

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/error_samples/get_table_profiling_error_samples.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/errorsamples
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`error_samples_list_model`</span>||*List[[ErrorSamplesListModel](../models/error_samples.md#errorsampleslistmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`data_group`</span>|Data group|*string*| |
|<span class="no-wrap-code">`month_start`</span>|Month start boundary|*string*| |
|<span class="no-wrap-code">`month_end`</span>|Month end boundary|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`category`</span>|Check category name|*string*| |
|<span class="no-wrap-code">`table_comparison`</span>|Table comparison name|*string*| |
|<span class="no-wrap-code">`max_results_per_check`</span>|Maximum number of results per check, the default is 100|*long*| |






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/errorsamples^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "errorSamplesEntries" : [ ]
		}, {
		  "errorSamplesEntries" : [ ]
		}, {
		  "errorSamplesEntries" : [ ]
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_table_profiling_error_samples
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_error_samples.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_table_profiling_error_samples
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_error_samples.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_table_profiling_error_samples
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_error_samples.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.error_samples import get_table_profiling_error_samples
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_error_samples.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			),
			ErrorSamplesListModel(
				error_samples_entries=[
				
				]
			)
		]
        ```
    
    
    



