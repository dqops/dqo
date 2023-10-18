from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.dqo_root import DqoRoot
from ..models.file_synchronization_direction import FileSynchronizationDirection
from ..types import UNSET, Unset

T = TypeVar("T", bound="SynchronizeRootFolderParameters")


@_attrs_define
class SynchronizeRootFolderParameters:
    """
    Attributes:
        folder (Union[Unset, DqoRoot]):
        direction (Union[Unset, FileSynchronizationDirection]):
        force_refresh_native_table (Union[Unset, bool]):
    """

    folder: Union[Unset, DqoRoot] = UNSET
    direction: Union[Unset, FileSynchronizationDirection] = UNSET
    force_refresh_native_table: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        folder: Union[Unset, str] = UNSET
        if not isinstance(self.folder, Unset):
            folder = self.folder.value

        direction: Union[Unset, str] = UNSET
        if not isinstance(self.direction, Unset):
            direction = self.direction.value

        force_refresh_native_table = self.force_refresh_native_table

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if folder is not UNSET:
            field_dict["folder"] = folder
        if direction is not UNSET:
            field_dict["direction"] = direction
        if force_refresh_native_table is not UNSET:
            field_dict["forceRefreshNativeTable"] = force_refresh_native_table

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _folder = d.pop("folder", UNSET)
        folder: Union[Unset, DqoRoot]
        if isinstance(_folder, Unset):
            folder = UNSET
        else:
            folder = DqoRoot(_folder)

        _direction = d.pop("direction", UNSET)
        direction: Union[Unset, FileSynchronizationDirection]
        if isinstance(_direction, Unset):
            direction = UNSET
        else:
            direction = FileSynchronizationDirection(_direction)

        force_refresh_native_table = d.pop("forceRefreshNativeTable", UNSET)

        synchronize_root_folder_parameters = cls(
            folder=folder,
            direction=direction,
            force_refresh_native_table=force_refresh_native_table,
        )

        synchronize_root_folder_parameters.additional_properties = d
        return synchronize_root_folder_parameters

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
