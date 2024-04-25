---
title: table level accuracy data quality checks
---
# table level accuracy data quality checks

This is a list of accuracy table data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## table-level accuracy checks
Compares the tested table with another (reference) table.

### [total row count match percent](./total-row-count-match-percent.md)
A table-level check that compares the row count of the current (tested) table with the row count of another table that is referenced. This check ensures that the difference between the row counts is below the maximum accepted percentage of difference.
 This check runs an SQL query with an INNER JOIN clause to join another (referenced) table that must be defined in the same database.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_total_row_count_match_percent`</span>](./total-row-count-match-percent.md#profile-total-row-count-match-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the total row count of the tested table matches the total row count of another (reference) table.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_total_row_count_match_percent`</span>](./total-row-count-match-percent.md#daily-total-row-count-match-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies the total ow count of a tested table and compares it to a row count of a reference table. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_total_row_count_match_percent`</span>](./total-row-count-match-percent.md#monthly-total-row-count-match-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies the total row count of a tested table and compares it to a row count of a reference table. Stores the most recent check result for each month when the data quality check was evaluated.|:material-check-bold:|







