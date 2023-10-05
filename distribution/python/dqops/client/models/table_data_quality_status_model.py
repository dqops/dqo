from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_data_quality_status_model_failed_checks_statuses import (
        TableDataQualityStatusModelFailedChecksStatuses,
    )


T = TypeVar("T", bound="TableDataQualityStatusModel")


@attr.s(auto_attribs=True)
class TableDataQualityStatusModel:
    """The table's most recent data quality status. It is a summary of the results of the most recently executed data
    quality checks on the table. Verify the value of the highest_severity_issue (0 - all data quality checks passed, 1 -
    a warning was detected, 2 - an error was detected, 3 - a fatal data quality issue was detected.

        Attributes:
            connection_name (Union[Unset, str]): The connection name in DQO.
            schema_name (Union[Unset, str]): The schema name.
            table_name (Union[Unset, str]): The table name.
            highest_severity_issue (Union[Unset, int]): The severity of the highest identified data quality issue (1 =
                warning, 2 = error, 3 = fatal) or 0 when no data quality issues were identified. This field will be null if no
                data quality checks were executed on the table.
            last_check_executed_at (Union[Unset, int]): The UTC timestamp when the most recent data quality check was
                executed on the table.
            executed_checks (Union[Unset, int]): The total number of most recent checks that were executed on the table.
                Table comparison checks that are comparing groups of data are counted as the number of compared data groups.
            valid_results (Union[Unset, int]): The number of most recent valid data quality checks that passed without
                raising any issues.
            warnings (Union[Unset, int]): The number of most recent data quality checks that failed by raising a warning
                severity data quality issue.
            errors (Union[Unset, int]): The number of most recent data quality checks that failed by raising an error
                severity data quality issue.
            fatals (Union[Unset, int]): The number of most recent data quality checks that failed by raising a fatal
                severity data quality issue.
            execution_errors (Union[Unset, int]): The number of data quality check execution errors that were reported due
                to access issues to the data source, invalid mapping in DQO, invalid queries in data quality sensors or invalid
                python rules. When an execution error is reported, the configuration of a data quality check on a table must be
                updated.
            failed_checks_statuses (Union[Unset, TableDataQualityStatusModelFailedChecksStatuses]): The paths to all failed
                data quality checks (keys) and severity of the highest data quality issue that was detected. Table-level checks
                are identified by the check name. Column-level checks are identified as a check_name[column_name].
    """

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
    failed_checks_statuses: Union[
        Unset, "TableDataQualityStatusModelFailedChecksStatuses"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        schema_name = self.schema_name
        table_name = self.table_name
        highest_severity_issue = self.highest_severity_issue
        last_check_executed_at = self.last_check_executed_at
        executed_checks = self.executed_checks
        valid_results = self.valid_results
        warnings = self.warnings
        errors = self.errors
        fatals = self.fatals
        execution_errors = self.execution_errors
        failed_checks_statuses: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.failed_checks_statuses, Unset):
            failed_checks_statuses = self.failed_checks_statuses.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if schema_name is not UNSET:
            field_dict["schema_name"] = schema_name
        if table_name is not UNSET:
            field_dict["table_name"] = table_name
        if highest_severity_issue is not UNSET:
            field_dict["highest_severity_issue"] = highest_severity_issue
        if last_check_executed_at is not UNSET:
            field_dict["last_check_executed_at"] = last_check_executed_at
        if executed_checks is not UNSET:
            field_dict["executed_checks"] = executed_checks
        if valid_results is not UNSET:
            field_dict["valid_results"] = valid_results
        if warnings is not UNSET:
            field_dict["warnings"] = warnings
        if errors is not UNSET:
            field_dict["errors"] = errors
        if fatals is not UNSET:
            field_dict["fatals"] = fatals
        if execution_errors is not UNSET:
            field_dict["execution_errors"] = execution_errors
        if failed_checks_statuses is not UNSET:
            field_dict["failed_checks_statuses"] = failed_checks_statuses

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_data_quality_status_model_failed_checks_statuses import (
            TableDataQualityStatusModelFailedChecksStatuses,
        )

        d = src_dict.copy()
        connection_name = d.pop("connection_name", UNSET)

        schema_name = d.pop("schema_name", UNSET)

        table_name = d.pop("table_name", UNSET)

        highest_severity_issue = d.pop("highest_severity_issue", UNSET)

        last_check_executed_at = d.pop("last_check_executed_at", UNSET)

        executed_checks = d.pop("executed_checks", UNSET)

        valid_results = d.pop("valid_results", UNSET)

        warnings = d.pop("warnings", UNSET)

        errors = d.pop("errors", UNSET)

        fatals = d.pop("fatals", UNSET)

        execution_errors = d.pop("execution_errors", UNSET)

        _failed_checks_statuses = d.pop("failed_checks_statuses", UNSET)
        failed_checks_statuses: Union[
            Unset, TableDataQualityStatusModelFailedChecksStatuses
        ]
        if isinstance(_failed_checks_statuses, Unset):
            failed_checks_statuses = UNSET
        else:
            failed_checks_statuses = (
                TableDataQualityStatusModelFailedChecksStatuses.from_dict(
                    _failed_checks_statuses
                )
            )

        table_data_quality_status_model = cls(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            highest_severity_issue=highest_severity_issue,
            last_check_executed_at=last_check_executed_at,
            executed_checks=executed_checks,
            valid_results=valid_results,
            warnings=warnings,
            errors=errors,
            fatals=fatals,
            execution_errors=execution_errors,
            failed_checks_statuses=failed_checks_statuses,
        )

        table_data_quality_status_model.additional_properties = d
        return table_data_quality_status_model

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> Any:
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: Any) -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
