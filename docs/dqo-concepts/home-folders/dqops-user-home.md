# DQOps user home



``` { .asc .annotate }
$DQO_USER_HOME
├───.data                                                                   
│   └───...(1)                                             
├───.index(2) 
│    ├─data_check_results.LOCAL.dqofidx.json
│    ├─data_check_results.REMOTE.dqofidx.json
│    ├─data_sensor_readouts.LOCAL.dqofidx.json
│    ├─data_sensor_readouts.REMOTE.dqofidx.json
│    └─...                                                                
├───.logs(3)
│   └─... 
├───checks(4)                                                                   
│   └─...   
├───rules                                                                 
│   └─...   
├───sensors                                                                 
│   └─...   
├───settings                                                                 
│   └─...   
└───sources                                                                
    └─...   
```

1. The [data storage](../data-storage/data-storage.md) used to keep a local copy of the data quality data lake for offline use
   and for accessing historical *sensor readouts* used for anomaly and change detection [rules](../rules/rules.md).
2. The `.index` folder is used internally by DQOps to track the synchronization status of local files with the DQOps Cloud Data Lake.
   This folder is not intended to be modified manually.



The `.index` folder contain JSON files used for tracing the synchronization status for each file between the local folder
and the DQOps Cloud SaaS hosted data lake. The files are managed by DQOps runtime.

The `.logs` folder contains daily log files for DQOps. If DQOps returns an error, please check the details of
the error (including the stack trace) in the files in this folder.

The `rules` folder contains user-defined [data quality rules](../rules/rules.md), while the `sensors` folder contains
user-defined [data quality sensors](../sensors/sensors.md) in Jinja2 templating engine which are further rendered into a
SQL queries.

The `sources` folder contains [YAML configuration files](../working-with-yaml-files/working-with-yaml-files.md) for
each connection and table. These files describe the metadata of imported data sources and contain a configuration
of enabled data quality checks on a table and column level. The alerting thresholds for data quality rules
are configured together with the list of enabled data quality checks.

