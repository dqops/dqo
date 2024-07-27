from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.incident_grouping_level import IncidentGroupingLevel
from ..models.minimum_grouping_severity_level import MinimumGroupingSeverityLevel
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.incident_notification_spec import IncidentNotificationSpec


T = TypeVar("T", bound="ConnectionIncidentGroupingSpec")


@_attrs_define
class ConnectionIncidentGroupingSpec:
    """
    Attributes:
        grouping_level (Union[Unset, IncidentGroupingLevel]):
        minimum_severity (Union[Unset, MinimumGroupingSeverityLevel]):
        divide_by_data_groups (Union[Unset, bool]): Create separate data quality incidents for each data group, creating
            different incidents for different groups of rows. By default, data groups are ignored for grouping data quality
            issues into data quality incidents.
        max_incident_length_days (Union[Unset, int]): The maximum length of a data quality incident in days. When a new
            data quality issue is detected after max_incident_length_days days since a similar data quality was first seen,
            a new data quality incident is created that will capture all following data quality issues for the next
            max_incident_length_days days. The default value is 60 days.
        mute_for_days (Union[Unset, int]): The number of days that all similar data quality issues are muted when a a
            data quality incident is closed in the 'mute' status.
        disabled (Union[Unset, bool]): Disables data quality incident creation for failed data quality checks on the
            data source.
        incident_notification (Union[Unset, IncidentNotificationSpec]):
    """

    grouping_level: Union[Unset, IncidentGroupingLevel] = UNSET
    minimum_severity: Union[Unset, MinimumGroupingSeverityLevel] = UNSET
    divide_by_data_groups: Union[Unset, bool] = UNSET
    max_incident_length_days: Union[Unset, int] = UNSET
    mute_for_days: Union[Unset, int] = UNSET
    disabled: Union[Unset, bool] = UNSET
    incident_notification: Union[Unset, "IncidentNotificationSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        grouping_level: Union[Unset, str] = UNSET
        if not isinstance(self.grouping_level, Unset):
            grouping_level = self.grouping_level.value

        minimum_severity: Union[Unset, str] = UNSET
        if not isinstance(self.minimum_severity, Unset):
            minimum_severity = self.minimum_severity.value

        divide_by_data_groups = self.divide_by_data_groups
        max_incident_length_days = self.max_incident_length_days
        mute_for_days = self.mute_for_days
        disabled = self.disabled
        incident_notification: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.incident_notification, Unset):
            incident_notification = self.incident_notification.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if grouping_level is not UNSET:
            field_dict["grouping_level"] = grouping_level
        if minimum_severity is not UNSET:
            field_dict["minimum_severity"] = minimum_severity
        if divide_by_data_groups is not UNSET:
            field_dict["divide_by_data_groups"] = divide_by_data_groups
        if max_incident_length_days is not UNSET:
            field_dict["max_incident_length_days"] = max_incident_length_days
        if mute_for_days is not UNSET:
            field_dict["mute_for_days"] = mute_for_days
        if disabled is not UNSET:
            field_dict["disabled"] = disabled
        if incident_notification is not UNSET:
            field_dict["incident_notification"] = incident_notification

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.incident_notification_spec import IncidentNotificationSpec

        d = src_dict.copy()
        _grouping_level = d.pop("grouping_level", UNSET)
        grouping_level: Union[Unset, IncidentGroupingLevel]
        if isinstance(_grouping_level, Unset):
            grouping_level = UNSET
        else:
            grouping_level = IncidentGroupingLevel(_grouping_level)

        _minimum_severity = d.pop("minimum_severity", UNSET)
        minimum_severity: Union[Unset, MinimumGroupingSeverityLevel]
        if isinstance(_minimum_severity, Unset):
            minimum_severity = UNSET
        else:
            minimum_severity = MinimumGroupingSeverityLevel(_minimum_severity)

        divide_by_data_groups = d.pop("divide_by_data_groups", UNSET)

        max_incident_length_days = d.pop("max_incident_length_days", UNSET)

        mute_for_days = d.pop("mute_for_days", UNSET)

        disabled = d.pop("disabled", UNSET)

        _incident_notification = d.pop("incident_notification", UNSET)
        incident_notification: Union[Unset, IncidentNotificationSpec]
        if isinstance(_incident_notification, Unset):
            incident_notification = UNSET
        else:
            incident_notification = IncidentNotificationSpec.from_dict(
                _incident_notification
            )

        connection_incident_grouping_spec = cls(
            grouping_level=grouping_level,
            minimum_severity=minimum_severity,
            divide_by_data_groups=divide_by_data_groups,
            max_incident_length_days=max_incident_length_days,
            mute_for_days=mute_for_days,
            disabled=disabled,
            incident_notification=incident_notification,
        )

        connection_incident_grouping_spec.additional_properties = d
        return connection_incident_grouping_spec

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
