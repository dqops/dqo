# Checks/column/nulls

This is a list of nulls column data quality checks supported by DQOps and a brief description of what they do.





## **nulls**
Checks for the presence of null or missing values in a column.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_nulls_count](./nulls-count.md#profile-nulls-count)|profiling|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count.|standard|
|[daily_nulls_count](./nulls-count.md#daily-nulls-count)|monitoring|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_nulls_count](./nulls-count.md#monthly-nulls-count)|monitoring|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_nulls_count](./nulls-count.md#daily-partition-nulls-count)|partitioned|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_nulls_count](./nulls-count.md#monthly-partition-nulls-count)|partitioned|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_nulls_percent](./nulls-percent.md#profile-nulls-percent)|profiling|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage.|advanced|
|[daily_nulls_percent](./nulls-percent.md#daily-nulls-percent)|monitoring|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_nulls_percent](./nulls-percent.md#monthly-nulls-percent)|monitoring|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|advanced|
|[daily_partition_nulls_percent](./nulls-percent.md#daily-partition-nulls-percent)|partitioned|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_nulls_percent](./nulls-percent.md#monthly-partition-nulls-percent)|partitioned|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_not_nulls_count](./not-nulls-count.md#profile-not-nulls-count)|profiling|Detects empty columns. The default rule min_count&#x3D;1 verifies that the column has any values. Verifies that the number of not null values in a column does not exceed the minimum accepted count.|standard|
|[daily_not_nulls_count](./not-nulls-count.md#daily-not-nulls-count)|monitoring|Detects empty columns. The default rule min_count&#x3D;1 verifies that the column has any values. Verifies that the number of not null values in a column does not exceed the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_not_nulls_count](./not-nulls-count.md#monthly-not-nulls-count)|monitoring|Detects columns that are empty and have no values (with the rule threshold min_count&#x3D;1). Verifies that the number of not null values in a column does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_not_nulls_count](./not-nulls-count.md#daily-partition-not-nulls-count)|partitioned|Detects empty columns. The default rule min_count&#x3D;1 verifies that the column has any values. Verifies that the number of not null values in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_not_nulls_count](./not-nulls-count.md#monthly-partition-not-nulls-count)|partitioned|Detects empty columns. The default rule min_count&#x3D;1 verifies that the column has any values. Verifies that the number of not null values in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_not_nulls_percent](./not-nulls-percent.md#profile-not-nulls-percent)|profiling|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage.|standard|
|[daily_not_nulls_percent](./not-nulls-percent.md#daily-not-nulls-percent)|monitoring|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_not_nulls_percent](./not-nulls-percent.md#monthly-not-nulls-percent)|monitoring|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_not_nulls_percent](./not-nulls-percent.md#daily-partition-not-nulls-percent)|partitioned|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_not_nulls_percent](./not-nulls-percent.md#monthly-partition-not-nulls-percent)|partitioned|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_nulls_percent_anomaly](./nulls-percent-anomaly.md#profile-nulls-percent-anomaly)|profiling|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|advanced|
|[daily_nulls_percent_anomaly](./nulls-percent-anomaly.md#daily-nulls-percent-anomaly)|monitoring|Verifies that the null percent value in a column changes in a rate within a percentile boundary during the last 90 days.|advanced|
|[daily_partition_nulls_percent_anomaly](./nulls-percent-anomaly.md#daily-partition-nulls-percent-anomaly)|partitioned|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_nulls_percent_change](./nulls-percent-change.md#profile-nulls-percent-change)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout.|advanced|
|[daily_nulls_percent_change](./nulls-percent-change.md#daily-nulls-percent-change)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since the last readout.|advanced|
|[daily_partition_nulls_percent_change](./nulls-percent-change.md#daily-partition-nulls-percent-change)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since last readout.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_nulls_percent_change_1_day](./nulls-percent-change-1-day.md#profile-nulls-percent-change-1-day)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|advanced|
|[daily_nulls_percent_change_1_day](./nulls-percent-change-1-day.md#daily-nulls-percent-change-1-day)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since the last readout from yesterday.|advanced|
|[daily_partition_nulls_percent_change_1_day](./nulls-percent-change-1-day.md#daily-partition-nulls-percent-change-1-day)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since the last readout from yesterday.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_nulls_percent_change_7_days](./nulls-percent-change-7-days.md#profile-nulls-percent-change-7-days)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|advanced|
|[daily_nulls_percent_change_7_days](./nulls-percent-change-7-days.md#daily-nulls-percent-change-7-days)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last week.|advanced|
|[daily_partition_nulls_percent_change_7_days](./nulls-percent-change-7-days.md#daily-partition-nulls-percent-change-7-days)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last week.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_nulls_percent_change_30_days](./nulls-percent-change-30-days.md#profile-nulls-percent-change-30-days)|profiling|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|advanced|
|[daily_nulls_percent_change_30_days](./nulls-percent-change-30-days.md#daily-nulls-percent-change-30-days)|monitoring|Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last month.|advanced|
|[daily_partition_nulls_percent_change_30_days](./nulls-percent-change-30-days.md#daily-partition-nulls-percent-change-30-days)|partitioned|Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last month.|advanced|







