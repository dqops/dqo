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
|<span class="no-wrap-code">`dashboards_folder_spec`</span>||*List[[DashboardsFolderSpec](../models/dashboards.md#dashboardsfolderspec)]*|








**Usage examples**


=== "curl"
    ### **Execution**

    ```bash
    curl http://localhost:8888/api/dashboards^
		-H "Accept: application/json"
	
    ```

    
    ### **Return value sample**
    
    
    ```js
    [ { }, { }, { } ]
    ```
    
    


=== "Python sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_all_dashboards
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_dashboards.sync(
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    [
		DashboardsFolderSpec(
			standard=False,
			dashboards=DashboardListSpec(),
			folders=DashboardsFolderListSpec()
		),
		DashboardsFolderSpec(
			standard=False,
			dashboards=DashboardListSpec(),
			folders=DashboardsFolderListSpec()
		),
		DashboardsFolderSpec(
			standard=False,
			dashboards=DashboardListSpec(),
			folders=DashboardsFolderListSpec()
		)
	]
    ```
    
    
    


=== "Python async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_all_dashboards
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_dashboards.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    [
		DashboardsFolderSpec(
			standard=False,
			dashboards=DashboardListSpec(),
			folders=DashboardsFolderListSpec()
		),
		DashboardsFolderSpec(
			standard=False,
			dashboards=DashboardListSpec(),
			folders=DashboardsFolderListSpec()
		),
		DashboardsFolderSpec(
			standard=False,
			dashboards=DashboardListSpec(),
			folders=DashboardsFolderListSpec()
		)
	]
    ```
    
    
    


=== "Python auth sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_all_dashboards
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_dashboards.sync(
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    [
		DashboardsFolderSpec(
			standard=False,
			dashboards=DashboardListSpec(),
			folders=DashboardsFolderListSpec()
		),
		DashboardsFolderSpec(
			standard=False,
			dashboards=DashboardListSpec(),
			folders=DashboardsFolderListSpec()
		),
		DashboardsFolderSpec(
			standard=False,
			dashboards=DashboardListSpec(),
			folders=DashboardsFolderListSpec()
		)
	]
    ```
    
    
    


=== "Python auth async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_all_dashboards
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_dashboards.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    [
		DashboardsFolderSpec(
			standard=False,
			dashboards=DashboardListSpec(),
			folders=DashboardsFolderListSpec()
		),
		DashboardsFolderSpec(
			standard=False,
			dashboards=DashboardListSpec(),
			folders=DashboardsFolderListSpec()
		),
		DashboardsFolderSpec(
			standard=False,
			dashboards=DashboardListSpec(),
			folders=DashboardsFolderListSpec()
		)
	]
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
|<span class="no-wrap-code">[`authenticated_dashboard_model`](../models/dashboards.md#authenticateddashboardmodel)</span>||*[AuthenticatedDashboardModel](../models/dashboards.md#authenticateddashboardmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`folder`</span>|Root folder name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`dashboard_name`</span>|Dashboard name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`window_location_origin`</span>|Optional url of the DQOps instance, it should be the value of window.location.origin.|*string*| |






**Usage examples**


=== "curl"
    ### **Execution**

    ```bash
    curl http://localhost:8888/api/dashboards/sample_folder_0/sample_dashboard^
		-H "Accept: application/json"
	
    ```

    
    ### **Return value sample**
    
    
    ```js
    { }
    ```
    
    


=== "Python sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_1
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_dashboard_level_1.sync(
	    'sample_folder_0',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
    ```
    
    
    


=== "Python async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_1
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_dashboard_level_1.asyncio(
	    'sample_folder_0',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
    ```
    
    
    


=== "Python auth sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_1
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_dashboard_level_1.sync(
	    'sample_folder_0',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
    ```
    
    
    


=== "Python auth async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_1
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_dashboard_level_1.asyncio(
	    'sample_folder_0',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
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
|<span class="no-wrap-code">[`authenticated_dashboard_model`](../models/dashboards.md#authenticateddashboardmodel)</span>||*[AuthenticatedDashboardModel](../models/dashboards.md#authenticateddashboardmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`folder_1`</span>|Root folder name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`folder_2`</span>|Second level folder name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`dashboard_name`</span>|Dashboard name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`window_location_origin`</span>|Optional url of the DQOps instance, it should be the value of window.location.origin.|*string*| |






**Usage examples**


=== "curl"
    ### **Execution**

    ```bash
    curl http://localhost:8888/api/dashboards/sample_folder_1/sample_folder_2/sample_dashboard^
		-H "Accept: application/json"
	
    ```

    
    ### **Return value sample**
    
    
    ```js
    { }
    ```
    
    


=== "Python sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_2
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_dashboard_level_2.sync(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
    ```
    
    
    


=== "Python async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_2
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_dashboard_level_2.asyncio(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
    ```
    
    
    


=== "Python auth sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_2
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_dashboard_level_2.sync(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
    ```
    
    
    


=== "Python auth async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_2
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_dashboard_level_2.asyncio(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
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
|<span class="no-wrap-code">[`authenticated_dashboard_model`](../models/dashboards.md#authenticateddashboardmodel)</span>||*[AuthenticatedDashboardModel](../models/dashboards.md#authenticateddashboardmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`folder_1`</span>|Root folder name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`folder_2`</span>|Second level folder name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`folder_3`</span>|Third level folder name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`dashboard_name`</span>|Dashboard name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`window_location_origin`</span>|Optional url of the DQOps instance, it should be the value of window.location.origin.|*string*| |






**Usage examples**


=== "curl"
    ### **Execution**

    ```bash
    curl http://localhost:8888/api/dashboards/sample_folder_1/sample_folder_2/sample_folder_3/sample_dashboard^
		-H "Accept: application/json"
	
    ```

    
    ### **Return value sample**
    
    
    ```js
    { }
    ```
    
    


=== "Python sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_3
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_dashboard_level_3.sync(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
    ```
    
    
    


=== "Python async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_3
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_dashboard_level_3.asyncio(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
    ```
    
    
    


=== "Python auth sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_3
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_dashboard_level_3.sync(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
    ```
    
    
    


=== "Python auth async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_3
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_dashboard_level_3.asyncio(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
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
|<span class="no-wrap-code">[`authenticated_dashboard_model`](../models/dashboards.md#authenticateddashboardmodel)</span>||*[AuthenticatedDashboardModel](../models/dashboards.md#authenticateddashboardmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`folder_1`</span>|Root folder name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`folder_2`</span>|Second level folder name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`folder_3`</span>|Third level folder name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`folder_4`</span>|Fourth level folder name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`dashboard_name`</span>|Dashboard name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`window_location_origin`</span>|Optional url of the DQOps instance, it should be the value of window.location.origin.|*string*| |






**Usage examples**


=== "curl"
    ### **Execution**

    ```bash
    curl http://localhost:8888/api/dashboards/sample_folder_1/sample_folder_2/sample_folder_3/sample_folder_4/sample_dashboard^
		-H "Accept: application/json"
	
    ```

    
    ### **Return value sample**
    
    
    ```js
    { }
    ```
    
    


=== "Python sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_4
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_dashboard_level_4.sync(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_folder_4',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
    ```
    
    
    


=== "Python async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_4
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_dashboard_level_4.asyncio(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_folder_4',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
    ```
    
    
    


=== "Python auth sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_4
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_dashboard_level_4.sync(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_folder_4',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
    ```
    
    
    


=== "Python auth async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_4
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_dashboard_level_4.asyncio(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_folder_4',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
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
|<span class="no-wrap-code">[`authenticated_dashboard_model`](../models/dashboards.md#authenticateddashboardmodel)</span>||*[AuthenticatedDashboardModel](../models/dashboards.md#authenticateddashboardmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`folder_1`</span>|Root folder name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`folder_2`</span>|Second level folder name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`folder_3`</span>|Third level folder name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`folder_4`</span>|Fourth level folder name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`folder_5`</span>|Fifth level folder name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`dashboard_name`</span>|Dashboard name|*string*|:material-check-bold:|
|<span class="no-wrap-code">`window_location_origin`</span>|Optional url of the DQOps instance, it should be the value of window.location.origin.|*string*| |






**Usage examples**


=== "curl"
    ### **Execution**

    ```bash
    curl http://localhost:8888/api/dashboards/sample_folder_1/sample_folder_2/sample_folder_3/sample_folder_4/sample_folder_5/sample_dashboard^
		-H "Accept: application/json"
	
    ```

    
    ### **Return value sample**
    
    
    ```js
    { }
    ```
    
    


=== "Python sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_5
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_dashboard_level_5.sync(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_folder_4',
	    'sample_folder_5',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
    ```
    
    
    


=== "Python async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_5
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_dashboard_level_5.asyncio(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_folder_4',
	    'sample_folder_5',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
    ```
    
    
    


=== "Python auth sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_5
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_dashboard_level_5.sync(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_folder_4',
	    'sample_folder_5',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
    ```
    
    
    


=== "Python auth async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.dashboards import get_dashboard_level_5
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_dashboard_level_5.asyncio(
	    'sample_folder_1',
	    'sample_folder_2',
	    'sample_folder_3',
	    'sample_folder_4',
	    'sample_folder_5',
	    'sample_dashboard',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    AuthenticatedDashboardModel()
    ```
    
    
    



