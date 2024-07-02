from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.collect_error_samples_on_table_parameters import (
        CollectErrorSamplesOnTableParameters,
    )
    from ..models.collect_error_samples_parameters import CollectErrorSamplesParameters
    from ..models.collect_statistics_on_table_queue_job_parameters import (
        CollectStatisticsOnTableQueueJobParameters,
    )
    from ..models.collect_statistics_queue_job_parameters import (
        CollectStatisticsQueueJobParameters,
    )
    from ..models.delete_stored_data_queue_job_parameters import (
        DeleteStoredDataQueueJobParameters,
    )
    from ..models.import_schema_queue_job_parameters import (
        ImportSchemaQueueJobParameters,
    )
    from ..models.import_tables_queue_job_parameters import (
        ImportTablesQueueJobParameters,
    )
    from ..models.monitoring_schedule_spec import MonitoringScheduleSpec
    from ..models.repair_stored_data_queue_job_parameters import (
        RepairStoredDataQueueJobParameters,
    )
    from ..models.run_checks_on_table_parameters import RunChecksOnTableParameters
    from ..models.run_checks_parameters import RunChecksParameters
    from ..models.synchronize_multiple_folders_dqo_queue_job_parameters import (
        SynchronizeMultipleFoldersDqoQueueJobParameters,
    )
    from ..models.synchronize_root_folder_dqo_queue_job_parameters import (
        SynchronizeRootFolderDqoQueueJobParameters,
    )


T = TypeVar("T", bound="DqoJobEntryParametersModel")


@_attrs_define
class DqoJobEntryParametersModel:
    """
    Attributes:
        synchronize_root_folder_parameters (Union[Unset, SynchronizeRootFolderDqoQueueJobParameters]):
        synchronize_multiple_folders_parameters (Union[Unset, SynchronizeMultipleFoldersDqoQueueJobParameters]):
        run_scheduled_checks_parameters (Union[Unset, MonitoringScheduleSpec]):
        run_checks_parameters (Union[Unset, RunChecksParameters]): Run checks configuration, specifies the target checks
            that should be executed and an optional time window.
        run_checks_on_table_parameters (Union[Unset, RunChecksOnTableParameters]): Run checks configuration for a job
            that will run checks on a single table, specifies the target table and the target checks that should be executed
            and an optional time window.
        collect_statistics_parameters (Union[Unset, CollectStatisticsQueueJobParameters]):
        collect_statistics_on_table_parameters (Union[Unset, CollectStatisticsOnTableQueueJobParameters]):
        collect_error_samples_parameters (Union[Unset, CollectErrorSamplesParameters]): Collect error samples job
            parameters, specifies the target checks that should be executed to collect error samples and an optional time
            window.
        collect_error_samples_on_table_parameters (Union[Unset, CollectErrorSamplesOnTableParameters]):
        import_schema_parameters (Union[Unset, ImportSchemaQueueJobParameters]):
        import_table_parameters (Union[Unset, ImportTablesQueueJobParameters]):
        delete_stored_data_parameters (Union[Unset, DeleteStoredDataQueueJobParameters]):
        repair_stored_data_parameters (Union[Unset, RepairStoredDataQueueJobParameters]):
    """

    synchronize_root_folder_parameters: Union[
        Unset, "SynchronizeRootFolderDqoQueueJobParameters"
    ] = UNSET
    synchronize_multiple_folders_parameters: Union[
        Unset, "SynchronizeMultipleFoldersDqoQueueJobParameters"
    ] = UNSET
    run_scheduled_checks_parameters: Union[Unset, "MonitoringScheduleSpec"] = UNSET
    run_checks_parameters: Union[Unset, "RunChecksParameters"] = UNSET
    run_checks_on_table_parameters: Union[Unset, "RunChecksOnTableParameters"] = UNSET
    collect_statistics_parameters: Union[
        Unset, "CollectStatisticsQueueJobParameters"
    ] = UNSET
    collect_statistics_on_table_parameters: Union[
        Unset, "CollectStatisticsOnTableQueueJobParameters"
    ] = UNSET
    collect_error_samples_parameters: Union[Unset, "CollectErrorSamplesParameters"] = (
        UNSET
    )
    collect_error_samples_on_table_parameters: Union[
        Unset, "CollectErrorSamplesOnTableParameters"
    ] = UNSET
    import_schema_parameters: Union[Unset, "ImportSchemaQueueJobParameters"] = UNSET
    import_table_parameters: Union[Unset, "ImportTablesQueueJobParameters"] = UNSET
    delete_stored_data_parameters: Union[
        Unset, "DeleteStoredDataQueueJobParameters"
    ] = UNSET
    repair_stored_data_parameters: Union[
        Unset, "RepairStoredDataQueueJobParameters"
    ] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        synchronize_root_folder_parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.synchronize_root_folder_parameters, Unset):
            synchronize_root_folder_parameters = (
                self.synchronize_root_folder_parameters.to_dict()
            )

        synchronize_multiple_folders_parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.synchronize_multiple_folders_parameters, Unset):
            synchronize_multiple_folders_parameters = (
                self.synchronize_multiple_folders_parameters.to_dict()
            )

        run_scheduled_checks_parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_scheduled_checks_parameters, Unset):
            run_scheduled_checks_parameters = (
                self.run_scheduled_checks_parameters.to_dict()
            )

        run_checks_parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_checks_parameters, Unset):
            run_checks_parameters = self.run_checks_parameters.to_dict()

        run_checks_on_table_parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.run_checks_on_table_parameters, Unset):
            run_checks_on_table_parameters = (
                self.run_checks_on_table_parameters.to_dict()
            )

        collect_statistics_parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.collect_statistics_parameters, Unset):
            collect_statistics_parameters = self.collect_statistics_parameters.to_dict()

        collect_statistics_on_table_parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.collect_statistics_on_table_parameters, Unset):
            collect_statistics_on_table_parameters = (
                self.collect_statistics_on_table_parameters.to_dict()
            )

        collect_error_samples_parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.collect_error_samples_parameters, Unset):
            collect_error_samples_parameters = (
                self.collect_error_samples_parameters.to_dict()
            )

        collect_error_samples_on_table_parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.collect_error_samples_on_table_parameters, Unset):
            collect_error_samples_on_table_parameters = (
                self.collect_error_samples_on_table_parameters.to_dict()
            )

        import_schema_parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.import_schema_parameters, Unset):
            import_schema_parameters = self.import_schema_parameters.to_dict()

        import_table_parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.import_table_parameters, Unset):
            import_table_parameters = self.import_table_parameters.to_dict()

        delete_stored_data_parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.delete_stored_data_parameters, Unset):
            delete_stored_data_parameters = self.delete_stored_data_parameters.to_dict()

        repair_stored_data_parameters: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.repair_stored_data_parameters, Unset):
            repair_stored_data_parameters = self.repair_stored_data_parameters.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if synchronize_root_folder_parameters is not UNSET:
            field_dict["synchronizeRootFolderParameters"] = (
                synchronize_root_folder_parameters
            )
        if synchronize_multiple_folders_parameters is not UNSET:
            field_dict["synchronizeMultipleFoldersParameters"] = (
                synchronize_multiple_folders_parameters
            )
        if run_scheduled_checks_parameters is not UNSET:
            field_dict["runScheduledChecksParameters"] = run_scheduled_checks_parameters
        if run_checks_parameters is not UNSET:
            field_dict["runChecksParameters"] = run_checks_parameters
        if run_checks_on_table_parameters is not UNSET:
            field_dict["runChecksOnTableParameters"] = run_checks_on_table_parameters
        if collect_statistics_parameters is not UNSET:
            field_dict["collectStatisticsParameters"] = collect_statistics_parameters
        if collect_statistics_on_table_parameters is not UNSET:
            field_dict["collectStatisticsOnTableParameters"] = (
                collect_statistics_on_table_parameters
            )
        if collect_error_samples_parameters is not UNSET:
            field_dict["collectErrorSamplesParameters"] = (
                collect_error_samples_parameters
            )
        if collect_error_samples_on_table_parameters is not UNSET:
            field_dict["collectErrorSamplesOnTableParameters"] = (
                collect_error_samples_on_table_parameters
            )
        if import_schema_parameters is not UNSET:
            field_dict["importSchemaParameters"] = import_schema_parameters
        if import_table_parameters is not UNSET:
            field_dict["importTableParameters"] = import_table_parameters
        if delete_stored_data_parameters is not UNSET:
            field_dict["deleteStoredDataParameters"] = delete_stored_data_parameters
        if repair_stored_data_parameters is not UNSET:
            field_dict["repairStoredDataParameters"] = repair_stored_data_parameters

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.collect_error_samples_on_table_parameters import (
            CollectErrorSamplesOnTableParameters,
        )
        from ..models.collect_error_samples_parameters import (
            CollectErrorSamplesParameters,
        )
        from ..models.collect_statistics_on_table_queue_job_parameters import (
            CollectStatisticsOnTableQueueJobParameters,
        )
        from ..models.collect_statistics_queue_job_parameters import (
            CollectStatisticsQueueJobParameters,
        )
        from ..models.delete_stored_data_queue_job_parameters import (
            DeleteStoredDataQueueJobParameters,
        )
        from ..models.import_schema_queue_job_parameters import (
            ImportSchemaQueueJobParameters,
        )
        from ..models.import_tables_queue_job_parameters import (
            ImportTablesQueueJobParameters,
        )
        from ..models.monitoring_schedule_spec import MonitoringScheduleSpec
        from ..models.repair_stored_data_queue_job_parameters import (
            RepairStoredDataQueueJobParameters,
        )
        from ..models.run_checks_on_table_parameters import RunChecksOnTableParameters
        from ..models.run_checks_parameters import RunChecksParameters
        from ..models.synchronize_multiple_folders_dqo_queue_job_parameters import (
            SynchronizeMultipleFoldersDqoQueueJobParameters,
        )
        from ..models.synchronize_root_folder_dqo_queue_job_parameters import (
            SynchronizeRootFolderDqoQueueJobParameters,
        )

        d = src_dict.copy()
        _synchronize_root_folder_parameters = d.pop(
            "synchronizeRootFolderParameters", UNSET
        )
        synchronize_root_folder_parameters: Union[
            Unset, SynchronizeRootFolderDqoQueueJobParameters
        ]
        if isinstance(_synchronize_root_folder_parameters, Unset):
            synchronize_root_folder_parameters = UNSET
        else:
            synchronize_root_folder_parameters = (
                SynchronizeRootFolderDqoQueueJobParameters.from_dict(
                    _synchronize_root_folder_parameters
                )
            )

        _synchronize_multiple_folders_parameters = d.pop(
            "synchronizeMultipleFoldersParameters", UNSET
        )
        synchronize_multiple_folders_parameters: Union[
            Unset, SynchronizeMultipleFoldersDqoQueueJobParameters
        ]
        if isinstance(_synchronize_multiple_folders_parameters, Unset):
            synchronize_multiple_folders_parameters = UNSET
        else:
            synchronize_multiple_folders_parameters = (
                SynchronizeMultipleFoldersDqoQueueJobParameters.from_dict(
                    _synchronize_multiple_folders_parameters
                )
            )

        _run_scheduled_checks_parameters = d.pop("runScheduledChecksParameters", UNSET)
        run_scheduled_checks_parameters: Union[Unset, MonitoringScheduleSpec]
        if isinstance(_run_scheduled_checks_parameters, Unset):
            run_scheduled_checks_parameters = UNSET
        else:
            run_scheduled_checks_parameters = MonitoringScheduleSpec.from_dict(
                _run_scheduled_checks_parameters
            )

        _run_checks_parameters = d.pop("runChecksParameters", UNSET)
        run_checks_parameters: Union[Unset, RunChecksParameters]
        if isinstance(_run_checks_parameters, Unset):
            run_checks_parameters = UNSET
        else:
            run_checks_parameters = RunChecksParameters.from_dict(
                _run_checks_parameters
            )

        _run_checks_on_table_parameters = d.pop("runChecksOnTableParameters", UNSET)
        run_checks_on_table_parameters: Union[Unset, RunChecksOnTableParameters]
        if isinstance(_run_checks_on_table_parameters, Unset):
            run_checks_on_table_parameters = UNSET
        else:
            run_checks_on_table_parameters = RunChecksOnTableParameters.from_dict(
                _run_checks_on_table_parameters
            )

        _collect_statistics_parameters = d.pop("collectStatisticsParameters", UNSET)
        collect_statistics_parameters: Union[Unset, CollectStatisticsQueueJobParameters]
        if isinstance(_collect_statistics_parameters, Unset):
            collect_statistics_parameters = UNSET
        else:
            collect_statistics_parameters = (
                CollectStatisticsQueueJobParameters.from_dict(
                    _collect_statistics_parameters
                )
            )

        _collect_statistics_on_table_parameters = d.pop(
            "collectStatisticsOnTableParameters", UNSET
        )
        collect_statistics_on_table_parameters: Union[
            Unset, CollectStatisticsOnTableQueueJobParameters
        ]
        if isinstance(_collect_statistics_on_table_parameters, Unset):
            collect_statistics_on_table_parameters = UNSET
        else:
            collect_statistics_on_table_parameters = (
                CollectStatisticsOnTableQueueJobParameters.from_dict(
                    _collect_statistics_on_table_parameters
                )
            )

        _collect_error_samples_parameters = d.pop(
            "collectErrorSamplesParameters", UNSET
        )
        collect_error_samples_parameters: Union[Unset, CollectErrorSamplesParameters]
        if isinstance(_collect_error_samples_parameters, Unset):
            collect_error_samples_parameters = UNSET
        else:
            collect_error_samples_parameters = CollectErrorSamplesParameters.from_dict(
                _collect_error_samples_parameters
            )

        _collect_error_samples_on_table_parameters = d.pop(
            "collectErrorSamplesOnTableParameters", UNSET
        )
        collect_error_samples_on_table_parameters: Union[
            Unset, CollectErrorSamplesOnTableParameters
        ]
        if isinstance(_collect_error_samples_on_table_parameters, Unset):
            collect_error_samples_on_table_parameters = UNSET
        else:
            collect_error_samples_on_table_parameters = (
                CollectErrorSamplesOnTableParameters.from_dict(
                    _collect_error_samples_on_table_parameters
                )
            )

        _import_schema_parameters = d.pop("importSchemaParameters", UNSET)
        import_schema_parameters: Union[Unset, ImportSchemaQueueJobParameters]
        if isinstance(_import_schema_parameters, Unset):
            import_schema_parameters = UNSET
        else:
            import_schema_parameters = ImportSchemaQueueJobParameters.from_dict(
                _import_schema_parameters
            )

        _import_table_parameters = d.pop("importTableParameters", UNSET)
        import_table_parameters: Union[Unset, ImportTablesQueueJobParameters]
        if isinstance(_import_table_parameters, Unset):
            import_table_parameters = UNSET
        else:
            import_table_parameters = ImportTablesQueueJobParameters.from_dict(
                _import_table_parameters
            )

        _delete_stored_data_parameters = d.pop("deleteStoredDataParameters", UNSET)
        delete_stored_data_parameters: Union[Unset, DeleteStoredDataQueueJobParameters]
        if isinstance(_delete_stored_data_parameters, Unset):
            delete_stored_data_parameters = UNSET
        else:
            delete_stored_data_parameters = (
                DeleteStoredDataQueueJobParameters.from_dict(
                    _delete_stored_data_parameters
                )
            )

        _repair_stored_data_parameters = d.pop("repairStoredDataParameters", UNSET)
        repair_stored_data_parameters: Union[Unset, RepairStoredDataQueueJobParameters]
        if isinstance(_repair_stored_data_parameters, Unset):
            repair_stored_data_parameters = UNSET
        else:
            repair_stored_data_parameters = (
                RepairStoredDataQueueJobParameters.from_dict(
                    _repair_stored_data_parameters
                )
            )

        dqo_job_entry_parameters_model = cls(
            synchronize_root_folder_parameters=synchronize_root_folder_parameters,
            synchronize_multiple_folders_parameters=synchronize_multiple_folders_parameters,
            run_scheduled_checks_parameters=run_scheduled_checks_parameters,
            run_checks_parameters=run_checks_parameters,
            run_checks_on_table_parameters=run_checks_on_table_parameters,
            collect_statistics_parameters=collect_statistics_parameters,
            collect_statistics_on_table_parameters=collect_statistics_on_table_parameters,
            collect_error_samples_parameters=collect_error_samples_parameters,
            collect_error_samples_on_table_parameters=collect_error_samples_on_table_parameters,
            import_schema_parameters=import_schema_parameters,
            import_table_parameters=import_table_parameters,
            delete_stored_data_parameters=delete_stored_data_parameters,
            repair_stored_data_parameters=repair_stored_data_parameters,
        )

        dqo_job_entry_parameters_model.additional_properties = d
        return dqo_job_entry_parameters_model

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
