
## SensorDefinitionYaml  
Data quality sensor definition YAML schema for a data quality sensor specification.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|table<br/>dashboards<br/>source<br/>sensor<br/>check<br/>rule<br/>file_index<br/>settings<br/>provider_sensor<br/>| | |
|[spec](#sensordefinitionspec)||[SensorDefinitionSpec](#sensordefinitionspec)| | | |









___  

## SensorDefinitionSpec  
Data Quality sensor definition specification. Provides the configuration for a data quality sensor definition, sensor&#x27;s parameters, etc.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[fields](#parameterdefinitionslistspec)|List of fields that are parameters of a custom sensor. Those fields are used by the DQO UI to display the data quality check editing screens with proper UI controls for all required fields.|[ParameterDefinitionsListSpec](#parameterdefinitionslistspec)| | | |
|requires_event_timestamp|The data quality sensor depends on the configuration of the event timestamp column name on the analyzed table. When true, the name of the column that stores the event (transaction, etc.) timestamp must be specified in the timestamp_columns.event_timestamp_column field on the table.|boolean| | | |
|requires_ingestion_timestamp|The data quality sensor depends on the configuration of the ingestion timestamp column name on the analyzed table. When true, the name of the column that stores the ingestion (created_at, loaded_at, etc.) timestamp must be specified in the timestamp_columns.ingestion_timestamp_column field on the table.|boolean| | | |









___  

## ParameterDefinitionsListSpec  
List of parameter definitions - the parameters for custom sensors or custom rules.  
  








___  

## ParameterDefinitionSpec  
Defines a single field that is a sensor parameter or a rule parameter.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|field_name|Field name that matches the field name (snake_case) used in the YAML specification.|string| | | |
|display_name|Field display name that should be shown as a label for the control.|string| | | |
|help_text|Help text (full description) that will be shown to the user as a hint when the cursor is moved over the control.|string| | | |
|data_type|Parameter data type.|enum|date<br/>string<br/>enum<br/>string_list<br/>object<br/>column_name<br/>boolean<br/>integer<br/>double<br/>instant<br/>integer_list<br/>long<br/>| | |
|display_hint|UI control display hint.|enum|textarea<br/>| | |
|required|True when the value for the parameter must be provided.|boolean| | | |
|allowed_values|List of allowed values for a field that is of an enum type.|string_list| | | |
|sample_values|List of sample values. The sample values are used in the documentation or help messages.|string_list| | | |









___  

