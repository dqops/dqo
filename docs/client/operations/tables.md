Manages tables inside a connection/schema  


___  
## create_table  
Creates a new table (adds a table metadata)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/create_table.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table specification|[TableSpec](../../../reference/yaml/TableYaml/#tablespec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"timestamp_columns\":{\"event_timestamp_column\":\"col1\",\"ingestion_timestamp_column\":\"col2\",\"partition_by_column\":\"col3\"},\"incremental_time_window\":{\"daily_partitioning_recent_days\":7,\"monthly_partitioning_recent_months\":1},\"profiling_checks\":{\"volume\":{\"profile_row_count\":{\"error\":{\"min_count\":1}}}},\"columns\":{}}"

    ```




___  
## delete_table  
Deletes a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/delete_table.py)
  

**DELETE**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_queue_job_id](../../models/Common/#dqoqueuejobid)||[DqoQueueJobId](../../models/Common/#dqoqueuejobid)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl -X DELETE http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "jobId" : 10832,
	  "createdAt" : "2007-10-11T13:42:00Z"
	}
    ```


___  
## get_table  
Return the table specification  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_model](../../models/tables/#tablemodel)||[TableModel](../../models/tables/#tablemodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "can_edit" : false
	}
    ```


___  
## get_table_basic  
Return the basic table information  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_basic.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/basic  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_list_model](../../models/tables/#tablelistmodel)||[TableListModel](../../models/tables/#tablelistmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/basic^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "connection_name" : "sample_connection",
	  "table_hash" : 2314522140819107818,
	  "target" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "has_any_configured_checks" : true,
	  "has_any_configured_profiling_checks" : true,
	  "run_checks_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true
	  },
	  "run_profiling_checks_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "checkType" : "profiling"
	  },
	  "run_monitoring_checks_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "checkType" : "monitoring"
	  },
	  "run_partition_checks_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "checkType" : "partitioned"
	  },
	  "data_clean_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "deleteErrors" : true,
	    "deleteStatistics" : true,
	    "deleteCheckResults" : true,
	    "deleteSensorReadouts" : true
	  },
	  "can_edit" : true,
	  "can_collect_statistics" : true,
	  "can_run_checks" : true,
	  "can_delete_data" : true
	}
    ```


___  
## get_table_columns_monitoring_checks_model  
Return a UI friendly model of configurations for column-level data quality monitoring checks on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_columns_monitoring_checks_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columnchecks/monitoring/{timeScale}/model  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_configuration_model||List[[CheckConfigurationModel](../../models/Common/#checkconfigurationmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Check time-scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|
|column_name_pattern|Column name pattern|string| |
|column_data_type|Column data-type|string| |
|check_category|Check category|string| |
|check_name|Check name|string| |
|check_enabled|Check enabled|boolean| |
|check_configured|Check configured|boolean| |
|limit|Limit of results, the default value is 1000|long| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columnchecks/monitoring/"daily"/model^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    [ {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	}, {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	}, {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	} ]
    ```


___  
## get_table_columns_partitioned_checks_model  
Return a UI friendly model of configurations for column-level data quality partitioned checks on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_columns_partitioned_checks_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columnchecks/partitioned/{timeScale}/model  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_configuration_model||List[[CheckConfigurationModel](../../models/Common/#checkconfigurationmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Check time-scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|
|column_name_pattern|Column name pattern|string| |
|column_data_type|Column data-type|string| |
|check_category|Check category|string| |
|check_name|Check name|string| |
|check_enabled|Check enabled|boolean| |
|check_configured|Check configured|boolean| |
|limit|Limit of results, the default value is 1000|long| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columnchecks/partitioned/"daily"/model^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    [ {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	}, {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	}, {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	} ]
    ```


___  
## get_table_columns_profiling_checks_model  
Return a UI friendly model of configurations for column-level data quality profiling checks on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_columns_profiling_checks_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columnchecks/profiling/model  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_configuration_model||List[[CheckConfigurationModel](../../models/Common/#checkconfigurationmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name_pattern|Column name pattern|string| |
|column_data_type|Column data-type|string| |
|check_category|Check category|string| |
|check_name|Check name|string| |
|check_enabled|Check enabled|boolean| |
|check_configured|Check configured|boolean| |
|limit|Limit of results, the default value is 1000|long| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columnchecks/profiling/model^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    [ {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	}, {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	}, {
	  "sensor_parameters" : [ ],
	  "disabled" : false,
	  "configured" : false
	} ]
    ```


___  
## get_table_comments  
Return the list of comments added to a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_comments.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/comments  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|comment_spec||List[[CommentSpec](../../models/Common/#commentspec)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/comments^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    [ {
	  "date" : "2007-12-03T10:15:30",
	  "comment_by" : "sample_user",
	  "comment" : "Sample comment"
	}, {
	  "date" : "2007-12-03T10:15:30",
	  "comment_by" : "sample_user",
	  "comment" : "Sample comment"
	}, {
	  "date" : "2007-12-03T10:15:30",
	  "comment_by" : "sample_user",
	  "comment" : "Sample comment"
	} ]
    ```


___  
## get_table_daily_monitoring_checks  
Return the configuration of daily table level data quality monitoring on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_daily_monitoring_checks.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/daily  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_daily_monitoring_check_categories_spec](../../models/tables/#tabledailymonitoringcheckcategoriesspec)||[TableDailyMonitoringCheckCategoriesSpec](../../models/tables/#tabledailymonitoringcheckcategoriesspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/daily^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "volume" : {
	    "daily_row_count" : {
	      "error" : {
	        "min_count" : 1
	      }
	    }
	  }
	}
    ```


___  
## get_table_daily_partitioned_checks  
Return the configuration of daily table level data quality partitioned checks on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_daily_partitioned_checks.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/daily  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_daily_partitioned_check_categories_spec](../../models/tables/#tabledailypartitionedcheckcategoriesspec)||[TableDailyPartitionedCheckCategoriesSpec](../../models/tables/#tabledailypartitionedcheckcategoriesspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/daily^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "volume" : {
	    "daily_partition_row_count" : {
	      "error" : {
	        "min_count" : 1
	      }
	    }
	  }
	}
    ```


___  
## get_table_default_grouping_configuration  
Return the default data grouping configuration for a table.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_default_grouping_configuration.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/defaultgroupingconfiguration  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[data_grouping_configuration_spec](../../../reference/yaml/ConnectionYaml/#datagroupingconfigurationspec)||[DataGroupingConfigurationSpec](../../../reference/yaml/ConnectionYaml/#datagroupingconfigurationspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/defaultgroupingconfiguration^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "level_3" : {
	    "source" : "column_value",
	    "column" : "sample_column"
	  }
	}
    ```


___  
## get_table_incident_grouping  
Return the configuration of incident grouping on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_incident_grouping.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/incidentgrouping  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_incident_grouping_spec](../../../reference/yaml/TableYaml/#tableincidentgroupingspec)||[TableIncidentGroupingSpec](../../../reference/yaml/TableYaml/#tableincidentgroupingspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/incidentgrouping^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "grouping_level" : "table_dimension",
	  "minimum_severity" : "warning",
	  "divide_by_data_group" : true,
	  "disabled" : false
	}
    ```


___  
## get_table_labels  
Return the list of labels assigned to a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_labels.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/labels  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|string||List[string]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/labels^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    []
    ```


___  
## get_table_monitoring_checks_basic_model  
Return a simplistic UI friendly model of table level data quality monitoring on a table for a given time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_monitoring_checks_basic_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/model/basic  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_list_model](../../models/Common/#checkcontainerlistmodel)||[CheckContainerListModel](../../models/Common/#checkcontainerlistmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/"daily"/model/basic^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "checks" : [ {
	    "check_category" : "sample_category_1",
	    "check_name" : "sample_check_1",
	    "help_text" : "Sample help text",
	    "configured" : true
	  }, {
	    "check_category" : "sample_category_2",
	    "check_name" : "sample_check_1",
	    "help_text" : "Sample help text",
	    "configured" : true
	  }, {
	    "check_category" : "sample_category_1",
	    "check_name" : "sample_check_2",
	    "help_text" : "Sample help text",
	    "configured" : true
	  }, {
	    "check_category" : "sample_category_2",
	    "check_name" : "sample_check_2",
	    "help_text" : "Sample help text",
	    "configured" : true
	  }, {
	    "check_category" : "sample_category_1",
	    "check_name" : "sample_check_3",
	    "help_text" : "Sample help text",
	    "configured" : true
	  }, {
	    "check_category" : "sample_category_2",
	    "check_name" : "sample_check_3",
	    "help_text" : "Sample help text",
	    "configured" : true
	  } ],
	  "can_edit" : false,
	  "can_run_checks" : true,
	  "can_delete_data" : true
	}
    ```


___  
## get_table_monitoring_checks_model  
Return a UI friendly model of configurations for table level data quality monitoring on a table for a given time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_monitoring_checks_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/model  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](../../models/Common/#checkcontainermodel)||[CheckContainerModel](../../models/Common/#checkcontainermodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/"daily"/model^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "categories" : [ {
	    "category" : "sample_category",
	    "help_text" : "Sample help text",
	    "checks" : [ {
	      "check_name" : "sample_check",
	      "help_text" : "Sample help text",
	      "sensor_parameters" : [ ],
	      "sensor_name" : "sample_target/sample_category/sample_sensor",
	      "quality_dimension" : "sample_quality_dimension",
	      "supports_grouping" : false,
	      "disabled" : false,
	      "exclude_from_kpi" : false,
	      "include_in_sla" : false,
	      "configured" : false,
	      "can_edit" : false,
	      "can_run_checks" : false,
	      "can_delete_data" : false
	    } ]
	  } ],
	  "can_edit" : false,
	  "can_run_checks" : false,
	  "can_delete_data" : false
	}
    ```


___  
## get_table_monitoring_checks_model_filter  
Return a UI friendly model of configurations for table level data quality monitoring on a table for a given time scale, filtered by category and check name.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_monitoring_checks_model_filter.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/model/filter/{checkCategory}/{checkName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](../../models/Common/#checkcontainermodel)||[CheckContainerModel](../../models/Common/#checkcontainermodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|
|check_category|Check category|string|:material-check-bold:|
|check_name|Check name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/"daily"/model/filter/sample_category/sample_check^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "categories" : [ {
	    "category" : "sample_category",
	    "help_text" : "Sample help text",
	    "checks" : [ {
	      "check_name" : "sample_check",
	      "help_text" : "Sample help text",
	      "sensor_parameters" : [ ],
	      "sensor_name" : "sample_target/sample_category/sample_sensor",
	      "quality_dimension" : "sample_quality_dimension",
	      "supports_grouping" : false,
	      "disabled" : false,
	      "exclude_from_kpi" : false,
	      "include_in_sla" : false,
	      "configured" : false,
	      "can_edit" : false,
	      "can_run_checks" : false,
	      "can_delete_data" : false
	    } ]
	  } ],
	  "can_edit" : false,
	  "can_run_checks" : false,
	  "can_delete_data" : false
	}
    ```


___  
## get_table_monitoring_checks_monthly  
Return the configuration of monthly table level data quality monitoring on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_monitoring_checks_monthly.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/monthly  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_monthly_monitoring_check_categories_spec](../../models/tables/#tablemonthlymonitoringcheckcategoriesspec)||[TableMonthlyMonitoringCheckCategoriesSpec](../../models/tables/#tablemonthlymonitoringcheckcategoriesspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/monthly^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "volume" : {
	    "monthly_row_count" : {
	      "error" : {
	        "min_count" : 1
	      }
	    }
	  }
	}
    ```


___  
## get_table_monitoring_checks_templates  
Return available data quality checks on a requested table.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_monitoring_checks_templates.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/bulkenable/monitoring/{timeScale}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_template||List[[CheckTemplate](../../models/Common/#checktemplate)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|
|check_category|Check category|string| |
|check_name|Check name|string| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/bulkenable/monitoring/"daily"^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    [ {
	  "sensor_parameters_definitions" : [ ]
	}, {
	  "sensor_parameters_definitions" : [ ]
	}, {
	  "sensor_parameters_definitions" : [ ]
	} ]
    ```


___  
## get_table_partitioned_checks_basic_model  
Return a simplistic UI friendly model of table level data quality partitioned checks on a table for a given time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_partitioned_checks_basic_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model/basic  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_list_model](../../models/Common/#checkcontainerlistmodel)||[CheckContainerListModel](../../models/Common/#checkcontainerlistmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/"daily"/model/basic^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "checks" : [ {
	    "check_category" : "sample_category_1",
	    "check_name" : "sample_check_1",
	    "help_text" : "Sample help text",
	    "configured" : true
	  }, {
	    "check_category" : "sample_category_2",
	    "check_name" : "sample_check_1",
	    "help_text" : "Sample help text",
	    "configured" : true
	  }, {
	    "check_category" : "sample_category_1",
	    "check_name" : "sample_check_2",
	    "help_text" : "Sample help text",
	    "configured" : true
	  }, {
	    "check_category" : "sample_category_2",
	    "check_name" : "sample_check_2",
	    "help_text" : "Sample help text",
	    "configured" : true
	  }, {
	    "check_category" : "sample_category_1",
	    "check_name" : "sample_check_3",
	    "help_text" : "Sample help text",
	    "configured" : true
	  }, {
	    "check_category" : "sample_category_2",
	    "check_name" : "sample_check_3",
	    "help_text" : "Sample help text",
	    "configured" : true
	  } ],
	  "can_edit" : false,
	  "can_run_checks" : true,
	  "can_delete_data" : true
	}
    ```


___  
## get_table_partitioned_checks_model  
Return a UI friendly model of configurations for table level data quality partitioned checks on a table for a given time scale  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_partitioned_checks_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](../../models/Common/#checkcontainermodel)||[CheckContainerModel](../../models/Common/#checkcontainermodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/"daily"/model^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "categories" : [ {
	    "category" : "sample_category",
	    "help_text" : "Sample help text",
	    "checks" : [ {
	      "check_name" : "sample_check",
	      "help_text" : "Sample help text",
	      "sensor_parameters" : [ ],
	      "sensor_name" : "sample_target/sample_category/sample_sensor",
	      "quality_dimension" : "sample_quality_dimension",
	      "supports_grouping" : false,
	      "disabled" : false,
	      "exclude_from_kpi" : false,
	      "include_in_sla" : false,
	      "configured" : false,
	      "can_edit" : false,
	      "can_run_checks" : false,
	      "can_delete_data" : false
	    } ]
	  } ],
	  "can_edit" : false,
	  "can_run_checks" : false,
	  "can_delete_data" : false
	}
    ```


___  
## get_table_partitioned_checks_model_filter  
Return a UI friendly model of configurations for table level data quality partitioned checks on a table for a given time scale, filtered by category and check name.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_partitioned_checks_model_filter.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model/filter/{checkCategory}/{checkName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](../../models/Common/#checkcontainermodel)||[CheckContainerModel](../../models/Common/#checkcontainermodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|
|check_category|Check category|string|:material-check-bold:|
|check_name|Check name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/"daily"/model/filter/sample_category/sample_check^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "categories" : [ {
	    "category" : "sample_category",
	    "help_text" : "Sample help text",
	    "checks" : [ {
	      "check_name" : "sample_check",
	      "help_text" : "Sample help text",
	      "sensor_parameters" : [ ],
	      "sensor_name" : "sample_target/sample_category/sample_sensor",
	      "quality_dimension" : "sample_quality_dimension",
	      "supports_grouping" : false,
	      "disabled" : false,
	      "exclude_from_kpi" : false,
	      "include_in_sla" : false,
	      "configured" : false,
	      "can_edit" : false,
	      "can_run_checks" : false,
	      "can_delete_data" : false
	    } ]
	  } ],
	  "can_edit" : false,
	  "can_run_checks" : false,
	  "can_delete_data" : false
	}
    ```


___  
## get_table_partitioned_checks_monthly  
Return the configuration of monthly table level data quality partitioned checks on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_partitioned_checks_monthly.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/monthly  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_monthly_partitioned_check_categories_spec](../../models/tables/#tablemonthlypartitionedcheckcategoriesspec)||[TableMonthlyPartitionedCheckCategoriesSpec](../../models/tables/#tablemonthlypartitionedcheckcategoriesspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/monthly^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "volume" : {
	    "monthly_partition_row_count" : {
	      "error" : {
	        "min_count" : 1
	      }
	    }
	  }
	}
    ```


___  
## get_table_partitioned_checks_templates  
Return available data quality checks on a requested table.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_partitioned_checks_templates.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/bulkenable/partitioned/{timeScale}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_template||List[[CheckTemplate](../../models/Common/#checktemplate)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|
|check_category|Check category|string| |
|check_name|Check name|string| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/bulkenable/partitioned/"daily"^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    [ {
	  "sensor_parameters_definitions" : [ ]
	}, {
	  "sensor_parameters_definitions" : [ ]
	}, {
	  "sensor_parameters_definitions" : [ ]
	} ]
    ```


___  
## get_table_partitioning  
Return the table partitioning information  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_partitioning.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioning  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_partitioning_model](../../models/tables/#tablepartitioningmodel)||[TablePartitioningModel](../../models/tables/#tablepartitioningmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioning^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "connection_name" : "sample_connection",
	  "target" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "timestamp_columns" : {
	    "event_timestamp_column" : "col1",
	    "ingestion_timestamp_column" : "col2",
	    "partition_by_column" : "col3"
	  },
	  "incremental_time_window" : {
	    "daily_partitioning_recent_days" : 7,
	    "monthly_partitioning_recent_months" : 1
	  },
	  "can_edit" : true
	}
    ```


___  
## get_table_profiling_checks  
Return the configuration of table level data quality checks on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_profiling_checks.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_profiling_check_categories_spec](../../models/tables/#tableprofilingcheckcategoriesspec)||[TableProfilingCheckCategoriesSpec](../../models/tables/#tableprofilingcheckcategoriesspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "volume" : {
	    "profile_row_count" : {
	      "error" : {
	        "min_count" : 1
	      }
	    }
	  }
	}
    ```


___  
## get_table_profiling_checks_basic_model  
Return a simplistic UI friendly model of all table level data quality profiling checks on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_profiling_checks_basic_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/model/basic  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_list_model](../../models/Common/#checkcontainerlistmodel)||[CheckContainerListModel](../../models/Common/#checkcontainerlistmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/model/basic^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "checks" : [ {
	    "check_category" : "sample_category_1",
	    "check_name" : "sample_check_1",
	    "help_text" : "Sample help text",
	    "configured" : true
	  }, {
	    "check_category" : "sample_category_2",
	    "check_name" : "sample_check_1",
	    "help_text" : "Sample help text",
	    "configured" : true
	  }, {
	    "check_category" : "sample_category_1",
	    "check_name" : "sample_check_2",
	    "help_text" : "Sample help text",
	    "configured" : true
	  }, {
	    "check_category" : "sample_category_2",
	    "check_name" : "sample_check_2",
	    "help_text" : "Sample help text",
	    "configured" : true
	  }, {
	    "check_category" : "sample_category_1",
	    "check_name" : "sample_check_3",
	    "help_text" : "Sample help text",
	    "configured" : true
	  }, {
	    "check_category" : "sample_category_2",
	    "check_name" : "sample_check_3",
	    "help_text" : "Sample help text",
	    "configured" : true
	  } ],
	  "can_edit" : false,
	  "can_run_checks" : true,
	  "can_delete_data" : true
	}
    ```


___  
## get_table_profiling_checks_model  
Return a UI friendly model of configurations for all table level data quality profiling checks on a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_profiling_checks_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/model  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](../../models/Common/#checkcontainermodel)||[CheckContainerModel](../../models/Common/#checkcontainermodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/model^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "categories" : [ {
	    "category" : "sample_category",
	    "help_text" : "Sample help text",
	    "checks" : [ {
	      "check_name" : "sample_check",
	      "help_text" : "Sample help text",
	      "sensor_parameters" : [ ],
	      "sensor_name" : "sample_target/sample_category/sample_sensor",
	      "quality_dimension" : "sample_quality_dimension",
	      "supports_grouping" : false,
	      "disabled" : false,
	      "exclude_from_kpi" : false,
	      "include_in_sla" : false,
	      "configured" : false,
	      "can_edit" : false,
	      "can_run_checks" : false,
	      "can_delete_data" : false
	    } ]
	  } ],
	  "can_edit" : false,
	  "can_run_checks" : false,
	  "can_delete_data" : false
	}
    ```


___  
## get_table_profiling_checks_model_filter  
Return a UI friendly model of configurations for all table level data quality profiling checks on a table passing a filter  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_profiling_checks_model_filter.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/model/filter/{checkCategory}/{checkName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](../../models/Common/#checkcontainermodel)||[CheckContainerModel](../../models/Common/#checkcontainermodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|check_category|Check category|string|:material-check-bold:|
|check_name|Check name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/model/filter/sample_category/sample_check^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "categories" : [ {
	    "category" : "sample_category",
	    "help_text" : "Sample help text",
	    "checks" : [ {
	      "check_name" : "sample_check",
	      "help_text" : "Sample help text",
	      "sensor_parameters" : [ ],
	      "sensor_name" : "sample_target/sample_category/sample_sensor",
	      "quality_dimension" : "sample_quality_dimension",
	      "supports_grouping" : false,
	      "disabled" : false,
	      "exclude_from_kpi" : false,
	      "include_in_sla" : false,
	      "configured" : false,
	      "can_edit" : false,
	      "can_run_checks" : false,
	      "can_delete_data" : false
	    } ]
	  } ],
	  "can_edit" : false,
	  "can_run_checks" : false,
	  "can_delete_data" : false
	}
    ```


___  
## get_table_profiling_checks_templates  
Return available data quality checks on a requested table.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_profiling_checks_templates.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/bulkenable/profiling  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_template||List[[CheckTemplate](../../models/Common/#checktemplate)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|check_category|Check category|string| |
|check_name|Check name|string| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/bulkenable/profiling^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    [ {
	  "sensor_parameters_definitions" : [ ]
	}, {
	  "sensor_parameters_definitions" : [ ]
	}, {
	  "sensor_parameters_definitions" : [ ]
	} ]
    ```


___  
## get_table_scheduling_group_override  
Return the schedule override configuration for a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_scheduling_group_override.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/schedulesoverride/{schedulingGroup}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[monitoring_schedule_spec](../../models/Common/#monitoringschedulespec)||[MonitoringScheduleSpec](../../models/Common/#monitoringschedulespec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[scheduling_group](../../models/Common/#checkrunschedulegroup)|Check scheduling group (named schedule)|[CheckRunScheduleGroup](../../models/Common/#checkrunschedulegroup)|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/schedulesoverride/"partitioned_daily"^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "cron_expression" : "0 12 1 * *"
	}
    ```


___  
## get_table_statistics  
Returns a list of the profiler (statistics) metrics on a chosen table captured during the most recent statistics collection.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_table_statistics.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/statistics  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_statistics_model](../../models/tables/#tablestatisticsmodel)||[TableStatisticsModel](../../models/tables/#tablestatisticsmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/statistics^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "can_collect_statistics" : false
	}
    ```


___  
## get_tables  
Returns a list of tables inside a connection/schema  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/get_tables.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|table_list_model||List[[TableListModel](../../models/tables/#tablelistmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    [ {
	  "connection_name" : "sample_connection",
	  "table_hash" : 2314522140819107818,
	  "target" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "has_any_configured_checks" : true,
	  "has_any_configured_profiling_checks" : true,
	  "run_checks_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true
	  },
	  "run_profiling_checks_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "checkType" : "profiling"
	  },
	  "run_monitoring_checks_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "checkType" : "monitoring"
	  },
	  "run_partition_checks_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "checkType" : "partitioned"
	  },
	  "data_clean_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "deleteErrors" : true,
	    "deleteStatistics" : true,
	    "deleteCheckResults" : true,
	    "deleteSensorReadouts" : true
	  },
	  "can_edit" : true,
	  "can_collect_statistics" : true,
	  "can_run_checks" : true,
	  "can_delete_data" : true
	}, {
	  "connection_name" : "sample_connection",
	  "table_hash" : 2314522140819107818,
	  "target" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "has_any_configured_checks" : true,
	  "has_any_configured_profiling_checks" : true,
	  "run_checks_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true
	  },
	  "run_profiling_checks_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "checkType" : "profiling"
	  },
	  "run_monitoring_checks_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "checkType" : "monitoring"
	  },
	  "run_partition_checks_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "checkType" : "partitioned"
	  },
	  "data_clean_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "deleteErrors" : true,
	    "deleteStatistics" : true,
	    "deleteCheckResults" : true,
	    "deleteSensorReadouts" : true
	  },
	  "can_edit" : true,
	  "can_collect_statistics" : true,
	  "can_run_checks" : true,
	  "can_delete_data" : true
	}, {
	  "connection_name" : "sample_connection",
	  "table_hash" : 2314522140819107818,
	  "target" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "has_any_configured_checks" : true,
	  "has_any_configured_profiling_checks" : true,
	  "run_checks_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true
	  },
	  "run_profiling_checks_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "checkType" : "profiling"
	  },
	  "run_monitoring_checks_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "checkType" : "monitoring"
	  },
	  "run_partition_checks_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "checkType" : "partitioned"
	  },
	  "data_clean_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "deleteErrors" : true,
	    "deleteStatistics" : true,
	    "deleteCheckResults" : true,
	    "deleteSensorReadouts" : true
	  },
	  "can_edit" : true,
	  "can_collect_statistics" : true,
	  "can_run_checks" : true,
	  "can_delete_data" : true
	} ]
    ```


___  
## update_table  
Updates an existing table specification, changing all the fields  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Full table specification|[TableSpec](../../../reference/yaml/TableYaml/#tablespec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"timestamp_columns\":{\"event_timestamp_column\":\"col1\",\"ingestion_timestamp_column\":\"col2\",\"partition_by_column\":\"col3\"},\"incremental_time_window\":{\"daily_partitioning_recent_days\":7,\"monthly_partitioning_recent_months\":1},\"profiling_checks\":{\"volume\":{\"profile_row_count\":{\"error\":{\"min_count\":1}}}},\"columns\":{}}"

    ```




___  
## update_table_basic  
Updates the basic field of an existing table, changing only the most important fields.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_basic.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/basic  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table basic model with the updated settings|[TableListModel](../../models/tables/#tablelistmodel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/basic^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection_name\":\"sample_connection\",\"table_hash\":2314522140819107818,\"target\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"has_any_configured_checks\":true,\"has_any_configured_profiling_checks\":true,\"run_checks_job_template\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true},\"run_profiling_checks_job_template\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"profiling\"},\"run_monitoring_checks_job_template\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"monitoring\"},\"run_partition_checks_job_template\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"partitioned\"},\"data_clean_job_template\":{\"connection\":\"sample_connection\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true},\"can_edit\":true,\"can_collect_statistics\":true,\"can_run_checks\":true,\"can_delete_data\":true}"

    ```




___  
## update_table_comments  
Updates the list of comments on an existing table.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_comments.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/comments  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|List of comments to attach (replace) on a table or an empty object to clear the list of comments on a table|List[[CommentSpec](../../models/Common/#commentspec)]| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/comments^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"[{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"},{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"},{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"}]"

    ```




___  
## update_table_daily_monitoring_checks  
Updates the list of daily table level data quality monitoring on an existing table.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_daily_monitoring_checks.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/daily  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of daily table level data quality monitoring to store or an empty object to remove all data quality monitoring on the table level (column level monitoring are preserved).|[TableDailyMonitoringCheckCategoriesSpec](../../models/tables/#tabledailymonitoringcheckcategoriesspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"volume\":{\"daily_row_count\":{\"error\":{\"min_count\":1}}}}"

    ```




___  
## update_table_default_grouping_configuration  
Updates the default data grouping configuration at a table level.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_default_grouping_configuration.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/defaultgroupingconfiguration  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Default data grouping configuration to store or an empty object to clear the data grouping configuration on a table level|[DataGroupingConfigurationSpec](../../../reference/yaml/ConnectionYaml/#datagroupingconfigurationspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/defaultgroupingconfiguration^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"level_3\":{\"source\":\"column_value\",\"column\":\"sample_column\"}}"

    ```




___  
## update_table_incident_grouping  
Updates the configuration of incident grouping on a table.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_incident_grouping.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/incidentgrouping  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|New configuration of the table&#x27;s incident grouping|[TableIncidentGroupingSpec](../../../reference/yaml/TableYaml/#tableincidentgroupingspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/incidentgrouping^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"grouping_level\":\"table_dimension\",\"minimum_severity\":\"warning\",\"divide_by_data_group\":true,\"disabled\":false}"

    ```




___  
## update_table_labels  
Updates the list of assigned labels of an existing table.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_labels.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/labels  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|List of labels to attach (replace) on a table or an empty object to clear the list of labels on a table|List[string]| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/labels^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"[]"

    ```




___  
## update_table_monitoring_checks_model  
Updates the data quality monitoring from a model that contains a patch with changes.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_monitoring_checks_model.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/model  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality monitoring configuration.|[CheckContainerModel](../../models/Common/#checkcontainermodel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/"daily"/model^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"

    ```




___  
## update_table_monitoring_checks_monthly  
Updates the list of monthly table level data quality monitoring on an existing table.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_monitoring_checks_monthly.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/monthly  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of monthly table level data quality monitoring to store or an empty object to remove all data quality monitoring on the table level (column level monitoring are preserved).|[TableMonthlyMonitoringCheckCategoriesSpec](../../models/tables/#tablemonthlymonitoringcheckcategoriesspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/monitoring/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"volume\":{\"monthly_row_count\":{\"error\":{\"min_count\":1}}}}"

    ```




___  
## update_table_partitioned_checks_daily  
Updates the list of daily table level data quality partitioned checks on an existing table.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_partitioned_checks_daily.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/daily  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of daily table level data quality partitioned checks to store or an empty object to remove all data quality partitioned checks on the table level (column level partitioned checks are preserved).|[TableDailyPartitionedCheckCategoriesSpec](../../models/tables/#tabledailypartitionedcheckcategoriesspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"volume\":{\"daily_partition_row_count\":{\"error\":{\"min_count\":1}}}}"

    ```




___  
## update_table_partitioned_checks_model  
Updates the data quality partitioned checks from a model that contains a patch with changes.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_partitioned_checks_model.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/model  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality partitioned checks configuration.|[CheckContainerModel](../../models/Common/#checkcontainermodel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/"daily"/model^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"

    ```




___  
## update_table_partitioned_checks_monthly  
Updates the list of monthly table level data quality partitioned checks on an existing table.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_partitioned_checks_monthly.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/monthly  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of monthly table level data quality partitioned checks to store or an empty object to remove all data quality partitioned checks on the table level (column level partitioned checks are preserved).|[TableMonthlyPartitionedCheckCategoriesSpec](../../models/tables/#tablemonthlypartitionedcheckcategoriesspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioned/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"volume\":{\"monthly_partition_row_count\":{\"error\":{\"min_count\":1}}}}"

    ```




___  
## update_table_partitioning  
Updates the table partitioning configuration of an existing table.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_partitioning.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioning  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table partitioning model with the updated settings|[TablePartitioningModel](../../models/tables/#tablepartitioningmodel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/partitioning^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection_name\":\"sample_connection\",\"target\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"timestamp_columns\":{\"event_timestamp_column\":\"col1\",\"ingestion_timestamp_column\":\"col2\",\"partition_by_column\":\"col3\"},\"incremental_time_window\":{\"daily_partitioning_recent_days\":7,\"monthly_partitioning_recent_months\":1},\"can_edit\":true}"

    ```




___  
## update_table_profiling_checks  
Updates the list of table level data quality profiling checks on an existing table.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_profiling_checks.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of table level data quality profiling checks to store or an empty object to remove all data quality profiling checks on the table level (column level profiling checks are preserved).|[TableProfilingCheckCategoriesSpec](../../models/tables/#tableprofilingcheckcategoriesspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"volume\":{\"profile_row_count\":{\"error\":{\"min_count\":1}}}}"

    ```




___  
## update_table_profiling_checks_model  
Updates the data quality profiling checks from a model that contains a patch with changes.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_profiling_checks_model.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/model  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality profiling checks configuration.|[CheckContainerModel](../../models/Common/#checkcontainermodel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/profiling/model^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"

    ```




___  
## update_table_scheduling_group_override  
Updates the overridden schedule configuration of an existing table for a named schedule group (named schedule for checks using the same time scale).  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/tables/update_table_scheduling_group_override.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/schedulesoverride/{schedulingGroup}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|[scheduling_group](../../models/Common/#checkrunschedulegroup)|Check scheduling group (named schedule)|[CheckRunScheduleGroup](../../models/Common/#checkrunschedulegroup)|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Table&#x27;s overridden schedule configuration to store or an empty object to clear the schedule configuration on a table|[MonitoringScheduleSpec](../../models/Common/#monitoringschedulespec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/schedulesoverride/"partitioned_daily"^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"cron_expression\":\"0 12 1 * *\"}"

    ```




