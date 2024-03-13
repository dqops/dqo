# DQOps REST API shared_credentials models reference
The references of all objects used by [shared_credentials](../operations/shared_credentials.md) REST API operations are listed below.


## CredentialType
Credential type - a text credential or a binary credential that must be updated as a base64 value.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|text<br/>binary<br/>|

___

## SharedCredentialListModel
Shared credentials list model with the basic information about the credential.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`credential_name`</span>|Credential name. It is the name of a file in the .credentials/ folder inside the DQOps user's home folder.|*string*|
|<span class="no-wrap-code">[`type`](#credentialtype)</span>|Credential type that is based on the detected format of the file. If the file can be parsed as a valid utf-8 string, it is assumed that the credential is a text. Otherwise, it is a binary file that can only be retrieved as a base64 value.|*[CredentialType](#credentialtype)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete the shared credential file.|*boolean*|
|<span class="no-wrap-code">`can_access_credential`</span>|Boolean flag that decides if the current user see or download the credential file.|*boolean*|


___

## SharedCredentialModel
Shared credentials full model used to create and update the credential. Contains one of two forms of the credential&#x27;s value: a text or a base64 binary value.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`credential_name`</span>|Credential name. It is the name of a file in the .credentials/ folder inside the DQOps user's home folder.|*string*|
|<span class="no-wrap-code">[`type`](./shared_credentials.md#credentialtype)</span>|Credential type that is based on the detected format of the file. If the file can be parsed as a valid utf-8 string, then it is assumed that the credential is a text. Otherwise, it is a binary file that can only be retrieved as a base64 value.|*[CredentialType](./shared_credentials.md#credentialtype)*|
|<span class="no-wrap-code">`text_value`</span>|Credential's value as a text. Only one value (the text_value or binary_value) should be not empty.|*string*|
|<span class="no-wrap-code">`binary_value`</span>|Credential's value for a binary credential that is stored as a base64 value. Only one value (the text_value or binary_value) should be not empty.|*string*|


___

