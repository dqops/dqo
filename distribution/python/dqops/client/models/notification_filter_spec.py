from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.check_type import CheckType
from ..types import UNSET, Unset

T = TypeVar("T", bound="NotificationFilterSpec")


@_attrs_define
class NotificationFilterSpec:
    r"""
    Attributes:
        connection (Union[Unset, str]): Connection name. Supports search patterns in the format: 'source\*', '\*_prod',
            'prefix\*suffix'.
        schema (Union[Unset, str]): Schema name. This field accepts search patterns in the format: 'schema_name_\*',
            '\*_schema', 'prefix\*suffix'.
        table (Union[Unset, str]): Table name. This field accepts search patterns in the format: 'table_name_\*',
            '\*table', 'prefix\*suffix'.
        table_priority (Union[Unset, int]): Table priority.
        data_group_name (Union[Unset, str]): Data group name. This field accepts search patterns in the format:
            'group_name_\*', '\*group', 'prefix\*suffix'.
        quality_dimension (Union[Unset, str]): Quality dimension.
        check_category (Union[Unset, str]): The target check category, for example: *nulls*, *volume*, *anomaly*.
        check_type (Union[Unset, CheckType]):
        check_name (Union[Unset, str]): The target check name to run only this named check. Uses the short check name
            which is the name of the deepest folder in the *checks* folder. This field supports search patterns such as:
            'profiling_\*', '\*_count', 'profiling_\*_percent'.
        highest_severity (Union[Unset, int]): Highest severity.
    """

    connection: Union[Unset, str] = UNSET
    schema: Union[Unset, str] = UNSET
    table: Union[Unset, str] = UNSET
    table_priority: Union[Unset, int] = UNSET
    data_group_name: Union[Unset, str] = UNSET
    quality_dimension: Union[Unset, str] = UNSET
    check_category: Union[Unset, str] = UNSET
    check_type: Union[Unset, CheckType] = UNSET
    check_name: Union[Unset, str] = UNSET
    highest_severity: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection = self.connection
        schema = self.schema
        table = self.table
        table_priority = self.table_priority
        data_group_name = self.data_group_name
        quality_dimension = self.quality_dimension
        check_category = self.check_category
        check_type: Union[Unset, str] = UNSET
        if not isinstance(self.check_type, Unset):
            check_type = self.check_type.value

        check_name = self.check_name
        highest_severity = self.highest_severity

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection is not UNSET:
            field_dict["connection"] = connection
        if schema is not UNSET:
            field_dict["schema"] = schema
        if table is not UNSET:
            field_dict["table"] = table
        if table_priority is not UNSET:
            field_dict["tablePriority"] = table_priority
        if data_group_name is not UNSET:
            field_dict["dataGroupName"] = data_group_name
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

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        connection = d.pop("connection", UNSET)

        schema = d.pop("schema", UNSET)

        table = d.pop("table", UNSET)

        table_priority = d.pop("tablePriority", UNSET)

        data_group_name = d.pop("dataGroupName", UNSET)

        quality_dimension = d.pop("qualityDimension", UNSET)

        check_category = d.pop("checkCategory", UNSET)

        _check_type = d.pop("checkType", UNSET)
        check_type: Union[Unset, CheckType]
        if isinstance(_check_type, Unset):
            check_type = UNSET
        else:
            check_type = CheckType(_check_type)

        check_name = d.pop("checkName", UNSET)

        highest_severity = d.pop("highestSeverity", UNSET)

        notification_filter_spec = cls(
            connection=connection,
            schema=schema,
            table=table,
            table_priority=table_priority,
            data_group_name=data_group_name,
            quality_dimension=quality_dimension,
            check_category=check_category,
            check_type=check_type,
            check_name=check_name,
            highest_severity=highest_severity,
        )

        notification_filter_spec.additional_properties = d
        return notification_filter_spec

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
