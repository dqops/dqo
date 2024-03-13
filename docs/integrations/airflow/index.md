# Airflow integration
This overview shows the list of Apache Airflow operators provided by DQOps that you can use to monitor data quality directly in the data pipelines. 

## Overview

DQOps integrates very easily with Apache Airflow. 

Operators are available for actions such as:

- **[DqopsRunProfilingChecksOperator](./run-checks-operator.md)** runs data quality checks
- **[DqopsCollectStatisticsOperator](./collect-statistics-operator.md)** collects basic statistics about tables
- **[DqopsTableImportOperator](./table-import-operator.md)** imports the table schema to DQOps, 
  setting up the default data quality checks
- **[DqopsAssertTableStatusOperator](./table-status-operator.md)** verifies the most recent data quality status of a table,
  the operator breaks DAG processing if any **fatal** severity issues are detected in the most recent data quality check run 
- **[DqopsWaitForJobOperator](./wait-for-job-operator.md)** tracks the execution of long-running tasks


## DQOps python package for Airflow

To use DQOps Airflow Operator an installation of the python package is required.
DQOps publishes the package on the [official repository Python Package Index (PyPI)](https://pypi.org/project/dqops/). 


## Combining DQOps with data pipelines in Airflow

Start the monitoring data quality at each stage of existing data pipelines.
[DQOps supports all commonly used databases, check out the complete list here](../../data-sources/index.md).


## Troubleshooting 

In case you get the error in a task based on DQOps airflow operator in logs, here are some solution to known issues: 

**httpx.ConnectError: [Errno 111] Connection refused**

This error points out that the DQOps python client can not connect to the DQOps instance. The reasons are:

- DQOps instance is down, or the server is not working
- The DQOps base url passed to the operator is invalid 
- The DQOps server is not reachable due to networking issues 


## What's next
- Look at the use case of [integrating DQOps data quality checks with Dbt](../dbt/index.md) to learn how to run Dbt and DQOps together in Apache Airflow.
