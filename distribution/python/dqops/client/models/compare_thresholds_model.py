from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="CompareThresholdsModel")


@_attrs_define
class CompareThresholdsModel:
    """Model with the compare threshold levels for raising data quality issues at different severity levels when the
    difference between the compared (tested) table and the reference table (the source of truth) exceed given thresholds
    as a percentage of difference between the actual value and the expected value from the reference table.

        Attributes:
            warning_difference_percent (Union[Unset, float]): The percentage difference between the measure value on the
                compared table and the reference table that raises a warning severity data quality issue when the difference is
                bigger.
            error_difference_percent (Union[Unset, float]): The percentage difference between the measure value on the
                compared table and the reference table that raises an error severity data quality issue when the difference is
                bigger.
            fatal_difference_percent (Union[Unset, float]): The percentage difference between the measure value on the
                compared table and the reference table that raises a fatal severity data quality issue when the difference is
                bigger.
    """

    warning_difference_percent: Union[Unset, float] = UNSET
    error_difference_percent: Union[Unset, float] = UNSET
    fatal_difference_percent: Union[Unset, float] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        warning_difference_percent = self.warning_difference_percent
        error_difference_percent = self.error_difference_percent
        fatal_difference_percent = self.fatal_difference_percent

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if warning_difference_percent is not UNSET:
            field_dict["warning_difference_percent"] = warning_difference_percent
        if error_difference_percent is not UNSET:
            field_dict["error_difference_percent"] = error_difference_percent
        if fatal_difference_percent is not UNSET:
            field_dict["fatal_difference_percent"] = fatal_difference_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        warning_difference_percent = d.pop("warning_difference_percent", UNSET)

        error_difference_percent = d.pop("error_difference_percent", UNSET)

        fatal_difference_percent = d.pop("fatal_difference_percent", UNSET)

        compare_thresholds_model = cls(
            warning_difference_percent=warning_difference_percent,
            error_difference_percent=error_difference_percent,
            fatal_difference_percent=fatal_difference_percent,
        )

        compare_thresholds_model.additional_properties = d
        return compare_thresholds_model

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
