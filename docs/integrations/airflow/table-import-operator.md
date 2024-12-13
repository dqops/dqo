---
title: Airflow Table Import Operator with Example DAG
---
# Airflow Table Import Operator with Example DAG
Read this reference to learn how to use the Apache Airflow operator for importing metadata of tables for further data quality monitoring and testing.

## Overview

The Airflow table import operator **DqopsTableImportOperator** is used to import table to connection existing in DQOps.
When the table schema changes you can also use this operator. 
It will update the table in DQOps application for recently added columns or updates of types in the existing columns.

## Operator parameters

Parameters that identify the table that should be imported to DQOps.

| Name              | Description                                                                                                                                                                                                          | Type                                                          |
|-------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------|
| connection_name   | (Required) The name of the connection in DQOps.                                                                                                                                                                      | str                                                           |
| schema_name       | (Required) The name of the schema.                                                                                                                                                                                   | str                                                           |
| table_name        | (Required) The name of the table.                                                                                                                                                                                    | str                                                           |
| base_url          | The base url to DQOps application. Default value is http://localhost:8888/, which is the instance of DQOps started locally                                                                                           | str                                                           |
| job_business_key  | Job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.                              | Union[Unset, None, str] = UNSET                               |
| wait_timeout      | The number of seconds the client will wait for activity before terminating the idle task. If not set, the timeout is read from the client’s default value, which is set to 120 seconds.                              | int                                                           |
| fail_on_timeout   | By default, exceeding the timeout limit causes the status of the task to end with the Failed status. By setting the flag of this parameter to True, the status of the failed task will result in the Success status. | bool [optional, default=True]                                 |

The operator inherits from BaseOperator and adds the above parameters.
For the complete list of BaseOperator parameters, visit the official Airflow webpage https://airflow.apache.org/


## Set up the operator

Entry requirements include:

- Installation of python package from PyPi called dqops
- Configuration of data source including setting the connection in DQOps application

**DAG example**

The example sets a task to import table "maven_restaurant_ratings.ratings" on an existing connection "example_connection". 
The operator connects to the locally started DQOps server.

```python
import datetime
import pendulum
from airflow import DAG
from dqops.airflow.table_import.dqops_table_import_operator import DqopsTableImportOperator

with DAG(
    dag_id="example_connection_dqops_table_import",
    start_date=pendulum.datetime(2023, 1, 1, tz="UTC"),
    catchup=False,
) as dag:
    import_table_task = DqopsTableImportOperator(
        task_id="dqops_connection_dqops_table_import_task",
        base_url="http://host.docker.internal:8888",
        connection_name="example_connection",
        schema_name="maven_restaurant_ratings",
        table_names=["ratings"]
    )
```


## Execution results

Airflow DAG provides logs to the executed tasks.
The status details will appear in a one line as an info level log from the operator,
which contains a JSON formatted response from DQOps presented below. 

```json5
{
  'jobId': {
    'jobId': 1698316589000000, 
    'createdAt': '2023-10-26T10:36:29.832902900Z'
  }, 
  'result': {
    'source_table_specs': [{
      'incremental_time_window': {
        'daily_partitioning_recent_days': 7, 
        'monthly_partitioning_recent_months': 1
      }, 
      'columns': {
        'Consumer_ID': {
          'type_snapshot': {
            'column_type': 'STRING', 
            'nullable': 'True'
          }, 
          'comments': []
        }, 
        'Restaurant_ID': {
          'type_snapshot': {
            'column_type': 'INT64', 
            'nullable': 'True'
          }, 
          'comments': []
        }, 
        'Overall_Rating': {
          'type_snapshot': {
            'column_type': 'INT64', 
            'nullable': 'True'
          }, 
          'comments': []
        }, 
        'Food_Rating': {
          'type_snapshot': {
            'column_type': 'INT64', 
            'nullable': 'True'
          }, 
          'comments': []
        }, 
        'Service_Rating': {
          'type_snapshot': {
            'column_type': 'INT64', 
            'nullable': 'True'
          }, 'comments': []
        }
      }, 'comments': []
    }]
  }, 
  'status': 'finished'
}
```

When the task execution succeeds or not, the task instance in Airflow will be marked as Success or Failed accordingly.

In this example the table has been imported with success, as present in the value of the status.
The returned object in the response has a type of ImportTablesQueueJobResult.

ImportTablesQueueJobResult include:

- **job_id (DqoQueueJobId)**: Identifies a single job that was pushed to the job queue.
- **result (ImportTablesResult)**: Result object returned from the "import tables" job. 
Contains the original table schemas and column schemas of imported tables.
- **status (DqoJobStatus)**: The job status.

## Job id

Job id has a type of DqoQueueJobId which includes job tracking details:

| name          | description                                               | type           |
|---------------|-----------------------------------------------------------|----------------|
| job_id        | Job id.                                                   | int            |
| parent_job_id | Identifies a single job that was pushed to the job queue. | DqoQueueJobId  |
| created_at    | The timestamp when the job was created.                   | int            |


## Status

Status field is the DqoJobStatus enum, which have one of values:

- **cancelled**: The job was fully cancelled and removed from the job queue.
- **cancel_requested**: A request to cancel a job was issued, but the job is not yet cancelled.
- **failed**: The job has failed with an execution error.
- **queued**: The job is queued.
- **running**: The job is now running.
- **finished**: The job has finished successfully.
- **waiting**: The job is parked until the concurrency constraints are met.

## What's next

- [Learn about run checks operator](./run-checks-operator.md)
- [Learn how to use run checks operator](./run-checks-use-case.md)
- [Learn about collect statistics operator](./collect-statistics-operator.md)
- [Learn about table status operator](./table-status-operator.md)
- [Learn about wait for job operator](./wait-for-job-operator.md)
