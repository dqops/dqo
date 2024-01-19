# column level

This is a list of column data quality checks supported by DQOps, broken down by a category and a brief description of what quality issued they detect.





## **accepted_values**
Verifies if all values in the column are from a set of known values, such as country codes.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_found_in_set_percent`</span>](./accepted_values/text-found-in-set-percent.md#profile-text-found-in-set-percent)|profiling|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_text_found_in_set_percent`</span>](./accepted_values/text-found-in-set-percent.md#daily-text-found-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_text_found_in_set_percent`</span>](./accepted_values/text-found-in-set-percent.md#monthly-text-found-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_text_found_in_set_percent`</span>](./accepted_values/text-found-in-set-percent.md#daily-partition-text-found-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_text_found_in_set_percent`</span>](./accepted_values/text-found-in-set-percent.md#monthly-partition-text-found-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_number_found_in_set_percent`</span>](./accepted_values/number-found-in-set-percent.md#profile-number-found-in-set-percent)|profiling|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_number_found_in_set_percent`</span>](./accepted_values/number-found-in-set-percent.md#daily-number-found-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_number_found_in_set_percent`</span>](./accepted_values/number-found-in-set-percent.md#monthly-number-found-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_number_found_in_set_percent`</span>](./accepted_values/number-found-in-set-percent.md#daily-partition-number-found-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_number_found_in_set_percent`</span>](./accepted_values/number-found-in-set-percent.md#monthly-partition-number-found-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_expected_text_values_in_use_count`</span>](./accepted_values/expected-text-values-in-use-count.md#profile-expected-text-values-in-use-count)|profiling|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|**|
|[<span class="no-wrap-code">`daily_expected_text_values_in_use_count`</span>](./accepted_values/expected-text-values-in-use-count.md#daily-expected-text-values-in-use-count)|monitoring|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_expected_text_values_in_use_count`</span>](./accepted_values/expected-text-values-in-use-count.md#monthly-expected-text-values-in-use-count)|monitoring|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_expected_text_values_in_use_count`</span>](./accepted_values/expected-text-values-in-use-count.md#daily-partition-expected-text-values-in-use-count)|partitioned|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_expected_text_values_in_use_count`</span>](./accepted_values/expected-text-values-in-use-count.md#monthly-partition-expected-text-values-in-use-count)|partitioned|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_expected_texts_in_top_values_count`</span>](./accepted_values/expected-texts-in-top-values-count.md#profile-expected-texts-in-top-values-count)|profiling|Verifies that the top X most popular column values contain all values from a list of expected values.|**|
|[<span class="no-wrap-code">`daily_expected_texts_in_top_values_count`</span>](./accepted_values/expected-texts-in-top-values-count.md#daily-expected-texts-in-top-values-count)|monitoring|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_expected_texts_in_top_values_count`</span>](./accepted_values/expected-texts-in-top-values-count.md#monthly-expected-texts-in-top-values-count)|monitoring|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_expected_texts_in_top_values_count`</span>](./accepted_values/expected-texts-in-top-values-count.md#daily-partition-expected-texts-in-top-values-count)|partitioned|Verifies that the top X most popular column values contain all values from a list of expected values. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_expected_texts_in_top_values_count`</span>](./accepted_values/expected-texts-in-top-values-count.md#monthly-partition-expected-texts-in-top-values-count)|partitioned|Verifies that the top X most popular column values contain all values from a list of expected values. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_expected_numbers_in_use_count`</span>](./accepted_values/expected-numbers-in-use-count.md#profile-expected-numbers-in-use-count)|profiling|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|**|
|[<span class="no-wrap-code">`daily_expected_numbers_in_use_count`</span>](./accepted_values/expected-numbers-in-use-count.md#daily-expected-numbers-in-use-count)|monitoring|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_expected_numbers_in_use_count`</span>](./accepted_values/expected-numbers-in-use-count.md#monthly-expected-numbers-in-use-count)|monitoring|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_expected_numbers_in_use_count`</span>](./accepted_values/expected-numbers-in-use-count.md#daily-partition-expected-numbers-in-use-count)|partitioned|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_expected_numbers_in_use_count`</span>](./accepted_values/expected-numbers-in-use-count.md#monthly-partition-expected-numbers-in-use-count)|partitioned|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each monthly partition.|**|






## **accuracy**


| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_total_sum_match_percent`</span>](./accuracy/total-sum-match-percent.md#profile-total-sum-match-percent)|profiling|Verifies that percentage of the difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number.|**|
|[<span class="no-wrap-code">`daily_total_sum_match_percent`</span>](./accuracy/total-sum-match-percent.md#daily-total-sum-match-percent)|monitoring|Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_total_sum_match_percent`</span>](./accuracy/total-sum-match-percent.md#monthly-total-sum-match-percent)|monitoring|Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_total_min_match_percent`</span>](./accuracy/total-min-match-percent.md#profile-total-min-match-percent)|profiling|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number.|**|
|[<span class="no-wrap-code">`daily_total_min_match_percent`</span>](./accuracy/total-min-match-percent.md#daily-total-min-match-percent)|monitoring|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_total_min_match_percent`</span>](./accuracy/total-min-match-percent.md#monthly-total-min-match-percent)|monitoring|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_total_max_match_percent`</span>](./accuracy/total-max-match-percent.md#profile-total-max-match-percent)|profiling|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number.|**|
|[<span class="no-wrap-code">`daily_total_max_match_percent`</span>](./accuracy/total-max-match-percent.md#daily-total-max-match-percent)|monitoring|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_total_max_match_percent`</span>](./accuracy/total-max-match-percent.md#monthly-total-max-match-percent)|monitoring|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_total_average_match_percent`</span>](./accuracy/total-average-match-percent.md#profile-total-average-match-percent)|profiling|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number.|**|
|[<span class="no-wrap-code">`daily_total_average_match_percent`</span>](./accuracy/total-average-match-percent.md#daily-total-average-match-percent)|monitoring|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_total_average_match_percent`</span>](./accuracy/total-average-match-percent.md#monthly-total-average-match-percent)|monitoring|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_total_not_null_count_match_percent`</span>](./accuracy/total-not-null-count-match-percent.md#profile-total-not-null-count-match-percent)|profiling|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_total_not_null_count_match_percent`</span>](./accuracy/total-not-null-count-match-percent.md#daily-total-not-null-count-match-percent)|monitoring|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_total_not_null_count_match_percent`</span>](./accuracy/total-not-null-count-match-percent.md#monthly-total-not-null-count-match-percent)|monitoring|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.|**|






## **anomaly**
Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sum_anomaly`</span>](./anomaly/sum-anomaly.md#profile-sum-anomaly)|profiling|Verifies that the sum in a column changes in a rate within a percentile boundary during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_sum_anomaly`</span>](./anomaly/sum-anomaly.md#daily-sum-anomaly)|monitoring|Verifies that the sum in a column changes in a rate within a percentile boundary during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_partition_sum_anomaly`</span>](./anomaly/sum-anomaly.md#daily-partition-sum-anomaly)|partitioned|Verifies that the sum in a column is within a percentile from measurements made during the last 90 days.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_mean_anomaly`</span>](./anomaly/mean-anomaly.md#profile-mean-anomaly)|profiling|Verifies that the mean value in a column changes in a rate within a percentile boundary during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_mean_anomaly`</span>](./anomaly/mean-anomaly.md#daily-mean-anomaly)|monitoring|Verifies that the mean value in a column changes in a rate within a percentile boundary during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_partition_mean_anomaly`</span>](./anomaly/mean-anomaly.md#daily-partition-mean-anomaly)|partitioned|Verifies that the mean value in a column is within a percentile from measurements made during the last 90 days.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_median_anomaly`</span>](./anomaly/median-anomaly.md#profile-median-anomaly)|profiling|Verifies that the median in a column changes in a rate within a percentile boundary during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_median_anomaly`</span>](./anomaly/median-anomaly.md#daily-median-anomaly)|monitoring|Verifies that the median in a column changes in a rate within a percentile boundary during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_partition_median_anomaly`</span>](./anomaly/median-anomaly.md#daily-partition-median-anomaly)|partitioned|Verifies that the median in a column is within a percentile from measurements made during the last 90 days.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_mean_change`</span>](./anomaly/mean-change.md#profile-mean-change)|profiling|Verifies that the mean value in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_mean_change`</span>](./anomaly/mean-change.md#daily-mean-change)|monitoring|Verifies that the mean value in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_mean_change`</span>](./anomaly/mean-change.md#monthly-mean-change)|monitoring|Verifies that the mean value in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_partition_mean_change`</span>](./anomaly/mean-change.md#daily-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout.|**|
|[<span class="no-wrap-code">`monthly_partition_mean_change`</span>](./anomaly/mean-change.md#monthly-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since the last readout.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_mean_change_1_day`</span>](./anomaly/mean-change-1-day.md#profile-mean-change-1-day)|profiling|Verifies that the mean value in a column changed in a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_mean_change_1_day`</span>](./anomaly/mean-change-1-day.md#daily-mean-change-1-day)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_partition_mean_change_1_day`</span>](./anomaly/mean-change-1-day.md#daily-partition-mean-change-1-day)|partitioned|Verifies that the mean value in a column changed in a fixed rate since the last readout from yesterday.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_mean_change_7_days`</span>](./anomaly/mean-change-7-days.md#profile-mean-change-7-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last week.|**|
|[<span class="no-wrap-code">`daily_mean_change_7_days`</span>](./anomaly/mean-change-7-days.md#daily-mean-change-7-days)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|**|
|[<span class="no-wrap-code">`daily_partition_mean_change_7_days`</span>](./anomaly/mean-change-7-days.md#daily-partition-mean-change-7-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last week.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_mean_change_30_days`</span>](./anomaly/mean-change-30-days.md#profile-mean-change-30-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last month.|**|
|[<span class="no-wrap-code">`daily_mean_change_30_days`</span>](./anomaly/mean-change-30-days.md#daily-mean-change-30-days)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|**|
|[<span class="no-wrap-code">`daily_partition_mean_change_30_days`</span>](./anomaly/mean-change-30-days.md#daily-partition-mean-change-30-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last month.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_median_change`</span>](./anomaly/median-change.md#profile-median-change)|profiling|Verifies that the median in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_median_change`</span>](./anomaly/median-change.md#daily-median-change)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_median_change`</span>](./anomaly/median-change.md#monthly-median-change)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_partition_median_change`</span>](./anomaly/median-change.md#daily-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_partition_median_change`</span>](./anomaly/median-change.md#monthly-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_median_change_1_day`</span>](./anomaly/median-change-1-day.md#profile-median-change-1-day)|profiling|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_median_change_1_day`</span>](./anomaly/median-change-1-day.md#daily-median-change-1-day)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_partition_median_change_1_day`</span>](./anomaly/median-change-1-day.md#daily-partition-median-change-1-day)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_median_change_7_days`</span>](./anomaly/median-change-7-days.md#profile-median-change-7-days)|profiling|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.|**|
|[<span class="no-wrap-code">`daily_median_change_7_days`</span>](./anomaly/median-change-7-days.md#daily-median-change-7-days)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.|**|
|[<span class="no-wrap-code">`daily_partition_median_change_7_days`</span>](./anomaly/median-change-7-days.md#daily-partition-median-change-7-days)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_median_change_30_days`</span>](./anomaly/median-change-30-days.md#profile-median-change-30-days)|profiling|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.|**|
|[<span class="no-wrap-code">`daily_median_change_30_days`</span>](./anomaly/median-change-30-days.md#daily-median-change-30-days)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.|**|
|[<span class="no-wrap-code">`daily_partition_median_change_30_days`</span>](./anomaly/median-change-30-days.md#daily-partition-median-change-30-days)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sum_change`</span>](./anomaly/sum-change.md#profile-sum-change)|profiling|Verifies that the sum in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_sum_change`</span>](./anomaly/sum-change.md#daily-sum-change)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_sum_change`</span>](./anomaly/sum-change.md#monthly-sum-change)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_partition_sum_change`</span>](./anomaly/sum-change.md#daily-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_partition_sum_change`</span>](./anomaly/sum-change.md#monthly-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sum_change_1_day`</span>](./anomaly/sum-change-1-day.md#profile-sum-change-1-day)|profiling|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_sum_change_1_day`</span>](./anomaly/sum-change-1-day.md#daily-sum-change-1-day)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_partition_sum_change_1_day`</span>](./anomaly/sum-change-1-day.md#daily-partition-sum-change-1-day)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sum_change_7_days`</span>](./anomaly/sum-change-7-days.md#profile-sum-change-7-days)|profiling|Verifies that the sum in a column changed in a fixed rate since the last readout from last week.|**|
|[<span class="no-wrap-code">`daily_sum_change_7_days`</span>](./anomaly/sum-change-7-days.md#daily-sum-change-7-days)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout from the last week.|**|
|[<span class="no-wrap-code">`daily_partition_sum_change_7_days`</span>](./anomaly/sum-change-7-days.md#daily-partition-sum-change-7-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout from the last week.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sum_change_30_days`</span>](./anomaly/sum-change-30-days.md#profile-sum-change-30-days)|profiling|Verifies that the sum in a column changed in a fixed rate since the last readout from last month.|**|
|[<span class="no-wrap-code">`daily_sum_change_30_days`</span>](./anomaly/sum-change-30-days.md#daily-sum-change-30-days)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout from the last month.|**|
|[<span class="no-wrap-code">`daily_partition_sum_change_30_days`</span>](./anomaly/sum-change-30-days.md#daily-partition-sum-change-30-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout from the last month.|**|






## **blanks**
Detects text columns that contain blank values, or values that are used as placeholders for missing values: &#x27;n/a&#x27;, &#x27;None&#x27;, etc.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_empty_text_found`</span>](./blanks/empty-text-found.md#profile-empty-text-found)|profiling|Verifies that empty strings in a column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_empty_text_found`</span>](./blanks/empty-text-found.md#daily-empty-text-found)|monitoring|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_empty_text_found`</span>](./blanks/empty-text-found.md#monthly-empty-text-found)|monitoring|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_empty_text_found`</span>](./blanks/empty-text-found.md#daily-partition-empty-text-found)|partitioned|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_empty_text_found`</span>](./blanks/empty-text-found.md#monthly-partition-empty-text-found)|partitioned|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_whitespace_text_found`</span>](./blanks/whitespace-text-found.md#profile-whitespace-text-found)|profiling|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_whitespace_text_found`</span>](./blanks/whitespace-text-found.md#daily-whitespace-text-found)|monitoring|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_whitespace_text_found`</span>](./blanks/whitespace-text-found.md#monthly-whitespace-text-found)|monitoring|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_whitespace_text_found`</span>](./blanks/whitespace-text-found.md#daily-partition-whitespace-text-found)|partitioned|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_whitespace_text_found`</span>](./blanks/whitespace-text-found.md#monthly-partition-whitespace-text-found)|partitioned|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_null_placeholder_text_found`</span>](./blanks/null-placeholder-text-found.md#profile-null-placeholder-text-found)|profiling|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_null_placeholder_text_found`</span>](./blanks/null-placeholder-text-found.md#daily-null-placeholder-text-found)|monitoring|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_null_placeholder_text_found`</span>](./blanks/null-placeholder-text-found.md#monthly-null-placeholder-text-found)|monitoring|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_null_placeholder_text_found`</span>](./blanks/null-placeholder-text-found.md#daily-partition-null-placeholder-text-found)|partitioned|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_null_placeholder_text_found`</span>](./blanks/null-placeholder-text-found.md#monthly-partition-null-placeholder-text-found)|partitioned|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_empty_text_percent`</span>](./blanks/empty-text-percent.md#profile-empty-text-percent)|profiling|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_empty_text_percent`</span>](./blanks/empty-text-percent.md#daily-empty-text-percent)|monitoring|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_empty_text_percent`</span>](./blanks/empty-text-percent.md#monthly-empty-text-percent)|monitoring|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_empty_text_percent`</span>](./blanks/empty-text-percent.md#daily-partition-empty-text-percent)|partitioned|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_empty_text_percent`</span>](./blanks/empty-text-percent.md#monthly-partition-empty-text-percent)|partitioned|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_whitespace_text_percent`</span>](./blanks/whitespace-text-percent.md#profile-whitespace-text-percent)|profiling|Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_whitespace_text_percent`</span>](./blanks/whitespace-text-percent.md#daily-whitespace-text-percent)|monitoring|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_whitespace_text_percent`</span>](./blanks/whitespace-text-percent.md#monthly-whitespace-text-percent)|monitoring|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_whitespace_text_percent`</span>](./blanks/whitespace-text-percent.md#daily-partition-whitespace-text-percent)|partitioned|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_whitespace_text_percent`</span>](./blanks/whitespace-text-percent.md#monthly-partition-whitespace-text-percent)|partitioned|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_null_placeholder_text_percent`</span>](./blanks/null-placeholder-text-percent.md#profile-null-placeholder-text-percent)|profiling|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_null_placeholder_text_percent`</span>](./blanks/null-placeholder-text-percent.md#daily-null-placeholder-text-percent)|monitoring|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_null_placeholder_text_percent`</span>](./blanks/null-placeholder-text-percent.md#monthly-null-placeholder-text-percent)|monitoring|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_null_placeholder_text_percent`</span>](./blanks/null-placeholder-text-percent.md#daily-partition-null-placeholder-text-percent)|partitioned|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_null_placeholder_text_percent`</span>](./blanks/null-placeholder-text-percent.md#monthly-partition-null-placeholder-text-percent)|partitioned|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|






## **bool**
Calculates the percentage of data in boolean columns.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_true_percent`</span>](./bool/true-percent.md#profile-true-percent)|profiling|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_true_percent`</span>](./bool/true-percent.md#daily-true-percent)|monitoring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_true_percent`</span>](./bool/true-percent.md#monthly-true-percent)|monitoring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_true_percent`</span>](./bool/true-percent.md#daily-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_true_percent`</span>](./bool/true-percent.md#monthly-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_false_percent`</span>](./bool/false-percent.md#profile-false-percent)|profiling|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_false_percent`</span>](./bool/false-percent.md#daily-false-percent)|monitoring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_false_percent`</span>](./bool/false-percent.md#monthly-false-percent)|monitoring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_false_percent`</span>](./bool/false-percent.md#daily-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_false_percent`</span>](./bool/false-percent.md#monthly-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|






## **comparisons**
Compares the columns in a table to another column in another table that is in a different data source.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sum_match`</span>](./comparisons/sum-match.md#profile-sum-match)|profiling|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds.|**|
|[<span class="no-wrap-code">`daily_sum_match`</span>](./comparisons/sum-match.md#daily-sum-match)|monitoring|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_sum_match`</span>](./comparisons/sum-match.md#monthly-sum-match)|monitoring|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_sum_match`</span>](./comparisons/sum-match.md#daily-partition-sum-match)|partitioned|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|**|
|[<span class="no-wrap-code">`monthly_partition_sum_match`</span>](./comparisons/sum-match.md#monthly-partition-sum-match)|partitioned|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_min_match`</span>](./comparisons/min-match.md#profile-min-match)|profiling|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds.|**|
|[<span class="no-wrap-code">`daily_min_match`</span>](./comparisons/min-match.md#daily-min-match)|monitoring|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_min_match`</span>](./comparisons/min-match.md#monthly-min-match)|monitoring|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_min_match`</span>](./comparisons/min-match.md#daily-partition-min-match)|partitioned|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|**|
|[<span class="no-wrap-code">`monthly_partition_min_match`</span>](./comparisons/min-match.md#monthly-partition-min-match)|partitioned|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_max_match`</span>](./comparisons/max-match.md#profile-max-match)|profiling|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds.|**|
|[<span class="no-wrap-code">`daily_max_match`</span>](./comparisons/max-match.md#daily-max-match)|monitoring|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_max_match`</span>](./comparisons/max-match.md#monthly-max-match)|monitoring|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_max_match`</span>](./comparisons/max-match.md#daily-partition-max-match)|partitioned|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|**|
|[<span class="no-wrap-code">`monthly_partition_max_match`</span>](./comparisons/max-match.md#monthly-partition-max-match)|partitioned|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_mean_match`</span>](./comparisons/mean-match.md#profile-mean-match)|profiling|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds.|**|
|[<span class="no-wrap-code">`daily_mean_match`</span>](./comparisons/mean-match.md#daily-mean-match)|monitoring|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_mean_match`</span>](./comparisons/mean-match.md#monthly-mean-match)|monitoring|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_mean_match`</span>](./comparisons/mean-match.md#daily-partition-mean-match)|partitioned|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|**|
|[<span class="no-wrap-code">`monthly_partition_mean_match`</span>](./comparisons/mean-match.md#monthly-partition-mean-match)|partitioned|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_not_null_count_match`</span>](./comparisons/not-null-count-match.md#profile-not-null-count-match)|profiling|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds.|**|
|[<span class="no-wrap-code">`daily_not_null_count_match`</span>](./comparisons/not-null-count-match.md#daily-not-null-count-match)|monitoring|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_not_null_count_match`</span>](./comparisons/not-null-count-match.md#monthly-not-null-count-match)|monitoring|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_not_null_count_match`</span>](./comparisons/not-null-count-match.md#daily-partition-not-null-count-match)|partitioned|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|**|
|[<span class="no-wrap-code">`monthly_partition_not_null_count_match`</span>](./comparisons/not-null-count-match.md#monthly-partition-not-null-count-match)|partitioned|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_null_count_match`</span>](./comparisons/null-count-match.md#profile-null-count-match)|profiling|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds.|**|
|[<span class="no-wrap-code">`daily_null_count_match`</span>](./comparisons/null-count-match.md#daily-null-count-match)|monitoring|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_null_count_match`</span>](./comparisons/null-count-match.md#monthly-null-count-match)|monitoring|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_null_count_match`</span>](./comparisons/null-count-match.md#daily-partition-null-count-match)|partitioned|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|**|
|[<span class="no-wrap-code">`monthly_partition_null_count_match`</span>](./comparisons/null-count-match.md#monthly-partition-null-count-match)|partitioned|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|**|






## **custom_sql**
Validate data against user-defined SQL queries at the column level. Checks in this group allows to validate that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sql_condition_failed_on_column`</span>](./custom_sql/sql-condition-failed-on-column.md#profile-sql-condition-failed-on-column)|profiling|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; col_tax&#x60;.|**|
|[<span class="no-wrap-code">`daily_sql_condition_failed_on_column`</span>](./custom_sql/sql-condition-failed-on-column.md#daily-sql-condition-failed-on-column)|monitoring|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; col_tax&#x60;. Stores the most recent captured count of failed rows for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_sql_condition_failed_on_column`</span>](./custom_sql/sql-condition-failed-on-column.md#monthly-sql-condition-failed-on-column)|monitoring|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores the most recent captured count of failed rows for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_sql_condition_failed_on_column`</span>](./custom_sql/sql-condition-failed-on-column.md#daily-partition-sql-condition-failed-on-column)|partitioned|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_sql_condition_failed_on_column`</span>](./custom_sql/sql-condition-failed-on-column.md#monthly-partition-sql-condition-failed-on-column)|partitioned|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sql_condition_passed_percent_on_column`</span>](./custom_sql/sql-condition-passed-percent-on-column.md#profile-sql-condition-passed-percent-on-column)|profiling|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;.|**|
|[<span class="no-wrap-code">`daily_sql_condition_passed_percent_on_column`</span>](./custom_sql/sql-condition-passed-percent-on-column.md#daily-sql-condition-passed-percent-on-column)|monitoring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_sql_condition_passed_percent_on_column`</span>](./custom_sql/sql-condition-passed-percent-on-column.md#monthly-sql-condition-passed-percent-on-column)|monitoring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;.  Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_sql_condition_passed_percent_on_column`</span>](./custom_sql/sql-condition-passed-percent-on-column.md#daily-partition-sql-condition-passed-percent-on-column)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_sql_condition_passed_percent_on_column`</span>](./custom_sql/sql-condition-passed-percent-on-column.md#monthly-partition-sql-condition-passed-percent-on-column)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sql_aggregate_expression_on_column`</span>](./custom_sql/sql-aggregate-expression-on-column.md#profile-sql-aggregate-expression-on-column)|profiling|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range.|**|
|[<span class="no-wrap-code">`daily_sql_aggregate_expression_on_column`</span>](./custom_sql/sql-aggregate-expression-on-column.md#daily-sql-aggregate-expression-on-column)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_sql_aggregate_expression_on_column`</span>](./custom_sql/sql-aggregate-expression-on-column.md#monthly-sql-aggregate-expression-on-column)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_sql_aggregate_expression_on_column`</span>](./custom_sql/sql-aggregate-expression-on-column.md#daily-partition-sql-aggregate-expression-on-column)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_sql_aggregate_expression_on_column`</span>](./custom_sql/sql-aggregate-expression-on-column.md#monthly-partition-sql-aggregate-expression-on-column)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each monthly partition.|**|






## **datatype**
Analyzes all values in a text column to detect if all values could be safely parsed to numeric, boolean, date or timestamp data types. Used to analyze tables in the landing zone.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_detected_datatype_in_text`</span>](./datatype/detected-datatype-in-text.md#profile-detected-datatype-in-text)|profiling|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type.|**|
|[<span class="no-wrap-code">`daily_detected_datatype_in_text`</span>](./datatype/detected-datatype-in-text.md#daily-detected-datatype-in-text)|monitoring|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_detected_datatype_in_text`</span>](./datatype/detected-datatype-in-text.md#monthly-detected-datatype-in-text)|monitoring|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_detected_datatype_in_text`</span>](./datatype/detected-datatype-in-text.md#daily-partition-detected-datatype-in-text)|partitioned|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_detected_datatype_in_text`</span>](./datatype/detected-datatype-in-text.md#monthly-partition-detected-datatype-in-text)|partitioned|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_detected_datatype_in_text_changed`</span>](./datatype/detected-datatype-in-text-changed.md#profile-detected-datatype-in-text-changed)|profiling|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|**|
|[<span class="no-wrap-code">`daily_detected_datatype_in_text_changed`</span>](./datatype/detected-datatype-in-text-changed.md#daily-detected-datatype-in-text-changed)|monitoring|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_detected_datatype_in_text_changed`</span>](./datatype/detected-datatype-in-text-changed.md#monthly-detected-datatype-in-text-changed)|monitoring|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_detected_datatype_in_text_changed`</span>](./datatype/detected-datatype-in-text-changed.md#daily-partition-detected-datatype-in-text-changed)|partitioned|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_detected_datatype_in_text_changed`</span>](./datatype/detected-datatype-in-text-changed.md#monthly-partition-detected-datatype-in-text-changed)|partitioned|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores a separate data quality check result for each monthly partition.|**|






## **datetime**
Validates that the data in a date or time column is in the expected format and within predefined ranges.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_date_values_in_future_percent`</span>](./datetime/date-values-in-future-percent.md#profile-date-values-in-future-percent)|profiling|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_date_values_in_future_percent`</span>](./datetime/date-values-in-future-percent.md#daily-date-values-in-future-percent)|monitoring|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_date_values_in_future_percent`</span>](./datetime/date-values-in-future-percent.md#monthly-date-values-in-future-percent)|monitoring|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_date_values_in_future_percent`</span>](./datetime/date-values-in-future-percent.md#daily-partition-date-values-in-future-percent)|partitioned|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_date_values_in_future_percent`</span>](./datetime/date-values-in-future-percent.md#monthly-partition-date-values-in-future-percent)|partitioned|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_datetime_value_in_range_date_percent`</span>](./datetime/datetime-value-in-range-date-percent.md#profile-datetime-value-in-range-date-percent)|profiling|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_datetime_value_in_range_date_percent`</span>](./datetime/datetime-value-in-range-date-percent.md#daily-datetime-value-in-range-date-percent)|monitoring|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_datetime_value_in_range_date_percent`</span>](./datetime/datetime-value-in-range-date-percent.md#monthly-datetime-value-in-range-date-percent)|monitoring|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_datetime_value_in_range_date_percent`</span>](./datetime/datetime-value-in-range-date-percent.md#daily-partition-datetime-value-in-range-date-percent)|partitioned|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_datetime_value_in_range_date_percent`</span>](./datetime/datetime-value-in-range-date-percent.md#monthly-partition-datetime-value-in-range-date-percent)|partitioned|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_date_match_format_percent`</span>](./datetime/date-match-format-percent.md#profile-date-match-format-percent)|profiling|Verifies that the percentage of date values matching the given format in a text column does not exceed the maximum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_date_match_format_percent`</span>](./datetime/date-match-format-percent.md#daily-date-match-format-percent)|monitoring|Verifies that the percentage of date values matching the given format in a text column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily monitoring.|**|
|[<span class="no-wrap-code">`monthly_date_match_format_percent`</span>](./datetime/date-match-format-percent.md#monthly-date-match-format-percent)|monitoring|Verifies that the percentage of date values matching the given format in a text column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly monitoring.|**|
|[<span class="no-wrap-code">`daily_partition_date_match_format_percent`</span>](./datetime/date-match-format-percent.md#daily-partition-date-match-format-percent)|partitioned|Verifies that the percentage of date values matching the given format in a text column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_date_match_format_percent`</span>](./datetime/date-match-format-percent.md#monthly-partition-date-match-format-percent)|partitioned|Verifies that the percentage of date values matching the given format in a text column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|






## **integrity**
Checks the referential integrity of a column against a column in another table.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_lookup_key_not_found`</span>](./integrity/lookup-key-not-found.md#profile-lookup-key-not-found)|profiling|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count.|**|
|[<span class="no-wrap-code">`daily_lookup_key_not_found`</span>](./integrity/lookup-key-not-found.md#daily-lookup-key-not-found)|monitoring|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_lookup_key_not_found`</span>](./integrity/lookup-key-not-found.md#monthly-lookup-key-not-found)|monitoring|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_lookup_key_not_found`</span>](./integrity/lookup-key-not-found.md#daily-partition-lookup-key-not-found)|partitioned|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_lookup_key_not_found`</span>](./integrity/lookup-key-not-found.md#monthly-partition-lookup-key-not-found)|partitioned|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_lookup_key_found_percent`</span>](./integrity/lookup-key-found-percent.md#profile-lookup-key-found-percent)|profiling|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count.|**|
|[<span class="no-wrap-code">`daily_lookup_key_found_percent`</span>](./integrity/lookup-key-found-percent.md#daily-lookup-key-found-percent)|monitoring|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_lookup_key_found_percent`</span>](./integrity/lookup-key-found-percent.md#monthly-lookup-key-found-percent)|monitoring|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_lookup_key_found_percent`</span>](./integrity/lookup-key-found-percent.md#daily-partition-lookup-key-found-percent)|partitioned|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_lookup_key_found_percent`</span>](./integrity/lookup-key-found-percent.md#monthly-partition-lookup-key-found-percent)|partitioned|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores a separate data quality check result for each monthly partition.|**|






## **nulls**
Checks for the presence of null or missing values in a column.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_nulls_count`</span>](./nulls/nulls-count.md#profile-nulls-count)|profiling|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_nulls_count`</span>](./nulls/nulls-count.md#daily-nulls-count)|monitoring|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_nulls_count`</span>](./nulls/nulls-count.md#monthly-nulls-count)|monitoring|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent count check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_nulls_count`</span>](./nulls/nulls-count.md#daily-partition-nulls-count)|partitioned|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_nulls_count`</span>](./nulls/nulls-count.md#monthly-partition-nulls-count)|partitioned|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_nulls_percent`</span>](./nulls/nulls-percent.md#profile-nulls-percent)|profiling|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_nulls_percent`</span>](./nulls/nulls-percent.md#daily-nulls-percent)|monitoring|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_nulls_percent`</span>](./nulls/nulls-percent.md#monthly-nulls-percent)|monitoring|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_nulls_percent`</span>](./nulls/nulls-percent.md#daily-partition-nulls-percent)|partitioned|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_nulls_percent`</span>](./nulls/nulls-percent.md#monthly-partition-nulls-percent)|partitioned|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_not_nulls_count`</span>](./nulls/not-nulls-count.md#profile-not-nulls-count)|profiling|Detects empty columns. The default rule min_count&#x3D;1 verifies that the column has any values. Verifies that the number of not null values in a column does not exceed the minimum accepted count.|**|
|[<span class="no-wrap-code">`daily_not_nulls_count`</span>](./nulls/not-nulls-count.md#daily-not-nulls-count)|monitoring|Detects empty columns. The default rule min_count&#x3D;1 verifies that the column has any values. Verifies that the number of not null values in a column does not exceed the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_not_nulls_count`</span>](./nulls/not-nulls-count.md#monthly-not-nulls-count)|monitoring|Detects columns that are empty and have no values (with the rule threshold min_count&#x3D;1). Verifies that the number of not null values in a column does not exceed the minimum accepted count. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_not_nulls_count`</span>](./nulls/not-nulls-count.md#daily-partition-not-nulls-count)|partitioned|Detects empty columns. The default rule min_count&#x3D;1 verifies that the column has any values. Verifies that the number of not null values in a column does not exceed the minimum accepted count. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_not_nulls_count`</span>](./nulls/not-nulls-count.md#monthly-partition-not-nulls-count)|partitioned|Detects empty columns. The default rule min_count&#x3D;1 verifies that the column has any values. Verifies that the number of not null values in a column does not exceed the minimum accepted count. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_not_nulls_percent`</span>](./nulls/not-nulls-percent.md#profile-not-nulls-percent)|profiling|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_not_nulls_percent`</span>](./nulls/not-nulls-percent.md#daily-not-nulls-percent)|monitoring|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_not_nulls_percent`</span>](./nulls/not-nulls-percent.md#monthly-not-nulls-percent)|monitoring|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_not_nulls_percent`</span>](./nulls/not-nulls-percent.md#daily-partition-not-nulls-percent)|partitioned|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_not_nulls_percent`</span>](./nulls/not-nulls-percent.md#monthly-partition-not-nulls-percent)|partitioned|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_nulls_percent_anomaly`</span>](./nulls/nulls-percent-anomaly.md#profile-nulls-percent-anomaly)|profiling|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|**|
|[<span class="no-wrap-code">`daily_nulls_percent_anomaly`</span>](./nulls/nulls-percent-anomaly.md#daily-nulls-percent-anomaly)|monitoring|Verifies that the null percent value in a column changes in a rate within a percentile boundary during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_partition_nulls_percent_anomaly`</span>](./nulls/nulls-percent-anomaly.md#daily-partition-nulls-percent-anomaly)|partitioned|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_nulls_percent_change`</span>](./nulls/nulls-percent-change.md#profile-nulls-percent-change)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout.|**|
|[<span class="no-wrap-code">`daily_nulls_percent_change`</span>](./nulls/nulls-percent-change.md#daily-nulls-percent-change)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_partition_nulls_percent_change`</span>](./nulls/nulls-percent-change.md#daily-partition-nulls-percent-change)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_nulls_percent_change_1_day`</span>](./nulls/nulls-percent-change-1-day.md#profile-nulls-percent-change-1-day)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_nulls_percent_change_1_day`</span>](./nulls/nulls-percent-change-1-day.md#daily-nulls-percent-change-1-day)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_partition_nulls_percent_change_1_day`</span>](./nulls/nulls-percent-change-1-day.md#daily-partition-nulls-percent-change-1-day)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since the last readout from yesterday.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_nulls_percent_change_7_days`</span>](./nulls/nulls-percent-change-7-days.md#profile-nulls-percent-change-7-days)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|**|
|[<span class="no-wrap-code">`daily_nulls_percent_change_7_days`</span>](./nulls/nulls-percent-change-7-days.md#daily-nulls-percent-change-7-days)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last week.|**|
|[<span class="no-wrap-code">`daily_partition_nulls_percent_change_7_days`</span>](./nulls/nulls-percent-change-7-days.md#daily-partition-nulls-percent-change-7-days)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last week.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_nulls_percent_change_30_days`</span>](./nulls/nulls-percent-change-30-days.md#profile-nulls-percent-change-30-days)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|**|
|[<span class="no-wrap-code">`daily_nulls_percent_change_30_days`</span>](./nulls/nulls-percent-change-30-days.md#daily-nulls-percent-change-30-days)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last month.|**|
|[<span class="no-wrap-code">`daily_partition_nulls_percent_change_30_days`</span>](./nulls/nulls-percent-change-30-days.md#daily-partition-nulls-percent-change-30-days)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last month.|**|






## **numeric**
Validates that the data in a numeric column is in the expected format or within predefined ranges.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_number_below_min_value`</span>](./numeric/number-below-min-value.md#profile-number-below-min-value)|profiling|The check counts the number of values in the column that is below the value defined by the user as a parameter.|**|
|[<span class="no-wrap-code">`daily_number_below_min_value`</span>](./numeric/number-below-min-value.md#daily-number-below-min-value)|monitoring|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_number_below_min_value`</span>](./numeric/number-below-min-value.md#monthly-number-below-min-value)|monitoring|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_number_below_min_value`</span>](./numeric/number-below-min-value.md#daily-partition-number-below-min-value)|partitioned|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_number_below_min_value`</span>](./numeric/number-below-min-value.md#monthly-partition-number-below-min-value)|partitioned|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_number_above_max_value`</span>](./numeric/number-above-max-value.md#profile-number-above-max-value)|profiling|The check counts the number of values in the column that is above the value defined by the user as a parameter.|**|
|[<span class="no-wrap-code">`daily_number_above_max_value`</span>](./numeric/number-above-max-value.md#daily-number-above-max-value)|monitoring|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_number_above_max_value`</span>](./numeric/number-above-max-value.md#monthly-number-above-max-value)|monitoring|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_number_above_max_value`</span>](./numeric/number-above-max-value.md#daily-partition-number-above-max-value)|partitioned|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_number_above_max_value`</span>](./numeric/number-above-max-value.md#monthly-partition-number-above-max-value)|partitioned|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_negative_values`</span>](./numeric/negative-values.md#profile-negative-values)|profiling|Verifies that the number of negative values in a column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_negative_values`</span>](./numeric/negative-values.md#daily-negative-values)|monitoring|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_negative_values`</span>](./numeric/negative-values.md#monthly-negative-values)|monitoring|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_negative_values`</span>](./numeric/negative-values.md#daily-partition-negative-values)|partitioned|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_negative_values`</span>](./numeric/negative-values.md#monthly-partition-negative-values)|partitioned|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_negative_values_percent`</span>](./numeric/negative-values-percent.md#profile-negative-values-percent)|profiling|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_negative_values_percent`</span>](./numeric/negative-values-percent.md#daily-negative-values-percent)|monitoring|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_negative_values_percent`</span>](./numeric/negative-values-percent.md#monthly-negative-values-percent)|monitoring|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_negative_values_percent`</span>](./numeric/negative-values-percent.md#daily-partition-negative-values-percent)|partitioned|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_negative_values_percent`</span>](./numeric/negative-values-percent.md#monthly-partition-negative-values-percent)|partitioned|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_number_below_min_value_percent`</span>](./numeric/number-below-min-value-percent.md#profile-number-below-min-value-percent)|profiling|The check counts the percentage of values in the column that is below the value defined by the user as a parameter.|**|
|[<span class="no-wrap-code">`daily_number_below_min_value_percent`</span>](./numeric/number-below-min-value-percent.md#daily-number-below-min-value-percent)|monitoring|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_number_below_min_value_percent`</span>](./numeric/number-below-min-value-percent.md#monthly-number-below-min-value-percent)|monitoring|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_number_below_min_value_percent`</span>](./numeric/number-below-min-value-percent.md#daily-partition-number-below-min-value-percent)|partitioned|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_number_below_min_value_percent`</span>](./numeric/number-below-min-value-percent.md#monthly-partition-number-below-min-value-percent)|partitioned|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_number_above_max_value_percent`</span>](./numeric/number-above-max-value-percent.md#profile-number-above-max-value-percent)|profiling|The check counts the percentage of values in the column that is above the value defined by the user as a parameter.|**|
|[<span class="no-wrap-code">`daily_number_above_max_value_percent`</span>](./numeric/number-above-max-value-percent.md#daily-number-above-max-value-percent)|monitoring|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_number_above_max_value_percent`</span>](./numeric/number-above-max-value-percent.md#monthly-number-above-max-value-percent)|monitoring|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_number_above_max_value_percent`</span>](./numeric/number-above-max-value-percent.md#daily-partition-number-above-max-value-percent)|partitioned|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_number_above_max_value_percent`</span>](./numeric/number-above-max-value-percent.md#monthly-partition-number-above-max-value-percent)|partitioned|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_number_in_range_percent`</span>](./numeric/number-in-range-percent.md#profile-number-in-range-percent)|profiling|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_number_in_range_percent`</span>](./numeric/number-in-range-percent.md#daily-number-in-range-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_number_in_range_percent`</span>](./numeric/number-in-range-percent.md#monthly-number-in-range-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_number_in_range_percent`</span>](./numeric/number-in-range-percent.md#daily-partition-number-in-range-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_number_in_range_percent`</span>](./numeric/number-in-range-percent.md#monthly-partition-number-in-range-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_integer_in_range_percent`</span>](./numeric/integer-in-range-percent.md#profile-integer-in-range-percent)|profiling|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_integer_in_range_percent`</span>](./numeric/integer-in-range-percent.md#daily-integer-in-range-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_integer_in_range_percent`</span>](./numeric/integer-in-range-percent.md#monthly-integer-in-range-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_integer_in_range_percent`</span>](./numeric/integer-in-range-percent.md#daily-partition-integer-in-range-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_integer_in_range_percent`</span>](./numeric/integer-in-range-percent.md#monthly-partition-integer-in-range-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_min_in_range`</span>](./numeric/min-in-range.md#profile-min-in-range)|profiling|Verifies that the minimum value in a column is not outside the expected range.|**|
|[<span class="no-wrap-code">`daily_min_in_range`</span>](./numeric/min-in-range.md#daily-min-in-range)|monitoring|Verifies that the minimum value in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_min_in_range`</span>](./numeric/min-in-range.md#monthly-min-in-range)|monitoring|Verifies that the minimum value in a column does not exceed the expected range. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_min_in_range`</span>](./numeric/min-in-range.md#daily-partition-min-in-range)|partitioned|Verifies that the minimum value in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_min_in_range`</span>](./numeric/min-in-range.md#monthly-partition-min-in-range)|partitioned|Verifies that the minimum value in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_max_in_range`</span>](./numeric/max-in-range.md#profile-max-in-range)|profiling|Verifies that the maximum value in a column is not outside the expected range.|**|
|[<span class="no-wrap-code">`daily_max_in_range`</span>](./numeric/max-in-range.md#daily-max-in-range)|monitoring|Verifies that the maximum value in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_max_in_range`</span>](./numeric/max-in-range.md#monthly-max-in-range)|monitoring|Verifies that the maximum value in a column does not exceed the expected range. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_max_in_range`</span>](./numeric/max-in-range.md#daily-partition-max-in-range)|partitioned|Verifies that the maximum value in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_max_in_range`</span>](./numeric/max-in-range.md#monthly-partition-max-in-range)|partitioned|Verifies that the maximum value in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sum_in_range`</span>](./numeric/sum-in-range.md#profile-sum-in-range)|profiling|Verifies that the sum of all values in a column is not outside the expected range.|**|
|[<span class="no-wrap-code">`daily_sum_in_range`</span>](./numeric/sum-in-range.md#daily-sum-in-range)|monitoring|Verifies that the sum of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_sum_in_range`</span>](./numeric/sum-in-range.md#monthly-sum-in-range)|monitoring|Verifies that the sum of all values in a column does not exceed the expected range. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_sum_in_range`</span>](./numeric/sum-in-range.md#daily-partition-sum-in-range)|partitioned|Verifies that the sum of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_sum_in_range`</span>](./numeric/sum-in-range.md#monthly-partition-sum-in-range)|partitioned|Verifies that the sum of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_mean_in_range`</span>](./numeric/mean-in-range.md#profile-mean-in-range)|profiling|Verifies that the average (mean) of all values in a column is not outside the expected range.|**|
|[<span class="no-wrap-code">`daily_mean_in_range`</span>](./numeric/mean-in-range.md#daily-mean-in-range)|monitoring|Verifies that the average (mean) of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_mean_in_range`</span>](./numeric/mean-in-range.md#monthly-mean-in-range)|monitoring|Verifies that the average (mean) of all values in a column does not exceed the expected range. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_mean_in_range`</span>](./numeric/mean-in-range.md#daily-partition-mean-in-range)|partitioned|Verifies that the average (mean) of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_mean_in_range`</span>](./numeric/mean-in-range.md#monthly-partition-mean-in-range)|partitioned|Verifies that the average (mean) of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_median_in_range`</span>](./numeric/median-in-range.md#profile-median-in-range)|profiling|Verifies that the median of all values in a column is not outside the expected range.|**|
|[<span class="no-wrap-code">`daily_median_in_range`</span>](./numeric/median-in-range.md#daily-median-in-range)|monitoring|Verifies that the median of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_median_in_range`</span>](./numeric/median-in-range.md#monthly-median-in-range)|monitoring|Verifies that the median of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_median_in_range`</span>](./numeric/median-in-range.md#daily-partition-median-in-range)|partitioned|Verifies that the median of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_median_in_range`</span>](./numeric/median-in-range.md#monthly-partition-median-in-range)|partitioned|Verifies that the median of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_percentile_in_range`</span>](./numeric/percentile-in-range.md#profile-percentile-in-range)|profiling|Verifies that the percentile of all values in a column is not outside the expected range.|**|
|[<span class="no-wrap-code">`daily_percentile_in_range`</span>](./numeric/percentile-in-range.md#daily-percentile-in-range)|monitoring|Verifies that the percentile of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_percentile_in_range`</span>](./numeric/percentile-in-range.md#monthly-percentile-in-range)|monitoring|Verifies that the percentile of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_percentile_in_range`</span>](./numeric/percentile-in-range.md#daily-partition-percentile-in-range)|partitioned|Verifies that the percentile of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_percentile_in_range`</span>](./numeric/percentile-in-range.md#monthly-partition-percentile-in-range)|partitioned|Verifies that the percentile of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_percentile_10_in_range`</span>](./numeric/percentile-10-in-range.md#profile-percentile-10-in-range)|profiling|Verifies that the percentile 10 of all values in a column is not outside the expected range.|**|
|[<span class="no-wrap-code">`daily_percentile_10_in_range`</span>](./numeric/percentile-10-in-range.md#daily-percentile-10-in-range)|monitoring|Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_percentile_10_in_range`</span>](./numeric/percentile-10-in-range.md#monthly-percentile-10-in-range)|monitoring|Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_percentile_10_in_range`</span>](./numeric/percentile-10-in-range.md#daily-partition-percentile-10-in-range)|partitioned|Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_percentile_10_in_range`</span>](./numeric/percentile-10-in-range.md#monthly-partition-percentile-10-in-range)|partitioned|Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_percentile_25_in_range`</span>](./numeric/percentile-25-in-range.md#profile-percentile-25-in-range)|profiling|Verifies that the percentile 25 of all values in a column is not outside the expected range.|**|
|[<span class="no-wrap-code">`daily_percentile_25_in_range`</span>](./numeric/percentile-25-in-range.md#daily-percentile-25-in-range)|monitoring|Verifies that the percentile 25 of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_percentile_25_in_range`</span>](./numeric/percentile-25-in-range.md#monthly-percentile-25-in-range)|monitoring|Verifies that the percentile 25 of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_percentile_25_in_range`</span>](./numeric/percentile-25-in-range.md#daily-partition-percentile-25-in-range)|partitioned|Verifies that the percentile 25 of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_percentile_25_in_range`</span>](./numeric/percentile-25-in-range.md#monthly-partition-percentile-25-in-range)|partitioned|Verifies that the percentile 25 of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_percentile_75_in_range`</span>](./numeric/percentile-75-in-range.md#profile-percentile-75-in-range)|profiling|Verifies that the percentile 75 of all values in a column is not outside the expected range.|**|
|[<span class="no-wrap-code">`daily_percentile_75_in_range`</span>](./numeric/percentile-75-in-range.md#daily-percentile-75-in-range)|monitoring|Verifies that the percentile 75 of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_percentile_75_in_range`</span>](./numeric/percentile-75-in-range.md#monthly-percentile-75-in-range)|monitoring|Verifies that the percentile 75 of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_percentile_75_in_range`</span>](./numeric/percentile-75-in-range.md#daily-partition-percentile-75-in-range)|partitioned|Verifies that the percentile 75 of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_percentile_75_in_range`</span>](./numeric/percentile-75-in-range.md#monthly-partition-percentile-75-in-range)|partitioned|Verifies that the percentile 75 of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_percentile_90_in_range`</span>](./numeric/percentile-90-in-range.md#profile-percentile-90-in-range)|profiling|Verifies that the percentile 90 of all values in a column is not outside the expected range.|**|
|[<span class="no-wrap-code">`daily_percentile_90_in_range`</span>](./numeric/percentile-90-in-range.md#daily-percentile-90-in-range)|monitoring|Verifies that the percentile 90 of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_percentile_90_in_range`</span>](./numeric/percentile-90-in-range.md#monthly-percentile-90-in-range)|monitoring|Verifies that the percentile 90 of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_percentile_90_in_range`</span>](./numeric/percentile-90-in-range.md#daily-partition-percentile-90-in-range)|partitioned|Verifies that the percentile 90 of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_percentile_90_in_range`</span>](./numeric/percentile-90-in-range.md#monthly-partition-percentile-90-in-range)|partitioned|Verifies that the percentile 90 of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sample_stddev_in_range`</span>](./numeric/sample-stddev-in-range.md#profile-sample-stddev-in-range)|profiling|Verifies that the sample standard deviation of all values in a column is not outside the expected range.|**|
|[<span class="no-wrap-code">`daily_sample_stddev_in_range`</span>](./numeric/sample-stddev-in-range.md#daily-sample-stddev-in-range)|monitoring|Verifies that the sample standard deviation of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_sample_stddev_in_range`</span>](./numeric/sample-stddev-in-range.md#monthly-sample-stddev-in-range)|monitoring|Verifies that the sample standard deviation of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_sample_stddev_in_range`</span>](./numeric/sample-stddev-in-range.md#daily-partition-sample-stddev-in-range)|partitioned|Verifies that the sample standard deviation of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_sample_stddev_in_range`</span>](./numeric/sample-stddev-in-range.md#monthly-partition-sample-stddev-in-range)|partitioned|Verifies that the sample standard deviation of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_population_stddev_in_range`</span>](./numeric/population-stddev-in-range.md#profile-population-stddev-in-range)|profiling|Verifies that the population standard deviation of all values in a column is not outside the expected range.|**|
|[<span class="no-wrap-code">`daily_population_stddev_in_range`</span>](./numeric/population-stddev-in-range.md#daily-population-stddev-in-range)|monitoring|Verifies that the population standard deviation of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_population_stddev_in_range`</span>](./numeric/population-stddev-in-range.md#monthly-population-stddev-in-range)|monitoring|Verifies that the population standard deviation of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_population_stddev_in_range`</span>](./numeric/population-stddev-in-range.md#daily-partition-population-stddev-in-range)|partitioned|Verifies that the population standard deviation of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_population_stddev_in_range`</span>](./numeric/population-stddev-in-range.md#monthly-partition-population-stddev-in-range)|partitioned|Verifies that the population standard deviation of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sample_variance_in_range`</span>](./numeric/sample-variance-in-range.md#profile-sample-variance-in-range)|profiling|Verifies that the sample variance of all values in a column is not outside the expected range.|**|
|[<span class="no-wrap-code">`daily_sample_variance_in_range`</span>](./numeric/sample-variance-in-range.md#daily-sample-variance-in-range)|monitoring|Verifies that the sample variance of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_sample_variance_in_range`</span>](./numeric/sample-variance-in-range.md#monthly-sample-variance-in-range)|monitoring|Verifies that the sample variance of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_sample_variance_in_range`</span>](./numeric/sample-variance-in-range.md#daily-partition-sample-variance-in-range)|partitioned|Verifies that the sample variance of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_sample_variance_in_range`</span>](./numeric/sample-variance-in-range.md#monthly-partition-sample-variance-in-range)|partitioned|Verifies that the sample variance of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_population_variance_in_range`</span>](./numeric/population-variance-in-range.md#profile-population-variance-in-range)|profiling|Verifies that the population variance of all values in a column is not outside the expected range.|**|
|[<span class="no-wrap-code">`daily_population_variance_in_range`</span>](./numeric/population-variance-in-range.md#daily-population-variance-in-range)|monitoring|Verifies that the population variance of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_population_variance_in_range`</span>](./numeric/population-variance-in-range.md#monthly-population-variance-in-range)|monitoring|Verifies that the population variance of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_population_variance_in_range`</span>](./numeric/population-variance-in-range.md#daily-partition-population-variance-in-range)|partitioned|Verifies that the population variance of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_population_variance_in_range`</span>](./numeric/population-variance-in-range.md#monthly-partition-population-variance-in-range)|partitioned|Verifies that the population variance of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_invalid_latitude`</span>](./numeric/invalid-latitude.md#profile-invalid-latitude)|profiling|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_invalid_latitude`</span>](./numeric/invalid-latitude.md#daily-invalid-latitude)|monitoring|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_invalid_latitude`</span>](./numeric/invalid-latitude.md#monthly-invalid-latitude)|monitoring|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_invalid_latitude`</span>](./numeric/invalid-latitude.md#daily-partition-invalid-latitude)|partitioned|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_invalid_latitude`</span>](./numeric/invalid-latitude.md#monthly-partition-invalid-latitude)|partitioned|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_valid_latitude_percent`</span>](./numeric/valid-latitude-percent.md#profile-valid-latitude-percent)|profiling|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_valid_latitude_percent`</span>](./numeric/valid-latitude-percent.md#daily-valid-latitude-percent)|monitoring|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_valid_latitude_percent`</span>](./numeric/valid-latitude-percent.md#monthly-valid-latitude-percent)|monitoring|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_valid_latitude_percent`</span>](./numeric/valid-latitude-percent.md#daily-partition-valid-latitude-percent)|partitioned|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_valid_latitude_percent`</span>](./numeric/valid-latitude-percent.md#monthly-partition-valid-latitude-percent)|partitioned|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_invalid_longitude`</span>](./numeric/invalid-longitude.md#profile-invalid-longitude)|profiling|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_invalid_longitude`</span>](./numeric/invalid-longitude.md#daily-invalid-longitude)|monitoring|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_invalid_longitude`</span>](./numeric/invalid-longitude.md#monthly-invalid-longitude)|monitoring|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_invalid_longitude`</span>](./numeric/invalid-longitude.md#daily-partition-invalid-longitude)|partitioned|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_invalid_longitude`</span>](./numeric/invalid-longitude.md#monthly-partition-invalid-longitude)|partitioned|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_valid_longitude_percent`</span>](./numeric/valid-longitude-percent.md#profile-valid-longitude-percent)|profiling|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_valid_longitude_percent`</span>](./numeric/valid-longitude-percent.md#daily-valid-longitude-percent)|monitoring|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_valid_longitude_percent`</span>](./numeric/valid-longitude-percent.md#monthly-valid-longitude-percent)|monitoring|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_valid_longitude_percent`</span>](./numeric/valid-longitude-percent.md#daily-partition-valid-longitude-percent)|partitioned|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_valid_longitude_percent`</span>](./numeric/valid-longitude-percent.md#monthly-partition-valid-longitude-percent)|partitioned|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_non_negative_values`</span>](./numeric/non-negative-values.md#profile-non-negative-values)|profiling|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_non_negative_values`</span>](./numeric/non-negative-values.md#daily-non-negative-values)|monitoring|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_non_negative_values`</span>](./numeric/non-negative-values.md#monthly-non-negative-values)|monitoring|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_non_negative_values`</span>](./numeric/non-negative-values.md#daily-partition-non-negative-values)|partitioned|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_non_negative_values`</span>](./numeric/non-negative-values.md#monthly-partition-non-negative-values)|partitioned|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_non_negative_values_percent`</span>](./numeric/non-negative-values-percent.md#profile-non-negative-values-percent)|profiling|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_non_negative_values_percent`</span>](./numeric/non-negative-values-percent.md#daily-non-negative-values-percent)|monitoring|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_non_negative_values_percent`</span>](./numeric/non-negative-values-percent.md#monthly-non-negative-values-percent)|monitoring|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_non_negative_values_percent`</span>](./numeric/non-negative-values-percent.md#daily-partition-non-negative-values-percent)|partitioned|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_non_negative_values_percent`</span>](./numeric/non-negative-values-percent.md#monthly-partition-non-negative-values-percent)|partitioned|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|






## **patterns**
Validates if a text column matches predefined patterns (such as an email address) or a custom regular expression.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_not_matching_regex_found`</span>](./patterns/text-not-matching-regex-found.md#profile-text-not-matching-regex-found)|profiling|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_text_not_matching_regex_found`</span>](./patterns/text-not-matching-regex-found.md#daily-text-not-matching-regex-found)|monitoring|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`monthly_text_not_matching_regex_found`</span>](./patterns/text-not-matching-regex-found.md#monthly-text-not-matching-regex-found)|monitoring|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_partition_text_not_matching_regex_found`</span>](./patterns/text-not-matching-regex-found.md#daily-partition-text-not-matching-regex-found)|partitioned|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`monthly_partition_text_not_matching_regex_found`</span>](./patterns/text-not-matching-regex-found.md#monthly-partition-text-not-matching-regex-found)|partitioned|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_texts_matching_regex_percent`</span>](./patterns/texts-matching-regex-percent.md#profile-texts-matching-regex-percent)|profiling|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_texts_matching_regex_percent`</span>](./patterns/texts-matching-regex-percent.md#daily-texts-matching-regex-percent)|monitoring|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`monthly_texts_matching_regex_percent`</span>](./patterns/texts-matching-regex-percent.md#monthly-texts-matching-regex-percent)|monitoring|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_partition_texts_matching_regex_percent`</span>](./patterns/texts-matching-regex-percent.md#daily-partition-texts-matching-regex-percent)|partitioned|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`monthly_partition_texts_matching_regex_percent`</span>](./patterns/texts-matching-regex-percent.md#monthly-partition-texts-matching-regex-percent)|partitioned|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_invalid_email_format_found`</span>](./patterns/invalid-email-format-found.md#profile-invalid-email-format-found)|profiling|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_invalid_email_format_found`</span>](./patterns/invalid-email-format-found.md#daily-invalid-email-format-found)|monitoring|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`monthly_invalid_email_format_found`</span>](./patterns/invalid-email-format-found.md#monthly-invalid-email-format-found)|monitoring|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_partition_invalid_email_format_found`</span>](./patterns/invalid-email-format-found.md#daily-partition-invalid-email-format-found)|partitioned|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`monthly_partition_invalid_email_format_found`</span>](./patterns/invalid-email-format-found.md#monthly-partition-invalid-email-format-found)|partitioned|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_not_matching_date_pattern_found`</span>](./patterns/text-not-matching-date-pattern-found.md#profile-text-not-matching-date-pattern-found)|profiling|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_text_not_matching_date_pattern_found`</span>](./patterns/text-not-matching-date-pattern-found.md#daily-text-not-matching-date-pattern-found)|monitoring|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`monthly_text_not_matching_date_pattern_found`</span>](./patterns/text-not-matching-date-pattern-found.md#monthly-text-not-matching-date-pattern-found)|monitoring|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_partition_text_not_matching_date_pattern_found`</span>](./patterns/text-not-matching-date-pattern-found.md#daily-partition-text-not-matching-date-pattern-found)|partitioned|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`monthly_partition_text_not_matching_date_pattern_found`</span>](./patterns/text-not-matching-date-pattern-found.md#monthly-partition-text-not-matching-date-pattern-found)|partitioned|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_matching_date_pattern_percent`</span>](./patterns/text-matching-date-pattern-percent.md#profile-text-matching-date-pattern-percent)|profiling|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_text_matching_date_pattern_percent`</span>](./patterns/text-matching-date-pattern-percent.md#daily-text-matching-date-pattern-percent)|monitoring|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`monthly_text_matching_date_pattern_percent`</span>](./patterns/text-matching-date-pattern-percent.md#monthly-text-matching-date-pattern-percent)|monitoring|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_partition_text_matching_date_pattern_percent`</span>](./patterns/text-matching-date-pattern-percent.md#daily-partition-text-matching-date-pattern-percent)|partitioned|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`monthly_partition_text_matching_date_pattern_percent`</span>](./patterns/text-matching-date-pattern-percent.md#monthly-partition-text-matching-date-pattern-percent)|partitioned|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_matching_name_pattern_percent`</span>](./patterns/text-matching-name-pattern-percent.md#profile-text-matching-name-pattern-percent)|profiling|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_text_matching_name_pattern_percent`</span>](./patterns/text-matching-name-pattern-percent.md#daily-text-matching-name-pattern-percent)|monitoring|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`monthly_text_matching_name_pattern_percent`</span>](./patterns/text-matching-name-pattern-percent.md#monthly-text-matching-name-pattern-percent)|monitoring|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_partition_text_matching_name_pattern_percent`</span>](./patterns/text-matching-name-pattern-percent.md#daily-partition-text-matching-name-pattern-percent)|partitioned|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`monthly_partition_text_matching_name_pattern_percent`</span>](./patterns/text-matching-name-pattern-percent.md#monthly-partition-text-matching-name-pattern-percent)|partitioned|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_invalid_uuid_format_found`</span>](./patterns/invalid-uuid-format-found.md#profile-invalid-uuid-format-found)|profiling|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_invalid_uuid_format_found`</span>](./patterns/invalid-uuid-format-found.md#daily-invalid-uuid-format-found)|monitoring|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`monthly_invalid_uuid_format_found`</span>](./patterns/invalid-uuid-format-found.md#monthly-invalid-uuid-format-found)|monitoring|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_partition_invalid_uuid_format_found`</span>](./patterns/invalid-uuid-format-found.md#daily-partition-invalid-uuid-format-found)|partitioned|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`monthly_partition_invalid_uuid_format_found`</span>](./patterns/invalid-uuid-format-found.md#monthly-partition-invalid-uuid-format-found)|partitioned|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_valid_uuid_format_percent`</span>](./patterns/valid-uuid-format-percent.md#profile-valid-uuid-format-percent)|profiling|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_valid_uuid_format_percent`</span>](./patterns/valid-uuid-format-percent.md#daily-valid-uuid-format-percent)|monitoring|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`monthly_valid_uuid_format_percent`</span>](./patterns/valid-uuid-format-percent.md#monthly-valid-uuid-format-percent)|monitoring|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_partition_valid_uuid_format_percent`</span>](./patterns/valid-uuid-format-percent.md#daily-partition-valid-uuid-format-percent)|partitioned|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`monthly_partition_valid_uuid_format_percent`</span>](./patterns/valid-uuid-format-percent.md#monthly-partition-valid-uuid-format-percent)|partitioned|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_invalid_ip4_address_format_found`</span>](./patterns/invalid-ip4-address-format-found.md#profile-invalid-ip4-address-format-found)|profiling|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_invalid_ip4_address_format_found`</span>](./patterns/invalid-ip4-address-format-found.md#daily-invalid-ip4-address-format-found)|monitoring|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`monthly_invalid_ip4_address_format_found`</span>](./patterns/invalid-ip4-address-format-found.md#monthly-invalid-ip4-address-format-found)|monitoring|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_partition_invalid_ip4_address_format_found`</span>](./patterns/invalid-ip4-address-format-found.md#daily-partition-invalid-ip4-address-format-found)|partitioned|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`monthly_partition_invalid_ip4_address_format_found`</span>](./patterns/invalid-ip4-address-format-found.md#monthly-partition-invalid-ip4-address-format-found)|partitioned|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_invalid_ip6_address_format_found`</span>](./patterns/invalid-ip6-address-format-found.md#profile-invalid-ip6-address-format-found)|profiling|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_invalid_ip6_address_format_found`</span>](./patterns/invalid-ip6-address-format-found.md#daily-invalid-ip6-address-format-found)|monitoring|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`monthly_invalid_ip6_address_format_found`</span>](./patterns/invalid-ip6-address-format-found.md#monthly-invalid-ip6-address-format-found)|monitoring|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_partition_invalid_ip6_address_format_found`</span>](./patterns/invalid-ip6-address-format-found.md#daily-partition-invalid-ip6-address-format-found)|partitioned|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`monthly_partition_invalid_ip6_address_format_found`</span>](./patterns/invalid-ip6-address-format-found.md#monthly-partition-invalid-ip6-address-format-found)|partitioned|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|**|






## **pii**
Checks for the presence of sensitive or personally identifiable information (PII) in a column such as email, phone, zip code, IP4 and IP6 addresses.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_contains_usa_phone_percent`</span>](./pii/contains-usa-phone-percent.md#profile-contains-usa-phone-percent)|profiling|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_contains_usa_phone_percent`</span>](./pii/contains-usa-phone-percent.md#daily-contains-usa-phone-percent)|monitoring|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_contains_usa_phone_percent`</span>](./pii/contains-usa-phone-percent.md#monthly-contains-usa-phone-percent)|monitoring|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_contains_usa_phone_percent`</span>](./pii/contains-usa-phone-percent.md#daily-partition-contains-usa-phone-percent)|partitioned|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_contains_usa_phone_percent`</span>](./pii/contains-usa-phone-percent.md#monthly-partition-contains-usa-phone-percent)|partitioned|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_contains_email_percent`</span>](./pii/contains-email-percent.md#profile-contains-email-percent)|profiling|Verifies that the percentage of rows that contains valid emails in a column does not exceed the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_contains_email_percent`</span>](./pii/contains-email-percent.md#daily-contains-email-percent)|monitoring|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_contains_email_percent`</span>](./pii/contains-email-percent.md#monthly-contains-email-percent)|monitoring|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_contains_email_percent`</span>](./pii/contains-email-percent.md#daily-partition-contains-email-percent)|partitioned|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_contains_email_percent`</span>](./pii/contains-email-percent.md#monthly-partition-contains-email-percent)|partitioned|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_contains_usa_zipcode_percent`</span>](./pii/contains-usa-zipcode-percent.md#profile-contains-usa-zipcode-percent)|profiling|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_contains_usa_zipcode_percent`</span>](./pii/contains-usa-zipcode-percent.md#daily-contains-usa-zipcode-percent)|monitoring|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_contains_usa_zipcode_percent`</span>](./pii/contains-usa-zipcode-percent.md#monthly-contains-usa-zipcode-percent)|monitoring|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_contains_usa_zipcode_percent`</span>](./pii/contains-usa-zipcode-percent.md#daily-partition-contains-usa-zipcode-percent)|partitioned|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_contains_usa_zipcode_percent`</span>](./pii/contains-usa-zipcode-percent.md#monthly-partition-contains-usa-zipcode-percent)|partitioned|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_contains_ip4_percent`</span>](./pii/contains-ip4-percent.md#profile-contains-ip4-percent)|profiling|Verifies that the percentage of rows that contains valid IP4 address values in a column does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_contains_ip4_percent`</span>](./pii/contains-ip4-percent.md#daily-contains-ip4-percent)|monitoring|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_contains_ip4_percent`</span>](./pii/contains-ip4-percent.md#monthly-contains-ip4-percent)|monitoring|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_contains_ip4_percent`</span>](./pii/contains-ip4-percent.md#daily-partition-contains-ip4-percent)|partitioned|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_contains_ip4_percent`</span>](./pii/contains-ip4-percent.md#monthly-partition-contains-ip4-percent)|partitioned|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_contains_ip6_percent`</span>](./pii/contains-ip6-percent.md#profile-contains-ip6-percent)|profiling|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_contains_ip6_percent`</span>](./pii/contains-ip6-percent.md#daily-contains-ip6-percent)|monitoring|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_contains_ip6_percent`</span>](./pii/contains-ip6-percent.md#monthly-contains-ip6-percent)|monitoring|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_contains_ip6_percent`</span>](./pii/contains-ip6-percent.md#daily-partition-contains-ip6-percent)|partitioned|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_contains_ip6_percent`</span>](./pii/contains-ip6-percent.md#monthly-partition-contains-ip6-percent)|partitioned|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|**|






## **schema**
Detects schema drifts such as a column is missing or the data type has changed.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_column_exists`</span>](./schema/column-exists.md#profile-column-exists)|profiling|Checks the metadata of the monitored table and verifies if the column exists.|**|
|[<span class="no-wrap-code">`daily_column_exists`</span>](./schema/column-exists.md#daily-column-exists)|monitoring|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_column_exists`</span>](./schema/column-exists.md#monthly-column-exists)|monitoring|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each month when the data quality check was evaluated.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_column_type_changed`</span>](./schema/column-type-changed.md#profile-column-type-changed)|profiling|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed.|**|
|[<span class="no-wrap-code">`daily_column_type_changed`</span>](./schema/column-type-changed.md#daily-column-type-changed)|monitoring|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last day. Stores the most recent hash for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_column_type_changed`</span>](./schema/column-type-changed.md#monthly-column-type-changed)|monitoring|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last month. Stores the most recent hash for each month when the data quality check was evaluated.|**|






## **text**
Validates that the data in a text column has a valid range, or can be parsed to other data types.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_max_length`</span>](./text/text-max-length.md#profile-text-max-length)|profiling|Verifies that the length of a text in a column does not exceed the maximum accepted length|**|
|[<span class="no-wrap-code">`daily_text_max_length`</span>](./text/text-max-length.md#daily-text-max-length)|monitoring|Verifies that the length of a text in a column does not exceed the maximum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_text_max_length`</span>](./text/text-max-length.md#monthly-text-max-length)|monitoring|Verifies that the length of a text in a column does not exceed the maximum accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_text_max_length`</span>](./text/text-max-length.md#daily-partition-text-max-length)|partitioned|Verifies that the length of a text in a column does not exceed the maximum accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_text_max_length`</span>](./text/text-max-length.md#monthly-partition-text-max-length)|partitioned|Verifies that the length of a text in a column does not exceed the maximum accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_min_length`</span>](./text/text-min-length.md#profile-text-min-length)|profiling|Verifies that the length of a text in a column does not fall below the minimum accepted length|**|
|[<span class="no-wrap-code">`daily_text_min_length`</span>](./text/text-min-length.md#daily-text-min-length)|monitoring|Verifies that the length of a text in a column does not fall below the minimum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_text_min_length`</span>](./text/text-min-length.md#monthly-text-min-length)|monitoring|Verifies that the length of a text in a column does not fall below the minimum accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_text_min_length`</span>](./text/text-min-length.md#daily-partition-text-min-length)|partitioned|Verifies that the length of a text in a column does not fall below the minimum accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_text_min_length`</span>](./text/text-min-length.md#monthly-partition-text-min-length)|partitioned|Verifies that the length of a text in a column does not fall below the minimum accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_mean_length`</span>](./text/text-mean-length.md#profile-text-mean-length)|profiling|Verifies that the length of a text in a column does not exceed the mean accepted length|**|
|[<span class="no-wrap-code">`daily_text_mean_length`</span>](./text/text-mean-length.md#daily-text-mean-length)|monitoring|Verifies that the length of a text in a column does not exceed the mean accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_text_mean_length`</span>](./text/text-mean-length.md#monthly-text-mean-length)|monitoring|Verifies that the length of a text in a column does not exceed the mean accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_text_mean_length`</span>](./text/text-mean-length.md#daily-partition-text-mean-length)|partitioned|Verifies that the length of a text in a column does not exceed the mean accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_text_mean_length`</span>](./text/text-mean-length.md#monthly-partition-text-mean-length)|partitioned|Verifies that the length of a text in a column does not exceed the mean accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_length_below_min_length`</span>](./text/text-length-below-min-length.md#profile-text-length-below-min-length)|profiling|The check counts the number of text values in the column that is below the length defined by the user as a parameter|**|
|[<span class="no-wrap-code">`daily_text_length_below_min_length`</span>](./text/text-length-below-min-length.md#daily-text-length-below-min-length)|monitoring|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_text_length_below_min_length`</span>](./text/text-length-below-min-length.md#monthly-text-length-below-min-length)|monitoring|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_text_length_below_min_length`</span>](./text/text-length-below-min-length.md#daily-partition-text-length-below-min-length)|partitioned|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_text_length_below_min_length`</span>](./text/text-length-below-min-length.md#monthly-partition-text-length-below-min-length)|partitioned|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_length_below_min_length_percent`</span>](./text/text-length-below-min-length-percent.md#profile-text-length-below-min-length-percent)|profiling|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter|**|
|[<span class="no-wrap-code">`daily_text_length_below_min_length_percent`</span>](./text/text-length-below-min-length-percent.md#daily-text-length-below-min-length-percent)|monitoring|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_text_length_below_min_length_percent`</span>](./text/text-length-below-min-length-percent.md#monthly-text-length-below-min-length-percent)|monitoring|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_text_length_below_min_length_percent`</span>](./text/text-length-below-min-length-percent.md#daily-partition-text-length-below-min-length-percent)|partitioned|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_text_length_below_min_length_percent`</span>](./text/text-length-below-min-length-percent.md#monthly-partition-text-length-below-min-length-percent)|partitioned|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_length_above_max_length`</span>](./text/text-length-above-max-length.md#profile-text-length-above-max-length)|profiling|The check counts the number of text values in the column that is above the length defined by the user as a parameter|**|
|[<span class="no-wrap-code">`daily_text_length_above_max_length`</span>](./text/text-length-above-max-length.md#daily-text-length-above-max-length)|monitoring|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_text_length_above_max_length`</span>](./text/text-length-above-max-length.md#monthly-text-length-above-max-length)|monitoring|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_text_length_above_max_length`</span>](./text/text-length-above-max-length.md#daily-partition-text-length-above-max-length)|partitioned|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_text_length_above_max_length`</span>](./text/text-length-above-max-length.md#monthly-partition-text-length-above-max-length)|partitioned|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_length_above_max_length_percent`</span>](./text/text-length-above-max-length-percent.md#profile-text-length-above-max-length-percent)|profiling|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter|**|
|[<span class="no-wrap-code">`daily_text_length_above_max_length_percent`</span>](./text/text-length-above-max-length-percent.md#daily-text-length-above-max-length-percent)|monitoring|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_text_length_above_max_length_percent`</span>](./text/text-length-above-max-length-percent.md#monthly-text-length-above-max-length-percent)|monitoring|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_text_length_above_max_length_percent`</span>](./text/text-length-above-max-length-percent.md#daily-partition-text-length-above-max-length-percent)|partitioned|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_text_length_above_max_length_percent`</span>](./text/text-length-above-max-length-percent.md#monthly-partition-text-length-above-max-length-percent)|partitioned|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_length_in_range_percent`</span>](./text/text-length-in-range-percent.md#profile-text-length-in-range-percent)|profiling|The check measures the percentage of those text values with length in the range provided by the user in the column|**|
|[<span class="no-wrap-code">`daily_text_length_in_range_percent`</span>](./text/text-length-in-range-percent.md#daily-text-length-in-range-percent)|monitoring|The check measures the percentage of those text values with length in the range provided by the user in the column. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_text_length_in_range_percent`</span>](./text/text-length-in-range-percent.md#monthly-text-length-in-range-percent)|monitoring|The check measures the percentage of those text values with length in the range provided by the user in the column. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_text_length_in_range_percent`</span>](./text/text-length-in-range-percent.md#daily-partition-text-length-in-range-percent)|partitioned|The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_text_length_in_range_percent`</span>](./text/text-length-in-range-percent.md#monthly-partition-text-length-in-range-percent)|partitioned|The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_parsable_to_boolean_percent`</span>](./text/text-parsable-to-boolean-percent.md#profile-text-parsable-to-boolean-percent)|profiling|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n.|**|
|[<span class="no-wrap-code">`daily_text_parsable_to_boolean_percent`</span>](./text/text-parsable-to-boolean-percent.md#daily-text-parsable-to-boolean-percent)|monitoring|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_text_parsable_to_boolean_percent`</span>](./text/text-parsable-to-boolean-percent.md#monthly-text-parsable-to-boolean-percent)|monitoring|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_text_parsable_to_boolean_percent`</span>](./text/text-parsable-to-boolean-percent.md#daily-partition-text-parsable-to-boolean-percent)|partitioned|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_text_parsable_to_boolean_percent`</span>](./text/text-parsable-to-boolean-percent.md#monthly-partition-text-parsable-to-boolean-percent)|partitioned|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_parsable_to_integer_percent`</span>](./text/text-parsable-to-integer-percent.md#profile-text-parsable-to-integer-percent)|profiling|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage|**|
|[<span class="no-wrap-code">`daily_text_parsable_to_integer_percent`</span>](./text/text-parsable-to-integer-percent.md#daily-text-parsable-to-integer-percent)|monitoring|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_text_parsable_to_integer_percent`</span>](./text/text-parsable-to-integer-percent.md#monthly-text-parsable-to-integer-percent)|monitoring|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_text_parsable_to_integer_percent`</span>](./text/text-parsable-to-integer-percent.md#daily-partition-text-parsable-to-integer-percent)|partitioned|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_text_parsable_to_integer_percent`</span>](./text/text-parsable-to-integer-percent.md#monthly-partition-text-parsable-to-integer-percent)|partitioned|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_parsable_to_float_percent`</span>](./text/text-parsable-to-float-percent.md#profile-text-parsable-to-float-percent)|profiling|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage|**|
|[<span class="no-wrap-code">`daily_text_parsable_to_float_percent`</span>](./text/text-parsable-to-float-percent.md#daily-text-parsable-to-float-percent)|monitoring|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_text_parsable_to_float_percent`</span>](./text/text-parsable-to-float-percent.md#monthly-text-parsable-to-float-percent)|monitoring|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_text_parsable_to_float_percent`</span>](./text/text-parsable-to-float-percent.md#daily-partition-text-parsable-to-float-percent)|partitioned|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_text_parsable_to_float_percent`</span>](./text/text-parsable-to-float-percent.md#monthly-partition-text-parsable-to-float-percent)|partitioned|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_parsable_to_date_percent`</span>](./text/text-parsable-to-date-percent.md#profile-text-parsable-to-date-percent)|profiling|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression|**|
|[<span class="no-wrap-code">`daily_text_parsable_to_date_percent`</span>](./text/text-parsable-to-date-percent.md#daily-text-parsable-to-date-percent)|monitoring|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_text_parsable_to_date_percent`</span>](./text/text-parsable-to-date-percent.md#monthly-text-parsable-to-date-percent)|monitoring|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_text_parsable_to_date_percent`</span>](./text/text-parsable-to-date-percent.md#daily-partition-text-parsable-to-date-percent)|partitioned|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_text_parsable_to_date_percent`</span>](./text/text-parsable-to-date-percent.md#monthly-partition-text-parsable-to-date-percent)|partitioned|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_surrounded_by_whitespace`</span>](./text/text-surrounded-by-whitespace.md#profile-text-surrounded-by-whitespace)|profiling|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table|**|
|[<span class="no-wrap-code">`daily_text_surrounded_by_whitespace`</span>](./text/text-surrounded-by-whitespace.md#daily-text-surrounded-by-whitespace)|monitoring|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_text_surrounded_by_whitespace`</span>](./text/text-surrounded-by-whitespace.md#monthly-text-surrounded-by-whitespace)|monitoring|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_text_surrounded_by_whitespace`</span>](./text/text-surrounded-by-whitespace.md#daily-partition-text-surrounded-by-whitespace)|partitioned|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_text_surrounded_by_whitespace`</span>](./text/text-surrounded-by-whitespace.md#monthly-partition-text-surrounded-by-whitespace)|partitioned|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_surrounded_by_whitespace_percent`</span>](./text/text-surrounded-by-whitespace-percent.md#profile-text-surrounded-by-whitespace-percent)|profiling|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage|**|
|[<span class="no-wrap-code">`daily_text_surrounded_by_whitespace_percent`</span>](./text/text-surrounded-by-whitespace-percent.md#daily-text-surrounded-by-whitespace-percent)|monitoring|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_text_surrounded_by_whitespace_percent`</span>](./text/text-surrounded-by-whitespace-percent.md#monthly-text-surrounded-by-whitespace-percent)|monitoring|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_text_surrounded_by_whitespace_percent`</span>](./text/text-surrounded-by-whitespace-percent.md#daily-partition-text-surrounded-by-whitespace-percent)|partitioned|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_text_surrounded_by_whitespace_percent`</span>](./text/text-surrounded-by-whitespace-percent.md#monthly-partition-text-surrounded-by-whitespace-percent)|partitioned|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_valid_country_code_percent`</span>](./text/text-valid-country-code-percent.md#profile-text-valid-country-code-percent)|profiling|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage|**|
|[<span class="no-wrap-code">`daily_text_valid_country_code_percent`</span>](./text/text-valid-country-code-percent.md#daily-text-valid-country-code-percent)|monitoring|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_text_valid_country_code_percent`</span>](./text/text-valid-country-code-percent.md#monthly-text-valid-country-code-percent)|monitoring|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_text_valid_country_code_percent`</span>](./text/text-valid-country-code-percent.md#daily-partition-text-valid-country-code-percent)|partitioned|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_text_valid_country_code_percent`</span>](./text/text-valid-country-code-percent.md#monthly-partition-text-valid-country-code-percent)|partitioned|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_valid_currency_code_percent`</span>](./text/text-valid-currency-code-percent.md#profile-text-valid-currency-code-percent)|profiling|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage|**|
|[<span class="no-wrap-code">`daily_text_valid_currency_code_percent`</span>](./text/text-valid-currency-code-percent.md#daily-text-valid-currency-code-percent)|monitoring|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_text_valid_currency_code_percent`</span>](./text/text-valid-currency-code-percent.md#monthly-text-valid-currency-code-percent)|monitoring|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_text_valid_currency_code_percent`</span>](./text/text-valid-currency-code-percent.md#daily-partition-text-valid-currency-code-percent)|partitioned|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_text_valid_currency_code_percent`</span>](./text/text-valid-currency-code-percent.md#monthly-partition-text-valid-currency-code-percent)|partitioned|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|**|






## **uniqueness**
Counts the number or percent of duplicate or unique values in a column.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_count`</span>](./uniqueness/distinct-count.md#profile-distinct-count)|profiling|Verifies that the number of distinct values in a column does not fall below the minimum accepted count.|**|
|[<span class="no-wrap-code">`daily_distinct_count`</span>](./uniqueness/distinct-count.md#daily-distinct-count)|monitoring|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_distinct_count`</span>](./uniqueness/distinct-count.md#monthly-distinct-count)|monitoring|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_count`</span>](./uniqueness/distinct-count.md#daily-partition-distinct-count)|partitioned|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_distinct_count`</span>](./uniqueness/distinct-count.md#monthly-partition-distinct-count)|partitioned|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_percent`</span>](./uniqueness/distinct-percent.md#profile-distinct-percent)|profiling|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent.|**|
|[<span class="no-wrap-code">`daily_distinct_percent`</span>](./uniqueness/distinct-percent.md#daily-distinct-percent)|monitoring|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_distinct_percent`</span>](./uniqueness/distinct-percent.md#monthly-distinct-percent)|monitoring|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_percent`</span>](./uniqueness/distinct-percent.md#daily-partition-distinct-percent)|partitioned|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_distinct_percent`</span>](./uniqueness/distinct-percent.md#monthly-partition-distinct-percent)|partitioned|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_duplicate_count`</span>](./uniqueness/duplicate-count.md#profile-duplicate-count)|profiling|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_duplicate_count`</span>](./uniqueness/duplicate-count.md#daily-duplicate-count)|monitoring|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_duplicate_count`</span>](./uniqueness/duplicate-count.md#monthly-duplicate-count)|monitoring|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_duplicate_count`</span>](./uniqueness/duplicate-count.md#daily-partition-duplicate-count)|partitioned|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_duplicate_count`</span>](./uniqueness/duplicate-count.md#monthly-partition-duplicate-count)|partitioned|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_duplicate_percent`</span>](./uniqueness/duplicate-percent.md#profile-duplicate-percent)|profiling|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_duplicate_percent`</span>](./uniqueness/duplicate-percent.md#daily-duplicate-percent)|monitoring|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_duplicate_percent`</span>](./uniqueness/duplicate-percent.md#monthly-duplicate-percent)|monitoring|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_duplicate_percent`</span>](./uniqueness/duplicate-percent.md#daily-partition-duplicate-percent)|partitioned|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_duplicate_percent`</span>](./uniqueness/duplicate-percent.md#monthly-partition-duplicate-percent)|partitioned|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_count_anomaly`</span>](./uniqueness/distinct-count-anomaly.md#profile-distinct-count-anomaly)|profiling|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_distinct_count_anomaly`</span>](./uniqueness/distinct-count-anomaly.md#daily-distinct-count-anomaly)|monitoring|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_count_anomaly`</span>](./uniqueness/distinct-count-anomaly.md#daily-partition-distinct-count-anomaly)|partitioned|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_percent_anomaly`</span>](./uniqueness/distinct-percent-anomaly.md#profile-distinct-percent-anomaly)|profiling|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_distinct_percent_anomaly`</span>](./uniqueness/distinct-percent-anomaly.md#daily-distinct-percent-anomaly)|monitoring|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_percent_anomaly`</span>](./uniqueness/distinct-percent-anomaly.md#daily-partition-distinct-percent-anomaly)|partitioned|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_count_change`</span>](./uniqueness/distinct-count-change.md#profile-distinct-count-change)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_distinct_count_change`</span>](./uniqueness/distinct-count-change.md#daily-distinct-count-change)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_distinct_count_change`</span>](./uniqueness/distinct-count-change.md#monthly-distinct-count-change)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_count_change`</span>](./uniqueness/distinct-count-change.md#daily-partition-distinct-count-change)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_partition_distinct_count_change`</span>](./uniqueness/distinct-count-change.md#monthly-partition-distinct-count-change)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_count_change_1_day`</span>](./uniqueness/distinct-count-change-1-day.md#profile-distinct-count-change-1-day)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_distinct_count_change_1_day`</span>](./uniqueness/distinct-count-change-1-day.md#daily-distinct-count-change-1-day)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_count_change_1_day`</span>](./uniqueness/distinct-count-change-1-day.md#daily-partition-distinct-count-change-1-day)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_count_change_7_days`</span>](./uniqueness/distinct-count-change-7-days.md#profile-distinct-count-change-7-days)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|**|
|[<span class="no-wrap-code">`daily_distinct_count_change_7_days`</span>](./uniqueness/distinct-count-change-7-days.md#daily-distinct-count-change-7-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_count_change_7_days`</span>](./uniqueness/distinct-count-change-7-days.md#daily-partition-distinct-count-change-7-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from the last week.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_count_change_30_days`</span>](./uniqueness/distinct-count-change-30-days.md#profile-distinct-count-change-30-days)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|**|
|[<span class="no-wrap-code">`daily_distinct_count_change_30_days`</span>](./uniqueness/distinct-count-change-30-days.md#daily-distinct-count-change-30-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_count_change_30_days`</span>](./uniqueness/distinct-count-change-30-days.md#daily-partition-distinct-count-change-30-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from the last month.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_percent_change`</span>](./uniqueness/distinct-percent-change.md#profile-distinct-percent-change)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_distinct_percent_change`</span>](./uniqueness/distinct-percent-change.md#daily-distinct-percent-change)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_distinct_percent_change`</span>](./uniqueness/distinct-percent-change.md#monthly-distinct-percent-change)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_percent_change`</span>](./uniqueness/distinct-percent-change.md#daily-partition-distinct-percent-change)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_partition_distinct_percent_change`</span>](./uniqueness/distinct-percent-change.md#monthly-partition-distinct-percent-change)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_percent_change_1_day`</span>](./uniqueness/distinct-percent-change-1-day.md#profile-distinct-percent-change-1-day)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_distinct_percent_change_1_day`</span>](./uniqueness/distinct-percent-change-1-day.md#daily-distinct-percent-change-1-day)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_percent_change_1_day`</span>](./uniqueness/distinct-percent-change-1-day.md#daily-partition-distinct-percent-change-1-day)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_percent_change_7_days`</span>](./uniqueness/distinct-percent-change-7-days.md#profile-distinct-percent-change-7-days)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|**|
|[<span class="no-wrap-code">`daily_distinct_percent_change_7_days`</span>](./uniqueness/distinct-percent-change-7-days.md#daily-distinct-percent-change-7-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_percent_change_7_days`</span>](./uniqueness/distinct-percent-change-7-days.md#daily-partition-distinct-percent-change-7-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from the last week.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_percent_change_30_days`</span>](./uniqueness/distinct-percent-change-30-days.md#profile-distinct-percent-change-30-days)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|**|
|[<span class="no-wrap-code">`daily_distinct_percent_change_30_days`</span>](./uniqueness/distinct-percent-change-30-days.md#daily-distinct-percent-change-30-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_percent_change_30_days`</span>](./uniqueness/distinct-percent-change-30-days.md#daily-partition-distinct-percent-change-30-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from the last month.|**|







