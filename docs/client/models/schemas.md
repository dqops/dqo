
## CheckTarget  
Enumeration of targets where the check is applied. It is one of &quot;table&quot; or &quot;column&quot;.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|column<br/>table<br/>|

___  

## CheckSearchFilters  
Hierarchy node search filters.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|column_name||string|
|column_data_type||string|
|column_nullable||boolean|
|[check_target](\docs\client\models\schemas\#checktarget)||[CheckTarget](\docs\client\models\schemas\#checktarget)|
|[check_type](\docs\client\models\#checktype)||[CheckType](\docs\client\models\#checktype)|
|[time_scale](\docs\client\models\#checktimescale)||[CheckTimeScale](\docs\client\models\#checktimescale)|
|check_category||string|
|table_comparison_name||string|
|check_name||string|
|sensor_name||string|
|check_configured||boolean|
|connection_name||string|
|schema_table_name||string|
|enabled||boolean|


___  

## SchemaModel  
Schema model that is returned by the REST API. Describes a single unique schema name.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|schema_name|Schema name.|string|
|[run_checks_job_template](#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run all checks within this schema.|[CheckSearchFilters](#checksearchfilters)|
|[run_profiling_checks_job_template](#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run profiling checks within this schema.|[CheckSearchFilters](#checksearchfilters)|
|[run_monitoring_checks_job_template](#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run monitoring checks within this schema.|[CheckSearchFilters](#checksearchfilters)|
|[run_partition_checks_job_template](#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run partition partitioned checks within this schema.|[CheckSearchFilters](#checksearchfilters)|
|[collect_statistics_job_template](\docs\client\models\jobs\#statisticscollectorsearchfilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors within this schema.|[StatisticsCollectorSearchFilters](\docs\client\models\jobs\#statisticscollectorsearchfilters)|
|[import_table_job_parameters](\docs\client\models\jobs\#importtablesqueuejobparameters)|Job parameters for the import tables job that will import all tables from this schema.|[ImportTablesQueueJobParameters](\docs\client\models\jobs\#importtablesqueuejobparameters)|
|[data_clean_job_template](\docs\client\models\jobs\#deletestoreddataqueuejobparameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this schema.|[DeleteStoredDataQueueJobParameters](\docs\client\models\jobs\#deletestoreddataqueuejobparameters)|
|can_edit|Boolean flag that decides if the current user can update or delete the schema.|boolean|
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean|
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean|
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean|


___  

