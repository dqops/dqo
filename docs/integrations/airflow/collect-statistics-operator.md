# Collect statistics operator

The Airflow collect statistics operator **DqopsCollectStatisticsOperator** is used to gather statistics for existing tables in DQOps platform.

You can learn more about [the basic data statistics here](../../working-with-dqo/basic-data-statistics/basic-data-statistics.md).

## Operator parameters

Parameters allow selection of specific connections, tables and columns which statistics should be loaded.

| Name              | Description                                                                                                                                                                                                          | Type                                    |
|-------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------|
| connection        | The connection name to the data source in DQOps.                                                                                                                                                                     | Union[Unset, str]                       |
| full_table_name   | The name of the table with it's schema name.                                                                                                                                                                         | Union[Unset, str]                       |
| enabled           | If set to true only enabled connections and tables are filtered. Otherwise only disabled connection or table are used.                                                                                               | Union[Unset, bool]                      |
| labels            | The label names of those edited by user on connections, tables and columns edited in DQOps platform.                                                                                                                 | Union[Unset, List[str]]                 |
| column_names      | The names of columns.                                                                                                                                                                                                | Union[Unset, List[str]]                 |
| sensor_name       | The name of the sensor.                                                                                                                                                                                              | Union[Unset, str]                       |
| target            | The name of the target which value is column or table.                                                                                                                                                               | Union[Unset, StatisticsCollectorTarget] |
| base_url          | The base url to DQOps application. Default value is http://localhost:8888/, which is the instance of DQOps started locally                                                                                           | str                                     |
| job_business_key  | Job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.                              | Union[Unset, None, str] = UNSET         |
| wait_timeout      | The number of seconds the client will wait for activity before terminating the idle task. If not set, the timeout is read from the client’s default value, which is set to 120 seconds.                              | int                                     |
| fail_on_timeout   | By default, exceeding the timeout limit causes the status of the task to end with the Failed status. By setting the flag of this parameter to True, the status of the failed task will result in the Success status. | bool [optional, default=True]           |

The operator inherits from BaseOperator and adds the above parameters.
For the complete list of BaseOperator parameters, visit the official Airflow webpage https://airflow.apache.org/


## Set up the operator

Entry requirements includes:

- Installation of python package from PyPi called dqops
- Configuration of data source in DQOps.

**DAG example**

The example sets a task to receive status from the monitoring checks set on the "maven_restaurant_ratings.consumers" table from "example_connection". 
The operator connects to the locally started DQOps server.

```python
import datetime
import pendulum
from airflow import DAG
from dqops.airflow.collect_statistics.dqops_collect_statistics_operator import DqopsCollectStatisticsOperator

with DAG(
    dag_id="example_connection_dqops_collect_statistics",
    start_date=pendulum.datetime(2023, 1, 1, tz="UTC"),
    catchup=False,
) as dag:
    import_table_task = DqopsCollectStatisticsOperator(
        task_id="dqops_connection_dqops_collect_statistics_task",
        base_url="http://host.docker.internal:8888",
        connection="example_connection",
        full_table_name="maven_restaurant_ratings.ratings"
    )

```


## Execution results

Airflow DAG provides logs to the executed tasks.
The status details will appear in a one line as an info level log from the operator, which contains a JSON formatted response from DQOps presented below. 

```json5
{
  'jobId': {
    'jobId': 1698474026000000, 
    'createdAt': '2023-10-28T06:20:26.215682800Z'
  }, 
  'result': {
    'executed_statistics_collectors': 69, 
    'total_collectors_executed': 0, 
    'columns_analyzed': 5, 
    'columns_successfully_analyzed': 5, 
    'total_collectors_failed': 0, 
    'total_collected_results': 186, 
    'source_table_specs': []
  }, 
  'status': 'succeeded'
}
```

Technically, the executed operator returns the [CollectStatisticsQueueJobResult](../../client/models/jobs.md#CollectStatisticsQueueJobResult) object with status details.
When the task execution succeeds or not, the task instance in Airflow will be marked as Success or Failed accordingly.

CollectStatisticsQueueJobResult includes:

- **job_id (DqoQueueJobId)**: Identifies a single job that was pushed to the job queue.
- **result (CollectStatisticsResult)**: Returns the result with the summary of the collected statistics.
- **status (DqoJobStatus)**: The job status.


## Job id

Job id has a type of DqoQueueJobId which includes job tracking details:

| name          | description                                               | type           |
|---------------|-----------------------------------------------------------|----------------|
| job_id        | Job id.                                                   | int            |
| parent_job_id | Identifies a single job that was pushed to the job queue. | DqoQueueJobId  |
| created_at    | The timestamp when the job was created.                   | int            |


## Collect statistics result

| Property name                  | Description                                                                                 | Data type |
|--------------------------------|---------------------------------------------------------------------------------------------|-----------|
| executed_statistics_collectors | The total count of all executed statistics collectors.                                      | integer   |
| total_collectors_executed      | The count of executed statistics collectors.                                                | integer   |
| columns_analyzed               | The count of columns for which DQOps executed a collector and tried to read the statistics. | integer   |
| columns_successfully_analyzed  | The count of columns for which DQOps managed to obtain statistics.                          | integer   |
| total_collectors_failed        | The count of statistics collectors that failed to execute.                                  | integer   |
| total_collected_results        | The total number of results that were collected.                                            | integer   |


## Status

Status field is the DqoJobStatus enum, which have one of values:

- **cancelled**: The job was fully cancelled and removed from the job queue.
- **cancel_requested**: A request to cancel a job was issued, but the job is not yet cancelled.
- **failed**: The job has failed with an execution error.
- **queued**: The job is queued.
- **running**: The job is now running.
- **succeeded**: The job has finished successfully.
- **waiting**: The job is parked until the concurrency constraints are met.

## What's next

- [Learn about run checks operator](run-checks-operator.md)
- [Learn how to use run checks operator](run-checks-use-case.md)
- [Learn about table import operator](table-import-operator.md)
- [Learn about table status operator](table-status-operator.md)
- [Learn about wait for job operator](wait-for-job-operator.md)
