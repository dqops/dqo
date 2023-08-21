
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
|[fields](\docs\reference\yaml\ruledefinitionyaml\#parameterdefinitionslistspec)|List of fields that are parameters of a custom sensor. Those fields are used by the DQO UI to display the data quality check editing screens with proper UI controls for all required fields.|[ParameterDefinitionsListSpec](\docs\reference\yaml\ruledefinitionyaml\#parameterdefinitionslistspec)| | | |
|requires_event_timestamp|The data quality sensor depends on the configuration of the event timestamp column name on the analyzed table. When true, the name of the column that stores the event (transaction, etc.) timestamp must be specified in the timestamp_columns.event_timestamp_column field on the table.|boolean| | | |
|requires_ingestion_timestamp|The data quality sensor depends on the configuration of the ingestion timestamp column name on the analyzed table. When true, the name of the column that stores the ingestion (created_at, loaded_at, etc.) timestamp must be specified in the timestamp_columns.ingestion_timestamp_column field on the table.|boolean| | | |









___  

