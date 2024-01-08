# Checks/column/anomaly

**This is a list of anomaly column data quality checks supported by DQOps and a brief description of what they do.**





## **anomaly**
Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_anomaly_stationary_30_days](../mean-anomaly-stationary-30-days.md#profile-mean-anomaly-stationary-30-days)|profiling|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_mean_anomaly_stationary_30_days](../mean-anomaly-stationary-30-days.md#daily-mean-anomaly-stationary-30-days)|monitoring|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_partition_mean_anomaly_stationary_30_days](../mean-anomaly-stationary-30-days.md#daily-partition-mean-anomaly-stationary-30-days)|partitioned|Verifies that the mean value in a column is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_anomaly_stationary](../mean-anomaly-stationary.md#profile-mean-anomaly-stationary)|profiling|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_mean_anomaly_stationary](../mean-anomaly-stationary.md#daily-mean-anomaly-stationary)|monitoring|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_partition_mean_anomaly_stationary](../mean-anomaly-stationary.md#daily-partition-mean-anomaly-stationary)|partitioned|Verifies that the mean value in a column is within a percentile from measurements made during the last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_anomaly_stationary_30_days](../median-anomaly-stationary-30-days.md#profile-median-anomaly-stationary-30-days)|profiling|Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_median_anomaly_stationary_30_days](../median-anomaly-stationary-30-days.md#daily-median-anomaly-stationary-30-days)|monitoring|Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_partition_median_anomaly_stationary_30_days](../median-anomaly-stationary-30-days.md#daily-partition-median-anomaly-stationary-30-days)|partitioned|Verifies that the median in a column is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_anomaly_stationary](../median-anomaly-stationary.md#profile-median-anomaly-stationary)|profiling|Verifies that the median in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_median_anomaly_stationary](../median-anomaly-stationary.md#daily-median-anomaly-stationary)|monitoring|Verifies that the median in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_partition_median_anomaly_stationary](../median-anomaly-stationary.md#daily-partition-median-anomaly-stationary)|partitioned|Verifies that the median in a column is within a percentile from measurements made during the last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_anomaly_differencing_30_days](../sum-anomaly-differencing-30-days.md#profile-sum-anomaly-differencing-30-days)|profiling|Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_sum_anomaly_differencing_30_days](../sum-anomaly-differencing-30-days.md#daily-sum-anomaly-differencing-30-days)|monitoring|Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_anomaly_differencing](../sum-anomaly-differencing.md#profile-sum-anomaly-differencing)|profiling|Verifies that the sum in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_sum_anomaly_differencing](../sum-anomaly-differencing.md#daily-sum-anomaly-differencing)|monitoring|Verifies that the sum in a column changes in a rate within a percentile boundary during last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_change](../mean-change.md#profile-mean-change)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[daily_mean_change](../mean-change.md#daily-mean-change)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[monthly_mean_change](../mean-change.md#monthly-mean-change)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[daily_partition_mean_change](../mean-change.md#daily-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[monthly_partition_mean_change](../mean-change.md#monthly-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_change_yesterday](../mean-change-yesterday.md#profile-mean-change-yesterday)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_mean_change_yesterday](../mean-change-yesterday.md#daily-mean-change-yesterday)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_mean_change_yesterday](../mean-change-yesterday.md#daily-partition-mean-change-yesterday)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_change_7_days](../mean-change-7-days.md#profile-mean-change-7-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|
|[daily_mean_change_7_days](../mean-change-7-days.md#daily-mean-change-7-days)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_mean_change_7_days](../mean-change-7-days.md#daily-partition-mean-change-7-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_change_30_days](../mean-change-30-days.md#profile-mean-change-30-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|
|[daily_mean_change_30_days](../mean-change-30-days.md#daily-mean-change-30-days)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_mean_change_30_days](../mean-change-30-days.md#daily-partition-mean-change-30-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_change](../median-change.md#profile-median-change)|profiling|Verifies that the median in a column changed in a fixed rate since last readout.|
|[daily_median_change](../median-change.md#daily-median-change)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout.|
|[monthly_median_change](../median-change.md#monthly-median-change)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout.|
|[daily_partition_median_change](../median-change.md#daily-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout.|
|[monthly_partition_median_change](../median-change.md#monthly-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_change_yesterday](../median-change-yesterday.md#profile-median-change-yesterday)|profiling|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|
|[daily_median_change_yesterday](../median-change-yesterday.md#daily-median-change-yesterday)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_median_change_yesterday](../median-change-yesterday.md#daily-partition-median-change-yesterday)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_change_7_days](../median-change-7-days.md#profile-median-change-7-days)|profiling|Verifies that the median in a column changed in a fixed rate since last readout from last week.|
|[daily_median_change_7_days](../median-change-7-days.md#daily-median-change-7-days)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_median_change_7_days](../median-change-7-days.md#daily-partition-median-change-7-days)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_change_30_days](../median-change-30-days.md#profile-median-change-30-days)|profiling|Verifies that the median in a column changed in a fixed rate since last readout from last month.|
|[daily_median_change_30_days](../median-change-30-days.md#daily-median-change-30-days)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_median_change_30_days](../median-change-30-days.md#daily-partition-median-change-30-days)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_change](../sum-change.md#profile-sum-change)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[daily_sum_change](../sum-change.md#daily-sum-change)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[monthly_sum_change](../sum-change.md#monthly-sum-change)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[daily_partition_sum_change](../sum-change.md#daily-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[monthly_partition_sum_change](../sum-change.md#monthly-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_change_yesterday](../sum-change-yesterday.md#profile-sum-change-yesterday)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|
|[daily_sum_change_yesterday](../sum-change-yesterday.md#daily-sum-change-yesterday)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_sum_change_yesterday](../sum-change-yesterday.md#daily-partition-sum-change-yesterday)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_change_7_days](../sum-change-7-days.md#profile-sum-change-7-days)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|
|[daily_sum_change_7_days](../sum-change-7-days.md#daily-sum-change-7-days)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_sum_change_7_days](../sum-change-7-days.md#daily-partition-sum-change-7-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_change_30_days](../sum-change-30-days.md#profile-sum-change-30-days)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|
|[daily_sum_change_30_days](../sum-change-30-days.md#daily-sum-change-30-days)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_sum_change_30_days](../sum-change-30-days.md#daily-partition-sum-change-30-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_sum_anomaly_stationary_30_days](../sum-anomaly-stationary-30-days.md#daily-partition-sum-anomaly-stationary-30-days)|partitioned|Verifies that the sum in a column is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_sum_anomaly_stationary](../sum-anomaly-stationary.md#daily-partition-sum-anomaly-stationary)|partitioned|Verifies that the sum in a column is within a percentile from measurements made during the last 90 days.|





