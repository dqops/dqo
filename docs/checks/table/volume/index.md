# table level volume data quality checks

This is a list of volume table data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## table-level volume checks
Evaluates the overall quality of the table by verifying the number of rows.

### [row count](./row-count.md)
This check detects empty or too-small tables. It captures the row count of a tested table.
 This check raises a data quality issue when the row count is below a minimum accepted value.
 The default value of the rule parameter **min_count** is 1 (row), which detects empty tables.
 When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count`</span>](./row-count.md#profile-row-count)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which ensures that the table is not empty.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_row_count`</span>](./row-count.md#daily-row-count)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which ensures that the table is not empty. Stores the most recent captured row count value for each day when the row count was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_row_count`</span>](./row-count.md#monthly-row-count)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which ensures that the table is not empty. Stores the most recent captured row count value for each month when the row count was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_row_count`</span>](./row-count.md#daily-partition-row-count)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that each daily partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which ensures that the partition is not empty.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_row_count`</span>](./row-count.md#monthly-partition-row-count)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that each monthly partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which ensures that the partition is not empty.|:material-check-bold:|



### [row count anomaly](./row-count-anomaly.md)
This check detects anomalies in the day-to-day changes to the table volume (the row count).
 It captures the row count for each day and compares the row count change (increase or decrease) since the previous day.
 This check raises a data quality issue when the change is in the top **anomaly_percent** percentage of the biggest day-to-day changes.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count_anomaly`</span>](./row-count-anomaly.md#profile-row-count-anomaly)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects when the row count has changed too much since the previous day. It uses time series anomaly detection to find the biggest volume changes during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_row_count_anomaly`</span>](./row-count-anomaly.md#daily-row-count-anomaly)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects when the row count has changed too much since the previous day. It uses time series anomaly detection to find the biggest volume changes during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_row_count_anomaly`</span>](./row-count-anomaly.md#daily-partition-row-count-anomaly)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects outstanding partitions whose volume (the row count) differs too much from the average daily partition size. It uses time series anomaly detection to find the outliers in the partition volume during the last 90 days.|:material-check-bold:|



### [row count change](./row-count-change.md)
This check compares the current table volume (the row count) to the last known row count.
 It raises a data quality issue when the change in row count (increase or decrease) exceeds a maximum accepted percentage of change.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count_change`</span>](./row-count-change.md#profile-row-count-change)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects when the volume&#x27;s (row count) change since the last known row count exceeds the maximum accepted change percentage.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_row_count_change`</span>](./row-count-change.md#daily-row-count-change)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects when the volume&#x27;s (row count) change since the last known row count exceeds the maximum accepted change percentage.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_row_count_change`</span>](./row-count-change.md#monthly-row-count-change)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects when the volume (row count) changes since the last known row count from a previous month exceeds the maximum accepted change percentage.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_row_count_change`</span>](./row-count-change.md#daily-partition-row-count-change)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects when the partition&#x27;s volume (row count) change between the current daily partition and the previous partition exceeds the maximum accepted change percentage.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_row_count_change`</span>](./row-count-change.md#monthly-partition-row-count-change)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects when the partition&#x27;s volume (row count) change between the current monthly partition and the previous partition exceeds the maximum accepted change percentage.|:material-check-bold:|



### [row count change 1 day](./row-count-change-1-day.md)
This check compares the current table volume (the row count) to the row count from the previous day.
 It raises a data quality issue when the change in row count (increase or decrease) since yesterday exceeds a maximum accepted percentage of change.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count_change_1_day`</span>](./row-count-change-1-day.md#profile-row-count-change-1-day)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects when the volume&#x27;s change (increase or decrease of the row count) since the previous day exceeds the maximum accepted change percentage.| |
|[<span class="no-wrap-code">`daily_row_count_change_1_day`</span>](./row-count-change-1-day.md#daily-row-count-change-1-day)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects when the volume&#x27;s change (increase or decrease of the row count) since the previous day exceeds the maximum accepted change percentage. | |
|[<span class="no-wrap-code">`daily_partition_row_count_change_1_day`</span>](./row-count-change-1-day.md#daily-partition-row-count-change-1-day)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects when the partition volume change (increase or decrease of the row count) since yesterday&#x27;s daily partition exceeds the maximum accepted change percentage. | |



### [row count change 7 days](./row-count-change-7-days.md)
This check compares the current table volume (the row count) to the row count seven days ago.
 This check compares the table volume to a value a week ago to overcome weekly seasonability and to compare Mondays to Mondays, Tuesdays to Tuesdays, etc.
 It raises a data quality issue when the change in row count (increase or decrease) since a week ago exceeds a maximum accepted percentage of change.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count_change_7_days`</span>](./row-count-change-7-days.md#profile-row-count-change-7-days)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|This check verifies that the percentage of change in the table&#x27;s volume (row count) since seven days ago is below the maximum accepted percentage. Verifying a volume change since a value a week ago overcomes the effect of weekly seasonability. | |
|[<span class="no-wrap-code">`daily_row_count_change_7_days`</span>](./row-count-change-7-days.md#daily-row-count-change-7-days)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|This check verifies that the percentage of change in the table&#x27;s volume (row count) since seven days ago is below the maximum accepted percentage. Verifying a volume change since a value a week ago overcomes the effect of weekly seasonability.| |
|[<span class="no-wrap-code">`daily_partition_row_count_change_7_days`</span>](./row-count-change-7-days.md#daily-partition-row-count-change-7-days)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|This check verifies that the percentage of change in the partition&#x27;s volume (row count) since seven days ago is below the maximum accepted percentage. Verifying a volume change since a value a week ago overcomes the effect of weekly seasonability.| |



### [row count change 30 days](./row-count-change-30-days.md)
This check compares the current table volume (the row count) to the row count 30 days ago.
 This check compares the table volume to a month ago value to overcome monthly seasonability.
 It raises a data quality issue when the change in row count (increase or decrease) since a value 30 days ago exceeds a maximum accepted percentage of change.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_row_count_change_30_days`</span>](./row-count-change-30-days.md#profile-row-count-change-30-days)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|This check verifies that the percentage of change in the table&#x27;s volume (row count) since thirty days ago is below the maximum accepted percentage. Comparing the current row count to a value 30 days ago overcomes the effect of monthly seasonability.| |
|[<span class="no-wrap-code">`daily_row_count_change_30_days`</span>](./row-count-change-30-days.md#daily-row-count-change-30-days)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|This check verifies that the percentage of change in the table&#x27;s volume (row count) since thirty days ago is below the maximum accepted percentage. Comparing the current row count to a value 30 days ago overcomes the effect of monthly seasonability.| |
|[<span class="no-wrap-code">`daily_partition_row_count_change_30_days`</span>](./row-count-change-30-days.md#daily-partition-row-count-change-30-days)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|This check verifies that the percentage of change in the partition&#x27;s volume (row count) since thirty days ago is below the maximum accepted percentage. Comparing the current row count to a value 30 days ago overcomes the effect of monthly seasonability.| |







