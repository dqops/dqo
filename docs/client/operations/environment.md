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
    ### **Execution**

    ```bash
    curl http://localhost:8888/api/environment/settings^
		-H "Accept: application/json"
	
    ```

    
    ### **Return value sample**
    
    
    ```js
    {
	  "properties" : { }
	}
    ```
    
    


=== "Python sync client"
    ### **Execution**

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

    
    ### **Return value sample**
    
    ```python
    DqoSettingsModel(
		properties={
		
		}
	)
    ```
    
    
    


=== "Python async client"
    ### **Execution**

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

    
    ### **Return value sample**
    
    ```python
    DqoSettingsModel(
		properties={
		
		}
	)
    ```
    
    
    


=== "Python auth sync client"
    ### **Execution**

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

    
    ### **Return value sample**
    
    ```python
    DqoSettingsModel(
		properties={
		
		}
	)
    ```
    
    
    


=== "Python auth async client"
    ### **Execution**

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

    
    ### **Return value sample**
    
    ```python
    DqoSettingsModel(
		properties={
		
		}
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
    ### **Execution**

    ```bash
    curl http://localhost:8888/api/environment/profile^
		-H "Accept: application/json"
	
    ```

    
    ### **Return value sample**
    
    
    ```js
    {
	  "data_quality_data_warehouse_enabled" : false,
	  "can_manage_account" : false,
	  "can_view_any_object" : false,
	  "can_manage_scheduler" : false,
	  "can_cancel_jobs" : false,
	  "can_run_checks" : false,
	  "can_delete_data" : false,
	  "can_collect_statistics" : false,
	  "can_manage_data_sources" : false,
	  "can_synchronize" : false,
	  "can_edit_comments" : false,
	  "can_edit_labels" : false,
	  "can_manage_definitions" : false,
	  "can_compare_tables" : false,
	  "can_manage_users" : false,
	  "can_manage_and_view_shared_credentials" : false,
	  "can_change_own_password" : false
	}
    ```
    
    


=== "Python sync client"
    ### **Execution**

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

    
    ### **Return value sample**
    
    ```python
    DqoUserProfileModel(
		data_quality_data_warehouse_enabled=False,
		can_manage_account=False,
		can_view_any_object=False,
		can_manage_scheduler=False,
		can_cancel_jobs=False,
		can_run_checks=False,
		can_delete_data=False,
		can_collect_statistics=False,
		can_manage_data_sources=False,
		can_synchronize=False,
		can_edit_comments=False,
		can_edit_labels=False,
		can_manage_definitions=False,
		can_compare_tables=False,
		can_manage_users=False,
		can_manage_and_view_shared_credentials=False,
		can_change_own_password=False
	)
    ```
    
    
    


=== "Python async client"
    ### **Execution**

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

    
    ### **Return value sample**
    
    ```python
    DqoUserProfileModel(
		data_quality_data_warehouse_enabled=False,
		can_manage_account=False,
		can_view_any_object=False,
		can_manage_scheduler=False,
		can_cancel_jobs=False,
		can_run_checks=False,
		can_delete_data=False,
		can_collect_statistics=False,
		can_manage_data_sources=False,
		can_synchronize=False,
		can_edit_comments=False,
		can_edit_labels=False,
		can_manage_definitions=False,
		can_compare_tables=False,
		can_manage_users=False,
		can_manage_and_view_shared_credentials=False,
		can_change_own_password=False
	)
    ```
    
    
    


=== "Python auth sync client"
    ### **Execution**

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

    
    ### **Return value sample**
    
    ```python
    DqoUserProfileModel(
		data_quality_data_warehouse_enabled=False,
		can_manage_account=False,
		can_view_any_object=False,
		can_manage_scheduler=False,
		can_cancel_jobs=False,
		can_run_checks=False,
		can_delete_data=False,
		can_collect_statistics=False,
		can_manage_data_sources=False,
		can_synchronize=False,
		can_edit_comments=False,
		can_edit_labels=False,
		can_manage_definitions=False,
		can_compare_tables=False,
		can_manage_users=False,
		can_manage_and_view_shared_credentials=False,
		can_change_own_password=False
	)
    ```
    
    
    


=== "Python auth async client"
    ### **Execution**

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

    
    ### **Return value sample**
    
    ```python
    DqoUserProfileModel(
		data_quality_data_warehouse_enabled=False,
		can_manage_account=False,
		can_view_any_object=False,
		can_manage_scheduler=False,
		can_cancel_jobs=False,
		can_run_checks=False,
		can_delete_data=False,
		can_collect_statistics=False,
		can_manage_data_sources=False,
		can_synchronize=False,
		can_edit_comments=False,
		can_edit_labels=False,
		can_manage_definitions=False,
		can_compare_tables=False,
		can_manage_users=False,
		can_manage_and_view_shared_credentials=False,
		can_change_own_password=False
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
    ### **Execution**

    ```bash
    curl http://localhost:8888/api/environment/issueapikey^
		-H "Accept: application/json"
	
    ```

    
    ### **Return value sample**
    
    
    ```js
    "sample_string_value"
    ```
    
    


=== "Python sync client"
    ### **Execution**

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

    
    ### **Return value sample**
    
    ```python
    'sample_string_value'
    ```
    
    
    


=== "Python async client"
    ### **Execution**

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

    
    ### **Return value sample**
    
    ```python
    'sample_string_value'
    ```
    
    
    


=== "Python auth sync client"
    ### **Execution**

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

    
    ### **Return value sample**
    
    ```python
    'sample_string_value'
    ```
    
    
    


=== "Python auth async client"
    ### **Execution**

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

    
    ### **Return value sample**
    
    ```python
    'sample_string_value'
    ```
    
    
    



