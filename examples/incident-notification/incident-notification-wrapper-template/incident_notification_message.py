from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from dqops.client.models.incident_status import IncidentStatus
from dqops.client.types import UNSET, Unset

T = TypeVar("T", bound="IncidentNotificationMessage")


@_attrs_define
class IncidentNotificationMessage:
    """
    Attributes:
        incident_id (Union[Unset, str]): Incident ID - the primary key that identifies each data quality incident.
        connection (Union[Unset, str]): Connection name affected by a data quality incident.
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
        data_group_name (Union[Unset, str]): The data group name that was affected by a data quality incident. The data
            group names are created from the values of columns and tags configured in the data grouping configuration. An
            example data group when grouping a static tag "customers"  as the first level grouping and a *country* column
            value for the second grouping level is *customers / UK*.
        quality_dimension (Union[Unset, str]): The data quality dimension that was affected by a data quality incident.
        check_category (Union[Unset, str]): The data quality check category that was affected by a data quality
            incident.
        check_type (Union[Unset, str]): The data quality check type that was affected by a data quality incident.
        check_name (Union[Unset, str]): The data quality check name that was affected by a data quality incident.
        highest_severity (Union[Unset, int]): The highest failed check severity that was detected as part of this data
            quality incident. Possible values are: 1 - warning, 2 - error, 3 - fatal.
        failed_checks_count (Union[Unset, int]): The total number of failed data quality checks that were seen when the
            incident was raised for the first time.
        issue_url (Union[Unset, str]): The link (url) to a ticket in an external system that is tracking this incident.
        status (Union[Unset, IncidentStatus]):
        message (Union[Unset, str]): Message with the details of the filtered notification such as purpose explanation,
            SLA note, etc.
        text (Union[Unset, str]): Notification text that contains the most important fields from the class.
    """

    incident_id: Union[Unset, str] = UNSET
    connection: Union[Unset, str] = UNSET
    schema: Union[Unset, str] = UNSET
    table: Union[Unset, str] = UNSET
    table_priority: Union[Unset, int] = UNSET
    incident_hash: Union[Unset, int] = UNSET
    first_seen: Union[Unset, int] = UNSET
    last_seen: Union[Unset, int] = UNSET
    incident_until: Union[Unset, int] = UNSET
    data_group_name: Union[Unset, str] = UNSET
    quality_dimension: Union[Unset, str] = UNSET
    check_category: Union[Unset, str] = UNSET
    check_type: Union[Unset, str] = UNSET
    check_name: Union[Unset, str] = UNSET
    highest_severity: Union[Unset, int] = UNSET
    failed_checks_count: Union[Unset, int] = UNSET
    issue_url: Union[Unset, str] = UNSET
    status: Union[Unset, IncidentStatus] = UNSET
    message: Union[Unset, str] = UNSET
    text: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        incident_id = self.incident_id
        connection = self.connection
        schema = self.schema
        table = self.table
        table_priority = self.table_priority
        incident_hash = self.incident_hash
        first_seen = self.first_seen
        last_seen = self.last_seen
        incident_until = self.incident_until
        data_group_name = self.data_group_name
        quality_dimension = self.quality_dimension
        check_category = self.check_category
        check_type = self.check_type
        check_name = self.check_name
        highest_severity = self.highest_severity
        failed_checks_count = self.failed_checks_count
        issue_url = self.issue_url
        status: Union[Unset, str] = UNSET
        if not isinstance(self.status, Unset):
            status = self.status.value

        message = self.message
        text = self.text

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if incident_id is not UNSET:
            field_dict["incident_id"] = incident_id
        if connection is not UNSET:
            field_dict["connection"] = connection
        if schema is not UNSET:
            field_dict["schema"] = schema
        if table is not UNSET:
            field_dict["table"] = table
        if table_priority is not UNSET:
            field_dict["table_priority"] = table_priority
        if incident_hash is not UNSET:
            field_dict["incident_hash"] = incident_hash
        if first_seen is not UNSET:
            field_dict["first_seen"] = first_seen
        if last_seen is not UNSET:
            field_dict["last_seen"] = last_seen
        if incident_until is not UNSET:
            field_dict["incident_until"] = incident_until
        if data_group_name is not UNSET:
            field_dict["data_group_name"] = data_group_name
        if quality_dimension is not UNSET:
            field_dict["quality_dimension"] = quality_dimension
        if check_category is not UNSET:
            field_dict["check_category"] = check_category
        if check_type is not UNSET:
            field_dict["check_type"] = check_type
        if check_name is not UNSET:
            field_dict["check_name"] = check_name
        if highest_severity is not UNSET:
            field_dict["highest_severity"] = highest_severity
        if failed_checks_count is not UNSET:
            field_dict["failed_checks_count"] = failed_checks_count
        if issue_url is not UNSET:
            field_dict["issue_url"] = issue_url
        if status is not UNSET:
            field_dict["status"] = status
        if message is not UNSET:
            field_dict["message"] = message
        if text is not UNSET:
            field_dict["text"] = text

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        incident_id = d.pop("incident_id", UNSET)

        connection = d.pop("connection", UNSET)

        schema = d.pop("schema", UNSET)

        table = d.pop("table", UNSET)

        table_priority = d.pop("table_priority", UNSET)

        incident_hash = d.pop("incident_hash", UNSET)

        first_seen = d.pop("first_seen", UNSET)

        last_seen = d.pop("last_seen", UNSET)

        incident_until = d.pop("incident_until", UNSET)

        data_group_name = d.pop("data_group_name", UNSET)

        quality_dimension = d.pop("quality_dimension", UNSET)

        check_category = d.pop("check_category", UNSET)

        check_type = d.pop("check_type", UNSET)

        check_name = d.pop("check_name", UNSET)

        highest_severity = d.pop("highest_severity", UNSET)

        failed_checks_count = d.pop("failed_checks_count", UNSET)

        issue_url = d.pop("issue_url", UNSET)

        _status = d.pop("status", UNSET)
        status: Union[Unset, IncidentStatus]
        if isinstance(_status, Unset):
            status = UNSET
        else:
            status = IncidentStatus(_status)

        message = d.pop("message", UNSET)

        text = d.pop("text", UNSET)

        incident_notification_message = cls(
            incident_id=incident_id,
            connection=connection,
            schema=schema,
            table=table,
            table_priority=table_priority,
            incident_hash=incident_hash,
            first_seen=first_seen,
            last_seen=last_seen,
            incident_until=incident_until,
            data_group_name=data_group_name,
            quality_dimension=quality_dimension,
            check_category=check_category,
            check_type=check_type,
            check_name=check_name,
            highest_severity=highest_severity,
            failed_checks_count=failed_checks_count,
            issue_url=issue_url,
            status=status,
            message=message,
            text=text,
        )

        incident_notification_message.additional_properties = d
        return incident_notification_message

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
