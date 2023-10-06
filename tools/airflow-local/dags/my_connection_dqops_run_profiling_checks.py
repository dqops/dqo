import airflow
from airflow import DAG
from airflow.utils.dates import days_ago
import datetime
import pendulum
from dqops.airflow_operators.run_checks.dqops_run_checks_operator import DqopsRunChecksOperator
from dqops.airflow_operators.run_checks.dqops_run_profiling_checks_operator import DqopsRunProfilingChecksOperator
from dqops.client.models.check_type import CheckType

with DAG(
    dag_id="my_connection_dqops_run_profiling_checks",
    schedule=datetime.timedelta(hours=4),
    start_date=pendulum.datetime(2021, 1, 1, tz="UTC"),
    catchup=False,
) as dag:
    hello_task = DqopsRunProfilingChecksOperator(
        task_id="dqops_run_profiling_checks_operator_task",
        # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
        url='http://host.docker.internal:8888',
        connection_name="my"
    )
