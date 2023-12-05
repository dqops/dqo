# Parquet files

**This is a list of parquet file types in DQOps.**

| Parquet file table | Description |
|--------------------|-------------|
|[check_results](./check_results/)|The data quality check results table that stores the data quality check results - a copy of sensor readouts (copied from the sensor_readouts table) and evaluated by the data quality rules.|
|[errors](./errors/)|The data quality execution errors table that stores execution errors captured during the sensor execution or the rule evaluation.|
|[incidents](./incidents/)|The data quality incidents table that tracks open incidents.|
|[sensor_readouts](./sensor_readouts/)|The data quality sensor readouts table that stores readouts (measures) captured by DQOps sensors, before the value are evaluated by the data quality rules.|
|[statistics](./statistics/)|The basic profiling results (statistics) table that stores basic profiling statistical values.|

