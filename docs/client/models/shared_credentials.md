
## CredentialType  
Credential type - a text credential or a binary credential that must be updated as a base64 value.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|binary<br/>text<br/>|

___  

## SharedCredentialListModel  
Shared credentials list model with the basic information about the credential.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|credential_name|Credential name. It is the name of a file in the .credentials/ folder inside the DQOps user&#x27;s home folder.|string|
|[type](#credentialtype)|Credential type that is based on the detected format of the file. If the file could be parsed as a valid utf-8 string then it is assumed that the credential is a text. Otherwise it is a binary file that could be retrieved only as a base64 value.|[CredentialType](#credentialtype)|
|can_edit|Boolean flag that decides if the current user can update or delete the shared credential file.|boolean|
|can_access_credential|Boolean flag that decides if the current user see or download the credential file.|boolean|


___  

## SharedCredentialModel  
Shared credentials full model used to create and update the credential. Contains one of two forms of the credential&#x27;s value: a text or a base64 binary value.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|credential_name|Credential name. It is the name of a file in the .credentials/ folder inside the DQOps user&#x27;s home folder.|string|
|[type](\docs\client\models\shared_credentials\#credentialtype)|Credential type that is based on the detected format of the file. If the file could be parsed as a valid utf-8 string then it is assumed that the credential is a text. Otherwise it is a binary file that could be retrieved only as a base64 value.|[CredentialType](\docs\client\models\shared_credentials\#credentialtype)|
|text_value|Credential&#x27;s value as a text. Only one value (the text_value or binary_value) should be not empty.|string|
|binary_value|Credential&#x27;s value for a binary credential that is stored as a base64 value. Only one value (the text_value or binary_value) should be not empty.|string|


___  
