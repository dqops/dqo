# Detecting data quality issues with comparisons
Read this guide to learn what types of data quality checks are supported in DQOps to detect issues related to comparisons.
The data quality checks are configured in the `comparisons` category in DQOps.

## Comparisons category
Data quality checks that are detecting issues related to comparisons are listed below.

## Detecting comparisons issues
How to detect comparisons data quality issues.

## List of comparisons checks at a table level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*row_count_match*](../../checks/table/comparisons/row-count-match.md)|Accuracy|Table level comparison check that compares the row count of the current (parent) table with the row count of the reference table.|:material-check-bold:|
|[*column_count_match*](../../checks/table/comparisons/column-count-match.md)|Accuracy|Table level comparison check that compares the column count of the current (parent) table with the column count of the reference table.|:material-check-bold:|


**Reference and samples**

The full list of all data quality checks in this category is located in the [table/comparisons](../../checks/table/comparisons/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../definition-of-data-quality-sensors.md)
that are used by those checks.

## List of comparisons checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*sum_match*](../../checks/column/comparisons/sum-match.md)|Accuracy|A column-level check that ensures that compares the sum of the values in the tested column to the sum of values in a reference column from the reference table. Compares the sum of values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).|:material-check-bold:|
|[*min_match*](../../checks/column/comparisons/min-match.md)|Accuracy|A column-level check that ensures that compares the minimum value in the tested column to the minimum value in a reference column from the reference table. Compares the minimum values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).|:material-check-bold:|
|[*max_match*](../../checks/column/comparisons/max-match.md)|Accuracy|A column-level check that ensures that compares the maximum value in the tested column to maximum value in a reference column from the reference table. Compares the maximum values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).|:material-check-bold:|
|[*mean_match*](../../checks/column/comparisons/mean-match.md)|Accuracy|A column-level check that ensures that compares the mean (average) of the values in the tested column to the mean (average) of values in a reference column from the reference table. Compares the mean (average) value for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).|:material-check-bold:|
|[*not_null_count_match*](../../checks/column/comparisons/not-null-count-match.md)|Accuracy|A column-level check that ensures that compares the count of not null values in the tested column to the count of not null values in a reference column from the reference table. Compares the count of not null values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).|:material-check-bold:|
|[*null_count_match*](../../checks/column/comparisons/null-count-match.md)|Accuracy|A column-level check that ensures that compares the count of null values in the tested column to the count of null values in a reference column from the reference table. Compares the count of null values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).|:material-check-bold:|


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/comparisons](../../checks/column/comparisons/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../data-quality-dimensions.md) used by DQOps