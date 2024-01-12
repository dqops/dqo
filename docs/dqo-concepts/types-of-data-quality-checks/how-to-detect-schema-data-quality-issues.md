# Detecting data quality issues with schema
The type of schema is documented here

## Detecting schema at a table level
How to detect schema on tables

## List of schema checks at a table level

## Detecting schema at a column level
How to detect schema on column

## List of schema checks at a column level
| Data quality check name | Data quality dimension | Description | Class |
|-------------------------|------------------------|-------------|-------|
|[column_exists](../../checks/column/schema/column-exists.md)|Completeness|Column level check that reads the metadata of the monitored table and verifies that the column still exists in the data source.
 The data quality sensor returns 1.0 when the column was found or 0.0 when the column was not found.|standard|
|[column_type_changed](../../checks/column/schema/column-type-changed.md)|Consistency|Column level check that detects if the data type of the column has changed since the last time it was retrieved.
 This check calculates a hash of all the components of the column&#x27;s data type: the data type name, length, scale, precision and nullability.
 A data quality issue will be detected if the hash of the column&#x27;s data types has changed.|standard|

## What's next
- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
