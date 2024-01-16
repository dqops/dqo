# table level timeliness data quality checks

This is a list of timeliness table data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **timeliness**
Assesses the freshness and staleness of data, as well as data ingestion delay and reload lag for partitioned data.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_data_freshness`</span>](./data-freshness.md#profile-data-freshness)|profiling|Calculates the number of days since the most recent event timestamp (freshness)|*standard*|
|[<span class="no-wrap-code">`daily_data_freshness`</span>](./data-freshness.md#daily-data-freshness)|monitoring|Daily  calculating the number of days since the most recent event timestamp (freshness)|*standard*|
|[<span class="no-wrap-code">`monthly_data_freshness`</span>](./data-freshness.md#monthly-data-freshness)|monitoring|Monthly monitoring calculating the number of days since the most recent event timestamp (freshness)|*standard*|
|[<span class="no-wrap-code">`daily_partition_data_freshness`</span>](./data-freshness.md#daily-partition-data-freshness)|partitioned|Daily partitioned check calculating the number of days since the most recent event timestamp (freshness)|*standard*|
|[<span class="no-wrap-code">`monthly_partition_data_freshness`</span>](./data-freshness.md#monthly-partition-data-freshness)|partitioned|Monthly partitioned check calculating the number of days since the most recent event (freshness)|*standard*|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_data_staleness`</span>](./data-staleness.md#profile-data-staleness)|profiling|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|*advanced*|
|[<span class="no-wrap-code">`daily_data_staleness`</span>](./data-staleness.md#daily-data-staleness)|monitoring|Daily  calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|*advanced*|
|[<span class="no-wrap-code">`monthly_data_staleness`</span>](./data-staleness.md#monthly-data-staleness)|monitoring|Monthly monitoring calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|*advanced*|
|[<span class="no-wrap-code">`daily_partition_data_staleness`</span>](./data-staleness.md#daily-partition-data-staleness)|partitioned|Daily partitioned check calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|*advanced*|
|[<span class="no-wrap-code">`monthly_partition_data_staleness`</span>](./data-staleness.md#monthly-partition-data-staleness)|partitioned|Monthly partitioned check calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)|*advanced*|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_data_ingestion_delay`</span>](./data-ingestion-delay.md#profile-data-ingestion-delay)|profiling|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|*advanced*|
|[<span class="no-wrap-code">`daily_data_ingestion_delay`</span>](./data-ingestion-delay.md#daily-data-ingestion-delay)|monitoring|Daily  calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|*advanced*|
|[<span class="no-wrap-code">`monthly_data_ingestion_delay`</span>](./data-ingestion-delay.md#monthly-data-ingestion-delay)|monitoring|Monthly monitoring calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|*advanced*|
|[<span class="no-wrap-code">`daily_partition_data_ingestion_delay`</span>](./data-ingestion-delay.md#daily-partition-data-ingestion-delay)|partitioned|Daily partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|*advanced*|
|[<span class="no-wrap-code">`monthly_partition_data_ingestion_delay`</span>](./data-ingestion-delay.md#monthly-partition-data-ingestion-delay)|partitioned|Monthly partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|*advanced*|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`daily_partition_reload_lag`</span>](./reload-lag.md#daily-partition-reload-lag)|partitioned|Daily partitioned check calculating the longest time a row waited to be load|*advanced*|
|[<span class="no-wrap-code">`monthly_partition_reload_lag`</span>](./reload-lag.md#monthly-partition-reload-lag)|partitioned|Monthly partitioned check calculating the longest time a row waited to be load|*advanced*|







