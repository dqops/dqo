# Reference of all operations used by DQOps REST API
This is a list of the operations in DQOps REST API Python client broken down by individual groups of operations.


## check_results
Returns all the data quality check results of executed checks on tables and columns.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`get_column_monitoring_checks_results`</span>](./check_results.md#get_column_monitoring_checks_results)|GET|Returns a complete view of the recent column level monitoring executions for the monitoring at a requested time scale|
|<span class="no-wrap-code">[`get_column_partitioned_checks_results`</span>](./check_results.md#get_column_partitioned_checks_results)|GET|Returns an overview of the most recent column level partitioned checks executions for a requested time scale|
|<span class="no-wrap-code">[`get_column_profiling_checks_results`</span>](./check_results.md#get_column_profiling_checks_results)|GET|Returns an overview of the most recent check executions for all column level data quality profiling checks on a column|
|<span class="no-wrap-code">[`get_table_data_quality_status`</span>](./check_results.md#get_table_data_quality_status)|GET|Read the most recent results of executed data quality checks on the table and return the current table&#x27;s data quality status - the number of failed data quality checks if the table has active data quality issues. Also returns the names of data quality checks that did not pass most recently. This operation verifies only the status of the most recently executed data quality checks. Previous data quality issues are not counted.|
|<span class="no-wrap-code">[`get_table_monitoring_checks_results`</span>](./check_results.md#get_table_monitoring_checks_results)|GET|Returns the complete results of the most recent table level monitoring executions for the monitoring at a requested time scale|
|<span class="no-wrap-code">[`get_table_partitioned_checks_results`</span>](./check_results.md#get_table_partitioned_checks_results)|GET|Returns a complete view of the recent table level partitioned checks executions for a requested time scale|
|<span class="no-wrap-code">[`get_table_profiling_checks_results`</span>](./check_results.md#get_table_profiling_checks_results)|GET|Returns the complete results of the most recent check executions for all table level data quality profiling checks on a table|


## check_results_overview
Returns the overview of the recently executed checks on tables and columns, returning a summary of the last 5 runs.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`get_column_monitoring_checks_overview`</span>](./check_results_overview.md#get_column_monitoring_checks_overview)|GET|Returns an overview of the most recent column level monitoring executions for the monitoring at a requested time scale|
|<span class="no-wrap-code">[`get_column_partitioned_checks_overview`</span>](./check_results_overview.md#get_column_partitioned_checks_overview)|GET|Returns an overview of the most recent column level partitioned checks executions for a requested time scale|
|<span class="no-wrap-code">[`get_column_profiling_checks_overview`</span>](./check_results_overview.md#get_column_profiling_checks_overview)|GET|Returns an overview of the most recent check executions for all column level data quality profiling checks on a column|
|<span class="no-wrap-code">[`get_table_monitoring_checks_overview`</span>](./check_results_overview.md#get_table_monitoring_checks_overview)|GET|Returns an overview of the most recent table level monitoring executions for the monitoring at a requested time scale|
|<span class="no-wrap-code">[`get_table_partitioned_checks_overview`</span>](./check_results_overview.md#get_table_partitioned_checks_overview)|GET|Returns an overview of the most recent table level partitioned checks executions for a requested time scale|
|<span class="no-wrap-code">[`get_table_profiling_checks_overview`</span>](./check_results_overview.md#get_table_profiling_checks_overview)|GET|Returns an overview of the most recent check executions for all table level data quality profiling checks on a table|


## checks
Data quality check definition management operations for adding/removing/changing custom data quality checks.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`create_check`</span>](./checks.md#create_check)|POST|Creates (adds) a new custom check that is a pair of a sensor name and a rule name.|
|<span class="no-wrap-code">[`delete_check`</span>](./checks.md#delete_check)|DELETE|Deletes a custom check definition|
|<span class="no-wrap-code">[`get_all_checks`</span>](./checks.md#get_all_checks)|GET|Returns a flat list of all checks available in DQOps, both built-in checks and user defined or customized checks.|
|<span class="no-wrap-code">[`get_check`</span>](./checks.md#get_check)|GET|Returns a check definition|
|<span class="no-wrap-code">[`get_check_folder_tree`</span>](./checks.md#get_check_folder_tree)|GET|Returns a tree of all checks available in DQOps, both built-in checks and user defined or customized checks.|
|<span class="no-wrap-code">[`update_check`</span>](./checks.md#update_check)|PUT|Updates an existing check, making a custom check definition if it is not present|


## columns
Operations related to manage the metadata of columns, and managing the configuration of column-level data quality checks.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`create_column`</span>](./columns.md#create_column)|POST|Creates a new column (adds a column metadata to the table)|
|<span class="no-wrap-code">[`delete_column`</span>](./columns.md#delete_column)|DELETE|Deletes a column from the table|
|<span class="no-wrap-code">[`get_column`</span>](./columns.md#get_column)|GET|Returns the full column specification|
|<span class="no-wrap-code">[`get_column_basic`</span>](./columns.md#get_column_basic)|GET|Returns the column specification|
|<span class="no-wrap-code">[`get_column_comments`</span>](./columns.md#get_column_comments)|GET|Return the list of comments assigned to a column|
|<span class="no-wrap-code">[`get_column_labels`</span>](./columns.md#get_column_labels)|GET|Return the list of labels assigned to a column|
|<span class="no-wrap-code">[`get_column_monitoring_checks_basic_model`</span>](./columns.md#get_column_monitoring_checks_basic_model)|GET|Return a simplistic UI friendly model of column level data quality monitoring on a column|
|<span class="no-wrap-code">[`get_column_monitoring_checks_daily`</span>](./columns.md#get_column_monitoring_checks_daily)|GET|Return the configuration of daily column level data quality monitoring on a column|
|<span class="no-wrap-code">[`get_column_monitoring_checks_model`</span>](./columns.md#get_column_monitoring_checks_model)|GET|Return a UI friendly model of column level data quality monitoring on a column|
|<span class="no-wrap-code">[`get_column_monitoring_checks_model_filter`</span>](./columns.md#get_column_monitoring_checks_model_filter)|GET|Return a UI friendly model of column level data quality monitoring on a column filtered by category and check name|
|<span class="no-wrap-code">[`get_column_monitoring_checks_monthly`</span>](./columns.md#get_column_monitoring_checks_monthly)|GET|Return the configuration of monthly column level data quality monitoring on a column|
|<span class="no-wrap-code">[`get_column_partitioned_checks_basic_model`</span>](./columns.md#get_column_partitioned_checks_basic_model)|GET|Return a simplistic UI friendly model of column level data quality partitioned checks on a column|
|<span class="no-wrap-code">[`get_column_partitioned_checks_daily`</span>](./columns.md#get_column_partitioned_checks_daily)|GET|Return the configuration of daily column level data quality partitioned checks on a column|
|<span class="no-wrap-code">[`get_column_partitioned_checks_model`</span>](./columns.md#get_column_partitioned_checks_model)|GET|Return a UI friendly model of column level data quality partitioned checks on a column|
|<span class="no-wrap-code">[`get_column_partitioned_checks_model_filter`</span>](./columns.md#get_column_partitioned_checks_model_filter)|GET|Return a UI friendly model of column level data quality partitioned checks on a column, filtered by category and check name|
|<span class="no-wrap-code">[`get_column_partitioned_checks_monthly`</span>](./columns.md#get_column_partitioned_checks_monthly)|GET|Return the configuration of monthly column level data quality partitioned checks on a column|
|<span class="no-wrap-code">[`get_column_profiling_checks`</span>](./columns.md#get_column_profiling_checks)|GET|Return the configuration of column level data quality profiling checks on a column|
|<span class="no-wrap-code">[`get_column_profiling_checks_basic_model`</span>](./columns.md#get_column_profiling_checks_basic_model)|GET|Return a simplistic UI friendly model of column level data quality profiling checks on a column|
|<span class="no-wrap-code">[`get_column_profiling_checks_model`</span>](./columns.md#get_column_profiling_checks_model)|GET|Return a UI friendly model of data quality profiling checks on a column|
|<span class="no-wrap-code">[`get_column_profiling_checks_model_filter`</span>](./columns.md#get_column_profiling_checks_model_filter)|GET|Return a UI friendly model of data quality profiling checks on a column filtered by category and check name|
|<span class="no-wrap-code">[`get_column_statistics`</span>](./columns.md#get_column_statistics)|GET|Returns the column specification with the metrics captured by the most recent statistics collection.|
|<span class="no-wrap-code">[`get_columns`</span>](./columns.md#get_columns)|GET|Returns a list of columns inside a table|
|<span class="no-wrap-code">[`get_columns_statistics`</span>](./columns.md#get_columns_statistics)|GET|Returns a list of columns inside a table with the metrics captured by the most recent statistics collection.|
|<span class="no-wrap-code">[`update_column`</span>](./columns.md#update_column)|PUT|Updates an existing column specification, changing all the fields (even the column level data quality checks).|
|<span class="no-wrap-code">[`update_column_basic`</span>](./columns.md#update_column_basic)|PUT|Updates an existing column, changing only the basic information like the expected data type (the data type snapshot).|
|<span class="no-wrap-code">[`update_column_comments`</span>](./columns.md#update_column_comments)|PUT|Updates the list of comments assigned to a column.|
|<span class="no-wrap-code">[`update_column_labels`</span>](./columns.md#update_column_labels)|PUT|Updates the list of labels assigned to a column.|
|<span class="no-wrap-code">[`update_column_monitoring_checks_daily`</span>](./columns.md#update_column_monitoring_checks_daily)|PUT|Updates configuration of daily column level data quality monitoring on a column.|
|<span class="no-wrap-code">[`update_column_monitoring_checks_model`</span>](./columns.md#update_column_monitoring_checks_model)|PUT|Updates configuration of column level data quality monitoring on a column, for a given time scale, from a UI friendly model.|
|<span class="no-wrap-code">[`update_column_monitoring_checks_monthly`</span>](./columns.md#update_column_monitoring_checks_monthly)|PUT|Updates configuration of monthly column level data quality monitoring checks on a column.|
|<span class="no-wrap-code">[`update_column_partitioned_checks_daily`</span>](./columns.md#update_column_partitioned_checks_daily)|PUT|Updates configuration of daily column level data quality partitioned checks on a column.|
|<span class="no-wrap-code">[`update_column_partitioned_checks_model`</span>](./columns.md#update_column_partitioned_checks_model)|PUT|Updates configuration of column level data quality partitioned checks on a column, for a given time scale, from a UI friendly model.|
|<span class="no-wrap-code">[`update_column_partitioned_checks_monthly`</span>](./columns.md#update_column_partitioned_checks_monthly)|PUT|Updates configuration of monthly column level data quality partitioned checks on a column.|
|<span class="no-wrap-code">[`update_column_profiling_checks`</span>](./columns.md#update_column_profiling_checks)|PUT|Updates configuration of column level data quality profiling checks on a column.|
|<span class="no-wrap-code">[`update_column_profiling_checks_model`</span>](./columns.md#update_column_profiling_checks_model)|PUT|Updates configuration of column level data quality profiling checks on a column from a UI friendly model.|


## connections
Operations for adding/updating/deleting the configuration of data sources managed by DQOps.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`bulk_activate_connection_checks`</span>](./connections.md#bulk_activate_connection_checks)|PUT|Activates all named check on this connection in the locations specified by filter|
|<span class="no-wrap-code">[`bulk_deactivate_connection_checks`</span>](./connections.md#bulk_deactivate_connection_checks)|PUT|Deactivates (deletes) all named check on this connection in the locations specified by filter|
|<span class="no-wrap-code">[`create_connection`</span>](./connections.md#create_connection)|POST|Creates a new connection|
|<span class="no-wrap-code">[`create_connection_basic`</span>](./connections.md#create_connection_basic)|POST|Creates a new connection given the basic information.|
|<span class="no-wrap-code">[`delete_connection`</span>](./connections.md#delete_connection)|DELETE|Deletes a connection|
|<span class="no-wrap-code">[`get_all_connections`</span>](./connections.md#get_all_connections)|GET|Returns a list of connections (data sources)|
|<span class="no-wrap-code">[`get_connection`</span>](./connections.md#get_connection)|GET|Return the full details of a connection given the connection name|
|<span class="no-wrap-code">[`get_connection_basic`</span>](./connections.md#get_connection_basic)|GET|Return the basic details of a connection given the connection name|
|<span class="no-wrap-code">[`get_connection_comments`</span>](./connections.md#get_connection_comments)|GET|Return the comments for a connection|
|<span class="no-wrap-code">[`get_connection_common_columns`</span>](./connections.md#get_connection_common_columns)|GET|Finds common column names that are used on one or more tables. The list of columns is sorted in descending order by column name.|
|<span class="no-wrap-code">[`get_connection_default_grouping_configuration`</span>](./connections.md#get_connection_default_grouping_configuration)|GET|Return the default data grouping configuration for a connection|
|<span class="no-wrap-code">[`get_connection_incident_grouping`</span>](./connections.md#get_connection_incident_grouping)|GET|Retrieves the configuration of data quality incident grouping and incident notifications|
|<span class="no-wrap-code">[`get_connection_labels`</span>](./connections.md#get_connection_labels)|GET|Return the labels for a connection|
|<span class="no-wrap-code">[`get_connection_scheduling_group`</span>](./connections.md#get_connection_scheduling_group)|GET|Return the schedule for a connection for a scheduling group|
|<span class="no-wrap-code">[`update_connection`</span>](./connections.md#update_connection)|PUT|Updates an existing connection|
|<span class="no-wrap-code">[`update_connection_basic`</span>](./connections.md#update_connection_basic)|PUT|Updates the basic information of a connection|
|<span class="no-wrap-code">[`update_connection_comments`</span>](./connections.md#update_connection_comments)|PUT|Updates (replaces) the list of comments of a connection|
|<span class="no-wrap-code">[`update_connection_default_grouping_configuration`</span>](./connections.md#update_connection_default_grouping_configuration)|PUT|Updates the default data grouping connection of a connection|
|<span class="no-wrap-code">[`update_connection_incident_grouping`</span>](./connections.md#update_connection_incident_grouping)|PUT|Updates (replaces) configuration of incident grouping and notifications on a connection (data source) level.|
|<span class="no-wrap-code">[`update_connection_labels`</span>](./connections.md#update_connection_labels)|PUT|Updates the list of labels of a connection|
|<span class="no-wrap-code">[`update_connection_scheduling_group`</span>](./connections.md#update_connection_scheduling_group)|PUT|Updates the schedule of a connection for a scheduling group (named schedule for checks with a similar time series configuration)|


## dashboards
Operations for retrieving the list of data quality dashboards supported by DQOps and issuing short-term access keys to open a dashboard.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`get_all_dashboards`</span>](./dashboards.md#get_all_dashboards)|GET|Returns a list of root folders with dashboards|
|<span class="no-wrap-code">[`get_dashboard_level_1`</span>](./dashboards.md#get_dashboard_level_1)|GET|Returns a single dashboard in the tree of folder with a temporary authenticated url|
|<span class="no-wrap-code">[`get_dashboard_level_2`</span>](./dashboards.md#get_dashboard_level_2)|GET|Returns a single dashboard in the tree of folders with a temporary authenticated url|
|<span class="no-wrap-code">[`get_dashboard_level_3`</span>](./dashboards.md#get_dashboard_level_3)|GET|Returns a single dashboard in the tree of folders with a temporary authenticated url|
|<span class="no-wrap-code">[`get_dashboard_level_4`</span>](./dashboards.md#get_dashboard_level_4)|GET|Returns a single dashboard in the tree of folders with a temporary authenticated url|
|<span class="no-wrap-code">[`get_dashboard_level_5`</span>](./dashboards.md#get_dashboard_level_5)|GET|Returns a single dashboard in the tree of folders with a temporary authenticated url|


## data_grouping_configurations
Operations for managing the configuration of data groupings on a table level in DQOps.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`create_table_grouping_configuration`</span>](./data_grouping_configurations.md#create_table_grouping_configuration)|POST|Creates a new data grouping configuration on a table level|
|<span class="no-wrap-code">[`delete_table_grouping_configuration`</span>](./data_grouping_configurations.md#delete_table_grouping_configuration)|DELETE|Deletes a data grouping configuration from a table|
|<span class="no-wrap-code">[`get_table_grouping_configuration`</span>](./data_grouping_configurations.md#get_table_grouping_configuration)|GET|Returns a model of the data grouping configuration|
|<span class="no-wrap-code">[`get_table_grouping_configurations`</span>](./data_grouping_configurations.md#get_table_grouping_configurations)|GET|Returns the list of data grouping configurations on a table|
|<span class="no-wrap-code">[`set_table_default_grouping_configuration`</span>](./data_grouping_configurations.md#set_table_default_grouping_configuration)|PATCH|Sets a table&#x27;s grouping configuration as the default or disables data grouping|
|<span class="no-wrap-code">[`update_table_grouping_configuration`</span>](./data_grouping_configurations.md#update_table_grouping_configuration)|PUT|Updates a data grouping configuration according to the provided model|


## data_sources
Rest API controller that operates on data sources that are not yet imported, testing connections or retrieving the metadata (schemas and tables).

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`get_remote_data_source_schemas`</span>](./data_sources.md#get_remote_data_source_schemas)|GET|Introspects a list of schemas inside a remote data source, identified by an already imported connection.|
|<span class="no-wrap-code">[`get_remote_data_source_tables`</span>](./data_sources.md#get_remote_data_source_tables)|GET|Introspects the list of columns inside a schema on a remote data source that is identified by a connection that was added to DQOps.|
|<span class="no-wrap-code">[`test_connection`</span>](./data_sources.md#test_connection)|POST|Checks if the given remote connection can be opened and if the credentials are valid|


## defaults
Default settings management for configuring the default data quality checks that are configured for all imported tables and columns.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`get_default_data_observability_daily_monitoring_column_checks`</span>](./defaults.md#get_default_data_observability_daily_monitoring_column_checks)|GET|Returns UI model to show and edit the default configuration of the daily monitoring (Data Observability and monitoring) checks that are configured for all imported columns on a column level.|
|<span class="no-wrap-code">[`get_default_data_observability_daily_monitoring_table_checks`</span>](./defaults.md#get_default_data_observability_daily_monitoring_table_checks)|GET|Returns UI model to show and edit the default configuration of the daily monitoring (Data Observability and monitoring) checks that are configured for all imported tables on a table level.|
|<span class="no-wrap-code">[`get_default_data_observability_monthly_monitoring_column_checks`</span>](./defaults.md#get_default_data_observability_monthly_monitoring_column_checks)|GET|Returns UI model to show and edit the default configuration of the monthly monitoring (Data Observability end of month scores) checks that are configured for all imported columns on a column level.|
|<span class="no-wrap-code">[`get_default_data_observability_monthly_monitoring_table_checks`</span>](./defaults.md#get_default_data_observability_monthly_monitoring_table_checks)|GET|Returns UI model to show and edit the default configuration of the monthly monitoring (Data Observability end of month scores) checks that are configured for all imported tables on a table level.|
|<span class="no-wrap-code">[`get_default_profiling_column_checks`</span>](./defaults.md#get_default_profiling_column_checks)|GET|Returns UI model to show and edit the default configuration of the profiling checks that are configured for all imported column on a column level.|
|<span class="no-wrap-code">[`get_default_profiling_table_checks`</span>](./defaults.md#get_default_profiling_table_checks)|GET|Returns UI model to show and edit the default configuration of the profiling checks that are configured for all imported tables on a table level.|
|<span class="no-wrap-code">[`get_default_schedule`</span>](./defaults.md#get_default_schedule)|GET|Returns spec to show and edit the default configuration of schedules.|
|<span class="no-wrap-code">[`get_default_webhooks`</span>](./defaults.md#get_default_webhooks)|GET|Returns spec to show and edit the default configuration of webhooks.|
|<span class="no-wrap-code">[`update_default_data_observability_daily_monitoring_column_checks`</span>](./defaults.md#update_default_data_observability_daily_monitoring_column_checks)|PUT|New configuration of the default daily monitoring (data observability) checks on a column level. These checks will be applied on new columns.|
|<span class="no-wrap-code">[`update_default_data_observability_daily_monitoring_table_checks`</span>](./defaults.md#update_default_data_observability_daily_monitoring_table_checks)|PUT|New configuration of the default daily monitoring (data observability) checks on a table level. These checks will be applied on new tables.|
|<span class="no-wrap-code">[`update_default_data_observability_monthly_monitoring_column_checks`</span>](./defaults.md#update_default_data_observability_monthly_monitoring_column_checks)|PUT|New configuration of the default monthly monitoring checkpoints on a column level. These checks will be applied on new columns.|
|<span class="no-wrap-code">[`update_default_data_observability_monthly_monitoring_table_checks`</span>](./defaults.md#update_default_data_observability_monthly_monitoring_table_checks)|PUT|New configuration of the default monthly monitoring checkpoints on a table level. These checks will be applied on new tables.|
|<span class="no-wrap-code">[`update_default_profiling_column_checks`</span>](./defaults.md#update_default_profiling_column_checks)|PUT|New configuration of the default profiling checks on a column level. These checks will be applied to new columns.|
|<span class="no-wrap-code">[`update_default_profiling_table_checks`</span>](./defaults.md#update_default_profiling_table_checks)|PUT|New configuration of the default profiling checks on a table level. These checks will be applied to new tables.|
|<span class="no-wrap-code">[`update_default_schedules`</span>](./defaults.md#update_default_schedules)|PUT|New configuration of the default schedules.|
|<span class="no-wrap-code">[`update_default_webhooks`</span>](./defaults.md#update_default_webhooks)|PUT|New configuration of the default webhooks.|


## dictionaries
Operations for managing data dictionary CSV files in DQOps. Data dictionaries can be used in *accepted_values* data quality checks.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`create_dictionary`</span>](./dictionaries.md#create_dictionary)|POST|Creates (adds) a new data dictionary CSV file, which creates a file in the DQOps user&#x27;s home dictionaries/ folder named as the dictionary and with the content that is provided in this call.|
|<span class="no-wrap-code">[`delete_dictionary`</span>](./dictionaries.md#delete_dictionary)|DELETE|Deletes a data dictionary CSV file from the DQOps user&#x27;s home dictionaries/ folder.|
|<span class="no-wrap-code">[`download_dictionary`</span>](./dictionaries.md#download_dictionary)|GET|Downloads a data dictionary CSV file|
|<span class="no-wrap-code">[`get_all_dictionaries`</span>](./dictionaries.md#get_all_dictionaries)|GET|Returns a list of all data dictionary CSV files that are present in the DQOps user&#x27;s home dictionaries/ folder.|
|<span class="no-wrap-code">[`get_dictionary`</span>](./dictionaries.md#get_dictionary)|GET|Returns the content of a data dictionary CSV file as a model object|
|<span class="no-wrap-code">[`update_dictionary`</span>](./dictionaries.md#update_dictionary)|PUT|Updates an existing data dictionary CSV file, replacing the dictionary&#x27;s file content.|


## environment
DQOps environment and configuration controller, provides access to the DQOps configuration, current user&#x27;s information and issue local API Keys for the calling user.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`get_dqo_settings`</span>](./environment.md#get_dqo_settings)|GET|Returns all effective DQOps configuration settings.|
|<span class="no-wrap-code">[`get_user_profile`</span>](./environment.md#get_user_profile)|GET|Returns the profile of the current user.|
|<span class="no-wrap-code">[`issue_api_key`</span>](./environment.md#issue_api_key)|GET|Issues a local API Key for the calling user. This API Key can be used to authenticate using the DQOps REST API client. This API Key should be passed in the &quot;Authorization&quot; HTTP header in the format &quot;Authorization: Bearer &lt;api_key&gt;&quot;.|


## errors
Operations that return the execution errors captured when data quality checks were executed on data sources, and sensors or rules failed with an error.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`get_column_monitoring_errors`</span>](./errors.md#get_column_monitoring_errors)|GET|Returns errors related to the recent column level monitoring executions for the monitoring at a requested time scale|
|<span class="no-wrap-code">[`get_column_partitioned_errors`</span>](./errors.md#get_column_partitioned_errors)|GET|Returns the errors related to the recent column level partitioned checks executions for a requested time scale|
|<span class="no-wrap-code">[`get_column_profiling_errors`</span>](./errors.md#get_column_profiling_errors)|GET|Returns the errors related to the recent check executions for all column level data quality profiling checks on a column|
|<span class="no-wrap-code">[`get_table_monitoring_errors`</span>](./errors.md#get_table_monitoring_errors)|GET|Returns the errors related to the most recent table level monitoring executions for the monitoring at a requested time scale|
|<span class="no-wrap-code">[`get_table_partitioned_errors`</span>](./errors.md#get_table_partitioned_errors)|GET|Returns errors related to the recent table level partitioned checks executions for a requested time scale|
|<span class="no-wrap-code">[`get_table_profiling_errors`</span>](./errors.md#get_table_profiling_errors)|GET|Returns the errors related to the most recent check executions for all table level data quality profiling checks on a table|


## healthcheck
Health check operations for checking if the DQOps service is up and operational. Used for monitoring by load balancers.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`is_healthy`</span>](./healthcheck.md#is_healthy)|GET|Checks if the DQOps instance is healthy and operational. Returns a text &quot;OK&quot; and a HTTP status code 200 when the service is active and can accept jobs,  or returns a text &quot;UNAVAILABLE&quot; and a HTTP status code 503 when the service is still starting or is shutting down.|


## incidents
Data quality incidents controller that supports reading and updating data quality incidents, such as changing the incident status or assigning an external ticket number.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`find_connection_incident_stats`</span>](./incidents.md#find_connection_incident_stats)|GET|Returns a list of connection names with incident statistics - the count of recent open incidents.|
|<span class="no-wrap-code">[`find_recent_incidents_on_connection`</span>](./incidents.md#find_recent_incidents_on_connection)|GET|Returns a list of recent data quality incidents.|
|<span class="no-wrap-code">[`get_incident`</span>](./incidents.md#get_incident)|GET|Return a single data quality incident&#x27;s details.|
|<span class="no-wrap-code">[`get_incident_histogram`</span>](./incidents.md#get_incident_histogram)|GET|Generates histograms of data quality issues for each day, returning the number of data quality issues on that day. The other histograms are by a column name and by a check name.|
|<span class="no-wrap-code">[`get_incident_issues`</span>](./incidents.md#get_incident_issues)|GET|Return a paged list of failed data quality check results that are related to an incident.|
|<span class="no-wrap-code">[`set_incident_issue_url`</span>](./incidents.md#set_incident_issue_url)|POST|Changes the incident&#x27;s issueUrl to a new status.|
|<span class="no-wrap-code">[`set_incident_status`</span>](./incidents.md#set_incident_status)|POST|Changes the incident&#x27;s status to a new status.|


## jobs
Jobs management controller that supports starting new jobs, such as running selected data quality checks. Provides access to the job queue for incremental monitoring.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`cancel_job`</span>](./jobs.md#cancel_job)|DELETE|Cancels a running job|
|<span class="no-wrap-code">[`collect_statistics_on_data_groups`</span>](./jobs.md#collect_statistics_on_data_groups)|POST|Starts a new background job that will run selected data statistics collectors on tables, calculating separate metric for each data grouping|
|<span class="no-wrap-code">[`collect_statistics_on_table`</span>](./jobs.md#collect_statistics_on_table)|POST|Starts a new background job that will run selected data statistics collectors for the entire table|
|<span class="no-wrap-code">[`delete_stored_data`</span>](./jobs.md#delete_stored_data)|POST|Starts a new background job that will delete stored data about check results, sensor readouts etc.|
|<span class="no-wrap-code">[`get_all_jobs`</span>](./jobs.md#get_all_jobs)|GET|Retrieves a list of all queued and recently finished jobs.|
|<span class="no-wrap-code">[`get_job`</span>](./jobs.md#get_job)|GET|Retrieves the current status of a single job, identified by a job id.|
|<span class="no-wrap-code">[`get_job_changes_since`</span>](./jobs.md#get_job_changes_since)|GET|Retrieves an incremental list of job changes (new jobs or job status changes)|
|<span class="no-wrap-code">[`import_tables`</span>](./jobs.md#import_tables)|POST|Starts a new background job that will import selected tables.|
|<span class="no-wrap-code">[`is_cron_scheduler_running`</span>](./jobs.md#is_cron_scheduler_running)|GET|Checks if the DQOps internal CRON scheduler is running and processing jobs scheduled using cron expressions.|
|<span class="no-wrap-code">[`run_checks`</span>](./jobs.md#run_checks)|POST|Starts a new background job that will run selected data quality checks|
|<span class="no-wrap-code">[`start_cron_scheduler`</span>](./jobs.md#start_cron_scheduler)|POST|Starts the job scheduler that runs monitoring jobs that are scheduled by assigning cron expressions.|
|<span class="no-wrap-code">[`stop_cron_scheduler`</span>](./jobs.md#stop_cron_scheduler)|POST|Stops the job scheduler that runs monitoring jobs that are scheduled by assigning cron expressions.|
|<span class="no-wrap-code">[`synchronize_folders`</span>](./jobs.md#synchronize_folders)|POST|Starts multiple file synchronization jobs that will synchronize files from selected DQOps User home folders to the DQOps Cloud. The default synchronization mode is a full synchronization (upload local files, download new files from the cloud).|
|<span class="no-wrap-code">[`wait_for_job`</span>](./jobs.md#wait_for_job)|GET|Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.|
|<span class="no-wrap-code">[`wait_for_run_checks_job`</span>](./jobs.md#wait_for_run_checks_job)|GET|Waits for a job to finish. Returns the status of a finished job or a current state of a job that is still running, but the wait timeout elapsed.|


## log_shipping
Log shipping controller that accepts logs sent from a web application or external tools and aggregates them in the local DQOps instance logs.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`log_debug`</span>](./log_shipping.md#log_debug)|POST|Logs an information message in the server&#x27;s logs as a debug severity log entry.|
|<span class="no-wrap-code">[`log_error`</span>](./log_shipping.md#log_error)|POST|Logs an information message in the server&#x27;s logs as an error severity log entry.|
|<span class="no-wrap-code">[`log_info`</span>](./log_shipping.md#log_info)|POST|Logs an information message in the server&#x27;s logs as an info severity log entry.|
|<span class="no-wrap-code">[`log_warn`</span>](./log_shipping.md#log_warn)|POST|Logs an information message in the server&#x27;s logs as a warn severity log entry.|


## rules
Operations for managing custom data quality rule definitions in DQOps. The custom rules are stored in the DQOps user home folder.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`create_rule`</span>](./rules.md#create_rule)|POST|Creates (adds) a new custom rule given the rule definition.|
|<span class="no-wrap-code">[`delete_rule`</span>](./rules.md#delete_rule)|DELETE|Deletes a custom rule definition|
|<span class="no-wrap-code">[`get_all_rules`</span>](./rules.md#get_all_rules)|GET|Returns a flat list of all rules available in DQOps, both built-in rules and user defined or customized rules.|
|<span class="no-wrap-code">[`get_rule`</span>](./rules.md#get_rule)|GET|Returns a rule definition|
|<span class="no-wrap-code">[`get_rule_folder_tree`</span>](./rules.md#get_rule_folder_tree)|GET|Returns a tree of all rules available in DQOps, both built-in rules and user defined or customized rules.|
|<span class="no-wrap-code">[`update_rule`</span>](./rules.md#update_rule)|PUT|Updates an existing rule, making a custom rule definition if it is not present|


## schemas
Operations for listing imported schemas from monitored data sources. Also provides operations for activating and deactivating multiple checks at once.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`get_schema_monitoring_checks_model`</span>](./schemas.md#get_schema_monitoring_checks_model)|GET|Return a UI friendly model of configurations for data quality monitoring checks on a schema|
|<span class="no-wrap-code">[`get_schema_monitoring_checks_templates`</span>](./schemas.md#get_schema_monitoring_checks_templates)|GET|Return available data quality checks on a requested schema.|
|<span class="no-wrap-code">[`get_schema_partitioned_checks_model`</span>](./schemas.md#get_schema_partitioned_checks_model)|GET|Return a UI friendly model of configurations for data quality partitioned checks on a schema|
|<span class="no-wrap-code">[`get_schema_partitioned_checks_templates`</span>](./schemas.md#get_schema_partitioned_checks_templates)|GET|Return available data quality checks on a requested schema.|
|<span class="no-wrap-code">[`get_schema_profiling_checks_model`</span>](./schemas.md#get_schema_profiling_checks_model)|GET|Return a flat list of configurations for profiling checks on a schema|
|<span class="no-wrap-code">[`get_schema_profiling_checks_templates`</span>](./schemas.md#get_schema_profiling_checks_templates)|GET|Return available data quality checks on a requested schema.|
|<span class="no-wrap-code">[`get_schemas`</span>](./schemas.md#get_schemas)|GET|Returns a list of schemas inside a connection|


## sensor_readouts
Operations that are retrieving the data quality sensor readouts of executed checks on tables and columns.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`get_column_monitoring_sensor_readouts`</span>](./sensor_readouts.md#get_column_monitoring_sensor_readouts)|GET|Returns a complete view of the sensor readouts for recent column level monitoring executions for the monitoring at a requested time scale|
|<span class="no-wrap-code">[`get_column_partitioned_sensor_readouts`</span>](./sensor_readouts.md#get_column_partitioned_sensor_readouts)|GET|Returns a view of the sensor readouts for recent column level partitioned checks executions for a requested time scale|
|<span class="no-wrap-code">[`get_column_profiling_sensor_readouts`</span>](./sensor_readouts.md#get_column_profiling_sensor_readouts)|GET|Returns sensor results of the recent check executions for all column level data quality profiling checks on a column|
|<span class="no-wrap-code">[`get_table_monitoring_sensor_readouts`</span>](./sensor_readouts.md#get_table_monitoring_sensor_readouts)|GET|Returns the complete results of the most recent table level monitoring executions for the monitoring at a requested time scale|
|<span class="no-wrap-code">[`get_table_partitioned_sensor_readouts`</span>](./sensor_readouts.md#get_table_partitioned_sensor_readouts)|GET|Returns a complete view of sensor readouts for recent table level partitioned checks executions for a requested time scale|
|<span class="no-wrap-code">[`get_table_profiling_sensor_readouts`</span>](./sensor_readouts.md#get_table_profiling_sensor_readouts)|GET|Returns the complete results of the most recent check executions for all table level data quality profiling checks on a table|


## sensors
Operations for managing custom data quality sensor definitions in DQOps. The custom sensors are stored in the DQOps user home folder.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`create_sensor`</span>](./sensors.md#create_sensor)|POST|Creates (adds) a new sensor given sensor information.|
|<span class="no-wrap-code">[`delete_sensor`</span>](./sensors.md#delete_sensor)|DELETE|Deletes a custom sensor definition|
|<span class="no-wrap-code">[`get_all_sensors`</span>](./sensors.md#get_all_sensors)|GET|Returns a flat list of all sensors available in DQOps, both built-in sensors and user defined or customized sensors.|
|<span class="no-wrap-code">[`get_sensor`</span>](./sensors.md#get_sensor)|GET|Returns a sensor model|
|<span class="no-wrap-code">[`get_sensor_folder_tree`</span>](./sensors.md#get_sensor_folder_tree)|GET|Returns a tree of all sensors available in DQOps, both built-in sensors and user defined or customized sensors.|
|<span class="no-wrap-code">[`update_sensor`</span>](./sensors.md#update_sensor)|PUT|Updates an existing sensor, making a custom sensor definition if it is not present. 
Removes sensor if custom definition is same as Dqo Home sensor|


## shared_credentials
Operations for managing shared credentials in DQOps. Credentials that are stored in the shared .credentials folder in the DQOps user&#x27;s home folder.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`create_shared_credential`</span>](./shared_credentials.md#create_shared_credential)|POST|Creates (adds) a new shared credential, which creates a file in the DQOps user&#x27;s home .credentials/ folder named as the credential and with the content that is provided in this call.|
|<span class="no-wrap-code">[`delete_shared_credential`</span>](./shared_credentials.md#delete_shared_credential)|DELETE|Deletes a shared credential file from the DQOps user&#x27;s home .credentials/ folder.|
|<span class="no-wrap-code">[`download_shared_credential`</span>](./shared_credentials.md#download_shared_credential)|GET|Downloads a shared credential&#x27;s file|
|<span class="no-wrap-code">[`get_all_shared_credentials`</span>](./shared_credentials.md#get_all_shared_credentials)|GET|Returns a list of all shared credentials that are present in the DQOps user&#x27;s home .credentials/ folder.|
|<span class="no-wrap-code">[`get_shared_credential`</span>](./shared_credentials.md#get_shared_credential)|GET|Returns a shared credential content|
|<span class="no-wrap-code">[`update_shared_credential`</span>](./shared_credentials.md#update_shared_credential)|PUT|Updates an existing shared credential, replacing the credential&#x27;s file content.|


## table_comparison_results
Operations that returns the results of the most recent table comparison that was performed between the compared table and the reference table (the source of truth).

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`get_table_comparison_monitoring_results`</span>](./table_comparison_results.md#get_table_comparison_monitoring_results)|GET|Retrieves the results of the most table comparison performed using the monitoring comparison checks.|
|<span class="no-wrap-code">[`get_table_comparison_partitioned_results`</span>](./table_comparison_results.md#get_table_comparison_partitioned_results)|GET|Retrieves the results of the most table comparison performed using the partitioned comparison checks, comparing days or months of data.|
|<span class="no-wrap-code">[`get_table_comparison_profiling_results`</span>](./table_comparison_results.md#get_table_comparison_profiling_results)|GET|Retrieves the results of the most table comparison performed using the profiling checks comparison checks.|


## table_comparisons
Operations for managing the configurations of table comparisons between tables on the same or different data sources

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`create_table_comparison_configuration`</span>](./table_comparisons.md#create_table_comparison_configuration)|POST|Creates a new table comparison configuration added to the compared table|
|<span class="no-wrap-code">[`create_table_comparison_monitoring_daily`</span>](./table_comparisons.md#create_table_comparison_monitoring_daily)|POST|Creates a table comparison configuration using daily monitoring checks|
|<span class="no-wrap-code">[`create_table_comparison_monitoring_monthly`</span>](./table_comparisons.md#create_table_comparison_monitoring_monthly)|POST|Creates a table comparison configuration using monthly monitoring checks|
|<span class="no-wrap-code">[`create_table_comparison_partitioned_daily`</span>](./table_comparisons.md#create_table_comparison_partitioned_daily)|POST|Creates a table comparison configuration using daily partitioned checks|
|<span class="no-wrap-code">[`create_table_comparison_partitioned_monthly`</span>](./table_comparisons.md#create_table_comparison_partitioned_monthly)|POST|Creates a table comparison configuration using monthly partitioned checks|
|<span class="no-wrap-code">[`create_table_comparison_profiling`</span>](./table_comparisons.md#create_table_comparison_profiling)|POST|Creates a table comparison configuration using profiling checks|
|<span class="no-wrap-code">[`delete_table_comparison_configuration`</span>](./table_comparisons.md#delete_table_comparison_configuration)|DELETE|Deletes a table comparison configuration from a compared table|
|<span class="no-wrap-code">[`get_table_comparison_configuration`</span>](./table_comparisons.md#get_table_comparison_configuration)|GET|Returns a model of the table comparison configuration|
|<span class="no-wrap-code">[`get_table_comparison_configurations`</span>](./table_comparisons.md#get_table_comparison_configurations)|GET|Returns the list of table comparison configurations on a compared table|
|<span class="no-wrap-code">[`get_table_comparison_monitoring_daily`</span>](./table_comparisons.md#get_table_comparison_monitoring_daily)|GET|Returns a model of the table comparison using daily monitoring checks (comparison once a day)|
|<span class="no-wrap-code">[`get_table_comparison_monitoring_monthly`</span>](./table_comparisons.md#get_table_comparison_monitoring_monthly)|GET|Returns a model of the table comparison using monthly monitoring checks (comparison once a month)|
|<span class="no-wrap-code">[`get_table_comparison_partitioned_daily`</span>](./table_comparisons.md#get_table_comparison_partitioned_daily)|GET|Returns a model of the table comparison using daily partition checks (comparing day to day)|
|<span class="no-wrap-code">[`get_table_comparison_partitioned_monthly`</span>](./table_comparisons.md#get_table_comparison_partitioned_monthly)|GET|Returns a model of the table comparison using monthly partition checks (comparing month to month)|
|<span class="no-wrap-code">[`get_table_comparison_profiling`</span>](./table_comparisons.md#get_table_comparison_profiling)|GET|Returns a model of the table comparison using profiling checks (comparison at any time)|
|<span class="no-wrap-code">[`update_table_comparison_configuration`</span>](./table_comparisons.md#update_table_comparison_configuration)|PUT|Updates a table configuration configuration|
|<span class="no-wrap-code">[`update_table_comparison_monitoring_daily`</span>](./table_comparisons.md#update_table_comparison_monitoring_daily)|PUT|Updates a table comparison checks monitoring daily|
|<span class="no-wrap-code">[`update_table_comparison_monitoring_monthly`</span>](./table_comparisons.md#update_table_comparison_monitoring_monthly)|PUT|Updates a table comparison checks monitoring monthly|
|<span class="no-wrap-code">[`update_table_comparison_partitioned_daily`</span>](./table_comparisons.md#update_table_comparison_partitioned_daily)|PUT|Updates a table comparison checks partitioned daily (comparing day to day)|
|<span class="no-wrap-code">[`update_table_comparison_partitioned_monthly`</span>](./table_comparisons.md#update_table_comparison_partitioned_monthly)|PUT|Updates a table comparison checks partitioned monthly (comparing month to month)|
|<span class="no-wrap-code">[`update_table_comparison_profiling`</span>](./table_comparisons.md#update_table_comparison_profiling)|PUT|Updates a table comparison profiling checks|


## tables
Operations related to manage the metadata of imported tables, and managing the configuration of table-level data quality checks.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`create_table`</span>](./tables.md#create_table)|POST|Creates a new table (adds a table metadata)|
|<span class="no-wrap-code">[`delete_table`</span>](./tables.md#delete_table)|DELETE|Deletes a table|
|<span class="no-wrap-code">[`get_table`</span>](./tables.md#get_table)|GET|Return the table specification|
|<span class="no-wrap-code">[`get_table_basic`</span>](./tables.md#get_table_basic)|GET|Return the basic table information|
|<span class="no-wrap-code">[`get_table_columns_monitoring_checks_model`</span>](./tables.md#get_table_columns_monitoring_checks_model)|GET|Return a UI friendly model of configurations for column-level data quality monitoring checks on a table|
|<span class="no-wrap-code">[`get_table_columns_partitioned_checks_model`</span>](./tables.md#get_table_columns_partitioned_checks_model)|GET|Return a UI friendly model of configurations for column-level data quality partitioned checks on a table|
|<span class="no-wrap-code">[`get_table_columns_profiling_checks_model`</span>](./tables.md#get_table_columns_profiling_checks_model)|GET|Return a UI friendly model of configurations for column-level data quality profiling checks on a table|
|<span class="no-wrap-code">[`get_table_comments`</span>](./tables.md#get_table_comments)|GET|Return the list of comments added to a table|
|<span class="no-wrap-code">[`get_table_daily_monitoring_checks`</span>](./tables.md#get_table_daily_monitoring_checks)|GET|Return the configuration of daily table level data quality monitoring on a table|
|<span class="no-wrap-code">[`get_table_daily_partitioned_checks`</span>](./tables.md#get_table_daily_partitioned_checks)|GET|Return the configuration of daily table level data quality partitioned checks on a table|
|<span class="no-wrap-code">[`get_table_default_grouping_configuration`</span>](./tables.md#get_table_default_grouping_configuration)|GET|Return the default data grouping configuration for a table.|
|<span class="no-wrap-code">[`get_table_incident_grouping`</span>](./tables.md#get_table_incident_grouping)|GET|Return the configuration of incident grouping on a table|
|<span class="no-wrap-code">[`get_table_labels`</span>](./tables.md#get_table_labels)|GET|Return the list of labels assigned to a table|
|<span class="no-wrap-code">[`get_table_monitoring_checks_basic_model`</span>](./tables.md#get_table_monitoring_checks_basic_model)|GET|Return a simplistic UI friendly model of table level data quality monitoring on a table for a given time scale|
|<span class="no-wrap-code">[`get_table_monitoring_checks_model`</span>](./tables.md#get_table_monitoring_checks_model)|GET|Return a UI friendly model of configurations for table level data quality monitoring on a table for a given time scale|
|<span class="no-wrap-code">[`get_table_monitoring_checks_model_filter`</span>](./tables.md#get_table_monitoring_checks_model_filter)|GET|Return a UI friendly model of configurations for table level data quality monitoring on a table for a given time scale, filtered by category and check name.|
|<span class="no-wrap-code">[`get_table_monitoring_checks_monthly`</span>](./tables.md#get_table_monitoring_checks_monthly)|GET|Return the configuration of monthly table level data quality monitoring on a table|
|<span class="no-wrap-code">[`get_table_monitoring_checks_templates`</span>](./tables.md#get_table_monitoring_checks_templates)|GET|Return available data quality checks on a requested table.|
|<span class="no-wrap-code">[`get_table_partitioned_checks_basic_model`</span>](./tables.md#get_table_partitioned_checks_basic_model)|GET|Return a simplistic UI friendly model of table level data quality partitioned checks on a table for a given time scale|
|<span class="no-wrap-code">[`get_table_partitioned_checks_model`</span>](./tables.md#get_table_partitioned_checks_model)|GET|Return a UI friendly model of configurations for table level data quality partitioned checks on a table for a given time scale|
|<span class="no-wrap-code">[`get_table_partitioned_checks_model_filter`</span>](./tables.md#get_table_partitioned_checks_model_filter)|GET|Return a UI friendly model of configurations for table level data quality partitioned checks on a table for a given time scale, filtered by category and check name.|
|<span class="no-wrap-code">[`get_table_partitioned_checks_monthly`</span>](./tables.md#get_table_partitioned_checks_monthly)|GET|Return the configuration of monthly table level data quality partitioned checks on a table|
|<span class="no-wrap-code">[`get_table_partitioned_checks_templates`</span>](./tables.md#get_table_partitioned_checks_templates)|GET|Return available data quality checks on a requested table.|
|<span class="no-wrap-code">[`get_table_partitioning`</span>](./tables.md#get_table_partitioning)|GET|Return the table partitioning information|
|<span class="no-wrap-code">[`get_table_profiling_checks`</span>](./tables.md#get_table_profiling_checks)|GET|Return the configuration of table level data quality checks on a table|
|<span class="no-wrap-code">[`get_table_profiling_checks_basic_model`</span>](./tables.md#get_table_profiling_checks_basic_model)|GET|Return a simplistic UI friendly model of all table level data quality profiling checks on a table|
|<span class="no-wrap-code">[`get_table_profiling_checks_model`</span>](./tables.md#get_table_profiling_checks_model)|GET|Return a UI friendly model of configurations for all table level data quality profiling checks on a table|
|<span class="no-wrap-code">[`get_table_profiling_checks_model_filter`</span>](./tables.md#get_table_profiling_checks_model_filter)|GET|Return a UI friendly model of configurations for all table level data quality profiling checks on a table passing a filter|
|<span class="no-wrap-code">[`get_table_profiling_checks_templates`</span>](./tables.md#get_table_profiling_checks_templates)|GET|Return available data quality checks on a requested table.|
|<span class="no-wrap-code">[`get_table_scheduling_group_override`</span>](./tables.md#get_table_scheduling_group_override)|GET|Return the schedule override configuration for a table|
|<span class="no-wrap-code">[`get_table_statistics`</span>](./tables.md#get_table_statistics)|GET|Returns a list of the profiler (statistics) metrics on a chosen table captured during the most recent statistics collection.|
|<span class="no-wrap-code">[`get_tables`</span>](./tables.md#get_tables)|GET|Returns a list of tables inside a connection/schema|
|<span class="no-wrap-code">[`update_table`</span>](./tables.md#update_table)|PUT|Updates an existing table specification, changing all the fields|
|<span class="no-wrap-code">[`update_table_basic`</span>](./tables.md#update_table_basic)|PUT|Updates the basic field of an existing table, changing only the most important fields.|
|<span class="no-wrap-code">[`update_table_comments`</span>](./tables.md#update_table_comments)|PUT|Updates the list of comments on an existing table.|
|<span class="no-wrap-code">[`update_table_daily_monitoring_checks`</span>](./tables.md#update_table_daily_monitoring_checks)|PUT|Updates the list of daily table level data quality monitoring on an existing table.|
|<span class="no-wrap-code">[`update_table_default_grouping_configuration`</span>](./tables.md#update_table_default_grouping_configuration)|PUT|Updates the default data grouping configuration at a table level.|
|<span class="no-wrap-code">[`update_table_incident_grouping`</span>](./tables.md#update_table_incident_grouping)|PUT|Updates the configuration of incident grouping on a table.|
|<span class="no-wrap-code">[`update_table_labels`</span>](./tables.md#update_table_labels)|PUT|Updates the list of assigned labels of an existing table.|
|<span class="no-wrap-code">[`update_table_monitoring_checks_model`</span>](./tables.md#update_table_monitoring_checks_model)|PUT|Updates the data quality monitoring from a model that contains a patch with changes.|
|<span class="no-wrap-code">[`update_table_monitoring_checks_monthly`</span>](./tables.md#update_table_monitoring_checks_monthly)|PUT|Updates the list of monthly table level data quality monitoring on an existing table.|
|<span class="no-wrap-code">[`update_table_partitioned_checks_daily`</span>](./tables.md#update_table_partitioned_checks_daily)|PUT|Updates the list of daily table level data quality partitioned checks on an existing table.|
|<span class="no-wrap-code">[`update_table_partitioned_checks_model`</span>](./tables.md#update_table_partitioned_checks_model)|PUT|Updates the data quality partitioned checks from a model that contains a patch with changes.|
|<span class="no-wrap-code">[`update_table_partitioned_checks_monthly`</span>](./tables.md#update_table_partitioned_checks_monthly)|PUT|Updates the list of monthly table level data quality partitioned checks on an existing table.|
|<span class="no-wrap-code">[`update_table_partitioning`</span>](./tables.md#update_table_partitioning)|PUT|Updates the table partitioning configuration of an existing table.|
|<span class="no-wrap-code">[`update_table_profiling_checks`</span>](./tables.md#update_table_profiling_checks)|PUT|Updates the list of table level data quality profiling checks on an existing table.|
|<span class="no-wrap-code">[`update_table_profiling_checks_model`</span>](./tables.md#update_table_profiling_checks_model)|PUT|Updates the data quality profiling checks from a model that contains a patch with changes.|
|<span class="no-wrap-code">[`update_table_scheduling_group_override`</span>](./tables.md#update_table_scheduling_group_override)|PUT|Updates the overridden schedule configuration of an existing table for a named schedule group (named schedule for checks using the same time scale).|


## timezones
Operations for returning time zone names and codes supported by DQOps.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`get_available_zone_ids`</span>](./timezones.md#get_available_zone_ids)|GET|Returns a list of available time zone ids|


## users
Operations for managing access for DQOps users in a multi-user installations. User management is supported in the TEAM and ENTERPRISE licences.

|&nbsp;Operation&nbsp;name&nbsp;|&nbsp;HTTP&nbsp;call&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|----------------|------|---------------------------------|
|<span class="no-wrap-code">[`change_caller_password`</span>](./users.md#change_caller_password)|PUT|Changes the password of the calling user. When the user is identified by the DQOps local API key, it is the user whose email is stored in the DQOps API Key.|
|<span class="no-wrap-code">[`change_user_password`</span>](./users.md#change_user_password)|PUT|Changes the password of a user identified by the email.|
|<span class="no-wrap-code">[`create_user`</span>](./users.md#create_user)|POST|Creates (adds) a new user to a multi-user account.|
|<span class="no-wrap-code">[`delete_user`</span>](./users.md#delete_user)|DELETE|Deletes a user from a multi-user account.|
|<span class="no-wrap-code">[`get_all_users`</span>](./users.md#get_all_users)|GET|Returns a list of all users.|
|<span class="no-wrap-code">[`get_user`</span>](./users.md#get_user)|GET|Returns the user model that describes the role of a user identified by an email|
|<span class="no-wrap-code">[`update_user`</span>](./users.md#update_user)|PUT|Updates a user in a multi-user account. The user&#x27;s email cannot be changed.|


