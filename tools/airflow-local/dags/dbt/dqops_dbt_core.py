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
    "connection" : "example_connection",
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
        connection_name="example_connection",
        schema_name="maven_restaurant_ratings",
        table_name="consumers"
    )

    # dummy task
    dbt_run_load = BashOperator(task_id="dbt_run_load", bash_command="echo 1")

    # dbt_run_load = BashOperator(
    #     task_id="dbt_run_load",
    #     bash_command="source $DBT_VENV_PATH && dbt run --select <dbt project name with model>",
    #     env={"DBT_VENV_PATH": DBT_VENV_PATH},
    #     cwd=DBT_PROJECT,
    # )

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
