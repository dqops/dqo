# Run checks operator

The Airflow run checks operators are used to execute checks configured in the DQOps application. 

The operator provides parameters which allow setting it specifically:

- within a connection, 
- particular table sensors, 
- check types 
- or a combination of them.

Run checks operators are working synchronously, so Airflow will wait for the end of execution. 
To prevent endless execution, the operator supports a timeout parameter.

There are 4 operators for running checks.

Generic operators:

- **DqopsRunChecksOperator**: Generic run checks operator for all types of checks.

Typed operators:

- **DqopsRunProfilingChecksOperator**: Run checks operator for running [profiling type of checks](../../dqo-concepts/checks/profiling-checks/profiling-checks.md).
- **DqopsRunMonitoringChecksOperator**: Run checks operator for running [monitoring type of checks](../../dqo-concepts/checks/monitoring-checks/monitoring-checks.md).
- **DqopsRunPartitionedChecksOperator**: Run checks operator for running [partition type of checks](../../dqo-concepts/checks/partition-checks/partition-checks.md).


## Operator parameters

Operator parameters can be treated as filters that limit the scope of checks that will be run.
When none of the available operator parameters are set, possibly all sensors will be run.  

| Name             | Description                                                                                                                                                                                                                                                                                                                                                          | Type                                                           |
|------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------|
| base_url         | The base url to DQOps application. Default value is http://localhost:8888/, which is the instance of DQOps started locally                                                                                                                                                                                                                                           | str                                                           |
| api_key          | The api key to DQOps application. Not set as default.                                                                                                                                                                                                                                                                                                                | str                                                            |
| connection       | The name of the connection to the data source in DQOps. When not set, all connection names will be used.                                                                                                                                                                                                                                                             | str                                                            |
| full_table_name  | The name of the table with the schema. When not set, checks from all tables will be run within the specified connection.                                                                                                                                                                                                                                             | str                                                            |
| check_type       | (Only available for DqopsRunChecksOperator) Specifies type of checks to be executed. When set, the operator will work as other run checks operators. The other operators have set this field to a constant value from CheckType enum. <br/> When not set, all types of checks will be executed. <br/> The enum is stored in _dqops.client.models.check_type_ module. | CheckType                                                      |
| wait_timeout     | The number of seconds the client will wait for activity before terminating the idle task. If not set, the timeout is read from the client’s default value, which is set to 120 seconds.                                                                                                                                                                              | int                                                            |
| fail_on_timeout  | By default, exceeding the timeout limit causes the status of the task to end with the Failed status. By setting the flag of this parameter to True, the status of the failed task will result in the Success status.                                                                                                                                                 | bool                                                           |
| fail_at_severity | The threshold level of rule severity, causing that an Airflow task finishes with failed status.                                                                                                                                                                                                                                                                      | RuleSeverityLevel [optional, default=RuleSeverityLevel.FATAL]  |

Above parameters are the only parameters that are the addition to the standard parameters of BaseOperator, from which the described operator inherits.
For the complete list of parameters that are supported by BaseOperator, visit the official Airflow webpage https://airflow.apache.org/

## Set up the run check operator

Entry requirements include:

- Installation of python package from PyPi called dqops
- Configuration of data source and checks in DQOps.

**DAG example**

The example sets a task to execute all profiling checks on "example_connection" connection every 12 hours. 
The operator connects to the locally started DQOps server.

```python
import datetime
import pendulum
from airflow import DAG
from dqops.airflow_operators.run_checks.dqops_run_profiling_checks_operator import DqopsRunProfilingChecksOperator

with DAG(
    dag_id="my_connection_dqops_run_profiling_checks",
    schedule=datetime.timedelta(hours=12),
    start_date=pendulum.datetime(2023, 1, 1, tz="UTC"),
    catchup=False,
) as dag:
    run_checks_task = DqopsRunProfilingChecksOperator(
        task_id="dqops_run_profiling_checks_operator_task",
        base_url="http://host.docker.internal:8888",
        connection="example_connection",
        full_table_name="maven_restaurant_ratings.consumers"
    )
```


## Execution results

Airflow DAG provides logs to the executed tasks.   
The status details will appear in a one line as an info level log from the operator, which contains a JSON formatted response from DQOps presented below.

```json5
{
  'jobId': {
    'jobId': 1696833858000000, 
    'createdAt': '2023-10-09T06:44:18.519114400Z'
  }, 
  'result': {
    'highest_severity': 'valid', 
    'executed_checks': 3, 
    'valid_results': 3, 
    'warnings': 0, 
    'errors': 0, 
    'fatals': 0, 
    'execution_errors': 0
  }, 
  'status': 'running'
}
```

Executed job adds information to an Airflow task log. 
The executed run checks operator returns the RunChecksQueueJobResult object with execution details.
When the task execution succeeded or not, the task instance in Airflow is marked as Success or Failed accordingly.

RunChecksQueueJobResult includes:

- **job_id (DqoQueueJobId)**: Identifies a single job that was pushed to the job queue.
- **result (RunChecksResult)**: Returns the result (highest data quality check severity and the finished checks count) for the checks that were recently executed.
- **status (DqoJobStatus)**: The job status.


## Job id

Job id has a type of DqoQueueJobId which includes job tracking details:

| name          | description                                               | type           |
|---------------|-----------------------------------------------------------|----------------|
| job_id        | Job id.                                                   | int            |
| parent_job_id | Identifies a single job that was pushed to the job queue. | DqoQueueJobId  |
| created_at    | The timestamp when the job was created.                   | int            |


## Result

Job result has a type of RunChecksResult with the overall summary of checks execution: 

| name             | description                                                                                                                                                                                                               | type                |
|------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------|
| highest_severity | The highest severity returned from executed checks (in order from highest): fatal, error, warning, valid. Here you can learn about [Severity levels](../../dqo-concepts/data-quality-kpis/data-quality-kpis.md)   | RuleSeverityLevel   |
| executed_checks  | The total count of all executed checks.                                                                                                                                                                                   | int                 |
| valid_results    | The total count of all checks that finished successfully (with no data quality issues).                                                                                                                                   | int                 |
| warnings         | The total count of all invalid data quality checks that finished raising a warning.                                                                                                                                       | int                 |
| errors           | The total count of all invalid data quality checks that finished raising an error.                                                                                                                                        | int                 |
| fatals           | The total count of all invalid data quality checks that finished raising a fatal error.                                                                                                                                   | int                 |
| execution_errors | The total number of checks that failed to execute due to some execution errors.                                                                                                                                           | int                 |


## Status

Status field is the DqoJobStatus enum, which have one of values:

- **cancelled**: The job was fully cancelled and removed from the job queue.
- **cancel_requested**: A request to cancel a job was issued, but the job is not yet cancelled.
- **failed**: The job has failed with an execution error.
- **queued**: The job is queued.
- **running**: The job is now running.
- **succeeded**: The job has finished successfully.
- **waiting**: The job is parked until the concurrency constraints are met.

## Long-running run check task

An approach is different in case of long-running Airflow tasks that take several minutes up to hours to be completed.

Instead of using a single operator, usage of WaitForJobOperator as an addition to the long-running task is necessary.

Check the [wait for job operator](wait-for-job-operator.md) page for more details.
