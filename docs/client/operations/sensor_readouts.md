# DQOps REST API sensor_readouts operations
Operations that are retrieving the data quality sensor readouts of executed checks on tables and columns.


___
## get_column_monitoring_sensor_readouts
Returns a complete view of the sensor readouts for recent column level monitoring executions for the monitoring at a requested time scale
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensor_readouts/get_column_monitoring_sensor_readouts.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/readouts
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`sensor_readouts_list_model`</span>||*List[[SensorReadoutsListModel](../models/sensor_readouts.md#sensorreadoutslistmodel)]*|




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

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily/readouts^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_column_monitoring_sensor_readouts
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_sensor_readouts.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_column_monitoring_sensor_readouts
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_sensor_readouts.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_column_monitoring_sensor_readouts
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_monitoring_sensor_readouts.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_column_monitoring_sensor_readouts
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_monitoring_sensor_readouts.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    [ {
	  "sensorReadoutEntries" : [ ]
	}, {
	  "sensorReadoutEntries" : [ ]
	}, {
	  "sensorReadoutEntries" : [ ]
	} ]
    ```


___
## get_column_partitioned_sensor_readouts
Returns a view of the sensor readouts for recent column level partitioned checks executions for a requested time scale
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensor_readouts/get_column_partitioned_sensor_readouts.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/readouts
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`sensor_readouts_list_model`</span>||*List[[SensorReadoutsListModel](../models/sensor_readouts.md#sensorreadoutslistmodel)]*|




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

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily/readouts^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_column_partitioned_sensor_readouts
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_sensor_readouts.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_column_partitioned_sensor_readouts
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_sensor_readouts.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_column_partitioned_sensor_readouts
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_partitioned_sensor_readouts.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_column_partitioned_sensor_readouts
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_partitioned_sensor_readouts.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    [ {
	  "sensorReadoutEntries" : [ ]
	}, {
	  "sensorReadoutEntries" : [ ]
	}, {
	  "sensorReadoutEntries" : [ ]
	} ]
    ```


___
## get_column_profiling_sensor_readouts
Returns sensor results of the recent check executions for all column level data quality profiling checks on a column
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensor_readouts/get_column_profiling_sensor_readouts.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/readouts
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`sensor_readouts_list_model`</span>||*List[[SensorReadoutsListModel](../models/sensor_readouts.md#sensorreadoutslistmodel)]*|




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

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/readouts^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_column_profiling_sensor_readouts
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_profiling_sensor_readouts.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_column_profiling_sensor_readouts
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_profiling_sensor_readouts.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_column_profiling_sensor_readouts
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_column_profiling_sensor_readouts.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_column_profiling_sensor_readouts
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_column_profiling_sensor_readouts.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    'sample_column',
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    [ {
	  "sensorReadoutEntries" : [ ]
	}, {
	  "sensorReadoutEntries" : [ ]
	}, {
	  "sensorReadoutEntries" : [ ]
	} ]
    ```


___
## get_table_monitoring_sensor_readouts
Returns the complete results of the most recent table level monitoring executions for the monitoring at a requested time scale
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensor_readouts/get_table_monitoring_sensor_readouts.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/readouts
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`sensor_readouts_list_model`</span>||*List[[SensorReadoutsListModel](../models/sensor_readouts.md#sensorreadoutslistmodel)]*|




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

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/daily/readouts^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_table_monitoring_sensor_readouts
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_sensor_readouts.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_table_monitoring_sensor_readouts
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_sensor_readouts.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_table_monitoring_sensor_readouts
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_monitoring_sensor_readouts.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_table_monitoring_sensor_readouts
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_monitoring_sensor_readouts.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    [ {
	  "sensorReadoutEntries" : [ ]
	}, {
	  "sensorReadoutEntries" : [ ]
	}, {
	  "sensorReadoutEntries" : [ ]
	} ]
    ```


___
## get_table_partitioned_sensor_readouts
Returns a complete view of sensor readouts for recent table level partitioned checks executions for a requested time scale
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensor_readouts/get_table_partitioned_sensor_readouts.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/readouts
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`sensor_readouts_list_model`</span>||*List[[SensorReadoutsListModel](../models/sensor_readouts.md#sensorreadoutslistmodel)]*|




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

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/daily/readouts^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_table_partitioned_sensor_readouts
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_sensor_readouts.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_table_partitioned_sensor_readouts
	from dqops.client.models import CheckTimeScale
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_sensor_readouts.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_table_partitioned_sensor_readouts
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_partitioned_sensor_readouts.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_table_partitioned_sensor_readouts
	from dqops.client.models import CheckTimeScale
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_partitioned_sensor_readouts.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    CheckTimeScale.daily,
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    [ {
	  "sensorReadoutEntries" : [ ]
	}, {
	  "sensorReadoutEntries" : [ ]
	}, {
	  "sensorReadoutEntries" : [ ]
	} ]
    ```


___
## get_table_profiling_sensor_readouts
Returns the complete results of the most recent check executions for all table level data quality profiling checks on a table
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/sensor_readouts/get_table_profiling_sensor_readouts.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/readouts
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`sensor_readouts_list_model`</span>||*List[[SensorReadoutsListModel](../models/sensor_readouts.md#sensorreadoutslistmodel)]*|




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

    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/readouts^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_table_profiling_sensor_readouts
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_sensor_readouts.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_table_profiling_sensor_readouts
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_sensor_readouts.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_table_profiling_sensor_readouts
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_table_profiling_sensor_readouts.sync(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.sensor_readouts import get_table_profiling_sensor_readouts
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_table_profiling_sensor_readouts.asyncio(
	    'sample_connection',
	    'sample_schema',
	    'sample_table',
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    [ {
	  "sensorReadoutEntries" : [ ]
	}, {
	  "sensorReadoutEntries" : [ ]
	}, {
	  "sensorReadoutEntries" : [ ]
	} ]
    ```


