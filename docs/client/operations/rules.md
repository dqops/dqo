
## create_rule  
Creates (adds) a new custom rule given the rule definition.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/create_rule.py)
  

**POST**
```
api/rules/{fullRuleName}  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Rule model|[RuleModel](\docs\client\operations\rules\#rulemodel)|false|


___  

## delete_rule  
Deletes a custom rule definition  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/delete_rule.py)
  

**DELETE**
```
api/rules/{fullRuleName}  
```





___  

## get_all_rules  
Returns a flat list of all rules available in DQO, both built-in rules and user defined or customized rules.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/get_all_rules.py)
  

**GET**
```
api/rules  
```





___  

## get_rule  
Returns a rule definition  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/get_rule.py)
  

**GET**
```
api/rules/{fullRuleName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[rule_model](\docs\client\operations\rules\#rulemodel)||[RuleModel](\docs\client\operations\rules\#rulemodel)|






___  

## get_rule_folder_tree  
Returns a tree of all rules available in DQO, both built-in rules and user defined or customized rules.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/get_rule_folder_tree.py)
  

**GET**
```
api/definitions/rules  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[rule_basic_folder_model]()||[RuleBasicFolderModel]()|






___  

## update_rule  
Updates an existing rule, making a custom rule definition if it is not present  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/update_rule.py)
  

**PUT**
```
api/rules/{fullRuleName}  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|List of rule definitions|[RuleModel](\docs\client\operations\rules\#rulemodel)|false|


___  

___  

## ParameterDefinitionsListSpec  
List of parameter definitions - the parameters for custom sensors or custom rules.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|mod_count||integer| | | |

___  

## RuleModel  
Rule model that is returned by the REST API. Describes a single unique rule name.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|rule_name|Rule name|string| | | |
|rule_python_module_content|Rule Python module content|string| | | |
|type|Rule runner type|enum|python<br/>java_class<br/>| | |
|java_class_name|Java class name for a rule runner that will execute the sensor. The &quot;type&quot; must be &quot;java_class&quot;.|string| | | |
|mode|Rule historic (past) values mode. A rule may require just the current sensor readout or use sensor readouts from past periods to perform prediction. The number of time windows is configured in the time_window setting.|enum|previous_readouts<br/>current_value<br/>| | |
|[time_window](\docs\reference\yaml\ruledefinitionyaml\#ruletimewindowsettingsspec)|Rule time window configuration when the mode is previous_readouts. Configures the number of past time windows (sensor readouts) that are passes as a parameter to the rule. For example, to calculate the average or perform prediction on historic data.|[timeWindow](\docs\reference\yaml\ruledefinitionyaml\#ruletimewindowsettingsspec)| | | |
|[fields](#parameterdefinitionslistspec)|List of fields that are parameters of a custom rule. Those fields are used by the DQO UI to display the data quality check editing screens with proper UI controls for all required fields.|[fields](#parameterdefinitionslistspec)| | | |
|custom|This rule has a custom (user level) definition.|boolean| | | |
|built_in|This rule has is a built-in rule.|boolean| | | |
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean| | | |

___  

