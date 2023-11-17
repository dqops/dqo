import datetime
import pendulum
from airflow import DAG
from dqops.airflow.table_status.dqops_assert_table_status_operator import DqopsAssertTableStatusOperator

with DAG(
    dag_id="dqops_assert_table_status_on_example_connection",
    schedule=datetime.timedelta(hours=12),
    start_date=pendulum.datetime(2023, 1, 1, tz="UTC"),
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
