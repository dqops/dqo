# DQOps run checks example with the Dbt Core 

The Airflow's DAG configuration presents the use of the dbt core with DQOps. 
The example runs the load process in DbtCloud preceded by the assert table status task. 
After the load execution table status is being refreshed by execution of the run checks task with an additional wait for job.

!!! info "Wait for job operator usage"

    The use of _wait for job_ operator in the example prevents from Airflow worker allocation in purpose for waiting until the run checks task has finished.
    This operator can be safely removed when you are sure that your table is lightweight and the DQOps' checks execution lasts less than the default 120 seconds timeout.


The example python code:

```python
import datetime
import pendulum
from airflow import DAG

from airflow.operators.dummy import DummyOperator
from airflow.operators.bash import BashOperator
# from airflow.providers.dbt.cloud.operators.dbt import DbtCloudRunJobOperator
from dqops.airflow.table_status.dqops_assert_table_status_operator import DqopsAssertTableStatusOperator
from dqops.airflow.run_checks.dqops_run_checks_operator import DqopsRunChecksOperator
from dqops.airflow.wait_for_job.dqops_wait_for_job_operator import DqopsWaitForJobOperator
from dqops.client.models.check_type import CheckType
from dqops.client.models.rule_severity_level import RuleSeverityLevel

DBT_PROJECT = "<path to root dbt projects directory>"
DBT_VENV_PATH = "<path to venv>"

run_checks_common_args = {
    "base_url" : "http://host.docker.internal:8888",
    "connection" : "marketing_with_dbt",
    "full_table_name" : "marketing_final.new_customers_us_dail"
    "check_type" : CheckType.MONITORING,
    "fail_on_timeout" : False,
    "wait_timeout" : 10
}

wait_for_job_common_args = {
    # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
    "base_url" : "http://host.docker.internal:8888",
    # the total time in seconds for the operator to wait will be the product of retries number and the retry_delay
    "retries" : 30,
    "retry_delay" : 60 # in seconds
}

with DAG(
    dag_id="dbt_core_example",
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
        table_name="new_customers_us_daily"
    )

    dbt_run_load = BashOperator(
        task_id="dbt_run_load",
        bash_command="source $DBT_VENV_PATH && dbt run --select <dbt project name with model>",
        env={"DBT_VENV_PATH": DBT_VENV_PATH},
        cwd=DBT_PROJECT,
    )

    run_checks = DqopsRunChecksOperator(
        task_id="dqops_run_checks",
        **run_checks_common_args
    )

    wait_for_job = DqopsWaitForJobOperator(
        task_id="dqops_wait_for_job",
        **wait_for_job_common_args
    )

    assert_status_task >> \
    dbt_run_load >> \
    run_checks >> \
    wait_for_job

```

It is recommended that the dbt project should be configured under the python's virtual environment that resolves the dependency conflicts between packages.

## The execution

First the table status task is run. It verifies the checks attached to the table.
If a failure is present, The load task will be halted.
This ensures new data is not loaded to the corrupted table.

To verify the issue, open DQOps UI and go to the Incidents tab in the menu section. Select the connection of the table.

The load will not run until the issue is fixed and previously failed checks run again to collect fresh data.

Next, the load task is run with the use of dbt core. 
After loading data the data quality is tested to provide the actual status.


![dbt-use-case-1](https://dqops.com/docs/images/integrations/airflow/dbt/dbt-core/dbt-core-1.png)


!!! tip "Re-run data quality checks"

    In case of a necessity of the data quality verification, clearing the __run_checks_post_load__ task makes Airflow will reschedule it immediately
    It will not run the whole dag from the beginning but the last two tasks of the presented example only.


## What's next

- [Learn about run checks operator](../airflow/run-checks-operator.md)
- [Learn about webhooks notifications](../webhooks/index.md)
- [Learn about wait for job operator](../airflow/wait-for-job-operator.md)
