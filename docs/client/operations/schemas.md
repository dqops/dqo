Schema management  


___  
## get_schema_monitoring_checks_model  
Return a UI friendly model of configurations for data quality monitoring checks on a schema  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_monitoring_checks_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/monitoring/{timeScale}/model  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_configuration_model||List[[CheckConfigurationModel](/docs/client/models/#checkconfigurationmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|[time_scale](/docs/client/models/#checktimescale)|Check time-scale|[CheckTimeScale](/docs/client/models/#checktimescale)|:material-check-bold:|
|table_name_pattern|Table name pattern|string| |
|column_name_pattern|Column name pattern|string| |
|column_data_type|Column data-type|string| |
|[check_target](/docs/client/models/#checktarget)|Check target|[CheckTarget](/docs/client/models/#checktarget)| |
|check_category|Check category|string| |
|check_name|Check name|string| |
|check_enabled|Check enabled|boolean| |
|check_configured|Check configured|boolean| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/monitoring/"daily"/model^
		-H "Accept: application/json"

    ```


___  
## get_schema_monitoring_checks_templates  
Return available data quality checks on a requested schema.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_monitoring_checks_templates.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/bulkenable/monitoring/{timeScale}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_template||List[[CheckTemplate](/docs/client/models/#checktemplate)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|[time_scale](/docs/client/models/#checktimescale)|Time scale|[CheckTimeScale](/docs/client/models/#checktimescale)|:material-check-bold:|
|[check_target](/docs/client/models/#checktarget)|Check target|[CheckTarget](/docs/client/models/#checktarget)| |
|check_category|Check category|string| |
|check_name|Check name|string| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/bulkenable/monitoring/"daily"^
		-H "Accept: application/json"

    ```


___  
## get_schema_partitioned_checks_model  
Return a UI friendly model of configurations for data quality partitioned checks on a schema  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_partitioned_checks_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/partitioned/{timeScale}/model  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_configuration_model||List[[CheckConfigurationModel](/docs/client/models/#checkconfigurationmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|[time_scale](/docs/client/models/#checktimescale)|Check time-scale|[CheckTimeScale](/docs/client/models/#checktimescale)|:material-check-bold:|
|table_name_pattern|Table name pattern|string| |
|column_name_pattern|Column name pattern|string| |
|column_data_type|Column data-type|string| |
|[check_target](/docs/client/models/#checktarget)|Check target|[CheckTarget](/docs/client/models/#checktarget)| |
|check_category|Check category|string| |
|check_name|Check name|string| |
|check_enabled|Check enabled|boolean| |
|check_configured|Check configured|boolean| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/partitioned/"daily"/model^
		-H "Accept: application/json"

    ```


___  
## get_schema_partitioned_checks_templates  
Return available data quality checks on a requested schema.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_partitioned_checks_templates.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/bulkenable/partitioned/{timeScale}  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_template||List[[CheckTemplate](/docs/client/models/#checktemplate)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|[time_scale](/docs/client/models/#checktimescale)|Time scale|[CheckTimeScale](/docs/client/models/#checktimescale)|:material-check-bold:|
|[check_target](/docs/client/models/#checktarget)|Check target|[CheckTarget](/docs/client/models/#checktarget)| |
|check_category|Check category|string| |
|check_name|Check name|string| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/bulkenable/partitioned/"daily"^
		-H "Accept: application/json"

    ```


___  
## get_schema_profiling_checks_model  
Return a flat list of configurations for profiling checks on a schema  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_profiling_checks_model.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/profiling/model  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_configuration_model||List[[CheckConfigurationModel](/docs/client/models/#checkconfigurationmodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|table_name_pattern|Table name pattern|string| |
|column_name_pattern|Column name pattern|string| |
|column_data_type|Column data-type|string| |
|[check_target](/docs/client/models/#checktarget)|Check target|[CheckTarget](/docs/client/models/#checktarget)| |
|check_category|Check category|string| |
|check_name|Check name|string| |
|check_enabled|Check enabled|boolean| |
|check_configured|Check configured|boolean| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/profiling/model^
		-H "Accept: application/json"

    ```


___  
## get_schema_profiling_checks_templates  
Return available data quality checks on a requested schema.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schema_profiling_checks_templates.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas/{schemaName}/bulkenable/profiling  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|check_template||List[[CheckTemplate](/docs/client/models/#checktemplate)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|
|schema_name|Schema name|string|:material-check-bold:|
|[check_target](/docs/client/models/#checktarget)|Check target|[CheckTarget](/docs/client/models/#checktarget)| |
|check_category|Check category|string| |
|check_name|Check name|string| |






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas/sample_schema/bulkenable/profiling^
		-H "Accept: application/json"

    ```


___  
## get_schemas  
Returns a list of schemas inside a connection  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/schemas/get_schemas.py)
  

**GET**
```
http://localhost:8888/api/connections/{connectionName}/schemas  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|schema_model||List[[SchemaModel](/docs/client/models/schemas/#schemamodel)]|




**Parameters of this method are described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Required&nbsp;|
|---------------|---------------------------------|-----------|-----------------|
|connection_name|Connection name|string|:material-check-bold:|






**Usage examples**  
=== "curl"
      
    ```bash
    curl http://localhost:8888/api/connections/sample_connection/schemas^
		-H "Accept: application/json"

    ```


