
## DataGroupingConfigurationListModel  
Basic model for data grouping configuration on a table, returned by the rest api.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|schema_name|Schema name.|string|
|table_name|Table name.|string|
|data_grouping_configuration_name|Data grouping configuration name.|string|
|default_data_grouping_configuration|True when this is the default data grouping configuration for the table.|boolean|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|


___  

## DataGroupingConfigurationModel  
Model of data grouping configuration on a table returned by the rest api, including all configuration information.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|schema_name|Schema name.|string|
|table_name|Table name.|string|
|data_grouping_configuration_name|Data grouping configuration name.|string|
|[spec](../../../reference/yaml/ConnectionYaml/#datagroupingconfigurationspec)|Data grouping specification with the definition of the list of data grouping dimensions, the column names to use in a **GROUP BY** clause or a value of a static tag to assign to every check result captured from the table.|[DataGroupingConfigurationSpec](../../../reference/yaml/ConnectionYaml/#datagroupingconfigurationspec)|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|
|yaml_parsing_error|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|string|


___  

## DataGroupingConfigurationTrimmedModel  
Data grouping on a table model with trimmed access path.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|data_grouping_configuration_name|Data grouping configuration name.|string|
|[spec](../../../reference/yaml/ConnectionYaml/#datagroupingconfigurationspec)|Data grouping configuration specification.|[DataGroupingConfigurationSpec](../../../reference/yaml/ConnectionYaml/#datagroupingconfigurationspec)|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|
|yaml_parsing_error|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|string|


___  

