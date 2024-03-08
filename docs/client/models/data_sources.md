# DQOps REST API data_sources models reference
The references of all objects used by [data_sources](/docs/client/operations/data_sources.md) REST API operations are listed below.


## ConnectionTestStatus
Tabular output format for printing the tabular results.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|SUCCESS<br/>FAILURE<br/>CONNECTION_ALREADY_EXISTS<br/>|

___

## ConnectionTestModel
Connection test status result model returned from REST API. Describes the status of testing a connection
 (opening a connection to verify if it usable, credentials are approved and the access was granted by the tested data source).


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`connection_test_result`](#connectionteststatus)</span>|Connection test result|*[ConnectionTestStatus](#connectionteststatus)*|
|<span class="no-wrap-code">`error_message`</span>|Optional error message when the status is not "SUCCESS"|*string*|


___

## RemoteTableListModel
Remote table list model that is returned when a data source is introspected to retrieve the list of tables available in a data source.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">`schema_name`</span>|Schema name.|*string*|
|<span class="no-wrap-code">`table_name`</span>|Table name.|*string*|
|<span class="no-wrap-code">`already_imported`</span>|A flag that tells if the table been already imported.|*boolean*|


___

## SchemaRemoteModel
Schema model returned from REST API. Describes a schema on the source database with established connection.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">`schema_name`</span>|Schema name.|*string*|
|<span class="no-wrap-code">`already_imported`</span>|Has the schema been imported.|*boolean*|
|<span class="no-wrap-code">[`import_table_job_parameters`](/docs/client/models/jobs.md#importtablesqueuejobparameters)</span>|Job parameters for the import tables job that will import all tables from this schema.|*[ImportTablesQueueJobParameters](/docs/client/models/jobs.md#importtablesqueuejobparameters)*|


___

