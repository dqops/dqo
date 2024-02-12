# column level anomaly data quality checks

This is a list of anomaly column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level anomaly checks
Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.

### [sum anomaly](./sum-anomaly.md)
A column-level check that ensures that the sum in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sum_anomaly`</span>](./sum-anomaly.md#profile-sum-anomaly)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the sum in a column changes in a rate within a percentile boundary during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_sum_anomaly`</span>](./sum-anomaly.md#daily-sum-anomaly)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the sum in a column changes in a rate within a percentile boundary during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_sum_anomaly`</span>](./sum-anomaly.md#daily-partition-sum-anomaly)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the sum in a column is within a percentile from measurements made during the last 90 days. Calculates the sum of each daily partition and detect anomalies between daily partitions.|:material-check-bold:|



### [mean anomaly](./mean-anomaly.md)
A column-level check that ensures that the mean value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_mean_anomaly`</span>](./mean-anomaly.md#profile-mean-anomaly)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the mean value in a column changes in a rate within a percentile boundary during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_mean_anomaly`</span>](./mean-anomaly.md#daily-mean-anomaly)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the mean value in a column changes in a rate within a percentile boundary during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_mean_anomaly`</span>](./mean-anomaly.md#daily-partition-mean-anomaly)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the mean value in a column is within a percentile from measurements made during the last 90 days. Calculates the mean (average) of each daily partition and detect anomalies between daily partitions.|:material-check-bold:|



### [median anomaly](./median-anomaly.md)
A column-level check that ensures that the median in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_median_anomaly`</span>](./median-anomaly.md#profile-median-anomaly)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the median in a column changes in a rate within a percentile boundary during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_median_anomaly`</span>](./median-anomaly.md#daily-median-anomaly)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the median in a column changes in a rate within a percentile boundary during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_median_anomaly`</span>](./median-anomaly.md#daily-partition-median-anomaly)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the median in a column is within a percentile from measurements made during the last 90 days. Calculates the median of each daily partition and detect anomalies between daily partitions.|:material-check-bold:|



### [min anomaly](./min-anomaly.md)
A column-level check that detects big changes of the minimum value in a numeric column, detecting new data outliers.
 If the values in the column are slightly changing day-to-day, DQOps detects new minimum values that changed much more than the typical change for the last 90 days.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_min_anomaly`</span>](./min-anomaly.md#profile-min-anomaly)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects new outliers, which are new minimum values, much below the last known minimum value. If the minimum value is constantly changing, detects outliers as the biggest change of the minimum value during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_min_anomaly`</span>](./min-anomaly.md#daily-min-anomaly)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects new outliers, which are new minimum values, much below the last known minimum value. If the minimum value is constantly changing, detects outliers as the biggest change of the minimum value during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_min_anomaly`</span>](./min-anomaly.md#daily-partition-min-anomaly)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects new outliers, which are new minimum values, much below the last known minimum value. If the minimum value is constantly changing, detects outliers as the biggest change of the minimum value during the last 90 days. Finds the minimum value of each daily partition and detect anomalies between daily partitions.|:material-check-bold:|



### [max anomaly](./max-anomaly.md)
A column-level check that detects big changes of the maximum value in a numeric column, detecting new data outliers.
 If the values in the column are slightly changing day-to-day, DQOps detects new maximum values that changed much more than the typical change for the last 90 days.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_max_anomaly`</span>](./max-anomaly.md#profile-max-anomaly)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects new outliers, which are new maximum values, much above the last known maximum value. If the maximum value is constantly changing, detects outliers as the biggest change of the maximum value during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_max_anomaly`</span>](./max-anomaly.md#daily-max-anomaly)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects new outliers, which are new maximum values, much above the last known maximum value. If the maximum value is constantly changing, detects outliers as the biggest change of the maximum value during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_max_anomaly`</span>](./max-anomaly.md#daily-partition-max-anomaly)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects new outliers, which are new maximum values, much above the last known maximum value. If the maximum value is constantly changing, detects outliers as the biggest change of the maximum value during the last 90 days. Finds the maximum value of each daily partition and detect anomalies between daily partitions.|:material-check-bold:|



### [mean change](./mean-change.md)
A column-level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_mean_change`</span>](./mean-change.md#profile-mean-change)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_mean_change`</span>](./mean-change.md#daily-mean-change)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_partition_mean_change`</span>](./mean-change.md#daily-partition-mean-change)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the mean value in a column changed in a fixed rate since last readout.| |



### [mean change 1 day](./mean-change-1-day.md)
A column-level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout from yesterday.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_mean_change_1_day`</span>](./mean-change-1-day.md#profile-mean-change-1-day)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout from yesterday.| |
|[<span class="no-wrap-code">`daily_mean_change_1_day`</span>](./mean-change-1-day.md#daily-mean-change-1-day)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.| |
|[<span class="no-wrap-code">`daily_partition_mean_change_1_day`</span>](./mean-change-1-day.md#daily-partition-mean-change-1-day)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout from yesterday.| |



### [mean change 7 days](./mean-change-7-days.md)
A column-level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout from last week.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_mean_change_7_days`</span>](./mean-change-7-days.md#profile-mean-change-7-days)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last week.| |
|[<span class="no-wrap-code">`daily_mean_change_7_days`</span>](./mean-change-7-days.md#daily-mean-change-7-days)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.| |
|[<span class="no-wrap-code">`daily_partition_mean_change_7_days`</span>](./mean-change-7-days.md#daily-partition-mean-change-7-days)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last week.| |



### [mean change 30 days](./mean-change-30-days.md)
A column-level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout from last month.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_mean_change_30_days`</span>](./mean-change-30-days.md#profile-mean-change-30-days)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last month.| |
|[<span class="no-wrap-code">`daily_mean_change_30_days`</span>](./mean-change-30-days.md#daily-mean-change-30-days)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.| |
|[<span class="no-wrap-code">`daily_partition_mean_change_30_days`</span>](./mean-change-30-days.md#daily-partition-mean-change-30-days)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last month.| |



### [median change](./median-change.md)
A column-level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_median_change`</span>](./median-change.md#profile-median-change)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_median_change`</span>](./median-change.md#daily-median-change)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_partition_median_change`</span>](./median-change.md#daily-partition-median-change)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout.| |



### [median change 1 day](./median-change-1-day.md)
A column-level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout from yesterday.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_median_change_1_day`</span>](./median-change-1-day.md#profile-median-change-1-day)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.| |
|[<span class="no-wrap-code">`daily_median_change_1_day`</span>](./median-change-1-day.md#daily-median-change-1-day)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.| |
|[<span class="no-wrap-code">`daily_partition_median_change_1_day`</span>](./median-change-1-day.md#daily-partition-median-change-1-day)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.| |



### [median change 7 days](./median-change-7-days.md)
A column-level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout from last week.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_median_change_7_days`</span>](./median-change-7-days.md#profile-median-change-7-days)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.| |
|[<span class="no-wrap-code">`daily_median_change_7_days`</span>](./median-change-7-days.md#daily-median-change-7-days)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.| |
|[<span class="no-wrap-code">`daily_partition_median_change_7_days`</span>](./median-change-7-days.md#daily-partition-median-change-7-days)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.| |



### [median change 30 days](./median-change-30-days.md)
A column-level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout from last month.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_median_change_30_days`</span>](./median-change-30-days.md#profile-median-change-30-days)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.| |
|[<span class="no-wrap-code">`daily_median_change_30_days`</span>](./median-change-30-days.md#daily-median-change-30-days)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.| |
|[<span class="no-wrap-code">`daily_partition_median_change_30_days`</span>](./median-change-30-days.md#daily-partition-median-change-30-days)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.| |



### [sum change](./sum-change.md)
A column-level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sum_change`</span>](./sum-change.md#profile-sum-change)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_sum_change`</span>](./sum-change.md#daily-sum-change)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_partition_sum_change`</span>](./sum-change.md#daily-partition-sum-change)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout.| |



### [sum change 1 day](./sum-change-1-day.md)
A column-level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout from yesterday.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sum_change_1_day`</span>](./sum-change-1-day.md#profile-sum-change-1-day)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.| |
|[<span class="no-wrap-code">`daily_sum_change_1_day`</span>](./sum-change-1-day.md#daily-sum-change-1-day)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.| |
|[<span class="no-wrap-code">`daily_partition_sum_change_1_day`</span>](./sum-change-1-day.md#daily-partition-sum-change-1-day)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.| |



### [sum change 7 days](./sum-change-7-days.md)
A column-level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout from last week.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sum_change_7_days`</span>](./sum-change-7-days.md#profile-sum-change-7-days)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from last week.| |
|[<span class="no-wrap-code">`daily_sum_change_7_days`</span>](./sum-change-7-days.md#daily-sum-change-7-days)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from the last week.| |
|[<span class="no-wrap-code">`daily_partition_sum_change_7_days`</span>](./sum-change-7-days.md#daily-partition-sum-change-7-days)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from the last week.| |



### [sum change 30 days](./sum-change-30-days.md)
A column-level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout from last month.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sum_change_30_days`</span>](./sum-change-30-days.md#profile-sum-change-30-days)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from last month.| |
|[<span class="no-wrap-code">`daily_sum_change_30_days`</span>](./sum-change-30-days.md#daily-sum-change-30-days)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from the last month.| |
|[<span class="no-wrap-code">`daily_partition_sum_change_30_days`</span>](./sum-change-30-days.md#daily-partition-sum-change-30-days)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from the last month.| |







