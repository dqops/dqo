
## RuleThresholdsModel  
Model that returns the form definition and the form data to edit a single rule with all three threshold levels (low, medium, high).  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[error](\docs\client\models\#ruleparametersmodel)|Rule parameters for the error severity rule.|[RuleParametersModel](\docs\client\models\#ruleparametersmodel)|
|[warning](\docs\client\models\#ruleparametersmodel)|Rule parameters for the warning severity rule.|[RuleParametersModel](\docs\client\models\#ruleparametersmodel)|
|[fatal](\docs\client\models\#ruleparametersmodel)|Rule parameters for the fatal severity rule.|[RuleParametersModel](\docs\client\models\#ruleparametersmodel)|


___  

## CommentsListSpec  
List of comments.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|mod_count||integer|


___  

## CheckTargetModel  
Enumeration of possible targets for check model request result.  
  

**The structure of this object is described below**  
  

|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|column<br/>table<br/>|

___  

## CheckModel  
Model that returns the form definition and the form data to edit a single data quality check.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_name|Data quality check name that is used in YAML.|string|
|help_text|Help text that describes the data quality check.|string|
|sensor_name|Full sensor name. This field is for information purposes and could be used to create additional custom checks that are reusing the same data quality sensor.|string|
|quality_dimension|Data quality dimension used for tagging the results of this data quality checks.|string|
|[rule](\docs\client\models\#rulethresholdsmodel)|Threshold (alerting) rules defined for a check.|[RuleThresholdsModel](\docs\client\models\#rulethresholdsmodel)|
|supports_grouping|The data quality check supports a custom data grouping configuration.|boolean|
|[data_grouping_override](\docs\reference\yaml\connectionyaml\#datagroupingconfigurationspec)|Data grouping configuration for this check. When a data grouping configuration is assigned at a check level, it overrides the data grouping configuration from the table level. Data grouping is configured in two cases: (1) the data in the table should be analyzed with a GROUP BY condition, to analyze different groups of rows using separate time series, for example a table contains data from multiple countries and there is a &#x27;country&#x27; column used for partitioning. (2) a static data grouping configuration is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). |[DataGroupingConfigurationSpec](\docs\reference\yaml\connectionyaml\#datagroupingconfigurationspec)|
|[schedule_override](\docs\client\models\#monitoringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[MonitoringScheduleSpec](\docs\client\models\#monitoringschedulespec)|
|[effective_schedule](\docs\client\models\#effectiveschedulemodel)|Model of configured schedule enabled on the check level.|[EffectiveScheduleModel](\docs\client\models\#effectiveschedulemodel)|
|[schedule_enabled_status](\docs\client\models\#scheduleenabledstatusmodel)|State of the scheduling override for this check.|[ScheduleEnabledStatusModel](\docs\client\models\#scheduleenabledstatusmodel)|
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)|
|disabled|Disables the data quality check. Only enabled checks are executed. The sensor should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean|
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean|
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean|
|configured|True if the data quality check is configured (not null). When saving the data quality check configuration, set the flag to true for storing the check.|boolean|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string|
|[run_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to start the job.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|[data_clean_job_template](\docs\client\models\#deletestoreddataqueuejobparameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this check.|[DeleteStoredDataQueueJobParameters](\docs\client\models\#deletestoreddataqueuejobparameters)|
|data_grouping_configuration|The name of a data grouping configuration defined at a table that should be used for this check.|string|
|[check_target](\docs\client\models\#checktargetmodel)|Type of the check&#x27;s target (column, table).|[CheckTargetModel](\docs\client\models\#checktargetmodel)|
|configuration_requirements_errors|List of configuration errors that must be fixed before the data quality check could be executed.|string_list|
|can_edit|Boolean flag that decides if the current user can edit the check.|boolean|
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean|
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean|


___  

## AllChecksPatchParameters  
  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_search_filters](\docs\client\models\#checksearchfilters)|Filters addressing basic tree search parameters. These filters takes precedence over other selectors.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|[check_model_patch](\docs\client\models\#checkmodel)|Sample configured check model which will pasted onto selected checks.|[CheckModel](\docs\client\models\#checkmodel)|
|override_conflicts|Override existing configurations if they&#x27;re present. If false, apply updates only to the fields for which no configuration exists.|boolean|


___  

## BulkCheckDisableParameters  
  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_search_filters](\docs\client\models\#checksearchfilters)|Filters addressing basic tree search parameters. These filters takes precedence over other selectors.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|


___  

## CommonColumnModel  
Dictionary model used for combo boxes to select a column. Returns a column name that exists in any table within a connection (source)
 and a count of the column occurrence. It is used to find the most common columns.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|column_name|Column name.|string|
|tables_count|Count of tables that are have a column with this name.|integer|


___  

## ConnectionSpecificationModel  
Connection model returned by the rest api.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|connection_hash|Connection hash that identifies the connection using a unique hash code.|long|
|[spec](\docs\reference\yaml\connectionyaml\#connectionspec)|Full connection specification, including all nested objects (but not a list of tables).|[ConnectionSpec](\docs\reference\yaml\connectionyaml\#connectionspec)|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|


___  

