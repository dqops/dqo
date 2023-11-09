from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.check_result_status import CheckResultStatus
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_current_data_quality_status_model_checks import (
        TableCurrentDataQualityStatusModelChecks,
    )
    from ..models.table_current_data_quality_status_model_columns import (
        TableCurrentDataQualityStatusModelColumns,
    )


T = TypeVar("T", bound="TableCurrentDataQualityStatusModel")


@_attrs_define
class TableCurrentDataQualityStatusModel:
    """The table's most recent data quality status. It is a summary of the results of the most recently executed data
    quality checks on the table. Verify the value of the highest_severity_level to see if there are any data quality
    issues on the table. The values of severity levels are: 0 - all data quality checks passed, 1 - a warning was
    detected, 2 - an error was detected, 3 - a fatal data quality issue was detected.

        Attributes:
            connection_name (Union[Unset, str]): The connection name in DQOps.
            schema_name (Union[Unset, str]): The schema name.
            table_name (Union[Unset, str]): The table name.
            highest_severity_level (Union[Unset, CheckResultStatus]):
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
                to access issues to the data source, invalid mapping in DQOps, invalid queries in data quality sensors or
                invalid python rules. When an execution error is reported, the configuration of a data quality check on a table
                must be updated.
            checks (Union[Unset, TableCurrentDataQualityStatusModelChecks]): The dictionary of statuses for data quality
                checks. The keys are data quality check names, the values are the current data quality check statuses that
                describe the most current status.
            columns (Union[Unset, TableCurrentDataQualityStatusModelColumns]): Dictionary of data statues for all columns
                that have any known data quality results. The keys in the dictionary are the column names.
    """

    connection_name: Union[Unset, str] = UNSET
    schema_name: Union[Unset, str] = UNSET
    table_name: Union[Unset, str] = UNSET
    highest_severity_level: Union[Unset, CheckResultStatus] = UNSET
    last_check_executed_at: Union[Unset, int] = UNSET
    executed_checks: Union[Unset, int] = UNSET
    valid_results: Union[Unset, int] = UNSET
    warnings: Union[Unset, int] = UNSET
    errors: Union[Unset, int] = UNSET
    fatals: Union[Unset, int] = UNSET
    execution_errors: Union[Unset, int] = UNSET
    checks: Union[Unset, "TableCurrentDataQualityStatusModelChecks"] = UNSET
    columns: Union[Unset, "TableCurrentDataQualityStatusModelColumns"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        schema_name = self.schema_name
        table_name = self.table_name
        highest_severity_level: Union[Unset, str] = UNSET
        if not isinstance(self.highest_severity_level, Unset):
            highest_severity_level = self.highest_severity_level.value

        last_check_executed_at = self.last_check_executed_at
        executed_checks = self.executed_checks
        valid_results = self.valid_results
        warnings = self.warnings
        errors = self.errors
        fatals = self.fatals
        execution_errors = self.execution_errors
        checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.checks, Unset):
            checks = self.checks.to_dict()

        columns: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.columns, Unset):
            columns = self.columns.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if schema_name is not UNSET:
            field_dict["schema_name"] = schema_name
        if table_name is not UNSET:
            field_dict["table_name"] = table_name
        if highest_severity_level is not UNSET:
            field_dict["highest_severity_level"] = highest_severity_level
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
        if checks is not UNSET:
            field_dict["checks"] = checks
        if columns is not UNSET:
            field_dict["columns"] = columns

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_current_data_quality_status_model_checks import (
            TableCurrentDataQualityStatusModelChecks,
        )
        from ..models.table_current_data_quality_status_model_columns import (
            TableCurrentDataQualityStatusModelColumns,
        )

        d = src_dict.copy()
        connection_name = d.pop("connection_name", UNSET)

        schema_name = d.pop("schema_name", UNSET)

        table_name = d.pop("table_name", UNSET)

        _highest_severity_level = d.pop("highest_severity_level", UNSET)
        highest_severity_level: Union[Unset, CheckResultStatus]
        if isinstance(_highest_severity_level, Unset):
            highest_severity_level = UNSET
        else:
            highest_severity_level = CheckResultStatus(_highest_severity_level)

        last_check_executed_at = d.pop("last_check_executed_at", UNSET)

        executed_checks = d.pop("executed_checks", UNSET)

        valid_results = d.pop("valid_results", UNSET)

        warnings = d.pop("warnings", UNSET)

        errors = d.pop("errors", UNSET)

        fatals = d.pop("fatals", UNSET)

        execution_errors = d.pop("execution_errors", UNSET)

        _checks = d.pop("checks", UNSET)
        checks: Union[Unset, TableCurrentDataQualityStatusModelChecks]
        if isinstance(_checks, Unset):
            checks = UNSET
        else:
            checks = TableCurrentDataQualityStatusModelChecks.from_dict(_checks)

        _columns = d.pop("columns", UNSET)
        columns: Union[Unset, TableCurrentDataQualityStatusModelColumns]
        if isinstance(_columns, Unset):
            columns = UNSET
        else:
            columns = TableCurrentDataQualityStatusModelColumns.from_dict(_columns)

        table_current_data_quality_status_model = cls(
            connection_name=connection_name,
            schema_name=schema_name,
            table_name=table_name,
            highest_severity_level=highest_severity_level,
            last_check_executed_at=last_check_executed_at,
            executed_checks=executed_checks,
            valid_results=valid_results,
            warnings=warnings,
            errors=errors,
            fatals=fatals,
            execution_errors=execution_errors,
            checks=checks,
            columns=columns,
        )

        table_current_data_quality_status_model.additional_properties = d
        return table_current_data_quality_status_model

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
