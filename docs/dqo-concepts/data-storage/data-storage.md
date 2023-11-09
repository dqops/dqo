# Data storage
In DQOps, sensor readouts and check results are stored as Apache Parquet files following the Apache
Hive compatible folder tree, partitioned by the connection name, table name, and month.
For example, check results for February 2023 for a single table would be stored in a file
`.data/check_results/c=bigquery-public-data/t=america_health_rankings.ahr/m=2023-02-01/rule_results.0.parquet`.

The data files are stored locally in the `.data` subfolder inside the **[DQOps User Home](../home-folders/dqops-user-home.md)** folder.
**[DQOps User Home](../home-folders/dqops-user-home.md)** folder is the place on the disk where DQOps
stores both the configuration files and the data result files.

The `.data` folder is organized as an offline Data Quality Data Lake, storing files in a Hive-compatible partitioning folder structure.
DQOps synchronizes the files between the `.data` folder and a Data Quality Data Lake hosted as a DQOps Cloud SaaS platform.
DQOps Cloud continuously refreshed the Data Quality Data Warehouse by loading the parquet data files
that were uploaded to the DQOps Cloud Data Quality Data Lake (the files from the `.data` folder).


## `.data` folder structure

The `.data` folder inside the **DQOps User Home** folder has the following structure.

``` { .asc .annotate hl_lines="2-26" }
$DQO_USER_HOME
├───.data                                                                   
│   ├───check_results(1)
│   │   └───c=bigquery-public-data                                                
│   │       └───t=america_health_rankings.ahr
│   │           └───m=2023-02-01 
│   │               └─rule_results.0.parquet
│   ├───errors(2)
│   │   └───c=bigquery-public-data                                                
│   │       └───t=america_health_rankings.ahr
│   │           └───m=2023-02-01  
│   │               └─errors.0.parquet                                          
│   ├───incidents(3)
│   │   └───c=bigquery-public-data                                                
│   │       └───m=2023-02-01
│   │           └───incidents.0.parquet 
│   ├───sensor_readouts(4)                                                            
│   │   └───c=bigquery-public-data                                                
│   │       └───t=america_health_rankings.ahr
│   │           └───m=2023-02-01
│   │               └─sensor_readout.0.parquet  
│   └───statistics(5)                                                        
│       └───c=bigquery-public-data                                                
│           └───t=america_health_rankings.ahr
│               └───m=2023-02-01   
│                   └─statistics.0.parquet   
├───.index(6) 
│    ├─data_check_results.LOCAL.dqofidx.json
│    ├─data_check_results.REMOTE.dqofidx.json
│    ├─data_sensor_readouts.LOCAL.dqofidx.json
│    ├─data_sensor_readouts.REMOTE.dqofidx.json
│    └─...                                                                
├───.logs
│   └─... 
├───checks                                                                   
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

1. The data quality [check results](../../reference/parquetfiles/check_results.md).
2. The execution [errors](../../reference/parquetfiles/errors.md) detected during the sensor's Jinja2 template rendering, running Python rules or returned
   by the data source when the query is executed.
3. The data quality [incidents](../../reference/parquetfiles/incidents.md)  which are groups of similar
   data quality issues (failed data quality checks) grouped into a single incident.
4. The [sensor readouts](../../reference/parquetfiles/sensor_readouts.md) which are the captured metrics about the quality of the data source. 
5. Basic [statistics](../../reference/parquetfiles/statistics.md) about the data sources at a table and column level, including the top 10 most common values for each column.
6. The `.index` folder is used internally by DQOps to track the synchronization status of local files with the DQOps Cloud Data Lake.
   This folder is not intended to be modified manually.

The `.data` folder contains subfolders with basic statistics (profiling), sensor readouts, check results, and error logs. These subfolders 
are further organized into subfolders for specific connections, tables, and months. Each dataset contains parquet 
files that store compressed data in a columnar format.
The detailed schema of each type of data table that is stored in the `.data` folder is documented in the reference section of the documentation.

The schema of the following tables are documented:

| Table name      | Purpose                                                                                                         | Table folder and schema                                                  |
|-----------------|-----------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------|
| check results   | the results of data quality checks, evaluated by rules                                                          | [.data/check_results](../../reference/parquetfiles/check_results.md)     |
| errors          | execution errors captured during the sensor rendering, rule evaluation or by running a query on the data source | [.data/errors](../../reference/parquetfiles/errors.md)                   |
| incidents       | data quality incidents tracked by DQOps                                                                         | [.data/incidents](../../reference/parquetfiles/incidents.md)             |
| sensor readouts | measures captured by data quality sensors                                                                       | [.data/sensor_readouts](../../reference/parquetfiles/sensor_readouts.md) |
| statistics      | basic statistics about the data source, including column sample values                                          | [.data/statistics](../../reference/parquetfiles/statistics.md)           |


## Storage of check results data
The way check results data are stored varies depending on the type of check used.

### **Profiling checks**

When the profiling data quality check is run, all sensor readouts are saved. As an illustration, if the check
is run three times, the table with the results could look like this:

| actual_value | time_period              |
|-------------:|--------------------------|
|       95.51% | 2023-04-05T09:07:03.578Z |
|       94.52% | 2023-04-06T09:08:50.635Z |
|       96.06% | 2023-04-07T09:10:44.386Z |

If there was a change in the data, and we run the check again, the table will be updated and the newest result will be added at the bottom.

| actual_value | time_period                  |
|-------------:|------------------------------|
|       95.51% | 2023-04-05T09:07:03.578Z     |
|       94.52% | 2023-04-06T09:08:50.635Z     |
|       96.06% | 2023-04-07T09:10:44.386Z     |
|   **95.79%** | **2023-04-07T11:47:20.843Z** |


### **Monitoring checks**

The daily monitoring checks store the most recent sensor readouts for each day when the data quality check was run.
This means that if you run a check several times a day only the most recent readout is stored. The previous readouts for
that day will be overwritten.

For example, if we run the check for three consecutive days, the results table could look like this:

| actual_value | time_period |
|-------------:|-------------|
|       95.51% | 2023-04-05  |
|       90.52% | 2023-04-06  |
|       91.06% | 2023-04-07  |

The original time_period timestamp of the result e.g. 2023-04-05T09:06:53.386Z is truncated to midnight for daily checks.

If there was a change in the data on 2023-04-07 and we run the check again, the table will be updated to show the latest result.

| actual_value | time_period    |
|-------------:|----------------|
|       95.51% | 2023-04-05     |
|       90.52% | 2023-04-06     |
|   **98.17%** | **2023-04-07** |

The previous result for 2023-04-07 was deleted.

Similarly, the monthly monitoring checks store the most recent sensor readout for each month when the data quality check was run.
For monthly monitoring checks, the original time_period of the result e.g. 2023-04-05T09:06:53.386Z is truncated to the 1st day of the month - 2023-04-01.

This approach allows you to track the data quality over time and calculate daily and monthly data quality KPIs.

### **Partition checks**

The daily partition checks store the most recent sensor readouts for each partition and each day when the data quality
check was run. This means that if you run check several times a day only the most recent readout is stored. The previous readouts for
that day will be overwritten.

The daily monitoring checks store the most recent sensor readouts for each day when the data quality check was run.
This means that if you run a check several times a day only the most recent readout is stored. The previous readouts for
that day will be overwritten.

For example, we have a table with results from three consecutive days that look like this:

| actual_value | time_period              |
|-------------:|--------------------------|
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
|-------------:|-------------|
|       93.64% | 2023-04-05  |
|       93.88% | 2023-04-06  |
|       94.76% | 2023-04-07  |


The original time_period of the result e.g. 2023-04-05T09:07:03.578Z is truncated to midnight for daily checks.

When there was a change in the data and on 2023-04-07 we run the check again, the table will be updated to show the latest result.

|  actual_value | time_period    |
|--------------:|----------------|
|        93.64% | 2023-04-05     |
|        93.88% | 2023-04-06     |
|    **98.65%** | **2023-04-07** |

The previous result for 2023-04-07 was deleted.

Similarly, the monthly monitoring checks store the most recent sensor readout for each month when the data quality check was run.
For monthly monitoring checks, the original time_period of the result e.g. 2023-04-05T09:07:03.578Z is truncated to the 1st day of the month - 2023-04-01.


## Synchronization with DQOps Cloud
DQOps creates a separate Data Quality Data Warehouse for each account, not sharing any tables or databases between tenants.
The Data Quality Data Warehouse is queries by a Looker Studio Community Connector provided by DQOps.
The data quality dashboards that are visible in DQOps are presented as embedded, multi-tenant Looker Studio dashboards.

The local, offline copy of the Data Quality Data Lake enables multiple deployment options.

- **Hybrid deployment** - an on-premise DQOps instance can monitor on-premise data source without opening connectivity
  to a data source from the cloud

- **Anomaly detection on historic data** - anomalies can be instantly detected by using historical data quality sensor readouts
  from previous dates

- **Anomaly detection with machine learning** - Python data quality rules can train time series prediction models to
  detect non-obvious or seasonal anomalies in the data

!!! note "DQOps Cloud license limits"

    The Data Quality Data Lake hosted by DQOps is a complimentary service provided for free for DQOps users using a FREE license.

    A standalone DQOps instance that uses a FREE DQOps Cloud license is limited to synchronize
    the data quality results for up to 5 tables organized within one data source. DQOps will pick the first data source
    and will synchronize the data for the first 5 tables.

    If you need to synchronize more results to the DQOps Cloud Data Quality Data Lake, please contact
    [DQOps sales](https://dqops.com/contact-us/).


The local Data Quality Data Warehouse is not limited to be synchronized with DQOps Cloud data lake.
Each user can set up a secondary file synchronization process that would replicate all the files in the `.data` folder
to a different location, using the user's owned S3 buckets, Storage Accounts or Cloud Storage.
Because the files are organized as a Hive-compatible data lake, it is possible to synchronize the data files
to any on-premise or cloud hosted data lake. The data files for each table should be registered as external tables.
This architecture will allow to build a private Data Quality Data Warehouse with custom data quality dashboards
using any SQL engine that can query Hive-compatible external tables. To be precise, the files could be queried
using Apache Hive, Apache Spark, DataBricks, Google BigQuery, Presto, Trino, SQL Server, Azure Synapse, Snowflake,
AWS Athena, and AWS Redshift Spectrum.
