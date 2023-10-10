
## get_remote_data_source_schemas  
Introspects a list of schemas inside a remote data source, identified by an already imported connection.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_sources/get_remote_data_source_schemas.py)
  

**GET**
```
api/datasource/connections/{connectionName}/schemas  
```





___  

## get_remote_data_source_tables  
Introspects the list of columns inside a schema on a remote data source that is identified by a connection that was added to DQO.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_sources/get_remote_data_source_tables.py)
  

**GET**
```
api/datasource/connections/{connectionName}/schemas/{schemaName}/tables  
```





___  

## test_connection  
Checks if the given remote connection could be opened and the credentials are valid  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/data_sources/test_connection.py)
  

**POST**
```
api/datasource/testconnection  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[connection_test_model](\docs\client\operations\data_sources\#connectiontestmodel)||[ConnectionTestModel](\docs\client\operations\data_sources\#connectiontestmodel)|






**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Basic connection model|[ConnectionBasicModel](\docs\client\models\#connectionbasicmodel)|false|


___  

___  

## ConnectionTestModel  
Connection test status result model returned from REST API. Describes the status of testing a connection
 (opening a connection to verify if it usable, credentials are approved and the access was granted by the tested data source).  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_test_result|Connection test result|enum|SUCCESS<br/>CONNECTION_ALREADY_EXISTS<br/>FAILURE<br/>| | |
|error_message|Optional error message when the status is not &quot;SUCCESS&quot;|string| | | |

___  

## RemoteTableBasicModel  
Basic table model that is returned when a data source is introspected to retrieve the list of tables available in a data source.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name|Connection name.|string| | | |
|schema_name|Schema name.|string| | | |
|table_name|Table name.|string| | | |
|already_imported|Has the table been imported.|boolean| | | |

___  

## SchemaRemoteModel  
Schema model returned from REST API. Describes a schema on the source database with established connection.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name|Connection name.|string| | | |
|schema_name|Schema name.|string| | | |
|already_imported|Has the schema been imported.|boolean| | | |
|[import_table_job_parameters](\docs\client\operations\schemas\#importtablesqueuejobparameters)|Job parameters for the import tables job that will import all tables from this schema.|[importTableJobParameters](\docs\client\operations\schemas\#importtablesqueuejobparameters)| | | |

___  

