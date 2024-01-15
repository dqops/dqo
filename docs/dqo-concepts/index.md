# DQOps key concepts overview
Follow this guide to learn each concept of DQOps Data Quality Operations Center to start measuring data quality for data sources.

## List of DQOps concepts
These topics introduce the basic concepts of DQOps.

 - **[Data quality checks](definition-of-data-quality-checks/index.md)**

    A data quality check detects data quality issues. The check in DQOps is defined as a pair
    of a [sensor](definition-of-data-quality-sensors.md) that captures metrics from the data source and a [rule](definition-of-data-quality-rules.md) that verifies the sensor's readout.
    For example, the [nulls_percent](../checks/column/nulls/nulls-percent.md) check uses both the [null_percent](../reference/sensors/column/nulls-column-sensors.md#null-percent) sensor
    and the [max_percent](../reference/rules/Comparison.md#max-percent) rule to detect if the maximum percent
    of null values in a tested column is satisfied.

    If the percent of null values in a column raises above the threshold (maximum allowed percent),
    a data quality issue is raised.

    DQOps has three types of data quality checks:
   
     - [Profiling checks](definition-of-data-quality-checks/data-profiling-checks.md) for measuring the initial data quality score during the profiling stage

     - [Monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) for observing and measuring the data quality daily or monthly

     - [Partition checks](definition-of-data-quality-checks/partition-checks.md) for analyzing partitioned data incrementally, or analyzing
       incrementally any table that has a date column.


 - **[Configuring data sources](configuring-data-sources.md)**
 
    DQOps stores the data source configuration and the metadata of imported tables in YAML files.
    The files are stored in the current folder, called the [`DQOps user home`](dqops-user-home-folder.md). 

    The connection parameters to data sources and metadata of imported tables can be edited in two ways,
    by using the [DQOps user interface](dqops-user-interface-overview.md), or by changing the YAML configuration files directly.
    Other methods of managing data sources include also using the [command-line interface](command-line-interface.md),
    or using the [DQOps Python client](../client/index.md).


 - **[Configuring data quality checks](configuring-data-quality-checks-and-rules.md)**

    Data quality checks are configured by setting the incident alerting thresholds
    by setting the [data quality rule](definition-of-data-quality-rules.md) parameters.

    DQOps uses YAML files to keep the configuration of data sources and the activated data quality checks on monitored tables.
    The DQOps YAML file format is fully documented and the YAML schema files are published.
    
    The DQOps YAML schema files allow unprecedented coding experience with Visual Studio Code when managing the
    configuring the data quality checks directly in the editor. Code completion, syntax validation and help hints
    are shown by Visual Studio Code and many other editors when editing DQOps YAML files.
    

 - **[Categories of checks](types-of-data-quality-checks/index.md)** 

    The most common data quality issues that are detected by DQOps are divided into **categories**.
    Read this section to learn what type of data quality problems are detected by DQOps out-of-the-box.
    For any other type of data quality issue, you can define custom data quality checks.


 - **[Running data quality checks](running-data-quality-checks.md)**

    Data quality checks configured for each table and column are executed by targeting the data source, table, column,
    check name, check type, check category or even labels assigned to tables or columns.


 - **[Data observability](data-observability.md)**

    When a new data source is imported into DQOps, a default set of data quality checks is activated.
    The main purpose of data observability is to detect anomalies in the data, database downtimes, schema changes,
    uniqueness issues, and an inconsistent growth of the table volume.


 - **[DQOps user home](dqops-user-home-folder.md)**

    `DQOps user home` is the most important folder, it is the place where DQOps stores all configuration and data quality results.
    When DQOps is started by running `python -m dqops`, the current working folder is used as the default `DQOps user home`.

    On the first run, DQOps will set up a folder tree to store the list of monitored data sources and the configuration of data quality
    checks for all imported tables. The configuration is stored in YAML files for simplicity of editing in Visual Studio Code.


 - **[Data quality sensors](definition-of-data-quality-sensors.md)**

    The data quality `sensors` are SQL queries defined as Jinja2 templates. A sensor is called by a data quality check
    to capture a data quality measure such as the row count from the monitored source. The sensor's measure is called
    a `sensor readout` in DQOps.


 - **[Data quality rules](definition-of-data-quality-rules.md)**

    Data quality rules in DQOps are Python functions that receive the `sensor readout`
    that was captured by sensor (a result of an SQL query).
    The rule verifies if the `sensor readout` is valid or a data quality issue should be raised.
    For example, the [max_percent](../reference/rules/Comparison.md#max-percent) rule will verify if the result
    of the [null_percent](../reference/sensors/column/nulls-column-sensors.md#null-percent) sensor is valid.


 - **[Data quality KPIs](definition-of-data-quality-kpis.md)**

    The data quality is measured by data quality KPIs (Key Performance Indicators).
    The definition of a data quality KPI in DQOps is a percentage of passed data quality checks out of all executed data quality checks.
    
    The data quality KPIs are calculated on multiple levels:
    
    - per table

    - per data source

    - per data quality dimension

    - or a combination of any other dimensions


 - **[Incremental data quality monitoring](incremental-data-quality-monitoring.md)**

    Learn how [partition checks](definition-of-data-quality-checks/partition-checks.md) are used to analyze data quality incrementally,
    even for very big tables, reaching terabyte or petabyte scale.

    Partitioned checks introduced by DQOps allow to detect data quality issues very early, as soon as invalid data was loaded
    in the most recent batch.


 - **[Data quality dashboards](types-of-data-quality-dashboards.md)**

    DQOps stores the data quality check results locally, but also the data is synchronized to a `Data Quality Data Warehouse`
    hosted in the cloud by DQOps for each user.

    The data quality dashboards are accessing the `Data Quality Data Warehouse` and enable reviewing the `Data Quality KPIs`
    or drilling down to the data quality issues. DQOps uses a custom Google Looker Studio Community Connector to access
    the user's `Data Quality Data Warehouse`.


 - **[Data quality dimensions](data-quality-dimensions.md)**

    The data quality dimensions are the fundamental way to group data quality checks into groups of checks that detect similar issue.
    The most important data quality dimensions supported by DQOps are:

    - `Availability` watches the tables in the data source, raising a data quality issue when the table is missing or returns errors

    - `Accuracy` checks compare the data to the "source of truth", which means comparing tables between stages and data sources

    - `Consistency` monitors the data over a period of time, looking for anomalies such as the usual percent of null values
      per day was within the regular range, but an unusual increase of the percent of null values in a column was observed for one day

    - `Completeness` detects missing data, for example columns with too many null values

    - `Reasonableness` identifies values that are not making sense, falling out of expected range

    - `Timeliness` tracks freshness of data, measuring the maximum allowed age of data
   
    - `Uniqueness` finds issues related to duplicate values

    - `Validity` detects common field format issues, such as an *email* field does not meet the email format


 - **[Data grouping](measuring-data-quality-with-data-grouping.md)**

    DQOps unique feature is the ability to use a **GROUP BY** clause in the data quality sensors, allowing to run data quality checks
    for multiple ranges of rows in the same table.

    For example, a table *fact_global_sales* that aggregates sales fact rows from multiple countries can be tested for each country.
    A column that identifies a country must be present in the table and data grouping must be configured.

    Data grouping allows detecting data quality issues for groups of rows loaded by different data streams,
    different data pipelines, or received from different vendors or departments.


 - **[User interface overview](dqops-user-interface-overview.md)**

    The user interface in DQOps is using a tabbed application that resembles many popular database management tools.
    Configuring data quality checks on multiple tables at the same time is supported in separate tabs.  


 - **[Command-line interface](command-line-interface.md)**

    Command-line access to DQOps is supported by a shell interface. The DQOps shell supports command and table name completion.


 - **[Data storage](data-storage-of-data-quality-results.md)**

    DQOps stores both the configuration of data sources, the configuration of data quality checks activated on tables
    and the data quality check execution results locally in a `DQOps user home` folder.

    The data quality results are stored in a *$DQO_USER_HOME/.data* folder that is a Hive-compliant local data lake.
    Please read the [data storage](data-storage-of-data-quality-results.md) concept guide to understand the data lake structure.


 - **[Deployment architecture](architecture/dqops-architecture.md)**

    DQOps can be hosted locally, in the cloud or as a hybrid deployment, running a semi-offline DQOps instances
    on-premise or in the customer's cloud environment.


 - **[Check execution flow](architecture/data-quality-check-execution-flow.md)**

    Detailed data quality execution flows that show how DQOps executes [data quality sensors](definition-of-data-quality-sensors.md),
    [data quality rules](definition-of-data-quality-rules.md), [data quality checks](definition-of-data-quality-checks/index.md),
    and how the data is [stored](data-storage-of-data-quality-results.md). Learn how execution errors are stored.
 