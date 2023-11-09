import json
import logging
from typing import Any, Dict, Union

from airflow.models.baseoperator import BaseOperator
from airflow.models.taskinstance import TaskInstance
from httpx import ReadTimeout

from dqops.airflow.common.exceptions.dqops_unfinished_job_exception import (
    DqopsUnfinishedJobException,
)
from dqops.airflow.common.tools.client_creator import create_client
from dqops.airflow.common.tools.server_response_verifier import (
    verify_server_response_correctness,
)
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
    Airflow wait for job operator used to wait until an another job has finished.
    The operator should be used for long running jobs to track the status

    """

    def __init__(
        self,
        *,
        task_id_to_wait_for: Union[Unset, None, str] = UNSET,
        base_url: str = "http://localhost:8888/",
        job_business_key: Union[Unset, None, str] = UNSET,
        wait_timeout: Union[Unset, None, int] = UNSET,
        fail_on_timeout: bool = True,
        **kwargs
    ) -> Union[Dict[str, Any], None]:
        """
        Parameters
        ----------
        task_id_to_wait_for : int
            The id of a task that the operator will wait for.
        base_url : str [optional, default="http://localhost:8888/"]
            The base url to DQOps application.
        job_business_key : Union[Unset, None, str] = UNSET
            Job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.
        wait_timeout : int
            Time in seconds for execution that client will wait. It prevents from hanging the task for an action that is never completed. If not set, the timeout is read form the client defaults, which value is 120 seconds.
        fail_on_timeout : bool [optional, default=True]
            Timeout is leading the task status to Failed by default. It can be omitted marking the task as Success by setting the flag to True.
        """

        super().__init__(**kwargs)
        self.task_id_to_wait_for = task_id_to_wait_for
        self.base_url: str = extract_base_url(base_url)
        self.job_business_key: str = job_business_key
        self.wait_timeout: int = wait_timeout
        self.fail_on_timeout: bool = fail_on_timeout

    def execute(self, context):
        client: Client = create_client(
            base_url=self.base_url, wait_timeout=self.wait_timeout
        )

        try:
            job_id: str = (
                self.job_business_key
                if self.job_business_key is not UNSET
                else self._gather_job_id(context)
            )

            logging.info("the job id is : " + job_id)

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

        if (
            job_result.status == DqoJobStatus.RUNNING
            or job_result.status == DqoJobStatus.WAITING
            or job_result.status == DqoJobStatus.QUEUED
        ):
            raise DqopsUnfinishedJobException()

        return job_result.to_dict()

    def _gather_job_id(self, context) -> str:
        if self.task_id_to_wait_for != UNSET:
            task_to_track: str = self.task_id_to_wait_for
        else:
            task_to_track = print(next(iter(context["task"].upstream_task_ids)))

        ti: TaskInstance = context.get("task_instance")
        xcom_job_result: ImportTablesQueueJobResult = ti.xcom_pull(
            key="return_value", task_ids=task_to_track
        )

        job_id: int = xcom_job_result["jobId"]["jobId"]

        return job_id
