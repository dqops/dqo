# Partition checks

In DQO, the check is a data quality test, which consists of a [data quality sensor](../../sensors/sensors.md) and a
[data quality rule](../../rules/rules.md).

Partition checks measure data quality for each daily or monthly partition by creating a separate data quality score. 

The daily partition checks store the most recent sensor readouts for each partition and each day when the data quality
check was run. This means that if you run check several times a day only the most recent readout is stored. The previous readouts for
that day will be overwritten.


The daily recurring checks store the most recent sensor readouts for each day when the data quality check was run.
This means that if you run a check several times a day only the most recent readout is stored. The previous readouts for
that day will be overwritten.

For example, we have a table with results from three consecutive days that look like this:

| actual_value |              time_period |
|-------------:|-------------------------:|
|       95.51% | 2023-04-05T09:07:03.578Z |
|       94.52% | 2023-04-05T09:08:50.635Z |
|       90.88% | 2023-04-05T09:10:44.386Z |
|       91.51% | 2023-04-06T09:07:03.578Z |
|       93.56% | 2023-04-06T09:08:50.635Z |
|       96.54% | 2023-04-06T09:10:44.386Z |
|       95.55% | 2023-04-07T09:07:03.578Z |
|       92.64% | 2023-04-07T09:08:50.635Z |
|       96.06% | 2023-04-07T09:10:44.386Z |

Daily partitioned data should be analyzed separately, for each daily partition. That is why daily partition checks use
**GROUP BY** clause with daily time slicing.

The following Google BigQuery query example captures time-sliced data to calculate metrics for each day separately.

``` sql hl_lines="1 4"
SELECT DATETIME_TRUNC(time_period, DAY) as time_period,
100.0 * SUM(CASE WHEN actual_value >= 0 THEN 1 ELSE 0 END) /
COUNT(*) as percentage_valid FROM schema.table
GROUP BY time_period
```

The results grouped by day may look like this:

| actual_value | time_period |
|-------------:|------------:|
|       93.64% |  2023-04-05 |
|       93.88% |  2023-04-06 |
|       94.76% |  2023-04-07 |


The original time_period of the result e.g. 2023-04-05T09:07:03.578Z is truncated to midnight for daily checks.

When there was a change in the data and on 2023-04-07 we run the check again, the table will be updated to show the latest result.

| actual_value |    time_period |
|-------------:|---------------:|
|       93.64% |     2023-04-05 |
|       93.88% |     2023-04-06 |
|   **98.65%** | **2023-04-07** |

The previous result for 2023-04-07 was deleted.

Similarly, the monthly recurring checks store the most recent sensor readout for each month when the data quality check was run.
For monthly recurring checks, the original time_period of the result e.g. 2023-04-05T09:07:03.578Z is truncated to the 1st day of the month - 2023-04-01.

To run a partition check, you need to select a data column that is the time partitioning key for the table.

## Setting up date or datetime column name
In order to enable time partition check, set a column that contains date, datetime or timestamp. 

1. Go to Data Source section
2. On a tree view select table of interest.
3. Go to the **Date and Time Columns** tag
4. In the dropdown menu under D**ATE or DATETIME column name for partition checks** select a column that contains date, datetime or timestamp.

## Checks configuration in the YAML file
Partition data quality checks, like other data quality checks in DQO, are defined as YAML files.

Below is an example of the YAML file showing sample configuration of a daily and monthly partition column data quality check
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



