Rule management  


___  
## create_rule  
Creates (adds) a new custom rule given the rule definition.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/create_rule.py)
  

**POST**
```
http://localhost:8888/api/rules/{fullRuleName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_rule_name|Full rule name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Rule model|[RuleModel](../../models/rules/#RuleModel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/rules/sample_target/sample_category/sample_rule^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"rule_name\":\"sample_rule\",\"type\":\"python\",\"java_class_name\":\"com.dqops.execution.rules.runners.python.PythonRuleRunner\",\"mode\":\"current_value\",\"custom\":false,\"built_in\":false,\"can_edit\":true}"

    ```


___  
## delete_rule  
Deletes a custom rule definition  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/delete_rule.py)
  

**DELETE**
```
http://localhost:8888/api/rules/{fullRuleName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_rule_name|Full rule name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl -X DELETE http://localhost:8888/api/rules/sample_target/sample_category/sample_rule^
		-H "Accept: application/json"

    ```


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
|rule_list_model||List[[RuleListModel](../../models/rules/#RuleListModel)]|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/rules^
		-H "Accept: application/json"

    ```


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
|[rule_model](../../models/rules/#RuleModel)||[RuleModel](../../models/rules/#RuleModel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_rule_name|Full rule name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/rules/sample_target/sample_category/sample_rule^
		-H "Accept: application/json"

    ```


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
|[rule_folder_model](../../models/rules/#RuleFolderModel)||[RuleFolderModel](../../models/rules/#RuleFolderModel)|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/definitions/rules^
		-H "Accept: application/json"

    ```


___  
## update_rule  
Updates an existing rule, making a custom rule definition if it is not present  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/update_rule.py)
  

**PUT**
```
http://localhost:8888/api/rules/{fullRuleName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|full_rule_name|Full rule name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|List of rule definitions|[RuleModel](../../models/rules/#RuleModel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/rules/sample_target/sample_category/sample_rule^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"rule_name\":\"sample_rule\",\"type\":\"python\",\"java_class_name\":\"com.dqops.execution.rules.runners.python.PythonRuleRunner\",\"mode\":\"current_value\",\"custom\":false,\"built_in\":false,\"can_edit\":true}"

    ```


