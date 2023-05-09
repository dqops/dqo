
## ProviderSensorYaml  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|table<br/>dashboards<br/>source<br/>sensor<br/>check<br/>rule<br/>file_index<br/>settings<br/>provider_sensor<br/>| | |
|[spec](#providersensordefinitionspec)||object| | | |

___  

## ProviderSensorDefinitionSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|type|Sensor implementation type|enum|sql_template<br/>java_class<br/>| | |
|java_class_name|Java class name for a sensor runner that will execute the sensor. The &quot;type&quot; must be &quot;java_class&quot;.|string| | | |
|supports_grouping_by_data_stream|The sensor supports grouping by the data stream, using the GROUP BY clause in SQL. Sensors that support a GROUP BY condition can capture separate data quality scores for each data stream. The default value is true, because most of the data quality sensor support grouping.|boolean| | | |
|supports_partitioned_checks|The sensor supports grouping by a partition date, using the GROUP BY clause in SQL. Sensors that support grouping by a partition_by_column could be used for partition checks, calculating separate data quality metrics for each daily/monthly partition. The default value is true, because most of the data quality sensor support partitioned checks.|boolean| | | |

___  

