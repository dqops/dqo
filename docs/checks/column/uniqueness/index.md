# Checks/column/uniqueness

**This is a list of uniqueness column data quality checks supported by DQOps and a brief description of what they do.**





## **uniqueness**
Counts the number or percent of duplicate or unique values in a column.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_distinct_count](../distinct-count.md#profile-distinct-count)|profiling|Verifies that the number of distinct values in a column does not fall below the minimum accepted count.|
|[daily_distinct_count](../distinct-count.md#daily-distinct-count)|monitoring|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_distinct_count](../distinct-count.md#monthly-distinct-count)|monitoring|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_distinct_count](../distinct-count.md#daily-partition-distinct-count)|partitioned|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_distinct_count](../distinct-count.md#monthly-partition-distinct-count)|partitioned|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_distinct_percent](../distinct-percent.md#profile-distinct-percent)|profiling|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent.|
|[daily_distinct_percent](../distinct-percent.md#daily-distinct-percent)|monitoring|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_distinct_percent](../distinct-percent.md#monthly-distinct-percent)|monitoring|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_distinct_percent](../distinct-percent.md#daily-partition-distinct-percent)|partitioned|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_distinct_percent](../distinct-percent.md#monthly-partition-distinct-percent)|partitioned|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_duplicate_count](../duplicate-count.md#profile-duplicate-count)|profiling|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.|
|[daily_duplicate_count](../duplicate-count.md#daily-duplicate-count)|monitoring|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_duplicate_count](../duplicate-count.md#monthly-duplicate-count)|monitoring|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_duplicate_count](../duplicate-count.md#daily-partition-duplicate-count)|partitioned|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_duplicate_count](../duplicate-count.md#monthly-partition-duplicate-count)|partitioned|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_duplicate_percent](../duplicate-percent.md#profile-duplicate-percent)|profiling|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.|
|[daily_duplicate_percent](../duplicate-percent.md#daily-duplicate-percent)|monitoring|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_duplicate_percent](../duplicate-percent.md#monthly-duplicate-percent)|monitoring|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_duplicate_percent](../duplicate-percent.md#daily-partition-duplicate-percent)|partitioned|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_duplicate_percent](../duplicate-percent.md#monthly-partition-duplicate-percent)|partitioned|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_anomaly_differencing_distinct_count_30_days](../anomaly-differencing-distinct-count-30-days.md#profile-anomaly-differencing-distinct-count-30-days)|profiling|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[daily_anomaly_differencing_distinct_count_30_days](../anomaly-differencing-distinct-count-30-days.md#daily-anomaly-differencing-distinct-count-30-days)|monitoring|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[monthly_anomaly_differencing_distinct_count_30_days](../anomaly-differencing-distinct-count-30-days.md#monthly-anomaly-differencing-distinct-count-30-days)|monitoring|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_anomaly_differencing_distinct_count](../anomaly-differencing-distinct-count.md#profile-anomaly-differencing-distinct-count)|profiling|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[daily_anomaly_differencing_distinct_count](../anomaly-differencing-distinct-count.md#daily-anomaly-differencing-distinct-count)|monitoring|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[monthly_anomaly_differencing_distinct_count](../anomaly-differencing-distinct-count.md#monthly-anomaly-differencing-distinct-count)|monitoring|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_anomaly_stationary_distinct_percent_30_days](../anomaly-stationary-distinct-percent-30-days.md#profile-anomaly-stationary-distinct-percent-30-days)|profiling|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[daily_anomaly_stationary_distinct_percent_30_days](../anomaly-stationary-distinct-percent-30-days.md#daily-anomaly-stationary-distinct-percent-30-days)|monitoring|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[monthly_anomaly_stationary_distinct_percent_30_days](../anomaly-stationary-distinct-percent-30-days.md#monthly-anomaly-stationary-distinct-percent-30-days)|monitoring|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[daily_partition_anomaly_stationary_distinct_percent_30_days](../anomaly-stationary-distinct-percent-30-days.md#daily-partition-anomaly-stationary-distinct-percent-30-days)|partitioned|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[monthly_partition_anomaly_stationary_distinct_percent_30_days](../anomaly-stationary-distinct-percent-30-days.md#monthly-partition-anomaly-stationary-distinct-percent-30-days)|partitioned|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_anomaly_stationary_distinct_percent](../anomaly-stationary-distinct-percent.md#profile-anomaly-stationary-distinct-percent)|profiling|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[daily_anomaly_stationary_distinct_percent](../anomaly-stationary-distinct-percent.md#daily-anomaly-stationary-distinct-percent)|monitoring|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[monthly_anomaly_stationary_distinct_percent](../anomaly-stationary-distinct-percent.md#monthly-anomaly-stationary-distinct-percent)|monitoring|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[daily_partition_anomaly_stationary_distinct_percent](../anomaly-stationary-distinct-percent.md#daily-partition-anomaly-stationary-distinct-percent)|partitioned|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[monthly_partition_anomaly_stationary_distinct_percent](../anomaly-stationary-distinct-percent.md#monthly-partition-anomaly-stationary-distinct-percent)|partitioned|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_count](../change-distinct-count.md#profile-change-distinct-count)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|
|[daily_change_distinct_count](../change-distinct-count.md#daily-change-distinct-count)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|
|[monthly_change_distinct_count](../change-distinct-count.md#monthly-change-distinct-count)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|
|[daily_partition_change_distinct_count](../change-distinct-count.md#daily-partition-change-distinct-count)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|
|[monthly_partition_change_distinct_count](../change-distinct-count.md#monthly-partition-change-distinct-count)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_count_since_7_days](../change-distinct-count-since-7-days.md#profile-change-distinct-count-since-7-days)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|
|[daily_change_distinct_count_since_7_days](../change-distinct-count-since-7-days.md#daily-change-distinct-count-since-7-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|
|[monthly_change_distinct_count_since_7_days](../change-distinct-count-since-7-days.md#monthly-change-distinct-count-since-7-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|
|[daily_partition_change_distinct_count_since_7_days](../change-distinct-count-since-7-days.md#daily-partition-change-distinct-count-since-7-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|
|[monthly_partition_change_distinct_count_since_7_days](../change-distinct-count-since-7-days.md#monthly-partition-change-distinct-count-since-7-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_count_since_30_days](../change-distinct-count-since-30-days.md#profile-change-distinct-count-since-30-days)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|
|[daily_change_distinct_count_since_30_days](../change-distinct-count-since-30-days.md#daily-change-distinct-count-since-30-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|
|[monthly_change_distinct_count_since_30_days](../change-distinct-count-since-30-days.md#monthly-change-distinct-count-since-30-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|
|[daily_partition_change_distinct_count_since_30_days](../change-distinct-count-since-30-days.md#daily-partition-change-distinct-count-since-30-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|
|[monthly_partition_change_distinct_count_since_30_days](../change-distinct-count-since-30-days.md#monthly-partition-change-distinct-count-since-30-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_count_since_yesterday](../change-distinct-count-since-yesterday.md#profile-change-distinct-count-since-yesterday)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[daily_change_distinct_count_since_yesterday](../change-distinct-count-since-yesterday.md#daily-change-distinct-count-since-yesterday)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[monthly_change_distinct_count_since_yesterday](../change-distinct-count-since-yesterday.md#monthly-change-distinct-count-since-yesterday)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[daily_partition_change_distinct_count_since_yesterday](../change-distinct-count-since-yesterday.md#daily-partition-change-distinct-count-since-yesterday)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[monthly_partition_change_distinct_count_since_yesterday](../change-distinct-count-since-yesterday.md#monthly-partition-change-distinct-count-since-yesterday)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_percent](../change-distinct-percent.md#profile-change-distinct-percent)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|
|[daily_change_distinct_percent](../change-distinct-percent.md#daily-change-distinct-percent)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|
|[monthly_change_distinct_percent](../change-distinct-percent.md#monthly-change-distinct-percent)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|
|[daily_partition_change_distinct_percent](../change-distinct-percent.md#daily-partition-change-distinct-percent)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|
|[monthly_partition_change_distinct_percent](../change-distinct-percent.md#monthly-partition-change-distinct-percent)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_percent_since_7_days](../change-distinct-percent-since-7-days.md#profile-change-distinct-percent-since-7-days)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|
|[daily_change_distinct_percent_since_7_days](../change-distinct-percent-since-7-days.md#daily-change-distinct-percent-since-7-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|
|[monthly_change_distinct_percent_since_7_days](../change-distinct-percent-since-7-days.md#monthly-change-distinct-percent-since-7-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|
|[daily_partition_change_distinct_percent_since_7_days](../change-distinct-percent-since-7-days.md#daily-partition-change-distinct-percent-since-7-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|
|[monthly_partition_change_distinct_percent_since_7_days](../change-distinct-percent-since-7-days.md#monthly-partition-change-distinct-percent-since-7-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_percent_since_30_days](../change-distinct-percent-since-30-days.md#profile-change-distinct-percent-since-30-days)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|
|[daily_change_distinct_percent_since_30_days](../change-distinct-percent-since-30-days.md#daily-change-distinct-percent-since-30-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|
|[monthly_change_distinct_percent_since_30_days](../change-distinct-percent-since-30-days.md#monthly-change-distinct-percent-since-30-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|
|[daily_partition_change_distinct_percent_since_30_days](../change-distinct-percent-since-30-days.md#daily-partition-change-distinct-percent-since-30-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|
|[monthly_partition_change_distinct_percent_since_30_days](../change-distinct-percent-since-30-days.md#monthly-partition-change-distinct-percent-since-30-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_change_distinct_percent_since_yesterday](../change-distinct-percent-since-yesterday.md#profile-change-distinct-percent-since-yesterday)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[daily_change_distinct_percent_since_yesterday](../change-distinct-percent-since-yesterday.md#daily-change-distinct-percent-since-yesterday)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[monthly_change_distinct_percent_since_yesterday](../change-distinct-percent-since-yesterday.md#monthly-change-distinct-percent-since-yesterday)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[daily_partition_change_distinct_percent_since_yesterday](../change-distinct-percent-since-yesterday.md#daily-partition-change-distinct-percent-since-yesterday)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|
|[monthly_partition_change_distinct_percent_since_yesterday](../change-distinct-percent-since-yesterday.md#monthly-partition-change-distinct-percent-since-yesterday)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_anomaly_stationary_distinct_count_30_days](../anomaly-stationary-distinct-count-30-days.md#daily-partition-anomaly-stationary-distinct-count-30-days)|partitioned|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|
|[monthly_partition_anomaly_stationary_distinct_count_30_days](../anomaly-stationary-distinct-count-30-days.md#monthly-partition-anomaly-stationary-distinct-count-30-days)|partitioned|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_anomaly_stationary_distinct_count](../anomaly-stationary-distinct-count.md#daily-partition-anomaly-stationary-distinct-count)|partitioned|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|
|[monthly_partition_anomaly_stationary_distinct_count](../anomaly-stationary-distinct-count.md#monthly-partition-anomaly-stationary-distinct-count)|partitioned|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|





