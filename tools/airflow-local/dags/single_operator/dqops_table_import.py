import datetime
import pendulum
from airflow import DAG
from dqops.airflow.table_import.dqops_table_import_operator import DqopsTableImportOperator

with DAG(
    dag_id="dqops_table_import_on_example_connection",
    start_date=pendulum.datetime(2023, 1, 1, tz="UTC"),
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
