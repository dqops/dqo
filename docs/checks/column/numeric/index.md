# Checks/column/numeric

This is a list of numeric column data quality checks supported by DQOps and a brief description of what they do.





## **numeric**
Validates that the data in a numeric column is in the expected format or within predefined ranges.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_number_below_min_value](./number-below-min-value.md#profile-number-below-min-value)|profiling|The check counts the number of values in the column that is below the value defined by the user as a parameter.|standard|
|[daily_number_below_min_value](./number-below-min-value.md#daily-number-below-min-value)|monitoring|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_number_below_min_value](./number-below-min-value.md#monthly-number-below-min-value)|monitoring|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|standard|
|[daily_partition_number_below_min_value](./number-below-min-value.md#daily-partition-number-below-min-value)|partitioned|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_number_below_min_value](./number-below-min-value.md#monthly-partition-number-below-min-value)|partitioned|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_number_above_max_value](./number-above-max-value.md#profile-number-above-max-value)|profiling|The check counts the number of values in the column that is above the value defined by the user as a parameter.|standard|
|[daily_number_above_max_value](./number-above-max-value.md#daily-number-above-max-value)|monitoring|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_number_above_max_value](./number-above-max-value.md#monthly-number-above-max-value)|monitoring|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|standard|
|[daily_partition_number_above_max_value](./number-above-max-value.md#daily-partition-number-above-max-value)|partitioned|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_number_above_max_value](./number-above-max-value.md#monthly-partition-number-above-max-value)|partitioned|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_negative_values](./negative-values.md#profile-negative-values)|profiling|Verifies that the number of negative values in a column does not exceed the maximum accepted count.|standard|
|[daily_negative_values](./negative-values.md#daily-negative-values)|monitoring|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_negative_values](./negative-values.md#monthly-negative-values)|monitoring|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.|standard|
|[daily_partition_negative_values](./negative-values.md#daily-partition-negative-values)|partitioned|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_negative_values](./negative-values.md#monthly-partition-negative-values)|partitioned|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_negative_values_percent](./negative-values-percent.md#profile-negative-values-percent)|profiling|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.|advanced|
|[daily_negative_values_percent](./negative-values-percent.md#daily-negative-values-percent)|monitoring|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_negative_values_percent](./negative-values-percent.md#monthly-negative-values-percent)|monitoring|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_negative_values_percent](./negative-values-percent.md#daily-partition-negative-values-percent)|partitioned|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_negative_values_percent](./negative-values-percent.md#monthly-partition-negative-values-percent)|partitioned|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_number_below_min_value_percent](./number-below-min-value-percent.md#profile-number-below-min-value-percent)|profiling|The check counts the percentage of values in the column that is below the value defined by the user as a parameter.|advanced|
|[daily_number_below_min_value_percent](./number-below-min-value-percent.md#daily-number-below-min-value-percent)|monitoring|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_number_below_min_value_percent](./number-below-min-value-percent.md#monthly-number-below-min-value-percent)|monitoring|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_number_below_min_value_percent](./number-below-min-value-percent.md#daily-partition-number-below-min-value-percent)|partitioned|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_number_below_min_value_percent](./number-below-min-value-percent.md#monthly-partition-number-below-min-value-percent)|partitioned|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_number_above_max_value_percent](./number-above-max-value-percent.md#profile-number-above-max-value-percent)|profiling|The check counts the percentage of values in the column that is above the value defined by the user as a parameter.|advanced|
|[daily_number_above_max_value_percent](./number-above-max-value-percent.md#daily-number-above-max-value-percent)|monitoring|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_number_above_max_value_percent](./number-above-max-value-percent.md#monthly-number-above-max-value-percent)|monitoring|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_number_above_max_value_percent](./number-above-max-value-percent.md#daily-partition-number-above-max-value-percent)|partitioned|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_number_above_max_value_percent](./number-above-max-value-percent.md#monthly-partition-number-above-max-value-percent)|partitioned|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_number_in_range_percent](./number-in-range-percent.md#profile-number-in-range-percent)|profiling|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|advanced|
|[daily_number_in_range_percent](./number-in-range-percent.md#daily-number-in-range-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_number_in_range_percent](./number-in-range-percent.md#monthly-number-in-range-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_number_in_range_percent](./number-in-range-percent.md#daily-partition-number-in-range-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_number_in_range_percent](./number-in-range-percent.md#monthly-partition-number-in-range-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_integer_in_range_percent](./integer-in-range-percent.md#profile-integer-in-range-percent)|profiling|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|advanced|
|[daily_integer_in_range_percent](./integer-in-range-percent.md#daily-integer-in-range-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_integer_in_range_percent](./integer-in-range-percent.md#monthly-integer-in-range-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_integer_in_range_percent](./integer-in-range-percent.md#daily-partition-integer-in-range-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_integer_in_range_percent](./integer-in-range-percent.md#monthly-partition-integer-in-range-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_min_in_range](./min-in-range.md#profile-min-in-range)|profiling|Verifies that the minimal value in a column is not outside the set range.|standard|
|[daily_min_in_range](./min-in-range.md#daily-min-in-range)|monitoring|Verifies that the minimal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_min_in_range](./min-in-range.md#monthly-min-in-range)|monitoring|Verifies that the minimal value in a column does not exceed the set range. Stores the most recent value for each month when the data quality check was evaluated.|standard|
|[daily_partition_min_in_range](./min-in-range.md#daily-partition-min-in-range)|partitioned|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_min_in_range](./min-in-range.md#monthly-partition-min-in-range)|partitioned|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_max_in_range](./max-in-range.md#profile-max-in-range)|profiling|Verifies that the maximal value in a column is not outside the set range.|standard|
|[daily_max_in_range](./max-in-range.md#daily-max-in-range)|monitoring|Verifies that the maximal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_max_in_range](./max-in-range.md#monthly-max-in-range)|monitoring|Verifies that the maximal value in a column does not exceed the set range. Stores the most recent value for each month when the data quality check was evaluated.|standard|
|[daily_partition_max_in_range](./max-in-range.md#daily-partition-max-in-range)|partitioned|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_max_in_range](./max-in-range.md#monthly-partition-max-in-range)|partitioned|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sum_in_range](./sum-in-range.md#profile-sum-in-range)|profiling|Verifies that the sum of all values in a column is not outside the set range.|standard|
|[daily_sum_in_range](./sum-in-range.md#daily-sum-in-range)|monitoring|Verifies that the sum of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_sum_in_range](./sum-in-range.md#monthly-sum-in-range)|monitoring|Verifies that the sum of all values in a column does not exceed the set range. Stores the most recent value for each month when the data quality check was evaluated.|standard|
|[daily_partition_sum_in_range](./sum-in-range.md#daily-partition-sum-in-range)|partitioned|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_sum_in_range](./sum-in-range.md#monthly-partition-sum-in-range)|partitioned|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_mean_in_range](./mean-in-range.md#profile-mean-in-range)|profiling|Verifies that the average (mean) of all values in a column is not outside the set range.|advanced|
|[daily_mean_in_range](./mean-in-range.md#daily-mean-in-range)|monitoring|Verifies that the average (mean) of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_mean_in_range](./mean-in-range.md#monthly-mean-in-range)|monitoring|Verifies that the average (mean) of all values in a column does not exceed the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_mean_in_range](./mean-in-range.md#daily-partition-mean-in-range)|partitioned|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_mean_in_range](./mean-in-range.md#monthly-partition-mean-in-range)|partitioned|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_median_in_range](./median-in-range.md#profile-median-in-range)|profiling|Verifies that the median of all values in a column is not outside the set range.|advanced|
|[daily_median_in_range](./median-in-range.md#daily-median-in-range)|monitoring|Verifies that the median of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[monthly_median_in_range](./median-in-range.md#monthly-median-in-range)|monitoring|Verifies that the median of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_median_in_range](./median-in-range.md#daily-partition-median-in-range)|partitioned|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_median_in_range](./median-in-range.md#monthly-partition-median-in-range)|partitioned|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_percentile_in_range](./percentile-in-range.md#profile-percentile-in-range)|profiling|Verifies that the percentile of all values in a column is not outside the set range.|advanced|
|[daily_percentile_in_range](./percentile-in-range.md#daily-percentile-in-range)|monitoring|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_percentile_in_range](./percentile-in-range.md#monthly-percentile-in-range)|monitoring|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_percentile_in_range](./percentile-in-range.md#daily-partition-percentile-in-range)|partitioned|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_percentile_in_range](./percentile-in-range.md#monthly-partition-percentile-in-range)|partitioned|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_percentile_10_in_range](./percentile-10-in-range.md#profile-percentile-10-in-range)|profiling|Verifies that the percentile 10 of all values in a column is not outside the set range.|advanced|
|[daily_percentile_10_in_range](./percentile-10-in-range.md#daily-percentile-10-in-range)|monitoring|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_percentile_10_in_range](./percentile-10-in-range.md#monthly-percentile-10-in-range)|monitoring|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_percentile_10_in_range](./percentile-10-in-range.md#daily-partition-percentile-10-in-range)|partitioned|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_percentile_10_in_range](./percentile-10-in-range.md#monthly-partition-percentile-10-in-range)|partitioned|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_percentile_25_in_range](./percentile-25-in-range.md#profile-percentile-25-in-range)|profiling|Verifies that the percentile 25 of all values in a column is not outside the set range.|advanced|
|[daily_percentile_25_in_range](./percentile-25-in-range.md#daily-percentile-25-in-range)|monitoring|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_percentile_25_in_range](./percentile-25-in-range.md#monthly-percentile-25-in-range)|monitoring|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_percentile_25_in_range](./percentile-25-in-range.md#daily-partition-percentile-25-in-range)|partitioned|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_percentile_25_in_range](./percentile-25-in-range.md#monthly-partition-percentile-25-in-range)|partitioned|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_percentile_75_in_range](./percentile-75-in-range.md#profile-percentile-75-in-range)|profiling|Verifies that the percentile 75 of all values in a column is not outside the set range.|advanced|
|[daily_percentile_75_in_range](./percentile-75-in-range.md#daily-percentile-75-in-range)|monitoring|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_percentile_75_in_range](./percentile-75-in-range.md#monthly-percentile-75-in-range)|monitoring|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_percentile_75_in_range](./percentile-75-in-range.md#daily-partition-percentile-75-in-range)|partitioned|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_percentile_75_in_range](./percentile-75-in-range.md#monthly-partition-percentile-75-in-range)|partitioned|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_percentile_90_in_range](./percentile-90-in-range.md#profile-percentile-90-in-range)|profiling|Verifies that the percentile 90 of all values in a column is not outside the set range.|advanced|
|[daily_percentile_90_in_range](./percentile-90-in-range.md#daily-percentile-90-in-range)|monitoring|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_percentile_90_in_range](./percentile-90-in-range.md#monthly-percentile-90-in-range)|monitoring|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_percentile_90_in_range](./percentile-90-in-range.md#daily-partition-percentile-90-in-range)|partitioned|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_percentile_90_in_range](./percentile-90-in-range.md#monthly-partition-percentile-90-in-range)|partitioned|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sample_stddev_in_range](./sample-stddev-in-range.md#profile-sample-stddev-in-range)|profiling|Verifies that the sample standard deviation of all values in a column is not outside the set range.|advanced|
|[daily_sample_stddev_in_range](./sample-stddev-in-range.md#daily-sample-stddev-in-range)|monitoring|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_sample_stddev_in_range](./sample-stddev-in-range.md#monthly-sample-stddev-in-range)|monitoring|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_sample_stddev_in_range](./sample-stddev-in-range.md#daily-partition-sample-stddev-in-range)|partitioned|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_sample_stddev_in_range](./sample-stddev-in-range.md#monthly-partition-sample-stddev-in-range)|partitioned|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_population_stddev_in_range](./population-stddev-in-range.md#profile-population-stddev-in-range)|profiling|Verifies that the population standard deviation of all values in a column is not outside the set range.|advanced|
|[daily_population_stddev_in_range](./population-stddev-in-range.md#daily-population-stddev-in-range)|monitoring|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_population_stddev_in_range](./population-stddev-in-range.md#monthly-population-stddev-in-range)|monitoring|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_population_stddev_in_range](./population-stddev-in-range.md#daily-partition-population-stddev-in-range)|partitioned|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_population_stddev_in_range](./population-stddev-in-range.md#monthly-partition-population-stddev-in-range)|partitioned|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_sample_variance_in_range](./sample-variance-in-range.md#profile-sample-variance-in-range)|profiling|Verifies that the sample variance of all values in a column is not outside the set range.|advanced|
|[daily_sample_variance_in_range](./sample-variance-in-range.md#daily-sample-variance-in-range)|monitoring|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_sample_variance_in_range](./sample-variance-in-range.md#monthly-sample-variance-in-range)|monitoring|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_sample_variance_in_range](./sample-variance-in-range.md#daily-partition-sample-variance-in-range)|partitioned|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_sample_variance_in_range](./sample-variance-in-range.md#monthly-partition-sample-variance-in-range)|partitioned|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_population_variance_in_range](./population-variance-in-range.md#profile-population-variance-in-range)|profiling|Verifies that the population variance of all values in a column is not outside the set range.|advanced|
|[daily_population_variance_in_range](./population-variance-in-range.md#daily-population-variance-in-range)|monitoring|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_population_variance_in_range](./population-variance-in-range.md#monthly-population-variance-in-range)|monitoring|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_population_variance_in_range](./population-variance-in-range.md#daily-partition-population-variance-in-range)|partitioned|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_population_variance_in_range](./population-variance-in-range.md#monthly-partition-population-variance-in-range)|partitioned|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_invalid_latitude](./invalid-latitude.md#profile-invalid-latitude)|profiling|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count.|advanced|
|[daily_invalid_latitude](./invalid-latitude.md#daily-invalid-latitude)|monitoring|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_invalid_latitude](./invalid-latitude.md#monthly-invalid-latitude)|monitoring|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_invalid_latitude](./invalid-latitude.md#daily-partition-invalid-latitude)|partitioned|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_invalid_latitude](./invalid-latitude.md#monthly-partition-invalid-latitude)|partitioned|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_valid_latitude_percent](./valid-latitude-percent.md#profile-valid-latitude-percent)|profiling|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage.|advanced|
|[daily_valid_latitude_percent](./valid-latitude-percent.md#daily-valid-latitude-percent)|monitoring|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_valid_latitude_percent](./valid-latitude-percent.md#monthly-valid-latitude-percent)|monitoring|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_valid_latitude_percent](./valid-latitude-percent.md#daily-partition-valid-latitude-percent)|partitioned|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_valid_latitude_percent](./valid-latitude-percent.md#monthly-partition-valid-latitude-percent)|partitioned|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_invalid_longitude](./invalid-longitude.md#profile-invalid-longitude)|profiling|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count.|advanced|
|[daily_invalid_longitude](./invalid-longitude.md#daily-invalid-longitude)|monitoring|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_invalid_longitude](./invalid-longitude.md#monthly-invalid-longitude)|monitoring|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_invalid_longitude](./invalid-longitude.md#daily-partition-invalid-longitude)|partitioned|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_invalid_longitude](./invalid-longitude.md#monthly-partition-invalid-longitude)|partitioned|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_valid_longitude_percent](./valid-longitude-percent.md#profile-valid-longitude-percent)|profiling|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage.|advanced|
|[daily_valid_longitude_percent](./valid-longitude-percent.md#daily-valid-longitude-percent)|monitoring|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_valid_longitude_percent](./valid-longitude-percent.md#monthly-valid-longitude-percent)|monitoring|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_valid_longitude_percent](./valid-longitude-percent.md#daily-partition-valid-longitude-percent)|partitioned|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_valid_longitude_percent](./valid-longitude-percent.md#monthly-partition-valid-longitude-percent)|partitioned|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_non_negative_values](./non-negative-values.md#profile-non-negative-values)|profiling|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count.|advanced|
|[daily_non_negative_values](./non-negative-values.md#daily-non-negative-values)|monitoring|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_non_negative_values](./non-negative-values.md#monthly-non-negative-values)|monitoring|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_non_negative_values](./non-negative-values.md#daily-partition-non-negative-values)|partitioned|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_non_negative_values](./non-negative-values.md#monthly-partition-non-negative-values)|partitioned|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_non_negative_values_percent](./non-negative-values-percent.md#profile-non-negative-values-percent)|profiling|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage.|advanced|
|[daily_non_negative_values_percent](./non-negative-values-percent.md#daily-non-negative-values-percent)|monitoring|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_non_negative_values_percent](./non-negative-values-percent.md#monthly-non-negative-values-percent)|monitoring|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_non_negative_values_percent](./non-negative-values-percent.md#daily-partition-non-negative-values-percent)|partitioned|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_non_negative_values_percent](./non-negative-values-percent.md#monthly-partition-non-negative-values-percent)|partitioned|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|







