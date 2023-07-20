from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

T = TypeVar("T", bound="RuleBasicModel")


@attr.s(auto_attribs=True)
class RuleBasicModel:
    """Rule basic model

    Attributes:
        rule_name (Union[Unset, str]): Rule name
        full_rule_name (Union[Unset, str]): Full rule name
        custom (Union[Unset, bool]): This rule has is a custom rule or was customized by the user.
        built_in (Union[Unset, bool]): This rule is provided with DQO as a built-in rule.
    """

    rule_name: Union[Unset, str] = UNSET
    full_rule_name: Union[Unset, str] = UNSET
    custom: Union[Unset, bool] = UNSET
    built_in: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        rule_name = self.rule_name
        full_rule_name = self.full_rule_name
        custom = self.custom
        built_in = self.built_in

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if rule_name is not UNSET:
            field_dict["rule_name"] = rule_name
        if full_rule_name is not UNSET:
            field_dict["full_rule_name"] = full_rule_name
        if custom is not UNSET:
            field_dict["custom"] = custom
        if built_in is not UNSET:
            field_dict["built_in"] = built_in

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        rule_name = d.pop("rule_name", UNSET)

        full_rule_name = d.pop("full_rule_name", UNSET)

        custom = d.pop("custom", UNSET)

        built_in = d.pop("built_in", UNSET)

        rule_basic_model = cls(
            rule_name=rule_name,
            full_rule_name=full_rule_name,
            custom=custom,
            built_in=built_in,
        )

        rule_basic_model.additional_properties = d
        return rule_basic_model

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
