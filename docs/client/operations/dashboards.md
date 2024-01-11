# DQOps REST API dashboards operations
Operations for retrieving the list of data quality dashboards supported by DQOps and issuing short-term access keys to open a dashboard.


___
## get_all_dashboards
Returns a list of root folders with dashboards
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_all_dashboards.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/dashboards
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|dashboards_folder_spec||List[[DashboardsFolderSpec](../models/dashboards.md#dashboardsfolderspec)]|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/dashboards^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_all_dashboards
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_all_dashboards.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_all_dashboards
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_all_dashboards.asyncio(
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_all_dashboards
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_all_dashboards.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_all_dashboards
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_all_dashboards.asyncio(
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
    [ { }, { }, { } ]
    ```


___
## get_dashboard_level_1
Returns a single dashboard in the tree of folder with a temporary authenticated url
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_dashboard_level_1.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/dashboards/{folder}/{dashboardName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[authenticated_dashboard_model](../models/dashboards.md#authenticateddashboardmodel)||[AuthenticatedDashboardModel](../models/dashboards.md#authenticateddashboardmodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|folder|Root folder name|string|:material-check-bold:|
|dashboard_name|Dashboard name|string|:material-check-bold:|
|window_location_origin|Optional url of the DQOps instance, it should be the value of window.location.origin.|string| |






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/dashboards/sample_folder_0/sample_dashboard^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_1
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_dashboard_level_1.sync(
	    'sample_folder_0',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_1
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_dashboard_level_1.asyncio(
	    'sample_folder_0',
	    'sample_dashboard',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_1
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_dashboard_level_1.sync(
	    'sample_folder_0',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_1
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_dashboard_level_1.asyncio(
	    'sample_folder_0',
	    'sample_dashboard',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
    { }
    ```


___
## get_dashboard_level_2
Returns a single dashboard in the tree of folders with a temporary authenticated url
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_dashboard_level_2.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/dashboards/{folder1}/{folder2}/{dashboardName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[authenticated_dashboard_model](../models/dashboards.md#authenticateddashboardmodel)||[AuthenticatedDashboardModel](../models/dashboards.md#authenticateddashboardmodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|folder_1|Root folder name|string|:material-check-bold:|
|folder_2|Second level folder name|string|:material-check-bold:|
|dashboard_name|Dashboard name|string|:material-check-bold:|
|window_location_origin|Optional url of the DQOps instance, it should be the value of window.location.origin.|string| |






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/dashboards/sample_folder_1/sample_folder_2/sample_dashboard^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_2
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_dashboard_level_2.sync(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_2
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_dashboard_level_2.asyncio(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_dashboard',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_2
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_dashboard_level_2.sync(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_2
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_dashboard_level_2.asyncio(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_dashboard',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
    { }
    ```


___
## get_dashboard_level_3
Returns a single dashboard in the tree of folders with a temporary authenticated url
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_dashboard_level_3.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/dashboards/{folder1}/{folder2}/{folder3}/{dashboardName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[authenticated_dashboard_model](../models/dashboards.md#authenticateddashboardmodel)||[AuthenticatedDashboardModel](../models/dashboards.md#authenticateddashboardmodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|folder_1|Root folder name|string|:material-check-bold:|
|folder_2|Second level folder name|string|:material-check-bold:|
|folder_3|Third level folder name|string|:material-check-bold:|
|dashboard_name|Dashboard name|string|:material-check-bold:|
|window_location_origin|Optional url of the DQOps instance, it should be the value of window.location.origin.|string| |






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/dashboards/sample_folder_1/sample_folder_2/sample_folder_3/sample_dashboard^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_3
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_dashboard_level_3.sync(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_3
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_dashboard_level_3.asyncio(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_dashboard',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_3
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_dashboard_level_3.sync(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_3
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_dashboard_level_3.asyncio(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_dashboard',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
    { }
    ```


___
## get_dashboard_level_4
Returns a single dashboard in the tree of folders with a temporary authenticated url
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_dashboard_level_4.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/dashboards/{folder1}/{folder2}/{folder3}/{folder4}/{dashboardName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[authenticated_dashboard_model](../models/dashboards.md#authenticateddashboardmodel)||[AuthenticatedDashboardModel](../models/dashboards.md#authenticateddashboardmodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|folder_1|Root folder name|string|:material-check-bold:|
|folder_2|Second level folder name|string|:material-check-bold:|
|folder_3|Third level folder name|string|:material-check-bold:|
|folder_4|Fourth level folder name|string|:material-check-bold:|
|dashboard_name|Dashboard name|string|:material-check-bold:|
|window_location_origin|Optional url of the DQOps instance, it should be the value of window.location.origin.|string| |






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/dashboards/sample_folder_1/sample_folder_2/sample_folder_3/sample_folder_4/sample_dashboard^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_4
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_dashboard_level_4.sync(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_folder_4',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_4
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_dashboard_level_4.asyncio(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_folder_4',
	    'sample_dashboard',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_4
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_dashboard_level_4.sync(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_folder_4',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_4
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_dashboard_level_4.asyncio(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_folder_4',
	    'sample_dashboard',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
    { }
    ```


___
## get_dashboard_level_5
Returns a single dashboard in the tree of folders with a temporary authenticated url
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/dashboards/get_dashboard_level_5.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/dashboards/{folder1}/{folder2}/{folder3}/{folder4}/{folder5}/{dashboardName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[authenticated_dashboard_model](../models/dashboards.md#authenticateddashboardmodel)||[AuthenticatedDashboardModel](../models/dashboards.md#authenticateddashboardmodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|folder_1|Root folder name|string|:material-check-bold:|
|folder_2|Second level folder name|string|:material-check-bold:|
|folder_3|Third level folder name|string|:material-check-bold:|
|folder_4|Fourth level folder name|string|:material-check-bold:|
|folder_5|Fifth level folder name|string|:material-check-bold:|
|dashboard_name|Dashboard name|string|:material-check-bold:|
|window_location_origin|Optional url of the DQOps instance, it should be the value of window.location.origin.|string| |






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/dashboards/sample_folder_1/sample_folder_2/sample_folder_3/sample_folder_4/sample_folder_5/sample_dashboard^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_5
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_dashboard_level_5.sync(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_folder_4',
	    'sample_folder_5',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_5
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_dashboard_level_5.asyncio(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_folder_4',
	    'sample_folder_5',
	    'sample_dashboard',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_5
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_dashboard_level_5.sync(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_folder_4',
	    'sample_folder_5',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_5
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_dashboard_level_5.asyncio(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_folder_4',
	    'sample_folder_5',
	    'sample_dashboard',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
    { }
    ```


