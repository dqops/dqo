# Run checks example

This page presents how to use the _run checks_ operator in the non-blocking DAG configuration in Airflow.

Also, the example demonstrates how to control the loading data stage execution by the detection of a data quality issue.

## Pre-requirements

Entry requirements include:

- Installation of python package from PyPi called dqops
- Configuration of data source and checks in DQOps

## DAG configuration

The below DAG uses three tasks: 

- [run checks](run-checks-operator.md),
- [wait for run checks execution](wait-for-job-operator.md),
- load data.

The code snippet does not contain the configuration of the data loading operator.
It has to be completed first before using the example.

```python
import datetime
import pendulum
from airflow import DAG

from dqops.airflow.run_checks.dqops_run_checks_operator import DqopsRunChecksOperator
from dqops.airflow.wait_for_job.dqops_wait_for_job_operator import DqopsWaitForJobOperator
from dqops.client.models.check_type import CheckType
from dqops.client.models.rule_severity_level import RuleSeverityLevel

... # (1)!

with DAG(
    dag_id="example_connection_run_checks_wait_and_load",
    schedule=datetime.timedelta(hours=12), # (2)!
    start_date=pendulum.datetime(2021, 1, 1, tz="UTC"),
    catchup=False,
    tags=["dqops_example"]
) as dag:
    run_checks = DqopsRunChecksOperator(
        task_id="dqops_run_checks_operator_task",        
        base_url="http://host.docker.internal:8888", # (3)!
        connection="example_connection",
        check_type=CheckType.MONITORING,
        fail_on_timeout=False, # (4)!
        wait_timeout=10 # (5)!
    )
   
    wait_for_job = DqopsWaitForJobOperator(
        task_id="dqops_wait_for_job",
        base_url="http://host.docker.internal:8888",
        retries=120, # (6)!
        retry_delay=60
    )

    """ This section contains the business loading task 

        load_new_data = # first configure the loading operator used in your project
    """
    
    run_checks >> wait_for_job >> load_new_data # (7)!

```

1. The import of the operator for the load_new_data task.
2. The DAG is set to run twice a day, every 12 hours.
3. The example uses a dockerized Airflow environment. It connects to the local DQOps instance on a localhost which can be reached from images with substitution the "host.docker.internal" in place of "localhost".
4. The parameter that allows bypass failure on the timeout on the long-running task. Otherwise, setting the **trigger_rule** to "all_done" in the configuration of the _wait for job_ operator allows it's task to be executed when the upstream _run checks_ task fails
5. The task is expected to be finished in over 10 minutes. The _run checks_ task responsibility is to call the DQOps API only. The default timeout (120s) is limited to 10s.
6. The total time in seconds for the operator to wait will be the product of *retries* number and the *retry_delay* value in seconds.
7. The example uses the automatically passed job ID. For this configuration it is important to place the _wait for job_ task right after the _run checks_ task.

First the _run checks_ task is executed.
It is expected to be a long-running activity and it will not finish in a short time. 
There is no reason to waste the Airflow worker which stays allocated.
To make the _run checks_ task **non-blocking**, the DAG uses the especially designed _wait for job_ task. 
The responsibility to receive the API response with the finished execution details is excluded from _run checks_ task and passed to the next, downstream task.
Now the _run checks_ task is only responsible for calling the DQOps API to initiate running the checks. 

The example releases the worker that execute _run checks_ task before the default timeout passes (which is 120 seconds) by setting the wait_timeout parameter to 10 seconds.
The time of 10 seconds ensures proper operation of the task.

The _wait for job_ task will track the _run checks_ task for two hours. 
It will start and call DQOps API every 60 seconds, 120 times in total.

!!! info "Wait for job operator usage"

    The _wait for job_ operator can be used with an another job types as well.

The overall tracking time is a product of two Airflow operator parameters called retries and retry_delay.

The tracking task will now Fail on detection of a data quality issue blocking the execution of the _load new data_ task.

!!! tip "Be informed by notifications"

    Consider a notification integration that is available in DQOps. <br>
    It allows you to receive the data quality status of resources as fast as possible. <br>
    In the above case you will be informed that run checks activity failed and new data have not loaded.


## Working example in Airflow 

An issue becomes visible in the Airflow Web UI. One of the recent task circles became red in the DAG.
The failure is shown in the DAG details. The _load new data_ task has not been started due to an issue.
The default value "all_success" of the _trigger_rule_ parameter of the operator demands all directly upstream tasks have succeeded.

![airflow-3](https://dqops.com/docs/images/integrations/airflow/run-checks-use-case/airflow-3.png)

It will not be started in the next scheduled DAG executions until the issue is solved.

![airflow-1](https://dqops.com/docs/images/integrations/airflow/run-checks-use-case/airflow-1.png)

For more details which will help to solve the issue use the Incidents in the DQOps UI and filter out the connection on the left side.  

![dqops-1](https://dqops.com/docs/images/integrations/airflow/run-checks-use-case/dqops-1.png)

To reach more information about the issue click the link on the **Data quality issue grouping** in the top menu.

The issue in the example points to the [Completeness dimension](../../dqo-concepts/data-quality-dimensions/data-quality-dimensions.md).
The check that controls the expected number of rows failed.
This means the data are incomplete and the issue has to be investigated and resolved.

!!! warning "Checks verification and adjustments"

    The actual number of rows verified by the check might be expected.
    It may be a result of some work with the data source.
    In this case the rules of the sensor have to be adjusted to the occurred change.

After fixing the issue, the task can be executed manually to rerun the data loading.
You will ensure that the fix did not corrupt the data in case of another data quality dimension because _run checks_ will be executed again.

Finally, the DAG execution should finish uninterrupted as presented on the screen.

![airflow-2](https://dqops.com/docs/images/integrations/airflow/run-checks-use-case/airflow-2.png)


!!! tip "Observe the table after loading"

    Consider using another _run checks_ task as a downstream task to the loading task. <br>
    This approach allows you to observe the state of the table and the data quality before and after the loading phase.


## What's next

- [Learn about webhooks notifications](../webhooks/index.md)
- [Learn about collect statistics operator](collect-statistics-operator.md)
- [Learn about wait for job operator](wait-for-job-operator.md)
- [Learn about table import operator](table-import-operator.md)
- [Learn about table status operator](table-status-operator.md)
