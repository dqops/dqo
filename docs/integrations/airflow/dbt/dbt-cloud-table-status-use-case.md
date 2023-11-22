# DQOps table status example with the DbtCloud

The Airflow's DAG configuration presents the use of the DbtCloud with the DQOps' table status operator.
The example executes the load job in DbtCloud preceded by the table status Airflow operator that verifies the overall status of the table based on checks defined in DQOps on this table.



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
from dqops.airflow.table_status.dqops_assert_table_status_operator import DqopsAssertTableStatusOperator

with DAG(
    dag_id="dbt_cloud_with_table_status_example",
    schedule=datetime.timedelta(hours=12),
    start_date=pendulum.datetime(2021, 1, 1, tz="UTC"),
    catchup=False,
    tags=["dqops_example"]
) as dag:
    
    assert_status_task = DqopsAssertTableStatusOperator(
        task_id="dqops_assert_table_status_operator_task",
        # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
        base_url="http://host.docker.internal:8888",
        connection_name="marketing_with_dbt",
        schema_name="marketing_final",
        table_name="new_customers_us_daily",
        fail_on_timeout=False,
        wait_timeout=10
    )
   
    pre_load_wait_for_job = DqopsWaitForJobOperator(
        task_id="dqops_wait_for_job_pre_load",
        # local DQOps instance on a localhost can be reached from images with substitution the "host.docker.internal" in place of "localhost"
        base_url="http://host.docker.internal:8888",
        # the total time in seconds for the operator to wait will be the product of retries number and the retry_delay
        retries=30,
        retry_delay=60 # in seconds
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

    assert_status_task >> \
    pre_load_wait_for_job >> \
    dbt_run_load

```


## The execution

The use of table status operator prevents from running load task on table with a data quality issue.

The table status operation reads results from all checks that are set on the table and it's columns.
Then it looks for any data quality issue.

!!! info "Schedules in DQOps"

    DQOps has built in [scheduling component](../../../working-with-dqo/schedules/index.md). It allows to run checks automatically on a different time of day.

The issue found by the table status is equal to finding at least one failed check on the tested table. 
The example do not use any of optional parameters of the DqopsAssertTableStatusOperator, 
so failed checks do not depend on [parameters that are available in the operator](../../table-status-operator.md) 
such as check type, quality dimension or any other, that can limit the number of sensors that the table status task verifies.

If failed check exist, the Airflow DAG execution will not be completed as below. Loading stage will not start.

![dbt-use-case-1](https://dqops.com/docs/images/integrations/airflow/dbt/dbt-cloud-table-status/dbt-cloud-table-status-1.png)

To find out the cause, open DQOps UI. 
Reach either Incidents tab and select the connection of the table or go to the checks

The status can be reviewed in DQOps UI.

#### todo  below #############################################################


!!! tip "Re-run data quality checks"

    On a need of the data quality verification, clearing the __run_checks_post_load__ task makes Airflow will reschedule it immediately
    It will not run the whole dag from the beginning but the last two tasks of the presented example only.





![dbt-use-case-3](https://dqops.com/docs/images/integrations/airflow/dbt/dbt-cloud-table-status/dbt-cloud-table-status-3.png)


Finally, 

![dbt-use-case-2](https://dqops.com/docs/images/integrations/airflow/dbt/dbt-cloud-table-status/dbt-cloud-table-status-2.png)


## What's next

- [Learn about run checks operator](../../run-checks-operator.md)
- [Learn about webhooks notifications](../../../webhooks/index.md)
- [Learn about wait for job operator](../../wait-for-job-operator.md)
