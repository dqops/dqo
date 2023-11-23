import datetime
import pendulum
from airflow import DAG

from airflow.operators.dummy import DummyOperator
# from airflow.providers.dbt.cloud.operators.dbt import DbtCloudRunJobOperator
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
    dbt_run_load
