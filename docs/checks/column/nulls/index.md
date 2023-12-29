# Checks/column/nulls

**This is a list of nulls column data quality checks supported by DQOps and a brief description of what they do.**





## **nulls**  
Checks for the presence of null or missing values in a column.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_count](nulls-count/#profile-nulls-count)|profiling|Verifies that the number of null values in a column does not exceed the maximum accepted count.|
|[daily_nulls_count](nulls-count/#daily-nulls-count)|monitoring|Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_nulls_count](nulls-count/#monthly-nulls-count)|monitoring|Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_nulls_count](nulls-count/#daily-partition-nulls-count)|partitioned|Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_nulls_count](nulls-count/#monthly-partition-nulls-count)|partitioned|Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent](nulls-percent/#profile-nulls-percent)|profiling|Verifies that the percent of null values in a column does not exceed the maximum accepted percentage.|
|[daily_nulls_percent](nulls-percent/#daily-nulls-percent)|monitoring|Verifies that the percentage of nulls in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_nulls_percent](nulls-percent/#monthly-nulls-percent)|monitoring|Verifies that the percentage of null values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_nulls_percent](nulls-percent/#daily-partition-nulls-percent)|partitioned|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_nulls_percent](nulls-percent/#monthly-partition-nulls-percent)|partitioned|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_anomaly_stationary_30_days](nulls-percent-anomaly-stationary-30-days/#profile-nulls-percent-anomaly-stationary-30-days)|profiling|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_nulls_percent_anomaly_stationary_30_days](nulls-percent-anomaly-stationary-30-days/#daily-nulls-percent-anomaly-stationary-30-days)|monitoring|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.|
|[daily_partition_nulls_percent_anomaly_stationary_30_days](nulls-percent-anomaly-stationary-30-days/#daily-partition-nulls-percent-anomaly-stationary-30-days)|partitioned|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_anomaly_stationary](nulls-percent-anomaly-stationary/#profile-nulls-percent-anomaly-stationary)|profiling|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_nulls_percent_anomaly_stationary](nulls-percent-anomaly-stationary/#daily-nulls-percent-anomaly-stationary)|monitoring|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|
|[daily_partition_nulls_percent_anomaly_stationary](nulls-percent-anomaly-stationary/#daily-partition-nulls-percent-anomaly-stationary)|partitioned|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_change](nulls-percent-change/#profile-nulls-percent-change)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout.|
|[daily_nulls_percent_change](nulls-percent-change/#daily-nulls-percent-change)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since last readout.|
|[daily_partition_nulls_percent_change](nulls-percent-change/#daily-partition-nulls-percent-change)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_change_yesterday](nulls-percent-change-yesterday/#profile-nulls-percent-change-yesterday)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_nulls_percent_change_yesterday](nulls-percent-change-yesterday/#daily-nulls-percent-change-yesterday)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|
|[daily_partition_nulls_percent_change_yesterday](nulls-percent-change-yesterday/#daily-partition-nulls-percent-change-yesterday)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_change_7_days](nulls-percent-change-7-days/#profile-nulls-percent-change-7-days)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|
|[daily_nulls_percent_change_7_days](nulls-percent-change-7-days/#daily-nulls-percent-change-7-days)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|
|[daily_partition_nulls_percent_change_7_days](nulls-percent-change-7-days/#daily-partition-nulls-percent-change-7-days)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_nulls_percent_change_30_days](nulls-percent-change-30-days/#profile-nulls-percent-change-30-days)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|
|[daily_nulls_percent_change_30_days](nulls-percent-change-30-days/#daily-nulls-percent-change-30-days)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|
|[daily_partition_nulls_percent_change_30_days](nulls-percent-change-30-days/#daily-partition-nulls-percent-change-30-days)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_not_nulls_count](not-nulls-count/#profile-not-nulls-count)|profiling|Verifies that the number of not null values in a column does not exceed the minimum accepted count.|
|[daily_not_nulls_count](not-nulls-count/#daily-not-nulls-count)|monitoring|Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_not_nulls_count](not-nulls-count/#monthly-not-nulls-count)|monitoring|Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_not_nulls_count](not-nulls-count/#daily-partition-not-nulls-count)|partitioned|Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_not_nulls_count](not-nulls-count/#monthly-partition-not-nulls-count)|partitioned|Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_not_nulls_percent](not-nulls-percent/#profile-not-nulls-percent)|profiling|Verifies that the percent of not null values in a column does not exceed the minimum accepted percentage.|
|[daily_not_nulls_percent](not-nulls-percent/#daily-not-nulls-percent)|monitoring|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_not_nulls_percent](not-nulls-percent/#monthly-not-nulls-percent)|monitoring|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_not_nulls_percent](not-nulls-percent/#daily-partition-not-nulls-percent)|partitioned|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_not_nulls_percent](not-nulls-percent/#monthly-partition-not-nulls-percent)|partitioned|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





