# DQOps REST API rules operations
Operations for managing custom data quality rule definitions in DQOps. The custom rules are stored in the DQOps user home folder.


___
## create_rule
Creates (adds) a new custom rule given the rule definition.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/create_rule.py) to see the source code on GitHub.


**POST**
```
http://localhost:8888/api/rules/{fullRuleName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`full_rule_name`</span>|Full rule name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Rule model|*[RuleModel](../models/rules.md#rulemodel)*| |




**Usage examples**

=== "curl"

    ```bash
    curl -X POST http://localhost:8888/api/rules/sample_target/sample_category/sample_rule^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"rule_name\":\"sample_rule\",\"type\":\"python\",\"java_class_name\":\"com.dqops.execution.rules.runners.python.PythonRuleRunner\",\"mode\":\"current_value\",\"custom\":false,\"built_in\":false,\"can_edit\":true}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.rules import create_rule
	from dqops.client.models import RuleModel, \
	                                RuleRunnerType, \
	                                RuleTimeWindowMode
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = RuleModel(
		rule_name='sample_rule',
		type=RuleRunnerType.python,
		java_class_name='com.dqops.execution.rules.runners.python.PythonRuleRunner',
		mode=RuleTimeWindowMode.current_value,
		custom=False,
		built_in=False,
		can_edit=True
	)
	
	call_result = create_rule.sync(
	    'sample_target/sample_category/sample_rule',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.rules import create_rule
	from dqops.client.models import RuleModel, \
	                                RuleRunnerType, \
	                                RuleTimeWindowMode
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = RuleModel(
		rule_name='sample_rule',
		type=RuleRunnerType.python,
		java_class_name='com.dqops.execution.rules.runners.python.PythonRuleRunner',
		mode=RuleTimeWindowMode.current_value,
		custom=False,
		built_in=False,
		can_edit=True
	)
	
	call_result = await create_rule.asyncio(
	    'sample_target/sample_category/sample_rule',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.rules import create_rule
	from dqops.client.models import RuleModel, \
	                                RuleRunnerType, \
	                                RuleTimeWindowMode
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = RuleModel(
		rule_name='sample_rule',
		type=RuleRunnerType.python,
		java_class_name='com.dqops.execution.rules.runners.python.PythonRuleRunner',
		mode=RuleTimeWindowMode.current_value,
		custom=False,
		built_in=False,
		can_edit=True
	)
	
	call_result = create_rule.sync(
	    'sample_target/sample_category/sample_rule',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.rules import create_rule
	from dqops.client.models import RuleModel, \
	                                RuleRunnerType, \
	                                RuleTimeWindowMode
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = RuleModel(
		rule_name='sample_rule',
		type=RuleRunnerType.python,
		java_class_name='com.dqops.execution.rules.runners.python.PythonRuleRunner',
		mode=RuleTimeWindowMode.current_value,
		custom=False,
		built_in=False,
		can_edit=True
	)
	
	call_result = await create_rule.asyncio(
	    'sample_target/sample_category/sample_rule',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```





___
## delete_rule
Deletes a custom rule definition
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/delete_rule.py) to see the source code on GitHub.


**DELETE**
```
http://localhost:8888/api/rules/{fullRuleName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`full_rule_name`</span>|Full rule name|*string*|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl -X DELETE http://localhost:8888/api/rules/sample_target/sample_category/sample_rule^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.rules import delete_rule
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = delete_rule.sync(
	    'sample_target/sample_category/sample_rule',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.rules import delete_rule
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	call_result = await delete_rule.asyncio(
	    'sample_target/sample_category/sample_rule',
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.rules import delete_rule
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = delete_rule.sync(
	    'sample_target/sample_category/sample_rule',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.rules import delete_rule
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	call_result = await delete_rule.asyncio(
	    'sample_target/sample_category/sample_rule',
	    client=dqops_client
	)
	
    ```





___
## get_all_rules
Returns a flat list of all rules available in DQOps, both built-in rules and user defined or customized rules.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/get_all_rules.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/rules
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`rule_list_model`</span>||*List[[RuleListModel](../models/rules.md#rulelistmodel)]*|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/rules^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.rules import get_all_rules
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_rules.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.rules import get_all_rules
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_rules.asyncio(
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.rules import get_all_rules
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_all_rules.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.rules import get_all_rules
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_all_rules.asyncio(
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    [ {
	  "rule_name" : "sample_rule",
	  "full_rule_name" : "sample_target/sample_category/sample_rule",
	  "custom" : false,
	  "built_in" : true,
	  "can_edit" : true
	}, {
	  "rule_name" : "sample_rule",
	  "full_rule_name" : "sample_target/sample_category/sample_rule",
	  "custom" : false,
	  "built_in" : true,
	  "can_edit" : true
	}, {
	  "rule_name" : "sample_rule",
	  "full_rule_name" : "sample_target/sample_category/sample_rule",
	  "custom" : false,
	  "built_in" : true,
	  "can_edit" : true
	} ]
    ```


___
## get_rule
Returns a rule definition
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/get_rule.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/rules/{fullRuleName}
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`rule_model`](../models/rules.md#rulemodel)</span>||*[RuleModel](../models/rules.md#rulemodel)*|




**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`full_rule_name`</span>|Full rule name|*string*|:material-check-bold:|






**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/rules/sample_target/sample_category/sample_rule^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.rules import get_rule
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_rule.sync(
	    'sample_target/sample_category/sample_rule',
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.rules import get_rule
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_rule.asyncio(
	    'sample_target/sample_category/sample_rule',
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.rules import get_rule
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_rule.sync(
	    'sample_target/sample_category/sample_rule',
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.rules import get_rule
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_rule.asyncio(
	    'sample_target/sample_category/sample_rule',
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    {
	  "rule_name" : "sample_rule",
	  "type" : "python",
	  "java_class_name" : "com.dqops.execution.rules.runners.python.PythonRuleRunner",
	  "mode" : "current_value",
	  "custom" : false,
	  "built_in" : false,
	  "can_edit" : true
	}
    ```


___
## get_rule_folder_tree
Returns a tree of all rules available in DQOps, both built-in rules and user defined or customized rules.
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/get_rule_folder_tree.py) to see the source code on GitHub.


**GET**
```
http://localhost:8888/api/definitions/rules
```

**Return value**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`rule_folder_model`](../models/rules.md#rulefoldermodel)</span>||*[RuleFolderModel](../models/rules.md#rulefoldermodel)*|








**Usage examples**

=== "curl"

    ```bash
    curl http://localhost:8888/api/definitions/rules^
		-H "Accept: application/json"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.rules import get_rule_folder_tree
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = get_rule_folder_tree.sync(
	    client=dqops_client
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.rules import get_rule_folder_tree
	
	
	dqops_client = client.Client(
	    'http://localhost:8888/',
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_rule_folder_tree.asyncio(
	    client=dqops_client
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.rules import get_rule_folder_tree
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = get_rule_folder_tree.sync(
	    client=dqops_client
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.rules import get_rule_folder_tree
	
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token,
	    raise_on_unexpected_status=True
	)
	
	call_result = await get_rule_folder_tree.asyncio(
	    client=dqops_client
	)
	
    ```




??? "Return value sample"
    ```js
    {
	  "rules" : [ {
	    "rule_name" : "sample_rule",
	    "full_rule_name" : "sample_target/sample_category/sample_rule",
	    "custom" : false,
	    "built_in" : true,
	    "can_edit" : true
	  }, {
	    "rule_name" : "sample_rule_1",
	    "full_rule_name" : "sample_target/sample_category/sample_rule_1",
	    "custom" : false,
	    "built_in" : true,
	    "can_edit" : true
	  }, {
	    "rule_name" : "sample_rule_2",
	    "full_rule_name" : "sample_target/sample_category/sample_rule_2",
	    "custom" : false,
	    "built_in" : true,
	    "can_edit" : true
	  }, {
	    "rule_name" : "sample_rule_3",
	    "full_rule_name" : "sample_target/sample_category/sample_rule_3",
	    "custom" : false,
	    "built_in" : true,
	    "can_edit" : true
	  } ],
	  "all_rules" : [ {
	    "rule_name" : "sample_rule",
	    "full_rule_name" : "sample_target/sample_category/sample_rule",
	    "custom" : false,
	    "built_in" : true,
	    "can_edit" : true
	  }, {
	    "rule_name" : "sample_rule_1",
	    "full_rule_name" : "sample_target/sample_category/sample_rule_1",
	    "custom" : false,
	    "built_in" : true,
	    "can_edit" : true
	  }, {
	    "rule_name" : "sample_rule_2",
	    "full_rule_name" : "sample_target/sample_category/sample_rule_2",
	    "custom" : false,
	    "built_in" : true,
	    "can_edit" : true
	  }, {
	    "rule_name" : "sample_rule_3",
	    "full_rule_name" : "sample_target/sample_category/sample_rule_3",
	    "custom" : false,
	    "built_in" : true,
	    "can_edit" : true
	  } ]
	}
    ```


___
## update_rule
Updates an existing rule, making a custom rule definition if it is not present
Follow the [link](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/rules/update_rule.py) to see the source code on GitHub.


**PUT**
```
http://localhost:8888/api/rules/{fullRuleName}
```



**Parameters of this method are described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|<span class="no-wrap-code">`full_rule_name`</span>|Full rule name|*string*|:material-check-bold:|




**Request body**

|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|List of rule definitions|*[RuleModel](../models/rules.md#rulemodel)*| |




**Usage examples**

=== "curl"

    ```bash
    curl -X PUT http://localhost:8888/api/rules/sample_target/sample_category/sample_rule^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"rule_name\":\"sample_rule\",\"type\":\"python\",\"java_class_name\":\"com.dqops.execution.rules.runners.python.PythonRuleRunner\",\"mode\":\"current_value\",\"custom\":false,\"built_in\":false,\"can_edit\":true}"
	
    ```

=== "Python sync client"

    ```python
    from dqops import client
	from dqops.client.api.rules import update_rule
	from dqops.client.models import RuleModel, \
	                                RuleRunnerType, \
	                                RuleTimeWindowMode
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = RuleModel(
		rule_name='sample_rule',
		type=RuleRunnerType.python,
		java_class_name='com.dqops.execution.rules.runners.python.PythonRuleRunner',
		mode=RuleTimeWindowMode.current_value,
		custom=False,
		built_in=False,
		can_edit=True
	)
	
	call_result = update_rule.sync(
	    'sample_target/sample_category/sample_rule',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python async client"

    ```python
    from dqops import client
	from dqops.client.api.rules import update_rule
	from dqops.client.models import RuleModel, \
	                                RuleRunnerType, \
	                                RuleTimeWindowMode
	
	dqops_client = client.Client(
	    'http://localhost:8888/'
	)
	
	request_body = RuleModel(
		rule_name='sample_rule',
		type=RuleRunnerType.python,
		java_class_name='com.dqops.execution.rules.runners.python.PythonRuleRunner',
		mode=RuleTimeWindowMode.current_value,
		custom=False,
		built_in=False,
		can_edit=True
	)
	
	call_result = await update_rule.asyncio(
	    'sample_target/sample_category/sample_rule',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth sync client"

    ```python
    from dqops import client
	from dqops.client.api.rules import update_rule
	from dqops.client.models import RuleModel, \
	                                RuleRunnerType, \
	                                RuleTimeWindowMode
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = RuleModel(
		rule_name='sample_rule',
		type=RuleRunnerType.python,
		java_class_name='com.dqops.execution.rules.runners.python.PythonRuleRunner',
		mode=RuleTimeWindowMode.current_value,
		custom=False,
		built_in=False,
		can_edit=True
	)
	
	call_result = update_rule.sync(
	    'sample_target/sample_category/sample_rule',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```

=== "Python auth async client"

    ```python
    from dqops import client
	from dqops.client.api.rules import update_rule
	from dqops.client.models import RuleModel, \
	                                RuleRunnerType, \
	                                RuleTimeWindowMode
	
	token = 's4mp13_4u7h_70k3n'
	
	dqops_client = client.AuthenticatedClient(
	    'http://localhost:8888/',
	    token=token
	)
	
	request_body = RuleModel(
		rule_name='sample_rule',
		type=RuleRunnerType.python,
		java_class_name='com.dqops.execution.rules.runners.python.PythonRuleRunner',
		mode=RuleTimeWindowMode.current_value,
		custom=False,
		built_in=False,
		can_edit=True
	)
	
	call_result = await update_rule.asyncio(
	    'sample_target/sample_category/sample_rule',
	    client=dqops_client,
	    json_body=request_body
	)
	
    ```





