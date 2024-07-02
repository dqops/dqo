from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_nulls_not_nulls_count_statistics_collector_spec import (
        ColumnNullsNotNullsCountStatisticsCollectorSpec,
    )
    from ..models.column_nulls_not_nulls_percent_statistics_collector_spec import (
        ColumnNullsNotNullsPercentStatisticsCollectorSpec,
    )
    from ..models.column_nulls_nulls_count_statistics_collector_spec import (
        ColumnNullsNullsCountStatisticsCollectorSpec,
    )
    from ..models.column_nulls_nulls_percent_statistics_collector_spec import (
        ColumnNullsNullsPercentStatisticsCollectorSpec,
    )


T = TypeVar("T", bound="ColumnNullsStatisticsCollectorsSpec")


@_attrs_define
class ColumnNullsStatisticsCollectorsSpec:
    """
    Attributes:
        nulls_count (Union[Unset, ColumnNullsNullsCountStatisticsCollectorSpec]):
        nulls_percent (Union[Unset, ColumnNullsNullsPercentStatisticsCollectorSpec]):
        not_nulls_count (Union[Unset, ColumnNullsNotNullsCountStatisticsCollectorSpec]):
        not_nulls_percent (Union[Unset, ColumnNullsNotNullsPercentStatisticsCollectorSpec]):
    """

    nulls_count: Union[Unset, "ColumnNullsNullsCountStatisticsCollectorSpec"] = UNSET
    nulls_percent: Union[Unset, "ColumnNullsNullsPercentStatisticsCollectorSpec"] = (
        UNSET
    )
    not_nulls_count: Union[Unset, "ColumnNullsNotNullsCountStatisticsCollectorSpec"] = (
        UNSET
    )
    not_nulls_percent: Union[
        Unset, "ColumnNullsNotNullsPercentStatisticsCollectorSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.nulls_count, Unset):
            nulls_count = self.nulls_count.to_dict()

        nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.nulls_percent, Unset):
            nulls_percent = self.nulls_percent.to_dict()

        not_nulls_count: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.not_nulls_count, Unset):
            not_nulls_count = self.not_nulls_count.to_dict()

        not_nulls_percent: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.not_nulls_percent, Unset):
            not_nulls_percent = self.not_nulls_percent.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if nulls_count is not UNSET:
            field_dict["nulls_count"] = nulls_count
        if nulls_percent is not UNSET:
            field_dict["nulls_percent"] = nulls_percent
        if not_nulls_count is not UNSET:
            field_dict["not_nulls_count"] = not_nulls_count
        if not_nulls_percent is not UNSET:
            field_dict["not_nulls_percent"] = not_nulls_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_nulls_not_nulls_count_statistics_collector_spec import (
            ColumnNullsNotNullsCountStatisticsCollectorSpec,
        )
        from ..models.column_nulls_not_nulls_percent_statistics_collector_spec import (
            ColumnNullsNotNullsPercentStatisticsCollectorSpec,
        )
        from ..models.column_nulls_nulls_count_statistics_collector_spec import (
            ColumnNullsNullsCountStatisticsCollectorSpec,
        )
        from ..models.column_nulls_nulls_percent_statistics_collector_spec import (
            ColumnNullsNullsPercentStatisticsCollectorSpec,
        )

        d = src_dict.copy()
        _nulls_count = d.pop("nulls_count", UNSET)
        nulls_count: Union[Unset, ColumnNullsNullsCountStatisticsCollectorSpec]
        if isinstance(_nulls_count, Unset):
            nulls_count = UNSET
        else:
            nulls_count = ColumnNullsNullsCountStatisticsCollectorSpec.from_dict(
                _nulls_count
            )

        _nulls_percent = d.pop("nulls_percent", UNSET)
        nulls_percent: Union[Unset, ColumnNullsNullsPercentStatisticsCollectorSpec]
        if isinstance(_nulls_percent, Unset):
            nulls_percent = UNSET
        else:
            nulls_percent = ColumnNullsNullsPercentStatisticsCollectorSpec.from_dict(
                _nulls_percent
            )

        _not_nulls_count = d.pop("not_nulls_count", UNSET)
        not_nulls_count: Union[Unset, ColumnNullsNotNullsCountStatisticsCollectorSpec]
        if isinstance(_not_nulls_count, Unset):
            not_nulls_count = UNSET
        else:
            not_nulls_count = ColumnNullsNotNullsCountStatisticsCollectorSpec.from_dict(
                _not_nulls_count
            )

        _not_nulls_percent = d.pop("not_nulls_percent", UNSET)
        not_nulls_percent: Union[
            Unset, ColumnNullsNotNullsPercentStatisticsCollectorSpec
        ]
        if isinstance(_not_nulls_percent, Unset):
            not_nulls_percent = UNSET
        else:
            not_nulls_percent = (
                ColumnNullsNotNullsPercentStatisticsCollectorSpec.from_dict(
                    _not_nulls_percent
                )
            )

        column_nulls_statistics_collectors_spec = cls(
            nulls_count=nulls_count,
            nulls_percent=nulls_percent,
            not_nulls_count=not_nulls_count,
            not_nulls_percent=not_nulls_percent,
        )

        column_nulls_statistics_collectors_spec.additional_properties = d
        return column_nulls_statistics_collectors_spec

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
