---
title: column level comparisons data quality checks
---
# column level comparisons data quality checks

This is a list of comparisons column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level comparisons checks
Compares the columns in a table to another column in another table that is in a different data source.

### [sum match](./sum-match.md)
A column-level check that ensures that compares the sum of the values in the tested column to the sum of values in a reference column from the reference table.
 Compares the sum of values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sum_match`</span>](./sum-match.md#profile-sum-match)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_sum_match`</span>](./sum-match.md#daily-sum-match)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_sum_match`</span>](./sum-match.md#monthly-sum-match)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_sum_match`</span>](./sum-match.md#daily-partition-sum-match)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_sum_match`</span>](./sum-match.md#monthly-partition-sum-match)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|:material-check-bold:|



### [min match](./min-match.md)
A column-level check that ensures that compares the minimum value in the tested column to the minimum value in a reference column from the reference table.
 Compares the minimum values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_min_match`</span>](./min-match.md#profile-min-match)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_min_match`</span>](./min-match.md#daily-min-match)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_min_match`</span>](./min-match.md#monthly-min-match)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_min_match`</span>](./min-match.md#daily-partition-min-match)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_min_match`</span>](./min-match.md#monthly-partition-min-match)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|:material-check-bold:|



### [max match](./max-match.md)
A column-level check that ensures that compares the maximum value in the tested column to maximum value in a reference column from the reference table.
 Compares the maximum values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_max_match`</span>](./max-match.md#profile-max-match)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_max_match`</span>](./max-match.md#daily-max-match)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_max_match`</span>](./max-match.md#monthly-max-match)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_max_match`</span>](./max-match.md#daily-partition-max-match)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_max_match`</span>](./max-match.md#monthly-partition-max-match)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|:material-check-bold:|



### [mean match](./mean-match.md)
A column-level check that ensures that compares the mean (average) of the values in the tested column to the mean (average) of values in a reference column from the reference table.
 Compares the mean (average) value for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_mean_match`</span>](./mean-match.md#profile-mean-match)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_mean_match`</span>](./mean-match.md#daily-mean-match)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_mean_match`</span>](./mean-match.md#monthly-mean-match)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_mean_match`</span>](./mean-match.md#daily-partition-mean-match)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_mean_match`</span>](./mean-match.md#monthly-partition-mean-match)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|:material-check-bold:|



### [not null count match](./not-null-count-match.md)
A column-level check that ensures that compares the count of not null values in the tested column to the count of not null values in a reference column from the reference table.
 Compares the count of not null values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_not_null_count_match`</span>](./not-null-count-match.md#profile-not-null-count-match)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_not_null_count_match`</span>](./not-null-count-match.md#daily-not-null-count-match)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_not_null_count_match`</span>](./not-null-count-match.md#monthly-not-null-count-match)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_not_null_count_match`</span>](./not-null-count-match.md#daily-partition-not-null-count-match)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_not_null_count_match`</span>](./not-null-count-match.md#monthly-partition-not-null-count-match)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|:material-check-bold:|



### [null count match](./null-count-match.md)
A column-level check that ensures that compares the count of null values in the tested column to the count of null values in a reference column from the reference table.
 Compares the count of null values for each group of data. The data is grouped using a GROUP BY clause and groups are matched between the tested (parent) table and the reference table (the source of truth).


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_null_count_match`</span>](./null-count-match.md#profile-null-count-match)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_null_count_match`</span>](./null-count-match.md#daily-null-count-match)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_null_count_match`</span>](./null-count-match.md#monthly-null-count-match)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_null_count_match`</span>](./null-count-match.md#daily-partition-null-count-match)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_null_count_match`</span>](./null-count-match.md#monthly-partition-null-count-match)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|:material-check-bold:|







