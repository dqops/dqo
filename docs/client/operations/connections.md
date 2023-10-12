
## bulk_disable_connection_checks  
Disables a named check on this connection in the locations specified by filter  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/bulk_disable_connection_checks.py)
  

**PUT**
```
api/connections/{connectionName}/checks/{checkName}/bulkdisable  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Check search filters and table/column selectors.|[BulkCheckDisableParameters](\docs\client\operations\connections\#bulkcheckdisableparameters)|false|


___  

## bulk_enable_connection_checks  
Enables a named check on this connection in the locations specified by filter  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/bulk_enable_connection_checks.py)
  

**PUT**
```
api/connections/{connectionName}/checks/{checkName}/bulkenable  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Check search filters and rules configuration|[AllChecksPatchParameters](\docs\client\operations\connections\#allcheckspatchparameters)|false|


___  

## create_connection  
Creates a new connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/create_connection.py)
  

**POST**
```
api/connections/{connectionName}  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Connection specification|[ConnectionSpec](\docs\client\operations\connections\#connectionspec)|false|


___  

## create_connection_basic  
Creates a new connection given the basic information.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/create_connection_basic.py)
  

**POST**
```
api/connections/{connectionName}/basic  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Basic connection model|[ConnectionModel](\docs\client\models\#connectionmodel)|false|


___  

## delete_connection  
Deletes a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/delete_connection.py)
  

**DELETE**
```
api/connections/{connectionName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_queue_job_id](\docs\client\models\#dqoqueuejobid)||[DqoQueueJobId](\docs\client\models\#dqoqueuejobid)|






___  

## get_all_connections  
Returns a list of connections (data sources)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_all_connections.py)
  

**GET**
```
api/connections  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[connection_model](\docs\client\models\#connectionmodel)||[ConnectionModel](\docs\client\models\#connectionmodel)|






___  

## get_connection  
Return the full details of a connection given the connection name  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection.py)
  

**GET**
```
api/connections/{connectionName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[connection_specification_model](\docs\client\operations\connections\#connectionspecificationmodel)||[ConnectionSpecificationModel](\docs\client\operations\connections\#connectionspecificationmodel)|






___  

## get_connection_basic  
Return the basic details of a connection given the connection name  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_basic.py)
  

**GET**
```
api/connections/{connectionName}/basic  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[connection_model](\docs\client\models\#connectionmodel)||[ConnectionModel](\docs\client\models\#connectionmodel)|






___  

## get_connection_comments  
Return the comments for a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_comments.py)
  

**GET**
```
api/connections/{connectionName}/comments  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[comment_spec](\docs\client\models\#commentspec)||[CommentSpec](\docs\client\models\#commentspec)|






___  

## get_connection_common_columns  
Finds common column names that are used on one or more tables. The list of columns is sorted in descending order by column name.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_common_columns.py)
  

**GET**
```
api/connections/{connectionName}/commoncolumns  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[common_column_model](\docs\client\operations\connections\#commoncolumnmodel)||[CommonColumnModel](\docs\client\operations\connections\#commoncolumnmodel)|






___  

## get_connection_default_grouping_configuration  
Return the default data grouping configuration for a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_default_grouping_configuration.py)
  

**GET**
```
api/connections/{connectionName}/defaultgroupingconfiguration  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[data_grouping_configuration_spec](\docs\client\models\#datagroupingconfigurationspec)||[DataGroupingConfigurationSpec](\docs\client\models\#datagroupingconfigurationspec)|






___  

## get_connection_incident_grouping  
Retrieves the configuration of data quality incident grouping and incident notifications  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_incident_grouping.py)
  

**GET**
```
api/connections/{connectionName}/incidentgrouping  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[connection_incident_grouping_spec](\docs\client\operations\connections\#connectionincidentgroupingspec)||[ConnectionIncidentGroupingSpec](\docs\client\operations\connections\#connectionincidentgroupingspec)|






___  

## get_connection_labels  
Return the labels for a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_labels.py)
  

**GET**
```
api/connections/{connectionName}/labels  
```





___  

## get_connection_scheduling_group  
Return the schedule for a connection for a scheduling group  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_scheduling_group.py)
  

**GET**
```
api/connections/{connectionName}/schedules/{schedulingGroup}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[monitoring_schedule_spec](\docs\client\models\#monitoringschedulespec)||[MonitoringScheduleSpec](\docs\client\models\#monitoringschedulespec)|






___  

## update_connection  
Updates an existing connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection.py)
  

**PUT**
```
api/connections/{connectionName}  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Connection specification|[ConnectionSpec](\docs\client\operations\connections\#connectionspec)|false|


___  

## update_connection_basic  
Updates the basic information of a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_basic.py)
  

**PUT**
```
api/connections/{connectionName}/basic  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Connection basic details|[ConnectionModel](\docs\client\models\#connectionmodel)|false|


___  

## update_connection_comments  
Updates (replaces) the list of comments of a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_comments.py)
  

**PUT**
```
api/connections/{connectionName}/comments  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|List of comments|[CommentSpec](\docs\client\models\#commentspec)|false|


___  

## update_connection_default_grouping_configuration  
Updates the default data grouping connection of a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_default_grouping_configuration.py)
  

**PUT**
```
api/connections/{connectionName}/defaultgroupingconfiguration  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Default data grouping configuration to be assigned to a connection|[DataGroupingConfigurationSpec](\docs\client\models\#datagroupingconfigurationspec)|false|


___  

## update_connection_incident_grouping  
Updates (replaces) configuration of incident grouping and notifications on a connection (data source) level.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_incident_grouping.py)
  

**PUT**
```
api/connections/{connectionName}/incidentgrouping  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Incident grouping and notification configuration|[ConnectionIncidentGroupingSpec](\docs\client\operations\connections\#connectionincidentgroupingspec)|false|


___  

## update_connection_labels  
Updates the list of labels of a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_labels.py)
  

**PUT**
```
api/connections/{connectionName}/labels  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|List of labels|[]()|false|


___  

## update_connection_scheduling_group  
Updates the schedule of a connection for a scheduling group (named schedule for checks with a similar time series configuration)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_scheduling_group.py)
  

**PUT**
```
api/connections/{connectionName}/schedules/{schedulingGroup}  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Monitoring schedule definition to store|[MonitoringScheduleSpec](\docs\client\models\#monitoringschedulespec)|false|


___  

___  

## BulkCheckDisableParameters  
  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[check_search_filters](\docs\client\models\#checksearchfilters)|Filters addressing basic tree search parameters. These filters takes precedence over other selectors.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)| | | |

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

## CommentsListSpec  
List of comments.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|mod_count||integer| | | |

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
|[incident_grouping](\docs\client\operations\connections\#connectionincidentgroupingspec)|Configuration of data quality incident grouping. Configures how failed data quality checks are grouped into data quality incidents.|[ConnectionIncidentGroupingSpec](\docs\client\operations\connections\#connectionincidentgroupingspec)| | | |
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
|[spec](\docs\client\operations\connections\#connectionspec)|Full connection specification, including all nested objects (but not a list of tables).|[ConnectionSpec](\docs\client\operations\connections\#connectionspec)| | | |
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean| | | |

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
|[comments](\docs\client\operations\connections\#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](\docs\client\operations\connections\#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled checks are executed. The sensor should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|configured|True if the data quality check is configured (not null). When saving the data quality check configuration, set the flag to true for storing the check.|boolean| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |
|[run_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to start the job.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)| | | |
|[data_clean_job_template](\docs\client\operations\jobs\#deletestoreddataqueuejobparameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this check.|[DeleteStoredDataQueueJobParameters](\docs\client\operations\jobs\#deletestoreddataqueuejobparameters)| | | |
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

## CommonColumnModel  
Dictionary model used for combo boxes to select a column. Returns a column name that exists in any table within a connection (source)
 and a count of the column occurrence. It is used to find the most common columns.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|column_name|Column name.|string| | | |
|tables_count|Count of tables that are have a column with this name.|integer| | | |

___  

