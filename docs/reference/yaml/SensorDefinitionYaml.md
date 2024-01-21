# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## SensorDefinitionYaml
Data quality sensor definition YAML schema for a data quality sensor specification.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`api_version`</span>|DQOps YAML schema version|*string*| |dqo/v1| |
|<span class="no-wrap-code ">`kind`</span>|File type|*enum*|*source*<br/>*table*<br/>*sensor*<br/>*provider_sensor*<br/>*rule*<br/>*check*<br/>*settings*<br/>*file_index*<br/>*dashboards*<br/>*default_schedules*<br/>*default_checks*<br/>*default_notifications*<br/>|sensor| |
|<span class="no-wrap-code ">[`spec`](./SensorDefinitionYaml.md#sensordefinitionspec)</span>|Custom data quality sensor specification object with definition of a custom sensor|*[SensorDefinitionSpec](./SensorDefinitionYaml.md#sensordefinitionspec)*| | | |









___


## SensorDefinitionSpec
Data Quality sensor definition specification. Provides the configuration for a data quality sensor definition, sensor&#x27;s parameters, etc.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`fields`](./SensorDefinitionYaml.md#parameterdefinitionslistspec)</span>|List of fields that are parameters of a custom sensor. Those fields are used by the DQOps UI to display the data quality check editing screens with proper UI controls for all required fields.|*[ParameterDefinitionsListSpec](./SensorDefinitionYaml.md#parameterdefinitionslistspec)*| | | |
|<span class="no-wrap-code ">`requires_event_timestamp`</span>|The data quality sensor depends on the configuration of the event timestamp column name on the analyzed table. When true, the name of the column that stores the event (transaction, etc.) timestamp must be specified in the timestamp_columns.event_timestamp_column field on the table.|*boolean*| | | |
|<span class="no-wrap-code ">`requires_ingestion_timestamp`</span>|The data quality sensor depends on the configuration of the ingestion timestamp column name on the analyzed table. When true, the name of the column that stores the ingestion (created_at, loaded_at, etc.) timestamp must be specified in the timestamp_columns.ingestion_timestamp_column field on the table.|*boolean*| | | |
|<span class="no-wrap-code ">`default_value`</span>|Default value that is used when the sensor returns no rows. A row count sensor may return no rows when a GROUP BY condition is added to capture the database server&#x27;s local time zone. In order to always return a value, a sensor may have a default value configured.|*double*| | | |
|<span class="no-wrap-code ">`parameters`</span>|Additional sensor definition parameters|*Dict[string, string]*| | | |









___


## ParameterDefinitionsListSpec
List of parameter definitions - the parameters for custom sensors or custom rules.









___


## ParameterDefinitionSpec
Defines a single field that is a sensor parameter or a rule parameter.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`field_name`</span>|Field name that matches the field name (snake_case) used in the YAML specification.|*string*| | | |
|<span class="no-wrap-code ">`display_name`</span>|Field display name that should be shown as a label for the control.|*string*| | | |
|<span class="no-wrap-code ">`help_text`</span>|Help text (full description) that will be shown to the user as a hint when the cursor is moved over the control.|*string*| | | |
|<span class="no-wrap-code ">`data_type`</span>|Parameter data type.|*enum*|*string*<br/>*boolean*<br/>*integer*<br/>*long*<br/>*double*<br/>*date*<br/>*datetime*<br/>*column_name*<br/>*enum*<br/>*string_list*<br/>*integer_list*<br/>*object*<br/>| | |
|<span class="no-wrap-code ">`display_hint`</span>|UI control display hint.|*enum*|*textarea*<br/>| | |
|<span class="no-wrap-code ">`required`</span>|True when the value for the parameter must be provided.|*boolean*| | | |
|<span class="no-wrap-code ">`allowed_values`</span>|List of allowed values for a field that is of an enum type.|*List[string]*| | | |
|<span class="no-wrap-code ">`sample_values`</span>|List of sample values. The sample values are used in the documentation or help messages.|*List[string]*| | | |









___


