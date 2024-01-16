# DQOps REST API defaults operations
Default settings management for configuring the default data quality checks that are configured for all imported tables and columns.


___
## get_default_data_observability_daily_monitoring_column_checks
Returns UI model to show and edit the default configuration of the daily monitoring (Data Observability and monitoring) checks that are configured for all imported columns on a column level.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_data_observability_daily_monitoring_column_checks.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/daily/column
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_model`](../models/common.md#checkcontainermodel)</span>||*[CheckContainerModel](../models/common.md#checkcontainermodel)*|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/daily/column^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_data_observability_daily_monitoring_column_checks
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_data_observability_daily_monitoring_column_checks.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_data_observability_daily_monitoring_column_checks
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_data_observability_daily_monitoring_column_checks.asyncio(
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_data_observability_daily_monitoring_column_checks
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_data_observability_daily_monitoring_column_checks.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_data_observability_daily_monitoring_column_checks
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_data_observability_daily_monitoring_column_checks.asyncio(
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
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


___
## get_default_data_observability_daily_monitoring_table_checks
Returns UI model to show and edit the default configuration of the daily monitoring (Data Observability and monitoring) checks that are configured for all imported tables on a table level.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_data_observability_daily_monitoring_table_checks.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/daily/table
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_model`](../models/common.md#checkcontainermodel)</span>||*[CheckContainerModel](../models/common.md#checkcontainermodel)*|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/daily/table^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_data_observability_daily_monitoring_table_checks
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_data_observability_daily_monitoring_table_checks.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_data_observability_daily_monitoring_table_checks
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_data_observability_daily_monitoring_table_checks.asyncio(
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_data_observability_daily_monitoring_table_checks
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_data_observability_daily_monitoring_table_checks.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_data_observability_daily_monitoring_table_checks
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_data_observability_daily_monitoring_table_checks.asyncio(
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
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


___
## get_default_data_observability_monthly_monitoring_column_checks
Returns UI model to show and edit the default configuration of the monthly monitoring (Data Observability end of month scores) checks that are configured for all imported columns on a column level.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_data_observability_monthly_monitoring_column_checks.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/monthly/column
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_model`](../models/common.md#checkcontainermodel)</span>||*[CheckContainerModel](../models/common.md#checkcontainermodel)*|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/monthly/column^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_data_observability_monthly_monitoring_column_checks
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_data_observability_monthly_monitoring_column_checks.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_data_observability_monthly_monitoring_column_checks
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_data_observability_monthly_monitoring_column_checks.asyncio(
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_data_observability_monthly_monitoring_column_checks
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_data_observability_monthly_monitoring_column_checks.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_data_observability_monthly_monitoring_column_checks
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_data_observability_monthly_monitoring_column_checks.asyncio(
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
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


___
## get_default_data_observability_monthly_monitoring_table_checks
Returns UI model to show and edit the default configuration of the monthly monitoring (Data Observability end of month scores) checks that are configured for all imported tables on a table level.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_data_observability_monthly_monitoring_table_checks.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/monthly/table
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_model`](../models/common.md#checkcontainermodel)</span>||*[CheckContainerModel](../models/common.md#checkcontainermodel)*|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/monthly/table^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_data_observability_monthly_monitoring_table_checks
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_data_observability_monthly_monitoring_table_checks.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_data_observability_monthly_monitoring_table_checks
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_data_observability_monthly_monitoring_table_checks.asyncio(
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_data_observability_monthly_monitoring_table_checks
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_data_observability_monthly_monitoring_table_checks.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_data_observability_monthly_monitoring_table_checks
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_data_observability_monthly_monitoring_table_checks.asyncio(
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
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


___
## get_default_profiling_column_checks
Returns UI model to show and edit the default configuration of the profiling checks that are configured for all imported column on a column level.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_profiling_column_checks.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/defaults/defaultchecks/profiling/column
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_model`](../models/common.md#checkcontainermodel)</span>||*[CheckContainerModel](../models/common.md#checkcontainermodel)*|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/defaults/defaultchecks/profiling/column^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_profiling_column_checks
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_profiling_column_checks.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_profiling_column_checks
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_profiling_column_checks.asyncio(
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_profiling_column_checks
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_profiling_column_checks.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_profiling_column_checks
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_profiling_column_checks.asyncio(
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
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


___
## get_default_profiling_table_checks
Returns UI model to show and edit the default configuration of the profiling checks that are configured for all imported tables on a table level.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_profiling_table_checks.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/defaults/defaultchecks/profiling/table
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_container_model`](../models/common.md#checkcontainermodel)</span>||*[CheckContainerModel](../models/common.md#checkcontainermodel)*|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/defaults/defaultchecks/profiling/table^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_profiling_table_checks
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_profiling_table_checks.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_profiling_table_checks
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_profiling_table_checks.asyncio(
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_profiling_table_checks
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_profiling_table_checks.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_profiling_table_checks
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_profiling_table_checks.asyncio(
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
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


___
## get_default_schedule
Returns spec to show and edit the default configuration of schedules.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_schedule.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/defaults/defaultschedule/{schedulingGroup}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`monitoring_schedule_spec`](../models/common.md#monitoringschedulespec)</span>||*[MonitoringScheduleSpec](../models/common.md#monitoringschedulespec)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">[`scheduling_group`](../models/common.md#checkrunschedulegroup)</span>|Check scheduling group (named schedule)|*[CheckRunScheduleGroup](../models/common.md#checkrunschedulegroup)*|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/defaults/defaultschedule/partitioned_daily^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_schedule
	from dqops.client.models import CheckRunScheduleGroup
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_schedule.sync(
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_schedule
	from dqops.client.models import CheckRunScheduleGroup
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_schedule.asyncio(
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_schedule
	from dqops.client.models import CheckRunScheduleGroup
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_schedule.sync(
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_schedule
	from dqops.client.models import CheckRunScheduleGroup
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_schedule.asyncio(
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    {
	  "cron_expression" : "0 12 1 * *"
	}
    ```


___
## get_default_webhooks
Returns spec to show and edit the default configuration of webhooks.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/get_default_webhooks.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/defaults/defaultwebhooks
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`incident_webhook_notifications_spec`](../../reference/yaml/ConnectionYaml.md#incidentwebhooknotificationsspec)</span>||*[IncidentWebhookNotificationsSpec](../../reference/yaml/ConnectionYaml.md#incidentwebhooknotificationsspec)*|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/defaults/defaultwebhooks^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_webhooks
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_webhooks.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_webhooks
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_webhooks.asyncio(
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_webhooks
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_default_webhooks.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import get_default_webhooks
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_default_webhooks.asyncio(
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    {
	  "incident_opened_webhook_url" : "https://sample_url.com/opened",
	  "incident_acknowledged_webhook_url" : "https://sample_url.com/acknowledged",
	  "incident_resolved_webhook_url" : "https://sample_url.com/resolved",
	  "incident_muted_webhook_url" : "https://sample_url.com/muted"
	}
    ```


___
## update_default_data_observability_daily_monitoring_column_checks
New configuration of the default daily monitoring (data observability) checks on a column level. These checks will be applied on new columns.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_data_observability_daily_monitoring_column_checks.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/daily/column
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the default configuration of the data observability daily monitoring checks configuration|*[CheckContainerModel](../models/common.md#checkcontainermodel)*| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/daily/column^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_data_observability_daily_monitoring_column_checks
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
	
	call_result = update_default_data_observability_daily_monitoring_column_checks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_data_observability_daily_monitoring_column_checks
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
	
	call_result = await update_default_data_observability_daily_monitoring_column_checks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_data_observability_daily_monitoring_column_checks
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
	
	call_result = update_default_data_observability_daily_monitoring_column_checks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_data_observability_daily_monitoring_column_checks
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
	
	call_result = await update_default_data_observability_daily_monitoring_column_checks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```





___
## update_default_data_observability_daily_monitoring_table_checks
New configuration of the default daily monitoring (data observability) checks on a table level. These checks will be applied on new tables.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_data_observability_daily_monitoring_table_checks.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/daily/table
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the default configuration of the data observability daily monitoring checks configuration|*[CheckContainerModel](../models/common.md#checkcontainermodel)*| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/daily/table^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_data_observability_daily_monitoring_table_checks
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
	
	call_result = update_default_data_observability_daily_monitoring_table_checks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_data_observability_daily_monitoring_table_checks
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
	
	call_result = await update_default_data_observability_daily_monitoring_table_checks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_data_observability_daily_monitoring_table_checks
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
	
	call_result = update_default_data_observability_daily_monitoring_table_checks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_data_observability_daily_monitoring_table_checks
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
	
	call_result = await update_default_data_observability_daily_monitoring_table_checks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```





___
## update_default_data_observability_monthly_monitoring_column_checks
New configuration of the default monthly monitoring checkpoints on a column level. These checks will be applied on new columns.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_data_observability_monthly_monitoring_column_checks.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/monthly/column
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the default configuration of the data observability monthly monitoring checks configuration|*[CheckContainerModel](../models/common.md#checkcontainermodel)*| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/monthly/column^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_data_observability_monthly_monitoring_column_checks
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
	
	call_result = update_default_data_observability_monthly_monitoring_column_checks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_data_observability_monthly_monitoring_column_checks
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
	
	call_result = await update_default_data_observability_monthly_monitoring_column_checks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_data_observability_monthly_monitoring_column_checks
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
	
	call_result = update_default_data_observability_monthly_monitoring_column_checks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_data_observability_monthly_monitoring_column_checks
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
	
	call_result = await update_default_data_observability_monthly_monitoring_column_checks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```





___
## update_default_data_observability_monthly_monitoring_table_checks
New configuration of the default monthly monitoring checkpoints on a table level. These checks will be applied on new tables.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_data_observability_monthly_monitoring_table_checks.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/monthly/table
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the default configuration of the data observability monthly monitoring checks configuration|*[CheckContainerModel](../models/common.md#checkcontainermodel)*| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultchecks/dataobservability/monitoring/monthly/table^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_data_observability_monthly_monitoring_table_checks
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
	
	call_result = update_default_data_observability_monthly_monitoring_table_checks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_data_observability_monthly_monitoring_table_checks
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
	
	call_result = await update_default_data_observability_monthly_monitoring_table_checks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_data_observability_monthly_monitoring_table_checks
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
	
	call_result = update_default_data_observability_monthly_monitoring_table_checks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_data_observability_monthly_monitoring_table_checks
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
	
	call_result = await update_default_data_observability_monthly_monitoring_table_checks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```





___
## update_default_profiling_column_checks
New configuration of the default profiling checks on a column level. These checks will be applied to new columns.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_profiling_column_checks.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/defaults/defaultchecks/profiling/column
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality profiling checks configuration|*[CheckContainerModel](../models/common.md#checkcontainermodel)*| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultchecks/profiling/column^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_profiling_column_checks
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
	
	call_result = update_default_profiling_column_checks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_profiling_column_checks
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
	
	call_result = await update_default_profiling_column_checks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_profiling_column_checks
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
	
	call_result = update_default_profiling_column_checks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_profiling_column_checks
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
	
	call_result = await update_default_profiling_column_checks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```





___
## update_default_profiling_table_checks
New configuration of the default profiling checks on a table level. These checks will be applied to new tables.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_profiling_table_checks.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/defaults/defaultchecks/profiling/table
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality profiling checks configuration|*[CheckContainerModel](../models/common.md#checkcontainermodel)*| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultchecks/profiling/table^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_profiling_table_checks
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
	
	call_result = update_default_profiling_table_checks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_profiling_table_checks
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
	
	call_result = await update_default_profiling_table_checks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_profiling_table_checks
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
	
	call_result = update_default_profiling_table_checks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_profiling_table_checks
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
	
	call_result = await update_default_profiling_table_checks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```





___
## update_default_schedules
New configuration of the default schedules.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_schedules.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/defaults/defaultschedule/{schedulingGroup}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">[`scheduling_group`](../models/common.md#checkrunschedulegroup)</span>|Check scheduling group (named schedule)|*[CheckRunScheduleGroup](../models/common.md#checkrunschedulegroup)*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Spec with default schedules changes to be applied to the default configuration.|*[MonitoringScheduleSpec](../models/common.md#monitoringschedulespec)*| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultschedule/partitioned_daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"cron_expression\":\"0 12 1 * *\"}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_schedules
	from dqops.client.models import CheckRunScheduleGroup, \
	                                MonitoringScheduleSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = MonitoringScheduleSpec(
		cron_expression='0 12 1 * *',
		disabled=False
	)
	
	call_result = update_default_schedules.sync(
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_schedules
	from dqops.client.models import CheckRunScheduleGroup, \
	                                MonitoringScheduleSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = MonitoringScheduleSpec(
		cron_expression='0 12 1 * *',
		disabled=False
	)
	
	call_result = await update_default_schedules.asyncio(
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_schedules
	from dqops.client.models import CheckRunScheduleGroup, \
	                                MonitoringScheduleSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = MonitoringScheduleSpec(
		cron_expression='0 12 1 * *',
		disabled=False
	)
	
	call_result = update_default_schedules.sync(
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_schedules
	from dqops.client.models import CheckRunScheduleGroup, \
	                                MonitoringScheduleSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = MonitoringScheduleSpec(
		cron_expression='0 12 1 * *',
		disabled=False
	)
	
	call_result = await update_default_schedules.asyncio(
	    CheckRunScheduleGroup.partitioned_daily,
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```





___
## update_default_webhooks
New configuration of the default webhooks.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/defaults/update_default_webhooks.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/defaults/defaultwebhooks
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Spec with default notification webhooks changes to be applied to the default configuration|*[IncidentWebhookNotificationsSpec](../../reference/yaml/ConnectionYaml.md#incidentwebhooknotificationsspec)*| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/defaults/defaultwebhooks^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"incident_opened_webhook_url\":\"https://sample_url.com/opened\",\"incident_acknowledged_webhook_url\":\"https://sample_url.com/acknowledged\",\"incident_resolved_webhook_url\":\"https://sample_url.com/resolved\",\"incident_muted_webhook_url\":\"https://sample_url.com/muted\"}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_webhooks
	from dqops.client.models import IncidentWebhookNotificationsSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = IncidentWebhookNotificationsSpec(
		incident_opened_webhook_url='https://sample_url.com/opened',
		incident_acknowledged_webhook_url='https://sample_url.com/acknowledged',
		incident_resolved_webhook_url='https://sample_url.com/resolved',
		incident_muted_webhook_url='https://sample_url.com/muted'
	)
	
	call_result = update_default_webhooks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_webhooks
	from dqops.client.models import IncidentWebhookNotificationsSpec
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = IncidentWebhookNotificationsSpec(
		incident_opened_webhook_url='https://sample_url.com/opened',
		incident_acknowledged_webhook_url='https://sample_url.com/acknowledged',
		incident_resolved_webhook_url='https://sample_url.com/resolved',
		incident_muted_webhook_url='https://sample_url.com/muted'
	)
	
	call_result = await update_default_webhooks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_webhooks
	from dqops.client.models import IncidentWebhookNotificationsSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = IncidentWebhookNotificationsSpec(
		incident_opened_webhook_url='https://sample_url.com/opened',
		incident_acknowledged_webhook_url='https://sample_url.com/acknowledged',
		incident_resolved_webhook_url='https://sample_url.com/resolved',
		incident_muted_webhook_url='https://sample_url.com/muted'
	)
	
	call_result = update_default_webhooks.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.defaults import update_default_webhooks
	from dqops.client.models import IncidentWebhookNotificationsSpec
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = IncidentWebhookNotificationsSpec(
		incident_opened_webhook_url='https://sample_url.com/opened',
		incident_acknowledged_webhook_url='https://sample_url.com/acknowledged',
		incident_resolved_webhook_url='https://sample_url.com/resolved',
		incident_muted_webhook_url='https://sample_url.com/muted'
	)
	
	call_result = await update_default_webhooks.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```





