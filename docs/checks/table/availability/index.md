---
title: table level availability data quality checks
---
# table level availability data quality checks

This is a list of availability table data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## table-level availability checks
Checks whether the table is accessible and available for use.

### [table availability](./table-availability.md)
A table-level check that ensures a query can be successfully executed on a table without server errors. It also verifies that the table exists and is accessible (queryable).
 The actual value (the result of the check) indicates the number of failures. If the table is accessible and a simple query can be executed without errors, the result will be 0.0.
 A sensor result (the actual value) of 1.0 indicates that there is a failure. Any value greater than 1.0 is stored only in the check result table and represents the number of consecutive failures in the following days.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_table_availability`</span>](./table-availability.md#profile-table-availability)|Table availability|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies availability of a table in a monitored database using a simple query.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_table_availability`</span>](./table-availability.md#daily-table-availability)|Table availability|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_table_availability`</span>](./table-availability.md#monthly-table-availability)|Table availability|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each month when the data quality check was evaluated.|:material-check-bold:|







