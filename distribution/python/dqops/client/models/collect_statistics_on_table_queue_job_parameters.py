from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.statistics_data_scope import StatisticsDataScope
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.collect_statistics_result import CollectStatisticsResult
    from ..models.physical_table_name import PhysicalTableName
    from ..models.statistics_collector_search_filters import (
        StatisticsCollectorSearchFilters,
    )


T = TypeVar("T", bound="CollectStatisticsOnTableQueueJobParameters")


@_attrs_define
class CollectStatisticsOnTableQueueJobParameters:
    """
    Attributes:
        connection (Union[Unset, str]): The name of the target connection.
        max_jobs_per_connection (Union[Unset, int]): The maximum number of concurrent 'run checks on table' jobs that
            can be run on this connection. Limits the number of concurrent jobs.
        table (Union[Unset, PhysicalTableName]):
        statistics_collector_search_filters (Union[Unset, StatisticsCollectorSearchFilters]):
        data_scope (Union[Unset, StatisticsDataScope]):
        samples_limit (Union[Unset, int]): The default limit of column samples that are collected.
        configure_table (Union[Unset, bool]): Turns on a special mode of collecting statistics that will configure the
            timestamp and ID columns. It should be used only during the first statistics collection.
        dummy_sensor_execution (Union[Unset, bool]): Boolean flag that enables a dummy statistics collection (sensors
            are executed, but the statistics results are not written to the parquet files).
        collect_statistics_result (Union[Unset, CollectStatisticsResult]): Returns the result with the summary of the
            statistics collected.
    """

    connection: Union[Unset, str] = UNSET
    max_jobs_per_connection: Union[Unset, int] = UNSET
    table: Union[Unset, "PhysicalTableName"] = UNSET
    statistics_collector_search_filters: Union[
        Unset, "StatisticsCollectorSearchFilters"
    ] = UNSET
    data_scope: Union[Unset, StatisticsDataScope] = UNSET
    samples_limit: Union[Unset, int] = UNSET
    configure_table: Union[Unset, bool] = UNSET
    dummy_sensor_execution: Union[Unset, bool] = UNSET
    collect_statistics_result: Union[Unset, "CollectStatisticsResult"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection = self.connection
        max_jobs_per_connection = self.max_jobs_per_connection
        table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.table, Unset):
            table = self.table.to_dict()

        statistics_collector_search_filters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.statistics_collector_search_filters, Unset):
            statistics_collector_search_filters = (
                self.statistics_collector_search_filters.to_dict()
            )

        data_scope: Union[Unset, str] = UNSET
        if not isinstance(self.data_scope, Unset):
            data_scope = self.data_scope.value

        samples_limit = self.samples_limit
        configure_table = self.configure_table
        dummy_sensor_execution = self.dummy_sensor_execution
        collect_statistics_result: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.collect_statistics_result, Unset):
            collect_statistics_result = self.collect_statistics_result.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection is not UNSET:
            field_dict["connection"] = connection
        if max_jobs_per_connection is not UNSET:
            field_dict["max_jobs_per_connection"] = max_jobs_per_connection
        if table is not UNSET:
            field_dict["table"] = table
        if statistics_collector_search_filters is not UNSET:
            field_dict["statistics_collector_search_filters"] = (
                statistics_collector_search_filters
            )
        if data_scope is not UNSET:
            field_dict["data_scope"] = data_scope
        if samples_limit is not UNSET:
            field_dict["samples_limit"] = samples_limit
        if configure_table is not UNSET:
            field_dict["configure_table"] = configure_table
        if dummy_sensor_execution is not UNSET:
            field_dict["dummy_sensor_execution"] = dummy_sensor_execution
        if collect_statistics_result is not UNSET:
            field_dict["collect_statistics_result"] = collect_statistics_result

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.collect_statistics_result import CollectStatisticsResult
        from ..models.physical_table_name import PhysicalTableName
        from ..models.statistics_collector_search_filters import (
            StatisticsCollectorSearchFilters,
        )

        d = src_dict.copy()
        connection = d.pop("connection", UNSET)

        max_jobs_per_connection = d.pop("max_jobs_per_connection", UNSET)

        _table = d.pop("table", UNSET)
        table: Union[Unset, PhysicalTableName]
        if isinstance(_table, Unset):
            table = UNSET
        else:
            table = PhysicalTableName.from_dict(_table)

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

        samples_limit = d.pop("samples_limit", UNSET)

        configure_table = d.pop("configure_table", UNSET)

        dummy_sensor_execution = d.pop("dummy_sensor_execution", UNSET)

        _collect_statistics_result = d.pop("collect_statistics_result", UNSET)
        collect_statistics_result: Union[Unset, CollectStatisticsResult]
        if isinstance(_collect_statistics_result, Unset):
            collect_statistics_result = UNSET
        else:
            collect_statistics_result = CollectStatisticsResult.from_dict(
                _collect_statistics_result
            )

        collect_statistics_on_table_queue_job_parameters = cls(
            connection=connection,
            max_jobs_per_connection=max_jobs_per_connection,
            table=table,
            statistics_collector_search_filters=statistics_collector_search_filters,
            data_scope=data_scope,
            samples_limit=samples_limit,
            configure_table=configure_table,
            dummy_sensor_execution=dummy_sensor_execution,
            collect_statistics_result=collect_statistics_result,
        )

        collect_statistics_on_table_queue_job_parameters.additional_properties = d
        return collect_statistics_on_table_queue_job_parameters

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
