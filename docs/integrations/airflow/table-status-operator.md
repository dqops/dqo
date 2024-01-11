# Table status operator

## Overview

The Airflow table status operators are used to receive the overall data quality status of a table based on checks run in the DQOps platform
If there are any issues with the table, the operator will inform about the scale of the issue and point towards a specific area of the data quality that needs improvement. 

These operators can be used to collect information about the data quality before or after the execution of a significant operation, which might save operational costs and time.

There are four operators available to check the status of the table.

- **DqopsAssertTableStatusOperator**: A generic Airflow assert table status operator that is used to receive the overall status of a table in DQOps.
- **DqopsAssertProfilingTableStatusOperator**: Verifies the status of [profiling checks](../../dqo-concepts/checks/profiling-checks/profiling-checks.md).
- **DqopsAssertMonitoringTableStatusOperator**: Verifies the status of [monitoring checks](../../dqo-concepts/checks/monitoring-checks/monitoring-checks.md).
- **DqopsAssertPartitionedTableStatusOperator**: Verifies the status of [partition checks](../../dqo-concepts/checks/partition-checks/partition-checks.md).


## Operator parameters

Parameters allow the selection of specific check results to be included in the received status.
The required parameters set clearly indicate the specific table in a connection.

| Name              | Description                                                                                                                                                                                                                                                                                                                                                                                             | Type                                                                               |
|-------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| connection_name   | (Required) The name of the connection in DQOps.                                                                                                                                                                                                                                                                                                                                                         | str                                                                                |
| schema_name       | (Required) The name of the schema.                                                                                                                                                                                                                                                                                                                                                                      | str                                                                                |
| table_name        | (Required) The name of the table.                                                                                                                                                                                                                                                                                                                                                                       | str                                                                                |
| months            | The number of months for reviewing the data quality check results. The default value is 1, which includes the current month and 1 previous month. For partitioned checks, it is the number of months to analyze.                                                                                                                                                                                        | Union[Unset, None, int]                                                            |
| profiling         | (Only available in DqopsAssertTableStatusOperator operator) Boolean flag to enable reading also the most recent status of *profiling* data quality checks. By default, DQOps does not read the check results of profiling checks.                                                                                                                                                                       | Union[Unset, None, bool]                                                           |
| monitoring        | (Only available in DqopsAssertTableStatusOperator operator) Boolean flag to configure reading the most recent status of *monitoring* data quality checks. By default, DQOps finds the most recent check result for each data quality check and returns its severity status. The default value is True.                                                                                                  | Union[Unset, None, bool]                                                           |
| partitioned       | (Only available in DqopsAssertTableStatusOperator operator) Boolean flag to configure reading the most recent status of *partitioned* data quality checks. By default, DQOps reads the check execution statuses of ALL partitioned checks in the filtered time range, for all partitions (time periods), because data quality issues could be present in earlier partitions. The default value is True. | Union[Unset, None, bool]                                                           |
| check_time_scale  | Time scale filter for monitoring and partition checks (values: CheckTimeScale.DAILY, CheckTimeScale.MONTHLY).                                                                                                                                                                                                                                                                                           | Union[Unset, None, [CheckTimeScale](../../client/models/common.md#CheckTimeScale)] |
| data_group        | The name of the data group.                                                                                                                                                                                                                                                                                                                                                                             | Union[Unset, None, str]                                                            |
| check_name        | The name of the data quality check.                                                                                                                                                                                                                                                                                                                                                                     | Union[Unset, None, str]                                                            |
| category          | The name of the check category.                                                                                                                                                                                                                                                                                                                                                                         | Union[Unset, None, str]                                                            | 
| table_comparison  | The name of the table comparison created in DQOps.                                                                                                                                                                                                                                                                                                                                                      | Union[Unset, None, str]                                                            | 
| quality_dimension | The name of the data quality dimension for which the check is categorized.                                                                                                                                                                                                                                                                                                                              | Union[Unset, None, str]                                                            |
| base_url          | The base url to DQOps application. Default value is http://localhost:8888/, which is the instance of DQOps started locally                                                                                                                                                                                                                                                                              | str                                                                                |
| wait_timeout      | The number of seconds the client will wait for activity before terminating the idle task. If not set, the timeout is read from the client’s default value, which is set to 120 seconds.                                                                                                                                                                                                                 | int                                                                                |
| fail_on_timeout   | By default, exceeding the timeout limit causes the status of the task to end with the Failed status. By setting the flag of this parameter to True, the status of the failed task will result in the Success status.                                                                                                                                                                                    | bool [optional, default=True]                                                      |
| fail_at_severity  | The threshold level of rule severity, causing the Airflow task to terminate with a Failed status.                                                                                                                                                                                                                                                                                                       | RuleSeverityLevel [optional, default=RuleSeverityLevel.FATAL]                      |

The operator inherits from BaseOperator and adds the above parameters.
For the complete list of BaseOperator parameters, visit the official Airflow webpage https://airflow.apache.org/


## Set up the operator

Entry requirements include:

- Installation of Python package from PyPi called dqops
- Configuration of data source and checks in DQOps platform.

**DAG example**

The following example shows how to configure an operator that receive status from the activated monitoring checks on the "maven_restaurant_ratings.consumers" table located in "example_connection". 
The operator connects to the locally started DQOps server.

```python
import datetime
import pendulum
from airflow import DAG
from dqops.airflow.table_status.dqops_assert_monitoring_table_status_operator import DqopsAssertMonitoringTableStatusOperator

with DAG(
    dag_id="example_connection_dqops_assert_table_status",
    schedule=datetime.timedelta(hours=12),
    start_date=pendulum.datetime(2023, 1, 1, tz="UTC"),
    catchup=False,
) as dag:
    assert_status_task = DqopsAssertMonitoringTableStatusOperator(
        task_id="dqops_assert_table_status_operator_task",
        base_url="http://host.docker.internal:8888",
        connection_name="example_connection",
        schema_name="maven_restaurant_ratings",
        table_name="consumers"
    )

```


## Execution results

Airflow DAG provides logs to the executed tasks. 
The status details will be displayed in one line in the Airflow Logs tab of the executed task as a JSON formatted INFO level log. 
An example of the response from the DQOps platform is shown below.

```json5
{
  'connection_name': 'example_connection', 
  'schema_name': 'maven_restaurant_ratings', 
  'table_name': 'consumers', 
  'current_severity': 'fatal',
  'highest_historical_severity': 'fatal',
  'last_check_executed_at': '2023-11-08T17:23:00.188Z', 
  'executed_checks': 148, 
  'valid_results': 143, 
  'warnings': 0, 
  'errors': 0, 
  'fatals': 5, 
  'execution_errors': 0, 
  'failed_checks_statuses': {
    'daily_row_count': 'fatal'
  },
  'checks': {
    'daily_table_availability': {
      'current_severity': 'valid',
      'highest_historical_severity': 'valid',
      'last_executed_at': '2023-11-08T17:23:00.188Z',
      'category': 'availability',
      'quality_dimension': 'Availability'
    },
    'daily_column_count_changed': {
      'current_severity': 'fatal',
      'highest_historical_severity': 'fatal',
      'last_executed_at': '2023-11-08T17:23:00.187Z',
      'category': 'schema',
      'quality_dimension': "Consistency"
    }
  },
  'columns': {
    'date': {
      'current_severity': 'valid',
      'highest_historical_severity': 'valid',
      'last_check_executed_at': '2023-11-08T17:23:00.108Z',
      'executed_checks': 36,
      'valid_results': 31,
      'warnings': 0,
      'errors': 0,
      'fatals': 0,
      'execution_errors': 5,
      'checks': {
        'daily_column_type_changed': {
          'current_severity': 'valid',
          'highest_historical_severity': 'valid',
          'last_executed_at': '2023-11-08T17:23:00.073Z',
          'category': 'schema',
          'quality_dimension': 'Consistency'
        },
        'daily_column_exists': {
          'current_severity': 'valid',
          'highest_historical_severity': 'valid',
          'last_executed_at': '2023-11-08T17:23:00.074Z',
          'category': 'schema',
          'quality_dimension': 'Validity'
        }
      }
    }
  }
}
```

The example indicates that out of 148 executed checks, 5 failed with a Fatal result. 
The failed checks name was daily_row_count, as shown in the failed_checks_statuses JSON object. 
Here we used the default number of months which is from the beginning of the previous month to the present.


!!! tip "Tip"
    It is easy to verify the issue with row count, which exceeds the expected limit set by the check's rules. 
    In such a case, we need to check if data was loaded or adjust the rule thresholds if the number of rows is as expected.


Furthermore, the task will finish with Failed Airflow status as we did not set the **fail_at_severity** parameter.

As we did not set the maximum_severity_threshold parameter, the task will finish with Failed Airflow status.
Technically, the executed operator returns the TableDataQualityStatusModel object with the relevant status details. 
Depending on whether the task execution was successful or not, the task instance will be marked as either Success or Failed, respectively.

## TableDataQualityStatusModel fields 

TableDataQualityStatusModel contains the following fields:

- **connection_name**: The name of the connection in DQOps.
- **schema_name**: The name of the schema.
- **table_name**: The name of the table.
- **current_severity**: The highest severity level of all identified data quality issues on the table and columns, 
    but only for the most recently executed data quality check. Returned values are:
    - **valid** when no data quality issues were detected
    - **warning** when only warnings were detected
    - **error** when regular data quality issues were detected
    - **fatal** when a fatal data quality was detected that should result in stopping the data pipeline to avoid loading invalid data 
- **highest_historical_severity**: The highest severity level of all identified data quality issues on the table and columns, 
    also including previously detected issues, even if the issue was corrected and the **current_severity** is valid. Returned values are:
    - **valid** when no data quality issues were detected
    - **warning** when only warnings were detected
    - **error** when regular data quality issues were detected
    - **fatal** when a fatal data quality was detected that should result in stopping the data pipeline to avoid loading invalid data
- **last_check_executed_at**: The UTC timestamp of the most recent data quality check executed on the table.
- **executed_checks**: The total number of most recent checks executed on the table. 
  For table comparison checks that compare groups of data this field is the number of compared data groups.
- **valid_results**: The number of most recent valid data quality checks that passed without raising any issues.
- **warnings**: The number of most recent data quality checks that failed by raising a warning severity data quality issue.
- **errors**: The number of most recent data quality checks that failed by raising an error severity data quality issue.
- **fatals**: The number of most recent data quality checks that failed by raising a fatal severity data quality issue.
- **execution_errors**: The number of data quality check execution errors that were reported due to access issues to the data source, 
  invalid mapping in DQOps, invalid queries in data quality sensors, or invalid Python rules. 
  When an execution error is reported, the configuration of a data quality check on a table must be updated.
- **checks** is a dictionary of data quality checks, storing objects that describe the current data quality status of the check and the last check's execution timestamp
- **columns** is a dictionary of columns covered by data quality checks, storing objects that describe the current data quality status of checks configured on that column

## What's next

- [Learn about run checks operator](./run-checks-operator.md)
- [Learn how to use run checks operator](./run-checks-use-case.md)
- [Learn about collect statistics operator](./collect-statistics-operator.md)
- [Learn about table import operator](./table-import-operator.md)
