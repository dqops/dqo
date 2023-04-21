# Working with the YAML files

In DQO, the configuration of [data quality checks](../checks/index.md) is defined in the YAML files. YAML is a human-readable
data serialization language that is often used for writing configuration files. 

Defining data quality checks in the YAML files
allows checks definitions to be stored in a source code repository and versioned along with any other pipeline code or 
machine learning code.

Below is an example of the YAML file showing sample configuration of an advanced profiling column data quality check nulls_percent.

``` yaml hl_lines="14-22"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  target:
    schema_name: target_schema
    table_name: target_table
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
    partitioned_checks_timestamp_source: event_timestamp
  columns:
    target_column:
      checks:
        nulls:
          nulls_percent:
            warning:
              max_percent: 1.0
            error:
              max_percent: 5.0
            fatal:
              max_percent: 30.0
      labels:
      - This is the column that is analyzed for data quality issues
    col_event_timestamp:
      labels:
      - optional column that stores the timestamp when the event/transaction happened
    col_inserted_at:
      labels:
      - optional column that stores the timestamp when row was ingested  
```
The `spec` section contains the details of the table, including the target schema and table name.

The `timestamp_columns` section specifies the column names for various timestamps in the data.

The `columns` section lists the columns in the table which has configured checks. In this example the column named
`target_column` has a configured check `nulls_percent`. This means that the sensor reads the percentage of null
values in `target_column`. If the percentage exceeds a certain threshold, an error, warning, or fatal message will
be raised.

## Editing the YAML files

YAMl configuration files are located in the `./sources` folder. The complete DQO YAML schema can e found 
[here](https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json). 

The YAML files in DQO support code completion in code editors, such as Visual Studio Code.

**Add Screen**