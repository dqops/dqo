Rule management  


___  
## create_rule  
Creates (adds) a new custom rule given the rule definition.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/create_rule.py)
  

**POST**
```
http://localhost:8888/api/rules/{fullRuleName}  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_rule_name|Full rule name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Rule model|[RuleModel](\docs\client\models\rules\#rulemodel)|false|



___  
## delete_rule  
Deletes a custom rule definition  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/delete_rule.py)
  

**DELETE**
```
http://localhost:8888/api/rules/{fullRuleName}  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_rule_name|Full rule name|string|true|





___  
## get_all_rules  
Returns a flat list of all rules available in DQOps, both built-in rules and user defined or customized rules.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/get_all_rules.py)
  

**GET**
```
http://localhost:8888/api/rules  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|rule_list_model||List[[RuleListModel](\docs\client\models\rules\#rulelistmodel)]|







___  
## get_rule  
Returns a rule definition  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/get_rule.py)
  

**GET**
```
http://localhost:8888/api/rules/{fullRuleName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[rule_model](\docs\client\models\rules\#rulemodel)||[RuleModel](\docs\client\models\rules\#rulemodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_rule_name|Full rule name|string|true|





___  
## get_rule_folder_tree  
Returns a tree of all rules available in DQOps, both built-in rules and user defined or customized rules.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/get_rule_folder_tree.py)
  

**GET**
```
http://localhost:8888/api/definitions/rules  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[rule_folder_model](\docs\client\models\rules\#rulefoldermodel)||[RuleFolderModel](\docs\client\models\rules\#rulefoldermodel)|







___  
## update_rule  
Updates an existing rule, making a custom rule definition if it is not present  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/update_rule.py)
  

**PUT**
```
http://localhost:8888/api/rules/{fullRuleName}  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_rule_name|Full rule name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|List of rule definitions|[RuleModel](\docs\client\models\rules\#rulemodel)|false|



