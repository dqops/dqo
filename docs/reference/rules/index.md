# Rules

**This is a list of the rules in DQOps broken down by category and a brief description of what they do.**


### **averages**

| Rule name | Description |
|-----------|-------------|
|[between_percent_moving_average_30_days](../Averages.md#between-percent-moving-average-30-days)|Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.|
|[between_percent_moving_average_60_days](../Averages.md#between-percent-moving-average-60-days)|Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.|
|[between_percent_moving_average_7_days](../Averages.md#between-percent-moving-average-7-days)|Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.|
|[percent_moving_average](../Averages.md#percent-moving-average)|Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.|
|[within_percent_moving_average_30_days](../Averages.md#within-percent-moving-average-30-days)|Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.|
|[within_percent_moving_average_60_days](../Averages.md#within-percent-moving-average-60-days)|Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.|
|[within_percent_moving_average_7_days](../Averages.md#within-percent-moving-average-7-days)|Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average of a time window.|


### **change**

| Rule name | Description |
|-----------|-------------|
|[between_change](../Change.md#between-change)|Data quality rule that verifies if data quality sensor readout value changed by a value between the provided bounds.|
|[between_change_1_day](../Change.md#between-change-1-day)|Data quality rule that verifies if data quality sensor readout value changed by a value between the provided bounds compared to yesterday.|
|[between_change_30_days](../Change.md#between-change-30-days)|Data quality rule that verifies if data quality sensor readout value changed by a value between the provided bounds compared to last month.|
|[between_change_7_days](../Change.md#between-change-7-days)|Data quality rule that verifies if data quality sensor readout value changed by a value between the provided bounds compared to last week.|
|[between_percent_change](../Change.md#between-percent-change)|Data quality rule that verifies if data quality sensor readout value changed by a percent between the provided bounds.|
|[between_percent_change_1_day](../Change.md#between-percent-change-1-day)|Data quality rule that verifies if data quality sensor readout value changed by a percent between the provided bounds compared to yesterday.|
|[between_percent_change_30_days](../Change.md#between-percent-change-30-days)|Data quality rule that verifies if data quality sensor readout value changed by a percent between the provided bounds compared to last month.|
|[between_percent_change_7_days](../Change.md#between-percent-change-7-days)|Data quality rule that verifies if data quality sensor readout value changed by a percent between the provided bounds compared to last week.|
|[change_difference](../Change.md#change-difference)|Data quality rule that verifies if data quality sensor readout value changed by a value within the provided bound.|
|[change_difference_1_day](../Change.md#change-difference-1-day)|Data quality rule that verifies if data quality sensor readout value changed by a value within the provided bound compared to yesterday.|
|[change_difference_30_days](../Change.md#change-difference-30-days)|Data quality rule that verifies if data quality sensor readout value changed by a value within the provided bound compared to last month.|
|[change_difference_7_days](../Change.md#change-difference-7-days)|Data quality rule that verifies if data quality sensor readout value changed by a value within the provided bound compared to last week.|
|[change_percent](../Change.md#change-percent)|Data quality rule that verifies if data quality sensor readout value changed by a percent within the provided bound.|
|[change_percent_1_day](../Change.md#change-percent-1-day)|Data quality rule that verifies if data quality sensor readout value changed by a percent within the provided bound compared to yesterday.|
|[change_percent_30_days](../Change.md#change-percent-30-days)|Data quality rule that verifies if data quality sensor readout value changed by a percent within the provided bound compared to last month.|
|[change_percent_7_days](../Change.md#change-percent-7-days)|Data quality rule that verifies if data quality sensor readout value changed by a percent within the provided bound compared to last week.|


### **comparison**

| Rule name | Description |
|-----------|-------------|
|[between_floats](../Comparison.md#between-floats)|Data quality rule that verifies if a data quality check readout is between from and to values.|
|[between_ints](../Comparison.md#between-ints)|Data quality rule that verifies if a data quality check readout is between begin and end values.|
|[datatype_equals](../Comparison.md#datatype-equals)|Data quality rule that verifies that a data quality check readout of a string_datatype_detect (the data type detection) matches an expected data type. The supported values are in the range 1..7, which are: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|
|[diff_percent](../Comparison.md#diff-percent)|Data quality rule that verifies if a data quality check readout is less or equal a maximum value.|
|[equals](../Comparison.md#equals)|Data quality rule that verifies that a data quality check readout equals a given value. A margin of error may be configured.|
|[equals_0](../Comparison.md#equals-0)|Data quality rule that verifies that a data quality check readout equals 0. It is used in data quality checks that have an expected value &quot;0&quot;.|
|[equals_1](../Comparison.md#equals-1)|Data quality rule that verifies that a data quality check readout equals 1. It is used in data quality checks that have an expected value &quot;1&quot;.|
|[equals_integer](../Comparison.md#equals-integer)|Data quality rule that verifies that a data quality check readout equals a given integer value, with an expected value preconfigured as 1.|
|[max](../Comparison.md#max)|Data quality rule that verifies if a data quality check readsout is less or equal a maximum value.|
|[max_count](../Comparison.md#max-count)|Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.|
|[max_days](../Comparison.md#max-days)|Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.|
|[max_failures](../Comparison.md#max-failures)|Data quality rule that verifies if the number of executive failures (the sensor returned 0) is below the max_failures. The default maximum failures is 0 failures (the first failure is reported).|
|[max_missing](../Comparison.md#max-missing)|Data quality rule that verifies the results of the data quality checks that count the number of values present in a column, comparing it to a list of expected values. The rule compares the count of expected values (received as expected_value) to the count of values found in the column (as the actual_value). The rule fails when the difference is higher than the expected max_missing, which is the maximum difference between the expected_value (the count of values in the expected_values list) and the actual number of values found in the column that match the list.|
|[max_percent](../Comparison.md#max-percent)|Data quality rule that verifies if a data quality check readout is less or equal a maximum value.|
|[max_value](../Comparison.md#max-value)|Data quality rule that verifies if a data quality check readout is less or equal a maximum value.|
|[min](../Comparison.md#min)|Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.|
|[min_count](../Comparison.md#min-count)|Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.|
|[min_percent](../Comparison.md#min-percent)|Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.|
|[min_value](../Comparison.md#min-value)|Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.|
|[value_changed](../Comparison.md#value-changed)|Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.|


### **percentile**

| Rule name | Description |
|-----------|-------------|
|[anomaly_differencing_percentile_moving_average](../Percentile.md#anomaly-differencing-percentile-moving-average)|Data quality rule that verifies if a data quality sensor readout value is probable under the estimated normal distribution based on the increments of previous values gathered within a time window.|
|[anomaly_differencing_percentile_moving_average_30_days](../Percentile.md#anomaly-differencing-percentile-moving-average-30-days)|Data quality rule that verifies if a data quality sensor readout value is probable under the estimated normal distribution based on the increments of previous values gathered within a time window.|
|[anomaly_stationary_percentile_moving_average](../Percentile.md#anomaly-stationary-percentile-moving-average)|Data quality rule that verifies if a data quality sensor readout value is probable under the estimated normal distribution based on the previous values gathered within a time window.|
|[anomaly_stationary_percentile_moving_average_30_days](../Percentile.md#anomaly-stationary-percentile-moving-average-30-days)|Data quality rule that verifies if a data quality sensor readout value is probable under the estimated normal distribution based on the previous values gathered within a time window.|
|[change_percentile_moving_30_days](../Percentile.md#change-percentile-moving-30-days)|Data quality rule that verifies if a data quality sensor readout value is probable under the estimated normal distribution based on the increments of previous values gathered within a time window.|
|[change_percentile_moving_60_days](../Percentile.md#change-percentile-moving-60-days)|Data quality rule that verifies if a data quality sensor readout value is probable under the estimated normal distribution based on the increments of previous values gathered within a time window.|
|[change_percentile_moving_7_days](../Percentile.md#change-percentile-moving-7-days)|Data quality rule that verifies if a data quality sensor readout value is probable under the estimated normal distribution based on the increments of previous values gathered within a time window.|
|[percentile_moving_30_days](../Percentile.md#percentile-moving-30-days)|Data quality rule that verifies if a data quality sensor readout value is probable under the estimated normal distribution based on the previous values gathered within a time window.|
|[percentile_moving_60_days](../Percentile.md#percentile-moving-60-days)|Data quality rule that verifies if a data quality sensor readout value is probable under the estimated normal distribution based on the previous values gathered within a time window.|
|[percentile_moving_7_days](../Percentile.md#percentile-moving-7-days)|Data quality rule that verifies if a data quality sensor readout value is probable under the estimated normal distribution based on the previous values gathered within a time window.|


### **stdev**

| Rule name | Description |
|-----------|-------------|
|[change_multiply_moving_stdev_30_days](../Stdev.md#change-multiply-moving-stdev-30-days)|Data quality rule that verifies if a data quality sensor readout value doesn&#x27;t excessively deviate from the moving average of increments on a time window.|
|[change_multiply_moving_stdev_60_days](../Stdev.md#change-multiply-moving-stdev-60-days)|Data quality rule that verifies if a data quality sensor readout value doesn&#x27;t excessively deviate from the moving average of increments on a time window.|
|[change_multiply_moving_stdev_7_days](../Stdev.md#change-multiply-moving-stdev-7-days)|Data quality rule that verifies if a data quality sensor readout value doesn&#x27;t excessively deviate from the moving average of increments on a time window.|
|[change_multiply_moving_stdev_within_30_days](../Stdev.md#change-multiply-moving-stdev-within-30-days)|Data quality rule that verifies if a data quality sensor readout value doesn&#x27;t excessively deviate from the moving average of increments on a time window.|
|[change_multiply_moving_stdev_within_60_days](../Stdev.md#change-multiply-moving-stdev-within-60-days)|Data quality rule that verifies if a data quality sensor readout value doesn&#x27;t excessively deviate from the moving average of increments on a time window.|
|[change_multiply_moving_stdev_within_7_days](../Stdev.md#change-multiply-moving-stdev-within-7-days)|Data quality rule that verifies if a data quality sensor readout value doesn&#x27;t excessively deviate from the moving average of increments on a time window.|
|[multiply_moving_stdev_30_days](../Stdev.md#multiply-moving-stdev-30-days)|Data quality rule that verifies if a data quality sensor readout value doesn&#x27;t excessively deviate from the moving average of a time window.|
|[multiply_moving_stdev_60_days](../Stdev.md#multiply-moving-stdev-60-days)|Data quality rule that verifies if a data quality sensor readout value doesn&#x27;t excessively deviate from the moving average of a time window.|
|[multiply_moving_stdev_7_days](../Stdev.md#multiply-moving-stdev-7-days)|Data quality rule that verifies if a data quality sensor readout value doesn&#x27;t excessively deviate from the moving average of a time window.|
|[multiply_moving_stdev_within_30_days](../Stdev.md#multiply-moving-stdev-within-30-days)|Data quality rule that verifies if a data quality sensor readout value doesn&#x27;t excessively deviate from the moving average of a time window.|
|[multiply_moving_stdev_within_60_days](../Stdev.md#multiply-moving-stdev-within-60-days)|Data quality rule that verifies if a data quality sensor readout value doesn&#x27;t excessively deviate from the moving average of a time window.|
|[multiply_moving_stdev_within_7_days](../Stdev.md#multiply-moving-stdev-within-7-days)|Data quality rule that verifies if a data quality sensor readout value doesn&#x27;t excessively deviate from the moving average of a time window.|


