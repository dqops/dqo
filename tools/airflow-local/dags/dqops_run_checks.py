import datetime
import pendulum
from airflow import DAG
from dqops.airflow.run_checks.dqops_run_checks_operator import DqopsRunChecksOperator
from dqops.client.models.check_type import CheckType

with DAG(
    dag_id="dqops_run_checks_on_example_connection",
    schedule=datetime.timedelta(hours=12),
    start_date=pendulum.datetime(2023, 1, 1, tz="UTC"),
    catchup=False,
    tags=["dqops_example"]
) as dag:
    run_checks_task = DqopsRunChecksOperator(
        task_id="dqops_run_checks_operator_task",
        # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
        base_url="http://host.docker.internal:8888",
        connection="example_connection",
        check_type=CheckType.MONITORING
    )
