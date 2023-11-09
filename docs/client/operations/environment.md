DQOps environment and configuration controller, provides access to the DQOps configuration, current user&#x27;s information and issue local API Keys for the calling user.  


___  
## get_dqo_settings  
Returns all effective DQOps configuration settings.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/environment/get_dqo_settings.py)
  

**GET**
```
http://localhost:8888/api/environment/settings  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_settings_model](../../models/environment/#dqosettingsmodel)||[DqoSettingsModel](../../models/environment/#dqosettingsmodel)|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/environment/settings^
		-H "Accept: application/json"

    ```


___  
## get_user_profile  
Returns the profile of the current user.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/environment/get_user_profile.py)
  

**GET**
```
http://localhost:8888/api/environment/profile  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_user_profile_model](../../models/environment/#dqouserprofilemodel)||[DqoUserProfileModel](../../models/environment/#dqouserprofilemodel)|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/environment/profile^
		-H "Accept: application/json"

    ```


___  
## issue_api_key  
Issues a local API Key for the calling user. This API Key could be used to authenticate using the DQOps REST API client. This API Key should be passed in the &quot;Authorization&quot; HTTP header in the format &quot;Authorization: Bearer &lt;api_key&gt;&quot;.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/environment/issue_api_key.py)
  

**GET**
```
http://localhost:8888/api/environment/issueapikey  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|string||string|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/environment/issueapikey^
		-H "Accept: application/json"

    ```


