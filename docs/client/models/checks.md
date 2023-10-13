
## CheckDefinitionModel  
Check model that is returned by the REST API. Describes a single unique data quality check.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_name|Check name|string|
|sensor_name|Sensor name|string|
|rule_name|Rule name|string|
|help_text|Help text that is shown in the check editor that describes the purpose and usage of the check|string|
|custom|This check has is a custom check or was customized by the user.|boolean|
|built_in|This check is provided with DQOps as a built-in check.|boolean|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|


___  

