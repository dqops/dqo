---
title: table level timeliness data quality checks
---
# table level timeliness data quality checks

This is a list of timeliness table data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## table-level timeliness checks
Assesses the freshness and staleness of data, as well as data ingestion delay and reload lag for partitioned data.

### [data freshness](./data-freshness.md)
A table-level check that calculates the time difference between the most recent row in the table and the current time.
 The timestamp column that is used for comparison is defined as the timestamp_columns.event_timestamp_column on the table configuration.
 This check is also known as &quot;Data Freshness&quot;.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_data_freshness`</span>](./data-freshness.md#profile-data-freshness)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Calculates the number of days since the most recent event timestamp (freshness)|:material-check-bold:|
|[<span class="no-wrap-code">`daily_data_freshness`</span>](./data-freshness.md#daily-data-freshness)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Daily calculating the number of days since the most recent event timestamp (freshness)|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_data_freshness`</span>](./data-freshness.md#monthly-data-freshness)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Monthly monitoring calculating the number of days since the most recent event timestamp (freshness)|:material-check-bold:|



### [data freshness anomaly](./data-freshness-anomaly.md)
This check calculates the most recent rows value and the current time and detects anomalies in a time series of previous averages.
 The timestamp column that is used for comparison is defined as the timestamp_columns.event_timestamp_column on the table configuration.
 It raises a data quality issue when the mean is in the top *anomaly_percent* percentage of the most outstanding values in the time series.
 This data quality check uses a 90-day time window and requires a history of at least 30 days.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_data_freshness_anomaly`</span>](./data-freshness-anomaly.md#profile-data-freshness-anomaly)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the number of days since the most recent event timestamp (freshness) changes in a rate within a percentile boundary during the last 90 days.| |
|[<span class="no-wrap-code">`daily_data_freshness_anomaly`</span>](./data-freshness-anomaly.md#daily-data-freshness-anomaly)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of days since the most recent event timestamp (freshness) changes in a rate within a percentile boundary during the last 90 days.| |



### [data staleness](./data-staleness.md)
A table-level check that calculates the time difference between the last timestamp when any data was loaded into a table and the current time.
 This check can only be use when a data pipeline, ETL process, or trigger in the data warehouse is filling an extra column with the timestamp when the data loading job was loaded.
 The ingestion column used for comparison is defined as the timestamp_columns.ingestion_timestamp_column on the table configuration.
 This check is also known as &quot;Data Staleness&quot;.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_data_staleness`</span>](./data-staleness.md#profile-data-staleness)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)| |
|[<span class="no-wrap-code">`daily_data_staleness`</span>](./data-staleness.md#daily-data-staleness)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Daily calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)| |
|[<span class="no-wrap-code">`monthly_data_staleness`</span>](./data-staleness.md#monthly-data-staleness)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Monthly monitoring calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)| |



### [data ingestion delay](./data-ingestion-delay.md)
A table-level check that calculates the time difference between the most recent row in the table and the most recent timestamp when the last row was loaded into the data warehouse or data lake.
 To identify the most recent row, the check finds the maximum value of the timestamp column that should contain the last modification timestamp from the source.
 The timestamp when the row was loaded is identified by the most recent (maximum) value a timestamp column that was filled by the data pipeline, for example: &quot;loaded_at&quot;, &quot;updated_at&quot;, etc.
 This check requires that the data pipeline is filling an extra column with the timestamp when the data loading job has been executed.
 The names of both columns used for comparison should be specified in the &quot;timestamp_columns&quot; configuration entry on the table.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_data_ingestion_delay`</span>](./data-ingestion-delay.md#profile-data-ingestion-delay)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp| |
|[<span class="no-wrap-code">`daily_data_ingestion_delay`</span>](./data-ingestion-delay.md#daily-data-ingestion-delay)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Daily calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp| |
|[<span class="no-wrap-code">`monthly_data_ingestion_delay`</span>](./data-ingestion-delay.md#monthly-data-ingestion-delay)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Monthly monitoring calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp| |
|[<span class="no-wrap-code">`daily_partition_data_ingestion_delay`</span>](./data-ingestion-delay.md#daily-partition-data-ingestion-delay)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Daily partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp| |
|[<span class="no-wrap-code">`monthly_partition_data_ingestion_delay`</span>](./data-ingestion-delay.md#monthly-partition-data-ingestion-delay)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Monthly partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp| |



### [reload lag](./reload-lag.md)
A table-level check that calculates the maximum difference in days between ingestion timestamp and event timestamp values on any row.
 This check should be executed only as a partitioned check because this check finds the longest delay between the time that the row was created
 in the data source and the timestamp when the row was loaded into its daily or monthly partition.
 This check detects that a daily or monthly partition was reloaded, setting also the most recent timestamps in the created_at, loaded_at, inserted_at or other similar columns
 filled by the data pipeline or an ETL process during data loading.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`daily_partition_reload_lag`</span>](./reload-lag.md#daily-partition-reload-lag)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Daily partitioned check calculating the longest time a row waited to be loaded, it is the maximum difference in days between the ingestion timestamp and the event timestamp column on any row in the monitored partition|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_reload_lag`</span>](./reload-lag.md#monthly-partition-reload-lag)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Monthly partitioned check calculating the longest time a row waited to be loaded, it is the maximum difference in days between the ingestion timestamp and the event timestamp column on any row in the monitored partition|:material-check-bold:|







