from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="MinCountRule1ParametersSpec")


@_attrs_define
class MinCountRule1ParametersSpec:
    """
    Attributes:
        min_count (Union[Unset, int]): Minimum accepted value for the actual_value returned by the sensor (inclusive).
    """

    min_count: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        min_count = self.min_count

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if min_count is not UNSET:
            field_dict["min_count"] = min_count

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        min_count = d.pop("min_count", UNSET)

        min_count_rule_1_parameters_spec = cls(
            min_count=min_count,
        )

        min_count_rule_1_parameters_spec.additional_properties = d
        return min_count_rule_1_parameters_spec

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
