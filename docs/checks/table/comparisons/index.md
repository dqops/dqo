# Checks/table/comparisons

**This is a list of comparisons table data quality checks supported by DQOps and a brief description of what they do.**





## **comparisons**  


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_match](./table/comparisons/row-count-match/#profile-row-count-match)|profiling|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause.|
|[daily_row_count_match](./table/comparisons/row-count-match/#daily-row-count-match)|monitoring|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_row_count_match](./table/comparisons/row-count-match/#monthly-row-count-match)|monitoring|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each month when the data quality check was evaluated.|
|[daily_partition_row_count_match](./table/comparisons/row-count-match/#daily-partition-row-count-match)|partitioned|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause on the time period (the daily partition) and all other data grouping columns. Stores the most recent captured value for each daily partition that was analyzed.|
|[monthly_partition_row_count_match](./table/comparisons/row-count-match/#monthly-partition-row-count-match)|partitioned|Verifies that the row count of the tested (parent) table matches the row count of the reference table, for each monthly partition (grouping rows by the time period, truncated to the month). Compares each group of data with a GROUP BY clause. Stores the most recent captured value for each monthly partition and optionally data groups.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_count_match](./table/comparisons/column-count-match/#profile-column-count-match)|profiling|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping.|
|[daily_column_count_match](./table/comparisons/column-count-match/#daily-column-count-match)|monitoring|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_column_count_match](./table/comparisons/column-count-match/#monthly-column-count-match)|monitoring|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping. Stores the most recent captured value for each month when the data quality check was evaluated.|





