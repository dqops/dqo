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
|check_configuration_model||List[[CheckConfigurationModel](../models/common.md#checkconfigurationmodel)]|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|[time_scale](../models/common.md#checktimescale)|Check time-scale|[CheckTimeScale](../models/common.md#checktimescale)|:material-check-bold:|
|table_name_pattern|Table name pattern|string| |
|column_name_pattern|Column name pattern|string| |
|column_data_type|Column data-type|string| |
|[check_target](../models/schemas.md#checktarget)|Check target|[CheckTarget](../models/schemas.md#checktarget)| |
|check_category|Check category|string| |
|check_name|Check name|string| |
|check_enabled|Check enabled|boolean| |
|check_configured|Check configured|boolean| |
|limit|Limit of results, the default value is 1000|long| |






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
|check_template||List[[CheckTemplate](../models/common.md#checktemplate)]|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|[time_scale](../models/common.md#checktimescale)|Time scale|[CheckTimeScale](../models/common.md#checktimescale)|:material-check-bold:|
|[check_target](../models/schemas.md#checktarget)|Check target|[CheckTarget](../models/schemas.md#checktarget)| |
|check_category|Check category|string| |
|check_name|Check name|string| |






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
|check_configuration_model||List[[CheckConfigurationModel](../models/common.md#checkconfigurationmodel)]|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|[time_scale](../models/common.md#checktimescale)|Check time-scale|[CheckTimeScale](../models/common.md#checktimescale)|:material-check-bold:|
|table_name_pattern|Table name pattern|string| |
|column_name_pattern|Column name pattern|string| |
|column_data_type|Column data-type|string| |
|[check_target](../models/schemas.md#checktarget)|Check target|[CheckTarget](../models/schemas.md#checktarget)| |
|check_category|Check category|string| |
|check_name|Check name|string| |
|check_enabled|Check enabled|boolean| |
|check_configured|Check configured|boolean| |
|limit|Limit of results, the default value is 1000|long| |






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
|check_template||List[[CheckTemplate](../models/common.md#checktemplate)]|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|[time_scale](../models/common.md#checktimescale)|Time scale|[CheckTimeScale](../models/common.md#checktimescale)|:material-check-bold:|
|[check_target](../models/schemas.md#checktarget)|Check target|[CheckTarget](../models/schemas.md#checktarget)| |
|check_category|Check category|string| |
|check_name|Check name|string| |






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
|check_configuration_model||List[[CheckConfigurationModel](../models/common.md#checkconfigurationmodel)]|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name_pattern|Table name pattern|string| |
|column_name_pattern|Column name pattern|string| |
|column_data_type|Column data-type|string| |
|[check_target](../models/schemas.md#checktarget)|Check target|[CheckTarget](../models/schemas.md#checktarget)| |
|check_category|Check category|string| |
|check_name|Check name|string| |
|check_enabled|Check enabled|boolean| |
|check_configured|Check configured|boolean| |
|limit|Limit of results, the default value is 1000|long| |






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
|check_template||List[[CheckTemplate](../models/common.md#checktemplate)]|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|[check_target](../models/schemas.md#checktarget)|Check target|[CheckTarget](../models/schemas.md#checktarget)| |
|check_category|Check category|string| |
|check_name|Check name|string| |






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
|schema_model||List[[SchemaModel](../models/schemas.md#schemamodel)]|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






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


