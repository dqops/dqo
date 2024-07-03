from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.physical_table_name import PhysicalTableName
    from ..models.statistics_collector_search_filters import (
        StatisticsCollectorSearchFilters,
    )
    from ..models.statistics_metric_model import StatisticsMetricModel


T = TypeVar("T", bound="TableStatisticsModel")


@_attrs_define
class TableStatisticsModel:
    """Model that returns a summary of the table level statistics (the basic profiling results).

    Attributes:
        connection_name (Union[Unset, str]): Connection name.
        table (Union[Unset, PhysicalTableName]):
        statistics (Union[Unset, List['StatisticsMetricModel']]): List of collected table level statistics.
        collect_table_statistics_job_template (Union[Unset, StatisticsCollectorSearchFilters]):
        collect_table_and_column_statistics_job_template (Union[Unset, StatisticsCollectorSearchFilters]):
        can_collect_statistics (Union[Unset, bool]): Boolean flag that decides if the current user can collect
            statistics.
    """

    connection_name: Union[Unset, str] = UNSET
    table: Union[Unset, "PhysicalTableName"] = UNSET
    statistics: Union[Unset, List["StatisticsMetricModel"]] = UNSET
    collect_table_statistics_job_template: Union[
        Unset, "StatisticsCollectorSearchFilters"
    ] = UNSET
    collect_table_and_column_statistics_job_template: Union[
        Unset, "StatisticsCollectorSearchFilters"
    ] = UNSET
    can_collect_statistics: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.table, Unset):
            table = self.table.to_dict()

        statistics: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.statistics, Unset):
            statistics = []
            for statistics_item_data in self.statistics:
                statistics_item = statistics_item_data.to_dict()

                statistics.append(statistics_item)

        collect_table_statistics_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.collect_table_statistics_job_template, Unset):
            collect_table_statistics_job_template = (
                self.collect_table_statistics_job_template.to_dict()
            )

        collect_table_and_column_statistics_job_template: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.collect_table_and_column_statistics_job_template, Unset):
            collect_table_and_column_statistics_job_template = (
                self.collect_table_and_column_statistics_job_template.to_dict()
            )

        can_collect_statistics = self.can_collect_statistics

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if table is not UNSET:
            field_dict["table"] = table
        if statistics is not UNSET:
            field_dict["statistics"] = statistics
        if collect_table_statistics_job_template is not UNSET:
            field_dict["collect_table_statistics_job_template"] = (
                collect_table_statistics_job_template
            )
        if collect_table_and_column_statistics_job_template is not UNSET:
            field_dict["collect_table_and_column_statistics_job_template"] = (
                collect_table_and_column_statistics_job_template
            )
        if can_collect_statistics is not UNSET:
            field_dict["can_collect_statistics"] = can_collect_statistics

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.physical_table_name import PhysicalTableName
        from ..models.statistics_collector_search_filters import (
            StatisticsCollectorSearchFilters,
        )
        from ..models.statistics_metric_model import StatisticsMetricModel

        d = src_dict.copy()
        connection_name = d.pop("connection_name", UNSET)

        _table = d.pop("table", UNSET)
        table: Union[Unset, PhysicalTableName]
        if isinstance(_table, Unset):
            table = UNSET
        else:
            table = PhysicalTableName.from_dict(_table)

        statistics = []
        _statistics = d.pop("statistics", UNSET)
        for statistics_item_data in _statistics or []:
            statistics_item = StatisticsMetricModel.from_dict(statistics_item_data)

            statistics.append(statistics_item)

        _collect_table_statistics_job_template = d.pop(
            "collect_table_statistics_job_template", UNSET
        )
        collect_table_statistics_job_template: Union[
            Unset, StatisticsCollectorSearchFilters
        ]
        if isinstance(_collect_table_statistics_job_template, Unset):
            collect_table_statistics_job_template = UNSET
        else:
            collect_table_statistics_job_template = (
                StatisticsCollectorSearchFilters.from_dict(
                    _collect_table_statistics_job_template
                )
            )

        _collect_table_and_column_statistics_job_template = d.pop(
            "collect_table_and_column_statistics_job_template", UNSET
        )
        collect_table_and_column_statistics_job_template: Union[
            Unset, StatisticsCollectorSearchFilters
        ]
        if isinstance(_collect_table_and_column_statistics_job_template, Unset):
            collect_table_and_column_statistics_job_template = UNSET
        else:
            collect_table_and_column_statistics_job_template = (
                StatisticsCollectorSearchFilters.from_dict(
                    _collect_table_and_column_statistics_job_template
                )
            )

        can_collect_statistics = d.pop("can_collect_statistics", UNSET)

        table_statistics_model = cls(
            connection_name=connection_name,
            table=table,
            statistics=statistics,
            collect_table_statistics_job_template=collect_table_statistics_job_template,
            collect_table_and_column_statistics_job_template=collect_table_and_column_statistics_job_template,
            can_collect_statistics=can_collect_statistics,
        )

        table_statistics_model.additional_properties = d
        return table_statistics_model

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
