---
title: column level integrity data quality checks
---
# column level integrity data quality checks

This is a list of integrity column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level integrity checks
Checks the referential integrity of a column against a column in another table.

### [lookup key not found](./lookup-key-not-found.md)
This check detects invalid values that are not present in a dictionary table. The lookup uses an outer join query within the same database.
 This check counts the number of values not found in the dictionary table. It raises a data quality issue when too many missing keys are discovered.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_lookup_key_not_found`</span>](./lookup-key-not-found.md#profile-lookup-key-not-found)|Maximum count of rows containing values not found in a reference table (foreign key lookup)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects invalid values that are not present in a dictionary table using an outer join query. Counts the number of invalid keys.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_lookup_key_not_found`</span>](./lookup-key-not-found.md#daily-lookup-key-not-found)|Maximum count of rows containing values not found in a reference table (foreign key lookup)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects invalid values that are not present in a dictionary table using an outer join query. Counts the number of invalid keys. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_lookup_key_not_found`</span>](./lookup-key-not-found.md#monthly-lookup-key-not-found)|Maximum count of rows containing values not found in a reference table (foreign key lookup)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects invalid values that are not present in a dictionary table using an outer join query. Counts the number of invalid keys. Stores the most recent check result for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_lookup_key_not_found`</span>](./lookup-key-not-found.md#daily-partition-lookup-key-not-found)|Maximum count of rows containing values not found in a reference table (foreign key lookup)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects invalid values that are not present in a dictionary table using an outer join query. Counts the number of invalid keys. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_lookup_key_not_found`</span>](./lookup-key-not-found.md#monthly-partition-lookup-key-not-found)|Maximum count of rows containing values not found in a reference table (foreign key lookup)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects invalid values that are not present in a dictionary table using an outer join query. Counts the number of invalid keys. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [lookup key found percent](./lookup-key-found-percent.md)
This check detects invalid values that are not present in a dictionary table. The lookup uses an outer join query within the same database.
 This check measures the percentage of valid keys found in the dictionary table.
 It raises a data quality issue when a percentage of valid keys is below a minimum accepted threshold.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_lookup_key_found_percent`</span>](./lookup-key-found-percent.md#profile-lookup-key-found-percent)|Minimum percentage of rows containing values not found in a reference table (foreign key lookup)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Measures the percentage of valid values that are present in a dictionary table. Joins this table to a dictionary table using an outer join.| |
|[<span class="no-wrap-code">`daily_lookup_key_found_percent`</span>](./lookup-key-found-percent.md#daily-lookup-key-found-percent)|Minimum percentage of rows containing values not found in a reference table (foreign key lookup)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Measures the percentage of valid values that are present in a dictionary table. Joins this table to a dictionary table using an outer join. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_lookup_key_found_percent`</span>](./lookup-key-found-percent.md#monthly-lookup-key-found-percent)|Minimum percentage of rows containing values not found in a reference table (foreign key lookup)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Measures the percentage of valid values that are present in a dictionary table. Joins this table to a dictionary table using an outer join. Stores the most recent check result for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_lookup_key_found_percent`</span>](./lookup-key-found-percent.md#daily-partition-lookup-key-found-percent)|Minimum percentage of rows containing values not found in a reference table (foreign key lookup)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Measures the percentage of valid values that are present in a dictionary table. Joins this table to a dictionary table using an outer join. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_lookup_key_found_percent`</span>](./lookup-key-found-percent.md#monthly-partition-lookup-key-found-percent)|Minimum percentage of rows containing values not found in a reference table (foreign key lookup)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Measures the percentage of valid values that are present in a dictionary table. Joins this table to a dictionary table using an outer join. Stores a separate data quality check result for each monthly partition.| |







