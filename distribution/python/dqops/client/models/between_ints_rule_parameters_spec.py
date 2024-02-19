from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="BetweenIntsRuleParametersSpec")


@_attrs_define
class BetweenIntsRuleParametersSpec:
    """
    Attributes:
        from_ (Union[Unset, int]): Minimum accepted value for the actual_value returned by the sensor (inclusive).
        to (Union[Unset, int]): Maximum accepted value for the actual_value returned by the sensor (inclusive).
    """

    from_: Union[Unset, int] = UNSET
    to: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        from_ = self.from_
        to = self.to

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if from_ is not UNSET:
            field_dict["from"] = from_
        if to is not UNSET:
            field_dict["to"] = to

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        from_ = d.pop("from", UNSET)

        to = d.pop("to", UNSET)

        between_ints_rule_parameters_spec = cls(
            from_=from_,
            to=to,
        )

        between_ints_rule_parameters_spec.additional_properties = d
        return between_ints_rule_parameters_spec

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
