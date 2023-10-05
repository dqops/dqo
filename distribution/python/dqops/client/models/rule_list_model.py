from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

T = TypeVar("T", bound="RuleListModel")


@attr.s(auto_attribs=True)
class RuleListModel:
    """Rule list model

    Attributes:
        rule_name (Union[Unset, str]): Rule name without the folder.
        full_rule_name (Union[Unset, str]): Full rule name, including the folder within the "rules" rule folder.
        custom (Union[Unset, bool]): This rule has is a custom rule or was customized by the user. This is a read-only
            value.
        built_in (Union[Unset, bool]): This rule is provided with DQO as a built-in rule. This is a read-only value.
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
    """

    rule_name: Union[Unset, str] = UNSET
    full_rule_name: Union[Unset, str] = UNSET
    custom: Union[Unset, bool] = UNSET
    built_in: Union[Unset, bool] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        rule_name = self.rule_name
        full_rule_name = self.full_rule_name
        custom = self.custom
        built_in = self.built_in
        can_edit = self.can_edit

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
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        rule_name = d.pop("rule_name", UNSET)

        full_rule_name = d.pop("full_rule_name", UNSET)

        custom = d.pop("custom", UNSET)

        built_in = d.pop("built_in", UNSET)

        can_edit = d.pop("can_edit", UNSET)

        rule_list_model = cls(
            rule_name=rule_name,
            full_rule_name=full_rule_name,
            custom=custom,
            built_in=built_in,
            can_edit=can_edit,
        )

        rule_list_model.additional_properties = d
        return rule_list_model

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
