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

The following diagram shows all DQOps component core in action:

![DQOps deployment as a PyPi package](https://dqops.com/docs/images/architecture/DQOPs-pypi-package-instance-components-min.png)



`DQOps runtime`

: fdfad


`DQOps user home`

: fdfad

- dfa
- fdasfa


`DQOps Cloud Data Quality Data Lake`

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