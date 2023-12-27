# Checks/column/bool

**This is a list of bool column data quality checks supported by DQOps and a brief description of what they do.**





## **bool**  
Calculates the percentage of data in a Boolean format.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_true_percent](./column/bool/true-percent/#profile-true-percent)|profiling|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage.|
|[daily_true_percent](./column/bool/true-percent/#daily-true-percent)|monitoring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_true_percent](./column/bool/true-percent/#monthly-true-percent)|monitoring|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_true_percent](./column/bool/true-percent/#daily-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_true_percent](./column/bool/true-percent/#monthly-partition-true-percent)|partitioned|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_false_percent](./column/bool/false-percent/#profile-false-percent)|profiling|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage.|
|[daily_false_percent](./column/bool/false-percent/#daily-false-percent)|monitoring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_false_percent](./column/bool/false-percent/#monthly-false-percent)|monitoring|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_false_percent](./column/bool/false-percent/#daily-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_false_percent](./column/bool/false-percent/#monthly-partition-false-percent)|partitioned|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|





