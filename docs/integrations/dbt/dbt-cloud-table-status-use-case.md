# DQOps table status example with the DbtCloud
This guide shows how to detect the data quality status of tables observed by DQOps within DbtCloud jobs, allowing to prevent loading corrupted data.

## Integration example

The Airflow's DAG configuration presents the use of the DbtCloud with the DQOps' table status operator.
The example executes the load job in DbtCloud preceded by the table status Airflow operator that verifies the overall status of the table based on checks defined in DQOps on this table.

```python
import datetime
import pendulum
from airflow import DAG

from airflow.providers.dbt.cloud.operators.dbt import DbtCloudRunJobOperator
from dqops.airflow.table_status.dqops_assert_table_status_operator import DqopsAssertTableStatusOperator

with DAG(
    dag_id="dbt_cloud_with_table_status_example",
    schedule=datetime.timedelta(hours=12),
    start_date=pendulum.datetime(2021, 1, 1, tz="UTC"),
    catchup=False,
    tags=["dqops_example"]
) as dag:
    
    assert_status_task = DqopsAssertTableStatusOperator(
        task_id="dqops_assert_table_status_operator_task",
        # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
        base_url="http://host.docker.internal:8888",
        connection_name="marketing_with_dbt",
        schema_name="marketing_final",
        table_name="new_customers_us_daily",
        fail_on_timeout=False,
        wait_timeout=10
    )

    dbt_run_load = DbtCloudRunJobOperator(
        task_id="dbt_run_load",
        dbt_cloud_conn_id="<your cloud connection id in dbt>",
        account_id="<your account id in dbt>",
        job_id="<your job id>",
        check_interval = 120, # every this time the job status in dbt is checked
        timeout = 60 * 60 * 6, # 6 hours
        wait_for_termination=True
    )

    assert_status_task >> \
    dbt_run_load

```


## The execution

The use of table status operator prevents from running the load task on the table with a data quality issue.

The table status operation reads results from all checks that are set on the table and its columns.
Then it looks for any data quality issue.

!!! info "Scheduling data quality checks in DQOps"

    DQOps has a built-in [scheduling component](../../working-with-dqo/configure-scheduling-of-data-quality-checks/index.md).
    It allows you to run checks automatically on a different time of day.


The issue found by the table status is equal to finding at least one failed check on the tested table. 
The example do not use any of optional parameters of the DqopsAssertTableStatusOperator, 
so failed checks do not depend on [parameters that are available in the operator](../airflow/table-status-operator.md) 
such as check type, quality dimension or any other, that can limit the number of sensors that the table status task verifies.

If any failed check exists, the Airflow DAG execution will not be completed as below. Loading stage will not start.

![dbt-use-case-1](https://dqops.com/docs/images/integrations/airflow/dbt/dbt-cloud-table-status/dbt-cloud-table-status-1.png)

To find out the cause, open DQOps UI and reach the Incidents tab in the menu. 
Then select the connection of the table.

!!! tip "Re-run data quality checks"

    On a need of the data quality verification, clearing the __run_checks_post_load__ task makes Airflow will reschedule it immediately
    It will not run the whole dag from the beginning but the last two tasks of the presented example only.

Below DAG is executed after data improvements.

![dbt-use-case-3](https://dqops.com/docs/images/integrations/airflow/dbt/dbt-cloud-table-status/dbt-cloud-table-status-3.png)

As you can see, the DAG is completed successfully. 

![dbt-use-case-2](https://dqops.com/docs/images/integrations/airflow/dbt/dbt-cloud-table-status/dbt-cloud-table-status-2.png)

It will protect the execution of the loading task in the future, when issues appear.

## Checks execution after the dbt load job

Use run checks operator to execute checks after the loading task. 
It will trigger checks that will collect data from the table for the table status task of the next DAG execution.
This approach will not require any scheduling in the DQOps application.

The example only shows the additional python code to the previous, above example. 


```python
# the import section

...
from dqops.airflow.run_checks.dqops_run_checks_operator import DqopsRunChecksOperator
from dqops.airflow.wait_for_job.dqops_wait_for_job_operator import DqopsWaitForJobOperator
...

# the task definitions

...
    run_checks = DqopsRunChecksOperator(
        task_id="dqops_run_checks",
        base_url="http://host.docker.internal:8888",
        connection="marketing_with_dbt",
        full_table_name="marketing_final.new_customers_us_daily"
        fail_on_timeout=False,
        wait_timeout=1
    )

    assert_status_task >> \
    dbt_run_load >> \
    run_checks

```

The code only runs the run checks task that does not wait for the execution of it.
This is why the wait_timeout parameter is set to 1 second.


## What's next

- [Learn about run checks operator](../airflow/run-checks-operator.md)
- [Learn about webhooks notifications](../webhooks/index.md)
- [Learn about wait for job operator](../airflow/wait-for-job-operator.md)
