from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_search_filters import CheckSearchFilters
    from ..models.column_current_data_quality_status_model import (
        ColumnCurrentDataQualityStatusModel,
    )
    from ..models.column_list_model_advanced_properties import (
        ColumnListModelAdvancedProperties,
    )
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
            column_name (Union[Unset, str]): Column name.
            labels (Union[Unset, List[str]]): List of labels applied to the table.
            sql_expression (Union[Unset, str]): SQL expression for a calculated column, or a column that applies additional
                data transformation to the original column value. The original column value is referenced by a token {column}.
            column_hash (Union[Unset, int]): Column hash that identifies the column using a unique hash code.
            disabled (Union[Unset, bool]): Disables all data quality checks on the column. Data quality checks will not be
                executed.
            id (Union[Unset, bool]): Marks columns that are part of a primary or a unique key. DQOps captures their values
                during error sampling to match invalid values to the rows in which the value was found.
            has_any_configured_checks (Union[Unset, bool]): True when the column has any checks configured (read-only).
            has_any_configured_profiling_checks (Union[Unset, bool]): True when the column has any profiling checks
                configured (read-only).
            has_any_configured_monitoring_checks (Union[Unset, bool]): True when the column has any monitoring checks
                configured (read-only).
            has_any_configured_partition_checks (Union[Unset, bool]): True when the column has any partition checks
                configured (read-only).
            type_snapshot (Union[Unset, ColumnTypeSnapshotSpec]):
            data_quality_status (Union[Unset, ColumnCurrentDataQualityStatusModel]): The column's most recent data quality
                status. It is a summary of the results of the most recently executed data quality checks on the column. Verify
                the value of the current_severity to see if there are any data quality issues on the column.
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
            advanced_properties (Union[Unset, ColumnListModelAdvancedProperties]): A dictionary of advanced properties that
                can be used for e.g. to support mapping data to data catalogs, a key/value dictionary.
            can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete the column.
            can_collect_statistics (Union[Unset, bool]): Boolean flag that decides if the current user can collect
                statistics.
            can_run_checks (Union[Unset, bool]): Boolean flag that decides if the current user can run checks.
            can_delete_data (Union[Unset, bool]): Boolean flag that decides if the current user can delete data (results).
    """

    connection_name: Union[Unset, str] = UNSET
    table: Union[Unset, "PhysicalTableName"] = UNSET
    column_name: Union[Unset, str] = UNSET
    labels: Union[Unset, List[str]] = UNSET
    sql_expression: Union[Unset, str] = UNSET
    column_hash: Union[Unset, int] = UNSET
    disabled: Union[Unset, bool] = UNSET
    id: Union[Unset, bool] = UNSET
    has_any_configured_checks: Union[Unset, bool] = UNSET
    has_any_configured_profiling_checks: Union[Unset, bool] = UNSET
    has_any_configured_monitoring_checks: Union[Unset, bool] = UNSET
    has_any_configured_partition_checks: Union[Unset, bool] = UNSET
    type_snapshot: Union[Unset, "ColumnTypeSnapshotSpec"] = UNSET
    data_quality_status: Union[Unset, "ColumnCurrentDataQualityStatusModel"] = UNSET
    run_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_profiling_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_monitoring_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_partition_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    collect_statistics_job_template: Union[
        Unset, "StatisticsCollectorSearchFilters"
    ] = UNSET
    data_clean_job_template: Union[Unset, "DeleteStoredDataQueueJobParameters"] = UNSET
    advanced_properties: Union[Unset, "ColumnListModelAdvancedProperties"] = UNSET
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
        labels: Union[Unset, List[str]] = UNSET
        if not isinstance(self.labels, Unset):
            labels = self.labels

        sql_expression = self.sql_expression
        column_hash = self.column_hash
        disabled = self.disabled
        id = self.id
        has_any_configured_checks = self.has_any_configured_checks
        has_any_configured_profiling_checks = self.has_any_configured_profiling_checks
        has_any_configured_monitoring_checks = self.has_any_configured_monitoring_checks
        has_any_configured_partition_checks = self.has_any_configured_partition_checks
        type_snapshot: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.type_snapshot, Unset):
            type_snapshot = self.type_snapshot.to_dict()

        data_quality_status: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_quality_status, Unset):
            data_quality_status = self.data_quality_status.to_dict()

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

        advanced_properties: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.advanced_properties, Unset):
            advanced_properties = self.advanced_properties.to_dict()

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
        if labels is not UNSET:
            field_dict["labels"] = labels
        if sql_expression is not UNSET:
            field_dict["sql_expression"] = sql_expression
        if column_hash is not UNSET:
            field_dict["column_hash"] = column_hash
        if disabled is not UNSET:
            field_dict["disabled"] = disabled
        if id is not UNSET:
            field_dict["id"] = id
        if has_any_configured_checks is not UNSET:
            field_dict["has_any_configured_checks"] = has_any_configured_checks
        if has_any_configured_profiling_checks is not UNSET:
            field_dict["has_any_configured_profiling_checks"] = (
                has_any_configured_profiling_checks
            )
        if has_any_configured_monitoring_checks is not UNSET:
            field_dict["has_any_configured_monitoring_checks"] = (
                has_any_configured_monitoring_checks
            )
        if has_any_configured_partition_checks is not UNSET:
            field_dict["has_any_configured_partition_checks"] = (
                has_any_configured_partition_checks
            )
        if type_snapshot is not UNSET:
            field_dict["type_snapshot"] = type_snapshot
        if data_quality_status is not UNSET:
            field_dict["data_quality_status"] = data_quality_status
        if run_checks_job_template is not UNSET:
            field_dict["run_checks_job_template"] = run_checks_job_template
        if run_profiling_checks_job_template is not UNSET:
            field_dict["run_profiling_checks_job_template"] = (
                run_profiling_checks_job_template
            )
        if run_monitoring_checks_job_template is not UNSET:
            field_dict["run_monitoring_checks_job_template"] = (
                run_monitoring_checks_job_template
            )
        if run_partition_checks_job_template is not UNSET:
            field_dict["run_partition_checks_job_template"] = (
                run_partition_checks_job_template
            )
        if collect_statistics_job_template is not UNSET:
            field_dict["collect_statistics_job_template"] = (
                collect_statistics_job_template
            )
        if data_clean_job_template is not UNSET:
            field_dict["data_clean_job_template"] = data_clean_job_template
        if advanced_properties is not UNSET:
            field_dict["advanced_properties"] = advanced_properties
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
        from ..models.column_current_data_quality_status_model import (
            ColumnCurrentDataQualityStatusModel,
        )
        from ..models.column_list_model_advanced_properties import (
            ColumnListModelAdvancedProperties,
        )
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

        labels = cast(List[str], d.pop("labels", UNSET))

        sql_expression = d.pop("sql_expression", UNSET)

        column_hash = d.pop("column_hash", UNSET)

        disabled = d.pop("disabled", UNSET)

        id = d.pop("id", UNSET)

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

        _data_quality_status = d.pop("data_quality_status", UNSET)
        data_quality_status: Union[Unset, ColumnCurrentDataQualityStatusModel]
        if isinstance(_data_quality_status, Unset):
            data_quality_status = UNSET
        else:
            data_quality_status = ColumnCurrentDataQualityStatusModel.from_dict(
                _data_quality_status
            )

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

        _advanced_properties = d.pop("advanced_properties", UNSET)
        advanced_properties: Union[Unset, ColumnListModelAdvancedProperties]
        if isinstance(_advanced_properties, Unset):
            advanced_properties = UNSET
        else:
            advanced_properties = ColumnListModelAdvancedProperties.from_dict(
                _advanced_properties
            )

        can_edit = d.pop("can_edit", UNSET)

        can_collect_statistics = d.pop("can_collect_statistics", UNSET)

        can_run_checks = d.pop("can_run_checks", UNSET)

        can_delete_data = d.pop("can_delete_data", UNSET)

        column_list_model = cls(
            connection_name=connection_name,
            table=table,
            column_name=column_name,
            labels=labels,
            sql_expression=sql_expression,
            column_hash=column_hash,
            disabled=disabled,
            id=id,
            has_any_configured_checks=has_any_configured_checks,
            has_any_configured_profiling_checks=has_any_configured_profiling_checks,
            has_any_configured_monitoring_checks=has_any_configured_monitoring_checks,
            has_any_configured_partition_checks=has_any_configured_partition_checks,
            type_snapshot=type_snapshot,
            data_quality_status=data_quality_status,
            run_checks_job_template=run_checks_job_template,
            run_profiling_checks_job_template=run_profiling_checks_job_template,
            run_monitoring_checks_job_template=run_monitoring_checks_job_template,
            run_partition_checks_job_template=run_partition_checks_job_template,
            collect_statistics_job_template=collect_statistics_job_template,
            data_clean_job_template=data_clean_job_template,
            advanced_properties=advanced_properties,
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
