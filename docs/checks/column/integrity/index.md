# Checks/column/integrity

**This is a list of integrity column data quality checks supported by DQOps and a brief description of what they do.**





## **integrity**  
Checks the referential integrity of a column against a column in another table.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_foreign_key_not_match_count](foreign-key-not-match-count/#profile-foreign-key-not-match-count)|profiling|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count.|
|[daily_foreign_key_not_match_count](foreign-key-not-match-count/#daily-foreign-key-not-match-count)|monitoring|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_foreign_key_not_match_count](foreign-key-not-match-count/#monthly-foreign-key-not-match-count)|monitoring|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_foreign_key_not_match_count](foreign-key-not-match-count/#daily-partition-foreign-key-not-match-count)|partitioned|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_foreign_key_not_match_count](foreign-key-not-match-count/#monthly-partition-foreign-key-not-match-count)|partitioned|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_foreign_key_match_percent](foreign-key-match-percent/#profile-foreign-key-match-percent)|profiling|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count.|
|[daily_foreign_key_match_percent](foreign-key-match-percent/#daily-foreign-key-match-percent)|monitoring|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_foreign_key_match_percent](foreign-key-match-percent/#monthly-foreign-key-match-percent)|monitoring|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_foreign_key_match_percent](foreign-key-match-percent/#daily-partition-foreign-key-match-percent)|partitioned|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_foreign_key_match_percent](foreign-key-match-percent/#monthly-partition-foreign-key-match-percent)|partitioned|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|





