# column level uniqueness data quality checks

This is a list of uniqueness column data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **uniqueness**
Counts the number or percent of duplicate or unique values in a column.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_count`</span>](./distinct-count.md#profile-distinct-count)|profiling|Verifies that the number of distinct values in a column does not fall below the minimum accepted count.|**|
|[<span class="no-wrap-code">`daily_distinct_count`</span>](./distinct-count.md#daily-distinct-count)|monitoring|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_distinct_count`</span>](./distinct-count.md#monthly-distinct-count)|monitoring|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_count`</span>](./distinct-count.md#daily-partition-distinct-count)|partitioned|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_distinct_count`</span>](./distinct-count.md#monthly-partition-distinct-count)|partitioned|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_percent`</span>](./distinct-percent.md#profile-distinct-percent)|profiling|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent.|**|
|[<span class="no-wrap-code">`daily_distinct_percent`</span>](./distinct-percent.md#daily-distinct-percent)|monitoring|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_distinct_percent`</span>](./distinct-percent.md#monthly-distinct-percent)|monitoring|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_percent`</span>](./distinct-percent.md#daily-partition-distinct-percent)|partitioned|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_distinct_percent`</span>](./distinct-percent.md#monthly-partition-distinct-percent)|partitioned|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_duplicate_count`</span>](./duplicate-count.md#profile-duplicate-count)|profiling|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.|**|
|[<span class="no-wrap-code">`daily_duplicate_count`</span>](./duplicate-count.md#daily-duplicate-count)|monitoring|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_duplicate_count`</span>](./duplicate-count.md#monthly-duplicate-count)|monitoring|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_duplicate_count`</span>](./duplicate-count.md#daily-partition-duplicate-count)|partitioned|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_duplicate_count`</span>](./duplicate-count.md#monthly-partition-duplicate-count)|partitioned|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_duplicate_percent`</span>](./duplicate-percent.md#profile-duplicate-percent)|profiling|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.|**|
|[<span class="no-wrap-code">`daily_duplicate_percent`</span>](./duplicate-percent.md#daily-duplicate-percent)|monitoring|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_duplicate_percent`</span>](./duplicate-percent.md#monthly-duplicate-percent)|monitoring|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`daily_partition_duplicate_percent`</span>](./duplicate-percent.md#daily-partition-duplicate-percent)|partitioned|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Stores a separate data quality check result for each daily partition.|**|
|[<span class="no-wrap-code">`monthly_partition_duplicate_percent`</span>](./duplicate-percent.md#monthly-partition-duplicate-percent)|partitioned|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Stores a separate data quality check result for each monthly partition.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_count_anomaly`</span>](./distinct-count-anomaly.md#profile-distinct-count-anomaly)|profiling|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_distinct_count_anomaly`</span>](./distinct-count-anomaly.md#daily-distinct-count-anomaly)|monitoring|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_count_anomaly`</span>](./distinct-count-anomaly.md#daily-partition-distinct-count-anomaly)|partitioned|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_percent_anomaly`</span>](./distinct-percent-anomaly.md#profile-distinct-percent-anomaly)|profiling|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_distinct_percent_anomaly`</span>](./distinct-percent-anomaly.md#daily-distinct-percent-anomaly)|monitoring|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_percent_anomaly`</span>](./distinct-percent-anomaly.md#daily-partition-distinct-percent-anomaly)|partitioned|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_count_change`</span>](./distinct-count-change.md#profile-distinct-count-change)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_distinct_count_change`</span>](./distinct-count-change.md#daily-distinct-count-change)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_distinct_count_change`</span>](./distinct-count-change.md#monthly-distinct-count-change)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_count_change`</span>](./distinct-count-change.md#daily-partition-distinct-count-change)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_partition_distinct_count_change`</span>](./distinct-count-change.md#monthly-partition-distinct-count-change)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_count_change_1_day`</span>](./distinct-count-change-1-day.md#profile-distinct-count-change-1-day)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_distinct_count_change_1_day`</span>](./distinct-count-change-1-day.md#daily-distinct-count-change-1-day)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_count_change_1_day`</span>](./distinct-count-change-1-day.md#daily-partition-distinct-count-change-1-day)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_count_change_7_days`</span>](./distinct-count-change-7-days.md#profile-distinct-count-change-7-days)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|**|
|[<span class="no-wrap-code">`daily_distinct_count_change_7_days`</span>](./distinct-count-change-7-days.md#daily-distinct-count-change-7-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_count_change_7_days`</span>](./distinct-count-change-7-days.md#daily-partition-distinct-count-change-7-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from the last week.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_count_change_30_days`</span>](./distinct-count-change-30-days.md#profile-distinct-count-change-30-days)|profiling|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|**|
|[<span class="no-wrap-code">`daily_distinct_count_change_30_days`</span>](./distinct-count-change-30-days.md#daily-distinct-count-change-30-days)|monitoring|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_count_change_30_days`</span>](./distinct-count-change-30-days.md#daily-partition-distinct-count-change-30-days)|partitioned|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from the last month.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_percent_change`</span>](./distinct-percent-change.md#profile-distinct-percent-change)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_distinct_percent_change`</span>](./distinct-percent-change.md#daily-distinct-percent-change)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_distinct_percent_change`</span>](./distinct-percent-change.md#monthly-distinct-percent-change)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_percent_change`</span>](./distinct-percent-change.md#daily-partition-distinct-percent-change)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|**|
|[<span class="no-wrap-code">`monthly_partition_distinct_percent_change`</span>](./distinct-percent-change.md#monthly-partition-distinct-percent-change)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_percent_change_1_day`</span>](./distinct-percent-change-1-day.md#profile-distinct-percent-change-1-day)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_distinct_percent_change_1_day`</span>](./distinct-percent-change-1-day.md#daily-distinct-percent-change-1-day)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_percent_change_1_day`</span>](./distinct-percent-change-1-day.md#daily-partition-distinct-percent-change-1-day)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_percent_change_7_days`</span>](./distinct-percent-change-7-days.md#profile-distinct-percent-change-7-days)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|**|
|[<span class="no-wrap-code">`daily_distinct_percent_change_7_days`</span>](./distinct-percent-change-7-days.md#daily-distinct-percent-change-7-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_percent_change_7_days`</span>](./distinct-percent-change-7-days.md#daily-partition-distinct-percent-change-7-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from the last week.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_distinct_percent_change_30_days`</span>](./distinct-percent-change-30-days.md#profile-distinct-percent-change-30-days)|profiling|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|**|
|[<span class="no-wrap-code">`daily_distinct_percent_change_30_days`</span>](./distinct-percent-change-30-days.md#daily-distinct-percent-change-30-days)|monitoring|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|**|
|[<span class="no-wrap-code">`daily_partition_distinct_percent_change_30_days`</span>](./distinct-percent-change-30-days.md#daily-partition-distinct-percent-change-30-days)|partitioned|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from the last month.|**|







