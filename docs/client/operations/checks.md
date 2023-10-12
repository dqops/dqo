
## create_check  
Creates (adds) a new custom check that is a pair of a sensor name and a rule name.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/create_check.py)
  

**POST**
```
api/checks/{fullCheckName}  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Check model|[CheckDefinitionModel](\docs\client\operations\checks\#checkdefinitionmodel)|false|


___  

## delete_check  
Deletes a custom check definition  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/delete_check.py)
  

**DELETE**
```
api/checks/{fullCheckName}  
```





___  

## get_all_checks  
Returns a flat list of all checks available in DQOps, both built-in checks and user defined or customized checks.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/get_all_checks.py)
  

**GET**
```
api/checks  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_definition_list_model]()||[CheckDefinitionListModel]()|






___  

## get_check  
Returns a check definition  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/get_check.py)
  

**GET**
```
api/checks/{fullCheckName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_definition_model](\docs\client\operations\checks\#checkdefinitionmodel)||[CheckDefinitionModel](\docs\client\operations\checks\#checkdefinitionmodel)|






___  

## get_check_folder_tree  
Returns a tree of all checks available in DQOps, both built-in checks and user defined or customized checks.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/get_check_folder_tree.py)
  

**GET**
```
api/definitions/checks  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_definition_folder_model]()||[CheckDefinitionFolderModel]()|






___  

## update_check  
Updates an existing check, making a custom check definition if it is not present  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/checks/update_check.py)
  

**PUT**
```
api/checks/{fullCheckName}  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|List of check definitions|[CheckDefinitionModel](\docs\client\operations\checks\#checkdefinitionmodel)|false|


___  

___  

## CheckDefinitionModel  
Check model that is returned by the REST API. Describes a single unique data quality check.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|check_name|Check name|string| | | |
|sensor_name|Sensor name|string| | | |
|rule_name|Rule name|string| | | |
|help_text|Help text that is shown in the check editor that describes the purpose and usage of the check|string| | | |
|custom|This check has is a custom check or was customized by the user.|boolean| | | |
|built_in|This check is provided with DQOps as a built-in check.|boolean| | | |
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean| | | |

___  

