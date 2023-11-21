import json
import logging
from typing import Any, Dict, List, Union

from airflow.models.baseoperator import BaseOperator
from httpx import ReadTimeout

from dqops.airflow.common.exceptions.dqops_data_quality_issue_detected_exception import (
    DqopsDataQualityIssueDetectedException,
)
from dqops.airflow.common.exceptions.dqops_job_failed_exception import (
    DqopsJobFailedException,
)
from dqops.airflow.common.tools.client_creator import create_client
from dqops.airflow.common.tools.rule_severity_level_utility import (
    get_severity_value_from_rule_severity,
)
from dqops.airflow.common.tools.server_response_verifier import (
    verify_server_response_correctness,
)
from dqops.airflow.common.tools.timeout.dqo_timeout import handle_dqo_timeout
from dqops.airflow.common.tools.timeout.python_client_timeout import (
    handle_python_timeout,
)
from dqops.airflow.common.tools.url_resolver import extract_base_url
from dqops.client import Client
from dqops.client.api.jobs.run_checks import sync_detailed
from dqops.client.models.check_search_filters import CheckSearchFilters
from dqops.client.models.check_target import CheckTarget
from dqops.client.models.check_time_scale import CheckTimeScale
from dqops.client.models.check_type import CheckType
from dqops.client.models.dqo_job_status import DqoJobStatus
from dqops.client.models.rule_severity_level import RuleSeverityLevel
from dqops.client.models.run_checks_parameters import RunChecksParameters
from dqops.client.models.run_checks_queue_job_result import RunChecksQueueJobResult
from dqops.client.types import UNSET, Response, Unset


class DqopsRunChecksOperator(BaseOperator):
    """
    Airflow run checks operator for execution of the DQOps sensors.

    """

    def __init__(
        self,
        *,
        base_url: str = "http://localhost:8888/",
        job_business_key: Union[Unset, None, str] = UNSET,
        api_key: str = UNSET,
        connection: Union[Unset, str] = UNSET,
        full_table_name: Union[Unset, str] = UNSET,
        enabled: Union[Unset, bool] = UNSET,
        tags: Union[Unset, List[str]] = UNSET,
        labels: Union[Unset, List[str]] = UNSET,
        column: Union[Unset, str] = UNSET,
        column_data_type: Union[Unset, str] = UNSET,
        column_nullable: Union[Unset, bool] = UNSET,
        check_target: Union[Unset, CheckTarget] = UNSET,
        check_type: Union[Unset, CheckType] = UNSET,
        time_scale: Union[Unset, CheckTimeScale] = UNSET,
        check_category: Union[Unset, str] = UNSET,
        table_comparison_name: Union[Unset, str] = UNSET,
        check_name: Union[Unset, str] = UNSET,
        sensor_name: Union[Unset, str] = UNSET,
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
            The connection name to the data source in DQOps. When not set, all connection names will be used. Supports search patterns in the format:
            'source\*', '\*_prod', 'prefix\*suffix'.
        full_table_name : str [optional, default=UNSET]
            The name of the table with the schema. The schema and table name. It is provided as *<schema_name>.<table_name>*,
            for example *public.fact_sales*.
            The schema and table name accept patterns both in the schema name and table name parts.
            Sample patterns are: 'schema_name.tab_prefix_\*', 'schema_name.*', '*.*', 'schema_name.\*_customer', 'schema_name.tab_\*_suffix'.
        enabled : Union[Unset, bool]
            A boolean flag to target enabled tables, columns or checks. When the value of this
            field is not set, the default value of this field is *true*, targeting only tables, columns and checks that are
            not implicitly disabled.
        tags : Union[Unset, List[str]]
            An array of tags assigned to the table. All tags must be present on a table to
            match. The tags can use patterns:  'prefix\*', '\*suffix', 'prefix\*suffix'. The tags are assigned to the table
            on the data grouping screen when any of the data grouping hierarchy level is assigned a static value, which is a
            tag.
        labels : Union[Unset, List[str]]
            An array of labels assigned to the table. All labels must be present on a
            table to match. The labels can use patterns:  'prefix\*', '\*suffix', 'prefix\*suffix'. The labels are assigned
            on the labels screen and stored in the *labels* node in the *.dqotable.yaml* file.
        column : Union[Unset, str]
            The column name. This field accepts search patterns in the format: 'fk_\*', '\*_id',
            'prefix\*suffix'.
        column_data_type : Union[Unset, str]
            The column data type that was imported from the data source and is stored
            in the [columns -> column_name -> type_snapshot ->
            column_type](../../reference/yaml/TableYaml/#columntypesnapshotspec) field in the *.dqotable.yaml* file.
        column_nullable : Union[Unset, bool]
            Optional filter to find only nullable (when the value is *true*) or not
            nullable (when the value is *false*) columns, based on the value of the [columns -> column_name -> type_snapshot
            -> nullable](../../reference/yaml/TableYaml/#columntypesnapshotspec) field in the *.dqotable.yaml* file.
        check_target : Union[Unset, CheckTarget]
        check_type : CheckType [optional, default=UNSET]
            Specifies type of checks to be executed. When not set, all types of checks will be executed. <br/> The enum is stored in _dqops.client.models.check_type_ module.
        time_scale : Union[Unset, CheckTimeScale]
        check_category : Union[Unset, str]
            The target check category, for example: *nulls*, *volume*, *anomaly*.
        table_comparison_name : Union[Unset, str]
            The name of a configured table comparison. When the table comparison
            is provided, DQOps will only perform table comparison checks that compare data between tables.
        check_name : Union[Unset, str]
            The target check name to run only this named check. Uses the short check name
            which is the name of the deepest folder in the *checks* folder. This field supports search patterns such as:
            'profiling_\*', '\*_count', 'profiling_\*_percent'.
        sensor_name : Union[Unset, str]
            The target sensor name to run only data quality checks that are using this
            sensor. Uses the full sensor name which is the full folder path within the *sensors* folder. This field supports
            search patterns such as: 'table/volume/row_\*', '\*_count', 'table/volume/prefix_\*_suffix'.

        wait_timeout : int [optional, default=UNSET]
            Time in seconds for execution that client will wait. It prevents from hanging the task for an action that is never completed. If not set, the timeout is read form the client defaults, which value is 120 seconds.
        fail_on_timeout : bool [optional, default=True]
            Timeout is leading the task status to Failed by default. It can be omitted marking the task as Success by setting the flag to True.
        fail_at_severity: RuleSeverityLevel [optional, default=RuleSeverityLevel.FATAL]
            The threshold level of rule severity, causing that an airflow task finishes with failed status.
        """

        super().__init__(**kwargs)
        self.base_url: str = extract_base_url(base_url)
        self.job_business_key: Union[Unset, None, str] = job_business_key
        self.api_key: str = api_key

        self.connection: Union[Unset, str] = connection
        self.full_table_name: Union[Unset, str] = full_table_name
        self.enabled: Union[Unset, bool] = enabled
        self.tags: Union[Unset, List[str]] = tags
        self.labels: Union[Unset, List[str]] = labels
        self.column: Union[Unset, str] = column
        self.column_data_type: Union[Unset, str] = column_data_type
        self.column_nullable: Union[Unset, bool] = column_nullable
        self.check_target: Union[Unset, CheckTarget] = check_target
        self.check_type: Union[Unset, CheckType] = check_type
        self.time_scale: Union[Unset, CheckTimeScale] = time_scale
        self.check_category: Union[Unset, str] = check_category
        self.table_comparison_name: Union[Unset, str] = table_comparison_name
        self.check_name: Union[Unset, str] = check_name
        self.sensor_name: Union[Unset, str] = sensor_name

        self.wait_timeout: int = wait_timeout
        self.fail_on_timeout: bool = fail_on_timeout
        self.fail_at_severity: RuleSeverityLevel = fail_at_severity

    def execute(self, context):
        filters: CheckSearchFilters = CheckSearchFilters(
            connection=self.connection,
            full_table_name=self.full_table_name,
            enabled=self.enabled,
            tags=self.tags,
            labels=self.labels,
            column=self.column,
            column_data_type=self.column_data_type,
            column_nullable=self.column_nullable,
            check_target=self.check_target,
            check_type=self.check_type,
            time_scale=self.time_scale,
            check_category=self.check_category,
            table_comparison_name=self.table_comparison_name,
            check_name=self.check_name,
            sensor_name=self.sensor_name,
        )
        params: RunChecksParameters = RunChecksParameters(check_search_filters=filters)

        client: Client = create_client(
            base_url=self.base_url, wait_timeout=self.wait_timeout
        )

        try:
            response: Response[RunChecksQueueJobResult] = sync_detailed(
                client=client,
                json_body=params,
                job_business_key=self.job_business_key,
                wait=True,
                wait_timeout=self.wait_timeout,
            )
        except ReadTimeout as exception:
            handle_python_timeout(exception, self.fail_on_timeout)
            return None

        verify_server_response_correctness(response)

        job_result: RunChecksQueueJobResult = RunChecksQueueJobResult.from_dict(
            json.loads(response.content.decode("utf-8"))
        )
        logging.info(job_result.to_dict())

        if job_result.status == DqoJobStatus.FAILED:
            raise DqopsJobFailedException(context["ti"], job_result.to_dict())

        # dqo times out with RunChecksQueueJobResult object details
        if job_result.status == DqoJobStatus.RUNNING:
            handle_dqo_timeout(self.fail_on_timeout)

        if (
            job_result.result.highest_severity is not None
            and get_severity_value_from_rule_severity(
                job_result.result.highest_severity
            )
            >= get_severity_value_from_rule_severity(self.fail_at_severity)
            and job_result.status != DqoJobStatus.CANCELLED
        ):
            raise DqopsDataQualityIssueDetectedException(
                context["ti"], job_result.to_dict()
            )

        return job_result.to_dict()
