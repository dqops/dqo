import logging
import json

from airflow.models.baseoperator import BaseOperator
from airflow.exceptions import AirflowException
from dqops.airflow.exceptions.dqops_checks_failed_exception import DqopsChecksFailedException
from dqops.airflow.exceptions.dqops_empty_response_exception import DqopsEmptyResponseException
from dqops.airflow.exceptions.dqops_job_failed_exception import DqopsJobFailedException
from dqops.airflow.tools.url_resolver import extract_base_url
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
    """
    Airflow run checks operator for execution the DQOps sensors.

    """

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
        """
        Parameters
        ----------
        url : str
            The base url to DQOps application. Default value is http://localhost:8888
        api_key : str
            Api key to DQOps application. Not set as default.
        connection_name : str
            The connection name to the data source in DQOps. When not set, all connection names will be used.
        schema_table_name : str
            The name of the table with the schema. When not set, checks from all tables will be run within the specified connection.
        check_type : CheckType
            Specifies type of checks to be executed. When not set, all types of checks will be executed. <br/> The enum is stored in _dqops.client.models.check_type_ module.
        wait_timeout : int
            Time in seconds for execution that client will wait. It prevents from hanging the task for an action that is never completed. If not set, the timeout is read form the client defaults, which value is 120 seconds.
        fail_on_timeout : str
            Timeout is leading the task status to Failed by default. It can be omitted marking the task as Success by setting the flag to True.
        """
        
        super().__init__(**kwargs)
        self.base_url: str = extract_base_url(url)
        self.api_key: str = api_key
        self.connection_name: str = connection_name
        self.schema_table_name: str = schema_table_name
        self.check_type: Optional[CheckType] = check_type
        self.wait_timeout: int = wait_timeout
        self.fail_on_timeout: bool = fail_on_timeout

    def execute(self, context):

        extra_timeout_seconds: int = 1

        filters: CheckSearchFilters = CheckSearchFilters(
            connection_name=self.connection_name,
            check_type=self.check_type,
            schema_table_name=self.schema_table_name,
        )

        params: RunChecksParameters = RunChecksParameters(check_search_filters=filters)

        client: Client = Client(base_url=self.base_url, 
                                timeout=self.wait_timeout + extra_timeout_seconds) # added extra time to python client to wait for dqo after it times out

        try:
            response: Response[RunChecksQueueJobResult] = sync_detailed(
                client=client, 
                json_body=params,
                wait=True,
                wait_timeout=self.wait_timeout
            )
        except ReadTimeout as exception:
            timeout_message: str = "DQOps' job has timed out!"  
            if self.fail_on_timeout:
                logging.error(timeout_message)
                raise exception
            else:
                logging.info(timeout_message)
            return None

        # When timeout is too short, returned object is empty
        if response.content.decode("utf-8") == '':
            raise DqopsEmptyResponseException()

        job_result: RunChecksQueueJobResult = RunChecksQueueJobResult.from_dict(json.loads(response.content.decode("utf-8")))
        logging.info(job_result.to_dict())

        if job_result.status == DqoJobStatus.FAILED:
            raise DqopsJobFailedException()

        # dqo times out with RunChecksQueueJobResult object details
        if job_result.status == DqoJobStatus.RUNNING:
            self._handle_timeout()

        if (job_result.result.highest_severity is not None   
                and job_result.result.highest_severity != RuleSeverityLevel.VALID 
                and job_result.status != DqoJobStatus.CANCELLED):
            raise DqopsChecksFailedException()

        return job_result.to_dict()
    
    def _handle_timeout(self):
        timeout_message: str = "DQOps' job has timed out!"  

        if self.fail_on_timeout:
            logging.error(timeout_message)
            raise AirflowException()
        else:
            logging.info(timeout_message)