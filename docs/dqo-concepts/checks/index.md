# Checks overview

In DQOps, the check is a data quality test that can be run on both table or column levels. The check consists of a 
[data quality sensor](../sensors/sensors.md) and a [data quality rule](../rules/rules.md).

The data quality sensor reads the value from the data source at a given point in time. The data quality rule includes 
a set of conditions (thresholds) that the sensor readout must meet. When the conditions are not met, the check detects 
an issue with your data, and it creates an [incident that can be viewed, filtered, and managed](../../working-with-dqo/incidents-and-notifications/incidents.md).

The components involved in running a data quality check are shown below.
The example below shows how DQOps performs the [daily_row_count](../../checks/table/volume/row-count.md#daily-row-count-)
data quality check that verifies if the number of rows in the monitored table is greater than the expected minimum row count.

![Data quality check components](https://dqops.com/docs/images/concepts/data_quality_check_structure_min.png)

The data quality check is evaluated on a monitored table (or column) in three phases.

- The placeholders for the table name (and column name) **[sensor](../sensors/sensors.md) template** are
  filled in a templated SQL query (called a data quality sensor) 


- The generated SQL query is execute SQL query on the data source, capturing the data quality measure.
  All data quality sensors in DQOps are expected to return a result column named *actual_value* as a
  data measure that will be evaluated with data quality rules.


- The data quality metric (called *sensor readout* in DQOps) is passed to a [data quality rule](../rules/rules.md) that is
  a Python function that will decide if the measure (sensor readout) should be accepted, or the data quality
  check should fail and generate a data quality issue at one of three severity levels: warning, error, fatal.
  
  The data quality measure (sensor readout) is passed up to tree data quality rules, because data quality rules
  for warning, error and fatal severity levels use different parameters (thresholds).  


## Configuring data quality checks
Data quality checks are defined as YAML files that support code completion in code editors, such as [Visual Studio Code](../../integrations/visual-studio-code/index.md).
Data quality check definitions can be stored in the source code repository, and versioned along with any other data
pipeline or machine learning code. The folder structure where DQOps stores those YAML files is called `DQOps user home`
and is documented in the [configuring checks](./configuring-checks.md) article.

Below is an example of the YAML file showing sample configuration of a profiling column data quality check
[profile_nulls_percent](../../checks/column/nulls/nulls-percent.md#profile-nulls-percent-).

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

1.  The node that contains configuration of checks. In this example, those are [profiling checks](./profiling-checks/profiling-checks.md)
    defined at a column level.

2.  The name of the check category. Check categories are grouping similar checks.

3.  The check name that is configured.

4.  The **[warning](#warning)** severity rule configuration.

5.  The **[error](#error)** severity rule configuration.

6.  The **[fatal](#fatal)** severity rule configuration.

The `spec` section contains the details of the table, including the target schema and table name. 

The `columns` section lists the columns in the table which has configured checks. In this example the column named 
`target_column` has a configured check `profile_nulls_percent`. This means that the sensor reads the percentage of null
values in `target_column`. If the percentage exceeds a certain threshold, an error, warning, or fatal message will
be raised.

The structure of the table configuration file is described in the [configuring checks](./configuring-checks.md) section.


## Issue severity levels
Each data quality check supports configuring the alerting thresholds at three levels: *warning*, *error* and *fatal*.
DQOps will pass the [sensor](../sensors/sensors.md) (the captured data quality metric, such as a percentage of null values)
to all three [data quality rules](../rules/rules.md), using different thresholds.
If rules at multiple severity levels identify a data quality issue (the rule fails), DQOps picks the severity level
of the most severe rule that failed in the order: *fatal*, *error*, *warning*.

The rule severity levels are described below.

### **Warning**
A warning level alerting threshold raises warnings for less important data quality issues,
usually anomalies or expected random or seasonal data quality issues. Warnings are
not treated as data quality issues. Data quality checks that did not pass the warning alerting rule, but did pass the
error and fatal alerting rules are still counted as passed data quality checks and do not reduce the
[data quality KPIs](../data-quality-kpis/data-quality-kpis.md) score. Warnings should be used to identify potential data
quality issues that should be monitored, but the data producer should not take accountability for them.

For example, a percentage of data quality check monitoring null value may raise a warning when the percentage of rows with a null value exceeds 1% of all rows.


### **Error**
The error is the default alerting level for monitoring checks, comparable to the "error" level in logging libraries.
Data quality checks that failed to pass the rule evaluation at the "error" severity level
are considered failed data quality checks for the purpose of calculating the [data quality KPI](../data-quality-kpis/data-quality-kpis.md) score.

For example, a percentage of data quality check monitoring null value may raise an error when the percentage of rows with a null value exceeds 5% of all rows.


### **Fatal**
The fatal severity level is the highest alerting threshold that should only be used to identify severe data quality issues.
These issues should result in stopping the data pipelines before the issue spreads throughout the system. Fatal data
quality issues are treated as failed data quality checks and reduce the [data quality KPIs](../data-quality-kpis/data-quality-kpis.md)
score. The fatal threshold should be used with caution. It is mainly useful when the data pipeline can trigger the data
quality check assessment and wait for the result. If any data quality check raises a fatal data quality issue, the data
pipeline should be stopped.

For example, a percentage of data quality check monitoring null value may raise a fatal alert when the percentage of rows with a null value exceeds 30% of all rows.

### **Rule severity matrix**
The purpose of reporting data quality issues at different severity levels is summarized below.

| Alerting threshold  | Data quality check passed | Data quality KPI result is decreased | Data pipeline should be stopped |
|---------------------|:-------------------------:|:------------------------------------:|:-------------------------------:|
| **Warning**         |  :material-close-thick:   |                                      |                                 |
| **Error** (default) |                           |        :material-close-thick:        |                                 |
| **Fatal**           |                           |        :material-close-thick:        |     :material-close-thick:      |


## Types of checks

In DQOps, data quality checks are divided into 3 types:

- [**Profiling checks**](./profiling-checks/profiling-checks.md) are designed for assessing the initial data quality score
  of a data source. Profiling checks are also useful for
  exploring and experimenting with various types of checks and determining the most suitable ones for regular data quality monitoring.


- [**Monitoring checks**](./monitoring-checks/monitoring-checks.md) are standard checks that monitor the data quality of a
  table or column. These checks create a single data quality result for the entire table or column. There are two categories
  of monitoring checks: daily checks and monthly checks. When run multiple times per day, the **daily checks** store only
  the most recent result for each day. **Monthly checks** store the most recent results for each month the data quality
  checks were run.


- [**Partition checks**](./partition-checks/partition-checks.md) are designed to measure the data quality in **partitioned data**.
  In contrast to monitoring checks, partition checks produce separate monitoring results for each partition.
  To run a partition check, you need to select a column that serves as the time partitioning key for the data.
  Partition checks are also divided into two categories: daily checks and monthly checks.
  Partition checks are designed for [incremental data quality monitoring](../data-quality-kpis/incremental-data-quality-monitoring.md).


## Categories of checks
Each type of checks is divided into two main categories: table and column. Table-level data quality checks are used to
evaluate the table at a high-level without reference to individual columns, while column-level checks are run on
specific column. Built-in checks available in DQOps are divided into the following subcategories.

You can access the full lists of available checks with detailed descriptions by clicking on a link.

### **Table checks**

| Subcategory                                              | Description                                                                                                                                                                                                                                 |
|----------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Volume](../../checks/table/volume/index.md)             | Evaluates the overall quality of the table by verifying the number of rows.                                                                                                                                                                 |
| [Timeliness](../../checks/table/timeliness/index.md)     | Assesses the freshness and staleness of data, as well as data ingestion delay and reload lag for partitioned data.                                                                                                                          |
| [Accuracy](../../checks/table/accuracy/index.md)         | Compares the tested table with another (reference) table.                                                                                                                                                                                   |
| [SQL](../../checks/table/sql/index.md)                   | Validate data against user-defined SQL queries at the table level. Checks in this group allow for validation that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range. |
| [Availability](../../checks/table/availability/index.md) | Checks whether the table is accessible and available for use.                                                                                                                                                                               |                                                                                                                                                                                                                                            |
| [Schema](../../checks/table/schema/index.md)             | Detects changes in the schema (schema drifts).                                                                                                                                                                                              |                                                                                                                                                                                                                                            |


### **Column checks**

| Subcategory                                           | Description                                                                                                                                                                                                                                |
|-------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Nulls](../../checks/column/nulls/index.md)           | Checks for the presence of null or missing values in a column.                                                                                                                                                                             |
| [Numeric](../../checks/column/numeric/index.md)       | Validates that the data in a numeric column is in the expected format or within predefined ranges.                                                                                                                                         |
| [Strings](../../checks/column/strings/index.md)       | Validates that the data in a string column match the expected format or pattern.                                                                                                                                                           |
| [Uniqueness](../../checks/column/uniqueness/index.md) | Counts the number or percent of duplicate or unique values in a column.                                                                                                                                                                    |
| [DateTime](../../checks/column/datetime/index.md)     | Validates that the data in a date or time column is in the expected format and within predefined ranges.                                                                                                                                   |
| [PII](../../checks/column/pii/index.md)               | Checks for the presence of sensitive or personally identifiable information (PII) in a column such as email, phone, zip code, IP4 and IP6 addresses.                                                                                       |
| [SQL](../../checks/column/sql/index.md)               | Validate data against user-defined SQL queries at the column level. Checks in this group allows to validate that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range. |
| [Bool](../../checks/column/bool/index.md)             | Calculates the percentage of data in a Boolean format.                                                                                                                                                                                     |
| [Integrity](../../checks/column/integrity/index.md)   | Checks the referential integrity of a column against a column in another table.                                                                                                                                                            |
| [Accuracy](../../checks/column/accuracy/index.md)     | Verifies that percentage of the difference in sum of a column with a reference column.                                                                                                                                                     |
| [Datatype](../../checks/column/datatype/index.md)     | Detects changes in the datatype.                                                                                                                                                                                                           |
| [Anomaly](../../checks/column/anomaly/index.md)       | Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.                                                                                                            |
| [Schema](../../checks/column/schema/index.md)         | Detects changes in the schema.                                                                                                                                                                                                             |



## What's next

- Learn how to [configure data quality checks](../../dqo-concepts/checks/configuring-checks.md)
- Learn how to [run data quality checks](../../dqo-concepts/running-checks/running-checks.md)
- [Learn more about profiling checks](profiling-checks/profiling-checks.md)
- [Learn more about monitoring checks](monitoring-checks/monitoring-checks.md)
- [Learn more about partition checks](./partition-checks/partition-checks.md)
