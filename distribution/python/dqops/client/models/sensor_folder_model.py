from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.sensor_folder_model_folders import SensorFolderModelFolders
    from ..models.sensor_list_model import SensorListModel


T = TypeVar("T", bound="SensorFolderModel")


@_attrs_define
class SensorFolderModel:
    """Sensor folder model that contains sensors defined in this folder or a list of nested folders with sensors.

    Attributes:
        folders (Union[Unset, SensorFolderModelFolders]): A dictionary of nested folders with sensors, the keys are the
            folder names.
        sensors (Union[Unset, List['SensorListModel']]): List of sensors defined in this folder.
        all_sensors (Union[Unset, List['SensorListModel']]):
    """

    folders: Union[Unset, "SensorFolderModelFolders"] = UNSET
    sensors: Union[Unset, List["SensorListModel"]] = UNSET
    all_sensors: Union[Unset, List["SensorListModel"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

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
        from ..models.sensor_folder_model_folders import SensorFolderModelFolders
        from ..models.sensor_list_model import SensorListModel

        d = src_dict.copy()
        _folders = d.pop("folders", UNSET)
        folders: Union[Unset, SensorFolderModelFolders]
        if isinstance(_folders, Unset):
            folders = UNSET
        else:
            folders = SensorFolderModelFolders.from_dict(_folders)

        sensors = []
        _sensors = d.pop("sensors", UNSET)
        for sensors_item_data in _sensors or []:
            sensors_item = SensorListModel.from_dict(sensors_item_data)

            sensors.append(sensors_item)

        all_sensors = []
        _all_sensors = d.pop("all_sensors", UNSET)
        for all_sensors_item_data in _all_sensors or []:
            all_sensors_item = SensorListModel.from_dict(all_sensors_item_data)

            all_sensors.append(all_sensors_item)

        sensor_folder_model = cls(
            folders=folders,
            sensors=sensors,
            all_sensors=all_sensors,
        )

        sensor_folder_model.additional_properties = d
        return sensor_folder_model

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
