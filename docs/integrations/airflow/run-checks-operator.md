# Run checks operator

The Airflow run checks operator is used to execute checks configured on the DQOps platform.

A run checks task result contains a simple summary of the data quality that is available in the logs of Airflow task as well as XCom return value of the task.  

The operator provides parameters which allow setting the operator to a specific scope of attached checks on the DQOps platform. 
Over a dozen of parameters for a **precise selection** of prepared checks are available including connection names, particular table sensors, 
dimensions, check types and more.

The run checks operators are working **synchronously**, so Airflow will wait for the end of execution. 
To prevent endless execution, the operator supports a timeout parameter.
**Asynchronous** work of the run checks operator is also possible when is used with [wait for job operator](wait-for-job-operator.md).

There are 4 operators for running checks:

- **DqopsRunChecksOperator**: Generic run checks operator for all types of checks.
- **DqopsRunProfilingChecksOperator**: Run checks operator for running [profiling type of checks](../../dqo-concepts/checks/profiling-checks/profiling-checks.md).
- **DqopsRunMonitoringChecksOperator**: Run checks operator for running [monitoring type of checks](../../dqo-concepts/checks/monitoring-checks/monitoring-checks.md).
- **DqopsRunPartitionedChecksOperator**: Run checks operator for running [partition type of checks](../../dqo-concepts/checks/partition-checks/partition-checks.md).


## Operator parameters

Operator parameters can be treated as filters that limit the scope of checks that are run.
When none of the parameters are set, possibly all sensors will be run.  

!!! tip "Use of multiple parameters"

    You can mix the parameters to run checks, for instance: 

    only partition checks from timeliness dimension on tables with name that contains "customers" literal. 


| Name                  | Description                                                                                                                                                                                                                                                                                                                                                                           | Type                                                                                                                                  |
|-----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------|
| base_url              | The base url to DQOps application. Default value is http://localhost:8888/, which is the instance of DQOps started locally                                                                                                                                                                                                                                                            | str                                                                                                                                   |
| job_business_key      | Job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.                                                                                                                                                                                               | Union[Unset, None, str] = UNSET                                                                                                       |
| api_key               | The api key to DQOps application. Not set as default.                                                                                                                                                                                                                                                                                                                                 | str                                                                                                                                   |
| connection            | The connection name to the data source in DQOps. When not set, all connection names will be used. Supports search patterns in the format: 'source\*', '\*_prod', 'prefix\*suffix'.                                                                                                                                                                                                    | str [optional, default=UNSET]                                                                                                         |
| full_table_name       | The name of the table with the schema. The schema and table name. It is provided as *<schema_name>.<table_name>*, for example *public.fact_sales*. <br> The schema and table name accept patterns both in the schema name and table name parts.<br> Sample patterns are: 'schema_name.tab_prefix_\*', 'schema_name.*', '*.*', 'schema_name.\*_customer', 'schema_name.tab_\*_suffix'. | str [optional, default=UNSET]                                                                                                         |
| enabled               | A boolean flag to target enabled tables, columns or checks. When the value of this field is not set, the default value of this field is *true*, targeting only tables, columns and checks that are not implicitly disabled.                                                                                                                                                           | Union[Unset, bool]                                                                                                                    |
| tags                  | An array of tags assigned to the table. All tags must be present on a table to match. The tags can use patterns:  'prefix\*', '\*suffix', 'prefix\*suffix'. The tags are assigned to the table on the data grouping screen when any of the data grouping hierarchy level is assigned a static value, which is a tag.                                                                  | Union[Unset, List[str]]                                                                                                               |
| labels                | An array of labels assigned to the table. All labels must be present on a table to match. The labels can use patterns:  'prefix\*', '\*suffix', 'prefix\*suffix'. The labels are assigned on the labels screen and stored in the *labels* node in the *.dqotable.yaml* file.                                                                                                          | Union[Unset, List[str]]                                                                                                               |
| column                | The column name. This field accepts search patterns in the format: 'fk_\*', '\*_id', 'prefix\*suffix'.                                                                                                                                                                                                                                                                                | Union[Unset, str]                                                                                                                     |
| column_data_type      | The column data type that was imported from the data source and is stored in the [columns -> column_name -> type_snapshot -> column_type](../../reference/yaml/TableYaml/#columntypesnapshotspec) field in the *.dqotable.yaml* file.                                                                                                                                                 | Union[Unset, str]                                                                                                                     |
| column_nullable       | Optional filter to find only nullable (when the value is *true*) or not nullable (when the value is *false*) columns, based on the value of the [columns -> column_name -> type_snapshot -> nullable](../../reference/yaml/TableYaml/#columntypesnapshotspec) field in the *.dqotable.yaml* file.                                                                                     | Union[Unset, bool]                                                                                                                    |
| check_target          | (values: CheckTarget.COLUMN, CheckTarget.TABLE)                                                                                                                                                                                                                                                                                                                                       | Union[Unset, [CheckTarget](../../schemas/#CheckTarget)]                                                                               | 
| check_type            | (Only available for DqopsRunChecksOperator) Specifies type of checks to be executed. When set, the operator will work as other run checks operators. When not set, all types of checks will be executed (values: CheckType.MONITORING, CheckType.PARTITIONED, CheckType.PROFILING).                                                                                                   | Union[Unset, [CheckType](../../table_comparisons/#CheckType)]                                                                         |
| time_scale            | Time scale filter (values: CheckTimeScale.DAILY, CheckTimeScale.MONTHLY).                                                                                                                                                                                                                                                                                                             | Union[Unset, [CheckTimeScale](../../client/models/#CheckTimeScale)]                                                                   |
| check_category        | The target [check category](../../../checks/#categories-of-checks), for example: *nulls*, *volume*, *anomaly*.                                                                                                                                                                                                                                                                        | Union[Unset, str]                                                                                                                     |
| table_comparison_name | The name of a configured table comparison. When the table comparison is provided, DQOps will only perform table comparison checks that compare data between tables.                                                                                                                                                                                                                   | Union[Unset, str]                                                                                                                     |
| check_name            | The target check name to run only this named check. Uses the short check name which is the name of the deepest folder in the *checks* folder. This field supports search patterns such as: 'profiling_\*', '\*_count', 'profiling_\*_percent'.                                                                                                                                        | Union[Unset, str]                                                                                                                     |
| sensor_name           | The target sensor name to run only data quality checks that are using this sensor. Uses the full sensor name which is the full folder path within the *sensors* folder. This field supports search patterns such as: 'table/volume/row_\*', '\*_count', 'table/volume/prefix_\*_suffix'.                                                                                              | Union[Unset, str]                                                                                                                     |
| wait_timeout          | The number of seconds the client will wait for activity before terminating the idle task. If not set, the timeout is read from the client’s default value, which is set to 120 seconds.                                                                                                                                                                                               | int                                                                                                                                   |
| fail_on_timeout       | By default, exceeding the timeout limit causes the status of the task to end with the Failed status. By setting the flag of this parameter to True, the status of the failed task will result in the Success status.                                                                                                                                                                  | bool                                                                                                                                  |
| fail_at_severity      | The threshold level of rule severity, causing that an Airflow task finishes with failed status (values: RuleSeverityLevel.VALID, RuleSeverityLevel.WARNING, RuleSeverityLevel.ERROR, RuleSeverityLevel.FATAL).                                                                                                                                                                        | [RuleSeverityLevel](../../client/models/check_results/#RuleSeverityLevel) [optional, default=RuleSeverityLevel.FATAL] |

The operator inherits from BaseOperator and adds the above parameters.
For the complete list of BaseOperator parameters, visit the official Airflow webpage https://airflow.apache.org/


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

Executed job adds information to an Airflow task log as well as to XCom's "return_value" key. 
The executed task of run check type returns the RunChecksQueueJobResult object with execution details visible above.

When the task execution succeeded or not, the task instance in Airflow is marked as Success or Failed accordingly.
If the data quality is flawless the Airflow task run square will be filled with the green color on an execution view in the DAG.

!!! tip "Use of fail_at_severity parameter"

    The use of fail_at_severity parameter allows to define a level of severity which will control success of task. <br>
    In default only the task execution with fatal severity level fails marking the square as red. <br>
    For failing the task on an another level, set the fail_at_severity parameter with the apropriate [Rule Severity Level](../../client/models/check_results/#RuleSeverityLevel)


RunChecksQueueJobResult includes:

- **job_id (DqoQueueJobId)**: Identifies a single job that was pushed to the job queue.
- **result (RunChecksResult)**: Returns the result (the highest data quality check severity and the finished checks count) for the checks that were recently executed.
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

| name             | description                                                                                                                                                                                                         | type                |
|------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------|
| highest_severity | The highest severity returned from executed checks (in order from the highest): fatal, error, warning, valid. Here you can learn about [Severity levels](../../dqo-concepts/data-quality-kpis/data-quality-kpis.md) | RuleSeverityLevel   |
| executed_checks  | The total count of all executed checks.                                                                                                                                                                             | int                 |
| valid_results    | The total count of all checks that finished successfully (with no data quality issues).                                                                                                                             | int                 |
| warnings         | The total count of all invalid data quality checks that finished raising a warning.                                                                                                                                 | int                 |
| errors           | The total count of all invalid data quality checks that finished raising an error.                                                                                                                                  | int                 |
| fatals           | The total count of all invalid data quality checks that finished raising a fatal error.                                                                                                                             | int                 |
| execution_errors | The total number of checks that failed to execute due to some execution errors.                                                                                                                                     | int                 |


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

## What's next

- [Learn how to use run checks operator](run-checks-use-case.md)
- [Learn about collect statistics operator](collect-statistics-operator.md)
- [Learn about table import operator](table-import-operator.md)
- [Learn about table status operator](table-status-operator.md)
- [Learn about wait for job operator](wait-for-job-operator.md)
- [Learn about webhooks notifications](../webhooks/index.md)
