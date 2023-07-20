from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_volume_statistics_collectors_spec import (
        TableVolumeStatisticsCollectorsSpec,
    )


T = TypeVar("T", bound="TableStatisticsCollectorsRootCategoriesSpec")


@attr.s(auto_attribs=True)
class TableStatisticsCollectorsRootCategoriesSpec:
    """
    Attributes:
        volume (Union[Unset, TableVolumeStatisticsCollectorsSpec]):
    """

    volume: Union[Unset, "TableVolumeStatisticsCollectorsSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        volume: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.volume, Unset):
            volume = self.volume.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if volume is not UNSET:
            field_dict["volume"] = volume

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
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

        table_statistics_collectors_root_categories_spec = cls(
            volume=volume,
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
