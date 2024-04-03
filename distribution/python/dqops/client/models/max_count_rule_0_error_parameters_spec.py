from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="MaxCountRule0ErrorParametersSpec")


@_attrs_define
class MaxCountRule0ErrorParametersSpec:
    """
    Attributes:
        max_count (Union[Unset, int]): Maximum accepted value for the actual_value returned by the sensor (inclusive).
    """

    max_count: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        max_count = self.max_count

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if max_count is not UNSET:
            field_dict["max_count"] = max_count

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        max_count = d.pop("max_count", UNSET)

        max_count_rule_0_error_parameters_spec = cls(
            max_count=max_count,
        )

        max_count_rule_0_error_parameters_spec.additional_properties = d
        return max_count_rule_0_error_parameters_spec

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
