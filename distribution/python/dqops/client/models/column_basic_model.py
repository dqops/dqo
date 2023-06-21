from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

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


T = TypeVar("T", bound="ColumnBasicModel")


@attr.s(auto_attribs=True)
class ColumnBasicModel:
    """Basic column model that returns the basic fields from a column specification, excluding nested nodes like a list of
    activated checks.

        Attributes:
            connection_name (Union[Unset, str]): Connection name.
            table (Union[Unset, PhysicalTableName]):
            column_name (Union[Unset, str]): Column name.
            sql_expression (Union[Unset, str]): SQL expression.
            column_hash (Union[Unset, int]): Column hash that identifies the column using a unique hash code.
            disabled (Union[Unset, bool]): Disables all data quality checks on the column. Data quality checks will not be
                executed.
            has_any_configured_checks (Union[Unset, bool]): True when the column has any checks configured.
            has_any_configured_profiling_checks (Union[Unset, bool]): True when the column has any profiling checks
                configured.
            has_any_configured_recurring_checks (Union[Unset, bool]): True when the column has any recurring checks
                configured.
            has_any_configured_partition_checks (Union[Unset, bool]): True when the column has any partition checks
                configured.
            type_snapshot (Union[Unset, ColumnTypeSnapshotSpec]):
            run_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter, identifies which
                checks on which tables and columns should be executed.
            run_profiling_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter,
                identifies which checks on which tables and columns should be executed.
            run_recurring_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter,
                identifies which checks on which tables and columns should be executed.
            run_partition_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter,
                identifies which checks on which tables and columns should be executed.
            collect_statistics_job_template (Union[Unset, StatisticsCollectorSearchFilters]):
            data_clean_job_template (Union[Unset, DeleteStoredDataQueueJobParameters]):
    """

    connection_name: Union[Unset, str] = UNSET
    table: Union[Unset, "PhysicalTableName"] = UNSET
    column_name: Union[Unset, str] = UNSET
    sql_expression: Union[Unset, str] = UNSET
    column_hash: Union[Unset, int] = UNSET
    disabled: Union[Unset, bool] = UNSET
    has_any_configured_checks: Union[Unset, bool] = UNSET
    has_any_configured_profiling_checks: Union[Unset, bool] = UNSET
    has_any_configured_recurring_checks: Union[Unset, bool] = UNSET
    has_any_configured_partition_checks: Union[Unset, bool] = UNSET
    type_snapshot: Union[Unset, "ColumnTypeSnapshotSpec"] = UNSET
    run_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_profiling_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_recurring_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_partition_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    collect_statistics_job_template: Union[
        Unset, "StatisticsCollectorSearchFilters"
    ] = UNSET
    data_clean_job_template: Union[Unset, "DeleteStoredDataQueueJobParameters"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

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
        has_any_configured_recurring_checks = self.has_any_configured_recurring_checks
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

        run_recurring_checks_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_recurring_checks_job_template, Unset):
            run_recurring_checks_job_template = (
                self.run_recurring_checks_job_template.to_dict()
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
        if has_any_configured_recurring_checks is not UNSET:
            field_dict[
                "has_any_configured_recurring_checks"
            ] = has_any_configured_recurring_checks
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
        if run_recurring_checks_job_template is not UNSET:
            field_dict[
                "run_recurring_checks_job_template"
            ] = run_recurring_checks_job_template
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

        has_any_configured_recurring_checks = d.pop(
            "has_any_configured_recurring_checks", UNSET
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

        _run_recurring_checks_job_template = d.pop(
            "run_recurring_checks_job_template", UNSET
        )
        run_recurring_checks_job_template: Union[Unset, CheckSearchFilters]
        if isinstance(_run_recurring_checks_job_template, Unset):
            run_recurring_checks_job_template = UNSET
        else:
            run_recurring_checks_job_template = CheckSearchFilters.from_dict(
                _run_recurring_checks_job_template
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

        column_basic_model = cls(
            connection_name=connection_name,
            table=table,
            column_name=column_name,
            sql_expression=sql_expression,
            column_hash=column_hash,
            disabled=disabled,
            has_any_configured_checks=has_any_configured_checks,
            has_any_configured_profiling_checks=has_any_configured_profiling_checks,
            has_any_configured_recurring_checks=has_any_configured_recurring_checks,
            has_any_configured_partition_checks=has_any_configured_partition_checks,
            type_snapshot=type_snapshot,
            run_checks_job_template=run_checks_job_template,
            run_profiling_checks_job_template=run_profiling_checks_job_template,
            run_recurring_checks_job_template=run_recurring_checks_job_template,
            run_partition_checks_job_template=run_partition_checks_job_template,
            collect_statistics_job_template=collect_statistics_job_template,
            data_clean_job_template=data_clean_job_template,
        )

        column_basic_model.additional_properties = d
        return column_basic_model

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
