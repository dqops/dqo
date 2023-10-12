from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.dashboard_spec import DashboardSpec


T = TypeVar("T", bound="AuthenticatedDashboardModel")


@_attrs_define
class AuthenticatedDashboardModel:
    """Describes a single authenticated dashboard.

    Attributes:
        folder_path (Union[Unset, str]): Folder path
        dashboard (Union[Unset, DashboardSpec]):
        authenticated_dashboard_url (Union[Unset, str]): Dashboard authenticated url with a short lived refresh token
    """

    folder_path: Union[Unset, str] = UNSET
    dashboard: Union[Unset, "DashboardSpec"] = UNSET
    authenticated_dashboard_url: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        folder_path = self.folder_path
        dashboard: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.dashboard, Unset):
            dashboard = self.dashboard.to_dict()

        authenticated_dashboard_url = self.authenticated_dashboard_url

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if folder_path is not UNSET:
            field_dict["folder_path"] = folder_path
        if dashboard is not UNSET:
            field_dict["dashboard"] = dashboard
        if authenticated_dashboard_url is not UNSET:
            field_dict["authenticated_dashboard_url"] = authenticated_dashboard_url

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.dashboard_spec import DashboardSpec

        d = src_dict.copy()
        folder_path = d.pop("folder_path", UNSET)

        _dashboard = d.pop("dashboard", UNSET)
        dashboard: Union[Unset, DashboardSpec]
        if isinstance(_dashboard, Unset):
            dashboard = UNSET
        else:
            dashboard = DashboardSpec.from_dict(_dashboard)

        authenticated_dashboard_url = d.pop("authenticated_dashboard_url", UNSET)

        authenticated_dashboard_model = cls(
            folder_path=folder_path,
            dashboard=dashboard,
            authenticated_dashboard_url=authenticated_dashboard_url,
        )

        authenticated_dashboard_model.additional_properties = d
        return authenticated_dashboard_model

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
