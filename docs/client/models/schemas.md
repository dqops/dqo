---
title: DQOps REST API schemas models reference
---
# DQOps REST API schemas models reference
The references of all objects used by [schemas](../operations/schemas.md) REST API operations are listed below.


## CheckTarget
Enumeration of targets where the check is applied. It is one of &quot;table&quot; or &quot;column&quot;.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|table<br/>column<br/>|

___

## SchemaModel
Schema model that is returned by the REST API. Describes a single unique schema name.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">`schema_name`</span>|Schema name.|*string*|
|<span class="no-wrap-code">`directory_prefix`</span>|Directory prefix.|*string*|
|<span class="no-wrap-code">[`run_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run all checks within this schema.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_profiling_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run profiling checks within this schema.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_monitoring_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run monitoring checks within this schema.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_partition_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run partition partitioned checks within this schema.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`collect_statistics_job_template`](./jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors within this schema.|*[StatisticsCollectorSearchFilters](./jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">[`import_table_job_parameters`](./jobs.md#importtablesqueuejobparameters)</span>|Job parameters for the import tables job that will import all tables from this schema.|*[ImportTablesQueueJobParameters](./jobs.md#importtablesqueuejobparameters)*|
|<span class="no-wrap-code">[`data_clean_job_template`](./jobs.md#deletestoreddataqueuejobparameters)</span>|Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this schema.|*[DeleteStoredDataQueueJobParameters](./jobs.md#deletestoreddataqueuejobparameters)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete the schema.|*boolean*|
|<span class="no-wrap-code">`can_collect_statistics`</span>|Boolean flag that decides if the current user can collect statistics.|*boolean*|
|<span class="no-wrap-code">`can_run_checks`</span>|Boolean flag that decides if the current user can run checks.|*boolean*|
|<span class="no-wrap-code">`can_delete_data`</span>|Boolean flag that decides if the current user can delete data (results).|*boolean*|
|<span class="no-wrap-code">`error_message`</span>|Field for error message.|*string*|


___

