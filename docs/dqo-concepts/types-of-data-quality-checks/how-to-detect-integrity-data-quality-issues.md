# Detecting data quality issues with integrity
The type of integrity is documented here

## Detecting integrity at a table level
How to detect integrity on tables

## List of integrity checks at a table level

## Detecting integrity at a column level
How to detect integrity on column

## List of integrity checks at a column level
| Data quality check name | Data quality dimension | Description | Class |
|-------------------------|------------------------|-------------|-------|
|[lookup_key_not_found](../../checks/column/integrity/lookup-key-not-found.md)|Integrity|Column-level check that ensures that there are no more than a maximum number of values not matching values in another table column.|standard|
|[lookup_key_found_percent](../../checks/column/integrity/lookup-key-found-percent.md)|Integrity|Column-level check that ensures that there are no more than a minimum percentage of values matching values in another table column.|advanced|

## What's next
- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
