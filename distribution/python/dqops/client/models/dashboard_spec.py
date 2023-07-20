from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.dashboard_spec_parameters import DashboardSpecParameters


T = TypeVar("T", bound="DashboardSpec")


@attr.s(auto_attribs=True)
class DashboardSpec:
    """
    Attributes:
        dashboard_name (Union[Unset, str]): Dashboard name
        url (Union[Unset, str]): Dashboard url
        width (Union[Unset, int]): Dashboard width (px)
        height (Union[Unset, int]): Dashboard height (px)
        parameters (Union[Unset, DashboardSpecParameters]): Key/value dictionary of additional parameters to be passed
            to the dashboard
    """

    dashboard_name: Union[Unset, str] = UNSET
    url: Union[Unset, str] = UNSET
    width: Union[Unset, int] = UNSET
    height: Union[Unset, int] = UNSET
    parameters: Union[Unset, "DashboardSpecParameters"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        dashboard_name = self.dashboard_name
        url = self.url
        width = self.width
        height = self.height
        parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.parameters, Unset):
            parameters = self.parameters.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if dashboard_name is not UNSET:
            field_dict["dashboard_name"] = dashboard_name
        if url is not UNSET:
            field_dict["url"] = url
        if width is not UNSET:
            field_dict["width"] = width
        if height is not UNSET:
            field_dict["height"] = height
        if parameters is not UNSET:
            field_dict["parameters"] = parameters

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.dashboard_spec_parameters import DashboardSpecParameters

        d = src_dict.copy()
        dashboard_name = d.pop("dashboard_name", UNSET)

        url = d.pop("url", UNSET)

        width = d.pop("width", UNSET)

        height = d.pop("height", UNSET)

        _parameters = d.pop("parameters", UNSET)
        parameters: Union[Unset, DashboardSpecParameters]
        if isinstance(_parameters, Unset):
            parameters = UNSET
        else:
            parameters = DashboardSpecParameters.from_dict(_parameters)

        dashboard_spec = cls(
            dashboard_name=dashboard_name,
            url=url,
            width=width,
            height=height,
            parameters=parameters,
        )

        dashboard_spec.additional_properties = d
        return dashboard_spec

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
