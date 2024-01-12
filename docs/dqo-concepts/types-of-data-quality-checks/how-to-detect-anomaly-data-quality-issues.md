# Detecting data quality issues with anomaly
The type of anomaly is documented here

## Detecting anomaly at a table level
How to detect anomaly on tables

## List of anomaly checks at a table level

## Detecting anomaly at a column level
How to detect anomaly on column

## List of anomaly checks at a column level
| Data quality check name | Data quality dimension | Description | Class |
|-------------------------|------------------------|-------------|-------|
|[mean_anomaly](../../checks/column/anomaly/mean-anomaly.md)|Consistency|Column level check that ensures that the mean value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|advanced|
|[median_anomaly](../../checks/column/anomaly/median-anomaly.md)|Consistency|Column level check that ensures that the median in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|advanced|
|[sum_anomaly](../../checks/column/anomaly/sum-anomaly.md)|Consistency|Column level check that ensures that the sum in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|standard|
|[mean_change](../../checks/column/anomaly/mean-change.md)|Consistency|Column level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout.|advanced|
|[mean_change_1_day](../../checks/column/anomaly/mean-change-1-day.md)|Consistency|Column level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout from yesterday.|advanced|
|[mean_change_7_days](../../checks/column/anomaly/mean-change-7-days.md)|Consistency|Column level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout from last week.|advanced|
|[mean_change_30_days](../../checks/column/anomaly/mean-change-30-days.md)|Consistency|Column level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout from last month.|advanced|
|[median_change](../../checks/column/anomaly/median-change.md)|Consistency|Column level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout.|advanced|
|[median_change_1_day](../../checks/column/anomaly/median-change-1-day.md)|Consistency|Column level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout from yesterday.|advanced|
|[median_change_7_days](../../checks/column/anomaly/median-change-7-days.md)|Consistency|Column level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout from last week.|advanced|
|[median_change_30_days](../../checks/column/anomaly/median-change-30-days.md)|Consistency|Column level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout from last month.|advanced|
|[sum_change](../../checks/column/anomaly/sum-change.md)|Consistency|Column level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout.|standard|
|[sum_change_1_day](../../checks/column/anomaly/sum-change-1-day.md)|Consistency|Column level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout from yesterday.|advanced|
|[sum_change_7_days](../../checks/column/anomaly/sum-change-7-days.md)|Consistency|Column level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout from last week.|advanced|
|[sum_change_30_days](../../checks/column/anomaly/sum-change-30-days.md)|Consistency|Column level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout from last month.|advanced|

## What's next
- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
