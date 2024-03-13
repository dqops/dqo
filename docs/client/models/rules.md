# DQOps REST API rules models reference
The references of all objects used by [rules](../operations/rules.md) REST API operations are listed below.


## RuleListModel
Rule list model that is returned by the REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`rule_name`</span>|Rule name without the folder.|*string*|
|<span class="no-wrap-code">`full_rule_name`</span>|Full rule name, including the folder within the "rules" rule folder.|*string*|
|<span class="no-wrap-code">`custom`</span>|This rule has is a custom rule or was customized by the user. This is a read-only value.|*boolean*|
|<span class="no-wrap-code">`built_in`</span>|This rule is provided with DQOps as a built-in rule. This is a read-only value.|*boolean*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

## RuleFolderModel
Rule folder model that is returned by the REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`folders`</span>|A dictionary of nested folders with rules, the keys are the folder names.|*Dict[string, [RuleFolderModel](./rules.md#rulefoldermodel)]*|
|<span class="no-wrap-code">`rules`</span>|List of rules defined in this folder.|*List[[RuleListModel](./rules.md#rulelistmodel)]*|


___

## RuleRunnerType
Implementation mode for a rule runner (rule implementation).


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|python<br/>java_class<br/>|

___

## RuleTimeWindowMode
Rule historic data mode. A rule may evaluate only the current sensor readout (current_value) or use historic values.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|current_value<br/>previous_readouts<br/>|

___

## ParameterDefinitionsListSpec
List of parameter definitions - the parameters for custom sensors or custom rules.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>|:mm|*List[[ParameterDefinitionSpec](../../reference/yaml/SensorDefinitionYaml.md#parameterdefinitionspec)]*|


___

## RuleModel
Rule model that is returned by the REST API. Describes a single unique rule name.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`rule_name`</span>|Rule name|*string*|
|<span class="no-wrap-code">`rule_python_module_content`</span>|Rule Python module content|*string*|
|<span class="no-wrap-code">[`type`](#rulerunnertype)</span>|Rule runner type|*[RuleRunnerType](#rulerunnertype)*|
|<span class="no-wrap-code">`java_class_name`</span>|Java class name for a rule runner that will execute the sensor. The "type" must be "java_class".|*string*|
|<span class="no-wrap-code">[`mode`](#ruletimewindowmode)</span>|Rule historic (past) values mode. A rule may require just the current sensor readout or use sensor readouts from past periods to perform prediction. The number of time windows is configured in the time_window setting.|*[RuleTimeWindowMode](#ruletimewindowmode)*|
|<span class="no-wrap-code">[`time_window`](../../reference/yaml/RuleDefinitionYaml.md#ruletimewindowsettingsspec)</span>|Rule time window configuration when the mode is previous_readouts. Configures the number of past time windows (sensor readouts) that are passes as a parameter to the rule. For example, to calculate the average or perform prediction on historic data.|*[RuleTimeWindowSettingsSpec](../../reference/yaml/RuleDefinitionYaml.md#ruletimewindowsettingsspec)*|
|<span class="no-wrap-code">[`fields`](#parameterdefinitionslistspec)</span>|List of fields that are parameters of a custom rule. Those fields are used by the DQOps UI to display the data quality check editing screens with proper UI controls for all required fields.|*[ParameterDefinitionsListSpec](#parameterdefinitionslistspec)*|
|<span class="no-wrap-code">`parameters`</span>|Additional rule parameters|*Dict[string, string]*|
|<span class="no-wrap-code">`custom`</span>|This rule has a custom (user level) definition.|*boolean*|
|<span class="no-wrap-code">`built_in`</span>|This rule has is a built-in rule.|*boolean*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

