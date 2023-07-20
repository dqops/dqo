from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

T = TypeVar("T", bound="CheckSpecModel")


@attr.s(auto_attribs=True)
class CheckSpecModel:
    """Check spec model

    Attributes:
        check_name (Union[Unset, str]): Check name
        sensor_name (Union[Unset, str]): Sensor name
        rule_name (Union[Unset, str]): Rule name
        help_text (Union[Unset, str]): Help text that is shown in the check editor that describes the purpose and usage
            of the check
        custom (Union[Unset, bool]): This check has is a custom check or was customized by the user.
        built_in (Union[Unset, bool]): This check is provided with DQO as a built-in check.
    """

    check_name: Union[Unset, str] = UNSET
    sensor_name: Union[Unset, str] = UNSET
    rule_name: Union[Unset, str] = UNSET
    help_text: Union[Unset, str] = UNSET
    custom: Union[Unset, bool] = UNSET
    built_in: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        check_name = self.check_name
        sensor_name = self.sensor_name
        rule_name = self.rule_name
        help_text = self.help_text
        custom = self.custom
        built_in = self.built_in

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if check_name is not UNSET:
            field_dict["check_name"] = check_name
        if sensor_name is not UNSET:
            field_dict["sensor_name"] = sensor_name
        if rule_name is not UNSET:
            field_dict["rule_name"] = rule_name
        if help_text is not UNSET:
            field_dict["help_text"] = help_text
        if custom is not UNSET:
            field_dict["custom"] = custom
        if built_in is not UNSET:
            field_dict["built_in"] = built_in

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        check_name = d.pop("check_name", UNSET)

        sensor_name = d.pop("sensor_name", UNSET)

        rule_name = d.pop("rule_name", UNSET)

        help_text = d.pop("help_text", UNSET)

        custom = d.pop("custom", UNSET)

        built_in = d.pop("built_in", UNSET)

        check_spec_model = cls(
            check_name=check_name,
            sensor_name=sensor_name,
            rule_name=rule_name,
            help_text=help_text,
            custom=custom,
            built_in=built_in,
        )

        check_spec_model.additional_properties = d
        return check_spec_model

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
