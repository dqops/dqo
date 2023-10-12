import datetime
from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field
from dateutil.parser import isoparse

from ..models.statistics_result_data_type import StatisticsResultDataType
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.statistics_metric_model_result import StatisticsMetricModelResult


T = TypeVar("T", bound="StatisticsMetricModel")


@_attrs_define
class StatisticsMetricModel:
    """
    Attributes:
        category (Union[Unset, str]): Statistics category
        collector (Union[Unset, str]): Statistics (metric) name
        result_data_type (Union[Unset, StatisticsResultDataType]):
        result (Union[Unset, StatisticsMetricModelResult]): Statistics result for the metric
        collected_at (Union[Unset, datetime.datetime]): The local timestamp when the metric was collected
        sample_count (Union[Unset, int]): The number of the value samples for this result value. Filled only by the
            column value sampling profilers.
        sample_index (Union[Unset, int]): The index of the result that was returned. Filled only by the column value
            sampling profilers to identify each column value sample.
    """

    category: Union[Unset, str] = UNSET
    collector: Union[Unset, str] = UNSET
    result_data_type: Union[Unset, StatisticsResultDataType] = UNSET
    result: Union[Unset, "StatisticsMetricModelResult"] = UNSET
    collected_at: Union[Unset, datetime.datetime] = UNSET
    sample_count: Union[Unset, int] = UNSET
    sample_index: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        category = self.category
        collector = self.collector
        result_data_type: Union[Unset, str] = UNSET
        if not isinstance(self.result_data_type, Unset):
            result_data_type = self.result_data_type.value

        result: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.result, Unset):
            result = self.result.to_dict()

        collected_at: Union[Unset, str] = UNSET
        if not isinstance(self.collected_at, Unset):
            collected_at = self.collected_at.isoformat()

        sample_count = self.sample_count
        sample_index = self.sample_index

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if category is not UNSET:
            field_dict["category"] = category
        if collector is not UNSET:
            field_dict["collector"] = collector
        if result_data_type is not UNSET:
            field_dict["resultDataType"] = result_data_type
        if result is not UNSET:
            field_dict["result"] = result
        if collected_at is not UNSET:
            field_dict["collectedAt"] = collected_at
        if sample_count is not UNSET:
            field_dict["sampleCount"] = sample_count
        if sample_index is not UNSET:
            field_dict["sampleIndex"] = sample_index

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.statistics_metric_model_result import StatisticsMetricModelResult

        d = src_dict.copy()
        category = d.pop("category", UNSET)

        collector = d.pop("collector", UNSET)

        _result_data_type = d.pop("resultDataType", UNSET)
        result_data_type: Union[Unset, StatisticsResultDataType]
        if isinstance(_result_data_type, Unset):
            result_data_type = UNSET
        else:
            result_data_type = StatisticsResultDataType(_result_data_type)

        _result = d.pop("result", UNSET)
        result: Union[Unset, StatisticsMetricModelResult]
        if isinstance(_result, Unset):
            result = UNSET
        else:
            result = StatisticsMetricModelResult.from_dict(_result)

        _collected_at = d.pop("collectedAt", UNSET)
        collected_at: Union[Unset, datetime.datetime]
        if isinstance(_collected_at, Unset):
            collected_at = UNSET
        else:
            collected_at = isoparse(_collected_at)

        sample_count = d.pop("sampleCount", UNSET)

        sample_index = d.pop("sampleIndex", UNSET)

        statistics_metric_model = cls(
            category=category,
            collector=collector,
            result_data_type=result_data_type,
            result=result,
            collected_at=collected_at,
            sample_count=sample_count,
            sample_index=sample_index,
        )

        statistics_metric_model.additional_properties = d
        return statistics_metric_model

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
