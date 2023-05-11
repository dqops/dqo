# Data storage

In DQO, sensor readouts and check results are stored as Apache Parquet files following the Apache
Hive compatible folder tree, partitioned by connection name, table name, and month.
For example, rule results for February 2023 for a single table would be stored in a file
`/.data/rule_results/c=bigquery-public-data/t=america_health_rankings.ahr/m=2023-02-01/rule_results.0.parquet`.

The data is stored locally in `userhome` folder, allowing true multi-cloud data collection without accessing any
sensitive data through an external cloud or SaaS solution.

The data can simply be replicated to a data lake or cloud bucket. Any SQL engine capable of querying
Hive-compatible data can query the output files of the data quality tool. Data can be queried using Apache Hive,
Apache Spark, DataBricks, Google BigQuery, Presto, Trino, SQL Server PolyBase, AWS Athena, and AWS Redshift Spectrum.

## Userhome folder structure

The `userhome` folder has the following structure:

```
userhome
├───.data                                                                   
│   ├───statistics                                                        
│   │   └───c=bigquery-public-data                                                
│   │       └───t=america_health_rankings.ahr
│   │           └───m=2023-02-01   
│   │               ├─.statistics.0.parquet.snappy.crc   
│   │               └─statistics.0.parquet.snappy   
│   ├───sensor_redouts                                                            
│   │   └───c=bigquery-public-data                                                
│   │       └───t=america_health_rankings.ahr
│   │           └───m=2023-02-01
│   │               ├─.sensor_readout.0.parquet.crc   
│   │               └─sensor_readout.0.parquet  
│   ├───check_results
│   │   └───c=bigquery-public-data                                                
│   │       └───t=america_health_rankings.ahr
│   │           └───m=2023-02-01 
│   │               ├─.rule_results.0.parquet.crc   
│   │               └─rule_results.0.parquet
│   └───errors
│       └───c=bigquery-public-data                                                
│           └───t=america_health_rankings.ahr
│               └───m=2023-02-01  
│                   ├─.errors.0.parquet.snappy.crc   
│                   └─errors.0.parquet.snappy                                           
├───.index 
│    ├─data_check_results.LOCAL.dqofidx.json
│    ├─data_check_results.REMOTE.dqofidx.json
│    ├─data_sensor_readouts.LOCAL.dqofidx.json
│    ├─data_sensor_readouts.REMOTE.dqofidx.json
│    ├─rules.LOCAL.dqofidx.json
│    ├─rules.REMOTE.dqofidx.json
│    ├─sensors.LOCAL.dqofidx.json
│    ├─sensors.REMOTE.dqofidx.json
│    ├─sources.LOCAL.dqofidx.json
│    └─sources.REMOTE.dqofidx.json                                                                
├───.logs
│   ├─dqo-logs.log   
│   └─dqo-logs-2023-02-01_15.log 
├───rules                                                                   
├───sensors                                                                 
└───sources                                                                
    └───bigquery-public-data
        └─america_health_rankings.ahr.dqotable.yaml
          
```

The `.data` folder contains subfolders with basic statistics (profiling), sensor readouts, check results, and error logs. These subfolders 
are further organized into subfolders for specific connections, tables, and months. Each dataset contains .parquet 
files that store compressed data in a columnar format and a .crc (Cyclic Redundancy Check) files used to verify the 
integrity of the Parquet file, to ensure that it has not been corrupted during transmission or storage.

The `.index` folder contain JSON files with metadata about the stored data. Each data type has a separate index file for 
both locally stored and remote sources.

The `.logs` folder contains daily log files for DQO.

The `rules` folder contains user-defined [data quality rules](../rules/rules.md), while the `sensors` folder contains 
user-defined [data quality sensors](../sensors/sensors.md) in Jinja2 templating engine which are further rendered into a
SQL queries.

The `sources` folder contains [YAML configuration files](../working-with-yaml-files/working-with-yaml-files.md) for 
each connection and table.

## Storage of check results data
The way check results data are stored varies depending on the type of check used.

### Advanced profiling checks

When the advanced profiling data quality check is run, all sensor readouts are saved. As an illustration, if the check
is run three times, the table with the results could look like this:

| actual_value |              time_period |
|-------------:|-------------------------:|
|       95.51% | 2023-04-05T09:07:03.578Z |
|       94.52% | 2023-04-06T09:08:50.635Z |
|       96.06% | 2023-04-07T09:10:44.386Z |

If there was a change in the data, and we run the check again, the table will be updated and the newest result will be added at the bottom.

| actual_value |                  time_period |
|-------------:|-----------------------------:|
|       95.51% |     2023-04-05T09:07:03.578Z |
|       94.52% |     2023-04-06T09:08:50.635Z |
|       96.06% |     2023-04-07T09:10:44.386Z |
|   **95.79%** | **2023-04-07T11:47:20.843Z** |

### Recurring checks

The daily recurring checks store the most recent sensor readouts for each day when the data quality check was run.
This means that if you run a check several times a day only the most recent readout is stored. The previous readouts for
that day will be overwritten.

For example, if we run the check for three consecutive days, the results table could look like this:

| actual_value | time_period |
|-------------:|------------:|
|       95.51% |  2023-04-05 |
|       90.52% |  2023-04-06 |
|       91.06% |  2023-04-07 |

The original time_period timestamp of the result e.g. 2023-04-05T09:06:53.386Z is truncated to midnight for daily checks.

If there was a change in the data on 2023-04-07 and we run the check again, the table will be updated to show the latest result.

| actual_value |    time_period |
|-------------:|---------------:|
|       95.51% |     2023-04-05 |
|       90.52% |     2023-04-06 |
|   **98.17%** | **2023-04-07** |

The previous result for 2023-04-07 was deleted.

Similarly, the monthly recurring checks store the most recent sensor readout for each month when the data quality check was run.
For monthly recurring checks, the original time_period of the result e.g. 2023-04-05T09:06:53.386Z is truncated to the 1st day of the month - 2023-04-01.

This approach allows you to track the data quality over time and calculate daily and monthly data quality KPIs.

### Partition checks

The daily partition checks store the most recent sensor readouts for each partition and each day when the data quality
check was run. This means that if you run check several times a day only the most recent readout is stored. The previous readouts for
that day will be overwritten.

The daily recurring checks store the most recent sensor readouts for each day when the data quality check was run.
This means that if you run a check several times a day only the most recent readout is stored. The previous readouts for
that day will be overwritten.

For example, we have a table with results from three consecutive days that look like this:

| actual_value |              time_period |
|-------------:|-------------------------:|
|       95.51% | 2023-04-05T09:07:03.578Z |
|       94.52% | 2023-04-05T09:08:50.635Z |
|       90.88% | 2023-04-05T09:10:44.386Z |
|       91.51% | 2023-04-06T09:07:03.578Z |
|       93.56% | 2023-04-06T09:08:50.635Z |
|       96.54% | 2023-04-06T09:10:44.386Z |
|       95.55% | 2023-04-07T09:07:03.578Z |
|       92.64% | 2023-04-07T09:08:50.635Z |
|       96.06% | 2023-04-07T09:10:44.386Z |

Daily partitioned data should be analyzed separately, for each daily partition. That is why daily partition checks use
**GROUP BY** clause with daily time slicing.

The following Google BigQuery query example captures time-sliced data to calculate metrics for each day separately.

``` sql hl_lines="1 4"
SELECT DATETIME_TRUNC(time_period, DAY) as time_period,
100.0 * SUM(CASE WHEN actual_value >= 0 THEN 1 ELSE 0 END) /
COUNT(*) as percentage_valid FROM schema.table
GROUP BY time_period
```

The results grouped by day may look like this:

| actual_value | time_period |
|-------------:|------------:|
|       93.64% |  2023-04-05 |
|       93.88% |  2023-04-06 |
|       94.76% |  2023-04-07 |


The original time_period of the result e.g. 2023-04-05T09:07:03.578Z is truncated to midnight for daily checks.

When there was a change in the data and on 2023-04-07 we run the check again, the table will be updated to show the latest result.

| actual_value |    time_period |
|-------------:|---------------:|
|       93.64% |     2023-04-05 |
|       93.88% |     2023-04-06 |
|   **98.65%** | **2023-04-07** |

The previous result for 2023-04-07 was deleted.

Similarly, the monthly recurring checks store the most recent sensor readout for each month when the data quality check was run.
For monthly recurring checks, the original time_period of the result e.g. 2023-04-05T09:07:03.578Z is truncated to the 1st day of the month - 2023-04-01.