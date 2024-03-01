from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.data_type_category import DataTypeCategory
from ..types import UNSET, Unset

T = TypeVar("T", bound="TargetColumnPatternSpec")


@_attrs_define
class TargetColumnPatternSpec:
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
        column (Union[Unset, str]): The target column name filter. Accepts wildcards in the format: *id, *, c_*.
        data_type (Union[Unset, str]): The target column data type filter. Filters by a physical (database specific)
            data type name imported from the data source. Accepts wildcards in the format: *int, *, big*.
        data_type_category (Union[Unset, DataTypeCategory]):
    """

    connection: Union[Unset, str] = UNSET
    schema: Union[Unset, str] = UNSET
    table: Union[Unset, str] = UNSET
    stage: Union[Unset, str] = UNSET
    table_priority: Union[Unset, int] = UNSET
    label: Union[Unset, str] = UNSET
    column: Union[Unset, str] = UNSET
    data_type: Union[Unset, str] = UNSET
    data_type_category: Union[Unset, DataTypeCategory] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection = self.connection
        schema = self.schema
        table = self.table
        stage = self.stage
        table_priority = self.table_priority
        label = self.label
        column = self.column
        data_type = self.data_type
        data_type_category: Union[Unset, str] = UNSET
        if not isinstance(self.data_type_category, Unset):
            data_type_category = self.data_type_category.value

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
        if column is not UNSET:
            field_dict["column"] = column
        if data_type is not UNSET:
            field_dict["data_type"] = data_type
        if data_type_category is not UNSET:
            field_dict["data_type_category"] = data_type_category

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

        column = d.pop("column", UNSET)

        data_type = d.pop("data_type", UNSET)

        _data_type_category = d.pop("data_type_category", UNSET)
        data_type_category: Union[Unset, DataTypeCategory]
        if isinstance(_data_type_category, Unset):
            data_type_category = UNSET
        else:
            data_type_category = DataTypeCategory(_data_type_category)

        target_column_pattern_spec = cls(
            connection=connection,
            schema=schema,
            table=table,
            stage=stage,
            table_priority=table_priority,
            label=label,
            column=column,
            data_type=data_type,
            data_type_category=data_type_category,
        )

        target_column_pattern_spec.additional_properties = d
        return target_column_pattern_spec

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
