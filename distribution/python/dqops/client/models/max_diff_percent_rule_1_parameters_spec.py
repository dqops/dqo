from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="MaxDiffPercentRule1ParametersSpec")


@_attrs_define
class MaxDiffPercentRule1ParametersSpec:
    """
    Attributes:
        max_diff_percent (Union[Unset, float]): Maximum accepted value for the percentage of difference between
            expected_value and actual_value returned by the sensor (inclusive).
    """

    max_diff_percent: Union[Unset, float] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        max_diff_percent = self.max_diff_percent

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if max_diff_percent is not UNSET:
            field_dict["max_diff_percent"] = max_diff_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        max_diff_percent = d.pop("max_diff_percent", UNSET)

        max_diff_percent_rule_1_parameters_spec = cls(
            max_diff_percent=max_diff_percent,
        )

        max_diff_percent_rule_1_parameters_spec.additional_properties = d
        return max_diff_percent_rule_1_parameters_spec

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
