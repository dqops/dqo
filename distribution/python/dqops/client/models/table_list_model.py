from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.profiling_time_period import ProfilingTimePeriod
from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_search_filters import CheckSearchFilters
    from ..models.delete_stored_data_queue_job_parameters import (
        DeleteStoredDataQueueJobParameters,
    )
    from ..models.physical_table_name import PhysicalTableName
    from ..models.statistics_collector_search_filters import (
        StatisticsCollectorSearchFilters,
    )
    from ..models.table_owner_spec import TableOwnerSpec


T = TypeVar("T", bound="TableListModel")


@_attrs_define
class TableListModel:
    """Table list model with a subset of parameters, excluding all nested objects.

    Attributes:
        connection_name (Union[Unset, str]): Connection name.
        table_hash (Union[Unset, int]): Table hash that identifies the table using a unique hash code.
        target (Union[Unset, PhysicalTableName]):
        disabled (Union[Unset, bool]): Disables all data quality checks on the table. Data quality checks will not be
            executed.
        stage (Union[Unset, str]): Stage name.
        filter_ (Union[Unset, str]): SQL WHERE clause added to the sensor queries.
        priority (Union[Unset, int]): Table priority (1, 2, 3, 4, ...). The tables could be assigned a priority level.
            The table priority is copied into each data quality check result and a sensor result, enabling efficient
            grouping of more and less important tables during a data quality improvement project, when the data quality
            issues on higher priority tables are fixed before data quality issues on less important tables.
        owner (Union[Unset, TableOwnerSpec]):
        profiling_checks_result_truncation (Union[Unset, ProfilingTimePeriod]):
        has_any_configured_checks (Union[Unset, bool]): True when the table has any checks configured.
        has_any_configured_profiling_checks (Union[Unset, bool]): True when the table has any profiling checks
            configured.
        has_any_configured_monitoring_checks (Union[Unset, bool]): True when the table has any monitoring checks
            configured.
        has_any_configured_partition_checks (Union[Unset, bool]): True when the table has any partition checks
            configured.
        partitioning_configuration_missing (Union[Unset, bool]): True when the table has missing configuration of the
            "partition_by_column" column, making any partition checks fail when executed.
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
        can_edit (Union[Unset, bool]): Boolean flag that decides if the current user can update or delete this object.
        can_collect_statistics (Union[Unset, bool]): Boolean flag that decides if the current user can collect
            statistics.
        can_run_checks (Union[Unset, bool]): Boolean flag that decides if the current user can run checks.
        can_delete_data (Union[Unset, bool]): Boolean flag that decides if the current user can delete data (results).
        yaml_parsing_error (Union[Unset, str]): Optional parsing error that was captured when parsing the YAML file.
            This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing
            error message and the file location.
    """

    connection_name: Union[Unset, str] = UNSET
    table_hash: Union[Unset, int] = UNSET
    target: Union[Unset, "PhysicalTableName"] = UNSET
    disabled: Union[Unset, bool] = UNSET
    stage: Union[Unset, str] = UNSET
    filter_: Union[Unset, str] = UNSET
    priority: Union[Unset, int] = UNSET
    owner: Union[Unset, "TableOwnerSpec"] = UNSET
    profiling_checks_result_truncation: Union[Unset, ProfilingTimePeriod] = UNSET
    has_any_configured_checks: Union[Unset, bool] = UNSET
    has_any_configured_profiling_checks: Union[Unset, bool] = UNSET
    has_any_configured_monitoring_checks: Union[Unset, bool] = UNSET
    has_any_configured_partition_checks: Union[Unset, bool] = UNSET
    partitioning_configuration_missing: Union[Unset, bool] = UNSET
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
    yaml_parsing_error: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        table_hash = self.table_hash
        target: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.target, Unset):
            target = self.target.to_dict()

        disabled = self.disabled
        stage = self.stage
        filter_ = self.filter_
        priority = self.priority
        owner: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.owner, Unset):
            owner = self.owner.to_dict()

        profiling_checks_result_truncation: Union[Unset, str] = UNSET
        if not isinstance(self.profiling_checks_result_truncation, Unset):
            profiling_checks_result_truncation = (
                self.profiling_checks_result_truncation.value
            )

        has_any_configured_checks = self.has_any_configured_checks
        has_any_configured_profiling_checks = self.has_any_configured_profiling_checks
        has_any_configured_monitoring_checks = self.has_any_configured_monitoring_checks
        has_any_configured_partition_checks = self.has_any_configured_partition_checks
        partitioning_configuration_missing = self.partitioning_configuration_missing
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
        yaml_parsing_error = self.yaml_parsing_error

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if table_hash is not UNSET:
            field_dict["table_hash"] = table_hash
        if target is not UNSET:
            field_dict["target"] = target
        if disabled is not UNSET:
            field_dict["disabled"] = disabled
        if stage is not UNSET:
            field_dict["stage"] = stage
        if filter_ is not UNSET:
            field_dict["filter"] = filter_
        if priority is not UNSET:
            field_dict["priority"] = priority
        if owner is not UNSET:
            field_dict["owner"] = owner
        if profiling_checks_result_truncation is not UNSET:
            field_dict[
                "profiling_checks_result_truncation"
            ] = profiling_checks_result_truncation
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
        if partitioning_configuration_missing is not UNSET:
            field_dict[
                "partitioning_configuration_missing"
            ] = partitioning_configuration_missing
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
        if yaml_parsing_error is not UNSET:
            field_dict["yaml_parsing_error"] = yaml_parsing_error

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_search_filters import CheckSearchFilters
        from ..models.delete_stored_data_queue_job_parameters import (
            DeleteStoredDataQueueJobParameters,
        )
        from ..models.physical_table_name import PhysicalTableName
        from ..models.statistics_collector_search_filters import (
            StatisticsCollectorSearchFilters,
        )
        from ..models.table_owner_spec import TableOwnerSpec

        d = src_dict.copy()
        connection_name = d.pop("connection_name", UNSET)

        table_hash = d.pop("table_hash", UNSET)

        _target = d.pop("target", UNSET)
        target: Union[Unset, PhysicalTableName]
        if isinstance(_target, Unset):
            target = UNSET
        else:
            target = PhysicalTableName.from_dict(_target)

        disabled = d.pop("disabled", UNSET)

        stage = d.pop("stage", UNSET)

        filter_ = d.pop("filter", UNSET)

        priority = d.pop("priority", UNSET)

        _owner = d.pop("owner", UNSET)
        owner: Union[Unset, TableOwnerSpec]
        if isinstance(_owner, Unset):
            owner = UNSET
        else:
            owner = TableOwnerSpec.from_dict(_owner)

        _profiling_checks_result_truncation = d.pop(
            "profiling_checks_result_truncation", UNSET
        )
        profiling_checks_result_truncation: Union[Unset, ProfilingTimePeriod]
        if isinstance(_profiling_checks_result_truncation, Unset):
            profiling_checks_result_truncation = UNSET
        else:
            profiling_checks_result_truncation = ProfilingTimePeriod(
                _profiling_checks_result_truncation
            )

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

        partitioning_configuration_missing = d.pop(
            "partitioning_configuration_missing", UNSET
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

        can_edit = d.pop("can_edit", UNSET)

        can_collect_statistics = d.pop("can_collect_statistics", UNSET)

        can_run_checks = d.pop("can_run_checks", UNSET)

        can_delete_data = d.pop("can_delete_data", UNSET)

        yaml_parsing_error = d.pop("yaml_parsing_error", UNSET)

        table_list_model = cls(
            connection_name=connection_name,
            table_hash=table_hash,
            target=target,
            disabled=disabled,
            stage=stage,
            filter_=filter_,
            priority=priority,
            owner=owner,
            profiling_checks_result_truncation=profiling_checks_result_truncation,
            has_any_configured_checks=has_any_configured_checks,
            has_any_configured_profiling_checks=has_any_configured_profiling_checks,
            has_any_configured_monitoring_checks=has_any_configured_monitoring_checks,
            has_any_configured_partition_checks=has_any_configured_partition_checks,
            partitioning_configuration_missing=partitioning_configuration_missing,
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
            yaml_parsing_error=yaml_parsing_error,
        )

        table_list_model.additional_properties = d
        return table_list_model

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
