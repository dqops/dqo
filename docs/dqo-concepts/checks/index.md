# Checks overview

In DQO, the check is a data quality test, which consists of a [data quality sensor](../sensors/sensors.md) and a 
[data quality rule](../rules/rules.md).

Data quality sensor reads the value from the data source at a given point in time. While data quality rule is a set of 
conditions against which sensor readouts are verified, described by a list of thresholds.

## Types of checks

In DQO there are 3 types of checks:

- [Profiling checks](profiling-checks/profiling-checks.md) that should be used to profile data and run 
experiments to see which check would be most appropriate for monitoring the quality of data.

- [Monitoring checks](monitoring-checks/monitoring-checks.md) are standard checks that monitor data quality. Monitoring 
checks can be run daily and monthly. Daily monitoring checks stores the most recent sensor readouts for each day when 
the data quality check was run. While monthly monitoring checks store the most recent sensor readout for each month 
when the data quality check was run.

- [Partition checks](./partition-checks/partition-checks.md) measure data quality for each daily or monthly partition by
creating a separate data quality score. To run a partition check, you need to select a data column that is the time 
partitioning key for the table.

## Categories of checks

Each type of checks is divided into two main categories: table and column. Table-level data quality checks are used to 
evaluate the table at a high-level without reference to individual columns, while column-level checks are run on 
specific column. Built-in checks available in DQO are divided into the following subcategories. 

You can access the full lists of available checks with detailed descriptions by clicking on a link. 

### **Table checks**

| Subcategory                                | Description                                                                                                                                                                                                                                 |
|--------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Volume](../../checks/#volume)             | Evaluates the overall quality of the table by verifying the number of rows.                                                                                                                                                                 |
| [Timeliness](../../checks/#timeliness)     | Assesses the freshness and staleness of data, as well as data ingestion delay and reload lag for partitioned data.                                                                                                                          |
| [Accuracy](../../checks/#accuracy)         | Compares the tested table with another (reference) table.                                                                                                                                                                                   |
| [SQL](../../checks/#sql)                   | Validate data against user-defined SQL queries at the table level. Checks in this group allow for validation that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range. |
| [Availability](../../checks/#availability) | Checks whether the table is accessible and available for use.                                                                                                                                                                               |                                                                                                                                                                                                                                            |
| [Schema](../../checks/#schema)             | Detects changes in the schema                                                                                                                                                                                                               |                                                                                                                                                                                                                                            |

### **Column checks**

| Subcategory                            | Description                                                                                                                                                                                                                                |
|----------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Nulls](../../checks/#nulls)           | Checks for the presence of null or missing values in a column.                                                                                                                                                                             |
| [Numeric](../../checks/#numeric)       | Validates that the data in a numeric column is in the expected format or within predefined ranges.                                                                                                                                         |
| [Strings](../../checks/#strings)       | Validates that the data in a string column match the expected format or pattern.                                                                                                                                                           |
| [Uniqueness](../../checks/#uniqueness) | Counts the number or percent of duplicate or unique values in a column.                                                                                                                                                                    |
| [DateTime](../../checks/#datetime)     | Validates that the data in a date or time column is in the expected format and within predefined ranges.                                                                                                                                   |
| [PII](../../checks/#pii)               | Checks for the presence of sensitive or personally identifiable information (PII) in a column such as email, phone, zip code, IP4 and IP6 addresses.                                                                                       |
| [SQL](../../checks/#sql_1)             | Validate data against user-defined SQL queries at the column level. Checks in this group allows to validate that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range. |
| [Bool](../../checks/#bool)             | Calculates the percentage of data in a Boolean format.                                                                                                                                                                                     |
| [Integrity](../../checks/#integrity)   | Checks the referential integrity of a column against a column in another table.                                                                                                                                                            |
| [Accuracy](../../checks/#accuracy)     | Verifies that percentage of the difference in sum of a column with a reference column.                                                                                                                                                     |
| [Datatype](../../checks/#datatype)     | Detects changes in the datatype.                                                                                                                                                                                                           |
| [Anomaly](../../checks/#anomaly)       | Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.                                                                                                            |
| [Schema](../../checks/#schema)         | Detects changes in the schema.                                                                                                                                                                                                             |

## Severity levels

Checks evaluate the results using rules. There are 3 severity levels in DQO: warning, error and fatal

- **Warning**. A warning level alerting threshold raises warnings for less important data quality issues. Warnings are
  not treated as data quality issues. Data quality checks that did not pass the warning alerting rule, but did pass the
  error and fatal alerting rules are still counted as passed data quality checks and do not reduce the
  [data quality KPIs](../data-quality-kpis/data-quality-kpis.md) score. Warnings should be used to identify potential data
  quality issues that should be monitored.
   
    For example, a percentage of data quality check monitoring null value may raise a warning when the percentage of rows with a null value exceeds 1% of all rows.


- **Error**. Error is the default alerting level, comparable to the "error" level in logging libraries. Data quality
  checks that failed to pass the rule evaluation at the "error" severity level are considered failed data quality checks.
  Errors reduce the value of [data quality KPIs](../data-quality-kpis/data-quality-kpis.md) score.

    For example, a percentage of data quality check monitoring null value may raise an error when the percentage of rows with a null value exceeds 5% of all rows.


- **Fatal**. Fatal is the highest alerting threshold that should only be used to identify severe data quality issues.
  These issues should result in stopping the data pipelines before the issue spreads throughout the system. Fatal data
  quality issues are treated as failed data quality checks and reduce the [data quality KPIs](../data-quality-kpis/data-quality-kpis.md)
  score. The fatal threshold should be used with caution. It is mainly useful when the data pipeline can trigger the data
  quality check assessment and wait for the result. If any data quality check raises a fatal data quality issue, the data
  pipeline should be stopped.
    
    For example, a percentage of data quality check monitoring null value may raise a fatal alert when the percentage of rows with a null value exceeds 30% of all rows.

| Alerting threshold  | Data quality check passed | Data quality KPI result is decreased | Data pipeline should be stopped |
|---------------------|:-------------------------:|:------------------------------------:|:-------------------------------:|
| **Warning**         |  :material-close-thick:   |                                      |                                 |
| **Error** (default) |                           |        :material-close-thick:        |                                 |
| **Fatal**           |                           |        :material-close-thick:        |     :material-close-thick:      |


## Checks configuration in YAML file
Data quality checks are defined as YAML files that support code completion in code editors, such as Visual Studio Code.
Data quality check definitions can be stored in the source code repository, and versioned along with any other data
pipeline or machine learning code.

Below is an example of the YAML file showing sample configuration of a profiling column data quality check nulls_percent.

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

## Data quality results

In DQO, data quality results (metrics captured by the [data quality sensor](../sensors/sensors.md)) are stored as Apache
Parquet files following the Apache Hive compatible folder tree, partitioned by connection name, table name, and month. 
For example, alerts for September 2022 for a single table would be stored in a file 
`.data/rule_results/c=conn_bq_17/t=austin_311.311_service_requests/m=2022-09-01/rule_results.0.parquet`. 

The sensor data are stored locally, allowing true multi-cloud data collection without accessing any sensitive data 
through an external cloud or SaaS solution.

The result data can simply be replicated to a data lake or cloud bucket. Later, any SQL engine capable of querying
Hive-compatible data can query the output files of the data quality tool. Data can be queried using Apache Hive,
Apache Spark, DataBricks, Google BigQuery, Presto, Trino, SQL Server PolyBase, AWS Athena, and AWS Redshift Spectrum.

Data cleaning is as simple as deleting a file from a folder, which can be done using the DQO user interface or CLI. 

## Testing date partitions

The majority of data quality solutions are limited to capturing data quality metrics for the whole table, without taking
into consideration that the old data is measured together with the most recent data. This limitation has serious 
implications, making many data quality results incorrect. DQO solves this challenge by allowing testing daily and 
monthly date partitions. DQO can capture metrics using a **GROUP BY** clause and supports daily and monthly slicing. 

Consider a simple data quality check that counts the percentage of rows with a non-negative value. A data quality
sensor that analyzes the whole table without time slicing, detecting a percentage of valid rows where the value of a 
tested column is greater or equal to 0, would run a SQL query similar to the following.

``` sql
SELECT CURRENT_DATETIME() as time_window,
100.0 * SUM(CASE WHEN tested_column >= 0 THEN 1 ELSE 0 END) /
COUNT(*) as percentage_valid FROM schema.table
```

The above data quality sensor may return the result as follows:

| time_window<br/>(metrics capture timestamp) |  percentage_valid |
|---------------------------------------------|------------------:|
| **2022-10-08**                              |            92.76% |

This query measures the percentage of valid rows (the value in the tested column is greater than 0), but the data 
quality issues with both the old and new rows will affect the final score equally. New issues that affected only 
yesterday's data may not be visible, as they are responsible for lowering the data quality score for only 1/356 of
one year’s data. Furthermore, daily or monthly partitioned data that is reloaded should be analyzed separately, for 
each daily partition.

DQO allows capturing metrics using a **GROUP BY** clause with daily or monthly time slicing. 
The following Google BigQuery query example captures time-sliced data to calculate metrics for each day separately.

``` sql hl_lines="1 4"
SELECT DATETIME_TRUNC(transaction_timestamp_column, DAY) as time_window,
100.0 * SUM(CASE WHEN tested_column >= 0 THEN 1 ELSE 0 END) /
COUNT(*) as percentage_valid FROM schema.table
GROUP BY time_window
```

The results captured by the data quality sensor (a SQL query above) may look like this: 

| time_window    | percentage_valid |
|----------------|-----------------:|
| **2022-10-04** |            95.5% |
| **2022-10-05** |            96.1% |
| **2022-10-06** |            94.9% |
| **2022-10-07** |            95.1% |
| **2022-10-08** |        **82.2%** |

Here we can easily identify a significantly large drop in the percentage of valid rows on 2022-10-08, which is below 
the average of ~95% of valid rows each day before the day of the data quality incident. A score in the query which did 
not group the data by day and calculated an aggregate score for the table only detected a drop to 92.76% which was not 
too far from the average score. The examples above only show 5 days of data, but in a real database, this drop will be 
below the average daily change in the metric value.

## Data stream segmentation

An important aspect of data monitoring is the ability to calculate data quality metrics for different groups of rows
stored in the same table. Data in the fact table can be loaded from different sources such as countries, states, or
received from different external sources. Each stream of data would be loaded by a different pipeline. Data pipelines
for different data streams may fail independently of each other.

Data streams can be identified by a discriminator column, such as country or state. DQO can analyze data within separate
segments with a GROUP BY <data_stream_discriminator_column> clause to the data quality queries. DQO support setting of up
to 9 different data streams. Below is an example of a query with grouping by country.

``` sql hl_lines="4 5"
SELECT CURRENT_DATETIME() as time_window,
100.0 * SUM(CASE WHEN tested_column >= 0 THEN 1 ELSE 0 END) /
COUNT(*) as percentage_valid,
country as stream_level_1 FROM schema.table
GROUP BY stream_level_1
```

The results pivoted for readability might look as follows:

| Time_window<br/>(metrics capture timestamp) |     US |     UK |     DE |
|---------------------------------------------|-------:|-------:|-------:|
| **2022-10-08**                              |  94.7% |  95.8% |  95.2% | 

Data quality scores, which are calculated for each data source or vendor separately, can simplify the root cause
analysis by linking the data quality incident to a data source, a data stream, an external data supplier or simply
a separate data pipeline that has loaded invalid data.

## Integration of data partitions with data segmentation by data streams

Data partitions can be integrated with data segmentation by data streams. 

For example, the GROUP BY clause can list columns that divide the data set by a day and a data stream discriminator column
(country in this example). A complete SQL query that the DQO tool would execute on the data source look like this:

``` sql hl_lines="1 4 5"
SELECT DATETIME_TRUNC(transaction_timestamp_column, DAY) as time_window,
100.0 * SUM(CASE WHEN tested_column >= 0 THEN 1 ELSE 0 END) /
COUNT(*) as percentage_valid,
country as stream_level_1 FROM schema.table
GROUP BY time_window, stream_level_1
```

The results of this query collect data quality scores for each day and country separately, and allows accurate 
identification of the source of the data quality issue.

| Time_window    |     US |     UK |     DE |
|----------------|-------:|-------:|-------:|
| **2022-10-04** |  96.4% |  94.2% |  95.2% |
| **2022-10-05** |  95.3% |  94.7% |  95.6% |
| **2022-10-05** |  93.9% |  96.4% |  96.2% |
| **2022-10-07** |  94.8% |  94.9% |  95.4% |
| **2022-10-08** |  94.7% | **0%** |  95.2% |


## What's next

- [Learn more about profiling checks](profiling-checks/profiling-checks.md)
- [Learn more about monitoring checks](monitoring-checks/monitoring-checks.md)
- [Learn more about partition checks](./partition-checks/partition-checks.md)
- [Learn how to configure schedules](../../working-with-dqo/schedules/index.md)