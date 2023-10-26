import json
import logging
from typing import Any, Dict, Optional, Union

from airflow.models.baseoperator import BaseOperator
from httpx import ReadTimeout
from dqops.airflow.common.exceptions.dqops_data_quality_issue_detected_exception import DqopsDataQualityIssueDetectedException
from dqops.airflow.common.exceptions.dqops_empty_response_exception import DqopsEmptyResponseException
from dqops.airflow.common.exceptions.dqops_job_failed_exception import DqopsJobFailedException
from dqops.airflow.common.tools.client_creator import create_client
from dqops.airflow.common.tools.rule_severity_level_utility import get_severity_value
from dqops.airflow.common.tools.timeout.dqo_timeout import handle_dqo_timeout
from dqops.airflow.common.tools.timeout.python_client_timeout import handle_python_timeout
from dqops.airflow.common.tools.url_resolver import extract_base_url
from dqops.client import Client
from dqops.client.api.jobs.run_checks import sync_detailed
from dqops.client.models.check_search_filters import CheckSearchFilters
from dqops.client.models.check_type import CheckType
from dqops.client.models.dqo_job_status import DqoJobStatus
from dqops.client.models.rule_severity_level import RuleSeverityLevel
from dqops.client.models.run_checks_parameters import RunChecksParameters
from dqops.client.models.run_checks_queue_job_result import RunChecksQueueJobResult
from dqops.client.types import UNSET, Response


class DqopsRunChecksOperator(BaseOperator):
    """
    Airflow run checks operator for execution of the DQOps sensors.

    """

    def __init__(
        self,
        *,
        base_url: str = "http://localhost:8888/",
        api_key: str = UNSET,
        connection_name: str = UNSET,
        schema_table_name: str = UNSET,
        check_type: Optional[CheckType] = UNSET,
        wait_timeout: int = UNSET,
        fail_on_timeout: bool = True,
        maximum_severity_threshold: RuleSeverityLevel = RuleSeverityLevel.ERROR,
        **kwargs
    ) -> Union[Dict[str, Any], None]:
        """
        Parameters
        ----------
        base_url : str [optional, default="http://localhost:8888/"]
            The base url to DQOps application.
        api_key : str [optional, default=UNSET]
            Api key to DQOps application. Not set as default.
        connection_name : str [optional, default=UNSET]
            The connection name to the data source in DQOps. When not set, all connection names will be used.
        schema_table_name : str [optional, default=UNSET]
            The name of the table with the schema. When not set, checks from all tables will be run within the specified connection.
        check_type : CheckType [optional, default=UNSET]
            Specifies type of checks to be executed. When not set, all types of checks will be executed. <br/> The enum is stored in _dqops.client.models.check_type_ module.
        wait_timeout : int [optional, default=UNSET]
            Time in seconds for execution that client will wait. It prevents from hanging the task for an action that is never completed. If not set, the timeout is read form the client defaults, which value is 120 seconds.
        fail_on_timeout : bool [optional, default=True]
            Timeout is leading the task status to Failed by default. It can be omitted marking the task as Success by setting the flag to True.
        maximum_severity_threshold: RuleSeverityLevel [optional, default=RuleSeverityLevel.ERROR]
            The maximum level of rule severity that is accepted, causing that an airflow task finishes with succeeded status.
        """

        super().__init__(**kwargs)
        self.base_url: str = extract_base_url(base_url)
        self.api_key: str = api_key
        self.connection_name: str = connection_name
        self.schema_table_name: str = schema_table_name
        self.check_type: Optional[CheckType] = check_type
        self.wait_timeout: int = wait_timeout
        self.fail_on_timeout: bool = fail_on_timeout
        self.maximum_severity_threshold: RuleSeverityLevel = maximum_severity_threshold

    def execute(self, context):
        filters: CheckSearchFilters = CheckSearchFilters(
            connection_name=self.connection_name,
            check_type=self.check_type,
            schema_table_name=self.schema_table_name,
        )
        params: RunChecksParameters = RunChecksParameters(check_search_filters=filters)

        client: Client = create_client(
            base_url=self.base_url, wait_timeout=self.wait_timeout
        )

        try:
            response: Response[RunChecksQueueJobResult] = sync_detailed(
                client=client,
                json_body=params,
                wait=True,
                wait_timeout=self.wait_timeout,
            )
        except ReadTimeout as exception:
            handle_python_timeout(exception, self.fail_on_timeout)
            return None

        # When timeout is too short, returned object is empty
        if response.content.decode("utf-8") == "":
            raise DqopsEmptyResponseException()

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
            and job_result.result.highest_severity
            > get_severity_value(self.maximum_severity_threshold)
            and job_result.status != DqoJobStatus.CANCELLED
        ):
            raise DqopsDataQualityIssueDetectedException()

        return job_result.to_dict()
