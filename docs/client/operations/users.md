
## change_caller_password  
Changes the password of the calling user. When the user is identified by the DQOps local API key, it is the user whose email is stored in the DQOps API Key.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/change_caller_password.py)
  

**PUT**
```
http://localhost:8888/api/mypassword  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|New Password|string|false|


___  

## change_user_password  
Changes the password of a user identified by the email.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/change_user_password.py)
  

**PUT**
```
http://localhost:8888/api/users/{email}/password  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|email|User&#x27;s email|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|New Password|string|false|


___  

## create_user  
Creates (adds) a new user to a multi-user account.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/create_user.py)
  

**POST**
```
http://localhost:8888/api/users  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|User model|[DqoCloudUserModel](\docs\client\models\users\#dqocloudusermodel)|false|


___  

## delete_user  
Deletes a user from a multi-user account.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/delete_user.py)
  

**DELETE**
```
http://localhost:8888/api/users/{email}  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|email|User&#x27;s email|string|true|




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
|[dqo_cloud_user_model](\docs\client\models\users\#dqocloudusermodel)||[DqoCloudUserModel](\docs\client\models\users\#dqocloudusermodel)|






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




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|email|User&#x27;s email|string|true|




___  

## update_user  
Updates a user in a multi-user account. The user&#x27;s email cannot be changed.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/users/update_user.py)
  

**PUT**
```
http://localhost:8888/api/users/{email}  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|email|User&#x27;s email|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|User model|[DqoCloudUserModel](\docs\client\models\users\#dqocloudusermodel)|false|


___  

