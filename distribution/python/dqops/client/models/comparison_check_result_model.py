from typing import Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ComparisonCheckResultModel")


@_attrs_define
class ComparisonCheckResultModel:
    """The table comparison check result model for the most recent data comparison run. Identifies the check name and the
    number of data groupings that passed or failed the comparison.

        Attributes:
            check_name (Union[Unset, str]): DQOps data quality check name.
            executed_at (Union[Unset, int]): The timestamp when the check was executed.
            valid_results (Union[Unset, int]): The number of data groups that were compared and the values matched within
                the accepted error margin for all check severity levels.
            warnings (Union[Unset, int]): The number of data groups that were compared and the values did not match, raising
                a warning severity level data quality issue.
            errors (Union[Unset, int]): The number of data groups that were compared and the values did not match, raising
                an error severity level data quality issue.
            fatals (Union[Unset, int]): The number of data groups that were compared and the values did not match, raising a
                fatal severity level data quality issue.
            execution_errors (Union[Unset, int]): The number of execution errors in the check or rule that prevented
                comparing the tables.
            not_matching_data_groups (Union[Unset, List[str]]): A list of not matching data grouping names.
    """

    check_name: Union[Unset, str] = UNSET
    executed_at: Union[Unset, int] = UNSET
    valid_results: Union[Unset, int] = UNSET
    warnings: Union[Unset, int] = UNSET
    errors: Union[Unset, int] = UNSET
    fatals: Union[Unset, int] = UNSET
    execution_errors: Union[Unset, int] = UNSET
    not_matching_data_groups: Union[Unset, List[str]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        check_name = self.check_name
        executed_at = self.executed_at
        valid_results = self.valid_results
        warnings = self.warnings
        errors = self.errors
        fatals = self.fatals
        execution_errors = self.execution_errors
        not_matching_data_groups: Union[Unset, List[str]] = UNSET
        if not isinstance(self.not_matching_data_groups, Unset):
            not_matching_data_groups = self.not_matching_data_groups

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if check_name is not UNSET:
            field_dict["check_name"] = check_name
        if executed_at is not UNSET:
            field_dict["executed_at"] = executed_at
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
        if not_matching_data_groups is not UNSET:
            field_dict["not_matching_data_groups"] = not_matching_data_groups

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        check_name = d.pop("check_name", UNSET)

        executed_at = d.pop("executed_at", UNSET)

        valid_results = d.pop("valid_results", UNSET)

        warnings = d.pop("warnings", UNSET)

        errors = d.pop("errors", UNSET)

        fatals = d.pop("fatals", UNSET)

        execution_errors = d.pop("execution_errors", UNSET)

        not_matching_data_groups = cast(
            List[str], d.pop("not_matching_data_groups", UNSET)
        )

        comparison_check_result_model = cls(
            check_name=check_name,
            executed_at=executed_at,
            valid_results=valid_results,
            warnings=warnings,
            errors=errors,
            fatals=fatals,
            execution_errors=execution_errors,
            not_matching_data_groups=not_matching_data_groups,
        )

        comparison_check_result_model.additional_properties = d
        return comparison_check_result_model

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
