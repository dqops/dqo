# Checks/column/anomaly

**This is a list of anomaly column data quality checks supported by DQOps and a brief description of what they do.**





## **anomaly**  
Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_anomaly_stationary_30_days](./column/anomaly/mean-anomaly-stationary-30-days/#profile-mean-anomaly-stationary-30-days)|profiling|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_mean_anomaly_stationary_30_days](./column/anomaly/mean-anomaly-stationary-30-days/#daily-mean-anomaly-stationary-30-days)|monitoring|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_partition_mean_anomaly_stationary_30_days](./column/anomaly/mean-anomaly-stationary-30-days/#daily-partition-mean-anomaly-stationary-30-days)|partitioned|Verifies that the mean value in a column is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_anomaly_stationary](./column/anomaly/mean-anomaly-stationary/#profile-mean-anomaly-stationary)|profiling|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_mean_anomaly_stationary](./column/anomaly/mean-anomaly-stationary/#daily-mean-anomaly-stationary)|monitoring|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_partition_mean_anomaly_stationary](./column/anomaly/mean-anomaly-stationary/#daily-partition-mean-anomaly-stationary)|partitioned|Verifies that the mean value in a column is within a percentile from measurements made during the last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_anomaly_stationary_30_days](./column/anomaly/median-anomaly-stationary-30-days/#profile-median-anomaly-stationary-30-days)|profiling|Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_median_anomaly_stationary_30_days](./column/anomaly/median-anomaly-stationary-30-days/#daily-median-anomaly-stationary-30-days)|monitoring|Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_partition_median_anomaly_stationary_30_days](./column/anomaly/median-anomaly-stationary-30-days/#daily-partition-median-anomaly-stationary-30-days)|partitioned|Verifies that the median in a column is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_anomaly_stationary](./column/anomaly/median-anomaly-stationary/#profile-median-anomaly-stationary)|profiling|Verifies that the median in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_median_anomaly_stationary](./column/anomaly/median-anomaly-stationary/#daily-median-anomaly-stationary)|monitoring|Verifies that the median in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_partition_median_anomaly_stationary](./column/anomaly/median-anomaly-stationary/#daily-partition-median-anomaly-stationary)|partitioned|Verifies that the median in a column is within a percentile from measurements made during the last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_anomaly_differencing_30_days](./column/anomaly/sum-anomaly-differencing-30-days/#profile-sum-anomaly-differencing-30-days)|profiling|Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_sum_anomaly_differencing_30_days](./column/anomaly/sum-anomaly-differencing-30-days/#daily-sum-anomaly-differencing-30-days)|monitoring|Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_anomaly_differencing](./column/anomaly/sum-anomaly-differencing/#profile-sum-anomaly-differencing)|profiling|Verifies that the sum in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_sum_anomaly_differencing](./column/anomaly/sum-anomaly-differencing/#daily-sum-anomaly-differencing)|monitoring|Verifies that the sum in a column changes in a rate within a percentile boundary during last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_change](./column/anomaly/mean-change/#profile-mean-change)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[daily_mean_change](./column/anomaly/mean-change/#daily-mean-change)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[monthly_mean_change](./column/anomaly/mean-change/#monthly-mean-change)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[daily_partition_mean_change](./column/anomaly/mean-change/#daily-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout.|
|[monthly_partition_mean_change](./column/anomaly/mean-change/#monthly-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_change_yesterday](./column/anomaly/mean-change-yesterday/#profile-mean-change-yesterday)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_mean_change_yesterday](./column/anomaly/mean-change-yesterday/#daily-mean-change-yesterday)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_mean_change_yesterday](./column/anomaly/mean-change-yesterday/#daily-partition-mean-change-yesterday)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_change_7_days](./column/anomaly/mean-change-7-days/#profile-mean-change-7-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|
|[daily_mean_change_7_days](./column/anomaly/mean-change-7-days/#daily-mean-change-7-days)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_mean_change_7_days](./column/anomaly/mean-change-7-days/#daily-partition-mean-change-7-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_mean_change_30_days](./column/anomaly/mean-change-30-days/#profile-mean-change-30-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|
|[daily_mean_change_30_days](./column/anomaly/mean-change-30-days/#daily-mean-change-30-days)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_mean_change_30_days](./column/anomaly/mean-change-30-days/#daily-partition-mean-change-30-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_change](./column/anomaly/median-change/#profile-median-change)|profiling|Verifies that the median in a column changed in a fixed rate since last readout.|
|[daily_median_change](./column/anomaly/median-change/#daily-median-change)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout.|
|[monthly_median_change](./column/anomaly/median-change/#monthly-median-change)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout.|
|[daily_partition_median_change](./column/anomaly/median-change/#daily-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout.|
|[monthly_partition_median_change](./column/anomaly/median-change/#monthly-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_change_yesterday](./column/anomaly/median-change-yesterday/#profile-median-change-yesterday)|profiling|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|
|[daily_median_change_yesterday](./column/anomaly/median-change-yesterday/#daily-median-change-yesterday)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_median_change_yesterday](./column/anomaly/median-change-yesterday/#daily-partition-median-change-yesterday)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_change_7_days](./column/anomaly/median-change-7-days/#profile-median-change-7-days)|profiling|Verifies that the median in a column changed in a fixed rate since last readout from last week.|
|[daily_median_change_7_days](./column/anomaly/median-change-7-days/#daily-median-change-7-days)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_median_change_7_days](./column/anomaly/median-change-7-days/#daily-partition-median-change-7-days)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_median_change_30_days](./column/anomaly/median-change-30-days/#profile-median-change-30-days)|profiling|Verifies that the median in a column changed in a fixed rate since last readout from last month.|
|[daily_median_change_30_days](./column/anomaly/median-change-30-days/#daily-median-change-30-days)|monitoring|Verifies that the median in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_median_change_30_days](./column/anomaly/median-change-30-days/#daily-partition-median-change-30-days)|partitioned|Verifies that the median in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_change](./column/anomaly/sum-change/#profile-sum-change)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[daily_sum_change](./column/anomaly/sum-change/#daily-sum-change)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[monthly_sum_change](./column/anomaly/sum-change/#monthly-sum-change)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[daily_partition_sum_change](./column/anomaly/sum-change/#daily-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout.|
|[monthly_partition_sum_change](./column/anomaly/sum-change/#monthly-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_change_yesterday](./column/anomaly/sum-change-yesterday/#profile-sum-change-yesterday)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|
|[daily_sum_change_yesterday](./column/anomaly/sum-change-yesterday/#daily-sum-change-yesterday)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_sum_change_yesterday](./column/anomaly/sum-change-yesterday/#daily-partition-sum-change-yesterday)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_change_7_days](./column/anomaly/sum-change-7-days/#profile-sum-change-7-days)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|
|[daily_sum_change_7_days](./column/anomaly/sum-change-7-days/#daily-sum-change-7-days)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_sum_change_7_days](./column/anomaly/sum-change-7-days/#daily-partition-sum-change-7-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_sum_change_30_days](./column/anomaly/sum-change-30-days/#profile-sum-change-30-days)|profiling|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|
|[daily_sum_change_30_days](./column/anomaly/sum-change-30-days/#daily-sum-change-30-days)|monitoring|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_sum_change_30_days](./column/anomaly/sum-change-30-days/#daily-partition-sum-change-30-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_sum_anomaly_stationary_30_days](./column/anomaly/sum-anomaly-stationary-30-days/#daily-partition-sum-anomaly-stationary-30-days)|partitioned|Verifies that the sum in a column is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_sum_anomaly_stationary](./column/anomaly/sum-anomaly-stationary/#daily-partition-sum-anomaly-stationary)|partitioned|Verifies that the sum in a column is within a percentile from measurements made during the last 90 days.|





