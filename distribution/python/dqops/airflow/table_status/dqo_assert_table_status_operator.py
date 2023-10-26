import json
import logging
from typing import Any, Dict, Union

from airflow.models.baseoperator import BaseOperator
from httpx import ReadTimeout

from dqops.airflow.common.exceptions.dqops_data_quality_issue_detected_exception import (
    DqopsDataQualityIssueDetectedException,
)
from dqops.airflow.common.exceptions.dqops_empty_response_exception import (
    DqopsEmptyResponseException,
)
from dqops.airflow.common.tools.client_creator import create_client
from dqops.airflow.common.tools.rule_severity_level_utility import get_severity_value
from dqops.airflow.common.tools.timeout.python_client_timeout import handle_python_timeout
from dqops.airflow.common.tools.url_resolver import extract_base_url
from dqops.client import Client
from dqops.client.api.check_results.get_table_data_quality_status import sync_detailed
from dqops.client.models.check_time_scale import CheckTimeScale
from dqops.client.models.check_type import CheckType
from dqops.client.models.rule_severity_level import RuleSeverityLevel
from dqops.client.models.table_data_quality_status_model import (
    TableDataQualityStatusModel,
)
from dqops.client.types import UNSET, Response, Unset

class DqoAssertTableStatusOperator(BaseOperator):
    """
    Airflow assert table status operator for receiving DQOps table status.

    """

    def __init__(
        self,
        connection_name: str,
        schema_name: str,
        table_name: str,
        *,
        months: Union[Unset, None, int] = UNSET,
        check_type: Union[Unset, None, CheckType] = UNSET,
        check_time_scale: Union[Unset, None, CheckTimeScale] = UNSET,
        data_group: Union[Unset, None, str] = UNSET,
        check_name: Union[Unset, None, str] = UNSET,
        category: Union[Unset, None, str] = UNSET,
        table_comparison: Union[Unset, None, str] = UNSET,
        quality_dimension: Union[Unset, None, str] = UNSET,
        base_url: str = "http://localhost:8888/",
        wait_timeout: int = UNSET,
        fail_on_timeout: bool = True,
        maximum_severity_threshold: RuleSeverityLevel = RuleSeverityLevel.ERROR,
        **kwargs
    ) -> Union[Dict[str, Any], None]:
        """
        Parameters
        ----------
        connection_name : str
            The connection name to the data source in DQOps. When not set, all connection names will be used.
        schema_name : str
            The schema name.
        table_name : str
            The table name.
        months : Union[Unset, None, int] = UNSET,
            Optional filter - the number of months to review the data quality check results.
            For partitioned checks, it is the number of months to analyze.
            The default value is 1 (which is the current month and 1 previous month).
        check_type : Union[Unset, None, CheckType] = UNSET
            Specifies type of checks to be executed. When not set, all types of checks will be executed. <br/> The enum is stored in _dqops.client.models.check_type_ module.
        check_time_scale : Union[Unset, None, CheckTimeScale] = UNSET
            Time scale filter for monitoring and partitioned checks (values: daily or monthly).
        data_group: Union[Unset, None, str] = UNSET
            Data group.
        check_name: Union[Unset, None, str] = UNSET
            Data quality check name.
        category: Union[Unset, None, str] = UNSET
            Check's category name.
        table_comparison: Union[Unset, None, str] = UNSET
            Table comparison name.
        quality_dimension: Union[Unset, None, str] = UNSET
            Check quality dimension.
        base_url : str [optional, default="http://localhost:8888/"]
            The base url to DQOps application.
        wait_timeout : int
            Time in seconds for execution that client will wait. It prevents from hanging the task for an action that is never completed. If not set, the timeout is read form the client defaults, which value is 120 seconds.
        fail_on_timeout : bool [optional, default=True]
            Timeout is leading the task status to Failed by default. It can be omitted marking the task as Success by setting the flag to True.
        maximum_severity_threshold: RuleSeverityLevel [optional, default=RuleSeverityLevel.ERROR]
            The maximum level of rule severity that is accepted, causing that an airflow task finishes with succeeded status.
        """

        super().__init__(**kwargs)
        self.connection_name: str = connection_name
        self.schema_name: str = schema_name
        self.table_name: str = table_name
        self.months: Union[Unset, None, int] = months
        self.check_type: Union[Unset, None, CheckType] = check_type
        self.check_time_scale: Union[Unset, None, CheckTimeScale] = check_time_scale
        self.data_group: Union[Unset, None, str] = data_group
        self.check_name: Union[Unset, None, str] = check_name
        self.category: Union[Unset, None, str] = category
        self.table_comparison: Union[Unset, None, str] = table_comparison
        self.quality_dimension: Union[Unset, None, str] = quality_dimension
        self.base_url: str = extract_base_url(base_url)
        self.wait_timeout: int = wait_timeout
        self.fail_on_timeout: bool = fail_on_timeout
        self.maximum_severity_threshold: RuleSeverityLevel = maximum_severity_threshold

    def execute(self, context):
        client: Client = create_client(base_url=self.base_url, wait_timeout=self.wait_timeout)

        try:
            response: Response[TableDataQualityStatusModel] = sync_detailed(
                connection_name=self.connection_name,
                schema_name=self.schema_name,
                table_name=self.table_name,
                client=client,
                months=self.months,
                check_type=self.check_type,
                check_time_scale=self.check_time_scale,
                data_group=self.data_group,
                check_name=self.check_name,
                category=self.category,
                table_comparison=self.table_comparison,
                quality_dimension=self.quality_dimension,
            )
        except ReadTimeout as exception:
            handle_python_timeout(exception, self.fail_on_timeout)
            return None

        # When timeout is too short, returned object is empty
        if response.content.decode("utf-8") == "":
            raise DqopsEmptyResponseException()

        table_dq_status: TableDataQualityStatusModel = (
            TableDataQualityStatusModel.from_dict(
                json.loads(response.content.decode("utf-8"))
            )
        )
        logging.info(table_dq_status.to_dict())

        if table_dq_status.highest_severity_issue > get_severity_value(self.maximum_severity_threshold):
            raise DqopsDataQualityIssueDetectedException()

        return table_dq_status.to_dict()
