import pendulum
from airflow import DAG
from dqops.airflow.run_checks.dqops_run_checks_operator import DqopsRunChecksOperator
from dqops.airflow.wait_for_job.dqops_wait_for_job_operator import DqopsWaitForJobOperator
from dqops.client.models.check_type import CheckType
from dqops.client.models.rule_severity_level import RuleSeverityLevel

with DAG(
    dag_id="example_connection_wait_for_job_run_checks",
    schedule="@once",
    start_date=pendulum.datetime(2021, 1, 1, tz="UTC"),
    catchup=False,
    tags=["dqops_example"]
) as dag:
    run_checks_task = DqopsRunChecksOperator(
        task_id="dqops_run_checks_operator_task",
        # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
        base_url="http://host.docker.internal:8888",
        connection="example_connection",
        fail_at_severity=RuleSeverityLevel.WARNING,
        check_type=CheckType.MONITORING
    )
   
    wait_for_job = DqopsWaitForJobOperator(
        task_id="dqops_wait_for_job",
        # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
        base_url="http://host.docker.internal:8888",
        # the total time in seconds for the operator to wait will be the product of retries number and the retry_delay
        retries=2,
        retry_delay=10, # in seconds
        trigger_rule="all_done"
        # the below parameter is set automatically when the wait for job operator has the only one upstream task
        # otherwise the parameter has to be set manually because the operator can only wait for a single job 
        # task_id_to_wait_for="dqops_table_import_task",
    )

    run_checks_task >> wait_for_job