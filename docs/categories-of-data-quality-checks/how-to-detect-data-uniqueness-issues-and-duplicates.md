# Detecting data quality issues with uniqueness
Read this guide to learn what types of data quality checks are supported in DQOps to detect issues related to uniqueness.
The data quality checks are configured in the `uniqueness` category in DQOps.

## Uniqueness category
Data quality checks that are detecting issues related to uniqueness are listed below.

## Detecting uniqueness issues
How to detect uniqueness data quality issues.

## Use cases
| **Name of the example**                                                             | **Description**                                                                                                                                                                                                         |
|:------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Percentage of duplicates](../examples/data-uniqueness/percentage-of-duplicates.md) | This example shows how to detect that the percentage of duplicate values in a column does not exceed the maximum accepted percentage using [duplicate_percent](../checks/column/uniqueness/duplicate-percent.md) check. |

## List of uniqueness checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*distinct_count*](../checks/column/uniqueness/distinct-count.md)|Uniqueness|A column-level check that ensures that the number of unique values in a column does not fall below the minimum accepted count.|:material-check-bold:|
|[*distinct_percent*](../checks/column/uniqueness/distinct-percent.md)|Uniqueness|A column-level check that ensures that the percentage of unique values in a column does not fall below the minimum accepted percentage.|:material-check-bold:|
|[*duplicate_count*](../checks/column/uniqueness/duplicate-count.md)|Uniqueness|A column-level check that ensures that the number of duplicate values in a column does not exceed the maximum accepted count.|:material-check-bold:|
|[*duplicate_percent*](../checks/column/uniqueness/duplicate-percent.md)|Uniqueness|A column-level check that ensures that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.| |
|[*distinct_count_anomaly*](../checks/column/uniqueness/distinct-count-anomaly.md)|Consistency|A column-level check that ensures that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|:material-check-bold:|
|[*distinct_percent_anomaly*](../checks/column/uniqueness/distinct-percent-anomaly.md)|Consistency|A column-level check that ensures that the distinct percent value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.| |
|[*distinct_count_change*](../checks/column/uniqueness/distinct-count-change.md)|Consistency|A column-level check that ensures that the distinct count in a monitored column has changed by a fixed rate since the last readout.| |
|[*distinct_count_change_1_day*](../checks/column/uniqueness/distinct-count-change-1-day.md)|Consistency|A column-level check that ensures that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.| |
|[*distinct_count_change_7_days*](../checks/column/uniqueness/distinct-count-change-7-days.md)|Consistency|A column-level check that ensures that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.| |
|[*distinct_count_change_30_days*](../checks/column/uniqueness/distinct-count-change-30-days.md)|Consistency|A column-level check that ensures that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.| |
|[*distinct_percent_change*](../checks/column/uniqueness/distinct-percent-change.md)|Consistency|A column-level check that ensures that the distinct percent in a monitored column has changed by a fixed rate since the last readout.| |
|[*distinct_percent_change_1_day*](../checks/column/uniqueness/distinct-percent-change-1-day.md)|Consistency|A column-level check that ensures that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.| |
|[*distinct_percent_change_7_days*](../checks/column/uniqueness/distinct-percent-change-7-days.md)|Consistency|A column-level check that ensures that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.| |
|[*distinct_percent_change_30_days*](../checks/column/uniqueness/distinct-percent-change-30-days.md)|Consistency|A column-level check that ensures that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.| |


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/uniqueness](../checks/column/uniqueness/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../dqo-concepts/definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../dqo-concepts/running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../dqo-concepts/configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../dqo-concepts/data-quality-dimensions.md) used by DQOps
