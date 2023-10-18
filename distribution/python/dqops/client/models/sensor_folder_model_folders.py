from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar

from attrs import define as _attrs_define
from attrs import field as _attrs_field

if TYPE_CHECKING:
    from ..models.sensor_folder_model import SensorFolderModel


T = TypeVar("T", bound="SensorFolderModelFolders")


@_attrs_define
class SensorFolderModelFolders:
    """A dictionary of nested folders with sensors, the keys are the folder names."""

    additional_properties: Dict[str, "SensorFolderModel"] = _attrs_field(
        init=False, factory=dict
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
        from ..models.sensor_folder_model import SensorFolderModel

        d = src_dict.copy()
        sensor_folder_model_folders = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = SensorFolderModel.from_dict(prop_dict)

            additional_properties[prop_name] = additional_property

        sensor_folder_model_folders.additional_properties = additional_properties
        return sensor_folder_model_folders

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> "SensorFolderModel":
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: "SensorFolderModel") -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
