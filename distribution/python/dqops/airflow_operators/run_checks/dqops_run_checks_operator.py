import logging
import json
from airflow.exceptions import AirflowException
from airflow.models.baseoperator import BaseOperator
# from airflow.utils.state import State
from dqops.airflow_operators.url_resolver import extract_base_url
from dqops.client import Client
from dqops.client.api.jobs.run_checks import sync_detailed
from dqops.client.models.check_search_filters import CheckSearchFilters
from dqops.client.models.check_type import CheckType
from dqops.client.models.rule_severity_level import RuleSeverityLevel
from dqops.client.models.run_checks_parameters import RunChecksParameters
from dqops.client.models.run_checks_queue_job_result import RunChecksQueueJobResult
from dqops.client.types import UNSET
from typing import Optional

class DqopsRunChecksOperator(BaseOperator):

    def __init__(self, 
        *,
        url: str = 'http://localhost:8888/',
        api_key: str = UNSET,
        connection_name: str = UNSET,
        full_table_name: str = UNSET,
        check_type: Optional[CheckType] = UNSET,
        **kwargs
    ) -> None:
        
        super().__init__(**kwargs)
        self.base_url: str = extract_base_url(url)
        self.api_key: str = api_key
        self.connection_name: str = connection_name
        self.full_table_name: str = full_table_name
        self.check_type: Optional[CheckType] = check_type

    def execute(self, context):

        filters: CheckSearchFilters = CheckSearchFilters(
            connection_name=self.connection_name,
            check_type=self.check_type,
            schema_table_name=self.full_table_name,
            )

        params: RunChecksParameters = RunChecksParameters(
            check_search_filters=filters)

        client: Client = Client(base_url=self.base_url)
        response: Optional[RunChecksQueueJobResult] = sync_detailed(
            client=client, 
            json_body=params,
            wait=True
        )

        content: json = json.loads(response.content.decode("utf-8"))

        logging.info(content)

        highest_severity : str = content["result"]["highest_severity"]

        if highest_severity != RuleSeverityLevel.VALID:
            # context['task_instance'] = State.FAILED
            raise AirflowException("DQOps checks failed!")

        return content