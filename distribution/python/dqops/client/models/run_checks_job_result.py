from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..models.run_checks_job_result_highest_severity import (
    RunChecksJobResultHighestSeverity,
)
from ..types import UNSET, Unset

T = TypeVar("T", bound="RunChecksJobResult")


@attr.s(auto_attribs=True)
class RunChecksJobResult:
    """Returns the result (highest data quality check severity and the finished checks count) for the checks that were
    recently executed.

        Attributes:
            highest_severity (Union[Unset, RunChecksJobResultHighestSeverity]): The highest check severity for the data
                quality checks executed in this batch.
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

    highest_severity: Union[Unset, RunChecksJobResultHighestSeverity] = UNSET
    executed_checks: Union[Unset, int] = UNSET
    valid_results: Union[Unset, int] = UNSET
    warnings: Union[Unset, int] = UNSET
    errors: Union[Unset, int] = UNSET
    fatals: Union[Unset, int] = UNSET
    execution_errors: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

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
            field_dict["highestSeverity"] = highest_severity
        if executed_checks is not UNSET:
            field_dict["executedChecks"] = executed_checks
        if valid_results is not UNSET:
            field_dict["validResults"] = valid_results
        if warnings is not UNSET:
            field_dict["warnings"] = warnings
        if errors is not UNSET:
            field_dict["errors"] = errors
        if fatals is not UNSET:
            field_dict["fatals"] = fatals
        if execution_errors is not UNSET:
            field_dict["executionErrors"] = execution_errors

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _highest_severity = d.pop("highestSeverity", UNSET)
        highest_severity: Union[Unset, RunChecksJobResultHighestSeverity]
        if isinstance(_highest_severity, Unset):
            highest_severity = UNSET
        else:
            highest_severity = RunChecksJobResultHighestSeverity(_highest_severity)

        executed_checks = d.pop("executedChecks", UNSET)

        valid_results = d.pop("validResults", UNSET)

        warnings = d.pop("warnings", UNSET)

        errors = d.pop("errors", UNSET)

        fatals = d.pop("fatals", UNSET)

        execution_errors = d.pop("executionErrors", UNSET)

        run_checks_job_result = cls(
            highest_severity=highest_severity,
            executed_checks=executed_checks,
            valid_results=valid_results,
            warnings=warnings,
            errors=errors,
            fatals=fatals,
            execution_errors=execution_errors,
        )

        run_checks_job_result.additional_properties = d
        return run_checks_job_result

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
