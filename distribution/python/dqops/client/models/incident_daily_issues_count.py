from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="IncidentDailyIssuesCount")


@_attrs_define
class IncidentDailyIssuesCount:
    """
    Attributes:
        warnings (Union[Unset, int]): The number of failed data quality checks that generated a warning severity data
            quality issue.
        errors (Union[Unset, int]): The number of failed data quality checks that generated an error severity data
            quality issue.
        fatals (Union[Unset, int]): The number of failed data quality checks that generated a fatal severity data
            quality issue.
        total_count (Union[Unset, int]): The total count of failed data quality checks on this day.
    """

    warnings: Union[Unset, int] = UNSET
    errors: Union[Unset, int] = UNSET
    fatals: Union[Unset, int] = UNSET
    total_count: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        warnings = self.warnings
        errors = self.errors
        fatals = self.fatals
        total_count = self.total_count

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if warnings is not UNSET:
            field_dict["warnings"] = warnings
        if errors is not UNSET:
            field_dict["errors"] = errors
        if fatals is not UNSET:
            field_dict["fatals"] = fatals
        if total_count is not UNSET:
            field_dict["totalCount"] = total_count

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        warnings = d.pop("warnings", UNSET)

        errors = d.pop("errors", UNSET)

        fatals = d.pop("fatals", UNSET)

        total_count = d.pop("totalCount", UNSET)

        incident_daily_issues_count = cls(
            warnings=warnings,
            errors=errors,
            fatals=fatals,
            total_count=total_count,
        )

        incident_daily_issues_count.additional_properties = d
        return incident_daily_issues_count

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
