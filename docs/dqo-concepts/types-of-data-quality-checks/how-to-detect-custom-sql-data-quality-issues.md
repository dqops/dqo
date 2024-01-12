# Detecting data quality issues with custom sql
The type of custom sql is documented here

## Detecting custom sql at a table level
How to detect custom sql on tables

## List of custom sql checks at a table level

## Detecting custom sql at a column level
How to detect custom sql on column

## List of custom sql checks at a column level
| Data quality check name | Data quality dimension | Description | Class |
|-------------------------|------------------------|-------------|-------|
|[sql_condition_passed_percent_on_column](../../checks/column/custom_sql/sql-condition-passed-percent-on-column.md)|Validity|Column level check that ensures that a set percentage of rows passed a custom SQL condition (expression).|advanced|
|[sql_condition_failed_count_on_column](../../checks/column/custom_sql/sql-condition-failed-count-on-column.md)|Validity|Column level check that ensures that there are no more than a set number of rows fail a custom SQL condition (expression) evaluated for a given column.|standard|
|[sql_aggregate_expression_on_column](../../checks/column/custom_sql/sql-aggregate-expression-on-column.md)|Reasonableness|Column level check that calculates a given SQL aggregate expression on a column and compares it with a maximum accepted value.|advanced|

## What's next
- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
