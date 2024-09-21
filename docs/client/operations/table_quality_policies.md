---
title: DQOps REST API table_quality_policies operations
---
# DQOps REST API table_quality_policies operations
Operations for managing the configuration of data quality policies at a table level. Policies are the default configuration of data quality checks for tables matching a pattern.


___
## copy_from_table_quality_policy
Creates (adds) a copy of an existing default table-level checks pattern configuration (data quality policy) under a new name.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/copy_from_table_quality_policy.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/policies/checks/table/{targetPatternName}/copyfrom/{sourcePatternName}
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
    curl -X POST http://localhost:8888/api/policies/checks/table/default/copyfrom/default^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import copy_from_table_quality_policy
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = copy_from_table_quality_policy.sync(
	    'default',
	    'default',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import copy_from_table_quality_policy
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await copy_from_table_quality_policy.asyncio(
	    'default',
	    'default',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import copy_from_table_quality_policy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = copy_from_table_quality_policy.sync(
	    'default',
	    'default',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import copy_from_table_quality_policy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await copy_from_table_quality_policy.asyncio(
	    'default',
	    'default',
	    client=dqops_client
	)
	
    ```

    



___
## create_table_quality_policy_pattern
Creates (adds) a new default table-level checks pattern (data quality policy) configuration by saving a full specification object.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/create_table_quality_policy_pattern.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/policies/checks/table/{patternName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Default checks pattern model|*[TableQualityPolicyModel](../models/table_quality_policies.md#tablequalitypolicymodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/policies/checks/table/default^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"policy_name\":\"default\",\"policy_spec\":{\"priority\":1000,\"monitoring_checks\":{\"daily\":{\"volume\":{\"daily_row_count\":{\"warning\":{\"min_count\":1}}}}}},\"can_edit\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import create_table_quality_policy_pattern
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableQualityPolicyModel, \
	                                TableQualityPolicySpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TargetTablePatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableQualityPolicyModel(
		policy_name='default',
		policy_spec=TableQualityPolicySpec(
			priority=1000,
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
	
	call_result = create_table_quality_policy_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import create_table_quality_policy_pattern
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableQualityPolicyModel, \
	                                TableQualityPolicySpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TargetTablePatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableQualityPolicyModel(
		policy_name='default',
		policy_spec=TableQualityPolicySpec(
			priority=1000,
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
	
	call_result = await create_table_quality_policy_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import create_table_quality_policy_pattern
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableQualityPolicyModel, \
	                                TableQualityPolicySpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TargetTablePatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableQualityPolicyModel(
		policy_name='default',
		policy_spec=TableQualityPolicySpec(
			priority=1000,
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
	
	call_result = create_table_quality_policy_pattern.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import create_table_quality_policy_pattern
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableQualityPolicyModel, \
	                                TableQualityPolicySpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TargetTablePatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableQualityPolicyModel(
		policy_name='default',
		policy_spec=TableQualityPolicySpec(
			priority=1000,
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
	
	call_result = await create_table_quality_policy_pattern.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## create_table_quality_policy_target
Creates (adds) a new default table-level checks pattern configuration (a table-level data quality policy).

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/create_table_quality_policy_target.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/policies/checks/table/{patternName}/target
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Default checks pattern model with only the target filters|*[TableQualityPolicyListModel](../models/table_quality_policies.md#tablequalitypolicylistmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/policies/checks/table/default/target^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"policy_name\":\"default\",\"priority\":100,\"disabled\":false,\"target_table\":{\"connection\":\"dwh\",\"schema\":\"public\",\"table\":\"fact_*\"},\"can_edit\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import create_table_quality_policy_target
	from dqops.client.models import TableQualityPolicyListModel, \
	                                TargetTablePatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableQualityPolicyListModel(
		policy_name='default',
		priority=100,
		disabled=False,
		target_table=TargetTablePatternSpec(
			connection='dwh',
			schema='public',
			table='fact_*'
		),
		can_edit=True
	)
	
	call_result = create_table_quality_policy_target.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import create_table_quality_policy_target
	from dqops.client.models import TableQualityPolicyListModel, \
	                                TargetTablePatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableQualityPolicyListModel(
		policy_name='default',
		priority=100,
		disabled=False,
		target_table=TargetTablePatternSpec(
			connection='dwh',
			schema='public',
			table='fact_*'
		),
		can_edit=True
	)
	
	call_result = await create_table_quality_policy_target.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import create_table_quality_policy_target
	from dqops.client.models import TableQualityPolicyListModel, \
	                                TargetTablePatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableQualityPolicyListModel(
		policy_name='default',
		priority=100,
		disabled=False,
		target_table=TargetTablePatternSpec(
			connection='dwh',
			schema='public',
			table='fact_*'
		),
		can_edit=True
	)
	
	call_result = create_table_quality_policy_target.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import create_table_quality_policy_target
	from dqops.client.models import TableQualityPolicyListModel, \
	                                TargetTablePatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableQualityPolicyListModel(
		policy_name='default',
		priority=100,
		disabled=False,
		target_table=TargetTablePatternSpec(
			connection='dwh',
			schema='public',
			table='fact_*'
		),
		can_edit=True
	)
	
	call_result = await create_table_quality_policy_target.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## delete_table_quality_policy
Deletes a default table-level checks pattern (a data quality policy at a column level).

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/delete_table_quality_policy.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/policies/checks/table/{patternName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X DELETE http://localhost:8888/api/policies/checks/table/default^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import delete_table_quality_policy
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = delete_table_quality_policy.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import delete_table_quality_policy
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await delete_table_quality_policy.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import delete_table_quality_policy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = delete_table_quality_policy.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import delete_table_quality_policy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await delete_table_quality_policy.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    



___
## get_monitoring_daily_table_quality_policy
Returns UI model to show and edit the default configuration of the daily monitoring checks that are configured for a check pattern on a table level.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/get_monitoring_daily_table_quality_policy.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/policies/checks/table/{patternName}/monitoring/daily
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
    curl http://localhost:8888/api/policies/checks/table/default/monitoring/daily^
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
	from dqops.client.api.table_quality_policies import get_monitoring_daily_table_quality_policy
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_monitoring_daily_table_quality_policy.sync(
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
	from dqops.client.api.table_quality_policies import get_monitoring_daily_table_quality_policy
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_monitoring_daily_table_quality_policy.asyncio(
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
	from dqops.client.api.table_quality_policies import get_monitoring_daily_table_quality_policy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_monitoring_daily_table_quality_policy.sync(
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
	from dqops.client.api.table_quality_policies import get_monitoring_daily_table_quality_policy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_monitoring_daily_table_quality_policy.asyncio(
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
## get_monitoring_monthly_table_quality_policy
Returns UI model to show and edit the default configuration of the monthly monitoring checks that are configured for a check pattern on a table level.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/get_monitoring_monthly_table_quality_policy.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/policies/checks/table/{patternName}/monitoring/monthly
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
    curl http://localhost:8888/api/policies/checks/table/default/monitoring/monthly^
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
	from dqops.client.api.table_quality_policies import get_monitoring_monthly_table_quality_policy
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_monitoring_monthly_table_quality_policy.sync(
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
	from dqops.client.api.table_quality_policies import get_monitoring_monthly_table_quality_policy
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_monitoring_monthly_table_quality_policy.asyncio(
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
	from dqops.client.api.table_quality_policies import get_monitoring_monthly_table_quality_policy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_monitoring_monthly_table_quality_policy.sync(
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
	from dqops.client.api.table_quality_policies import get_monitoring_monthly_table_quality_policy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_monitoring_monthly_table_quality_policy.asyncio(
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
## get_partitioned_daily_table_quality_policy
Returns UI model to show and edit the default configuration of the daily partitioned checks that are configured for a check pattern on a table level.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/get_partitioned_daily_table_quality_policy.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/policies/checks/table/{patternName}/partitioned/daily
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
    curl http://localhost:8888/api/policies/checks/table/default/partitioned/daily^
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
	from dqops.client.api.table_quality_policies import get_partitioned_daily_table_quality_policy
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_partitioned_daily_table_quality_policy.sync(
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
	from dqops.client.api.table_quality_policies import get_partitioned_daily_table_quality_policy
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_partitioned_daily_table_quality_policy.asyncio(
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
	from dqops.client.api.table_quality_policies import get_partitioned_daily_table_quality_policy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_partitioned_daily_table_quality_policy.sync(
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
	from dqops.client.api.table_quality_policies import get_partitioned_daily_table_quality_policy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_partitioned_daily_table_quality_policy.asyncio(
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
## get_partitioned_monthly_table_quality_policy
Returns UI model to show and edit the default configuration of the monthly partitioned checks that are configured for a check pattern on a table level.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/get_partitioned_monthly_table_quality_policy.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/policies/checks/table/{patternName}/partitioned/monthly
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
    curl http://localhost:8888/api/policies/checks/table/default/partitioned/monthly^
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
	from dqops.client.api.table_quality_policies import get_partitioned_monthly_table_quality_policy
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_partitioned_monthly_table_quality_policy.sync(
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
	from dqops.client.api.table_quality_policies import get_partitioned_monthly_table_quality_policy
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_partitioned_monthly_table_quality_policy.asyncio(
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
	from dqops.client.api.table_quality_policies import get_partitioned_monthly_table_quality_policy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_partitioned_monthly_table_quality_policy.sync(
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
	from dqops.client.api.table_quality_policies import get_partitioned_monthly_table_quality_policy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_partitioned_monthly_table_quality_policy.asyncio(
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
## get_profiling_table_quality_policy
Returns UI model to show and edit the default configuration of the profiling checks that are configured for a check pattern on a table level.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/get_profiling_table_quality_policy.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/policies/checks/table/{patternName}/profiling
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
    curl http://localhost:8888/api/policies/checks/table/default/profiling^
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
	from dqops.client.api.table_quality_policies import get_profiling_table_quality_policy
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_profiling_table_quality_policy.sync(
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
	from dqops.client.api.table_quality_policies import get_profiling_table_quality_policy
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_profiling_table_quality_policy.asyncio(
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
	from dqops.client.api.table_quality_policies import get_profiling_table_quality_policy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_profiling_table_quality_policy.sync(
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
	from dqops.client.api.table_quality_policies import get_profiling_table_quality_policy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_profiling_table_quality_policy.asyncio(
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
## get_table_quality_policies
Returns a flat list of all table-level default check patterns (data quality policies) configured for this instance. Default checks are applied on tables dynamically.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/get_table_quality_policies.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/policies/checks/table
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`table_quality_policy_list_model`</span>||*List[[TableQualityPolicyListModel](../models/table_quality_policies.md#tablequalitypolicylistmodel)]*|








**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/policies/checks/table^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "policy_name" : "default",
		  "priority" : 100,
		  "disabled" : false,
		  "target_table" : {
		    "connection" : "dwh",
		    "schema" : "public",
		    "table" : "fact_*"
		  },
		  "can_edit" : true
		}, {
		  "policy_name" : "default",
		  "priority" : 100,
		  "disabled" : false,
		  "target_table" : {
		    "connection" : "dwh",
		    "schema" : "public",
		    "table" : "fact_*"
		  },
		  "can_edit" : true
		}, {
		  "policy_name" : "default",
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
	from dqops.client.api.table_quality_policies import get_table_quality_policies
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_quality_policies.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			TableQualityPolicyListModel(
				policy_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			),
			TableQualityPolicyListModel(
				policy_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			),
			TableQualityPolicyListModel(
				policy_name='default',
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
	from dqops.client.api.table_quality_policies import get_table_quality_policies
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_quality_policies.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			TableQualityPolicyListModel(
				policy_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			),
			TableQualityPolicyListModel(
				policy_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			),
			TableQualityPolicyListModel(
				policy_name='default',
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
	from dqops.client.api.table_quality_policies import get_table_quality_policies
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_quality_policies.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			TableQualityPolicyListModel(
				policy_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			),
			TableQualityPolicyListModel(
				policy_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			),
			TableQualityPolicyListModel(
				policy_name='default',
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
	from dqops.client.api.table_quality_policies import get_table_quality_policies
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_quality_policies.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			TableQualityPolicyListModel(
				policy_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			),
			TableQualityPolicyListModel(
				policy_name='default',
				priority=100,
				disabled=False,
				target_table=TargetTablePatternSpec(
					connection='dwh',
					schema='public',
					table='fact_*'
				),
				can_edit=True
			),
			TableQualityPolicyListModel(
				policy_name='default',
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
## get_table_quality_policy
Returns a default table-level checks pattern (data quality policy) definition as a full specification object

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/get_table_quality_policy.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/policies/checks/table/{patternName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_quality_policy_model`](../models/table_quality_policies.md#tablequalitypolicymodel)</span>||*[TableQualityPolicyModel](../models/table_quality_policies.md#tablequalitypolicymodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Table pattern name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/policies/checks/table/default^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "policy_name" : "default",
		  "policy_spec" : {
		    "priority" : 1000,
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
	from dqops.client.api.table_quality_policies import get_table_quality_policy
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_quality_policy.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableQualityPolicyModel(
			policy_name='default',
			policy_spec=TableQualityPolicySpec(
				priority=1000,
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
	from dqops.client.api.table_quality_policies import get_table_quality_policy
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_quality_policy.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableQualityPolicyModel(
			policy_name='default',
			policy_spec=TableQualityPolicySpec(
				priority=1000,
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
	from dqops.client.api.table_quality_policies import get_table_quality_policy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_quality_policy.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableQualityPolicyModel(
			policy_name='default',
			policy_spec=TableQualityPolicySpec(
				priority=1000,
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
	from dqops.client.api.table_quality_policies import get_table_quality_policy
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_quality_policy.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableQualityPolicyModel(
			policy_name='default',
			policy_spec=TableQualityPolicySpec(
				priority=1000,
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
## get_table_quality_policy_target
Returns a default checks pattern definition (a data quality policy)

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/get_table_quality_policy_target.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/policies/checks/table/{patternName}/target
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`table_quality_policy_list_model`](../models/table_quality_policies.md#tablequalitypolicylistmodel)</span>||*[TableQualityPolicyListModel](../models/table_quality_policies.md#tablequalitypolicylistmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Table pattern name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/policies/checks/table/default/target^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "policy_name" : "default",
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
	from dqops.client.api.table_quality_policies import get_table_quality_policy_target
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_quality_policy_target.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableQualityPolicyListModel(
			policy_name='default',
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
	from dqops.client.api.table_quality_policies import get_table_quality_policy_target
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_quality_policy_target.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableQualityPolicyListModel(
			policy_name='default',
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
	from dqops.client.api.table_quality_policies import get_table_quality_policy_target
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_quality_policy_target.sync(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableQualityPolicyListModel(
			policy_name='default',
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
	from dqops.client.api.table_quality_policies import get_table_quality_policy_target
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_quality_policy_target.asyncio(
	    'default',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        TableQualityPolicyListModel(
			policy_name='default',
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
## update_monitoring_daily_table_quality_policy
New configuration of the default daily monitoring checks on a table level. These checks will be applied to tables.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/update_monitoring_daily_table_quality_policy.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/policies/checks/table/{patternName}/monitoring/daily
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
    curl -X PUT http://localhost:8888/api/policies/checks/table/default/monitoring/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/table/volume/row_count\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_error_sampling\":false,\"supports_grouping\":false,\"default_severity\":\"error\",\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_monitoring_daily_table_quality_policy
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
	
	call_result = update_monitoring_daily_table_quality_policy.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_monitoring_daily_table_quality_policy
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
	
	call_result = await update_monitoring_daily_table_quality_policy.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_monitoring_daily_table_quality_policy
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
	
	call_result = update_monitoring_daily_table_quality_policy.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_monitoring_daily_table_quality_policy
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
	
	call_result = await update_monitoring_daily_table_quality_policy.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_monitoring_monthly_table_quality_policy
New configuration of the default monthly monitoring checks on a table level. These checks will be applied to tables.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/update_monitoring_monthly_table_quality_policy.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/policies/checks/table/{patternName}/monitoring/monthly
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
    curl -X PUT http://localhost:8888/api/policies/checks/table/default/monitoring/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/table/volume/row_count\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_error_sampling\":false,\"supports_grouping\":false,\"default_severity\":\"error\",\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_monitoring_monthly_table_quality_policy
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
	
	call_result = update_monitoring_monthly_table_quality_policy.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_monitoring_monthly_table_quality_policy
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
	
	call_result = await update_monitoring_monthly_table_quality_policy.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_monitoring_monthly_table_quality_policy
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
	
	call_result = update_monitoring_monthly_table_quality_policy.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_monitoring_monthly_table_quality_policy
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
	
	call_result = await update_monitoring_monthly_table_quality_policy.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_partitioned_daily_table_quality_policy
New configuration of the default daily partitioned checks on a table level. These checks will be applied to tables.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/update_partitioned_daily_table_quality_policy.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/policies/checks/table/{patternName}/partitioned/daily
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
    curl -X PUT http://localhost:8888/api/policies/checks/table/default/partitioned/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/table/volume/row_count\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_error_sampling\":false,\"supports_grouping\":false,\"default_severity\":\"error\",\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_partitioned_daily_table_quality_policy
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
	
	call_result = update_partitioned_daily_table_quality_policy.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_partitioned_daily_table_quality_policy
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
	
	call_result = await update_partitioned_daily_table_quality_policy.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_partitioned_daily_table_quality_policy
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
	
	call_result = update_partitioned_daily_table_quality_policy.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_partitioned_daily_table_quality_policy
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
	
	call_result = await update_partitioned_daily_table_quality_policy.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_partitioned_monthly_table_quality_policy
New configuration of the default monthly partitioned checks on a table level. These checks will be applied to tables.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/update_partitioned_monthly_table_quality_policy.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/policies/checks/table/{patternName}/partitioned/monthly
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
    curl -X PUT http://localhost:8888/api/policies/checks/table/default/partitioned/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/table/volume/row_count\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_error_sampling\":false,\"supports_grouping\":false,\"default_severity\":\"error\",\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_partitioned_monthly_table_quality_policy
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
	
	call_result = update_partitioned_monthly_table_quality_policy.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_partitioned_monthly_table_quality_policy
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
	
	call_result = await update_partitioned_monthly_table_quality_policy.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_partitioned_monthly_table_quality_policy
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
	
	call_result = update_partitioned_monthly_table_quality_policy.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_partitioned_monthly_table_quality_policy
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
	
	call_result = await update_partitioned_monthly_table_quality_policy.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_profiling_table_quality_policy
New configuration of the default profiling checks on a table level. These checks will be applied to tables.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/update_profiling_table_quality_policy.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/policies/checks/table/{patternName}/profiling
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
    curl -X PUT http://localhost:8888/api/policies/checks/table/default/profiling^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/table/volume/row_count\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_error_sampling\":false,\"supports_grouping\":false,\"default_severity\":\"error\",\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_profiling_table_quality_policy
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
	
	call_result = update_profiling_table_quality_policy.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_profiling_table_quality_policy
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
	
	call_result = await update_profiling_table_quality_policy.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_profiling_table_quality_policy
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
	
	call_result = update_profiling_table_quality_policy.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_profiling_table_quality_policy
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
	
	call_result = await update_profiling_table_quality_policy.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_quality_policy
Updates an default table-level checks pattern (data quality policy) by saving a full specification object

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/update_table_quality_policy.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/policies/checks/table/{patternName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Default checks pattern model|*[TableQualityPolicyModel](../models/table_quality_policies.md#tablequalitypolicymodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/policies/checks/table/default^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"policy_name\":\"default\",\"policy_spec\":{\"priority\":1000,\"monitoring_checks\":{\"daily\":{\"volume\":{\"daily_row_count\":{\"warning\":{\"min_count\":1}}}}}},\"can_edit\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_table_quality_policy
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableQualityPolicyModel, \
	                                TableQualityPolicySpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TargetTablePatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableQualityPolicyModel(
		policy_name='default',
		policy_spec=TableQualityPolicySpec(
			priority=1000,
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
	
	call_result = update_table_quality_policy.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_table_quality_policy
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableQualityPolicyModel, \
	                                TableQualityPolicySpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TargetTablePatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableQualityPolicyModel(
		policy_name='default',
		policy_spec=TableQualityPolicySpec(
			priority=1000,
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
	
	call_result = await update_table_quality_policy.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_table_quality_policy
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableQualityPolicyModel, \
	                                TableQualityPolicySpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TargetTablePatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableQualityPolicyModel(
		policy_name='default',
		policy_spec=TableQualityPolicySpec(
			priority=1000,
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
	
	call_result = update_table_quality_policy.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_table_quality_policy
	from dqops.client.models import MinCountRule1ParametersSpec, \
	                                TableMonitoringCheckCategoriesSpec, \
	                                TablePartitionedCheckCategoriesSpec, \
	                                TableProfilingCheckCategoriesSpec, \
	                                TableQualityPolicyModel, \
	                                TableQualityPolicySpec, \
	                                TableRowCountCheckSpec, \
	                                TableVolumeProfilingChecksSpec, \
	                                TableVolumeRowCountSensorParametersSpec, \
	                                TargetTablePatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableQualityPolicyModel(
		policy_name='default',
		policy_spec=TableQualityPolicySpec(
			priority=1000,
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
	
	call_result = await update_table_quality_policy.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## update_table_quality_policy_target
Updates an default table-level checks pattern (data quality policy), changing only the target object

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_quality_policies/update_table_quality_policy_target.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/policies/checks/table/{patternName}/target
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`pattern_name`</span>|Pattern name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Default checks pattern model|*[TableQualityPolicyListModel](../models/table_quality_policies.md#tablequalitypolicylistmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/policies/checks/table/default/target^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"policy_name\":\"default\",\"priority\":100,\"disabled\":false,\"target_table\":{\"connection\":\"dwh\",\"schema\":\"public\",\"table\":\"fact_*\"},\"can_edit\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_table_quality_policy_target
	from dqops.client.models import TableQualityPolicyListModel, \
	                                TargetTablePatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableQualityPolicyListModel(
		policy_name='default',
		priority=100,
		disabled=False,
		target_table=TargetTablePatternSpec(
			connection='dwh',
			schema='public',
			table='fact_*'
		),
		can_edit=True
	)
	
	call_result = update_table_quality_policy_target.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_table_quality_policy_target
	from dqops.client.models import TableQualityPolicyListModel, \
	                                TargetTablePatternSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = TableQualityPolicyListModel(
		policy_name='default',
		priority=100,
		disabled=False,
		target_table=TargetTablePatternSpec(
			connection='dwh',
			schema='public',
			table='fact_*'
		),
		can_edit=True
	)
	
	call_result = await update_table_quality_policy_target.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_table_quality_policy_target
	from dqops.client.models import TableQualityPolicyListModel, \
	                                TargetTablePatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableQualityPolicyListModel(
		policy_name='default',
		priority=100,
		disabled=False,
		target_table=TargetTablePatternSpec(
			connection='dwh',
			schema='public',
			table='fact_*'
		),
		can_edit=True
	)
	
	call_result = update_table_quality_policy_target.sync(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.table_quality_policies import update_table_quality_policy_target
	from dqops.client.models import TableQualityPolicyListModel, \
	                                TargetTablePatternSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = TableQualityPolicyListModel(
		policy_name='default',
		priority=100,
		disabled=False,
		target_table=TargetTablePatternSpec(
			connection='dwh',
			schema='public',
			table='fact_*'
		),
		can_edit=True
	)
	
	call_result = await update_table_quality_policy_target.asyncio(
	    'default',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



