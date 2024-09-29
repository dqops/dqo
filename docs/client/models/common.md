---
title: DQOps REST API common models reference
---
# DQOps REST API common models reference
The references of all objects used as shared REST API models in all operations are listed below.


## CheckType
Enumeration of data quality check types: profiling, monitoring, partitioned.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|profiling<br/>monitoring<br/>partitioned<br/>|

___

## CheckTimeScale
Enumeration of time scale of monitoring and partitioned data quality checks (daily, monthly, etc.)


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|daily<br/>monthly<br/>|

___

## FieldModel
Model of a single field that is used to edit a parameter value for a sensor or a rule. Describes the type of the field and the current value.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`definition`](../../reference/yaml/SensorDefinitionYaml.md#parameterdefinitionspec)</span>|Field name that matches the field name (snake_case) used in the YAML specification.|*[ParameterDefinitionSpec](../../reference/yaml/SensorDefinitionYaml.md#parameterdefinitionspec)*|
|<span class="no-wrap-code">`optional`</span>|Field value is optional and may be null, when false - the field is required and must be filled.|*boolean*|
|<span class="no-wrap-code">`string_value`</span>|Field value for a string field.|*string*|
|<span class="no-wrap-code">`boolean_value`</span>|Field value for a boolean field.|*boolean*|
|<span class="no-wrap-code">`integer_value`</span>|Field value for an integer (32-bit) field.|*integer*|
|<span class="no-wrap-code">`long_value`</span>|Field value for a long (64-bit) field.|*long*|
|<span class="no-wrap-code">`double_value`</span>|Field value for a double field.|*double*|
|<span class="no-wrap-code">`datetime_value`</span>|Field value for a date time field.|*datetime*|
|<span class="no-wrap-code">`column_name_value`</span>|Field value for a column name field.|*string*|
|<span class="no-wrap-code">`enum_value`</span>|Field value for an enum (choice) field.|*string*|
|<span class="no-wrap-code">`string_list_value`</span>|Field value for an array (list) of strings.|*List[string]*|
|<span class="no-wrap-code">`integer_list_value`</span>|Field value for an array (list) of integers, using 64 bit integers.|*List[integer]*|
|<span class="no-wrap-code">`date_value`</span>|Field value for an date.|*date*|


___

## RuleParametersModel
Model that returns the form definition and the form data to edit parameters (thresholds) for a rule at a single severity level (low, medium, high).


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`rule_name`</span>|Full rule name. This field is for information purposes and can be used to create additional custom checks that reuse the same data quality rule.|*string*|
|<span class="no-wrap-code">`rule_parameters`</span>|List of fields for editing the rule parameters like thresholds.|*List[[FieldModel](./common.md#fieldmodel)]*|
|<span class="no-wrap-code">`disabled`</span>|Disable the rule. The rule will not be evaluated. The sensor will also not be executed if it has no enabled rules.|*boolean*|
|<span class="no-wrap-code">`configured`</span>|Returns true when the rule is configured (is not null), so it should be shown in the UI as configured (having values).|*boolean*|


___

## CheckConfigurationModel
Model containing fundamental configuration of a single data quality check.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">`schema_name`</span>|Schema name.|*string*|
|<span class="no-wrap-code">`table_name`</span>|Table name.|*string*|
|<span class="no-wrap-code">`column_name`</span>|Column name, if the check is set up on a column.|*string*|
|<span class="no-wrap-code">[`check_target`](./schemas.md#checktarget)</span>|Check target (table or column).|*[CheckTarget](./schemas.md#checktarget)*|
|<span class="no-wrap-code">[`check_type`](./common.md#checktype)</span>|Check type (profiling, monitoring, partitioned).|*[CheckType](./common.md#checktype)*|
|<span class="no-wrap-code">[`check_time_scale`](./common.md#checktimescale)</span>|Check timescale (for monitoring and partitioned checks).|*[CheckTimeScale](./common.md#checktimescale)*|
|<span class="no-wrap-code">`category_name`</span>|Category to which this check belongs.|*string*|
|<span class="no-wrap-code">`check_name`</span>|Check name that is used in YAML file.|*string*|
|<span class="no-wrap-code">`sensor_parameters`</span>|List of fields for editing the sensor parameters.|*List[[FieldModel](#fieldmodel)]*|
|<span class="no-wrap-code">`table_level_filter`</span>|SQL WHERE clause added to the sensor query for every check on this table.|*string*|
|<span class="no-wrap-code">`sensor_level_filter`</span>|SQL WHERE clause added to the sensor query for this check.|*string*|
|<span class="no-wrap-code">[`warning`](#ruleparametersmodel)</span>|Rule parameters for the warning severity rule.|*[RuleParametersModel](#ruleparametersmodel)*|
|<span class="no-wrap-code">[`error`](./common.md#ruleparametersmodel)</span>|Rule parameters for the error severity rule.|*[RuleParametersModel](./common.md#ruleparametersmodel)*|
|<span class="no-wrap-code">[`fatal`](./common.md#ruleparametersmodel)</span>|Rule parameters for the fatal severity rule.|*[RuleParametersModel](./common.md#ruleparametersmodel)*|
|<span class="no-wrap-code">`disabled`</span>|Whether the check has been disabled.|*boolean*|
|<span class="no-wrap-code">`configured`</span>|Whether the check is configured (not null).|*boolean*|


___

## CheckListModel
Simplistic model that returns a single data quality check, its name and &quot;configured&quot; flag.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_category`</span>|Check category.|*string*|
|<span class="no-wrap-code">`check_name`</span>|Data quality check name that is used in YAML.|*string*|
|<span class="no-wrap-code">`help_text`</span>|Help text that describes the data quality check.|*string*|
|<span class="no-wrap-code">`configured`</span>|True if the data quality check is configured (not null). When saving the data quality check configuration, set the flag to true for storing the check.|*boolean*|


___

## CheckContainerListModel
Simplistic model that returns the list of data quality checks, their names, categories and &quot;configured&quot; flag.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`checks`</span>|Simplistic list of all data quality checks.|*List[[CheckListModel](#checklistmodel)]*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can edit the check.|*boolean*|
|<span class="no-wrap-code">`can_run_checks`</span>|Boolean flag that decides if the current user can run checks.|*boolean*|
|<span class="no-wrap-code">`can_delete_data`</span>|Boolean flag that decides if the current user can delete data (results).|*boolean*|


___

## RuleThresholdsModel
Model that returns the form definition and the form data to edit a single rule with all three threshold levels (low, medium, high).


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`error`](./common.md#ruleparametersmodel)</span>|Rule parameters for the error severity rule.|*[RuleParametersModel](./common.md#ruleparametersmodel)*|
|<span class="no-wrap-code">[`warning`](./common.md#ruleparametersmodel)</span>|Rule parameters for the warning severity rule.|*[RuleParametersModel](./common.md#ruleparametersmodel)*|
|<span class="no-wrap-code">[`fatal`](./common.md#ruleparametersmodel)</span>|Rule parameters for the fatal severity rule.|*[RuleParametersModel](./common.md#ruleparametersmodel)*|


___

## DefaultRuleSeverityLevel
Default rule severity levels. Matches the severity level name (warning - 1, alert - 2, fatal - 3) with a numeric level.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|none<br/>warning<br/>error<br/>fatal<br/>|

___

## MonitoringScheduleSpec
Monitoring job schedule specification.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`cron_expression`</span>|Unix style cron expression that specifies when to execute scheduled operations like running data quality checks or synchronizing the configuration with the cloud.|*string*|
|<span class="no-wrap-code">`disabled`</span>|Disables the schedule. When the value of this 'disable' field is false, the schedule is stored in the metadata but it is not activated to run data quality checks.|*boolean*|


___

## CheckRunScheduleGroup
The run check scheduling group (profiling, daily checks, monthly checks, etc), which identifies the configuration of a schedule (cron expression) used schedule these checks on the job scheduler.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|profiling<br/>monitoring_daily<br/>monitoring_monthly<br/>partitioned_daily<br/>partitioned_monthly<br/>|

___

## EffectiveScheduleLevelModel
Enumeration of possible levels at which a schedule can be configured.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|connection<br/>table_override<br/>check_override<br/>|

___

## EffectiveScheduleModel
Model of a configured schedule (on connection or table) or schedule override (on check). Describes the CRON expression and the time of the upcoming execution, as well as the duration until this time.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`schedule_group`](./common.md#checkrunschedulegroup)</span>|Field value for a schedule group to which this schedule belongs.|*[CheckRunScheduleGroup](./common.md#checkrunschedulegroup)*|
|<span class="no-wrap-code">[`schedule_level`](#effectiveschedulelevelmodel)</span>|Field value for the level at which the schedule has been configured.|*[EffectiveScheduleLevelModel](#effectiveschedulelevelmodel)*|
|<span class="no-wrap-code">`cron_expression`</span>|Field value for a CRON expression defining the scheduling.|*string*|
|<span class="no-wrap-code">`disabled`</span>|Field value stating if the schedule has been explicitly disabled.|*boolean*|


___

## ScheduleEnabledStatusModel
Enumeration of possible ways a schedule can be configured.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|enabled<br/>disabled<br/>not_configured<br/>overridden_by_checks<br/>|

___

## CommentSpec
Comment entry. Comments are added when a change was made and the change should be recorded in a persisted format.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`date`</span>|Comment date and time|*datetime*|
|<span class="no-wrap-code">`comment_by`</span>|Commented by|*string*|
|<span class="no-wrap-code">`comment`</span>|Comment text|*string*|


___

## CommentsListSpec
List of comments.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`self`</span>||*List[[CommentSpec](./common.md#commentspec)]*|


___

## CheckSearchFilters
Target data quality checks filter, identifies which checks on which tables and columns should be executed.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`column`</span>|The column name. This field accepts search patterns in the format: 'fk_\*', '\*_id', 'prefix\*suffix'.|*string*|
|<span class="no-wrap-code">`column_data_type`</span>|The column data type that was imported from the data source and is stored in the [columns -> column_name -> type_snapshot -> column_type](../../reference/yaml/TableYaml.md#columntypesnapshotspec) field in the *.dqotable.yaml* file.|*string*|
|<span class="no-wrap-code">`column_nullable`</span>|Optional filter to find only nullable (when the value is *true*) or not nullable (when the value is *false*) columns, based on the value of the [columns -> column_name -> type_snapshot -> nullable](../../reference/yaml/TableYaml.md#columntypesnapshotspec) field in the *.dqotable.yaml* file.|*boolean*|
|<span class="no-wrap-code">[`check_target`](./schemas.md#checktarget)</span>|The target type of object to run checks. Supported values are: *table* to run only table level checks or *column* to run only column level checks.|*[CheckTarget](./schemas.md#checktarget)*|
|<span class="no-wrap-code">[`check_type`](./common.md#checktype)</span>|The target type of checks to run. Supported values are *profiling*, *monitoring* and *partitioned*.|*[CheckType](./common.md#checktype)*|
|<span class="no-wrap-code">[`time_scale`](./common.md#checktimescale)</span>|The time scale of *monitoring* or *partitioned* checks to run. Supports running only *daily* or *monthly* checks. Daily monitoring checks will replace today's value for all captured check results.|*[CheckTimeScale](./common.md#checktimescale)*|
|<span class="no-wrap-code">`check_category`</span>|The target check category, for example: *nulls*, *volume*, *anomaly*.|*string*|
|<span class="no-wrap-code">`quality_dimension`</span>|The target data quality dimension, for example: *Completeness*, *Accuracy*, *Consistency*, *Timeliness*, *Availability*.|*string*|
|<span class="no-wrap-code">`table_comparison_name`</span>|The name of a configured table comparison. When the table comparison is provided, DQOps will only perform table comparison checks that compare data between tables.|*string*|
|<span class="no-wrap-code">`check_name`</span>|The target check name to run only this named check. Uses the short check name which is the name of the deepest folder in the *checks* folder. This field supports search patterns such as: 'profiling_\*', '\*_count', 'profiling_\*_percent'.|*string*|
|<span class="no-wrap-code">`sensor_name`</span>|The target sensor name to run only data quality checks that are using this sensor. Uses the full sensor name which is the full folder path within the *sensors* folder. This field supports search patterns such as: 'table/volume/row_\*', '\*_count', 'table/volume/prefix_\*_suffix'.|*string*|
|<span class="no-wrap-code">`connection`</span>|The connection (data source) name. Supports search patterns in the format: 'source\*', '\*_prod', 'prefix\*suffix'.|*string*|
|<span class="no-wrap-code">`full_table_name`</span>|The schema and table name. It is provided as *<schema_name>.<table_name>*, for example *public.fact_sales*. The schema and table name accept patterns both in the schema name and table name parts. Sample patterns are: 'schema_name.tab_prefix_\*', 'schema_name.*', '*.*', 'schema_name.\*_customer', 'schema_name.tab_\*_suffix'.|*string*|
|<span class="no-wrap-code">`enabled`</span>|A boolean flag to target enabled tables, columns or checks. When the value of this field is not set, the default value of this field is *true*, targeting only tables, columns and checks that are not implicitly disabled.|*boolean*|
|<span class="no-wrap-code">`max_results`</span>|Optional limit for the maximum number of results to return.|*integer*|


___

## CheckTargetModel
Enumeration of possible targets for check model request result.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|table<br/>column<br/>|

___

## SimilarCheckModel
Describes a single check that is similar to other checks in other check types.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_target`](./schemas.md#checktarget)</span>|The check target (table or column).|*[CheckTarget](./schemas.md#checktarget)*|
|<span class="no-wrap-code">[`check_type`](./common.md#checktype)</span>|The check type.|*[CheckType](./common.md#checktype)*|
|<span class="no-wrap-code">[`time_scale`](./common.md#checktimescale)</span>|The time scale (daily, monthly). The time scale is optional and can be null (for profiling checks).|*[CheckTimeScale](./common.md#checktimescale)*|
|<span class="no-wrap-code">`category`</span>|The check's category.|*string*|
|<span class="no-wrap-code">`check_name`</span>|Similar check name in another category.|*string*|


___

## CheckModel
Model that returns the form definition and the form data to edit a single data quality check.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_name`</span>|Data quality check name that is used in YAML.|*string*|
|<span class="no-wrap-code">`help_text`</span>|Help text that describes the data quality check.|*string*|
|<span class="no-wrap-code">`display_name`</span>|User assigned display name that is shown instead of the original data quality check name.|*string*|
|<span class="no-wrap-code">`friendly_name`</span>|An alternative check's name that is shown on the check editor as a hint.|*string*|
|<span class="no-wrap-code">`sensor_parameters`</span>|List of fields for editing the sensor parameters.|*List[[FieldModel](./common.md#fieldmodel)]*|
|<span class="no-wrap-code">`sensor_name`</span>|Full sensor name. This field is for information purposes and can be used to create additional custom checks that reuse the same data quality sensor.|*string*|
|<span class="no-wrap-code">`quality_dimension`</span>|Data quality dimension used for tagging the results of this data quality checks.|*string*|
|<span class="no-wrap-code">[`rule`](#rulethresholdsmodel)</span>|Threshold (alerting) rules defined for a check.|*[RuleThresholdsModel](#rulethresholdsmodel)*|
|<span class="no-wrap-code">`supports_error_sampling`</span>|The data quality check supports capturing error samples, because an error sampling template is defined.|*boolean*|
|<span class="no-wrap-code">`supports_grouping`</span>|The data quality check supports a custom data grouping configuration.|*boolean*|
|<span class="no-wrap-code">`standard`</span>|This is a standard data quality check that is always shown on the data quality checks editor screen. Non-standard data quality checks (when the value is false) are advanced checks that are shown when the user decides to expand the list of checks.|*boolean*|
|<span class="no-wrap-code">`default_check`</span>|This is a check that was applied on-the-fly, because it is configured as a default data observability check and can be run, but it is not configured in the table YAML.|*boolean*|
|<span class="no-wrap-code">[`default_severity`](#defaultruleseveritylevel)</span>|The severity level (warning, error, fatal) for the default rule that is activated in the data quality check editor when the check is enabled.|*[DefaultRuleSeverityLevel](#defaultruleseveritylevel)*|
|<span class="no-wrap-code">[`data_grouping_override`](../../reference/yaml/ConnectionYaml.md#datagroupingconfigurationspec)</span>|Data grouping configuration for this check. When a data grouping configuration is assigned at a check level, it overrides the data grouping configuration from the table level. Data grouping is configured in two cases: (1) the data in the table should be analyzed with a GROUP BY condition, to analyze different groups of rows using separate time series, for example a table contains data from multiple countries and there is a 'country' column used for partitioning. (2) a static data grouping configuration is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). |*[DataGroupingConfigurationSpec](../../reference/yaml/ConnectionYaml.md#datagroupingconfigurationspec)*|
|<span class="no-wrap-code">[`schedule_override`](./common.md#monitoringschedulespec)</span>|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|*[MonitoringScheduleSpec](./common.md#monitoringschedulespec)*|
|<span class="no-wrap-code">[`effective_schedule`](#effectiveschedulemodel)</span>|Model of configured schedule enabled on the check level.|*[EffectiveScheduleModel](#effectiveschedulemodel)*|
|<span class="no-wrap-code">[`schedule_enabled_status`](#scheduleenabledstatusmodel)</span>|State of the scheduling override for this check.|*[ScheduleEnabledStatusModel](#scheduleenabledstatusmodel)*|
|<span class="no-wrap-code">[`comments`](#commentslistspec)</span>|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|*[CommentsListSpec](#commentslistspec)*|
|<span class="no-wrap-code">`disabled`</span>|Disables the data quality check. Only enabled checks are executed. The sensor should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|*boolean*|
|<span class="no-wrap-code">`exclude_from_kpi`</span>|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|*boolean*|
|<span class="no-wrap-code">`include_in_sla`</span>|Marks the data quality check as part of a data quality SLA (Data Contract). The data quality SLA is a set of critical data quality checks that must always pass and are considered as a Data Contract for the dataset.|*boolean*|
|<span class="no-wrap-code">`configured`</span>|True if the data quality check is configured (not null). When saving the data quality check configuration, set the flag to true for storing the check.|*boolean*|
|<span class="no-wrap-code">`filter`</span>|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|*string*|
|<span class="no-wrap-code">[`run_checks_job_template`](#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to start the job.|*[CheckSearchFilters](#checksearchfilters)*|
|<span class="no-wrap-code">[`data_clean_job_template`](./jobs.md#deletestoreddataqueuejobparameters)</span>|Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this check.|*[DeleteStoredDataQueueJobParameters](./jobs.md#deletestoreddataqueuejobparameters)*|
|<span class="no-wrap-code">`data_grouping_configuration`</span>|The name of a data grouping configuration defined at a table that should be used for this check.|*string*|
|<span class="no-wrap-code">`always_collect_error_samples`</span>|Forces collecting error samples for this check whenever it fails, even if it is a monitoring check that is run by a scheduler, and running an additional query to collect error samples will impose additional load on the data source.|*boolean*|
|<span class="no-wrap-code">`do_not_schedule`</span>|Disables running this check by a DQOps CRON scheduler. When a check is disabled from scheduling, it can be only triggered from the user interface or by submitting "run checks" job.|*boolean*|
|<span class="no-wrap-code">[`check_target`](#checktargetmodel)</span>|Type of the check's target (column, table).|*[CheckTargetModel](#checktargetmodel)*|
|<span class="no-wrap-code">`configuration_requirements_errors`</span>|List of configuration errors that must be fixed before the data quality check can be executed.|*List[string]*|
|<span class="no-wrap-code">`similar_checks`</span>|List of similar checks in other check types or in other time scales.|*List[[SimilarCheckModel](#similarcheckmodel)]*|
|<span class="no-wrap-code">`check_hash`</span>|The check hash code that identifies the check instance.|*long*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can edit the check.|*boolean*|
|<span class="no-wrap-code">`can_run_checks`</span>|Boolean flag that decides if the current user can run checks.|*boolean*|
|<span class="no-wrap-code">`can_delete_data`</span>|Boolean flag that decides if the current user can delete data (results).|*boolean*|


___

## QualityCategoryModel
Model that returns the form definition and the form data to edit all checks within a single category.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`category`</span>|Data quality check category name.|*string*|
|<span class="no-wrap-code">`comparison_name`</span>|The name of the reference table configuration used for a cross table data comparison (when the category is 'comparisons').|*string*|
|<span class="no-wrap-code">`compare_to_column`</span>|The name of the column in the reference table that is compared.|*string*|
|<span class="no-wrap-code">`help_text`</span>|Help text that describes the category.|*string*|
|<span class="no-wrap-code">`checks`</span>|List of data quality checks within the category.|*List[[CheckModel](#checkmodel)]*|
|<span class="no-wrap-code">[`run_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to start the job.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`data_clean_job_template`](./jobs.md#deletestoreddataqueuejobparameters)</span>|Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this quality category.|*[DeleteStoredDataQueueJobParameters](./jobs.md#deletestoreddataqueuejobparameters)*|


___

## CheckContainerModel
Model that returns the form definition and the form data to edit all data quality checks divided by categories.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`categories`</span>|List of all data quality categories that contain data quality checks inside.|*List[[QualityCategoryModel](#qualitycategorymodel)]*|
|<span class="no-wrap-code">[`effective_schedule`](./common.md#effectiveschedulemodel)</span>|Model of configured schedule enabled on the check container.|*[EffectiveScheduleModel](./common.md#effectiveschedulemodel)*|
|<span class="no-wrap-code">[`effective_schedule_enabled_status`](./common.md#scheduleenabledstatusmodel)</span>|State of the effective scheduling on the check container.|*[ScheduleEnabledStatusModel](./common.md#scheduleenabledstatusmodel)*|
|<span class="no-wrap-code">`partition_by_column`</span>|The name of the column that partitioned checks will use for the time period partitioning. Important only for partitioned checks.|*string*|
|<span class="no-wrap-code">[`run_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to start the job.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`data_clean_job_template`](./jobs.md#deletestoreddataqueuejobparameters)</span>|Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this check container|*[DeleteStoredDataQueueJobParameters](./jobs.md#deletestoreddataqueuejobparameters)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can edit the check.|*boolean*|
|<span class="no-wrap-code">`can_run_checks`</span>|Boolean flag that decides if the current user can run checks.|*boolean*|
|<span class="no-wrap-code">`can_delete_data`</span>|Boolean flag that decides if the current user can delete data (results).|*boolean*|


___

## CheckContainerTypeModel
Model identifying the check type and timescale of checks belonging to a container.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_type`](./common.md#checktype)</span>|Check type.|*[CheckType](./common.md#checktype)*|
|<span class="no-wrap-code">[`check_time_scale`](./common.md#checktimescale)</span>|Check timescale.|*[CheckTimeScale](./common.md#checktimescale)*|


___

## CheckTemplate
Model depicting a named data quality check that can potentially be enabled, regardless to its position in hierarchy tree.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_target`](./schemas.md#checktarget)</span>|Check target (table, column)|*[CheckTarget](./schemas.md#checktarget)*|
|<span class="no-wrap-code">`check_category`</span>|Data quality check category.|*string*|
|<span class="no-wrap-code">`check_name`</span>|Data quality check name that is used in YAML.|*string*|
|<span class="no-wrap-code">`help_text`</span>|Help text that describes the data quality check.|*string*|
|<span class="no-wrap-code">[`check_container_type`](#checkcontainertypemodel)</span>|Check type with time-scale.|*[CheckContainerTypeModel](#checkcontainertypemodel)*|
|<span class="no-wrap-code">`sensor_name`</span>|Full sensor name.|*string*|
|<span class="no-wrap-code">[`check_model`](./common.md#checkmodel)</span>|Template of the check model with the sensor parameters and rule parameters|*[CheckModel](./common.md#checkmodel)*|
|<span class="no-wrap-code">`sensor_parameters_definitions`</span>|List of sensor parameter fields definitions.|*List[[ParameterDefinitionSpec](../../reference/yaml/SensorDefinitionYaml.md#parameterdefinitionspec)]*|
|<span class="no-wrap-code">`rule_parameters_definitions`</span>|List of threshold (alerting) rule's parameters definitions (for a single rule, regardless of severity).|*List[[ParameterDefinitionSpec](../../reference/yaml/SensorDefinitionYaml.md#parameterdefinitionspec)]*|


___

## PhysicalTableName
Physical table name that is a combination of a schema name and a physical table name (without any quoting or escaping).


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`schema_name`</span>|Schema name|*string*|
|<span class="no-wrap-code">`table_name`</span>|Table name|*string*|


___

## RuleSeverityLevel
Rule severity levels. Matches the severity level name (warning - 1, alert - 2, fatal - 3) with a numeric level.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|valid<br/>warning<br/>error<br/>fatal<br/>|

___

## CheckResultStatus
Enumeration of check execution statuses. It is the highest severity or an error if the sensor cannot be executed due to a configuration issue.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|valid<br/>warning<br/>error<br/>fatal<br/>execution_error<br/>|

___

## CheckCurrentDataQualityStatusModel
The most recent data quality status for a single data quality check.
 If data grouping is enabled, this model will return the highest data quality issue status from all
 data quality results for all data groups.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`current_severity`](#checkresultstatus)</span>|The data quality issue severity for this data quality check. An additional value *execution_error* is used to tell that the check, sensor or rule failed to execute due to insufficient  permissions to the table or an error in the sensor's template or a Python rule. For partitioned checks, it is the highest severity of all results for all partitions (time periods) in the analyzed time range.|*[CheckResultStatus](#checkresultstatus)*|
|<span class="no-wrap-code">[`highest_historical_severity`](./common.md#ruleseveritylevel)</span>|The highest severity of previous executions of this data quality issue in the analyzed time range. It can be different from the *current_severity* if the data quality issue was solved and the most recently data quality issue did not detect it anymore. For partitioned checks, this field returns the same value as the *current_severity*, because data quality issues in older partitions are still valid.|*[RuleSeverityLevel](./common.md#ruleseveritylevel)*|
|<span class="no-wrap-code">`column_name`</span>|Optional column name for column-level data quality checks.|*string*|
|<span class="no-wrap-code">[`check_type`](./common.md#checktype)</span>|The check type: profiling, monitoring, partitioned.|*[CheckType](./common.md#checktype)*|
|<span class="no-wrap-code">[`time_scale`](./common.md#checktimescale)</span>|The check time scale for *monitoring* and *partitioned* check types. The time scales are *daily* and *monthly*. The profiling checks do not have a time scale.|*[CheckTimeScale](./common.md#checktimescale)*|
|<span class="no-wrap-code">`category`</span>|Check category name, such as nulls, schema, strings, volume.|*string*|
|<span class="no-wrap-code">`quality_dimension`</span>|Data quality dimension, such as Completeness, Uniqueness, Validity.|*string*|
|<span class="no-wrap-code">`executed_checks`</span>|The total number of most recent checks that were executed on the column. Table comparison checks that are comparing groups of data are counted as the number of compared data groups.|*integer*|
|<span class="no-wrap-code">`valid_results`</span>|The number of most recent valid data quality checks that passed without raising any issues.|*integer*|
|<span class="no-wrap-code">`warnings`</span>|The number of most recent data quality checks that failed by raising a warning severity data quality issue.|*integer*|
|<span class="no-wrap-code">`errors`</span>|The number of most recent data quality checks that failed by raising an error severity data quality issue.|*integer*|
|<span class="no-wrap-code">`fatals`</span>|The number of most recent data quality checks that failed by raising a fatal severity data quality issue.|*integer*|
|<span class="no-wrap-code">`execution_errors`</span>|The number of data quality check execution errors that were reported due to access issues to the data source, invalid mapping in DQOps, invalid queries in data quality sensors or invalid python rules. When an execution error is reported, the configuration of a data quality check on a column must be updated.|*integer*|


___

## DimensionCurrentDataQualityStatusModel
A model that describes the current data quality status for a single data quality dimension.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`dimension`</span>|Data quality dimension name. The most popular dimensions are: Completeness, Uniqueness, Timeliness, Validity, Consistency, Accuracy, Availability.|*string*|
|<span class="no-wrap-code">[`current_severity`](./common.md#ruleseveritylevel)</span>|The most recent data quality issue severity for this table. When the table is monitored using data grouping, it is the highest issue severity of all recently analyzed data groups. For partitioned checks, it is the highest severity of all results for all partitions (time periods) in the analyzed time range.|*[RuleSeverityLevel](./common.md#ruleseveritylevel)*|
|<span class="no-wrap-code">[`highest_historical_severity`](./common.md#ruleseveritylevel)</span>|The highest severity of previous executions of this data quality issue in the analyzed time range. It can be different from the *current_severity* if the data quality issue was solved and the most recently data quality issue did not detect it anymore. For partitioned checks, this field returns the same value as the *current_severity*, because data quality issues in older partitions are still valid.|*[RuleSeverityLevel](./common.md#ruleseveritylevel)*|
|<span class="no-wrap-code">`executed_checks`</span>|The total number of most recent checks that were executed on the table for one data quality dimension. Table comparison checks that are comparing groups of data are counted as the number of compared data groups.|*integer*|
|<span class="no-wrap-code">`valid_results`</span>|The number of most recent valid data quality checks that passed without raising any issues.|*integer*|
|<span class="no-wrap-code">`warnings`</span>|The number of most recent data quality checks that failed by raising a warning severity data quality issue.|*integer*|
|<span class="no-wrap-code">`errors`</span>|The number of most recent data quality checks that failed by raising an error severity data quality issue.|*integer*|
|<span class="no-wrap-code">`fatals`</span>|The number of most recent data quality checks that failed by raising a fatal severity data quality issue.|*integer*|
|<span class="no-wrap-code">`execution_errors`</span>|The number of data quality check execution errors that were reported due to access issues to the data source, invalid mapping in DQOps, invalid queries in data quality sensors or invalid python rules. When an execution error is reported, the configuration of a data quality check on a table must be updated.|*integer*|
|<span class="no-wrap-code">`data_quality_kpi`</span>|Data quality KPI score for the data quality dimension, measured as a percentage of passed data quality checks. DQOps counts data quality issues at a warning severity level as passed checks. The data quality KPI score is a value in the range 0..100.|*double*|


___

## ColumnCurrentDataQualityStatusModel
The column validity status. It is a summary of the results of the most recently executed data quality checks on the column.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`current_severity`](#ruleseveritylevel)</span>|The most recent data quality issue severity for this column. When the table is monitored using data grouping, it is the highest issue severity of all recently analyzed data groups. For partitioned checks, it is the highest severity of all results for all partitions (time periods) in the analyzed time range.|*[RuleSeverityLevel](#ruleseveritylevel)*|
|<span class="no-wrap-code">[`highest_historical_severity`](./common.md#ruleseveritylevel)</span>|The highest severity of previous executions of this data quality issue in the analyzed time range. It can be different from the *current_severity* if the data quality issue was solved and the most recently data quality issue did not detect it anymore. For partitioned checks, this field returns the same value as the *current_severity*, because data quality issues in older partitions are still valid.|*[RuleSeverityLevel](./common.md#ruleseveritylevel)*|
|<span class="no-wrap-code">`executed_checks`</span>|The total number of most recent checks that were executed on the column. Table comparison checks that are comparing groups of data are counted as the number of compared data groups.|*integer*|
|<span class="no-wrap-code">`valid_results`</span>|The number of most recent valid data quality checks that passed without raising any issues.|*integer*|
|<span class="no-wrap-code">`warnings`</span>|The number of most recent data quality checks that failed by raising a warning severity data quality issue.|*integer*|
|<span class="no-wrap-code">`errors`</span>|The number of most recent data quality checks that failed by raising an error severity data quality issue.|*integer*|
|<span class="no-wrap-code">`fatals`</span>|The number of most recent data quality checks that failed by raising a fatal severity data quality issue.|*integer*|
|<span class="no-wrap-code">`execution_errors`</span>|The number of data quality check execution errors that were reported due to access issues to the data source, invalid mapping in DQOps, invalid queries in data quality sensors or invalid python rules. When an execution error is reported, the configuration of a data quality check on a column must be updated.|*integer*|
|<span class="no-wrap-code">`data_quality_kpi`</span>|Data quality KPI score for the column, measured as a percentage of passed data quality checks. DQOps counts data quality issues at a warning severity level as passed checks. The data quality KPI score is a value in the range 0..100.|*double*|
|<span class="no-wrap-code">`checks`</span>|The dictionary of statuses for data quality checks. The keys are data quality check names, the values are the current data quality check statuses that describe the most current status.|*Dict[string, [CheckCurrentDataQualityStatusModel](#checkcurrentdataqualitystatusmodel)]*|
|<span class="no-wrap-code">`dimensions`</span>|Dictionary of the current data quality statues for each data quality dimension.|*Dict[string, [DimensionCurrentDataQualityStatusModel](#dimensioncurrentdataqualitystatusmodel)]*|


___

## ColumnListModel
Column list model that returns the basic fields from a column specification, excluding nested nodes like a list of activated checks.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">[`table`](#physicaltablename)</span>|Physical table name including the schema and table names.|*[PhysicalTableName](#physicaltablename)*|
|<span class="no-wrap-code">`column_name`</span>|Column name.|*string*|
|<span class="no-wrap-code">`sql_expression`</span>|SQL expression for a calculated column, or a column that applies additional data transformation to the original column value. The original column value is referenced by a token {column}.|*string*|
|<span class="no-wrap-code">`column_hash`</span>|Column hash that identifies the column using a unique hash code.|*long*|
|<span class="no-wrap-code">`disabled`</span>|Disables all data quality checks on the column. Data quality checks will not be executed.|*boolean*|
|<span class="no-wrap-code">`id`</span>|Marks columns that are part of a primary or a unique key. DQOps captures their values during error sampling to match invalid values to the rows in which the value was found.|*boolean*|
|<span class="no-wrap-code">`has_any_configured_checks`</span>|True when the column has any checks configured (read-only).|*boolean*|
|<span class="no-wrap-code">`has_any_configured_profiling_checks`</span>|True when the column has any profiling checks configured (read-only).|*boolean*|
|<span class="no-wrap-code">`has_any_configured_monitoring_checks`</span>|True when the column has any monitoring checks configured (read-only).|*boolean*|
|<span class="no-wrap-code">`has_any_configured_partition_checks`</span>|True when the column has any partition checks configured (read-only).|*boolean*|
|<span class="no-wrap-code">[`type_snapshot`](../../reference/yaml/TableYaml.md#columntypesnapshotspec)</span>|Column data type that was retrieved when the table metadata was imported.|*[ColumnTypeSnapshotSpec](../../reference/yaml/TableYaml.md#columntypesnapshotspec)*|
|<span class="no-wrap-code">[`data_quality_status`](#columncurrentdataqualitystatusmodel)</span>|The current data quality status for the column, grouped by data quality dimensions. DQOps may return a null value when the results were not yet loaded into the cache. In that case, the client should wait a few seconds and retry a call to get the most recent data quality status of the column.|*[ColumnCurrentDataQualityStatusModel](#columncurrentdataqualitystatusmodel)*|
|<span class="no-wrap-code">[`run_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run all checks within this column.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_profiling_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run profiling checks within this column.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_monitoring_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run monitoring checks within this column.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_partition_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run partition partitioned checks within this column.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`collect_statistics_job_template`](./jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collector within this column.|*[StatisticsCollectorSearchFilters](./jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">[`data_clean_job_template`](./jobs.md#deletestoreddataqueuejobparameters)</span>|Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this column.|*[DeleteStoredDataQueueJobParameters](./jobs.md#deletestoreddataqueuejobparameters)*|
|<span class="no-wrap-code">`advanced_properties`</span>|A dictionary of advanced properties that can be used for e.g. to support mapping data to data catalogs, a key/value dictionary.|*Dict[string, string]*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete the column.|*boolean*|
|<span class="no-wrap-code">`can_collect_statistics`</span>|Boolean flag that decides if the current user can collect statistics.|*boolean*|
|<span class="no-wrap-code">`can_run_checks`</span>|Boolean flag that decides if the current user can run checks.|*boolean*|
|<span class="no-wrap-code">`can_delete_data`</span>|Boolean flag that decides if the current user can delete data (results).|*boolean*|


___

## ProviderType
Data source provider type (dialect type).
  We will use lower case names to avoid issues with parsing, even if the enum names are not named following the Java naming convention.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|bigquery<br/>databricks<br/>mysql<br/>oracle<br/>postgresql<br/>duckdb<br/>presto<br/>redshift<br/>snowflake<br/>spark<br/>sqlserver<br/>trino<br/>|

___

## ConnectionModel
Connection model returned by the rest api that is limited only to the basic fields, excluding nested nodes.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">`connection_hash`</span>|Connection hash that identifies the connection using a unique hash code.|*long*|
|<span class="no-wrap-code">`parallel_jobs_limit`</span>|The concurrency limit for the maximum number of parallel SQL queries executed on this connection.|*integer*|
|<span class="no-wrap-code">`schedule_on_instance`</span>|Limits running scheduled checks (started by a CRON job scheduler) to run only on a named DQOps instance. When this field is empty, data quality checks are run on all DQOps instances. Set a DQOps instance name to run checks on a named instance only. The default name of the DQOps Cloud SaaS instance is "cloud".|*string*|
|<span class="no-wrap-code">[`provider_type`](#providertype)</span>|Database provider type (required). Accepts: bigquery, snowflake, etc.|*[ProviderType](#providertype)*|
|<span class="no-wrap-code">[`bigquery`](../../reference/yaml/ConnectionYaml.md#bigqueryparametersspec)</span>|BigQuery connection parameters. Specify parameters in the bigquery section.|*[BigQueryParametersSpec](../../reference/yaml/ConnectionYaml.md#bigqueryparametersspec)*|
|<span class="no-wrap-code">[`snowflake`](../../reference/yaml/ConnectionYaml.md#snowflakeparametersspec)</span>|Snowflake connection parameters.|*[SnowflakeParametersSpec](../../reference/yaml/ConnectionYaml.md#snowflakeparametersspec)*|
|<span class="no-wrap-code">[`postgresql`](../../reference/yaml/ConnectionYaml.md#postgresqlparametersspec)</span>|PostgreSQL connection parameters.|*[PostgresqlParametersSpec](../../reference/yaml/ConnectionYaml.md#postgresqlparametersspec)*|
|<span class="no-wrap-code">[`duckdb`](../../reference/yaml/ConnectionYaml.md#duckdbparametersspec)</span>|DuckDB connection parameters.|*[DuckdbParametersSpec](../../reference/yaml/ConnectionYaml.md#duckdbparametersspec)*|
|<span class="no-wrap-code">[`redshift`](../../reference/yaml/ConnectionYaml.md#redshiftparametersspec)</span>|Redshift connection parameters.|*[RedshiftParametersSpec](../../reference/yaml/ConnectionYaml.md#redshiftparametersspec)*|
|<span class="no-wrap-code">[`sqlserver`](../../reference/yaml/ConnectionYaml.md#sqlserverparametersspec)</span>|SqlServer connection parameters.|*[SqlServerParametersSpec](../../reference/yaml/ConnectionYaml.md#sqlserverparametersspec)*|
|<span class="no-wrap-code">[`presto`](../../reference/yaml/ConnectionYaml.md#prestoparametersspec)</span>|Presto connection parameters.|*[PrestoParametersSpec](../../reference/yaml/ConnectionYaml.md#prestoparametersspec)*|
|<span class="no-wrap-code">[`trino`](../../reference/yaml/ConnectionYaml.md#trinoparametersspec)</span>|Trino connection parameters.|*[TrinoParametersSpec](../../reference/yaml/ConnectionYaml.md#trinoparametersspec)*|
|<span class="no-wrap-code">[`mysql`](../../reference/yaml/ConnectionYaml.md#mysqlparametersspec)</span>|MySQL connection parameters.|*[MysqlParametersSpec](../../reference/yaml/ConnectionYaml.md#mysqlparametersspec)*|
|<span class="no-wrap-code">[`oracle`](../../reference/yaml/ConnectionYaml.md#oracleparametersspec)</span>|Oracle connection parameters.|*[OracleParametersSpec](../../reference/yaml/ConnectionYaml.md#oracleparametersspec)*|
|<span class="no-wrap-code">[`spark`](../../reference/yaml/ConnectionYaml.md#sparkparametersspec)</span>|Spark connection parameters.|*[SparkParametersSpec](../../reference/yaml/ConnectionYaml.md#sparkparametersspec)*|
|<span class="no-wrap-code">[`databricks`](../../reference/yaml/ConnectionYaml.md#databricksparametersspec)</span>|Databricks connection parameters.|*[DatabricksParametersSpec](../../reference/yaml/ConnectionYaml.md#databricksparametersspec)*|
|<span class="no-wrap-code">[`run_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run all checks within this connection.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_profiling_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run profiling checks within this connection.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_monitoring_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run monitoring checks within this connection.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_partition_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run partition partitioned checks within this connection.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`collect_statistics_job_template`](./jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors within this connection.|*[StatisticsCollectorSearchFilters](./jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">[`data_clean_job_template`](./jobs.md#deletestoreddataqueuejobparameters)</span>|Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this connection.|*[DeleteStoredDataQueueJobParameters](./jobs.md#deletestoreddataqueuejobparameters)*|
|<span class="no-wrap-code">`advanced_properties`</span>|A dictionary of advanced properties that can be used for e.g. to support mapping data to data catalogs, a key/value dictionary.|*Dict[string, string]*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete the connection to the data source.|*boolean*|
|<span class="no-wrap-code">`can_collect_statistics`</span>|Boolean flag that decides if the current user can collect statistics.|*boolean*|
|<span class="no-wrap-code">`can_run_checks`</span>|Boolean flag that decides if the current user can run checks.|*boolean*|
|<span class="no-wrap-code">`can_delete_data`</span>|Boolean flag that decides if the current user can delete data (results).|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

## DqoQueueJobId
Identifies a single job.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`job_id`</span>|Job id.|*long*|
|<span class="no-wrap-code">`job_business_key`</span>|Optional job business key that was assigned to the job. A business key is an alternative user assigned unique job identifier used to find the status of a job finding it by the business key.|*string*|
|<span class="no-wrap-code">[`parent_job_id`](./common.md#dqoqueuejobid)</span>|Parent job id. Filled only for nested jobs, for example a sub-job that runs data quality checks on a single table.|*[DqoQueueJobId](./common.md#dqoqueuejobid)*|


___

## ProfilingTimePeriodTruncation
The time period for profiling checks (millisecond, daily, monthly, weekly, hourly).
 The default profiling check stores one value per month. When profiling checks is re-executed during the month,
 the previous profiling checks value is overwritten and only the most recent value is stored.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|store_the_most_recent_result_per_month<br/>store_the_most_recent_result_per_week<br/>store_the_most_recent_result_per_day<br/>store_the_most_recent_result_per_hour<br/>store_all_results_without_date_truncation<br/>|

___

## TableListModel
Table list model returned by the rest api that is limited only to the basic fields, excluding nested nodes.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">`table_hash`</span>|Table hash that identifies the table using a unique hash code.|*long*|
|<span class="no-wrap-code">[`target`](./common.md#physicaltablename)</span>|Physical table details (a physical schema name and a physical table name).|*[PhysicalTableName](./common.md#physicaltablename)*|
|<span class="no-wrap-code">`disabled`</span>|Disables all data quality checks on the table. Data quality checks will not be executed.|*boolean*|
|<span class="no-wrap-code">`stage`</span>|Stage name.|*string*|
|<span class="no-wrap-code">`filter`</span>|SQL WHERE clause added to the sensor queries.|*string*|
|<span class="no-wrap-code">`do_not_collect_error_samples_in_profiling`</span>|Disable automatic collection of error samples in the profiling section. The profiling checks by default always collect error samples for failed data quality checks.|*boolean*|
|<span class="no-wrap-code">`always_collect_error_samples_in_monitoring`</span>|Always collect error samples for failed monitoring checks. DQOps will not collect error samples automatically when the checks are executed by a scheduler or by running checks from the metadata tree. Error samples are always collected only when the checks are run from the check editor.|*boolean*|
|<span class="no-wrap-code">`priority`</span>|Table priority (1, 2, 3, 4, ...). The tables can be assigned a priority level. The table priority is copied into each data quality check result and a sensor result, enabling efficient grouping of more and less important tables during a data quality improvement project, when the data quality issues on higher priority tables are fixed before data quality issues on less important tables.|*integer*|
|<span class="no-wrap-code">[`owner`](../../reference/yaml/TableYaml.md#tableownerspec)</span>|Table owner information like the data steward name or the business application name.|*[TableOwnerSpec](../../reference/yaml/TableYaml.md#tableownerspec)*|
|<span class="no-wrap-code">[`profiling_checks_result_truncation`](#profilingtimeperiodtruncation)</span>|Defines how many profiling checks results are stored for the table monthly. By default, DQOps will use the 'one_per_month' configuration and store only the most recent profiling checks result executed during the month. By changing this value, it is possible to store one value per day or even store all profiling checks results.|*[ProfilingTimePeriodTruncation](#profilingtimeperiodtruncation)*|
|<span class="no-wrap-code">[`file_format`](../../reference/yaml/TableYaml.md#fileformatspec)</span>|File format for a file based table, such as a CSV or Parquet file.|*[FileFormatSpec](../../reference/yaml/TableYaml.md#fileformatspec)*|
|<span class="no-wrap-code">[`data_quality_status`](./check_results.md#tablecurrentdataqualitystatusmodel)</span>|The current data quality status for the table, grouped by data quality dimensions. DQOps may return a null value when the results were not yet loaded into the cache. In that case, the client should wait a few seconds and retry a call to get the most recent data quality status of the table.|*[TableCurrentDataQualityStatusModel](./check_results.md#tablecurrentdataqualitystatusmodel)*|
|<span class="no-wrap-code">`has_any_configured_checks`</span>|True when the table has any checks configured.|*boolean*|
|<span class="no-wrap-code">`has_any_configured_profiling_checks`</span>|True when the table has any profiling checks configured.|*boolean*|
|<span class="no-wrap-code">`has_any_configured_monitoring_checks`</span>|True when the table has any monitoring checks configured.|*boolean*|
|<span class="no-wrap-code">`has_any_configured_partition_checks`</span>|True when the table has any partition checks configured.|*boolean*|
|<span class="no-wrap-code">`partitioning_configuration_missing`</span>|True when the table has missing configuration of the "partition_by_column" column, making any partition checks fail when executed.|*boolean*|
|<span class="no-wrap-code">[`run_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run all checks within this table.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_profiling_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run profiling checks within this table.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_monitoring_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run monitoring checks within this table.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_partition_checks_job_template`](./common.md#checksearchfilters)</span>|Configured parameters for the "check run" job that should be pushed to the job queue in order to run partition partitioned checks within this table.|*[CheckSearchFilters](./common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`collect_statistics_job_template`](./jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors within this table.|*[StatisticsCollectorSearchFilters](./jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">[`data_clean_job_template`](./jobs.md#deletestoreddataqueuejobparameters)</span>|Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this table.|*[DeleteStoredDataQueueJobParameters](./jobs.md#deletestoreddataqueuejobparameters)*|
|<span class="no-wrap-code">`advanced_properties`</span>|A dictionary of advanced properties that can be used for e.g. to support mapping data to data catalogs, a key/value dictionary.|*Dict[string, string]*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can update or delete this object.|*boolean*|
|<span class="no-wrap-code">`can_collect_statistics`</span>|Boolean flag that decides if the current user can collect statistics.|*boolean*|
|<span class="no-wrap-code">`can_run_checks`</span>|Boolean flag that decides if the current user can run checks.|*boolean*|
|<span class="no-wrap-code">`can_delete_data`</span>|Boolean flag that decides if the current user can delete data (results).|*boolean*|
|<span class="no-wrap-code">`yaml_parsing_error`</span>|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|*string*|


___

