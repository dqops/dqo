import datetime
import pendulum
from airflow import DAG
from dqops.airflow.table_status.dqo_assert_table_status_operator import DqoAssertTableStatusOperator
from dqops.client.models.check_type import CheckType

with DAG(
    dag_id="example_connection_dqops_assert_table_status",
    schedule=datetime.timedelta(hours=12),
    start_date=pendulum.datetime(2023, 1, 1, tz="UTC"),
    catchup=False,
) as dag:
    assert_status_task = DqoAssertTableStatusOperator(
        task_id="dqo_assert_table_status_operator_task",
        # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
        base_url="http://host.docker.internal:8888",
        connection_name="example_connection",
        schema_name="maven_restaurant_ratings",
        table_name="consumers",
        check_type=CheckType.MONITORING
    )
