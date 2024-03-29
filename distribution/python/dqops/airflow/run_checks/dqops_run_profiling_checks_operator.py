from typing import Any, Dict, Union

from dqops.airflow.run_checks.dqops_run_checks_operator import DqopsRunChecksOperator
from dqops.client.models.check_type import CheckType
from dqops.client.models.rule_severity_level import RuleSeverityLevel
from dqops.client.types import UNSET, Unset


class DqopsRunProfilingChecksOperator(DqopsRunChecksOperator):
    """
    Airflow run checks operator for execution of the DQOps profiling type sensors.

    """

    def __init__(
        self,
        *,
        base_url: str = "http://localhost:8888/",
        job_business_key: Union[Unset, None, str] = UNSET,
        api_key: str = UNSET,
        connection: str = UNSET,
        full_table_name: str = UNSET,
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
        wait_timeout : int [optional, default=UNSET]
            Time in seconds for execution that client will wait. It prevents from hanging the task for an action that is never completed. If not set, the timeout is read form the client defaults, which value is 120 seconds.
        fail_on_timeout : bool [optional, default=True]
            Timeout is leading the task status to Failed by default. It can be omitted marking the task as Success by setting the flag to True.
        fail_at_severity: RuleSeverityLevel [optional, default=RuleSeverityLevel.FATAL]
            The threshold level of rule severity, causing that an airflow task finishes with failed status.
        """

        super().__init__(
            base_url=base_url,
            job_business_key=job_business_key,
            api_key=api_key,
            connection=connection,
            full_table_name=full_table_name,
            check_type=CheckType.PROFILING,
            wait_timeout=wait_timeout,
            fail_on_timeout=fail_on_timeout,
            fail_at_severity=fail_at_severity,
            **kwargs
        )
