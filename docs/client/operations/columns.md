
## create_column  
Creates a new column (adds a column metadata to the table)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/create_column.py)
  

**POST**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Column specification|[ColumnSpec](\docs\client\operations\columns\#columnspec)|false|


___  

## delete_column  
Deletes a column from the table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/delete_column.py)
  

**DELETE**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[dqo_queue_job_id](\docs\client\models\#dqoqueuejobid)||[DqoQueueJobId](\docs\client\models\#dqoqueuejobid)|






___  

## get_column  
Returns the full column specification  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[column_model](\docs\client\operations\columns\#columnmodel)||[ColumnModel](\docs\client\operations\columns\#columnmodel)|






___  

## get_column_basic  
Returns the column specification  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_basic.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/basic  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[column_list_model](\docs\client\operations\columns\#columnlistmodel)||[ColumnListModel](\docs\client\operations\columns\#columnlistmodel)|






___  

## get_column_comments  
Return the list of comments assigned to a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_comments.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/comments  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[comment_spec](\docs\client\models\#commentspec)||[CommentSpec](\docs\client\models\#commentspec)|






___  

## get_column_labels  
Return the list of labels assigned to a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_labels.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/labels  
```





___  

## get_column_monitoring_checks_basic_model  
Return a simplistic UI friendly model of column level data quality monitoring on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_monitoring_checks_basic_model.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/model/basic  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_list_model](\docs\client\models\#checkcontainerlistmodel)||[CheckContainerListModel](\docs\client\models\#checkcontainerlistmodel)|






___  

## get_column_monitoring_checks_daily  
Return the configuration of daily column level data quality monitoring on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_monitoring_checks_daily.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/daily  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[column_daily_monitoring_check_categories_spec](\docs\client\operations\columns\#columndailymonitoringcheckcategoriesspec)||[ColumnDailyMonitoringCheckCategoriesSpec](\docs\client\operations\columns\#columndailymonitoringcheckcategoriesspec)|






___  

## get_column_monitoring_checks_model  
Return a UI friendly model of column level data quality monitoring on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_monitoring_checks_model.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/model  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|






___  

## get_column_monitoring_checks_model_filter  
Return a UI friendly model of column level data quality monitoring on a column filtered by category and check name  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_monitoring_checks_model_filter.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/model/filter/{checkCategory}/{checkName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|






___  

## get_column_monitoring_checks_monthly  
Return the configuration of monthly column level data quality monitoring on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_monitoring_checks_monthly.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/monthly  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[column_monthly_monitoring_check_categories_spec](\docs\client\operations\columns\#columnmonthlymonitoringcheckcategoriesspec)||[ColumnMonthlyMonitoringCheckCategoriesSpec](\docs\client\operations\columns\#columnmonthlymonitoringcheckcategoriesspec)|






___  

## get_column_partitioned_checks_basic_model  
Return a simplistic UI friendly model of column level data quality partitioned checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_partitioned_checks_basic_model.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model/basic  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_list_model](\docs\client\models\#checkcontainerlistmodel)||[CheckContainerListModel](\docs\client\models\#checkcontainerlistmodel)|






___  

## get_column_partitioned_checks_daily  
Return the configuration of daily column level data quality partitioned checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_partitioned_checks_daily.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/daily  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[column_daily_partitioned_check_categories_spec](\docs\client\operations\columns\#columndailypartitionedcheckcategoriesspec)||[ColumnDailyPartitionedCheckCategoriesSpec](\docs\client\operations\columns\#columndailypartitionedcheckcategoriesspec)|






___  

## get_column_partitioned_checks_model  
Return a UI friendly model of column level data quality partitioned checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_partitioned_checks_model.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|






___  

## get_column_partitioned_checks_model_filter  
Return a UI friendly model of column level data quality partitioned checks on a column, filtered by category and check name  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_partitioned_checks_model_filter.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model/filter/{checkCategory}/{checkName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|






___  

## get_column_partitioned_checks_monthly  
Return the configuration of monthly column level data quality partitioned checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_partitioned_checks_monthly.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/monthly  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[column_monthly_partitioned_check_categories_spec](\docs\client\operations\columns\#columnmonthlypartitionedcheckcategoriesspec)||[ColumnMonthlyPartitionedCheckCategoriesSpec](\docs\client\operations\columns\#columnmonthlypartitionedcheckcategoriesspec)|






___  

## get_column_profiling_checks  
Return the configuration of column level data quality profiling checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_profiling_checks.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[column_profiling_check_categories_spec](\docs\client\operations\columns\#columnprofilingcheckcategoriesspec)||[ColumnProfilingCheckCategoriesSpec](\docs\client\operations\columns\#columnprofilingcheckcategoriesspec)|






___  

## get_column_profiling_checks_basic_model  
Return a simplistic UI friendly model of column level data quality profiling checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_profiling_checks_basic_model.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/model/basic  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_list_model](\docs\client\models\#checkcontainerlistmodel)||[CheckContainerListModel](\docs\client\models\#checkcontainerlistmodel)|






___  

## get_column_profiling_checks_model  
Return a UI friendly model of data quality profiling checks on a column  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_profiling_checks_model.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/model  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|






___  

## get_column_profiling_checks_model_filter  
Return a UI friendly model of data quality profiling checks on a column filtered by category and check name  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_profiling_checks_model_filter.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/model/filter/{checkCategory}/{checkName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_container_model](\docs\client\models\#checkcontainermodel)||[CheckContainerModel](\docs\client\models\#checkcontainermodel)|






___  

## get_column_statistics  
Returns the column specification with the metrics captured by the most recent statistics collection.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_column_statistics.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/statistics  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[column_statistics_model](\docs\client\operations\columns\#columnstatisticsmodel)||[ColumnStatisticsModel](\docs\client\operations\columns\#columnstatisticsmodel)|






___  

## get_columns  
Returns a list of columns inside a table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_columns.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[column_list_model](\docs\client\operations\columns\#columnlistmodel)||[ColumnListModel](\docs\client\operations\columns\#columnlistmodel)|






___  

## get_columns_statistics  
Returns a list of columns inside a table with the metrics captured by the most recent statistics collection.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/get_columns_statistics.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/statistics  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_columns_statistics_model](\docs\client\operations\columns\#tablecolumnsstatisticsmodel)||[TableColumnsStatisticsModel](\docs\client\operations\columns\#tablecolumnsstatisticsmodel)|






___  

## update_column  
Updates an existing column specification, changing all the fields (even the column level data quality checks).  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column.py)
  

**PUT**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Column specification|[ColumnSpec](\docs\client\operations\columns\#columnspec)|false|


___  

## update_column_basic  
Updates an existing column, changing only the basic information like the expected data type (the data type snapshot).  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_basic.py)
  

**PUT**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/basic  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Basic column information to store|[ColumnListModel](\docs\client\operations\columns\#columnlistmodel)|false|


___  

## update_column_comments  
Updates the list of comments assigned to a column.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_comments.py)
  

**PUT**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/comments  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|List of comments to stored (replaced) on the column or an empty object to clear the list of assigned comments on the column|[CommentSpec](\docs\client\models\#commentspec)|false|


___  

## update_column_labels  
Updates the list of labels assigned to a column.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_labels.py)
  

**PUT**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/labels  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|List of labels to stored (replaced) on the column or an empty object to clear the list of assigned labels on the column|[]()|false|


___  

## update_column_monitoring_checks_daily  
Updates configuration of daily column level data quality monitoring on a column.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_monitoring_checks_daily.py)
  

**PUT**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/daily  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Configuration of daily column level data quality monitoring to configure on a column or an empty object to clear the list of assigned daily data quality monitoring on the column|[ColumnDailyMonitoringCheckCategoriesSpec](\docs\client\operations\columns\#columndailymonitoringcheckcategoriesspec)|false|


___  

## update_column_monitoring_checks_model  
Updates configuration of column level data quality monitoring on a column, for a given time scale, from a UI friendly model.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_monitoring_checks_model.py)
  

**PUT**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/{timeScale}/model  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Model with the changes to be applied to the data quality monitoring configuration|[CheckContainerModel](\docs\client\models\#checkcontainermodel)|false|


___  

## update_column_monitoring_checks_monthly  
Updates configuration of monthly column level data quality monitoring checks on a column.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_monitoring_checks_monthly.py)
  

**PUT**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/monitoring/monthly  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Configuration of monthly column level data quality monitoring to configure on a column or an empty object to clear the list of assigned monthly data quality monitoring on the column|[ColumnMonthlyMonitoringCheckCategoriesSpec](\docs\client\operations\columns\#columnmonthlymonitoringcheckcategoriesspec)|false|


___  

## update_column_partitioned_checks_daily  
Updates configuration of daily column level data quality partitioned checks on a column.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_partitioned_checks_daily.py)
  

**PUT**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/daily  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Configuration of daily column level data quality partitioned checks to configure on a column or an empty object to clear the list of assigned data quality partitioned checks on the column|[ColumnDailyPartitionedCheckCategoriesSpec](\docs\client\operations\columns\#columndailypartitionedcheckcategoriesspec)|false|


___  

## update_column_partitioned_checks_model  
Updates configuration of column level data quality partitioned checks on a column, for a given time scale, from a UI friendly model.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_partitioned_checks_model.py)
  

**PUT**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/{timeScale}/model  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Model with the changes to be applied to the data quality partitioned checks configuration|[CheckContainerModel](\docs\client\models\#checkcontainermodel)|false|


___  

## update_column_partitioned_checks_monthly  
Updates configuration of monthly column level data quality partitioned checks on a column.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_partitioned_checks_monthly.py)
  

**PUT**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/partitioned/monthly  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Configuration of monthly column level data quality partitioned checks to configure on a column or an empty object to clear the list of assigned data quality partitioned checks on the column|[ColumnMonthlyPartitionedCheckCategoriesSpec](\docs\client\operations\columns\#columnmonthlypartitionedcheckcategoriesspec)|false|


___  

## update_column_profiling_checks  
Updates configuration of column level data quality profiling checks on a column.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_profiling_checks.py)
  

**PUT**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Configuration of column level data quality profiling checks to configure on a column or an empty object to clear the list of assigned data quality profiling checks on the column|[ColumnProfilingCheckCategoriesSpec](\docs\client\operations\columns\#columnprofilingcheckcategoriesspec)|false|


___  

## update_column_profiling_checks_model  
Updates configuration of column level data quality profiling checks on a column from a UI friendly model.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/columns/update_column_profiling_checks_model.py)
  

**PUT**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/columns/{columnName}/profiling/model  
```





**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Model with the changes to be applied to the data quality profiling checks configuration|[CheckContainerModel](\docs\client\models\#checkcontainermodel)|false|


___  

___  

## ColumnComparisonDailyPartitionedChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|access_order||boolean| | | |
|size||integer| | | |
|mod_count||integer| | | |
|threshold||integer| | | |

___  

## ColumnDailyPartitionedCheckCategoriesSpec  
Container of data quality partitioned checks on a column level that are checking numeric values at a daily level.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnnullsdailypartitionedchecksspec)|Daily partitioned checks of nulls in the column|[ColumnNullsDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnnullsdailypartitionedchecksspec)| | | |
|[numeric](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnnumericdailypartitionedchecksspec)|Daily partitioned checks of numeric in the column|[ColumnNumericDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnnumericdailypartitionedchecksspec)| | | |
|[strings](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnstringsdailypartitionedchecksspec)|Daily partitioned checks of strings in the column|[ColumnStringsDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnstringsdailypartitionedchecksspec)| | | |
|[uniqueness](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnuniquenessdailypartitionedchecksspec)|Daily partitioned checks of uniqueness in the column|[ColumnUniquenessDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnuniquenessdailypartitionedchecksspec)| | | |
|[datetime](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columndatetimedailypartitionedchecksspec)|Daily partitioned checks of datetime in the column|[ColumnDatetimeDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columndatetimedailypartitionedchecksspec)| | | |
|[pii](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnpiidailypartitionedchecksspec)|Daily partitioned checks of Personal Identifiable Information (PII) in the column|[ColumnPiiDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnpiidailypartitionedchecksspec)| | | |
|[sql](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnsqldailypartitionedchecksspec)|Daily partitioned checks using custom SQL expressions evaluated on the column|[ColumnSqlDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnsqldailypartitionedchecksspec)| | | |
|[bool](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnbooldailypartitionedchecksspec)|Daily partitioned checks for booleans in the column|[ColumnBoolDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnbooldailypartitionedchecksspec)| | | |
|[integrity](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnintegritydailypartitionedchecksspec)|Daily partitioned checks for integrity in the column|[ColumnIntegrityDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnintegritydailypartitionedchecksspec)| | | |
|[accuracy](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnaccuracydailypartitionedchecksspec)|Daily partitioned checks for accuracy in the column|[ColumnAccuracyDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnaccuracydailypartitionedchecksspec)| | | |
|[datatype](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columndatatypedailypartitionedchecksspec)|Daily partitioned checks for datatype in the column|[ColumnDatatypeDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columndatatypedailypartitionedchecksspec)| | | |
|[anomaly](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnanomalydailypartitionedchecksspec)|Daily partitioned checks for anomaly in the column|[ColumnAnomalyDailyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-daily-partitioned-checks\#columnanomalydailypartitionedchecksspec)| | | |
|[comparisons](#columncomparisondailypartitionedchecksspecmap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonDailyPartitionedChecksSpecMap](#columncomparisondailypartitionedchecksspecmap)| | | |
|[custom](\docs\client\operations\tables\#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](\docs\client\operations\tables\#customcheckspecmap)| | | |

___  

## ColumnComparisonProfilingChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|access_order||boolean| | | |
|size||integer| | | |
|mod_count||integer| | | |
|threshold||integer| | | |

___  

## ColumnProfilingCheckCategoriesSpec  
Container of column level, preconfigured checks.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](\docs\reference\yaml\profiling\column-profiling-checks\#columnnullsprofilingchecksspec)|Configuration of column level checks that verify nulls and blanks.|[ColumnNullsProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnnullsprofilingchecksspec)| | | |
|[numeric](\docs\reference\yaml\profiling\column-profiling-checks\#columnnumericprofilingchecksspec)|Configuration of column level checks that verify negative values.|[ColumnNumericProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnnumericprofilingchecksspec)| | | |
|[strings](\docs\reference\yaml\profiling\column-profiling-checks\#columnstringsprofilingchecksspec)|Configuration of strings checks on a column level.|[ColumnStringsProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnstringsprofilingchecksspec)| | | |
|[uniqueness](\docs\reference\yaml\profiling\column-profiling-checks\#columnuniquenessprofilingchecksspec)|Configuration of uniqueness checks on a column level.|[ColumnUniquenessProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnuniquenessprofilingchecksspec)| | | |
|[datetime](\docs\reference\yaml\profiling\column-profiling-checks\#columndatetimeprofilingchecksspec)|Configuration of datetime checks on a column level.|[ColumnDatetimeProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columndatetimeprofilingchecksspec)| | | |
|[pii](\docs\reference\yaml\profiling\column-profiling-checks\#columnpiiprofilingchecksspec)|Configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnpiiprofilingchecksspec)| | | |
|[sql](\docs\reference\yaml\profiling\column-profiling-checks\#columnsqlprofilingchecksspec)|Configuration of SQL checks that use custom SQL aggregated expressions and SQL conditions in data quality checks.|[ColumnSqlProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnsqlprofilingchecksspec)| | | |
|[bool](\docs\reference\yaml\profiling\column-profiling-checks\#columnboolprofilingchecksspec)|Configuration of booleans checks on a column level.|[ColumnBoolProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnboolprofilingchecksspec)| | | |
|[integrity](\docs\reference\yaml\profiling\column-profiling-checks\#columnintegrityprofilingchecksspec)|Configuration of integrity checks on a column level.|[ColumnIntegrityProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnintegrityprofilingchecksspec)| | | |
|[accuracy](\docs\reference\yaml\profiling\column-profiling-checks\#columnaccuracyprofilingchecksspec)|Configuration of accuracy checks on a column level.|[ColumnAccuracyProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnaccuracyprofilingchecksspec)| | | |
|[datatype](\docs\reference\yaml\profiling\column-profiling-checks\#columndatatypeprofilingchecksspec)|Configuration of datatype checks on a column level.|[ColumnDatatypeProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columndatatypeprofilingchecksspec)| | | |
|[anomaly](\docs\reference\yaml\profiling\column-profiling-checks\#columnanomalyprofilingchecksspec)|Configuration of anomaly checks on a column level.|[ColumnAnomalyProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnanomalyprofilingchecksspec)| | | |
|[schema](\docs\reference\yaml\profiling\column-profiling-checks\#columnschemaprofilingchecksspec)|Configuration of schema checks on a column level.|[ColumnSchemaProfilingChecksSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnschemaprofilingchecksspec)| | | |
|[comparisons](#columncomparisonprofilingchecksspecmap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonProfilingChecksSpecMap](#columncomparisonprofilingchecksspecmap)| | | |
|[custom](\docs\client\operations\tables\#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](\docs\client\operations\tables\#customcheckspecmap)| | | |

___  

## ColumnComparisonDailyMonitoringChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|access_order||boolean| | | |
|size||integer| | | |
|mod_count||integer| | | |
|threshold||integer| | | |

___  

## ColumnDailyMonitoringCheckCategoriesSpec  
Container of column level daily monitoring checks. Contains categories of daily monitoring checks.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnnullsdailymonitoringchecksspec)|Daily monitoring checks of nulls in the column|[ColumnNullsDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnnullsdailymonitoringchecksspec)| | | |
|[numeric](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnnumericdailymonitoringchecksspec)|Daily monitoring checks of numeric in the column|[ColumnNumericDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnnumericdailymonitoringchecksspec)| | | |
|[strings](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnstringsdailymonitoringchecksspec)|Daily monitoring checks of strings in the column|[ColumnStringsDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnstringsdailymonitoringchecksspec)| | | |
|[uniqueness](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnuniquenessdailymonitoringchecksspec)|Daily monitoring checks of uniqueness in the column|[ColumnUniquenessDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnuniquenessdailymonitoringchecksspec)| | | |
|[datetime](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columndatetimedailymonitoringchecksspec)|Daily monitoring checks of datetime in the column|[ColumnDatetimeDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columndatetimedailymonitoringchecksspec)| | | |
|[pii](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnpiidailymonitoringchecksspec)|Daily monitoring checks of Personal Identifiable Information (PII) in the column|[ColumnPiiDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnpiidailymonitoringchecksspec)| | | |
|[sql](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnsqldailymonitoringchecksspec)|Daily monitoring checks of custom SQL checks in the column|[ColumnSqlDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnsqldailymonitoringchecksspec)| | | |
|[bool](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnbooldailymonitoringchecksspec)|Daily monitoring checks of booleans in the column|[ColumnBoolDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnbooldailymonitoringchecksspec)| | | |
|[integrity](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnintegritydailymonitoringchecksspec)|Daily monitoring checks of integrity in the column|[ColumnIntegrityDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnintegritydailymonitoringchecksspec)| | | |
|[accuracy](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnaccuracydailymonitoringchecksspec)|Daily monitoring checks of accuracy in the column|[ColumnAccuracyDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnaccuracydailymonitoringchecksspec)| | | |
|[datatype](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columndatatypedailymonitoringchecksspec)|Daily monitoring checks of datatype in the column|[ColumnDatatypeDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columndatatypedailymonitoringchecksspec)| | | |
|[anomaly](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnanomalydailymonitoringchecksspec)|Daily monitoring checks of anomaly in the column|[ColumnAnomalyDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnanomalydailymonitoringchecksspec)| | | |
|[schema](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnschemadailymonitoringchecksspec)|Daily monitoring column schema checks|[ColumnSchemaDailyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-daily-monitoring-checks\#columnschemadailymonitoringchecksspec)| | | |
|[comparisons](#columncomparisondailymonitoringchecksspecmap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonDailyMonitoringChecksSpecMap](#columncomparisondailymonitoringchecksspecmap)| | | |
|[custom](\docs\client\operations\tables\#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](\docs\client\operations\tables\#customcheckspecmap)| | | |

___  

## ColumnComparisonMonthlyMonitoringChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|access_order||boolean| | | |
|size||integer| | | |
|mod_count||integer| | | |
|threshold||integer| | | |

___  

## ColumnMonthlyMonitoringCheckCategoriesSpec  
Container of column level monthly monitoring checks. Contains categories of monthly monitoring checks.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnnullsmonthlymonitoringchecksspec)|Monthly monitoring checks of nulls in the column|[ColumnNullsMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnnullsmonthlymonitoringchecksspec)| | | |
|[numeric](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnnumericmonthlymonitoringchecksspec)|Monthly monitoring checks of numeric in the column|[ColumnNumericMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnnumericmonthlymonitoringchecksspec)| | | |
|[strings](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnstringsmonthlymonitoringchecksspec)|Monthly monitoring checks of strings in the column|[ColumnStringsMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnstringsmonthlymonitoringchecksspec)| | | |
|[uniqueness](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnuniquenessmonthlymonitoringchecksspec)|Monthly monitoring checks of uniqueness in the column|[ColumnUniquenessMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnuniquenessmonthlymonitoringchecksspec)| | | |
|[datetime](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columndatetimemonthlymonitoringchecksspec)|Monthly monitoring checks of datetime in the column|[ColumnDatetimeMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columndatetimemonthlymonitoringchecksspec)| | | |
|[pii](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnpiimonthlymonitoringchecksspec)|Monthly monitoring checks of Personal Identifiable Information (PII) in the column|[ColumnPiiMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnpiimonthlymonitoringchecksspec)| | | |
|[sql](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnsqlmonthlymonitoringchecksspec)|Monthly monitoring checks of custom SQL checks in the column|[ColumnSqlMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnsqlmonthlymonitoringchecksspec)| | | |
|[bool](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnboolmonthlymonitoringchecksspec)|Monthly monitoring checks of booleans in the column|[ColumnBoolMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnboolmonthlymonitoringchecksspec)| | | |
|[integrity](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnintegritymonthlymonitoringchecksspec)|Monthly monitoring checks of integrity in the column|[ColumnIntegrityMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnintegritymonthlymonitoringchecksspec)| | | |
|[accuracy](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnaccuracymonthlymonitoringchecksspec)|Monthly monitoring checks of accuracy in the column|[ColumnAccuracyMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnaccuracymonthlymonitoringchecksspec)| | | |
|[datatype](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columndatatypemonthlymonitoringchecksspec)|Monthly monitoring checks of datatype in the column|[ColumnDatatypeMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columndatatypemonthlymonitoringchecksspec)| | | |
|[anomaly](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnanomalymonthlymonitoringchecksspec)|Monthly monitoring checks of anomaly in the column|[ColumnAnomalyMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnanomalymonthlymonitoringchecksspec)| | | |
|[schema](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnschemamonthlymonitoringchecksspec)|Monthly monitoring column schema checks|[ColumnSchemaMonthlyMonitoringChecksSpec](\docs\reference\yaml\monitoring\column-monthly-monitoring-checks\#columnschemamonthlymonitoringchecksspec)| | | |
|[comparisons](#columncomparisonmonthlymonitoringchecksspecmap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonMonthlyMonitoringChecksSpecMap](#columncomparisonmonthlymonitoringchecksspecmap)| | | |
|[custom](\docs\client\operations\tables\#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](\docs\client\operations\tables\#customcheckspecmap)| | | |

___  

## ColumnMonitoringChecksRootSpec  
Container of column level monitoring, divided by the time window (daily, monthly, etc.)  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](\docs\client\operations\columns\#columndailymonitoringcheckcategoriesspec)|Configuration of daily monitoring evaluated at a column level.|[ColumnDailyMonitoringCheckCategoriesSpec](\docs\client\operations\columns\#columndailymonitoringcheckcategoriesspec)| | | |
|[monthly](\docs\client\operations\columns\#columnmonthlymonitoringcheckcategoriesspec)|Configuration of monthly monitoring evaluated at a column level.|[ColumnMonthlyMonitoringCheckCategoriesSpec](\docs\client\operations\columns\#columnmonthlymonitoringcheckcategoriesspec)| | | |

___  

## ColumnComparisonMonthlyPartitionedChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|access_order||boolean| | | |
|size||integer| | | |
|mod_count||integer| | | |
|threshold||integer| | | |

___  

## ColumnMonthlyPartitionedCheckCategoriesSpec  
Container of data quality partitioned checks on a column level that are checking numeric values at a monthly level.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnnullsmonthlypartitionedchecksspec)|Monthly partitioned checks of nulls values in the column|[ColumnNullsMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnnullsmonthlypartitionedchecksspec)| | | |
|[numeric](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnnumericmonthlypartitionedchecksspec)|Monthly partitioned checks of numeric values in the column|[ColumnNumericMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnnumericmonthlypartitionedchecksspec)| | | |
|[strings](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnstringsmonthlypartitionedchecksspec)|Monthly partitioned checks of strings values in the column|[ColumnStringsMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnstringsmonthlypartitionedchecksspec)| | | |
|[uniqueness](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnuniquenessmonthlypartitionedchecksspec)|Monthly partitioned checks of uniqueness values in the column|[ColumnUniquenessMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnuniquenessmonthlypartitionedchecksspec)| | | |
|[datetime](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columndatetimemonthlypartitionedchecksspec)|Monthly partitioned checks of datetime values in the column|[ColumnDatetimeMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columndatetimemonthlypartitionedchecksspec)| | | |
|[pii](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnpiimonthlypartitionedchecksspec)|Monthly partitioned checks of Personal Identifiable Information (PII) in the column|[ColumnPiiMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnpiimonthlypartitionedchecksspec)| | | |
|[sql](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnsqlmonthlypartitionedchecksspec)|Monthly partitioned checks using custom SQL expressions and conditions on the column|[ColumnSqlMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnsqlmonthlypartitionedchecksspec)| | | |
|[bool](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnboolmonthlypartitionedchecksspec)|Monthly partitioned checks for booleans in the column|[ColumnBoolMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnboolmonthlypartitionedchecksspec)| | | |
|[integrity](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnintegritymonthlypartitionedchecksspec)|Monthly partitioned checks for integrity in the column|[ColumnIntegrityMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnintegritymonthlypartitionedchecksspec)| | | |
|[accuracy](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnaccuracymonthlypartitionedchecksspec)|Monthly partitioned checks for accuracy in the column|[ColumnAccuracyMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnaccuracymonthlypartitionedchecksspec)| | | |
|[datatype](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columndatatypemonthlypartitionedchecksspec)|Monthly partitioned checks for datatype in the column|[ColumnDatatypeMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columndatatypemonthlypartitionedchecksspec)| | | |
|[anomaly](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnanomalymonthlypartitionedchecksspec)|Monthly partitioned checks for anomaly in the column|[ColumnAnomalyMonthlyPartitionedChecksSpec](\docs\reference\yaml\partitioned\column-monthly-partitioned-checks\#columnanomalymonthlypartitionedchecksspec)| | | |
|[comparisons](#columncomparisonmonthlypartitionedchecksspecmap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonMonthlyPartitionedChecksSpecMap](#columncomparisonmonthlypartitionedchecksspecmap)| | | |
|[custom](\docs\client\operations\tables\#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](\docs\client\operations\tables\#customcheckspecmap)| | | |

___  

## ColumnPartitionedChecksRootSpec  
Container of column level partitioned checks, divided by the time window (daily, monthly, etc.)  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](\docs\client\operations\columns\#columndailypartitionedcheckcategoriesspec)|Configuration of day partitioned data quality checks evaluated at a column level.|[ColumnDailyPartitionedCheckCategoriesSpec](\docs\client\operations\columns\#columndailypartitionedcheckcategoriesspec)| | | |
|[monthly](\docs\client\operations\columns\#columnmonthlypartitionedcheckcategoriesspec)|Configuration of monthly partitioned data quality checks evaluated at a column level.|[ColumnMonthlyPartitionedCheckCategoriesSpec](\docs\client\operations\columns\#columnmonthlypartitionedcheckcategoriesspec)| | | |

___  

## ColumnSpec  
Column specification that identifies a single column.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|disabled|Disables all data quality checks on the column. Data quality checks will not be executed.|boolean| | | |
|sql_expression|SQL expression used for calculated fields or when additional column value transformation is required before the column could be used analyzed in data quality checks (data type conversion, transformation). It should be an SQL expression using the SQL language of the analyzed database type. Use replacement tokens {table} to replace the content with the full table name, {alias} to replace the content with the table alias of an analyzed table or {column} to replace the content with the analyzed column name. An example to extract a value from a string column that stores a JSON in PostgreSQL: &quot;{column}::json-&gt;&#x27;address&#x27;-&gt;&#x27;zip&#x27;&quot;.|string| | | |
|[type_snapshot](\docs\reference\yaml\tableyaml\#columntypesnapshotspec)|Column data type that was retrieved when the table metadata was imported.|[ColumnTypeSnapshotSpec](\docs\reference\yaml\tableyaml\#columntypesnapshotspec)| | | |
|[profiling_checks](\docs\client\operations\columns\#columnprofilingcheckcategoriesspec)|Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.|[ColumnProfilingCheckCategoriesSpec](\docs\client\operations\columns\#columnprofilingcheckcategoriesspec)| | | |
|[monitoring_checks](#columnmonitoringchecksrootspec)|Configuration of column level monitoring checks. Monitoring are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A monitoring stores only the most recent data quality check result for each period of time.|[ColumnMonitoringChecksRootSpec](#columnmonitoringchecksrootspec)| | | |
|[partitioned_checks](#columnpartitionedchecksrootspec)|Configuration of column level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.|[ColumnPartitionedChecksRootSpec](#columnpartitionedchecksrootspec)| | | |
|[statistics](\docs\reference\yaml\tableyaml\#columnstatisticscollectorsrootcategoriesspec)|Custom configuration of a column level statistics collector (a basic profiler). Enables customization of the statistics collector settings when the collector is analysing this column.|[ColumnStatisticsCollectorsRootCategoriesSpec](\docs\reference\yaml\tableyaml\#columnstatisticscollectorsrootcategoriesspec)| | | |
|[labels](\docs\client\operations\connections\#labelsetspec)|Custom labels that were assigned to the column. Labels are used for searching for columns when filtered data quality checks are executed.|[LabelSetSpec](\docs\client\operations\connections\#labelsetspec)| | | |
|[comments](\docs\client\operations\connections\#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](\docs\client\operations\connections\#commentslistspec)| | | |

___  

## ColumnModel  
Table model that returns the specification of a single column in the REST Api.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name|Connection name.|string| | | |
|[table](\docs\client\operations\jobs\#physicaltablename)|Physical table name including the schema and table names.|[PhysicalTableName](\docs\client\operations\jobs\#physicaltablename)| | | |
|column_name|Column name.|string| | | |
|column_hash|Column hash that identifies the column using a unique hash code.|long| | | |
|[spec](\docs\client\operations\columns\#columnspec)|Full column specification.|[ColumnSpec](\docs\client\operations\columns\#columnspec)| | | |
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean| | | |

___  

## TableColumnsStatisticsModel  
Model that returns a summary of the column statistics (the basic profiling results) for a single table, showing statistics for all columns.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name|Connection name.|string| | | |
|[table](\docs\client\operations\jobs\#physicaltablename)|Physical table name including the schema and table names.|[PhysicalTableName](\docs\client\operations\jobs\#physicaltablename)| | | |
|[collect_column_statistics_job_template](\docs\client\operations\jobs\#statisticscollectorsearchfilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors for all columns on this table.|[StatisticsCollectorSearchFilters](\docs\client\operations\jobs\#statisticscollectorsearchfilters)| | | |
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean| | | |

___  

## ColumnStatisticsModel  
Column model that returns the basic fields from a column specification and a summary of the most recent statistics collection.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name|Connection name.|string| | | |
|[table](\docs\client\operations\jobs\#physicaltablename)|Physical table name including the schema and table names.|[PhysicalTableName](\docs\client\operations\jobs\#physicaltablename)| | | |
|column_name|Column name.|string| | | |
|column_hash|Column hash that identifies the column using a unique hash code.|long| | | |
|disabled|Disables all data quality checks on the column. Data quality checks will not be executed.|boolean| | | |
|has_any_configured_checks|True when the column has any checks configured.|boolean| | | |
|[type_snapshot](\docs\reference\yaml\tableyaml\#columntypesnapshotspec)|Column data type that was retrieved when the table metadata was imported.|[ColumnTypeSnapshotSpec](\docs\reference\yaml\tableyaml\#columntypesnapshotspec)| | | |
|[collect_column_statistics_job_template](\docs\client\operations\jobs\#statisticscollectorsearchfilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collectors for this column|[StatisticsCollectorSearchFilters](\docs\client\operations\jobs\#statisticscollectorsearchfilters)| | | |
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean| | | |

___  

## ColumnListModel  
Column list model that returns the basic fields from a column specification, excluding nested nodes like a list of activated checks.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name|Connection name.|string| | | |
|[table](\docs\client\operations\jobs\#physicaltablename)|Physical table name including the schema and table names.|[PhysicalTableName](\docs\client\operations\jobs\#physicaltablename)| | | |
|column_name|Column names.|string| | | |
|sql_expression|SQL expression.|string| | | |
|column_hash|Column hash that identifies the column using a unique hash code.|long| | | |
|disabled|Disables all data quality checks on the column. Data quality checks will not be executed.|boolean| | | |
|has_any_configured_checks|True when the column has any checks configured.|boolean| | | |
|has_any_configured_profiling_checks|True when the column has any profiling checks configured.|boolean| | | |
|has_any_configured_monitoring_checks|True when the column has any monitoring checks configured.|boolean| | | |
|has_any_configured_partition_checks|True when the column has any partition checks configured.|boolean| | | |
|[type_snapshot](\docs\reference\yaml\tableyaml\#columntypesnapshotspec)|Column data type that was retrieved when the table metadata was imported.|[ColumnTypeSnapshotSpec](\docs\reference\yaml\tableyaml\#columntypesnapshotspec)| | | |
|[run_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run all checks within this column.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)| | | |
|[run_profiling_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run profiling checks within this column.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)| | | |
|[run_monitoring_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run monitoring checks within this column.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)| | | |
|[run_partition_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run partition partitioned checks within this column.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)| | | |
|[collect_statistics_job_template](\docs\client\operations\jobs\#statisticscollectorsearchfilters)|Configured parameters for the &quot;collect statistics&quot; job that should be pushed to the job queue in order to run all statistics collector within this column.|[StatisticsCollectorSearchFilters](\docs\client\operations\jobs\#statisticscollectorsearchfilters)| | | |
|[data_clean_job_template](\docs\client\operations\jobs\#deletestoreddataqueuejobparameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored results connected with this column.|[DeleteStoredDataQueueJobParameters](\docs\client\operations\jobs\#deletestoreddataqueuejobparameters)| | | |
|can_edit|Boolean flag that decides if the current user can update or delete the column.|boolean| | | |
|can_collect_statistics|Boolean flag that decides if the current user can collect statistics.|boolean| | | |
|can_run_checks|Boolean flag that decides if the current user can run checks.|boolean| | | |
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean| | | |

___  

