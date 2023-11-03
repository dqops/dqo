Data quality check definition management  


___  
## create_check  
Creates (adds) a new custom check that is a pair of a sensor name and a rule name.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/create_check.py)
  

**POST**
```
http://localhost:8888/api/checks/{fullCheckName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_check_name|Full check name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Check model|[CheckDefinitionModel](\docs\client\models\checks\#checkdefinitionmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/checks/sample_target/sample_category/sample_check
		-H "Accept: application/json"
		-H "Content-Type: application/json"
		-d '{
		  "check_name" : "sample_check",
		  "sensor_name" : "sample_target/sample_category/sample_sensor",
		  "rule_name" : "sample_target/sample_category/sample_rule",
		  "help_text" : "Sample help text",
		  "custom" : false,
		  "built_in" : false,
		  "can_edit" : true
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
## delete_check  
Deletes a custom check definition  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/delete_check.py)
  

**DELETE**
```
http://localhost:8888/api/checks/{fullCheckName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_check_name|Full check name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl -X DELETE http://localhost:8888/api/checks/sample_target/sample_category/sample_check
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
## get_all_checks  
Returns a flat list of all checks available in DQOps, both built-in checks and user defined or customized checks.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/get_all_checks.py)
  

**GET**
```
http://localhost:8888/api/checks  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_definition_list_model||List[[CheckDefinitionListModel](\docs\client\models\checks\#checkdefinitionlistmodel)]|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/checks
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
## get_check  
Returns a check definition  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/get_check.py)
  

**GET**
```
http://localhost:8888/api/checks/{fullCheckName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_definition_model](\docs\client\models\checks\#checkdefinitionmodel)||[CheckDefinitionModel](\docs\client\models\checks\#checkdefinitionmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_check_name|Full check name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/checks/sample_target/sample_category/sample_check
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
## get_check_folder_tree  
Returns a tree of all checks available in DQOps, both built-in checks and user defined or customized checks.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/get_check_folder_tree.py)
  

**GET**
```
http://localhost:8888/api/definitions/checks  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_definition_folder_model](\docs\client\models\checks\#checkdefinitionfoldermodel)||[CheckDefinitionFolderModel](\docs\client\models\checks\#checkdefinitionfoldermodel)|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/definitions/checks
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
## update_check  
Updates an existing check, making a custom check definition if it is not present  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/update_check.py)
  

**PUT**
```
http://localhost:8888/api/checks/{fullCheckName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_check_name|Full check name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|List of check definitions|[CheckDefinitionModel](\docs\client\models\checks\#checkdefinitionmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/checks/sample_target/sample_category/sample_check
		-H "Accept: application/json"
		-H "Content-Type: application/json"
		-d '{
		  "check_name" : "sample_check",
		  "sensor_name" : "sample_target/sample_category/sample_sensor",
		  "rule_name" : "sample_target/sample_category/sample_rule",
		  "help_text" : "Sample help text",
		  "custom" : false,
		  "built_in" : false,
		  "can_edit" : true
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


