# Detecting data quality issues with availability
The type of availability is documented here

## Detecting availability at a table level
How to detect availability on tables

## List of availability checks at a table level
| Data quality check name | Data quality dimension | Description | Class |
|-------------------------|------------------------|-------------|-------|
|[table_availability](../../checks/table/availability/table-availability.md)|Availability|Table-level check that verifies that a query can be executed on a table and that the server does not return errors, that the table exists, and that the table is accessible (queryable).
 The actual value (the result of the check) is the number of failures. When the table is accessible and a simple query was executed without errors, the result is 0.0.
 The sensor result (the actual value) 1.0 means that there is a failure. A value higher than 1.0 is stored only in the check result table and it is the number of consecutive failures in following days.|standard|

## Detecting availability at a column level
How to detect availability on column

## List of availability checks at a column level

## What's next
- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
