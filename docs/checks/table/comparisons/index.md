---
title: List of table level comparisons data quality checks
---
# List of table level comparisons data quality checks

This is a list of comparisons table data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## table-level comparisons checks
Compares the table (the row count, and the column count) to another table in a different data source.

### [row count match](./row-count-match.md)
Table level comparison check that compares the row count of the current (parent) table with the row count of the reference table.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count_match`</span>](./row-count-match.md#profile-row-count-match)|Maximum percentage of difference between row count of compared tables|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_row_count_match`</span>](./row-count-match.md#daily-row-count-match)|Maximum percentage of difference between row count of compared tables|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_row_count_match`</span>](./row-count-match.md#monthly-row-count-match)|Maximum percentage of difference between row count of compared tables|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_row_count_match`</span>](./row-count-match.md#daily-partition-row-count-match)|Maximum percentage of difference between row count of compared tables|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause on the time period (the daily partition) and all other data grouping columns. Stores the most recent captured value for each daily partition that was analyzed.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_row_count_match`</span>](./row-count-match.md#monthly-partition-row-count-match)|Maximum percentage of difference between row count of compared tables|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the row count of the tested (parent) table matches the row count of the reference table, for each monthly partition (grouping rows by the time period, truncated to the month). Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each monthly partition and optionally data groups.|:material-check-bold:|



### [column count match](./column-count-match.md)
Table level comparison check that compares the column count of the current (parent) table with the column count of the reference table.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_column_count_match`</span>](./column-count-match.md#profile-column-count-match)|Maximum percentage of difference between column count of compared tables|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_column_count_match`</span>](./column-count-match.md#daily-column-count-match)|Maximum percentage of difference between column count of compared tables|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_column_count_match`</span>](./column-count-match.md#monthly-column-count-match)|Maximum percentage of difference between column count of compared tables|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|







