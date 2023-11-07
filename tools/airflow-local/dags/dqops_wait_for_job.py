import pendulum
from airflow import DAG

from dqops.airflow.table_import.dqops_table_import_operator import DqopsTableImportOperator
from dqops.airflow.wait_for_job.dqops_wait_for_job_operator import DqopsWaitForJobOperator

with DAG(
    dag_id="example_connection_wait_for_job",
    schedule="@once",
    start_date=pendulum.datetime(2021, 1, 1, tz="UTC"),
    catchup=False,
    tags=["dqops_example"]
) as dag:
    import_table_task = DqopsTableImportOperator(
        task_id="dqops_connection_dqops_table_import_task",
        # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
        base_url="http://host.docker.internal:8888",
        connection_name="example_connection",
        schema_name="maven_restaurant_ratings",
        table_names=["ratings"]
    )

    import_table_task_2 = DqopsTableImportOperator(
        task_id="dqops_connection_dqops_table_import_task_2",
        # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
        base_url="http://host.docker.internal:8888",
        connection_name="example_connection",
        schema_name="maven_restaurant_ratings",
        table_names=["consumers"]
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
        # task_id_to_wait_for="dqops_connection_dqops_table_import_task",
    )

    import_table_task >> wait_for_job