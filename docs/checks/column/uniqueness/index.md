---
title: List of column level uniqueness data quality checks
---
# List of column level uniqueness data quality checks

This is a list of uniqueness column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level uniqueness checks
Counts the number or percent of duplicate or unique values in a column.

### [distinct count](./distinct-count.md)
This check counts distinct values and verifies if the distinct count is within an accepted range. It raises a data quality issue when the distinct count is below or above the accepted range.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_distinct_count`</span>](./distinct-count.md#profile-distinct-count)|Verify that the count of distinct values is in the expected range|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the number of distinct values stays within an accepted range.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_distinct_count`</span>](./distinct-count.md#daily-distinct-count)|Verify that the count of distinct values is in the expected range|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of distinct values stays within an accepted range. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_distinct_count`</span>](./distinct-count.md#monthly-distinct-count)|Verify that the count of distinct values is in the expected range|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies  that the number of distinct values stays within an accepted range. Stores the most recent check result for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_distinct_count`</span>](./distinct-count.md#daily-partition-distinct-count)|Verify that the count of distinct values is in the expected range|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies  that the number of distinct values stays within an accepted range. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_distinct_count`</span>](./distinct-count.md#monthly-partition-distinct-count)|Verify that the count of distinct values is in the expected range|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies  that the number of distinct values stays within an accepted range. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [distinct percent](./distinct-percent.md)
This check measures the percentage of distinct values in all non-null values. It verifies that the percentage of distinct values meets a minimum and maximum values.
 The default value of 100% distinct values ensures the column has no duplicate values.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_distinct_percent`</span>](./distinct-percent.md#profile-distinct-percent)|The minimum ratio of distinct values to the count of non null values (detect duplicate values)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_distinct_percent`</span>](./distinct-percent.md#daily-distinct-percent)|The minimum ratio of distinct values to the count of non null values (detect duplicate values)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_distinct_percent`</span>](./distinct-percent.md#monthly-distinct-percent)|The minimum ratio of distinct values to the count of non null values (detect duplicate values)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent check result for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_distinct_percent`</span>](./distinct-percent.md#daily-partition-distinct-percent)|The minimum ratio of distinct values to the count of non null values (detect duplicate values)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_distinct_percent`</span>](./distinct-percent.md#monthly-partition-distinct-percent)|The minimum ratio of distinct values to the count of non null values (detect duplicate values)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [duplicate count](./duplicate-count.md)
This check counts duplicate values. It raises a data quality issue when the number of duplicates is above a minimum accepted value.
 The default configuration detects duplicate values by enforcing that the *min_count* of duplicates is zero.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_duplicate_count`</span>](./duplicate-count.md#profile-duplicate-count)|Maximum count of duplicate values|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_duplicate_count`</span>](./duplicate-count.md#daily-duplicate-count)|Maximum count of duplicate values|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_duplicate_count`</span>](./duplicate-count.md#monthly-duplicate-count)|Maximum count of duplicate values|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent check result for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_duplicate_count`</span>](./duplicate-count.md#daily-partition-duplicate-count)|Maximum count of duplicate values|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_duplicate_count`</span>](./duplicate-count.md#monthly-partition-duplicate-count)|Maximum count of duplicate values|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [duplicate percent](./duplicate-percent.md)
This check measures the percentage of duplicate values in all non-null values. It raises a data quality issue when the percentage of duplicates is above an accepted threshold.
 The default threshold is 0% duplicate values.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_duplicate_percent`</span>](./duplicate-percent.md#profile-duplicate-percent)|Maximum percentage of duplicate values|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.| |
|[<span class="no-wrap-code">`daily_duplicate_percent`</span>](./duplicate-percent.md#daily-duplicate-percent)|Maximum percentage of duplicate values|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_duplicate_percent`</span>](./duplicate-percent.md#monthly-duplicate-percent)|Maximum percentage of duplicate values|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_duplicate_percent`</span>](./duplicate-percent.md#daily-partition-duplicate-percent)|Maximum percentage of duplicate values|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_duplicate_percent`</span>](./duplicate-percent.md#monthly-partition-duplicate-percent)|Maximum percentage of duplicate values|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Stores a separate data quality check result for each monthly partition.| |



### [distinct count anomaly](./distinct-count-anomaly.md)
This check monitors the count of distinct values and detects anomalies in the changes of the distinct count. It monitors a 90-day time window.
 The check is configured by setting a desired percentage of anomalies to identify as data quality issues.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_distinct_count_anomaly`</span>](./distinct-count-anomaly.md#profile-distinct-count-anomaly)|Abnormal change in the number of distinct values. Measured as a percentile of anomalous measures.|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_distinct_count_anomaly`</span>](./distinct-count-anomaly.md#daily-distinct-count-anomaly)|Abnormal change in the number of distinct values. Measured as a percentile of anomalous measures.|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_distinct_count_anomaly`</span>](./distinct-count-anomaly.md#daily-partition-distinct-count-anomaly)|Abnormal change in the number of distinct values. Measured as a percentile of anomalous measures.|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.| |



### [distinct percent anomaly](./distinct-percent-anomaly.md)
This check monitors the percentage of distinct values and detects anomalies in the changes in this percentage. It monitors a 90-day time window.
 The check is configured by setting a desired percentage of anomalies to identify as data quality issues.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_distinct_percent_anomaly`</span>](./distinct-percent-anomaly.md#profile-distinct-percent-anomaly)|Abnormal change in the percentage of distinct values. Measured as a percentile of anomalous measures.|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.| |
|[<span class="no-wrap-code">`daily_distinct_percent_anomaly`</span>](./distinct-percent-anomaly.md#daily-distinct-percent-anomaly)|Abnormal change in the percentage of distinct values. Measured as a percentile of anomalous measures.|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.| |
|[<span class="no-wrap-code">`daily_partition_distinct_percent_anomaly`</span>](./distinct-percent-anomaly.md#daily-partition-distinct-percent-anomaly)|Abnormal change in the percentage of distinct values. Measured as a percentile of anomalous measures.|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.| |



### [distinct count change](./distinct-count-change.md)
This check monitors the count of distinct values and compares it to the last known value. It raises a data quality issue when the change exceeds an accepted threshold.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_distinct_count_change`</span>](./distinct-count-change.md#profile-distinct-count-change)|Maximum relative change in the count of distinct values since the last known value|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_distinct_count_change`</span>](./distinct-count-change.md#daily-distinct-count-change)|Maximum relative change in the count of distinct values since the last known value|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`monthly_distinct_count_change`</span>](./distinct-count-change.md#monthly-distinct-count-change)|Maximum relative change in the count of distinct values since the last known value|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_partition_distinct_count_change`</span>](./distinct-count-change.md#daily-partition-distinct-count-change)|Maximum relative change in the count of distinct values since the last known value|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`monthly_partition_distinct_count_change`</span>](./distinct-count-change.md#monthly-partition-distinct-count-change)|Maximum relative change in the count of distinct values since the last known value|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.| |



### [distinct count change 1 day](./distinct-count-change-1-day.md)
This check monitors the count of distinct values and compares it to the measure from the previous day. It raises a data quality issue when the change exceeds an accepted threshold.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_distinct_count_change_1_day`</span>](./distinct-count-change-1-day.md#profile-distinct-count-change-1-day)|Maximum relative change in the count of distinct values vs 1 day ago|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.| |
|[<span class="no-wrap-code">`daily_distinct_count_change_1_day`</span>](./distinct-count-change-1-day.md#daily-distinct-count-change-1-day)|Maximum relative change in the count of distinct values vs 1 day ago|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.| |
|[<span class="no-wrap-code">`daily_partition_distinct_count_change_1_day`</span>](./distinct-count-change-1-day.md#daily-partition-distinct-count-change-1-day)|Maximum relative change in the count of distinct values vs 1 day ago|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.| |



### [distinct count change 7 days](./distinct-count-change-7-days.md)
This check monitors the count of distinct values and compares it to the measure seven days ago to overcome the weekly seasonability impact.
 It raises a data quality issue when the change exceeds an accepted threshold.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_distinct_count_change_7_days`</span>](./distinct-count-change-7-days.md#profile-distinct-count-change-7-days)|Maximum relative change in the count of distinct values vs 7 days ago|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.| |
|[<span class="no-wrap-code">`daily_distinct_count_change_7_days`</span>](./distinct-count-change-7-days.md#daily-distinct-count-change-7-days)|Maximum relative change in the count of distinct values vs 7 days ago|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.| |
|[<span class="no-wrap-code">`daily_partition_distinct_count_change_7_days`</span>](./distinct-count-change-7-days.md#daily-partition-distinct-count-change-7-days)|Maximum relative change in the count of distinct values vs 7 days ago|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from the last week.| |



### [distinct count change 30 days](./distinct-count-change-30-days.md)
This check monitors the count of distinct values and compares it to the measure thirty days ago to overcome the monthly seasonability impact.
 It raises a data quality issue when the change exceeds an accepted threshold.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_distinct_count_change_30_days`</span>](./distinct-count-change-30-days.md#profile-distinct-count-change-30-days)|Maximum relative change in the count of distinct values vs 30 days ago|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.| |
|[<span class="no-wrap-code">`daily_distinct_count_change_30_days`</span>](./distinct-count-change-30-days.md#daily-distinct-count-change-30-days)|Maximum relative change in the count of distinct values vs 30 days ago|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.| |
|[<span class="no-wrap-code">`daily_partition_distinct_count_change_30_days`</span>](./distinct-count-change-30-days.md#daily-partition-distinct-count-change-30-days)|Maximum relative change in the count of distinct values vs 30 days ago|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from the last month.| |



### [distinct percent change](./distinct-percent-change.md)
This check monitors the percentage of distinct values and compares it to the last known value. It raises a data quality issue when the change exceeds an accepted threshold.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_distinct_percent_change`</span>](./distinct-percent-change.md#profile-distinct-percent-change)|Maximum relative change in the percentage of distinct values since the last known value|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_distinct_percent_change`</span>](./distinct-percent-change.md#daily-distinct-percent-change)|Maximum relative change in the percentage of distinct values since the last known value|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`monthly_distinct_percent_change`</span>](./distinct-percent-change.md#monthly-distinct-percent-change)|Maximum relative change in the percentage of distinct values since the last known value|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`daily_partition_distinct_percent_change`</span>](./distinct-percent-change.md#daily-partition-distinct-percent-change)|Maximum relative change in the percentage of distinct values since the last known value|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.| |
|[<span class="no-wrap-code">`monthly_partition_distinct_percent_change`</span>](./distinct-percent-change.md#monthly-partition-distinct-percent-change)|Maximum relative change in the percentage of distinct values since the last known value|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.| |



### [distinct percent change 1 day](./distinct-percent-change-1-day.md)
This check monitors the percentage of distinct values and compares it to the measure from the previous day. It raises a data quality issue when the change exceeds an accepted threshold.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_distinct_percent_change_1_day`</span>](./distinct-percent-change-1-day.md#profile-distinct-percent-change-1-day)|Maximum relative change in the percentage of distinct values vs 1 day ago|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.| |
|[<span class="no-wrap-code">`daily_distinct_percent_change_1_day`</span>](./distinct-percent-change-1-day.md#daily-distinct-percent-change-1-day)|Maximum relative change in the percentage of distinct values vs 1 day ago|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.| |
|[<span class="no-wrap-code">`daily_partition_distinct_percent_change_1_day`</span>](./distinct-percent-change-1-day.md#daily-partition-distinct-percent-change-1-day)|Maximum relative change in the percentage of distinct values vs 1 day ago|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.| |



### [distinct percent change 7 days](./distinct-percent-change-7-days.md)
This check monitors the percentage of distinct values and compares it to the measure seven days ago to overcome the weekly seasonability impact.
 It raises a data quality issue when the change exceeds an accepted threshold.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_distinct_percent_change_7_days`</span>](./distinct-percent-change-7-days.md#profile-distinct-percent-change-7-days)|Maximum relative change in the percentage of distinct values vs 7 days sago|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.| |
|[<span class="no-wrap-code">`daily_distinct_percent_change_7_days`</span>](./distinct-percent-change-7-days.md#daily-distinct-percent-change-7-days)|Maximum relative change in the percentage of distinct values vs 7 days sago|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.| |
|[<span class="no-wrap-code">`daily_partition_distinct_percent_change_7_days`</span>](./distinct-percent-change-7-days.md#daily-partition-distinct-percent-change-7-days)|Maximum relative change in the percentage of distinct values vs 7 days sago|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from the last week.| |



### [distinct percent change 30 days](./distinct-percent-change-30-days.md)
This check monitors the percentage of distinct values and compares it to the measure thirty days ago to overcome the monthly seasonability impact.
 It raises a data quality issue when the change exceeds an accepted threshold.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_distinct_percent_change_30_days`</span>](./distinct-percent-change-30-days.md#profile-distinct-percent-change-30-days)|Maximum relative change in the percentage of distinct values vs 30 days ago|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.| |
|[<span class="no-wrap-code">`daily_distinct_percent_change_30_days`</span>](./distinct-percent-change-30-days.md#daily-distinct-percent-change-30-days)|Maximum relative change in the percentage of distinct values vs 30 days ago|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.| |
|[<span class="no-wrap-code">`daily_partition_distinct_percent_change_30_days`</span>](./distinct-percent-change-30-days.md#daily-partition-distinct-percent-change-30-days)|Maximum relative change in the percentage of distinct values vs 30 days ago|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from the last month.| |







