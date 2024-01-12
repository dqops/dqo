# Detecting data quality issues with uniqueness
The type of uniqueness is documented here

## Detecting uniqueness at a table level
How to detect uniqueness on tables

## List of uniqueness checks at a table level

## Detecting uniqueness at a column level
How to detect uniqueness on column

## List of uniqueness checks at a column level
| Data quality check name | Data quality dimension | Description | Class |
|-------------------------|------------------------|-------------|-------|
|[distinct_count](../../checks/column/uniqueness/distinct-count.md)|Uniqueness|Column-level check that ensures that the number of unique values in a column does not fall below the minimum accepted count.|standard|
|[distinct_percent](../../checks/column/uniqueness/distinct-percent.md)|Uniqueness|Column-level check that ensures that the percentage of unique values in a column does not fall below the minimum accepted percentage.|standard|
|[duplicate_count](../../checks/column/uniqueness/duplicate-count.md)|Uniqueness|Column-level check that ensures that the number of duplicate values in a column does not exceed the maximum accepted count.|standard|
|[duplicate_percent](../../checks/column/uniqueness/duplicate-percent.md)|Uniqueness|Column-level check that ensures that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.|advanced|
|[distinct_count_anomaly](../../checks/column/uniqueness/distinct-count-anomaly.md)|Consistency|Column-level check that ensures that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|standard|
|[distinct_percent_anomaly](../../checks/column/uniqueness/distinct-percent-anomaly.md)|Consistency|Column-level check that ensures that the distinct percent value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|advanced|
|[distinct_count_change](../../checks/column/uniqueness/distinct-count-change.md)|Consistency|Column-level check that ensures that the distinct count in a monitored column has changed by a fixed rate since the last readout.|advanced|
|[distinct_count_change_1_day](../../checks/column/uniqueness/distinct-count-change-1-day.md)|Consistency|Column-level check that ensures that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|advanced|
|[distinct_count_change_7_days](../../checks/column/uniqueness/distinct-count-change-7-days.md)|Consistency|Column-level check that ensures that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|advanced|
|[distinct_count_change_30_days](../../checks/column/uniqueness/distinct-count-change-30-days.md)|Consistency|Column-level check that ensures that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|advanced|
|[distinct_percent_change](../../checks/column/uniqueness/distinct-percent-change.md)|Consistency|Column-level check that ensures that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|advanced|
|[distinct_percent_change_1_day](../../checks/column/uniqueness/distinct-percent-change-1-day.md)|Consistency|Column-level check that ensures that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|advanced|
|[distinct_percent_change_7_days](../../checks/column/uniqueness/distinct-percent-change-7-days.md)|Consistency|Column-level check that ensures that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|advanced|
|[distinct_percent_change_30_days](../../checks/column/uniqueness/distinct-percent-change-30-days.md)|Consistency|Column-level check that ensures that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|advanced|

## What's next
- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
