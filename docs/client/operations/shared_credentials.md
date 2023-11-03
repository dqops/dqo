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
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Shared credential model|[SharedCredentialModel](\docs\client\models\shared_credentials\#sharedcredentialmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/credentials
		-H "Accept: application/json"
		-H "Content-Type: application/json"
		-d '{
		  "credential_name" : "sample_credential",
		  "type" : "text",
		  "text_value" : "sample_credential_text_value"
		}'
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
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
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|credential_name|Full shared credential name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl -X DELETE http://localhost:8888/api/credentials/sample_credential
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
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
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|credential_name|Shared credential file name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/credentials/sample_credential/download
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
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
|shared_credential_list_model||List[[SharedCredentialListModel](\docs\client\models\shared_credentials\#sharedcredentiallistmodel)]|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/credentials
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
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
|[shared_credential_model](\docs\client\models\shared_credentials\#sharedcredentialmodel)||[SharedCredentialModel](\docs\client\models\shared_credentials\#sharedcredentialmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|credential_name|Shared credential file name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/credentials/sample_credential
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
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
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|credential_name|Credential file name that will be updated|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Shared credential model|[SharedCredentialModel](\docs\client\models\shared_credentials\#sharedcredentialmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/credential/sample_credential
		-H "Accept: application/json"
		-H "Content-Type: application/json"
		-d '{
		  "credential_name" : "sample_credential",
		  "type" : "text",
		  "text_value" : "sample_credential_text_value"
		}'
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


