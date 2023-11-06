import datetime
import pendulum
from airflow import DAG
from dqops.airflow.collect_statistics.dqo_collect_statistics_operator import DqoCollectStatisticsOperator

with DAG(
    dag_id="example_connection_dqops_collect_statistics",
    start_date=pendulum.datetime(2023, 1, 1, tz="UTC"),
    catchup=False,
) as dag:
    import_table_task = DqoCollectStatisticsOperator(
        task_id="dqo_connection_dqops_collect_statistics_task",
        # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
        base_url="http://host.docker.internal:8888",
        connection_name="example_connection",
        schema_table_name="maven_restaurant_ratings.ratings"
    )
