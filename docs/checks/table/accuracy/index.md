# table level accuracy data quality checks

This is a list of accuracy table data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **accuracy**
Compares the tested table with another (reference) table.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_total_row_count_match_percent](./total-row-count-match-percent.md#profile-total-row-count-match-percent)|profiling|Verifies that the total row count of the tested table matches the total row count of another (reference) table.|standard|
|[daily_total_row_count_match_percent](./total-row-count-match-percent.md#daily-total-row-count-match-percent)|monitoring|Verifies the total ow count of a tested table and compares it to a row count of a reference table. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_total_row_count_match_percent](./total-row-count-match-percent.md#monthly-total-row-count-match-percent)|monitoring|Verifies the total row count of a tested table and compares it to a row count of a reference table. Stores the most recent check result for each month when the data quality check was evaluated.|standard|







