# Detecting data quality issues with nulls
Read this guide to learn what types of data quality checks are supported in DQOps to detect issues related to nulls.
The data quality checks are configured in the `nulls` category in DQOps.

## Nulls category
Data quality checks that are detecting issues related to nulls are listed below.

## Detecting nulls issues
How to detect nulls data quality issues.

## Use cases
| **Name of the example**                                                          | **Description**                                                                                                                                   |
|:---------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------|
| [Detect incomplete columns](../examples/data-completeness/detect-null-values.md) | This example shows how to incomplete columns that have too many null values using the [nulls_count](../checks/column/nulls/nulls-count.md) check. |

## List of nulls checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*nulls_count*](../checks/column/nulls/nulls-count.md)|Completeness|Detects incomplete columns that contain any *null* values. Counts the number of rows having a null value. Raises a data quality issue when the count of null values is above a *max_count* threshold.|:material-check-bold:|
|[*nulls_percent*](../checks/column/nulls/nulls-percent.md)|Completeness|Detects incomplete columns that contain any *null* values. Measures the percentage of rows having a null value. Raises a data quality issue when the percentage of null values is above a *max_percent* threshold. Configure this check to accept a given percentage of null values by setting the *max_percent* parameter.|:material-check-bold:|
|[*nulls_percent_anomaly*](../checks/column/nulls/nulls-percent-anomaly.md)|Consistency|Detects day-to-day anomalies in the percentage of *null* values. Measures the percentage of rows having a *null* value. Raises a data quality issue when the rate of null values increases or decreases too much.|:material-check-bold:|
|[*not_nulls_count*](../checks/column/nulls/not-nulls-count.md)|Completeness|Detects empty columns that contain only *null* values. Counts the number of rows that have non-null values. Raises a data quality issue when the count of non-null values is below *min_count*. The default value of the *min_count* parameter is 1, but DQOps supports setting a higher number to assert that a column has at least that many non-null values.|:material-check-bold:|
|[*not_nulls_percent*](../checks/column/nulls/not-nulls-percent.md)|Completeness|Detects incomplete columns that contain too few non-null values. Measures the percentage of rows that have non-null values. Raises a data quality issue when the percentage of non-null values is below *min_percentage*. The default value of the *min_percentage* parameter is 100.0, but DQOps supports setting a lower value to accept some nulls.| |
|[*nulls_percent_change*](../checks/column/nulls/nulls-percent-change.md)|Consistency|Detects relative increases or decreases in the percentage of null values since the last measured percentage. Measures the percentage of null values for each day. Raises a data quality issue when the change in the percentage of null values is above *max_percent* of the previous percentage.| |
|[*nulls_percent_change_1_day*](../checks/column/nulls/nulls-percent-change-1-day.md)|Consistency|Detects relative increases or decreases in the percentage of null values since the previous day. Measures the percentage of null values for each day. Raises a data quality issue when the change in the percentage of null values is above *max_percent* of the previous percentage.| |
|[*nulls_percent_change_7_days*](../checks/column/nulls/nulls-percent-change-7-days.md)|Consistency|Detects relative increases or decreases in the percentage of null values since the last week (seven days ago). Measures the percentage of null values for each day. Raises a data quality issue when the change in the percentage of null values is above *max_percent* of the previous percentage.| |
|[*nulls_percent_change_30_days*](../checks/column/nulls/nulls-percent-change-30-days.md)|Consistency|Detects relative increases or decreases in the percentage of null values since the last month (30 days ago). Measures the percentage of null values for each day. Raises a data quality issue when the change in the percentage of null values is above *max_percent* of the previous percentage.| |


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/nulls](../checks/column/nulls/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
