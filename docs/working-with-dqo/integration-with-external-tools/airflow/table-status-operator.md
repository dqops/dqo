# Table status operator

Table status airflow operators are used to receive the overall data quality status for a table from previously run sensors. 
When issues are present on the table, the operator informs about the scale of issue and points them for further work on the data quality. 

The operator can be used to collect information about the data quality before or after execution of a significant operation. 

There are 4 operators for checking the status.

- **DqoAssertTableStatusOperator**: Generic airflow assert table status operator for receiving DQOps table status.

This operator is used for receiving overall status on a table. 

And three operators with that verifies status from specific type of checks:

- **DqoAssertProfilingTableStatusOperator**: For profiling checks.
- **DqoAssertMonitoringTableStatusOperator**: For monitoring checks.
- **DqoAssertPartitionedTableStatusOperator**: For partitioned checks.


## Operator parameters

Parameters allow selection of specific checks results that should be contained in the received status.
The required parameters set clearly indicates the specific table in a connection.

| Name                | Description                                                                                                                                                                                                                                                                                                               | Type                                                          |
|---------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------|
| connection_name     | (Required) The connection name to the data source in DQOps.                                                                                                                                                                                                                                                               | str                                                           |
| schema_name         | (Required) The schema name.                                                                                                                                                                                                                                                                                               | str                                                           |
| table_name          | (Required) The table name.                                                                                                                                                                                                                                                                                                | str                                                           |
| months              | The number of months to review the data quality check results. For partitioned checks, it is the number of months to analyze. The default value is 1 (which is the current month and 1 previous month).                                                                                                                   | Union[Unset, None, int]                                       |
| check_type          | Specifies type of checks to be executed. When not set, all types of checks will be executed. <br/> The enum is stored in _dqops.client.models.check_type_ module.                                                                                                                                                         | Union[Unset, None, CheckType]                                 |
| check_time_scale    | Time scale filter for monitoring and partitioned checks (values: daily or monthly).                                                                                                                                                                                                                                       | Union[Unset, None, CheckTimeScale]                            |
| data_group          | Data group.                                                                                                                                                                                                                                                                                                               | Union[Unset, None, str]                                       |
| check_name          | Data quality check name.                                                                                                                                                                                                                                                                                                  | Union[Unset, None, str]                                       |
| category            | Category name of the check.                                                                                                                                                                                                                                                                                               | Union[Unset, None, str]                                       | 
| table_comparison    | Table comparison name.                                                                                                                                                                                                                                                                                                    | Union[Unset, None, str]                                       | 
| quality_dimension   | Quality dimension of the check.                                                                                                                                                                                                                                                                                           | Union[Unset, None, str]                                       |
| base_url            | The base url to DQOps application. Default value is http://localhost:8888/                                                                                                                                                                                                                                                | str                                                           |
| wait_timeout        | Time in seconds for execution that client will wait. It prevents from hanging the task for an action that is never completed. If not set, the timeout is read form the client defaults, which value is 120 seconds.                                                                                                       | int                                                           |
| fail_on_timeout     | Timeout is leading the task status to Failed by default. It can be omitted marking the task as Success by setting the flag to True.                                                                                                                                                                                       | bool [optional, default=True]                                 |
| fail_at_severity    | The threshold level of rule severity, causing that an airflow task finishes with failed status.                                                                                                                                                                                                                           | RuleSeverityLevel [optional, default=RuleSeverityLevel.FATAL] |

Above parameters are the only parameters that are the addition to the standard parameters of BaseOperator, from which the described operator inherits.
For the complete list of parameters that are supported by BaseOperator, visit the official airflow webpage https://airflow.apache.org/

## Set up the operator

Entry requirements includes:

- installation of python package from PyPi called dqops
- configuration of data source and sensors in DQOps.

**DAG example**

The example sets a task to receive status from the monitoring sensors set on the "maven_restaurant_ratings.consumers" table from "example_connection". 
The operator connects to the locally started DQOps server.

```python
import datetime
import pendulum
from airflow import DAG
from dqops.airflow.table_status.dqo_assert_monitoring_table_status_operator import DqoAssertMonitoringTableStatusOperator

with DAG(
    dag_id="example_connection_dqops_assert_table_status",
    schedule=datetime.timedelta(hours=12),
    start_date=pendulum.datetime(2023, 1, 1, tz="UTC"),
    catchup=False,
) as dag:
    assert_status_task = DqoAssertMonitoringTableStatusOperator(
        task_id="dqo_assert_table_status_operator_task",
        base_url="http://host.docker.internal:8888",
        connection_name="example_connection",
        schema_name="maven_restaurant_ratings",
        table_name="consumers"
    )

```


## Execution details

Airflow DAG provides logs to the executed tasks.
The status details will appear in a one line as an info level log from the operator, which contains a JSON formatted response from DQOps presented below. 

```json5
{
  'connection_name': 'example_connection', 
  'schema_name': 'maven_restaurant_ratings', 
  'table_name': 'consumers', 
  'highest_severity_issue': 3, 
  'last_check_executed_at': '2023-10-25T12:00:00.578Z', 
  'executed_checks': 148, 
  'valid_results': 143, 
  'warnings': 0, 
  'errors': 0, 
  'fatals': 5, 
  'execution_errors': 0, 
  'failed_checks_statuses': {
    'daily_row_count': 'fatal'
  }
}
```

In this example we used the default number of months to be checked (it is from the beginning of the previous month up to now).
Since that time 5 checks have failed out of 148 in total.
The causes of the failure are known, which are shown in **failed_checks_statuses** JSON object.


```text
Tip

This kind of issue is easy to verify. It is the row count issue, which falls out of expected bounds of the check's rule threshold.
Either rule adjustment is necessary in case the data are correct, or part of data did not load which was easily verified by checking the status.
```

Furthermore, the task will finish with Failed airflow status as we did not set the **maximum_severity_threshold** parameter.

Technically, the executed operator returns the TableDataQualityStatusModel object with status details.
When the task execution succeeds or not, the task instance in airflow will be marked as Success or Failed accordingly.

## TableDataQualityStatusModel fields 

TableDataQualityStatusModel includes:

- **connection_name**: The connection name in DQOps.
- **schema_name**: The schema name.
- **table_name**: The table name.
- **highest_severity_issue**: The severity of the highest identified data quality issue (1 = warning, 2 = error, 3 = fatal) 
or 0 when no data quality issues were identified. This field will be null if no data quality checks were executed on the table.
- **last_check_executed_at**: The UTC timestamp when the most recent data quality check was executed on the table.
- **executed_checks**: The total number of most recent checks that were executed on the table. 
Table comparison checks that are comparing groups of data are counted as the number of compared data groups.
- **valid_results**: The number of most recent valid data quality checks that passed without raising any issues.
- **warnings**: The number of most recent data quality checks that failed by raising a warning severity data quality issue.
- **errors**: The number of most recent data quality checks that failed by raising an error severity data quality issue.
- **fatals**: The number of most recent data quality checks that failed by raising a fatal severity data quality issue.
- **execution_errors**: The number of data quality check execution errors that were reported due to access issues to the data source, 
invalid mapping in DQOps, invalid queries in data quality sensors or invalid python rules. 
When an execution error is reported, the configuration of a data quality check on a table must be updated.
- **failed_checks_statuses** (TableDataQualityStatusModelFailedChecksStatuses): The paths to all failed
data quality checks (keys) and severity of the highest data quality issue that was detected. Table-level checks
are identified by the check name. Column-level checks are identified as a check_name[column_name].
