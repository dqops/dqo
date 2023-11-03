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
from dqops.client.api.jobs.collect_statistics_on_table import sync_detailed
from dqops.client.models.collect_statistics_queue_job_result import (
    CollectStatisticsQueueJobResult,
)
from dqops.client.models.dqo_job_status import DqoJobStatus
from dqops.client.models.statistics_collector_search_filters import (
    StatisticsCollectorSearchFilters,
)
from dqops.client.models.statistics_collector_target import StatisticsCollectorTarget
from dqops.client.types import UNSET, Response, Unset


class DqoCollectStatisticsOperator(BaseOperator):
    """
    Airflow collect statistics operator for receiving DQOps table status.

    """

    def __init__(
        self,
        *,
        connection_name: (Union[Unset, str]) = UNSET,
        schema_table_name: Union[Unset, str] = UNSET,
        enabled: Union[Unset, bool] = UNSET,
        labels: Union[Unset, List[str]] = UNSET,
        column_names: Union[Unset, List[str]] = UNSET,
        sensor_name: Union[Unset, str] = UNSET,
        target: Union[Unset, StatisticsCollectorTarget] = UNSET,
        base_url: str = "http://localhost:8888/",
        wait_timeout: Union[Unset, None, int] = UNSET,
        fail_on_timeout: bool = True,
        **kwargs
    ) -> Union[Dict[str, Any], None]:
        """
        All parameters are optional. When not set, all statistics will be collected

        Parameters
        ----------
        connection_name : Union[Unset, str]
            The connection name to the data source in DQOps.
        schema_table_name : Union[Unset, str]
            The schema name with the table name.
        enabled : Union[Unset, bool]
            If set to true only enabled connections and tables are filtered. Otherwise only disabled connection or table are used.
        labels: Union[Unset, List[str]] = UNSET
            The label names of those edited by user on connections, tables and columns edited in DQOps platform.
        column_names : Union[Unset, List[str]] = UNSET
            The names of columns.
        sensor_name : Union[Unset, str] = UNSET
            The name of the sensor
        target : Union[Unset, StatisticsCollectorTarget] = UNSET
            The name of the target which value is column or table.
        base_url : str [optional, default="http://localhost:8888/"]
            The base url to DQOps application.
        wait_timeout : int
            Time in seconds for execution that client will wait. It prevents from hanging the task for an action that is never completed. If not set, the timeout is read form the client defaults, which value is 120 seconds.
        fail_on_timeout : bool [optional, default=True]
            Timeout is leading the task status to Failed by default. It can be omitted marking the task as Success by setting the flag to True.
        """

        super().__init__(**kwargs)
        self.connection_name: Union[Unset, str] = connection_name
        self.schema_table_name: Union[Unset, str] = schema_table_name
        self.enabled: Union[Unset, bool] = enabled
        self.labels: Union[Unset, List[str]] = labels
        self.column_names: Union[Unset, List[str]] = column_names
        self.sensor_name: Union[Unset, str] = sensor_name
        self.target: Union[Unset, StatisticsCollectorTarget] = target

        self.base_url: str = extract_base_url(base_url)
        self.wait_timeout: int = wait_timeout
        self.fail_on_timeout: bool = fail_on_timeout

    def execute(self, context):
        client: Client = create_client(
            base_url=self.base_url, wait_timeout=self.wait_timeout
        )

        try:
            search_filters: StatisticsCollectorSearchFilters = (
                StatisticsCollectorSearchFilters(
                    connection_name=self.connection_name,
                    schema_table_name=self.schema_table_name,
                    enabled=self.enabled,
                    labels=self.labels,
                    column_names=self.column_names,
                    sensor_name=self.sensor_name,
                    target=self.target,
                )
            )

            response: Response[CollectStatisticsQueueJobResult] = sync_detailed(
                client=client,
                json_body=search_filters,
                wait=True,
                wait_timeout=self.wait_timeout,
            )
        except ReadTimeout as exception:
            handle_python_timeout(exception, self.fail_on_timeout)
            return None

        verify_server_response_correctness(response)

        job_result: CollectStatisticsQueueJobResult = (
            CollectStatisticsQueueJobResult.from_dict(
                json.loads(response.content.decode("utf-8"))
            )
        )
        logging.info(job_result.to_dict())

        if job_result.status == DqoJobStatus.FAILED:
            raise DqopsJobFailedException()

        if job_result.status == DqoJobStatus.RUNNING:
            handle_dqo_timeout(self.fail_on_timeout)

        return job_result.to_dict()
