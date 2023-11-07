
"""Example DAG demonstrating the usage of XComs."""
from __future__ import annotations

import pendulum

from airflow import DAG, XComArg
from airflow.decorators import task
from airflow.operators.bash import BashOperator

from dqops.airflow.table_import.dqops_table_import_operator import DqopsTableImportOperator
from dqops.airflow.wait_for_job.dqops_wait_for_job_operator import DqopsWaitForJobOperator

with DAG(
    dag_id="example_xcom_1",
    schedule="@once",
    start_date=pendulum.datetime(2021, 1, 1, tz="UTC"),
    catchup=False,
    tags=["example"]
    
) as dag:
    import_table_task = DqopsTableImportOperator(
        task_id="dqops_connection_dqops_table_import_task",
        # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
        base_url="http://host.docker.internal:8888",
        connection_name="example_connection",
        schema_name="maven_restaurant_ratings",
        table_names=["ratings"]
    )
    
    wait_for_job = DqopsWaitForJobOperator(
        task_id="dqo_wait_for_job",
        # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
        base_url="http://host.docker.internal:8888",
        task_instance_name="dqops_connection_dqops_table_import_task",
        retries=5,
        retry_delay=10
    )

    import_table_task >> wait_for_job