import datetime

import logging
from typing import Any, Dict, Union

from httpx import ReadTimeout
from dqops.airflow.check_results.common.check_results_summary import CheckResultsSummary

from dqops.airflow.exceptions.dqops_data_quality_issue_detected_exception import (
    DqopsDataQualityIssueDetectedException,
)
from dqops.airflow.exceptions.dqops_empty_response_exception import (
    DqopsEmptyResponseException,
)
from dqops.airflow.tools.client_creator import create_client
from dqops.airflow.tools.rule_severity_level_utility import get_severity_value
from dqops.airflow.tools.timeout.python_client_timeout import handle_python_timeout
from dqops.airflow.tools.url_resolver import extract_base_url
from dqops.client import Client
import dqops.client.api.check_results.get_table_profiling_checks_results as profiling_checks_results
import dqops.client.api.check_results.get_table_monitoring_checks_results as monitoring_checks_results
import dqops.client.api.check_results.get_table_partitioned_checks_results as partitioned_checks_results
from dqops.client.models.check_results_list_model import CheckResultsListModel
from dqops.client.models.check_time_scale import CheckTimeScale
from dqops.client.models.check_type import CheckType
from dqops.client.models.rule_severity_level import RuleSeverityLevel
from dqops.client.types import UNSET, Response, Unset
from dqops.client.models.check_type import CheckType

class DqoAssertTableCheckResults():
    """
    Table check result class common to check results airflow operators.

    """

    def __init__(
        self,
        connection_name: str,
        schema_name: str,
        table_name: str,
        check_type: Union[Unset, None, CheckType] = UNSET,
        *,
        time_scale: Union[Unset, None, CheckTimeScale] = UNSET, # only used in monitoring and partitioned
        data_group: Union[Unset, None, str] = UNSET,
        month_start: Union[Unset, None, datetime.date] = UNSET,
        month_end: Union[Unset, None, datetime.date] = UNSET,
        check_name: Union[Unset, None, str] = UNSET,
        category: Union[Unset, None, str] = UNSET,
        table_comparison: Union[Unset, None, str] = UNSET,
        max_results_per_check: Union[Unset, None, int] = UNSET,
        base_url: str = "http://localhost:8888/",
        wait_timeout: int = UNSET,
        fail_on_timeout: bool = True,
        maximum_severity_threshold: RuleSeverityLevel = RuleSeverityLevel.ERROR,
        **kwargs,
    ) -> Union[Dict[str, Any], None]:
        """
        ## todo #####################################################################################################

        Parameters
        ----------
        connection_name : str
            The connection name to the data source in DQOps. When not set, all connection names will be used.
        schema_name : str
            The schema name.
        table_name : str
            The table name.
        check_type : Union[Unset, None, CheckType] = UNSET
            Specifies type of checks to be executed. When not set, all types of checks will be executed. <br/> The enum is stored in _dqops.client.models.check_type_ module.
        check_time_scale : Union[Unset, None, CheckTimeScale] = UNSET
            Time scale filter for monitoring and partitioned checks (values: daily or monthly).
        base_url : str [optional, default="http://localhost:8888/"]
            The base url to DQOps application.
        wait_timeout : int
            Time in seconds for execution that client will wait. It prevents from hanging the task for an action that is never completed. If not set, the timeout is read form the client defaults, which value is 120 seconds.
        fail_on_timeout : bool [optional, default=True]
            Timeout is leading the task status to Failed by default. It can be omitted marking the task as Success by setting the flag to True.
        maximum_severity_threshold: RuleSeverityLevel [optional, default=RuleSeverityLevel.ERROR]
            The maximum level of rule severity that is accepted, causing that an airflow task finishes with succeeded status.
        """

        self.connection_name: str = connection_name
        self.schema_name: str = schema_name
        self.table_name: str = table_name
        self.time_scale: Union[Unset, None, CheckTimeScale] = time_scale

        self.data_group: Union[Unset, None, str] = data_group
        self.month_start: Union[Unset, None, datetime.date] = month_start
        self.month_end: Union[Unset, None, datetime.date] = month_end
        self.check_name: Union[Unset, None, str] = check_name
        self.category: Union[Unset, None, str] = category
        self.table_comparison: Union[Unset, None, str] = table_comparison
        self.max_results_per_check: Union[Unset, None, int] = max_results_per_check

        self.check_type: Union[Unset, None, CheckType] = check_type

        self.base_url: str = extract_base_url(base_url)
        self.wait_timeout: int = wait_timeout
        self.fail_on_timeout: bool = fail_on_timeout

        self.maximum_severity_threshold: RuleSeverityLevel = maximum_severity_threshold

    def execute(self):
        client: Client = create_client(base_url=self.base_url, wait_timeout=self.wait_timeout)

        try:
            if self.check_type == CheckType.PROFILING:
                response: Response[CheckResultsListModel] = self.profiling_sync_detailed(client)
            
            if self.check_type == CheckType.MONITORING:
                response: Response[CheckResultsListModel] = self.monitoring_sync_detailed(client)

            if self.check_type == CheckType.PARTITIONED:
                response: Response[CheckResultsListModel] = self.partitioned_sync_detailed(client)

        except ReadTimeout as exception:
            handle_python_timeout(exception, self.fail_on_timeout)
            return None

        # When timeout is too short, returned object is empty
        if response.content.decode("utf-8") == "":
            raise DqopsEmptyResponseException()

        content_list: list = response.content.decode("utf-8")

        status: CheckResultsSummary = CheckResultsSummary(self.connection_name, 
                                                                    self.schema_name, 
                                                                    self.table_name)
        status.calculate_status(content_list)
        logging.info(status.to_dict())

        if status.highest_severity_issue > get_severity_value(self.maximum_severity_threshold):
            raise DqopsDataQualityIssueDetectedException()

        return status.to_dict()

    def profiling_sync_detailed(self, client) -> Response[CheckResultsListModel]:
        response: Response[CheckResultsListModel] = profiling_checks_results.sync_detailed(
            connection_name=self.connection_name,
            schema_name=self.schema_name,
            table_name=self.table_name,
            client=client,
            data_group=self.data_group,
            month_start=self.month_start,
            month_end=self.month_end,
            check_name=self.check_name,
            category=self.category,
            table_comparison=self.table_comparison,
            max_results_per_check=self.max_results_per_check,
        )

        return response

    def monitoring_sync_detailed(self, client) -> Response[CheckResultsListModel]:
        response: Response[CheckResultsListModel] = monitoring_checks_results.sync_detailed(
            connection_name=self.connection_name,
            schema_name=self.schema_name,
            table_name=self.table_name,
            time_scale=self.time_scale,
            client=client,
            data_group=self.data_group,
            month_start=self.month_start,
            month_end=self.month_end,
            check_name=self.check_name,
            category=self.category,
            table_comparison=self.table_comparison,
            max_results_per_check=self.max_results_per_check,
        )

        return response
    
    def partitioned_sync_detailed(self, client) -> Response[CheckResultsListModel]:
        response: Response[CheckResultsListModel] = partitioned_checks_results.sync_detailed(
            connection_name=self.connection_name,
            schema_name=self.schema_name,
            table_name=self.table_name,
            time_scale=self.time_scale,
            client=client,
            data_group=self.data_group,
            month_start=self.month_start,
            month_end=self.month_end,
            check_name=self.check_name,
            category=self.category,
            table_comparison=self.table_comparison,
            max_results_per_check=self.max_results_per_check,
        )

        return response
