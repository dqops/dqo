import json
import logging
from typing import Any, Dict, Union

from airflow.models.baseoperator import BaseOperator
from airflow.models.taskinstance import TaskInstance
from httpx import ReadTimeout

from dqops.airflow.common.exceptions.dqops_job_failed_exception import (
    DqopsJobFailedException,
)
from dqops.airflow.common.exceptions.dqops_unfinished_job_exception import DqopsUnfinishedJobException
from dqops.airflow.common.tools.client_creator import create_client
from dqops.airflow.common.tools.server_response_verifier import (
    verify_server_response_correctness,
)
from dqops.airflow.common.tools.timeout.dqo_timeout import handle_dqo_timeout
from dqops.airflow.common.tools.timeout.python_client_timeout import (
    handle_python_timeout,
)
from dqops.airflow.common.tools.url_resolver import extract_base_url
from dqops.client import Client
from dqops.client.api.jobs.wait_for_job import sync_detailed
from dqops.client.models.dqo_job_history_entry_model import DqoJobHistoryEntryModel
from dqops.client.models.dqo_job_status import DqoJobStatus
from dqops.client.models.import_tables_queue_job_result import (
    ImportTablesQueueJobResult,
)
from dqops.client.types import UNSET, Response, Unset


class DqopsWaitForJobOperator(BaseOperator):
    """
    Airflow wait for job operator for ################################ todo.

    """

    def __init__(
        self,
        job_id: int,
        *,
        base_url: str = "http://localhost:8888/",
        wait_timeout: Union[Unset, None, int] = UNSET,
        fail_on_timeout: bool = True,
        task_instance_name,
        **kwargs
    ) -> Union[Dict[str, Any], None]:
        """
        Parameters
        ----------
        job_id : int
            ####################################################################### todo
        base_url : str [optional, default="http://localhost:8888/"]
            The base url to DQOps application.
        wait_timeout : int
            Time in seconds for execution that client will wait. It prevents from hanging the task for an action that is never completed. If not set, the timeout is read form the client defaults, which value is 120 seconds.
        fail_on_timeout : bool [optional, default=True]
            Timeout is leading the task status to Failed by default. It can be omitted marking the task as Success by setting the flag to True.
        """

        super().__init__(**kwargs)
        self.job_id: int = job_id
        self.base_url: str = extract_base_url(base_url)
        self.wait_timeout: int = wait_timeout
        self.fail_on_timeout: bool = fail_on_timeout
        self.task_instance_name = task_instance_name

    def execute(self, context):
        client: Client = create_client(
            base_url=self.base_url, wait_timeout=self.wait_timeout
        )

        try:
            ti: TaskInstance = context.get("task_instance")
            xcom_job_result: ImportTablesQueueJobResult = ti.xcom_pull(
                key="return_value", task_ids=self.task_instance_name
            )

            job_id = xcom_job_result["jobId"]["jobId"]

            response: Response[DqoJobHistoryEntryModel] = sync_detailed(
                job_id=job_id,
                client=client,
                wait_timeout=self.wait_timeout,
            )

        except ReadTimeout as exception:
            handle_python_timeout(exception, self.fail_on_timeout)
            return None

        verify_server_response_correctness(response)

        job_result: ImportTablesQueueJobResult = ImportTablesQueueJobResult.from_dict(
            json.loads(response.content.decode("utf-8"))
        )
        logging.info(job_result.to_dict())

        if job_result.status == DqoJobStatus.FAILED:
            raise DqopsJobFailedException()

        if job_result.status == DqoJobStatus.RUNNING:
            handle_dqo_timeout(self.fail_on_timeout)

        if job_result.status != DqoJobStatus.SUCCEEDED:
            raise DqopsUnfinishedJobException()

        return job_result.to_dict()
