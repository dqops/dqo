# Detecting data quality issues with accuracy
The type of accuracy is documented here

## Detecting accuracy at a table level
How to detect accuracy on tables

## List of accuracy checks at a table level

## Detecting accuracy at a column level
How to detect accuracy on column

## List of accuracy checks at a column level
| Data quality check name | Data quality dimension | Description | Class |
|-------------------------|------------------------|-------------|-------|
|[total_sum_match_percent](../../checks/column/accuracy/total-sum-match-percent.md)|Accuracy|Column-level check that ensures that there are no more than a maximum percentage of difference of sum of a table column and of a sum of another table column.|standard|
|[total_min_match_percent](../../checks/column/accuracy/total-min-match-percent.md)|Accuracy|Column level check that ensures that there are no more than a maximum percentage of difference of min of a table column and of a min of another table column.|standard|
|[total_max_match_percent](../../checks/column/accuracy/total-max-match-percent.md)|Accuracy|Column level check that ensures that there are no more than a maximum percentage of difference of max of a table column and of a max of another table column.|standard|
|[total_average_match_percent](../../checks/column/accuracy/total-average-match-percent.md)|Accuracy|Column level check that ensures that there are no more than a maximum percentage of difference of average of a table column and of an average of another table column.|standard|
|[total_not_null_count_match_percent](../../checks/column/accuracy/total-not-null-count-match-percent.md)|Accuracy|Column level check that ensures that there are no more than a maximum percentage of difference of the row count of a tested table&#x27;s column (counting the not null values) and of an row count of another (reference) table, also counting all rows with not null values.|standard|

## What's next
- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
