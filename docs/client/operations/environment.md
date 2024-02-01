# DQOps REST API environment operations
DQOps environment and configuration controller, provides access to the DQOps configuration, current user&#x27;s information and issue local API Keys for the calling user.


___
## get_dqo_settings
Returns all effective DQOps configuration settings.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/environment/get_dqo_settings.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/environment/settings
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`dqo_settings_model`](../models/environment.md#dqosettingsmodel)</span>||*[DqoSettingsModel](../models/environment.md#dqosettingsmodel)*|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/environment/settings^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.environment import get_dqo_settings
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_dqo_settings.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.environment import get_dqo_settings
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_dqo_settings.asyncio(
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.environment import get_dqo_settings
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_dqo_settings.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.environment import get_dqo_settings
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_dqo_settings.asyncio(
	    client=dqops_client
	)
	
    ```





___
## get_user_profile
Returns the profile of the current user.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/environment/get_user_profile.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/environment/profile
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`dqo_user_profile_model`](../models/environment.md#dqouserprofilemodel)</span>||*[DqoUserProfileModel](../models/environment.md#dqouserprofilemodel)*|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/environment/profile^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.environment import get_user_profile
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_user_profile.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.environment import get_user_profile
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_user_profile.asyncio(
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.environment import get_user_profile
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_user_profile.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.environment import get_user_profile
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_user_profile.asyncio(
	    client=dqops_client
	)
	
    ```





___
## issue_api_key
Issues a local API Key for the calling user. This API Key can be used to authenticate using the DQOps REST API client. This API Key should be passed in the &quot;Authorization&quot; HTTP header in the format &quot;Authorization: Bearer &lt;api_key&gt;&quot;.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/environment/issue_api_key.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/environment/issueapikey
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`string`</span>||*string*|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/environment/issueapikey^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.environment import issue_api_key
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = issue_api_key.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.environment import issue_api_key
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await issue_api_key.asyncio(
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.environment import issue_api_key
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = issue_api_key.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.environment import issue_api_key
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await issue_api_key.asyncio(
	    client=dqops_client
	)
	
    ```





