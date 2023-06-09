from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_uniqueness_duplicate_count_statistics_collector_spec import (
        ColumnUniquenessDuplicateCountStatisticsCollectorSpec,
    )
    from ..models.column_uniqueness_duplicate_percent_statistics_collector_spec import (
        ColumnUniquenessDuplicatePercentStatisticsCollectorSpec,
    )
    from ..models.column_uniqueness_unique_count_statistics_collector_spec import (
        ColumnUniquenessUniqueCountStatisticsCollectorSpec,
    )
    from ..models.column_uniqueness_unique_percent_statistics_collector_spec import (
        ColumnUniquenessUniquePercentStatisticsCollectorSpec,
    )


T = TypeVar("T", bound="ColumnUniquenessStatisticsCollectorsSpec")


@attr.s(auto_attribs=True)
class ColumnUniquenessStatisticsCollectorsSpec:
    """
    Attributes:
        unique_count (Union[Unset, ColumnUniquenessUniqueCountStatisticsCollectorSpec]):
        unique_percent (Union[Unset, ColumnUniquenessUniquePercentStatisticsCollectorSpec]):
        duplicate_count (Union[Unset, ColumnUniquenessDuplicateCountStatisticsCollectorSpec]):
        duplicate_percent (Union[Unset, ColumnUniquenessDuplicatePercentStatisticsCollectorSpec]):
    """

    unique_count: Union[
        Unset, "ColumnUniquenessUniqueCountStatisticsCollectorSpec"
    ] = UNSET
    unique_percent: Union[
        Unset, "ColumnUniquenessUniquePercentStatisticsCollectorSpec"
    ] = UNSET
    duplicate_count: Union[
        Unset, "ColumnUniquenessDuplicateCountStatisticsCollectorSpec"
    ] = UNSET
    duplicate_percent: Union[
        Unset, "ColumnUniquenessDuplicatePercentStatisticsCollectorSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        unique_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.unique_count, Unset):
            unique_count = self.unique_count.to_dict()

        unique_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.unique_percent, Unset):
            unique_percent = self.unique_percent.to_dict()

        duplicate_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.duplicate_count, Unset):
            duplicate_count = self.duplicate_count.to_dict()

        duplicate_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.duplicate_percent, Unset):
            duplicate_percent = self.duplicate_percent.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if unique_count is not UNSET:
            field_dict["unique_count"] = unique_count
        if unique_percent is not UNSET:
            field_dict["unique_percent"] = unique_percent
        if duplicate_count is not UNSET:
            field_dict["duplicate_count"] = duplicate_count
        if duplicate_percent is not UNSET:
            field_dict["duplicate_percent"] = duplicate_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_uniqueness_duplicate_count_statistics_collector_spec import (
            ColumnUniquenessDuplicateCountStatisticsCollectorSpec,
        )
        from ..models.column_uniqueness_duplicate_percent_statistics_collector_spec import (
            ColumnUniquenessDuplicatePercentStatisticsCollectorSpec,
        )
        from ..models.column_uniqueness_unique_count_statistics_collector_spec import (
            ColumnUniquenessUniqueCountStatisticsCollectorSpec,
        )
        from ..models.column_uniqueness_unique_percent_statistics_collector_spec import (
            ColumnUniquenessUniquePercentStatisticsCollectorSpec,
        )

        d = src_dict.copy()
        _unique_count = d.pop("unique_count", UNSET)
        unique_count: Union[Unset, ColumnUniquenessUniqueCountStatisticsCollectorSpec]
        if isinstance(_unique_count, Unset):
            unique_count = UNSET
        else:
            unique_count = ColumnUniquenessUniqueCountStatisticsCollectorSpec.from_dict(
                _unique_count
            )

        _unique_percent = d.pop("unique_percent", UNSET)
        unique_percent: Union[
            Unset, ColumnUniquenessUniquePercentStatisticsCollectorSpec
        ]
        if isinstance(_unique_percent, Unset):
            unique_percent = UNSET
        else:
            unique_percent = (
                ColumnUniquenessUniquePercentStatisticsCollectorSpec.from_dict(
                    _unique_percent
                )
            )

        _duplicate_count = d.pop("duplicate_count", UNSET)
        duplicate_count: Union[
            Unset, ColumnUniquenessDuplicateCountStatisticsCollectorSpec
        ]
        if isinstance(_duplicate_count, Unset):
            duplicate_count = UNSET
        else:
            duplicate_count = (
                ColumnUniquenessDuplicateCountStatisticsCollectorSpec.from_dict(
                    _duplicate_count
                )
            )

        _duplicate_percent = d.pop("duplicate_percent", UNSET)
        duplicate_percent: Union[
            Unset, ColumnUniquenessDuplicatePercentStatisticsCollectorSpec
        ]
        if isinstance(_duplicate_percent, Unset):
            duplicate_percent = UNSET
        else:
            duplicate_percent = (
                ColumnUniquenessDuplicatePercentStatisticsCollectorSpec.from_dict(
                    _duplicate_percent
                )
            )

        column_uniqueness_statistics_collectors_spec = cls(
            unique_count=unique_count,
            unique_percent=unique_percent,
            duplicate_count=duplicate_count,
            duplicate_percent=duplicate_percent,
        )

        column_uniqueness_statistics_collectors_spec.additional_properties = d
        return column_uniqueness_statistics_collectors_spec

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
