# Detecting data quality issues with nulls
The type of nulls is documented here

## Detecting nulls at a table level
How to detect nulls on tables

## List of nulls checks at a table level

## Detecting nulls at a column level
How to detect nulls on column

## List of nulls checks at a column level
| Data quality check name | Data quality dimension | Description | Class |
|-------------------------|------------------------|-------------|-------|
|[nulls_count](../../checks/column/nulls/nulls-count.md)|Completeness|Column-level check that ensures that there are no more than a set number of null values in the monitored column.|standard|
|[nulls_percent](../../checks/column/nulls/nulls-percent.md)|Completeness|Column-level check that ensures that there are no more than a set percentage of null values in the monitored column.|advanced|
|[not_nulls_count](../../checks/column/nulls/not-nulls-count.md)|Completeness|Column-level check that ensures that there are no more than a set number of null values in the monitored column.|standard|
|[not_nulls_percent](../../checks/column/nulls/not-nulls-percent.md)|Completeness|Column-level check that ensures that there are no more than a set percentage of not null values in the monitored column.|standard|
|[nulls_percent_anomaly](../../checks/column/nulls/nulls-percent-anomaly.md)|Consistency|Column-level check that ensures that the null percent value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days. Use in partitioned checks.|advanced|
|[nulls_percent_change](../../checks/column/nulls/nulls-percent-change.md)|Consistency|Column-level check that ensures that the null percent in a monitored column has changed by a fixed rate since the last readout.|advanced|
|[nulls_percent_change_1_day](../../checks/column/nulls/nulls-percent-change-1-day.md)|Consistency|Column-level check that ensures that the null percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|advanced|
|[nulls_percent_change_7_days](../../checks/column/nulls/nulls-percent-change-7-days.md)|Consistency|Column-level check that ensures that the null percent in a monitored column has changed by a fixed rate since the last readout from last week.|advanced|
|[nulls_percent_change_30_days](../../checks/column/nulls/nulls-percent-change-30-days.md)|Consistency|Column-level check that ensures that the null percent in a monitored column has changed by a fixed rate since the last readout from last month.|advanced|

## What's next
- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
