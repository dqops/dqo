# Airflow setup 

DQOps integrates with Apache Airflow. 

Operators are available for actions such as:
- run checks
- (Planned) collect statistics
- (Planned) import tables
- table status verification


## DQOps python package for airflow

To use DQOps Airflow Operator an installation of the python package is required.
DQOps publishes the package on the official repository Python Package Index (PyPI). https://pypi.org/project/dqops/


## Troubleshooting 

In case you get the error in a task based on DQOps airflow operator in logs, here are some solution to known issues: 

**httpx.ConnectError: [Errno 111] Connection refused**

This error points out that the DQOps python client can not connect to the DQOps application. The reasons are:
- DQOps application is down, server is not running
- Passed url to application is invalid 
- The DQOps server is not reachable due to network configuration 
