# column level comparisons data quality checks

This is a list of comparisons column data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **comparisons**
Compares the columns in a table to another column in another table that is in a different data source.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sum_match`</span>](./sum-match.md#profile-sum-match)|profiling|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds.|**|
|[<span class="no-wrap-code">`daily_sum_match`</span>](./sum-match.md#daily-sum-match)|monitoring|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_sum_match`</span>](./sum-match.md#monthly-sum-match)|monitoring|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_sum_match`</span>](./sum-match.md#daily-partition-sum-match)|partitioned|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|**|
|[<span class="no-wrap-code">`monthly_partition_sum_match`</span>](./sum-match.md#monthly-partition-sum-match)|partitioned|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_min_match`</span>](./min-match.md#profile-min-match)|profiling|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds.|**|
|[<span class="no-wrap-code">`daily_min_match`</span>](./min-match.md#daily-min-match)|monitoring|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_min_match`</span>](./min-match.md#monthly-min-match)|monitoring|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_min_match`</span>](./min-match.md#daily-partition-min-match)|partitioned|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|**|
|[<span class="no-wrap-code">`monthly_partition_min_match`</span>](./min-match.md#monthly-partition-min-match)|partitioned|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_max_match`</span>](./max-match.md#profile-max-match)|profiling|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds.|**|
|[<span class="no-wrap-code">`daily_max_match`</span>](./max-match.md#daily-max-match)|monitoring|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_max_match`</span>](./max-match.md#monthly-max-match)|monitoring|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_max_match`</span>](./max-match.md#daily-partition-max-match)|partitioned|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|**|
|[<span class="no-wrap-code">`monthly_partition_max_match`</span>](./max-match.md#monthly-partition-max-match)|partitioned|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_mean_match`</span>](./mean-match.md#profile-mean-match)|profiling|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds.|**|
|[<span class="no-wrap-code">`daily_mean_match`</span>](./mean-match.md#daily-mean-match)|monitoring|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_mean_match`</span>](./mean-match.md#monthly-mean-match)|monitoring|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_mean_match`</span>](./mean-match.md#daily-partition-mean-match)|partitioned|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|**|
|[<span class="no-wrap-code">`monthly_partition_mean_match`</span>](./mean-match.md#monthly-partition-mean-match)|partitioned|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_not_null_count_match`</span>](./not-null-count-match.md#profile-not-null-count-match)|profiling|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds.|**|
|[<span class="no-wrap-code">`daily_not_null_count_match`</span>](./not-null-count-match.md#daily-not-null-count-match)|monitoring|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_not_null_count_match`</span>](./not-null-count-match.md#monthly-not-null-count-match)|monitoring|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_not_null_count_match`</span>](./not-null-count-match.md#daily-partition-not-null-count-match)|partitioned|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|**|
|[<span class="no-wrap-code">`monthly_partition_not_null_count_match`</span>](./not-null-count-match.md#monthly-partition-not-null-count-match)|partitioned|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_null_count_match`</span>](./null-count-match.md#profile-null-count-match)|profiling|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds.|**|
|[<span class="no-wrap-code">`daily_null_count_match`</span>](./null-count-match.md#daily-null-count-match)|monitoring|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_null_count_match`</span>](./null-count-match.md#monthly-null-count-match)|monitoring|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_null_count_match`</span>](./null-count-match.md#daily-partition-null-count-match)|partitioned|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|**|
|[<span class="no-wrap-code">`monthly_partition_null_count_match`</span>](./null-count-match.md#monthly-partition-null-count-match)|partitioned|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|**|







