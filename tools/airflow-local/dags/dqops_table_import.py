import datetime
import pendulum
from airflow import DAG
from dqops.airflow.table_import.dqo_table_import_operator import DqoTableImportOperator

with DAG(
    dag_id="example_connection_dqops_table_import",
    start_date=pendulum.datetime(2023, 1, 1, tz="UTC"),
    catchup=False,
) as dag:
    import_table_task = DqoTableImportOperator(
        task_id="dqo_connection_dqops_table_import_task",
        # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
        base_url="http://host.docker.internal:8888",
        connection_name="example_connection",
        schema_name="maven_restaurant_ratings",
        table_names=["ratings"]
    )
