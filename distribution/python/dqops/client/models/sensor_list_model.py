from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.provider_sensor_list_model import ProviderSensorListModel


T = TypeVar("T", bound="SensorListModel")


@_attrs_define
class SensorListModel:
    """Sensor list model

    Attributes:
        sensor_name (Union[Unset, str]): Sensor name, excluding the parent folder.
        full_sensor_name (Union[Unset, str]): Full sensor name, including the folder path within the "sensors" folder
            where the sensor definitions are stored. This is the unique identifier of the sensor.
        custom (Union[Unset, bool]): This sensor has is a custom sensor or was customized by the user. This is a read-
            only flag.
        built_in (Union[Unset, bool]): This sensor is provided with DQOps as a built-in sensor. This is a read-only
            flag.
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
        provider_sensors (Union[Unset, List['ProviderSensorListModel']]): List of provider (database) specific models.
        yaml_parsing_error (Union[Unset, str]): Optional parsing error that was captured when parsing the YAML file.
            This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing
            error message and the file location.
    """

    sensor_name: Union[Unset, str] = UNSET
    full_sensor_name: Union[Unset, str] = UNSET
    custom: Union[Unset, bool] = UNSET
    built_in: Union[Unset, bool] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    provider_sensors: Union[Unset, List["ProviderSensorListModel"]] = UNSET
    yaml_parsing_error: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        sensor_name = self.sensor_name
        full_sensor_name = self.full_sensor_name
        custom = self.custom
        built_in = self.built_in
        can_edit = self.can_edit
        provider_sensors: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.provider_sensors, Unset):
            provider_sensors = []
            for provider_sensors_item_data in self.provider_sensors:
                provider_sensors_item = provider_sensors_item_data.to_dict()

                provider_sensors.append(provider_sensors_item)

        yaml_parsing_error = self.yaml_parsing_error

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if sensor_name is not UNSET:
            field_dict["sensor_name"] = sensor_name
        if full_sensor_name is not UNSET:
            field_dict["full_sensor_name"] = full_sensor_name
        if custom is not UNSET:
            field_dict["custom"] = custom
        if built_in is not UNSET:
            field_dict["built_in"] = built_in
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit
        if provider_sensors is not UNSET:
            field_dict["provider_sensors"] = provider_sensors
        if yaml_parsing_error is not UNSET:
            field_dict["yaml_parsing_error"] = yaml_parsing_error

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.provider_sensor_list_model import ProviderSensorListModel

        d = src_dict.copy()
        sensor_name = d.pop("sensor_name", UNSET)

        full_sensor_name = d.pop("full_sensor_name", UNSET)

        custom = d.pop("custom", UNSET)

        built_in = d.pop("built_in", UNSET)

        can_edit = d.pop("can_edit", UNSET)

        provider_sensors = []
        _provider_sensors = d.pop("provider_sensors", UNSET)
        for provider_sensors_item_data in _provider_sensors or []:
            provider_sensors_item = ProviderSensorListModel.from_dict(
                provider_sensors_item_data
            )

            provider_sensors.append(provider_sensors_item)

        yaml_parsing_error = d.pop("yaml_parsing_error", UNSET)

        sensor_list_model = cls(
            sensor_name=sensor_name,
            full_sensor_name=full_sensor_name,
            custom=custom,
            built_in=built_in,
            can_edit=can_edit,
            provider_sensors=provider_sensors,
            yaml_parsing_error=yaml_parsing_error,
        )

        sensor_list_model.additional_properties = d
        return sensor_list_model

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
