
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

## ConnectionSpec  
Data source (connection) specification.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|provider_type|Database provider type (required).|enum|snowflake<br/>oracle<br/>postgresql<br/>redshift<br/>sqlserver<br/>mysql<br/>bigquery<br/>| | |
|[bigquery](#bigqueryparametersspec)|BigQuery connection parameters. Specify parameters in the bigquery section.|[BigQueryParametersSpec](#bigqueryparametersspec)| | | |
|[snowflake](#snowflakeparametersspec)|Snowflake connection parameters. Specify parameters in the snowflake section or set the url (which is the Snowflake JDBC url).|[SnowflakeParametersSpec](#snowflakeparametersspec)| | | |
|[postgresql](#postgresqlparametersspec)|PostgreSQL connection parameters. Specify parameters in the postgresql section or set the url (which is the PostgreSQL JDBC url).|[PostgresqlParametersSpec](#postgresqlparametersspec)| | | |
|[redshift](#redshiftparametersspec)|Redshift connection parameters. Specify parameters in the redshift section or set the url (which is the Redshift JDBC url).|[RedshiftParametersSpec](#redshiftparametersspec)| | | |
|[sqlserver](#sqlserverparametersspec)|SQL Server connection parameters. Specify parameters in the sqlserver section or set the url (which is the SQL Server JDBC url).|[SqlServerParametersSpec](#sqlserverparametersspec)| | | |
|[mysql](#mysqlparametersspec)|MySQL connection parameters. Specify parameters in the sqlserver section or set the url (which is the MySQL JDBC url).|[MysqlParametersSpec](#mysqlparametersspec)| | | |
|[oracle](#oracleparametersspec)|Oracle connection parameters. Specify parameters in the postgresql section or set the url (which is the Oracle JDBC url).|[OracleParametersSpec](#oracleparametersspec)| | | |
|parallel_runs_limit|The concurrency limit for the maximum number of parallel SQL queries executed on this connection.|integer| | | |
|[default_grouping_configuration](#datagroupingconfigurationspec)|Default data grouping configuration for all tables. The configuration may be overridden on table, column and check level. Data groupings are configured in two cases: (1) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a &#x27;country&#x27; column used for partitioning. a static dimension is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). (2) a static dimension is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). |[DataGroupingConfigurationSpec](#datagroupingconfigurationspec)| | | |
|[schedules](#monitoringschedulesspec)|Configuration of the job scheduler that runs data quality checks. The scheduler configuration is divided into types of checks that have different schedules.|[MonitoringSchedulesSpec](#monitoringschedulesspec)| | | |
|[incident_grouping](#connectionincidentgroupingspec)|Configuration of data quality incident grouping. Configures how failed data quality checks are grouped into data quality incidents.|[ConnectionIncidentGroupingSpec](#connectionincidentgroupingspec)| | | |
|[comments](\docs\reference\yaml\profiling\table-profiling-checks\#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](\docs\reference\yaml\profiling\table-profiling-checks\#commentslistspec)| | | |
|[labels](#labelsetspec)|Custom labels that were assigned to the connection. Labels are used for searching for tables when filtered data quality checks are executed.|[LabelSetSpec](#labelsetspec)| | | |









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

## DataGroupingConfigurationSpec  
Configuration of the data groupings that is used to calculate data quality checks with a GROUP BY clause.
 Data grouping levels may be hardcoded if we have different (but similar) tables for different business areas (countries, product groups).
 We can also pull data grouping levels directly from the database if a table has a column that identifies a business area.
 Data quality results for new groups are dynamically identified in the database by the GROUP BY clause. Sensor values are extracted for each data group separately,
 a time series is build for each data group separately.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[level_1](#datagroupingdimensionspec)|Data grouping dimension level 1 configuration.|[DataGroupingDimensionSpec](#datagroupingdimensionspec)| | | |
|[level_2](#datagroupingdimensionspec)|Data grouping dimension level 2 configuration.|[DataGroupingDimensionSpec](#datagroupingdimensionspec)| | | |
|[level_3](#datagroupingdimensionspec)|Data grouping dimension level 3 configuration.|[DataGroupingDimensionSpec](#datagroupingdimensionspec)| | | |
|[level_4](#datagroupingdimensionspec)|Data grouping dimension level 4 configuration.|[DataGroupingDimensionSpec](#datagroupingdimensionspec)| | | |
|[level_5](#datagroupingdimensionspec)|Data grouping dimension level 5 configuration.|[DataGroupingDimensionSpec](#datagroupingdimensionspec)| | | |
|[level_6](#datagroupingdimensionspec)|Data grouping dimension level 6 configuration.|[DataGroupingDimensionSpec](#datagroupingdimensionspec)| | | |
|[level_7](#datagroupingdimensionspec)|Data grouping dimension level 7 configuration.|[DataGroupingDimensionSpec](#datagroupingdimensionspec)| | | |
|[level_8](#datagroupingdimensionspec)|Data grouping dimension level 8 configuration.|[DataGroupingDimensionSpec](#datagroupingdimensionspec)| | | |
|[level_9](#datagroupingdimensionspec)|Data grouping dimension level 9 configuration.|[DataGroupingDimensionSpec](#datagroupingdimensionspec)| | | |









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
|[profiling](\docs\reference\yaml\profiling\table-profiling-checks\#monitoringschedulespec)|Schedule for running profiling data quality checks.|[MonitoringScheduleSpec](\docs\reference\yaml\profiling\table-profiling-checks\#monitoringschedulespec)| | | |
|[monitoring_daily](\docs\reference\yaml\profiling\table-profiling-checks\#monitoringschedulespec)|Schedule for running daily monitoring checks.|[MonitoringScheduleSpec](\docs\reference\yaml\profiling\table-profiling-checks\#monitoringschedulespec)| | | |
|[monitoring_monthly](\docs\reference\yaml\profiling\table-profiling-checks\#monitoringschedulespec)|Schedule for running monthly monitoring checks.|[MonitoringScheduleSpec](\docs\reference\yaml\profiling\table-profiling-checks\#monitoringschedulespec)| | | |
|[partitioned_daily](\docs\reference\yaml\profiling\table-profiling-checks\#monitoringschedulespec)|Schedule for running daily partitioned checks.|[MonitoringScheduleSpec](\docs\reference\yaml\profiling\table-profiling-checks\#monitoringschedulespec)| | | |
|[partitioned_monthly](\docs\reference\yaml\profiling\table-profiling-checks\#monitoringschedulespec)|Schedule for running monthly partitioned checks.|[MonitoringScheduleSpec](\docs\reference\yaml\profiling\table-profiling-checks\#monitoringschedulespec)| | | |









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

## ConnectionYaml  
Connection definition for a data source connection that is covered by data quality checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|table<br/>default_schedules<br/>dashboards<br/>source<br/>sensor<br/>check<br/>default_checks<br/>rule<br/>file_index<br/>settings<br/>default_notifications<br/>provider_sensor<br/>| | |
|[spec](#connectionspec)||[ConnectionSpec](#connectionspec)| | | |









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
|[webhooks](#incidentwebhooknotificationsspec)|Configuration of Webhook URLs for new or updated incident notifications.|[IncidentWebhookNotificationsSpec](#incidentwebhooknotificationsspec)| | | |









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

## LabelSetSpec  
Collection of unique labels assigned to items (tables, columns, checks) that could be targeted for a data quality check execution.  
  




___  

