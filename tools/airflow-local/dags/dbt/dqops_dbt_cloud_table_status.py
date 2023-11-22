import datetime
import pendulum
from airflow import DAG

from airflow.operators.dummy import DummyOperator
# from airflow.providers.dbt.cloud.operators.dbt import DbtCloudRunJobOperator
from dqops.airflow.run_checks.dqops_run_checks_operator import DqopsRunChecksOperator
from dqops.airflow.wait_for_job.dqops_wait_for_job_operator import DqopsWaitForJobOperator
from dqops.client.models.check_type import CheckType
from dqops.client.models.rule_severity_level import RuleSeverityLevel
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
        connection_name="example_connection",
        schema_name="maven_restaurant_ratings",
        table_name="consumers"
    )
   
    pre_load_wait_for_job = DqopsWaitForJobOperator(
        task_id="dqops_wait_for_job_pre_load",
        # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
        base_url="http://host.docker.internal:8888",
        # the total time in seconds for the operator to wait will be the product of retries number and the retry_delay
        retries=30,
        retry_delay=60 # in seconds
    )

    dbt_run_load = DummyOperator(task_id="dbt_run_load")

    # dbt_run_load = DbtCloudRunJobOperator(
    #     task_id="dbt_run_load",
    #     dbt_cloud_conn_id="<your cloud connection id in dbt>",
    #     account_id="<your account id in dbt>",
    #     job_id="<your job id>",
    #     check_interval = 120, # every this time the job status in dbt is checked
    #     timeout = 60 * 60 * 6, # 6 hours
    #     wait_for_termination=True
    # )

    assert_status_task >> \
    pre_load_wait_for_job >> \
    dbt_run_load
