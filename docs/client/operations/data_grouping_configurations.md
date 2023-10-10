
## create_table_grouping_configuration  
Creates a new data grouping configuration on a table level  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/create_table_grouping_configuration.py)
  

**POST**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Data grouping configuration simplified model|[DataGroupingConfigurationTrimmedModel](\docs\client\operations\data_grouping_configurations\#datagroupingconfigurationtrimmedmodel)|false|


___  

## delete_table_grouping_configuration  
Deletes a data grouping configuration from a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/delete_table_grouping_configuration.py)
  

**DELETE**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{dataGroupingConfigurationName}  
```





___  

## get_table_grouping_configuration  
Returns a model of the data grouping configuration  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/get_table_grouping_configuration.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{groupingConfigurationName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[data_grouping_configuration_model](\docs\client\operations\data_grouping_configurations\#datagroupingconfigurationmodel)||[DataGroupingConfigurationModel](\docs\client\operations\data_grouping_configurations\#datagroupingconfigurationmodel)|






___  

## get_table_grouping_configurations  
Returns the list of data grouping configurations on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/get_table_grouping_configurations.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings  
```





___  

## set_table_default_grouping_configuration  
Sets a table&#x27;s grouping configuration as the default or disables data grouping  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/set_table_default_grouping_configuration.py)
  

**PATCH**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/setdefault  
```





___  

## update_table_grouping_configuration  
Updates a data grouping configuration according to the provided model  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/update_table_grouping_configuration.py)
  

**PUT**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{dataGroupingConfigurationName}  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Data grouping configuration simplified model|[DataGroupingConfigurationTrimmedModel](\docs\client\operations\data_grouping_configurations\#datagroupingconfigurationtrimmedmodel)|false|


___  

___  

## DataGroupingConfigurationBasicModel  
Basic model for data grouping configuration on a table, returned by the rest api.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name|Connection name.|string| | | |
|schema_name|Schema name.|string| | | |
|table_name|Table name.|string| | | |
|data_grouping_configuration_name|Data grouping configuration name.|string| | | |
|default_data_grouping_configuration|True when this is the default data grouping configuration for the table.|boolean| | | |
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean| | | |

___  

## DataGroupingConfigurationTrimmedModel  
Data grouping on a table model with trimmed access path.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|data_grouping_configuration_name|Data grouping configuration name.|string| | | |
|[spec](\docs\client\models\#datagroupingconfigurationspec)|Data grouping configuration specification.|[spec](\docs\client\models\#datagroupingconfigurationspec)| | | |
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean| | | |

___  

## DataGroupingConfigurationModel  
Model of data grouping configuration on a table returned by the rest api, including all configuration information.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name|Connection name.|string| | | |
|schema_name|Schema name.|string| | | |
|table_name|Table name.|string| | | |
|data_grouping_configuration_name|Data grouping configuration name.|string| | | |
|[spec](\docs\client\models\#datagroupingconfigurationspec)|Data stream specification.|[spec](\docs\client\models\#datagroupingconfigurationspec)| | | |
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean| | | |

___  

