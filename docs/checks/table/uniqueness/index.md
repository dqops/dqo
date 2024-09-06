---
title: table level uniqueness data quality checks
---
# table level uniqueness data quality checks

This is a list of uniqueness table data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## table-level uniqueness checks


### [duplicate record count](./duplicate-record-count.md)
This check counts duplicate records values. It raises a data quality issue when the number of duplicates is above a minimum accepted value.
 The default configuration detects duplicate rows by enforcing that the *min_count* of duplicates is zero.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_duplicate_record_count`</span>](./duplicate-record-count.md#profile-duplicate-record-count)|Maximum count of duplicate records|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the number of duplicate record values in a table does not exceed the maximum accepted count.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_duplicate_record_count`</span>](./duplicate-record-count.md#daily-duplicate-record-count)|Maximum count of duplicate records|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of duplicate record values in a table does not exceed the maximum accepted count.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_duplicate_record_count`</span>](./duplicate-record-count.md#monthly-duplicate-record-count)|Maximum count of duplicate records|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of duplicate record values in a table does not exceed the maximum accepted count.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_duplicate_record_count`</span>](./duplicate-record-count.md#daily-partition-duplicate-record-count)|Maximum count of duplicate records|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of duplicate record values in a table does not exceed the maximum accepted count.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_duplicate_record_count`</span>](./duplicate-record-count.md#monthly-partition-duplicate-record-count)|Maximum count of duplicate records|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of duplicate record values in a table does not exceed the maximum accepted count.|:material-check-bold:|



### [duplicate record percent](./duplicate-record-percent.md)
This check measures the percentage of duplicate records values. It raises a data quality issue when the percentage of duplicates is above a minimum accepted value.
 The default threshold is 0% duplicate values.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_duplicate_record_percent`</span>](./duplicate-record-percent.md#profile-duplicate-record-percent)|Maximum percentage of duplicate records|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of duplicate record values in a table does not exceed the maximum accepted percentage.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_duplicate_record_percent`</span>](./duplicate-record-percent.md#daily-duplicate-record-percent)|Maximum percentage of duplicate records|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of duplicate record values in a table does not exceed the maximum accepted percentage.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_duplicate_record_percent`</span>](./duplicate-record-percent.md#monthly-duplicate-record-percent)|Maximum percentage of duplicate records|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of duplicate record values in a table does not exceed the maximum accepted percentage.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_duplicate_record_percent`</span>](./duplicate-record-percent.md#daily-partition-duplicate-record-percent)|Maximum percentage of duplicate records|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of duplicate record values in a table does not exceed the maximum accepted percentage.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_duplicate_record_percent`</span>](./duplicate-record-percent.md#monthly-partition-duplicate-record-percent)|Maximum percentage of duplicate records|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of duplicate record values in a table does not exceed the maximum accepted percentage.|:material-check-bold:|







