# DQOps REST API common models reference
The references of all objects used as shared REST API models in all operations are listed below.


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
|<span class="no-wrap-code">[`definition`](\docs\reference\yaml\SensorDefinitionYaml\#parameterdefinitionspec)</span>|Field name that matches the field name (snake_case) used in the YAML specification.|*[ParameterDefinitionSpec](\docs\reference\yaml\SensorDefinitionYaml\#parameterdefinitionspec)*|
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
|<span class="no-wrap-code">`rule_name`</span>|Full rule name. This field is for information purposes and could be used to create additional custom checks that are reusing the same data quality rule.|*string*|
|<span class="no-wrap-code">`rule_parameters`</span>|List of fields for editing the rule parameters like thresholds.|*List[[FieldModel](\docs\client\models\common.md#fieldmodel)]*|
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
|<span class="no-wrap-code">[`check_target`](\docs\client\models\schemas.md#checktarget)</span>|Check target (table or column).|*[CheckTarget](\docs\client\models\schemas.md#checktarget)*|
|<span class="no-wrap-code">[`check_type`](\docs\client\models\table_comparisons.md#checktype)</span>|Check type (profiling, monitoring, partitioned).|*[CheckType](\docs\client\models\table_comparisons.md#checktype)*|
|<span class="no-wrap-code">[`check_time_scale`](\docs\client\models\common.md#checktimescale)</span>|Check timescale (for monitoring and partitioned checks).|*[CheckTimeScale](\docs\client\models\common.md#checktimescale)*|
|<span class="no-wrap-code">`category_name`</span>|Category to which this check belongs.|*string*|
|<span class="no-wrap-code">`check_name`</span>|Check name that is used in YAML file.|*string*|
|<span class="no-wrap-code">`sensor_parameters`</span>|List of fields for editing the sensor parameters.|*List[[FieldModel](#fieldmodel)]*|
|<span class="no-wrap-code">`table_level_filter`</span>|SQL WHERE clause added to the sensor query for every check on this table.|*string*|
|<span class="no-wrap-code">`sensor_level_filter`</span>|SQL WHERE clause added to the sensor query for this check.|*string*|
|<span class="no-wrap-code">[`warning`](#ruleparametersmodel)</span>|Rule parameters for the warning severity rule.|*[RuleParametersModel](#ruleparametersmodel)*|
|<span class="no-wrap-code">[`error`](\docs\client\models\common.md#ruleparametersmodel)</span>|Rule parameters for the error severity rule.|*[RuleParametersModel](\docs\client\models\common.md#ruleparametersmodel)*|
|<span class="no-wrap-code">[`fatal`](\docs\client\models\common.md#ruleparametersmodel)</span>|Rule parameters for the fatal severity rule.|*[RuleParametersModel](\docs\client\models\common.md#ruleparametersmodel)*|
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
|<span class="no-wrap-code">[`error`](\docs\client\models\common.md#ruleparametersmodel)</span>|Rule parameters for the error severity rule.|*[RuleParametersModel](\docs\client\models\common.md#ruleparametersmodel)*|
|<span class="no-wrap-code">[`warning`](\docs\client\models\common.md#ruleparametersmodel)</span>|Rule parameters for the warning severity rule.|*[RuleParametersModel](\docs\client\models\common.md#ruleparametersmodel)*|
|<span class="no-wrap-code">[`fatal`](\docs\client\models\common.md#ruleparametersmodel)</span>|Rule parameters for the fatal severity rule.|*[RuleParametersModel](\docs\client\models\common.md#ruleparametersmodel)*|


___

## MonitoringScheduleSpec
Monitoring job schedule specification.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`cron_expression`</span>|Unix style cron expression that specifies when to execute scheduled operations like running data quality checks or synchronizing the configuration with the cloud.|*string*|
|<span class="no-wrap-code">`disabled`</span>|Disables the schedule. When the value of this &#x27;disable&#x27; field is false, the schedule is stored in the metadata but it is not activated to run data quality checks.|*boolean*|


___

## CheckRunScheduleGroup
The run check scheduling group (profiling, daily checks, monthly checks, etc), which identifies the configuration of a schedule (cron expression) used schedule these checks on the job scheduler.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|profiling<br/>monitoring_daily<br/>monitoring_monthly<br/>partitioned_daily<br/>partitioned_monthly<br/>|

___

## EffectiveScheduleLevelModel
Enumeration of possible levels at which a schedule could be configured.


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
|<span class="no-wrap-code">[`schedule_group`](\docs\client\models\common.md#checkrunschedulegroup)</span>|Field value for a schedule group to which this schedule belongs.|*[CheckRunScheduleGroup](\docs\client\models\common.md#checkrunschedulegroup)*|
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
|<span class="no-wrap-code">`self`</span>|:mm|*List[[CommentSpec](\docs\client\models\common.md#commentspec)]*|


___

## CheckSearchFilters
Target data quality checks filter, identifies which checks on which tables and columns should be executed.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`column`</span>|The column name. This field accepts search patterns in the format: &#x27;fk_\*&#x27;, &#x27;\*_id&#x27;, &#x27;prefix\*suffix&#x27;.|*string*|
|<span class="no-wrap-code">`column_data_type`</span>|The column data type that was imported from the data source and is stored in the [columns -&gt; column_name -&gt; type_snapshot -&gt; column_type](/docs/reference/yaml/TableYaml/#columntypesnapshotspec) field in the *.dqotable.yaml* file.|*string*|
|<span class="no-wrap-code">`column_nullable`</span>|Optional filter to find only nullable (when the value is *true*) or not nullable (when the value is *false*) columns, based on the value of the [columns -&gt; column_name -&gt; type_snapshot -&gt; nullable](/docs/reference/yaml/TableYaml/#columntypesnapshotspec) field in the *.dqotable.yaml* file.|*boolean*|
|<span class="no-wrap-code">[`check_target`](\docs\client\models\schemas.md#checktarget)</span>|The target type of object to run checks. Supported values are: *table* to run only table level checks or *column* to run only column level checks.|*[CheckTarget](\docs\client\models\schemas.md#checktarget)*|
|<span class="no-wrap-code">[`check_type`](\docs\client\models\table_comparisons.md#checktype)</span>|The target type of checks to run. Supported values are *profiling*, *monitoring* and *partitioned*.|*[CheckType](\docs\client\models\table_comparisons.md#checktype)*|
|<span class="no-wrap-code">[`time_scale`](\docs\client\models\common.md#checktimescale)</span>|The time scale of *monitoring* or *partitioned* checks to run. Supports running only *daily* or *monthly* checks. Daily monitoring checks will replace today&#x27;s value for all captured check results.|*[CheckTimeScale](\docs\client\models\common.md#checktimescale)*|
|<span class="no-wrap-code">`check_category`</span>|The target check category, for example: *nulls*, *volume*, *anomaly*.|*string*|
|<span class="no-wrap-code">`table_comparison_name`</span>|The name of a configured table comparison. When the table comparison is provided, DQOps will only perform table comparison checks that compare data between tables.|*string*|
|<span class="no-wrap-code">`check_name`</span>|The target check name to run only this named check. Uses the short check name which is the name of the deepest folder in the *checks* folder. This field supports search patterns such as: &#x27;profiling_\*&#x27;, &#x27;\*_count&#x27;, &#x27;profiling_\*_percent&#x27;.|*string*|
|<span class="no-wrap-code">`sensor_name`</span>|The target sensor name to run only data quality checks that are using this sensor. Uses the full sensor name which is the full folder path within the *sensors* folder. This field supports search patterns such as: &#x27;table/volume/row_\*&#x27;, &#x27;\*_count&#x27;, &#x27;table/volume/prefix_\*_suffix&#x27;.|*string*|
|<span class="no-wrap-code">`connection`</span>|The connection (data source) name. Supports search patterns in the format: &#x27;source\*&#x27;, &#x27;\*_prod&#x27;, &#x27;prefix\*suffix&#x27;.|*string*|
|<span class="no-wrap-code">`full_table_name`</span>|The schema and table name. It is provided as *&lt;schema_name&gt;.&lt;table_name&gt;*, for example *public.fact_sales*. The schema and table name accept patterns both in the schema name and table name parts. Sample patterns are: &#x27;schema_name.tab_prefix_\*&#x27;, &#x27;schema_name.*&#x27;, &#x27;*.*&#x27;, &#x27;schema_name.\*_customer&#x27;, &#x27;schema_name.tab_\*_suffix&#x27;.|*string*|
|<span class="no-wrap-code">`enabled`</span>|A boolean flag to target enabled tables, columns or checks. When the value of this field is not set, the default value of this field is *true*, targeting only tables, columns and checks that are not implicitly disabled.|*boolean*|


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
|<span class="no-wrap-code">[`check_target`](\docs\client\models\schemas.md#checktarget)</span>|The check target (table or column).|*[CheckTarget](\docs\client\models\schemas.md#checktarget)*|
|<span class="no-wrap-code">[`check_type`](\docs\client\models\table_comparisons.md#checktype)</span>|The check type.|*[CheckType](\docs\client\models\table_comparisons.md#checktype)*|
|<span class="no-wrap-code">[`time_scale`](\docs\client\models\common.md#checktimescale)</span>|The time scale (daily, monthly). The time scale is optional and could be null (for profiling checks).|*[CheckTimeScale](\docs\client\models\common.md#checktimescale)*|
|<span class="no-wrap-code">`category`</span>|The check&#x27;s category.|*string*|
|<span class="no-wrap-code">`check_name`</span>|The similar check name in another category.|*string*|


___

## CheckModel
Model that returns the form definition and the form data to edit a single data quality check.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`check_name`</span>|Data quality check name that is used in YAML.|*string*|
|<span class="no-wrap-code">`help_text`</span>|Help text that describes the data quality check.|*string*|
|<span class="no-wrap-code">`sensor_parameters`</span>|List of fields for editing the sensor parameters.|*List[[FieldModel](\docs\client\models\common.md#fieldmodel)]*|
|<span class="no-wrap-code">`sensor_name`</span>|Full sensor name. This field is for information purposes and could be used to create additional custom checks that are reusing the same data quality sensor.|*string*|
|<span class="no-wrap-code">`quality_dimension`</span>|Data quality dimension used for tagging the results of this data quality checks.|*string*|
|<span class="no-wrap-code">[`rule`](#rulethresholdsmodel)</span>|Threshold (alerting) rules defined for a check.|*[RuleThresholdsModel](#rulethresholdsmodel)*|
|<span class="no-wrap-code">`supports_grouping`</span>|The data quality check supports a custom data grouping configuration.|*boolean*|
|<span class="no-wrap-code">`standard`</span>|This is a standard data quality check that is always shown on the data quality checks editor screen. Non-standard data quality checks (when the value is false) are advanced checks that are shown when the user decides to expand the list of checks.|*boolean*|
|<span class="no-wrap-code">[`data_grouping_override`](\docs\reference\yaml\ConnectionYaml\#datagroupingconfigurationspec)</span>|Data grouping configuration for this check. When a data grouping configuration is assigned at a check level, it overrides the data grouping configuration from the table level. Data grouping is configured in two cases: (1) the data in the table should be analyzed with a GROUP BY condition, to analyze different groups of rows using separate time series, for example a table contains data from multiple countries and there is a &#x27;country&#x27; column used for partitioning. (2) a static data grouping configuration is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). |*[DataGroupingConfigurationSpec](\docs\reference\yaml\ConnectionYaml\#datagroupingconfigurationspec)*|
|<span class="no-wrap-code">[`schedule_override`](\docs\client\models\common.md#monitoringschedulespec)</span>|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|*[MonitoringScheduleSpec](\docs\client\models\common.md#monitoringschedulespec)*|
|<span class="no-wrap-code">[`effective_schedule`](#effectiveschedulemodel)</span>|Model of configured schedule enabled on the check level.|*[EffectiveScheduleModel](#effectiveschedulemodel)*|
|<span class="no-wrap-code">[`schedule_enabled_status`](#scheduleenabledstatusmodel)</span>|State of the scheduling override for this check.|*[ScheduleEnabledStatusModel](#scheduleenabledstatusmodel)*|
|<span class="no-wrap-code">[`comments`](#commentslistspec)</span>|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|*[CommentsListSpec](#commentslistspec)*|
|<span class="no-wrap-code">`disabled`</span>|Disables the data quality check. Only enabled checks are executed. The sensor should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|*boolean*|
|<span class="no-wrap-code">`exclude_from_kpi`</span>|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|*boolean*|
|<span class="no-wrap-code">`include_in_sla`</span>|Marks the data quality check as part of a data quality SLA (Data Contract). The data quality SLA is a set of critical data quality checks that must always pass and are considered as a Data Contract for the dataset.|*boolean*|
|<span class="no-wrap-code">`configured`</span>|True if the data quality check is configured (not null). When saving the data quality check configuration, set the flag to true for storing the check.|*boolean*|
|<span class="no-wrap-code">`filter`</span>|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|*string*|
|<span class="no-wrap-code">[`run_checks_job_template`](#checksearchfilters)</span>|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to start the job.|*[CheckSearchFilters](#checksearchfilters)*|
|<span class="no-wrap-code">[`data_clean_job_template`](\docs\client\models\jobs.md#deletestoreddataqueuejobparameters)</span>|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this check.|*[DeleteStoredDataQueueJobParameters](\docs\client\models\jobs.md#deletestoreddataqueuejobparameters)*|
|<span class="no-wrap-code">`data_grouping_configuration`</span>|The name of a data grouping configuration defined at a table that should be used for this check.|*string*|
|<span class="no-wrap-code">[`check_target`](#checktargetmodel)</span>|Type of the check&#x27;s target (column, table).|*[CheckTargetModel](#checktargetmodel)*|
|<span class="no-wrap-code">`configuration_requirements_errors`</span>|List of configuration errors that must be fixed before the data quality check could be executed.|*List[string]*|
|<span class="no-wrap-code">`similar_checks`</span>|List of similar checks in other check types or in other time scales.|*List[[SimilarCheckModel](#similarcheckmodel)]*|
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
|<span class="no-wrap-code">`comparison_name`</span>|The name of the reference table configuration used for a cross table data comparison (when the category is &#x27;comparisons&#x27;).|*string*|
|<span class="no-wrap-code">`compare_to_column`</span>|The name of the column in the reference table that is compared.|*string*|
|<span class="no-wrap-code">`help_text`</span>|Help text that describes the category.|*string*|
|<span class="no-wrap-code">`checks`</span>|List of data quality checks within the category.|*List[[CheckModel](#checkmodel)]*|
|<span class="no-wrap-code">[`run_checks_job_template`](\docs\client\models\common.md#checksearchfilters)</span>|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to start the job.|*[CheckSearchFilters](\docs\client\models\common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`data_clean_job_template`](\docs\client\models\jobs.md#deletestoreddataqueuejobparameters)</span>|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this quality category.|*[DeleteStoredDataQueueJobParameters](\docs\client\models\jobs.md#deletestoreddataqueuejobparameters)*|


___

## CheckContainerModel
Model that returns the form definition and the form data to edit all data quality checks divided by categories.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`categories`</span>|List of all data quality categories that contain data quality checks inside.|*List[[QualityCategoryModel](#qualitycategorymodel)]*|
|<span class="no-wrap-code">[`effective_schedule`](\docs\client\models\common.md#effectiveschedulemodel)</span>|Model of configured schedule enabled on the check container.|*[EffectiveScheduleModel](\docs\client\models\common.md#effectiveschedulemodel)*|
|<span class="no-wrap-code">[`effective_schedule_enabled_status`](\docs\client\models\common.md#scheduleenabledstatusmodel)</span>|State of the effective scheduling on the check container.|*[ScheduleEnabledStatusModel](\docs\client\models\common.md#scheduleenabledstatusmodel)*|
|<span class="no-wrap-code">`partition_by_column`</span>|The name of the column that partitioned checks will use for the time period partitioning. Important only for partitioned checks.|*string*|
|<span class="no-wrap-code">[`run_checks_job_template`](\docs\client\models\common.md#checksearchfilters)</span>|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to start the job.|*[CheckSearchFilters](\docs\client\models\common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`data_clean_job_template`](\docs\client\models\jobs.md#deletestoreddataqueuejobparameters)</span>|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this check container|*[DeleteStoredDataQueueJobParameters](\docs\client\models\jobs.md#deletestoreddataqueuejobparameters)*|
|<span class="no-wrap-code">`can_edit`</span>|Boolean flag that decides if the current user can edit the check.|*boolean*|
|<span class="no-wrap-code">`can_run_checks`</span>|Boolean flag that decides if the current user can run checks.|*boolean*|
|<span class="no-wrap-code">`can_delete_data`</span>|Boolean flag that decides if the current user can delete data (results).|*boolean*|


___

## CheckContainerTypeModel
Model identifying the check type and timescale of checks belonging to a container.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_type`](\docs\client\models\table_comparisons.md#checktype)</span>|Check type.|*[CheckType](\docs\client\models\table_comparisons.md#checktype)*|
|<span class="no-wrap-code">[`check_time_scale`](\docs\client\models\common.md#checktimescale)</span>|Check timescale.|*[CheckTimeScale](\docs\client\models\common.md#checktimescale)*|


___

## CheckTemplate
Model depicting a named data quality check that can potentially be enabled, regardless to its position in hierarchy tree.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">[`check_target`](\docs\client\models\schemas.md#checktarget)</span>|Check target (table, column)|*[CheckTarget](\docs\client\models\schemas.md#checktarget)*|
|<span class="no-wrap-code">`check_category`</span>|Data quality check category.|*string*|
|<span class="no-wrap-code">`check_name`</span>|Data quality check name that is used in YAML.|*string*|
|<span class="no-wrap-code">`help_text`</span>|Help text that describes the data quality check.|*string*|
|<span class="no-wrap-code">[`check_container_type`](#checkcontainertypemodel)</span>|Check type with time-scale.|*[CheckContainerTypeModel](#checkcontainertypemodel)*|
|<span class="no-wrap-code">`sensor_name`</span>|Full sensor name.|*string*|
|<span class="no-wrap-code">[`check_model`](\docs\client\models\common.md#checkmodel)</span>|Template of the check model with the sensor parameters and rule parameters|*[CheckModel](\docs\client\models\common.md#checkmodel)*|
|<span class="no-wrap-code">`sensor_parameters_definitions`</span>|List of sensor parameter fields definitions.|*List[[ParameterDefinitionSpec](\docs\reference\yaml\SensorDefinitionYaml\#parameterdefinitionspec)]*|
|<span class="no-wrap-code">`rule_parameters_definitions`</span>|List of threshold (alerting) rule&#x27;s parameters definitions (for a single rule, regardless of severity).|*List[[ParameterDefinitionSpec](\docs\reference\yaml\SensorDefinitionYaml\#parameterdefinitionspec)]*|


___

## ProviderType
Data source provider type (dialect type).
  We will use lower case names to avoid issues with parsing, even if the enum names are not named following the Java naming convention.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|bigquery<br/>databricks<br/>mysql<br/>oracle<br/>postgresql<br/>presto<br/>redshift<br/>snowflake<br/>spark<br/>sqlserver<br/>trino<br/>|

___

## ConnectionModel
Connection model returned by the rest api that is limited only to the basic fields, excluding nested nodes.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|<span class="no-wrap-code">`connection_name`</span>|Connection name.|*string*|
|<span class="no-wrap-code">`connection_hash`</span>|Connection hash that identifies the connection using a unique hash code.|*long*|
|<span class="no-wrap-code">`parallel_jobs_limit`</span>|The concurrency limit for the maximum number of parallel SQL queries executed on this connection.|*integer*|
|<span class="no-wrap-code">[`provider_type`](#providertype)</span>|Database provider type (required). Accepts: bigquery, snowflake, etc.|*[ProviderType](#providertype)*|
|<span class="no-wrap-code">[`bigquery`](\docs\reference\yaml\ConnectionYaml\#bigqueryparametersspec)</span>|BigQuery connection parameters. Specify parameters in the bigquery section.|*[BigQueryParametersSpec](\docs\reference\yaml\ConnectionYaml\#bigqueryparametersspec)*|
|<span class="no-wrap-code">[`snowflake`](\docs\reference\yaml\ConnectionYaml\#snowflakeparametersspec)</span>|Snowflake connection parameters.|*[SnowflakeParametersSpec](\docs\reference\yaml\ConnectionYaml\#snowflakeparametersspec)*|
|<span class="no-wrap-code">[`postgresql`](\docs\reference\yaml\ConnectionYaml\#postgresqlparametersspec)</span>|PostgreSQL connection parameters.|*[PostgresqlParametersSpec](\docs\reference\yaml\ConnectionYaml\#postgresqlparametersspec)*|
|<span class="no-wrap-code">[`redshift`](\docs\reference\yaml\ConnectionYaml\#redshiftparametersspec)</span>|Redshift connection parameters.|*[RedshiftParametersSpec](\docs\reference\yaml\ConnectionYaml\#redshiftparametersspec)*|
|<span class="no-wrap-code">[`sqlserver`](\docs\reference\yaml\ConnectionYaml\#sqlserverparametersspec)</span>|SqlServer connection parameters.|*[SqlServerParametersSpec](\docs\reference\yaml\ConnectionYaml\#sqlserverparametersspec)*|
|<span class="no-wrap-code">[`presto`](\docs\reference\yaml\ConnectionYaml\#prestoparametersspec)</span>|Presto connection parameters.|*[PrestoParametersSpec](\docs\reference\yaml\ConnectionYaml\#prestoparametersspec)*|
|<span class="no-wrap-code">[`trino`](\docs\reference\yaml\ConnectionYaml\#trinoparametersspec)</span>|Trino connection parameters.|*[TrinoParametersSpec](\docs\reference\yaml\ConnectionYaml\#trinoparametersspec)*|
|<span class="no-wrap-code">[`mysql`](\docs\reference\yaml\ConnectionYaml\#mysqlparametersspec)</span>|MySQL connection parameters.|*[MysqlParametersSpec](\docs\reference\yaml\ConnectionYaml\#mysqlparametersspec)*|
|<span class="no-wrap-code">[`oracle`](\docs\reference\yaml\ConnectionYaml\#oracleparametersspec)</span>|Oracle connection parameters.|*[OracleParametersSpec](\docs\reference\yaml\ConnectionYaml\#oracleparametersspec)*|
|<span class="no-wrap-code">[`spark`](\docs\reference\yaml\ConnectionYaml\#sparkparametersspec)</span>|Spark connection parameters.|*[SparkParametersSpec](\docs\reference\yaml\ConnectionYaml\#sparkparametersspec)*|
|<span class="no-wrap-code">[`databricks`](\docs\reference\yaml\ConnectionYaml\#databricksparametersspec)</span>|Databricks connection parameters.|*[DatabricksParametersSpec](\docs\reference\yaml\ConnectionYaml\#databricksparametersspec)*|
|<span class="no-wrap-code">[`run_checks_job_template`](\docs\client\models\common.md#checksearchfilters)</span>|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run all checks within this connection.|*[CheckSearchFilters](\docs\client\models\common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_profiling_checks_job_template`](\docs\client\models\common.md#checksearchfilters)</span>|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run profiling checks within this connection.|*[CheckSearchFilters](\docs\client\models\common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_monitoring_checks_job_template`](\docs\client\models\common.md#checksearchfilters)</span>|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run monitoring checks within this connection.|*[CheckSearchFilters](\docs\client\models\common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`run_partition_checks_job_template`](\docs\client\models\common.md#checksearchfilters)</span>|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run partition partitioned checks within this connection.|*[CheckSearchFilters](\docs\client\models\common.md#checksearchfilters)*|
|<span class="no-wrap-code">[`collect_statistics_job_template`](\docs\client\models\jobs.md#statisticscollectorsearchfilters)</span>|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors within this connection.|*[StatisticsCollectorSearchFilters](\docs\client\models\jobs.md#statisticscollectorsearchfilters)*|
|<span class="no-wrap-code">[`data_clean_job_template`](\docs\client\models\jobs.md#deletestoreddataqueuejobparameters)</span>|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this connection.|*[DeleteStoredDataQueueJobParameters](\docs\client\models\jobs.md#deletestoreddataqueuejobparameters)*|
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
|<span class="no-wrap-code">[`parent_job_id`](\docs\client\models\common.md#dqoqueuejobid)</span>|Parent job id. Filled only for nested jobs, for example a sub-job that runs data quality checks on a single table.|*[DqoQueueJobId](\docs\client\models\common.md#dqoqueuejobid)*|


___

