from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.dashboard_spec import DashboardSpec


T = TypeVar("T", bound="DashboardsFolderSpec")


@_attrs_define
class DashboardsFolderSpec:
    """
    Attributes:
        folder_name (Union[Unset, str]): Folder name
        standard (Union[Unset, bool]): Always shows this schema tree node because it contains standard dashboards. Set
            the value to false to show this folder only when advanced dashboards are enabled.
        dashboards (Union[Unset, List['DashboardSpec']]): List of data quality dashboard at this level.
        folders (Union[Unset, List['DashboardsFolderSpec']]): List of data quality dashboard folders at this level.
    """

    folder_name: Union[Unset, str] = UNSET
    standard: Union[Unset, bool] = UNSET
    dashboards: Union[Unset, List["DashboardSpec"]] = UNSET
    folders: Union[Unset, List["DashboardsFolderSpec"]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        folder_name = self.folder_name
        standard = self.standard
        dashboards: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.dashboards, Unset):
            dashboards = []
            for dashboards_item_data in self.dashboards:
                dashboards_item = dashboards_item_data.to_dict()

                dashboards.append(dashboards_item)

        folders: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.folders, Unset):
            folders = []
            for folders_item_data in self.folders:
                folders_item = folders_item_data.to_dict()

                folders.append(folders_item)

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if folder_name is not UNSET:
            field_dict["folder_name"] = folder_name
        if standard is not UNSET:
            field_dict["standard"] = standard
        if dashboards is not UNSET:
            field_dict["dashboards"] = dashboards
        if folders is not UNSET:
            field_dict["folders"] = folders

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.dashboard_spec import DashboardSpec

        d = src_dict.copy()
        folder_name = d.pop("folder_name", UNSET)

        standard = d.pop("standard", UNSET)

        dashboards = []
        _dashboards = d.pop("dashboards", UNSET)
        for dashboards_item_data in _dashboards or []:
            dashboards_item = DashboardSpec.from_dict(dashboards_item_data)

            dashboards.append(dashboards_item)

        folders = []
        _folders = d.pop("folders", UNSET)
        for folders_item_data in _folders or []:
            folders_item = DashboardsFolderSpec.from_dict(folders_item_data)

            folders.append(folders_item)

        dashboards_folder_spec = cls(
            folder_name=folder_name,
            standard=standard,
            dashboards=dashboards,
            folders=folders,
        )

        dashboards_folder_spec.additional_properties = d
        return dashboards_folder_spec

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
