# DQOps REST API shared_credentials operations
Operations for managing shared credentials in DQOps. Credentials that are stored in the shared .credentials folder in the DQOps user&#x27;s home folder.


___
## create_shared_credential
Creates (adds) a new shared credential, which creates a file in the DQOps user&#x27;s home .credentials/ folder named as the credential and with the content that is provided in this call.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/create_shared_credential.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/credentials
```





**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Shared credential model|*[SharedCredentialModel](../models/shared_credentials.md#sharedcredentialmodel)*| |




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
		type=CredentialType.TEXT,
		text_value='sample_credential_text_value'
	)
	
	call_result = create_shared_credential.sync(
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
		type=CredentialType.TEXT,
		text_value='sample_credential_text_value'
	)
	
	call_result = await create_shared_credential.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
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
		type=CredentialType.TEXT,
		text_value='sample_credential_text_value'
	)
	
	call_result = create_shared_credential.sync(
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
		type=CredentialType.TEXT,
		text_value='sample_credential_text_value'
	)
	
	call_result = await create_shared_credential.asyncio(
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```





___
## delete_shared_credential
Deletes a shared credential file from the DQOps user&#x27;s home .credentials/ folder.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/delete_shared_credential.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/credentials/{credentialName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`credential_name`</span>|Full shared credential name|*string*|:material-check-bold:|






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
	
	call_result = delete_shared_credential.sync(
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
	
	call_result = await delete_shared_credential.asyncio(
	    'sample_credential',
	    client=dqops_client
	)
	
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
	
	call_result = delete_shared_credential.sync(
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
	
	call_result = await delete_shared_credential.asyncio(
	    'sample_credential',
	    client=dqops_client
	)
	
    ```





___
## download_shared_credential
Downloads a shared credential&#x27;s file

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/download_shared_credential.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/credentials/{credentialName}/download
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`credential_name`</span>|Shared credential file name|*string*|:material-check-bold:|






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
	
	call_result = download_shared_credential.sync(
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
	
	call_result = await download_shared_credential.asyncio(
	    'sample_credential',
	    client=dqops_client
	)
	
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
	
	call_result = download_shared_credential.sync(
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
	
	call_result = await download_shared_credential.asyncio(
	    'sample_credential',
	    client=dqops_client
	)
	
    ```





___
## get_all_shared_credentials
Returns a list of all shared credentials that are present in the DQOps user&#x27;s home .credentials/ folder.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/get_all_shared_credentials.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/credentials
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`shared_credential_list_model`</span>||*List[[SharedCredentialListModel](../models/shared_credentials.md#sharedcredentiallistmodel)]*|








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
	
	call_result = get_all_shared_credentials.sync(
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
	
	call_result = await get_all_shared_credentials.asyncio(
	    client=dqops_client
	)
	
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
	
	call_result = get_all_shared_credentials.sync(
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
	
	call_result = await get_all_shared_credentials.asyncio(
	    client=dqops_client
	)
	
    ```





___
## get_shared_credential
Returns a shared credential content

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/get_shared_credential.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/credentials/{credentialName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`shared_credential_model`](../models/shared_credentials.md#sharedcredentialmodel)</span>||*[SharedCredentialModel](../models/shared_credentials.md#sharedcredentialmodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`credential_name`</span>|Shared credential file name|*string*|:material-check-bold:|






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
	
	call_result = get_shared_credential.sync(
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
	
	call_result = await get_shared_credential.asyncio(
	    'sample_credential',
	    client=dqops_client
	)
	
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
	
	call_result = get_shared_credential.sync(
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
	
	call_result = await get_shared_credential.asyncio(
	    'sample_credential',
	    client=dqops_client
	)
	
    ```





___
## update_shared_credential
Updates an existing shared credential, replacing the credential&#x27;s file content.

Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/update_shared_credential.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/credential/{credentialName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`credential_name`</span>|Credential file name that will be updated|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Shared credential model|*[SharedCredentialModel](../models/shared_credentials.md#sharedcredentialmodel)*| |




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
		type=CredentialType.TEXT,
		text_value='sample_credential_text_value'
	)
	
	call_result = update_shared_credential.sync(
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
		type=CredentialType.TEXT,
		text_value='sample_credential_text_value'
	)
	
	call_result = await update_shared_credential.asyncio(
	    'sample_credential',
	    client=dqops_client,
	    json_body=request_body
	)
	
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
		type=CredentialType.TEXT,
		text_value='sample_credential_text_value'
	)
	
	call_result = update_shared_credential.sync(
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
		type=CredentialType.TEXT,
		text_value='sample_credential_text_value'
	)
	
	call_result = await update_shared_credential.asyncio(
	    'sample_credential',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```





