# DQOps REST API schemas operations
Operations for listing imported schemas from monitored data sources. Also provides operations for activating and deactivating multiple checks at once.


___
## get_schema_monitoring_checks_model
Return a UI friendly model of configurations for data quality monitoring checks on a schema
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_monitoring_checks_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/monitoring/{timeScale}/model
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_configuration_model`</span>||*List[[CheckConfigurationModel](../models/common.md#checkconfigurationmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Check time-scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`table_name_pattern`</span>|Table name pattern|*string*| |
|<span class="no-wrap-code">`column_name_pattern`</span>|Column name pattern|*string*| |
|<span class="no-wrap-code">`column_data_type`</span>|Column data-type|*string*| |
|<span class="no-wrap-code">[`check_target`](../models/schemas.md#checktarget)</span>|Check target|*[CheckTarget](../models/schemas.md#checktarget)*| |
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`check_enabled`</span>|Check enabled|*boolean*| |
|<span class="no-wrap-code">`check_configured`</span>|Check configured|*boolean*| |
|<span class="no-wrap-code">`limit`</span>|Limit of results, the default value is 1000|*long*| |






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/monitoring/daily/model^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_monitoring_checks_model
	from dqops.client.models import CheckTarget, \
	                                CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_schema_monitoring_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_monitoring_checks_model
	from dqops.client.models import CheckTarget, \
	                                CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_schema_monitoring_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_monitoring_checks_model
	from dqops.client.models import CheckTarget, \
	                                CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_schema_monitoring_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_monitoring_checks_model
	from dqops.client.models import CheckTarget, \
	                                CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_schema_monitoring_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    [ {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	}, {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	}, {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	} ]
    ```


___
## get_schema_monitoring_checks_templates
Return available data quality checks on a requested schema.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_monitoring_checks_templates.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/bulkenable/monitoring/{timeScale}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_template`</span>||*List[[CheckTemplate](../models/common.md#checktemplate)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">[`check_target`](../models/schemas.md#checktarget)</span>|Check target|*[CheckTarget](../models/schemas.md#checktarget)*| |
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/bulkenable/monitoring/daily^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_monitoring_checks_templates
	from dqops.client.models import CheckTarget, \
	                                CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_schema_monitoring_checks_templates.sync(
	    'sample_connection',
	    'sample_schema',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_monitoring_checks_templates
	from dqops.client.models import CheckTarget, \
	                                CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_schema_monitoring_checks_templates.asyncio(
	    'sample_connection',
	    'sample_schema',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_monitoring_checks_templates
	from dqops.client.models import CheckTarget, \
	                                CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_schema_monitoring_checks_templates.sync(
	    'sample_connection',
	    'sample_schema',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_monitoring_checks_templates
	from dqops.client.models import CheckTarget, \
	                                CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_schema_monitoring_checks_templates.asyncio(
	    'sample_connection',
	    'sample_schema',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    [ {
	  "sensor_parameters_definitions" : [ ]
	}, {
	  "sensor_parameters_definitions" : [ ]
	}, {
	  "sensor_parameters_definitions" : [ ]
	} ]
    ```


___
## get_schema_partitioned_checks_model
Return a UI friendly model of configurations for data quality partitioned checks on a schema
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_partitioned_checks_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/partitioned/{timeScale}/model
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_configuration_model`</span>||*List[[CheckConfigurationModel](../models/common.md#checkconfigurationmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Check time-scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">`table_name_pattern`</span>|Table name pattern|*string*| |
|<span class="no-wrap-code">`column_name_pattern`</span>|Column name pattern|*string*| |
|<span class="no-wrap-code">`column_data_type`</span>|Column data-type|*string*| |
|<span class="no-wrap-code">[`check_target`](../models/schemas.md#checktarget)</span>|Check target|*[CheckTarget](../models/schemas.md#checktarget)*| |
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`check_enabled`</span>|Check enabled|*boolean*| |
|<span class="no-wrap-code">`check_configured`</span>|Check configured|*boolean*| |
|<span class="no-wrap-code">`limit`</span>|Limit of results, the default value is 1000|*long*| |






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/partitioned/daily/model^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_partitioned_checks_model
	from dqops.client.models import CheckTarget, \
	                                CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_schema_partitioned_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_partitioned_checks_model
	from dqops.client.models import CheckTarget, \
	                                CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_schema_partitioned_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_partitioned_checks_model
	from dqops.client.models import CheckTarget, \
	                                CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_schema_partitioned_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_partitioned_checks_model
	from dqops.client.models import CheckTarget, \
	                                CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_schema_partitioned_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    [ {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	}, {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	}, {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	} ]
    ```


___
## get_schema_partitioned_checks_templates
Return available data quality checks on a requested schema.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_partitioned_checks_templates.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/bulkenable/partitioned/{timeScale}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_template`</span>||*List[[CheckTemplate](../models/common.md#checktemplate)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`time_scale`](../models/common.md#checktimescale)</span>|Time scale|*[CheckTimeScale](../models/common.md#checktimescale)*|:material-check-bold:|
|<span class="no-wrap-code">[`check_target`](../models/schemas.md#checktarget)</span>|Check target|*[CheckTarget](../models/schemas.md#checktarget)*| |
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/bulkenable/partitioned/daily^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_partitioned_checks_templates
	from dqops.client.models import CheckTarget, \
	                                CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_schema_partitioned_checks_templates.sync(
	    'sample_connection',
	    'sample_schema',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_partitioned_checks_templates
	from dqops.client.models import CheckTarget, \
	                                CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_schema_partitioned_checks_templates.asyncio(
	    'sample_connection',
	    'sample_schema',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_partitioned_checks_templates
	from dqops.client.models import CheckTarget, \
	                                CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_schema_partitioned_checks_templates.sync(
	    'sample_connection',
	    'sample_schema',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_partitioned_checks_templates
	from dqops.client.models import CheckTarget, \
	                                CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_schema_partitioned_checks_templates.asyncio(
	    'sample_connection',
	    'sample_schema',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    [ {
	  "sensor_parameters_definitions" : [ ]
	}, {
	  "sensor_parameters_definitions" : [ ]
	}, {
	  "sensor_parameters_definitions" : [ ]
	} ]
    ```


___
## get_schema_profiling_checks_model
Return a flat list of configurations for profiling checks on a schema
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_profiling_checks_model.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/profiling/model
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_configuration_model`</span>||*List[[CheckConfigurationModel](../models/common.md#checkconfigurationmodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`table_name_pattern`</span>|Table name pattern|*string*| |
|<span class="no-wrap-code">`column_name_pattern`</span>|Column name pattern|*string*| |
|<span class="no-wrap-code">`column_data_type`</span>|Column data-type|*string*| |
|<span class="no-wrap-code">[`check_target`](../models/schemas.md#checktarget)</span>|Check target|*[CheckTarget](../models/schemas.md#checktarget)*| |
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |
|<span class="no-wrap-code">`check_enabled`</span>|Check enabled|*boolean*| |
|<span class="no-wrap-code">`check_configured`</span>|Check configured|*boolean*| |
|<span class="no-wrap-code">`limit`</span>|Limit of results, the default value is 1000|*long*| |






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/profiling/model^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_profiling_checks_model
	from dqops.client.models import CheckTarget
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_schema_profiling_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_profiling_checks_model
	from dqops.client.models import CheckTarget
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_schema_profiling_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_profiling_checks_model
	from dqops.client.models import CheckTarget
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_schema_profiling_checks_model.sync(
	    'sample_connection',
	    'sample_schema',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_profiling_checks_model
	from dqops.client.models import CheckTarget
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_schema_profiling_checks_model.asyncio(
	    'sample_connection',
	    'sample_schema',
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    [ {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	}, {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	}, {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	} ]
    ```


___
## get_schema_profiling_checks_templates
Return available data quality checks on a requested schema.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_profiling_checks_templates.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/bulkenable/profiling
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_template`</span>||*List[[CheckTemplate](../models/common.md#checktemplate)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|:material-check-bold:|
|<span class="no-wrap-code">[`check_target`](../models/schemas.md#checktarget)</span>|Check target|*[CheckTarget](../models/schemas.md#checktarget)*| |
|<span class="no-wrap-code">`check_category`</span>|Check category|*string*| |
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*| |






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/bulkenable/profiling^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_profiling_checks_templates
	from dqops.client.models import CheckTarget
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_schema_profiling_checks_templates.sync(
	    'sample_connection',
	    'sample_schema',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_profiling_checks_templates
	from dqops.client.models import CheckTarget
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_schema_profiling_checks_templates.asyncio(
	    'sample_connection',
	    'sample_schema',
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_profiling_checks_templates
	from dqops.client.models import CheckTarget
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_schema_profiling_checks_templates.sync(
	    'sample_connection',
	    'sample_schema',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schema_profiling_checks_templates
	from dqops.client.models import CheckTarget
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_schema_profiling_checks_templates.asyncio(
	    'sample_connection',
	    'sample_schema',
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    [ {
	  "sensor_parameters_definitions" : [ ]
	}, {
	  "sensor_parameters_definitions" : [ ]
	}, {
	  "sensor_parameters_definitions" : [ ]
	} ]
    ```


___
## get_schemas
Returns a list of schemas inside a connection
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schemas.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`schema_model`</span>||*List[[SchemaModel](../models/schemas.md#schemamodel)]*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schemas
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_schemas.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schemas
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_schemas.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schemas
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_schemas.sync(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.schemas import get_schemas
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_schemas.asyncio(
	    'sample_connection',
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    [ {
	  "can_edit" : false,
	  "can_collect_statistics" : false,
	  "can_run_checks" : false,
	  "can_delete_data" : false
	}, {
	  "can_edit" : false,
	  "can_collect_statistics" : false,
	  "can_run_checks" : false,
	  "can_delete_data" : false
	}, {
	  "can_edit" : false,
	  "can_collect_statistics" : false,
	  "can_run_checks" : false,
	  "can_delete_data" : false
	} ]
    ```


