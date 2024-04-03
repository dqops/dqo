from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.rule_severity_level import RuleSeverityLevel
from ..types import UNSET, Unset

T = TypeVar("T", bound="RunChecksResult")


@_attrs_define
class RunChecksResult:
    """Returns the result (highest data quality check severity and the finished checks count) for the checks that were
    recently executed.

        Attributes:
            highest_severity (Union[Unset, RuleSeverityLevel]):
            executed_checks (Union[Unset, int]): The total count of all executed checks.
            valid_results (Union[Unset, int]): The total count of all checks that finished successfully (with no data
                quality issues).
            warnings (Union[Unset, int]): The total count of all invalid data quality checks that finished raising a
                warning.
            errors (Union[Unset, int]): The total count of all invalid data quality checks that finished raising an error.
            fatals (Union[Unset, int]): The total count of all invalid data quality checks that finished raising a fatal
                error.
            execution_errors (Union[Unset, int]): The total number of checks that failed to execute due to some execution
                errors.
    """

    highest_severity: Union[Unset, RuleSeverityLevel] = UNSET
    executed_checks: Union[Unset, int] = UNSET
    valid_results: Union[Unset, int] = UNSET
    warnings: Union[Unset, int] = UNSET
    errors: Union[Unset, int] = UNSET
    fatals: Union[Unset, int] = UNSET
    execution_errors: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        highest_severity: Union[Unset, str] = UNSET
        if not isinstance(self.highest_severity, Unset):
            highest_severity = self.highest_severity.value

        executed_checks = self.executed_checks
        valid_results = self.valid_results
        warnings = self.warnings
        errors = self.errors
        fatals = self.fatals
        execution_errors = self.execution_errors

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if highest_severity is not UNSET:
            field_dict["highest_severity"] = highest_severity
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

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _highest_severity = d.pop("highest_severity", UNSET)
        highest_severity: Union[Unset, RuleSeverityLevel]
        if isinstance(_highest_severity, Unset):
            highest_severity = UNSET
        else:
            highest_severity = RuleSeverityLevel(_highest_severity)

        executed_checks = d.pop("executed_checks", UNSET)

        valid_results = d.pop("valid_results", UNSET)

        warnings = d.pop("warnings", UNSET)

        errors = d.pop("errors", UNSET)

        fatals = d.pop("fatals", UNSET)

        execution_errors = d.pop("execution_errors", UNSET)

        run_checks_result = cls(
            highest_severity=highest_severity,
            executed_checks=executed_checks,
            valid_results=valid_results,
            warnings=warnings,
            errors=errors,
            fatals=fatals,
            execution_errors=execution_errors,
        )

        run_checks_result.additional_properties = d
        return run_checks_result

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
