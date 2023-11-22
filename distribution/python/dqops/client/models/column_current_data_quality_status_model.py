from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.rule_severity_level import RuleSeverityLevel
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_current_data_quality_status_model_checks import (
        ColumnCurrentDataQualityStatusModelChecks,
    )


T = TypeVar("T", bound="ColumnCurrentDataQualityStatusModel")


@_attrs_define
class ColumnCurrentDataQualityStatusModel:
    """The column's most recent data quality status. It is a summary of the results of the most recently executed data
    quality checks on the column. Verify the value of the current_severity to see if there are any data quality issues
    on the column.

        Attributes:
            current_severity (Union[Unset, RuleSeverityLevel]):
            highest_historical_severity (Union[Unset, RuleSeverityLevel]):
            last_check_executed_at (Union[Unset, int]): The UTC timestamp when the most recent data quality check was
                executed on the column.
            executed_checks (Union[Unset, int]): The total number of most recent checks that were executed on the column.
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
                invalid python rules. When an execution error is reported, the configuration of a data quality check on a column
                must be updated.
            checks (Union[Unset, ColumnCurrentDataQualityStatusModelChecks]): The dictionary of statuses for data quality
                checks. The keys are data quality check names, the values are the current data quality check statuses that
                describe the most current status.
    """

    current_severity: Union[Unset, RuleSeverityLevel] = UNSET
    highest_historical_severity: Union[Unset, RuleSeverityLevel] = UNSET
    last_check_executed_at: Union[Unset, int] = UNSET
    executed_checks: Union[Unset, int] = UNSET
    valid_results: Union[Unset, int] = UNSET
    warnings: Union[Unset, int] = UNSET
    errors: Union[Unset, int] = UNSET
    fatals: Union[Unset, int] = UNSET
    execution_errors: Union[Unset, int] = UNSET
    checks: Union[Unset, "ColumnCurrentDataQualityStatusModelChecks"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        current_severity: Union[Unset, str] = UNSET
        if not isinstance(self.current_severity, Unset):
            current_severity = self.current_severity.value

        highest_historical_severity: Union[Unset, str] = UNSET
        if not isinstance(self.highest_historical_severity, Unset):
            highest_historical_severity = self.highest_historical_severity.value

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

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if current_severity is not UNSET:
            field_dict["current_severity"] = current_severity
        if highest_historical_severity is not UNSET:
            field_dict["highest_historical_severity"] = highest_historical_severity
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

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_current_data_quality_status_model_checks import (
            ColumnCurrentDataQualityStatusModelChecks,
        )

        d = src_dict.copy()
        _current_severity = d.pop("current_severity", UNSET)
        current_severity: Union[Unset, RuleSeverityLevel]
        if isinstance(_current_severity, Unset):
            current_severity = UNSET
        else:
            current_severity = RuleSeverityLevel(_current_severity)

        _highest_historical_severity = d.pop("highest_historical_severity", UNSET)
        highest_historical_severity: Union[Unset, RuleSeverityLevel]
        if isinstance(_highest_historical_severity, Unset):
            highest_historical_severity = UNSET
        else:
            highest_historical_severity = RuleSeverityLevel(
                _highest_historical_severity
            )

        last_check_executed_at = d.pop("last_check_executed_at", UNSET)

        executed_checks = d.pop("executed_checks", UNSET)

        valid_results = d.pop("valid_results", UNSET)

        warnings = d.pop("warnings", UNSET)

        errors = d.pop("errors", UNSET)

        fatals = d.pop("fatals", UNSET)

        execution_errors = d.pop("execution_errors", UNSET)

        _checks = d.pop("checks", UNSET)
        checks: Union[Unset, ColumnCurrentDataQualityStatusModelChecks]
        if isinstance(_checks, Unset):
            checks = UNSET
        else:
            checks = ColumnCurrentDataQualityStatusModelChecks.from_dict(_checks)

        column_current_data_quality_status_model = cls(
            current_severity=current_severity,
            highest_historical_severity=highest_historical_severity,
            last_check_executed_at=last_check_executed_at,
            executed_checks=executed_checks,
            valid_results=valid_results,
            warnings=warnings,
            errors=errors,
            fatals=fatals,
            execution_errors=execution_errors,
            checks=checks,
        )

        column_current_data_quality_status_model.additional_properties = d
        return column_current_data_quality_status_model

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
