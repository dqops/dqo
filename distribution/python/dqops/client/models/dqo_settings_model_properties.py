from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar

from attrs import define as _attrs_define
from attrs import field as _attrs_field

if TYPE_CHECKING:
    from ..models.dqo_settings_model_properties_additional_property import (
        DqoSettingsModelPropertiesAdditionalProperty,
    )


T = TypeVar("T", bound="DqoSettingsModelProperties")


@_attrs_define
class DqoSettingsModelProperties:
    """Dictionary of all effective DQOps system properties, retrieved from the default configuration files, user
    configuration files, environment variables and 'dqo' command arguments.

    """

    additional_properties: Dict[str, "DqoSettingsModelPropertiesAdditionalProperty"] = (
        _attrs_field(init=False, factory=dict)
    )

    def to_dict(self) -> Dict[str, Any]:
        pass

        field_dict: Dict[str, Any] = {}
        for prop_name, prop in self.additional_properties.items():
            field_dict[prop_name] = prop.to_dict()

        field_dict.update({})

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.dqo_settings_model_properties_additional_property import (
            DqoSettingsModelPropertiesAdditionalProperty,
        )

        d = src_dict.copy()
        dqo_settings_model_properties = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = (
                DqoSettingsModelPropertiesAdditionalProperty.from_dict(prop_dict)
            )

            additional_properties[prop_name] = additional_property

        dqo_settings_model_properties.additional_properties = additional_properties
        return dqo_settings_model_properties

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> "DqoSettingsModelPropertiesAdditionalProperty":
        return self.additional_properties[key]

    def __setitem__(
        self, key: str, value: "DqoSettingsModelPropertiesAdditionalProperty"
    ) -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
