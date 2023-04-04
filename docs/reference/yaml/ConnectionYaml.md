
## ConnectionYaml  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|table<br/>dashboards<br/>source<br/>sensor<br/>check<br/>rule<br/>file_index<br/>settings<br/>provider_sensor<br/>| | |
|[spec](#connectionspec)||object| | | |

___  

## ConnectionSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|provider_type|Database provider type (required). Accepts: bigquery, snowflake.|enum|snowflake<br/>postgresql<br/>redshift<br/>sqlserver<br/>bigquery<br/>| | |
|[bigquery](#bigqueryparametersspec)|BigQuery connection parameters. Specify parameters in the bigquery section.|object| | | |
|[snowflake](#snowflakeparametersspec)|Snowflake connection parameters. Specify parameters in the snowflake section or set the url (which is the Snowflake JDBC url).|object| | | |
|[postgresql](#postgresqlparametersspec)|PostgreSQL connection parameters. Specify parameters in the postgresql section or set the url (which is the Snowflake JDBC url).|object| | | |
|[redshift](#redshiftparametersspec)|Redshift connection parameters. Specify parameters in the redshift section or set the url (which is the Redshift JDBC url).|object| | | |
|[sqlserver](#sqlserverparametersspec)|SQL Server connection parameters. Specify parameters in the sqlserver section or set the url (which is the SQL Server JDBC url).|object| | | |
|parallel_runs_limit|The concurrency limit for the maximum number of parallel executions of checks on this connection.|integer| | | |
|[default_data_stream_mapping](#datastreammappingspec)|Default data streams configuration for all tables. The configuration may be overridden on table, column and check level. Data streams are configured in two cases: (1) a static dimension is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). (2) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a &#x27;country&#x27; column used for partitioning.|object| | | |
|[schedules](#recurringschedulesspec)|Configuration of the job scheduler that runs data quality checks. The scheduler configuration is divided into types of checks that have different schedules.|object| | | |
|[notifications](#notificationsettingsspec)|Configuration of the notifications settings. Notifications are published when new data quality issues are detected.|object| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|object| | | |
|[labels](#labelsetspec)|Custom labels that were assigned to the connection. Labels are used for searching for tables when filtered data quality checks are executed.|object| | | |

___  

## BigQueryParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|source_project_id|Source GCP project ID. This is the project that has datasets that will be imported.|string| | | |
|billing_project_id|Billing GCP project ID. This is the project used as the default GCP project. The calling user must have a bigquery.jobs.create permission in this project.|string| | | |
|authentication_mode|Authentication mode to the Google Cloud.|enum|json_key_content<br/>json_key_path<br/>google_application_credentials<br/>| | |
|json_key_content|JSON key content. Use an environment variable that contains the content of the key as ${KEY_ENV} or a name of a secret in the GCP Secret Manager: ${sm://key-secret-name}. Requires the authentication-mode: json_key_content.|string| | | |
|json_key_path|A path to the JSON key file. Requires the authentication-mode: json_key_path.|string| | | |
|quota_project_id|Quota GCP project ID.|string| | | |

___  

## SnowflakeParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|account|Snowflake account name, e.q. &lt;account&gt;, &lt;account&gt;-&lt;locator&gt;, &lt;account&gt;.&lt;region&gt; or &lt;account&gt;.&lt;region&gt;.&lt;platform&gt;.. Supports also a ${SNOWFLAKE_ACCOUNT} configuration with a custom environment variable.|string| | | |
|warehouse|Snowflake warehouse name. Supports also a ${SNOWFLAKE_WAREHOUSE} configuration with a custom environment variable.|string| | | |
|database|Snowflake database name. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.|string| | | |
|user|Snowflake user name. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.|string| | | |
|password|Snowflake database password. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.|string| | | |
|role|Snowflake role name. Supports also a ${SNOWFLAKE_ROLE} configuration with a custom environment variable.|string| | | |

___  

## PostgresqlParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|host|PostgreSQL host name. Supports also a ${POSTGRESQL_HOST} configuration with a custom environment variable.|string| | | |
|port|PostgreSQL port name. The default port is 5432. Supports also a ${POSTGRESQL_PORT} configuration with a custom environment variable.|string| | | |
|database|PostgreSQL database name. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.|string| | | |
|user|PostgreSQL user name. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.|string| | | |
|password|PostgreSQL database password. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.|string| | | |
|options|PostgreSQL connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes. Supports also a ${POSTGRESQL_OPTIONS} configuration with a custom environment variable.|string| | | |
|ssl|Connect to PostgreSQL using SSL. The default value is false.|boolean| | | |

___  

## RedshiftParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|host|Redshift host name. Supports also a ${REDSHIFT_HOST} configuration with a custom environment variable.|string| | | |
|port|Redshift port name. The default port is 5432. Supports also a ${REDSHIFT_PORT} configuration with a custom environment variable.|string| | | |
|database|Redshift database name. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.|string| | | |
|user|Redshift user name. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.|string| | | |
|password|Redshift database password. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.|string| | | |
|options|Redshift connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes. Supports also a ${REDSHIFT_OPTIONS} configuration with a custom environment variable.|string| | | |
|ssl|Connect to Redshift using SSL. The default value is false.|boolean| | | |

___  

## SqlServerParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|host|SQL Server host name. Supports also a ${SQLSERVER_HOST} configuration with a custom environment variable.|string| | | |
|port|SQL Server port name. The default port is 1433. Supports also a ${SQLSERVER_PORT} configuration with a custom environment variable.|string| | | |
|database|SQL Server database name. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.|string| | | |
|user|SQL Server user name. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.|string| | | |
|password|SQL Server database password. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.|string| | | |
|options|SQL Server connection &#x27;options&#x27; initialization parameter. For example setting this to -c statement_timeout&#x3D;5min would set the statement timeout parameter for this session to 5 minutes. Supports also a ${SQLSERVER_OPTIONS} configuration with a custom environment variable.|string| | | |
|ssl|Connecting to SQL Server with SSL disabled. The default value is false.|boolean| | | |

___  

## DataStreamMappingSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[level1](#datastreamlevelspec)|Data stream level 1 configuration.|object| | | |
|[level2](#datastreamlevelspec)|Data stream level 2 configuration.|object| | | |
|[level3](#datastreamlevelspec)|Data stream level 3 configuration.|object| | | |
|[level4](#datastreamlevelspec)|Data stream level 4 configuration.|object| | | |
|[level5](#datastreamlevelspec)|Data stream level 5 configuration.|object| | | |
|[level6](#datastreamlevelspec)|Data stream level 6 configuration.|object| | | |
|[level7](#datastreamlevelspec)|Data stream level 7 configuration.|object| | | |
|[level8](#datastreamlevelspec)|Data stream level 8 configuration.|object| | | |
|[level9](#datastreamlevelspec)|Data stream level 9 configuration.|object| | | |

___  

## DataStreamLevelSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|source|The source of the data stream level value. The default stream level source is a tag. Assign a tag when there are multiple similar tables that store the same data for different areas (countries, etc.). This could be a country name if a table or partition stores information for that country.|enum|tag<br/>column_value<br/>| | |
|tag|Data stream tag. Assign a hardcoded (static) data stream level value (tag) when there are multiple similar tables that store the same data for different areas (countries, etc.). This could be a country name if a table or partition stores information for that country.|string| | | |
|column|Column name that contains a dynamic data stream level value (for dynamic data-driven data streams). Sensor queries will be extended with a GROUP BY {data stream level colum name}, sensors (and alerts) will be calculated for each unique value of the specified column. Also a separate time series will be tracked for each value.|column_name| | | |
|name|Data stream level name.|string| | | |

___  

## RecurringSchedulesSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profiling](#recurringschedulespec)|Schedule for running profiling data quality checks.|object| | | |
|[recurring_daily](#recurringschedulespec)|Schedule for running daily recurring checks.|object| | | |
|[recurring_monthly](#recurringschedulespec)|Schedule for running monthly recurring checks.|object| | | |
|[partitioned_daily](#recurringschedulespec)|Schedule for running daily partitioned checks.|object| | | |
|[partitioned_monthly](#recurringschedulespec)|Schedule for running monthly partitioned checks.|object| | | |

___  

## RecurringScheduleSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|cron_expression|Unix style cron expression that specifies when to execute scheduled operations like running data quality checks or synchronizing the configuration with the cloud.|string| | | |
|disabled|Disables the schedule. When the value of this &#x27;disable&#x27; field is false, the schedule is stored in the metadata but it is not activated to run data quality checks.|boolean| | | |

___  

## NotificationSettingsSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|webhook_url|Webhook URL where the notification messages are pushed using a HTTP POST request.|string| | | |
|hours_since_last_alert|Number of hours since the last alert. New data quality issues identified before that timestamp are not sent as notifications.|integer| | | |

___  

## CommentsListSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|mod_count||integer| | | |

___  

## LabelSetSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|

___  

