# Integration DQOps with Dbt
This guide lists the integration options between DQOps and Dbt for running data quality checks along Dbt data transformations.

## Overview

Airflow orchestrated pipelines for loading data do not provide observability facilities.
It requires to open external applications or web pages and to search for loaded data.
The inspection of loaded data with simple checks are configured complexly in Airflow.
It also limits the long-term view perspective on the issue, because the review of a single result of e.g. an SQL query to the data has a lack of context.

DQOps provides the automatic verification of the data that are available straight in the Airflow DAG execution details.
Also, when the data issue occurs, it will be visible on the main Airflow page.

All results are collected to the DQOps platform where the seasonal data issues can be observed.

Using the DQOps with the **dbt** provides additional prospect to control the pipeline depending on the data.
Loading data to a broken table can be prevented, which might ease the cleaning of it.

DQOps works with **Dbt Core** and **DbtCloud** as well.


## Pre-execution requirements

Entry requirements include:

- Installation of python package from PyPi called dqops
- Configuration of data source and checks in DQOps
- (DbtCloud only) Installation of apache-airflow-providers-dbt-cloud with dbt operators


## Integration examples

- [Dbt cloud with run checks](dbt-cloud-run-checks-use-case.md)
- [Dbt cloud with table status](dbt-cloud-table-status-use-case.md)
- [Dbt core with run checks](dbt-core-use-case.md)


## What's next

- [Learn about run checks Airflow operator](../airflow/run-checks-operator.md)
- [Learn about wait for job Airflow operator](../airflow/wait-for-job-operator.md)

