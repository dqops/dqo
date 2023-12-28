Manages columns inside a table  


___  
## create_column  
Creates a new column (adds a column metadata to the table)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/create_column.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Column specification|[ColumnSpec](../../../reference/yaml/TableYaml/#columnspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"type_snapshot\":{\"column_type\":\"string\",\"nullable\":false,\"length\":256},\"profiling_checks\":{\"nulls\":{\"profile_nulls_count\":{\"error\":{\"max_count\":10}}}}}"

    ```




___  
## delete_column  
Deletes a column from the table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/delete_column.py)
  

**DELETE**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}  
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
|column_name|Column name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl -X DELETE http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column^
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
## get_column  
Returns the full column specification  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[column_model](../../models/columns/#columnmodel)||[ColumnModel](../../models/columns/#columnmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "connection_name" : "sample_connection",
	  "table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "column_name" : "sample_column",
	  "spec" : {
	    "type_snapshot" : {
	      "column_type" : "string",
	      "nullable" : false,
	      "length" : 256
	    },
	    "profiling_checks" : {
	      "nulls" : {
	        "profile_nulls_count" : {
	          "error" : {
	            "max_count" : 10
	          }
	        }
	      }
	    }
	  },
	  "can_edit" : true
	}
    ```


___  
## get_column_basic  
Returns the column specification  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_basic.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/basic  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[column_list_model](../../models/columns/#columnlistmodel)||[ColumnListModel](../../models/columns/#columnlistmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/basic^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "connection_name" : "sample_connection",
	  "table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "column_name" : "sample_column",
	  "has_any_configured_checks" : true,
	  "has_any_configured_profiling_checks" : true,
	  "type_snapshot" : {
	    "column_type" : "string",
	    "nullable" : false,
	    "length" : 256
	  },
	  "can_edit" : false,
	  "can_collect_statistics" : true,
	  "can_run_checks" : true,
	  "can_delete_data" : true
	}
    ```


___  
## get_column_comments  
Return the list of comments assigned to a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_comments.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/comments  
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
|column_name|Column name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/comments^
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
## get_column_labels  
Return the list of labels assigned to a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_labels.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/labels  
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
|column_name|Column name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/labels^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    []
    ```


___  
## get_column_monitoring_checks_basic_model  
Return a simplistic UI friendly model of column level data quality monitoring on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_monitoring_checks_basic_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/model/basic  
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
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/"daily"/model/basic^
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
## get_column_monitoring_checks_daily  
Return the configuration of daily column level data quality monitoring on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_monitoring_checks_daily.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/daily  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[column_daily_monitoring_check_categories_spec](../../models/columns/#columndailymonitoringcheckcategoriesspec)||[ColumnDailyMonitoringCheckCategoriesSpec](../../models/columns/#columndailymonitoringcheckcategoriesspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "nulls" : {
	    "daily_nulls_count" : {
	      "error" : {
	        "max_count" : 10
	      }
	    }
	  }
	}
    ```


___  
## get_column_monitoring_checks_model  
Return a UI friendly model of column level data quality monitoring on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_monitoring_checks_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/model  
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
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/"daily"/model^
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
## get_column_monitoring_checks_model_filter  
Return a UI friendly model of column level data quality monitoring on a column filtered by category and check name  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_monitoring_checks_model_filter.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/model/filter/{checkCategory}/{checkName}  
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
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|
|check_category|Check category|string|:material-check-bold:|
|check_name|Check name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/"daily"/model/filter/sample_category/sample_check^
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
## get_column_monitoring_checks_monthly  
Return the configuration of monthly column level data quality monitoring on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_monitoring_checks_monthly.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/monthly  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[column_monthly_monitoring_check_categories_spec](../../models/columns/#columnmonthlymonitoringcheckcategoriesspec)||[ColumnMonthlyMonitoringCheckCategoriesSpec](../../models/columns/#columnmonthlymonitoringcheckcategoriesspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/monthly^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "nulls" : {
	    "monthly_nulls_count" : {
	      "error" : {
	        "max_count" : 10
	      }
	    }
	  }
	}
    ```


___  
## get_column_partitioned_checks_basic_model  
Return a simplistic UI friendly model of column level data quality partitioned checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_partitioned_checks_basic_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model/basic  
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
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/"daily"/model/basic^
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
## get_column_partitioned_checks_daily  
Return the configuration of daily column level data quality partitioned checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_partitioned_checks_daily.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/daily  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[column_daily_partitioned_check_categories_spec](../../models/columns/#columndailypartitionedcheckcategoriesspec)||[ColumnDailyPartitionedCheckCategoriesSpec](../../models/columns/#columndailypartitionedcheckcategoriesspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "nulls" : {
	    "daily_partition_nulls_count" : {
	      "error" : {
	        "max_count" : 10
	      }
	    }
	  }
	}
    ```


___  
## get_column_partitioned_checks_model  
Return a UI friendly model of column level data quality partitioned checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_partitioned_checks_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model  
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
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/"daily"/model^
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
## get_column_partitioned_checks_model_filter  
Return a UI friendly model of column level data quality partitioned checks on a column, filtered by category and check name  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_partitioned_checks_model_filter.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model/filter/{checkCategory}/{checkName}  
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
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|
|check_category|Check category|string|:material-check-bold:|
|check_name|Check name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/"daily"/model/filter/sample_category/sample_check^
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
## get_column_partitioned_checks_monthly  
Return the configuration of monthly column level data quality partitioned checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_partitioned_checks_monthly.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/monthly  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[column_monthly_partitioned_check_categories_spec](../../models/columns/#columnmonthlypartitionedcheckcategoriesspec)||[ColumnMonthlyPartitionedCheckCategoriesSpec](../../models/columns/#columnmonthlypartitionedcheckcategoriesspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/monthly^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "nulls" : {
	    "monthly_partition_nulls_count" : {
	      "error" : {
	        "max_count" : 10
	      }
	    }
	  }
	}
    ```


___  
## get_column_profiling_checks  
Return the configuration of column level data quality profiling checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_profiling_checks.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[column_profiling_check_categories_spec](../../models/columns/#columnprofilingcheckcategoriesspec)||[ColumnProfilingCheckCategoriesSpec](../../models/columns/#columnprofilingcheckcategoriesspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "nulls" : {
	    "profile_nulls_count" : {
	      "error" : {
	        "max_count" : 10
	      }
	    }
	  }
	}
    ```


___  
## get_column_profiling_checks_basic_model  
Return a simplistic UI friendly model of column level data quality profiling checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_profiling_checks_basic_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/model/basic  
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
|column_name|Column name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/model/basic^
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
## get_column_profiling_checks_model  
Return a UI friendly model of data quality profiling checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_profiling_checks_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/model  
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
|column_name|Column name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/model^
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
## get_column_profiling_checks_model_filter  
Return a UI friendly model of data quality profiling checks on a column filtered by category and check name  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_profiling_checks_model_filter.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/model/filter/{checkCategory}/{checkName}  
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
|column_name|Column name|string|:material-check-bold:|
|check_category|Check category|string|:material-check-bold:|
|check_name|Check name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/model/filter/sample_category/sample_check^
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
## get_column_statistics  
Returns the column specification with the metrics captured by the most recent statistics collection.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_statistics.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/statistics  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[column_statistics_model](../../models/columns/#columnstatisticsmodel)||[ColumnStatisticsModel](../../models/columns/#columnstatisticsmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/statistics^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "connection_name" : "sample_connection",
	  "table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "column_name" : "sample_column",
	  "has_any_configured_checks" : true,
	  "type_snapshot" : {
	    "column_type" : "string",
	    "nullable" : false,
	    "length" : 256
	  },
	  "statistics" : [ {
	    "category" : "sample_category",
	    "collector" : "sample_collector",
	    "resultDataType" : "integer",
	    "result" : 4372,
	    "collectedAt" : "2007-10-11T18:00:00"
	  }, {
	    "category" : "sample_category",
	    "collector" : "sample_collector",
	    "resultDataType" : "integer",
	    "result" : 9624,
	    "collectedAt" : "2007-10-12T18:00:00"
	  }, {
	    "category" : "sample_category",
	    "collector" : "sample_collector",
	    "resultDataType" : "integer",
	    "result" : 1575,
	    "collectedAt" : "2007-10-13T18:00:00"
	  }, {
	    "category" : "sample_category",
	    "collector" : "sample_collector",
	    "resultDataType" : "integer",
	    "result" : 5099,
	    "collectedAt" : "2007-10-14T18:00:00"
	  }, {
	    "category" : "sample_category",
	    "collector" : "sample_collector",
	    "resultDataType" : "integer",
	    "result" : 9922,
	    "collectedAt" : "2007-10-15T18:00:00"
	  } ],
	  "collect_column_statistics_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "columnNames" : [ "sample_column" ]
	  },
	  "can_collect_statistics" : true
	}
    ```


___  
## get_columns  
Returns a list of columns inside a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_columns.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|column_list_model||List[[ColumnListModel](../../models/columns/#columnlistmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    [ {
	  "connection_name" : "sample_connection",
	  "table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "column_name" : "sample_column",
	  "has_any_configured_checks" : true,
	  "has_any_configured_profiling_checks" : true,
	  "type_snapshot" : {
	    "column_type" : "string",
	    "nullable" : false,
	    "length" : 256
	  },
	  "can_edit" : false,
	  "can_collect_statistics" : true,
	  "can_run_checks" : true,
	  "can_delete_data" : true
	}, {
	  "connection_name" : "sample_connection",
	  "table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "column_name" : "sample_column",
	  "has_any_configured_checks" : true,
	  "has_any_configured_profiling_checks" : true,
	  "type_snapshot" : {
	    "column_type" : "string",
	    "nullable" : false,
	    "length" : 256
	  },
	  "can_edit" : false,
	  "can_collect_statistics" : true,
	  "can_run_checks" : true,
	  "can_delete_data" : true
	}, {
	  "connection_name" : "sample_connection",
	  "table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "column_name" : "sample_column",
	  "has_any_configured_checks" : true,
	  "has_any_configured_profiling_checks" : true,
	  "type_snapshot" : {
	    "column_type" : "string",
	    "nullable" : false,
	    "length" : 256
	  },
	  "can_edit" : false,
	  "can_collect_statistics" : true,
	  "can_run_checks" : true,
	  "can_delete_data" : true
	} ]
    ```


___  
## get_columns_statistics  
Returns a list of columns inside a table with the metrics captured by the most recent statistics collection.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_columns_statistics.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/statistics  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_columns_statistics_model](../../models/columns/#tablecolumnsstatisticsmodel)||[TableColumnsStatisticsModel](../../models/columns/#tablecolumnsstatisticsmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/statistics^
		-H "Accept: application/json"

    ```



**Return value sample**  
    ```js
    {
	  "connection_name" : "sample_connection",
	  "table" : {
	    "schema_name" : "sample_schema",
	    "table_name" : "sample_table"
	  },
	  "column_statistics" : [ {
	    "connection_name" : "sample_connection",
	    "table" : {
	      "schema_name" : "sample_schema",
	      "table_name" : "sample_table"
	    },
	    "column_name" : "sample_column",
	    "has_any_configured_checks" : true,
	    "type_snapshot" : {
	      "column_type" : "string",
	      "nullable" : false,
	      "length" : 256
	    },
	    "statistics" : [ {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
	      "resultDataType" : "integer",
	      "result" : 4372,
	      "collectedAt" : "2007-10-11T18:00:00"
	    }, {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
	      "resultDataType" : "integer",
	      "result" : 9624,
	      "collectedAt" : "2007-10-12T18:00:00"
	    }, {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
	      "resultDataType" : "integer",
	      "result" : 1575,
	      "collectedAt" : "2007-10-13T18:00:00"
	    }, {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
	      "resultDataType" : "integer",
	      "result" : 5099,
	      "collectedAt" : "2007-10-14T18:00:00"
	    }, {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
	      "resultDataType" : "integer",
	      "result" : 9922,
	      "collectedAt" : "2007-10-15T18:00:00"
	    } ],
	    "collect_column_statistics_job_template" : {
	      "connection" : "sample_connection",
	      "fullTableName" : "sample_schema.sample_table",
	      "enabled" : true,
	      "columnNames" : [ "sample_column" ]
	    },
	    "can_collect_statistics" : true
	  }, {
	    "connection_name" : "sample_connection",
	    "table" : {
	      "schema_name" : "sample_schema",
	      "table_name" : "sample_table"
	    },
	    "column_name" : "sample_column_1",
	    "has_any_configured_checks" : true,
	    "type_snapshot" : {
	      "column_type" : "string",
	      "nullable" : false,
	      "length" : 256
	    },
	    "statistics" : [ {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
	      "resultDataType" : "integer",
	      "result" : 4372,
	      "collectedAt" : "2007-10-11T18:00:00"
	    }, {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
	      "resultDataType" : "integer",
	      "result" : 9624,
	      "collectedAt" : "2007-10-12T18:00:00"
	    }, {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
	      "resultDataType" : "integer",
	      "result" : 1575,
	      "collectedAt" : "2007-10-13T18:00:00"
	    }, {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
	      "resultDataType" : "integer",
	      "result" : 5099,
	      "collectedAt" : "2007-10-14T18:00:00"
	    }, {
	      "category" : "sample_category",
	      "collector" : "sample_collector",
	      "resultDataType" : "integer",
	      "result" : 9922,
	      "collectedAt" : "2007-10-15T18:00:00"
	    } ],
	    "collect_column_statistics_job_template" : {
	      "connection" : "sample_connection",
	      "fullTableName" : "sample_schema.sample_table",
	      "enabled" : true,
	      "columnNames" : [ "sample_column" ]
	    },
	    "can_collect_statistics" : true
	  } ],
	  "collect_column_statistics_job_template" : {
	    "connection" : "sample_connection",
	    "fullTableName" : "sample_schema.sample_table",
	    "enabled" : true,
	    "columnNames" : [ "sample_column" ],
	    "collectorCategory" : "sample_category"
	  },
	  "can_collect_statistics" : true
	}
    ```


___  
## update_column  
Updates an existing column specification, changing all the fields (even the column level data quality checks).  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Column specification|[ColumnSpec](../../../reference/yaml/TableYaml/#columnspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"type_snapshot\":{\"column_type\":\"string\",\"nullable\":false,\"length\":256},\"profiling_checks\":{\"nulls\":{\"profile_nulls_count\":{\"error\":{\"max_count\":10}}}}}"

    ```




___  
## update_column_basic  
Updates an existing column, changing only the basic information like the expected data type (the data type snapshot).  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_basic.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/basic  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Basic column information to store|[ColumnListModel](../../models/columns/#columnlistmodel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/basic^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"connection_name\":\"sample_connection\",\"table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"column_name\":\"sample_column\",\"has_any_configured_checks\":true,\"has_any_configured_profiling_checks\":true,\"type_snapshot\":{\"column_type\":\"string\",\"nullable\":false,\"length\":256},\"can_edit\":false,\"can_collect_statistics\":true,\"can_run_checks\":true,\"can_delete_data\":true}"

    ```




___  
## update_column_comments  
Updates the list of comments assigned to a column.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_comments.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/comments  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|List of comments to stored (replaced) on the column or an empty object to clear the list of assigned comments on the column|List[[CommentSpec](../../models/Common/#commentspec)]| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/comments^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"[{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"},{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"},{\"date\":\"2007-12-03T10:15:30\",\"comment_by\":\"sample_user\",\"comment\":\"Sample comment\"}]"

    ```




___  
## update_column_labels  
Updates the list of labels assigned to a column.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_labels.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/labels  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|List of labels to stored (replaced) on the column or an empty object to clear the list of assigned labels on the column|List[string]| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/labels^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"[]"

    ```




___  
## update_column_monitoring_checks_daily  
Updates configuration of daily column level data quality monitoring on a column.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_monitoring_checks_daily.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/daily  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of daily column level data quality monitoring to configure on a column or an empty object to clear the list of assigned daily data quality monitoring on the column|[ColumnDailyMonitoringCheckCategoriesSpec](../../models/columns/#columndailymonitoringcheckcategoriesspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"nulls\":{\"daily_nulls_count\":{\"error\":{\"max_count\":10}}}}"

    ```




___  
## update_column_monitoring_checks_model  
Updates configuration of column level data quality monitoring on a column, for a given time scale, from a UI friendly model.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_monitoring_checks_model.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/model  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality monitoring configuration|[CheckContainerModel](../../models/Common/#checkcontainermodel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/"daily"/model^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"

    ```




___  
## update_column_monitoring_checks_monthly  
Updates configuration of monthly column level data quality monitoring checks on a column.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_monitoring_checks_monthly.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/monthly  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of monthly column level data quality monitoring to configure on a column or an empty object to clear the list of assigned monthly data quality monitoring on the column|[ColumnMonthlyMonitoringCheckCategoriesSpec](../../models/columns/#columnmonthlymonitoringcheckcategoriesspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"nulls\":{\"monthly_nulls_count\":{\"error\":{\"max_count\":10}}}}"

    ```




___  
## update_column_partitioned_checks_daily  
Updates configuration of daily column level data quality partitioned checks on a column.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_partitioned_checks_daily.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/daily  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of daily column level data quality partitioned checks to configure on a column or an empty object to clear the list of assigned data quality partitioned checks on the column|[ColumnDailyPartitionedCheckCategoriesSpec](../../models/columns/#columndailypartitionedcheckcategoriesspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"nulls\":{\"daily_partition_nulls_count\":{\"error\":{\"max_count\":10}}}}"

    ```




___  
## update_column_partitioned_checks_model  
Updates configuration of column level data quality partitioned checks on a column, for a given time scale, from a UI friendly model.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_partitioned_checks_model.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|
|[time_scale](../../models/Common/#checktimescale)|Time scale|[CheckTimeScale](../../models/Common/#checktimescale)|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality partitioned checks configuration|[CheckContainerModel](../../models/Common/#checkcontainermodel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/"daily"/model^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"

    ```




___  
## update_column_partitioned_checks_monthly  
Updates configuration of monthly column level data quality partitioned checks on a column.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_partitioned_checks_monthly.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/monthly  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of monthly column level data quality partitioned checks to configure on a column or an empty object to clear the list of assigned data quality partitioned checks on the column|[ColumnMonthlyPartitionedCheckCategoriesSpec](../../models/columns/#columnmonthlypartitionedcheckcategoriesspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"nulls\":{\"monthly_partition_nulls_count\":{\"error\":{\"max_count\":10}}}}"

    ```




___  
## update_column_profiling_checks  
Updates configuration of column level data quality profiling checks on a column.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_profiling_checks.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of column level data quality profiling checks to configure on a column or an empty object to clear the list of assigned data quality profiling checks on the column|[ColumnProfilingCheckCategoriesSpec](../../models/columns/#columnprofilingcheckcategoriesspec)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"nulls\":{\"profile_nulls_count\":{\"error\":{\"max_count\":10}}}}"

    ```




___  
## update_column_profiling_checks_model  
Updates configuration of column level data quality profiling checks on a column from a UI friendly model.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_profiling_checks_model.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/model  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name|Table name|string|:material-check-bold:|
|column_name|Column name|string|:material-check-bold:|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality profiling checks configuration|[CheckContainerModel](../../models/Common/#checkcontainermodel)| |




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/model^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"

    ```




