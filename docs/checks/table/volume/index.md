# Checks/table/volume

**This is a list of volume table data quality checks supported by DQOps and a brief description of what they do.**





## **volume**
Evaluates the overall quality of the table by verifying the number of rows.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count](../row-count.md#profile-row-count)|profiling|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|
|[daily_row_count](../row-count.md#daily-row-count)|monitoring|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.Stores the most recent captured row count value for each day when the row count was evaluated.|
|[monthly_row_count](../row-count.md#monthly-row-count)|monitoring|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.Stores the most recent captured row count value for each month when the row count was evaluated.|
|[daily_partition_row_count](../row-count.md#daily-partition-row-count)|partitioned|Verifies that each daily partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|
|[monthly_partition_row_count](../row-count.md#monthly-partition-row-count)|partitioned|Verifies that each monthly partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_anomaly_differencing_30_days](../row-count-anomaly-differencing-30-days.md#profile-row-count-anomaly-differencing-30-days)|profiling|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.|
|[daily_row_count_anomaly_differencing_30_days](../row-count-anomaly-differencing-30-days.md#daily-row-count-anomaly-differencing-30-days)|monitoring|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_anomaly_differencing](../row-count-anomaly-differencing.md#profile-row-count-anomaly-differencing)|profiling|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.|
|[daily_row_count_anomaly_differencing](../row-count-anomaly-differencing.md#daily-row-count-anomaly-differencing)|monitoring|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_change](../row-count-change.md#profile-row-count-change)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|
|[daily_row_count_change](../row-count-change.md#daily-row-count-change)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last day with a row count captured.|
|[monthly_row_count_change](../row-count-change.md#monthly-row-count-change)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last month.|
|[daily_partition_row_count_change](../row-count-change.md#daily-partition-row-count-change)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|
|[monthly_partition_row_count_change](../row-count-change.md#monthly-partition-row-count-change)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_change_yesterday](../row-count-change-yesterday.md#profile-row-count-change-yesterday)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|
|[daily_row_count_change_yesterday](../row-count-change-yesterday.md#daily-row-count-change-yesterday)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|
|[daily_partition_row_count_change_yesterday](../row-count-change-yesterday.md#daily-partition-row-count-change-yesterday)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_change_7_days](../row-count-change-7-days.md#profile-row-count-change-7-days)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|
|[daily_row_count_change_7_days](../row-count-change-7-days.md#daily-row-count-change-7-days)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|
|[daily_partition_row_count_change_7_days](../row-count-change-7-days.md#daily-partition-row-count-change-7-days)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_row_count_change_30_days](../row-count-change-30-days.md#profile-row-count-change-30-days)|profiling|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|
|[daily_row_count_change_30_days](../row-count-change-30-days.md#daily-row-count-change-30-days)|monitoring|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|
|[daily_partition_row_count_change_30_days](../row-count-change-30-days.md#daily-partition-row-count-change-30-days)|partitioned|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_row_count_anomaly_stationary_30_days](../row-count-anomaly-stationary-30-days.md#daily-partition-row-count-anomaly-stationary-30-days)|partitioned|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 30 days.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_row_count_anomaly_stationary](../row-count-anomaly-stationary.md#daily-partition-row-count-anomaly-stationary)|partitioned|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 90 days.|





