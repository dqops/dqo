from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_schema_statistics_collectors_spec import (
        TableSchemaStatisticsCollectorsSpec,
    )
    from ..models.table_volume_statistics_collectors_spec import (
        TableVolumeStatisticsCollectorsSpec,
    )


T = TypeVar("T", bound="TableStatisticsCollectorsRootCategoriesSpec")


@_attrs_define
class TableStatisticsCollectorsRootCategoriesSpec:
    """
    Attributes:
        volume (Union[Unset, TableVolumeStatisticsCollectorsSpec]):
        schema (Union[Unset, TableSchemaStatisticsCollectorsSpec]):
    """

    volume: Union[Unset, "TableVolumeStatisticsCollectorsSpec"] = UNSET
    schema: Union[Unset, "TableSchemaStatisticsCollectorsSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        volume: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.volume, Unset):
            volume = self.volume.to_dict()

        schema: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.schema, Unset):
            schema = self.schema.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if volume is not UNSET:
            field_dict["volume"] = volume
        if schema is not UNSET:
            field_dict["schema"] = schema

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_schema_statistics_collectors_spec import (
            TableSchemaStatisticsCollectorsSpec,
        )
        from ..models.table_volume_statistics_collectors_spec import (
            TableVolumeStatisticsCollectorsSpec,
        )

        d = src_dict.copy()
        _volume = d.pop("volume", UNSET)
        volume: Union[Unset, TableVolumeStatisticsCollectorsSpec]
        if isinstance(_volume, Unset):
            volume = UNSET
        else:
            volume = TableVolumeStatisticsCollectorsSpec.from_dict(_volume)

        _schema = d.pop("schema", UNSET)
        schema: Union[Unset, TableSchemaStatisticsCollectorsSpec]
        if isinstance(_schema, Unset):
            schema = UNSET
        else:
            schema = TableSchemaStatisticsCollectorsSpec.from_dict(_schema)

        table_statistics_collectors_root_categories_spec = cls(
            volume=volume,
            schema=schema,
        )

        table_statistics_collectors_root_categories_spec.additional_properties = d
        return table_statistics_collectors_root_categories_spec

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
