
## RuleRunnerType  
Implementation mode for a rule runner (rule implementation).  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|python<br/>custom_class<br/>|

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
|mod_count||integer|


___  

## RuleModel  
Rule model that is returned by the REST API. Describes a single unique rule name.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|rule_name|Rule name|string|
|rule_python_module_content|Rule Python module content|string|
|type|Rule runner type|[RuleRunnerType](#null)|
|java_class_name|Java class name for a rule runner that will execute the sensor. The &quot;type&quot; must be &quot;java_class&quot;.|string|
|mode|Rule historic (past) values mode. A rule may require just the current sensor readout or use sensor readouts from past periods to perform prediction. The number of time windows is configured in the time_window setting.|[RuleTimeWindowMode](#null)|
|[time_window](\docs\reference\yaml\ruledefinitionyaml\#ruletimewindowsettingsspec)|Rule time window configuration when the mode is previous_readouts. Configures the number of past time windows (sensor readouts) that are passes as a parameter to the rule. For example, to calculate the average or perform prediction on historic data.|[RuleTimeWindowSettingsSpec](\docs\reference\yaml\ruledefinitionyaml\#ruletimewindowsettingsspec)|
|[fields](#null)|List of fields that are parameters of a custom rule. Those fields are used by the DQOps UI to display the data quality check editing screens with proper UI controls for all required fields.|[ParameterDefinitionsListSpec](#null)|
|parameters|Additional rule parameters|Map&lt;string, string&gt;|
|custom|This rule has a custom (user level) definition.|boolean|
|built_in|This rule has is a built-in rule.|boolean|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|


___  

