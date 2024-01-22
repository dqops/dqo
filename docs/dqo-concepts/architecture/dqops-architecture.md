# DQOps architecture
This guide shows the components of a DQOps environment, and what are the possible deployment options for a SaaS-hosted, on-premise, or hybrid environment.

## Overview

DQOps is designed to support multiple deployment options, including both local development and multi-user production environments.

## DQOps core components

The principal design idea behind DQOps is a great integration with DevOps and GitOps pipelines, without sacrificing 
simplicity of use for less technical users who favor managing data quality checks using a user interface, instead of
editing YAML files. Technical users should be able to manage data quality check configuration at scale by changing YAML
files in their editor of choice and version the configuration in Git. Non-technical users should be able to 
configure data quality checks from the browser.

DQOps implements its principal idea by separating the platform into four core components:

- `DQOps runtime` - All-in-one data quality engine that contains both
    * an embedded web server hosting the user interface and the REST API for integration with external tools,
    * command-line shell interface for running commands.
  

- `DQOps user home` - Local folder that stores the configuration of activated data quality checks and a local copy of all
  data quality results, enabling offline and hybrid deployment models.
  The data model is described in the [Data storage](../data-storage-of-data-quality-results.md) documentation.

 
- `DQOps Cloud` - DQOps hosted cloud backend for exposing data quality dashboards which has the following components:
    * **Data Quality Data Lake** - Storage buckets with a copy of the data quality check configuration and a copy of
      data quality results. Each user who created a DQOps Cloud account receives a private data lake. 
    * **Data Quality Data Warehouse** - BigQuery external and native tables storing all data quality sensor readouts, data quality
      check results, errors, and incidents. A private BigQuery dataset is created for each user.
    * **Data Quality Dashboards** - Dashboards developed by DQOps that present data quality issues and KPIs. The dashboards
      are designed using Looker Studio. They are accessing the Data Quality Data Warehouse using a dedicated
      DQOps Looker Studio Community Connector. 


- `Python client` - The DQOps python client provides typed access to all [operations](../../client/operations/index.md) supported by the DQOps user interface, enabling
  complex automation of all operations. The client supports:
    * running data quality checks from external tools,
    * importing metadata,
    * managing data quality results,
    * detecting the data quality status of monitored tables,
    * changing the configuration of data quality checks.


- `Looker Studio Dashboards` - a set of data quality dashboards that are using a Looker Studio Community Connector for DQOps.
  The dashboards are accessing the **Data Quality Data Warehouse**.


![Data quality platform components](https://dqops.com/docs/images/concepts/dqops_components_min.png)


## DQOps local deployment as a Python package
The simplest way to start using DQOps as a local, standalone data quality tool is by starting it as a Python module.
Despite that DQOps is mostly developed as a Java application based on Spring Boot, it is available also as a 
[dqops](https://pypi.org/project/dqops/) PyPi package.

Once DQOps is [installed from PyPI](../../dqops-installation/install-dqops-using-pip.md), it should be started by

  ```
  python -m dqops
  ```

During the first start, DQOps will download the full DQOps distribution from [DQOps releases on GitHub](https://github.com/dqops/dqo/releases)
and will additionally download Java JRE 17.

The following diagram shows all DQOps core components in action:

![DQOps deployment as a PyPi package](https://dqops.com/docs/images/architecture/DQOPs-pypi-package-instance-components-min.png)

The DQOps local instance (in the middle of the diagram) is a running DQOps instance that references a local
`DQOps user home` folder with the metadata, data quality check configuration, and results.

Internally, DQOps distribution uses another folder that contains the definition of built-in data quality checks,
sensors, and rules. It is a local copy of the [DQOps home source folder](https://github.com/dqops/dqo/tree/develop/home).

DQOps communicates with the DQOps Cloud, replicating the content of the `DQOps user home` folder to the 
Data Quality Data Lake. The parquet files with the data quality check results are then loaded to a Data Quality Data Warehouse
and presented as embedded Looker Studio data quality dashboards inside the DQOps web interface.

## DQOps local instance components
DQOps components working locally are described below, following the top-down order of components on the diagram.


### **DQOps home**

[DQOps home](https://github.com/dqops/dqo/tree/develop/home) contains the definition of built-in data quality checks,
sensors, rules, and dashboard configuration files. The folders in the `DQOps home` are:

- `$DQO_HOME/sensors` - Jinja2 SQL templates for data quality sensors, these are SQL SELECT statements that capture
  metrics from monitored data sources, such as the row count on a table level or nulls count on a column level.

- `$DQO_HOME/rules` - Python functions that are called by DQOps to verify metrics obtained by running sensor queries on the
  monitored data source.

- `$DQO_HOME/checks` - YAML files that configure the mapping between a data quality sensor and a data quality rule. 
  For example, a data quality check [nulls-percent](../../checks/column/nulls/nulls-count.md) is a pair of the
  [null_percent](../../reference/sensors/column/nulls-column-sensors.md#null-percent) sensor and
  the [max_percent](../../reference/rules/Comparison.md#max-percent) rule.
  The definition of the default data quality checks in this folder cannot be modified to change the configuration
  of the data quality check (change the rule). They are provided for reference only and to allow creating similar custom checks
  in the `DQOps user home`*/checks* folder.

- `$DQO_HOME/settings` - This folder contains the default list of the built-in data quality dashboards stored in the
  [settings/dashboardslist.dqodashboards.yaml](../../reference/yaml/DashboardYaml.md) file. The original 
  *[dashboardslist.dqodashboards.yaml* file can be found on GitHub following the link](https://github.com/dqops/dqo/blob/develop/home/settings/dashboardslist.dqodashboards.yaml).

- `$DQO_HOME/lib` - Python engine entry point modules that render SQL queries from Jinja2 templates and evaluate 
  the data quality rules. DQOps Java JVM runtime starts two Python processes to run the 
  [evaluate_rules.py](https://github.com/dqops/dqo/blob/develop/home/lib/evaluate_rules.py) and the
  [evaluate_templates.py](https://github.com/dqops/dqo/blob/develop/home/lib/evaluate_templates.py) modules. 

- `$DQO_HOME/bin` - DQOps shell scripts and binary libraries required to start DQOps.

- `$DQO_HOME/jars` - Java jar libraries for JDBC connectors that are not bundled and the DQOps combined jar library. These files are
  not found on GitHub and are found only in release packages.


### **DQOps engine**

DQOps runs as a Java JVM process that starts two additional Python processes to run the Jinja2 template engine
and call data quality rules as Python functions. 
DQOps java process also exposes an http web server. The default port is 8888, but it could be changed 
by setting the [--server.port](../../command-line-interface/dqo.md) startup parameter.


### **DQOps user home**

The *DQOps user home* folder (abbreviated as the *$D.U.H*) is the location where DQOps stores YAML metadata files, custom definitions for data quality checks
and the data folders. You can find more [detailed description of the DQOps user home folder here](../data-storage-of-data-quality-results.md).
DQOps uses the current working folder as the *DQOps user home* unless a different folder was specified
by setting the `$DQO_USER_HOME` environment variable or passing a *--dqo.user.home=&lt;alternative_user_home_location&gt;* startup parameter.

The structure of the *DQOps user home* folder is fully described in the [DQOps use home](../dqops-user-home-folder.md) article.


### **Git repository**

The *DQOps user home* folder can be integrated with a full DataOps process by pushing the content of the *DQOps user home* to a Git repository manually.
The *.gitignore* file excludes folders that should not be stored in the code repository.
The excluded folders are *.data/*, *.credentials/*, *.logs/* and *.index/*.

## DQOps Cloud

The cloud-hosted side of a DQOps deployment is responsible for hosting data quality dashboards.
DQOps provides complimentary cloud infrastructure for users using a FREE DQOps license.
The DQOps Cloud components are:

### **Data Quality Data Lake** 
*Data Quality Data Lake* is composed of two GCP storage buckets that store the configuration files and Parquet data files. DQOps local
instance synchronizes files from the *./sources*, *./sensors*, *./rules*, *./checks*, *./settings* and *./.credentials*
to the configuration bucket. All files from the *./.data* folder are replicated to the second data bucket.
 
### **Data Quality Data Warehouse**
*Data Quality Data Warehouse* is a private data quality data warehouse that is set up for each DQOps Cloud account. The warehouse
uses the Parquet files that were replicated from the *./.data* folder as external tables. DQOps Cloud continuously loads
these parquet files (the data quality results) to BigQuery native tables. Direct access to the Data Quality Data Warehouse
requires a DQOps Cloud ENTERPRISE license.

### **Data Quality Dashboards**
DQOps provides *Data Quality Dashboards* over the most common data quality activities. The dashboards are
developed with Google Looker Studio and access the customer's data quality data warehouse using
a Looker Studio Community Connector provided by DQOps. The links and configuration parameters to the built-in dashboards
are defined in the [$DQO_HOME/settings/dashboardslist.dqodashboards.yaml](https://github.com/dqops/dqo/blob/develop/home/settings/dashboardslist.dqodashboards.yaml) file.
Please check the [data quality dashboard customization](../../working-with-dqo/review-the-data-quality-results-on-dashboards.md) manual
to learn how to change the built-in dashboards or add custom dashboards. 

Please contact [DQOps sales](https://dqops.com/contact-us/) to discuss other deployment options, including on-premise installation.


## DQOps client interfaces
DQOps provides various ways to interact with the system. The data quality engineers have a choice of using
the user-friendly interface or edit the YAML files directly in a text editor.

### **User interface**
Each DQOps instance starts an embedded web server that hosts the user interface locally.
The user interface interacts with the DQOps REST API.

### **Command-line interface**
When DQOps is started as a Python package in a console window, a [cli mode](../command-line-interface.md) is activated.
The shell mode supports running various operations from the command line, especially importing metadata of data sources
and running data quality checks. For a [full documentation of all DQOPs cli commands follow the link](../../command-line-interface/index.md).

### **REST API**
The web server that is embedded in DQOps exposes REST API endpoints for all operations.
The REST API can be used by external clients to run data quality checks.
Additionally, when complex automation of all data quality activities is required, DQOps REST API can be used directly.
A full description of all REST API operations is included in the [DQOps Python Client](../../client/index.md) documentation.

### **Python client**
The *dqops* Python module contains a typed Python [client](../../client/index.md) that is a wrapper over the DQOps REST API.
The source code of the module is available on [GitHub](https://github.com/dqops/dqo/tree/develop/distribution/python/dqops/client) for reference.
The Python client can be integrated with additional tools, called from data pipelines or imported directly into a Notebook, to execute
data quality checks directly using the [run_checks](../../client/operations/jobs.md#run_checks) function.

### **Apache Airflow operators**
DQOps can be used in Airflow's DAGs using DQOps operators. The most important DQOps Airflow operators are listed below.

- [run_checks_operator](../../integrations/airflow/run-checks-operator.md) that executes
  data quality checks as part of a DAG data pipeline. The operator can be executed before a data load operation to assess the quality
  of data sources and after the data loading operation to verify the loaded data. The DQOps operators can stop the data
  pipeline if any **fatal** severity data quality issues are detected.

- [assert_table_status_operator](../../integrations/airflow/table-status-operator.md) verifies
  the status of a source table that has already been analyzed for data quality issues as a separate process.


### **Direct file modifications**
The files in the *DQOps user home* can be edited directly. Especially, DQOps provides the YAML file schema for the Visual Studio Code,
enabling out-of-the-box code completion support for the [connection.dqoconnection.yaml](../../reference/yaml/ConnectionYaml.md) and the
[&lt;schema_name&gt;.&lt;table_name&gt;.dqotable.yaml](../../reference/yaml/TableYaml.md) table files.

DQOps depends on the file system change notification to detect file modifications. When a YAML file in the *DQOps user home*
is modified by the user or as a result of a *git pull* command, DQOps detects the change and invalidates a copy
of the file in the in-memory cache instantly.

## Production deployment as a Docker container
For long-running production deployment, DQOps should be started as a Docker container.
The [dqops/dqo](https://hub.docker.com/r/dqops/dqo) docker image can be pulled directly from Docker Hub.

The Docker deployment diagram does not differ much from running DQOps locally as a Python module.
![DQOps deployment in Docker](https://dqops.com/docs/images/architecture/DQOPs-docker-instance-components-min.png)

The following command will start DQOps as a docker container:

  ```
  docker run -d -v .:/dqo/userhome -p 8888:8888 dqops/dqo --dqo.cloud.api-key=here-our-DQOps-Cloud-API-key run
  ```

The only important differences are:

- *DQOps user home* folder must be mounted as a volume to the */dqo/userhome* folder inside the container.

- The web server port *8888* must be exposed from the container using the *-p 8888:8888* parameter.

- DQOps should be started in a headless mode (without the command-line shell).
  Starting DQOps in a server mode is activated with the [run](../../command-line-interface/run.md) command.

- the DQOps Cloud Pairing API Key must be given ahead of time in the *--dqo.cloud.api-key=* parameter
  because DQOps will not be able to ask the user to log in to DQOps Cloud when the command-line interface is disabled.
  The DQOps Cloud Pairing API Key is found on the [https://cloud.dqops.com/account](https://cloud.dqops.com/account) page.

For detailed steps required to start DQOps as a docker container, please read the 
[run dqo as a docker container](../../dqops-installation/run-dqops-as-docker-container.md) manual. 


## Local and hybrid deployment components

### **DQOps SaaS and hybrid deployments**
Paid instances of DQOps can fully utilize all DQOps Cloud features, including support for user roles, SSO integration,
and hybrid deployments.

DQOps deployment without a SaaS hosted DQOps instance is shown below.

![DQOps on-premise deployment](https://dqops.com/docs/images/architecture/DQOps-architecture-components-on-premise-min.png)

The additional components shown in this diagram are the DQOps SaaS Servers that provide the user management and
the data quality data warehouse management operations.

### **Hybrid deployments**
Paid DQOps subscriptions offer a DQOps instance hosted in the DQOps cloud.
The DQOps cloud instance can cooperate with additional on-premise instances that are synchronizing with the shared
DQOps Cloud Data Lake.

A hybrid deployment is presented below.

![DQOps Cloud hybrid deployment](https://dqops.com/docs/images/architecture/DQOps-architecture-components-hybrid-min.png)

A cloud-hosted VM running the DQOps instance is hosted in the cloud by DQOps. The users authorized to use the instance
are redirected to the DQOps Cloud login page to authenticate. It is also possible to configure a custom OpenID connector
to authenticate through the customer's login page.

## What's more
- Learn how DQOps [runs data quality checks, sensors, and rules](data-quality-check-execution-flow.md), and when the
  errors are reported.
- Learn how the [local data quality data lake](../data-storage-of-data-quality-results.md) is used to store a local copy
  of data quality results, and how the data is partitioned.
