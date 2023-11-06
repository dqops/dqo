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
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Column specification|[ColumnSpec](\docs\reference\yaml\tableyaml\#columnspec)|false|




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
|[dqo_queue_job_id](\docs\client\models\#dqoqueuejobid)||[DqoQueueJobId](\docs\client\models\#dqoqueuejobid)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl -X DELETE http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column^
		-H "Accept: application/json"

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
|[column_model](\docs\client\models\columns\#columnmodel)||[ColumnModel](\docs\client\models\columns\#columnmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column^
		-H "Accept: application/json"

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
|[column_list_model](\docs\client\models\columns\#columnlistmodel)||[ColumnListModel](\docs\client\models\columns\#columnlistmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/basic^
		-H "Accept: application/json"

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
|comment_spec||List[[CommentSpec](\docs\client\models\#commentspec)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/comments^
		-H "Accept: application/json"

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
|string||string_list|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/labels^
		-H "Accept: application/json"

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
|[check_container_list_model](\docs\client\models\#checkcontainerlistmodel)||[CheckContainerListModel](\docs\client\models\#checkcontainerlistmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|
|[time_scale](\docs\client\models\#checktimescale)|Time scale|[CheckTimeScale](\docs\client\models\#checktimescale)|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/"daily"/model/basic^
		-H "Accept: application/json"

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
|[column_daily_monitoring_check_categories_spec](\docs\client\models\columns\#columndailymonitoringcheckcategoriesspec)||[ColumnDailyMonitoringCheckCategoriesSpec](\docs\client\models\columns\#columndailymonitoringcheckcategoriesspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/daily^
		-H "Accept: application/json"

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
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|
|[time_scale](\docs\client\models\#checktimescale)|Time scale|[CheckTimeScale](\docs\client\models\#checktimescale)|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/"daily"/model^
		-H "Accept: application/json"

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
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|
|[time_scale](\docs\client\models\#checktimescale)|Time scale|[CheckTimeScale](\docs\client\models\#checktimescale)|true|
|check_category|Check category|string|true|
|check_name|Check name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/"daily"/model/filter/sample_category/sample_check^
		-H "Accept: application/json"

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
|[column_monthly_monitoring_check_categories_spec](\docs\client\models\columns\#columnmonthlymonitoringcheckcategoriesspec)||[ColumnMonthlyMonitoringCheckCategoriesSpec](\docs\client\models\columns\#columnmonthlymonitoringcheckcategoriesspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/monitoring/monthly^
		-H "Accept: application/json"

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
|[check_container_list_model](\docs\client\models\#checkcontainerlistmodel)||[CheckContainerListModel](\docs\client\models\#checkcontainerlistmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|
|[time_scale](\docs\client\models\#checktimescale)|Time scale|[CheckTimeScale](\docs\client\models\#checktimescale)|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/"daily"/model/basic^
		-H "Accept: application/json"

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
|[column_daily_partitioned_check_categories_spec](\docs\client\models\columns\#columndailypartitionedcheckcategoriesspec)||[ColumnDailyPartitionedCheckCategoriesSpec](\docs\client\models\columns\#columndailypartitionedcheckcategoriesspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/daily^
		-H "Accept: application/json"

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
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|
|[time_scale](\docs\client\models\#checktimescale)|Time scale|[CheckTimeScale](\docs\client\models\#checktimescale)|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/"daily"/model^
		-H "Accept: application/json"

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
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|
|[time_scale](\docs\client\models\#checktimescale)|Time scale|[CheckTimeScale](\docs\client\models\#checktimescale)|true|
|check_category|Check category|string|true|
|check_name|Check name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/"daily"/model/filter/sample_category/sample_check^
		-H "Accept: application/json"

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
|[column_monthly_partitioned_check_categories_spec](\docs\client\models\columns\#columnmonthlypartitionedcheckcategoriesspec)||[ColumnMonthlyPartitionedCheckCategoriesSpec](\docs\client\models\columns\#columnmonthlypartitionedcheckcategoriesspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/partitioned/monthly^
		-H "Accept: application/json"

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
|[column_profiling_check_categories_spec](\docs\client\models\columns\#columnprofilingcheckcategoriesspec)||[ColumnProfilingCheckCategoriesSpec](\docs\client\models\columns\#columnprofilingcheckcategoriesspec)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling^
		-H "Accept: application/json"

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
|[check_container_list_model](\docs\client\models\#checkcontainerlistmodel)||[CheckContainerListModel](\docs\client\models\#checkcontainerlistmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/model/basic^
		-H "Accept: application/json"

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
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/model^
		-H "Accept: application/json"

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
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|
|check_category|Check category|string|true|
|check_name|Check name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/model/filter/sample_category/sample_check^
		-H "Accept: application/json"

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
|[column_statistics_model](\docs\client\models\columns\#columnstatisticsmodel)||[ColumnStatisticsModel](\docs\client\models\columns\#columnstatisticsmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/statistics^
		-H "Accept: application/json"

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
|column_list_model||List[[ColumnListModel](\docs\client\models\columns\#columnlistmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns^
		-H "Accept: application/json"

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
|[table_columns_statistics_model](\docs\client\models\columns\#tablecolumnsstatisticsmodel)||[TableColumnsStatisticsModel](\docs\client\models\columns\#tablecolumnsstatisticsmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/statistics^
		-H "Accept: application/json"

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
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Column specification|[ColumnSpec](\docs\reference\yaml\tableyaml\#columnspec)|false|




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
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Basic column information to store|[ColumnListModel](\docs\client\models\columns\#columnlistmodel)|false|




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
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|List of comments to stored (replaced) on the column or an empty object to clear the list of assigned comments on the column|List[[CommentSpec](\docs\client\models\#commentspec)]|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/comments^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"[]"

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
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|List of labels to stored (replaced) on the column or an empty object to clear the list of assigned labels on the column|string_list|false|




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
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of daily column level data quality monitoring to configure on a column or an empty object to clear the list of assigned daily data quality monitoring on the column|[ColumnDailyMonitoringCheckCategoriesSpec](\docs\client\models\columns\#columndailymonitoringcheckcategoriesspec)|false|




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
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|
|[time_scale](\docs\client\models\#checktimescale)|Time scale|[CheckTimeScale](\docs\client\models\#checktimescale)|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality monitoring configuration|[CheckContainerModel](\docs\client\models\#checkcontainermodel)|false|




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
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of monthly column level data quality monitoring to configure on a column or an empty object to clear the list of assigned monthly data quality monitoring on the column|[ColumnMonthlyMonitoringCheckCategoriesSpec](\docs\client\models\columns\#columnmonthlymonitoringcheckcategoriesspec)|false|




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
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of daily column level data quality partitioned checks to configure on a column or an empty object to clear the list of assigned data quality partitioned checks on the column|[ColumnDailyPartitionedCheckCategoriesSpec](\docs\client\models\columns\#columndailypartitionedcheckcategoriesspec)|false|




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
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|
|[time_scale](\docs\client\models\#checktimescale)|Time scale|[CheckTimeScale](\docs\client\models\#checktimescale)|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality partitioned checks configuration|[CheckContainerModel](\docs\client\models\#checkcontainermodel)|false|




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
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of monthly column level data quality partitioned checks to configure on a column or an empty object to clear the list of assigned data quality partitioned checks on the column|[ColumnMonthlyPartitionedCheckCategoriesSpec](\docs\client\models\columns\#columnmonthlypartitionedcheckcategoriesspec)|false|




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
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Configuration of column level data quality profiling checks to configure on a column or an empty object to clear the list of assigned data quality profiling checks on the column|[ColumnProfilingCheckCategoriesSpec](\docs\client\models\columns\#columnprofilingcheckcategoriesspec)|false|




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
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|column_name|Column name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Model with the changes to be applied to the data quality profiling checks configuration|[CheckContainerModel](\docs\client\models\#checkcontainermodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/columns/sample_column/profiling/model^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"categories\":[{\"category\":\"sample_category\",\"help_text\":\"Sample help text\",\"checks\":[{\"check_name\":\"sample_check\",\"help_text\":\"Sample help text\",\"sensor_parameters\":[],\"sensor_name\":\"sample_target/sample_category/sample_sensor\",\"quality_dimension\":\"sample_quality_dimension\",\"supports_grouping\":false,\"disabled\":false,\"exclude_from_kpi\":false,\"include_in_sla\":false,\"configured\":false,\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}]}],\"can_edit\":false,\"can_run_checks\":false,\"can_delete_data\":false}"

    ```


