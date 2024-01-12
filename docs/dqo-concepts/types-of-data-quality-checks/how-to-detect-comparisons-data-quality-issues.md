# Detecting data quality issues with comparisons
The type of comparisons is documented here

## Detecting comparisons at a table level
How to detect comparisons on tables

## List of comparisons checks at a table level

## Detecting comparisons at a column level
How to detect comparisons on column

## List of comparisons checks at a column level
| Data quality check name | Data quality dimension | Description | Class |
|-------------------------|------------------------|-------------|-------|
|[sum_match](../../checks/column/comparisons/sum-match.md)|Accuracy|Column-level check that ensures that compares the sum of the values in the tested column to the sum of values in a reference column from the reference table.
 Compares the sum of values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).|standard|
|[min_match](../../checks/column/comparisons/min-match.md)|Accuracy|Column-level check that ensures that compares the minimum value in the tested column to the minimum value in a reference column from the reference table.
 Compares the minimum values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).|standard|
|[max_match](../../checks/column/comparisons/max-match.md)|Accuracy|Column-level check that ensures that compares the maximum value in the tested column to maximum value in a reference column from the reference table.
 Compares the maximum values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).|standard|
|[mean_match](../../checks/column/comparisons/mean-match.md)|Accuracy|Column-level check that ensures that compares the mean (average) of the values in the tested column to the mean (average) of values in a reference column from the reference table.
 Compares the mean (average) value for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).|standard|
|[not_null_count_match](../../checks/column/comparisons/not-null-count-match.md)|Accuracy|Column-level check that ensures that compares the count of not null values in the tested column to the count of not null values in a reference column from the reference table.
 Compares the count of not null values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).|standard|
|[null_count_match](../../checks/column/comparisons/null-count-match.md)|Accuracy|Column-level check that ensures that compares the count of null values in the tested column to the count of null values in a reference column from the reference table.
 Compares the count of null values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).|standard|

## What's next
- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
