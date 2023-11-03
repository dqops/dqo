Jobs management controller that supports starting new jobs, such as running selected data quality checks  


___  
## cancel_job  
Cancels a running job  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/cancel_job.py)
  

**DELETE**
```
http://localhost:8888/api/jobs/jobs/{jobId}  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|job_id|Job id|long|true|





___  
## collect_statistics_on_data_groups  
Starts a new background job that will run selected data statistics collectors on tables, calculating separate metric for each data grouping  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/collect_statistics_on_data_groups.py)
  

**POST**
```
http://localhost:8888/api/jobs/collectstatistics/withgrouping  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[collect_statistics_queue_job_result](\docs\client\models\jobs\#collectstatisticsqueuejobresult)||[CollectStatisticsQueueJobResult](\docs\client\models\jobs\#collectstatisticsqueuejobresult)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|wait|Wait until the statistic collection job finishes to run, the default value is false (queue a background job and return the job id)|boolean|false|
|wait_timeout|The wait timeout in seconds, when the wait timeout elapses and the job is still running, only the job id is returned without the results. The default timeout is 120 seconds, but could be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|long|false|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Data statistics collectors filter|[StatisticsCollectorSearchFilters](\docs\client\models\#statisticscollectorsearchfilters)|false|



___  
## collect_statistics_on_table  
Starts a new background job that will run selected data statistics collectors on a whole table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/collect_statistics_on_table.py)
  

**POST**
```
http://localhost:8888/api/jobs/collectstatistics/table  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[collect_statistics_queue_job_result](\docs\client\models\jobs\#collectstatisticsqueuejobresult)||[CollectStatisticsQueueJobResult](\docs\client\models\jobs\#collectstatisticsqueuejobresult)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|wait|Wait until the statistic collection job finishes to run, the default value is false (queue a background job and return the job id)|boolean|false|
|wait_timeout|The wait timeout in seconds, when the wait timeout elapses and the job is still running, only the job id is returned without the results. The default timeout is 120 seconds, but could be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|long|false|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Data statistics collectors filter|[StatisticsCollectorSearchFilters](\docs\client\models\#statisticscollectorsearchfilters)|false|



___  
## delete_stored_data  
Starts a new background job that will delete stored data about check results, sensor readouts etc.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/delete_stored_data.py)
  

**POST**
```
http://localhost:8888/api/jobs/deletestoreddata  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[delete_stored_data_queue_job_result](\docs\client\models\jobs\#deletestoreddataqueuejobresult)||[DeleteStoredDataQueueJobResult](\docs\client\models\jobs\#deletestoreddataqueuejobresult)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|wait|Wait until the import tables job finishes to run, the default value is false (queue a background job and return the job id)|boolean|false|
|wait_timeout|The wait timeout in seconds, when the wait timeout elapses and the delete stored data job is still running, only the job id is returned without the results. The default timeout is 120 seconds, but could be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|long|false|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Delete stored data job parameters|[DeleteStoredDataQueueJobParameters](\docs\client\models\#deletestoreddataqueuejobparameters)|false|



___  
## get_all_jobs  
Retrieves a list of all queued and recently finished jobs.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/get_all_jobs.py)
  

**GET**
```
http://localhost:8888/api/jobs/jobs  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_job_queue_initial_snapshot_model](\docs\client\models\jobs\#dqojobqueueinitialsnapshotmodel)||[DqoJobQueueInitialSnapshotModel](\docs\client\models\jobs\#dqojobqueueinitialsnapshotmodel)|







___  
## get_job  
Retrieves the current status of a single job, identified by a job id.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/get_job.py)
  

**GET**
```
http://localhost:8888/api/jobs/jobs/{jobId}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_job_history_entry_model](\docs\client\models\jobs\#dqojobhistoryentrymodel)||[DqoJobHistoryEntryModel](\docs\client\models\jobs\#dqojobhistoryentrymodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|job_id|Job id|long|true|





___  
## get_job_changes_since  
Retrieves an incremental list of job changes (new jobs or job status changes)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/get_job_changes_since.py)
  

**GET**
```
http://localhost:8888/api/jobs/jobchangessince/{sequenceNumber}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_job_queue_incremental_snapshot_model](\docs\client\models\jobs\#dqojobqueueincrementalsnapshotmodel)||[DqoJobQueueIncrementalSnapshotModel](\docs\client\models\jobs\#dqojobqueueincrementalsnapshotmodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|sequence_number|Change sequence number to get job changes after that sequence|long|true|





___  
## import_tables  
Starts a new background job that will import selected tables.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/import_tables.py)
  

**POST**
```
http://localhost:8888/api/jobs/importtables  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[import_tables_queue_job_result](\docs\client\models\jobs\#importtablesqueuejobresult)||[ImportTablesQueueJobResult](\docs\client\models\jobs\#importtablesqueuejobresult)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|wait|Wait until the import tables job finishes to run, the default value is false (queue a background job and return the job id)|boolean|false|
|wait_timeout|The wait timeout in seconds, when the wait timeout elapses and the import tables job is still running, only the job id is returned without the results. The default timeout is 120 seconds, but could be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|long|false|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Import tables job parameters|[ImportTablesQueueJobParameters](\docs\client\models\jobs\#importtablesqueuejobparameters)|false|



___  
## is_cron_scheduler_running  
Checks if the DQOps internal CRON scheduler is running and processing jobs scheduled using cron expressions.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/is_cron_scheduler_running.py)
  

**GET**
```
http://localhost:8888/api/jobs/scheduler/isrunning  
```






___  
## run_checks  
Starts a new background job that will run selected data quality checks  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/run_checks.py)
  

**POST**
```
http://localhost:8888/api/jobs/runchecks  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[run_checks_queue_job_result](\docs\client\models\jobs\#runchecksqueuejobresult)||[RunChecksQueueJobResult](\docs\client\models\jobs\#runchecksqueuejobresult)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|wait|Wait until the checks finish to run, the default value is false (queue a background job and return the job id)|boolean|false|
|wait_timeout|The wait timeout in seconds, when the wait timeout elapses and the checks are still running, only the job id is returned without the results. The default timeout is 120 seconds, but could be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|long|false|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Data quality check run configuration (target checks and an optional time range)|[RunChecksParameters](\docs\client\models\jobs\#runchecksparameters)|false|



___  
## start_cron_scheduler  
Starts the job scheduler that runs monitoring jobs that are scheduled by assigning cron expressions.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/start_cron_scheduler.py)
  

**POST**
```
http://localhost:8888/api/jobs/scheduler/status/start  
```






___  
## stop_cron_scheduler  
Stops the job scheduler that runs monitoring jobs that are scheduled by assigning cron expressions.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/stop_cron_scheduler.py)
  

**POST**
```
http://localhost:8888/api/jobs/scheduler/status/stop  
```






___  
## synchronize_folders  
Starts multiple file synchronization jobs that will synchronize files from selected DQOps User home folders to the DQOps Cloud. The default synchronization mode is a full synchronization (upload local files, download new files from the cloud).  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/synchronize_folders.py)
  

**POST**
```
http://localhost:8888/api/jobs/synchronize  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[synchronize_multiple_folders_queue_job_result](\docs\client\models\jobs\#synchronizemultiplefoldersqueuejobresult)||[SynchronizeMultipleFoldersQueueJobResult](\docs\client\models\jobs\#synchronizemultiplefoldersqueuejobresult)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|wait|Wait until the synchronize multiple folders job finishes to run, the default value is false (queue a background job and return the job id)|boolean|false|
|wait_timeout|The wait timeout in seconds, when the wait timeout elapses and the synchronization with the DQOps Cloud is still running, only the job id is returned without the results. The default timeout is 120 seconds, but could be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|long|false|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Selection of folders that should be synchronized to the DQOps Cloud|[SynchronizeMultipleFoldersDqoQueueJobParameters](\docs\client\models\jobs\#synchronizemultiplefoldersdqoqueuejobparameters)|false|



___  
## wait_for_job  
Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/wait_for_job.py)
  

**GET**
```
http://localhost:8888/api/jobs/jobs/{jobId}/wait  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_job_history_entry_model](\docs\client\models\jobs\#dqojobhistoryentrymodel)||[DqoJobHistoryEntryModel](\docs\client\models\jobs\#dqojobhistoryentrymodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|job_id|Job id|long|true|
|wait_timeout|The wait timeout in seconds, when the wait timeout elapses and the job is still running, the method returns the job model that is not yet finished and has no results. The default timeout is 120 seconds, but could be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|long|false|





___  
## wait_for_run_checks_job  
Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/wait_for_run_checks_job.py)
  

**GET**
```
http://localhost:8888/api/jobs/runchecks/{jobId}/wait  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[run_checks_queue_job_result](\docs\client\models\jobs\#runchecksqueuejobresult)||[RunChecksQueueJobResult](\docs\client\models\jobs\#runchecksqueuejobresult)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|job_id|Job id|long|true|
|wait_timeout|The wait timeout in seconds, when the wait timeout elapses and the job is still running, the method returns the job model that is not yet finished and has no results. The default timeout is 120 seconds, but could be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|long|false|





