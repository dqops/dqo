---
title: Parquet files supported by DQOps
---
# Parquet files supported by DQOps
The list of parquet tables used by DQOps to store data quality results in the *[$DQO_USER_HOME](../../dqo-concepts/dqops-user-home-folder.md)/.data* folder is listed below.


## List of supported parquet tables

| Parquet file table | Description |
|--------------------|-------------|
|[check_results](./check_results.md)|The data quality check results table that stores the data quality check results - a copy of sensor readouts (copied from the sensor_readouts table) and evaluated by the data quality rules.|
|[errors](./errors.md)|The data quality execution errors table that stores execution errors captured during the sensor execution or the rule evaluation.|
|[incidents](./incidents.md)|The data quality incidents table that tracks open incidents.|
|[sensor_readouts](./sensor_readouts.md)|The data quality sensor readouts table that stores readouts (measures) captured by DQOps sensors, before the value are evaluated by the data quality rules.|
|[statistics](./statistics.md)|The basic profiling results (statistics) table that stores basic profiling statistical values.|


## What's more
- You can find more information on how the Parquet files are partitioned in the [data quality results storage concept](../../dqo-concepts/data-storage-of-data-quality-results.md).
