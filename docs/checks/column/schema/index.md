---
title: column level schema data quality checks
---
# column level schema data quality checks

This is a list of schema column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level schema checks
Detects schema drifts such as a column is missing or the data type has changed.

### [column exists](./column-exists.md)
A column-level check that reads the metadata of the monitored table and verifies if the column still exists in the data source.
 The data quality sensor returns a value of 1.0 when the column is found or 0.0 when the column is not found.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_column_exists`</span>](./column-exists.md#profile-column-exists)|Verify if the column exist|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Checks the metadata of the monitored table and verifies if the column exists.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_column_exists`</span>](./column-exists.md#daily-column-exists)|Verify if the column exist|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_column_exists`</span>](./column-exists.md#monthly-column-exists)|Verify if the column exist|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each month when the data quality check was evaluated.|:material-check-bold:|



### [column type changed](./column-type-changed.md)
A column-level check that detects if the data type of the column has changed since the last retrieval.
 This check calculates the hash of all the components of the column&#x27;s data type: the data type name, length, scale, precision and nullability.
 A data quality issue will be detected if the hash of the column&#x27;s data types has changed.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_column_type_changed`</span>](./column-type-changed.md#profile-column-type-changed)|Verify if the column data type has changed|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_column_type_changed`</span>](./column-type-changed.md#daily-column-type-changed)|Verify if the column data type has changed|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last day. Stores the most recent hash for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_column_type_changed`</span>](./column-type-changed.md#monthly-column-type-changed)|Verify if the column data type has changed|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last month. Stores the most recent hash for each month when the data quality check was evaluated.|:material-check-bold:|







