# Detecting data quality issues with anomaly
Read this guide to learn how to detect anomalies in numeric data using DQOps.
The data quality checks are configured in the `anomaly` category in DQOps.

## What is an anomaly in data
Anomalies in data are unexpected values outside the regular range.

We can detect two types of anomalies in numeric columns with a data quality platform such as DQOps. We can detect outliers,
which are new minimum and maximum values, or we can detect that the typical values such as mean (average), median, or sum
have changed.

### Sample data for anomaly detection
We will use a latitude column in a 311 Austin municipal services call history table,
which stores the latitude of the reported incident's location.

The following screen shows the data profiling summary captured by DQOps for the latitude column, showing the top 10 most common values.

![Data profiling column result of a latitude column with sample values](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/numeric-column-latitude-profile-min.png){ loading=lazy }

We are expecting that the majority of service requests are reported in Austin, whose coordinates are 30°16′2″N 97°44′35″W.
The city center is at  30°16′2″N, so the values in the latitude column should be around that value.
Indeed, the profiling results show that the mean latitude is 30.28, which is 30° 16' 48"N, and it is not far away.

### Data outliers
An invalid value is present that is far below the minimum value or the maximum value.
We can detect such outliers by detecting that the minimum or maximum value in a column has changed since the last time 
[data quality checks were run](../running-data-quality-checks.md).


### Typical values out of range


![Data anomalies in a mean value of latitude in partitions table view](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/numeric-mean-anomaly-partitions-result-table-min.png){ loading=lazy }

![Data anomalies in a mean value of latitude in partitions chart view](https://dqops.com/docs/images/concepts/categories-of-data-quality-checks/numeric-mean-anomaly-partitions-chart-min.png){ loading=lazy }


## Detecting anomalies
How to detect anomaly data quality issues.



## List of anomaly checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*sum_anomaly*](../../checks/column/anomaly/sum-anomaly.md)|Consistency|A column-level check that ensures that the sum in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|:material-check-bold:|
|[*mean_anomaly*](../../checks/column/anomaly/mean-anomaly.md)|Consistency|A column-level check that ensures that the mean value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|:material-check-bold:|
|[*median_anomaly*](../../checks/column/anomaly/median-anomaly.md)|Consistency|A column-level check that ensures that the median in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.| |
|[*mean_change*](../../checks/column/anomaly/mean-change.md)|Consistency|A column-level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout.| |
|[*mean_change_1_day*](../../checks/column/anomaly/mean-change-1-day.md)|Consistency|A column-level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout from yesterday.| |
|[*mean_change_7_days*](../../checks/column/anomaly/mean-change-7-days.md)|Consistency|A column-level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout from last week.| |
|[*mean_change_30_days*](../../checks/column/anomaly/mean-change-30-days.md)|Consistency|A column-level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout from last month.| |
|[*median_change*](../../checks/column/anomaly/median-change.md)|Consistency|A column-level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout.| |
|[*median_change_1_day*](../../checks/column/anomaly/median-change-1-day.md)|Consistency|A column-level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout from yesterday.| |
|[*median_change_7_days*](../../checks/column/anomaly/median-change-7-days.md)|Consistency|A column-level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout from last week.| |
|[*median_change_30_days*](../../checks/column/anomaly/median-change-30-days.md)|Consistency|A column-level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout from last month.| |
|[*sum_change*](../../checks/column/anomaly/sum-change.md)|Consistency|A column-level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout.| |
|[*sum_change_1_day*](../../checks/column/anomaly/sum-change-1-day.md)|Consistency|A column-level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout from yesterday.| |
|[*sum_change_7_days*](../../checks/column/anomaly/sum-change-7-days.md)|Consistency|A column-level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout from last week.| |
|[*sum_change_30_days*](../../checks/column/anomaly/sum-change-30-days.md)|Consistency|A column-level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout from last month.| |


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/anomaly](../../checks/column/anomaly/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../data-quality-dimensions.md) used by DQOps
