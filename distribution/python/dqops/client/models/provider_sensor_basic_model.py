from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..models.provider_sensor_basic_model_provider_type import (
    ProviderSensorBasicModelProviderType,
)
from ..types import UNSET, Unset

T = TypeVar("T", bound="ProviderSensorBasicModel")


@attr.s(auto_attribs=True)
class ProviderSensorBasicModel:
    """Provider sensor basic model

    Attributes:
        provider_type (Union[Unset, ProviderSensorBasicModelProviderType]): Provider type.
        custom (Union[Unset, bool]): This connection specific template is a custom sensor template or was customized by
            the user.
        built_in (Union[Unset, bool]): This connection specific template is provided with DQO as a built-in sensor.
    """

    provider_type: Union[Unset, ProviderSensorBasicModelProviderType] = UNSET
    custom: Union[Unset, bool] = UNSET
    built_in: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        provider_type: Union[Unset, str] = UNSET
        if not isinstance(self.provider_type, Unset):
            provider_type = self.provider_type.value

        custom = self.custom
        built_in = self.built_in

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if provider_type is not UNSET:
            field_dict["provider_type"] = provider_type
        if custom is not UNSET:
            field_dict["custom"] = custom
        if built_in is not UNSET:
            field_dict["built_in"] = built_in

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _provider_type = d.pop("provider_type", UNSET)
        provider_type: Union[Unset, ProviderSensorBasicModelProviderType]
        if isinstance(_provider_type, Unset):
            provider_type = UNSET
        else:
            provider_type = ProviderSensorBasicModelProviderType(_provider_type)

        custom = d.pop("custom", UNSET)

        built_in = d.pop("built_in", UNSET)

        provider_sensor_basic_model = cls(
            provider_type=provider_type,
            custom=custom,
            built_in=built_in,
        )

        provider_sensor_basic_model.additional_properties = d
        return provider_sensor_basic_model

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
