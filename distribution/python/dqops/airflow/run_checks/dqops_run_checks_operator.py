import json
import logging
from typing import Any, Dict, Optional, Union

from airflow.models.baseoperator import BaseOperator
from httpx import ReadTimeout

from dqops.airflow.common.exceptions.dqops_data_quality_issue_detected_exception import (
    DqopsDataQualityIssueDetectedException,
)
from dqops.airflow.common.exceptions.dqops_job_failed_exception import (
    DqopsJobFailedException,
)
from dqops.airflow.common.tools.client_creator import create_client
from dqops.airflow.common.tools.rule_severity_level_utility import get_severity_value
from dqops.airflow.common.tools.server_response_verifier import (
    verify_server_response_correctness,
)
from dqops.airflow.common.tools.timeout.dqo_timeout import handle_dqo_timeout
from dqops.airflow.common.tools.timeout.python_client_timeout import (
    handle_python_timeout,
)
from dqops.airflow.common.tools.url_resolver import extract_base_url
from dqops.client import Client
from dqops.client.api.jobs.run_checks import sync_detailed
from dqops.client.models.check_search_filters import CheckSearchFilters
from dqops.client.models.check_type import CheckType
from dqops.client.models.dqo_job_status import DqoJobStatus
from dqops.client.models.rule_severity_level import RuleSeverityLevel
from dqops.client.models.run_checks_parameters import RunChecksParameters
from dqops.client.models.run_checks_queue_job_result import RunChecksQueueJobResult
from dqops.client.types import UNSET, Response, Unset


class DqopsRunChecksOperator(BaseOperator):
    """
    Airflow run checks operator for execution of the DQOps sensors.

    """

    def __init__(
        self,
        *,
        base_url: str = "http://localhost:8888/",
        job_business_key: Union[Unset, None, str] = UNSET,
        api_key: str = UNSET,
        connection: str = UNSET,
        full_table_name: str = UNSET,
        check_type: Optional[CheckType] = UNSET,
        wait_timeout: int = UNSET,
        fail_on_timeout: bool = True,
        fail_at_severity: RuleSeverityLevel = RuleSeverityLevel.FATAL,
        **kwargs
    ) -> Union[Dict[str, Any], None]:
        """
        Parameters
        ----------
        base_url : str [optional, default="http://localhost:8888/"]
            The base url to DQOps application.
        job_business_key : Union[Unset, None, str] = UNSET
            Job business key that is a user assigned unique job id, used to check the job status by looking up the job by a user assigned identifier, instead of the DQOps assigned job identifier.
        api_key : str [optional, default=UNSET]
            Api key to DQOps application. Not set as default.
        connection : str [optional, default=UNSET]
            The connection name to the data source in DQOps. When not set, all connection names will be used.
        full_table_name : str [optional, default=UNSET]
            The name of the table with the schema. When not set, checks from all tables will be run within the specified connection.
        check_type : CheckType [optional, default=UNSET]
            Specifies type of checks to be executed. When not set, all types of checks will be executed. <br/> The enum is stored in _dqops.client.models.check_type_ module.
        wait_timeout : int [optional, default=UNSET]
            Time in seconds for execution that client will wait. It prevents from hanging the task for an action that is never completed. If not set, the timeout is read form the client defaults, which value is 120 seconds.
        fail_on_timeout : bool [optional, default=True]
            Timeout is leading the task status to Failed by default. It can be omitted marking the task as Success by setting the flag to True.
        fail_at_severity: RuleSeverityLevel [optional, default=RuleSeverityLevel.FATAL]
            The threshold level of rule severity, causing that an airflow task finishes with failed status.
        """

        super().__init__(**kwargs)
        self.base_url: str = extract_base_url(base_url)
        self.job_business_key: Union[Unset, None, str] = job_business_key
        self.api_key: str = api_key
        self.connection: str = connection
        self.full_table_name: str = full_table_name
        self.check_type: Optional[CheckType] = check_type
        self.wait_timeout: int = wait_timeout
        self.fail_on_timeout: bool = fail_on_timeout
        self.fail_at_severity: RuleSeverityLevel = fail_at_severity

    def execute(self, context):
        filters: CheckSearchFilters = CheckSearchFilters(
            connection=self.connection,
            check_type=self.check_type,
            full_table_name=self.full_table_name,
        )
        params: RunChecksParameters = RunChecksParameters(check_search_filters=filters)

        client: Client = create_client(
            base_url=self.base_url, wait_timeout=self.wait_timeout
        )

        try:
            response: Response[RunChecksQueueJobResult] = sync_detailed(
                client=client,
                json_body=params,
                job_business_key=self.job_business_key,
                wait=True,
                wait_timeout=self.wait_timeout,
            )
        except ReadTimeout as exception:
            handle_python_timeout(exception, self.fail_on_timeout)
            return None

        verify_server_response_correctness(response)

        job_result: RunChecksQueueJobResult = RunChecksQueueJobResult.from_dict(
            json.loads(response.content.decode("utf-8"))
        )
        logging.info(job_result.to_dict())

        if job_result.status == DqoJobStatus.FAILED:
            raise DqopsJobFailedException()

        # dqo times out with RunChecksQueueJobResult object details
        if job_result.status == DqoJobStatus.RUNNING:
            handle_dqo_timeout(self.fail_on_timeout)

        if (
            job_result.result.highest_severity is not None
            and get_severity_value(job_result.result.highest_severity)
            >= get_severity_value(self.fail_at_severity)
            and job_result.status != DqoJobStatus.CANCELLED
        ):
            raise DqopsDataQualityIssueDetectedException()

        return job_result.to_dict()
