# Checks/table/timeliness

**This is a list of timeliness table data quality checks supported by DQOps and a brief description of what they do.**





## **timeliness**
Assesses the freshness and staleness of data, as well as data ingestion delay and reload lag for partitioned data.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_data_freshness](../data-freshness.md#profile-data-freshness)|profiling|Calculates the number of days since the most recent event timestamp (freshness)|
|[daily_data_freshness](../data-freshness.md#daily-data-freshness)|monitoring|Daily  calculating the number of days since the most recent event timestamp (freshness)|
|[monthly_data_freshness](../data-freshness.md#monthly-data-freshness)|monitoring|Monthly monitoring calculating the number of days since the most recent event timestamp (freshness)|
|[daily_partition_data_freshness](../data-freshness.md#daily-partition-data-freshness)|partitioned|Daily partitioned check calculating the number of days since the most recent event timestamp (freshness)|
|[monthly_partition_data_freshness](../data-freshness.md#monthly-partition-data-freshness)|partitioned|Monthly partitioned check calculating the number of days since the most recent event (freshness)|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_data_staleness](../data-staleness.md#profile-data-staleness)|profiling|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[daily_data_staleness](../data-staleness.md#daily-data-staleness)|monitoring|Daily  calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[monthly_data_staleness](../data-staleness.md#monthly-data-staleness)|monitoring|Monthly monitoring calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[daily_partition_data_staleness](../data-staleness.md#daily-partition-data-staleness)|partitioned|Daily partitioned check calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|
|[monthly_partition_data_staleness](../data-staleness.md#monthly-partition-data-staleness)|partitioned|Monthly partitioned check calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_data_ingestion_delay](../data-ingestion-delay.md#profile-data-ingestion-delay)|profiling|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[daily_data_ingestion_delay](../data-ingestion-delay.md#daily-data-ingestion-delay)|monitoring|Daily  calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[monthly_data_ingestion_delay](../data-ingestion-delay.md#monthly-data-ingestion-delay)|monitoring|Monthly monitoring calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[daily_partition_data_ingestion_delay](../data-ingestion-delay.md#daily-partition-data-ingestion-delay)|partitioned|Daily partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|
|[monthly_partition_data_ingestion_delay](../data-ingestion-delay.md#monthly-partition-data-ingestion-delay)|partitioned|Monthly partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|


| Check name | Check type | Description |
|------------|------------|-------------|
|[daily_partition_reload_lag](../reload-lag.md#daily-partition-reload-lag)|partitioned|Daily partitioned check calculating the longest time a row waited to be load|
|[monthly_partition_reload_lag](../reload-lag.md#monthly-partition-reload-lag)|partitioned|Monthly partitioned check calculating the longest time a row waited to be load|





