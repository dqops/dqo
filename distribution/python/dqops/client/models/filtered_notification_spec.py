from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.incident_notification_target_spec import (
        IncidentNotificationTargetSpec,
    )
    from ..models.notification_filter_spec import NotificationFilterSpec


T = TypeVar("T", bound="FilteredNotificationSpec")


@_attrs_define
class FilteredNotificationSpec:
    """
    Attributes:
        filter_ (Union[Unset, NotificationFilterSpec]):
        target (Union[Unset, IncidentNotificationTargetSpec]):
        priority (Union[Unset, int]): The priority of the notification. Notifications are sent to the first notification
            targets that matches the filters when processAdditionalFilters is not set.
        process_additional_filters (Union[Unset, bool]): Flag to break sending next notifications. Setting to true
            allows to send next notification from the list in priority order that matches the filter.
        disabled (Union[Unset, bool]): Flag to turn off the notification filter.
        message (Union[Unset, str]): Message with the details of the filtered notification such as purpose explanation,
            SLA note, etc.
        do_not_create_incidents (Union[Unset, bool]): Flag to remove incident that match the filters.
    """

    filter_: Union[Unset, "NotificationFilterSpec"] = UNSET
    target: Union[Unset, "IncidentNotificationTargetSpec"] = UNSET
    priority: Union[Unset, int] = UNSET
    process_additional_filters: Union[Unset, bool] = UNSET
    disabled: Union[Unset, bool] = UNSET
    message: Union[Unset, str] = UNSET
    do_not_create_incidents: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        filter_: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.filter_, Unset):
            filter_ = self.filter_.to_dict()

        target: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.target, Unset):
            target = self.target.to_dict()

        priority = self.priority
        process_additional_filters = self.process_additional_filters
        disabled = self.disabled
        message = self.message
        do_not_create_incidents = self.do_not_create_incidents

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if filter_ is not UNSET:
            field_dict["filter"] = filter_
        if target is not UNSET:
            field_dict["target"] = target
        if priority is not UNSET:
            field_dict["priority"] = priority
        if process_additional_filters is not UNSET:
            field_dict["process_additional_filters"] = process_additional_filters
        if disabled is not UNSET:
            field_dict["disabled"] = disabled
        if message is not UNSET:
            field_dict["message"] = message
        if do_not_create_incidents is not UNSET:
            field_dict["do_not_create_incidents"] = do_not_create_incidents

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.incident_notification_target_spec import (
            IncidentNotificationTargetSpec,
        )
        from ..models.notification_filter_spec import NotificationFilterSpec

        d = src_dict.copy()
        _filter_ = d.pop("filter", UNSET)
        filter_: Union[Unset, NotificationFilterSpec]
        if isinstance(_filter_, Unset):
            filter_ = UNSET
        else:
            filter_ = NotificationFilterSpec.from_dict(_filter_)

        _target = d.pop("target", UNSET)
        target: Union[Unset, IncidentNotificationTargetSpec]
        if isinstance(_target, Unset):
            target = UNSET
        else:
            target = IncidentNotificationTargetSpec.from_dict(_target)

        priority = d.pop("priority", UNSET)

        process_additional_filters = d.pop("process_additional_filters", UNSET)

        disabled = d.pop("disabled", UNSET)

        message = d.pop("message", UNSET)

        do_not_create_incidents = d.pop("do_not_create_incidents", UNSET)

        filtered_notification_spec = cls(
            filter_=filter_,
            target=target,
            priority=priority,
            process_additional_filters=process_additional_filters,
            disabled=disabled,
            message=message,
            do_not_create_incidents=do_not_create_incidents,
        )

        filtered_notification_spec.additional_properties = d
        return filtered_notification_spec

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
