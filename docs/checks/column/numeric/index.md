# Checks/column/numeric

**This is a list of numeric column data quality checks supported by DQOps and a brief description of what they do.**





## **numeric**  
Validates that the data in a numeric column is in the expected format or within predefined ranges.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_negative_count](negative-count/#profile-negative-count)|profiling|Verifies that the number of negative values in a column does not exceed the maximum accepted count.|
|[daily_negative_count](negative-count/#daily-negative-count)|monitoring|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_negative_count](negative-count/#monthly-negative-count)|monitoring|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_negative_count](negative-count/#daily-partition-negative-count)|partitioned|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_negative_count](negative-count/#monthly-partition-negative-count)|partitioned|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_negative_percent](negative-percent/#profile-negative-percent)|profiling|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.|
|[daily_negative_percent](negative-percent/#daily-negative-percent)|monitoring|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_negative_percent](negative-percent/#monthly-negative-percent)|monitoring|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_negative_percent](negative-percent/#daily-partition-negative-percent)|partitioned|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_negative_percent](negative-percent/#monthly-partition-negative-percent)|partitioned|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_non_negative_count](non-negative-count/#profile-non-negative-count)|profiling|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count.|
|[daily_non_negative_count](non-negative-count/#daily-non-negative-count)|monitoring|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_non_negative_count](non-negative-count/#monthly-non-negative-count)|monitoring|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_non_negative_count](non-negative-count/#daily-partition-non-negative-count)|partitioned|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_non_negative_count](non-negative-count/#monthly-partition-non-negative-count)|partitioned|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_non_negative_percent](non-negative-percent/#profile-non-negative-percent)|profiling|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage.|
|[daily_non_negative_percent](non-negative-percent/#daily-non-negative-percent)|monitoring|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_non_negative_percent](non-negative-percent/#monthly-non-negative-percent)|monitoring|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_non_negative_percent](non-negative-percent/#daily-partition-non-negative-percent)|partitioned|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_non_negative_percent](non-negative-percent/#monthly-partition-non-negative-percent)|partitioned|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_expected_numbers_in_use_count](expected-numbers-in-use-count/#profile-expected-numbers-in-use-count)|profiling|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|
|[daily_expected_numbers_in_use_count](expected-numbers-in-use-count/#daily-expected-numbers-in-use-count)|monitoring|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_expected_numbers_in_use_count](expected-numbers-in-use-count/#monthly-expected-numbers-in-use-count)|monitoring|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_expected_numbers_in_use_count](expected-numbers-in-use-count/#daily-partition-expected-numbers-in-use-count)|partitioned|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_expected_numbers_in_use_count](expected-numbers-in-use-count/#monthly-partition-expected-numbers-in-use-count)|partitioned|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_number_value_in_set_percent](number-value-in-set-percent/#profile-number-value-in-set-percent)|profiling|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|
|[daily_number_value_in_set_percent](number-value-in-set-percent/#daily-number-value-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_number_value_in_set_percent](number-value-in-set-percent/#monthly-number-value-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_number_value_in_set_percent](number-value-in-set-percent/#daily-partition-number-value-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_number_value_in_set_percent](number-value-in-set-percent/#monthly-partition-number-value-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_values_in_range_numeric_percent](values-in-range-numeric-percent/#profile-values-in-range-numeric-percent)|profiling|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|
|[daily_values_in_range_numeric_percent](values-in-range-numeric-percent/#daily-values-in-range-numeric-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_values_in_range_numeric_percent](values-in-range-numeric-percent/#monthly-values-in-range-numeric-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_values_in_range_numeric_percent](values-in-range-numeric-percent/#daily-partition-values-in-range-numeric-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_values_in_range_numeric_percent](values-in-range-numeric-percent/#monthly-partition-values-in-range-numeric-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_values_in_range_integers_percent](values-in-range-integers-percent/#profile-values-in-range-integers-percent)|profiling|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|
|[daily_values_in_range_integers_percent](values-in-range-integers-percent/#daily-values-in-range-integers-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_values_in_range_integers_percent](values-in-range-integers-percent/#monthly-values-in-range-integers-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_values_in_range_integers_percent](values-in-range-integers-percent/#daily-partition-values-in-range-integers-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_values_in_range_integers_percent](values-in-range-integers-percent/#monthly-partition-values-in-range-integers-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_value_below_min_value_count](value-below-min-value-count/#profile-value-below-min-value-count)|profiling|The check counts the number of values in the column that is below the value defined by the user as a parameter.|
|[daily_value_below_min_value_count](value-below-min-value-count/#daily-value-below-min-value-count)|monitoring|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_value_below_min_value_count](value-below-min-value-count/#monthly-value-below-min-value-count)|monitoring|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_below_min_value_count](value-below-min-value-count/#daily-partition-value-below-min-value-count)|partitioned|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_below_min_value_count](value-below-min-value-count/#monthly-partition-value-below-min-value-count)|partitioned|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_value_below_min_value_percent](value-below-min-value-percent/#profile-value-below-min-value-percent)|profiling|The check counts the percentage of values in the column that is below the value defined by the user as a parameter.|
|[daily_value_below_min_value_percent](value-below-min-value-percent/#daily-value-below-min-value-percent)|monitoring|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_value_below_min_value_percent](value-below-min-value-percent/#monthly-value-below-min-value-percent)|monitoring|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_below_min_value_percent](value-below-min-value-percent/#daily-partition-value-below-min-value-percent)|partitioned|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_below_min_value_percent](value-below-min-value-percent/#monthly-partition-value-below-min-value-percent)|partitioned|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_value_above_max_value_count](value-above-max-value-count/#profile-value-above-max-value-count)|profiling|The check counts the number of values in the column that is above the value defined by the user as a parameter.|
|[daily_value_above_max_value_count](value-above-max-value-count/#daily-value-above-max-value-count)|monitoring|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_value_above_max_value_count](value-above-max-value-count/#monthly-value-above-max-value-count)|monitoring|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_above_max_value_count](value-above-max-value-count/#daily-partition-value-above-max-value-count)|partitioned|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_above_max_value_count](value-above-max-value-count/#monthly-partition-value-above-max-value-count)|partitioned|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_value_above_max_value_percent](value-above-max-value-percent/#profile-value-above-max-value-percent)|profiling|The check counts the percentage of values in the column that is above the value defined by the user as a parameter.|
|[daily_value_above_max_value_percent](value-above-max-value-percent/#daily-value-above-max-value-percent)|monitoring|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_value_above_max_value_percent](value-above-max-value-percent/#monthly-value-above-max-value-percent)|monitoring|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_above_max_value_percent](value-above-max-value-percent/#daily-partition-value-above-max-value-percent)|partitioned|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_above_max_value_percent](value-above-max-value-percent/#monthly-partition-value-above-max-value-percent)|partitioned|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_max_in_range](max-in-range/#profile-max-in-range)|profiling|Verifies that the maximal value in a column is not outside the set range.|
|[daily_max_in_range](max-in-range/#daily-max-in-range)|monitoring|Verifies that the maximal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_max_in_range](max-in-range/#monthly-max-in-range)|monitoring|Verifies that the maximal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_max_in_range](max-in-range/#daily-partition-max-in-range)|partitioned|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_max_in_range](max-in-range/#monthly-partition-max-in-range)|partitioned|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_min_in_range](min-in-range/#profile-min-in-range)|profiling|Verifies that the minimal value in a column is not outside the set range.|
|[daily_min_in_range](min-in-range/#daily-min-in-range)|monitoring|Verifies that the minimal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_min_in_range](min-in-range/#monthly-min-in-range)|monitoring|Verifies that the minimal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_min_in_range](min-in-range/#daily-partition-min-in-range)|partitioned|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_min_in_range](min-in-range/#monthly-partition-min-in-range)|partitioned|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_in_range](mean-in-range/#profile-mean-in-range)|profiling|Verifies that the average (mean) of all values in a column is not outside the set range.|
|[daily_mean_in_range](mean-in-range/#daily-mean-in-range)|monitoring|Verifies that the average (mean) of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_mean_in_range](mean-in-range/#monthly-mean-in-range)|monitoring|Verifies that the average (mean) of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_mean_in_range](mean-in-range/#daily-partition-mean-in-range)|partitioned|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_mean_in_range](mean-in-range/#monthly-partition-mean-in-range)|partitioned|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_percentile_in_range](percentile-in-range/#profile-percentile-in-range)|profiling|Verifies that the percentile of all values in a column is not outside the set range.|
|[daily_percentile_in_range](percentile-in-range/#daily-percentile-in-range)|monitoring|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_percentile_in_range](percentile-in-range/#monthly-percentile-in-range)|monitoring|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_percentile_in_range](percentile-in-range/#daily-partition-percentile-in-range)|partitioned|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_percentile_in_range](percentile-in-range/#monthly-partition-percentile-in-range)|partitioned|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_in_range](median-in-range/#profile-median-in-range)|profiling|Verifies that the median of all values in a column is not outside the set range.|
|[daily_median_in_range](median-in-range/#daily-median-in-range)|monitoring|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[monthly_median_in_range](median-in-range/#monthly-median-in-range)|monitoring|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_median_in_range](median-in-range/#daily-partition-median-in-range)|partitioned|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_median_in_range](median-in-range/#monthly-partition-median-in-range)|partitioned|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_percentile_10_in_range](percentile-10-in-range/#profile-percentile-10-in-range)|profiling|Verifies that the percentile 10 of all values in a column is not outside the set range.|
|[daily_percentile_10_in_range](percentile-10-in-range/#daily-percentile-10-in-range)|monitoring|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_percentile_10_in_range](percentile-10-in-range/#monthly-percentile-10-in-range)|monitoring|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_percentile_10_in_range](percentile-10-in-range/#daily-partition-percentile-10-in-range)|partitioned|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_percentile_10_in_range](percentile-10-in-range/#monthly-partition-percentile-10-in-range)|partitioned|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_percentile_25_in_range](percentile-25-in-range/#profile-percentile-25-in-range)|profiling|Verifies that the percentile 25 of all values in a column is not outside the set range.|
|[daily_percentile_25_in_range](percentile-25-in-range/#daily-percentile-25-in-range)|monitoring|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_percentile_25_in_range](percentile-25-in-range/#monthly-percentile-25-in-range)|monitoring|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_percentile_25_in_range](percentile-25-in-range/#daily-partition-percentile-25-in-range)|partitioned|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_percentile_25_in_range](percentile-25-in-range/#monthly-partition-percentile-25-in-range)|partitioned|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_percentile_75_in_range](percentile-75-in-range/#profile-percentile-75-in-range)|profiling|Verifies that the percentile 75 of all values in a column is not outside the set range.|
|[daily_percentile_75_in_range](percentile-75-in-range/#daily-percentile-75-in-range)|monitoring|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_percentile_75_in_range](percentile-75-in-range/#monthly-percentile-75-in-range)|monitoring|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_percentile_75_in_range](percentile-75-in-range/#daily-partition-percentile-75-in-range)|partitioned|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_percentile_75_in_range](percentile-75-in-range/#monthly-partition-percentile-75-in-range)|partitioned|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_percentile_90_in_range](percentile-90-in-range/#profile-percentile-90-in-range)|profiling|Verifies that the percentile 90 of all values in a column is not outside the set range.|
|[daily_percentile_90_in_range](percentile-90-in-range/#daily-percentile-90-in-range)|monitoring|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_percentile_90_in_range](percentile-90-in-range/#monthly-percentile-90-in-range)|monitoring|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_percentile_90_in_range](percentile-90-in-range/#daily-partition-percentile-90-in-range)|partitioned|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_percentile_90_in_range](percentile-90-in-range/#monthly-partition-percentile-90-in-range)|partitioned|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sample_stddev_in_range](sample-stddev-in-range/#profile-sample-stddev-in-range)|profiling|Verifies that the sample standard deviation of all values in a column is not outside the set range.|
|[daily_sample_stddev_in_range](sample-stddev-in-range/#daily-sample-stddev-in-range)|monitoring|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sample_stddev_in_range](sample-stddev-in-range/#monthly-sample-stddev-in-range)|monitoring|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sample_stddev_in_range](sample-stddev-in-range/#daily-partition-sample-stddev-in-range)|partitioned|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sample_stddev_in_range](sample-stddev-in-range/#monthly-partition-sample-stddev-in-range)|partitioned|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_population_stddev_in_range](population-stddev-in-range/#profile-population-stddev-in-range)|profiling|Verifies that the population standard deviation of all values in a column is not outside the set range.|
|[daily_population_stddev_in_range](population-stddev-in-range/#daily-population-stddev-in-range)|monitoring|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_population_stddev_in_range](population-stddev-in-range/#monthly-population-stddev-in-range)|monitoring|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_population_stddev_in_range](population-stddev-in-range/#daily-partition-population-stddev-in-range)|partitioned|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_population_stddev_in_range](population-stddev-in-range/#monthly-partition-population-stddev-in-range)|partitioned|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sample_variance_in_range](sample-variance-in-range/#profile-sample-variance-in-range)|profiling|Verifies that the sample variance of all values in a column is not outside the set range.|
|[daily_sample_variance_in_range](sample-variance-in-range/#daily-sample-variance-in-range)|monitoring|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sample_variance_in_range](sample-variance-in-range/#monthly-sample-variance-in-range)|monitoring|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sample_variance_in_range](sample-variance-in-range/#daily-partition-sample-variance-in-range)|partitioned|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sample_variance_in_range](sample-variance-in-range/#monthly-partition-sample-variance-in-range)|partitioned|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_population_variance_in_range](population-variance-in-range/#profile-population-variance-in-range)|profiling|Verifies that the population variance of all values in a column is not outside the set range.|
|[daily_population_variance_in_range](population-variance-in-range/#daily-population-variance-in-range)|monitoring|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_population_variance_in_range](population-variance-in-range/#monthly-population-variance-in-range)|monitoring|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_population_variance_in_range](population-variance-in-range/#daily-partition-population-variance-in-range)|partitioned|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_population_variance_in_range](population-variance-in-range/#monthly-partition-population-variance-in-range)|partitioned|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_in_range](sum-in-range/#profile-sum-in-range)|profiling|Verifies that the sum of all values in a column is not outside the set range.|
|[daily_sum_in_range](sum-in-range/#daily-sum-in-range)|monitoring|Verifies that the sum of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sum_in_range](sum-in-range/#monthly-sum-in-range)|monitoring|Verifies that the sum of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sum_in_range](sum-in-range/#daily-partition-sum-in-range)|partitioned|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sum_in_range](sum-in-range/#monthly-partition-sum-in-range)|partitioned|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_invalid_latitude_count](invalid-latitude-count/#profile-invalid-latitude-count)|profiling|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count.|
|[daily_invalid_latitude_count](invalid-latitude-count/#daily-invalid-latitude-count)|monitoring|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_invalid_latitude_count](invalid-latitude-count/#monthly-invalid-latitude-count)|monitoring|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_invalid_latitude_count](invalid-latitude-count/#daily-partition-invalid-latitude-count)|partitioned|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_invalid_latitude_count](invalid-latitude-count/#monthly-partition-invalid-latitude-count)|partitioned|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_valid_latitude_percent](valid-latitude-percent/#profile-valid-latitude-percent)|profiling|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage.|
|[daily_valid_latitude_percent](valid-latitude-percent/#daily-valid-latitude-percent)|monitoring|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_valid_latitude_percent](valid-latitude-percent/#monthly-valid-latitude-percent)|monitoring|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_latitude_percent](valid-latitude-percent/#daily-partition-valid-latitude-percent)|partitioned|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_latitude_percent](valid-latitude-percent/#monthly-partition-valid-latitude-percent)|partitioned|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_invalid_longitude_count](invalid-longitude-count/#profile-invalid-longitude-count)|profiling|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count.|
|[daily_invalid_longitude_count](invalid-longitude-count/#daily-invalid-longitude-count)|monitoring|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_invalid_longitude_count](invalid-longitude-count/#monthly-invalid-longitude-count)|monitoring|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_invalid_longitude_count](invalid-longitude-count/#daily-partition-invalid-longitude-count)|partitioned|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_invalid_longitude_count](invalid-longitude-count/#monthly-partition-invalid-longitude-count)|partitioned|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_valid_longitude_percent](valid-longitude-percent/#profile-valid-longitude-percent)|profiling|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage.|
|[daily_valid_longitude_percent](valid-longitude-percent/#daily-valid-longitude-percent)|monitoring|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_valid_longitude_percent](valid-longitude-percent/#monthly-valid-longitude-percent)|monitoring|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_longitude_percent](valid-longitude-percent/#daily-partition-valid-longitude-percent)|partitioned|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_longitude_percent](valid-longitude-percent/#monthly-partition-valid-longitude-percent)|partitioned|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





