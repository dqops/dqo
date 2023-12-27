# Checks/table/accuracy

**This is a list of accuracy table data quality checks supported by DQOps and a brief description of what they do.**





## **accuracy**  
Compares the tested table with another (reference) table.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_total_row_count_match_percent](./table/accuracy/total-row-count-match-percent/#profile-total-row-count-match-percent)|profiling|Verifies that the total row count of the tested table matches the total row count of another (reference) table.|
|[daily_total_row_count_match_percent](./table/accuracy/total-row-count-match-percent/#daily-total-row-count-match-percent)|monitoring|Verifies the total ow count of a tested table and compares it to a row count of a reference table. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_total_row_count_match_percent](./table/accuracy/total-row-count-match-percent/#monthly-total-row-count-match-percent)|monitoring|Verifies the total row count of a tested table and compares it to a row count of a reference table. Stores the most recent row count for each month when the data quality check was evaluated.|





