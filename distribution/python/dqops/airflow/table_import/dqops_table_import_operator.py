import json
import logging
from typing import Any, Dict, List, Union

from airflow.models.baseoperator import BaseOperator
from httpx import ReadTimeout

from dqops.airflow.common.exceptions.dqops_job_failed_exception import (
    DqopsJobFailedException,
)
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
from dqops.client.api.jobs.import_tables import sync_detailed
from dqops.client.models.dqo_job_status import DqoJobStatus
from dqops.client.models.import_tables_queue_job_parameters import (
    ImportTablesQueueJobParameters,
)
from dqops.client.models.import_tables_queue_job_result import (
    ImportTablesQueueJobResult,
)
from dqops.client.types import UNSET, Response, Unset


class DqopsTableImportOperator(BaseOperator):
    """
    Airflow assert table status operator for receiving DQOps table status.

    """

    def __init__(
        self,
        connection_name: str,
        schema_name: str,
        table_names: List[str],
        *,
        base_url: str = "http://localhost:8888/",
        job_business_key: Union[Unset, None, str] = UNSET,
        wait_timeout: Union[Unset, None, int] = UNSET,
        fail_on_timeout: bool = True,
        **kwargs
    ) -> Union[Dict[str, Any], None]:
        """
        Parameters
        ----------
        connection_name : str
            The connection name to the data source in DQOps. When not set, all connection names will be used.
        schema_name : str
            The schema name.
        table_names : List[str]
            The table names to be imported.
        base_url : str [optional, default="http://localhost:8888/"]
            The base url to DQOps application.
        job_business_key : Union[Unset, None, str] = UNSET
            Job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.
        wait_timeout : Union[Unset, None, int]
            Time in seconds for execution that client will wait. It prevents from hanging the task for an action that is never completed. If not set, the timeout is read form the client defaults, which value is 120 seconds.
        fail_on_timeout : bool [optional, default=True]
            Timeout is leading the task status to Failed by default. It can be omitted marking the task as Success by setting the flag to True.
        """

        super().__init__(**kwargs)
        self.connection_name: str = connection_name
        self.schema_name: str = schema_name
        self.table_names: List[str] = table_names

        self.base_url: str = extract_base_url(base_url)
        self.job_business_key: Union[Unset, None, str] = job_business_key
        self.wait_timeout: Union[Unset, None, int] = wait_timeout
        self.fail_on_timeout: bool = fail_on_timeout

    def execute(self, context):
        client: Client = create_client(
            base_url=self.base_url, wait_timeout=self.wait_timeout
        )

        try:
            table_import_parameters: ImportTablesQueueJobParameters = (
                ImportTablesQueueJobParameters(
                    connection_name=self.connection_name,
                    schema_name=self.schema_name,
                    table_names=self.table_names,
                )
            )

            response: Response[ImportTablesQueueJobResult] = sync_detailed(
                client=client,
                json_body=table_import_parameters,
                job_business_key=self.job_business_key,
                wait=True,
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
            raise DqopsJobFailedException(context["ti"], job_result.to_dict())

        if job_result.status == DqoJobStatus.RUNNING:
            handle_dqo_timeout(self.fail_on_timeout)

        return job_result.to_dict()
