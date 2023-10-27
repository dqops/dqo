# Table import operator

Table import airflow operator **DqoTableImportOperator** is used to import table to connection existing in DQOps.
When the table schema changes you can also use this operator. 
It will update the table in DQOps application for recently added columns or updates of types in the existing columns.

## Operator parameters

Parameters points to the particular table that have to be imported.

| Name            | Description                                                                                                                                                                                                                                                                                                               | Type                                                          |
|-----------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------|
| connection_name | (Required) The connection name to the data source in DQOps.                                                                                                                                                                                                                                                               | str                                                           |
| schema_name     | (Required) The schema name.                                                                                                                                                                                                                                                                                               | str                                                           |
| table_name      | (Required) The table name.                                                                                                                                                                                                                                                                                                | str                                                           |
| base_url        | The base url to DQOps application. Default value is http://localhost:8888/                                                                                                                                                                                                                                                | str                                                           |
| wait_timeout    | Time in seconds for execution that client will wait. It prevents from hanging the task for an action that is never completed. If not set, the timeout is read form the client defaults, which value is 120 seconds.                                                                                                       | int                                                           |
| fail_on_timeout | Timeout is leading the task status to Failed by default. It can be omitted marking the task as Success by setting the flag to True.                                                                                                                                                                                       | bool [optional, default=True]                                 |

Above parameters are the only parameters that are the addition to the standard parameters of BaseOperator, from which the described operator inherits.
For the complete list of parameters that are supported by BaseOperator, visit the official airflow webpage https://airflow.apache.org/


## Set up the operator

Entry requirements includes:

- installation of python package from PyPi called dqops
- configuration of data source including setting the connection in DQOps application

**DAG example**

The example sets a task to import table "maven_restaurant_ratings.ratings" on an existing connection "example_connection". 
The operator connects to the locally started DQOps server.

```python
import datetime
import pendulum
from airflow import DAG
from dqops.airflow.table_import.dqo_table_import_operator import DqoTableImportOperator

with DAG(
    dag_id="example_connection_dqops_table_import",
    start_date=pendulum.datetime(2023, 1, 1, tz="UTC"),
    catchup=False,
) as dag:
    import_table_task = DqoTableImportOperator(
        task_id="dqo_connection_dqops_table_import_task",
        base_url="http://host.docker.internal:8888",
        connection_name="example_connection",
        schema_name="maven_restaurant_ratings",
        table_names=["ratings"]
    )
```


## Execution details

Airflow DAG provides logs to the executed tasks.
The status details will appear in a one line as an info level log from the operator, which contains a JSON formatted response from DQOps presented below. 

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
  'status': 'succeeded'
}
```

When the task execution succeeds or not, the task instance in airflow will be marked as Success or Failed accordingly.

In this example the table has been imported with success, as present in the value of the status.
The returned object in the response has a type of ImportTablesQueueJobResult.

ImportTablesQueueJobResult includes:

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
- **succeeded**: The job has finished successfully.
- **waiting**: The job is parked until the concurrency constraints are met.
