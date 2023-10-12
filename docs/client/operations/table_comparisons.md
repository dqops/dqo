
## create_table_comparison_configuration  
Creates a new table comparison configuration added to the compared table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_configuration.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Table comparison configuration model|[TableComparisonConfigurationModel](\docs\client\operations\table_comparisons\#tablecomparisonconfigurationmodel)|false|


___  

## create_table_comparison_monitoring_daily  
Creates a table comparison configuration using daily monitoring checks  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_monitoring_daily.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/monitoring/daily  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\operations\table_comparisons\#tablecomparisonmodel)|false|


___  

## create_table_comparison_monitoring_monthly  
Creates a table comparison configuration using monthly monitoring checks  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_monitoring_monthly.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/monitoring/monthly  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\operations\table_comparisons\#tablecomparisonmodel)|false|


___  

## create_table_comparison_partitioned_daily  
Creates a table comparison configuration using daily partitioned checks  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_partitioned_daily.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/partitioned/daily  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\operations\table_comparisons\#tablecomparisonmodel)|false|


___  

## create_table_comparison_partitioned_monthly  
Creates a table comparison configuration using monthly partitioned checks  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_partitioned_monthly.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/partitioned/monthly  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\operations\table_comparisons\#tablecomparisonmodel)|false|


___  

## create_table_comparison_profiling  
Creates a table comparison configuration using profiling checks  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/create_table_comparison_profiling.py)
  

**POST**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/profiling  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\operations\table_comparisons\#tablecomparisonmodel)|false|


___  

## delete_table_comparison_configuration  
Deletes a table comparison configuration from a compared table  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/delete_table_comparison_configuration.py)
  

**DELETE**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations/{tableComparisonConfigurationName}  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Reference table configuration name|string|true|




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
|[table_comparison_configuration_model](\docs\client\operations\table_comparisons\#tablecomparisonconfigurationmodel)||[TableComparisonConfigurationModel](\docs\client\operations\table_comparisons\#tablecomparisonconfigurationmodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Reference table configuration name|string|true|




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
|[table_comparison_configuration_model](\docs\client\operations\table_comparisons\#tablecomparisonconfigurationmodel)||[TableComparisonConfigurationModel](\docs\client\operations\table_comparisons\#tablecomparisonconfigurationmodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|[check_type](\docs\client\models\#checktype)|Optional check type filter (profiling, monitoring, partitioned).|[CheckType](\docs\client\models\#checktype)|false|
|[check_time_scale](\docs\client\models\#checktimescale)|Optional time scale filter for table comparisons specific to the monitoring and partitioned checks (values: daily or monthly).|[CheckTimeScale](\docs\client\models\#checktimescale)|false|




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
|[table_comparison_model](\docs\client\operations\table_comparisons\#tablecomparisonmodel)||[TableComparisonModel](\docs\client\operations\table_comparisons\#tablecomparisonmodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|




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
|[table_comparison_model](\docs\client\operations\table_comparisons\#tablecomparisonmodel)||[TableComparisonModel](\docs\client\operations\table_comparisons\#tablecomparisonmodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|




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
|[table_comparison_model](\docs\client\operations\table_comparisons\#tablecomparisonmodel)||[TableComparisonModel](\docs\client\operations\table_comparisons\#tablecomparisonmodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|




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
|[table_comparison_model](\docs\client\operations\table_comparisons\#tablecomparisonmodel)||[TableComparisonModel](\docs\client\operations\table_comparisons\#tablecomparisonmodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|




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
|[table_comparison_model](\docs\client\operations\table_comparisons\#tablecomparisonmodel)||[TableComparisonModel](\docs\client\operations\table_comparisons\#tablecomparisonmodel)|




**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|




___  

## update_table_comparison_configuration  
Updates a table configuration configuration  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_configuration.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisonconfigurations/{tableComparisonConfigurationName}  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Table comparison model with the configuration of the tables to compare|[TableComparisonConfigurationModel](\docs\client\operations\table_comparisons\#tablecomparisonconfigurationmodel)|false|


___  

## update_table_comparison_monitoring_daily  
Updates a table comparison checks monitoring daily  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_monitoring_daily.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/monitoring/daily/{tableComparisonConfigurationName}  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\operations\table_comparisons\#tablecomparisonmodel)|false|


___  

## update_table_comparison_monitoring_monthly  
Updates a table comparison checks monitoring monthly  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_monitoring_monthly.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/monitoring/monthly/{tableComparisonConfigurationName}  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\operations\table_comparisons\#tablecomparisonmodel)|false|


___  

## update_table_comparison_partitioned_daily  
Updates a table comparison checks partitioned daily (comparing day to day)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_partitioned_daily.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/partitioned/daily/{tableComparisonConfigurationName}  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\operations\table_comparisons\#tablecomparisonmodel)|false|


___  

## update_table_comparison_partitioned_monthly  
Updates a table comparison checks partitioned monthly (comparing month to month)  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_partitioned_monthly.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/partitioned/monthly/{tableComparisonConfigurationName}  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\operations\table_comparisons\#tablecomparisonmodel)|false|


___  

## update_table_comparison_profiling  
Updates a table comparison profiling checks  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparisons/update_table_comparison_profiling.py)
  

**PUT**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/tablecomparisons/profiling/{tableComparisonConfigurationName}  
```



**The structure of this method is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|----------|
|connection_name|Connection name|string|true|
|schema_name|Schema name|string|true|
|table_name|Table name|string|true|
|table_comparison_configuration_name|Table comparison configuration name|string|true|




**Request body**  
  
|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------------------------|-----------|----------|
|Table comparison configuration model with the selected checks to use for comparison|[TableComparisonModel](\docs\client\operations\table_comparisons\#tablecomparisonmodel)|false|


___  

___  

## TableComparisonConfigurationModel  
Model that contains the basic information about a table comparison configuration that specifies how the current table could be compared to another table that is a source of truth for comparison.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|table_comparison_configuration_name|The name of the table comparison configuration that is defined in the &#x27;table_comparisons&#x27; node on the table specification.|string| | | |
|compared_connection|Compared connection name - the connection name to the data source that is compared (verified).|string| | | |
|[compared_table](\docs\client\operations\jobs\#physicaltablename)|The schema and table name of the compared table that is verified.|[PhysicalTableName](\docs\client\operations\jobs\#physicaltablename)| | | |
|reference_connection|Reference connection name - the connection name to the data source that has the reference data to compare to.|string| | | |
|[reference_table](\docs\client\operations\jobs\#physicaltablename)|The schema and table name of the reference table that has the expected data.|[PhysicalTableName](\docs\client\operations\jobs\#physicaltablename)| | | |
|check_type|The type of checks (profiling, monitoring, partitioned) that this check comparison configuration is applicable. The default value is &#x27;profiling&#x27;.|enum|profiling<br/>partitioned<br/>monitoring<br/>| | |
|time_scale|The time scale that this check comparison configuration is applicable. Supported values are &#x27;daily&#x27; and &#x27;monthly&#x27; for monitoring and partitioned checks or an empty value for profiling checks.|enum|daily<br/>monthly<br/>| | |
|can_edit|Boolean flag that decides if the current user can update or delete the table comparison.|boolean| | | |
|can_run_compare_checks|Boolean flag that decides if the current user can run comparison checks.|boolean| | | |
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean| | | |

___  

## CompareThresholdsModel  
Model with the custom compare threshold levels for raising data quality issues at different severity levels
 when the difference between the compared (tested) table and the reference table (the source of truth) exceed given
 thresholds as a percentage of difference between the actual value and the expected value from the reference table.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|warning_difference_percent|The percentage difference between the measure value on the compared table and the reference table that raises a warning severity data quality issue when the difference is bigger.|double| | | |
|error_difference_percent|The percentage difference between the measure value on the compared table and the reference table that raises an error severity data quality issue when the difference is bigger.|double| | | |
|fatal_difference_percent|The percentage difference between the measure value on the compared table and the reference table that raises a fatal severity data quality issue when the difference is bigger.|double| | | |

___  

## TableComparisonModel  
Model that contains the all editable information about a table-to-table comparison defined on a compared table.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|table_comparison_configuration_name|The name of the table comparison configuration that is defined in the &#x27;table_comparisons&#x27; node on the table specification.|string| | | |
|compared_connection|Compared connection name - the connection name to the data source that is compared (verified).|string| | | |
|[compared_table](\docs\client\operations\jobs\#physicaltablename)|The schema and table name of the compared table that is verified.|[PhysicalTableName](\docs\client\operations\jobs\#physicaltablename)| | | |
|reference_connection|Reference connection name - the connection name to the data source that has the reference data to compare to.|string| | | |
|[reference_table](\docs\client\operations\jobs\#physicaltablename)|The schema and table name of the reference table that has the expected data.|[PhysicalTableName](\docs\client\operations\jobs\#physicaltablename)| | | |
|[default_compare_thresholds](#comparethresholdsmodel)|The template of the compare thresholds that should be applied to all comparisons when the comparison is enabled.|[CompareThresholdsModel](#comparethresholdsmodel)| | | |
|[compare_row_count](#comparethresholdsmodel)|The row count comparison configuration.|[CompareThresholdsModel](#comparethresholdsmodel)| | | |
|[compare_column_count](#comparethresholdsmodel)|The column count comparison configuration.|[CompareThresholdsModel](#comparethresholdsmodel)| | | |
|supports_compare_column_count|Boolean flag that decides if this comparison type supports comparing the column count between tables. Partitioned table comparisons do not support comparing the column counts.|boolean| | | |
|[compare_table_run_checks_job_template](\docs\client\models\#checksearchfilters)|Configured parameters for the &quot;check run&quot; job that should be pushed to the job queue in order to run the table comparison checks for this table, using checks selected in this model.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)| | | |
|[compare_table_clean_data_job_template](\docs\client\operations\jobs\#deletestoreddataqueuejobparameters)|Configured parameters for the &quot;data clean&quot; job that after being supplied with a time range should be pushed to the job queue in order to remove stored check results for this table comparison.|[DeleteStoredDataQueueJobParameters](\docs\client\operations\jobs\#deletestoreddataqueuejobparameters)| | | |
|can_edit|Boolean flag that decides if the current user can update or delete the table comparison.|boolean| | | |
|can_run_compare_checks|Boolean flag that decides if the current user can run comparison checks.|boolean| | | |
|can_delete_data|Boolean flag that decides if the current user can delete data (results).|boolean| | | |

___  

