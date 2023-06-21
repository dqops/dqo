from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar

import attr

if TYPE_CHECKING:
    from ..models.sensor_basic_folder_model import SensorBasicFolderModel


T = TypeVar("T", bound="SensorBasicFolderModelFolders")


@attr.s(auto_attribs=True)
class SensorBasicFolderModelFolders:
    """A map of folder-level children sensors."""

    additional_properties: Dict[str, "SensorBasicFolderModel"] = attr.ib(
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
        from ..models.sensor_basic_folder_model import SensorBasicFolderModel

        d = src_dict.copy()
        sensor_basic_folder_model_folders = cls()

        additional_properties = {}
        for prop_name, prop_dict in d.items():
            additional_property = SensorBasicFolderModel.from_dict(prop_dict)

            additional_properties[prop_name] = additional_property

        sensor_basic_folder_model_folders.additional_properties = additional_properties
        return sensor_basic_folder_model_folders

    @property
    def additional_keys(self) -> List[str]:
        return list(self.additional_properties.keys())

    def __getitem__(self, key: str) -> "SensorBasicFolderModel":
        return self.additional_properties[key]

    def __setitem__(self, key: str, value: "SensorBasicFolderModel") -> None:
        self.additional_properties[key] = value

    def __delitem__(self, key: str) -> None:
        del self.additional_properties[key]

    def __contains__(self, key: str) -> bool:
        return key in self.additional_properties
