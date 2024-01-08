
## RuleListModel
Rule list model that is returned by the REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|rule_name|Rule name without the folder.|string|
|full_rule_name|Full rule name, including the folder within the &quot;rules&quot; rule folder.|string|
|custom|This rule has is a custom rule or was customized by the user. This is a read-only value.|boolean|
|built_in|This rule is provided with DQOps as a built-in rule. This is a read-only value.|boolean|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|
|yaml_parsing_error|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|string|


___

## RuleFolderModel
Rule folder model that is returned by the REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|folders|A dictionary of nested folders with rules, the keys are the folder names.|Dict[string, [RuleFolderModel](#RuleFolderModel)]|
|rules|List of rules defined in this folder.|List[[RuleListModel](#RuleListModel)]|


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
|string|previous_readouts<br/>current_value<br/>|

___

## ParameterDefinitionsListSpec
List of parameter definitions - the parameters for custom sensors or custom rules.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|self||List[[ParameterDefinitionSpec](../../../reference/yaml/SensorDefinitionYaml.md#parameterdefinitionspec)]|


___

## RuleModel
Rule model that is returned by the REST API. Describes a single unique rule name.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|rule_name|Rule name|string|
|rule_python_module_content|Rule Python module content|string|
|[type](#rulerunnertype)|Rule runner type|[RuleRunnerType](#rulerunnertype)|
|java_class_name|Java class name for a rule runner that will execute the sensor. The &quot;type&quot; must be &quot;java_class&quot;.|string|
|[mode](#ruletimewindowmode)|Rule historic (past) values mode. A rule may require just the current sensor readout or use sensor readouts from past periods to perform prediction. The number of time windows is configured in the time_window setting.|[RuleTimeWindowMode](#ruletimewindowmode)|
|[time_window](../../../reference/yaml/RuleDefinitionYaml.md#ruletimewindowsettingsspec)|Rule time window configuration when the mode is previous_readouts. Configures the number of past time windows (sensor readouts) that are passes as a parameter to the rule. For example, to calculate the average or perform prediction on historic data.|[RuleTimeWindowSettingsSpec](../../../reference/yaml/RuleDefinitionYaml.md#ruletimewindowsettingsspec)|
|[fields](#parameterdefinitionslistspec)|List of fields that are parameters of a custom rule. Those fields are used by the DQOps UI to display the data quality check editing screens with proper UI controls for all required fields.|[ParameterDefinitionsListSpec](#parameterdefinitionslistspec)|
|parameters|Additional rule parameters|Dict[string, string]|
|custom|This rule has a custom (user level) definition.|boolean|
|built_in|This rule has is a built-in rule.|boolean|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|
|yaml_parsing_error|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|string|


___

