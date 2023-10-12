
## RuleParametersModel  
Model that returns the form definition and the form data to edit parameters (thresholds) for a rule at a single severity level (low, medium, high).  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|rule_name|Full rule name. This field is for information purposes and could be used to create additional custom checks that are reusing the same data quality rule.|string| | | |
|disabled|Disable the rule. The rule will not be evaluated. The sensor will also not be executed if it has no enabled rules.|boolean| | | |
|configured|Returns true when the rule is configured (is not null), so it should be shown in the UI as configured (having values).|boolean| | | |

___  

## CheckConfigurationModel  
Model containing fundamental configuration of a single data quality check.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name|Connection name.|string| | | |
|schema_name|Schema name.|string| | | |
|table_name|Table name.|string| | | |
|column_name|Column name, if the check is set up on a column.|string| | | |
|check_target|Check target (table or column).|enum|column<br/>table<br/>| | |
|check_type|Check type (profiling, monitoring, partitioned).|enum|profiling<br/>partitioned<br/>monitoring<br/>| | |
|check_time_scale|Category to which this check belongs.|enum|daily<br/>monthly<br/>| | |
|category_name|Category to which this check belongs.|string| | | |
|check_name|Check name that is used in YAML file.|string| | | |
|table_level_filter|SQL WHERE clause added to the sensor query for every check on this table.|string| | | |
|sensor_level_filter|SQL WHERE clause added to the sensor query for this check.|string| | | |
|[warning](#ruleparametersmodel)|Rule parameters for the warning severity rule.|[RuleParametersModel](#ruleparametersmodel)| | | |
|[error](#ruleparametersmodel)|Rule parameters for the error severity rule.|[RuleParametersModel](#ruleparametersmodel)| | | |
|[fatal](#ruleparametersmodel)|Rule parameters for the fatal severity rule.|[RuleParametersModel](#ruleparametersmodel)| | | |
|disabled|Whether the check has been disabled.|boolean| | | |
|configured|Whether the check is configured (not null).|boolean| | | |

___  

## CheckContainerListModel  
Simplistic model that returns the list of data quality checks, their names, categories and &quot;configured&quot; flag.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|can_edit|Boolean flag that decides if the current user can edit the check.|boolean| | | |
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean| | | |
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean| | | |

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

## CheckContainerModel  
Model that returns the form definition and the form data to edit all data quality checks divided by categories.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[effective_schedule](#effectiveschedulemodel)|Model of configured schedule enabled on the check container.|[EffectiveScheduleModel](#effectiveschedulemodel)| | | |
|effective_schedule_enabled_status|State of the effective scheduling on the check container.|enum|not_configured<br/>disabled<br/>overridden_by_checks<br/>enabled<br/>| | |
|partition_by_column|The name of the column that partitioned checks will use for the time period partitioning. Important only for partitioned checks.|string| | | |
|[run_checks_job_template](#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to start the job.|[CheckSearchFilters](#checksearchfilters)| | | |
|[data_clean_job_template](\docs\client\operations\jobs\#deletestoreddataqueuejobparameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this check container|[DeleteStoredDataQueueJobParameters](\docs\client\operations\jobs\#deletestoreddataqueuejobparameters)| | | |
|can_edit|Boolean flag that decides if the current user can edit the check.|boolean| | | |
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean| | | |
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean| | | |

___  

## CheckRunScheduleGroup  
The run check scheduling group (profiling, daily checks, monthly checks, etc), which identifies the configuration of a schedule (cron expression) used schedule these checks on the job scheduler.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|name||string| | | |
|ordinal||integer| | | |

___  

## CheckContainerTypeModel  
Model identifying the check type and timescale of checks belonging to a container.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|check_type|Check type.|enum|profiling<br/>partitioned<br/>monitoring<br/>| | |
|check_time_scale|Check timescale.|enum|daily<br/>monthly<br/>| | |

___  

## CheckTemplate  
Model depicting a named data quality check that can potentially be enabled, regardless to its position in hierarchy tree.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|check_target|Check target (table, column)|enum|column<br/>table<br/>| | |
|check_category|Data quality check category.|string| | | |
|check_name|Data quality check name that is used in YAML.|string| | | |
|help_text|Help text that describes the data quality check.|string| | | |
|[check_container_type](#checkcontainertypemodel)|Check type with time-scale.|[CheckContainerTypeModel](#checkcontainertypemodel)| | | |
|sensor_name|Full sensor name.|string| | | |

___  

## CheckTimeScale  
Enumeration of time scale of monitoring and partitioned data quality checks (daily, monthly, etc.)  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|name||string| | | |
|ordinal||integer| | | |

___  

## CheckType  
Enumeration of data quality check types: profiling, monitoring, partitioned.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|display_name||string| | | |
|name||string| | | |
|ordinal||integer| | | |

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

## StatisticsCollectorSearchFilters  
Hierarchy node search filters for finding enabled statistics collectors (basic profilers) to be started.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|collector_name||string| | | |
|sensor_name||string| | | |
|collector_category||string| | | |
|target||enum|column<br/>table<br/>| | |
|connection_name||string| | | |
|schema_table_name||string| | | |
|enabled||boolean| | | |

___  

## ConnectionModel  
Connection model returned by the rest api that is limited only to the basic fields, excluding nested nodes.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name|Connection name.|string| | | |
|connection_hash|Connection hash that identifies the connection using a unique hash code.|long| | | |
|parallel_runs_limit|The concurrency limit for the maximum number of parallel SQL queries executed on this connection.|integer| | | |
|provider_type|Database provider type (required). Accepts: bigquery, snowflake.|enum|snowflake<br/>oracle<br/>postgresql<br/>redshift<br/>sqlserver<br/>mysql<br/>bigquery<br/>| | |
|[bigquery](\docs\reference\yaml\connectionyaml\#bigqueryparametersspec)|BigQuery connection parameters. Specify parameters in the bigquery section.|[BigQueryParametersSpec](\docs\reference\yaml\connectionyaml\#bigqueryparametersspec)| | | |
|[snowflake](\docs\reference\yaml\connectionyaml\#snowflakeparametersspec)|Snowflake connection parameters.|[SnowflakeParametersSpec](\docs\reference\yaml\connectionyaml\#snowflakeparametersspec)| | | |
|[postgresql](\docs\reference\yaml\connectionyaml\#postgresqlparametersspec)|PostgreSQL connection parameters.|[PostgresqlParametersSpec](\docs\reference\yaml\connectionyaml\#postgresqlparametersspec)| | | |
|[redshift](\docs\reference\yaml\connectionyaml\#redshiftparametersspec)|Redshift connection parameters.|[RedshiftParametersSpec](\docs\reference\yaml\connectionyaml\#redshiftparametersspec)| | | |
|[sqlserver](\docs\reference\yaml\connectionyaml\#sqlserverparametersspec)|SqlServer connection parameters.|[SqlServerParametersSpec](\docs\reference\yaml\connectionyaml\#sqlserverparametersspec)| | | |
|[mysql](\docs\reference\yaml\connectionyaml\#mysqlparametersspec)|MySQL connection parameters.|[MysqlParametersSpec](\docs\reference\yaml\connectionyaml\#mysqlparametersspec)| | | |
|[oracle](\docs\reference\yaml\connectionyaml\#oracleparametersspec)|Oracle connection parameters.|[OracleParametersSpec](\docs\reference\yaml\connectionyaml\#oracleparametersspec)| | | |
|[run_checks_job_template](#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run all checks within this connection.|[CheckSearchFilters](#checksearchfilters)| | | |
|[run_profiling_checks_job_template](#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run profiling checks within this connection.|[CheckSearchFilters](#checksearchfilters)| | | |
|[run_monitoring_checks_job_template](#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run monitoring checks within this connection.|[CheckSearchFilters](#checksearchfilters)| | | |
|[run_partition_checks_job_template](#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run partition partitioned checks within this connection.|[CheckSearchFilters](#checksearchfilters)| | | |
|[collect_statistics_job_template](\docs\client\operations\jobs\#statisticscollectorsearchfilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors within this connection.|[StatisticsCollectorSearchFilters](\docs\client\operations\jobs\#statisticscollectorsearchfilters)| | | |
|[data_clean_job_template](\docs\client\operations\jobs\#deletestoreddataqueuejobparameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this connection.|[DeleteStoredDataQueueJobParameters](\docs\client\operations\jobs\#deletestoreddataqueuejobparameters)| | | |
|can_edit|Boolean flag that decides if the current user can update or delete the connection to the data source.|boolean| | | |
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean| | | |
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean| | | |
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean| | | |

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

## DqoQueueJobId  
Identifies a single job.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|job_id|Job id.|long| | | |
|[parent_job_id](\docs\client\models\#dqoqueuejobid)|Parent job id. Filled only for nested jobs, for example a sub-job that runs data quality checks on a single table.|[DqoQueueJobId](\docs\client\models\#dqoqueuejobid)| | | |

___  

## MonitoringScheduleSpec  
Monitoring job schedule specification.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|cron_expression|Unix style cron expression that specifies when to execute scheduled operations like running data quality checks or synchronizing the configuration with the cloud.|string| | | |
|disabled|Disables the schedule. When the value of this &#x27;disable&#x27; field is false, the schedule is stored in the metadata but it is not activated to run data quality checks.|boolean| | | |

___  

