from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.sensor_basic_folder_model_folders import SensorBasicFolderModelFolders
    from ..models.sensor_basic_model import SensorBasicModel


T = TypeVar("T", bound="SensorBasicFolderModel")


@attr.s(auto_attribs=True)
class SensorBasicFolderModel:
    """Sensor basic folder model

    Attributes:
        folders (Union[Unset, SensorBasicFolderModelFolders]): A map of folder-level children sensors.
        sensors (Union[Unset, List['SensorBasicModel']]): Whether the sensor is a User Home sensor.
        all_sensors (Union[Unset, List['SensorBasicModel']]):
    """

    folders: Union[Unset, "SensorBasicFolderModelFolders"] = UNSET
    sensors: Union[Unset, List["SensorBasicModel"]] = UNSET
    all_sensors: Union[Unset, List["SensorBasicModel"]] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        folders: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.folders, Unset):
            folders = self.folders.to_dict()

        sensors: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.sensors, Unset):
            sensors = []
            for sensors_item_data in self.sensors:
                sensors_item = sensors_item_data.to_dict()

                sensors.append(sensors_item)

        all_sensors: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.all_sensors, Unset):
            all_sensors = []
            for all_sensors_item_data in self.all_sensors:
                all_sensors_item = all_sensors_item_data.to_dict()

                all_sensors.append(all_sensors_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if folders is not UNSET:
            field_dict["folders"] = folders
        if sensors is not UNSET:
            field_dict["sensors"] = sensors
        if all_sensors is not UNSET:
            field_dict["all_sensors"] = all_sensors

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.sensor_basic_folder_model_folders import (
            SensorBasicFolderModelFolders,
        )
        from ..models.sensor_basic_model import SensorBasicModel

        d = src_dict.copy()
        _folders = d.pop("folders", UNSET)
        folders: Union[Unset, SensorBasicFolderModelFolders]
        if isinstance(_folders, Unset):
            folders = UNSET
        else:
            folders = SensorBasicFolderModelFolders.from_dict(_folders)

        sensors = []
        _sensors = d.pop("sensors", UNSET)
        for sensors_item_data in _sensors or []:
            sensors_item = SensorBasicModel.from_dict(sensors_item_data)

            sensors.append(sensors_item)

        all_sensors = []
        _all_sensors = d.pop("all_sensors", UNSET)
        for all_sensors_item_data in _all_sensors or []:
            all_sensors_item = SensorBasicModel.from_dict(all_sensors_item_data)

            all_sensors.append(all_sensors_item)

        sensor_basic_folder_model = cls(
            folders=folders,
            sensors=sensors,
            all_sensors=all_sensors,
        )

        sensor_basic_folder_model.additional_properties = d
        return sensor_basic_folder_model

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
