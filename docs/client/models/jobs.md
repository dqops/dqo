---
title: DQOps REST API jobs models reference
---
# DQOps REST API jobs models reference
The references of all objects used by [jobs](../operations/jobs.md) REST API operations are listed below.


## CollectStatisticsResult



**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`executed_statistics_collectors`</span>|The total count of all executed statistics collectors.|*integer*|
|<span class="no-wrap-code">`total_collectors_executed`</span>|The count of executed statistics collectors.|*integer*|
|<span class="no-wrap-code">`columns_analyzed`</span>|The count of columns for which DQOps executed a collector and tried to read the statistics.|*integer*|
|<span class="no-wrap-code">`columns_successfully_analyzed`</span>|The count of columns for which DQOps managed to obtain statistics.|*integer*|
|<span class="no-wrap-code">`total_collectors_failed`</span>|The count of statistics collectors that failed to execute.|*integer*|
|<span class="no-wrap-code">`total_collected_results`</span>|The total number of results that were collected.|*integer*|


___

## DqoJobStatus
Job status of a job on the queue.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|queued<br/>running<br/>waiting<br/>finished<br/>failed<br/>cancel_requested<br/>cancelled<br/>|

___

## CollectStatisticsQueueJobResult



**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`job_id`](./common.md#dqoqueuejobid)</span>|Job id that identifies a job that was started on the DQOps job queue.|*[DqoQueueJobId](./common.md#dqoqueuejobid)*|
|<span class="no-wrap-code">[`result`](#collectstatisticsresult)</span>|Optional result object that is returned only when the wait parameter was true and the "collect statistics" job has finished. Contains the summary result of collecting basic statistics, including the number of statistics collectors (queries) that managed to capture metrics about the table(s). |*[CollectStatisticsResult](#collectstatisticsresult)*|
|<span class="no-wrap-code">[`status`](#dqojobstatus)</span>|Job status|*[DqoJobStatus](#dqojobstatus)*|


___

## DeleteStoredDataQueueJobParameters
Parameters for the &quot;delete stored data* queue job that deletes data from parquet files stored in DQOps user home&#x27;s *.data* directory.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection`</span>|The connection name.|*string*|
|<span class="no-wrap-code">`full_table_name`</span>|The schema and table name. It is provided as *<schema_name>.<table_name>*, for example *public.fact_sales*. This filter does not support patterns.|*string*|
|<span class="no-wrap-code">`date_start`</span>|The start date (inclusive) to delete the data, based on the *time_period* column in Parquet files.|*date*|
|<span class="no-wrap-code">`date_end`</span>|The end date (inclusive) to delete the data, based on the *time_period* column in Parquet files.|*date*|
|<span class="no-wrap-code">`delete_errors`</span>|Delete the data from the [errors](../../reference/parquetfiles/errors.md) table. Because the default value is *false*, this parameter must be set to *true* to delete the errors.|*boolean*|
|<span class="no-wrap-code">`delete_statistics`</span>|Delete the data from the [statistics](../../reference/parquetfiles/statistics.md) table. Because the default value is *false*, this parameter must be set to *true* to delete the statistics.|*boolean*|
|<span class="no-wrap-code">`delete_check_results`</span>|Delete the data from the [check_results](../../reference/parquetfiles/check_results.md) table. Because the default value is *false*, this parameter must be set to *true* to delete the check results.|*boolean*|
|<span class="no-wrap-code">`delete_sensor_readouts`</span>|Delete the data from the [sensor_readouts](../../reference/parquetfiles/sensor_readouts.md) table. Because the default value is *false*, this parameter must be set to *true* to delete the sensor readouts.|*boolean*|
|<span class="no-wrap-code">`column_names`</span>|The list of column names to delete the data for column level results or errors only for selected columns.|*List[string]*|
|<span class="no-wrap-code">`check_category`</span>|The check category name, for example *volume* or *anomaly*.|*string*|
|<span class="no-wrap-code">`table_comparison_name`</span>|The name of a table comparison configuration. Deletes only table comparison results (and errors) for a given comparison.|*string*|
|<span class="no-wrap-code">`check_name`</span>|The name of a data quality check. Uses the short check name, for example *daily_row_count*.|*string*|
|<span class="no-wrap-code">`check_type`</span>|The type of checks whose results and errors should be deleted. For example, use *monitoring* to delete only monitoring checks data.|*string*|
|<span class="no-wrap-code">`sensor_name`</span>|The full sensor name whose results, checks based on the sensor, statistics and errors generated by the sensor sound be deleted. Uses a full sensor name, for example: *table/volume/row_count*.|*string*|
|<span class="no-wrap-code">`data_group_tag`</span>|The names of data groups in any of the *grouping_level_1*...*grouping_level_9* columns in the Parquet tables. Enables deleting data tagged for one data source or a subset of results when the group level is captured from a column in a monitored table.|*string*|
|<span class="no-wrap-code">`quality_dimension`</span>|The data quality dimension name, for example *Timeliness* or *Completeness*.|*string*|
|<span class="no-wrap-code">`time_gradient`</span>|The time gradient (time scale) of the sensor and check results that are captured.|*string*|
|<span class="no-wrap-code">`collector_category`</span>|The statistics collector category when statistics should be deleted. A statistics category is a group of statistics, for example *sampling* for the column value samples.|*string*|
|<span class="no-wrap-code">`collector_name`</span>|The statistics collector name when only statistics are deleted for a selected collector, for example *sample_values*.|*string*|
|<span class="no-wrap-code">`collector_target`</span>|The type of the target object for which the basic statistics are deleted. Supported values are *table* and *column*.|*string*|


___

## DqoRoot
DQOps root folders in the dqo use home that may be replicated to a remote file system (uploaded to DQOps Cloud or any other cloud).
 It is also used as a lock scope.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|data_sensor_readouts<br/>data_check_results<br/>data_statistics<br/>data_errors<br/>data_incidents<br/>sources<br/>sensors<br/>rules<br/>checks<br/>settings<br/>credentials<br/>dictionaries<br/>patterns<br/>_indexes<br/>_local_settings<br/>|

___

## ParquetPartitionId
Identifies a single partition for hive partitioned tables stored as parquet files.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`data_domain`</span>|Data domain name.|*string*|
|<span class="no-wrap-code">[`table_type`](#dqoroot)</span>|Table type.|*[DqoRoot](#dqoroot)*|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">[`table_name`](./common.md#physicaltablename)</span>|Table name (schema.table).|*[PhysicalTableName](./common.md#physicaltablename)*|
|<span class="no-wrap-code">`month`</span>|The date of teh first day of the month that identifies a monthly partition.|*date*|


___

## DataDeleteResultPartition
Results of the &quot;data delete&quot; job for the monthly partition.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`rows_affected_count`</span>|The number of rows that were deleted from the partition.|*integer*|
|<span class="no-wrap-code">`partition_deleted`</span>|True if a whole partition (a parquet file) was deleted instead of removing only selected rows.|*boolean*|


___

## DeleteStoredDataResult
Compiled results of the &quot;data delete&quot;.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`partition_results`</span>|Dictionary of partitions that where deleted or updated when the rows were deleted.|*Dict[[ParquetPartitionId](#parquetpartitionid), [DataDeleteResultPartition](#datadeleteresultpartition)]*|


___

## DeleteStoredDataQueueJobResult
Object returned from the operation that queues a &quot;delete stored data&quot; job. The result contains the job id that was started
 and optionally can also contain a dictionary of partitions that were cleared or deleted if the operation was started with wait&#x3D;true parameter to wait for the &quot;delete stored data&quot; job to finish.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`job_id`](./common.md#dqoqueuejobid)</span>|Job id that identifies a job that was started on the DQOps job queue.|*[DqoQueueJobId](./common.md#dqoqueuejobid)*|
|<span class="no-wrap-code">[`result`](#deletestoreddataresult)</span>|Optional result object that is returned only when the wait parameter was true and the "delete stored data" job has finished. Contains a list of partitions that were deleted or updated.|*[DeleteStoredDataResult](#deletestoreddataresult)*|
|<span class="no-wrap-code">[`status`](./jobs.md#dqojobstatus)</span>|Job status|*[DqoJobStatus](./jobs.md#dqojobstatus)*|


___

## DqoJobType
Job type that identifies a job by type.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|run_checks<br/>run_checks_on_table<br/>collect_statistics<br/>collect_statistics_on_table<br/>queue_thread_shutdown<br/>synchronize_folder<br/>synchronize_multiple_folders<br/>run_scheduled_checks_cron<br/>import_schema<br/>import_tables<br/>delete_stored_data<br/>repair_stored_data<br/>|

___

## FileSynchronizationDirection
Data synchronization direction between a local DQOps Home and DQOps Cloud data quality data warehouse.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|full<br/>download<br/>upload<br/>|

___

## SynchronizeRootFolderParameters
Parameter object for starting a file synchronization job. Identifies the folder and direction that should be synchronized.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`folder`](./jobs.md#dqoroot)</span>|:mm|*[DqoRoot](./jobs.md#dqoroot)*|
|<span class="no-wrap-code">[`direction`](#filesynchronizationdirection)</span>|:mm|*[FileSynchronizationDirection](#filesynchronizationdirection)*|
|<span class="no-wrap-code">`force_refresh_native_table`</span>|:mm|*boolean*|


___

## SynchronizeRootFolderDqoQueueJobParameters
Parameters object for a job that synchronizes one folder with DQOps Cloud.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`synchronization_parameter`](#synchronizerootfolderparameters)</span>|:mm|*[SynchronizeRootFolderParameters](#synchronizerootfolderparameters)*|


___

## SynchronizeMultipleFoldersDqoQueueJobParameters
Simple object for starting multiple folder synchronization jobs with the same configuration.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`direction`](./jobs.md#filesynchronizationdirection)</span>|File synchronization direction, the default is full synchronization (push local changes and pull other changes from DQOps Cloud).|*[FileSynchronizationDirection](./jobs.md#filesynchronizationdirection)*|
|<span class="no-wrap-code">`force_refresh_native_tables`</span>|Force full refresh of native tables in the data quality data warehouse. The default synchronization mode is to refresh only modified data.|*boolean*|
|<span class="no-wrap-code">`detect_cron_schedules`</span>|Scans the yaml files (with the configuration for connections and tables) and detects new cron schedules. Detected cron schedules are registered in the cron (Quartz) job scheduler.|*boolean*|
|<span class="no-wrap-code">`sources`</span>|Synchronize the "sources" folder.|*boolean*|
|<span class="no-wrap-code">`sensors`</span>|Synchronize the "sensors" folder.|*boolean*|
|<span class="no-wrap-code">`rules`</span>|Synchronize the "rules" folder.|*boolean*|
|<span class="no-wrap-code">`checks`</span>|Synchronize the "checks" folder.|*boolean*|
|<span class="no-wrap-code">`settings`</span>|Synchronize the "settings" folder.|*boolean*|
|<span class="no-wrap-code">`credentials`</span>|Synchronize the ".credentials" folder.|*boolean*|
|<span class="no-wrap-code">`dictionaries`</span>|Synchronize the "dictionaries" folder.|*boolean*|
|<span class="no-wrap-code">`patterns`</span>|Synchronize the "patterns" folder.|*boolean*|
|<span class="no-wrap-code">`data_sensor_readouts`</span>|Synchronize the ".data/sensor_readouts" folder.|*boolean*|
|<span class="no-wrap-code">`data_check_results`</span>|Synchronize the ".data/check_results" folder.|*boolean*|
|<span class="no-wrap-code">`data_statistics`</span>|Synchronize the ".data/statistics" folder.|*boolean*|
|<span class="no-wrap-code">`data_errors`</span>|Synchronize the ".data/errors" folder.|*boolean*|
|<span class="no-wrap-code">`data_incidents`</span>|Synchronize the ".data/incidents" folder.|*boolean*|
|<span class="no-wrap-code">`synchronize_folder_with_local_changes`</span>|Synchronize all folders that have local changes. When this field is set to true, there is no need to enable synchronization of single folders because DQOps will decide which folders need synchronization (to be pushed to the cloud).|*boolean*|


___

## TimeWindowFilterParameters



**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`daily_partitioning_recent_days`</span>|The number of recent days to analyze incrementally by daily partitioned data quality checks.|*integer*|
|<span class="no-wrap-code">`daily_partitioning_include_today`</span>|Analyze also today and later days when running daily partitioned checks. By default, daily partitioned checks will not analyze today and future dates. Setting true will disable filtering the end dates.|*boolean*|
|<span class="no-wrap-code">`monthly_partitioning_recent_months`</span>|The number of recent months to analyze incrementally by monthly partitioned data quality checks.|*integer*|
|<span class="no-wrap-code">`monthly_partitioning_include_current_month`</span>|Analyze also the current month and later months when running monthly partitioned checks. By default, monthly partitioned checks will not analyze the current month and future months. Setting true will disable filtering the end dates.|*boolean*|
|<span class="no-wrap-code">`from_date`</span>|Analyze the data since the given date (inclusive). The date should be an ISO 8601 date (yyyy-MM-dd). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date overrides recent days and recent months.|*date*|
|<span class="no-wrap-code">`from_date_time`</span>|Analyze the data since the given date and time (inclusive). The date and time should be an ISO 8601 local date and time without the time zone (yyyy-MM-dd HH\:mm:ss). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the beginning date and time overrides recent days and recent months.|*datetime*|
|<span class="no-wrap-code">`to_date`</span>|Analyze the data until the given date (exclusive, the given date and the following dates are not analyzed). The date should be an ISO 8601 date (YYYY-MM-DD). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date overrides the parameters to disable analyzing today or the current month.|*date*|
|<span class="no-wrap-code">`to_date_time`</span>|Analyze the data until the given date and time (exclusive). The date should be an ISO 8601 date (yyyy-MM-dd). The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. Setting the end date and time overrides the parameters to disable analyzing today or the current month.|*datetime*|


___

## RunChecksResult



**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`highest_severity`](./check_results.md#ruleseveritylevel)</span>|The highest check severity for the data quality checks executed in this batch.|*[RuleSeverityLevel](./check_results.md#ruleseveritylevel)*|
|<span class="no-wrap-code">`executed_checks`</span>|The total count of all executed checks.|*integer*|
|<span class="no-wrap-code">`valid_results`</span>|The total count of all checks that finished successfully (with no data quality issues).|*integer*|
|<span class="no-wrap-code">`warnings`</span>|The total count of all invalid data quality checks that finished raising a warning.|*integer*|
|<span class="no-wrap-code">`errors`</span>|The total count of all invalid data quality checks that finished raising an error.|*integer*|
|<span class="no-wrap-code">`fatals`</span>|The total count of all invalid data quality checks that finished raising a fatal error.|*integer*|
|<span class="no-wrap-code">`execution_errors`</span>|The total number of checks that failed to execute due to some execution errors.|*integer*|


___

## RunChecksParameters



**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_search_filters`](./common.md#checksearchfilters)</span>|Target data quality checks filter.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`time_window_filter`](#timewindowfilterparameters)</span>|Optional time window filter, configures the time range that is analyzed or the number of recent days/months to analyze for day or month partitioned data.|*[TimeWindowFilterParameters](#timewindowfilterparameters)*|
|<span class="no-wrap-code">`dummy_execution`</span>|Set the value to true when the data quality checks should be executed in a dummy mode (without running checks on the target systems and storing the results). Only the jinja2 sensors will be rendered.|*boolean*|
|<span class="no-wrap-code">[`run_checks_result`](#runchecksresult)</span>|The result of running the check, updated when the run checks job finishes. Contains the count of executed checks.|*[RunChecksResult](#runchecksresult)*|


___

## RunChecksOnTableParameters



**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection`</span>|The name of the target connection.|*string*|
|<span class="no-wrap-code">`max_jobs_per_connection`</span>|The maximum number of concurrent 'run checks on table' jobs that can be run on this connection. Limits the number of concurrent jobs.|*integer*|
|<span class="no-wrap-code">[`table`](./common.md#physicaltablename)</span>|The full physical name (schema.table) of the target table.|*[PhysicalTableName](./common.md#physicaltablename)*|
|<span class="no-wrap-code">[`check_search_filters`](./common.md#checksearchfilters)</span>|Target data quality checks filter.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`time_window_filter`](./jobs.md#timewindowfilterparameters)</span>|Optional time window filter, configures the time range that is analyzed or the number of recent days/months to analyze for day or month partitioned data.|*[TimeWindowFilterParameters](./jobs.md#timewindowfilterparameters)*|
|<span class="no-wrap-code">`dummy_execution`</span>|Set the value to true when the data quality checks should be executed in a dummy mode (without running checks on the target systems and storing the results). Only the jinja2 sensors will be rendered.|*boolean*|
|<span class="no-wrap-code">[`run_checks_result`](./jobs.md#runchecksresult)</span>|The result of running the check, updated when the run checks job finishes. Contains the count of executed checks.|*[RunChecksResult](./jobs.md#runchecksresult)*|


___

## StatisticsCollectorTarget



**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|table<br/>column<br/>|

___

## StatisticsCollectorSearchFilters
Hierarchy node search filters for finding enabled statistics collectors (basic profilers) to be started.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`column_names`</span>|The list of column names or column name patters. This field accepts search patterns in the format: 'fk_\*', '\*_id', 'prefix\*suffix'.|*List[string]*|
|<span class="no-wrap-code">`collector_name`</span>|The target statistics collector name to capture only selected statistics. Uses the short collector nameThis field supports search patterns such as: 'prefix\*', '\*suffix', 'prefix_\*_suffix'. In order to collect only top 10 most common column samples, use 'column_samples'.|*string*|
|<span class="no-wrap-code">`sensor_name`</span>|The target sensor name to run only data quality checks that are using this sensor. Uses the full sensor name which is the full folder path within the *sensors* folder. This field supports search patterns such as: 'table/volume/row_\*', '\*_count', 'table/volume/prefix_\*_suffix'.|*string*|
|<span class="no-wrap-code">`collector_category`</span>|The target statistics collector category, for example: *nulls*, *volume*, *sampling*.|*string*|
|<span class="no-wrap-code">[`target`](#statisticscollectortarget)</span>|The target type of object to collect statistics from. Supported values are: *table* to collect only table level statistics or *column* to collect only column level statistics.|*[StatisticsCollectorTarget](#statisticscollectortarget)*|
|<span class="no-wrap-code">`connection`</span>|The connection (data source) name. Supports search patterns in the format: 'source\*', '\*_prod', 'prefix\*suffix'.|*string*|
|<span class="no-wrap-code">`full_table_name`</span>|The schema and table name. It is provided as *<schema_name>.<table_name>*, for example *public.fact_sales*. The schema and table name accept patterns both in the schema name and table name parts. Sample patterns are: 'schema_name.tab_prefix_\*', 'schema_name.*', '*.*', 'schema_name.\*_customer', 'schema_name.tab_\*_suffix'.|*string*|
|<span class="no-wrap-code">`enabled`</span>|A boolean flag to target enabled tables, columns or checks. When the value of this field is not set, the default value of this field is *true*, targeting only tables, columns and checks that are not implicitly disabled.|*boolean*|
|<span class="no-wrap-code">`max_results`</span>|Optional limit for the maximum number of results to return.|*integer*|


___

## StatisticsDataScope
Enumeration of possible statistics scopes. &quot;table&quot; - a whole table was profiled, &quot;data_groupings&quot; - groups of rows were profiled.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|table<br/>data_group<br/>|

___

## CollectStatisticsQueueJobParameters



**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`statistics_collector_search_filters`](./jobs.md#statisticscollectorsearchfilters)</span>|Statistics collectors search filters that identify the type of statistics collector to run.|*[StatisticsCollectorSearchFilters](./jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">[`data_scope`](#statisticsdatascope)</span>|The target scope of collecting statistics. Statistics can be collected for the entire table or for each data grouping separately.|*[StatisticsDataScope](#statisticsdatascope)*|
|<span class="no-wrap-code">`dummy_sensor_execution`</span>|Boolean flag that enables a dummy statistics collection (sensors are executed, but the statistics results are not written to the parquet files).|*boolean*|
|<span class="no-wrap-code">[`collect_statistics_result`](./jobs.md#collectstatisticsresult)</span>|The summary of the statistics collection job after if finished. Returns the number of collectors analyzed, columns analyzed, statistics results captured.|*[CollectStatisticsResult](./jobs.md#collectstatisticsresult)*|


___

## CollectStatisticsOnTableQueueJobParameters



**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection`</span>|The name of the target connection.|*string*|
|<span class="no-wrap-code">`max_jobs_per_connection`</span>|The maximum number of concurrent 'run checks on table' jobs that can be run on this connection. Limits the number of concurrent jobs.|*integer*|
|<span class="no-wrap-code">[`table`](./common.md#physicaltablename)</span>|The full physical name (schema.table) of the target table.|*[PhysicalTableName](./common.md#physicaltablename)*|
|<span class="no-wrap-code">[`statistics_collector_search_filters`](./jobs.md#statisticscollectorsearchfilters)</span>|Statistics collectors search filters that identify the type of statistics collector to run.|*[StatisticsCollectorSearchFilters](./jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">[`data_scope`](./jobs.md#statisticsdatascope)</span>|The target scope of collecting statistics. Statistics can be collected for the entire table or for each data grouping separately.|*[StatisticsDataScope](./jobs.md#statisticsdatascope)*|
|<span class="no-wrap-code">`dummy_sensor_execution`</span>|Boolean flag that enables a dummy statistics collection (sensors are executed, but the statistics results are not written to the parquet files).|*boolean*|
|<span class="no-wrap-code">[`collect_statistics_result`](./jobs.md#collectstatisticsresult)</span>|The summary of the statistics collection job after if finished. Returns the number of collectors analyzed, columns analyzed, statistics results captured.|*[CollectStatisticsResult](./jobs.md#collectstatisticsresult)*|


___

## ImportSchemaQueueJobParameters
Parameters for the {@link ImportSchemaQueueJob ImportSchemaQueueJob} job that imports tables from a database.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|:mm|*string*|
|<span class="no-wrap-code">`schema_name`</span>|:mm|*string*|
|<span class="no-wrap-code">`table_name_pattern`</span>|:mm|*string*|


___

## ImportTablesQueueJobParameters
Parameters for the {@link ImportTablesQueueJob ImportTablesQueueJob} job that imports selected tables from the source database.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name|*string*|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|
|<span class="no-wrap-code">`table_names`</span>|Optional list of table names inside the schema. When the list of tables is empty, all tables are imported.|*List[string]*|


___

## RepairStoredDataQueueJobParameters
Parameters for the {@link RepairStoredDataQueueJob RepairStoredDataQueueJob} job that repairs data stored in user&#x27;s &quot;.data&quot; directory.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|:mm|*string*|
|<span class="no-wrap-code">`schema_table_name`</span>|:mm|*string*|
|<span class="no-wrap-code">`repair_errors`</span>|:mm|*boolean*|
|<span class="no-wrap-code">`repair_statistics`</span>|:mm|*boolean*|
|<span class="no-wrap-code">`repair_check_results`</span>|:mm|*boolean*|
|<span class="no-wrap-code">`repair_sensor_readouts`</span>|:mm|*boolean*|


___

## DqoJobEntryParametersModel
Model object returned to UI that has typed fields for each supported job parameter type.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`synchronize_root_folder_parameters`](#synchronizerootfolderdqoqueuejobparameters)</span>|:mm|*[SynchronizeRootFolderDqoQueueJobParameters](#synchronizerootfolderdqoqueuejobparameters)*|
|<span class="no-wrap-code">[`synchronize_multiple_folders_parameters`](./jobs.md#synchronizemultiplefoldersdqoqueuejobparameters)</span>|:mm|*[SynchronizeMultipleFoldersDqoQueueJobParameters](./jobs.md#synchronizemultiplefoldersdqoqueuejobparameters)*|
|<span class="no-wrap-code">[`run_scheduled_checks_parameters`](./common.md#monitoringschedulespec)</span>|:mm|*[MonitoringScheduleSpec](./common.md#monitoringschedulespec)*|
|<span class="no-wrap-code">[`run_checks_parameters`](./jobs.md#runchecksparameters)</span>|:mm|*[RunChecksParameters](./jobs.md#runchecksparameters)*|
|<span class="no-wrap-code">[`run_checks_on_table_parameters`](#runchecksontableparameters)</span>|:mm|*[RunChecksOnTableParameters](#runchecksontableparameters)*|
|<span class="no-wrap-code">[`collect_statistics_parameters`](#collectstatisticsqueuejobparameters)</span>|:mm|*[CollectStatisticsQueueJobParameters](#collectstatisticsqueuejobparameters)*|
|<span class="no-wrap-code">[`collect_statistics_on_table_parameters`](#collectstatisticsontablequeuejobparameters)</span>|:mm|*[CollectStatisticsOnTableQueueJobParameters](#collectstatisticsontablequeuejobparameters)*|
|<span class="no-wrap-code">[`import_schema_parameters`](#importschemaqueuejobparameters)</span>|:mm|*[ImportSchemaQueueJobParameters](#importschemaqueuejobparameters)*|
|<span class="no-wrap-code">[`import_table_parameters`](./jobs.md#importtablesqueuejobparameters)</span>|:mm|*[ImportTablesQueueJobParameters](./jobs.md#importtablesqueuejobparameters)*|
|<span class="no-wrap-code">[`delete_stored_data_parameters`](./jobs.md#deletestoreddataqueuejobparameters)</span>|:mm|*[DeleteStoredDataQueueJobParameters](./jobs.md#deletestoreddataqueuejobparameters)*|
|<span class="no-wrap-code">[`repair_stored_data_parameters`](#repairstoreddataqueuejobparameters)</span>|:mm|*[RepairStoredDataQueueJobParameters](#repairstoreddataqueuejobparameters)*|


___

## DqoJobHistoryEntryModel
Model of a single job that was scheduled or has finished. It is stored in the job monitoring service on the history list.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`job_id`](./common.md#dqoqueuejobid)</span>|:mm|*[DqoQueueJobId](./common.md#dqoqueuejobid)*|
|<span class="no-wrap-code">[`job_type`](#dqojobtype)</span>|:mm|*[DqoJobType](#dqojobtype)*|
|<span class="no-wrap-code">[`parameters`](#dqojobentryparametersmodel)</span>|:mm|*[DqoJobEntryParametersModel](#dqojobentryparametersmodel)*|
|<span class="no-wrap-code">[`status`](./jobs.md#dqojobstatus)</span>|:mm|*[DqoJobStatus](./jobs.md#dqojobstatus)*|
|<span class="no-wrap-code">`error_message`</span>|:mm|*string*|


___

## DqoJobChangeModel
Describes a change to the job status or the job queue (such as a new job was added).


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`status`](./jobs.md#dqojobstatus)</span>|:mm|*[DqoJobStatus](./jobs.md#dqojobstatus)*|
|<span class="no-wrap-code">[`job_id`](./common.md#dqoqueuejobid)</span>|:mm|*[DqoQueueJobId](./common.md#dqoqueuejobid)*|
|<span class="no-wrap-code">`change_sequence`</span>|:mm|*long*|
|<span class="no-wrap-code">[`updated_model`](./jobs.md#dqojobhistoryentrymodel)</span>|:mm|*[DqoJobHistoryEntryModel](./jobs.md#dqojobhistoryentrymodel)*|


___

## FolderSynchronizationStatus
Enumeration of statuses identifying the synchronization status for each folder that can be synchronized with the DQOps cloud.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|unchanged<br/>changed<br/>synchronizing<br/>|

___

## CloudSynchronizationFoldersStatusModel
Model that describes the current synchronization status for each folder.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`sources`](#foldersynchronizationstatus)</span>|The synchronization status of the "sources" folder.|*[FolderSynchronizationStatus](#foldersynchronizationstatus)*|
|<span class="no-wrap-code">[`sensors`](./jobs.md#foldersynchronizationstatus)</span>|The synchronization status of the "sensors" folder.|*[FolderSynchronizationStatus](./jobs.md#foldersynchronizationstatus)*|
|<span class="no-wrap-code">[`rules`](./jobs.md#foldersynchronizationstatus)</span>|The synchronization status of the "rules" folder.|*[FolderSynchronizationStatus](./jobs.md#foldersynchronizationstatus)*|
|<span class="no-wrap-code">[`checks`](./jobs.md#foldersynchronizationstatus)</span>|The synchronization status of the "checks" folder.|*[FolderSynchronizationStatus](./jobs.md#foldersynchronizationstatus)*|
|<span class="no-wrap-code">[`settings`](./jobs.md#foldersynchronizationstatus)</span>|The synchronization status of the "settings" folder.|*[FolderSynchronizationStatus](./jobs.md#foldersynchronizationstatus)*|
|<span class="no-wrap-code">[`credentials`](./jobs.md#foldersynchronizationstatus)</span>|The synchronization status of the ".credentials" folder.|*[FolderSynchronizationStatus](./jobs.md#foldersynchronizationstatus)*|
|<span class="no-wrap-code">[`dictionaries`](./jobs.md#foldersynchronizationstatus)</span>|The synchronization status of the "dictionaries" folder.|*[FolderSynchronizationStatus](./jobs.md#foldersynchronizationstatus)*|
|<span class="no-wrap-code">[`patterns`](./jobs.md#foldersynchronizationstatus)</span>|The synchronization status of the "patterns" folder.|*[FolderSynchronizationStatus](./jobs.md#foldersynchronizationstatus)*|
|<span class="no-wrap-code">[`data_sensor_readouts`](./jobs.md#foldersynchronizationstatus)</span>|The synchronization status of the ".data/sensor_readouts" folder.|*[FolderSynchronizationStatus](./jobs.md#foldersynchronizationstatus)*|
|<span class="no-wrap-code">[`data_check_results`](./jobs.md#foldersynchronizationstatus)</span>|The synchronization status of the ".data/check_results" folder.|*[FolderSynchronizationStatus](./jobs.md#foldersynchronizationstatus)*|
|<span class="no-wrap-code">[`data_statistics`](./jobs.md#foldersynchronizationstatus)</span>|The synchronization status of the ".data/statistics" folder.|*[FolderSynchronizationStatus](./jobs.md#foldersynchronizationstatus)*|
|<span class="no-wrap-code">[`data_errors`](./jobs.md#foldersynchronizationstatus)</span>|The synchronization status of the ".data/errors" folder.|*[FolderSynchronizationStatus](./jobs.md#foldersynchronizationstatus)*|
|<span class="no-wrap-code">[`data_incidents`](./jobs.md#foldersynchronizationstatus)</span>|The synchronization status of the ".data/incidents" folder.|*[FolderSynchronizationStatus](./jobs.md#foldersynchronizationstatus)*|


___

## DqoJobQueueIncrementalSnapshotModel
Job history snapshot model that returns only changes after a given change sequence.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`job_changes`</span>|:mm|*List[[DqoJobChangeModel](#dqojobchangemodel)]*|
|<span class="no-wrap-code">[`folder_synchronization_status`](#cloudsynchronizationfoldersstatusmodel)</span>|:mm|*[CloudSynchronizationFoldersStatusModel](#cloudsynchronizationfoldersstatusmodel)*|
|<span class="no-wrap-code">`last_sequence_number`</span>|:mm|*long*|


___

## DqoJobQueueInitialSnapshotModel
Returns the current snapshot of running jobs.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`jobs`</span>|:mm|*List[[DqoJobHistoryEntryModel](./jobs.md#dqojobhistoryentrymodel)]*|
|<span class="no-wrap-code">[`folder_synchronization_status`](./jobs.md#cloudsynchronizationfoldersstatusmodel)</span>|:mm|*[CloudSynchronizationFoldersStatusModel](./jobs.md#cloudsynchronizationfoldersstatusmodel)*|
|<span class="no-wrap-code">`last_sequence_number`</span>|:mm|*long*|


___

## ImportTablesResult
Result object from the {@link ImportTablesQueueJob ImportTablesQueueJob} table import job that returns list of tables that have been imported.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`source_table_specs`</span>|Table schemas (including column schemas) of imported tables.|*List[[TableSpec](../../reference/yaml/TableYaml.md#tablespec)]*|


___

## ImportTablesQueueJobResult
Object returned from the operation that queues a &quot;import tables&quot; job. The result contains the job id that was started
 and optionally can also contain the result of importing tables if the operation was started with wait&#x3D;true parameter to wait for the &quot;import tables&quot; job to finish.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`job_id`](./common.md#dqoqueuejobid)</span>|Job id that identifies a job that was started on the DQOps job queue.|*[DqoQueueJobId](./common.md#dqoqueuejobid)*|
|<span class="no-wrap-code">[`result`](#importtablesresult)</span>|Optional result object that is returned only when the wait parameter was true and the "import tables" job has finished. Contains the summary result of importing tables, including table and column schemas of imported tables. |*[ImportTablesResult](#importtablesresult)*|
|<span class="no-wrap-code">[`status`](./jobs.md#dqojobstatus)</span>|Job status|*[DqoJobStatus](./jobs.md#dqojobstatus)*|


___

## RunChecksQueueJobResult



**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`job_id`](./common.md#dqoqueuejobid)</span>|Job id that identifies a job that was started on the DQOps job queue.|*[DqoQueueJobId](./common.md#dqoqueuejobid)*|
|<span class="no-wrap-code">[`result`](./jobs.md#runchecksresult)</span>|Optional result object that is returned only when the wait parameter was true and the "run checks" job has finished. Contains the summary result of the data quality checks executed, including the severity of the most severe issue detected. The calling code (the data pipeline) can decide if further processing should be continued.|*[RunChecksResult](./jobs.md#runchecksresult)*|
|<span class="no-wrap-code">[`status`](./jobs.md#dqojobstatus)</span>|Job status|*[DqoJobStatus](./jobs.md#dqojobstatus)*|


___

## SpringErrorPayload
Object mapped to the default spring error payload (key/values).


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`timestamp`</span>|Error timestamp as an epoch timestamp.|*long*|
|<span class="no-wrap-code">`status`</span>|Optional status code.|*integer*|
|<span class="no-wrap-code">`error`</span>|Error name.|*string*|
|<span class="no-wrap-code">`exception`</span>|Optional exception.|*string*|
|<span class="no-wrap-code">`message`</span>|Exception's message.|*string*|
|<span class="no-wrap-code">`path`</span>|Exception's stack trace (optional).|*string*|


___

## SynchronizeMultipleFoldersQueueJobResult
Object returned from the operation that queues a &quot;synchronize multiple folders&quot; job. The result contains the job id that was started
 and optionally can also contain the job finish status if the operation was started with wait&#x3D;true parameter to wait for the &quot;synchronize multiple folders&quot; job to finish.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`job_id`](./common.md#dqoqueuejobid)</span>|Job id that identifies a job that was started on the DQOps job queue.|*[DqoQueueJobId](./common.md#dqoqueuejobid)*|
|<span class="no-wrap-code">[`status`](./jobs.md#dqojobstatus)</span>|Job status|*[DqoJobStatus](./jobs.md#dqojobstatus)*|


___

