Manages connections to monitored data sources  


___  
## bulk_disable_connection_checks  
Disables a named check on this connection in the locations specified by filter  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/bulk_disable_connection_checks.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/checks/{checkName}/bulkdisable  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|check_name|Check name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Check search filters and table/column selectors.|[BulkCheckDisableParameters](../../models/connections/#BulkCheckDisableParameters)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/checks/sample_check/bulkdisable^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"check_search_filters\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"column\":\"sample_column\",\"columnDataType\":\"string\"}}"

    ```


___  
## bulk_enable_connection_checks  
Enables a named check on this connection in the locations specified by filter  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/bulk_enable_connection_checks.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/checks/{checkName}/bulkenable  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|check_name|Check name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Check search filters and rules configuration|[AllChecksPatchParameters](../../models/connections/#AllChecksPatchParameters)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/checks/sample_check/bulkenable^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"check_search_filters\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"column\":\"sample_column\",\"columnDataType\":\"string\"},\"check_model_patch\":{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false},\"override_conflicts\":true}"

    ```


___  
## create_connection  
Creates a new connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/create_connection.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Connection specification|[ConnectionSpec](../../../reference/yaml/ConnectionYaml/#connectionspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"provider_type\":\"postgresql\",\"postgresql\":{\"host\":\"localhost\",\"port\":\"5432\",\"database\":\"db\",\"user\":\"PASSWD\",\"sslmode\":\"disable\"},\"parallel_jobs_limit\":4,\"incident_grouping\":{\"grouping_level\":\"table_dimension_category\",\"minimum_severity\":\"warning\",\"max_incident_length_days\":60,\"mute_for_days\":60}}"

    ```


___  
## create_connection_basic  
Creates a new connection given the basic information.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/create_connection_basic.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}/basic  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Basic connection model|[ConnectionModel](../../models/#ConnectionModel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/basic^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection_name\":\"sample_connection\",\"parallel_runs_limit\":4,\"provider_type\":\"postgresql\",\"postgresql\":{\"host\":\"localhost\",\"port\":\"5432\",\"database\":\"db\",\"user\":\"PASSWD\",\"sslmode\":\"disable\"},\"run_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true},\"run_profiling_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"profiling\"},\"run_monitoring_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"monitoring\"},\"run_partition_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"partitioned\"},\"collect_statistics_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"columnNames\":[]},\"data_clean_job_template\":{\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true},\"can_edit\":false,\"can_collect_statistics\":true,\"can_run_checks\":true,\"can_delete_data\":true}"

    ```


___  
## delete_connection  
Deletes a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/delete_connection.py)
  

**DELETE**
```
http://localhost:8888/api/connections/{connectionName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_queue_job_id](../../models/#DqoQueueJobId)||[DqoQueueJobId](../../models/#DqoQueueJobId)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl -X DELETE http://localhost:8888/api/connections/sample_connection^
		-H "Accept: application/json"

    ```


___  
## get_all_connections  
Returns a list of connections (data sources)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_all_connections.py)
  

**GET**
```
http://localhost:8888/api/connections  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_model||List[[ConnectionModel](../../models/#ConnectionModel)]|








**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections^
		-H "Accept: application/json"

    ```


___  
## get_connection  
Return the full details of a connection given the connection name  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[connection_specification_model](../../models/connections/#ConnectionSpecificationModel)||[ConnectionSpecificationModel](../../models/connections/#ConnectionSpecificationModel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection^
		-H "Accept: application/json"

    ```


___  
## get_connection_basic  
Return the basic details of a connection given the connection name  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_basic.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/basic  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[connection_model](../../models/#ConnectionModel)||[ConnectionModel](../../models/#ConnectionModel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/basic^
		-H "Accept: application/json"

    ```


___  
## get_connection_comments  
Return the comments for a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_comments.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/comments  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|comment_spec||List[[CommentSpec](../../models/#CommentSpec)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/comments^
		-H "Accept: application/json"

    ```


___  
## get_connection_common_columns  
Finds common column names that are used on one or more tables. The list of columns is sorted in descending order by column name.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_common_columns.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/commoncolumns  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|common_column_model||List[[CommonColumnModel](../../models/connections/#CommonColumnModel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/commoncolumns^
		-H "Accept: application/json"

    ```


___  
## get_connection_default_grouping_configuration  
Return the default data grouping configuration for a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_default_grouping_configuration.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/defaultgroupingconfiguration  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[data_grouping_configuration_spec](../../../reference/yaml/ConnectionYaml/#datagroupingconfigurationspec)||[DataGroupingConfigurationSpec](../../../reference/yaml/ConnectionYaml/#datagroupingconfigurationspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/defaultgroupingconfiguration^
		-H "Accept: application/json"

    ```


___  
## get_connection_incident_grouping  
Retrieves the configuration of data quality incident grouping and incident notifications  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_incident_grouping.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/incidentgrouping  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[connection_incident_grouping_spec](../../../reference/yaml/ConnectionYaml/#connectionincidentgroupingspec)||[ConnectionIncidentGroupingSpec](../../../reference/yaml/ConnectionYaml/#connectionincidentgroupingspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/incidentgrouping^
		-H "Accept: application/json"

    ```


___  
## get_connection_labels  
Return the labels for a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_labels.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/labels  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|string||string_list|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/labels^
		-H "Accept: application/json"

    ```


___  
## get_connection_scheduling_group  
Return the schedule for a connection for a scheduling group  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/get_connection_scheduling_group.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schedules/{schedulingGroup}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[monitoring_schedule_spec](../../models/#MonitoringScheduleSpec)||[MonitoringScheduleSpec](../../models/#MonitoringScheduleSpec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|[scheduling_group](../../models/#CheckRunScheduleGroup)|Check scheduling group (named schedule)|[CheckRunScheduleGroup](../../models/#CheckRunScheduleGroup)|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schedules/"partitioned_daily"^
		-H "Accept: application/json"

    ```


___  
## update_connection  
Updates an existing connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Connection specification|[ConnectionSpec](../../../reference/yaml/ConnectionYaml/#connectionspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"provider_type\":\"postgresql\",\"postgresql\":{\"host\":\"localhost\",\"port\":\"5432\",\"database\":\"db\",\"user\":\"PASSWD\",\"sslmode\":\"disable\"},\"parallel_jobs_limit\":4,\"incident_grouping\":{\"grouping_level\":\"table_dimension_category\",\"minimum_severity\":\"warning\",\"max_incident_length_days\":60,\"mute_for_days\":60}}"

    ```


___  
## update_connection_basic  
Updates the basic information of a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_basic.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/basic  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Connection basic details|[ConnectionModel](../../models/#ConnectionModel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/basic^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection_name\":\"sample_connection\",\"parallel_runs_limit\":4,\"provider_type\":\"postgresql\",\"postgresql\":{\"host\":\"localhost\",\"port\":\"5432\",\"database\":\"db\",\"user\":\"PASSWD\",\"sslmode\":\"disable\"},\"run_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true},\"run_profiling_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"profiling\"},\"run_monitoring_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"monitoring\"},\"run_partition_checks_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"checkType\":\"partitioned\"},\"collect_statistics_job_template\":{\"connection\":\"sample_connection\",\"enabled\":true,\"columnNames\":[]},\"data_clean_job_template\":{\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true},\"can_edit\":false,\"can_collect_statistics\":true,\"can_run_checks\":true,\"can_delete_data\":true}"

    ```


___  
## update_connection_comments  
Updates (replaces) the list of comments of a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_comments.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/comments  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|List of comments|List[[CommentSpec](../../models/#CommentSpec)]| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/comments^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"[]"

    ```


___  
## update_connection_default_grouping_configuration  
Updates the default data grouping connection of a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_default_grouping_configuration.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/defaultgroupingconfiguration  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Default data grouping configuration to be assigned to a connection|[DataGroupingConfigurationSpec](../../../reference/yaml/ConnectionYaml/#datagroupingconfigurationspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/defaultgroupingconfiguration^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"level_3\":{\"source\":\"column_value\",\"column\":\"sample_column\"}}"

    ```


___  
## update_connection_incident_grouping  
Updates (replaces) configuration of incident grouping and notifications on a connection (data source) level.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_incident_grouping.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/incidentgrouping  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Incident grouping and notification configuration|[ConnectionIncidentGroupingSpec](../../../reference/yaml/ConnectionYaml/#connectionincidentgroupingspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/incidentgrouping^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"grouping_level\":\"table_dimension\",\"minimum_severity\":\"warning\",\"divide_by_data_groups\":true,\"max_incident_length_days\":60,\"mute_for_days\":60,\"webhooks\":{\"incident_opened_webhook_url\":\"https://sample_url.com/opened\",\"incident_acknowledged_webhook_url\":\"https://sample_url.com/acknowledged\",\"incident_resolved_webhook_url\":\"https://sample_url.com/resolved\",\"incident_muted_webhook_url\":\"https://sample_url.com/muted\"}}"

    ```


___  
## update_connection_labels  
Updates the list of labels of a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_labels.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/labels  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|List of labels|string_list| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/labels^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"[]"

    ```


___  
## update_connection_scheduling_group  
Updates the schedule of a connection for a scheduling group (named schedule for checks with a similar time series configuration)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/connections/update_connection_scheduling_group.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schedules/{schedulingGroup}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|[scheduling_group](../../models/#CheckRunScheduleGroup)|Check scheduling group (named schedule)|[CheckRunScheduleGroup](../../models/#CheckRunScheduleGroup)|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Monitoring schedule definition to store|[MonitoringScheduleSpec](../../models/#MonitoringScheduleSpec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schedules/"partitioned_daily"^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"cron_expression\":\"0 12 1 * *\"}"

    ```


