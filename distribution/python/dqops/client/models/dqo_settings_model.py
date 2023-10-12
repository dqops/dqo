from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.dqo_settings_model_properties import DqoSettingsModelProperties


T = TypeVar("T", bound="DqoSettingsModel")


@_attrs_define
class DqoSettingsModel:
    """DQOps system settings

    Attributes:
        properties (Union[Unset, DqoSettingsModelProperties]): Dictionary of all effective DQOps system properties,
            retrieved from the default configuration files, user configuration files, environment variables and 'dqo'
            command arguments.
    """

    properties: Union[Unset, "DqoSettingsModelProperties"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        properties: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.properties, Unset):
            properties = self.properties.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if properties is not UNSET:
            field_dict["properties"] = properties

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.dqo_settings_model_properties import DqoSettingsModelProperties

        d = src_dict.copy()
        _properties = d.pop("properties", UNSET)
        properties: Union[Unset, DqoSettingsModelProperties]
        if isinstance(_properties, Unset):
            properties = UNSET
        else:
            properties = DqoSettingsModelProperties.from_dict(_properties)

        dqo_settings_model = cls(
            properties=properties,
        )

        dqo_settings_model.additional_properties = d
        return dqo_settings_model

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
