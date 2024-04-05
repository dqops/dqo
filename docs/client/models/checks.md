---
title: DQOps REST API checks models reference
---
# DQOps REST API checks models reference
The references of all objects used by [checks](../operations/checks.md) REST API operations are listed below.


## CheckDefinitionListModel
Check list model that is returned by the REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*|
|<span class="no-wrap-code">`full_check_name`</span>|Full check name|*string*|
|<span class="no-wrap-code">`custom`</span>|This check has is a custom check or was customized by the user.|*boolean*|
|<span class="no-wrap-code">`built_in`</span>|This check is provided with DQOps as a built-in check.|*boolean*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

## CheckDefinitionFolderModel
Check list folder model that is returned by the REST API.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`folders`</span>|A dictionary of nested folders with data quality checks. The keys are the folder names.|*Dict[string, [CheckDefinitionFolderModel](./checks.md#checkdefinitionfoldermodel)]*|
|<span class="no-wrap-code">`checks`</span>|List of data quality checks defined in this folder.|*List[[CheckDefinitionListModel](./checks.md#checkdefinitionlistmodel)]*|


___

## CheckDefinitionModel
Check model that is returned by the REST API. Describes a single unique data quality check.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_name`</span>|Check name|*string*|
|<span class="no-wrap-code">`sensor_name`</span>|Sensor name|*string*|
|<span class="no-wrap-code">`rule_name`</span>|Rule name|*string*|
|<span class="no-wrap-code">`help_text`</span>|Help text that is shown in the check editor that describes the purpose and usage of the check|*string*|
|<span class="no-wrap-code">`friendly_name`</span>|An alternative check's name that is shown on the check editor.|*string*|
|<span class="no-wrap-code">`standard`</span>|This is a standard data quality check that is always shown on the data quality checks editor screen. Non-standard data quality checks (when the value is false) are advanced checks that are shown when the user decides to expand the list of checks.|*boolean*|
|<span class="no-wrap-code">`custom`</span>|This check has is a custom check or was customized by the user.|*boolean*|
|<span class="no-wrap-code">`built_in`</span>|This check is provided with DQOps as a built-in check.|*boolean*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

