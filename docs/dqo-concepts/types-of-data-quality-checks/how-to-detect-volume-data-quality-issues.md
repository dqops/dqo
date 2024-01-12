# Detecting data quality issues with volume
The type of volume is documented here

## Detecting volume at a table level
How to detect volume on tables

## List of volume checks at a table level
| Data quality check name | Data quality dimension | Description | Class |
|-------------------------|------------------------|-------------|-------|
|[row_count](../../checks/table/volume/row-count.md)|Completeness|Row count (select count(*) from ...) test that runs a row_count check, obtains a count of rows and verifies the number by calling the row count rule.
 The default configuration for all severity rules (warning, error, fatal data quality issue) is to require at least one row, which checks if the table is not empty.|standard|
|[row_count_anomaly](../../checks/table/volume/row-count-anomaly.md)|Consistency|Table-level check that ensures that the row count is within a two-tailed percentile from measurements made during the last 90 days.|advanced|
|[row_count_change](../../checks/table/volume/row-count-change.md)|Consistency|Table-level check that ensures that the row count changed by a fixed rate since the last readout.|advanced|
|[row_count_change_1_day](../../checks/table/volume/row-count-change-1-day.md)|Consistency|Table-level check that ensures that the row count changed by a fixed rate since the last readout from yesterday.|advanced|
|[row_count_change_7_days](../../checks/table/volume/row-count-change-7-days.md)|Consistency|Table-level check that ensures that the row count changed by a fixed rate since the last readout from last week.|advanced|
|[row_count_change_30_days](../../checks/table/volume/row-count-change-30-days.md)|Consistency|Table-level check that ensures that the row count changed by a fixed rate since the last readout from last month.|advanced|

## Detecting volume at a column level
How to detect volume on column

## List of volume checks at a column level

## What's next
- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
