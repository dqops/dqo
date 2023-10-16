from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="MinValueRuleParametersSpec")


@_attrs_define
class MinValueRuleParametersSpec:
    """
    Attributes:
        min_value (Union[Unset, float]): Minimum accepted value for the actual_value returned by the sensor (inclusive).
    """

    min_value: Union[Unset, float] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        min_value = self.min_value

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if min_value is not UNSET:
            field_dict["min_value"] = min_value

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        min_value = d.pop("min_value", UNSET)

        min_value_rule_parameters_spec = cls(
            min_value=min_value,
        )

        min_value_rule_parameters_spec.additional_properties = d
        return min_value_rule_parameters_spec

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
