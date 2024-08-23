from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.incident_filtered_notification_location import (
    IncidentFilteredNotificationLocation,
)
from ..models.incident_status import IncidentStatus
from ..types import UNSET, Unset

T = TypeVar("T", bound="IncidentModel")


@_attrs_define
class IncidentModel:
    """
    Attributes:
        incident_id (Union[Unset, str]): Incident ID - the primary key that identifies each data quality incident.
        connection (Union[Unset, str]): Connection name affected by a data quality incident.
        year (Union[Unset, int]): The year when the incident was first seen. This value is required to load an
            incident's monthly partition.
        month (Union[Unset, int]): The month when the incident was first seen. This value is required to load an
            incident's monthly partition.
        schema (Union[Unset, str]): Schema name affected by a data quality incident.
        table (Union[Unset, str]): Table name affected by a data quality incident.
        table_priority (Union[Unset, int]): Table priority of the table that was affected by a data quality incident.
        incident_hash (Union[Unset, int]): Data quality incident hash that identifies similar incidents on the same
            incident grouping level.
        first_seen (Union[Unset, int]): The UTC timestamp when the data quality incident was first seen.
        last_seen (Union[Unset, int]): The UTC timestamp when the data quality incident was last seen.
        incident_until (Union[Unset, int]): The UTC timestamp when the data quality incident is valid until. All new
            failed data quality check results until that date will be included in this incident, unless the incident status
            is changed to resolved, so a new incident must be created.
        data_group (Union[Unset, str]): The data group that was affected by a data quality incident.
        quality_dimension (Union[Unset, str]): The data quality dimension that was affected by a data quality incident.
        check_category (Union[Unset, str]): The data quality check category that was affected by a data quality
            incident.
        check_type (Union[Unset, str]): The data quality check type that was affected by a data quality incident.
        check_name (Union[Unset, str]): The data quality check name that was affected by a data quality incident.
        highest_severity (Union[Unset, int]): The highest failed check severity that was detected as part of this data
            quality incident. Possible values are: 1 - warning, 2 - error, 3 - fatal.
        minimum_severity (Union[Unset, int]): The minimum severity of the data quality incident, copied from the
            incident configuration at a connection or table at the time when the incident was first seen. Possible values
            are: 1 - warning, 2 - error, 3 - fatal.
        failed_checks_count (Union[Unset, int]): The total number of failed data quality checks that were seen when the
            incident was raised for the first time.
        issue_url (Union[Unset, str]): The link (url) to a ticket in an external system that is tracking this incident.
        status (Union[Unset, IncidentStatus]):
        notification_name (Union[Unset, str]): Matching filtered notification for this incident.
        notification_location (Union[Unset, IncidentFilteredNotificationLocation]):
    """

    incident_id: Union[Unset, str] = UNSET
    connection: Union[Unset, str] = UNSET
    year: Union[Unset, int] = UNSET
    month: Union[Unset, int] = UNSET
    schema: Union[Unset, str] = UNSET
    table: Union[Unset, str] = UNSET
    table_priority: Union[Unset, int] = UNSET
    incident_hash: Union[Unset, int] = UNSET
    first_seen: Union[Unset, int] = UNSET
    last_seen: Union[Unset, int] = UNSET
    incident_until: Union[Unset, int] = UNSET
    data_group: Union[Unset, str] = UNSET
    quality_dimension: Union[Unset, str] = UNSET
    check_category: Union[Unset, str] = UNSET
    check_type: Union[Unset, str] = UNSET
    check_name: Union[Unset, str] = UNSET
    highest_severity: Union[Unset, int] = UNSET
    minimum_severity: Union[Unset, int] = UNSET
    failed_checks_count: Union[Unset, int] = UNSET
    issue_url: Union[Unset, str] = UNSET
    status: Union[Unset, IncidentStatus] = UNSET
    notification_name: Union[Unset, str] = UNSET
    notification_location: Union[Unset, IncidentFilteredNotificationLocation] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        incident_id = self.incident_id
        connection = self.connection
        year = self.year
        month = self.month
        schema = self.schema
        table = self.table
        table_priority = self.table_priority
        incident_hash = self.incident_hash
        first_seen = self.first_seen
        last_seen = self.last_seen
        incident_until = self.incident_until
        data_group = self.data_group
        quality_dimension = self.quality_dimension
        check_category = self.check_category
        check_type = self.check_type
        check_name = self.check_name
        highest_severity = self.highest_severity
        minimum_severity = self.minimum_severity
        failed_checks_count = self.failed_checks_count
        issue_url = self.issue_url
        status: Union[Unset, str] = UNSET
        if not isinstance(self.status, Unset):
            status = self.status.value

        notification_name = self.notification_name
        notification_location: Union[Unset, str] = UNSET
        if not isinstance(self.notification_location, Unset):
            notification_location = self.notification_location.value

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if incident_id is not UNSET:
            field_dict["incidentId"] = incident_id
        if connection is not UNSET:
            field_dict["connection"] = connection
        if year is not UNSET:
            field_dict["year"] = year
        if month is not UNSET:
            field_dict["month"] = month
        if schema is not UNSET:
            field_dict["schema"] = schema
        if table is not UNSET:
            field_dict["table"] = table
        if table_priority is not UNSET:
            field_dict["tablePriority"] = table_priority
        if incident_hash is not UNSET:
            field_dict["incidentHash"] = incident_hash
        if first_seen is not UNSET:
            field_dict["firstSeen"] = first_seen
        if last_seen is not UNSET:
            field_dict["lastSeen"] = last_seen
        if incident_until is not UNSET:
            field_dict["incidentUntil"] = incident_until
        if data_group is not UNSET:
            field_dict["dataGroup"] = data_group
        if quality_dimension is not UNSET:
            field_dict["qualityDimension"] = quality_dimension
        if check_category is not UNSET:
            field_dict["checkCategory"] = check_category
        if check_type is not UNSET:
            field_dict["checkType"] = check_type
        if check_name is not UNSET:
            field_dict["checkName"] = check_name
        if highest_severity is not UNSET:
            field_dict["highestSeverity"] = highest_severity
        if minimum_severity is not UNSET:
            field_dict["minimumSeverity"] = minimum_severity
        if failed_checks_count is not UNSET:
            field_dict["failedChecksCount"] = failed_checks_count
        if issue_url is not UNSET:
            field_dict["issueUrl"] = issue_url
        if status is not UNSET:
            field_dict["status"] = status
        if notification_name is not UNSET:
            field_dict["notificationName"] = notification_name
        if notification_location is not UNSET:
            field_dict["notificationLocation"] = notification_location

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        incident_id = d.pop("incidentId", UNSET)

        connection = d.pop("connection", UNSET)

        year = d.pop("year", UNSET)

        month = d.pop("month", UNSET)

        schema = d.pop("schema", UNSET)

        table = d.pop("table", UNSET)

        table_priority = d.pop("tablePriority", UNSET)

        incident_hash = d.pop("incidentHash", UNSET)

        first_seen = d.pop("firstSeen", UNSET)

        last_seen = d.pop("lastSeen", UNSET)

        incident_until = d.pop("incidentUntil", UNSET)

        data_group = d.pop("dataGroup", UNSET)

        quality_dimension = d.pop("qualityDimension", UNSET)

        check_category = d.pop("checkCategory", UNSET)

        check_type = d.pop("checkType", UNSET)

        check_name = d.pop("checkName", UNSET)

        highest_severity = d.pop("highestSeverity", UNSET)

        minimum_severity = d.pop("minimumSeverity", UNSET)

        failed_checks_count = d.pop("failedChecksCount", UNSET)

        issue_url = d.pop("issueUrl", UNSET)

        _status = d.pop("status", UNSET)
        status: Union[Unset, IncidentStatus]
        if isinstance(_status, Unset):
            status = UNSET
        else:
            status = IncidentStatus(_status)

        notification_name = d.pop("notificationName", UNSET)

        _notification_location = d.pop("notificationLocation", UNSET)
        notification_location: Union[Unset, IncidentFilteredNotificationLocation]
        if isinstance(_notification_location, Unset):
            notification_location = UNSET
        else:
            notification_location = IncidentFilteredNotificationLocation(
                _notification_location
            )

        incident_model = cls(
            incident_id=incident_id,
            connection=connection,
            year=year,
            month=month,
            schema=schema,
            table=table,
            table_priority=table_priority,
            incident_hash=incident_hash,
            first_seen=first_seen,
            last_seen=last_seen,
            incident_until=incident_until,
            data_group=data_group,
            quality_dimension=quality_dimension,
            check_category=check_category,
            check_type=check_type,
            check_name=check_name,
            highest_severity=highest_severity,
            minimum_severity=minimum_severity,
            failed_checks_count=failed_checks_count,
            issue_url=issue_url,
            status=status,
            notification_name=notification_name,
            notification_location=notification_location,
        )

        incident_model.additional_properties = d
        return incident_model

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
