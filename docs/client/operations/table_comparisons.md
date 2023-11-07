Manages the configuration of table comparisons between tables on the same or different data sources  


___  
## create_table_comparison_configuration  
Creates a new table comparison configuration added to the compared table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_configuration.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model|[TableComparisonConfigurationModel](\docs\client\models\table_comparisons\#tablecomparisonconfigurationmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisonconfigurations^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_schema.sample_table\",\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"check_type\":\"profiling\",\"grouping_columns\":[],\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"

    ```


___  
## create_table_comparison_monitoring_daily  
Creates a table comparison configuration using daily monitoring checks  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_monitoring_daily.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/monitoring/daily  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\models\table_comparisons\#tablecomparisonmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/monitoring/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0,\"fatal_difference_percent\":5.0},\"supports_compare_column_count\":false,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"partitioned\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"partitioned\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"

    ```


___  
## create_table_comparison_monitoring_monthly  
Creates a table comparison configuration using monthly monitoring checks  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_monitoring_monthly.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/monitoring/monthly  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\models\table_comparisons\#tablecomparisonmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/monitoring/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0,\"fatal_difference_percent\":5.0},\"supports_compare_column_count\":false,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"partitioned\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"partitioned\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"

    ```


___  
## create_table_comparison_partitioned_daily  
Creates a table comparison configuration using daily partitioned checks  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_partitioned_daily.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/partitioned/daily  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\models\table_comparisons\#tablecomparisonmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/partitioned/daily^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0,\"fatal_difference_percent\":5.0},\"supports_compare_column_count\":false,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"partitioned\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"partitioned\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"

    ```


___  
## create_table_comparison_partitioned_monthly  
Creates a table comparison configuration using monthly partitioned checks  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_partitioned_monthly.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/partitioned/monthly  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\models\table_comparisons\#tablecomparisonmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/partitioned/monthly^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0,\"fatal_difference_percent\":5.0},\"supports_compare_column_count\":false,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"partitioned\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"partitioned\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"

    ```


___  
## create_table_comparison_profiling  
Creates a table comparison configuration using profiling checks  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_profiling.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/profiling  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\models\table_comparisons\#tablecomparisonmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X POST http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/profiling^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0,\"fatal_difference_percent\":5.0},\"supports_compare_column_count\":false,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"partitioned\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"partitioned\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"

    ```


___  
## delete_table_comparison_configuration  
Deletes a table comparison configuration from a compared table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/delete_table_comparison_configuration.py)
  

**DELETE**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations/{tableComparisonConfigurationName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Reference table configuration name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl -X DELETE http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisonconfigurations/sample_table_comparison^
		-H "Accept: application/json"

    ```


___  
## get_table_comparison_configuration  
Returns a model of the table comparison configuration  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/get_table_comparison_configuration.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations/{tableComparisonConfigurationName}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_comparison_configuration_model](\docs\client\models\table_comparisons\#tablecomparisonconfigurationmodel)||[TableComparisonConfigurationModel](\docs\client\models\table_comparisons\#tablecomparisonconfigurationmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Reference table configuration name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisonconfigurations/sample_table_comparison^
		-H "Accept: application/json"

    ```


___  
## get_table_comparison_configurations  
Returns the list of table comparison configurations on a compared table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/get_table_comparison_configurations.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|table_comparison_configuration_model||List[[TableComparisonConfigurationModel](\docs\client\models\table_comparisons\#tablecomparisonconfigurationmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|[check_type](\docs\client\models\#checktype)|Optional check type filter (profiling, monitoring, partitioned).|[CheckType](\docs\client\models\#checktype)|false|
|[check_time_scale](\docs\client\models\#checktimescale)|Optional time scale filter for table comparisons specific to the monitoring and partitioned checks (values: daily or monthly).|[CheckTimeScale](\docs\client\models\#checktimescale)|false|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisonconfigurations^
		-H "Accept: application/json"

    ```


___  
## get_table_comparison_monitoring_daily  
Returns a model of the table comparison using daily monitoring checks (comparison once a day)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/get_table_comparison_monitoring_daily.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}/monitoring/daily  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_comparison_model](\docs\client\models\table_comparisons\#tablecomparisonmodel)||[TableComparisonModel](\docs\client\models\table_comparisons\#tablecomparisonmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/sample_table_comparison/monitoring/daily^
		-H "Accept: application/json"

    ```


___  
## get_table_comparison_monitoring_monthly  
Returns a model of the table comparison using monthly monitoring checks (comparison once a month)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/get_table_comparison_monitoring_monthly.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}/monitoring/monthly  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_comparison_model](\docs\client\models\table_comparisons\#tablecomparisonmodel)||[TableComparisonModel](\docs\client\models\table_comparisons\#tablecomparisonmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/sample_table_comparison/monitoring/monthly^
		-H "Accept: application/json"

    ```


___  
## get_table_comparison_partitioned_daily  
Returns a model of the table comparison using daily partition checks (comparing day to day)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/get_table_comparison_partitioned_daily.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}/partitioned/daily  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_comparison_model](\docs\client\models\table_comparisons\#tablecomparisonmodel)||[TableComparisonModel](\docs\client\models\table_comparisons\#tablecomparisonmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/sample_table_comparison/partitioned/daily^
		-H "Accept: application/json"

    ```


___  
## get_table_comparison_partitioned_monthly  
Returns a model of the table comparison using monthly partition checks (comparing month to month)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/get_table_comparison_partitioned_monthly.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}/partitioned/monthly  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_comparison_model](\docs\client\models\table_comparisons\#tablecomparisonmodel)||[TableComparisonModel](\docs\client\models\table_comparisons\#tablecomparisonmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/sample_table_comparison/partitioned/monthly^
		-H "Accept: application/json"

    ```


___  
## get_table_comparison_profiling  
Returns a model of the table comparison using profiling checks (comparison at any time)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/get_table_comparison_profiling.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/{tableComparisonConfigurationName}/profiling  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_comparison_model](\docs\client\models\table_comparisons\#tablecomparisonmodel)||[TableComparisonModel](\docs\client\models\table_comparisons\#tablecomparisonmodel)|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/sample_table_comparison/profiling^
		-H "Accept: application/json"

    ```


___  
## update_table_comparison_configuration  
Updates a table configuration configuration  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_configuration.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations/{tableComparisonConfigurationName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison model with the configuration of the tables to compare|[TableComparisonConfigurationModel](\docs\client\models\table_comparisons\#tablecomparisonconfigurationmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisonconfigurations/sample_table_comparison^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_schema.sample_table\",\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"check_type\":\"profiling\",\"grouping_columns\":[],\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"

    ```


___  
## update_table_comparison_monitoring_daily  
Updates a table comparison checks monitoring daily  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_monitoring_daily.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/monitoring/daily/{tableComparisonConfigurationName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\models\table_comparisons\#tablecomparisonmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/monitoring/daily/sample_table_comparison^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0,\"fatal_difference_percent\":5.0},\"supports_compare_column_count\":false,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"partitioned\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"partitioned\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"

    ```


___  
## update_table_comparison_monitoring_monthly  
Updates a table comparison checks monitoring monthly  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_monitoring_monthly.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/monitoring/monthly/{tableComparisonConfigurationName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\models\table_comparisons\#tablecomparisonmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/monitoring/monthly/sample_table_comparison^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0,\"fatal_difference_percent\":5.0},\"supports_compare_column_count\":false,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"partitioned\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"partitioned\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"

    ```


___  
## update_table_comparison_partitioned_daily  
Updates a table comparison checks partitioned daily (comparing day to day)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_partitioned_daily.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/partitioned/daily/{tableComparisonConfigurationName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\models\table_comparisons\#tablecomparisonmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/partitioned/daily/sample_table_comparison^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0,\"fatal_difference_percent\":5.0},\"supports_compare_column_count\":false,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"partitioned\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"partitioned\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"

    ```


___  
## update_table_comparison_partitioned_monthly  
Updates a table comparison checks partitioned monthly (comparing month to month)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_partitioned_monthly.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/partitioned/monthly/{tableComparisonConfigurationName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\models\table_comparisons\#tablecomparisonmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/partitioned/monthly/sample_table_comparison^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0,\"fatal_difference_percent\":5.0},\"supports_compare_column_count\":false,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"partitioned\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"partitioned\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"

    ```


___  
## update_table_comparison_profiling  
Updates a table comparison profiling checks  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_profiling.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/profiling/{tableComparisonConfigurationName}  
```



**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Is&nbsp;it required?&nbsp;|
|---------------------------------|-----------|-----------------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\models\table_comparisons\#tablecomparisonmodel)|false|




**Usage examples**  
=== "curl"
      
    ```bash
    curl -X PUT http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/tables/sample_table/tablecomparisons/profiling/sample_table_comparison^
		-H "Accept: application/json"^
		-H "Content-Type: application/json"^
		-d^
		"{\"table_comparison_configuration_name\":\"sample_table_comparison\",\"compared_connection\":\"unknown\",\"compared_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"reference_connection\":\"sample_connection\",\"reference_table\":{\"schema_name\":\"sample_schema\",\"table_name\":\"sample_table\"},\"grouping_columns\":[],\"default_compare_thresholds\":{\"warning_difference_percent\":0.0,\"error_difference_percent\":1.0,\"fatal_difference_percent\":5.0},\"supports_compare_column_count\":false,\"columns\":[],\"compare_table_run_checks_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"enabled\":true,\"checkType\":\"partitioned\",\"timeScale\":\"daily\",\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\"},\"compare_table_clean_data_job_template\":{\"connection\":\"unknown\",\"fullTableName\":\"sample_schema.sample_table\",\"deleteErrors\":true,\"deleteStatistics\":true,\"deleteCheckResults\":true,\"deleteSensorReadouts\":true,\"checkCategory\":\"comparisons\",\"tableComparisonName\":\"sample_table_comparison\",\"checkType\":\"partitioned\",\"timeGradient\":\"day\"},\"can_edit\":true,\"can_run_compare_checks\":true,\"can_delete_data\":true}"

    ```


