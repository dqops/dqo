from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.provider_type import ProviderType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.provider_sensor_definition_spec import ProviderSensorDefinitionSpec


T = TypeVar("T", bound="ProviderSensorModel")


@_attrs_define
class ProviderSensorModel:
    """Provider sensor model

    Attributes:
        provider_type (Union[Unset, ProviderType]):
        provider_sensor_definition_spec (Union[Unset, ProviderSensorDefinitionSpec]):
        sql_template (Union[Unset, str]): Provider specific Jinja2 SQL template
        error_sampling_template (Union[Unset, str]): Provider specific Jinja2 SQL template for capturing error samples
        custom (Union[Unset, bool]): Whether the provider sensor is a User Home provider sensor
        built_in (Union[Unset, bool]): This is a DQOps built-in provider sensor, whose parameters cannot be changed.
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
        yaml_parsing_error (Union[Unset, str]): Optional parsing error that was captured when parsing the YAML file.
            This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing
            error message and the file location.
    """

    provider_type: Union[Unset, ProviderType] = UNSET
    provider_sensor_definition_spec: Union[Unset, "ProviderSensorDefinitionSpec"] = (
        UNSET
    )
    sql_template: Union[Unset, str] = UNSET
    error_sampling_template: Union[Unset, str] = UNSET
    custom: Union[Unset, bool] = UNSET
    built_in: Union[Unset, bool] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    yaml_parsing_error: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

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
        error_sampling_template = self.error_sampling_template
        custom = self.custom
        built_in = self.built_in
        can_edit = self.can_edit
        yaml_parsing_error = self.yaml_parsing_error

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if provider_type is not UNSET:
            field_dict["providerType"] = provider_type
        if provider_sensor_definition_spec is not UNSET:
            field_dict["providerSensorDefinitionSpec"] = provider_sensor_definition_spec
        if sql_template is not UNSET:
            field_dict["sqlTemplate"] = sql_template
        if error_sampling_template is not UNSET:
            field_dict["errorSamplingTemplate"] = error_sampling_template
        if custom is not UNSET:
            field_dict["custom"] = custom
        if built_in is not UNSET:
            field_dict["builtIn"] = built_in
        if can_edit is not UNSET:
            field_dict["canEdit"] = can_edit
        if yaml_parsing_error is not UNSET:
            field_dict["yamlParsingError"] = yaml_parsing_error

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.provider_sensor_definition_spec import (
            ProviderSensorDefinitionSpec,
        )

        d = src_dict.copy()
        _provider_type = d.pop("providerType", UNSET)
        provider_type: Union[Unset, ProviderType]
        if isinstance(_provider_type, Unset):
            provider_type = UNSET
        else:
            provider_type = ProviderType(_provider_type)

        _provider_sensor_definition_spec = d.pop("providerSensorDefinitionSpec", UNSET)
        provider_sensor_definition_spec: Union[Unset, ProviderSensorDefinitionSpec]
        if isinstance(_provider_sensor_definition_spec, Unset):
            provider_sensor_definition_spec = UNSET
        else:
            provider_sensor_definition_spec = ProviderSensorDefinitionSpec.from_dict(
                _provider_sensor_definition_spec
            )

        sql_template = d.pop("sqlTemplate", UNSET)

        error_sampling_template = d.pop("errorSamplingTemplate", UNSET)

        custom = d.pop("custom", UNSET)

        built_in = d.pop("builtIn", UNSET)

        can_edit = d.pop("canEdit", UNSET)

        yaml_parsing_error = d.pop("yamlParsingError", UNSET)

        provider_sensor_model = cls(
            provider_type=provider_type,
            provider_sensor_definition_spec=provider_sensor_definition_spec,
            sql_template=sql_template,
            error_sampling_template=error_sampling_template,
            custom=custom,
            built_in=built_in,
            can_edit=can_edit,
            yaml_parsing_error=yaml_parsing_error,
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
