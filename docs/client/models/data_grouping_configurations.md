# DQOps REST API data_grouping_configurations models reference
The references of all objects used by [data_grouping_configurations](/docs/client/operations/data_grouping_configurations.md) REST API operations are listed below.


## DataGroupingConfigurationListModel
Basic model for data grouping configuration on a table, returned by the rest api.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">`schema_name`</span>|Schema name.|*string*|
|<span class="no-wrap-code">`table_name`</span>|Table name.|*string*|
|<span class="no-wrap-code">`data_grouping_configuration_name`</span>|Data grouping configuration name.|*string*|
|<span class="no-wrap-code">`default_data_grouping_configuration`</span>|True when this is the default data grouping configuration for the table.|*boolean*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|


___

## DataGroupingConfigurationModel
Model of data grouping configuration on a table returned by the rest api, including all configuration information.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">`schema_name`</span>|Schema name.|*string*|
|<span class="no-wrap-code">`table_name`</span>|Table name.|*string*|
|<span class="no-wrap-code">`data_grouping_configuration_name`</span>|Data grouping configuration name.|*string*|
|<span class="no-wrap-code">[`spec`](/docs/reference/yaml/ConnectionYaml/#datagroupingconfigurationspec)</span>|Data grouping specification with the definition of the list of data grouping dimensions, the column names to use in a **GROUP BY** clause or a value of a static tag to assign to every check result captured from the table.|*[DataGroupingConfigurationSpec](/docs/reference/yaml/ConnectionYaml/#datagroupingconfigurationspec)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

## DataGroupingConfigurationTrimmedModel
Data grouping on a table model with trimmed access path.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`data_grouping_configuration_name`</span>|Data grouping configuration name.|*string*|
|<span class="no-wrap-code">[`spec`](/docs/reference/yaml/ConnectionYaml/#datagroupingconfigurationspec)</span>|Data grouping configuration specification.|*[DataGroupingConfigurationSpec](/docs/reference/yaml/ConnectionYaml/#datagroupingconfigurationspec)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

