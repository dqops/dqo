from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="EqualsIntegerRuleParametersSpec")


@_attrs_define
class EqualsIntegerRuleParametersSpec:
    """
    Attributes:
        expected_value (Union[Unset, int]): Expected value for the actual_value returned by the sensor. It must be an
            integer value.
    """

    expected_value: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        expected_value = self.expected_value

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if expected_value is not UNSET:
            field_dict["expected_value"] = expected_value

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        expected_value = d.pop("expected_value", UNSET)

        equals_integer_rule_parameters_spec = cls(
            expected_value=expected_value,
        )

        equals_integer_rule_parameters_spec.additional_properties = d
        return equals_integer_rule_parameters_spec

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
