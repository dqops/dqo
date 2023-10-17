from dqops.airflow.run_checks.dqops_run_checks_operator import DqopsRunChecksOperator
from dqops.client.models.check_type import CheckType
from dqops.client.types import UNSET
from typing import Any, Dict, Union

class DqopsRunPartitionedChecksOperator(DqopsRunChecksOperator):
    """
    Airflow run checks operator for execution of the DQOps partitioning type sensors.

    """

    def __init__(
        self,
        *,
        base_url: str = "http://localhost:8888/",
        api_key: str = UNSET,
        connection_name: str = UNSET,
        schema_table_name: str = UNSET,
        wait_timeout: int = UNSET,
        fail_on_timeout: bool = True,
        **kwargs
    ) -> Union[Dict[str, Any], None]:
        """
        Parameters
        ----------
        base_url : str
            The base url to DQOps application. Default value is http://localhost:8888
        api_key : str
            Api key to DQOps application. Not set as default.
        connection_name : str
            The connection name to the data source in DQOps. When not set, all connection names will be used.
        schema_table_name : str
            The name of the table with the schema. When not set, checks from all tables will be run within the specified connection.
        wait_timeout : int
            Time in seconds for execution that client will wait. It prevents from hanging the task for an action that is never completed. If not set, the timeout is read form the client defaults, which value is 120 seconds.
        fail_on_timeout : str
            Timeout is leading the task status to Failed by default. It can be omitted marking the task as Success by setting the flag to True.
        """
        
        super().__init__(
            base_url=base_url,
            api_key=api_key,
            connection_name=connection_name,
            schema_table_name=schema_table_name,
            check_type=CheckType.PARTITIONED,
            wait_timeout=wait_timeout,
            fail_on_timeout=fail_on_timeout,
            **kwargs
        )
