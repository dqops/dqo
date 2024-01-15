# column level anomaly data quality checks

This is a list of anomaly column data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **anomaly**
Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_sum_anomaly](./sum-anomaly.md#profile-sum-anomaly)|profiling|Verifies that the sum in a column changes in a rate within a percentile boundary during the last 90 days.|standard|
|[daily_sum_anomaly](./sum-anomaly.md#daily-sum-anomaly)|monitoring|Verifies that the sum in a column changes in a rate within a percentile boundary during the last 90 days.|standard|
|[daily_partition_sum_anomaly](./sum-anomaly.md#daily-partition-sum-anomaly)|partitioned|Verifies that the sum in a column is within a percentile from measurements made during the last 90 days.|standard|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_mean_anomaly](./mean-anomaly.md#profile-mean-anomaly)|profiling|Verifies that the mean value in a column changes in a rate within a percentile boundary during the last 90 days.|standard|
|[daily_mean_anomaly](./mean-anomaly.md#daily-mean-anomaly)|monitoring|Verifies that the mean value in a column changes in a rate within a percentile boundary during the last 90 days.|standard|
|[daily_partition_mean_anomaly](./mean-anomaly.md#daily-partition-mean-anomaly)|partitioned|Verifies that the mean value in a column is within a percentile from measurements made during the last 90 days.|standard|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_median_anomaly](./median-anomaly.md#profile-median-anomaly)|profiling|Verifies that the median in a column changes in a rate within a percentile boundary during the last 90 days.|advanced|
|[daily_median_anomaly](./median-anomaly.md#daily-median-anomaly)|monitoring|Verifies that the median in a column changes in a rate within a percentile boundary during the last 90 days.|advanced|
|[daily_partition_median_anomaly](./median-anomaly.md#daily-partition-median-anomaly)|partitioned|Verifies that the median in a column is within a percentile from measurements made during the last 90 days.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_mean_change](./mean-change.md#profile-mean-change)|profiling|Verifies that the mean value in a column changed in a fixed rate since the last readout.|advanced|
|[daily_mean_change](./mean-change.md#daily-mean-change)|monitoring|Verifies that the mean value in a column changed in a fixed rate since the last readout.|advanced|
|[monthly_mean_change](./mean-change.md#monthly-mean-change)|monitoring|Verifies that the mean value in a column changed in a fixed rate since the last readout.|advanced|
|[daily_partition_mean_change](./mean-change.md#daily-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout.|advanced|
|[monthly_partition_mean_change](./mean-change.md#monthly-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since the last readout.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_mean_change_1_day](./mean-change-1-day.md#profile-mean-change-1-day)|profiling|Verifies that the mean value in a column changed in a fixed rate since the last readout from yesterday.|advanced|
|[daily_mean_change_1_day](./mean-change-1-day.md#daily-mean-change-1-day)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|advanced|
|[daily_partition_mean_change_1_day](./mean-change-1-day.md#daily-partition-mean-change-1-day)|partitioned|Verifies that the mean value in a column changed in a fixed rate since the last readout from yesterday.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_mean_change_7_days](./mean-change-7-days.md#profile-mean-change-7-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last week.|advanced|
|[daily_mean_change_7_days](./mean-change-7-days.md#daily-mean-change-7-days)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|advanced|
|[daily_partition_mean_change_7_days](./mean-change-7-days.md#daily-partition-mean-change-7-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last week.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_mean_change_30_days](./mean-change-30-days.md#profile-mean-change-30-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last month.|advanced|
|[daily_mean_change_30_days](./mean-change-30-days.md#daily-mean-change-30-days)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|advanced|
|[daily_partition_mean_change_30_days](./mean-change-30-days.md#daily-partition-mean-change-30-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last month.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_median_change](./median-change.md#profile-median-change)|profiling|Verifies that the median in a column changed in a fixed rate since the last readout.|advanced|
|[daily_median_change](./median-change.md#daily-median-change)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout.|advanced|
|[monthly_median_change](./median-change.md#monthly-median-change)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout.|advanced|
|[daily_partition_median_change](./median-change.md#daily-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout.|advanced|
|[monthly_partition_median_change](./median-change.md#monthly-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_median_change_1_day](./median-change-1-day.md#profile-median-change-1-day)|profiling|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.|advanced|
|[daily_median_change_1_day](./median-change-1-day.md#daily-median-change-1-day)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.|advanced|
|[daily_partition_median_change_1_day](./median-change-1-day.md#daily-partition-median-change-1-day)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_median_change_7_days](./median-change-7-days.md#profile-median-change-7-days)|profiling|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.|advanced|
|[daily_median_change_7_days](./median-change-7-days.md#daily-median-change-7-days)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.|advanced|
|[daily_partition_median_change_7_days](./median-change-7-days.md#daily-partition-median-change-7-days)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_median_change_30_days](./median-change-30-days.md#profile-median-change-30-days)|profiling|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.|advanced|
|[daily_median_change_30_days](./median-change-30-days.md#daily-median-change-30-days)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.|advanced|
|[daily_partition_median_change_30_days](./median-change-30-days.md#daily-partition-median-change-30-days)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_sum_change](./sum-change.md#profile-sum-change)|profiling|Verifies that the sum in a column changed in a fixed rate since the last readout.|advanced|
|[daily_sum_change](./sum-change.md#daily-sum-change)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout.|advanced|
|[monthly_sum_change](./sum-change.md#monthly-sum-change)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout.|advanced|
|[daily_partition_sum_change](./sum-change.md#daily-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout.|advanced|
|[monthly_partition_sum_change](./sum-change.md#monthly-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_sum_change_1_day](./sum-change-1-day.md#profile-sum-change-1-day)|profiling|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.|advanced|
|[daily_sum_change_1_day](./sum-change-1-day.md#daily-sum-change-1-day)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.|advanced|
|[daily_partition_sum_change_1_day](./sum-change-1-day.md#daily-partition-sum-change-1-day)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_sum_change_7_days](./sum-change-7-days.md#profile-sum-change-7-days)|profiling|Verifies that the sum in a column changed in a fixed rate since the last readout from last week.|advanced|
|[daily_sum_change_7_days](./sum-change-7-days.md#daily-sum-change-7-days)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout from the last week.|advanced|
|[daily_partition_sum_change_7_days](./sum-change-7-days.md#daily-partition-sum-change-7-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout from the last week.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_sum_change_30_days](./sum-change-30-days.md#profile-sum-change-30-days)|profiling|Verifies that the sum in a column changed in a fixed rate since the last readout from last month.|advanced|
|[daily_sum_change_30_days](./sum-change-30-days.md#daily-sum-change-30-days)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout from the last month.|advanced|
|[daily_partition_sum_change_30_days](./sum-change-30-days.md#daily-partition-sum-change-30-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout from the last month.|advanced|







