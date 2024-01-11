# Checks/table/volume

This is a list of volume table data quality checks supported by DQOps and a brief description of what they do.





## **volume**
Evaluates the overall quality of the table by verifying the number of rows.

| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_row_count](./row-count.md#profile-row-count)|profiling|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|standard|
|[daily_row_count](./row-count.md#daily-row-count)|monitoring|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.Stores the most recent captured row count value for each day when the row count was evaluated.|standard|
|[monthly_row_count](./row-count.md#monthly-row-count)|monitoring|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.Stores the most recent captured row count value for each month when the row count was evaluated.|standard|
|[daily_partition_row_count](./row-count.md#daily-partition-row-count)|partitioned|Verifies that each daily partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|standard|
|[monthly_partition_row_count](./row-count.md#monthly-partition-row-count)|partitioned|Verifies that each monthly partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_row_count_anomaly](./row-count-anomaly.md#profile-row-count-anomaly)|profiling|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.|advanced|
|[daily_row_count_anomaly](./row-count-anomaly.md#daily-row-count-anomaly)|monitoring|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during the last 90 days.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_row_count_change](./row-count-change.md#profile-row-count-change)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|advanced|
|[daily_row_count_change](./row-count-change.md#daily-row-count-change)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last day with a row count captured.|advanced|
|[monthly_row_count_change](./row-count-change.md#monthly-row-count-change)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last month.|advanced|
|[daily_partition_row_count_change](./row-count-change.md#daily-partition-row-count-change)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|advanced|
|[monthly_partition_row_count_change](./row-count-change.md#monthly-partition-row-count-change)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_row_count_change_1_day](./row-count-change-1-day.md#profile-row-count-change-1-day)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|advanced|
|[daily_row_count_change_1_day](./row-count-change-1-day.md#daily-row-count-change-1-day)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|advanced|
|[daily_partition_row_count_change_1_day](./row-count-change-1-day.md#daily-partition-row-count-change-1-day)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_row_count_change_7_days](./row-count-change-7-days.md#profile-row-count-change-7-days)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|advanced|
|[daily_row_count_change_7_days](./row-count-change-7-days.md#daily-row-count-change-7-days)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from the last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|advanced|
|[daily_partition_row_count_change_7_days](./row-count-change-7-days.md#daily-partition-row-count-change-7-days)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_row_count_change_30_days](./row-count-change-30-days.md#profile-row-count-change-30-days)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|advanced|
|[daily_row_count_change_30_days](./row-count-change-30-days.md#daily-row-count-change-30-days)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from the last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|advanced|
|[daily_partition_row_count_change_30_days](./row-count-change-30-days.md#daily-partition-row-count-change-30-days)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[daily_partition_row_count_anomaly](./row-count-anomaly.md#daily-partition-row-count-anomaly)|partitioned|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 90 days.|advanced|







