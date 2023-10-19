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
|---------------------------------|-----------|----------|
|Shared credential model|[SharedCredentialModel](\docs\client\models\shared_credentials\#sharedcredentialmodel)|false|



___  
## delete_shared_credential  
Deletes a shared credential file from the DQOps user&#x27;s home .credentials/ folder.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/delete_shared_credential.py)
  

**DELETE**
```
http://localhost:8888/api/credentials/{credentialName}  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|credential_name|Full shared credential name|string|true|





___  
## download_shared_credential  
Downloads a shared credential&#x27;s file  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/download_shared_credential.py)
  

**GET**
```
http://localhost:8888/api/credentials/{credentialName}/download  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|credential_name|Shared credential file name|string|true|





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
|[shared_credential_list_model]()||[SharedCredentialListModel]()|







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




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|credential_name|Shared credential file name|string|true|





___  
## update_shared_credential  
Updates an existing shared credential, replacing the credential&#x27;s file content.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/update_shared_credential.py)
  

**PUT**
```
http://localhost:8888/api/credential/{credentialName}  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|credential_name|Credential file name that will be updated|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Shared credential model|[SharedCredentialModel](\docs\client\models\shared_credentials\#sharedcredentialmodel)|false|



