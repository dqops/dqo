# Checks/column/nulls

**This is a list of nulls column data quality checks supported by DQOps and a brief description of what they do.**





## **nulls**
Checks for the presence of null or missing values in a column.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_count](../nulls-count.md#profile-nulls-count)|profiling|Detects columns with any null values when the max_count&#x3D;0. Verifies that the number of null values in a column does not exceed the maximum accepted count.|
|[daily_nulls_count](../nulls-count.md#daily-nulls-count)|monitoring|Detects columns with any null values when the max_count&#x3D;0. Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_nulls_count](../nulls-count.md#monthly-nulls-count)|monitoring|Detects columns with any null values when the max_count&#x3D;0. Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_nulls_count](../nulls-count.md#daily-partition-nulls-count)|partitioned|Detects columns with any null values when the max_count&#x3D;0. Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_nulls_count](../nulls-count.md#monthly-partition-nulls-count)|partitioned|Detects columns with any null values when the max_count&#x3D;0. Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent](../nulls-percent.md#profile-nulls-percent)|profiling|Verifies that the percent of null values in a column does not exceed the maximum accepted percentage.|
|[daily_nulls_percent](../nulls-percent.md#daily-nulls-percent)|monitoring|Verifies that the percentage of nulls in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_nulls_percent](../nulls-percent.md#monthly-nulls-percent)|monitoring|Verifies that the percentage of null values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_nulls_percent](../nulls-percent.md#daily-partition-nulls-percent)|partitioned|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_nulls_percent](../nulls-percent.md#monthly-partition-nulls-percent)|partitioned|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_anomaly_stationary_30_days](../nulls-percent-anomaly-stationary-30-days.md#profile-nulls-percent-anomaly-stationary-30-days)|profiling|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_nulls_percent_anomaly_stationary_30_days](../nulls-percent-anomaly-stationary-30-days.md#daily-nulls-percent-anomaly-stationary-30-days)|monitoring|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_partition_nulls_percent_anomaly_stationary_30_days](../nulls-percent-anomaly-stationary-30-days.md#daily-partition-nulls-percent-anomaly-stationary-30-days)|partitioned|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_anomaly_stationary](../nulls-percent-anomaly-stationary.md#profile-nulls-percent-anomaly-stationary)|profiling|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_nulls_percent_anomaly_stationary](../nulls-percent-anomaly-stationary.md#daily-nulls-percent-anomaly-stationary)|monitoring|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_partition_nulls_percent_anomaly_stationary](../nulls-percent-anomaly-stationary.md#daily-partition-nulls-percent-anomaly-stationary)|partitioned|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_change](../nulls-percent-change.md#profile-nulls-percent-change)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout.|
|[daily_nulls_percent_change](../nulls-percent-change.md#daily-nulls-percent-change)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since last readout.|
|[daily_partition_nulls_percent_change](../nulls-percent-change.md#daily-partition-nulls-percent-change)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_change_yesterday](../nulls-percent-change-yesterday.md#profile-nulls-percent-change-yesterday)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_nulls_percent_change_yesterday](../nulls-percent-change-yesterday.md#daily-nulls-percent-change-yesterday)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_nulls_percent_change_yesterday](../nulls-percent-change-yesterday.md#daily-partition-nulls-percent-change-yesterday)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_change_7_days](../nulls-percent-change-7-days.md#profile-nulls-percent-change-7-days)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|
|[daily_nulls_percent_change_7_days](../nulls-percent-change-7-days.md#daily-nulls-percent-change-7-days)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_nulls_percent_change_7_days](../nulls-percent-change-7-days.md#daily-partition-nulls-percent-change-7-days)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_change_30_days](../nulls-percent-change-30-days.md#profile-nulls-percent-change-30-days)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|
|[daily_nulls_percent_change_30_days](../nulls-percent-change-30-days.md#daily-nulls-percent-change-30-days)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_nulls_percent_change_30_days](../nulls-percent-change-30-days.md#daily-partition-nulls-percent-change-30-days)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_not_nulls_count](../not-nulls-count.md#profile-not-nulls-count)|profiling|Detects empty columns with the min_count&#x3D;0 rule. Verifies that the number of not null values in a column does not exceed the minimum accepted count.|
|[daily_not_nulls_count](../not-nulls-count.md#daily-not-nulls-count)|monitoring|Detects empty columns with the min_count&#x3D;0 rule. Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_not_nulls_count](../not-nulls-count.md#monthly-not-nulls-count)|monitoring|Detects empty columns with the min_count&#x3D;0 rule. Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_not_nulls_count](../not-nulls-count.md#daily-partition-not-nulls-count)|partitioned|Detects empty columns with the min_count&#x3D;0 rule. Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_not_nulls_count](../not-nulls-count.md#monthly-partition-not-nulls-count)|partitioned|Detects empty columns with the min_count&#x3D;0 rule. Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_not_nulls_percent](../not-nulls-percent.md#profile-not-nulls-percent)|profiling|Verifies that the percent of not null values in a column does not exceed the minimum accepted percentage.|
|[daily_not_nulls_percent](../not-nulls-percent.md#daily-not-nulls-percent)|monitoring|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_not_nulls_percent](../not-nulls-percent.md#monthly-not-nulls-percent)|monitoring|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_not_nulls_percent](../not-nulls-percent.md#daily-partition-not-nulls-percent)|partitioned|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_not_nulls_percent](../not-nulls-percent.md#monthly-partition-not-nulls-percent)|partitioned|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





