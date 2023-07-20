from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..models.collect_statistics_on_table_queue_job_parameters_data_scope import (
    CollectStatisticsOnTableQueueJobParametersDataScope,
)
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.collect_statistics_queue_job_result import (
        CollectStatisticsQueueJobResult,
    )
    from ..models.physical_table_name import PhysicalTableName
    from ..models.statistics_collector_search_filters import (
        StatisticsCollectorSearchFilters,
    )


T = TypeVar("T", bound="CollectStatisticsOnTableQueueJobParameters")


@attr.s(auto_attribs=True)
class CollectStatisticsOnTableQueueJobParameters:
    """
    Attributes:
        connection (Union[Unset, str]): The name of the target connection.
        max_jobs_per_connection (Union[Unset, int]): The maximum number of concurrent 'run checks on table' jobs that
            could be run on this connection. Limits the number of concurrent jobs.
        table (Union[Unset, PhysicalTableName]):
        statistics_collector_search_filters (Union[Unset, StatisticsCollectorSearchFilters]):
        data_scope (Union[Unset, CollectStatisticsOnTableQueueJobParametersDataScope]): The target scope of collecting
            statistics. Statistics could be collected on a whole table or for each data grouping separately.
        dummy_sensor_execution (Union[Unset, bool]): Boolean flag that enables a dummy statistics collection (sensors
            are executed, but the statistics results are not written to the parquet files).
        collect_statistics_result (Union[Unset, CollectStatisticsQueueJobResult]): Returns the result with the summary
            of the statistics collected.
    """

    connection: Union[Unset, str] = UNSET
    max_jobs_per_connection: Union[Unset, int] = UNSET
    table: Union[Unset, "PhysicalTableName"] = UNSET
    statistics_collector_search_filters: Union[
        Unset, "StatisticsCollectorSearchFilters"
    ] = UNSET
    data_scope: Union[
        Unset, CollectStatisticsOnTableQueueJobParametersDataScope
    ] = UNSET
    dummy_sensor_execution: Union[Unset, bool] = UNSET
    collect_statistics_result: Union[Unset, "CollectStatisticsQueueJobResult"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

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
            field_dict["maxJobsPerConnection"] = max_jobs_per_connection
        if table is not UNSET:
            field_dict["table"] = table
        if statistics_collector_search_filters is not UNSET:
            field_dict[
                "statisticsCollectorSearchFilters"
            ] = statistics_collector_search_filters
        if data_scope is not UNSET:
            field_dict["dataScope"] = data_scope
        if dummy_sensor_execution is not UNSET:
            field_dict["dummySensorExecution"] = dummy_sensor_execution
        if collect_statistics_result is not UNSET:
            field_dict["collectStatisticsResult"] = collect_statistics_result

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.collect_statistics_queue_job_result import (
            CollectStatisticsQueueJobResult,
        )
        from ..models.physical_table_name import PhysicalTableName
        from ..models.statistics_collector_search_filters import (
            StatisticsCollectorSearchFilters,
        )

        d = src_dict.copy()
        connection = d.pop("connection", UNSET)

        max_jobs_per_connection = d.pop("maxJobsPerConnection", UNSET)

        _table = d.pop("table", UNSET)
        table: Union[Unset, PhysicalTableName]
        if isinstance(_table, Unset):
            table = UNSET
        else:
            table = PhysicalTableName.from_dict(_table)

        _statistics_collector_search_filters = d.pop(
            "statisticsCollectorSearchFilters", UNSET
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

        _data_scope = d.pop("dataScope", UNSET)
        data_scope: Union[Unset, CollectStatisticsOnTableQueueJobParametersDataScope]
        if isinstance(_data_scope, Unset):
            data_scope = UNSET
        else:
            data_scope = CollectStatisticsOnTableQueueJobParametersDataScope(
                _data_scope
            )

        dummy_sensor_execution = d.pop("dummySensorExecution", UNSET)

        _collect_statistics_result = d.pop("collectStatisticsResult", UNSET)
        collect_statistics_result: Union[Unset, CollectStatisticsQueueJobResult]
        if isinstance(_collect_statistics_result, Unset):
            collect_statistics_result = UNSET
        else:
            collect_statistics_result = CollectStatisticsQueueJobResult.from_dict(
                _collect_statistics_result
            )

        collect_statistics_on_table_queue_job_parameters = cls(
            connection=connection,
            max_jobs_per_connection=max_jobs_per_connection,
            table=table,
            statistics_collector_search_filters=statistics_collector_search_filters,
            data_scope=data_scope,
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
