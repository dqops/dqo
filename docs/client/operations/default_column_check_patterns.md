---
title: DQOps REST API default_column_check_patterns operations
---
# DQOps REST API default_column_check_patterns operations
Operations for managing the configuration of the default column-level checks for columns matching a pattern.


___
## create_default_column_checks_pattern
Creates (adds) a new default column-level checks pattern configuration by saving a full specification object.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/create_default_column_checks_pattern.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/default/checks/column/{patternName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Default checks pattern model|*[DefaultColumnChecksPatternModel](../models/default_column_check_patterns.md#defaultcolumncheckspatternmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/default/checks/column/default^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"pattern_name\":\"id columns not null\",\"pattern_spec\":{\"priority\":0,\"target\":{\"column\":\"id\"},\"monitoring_checks\":{\"daily\":{\"nulls\":{\"daily_nulls_count\":{\"error\":{\"max_count\":0}}}}}},\"can_edit\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import create_default_column_checks_pattern
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnDefaultChecksPatternSpec, \
	                                ColumnMonitoringCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnPartitionedCheckCategoriesSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                DefaultColumnChecksPatternModel, \
	                                MaxCountRule0ErrorParametersSpec, \
	                                TargetColumnPatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DefaultColumnChecksPatternModel(
		pattern_name='id columns not null',
		pattern_spec=ColumnDefaultChecksPatternSpec(
			priority=0,
			disabled=False,
			target=TargetColumnPatternSpec(column='id'),
			profiling_checks=ColumnProfilingCheckCategoriesSpec(comparisons=ColumnComparisonProfilingChecksSpecMap()),
			monitoring_checks=ColumnMonitoringCheckCategoriesSpec(
				daily=ColumnDailyMonitoringCheckCategoriesSpec(
					nulls=ColumnNullsDailyMonitoringChecksSpec(
						daily_nulls_count=ColumnNullsCountCheckSpec(
							parameters=ColumnNullsNullsCountSensorParametersSpec(),
							error=MaxCountRule0ErrorParametersSpec(max_count=0),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False
						)
					),
					comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
				)
			),
			partitioned_checks=ColumnPartitionedCheckCategoriesSpec()
		),
		can_edit=True
	)
	
	call_result = create_default_column_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import create_default_column_checks_pattern
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnDefaultChecksPatternSpec, \
	                                ColumnMonitoringCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnPartitionedCheckCategoriesSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                DefaultColumnChecksPatternModel, \
	                                MaxCountRule0ErrorParametersSpec, \
	                                TargetColumnPatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DefaultColumnChecksPatternModel(
		pattern_name='id columns not null',
		pattern_spec=ColumnDefaultChecksPatternSpec(
			priority=0,
			disabled=False,
			target=TargetColumnPatternSpec(column='id'),
			profiling_checks=ColumnProfilingCheckCategoriesSpec(comparisons=ColumnComparisonProfilingChecksSpecMap()),
			monitoring_checks=ColumnMonitoringCheckCategoriesSpec(
				daily=ColumnDailyMonitoringCheckCategoriesSpec(
					nulls=ColumnNullsDailyMonitoringChecksSpec(
						daily_nulls_count=ColumnNullsCountCheckSpec(
							parameters=ColumnNullsNullsCountSensorParametersSpec(),
							error=MaxCountRule0ErrorParametersSpec(max_count=0),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False
						)
					),
					comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
				)
			),
			partitioned_checks=ColumnPartitionedCheckCategoriesSpec()
		),
		can_edit=True
	)
	
	call_result = await create_default_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import create_default_column_checks_pattern
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnDefaultChecksPatternSpec, \
	                                ColumnMonitoringCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnPartitionedCheckCategoriesSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                DefaultColumnChecksPatternModel, \
	                                MaxCountRule0ErrorParametersSpec, \
	                                TargetColumnPatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DefaultColumnChecksPatternModel(
		pattern_name='id columns not null',
		pattern_spec=ColumnDefaultChecksPatternSpec(
			priority=0,
			disabled=False,
			target=TargetColumnPatternSpec(column='id'),
			profiling_checks=ColumnProfilingCheckCategoriesSpec(comparisons=ColumnComparisonProfilingChecksSpecMap()),
			monitoring_checks=ColumnMonitoringCheckCategoriesSpec(
				daily=ColumnDailyMonitoringCheckCategoriesSpec(
					nulls=ColumnNullsDailyMonitoringChecksSpec(
						daily_nulls_count=ColumnNullsCountCheckSpec(
							parameters=ColumnNullsNullsCountSensorParametersSpec(),
							error=MaxCountRule0ErrorParametersSpec(max_count=0),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False
						)
					),
					comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
				)
			),
			partitioned_checks=ColumnPartitionedCheckCategoriesSpec()
		),
		can_edit=True
	)
	
	call_result = create_default_column_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import create_default_column_checks_pattern
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnDefaultChecksPatternSpec, \
	                                ColumnMonitoringCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnPartitionedCheckCategoriesSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                DefaultColumnChecksPatternModel, \
	                                MaxCountRule0ErrorParametersSpec, \
	                                TargetColumnPatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DefaultColumnChecksPatternModel(
		pattern_name='id columns not null',
		pattern_spec=ColumnDefaultChecksPatternSpec(
			priority=0,
			disabled=False,
			target=TargetColumnPatternSpec(column='id'),
			profiling_checks=ColumnProfilingCheckCategoriesSpec(comparisons=ColumnComparisonProfilingChecksSpecMap()),
			monitoring_checks=ColumnMonitoringCheckCategoriesSpec(
				daily=ColumnDailyMonitoringCheckCategoriesSpec(
					nulls=ColumnNullsDailyMonitoringChecksSpec(
						daily_nulls_count=ColumnNullsCountCheckSpec(
							parameters=ColumnNullsNullsCountSensorParametersSpec(),
							error=MaxCountRule0ErrorParametersSpec(max_count=0),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False
						)
					),
					comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
				)
			),
			partitioned_checks=ColumnPartitionedCheckCategoriesSpec()
		),
		can_edit=True
	)
	
	call_result = await create_default_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## create_default_column_checks_pattern_target
Creates (adds) a new default column-level checks pattern configuration.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/create_default_column_checks_pattern_target.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/default/checks/column/{patternName}/target
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Default checks pattern model with only the target filters|*[DefaultColumnChecksPatternListModel](../models/default_column_check_patterns.md#defaultcolumncheckspatternlistmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/default/checks/column/default/target^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"pattern_name\":\"default\",\"priority\":100,\"disabled\":false,\"target_column\":{\"connection\":\"dwh\",\"column\":\"id\"},\"can_edit\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import create_default_column_checks_pattern_target
	from dqops.client.models import DefaultColumnChecksPatternListModel, \
	                                TargetColumnPatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DefaultColumnChecksPatternListModel(
		pattern_name='default',
		priority=100,
		disabled=False,
		target_column=TargetColumnPatternSpec(
			column='id',
			connection='dwh'
		),
		can_edit=True
	)
	
	call_result = create_default_column_checks_pattern_target.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import create_default_column_checks_pattern_target
	from dqops.client.models import DefaultColumnChecksPatternListModel, \
	                                TargetColumnPatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DefaultColumnChecksPatternListModel(
		pattern_name='default',
		priority=100,
		disabled=False,
		target_column=TargetColumnPatternSpec(
			column='id',
			connection='dwh'
		),
		can_edit=True
	)
	
	call_result = await create_default_column_checks_pattern_target.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import create_default_column_checks_pattern_target
	from dqops.client.models import DefaultColumnChecksPatternListModel, \
	                                TargetColumnPatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DefaultColumnChecksPatternListModel(
		pattern_name='default',
		priority=100,
		disabled=False,
		target_column=TargetColumnPatternSpec(
			column='id',
			connection='dwh'
		),
		can_edit=True
	)
	
	call_result = create_default_column_checks_pattern_target.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import create_default_column_checks_pattern_target
	from dqops.client.models import DefaultColumnChecksPatternListModel, \
	                                TargetColumnPatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DefaultColumnChecksPatternListModel(
		pattern_name='default',
		priority=100,
		disabled=False,
		target_column=TargetColumnPatternSpec(
			column='id',
			connection='dwh'
		),
		can_edit=True
	)
	
	call_result = await create_default_column_checks_pattern_target.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## delete_default_column_checks_pattern
Deletes a default column-level checks pattern

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/delete_default_column_checks_pattern.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/default/checks/column/{patternName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X DELETE http://localhost:8888/api/default/checks/column/default^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import delete_default_column_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = delete_default_column_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import delete_default_column_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await delete_default_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import delete_default_column_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = delete_default_column_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import delete_default_column_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await delete_default_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    



___
## get_all_default_column_checks_patterns
Returns a flat list of all column-level default check patterns configured for this instance. Default checks are applied on columns dynamically.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/get_all_default_column_checks_patterns.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/checks/column
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`default_column_checks_pattern_list_model`</span>||*List[[DefaultColumnChecksPatternListModel](../models/default_column_check_patterns.md#defaultcolumncheckspatternlistmodel)]*|








**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/default/checks/column^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "pattern_name" : "default",
		  "priority" : 100,
		  "disabled" : false,
		  "target_column" : {
		    "connection" : "dwh",
		    "column" : "id"
		  },
		  "can_edit" : true
		}, {
		  "pattern_name" : "default",
		  "priority" : 100,
		  "disabled" : false,
		  "target_column" : {
		    "connection" : "dwh",
		    "column" : "id"
		  },
		  "can_edit" : true
		}, {
		  "pattern_name" : "default",
		  "priority" : 100,
		  "disabled" : false,
		  "target_column" : {
		    "connection" : "dwh",
		    "column" : "id"
		  },
		  "can_edit" : true
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_all_default_column_checks_patterns
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_default_column_checks_patterns.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			DefaultColumnChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_column=TargetColumnPatternSpec(
					column='id',
					connection='dwh'
				),
				can_edit=True
			),
			DefaultColumnChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_column=TargetColumnPatternSpec(
					column='id',
					connection='dwh'
				),
				can_edit=True
			),
			DefaultColumnChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_column=TargetColumnPatternSpec(
					column='id',
					connection='dwh'
				),
				can_edit=True
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_all_default_column_checks_patterns
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_default_column_checks_patterns.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			DefaultColumnChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_column=TargetColumnPatternSpec(
					column='id',
					connection='dwh'
				),
				can_edit=True
			),
			DefaultColumnChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_column=TargetColumnPatternSpec(
					column='id',
					connection='dwh'
				),
				can_edit=True
			),
			DefaultColumnChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_column=TargetColumnPatternSpec(
					column='id',
					connection='dwh'
				),
				can_edit=True
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_all_default_column_checks_patterns
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_default_column_checks_patterns.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			DefaultColumnChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_column=TargetColumnPatternSpec(
					column='id',
					connection='dwh'
				),
				can_edit=True
			),
			DefaultColumnChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_column=TargetColumnPatternSpec(
					column='id',
					connection='dwh'
				),
				can_edit=True
			),
			DefaultColumnChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_column=TargetColumnPatternSpec(
					column='id',
					connection='dwh'
				),
				can_edit=True
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_all_default_column_checks_patterns
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_default_column_checks_patterns.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			DefaultColumnChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_column=TargetColumnPatternSpec(
					column='id',
					connection='dwh'
				),
				can_edit=True
			),
			DefaultColumnChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_column=TargetColumnPatternSpec(
					column='id',
					connection='dwh'
				),
				can_edit=True
			),
			DefaultColumnChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_column=TargetColumnPatternSpec(
					column='id',
					connection='dwh'
				),
				can_edit=True
			)
		]
        ```
    
    
    



___
## get_default_column_checks_pattern
Returns a default checks pattern definition as a full specification object

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/get_default_column_checks_pattern.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/checks/column/{patternName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`default_column_checks_pattern_model`](../models/default_column_check_patterns.md#defaultcolumncheckspatternmodel)</span>||*[DefaultColumnChecksPatternModel](../models/default_column_check_patterns.md#defaultcolumncheckspatternmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Column pattern name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/default/checks/column/default^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "pattern_name" : "id columns not null",
		  "pattern_spec" : {
		    "priority" : 0,
		    "target" : {
		      "column" : "id"
		    },
		    "monitoring_checks" : {
		      "daily" : {
		        "nulls" : {
		          "daily_nulls_count" : {
		            "error" : {
		              "max_count" : 0
		            }
		          }
		        }
		      }
		    }
		  },
		  "can_edit" : true
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_column_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_column_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DefaultColumnChecksPatternModel(
			pattern_name='id columns not null',
			pattern_spec=ColumnDefaultChecksPatternSpec(
				priority=0,
				disabled=False,
				target=TargetColumnPatternSpec(column='id'),
				profiling_checks=ColumnProfilingCheckCategoriesSpec(comparisons=ColumnComparisonProfilingChecksSpecMap()),
				monitoring_checks=ColumnMonitoringCheckCategoriesSpec(
					daily=ColumnDailyMonitoringCheckCategoriesSpec(
						nulls=ColumnNullsDailyMonitoringChecksSpec(
							daily_nulls_count=ColumnNullsCountCheckSpec(
								parameters=ColumnNullsNullsCountSensorParametersSpec(),
								error=MaxCountRule0ErrorParametersSpec(max_count=0),
								disabled=False,
								exclude_from_kpi=False,
								include_in_sla=False
							)
						),
						comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
					)
				),
				partitioned_checks=ColumnPartitionedCheckCategoriesSpec()
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_column_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DefaultColumnChecksPatternModel(
			pattern_name='id columns not null',
			pattern_spec=ColumnDefaultChecksPatternSpec(
				priority=0,
				disabled=False,
				target=TargetColumnPatternSpec(column='id'),
				profiling_checks=ColumnProfilingCheckCategoriesSpec(comparisons=ColumnComparisonProfilingChecksSpecMap()),
				monitoring_checks=ColumnMonitoringCheckCategoriesSpec(
					daily=ColumnDailyMonitoringCheckCategoriesSpec(
						nulls=ColumnNullsDailyMonitoringChecksSpec(
							daily_nulls_count=ColumnNullsCountCheckSpec(
								parameters=ColumnNullsNullsCountSensorParametersSpec(),
								error=MaxCountRule0ErrorParametersSpec(max_count=0),
								disabled=False,
								exclude_from_kpi=False,
								include_in_sla=False
							)
						),
						comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
					)
				),
				partitioned_checks=ColumnPartitionedCheckCategoriesSpec()
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_column_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_column_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DefaultColumnChecksPatternModel(
			pattern_name='id columns not null',
			pattern_spec=ColumnDefaultChecksPatternSpec(
				priority=0,
				disabled=False,
				target=TargetColumnPatternSpec(column='id'),
				profiling_checks=ColumnProfilingCheckCategoriesSpec(comparisons=ColumnComparisonProfilingChecksSpecMap()),
				monitoring_checks=ColumnMonitoringCheckCategoriesSpec(
					daily=ColumnDailyMonitoringCheckCategoriesSpec(
						nulls=ColumnNullsDailyMonitoringChecksSpec(
							daily_nulls_count=ColumnNullsCountCheckSpec(
								parameters=ColumnNullsNullsCountSensorParametersSpec(),
								error=MaxCountRule0ErrorParametersSpec(max_count=0),
								disabled=False,
								exclude_from_kpi=False,
								include_in_sla=False
							)
						),
						comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
					)
				),
				partitioned_checks=ColumnPartitionedCheckCategoriesSpec()
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_column_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DefaultColumnChecksPatternModel(
			pattern_name='id columns not null',
			pattern_spec=ColumnDefaultChecksPatternSpec(
				priority=0,
				disabled=False,
				target=TargetColumnPatternSpec(column='id'),
				profiling_checks=ColumnProfilingCheckCategoriesSpec(comparisons=ColumnComparisonProfilingChecksSpecMap()),
				monitoring_checks=ColumnMonitoringCheckCategoriesSpec(
					daily=ColumnDailyMonitoringCheckCategoriesSpec(
						nulls=ColumnNullsDailyMonitoringChecksSpec(
							daily_nulls_count=ColumnNullsCountCheckSpec(
								parameters=ColumnNullsNullsCountSensorParametersSpec(),
								error=MaxCountRule0ErrorParametersSpec(max_count=0),
								disabled=False,
								exclude_from_kpi=False,
								include_in_sla=False
							)
						),
						comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
					)
				),
				partitioned_checks=ColumnPartitionedCheckCategoriesSpec()
			),
			can_edit=True
		)
        ```
    
    
    



___
## get_default_column_checks_pattern_target
Returns a default checks pattern definition

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/get_default_column_checks_pattern_target.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/checks/column/{patternName}/target
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`default_column_checks_pattern_list_model`](../models/default_column_check_patterns.md#defaultcolumncheckspatternlistmodel)</span>||*[DefaultColumnChecksPatternListModel](../models/default_column_check_patterns.md#defaultcolumncheckspatternlistmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Column pattern name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/default/checks/column/default/target^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "pattern_name" : "default",
		  "priority" : 100,
		  "disabled" : false,
		  "target_column" : {
		    "connection" : "dwh",
		    "column" : "id"
		  },
		  "can_edit" : true
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_column_checks_pattern_target
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_column_checks_pattern_target.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DefaultColumnChecksPatternListModel(
			pattern_name='default',
			priority=100,
			disabled=False,
			target_column=TargetColumnPatternSpec(
				column='id',
				connection='dwh'
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_column_checks_pattern_target
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_column_checks_pattern_target.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DefaultColumnChecksPatternListModel(
			pattern_name='default',
			priority=100,
			disabled=False,
			target_column=TargetColumnPatternSpec(
				column='id',
				connection='dwh'
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_column_checks_pattern_target
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_column_checks_pattern_target.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DefaultColumnChecksPatternListModel(
			pattern_name='default',
			priority=100,
			disabled=False,
			target_column=TargetColumnPatternSpec(
				column='id',
				connection='dwh'
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_column_checks_pattern_target
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_column_checks_pattern_target.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DefaultColumnChecksPatternListModel(
			pattern_name='default',
			priority=100,
			disabled=False,
			target_column=TargetColumnPatternSpec(
				column='id',
				connection='dwh'
			),
			can_edit=True
		)
        ```
    
    
    



___
## get_default_monitoring_daily_column_checks_pattern
Returns UI model to show and edit the default configuration of the daily monitoring checks that are configured for a check pattern on a column level.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/get_default_monitoring_daily_column_checks_pattern.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/checks/column/{patternName}/monitoring/daily
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_model`](../models/common.md#checkcontainermodel)</span>||*[CheckContainerModel](../models/common.md#checkcontainermodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/default/checks/column/default/monitoring/daily^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "categories" : [ {
		    "category" : "sample_category",
		    "help_text" : "Sample help text",
		    "checks" : [ {
		      "check_name" : "sample_check",
		      "help_text" : "Sample help text",
		      "sensor_parameters" : [ ],
		      "sensor_name" : "sample_target/sample_category/sample_sensor",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_grouping" : false,
		      "disabled" : false,
		      "exclude_from_kpi" : false,
		      "include_in_sla" : false,
		      "configured" : false,
		      "can_edit" : false,
		      "can_run_checks" : false,
		      "can_delete_data" : false
		    } ]
		  } ],
		  "can_edit" : false,
		  "can_run_checks" : false,
		  "can_delete_data" : false
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_monitoring_daily_column_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_monitoring_daily_column_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_monitoring_daily_column_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_monitoring_daily_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_monitoring_daily_column_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_monitoring_daily_column_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_monitoring_daily_column_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_monitoring_daily_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    



___
## get_default_monitoring_monthly_column_checks_pattern
Returns UI model to show and edit the default configuration of the monthly monitoring checks that are configured for a check pattern on a column level.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/get_default_monitoring_monthly_column_checks_pattern.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/checks/column/{patternName}/monitoring/monthly
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_model`](../models/common.md#checkcontainermodel)</span>||*[CheckContainerModel](../models/common.md#checkcontainermodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/default/checks/column/default/monitoring/monthly^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "categories" : [ {
		    "category" : "sample_category",
		    "help_text" : "Sample help text",
		    "checks" : [ {
		      "check_name" : "sample_check",
		      "help_text" : "Sample help text",
		      "sensor_parameters" : [ ],
		      "sensor_name" : "sample_target/sample_category/sample_sensor",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_grouping" : false,
		      "disabled" : false,
		      "exclude_from_kpi" : false,
		      "include_in_sla" : false,
		      "configured" : false,
		      "can_edit" : false,
		      "can_run_checks" : false,
		      "can_delete_data" : false
		    } ]
		  } ],
		  "can_edit" : false,
		  "can_run_checks" : false,
		  "can_delete_data" : false
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_monitoring_monthly_column_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_monitoring_monthly_column_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_monitoring_monthly_column_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_monitoring_monthly_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_monitoring_monthly_column_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_monitoring_monthly_column_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_monitoring_monthly_column_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_monitoring_monthly_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    



___
## get_default_partitioned_daily_column_checks_pattern
Returns UI model to show and edit the default configuration of the daily partitioned checks that are configured for a check pattern on a column level.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/get_default_partitioned_daily_column_checks_pattern.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/checks/column/{patternName}/partitioned/daily
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_model`](../models/common.md#checkcontainermodel)</span>||*[CheckContainerModel](../models/common.md#checkcontainermodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/default/checks/column/default/partitioned/daily^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "categories" : [ {
		    "category" : "sample_category",
		    "help_text" : "Sample help text",
		    "checks" : [ {
		      "check_name" : "sample_check",
		      "help_text" : "Sample help text",
		      "sensor_parameters" : [ ],
		      "sensor_name" : "sample_target/sample_category/sample_sensor",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_grouping" : false,
		      "disabled" : false,
		      "exclude_from_kpi" : false,
		      "include_in_sla" : false,
		      "configured" : false,
		      "can_edit" : false,
		      "can_run_checks" : false,
		      "can_delete_data" : false
		    } ]
		  } ],
		  "can_edit" : false,
		  "can_run_checks" : false,
		  "can_delete_data" : false
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_partitioned_daily_column_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_partitioned_daily_column_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_partitioned_daily_column_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_partitioned_daily_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_partitioned_daily_column_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_partitioned_daily_column_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_partitioned_daily_column_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_partitioned_daily_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    



___
## get_default_partitioned_monthly_column_checks_pattern
Returns UI model to show and edit the default configuration of the monthly partitioned checks that are configured for a check pattern on a column level.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/get_default_partitioned_monthly_column_checks_pattern.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/checks/column/{patternName}/partitioned/monthly
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_model`](../models/common.md#checkcontainermodel)</span>||*[CheckContainerModel](../models/common.md#checkcontainermodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/default/checks/column/default/partitioned/monthly^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "categories" : [ {
		    "category" : "sample_category",
		    "help_text" : "Sample help text",
		    "checks" : [ {
		      "check_name" : "sample_check",
		      "help_text" : "Sample help text",
		      "sensor_parameters" : [ ],
		      "sensor_name" : "sample_target/sample_category/sample_sensor",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_grouping" : false,
		      "disabled" : false,
		      "exclude_from_kpi" : false,
		      "include_in_sla" : false,
		      "configured" : false,
		      "can_edit" : false,
		      "can_run_checks" : false,
		      "can_delete_data" : false
		    } ]
		  } ],
		  "can_edit" : false,
		  "can_run_checks" : false,
		  "can_delete_data" : false
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_partitioned_monthly_column_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_partitioned_monthly_column_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_partitioned_monthly_column_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_partitioned_monthly_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_partitioned_monthly_column_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_partitioned_monthly_column_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_partitioned_monthly_column_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_partitioned_monthly_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    



___
## get_default_profiling_column_checks_pattern
Returns UI model to show and edit the default configuration of the profiling checks that are configured for a check pattern on a column level.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/get_default_profiling_column_checks_pattern.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/checks/column/{patternName}/profiling
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_model`](../models/common.md#checkcontainermodel)</span>||*[CheckContainerModel](../models/common.md#checkcontainermodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/default/checks/column/default/profiling^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "categories" : [ {
		    "category" : "sample_category",
		    "help_text" : "Sample help text",
		    "checks" : [ {
		      "check_name" : "sample_check",
		      "help_text" : "Sample help text",
		      "sensor_parameters" : [ ],
		      "sensor_name" : "sample_target/sample_category/sample_sensor",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_grouping" : false,
		      "disabled" : false,
		      "exclude_from_kpi" : false,
		      "include_in_sla" : false,
		      "configured" : false,
		      "can_edit" : false,
		      "can_run_checks" : false,
		      "can_delete_data" : false
		    } ]
		  } ],
		  "can_edit" : false,
		  "can_run_checks" : false,
		  "can_delete_data" : false
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_profiling_column_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_profiling_column_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_profiling_column_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_profiling_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_profiling_column_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_profiling_column_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import get_default_profiling_column_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_profiling_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckContainerModel(
			categories=[
				QualityCategoryModel(
					category='sample_category',
					help_text='Sample help text',
					checks=[
						CheckModel(
							check_name='sample_check',
							help_text='Sample help text',
							sensor_parameters=[
							
							],
							sensor_name='sample_target/sample_category/sample_sensor',
							quality_dimension='sample_quality_dimension',
							supports_grouping=False,
							standard=False,
							default_check=False,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							can_edit=False,
							can_run_checks=False,
							can_delete_data=False
						)
					]
				)
			],
			can_edit=False,
			can_run_checks=False,
			can_delete_data=False
		)
        ```
    
    
    



___
## update_default_column_checks_pattern
Updates an default column-level checks pattern by saving a full specification object

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/update_default_column_checks_pattern.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/default/checks/column/{patternName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Default checks pattern model|*[DefaultColumnChecksPatternModel](../models/default_column_check_patterns.md#defaultcolumncheckspatternmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/default/checks/column/default^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"pattern_name\":\"id columns not null\",\"pattern_spec\":{\"priority\":0,\"target\":{\"column\":\"id\"},\"monitoring_checks\":{\"daily\":{\"nulls\":{\"daily_nulls_count\":{\"error\":{\"max_count\":0}}}}}},\"can_edit\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_column_checks_pattern
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnDefaultChecksPatternSpec, \
	                                ColumnMonitoringCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnPartitionedCheckCategoriesSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                DefaultColumnChecksPatternModel, \
	                                MaxCountRule0ErrorParametersSpec, \
	                                TargetColumnPatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DefaultColumnChecksPatternModel(
		pattern_name='id columns not null',
		pattern_spec=ColumnDefaultChecksPatternSpec(
			priority=0,
			disabled=False,
			target=TargetColumnPatternSpec(column='id'),
			profiling_checks=ColumnProfilingCheckCategoriesSpec(comparisons=ColumnComparisonProfilingChecksSpecMap()),
			monitoring_checks=ColumnMonitoringCheckCategoriesSpec(
				daily=ColumnDailyMonitoringCheckCategoriesSpec(
					nulls=ColumnNullsDailyMonitoringChecksSpec(
						daily_nulls_count=ColumnNullsCountCheckSpec(
							parameters=ColumnNullsNullsCountSensorParametersSpec(),
							error=MaxCountRule0ErrorParametersSpec(max_count=0),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False
						)
					),
					comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
				)
			),
			partitioned_checks=ColumnPartitionedCheckCategoriesSpec()
		),
		can_edit=True
	)
	
	call_result = update_default_column_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_column_checks_pattern
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnDefaultChecksPatternSpec, \
	                                ColumnMonitoringCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnPartitionedCheckCategoriesSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                DefaultColumnChecksPatternModel, \
	                                MaxCountRule0ErrorParametersSpec, \
	                                TargetColumnPatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DefaultColumnChecksPatternModel(
		pattern_name='id columns not null',
		pattern_spec=ColumnDefaultChecksPatternSpec(
			priority=0,
			disabled=False,
			target=TargetColumnPatternSpec(column='id'),
			profiling_checks=ColumnProfilingCheckCategoriesSpec(comparisons=ColumnComparisonProfilingChecksSpecMap()),
			monitoring_checks=ColumnMonitoringCheckCategoriesSpec(
				daily=ColumnDailyMonitoringCheckCategoriesSpec(
					nulls=ColumnNullsDailyMonitoringChecksSpec(
						daily_nulls_count=ColumnNullsCountCheckSpec(
							parameters=ColumnNullsNullsCountSensorParametersSpec(),
							error=MaxCountRule0ErrorParametersSpec(max_count=0),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False
						)
					),
					comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
				)
			),
			partitioned_checks=ColumnPartitionedCheckCategoriesSpec()
		),
		can_edit=True
	)
	
	call_result = await update_default_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_column_checks_pattern
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnDefaultChecksPatternSpec, \
	                                ColumnMonitoringCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnPartitionedCheckCategoriesSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                DefaultColumnChecksPatternModel, \
	                                MaxCountRule0ErrorParametersSpec, \
	                                TargetColumnPatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DefaultColumnChecksPatternModel(
		pattern_name='id columns not null',
		pattern_spec=ColumnDefaultChecksPatternSpec(
			priority=0,
			disabled=False,
			target=TargetColumnPatternSpec(column='id'),
			profiling_checks=ColumnProfilingCheckCategoriesSpec(comparisons=ColumnComparisonProfilingChecksSpecMap()),
			monitoring_checks=ColumnMonitoringCheckCategoriesSpec(
				daily=ColumnDailyMonitoringCheckCategoriesSpec(
					nulls=ColumnNullsDailyMonitoringChecksSpec(
						daily_nulls_count=ColumnNullsCountCheckSpec(
							parameters=ColumnNullsNullsCountSensorParametersSpec(),
							error=MaxCountRule0ErrorParametersSpec(max_count=0),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False
						)
					),
					comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
				)
			),
			partitioned_checks=ColumnPartitionedCheckCategoriesSpec()
		),
		can_edit=True
	)
	
	call_result = update_default_column_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_column_checks_pattern
	from dqops.client.models import ColumnComparisonProfilingChecksSpecMap, \
	                                ColumnDefaultChecksPatternSpec, \
	                                ColumnMonitoringCheckCategoriesSpec, \
	                                ColumnNullsCountCheckSpec, \
	                                ColumnNullsNullsCountSensorParametersSpec, \
	                                ColumnNullsProfilingChecksSpec, \
	                                ColumnPartitionedCheckCategoriesSpec, \
	                                ColumnProfilingCheckCategoriesSpec, \
	                                DefaultColumnChecksPatternModel, \
	                                MaxCountRule0ErrorParametersSpec, \
	                                TargetColumnPatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DefaultColumnChecksPatternModel(
		pattern_name='id columns not null',
		pattern_spec=ColumnDefaultChecksPatternSpec(
			priority=0,
			disabled=False,
			target=TargetColumnPatternSpec(column='id'),
			profiling_checks=ColumnProfilingCheckCategoriesSpec(comparisons=ColumnComparisonProfilingChecksSpecMap()),
			monitoring_checks=ColumnMonitoringCheckCategoriesSpec(
				daily=ColumnDailyMonitoringCheckCategoriesSpec(
					nulls=ColumnNullsDailyMonitoringChecksSpec(
						daily_nulls_count=ColumnNullsCountCheckSpec(
							parameters=ColumnNullsNullsCountSensorParametersSpec(),
							error=MaxCountRule0ErrorParametersSpec(max_count=0),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False
						)
					),
					comparisons=ColumnComparisonDailyMonitoringChecksSpecMap()
				)
			),
			partitioned_checks=ColumnPartitionedCheckCategoriesSpec()
		),
		can_edit=True
	)
	
	call_result = await update_default_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_default_column_checks_pattern_target
Updates an default column-level checks pattern, changing only the target object

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/update_default_column_checks_pattern_target.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/default/checks/column/{patternName}/target
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Default checks pattern model|*[DefaultColumnChecksPatternListModel](../models/default_column_check_patterns.md#defaultcolumncheckspatternlistmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/default/checks/column/default/target^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"pattern_name\":\"default\",\"priority\":100,\"disabled\":false,\"target_column\":{\"connection\":\"dwh\",\"column\":\"id\"},\"can_edit\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_column_checks_pattern_target
	from dqops.client.models import DefaultColumnChecksPatternListModel, \
	                                TargetColumnPatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DefaultColumnChecksPatternListModel(
		pattern_name='default',
		priority=100,
		disabled=False,
		target_column=TargetColumnPatternSpec(
			column='id',
			connection='dwh'
		),
		can_edit=True
	)
	
	call_result = update_default_column_checks_pattern_target.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_column_checks_pattern_target
	from dqops.client.models import DefaultColumnChecksPatternListModel, \
	                                TargetColumnPatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DefaultColumnChecksPatternListModel(
		pattern_name='default',
		priority=100,
		disabled=False,
		target_column=TargetColumnPatternSpec(
			column='id',
			connection='dwh'
		),
		can_edit=True
	)
	
	call_result = await update_default_column_checks_pattern_target.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_column_checks_pattern_target
	from dqops.client.models import DefaultColumnChecksPatternListModel, \
	                                TargetColumnPatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DefaultColumnChecksPatternListModel(
		pattern_name='default',
		priority=100,
		disabled=False,
		target_column=TargetColumnPatternSpec(
			column='id',
			connection='dwh'
		),
		can_edit=True
	)
	
	call_result = update_default_column_checks_pattern_target.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_column_checks_pattern_target
	from dqops.client.models import DefaultColumnChecksPatternListModel, \
	                                TargetColumnPatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DefaultColumnChecksPatternListModel(
		pattern_name='default',
		priority=100,
		disabled=False,
		target_column=TargetColumnPatternSpec(
			column='id',
			connection='dwh'
		),
		can_edit=True
	)
	
	call_result = await update_default_column_checks_pattern_target.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_default_monitoring_daily_column_checks_pattern
New configuration of the default daily monitoring checks on a column level. These checks will be applied to columns.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/update_default_monitoring_daily_column_checks_pattern.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/default/checks/column/{patternName}/monitoring/daily
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality daily monitoring checks configuration|*[CheckContainerModel](../models/common.md#checkcontainermodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/default/checks/column/default/monitoring/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_monitoring_daily_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = update_default_monitoring_daily_column_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_monitoring_daily_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = await update_default_monitoring_daily_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_monitoring_daily_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = update_default_monitoring_daily_column_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_monitoring_daily_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = await update_default_monitoring_daily_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_default_monitoring_monthly_column_checks_pattern
New configuration of the default monthly monitoring checks on a column level. These checks will be applied to columns.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/update_default_monitoring_monthly_column_checks_pattern.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/default/checks/column/{patternName}/monitoring/monthly
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality monthly monitoring checks configuration|*[CheckContainerModel](../models/common.md#checkcontainermodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/default/checks/column/default/monitoring/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_monitoring_monthly_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = update_default_monitoring_monthly_column_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_monitoring_monthly_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = await update_default_monitoring_monthly_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_monitoring_monthly_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = update_default_monitoring_monthly_column_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_monitoring_monthly_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = await update_default_monitoring_monthly_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_default_partitioned_daily_column_checks_pattern
New configuration of the default daily partitioned checks on a column level. These checks will be applied to columns.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/update_default_partitioned_daily_column_checks_pattern.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/default/checks/column/{patternName}/partitioned/daily
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality daily partitioned checks configuration|*[CheckContainerModel](../models/common.md#checkcontainermodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/default/checks/column/default/partitioned/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_partitioned_daily_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = update_default_partitioned_daily_column_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_partitioned_daily_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = await update_default_partitioned_daily_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_partitioned_daily_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = update_default_partitioned_daily_column_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_partitioned_daily_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = await update_default_partitioned_daily_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_default_partitioned_monthly_column_checks_pattern
New configuration of the default monthly partitioned checks on a column level. These checks will be applied to columns.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/update_default_partitioned_monthly_column_checks_pattern.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/default/checks/column/{patternName}/partitioned/monthly
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality monthly partitioned checks configuration|*[CheckContainerModel](../models/common.md#checkcontainermodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/default/checks/column/default/partitioned/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_partitioned_monthly_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = update_default_partitioned_monthly_column_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_partitioned_monthly_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = await update_default_partitioned_monthly_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_partitioned_monthly_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = update_default_partitioned_monthly_column_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_partitioned_monthly_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = await update_default_partitioned_monthly_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_default_profiling_column_checks_pattern
New configuration of the default profiling checks on a column level. These checks will be applied to columns.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_column_check_patterns/update_default_profiling_column_checks_pattern.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/default/checks/column/{patternName}/profiling
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality profiling checks configuration|*[CheckContainerModel](../models/common.md#checkcontainermodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/default/checks/column/default/profiling^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_profiling_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = update_default_profiling_column_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_profiling_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = await update_default_profiling_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_profiling_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = update_default_profiling_column_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_column_check_patterns import update_default_profiling_column_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckContainerModel(
		categories=[
			QualityCategoryModel(
				category='sample_category',
				help_text='Sample help text',
				checks=[
					CheckModel(
						check_name='sample_check',
						help_text='Sample help text',
						sensor_parameters=[
						
						],
						sensor_name='sample_target/sample_category/sample_sensor',
						quality_dimension='sample_quality_dimension',
						supports_grouping=False,
						standard=False,
						default_check=False,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						can_edit=False,
						can_run_checks=False,
						can_delete_data=False
					)
				]
			)
		],
		can_edit=False,
		can_run_checks=False,
		can_delete_data=False
	)
	
	call_result = await update_default_profiling_column_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



