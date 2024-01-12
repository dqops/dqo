# Detecting data quality issues with blanks
The type of blanks is documented here

## Detecting blanks at a table level
How to detect blanks on tables

## List of blanks checks at a table level

## Detecting blanks at a column level
How to detect blanks on column

## List of blanks checks at a column level
| Data quality check name | Data quality dimension | Description | Class |
|-------------------------|------------------------|-------------|-------|
|[empty_text_found](../../checks/column/blanks/empty-text-found.md)|Completeness|Column-level check that ensures that there are no more than a maximum number of empty texts in a monitored column.|standard|
|[whitespace_text_found](../../checks/column/blanks/whitespace-text-found.md)|Completeness|Column-level check that ensures that there are no more than a maximum number of whitespace texts in a monitored column.|standard|
|[null_placeholder_text_found](../../checks/column/blanks/null-placeholder-text-found.md)|Completeness|Column-level check that ensures that there are no more than a maximum number of rows with a null placeholder texts in a monitored column.|standard|
|[empty_text_percent](../../checks/column/blanks/empty-text-percent.md)|Completeness|Column-level check that ensures that there are no more than a maximum percent of empty texts in a monitored column.|advanced|
|[whitespace_text_percent](../../checks/column/blanks/whitespace-text-percent.md)|Completeness|Column-level check that ensures that there are no more than a maximum percent of whitespace texts in a monitored column.|advanced|
|[null_placeholder_text_percent](../../checks/column/blanks/null-placeholder-text-percent.md)|Completeness|Column-level check that ensures that there are no more than a maximum percent of rows with a null placeholder texts in a monitored column.|advanced|

## What's next
- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
