
## CheckDefinitionYaml  
Custom check specification that describes a pair of a sensor name and rule name.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|[kind](#specificationkind)||[SpecificationKind](#specificationkind)|table<br/>default_schedules<br/>dashboards<br/>source<br/>sensor<br/>check<br/>default_checks<br/>rule<br/>file_index<br/>settings<br/>default_notifications<br/>provider_sensor<br/>| | |
|[spec](#checkdefinitionspec)||[CheckDefinitionSpec](#checkdefinitionspec)| | | |









___  

## CheckDefinitionSpec  
Custom data quality check specification. Provides the custom check configuration which is a pair of a sensor name and a rule name.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sensor_name|Sensor name. It is a folder name inside the user&#x27;s home &#x27;sensors&#x27; folder or the DQOps Home (DQOps distribution) home/sensors folder. Sample sensor name: table/volume/row_count.|string| | | |
|rule_name|Rule name used for the check. It is a path to a custom rule python module that starts at the user&#x27;s home &#x27;rules&#x27; folder. The path should not end with the .py file extension. Sample rule: myrules/my_custom_rule.|string| | | |
|help_text|Help text that describes the data quality check.|string| | | |









___  
