Jobs management controller that supports starting new jobs, such as running selected data quality checks  


___  
## cancel_job  
Cancels a running job  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/cancel_job.py)
  

**DELETE**
```
http://localhost:8888/api/jobs/jobs/{jobId}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|job_id|Job id|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl -X DELETE http://localhost:8888/api/jobs/jobs/123123124324324^
		-H "Accept: application/json"

    ```




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
|[collect_statistics_queue_job_result](../../models/jobs/#collectstatisticsqueuejobresult)||[CollectStatisticsQueueJobResult](../../models/jobs/#collectstatisticsqueuejobresult)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|job_business_key|Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.|string| |
|wait|Wait until the statistic collection job finishes to run, the default value is false (queue a background job and return the job id)|boolean| |
|wait_timeout|The wait timeout in seconds, when the wait timeout elapses and the job is still running, only the job id is returned without the results. The default timeout is 120 seconds, but could be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|long| |




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Data statistics collectors filter|[StatisticsCollectorSearchFilters](../../models/jobs/#statisticscollectorsearchfilters)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/jobs/collectstatistics/withgrouping^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"columnNames\":[\"sample_column\"],\"collectorCategory\":\"sample_category\"}"

    ```



**Return value sample**  
    ```js
    {
	  "status" : "queued"
	}
    ```


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
|[collect_statistics_queue_job_result](../../models/jobs/#collectstatisticsqueuejobresult)||[CollectStatisticsQueueJobResult](../../models/jobs/#collectstatisticsqueuejobresult)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|job_business_key|Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.|string| |
|wait|Wait until the statistic collection job finishes to run, the default value is false (queue a background job and return the job id)|boolean| |
|wait_timeout|The wait timeout in seconds, when the wait timeout elapses and the job is still running, only the job id is returned without the results. The default timeout is 120 seconds, but could be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|long| |




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Data statistics collectors filter|[StatisticsCollectorSearchFilters](../../models/jobs/#statisticscollectorsearchfilters)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/jobs/collectstatistics/table^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"columnNames\":[\"sample_column\"],\"collectorCategory\":\"sample_category\"}"

    ```



**Return value sample**  
    ```js
    {
	  "status" : "queued"
	}
    ```


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
|[delete_stored_data_queue_job_result](../../models/jobs/#deletestoreddataqueuejobresult)||[DeleteStoredDataQueueJobResult](../../models/jobs/#deletestoreddataqueuejobresult)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|job_business_key|Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.|string| |
|wait|Wait until the import tables job finishes to run, the default value is false (queue a background job and return the job id)|boolean| |
|wait_timeout|The wait timeout in seconds, when the wait timeout elapses and the delete stored data job is still running, only the job id is returned without the results. The default timeout is 120 seconds, but could be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|long| |




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Delete stored data job parameters|[DeleteStoredDataQueueJobParameters](../../models/jobs/#deletestoreddataqueuejobparameters)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/jobs/deletestoreddata^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"columnNames\":[\"sample_column\"]}"

    ```



**Return value sample**  
    ```js
    {
	  "jobId" : {
	    "jobId" : 10832,
	    "createdAt" : "2007-10-11T13:42:00Z"
	  },
	  "status" : "queued"
	}
    ```


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
|[dqo_job_queue_initial_snapshot_model](../../models/jobs/#dqojobqueueinitialsnapshotmodel)||[DqoJobQueueInitialSnapshotModel](../../models/jobs/#dqojobqueueinitialsnapshotmodel)|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/jobs/jobs^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "jobs" : [ ],
	  "folderSynchronizationStatus" : {
	    "sources" : "unchanged",
	    "sensors" : "unchanged",
	    "rules" : "unchanged",
	    "checks" : "unchanged",
	    "settings" : "unchanged",
	    "credentials" : "unchanged",
	    "data_sensor_readouts" : "unchanged",
	    "data_check_results" : "unchanged",
	    "data_statistics" : "unchanged",
	    "data_errors" : "unchanged",
	    "data_incidents" : "unchanged"
	  },
	  "lastSequenceNumber" : 3854372
	}
    ```


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
|[dqo_job_history_entry_model](../../models/jobs/#dqojobhistoryentrymodel)||[DqoJobHistoryEntryModel](../../models/jobs/#dqojobhistoryentrymodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|job_id|Job id|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/jobs/jobs/123123124324324^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    { }
    ```


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
|[dqo_job_queue_incremental_snapshot_model](../../models/jobs/#dqojobqueueincrementalsnapshotmodel)||[DqoJobQueueIncrementalSnapshotModel](../../models/jobs/#dqojobqueueincrementalsnapshotmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|sequence_number|Change sequence number to get job changes after that sequence|long|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/jobs/jobchangessince/3854372^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "jobChanges" : [ ],
	  "folderSynchronizationStatus" : {
	    "sources" : "unchanged",
	    "sensors" : "unchanged",
	    "rules" : "unchanged",
	    "checks" : "unchanged",
	    "settings" : "unchanged",
	    "credentials" : "unchanged",
	    "data_sensor_readouts" : "unchanged",
	    "data_check_results" : "unchanged",
	    "data_statistics" : "unchanged",
	    "data_errors" : "unchanged",
	    "data_incidents" : "unchanged"
	  },
	  "lastSequenceNumber" : 3854372
	}
    ```


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
|[import_tables_queue_job_result](../../models/jobs/#importtablesqueuejobresult)||[ImportTablesQueueJobResult](../../models/jobs/#importtablesqueuejobresult)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|job_business_key|Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.|string| |
|wait|Wait until the import tables job finishes to run, the default value is false (queue a background job and return the job id)|boolean| |
|wait_timeout|The wait timeout in seconds, when the wait timeout elapses and the import tables job is still running, only the job id is returned without the results. The default timeout is 120 seconds, but could be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|long| |




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Import tables job parameters|[ImportTablesQueueJobParameters](../../models/jobs/#importtablesqueuejobparameters)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/jobs/importtables^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connectionName\":\"sample_connection\",\"schemaName\":\"sample_schema\",\"tableNames\":[\"sample_table\"]}"

    ```



**Return value sample**  
    ```js
    {
	  "status" : "queued"
	}
    ```


___  
## is_cron_scheduler_running  
Checks if the DQOps internal CRON scheduler is running and processing jobs scheduled using cron expressions.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/is_cron_scheduler_running.py)
  

**GET**
```
http://localhost:8888/api/jobs/scheduler/isrunning  
```







**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/jobs/scheduler/isrunning^
		-H "Accept: application/json"

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
|[run_checks_queue_job_result](../../models/jobs/#runchecksqueuejobresult)||[RunChecksQueueJobResult](../../models/jobs/#runchecksqueuejobresult)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|job_business_key|Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.|string| |
|wait|Wait until the checks finish to run, the default value is false (queue a background job and return the job id)|boolean| |
|wait_timeout|The wait timeout in seconds, when the wait timeout elapses and the checks are still running, only the job id is returned without the results. The default timeout is 120 seconds, but could be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|long| |




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Data quality check run configuration (target checks and an optional time range)|[RunChecksParameters](../../models/jobs/#runchecksparameters)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/jobs/runchecks^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"check_search_filters\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"column\":\"sample_column\",\"columnDataType\":\"string\"},\"dummy_execution\":false}"

    ```



**Return value sample**  
    ```js
    {
	  "status" : "queued"
	}
    ```


___  
## start_cron_scheduler  
Starts the job scheduler that runs monitoring jobs that are scheduled by assigning cron expressions.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/start_cron_scheduler.py)
  

**POST**
```
http://localhost:8888/api/jobs/scheduler/status/start  
```







**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/jobs/scheduler/status/start^
		-H "Accept: application/json"

    ```




___  
## stop_cron_scheduler  
Stops the job scheduler that runs monitoring jobs that are scheduled by assigning cron expressions.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/stop_cron_scheduler.py)
  

**POST**
```
http://localhost:8888/api/jobs/scheduler/status/stop  
```







**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/jobs/scheduler/status/stop^
		-H "Accept: application/json"

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
|[synchronize_multiple_folders_queue_job_result](../../models/jobs/#synchronizemultiplefoldersqueuejobresult)||[SynchronizeMultipleFoldersQueueJobResult](../../models/jobs/#synchronizemultiplefoldersqueuejobresult)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|job_business_key|Optional job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.|string| |
|wait|Wait until the synchronize multiple folders job finishes to run, the default value is false (queue a background job and return the job id)|boolean| |
|wait_timeout|The wait timeout in seconds, when the wait timeout elapses and the synchronization with the DQOps Cloud is still running, only the job id is returned without the results. The default timeout is 120 seconds, but could be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|long| |




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Selection of folders that should be synchronized to the DQOps Cloud|[SynchronizeMultipleFoldersDqoQueueJobParameters](../../models/jobs/#synchronizemultiplefoldersdqoqueuejobparameters)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/jobs/synchronize^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"direction\":\"full\",\"forceRefreshNativeTables\":false,\"detectCronSchedules\":false,\"sources\":true,\"sensors\":true,\"rules\":true,\"checks\":true,\"settings\":true,\"credentials\":true,\"dataSensorReadouts\":true,\"dataCheckResults\":true,\"dataStatistics\":true,\"dataErrors\":true,\"dataIncidents\":true,\"synchronizeFolderWithLocalChanges\":false}"

    ```



**Return value sample**  
    ```js
    {
	  "status" : "queued"
	}
    ```


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
|[dqo_job_history_entry_model](../../models/jobs/#dqojobhistoryentrymodel)||[DqoJobHistoryEntryModel](../../models/jobs/#dqojobhistoryentrymodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|job_id|Job id|string|:material-check-bold:|
|wait_timeout|The wait timeout in seconds, when the wait timeout elapses and the job is still running, the method returns the job model that is not yet finished and has no results. The default timeout is 120 seconds, but could be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|long| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/jobs/jobs/123123124324324/wait^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    { }
    ```


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
|[run_checks_queue_job_result](../../models/jobs/#runchecksqueuejobresult)||[RunChecksQueueJobResult](../../models/jobs/#runchecksqueuejobresult)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|job_id|Job id, it can be a job business key assigned to the job or a job id generated by DQOps|string|:material-check-bold:|
|wait_timeout|The wait timeout in seconds, when the wait timeout elapses and the job is still running, the method returns the job model that is not yet finished and has no results. The default timeout is 120 seconds, but could be reconfigured (see the &#x27;dqo&#x27; cli command documentation).|long| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/jobs/runchecks/123123124324324/wait^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "status" : "queued"
	}
    ```


