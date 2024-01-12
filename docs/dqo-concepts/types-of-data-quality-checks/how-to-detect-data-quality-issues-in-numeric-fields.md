# Detecting data quality issues with numeric
Read this guide to learn what types of data quality checks are supported in DQOps to detect issues related to numeric.
The data quality checks are configured in the `numeric` category in DQOps.

## numeric category
Data quality checks that are detecting issues related to numeric are listed below.

## Detecting numeric issues
How to detect numeric data quality issues.

## List of numeric checks at a table level

## List of numeric checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[number_below_min_value](../../checks/column/numeric/number-below-min-value.md)|Validity|Column-level check that ensures that the number of values in the monitored column with a value below the value defined by the user as a parameter does not exceed set thresholds.|:material-check-bold:|
|[number_above_max_value](../../checks/column/numeric/number-above-max-value.md)|Validity|Column level check that ensures that the number of values in the monitored column with a value above the value defined by the user as a parameter does not exceed set thresholds.|:material-check-bold:|
|[negative_values](../../checks/column/numeric/negative-values.md)|Validity|Column-level check that ensures that there are no more than a set number of negative values in a monitored column.|:material-check-bold:|
|[negative_values_percent](../../checks/column/numeric/negative-values-percent.md)|Validity|Column level check that ensures that there are no more than a set percentage of negative values in a monitored column.| |
|[number_below_min_value_percent](../../checks/column/numeric/number-below-min-value-percent.md)|Validity|Column level check that ensures that the percentage of values in the monitored column with a value below the value defined by the user as a parameter does not fall below set thresholds.| |
|[number_above_max_value_percent](../../checks/column/numeric/number-above-max-value-percent.md)|Validity|Column-level check that ensures that the percentage of values in the monitored column with a value above the value defined by the user as a parameter does not fall below set thresholds.| |
|[number_in_range_percent](../../checks/column/numeric/number-in-range-percent.md)|Reasonableness|Column level check that ensures that there are no more than a set percentage of values from range in a monitored column.| |
|[integer_in_range_percent](../../checks/column/numeric/integer-in-range-percent.md)|Reasonableness|Column-level check that ensures that there are no more than a set number of values from range in a monitored column.| |
|[min_in_range](../../checks/column/numeric/min-in-range.md)|Reasonableness|Column level check that ensures that the minimal values are in a set range in a monitored column.|:material-check-bold:|
|[max_in_range](../../checks/column/numeric/max-in-range.md)|Reasonableness|Column level check that ensures that the maximal values are in a set range in a monitored column.|:material-check-bold:|
|[sum_in_range](../../checks/column/numeric/sum-in-range.md)|Reasonableness|Column level check that ensures that the sum of values in a monitored column is in a set range.|:material-check-bold:|
|[mean_in_range](../../checks/column/numeric/mean-in-range.md)|Reasonableness|Column level check that ensures that the average (mean) of values in a monitored column is in a set range.| |
|[median_in_range](../../checks/column/numeric/median-in-range.md)|Reasonableness|Column level check that ensures that the median of values in a monitored column is in a set range.| |
|[percentile_in_range](../../checks/column/numeric/percentile-in-range.md)|Reasonableness|Column level check that ensures that the percentile of values in a monitored column is in a set range.| |
|[percentile_10_in_range](../../checks/column/numeric/percentile-10-in-range.md)|Reasonableness|Column level check that ensures that the percentile 10 of values in a monitored column is in a set range.| |
|[percentile_25_in_range](../../checks/column/numeric/percentile-25-in-range.md)|Reasonableness|Column level check that ensures that the percentile 25 of values in a monitored column is in a set range.| |
|[percentile_75_in_range](../../checks/column/numeric/percentile-75-in-range.md)|Reasonableness|Column level check that ensures that the percentile 75 of values in a monitored column is in a set range.| |
|[percentile_90_in_range](../../checks/column/numeric/percentile-90-in-range.md)|Reasonableness|Column-level check that ensures that the percentile 90 of values in a monitored column is in a set range.| |
|[sample_stddev_in_range](../../checks/column/numeric/sample-stddev-in-range.md)|Reasonableness|Column level check that ensures the sample standard deviation is in a set range in a monitored column.| |
|[population_stddev_in_range](../../checks/column/numeric/population-stddev-in-range.md)|Reasonableness|Column-level check that ensures that the population standard deviation is in a set range in a monitored column.| |
|[sample_variance_in_range](../../checks/column/numeric/sample-variance-in-range.md)|Reasonableness|Column level check that ensures the sample variance is in a set range in a monitored column.| |
|[population_variance_in_range](../../checks/column/numeric/population-variance-in-range.md)|Reasonableness|Column-level check that ensures that the population variance is in a set range in a monitored column.| |
|[invalid_latitude](../../checks/column/numeric/invalid-latitude.md)|Validity|Column level check that ensures that there are no more than a set number of invalid latitude values in a monitored column.| |
|[valid_latitude_percent](../../checks/column/numeric/valid-latitude-percent.md)|Validity|Column level check that ensures that there are no more than a set percentage of valid latitude values in a monitored column.| |
|[invalid_longitude](../../checks/column/numeric/invalid-longitude.md)|Validity|Column-level check that ensures that there are no more than a set number of invalid longitude values in a monitored column.| |
|[valid_longitude_percent](../../checks/column/numeric/valid-longitude-percent.md)|Validity|Column-level check that ensures that there are no more than a set percentage of valid longitude values in a monitored column.| |
|[non_negative_values](../../checks/column/numeric/non-negative-values.md)|Validity|Column-level check that ensures that there are no more than a maximum number of non-negative values in a monitored column.| |
|[non_negative_values_percent](../../checks/column/numeric/non-negative-values-percent.md)|Validity|Column level check that ensures that there are no more than a set percentage of negative values in a monitored column.| |


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/numeric](../../checks/column/numeric/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../data-quality-dimensions.md) used by DQOps
