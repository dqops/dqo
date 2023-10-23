import datetime
from typing import Any, Dict, Union

from airflow.models.baseoperator import BaseOperator
from dqops.airflow.check_results.common.dqo_assert_table_check_results import (
    DqoAssertTableCheckResults
)
from dqops.airflow.common.tools.url_resolver import extract_base_url
from dqops.client.models.check_time_scale import CheckTimeScale
from dqops.client.models.check_type import CheckType
from dqops.client.models.rule_severity_level import RuleSeverityLevel
from dqops.client.types import UNSET, Unset


class DqoAssertTableMonitoringCheckResultsOperator(BaseOperator):
    """
    Returns the summary for the complete results of the most recent check executions for all table level data quality monitoring checks on a table",
    
    """

    def __init__(
        self,
        connection_name: str,
        schema_name: str,
        table_name: str,
        time_scale: Union[Unset, None, CheckTimeScale] = UNSET,
        *,
        data_group: Union[Unset, None, str] = UNSET,
        month_start: Union[Unset, None, datetime.date] = UNSET,
        month_end: Union[Unset, None, datetime.date] = UNSET,
        check_name: Union[Unset, None, str] = UNSET,
        category: Union[Unset, None, str] = UNSET,
        table_comparison: Union[Unset, None, str] = UNSET,
        max_results_per_check: Union[Unset, None, int] = UNSET,
        months: Union[Unset, None, int] = UNSET,
        base_url: str = "http://localhost:8888/",
        wait_timeout: int = UNSET,
        fail_on_timeout: bool = True,
        maximum_severity_threshold: RuleSeverityLevel = RuleSeverityLevel.ERROR,
        **kwargs,
    ) -> Union[Dict[str, Any], None]:
        """
        Parameters
        ----------
        connection_name : str
            The connection name to the data source in DQOps.
        schema_name : str
            The schema name.
        table_name : str
            The table name.
        time_scale : Union[Unset, None, CheckTimeScale] = UNSET
            Time scale filter for monitoring and partitioned checks (values: daily or monthly).
        data_group : Union[Unset, None, str] = UNSET,
            Data group.
        month_start : Union[Unset, None, datetime.date] = UNSET
            Month start boundary
        month_end : Union[Unset, None, datetime.date] = UNSET
            Month end boundary
        check_name : Union[Unset, None, str] = UNSET
            Check name
        category : Union[Unset, None, str] = UNSET
            Check category name
        table_comparison : Union[Unset, None, str] = UNSET
            Table comparison name
        max_results_per_check : Union[Unset, None, int] = UNSET
            Maximum number of results per check
        base_url : str [optional, default="http://localhost:8888/"]
            The base url to DQOps application.
        wait_timeout : int
            Time in seconds for execution that client will wait. It prevents from hanging the task for an action that is never completed. If not set, the timeout is read form the client defaults, which value is 120 seconds.
        fail_on_timeout : bool [optional, default=True]
            Timeout is leading the task status to Failed by default. It can be omitted marking the task as Success by setting the flag to True.
        maximum_severity_threshold: RuleSeverityLevel [optional, default=RuleSeverityLevel.ERROR]
            The maximum level of rule severity that is accepted, causing that an airflow task finishes with succeeded status.
        """

        self.check_type: CheckType = CheckType.MONITORING

        super().__init__(**kwargs)
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

        self.months: Union[Unset, None, int] = months

        self.base_url: str = extract_base_url(base_url)
        self.wait_timeout: int = wait_timeout
        self.fail_on_timeout: bool = fail_on_timeout

        self.maximum_severity_threshold: RuleSeverityLevel = maximum_severity_threshold
        self.__dict__.update(kwargs)

    def execute(self, context):

        check_result: DqoAssertTableCheckResults = DqoAssertTableCheckResults(
            connection_name=self.connection_name,
            schema_name=self.schema_name,
            table_name=self.table_name,
            time_scale=self.time_scale,
            data_group=self.data_group,
            month_start=self.month_start,
            month_end=self.month_end,
            check_name=self.check_name,
            category=self.category,
            table_comparison=self.table_comparison,
            max_results_per_check=self.max_results_per_check,
            months=self.months,
            check_type=self.check_type,
            base_url=self.base_url,
            wait_timeout=self.wait_timeout,
            fail_on_timeout=self.fail_on_timeout,
            maximum_severity_threshold=self.maximum_severity_threshold,
            kwargs=self.__dict__
        )
        
        return check_result.execute()
