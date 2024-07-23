---
title: column level anomaly data quality checks
---
# column level anomaly data quality checks

This is a list of anomaly column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level anomaly checks
Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.

### [sum anomaly](./sum-anomaly.md)
This check calculates a sum of values in a numeric column and detects anomalies in a time series of previous sums.
 It raises a data quality issue when the sum is in the top *anomaly_percent* percentage of the most outstanding values in the time series.
 This data quality check uses a 90-day time window and requires a history of at least 30 days.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sum_anomaly`</span>](./sum-anomaly.md#profile-sum-anomaly)|Abnormal change in the sum of numeric values. Measured as a percentile of anomalous values.|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the sum in a column changes in a rate within a percentile boundary during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_sum_anomaly`</span>](./sum-anomaly.md#daily-sum-anomaly)|Abnormal change in the sum of numeric values. Measured as a percentile of anomalous values.|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the sum in a column changes in a rate within a percentile boundary during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_sum_anomaly`</span>](./sum-anomaly.md#daily-partition-sum-anomaly)|Abnormal change in the sum of numeric values. Measured as a percentile of anomalous values.|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the sum in a column is within a percentile from measurements made during the last 90 days. Calculates the sum of each daily partition and detect anomalies between daily partitions.|:material-check-bold:|



### [mean anomaly](./mean-anomaly.md)
This check calculates a mean (average) of values in a numeric column and detects anomalies in a time series of previous averages.
 It raises a data quality issue when the mean is in the top *anomaly_percent* percentage of the most outstanding values in the time series.
 This data quality check uses a 90-day time window and requires a history of at least 30 days.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_mean_anomaly`</span>](./mean-anomaly.md#profile-mean-anomaly)|Abnormal change in the mean (average) of numeric values. Measured as a percentile of anomalous values.|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the mean value in a column changes in a rate within a percentile boundary during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_mean_anomaly`</span>](./mean-anomaly.md#daily-mean-anomaly)|Abnormal change in the mean (average) of numeric values. Measured as a percentile of anomalous values.|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the mean value in a column changes in a rate within a percentile boundary during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_mean_anomaly`</span>](./mean-anomaly.md#daily-partition-mean-anomaly)|Abnormal change in the mean (average) of numeric values. Measured as a percentile of anomalous values.|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the mean value in a column is within a percentile from measurements made during the last 90 days. Calculates the mean (average) of each daily partition and detect anomalies between daily partitions.|:material-check-bold:|



### [median anomaly](./median-anomaly.md)
This check calculates a median of values in a numeric column and detects anomalies in a time series of previous medians.
 It raises a data quality issue when the median is in the top *anomaly_percent* percentage of the most outstanding values in the time series.
 This data quality check uses a 90-day time window and requires a history of at least 30 days.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_median_anomaly`</span>](./median-anomaly.md#profile-median-anomaly)|Abnormal change in the median of numeric values. Measured as a percentile of anomalous values.|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the median in a column changes in a rate within a percentile boundary during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_median_anomaly`</span>](./median-anomaly.md#daily-median-anomaly)|Abnormal change in the median of numeric values. Measured as a percentile of anomalous values.|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the median in a column changes in a rate within a percentile boundary during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_median_anomaly`</span>](./median-anomaly.md#daily-partition-median-anomaly)|Abnormal change in the median of numeric values. Measured as a percentile of anomalous values.|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the median in a column is within a percentile from measurements made during the last 90 days. Calculates the median of each daily partition and detect anomalies between daily partitions.|:material-check-bold:|



### [min anomaly](./min-anomaly.md)
This check finds a minimum value in a numeric column and detects anomalies in a time series of previous minimum values.
 It raises a data quality issue when the current minimum value is in the top *anomaly_percent* percentage of the most outstanding
 values in the time series (it is a new minimum value, far from the previous one).
 This data quality check uses a 90-day time window and requires a history of at least 30 days.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_min_anomaly`</span>](./min-anomaly.md#profile-min-anomaly)|Abnormal change in the minimum of numeric values. Measured as a percentile of anomalous values.|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects new outliers, which are new minimum values, much below the last known minimum value. If the minimum value is constantly changing, detects outliers as the biggest change of the minimum value during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_min_anomaly`</span>](./min-anomaly.md#daily-min-anomaly)|Abnormal change in the minimum of numeric values. Measured as a percentile of anomalous values.|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects new outliers, which are new minimum values, much below the last known minimum value. If the minimum value is constantly changing, detects outliers as the biggest change of the minimum value during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_min_anomaly`</span>](./min-anomaly.md#daily-partition-min-anomaly)|Abnormal change in the minimum of numeric values. Measured as a percentile of anomalous values.|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects new outliers, which are new minimum values, much below the last known minimum value. If the minimum value is constantly changing, detects outliers as the biggest change of the minimum value during the last 90 days. Finds the minimum value of each daily partition and detect anomalies between daily partitions.|:material-check-bold:|



### [max anomaly](./max-anomaly.md)
This check finds a maximum value in a numeric column and detects anomalies in a time series of previous maximum values.
 It raises a data quality issue when the current maximum value is in the top *anomaly_percent* percentage of the most outstanding
 values in the time series (it is a new maximum value, far from the previous one).
 This data quality check uses a 90-day time window and requires a history of at least 30 days.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_max_anomaly`</span>](./max-anomaly.md#profile-max-anomaly)|Abnormal change in the maximum of numeric values. Measured as a percentile of anomalous values.|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects new outliers, which are new maximum values, much above the last known maximum value. If the maximum value is constantly changing, detects outliers as the biggest change of the maximum value during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_max_anomaly`</span>](./max-anomaly.md#daily-max-anomaly)|Abnormal change in the maximum of numeric values. Measured as a percentile of anomalous values.|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects new outliers, which are new maximum values, much above the last known maximum value. If the maximum value is constantly changing, detects outliers as the biggest change of the maximum value during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_max_anomaly`</span>](./max-anomaly.md#daily-partition-max-anomaly)|Abnormal change in the maximum of numeric values. Measured as a percentile of anomalous values.|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects new outliers, which are new maximum values, much above the last known maximum value. If the maximum value is constantly changing, detects outliers as the biggest change of the maximum value during the last 90 days. Finds the maximum value of each daily partition and detect anomalies between daily partitions.|:material-check-bold:|



### [mean change](./mean-change.md)
This check detects that the mean (average) of numeric values has changed more than *max_percent* from the last measured mean.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_mean_change`</span>](./mean-change.md#profile-mean-change)|Maximum relative change in the mean (average) of numeric values since the last known value|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_mean_change`</span>](./mean-change.md#daily-mean-change)|Maximum relative change in the mean (average) of numeric values since the last known value|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_partition_mean_change`</span>](./mean-change.md#daily-partition-mean-change)|Maximum relative change in the mean (average) of numeric values since the last known value|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the mean value in a column changed in a fixed rate since last readout.| |



### [mean change 1 day](./mean-change-1-day.md)
This check detects that the mean (average) of numeric values has changed more than *max_percent* from the mean value measured one day ago (yesterday).


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_mean_change_1_day`</span>](./mean-change-1-day.md#profile-mean-change-1-day)|Maximum relative change in the mean (average) of numeric values vs 1 day ago|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout from yesterday.| |
|[<span class="no-wrap-code">`daily_mean_change_1_day`</span>](./mean-change-1-day.md#daily-mean-change-1-day)|Maximum relative change in the mean (average) of numeric values vs 1 day ago|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.| |
|[<span class="no-wrap-code">`daily_partition_mean_change_1_day`</span>](./mean-change-1-day.md#daily-partition-mean-change-1-day)|Maximum relative change in the mean (average) of numeric values vs 1 day ago|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout from yesterday.| |



### [mean change 7 days](./mean-change-7-days.md)
This check detects that the mean (average) value of numeric values has changed more than *max_percent* from the mean value measured seven days ago.
 This check aims to overcome a weekly seasonability and compare Mondays to Mondays, Tuesdays to Tuesdays, etc.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_mean_change_7_days`</span>](./mean-change-7-days.md#profile-mean-change-7-days)|Maximum relative change in the mean (average) of numeric values vs 7 days ago|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last week.| |
|[<span class="no-wrap-code">`daily_mean_change_7_days`</span>](./mean-change-7-days.md#daily-mean-change-7-days)|Maximum relative change in the mean (average) of numeric values vs 7 days ago|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.| |
|[<span class="no-wrap-code">`daily_partition_mean_change_7_days`</span>](./mean-change-7-days.md#daily-partition-mean-change-7-days)|Maximum relative change in the mean (average) of numeric values vs 7 days ago|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last week.| |



### [mean change 30 days](./mean-change-30-days.md)
This check detects that the mean (average) of numeric values has changed more than *max_percent* from the mean value measured thirty days ago.
 This check aims to overcome a monthly seasonability and compare a value to a similar value a month ago.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_mean_change_30_days`</span>](./mean-change-30-days.md#profile-mean-change-30-days)|Maximum relative change in the mean (average) of numeric values vs 30 days ago|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last month.| |
|[<span class="no-wrap-code">`daily_mean_change_30_days`</span>](./mean-change-30-days.md#daily-mean-change-30-days)|Maximum relative change in the mean (average) of numeric values vs 30 days ago|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.| |
|[<span class="no-wrap-code">`daily_partition_mean_change_30_days`</span>](./mean-change-30-days.md#daily-partition-mean-change-30-days)|Maximum relative change in the mean (average) of numeric values vs 30 days ago|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last month.| |



### [median change](./median-change.md)
This check detects that the median of numeric values has changed more than *max_percent* from the last measured median.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_median_change`</span>](./median-change.md#profile-median-change)|Maximum relative change in the median of numeric values since the last known value|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_median_change`</span>](./median-change.md#daily-median-change)|Maximum relative change in the median of numeric values since the last known value|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_partition_median_change`</span>](./median-change.md#daily-partition-median-change)|Maximum relative change in the median of numeric values since the last known value|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout.| |



### [median change 1 day](./median-change-1-day.md)
This check detects that the median of numeric values has changed more than *max_percent* from the median value measured one day ago (yesterday).


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_median_change_1_day`</span>](./median-change-1-day.md#profile-median-change-1-day)|Maximum relative change in the median of numeric values vs 1 day ago|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.| |
|[<span class="no-wrap-code">`daily_median_change_1_day`</span>](./median-change-1-day.md#daily-median-change-1-day)|Maximum relative change in the median of numeric values vs 1 day ago|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.| |
|[<span class="no-wrap-code">`daily_partition_median_change_1_day`</span>](./median-change-1-day.md#daily-partition-median-change-1-day)|Maximum relative change in the median of numeric values vs 1 day ago|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.| |



### [median change 7 days](./median-change-7-days.md)
This check detects that the median of numeric values has changed more than *max_percent* from the median value measured seven days ago.
 This check aims to overcome a weekly seasonability and compare Mondays to Mondays, Tuesdays to Tuesdays, etc.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_median_change_7_days`</span>](./median-change-7-days.md#profile-median-change-7-days)|Maximum relative change in the median of numeric values vs 7 days ago|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.| |
|[<span class="no-wrap-code">`daily_median_change_7_days`</span>](./median-change-7-days.md#daily-median-change-7-days)|Maximum relative change in the median of numeric values vs 7 days ago|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.| |
|[<span class="no-wrap-code">`daily_partition_median_change_7_days`</span>](./median-change-7-days.md#daily-partition-median-change-7-days)|Maximum relative change in the median of numeric values vs 7 days ago|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.| |



### [median change 30 days](./median-change-30-days.md)
This check detects that the median of numeric values has changed more than *max_percent* from the median value measured thirty days ago.
 This check aims to overcome a monthly seasonability and compare a value to a similar value a month ago.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_median_change_30_days`</span>](./median-change-30-days.md#profile-median-change-30-days)|Maximum relative change in the median of numeric values vs 30 days ago|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.| |
|[<span class="no-wrap-code">`daily_median_change_30_days`</span>](./median-change-30-days.md#daily-median-change-30-days)|Maximum relative change in the median of numeric values vs 30 days ago|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.| |
|[<span class="no-wrap-code">`daily_partition_median_change_30_days`</span>](./median-change-30-days.md#daily-partition-median-change-30-days)|Maximum relative change in the median of numeric values vs 30 days ago|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.| |



### [sum change](./sum-change.md)
This check detects that the sum of numeric values has changed more than *max_percent* from the last measured sum.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sum_change`</span>](./sum-change.md#profile-sum-change)|Maximum relative change in the sum of numeric values since the last known value|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_sum_change`</span>](./sum-change.md#daily-sum-change)|Maximum relative change in the sum of numeric values since the last known value|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_partition_sum_change`</span>](./sum-change.md#daily-partition-sum-change)|Maximum relative change in the sum of numeric values since the last known value|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout.| |



### [sum change 1 day](./sum-change-1-day.md)
This check detects that the sum of numeric values has changed more than *max_percent* from the sum measured one day ago (yesterday).


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sum_change_1_day`</span>](./sum-change-1-day.md#profile-sum-change-1-day)|Maximum relative change in the sum of numeric values vs 1 day ago|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.| |
|[<span class="no-wrap-code">`daily_sum_change_1_day`</span>](./sum-change-1-day.md#daily-sum-change-1-day)|Maximum relative change in the sum of numeric values vs 1 day ago|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.| |
|[<span class="no-wrap-code">`daily_partition_sum_change_1_day`</span>](./sum-change-1-day.md#daily-partition-sum-change-1-day)|Maximum relative change in the sum of numeric values vs 1 day ago|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.| |



### [sum change 7 days](./sum-change-7-days.md)
This check detects that the sum of numeric values has changed more than *max_percent* from the sum measured seven days ago.
 This check aims to overcome a weekly seasonability and compare Mondays to Mondays, Tuesdays to Tuesdays, etc.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sum_change_7_days`</span>](./sum-change-7-days.md#profile-sum-change-7-days)|Maximum relative change in the sum of numeric values vs 7 days ago|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from last week.| |
|[<span class="no-wrap-code">`daily_sum_change_7_days`</span>](./sum-change-7-days.md#daily-sum-change-7-days)|Maximum relative change in the sum of numeric values vs 7 days ago|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from the last week.| |
|[<span class="no-wrap-code">`daily_partition_sum_change_7_days`</span>](./sum-change-7-days.md#daily-partition-sum-change-7-days)|Maximum relative change in the sum of numeric values vs 7 days ago|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from the last week.| |



### [sum change 30 days](./sum-change-30-days.md)
This check detects that the sum of numeric values has changed more than *max_percent* from the sum measured thirty days ago.
 This check aims to overcome a monthly seasonability and compare a value to a similar value a month ago.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_sum_change_30_days`</span>](./sum-change-30-days.md#profile-sum-change-30-days)|Maximum relative change in the sum of numeric values vs 30 days ago|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from last month.| |
|[<span class="no-wrap-code">`daily_sum_change_30_days`</span>](./sum-change-30-days.md#daily-sum-change-30-days)|Maximum relative change in the sum of numeric values vs 30 days ago|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from the last month.| |
|[<span class="no-wrap-code">`daily_partition_sum_change_30_days`</span>](./sum-change-30-days.md#daily-partition-sum-change-30-days)|Maximum relative change in the sum of numeric values vs 30 days ago|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from the last month.| |







