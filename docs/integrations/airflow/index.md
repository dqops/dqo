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
DQOps publishes the package on the official repository Python Package Index (PyPI). https://pypi.org/project/dqops/


## Combining DQOps with data pipelines in Airflow.

Start the observation of the data quality at every step of the existing data pipelines.
Might your technology stack is based on a database that is supported in DQOps. 
[Check the complete list of the built databases connectors](../../data-sources/index.md).

For an immediate checks setup DQOps platform implements dozens of sensors. 
Just activate a few of them choosing from the list.

Any business specific type of data verification is possible since checks are that are fully customizable.


## Troubleshooting 

In case you get the error in a task based on DQOps airflow operator in logs, here are some solution to known issues: 

**httpx.ConnectError: [Errno 111] Connection refused**

This error points out that the DQOps python client can not connect to the DQOps application. The reasons are:

- DQOps application is down, server is not running
- Passed url to application is invalid 
- The DQOps server is not reachable due to network configuration 
