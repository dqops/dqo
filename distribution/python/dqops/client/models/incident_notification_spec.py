from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.incident_notification_spec_filtered_notifications import (
        IncidentNotificationSpecFilteredNotifications,
    )


T = TypeVar("T", bound="IncidentNotificationSpec")


@_attrs_define
class IncidentNotificationSpec:
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
        filtered_notifications (Union[Unset, IncidentNotificationSpecFilteredNotifications]): Filtered notifications map
            with filter configuration and notification addresses treated with higher priority than those from the current
            class.
    """

    incident_opened_addresses: Union[Unset, str] = UNSET
    incident_acknowledged_addresses: Union[Unset, str] = UNSET
    incident_resolved_addresses: Union[Unset, str] = UNSET
    incident_muted_addresses: Union[Unset, str] = UNSET
    filtered_notifications: Union[
        Unset, "IncidentNotificationSpecFilteredNotifications"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        incident_opened_addresses = self.incident_opened_addresses
        incident_acknowledged_addresses = self.incident_acknowledged_addresses
        incident_resolved_addresses = self.incident_resolved_addresses
        incident_muted_addresses = self.incident_muted_addresses
        filtered_notifications: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.filtered_notifications, Unset):
            filtered_notifications = self.filtered_notifications.to_dict()

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
        if filtered_notifications is not UNSET:
            field_dict["filtered_notifications"] = filtered_notifications

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.incident_notification_spec_filtered_notifications import (
            IncidentNotificationSpecFilteredNotifications,
        )

        d = src_dict.copy()
        incident_opened_addresses = d.pop("incident_opened_addresses", UNSET)

        incident_acknowledged_addresses = d.pop(
            "incident_acknowledged_addresses", UNSET
        )

        incident_resolved_addresses = d.pop("incident_resolved_addresses", UNSET)

        incident_muted_addresses = d.pop("incident_muted_addresses", UNSET)

        _filtered_notifications = d.pop("filtered_notifications", UNSET)
        filtered_notifications: Union[
            Unset, IncidentNotificationSpecFilteredNotifications
        ]
        if isinstance(_filtered_notifications, Unset):
            filtered_notifications = UNSET
        else:
            filtered_notifications = (
                IncidentNotificationSpecFilteredNotifications.from_dict(
                    _filtered_notifications
                )
            )

        incident_notification_spec = cls(
            incident_opened_addresses=incident_opened_addresses,
            incident_acknowledged_addresses=incident_acknowledged_addresses,
            incident_resolved_addresses=incident_resolved_addresses,
            incident_muted_addresses=incident_muted_addresses,
            filtered_notifications=filtered_notifications,
        )

        incident_notification_spec.additional_properties = d
        return incident_notification_spec

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
