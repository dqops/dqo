# column level anomaly data quality checks

This is a list of anomaly column data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **anomaly**
Detects anomalous (unexpected) changes and outliers in the time series of data quality results collected over a period of time.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sum_anomaly`</span>](./sum-anomaly.md#profile-sum-anomaly)|profiling|Verifies that the sum in a column changes in a rate within a percentile boundary during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_sum_anomaly`</span>](./sum-anomaly.md#daily-sum-anomaly)|monitoring|Verifies that the sum in a column changes in a rate within a percentile boundary during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_partition_sum_anomaly`</span>](./sum-anomaly.md#daily-partition-sum-anomaly)|partitioned|Verifies that the sum in a column is within a percentile from measurements made during the last 90 days.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_mean_anomaly`</span>](./mean-anomaly.md#profile-mean-anomaly)|profiling|Verifies that the mean value in a column changes in a rate within a percentile boundary during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_mean_anomaly`</span>](./mean-anomaly.md#daily-mean-anomaly)|monitoring|Verifies that the mean value in a column changes in a rate within a percentile boundary during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_partition_mean_anomaly`</span>](./mean-anomaly.md#daily-partition-mean-anomaly)|partitioned|Verifies that the mean value in a column is within a percentile from measurements made during the last 90 days.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_median_anomaly`</span>](./median-anomaly.md#profile-median-anomaly)|profiling|Verifies that the median in a column changes in a rate within a percentile boundary during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_median_anomaly`</span>](./median-anomaly.md#daily-median-anomaly)|monitoring|Verifies that the median in a column changes in a rate within a percentile boundary during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_partition_median_anomaly`</span>](./median-anomaly.md#daily-partition-median-anomaly)|partitioned|Verifies that the median in a column is within a percentile from measurements made during the last 90 days.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_mean_change`</span>](./mean-change.md#profile-mean-change)|profiling|Verifies that the mean value in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_mean_change`</span>](./mean-change.md#daily-mean-change)|monitoring|Verifies that the mean value in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_mean_change`</span>](./mean-change.md#monthly-mean-change)|monitoring|Verifies that the mean value in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_partition_mean_change`</span>](./mean-change.md#daily-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since last readout.|**|
|[<span class="no-wrap-code">`monthly_partition_mean_change`</span>](./mean-change.md#monthly-partition-mean-change)|partitioned|Verifies that the mean value in a column changed in a fixed rate since the last readout.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_mean_change_1_day`</span>](./mean-change-1-day.md#profile-mean-change-1-day)|profiling|Verifies that the mean value in a column changed in a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_mean_change_1_day`</span>](./mean-change-1-day.md#daily-mean-change-1-day)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_partition_mean_change_1_day`</span>](./mean-change-1-day.md#daily-partition-mean-change-1-day)|partitioned|Verifies that the mean value in a column changed in a fixed rate since the last readout from yesterday.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_mean_change_7_days`</span>](./mean-change-7-days.md#profile-mean-change-7-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last week.|**|
|[<span class="no-wrap-code">`daily_mean_change_7_days`</span>](./mean-change-7-days.md#daily-mean-change-7-days)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|**|
|[<span class="no-wrap-code">`daily_partition_mean_change_7_days`</span>](./mean-change-7-days.md#daily-partition-mean-change-7-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last week.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_mean_change_30_days`</span>](./mean-change-30-days.md#profile-mean-change-30-days)|profiling|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last month.|**|
|[<span class="no-wrap-code">`daily_mean_change_30_days`</span>](./mean-change-30-days.md#daily-mean-change-30-days)|monitoring|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|**|
|[<span class="no-wrap-code">`daily_partition_mean_change_30_days`</span>](./mean-change-30-days.md#daily-partition-mean-change-30-days)|partitioned|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last month.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_median_change`</span>](./median-change.md#profile-median-change)|profiling|Verifies that the median in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_median_change`</span>](./median-change.md#daily-median-change)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_median_change`</span>](./median-change.md#monthly-median-change)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_partition_median_change`</span>](./median-change.md#daily-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_partition_median_change`</span>](./median-change.md#monthly-partition-median-change)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_median_change_1_day`</span>](./median-change-1-day.md#profile-median-change-1-day)|profiling|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_median_change_1_day`</span>](./median-change-1-day.md#daily-median-change-1-day)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_partition_median_change_1_day`</span>](./median-change-1-day.md#daily-partition-median-change-1-day)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_median_change_7_days`</span>](./median-change-7-days.md#profile-median-change-7-days)|profiling|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.|**|
|[<span class="no-wrap-code">`daily_median_change_7_days`</span>](./median-change-7-days.md#daily-median-change-7-days)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.|**|
|[<span class="no-wrap-code">`daily_partition_median_change_7_days`</span>](./median-change-7-days.md#daily-partition-median-change-7-days)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_median_change_30_days`</span>](./median-change-30-days.md#profile-median-change-30-days)|profiling|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.|**|
|[<span class="no-wrap-code">`daily_median_change_30_days`</span>](./median-change-30-days.md#daily-median-change-30-days)|monitoring|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.|**|
|[<span class="no-wrap-code">`daily_partition_median_change_30_days`</span>](./median-change-30-days.md#daily-partition-median-change-30-days)|partitioned|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sum_change`</span>](./sum-change.md#profile-sum-change)|profiling|Verifies that the sum in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_sum_change`</span>](./sum-change.md#daily-sum-change)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_sum_change`</span>](./sum-change.md#monthly-sum-change)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_partition_sum_change`</span>](./sum-change.md#daily-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_partition_sum_change`</span>](./sum-change.md#monthly-partition-sum-change)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sum_change_1_day`</span>](./sum-change-1-day.md#profile-sum-change-1-day)|profiling|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_sum_change_1_day`</span>](./sum-change-1-day.md#daily-sum-change-1-day)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_partition_sum_change_1_day`</span>](./sum-change-1-day.md#daily-partition-sum-change-1-day)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sum_change_7_days`</span>](./sum-change-7-days.md#profile-sum-change-7-days)|profiling|Verifies that the sum in a column changed in a fixed rate since the last readout from last week.|**|
|[<span class="no-wrap-code">`daily_sum_change_7_days`</span>](./sum-change-7-days.md#daily-sum-change-7-days)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout from the last week.|**|
|[<span class="no-wrap-code">`daily_partition_sum_change_7_days`</span>](./sum-change-7-days.md#daily-partition-sum-change-7-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout from the last week.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_sum_change_30_days`</span>](./sum-change-30-days.md#profile-sum-change-30-days)|profiling|Verifies that the sum in a column changed in a fixed rate since the last readout from last month.|**|
|[<span class="no-wrap-code">`daily_sum_change_30_days`</span>](./sum-change-30-days.md#daily-sum-change-30-days)|monitoring|Verifies that the sum in a column changed in a fixed rate since the last readout from the last month.|**|
|[<span class="no-wrap-code">`daily_partition_sum_change_30_days`</span>](./sum-change-30-days.md#daily-partition-sum-change-30-days)|partitioned|Verifies that the sum in a column changed in a fixed rate since the last readout from the last month.|**|







