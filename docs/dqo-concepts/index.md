---
title: DQOps Data Quality Operations Center Concepts
---
# DQOps Data Quality Operations Center Concepts
Follow this guide to learn each concept of DQOps Data Quality Operations Center to start measuring data quality for data sources.

## Introduction

### **What is DQOps?**

DQOps is a powerful data quality and observability platform designed to address the entire data lifecycle,
from initial data assessment to advanced automation.

* Quickly start a local data quality environment.
* Configure data quality checks using the user interface or YAML files. Automate this process with rule mining engine and built-in data quality policies. 
* Run data quality checks directly from your data pipelines.
* Utilize the user interface for easy testing and issues review.
* Receive incident notifications via email or webhook, and create multiple notification filters to customize alerts for specific scenarios.

### **Who needs DQOps?**
DQOps is designed to meet the diverse needs of various data stakeholders across different stages of the data platform lifecycle.

**Data engineers** need to integrate data quality checks directly into data pipelines, test the quality of data sources before they are transformed, and verify the data quality of target tables populated by the pipeline.

**Data steward**, who ensure the trustworthiness and usability of data, need a robust data quality platform to validate the quality of data assets and manage data cleansing workflows to address any issues.

**Data consumer (data scientists and data analysts)** want to know the data quality score for tables and quickly assert their expectations about the data quality of essential data sources.

### **When do you need DQOps?**
DQOps is essential for organizations that

* Need to assess the data quality of new data sources.
* Want to establish robust data observability practice to monitor data ingestion, transformation, and storage processes to detect anomalies, errors, or deviations from expected behavior.
* Aim to demonstrate data quality issues to business sponsors using the user interface and data quality dashboards.

### **How DQOps works?**
Download DQOps directly from PyPI.
Run DQOps locally without configuring databases or set up on-premise environment.
Assess your data with basic statistics and automatically configure profiling checks using the rule mining engine.
Activate data observability by setting up monitoring checks to automatically detect new data quality issues in the future.
Receive notifications for critical issues and track their resolution.

DQOps does not use a database to store the configuration. Instead, all data quality configuration files are stored in 
YAML files. This code-first approach allows the data quality check configuration to be stored in a source code repository
and versioned along with other pipeline or machine learning code.

## List of DQOps concepts
This article is a dictionary of DQOps terms. Click on the links to learn about every concept.

### **[Data quality process](data-quality-process.md)**
DQOps follows a two-stage data quality process. The first step is a [data quality assessment](data-quality-process.md#data-quality-assessment) using the basic statistics and [data profiler](definition-of-data-quality-checks/data-profiling-checks.md). 
This step identifies confirmed data quality issues. In the second stage,
users configure [monitoring](definition-of-data-quality-checks/data-observability-monitoring-checks.md) and [partition checks](definition-of-data-quality-checks/partition-checks.md) that regularly verify data quality using Data Observability.


### **[Data quality rule miner](data-quality-rule-mining.md)**
The data quality rule miner is responsible for proposing the configuration of
[data quality checks](definition-of-data-quality-checks/index.md) and their
[rule thresholds](definition-of-data-quality-rules.md) to detect [the most common data quality checks](data-quality-rule-mining.md#information-used-for-proposing-checks).


### **[What is a data quality check](definition-of-data-quality-checks/index.md)**
A data quality check detects data quality issues. The check in DQOps is defined as a pair
of a [sensor](definition-of-data-quality-sensors.md) that captures metrics from the data source and a [rule](definition-of-data-quality-rules.md) that verifies the sensor's readout.
For example, the [nulls_percent](../checks/column/nulls/nulls-percent.md) check uses both the [null_percent](../reference/sensors/column/nulls-column-sensors.md#null-percent) sensor
and the [max_percent](../reference/rules/Comparison.md#max-percent) rule to detect if the maximum percent
of null values in a tested column is satisfied.

If the [percent of null values](../checks/column/nulls/nulls-percent.md) in a column raises above the threshold 
(maximum allowed percent), a data quality issue is raised.

DQOps has three types of data quality checks:

- [Profiling checks](definition-of-data-quality-checks/data-profiling-checks.md) for measuring the initial data quality score during the profiling stage

- [Monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) for observing and measuring the data quality daily or monthly

- [Partition checks](definition-of-data-quality-checks/partition-checks.md) for
  [analyzing partitioned data incrementally](incremental-data-quality-monitoring.md), or analyzing
  incrementally any table that has a date column.


### **[User interface overview](dqops-user-interface-overview.md)**
The user interface in DQOps is using a tabbed application that resembles many popular database management tools.
Configuring data quality checks on multiple tables at the same time is supported in separate tabs.


### **[Configuring data sources](configuring-data-sources.md)**
DQOps stores the data source configuration and the metadata of imported tables in YAML files.
The files are stored in the current folder, called the [`DQOps user home`](dqops-user-home-folder.md). 

The connection parameters to data sources and metadata of imported tables can be edited in two ways,
by using the [DQOps user interface](dqops-user-interface-overview.md), or by changing the YAML configuration files directly.
Other methods of managing data sources include also using the [command-line interface](command-line-interface.md),
or using the [DQOps Python client](../client/index.md). Review the [list of data quality sources](../data-sources/index.md)
to see which databases are supported by DQOps. 


### **[Configuring table metadata](configuring-table-metadata.md)**
The metadata and the [configuration of all data quality checks](configuring-data-quality-checks-and-rules.md)
is stored in [*.dqotable.yaml*](../reference/yaml/TableYaml.md) YAML files, following a naming convention
`sources/<data_source_name>/<schema_name>.<table_name>.dqotable.yaml`. 
The files can be stored and versioned in your Git repository. 

You can also easily add similar tables, or move the data quality check configuration from
development tables to production tables by copying and renaming the [*.dqotable.yaml*](../reference/yaml/TableYaml.md) files.


### **[Configuring data quality checks](configuring-data-quality-checks-and-rules.md)**
Data quality checks are configured by setting the incident alerting thresholds
by setting the [data quality rule](definition-of-data-quality-rules.md) parameters.

DQOps uses YAML files to keep the configuration of data sources and the activated data quality checks on monitored tables.
The [DQOps YAML file format](../reference/yaml/index.md) is fully documented and the YAML schema files are published.

The DQOps YAML schema files allow unprecedented 
[coding experience with Visual Studio Code](../integrations/visual-studio-code/index.md) when managing the
configuring the data quality checks directly in the editor. Code completion, syntax validation and help hints
are shown by Visual Studio Code and many other editors when editing DQOps YAML files.


### **[Running data quality checks](running-data-quality-checks.md)**
Data quality checks configured for each table and column are executed by targeting the data source, table, column,
check name, check type, check category or even labels assigned to tables or columns.


### **[Data quality rule mining](data-quality-rule-mining.md)**
DQOps supports data quality rule mining, a feature that uses statistics and sample data to automatically configure data quality checks,
which will detect the most common data quality issues without manual configuration.


### **[Data observability](data-observability.md)**
When a new data source is imported into DQOps, a default set of data quality checks is activated.
The main purpose of data observability is to detect anomalies in the data, database downtimes, schema changes,
uniqueness issues, and an inconsistent growth of the table volume.


### **[DQOps user home](dqops-user-home-folder.md)**
`DQOps user home` is the most important folder, it is the place where DQOps stores all configuration and data quality results.
When DQOps is started by running `python -m dqops`, the current working folder is used as the default `DQOps user home`.

On the first run, DQOps will set up a folder tree to store the list of monitored data sources and the configuration of data quality
checks for all imported tables. The configuration is stored in YAML files for simplicity of editing in Visual Studio Code.


### **[Data quality sensors](definition-of-data-quality-sensors.md)**
The data quality `sensors` are SQL queries defined as Jinja2 templates. A sensor is called by a data quality check
to capture a data quality measure such as the row count from the monitored source. The sensor's measure is called
a `sensor readout` in DQOps.


### **[Data quality rules](definition-of-data-quality-rules.md)**
Data quality rules in DQOps are Python functions that receive the `sensor readout`
that was captured by sensor (a result of an SQL query).
The rule verifies if the `sensor readout` is valid or a data quality issue should be raised.
For example, the [max_percent](../reference/rules/Comparison.md#max-percent) rule will verify if the result
of the [null_percent](../reference/sensors/column/nulls-column-sensors.md#null-percent) sensor is acceptable.


### **[Data quality KPIs](definition-of-data-quality-kpis.md)**
The data quality is measured by data quality KPIs (Key Performance Indicators).
The definition of a data quality KPI in DQOps is a percentage of passed data quality checks out of all executed data quality checks.

The data quality KPIs are calculated on multiple levels:

- per table

- per data source

- per data quality dimension

- or a combination of any other dimensions


### **[Incremental data quality monitoring](incremental-data-quality-monitoring.md)**
Learn how [partition checks](definition-of-data-quality-checks/partition-checks.md) are used to analyze data quality incrementally,
even for very big tables, reaching terabyte or petabyte scale.

Partitioned checks introduced by DQOps allow to detect data quality issues very early, as soon as invalid data was loaded
in the most recent batch.


### **[Data quality dashboards](types-of-data-quality-dashboards.md)**
DQOps stores the data quality check results locally, but also the data is synchronized to a `Data Quality Data Warehouse`
hosted in the cloud by DQOps for each user.

The data quality dashboards are accessing the `Data Quality Data Warehouse` and enable reviewing the `Data Quality KPIs`
or drilling down to the data quality issues. DQOps uses a custom Google Looker Studio Community Connector to access
the user's `Data Quality Data Warehouse`.


### **[Data quality dimensions](data-quality-dimensions.md)**
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


### **[Auditing time periods](auditing-data-quality-with-time-periods.md)**
DQOps captures the time period for which the data quality result is valid. This can be the data quality status at the end of the day
in *[daily monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md#daily-monitoring-checks)*.
Learn how DQOps captures the local timezone of monitored data sources, even if monitoring databases are located
in different countries, regions and continents.


### **[Data grouping](measuring-data-quality-with-data-grouping.md)**
DQOps unique feature is the ability to use a **GROUP BY** clause in the data quality sensors, allowing to run data quality checks
for multiple ranges of rows in the same table.

For example, a table *fact_global_sales* that aggregates sales fact rows from multiple countries can be tested for each country.
A column that identifies a country must be present in the table and data grouping must be configured.

Data grouping allows detecting data quality issues for groups of rows loaded by different data streams,
different data pipelines, or received from different vendors or departments.


### **[Command-line interface](command-line-interface.md)**
Command-line access to DQOps is supported by a shell interface. The DQOps shell supports command and table name completion.


###  **[Data storage](data-storage-of-data-quality-results.md)**
DQOps stores both the configuration of data sources, the configuration of data quality checks activated on tables
and the data quality check execution results locally in a `DQOps user home` folder.

The data quality results are stored in a *$DQO_USER_HOME/.data* folder that is a Hive-compliant local data lake.
Please read the [data storage](data-storage-of-data-quality-results.md) concept guide to understand the data lake structure.


### **[Deployment architecture](architecture/dqops-architecture.md)**
DQOps can be hosted locally, in the cloud or as a hybrid deployment, running a semi-offline DQOps instances
on-premise or in the customer's cloud environment.


### **[Check execution flow](architecture/data-quality-check-execution-flow.md)**
Detailed data quality execution flows that show how DQOps executes [data quality sensors](definition-of-data-quality-sensors.md),
[data quality rules](definition-of-data-quality-rules.md), [data quality checks](definition-of-data-quality-checks/index.md),
and how the data is [stored](data-storage-of-data-quality-results.md). Learn how execution errors are stored.
 

## Other topics
Check out the other areas of the DQOps documentation.

### **[Installing DQOps](../dqops-installation/index.md)**
Learn now to install DQOps using [pip](../dqops-installation/install-dqops-using-pip.md), [docker](../dqops-installation/run-dqops-as-docker-container.md),
or installing locally from a [release package](../dqops-installation/install-dqops-from-release-package.md).


### **[List of data sources](../data-sources/index.md)**
The list of supported data sources and how to register them in DQOps.


### **[DQOps use cases](../examples/index.md)**
Review a list of data quality use cases, and how to detect most common data quality issues with DQOps.
Each use case is a step-by-step guide, starting with the description of a problem, followed by the steps to configure
relevant data quality checks, and finally showing the data quality dashboards.


###  **[Working with DQOps](../working-with-dqo/index.md)**
The remaining step-by-step manuals not included in the DQOps basic concepts guide.


### **[Integrations](../integrations/index.md)**
Find out how DQOps integrates with other systems. How to run [data quality checks in Apache Airflow](../integrations/airflow/index.md),
or how to send data quality incident notifications to [Slack](../integrations/slack/configuring-slack-notifications.md).


### **[Command-line interface](../command-line-interface/index.md)**
DQOps supports running commands directly from the [operating system shell](command-line-interface.md#integrating-dqops-into-shell-scripts),
or using the [DQOps integrated shell](command-line-interface.md). The [command-line interface](../command-line-interface/index.md)   
is a reference of all DQOps commands.


### **[REST API Python Client](../client/index.md)**
Using DQOps is not limited only to the [user interface](dqops-user-interface-overview.md), or the
[command-line shell](../command-line-interface/index.md). All operations such as [running data quality checks](../client/operations/jobs.md#run_checks)
are also supported from a DQOps Python client. The [REST API Python Client](../client/index.md) shows ready-to-use Python code samples.


### **[Data quality checks reference](../checks/index.md)**
The reference of all data quality checks that are included in DQOps. The reference of each data quality check has
a YAML configuration fragment, and examples of SQL queries for each data source.


### **[Reference](../reference/index.md)**
The full reference of all [data quality sensors](../reference/sensors/index.md), [data quality rules](../reference/rules/index.md),
[DQOps YAML files](../reference/yaml/index.md) and [DQOps Parquet schema](../reference/parquetfiles/index.md). 

