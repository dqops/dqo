Shared credentials management for managing credentials that are stored in the shared .credentials folder in the DQOps user&#x27;s home folder.


___
## create_shared_credential
Creates (adds) a new shared credential, which creates a file in the DQOps user&#x27;s home .credentials/ folder named as the credential and with the content that is provided in this call.
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/create_shared_credential.py)


**POST**
```
http://localhost:8888/api/credentials
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Shared credential model|[SharedCredentialModel](../models/shared_credentials.md#sharedcredentialmodel)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X POST http://localhost:8888/api/credentials^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"credential_name\":\"sample_credential\",\"type\":\"text\",\"text_value\":\"sample_credential_text_value\"}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import create_shared_credential
	from dqops.client.models import CredentialType, \
	                                SharedCredentialModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = SharedCredentialModel(
		credential_name='sample_credential',
		type=CredentialType.text,
		text_value='sample_credential_text_value'
	)
	
	create_shared_credential.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import create_shared_credential
	from dqops.client.models import CredentialType, \
	                                SharedCredentialModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = SharedCredentialModel(
		credential_name='sample_credential',
		type=CredentialType.text,
		text_value='sample_credential_text_value'
	)
	
	async_result = create_shared_credential.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import create_shared_credential
	from dqops.client.models import CredentialType, \
	                                SharedCredentialModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = SharedCredentialModel(
		credential_name='sample_credential',
		type=CredentialType.text,
		text_value='sample_credential_text_value'
	)
	
	create_shared_credential.sync(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import create_shared_credential
	from dqops.client.models import CredentialType, \
	                                SharedCredentialModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = SharedCredentialModel(
		credential_name='sample_credential',
		type=CredentialType.text,
		text_value='sample_credential_text_value'
	)
	
	async_result = create_shared_credential.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





___
## delete_shared_credential
Deletes a shared credential file from the DQOps user&#x27;s home .credentials/ folder.
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/delete_shared_credential.py)


**DELETE**
```
http://localhost:8888/api/credentials/{credentialName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|credential_name|Full shared credential name|string|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl -X DELETE http://localhost:8888/api/credentials/sample_credential^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import delete_shared_credential
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	delete_shared_credential.sync(
	    'sample_credential',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import delete_shared_credential
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	async_result = delete_shared_credential.asyncio(
	    'sample_credential',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import delete_shared_credential
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	delete_shared_credential.sync(
	    'sample_credential',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import delete_shared_credential
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	async_result = delete_shared_credential.asyncio(
	    'sample_credential',
	    client=dqops_client
	)
	
	await async_result
	
    ```





___
## download_shared_credential
Downloads a shared credential&#x27;s file
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/download_shared_credential.py)


**GET**
```
http://localhost:8888/api/credentials/{credentialName}/download
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|credential_name|Shared credential file name|string|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/credentials/sample_credential/download^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import download_shared_credential
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	download_shared_credential.sync(
	    'sample_credential',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import download_shared_credential
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = download_shared_credential.asyncio(
	    'sample_credential',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import download_shared_credential
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	download_shared_credential.sync(
	    'sample_credential',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import download_shared_credential
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = download_shared_credential.asyncio(
	    'sample_credential',
	    client=dqops_client
	)
	
	await async_result
	
    ```





___
## get_all_shared_credentials
Returns a list of all shared credentials that are present in the DQOps user&#x27;s home .credentials/ folder..
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/get_all_shared_credentials.py)


**GET**
```
http://localhost:8888/api/credentials
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|shared_credential_list_model||List[[SharedCredentialListModel](../models/shared_credentials.md#sharedcredentiallistmodel)]|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/credentials^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import get_all_shared_credentials
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_all_shared_credentials.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import get_all_shared_credentials
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_all_shared_credentials.asyncio(
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import get_all_shared_credentials
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_all_shared_credentials.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import get_all_shared_credentials
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_all_shared_credentials.asyncio(
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
    [ {
	  "can_edit" : false,
	  "can_access_credential" : false
	}, {
	  "can_edit" : false,
	  "can_access_credential" : false
	}, {
	  "can_edit" : false,
	  "can_access_credential" : false
	} ]
    ```


___
## get_shared_credential
Returns a shared credential content
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/get_shared_credential.py)


**GET**
```
http://localhost:8888/api/credentials/{credentialName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[shared_credential_model](../models/shared_credentials.md#sharedcredentialmodel)||[SharedCredentialModel](../models/shared_credentials.md#sharedcredentialmodel)|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|credential_name|Shared credential file name|string|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/credentials/sample_credential^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import get_shared_credential
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	get_shared_credential.sync(
	    'sample_credential',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import get_shared_credential
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	async_result = get_shared_credential.asyncio(
	    'sample_credential',
	    client=dqops_client
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import get_shared_credential
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	get_shared_credential.sync(
	    'sample_credential',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import get_shared_credential
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	async_result = get_shared_credential.asyncio(
	    'sample_credential',
	    client=dqops_client
	)
	
	await async_result
	
    ```




??? "Return value sample"
    ```js
    {
	  "credential_name" : "sample_credential",
	  "type" : "text",
	  "text_value" : "sample_credential_text_value"
	}
    ```


___
## update_shared_credential
Updates an existing shared credential, replacing the credential&#x27;s file content.
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/update_shared_credential.py)


**PUT**
```
http://localhost:8888/api/credential/{credentialName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|credential_name|Credential file name that will be updated|string|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Shared credential model|[SharedCredentialModel](../models/shared_credentials.md#sharedcredentialmodel)| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/credential/sample_credential^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"credential_name\":\"sample_credential\",\"type\":\"text\",\"text_value\":\"sample_credential_text_value\"}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import update_shared_credential
	from dqops.client.models import CredentialType, \
	                                SharedCredentialModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = SharedCredentialModel(
		credential_name='sample_credential',
		type=CredentialType.text,
		text_value='sample_credential_text_value'
	)
	
	update_shared_credential.sync(
	    'sample_credential',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import update_shared_credential
	from dqops.client.models import CredentialType, \
	                                SharedCredentialModel
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = SharedCredentialModel(
		credential_name='sample_credential',
		type=CredentialType.text,
		text_value='sample_credential_text_value'
	)
	
	async_result = update_shared_credential.asyncio(
	    'sample_credential',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import update_shared_credential
	from dqops.client.models import CredentialType, \
	                                SharedCredentialModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = SharedCredentialModel(
		credential_name='sample_credential',
		type=CredentialType.text,
		text_value='sample_credential_text_value'
	)
	
	update_shared_credential.sync(
	    'sample_credential',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.shared_credentials import update_shared_credential
	from dqops.client.models import CredentialType, \
	                                SharedCredentialModel
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = SharedCredentialModel(
		credential_name='sample_credential',
		type=CredentialType.text,
		text_value='sample_credential_text_value'
	)
	
	async_result = update_shared_credential.asyncio(
	    'sample_credential',
	    client=dqops_client,
	    json_body=request_body
	)
	
	await async_result
	
    ```





