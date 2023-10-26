# DQOps architecture
DQOps is designed to support multiple deployment options, including both a local development and a multi-user production environments.

## DQOps core components

The principal design idea behind DQOps is a great integration with DevOps and GitOps pipelines, without sacrificing 
simplicity of use for less technical users who favour managing data quality checks using a user interface, instead of
editing YAML files. Technical users should be able to manage data quality check configuration at scale by changing YAML
files in their editor of choice and version the configuration in Git. Non-technical users should be able to 
configure data quality checks from the browser.

DQOps achieves its principal idea by separating the platform into four core components:

- `DQOps runtime` - All-in-one data quality engine that contains both
    * an embedded web server hosting the user interface and the REST API for integration with external tools
    * command-line shell interface for running commands
  
- `DQOPs user home` - Local folder storing the configuration of enabled data quality checks and a local copy of all
  data quality results, enabling offline and hybrid deployment models.
  The data model is described in the [Data storage](../data-storage/data-storage.md) documentation.
 
- `DQOps Cloud` - DQOps hosted cloud backend for exposing data quality dashboards which has the following components:
    * **Data Quality Data Lake** - Storage buckets with a copy of data quality check configuration and a copy of
      data quality results. Each user who created a DQOps Cloud account receives a private data lake. 
    * **Data Quality Data Warehouse** - BigQuery external and native tables storing all data quality sensor readouts, data quality
      check results, errors, incidents. A private BigQuery dataset is created for each user.
    * **Data Quality Dashboards** - Dashboards developed by DQOps that present data quality issues and KPIs. The dashboards
      are designed using Looker Studio. They are accessing the Data Quality Data Warehouse using a dedicated
      DQOps Looker Studio Community Connector. 

- `Python client` - DQOps python client provides typed access to all operations supported by DQOps user interface, enabling
  complex automation of all operations. The client supports:
    * running data quality checks from external tools
    * importing metadata
    * managing data quality results
    * detecting the data quality status of monitored tables
    * changing the configuration of data quality checks


## DQOps local deployment
The simplest way to start using DQOps as a local, standalone data quality tool is by starting it as a Python module.
Despite that DQOps is mostly developed as a Java application based on Spring Boot, it is available also as a 
[dqops](https://pypi.org/project/dqops/) PyPi package.

Once DQOps is [installed from PyPI](../../working-with-dqo/installation/install-dqo-using-pip.md), it should be started by

  ```
  python -m dqops
  ```

During the first start, DQOps will download the full DQOps distribution from [DQOps releases on GitHub](https://github.com/dqops/dqo/releases)
and will additionally download Java JRE 17.

The following diagram shows all DQOps core components in action:

![DQOps deployment as a PyPi package](https://dqops.com/docs/images/architecture/DQOPs-pypi-package-instance-components-min.png)

The DQOps local instance (in the middle of the diagram) is a running DQOps instance that references a local
`DQOps user home` folder with the metadata, data quality check configuration and results.

Internally, DQOps distribution uses another folder that contains the definition of built-in data quality checks,
sensors and rules. It is a local copy of the [DQOps home source folder](https://github.com/dqops/dqo/tree/develop/home).

DQOps communicates with the DQOps Cloud, replicating the content of the `DQOps user home` folder to the 
Data Quality Data Lake. The parquet files with the data quality check results are then loaded to a Data Quality Data Warehouse
and presented as embedded Looker Studio data quality dashboards inside the DQOps web interface.

## DQOps local instance components
DQOps components working locally are described below, following the top-down order of components on the diagram.

`DQOps home`

[DQOps home](https://github.com/dqops/dqo/tree/develop/home) contains the definition of built-in data quality checks,
sensors, rules, and dashboard configuration files. The folders in the `DQOps home` are:

- *$DQO_HOME/sensors* - Jinja2 SQL templates for data quality sensors, these are SQL SELECT statements that capture
  metrics from monitored data sources, such as the row count on a table level or a nulls count on a column level.
- *$DQO_HOME/rules* - Python functions that are called by DQOps to verify metrics obtained by running sensor queries on the
  monitored data source.
- *$DQO_HOME/checks* - YAML files that configure the mapping between a data quality sensor and a data quality rule. 
  For example, a data quality check [nulls-percent](../../checks/column/nulls/nulls-count.md) is a pair of the
  [null_percent](../../reference/sensors/column/nulls-column-sensors.md) sensor and the [max_percent](../../reference/rules/Comparison.md#max-percent) rule.
  The definition of the default data quality checks in this folder cannot be modified to change the configuration
  of the data quality check (change the rule). They are provided for reference only and to allow creating similar custom checks
  in the `DQOps user home`*/checks* folder.
- *$DQO_HOME/settings* - This folder contains the default list of the built-in data quality dashboards stored in the
  [settings/dashboardslist.dqodashboards.yaml](../../reference/yaml/DashboardYaml.md) file. The original 
  *dashboardslist.dqodashboards.yaml* file can be found on GitHub
  [here](https://github.com/dqops/dqo/blob/develop/home/settings/dashboardslist.dqodashboards.yaml).
- *$DQO_HOME/lib* - Python engine entry point modules that are rendering SQL queries from Jinja2 templates and evaluate 
  the data quality rules. DQOps Java JVM runtime starts two Python processes to run the 
  [evaluate_rules.py](https://github.com/dqops/dqo/blob/develop/home/lib/evaluate_rules.py) and the
  [evaluate_templates.py](https://github.com/dqops/dqo/blob/develop/home/lib/evaluate_templates.py) modules. 
- *$DQO_HOME/bin* - DQOps shell scripts and binary libraries required to start DQOps.
- *$DQO_HOME/jars* - Java jar libraries for JDBC connectors that are not bundled and the DQOps combined jar library. These files are
  not found on GitHub and are found only in release packages.

`DQOps`

DQOps runs as a Java JVM process that starts two additional Python processes to run the Jinja2 template engine
and call data quality rules as Python functions. 
DQOps java process also exposes a http web server. The default port is 8888, but could be changed 
by setting the [--server.port](../../command-line-interface/dqo.md) startup parameter.

`DQOps user home`

The *DQOps user home* folder (abbreviated as the *$D.U.H*) is the location where DQOps stores YAML metadata files, custom definitions for data quality checks
and the data folders. The detailed description of the folder is [here](../data-storage/data-storage.md).
DQOps uses the current working folder as the *DQOps user home*, unless a different folder was specified
by setting the `$DQO_USER_HOME` environment variable or passing a *--dqo.user.home=<alternative_user_home_location>* startup parameter.

The most important folders in the *DQOps user home* are:

- *./sources* (or *$DQO_USER_HOME/sources*) stores the metadata of data sources with the configuration of enabled data quality checks.
  This is the folder that will be actively used to change the data quality check configuration directly in YAML files.
  The *./sources* folder has nested folders for data sources, the name of the data source is simply the folder name.
  Each data source's subfolder contains the data source configuration file [connection.dqoconnection.yaml](../../reference/yaml/ConnectionYaml.md).
  The remaining files are the metadata files for each table, named as [<schema_name>.<table_name>.dqotable.yaml](../../reference/yaml/TableYaml.md). 
- *./sensors* (or *$DQO_USER_HOME/sensors*) is a place to create custom data quality sensor definitions. The data quality sensors
  are defined as Jinja2 templates of SQL queries. 
- *./rules* (or *$DQO_USER_HOME/rules*) is a place to create custom data quality rules. The data quality rules are Python modules
  that are called by DQOps to verify the results of the data quality metrics captured by the sensors.
- *./checks* (or *$DQO_USER_HOME/checks*) is a folder where custom data quality checks are defined. A custom data quality check
  is defined in [.checkspec.yaml](../../reference/yaml/CheckDefinitionYaml.md) files. The data quality check definition is a pair
  of a data quality sensor that captures a metric value and a data quality rule that will assess if the metric value is an issue or not.
- *./settings* (or *$DQO_USER_HOME/settings*) contains shared configuration that is synchronized to DQOps Cloud data lake.


`DQOps Cloud`

: fdfad


`Looker Studio Dashboards`

: fdfad


`Git repository`

: The metadata of monitored data sources and a configuration of enabled data quality checks that are stored
in the 


## Local development deployment as a PyPi package

![DQOps deployment as a PyPi package](https://dqops.com/docs/images/architecture/DQOPs-pypi-package-instance-components-min.png)


## Production deployment as a Docker container

![DQOps deployment in Docker](https://dqops.com/docs/images/architecture/DQOPs-docker-instance-components-min.png)

![DQOps on-premise deployment](https://dqops.com/docs/images/architecture/DQOps-architecture-components-on-premise-min.png)

![DQOps Cloud hybrid deployment](https://dqops.com/docs/images/architecture/DQOps-architecture-components-hybrid-min.png)

TODO: Add a diagram showing DQOPs internal components: file watcher, cache, sensor runner, rule runner, scheduler, queue, rest api,