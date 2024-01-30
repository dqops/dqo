# table level volume data quality checks

This is a list of volume table data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## table-level volume checks
Evaluates the overall quality of the table by verifying the number of rows.

### [row count](./row-count.md)
A table-level check that ensures that the tested table has at least a minimum accepted number of rows.
 The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count`</span>](./row-count.md#profile-row-count)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_row_count`</span>](./row-count.md#daily-row-count)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.Stores the most recent captured row count value for each day when the row count was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_row_count`</span>](./row-count.md#monthly-row-count)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.Stores the most recent captured row count value for each month when the row count was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_row_count`</span>](./row-count.md#daily-partition-row-count)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that each daily partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_row_count`</span>](./row-count.md#monthly-partition-row-count)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that each monthly partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|:material-check-bold:|



### [row count anomaly](./row-count-anomaly.md)
A table-level check that ensures that the row count is within a two-tailed percentile from measurements made during the last 90 days.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count_anomaly`</span>](./row-count-anomaly.md#profile-row-count-anomaly)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.| |
|[<span class="no-wrap-code">`daily_row_count_anomaly`</span>](./row-count-anomaly.md#daily-row-count-anomaly)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during the last 90 days.| |
|[<span class="no-wrap-code">`daily_partition_row_count_anomaly`</span>](./row-count-anomaly.md#daily-partition-row-count-anomaly)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the total row count of the tested table is within a percentile from measurements made during the last 90 days.| |



### [row count change](./row-count-change.md)
A table-level check that ensures that the row count changed by a fixed rate since the last readout.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count_change`</span>](./row-count-change.md#profile-row-count-change)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_row_count_change`</span>](./row-count-change.md#daily-row-count-change)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last day with a row count captured.| |
|[<span class="no-wrap-code">`monthly_row_count_change`</span>](./row-count-change.md#monthly-row-count-change)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last month.| |
|[<span class="no-wrap-code">`daily_partition_row_count_change`</span>](./row-count-change.md#daily-partition-row-count-change)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`monthly_partition_row_count_change`</span>](./row-count-change.md#monthly-partition-row-count-change)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.| |



### [row count change 1 day](./row-count-change-1-day.md)
A table-level check that ensures that the row count changed by a fixed rate since the last readout from yesterday.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count_change_1_day`</span>](./row-count-change-1-day.md#profile-row-count-change-1-day)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.| |
|[<span class="no-wrap-code">`daily_row_count_change_1_day`</span>](./row-count-change-1-day.md#daily-row-count-change-1-day)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.| |
|[<span class="no-wrap-code">`daily_partition_row_count_change_1_day`</span>](./row-count-change-1-day.md#daily-partition-row-count-change-1-day)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.| |



### [row count change 7 days](./row-count-change-7-days.md)
A table-level check that ensures that the row count changed by a fixed rate since the last readout from last week.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count_change_7_days`</span>](./row-count-change-7-days.md#profile-row-count-change-7-days)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.| |
|[<span class="no-wrap-code">`daily_row_count_change_7_days`</span>](./row-count-change-7-days.md#daily-row-count-change-7-days)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from the last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.| |
|[<span class="no-wrap-code">`daily_partition_row_count_change_7_days`</span>](./row-count-change-7-days.md#daily-partition-row-count-change-7-days)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.| |



### [row count change 30 days](./row-count-change-30-days.md)
A table-level check that ensures that the row count changed by a fixed rate since the last readout from last month.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count_change_30_days`</span>](./row-count-change-30-days.md#profile-row-count-change-30-days)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.| |
|[<span class="no-wrap-code">`daily_row_count_change_30_days`</span>](./row-count-change-30-days.md#daily-row-count-change-30-days)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from the last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.| |
|[<span class="no-wrap-code">`daily_partition_row_count_change_30_days`</span>](./row-count-change-30-days.md#daily-partition-row-count-change-30-days)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.| |







