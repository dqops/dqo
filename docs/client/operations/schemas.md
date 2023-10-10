
## get_schema_monitoring_checks_model  
Return a UI friendly model of configurations for data quality monitoring checks on a schema  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_monitoring_checks_model.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/monitoring/{timeScale}/model  
```





___  

## get_schema_monitoring_checks_templates  
Return available data quality checks on a requested schema.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_monitoring_checks_templates.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/bulkenable/monitoring/{timeScale}  
```





___  

## get_schema_partitioned_checks_model  
Return a UI friendly model of configurations for data quality partitioned checks on a schema  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_partitioned_checks_model.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/partitioned/{timeScale}/model  
```





___  

## get_schema_partitioned_checks_templates  
Return available data quality checks on a requested schema.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_partitioned_checks_templates.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/bulkenable/partitioned/{timeScale}  
```





___  

## get_schema_profiling_checks_model  
Return a flat list of configurations for profiling checks on a schema  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_profiling_checks_model.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/profiling/model  
```





___  

## get_schema_profiling_checks_templates  
Return available data quality checks on a requested schema.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_profiling_checks_templates.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/bulkenable/profiling  
```





___  

## get_schemas  
Returns a list of schemas inside a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schemas.py)
  

**GET**
```
api/connections/{connectionName}/schemas  
```





___  

___  

## CheckTarget  
Enumeration of targets where the check is applied. It is one of &quot;table&quot; or &quot;column&quot;.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|name||string| | | |
|ordinal||integer| | | |

___  

## ImportTablesQueueJobParameters  
Parameters for the {@link ImportTablesQueueJob ImportTablesQueueJob} job that imports selected tables from the source database.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name||string| | | |
|schema_name||string| | | |
|table_names||string_list| | | |

___  

## SchemaModel  
Schema model that is returned by the REST API. Describes a single unique schema name.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name|Connection name.|string| | | |
|schema_name|Schema name.|string| | | |
|[run_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run all checks within this schema.|[runChecksJobTemplate](\docs\client\models\#checksearchfilters)| | | |
|[run_profiling_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run profiling checks within this schema.|[runProfilingChecksJobTemplate](\docs\client\models\#checksearchfilters)| | | |
|[run_monitoring_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run monitoring checks within this schema.|[runMonitoringChecksJobTemplate](\docs\client\models\#checksearchfilters)| | | |
|[run_partition_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run partition partitioned checks within this schema.|[runPartitionChecksJobTemplate](\docs\client\models\#checksearchfilters)| | | |
|[collect_statistics_job_template](\docs\client\models\#statisticscollectorsearchfilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors within this schema.|[collectStatisticsJobTemplate](\docs\client\models\#statisticscollectorsearchfilters)| | | |
|[import_table_job_parameters](#importtablesqueuejobparameters)|Job parameters for the import tables job that will import all tables from this schema.|[importTableJobParameters](#importtablesqueuejobparameters)| | | |
|[data_clean_job_template](\docs\client\models\#deletestoreddataqueuejobparameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this schema.|[dataCleanJobTemplate](\docs\client\models\#deletestoreddataqueuejobparameters)| | | |
|can_edit|Boolean flag that decides if the current user can update or delete the schema.|boolean| | | |
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean| | | |
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean| | | |
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean| | | |

___  

