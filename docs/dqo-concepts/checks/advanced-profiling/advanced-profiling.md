# Advanced profiling

In DQO, the check is a data quality test, which consists of a [data quality sensor](../../sensors/sensors.md) and a
[data quality rule](../../rules/rules.md).

Advanced profiling is a type of check that should be used to profile data and run experiments to see which types 
of [recurring checks](../recurring-checks/recurring-checks.md) or [partition checks](../partition-checks/partition-checks.md)
are the most appropriate for monitoring the quality of data.

## Checks configuration in YAML file
Advance profiling data quality checks, like other data quality checks in DQO checks are defined as YAML files.

Below is an example of YAML file showing sample configuration of an advanced profiling column data quality check nulls_percent.

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
            error:
              max_percent: 1.0
            warning:
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

## What's next

- [Explore how to add advanced profiling checks](../../../working-with-dqo/run-data-quality-checks/run-data-quality-checks.md)