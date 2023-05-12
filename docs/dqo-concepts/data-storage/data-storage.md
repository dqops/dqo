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
