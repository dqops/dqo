# DQOps REST API checks operations
Data quality check definition management operations for adding/removing/changing custom data quality checks.


___
## create_check
Creates (adds) a new custom check that is a pair of a sensor name and a rule name.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/create_check.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/checks/{fullCheckName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`full_check_name`</span>|Full check name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Check model|*[CheckDefinitionModel](../models/checks.md#checkdefinitionmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/checks/sample_target/sample_category/sample_check^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"check_name\":\"sample_check\",\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"rule_name\":\"sample_target/sample_category/sample_rule\",\"help_text\":\"Sample help text\",\"standard\":false,\"custom\":true,\"built_in\":false,\"can_edit\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import create_check
	from dqops.client.models import CheckDefinitionModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckDefinitionModel(
		check_name='sample_check',
		sensor_name='sample_target/sample_category/sample_sensor',
		rule_name='sample_target/sample_category/sample_rule',
		help_text='Sample help text',
		standard=False,
		custom=True,
		built_in=False,
		can_edit=True
	)
	
	call_result = create_check.sync(
	    'sample_target/sample_category/sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import create_check
	from dqops.client.models import CheckDefinitionModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckDefinitionModel(
		check_name='sample_check',
		sensor_name='sample_target/sample_category/sample_sensor',
		rule_name='sample_target/sample_category/sample_rule',
		help_text='Sample help text',
		standard=False,
		custom=True,
		built_in=False,
		can_edit=True
	)
	
	call_result = await create_check.asyncio(
	    'sample_target/sample_category/sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import create_check
	from dqops.client.models import CheckDefinitionModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckDefinitionModel(
		check_name='sample_check',
		sensor_name='sample_target/sample_category/sample_sensor',
		rule_name='sample_target/sample_category/sample_rule',
		help_text='Sample help text',
		standard=False,
		custom=True,
		built_in=False,
		can_edit=True
	)
	
	call_result = create_check.sync(
	    'sample_target/sample_category/sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import create_check
	from dqops.client.models import CheckDefinitionModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckDefinitionModel(
		check_name='sample_check',
		sensor_name='sample_target/sample_category/sample_sensor',
		rule_name='sample_target/sample_category/sample_rule',
		help_text='Sample help text',
		standard=False,
		custom=True,
		built_in=False,
		can_edit=True
	)
	
	call_result = await create_check.asyncio(
	    'sample_target/sample_category/sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## delete_check
Deletes a custom check definition

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/delete_check.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/checks/{fullCheckName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`full_check_name`</span>|Full check name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X DELETE http://localhost:8888/api/checks/sample_target/sample_category/sample_check^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import delete_check
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = delete_check.sync(
	    'sample_target/sample_category/sample_check',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import delete_check
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await delete_check.asyncio(
	    'sample_target/sample_category/sample_check',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import delete_check
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = delete_check.sync(
	    'sample_target/sample_category/sample_check',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import delete_check
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await delete_check.asyncio(
	    'sample_target/sample_category/sample_check',
	    client=dqops_client
	)
	
    ```

    



___
## get_all_checks
Returns a flat list of all checks available in DQOps, both built-in checks and user defined or customized checks.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/get_all_checks.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/checks
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_definition_list_model`</span>||*List[[CheckDefinitionListModel](../models/checks.md#checkdefinitionlistmodel)]*|








**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/checks^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        [ {
		  "check_name" : "sample_check",
		  "full_check_name" : "sample_target/sample_category/sample_check",
		  "custom" : false,
		  "built_in" : false,
		  "can_edit" : true
		}, {
		  "check_name" : "sample_check",
		  "full_check_name" : "sample_target/sample_category/sample_check",
		  "custom" : false,
		  "built_in" : false,
		  "can_edit" : true
		}, {
		  "check_name" : "sample_check",
		  "full_check_name" : "sample_target/sample_category/sample_check",
		  "custom" : false,
		  "built_in" : false,
		  "can_edit" : true
		} ]
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import get_all_checks
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_checks.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckDefinitionListModel(
				check_name='sample_check',
				full_check_name='sample_target/sample_category/sample_check',
				custom=False,
				built_in=False,
				can_edit=True
			),
			CheckDefinitionListModel(
				check_name='sample_check',
				full_check_name='sample_target/sample_category/sample_check',
				custom=False,
				built_in=False,
				can_edit=True
			),
			CheckDefinitionListModel(
				check_name='sample_check',
				full_check_name='sample_target/sample_category/sample_check',
				custom=False,
				built_in=False,
				can_edit=True
			)
		]
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import get_all_checks
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_checks.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckDefinitionListModel(
				check_name='sample_check',
				full_check_name='sample_target/sample_category/sample_check',
				custom=False,
				built_in=False,
				can_edit=True
			),
			CheckDefinitionListModel(
				check_name='sample_check',
				full_check_name='sample_target/sample_category/sample_check',
				custom=False,
				built_in=False,
				can_edit=True
			),
			CheckDefinitionListModel(
				check_name='sample_check',
				full_check_name='sample_target/sample_category/sample_check',
				custom=False,
				built_in=False,
				can_edit=True
			)
		]
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import get_all_checks
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_checks.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckDefinitionListModel(
				check_name='sample_check',
				full_check_name='sample_target/sample_category/sample_check',
				custom=False,
				built_in=False,
				can_edit=True
			),
			CheckDefinitionListModel(
				check_name='sample_check',
				full_check_name='sample_target/sample_category/sample_check',
				custom=False,
				built_in=False,
				can_edit=True
			),
			CheckDefinitionListModel(
				check_name='sample_check',
				full_check_name='sample_target/sample_category/sample_check',
				custom=False,
				built_in=False,
				can_edit=True
			)
		]
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import get_all_checks
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_checks.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        [
			CheckDefinitionListModel(
				check_name='sample_check',
				full_check_name='sample_target/sample_category/sample_check',
				custom=False,
				built_in=False,
				can_edit=True
			),
			CheckDefinitionListModel(
				check_name='sample_check',
				full_check_name='sample_target/sample_category/sample_check',
				custom=False,
				built_in=False,
				can_edit=True
			),
			CheckDefinitionListModel(
				check_name='sample_check',
				full_check_name='sample_target/sample_category/sample_check',
				custom=False,
				built_in=False,
				can_edit=True
			)
		]
        ```
    
    
    



___
## get_check
Returns a check definition

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/get_check.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/checks/{fullCheckName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_definition_model`](../models/checks.md#checkdefinitionmodel)</span>||*[CheckDefinitionModel](../models/checks.md#checkdefinitionmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`full_check_name`</span>|Full check name|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/checks/sample_target/sample_category/sample_check^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        {
		  "check_name" : "sample_check",
		  "sensor_name" : "sample_target/sample_category/sample_sensor",
		  "rule_name" : "sample_target/sample_category/sample_rule",
		  "help_text" : "Sample help text",
		  "standard" : false,
		  "custom" : true,
		  "built_in" : false,
		  "can_edit" : true
		}
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import get_check
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_check.sync(
	    'sample_target/sample_category/sample_check',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckDefinitionModel(
			check_name='sample_check',
			sensor_name='sample_target/sample_category/sample_sensor',
			rule_name='sample_target/sample_category/sample_rule',
			help_text='Sample help text',
			standard=False,
			custom=True,
			built_in=False,
			can_edit=True
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import get_check
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_check.asyncio(
	    'sample_target/sample_category/sample_check',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckDefinitionModel(
			check_name='sample_check',
			sensor_name='sample_target/sample_category/sample_sensor',
			rule_name='sample_target/sample_category/sample_rule',
			help_text='Sample help text',
			standard=False,
			custom=True,
			built_in=False,
			can_edit=True
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import get_check
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_check.sync(
	    'sample_target/sample_category/sample_check',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckDefinitionModel(
			check_name='sample_check',
			sensor_name='sample_target/sample_category/sample_sensor',
			rule_name='sample_target/sample_category/sample_rule',
			help_text='Sample help text',
			standard=False,
			custom=True,
			built_in=False,
			can_edit=True
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import get_check
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_check.asyncio(
	    'sample_target/sample_category/sample_check',
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckDefinitionModel(
			check_name='sample_check',
			sensor_name='sample_target/sample_category/sample_sensor',
			rule_name='sample_target/sample_category/sample_rule',
			help_text='Sample help text',
			standard=False,
			custom=True,
			built_in=False,
			can_edit=True
		)
        ```
    
    
    



___
## get_check_folder_tree
Returns a tree of all checks available in DQOps, both built-in checks and user defined or customized checks.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/get_check_folder_tree.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/definitions/checks
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_definition_folder_model`](../models/checks.md#checkdefinitionfoldermodel)</span>||*[CheckDefinitionFolderModel](../models/checks.md#checkdefinitionfoldermodel)*|








**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl http://localhost:8888/api/definitions/checks^
		-H "Accept: application/json"
	
    ```

    
    ??? example "Expand to see the returned result"
    
    
        ```
        { }
        ```
    
    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import get_check_folder_tree
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_check_folder_tree.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckDefinitionFolderModel(
			folders={
			
			}
		)
        ```
    
    
    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import get_check_folder_tree
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_check_folder_tree.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckDefinitionFolderModel(
			folders={
			
			}
		)
        ```
    
    
    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import get_check_folder_tree
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_check_folder_tree.sync(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckDefinitionFolderModel(
			folders={
			
			}
		)
        ```
    
    
    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import get_check_folder_tree
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_check_folder_tree.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ??? example "Expand to see the returned result"
    
        ```
        CheckDefinitionFolderModel(
			folders={
			
			}
		)
        ```
    
    
    



___
## update_check
Updates an existing check, making a custom check definition if it is not present

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/update_check.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/checks/{fullCheckName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`full_check_name`</span>|Full check name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|List of check definitions|*[CheckDefinitionModel](../models/checks.md#checkdefinitionmodel)*| |




**Usage examples**


=== "curl"
    **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/checks/sample_target/sample_category/sample_check^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"check_name\":\"sample_check\",\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"rule_name\":\"sample_target/sample_category/sample_rule\",\"help_text\":\"Sample help text\",\"standard\":false,\"custom\":true,\"built_in\":false,\"can_edit\":true}"
	
    ```

    


=== "Python sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import update_check
	from dqops.client.models import CheckDefinitionModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckDefinitionModel(
		check_name='sample_check',
		sensor_name='sample_target/sample_category/sample_sensor',
		rule_name='sample_target/sample_category/sample_rule',
		help_text='Sample help text',
		standard=False,
		custom=True,
		built_in=False,
		can_edit=True
	)
	
	call_result = update_check.sync(
	    'sample_target/sample_category/sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import update_check
	from dqops.client.models import CheckDefinitionModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = CheckDefinitionModel(
		check_name='sample_check',
		sensor_name='sample_target/sample_category/sample_sensor',
		rule_name='sample_target/sample_category/sample_rule',
		help_text='Sample help text',
		standard=False,
		custom=True,
		built_in=False,
		can_edit=True
	)
	
	call_result = await update_check.asyncio(
	    'sample_target/sample_category/sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import update_check
	from dqops.client.models import CheckDefinitionModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckDefinitionModel(
		check_name='sample_check',
		sensor_name='sample_target/sample_category/sample_sensor',
		rule_name='sample_target/sample_category/sample_rule',
		help_text='Sample help text',
		standard=False,
		custom=True,
		built_in=False,
		can_edit=True
	)
	
	call_result = update_check.sync(
	    'sample_target/sample_category/sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    **Execution**

    ```python
    from dqops import client
	from dqops.client.api.checks import update_check
	from dqops.client.models import CheckDefinitionModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = CheckDefinitionModel(
		check_name='sample_check',
		sensor_name='sample_target/sample_category/sample_sensor',
		rule_name='sample_target/sample_category/sample_rule',
		help_text='Sample help text',
		standard=False,
		custom=True,
		built_in=False,
		can_edit=True
	)
	
	call_result = await update_check.asyncio(
	    'sample_target/sample_category/sample_check',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



