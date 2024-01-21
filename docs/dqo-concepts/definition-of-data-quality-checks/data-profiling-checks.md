# Data profiling checks
Read this guide to understand the purpose of data profiling checks in DQOps, and how they are used to evaluate the initial data quality status of tables.

## What are data profiling checks?
The data profiling checks in DQOps are responsible for **assessing the initial data quality score** of data sources.
They should be activated on new data sources to verify that the dataset meets the minimum data quality requirements.

Profiling checks are also useful for exploring and experimenting with various types of checks to determine the most suitable
ones for regular data quality monitoring.
Before activating a data quality  [**monitoring**](data-observability-monitoring-checks.md) check, the user should test
a **profiling** version of the data quality check. Every [**monitoring**](data-observability-monitoring-checks.md) and
[**partition**](partition-checks.md) data quality check has a **profiling** version, named as _profiling\_\*_.

### **Summary**
The following table summarizes the key concepts of *profiling* checks in DQOps.

| Check type      | Purpose                                                                                                                                                                                                                                                               | Time period truncation                                                                                                                        | Check name prefix |
|-----------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------|-------------------|
| **_profiling_** | Evaluate the initial data quality score of new tables.<br/>Experiment with DQOps data quality checks before activating [**monitoring**](data-observability-monitoring-checks.md) checks for measuring the [data quality KPIs](../definition-of-data-quality-kpis.md). | One data quality profiling result captured **per month**,<br/> when profiling is repeated in the same month, the previous result is replaced. | _profile\_\*_     |


## Data profiling status checkpoints
DQOps stores only the one data quality *profiling* result for each month. 
If user runs the same **profiling** again during the same month, the previous result is replaced.
This behavior is designed for experimentation and tuning the  parameters for the [data quality rules](../definition-of-data-quality-rules.md).

### **Profiling checks in DQOps user interface**
The following screen shows the profiling results for a [profile_row_count](../../checks/table/volume/row-count.md#profile-row-count)
data quality check that detects empty tables. By setting the `min_count` rule parameter to **1**, DQOps will raise an **error** [severity](index.md#issue-severity-levels)
data quality issue for empty tables. The data quality issue is stored in the [check_results](../../reference/parquetfiles/check_results.md) Parquet table.

![data profiling screen in DQOps](https://dqops.com/docs/images/concepts/types-of-data-quality-checks/table-profiling-checks-results-min.png)

The screenshot above shows the most recent data profiling results captured at *2024-01-20 16:18:49*.
The dates in the *Executed At* column show the dates when the *profiling* checks were executed according to a [CRON schedule](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md),
which is _0 12 \* \* \*_ (every day at 12 PM).
The *Profile data (local time)* is the time period (month) for which the profiling result is valid. The date is truncated
to the 1st day of the month when the [profiling data quality checks were run](../running-data-quality-checks.md#running-profiling-checks).

### **Initial data quality KPI score**
The [data quality dashboard](../types-of-data-quality-dashboards.md) showing the data profiling results are located in the *Profiling* folder.
The *Profiling KPIs scorecard - summary* dashboard shows the initial [data quality KPI score](../definition-of-data-quality-kpis.md),
which is calculated as a percentage of passed data profiling checks for the current month.

![data profiling initial data quality score dashboard](https://dqops.com/docs/images/concepts/types-of-data-quality-checks/data-profiling-data-quality-kpi-scorecard-min.png)

## Profiling checks pros and cons

### **When to use profiling checks**
Use the data profiling checks for:

- assessing the initial data quality score of new tables
- experimenting with data quality checks, DQOps has 150+ [built-in data quality checks](../../checks/index.md)
- testing custom data quality checks, and [sensors](../definition-of-data-quality-sensors.md), and [rules](../definition-of-data-quality-rules.md).
- testing changes to monitored tables before [data quality monitoring](data-observability-monitoring-checks.md) checks are activated,
  and the [data quality KPI score](../definition-of-data-quality-kpis.md) is used to verify compliance with **data contracts**.

### **Limitations of profiling checks**
Data profiling checks store only one result per month, which limit their usage only to assessing the initial data quality.

- Do not use the [DQOps REST API Client](../../client/index.md) from data pipelines for running *profiling* checks,
  unless there is a requirement to maintain the most recent data profiling status at all time. Run [monitoring](data-observability-monitoring-checks.md)
  data quality checks instead to capture the result for each day.

- Do not activate any **anomaly** detection and relative value lookup checks as *profiling* checks, because anomaly detection
  depends on feeding a full history of data quality results at least at a *daily* scale. Profiling checks in DQOps store only one result
  per month, so the history of data quality results is limited. The following screenshot shows how
  the DQOps [check editor](../dqops-user-interface-overview.md#check-editor) presents anomaly detection checks that cannot be run,
  because there is not enough historical data to be used for prediction.

![data anomaly detection by data profiling checks](https://dqops.com/docs/images/concepts/types-of-data-quality-checks/data-profiling-checks-not-supported-anomaly-in-editor-min.png)

## Profiling check configuration in DQOps YAML files
The configuration of active data quality profiling checks is stored in the [.dqotable.yaml](../configuring-table-metadata.md#table-yaml-file-structure)
files. 
Please review the samples in the [configuring table metadata](../configuring-table-metadata.md) article to learn more.

- [configuring table-level profiling checks](../configuring-data-quality-checks-and-rules.md#table-level-profiling-checks) shows 
  how to configure profiling checks at a *table* level 

- [configuring column-level profiling checks](../configuring-data-quality-checks-and-rules.md#column-profiling-checks) shows
  how to configure profiling checks at a *column* level


## What's next
- Learn how to monitor data quality using the [monitoring checks](data-observability-monitoring-checks.md).
- Learn how to analyze data quality of partitioned data using the [partition checks](partition-checks.md).
- Read the [configuring table-level profiling checks](../configuring-data-quality-checks-and-rules.md#table-level-profiling-checks) and
  [configuring column-level profiling checks](../configuring-data-quality-checks-and-rules.md#column-profiling-checks) to learn
  the details of configuring profiling checks in YAML files. 
- Learn how to use profiling checks in 
  the [end-to-end data quality improvement process](../definition-of-data-quality-kpis.md#data-quality-improvement-process) using DQOps. 
