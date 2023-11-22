import datetime
import pendulum
from airflow import DAG

from airflow.operators.dummy import DummyOperator
# from airflow.providers.dbt.cloud.operators.dbt import DbtCloudRunJobOperator
from dqops.airflow.run_checks.dqops_run_checks_operator import DqopsRunChecksOperator
from dqops.airflow.wait_for_job.dqops_wait_for_job_operator import DqopsWaitForJobOperator
from dqops.client.models.check_type import CheckType
from dqops.client.models.rule_severity_level import RuleSeverityLevel

## todo all below #############################################


PATH_TO_DBT_PROJECT = "<path to your dbt project>"
PATH_TO_DBT_VENV = "<path to your venv activate binary>"

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
    dag_id="dbt_cloud_example",
    schedule=datetime.timedelta(hours=12),
    start_date=pendulum.datetime(2021, 1, 1, tz="UTC"),
    catchup=False,
    tags=["dqops_example"]
) as dag:
    pre_load_run_checks = DqopsRunChecksOperator(
        task_id="dqops_run_checks_pre_load",
        **run_checks_common_args
    )

    post_load_run_checks = DqopsRunChecksOperator(
        task_id="dqops_run_checks_post_load",
        **run_checks_common_args
    )
   
    pre_load_wait_for_job = DqopsWaitForJobOperator(
        task_id="dqops_wait_for_job_pre_load",
        **wait_for_job_common_args
    )

    post_load_wait_for_job = DqopsWaitForJobOperator(
        task_id="dqops_wait_for_job_post_load",
        **wait_for_job_common_args
    )

    dbt_run_load = DummyOperator(task_id="dbt_run_load")




@dag(
    start_date=datetime(2023, 3, 23),
    schedule="@daily",
    catchup=False,
)
def simple_dbt_dag():
    dbt_run = BashOperator(
        task_id="dbt_run",
        bash_command="source $PATH_TO_DBT_VENV && dbt run --models .",
        env={"PATH_TO_DBT_VENV": PATH_TO_DBT_VENV},
        cwd=PATH_TO_DBT_PROJECT,
    )



    # dbt_run_load = DbtCloudRunJobOperator(
    #     task_id="dbt_run_load",
    #     dbt_cloud_conn_id="<your cloud connection id in dbt>",
    #     account_id="<your account id in dbt>",
    #     job_id="<your job id>",
    #     check_interval = 120, # every this time the job status in dbt is checked
    #     timeout = 60 * 60 * 6, # 6 hours
    #     wait_for_termination=True
    # )

    pre_load_run_checks >> \
    pre_load_wait_for_job >> \
    dbt_run_load >> \
    post_load_run_checks >> \
    post_load_wait_for_job
