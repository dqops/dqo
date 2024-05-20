from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="IncidentsPerConnectionModel")


@_attrs_define
class IncidentsPerConnectionModel:
    """
    Attributes:
        connection (Union[Unset, str]): Connection (data source) name.
        open_incidents (Union[Unset, int]): Count of open (new) data quality incidents.
        most_recent_first_seen (Union[Unset, int]): The UTC timestamp when the most recent data quality incident was
            first seen.
    """

    connection: Union[Unset, str] = UNSET
    open_incidents: Union[Unset, int] = UNSET
    most_recent_first_seen: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection = self.connection
        open_incidents = self.open_incidents
        most_recent_first_seen = self.most_recent_first_seen

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection is not UNSET:
            field_dict["connection"] = connection
        if open_incidents is not UNSET:
            field_dict["openIncidents"] = open_incidents
        if most_recent_first_seen is not UNSET:
            field_dict["mostRecentFirstSeen"] = most_recent_first_seen

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        connection = d.pop("connection", UNSET)

        open_incidents = d.pop("openIncidents", UNSET)

        most_recent_first_seen = d.pop("mostRecentFirstSeen", UNSET)

        incidents_per_connection_model = cls(
            connection=connection,
            open_incidents=open_incidents,
            most_recent_first_seen=most_recent_first_seen,
        )

        incidents_per_connection_model.additional_properties = d
        return incidents_per_connection_model

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
