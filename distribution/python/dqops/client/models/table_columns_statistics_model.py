from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_statistics_model import ColumnStatisticsModel
    from ..models.physical_table_name import PhysicalTableName
    from ..models.statistics_collector_search_filters import (
        StatisticsCollectorSearchFilters,
    )


T = TypeVar("T", bound="TableColumnsStatisticsModel")


@_attrs_define
class TableColumnsStatisticsModel:
    """Model that returns a summary of the column statistics (the basic profiling results) for a single table, showing
    statistics for all columns.

        Attributes:
            connection_name (Union[Unset, str]): Connection name.
            table (Union[Unset, PhysicalTableName]):
            column_statistics (Union[Unset, List['ColumnStatisticsModel']]): List of collected column level statistics for
                all columns.
            collect_column_statistics_job_template (Union[Unset, StatisticsCollectorSearchFilters]):
            can_collect_statistics (Union[Unset, bool]): Boolean flag that decides if the current user can collect
                statistics.
    """

    connection_name: Union[Unset, str] = UNSET
    table: Union[Unset, "PhysicalTableName"] = UNSET
    column_statistics: Union[Unset, List["ColumnStatisticsModel"]] = UNSET
    collect_column_statistics_job_template: Union[
        Unset, "StatisticsCollectorSearchFilters"
    ] = UNSET
    can_collect_statistics: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.table, Unset):
            table = self.table.to_dict()

        column_statistics: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.column_statistics, Unset):
            column_statistics = []
            for column_statistics_item_data in self.column_statistics:
                column_statistics_item = column_statistics_item_data.to_dict()

                column_statistics.append(column_statistics_item)

        collect_column_statistics_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.collect_column_statistics_job_template, Unset):
            collect_column_statistics_job_template = (
                self.collect_column_statistics_job_template.to_dict()
            )

        can_collect_statistics = self.can_collect_statistics

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if table is not UNSET:
            field_dict["table"] = table
        if column_statistics is not UNSET:
            field_dict["column_statistics"] = column_statistics
        if collect_column_statistics_job_template is not UNSET:
            field_dict["collect_column_statistics_job_template"] = (
                collect_column_statistics_job_template
            )
        if can_collect_statistics is not UNSET:
            field_dict["can_collect_statistics"] = can_collect_statistics

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_statistics_model import ColumnStatisticsModel
        from ..models.physical_table_name import PhysicalTableName
        from ..models.statistics_collector_search_filters import (
            StatisticsCollectorSearchFilters,
        )

        d = src_dict.copy()
        connection_name = d.pop("connection_name", UNSET)

        _table = d.pop("table", UNSET)
        table: Union[Unset, PhysicalTableName]
        if isinstance(_table, Unset):
            table = UNSET
        else:
            table = PhysicalTableName.from_dict(_table)

        column_statistics = []
        _column_statistics = d.pop("column_statistics", UNSET)
        for column_statistics_item_data in _column_statistics or []:
            column_statistics_item = ColumnStatisticsModel.from_dict(
                column_statistics_item_data
            )

            column_statistics.append(column_statistics_item)

        _collect_column_statistics_job_template = d.pop(
            "collect_column_statistics_job_template", UNSET
        )
        collect_column_statistics_job_template: Union[
            Unset, StatisticsCollectorSearchFilters
        ]
        if isinstance(_collect_column_statistics_job_template, Unset):
            collect_column_statistics_job_template = UNSET
        else:
            collect_column_statistics_job_template = (
                StatisticsCollectorSearchFilters.from_dict(
                    _collect_column_statistics_job_template
                )
            )

        can_collect_statistics = d.pop("can_collect_statistics", UNSET)

        table_columns_statistics_model = cls(
            connection_name=connection_name,
            table=table,
            column_statistics=column_statistics,
            collect_column_statistics_job_template=collect_column_statistics_job_template,
            can_collect_statistics=can_collect_statistics,
        )

        table_columns_statistics_model.additional_properties = d
        return table_columns_statistics_model

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
