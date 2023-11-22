# DQOps run checks example with the DbtCloud

The Airflow's DAG configuration presents the use of the DbtCloud with the DQOps' run checks operator. 
The example executes the load job in DbtCloud surrounded by data quality verification of a single table, 
done before and after loading the data. 

!!! info "Wait for job operator usage"

    The use of _wait for job_ operator in the example prevents from Airflow worker allocation in purpose for waiting until the run checks task has finished.
    This operator can be safely removed when you are sure that your table is lightweight and the DQOps' checks execution lasts less than the default 120 seconds timeout.


```python
import datetime
import pendulum
from airflow import DAG

from airflow.providers.dbt.cloud.operators.dbt import DbtCloudRunJobOperator
from dqops.airflow.run_checks.dqops_run_checks_operator import DqopsRunChecksOperator
from dqops.airflow.wait_for_job.dqops_wait_for_job_operator import DqopsWaitForJobOperator
from dqops.client.models.check_type import CheckType
from dqops.client.models.rule_severity_level import RuleSeverityLevel

run_checks_common_args = {
    "base_url" : "http://host.docker.internal:8888",
    "connection" : "marketing_with_dbt",
    "full_table_name" : "marketing_final.new_customers_us_daily",
    "check_type" : CheckType.MONITORING,
    "fail_on_timeout" : False,
    "wait_timeout" : 10
}

wait_for_job_common_args = {
    # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
    "base_url" : "http://host.docker.internal:8888",
    # the total time in seconds for the operator to wait will be the product of retries number and the retry_delay
    "retries" : 30,
    "retry_delay" : 60 # in seconds
}

with DAG(
    dag_id="dbt_cloud_example",
    schedule=datetime.timedelta(hours=12),
    start_date=pendulum.datetime(2021, 1, 1, tz="UTC"),
    catchup=False,
    tags=["dqops_example"]
) as dag:
    pre_load_run_checks = DqopsRunChecksOperator(
        task_id="dqops_run_checks_pre_load",
        **run_checks_common_args
    )

    post_load_run_checks = DqopsRunChecksOperator(
        task_id="dqops_run_checks_post_load",
        **run_checks_common_args
    )
   
    pre_load_wait_for_job = DqopsWaitForJobOperator(
        task_id="dqops_wait_for_job_pre_load",
        **wait_for_job_common_args
    )

    post_load_wait_for_job = DqopsWaitForJobOperator(
        task_id="dqops_wait_for_job_post_load",
        **wait_for_job_common_args
    )

    dbt_run_load = DbtCloudRunJobOperator(
        task_id="dbt_run_load",
        dbt_cloud_conn_id="<your cloud connection id in dbt>",
        account_id="<your account id in dbt>",
        job_id="<your job id>",
        check_interval = 120, # every this time the job status in dbt is checked
        timeout = 60 * 60 * 6, # 6 hours
        wait_for_termination=True
    )

    pre_load_run_checks >> \
    pre_load_wait_for_job >> \
    dbt_run_load >> \
    post_load_run_checks >> \
    post_load_wait_for_job
```


## The execution

Testing the data twice has two reasons.

The first is the load job will not start and will not insert new data to the broken table until it is fixed.
The check works like a fuse here.

The second verification is aimed to check the rows that have already arrived at the table.

In case of completeness issue of loaded data, the data engineer responsible for this table can react instantly. 
He will know about the issue because the last task of the DAG will fail as below.

![dbt-use-case-1](https://dqops.com/docs/images/integrations/airflow/dbt-use-case/dbt-1.png)


!!! tip "Re-run data quality checks"

    In case of a necessity of the data quality verification, clearing the __run_checks_post_load__ task makes Airflow will reschedule it immediately
    It will not run the whole dag from the beginning but the last two tasks of the presented example only.


![dbt-use-case-2](https://dqops.com/docs/images/integrations/airflow/dbt-use-case/dbt-2.png)


## What's next

- [Learn about run checks operator](../../run-checks-operator.md)
- [Learn about webhooks notifications](../../../webhooks/index.md)
- [Learn about wait for job operator](../../wait-for-job-operator.md)
