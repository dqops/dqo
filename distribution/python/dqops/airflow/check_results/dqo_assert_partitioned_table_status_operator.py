from typing import Any, Dict, Union

from airflow.models.baseoperator import BaseOperator

from dqops.client.models.check_time_scale import CheckTimeScale
from dqops.client.models.check_type import CheckType
from dqops.client.models.rule_severity_level import RuleSeverityLevel
from dqops.client.types import UNSET, Unset


class DqoAssertPartitionedTableStatusOperator(BaseOperator):
    """
    Airflow assert table status operator for receiving DQOps table status for partitioned type checks.

    """

    def __init__(
        self,
        connection_name: str,
        schema_name: str,
        table_name: str,
        *,
        months: Union[Unset, None, int] = UNSET,
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
        months : Union[Unset, None, int] = UNSET
            Optional filter - the number of months to review the data quality check results.
            For partitioned checks, it is the number of months to analyze.
            The default value is 1 (which is the current month and 1 previous month).
        check_time_scale : Union[Unset, None, CheckTimeScale] = UNSET
            Time scale filter (values: daily or monthly).
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

        super().__init__(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            months=months,
            check_type=CheckType.PARTITIONED,
            check_time_scale=check_time_scale,
            data_group=data_group,
            check_name=check_name,
            category=category,
            table_comparison=table_comparison,
            quality_dimension=quality_dimension,
            base_url=base_url,
            wait_timeout=wait_timeout,
            fail_on_timeout=fail_on_timeout,
            maximum_severity_threshold=maximum_severity_threshold,
            **kwargs
        )

