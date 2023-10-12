from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_volume_row_count_statistics_collector_spec import (
        TableVolumeRowCountStatisticsCollectorSpec,
    )


T = TypeVar("T", bound="TableVolumeStatisticsCollectorsSpec")


@_attrs_define
class TableVolumeStatisticsCollectorsSpec:
    """
    Attributes:
        row_count (Union[Unset, TableVolumeRowCountStatisticsCollectorSpec]):
    """

    row_count: Union[Unset, "TableVolumeRowCountStatisticsCollectorSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        row_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.row_count, Unset):
            row_count = self.row_count.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if row_count is not UNSET:
            field_dict["row_count"] = row_count

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_volume_row_count_statistics_collector_spec import (
            TableVolumeRowCountStatisticsCollectorSpec,
        )

        d = src_dict.copy()
        _row_count = d.pop("row_count", UNSET)
        row_count: Union[Unset, TableVolumeRowCountStatisticsCollectorSpec]
        if isinstance(_row_count, Unset):
            row_count = UNSET
        else:
            row_count = TableVolumeRowCountStatisticsCollectorSpec.from_dict(_row_count)

        table_volume_statistics_collectors_spec = cls(
            row_count=row_count,
        )

        table_volume_statistics_collectors_spec.additional_properties = d
        return table_volume_statistics_collectors_spec

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
