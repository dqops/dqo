from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.check_search_filters import CheckSearchFilters
    from ..models.delete_stored_data_queue_job_parameters import (
        DeleteStoredDataQueueJobParameters,
    )
    from ..models.import_tables_queue_job_parameters import (
        ImportTablesQueueJobParameters,
    )
    from ..models.statistics_collector_search_filters import (
        StatisticsCollectorSearchFilters,
    )


T = TypeVar("T", bound="SchemaModel")


@attr.s(auto_attribs=True)
class SchemaModel:
    """Schema model

    Attributes:
        connection_name (Union[Unset, str]): Connection name.
        schema_name (Union[Unset, str]): Schema name.
        run_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter, identifies which
            checks on which tables and columns should be executed.
        run_profiling_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter,
            identifies which checks on which tables and columns should be executed.
        run_recurring_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter,
            identifies which checks on which tables and columns should be executed.
        run_partition_checks_job_template (Union[Unset, CheckSearchFilters]): Target data quality checks filter,
            identifies which checks on which tables and columns should be executed.
        collect_statistics_job_template (Union[Unset, StatisticsCollectorSearchFilters]):
        import_table_job_parameters (Union[Unset, ImportTablesQueueJobParameters]):
        data_clean_job_template (Union[Unset, DeleteStoredDataQueueJobParameters]):
    """

    connection_name: Union[Unset, str] = UNSET
    schema_name: Union[Unset, str] = UNSET
    run_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_profiling_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_recurring_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    run_partition_checks_job_template: Union[Unset, "CheckSearchFilters"] = UNSET
    collect_statistics_job_template: Union[
        Unset, "StatisticsCollectorSearchFilters"
    ] = UNSET
    import_table_job_parameters: Union[Unset, "ImportTablesQueueJobParameters"] = UNSET
    data_clean_job_template: Union[Unset, "DeleteStoredDataQueueJobParameters"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        connection_name = self.connection_name
        schema_name = self.schema_name
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

        import_table_job_parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.import_table_job_parameters, Unset):
            import_table_job_parameters = self.import_table_job_parameters.to_dict()

        data_clean_job_template: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_clean_job_template, Unset):
            data_clean_job_template = self.data_clean_job_template.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if connection_name is not UNSET:
            field_dict["connection_name"] = connection_name
        if schema_name is not UNSET:
            field_dict["schema_name"] = schema_name
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
        if import_table_job_parameters is not UNSET:
            field_dict["import_table_job_parameters"] = import_table_job_parameters
        if data_clean_job_template is not UNSET:
            field_dict["data_clean_job_template"] = data_clean_job_template

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.check_search_filters import CheckSearchFilters
        from ..models.delete_stored_data_queue_job_parameters import (
            DeleteStoredDataQueueJobParameters,
        )
        from ..models.import_tables_queue_job_parameters import (
            ImportTablesQueueJobParameters,
        )
        from ..models.statistics_collector_search_filters import (
            StatisticsCollectorSearchFilters,
        )

        d = src_dict.copy()
        connection_name = d.pop("connection_name", UNSET)

        schema_name = d.pop("schema_name", UNSET)

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

        _import_table_job_parameters = d.pop("import_table_job_parameters", UNSET)
        import_table_job_parameters: Union[Unset, ImportTablesQueueJobParameters]
        if isinstance(_import_table_job_parameters, Unset):
            import_table_job_parameters = UNSET
        else:
            import_table_job_parameters = ImportTablesQueueJobParameters.from_dict(
                _import_table_job_parameters
            )

        _data_clean_job_template = d.pop("data_clean_job_template", UNSET)
        data_clean_job_template: Union[Unset, DeleteStoredDataQueueJobParameters]
        if isinstance(_data_clean_job_template, Unset):
            data_clean_job_template = UNSET
        else:
            data_clean_job_template = DeleteStoredDataQueueJobParameters.from_dict(
                _data_clean_job_template
            )

        schema_model = cls(
            connection_name=connection_name,
            schema_name=schema_name,
            run_checks_job_template=run_checks_job_template,
            run_profiling_checks_job_template=run_profiling_checks_job_template,
            run_recurring_checks_job_template=run_recurring_checks_job_template,
            run_partition_checks_job_template=run_partition_checks_job_template,
            collect_statistics_job_template=collect_statistics_job_template,
            import_table_job_parameters=import_table_job_parameters,
            data_clean_job_template=data_clean_job_template,
        )

        schema_model.additional_properties = d
        return schema_model

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
