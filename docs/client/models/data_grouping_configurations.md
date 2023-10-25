
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
|[spec](\docs\reference\yaml\connectionyaml\#datagroupingconfigurationspec)|Data stream specification.|[DataGroupingConfigurationSpec](\docs\reference\yaml\connectionyaml\#datagroupingconfigurationspec)|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|


___  

## DataGroupingConfigurationTrimmedModel  
Data grouping on a table model with trimmed access path.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|data_grouping_configuration_name|Data grouping configuration name.|string|
|[spec](\docs\reference\yaml\connectionyaml\#datagroupingconfigurationspec)|Data grouping configuration specification.|[DataGroupingConfigurationSpec](\docs\reference\yaml\connectionyaml\#datagroupingconfigurationspec)|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|


___  

