from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_range_max_value_statistics_collector_spec import (
        ColumnRangeMaxValueStatisticsCollectorSpec,
    )
    from ..models.column_range_mean_value_statistics_collector_spec import (
        ColumnRangeMeanValueStatisticsCollectorSpec,
    )
    from ..models.column_range_median_value_statistics_collector_spec import (
        ColumnRangeMedianValueStatisticsCollectorSpec,
    )
    from ..models.column_range_min_value_statistics_collector_spec import (
        ColumnRangeMinValueStatisticsCollectorSpec,
    )
    from ..models.column_range_sum_value_statistics_collector_spec import (
        ColumnRangeSumValueStatisticsCollectorSpec,
    )


T = TypeVar("T", bound="ColumnRangeStatisticsCollectorsSpec")


@_attrs_define
class ColumnRangeStatisticsCollectorsSpec:
    """
    Attributes:
        min_value (Union[Unset, ColumnRangeMinValueStatisticsCollectorSpec]):
        median_value (Union[Unset, ColumnRangeMedianValueStatisticsCollectorSpec]):
        max_value (Union[Unset, ColumnRangeMaxValueStatisticsCollectorSpec]):
        mean_value (Union[Unset, ColumnRangeMeanValueStatisticsCollectorSpec]):
        sum_value (Union[Unset, ColumnRangeSumValueStatisticsCollectorSpec]):
    """

    min_value: Union[Unset, "ColumnRangeMinValueStatisticsCollectorSpec"] = UNSET
    median_value: Union[Unset, "ColumnRangeMedianValueStatisticsCollectorSpec"] = UNSET
    max_value: Union[Unset, "ColumnRangeMaxValueStatisticsCollectorSpec"] = UNSET
    mean_value: Union[Unset, "ColumnRangeMeanValueStatisticsCollectorSpec"] = UNSET
    sum_value: Union[Unset, "ColumnRangeSumValueStatisticsCollectorSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        min_value: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.min_value, Unset):
            min_value = self.min_value.to_dict()

        median_value: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.median_value, Unset):
            median_value = self.median_value.to_dict()

        max_value: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.max_value, Unset):
            max_value = self.max_value.to_dict()

        mean_value: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.mean_value, Unset):
            mean_value = self.mean_value.to_dict()

        sum_value: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.sum_value, Unset):
            sum_value = self.sum_value.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if min_value is not UNSET:
            field_dict["min_value"] = min_value
        if median_value is not UNSET:
            field_dict["median_value"] = median_value
        if max_value is not UNSET:
            field_dict["max_value"] = max_value
        if mean_value is not UNSET:
            field_dict["mean_value"] = mean_value
        if sum_value is not UNSET:
            field_dict["sum_value"] = sum_value

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_range_max_value_statistics_collector_spec import (
            ColumnRangeMaxValueStatisticsCollectorSpec,
        )
        from ..models.column_range_mean_value_statistics_collector_spec import (
            ColumnRangeMeanValueStatisticsCollectorSpec,
        )
        from ..models.column_range_median_value_statistics_collector_spec import (
            ColumnRangeMedianValueStatisticsCollectorSpec,
        )
        from ..models.column_range_min_value_statistics_collector_spec import (
            ColumnRangeMinValueStatisticsCollectorSpec,
        )
        from ..models.column_range_sum_value_statistics_collector_spec import (
            ColumnRangeSumValueStatisticsCollectorSpec,
        )

        d = src_dict.copy()
        _min_value = d.pop("min_value", UNSET)
        min_value: Union[Unset, ColumnRangeMinValueStatisticsCollectorSpec]
        if isinstance(_min_value, Unset):
            min_value = UNSET
        else:
            min_value = ColumnRangeMinValueStatisticsCollectorSpec.from_dict(_min_value)

        _median_value = d.pop("median_value", UNSET)
        median_value: Union[Unset, ColumnRangeMedianValueStatisticsCollectorSpec]
        if isinstance(_median_value, Unset):
            median_value = UNSET
        else:
            median_value = ColumnRangeMedianValueStatisticsCollectorSpec.from_dict(
                _median_value
            )

        _max_value = d.pop("max_value", UNSET)
        max_value: Union[Unset, ColumnRangeMaxValueStatisticsCollectorSpec]
        if isinstance(_max_value, Unset):
            max_value = UNSET
        else:
            max_value = ColumnRangeMaxValueStatisticsCollectorSpec.from_dict(_max_value)

        _mean_value = d.pop("mean_value", UNSET)
        mean_value: Union[Unset, ColumnRangeMeanValueStatisticsCollectorSpec]
        if isinstance(_mean_value, Unset):
            mean_value = UNSET
        else:
            mean_value = ColumnRangeMeanValueStatisticsCollectorSpec.from_dict(
                _mean_value
            )

        _sum_value = d.pop("sum_value", UNSET)
        sum_value: Union[Unset, ColumnRangeSumValueStatisticsCollectorSpec]
        if isinstance(_sum_value, Unset):
            sum_value = UNSET
        else:
            sum_value = ColumnRangeSumValueStatisticsCollectorSpec.from_dict(_sum_value)

        column_range_statistics_collectors_spec = cls(
            min_value=min_value,
            median_value=median_value,
            max_value=max_value,
            mean_value=mean_value,
            sum_value=sum_value,
        )

        column_range_statistics_collectors_spec.additional_properties = d
        return column_range_statistics_collectors_spec

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
