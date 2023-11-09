
## CheckDefinitionListModel  
Check list model that is returned by the REST API.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_name|Check name|string|
|full_check_name|Full check name|string|
|custom|This check has is a custom check or was customized by the user.|boolean|
|built_in|This check is provided with DQOps as a built-in check.|boolean|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|
|yaml_parsing_error|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|string|


___  

## CheckDefinitionFolderModel  
Check list folder model that is returned by the REST API.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|folders|A dictionary of nested folders with data quality checks. The keys are the folder names.|Dict[string, [CheckDefinitionFolderModel](../checks/#CheckDefinitionFolderModel)]|
|checks|List of data quality checks defined in this folder.|List[[CheckDefinitionListModel](../checks/#CheckDefinitionListModel)]|


___  

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
|yaml_parsing_error|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|string|


___  

