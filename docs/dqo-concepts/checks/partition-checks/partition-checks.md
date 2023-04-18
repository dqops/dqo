# Partition checks

In DQO, the check is a data quality test, which consists of a [data quality sensor](../../sensors/sensors.md) and a
[data quality rule](../../rules/rules.md).

Partition checks measure data quality for each daily or monthly partition by creating a separate data quality score. 
To run a partition check, you need to select a data column that is the time partitioning key for the table.

## Setting up date or datetime column name
In order to enable time partition check, set a column that contains date, datetime or timestamp. 

1. Go to Data Source section
2. On a tree view select table of interest.
3. Go to the **Date and Time Columns** tag
4. In the dropdown menu under D**ATE or DATETIME column name for partition checks** select a column that contains date, datetime or timestamp.

## Checks configuration in YAML file
Partition data quality checks, like other data quality checks in DQO, are defined as YAML files.

Below is an example of YAML file showing sample configuration of a daily and monthly partition column data quality check
nulls_percent.

=== "Daily partition check"
    ``` yaml hl_lines="14-23"
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
          partition_checks:
            daily:
              nulls:
                daily_partition_checks_nulls_percent:
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
=== "Monthly partition check"
    ``` yaml hl_lines="14-23"
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
          partotion_checks:
            monthly:
              nulls:
                monthly_partition_checks_nulls_percent:
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

## Analysis of incrementally partitioned data


## What's next

- [Learn more about advanced profiling checks](../advanced-profiling/advanced-profiling.md)
- [Learn more about recurring checks](../recurring-checks/recurring-checks.md)



