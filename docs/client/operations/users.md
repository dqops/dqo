# DQOps REST API users operations
Operations for managing access for DQOps users in a multi-user installations. User management is supported in the TEAM and ENTERPRISE licences.


___
## change_caller_password
Changes the password of the calling user. When the user is identified by the DQOps local API key, it is the user whose email is stored in the DQOps API Key.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/change_caller_password.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/mypassword
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|New Password|*string*| |




**Usage examples**


=== "curl"
    ### **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/mypassword^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"\"sample_string_value\""
	
    ```

    


=== "Python sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import change_caller_password
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = 'sample_string_value'
	
	call_result = change_caller_password.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import change_caller_password
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = 'sample_string_value'
	
	call_result = await change_caller_password.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import change_caller_password
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = 'sample_string_value'
	
	call_result = change_caller_password.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import change_caller_password
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = 'sample_string_value'
	
	call_result = await change_caller_password.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## change_user_password
Changes the password of a user identified by the email.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/change_user_password.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/users/{email}/password
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`email`</span>|User&#x27;s email|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|New Password|*string*| |




**Usage examples**


=== "curl"
    ### **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/users/sample_user@mail.com/password^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"\"sample_string_value\""
	
    ```

    


=== "Python sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import change_user_password
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = 'sample_string_value'
	
	call_result = change_user_password.sync(
	    'sample_user@mail.com',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import change_user_password
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = 'sample_string_value'
	
	call_result = await change_user_password.asyncio(
	    'sample_user@mail.com',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import change_user_password
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = 'sample_string_value'
	
	call_result = change_user_password.sync(
	    'sample_user@mail.com',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import change_user_password
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = 'sample_string_value'
	
	call_result = await change_user_password.asyncio(
	    'sample_user@mail.com',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## create_user
Creates (adds) a new user to a multi-user account.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/create_user.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/users
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|User model|*[DqoCloudUserModel](../models/users.md#dqocloudusermodel)*| |




**Usage examples**


=== "curl"
    ### **Execution**

    ```bash
    curl -X POST http://localhost:8888/api/users^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"email\":\"sample_user@mail.com\",\"accountRole\":\"operator\"}"
	
    ```

    


=== "Python sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import create_user
	from dqops.client.models import DqoCloudUserModel, \
	                                DqoUserRole
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DqoCloudUserModel(
		email='sample_user@mail.com',
		account_role=DqoUserRole.OPERATOR
	)
	
	call_result = create_user.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import create_user
	from dqops.client.models import DqoCloudUserModel, \
	                                DqoUserRole
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DqoCloudUserModel(
		email='sample_user@mail.com',
		account_role=DqoUserRole.OPERATOR
	)
	
	call_result = await create_user.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import create_user
	from dqops.client.models import DqoCloudUserModel, \
	                                DqoUserRole
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DqoCloudUserModel(
		email='sample_user@mail.com',
		account_role=DqoUserRole.OPERATOR
	)
	
	call_result = create_user.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import create_user
	from dqops.client.models import DqoCloudUserModel, \
	                                DqoUserRole
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DqoCloudUserModel(
		email='sample_user@mail.com',
		account_role=DqoUserRole.OPERATOR
	)
	
	call_result = await create_user.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



___
## delete_user
Deletes a user from a multi-user account.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/delete_user.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/users/{email}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`email`</span>|User&#x27;s email|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    ### **Execution**

    ```bash
    curl -X DELETE http://localhost:8888/api/users/sample_user@mail.com^
		-H "Accept: application/json"
	
    ```

    


=== "Python sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import delete_user
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = delete_user.sync(
	    'sample_user@mail.com',
	    client=dqops_client
	)
	
    ```

    


=== "Python async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import delete_user
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await delete_user.asyncio(
	    'sample_user@mail.com',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import delete_user
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = delete_user.sync(
	    'sample_user@mail.com',
	    client=dqops_client
	)
	
    ```

    


=== "Python auth async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import delete_user
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await delete_user.asyncio(
	    'sample_user@mail.com',
	    client=dqops_client
	)
	
    ```

    



___
## get_all_users
Returns a list of all users.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/get_all_users.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/users
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`dqo_cloud_user_model`</span>||*List[[DqoCloudUserModel](../models/users.md#dqocloudusermodel)]*|








**Usage examples**


=== "curl"
    ### **Execution**

    ```bash
    curl http://localhost:8888/api/users^
		-H "Accept: application/json"
	
    ```

    
    ### **Return value sample**
    
    
    ```js
    [ {
	  "email" : "sample_user@mail.com",
	  "accountRole" : "operator"
	}, {
	  "email" : "sample_user@mail.com",
	  "accountRole" : "operator"
	}, {
	  "email" : "sample_user@mail.com",
	  "accountRole" : "operator"
	} ]
    ```
    
    


=== "Python sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import get_all_users
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_users.sync(
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    [
		DqoCloudUserModel(
			email='sample_user@mail.com',
			account_role=DqoUserRole.OPERATOR
		),
		DqoCloudUserModel(
			email='sample_user@mail.com',
			account_role=DqoUserRole.OPERATOR
		),
		DqoCloudUserModel(
			email='sample_user@mail.com',
			account_role=DqoUserRole.OPERATOR
		)
	]
    ```
    
    
    


=== "Python async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import get_all_users
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_users.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    [
		DqoCloudUserModel(
			email='sample_user@mail.com',
			account_role=DqoUserRole.OPERATOR
		),
		DqoCloudUserModel(
			email='sample_user@mail.com',
			account_role=DqoUserRole.OPERATOR
		),
		DqoCloudUserModel(
			email='sample_user@mail.com',
			account_role=DqoUserRole.OPERATOR
		)
	]
    ```
    
    
    


=== "Python auth sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import get_all_users
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_users.sync(
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    [
		DqoCloudUserModel(
			email='sample_user@mail.com',
			account_role=DqoUserRole.OPERATOR
		),
		DqoCloudUserModel(
			email='sample_user@mail.com',
			account_role=DqoUserRole.OPERATOR
		),
		DqoCloudUserModel(
			email='sample_user@mail.com',
			account_role=DqoUserRole.OPERATOR
		)
	]
    ```
    
    
    


=== "Python auth async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import get_all_users
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_users.asyncio(
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    [
		DqoCloudUserModel(
			email='sample_user@mail.com',
			account_role=DqoUserRole.OPERATOR
		),
		DqoCloudUserModel(
			email='sample_user@mail.com',
			account_role=DqoUserRole.OPERATOR
		),
		DqoCloudUserModel(
			email='sample_user@mail.com',
			account_role=DqoUserRole.OPERATOR
		)
	]
    ```
    
    
    



___
## get_user
Returns the user model that describes the role of a user identified by an email

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/get_user.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/users/{email}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`dqo_cloud_user_model`](../models/users.md#dqocloudusermodel)</span>||*[DqoCloudUserModel](../models/users.md#dqocloudusermodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`email`</span>|User&#x27;s email|*string*|:material-check-bold:|






**Usage examples**


=== "curl"
    ### **Execution**

    ```bash
    curl http://localhost:8888/api/users/sample_user@mail.com^
		-H "Accept: application/json"
	
    ```

    
    ### **Return value sample**
    
    
    ```js
    {
	  "email" : "sample_user@mail.com",
	  "accountRole" : "operator"
	}
    ```
    
    


=== "Python sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import get_user
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_user.sync(
	    'sample_user@mail.com',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    DqoCloudUserModel(
		email='sample_user@mail.com',
		account_role=DqoUserRole.OPERATOR
	)
    ```
    
    
    


=== "Python async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import get_user
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_user.asyncio(
	    'sample_user@mail.com',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    DqoCloudUserModel(
		email='sample_user@mail.com',
		account_role=DqoUserRole.OPERATOR
	)
    ```
    
    
    


=== "Python auth sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import get_user
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_user.sync(
	    'sample_user@mail.com',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    DqoCloudUserModel(
		email='sample_user@mail.com',
		account_role=DqoUserRole.OPERATOR
	)
    ```
    
    
    


=== "Python auth async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import get_user
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_user.asyncio(
	    'sample_user@mail.com',
	    client=dqops_client
	)
	
    ```

    
    ### **Return value sample**
    
    ```python
    DqoCloudUserModel(
		email='sample_user@mail.com',
		account_role=DqoUserRole.OPERATOR
	)
    ```
    
    
    



___
## update_user
Updates a user in a multi-user account. The user&#x27;s email cannot be changed.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/update_user.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/users/{email}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`email`</span>|User&#x27;s email|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|User model|*[DqoCloudUserModel](../models/users.md#dqocloudusermodel)*| |




**Usage examples**


=== "curl"
    ### **Execution**

    ```bash
    curl -X PUT http://localhost:8888/api/users/sample_user@mail.com^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"email\":\"sample_user@mail.com\",\"accountRole\":\"operator\"}"
	
    ```

    


=== "Python sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import update_user
	from dqops.client.models import DqoCloudUserModel, \
	                                DqoUserRole
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DqoCloudUserModel(
		email='sample_user@mail.com',
		account_role=DqoUserRole.OPERATOR
	)
	
	call_result = update_user.sync(
	    'sample_user@mail.com',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import update_user
	from dqops.client.models import DqoCloudUserModel, \
	                                DqoUserRole
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = DqoCloudUserModel(
		email='sample_user@mail.com',
		account_role=DqoUserRole.OPERATOR
	)
	
	call_result = await update_user.asyncio(
	    'sample_user@mail.com',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth sync client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import update_user
	from dqops.client.models import DqoCloudUserModel, \
	                                DqoUserRole
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DqoCloudUserModel(
		email='sample_user@mail.com',
		account_role=DqoUserRole.OPERATOR
	)
	
	call_result = update_user.sync(
	    'sample_user@mail.com',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    


=== "Python auth async client"
    ### **Execution**

    ```python
    from dqops import client
	from dqops.client.api.users import update_user
	from dqops.client.models import DqoCloudUserModel, \
	                                DqoUserRole
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = DqoCloudUserModel(
		email='sample_user@mail.com',
		account_role=DqoUserRole.OPERATOR
	)
	
	call_result = await update_user.asyncio(
	    'sample_user@mail.com',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

    



