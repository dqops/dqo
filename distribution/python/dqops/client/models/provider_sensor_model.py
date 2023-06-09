from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..models.provider_sensor_model_provider_type import ProviderSensorModelProviderType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.provider_sensor_definition_spec import ProviderSensorDefinitionSpec


T = TypeVar("T", bound="ProviderSensorModel")


@attr.s(auto_attribs=True)
class ProviderSensorModel:
    """Provider sensor model

    Attributes:
        provider_type (Union[Unset, ProviderSensorModelProviderType]): Provider type.
        provider_sensor_definition_spec (Union[Unset, ProviderSensorDefinitionSpec]):
        sql_template (Union[Unset, str]): Provider Sql template
        custom (Union[Unset, bool]): Whether the provider sensor is a User Home provider sensor
        built_in (Union[Unset, bool]): This is a DQO built-in provider sensor, whose parameters cannot be changed.
    """

    provider_type: Union[Unset, ProviderSensorModelProviderType] = UNSET
    provider_sensor_definition_spec: Union[
        Unset, "ProviderSensorDefinitionSpec"
    ] = UNSET
    sql_template: Union[Unset, str] = UNSET
    custom: Union[Unset, bool] = UNSET
    built_in: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        provider_type: Union[Unset, str] = UNSET
        if not isinstance(self.provider_type, Unset):
            provider_type = self.provider_type.value

        provider_sensor_definition_spec: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.provider_sensor_definition_spec, Unset):
            provider_sensor_definition_spec = (
                self.provider_sensor_definition_spec.to_dict()
            )

        sql_template = self.sql_template
        custom = self.custom
        built_in = self.built_in

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if provider_type is not UNSET:
            field_dict["providerType"] = provider_type
        if provider_sensor_definition_spec is not UNSET:
            field_dict["providerSensorDefinitionSpec"] = provider_sensor_definition_spec
        if sql_template is not UNSET:
            field_dict["sqlTemplate"] = sql_template
        if custom is not UNSET:
            field_dict["custom"] = custom
        if built_in is not UNSET:
            field_dict["builtIn"] = built_in

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.provider_sensor_definition_spec import (
            ProviderSensorDefinitionSpec,
        )

        d = src_dict.copy()
        _provider_type = d.pop("providerType", UNSET)
        provider_type: Union[Unset, ProviderSensorModelProviderType]
        if isinstance(_provider_type, Unset):
            provider_type = UNSET
        else:
            provider_type = ProviderSensorModelProviderType(_provider_type)

        _provider_sensor_definition_spec = d.pop("providerSensorDefinitionSpec", UNSET)
        provider_sensor_definition_spec: Union[Unset, ProviderSensorDefinitionSpec]
        if isinstance(_provider_sensor_definition_spec, Unset):
            provider_sensor_definition_spec = UNSET
        else:
            provider_sensor_definition_spec = ProviderSensorDefinitionSpec.from_dict(
                _provider_sensor_definition_spec
            )

        sql_template = d.pop("sqlTemplate", UNSET)

        custom = d.pop("custom", UNSET)

        built_in = d.pop("builtIn", UNSET)

        provider_sensor_model = cls(
            provider_type=provider_type,
            provider_sensor_definition_spec=provider_sensor_definition_spec,
            sql_template=sql_template,
            custom=custom,
            built_in=built_in,
        )

        provider_sensor_model.additional_properties = d
        return provider_sensor_model

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
