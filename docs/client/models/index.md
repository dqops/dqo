# models

## CheckTarget  
Enumeration of targets where the check is applied. It is one of &quot;table&quot; or &quot;column&quot;.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|column<br/>table<br/>|

___  

## CheckType  
Enumeration of data quality check types: profiling, monitoring, partitioned.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|profiling<br/>partitioned<br/>monitoring<br/>|

___  

## CheckTimeScale  
Enumeration of time scale of monitoring and partitioned data quality checks (daily, monthly, etc.)  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|daily<br/>monthly<br/>|

___  

## RuleParametersModel  
Model that returns the form definition and the form data to edit parameters (thresholds) for a rule at a single severity level (low, medium, high).  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|rule_name|Full rule name. This field is for information purposes and could be used to create additional custom checks that are reusing the same data quality rule.|string|
|disabled|Disable the rule. The rule will not be evaluated. The sensor will also not be executed if it has no enabled rules.|boolean|
|configured|Returns true when the rule is configured (is not null), so it should be shown in the UI as configured (having values).|boolean|


___  

## CheckConfigurationModel  
Model containing fundamental configuration of a single data quality check.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|schema_name|Schema name.|string|
|table_name|Table name.|string|
|column_name|Column name, if the check is set up on a column.|string|
|[check_target](\docs\client\models\#checktarget)|Check target (table or column).|[CheckTarget](\docs\client\models\#checktarget)|
|[check_type](\docs\client\models\#checktype)|Check type (profiling, monitoring, partitioned).|[CheckType](\docs\client\models\#checktype)|
|[check_time_scale](\docs\client\models\#checktimescale)|Category to which this check belongs.|[CheckTimeScale](\docs\client\models\#checktimescale)|
|category_name|Category to which this check belongs.|string|
|check_name|Check name that is used in YAML file.|string|
|table_level_filter|SQL WHERE clause added to the sensor query for every check on this table.|string|
|sensor_level_filter|SQL WHERE clause added to the sensor query for this check.|string|
|[warning](\docs\client\models\#ruleparametersmodel)|Rule parameters for the warning severity rule.|[RuleParametersModel](\docs\client\models\#ruleparametersmodel)|
|[error](\docs\client\models\#ruleparametersmodel)|Rule parameters for the error severity rule.|[RuleParametersModel](\docs\client\models\#ruleparametersmodel)|
|[fatal](\docs\client\models\#ruleparametersmodel)|Rule parameters for the fatal severity rule.|[RuleParametersModel](\docs\client\models\#ruleparametersmodel)|
|disabled|Whether the check has been disabled.|boolean|
|configured|Whether the check is configured (not null).|boolean|


___  

## CheckContainerListModel  
Simplistic model that returns the list of data quality checks, their names, categories and &quot;configured&quot; flag.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|can_edit|Boolean flag that decides if the current user can edit the check.|boolean|
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean|
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean|


___  

## CheckRunScheduleGroup  
The run check scheduling group (profiling, daily checks, monthly checks, etc), which identifies the configuration of a schedule (cron expression) used schedule these checks on the job scheduler.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|monitoring_monthly<br/>profiling<br/>partitioned_daily<br/>monitoring_daily<br/>partitioned_monthly<br/>|

___  

## EffectiveScheduleLevelModel  
Enumeration of possible levels at which a schedule could be configured.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|check_override<br/>connection<br/>table_override<br/>|

___  

## EffectiveScheduleModel  
Model of a configured schedule (on connection or table) or schedule override (on check). Describes the CRON expression and the time of the upcoming execution, as well as the duration until this time.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[schedule_group](\docs\client\models\#checkrunschedulegroup)|Field value for a schedule group to which this schedule belongs.|[CheckRunScheduleGroup](\docs\client\models\#checkrunschedulegroup)|
|[schedule_level](\docs\client\models\#effectiveschedulelevelmodel)|Field value for the level at which the schedule has been configured.|[EffectiveScheduleLevelModel](\docs\client\models\#effectiveschedulelevelmodel)|
|cron_expression|Field value for a CRON expression defining the scheduling.|string|
|disabled|Field value stating if the schedule has been explicitly disabled.|boolean|


___  

## ScheduleEnabledStatusModel  
Enumeration of possible ways a schedule can be configured.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|not_configured<br/>disabled<br/>overridden_by_checks<br/>enabled<br/>|

___  

## CheckSearchFilters  
Hierarchy node search filters.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|column_name||string|
|column_data_type||string|
|column_nullable||boolean|
|[check_target](\docs\client\models\#checktarget)||[CheckTarget](\docs\client\models\#checktarget)|
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

## DeleteStoredDataQueueJobParameters  
Parameters for the {@link DeleteStoredDataQueueJob DeleteStoredDataQueueJob} job that deletes data stored in user&#x27;s &quot;.data&quot; directory.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name||string|
|schema_table_name||string|
|date_start|Data start data|date|
|date_end||date|
|delete_errors||boolean|
|delete_statistics||boolean|
|delete_check_results||boolean|
|delete_sensor_readouts||boolean|
|column_names||string_list|
|check_category||string|
|table_comparison_name||string|
|check_name||string|
|check_type||string|
|sensor_name||string|
|data_group_tag||string|
|quality_dimension||string|
|time_gradient||string|
|collector_category||string|
|collector_name||string|
|collector_target||string|


___  

## CheckContainerModel  
Model that returns the form definition and the form data to edit all data quality checks divided by categories.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[effective_schedule](\docs\client\models\#effectiveschedulemodel)|Model of configured schedule enabled on the check container.|[EffectiveScheduleModel](\docs\client\models\#effectiveschedulemodel)|
|[effective_schedule_enabled_status](\docs\client\models\#scheduleenabledstatusmodel)|State of the effective scheduling on the check container.|[ScheduleEnabledStatusModel](\docs\client\models\#scheduleenabledstatusmodel)|
|partition_by_column|The name of the column that partitioned checks will use for the time period partitioning. Important only for partitioned checks.|string|
|[run_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to start the job.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|[data_clean_job_template](\docs\client\models\#deletestoreddataqueuejobparameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this check container|[DeleteStoredDataQueueJobParameters](\docs\client\models\#deletestoreddataqueuejobparameters)|
|can_edit|Boolean flag that decides if the current user can edit the check.|boolean|
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean|
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean|


___  

## CheckContainerTypeModel  
Model identifying the check type and timescale of checks belonging to a container.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_type](\docs\client\models\#checktype)|Check type.|[CheckType](\docs\client\models\#checktype)|
|[check_time_scale](\docs\client\models\#checktimescale)|Check timescale.|[CheckTimeScale](\docs\client\models\#checktimescale)|


___  

## CheckTemplate  
Model depicting a named data quality check that can potentially be enabled, regardless to its position in hierarchy tree.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_target](\docs\client\models\#checktarget)|Check target (table, column)|[CheckTarget](\docs\client\models\#checktarget)|
|check_category|Data quality check category.|string|
|check_name|Data quality check name that is used in YAML.|string|
|help_text|Help text that describes the data quality check.|string|
|[check_container_type](\docs\client\models\#checkcontainertypemodel)|Check type with time-scale.|[CheckContainerTypeModel](\docs\client\models\#checkcontainertypemodel)|
|sensor_name|Full sensor name.|string|


___  

## CommentSpec  
Comment entry. Comments are added when a change was made and the change should be recorded in a persisted format.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|date|Comment date and time|datetime|
|comment_by|Commented by|string|
|comment|Comment text|string|


___  

## ProviderType  
Data source provider type (dialect type).
  We will use lower case names to avoid issues with parsing, even if the enum names are not named following the Java naming convention.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|snowflake<br/>oracle<br/>postgresql<br/>redshift<br/>sqlserver<br/>mysql<br/>bigquery<br/>|

___  

## StatisticsCollectorTarget  
  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|column<br/>table<br/>|

___  

## StatisticsCollectorSearchFilters  
Hierarchy node search filters for finding enabled statistics collectors (basic profilers) to be started.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|collector_name||string|
|sensor_name||string|
|collector_category||string|
|[target](\docs\client\models\#statisticscollectortarget)||[StatisticsCollectorTarget](\docs\client\models\#statisticscollectortarget)|
|connection_name||string|
|schema_table_name||string|
|enabled||boolean|


___  

## ConnectionModel  
Connection model returned by the rest api that is limited only to the basic fields, excluding nested nodes.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|connection_hash|Connection hash that identifies the connection using a unique hash code.|long|
|parallel_runs_limit|The concurrency limit for the maximum number of parallel SQL queries executed on this connection.|integer|
|[provider_type](\docs\client\models\#providertype)|Database provider type (required). Accepts: bigquery, snowflake.|[ProviderType](\docs\client\models\#providertype)|
|[bigquery](\docs\reference\yaml\connectionyaml\#bigqueryparametersspec)|BigQuery connection parameters. Specify parameters in the bigquery section.|[BigQueryParametersSpec](\docs\reference\yaml\connectionyaml\#bigqueryparametersspec)|
|[snowflake](\docs\reference\yaml\connectionyaml\#snowflakeparametersspec)|Snowflake connection parameters.|[SnowflakeParametersSpec](\docs\reference\yaml\connectionyaml\#snowflakeparametersspec)|
|[postgresql](\docs\reference\yaml\connectionyaml\#postgresqlparametersspec)|PostgreSQL connection parameters.|[PostgresqlParametersSpec](\docs\reference\yaml\connectionyaml\#postgresqlparametersspec)|
|[redshift](\docs\reference\yaml\connectionyaml\#redshiftparametersspec)|Redshift connection parameters.|[RedshiftParametersSpec](\docs\reference\yaml\connectionyaml\#redshiftparametersspec)|
|[sqlserver](\docs\reference\yaml\connectionyaml\#sqlserverparametersspec)|SqlServer connection parameters.|[SqlServerParametersSpec](\docs\reference\yaml\connectionyaml\#sqlserverparametersspec)|
|[mysql](\docs\reference\yaml\connectionyaml\#mysqlparametersspec)|MySQL connection parameters.|[MysqlParametersSpec](\docs\reference\yaml\connectionyaml\#mysqlparametersspec)|
|[oracle](\docs\reference\yaml\connectionyaml\#oracleparametersspec)|Oracle connection parameters.|[OracleParametersSpec](\docs\reference\yaml\connectionyaml\#oracleparametersspec)|
|[run_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run all checks within this connection.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|[run_profiling_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run profiling checks within this connection.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|[run_monitoring_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run monitoring checks within this connection.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|[run_partition_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run partition partitioned checks within this connection.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|[collect_statistics_job_template](\docs\client\models\#statisticscollectorsearchfilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors within this connection.|[StatisticsCollectorSearchFilters](\docs\client\models\#statisticscollectorsearchfilters)|
|[data_clean_job_template](\docs\client\models\#deletestoreddataqueuejobparameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this connection.|[DeleteStoredDataQueueJobParameters](\docs\client\models\#deletestoreddataqueuejobparameters)|
|can_edit|Boolean flag that decides if the current user can update or delete the connection to the data source.|boolean|
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean|
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean|
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean|


___  

## DqoQueueJobId  
Identifies a single job.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|job_id|Job id.|long|
|[parent_job_id](\docs\client\models\#dqoqueuejobid)|Parent job id. Filled only for nested jobs, for example a sub-job that runs data quality checks on a single table.|[DqoQueueJobId](\docs\client\models\#dqoqueuejobid)|


___  

## MonitoringScheduleSpec  
Monitoring job schedule specification.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|cron_expression|Unix style cron expression that specifies when to execute scheduled operations like running data quality checks or synchronizing the configuration with the cloud.|string|
|disabled|Disables the schedule. When the value of this &#x27;disable&#x27; field is false, the schedule is stored in the metadata but it is not activated to run data quality checks.|boolean|


___  

