# column level numeric data quality checks

This is a list of numeric column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level numeric checks
Validates that the data in a numeric column is in the expected format or within predefined ranges.

### [number below min value](./number-below-min-value.md)
This check finds numeric values smaller than the minimum accepted value. It counts the values that are too small.
 This check raises a data quality issue when the count of too small values exceeds the maximum accepted count.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_number_below_min_value`</span>](./number-below-min-value.md#profile-number-below-min-value)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|The check counts the number of values in the column that are below the value defined by the user as a parameter.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_number_below_min_value`</span>](./number-below-min-value.md#daily-number-below-min-value)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the number of values in the column that are below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_number_below_min_value`</span>](./number-below-min-value.md#monthly-number-below-min-value)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the number of values in the column that are below the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_number_below_min_value`</span>](./number-below-min-value.md#daily-partition-number-below-min-value)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the number of values in the column that are below the value defined by the user as a parameter. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_number_below_min_value`</span>](./number-below-min-value.md#monthly-partition-number-below-min-value)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the number of values in the column that are below the value defined by the user as a parameter. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [number above max value](./number-above-max-value.md)
This check finds numeric values bigger than the maximum accepted value. It counts the values that are too big.
 This check raises a data quality issue when the count of too big values exceeds the maximum accepted count.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_number_above_max_value`</span>](./number-above-max-value.md#profile-number-above-max-value)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|The check counts the number of values in the column that are above the value defined by the user as a parameter.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_number_above_max_value`</span>](./number-above-max-value.md#daily-number-above-max-value)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the number of values in the column that are above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_number_above_max_value`</span>](./number-above-max-value.md#monthly-number-above-max-value)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the number of values in the column that are above the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_number_above_max_value`</span>](./number-above-max-value.md#daily-partition-number-above-max-value)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the number of values in the column that are above the value defined by the user as a parameter. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_number_above_max_value`</span>](./number-above-max-value.md#monthly-partition-number-above-max-value)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the number of values in the column that are above the value defined by the user as a parameter. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [negative values](./negative-values.md)
This check finds and counts negative values in a numeric column. It raises a data quality issue when the count of negative values is above the maximum accepted count.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_negative_values`</span>](./negative-values.md#profile-negative-values)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the number of negative values in a column does not exceed the maximum accepted count.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_negative_values`</span>](./negative-values.md#daily-negative-values)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_negative_values`</span>](./negative-values.md#monthly-negative-values)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_negative_values`</span>](./negative-values.md#daily-partition-negative-values)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_negative_values`</span>](./negative-values.md#monthly-partition-negative-values)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [negative values percent](./negative-values-percent.md)
This check finds negative values in a numeric column. It measures the percentage of negative values and raises a data quality issue
 when the rate of negative values exceeds the maximum accepted percentage.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_negative_values_percent`</span>](./negative-values-percent.md#profile-negative-values-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.| |
|[<span class="no-wrap-code">`daily_negative_values_percent`</span>](./negative-values-percent.md#daily-negative-values-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_negative_values_percent`</span>](./negative-values-percent.md#monthly-negative-values-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_negative_values_percent`</span>](./negative-values-percent.md#daily-partition-negative-values-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_negative_values_percent`</span>](./negative-values-percent.md#monthly-partition-negative-values-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.| |



### [number below min value percent](./number-below-min-value-percent.md)
This check finds numeric values smaller than the minimum accepted value. It measures the percentage of values that are too small.
 This check raises a data quality issue when the percentage of values that are too small exceeds the maximum accepted percentage.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_number_below_min_value_percent`</span>](./number-below-min-value-percent.md#profile-number-below-min-value-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|The check counts the percentage of values in the column that are below the value defined by the user as a parameter.| |
|[<span class="no-wrap-code">`daily_number_below_min_value_percent`</span>](./number-below-min-value-percent.md#daily-number-below-min-value-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the percentage of values in the column that are below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_number_below_min_value_percent`</span>](./number-below-min-value-percent.md#monthly-number-below-min-value-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the percentage of values in the column that are below the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_number_below_min_value_percent`</span>](./number-below-min-value-percent.md#daily-partition-number-below-min-value-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the percentage of values in the column that are below the value defined by the user as a parameter. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_number_below_min_value_percent`</span>](./number-below-min-value-percent.md#monthly-partition-number-below-min-value-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the percentage of values in the column that are below the value defined by the user as a parameter. Stores a separate data quality check result for each monthly partition.| |



### [number above max value percent](./number-above-max-value-percent.md)
This check finds numeric values bigger than the maximum accepted value. It measures the percentage of values that are too big.
 This check raises a data quality issue when the percentage of values that are too big exceeds the maximum accepted percentage.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_number_above_max_value_percent`</span>](./number-above-max-value-percent.md#profile-number-above-max-value-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|The check counts the percentage of values in the column that are above the value defined by the user as a parameter.| |
|[<span class="no-wrap-code">`daily_number_above_max_value_percent`</span>](./number-above-max-value-percent.md#daily-number-above-max-value-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the percentage of values in the column that are above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_number_above_max_value_percent`</span>](./number-above-max-value-percent.md#monthly-number-above-max-value-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the percentage of values in the column that are above the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_number_above_max_value_percent`</span>](./number-above-max-value-percent.md#daily-partition-number-above-max-value-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the percentage of values in the column that are above the value defined by the user as a parameter. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_number_above_max_value_percent`</span>](./number-above-max-value-percent.md#monthly-partition-number-above-max-value-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the percentage of values in the column that are above the value defined by the user as a parameter. Stores a separate data quality check result for each monthly partition.| |



### [number in range percent](./number-in-range-percent.md)
This check verifies that values in a numeric column are within an accepted range.
 It measures the percentage of values within the valid range and raises a data quality issue when the rate of valid values is below a minimum accepted percentage.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_number_in_range_percent`</span>](./number-in-range-percent.md#profile-number-in-range-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.| |
|[<span class="no-wrap-code">`daily_number_in_range_percent`</span>](./number-in-range-percent.md#daily-number-in-range-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_number_in_range_percent`</span>](./number-in-range-percent.md#monthly-number-in-range-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_number_in_range_percent`</span>](./number-in-range-percent.md#daily-partition-number-in-range-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_number_in_range_percent`</span>](./number-in-range-percent.md#monthly-partition-number-in-range-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.| |



### [integer in range percent](./integer-in-range-percent.md)
This check verifies that numeric values are within a range of accepted values.
 It measures the percentage of values in the range and raises a data quality issue when the percentage of valid values is below an accepted rate.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_integer_in_range_percent`</span>](./integer-in-range-percent.md#profile-integer-in-range-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.| |
|[<span class="no-wrap-code">`daily_integer_in_range_percent`</span>](./integer-in-range-percent.md#daily-integer-in-range-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_integer_in_range_percent`</span>](./integer-in-range-percent.md#monthly-integer-in-range-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_integer_in_range_percent`</span>](./integer-in-range-percent.md#daily-partition-integer-in-range-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_integer_in_range_percent`</span>](./integer-in-range-percent.md#monthly-partition-integer-in-range-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.| |



### [min in range](./min-in-range.md)
This check finds a minimum value in a numeric column. It verifies that the minimum value is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_min_in_range`</span>](./min-in-range.md#profile-min-in-range)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the minimum value in a column is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_min_in_range`</span>](./min-in-range.md#daily-min-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the minimum value in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_min_in_range`</span>](./min-in-range.md#monthly-min-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the minimum value in a column does not exceed the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_min_in_range`</span>](./min-in-range.md#daily-partition-min-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the minimum value in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_min_in_range`</span>](./min-in-range.md#monthly-partition-min-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the minimum value in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |



### [max in range](./max-in-range.md)
This check finds a maximum value in a numeric column. It verifies that the maximum value is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_max_in_range`</span>](./max-in-range.md#profile-max-in-range)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the maximum value in a column is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_max_in_range`</span>](./max-in-range.md#daily-max-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the maximum value in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_max_in_range`</span>](./max-in-range.md#monthly-max-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the maximum value in a column does not exceed the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_max_in_range`</span>](./max-in-range.md#daily-partition-max-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the maximum value in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_max_in_range`</span>](./max-in-range.md#monthly-partition-max-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the maximum value in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |



### [sum in range](./sum-in-range.md)
This check calculates a sum of numeric values. It verifies that the sum is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sum_in_range`</span>](./sum-in-range.md#profile-sum-in-range)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the sum of all values in a column is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_sum_in_range`</span>](./sum-in-range.md#daily-sum-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the sum of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_sum_in_range`</span>](./sum-in-range.md#monthly-sum-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the sum of all values in a column does not exceed the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_sum_in_range`</span>](./sum-in-range.md#daily-partition-sum-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the sum of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_sum_in_range`</span>](./sum-in-range.md#monthly-partition-sum-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the sum of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |



### [mean in range](./mean-in-range.md)
This check calculates a mean (average) value in a numeric column. It verifies that the average value is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_mean_in_range`</span>](./mean-in-range.md#profile-mean-in-range)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the average (mean) of all values in a column is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_mean_in_range`</span>](./mean-in-range.md#daily-mean-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the average (mean) of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_mean_in_range`</span>](./mean-in-range.md#monthly-mean-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the average (mean) of all values in a column does not exceed the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_mean_in_range`</span>](./mean-in-range.md#daily-partition-mean-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the average (mean) of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_mean_in_range`</span>](./mean-in-range.md#monthly-partition-mean-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the average (mean) of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |



### [median in range](./median-in-range.md)
This check finds a median value in a numeric column. It verifies that the median value is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_median_in_range`</span>](./median-in-range.md#profile-median-in-range)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the median of all values in a column is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_median_in_range`</span>](./median-in-range.md#daily-median-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the median of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_median_in_range`</span>](./median-in-range.md#monthly-median-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the median of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_median_in_range`</span>](./median-in-range.md#daily-partition-median-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the median of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_median_in_range`</span>](./median-in-range.md#monthly-partition-median-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the median of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |



### [percentile in range](./percentile-in-range.md)
This check finds a requested percentile value of numeric values. The percentile is configured as a value in the range [0, 1]. This check verifies that the given percentile is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_percentile_in_range`</span>](./percentile-in-range.md#profile-percentile-in-range)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentile of all values in a column is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_percentile_in_range`</span>](./percentile-in-range.md#daily-percentile-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentile of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_percentile_in_range`</span>](./percentile-in-range.md#monthly-percentile-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentile of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_percentile_in_range`</span>](./percentile-in-range.md#daily-partition-percentile-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentile of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_percentile_in_range`</span>](./percentile-in-range.md#monthly-partition-percentile-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentile of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |



### [percentile 10 in range](./percentile-10-in-range.md)
This check finds the 10th percentile value in a numeric column. The 10th percentile is a value greater than 10% of the smallest values and smaller than the remaining 90% of other values.
 This check verifies that the 10th percentile is within the range of accepted values and raises a data quality issue when it is not within a valid range.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_percentile_10_in_range`</span>](./percentile-10-in-range.md#profile-percentile-10-in-range)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentile 10 of all values in a column is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_percentile_10_in_range`</span>](./percentile-10-in-range.md#daily-percentile-10-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_percentile_10_in_range`</span>](./percentile-10-in-range.md#monthly-percentile-10-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_percentile_10_in_range`</span>](./percentile-10-in-range.md#daily-partition-percentile-10-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_percentile_10_in_range`</span>](./percentile-10-in-range.md#monthly-partition-percentile-10-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |



### [percentile 25 in range](./percentile-25-in-range.md)
This check finds the 25th percentile value in a numeric column. The 10th percentile is a value greater than 25% of the smallest values and smaller than the remaining 75% of other values.
 This check verifies that the 25th percentile is within the range of accepted values and raises a data quality issue when it is not within a valid range.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_percentile_25_in_range`</span>](./percentile-25-in-range.md#profile-percentile-25-in-range)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentile 25 of all values in a column is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_percentile_25_in_range`</span>](./percentile-25-in-range.md#daily-percentile-25-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentile 25 of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_percentile_25_in_range`</span>](./percentile-25-in-range.md#monthly-percentile-25-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentile 25 of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_percentile_25_in_range`</span>](./percentile-25-in-range.md#daily-partition-percentile-25-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentile 25 of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_percentile_25_in_range`</span>](./percentile-25-in-range.md#monthly-partition-percentile-25-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentile 25 of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |



### [percentile 75 in range](./percentile-75-in-range.md)
This check finds the 75th percentile value in a numeric column. The 75th percentile is a value greater than 75% of the smallest values and smaller than the remaining 25% of other values.
 This check verifies that the 75th percentile is within the range of accepted values and raises a data quality issue when it is not within a valid range.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_percentile_75_in_range`</span>](./percentile-75-in-range.md#profile-percentile-75-in-range)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentile 75 of all values in a column is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_percentile_75_in_range`</span>](./percentile-75-in-range.md#daily-percentile-75-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentile 75 of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_percentile_75_in_range`</span>](./percentile-75-in-range.md#monthly-percentile-75-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentile 75 of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_percentile_75_in_range`</span>](./percentile-75-in-range.md#daily-partition-percentile-75-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentile 75 of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_percentile_75_in_range`</span>](./percentile-75-in-range.md#monthly-partition-percentile-75-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentile 75 of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |



### [percentile 90 in range](./percentile-90-in-range.md)
This check finds the 90th percentile value in a numeric column. The 90th percentile is a value greater than 90% of the smallest values and smaller than the remaining 10% of other values.
 This check verifies that the 90th percentile is within the range of accepted values and raises a data quality issue when it is not within a valid range.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_percentile_90_in_range`</span>](./percentile-90-in-range.md#profile-percentile-90-in-range)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentile 90 of all values in a column is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_percentile_90_in_range`</span>](./percentile-90-in-range.md#daily-percentile-90-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentile 90 of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_percentile_90_in_range`</span>](./percentile-90-in-range.md#monthly-percentile-90-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentile 90 of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_percentile_90_in_range`</span>](./percentile-90-in-range.md#daily-partition-percentile-90-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentile 90 of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_percentile_90_in_range`</span>](./percentile-90-in-range.md#monthly-partition-percentile-90-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentile 90 of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |



### [sample stddev in range](./sample-stddev-in-range.md)
This check calculates the standard deviation of numeric values. It verifies that the standard deviation is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sample_stddev_in_range`</span>](./sample-stddev-in-range.md#profile-sample-stddev-in-range)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the sample standard deviation of all values in a column is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_sample_stddev_in_range`</span>](./sample-stddev-in-range.md#daily-sample-stddev-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the sample standard deviation of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_sample_stddev_in_range`</span>](./sample-stddev-in-range.md#monthly-sample-stddev-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the sample standard deviation of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_sample_stddev_in_range`</span>](./sample-stddev-in-range.md#daily-partition-sample-stddev-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the sample standard deviation of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_sample_stddev_in_range`</span>](./sample-stddev-in-range.md#monthly-partition-sample-stddev-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the sample standard deviation of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |



### [population stddev in range](./population-stddev-in-range.md)
This check calculates the population standard deviation of numeric values. It verifies that the population standard deviation is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_population_stddev_in_range`</span>](./population-stddev-in-range.md#profile-population-stddev-in-range)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the population standard deviation of all values in a column is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_population_stddev_in_range`</span>](./population-stddev-in-range.md#daily-population-stddev-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the population standard deviation of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_population_stddev_in_range`</span>](./population-stddev-in-range.md#monthly-population-stddev-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the population standard deviation of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_population_stddev_in_range`</span>](./population-stddev-in-range.md#daily-partition-population-stddev-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the population standard deviation of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_population_stddev_in_range`</span>](./population-stddev-in-range.md#monthly-partition-population-stddev-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the population standard deviation of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |



### [sample variance in range](./sample-variance-in-range.md)
This check calculates a sample variance of numeric values. It verifies that the sample variance is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sample_variance_in_range`</span>](./sample-variance-in-range.md#profile-sample-variance-in-range)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the sample variance of all values in a column is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_sample_variance_in_range`</span>](./sample-variance-in-range.md#daily-sample-variance-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the sample variance of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_sample_variance_in_range`</span>](./sample-variance-in-range.md#monthly-sample-variance-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the sample variance of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_sample_variance_in_range`</span>](./sample-variance-in-range.md#daily-partition-sample-variance-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the sample variance of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_sample_variance_in_range`</span>](./sample-variance-in-range.md#monthly-partition-sample-variance-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the sample variance of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |



### [population variance in range](./population-variance-in-range.md)
This check calculates a population variance of numeric values. It verifies that the population variance is within the range of accepted values
 and raises a data quality issue when it is not within a valid range.o


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_population_variance_in_range`</span>](./population-variance-in-range.md#profile-population-variance-in-range)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the population variance of all values in a column is not outside the expected range.| |
|[<span class="no-wrap-code">`daily_population_variance_in_range`</span>](./population-variance-in-range.md#daily-population-variance-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the population variance of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_population_variance_in_range`</span>](./population-variance-in-range.md#monthly-population-variance-in-range)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the population variance of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_population_variance_in_range`</span>](./population-variance-in-range.md#daily-partition-population-variance-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the population variance of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_population_variance_in_range`</span>](./population-variance-in-range.md#monthly-partition-population-variance-in-range)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the population variance of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.| |



### [invalid latitude](./invalid-latitude.md)
This check finds numeric values that are not valid latitude coordinates. A valid latitude coordinate is in the range -90...90. It counts the values outside a valid range for a latitude.
 This check raises a data quality issue when the count of invalid values exceeds the maximum accepted count.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_invalid_latitude`</span>](./invalid-latitude.md#profile-invalid-latitude)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`daily_invalid_latitude`</span>](./invalid-latitude.md#daily-invalid-latitude)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_invalid_latitude`</span>](./invalid-latitude.md#monthly-invalid-latitude)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_invalid_latitude`</span>](./invalid-latitude.md#daily-partition-invalid-latitude)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_invalid_latitude`</span>](./invalid-latitude.md#monthly-partition-invalid-latitude)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.| |



### [valid latitude percent](./valid-latitude-percent.md)
This check verifies that numeric values are valid latitude coordinates.
 A valid latitude coordinate is in the range -90...90. It measures the percentage of values within a valid range for a latitude.
 This check raises a data quality issue when the rate of valid values is below the minimum accepted percentage.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_valid_latitude_percent`</span>](./valid-latitude-percent.md#profile-valid-latitude-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage.| |
|[<span class="no-wrap-code">`daily_valid_latitude_percent`</span>](./valid-latitude-percent.md#daily-valid-latitude-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_valid_latitude_percent`</span>](./valid-latitude-percent.md#monthly-valid-latitude-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_valid_latitude_percent`</span>](./valid-latitude-percent.md#daily-partition-valid-latitude-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_valid_latitude_percent`</span>](./valid-latitude-percent.md#monthly-partition-valid-latitude-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.| |



### [invalid longitude](./invalid-longitude.md)
This check finds numeric values that are not valid longitude coordinates. A valid longitude coordinate is in the range -180...180. It counts the values outside a valid range for a longitude.
 This check raises a data quality issue when the count of invalid values exceeds the maximum accepted count.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_invalid_longitude`</span>](./invalid-longitude.md#profile-invalid-longitude)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`daily_invalid_longitude`</span>](./invalid-longitude.md#daily-invalid-longitude)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_invalid_longitude`</span>](./invalid-longitude.md#monthly-invalid-longitude)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_invalid_longitude`</span>](./invalid-longitude.md#daily-partition-invalid-longitude)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_invalid_longitude`</span>](./invalid-longitude.md#monthly-partition-invalid-longitude)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.| |



### [valid longitude percent](./valid-longitude-percent.md)
This check verifies that numeric values are valid longitude coordinates. A valid longitude coordinate is in the range --180...180.
 It measures the percentage of values within a valid range for a longitude.
 This check raises a data quality issue when the rate of valid values is below the minimum accepted percentage.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_valid_longitude_percent`</span>](./valid-longitude-percent.md#profile-valid-longitude-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage.| |
|[<span class="no-wrap-code">`daily_valid_longitude_percent`</span>](./valid-longitude-percent.md#daily-valid-longitude-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_valid_longitude_percent`</span>](./valid-longitude-percent.md#monthly-valid-longitude-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_valid_longitude_percent`</span>](./valid-longitude-percent.md#daily-partition-valid-longitude-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_valid_longitude_percent`</span>](./valid-longitude-percent.md#monthly-partition-valid-longitude-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.| |



### [non negative values](./non-negative-values.md)
This check finds and counts non0negative values in a numeric column. It raises a data quality issue when the count of non-negative values is above the maximum accepted count.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_non_negative_values`</span>](./non-negative-values.md#profile-non-negative-values)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count.| |
|[<span class="no-wrap-code">`daily_non_negative_values`</span>](./non-negative-values.md#daily-non-negative-values)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_non_negative_values`</span>](./non-negative-values.md#monthly-non-negative-values)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_non_negative_values`</span>](./non-negative-values.md#daily-partition-non-negative-values)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_non_negative_values`</span>](./non-negative-values.md#monthly-partition-non-negative-values)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.| |



### [non negative values percent](./non-negative-values-percent.md)
This check finds non-negative values in a numeric column.
 It measures the percentage of non-negative values and raises a data quality issue when the rate of non-negative values exceeds the maximum accepted percentage.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_non_negative_values_percent`</span>](./non-negative-values-percent.md#profile-non-negative-values-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage.| |
|[<span class="no-wrap-code">`daily_non_negative_values_percent`</span>](./non-negative-values-percent.md#daily-non-negative-values-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_non_negative_values_percent`</span>](./non-negative-values-percent.md#monthly-non-negative-values-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_non_negative_values_percent`</span>](./non-negative-values-percent.md#daily-partition-non-negative-values-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_non_negative_values_percent`</span>](./non-negative-values-percent.md#monthly-partition-non-negative-values-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.| |







