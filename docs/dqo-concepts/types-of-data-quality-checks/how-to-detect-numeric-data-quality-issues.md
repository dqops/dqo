# Detecting data quality issues with numeric
The type of numeric is documented here

## Detecting numeric at a table level
How to detect numeric on tables

## List of numeric checks at a table level

## Detecting numeric at a column level
How to detect numeric on column

## List of numeric checks at a column level
| Data quality check name | Data quality dimension | Description | Class |
|-------------------------|------------------------|-------------|-------|
|[number_below_min_value](../../checks/column/numeric/number-below-min-value.md)|Validity|Column-level check that ensures that the number of values in the monitored column with a value below the value defined by the user as a parameter does not exceed set thresholds.|standard|
|[number_above_max_value](../../checks/column/numeric/number-above-max-value.md)|Validity|Column level check that ensures that the number of values in the monitored column with a value above the value defined by the user as a parameter does not exceed set thresholds.|standard|
|[negative_values](../../checks/column/numeric/negative-values.md)|Validity|Column-level check that ensures that there are no more than a set number of negative values in a monitored column.|standard|
|[negative_values_percent](../../checks/column/numeric/negative-values-percent.md)|Validity|Column level check that ensures that there are no more than a set percentage of negative values in a monitored column.|advanced|
|[number_below_min_value_percent](../../checks/column/numeric/number-below-min-value-percent.md)|Validity|Column level check that ensures that the percentage of values in the monitored column with a value below the value defined by the user as a parameter does not fall below set thresholds.|advanced|
|[number_above_max_value_percent](../../checks/column/numeric/number-above-max-value-percent.md)|Validity|Column-level check that ensures that the percentage of values in the monitored column with a value above the value defined by the user as a parameter does not fall below set thresholds.|advanced|
|[number_in_range_percent](../../checks/column/numeric/number-in-range-percent.md)|Reasonableness|Column level check that ensures that there are no more than a set percentage of values from range in a monitored column.|advanced|
|[integer_in_range_percent](../../checks/column/numeric/integer-in-range-percent.md)|Reasonableness|Column-level check that ensures that there are no more than a set number of values from range in a monitored column.|advanced|
|[min_in_range](../../checks/column/numeric/min-in-range.md)|Reasonableness|Column level check that ensures that the minimal values are in a set range in a monitored column.|standard|
|[max_in_range](../../checks/column/numeric/max-in-range.md)|Reasonableness|Column level check that ensures that the maximal values are in a set range in a monitored column.|standard|
|[sum_in_range](../../checks/column/numeric/sum-in-range.md)|Reasonableness|Column level check that ensures that the sum of values in a monitored column is in a set range.|standard|
|[mean_in_range](../../checks/column/numeric/mean-in-range.md)|Reasonableness|Column level check that ensures that the average (mean) of values in a monitored column is in a set range.|advanced|
|[median_in_range](../../checks/column/numeric/median-in-range.md)|Reasonableness|Column level check that ensures that the median of values in a monitored column is in a set range.|advanced|
|[percentile_in_range](../../checks/column/numeric/percentile-in-range.md)|Reasonableness|Column level check that ensures that the percentile of values in a monitored column is in a set range.|advanced|
|[percentile_10_in_range](../../checks/column/numeric/percentile-10-in-range.md)|Reasonableness|Column level check that ensures that the percentile 10 of values in a monitored column is in a set range.|advanced|
|[percentile_25_in_range](../../checks/column/numeric/percentile-25-in-range.md)|Reasonableness|Column level check that ensures that the percentile 25 of values in a monitored column is in a set range.|advanced|
|[percentile_75_in_range](../../checks/column/numeric/percentile-75-in-range.md)|Reasonableness|Column level check that ensures that the percentile 75 of values in a monitored column is in a set range.|advanced|
|[percentile_90_in_range](../../checks/column/numeric/percentile-90-in-range.md)|Reasonableness|Column-level check that ensures that the percentile 90 of values in a monitored column is in a set range.|advanced|
|[sample_stddev_in_range](../../checks/column/numeric/sample-stddev-in-range.md)|Reasonableness|Column level check that ensures the sample standard deviation is in a set range in a monitored column.|advanced|
|[population_stddev_in_range](../../checks/column/numeric/population-stddev-in-range.md)|Reasonableness|Column-level check that ensures that the population standard deviation is in a set range in a monitored column.|advanced|
|[sample_variance_in_range](../../checks/column/numeric/sample-variance-in-range.md)|Reasonableness|Column level check that ensures the sample variance is in a set range in a monitored column.|advanced|
|[population_variance_in_range](../../checks/column/numeric/population-variance-in-range.md)|Reasonableness|Column-level check that ensures that the population variance is in a set range in a monitored column.|advanced|
|[invalid_latitude](../../checks/column/numeric/invalid-latitude.md)|Validity|Column level check that ensures that there are no more than a set number of invalid latitude values in a monitored column.|advanced|
|[valid_latitude_percent](../../checks/column/numeric/valid-latitude-percent.md)|Validity|Column level check that ensures that there are no more than a set percentage of valid latitude values in a monitored column.|advanced|
|[invalid_longitude](../../checks/column/numeric/invalid-longitude.md)|Validity|Column-level check that ensures that there are no more than a set number of invalid longitude values in a monitored column.|advanced|
|[valid_longitude_percent](../../checks/column/numeric/valid-longitude-percent.md)|Validity|Column-level check that ensures that there are no more than a set percentage of valid longitude values in a monitored column.|advanced|
|[non_negative_values](../../checks/column/numeric/non-negative-values.md)|Validity|Column-level check that ensures that there are no more than a maximum number of non-negative values in a monitored column.|advanced|
|[non_negative_values_percent](../../checks/column/numeric/non-negative-values-percent.md)|Validity|Column level check that ensures that there are no more than a set percentage of negative values in a monitored column.|advanced|

## What's next
- Learn now to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
