
## CheckSearchFilters  
Hierarchy node search filters.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|column_name||string| | | |
|column_data_type||string| | | |
|column_nullable||boolean| | | |
|check_target||enum|column<br/>table<br/>| | |
|check_type||enum|profiling<br/>partitioned<br/>monitoring<br/>| | |
|time_scale||enum|daily<br/>monthly<br/>| | |
|check_category||string| | | |
|table_comparison_name||string| | | |
|check_name||string| | | |
|sensor_name||string| | | |
|check_configured||boolean| | | |
|connection_name||string| | | |
|schema_table_name||string| | | |
|enabled||boolean| | | |

___  

## RuleParametersModel  
Model that returns the form definition and the form data to edit parameters (thresholds) for a rule at a single severity level (low, medium, high).  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|rule_name|Full rule name. This field is for information purposes and could be used to create additional custom checks that are reusing the same data quality rule.|string| | | |
|disabled|Disable the rule. The rule will not be evaluated. The sensor will also not be executed if it has no enabled rules.|boolean| | | |
|configured|Returns true when the rule is configured (is not null), so it should be shown in the UI as configured (having values).|boolean| | | |

___  

## RuleThresholdsModel  
Model that returns the form definition and the form data to edit a single rule with all three threshold levels (low, medium, high).  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[error](\docs\client\models\#ruleparametersmodel)|Rule parameters for the error severity rule.|[RuleParametersModel](\docs\client\models\#ruleparametersmodel)| | | |
|[warning](\docs\client\models\#ruleparametersmodel)|Rule parameters for the warning severity rule.|[RuleParametersModel](\docs\client\models\#ruleparametersmodel)| | | |
|[fatal](\docs\client\models\#ruleparametersmodel)|Rule parameters for the fatal severity rule.|[RuleParametersModel](\docs\client\models\#ruleparametersmodel)| | | |

___  

## DataGroupingDimensionSpec  
Single data grouping dimension configuration. A data grouping dimension may be configured as a hardcoded value or a mapping to a column.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|source|The source of the data grouping dimension value. The default grouping dimension source is a tag. Assign a tag when there are multiple similar tables that store the same data for different areas (countries, etc.). This could be a country name if a table or partition stores information for that country.|enum|tag<br/>column_value<br/>| | |
|tag|The value assigned to a data quality grouping dimension when the source is &#x27;tag&#x27;. Assign a hardcoded (static) data grouping dimension value (tag) when there are multiple similar tables that store the same data for different areas (countries, etc.). This could be a country name if a table or partition stores information for that country.|string| | | |
|column|Column name that contains a dynamic data grouping dimension value (for dynamic data-driven data groupings). Sensor queries will be extended with a GROUP BY {data group level colum name}, sensors (and alerts) will be calculated for each unique value of the specified column. Also a separate time series will be tracked for each value.|column_name| | | |
|name|Data grouping dimension name.|string| | | |

___  

## DataGroupingConfigurationSpec  
Configuration of the data groupings that is used to calculate data quality checks with a GROUP BY clause.
 Data grouping levels may be hardcoded if we have different (but similar) tables for different business areas (countries, product groups).
 We can also pull data grouping levels directly from the database if a table has a column that identifies a business area.
 Data quality results for new groups are dynamically identified in the database by the GROUP BY clause. Sensor values are extracted for each data group separately,
 a time series is build for each data group separately.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[level_1](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 1 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |
|[level_2](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 2 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |
|[level_3](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 3 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |
|[level_4](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 4 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |
|[level_5](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 5 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |
|[level_6](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 6 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |
|[level_7](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 7 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |
|[level_8](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 8 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |
|[level_9](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 9 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |

___  

## MonitoringScheduleSpec  
Monitoring job schedule specification.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|cron_expression|Unix style cron expression that specifies when to execute scheduled operations like running data quality checks or synchronizing the configuration with the cloud.|string| | | |
|disabled|Disables the schedule. When the value of this &#x27;disable&#x27; field is false, the schedule is stored in the metadata but it is not activated to run data quality checks.|boolean| | | |

___  

## EffectiveScheduleModel  
Model of a configured schedule (on connection or table) or schedule override (on check). Describes the CRON expression and the time of the upcoming execution, as well as the duration until this time.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|schedule_group|Field value for a schedule group to which this schedule belongs.|enum|monitoring_monthly<br/>profiling<br/>partitioned_daily<br/>monitoring_daily<br/>partitioned_monthly<br/>| | |
|schedule_level|Field value for the level at which the schedule has been configured.|enum|check_override<br/>connection<br/>table_override<br/>| | |
|cron_expression|Field value for a CRON expression defining the scheduling.|string| | | |
|disabled|Field value stating if the schedule has been explicitly disabled.|boolean| | | |

___  

## CommentSpec  
Comment entry. Comments are added when a change was made and the change should be recorded in a persisted format.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|date|Comment date and time|datetime| | | |
|comment_by|Commented by|string| | | |
|comment|Comment text|string| | | |

___  

## CommentsListSpec  
List of comments.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|mod_count||integer| | | |

___  

## DeleteStoredDataQueueJobParameters  
Parameters for the {@link DeleteStoredDataQueueJob DeleteStoredDataQueueJob} job that deletes data stored in user&#x27;s &quot;.data&quot; directory.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name||string| | | |
|schema_table_name||string| | | |
|date_start||date| | | |
|date_end||date| | | |
|delete_errors||boolean| | | |
|delete_statistics||boolean| | | |
|delete_check_results||boolean| | | |
|delete_sensor_readouts||boolean| | | |
|column_names||string_list| | | |
|check_category||string| | | |
|table_comparison_name||string| | | |
|check_name||string| | | |
|check_type||string| | | |
|sensor_name||string| | | |
|data_group_tag||string| | | |
|quality_dimension||string| | | |
|time_gradient||string| | | |
|collector_category||string| | | |
|collector_name||string| | | |
|collector_target||string| | | |

___  

## CheckModel  
Model that returns the form definition and the form data to edit a single data quality check.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|check_name|Data quality check name that is used in YAML.|string| | | |
|help_text|Help text that describes the data quality check.|string| | | |
|sensor_name|Full sensor name. This field is for information purposes and could be used to create additional custom checks that are reusing the same data quality sensor.|string| | | |
|quality_dimension|Data quality dimension used for tagging the results of this data quality checks.|string| | | |
|[rule](#rulethresholdsmodel)|Threshold (alerting) rules defined for a check.|[RuleThresholdsModel](#rulethresholdsmodel)| | | |
|supports_grouping|The data quality check supports a custom data grouping configuration.|boolean| | | |
|[data_grouping_override](\docs\client\models\#datagroupingconfigurationspec)|Data grouping configuration for this check. When a data grouping configuration is assigned at a check level, it overrides the data grouping configuration from the table level. Data grouping is configured in two cases: (1) the data in the table should be analyzed with a GROUP BY condition, to analyze different groups of rows using separate time series, for example a table contains data from multiple countries and there is a &#x27;country&#x27; column used for partitioning. (2) a static data grouping configuration is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). |[DataGroupingConfigurationSpec](\docs\client\models\#datagroupingconfigurationspec)| | | |
|[schedule_override](\docs\client\models\#monitoringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[MonitoringScheduleSpec](\docs\client\models\#monitoringschedulespec)| | | |
|[effective_schedule](\docs\client\models\#effectiveschedulemodel)|Model of configured schedule enabled on the check level.|[EffectiveScheduleModel](\docs\client\models\#effectiveschedulemodel)| | | |
|schedule_enabled_status|State of the scheduling override for this check.|enum|not_configured<br/>disabled<br/>overridden_by_checks<br/>enabled<br/>| | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled checks are executed. The sensor should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|configured|True if the data quality check is configured (not null). When saving the data quality check configuration, set the flag to true for storing the check.|boolean| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |
|[run_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to start the job.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)| | | |
|[data_clean_job_template](\docs\client\models\jobs\#deletestoreddataqueuejobparameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this check.|[DeleteStoredDataQueueJobParameters](\docs\client\models\jobs\#deletestoreddataqueuejobparameters)| | | |
|data_grouping_configuration|The name of a data grouping configuration defined at a table that should be used for this check.|string| | | |
|check_target|Type of the check&#x27;s target (column, table).|enum|column<br/>table<br/>| | |
|configuration_requirements_errors|List of configuration errors that must be fixed before the data quality check could be executed.|string_list| | | |
|can_edit|Boolean flag that decides if the current user can edit the check.|boolean| | | |
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean| | | |
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean| | | |

___  

## AllChecksPatchParameters  
  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[check_search_filters](\docs\client\models\#checksearchfilters)|Filters addressing basic tree search parameters. These filters takes precedence over other selectors.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)| | | |
|[check_model_patch](#checkmodel)|Sample configured check model which will pasted onto selected checks.|[CheckModel](#checkmodel)| | | |
|override_conflicts|Override existing configurations if they&#x27;re present. If false, apply updates only to the fields for which no configuration exists.|boolean| | | |

___  

## BulkCheckDisableParameters  
  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[check_search_filters](\docs\client\models\#checksearchfilters)|Filters addressing basic tree search parameters. These filters takes precedence over other selectors.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)| | | |

___  

## CommonColumnModel  
Dictionary model used for combo boxes to select a column. Returns a column name that exists in any table within a connection (source)
 and a count of the column occurrence. It is used to find the most common columns.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|column_name|Column name.|string| | | |
|tables_count|Count of tables that are have a column with this name.|integer| | | |

___  

## IncidentWebhookNotificationsSpec  
Configuration of Webhook URLs used for new or updated incident&#x27;s notifications.
 Specifies the URLs of webhooks where the notification messages are sent.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|incident_opened_webhook_url|Webhook URL where the notification messages describing new incidents are pushed using a HTTP POST request. The format of the JSON message is documented in the IncidentNotificationMessage object.|string| | | |
|incident_acknowledged_webhook_url|Webhook URL where the notification messages describing acknowledged messages are pushed using a HTTP POST request. The format of the JSON message is documented in the IncidentNotificationMessage object.|string| | | |
|incident_resolved_webhook_url|Webhook URL where the notification messages describing resolved messages are pushed using a HTTP POST request. The format of the JSON message is documented in the IncidentNotificationMessage object.|string| | | |
|incident_muted_webhook_url|Webhook URL where the notification messages describing muted messages are pushed using a HTTP POST request. The format of the JSON message is documented in the IncidentNotificationMessage object.|string| | | |

___  

## ConnectionIncidentGroupingSpec  
Configuration of data quality incident grouping on a connection level. Defines how similar data quality issues are grouped into incidents.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|grouping_level|Grouping level of failed data quality checks for creating higher level data quality incidents. The default grouping level is by a table, a data quality dimension and a check category (i.e. a datatype data quality incident detected on a table X in the numeric checks category).|enum|table_dimension_category_type<br/>table_dimension<br/>table<br/>table_dimension_category<br/>table_dimension_category_name<br/>| | |
|minimum_severity|Minimum severity level of data quality issues that are grouped into incidents. The default minimum severity level is &#x27;warning&#x27;. Other supported severity levels are &#x27;error&#x27; and &#x27;fatal&#x27;.|enum|warning<br/>error<br/>fatal<br/>| | |
|divide_by_data_groups|Create separate data quality incidents for each data group, creating different incidents for different groups of rows. By default, data groups are ignored for grouping data quality issues into data quality incidents.|boolean| | | |
|max_incident_length_days|The maximum length of a data quality incident in days. When a new data quality issue is detected after max_incident_length_days days since a similar data quality was first seen, a new data quality incident is created that will capture all following data quality issues for the next max_incident_length_days days. The default value is 60 days.|integer| | | |
|mute_for_days|The number of days that all similar data quality issues are muted when a a data quality incident is closed in the &#x27;mute&#x27; status.|integer| | | |
|disabled|Disables data quality incident creation for failed data quality checks on the data source.|boolean| | | |
|[webhooks](\docs\reference\yaml\connectionyaml\#incidentwebhooknotificationsspec)|Configuration of Webhook URLs for new or updated incident notifications.|[IncidentWebhookNotificationsSpec](\docs\reference\yaml\connectionyaml\#incidentwebhooknotificationsspec)| | | |

___  

## BigQueryParametersSpec  
BigQuery connection parameters.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|source_project_id|Source GCP project ID. This is the project that has datasets that will be imported.|string| | | |
|jobs_create_project|Configures the way how to select the project that will be used to start BigQuery jobs and will be used for billing. The user/service identified by the credentials must have bigquery.jobs.create permission in that project.|enum|create_jobs_in_default_project_from_credentials<br/>create_jobs_in_source_project<br/>create_jobs_in_selected_billing_project_id<br/>| | |
|billing_project_id|Billing GCP project ID. This is the project used as the default GCP project. The calling user must have a bigquery.jobs.create permission in this project.|string| | | |
|authentication_mode|Authentication mode to the Google Cloud.|enum|json_key_content<br/>json_key_path<br/>google_application_credentials<br/>| | |
|json_key_content|JSON key content. Use an environment variable that contains the content of the key as ${KEY_ENV} or a name of a secret in the GCP Secret Manager: ${sm://key-secret-name}. Requires the authentication-mode: json_key_content.|string| | | |
|json_key_path|A path to the JSON key file. Requires the authentication-mode: json_key_path.|string| | | |
|quota_project_id|Quota GCP project ID.|string| | | |

___  

## SnowflakeParametersSpec  
Snowflake connection parameters.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|account|Snowflake account name, e.q. &lt;account&gt;, &lt;account&gt;-&lt;locator&gt;, &lt;account&gt;.&lt;region&gt; or &lt;account&gt;.&lt;region&gt;.&lt;platform&gt;.. Supports also a ${SNOWFLAKE_ACCOUNT} configuration with a custom environment variable.|string| | | |
|warehouse|Snowflake warehouse name. Supports also a ${SNOWFLAKE_WAREHOUSE} configuration with a custom environment variable.|string| | | |
|database|Snowflake database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|user|Snowflake user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|password|Snowflake database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|role|Snowflake role name. Supports also ${SNOWFLAKE_ROLE} configuration with a custom environment variable.|string| | | |

___  

## PostgresqlParametersSpec  
Postgresql connection parameters.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|host|PostgreSQL host name. Supports also a ${POSTGRESQL_HOST} configuration with a custom environment variable.|string| | | |
|port|PostgreSQL port number. The default port is 5432. Supports also a ${POSTGRESQL_PORT} configuration with a custom environment variable.|string| | | |
|database|PostgreSQL database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|user|PostgreSQL user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|password|PostgreSQL database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|options|PostgreSQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes. Supports also a ${POSTGRESQL_OPTIONS} configuration with a custom environment variable.|string| | | |
|sslmode|Sslmode PostgreSQL connection parameter. The default value is disabled.|enum|allow<br/>prefer<br/>disable<br/>require<br/>verify-full<br/>verify-ca<br/>| | |

___  

## RedshiftParametersSpec  
Redshift connection parameters.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|host|Redshift host name. Supports also a ${REDSHIFT_HOST} configuration with a custom environment variable.|string| | | |
|port|Redshift port number. The default port is 5432. Supports also a ${REDSHIFT_PORT} configuration with a custom environment variable.|string| | | |
|database|Redshift database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|user|Redshift user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|password|Redshift database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|options|Redshift connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes. Supports also a ${REDSHIFT_OPTIONS} configuration with a custom environment variable.|string| | | |

___  

## SqlServerParametersSpec  
Microsoft SQL Server connection parameters.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|host|SQL Server host name. Supports also a ${SQLSERVER_HOST} configuration with a custom environment variable.|string| | | |
|port|SQL Server port number. The default port is 1433. Supports also a ${SQLSERVER_PORT} configuration with a custom environment variable.|string| | | |
|database|SQL Server database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|user|SQL Server user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|password|SQL Server database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|options|SQL Server connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes. Supports also a ${SQLSERVER_OPTIONS} configuration with a custom environment variable.|string| | | |
|disable_encryption|Disable SSL encryption parameter. The default value is false. You may need to disable encryption when SQL Server is started in Docker.|boolean| | | |

___  

## MysqlParametersSpec  
MySql connection parameters.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|host|MySQL host name. Supports also a ${MYSQL_HOST} configuration with a custom environment variable.|string| | | |
|port|MySQL port number. The default port is 3306. Supports also a ${MYSQL_PORT} configuration with a custom environment variable.|string| | | |
|database|MySQL database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|user|MySQL user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|password|MySQL database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|options|MySQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes. Supports also a ${MYSQL_OPTIONS} configuration with a custom environment variable.|string| | | |
|sslmode|SslMode MySQL connection parameter.|enum|DISABLED<br/>PREFERRED<br/>VERIFY_IDENTITY<br/>VERIFY_CA<br/>REQUIRED<br/>| | |

___  

## OracleParametersSpec  
Oracle connection parameters.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|host|Oracle host name. Supports also a ${ORACLE_HOST} configuration with a custom environment variable.|string| | | |
|port|Oracle port number. The default port is 1521. Supports also a ${ORACLE_PORT} configuration with a custom environment variable.|string| | | |
|database|Oracle database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|user|Oracle user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|password|Oracle database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.|string| | | |
|options|Oracle connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes. Supports also a ${ORACLE_OPTIONS} configuration with a custom environment variable.|string| | | |
|initialization_sql|Custom SQL that is executed after connecting to Oracle. This SQL script can configure the default language, for example: alter session set NLS_DATE_FORMAT&#x3D;&#x27;YYYY-DD-MM HH24:MI:SS&#x27;|string| | | |

___  

## MonitoringSchedulesSpec  
Container of all monitoring schedules (cron expressions) for each type of checks.
 Data quality checks are grouped by type (profiling, whole table checks, time period partitioned checks).
 Each group of checks could be divided additionally by time scale (daily, monthly, etc).
 Each time scale has a different monitoring schedule used by the job scheduler to run the checks.
 These schedules are defined in this object.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profiling](\docs\client\models\#monitoringschedulespec)|Schedule for running profiling data quality checks.|[MonitoringScheduleSpec](\docs\client\models\#monitoringschedulespec)| | | |
|[monitoring_daily](\docs\client\models\#monitoringschedulespec)|Schedule for running daily monitoring checks.|[MonitoringScheduleSpec](\docs\client\models\#monitoringschedulespec)| | | |
|[monitoring_monthly](\docs\client\models\#monitoringschedulespec)|Schedule for running monthly monitoring checks.|[MonitoringScheduleSpec](\docs\client\models\#monitoringschedulespec)| | | |
|[partitioned_daily](\docs\client\models\#monitoringschedulespec)|Schedule for running daily partitioned checks.|[MonitoringScheduleSpec](\docs\client\models\#monitoringschedulespec)| | | |
|[partitioned_monthly](\docs\client\models\#monitoringschedulespec)|Schedule for running monthly partitioned checks.|[MonitoringScheduleSpec](\docs\client\models\#monitoringschedulespec)| | | |

___  

## LabelSetSpec  
Collection of unique labels assigned to items (tables, columns, checks) that could be targeted for a data quality check execution.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|

___  

## ConnectionSpec  
Data source (connection) specification.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|provider_type|Database provider type (required).|enum|snowflake<br/>oracle<br/>postgresql<br/>redshift<br/>sqlserver<br/>mysql<br/>bigquery<br/>| | |
|[bigquery](\docs\reference\yaml\connectionyaml\#bigqueryparametersspec)|BigQuery connection parameters. Specify parameters in the bigquery section.|[BigQueryParametersSpec](\docs\reference\yaml\connectionyaml\#bigqueryparametersspec)| | | |
|[snowflake](\docs\reference\yaml\connectionyaml\#snowflakeparametersspec)|Snowflake connection parameters. Specify parameters in the snowflake section or set the url (which is the Snowflake JDBC url).|[SnowflakeParametersSpec](\docs\reference\yaml\connectionyaml\#snowflakeparametersspec)| | | |
|[postgresql](\docs\reference\yaml\connectionyaml\#postgresqlparametersspec)|PostgreSQL connection parameters. Specify parameters in the postgresql section or set the url (which is the PostgreSQL JDBC url).|[PostgresqlParametersSpec](\docs\reference\yaml\connectionyaml\#postgresqlparametersspec)| | | |
|[redshift](\docs\reference\yaml\connectionyaml\#redshiftparametersspec)|Redshift connection parameters. Specify parameters in the redshift section or set the url (which is the Redshift JDBC url).|[RedshiftParametersSpec](\docs\reference\yaml\connectionyaml\#redshiftparametersspec)| | | |
|[sqlserver](\docs\reference\yaml\connectionyaml\#sqlserverparametersspec)|SQL Server connection parameters. Specify parameters in the sqlserver section or set the url (which is the SQL Server JDBC url).|[SqlServerParametersSpec](\docs\reference\yaml\connectionyaml\#sqlserverparametersspec)| | | |
|[mysql](\docs\reference\yaml\connectionyaml\#mysqlparametersspec)|MySQL connection parameters. Specify parameters in the sqlserver section or set the url (which is the MySQL JDBC url).|[MysqlParametersSpec](\docs\reference\yaml\connectionyaml\#mysqlparametersspec)| | | |
|[oracle](\docs\reference\yaml\connectionyaml\#oracleparametersspec)|Oracle connection parameters. Specify parameters in the postgresql section or set the url (which is the Oracle JDBC url).|[OracleParametersSpec](\docs\reference\yaml\connectionyaml\#oracleparametersspec)| | | |
|parallel_runs_limit|The concurrency limit for the maximum number of parallel SQL queries executed on this connection.|integer| | | |
|[default_grouping_configuration](\docs\client\models\#datagroupingconfigurationspec)|Default data grouping configuration for all tables. The configuration may be overridden on table, column and check level. Data groupings are configured in two cases: (1) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a &#x27;country&#x27; column used for partitioning. a static dimension is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). (2) a static dimension is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). |[DataGroupingConfigurationSpec](\docs\client\models\#datagroupingconfigurationspec)| | | |
|[schedules](\docs\reference\yaml\connectionyaml\#monitoringschedulesspec)|Configuration of the job scheduler that runs data quality checks. The scheduler configuration is divided into types of checks that have different schedules.|[MonitoringSchedulesSpec](\docs\reference\yaml\connectionyaml\#monitoringschedulesspec)| | | |
|[incident_grouping](\docs\client\models\connections\#connectionincidentgroupingspec)|Configuration of data quality incident grouping. Configures how failed data quality checks are grouped into data quality incidents.|[ConnectionIncidentGroupingSpec](\docs\client\models\connections\#connectionincidentgroupingspec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|[labels](#labelsetspec)|Custom labels that were assigned to the connection. Labels are used for searching for tables when filtered data quality checks are executed.|[LabelSetSpec](#labelsetspec)| | | |

___  

## ConnectionSpecificationModel  
Connection model returned by the rest api.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name|Connection name.|string| | | |
|connection_hash|Connection hash that identifies the connection using a unique hash code.|long| | | |
|[spec](\docs\client\models\connections\#connectionspec)|Full connection specification, including all nested objects (but not a list of tables).|[ConnectionSpec](\docs\client\models\connections\#connectionspec)| | | |
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean| | | |

___  

