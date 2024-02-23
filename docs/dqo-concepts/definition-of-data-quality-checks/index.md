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
  
  The data quality measure (sensor readout) is passed up to three data quality rules because data quality rules
  for warning, error, and fatal severity levels use different parameters (thresholds).  


## Activating data quality checks

### **DQOps user interface**
Data quality checks are activated in the DQOps user interface on the [data quality check editor](../dqops-user-interface-overview.md#check-editor)
screen shown below.
The data quality check is activated by turning the green switch on the left to an *ON* position.
The alerting thresholds for [data quality issue severity levels](#issue-severity-levels) are configured in the *Warning threshold*, 
*Error threshold* and *Fatal threshold* columns.

The following example shows a [daily_row_count](../../checks/table/volume/row-count.md#daily-row-count) data quality check that measures
the row count captured for each day. It is a [*daily monitoring check*](data-observability-monitoring-checks.md#daily-monitoring-checks) type.
The table in this example stores a list of all known countries recognized by the United Nations. The last known row count was 249 rows (countries).
The configuration of alerting rules are:

- raise a [*warning* severity data quality issue](#warning) when the row count (the number of countries) drops below **240**
- raise an [*error* severity data quality issue](#error) when the row count (the number of countries) drops below **100**
- raise a [*fatal* severity data quality issue](#fatal) when the table is empty, because the row count (the number of countries) is below **1**

![data quality rule severity levels in DQOps check editor](https://dqops.com/docs/images/concepts/types-of-data-quality-checks/check_editor_rules-min.png)


### **DQOps YAML files**
Data quality checks are defined as [YAML files](../configuring-data-sources.md#dqops-yaml-file-example) that support code completion in code editors, such as [Visual Studio Code](../../integrations/visual-studio-code/index.md).
Data quality check definitions can be stored in the source code repository, and versioned along with any other data
pipeline or machine learning code. The folder structure where DQOps stores those YAML files is called `DQOps user home`
and is documented in the [configuring data quality checks](../configuring-data-quality-checks-and-rules.md) article.

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
Each data quality check supports setting the alerting thresholds at three severity levels: *warning*, *error*, and *fatal*.
DQOps evaluates the [sensor readout](../definition-of-data-quality-sensors.md) (the captured data quality metric, such as a percentage of null values)
by three [data quality rules](../definition-of-data-quality-rules.md), using different alerting thresholds configured as *rule parameters*.
If multiple rules at different severity levels identify a data quality issue (the rule fails), DQOps picks the severity level
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
| **Warning**         |   :material-check-bold:   |                                      |                                 |
| **Error** (default) |                           |        :material-check-bold:         |                                 |
| **Fatal**           |                           |        :material-check-bold:         |      :material-check-bold:      |


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


## Define custom check in DQOps user interface
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
  divided into [categories of data quality checks](../../categories-of-data-quality-checks/index.md)
