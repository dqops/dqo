# table level timeliness data quality checks

This is a list of timeliness table data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **timeliness**
Assesses the freshness and staleness of data, as well as data ingestion delay and reload lag for partitioned data.

| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_data_freshness`</span>](./data-freshness.md#profile-data-freshness)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Calculates the number of days since the most recent event timestamp (freshness)|:material-check-bold:|
|[<span class="no-wrap-code">`daily_data_freshness`</span>](./data-freshness.md#daily-data-freshness)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Daily  calculating the number of days since the most recent event timestamp (freshness)|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_data_freshness`</span>](./data-freshness.md#monthly-data-freshness)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Monthly monitoring calculating the number of days since the most recent event timestamp (freshness)|:material-check-bold:|



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_data_staleness`</span>](./data-staleness.md#profile-data-staleness)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)| |
|[<span class="no-wrap-code">`daily_data_staleness`</span>](./data-staleness.md#daily-data-staleness)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Daily  calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)| |
|[<span class="no-wrap-code">`monthly_data_staleness`</span>](./data-staleness.md#monthly-data-staleness)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Monthly monitoring calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)| |



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_data_ingestion_delay`</span>](./data-ingestion-delay.md#profile-data-ingestion-delay)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp| |
|[<span class="no-wrap-code">`daily_data_ingestion_delay`</span>](./data-ingestion-delay.md#daily-data-ingestion-delay)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Daily  calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp| |
|[<span class="no-wrap-code">`monthly_data_ingestion_delay`</span>](./data-ingestion-delay.md#monthly-data-ingestion-delay)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Monthly monitoring calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp| |
|[<span class="no-wrap-code">`daily_partition_data_ingestion_delay`</span>](./data-ingestion-delay.md#daily-partition-data-ingestion-delay)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Daily partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp| |
|[<span class="no-wrap-code">`monthly_partition_data_ingestion_delay`</span>](./data-ingestion-delay.md#monthly-partition-data-ingestion-delay)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Monthly partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp| |



| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`daily_partition_reload_lag`</span>](./reload-lag.md#daily-partition-reload-lag)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Daily partitioned check calculating the longest time a row waited to be loaded, it is the maximum difference in days between the ingestion timestamp and the event timestamp column on any row in the monitored partition| |
|[<span class="no-wrap-code">`monthly_partition_reload_lag`</span>](./reload-lag.md#monthly-partition-reload-lag)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Monthly partitioned check calculating the longest time a row waited to be loaded, it is the maximum difference in days between the ingestion timestamp and the event timestamp column on any row in the monitored partition| |







