# Airflow integration overview 

DQOps integrates with Apache Airflow. 

Operators are available for actions such as:

- **[DqopsRunProfilingChecksOperator](./run-checks-operator.md)** runs data quality checks
- **[DqopsCollectStatisticsOperator](./collect-statistics-operator.md)** collects basic statistics about tables
- **[DqopsTableImportOperator](./table-import-operator.md)** imports the table schema to DQOps, 
  setting up the default data quality checks
- **[DqopsAssertTableStatusOperator](./table-status-operator.md)** verifies the most recent data quality status of a table,
  the operator breaks DAG processing if any **fatal** severity issues are detected in the most recent data quality check run 


## DQOps python package for Airflow

To use DQOps Airflow Operator an installation of the python package is required.
DQOps publishes the package on the official repository Python Package Index (PyPI). https://pypi.org/project/dqops/


## Troubleshooting 

In case you get the error in a task based on DQOps airflow operator in logs, here are some solution to known issues: 

**httpx.ConnectError: [Errno 111] Connection refused**

This error points out that the DQOps python client can not connect to the DQOps application. The reasons are:

- DQOps application is down, server is not running
- Passed url to application is invalid 
- The DQOps server is not reachable due to network configuration 
