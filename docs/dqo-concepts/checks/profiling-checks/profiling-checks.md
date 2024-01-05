# Profiling

## What are profiling checks?

In DQOps, the check is a data quality test, which consists of a [data quality sensor](../../sensors/sensors.md) and a
[data quality rule](../../rules/rules.md).

Profiling checks are useful for exploring and experimenting with various types of checks and determining the most suitable
ones for regular data quality monitoring.

When the profiling data quality check is run, only one sensor readout is saved per month. As an illustration, if the check 
is run three times in April, and one time in May the table with the results could look like this:

| actual_value |              time_period |
|-------------:|-------------------------:|
|       95.51% | 2023-04-30T09:07:03.578Z |
|       94.52% | 2023-05-01T09:08:50.635Z |

If there was a change in the data, and we run the check again in May, the result for May will be updated.

| actual_value |                  time_period |
|-------------:|-----------------------------:|
|       95.51% |     2023-04-05T09:07:03.578Z |
|   **95.79%** | **2023-05-02T11:47:20.843Z** |


## Checks configuration in the YAML file
Profiling data quality checks, like other data quality checks in DQOps are defined as YAML files.

Below is an example of the YAML file showing sample configuration of a profiling column data quality check nulls_percent.

``` yaml hl_lines="14-22"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
    partitioned_checks_timestamp_source: event_timestamp
  columns:
    target_column:
      checks:
        nulls:
          profile_nulls_percent:
            error:
              max_percent: 1.0
            warning:
              max_percent: 5.0
            fatal:
              max_percent: 30.0
      labels:
      - This is the column that is analyzed for data quality issues
```
The `spec` section contains the details of the table, including the target schema and table name.

The `timestamp_columns` section specifies the column names for various timestamps in the data.

The `columns` section lists the columns in the table which has configured checks. In this example the column named
`target_column` has a configured check `profile_nulls_percent`. This means that the sensor reads the percentage of null
values in `target_column`. If the percentage exceeds a certain threshold, an error, warning, or fatal message will
be raised.

## What's next

- [Learn more about monitoring checks](../monitoring-checks/monitoring-checks.md)
- [Learn more about partition checks](../partition-checks/partition-checks.md)