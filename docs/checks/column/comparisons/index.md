# Checks/column/comparisons

**This is a list of comparisons column data quality checks supported by DQOps and a brief description of what they do.**





## **comparisons**  


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_match](sum-match/#profile-sum-match)|profiling|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_sum_match](sum-match/#daily-sum-match)|monitoring|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_sum_match](sum-match/#monthly-sum-match)|monitoring|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_sum_match](sum-match/#daily-partition-sum-match)|partitioned|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_sum_match](sum-match/#monthly-partition-sum-match)|partitioned|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_min_match](min-match/#profile-min-match)|profiling|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_min_match](min-match/#daily-min-match)|monitoring|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_min_match](min-match/#monthly-min-match)|monitoring|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_min_match](min-match/#daily-partition-min-match)|partitioned|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_min_match](min-match/#monthly-partition-min-match)|partitioned|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_max_match](max-match/#profile-max-match)|profiling|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_max_match](max-match/#daily-max-match)|monitoring|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_max_match](max-match/#monthly-max-match)|monitoring|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_max_match](max-match/#daily-partition-max-match)|partitioned|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_max_match](max-match/#monthly-partition-max-match)|partitioned|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_match](mean-match/#profile-mean-match)|profiling|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_mean_match](mean-match/#daily-mean-match)|monitoring|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_mean_match](mean-match/#monthly-mean-match)|monitoring|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_mean_match](mean-match/#daily-partition-mean-match)|partitioned|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_mean_match](mean-match/#monthly-partition-mean-match)|partitioned|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_not_null_count_match](not-null-count-match/#profile-not-null-count-match)|profiling|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_not_null_count_match](not-null-count-match/#daily-not-null-count-match)|monitoring|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_not_null_count_match](not-null-count-match/#monthly-not-null-count-match)|monitoring|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_not_null_count_match](not-null-count-match/#daily-partition-not-null-count-match)|partitioned|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_not_null_count_match](not-null-count-match/#monthly-partition-not-null-count-match)|partitioned|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_null_count_match](null-count-match/#profile-null-count-match)|profiling|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds.|
|[daily_null_count_match](null-count-match/#daily-null-count-match)|monitoring|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_null_count_match](null-count-match/#monthly-null-count-match)|monitoring|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_null_count_match](null-count-match/#daily-partition-null-count-match)|partitioned|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|
|[monthly_partition_null_count_match](null-count-match/#monthly-partition-null-count-match)|partitioned|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|





