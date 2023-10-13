from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_uniqueness_distinct_count_statistics_collector_spec import (
        ColumnUniquenessDistinctCountStatisticsCollectorSpec,
    )
    from ..models.column_uniqueness_distinct_percent_statistics_collector_spec import (
        ColumnUniquenessDistinctPercentStatisticsCollectorSpec,
    )
    from ..models.column_uniqueness_duplicate_count_statistics_collector_spec import (
        ColumnUniquenessDuplicateCountStatisticsCollectorSpec,
    )
    from ..models.column_uniqueness_duplicate_percent_statistics_collector_spec import (
        ColumnUniquenessDuplicatePercentStatisticsCollectorSpec,
    )


T = TypeVar("T", bound="ColumnUniquenessStatisticsCollectorsSpec")


@_attrs_define
class ColumnUniquenessStatisticsCollectorsSpec:
    """
    Attributes:
        distinct_count (Union[Unset, ColumnUniquenessDistinctCountStatisticsCollectorSpec]):
        distinct_percent (Union[Unset, ColumnUniquenessDistinctPercentStatisticsCollectorSpec]):
        duplicate_count (Union[Unset, ColumnUniquenessDuplicateCountStatisticsCollectorSpec]):
        duplicate_percent (Union[Unset, ColumnUniquenessDuplicatePercentStatisticsCollectorSpec]):
    """

    distinct_count: Union[
        Unset, "ColumnUniquenessDistinctCountStatisticsCollectorSpec"
    ] = UNSET
    distinct_percent: Union[
        Unset, "ColumnUniquenessDistinctPercentStatisticsCollectorSpec"
    ] = UNSET
    duplicate_count: Union[
        Unset, "ColumnUniquenessDuplicateCountStatisticsCollectorSpec"
    ] = UNSET
    duplicate_percent: Union[
        Unset, "ColumnUniquenessDuplicatePercentStatisticsCollectorSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        distinct_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.distinct_count, Unset):
            distinct_count = self.distinct_count.to_dict()

        distinct_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.distinct_percent, Unset):
            distinct_percent = self.distinct_percent.to_dict()

        duplicate_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.duplicate_count, Unset):
            duplicate_count = self.duplicate_count.to_dict()

        duplicate_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.duplicate_percent, Unset):
            duplicate_percent = self.duplicate_percent.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if distinct_count is not UNSET:
            field_dict["distinct_count"] = distinct_count
        if distinct_percent is not UNSET:
            field_dict["distinct_percent"] = distinct_percent
        if duplicate_count is not UNSET:
            field_dict["duplicate_count"] = duplicate_count
        if duplicate_percent is not UNSET:
            field_dict["duplicate_percent"] = duplicate_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_uniqueness_distinct_count_statistics_collector_spec import (
            ColumnUniquenessDistinctCountStatisticsCollectorSpec,
        )
        from ..models.column_uniqueness_distinct_percent_statistics_collector_spec import (
            ColumnUniquenessDistinctPercentStatisticsCollectorSpec,
        )
        from ..models.column_uniqueness_duplicate_count_statistics_collector_spec import (
            ColumnUniquenessDuplicateCountStatisticsCollectorSpec,
        )
        from ..models.column_uniqueness_duplicate_percent_statistics_collector_spec import (
            ColumnUniquenessDuplicatePercentStatisticsCollectorSpec,
        )

        d = src_dict.copy()
        _distinct_count = d.pop("distinct_count", UNSET)
        distinct_count: Union[
            Unset, ColumnUniquenessDistinctCountStatisticsCollectorSpec
        ]
        if isinstance(_distinct_count, Unset):
            distinct_count = UNSET
        else:
            distinct_count = (
                ColumnUniquenessDistinctCountStatisticsCollectorSpec.from_dict(
                    _distinct_count
                )
            )

        _distinct_percent = d.pop("distinct_percent", UNSET)
        distinct_percent: Union[
            Unset, ColumnUniquenessDistinctPercentStatisticsCollectorSpec
        ]
        if isinstance(_distinct_percent, Unset):
            distinct_percent = UNSET
        else:
            distinct_percent = (
                ColumnUniquenessDistinctPercentStatisticsCollectorSpec.from_dict(
                    _distinct_percent
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
            distinct_count=distinct_count,
            distinct_percent=distinct_percent,
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
