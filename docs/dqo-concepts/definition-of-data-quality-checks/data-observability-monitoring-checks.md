# Monitoring checks

## What are monitoring checks?

In DQOps, the check is a data quality test, which consists of a [data quality sensor](../sensors/sensors.md) and a
[data quality rule](../definition-of-data-quality-rules.md).

Monitoring checks are standard checks that monitor the data quality of a table or column. There are two categories of 
monitoring checks: daily checks and monthly checks.

The daily monitoring checks store the most recent sensor readouts for each day when the data quality check was run. 
This means that if you run a check several times a day only the most recent readout is stored. The previous readouts for 
that day will be overwritten. 

For example, if we run the check for three consecutive days, the results table could look like this:

| actual_value | time_period |
|-------------:|------------:|
 |       95.51% |  2023-04-05 |
 |       90.52% |  2023-04-06 |
 |       91.06% |  2023-04-07 |

The original time_period timestamp of the result e.g. 2023-04-05T09:06:53.386Z is truncated to midnight for daily checks.

If there was a change in the data on 2023-04-07, and we run the check again, the table will be updated to show the latest result. 

| actual_value |    time_period |
|-------------:|---------------:|
|       95.51% |     2023-04-05 |
|       90.52% |     2023-04-06 |
|   **98.17%** | **2023-04-07** |

The previous result for 2023-04-07 was deleted.

Similarly, the monthly monitoring checks store the most recent sensor readout for each month when the data quality check was run.
For monthly monitoring checks, the original time_period of the result e.g. 2023-04-05T09:06:53.386Z is truncated to the 1st day of the month - 2023-04-01. 

This approach allows you to track the data quality over time and calculate daily and monthly data quality KPIs.

## Checks configuration in the YAML file
Monitoring data quality checks, like other data quality checks in DQOps, are defined as YAML files.

Below is an example of the YAML file showing sample configuration of a daily and monthly monitoring column data quality check
nulls_percent.

=== "Daily monitoring check"
    ``` yaml hl_lines="10-19"
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
          monitoring_checks:
            daily:
              nulls:
                daily_nulls_percent:
                  warning:
                    max_percent: 1.0
                  error:
                    max_percent: 5.0
                  fatal:
                    max_percent: 30.0
          labels:
          - This is the column that is analyzed for data quality issues
    ```
=== "Monthly monitoring check"
    ``` yaml hl_lines="10-19"
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
          monitoring_checks:
            monthly:
              nulls:
                monthly_nulls_percent:
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
`target_column` has a configured daily or monthly check `nulls_percent`. This means that the sensor reads the percentage of null
values in `target_column`. If the percentage exceeds a certain threshold, an error, warning, or fatal message will
be raised. 

For daily monitoring check even if the check is run several times a day, only the last sensor readout for each day
is stored. For monthly monitoring check even if the check is run several in a month, only the last sensor readout for each month
is stored.

## What's next 

- [Learn more about profiling checks](data-profiling-checks.md)
- [Learn more about partition checks](partition-checks.md)