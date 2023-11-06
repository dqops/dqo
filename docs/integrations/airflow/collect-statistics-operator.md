# Collect statistics operator

The collect statistics airflow operator **DqoCollectStatisticsOperator** is used to gather statistics for existing tables in DQOps platform.

You can learn more about [the basic data statistics here](../../working-with-dqo/basic-data-statistics/basic-data-statistics.md).

## Operator parameters

Parameters allow selection of specific connections, tables and columns which statistics should be loaded.

| Name                 | Description                                                                                                                                                                                                         | Type                                              |
|----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------|
| connection_name      | The connection name to the data source in DQOps.                                                                                                                                                                    | Union[Unset, str]                                 |
| schema_table_name    | The table name with it's schema name.                                                                                                                                                                               | Union[Unset, str]                                 |
| table_name           | The table name.                                                                                                                                                                                                     | Union[Unset, str]                                 |
| enabled              | If set to true only enabled connections and tables are filtered. Otherwise only disabled connection or table are used.                                                                                              | Union[Unset, bool]                                |
| labels               | The label names of those edited by user on connections, tables and columns edited in DQOps platform.                                                                                                                | Union[Unset, List[str]]                      |
| column_names         | The names of columns.                                                                                                                                                                                               | Union[Unset, List[str]]                      |
| sensor_name          | The name of the sensor                                                                                                                                                                                              | Union[Unset, str]                            |
| target               | The name of the target which value is column or table.                                                                                                                                                              | Union[Unset, StatisticsCollectorTarget]      |
| base_url             | The base url to DQOps application. Default value is http://localhost:8888/                                                                                                                                          | str                                               |
| wait_timeout         | Time in seconds for execution that client will wait. It prevents from hanging the task for an action that is never completed. If not set, the timeout is read form the client defaults, which value is 120 seconds. | int                                               |
| fail_on_timeout      | Timeout is leading the task status to Failed by default. It can be omitted marking the task as Success by setting the flag to True.                                                                                 | bool [optional, default=True]                     |

Above parameters are the only parameters that are the addition to the standard parameters of BaseOperator, from which the described operator inherits.
For the complete list of parameters that are supported by BaseOperator, visit the official airflow webpage https://airflow.apache.org/

## Set up the operator

Entry requirements includes:

- Installation of python package from PyPi called dqops
- Configuration of data source in DQOps.

**DAG example**

The example sets a task to receive status from the monitoring sensors set on the "maven_restaurant_ratings.consumers" table from "example_connection". 
The operator connects to the locally started DQOps server.

```python
import datetime
import pendulum
from airflow import DAG
from dqops.airflow.collect_statistics.dqo_collect_statistics_operator import DqoCollectStatisticsOperator

with DAG(
    dag_id="example_connection_dqops_collect_statistics",
    start_date=pendulum.datetime(2023, 1, 1, tz="UTC"),
    catchup=False,
) as dag:
    import_table_task = DqoCollectStatisticsOperator(
        task_id="dqo_connection_dqops_collect_statistics_task",
        base_url="http://host.docker.internal:8888",
        connection_name="example_connection",
        schema_table_name="maven_restaurant_ratings.ratings"
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
When the task execution succeeds or not, the task instance in airflow will be marked as Success or Failed accordingly.

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
