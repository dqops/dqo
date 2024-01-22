# Detecting data quality issues with numeric
Read this guide to learn what types of data quality checks are supported in DQOps to detect issues related to numeric.
The data quality checks are configured in the `numeric` category in DQOps.

## Numeric category
Data quality checks that are detecting issues related to numeric are listed below.

## Detecting numeric issues
How to detect numeric data quality issues.

## Use cases
| **Name of the example**                                                                                                  | **Description**                                                                                                                                                                                                                                                                                             |
|:-------------------------------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Percentage of negative values](../../examples/data-validity/percentage-of-negative-values.md)                           | This example shows how to detect that the percentage of negative values in a column does not exceed a set threshold using [negative_values_percent](../../checks/column/numeric/negative-values-percent.md) check.                                                                                          |
| [Percentage of values in range](../../examples/data-reasonability/percentage-of-integer-values-in-range.md)              | This example shows how to detect that the percentage of values within a set range in a column does not exceed a set threshold using [integer_in_range_percent](../../checks/column/numeric/integer-in-range-percent.md) check.                                                                              |
| [Percentage of valid latitude and longitude](../../examples/data-validity/percentage-of-valid-latitude-and-longitude.md) | This example shows how to detect that the percentage of valid latitude and longitude values remain above a set threshold using [valid_latitude_percent](../../checks/column/numeric/valid-latitude-percent.md) and [valid_longitude_percent](../../checks/column/numeric/valid-longitude-percent.md)checks. |

## List of numeric checks at a column level
| Data quality check name | Data quality dimension | Description | Standard check |
|-------------------------|------------------------|-------------|-------|
|[*number_below_min_value*](../../checks/column/numeric/number-below-min-value.md)|Validity|A column-level check that ensures that the number of values in the monitored column with a value below a user-defined value as a parameter does not exceed set thresholds.|:material-check-bold:|
|[*number_above_max_value*](../../checks/column/numeric/number-above-max-value.md)|Validity|A column-level check that ensures that the number of values in the monitored column with a value above a user-defined value as a parameter does not exceed set thresholds.|:material-check-bold:|
|[*negative_values*](../../checks/column/numeric/negative-values.md)|Validity|A column-level check that ensures that there are no more than a set number of negative values in a monitored column.|:material-check-bold:|
|[*negative_values_percent*](../../checks/column/numeric/negative-values-percent.md)|Validity|A column-level check that ensures that there are no more than a set percentage of negative values in a monitored column.| |
|[*number_below_min_value_percent*](../../checks/column/numeric/number-below-min-value-percent.md)|Validity|A column-level check that ensures that the percentage of values in the monitored column with a value below a user-defined value as a parameter does not fall below set thresholds.| |
|[*number_above_max_value_percent*](../../checks/column/numeric/number-above-max-value-percent.md)|Validity|A column-level check that ensures that the percentage of values in the monitored column with a value above a user-defined value as a parameter does not fall below set thresholds.| |
|[*number_in_range_percent*](../../checks/column/numeric/number-in-range-percent.md)|Reasonableness|A column-level check that ensures that there are no more than a set percentage of values from the range in a monitored column.| |
|[*integer_in_range_percent*](../../checks/column/numeric/integer-in-range-percent.md)|Reasonableness|A column-level check that ensures that there are no more than a set number of values from range in a monitored column.| |
|[*min_in_range*](../../checks/column/numeric/min-in-range.md)|Reasonableness|A column-level check that ensures that the minimum values are within the expected range in the monitored column.| |
|[*max_in_range*](../../checks/column/numeric/max-in-range.md)|Reasonableness|A column-level check that ensures that the maximum values are within the expected range in the monitored column.| |
|[*sum_in_range*](../../checks/column/numeric/sum-in-range.md)|Reasonableness|A column-level check that ensures that the sum value in the monitored column is within the expected range.| |
|[*mean_in_range*](../../checks/column/numeric/mean-in-range.md)|Reasonableness|A column-level check that ensures that the average (mean) value in the monitored column is within the expected range.| |
|[*median_in_range*](../../checks/column/numeric/median-in-range.md)|Reasonableness|A column-level check that ensures that the median value in the monitored column is within the expected range.| |
|[*percentile_in_range*](../../checks/column/numeric/percentile-in-range.md)|Reasonableness|A column-level check that ensures that the percentile of values in a monitored columnis within the expected range.| |
|[*percentile_10_in_range*](../../checks/column/numeric/percentile-10-in-range.md)|Reasonableness|A column-level check that ensures that the 10th percentile of values in the monitored column is within the expected range.| |
|[*percentile_25_in_range*](../../checks/column/numeric/percentile-25-in-range.md)|Reasonableness|A column-level check that ensures that the 25th percentile of values in the monitored column is within the expected range.| |
|[*percentile_75_in_range*](../../checks/column/numeric/percentile-75-in-range.md)|Reasonableness|A column-level check that ensures that the 75th percentile of values in the monitored column is within the expected range.| |
|[*percentile_90_in_range*](../../checks/column/numeric/percentile-90-in-range.md)|Reasonableness|A column-level check that ensures that the 90th percentile of values in the monitored column is within the expected range.| |
|[*sample_stddev_in_range*](../../checks/column/numeric/sample-stddev-in-range.md)|Reasonableness|A column-level check that ensures that the standard deviation of the sample is within the expected range in the monitored column.| |
|[*population_stddev_in_range*](../../checks/column/numeric/population-stddev-in-range.md)|Reasonableness|A column-level check that ensures that the population standard deviationis within the expected range in a monitored column.| |
|[*sample_variance_in_range*](../../checks/column/numeric/sample-variance-in-range.md)|Reasonableness|A column-level check that ensures the sample varianceis within the expected range in a monitored column.| |
|[*population_variance_in_range*](../../checks/column/numeric/population-variance-in-range.md)|Reasonableness|A column-level check that ensures that the population varianceis within the expected range in a monitored column.| |
|[*invalid_latitude*](../../checks/column/numeric/invalid-latitude.md)|Validity|A column-level check that ensures that there are no more than a set number of invalid latitude values in a monitored column.| |
|[*valid_latitude_percent*](../../checks/column/numeric/valid-latitude-percent.md)|Validity|A column-level check that ensures that there are no more than a set percentage of valid latitude values in a monitored column.| |
|[*invalid_longitude*](../../checks/column/numeric/invalid-longitude.md)|Validity|A column-level check that ensures that there are no more than a set number of invalid longitude values in a monitored column.| |
|[*valid_longitude_percent*](../../checks/column/numeric/valid-longitude-percent.md)|Validity|A column-level check that ensures that there are no more than a set percentage of valid longitude values in a monitored column.| |
|[*non_negative_values*](../../checks/column/numeric/non-negative-values.md)|Validity|A column-level check that ensures that there are no more than a maximum number of non-negative values in a monitored column.| |
|[*non_negative_values_percent*](../../checks/column/numeric/non-negative-values-percent.md)|Validity|A column-level check that ensures that there are no more than a set percentage of negative values in a monitored column.| |


**Reference and samples**

The full list of all data quality checks in this category is located in the [column/numeric](../../checks/column/numeric/index.md) reference.
The reference section provides YAML code samples that are ready to copy-paste to the [*.dqotable.yaml*](../../reference/yaml/TableYaml.md) files,
the parameters reference, and samples of data source specific SQL queries generated by [data quality sensors](../definition-of-data-quality-sensors.md)
that are used by those checks.

## What's next
- Learn how to [run data quality checks](../running-data-quality-checks.md#targeting-a-category-of-checks) filtering by a check category name
- Learn how to [configure data quality checks](../configuring-data-quality-checks-and-rules.md) and apply alerting rules
- Read the definition of [data quality dimensions](../data-quality-dimensions.md) used by DQOps
