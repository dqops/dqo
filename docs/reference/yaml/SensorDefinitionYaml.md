
## SensorDefinitionSpec  
Data Quality sensor definition specification. Provides the configuration for a data quality sensor definition, sensor&#x27;s parameters, etc.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[fields](#parameterdefinitionslistspec)|List of fields that are parameters of a custom sensor. Those fields are used by the DQOps UI to display the data quality check editing screens with proper UI controls for all required fields.|[ParameterDefinitionsListSpec](#parameterdefinitionslistspec)| | | |
|requires_event_timestamp|The data quality sensor depends on the configuration of the event timestamp column name on the analyzed table. When true, the name of the column that stores the event (transaction, etc.) timestamp must be specified in the timestamp_columns.event_timestamp_column field on the table.|boolean| | | |
|requires_ingestion_timestamp|The data quality sensor depends on the configuration of the ingestion timestamp column name on the analyzed table. When true, the name of the column that stores the ingestion (created_at, loaded_at, etc.) timestamp must be specified in the timestamp_columns.ingestion_timestamp_column field on the table.|boolean| | | |
|default_value|Default value that is used when the sensor returns no rows. A row count sensor may return no rows when a GROUP BY condition is added to capture the database server&#x27;s local time zone. In order to always return a value, a sensor may have a default value configured.|double| | | |
|parameters|Additional sensor definition parameters|Dict[string, string]| | | |









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
|[data_type](#parameterdatatype)|Parameter data type.|[ParameterDataType](#parameterdatatype)|date<br/>string<br/>enum<br/>string_list<br/>object<br/>datetime<br/>column_name<br/>boolean<br/>integer<br/>double<br/>integer_list<br/>long<br/>| | |
|[display_hint](#displayhint)|UI control display hint.|[DisplayHint](#displayhint)|textarea<br/>| | |
|required|True when the value for the parameter must be provided.|boolean| | | |
|allowed_values|List of allowed values for a field that is of an enum type.|string_list| | | |
|sample_values|List of sample values. The sample values are used in the documentation or help messages.|string_list| | | |









___  

## SensorDefinitionYaml  
Data quality sensor definition YAML schema for a data quality sensor specification.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|[kind](#specificationkind)||[SpecificationKind](#specificationkind)|table<br/>default_schedules<br/>dashboards<br/>source<br/>sensor<br/>check<br/>default_checks<br/>rule<br/>file_index<br/>settings<br/>default_notifications<br/>provider_sensor<br/>| | |
|[spec](#sensordefinitionspec)||[SensorDefinitionSpec](#sensordefinitionspec)| | | |









___  

