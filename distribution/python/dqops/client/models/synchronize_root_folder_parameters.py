from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..models.synchronize_root_folder_parameters_direction import (
    SynchronizeRootFolderParametersDirection,
)
from ..models.synchronize_root_folder_parameters_folder import (
    SynchronizeRootFolderParametersFolder,
)
from ..types import UNSET, Unset

T = TypeVar("T", bound="SynchronizeRootFolderParameters")


@attr.s(auto_attribs=True)
class SynchronizeRootFolderParameters:
    """
    Attributes:
        folder (Union[Unset, SynchronizeRootFolderParametersFolder]):
        direction (Union[Unset, SynchronizeRootFolderParametersDirection]):
        force_refresh_native_table (Union[Unset, bool]):
    """

    folder: Union[Unset, SynchronizeRootFolderParametersFolder] = UNSET
    direction: Union[Unset, SynchronizeRootFolderParametersDirection] = UNSET
    force_refresh_native_table: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

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
        folder: Union[Unset, SynchronizeRootFolderParametersFolder]
        if isinstance(_folder, Unset):
            folder = UNSET
        else:
            folder = SynchronizeRootFolderParametersFolder(_folder)

        _direction = d.pop("direction", UNSET)
        direction: Union[Unset, SynchronizeRootFolderParametersDirection]
        if isinstance(_direction, Unset):
            direction = UNSET
        else:
            direction = SynchronizeRootFolderParametersDirection(_direction)

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
