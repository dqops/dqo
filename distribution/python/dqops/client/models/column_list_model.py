from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_search_filters import CheckSearchFilters
    from ..models.column_type_snapshot_spec import ColumnTypeSnapshotSpec
    from ..models.delete_stored_data_queue_job_parameters import (
        DeleteStoredDataQueueJobParameters,
    )
    from ..models.physical_table_name import PhysicalTableName
    from ..models.statistics_collector_search_filters import (
        StatisticsCollectorSearchFilters,
    )


T = TypeVar("T", bound="ColumnListModel")


@_attrs_define
class ColumnListModel:
    """Column list model that returns the basic fields from a column specification, excluding nested nodes like a list of
    activated checks.

        Attributes:
            connection_name (Union[Unset, str]): Connection name.
            table (Union[Unset, PhysicalTableName]):
            column_name (Union[Unset, str]): Column names.
            sql_expression (Union[Unset, str]): SQL expression.
            column_hash (Union[Unset, int]): Column hash that identifies the column using a unique hash code.
            disabled (Union[Unset, bool]): Disables all data quality checks on the column. Data quality checks will not be
                executed.
            has_any_configured_checks (Union[Unset, bool]): True when the column has any checks configured.
            has_any_configured_profiling_checks (Union[Unset, bool]): True when the column has any profiling checks
                configured.
            has_any_configured_monitoring_checks (Union[Unset, bool]): True when the column has any monitoring checks
                configured.
            has_any_configured_partition_checks (Union[Unset, bool]): True when the column has any partition checks
                configured.
            type_snapshot (Union[Unset, ColumnTypeSnapshotSpec]):
            run_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter, identifies which
                checks on which tables and columns should be executed.
            run_profiling_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter,
                identifies which checks on which tables and columns should be executed.
            run_monitoring_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter,
                identifies which checks on which tables and columns should be executed.
            run_partition_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter,
                identifies which checks on which tables and columns should be executed.
            collect_statistics_job_template (Union[Unset, StatisticsCollectorSearchFilters]):
            data_clean_job_template (Union[Unset, DeleteStoredDataQueueJobParameters]):
            can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete the column.
            can_collect_statistics (Union[Unset, bool]): Boolean flag that decides if the current user can collect
                statistics.
            can_run_checks (Union[Unset, bool]): Boolean flag that decides if the current user can run checks.
            can_delete_data (Union[Unset, bool]): Boolean flag that decides if the current user can delete data (results).
    """

    connection_name: Union[Unset, str] = UNSET
    table: Union[Unset, "PhysicalTableName"] = UNSET
    column_name: Union[Unset, str] = UNSET
    sql_expression: Union[Unset, str] = UNSET
    column_hash: Union[Unset, int] = UNSET
    disabled: Union[Unset, bool] = UNSET
    has_any_configured_checks: Union[Unset, bool] = UNSET
    has_any_configured_profiling_checks: Union[Unset, bool] = UNSET
    has_any_configured_monitoring_checks: Union[Unset, bool] = UNSET
    has_any_configured_partition_checks: Union[Unset, bool] = UNSET
    type_snapshot: Union[Unset, "ColumnTypeSnapshotSpec"] = UNSET
    run_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_profiling_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_monitoring_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_partition_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    collect_statistics_job_template: Union[
        Unset, "StatisticsCollectorSearchFilters"
    ] = UNSET
    data_clean_job_template: Union[Unset, "DeleteStoredDataQueueJobParameters"] = UNSET
    can_edit: Union[Unset, bool] = UNSET
    can_collect_statistics: Union[Unset, bool] = UNSET
    can_run_checks: Union[Unset, bool] = UNSET
    can_delete_data: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        table: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.table, Unset):
            table = self.table.to_dict()

        column_name = self.column_name
        sql_expression = self.sql_expression
        column_hash = self.column_hash
        disabled = self.disabled
        has_any_configured_checks = self.has_any_configured_checks
        has_any_configured_profiling_checks = self.has_any_configured_profiling_checks
        has_any_configured_monitoring_checks = self.has_any_configured_monitoring_checks
        has_any_configured_partition_checks = self.has_any_configured_partition_checks
        type_snapshot: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.type_snapshot, Unset):
            type_snapshot = self.type_snapshot.to_dict()

        run_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_checks_job_template, Unset):
            run_checks_job_template = self.run_checks_job_template.to_dict()

        run_profiling_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_profiling_checks_job_template, Unset):
            run_profiling_checks_job_template = (
                self.run_profiling_checks_job_template.to_dict()
            )

        run_monitoring_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_monitoring_checks_job_template, Unset):
            run_monitoring_checks_job_template = (
                self.run_monitoring_checks_job_template.to_dict()
            )

        run_partition_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_partition_checks_job_template, Unset):
            run_partition_checks_job_template = (
                self.run_partition_checks_job_template.to_dict()
            )

        collect_statistics_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.collect_statistics_job_template, Unset):
            collect_statistics_job_template = (
                self.collect_statistics_job_template.to_dict()
            )

        data_clean_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_clean_job_template, Unset):
            data_clean_job_template = self.data_clean_job_template.to_dict()

        can_edit = self.can_edit
        can_collect_statistics = self.can_collect_statistics
        can_run_checks = self.can_run_checks
        can_delete_data = self.can_delete_data

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if table is not UNSET:
            field_dict["table"] = table
        if column_name is not UNSET:
            field_dict["column_name"] = column_name
        if sql_expression is not UNSET:
            field_dict["sql_expression"] = sql_expression
        if column_hash is not UNSET:
            field_dict["column_hash"] = column_hash
        if disabled is not UNSET:
            field_dict["disabled"] = disabled
        if has_any_configured_checks is not UNSET:
            field_dict["has_any_configured_checks"] = has_any_configured_checks
        if has_any_configured_profiling_checks is not UNSET:
            field_dict[
                "has_any_configured_profiling_checks"
            ] = has_any_configured_profiling_checks
        if has_any_configured_monitoring_checks is not UNSET:
            field_dict[
                "has_any_configured_monitoring_checks"
            ] = has_any_configured_monitoring_checks
        if has_any_configured_partition_checks is not UNSET:
            field_dict[
                "has_any_configured_partition_checks"
            ] = has_any_configured_partition_checks
        if type_snapshot is not UNSET:
            field_dict["type_snapshot"] = type_snapshot
        if run_checks_job_template is not UNSET:
            field_dict["run_checks_job_template"] = run_checks_job_template
        if run_profiling_checks_job_template is not UNSET:
            field_dict[
                "run_profiling_checks_job_template"
            ] = run_profiling_checks_job_template
        if run_monitoring_checks_job_template is not UNSET:
            field_dict[
                "run_monitoring_checks_job_template"
            ] = run_monitoring_checks_job_template
        if run_partition_checks_job_template is not UNSET:
            field_dict[
                "run_partition_checks_job_template"
            ] = run_partition_checks_job_template
        if collect_statistics_job_template is not UNSET:
            field_dict[
                "collect_statistics_job_template"
            ] = collect_statistics_job_template
        if data_clean_job_template is not UNSET:
            field_dict["data_clean_job_template"] = data_clean_job_template
        if can_edit is not UNSET:
            field_dict["can_edit"] = can_edit
        if can_collect_statistics is not UNSET:
            field_dict["can_collect_statistics"] = can_collect_statistics
        if can_run_checks is not UNSET:
            field_dict["can_run_checks"] = can_run_checks
        if can_delete_data is not UNSET:
            field_dict["can_delete_data"] = can_delete_data

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_search_filters import CheckSearchFilters
        from ..models.column_type_snapshot_spec import ColumnTypeSnapshotSpec
        from ..models.delete_stored_data_queue_job_parameters import (
            DeleteStoredDataQueueJobParameters,
        )
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

        column_name = d.pop("column_name", UNSET)

        sql_expression = d.pop("sql_expression", UNSET)

        column_hash = d.pop("column_hash", UNSET)

        disabled = d.pop("disabled", UNSET)

        has_any_configured_checks = d.pop("has_any_configured_checks", UNSET)

        has_any_configured_profiling_checks = d.pop(
            "has_any_configured_profiling_checks", UNSET
        )

        has_any_configured_monitoring_checks = d.pop(
            "has_any_configured_monitoring_checks", UNSET
        )

        has_any_configured_partition_checks = d.pop(
            "has_any_configured_partition_checks", UNSET
        )

        _type_snapshot = d.pop("type_snapshot", UNSET)
        type_snapshot: Union[Unset, ColumnTypeSnapshotSpec]
        if isinstance(_type_snapshot, Unset):
            type_snapshot = UNSET
        else:
            type_snapshot = ColumnTypeSnapshotSpec.from_dict(_type_snapshot)

        _run_checks_job_template = d.pop("run_checks_job_template", UNSET)
        run_checks_job_template: Union[Unset, CheckSearchFilters]
        if isinstance(_run_checks_job_template, Unset):
            run_checks_job_template = UNSET
        else:
            run_checks_job_template = CheckSearchFilters.from_dict(
                _run_checks_job_template
            )

        _run_profiling_checks_job_template = d.pop(
            "run_profiling_checks_job_template", UNSET
        )
        run_profiling_checks_job_template: Union[Unset, CheckSearchFilters]
        if isinstance(_run_profiling_checks_job_template, Unset):
            run_profiling_checks_job_template = UNSET
        else:
            run_profiling_checks_job_template = CheckSearchFilters.from_dict(
                _run_profiling_checks_job_template
            )

        _run_monitoring_checks_job_template = d.pop(
            "run_monitoring_checks_job_template", UNSET
        )
        run_monitoring_checks_job_template: Union[Unset, CheckSearchFilters]
        if isinstance(_run_monitoring_checks_job_template, Unset):
            run_monitoring_checks_job_template = UNSET
        else:
            run_monitoring_checks_job_template = CheckSearchFilters.from_dict(
                _run_monitoring_checks_job_template
            )

        _run_partition_checks_job_template = d.pop(
            "run_partition_checks_job_template", UNSET
        )
        run_partition_checks_job_template: Union[Unset, CheckSearchFilters]
        if isinstance(_run_partition_checks_job_template, Unset):
            run_partition_checks_job_template = UNSET
        else:
            run_partition_checks_job_template = CheckSearchFilters.from_dict(
                _run_partition_checks_job_template
            )

        _collect_statistics_job_template = d.pop(
            "collect_statistics_job_template", UNSET
        )
        collect_statistics_job_template: Union[Unset, StatisticsCollectorSearchFilters]
        if isinstance(_collect_statistics_job_template, Unset):
            collect_statistics_job_template = UNSET
        else:
            collect_statistics_job_template = (
                StatisticsCollectorSearchFilters.from_dict(
                    _collect_statistics_job_template
                )
            )

        _data_clean_job_template = d.pop("data_clean_job_template", UNSET)
        data_clean_job_template: Union[Unset, DeleteStoredDataQueueJobParameters]
        if isinstance(_data_clean_job_template, Unset):
            data_clean_job_template = UNSET
        else:
            data_clean_job_template = DeleteStoredDataQueueJobParameters.from_dict(
                _data_clean_job_template
            )

        can_edit = d.pop("can_edit", UNSET)

        can_collect_statistics = d.pop("can_collect_statistics", UNSET)

        can_run_checks = d.pop("can_run_checks", UNSET)

        can_delete_data = d.pop("can_delete_data", UNSET)

        column_list_model = cls(
            connection_name=connection_name,
            table=table,
            column_name=column_name,
            sql_expression=sql_expression,
            column_hash=column_hash,
            disabled=disabled,
            has_any_configured_checks=has_any_configured_checks,
            has_any_configured_profiling_checks=has_any_configured_profiling_checks,
            has_any_configured_monitoring_checks=has_any_configured_monitoring_checks,
            has_any_configured_partition_checks=has_any_configured_partition_checks,
            type_snapshot=type_snapshot,
            run_checks_job_template=run_checks_job_template,
            run_profiling_checks_job_template=run_profiling_checks_job_template,
            run_monitoring_checks_job_template=run_monitoring_checks_job_template,
            run_partition_checks_job_template=run_partition_checks_job_template,
            collect_statistics_job_template=collect_statistics_job_template,
            data_clean_job_template=data_clean_job_template,
            can_edit=can_edit,
            can_collect_statistics=can_collect_statistics,
            can_run_checks=can_run_checks,
            can_delete_data=can_delete_data,
        )

        column_list_model.additional_properties = d
        return column_list_model

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
