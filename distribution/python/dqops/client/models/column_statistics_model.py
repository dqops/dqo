from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.column_type_snapshot_spec import ColumnTypeSnapshotSpec
    from ..models.physical_table_name import PhysicalTableName
    from ..models.statistics_collector_search_filters import (
        StatisticsCollectorSearchFilters,
    )
    from ..models.statistics_metric_model import StatisticsMetricModel


T = TypeVar("T", bound="ColumnStatisticsModel")


@_attrs_define
class ColumnStatisticsModel:
    """Column model that returns the basic fields from a column specification with the additional data statistics.

    Attributes:
        connection_name (Union[Unset, str]): Connection name.
        table (Union[Unset, PhysicalTableName]):
        column_name (Union[Unset, str]): Column name.
        column_hash (Union[Unset, int]): Column hash that identifies the column using a unique hash code.
        disabled (Union[Unset, bool]): Disables all data quality checks on the column. Data quality checks will not be
            executed.
        has_any_configured_checks (Union[Unset, bool]): True when the column has any checks configured.
        type_snapshot (Union[Unset, ColumnTypeSnapshotSpec]):
        statistics (Union[Unset, List['StatisticsMetricModel']]): List of collected column statistics.
        collect_column_statistics_job_template (Union[Unset, StatisticsCollectorSearchFilters]):
        can_collect_statistics (Union[Unset, bool]): Boolean flag that decides if the current user can collect
            statistics.
    """

    connection_name: Union[Unset, str] = UNSET
    table: Union[Unset, "PhysicalTableName"] = UNSET
    column_name: Union[Unset, str] = UNSET
    column_hash: Union[Unset, int] = UNSET
    disabled: Union[Unset, bool] = UNSET
    has_any_configured_checks: Union[Unset, bool] = UNSET
    type_snapshot: Union[Unset, "ColumnTypeSnapshotSpec"] = UNSET
    statistics: Union[Unset, List["StatisticsMetricModel"]] = UNSET
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

        column_name = self.column_name
        column_hash = self.column_hash
        disabled = self.disabled
        has_any_configured_checks = self.has_any_configured_checks
        type_snapshot: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.type_snapshot, Unset):
            type_snapshot = self.type_snapshot.to_dict()

        statistics: Union[Unset, List[Dict[str, Any]]] = UNSET
        if not isinstance(self.statistics, Unset):
            statistics = []
            for statistics_item_data in self.statistics:
                statistics_item = statistics_item_data.to_dict()

                statistics.append(statistics_item)

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
        if column_name is not UNSET:
            field_dict["column_name"] = column_name
        if column_hash is not UNSET:
            field_dict["column_hash"] = column_hash
        if disabled is not UNSET:
            field_dict["disabled"] = disabled
        if has_any_configured_checks is not UNSET:
            field_dict["has_any_configured_checks"] = has_any_configured_checks
        if type_snapshot is not UNSET:
            field_dict["type_snapshot"] = type_snapshot
        if statistics is not UNSET:
            field_dict["statistics"] = statistics
        if collect_column_statistics_job_template is not UNSET:
            field_dict["collect_column_statistics_job_template"] = (
                collect_column_statistics_job_template
            )
        if can_collect_statistics is not UNSET:
            field_dict["can_collect_statistics"] = can_collect_statistics

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.column_type_snapshot_spec import ColumnTypeSnapshotSpec
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

        column_name = d.pop("column_name", UNSET)

        column_hash = d.pop("column_hash", UNSET)

        disabled = d.pop("disabled", UNSET)

        has_any_configured_checks = d.pop("has_any_configured_checks", UNSET)

        _type_snapshot = d.pop("type_snapshot", UNSET)
        type_snapshot: Union[Unset, ColumnTypeSnapshotSpec]
        if isinstance(_type_snapshot, Unset):
            type_snapshot = UNSET
        else:
            type_snapshot = ColumnTypeSnapshotSpec.from_dict(_type_snapshot)

        statistics = []
        _statistics = d.pop("statistics", UNSET)
        for statistics_item_data in _statistics or []:
            statistics_item = StatisticsMetricModel.from_dict(statistics_item_data)

            statistics.append(statistics_item)

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

        column_statistics_model = cls(
            connection_name=connection_name,
            table=table,
            column_name=column_name,
            column_hash=column_hash,
            disabled=disabled,
            has_any_configured_checks=has_any_configured_checks,
            type_snapshot=type_snapshot,
            statistics=statistics,
            collect_column_statistics_job_template=collect_column_statistics_job_template,
            can_collect_statistics=can_collect_statistics,
        )

        column_statistics_model.additional_properties = d
        return column_statistics_model

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
