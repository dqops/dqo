
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
|[definition](../../reference/yaml/SensorDefinitionYaml.md#parameterdefinitionspec)|Field name that matches the field name (snake_case) used in the YAML specification.|[ParameterDefinitionSpec](../../reference/yaml/SensorDefinitionYaml.md#parameterdefinitionspec)|
|optional|Field value is optional and may be null, when false - the field is required and must be filled.|boolean|
|string_value|Field value for a string field.|string|
|boolean_value|Field value for a boolean field.|boolean|
|integer_value|Field value for an integer (32-bit) field.|integer|
|long_value|Field value for a long (64-bit) field.|long|
|double_value|Field value for a double field.|double|
|datetime_value|Field value for a date time field.|datetime|
|column_name_value|Field value for a column name field.|string|
|enum_value|Field value for an enum (choice) field.|string|
|string_list_value|Field value for an array (list) of strings.|List[string]|
|integer_list_value|Field value for an array (list) of integers, using 64 bit integers.|List[integer]|
|date_value|Field value for an date.|date|


___

## RuleParametersModel
Model that returns the form definition and the form data to edit parameters (thresholds) for a rule at a single severity level (low, medium, high).


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|rule_name|Full rule name. This field is for information purposes and could be used to create additional custom checks that are reusing the same data quality rule.|string|
|rule_parameters|List of fields for editing the rule parameters like thresholds.|List[[FieldModel](./Common.md#fieldmodel)]|
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
|[check_target](./schemas.md#CheckTarget)|Check target (table or column).|[CheckTarget](./schemas.md#CheckTarget)|
|[check_type](./table_comparisons.md#CheckType)|Check type (profiling, monitoring, partitioned).|[CheckType](./table_comparisons.md#CheckType)|
|[check_time_scale](./Common.md#CheckTimeScale)|Check timescale (for monitoring and partitioned checks).|[CheckTimeScale](./Common.md#CheckTimeScale)|
|category_name|Category to which this check belongs.|string|
|check_name|Check name that is used in YAML file.|string|
|sensor_parameters|List of fields for editing the sensor parameters.|List[[FieldModel](#fieldmodel)]|
|table_level_filter|SQL WHERE clause added to the sensor query for every check on this table.|string|
|sensor_level_filter|SQL WHERE clause added to the sensor query for this check.|string|
|[warning](#ruleparametersmodel)|Rule parameters for the warning severity rule.|[RuleParametersModel](#ruleparametersmodel)|
|[error](./Common.md#ruleparametersmodel)|Rule parameters for the error severity rule.|[RuleParametersModel](./Common.md#ruleparametersmodel)|
|[fatal](./Common.md#ruleparametersmodel)|Rule parameters for the fatal severity rule.|[RuleParametersModel](./Common.md#ruleparametersmodel)|
|disabled|Whether the check has been disabled.|boolean|
|configured|Whether the check is configured (not null).|boolean|


___

## CheckListModel
Simplistic model that returns a single data quality check, its name and &quot;configured&quot; flag.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_category|Check category.|string|
|check_name|Data quality check name that is used in YAML.|string|
|help_text|Help text that describes the data quality check.|string|
|configured|True if the data quality check is configured (not null). When saving the data quality check configuration, set the flag to true for storing the check.|boolean|


___

## CheckContainerListModel
Simplistic model that returns the list of data quality checks, their names, categories and &quot;configured&quot; flag.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|checks|Simplistic list of all data quality checks.|List[[CheckListModel](#checklistmodel)]|
|can_edit|Boolean flag that decides if the current user can edit the check.|boolean|
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean|
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean|


___

## RuleThresholdsModel
Model that returns the form definition and the form data to edit a single rule with all three threshold levels (low, medium, high).


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[error](./Common.md#ruleparametersmodel)|Rule parameters for the error severity rule.|[RuleParametersModel](./Common.md#ruleparametersmodel)|
|[warning](./Common.md#ruleparametersmodel)|Rule parameters for the warning severity rule.|[RuleParametersModel](./Common.md#ruleparametersmodel)|
|[fatal](./Common.md#ruleparametersmodel)|Rule parameters for the fatal severity rule.|[RuleParametersModel](./Common.md#ruleparametersmodel)|


___

## MonitoringScheduleSpec
Monitoring job schedule specification.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|cron_expression|Unix style cron expression that specifies when to execute scheduled operations like running data quality checks or synchronizing the configuration with the cloud.|string|
|disabled|Disables the schedule. When the value of this &#x27;disable&#x27; field is false, the schedule is stored in the metadata but it is not activated to run data quality checks.|boolean|


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
|[schedule_group](./Common.md#CheckRunScheduleGroup)|Field value for a schedule group to which this schedule belongs.|[CheckRunScheduleGroup](./Common.md#CheckRunScheduleGroup)|
|[schedule_level](#effectiveschedulelevelmodel)|Field value for the level at which the schedule has been configured.|[EffectiveScheduleLevelModel](#effectiveschedulelevelmodel)|
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

## CommentSpec
Comment entry. Comments are added when a change was made and the change should be recorded in a persisted format.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|date|Comment date and time|datetime|
|comment_by|Commented by|string|
|comment|Comment text|string|


___

## CommentsListSpec
List of comments.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|self||List[[CommentSpec](./Common.md#commentspec)]|


___

## CheckSearchFilters
Target data quality checks filter, identifies which checks on which tables and columns should be executed.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|column|The column name. This field accepts search patterns in the format: &#x27;fk_\*&#x27;, &#x27;\*_id&#x27;, &#x27;prefix\*suffix&#x27;.|string|
|column_data_type|The column data type that was imported from the data source and is stored in the [columns -&gt; column_name -&gt; type_snapshot -&gt; column_type](../../reference/yaml/TableYaml.md#columntypesnapshotspec) field in the *.dqotable.yaml* file.|string|
|column_nullable|Optional filter to find only nullable (when the value is *true*) or not nullable (when the value is *false*) columns, based on the value of the [columns -&gt; column_name -&gt; type_snapshot -&gt; nullable](../../reference/yaml/TableYaml.md#columntypesnapshotspec) field in the *.dqotable.yaml* file.|boolean|
|[check_target](./schemas.md#CheckTarget)|The target type of object to run checks. Supported values are: *table* to run only table level checks or *column* to run only column level checks.|[CheckTarget](./schemas.md#CheckTarget)|
|[check_type](./table_comparisons.md#CheckType)|The target type of checks to run. Supported values are *profiling*, *monitoring* and *partitioned*.|[CheckType](./table_comparisons.md#CheckType)|
|[time_scale](./Common.md#checktimescale)|The time scale of *monitoring* or *partitioned* checks to run. Supports running only *daily* or *monthly* checks. Daily monitoring checks will replace today&#x27;s value for all captured check results.|[CheckTimeScale](./Common.md#checktimescale)|
|check_category|The target check category, for example: *nulls*, *volume*, *anomaly*.|string|
|table_comparison_name|The name of a configured table comparison. When the table comparison is provided, DQOps will only perform table comparison checks that compare data between tables.|string|
|check_name|The target check name to run only this named check. Uses the short check name which is the name of the deepest folder in the *checks* folder. This field supports search patterns such as: &#x27;profiling_\*&#x27;, &#x27;\*_count&#x27;, &#x27;profiling_\*_percent&#x27;.|string|
|sensor_name|The target sensor name to run only data quality checks that are using this sensor. Uses the full sensor name which is the full folder path within the *sensors* folder. This field supports search patterns such as: &#x27;table/volume/row_\*&#x27;, &#x27;\*_count&#x27;, &#x27;table/volume/prefix_\*_suffix&#x27;.|string|
|connection|The connection (data source) name. Supports search patterns in the format: &#x27;source\*&#x27;, &#x27;\*_prod&#x27;, &#x27;prefix\*suffix&#x27;.|string|
|full_table_name|The schema and table name. It is provided as *&lt;schema_name&gt;.&lt;table_name&gt;*, for example *public.fact_sales*. The schema and table name accept patterns both in the schema name and table name parts. Sample patterns are: &#x27;schema_name.tab_prefix_\*&#x27;, &#x27;schema_name.*&#x27;, &#x27;*.*&#x27;, &#x27;schema_name.\*_customer&#x27;, &#x27;schema_name.tab_\*_suffix&#x27;.|string|
|enabled|A boolean flag to target enabled tables, columns or checks. When the value of this field is not set, the default value of this field is *true*, targeting only tables, columns and checks that are not implicitly disabled.|boolean|


___

## CheckTargetModel
Enumeration of possible targets for check model request result.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|column<br/>table<br/>|

___

## SimilarCheckModel
Describes a single check that is similar to other checks in other check types.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_target](./schemas.md#CheckTarget)|The check target (table or column).|[CheckTarget](./schemas.md#CheckTarget)|
|[check_type](./table_comparisons.md#CheckType)|The check type.|[CheckType](./table_comparisons.md#CheckType)|
|[time_scale](./Common.md#checktimescale)|The time scale (daily, monthly). The time scale is optional and could be null (for profiling checks).|[CheckTimeScale](./Common.md#checktimescale)|
|category|The check&#x27;s category.|string|
|check_name|The similar check name in another category.|string|


___

## CheckModel
Model that returns the form definition and the form data to edit a single data quality check.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_name|Data quality check name that is used in YAML.|string|
|help_text|Help text that describes the data quality check.|string|
|sensor_parameters|List of fields for editing the sensor parameters.|List[[FieldModel](./Common.md#fieldmodel)]|
|sensor_name|Full sensor name. This field is for information purposes and could be used to create additional custom checks that are reusing the same data quality sensor.|string|
|quality_dimension|Data quality dimension used for tagging the results of this data quality checks.|string|
|[rule](#rulethresholdsmodel)|Threshold (alerting) rules defined for a check.|[RuleThresholdsModel](#rulethresholdsmodel)|
|supports_grouping|The data quality check supports a custom data grouping configuration.|boolean|
|standard|This is a standard data quality check that is always shown on the data quality checks editor screen. Non-standard data quality checks (when the value is false) are advanced checks that are shown when the user decides to expand the list of checks.|boolean|
|[data_grouping_override](../../reference/yaml/ConnectionYaml.md#datagroupingconfigurationspec)|Data grouping configuration for this check. When a data grouping configuration is assigned at a check level, it overrides the data grouping configuration from the table level. Data grouping is configured in two cases: (1) the data in the table should be analyzed with a GROUP BY condition, to analyze different groups of rows using separate time series, for example a table contains data from multiple countries and there is a &#x27;country&#x27; column used for partitioning. (2) a static data grouping configuration is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). |[DataGroupingConfigurationSpec](../../reference/yaml/ConnectionYaml.md#datagroupingconfigurationspec)|
|[schedule_override](./Common.md#MonitoringScheduleSpec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[MonitoringScheduleSpec](./Common.md#MonitoringScheduleSpec)|
|[effective_schedule](#effectiveschedulemodel)|Model of configured schedule enabled on the check level.|[EffectiveScheduleModel](#effectiveschedulemodel)|
|[schedule_enabled_status](#scheduleenabledstatusmodel)|State of the scheduling override for this check.|[ScheduleEnabledStatusModel](#scheduleenabledstatusmodel)|
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)|
|disabled|Disables the data quality check. Only enabled checks are executed. The sensor should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean|
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean|
|include_in_sla|Marks the data quality check as part of a data quality SLA (Data Contract). The data quality SLA is a set of critical data quality checks that must always pass and are considered as a Data Contract for the dataset.|boolean|
|configured|True if the data quality check is configured (not null). When saving the data quality check configuration, set the flag to true for storing the check.|boolean|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string|
|[run_checks_job_template](#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to start the job.|[CheckSearchFilters](#checksearchfilters)|
|[data_clean_job_template](./jobs.md#DeleteStoredDataQueueJobParameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this check.|[DeleteStoredDataQueueJobParameters](./jobs.md#DeleteStoredDataQueueJobParameters)|
|data_grouping_configuration|The name of a data grouping configuration defined at a table that should be used for this check.|string|
|[check_target](#checktargetmodel)|Type of the check&#x27;s target (column, table).|[CheckTargetModel](#checktargetmodel)|
|configuration_requirements_errors|List of configuration errors that must be fixed before the data quality check could be executed.|List[string]|
|similar_checks|List of similar checks in other check types or in other time scales.|List[[SimilarCheckModel](#similarcheckmodel)]|
|can_edit|Boolean flag that decides if the current user can edit the check.|boolean|
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean|
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean|


___

## QualityCategoryModel
Model that returns the form definition and the form data to edit all checks within a single category.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|category|Data quality check category name.|string|
|comparison_name|The name of the reference table configuration used for a cross table data comparison (when the category is &#x27;comparisons&#x27;).|string|
|compare_to_column|The name of the column in the reference table that is compared.|string|
|help_text|Help text that describes the category.|string|
|checks|List of data quality checks within the category.|List[[CheckModel](#checkmodel)]|
|[run_checks_job_template](./Common.md#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to start the job.|[CheckSearchFilters](./Common.md#checksearchfilters)|
|[data_clean_job_template](./jobs.md#DeleteStoredDataQueueJobParameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this quality category.|[DeleteStoredDataQueueJobParameters](./jobs.md#DeleteStoredDataQueueJobParameters)|


___

## CheckContainerModel
Model that returns the form definition and the form data to edit all data quality checks divided by categories.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|categories|List of all data quality categories that contain data quality checks inside.|List[[QualityCategoryModel](#qualitycategorymodel)]|
|[effective_schedule](./Common.md#effectiveschedulemodel)|Model of configured schedule enabled on the check container.|[EffectiveScheduleModel](./Common.md#effectiveschedulemodel)|
|[effective_schedule_enabled_status](./Common.md#scheduleenabledstatusmodel)|State of the effective scheduling on the check container.|[ScheduleEnabledStatusModel](./Common.md#scheduleenabledstatusmodel)|
|partition_by_column|The name of the column that partitioned checks will use for the time period partitioning. Important only for partitioned checks.|string|
|[run_checks_job_template](./Common.md#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to start the job.|[CheckSearchFilters](./Common.md#checksearchfilters)|
|[data_clean_job_template](./jobs.md#DeleteStoredDataQueueJobParameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this check container|[DeleteStoredDataQueueJobParameters](./jobs.md#DeleteStoredDataQueueJobParameters)|
|can_edit|Boolean flag that decides if the current user can edit the check.|boolean|
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean|
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean|


___

## CheckContainerTypeModel
Model identifying the check type and timescale of checks belonging to a container.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_type](./table_comparisons.md#CheckType)|Check type.|[CheckType](./table_comparisons.md#CheckType)|
|[check_time_scale](./Common.md#checktimescale)|Check timescale.|[CheckTimeScale](./Common.md#checktimescale)|


___

## CheckTemplate
Model depicting a named data quality check that can potentially be enabled, regardless to its position in hierarchy tree.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_target](./schemas.md#CheckTarget)|Check target (table, column)|[CheckTarget](./schemas.md#CheckTarget)|
|check_category|Data quality check category.|string|
|check_name|Data quality check name that is used in YAML.|string|
|help_text|Help text that describes the data quality check.|string|
|[check_container_type](#checkcontainertypemodel)|Check type with time-scale.|[CheckContainerTypeModel](#checkcontainertypemodel)|
|sensor_name|Full sensor name.|string|
|[check_model](./Common.md#checkmodel)|Template of the check model with the sensor parameters and rule parameters|[CheckModel](./Common.md#checkmodel)|
|sensor_parameters_definitions|List of sensor parameter fields definitions.|List[[ParameterDefinitionSpec](../../reference/yaml/SensorDefinitionYaml.md#parameterdefinitionspec)]|
|rule_parameters_definitions|List of threshold (alerting) rule&#x27;s parameters definitions (for a single rule, regardless of severity).|List[[ParameterDefinitionSpec](../../reference/yaml/SensorDefinitionYaml.md#parameterdefinitionspec)]|


___

## ProviderType
Data source provider type (dialect type).
  We will use lower case names to avoid issues with parsing, even if the enum names are not named following the Java naming convention.


**The structure of this object is described below**


|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|
|-----------|-------------|
|string|snowflake<br/>oracle<br/>postgresql<br/>redshift<br/>sqlserver<br/>trino<br/>spark<br/>databricks<br/>mysql<br/>bigquery<br/>presto<br/>|

___

## ConnectionModel
Connection model returned by the rest api that is limited only to the basic fields, excluding nested nodes.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|connection_hash|Connection hash that identifies the connection using a unique hash code.|long|
|parallel_jobs_limit|The concurrency limit for the maximum number of parallel SQL queries executed on this connection.|integer|
|[provider_type](#providertype)|Database provider type (required). Accepts: bigquery, snowflake, etc.|[ProviderType](#providertype)|
|[bigquery](../../reference/yaml/ConnectionYaml.md#bigqueryparametersspec)|BigQuery connection parameters. Specify parameters in the bigquery section.|[BigQueryParametersSpec](../../reference/yaml/ConnectionYaml.md#bigqueryparametersspec)|
|[snowflake](../../reference/yaml/ConnectionYaml.md#snowflakeparametersspec)|Snowflake connection parameters.|[SnowflakeParametersSpec](../../reference/yaml/ConnectionYaml.md#snowflakeparametersspec)|
|[postgresql](../../reference/yaml/ConnectionYaml.md#postgresqlparametersspec)|PostgreSQL connection parameters.|[PostgresqlParametersSpec](../../reference/yaml/ConnectionYaml.md#postgresqlparametersspec)|
|[redshift](../../reference/yaml/ConnectionYaml.md#redshiftparametersspec)|Redshift connection parameters.|[RedshiftParametersSpec](../../reference/yaml/ConnectionYaml.md#redshiftparametersspec)|
|[sqlserver](../../reference/yaml/ConnectionYaml.md#sqlserverparametersspec)|SqlServer connection parameters.|[SqlServerParametersSpec](../../reference/yaml/ConnectionYaml.md#sqlserverparametersspec)|
|[presto](../../reference/yaml/ConnectionYaml.md#prestoparametersspec)|Presto connection parameters.|[PrestoParametersSpec](../../reference/yaml/ConnectionYaml.md#prestoparametersspec)|
|[trino](../../reference/yaml/ConnectionYaml.md#trinoparametersspec)|Trino connection parameters.|[TrinoParametersSpec](../../reference/yaml/ConnectionYaml.md#trinoparametersspec)|
|[mysql](../../reference/yaml/ConnectionYaml.md#mysqlparametersspec)|MySQL connection parameters.|[MysqlParametersSpec](../../reference/yaml/ConnectionYaml.md#mysqlparametersspec)|
|[oracle](../../reference/yaml/ConnectionYaml.md#oracleparametersspec)|Oracle connection parameters.|[OracleParametersSpec](../../reference/yaml/ConnectionYaml.md#oracleparametersspec)|
|[spark](../../reference/yaml/ConnectionYaml.md#sparkparametersspec)|Spark connection parameters.|[SparkParametersSpec](../../reference/yaml/ConnectionYaml.md#sparkparametersspec)|
|[databricks](../../reference/yaml/ConnectionYaml.md#databricksparametersspec)|Databricks connection parameters.|[DatabricksParametersSpec](../../reference/yaml/ConnectionYaml.md#databricksparametersspec)|
|[run_checks_job_template](./Common.md#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run all checks within this connection.|[CheckSearchFilters](./Common.md#checksearchfilters)|
|[run_profiling_checks_job_template](./Common.md#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run profiling checks within this connection.|[CheckSearchFilters](./Common.md#checksearchfilters)|
|[run_monitoring_checks_job_template](./Common.md#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run monitoring checks within this connection.|[CheckSearchFilters](./Common.md#checksearchfilters)|
|[run_partition_checks_job_template](./Common.md#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run partition partitioned checks within this connection.|[CheckSearchFilters](./Common.md#checksearchfilters)|
|[collect_statistics_job_template](./jobs.md#StatisticsCollectorSearchFilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors within this connection.|[StatisticsCollectorSearchFilters](./jobs.md#StatisticsCollectorSearchFilters)|
|[data_clean_job_template](./jobs.md#DeleteStoredDataQueueJobParameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this connection.|[DeleteStoredDataQueueJobParameters](./jobs.md#DeleteStoredDataQueueJobParameters)|
|can_edit|Boolean flag that decides if the current user can update or delete the connection to the data source.|boolean|
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean|
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean|
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean|
|yaml_parsing_error|Optional parsing error that was captured when parsing the YAML file. This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.|string|


___

## DqoQueueJobId
Identifies a single job.


**The structure of this object is described below**


|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|job_id|Job id.|long|
|job_business_key|Optional job business key that was assigned to the job. A business key is an alternative user assigned unique job identifier used to find the status of a job finding it by the business key.|string|
|[parent_job_id](./Common.md#DqoQueueJobId)|Parent job id. Filled only for nested jobs, for example a sub-job that runs data quality checks on a single table.|[DqoQueueJobId](./Common.md#DqoQueueJobId)|


___

