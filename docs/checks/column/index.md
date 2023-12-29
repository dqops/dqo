# Checks/column

**This is a list of column data quality checks supported by DQOps, broken down by a category and a brief description of what they do.**





## **accuracy**  


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_total_sum_match_percent](accuracy/total-sum-match-percent/#profile-total-sum-match-percent)|profiling|Verifies that percentage of the difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number.|
|[daily_total_sum_match_percent](accuracy/total-sum-match-percent/#daily-total-sum-match-percent)|monitoring|Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_sum_match_percent](accuracy/total-sum-match-percent/#monthly-total-sum-match-percent)|monitoring|Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_total_min_match_percent](accuracy/total-min-match-percent/#profile-total-min-match-percent)|profiling|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number.|
|[daily_total_min_match_percent](accuracy/total-min-match-percent/#daily-total-min-match-percent)|monitoring|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_min_match_percent](accuracy/total-min-match-percent/#monthly-total-min-match-percent)|monitoring|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_total_max_match_percent](accuracy/total-max-match-percent/#profile-total-max-match-percent)|profiling|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number.|
|[daily_total_max_match_percent](accuracy/total-max-match-percent/#daily-total-max-match-percent)|monitoring|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_max_match_percent](accuracy/total-max-match-percent/#monthly-total-max-match-percent)|monitoring|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_total_average_match_percent](accuracy/total-average-match-percent/#profile-total-average-match-percent)|profiling|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number.|
|[daily_total_average_match_percent](accuracy/total-average-match-percent/#daily-total-average-match-percent)|monitoring|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_average_match_percent](accuracy/total-average-match-percent/#monthly-total-average-match-percent)|monitoring|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_total_not_null_count_match_percent](accuracy/total-not-null-count-match-percent/#profile-total-not-null-count-match-percent)|profiling|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[daily_total_not_null_count_match_percent](accuracy/total-not-null-count-match-percent/#daily-total-not-null-count-match-percent)|monitoring|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_not_null_count_match_percent](accuracy/total-not-null-count-match-percent/#monthly-total-not-null-count-match-percent)|monitoring|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|





## **anomaly**  
Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_anomaly_stationary_30_days](anomaly/mean-anomaly-stationary-30-days/#profile-mean-anomaly-stationary-30-days)|profiling|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_mean_anomaly_stationary_30_days](anomaly/mean-anomaly-stationary-30-days/#daily-mean-anomaly-stationary-30-days)|monitoring|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_partition_mean_anomaly_stationary_30_days](anomaly/mean-anomaly-stationary-30-days/#daily-partition-mean-anomaly-stationary-30-days)|partitioned|Verifies that the mean value in a column is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_anomaly_stationary](anomaly/mean-anomaly-stationary/#profile-mean-anomaly-stationary)|profiling|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_mean_anomaly_stationary](anomaly/mean-anomaly-stationary/#daily-mean-anomaly-stationary)|monitoring|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_partition_mean_anomaly_stationary](anomaly/mean-anomaly-stationary/#daily-partition-mean-anomaly-stationary)|partitioned|Verifies that the mean value in a column is within a percentile from measurements made during the last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_anomaly_stationary_30_days](anomaly/median-anomaly-stationary-30-days/#profile-median-anomaly-stationary-30-days)|profiling|Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_median_anomaly_stationary_30_days](anomaly/median-anomaly-stationary-30-days/#daily-median-anomaly-stationary-30-days)|monitoring|Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_partition_median_anomaly_stationary_30_days](anomaly/median-anomaly-stationary-30-days/#daily-partition-median-anomaly-stationary-30-days)|partitioned|Verifies that the median in a column is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_anomaly_stationary](anomaly/median-anomaly-stationary/#profile-median-anomaly-stationary)|profiling|Verifies that the median in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_median_anomaly_stationary](anomaly/median-anomaly-stationary/#daily-median-anomaly-stationary)|monitoring|Verifies that the median in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_partition_median_anomaly_stationary](anomaly/median-anomaly-stationary/#daily-partition-median-anomaly-stationary)|partitioned|Verifies that the median in a column is within a percentile from measurements made during the last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_anomaly_differencing_30_days](anomaly/sum-anomaly-differencing-30-days/#profile-sum-anomaly-differencing-30-days)|profiling|Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_sum_anomaly_differencing_30_days](anomaly/sum-anomaly-differencing-30-days/#daily-sum-anomaly-differencing-30-days)|monitoring|Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_anomaly_differencing](anomaly/sum-anomaly-differencing/#profile-sum-anomaly-differencing)|profiling|Verifies that the sum in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_sum_anomaly_differencing](anomaly/sum-anomaly-differencing/#daily-sum-anomaly-differencing)|monitoring|Verifies that the sum in a column changes in a rate within a percentile boundary during last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_change](anomaly/mean-change/#profile-mean-change)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[daily_mean_change](anomaly/mean-change/#daily-mean-change)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[monthly_mean_change](anomaly/mean-change/#monthly-mean-change)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[daily_partition_mean_change](anomaly/mean-change/#daily-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[monthly_partition_mean_change](anomaly/mean-change/#monthly-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_change_yesterday](anomaly/mean-change-yesterday/#profile-mean-change-yesterday)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_mean_change_yesterday](anomaly/mean-change-yesterday/#daily-mean-change-yesterday)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_mean_change_yesterday](anomaly/mean-change-yesterday/#daily-partition-mean-change-yesterday)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_change_7_days](anomaly/mean-change-7-days/#profile-mean-change-7-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|
|[daily_mean_change_7_days](anomaly/mean-change-7-days/#daily-mean-change-7-days)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_mean_change_7_days](anomaly/mean-change-7-days/#daily-partition-mean-change-7-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_change_30_days](anomaly/mean-change-30-days/#profile-mean-change-30-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|
|[daily_mean_change_30_days](anomaly/mean-change-30-days/#daily-mean-change-30-days)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_mean_change_30_days](anomaly/mean-change-30-days/#daily-partition-mean-change-30-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_change](anomaly/median-change/#profile-median-change)|profiling|Verifies that the median in a column changed in a fixed rate since last readout.|
|[daily_median_change](anomaly/median-change/#daily-median-change)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout.|
|[monthly_median_change](anomaly/median-change/#monthly-median-change)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout.|
|[daily_partition_median_change](anomaly/median-change/#daily-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout.|
|[monthly_partition_median_change](anomaly/median-change/#monthly-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_change_yesterday](anomaly/median-change-yesterday/#profile-median-change-yesterday)|profiling|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|
|[daily_median_change_yesterday](anomaly/median-change-yesterday/#daily-median-change-yesterday)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_median_change_yesterday](anomaly/median-change-yesterday/#daily-partition-median-change-yesterday)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_change_7_days](anomaly/median-change-7-days/#profile-median-change-7-days)|profiling|Verifies that the median in a column changed in a fixed rate since last readout from last week.|
|[daily_median_change_7_days](anomaly/median-change-7-days/#daily-median-change-7-days)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_median_change_7_days](anomaly/median-change-7-days/#daily-partition-median-change-7-days)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_change_30_days](anomaly/median-change-30-days/#profile-median-change-30-days)|profiling|Verifies that the median in a column changed in a fixed rate since last readout from last month.|
|[daily_median_change_30_days](anomaly/median-change-30-days/#daily-median-change-30-days)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_median_change_30_days](anomaly/median-change-30-days/#daily-partition-median-change-30-days)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_change](anomaly/sum-change/#profile-sum-change)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[daily_sum_change](anomaly/sum-change/#daily-sum-change)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[monthly_sum_change](anomaly/sum-change/#monthly-sum-change)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[daily_partition_sum_change](anomaly/sum-change/#daily-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[monthly_partition_sum_change](anomaly/sum-change/#monthly-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_change_yesterday](anomaly/sum-change-yesterday/#profile-sum-change-yesterday)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|
|[daily_sum_change_yesterday](anomaly/sum-change-yesterday/#daily-sum-change-yesterday)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_sum_change_yesterday](anomaly/sum-change-yesterday/#daily-partition-sum-change-yesterday)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_change_7_days](anomaly/sum-change-7-days/#profile-sum-change-7-days)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|
|[daily_sum_change_7_days](anomaly/sum-change-7-days/#daily-sum-change-7-days)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_sum_change_7_days](anomaly/sum-change-7-days/#daily-partition-sum-change-7-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_change_30_days](anomaly/sum-change-30-days/#profile-sum-change-30-days)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|
|[daily_sum_change_30_days](anomaly/sum-change-30-days/#daily-sum-change-30-days)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_sum_change_30_days](anomaly/sum-change-30-days/#daily-partition-sum-change-30-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_sum_anomaly_stationary_30_days](anomaly/sum-anomaly-stationary-30-days/#daily-partition-sum-anomaly-stationary-30-days)|partitioned|Verifies that the sum in a column is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_sum_anomaly_stationary](anomaly/sum-anomaly-stationary/#daily-partition-sum-anomaly-stationary)|partitioned|Verifies that the sum in a column is within a percentile from measurements made during the last 90 days.|





## **bool**  
Calculates the percentage of data in a Boolean format.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_true_percent](bool/true-percent/#profile-true-percent)|profiling|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage.|
|[daily_true_percent](bool/true-percent/#daily-true-percent)|monitoring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_true_percent](bool/true-percent/#monthly-true-percent)|monitoring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_true_percent](bool/true-percent/#daily-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_true_percent](bool/true-percent/#monthly-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_false_percent](bool/false-percent/#profile-false-percent)|profiling|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage.|
|[daily_false_percent](bool/false-percent/#daily-false-percent)|monitoring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_false_percent](bool/false-percent/#monthly-false-percent)|monitoring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_false_percent](bool/false-percent/#daily-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_false_percent](bool/false-percent/#monthly-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





## **comparisons**  


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_match](comparisons/sum-match/#profile-sum-match)|profiling|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_sum_match](comparisons/sum-match/#daily-sum-match)|monitoring|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sum_match](comparisons/sum-match/#monthly-sum-match)|monitoring|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_sum_match](comparisons/sum-match/#daily-partition-sum-match)|partitioned|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_sum_match](comparisons/sum-match/#monthly-partition-sum-match)|partitioned|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_min_match](comparisons/min-match/#profile-min-match)|profiling|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_min_match](comparisons/min-match/#daily-min-match)|monitoring|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_min_match](comparisons/min-match/#monthly-min-match)|monitoring|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_min_match](comparisons/min-match/#daily-partition-min-match)|partitioned|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_min_match](comparisons/min-match/#monthly-partition-min-match)|partitioned|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_max_match](comparisons/max-match/#profile-max-match)|profiling|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_max_match](comparisons/max-match/#daily-max-match)|monitoring|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_max_match](comparisons/max-match/#monthly-max-match)|monitoring|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_max_match](comparisons/max-match/#daily-partition-max-match)|partitioned|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_max_match](comparisons/max-match/#monthly-partition-max-match)|partitioned|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_match](comparisons/mean-match/#profile-mean-match)|profiling|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_mean_match](comparisons/mean-match/#daily-mean-match)|monitoring|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_mean_match](comparisons/mean-match/#monthly-mean-match)|monitoring|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_mean_match](comparisons/mean-match/#daily-partition-mean-match)|partitioned|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_mean_match](comparisons/mean-match/#monthly-partition-mean-match)|partitioned|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_not_null_count_match](comparisons/not-null-count-match/#profile-not-null-count-match)|profiling|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_not_null_count_match](comparisons/not-null-count-match/#daily-not-null-count-match)|monitoring|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_not_null_count_match](comparisons/not-null-count-match/#monthly-not-null-count-match)|monitoring|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_not_null_count_match](comparisons/not-null-count-match/#daily-partition-not-null-count-match)|partitioned|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_not_null_count_match](comparisons/not-null-count-match/#monthly-partition-not-null-count-match)|partitioned|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_null_count_match](comparisons/null-count-match/#profile-null-count-match)|profiling|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_null_count_match](comparisons/null-count-match/#daily-null-count-match)|monitoring|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_null_count_match](comparisons/null-count-match/#monthly-null-count-match)|monitoring|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_null_count_match](comparisons/null-count-match/#daily-partition-null-count-match)|partitioned|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_null_count_match](comparisons/null-count-match/#monthly-partition-null-count-match)|partitioned|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|





## **datatype**  


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_datatype_detected](datatype/string-datatype-detected/#profile-string-datatype-detected)|profiling|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type.|
|[daily_string_datatype_detected](datatype/string-datatype-detected/#daily-string-datatype-detected)|monitoring|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_datatype_detected](datatype/string-datatype-detected/#monthly-string-datatype-detected)|monitoring|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_datatype_detected](datatype/string-datatype-detected/#daily-partition-string-datatype-detected)|partitioned|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_datatype_detected](datatype/string-datatype-detected/#monthly-partition-string-datatype-detected)|partitioned|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_datatype_changed](datatype/string-datatype-changed/#profile-string-datatype-changed)|profiling|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|
|[daily_string_datatype_changed](datatype/string-datatype-changed/#daily-string-datatype-changed)|monitoring|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_datatype_changed](datatype/string-datatype-changed/#monthly-string-datatype-changed)|monitoring|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[daily_partition_string_datatype_changed](datatype/string-datatype-changed/#daily-partition-string-datatype-changed)|partitioned|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_datatype_changed](datatype/string-datatype-changed/#monthly-partition-string-datatype-changed)|partitioned|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each monthly partition.|





## **datetime**  
Validates that the data in a date or time column is in the expected format and within predefined ranges.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_date_match_format_percent](datetime/date-match-format-percent/#profile-date-match-format-percent)|profiling|Verifies that the percentage of date values matching the given format in a column does not exceed the minimum accepted percentage.|
|[daily_date_match_format_percent](datetime/date-match-format-percent/#daily-date-match-format-percent)|monitoring|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily monitoring.|
|[monthly_date_match_format_percent](datetime/date-match-format-percent/#monthly-date-match-format-percent)|monitoring|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly monitoring.|
|[daily_partition_date_match_format_percent](datetime/date-match-format-percent/#daily-partition-date-match-format-percent)|partitioned|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_date_match_format_percent](datetime/date-match-format-percent/#monthly-partition-date-match-format-percent)|partitioned|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_date_values_in_future_percent](datetime/date-values-in-future-percent/#profile-date-values-in-future-percent)|profiling|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage.|
|[daily_date_values_in_future_percent](datetime/date-values-in-future-percent/#daily-date-values-in-future-percent)|monitoring|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_date_values_in_future_percent](datetime/date-values-in-future-percent/#monthly-date-values-in-future-percent)|monitoring|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_date_values_in_future_percent](datetime/date-values-in-future-percent/#daily-partition-date-values-in-future-percent)|partitioned|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_date_values_in_future_percent](datetime/date-values-in-future-percent/#monthly-partition-date-values-in-future-percent)|partitioned|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_datetime_value_in_range_date_percent](datetime/datetime-value-in-range-date-percent/#profile-datetime-value-in-range-date-percent)|profiling|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage.|
|[daily_datetime_value_in_range_date_percent](datetime/datetime-value-in-range-date-percent/#daily-datetime-value-in-range-date-percent)|monitoring|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_datetime_value_in_range_date_percent](datetime/datetime-value-in-range-date-percent/#monthly-datetime-value-in-range-date-percent)|monitoring|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_datetime_value_in_range_date_percent](datetime/datetime-value-in-range-date-percent/#daily-partition-datetime-value-in-range-date-percent)|partitioned|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_datetime_value_in_range_date_percent](datetime/datetime-value-in-range-date-percent/#monthly-partition-datetime-value-in-range-date-percent)|partitioned|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





## **integrity**  
Checks the referential integrity of a column against a column in another table.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_foreign_key_not_match_count](integrity/foreign-key-not-match-count/#profile-foreign-key-not-match-count)|profiling|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count.|
|[daily_foreign_key_not_match_count](integrity/foreign-key-not-match-count/#daily-foreign-key-not-match-count)|monitoring|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_foreign_key_not_match_count](integrity/foreign-key-not-match-count/#monthly-foreign-key-not-match-count)|monitoring|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_foreign_key_not_match_count](integrity/foreign-key-not-match-count/#daily-partition-foreign-key-not-match-count)|partitioned|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_foreign_key_not_match_count](integrity/foreign-key-not-match-count/#monthly-partition-foreign-key-not-match-count)|partitioned|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_foreign_key_match_percent](integrity/foreign-key-match-percent/#profile-foreign-key-match-percent)|profiling|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count.|
|[daily_foreign_key_match_percent](integrity/foreign-key-match-percent/#daily-foreign-key-match-percent)|monitoring|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_foreign_key_match_percent](integrity/foreign-key-match-percent/#monthly-foreign-key-match-percent)|monitoring|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_foreign_key_match_percent](integrity/foreign-key-match-percent/#daily-partition-foreign-key-match-percent)|partitioned|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_foreign_key_match_percent](integrity/foreign-key-match-percent/#monthly-partition-foreign-key-match-percent)|partitioned|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|





## **nulls**  
Checks for the presence of null or missing values in a column.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_count](nulls/nulls-count/#profile-nulls-count)|profiling|Verifies that the number of null values in a column does not exceed the maximum accepted count.|
|[daily_nulls_count](nulls/nulls-count/#daily-nulls-count)|monitoring|Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_nulls_count](nulls/nulls-count/#monthly-nulls-count)|monitoring|Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_nulls_count](nulls/nulls-count/#daily-partition-nulls-count)|partitioned|Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_nulls_count](nulls/nulls-count/#monthly-partition-nulls-count)|partitioned|Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent](nulls/nulls-percent/#profile-nulls-percent)|profiling|Verifies that the percent of null values in a column does not exceed the maximum accepted percentage.|
|[daily_nulls_percent](nulls/nulls-percent/#daily-nulls-percent)|monitoring|Verifies that the percentage of nulls in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_nulls_percent](nulls/nulls-percent/#monthly-nulls-percent)|monitoring|Verifies that the percentage of null values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_nulls_percent](nulls/nulls-percent/#daily-partition-nulls-percent)|partitioned|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_nulls_percent](nulls/nulls-percent/#monthly-partition-nulls-percent)|partitioned|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_anomaly_stationary_30_days](nulls/nulls-percent-anomaly-stationary-30-days/#profile-nulls-percent-anomaly-stationary-30-days)|profiling|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_nulls_percent_anomaly_stationary_30_days](nulls/nulls-percent-anomaly-stationary-30-days/#daily-nulls-percent-anomaly-stationary-30-days)|monitoring|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_partition_nulls_percent_anomaly_stationary_30_days](nulls/nulls-percent-anomaly-stationary-30-days/#daily-partition-nulls-percent-anomaly-stationary-30-days)|partitioned|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_anomaly_stationary](nulls/nulls-percent-anomaly-stationary/#profile-nulls-percent-anomaly-stationary)|profiling|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_nulls_percent_anomaly_stationary](nulls/nulls-percent-anomaly-stationary/#daily-nulls-percent-anomaly-stationary)|monitoring|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_partition_nulls_percent_anomaly_stationary](nulls/nulls-percent-anomaly-stationary/#daily-partition-nulls-percent-anomaly-stationary)|partitioned|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_change](nulls/nulls-percent-change/#profile-nulls-percent-change)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout.|
|[daily_nulls_percent_change](nulls/nulls-percent-change/#daily-nulls-percent-change)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since last readout.|
|[daily_partition_nulls_percent_change](nulls/nulls-percent-change/#daily-partition-nulls-percent-change)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_change_yesterday](nulls/nulls-percent-change-yesterday/#profile-nulls-percent-change-yesterday)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_nulls_percent_change_yesterday](nulls/nulls-percent-change-yesterday/#daily-nulls-percent-change-yesterday)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_nulls_percent_change_yesterday](nulls/nulls-percent-change-yesterday/#daily-partition-nulls-percent-change-yesterday)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_change_7_days](nulls/nulls-percent-change-7-days/#profile-nulls-percent-change-7-days)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|
|[daily_nulls_percent_change_7_days](nulls/nulls-percent-change-7-days/#daily-nulls-percent-change-7-days)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_nulls_percent_change_7_days](nulls/nulls-percent-change-7-days/#daily-partition-nulls-percent-change-7-days)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_change_30_days](nulls/nulls-percent-change-30-days/#profile-nulls-percent-change-30-days)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|
|[daily_nulls_percent_change_30_days](nulls/nulls-percent-change-30-days/#daily-nulls-percent-change-30-days)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_nulls_percent_change_30_days](nulls/nulls-percent-change-30-days/#daily-partition-nulls-percent-change-30-days)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_not_nulls_count](nulls/not-nulls-count/#profile-not-nulls-count)|profiling|Verifies that the number of not null values in a column does not exceed the minimum accepted count.|
|[daily_not_nulls_count](nulls/not-nulls-count/#daily-not-nulls-count)|monitoring|Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_not_nulls_count](nulls/not-nulls-count/#monthly-not-nulls-count)|monitoring|Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_not_nulls_count](nulls/not-nulls-count/#daily-partition-not-nulls-count)|partitioned|Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_not_nulls_count](nulls/not-nulls-count/#monthly-partition-not-nulls-count)|partitioned|Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_not_nulls_percent](nulls/not-nulls-percent/#profile-not-nulls-percent)|profiling|Verifies that the percent of not null values in a column does not exceed the minimum accepted percentage.|
|[daily_not_nulls_percent](nulls/not-nulls-percent/#daily-not-nulls-percent)|monitoring|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_not_nulls_percent](nulls/not-nulls-percent/#monthly-not-nulls-percent)|monitoring|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_not_nulls_percent](nulls/not-nulls-percent/#daily-partition-not-nulls-percent)|partitioned|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_not_nulls_percent](nulls/not-nulls-percent/#monthly-partition-not-nulls-percent)|partitioned|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





## **numeric**  
Validates that the data in a numeric column is in the expected format or within predefined ranges.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_negative_count](numeric/negative-count/#profile-negative-count)|profiling|Verifies that the number of negative values in a column does not exceed the maximum accepted count.|
|[daily_negative_count](numeric/negative-count/#daily-negative-count)|monitoring|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_negative_count](numeric/negative-count/#monthly-negative-count)|monitoring|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_negative_count](numeric/negative-count/#daily-partition-negative-count)|partitioned|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_negative_count](numeric/negative-count/#monthly-partition-negative-count)|partitioned|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_negative_percent](numeric/negative-percent/#profile-negative-percent)|profiling|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.|
|[daily_negative_percent](numeric/negative-percent/#daily-negative-percent)|monitoring|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_negative_percent](numeric/negative-percent/#monthly-negative-percent)|monitoring|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_negative_percent](numeric/negative-percent/#daily-partition-negative-percent)|partitioned|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_negative_percent](numeric/negative-percent/#monthly-partition-negative-percent)|partitioned|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_non_negative_count](numeric/non-negative-count/#profile-non-negative-count)|profiling|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count.|
|[daily_non_negative_count](numeric/non-negative-count/#daily-non-negative-count)|monitoring|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_non_negative_count](numeric/non-negative-count/#monthly-non-negative-count)|monitoring|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_non_negative_count](numeric/non-negative-count/#daily-partition-non-negative-count)|partitioned|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_non_negative_count](numeric/non-negative-count/#monthly-partition-non-negative-count)|partitioned|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_non_negative_percent](numeric/non-negative-percent/#profile-non-negative-percent)|profiling|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage.|
|[daily_non_negative_percent](numeric/non-negative-percent/#daily-non-negative-percent)|monitoring|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_non_negative_percent](numeric/non-negative-percent/#monthly-non-negative-percent)|monitoring|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_non_negative_percent](numeric/non-negative-percent/#daily-partition-non-negative-percent)|partitioned|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_non_negative_percent](numeric/non-negative-percent/#monthly-partition-non-negative-percent)|partitioned|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_expected_numbers_in_use_count](numeric/expected-numbers-in-use-count/#profile-expected-numbers-in-use-count)|profiling|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|
|[daily_expected_numbers_in_use_count](numeric/expected-numbers-in-use-count/#daily-expected-numbers-in-use-count)|monitoring|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_expected_numbers_in_use_count](numeric/expected-numbers-in-use-count/#monthly-expected-numbers-in-use-count)|monitoring|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_expected_numbers_in_use_count](numeric/expected-numbers-in-use-count/#daily-partition-expected-numbers-in-use-count)|partitioned|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_expected_numbers_in_use_count](numeric/expected-numbers-in-use-count/#monthly-partition-expected-numbers-in-use-count)|partitioned|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_number_value_in_set_percent](numeric/number-value-in-set-percent/#profile-number-value-in-set-percent)|profiling|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|
|[daily_number_value_in_set_percent](numeric/number-value-in-set-percent/#daily-number-value-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_number_value_in_set_percent](numeric/number-value-in-set-percent/#monthly-number-value-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_number_value_in_set_percent](numeric/number-value-in-set-percent/#daily-partition-number-value-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_number_value_in_set_percent](numeric/number-value-in-set-percent/#monthly-partition-number-value-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_values_in_range_numeric_percent](numeric/values-in-range-numeric-percent/#profile-values-in-range-numeric-percent)|profiling|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|
|[daily_values_in_range_numeric_percent](numeric/values-in-range-numeric-percent/#daily-values-in-range-numeric-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_values_in_range_numeric_percent](numeric/values-in-range-numeric-percent/#monthly-values-in-range-numeric-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_values_in_range_numeric_percent](numeric/values-in-range-numeric-percent/#daily-partition-values-in-range-numeric-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_values_in_range_numeric_percent](numeric/values-in-range-numeric-percent/#monthly-partition-values-in-range-numeric-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_values_in_range_integers_percent](numeric/values-in-range-integers-percent/#profile-values-in-range-integers-percent)|profiling|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|
|[daily_values_in_range_integers_percent](numeric/values-in-range-integers-percent/#daily-values-in-range-integers-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_values_in_range_integers_percent](numeric/values-in-range-integers-percent/#monthly-values-in-range-integers-percent)|monitoring|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_values_in_range_integers_percent](numeric/values-in-range-integers-percent/#daily-partition-values-in-range-integers-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_values_in_range_integers_percent](numeric/values-in-range-integers-percent/#monthly-partition-values-in-range-integers-percent)|partitioned|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_value_below_min_value_count](numeric/value-below-min-value-count/#profile-value-below-min-value-count)|profiling|The check counts the number of values in the column that is below the value defined by the user as a parameter.|
|[daily_value_below_min_value_count](numeric/value-below-min-value-count/#daily-value-below-min-value-count)|monitoring|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_value_below_min_value_count](numeric/value-below-min-value-count/#monthly-value-below-min-value-count)|monitoring|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_below_min_value_count](numeric/value-below-min-value-count/#daily-partition-value-below-min-value-count)|partitioned|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_below_min_value_count](numeric/value-below-min-value-count/#monthly-partition-value-below-min-value-count)|partitioned|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_value_below_min_value_percent](numeric/value-below-min-value-percent/#profile-value-below-min-value-percent)|profiling|The check counts the percentage of values in the column that is below the value defined by the user as a parameter.|
|[daily_value_below_min_value_percent](numeric/value-below-min-value-percent/#daily-value-below-min-value-percent)|monitoring|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_value_below_min_value_percent](numeric/value-below-min-value-percent/#monthly-value-below-min-value-percent)|monitoring|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_below_min_value_percent](numeric/value-below-min-value-percent/#daily-partition-value-below-min-value-percent)|partitioned|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_below_min_value_percent](numeric/value-below-min-value-percent/#monthly-partition-value-below-min-value-percent)|partitioned|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_value_above_max_value_count](numeric/value-above-max-value-count/#profile-value-above-max-value-count)|profiling|The check counts the number of values in the column that is above the value defined by the user as a parameter.|
|[daily_value_above_max_value_count](numeric/value-above-max-value-count/#daily-value-above-max-value-count)|monitoring|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_value_above_max_value_count](numeric/value-above-max-value-count/#monthly-value-above-max-value-count)|monitoring|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_above_max_value_count](numeric/value-above-max-value-count/#daily-partition-value-above-max-value-count)|partitioned|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_above_max_value_count](numeric/value-above-max-value-count/#monthly-partition-value-above-max-value-count)|partitioned|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_value_above_max_value_percent](numeric/value-above-max-value-percent/#profile-value-above-max-value-percent)|profiling|The check counts the percentage of values in the column that is above the value defined by the user as a parameter.|
|[daily_value_above_max_value_percent](numeric/value-above-max-value-percent/#daily-value-above-max-value-percent)|monitoring|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_value_above_max_value_percent](numeric/value-above-max-value-percent/#monthly-value-above-max-value-percent)|monitoring|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_value_above_max_value_percent](numeric/value-above-max-value-percent/#daily-partition-value-above-max-value-percent)|partitioned|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_value_above_max_value_percent](numeric/value-above-max-value-percent/#monthly-partition-value-above-max-value-percent)|partitioned|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_max_in_range](numeric/max-in-range/#profile-max-in-range)|profiling|Verifies that the maximal value in a column is not outside the set range.|
|[daily_max_in_range](numeric/max-in-range/#daily-max-in-range)|monitoring|Verifies that the maximal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_max_in_range](numeric/max-in-range/#monthly-max-in-range)|monitoring|Verifies that the maximal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_max_in_range](numeric/max-in-range/#daily-partition-max-in-range)|partitioned|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_max_in_range](numeric/max-in-range/#monthly-partition-max-in-range)|partitioned|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_min_in_range](numeric/min-in-range/#profile-min-in-range)|profiling|Verifies that the minimal value in a column is not outside the set range.|
|[daily_min_in_range](numeric/min-in-range/#daily-min-in-range)|monitoring|Verifies that the minimal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_min_in_range](numeric/min-in-range/#monthly-min-in-range)|monitoring|Verifies that the minimal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_min_in_range](numeric/min-in-range/#daily-partition-min-in-range)|partitioned|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_min_in_range](numeric/min-in-range/#monthly-partition-min-in-range)|partitioned|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_in_range](numeric/mean-in-range/#profile-mean-in-range)|profiling|Verifies that the average (mean) of all values in a column is not outside the set range.|
|[daily_mean_in_range](numeric/mean-in-range/#daily-mean-in-range)|monitoring|Verifies that the average (mean) of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_mean_in_range](numeric/mean-in-range/#monthly-mean-in-range)|monitoring|Verifies that the average (mean) of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_mean_in_range](numeric/mean-in-range/#daily-partition-mean-in-range)|partitioned|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_mean_in_range](numeric/mean-in-range/#monthly-partition-mean-in-range)|partitioned|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_percentile_in_range](numeric/percentile-in-range/#profile-percentile-in-range)|profiling|Verifies that the percentile of all values in a column is not outside the set range.|
|[daily_percentile_in_range](numeric/percentile-in-range/#daily-percentile-in-range)|monitoring|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_percentile_in_range](numeric/percentile-in-range/#monthly-percentile-in-range)|monitoring|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_percentile_in_range](numeric/percentile-in-range/#daily-partition-percentile-in-range)|partitioned|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_percentile_in_range](numeric/percentile-in-range/#monthly-partition-percentile-in-range)|partitioned|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_in_range](numeric/median-in-range/#profile-median-in-range)|profiling|Verifies that the median of all values in a column is not outside the set range.|
|[daily_median_in_range](numeric/median-in-range/#daily-median-in-range)|monitoring|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[monthly_median_in_range](numeric/median-in-range/#monthly-median-in-range)|monitoring|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_median_in_range](numeric/median-in-range/#daily-partition-median-in-range)|partitioned|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_median_in_range](numeric/median-in-range/#monthly-partition-median-in-range)|partitioned|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_percentile_10_in_range](numeric/percentile-10-in-range/#profile-percentile-10-in-range)|profiling|Verifies that the percentile 10 of all values in a column is not outside the set range.|
|[daily_percentile_10_in_range](numeric/percentile-10-in-range/#daily-percentile-10-in-range)|monitoring|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_percentile_10_in_range](numeric/percentile-10-in-range/#monthly-percentile-10-in-range)|monitoring|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_percentile_10_in_range](numeric/percentile-10-in-range/#daily-partition-percentile-10-in-range)|partitioned|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_percentile_10_in_range](numeric/percentile-10-in-range/#monthly-partition-percentile-10-in-range)|partitioned|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_percentile_25_in_range](numeric/percentile-25-in-range/#profile-percentile-25-in-range)|profiling|Verifies that the percentile 25 of all values in a column is not outside the set range.|
|[daily_percentile_25_in_range](numeric/percentile-25-in-range/#daily-percentile-25-in-range)|monitoring|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_percentile_25_in_range](numeric/percentile-25-in-range/#monthly-percentile-25-in-range)|monitoring|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_percentile_25_in_range](numeric/percentile-25-in-range/#daily-partition-percentile-25-in-range)|partitioned|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_percentile_25_in_range](numeric/percentile-25-in-range/#monthly-partition-percentile-25-in-range)|partitioned|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_percentile_75_in_range](numeric/percentile-75-in-range/#profile-percentile-75-in-range)|profiling|Verifies that the percentile 75 of all values in a column is not outside the set range.|
|[daily_percentile_75_in_range](numeric/percentile-75-in-range/#daily-percentile-75-in-range)|monitoring|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_percentile_75_in_range](numeric/percentile-75-in-range/#monthly-percentile-75-in-range)|monitoring|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_percentile_75_in_range](numeric/percentile-75-in-range/#daily-partition-percentile-75-in-range)|partitioned|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_percentile_75_in_range](numeric/percentile-75-in-range/#monthly-partition-percentile-75-in-range)|partitioned|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_percentile_90_in_range](numeric/percentile-90-in-range/#profile-percentile-90-in-range)|profiling|Verifies that the percentile 90 of all values in a column is not outside the set range.|
|[daily_percentile_90_in_range](numeric/percentile-90-in-range/#daily-percentile-90-in-range)|monitoring|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_percentile_90_in_range](numeric/percentile-90-in-range/#monthly-percentile-90-in-range)|monitoring|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_percentile_90_in_range](numeric/percentile-90-in-range/#daily-partition-percentile-90-in-range)|partitioned|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_percentile_90_in_range](numeric/percentile-90-in-range/#monthly-partition-percentile-90-in-range)|partitioned|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sample_stddev_in_range](numeric/sample-stddev-in-range/#profile-sample-stddev-in-range)|profiling|Verifies that the sample standard deviation of all values in a column is not outside the set range.|
|[daily_sample_stddev_in_range](numeric/sample-stddev-in-range/#daily-sample-stddev-in-range)|monitoring|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sample_stddev_in_range](numeric/sample-stddev-in-range/#monthly-sample-stddev-in-range)|monitoring|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sample_stddev_in_range](numeric/sample-stddev-in-range/#daily-partition-sample-stddev-in-range)|partitioned|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sample_stddev_in_range](numeric/sample-stddev-in-range/#monthly-partition-sample-stddev-in-range)|partitioned|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_population_stddev_in_range](numeric/population-stddev-in-range/#profile-population-stddev-in-range)|profiling|Verifies that the population standard deviation of all values in a column is not outside the set range.|
|[daily_population_stddev_in_range](numeric/population-stddev-in-range/#daily-population-stddev-in-range)|monitoring|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_population_stddev_in_range](numeric/population-stddev-in-range/#monthly-population-stddev-in-range)|monitoring|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_population_stddev_in_range](numeric/population-stddev-in-range/#daily-partition-population-stddev-in-range)|partitioned|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_population_stddev_in_range](numeric/population-stddev-in-range/#monthly-partition-population-stddev-in-range)|partitioned|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sample_variance_in_range](numeric/sample-variance-in-range/#profile-sample-variance-in-range)|profiling|Verifies that the sample variance of all values in a column is not outside the set range.|
|[daily_sample_variance_in_range](numeric/sample-variance-in-range/#daily-sample-variance-in-range)|monitoring|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sample_variance_in_range](numeric/sample-variance-in-range/#monthly-sample-variance-in-range)|monitoring|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sample_variance_in_range](numeric/sample-variance-in-range/#daily-partition-sample-variance-in-range)|partitioned|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sample_variance_in_range](numeric/sample-variance-in-range/#monthly-partition-sample-variance-in-range)|partitioned|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_population_variance_in_range](numeric/population-variance-in-range/#profile-population-variance-in-range)|profiling|Verifies that the population variance of all values in a column is not outside the set range.|
|[daily_population_variance_in_range](numeric/population-variance-in-range/#daily-population-variance-in-range)|monitoring|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_population_variance_in_range](numeric/population-variance-in-range/#monthly-population-variance-in-range)|monitoring|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_population_variance_in_range](numeric/population-variance-in-range/#daily-partition-population-variance-in-range)|partitioned|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_population_variance_in_range](numeric/population-variance-in-range/#monthly-partition-population-variance-in-range)|partitioned|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_in_range](numeric/sum-in-range/#profile-sum-in-range)|profiling|Verifies that the sum of all values in a column is not outside the set range.|
|[daily_sum_in_range](numeric/sum-in-range/#daily-sum-in-range)|monitoring|Verifies that the sum of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sum_in_range](numeric/sum-in-range/#monthly-sum-in-range)|monitoring|Verifies that the sum of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sum_in_range](numeric/sum-in-range/#daily-partition-sum-in-range)|partitioned|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sum_in_range](numeric/sum-in-range/#monthly-partition-sum-in-range)|partitioned|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_invalid_latitude_count](numeric/invalid-latitude-count/#profile-invalid-latitude-count)|profiling|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count.|
|[daily_invalid_latitude_count](numeric/invalid-latitude-count/#daily-invalid-latitude-count)|monitoring|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_invalid_latitude_count](numeric/invalid-latitude-count/#monthly-invalid-latitude-count)|monitoring|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_invalid_latitude_count](numeric/invalid-latitude-count/#daily-partition-invalid-latitude-count)|partitioned|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_invalid_latitude_count](numeric/invalid-latitude-count/#monthly-partition-invalid-latitude-count)|partitioned|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_valid_latitude_percent](numeric/valid-latitude-percent/#profile-valid-latitude-percent)|profiling|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage.|
|[daily_valid_latitude_percent](numeric/valid-latitude-percent/#daily-valid-latitude-percent)|monitoring|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_valid_latitude_percent](numeric/valid-latitude-percent/#monthly-valid-latitude-percent)|monitoring|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_latitude_percent](numeric/valid-latitude-percent/#daily-partition-valid-latitude-percent)|partitioned|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_latitude_percent](numeric/valid-latitude-percent/#monthly-partition-valid-latitude-percent)|partitioned|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_invalid_longitude_count](numeric/invalid-longitude-count/#profile-invalid-longitude-count)|profiling|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count.|
|[daily_invalid_longitude_count](numeric/invalid-longitude-count/#daily-invalid-longitude-count)|monitoring|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_invalid_longitude_count](numeric/invalid-longitude-count/#monthly-invalid-longitude-count)|monitoring|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_invalid_longitude_count](numeric/invalid-longitude-count/#daily-partition-invalid-longitude-count)|partitioned|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_invalid_longitude_count](numeric/invalid-longitude-count/#monthly-partition-invalid-longitude-count)|partitioned|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_valid_longitude_percent](numeric/valid-longitude-percent/#profile-valid-longitude-percent)|profiling|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage.|
|[daily_valid_longitude_percent](numeric/valid-longitude-percent/#daily-valid-longitude-percent)|monitoring|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_valid_longitude_percent](numeric/valid-longitude-percent/#monthly-valid-longitude-percent)|monitoring|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_longitude_percent](numeric/valid-longitude-percent/#daily-partition-valid-longitude-percent)|partitioned|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_longitude_percent](numeric/valid-longitude-percent/#monthly-partition-valid-longitude-percent)|partitioned|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





## **pii**  
Checks for the presence of sensitive or personally identifiable information (PII) in a column such as email, phone, zip code, IP4 and IP6 addresses.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_contains_usa_phone_percent](pii/contains-usa-phone-percent/#profile-contains-usa-phone-percent)|profiling|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage.|
|[daily_contains_usa_phone_percent](pii/contains-usa-phone-percent/#daily-contains-usa-phone-percent)|monitoring|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_usa_phone_percent](pii/contains-usa-phone-percent/#monthly-contains-usa-phone-percent)|monitoring|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_usa_phone_percent](pii/contains-usa-phone-percent/#daily-partition-contains-usa-phone-percent)|partitioned|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_usa_phone_percent](pii/contains-usa-phone-percent/#monthly-partition-contains-usa-phone-percent)|partitioned|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_contains_usa_zipcode_percent](pii/contains-usa-zipcode-percent/#profile-contains-usa-zipcode-percent)|profiling|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.|
|[daily_contains_usa_zipcode_percent](pii/contains-usa-zipcode-percent/#daily-contains-usa-zipcode-percent)|monitoring|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_usa_zipcode_percent](pii/contains-usa-zipcode-percent/#monthly-contains-usa-zipcode-percent)|monitoring|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_usa_zipcode_percent](pii/contains-usa-zipcode-percent/#daily-partition-contains-usa-zipcode-percent)|partitioned|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_usa_zipcode_percent](pii/contains-usa-zipcode-percent/#monthly-partition-contains-usa-zipcode-percent)|partitioned|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_contains_email_percent](pii/contains-email-percent/#profile-contains-email-percent)|profiling|Verifies that the percentage of rows that contains valid emails in a column does not exceed the minimum accepted percentage.|
|[daily_contains_email_percent](pii/contains-email-percent/#daily-contains-email-percent)|monitoring|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_email_percent](pii/contains-email-percent/#monthly-contains-email-percent)|monitoring|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_email_percent](pii/contains-email-percent/#daily-partition-contains-email-percent)|partitioned|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_email_percent](pii/contains-email-percent/#monthly-partition-contains-email-percent)|partitioned|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_contains_ip4_percent](pii/contains-ip4-percent/#profile-contains-ip4-percent)|profiling|Verifies that the percentage of rows that contains valid IP4 address values in a column does not fall below the minimum accepted percentage.|
|[daily_contains_ip4_percent](pii/contains-ip4-percent/#daily-contains-ip4-percent)|monitoring|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_ip4_percent](pii/contains-ip4-percent/#monthly-contains-ip4-percent)|monitoring|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_ip4_percent](pii/contains-ip4-percent/#daily-partition-contains-ip4-percent)|partitioned|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_ip4_percent](pii/contains-ip4-percent/#monthly-partition-contains-ip4-percent)|partitioned|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_contains_ip6_percent](pii/contains-ip6-percent/#profile-contains-ip6-percent)|profiling|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage.|
|[daily_contains_ip6_percent](pii/contains-ip6-percent/#daily-contains-ip6-percent)|monitoring|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_contains_ip6_percent](pii/contains-ip6-percent/#monthly-contains-ip6-percent)|monitoring|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_contains_ip6_percent](pii/contains-ip6-percent/#daily-partition-contains-ip6-percent)|partitioned|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_contains_ip6_percent](pii/contains-ip6-percent/#monthly-partition-contains-ip6-percent)|partitioned|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





## **schema**  
Detects schema drifts such as a column is missing or the data type has changed.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_exists](schema/column-exists/#profile-column-exists)|profiling|Checks the metadata of the monitored table and verifies if the column exists.|
|[daily_column_exists](schema/column-exists/#daily-column-exists)|monitoring|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each day when the data quality check was evaluated.|
|[monthly_column_exists](schema/column-exists/#monthly-column-exists)|monitoring|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_type_changed](schema/column-type-changed/#profile-column-type-changed)|profiling|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed.|
|[daily_column_type_changed](schema/column-type-changed/#daily-column-type-changed)|monitoring|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last day. Stores the most recent hash for each day when the data quality check was evaluated.|
|[monthly_column_type_changed](schema/column-type-changed/#monthly-column-type-changed)|monitoring|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last month. Stores the most recent hash for each month when the data quality check was evaluated.|





## **sql**  
Validate data against user-defined SQL queries at the column level. Checks in this group allows to validate that the set percentage of rows passed a custom SQL expression or that the custom SQL expression is not outside the set range.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sql_condition_passed_percent_on_column](sql/sql-condition-passed-percent-on-column/#profile-sql-condition-passed-percent-on-column)|profiling|Verifies that a minimum percentage of rows passed a custom SQL condition (expression).|
|[daily_sql_condition_passed_percent_on_column](sql/sql-condition-passed-percent-on-column/#daily-sql-condition-passed-percent-on-column)|monitoring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_condition_passed_percent_on_column](sql/sql-condition-passed-percent-on-column/#monthly-sql-condition-passed-percent-on-column)|monitoring|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_condition_passed_percent_on_column](sql/sql-condition-passed-percent-on-column/#daily-partition-sql-condition-passed-percent-on-column)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_condition_passed_percent_on_column](sql/sql-condition-passed-percent-on-column/#monthly-partition-sql-condition-passed-percent-on-column)|partitioned|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sql_condition_failed_count_on_column](sql/sql-condition-failed-count-on-column/#profile-sql-condition-failed-count-on-column)|profiling|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count.|
|[daily_sql_condition_failed_count_on_column](sql/sql-condition-failed-count-on-column/#daily-sql-condition-failed-count-on-column)|monitoring|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_condition_failed_count_on_column](sql/sql-condition-failed-count-on-column/#monthly-sql-condition-failed-count-on-column)|monitoring|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_condition_failed_count_on_column](sql/sql-condition-failed-count-on-column/#daily-partition-sql-condition-failed-count-on-column)|partitioned|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_condition_failed_count_on_column](sql/sql-condition-failed-count-on-column/#monthly-partition-sql-condition-failed-count-on-column)|partitioned|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sql_aggregate_expr_column](sql/sql-aggregate-expr-column/#profile-sql-aggregate-expr-column)|profiling|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|
|[daily_sql_aggregate_expr_column](sql/sql-aggregate-expr-column/#daily-sql-aggregate-expr-column)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sql_aggregate_expr_column](sql/sql-aggregate-expr-column/#monthly-sql-aggregate-expr-column)|monitoring|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_sql_aggregate_expr_column](sql/sql-aggregate-expr-column/#daily-partition-sql-aggregate-expr-column)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_sql_aggregate_expr_column](sql/sql-aggregate-expr-column/#monthly-partition-sql-aggregate-expr-column)|partitioned|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|





## **strings**  
Validates that the data in a string column match the expected format or pattern.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_max_length](strings/string-max-length/#profile-string-max-length)|profiling|Verifies that the length of string in a column does not exceed the maximum accepted length.|
|[daily_string_max_length](strings/string-max-length/#daily-string-max-length)|monitoring|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_max_length](strings/string-max-length/#monthly-string-max-length)|monitoring|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_max_length](strings/string-max-length/#daily-partition-string-max-length)|partitioned|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_max_length](strings/string-max-length/#monthly-partition-string-max-length)|partitioned|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_min_length](strings/string-min-length/#profile-string-min-length)|profiling|Verifies that the length of string in a column does not fall below the minimum accepted length.|
|[daily_string_min_length](strings/string-min-length/#daily-string-min-length)|monitoring|Verifies that the length of string in a column does not fall below the minimum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_min_length](strings/string-min-length/#monthly-string-min-length)|monitoring|Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_min_length](strings/string-min-length/#daily-partition-string-min-length)|partitioned|Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_min_length](strings/string-min-length/#monthly-partition-string-min-length)|partitioned|Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_mean_length](strings/string-mean-length/#profile-string-mean-length)|profiling|Verifies that the length of string in a column does not exceed the mean accepted length.|
|[daily_string_mean_length](strings/string-mean-length/#daily-string-mean-length)|monitoring|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_mean_length](strings/string-mean-length/#monthly-string-mean-length)|monitoring|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_mean_length](strings/string-mean-length/#daily-partition-string-mean-length)|partitioned|Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_mean_length](strings/string-mean-length/#monthly-partition-string-mean-length)|partitioned|Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_length_below_min_length_count](strings/string-length-below-min-length-count/#profile-string-length-below-min-length-count)|profiling|The check counts the number of strings in the column that is below the length defined by the user as a parameter.|
|[daily_string_length_below_min_length_count](strings/string-length-below-min-length-count/#daily-string-length-below-min-length-count)|monitoring|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_below_min_length_count](strings/string-length-below-min-length-count/#monthly-string-length-below-min-length-count)|monitoring|The check counts those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_below_min_length_count](strings/string-length-below-min-length-count/#daily-partition-string-length-below-min-length-count)|partitioned|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_below_min_length_count](strings/string-length-below-min-length-count/#monthly-partition-string-length-below-min-length-count)|partitioned|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_length_below_min_length_percent](strings/string-length-below-min-length-percent/#profile-string-length-below-min-length-percent)|profiling|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter.|
|[daily_string_length_below_min_length_percent](strings/string-length-below-min-length-percent/#daily-string-length-below-min-length-percent)|monitoring|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_below_min_length_percent](strings/string-length-below-min-length-percent/#monthly-string-length-below-min-length-percent)|monitoring|The check counts percentage of those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_below_min_length_percent](strings/string-length-below-min-length-percent/#daily-partition-string-length-below-min-length-percent)|partitioned|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_below_min_length_percent](strings/string-length-below-min-length-percent/#monthly-partition-string-length-below-min-length-percent)|partitioned|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_length_above_max_length_count](strings/string-length-above-max-length-count/#profile-string-length-above-max-length-count)|profiling|The check counts the number of strings in the column that is above the length defined by the user as a parameter.|
|[daily_string_length_above_max_length_count](strings/string-length-above-max-length-count/#daily-string-length-above-max-length-count)|monitoring|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_above_max_length_count](strings/string-length-above-max-length-count/#monthly-string-length-above-max-length-count)|monitoring|The check counts those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_above_max_length_count](strings/string-length-above-max-length-count/#daily-partition-string-length-above-max-length-count)|partitioned|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_above_max_length_count](strings/string-length-above-max-length-count/#monthly-partition-string-length-above-max-length-count)|partitioned|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_length_above_max_length_percent](strings/string-length-above-max-length-percent/#profile-string-length-above-max-length-percent)|profiling|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter.|
|[daily_string_length_above_max_length_percent](strings/string-length-above-max-length-percent/#daily-string-length-above-max-length-percent)|monitoring|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_above_max_length_percent](strings/string-length-above-max-length-percent/#monthly-string-length-above-max-length-percent)|monitoring|The check counts percentage of those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_above_max_length_percent](strings/string-length-above-max-length-percent/#daily-partition-string-length-above-max-length-percent)|partitioned|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_above_max_length_percent](strings/string-length-above-max-length-percent/#monthly-partition-string-length-above-max-length-percent)|partitioned|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_length_in_range_percent](strings/string-length-in-range-percent/#profile-string-length-in-range-percent)|profiling|The check counts the percentage of those strings with length in the range provided by the user in the column. |
|[daily_string_length_in_range_percent](strings/string-length-in-range-percent/#daily-string-length-in-range-percent)|monitoring|The check counts the percentage of those strings with length in the range provided by the user in the column. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_in_range_percent](strings/string-length-in-range-percent/#monthly-string-length-in-range-percent)|monitoring|The check counts percentage of those strings with length in the range provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_in_range_percent](strings/string-length-in-range-percent/#daily-partition-string-length-in-range-percent)|partitioned|The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_in_range_percent](strings/string-length-in-range-percent/#monthly-partition-string-length-in-range-percent)|partitioned|The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_empty_count](strings/string-empty-count/#profile-string-empty-count)|profiling|Verifies that empty strings in a column does not exceed the maximum accepted count.|
|[daily_string_empty_count](strings/string-empty-count/#daily-string-empty-count)|monitoring|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_empty_count](strings/string-empty-count/#monthly-string-empty-count)|monitoring|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_empty_count](strings/string-empty-count/#daily-partition-string-empty-count)|partitioned|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_empty_count](strings/string-empty-count/#monthly-partition-string-empty-count)|partitioned|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_empty_percent](strings/string-empty-percent/#profile-string-empty-percent)|profiling|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.|
|[daily_string_empty_percent](strings/string-empty-percent/#daily-string-empty-percent)|monitoring|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_empty_percent](strings/string-empty-percent/#monthly-string-empty-percent)|monitoring|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_empty_percent](strings/string-empty-percent/#daily-partition-string-empty-percent)|partitioned|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_empty_percent](strings/string-empty-percent/#monthly-partition-string-empty-percent)|partitioned|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_whitespace_count](strings/string-whitespace-count/#profile-string-whitespace-count)|profiling|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count.|
|[daily_string_whitespace_count](strings/string-whitespace-count/#daily-string-whitespace-count)|monitoring|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_whitespace_count](strings/string-whitespace-count/#monthly-string-whitespace-count)|monitoring|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_whitespace_count](strings/string-whitespace-count/#daily-partition-string-whitespace-count)|partitioned|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_whitespace_count](strings/string-whitespace-count/#monthly-partition-string-whitespace-count)|partitioned|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_whitespace_percent](strings/string-whitespace-percent/#profile-string-whitespace-percent)|profiling|Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.|
|[daily_string_whitespace_percent](strings/string-whitespace-percent/#daily-string-whitespace-percent)|monitoring|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_whitespace_percent](strings/string-whitespace-percent/#monthly-string-whitespace-percent)|monitoring|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_whitespace_percent](strings/string-whitespace-percent/#daily-partition-string-whitespace-percent)|partitioned|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_whitespace_percent](strings/string-whitespace-percent/#monthly-partition-string-whitespace-percent)|partitioned|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_surrounded_by_whitespace_count](strings/string-surrounded-by-whitespace-count/#profile-string-surrounded-by-whitespace-count)|profiling|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count.|
|[daily_string_surrounded_by_whitespace_count](strings/string-surrounded-by-whitespace-count/#daily-string-surrounded-by-whitespace-count)|monitoring|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_surrounded_by_whitespace_count](strings/string-surrounded-by-whitespace-count/#monthly-string-surrounded-by-whitespace-count)|monitoring|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_surrounded_by_whitespace_count](strings/string-surrounded-by-whitespace-count/#daily-partition-string-surrounded-by-whitespace-count)|partitioned|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_surrounded_by_whitespace_count](strings/string-surrounded-by-whitespace-count/#monthly-partition-string-surrounded-by-whitespace-count)|partitioned|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_surrounded_by_whitespace_percent](strings/string-surrounded-by-whitespace-percent/#profile-string-surrounded-by-whitespace-percent)|profiling|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage.|
|[daily_string_surrounded_by_whitespace_percent](strings/string-surrounded-by-whitespace-percent/#daily-string-surrounded-by-whitespace-percent)|monitoring|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_surrounded_by_whitespace_percent](strings/string-surrounded-by-whitespace-percent/#monthly-string-surrounded-by-whitespace-percent)|monitoring|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_surrounded_by_whitespace_percent](strings/string-surrounded-by-whitespace-percent/#daily-partition-string-surrounded-by-whitespace-percent)|partitioned|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_surrounded_by_whitespace_percent](strings/string-surrounded-by-whitespace-percent/#monthly-partition-string-surrounded-by-whitespace-percent)|partitioned|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_null_placeholder_count](strings/string-null-placeholder-count/#profile-string-null-placeholder-count)|profiling|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count.|
|[daily_string_null_placeholder_count](strings/string-null-placeholder-count/#daily-string-null-placeholder-count)|monitoring|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_null_placeholder_count](strings/string-null-placeholder-count/#monthly-string-null-placeholder-count)|monitoring|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_null_placeholder_count](strings/string-null-placeholder-count/#daily-partition-string-null-placeholder-count)|partitioned|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_null_placeholder_count](strings/string-null-placeholder-count/#monthly-partition-string-null-placeholder-count)|partitioned|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_null_placeholder_percent](strings/string-null-placeholder-percent/#profile-string-null-placeholder-percent)|profiling|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.|
|[daily_string_null_placeholder_percent](strings/string-null-placeholder-percent/#daily-string-null-placeholder-percent)|monitoring|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_null_placeholder_percent](strings/string-null-placeholder-percent/#monthly-string-null-placeholder-percent)|monitoring|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_null_placeholder_percent](strings/string-null-placeholder-percent/#daily-partition-string-null-placeholder-percent)|partitioned|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_null_placeholder_percent](strings/string-null-placeholder-percent/#monthly-partition-string-null-placeholder-percent)|partitioned|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_boolean_placeholder_percent](strings/string-boolean-placeholder-percent/#profile-string-boolean-placeholder-percent)|profiling|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage.|
|[daily_string_boolean_placeholder_percent](strings/string-boolean-placeholder-percent/#daily-string-boolean-placeholder-percent)|monitoring|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_boolean_placeholder_percent](strings/string-boolean-placeholder-percent/#monthly-string-boolean-placeholder-percent)|monitoring|Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_boolean_placeholder_percent](strings/string-boolean-placeholder-percent/#daily-partition-string-boolean-placeholder-percent)|partitioned|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_boolean_placeholder_percent](strings/string-boolean-placeholder-percent/#monthly-partition-string-boolean-placeholder-percent)|partitioned|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_parsable_to_integer_percent](strings/string-parsable-to-integer-percent/#profile-string-parsable-to-integer-percent)|profiling|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage.|
|[daily_string_parsable_to_integer_percent](strings/string-parsable-to-integer-percent/#daily-string-parsable-to-integer-percent)|monitoring|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_parsable_to_integer_percent](strings/string-parsable-to-integer-percent/#monthly-string-parsable-to-integer-percent)|monitoring|Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_parsable_to_integer_percent](strings/string-parsable-to-integer-percent/#daily-partition-string-parsable-to-integer-percent)|partitioned|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_parsable_to_integer_percent](strings/string-parsable-to-integer-percent/#monthly-partition-string-parsable-to-integer-percent)|partitioned|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_parsable_to_float_percent](strings/string-parsable-to-float-percent/#profile-string-parsable-to-float-percent)|profiling|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage.|
|[daily_string_parsable_to_float_percent](strings/string-parsable-to-float-percent/#daily-string-parsable-to-float-percent)|monitoring|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_parsable_to_float_percent](strings/string-parsable-to-float-percent/#monthly-string-parsable-to-float-percent)|monitoring|Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_parsable_to_float_percent](strings/string-parsable-to-float-percent/#daily-partition-string-parsable-to-float-percent)|partitioned|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_parsable_to_float_percent](strings/string-parsable-to-float-percent/#monthly-partition-string-parsable-to-float-percent)|partitioned|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_expected_strings_in_use_count](strings/expected-strings-in-use-count/#profile-expected-strings-in-use-count)|profiling|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|
|[daily_expected_strings_in_use_count](strings/expected-strings-in-use-count/#daily-expected-strings-in-use-count)|monitoring|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_expected_strings_in_use_count](strings/expected-strings-in-use-count/#monthly-expected-strings-in-use-count)|monitoring|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_expected_strings_in_use_count](strings/expected-strings-in-use-count/#daily-partition-expected-strings-in-use-count)|partitioned|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_expected_strings_in_use_count](strings/expected-strings-in-use-count/#monthly-partition-expected-strings-in-use-count)|partitioned|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_value_in_set_percent](strings/string-value-in-set-percent/#profile-string-value-in-set-percent)|profiling|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|
|[daily_string_value_in_set_percent](strings/string-value-in-set-percent/#daily-string-value-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_value_in_set_percent](strings/string-value-in-set-percent/#monthly-string-value-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_value_in_set_percent](strings/string-value-in-set-percent/#daily-partition-string-value-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_value_in_set_percent](strings/string-value-in-set-percent/#monthly-partition-string-value-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_valid_dates_percent](strings/string-valid-dates-percent/#profile-string-valid-dates-percent)|profiling|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage.|
|[daily_string_valid_dates_percent](strings/string-valid-dates-percent/#daily-string-valid-dates-percent)|monitoring|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_valid_dates_percent](strings/string-valid-dates-percent/#monthly-string-valid-dates-percent)|monitoring|Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_valid_dates_percent](strings/string-valid-dates-percent/#daily-partition-string-valid-dates-percent)|partitioned|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_valid_dates_percent](strings/string-valid-dates-percent/#monthly-partition-string-valid-dates-percent)|partitioned|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_valid_country_code_percent](strings/string-valid-country-code-percent/#profile-string-valid-country-code-percent)|profiling|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage.|
|[daily_string_valid_country_code_percent](strings/string-valid-country-code-percent/#daily-string-valid-country-code-percent)|monitoring|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_valid_country_code_percent](strings/string-valid-country-code-percent/#monthly-string-valid-country-code-percent)|monitoring|Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_valid_country_code_percent](strings/string-valid-country-code-percent/#daily-partition-string-valid-country-code-percent)|partitioned|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_valid_country_code_percent](strings/string-valid-country-code-percent/#monthly-partition-string-valid-country-code-percent)|partitioned|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_valid_currency_code_percent](strings/string-valid-currency-code-percent/#profile-string-valid-currency-code-percent)|profiling|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage.|
|[daily_string_valid_currency_code_percent](strings/string-valid-currency-code-percent/#daily-string-valid-currency-code-percent)|monitoring|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_valid_currency_code_percent](strings/string-valid-currency-code-percent/#monthly-string-valid-currency-code-percent)|monitoring|Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_valid_currency_code_percent](strings/string-valid-currency-code-percent/#daily-partition-string-valid-currency-code-percent)|partitioned|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_valid_currency_code_percent](strings/string-valid-currency-code-percent/#monthly-partition-string-valid-currency-code-percent)|partitioned|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_invalid_email_count](strings/string-invalid-email-count/#profile-string-invalid-email-count)|profiling|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_email_count](strings/string-invalid-email-count/#daily-string-invalid-email-count)|monitoring|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_invalid_email_count](strings/string-invalid-email-count/#monthly-string-invalid-email-count)|monitoring|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_email_count](strings/string-invalid-email-count/#daily-partition-string-invalid-email-count)|partitioned|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_email_count](strings/string-invalid-email-count/#monthly-partition-string-invalid-email-count)|partitioned|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_invalid_uuid_count](strings/string-invalid-uuid-count/#profile-string-invalid-uuid-count)|profiling|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_uuid_count](strings/string-invalid-uuid-count/#daily-string-invalid-uuid-count)|monitoring|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_invalid_uuid_count](strings/string-invalid-uuid-count/#monthly-string-invalid-uuid-count)|monitoring|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_uuid_count](strings/string-invalid-uuid-count/#daily-partition-string-invalid-uuid-count)|partitioned|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_uuid_count](strings/string-invalid-uuid-count/#monthly-partition-string-invalid-uuid-count)|partitioned|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_valid_uuid_percent](strings/string-valid-uuid-percent/#profile-string-valid-uuid-percent)|profiling|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage.|
|[daily_string_valid_uuid_percent](strings/string-valid-uuid-percent/#daily-string-valid-uuid-percent)|monitoring|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_valid_uuid_percent](strings/string-valid-uuid-percent/#monthly-string-valid-uuid-percent)|monitoring|Verifies that the percentage of valid UUID in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_uuid_percent](strings/string-valid-uuid-percent/#daily-partition-valid-uuid-percent)|partitioned|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_uuid_percent](strings/string-valid-uuid-percent/#monthly-partition-valid-uuid-percent)|partitioned|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_invalid_ip4_address_count](strings/string-invalid-ip4-address-count/#profile-string-invalid-ip4-address-count)|profiling|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_ip4_address_count](strings/string-invalid-ip4-address-count/#daily-string-invalid-ip4-address-count)|monitoring|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_invalid_ip4_address_count](strings/string-invalid-ip4-address-count/#monthly-string-invalid-ip4-address-count)|monitoring|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_ip4_address_count](strings/string-invalid-ip4-address-count/#daily-partition-string-invalid-ip4-address-count)|partitioned|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_ip4_address_count](strings/string-invalid-ip4-address-count/#monthly-partition-string-invalid-ip4-address-count)|partitioned|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_invalid_ip6_address_count](strings/string-invalid-ip6-address-count/#profile-string-invalid-ip6-address-count)|profiling|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_ip6_address_count](strings/string-invalid-ip6-address-count/#daily-string-invalid-ip6-address-count)|monitoring|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_invalid_ip6_address_count](strings/string-invalid-ip6-address-count/#monthly-string-invalid-ip6-address-count)|monitoring|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_ip6_address_count](strings/string-invalid-ip6-address-count/#daily-partition-string-invalid-ip6-address-count)|partitioned|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_ip6_address_count](strings/string-invalid-ip6-address-count/#monthly-partition-string-invalid-ip6-address-count)|partitioned|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_not_match_regex_count](strings/string-not-match-regex-count/#profile-string-not-match-regex-count)|profiling|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count.|
|[daily_string_not_match_regex_count](strings/string-not-match-regex-count/#daily-string-not-match-regex-count)|monitoring|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_not_match_regex_count](strings/string-not-match-regex-count/#monthly-string-not-match-regex-count)|monitoring|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_not_match_regex_count](strings/string-not-match-regex-count/#daily-partition-string-not-match-regex-count)|partitioned|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_not_match_regex_count](strings/string-not-match-regex-count/#monthly-partition-string-not-match-regex-count)|partitioned|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_match_regex_percent](strings/string-match-regex-percent/#profile-string-match-regex-percent)|profiling|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage.|
|[daily_string_match_regex_percent](strings/string-match-regex-percent/#daily-string-match-regex-percent)|monitoring|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_match_regex_percent](strings/string-match-regex-percent/#monthly-string-match-regex-percent)|monitoring|Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_match_regex_percent](strings/string-match-regex-percent/#daily-partition-string-match-regex-percent)|partitioned|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_match_regex_percent](strings/string-match-regex-percent/#monthly-partition-string-match-regex-percent)|partitioned|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_not_match_date_regex_count](strings/string-not-match-date-regex-count/#profile-string-not-match-date-regex-count)|profiling|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count.|
|[daily_string_not_match_date_regex_count](strings/string-not-match-date-regex-count/#daily-string-not-match-date-regex-count)|monitoring|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_not_match_date_regex_count](strings/string-not-match-date-regex-count/#monthly-string-not-match-date-regex-count)|monitoring|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_not_match_date_regex_count](strings/string-not-match-date-regex-count/#daily-partition-string-not-match-date-regex-count)|partitioned|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_not_match_date_regex_count](strings/string-not-match-date-regex-count/#monthly-partition-string-not-match-date-regex-count)|partitioned|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_match_date_regex_percent](strings/string-match-date-regex-percent/#profile-string-match-date-regex-percent)|profiling|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage.|
|[daily_string_match_date_regex_percent](strings/string-match-date-regex-percent/#daily-string-match-date-regex-percent)|monitoring|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_match_date_regex_percent](strings/string-match-date-regex-percent/#monthly-string-match-date-regex-percent)|monitoring|Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_match_date_regex_percent](strings/string-match-date-regex-percent/#daily-partition-string-match-date-regex-percent)|partitioned|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_match_date_regex_percent](strings/string-match-date-regex-percent/#monthly-partition-string-match-date-regex-percent)|partitioned|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_match_name_regex_percent](strings/string-match-name-regex-percent/#profile-string-match-name-regex-percent)|profiling|Verifies that the percentage of strings matching the name regex in a column does not fall below the minimum accepted percentage.|
|[daily_string_match_name_regex_percent](strings/string-match-name-regex-percent/#daily-string-match-name-regex-percent)|monitoring|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_match_name_regex_percent](strings/string-match-name-regex-percent/#monthly-string-match-name-regex-percent)|monitoring|Verifies that the percentage of strings matching the name regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_match_name_regex_percent](strings/string-match-name-regex-percent/#daily-partition-string-match-name-regex-percent)|partitioned|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_match_name_regex_percent](strings/string-match-name-regex-percent/#monthly-partition-string-match-name-regex-percent)|partitioned|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_expected_strings_in_top_values_count](strings/expected-strings-in-top-values-count/#profile-expected-strings-in-top-values-count)|profiling|Verifies that the top X most popular column values contain all values from a list of expected values.|
|[daily_expected_strings_in_top_values_count](strings/expected-strings-in-top-values-count/#daily-expected-strings-in-top-values-count)|monitoring|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_expected_strings_in_top_values_count](strings/expected-strings-in-top-values-count/#monthly-expected-strings-in-top-values-count)|monitoring|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_expected_strings_in_top_values_count](strings/expected-strings-in-top-values-count/#daily-partition-expected-strings-in-top-values-count)|partitioned|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_expected_strings_in_top_values_count](strings/expected-strings-in-top-values-count/#monthly-partition-expected-strings-in-top-values-count)|partitioned|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each monthly partition.|





## **uniqueness**  
Counts the number or percent of duplicate or unique values in a column.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_distinct_count](uniqueness/distinct-count/#profile-distinct-count)|profiling|Verifies that the number of distinct values in a column does not fall below the minimum accepted count.|
|[daily_distinct_count](uniqueness/distinct-count/#daily-distinct-count)|monitoring|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_distinct_count](uniqueness/distinct-count/#monthly-distinct-count)|monitoring|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_distinct_count](uniqueness/distinct-count/#daily-partition-distinct-count)|partitioned|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_distinct_count](uniqueness/distinct-count/#monthly-partition-distinct-count)|partitioned|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_distinct_percent](uniqueness/distinct-percent/#profile-distinct-percent)|profiling|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent.|
|[daily_distinct_percent](uniqueness/distinct-percent/#daily-distinct-percent)|monitoring|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_distinct_percent](uniqueness/distinct-percent/#monthly-distinct-percent)|monitoring|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_distinct_percent](uniqueness/distinct-percent/#daily-partition-distinct-percent)|partitioned|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_distinct_percent](uniqueness/distinct-percent/#monthly-partition-distinct-percent)|partitioned|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_duplicate_count](uniqueness/duplicate-count/#profile-duplicate-count)|profiling|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.|
|[daily_duplicate_count](uniqueness/duplicate-count/#daily-duplicate-count)|monitoring|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_duplicate_count](uniqueness/duplicate-count/#monthly-duplicate-count)|monitoring|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_duplicate_count](uniqueness/duplicate-count/#daily-partition-duplicate-count)|partitioned|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_duplicate_count](uniqueness/duplicate-count/#monthly-partition-duplicate-count)|partitioned|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_duplicate_percent](uniqueness/duplicate-percent/#profile-duplicate-percent)|profiling|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.|
|[daily_duplicate_percent](uniqueness/duplicate-percent/#daily-duplicate-percent)|monitoring|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_duplicate_percent](uniqueness/duplicate-percent/#monthly-duplicate-percent)|monitoring|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_duplicate_percent](uniqueness/duplicate-percent/#daily-partition-duplicate-percent)|partitioned|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_duplicate_percent](uniqueness/duplicate-percent/#monthly-partition-duplicate-percent)|partitioned|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_anomaly_differencing_distinct_count_30_days](uniqueness/anomaly-differencing-distinct-count-30-days/#profile-anomaly-differencing-distinct-count-30-days)|profiling|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[daily_anomaly_differencing_distinct_count_30_days](uniqueness/anomaly-differencing-distinct-count-30-days/#daily-anomaly-differencing-distinct-count-30-days)|monitoring|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[monthly_anomaly_differencing_distinct_count_30_days](uniqueness/anomaly-differencing-distinct-count-30-days/#monthly-anomaly-differencing-distinct-count-30-days)|monitoring|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_anomaly_differencing_distinct_count](uniqueness/anomaly-differencing-distinct-count/#profile-anomaly-differencing-distinct-count)|profiling|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[daily_anomaly_differencing_distinct_count](uniqueness/anomaly-differencing-distinct-count/#daily-anomaly-differencing-distinct-count)|monitoring|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[monthly_anomaly_differencing_distinct_count](uniqueness/anomaly-differencing-distinct-count/#monthly-anomaly-differencing-distinct-count)|monitoring|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_anomaly_stationary_distinct_percent_30_days](uniqueness/anomaly-stationary-distinct-percent-30-days/#profile-anomaly-stationary-distinct-percent-30-days)|profiling|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[daily_anomaly_stationary_distinct_percent_30_days](uniqueness/anomaly-stationary-distinct-percent-30-days/#daily-anomaly-stationary-distinct-percent-30-days)|monitoring|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[monthly_anomaly_stationary_distinct_percent_30_days](uniqueness/anomaly-stationary-distinct-percent-30-days/#monthly-anomaly-stationary-distinct-percent-30-days)|monitoring|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[daily_partition_anomaly_stationary_distinct_percent_30_days](uniqueness/anomaly-stationary-distinct-percent-30-days/#daily-partition-anomaly-stationary-distinct-percent-30-days)|partitioned|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[monthly_partition_anomaly_stationary_distinct_percent_30_days](uniqueness/anomaly-stationary-distinct-percent-30-days/#monthly-partition-anomaly-stationary-distinct-percent-30-days)|partitioned|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_anomaly_stationary_distinct_percent](uniqueness/anomaly-stationary-distinct-percent/#profile-anomaly-stationary-distinct-percent)|profiling|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[daily_anomaly_stationary_distinct_percent](uniqueness/anomaly-stationary-distinct-percent/#daily-anomaly-stationary-distinct-percent)|monitoring|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[monthly_anomaly_stationary_distinct_percent](uniqueness/anomaly-stationary-distinct-percent/#monthly-anomaly-stationary-distinct-percent)|monitoring|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[daily_partition_anomaly_stationary_distinct_percent](uniqueness/anomaly-stationary-distinct-percent/#daily-partition-anomaly-stationary-distinct-percent)|partitioned|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[monthly_partition_anomaly_stationary_distinct_percent](uniqueness/anomaly-stationary-distinct-percent/#monthly-partition-anomaly-stationary-distinct-percent)|partitioned|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_count](uniqueness/change-distinct-count/#profile-change-distinct-count)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|
|[daily_change_distinct_count](uniqueness/change-distinct-count/#daily-change-distinct-count)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|
|[monthly_change_distinct_count](uniqueness/change-distinct-count/#monthly-change-distinct-count)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|
|[daily_partition_change_distinct_count](uniqueness/change-distinct-count/#daily-partition-change-distinct-count)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|
|[monthly_partition_change_distinct_count](uniqueness/change-distinct-count/#monthly-partition-change-distinct-count)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_count_since_7_days](uniqueness/change-distinct-count-since-7-days/#profile-change-distinct-count-since-7-days)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|
|[daily_change_distinct_count_since_7_days](uniqueness/change-distinct-count-since-7-days/#daily-change-distinct-count-since-7-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|
|[monthly_change_distinct_count_since_7_days](uniqueness/change-distinct-count-since-7-days/#monthly-change-distinct-count-since-7-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|
|[daily_partition_change_distinct_count_since_7_days](uniqueness/change-distinct-count-since-7-days/#daily-partition-change-distinct-count-since-7-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|
|[monthly_partition_change_distinct_count_since_7_days](uniqueness/change-distinct-count-since-7-days/#monthly-partition-change-distinct-count-since-7-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_count_since_30_days](uniqueness/change-distinct-count-since-30-days/#profile-change-distinct-count-since-30-days)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|
|[daily_change_distinct_count_since_30_days](uniqueness/change-distinct-count-since-30-days/#daily-change-distinct-count-since-30-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|
|[monthly_change_distinct_count_since_30_days](uniqueness/change-distinct-count-since-30-days/#monthly-change-distinct-count-since-30-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|
|[daily_partition_change_distinct_count_since_30_days](uniqueness/change-distinct-count-since-30-days/#daily-partition-change-distinct-count-since-30-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|
|[monthly_partition_change_distinct_count_since_30_days](uniqueness/change-distinct-count-since-30-days/#monthly-partition-change-distinct-count-since-30-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_count_since_yesterday](uniqueness/change-distinct-count-since-yesterday/#profile-change-distinct-count-since-yesterday)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[daily_change_distinct_count_since_yesterday](uniqueness/change-distinct-count-since-yesterday/#daily-change-distinct-count-since-yesterday)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[monthly_change_distinct_count_since_yesterday](uniqueness/change-distinct-count-since-yesterday/#monthly-change-distinct-count-since-yesterday)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[daily_partition_change_distinct_count_since_yesterday](uniqueness/change-distinct-count-since-yesterday/#daily-partition-change-distinct-count-since-yesterday)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[monthly_partition_change_distinct_count_since_yesterday](uniqueness/change-distinct-count-since-yesterday/#monthly-partition-change-distinct-count-since-yesterday)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_percent](uniqueness/change-distinct-percent/#profile-change-distinct-percent)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|
|[daily_change_distinct_percent](uniqueness/change-distinct-percent/#daily-change-distinct-percent)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|
|[monthly_change_distinct_percent](uniqueness/change-distinct-percent/#monthly-change-distinct-percent)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|
|[daily_partition_change_distinct_percent](uniqueness/change-distinct-percent/#daily-partition-change-distinct-percent)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|
|[monthly_partition_change_distinct_percent](uniqueness/change-distinct-percent/#monthly-partition-change-distinct-percent)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_percent_since_7_days](uniqueness/change-distinct-percent-since-7-days/#profile-change-distinct-percent-since-7-days)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|
|[daily_change_distinct_percent_since_7_days](uniqueness/change-distinct-percent-since-7-days/#daily-change-distinct-percent-since-7-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|
|[monthly_change_distinct_percent_since_7_days](uniqueness/change-distinct-percent-since-7-days/#monthly-change-distinct-percent-since-7-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|
|[daily_partition_change_distinct_percent_since_7_days](uniqueness/change-distinct-percent-since-7-days/#daily-partition-change-distinct-percent-since-7-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|
|[monthly_partition_change_distinct_percent_since_7_days](uniqueness/change-distinct-percent-since-7-days/#monthly-partition-change-distinct-percent-since-7-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_percent_since_30_days](uniqueness/change-distinct-percent-since-30-days/#profile-change-distinct-percent-since-30-days)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|
|[daily_change_distinct_percent_since_30_days](uniqueness/change-distinct-percent-since-30-days/#daily-change-distinct-percent-since-30-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|
|[monthly_change_distinct_percent_since_30_days](uniqueness/change-distinct-percent-since-30-days/#monthly-change-distinct-percent-since-30-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|
|[daily_partition_change_distinct_percent_since_30_days](uniqueness/change-distinct-percent-since-30-days/#daily-partition-change-distinct-percent-since-30-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|
|[monthly_partition_change_distinct_percent_since_30_days](uniqueness/change-distinct-percent-since-30-days/#monthly-partition-change-distinct-percent-since-30-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_percent_since_yesterday](uniqueness/change-distinct-percent-since-yesterday/#profile-change-distinct-percent-since-yesterday)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[daily_change_distinct_percent_since_yesterday](uniqueness/change-distinct-percent-since-yesterday/#daily-change-distinct-percent-since-yesterday)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[monthly_change_distinct_percent_since_yesterday](uniqueness/change-distinct-percent-since-yesterday/#monthly-change-distinct-percent-since-yesterday)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[daily_partition_change_distinct_percent_since_yesterday](uniqueness/change-distinct-percent-since-yesterday/#daily-partition-change-distinct-percent-since-yesterday)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[monthly_partition_change_distinct_percent_since_yesterday](uniqueness/change-distinct-percent-since-yesterday/#monthly-partition-change-distinct-percent-since-yesterday)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_anomaly_stationary_distinct_count_30_days](uniqueness/anomaly-stationary-distinct-count-30-days/#daily-partition-anomaly-stationary-distinct-count-30-days)|partitioned|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[monthly_partition_anomaly_stationary_distinct_count_30_days](uniqueness/anomaly-stationary-distinct-count-30-days/#monthly-partition-anomaly-stationary-distinct-count-30-days)|partitioned|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_anomaly_stationary_distinct_count](uniqueness/anomaly-stationary-distinct-count/#daily-partition-anomaly-stationary-distinct-count)|partitioned|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[monthly_partition_anomaly_stationary_distinct_count](uniqueness/anomaly-stationary-distinct-count/#monthly-partition-anomaly-stationary-distinct-count)|partitioned|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|





