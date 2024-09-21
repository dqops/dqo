---
title: DQOps REST API rule_mining operations
---
# DQOps REST API rule_mining operations
Performs rule mining and proposes the configuration of data quality checks and their rule thresholds for tables.


___
## apply_proposed_monitoring_checks
Applies the proposed configuration of data quality monitoring checks on a table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rule_mining/apply_proposed_monitoring_checks.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/applyproposal
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Proposed configuration of data quality checks to be applied on the table and its columns.|*[CheckMiningProposalModel](../models/rule_mining.md#checkminingproposalmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/daily/applyproposal^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"missing_current_statistics\":false,\"missing_current_profiling_check_results\":false,\"column_checks\":{}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import apply_proposed_monitoring_checks
	from dqops.client.models import CheckContainerModel, \
	                                CheckMiningProposalModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                DefaultRuleSeverityLevel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckMiningProposalModel(
		missing_current_statistics=False,
		missing_current_profiling_check_results=False,
		column_checks={
		
		}
	)
	
	call_result = apply_proposed_monitoring_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import apply_proposed_monitoring_checks
	from dqops.client.models import CheckContainerModel, \
	                                CheckMiningProposalModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                DefaultRuleSeverityLevel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckMiningProposalModel(
		missing_current_statistics=False,
		missing_current_profiling_check_results=False,
		column_checks={
		
		}
	)
	
	call_result = await apply_proposed_monitoring_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import apply_proposed_monitoring_checks
	from dqops.client.models import CheckContainerModel, \
	                                CheckMiningProposalModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                DefaultRuleSeverityLevel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckMiningProposalModel(
		missing_current_statistics=False,
		missing_current_profiling_check_results=False,
		column_checks={
		
		}
	)
	
	call_result = apply_proposed_monitoring_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import apply_proposed_monitoring_checks
	from dqops.client.models import CheckContainerModel, \
	                                CheckMiningProposalModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                DefaultRuleSeverityLevel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckMiningProposalModel(
		missing_current_statistics=False,
		missing_current_profiling_check_results=False,
		column_checks={
		
		}
	)
	
	call_result = await apply_proposed_monitoring_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## apply_proposed_partitioned_checks
Applies the proposed configuration of data quality partitioned checks on a table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rule_mining/apply_proposed_partitioned_checks.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/applyproposal
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Proposed configuration of data quality checks to be applied on the table and its columns.|*[CheckMiningProposalModel](../models/rule_mining.md#checkminingproposalmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/daily/applyproposal^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"missing_current_statistics\":false,\"missing_current_profiling_check_results\":false,\"column_checks\":{}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import apply_proposed_partitioned_checks
	from dqops.client.models import CheckContainerModel, \
	                                CheckMiningProposalModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                DefaultRuleSeverityLevel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckMiningProposalModel(
		missing_current_statistics=False,
		missing_current_profiling_check_results=False,
		column_checks={
		
		}
	)
	
	call_result = apply_proposed_partitioned_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import apply_proposed_partitioned_checks
	from dqops.client.models import CheckContainerModel, \
	                                CheckMiningProposalModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                DefaultRuleSeverityLevel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckMiningProposalModel(
		missing_current_statistics=False,
		missing_current_profiling_check_results=False,
		column_checks={
		
		}
	)
	
	call_result = await apply_proposed_partitioned_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import apply_proposed_partitioned_checks
	from dqops.client.models import CheckContainerModel, \
	                                CheckMiningProposalModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                DefaultRuleSeverityLevel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckMiningProposalModel(
		missing_current_statistics=False,
		missing_current_profiling_check_results=False,
		column_checks={
		
		}
	)
	
	call_result = apply_proposed_partitioned_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import apply_proposed_partitioned_checks
	from dqops.client.models import CheckContainerModel, \
	                                CheckMiningProposalModel, \
	                                CheckModel, \
	                                CheckTimeScale, \
	                                DefaultRuleSeverityLevel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckMiningProposalModel(
		missing_current_statistics=False,
		missing_current_profiling_check_results=False,
		column_checks={
		
		}
	)
	
	call_result = await apply_proposed_partitioned_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## apply_proposed_profiling_checks
Applies the proposed configuration of data quality profiling checks on a table.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rule_mining/apply_proposed_profiling_checks.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/applyproposal
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Proposed configuration of data quality checks to be applied on the table and its columns.|*[CheckMiningProposalModel](../models/rule_mining.md#checkminingproposalmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/applyproposal^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"missing_current_statistics\":false,\"missing_current_profiling_check_results\":false,\"column_checks\":{}}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import apply_proposed_profiling_checks
	from dqops.client.models import CheckContainerModel, \
	                                CheckMiningProposalModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckMiningProposalModel(
		missing_current_statistics=False,
		missing_current_profiling_check_results=False,
		column_checks={
		
		}
	)
	
	call_result = apply_proposed_profiling_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import apply_proposed_profiling_checks
	from dqops.client.models import CheckContainerModel, \
	                                CheckMiningProposalModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckMiningProposalModel(
		missing_current_statistics=False,
		missing_current_profiling_check_results=False,
		column_checks={
		
		}
	)
	
	call_result = await apply_proposed_profiling_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import apply_proposed_profiling_checks
	from dqops.client.models import CheckContainerModel, \
	                                CheckMiningProposalModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckMiningProposalModel(
		missing_current_statistics=False,
		missing_current_profiling_check_results=False,
		column_checks={
		
		}
	)
	
	call_result = apply_proposed_profiling_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import apply_proposed_profiling_checks
	from dqops.client.models import CheckContainerModel, \
	                                CheckMiningProposalModel, \
	                                CheckModel, \
	                                DefaultRuleSeverityLevel, \
	                                FieldModel, \
	                                QualityCategoryModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckMiningProposalModel(
		missing_current_statistics=False,
		missing_current_profiling_check_results=False,
		column_checks={
		
		}
	)
	
	call_result = await apply_proposed_profiling_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## propose_table_monitoring_checks
Proposes the configuration of monitoring checks on a table by generating suggested configuration of checks and their rule thresholds.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rule_mining/propose_table_monitoring_checks.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/propose
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_mining_proposal_model`](../models/rule_mining.md#checkminingproposalmodel)</span>||*[CheckMiningProposalModel](../models/rule_mining.md#checkminingproposalmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Data quality check mining parameters which configure which rules are analyzed and proposed|*[CheckMiningParametersModel](../models/rule_mining.md#checkminingparametersmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/daily/propose^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"severity_level\":\"error\",\"copy_failed_profiling_checks\":false,\"copy_disabled_profiling_checks\":false,\"copy_profiling_checks\":true,\"reconfigure_policy_enabled_checks\":true,\"propose_checks_from_statistics\":true,\"propose_minimum_row_count\":true,\"propose_column_count\":true,\"propose_timeliness_checks\":true,\"propose_nulls_checks\":true,\"propose_not_nulls_checks\":true,\"propose_text_values_data_type\":true,\"propose_column_exists\":true,\"propose_uniqueness_checks\":true,\"propose_numeric_ranges\":true,\"propose_percentile_ranges\":true,\"propose_text_length_ranges\":true,\"propose_word_count_ranges\":true,\"propose_values_in_set_checks\":true,\"values_in_set_treat_rare_values_as_invalid\":true,\"propose_top_values_checks\":true,\"propose_text_conversion_checks\":true,\"propose_bool_percent_checks\":true,\"propose_date_checks\":true,\"propose_standard_pattern_checks\":true,\"detect_regular_expressions\":true,\"propose_whitespace_checks\":true,\"apply_pii_checks\":true,\"propose_custom_checks\":true}"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "missing_current_statistics" : false,
		  "missing_current_profiling_check_results" : false,
		  "column_checks" : { }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import propose_table_monitoring_checks
	from dqops.client.models import CheckMiningParametersModel, \
	                                CheckTimeScale, \
	                                TargetRuleSeverityLevel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckMiningParametersModel(
		severity_level=TargetRuleSeverityLevel.ERROR,
		copy_failed_profiling_checks=False,
		copy_disabled_profiling_checks=False,
		copy_profiling_checks=True,
		reconfigure_policy_enabled_checks=True,
		propose_checks_from_statistics=True,
		propose_minimum_row_count=True,
		propose_column_count=True,
		propose_timeliness_checks=True,
		propose_nulls_checks=True,
		propose_not_nulls_checks=True,
		propose_text_values_data_type=True,
		propose_column_exists=True,
		propose_uniqueness_checks=True,
		propose_numeric_ranges=True,
		propose_percentile_ranges=True,
		propose_text_length_ranges=True,
		propose_word_count_ranges=True,
		propose_values_in_set_checks=True,
		values_in_set_treat_rare_values_as_invalid=True,
		propose_top_values_checks=True,
		propose_text_conversion_checks=True,
		propose_bool_percent_checks=True,
		propose_date_checks=True,
		propose_standard_pattern_checks=True,
		detect_regular_expressions=True,
		propose_whitespace_checks=True,
		apply_pii_checks=True,
		propose_custom_checks=True
	)
	
	call_result = propose_table_monitoring_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckMiningProposalModel(
			missing_current_statistics=False,
			missing_current_profiling_check_results=False,
			column_checks={
			
			}
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import propose_table_monitoring_checks
	from dqops.client.models import CheckMiningParametersModel, \
	                                CheckTimeScale, \
	                                TargetRuleSeverityLevel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckMiningParametersModel(
		severity_level=TargetRuleSeverityLevel.ERROR,
		copy_failed_profiling_checks=False,
		copy_disabled_profiling_checks=False,
		copy_profiling_checks=True,
		reconfigure_policy_enabled_checks=True,
		propose_checks_from_statistics=True,
		propose_minimum_row_count=True,
		propose_column_count=True,
		propose_timeliness_checks=True,
		propose_nulls_checks=True,
		propose_not_nulls_checks=True,
		propose_text_values_data_type=True,
		propose_column_exists=True,
		propose_uniqueness_checks=True,
		propose_numeric_ranges=True,
		propose_percentile_ranges=True,
		propose_text_length_ranges=True,
		propose_word_count_ranges=True,
		propose_values_in_set_checks=True,
		values_in_set_treat_rare_values_as_invalid=True,
		propose_top_values_checks=True,
		propose_text_conversion_checks=True,
		propose_bool_percent_checks=True,
		propose_date_checks=True,
		propose_standard_pattern_checks=True,
		detect_regular_expressions=True,
		propose_whitespace_checks=True,
		apply_pii_checks=True,
		propose_custom_checks=True
	)
	
	call_result = await propose_table_monitoring_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckMiningProposalModel(
			missing_current_statistics=False,
			missing_current_profiling_check_results=False,
			column_checks={
			
			}
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import propose_table_monitoring_checks
	from dqops.client.models import CheckMiningParametersModel, \
	                                CheckTimeScale, \
	                                TargetRuleSeverityLevel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckMiningParametersModel(
		severity_level=TargetRuleSeverityLevel.ERROR,
		copy_failed_profiling_checks=False,
		copy_disabled_profiling_checks=False,
		copy_profiling_checks=True,
		reconfigure_policy_enabled_checks=True,
		propose_checks_from_statistics=True,
		propose_minimum_row_count=True,
		propose_column_count=True,
		propose_timeliness_checks=True,
		propose_nulls_checks=True,
		propose_not_nulls_checks=True,
		propose_text_values_data_type=True,
		propose_column_exists=True,
		propose_uniqueness_checks=True,
		propose_numeric_ranges=True,
		propose_percentile_ranges=True,
		propose_text_length_ranges=True,
		propose_word_count_ranges=True,
		propose_values_in_set_checks=True,
		values_in_set_treat_rare_values_as_invalid=True,
		propose_top_values_checks=True,
		propose_text_conversion_checks=True,
		propose_bool_percent_checks=True,
		propose_date_checks=True,
		propose_standard_pattern_checks=True,
		detect_regular_expressions=True,
		propose_whitespace_checks=True,
		apply_pii_checks=True,
		propose_custom_checks=True
	)
	
	call_result = propose_table_monitoring_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckMiningProposalModel(
			missing_current_statistics=False,
			missing_current_profiling_check_results=False,
			column_checks={
			
			}
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import propose_table_monitoring_checks
	from dqops.client.models import CheckMiningParametersModel, \
	                                CheckTimeScale, \
	                                TargetRuleSeverityLevel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckMiningParametersModel(
		severity_level=TargetRuleSeverityLevel.ERROR,
		copy_failed_profiling_checks=False,
		copy_disabled_profiling_checks=False,
		copy_profiling_checks=True,
		reconfigure_policy_enabled_checks=True,
		propose_checks_from_statistics=True,
		propose_minimum_row_count=True,
		propose_column_count=True,
		propose_timeliness_checks=True,
		propose_nulls_checks=True,
		propose_not_nulls_checks=True,
		propose_text_values_data_type=True,
		propose_column_exists=True,
		propose_uniqueness_checks=True,
		propose_numeric_ranges=True,
		propose_percentile_ranges=True,
		propose_text_length_ranges=True,
		propose_word_count_ranges=True,
		propose_values_in_set_checks=True,
		values_in_set_treat_rare_values_as_invalid=True,
		propose_top_values_checks=True,
		propose_text_conversion_checks=True,
		propose_bool_percent_checks=True,
		propose_date_checks=True,
		propose_standard_pattern_checks=True,
		detect_regular_expressions=True,
		propose_whitespace_checks=True,
		apply_pii_checks=True,
		propose_custom_checks=True
	)
	
	call_result = await propose_table_monitoring_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckMiningProposalModel(
			missing_current_statistics=False,
			missing_current_profiling_check_results=False,
			column_checks={
			
			}
		)
        ```
    
    
    



___
## propose_table_partitioned_checks
Proposes the configuration of partitioned checks on a table by generating suggested configuration of checks and their rule thresholds.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rule_mining/propose_table_partitioned_checks.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/propose
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_mining_proposal_model`](../models/rule_mining.md#checkminingproposalmodel)</span>||*[CheckMiningProposalModel](../models/rule_mining.md#checkminingproposalmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Data quality check mining parameters which configure which rules are analyzed and proposed|*[CheckMiningParametersModel](../models/rule_mining.md#checkminingparametersmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/daily/propose^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"severity_level\":\"error\",\"copy_failed_profiling_checks\":false,\"copy_disabled_profiling_checks\":false,\"copy_profiling_checks\":true,\"reconfigure_policy_enabled_checks\":true,\"propose_checks_from_statistics\":true,\"propose_minimum_row_count\":true,\"propose_column_count\":true,\"propose_timeliness_checks\":true,\"propose_nulls_checks\":true,\"propose_not_nulls_checks\":true,\"propose_text_values_data_type\":true,\"propose_column_exists\":true,\"propose_uniqueness_checks\":true,\"propose_numeric_ranges\":true,\"propose_percentile_ranges\":true,\"propose_text_length_ranges\":true,\"propose_word_count_ranges\":true,\"propose_values_in_set_checks\":true,\"values_in_set_treat_rare_values_as_invalid\":true,\"propose_top_values_checks\":true,\"propose_text_conversion_checks\":true,\"propose_bool_percent_checks\":true,\"propose_date_checks\":true,\"propose_standard_pattern_checks\":true,\"detect_regular_expressions\":true,\"propose_whitespace_checks\":true,\"apply_pii_checks\":true,\"propose_custom_checks\":true}"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "missing_current_statistics" : false,
		  "missing_current_profiling_check_results" : false,
		  "column_checks" : { }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import propose_table_partitioned_checks
	from dqops.client.models import CheckMiningParametersModel, \
	                                CheckTimeScale, \
	                                TargetRuleSeverityLevel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckMiningParametersModel(
		severity_level=TargetRuleSeverityLevel.ERROR,
		copy_failed_profiling_checks=False,
		copy_disabled_profiling_checks=False,
		copy_profiling_checks=True,
		reconfigure_policy_enabled_checks=True,
		propose_checks_from_statistics=True,
		propose_minimum_row_count=True,
		propose_column_count=True,
		propose_timeliness_checks=True,
		propose_nulls_checks=True,
		propose_not_nulls_checks=True,
		propose_text_values_data_type=True,
		propose_column_exists=True,
		propose_uniqueness_checks=True,
		propose_numeric_ranges=True,
		propose_percentile_ranges=True,
		propose_text_length_ranges=True,
		propose_word_count_ranges=True,
		propose_values_in_set_checks=True,
		values_in_set_treat_rare_values_as_invalid=True,
		propose_top_values_checks=True,
		propose_text_conversion_checks=True,
		propose_bool_percent_checks=True,
		propose_date_checks=True,
		propose_standard_pattern_checks=True,
		detect_regular_expressions=True,
		propose_whitespace_checks=True,
		apply_pii_checks=True,
		propose_custom_checks=True
	)
	
	call_result = propose_table_partitioned_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckMiningProposalModel(
			missing_current_statistics=False,
			missing_current_profiling_check_results=False,
			column_checks={
			
			}
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import propose_table_partitioned_checks
	from dqops.client.models import CheckMiningParametersModel, \
	                                CheckTimeScale, \
	                                TargetRuleSeverityLevel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckMiningParametersModel(
		severity_level=TargetRuleSeverityLevel.ERROR,
		copy_failed_profiling_checks=False,
		copy_disabled_profiling_checks=False,
		copy_profiling_checks=True,
		reconfigure_policy_enabled_checks=True,
		propose_checks_from_statistics=True,
		propose_minimum_row_count=True,
		propose_column_count=True,
		propose_timeliness_checks=True,
		propose_nulls_checks=True,
		propose_not_nulls_checks=True,
		propose_text_values_data_type=True,
		propose_column_exists=True,
		propose_uniqueness_checks=True,
		propose_numeric_ranges=True,
		propose_percentile_ranges=True,
		propose_text_length_ranges=True,
		propose_word_count_ranges=True,
		propose_values_in_set_checks=True,
		values_in_set_treat_rare_values_as_invalid=True,
		propose_top_values_checks=True,
		propose_text_conversion_checks=True,
		propose_bool_percent_checks=True,
		propose_date_checks=True,
		propose_standard_pattern_checks=True,
		detect_regular_expressions=True,
		propose_whitespace_checks=True,
		apply_pii_checks=True,
		propose_custom_checks=True
	)
	
	call_result = await propose_table_partitioned_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckMiningProposalModel(
			missing_current_statistics=False,
			missing_current_profiling_check_results=False,
			column_checks={
			
			}
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import propose_table_partitioned_checks
	from dqops.client.models import CheckMiningParametersModel, \
	                                CheckTimeScale, \
	                                TargetRuleSeverityLevel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckMiningParametersModel(
		severity_level=TargetRuleSeverityLevel.ERROR,
		copy_failed_profiling_checks=False,
		copy_disabled_profiling_checks=False,
		copy_profiling_checks=True,
		reconfigure_policy_enabled_checks=True,
		propose_checks_from_statistics=True,
		propose_minimum_row_count=True,
		propose_column_count=True,
		propose_timeliness_checks=True,
		propose_nulls_checks=True,
		propose_not_nulls_checks=True,
		propose_text_values_data_type=True,
		propose_column_exists=True,
		propose_uniqueness_checks=True,
		propose_numeric_ranges=True,
		propose_percentile_ranges=True,
		propose_text_length_ranges=True,
		propose_word_count_ranges=True,
		propose_values_in_set_checks=True,
		values_in_set_treat_rare_values_as_invalid=True,
		propose_top_values_checks=True,
		propose_text_conversion_checks=True,
		propose_bool_percent_checks=True,
		propose_date_checks=True,
		propose_standard_pattern_checks=True,
		detect_regular_expressions=True,
		propose_whitespace_checks=True,
		apply_pii_checks=True,
		propose_custom_checks=True
	)
	
	call_result = propose_table_partitioned_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckMiningProposalModel(
			missing_current_statistics=False,
			missing_current_profiling_check_results=False,
			column_checks={
			
			}
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import propose_table_partitioned_checks
	from dqops.client.models import CheckMiningParametersModel, \
	                                CheckTimeScale, \
	                                TargetRuleSeverityLevel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckMiningParametersModel(
		severity_level=TargetRuleSeverityLevel.ERROR,
		copy_failed_profiling_checks=False,
		copy_disabled_profiling_checks=False,
		copy_profiling_checks=True,
		reconfigure_policy_enabled_checks=True,
		propose_checks_from_statistics=True,
		propose_minimum_row_count=True,
		propose_column_count=True,
		propose_timeliness_checks=True,
		propose_nulls_checks=True,
		propose_not_nulls_checks=True,
		propose_text_values_data_type=True,
		propose_column_exists=True,
		propose_uniqueness_checks=True,
		propose_numeric_ranges=True,
		propose_percentile_ranges=True,
		propose_text_length_ranges=True,
		propose_word_count_ranges=True,
		propose_values_in_set_checks=True,
		values_in_set_treat_rare_values_as_invalid=True,
		propose_top_values_checks=True,
		propose_text_conversion_checks=True,
		propose_bool_percent_checks=True,
		propose_date_checks=True,
		propose_standard_pattern_checks=True,
		detect_regular_expressions=True,
		propose_whitespace_checks=True,
		apply_pii_checks=True,
		propose_custom_checks=True
	)
	
	call_result = await propose_table_partitioned_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckMiningProposalModel(
			missing_current_statistics=False,
			missing_current_profiling_check_results=False,
			column_checks={
			
			}
		)
        ```
    
    
    



___
## propose_table_profiling_checks
Proposes the configuration of profiling checks on a table by generating suggested configuration of checks and their rule thresholds.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rule_mining/propose_table_profiling_checks.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/propose
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_mining_proposal_model`](../models/rule_mining.md#checkminingproposalmodel)</span>||*[CheckMiningProposalModel](../models/rule_mining.md#checkminingproposalmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Data quality check mining parameters which configure which rules are analyzed and proposed|*[CheckMiningParametersModel](../models/rule_mining.md#checkminingparametersmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/propose^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"severity_level\":\"error\",\"copy_failed_profiling_checks\":false,\"copy_disabled_profiling_checks\":false,\"copy_profiling_checks\":true,\"reconfigure_policy_enabled_checks\":true,\"propose_checks_from_statistics\":true,\"propose_minimum_row_count\":true,\"propose_column_count\":true,\"propose_timeliness_checks\":true,\"propose_nulls_checks\":true,\"propose_not_nulls_checks\":true,\"propose_text_values_data_type\":true,\"propose_column_exists\":true,\"propose_uniqueness_checks\":true,\"propose_numeric_ranges\":true,\"propose_percentile_ranges\":true,\"propose_text_length_ranges\":true,\"propose_word_count_ranges\":true,\"propose_values_in_set_checks\":true,\"values_in_set_treat_rare_values_as_invalid\":true,\"propose_top_values_checks\":true,\"propose_text_conversion_checks\":true,\"propose_bool_percent_checks\":true,\"propose_date_checks\":true,\"propose_standard_pattern_checks\":true,\"detect_regular_expressions\":true,\"propose_whitespace_checks\":true,\"apply_pii_checks\":true,\"propose_custom_checks\":true}"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "missing_current_statistics" : false,
		  "missing_current_profiling_check_results" : false,
		  "column_checks" : { }
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import propose_table_profiling_checks
	from dqops.client.models import CheckMiningParametersModel, \
	                                TargetRuleSeverityLevel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckMiningParametersModel(
		severity_level=TargetRuleSeverityLevel.ERROR,
		copy_failed_profiling_checks=False,
		copy_disabled_profiling_checks=False,
		copy_profiling_checks=True,
		reconfigure_policy_enabled_checks=True,
		propose_checks_from_statistics=True,
		propose_minimum_row_count=True,
		propose_column_count=True,
		propose_timeliness_checks=True,
		propose_nulls_checks=True,
		propose_not_nulls_checks=True,
		propose_text_values_data_type=True,
		propose_column_exists=True,
		propose_uniqueness_checks=True,
		propose_numeric_ranges=True,
		propose_percentile_ranges=True,
		propose_text_length_ranges=True,
		propose_word_count_ranges=True,
		propose_values_in_set_checks=True,
		values_in_set_treat_rare_values_as_invalid=True,
		propose_top_values_checks=True,
		propose_text_conversion_checks=True,
		propose_bool_percent_checks=True,
		propose_date_checks=True,
		propose_standard_pattern_checks=True,
		detect_regular_expressions=True,
		propose_whitespace_checks=True,
		apply_pii_checks=True,
		propose_custom_checks=True
	)
	
	call_result = propose_table_profiling_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckMiningProposalModel(
			missing_current_statistics=False,
			missing_current_profiling_check_results=False,
			column_checks={
			
			}
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import propose_table_profiling_checks
	from dqops.client.models import CheckMiningParametersModel, \
	                                TargetRuleSeverityLevel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckMiningParametersModel(
		severity_level=TargetRuleSeverityLevel.ERROR,
		copy_failed_profiling_checks=False,
		copy_disabled_profiling_checks=False,
		copy_profiling_checks=True,
		reconfigure_policy_enabled_checks=True,
		propose_checks_from_statistics=True,
		propose_minimum_row_count=True,
		propose_column_count=True,
		propose_timeliness_checks=True,
		propose_nulls_checks=True,
		propose_not_nulls_checks=True,
		propose_text_values_data_type=True,
		propose_column_exists=True,
		propose_uniqueness_checks=True,
		propose_numeric_ranges=True,
		propose_percentile_ranges=True,
		propose_text_length_ranges=True,
		propose_word_count_ranges=True,
		propose_values_in_set_checks=True,
		values_in_set_treat_rare_values_as_invalid=True,
		propose_top_values_checks=True,
		propose_text_conversion_checks=True,
		propose_bool_percent_checks=True,
		propose_date_checks=True,
		propose_standard_pattern_checks=True,
		detect_regular_expressions=True,
		propose_whitespace_checks=True,
		apply_pii_checks=True,
		propose_custom_checks=True
	)
	
	call_result = await propose_table_profiling_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckMiningProposalModel(
			missing_current_statistics=False,
			missing_current_profiling_check_results=False,
			column_checks={
			
			}
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import propose_table_profiling_checks
	from dqops.client.models import CheckMiningParametersModel, \
	                                TargetRuleSeverityLevel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckMiningParametersModel(
		severity_level=TargetRuleSeverityLevel.ERROR,
		copy_failed_profiling_checks=False,
		copy_disabled_profiling_checks=False,
		copy_profiling_checks=True,
		reconfigure_policy_enabled_checks=True,
		propose_checks_from_statistics=True,
		propose_minimum_row_count=True,
		propose_column_count=True,
		propose_timeliness_checks=True,
		propose_nulls_checks=True,
		propose_not_nulls_checks=True,
		propose_text_values_data_type=True,
		propose_column_exists=True,
		propose_uniqueness_checks=True,
		propose_numeric_ranges=True,
		propose_percentile_ranges=True,
		propose_text_length_ranges=True,
		propose_word_count_ranges=True,
		propose_values_in_set_checks=True,
		values_in_set_treat_rare_values_as_invalid=True,
		propose_top_values_checks=True,
		propose_text_conversion_checks=True,
		propose_bool_percent_checks=True,
		propose_date_checks=True,
		propose_standard_pattern_checks=True,
		detect_regular_expressions=True,
		propose_whitespace_checks=True,
		apply_pii_checks=True,
		propose_custom_checks=True
	)
	
	call_result = propose_table_profiling_checks.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckMiningProposalModel(
			missing_current_statistics=False,
			missing_current_profiling_check_results=False,
			column_checks={
			
			}
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.rule_mining import propose_table_profiling_checks
	from dqops.client.models import CheckMiningParametersModel, \
	                                TargetRuleSeverityLevel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckMiningParametersModel(
		severity_level=TargetRuleSeverityLevel.ERROR,
		copy_failed_profiling_checks=False,
		copy_disabled_profiling_checks=False,
		copy_profiling_checks=True,
		reconfigure_policy_enabled_checks=True,
		propose_checks_from_statistics=True,
		propose_minimum_row_count=True,
		propose_column_count=True,
		propose_timeliness_checks=True,
		propose_nulls_checks=True,
		propose_not_nulls_checks=True,
		propose_text_values_data_type=True,
		propose_column_exists=True,
		propose_uniqueness_checks=True,
		propose_numeric_ranges=True,
		propose_percentile_ranges=True,
		propose_text_length_ranges=True,
		propose_word_count_ranges=True,
		propose_values_in_set_checks=True,
		values_in_set_treat_rare_values_as_invalid=True,
		propose_top_values_checks=True,
		propose_text_conversion_checks=True,
		propose_bool_percent_checks=True,
		propose_date_checks=True,
		propose_standard_pattern_checks=True,
		detect_regular_expressions=True,
		propose_whitespace_checks=True,
		apply_pii_checks=True,
		propose_custom_checks=True
	)
	
	call_result = await propose_table_profiling_checks.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckMiningProposalModel(
			missing_current_statistics=False,
			missing_current_profiling_check_results=False,
			column_checks={
			
			}
		)
        ```
    
    
    



