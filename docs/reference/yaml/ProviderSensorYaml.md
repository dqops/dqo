
## ProviderSensorDefinitionSpec  
Specification (configuration) for a provider specific implementation of a data quality sensor or an SQL template.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|type|Sensor implementation type|enum|sql_template<br/>java_class<br/>| | |
|java_class_name|Java class name for a sensor runner that will execute the sensor. The &quot;type&quot; must be &quot;java_class&quot;.|string| | | |
|supports_grouping|The sensor supports grouping, using the GROUP BY clause in SQL. Sensors that support a GROUP BY condition can capture separate data quality scores for each data group. The default value is true, because most of the data quality sensor support grouping.|boolean| | | |
|supports_partitioned_checks|The sensor supports grouping by a partition date, using the GROUP BY clause in SQL. Sensors that support grouping by a partition_by_column could be used for partition checks, calculating separate data quality metrics for each daily/monthly partition. The default value is true, because most of the data quality sensor support partitioned checks.|boolean| | | |
|disable_merging_queries|Disables merging this sensor&#x27;s SQL with other sensors. When this parameter is &#x27;true&#x27;, the sensor&#x27;s SQL will be executed as an independent query.|boolean| | | |









___  

## ProviderSensorYaml  
Provider specific data quality sensor definition YAML schema for a data quality sensor configuration specification.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|table<br/>default_schedules<br/>dashboards<br/>source<br/>sensor<br/>check<br/>default_checks<br/>rule<br/>file_index<br/>settings<br/>default_notifications<br/>provider_sensor<br/>| | |
|[spec](#providersensordefinitionspec)||[ProviderSensorDefinitionSpec](#providersensordefinitionspec)| | | |









___  

