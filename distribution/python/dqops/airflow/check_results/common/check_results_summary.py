from typing import  Any, Dict, Union

from dqops.client.models.check_results_list_model import CheckResultsListModel
import json
from dqops.client.types import UNSET, Unset

class CheckResultsSummary:

    connection_name: Union[Unset, str] = UNSET
    schema_name: Union[Unset, str] = UNSET
    table_name: Union[Unset, str] = UNSET
    highest_severity_issue: Union[Unset, int] = UNSET
    last_check_executed_at: Union[Unset, int] = UNSET
    executed_checks: Union[Unset, int] = UNSET
    valid_results: Union[Unset, int] = UNSET
    warnings: Union[Unset, int] = UNSET
    errors: Union[Unset, int] = UNSET
    fatals: Union[Unset, int] = UNSET
    execution_errors: Union[Unset, int] = UNSET

    def __init__(self,
                 connection_name: str,
                 schema_name: str,
                 table_name: str):

        self.connection_name=connection_name
        self.schema_name=schema_name
        self.table_name=table_name
        self.highest_severity_issue=0
        self.executed_checks=0
        self.valid_results=0
        self.warnings=0
        self.errors=0
        self.fatals=0
        self.execution_errors=0

    def calculate_status(self, json_list):

        for item in json.loads(json_list):

            check_result: CheckResultsListModel = CheckResultsListModel.from_dict(item)

            for entry in check_result.check_result_entries:
                self.executed_checks += 1
                if self.last_check_executed_at is UNSET or self.last_check_executed_at < entry.executed_at:
                    self.last_check_executed_at = entry.executed_at
                if entry.severity == 0: self.valid_results += 1
                if entry.severity == 1: self.warnings += 1
                if entry.severity == 2: self.errors += 1
                if entry.severity == 3: self.fatals += 1
                if entry.severity == 4: self.execution_errors += 1

                if entry.severity > self.highest_severity_issue:
                    self.highest_severity_issue = entry.severity

    def to_dict(self) -> Dict[str, Any]:

        field_dict: Dict[str, Any] = {}
        if self.connection_name is not UNSET:
            field_dict["connectionName"] = self.connection_name
        if self.schema_name is not UNSET:
            field_dict["schemaName"] = self.schema_name
        if self.table_name is not UNSET:
            field_dict["tableName"] = self.table_name
        if self.highest_severity_issue is not UNSET:
            field_dict["highestSeverityIssue"] = self.highest_severity_issue
        if self.last_check_executed_at is not UNSET:
            field_dict["lastCheckExecutedAt"] = self.last_check_executed_at
        if self.executed_checks is not UNSET:
            field_dict["executedChecks"] = self.executed_checks
        if self.valid_results is not UNSET:
            field_dict["validResults"] = self.valid_results
        if self.warnings is not UNSET:
            field_dict["warnings"] = self.warnings
        if self.errors is not UNSET:
            field_dict["errors"] = self.errors
        if self.fatals is not UNSET:
            field_dict["fatals"] = self.fatals
        if self.execution_errors is not UNSET:
            field_dict["execution_errors"] = self.execution_errors

        return field_dict
    