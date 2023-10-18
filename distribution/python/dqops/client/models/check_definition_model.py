from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="CheckDefinitionModel")


@_attrs_define
class CheckDefinitionModel:
    """Data quality check definition model

    Attributes:
        check_name (Union[Unset, str]): Check name
        sensor_name (Union[Unset, str]): Sensor name
        rule_name (Union[Unset, str]): Rule name
        help_text (Union[Unset, str]): Help text that is shown in the check editor that describes the purpose and usage
            of the check
        custom (Union[Unset, bool]): This check has is a custom check or was customized by the user.
        built_in (Union[Unset, bool]): This check is provided with DQOps as a built-in check.
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
    """

    check_name: Union[Unset, str] = UNSET
    sensor_name: Union[Unset, str] = UNSET
    rule_name: Union[Unset, str] = UNSET
    help_text: Union[Unset, str] = UNSET
    custom: Union[Unset, bool] = UNSET
    built_in: Union[Unset, bool] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        check_name = self.check_name
        sensor_name = self.sensor_name
        rule_name = self.rule_name
        help_text = self.help_text
        custom = self.custom
        built_in = self.built_in
        can_edit = self.can_edit

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
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit

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

        can_edit = d.pop("can_edit", UNSET)

        check_definition_model = cls(
            check_name=check_name,
            sensor_name=sensor_name,
            rule_name=rule_name,
            help_text=help_text,
            custom=custom,
            built_in=built_in,
            can_edit=can_edit,
        )

        check_definition_model.additional_properties = d
        return check_definition_model

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
