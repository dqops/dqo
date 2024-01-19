# Data quality checks overview
Data quality checks are responsible for detecting data quality issues, and asserting the data quality requirements for monitored data sources.

## Data quality check definition
In DQOps, a check is a data quality test that can be run on both table or column levels. The data quality check consists of a 
[data quality sensor](../definition-of-data-quality-sensors.md) and a [data quality rule](../definition-of-data-quality-rules.md).

The data quality sensor reads the value from the data source at a given point in time. The data quality rule includes 
a set of conditions (thresholds) that the sensor readout must meet. When the conditions are not met, the check detects 
an issue with your data, and it creates an [incident that can be viewed, filtered, and managed](../../working-with-dqo/managing-data-quality-incidents-with-dqops.md).

The components involved in running a data quality check are shown below.
The example below shows how DQOps performs the [daily_row_count](../../checks/table/volume/row-count.md#daily-row-count)
data quality check that verifies if the number of rows in the monitored table is greater than the expected minimum row count.

![Data quality check components](https://dqops.com/docs/images/concepts/data_quality_check_structure_min.png)

The data quality check is evaluated on a monitored table (or column) in three phases.

- The placeholders for the table name (and column name) **[sensor](../definition-of-data-quality-sensors.md) template** are
  filled in a templated SQL query (called a data quality sensor) 


- The generated SQL query is executed on the data source, capturing the data quality measure.
  At is expected that all data quality sensors in DQOps will return a result column named *actual_value* as a data measure,
  which will be evaluated with data quality rules.


- The data quality metric (called *sensor readout* in DQOps) is passed to a [data quality rule](../definition-of-data-quality-rules.md) that is
  a Python function. The function determines if the measure (sensor readout) should be accepted or if the data quality
  check should fail and generate a data quality issue at one of three severity levels: warning, error, or fatal.
  
  The data quality measure (sensor readout) is passed up to tree data quality rules because data quality rules
  for warning, error, and fatal severity levels use different parameters (thresholds).  


## Configuring data quality checks
Data quality checks are defined as YAML files that support code completion in code editors, such as [Visual Studio Code](../../integrations/visual-studio-code/index.md).
Data quality check definitions can be stored in the source code repository, and versioned along with any other data
pipeline or machine learning code. The folder structure where DQOps stores those YAML files is called `DQOps user home`
and is documented in the [configuring checks](../configuring-data-quality-checks-and-rules.md) article.

Below is an example of the YAML file showing a sample configuration of a profiling column data quality check
[profile_nulls_percent](../../checks/column/nulls/nulls-percent.md#profile-nulls-percent).

``` { .yaml .annotate linenums="1" hl_lines="10-16" }
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  ...
  columns:
    target_column:
      profiling_checks: # (1)!
        nulls: # (2)!
          profile_nulls_percent: # (3)!
            warning: # (4)!
              max_percent: 1.0
            error: # (5)!
              max_percent: 5.0
            fatal: # (6)!
              max_percent: 30.0
      labels:
      - This is the column that is analyzed for data quality issues
```

1.  The node that contains configuration of checks. In this example, these are [profiling checks](data-profiling-checks.md)
    defined at a column level.

2.  The name of the check category. Check categories group similar checks.

3.  The name of the configured check.

4.  The **[warning](#warning)** severity rule configuration.

5.  The **[error](#error)** severity rule configuration.

6.  The **[fatal](#fatal)** severity rule configuration.

The `spec` section contains the details of the table, including the target schema and table name. 

The `columns` section lists the columns in the table that has configured checks. In this example, the column named 
`target_column` has a configured check `profile_nulls_percent`. This means that the sensor reads the percentage of null
values in `target_column`. If the percentage exceeds a certain threshold, an error, warning, or fatal message will
be raised.

The structure of the table configuration file is described in the [configuring checks](../configuring-data-quality-checks-and-rules.md) section.


## Issue severity levels
Each data quality check supports configuring the alerting thresholds at three levels: *warning*, *error*, and *fatal*.
DQOps will pass the [sensor](../definition-of-data-quality-sensors.md) (the captured data quality metric, such as a percentage of null values)
to all three [data quality rules](../definition-of-data-quality-rules.md), using different thresholds.
If rules at multiple severity levels identify a data quality issue (the rule fails), DQOps picks the severity level
of the most severe rule that failed in the following order: *fatal*, *error*, and *warning*.

The rule severity levels are described below.

### **Warning**
The warning level alerting threshold raises warnings for less important data quality issues,
usually anomalies or expected random or seasonal data quality issues. Warnings are
not treated as data quality issues. Data quality checks that have not passed the warning alerting rule, but have passed the
error and fatal alerting rules, are still counted as passed data quality checks and do not reduce the
[data quality KPIs](../definition-of-data-quality-kpis.md) score. Warnings should be used to identify potential data
quality issues that should be monitored, but the data producer should not take accountability for them.

For example, a percentage of data quality check monitoring null value may raise a warning when the percentage of rows with a null value exceeds 1% of all rows.


### **Error**
The error is the default alerting level for monitoring checks, comparable to the "error" level in logging libraries.
Data quality checks that failed to pass the rule evaluation at the "error" severity level
are considered failed data quality checks for the purpose of calculating the [data quality KPI](../definition-of-data-quality-kpis.md) score.

For example, a percentage of data quality check monitoring null value may raise an error when the percentage of rows with a null value exceeds 5% of all rows.


### **Fatal**
The fatal severity level is the highest alerting threshold that should only be used to identify severe data quality issues.
These issues should result in stopping data pipelines before the issue spreads throughout the system. Fatal data
quality issues are treated as failed data quality checks and reduce the [data quality KPIs](../definition-of-data-quality-kpis.md)
score. The fatal threshold should be used with caution. It is mainly useful when the data pipeline can trigger the data
quality check assessment and wait for the result. If any data quality check raises a fatal data quality issue, the data
pipeline should be stopped.

For example, a percentage of data quality check monitoring null value may raise a fatal alert when the percentage of rows with a null value exceeds 30% of all rows.


## Rule severity matrix
The purpose of reporting data quality issues at different severity levels is summarized below.

| Alerting threshold  | Data quality check passed | Data quality KPI result is decreased | Data pipeline should be stopped |
|---------------------|:-------------------------:|:------------------------------------:|:-------------------------------:|
| **Warning**         |  :material-close-thick:   |                                      |                                 |
| **Error** (default) |                           |        :material-close-thick:        |                                 |
| **Fatal**           |                           |        :material-close-thick:        |     :material-close-thick:      |


## Types of checks

In DQOps, data quality checks are divided into 3 types.

### **Profiling checks**
[**Profiling checks**](data-profiling-checks.md) are designed to assess the initial data quality score
of a data source. Profiling checks are also useful for
exploring and experimenting with various types of checks and determining the most suitable ones for regular data quality monitoring.


### **Monitoring checks**
[**Monitoring checks**](data-observability-monitoring-checks.md) are standard checks that monitor the data quality of a
table or column. They can also be referred to as **Data Observability** checks.
These checks capture a single data quality result for the entire table or column. There are two categories
of monitoring checks: *daily* checks and *monthly* checks.

- **Daily monitoring checks** capture the end-of-day data quality status of the monitored table or column.
  When they are run again during the day, the **daily checks** store only the most recent data quality status for that day.

- **Monthly monitoring checks** capture the end-of-month data quality status of the monitored table or column.
  When they are run again during the month, they store only the most recent status for the current month, 
  overwriting the previous data quality status from the previous evaluation during the current month.


### **Partition checks**
[**Partition checks**](partition-checks.md) are designed to measure the data quality of **partitioned data**.
In contrast to monitoring checks, partition checks capture a separate data quality result for each partition.
To run a partition check, you need to select a column that serves as the time partitioning key for the data.
Partition checks are also divided into two categories: daily checks and monthly checks.
Partition checks are designed for [incremental data quality monitoring](../incremental-data-quality-monitoring.md).


## Tested time periods
Knowing the time when the data quality issue was present is essential for knowing when the issue has begun.
The data quality issue will be fixed, but the same data quality issue can happen again in the future.

DQOps stores [historical data quality results](../data-storage-of-data-quality-results.md) for further analysis, and
to measure the trustworthiness of the data source by calculating a [data quality KPI](../definition-of-data-quality-kpis.md) score.


### **Time periods in SQL queries**
The following example shows the SQL query for [PostgreSQL](../../data-sources/postgresql.md), which is generated by DQOps for
the [daily_nulls_percent](../../checks/column/nulls/nulls-percent.md#daily-nulls-percent) data quality check that measures the percentage of rows with null values in a monitored column.
This is an example of a daily [monitoring check](data-observability-monitoring-checks.md).

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

1.  Actual value returned by the [data quality sensor](../definition-of-data-quality-sensors.md), called the **sensor readout**.
    It is the measure captured by the data quality sensor. It will be verified by the [data quality rule](../definition-of-data-quality-rules.md).

2.  The time period for which the **sensor readout** is valid, using a local time zone of the monitored database.
    The current time is truncated (casted) to a local date.

3.  The time period (day) for which the **sensor readout** is valid, but converted to timestamp with the database server's time zone.


DQOps captures the data quality measure, called the **sensor readout**. The data quality [sensor templates](../definition-of-data-quality-sensors.md)
require that the result is returned as an `actual_value` result column.

The remaining two columns that are returned by the query are:

 -  `time_period` returns the time period for which this data quality check result is valid. DQOps prefers to query the data source
    for the time period, instead of relying on the DQOps server's local time zone,
    in order to capture the time in the monitored database's local time zone. It is important for monitoring the quality of data sources
    across different time zones.
    Because this SQL query was generated for a daily [monitoring check](data-observability-monitoring-checks.md), the
    local system time is truncated to the beginning of the day by applying a *CAST as date* expression. 


 -  `time_period_utc` is the same value that is returned by the `time_period` query but converted to a timestamp data type,
    which stores an absolute time, including the time zone of the server.

The time period captured from the monitored data source is truncated to the beginning of the time period for which the data quality
result is valid. In the example above, the [daily_nulls_percent](../../checks/column/nulls/nulls-percent.md#daily-nulls-percent)
that captures the end-of-day data quality status of the percentage of null values will capture the result for the day when it was executed.


### **Daily data quality status snapshots**
Daily [monitoring checks](data-observability-monitoring-checks.md) measures the end-of-day data quality status.
The date for which the data quality status is valid is calculated by truncating the `time_period` to the beginning of the
current day.

In case the data quality issue was detected in the morning, fixed during the day, and the fix was revalidated
by running the data quality check again on the same day, DQOps will *forget* the previous data quality result (the failure),
and will replace it with the most recent end-of-day data quality status.


### **Monthly data quality status snapshots**
Monthly [monitoring checks](data-observability-monitoring-checks.md) measure the end-of-month data quality status.
DQOps runs them daily because the default [CRON schedule](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md) for executing
monthly data quality monitoring checks is configured to run every day.  

The SQL query generated for a similar, monthly data quality check
[monthly_nulls_percent](../../checks/column/nulls/nulls-percent.md#monthly-nulls-percent) is shown below.

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


### **Local vs UTC time**
DQOps captures both the local time of the monitored data source and an absolute time with the time zone.
The local time is returned from the data quality SQL query in the `time_period` result column, while the absolute time
with a time zone is returned to the `time_zone_utc` column.

By capturing the time period as both a local time of the monitored database, and an absolute time with a time zone, DQOps can
detect the time zone offset on the monitored database correctly, including even the daylight saving time.


### **Time periods for partitioned data**
DQOps can analyze tables that are physically partitioned by a date or date time column, such as time-partitioned ingestion tables.
Other types of partitioned data that are a perfect target of [partition checks](partition-checks.md)
are tables with a date/datetime column that identifies an event, transaction, sale, or operation.

The column name that is used for partitioning must be selected for each table, the name of the table is stored in the
table metadata YAML file in the `timestamp_columns.partition_by_column` field.

The following example shows the SQL query generated by DQOps to run the
[daily_partition_nulls_percent](../../checks/column/nulls/nulls-percent.md#daily-partition-nulls-percent) check
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


### **Measure data quality incrementally**
Measuring the quality of partitioned data with [partition checks](partition-checks.md)
is designed to measure the data quality of *append-only* tables, such as fact tables.
It is also designed for measuring the quality of very big tables, analyzing only the *tail* of the database, and avoiding
unnecessary pressure on the data source caused by data quality monitoring.

Please read the [monitoring data quality incrementally](../incremental-data-quality-monitoring.md) guide for details
and additional use cases.

## Define custom check in UI
You can easily change the rules and sensor associated with the checks in DQOps
using the **Configuration** section of the [user interface](../dqops-user-interface-overview.md).


### **Check definition screen**
Below is an example of the **Configuration** screen for the `daily_row_count` check.
This screen is responsible for editing the specification files for a custom data quality check
stored in the [*$DQO_USER_HOME/checks/\*\*/\*.dqocheck.yaml*](../../reference/yaml/CheckDefinitionYaml.md) files.

![Check definition configuration](https://dqops.com/docs/images/concepts/check-definition-configuration.png)


## What's next

- Learn how to [configure data sources](../configuring-data-sources.md)
- Learn how to [configure data quality checks](../configuring-data-quality-checks-and-rules.md)
- Learn how to [run data quality checks](../running-data-quality-checks.md)
- [Learn more about profiling checks](data-profiling-checks.md)
- [Learn more about monitoring checks](data-observability-monitoring-checks.md)
- [Learn more about partition checks](partition-checks.md)
- Review the list of common data quality problems detected by DQOps, 
  divided into [categories of data quality checks](../types-of-data-quality-checks/index.md)
