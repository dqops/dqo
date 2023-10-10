import logging
import json

from dqops.airflow_operators.exceptions.DqopsChecksFailedException import DqopsChecksFailedException
from dqops.airflow_operators.exceptions.DqopsJobFailedException import DqopsJobFailedException
from dqops.airflow_operators.url_resolver import extract_base_url
from dqops.client import Client
from dqops.client.api.jobs.run_checks import sync_detailed
from dqops.client.models.check_search_filters import CheckSearchFilters
from dqops.client.models.check_type import CheckType
from dqops.client.models.dqo_job_status import DqoJobStatus
from dqops.client.models.run_checks_parameters import RunChecksParameters
from dqops.client.models.run_checks_queue_job_result import RunChecksQueueJobResult
from dqops.client.models.rule_severity_level import RuleSeverityLevel
from dqops.client.types import UNSET, Response
from httpx import ReadTimeout
from typing import Optional

class DqopsRunChecksOperator(BaseOperator):

    def __init__(self, 
        *,
        url: str = 'http://localhost:8888/',
        api_key: str = UNSET,
        connection_name: str = UNSET,
        schema_table_name: str = UNSET,
        check_type: Optional[CheckType] = UNSET,
        wait_timeout: int = UNSET,
        fail_on_timeout: bool = True,
        **kwargs
    ) -> None:
        
        super().__init__(**kwargs)
        self.base_url: str = extract_base_url(url)
        self.api_key: str = api_key
        self.connection_name: str = connection_name
        self.schema_table_name: str = schema_table_name
        self.check_type: Optional[CheckType] = check_type
        self.wait_timeout: int = wait_timeout
        self.fail_on_timeout: bool = fail_on_timeout

    def execute(self, context):

        filters: CheckSearchFilters = CheckSearchFilters(
            connection_name=self.connection_name,
            check_type=self.check_type,
            schema_table_name=self.schema_table_name,
        )

        params: RunChecksParameters = RunChecksParameters(check_search_filters=filters)

        client: Client = Client(base_url=self.base_url)

        try:
            response: Response[RunChecksQueueJobResult] = sync_detailed(
                client=client, 
                json_body=params,
                wait=True,
                wait_timeout=self.wait_timeout
            )

            job_result: RunChecksQueueJobResult = RunChecksQueueJobResult.from_dict(json.loads(response.content.decode("utf-8")))

            logging.info(job_result.to_dict())

            if job_result.status == DqoJobStatus.FAILED:
                raise DqopsJobFailedException()

            if (job_result.result.highest_severity is not None   
                    and job_result.result.highest_severity != RuleSeverityLevel.VALID 
                    and job_result.status != DqoJobStatus.CANCELLED):
                raise DqopsChecksFailedException()
            
        except ReadTimeout as exception:
            logging.info("The job has timed out.")

            if self.fail_on_timeout:
                raise exception

            return None

        return job_result.to_dict()