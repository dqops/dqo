Manages data grouping configurations on a table  


___  
## create_table_grouping_configuration  
Creates a new data grouping configuration on a table level  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/create_table_grouping_configuration.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Data grouping configuration simplified model|[DataGroupingConfigurationTrimmedModel](\docs\client\models\data_grouping_configurations\#datagroupingconfigurationtrimmedmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings
		-H "Accept: application/json"
		-H "Content-Type: application/json"
		-d '{
		  "data_grouping_configuration_name" : "sample_data_grouping",
		  "spec" : {
		    "level_3" : {
		      "source" : "column_value",
		      "column" : "sample_column"
		    }
		  },
		  "can_edit" : true
		}'
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


___  
## delete_table_grouping_configuration  
Deletes a data grouping configuration from a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/delete_table_grouping_configuration.py)
  

**DELETE**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{dataGroupingConfigurationName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|data_grouping_configuration_name|Data grouping configuration name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl -X DELETE http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings/sample_data_grouping
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


___  
## get_table_grouping_configuration  
Returns a model of the data grouping configuration  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/get_table_grouping_configuration.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{groupingConfigurationName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[data_grouping_configuration_model](\docs\client\models\data_grouping_configurations\#datagroupingconfigurationmodel)||[DataGroupingConfigurationModel](\docs\client\models\data_grouping_configurations\#datagroupingconfigurationmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|grouping_configuration_name|Data grouping configuration name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings/sample_data_grouping
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


___  
## get_table_grouping_configurations  
Returns the list of data grouping configurations on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/get_table_grouping_configurations.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|data_grouping_configuration_list_model||List[[DataGroupingConfigurationListModel](\docs\client\models\data_grouping_configurations\#datagroupingconfigurationlistmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


___  
## set_table_default_grouping_configuration  
Sets a table&#x27;s grouping configuration as the default or disables data grouping  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/set_table_default_grouping_configuration.py)
  

**PATCH**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/setdefault  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|data_grouping_configuration_name|Data grouping configuration name or empty to disable data grouping|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PATCH http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings/setdefault
		-H "Accept: application/json"
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


___  
## update_table_grouping_configuration  
Updates a data grouping configuration according to the provided model  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_grouping_configurations/update_table_grouping_configuration.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/groupings/{dataGroupingConfigurationName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|data_grouping_configuration_name|Data grouping configuration name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Data grouping configuration simplified model|[DataGroupingConfigurationTrimmedModel](\docs\client\models\data_grouping_configurations\#datagroupingconfigurationtrimmedmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/groupings/sample_data_grouping
		-H "Accept: application/json"
		-H "Content-Type: application/json"
		-d '{
		  "data_grouping_configuration_name" : "sample_data_grouping",
		  "spec" : {
		    "level_3" : {
		      "source" : "column_value",
		      "column" : "sample_column"
		    }
		  },
		  "can_edit" : true
		}'
    ```
=== "python_sync"
      
    ```
    No render
    ```
=== "python_async"
      
    ```
    No render
    ```


