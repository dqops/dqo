import datetime
import pendulum
from airflow import DAG

from airflow.operators.dummy import DummyOperator
from dqops.airflow.run_checks.dqops_run_checks_operator import DqopsRunChecksOperator
from dqops.airflow.wait_for_job.dqops_wait_for_job_operator import DqopsWaitForJobOperator
from dqops.client.models.check_type import CheckType
from dqops.client.models.rule_severity_level import RuleSeverityLevel

with DAG(
    dag_id="example_connection_run_checks_wait_and_load",
    schedule=datetime.timedelta(hours=12),
    start_date=pendulum.datetime(2021, 1, 1, tz="UTC"),
    catchup=False,
    tags=["dqops_example"]
) as dag:
    run_checks = DqopsRunChecksOperator(
        task_id="dqops_run_checks",
        # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
        base_url="http://host.docker.internal:8888",
        connection="example_connection",
        check_type=CheckType.MONITORING,
        fail_on_timeout=False,
        wait_timeout=10
    )
   
    wait_for_job = DqopsWaitForJobOperator(
        task_id="dqops_wait_for_job",
        # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
        base_url="http://host.docker.internal:8888",
        # the total time in seconds for the operator to wait will be the product of retries number and the retry_delay
        retries=2,
        retry_delay=10, # in seconds
        # trigger_rule="all_done" # crucial when the upstream tracked task do not use fail_on_timeout parameter
        # the below parameter is set automatically when the wait for job operator has the only one upstream task
        # otherwise the parameter has to be set manually because the operator can only wait for a single job 
        # task_id_to_wait_for="dqops_table_import_task",
    )

    # This section contains your business loading task that has to be replaced.
    load_new_data = DummyOperator(
        task_id="load_new_data"
    )

    run_checks >> wait_for_job >> load_new_data

