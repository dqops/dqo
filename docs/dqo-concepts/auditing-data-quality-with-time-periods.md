# Auditing data quality with time periods
Read this guide to learn how DQOps captures the database local time when running data quality checks, to enable auditing data quality results.

## Tested time periods
Knowing the time when the data quality issue was present is essential for knowing when the issue has begun.
The data quality issue will be fixed, but the same data quality issue can happen again in the future.

DQOps stores [historical data quality results](data-storage-of-data-quality-results.md) for further analysis, and
to measure the trustworthiness of the data source by calculating a [data quality KPI](definition-of-data-quality-kpis.md) score.


## Time periods in SQL queries
The following example shows the SQL query for [PostgreSQL](../data-sources/postgresql.md), which is generated by DQOps for
the [daily_nulls_percent](../checks/column/nulls/nulls-percent.md#daily-nulls-percent) data quality check that measures the percentage of rows with null values in a monitored column.
This is an example of a daily [monitoring check](definition-of-data-quality-checks/data-observability-monitoring-checks.md).

The SQL query generated by DQOps is shown below.

``` { .sql .annotate linenums="1" hl_lines="11-12" }
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN analyzed_table."target_column" IS NULL THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value, -- (1)!
    CAST(LOCALTIMESTAMP AS date) AS time_period, -- (2)!
    CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc -- (3)!
FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
```

1.  Actual value returned by the [data quality sensor](definition-of-data-quality-sensors.md), called the **sensor readout**.
    It is the measure captured by the data quality sensor. It will be verified by the [data quality rule](definition-of-data-quality-rules.md).

2.  The time period for which the **sensor readout** is valid, using a local time zone of the monitored database.
    The current time is truncated (casted) to a local date.

3.  The time period (day) for which the **sensor readout** is valid, but converted to timestamp with the database server's time zone.


DQOps captures the data quality measure, called the **sensor readout**. The data quality [sensor templates](definition-of-data-quality-sensors.md)
require that the result is returned as an `actual_value` result column.

The remaining two columns that are returned by the query are:

-  `time_period` returns the time period for which this data quality check result is valid. DQOps prefers to query the data source
   for the time period, instead of relying on the DQOps server's local time zone,
   in order to capture the time in the monitored database's local time zone. It is important for monitoring the quality of data sources
   across different time zones.
   Because this SQL query was generated for a daily [monitoring check](definition-of-data-quality-checks/data-observability-monitoring-checks.md), the
   local system time is truncated to the beginning of the day by applying a *CAST as date* expression.


-  `time_period_utc` is the same value that is returned by the `time_period` query but converted to a timestamp data type,
   which stores an absolute time, including the time zone of the server.

The time period captured from the monitored data source is truncated to the beginning of the time period for which the data quality
result is valid. In the example above, the [daily_nulls_percent](../checks/column/nulls/nulls-percent.md#daily-nulls-percent)
that captures the end-of-day data quality status of the percentage of null values will capture the result for the day when it was executed.


## Storing results of monitoring and profiling checks
The [data profiling checks](definition-of-data-quality-checks/data-profiling-checks.md) and 
[data quality monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) capture
the local and UTC timestamp from the monitored database.

### **Daily data quality status snapshots**
Daily [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) measures the end-of-day data quality status.
The date for which the data quality status is valid is calculated by truncating the `time_period` to the beginning of the
current day.

In case the data quality issue was detected in the morning, fixed during the day, and the fix was revalidated
by running the data quality check again on the same day, DQOps will *forget* the previous data quality result (the failure),
and will replace it with the most recent end-of-day data quality status.

The SQL query generated by DQOps is the same as shown above.

``` { .sql .annotate linenums="1" hl_lines="11-12" }
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN analyzed_table."target_column" IS NULL THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    CAST(LOCALTIMESTAMP AS date) AS time_period,
    CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
```

### **Monthly data quality status snapshots**
Monthly [monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) measure the end-of-month data quality status.
DQOps runs them daily because the default [CRON schedule](../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md) for executing
monthly data quality monitoring checks is configured to run every day.

The SQL query generated for a similar, monthly data quality check
[monthly_nulls_percent](../checks/column/nulls/nulls-percent.md#monthly-nulls-percent) is shown below.

``` { .sql .annotate linenums="1" hl_lines="11-12" }
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN analyzed_table."target_column" IS NULL THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period, -- (1)!
    CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc -- (2)!
FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
```

1.  The `time_period` value truncated to the beginning of the current month.

2.  The `time_period` value truncated to the beginning of the current month, and converted to an absolute time by adding also
    the time zone of the database server.

The difference in the query is only in the date truncation that was applied. The date is truncated to the beginning of the current month.


## Local vs UTC time
DQOps captures both the local time of the monitored data source and an absolute time with the time zone.
The local time is returned from the data quality SQL query in the `time_period` result column, while the absolute time
with a time zone is returned to the `time_zone_utc` column.

By capturing the time period as both a local time of the monitored database, and an absolute time with a time zone, DQOps can
detect the time zone offset on the monitored database correctly, including even the daylight saving time.


## Time periods for partitioned data
DQOps can analyze tables that are physically partitioned by a date or date time column, such as time-partitioned ingestion tables.
Other types of partitioned data that are a perfect target of [partition checks](definition-of-data-quality-checks/partition-checks.md)
are tables with a date/datetime column that identifies an event, transaction, sale, or operation.

The column name that is used for partitioning must be selected for each table, the name of the table is stored in the
table metadata YAML file in the `timestamp_columns.partition_by_column` field.

The following example shows the SQL query generated by DQOps to run the
[daily_partition_nulls_percent](../checks/column/nulls/nulls-percent.md#daily-partition-nulls-percent) check
that groups rows by the date column, aggregating the data quality measures by date, without the time.

``` { .sql .annotate linenums="1" hl_lines="11-12" }
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN analyzed_table."target_column" IS NULL THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value,
    CAST(analyzed_table."transaction_date" AS date) AS time_period,
    CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
```

The date column used for grouping in the example above is the `"transaction_date"`.

!!! info "Configuring timestamp columns for partitioned checks"
 
    Read the [setting up date partitioning column](definition-of-data-quality-checks/partition-checks.md#setting-up-date-partitioning-column)
    guide to learn how to configure the date/timestamp column that DQOps uses in the **GROUP BY** clause
    in [partition checks](definition-of-data-quality-checks/partition-checks.md).


## Measure data quality incrementally
Measuring the quality of partitioned data with [partition checks](definition-of-data-quality-checks/partition-checks.md)
is designed to measure the data quality of *append-only* tables, such as fact tables.
It is also designed for measuring the quality of very big tables, analyzing only the *tail* of the database, and avoiding
unnecessary pressure on the data source caused by data quality monitoring.

Please read the [monitoring data quality incrementally](incremental-data-quality-monitoring.md) guide for details
and additional use cases.

## What's next
- Learn about [data profiling checks](definition-of-data-quality-checks/data-profiling-checks.md),
  [data quality monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md)
  and [partition checks](definition-of-data-quality-checks/partition-checks.md).
- Learn how DQOps [stores data quality results](data-storage-of-data-quality-results.md) in Parquet files.
- Expand the [samples of SQL queries](../checks/table/volume/row-count.md#daily-row-count) generated by DQOps for each data source
  to see how the time periods are captured in queries.