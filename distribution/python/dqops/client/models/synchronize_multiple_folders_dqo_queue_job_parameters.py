from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.file_synchronization_direction import FileSynchronizationDirection
from ..types import UNSET, Unset

T = TypeVar("T", bound="SynchronizeMultipleFoldersDqoQueueJobParameters")


@_attrs_define
class SynchronizeMultipleFoldersDqoQueueJobParameters:
    """
    Attributes:
        direction (Union[Unset, FileSynchronizationDirection]):
        force_refresh_native_tables (Union[Unset, bool]): Force full refresh of native tables in the data quality data
            warehouse. The default synchronization mode is to refresh only modified data.
        detect_cron_schedules (Union[Unset, bool]): Scans the yaml files (with the configuration for connections and
            tables) and detects new cron schedules. Detected cron schedules are registered in the cron (Quartz) job
            scheduler.
        sources (Union[Unset, bool]): Synchronize the "sources" folder.
        sensors (Union[Unset, bool]): Synchronize the "sensors" folder.
        rules (Union[Unset, bool]): Synchronize the "rules" folder.
        checks (Union[Unset, bool]): Synchronize the "checks" folder.
        settings (Union[Unset, bool]): Synchronize the "settings" folder.
        credentials (Union[Unset, bool]): Synchronize the ".credentials" folder.
        dictionaries (Union[Unset, bool]): Synchronize the "dictionaries" folder.
        patterns (Union[Unset, bool]): Synchronize the "patterns" folder.
        data_sensor_readouts (Union[Unset, bool]): Synchronize the ".data/sensor_readouts" folder.
        data_check_results (Union[Unset, bool]): Synchronize the ".data/check_results" folder.
        data_statistics (Union[Unset, bool]): Synchronize the ".data/statistics" folder.
        data_errors (Union[Unset, bool]): Synchronize the ".data/errors" folder.
        data_incidents (Union[Unset, bool]): Synchronize the ".data/incidents" folder.
        synchronize_folder_with_local_changes (Union[Unset, bool]): Synchronize all folders that have local changes.
            When this field is set to true, there is no need to enable synchronization of single folders because DQOps will
            decide which folders need synchronization (to be pushed to the cloud).
    """

    direction: Union[Unset, FileSynchronizationDirection] = UNSET
    force_refresh_native_tables: Union[Unset, bool] = UNSET
    detect_cron_schedules: Union[Unset, bool] = UNSET
    sources: Union[Unset, bool] = UNSET
    sensors: Union[Unset, bool] = UNSET
    rules: Union[Unset, bool] = UNSET
    checks: Union[Unset, bool] = UNSET
    settings: Union[Unset, bool] = UNSET
    credentials: Union[Unset, bool] = UNSET
    dictionaries: Union[Unset, bool] = UNSET
    patterns: Union[Unset, bool] = UNSET
    data_sensor_readouts: Union[Unset, bool] = UNSET
    data_check_results: Union[Unset, bool] = UNSET
    data_statistics: Union[Unset, bool] = UNSET
    data_errors: Union[Unset, bool] = UNSET
    data_incidents: Union[Unset, bool] = UNSET
    synchronize_folder_with_local_changes: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        direction: Union[Unset, str] = UNSET
        if not isinstance(self.direction, Unset):
            direction = self.direction.value

        force_refresh_native_tables = self.force_refresh_native_tables
        detect_cron_schedules = self.detect_cron_schedules
        sources = self.sources
        sensors = self.sensors
        rules = self.rules
        checks = self.checks
        settings = self.settings
        credentials = self.credentials
        dictionaries = self.dictionaries
        patterns = self.patterns
        data_sensor_readouts = self.data_sensor_readouts
        data_check_results = self.data_check_results
        data_statistics = self.data_statistics
        data_errors = self.data_errors
        data_incidents = self.data_incidents
        synchronize_folder_with_local_changes = (
            self.synchronize_folder_with_local_changes
        )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if direction is not UNSET:
            field_dict["direction"] = direction
        if force_refresh_native_tables is not UNSET:
            field_dict["forceRefreshNativeTables"] = force_refresh_native_tables
        if detect_cron_schedules is not UNSET:
            field_dict["detectCronSchedules"] = detect_cron_schedules
        if sources is not UNSET:
            field_dict["sources"] = sources
        if sensors is not UNSET:
            field_dict["sensors"] = sensors
        if rules is not UNSET:
            field_dict["rules"] = rules
        if checks is not UNSET:
            field_dict["checks"] = checks
        if settings is not UNSET:
            field_dict["settings"] = settings
        if credentials is not UNSET:
            field_dict["credentials"] = credentials
        if dictionaries is not UNSET:
            field_dict["dictionaries"] = dictionaries
        if patterns is not UNSET:
            field_dict["patterns"] = patterns
        if data_sensor_readouts is not UNSET:
            field_dict["dataSensorReadouts"] = data_sensor_readouts
        if data_check_results is not UNSET:
            field_dict["dataCheckResults"] = data_check_results
        if data_statistics is not UNSET:
            field_dict["dataStatistics"] = data_statistics
        if data_errors is not UNSET:
            field_dict["dataErrors"] = data_errors
        if data_incidents is not UNSET:
            field_dict["dataIncidents"] = data_incidents
        if synchronize_folder_with_local_changes is not UNSET:
            field_dict["synchronizeFolderWithLocalChanges"] = (
                synchronize_folder_with_local_changes
            )

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _direction = d.pop("direction", UNSET)
        direction: Union[Unset, FileSynchronizationDirection]
        if isinstance(_direction, Unset):
            direction = UNSET
        else:
            direction = FileSynchronizationDirection(_direction)

        force_refresh_native_tables = d.pop("forceRefreshNativeTables", UNSET)

        detect_cron_schedules = d.pop("detectCronSchedules", UNSET)

        sources = d.pop("sources", UNSET)

        sensors = d.pop("sensors", UNSET)

        rules = d.pop("rules", UNSET)

        checks = d.pop("checks", UNSET)

        settings = d.pop("settings", UNSET)

        credentials = d.pop("credentials", UNSET)

        dictionaries = d.pop("dictionaries", UNSET)

        patterns = d.pop("patterns", UNSET)

        data_sensor_readouts = d.pop("dataSensorReadouts", UNSET)

        data_check_results = d.pop("dataCheckResults", UNSET)

        data_statistics = d.pop("dataStatistics", UNSET)

        data_errors = d.pop("dataErrors", UNSET)

        data_incidents = d.pop("dataIncidents", UNSET)

        synchronize_folder_with_local_changes = d.pop(
            "synchronizeFolderWithLocalChanges", UNSET
        )

        synchronize_multiple_folders_dqo_queue_job_parameters = cls(
            direction=direction,
            force_refresh_native_tables=force_refresh_native_tables,
            detect_cron_schedules=detect_cron_schedules,
            sources=sources,
            sensors=sensors,
            rules=rules,
            checks=checks,
            settings=settings,
            credentials=credentials,
            dictionaries=dictionaries,
            patterns=patterns,
            data_sensor_readouts=data_sensor_readouts,
            data_check_results=data_check_results,
            data_statistics=data_statistics,
            data_errors=data_errors,
            data_incidents=data_incidents,
            synchronize_folder_with_local_changes=synchronize_folder_with_local_changes,
        )

        synchronize_multiple_folders_dqo_queue_job_parameters.additional_properties = d
        return synchronize_multiple_folders_dqo_queue_job_parameters

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
