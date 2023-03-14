
## ProviderSensorYaml  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|table<br/>dashboards<br/>source<br/>sensor<br/>rule<br/>file_index<br/>settings<br/>provider_sensor<br/>| | |
|[spec](#providersensordefinitionspec)||object| | | |

___  

## ProviderSensorDefinitionSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|type|Sensor implementation type|enum|sql_template<br/>java_class<br/>| | |
|java_class_name|Java class name for a sensor runner that will execute the sensor. The &quot;type&quot; must be &quot;java_class&quot;.|string| | | |
|supports_grouping|The sensor supports grouping (the GROUP BY clause in SQL), allowing to use the sensor for partition checks (to group each day of data and calculate a separate metric) or to use data streams segregation to group the results by additional columns. The default value is true, because most of the data quality sensor support grouping.|boolean| | | |

___  

