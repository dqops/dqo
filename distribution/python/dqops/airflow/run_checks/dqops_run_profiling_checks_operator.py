from dqops.airflow_operators.run_checks.dqops_run_checks_operator import DqopsRunChecksOperator
from dqops.client.models.check_type import CheckType
from dqops.client.types import UNSET

class DqopsRunProfilingChecksOperator(DqopsRunChecksOperator):

    def __init__(self, 
        *,
        url: str = 'http://localhost:8888/',
        api_key: str = UNSET,
        connection_name: str = UNSET,
        schema_table_name: str = UNSET,
        wait_timeout: int = UNSET,
        fail_on_timeout: bool = True,
        **kwargs
    ) -> None:
        
        super().__init__(
            url=url,
            api_key=api_key,
            connection_name=connection_name,
            schema_table_name=schema_table_name,
            check_type=CheckType.PROFILING,
            wait_timeout=wait_timeout,
            fail_on_timeout=fail_on_timeout,
            **kwargs)
