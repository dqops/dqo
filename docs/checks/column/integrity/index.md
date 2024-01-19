# column level integrity data quality checks

This is a list of integrity column data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **integrity**
Checks the referential integrity of a column against a column in another table.

| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_lookup_key_not_found`</span>](./lookup-key-not-found.md#profile-lookup-key-not-found)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_lookup_key_not_found`</span>](./lookup-key-not-found.md#daily-lookup-key-not-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_lookup_key_not_found`</span>](./lookup-key-not-found.md#monthly-lookup-key-not-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent check result for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_lookup_key_not_found`</span>](./lookup-key-not-found.md#daily-partition-lookup-key-not-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_lookup_key_not_found`</span>](./lookup-key-not-found.md#monthly-partition-lookup-key-not-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_lookup_key_found_percent`</span>](./lookup-key-found-percent.md#profile-lookup-key-found-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count.| |
|[<span class="no-wrap-code">`daily_lookup_key_found_percent`</span>](./lookup-key-found-percent.md#daily-lookup-key-found-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_lookup_key_found_percent`</span>](./lookup-key-found-percent.md#monthly-lookup-key-found-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent check result for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_lookup_key_found_percent`</span>](./lookup-key-found-percent.md#daily-partition-lookup-key-found-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_lookup_key_found_percent`</span>](./lookup-key-found-percent.md#monthly-partition-lookup-key-found-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores a separate data quality check result for each monthly partition.| |







