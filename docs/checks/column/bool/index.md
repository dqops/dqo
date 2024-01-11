# Checks/column/bool

This is a list of bool column data quality checks supported by DQOps and a brief description of what they do.





## **bool**
Calculates the percentage of data in a Boolean format.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_true_percent](./true-percent.md#profile-true-percent)|profiling|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage.|standard|
|[daily_true_percent](./true-percent.md#daily-true-percent)|monitoring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_true_percent](./true-percent.md#monthly-true-percent)|monitoring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_true_percent](./true-percent.md#daily-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_true_percent](./true-percent.md#monthly-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_false_percent](./false-percent.md#profile-false-percent)|profiling|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage.|standard|
|[daily_false_percent](./false-percent.md#daily-false-percent)|monitoring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_false_percent](./false-percent.md#monthly-false-percent)|monitoring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|standard|
|[daily_partition_false_percent](./false-percent.md#daily-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_false_percent](./false-percent.md#monthly-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|standard|







