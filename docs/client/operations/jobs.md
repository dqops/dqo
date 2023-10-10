
## cancel_job  
Cancels a running job  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/cancel_job.py)
  

**DELETE**
```
api/jobs/jobs/{jobId}  
```





___  

## collect_statistics_on_data_groups  
Starts a new background job that will run selected data statistics collectors on tables, calculating separate metric for each data grouping  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/collect_statistics_on_data_groups.py)
  

**POST**
```
api/jobs/collectstatistics/withgrouping  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_queue_job_id](\docs\client\models\#dqoqueuejobid)||[DqoQueueJobId](\docs\client\models\#dqoqueuejobid)|






**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Data statistics collectors filter|[StatisticsCollectorSearchFilters](\docs\client\models\#statisticscollectorsearchfilters)|false|


___  

## collect_statistics_on_table  
Starts a new background job that will run selected data statistics collectors on a whole table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/collect_statistics_on_table.py)
  

**POST**
```
api/jobs/collectstatistics/table  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_queue_job_id](\docs\client\models\#dqoqueuejobid)||[DqoQueueJobId](\docs\client\models\#dqoqueuejobid)|






**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Data statistics collectors filter|[StatisticsCollectorSearchFilters](\docs\client\models\#statisticscollectorsearchfilters)|false|


___  

## delete_stored_data  
Starts a new background job that will delete stored data about check results, sensor readouts etc.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/delete_stored_data.py)
  

**POST**
```
api/jobs/deletestoreddata  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_queue_job_id](\docs\client\models\#dqoqueuejobid)||[DqoQueueJobId](\docs\client\models\#dqoqueuejobid)|






**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Delete stored data job parameters|[DeleteStoredDataQueueJobParameters](\docs\client\models\#deletestoreddataqueuejobparameters)|false|


___  

## get_all_jobs  
Retrieves a list of all queued and recently finished jobs.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/get_all_jobs.py)
  

**GET**
```
api/jobs/jobs  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_job_queue_initial_snapshot_model]()||[DqoJobQueueInitialSnapshotModel]()|






___  

## get_job  
Retrieves the current status of a single job, identified by a job id.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/get_job.py)
  

**GET**
```
api/jobs/jobs/{jobId}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_job_history_entry_model](\docs\client\operations\jobs\#dqojobhistoryentrymodel)||[DqoJobHistoryEntryModel](\docs\client\operations\jobs\#dqojobhistoryentrymodel)|






___  

## get_job_changes_since  
Retrieves an incremental list of job changes (new jobs or job status changes)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/get_job_changes_since.py)
  

**GET**
```
api/jobs/jobchangessince/{sequenceNumber}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_job_queue_incremental_snapshot_model](\docs\client\operations\jobs\#dqojobqueueincrementalsnapshotmodel)||[DqoJobQueueIncrementalSnapshotModel](\docs\client\operations\jobs\#dqojobqueueincrementalsnapshotmodel)|






___  

## import_tables  
Starts a new background job that will import selected tables.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/import_tables.py)
  

**POST**
```
api/jobs/importtables  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_queue_job_id](\docs\client\models\#dqoqueuejobid)||[DqoQueueJobId](\docs\client\models\#dqoqueuejobid)|






**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Import tables job parameters|[ImportTablesQueueJobParameters](\docs\client\operations\schemas\#importtablesqueuejobparameters)|false|


___  

## is_cron_scheduler_running  
Checks if the DQO internal CRON scheduler is running and processing jobs scheduled using cron expressions.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/is_cron_scheduler_running.py)
  

**GET**
```
api/jobs/scheduler/isrunning  
```





___  

## run_checks  
Starts a new background job that will run selected data quality checks  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/run_checks.py)
  

**POST**
```
api/jobs/runchecks  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[run_checks_queue_job_result](\docs\client\operations\jobs\#runchecksqueuejobresult)||[RunChecksQueueJobResult](\docs\client\operations\jobs\#runchecksqueuejobresult)|






**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Data quality check run configuration (target checks and an optional time range)|[RunChecksParameters](\docs\client\operations\jobs\#runchecksparameters)|false|


___  

## start_cron_scheduler  
Starts the job scheduler that runs monitoring jobs that are scheduled by assigning cron expressions.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/start_cron_scheduler.py)
  

**POST**
```
api/jobs/scheduler/status/start  
```





___  

## stop_cron_scheduler  
Stops the job scheduler that runs monitoring jobs that are scheduled by assigning cron expressions.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/stop_cron_scheduler.py)
  

**POST**
```
api/jobs/scheduler/status/stop  
```





___  

## synchronize_folders  
Starts multiple file synchronization jobs that will synchronize files from selected DQO User home folders to the DQO Cloud. The default synchronization mode is a full synchronization (upload local files, download new files from the cloud).  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/synchronize_folders.py)
  

**POST**
```
api/jobs/synchronize  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_queue_job_id](\docs\client\models\#dqoqueuejobid)||[DqoQueueJobId](\docs\client\models\#dqoqueuejobid)|






**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Selection of folders that should be synchronized to the DQO Cloud|[SynchronizeMultipleFoldersDqoQueueJobParameters](\docs\client\operations\jobs\#synchronizemultiplefoldersdqoqueuejobparameters)|false|


___  

## wait_for_job  
Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/wait_for_job.py)
  

**GET**
```
api/jobs/jobs/{jobId}/wait  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_job_history_entry_model](\docs\client\operations\jobs\#dqojobhistoryentrymodel)||[DqoJobHistoryEntryModel](\docs\client\operations\jobs\#dqojobhistoryentrymodel)|






___  

## wait_for_run_checks_job  
Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/jobs/wait_for_run_checks_job.py)
  

**GET**
```
api/jobs/runchecks/{jobId}/wait  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[run_checks_queue_job_result](\docs\client\operations\jobs\#runchecksqueuejobresult)||[RunChecksQueueJobResult](\docs\client\operations\jobs\#runchecksqueuejobresult)|






___  

___  

## CloudSynchronizationFoldersStatusModel  
Model that describes the current synchronization status for each folder.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sources|The synchronization status of the &quot;sources&quot; folder.|enum|synchronizing<br/>unchanged<br/>changed<br/>| | |
|sensors|The synchronization status of the &quot;sensors&quot; folder.|enum|synchronizing<br/>unchanged<br/>changed<br/>| | |
|rules|The synchronization status of the &quot;rules&quot; folder.|enum|synchronizing<br/>unchanged<br/>changed<br/>| | |
|checks|The synchronization status of the &quot;checks&quot; folder.|enum|synchronizing<br/>unchanged<br/>changed<br/>| | |
|settings|The synchronization status of the &quot;settings&quot; folder.|enum|synchronizing<br/>unchanged<br/>changed<br/>| | |
|credentials|The synchronization status of the &quot;.credentials&quot; folder.|enum|synchronizing<br/>unchanged<br/>changed<br/>| | |
|data_sensor_readouts|The synchronization status of the &quot;.data/sensor_readouts&quot; folder.|enum|synchronizing<br/>unchanged<br/>changed<br/>| | |
|data_check_results|The synchronization status of the &quot;.data/check_results&quot; folder.|enum|synchronizing<br/>unchanged<br/>changed<br/>| | |
|data_statistics|The synchronization status of the &quot;.data/statistics&quot; folder.|enum|synchronizing<br/>unchanged<br/>changed<br/>| | |
|data_errors|The synchronization status of the &quot;.data/errors&quot; folder.|enum|synchronizing<br/>unchanged<br/>changed<br/>| | |
|data_incidents|The synchronization status of the &quot;.data/incidents&quot; folder.|enum|synchronizing<br/>unchanged<br/>changed<br/>| | |

___  

## DqoJobQueueIncrementalSnapshotModel  
Job history snapshot model that returns only changes after a given change sequence.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[folder_synchronization_status](#cloudsynchronizationfoldersstatusmodel)||[folderSynchronizationStatus](#cloudsynchronizationfoldersstatusmodel)| | | |
|last_sequence_number||long| | | |

___  

## RunChecksJobResult  
  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|highest_severity|The highest check severity for the data quality checks executed in this batch.|enum|valid<br/>warning<br/>error<br/>fatal<br/>| | |
|executed_checks|The total count of all executed checks.|integer| | | |
|valid_results|The total count of all checks that finished successfully (with no data quality issues).|integer| | | |
|warnings|The total count of all invalid data quality checks that finished raising a warning.|integer| | | |
|errors|The total count of all invalid data quality checks that finished raising an error.|integer| | | |
|fatals|The total count of all invalid data quality checks that finished raising a fatal error.|integer| | | |
|execution_errors|The total number of checks that failed to execute due to some execution errors.|integer| | | |

___  

## RunChecksQueueJobResult  
  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[job_id](\docs\client\models\#dqoqueuejobid)|Job id that identifies a job that was started on the DQO job queue.|[jobId](\docs\client\models\#dqoqueuejobid)| | | |
|[result](#runchecksjobresult)|Optional result object that is returned only when the wait parameter was true and the &quot;run checks&quot; job has finished. Contains the summary result of the data quality checks executed, including the severity of the most severe issue detected. The calling code (the data pipeline) can decide if further processing should be continued.|[result](#runchecksjobresult)| | | |
|status|Job status|enum|running<br/>waiting<br/>queued<br/>cancelled<br/>cancel_requested<br/>failed<br/>succeeded<br/>| | |

___  

## SynchronizeRootFolderParameters  
Parameter object for starting a file synchronization job. Identifies the folder and direction that should be synchronized.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|folder||enum|settings<br/>_indexes<br/>data_errors<br/>sources<br/>data_incidents<br/>credentials<br/>rules<br/>data_sensor_readouts<br/>data_statistics<br/>sensors<br/>checks<br/>data_check_results<br/>_local_settings<br/>| | |
|direction||enum|download<br/>upload<br/>full<br/>| | |
|force_refresh_native_table||boolean| | | |

___  

## SynchronizeRootFolderDqoQueueJobParameters  
Parameters object for a job that synchronizes one folder with DQO Cloud.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[synchronization_parameter](#synchronizerootfolderparameters)||[synchronizationParameter](#synchronizerootfolderparameters)| | | |

___  

## SynchronizeMultipleFoldersDqoQueueJobParameters  
Simple object for starting multiple folder synchronization jobs with the same configuration.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|direction|File synchronization direction, the default is full synchronization (push local changes and pull other changes from DQO Cloud).|enum|download<br/>upload<br/>full<br/>| | |
|force_refresh_native_tables|Force full refresh of native tables in the data quality data warehouse. The default synchronization mode is to refresh only modified data.|boolean| | | |
|detect_cron_schedules|Scans the yaml files (with the configuration for connections and tables) and detects new cron schedules. Detected cron schedules are registered in the cron (Quartz) job scheduler.|boolean| | | |
|sources|Synchronize the &quot;sources&quot; folder.|boolean| | | |
|sensors|Synchronize the &quot;sensors&quot; folder.|boolean| | | |
|rules|Synchronize the &quot;rules&quot; folder.|boolean| | | |
|checks|Synchronize the &quot;checks&quot; folder.|boolean| | | |
|settings|Synchronize the &quot;settings&quot; folder.|boolean| | | |
|credentials|Synchronize the &quot;.credentials&quot; folder.|boolean| | | |
|data_sensor_readouts|Synchronize the &quot;.data/sensor_readouts&quot; folder.|boolean| | | |
|data_check_results|Synchronize the &quot;.data/check_results&quot; folder.|boolean| | | |
|data_statistics|Synchronize the &quot;.data/statistics&quot; folder.|boolean| | | |
|data_errors|Synchronize the &quot;.data/errors&quot; folder.|boolean| | | |
|data_incidents|Synchronize the &quot;.data/incidents&quot; folder.|boolean| | | |
|synchronize_folder_with_local_changes|Synchronize all folders that have local changes. When this field is set to true, there is no need to enable synchronization of single folders because DQO will decide which folders need synchronization (to be pushed to the cloud).|boolean| | | |

___  

## TimeWindowFilterParameters  
  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|daily_partitioning_recent_days|The number of recent days to analyze incrementally by daily partitioned data quality checks.|integer| | | |
|daily_partitioning_include_today|Analyze also today and later days when running daily partitioned checks. By default, daily partitioned checks will not analyze today and future dates. Setting true will disable filtering the end dates.|boolean| | | |
|monthly_partitioning_recent_months|The number of recent months to analyze incrementally by monthly partitioned data quality checks.|integer| | | |
|monthly_partitioning_include_current_month|Analyze also the current month and later months when running monthly partitioned checks. By default, monthly partitioned checks will not analyze the current month and future months. Setting true will disable filtering the end dates.|boolean| | | |
|from_date|Analyze the data since the given date (inclusive). The date should be an ISO 8601 date (yyyy-MM-dd). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date overrides recent days and recent months.|date| | | |
|from_date_time|Analyze the data since the given date and time (inclusive). The date and time should be an ISO 8601 local date and time without the time zone (yyyy-MM-dd HH:mm:ss). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date and time overrides recent days and recent months.|datetime| | | |
|to_date|Analyze the data until the given date (exclusive, the given date and the following dates are not analyzed). The date should be an ISO 8601 date (YYYY-MM-DD). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date overrides the parameters to disable analyzing today or the current month.|date| | | |
|to_date_time|Analyze the data until the given date and time (exclusive). The date should be an ISO 8601 date (yyyy-MM-dd). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date and time overrides the parameters to disable analyzing today or the current month.|datetime| | | |

___  

## RunChecksParameters  
  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[check_search_filters](\docs\client\models\#checksearchfilters)|Target data quality checks filter.|[checkSearchFilters](\docs\client\models\#checksearchfilters)| | | |
|[time_window_filter](#timewindowfilterparameters)|Optional time window filter, configures the time range that is analyzed or the number of recent days/months to analyze for day or month partitioned data.|[timeWindowFilter](#timewindowfilterparameters)| | | |
|dummy_execution|Set the value to true when the data quality checks should be executed in a dummy mode (without running checks on the target systems and storing the results). Only the jinja2 sensors will be rendered.|boolean| | | |
|[run_checks_result](\docs\client\operations\jobs\#runchecksjobresult)|The result of running the check, updated when the run checks job finishes. Contains the count of executed checks.|[runChecksResult](\docs\client\operations\jobs\#runchecksjobresult)| | | |

___  

## RunChecksOnTableParameters  
  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection|The name of the target connection.|string| | | |
|max_jobs_per_connection|The maximum number of concurrent &#x27;run checks on table&#x27; jobs that could be run on this connection. Limits the number of concurrent jobs.|integer| | | |
|[table](\docs\client\operations\columns\#physicaltablename)|The full physical name (schema.table) of the target table.|[table](\docs\client\operations\columns\#physicaltablename)| | | |
|[check_search_filters](\docs\client\models\#checksearchfilters)|Target data quality checks filter.|[checkSearchFilters](\docs\client\models\#checksearchfilters)| | | |
|[time_window_filter](#timewindowfilterparameters)|Optional time window filter, configures the time range that is analyzed or the number of recent days/months to analyze for day or month partitioned data.|[timeWindowFilter](#timewindowfilterparameters)| | | |
|dummy_execution|Set the value to true when the data quality checks should be executed in a dummy mode (without running checks on the target systems and storing the results). Only the jinja2 sensors will be rendered.|boolean| | | |
|[run_checks_result](\docs\client\operations\jobs\#runchecksjobresult)|The result of running the check, updated when the run checks job finishes. Contains the count of executed checks.|[runChecksResult](\docs\client\operations\jobs\#runchecksjobresult)| | | |

___  

## CollectStatisticsQueueJobResult  
  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|executed_statistics_collectors|The total count of all executed statistics collectors.|integer| | | |
|total_collectors_executed|The count of executed statistics collectors.|integer| | | |
|columns_analyzed|The count of columns for which DQO executed a collector and tried to read the statistics.|integer| | | |
|columns_successfully_analyzed|The count of columns for which DQO managed to obtain statistics.|integer| | | |
|total_collectors_failed|The count of statistics collectors that failed to execute.|integer| | | |
|total_collected_results|The total number of results that were collected.|integer| | | |

___  

## CollectStatisticsQueueJobParameters  
  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[statistics_collector_search_filters](\docs\client\models\#statisticscollectorsearchfilters)|Statistics collectors search filters that identify the type of statistics collector to run.|[statisticsCollectorSearchFilters](\docs\client\models\#statisticscollectorsearchfilters)| | | |
|data_scope|The target scope of collecting statistics. Statistics could be collected on a whole table or for each data grouping separately.|enum|table<br/>data_groupings<br/>| | |
|dummy_sensor_execution|Boolean flag that enables a dummy statistics collection (sensors are executed, but the statistics results are not written to the parquet files).|boolean| | | |
|[collect_statistics_result](#collectstatisticsqueuejobresult)|The summary of the statistics collection job after if finished. Returns the number of collectors analyzed, columns analyzed, statistics results captured.|[collectStatisticsResult](#collectstatisticsqueuejobresult)| | | |

___  

## CollectStatisticsOnTableQueueJobParameters  
  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection|The name of the target connection.|string| | | |
|max_jobs_per_connection|The maximum number of concurrent &#x27;run checks on table&#x27; jobs that could be run on this connection. Limits the number of concurrent jobs.|integer| | | |
|[table](\docs\client\operations\columns\#physicaltablename)|The full physical name (schema.table) of the target table.|[table](\docs\client\operations\columns\#physicaltablename)| | | |
|[statistics_collector_search_filters](\docs\client\models\#statisticscollectorsearchfilters)|Statistics collectors search filters that identify the type of statistics collector to run.|[statisticsCollectorSearchFilters](\docs\client\models\#statisticscollectorsearchfilters)| | | |
|data_scope|The target scope of collecting statistics. Statistics could be collected on a whole table or for each data grouping separately.|enum|table<br/>data_groupings<br/>| | |
|dummy_sensor_execution|Boolean flag that enables a dummy statistics collection (sensors are executed, but the statistics results are not written to the parquet files).|boolean| | | |
|[collect_statistics_result](#collectstatisticsqueuejobresult)|The summary of the statistics collection job after if finished. Returns the number of collectors analyzed, columns analyzed, statistics results captured.|[collectStatisticsResult](#collectstatisticsqueuejobresult)| | | |

___  

## ImportSchemaQueueJobParameters  
Parameters for the {@link ImportSchemaQueueJob ImportSchemaQueueJob} job that imports tables from a database.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name||string| | | |
|schema_name||string| | | |
|table_name_pattern||string| | | |

___  

## RepairStoredDataQueueJobParameters  
Parameters for the {@link RepairStoredDataQueueJob RepairStoredDataQueueJob} job that repairs data stored in user&#x27;s &quot;.data&quot; directory.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name||string| | | |
|schema_table_name||string| | | |
|repair_errors||boolean| | | |
|repair_statistics||boolean| | | |
|repair_check_results||boolean| | | |
|repair_sensor_readouts||boolean| | | |

___  

## DqoJobEntryParametersModel  
Model object returned to UI that has typed fields for each supported job parameter type.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[synchronize_root_folder_parameters](#synchronizerootfolderdqoqueuejobparameters)||[synchronizeRootFolderParameters](#synchronizerootfolderdqoqueuejobparameters)| | | |
|[synchronize_multiple_folders_parameters](#synchronizemultiplefoldersdqoqueuejobparameters)||[synchronizeMultipleFoldersParameters](#synchronizemultiplefoldersdqoqueuejobparameters)| | | |
|[run_scheduled_checks_parameters](\docs\client\models\#monitoringschedulespec)||[runScheduledChecksParameters](\docs\client\models\#monitoringschedulespec)| | | |
|[run_checks_parameters](\docs\client\operations\jobs\#runchecksparameters)||[runChecksParameters](\docs\client\operations\jobs\#runchecksparameters)| | | |
|[run_checks_on_table_parameters](#runchecksontableparameters)||[runChecksOnTableParameters](#runchecksontableparameters)| | | |
|[collect_statistics_parameters](#collectstatisticsqueuejobparameters)||[collectStatisticsParameters](#collectstatisticsqueuejobparameters)| | | |
|[collect_statistics_on_table_parameters](#collectstatisticsontablequeuejobparameters)||[collectStatisticsOnTableParameters](#collectstatisticsontablequeuejobparameters)| | | |
|[import_schema_parameters](#importschemaqueuejobparameters)||[importSchemaParameters](#importschemaqueuejobparameters)| | | |
|[import_table_parameters](\docs\client\operations\schemas\#importtablesqueuejobparameters)||[importTableParameters](\docs\client\operations\schemas\#importtablesqueuejobparameters)| | | |
|[delete_stored_data_parameters](\docs\client\models\#deletestoreddataqueuejobparameters)||[deleteStoredDataParameters](\docs\client\models\#deletestoreddataqueuejobparameters)| | | |
|[repair_stored_data_parameters](#repairstoreddataqueuejobparameters)||[repairStoredDataParameters](#repairstoreddataqueuejobparameters)| | | |

___  

## DqoJobHistoryEntryModel  
Model of a single job that was scheduled or has finished. It is stored in the job monitoring service on the history list.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[job_id](\docs\client\models\#dqoqueuejobid)||[jobId](\docs\client\models\#dqoqueuejobid)| | | |
|job_type||enum|collect statistics<br/>import schema<br/>queue thread shutdown<br/>repair stored data<br/>run checks<br/>import selected tables<br/>run checks on table<br/>collect statistics on table<br/>run scheduled checks by cron<br/>synchronize folder<br/>synchronize multiple folders<br/>delete stored data<br/>| | |
|[parameters](#dqojobentryparametersmodel)||[parameters](#dqojobentryparametersmodel)| | | |
|status||enum|running<br/>waiting<br/>queued<br/>cancelled<br/>cancel_requested<br/>failed<br/>succeeded<br/>| | |
|error_message||string| | | |

___  

