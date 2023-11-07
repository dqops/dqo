# Partition checks

In DQOps, the check is a data quality test, which consists of a [data quality sensor](../../sensors/sensors.md) and a
[data quality rule](../../rules/rules.md).

Partition checks are designed to measure the data quality in partitioned data. In contrast to [monitoring checks](../monitoring-checks/monitoring-checks.md),
partition checks produce separate monitoring results for each partition. There are two categories of partition checks: daily checks and monthly checks.

The daily partition checks store the most recent sensor readouts for each partition and each day when the data quality
check was run. This means that if you run check several times a day only the most recent readout is stored. The previous readouts for
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

Similarly, the monthly partition checks store the most recent sensor readout for each month when the data quality check was run.
For monthly partition checks, the original time_period of the result e.g. 2023-04-05T09:07:03.578Z is truncated to the 1st day of the month - 2023-04-01.

To run a partition check, you need to select a column that serves as the time partitioning key for the data.

## Setting up date or datetime column name
In order to enable time partition check, set a column that contains date, datetime or timestamp. 

1. Go to the **Data Sources** section.

2. Select the table of interest from the tree view.

3. Select the **Data and Time Columns** tab and select a column from the drop-down list in the "Date or datetime
   column name for partition checks" input field.

   ![Checking results](https://dqops.com/docs/images/run-data-quality-checks/working-with-dqo/date-or-datetime-column-configuration-for-partion-checks.jpg)

4. Click the Save button in the upper right corner.

## Checks configuration in the YAML file
Partition data quality checks, like other data quality checks in DQOps, are defined as YAML files.

Below is an example of the YAML file showing sample configuration of a daily and monthly partition column data quality check
nulls_percent.

=== "Daily partition check"
    ``` yaml hl_lines="11-20"
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
    ```
=== "Monthly partition check"
    ``` yaml hl_lines="11-20"
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
    ```

## What's next

- [Learn more about profiling checks](../profiling-checks/profiling-checks.md)
- [Learn more about monitoring checks](../monitoring-checks/monitoring-checks.md)



