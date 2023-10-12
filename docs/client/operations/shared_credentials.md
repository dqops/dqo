
## create_shared_credential  
Creates (adds) a new shared credential, which creates a file in the DQOps user&#x27;s home .credentials/ folder named as the credential and with the content that is provided in this call.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/create_shared_credential.py)
  

**POST**
```
api/credentials  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Shared credential model|[SharedCredentialModel](\docs\client\operations\shared_credentials\#sharedcredentialmodel)|false|


___  

## delete_shared_credential  
Deletes a shared credential file from the DQOps user&#x27;s home .credentials/ folder.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/delete_shared_credential.py)
  

**DELETE**
```
api/credentials/{credentialName}  
```





___  

## download_shared_credential  
Downloads a shared credential&#x27;s file  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/download_shared_credential.py)
  

**GET**
```
api/credentials/{credentialName}/download  
```





___  

## get_all_shared_credentials  
Returns a list of all shared credentials that are present in the DQOps user&#x27;s home .credentials/ folder..  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/get_all_shared_credentials.py)
  

**GET**
```
api/credentials  
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
api/credentials/{credentialName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[shared_credential_model](\docs\client\operations\shared_credentials\#sharedcredentialmodel)||[SharedCredentialModel](\docs\client\operations\shared_credentials\#sharedcredentialmodel)|






___  

## update_shared_credential  
Updates an existing shared credential, replacing the credential&#x27;s file content.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/shared_credentials/update_shared_credential.py)
  

**PUT**
```
api/credential/{credentialName}  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Shared credential model|[SharedCredentialModel](\docs\client\operations\shared_credentials\#sharedcredentialmodel)|false|


___  

___  

## SharedCredentialModel  
Shared credentials full model used to create and update the credential. Contains one of two forms of the credential&#x27;s value: a text or a base64 binary value.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|credential_name|Credential name. It is the name of a file in the .credentials/ folder inside the DQOps user&#x27;s home folder.|string| | | |
|type|Credential type that is based on the detected format of the file. If the file could be parsed as a valid utf-8 string then it is assumed that the credential is a text. Otherwise it is a binary file that could be retrieved only as a base64 value.|enum|binary<br/>text<br/>| | |
|text_value|Credential&#x27;s value as a text. Only one value (the text_value or binary_value) should be not empty.|string| | | |
|binary_value|Credential&#x27;s value for a binary credential that is stored as a base64 value. Only one value (the text_value or binary_value) should be not empty.|string| | | |

___  

