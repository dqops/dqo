from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_schema_column_count_statistics_collector_spec import (
        TableSchemaColumnCountStatisticsCollectorSpec,
    )


T = TypeVar("T", bound="TableSchemaStatisticsCollectorsSpec")


@_attrs_define
class TableSchemaStatisticsCollectorsSpec:
    """
    Attributes:
        column_count (Union[Unset, TableSchemaColumnCountStatisticsCollectorSpec]):
    """

    column_count: Union[Unset, "TableSchemaColumnCountStatisticsCollectorSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        column_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.column_count, Unset):
            column_count = self.column_count.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if column_count is not UNSET:
            field_dict["column_count"] = column_count

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_schema_column_count_statistics_collector_spec import (
            TableSchemaColumnCountStatisticsCollectorSpec,
        )

        d = src_dict.copy()
        _column_count = d.pop("column_count", UNSET)
        column_count: Union[Unset, TableSchemaColumnCountStatisticsCollectorSpec]
        if isinstance(_column_count, Unset):
            column_count = UNSET
        else:
            column_count = TableSchemaColumnCountStatisticsCollectorSpec.from_dict(
                _column_count
            )

        table_schema_statistics_collectors_spec = cls(
            column_count=column_count,
        )

        table_schema_statistics_collectors_spec.additional_properties = d
        return table_schema_statistics_collectors_spec

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
