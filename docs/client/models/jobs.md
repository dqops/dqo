
## CollectStatisticsResult  
  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|executed_statistics_collectors|The total count of all executed statistics collectors.|integer|
|total_collectors_executed|The count of executed statistics collectors.|integer|
|columns_analyzed|The count of columns for which DQOps executed a collector and tried to read the statistics.|integer|
|columns_successfully_analyzed|The count of columns for which DQOps managed to obtain statistics.|integer|
|total_collectors_failed|The count of statistics collectors that failed to execute.|integer|
|total_collected_results|The total number of results that were collected.|integer|


___  

## DqoJobStatus  
Job status of a job on the queue.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|running<br/>waiting<br/>queued<br/>cancelled<br/>cancel_requested<br/>failed<br/>succeeded<br/>|

___  

## CollectStatisticsQueueJobResult  
  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[job_id](../#dqoqueuejobid)|Job id that identifies a job that was started on the DQOps job queue.|[DqoQueueJobId](../#dqoqueuejobid)|
|[result](#collectstatisticsresult)|Optional result object that is returned only when the wait parameter was true and the &quot;collect statistics&quot; job has finished. Contains the summary result of collecting basic statistics, including the number of statistics collectors (queries) that managed to capture metrics about the table(s). |[CollectStatisticsResult](#collectstatisticsresult)|
|[status](#dqojobstatus)|Job status|[DqoJobStatus](#dqojobstatus)|


___  

## DqoRoot  
DQOps root folders in the dqo use home that may be replicated to a remote file system (uploaded to DQOps Cloud or any other cloud).
 It is also used as a lock scope.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|settings<br/>_indexes<br/>data_errors<br/>sources<br/>data_incidents<br/>credentials<br/>rules<br/>data_sensor_readouts<br/>data_statistics<br/>sensors<br/>checks<br/>data_check_results<br/>_local_settings<br/>|

___  

## ParquetPartitionId  
Identifies a single partition for hive partitioned tables stored as parquet files.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_type](#dqoroot)|Table type.|[DqoRoot](#dqoroot)|
|connection_name|Connection name.|string|
|[table_name](../columns/#physicaltablename)|Table name (schema.table).|[PhysicalTableName](../columns/#physicaltablename)|
|month|The date of teh first day of the month that identifies a monthly partition.|date|


___  

## DataDeleteResultPartition  
Results of the &quot;data delete&quot; job for the monthly partition.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|rows_affected_count|The number of rows that were deleted from the partition.|integer|
|partition_deleted|True if a whole partition (a parquet file) was deleted instead of removing only selected rows.|boolean|


___  

## DeleteStoredDataResult  
Compiled results of the &quot;data delete&quot;.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|partition_results|Dictionary of partitions that where deleted or updated when the rows were deleted.|Dict[[ParquetPartitionId](#parquetpartitionid), [DataDeleteResultPartition](#datadeleteresultpartition)]|


___  

## DeleteStoredDataQueueJobResult  
Object returned from the operation that queues a &quot;delete stored data&quot; job. The result contains the job id that was started
 and optionally can also contain a dictionary of partitions that were cleared or deleted if the operation was started with wait&#x3D;true parameter to wait for the &quot;delete stored data&quot; job to finish.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[job_id](../#dqoqueuejobid)|Job id that identifies a job that was started on the DQOps job queue.|[DqoQueueJobId](../#dqoqueuejobid)|
|[result](#deletestoreddataresult)|Optional result object that is returned only when the wait parameter was true and the &quot;delete stored data&quot; job has finished. Contains a list of partitions that were deleted or updated.|[DeleteStoredDataResult](#deletestoreddataresult)|
|[status](../jobs/#dqojobstatus)|Job status|[DqoJobStatus](../jobs/#dqojobstatus)|


___  

## DqoJobType  
Job type that identifies a job by type.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|delete_stored_data<br/>import_schema<br/>repair_stored_data<br/>run_scheduled_checks_cron<br/>synchronize_folder<br/>run_checks_on_table<br/>run_checks<br/>collect_statistics<br/>synchronize_multiple_folders<br/>queue_thread_shutdown<br/>collect_statistics_on_table<br/>import_tables<br/>|

___  

## FileSynchronizationDirection  
Data synchronization direction between a local DQOps Home and DQOps Cloud data quality data warehouse.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|download<br/>upload<br/>full<br/>|

___  

## SynchronizeRootFolderParameters  
Parameter object for starting a file synchronization job. Identifies the folder and direction that should be synchronized.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[folder](../jobs/#dqoroot)||[DqoRoot](../jobs/#dqoroot)|
|[direction](#filesynchronizationdirection)||[FileSynchronizationDirection](#filesynchronizationdirection)|
|force_refresh_native_table||boolean|


___  

## SynchronizeRootFolderDqoQueueJobParameters  
Parameters object for a job that synchronizes one folder with DQOps Cloud.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[synchronization_parameter](#synchronizerootfolderparameters)||[SynchronizeRootFolderParameters](#synchronizerootfolderparameters)|


___  

## SynchronizeMultipleFoldersDqoQueueJobParameters  
Simple object for starting multiple folder synchronization jobs with the same configuration.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[direction](../jobs/#filesynchronizationdirection)|File synchronization direction, the default is full synchronization (push local changes and pull other changes from DQOps Cloud).|[FileSynchronizationDirection](../jobs/#filesynchronizationdirection)|
|force_refresh_native_tables|Force full refresh of native tables in the data quality data warehouse. The default synchronization mode is to refresh only modified data.|boolean|
|detect_cron_schedules|Scans the yaml files (with the configuration for connections and tables) and detects new cron schedules. Detected cron schedules are registered in the cron (Quartz) job scheduler.|boolean|
|sources|Synchronize the &quot;sources&quot; folder.|boolean|
|sensors|Synchronize the &quot;sensors&quot; folder.|boolean|
|rules|Synchronize the &quot;rules&quot; folder.|boolean|
|checks|Synchronize the &quot;checks&quot; folder.|boolean|
|settings|Synchronize the &quot;settings&quot; folder.|boolean|
|credentials|Synchronize the &quot;.credentials&quot; folder.|boolean|
|data_sensor_readouts|Synchronize the &quot;.data/sensor_readouts&quot; folder.|boolean|
|data_check_results|Synchronize the &quot;.data/check_results&quot; folder.|boolean|
|data_statistics|Synchronize the &quot;.data/statistics&quot; folder.|boolean|
|data_errors|Synchronize the &quot;.data/errors&quot; folder.|boolean|
|data_incidents|Synchronize the &quot;.data/incidents&quot; folder.|boolean|
|synchronize_folder_with_local_changes|Synchronize all folders that have local changes. When this field is set to true, there is no need to enable synchronization of single folders because DQOps will decide which folders need synchronization (to be pushed to the cloud).|boolean|


___  

## TimeWindowFilterParameters  
  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|daily_partitioning_recent_days|The number of recent days to analyze incrementally by daily partitioned data quality checks.|integer|
|daily_partitioning_include_today|Analyze also today and later days when running daily partitioned checks. By default, daily partitioned checks will not analyze today and future dates. Setting true will disable filtering the end dates.|boolean|
|monthly_partitioning_recent_months|The number of recent months to analyze incrementally by monthly partitioned data quality checks.|integer|
|monthly_partitioning_include_current_month|Analyze also the current month and later months when running monthly partitioned checks. By default, monthly partitioned checks will not analyze the current month and future months. Setting true will disable filtering the end dates.|boolean|
|from_date|Analyze the data since the given date (inclusive). The date should be an ISO 8601 date (yyyy-MM-dd). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date overrides recent days and recent months.|date|
|from_date_time|Analyze the data since the given date and time (inclusive). The date and time should be an ISO 8601 local date and time without the time zone (yyyy-MM-dd HH:mm:ss). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date and time overrides recent days and recent months.|datetime|
|to_date|Analyze the data until the given date (exclusive, the given date and the following dates are not analyzed). The date should be an ISO 8601 date (YYYY-MM-DD). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date overrides the parameters to disable analyzing today or the current month.|date|
|to_date_time|Analyze the data until the given date and time (exclusive). The date should be an ISO 8601 date (yyyy-MM-dd). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date and time overrides the parameters to disable analyzing today or the current month.|datetime|


___  

## RuleSeverityLevel  
Rule severity levels. Matches the severity level name (warning - 1, alert - 2, fatal - 3) with a numeric level.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|valid<br/>warning<br/>error<br/>fatal<br/>|

___  

## RunChecksResult  
  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[highest_severity](#ruleseveritylevel)|The highest check severity for the data quality checks executed in this batch.|[RuleSeverityLevel](#ruleseveritylevel)|
|executed_checks|The total count of all executed checks.|integer|
|valid_results|The total count of all checks that finished successfully (with no data quality issues).|integer|
|warnings|The total count of all invalid data quality checks that finished raising a warning.|integer|
|errors|The total count of all invalid data quality checks that finished raising an error.|integer|
|fatals|The total count of all invalid data quality checks that finished raising a fatal error.|integer|
|execution_errors|The total number of checks that failed to execute due to some execution errors.|integer|


___  

## RunChecksParameters  
  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_search_filters](../#checksearchfilters)|Target data quality checks filter.|[CheckSearchFilters](../#checksearchfilters)|
|[time_window_filter](#timewindowfilterparameters)|Optional time window filter, configures the time range that is analyzed or the number of recent days/months to analyze for day or month partitioned data.|[TimeWindowFilterParameters](#timewindowfilterparameters)|
|dummy_execution|Set the value to true when the data quality checks should be executed in a dummy mode (without running checks on the target systems and storing the results). Only the jinja2 sensors will be rendered.|boolean|
|[run_checks_result](#runchecksresult)|The result of running the check, updated when the run checks job finishes. Contains the count of executed checks.|[RunChecksResult](#runchecksresult)|


___  

## RunChecksOnTableParameters  
  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection|The name of the target connection.|string|
|max_jobs_per_connection|The maximum number of concurrent &#x27;run checks on table&#x27; jobs that could be run on this connection. Limits the number of concurrent jobs.|integer|
|[table](../columns/#physicaltablename)|The full physical name (schema.table) of the target table.|[PhysicalTableName](../columns/#physicaltablename)|
|[check_search_filters](../#checksearchfilters)|Target data quality checks filter.|[CheckSearchFilters](../#checksearchfilters)|
|[time_window_filter](../jobs/#timewindowfilterparameters)|Optional time window filter, configures the time range that is analyzed or the number of recent days/months to analyze for day or month partitioned data.|[TimeWindowFilterParameters](../jobs/#timewindowfilterparameters)|
|dummy_execution|Set the value to true when the data quality checks should be executed in a dummy mode (without running checks on the target systems and storing the results). Only the jinja2 sensors will be rendered.|boolean|
|[run_checks_result](../jobs/#runchecksresult)|The result of running the check, updated when the run checks job finishes. Contains the count of executed checks.|[RunChecksResult](../jobs/#runchecksresult)|


___  

## StatisticsDataScope  
Enumeration of possible statistics scopes. &quot;table&quot; - a whole table was profiled, &quot;data_groupings&quot; - groups of rows were profiled.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|data_group<br/>table<br/>|

___  

## CollectStatisticsQueueJobParameters  
  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[statistics_collector_search_filters](../#statisticscollectorsearchfilters)|Statistics collectors search filters that identify the type of statistics collector to run.|[StatisticsCollectorSearchFilters](../#statisticscollectorsearchfilters)|
|[data_scope](#statisticsdatascope)|The target scope of collecting statistics. Statistics could be collected on a whole table or for each data grouping separately.|[StatisticsDataScope](#statisticsdatascope)|
|dummy_sensor_execution|Boolean flag that enables a dummy statistics collection (sensors are executed, but the statistics results are not written to the parquet files).|boolean|
|[collect_statistics_result](../jobs/#collectstatisticsresult)|The summary of the statistics collection job after if finished. Returns the number of collectors analyzed, columns analyzed, statistics results captured.|[CollectStatisticsResult](../jobs/#collectstatisticsresult)|


___  

## CollectStatisticsOnTableQueueJobParameters  
  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection|The name of the target connection.|string|
|max_jobs_per_connection|The maximum number of concurrent &#x27;run checks on table&#x27; jobs that could be run on this connection. Limits the number of concurrent jobs.|integer|
|[table](../columns/#physicaltablename)|The full physical name (schema.table) of the target table.|[PhysicalTableName](../columns/#physicaltablename)|
|[statistics_collector_search_filters](../#statisticscollectorsearchfilters)|Statistics collectors search filters that identify the type of statistics collector to run.|[StatisticsCollectorSearchFilters](../#statisticscollectorsearchfilters)|
|[data_scope](../jobs/#statisticsdatascope)|The target scope of collecting statistics. Statistics could be collected on a whole table or for each data grouping separately.|[StatisticsDataScope](../jobs/#statisticsdatascope)|
|dummy_sensor_execution|Boolean flag that enables a dummy statistics collection (sensors are executed, but the statistics results are not written to the parquet files).|boolean|
|[collect_statistics_result](../jobs/#collectstatisticsresult)|The summary of the statistics collection job after if finished. Returns the number of collectors analyzed, columns analyzed, statistics results captured.|[CollectStatisticsResult](../jobs/#collectstatisticsresult)|


___  

## ImportSchemaQueueJobParameters  
Parameters for the {@link ImportSchemaQueueJob ImportSchemaQueueJob} job that imports tables from a database.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name||string|
|schema_name||string|
|table_name_pattern||string|


___  

## ImportTablesQueueJobParameters  
Parameters for the {@link ImportTablesQueueJob ImportTablesQueueJob} job that imports selected tables from the source database.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name||string|
|schema_name||string|
|table_names||string_list|


___  

## RepairStoredDataQueueJobParameters  
Parameters for the {@link RepairStoredDataQueueJob RepairStoredDataQueueJob} job that repairs data stored in user&#x27;s &quot;.data&quot; directory.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name||string|
|schema_table_name||string|
|repair_errors||boolean|
|repair_statistics||boolean|
|repair_check_results||boolean|
|repair_sensor_readouts||boolean|


___  

## DqoJobEntryParametersModel  
Model object returned to UI that has typed fields for each supported job parameter type.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[synchronize_root_folder_parameters](#synchronizerootfolderdqoqueuejobparameters)||[SynchronizeRootFolderDqoQueueJobParameters](#synchronizerootfolderdqoqueuejobparameters)|
|[synchronize_multiple_folders_parameters](../jobs/#synchronizemultiplefoldersdqoqueuejobparameters)||[SynchronizeMultipleFoldersDqoQueueJobParameters](../jobs/#synchronizemultiplefoldersdqoqueuejobparameters)|
|[run_scheduled_checks_parameters](../#monitoringschedulespec)||[MonitoringScheduleSpec](../#monitoringschedulespec)|
|[run_checks_parameters](../jobs/#runchecksparameters)||[RunChecksParameters](../jobs/#runchecksparameters)|
|[run_checks_on_table_parameters](#runchecksontableparameters)||[RunChecksOnTableParameters](#runchecksontableparameters)|
|[collect_statistics_parameters](#collectstatisticsqueuejobparameters)||[CollectStatisticsQueueJobParameters](#collectstatisticsqueuejobparameters)|
|[collect_statistics_on_table_parameters](#collectstatisticsontablequeuejobparameters)||[CollectStatisticsOnTableQueueJobParameters](#collectstatisticsontablequeuejobparameters)|
|[import_schema_parameters](#importschemaqueuejobparameters)||[ImportSchemaQueueJobParameters](#importschemaqueuejobparameters)|
|[import_table_parameters](../jobs/#importtablesqueuejobparameters)||[ImportTablesQueueJobParameters](../jobs/#importtablesqueuejobparameters)|
|[delete_stored_data_parameters](../#deletestoreddataqueuejobparameters)||[DeleteStoredDataQueueJobParameters](../#deletestoreddataqueuejobparameters)|
|[repair_stored_data_parameters](#repairstoreddataqueuejobparameters)||[RepairStoredDataQueueJobParameters](#repairstoreddataqueuejobparameters)|


___  

## DqoJobHistoryEntryModel  
Model of a single job that was scheduled or has finished. It is stored in the job monitoring service on the history list.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[job_id](../#dqoqueuejobid)||[DqoQueueJobId](../#dqoqueuejobid)|
|[job_type](#dqojobtype)||[DqoJobType](#dqojobtype)|
|[parameters](#dqojobentryparametersmodel)||[DqoJobEntryParametersModel](#dqojobentryparametersmodel)|
|[status](../jobs/#dqojobstatus)||[DqoJobStatus](../jobs/#dqojobstatus)|
|error_message||string|


___  

## DqoJobChangeModel  
Describes a change to the job status or the job queue (such as a new job was added).  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[status](../jobs/#dqojobstatus)||[DqoJobStatus](../jobs/#dqojobstatus)|
|[job_id](../#dqoqueuejobid)||[DqoQueueJobId](../#dqoqueuejobid)|
|change_sequence||long|
|[updated_model](../jobs/#dqojobhistoryentrymodel)||[DqoJobHistoryEntryModel](../jobs/#dqojobhistoryentrymodel)|


___  

## FolderSynchronizationStatus  
Enumeration of statuses that identify the synchronization status for each folder that could be synchronized to DQOps Cloud.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|synchronizing<br/>unchanged<br/>changed<br/>|

___  

## CloudSynchronizationFoldersStatusModel  
Model that describes the current synchronization status for each folder.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[sources](#foldersynchronizationstatus)|The synchronization status of the &quot;sources&quot; folder.|[FolderSynchronizationStatus](#foldersynchronizationstatus)|
|[sensors](../jobs/#foldersynchronizationstatus)|The synchronization status of the &quot;sensors&quot; folder.|[FolderSynchronizationStatus](../jobs/#foldersynchronizationstatus)|
|[rules](../jobs/#foldersynchronizationstatus)|The synchronization status of the &quot;rules&quot; folder.|[FolderSynchronizationStatus](../jobs/#foldersynchronizationstatus)|
|[checks](../jobs/#foldersynchronizationstatus)|The synchronization status of the &quot;checks&quot; folder.|[FolderSynchronizationStatus](../jobs/#foldersynchronizationstatus)|
|[settings](../jobs/#foldersynchronizationstatus)|The synchronization status of the &quot;settings&quot; folder.|[FolderSynchronizationStatus](../jobs/#foldersynchronizationstatus)|
|[credentials](../jobs/#foldersynchronizationstatus)|The synchronization status of the &quot;.credentials&quot; folder.|[FolderSynchronizationStatus](../jobs/#foldersynchronizationstatus)|
|[data_sensor_readouts](../jobs/#foldersynchronizationstatus)|The synchronization status of the &quot;.data/sensor_readouts&quot; folder.|[FolderSynchronizationStatus](../jobs/#foldersynchronizationstatus)|
|[data_check_results](../jobs/#foldersynchronizationstatus)|The synchronization status of the &quot;.data/check_results&quot; folder.|[FolderSynchronizationStatus](../jobs/#foldersynchronizationstatus)|
|[data_statistics](../jobs/#foldersynchronizationstatus)|The synchronization status of the &quot;.data/statistics&quot; folder.|[FolderSynchronizationStatus](../jobs/#foldersynchronizationstatus)|
|[data_errors](../jobs/#foldersynchronizationstatus)|The synchronization status of the &quot;.data/errors&quot; folder.|[FolderSynchronizationStatus](../jobs/#foldersynchronizationstatus)|
|[data_incidents](../jobs/#foldersynchronizationstatus)|The synchronization status of the &quot;.data/incidents&quot; folder.|[FolderSynchronizationStatus](../jobs/#foldersynchronizationstatus)|


___  

## DqoJobQueueIncrementalSnapshotModel  
Job history snapshot model that returns only changes after a given change sequence.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|job_changes||List[[DqoJobChangeModel](#dqojobchangemodel)]|
|[folder_synchronization_status](#cloudsynchronizationfoldersstatusmodel)||[CloudSynchronizationFoldersStatusModel](#cloudsynchronizationfoldersstatusmodel)|
|last_sequence_number||long|


___  

## DqoJobQueueInitialSnapshotModel  
Returns the current snapshot of running jobs.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|jobs||List[[DqoJobHistoryEntryModel](../jobs/#dqojobhistoryentrymodel)]|
|[folder_synchronization_status](../jobs/#cloudsynchronizationfoldersstatusmodel)||[CloudSynchronizationFoldersStatusModel](../jobs/#cloudsynchronizationfoldersstatusmodel)|
|last_sequence_number||long|


___  

## ImportTablesResult  
Result object from the {@link ImportTablesQueueJob ImportTablesQueueJob} table import job that returns list of tables that have been imported.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|source_table_specs|Table schemas (including column schemas) of imported tables.|List[[TableSpec](../../../reference/yaml/tableyaml/#tablespec)]|


___  

## ImportTablesQueueJobResult  
Object returned from the operation that queues a &quot;import tables&quot; job. The result contains the job id that was started
 and optionally can also contain the result of importing tables if the operation was started with wait&#x3D;true parameter to wait for the &quot;import tables&quot; job to finish.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[job_id](../#dqoqueuejobid)|Job id that identifies a job that was started on the DQOps job queue.|[DqoQueueJobId](../#dqoqueuejobid)|
|[result](#importtablesresult)|Optional result object that is returned only when the wait parameter was true and the &quot;import tables&quot; job has finished. Contains the summary result of importing tables, including table and column schemas of imported tables. |[ImportTablesResult](#importtablesresult)|
|[status](../jobs/#dqojobstatus)|Job status|[DqoJobStatus](../jobs/#dqojobstatus)|


___  

## RunChecksQueueJobResult  
  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[job_id](../#dqoqueuejobid)|Job id that identifies a job that was started on the DQOps job queue.|[DqoQueueJobId](../#dqoqueuejobid)|
|[result](../jobs/#runchecksresult)|Optional result object that is returned only when the wait parameter was true and the &quot;run checks&quot; job has finished. Contains the summary result of the data quality checks executed, including the severity of the most severe issue detected. The calling code (the data pipeline) can decide if further processing should be continued.|[RunChecksResult](../jobs/#runchecksresult)|
|[status](../jobs/#dqojobstatus)|Job status|[DqoJobStatus](../jobs/#dqojobstatus)|


___  

## SynchronizeMultipleFoldersQueueJobResult  
Object returned from the operation that queues a &quot;synchronize multiple folders&quot; job. The result contains the job id that was started
 and optionally can also contain the job finish status if the operation was started with wait&#x3D;true parameter to wait for the &quot;synchronize multiple folders&quot; job to finish.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[job_id](../#dqoqueuejobid)|Job id that identifies a job that was started on the DQOps job queue.|[DqoQueueJobId](../#dqoqueuejobid)|
|[status](../jobs/#dqojobstatus)|Job status|[DqoJobStatus](../jobs/#dqojobstatus)|


___  

