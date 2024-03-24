from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="TargetTablePatternSpec")


@_attrs_define
class TargetTablePatternSpec:
    """
    Attributes:
        connection (Union[Unset, str]): The data source connection name filter. Accepts wildcards in the format: *conn,
            *, conn*.
        schema (Union[Unset, str]): The schema name filter. Accepts wildcards in the format: *_prod, *, pub*.
        table (Union[Unset, str]): The table name filter. Accepts wildcards in the format: *_customers, *, fact_*.
        stage (Union[Unset, str]): The table stage filter. Accepts wildcards in the format: *_landing, *, staging_*.
        table_priority (Union[Unset, int]): The maximum table priority (inclusive) for tables that are covered by the
            default checks.
        label (Union[Unset, str]): The label filter. Accepts wildcards in the format: *_customers, *, fact_*. The label
            must be present on the connection or table.
    """

    connection: Union[Unset, str] = UNSET
    schema: Union[Unset, str] = UNSET
    table: Union[Unset, str] = UNSET
    stage: Union[Unset, str] = UNSET
    table_priority: Union[Unset, int] = UNSET
    label: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection = self.connection
        schema = self.schema
        table = self.table
        stage = self.stage
        table_priority = self.table_priority
        label = self.label

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection is not UNSET:
            field_dict["connection"] = connection
        if schema is not UNSET:
            field_dict["schema"] = schema
        if table is not UNSET:
            field_dict["table"] = table
        if stage is not UNSET:
            field_dict["stage"] = stage
        if table_priority is not UNSET:
            field_dict["table_priority"] = table_priority
        if label is not UNSET:
            field_dict["label"] = label

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        connection = d.pop("connection", UNSET)

        schema = d.pop("schema", UNSET)

        table = d.pop("table", UNSET)

        stage = d.pop("stage", UNSET)

        table_priority = d.pop("table_priority", UNSET)

        label = d.pop("label", UNSET)

        target_table_pattern_spec = cls(
            connection=connection,
            schema=schema,
            table=table,
            stage=stage,
            table_priority=table_priority,
            label=label,
        )

        target_table_pattern_spec.additional_properties = d
        return target_table_pattern_spec

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
