# Detecting data quality issues with bool
The type of bool is documented here

## Detecting bool at a table level
How to detect bool on tables

## List of bool checks at a table level

## Detecting bool at a column level
How to detect bool on column

## List of bool checks at a column level
| Data quality check name | Data quality dimension | Description | Class |
|-------------------------|------------------------|-------------|-------|
|[true_percent](../../checks/column/bool/true-percent.md)|Reasonableness|Column level check that ensures that there are at least percentage of rows with a true value in a monitored column.|standard|
|[false_percent](../../checks/column/bool/false-percent.md)|Reasonableness|Column level check that ensures that there are at least a minimum percentage of rows with a false value in a monitored column.|standard|

## What's next
- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
