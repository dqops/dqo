# column level integrity data quality checks

This is a list of integrity column data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **integrity**
Checks the referential integrity of a column against a column in another table.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_lookup_key_not_found](./lookup-key-not-found.md#profile-lookup-key-not-found)|profiling|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count.|standard|
|[daily_lookup_key_not_found](./lookup-key-not-found.md#daily-lookup-key-not-found)|monitoring|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_lookup_key_not_found](./lookup-key-not-found.md#monthly-lookup-key-not-found)|monitoring|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent check result for each month when the data quality check was evaluated.|standard|
|[daily_partition_lookup_key_not_found](./lookup-key-not-found.md#daily-partition-lookup-key-not-found)|partitioned|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores a separate data quality check result for each daily partition.|standard|
|[monthly_partition_lookup_key_not_found](./lookup-key-not-found.md#monthly-partition-lookup-key-not-found)|partitioned|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores a separate data quality check result for each monthly partition.|standard|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_lookup_key_found_percent](./lookup-key-found-percent.md#profile-lookup-key-found-percent)|profiling|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count.|advanced|
|[daily_lookup_key_found_percent](./lookup-key-found-percent.md#daily-lookup-key-found-percent)|monitoring|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_lookup_key_found_percent](./lookup-key-found-percent.md#monthly-lookup-key-found-percent)|monitoring|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent check result for each month when the data quality check was evaluated.|advanced|
|[daily_partition_lookup_key_found_percent](./lookup-key-found-percent.md#daily-partition-lookup-key-found-percent)|partitioned|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores a separate data quality check result for each daily partition.|advanced|
|[monthly_partition_lookup_key_found_percent](./lookup-key-found-percent.md#monthly-partition-lookup-key-found-percent)|partitioned|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores a separate data quality check result for each monthly partition.|advanced|







