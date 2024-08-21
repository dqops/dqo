from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="IncidentNotificationTargetSpec")


@_attrs_define
class IncidentNotificationTargetSpec:
    """
    Attributes:
        incident_opened_addresses (Union[Unset, str]): Notification address(es) where the notification messages
            describing new incidents are pushed using a HTTP POST request (for webhook address) or an SMTP (for email
            address). The format of the JSON message is documented in the IncidentNotificationMessage object.
        incident_acknowledged_addresses (Union[Unset, str]): Notification address(es) where the notification messages
            describing acknowledged messages are pushed using a HTTP POST request (for webhook address) or an SMTP (for
            email address). The format of the JSON message is documented in the IncidentNotificationMessage object.
        incident_resolved_addresses (Union[Unset, str]): Notification address(es) where the notification messages
            describing resolved messages are pushed using a HTTP POST request (for webhook address) or an SMTP (for email
            address). The format of the JSON message is documented in the IncidentNotificationMessage object.
        incident_muted_addresses (Union[Unset, str]): Notification address(es) where the notification messages
            describing muted messages are pushed using a HTTP POST request (for webhook address) or an SMTP (for email
            address). The format of the JSON message is documented in the IncidentNotificationMessage object.
    """

    incident_opened_addresses: Union[Unset, str] = UNSET
    incident_acknowledged_addresses: Union[Unset, str] = UNSET
    incident_resolved_addresses: Union[Unset, str] = UNSET
    incident_muted_addresses: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        incident_opened_addresses = self.incident_opened_addresses
        incident_acknowledged_addresses = self.incident_acknowledged_addresses
        incident_resolved_addresses = self.incident_resolved_addresses
        incident_muted_addresses = self.incident_muted_addresses

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if incident_opened_addresses is not UNSET:
            field_dict["incident_opened_addresses"] = incident_opened_addresses
        if incident_acknowledged_addresses is not UNSET:
            field_dict["incident_acknowledged_addresses"] = (
                incident_acknowledged_addresses
            )
        if incident_resolved_addresses is not UNSET:
            field_dict["incident_resolved_addresses"] = incident_resolved_addresses
        if incident_muted_addresses is not UNSET:
            field_dict["incident_muted_addresses"] = incident_muted_addresses

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        incident_opened_addresses = d.pop("incident_opened_addresses", UNSET)

        incident_acknowledged_addresses = d.pop(
            "incident_acknowledged_addresses", UNSET
        )

        incident_resolved_addresses = d.pop("incident_resolved_addresses", UNSET)

        incident_muted_addresses = d.pop("incident_muted_addresses", UNSET)

        incident_notification_target_spec = cls(
            incident_opened_addresses=incident_opened_addresses,
            incident_acknowledged_addresses=incident_acknowledged_addresses,
            incident_resolved_addresses=incident_resolved_addresses,
            incident_muted_addresses=incident_muted_addresses,
        )

        incident_notification_target_spec.additional_properties = d
        return incident_notification_target_spec

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
