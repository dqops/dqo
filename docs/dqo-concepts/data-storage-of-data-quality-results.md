# Data quality results storage in DQOps
Read this guide to learn now DQOps stores data quality results locally, to enable even offline work, and monitoring on-premise or sensitive data sources.

## Data format
In DQOps, sensor readouts and check results are stored as Apache Parquet files following the Apache
Hive compatible folder tree, partitioned by the connection name, table name, and month.
For example, check results for February 2023 for a single table would be stored in a file
`.data/check_results/c=bigquery-public-data/t=america_health_rankings.ahr/m=2023-02-01/rule_results.0.parquet`.

The data files are stored locally in the `.data` subfolder inside the **[DQOps User Home](dqops-user-home-folder.md)** folder.
**[DQOps User Home](dqops-user-home-folder.md)** folder is the place on the disk where DQOps
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

1. The data quality [check results](../reference/parquetfiles/check_results.md).
2. The execution [errors](../reference/parquetfiles/errors.md) detected during the sensor's Jinja2 template rendering, running Python rules or returned
   by the data source when the query is executed.
3. The data quality [incidents](../reference/parquetfiles/incidents.md)  which are groups of similar
   data quality issues (failed data quality checks) grouped into a single incident.
4. The [sensor readouts](../reference/parquetfiles/sensor_readouts.md) which are the captured metrics about the quality of the data source. 
5. Basic [statistics](../reference/parquetfiles/statistics.md) about the data sources at a table and column level, including the top 10 most common values for each column.
6. The `.index` folder is used internally by DQOps to track the synchronization status of local files with the DQOps Cloud Data Lake.
   This folder is not intended to be modified manually.

The `.data` folder contains subfolders with basic statistics (profiling), sensor readouts, check results, and error logs. Those folders 
are further organized into subfolders for specific connections, tables, and months. Each dataset contains parquet 
files that store compressed data in a columnar format.
The detailed schema of each type of data table that is stored in the `.data` folder is documented in the reference section of the documentation.

The schema of the following tables are documented:

| Table name      | Purpose                                                                                                         | Table folder and schema                                                  |
|-----------------|-----------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------|
| check results   | the results of data quality checks, evaluated by data quality rules                                             | [.data/check_results](../reference/parquetfiles/check_results.md)     |
| errors          | execution errors captured during the sensor rendering, rule evaluation or by running a query on the data source | [.data/errors](../reference/parquetfiles/errors.md)                   |
| incidents       | data quality incidents tracked by DQOps                                                                         | [.data/incidents](../reference/parquetfiles/incidents.md)             |
| sensor readouts | measures captured by data quality sensors                                                                       | [.data/sensor_readouts](../reference/parquetfiles/sensor_readouts.md) |
| statistics      | basic statistics about the data source, including column sample values                                          | [.data/statistics](../reference/parquetfiles/statistics.md)           |


## How data quality results are stored

### **Parquet tables**
When a [data quality check](definition-of-data-quality-checks/index.md) is [run](running-data-quality-checks.md), DQOps runs the SQL query
generated from the [data quality sensor](definition-of-data-quality-sensors.md) template on the monitored data source.
The query results (called the **sensor readouts** in DQOps) are stored
in the [sensor_readouts](../reference/parquetfiles/sensor_readouts.md) Parquet table.

The **sensor readouts** are processed by [data quality rules](definition-of-data-quality-rules.md), identifying invalid data quality results.
Each sensor readout row is copied to the [check_results](../reference/parquetfiles/check_results.md) table, but additional columns such as the `severity` are added.

Otherwise, if the data quality SQL query fails to execute, or the [data quality rule](definition-of-data-quality-rules.md) Python function fails,
the error is stored in the [errors](../reference/parquetfiles/errors.md) table.

### **Sensor readout mapping**
Let's suppose that the [profiling_nulls_percent](../checks/column/nulls/nulls-percent.md#profile-nulls-percent) check is executed
on [PostgreSQL](../data-sources/postgresql.md) database.

The SQL query generated by DQOps is shown below.

``` { .sql .annotate linenums="1" hl_lines="10-12" }
SELECT
    CASE
        WHEN COUNT(*) = 0 THEN 100.0
        ELSE 100.0 * SUM(
            CASE
                WHEN analyzed_table."target_column" IS NULL THEN 1
                ELSE 0
            END
        ) / COUNT(*)
    END AS actual_value, -- (1)!
    DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period, -- (2)!
    CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc -- (3)!
FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
GROUP BY time_period, time_period_utc
ORDER BY time_period, time_period_utc
```

1.  Actual value returned by the data quality sensor, called the **sensor readout**. It is the measure captured by the data quality sensor.

2.  The time period for which the **sensor readout** is valid, using a local time zone of the monitored database.
    Profiling checks are truncating the date to the beginning of the month.

3.  The time period for which the **sensor readout** is valid, but converted to a UTC timestamp.


The data quality query captures the data quality measure captured from the data source, returned as an `actual_value` result column.
This value is called a **sensor readout** across the DQOps reference documentation.

The additional columns that are captured are:

- The `time_period` for which the **sensor readout** is valid, using a local time zone of the monitored database.
  Profiling checks are truncating the date to the beginning of the month.

- The `time_period_utc` for which the **sensor readout** is valid, but converted to a UTC timestamp.

The result of the query is stored in the [sensor_readouts](../reference/parquetfiles/sensor_readouts.md) Parquet table.

### **Additional sensor readout columns**
The generated SQL query can also include the data grouping columns,
such as `grouping_level_1`. Sample SQL queries generated with data grouping by column clauses are
shown in the [data grouping concept](measuring-data-quality-with-data-grouping.md) guide.

DQOps will also store information about the monitored data source, table, column, data quality check name, type, category and dimension.


### **Identifiers**
The rows in the [sensor_readouts](../reference/parquetfiles/sensor_readouts.md), [check_results](../reference/parquetfiles/check_results.md) and [errors](../reference/parquetfiles/errors.md)
share common identity columns that identify each data quality result, and each time series stream.

| Column            | Description                                                                                                                                                                                                                                        | Sample value                         |
|:------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------|
| `time_period`     | The time period, for which the **sensor readout** is valid. The value may be truncated for some types of data quality checks.                                                                                                                      | 2023-04-01T00:00:00                  |
| `check_hash`      | Hash code calculated from the data source (connection) name, schema name, table name, column name (optional) and the check name.                                                                                                                   | 4907661392389800303                  |
| `data_group_hash` | Hash code calculated from the [data grouping](measuring-data-quality-with-data-grouping.md) level columns.                                                                                                                                                    | 3432492748264648423                  |
| `time_series_id`  | Unique identifier of each time series, a time series is a collection of data quality results captured by the same data quality check (identified by the `check_hash`), for the same data group that is identified by the `data_group_hash` column. | 441b8408-d34b-016f-19d0-ca77a4156fba |
| **`id`**          | Primary key of the row. Calculated as as hash value of the `time_series_id`, `check_hash`, and the `time_period` columns, that identify each data quality result within a time series by the time period.                                          | ef606aaf-dec8-5207-4d54-db2f5669c32d |


DQOps de-duplicates results stored in the data quality data warehouse by overwriting previous results identified by
the same **`id`** column value. This means that the result of executing the same data quality check again will overwrite the previous result.
The behavior is further described for each [type of data quality checks](definition-of-data-quality-checks/index.md#types-of-checks).


## Storage of check results
The way check results data are stored varies depending on the type of check used.

### **Profiling checks**

When the [profiling data quality check](definition-of-data-quality-checks/data-profiling-checks.md) is run, 
all sensor readouts are saved. As an illustration, if the check
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
DQOps creates a separate Data Quality Data Warehouse for each DQOps Cloud account, not sharing any tables or databases between tenants.
The Data Quality Data Warehouse is queried by a Looker Studio Community Connector provided by DQOps.
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


### **Authentication with DQOps Cloud**
DQOps local instance authenticates to DQOps Cloud using a `DQOps Cloud Pairing API Key`, which can be found
on the [https://cloud.dqops.com/account](https://cloud.dqops.com/account) page.

The `DQOps Cloud Pairing API Key` is stored in the [`DQOps user home`*/.localsettings.dqosettings.yaml*](../reference/yaml/LocalSettingsYaml.md) file.
The file does not need to be modified manually. DQOps automates the process of issuing and downloading the `DQOps Cloud Pairing API Key`
in a secure way.

Open the [DQOps Shell](command-line-interface.md) and type the command

&gt; [`cloud login`](../command-line-interface/cloud.md#dqo-cloud-login)

DQOps will open a browser window, navigate to the [https://cloud.dqops.com/account](https://cloud.dqops.com/account), and download the pairing key.

Alternatively, if [DQOps is started as a Docker container](../dqops-installation/run-dqops-as-docker-container.md), and 
opening a browser window is not possible, it is also possible to pass the `DQOps Cloud Pairing API Key` using a `--dqo.cloud.api-key=KEY` parameter.
Instead of using a parameter, the key can be passed by setting (and exporting) a `DQO_CLOUD_API_KEY` environment variable
that holds the value of the `DQOps Cloud Pairing API Key`.


### **Synchronize from UI**
DQOps tracks local file changes, comparing the list of files previously synchronized to DQOps Cloud with the current list of files.
When any YAML, or *.parquet* file is saved, deleted or updated locally, DQOps detects the file change and changes the color of the
`Synchronize` button to green. 

![Synchronize button status when local changes are present in DQOps](https://dqops.com/docs/images/concepts/data-storage/synchronize-button-local-changes-present-min.png){ loading=lazy }

Press the `Synchronize` button to start the synchronization job. The progress of synchronization can be tracked in the job
notification panel on the right of the `Synchronize` button.
When no local changes are present, the button is white.

![Synchronize button status when no local changes are present in DQOps](https://dqops.com/docs/images/concepts/data-storage/synchronize-button-no-local-changes-present-min.png){ loading=lazy }

!!! info "Only local changes are detected"

    DQOps only detects local changes. The button color is not aware of file changes pushed to the DQOps Cloud Data Lake
    from another DQOps instance.

    In order to pull remote changes from the DQOps Cloud Data Lake, please start the synchronization job even if the button is white.


### **Synchronize from command-line**
The synchronization can be triggered from the DQOps command line. The following commands are supported.

| Command&nbsp;name                                                                                 | Description                                                                                                                                                              |
|---------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| &gt; [`cloud sync all`](../command-line-interface/cloud.md#dqo-cloud-sync-all)                 | Synchronizes all files between the local `DQOps user home` folder and the DQOps Cloud Data Lake                                                                          |
| &gt; [`cloud sync data`](../command-line-interface/cloud.md#dqo-cloud-sync-data)               | Synchronizes only parquet (data) files between the local `DQOps user home`*/.data* folder and the DQOps Cloud Data Lake                                                  |
| &gt; [`cloud sync sources`](../command-line-interface/cloud.md#dqo-cloud-sync-sources)         | Synchronizes only the configuration of data sources (YAML files) between the local `DQOps user home`*/sources* folder and the DQOps Cloud Data Lake                      |
| &gt; [`cloud sync sensors`](../command-line-interface/cloud.md#dqo-cloud-sync-sensors)         | Synchronizes only the definition of custom data quality sensors (query templates) between the local `DQOps user home`*/sensors* folder and the DQOps Cloud Data Lake     |
| &gt; [`cloud sync rules`](../command-line-interface/cloud.md#dqo-cloud-sync-rules)             | Synchronizes only the definition of custom data quality rules (Python functions) between the local `DQOps user home`*/rules* folder and the DQOps Cloud Data Lake        |
| &gt; [`cloud sync checks`](../command-line-interface/cloud.md#dqo-cloud-sync-checks)           | Synchronizes only the definition of custom data quality checks (sensor and rule pairs) between the local `DQOps user home`*/checks* folder and the DQOps Cloud Data Lake |
| &gt; [`cloud sync settings`](../command-line-interface/cloud.md#dqo-cloud-sync-settings)       | Synchronizes only the configuration (setting) files between the local `DQOps user home`*/settings* folder and the DQOps Cloud Data Lake                                  |
| &gt; [`cloud sync credentials`](../command-line-interface/cloud.md#dqo-cloud-sync-credentials) | Synchronizes only the shared credential files between the local `DQOps user home`*/credentials* folder and the DQOps Cloud Data Lake                                     |


### **Automatic synchronization**
DQOps automatically runs an initial synchronization when the application is started in a shell mode (without any commands to run and exit),
or when DQOps is started in a server mode, using the [`dqo run`](../command-line-interface/run.md) command.

After DQOps is started, teh synchronization is performed according to the CRON schedule configured in the 
[`--dqo.scheduler.synchronize-cron-schedule`](../command-line-interface/dqo.md#--dqo.scheduler.synchronize-cron-schedule) parameter.

!!! note "FREE (community) version limits"

    The configuration of the minutes CRON component is ignored for DQOps Cloud account using a FREE version.
    Instead, DQOps Cloud limits the synchronization frequency to one synchronization per hour on a randomly choosen time. 


### **Trigger synchronization from DQOps Python client**
Synchronization of a DQOps instance with the DQOps Cloud Data Lake can be also triggered using a DQOps Python client.

Please find the documentation of all parameters in the [`synchronize_folders` command reference](../client/operations/jobs.md#synchronize_folders).


```python
from dqops import client
from dqops.client.api.jobs import synchronize_folders
from dqops.client.models import FileSynchronizationDirection, \
                                SynchronizeMultipleFoldersDqoQueueJobParameters

token = 'DQOps local API Key'

dqops_client = client.AuthenticatedClient(
    'http://localhost:8888/',
    token=token
)

request_body = SynchronizeMultipleFoldersDqoQueueJobParameters(
    direction=FileSynchronizationDirection.full,
    force_refresh_native_tables=False,
    detect_cron_schedules=False,
    sources=True,
    sensors=True,
    rules=True,
    checks=True,
    settings=True,
    credentials=True,
    data_sensor_readouts=True,
    data_check_results=True,
    data_statistics=True,
    data_errors=True,
    data_incidents=True,
    synchronize_folder_with_local_changes=False
)

synchronize_folders.sync(
    client=dqops_client,
    json_body=request_body
)

```



### **Push data quality results to a private cloud**
The local Data Quality Data Warehouse is not limited to be synchronized with DQOps Cloud data lake.
Each user can set up a secondary file synchronization process that would replicate all the files in the `.data` folder
to a different location, using the user's owned S3 buckets, Storage Accounts or Cloud Storage.

Because the files are organized as a Hive-compatible data lake, it is possible to synchronize the data files
to any on-premise or cloud hosted data lake. The data files for each table should be registered as external tables.
This architecture will allow to build a private Data Quality Data Warehouse with custom data quality dashboards
using any SQL engine that can query Hive-compatible external tables. To be precise, the files could be queried
using Apache Hive, Apache Spark, DataBricks, Google BigQuery, Presto, Trino, SQL Server, Azure Synapse, Snowflake,
AWS Athena, and AWS Redshift Spectrum.


## What's next
- Review the [Parquet schema](../reference/parquetfiles/index.md) of all tables stored in the `.data` folder
- Learn how to [delete selected data quality results](../working-with-dqo/delete-data-quality-results.md)
- Learn about other folders stored in the **[DQOps user home](dqops-user-home-folder.md)** folder
- Review the architecture of DQOps and [how folders in the `DQOps user home` are handled](architecture/dqops-architecture.md)