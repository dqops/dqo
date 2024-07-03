from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.statistics_data_scope import StatisticsDataScope
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.collect_statistics_result import CollectStatisticsResult
    from ..models.statistics_collector_search_filters import (
        StatisticsCollectorSearchFilters,
    )


T = TypeVar("T", bound="CollectStatisticsQueueJobParameters")


@_attrs_define
class CollectStatisticsQueueJobParameters:
    """
    Attributes:
        statistics_collector_search_filters (Union[Unset, StatisticsCollectorSearchFilters]):
        data_scope (Union[Unset, StatisticsDataScope]):
        dummy_sensor_execution (Union[Unset, bool]): Boolean flag that enables a dummy statistics collection (sensors
            are executed, but the statistics results are not written to the parquet files).
        collect_statistics_result (Union[Unset, CollectStatisticsResult]): Returns the result with the summary of the
            statistics collected.
    """

    statistics_collector_search_filters: Union[
        Unset, "StatisticsCollectorSearchFilters"
    ] = UNSET
    data_scope: Union[Unset, StatisticsDataScope] = UNSET
    dummy_sensor_execution: Union[Unset, bool] = UNSET
    collect_statistics_result: Union[Unset, "CollectStatisticsResult"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        statistics_collector_search_filters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.statistics_collector_search_filters, Unset):
            statistics_collector_search_filters = (
                self.statistics_collector_search_filters.to_dict()
            )

        data_scope: Union[Unset, str] = UNSET
        if not isinstance(self.data_scope, Unset):
            data_scope = self.data_scope.value

        dummy_sensor_execution = self.dummy_sensor_execution
        collect_statistics_result: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.collect_statistics_result, Unset):
            collect_statistics_result = self.collect_statistics_result.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if statistics_collector_search_filters is not UNSET:
            field_dict["statistics_collector_search_filters"] = (
                statistics_collector_search_filters
            )
        if data_scope is not UNSET:
            field_dict["data_scope"] = data_scope
        if dummy_sensor_execution is not UNSET:
            field_dict["dummy_sensor_execution"] = dummy_sensor_execution
        if collect_statistics_result is not UNSET:
            field_dict["collect_statistics_result"] = collect_statistics_result

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.collect_statistics_result import CollectStatisticsResult
        from ..models.statistics_collector_search_filters import (
            StatisticsCollectorSearchFilters,
        )

        d = src_dict.copy()
        _statistics_collector_search_filters = d.pop(
            "statistics_collector_search_filters", UNSET
        )
        statistics_collector_search_filters: Union[
            Unset, StatisticsCollectorSearchFilters
        ]
        if isinstance(_statistics_collector_search_filters, Unset):
            statistics_collector_search_filters = UNSET
        else:
            statistics_collector_search_filters = (
                StatisticsCollectorSearchFilters.from_dict(
                    _statistics_collector_search_filters
                )
            )

        _data_scope = d.pop("data_scope", UNSET)
        data_scope: Union[Unset, StatisticsDataScope]
        if isinstance(_data_scope, Unset):
            data_scope = UNSET
        else:
            data_scope = StatisticsDataScope(_data_scope)

        dummy_sensor_execution = d.pop("dummy_sensor_execution", UNSET)

        _collect_statistics_result = d.pop("collect_statistics_result", UNSET)
        collect_statistics_result: Union[Unset, CollectStatisticsResult]
        if isinstance(_collect_statistics_result, Unset):
            collect_statistics_result = UNSET
        else:
            collect_statistics_result = CollectStatisticsResult.from_dict(
                _collect_statistics_result
            )

        collect_statistics_queue_job_parameters = cls(
            statistics_collector_search_filters=statistics_collector_search_filters,
            data_scope=data_scope,
            dummy_sensor_execution=dummy_sensor_execution,
            collect_statistics_result=collect_statistics_result,
        )

        collect_statistics_queue_job_parameters.additional_properties = d
        return collect_statistics_queue_job_parameters

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
