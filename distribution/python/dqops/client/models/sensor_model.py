from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.provider_sensor_model import ProviderSensorModel
    from ..models.sensor_definition_spec import SensorDefinitionSpec


T = TypeVar("T", bound="SensorModel")


@_attrs_define
class SensorModel:
    """Sensor model that describes the configuration of a single built-in or custom sensor.

    Attributes:
        full_sensor_name (Union[Unset, str]): Full sensor name.
        sensor_definition_spec (Union[Unset, SensorDefinitionSpec]):
        provider_sensor_list (Union[Unset, List['ProviderSensorModel']]): Provider sensors list with provider specific
            sensor definitions.
        custom (Union[Unset, bool]): Whether the sensor is a User Home sensor
        built_in (Union[Unset, bool]): This is a DQOps built-in sensor, whose parameters cannot be changed.
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
        yaml_parsing_error (Union[Unset, str]): Optional parsing error that was captured when parsing the YAML file.
            This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing
            error message and the file location.
    """

    full_sensor_name: Union[Unset, str] = UNSET
    sensor_definition_spec: Union[Unset, "SensorDefinitionSpec"] = UNSET
    provider_sensor_list: Union[Unset, List["ProviderSensorModel"]] = UNSET
    custom: Union[Unset, bool] = UNSET
    built_in: Union[Unset, bool] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    yaml_parsing_error: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        full_sensor_name = self.full_sensor_name
        sensor_definition_spec: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sensor_definition_spec, Unset):
            sensor_definition_spec = self.sensor_definition_spec.to_dict()

        provider_sensor_list: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.provider_sensor_list, Unset):
            provider_sensor_list = []
            for provider_sensor_list_item_data in self.provider_sensor_list:
                provider_sensor_list_item = provider_sensor_list_item_data.to_dict()

                provider_sensor_list.append(provider_sensor_list_item)

        custom = self.custom
        built_in = self.built_in
        can_edit = self.can_edit
        yaml_parsing_error = self.yaml_parsing_error

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if full_sensor_name is not UNSET:
            field_dict["full_sensor_name"] = full_sensor_name
        if sensor_definition_spec is not UNSET:
            field_dict["sensor_definition_spec"] = sensor_definition_spec
        if provider_sensor_list is not UNSET:
            field_dict["provider_sensor_list"] = provider_sensor_list
        if custom is not UNSET:
            field_dict["custom"] = custom
        if built_in is not UNSET:
            field_dict["built_in"] = built_in
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit
        if yaml_parsing_error is not UNSET:
            field_dict["yaml_parsing_error"] = yaml_parsing_error

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.provider_sensor_model import ProviderSensorModel
        from ..models.sensor_definition_spec import SensorDefinitionSpec

        d = src_dict.copy()
        full_sensor_name = d.pop("full_sensor_name", UNSET)

        _sensor_definition_spec = d.pop("sensor_definition_spec", UNSET)
        sensor_definition_spec: Union[Unset, SensorDefinitionSpec]
        if isinstance(_sensor_definition_spec, Unset):
            sensor_definition_spec = UNSET
        else:
            sensor_definition_spec = SensorDefinitionSpec.from_dict(
                _sensor_definition_spec
            )

        provider_sensor_list = []
        _provider_sensor_list = d.pop("provider_sensor_list", UNSET)
        for provider_sensor_list_item_data in _provider_sensor_list or []:
            provider_sensor_list_item = ProviderSensorModel.from_dict(
                provider_sensor_list_item_data
            )

            provider_sensor_list.append(provider_sensor_list_item)

        custom = d.pop("custom", UNSET)

        built_in = d.pop("built_in", UNSET)

        can_edit = d.pop("can_edit", UNSET)

        yaml_parsing_error = d.pop("yaml_parsing_error", UNSET)

        sensor_model = cls(
            full_sensor_name=full_sensor_name,
            sensor_definition_spec=sensor_definition_spec,
            provider_sensor_list=provider_sensor_list,
            custom=custom,
            built_in=built_in,
            can_edit=can_edit,
            yaml_parsing_error=yaml_parsing_error,
        )

        sensor_model.additional_properties = d
        return sensor_model

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
