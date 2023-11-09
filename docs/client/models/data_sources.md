
## ConnectionTestStatus  
Tabular output format for printing the tabular results.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|SUCCESS<br/>CONNECTION_ALREADY_EXISTS<br/>FAILURE<br/>|

___  

## ConnectionTestModel  
Connection test status result model returned from REST API. Describes the status of testing a connection
 (opening a connection to verify if it usable, credentials are approved and the access was granted by the tested data source).  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[connection_test_result](#connectionteststatus)|Connection test result|[ConnectionTestStatus](#connectionteststatus)|
|error_message|Optional error message when the status is not &quot;SUCCESS&quot;|string|


___  

## RemoteTableListModel  
Remote table list model that is returned when a data source is introspected to retrieve the list of tables available in a data source.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|schema_name|Schema name.|string|
|table_name|Table name.|string|
|already_imported|A flag that tells if the table been already imported.|boolean|


___  

## SchemaRemoteModel  
Schema model returned from REST API. Describes a schema on the source database with established connection.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|schema_name|Schema name.|string|
|already_imported|Has the schema been imported.|boolean|
|[import_table_job_parameters](../jobs/#importtablesqueuejobparameters)|Job parameters for the import tables job that will import all tables from this schema.|[ImportTablesQueueJobParameters](../jobs/#importtablesqueuejobparameters)|


___  

