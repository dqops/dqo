# operations

**This is a list of the operations in DQOps REST API Python client broken down by individual controllers.**


## check_results  
Returns the complete results of executed checks on tables and columns.  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[get_column_monitoring_checks_results](./check_results/#get_column_monitoring_checks_results)|GET|Returns a complete view of the recent column level monitoring executions for the monitoring at a requested time scale|
|[get_column_partitioned_checks_results](./check_results/#get_column_partitioned_checks_results)|GET|Returns an overview of the most recent column level partitioned checks executions for a requested time scale|
|[get_column_profiling_checks_results](./check_results/#get_column_profiling_checks_results)|GET|Returns an overview of the most recent check executions for all column level data quality profiling checks on a column|
|[get_table_data_quality_status](./check_results/#get_table_data_quality_status)|GET|Read the most recent results of executed data quality checks on the table and return the current table&#x27;s data quality status - the number of failed data quality checks if the table has active data quality issues. Also returns the names of data quality checks that did not pass most recently. This operation verifies only the status of the most recently executed data quality checks. Previous data quality issues are not counted.|
|[get_table_monitoring_checks_results](./check_results/#get_table_monitoring_checks_results)|GET|Returns the complete results of the most recent table level monitoring executions for the monitoring at a requested time scale|
|[get_table_partitioned_checks_results](./check_results/#get_table_partitioned_checks_results)|GET|Returns a complete view of the recent table level partitioned checks executions for a requested time scale|
|[get_table_profiling_checks_results](./check_results/#get_table_profiling_checks_results)|GET|Returns the complete results of the most recent check executions for all table level data quality profiling checks on a table|


## check_results_overview  
Returns the overview of the recently executed checks on tables and columns.  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[get_column_monitoring_checks_overview](./check_results_overview/#get_column_monitoring_checks_overview)|GET|Returns an overview of the most recent column level monitoring executions for the monitoring at a requested time scale|
|[get_column_partitioned_checks_overview](./check_results_overview/#get_column_partitioned_checks_overview)|GET|Returns an overview of the most recent column level partitioned checks executions for a requested time scale|
|[get_column_profiling_checks_overview](./check_results_overview/#get_column_profiling_checks_overview)|GET|Returns an overview of the most recent check executions for all column level data quality profiling checks on a column|
|[get_table_monitoring_checks_overview](./check_results_overview/#get_table_monitoring_checks_overview)|GET|Returns an overview of the most recent table level monitoring executions for the monitoring at a requested time scale|
|[get_table_partitioned_checks_overview](./check_results_overview/#get_table_partitioned_checks_overview)|GET|Returns an overview of the most recent table level partitioned checks executions for a requested time scale|
|[get_table_profiling_checks_overview](./check_results_overview/#get_table_profiling_checks_overview)|GET|Returns an overview of the most recent check executions for all table level data quality profiling checks on a table|


## checks  
Data quality check definition management  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[create_check](./checks/#create_check)|POST|Creates (adds) a new custom check that is a pair of a sensor name and a rule name.|
|[delete_check](./checks/#delete_check)|DELETE|Deletes a custom check definition|
|[get_all_checks](./checks/#get_all_checks)|GET|Returns a flat list of all checks available in DQOps, both built-in checks and user defined or customized checks.|
|[get_check](./checks/#get_check)|GET|Returns a check definition|
|[get_check_folder_tree](./checks/#get_check_folder_tree)|GET|Returns a tree of all checks available in DQOps, both built-in checks and user defined or customized checks.|
|[update_check](./checks/#update_check)|PUT|Updates an existing check, making a custom check definition if it is not present|


## columns  
Manages columns inside a table  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[create_column](./columns/#create_column)|POST|Creates a new column (adds a column metadata to the table)|
|[delete_column](./columns/#delete_column)|DELETE|Deletes a column from the table|
|[get_column](./columns/#get_column)|GET|Returns the full column specification|
|[get_column_basic](./columns/#get_column_basic)|GET|Returns the column specification|
|[get_column_comments](./columns/#get_column_comments)|GET|Return the list of comments assigned to a column|
|[get_column_labels](./columns/#get_column_labels)|GET|Return the list of labels assigned to a column|
|[get_column_monitoring_checks_basic_model](./columns/#get_column_monitoring_checks_basic_model)|GET|Return a simplistic UI friendly model of column level data quality monitoring on a column|
|[get_column_monitoring_checks_daily](./columns/#get_column_monitoring_checks_daily)|GET|Return the configuration of daily column level data quality monitoring on a column|
|[get_column_monitoring_checks_model](./columns/#get_column_monitoring_checks_model)|GET|Return a UI friendly model of column level data quality monitoring on a column|
|[get_column_monitoring_checks_model_filter](./columns/#get_column_monitoring_checks_model_filter)|GET|Return a UI friendly model of column level data quality monitoring on a column filtered by category and check name|
|[get_column_monitoring_checks_monthly](./columns/#get_column_monitoring_checks_monthly)|GET|Return the configuration of monthly column level data quality monitoring on a column|
|[get_column_partitioned_checks_basic_model](./columns/#get_column_partitioned_checks_basic_model)|GET|Return a simplistic UI friendly model of column level data quality partitioned checks on a column|
|[get_column_partitioned_checks_daily](./columns/#get_column_partitioned_checks_daily)|GET|Return the configuration of daily column level data quality partitioned checks on a column|
|[get_column_partitioned_checks_model](./columns/#get_column_partitioned_checks_model)|GET|Return a UI friendly model of column level data quality partitioned checks on a column|
|[get_column_partitioned_checks_model_filter](./columns/#get_column_partitioned_checks_model_filter)|GET|Return a UI friendly model of column level data quality partitioned checks on a column, filtered by category and check name|
|[get_column_partitioned_checks_monthly](./columns/#get_column_partitioned_checks_monthly)|GET|Return the configuration of monthly column level data quality partitioned checks on a column|
|[get_column_profiling_checks](./columns/#get_column_profiling_checks)|GET|Return the configuration of column level data quality profiling checks on a column|
|[get_column_profiling_checks_basic_model](./columns/#get_column_profiling_checks_basic_model)|GET|Return a simplistic UI friendly model of column level data quality profiling checks on a column|
|[get_column_profiling_checks_model](./columns/#get_column_profiling_checks_model)|GET|Return a UI friendly model of data quality profiling checks on a column|
|[get_column_profiling_checks_model_filter](./columns/#get_column_profiling_checks_model_filter)|GET|Return a UI friendly model of data quality profiling checks on a column filtered by category and check name|
|[get_column_statistics](./columns/#get_column_statistics)|GET|Returns the column specification with the metrics captured by the most recent statistics collection.|
|[get_columns](./columns/#get_columns)|GET|Returns a list of columns inside a table|
|[get_columns_statistics](./columns/#get_columns_statistics)|GET|Returns a list of columns inside a table with the metrics captured by the most recent statistics collection.|
|[update_column](./columns/#update_column)|PUT|Updates an existing column specification, changing all the fields (even the column level data quality checks).|
|[update_column_basic](./columns/#update_column_basic)|PUT|Updates an existing column, changing only the basic information like the expected data type (the data type snapshot).|
|[update_column_comments](./columns/#update_column_comments)|PUT|Updates the list of comments assigned to a column.|
|[update_column_labels](./columns/#update_column_labels)|PUT|Updates the list of labels assigned to a column.|
|[update_column_monitoring_checks_daily](./columns/#update_column_monitoring_checks_daily)|PUT|Updates configuration of daily column level data quality monitoring on a column.|
|[update_column_monitoring_checks_model](./columns/#update_column_monitoring_checks_model)|PUT|Updates configuration of column level data quality monitoring on a column, for a given time scale, from a UI friendly model.|
|[update_column_monitoring_checks_monthly](./columns/#update_column_monitoring_checks_monthly)|PUT|Updates configuration of monthly column level data quality monitoring checks on a column.|
|[update_column_partitioned_checks_daily](./columns/#update_column_partitioned_checks_daily)|PUT|Updates configuration of daily column level data quality partitioned checks on a column.|
|[update_column_partitioned_checks_model](./columns/#update_column_partitioned_checks_model)|PUT|Updates configuration of column level data quality partitioned checks on a column, for a given time scale, from a UI friendly model.|
|[update_column_partitioned_checks_monthly](./columns/#update_column_partitioned_checks_monthly)|PUT|Updates configuration of monthly column level data quality partitioned checks on a column.|
|[update_column_profiling_checks](./columns/#update_column_profiling_checks)|PUT|Updates configuration of column level data quality profiling checks on a column.|
|[update_column_profiling_checks_model](./columns/#update_column_profiling_checks_model)|PUT|Updates configuration of column level data quality profiling checks on a column from a UI friendly model.|


## connections  
Manages connections to monitored data sources  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[bulk_activate_connection_checks](./connections/#bulk_activate_connection_checks)|PUT|Activates all named check on this connection in the locations specified by filter|
|[bulk_deactivate_connection_checks](./connections/#bulk_deactivate_connection_checks)|PUT|Deactivates (deletes) all named check on this connection in the locations specified by filter|
|[create_connection](./connections/#create_connection)|POST|Creates a new connection|
|[create_connection_basic](./connections/#create_connection_basic)|POST|Creates a new connection given the basic information.|
|[delete_connection](./connections/#delete_connection)|DELETE|Deletes a connection|
|[get_all_connections](./connections/#get_all_connections)|GET|Returns a list of connections (data sources)|
|[get_connection](./connections/#get_connection)|GET|Return the full details of a connection given the connection name|
|[get_connection_basic](./connections/#get_connection_basic)|GET|Return the basic details of a connection given the connection name|
|[get_connection_comments](./connections/#get_connection_comments)|GET|Return the comments for a connection|
|[get_connection_common_columns](./connections/#get_connection_common_columns)|GET|Finds common column names that are used on one or more tables. The list of columns is sorted in descending order by column name.|
|[get_connection_default_grouping_configuration](./connections/#get_connection_default_grouping_configuration)|GET|Return the default data grouping configuration for a connection|
|[get_connection_incident_grouping](./connections/#get_connection_incident_grouping)|GET|Retrieves the configuration of data quality incident grouping and incident notifications|
|[get_connection_labels](./connections/#get_connection_labels)|GET|Return the labels for a connection|
|[get_connection_scheduling_group](./connections/#get_connection_scheduling_group)|GET|Return the schedule for a connection for a scheduling group|
|[update_connection](./connections/#update_connection)|PUT|Updates an existing connection|
|[update_connection_basic](./connections/#update_connection_basic)|PUT|Updates the basic information of a connection|
|[update_connection_comments](./connections/#update_connection_comments)|PUT|Updates (replaces) the list of comments of a connection|
|[update_connection_default_grouping_configuration](./connections/#update_connection_default_grouping_configuration)|PUT|Updates the default data grouping connection of a connection|
|[update_connection_incident_grouping](./connections/#update_connection_incident_grouping)|PUT|Updates (replaces) configuration of incident grouping and notifications on a connection (data source) level.|
|[update_connection_labels](./connections/#update_connection_labels)|PUT|Updates the list of labels of a connection|
|[update_connection_scheduling_group](./connections/#update_connection_scheduling_group)|PUT|Updates the schedule of a connection for a scheduling group (named schedule for checks with a similar time series configuration)|


## dashboards  
Provides access to data quality dashboards  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[get_all_dashboards](./dashboards/#get_all_dashboards)|GET|Returns a list of root folders with dashboards|
|[get_dashboard_level_1](./dashboards/#get_dashboard_level_1)|GET|Returns a single dashboard in the tree of folder with a temporary authenticated url|
|[get_dashboard_level_2](./dashboards/#get_dashboard_level_2)|GET|Returns a single dashboard in the tree of folders with a temporary authenticated url|
|[get_dashboard_level_3](./dashboards/#get_dashboard_level_3)|GET|Returns a single dashboard in the tree of folders with a temporary authenticated url|
|[get_dashboard_level_4](./dashboards/#get_dashboard_level_4)|GET|Returns a single dashboard in the tree of folders with a temporary authenticated url|
|[get_dashboard_level_5](./dashboards/#get_dashboard_level_5)|GET|Returns a single dashboard in the tree of folders with a temporary authenticated url|


## data_grouping_configurations  
Manages data grouping configurations on a table  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[create_table_grouping_configuration](./data_grouping_configurations/#create_table_grouping_configuration)|POST|Creates a new data grouping configuration on a table level|
|[delete_table_grouping_configuration](./data_grouping_configurations/#delete_table_grouping_configuration)|DELETE|Deletes a data grouping configuration from a table|
|[get_table_grouping_configuration](./data_grouping_configurations/#get_table_grouping_configuration)|GET|Returns a model of the data grouping configuration|
|[get_table_grouping_configurations](./data_grouping_configurations/#get_table_grouping_configurations)|GET|Returns the list of data grouping configurations on a table|
|[set_table_default_grouping_configuration](./data_grouping_configurations/#set_table_default_grouping_configuration)|PATCH|Sets a table&#x27;s grouping configuration as the default or disables data grouping|
|[update_table_grouping_configuration](./data_grouping_configurations/#update_table_grouping_configuration)|PUT|Updates a data grouping configuration according to the provided model|


## data_sources  
Rest API controller that operates on data sources that are not yet imported, testing connections or retrieving the metadata (schemas and tables).  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[get_remote_data_source_schemas](./data_sources/#get_remote_data_source_schemas)|GET|Introspects a list of schemas inside a remote data source, identified by an already imported connection.|
|[get_remote_data_source_tables](./data_sources/#get_remote_data_source_tables)|GET|Introspects the list of columns inside a schema on a remote data source that is identified by a connection that was added to DQOps.|
|[test_connection](./data_sources/#test_connection)|POST|Checks if the given remote connection could be opened and the credentials are valid|


## defaults  
Default settings management for configuring the default data quality checks that are configured for all imported tables and columns.  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[get_default_data_observability_daily_monitoring_column_checks](./defaults/#get_default_data_observability_daily_monitoring_column_checks)|GET|Returns UI model to show and edit the default configuration of the daily monitoring (Data Observability and monitoring) checks that are configured for all imported columns on a column level.|
|[get_default_data_observability_daily_monitoring_table_checks](./defaults/#get_default_data_observability_daily_monitoring_table_checks)|GET|Returns UI model to show and edit the default configuration of the daily monitoring (Data Observability and monitoring) checks that are configured for all imported tables on a table level.|
|[get_default_data_observability_monthly_monitoring_column_checks](./defaults/#get_default_data_observability_monthly_monitoring_column_checks)|GET|Returns UI model to show and edit the default configuration of the monthly monitoring (Data Observability end of month scores) checks that are configured for all imported columns on a column level.|
|[get_default_data_observability_monthly_monitoring_table_checks](./defaults/#get_default_data_observability_monthly_monitoring_table_checks)|GET|Returns UI model to show and edit the default configuration of the monthly monitoring (Data Observability end of month scores) checks that are configured for all imported tables on a table level.|
|[get_default_profiling_column_checks](./defaults/#get_default_profiling_column_checks)|GET|Returns UI model to show and edit the default configuration of the profiling checks that are configured for all imported column on a column level.|
|[get_default_profiling_table_checks](./defaults/#get_default_profiling_table_checks)|GET|Returns UI model to show and edit the default configuration of the profiling checks that are configured for all imported tables on a table level.|
|[get_default_schedule](./defaults/#get_default_schedule)|GET|Returns spec to show and edit the default configuration of schedules.|
|[get_default_webhooks](./defaults/#get_default_webhooks)|GET|Returns spec to show and edit the default configuration of webhooks.|
|[update_default_data_observability_daily_monitoring_column_checks](./defaults/#update_default_data_observability_daily_monitoring_column_checks)|PUT|New configuration of the default daily monitoring (data observability) checks on a column level. These checks will be applied on new columns.|
|[update_default_data_observability_daily_monitoring_table_checks](./defaults/#update_default_data_observability_daily_monitoring_table_checks)|PUT|New configuration of the default daily monitoring (data observability) checks on a table level. These checks will be applied on new tables.|
|[update_default_data_observability_monthly_monitoring_column_checks](./defaults/#update_default_data_observability_monthly_monitoring_column_checks)|PUT|New configuration of the default monthly monitoring checkpoints on a column level. These checks will be applied on new columns.|
|[update_default_data_observability_monthly_monitoring_table_checks](./defaults/#update_default_data_observability_monthly_monitoring_table_checks)|PUT|New configuration of the default monthly monitoring checkpoints on a table level. These checks will be applied on new tables.|
|[update_default_profiling_column_checks](./defaults/#update_default_profiling_column_checks)|PUT|New configuration of the default profiling checks on a column level. These checks will be applied to new columns.|
|[update_default_profiling_table_checks](./defaults/#update_default_profiling_table_checks)|PUT|New configuration of the default profiling checks on a table level. These checks will be applied to new tables.|
|[update_default_schedules](./defaults/#update_default_schedules)|PUT|New configuration of the default schedules.|
|[update_default_webhooks](./defaults/#update_default_webhooks)|PUT|New configuration of the default webhooks.|


## environment  
DQOps environment and configuration controller, provides access to the DQOps configuration, current user&#x27;s information and issue local API Keys for the calling user.  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[get_dqo_settings](./environment/#get_dqo_settings)|GET|Returns all effective DQOps configuration settings.|
|[get_user_profile](./environment/#get_user_profile)|GET|Returns the profile of the current user.|
|[issue_api_key](./environment/#issue_api_key)|GET|Issues a local API Key for the calling user. This API Key could be used to authenticate using the DQOps REST API client. This API Key should be passed in the &quot;Authorization&quot; HTTP header in the format &quot;Authorization: Bearer &lt;api_key&gt;&quot;.|


## errors  
Returns the errors related to check executions on tables and columns.  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[get_column_monitoring_errors](./errors/#get_column_monitoring_errors)|GET|Returns errors related to the recent column level monitoring executions for the monitoring at a requested time scale|
|[get_column_partitioned_errors](./errors/#get_column_partitioned_errors)|GET|Returns the errors related to the recent column level partitioned checks executions for a requested time scale|
|[get_column_profiling_errors](./errors/#get_column_profiling_errors)|GET|Returns the errors related to the recent check executions for all column level data quality profiling checks on a column|
|[get_table_monitoring_errors](./errors/#get_table_monitoring_errors)|GET|Returns the errors related to the most recent table level monitoring executions for the monitoring at a requested time scale|
|[get_table_partitioned_errors](./errors/#get_table_partitioned_errors)|GET|Returns errors related to the recent table level partitioned checks executions for a requested time scale|
|[get_table_profiling_errors](./errors/#get_table_profiling_errors)|GET|Returns the errors related to the most recent check executions for all table level data quality profiling checks on a table|


## healthcheck  
Health check service for checking if the DQOps service is up and operational.  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[is_healthy](./healthcheck/#is_healthy)|GET|Checks if the DQOps instance is healthy and operational. Returns a text &quot;OK&quot; and a HTTP status code 200 when the service is active and can accept jobs,  or returns a text &quot;UNAVAILABLE&quot; and a HTTP status code 503 when the service is still starting or is shutting down.|


## incidents  
Data quality incidents controller that supports loading incidents and changing the status of an incident.  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[find_connection_incident_stats](./incidents/#find_connection_incident_stats)|GET|Returns a list of connection names with incident statistics - the count of recent open incidents.|
|[find_recent_incidents_on_connection](./incidents/#find_recent_incidents_on_connection)|GET|Returns a list of recent data quality incidents.|
|[get_incident](./incidents/#get_incident)|GET|Return a single data quality incident&#x27;s details.|
|[get_incident_histogram](./incidents/#get_incident_histogram)|GET|Generates histograms of data quality issues for each day, returning the number of data quality issues on that day. The other histograms are by a column name and by a check name.|
|[get_incident_issues](./incidents/#get_incident_issues)|GET|Return a paged list of failed data quality check results that are related to an incident.|
|[set_incident_issue_url](./incidents/#set_incident_issue_url)|POST|Changes the incident&#x27;s issueUrl to a new status.|
|[set_incident_status](./incidents/#set_incident_status)|POST|Changes the incident&#x27;s status to a new status.|


## jobs  
Jobs management controller that supports starting new jobs, such as running selected data quality checks  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[cancel_job](./jobs/#cancel_job)|DELETE|Cancels a running job|
|[collect_statistics_on_data_groups](./jobs/#collect_statistics_on_data_groups)|POST|Starts a new background job that will run selected data statistics collectors on tables, calculating separate metric for each data grouping|
|[collect_statistics_on_table](./jobs/#collect_statistics_on_table)|POST|Starts a new background job that will run selected data statistics collectors on a whole table|
|[delete_stored_data](./jobs/#delete_stored_data)|POST|Starts a new background job that will delete stored data about check results, sensor readouts etc.|
|[get_all_jobs](./jobs/#get_all_jobs)|GET|Retrieves a list of all queued and recently finished jobs.|
|[get_job](./jobs/#get_job)|GET|Retrieves the current status of a single job, identified by a job id.|
|[get_job_changes_since](./jobs/#get_job_changes_since)|GET|Retrieves an incremental list of job changes (new jobs or job status changes)|
|[import_tables](./jobs/#import_tables)|POST|Starts a new background job that will import selected tables.|
|[is_cron_scheduler_running](./jobs/#is_cron_scheduler_running)|GET|Checks if the DQOps internal CRON scheduler is running and processing jobs scheduled using cron expressions.|
|[run_checks](./jobs/#run_checks)|POST|Starts a new background job that will run selected data quality checks|
|[start_cron_scheduler](./jobs/#start_cron_scheduler)|POST|Starts the job scheduler that runs monitoring jobs that are scheduled by assigning cron expressions.|
|[stop_cron_scheduler](./jobs/#stop_cron_scheduler)|POST|Stops the job scheduler that runs monitoring jobs that are scheduled by assigning cron expressions.|
|[synchronize_folders](./jobs/#synchronize_folders)|POST|Starts multiple file synchronization jobs that will synchronize files from selected DQOps User home folders to the DQOps Cloud. The default synchronization mode is a full synchronization (upload local files, download new files from the cloud).|
|[wait_for_job](./jobs/#wait_for_job)|GET|Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.|
|[wait_for_run_checks_job](./jobs/#wait_for_run_checks_job)|GET|Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.|


## log_shipping  
Log shipping controller that accepts logs sent from a web application or external tools and aggregates them in the local DQOps instance logs.  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[log_debug](./log_shipping/#log_debug)|POST|Logs an information message in the server&#x27;s logs as a debug severity log entry.|
|[log_error](./log_shipping/#log_error)|POST|Logs an information message in the server&#x27;s logs as an error severity log entry.|
|[log_info](./log_shipping/#log_info)|POST|Logs an information message in the server&#x27;s logs as an info severity log entry.|
|[log_warn](./log_shipping/#log_warn)|POST|Logs an information message in the server&#x27;s logs as a warn severity log entry.|


## rules  
Rule management  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[create_rule](./rules/#create_rule)|POST|Creates (adds) a new custom rule given the rule definition.|
|[delete_rule](./rules/#delete_rule)|DELETE|Deletes a custom rule definition|
|[get_all_rules](./rules/#get_all_rules)|GET|Returns a flat list of all rules available in DQOps, both built-in rules and user defined or customized rules.|
|[get_rule](./rules/#get_rule)|GET|Returns a rule definition|
|[get_rule_folder_tree](./rules/#get_rule_folder_tree)|GET|Returns a tree of all rules available in DQOps, both built-in rules and user defined or customized rules.|
|[update_rule](./rules/#update_rule)|PUT|Updates an existing rule, making a custom rule definition if it is not present|


## schemas  
Schema management  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[get_schema_monitoring_checks_model](./schemas/#get_schema_monitoring_checks_model)|GET|Return a UI friendly model of configurations for data quality monitoring checks on a schema|
|[get_schema_monitoring_checks_templates](./schemas/#get_schema_monitoring_checks_templates)|GET|Return available data quality checks on a requested schema.|
|[get_schema_partitioned_checks_model](./schemas/#get_schema_partitioned_checks_model)|GET|Return a UI friendly model of configurations for data quality partitioned checks on a schema|
|[get_schema_partitioned_checks_templates](./schemas/#get_schema_partitioned_checks_templates)|GET|Return available data quality checks on a requested schema.|
|[get_schema_profiling_checks_model](./schemas/#get_schema_profiling_checks_model)|GET|Return a flat list of configurations for profiling checks on a schema|
|[get_schema_profiling_checks_templates](./schemas/#get_schema_profiling_checks_templates)|GET|Return available data quality checks on a requested schema.|
|[get_schemas](./schemas/#get_schemas)|GET|Returns a list of schemas inside a connection|


## sensor_readouts  
Returns the complete sensor readouts of executed checks on tables and columns.  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[get_column_monitoring_sensor_readouts](./sensor_readouts/#get_column_monitoring_sensor_readouts)|GET|Returns a complete view of the sensor readouts for recent column level monitoring executions for the monitoring at a requested time scale|
|[get_column_partitioned_sensor_readouts](./sensor_readouts/#get_column_partitioned_sensor_readouts)|GET|Returns a view of the sensor readouts for recent column level partitioned checks executions for a requested time scale|
|[get_column_profiling_sensor_readouts](./sensor_readouts/#get_column_profiling_sensor_readouts)|GET|Returns sensor results of the recent check executions for all column level data quality profiling checks on a column|
|[get_table_monitoring_sensor_readouts](./sensor_readouts/#get_table_monitoring_sensor_readouts)|GET|Returns the complete results of the most recent table level monitoring executions for the monitoring at a requested time scale|
|[get_table_partitioned_sensor_readouts](./sensor_readouts/#get_table_partitioned_sensor_readouts)|GET|Returns a complete view of sensor readouts for recent table level partitioned checks executions for a requested time scale|
|[get_table_profiling_sensor_readouts](./sensor_readouts/#get_table_profiling_sensor_readouts)|GET|Returns the complete results of the most recent check executions for all table level data quality profiling checks on a table|


## sensors  
Sensors definition management  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[create_sensor](./sensors/#create_sensor)|POST|Creates (adds) a new sensor given sensor information.|
|[delete_sensor](./sensors/#delete_sensor)|DELETE|Deletes a custom sensor definition|
|[get_all_sensors](./sensors/#get_all_sensors)|GET|Returns a flat list of all sensors available in DQOps, both built-in sensors and user defined or customized sensors.|
|[get_sensor](./sensors/#get_sensor)|GET|Returns a sensor model|
|[get_sensor_folder_tree](./sensors/#get_sensor_folder_tree)|GET|Returns a tree of all sensors available in DQOps, both built-in sensors and user defined or customized sensors.|
|[update_sensor](./sensors/#update_sensor)|PUT|Updates an existing sensor, making a custom sensor definition if it is not present. 
Removes sensor if custom definition is same as Dqo Home sensor|


## shared_credentials  
Shared credentials management for managing credentials that are stored in the shared .credentials folder in the DQOps user&#x27;s home folder.  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[create_shared_credential](./shared_credentials/#create_shared_credential)|POST|Creates (adds) a new shared credential, which creates a file in the DQOps user&#x27;s home .credentials/ folder named as the credential and with the content that is provided in this call.|
|[delete_shared_credential](./shared_credentials/#delete_shared_credential)|DELETE|Deletes a shared credential file from the DQOps user&#x27;s home .credentials/ folder.|
|[download_shared_credential](./shared_credentials/#download_shared_credential)|GET|Downloads a shared credential&#x27;s file|
|[get_all_shared_credentials](./shared_credentials/#get_all_shared_credentials)|GET|Returns a list of all shared credentials that are present in the DQOps user&#x27;s home .credentials/ folder..|
|[get_shared_credential](./shared_credentials/#get_shared_credential)|GET|Returns a shared credential content|
|[update_shared_credential](./shared_credentials/#update_shared_credential)|PUT|Updates an existing shared credential, replacing the credential&#x27;s file content.|


## table_comparison_results  
Controller that returns the results of the most recent table comparison that was performed between the compared table and the reference table (the source of truth).  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[get_table_comparison_monitoring_results](./table_comparison_results/#get_table_comparison_monitoring_results)|GET|Retrieves the results of the most table comparison performed using the monitoring comparison checks.|
|[get_table_comparison_partitioned_results](./table_comparison_results/#get_table_comparison_partitioned_results)|GET|Retrieves the results of the most table comparison performed using the partitioned comparison checks, comparing days or months of data.|
|[get_table_comparison_profiling_results](./table_comparison_results/#get_table_comparison_profiling_results)|GET|Retrieves the results of the most table comparison performed using the profiling checks comparison checks.|


## table_comparisons  
Manages the configuration of table comparisons between tables on the same or different data sources  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[create_table_comparison_configuration](./table_comparisons/#create_table_comparison_configuration)|POST|Creates a new table comparison configuration added to the compared table|
|[create_table_comparison_monitoring_daily](./table_comparisons/#create_table_comparison_monitoring_daily)|POST|Creates a table comparison configuration using daily monitoring checks|
|[create_table_comparison_monitoring_monthly](./table_comparisons/#create_table_comparison_monitoring_monthly)|POST|Creates a table comparison configuration using monthly monitoring checks|
|[create_table_comparison_partitioned_daily](./table_comparisons/#create_table_comparison_partitioned_daily)|POST|Creates a table comparison configuration using daily partitioned checks|
|[create_table_comparison_partitioned_monthly](./table_comparisons/#create_table_comparison_partitioned_monthly)|POST|Creates a table comparison configuration using monthly partitioned checks|
|[create_table_comparison_profiling](./table_comparisons/#create_table_comparison_profiling)|POST|Creates a table comparison configuration using profiling checks|
|[delete_table_comparison_configuration](./table_comparisons/#delete_table_comparison_configuration)|DELETE|Deletes a table comparison configuration from a compared table|
|[get_table_comparison_configuration](./table_comparisons/#get_table_comparison_configuration)|GET|Returns a model of the table comparison configuration|
|[get_table_comparison_configurations](./table_comparisons/#get_table_comparison_configurations)|GET|Returns the list of table comparison configurations on a compared table|
|[get_table_comparison_monitoring_daily](./table_comparisons/#get_table_comparison_monitoring_daily)|GET|Returns a model of the table comparison using daily monitoring checks (comparison once a day)|
|[get_table_comparison_monitoring_monthly](./table_comparisons/#get_table_comparison_monitoring_monthly)|GET|Returns a model of the table comparison using monthly monitoring checks (comparison once a month)|
|[get_table_comparison_partitioned_daily](./table_comparisons/#get_table_comparison_partitioned_daily)|GET|Returns a model of the table comparison using daily partition checks (comparing day to day)|
|[get_table_comparison_partitioned_monthly](./table_comparisons/#get_table_comparison_partitioned_monthly)|GET|Returns a model of the table comparison using monthly partition checks (comparing month to month)|
|[get_table_comparison_profiling](./table_comparisons/#get_table_comparison_profiling)|GET|Returns a model of the table comparison using profiling checks (comparison at any time)|
|[update_table_comparison_configuration](./table_comparisons/#update_table_comparison_configuration)|PUT|Updates a table configuration configuration|
|[update_table_comparison_monitoring_daily](./table_comparisons/#update_table_comparison_monitoring_daily)|PUT|Updates a table comparison checks monitoring daily|
|[update_table_comparison_monitoring_monthly](./table_comparisons/#update_table_comparison_monitoring_monthly)|PUT|Updates a table comparison checks monitoring monthly|
|[update_table_comparison_partitioned_daily](./table_comparisons/#update_table_comparison_partitioned_daily)|PUT|Updates a table comparison checks partitioned daily (comparing day to day)|
|[update_table_comparison_partitioned_monthly](./table_comparisons/#update_table_comparison_partitioned_monthly)|PUT|Updates a table comparison checks partitioned monthly (comparing month to month)|
|[update_table_comparison_profiling](./table_comparisons/#update_table_comparison_profiling)|PUT|Updates a table comparison profiling checks|


## tables  
Manages tables inside a connection/schema  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[create_table](./tables/#create_table)|POST|Creates a new table (adds a table metadata)|
|[delete_table](./tables/#delete_table)|DELETE|Deletes a table|
|[get_table](./tables/#get_table)|GET|Return the table specification|
|[get_table_basic](./tables/#get_table_basic)|GET|Return the basic table information|
|[get_table_columns_monitoring_checks_model](./tables/#get_table_columns_monitoring_checks_model)|GET|Return a UI friendly model of configurations for column-level data quality monitoring checks on a table|
|[get_table_columns_partitioned_checks_model](./tables/#get_table_columns_partitioned_checks_model)|GET|Return a UI friendly model of configurations for column-level data quality partitioned checks on a table|
|[get_table_columns_profiling_checks_model](./tables/#get_table_columns_profiling_checks_model)|GET|Return a UI friendly model of configurations for column-level data quality profiling checks on a table|
|[get_table_comments](./tables/#get_table_comments)|GET|Return the list of comments added to a table|
|[get_table_daily_monitoring_checks](./tables/#get_table_daily_monitoring_checks)|GET|Return the configuration of daily table level data quality monitoring on a table|
|[get_table_daily_partitioned_checks](./tables/#get_table_daily_partitioned_checks)|GET|Return the configuration of daily table level data quality partitioned checks on a table|
|[get_table_default_grouping_configuration](./tables/#get_table_default_grouping_configuration)|GET|Return the default data grouping configuration for a table.|
|[get_table_incident_grouping](./tables/#get_table_incident_grouping)|GET|Return the configuration of incident grouping on a table|
|[get_table_labels](./tables/#get_table_labels)|GET|Return the list of labels assigned to a table|
|[get_table_monitoring_checks_basic_model](./tables/#get_table_monitoring_checks_basic_model)|GET|Return a simplistic UI friendly model of table level data quality monitoring on a table for a given time scale|
|[get_table_monitoring_checks_model](./tables/#get_table_monitoring_checks_model)|GET|Return a UI friendly model of configurations for table level data quality monitoring on a table for a given time scale|
|[get_table_monitoring_checks_model_filter](./tables/#get_table_monitoring_checks_model_filter)|GET|Return a UI friendly model of configurations for table level data quality monitoring on a table for a given time scale, filtered by category and check name.|
|[get_table_monitoring_checks_monthly](./tables/#get_table_monitoring_checks_monthly)|GET|Return the configuration of monthly table level data quality monitoring on a table|
|[get_table_monitoring_checks_templates](./tables/#get_table_monitoring_checks_templates)|GET|Return available data quality checks on a requested table.|
|[get_table_partitioned_checks_basic_model](./tables/#get_table_partitioned_checks_basic_model)|GET|Return a simplistic UI friendly model of table level data quality partitioned checks on a table for a given time scale|
|[get_table_partitioned_checks_model](./tables/#get_table_partitioned_checks_model)|GET|Return a UI friendly model of configurations for table level data quality partitioned checks on a table for a given time scale|
|[get_table_partitioned_checks_model_filter](./tables/#get_table_partitioned_checks_model_filter)|GET|Return a UI friendly model of configurations for table level data quality partitioned checks on a table for a given time scale, filtered by category and check name.|
|[get_table_partitioned_checks_monthly](./tables/#get_table_partitioned_checks_monthly)|GET|Return the configuration of monthly table level data quality partitioned checks on a table|
|[get_table_partitioned_checks_templates](./tables/#get_table_partitioned_checks_templates)|GET|Return available data quality checks on a requested table.|
|[get_table_partitioning](./tables/#get_table_partitioning)|GET|Return the table partitioning information|
|[get_table_profiling_checks](./tables/#get_table_profiling_checks)|GET|Return the configuration of table level data quality checks on a table|
|[get_table_profiling_checks_basic_model](./tables/#get_table_profiling_checks_basic_model)|GET|Return a simplistic UI friendly model of all table level data quality profiling checks on a table|
|[get_table_profiling_checks_model](./tables/#get_table_profiling_checks_model)|GET|Return a UI friendly model of configurations for all table level data quality profiling checks on a table|
|[get_table_profiling_checks_model_filter](./tables/#get_table_profiling_checks_model_filter)|GET|Return a UI friendly model of configurations for all table level data quality profiling checks on a table passing a filter|
|[get_table_profiling_checks_templates](./tables/#get_table_profiling_checks_templates)|GET|Return available data quality checks on a requested table.|
|[get_table_scheduling_group_override](./tables/#get_table_scheduling_group_override)|GET|Return the schedule override configuration for a table|
|[get_table_statistics](./tables/#get_table_statistics)|GET|Returns a list of the profiler (statistics) metrics on a chosen table captured during the most recent statistics collection.|
|[get_tables](./tables/#get_tables)|GET|Returns a list of tables inside a connection/schema|
|[update_table](./tables/#update_table)|PUT|Updates an existing table specification, changing all the fields|
|[update_table_basic](./tables/#update_table_basic)|PUT|Updates the basic field of an existing table, changing only the most important fields.|
|[update_table_comments](./tables/#update_table_comments)|PUT|Updates the list of comments on an existing table.|
|[update_table_daily_monitoring_checks](./tables/#update_table_daily_monitoring_checks)|PUT|Updates the list of daily table level data quality monitoring on an existing table.|
|[update_table_default_grouping_configuration](./tables/#update_table_default_grouping_configuration)|PUT|Updates the default data grouping configuration at a table level.|
|[update_table_incident_grouping](./tables/#update_table_incident_grouping)|PUT|Updates the configuration of incident grouping on a table.|
|[update_table_labels](./tables/#update_table_labels)|PUT|Updates the list of assigned labels of an existing table.|
|[update_table_monitoring_checks_model](./tables/#update_table_monitoring_checks_model)|PUT|Updates the data quality monitoring from a model that contains a patch with changes.|
|[update_table_monitoring_checks_monthly](./tables/#update_table_monitoring_checks_monthly)|PUT|Updates the list of monthly table level data quality monitoring on an existing table.|
|[update_table_partitioned_checks_daily](./tables/#update_table_partitioned_checks_daily)|PUT|Updates the list of daily table level data quality partitioned checks on an existing table.|
|[update_table_partitioned_checks_model](./tables/#update_table_partitioned_checks_model)|PUT|Updates the data quality partitioned checks from a model that contains a patch with changes.|
|[update_table_partitioned_checks_monthly](./tables/#update_table_partitioned_checks_monthly)|PUT|Updates the list of monthly table level data quality partitioned checks on an existing table.|
|[update_table_partitioning](./tables/#update_table_partitioning)|PUT|Updates the table partitioning configuration of an existing table.|
|[update_table_profiling_checks](./tables/#update_table_profiling_checks)|PUT|Updates the list of table level data quality profiling checks on an existing table.|
|[update_table_profiling_checks_model](./tables/#update_table_profiling_checks_model)|PUT|Updates the data quality profiling checks from a model that contains a patch with changes.|
|[update_table_scheduling_group_override](./tables/#update_table_scheduling_group_override)|PUT|Updates the overridden schedule configuration of an existing table for a named schedule group (named schedule for checks using the same time scale).|


## timezones  
Timezone management  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[get_available_zone_ids](./timezones/#get_available_zone_ids)|GET|Returns a list of available time zone ids|


## users  
User management service  
  
|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|[change_caller_password](./users/#change_caller_password)|PUT|Changes the password of the calling user. When the user is identified by the DQOps local API key, it is the user whose email is stored in the DQOps API Key.|
|[change_user_password](./users/#change_user_password)|PUT|Changes the password of a user identified by the email.|
|[create_user](./users/#create_user)|POST|Creates (adds) a new user to a multi-user account.|
|[delete_user](./users/#delete_user)|DELETE|Deletes a user from a multi-user account.|
|[get_all_users](./users/#get_all_users)|GET|Returns a list of all users.|
|[get_user](./users/#get_user)|GET|Returns the user model that describes the role of a user identified by an email|
|[update_user](./users/#update_user)|PUT|Updates a user in a multi-user account. The user&#x27;s email cannot be changed.|


