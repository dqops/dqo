# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## ProviderSensorYaml
Provider specific data quality sensor definition YAML schema for a data quality sensor configuration specification.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`api_version`</span>||*string*| | | |
|<span class="no-wrap-code ">`kind`</span>||*enum*|source<br/>table<br/>sensor<br/>provider_sensor<br/>rule<br/>check<br/>settings<br/>file_index<br/>dashboards<br/>default_schedules<br/>default_checks<br/>default_notifications<br/>| | |
|<span class="no-wrap-code ">[`spec`](./ProviderSensorYaml.md#providersensordefinitionspec)</span>||*[ProviderSensorDefinitionSpec](./ProviderSensorYaml.md#providersensordefinitionspec)*| | | |









___


## ProviderSensorDefinitionSpec
Specification (configuration) for a provider specific implementation of a data quality sensor or an SQL template.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`type`</span>|Sensor implementation type|*enum*|sql_template<br/>java_class<br/>| | |
|<span class="no-wrap-code ">`java_class_name`</span>|Java class name for a sensor runner that will execute the sensor. The &quot;type&quot; must be &quot;java_class&quot;.|*string*| | | |
|<span class="no-wrap-code ">`supports_grouping`</span>|The sensor supports grouping, using the GROUP BY clause in SQL. Sensors that support a GROUP BY condition can capture separate data quality scores for each data group. The default value is true, because most of the data quality sensor support grouping.|*boolean*| | | |
|<span class="no-wrap-code ">`supports_partitioned_checks`</span>|The sensor supports grouping by a partition date, using the GROUP BY clause in SQL. Sensors that support grouping by a partition_by_column could be used for partition checks, calculating separate data quality metrics for each daily/monthly partition. The default value is true, because most of the data quality sensor support partitioned checks.|*boolean*| | | |
|<span class="no-wrap-code ">`parameters`</span>|Additional provider specific sensor parameters|*Dict[string, string]*| | | |
|<span class="no-wrap-code ">`disable_merging_queries`</span>|Disables merging this sensor&#x27;s SQL with other sensors. When this parameter is &#x27;true&#x27;, the sensor&#x27;s SQL will be executed as an independent query.|*boolean*| | | |









___


