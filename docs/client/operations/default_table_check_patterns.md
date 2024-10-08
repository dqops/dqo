---
title: DQOps REST API default_table_check_patterns operations
---
# DQOps REST API default_table_check_patterns operations
Operations for managing the configuration of the default table-level checks for tables matching a pattern.


___
## copy_from_default_table_checks_pattern
Creates (adds) a copy of an existing default table-level checks pattern configuration, under a new name.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/copy_from_default_table_checks_pattern.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/default/checks/table/{targetPatternName}/copyfrom/{sourcePatternName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`target_pattern_name`</span>|Target pattern name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`source_pattern_name`</span>|Source pattern name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/default/checks/table/default/copyfrom/default^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import copy_from_default_table_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = copy_from_default_table_checks_pattern.sync(
	    'default',
	    'default',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import copy_from_default_table_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await copy_from_default_table_checks_pattern.asyncio(
	    'default',
	    'default',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import copy_from_default_table_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = copy_from_default_table_checks_pattern.sync(
	    'default',
	    'default',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import copy_from_default_table_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await copy_from_default_table_checks_pattern.asyncio(
	    'default',
	    'default',
	    client=dqops_client
	)
	
    ```

    



___
## create_default_table_checks_pattern
Creates (adds) a new default table-level checks pattern configuration by saving a full specification object.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/create_default_table_checks_pattern.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/default/checks/table/{patternName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Default checks pattern model|*[DefaultTableChecksPatternModel](../models/default_table_check_patterns.md#defaulttablecheckspatternmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/default/checks/table/default^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"pattern_name\":\"default\",\"pattern_spec\":{\"priority\":0,\"monitoring_checks\":{\"daily\":{\"volume\":{\"daily_row_count\":{\"warning\":{\"min_count\":1}}}}}},\"can_edit\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import create_default_table_checks_pattern
	from dqops.client.models import DefaultTableChecksPatternModel, \
	                                MinCountRule1ParametersSpec, \
	                                TableDefaultChecksPatternSpec, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TargetTablePatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DefaultTableChecksPatternModel(
		pattern_name='default',
		pattern_spec=TableDefaultChecksPatternSpec(
			priority=0,
			disabled=False,
			target=TargetTablePatternSpec(),
			profiling_checks=TableProfilingCheckCategoriesSpec(comparisons=TableComparisonProfilingChecksSpecMap()),
			monitoring_checks=TableMonitoringCheckCategoriesSpec(
				daily=TableDailyMonitoringCheckCategoriesSpec(
					volume=TableVolumeDailyMonitoringChecksSpec(
						daily_row_count=TableRowCountCheckSpec(
							parameters=TableVolumeRowCountSensorParametersSpec(),
							warning=MinCountRule1ParametersSpec(min_count=1),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							always_collect_error_samples=False
						)
					),
					comparisons=TableComparisonDailyMonitoringChecksSpecMap()
				)
			),
			partitioned_checks=TablePartitionedCheckCategoriesSpec()
		),
		can_edit=True
	)
	
	call_result = create_default_table_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import create_default_table_checks_pattern
	from dqops.client.models import DefaultTableChecksPatternModel, \
	                                MinCountRule1ParametersSpec, \
	                                TableDefaultChecksPatternSpec, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TargetTablePatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DefaultTableChecksPatternModel(
		pattern_name='default',
		pattern_spec=TableDefaultChecksPatternSpec(
			priority=0,
			disabled=False,
			target=TargetTablePatternSpec(),
			profiling_checks=TableProfilingCheckCategoriesSpec(comparisons=TableComparisonProfilingChecksSpecMap()),
			monitoring_checks=TableMonitoringCheckCategoriesSpec(
				daily=TableDailyMonitoringCheckCategoriesSpec(
					volume=TableVolumeDailyMonitoringChecksSpec(
						daily_row_count=TableRowCountCheckSpec(
							parameters=TableVolumeRowCountSensorParametersSpec(),
							warning=MinCountRule1ParametersSpec(min_count=1),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							always_collect_error_samples=False
						)
					),
					comparisons=TableComparisonDailyMonitoringChecksSpecMap()
				)
			),
			partitioned_checks=TablePartitionedCheckCategoriesSpec()
		),
		can_edit=True
	)
	
	call_result = await create_default_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import create_default_table_checks_pattern
	from dqops.client.models import DefaultTableChecksPatternModel, \
	                                MinCountRule1ParametersSpec, \
	                                TableDefaultChecksPatternSpec, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TargetTablePatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DefaultTableChecksPatternModel(
		pattern_name='default',
		pattern_spec=TableDefaultChecksPatternSpec(
			priority=0,
			disabled=False,
			target=TargetTablePatternSpec(),
			profiling_checks=TableProfilingCheckCategoriesSpec(comparisons=TableComparisonProfilingChecksSpecMap()),
			monitoring_checks=TableMonitoringCheckCategoriesSpec(
				daily=TableDailyMonitoringCheckCategoriesSpec(
					volume=TableVolumeDailyMonitoringChecksSpec(
						daily_row_count=TableRowCountCheckSpec(
							parameters=TableVolumeRowCountSensorParametersSpec(),
							warning=MinCountRule1ParametersSpec(min_count=1),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							always_collect_error_samples=False
						)
					),
					comparisons=TableComparisonDailyMonitoringChecksSpecMap()
				)
			),
			partitioned_checks=TablePartitionedCheckCategoriesSpec()
		),
		can_edit=True
	)
	
	call_result = create_default_table_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import create_default_table_checks_pattern
	from dqops.client.models import DefaultTableChecksPatternModel, \
	                                MinCountRule1ParametersSpec, \
	                                TableDefaultChecksPatternSpec, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TargetTablePatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DefaultTableChecksPatternModel(
		pattern_name='default',
		pattern_spec=TableDefaultChecksPatternSpec(
			priority=0,
			disabled=False,
			target=TargetTablePatternSpec(),
			profiling_checks=TableProfilingCheckCategoriesSpec(comparisons=TableComparisonProfilingChecksSpecMap()),
			monitoring_checks=TableMonitoringCheckCategoriesSpec(
				daily=TableDailyMonitoringCheckCategoriesSpec(
					volume=TableVolumeDailyMonitoringChecksSpec(
						daily_row_count=TableRowCountCheckSpec(
							parameters=TableVolumeRowCountSensorParametersSpec(),
							warning=MinCountRule1ParametersSpec(min_count=1),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							always_collect_error_samples=False
						)
					),
					comparisons=TableComparisonDailyMonitoringChecksSpecMap()
				)
			),
			partitioned_checks=TablePartitionedCheckCategoriesSpec()
		),
		can_edit=True
	)
	
	call_result = await create_default_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## create_default_table_checks_pattern_target
Creates (adds) a new default table-level checks pattern configuration.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/create_default_table_checks_pattern_target.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/default/checks/table/{patternName}/target
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Default checks pattern model with only the target filters|*[DefaultTableChecksPatternListModel](../models/default_table_check_patterns.md#defaulttablecheckspatternlistmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/default/checks/table/default/target^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"pattern_name\":\"default\",\"priority\":100,\"disabled\":false,\"target_table\":{\"connection\":\"dwh\",\"schema\":\"public\",\"table\":\"fact_*\"},\"can_edit\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import create_default_table_checks_pattern_target
	from dqops.client.models import DefaultTableChecksPatternListModel, \
	                                TargetTablePatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DefaultTableChecksPatternListModel(
		pattern_name='default',
		priority=100,
		disabled=False,
		target_table=TargetTablePatternSpec(
			connection='dwh',
			schema='public',
			table='fact_*'
		),
		can_edit=True
	)
	
	call_result = create_default_table_checks_pattern_target.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import create_default_table_checks_pattern_target
	from dqops.client.models import DefaultTableChecksPatternListModel, \
	                                TargetTablePatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DefaultTableChecksPatternListModel(
		pattern_name='default',
		priority=100,
		disabled=False,
		target_table=TargetTablePatternSpec(
			connection='dwh',
			schema='public',
			table='fact_*'
		),
		can_edit=True
	)
	
	call_result = await create_default_table_checks_pattern_target.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import create_default_table_checks_pattern_target
	from dqops.client.models import DefaultTableChecksPatternListModel, \
	                                TargetTablePatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DefaultTableChecksPatternListModel(
		pattern_name='default',
		priority=100,
		disabled=False,
		target_table=TargetTablePatternSpec(
			connection='dwh',
			schema='public',
			table='fact_*'
		),
		can_edit=True
	)
	
	call_result = create_default_table_checks_pattern_target.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import create_default_table_checks_pattern_target
	from dqops.client.models import DefaultTableChecksPatternListModel, \
	                                TargetTablePatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DefaultTableChecksPatternListModel(
		pattern_name='default',
		priority=100,
		disabled=False,
		target_table=TargetTablePatternSpec(
			connection='dwh',
			schema='public',
			table='fact_*'
		),
		can_edit=True
	)
	
	call_result = await create_default_table_checks_pattern_target.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## delete_default_table_checks_pattern
Deletes a default table-level checks pattern

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/delete_default_table_checks_pattern.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/default/checks/table/{patternName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X DELETE http://localhost:8888/api/default/checks/table/default^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import delete_default_table_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = delete_default_table_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import delete_default_table_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await delete_default_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import delete_default_table_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = delete_default_table_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import delete_default_table_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await delete_default_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    



___
## get_all_default_table_checks_patterns
Returns a flat list of all table-level default check patterns configured for this instance. Default checks are applied on tables dynamically.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/get_all_default_table_checks_patterns.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/checks/table
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`default_table_checks_pattern_list_model`</span>||*List[[DefaultTableChecksPatternListModel](../models/default_table_check_patterns.md#defaulttablecheckspatternlistmodel)]*|








**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/default/checks/table^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "pattern_name" : "default",
		  "priority" : 100,
		  "disabled" : false,
		  "target_table" : {
		    "connection" : "dwh",
		    "schema" : "public",
		    "table" : "fact_*"
		  },
		  "can_edit" : true
		}, {
		  "pattern_name" : "default",
		  "priority" : 100,
		  "disabled" : false,
		  "target_table" : {
		    "connection" : "dwh",
		    "schema" : "public",
		    "table" : "fact_*"
		  },
		  "can_edit" : true
		}, {
		  "pattern_name" : "default",
		  "priority" : 100,
		  "disabled" : false,
		  "target_table" : {
		    "connection" : "dwh",
		    "schema" : "public",
		    "table" : "fact_*"
		  },
		  "can_edit" : true
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import get_all_default_table_checks_patterns
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_default_table_checks_patterns.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			DefaultTableChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			),
			DefaultTableChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			),
			DefaultTableChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import get_all_default_table_checks_patterns
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_default_table_checks_patterns.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			DefaultTableChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			),
			DefaultTableChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			),
			DefaultTableChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import get_all_default_table_checks_patterns
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_default_table_checks_patterns.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			DefaultTableChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			),
			DefaultTableChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			),
			DefaultTableChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import get_all_default_table_checks_patterns
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_default_table_checks_patterns.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			DefaultTableChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			),
			DefaultTableChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			),
			DefaultTableChecksPatternListModel(
				pattern_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			)
		]
        ```
    
    
    



___
## get_default_monitoring_daily_table_checks_pattern
Returns UI model to show and edit the default configuration of the daily monitoring checks that are configured for a check pattern on a table level.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/get_default_monitoring_daily_table_checks_pattern.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/checks/table/{patternName}/monitoring/daily
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
    curl http://localhost:8888/api/default/checks/table/default/monitoring/daily^
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
		      "sensor_name" : "sample_target/sample_category/table/volume/row_count",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_error_sampling" : false,
		      "supports_grouping" : false,
		      "default_severity" : "error",
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
	from dqops.client.api.default_table_check_patterns import get_default_monitoring_daily_table_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_monitoring_daily_table_checks_pattern.sync(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
	from dqops.client.api.default_table_check_patterns import get_default_monitoring_daily_table_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_monitoring_daily_table_checks_pattern.asyncio(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
	from dqops.client.api.default_table_check_patterns import get_default_monitoring_daily_table_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_monitoring_daily_table_checks_pattern.sync(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
	from dqops.client.api.default_table_check_patterns import get_default_monitoring_daily_table_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_monitoring_daily_table_checks_pattern.asyncio(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
## get_default_monitoring_monthly_table_checks_pattern
Returns UI model to show and edit the default configuration of the monthly monitoring checks that are configured for a check pattern on a table level.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/get_default_monitoring_monthly_table_checks_pattern.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/checks/table/{patternName}/monitoring/monthly
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
    curl http://localhost:8888/api/default/checks/table/default/monitoring/monthly^
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
		      "sensor_name" : "sample_target/sample_category/table/volume/row_count",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_error_sampling" : false,
		      "supports_grouping" : false,
		      "default_severity" : "error",
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
	from dqops.client.api.default_table_check_patterns import get_default_monitoring_monthly_table_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_monitoring_monthly_table_checks_pattern.sync(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
	from dqops.client.api.default_table_check_patterns import get_default_monitoring_monthly_table_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_monitoring_monthly_table_checks_pattern.asyncio(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
	from dqops.client.api.default_table_check_patterns import get_default_monitoring_monthly_table_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_monitoring_monthly_table_checks_pattern.sync(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
	from dqops.client.api.default_table_check_patterns import get_default_monitoring_monthly_table_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_monitoring_monthly_table_checks_pattern.asyncio(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
## get_default_partitioned_daily_table_checks_pattern
Returns UI model to show and edit the default configuration of the daily partitioned checks that are configured for a check pattern on a table level.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/get_default_partitioned_daily_table_checks_pattern.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/checks/table/{patternName}/partitioned/daily
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
    curl http://localhost:8888/api/default/checks/table/default/partitioned/daily^
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
		      "sensor_name" : "sample_target/sample_category/table/volume/row_count",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_error_sampling" : false,
		      "supports_grouping" : false,
		      "default_severity" : "error",
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
	from dqops.client.api.default_table_check_patterns import get_default_partitioned_daily_table_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_partitioned_daily_table_checks_pattern.sync(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
	from dqops.client.api.default_table_check_patterns import get_default_partitioned_daily_table_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_partitioned_daily_table_checks_pattern.asyncio(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
	from dqops.client.api.default_table_check_patterns import get_default_partitioned_daily_table_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_partitioned_daily_table_checks_pattern.sync(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
	from dqops.client.api.default_table_check_patterns import get_default_partitioned_daily_table_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_partitioned_daily_table_checks_pattern.asyncio(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
## get_default_partitioned_monthly_table_checks_pattern
Returns UI model to show and edit the default configuration of the monthly partitioned checks that are configured for a check pattern on a table level.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/get_default_partitioned_monthly_table_checks_pattern.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/checks/table/{patternName}/partitioned/monthly
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
    curl http://localhost:8888/api/default/checks/table/default/partitioned/monthly^
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
		      "sensor_name" : "sample_target/sample_category/table/volume/row_count",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_error_sampling" : false,
		      "supports_grouping" : false,
		      "default_severity" : "error",
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
	from dqops.client.api.default_table_check_patterns import get_default_partitioned_monthly_table_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_partitioned_monthly_table_checks_pattern.sync(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
	from dqops.client.api.default_table_check_patterns import get_default_partitioned_monthly_table_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_partitioned_monthly_table_checks_pattern.asyncio(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
	from dqops.client.api.default_table_check_patterns import get_default_partitioned_monthly_table_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_partitioned_monthly_table_checks_pattern.sync(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
	from dqops.client.api.default_table_check_patterns import get_default_partitioned_monthly_table_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_partitioned_monthly_table_checks_pattern.asyncio(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
## get_default_profiling_table_checks_pattern
Returns UI model to show and edit the default configuration of the profiling checks that are configured for a check pattern on a table level.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/get_default_profiling_table_checks_pattern.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/checks/table/{patternName}/profiling
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
    curl http://localhost:8888/api/default/checks/table/default/profiling^
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
		      "sensor_name" : "sample_target/sample_category/table/volume/row_count",
		      "quality_dimension" : "sample_quality_dimension",
		      "supports_error_sampling" : false,
		      "supports_grouping" : false,
		      "default_severity" : "error",
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
	from dqops.client.api.default_table_check_patterns import get_default_profiling_table_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_profiling_table_checks_pattern.sync(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
	from dqops.client.api.default_table_check_patterns import get_default_profiling_table_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_profiling_table_checks_pattern.asyncio(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
	from dqops.client.api.default_table_check_patterns import get_default_profiling_table_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_profiling_table_checks_pattern.sync(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
	from dqops.client.api.default_table_check_patterns import get_default_profiling_table_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_profiling_table_checks_pattern.asyncio(
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
							sensor_name='sample_target/sample_category/table/volume/row_count',
							quality_dimension='sample_quality_dimension',
							supports_error_sampling=False,
							supports_grouping=False,
							standard=False,
							default_check=False,
							default_severity=DefaultRuleSeverityLevel.ERROR,
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							configured=False,
							always_collect_error_samples=False,
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
## get_default_table_checks_pattern
Returns a default checks pattern definition as a full specification object

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/get_default_table_checks_pattern.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/checks/table/{patternName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`default_table_checks_pattern_model`](../models/default_table_check_patterns.md#defaulttablecheckspatternmodel)</span>||*[DefaultTableChecksPatternModel](../models/default_table_check_patterns.md#defaulttablecheckspatternmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Table pattern name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/default/checks/table/default^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "pattern_name" : "default",
		  "pattern_spec" : {
		    "priority" : 0,
		    "monitoring_checks" : {
		      "daily" : {
		        "volume" : {
		          "daily_row_count" : {
		            "warning" : {
		              "min_count" : 1
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
	from dqops.client.api.default_table_check_patterns import get_default_table_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_table_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DefaultTableChecksPatternModel(
			pattern_name='default',
			pattern_spec=TableDefaultChecksPatternSpec(
				priority=0,
				disabled=False,
				target=TargetTablePatternSpec(),
				profiling_checks=TableProfilingCheckCategoriesSpec(comparisons=TableComparisonProfilingChecksSpecMap()),
				monitoring_checks=TableMonitoringCheckCategoriesSpec(
					daily=TableDailyMonitoringCheckCategoriesSpec(
						volume=TableVolumeDailyMonitoringChecksSpec(
							daily_row_count=TableRowCountCheckSpec(
								parameters=TableVolumeRowCountSensorParametersSpec(),
								warning=MinCountRule1ParametersSpec(min_count=1),
								disabled=False,
								exclude_from_kpi=False,
								include_in_sla=False,
								always_collect_error_samples=False
							)
						),
						comparisons=TableComparisonDailyMonitoringChecksSpecMap()
					)
				),
				partitioned_checks=TablePartitionedCheckCategoriesSpec()
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import get_default_table_checks_pattern
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DefaultTableChecksPatternModel(
			pattern_name='default',
			pattern_spec=TableDefaultChecksPatternSpec(
				priority=0,
				disabled=False,
				target=TargetTablePatternSpec(),
				profiling_checks=TableProfilingCheckCategoriesSpec(comparisons=TableComparisonProfilingChecksSpecMap()),
				monitoring_checks=TableMonitoringCheckCategoriesSpec(
					daily=TableDailyMonitoringCheckCategoriesSpec(
						volume=TableVolumeDailyMonitoringChecksSpec(
							daily_row_count=TableRowCountCheckSpec(
								parameters=TableVolumeRowCountSensorParametersSpec(),
								warning=MinCountRule1ParametersSpec(min_count=1),
								disabled=False,
								exclude_from_kpi=False,
								include_in_sla=False,
								always_collect_error_samples=False
							)
						),
						comparisons=TableComparisonDailyMonitoringChecksSpecMap()
					)
				),
				partitioned_checks=TablePartitionedCheckCategoriesSpec()
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import get_default_table_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_table_checks_pattern.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DefaultTableChecksPatternModel(
			pattern_name='default',
			pattern_spec=TableDefaultChecksPatternSpec(
				priority=0,
				disabled=False,
				target=TargetTablePatternSpec(),
				profiling_checks=TableProfilingCheckCategoriesSpec(comparisons=TableComparisonProfilingChecksSpecMap()),
				monitoring_checks=TableMonitoringCheckCategoriesSpec(
					daily=TableDailyMonitoringCheckCategoriesSpec(
						volume=TableVolumeDailyMonitoringChecksSpec(
							daily_row_count=TableRowCountCheckSpec(
								parameters=TableVolumeRowCountSensorParametersSpec(),
								warning=MinCountRule1ParametersSpec(min_count=1),
								disabled=False,
								exclude_from_kpi=False,
								include_in_sla=False,
								always_collect_error_samples=False
							)
						),
						comparisons=TableComparisonDailyMonitoringChecksSpecMap()
					)
				),
				partitioned_checks=TablePartitionedCheckCategoriesSpec()
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import get_default_table_checks_pattern
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DefaultTableChecksPatternModel(
			pattern_name='default',
			pattern_spec=TableDefaultChecksPatternSpec(
				priority=0,
				disabled=False,
				target=TargetTablePatternSpec(),
				profiling_checks=TableProfilingCheckCategoriesSpec(comparisons=TableComparisonProfilingChecksSpecMap()),
				monitoring_checks=TableMonitoringCheckCategoriesSpec(
					daily=TableDailyMonitoringCheckCategoriesSpec(
						volume=TableVolumeDailyMonitoringChecksSpec(
							daily_row_count=TableRowCountCheckSpec(
								parameters=TableVolumeRowCountSensorParametersSpec(),
								warning=MinCountRule1ParametersSpec(min_count=1),
								disabled=False,
								exclude_from_kpi=False,
								include_in_sla=False,
								always_collect_error_samples=False
							)
						),
						comparisons=TableComparisonDailyMonitoringChecksSpecMap()
					)
				),
				partitioned_checks=TablePartitionedCheckCategoriesSpec()
			),
			can_edit=True
		)
        ```
    
    
    



___
## get_default_table_checks_pattern_target
Returns a default checks pattern definition

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/get_default_table_checks_pattern_target.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/default/checks/table/{patternName}/target
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`default_table_checks_pattern_list_model`](../models/default_table_check_patterns.md#defaulttablecheckspatternlistmodel)</span>||*[DefaultTableChecksPatternListModel](../models/default_table_check_patterns.md#defaulttablecheckspatternlistmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Table pattern name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/default/checks/table/default/target^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "pattern_name" : "default",
		  "priority" : 100,
		  "disabled" : false,
		  "target_table" : {
		    "connection" : "dwh",
		    "schema" : "public",
		    "table" : "fact_*"
		  },
		  "can_edit" : true
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import get_default_table_checks_pattern_target
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_table_checks_pattern_target.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DefaultTableChecksPatternListModel(
			pattern_name='default',
			priority=100,
			disabled=False,
			target_table=TargetTablePatternSpec(
				connection='dwh',
				schema='public',
				table='fact_*'
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import get_default_table_checks_pattern_target
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_table_checks_pattern_target.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DefaultTableChecksPatternListModel(
			pattern_name='default',
			priority=100,
			disabled=False,
			target_table=TargetTablePatternSpec(
				connection='dwh',
				schema='public',
				table='fact_*'
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import get_default_table_checks_pattern_target
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_table_checks_pattern_target.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DefaultTableChecksPatternListModel(
			pattern_name='default',
			priority=100,
			disabled=False,
			target_table=TargetTablePatternSpec(
				connection='dwh',
				schema='public',
				table='fact_*'
			),
			can_edit=True
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import get_default_table_checks_pattern_target
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_table_checks_pattern_target.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        DefaultTableChecksPatternListModel(
			pattern_name='default',
			priority=100,
			disabled=False,
			target_table=TargetTablePatternSpec(
				connection='dwh',
				schema='public',
				table='fact_*'
			),
			can_edit=True
		)
        ```
    
    
    



___
## update_default_monitoring_daily_table_checks_pattern
New configuration of the default daily monitoring checks on a table level. These checks will be applied to tables.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/update_default_monitoring_daily_table_checks_pattern.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/default/checks/table/{patternName}/monitoring/daily
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
    curl -X PUT http://localhost:8888/api/default/checks/table/default/monitoring/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/table/volume/row_count\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_error_sampling\":false,\"supports_grouping\":false,\"default_severity\":\"error\",\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_monitoring_daily_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = update_default_monitoring_daily_table_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_monitoring_daily_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = await update_default_monitoring_daily_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_monitoring_daily_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = update_default_monitoring_daily_table_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_monitoring_daily_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = await update_default_monitoring_daily_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_default_monitoring_monthly_table_checks_pattern
New configuration of the default monthly monitoring checks on a table level. These checks will be applied to tables.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/update_default_monitoring_monthly_table_checks_pattern.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/default/checks/table/{patternName}/monitoring/monthly
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
    curl -X PUT http://localhost:8888/api/default/checks/table/default/monitoring/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/table/volume/row_count\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_error_sampling\":false,\"supports_grouping\":false,\"default_severity\":\"error\",\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_monitoring_monthly_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = update_default_monitoring_monthly_table_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_monitoring_monthly_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = await update_default_monitoring_monthly_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_monitoring_monthly_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = update_default_monitoring_monthly_table_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_monitoring_monthly_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = await update_default_monitoring_monthly_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_default_partitioned_daily_table_checks_pattern
New configuration of the default daily partitioned checks on a table level. These checks will be applied to tables.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/update_default_partitioned_daily_table_checks_pattern.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/default/checks/table/{patternName}/partitioned/daily
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
    curl -X PUT http://localhost:8888/api/default/checks/table/default/partitioned/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/table/volume/row_count\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_error_sampling\":false,\"supports_grouping\":false,\"default_severity\":\"error\",\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_partitioned_daily_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = update_default_partitioned_daily_table_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_partitioned_daily_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = await update_default_partitioned_daily_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_partitioned_daily_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = update_default_partitioned_daily_table_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_partitioned_daily_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = await update_default_partitioned_daily_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_default_partitioned_monthly_table_checks_pattern
New configuration of the default monthly partitioned checks on a table level. These checks will be applied to tables.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/update_default_partitioned_monthly_table_checks_pattern.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/default/checks/table/{patternName}/partitioned/monthly
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
    curl -X PUT http://localhost:8888/api/default/checks/table/default/partitioned/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/table/volume/row_count\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_error_sampling\":false,\"supports_grouping\":false,\"default_severity\":\"error\",\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_partitioned_monthly_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = update_default_partitioned_monthly_table_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_partitioned_monthly_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = await update_default_partitioned_monthly_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_partitioned_monthly_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = update_default_partitioned_monthly_table_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_partitioned_monthly_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = await update_default_partitioned_monthly_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_default_profiling_table_checks_pattern
New configuration of the default profiling checks on a table level. These checks will be applied to tables.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/update_default_profiling_table_checks_pattern.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/default/checks/table/{patternName}/profiling
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
    curl -X PUT http://localhost:8888/api/default/checks/table/default/profiling^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/table/volume/row_count\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_error_sampling\":false,\"supports_grouping\":false,\"default_severity\":\"error\",\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_profiling_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = update_default_profiling_table_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_profiling_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = await update_default_profiling_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_profiling_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = update_default_profiling_table_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_profiling_table_checks_pattern
	from dqops.client.models import CheckContainerModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
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
						sensor_name='sample_target/sample_category/table/volume/row_count',
						quality_dimension='sample_quality_dimension',
						supports_error_sampling=False,
						supports_grouping=False,
						standard=False,
						default_check=False,
						default_severity=DefaultRuleSeverityLevel.ERROR,
						disabled=False,
						exclude_from_kpi=False,
						include_in_sla=False,
						configured=False,
						always_collect_error_samples=False,
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
	
	call_result = await update_default_profiling_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_default_table_checks_pattern
Updates an default table-level checks pattern by saving a full specification object

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/update_default_table_checks_pattern.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/default/checks/table/{patternName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Default checks pattern model|*[DefaultTableChecksPatternModel](../models/default_table_check_patterns.md#defaulttablecheckspatternmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/default/checks/table/default^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"pattern_name\":\"default\",\"pattern_spec\":{\"priority\":0,\"monitoring_checks\":{\"daily\":{\"volume\":{\"daily_row_count\":{\"warning\":{\"min_count\":1}}}}}},\"can_edit\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_table_checks_pattern
	from dqops.client.models import DefaultTableChecksPatternModel, \
	                                MinCountRule1ParametersSpec, \
	                                TableDefaultChecksPatternSpec, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TargetTablePatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DefaultTableChecksPatternModel(
		pattern_name='default',
		pattern_spec=TableDefaultChecksPatternSpec(
			priority=0,
			disabled=False,
			target=TargetTablePatternSpec(),
			profiling_checks=TableProfilingCheckCategoriesSpec(comparisons=TableComparisonProfilingChecksSpecMap()),
			monitoring_checks=TableMonitoringCheckCategoriesSpec(
				daily=TableDailyMonitoringCheckCategoriesSpec(
					volume=TableVolumeDailyMonitoringChecksSpec(
						daily_row_count=TableRowCountCheckSpec(
							parameters=TableVolumeRowCountSensorParametersSpec(),
							warning=MinCountRule1ParametersSpec(min_count=1),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							always_collect_error_samples=False
						)
					),
					comparisons=TableComparisonDailyMonitoringChecksSpecMap()
				)
			),
			partitioned_checks=TablePartitionedCheckCategoriesSpec()
		),
		can_edit=True
	)
	
	call_result = update_default_table_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_table_checks_pattern
	from dqops.client.models import DefaultTableChecksPatternModel, \
	                                MinCountRule1ParametersSpec, \
	                                TableDefaultChecksPatternSpec, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TargetTablePatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DefaultTableChecksPatternModel(
		pattern_name='default',
		pattern_spec=TableDefaultChecksPatternSpec(
			priority=0,
			disabled=False,
			target=TargetTablePatternSpec(),
			profiling_checks=TableProfilingCheckCategoriesSpec(comparisons=TableComparisonProfilingChecksSpecMap()),
			monitoring_checks=TableMonitoringCheckCategoriesSpec(
				daily=TableDailyMonitoringCheckCategoriesSpec(
					volume=TableVolumeDailyMonitoringChecksSpec(
						daily_row_count=TableRowCountCheckSpec(
							parameters=TableVolumeRowCountSensorParametersSpec(),
							warning=MinCountRule1ParametersSpec(min_count=1),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							always_collect_error_samples=False
						)
					),
					comparisons=TableComparisonDailyMonitoringChecksSpecMap()
				)
			),
			partitioned_checks=TablePartitionedCheckCategoriesSpec()
		),
		can_edit=True
	)
	
	call_result = await update_default_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_table_checks_pattern
	from dqops.client.models import DefaultTableChecksPatternModel, \
	                                MinCountRule1ParametersSpec, \
	                                TableDefaultChecksPatternSpec, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TargetTablePatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DefaultTableChecksPatternModel(
		pattern_name='default',
		pattern_spec=TableDefaultChecksPatternSpec(
			priority=0,
			disabled=False,
			target=TargetTablePatternSpec(),
			profiling_checks=TableProfilingCheckCategoriesSpec(comparisons=TableComparisonProfilingChecksSpecMap()),
			monitoring_checks=TableMonitoringCheckCategoriesSpec(
				daily=TableDailyMonitoringCheckCategoriesSpec(
					volume=TableVolumeDailyMonitoringChecksSpec(
						daily_row_count=TableRowCountCheckSpec(
							parameters=TableVolumeRowCountSensorParametersSpec(),
							warning=MinCountRule1ParametersSpec(min_count=1),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							always_collect_error_samples=False
						)
					),
					comparisons=TableComparisonDailyMonitoringChecksSpecMap()
				)
			),
			partitioned_checks=TablePartitionedCheckCategoriesSpec()
		),
		can_edit=True
	)
	
	call_result = update_default_table_checks_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_table_checks_pattern
	from dqops.client.models import DefaultTableChecksPatternModel, \
	                                MinCountRule1ParametersSpec, \
	                                TableDefaultChecksPatternSpec, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TargetTablePatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DefaultTableChecksPatternModel(
		pattern_name='default',
		pattern_spec=TableDefaultChecksPatternSpec(
			priority=0,
			disabled=False,
			target=TargetTablePatternSpec(),
			profiling_checks=TableProfilingCheckCategoriesSpec(comparisons=TableComparisonProfilingChecksSpecMap()),
			monitoring_checks=TableMonitoringCheckCategoriesSpec(
				daily=TableDailyMonitoringCheckCategoriesSpec(
					volume=TableVolumeDailyMonitoringChecksSpec(
						daily_row_count=TableRowCountCheckSpec(
							parameters=TableVolumeRowCountSensorParametersSpec(),
							warning=MinCountRule1ParametersSpec(min_count=1),
							disabled=False,
							exclude_from_kpi=False,
							include_in_sla=False,
							always_collect_error_samples=False
						)
					),
					comparisons=TableComparisonDailyMonitoringChecksSpecMap()
				)
			),
			partitioned_checks=TablePartitionedCheckCategoriesSpec()
		),
		can_edit=True
	)
	
	call_result = await update_default_table_checks_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_default_table_checks_pattern_target
Updates an default table-level checks pattern, changing only the target object

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/default_table_check_patterns/update_default_table_checks_pattern_target.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/default/checks/table/{patternName}/target
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Default checks pattern model|*[DefaultTableChecksPatternListModel](../models/default_table_check_patterns.md#defaulttablecheckspatternlistmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/default/checks/table/default/target^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"pattern_name\":\"default\",\"priority\":100,\"disabled\":false,\"target_table\":{\"connection\":\"dwh\",\"schema\":\"public\",\"table\":\"fact_*\"},\"can_edit\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_table_checks_pattern_target
	from dqops.client.models import DefaultTableChecksPatternListModel, \
	                                TargetTablePatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DefaultTableChecksPatternListModel(
		pattern_name='default',
		priority=100,
		disabled=False,
		target_table=TargetTablePatternSpec(
			connection='dwh',
			schema='public',
			table='fact_*'
		),
		can_edit=True
	)
	
	call_result = update_default_table_checks_pattern_target.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_table_checks_pattern_target
	from dqops.client.models import DefaultTableChecksPatternListModel, \
	                                TargetTablePatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DefaultTableChecksPatternListModel(
		pattern_name='default',
		priority=100,
		disabled=False,
		target_table=TargetTablePatternSpec(
			connection='dwh',
			schema='public',
			table='fact_*'
		),
		can_edit=True
	)
	
	call_result = await update_default_table_checks_pattern_target.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_table_checks_pattern_target
	from dqops.client.models import DefaultTableChecksPatternListModel, \
	                                TargetTablePatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DefaultTableChecksPatternListModel(
		pattern_name='default',
		priority=100,
		disabled=False,
		target_table=TargetTablePatternSpec(
			connection='dwh',
			schema='public',
			table='fact_*'
		),
		can_edit=True
	)
	
	call_result = update_default_table_checks_pattern_target.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.default_table_check_patterns import update_default_table_checks_pattern_target
	from dqops.client.models import DefaultTableChecksPatternListModel, \
	                                TargetTablePatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DefaultTableChecksPatternListModel(
		pattern_name='default',
		priority=100,
		disabled=False,
		target_table=TargetTablePatternSpec(
			connection='dwh',
			schema='public',
			table='fact_*'
		),
		can_edit=True
	)
	
	call_result = await update_default_table_checks_pattern_target.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



