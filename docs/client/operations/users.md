User management service  


___  
## change_caller_password  
Changes the password of the calling user. When the user is identified by the DQOps local API key, it is the user whose email is stored in the DQOps API Key.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/change_caller_password.py)
  

**PUT**
```
http://localhost:8888/api/mypassword  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|New Password|string|false|




**Usage examples**  
=== "curl"
      
    ```
    curl -X PUT http://localhost:8888/api/mypassword
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
## change_user_password  
Changes the password of a user identified by the email.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/change_user_password.py)
  

**PUT**
```
http://localhost:8888/api/users/{email}/password  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|email|User&#x27;s email|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|New Password|string|false|




**Usage examples**  
=== "curl"
      
    ```
    curl -X PUT http://localhost:8888/api/users/sample_user@mail.com/password
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
## create_user  
Creates (adds) a new user to a multi-user account.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/create_user.py)
  

**POST**
```
http://localhost:8888/api/users  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|User model|[DqoCloudUserModel](\docs\client\models\users\#dqocloudusermodel)|false|




**Usage examples**  
=== "curl"
      
    ```
    curl -X POST http://localhost:8888/api/users
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
## delete_user  
Deletes a user from a multi-user account.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/delete_user.py)
  

**DELETE**
```
http://localhost:8888/api/users/{email}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|email|User&#x27;s email|string|true|






**Usage examples**  
=== "curl"
      
    ```
    curl -X DELETE http://localhost:8888/api/users/sample_user@mail.com
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
## get_all_users  
Returns a list of all users.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/get_all_users.py)
  

**GET**
```
http://localhost:8888/api/users  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|dqo_cloud_user_model||List[[DqoCloudUserModel](\docs\client\models\users\#dqocloudusermodel)]|








**Usage examples**  
=== "curl"
      
    ```
    curl http://localhost:8888/api/users
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
## get_user  
Returns the user model that describes the role of a user identified by an email  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/get_user.py)
  

**GET**
```
http://localhost:8888/api/users/{email}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_cloud_user_model](\docs\client\models\users\#dqocloudusermodel)||[DqoCloudUserModel](\docs\client\models\users\#dqocloudusermodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|email|User&#x27;s email|string|true|






**Usage examples**  
=== "curl"
      
    ```
    curl http://localhost:8888/api/users/sample_user@mail.com
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
## update_user  
Updates a user in a multi-user account. The user&#x27;s email cannot be changed.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/update_user.py)
  

**PUT**
```
http://localhost:8888/api/users/{email}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|email|User&#x27;s email|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|User model|[DqoCloudUserModel](\docs\client\models\users\#dqocloudusermodel)|false|




**Usage examples**  
=== "curl"
      
    ```
    curl -X PUT http://localhost:8888/api/users/sample_user@mail.com
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


