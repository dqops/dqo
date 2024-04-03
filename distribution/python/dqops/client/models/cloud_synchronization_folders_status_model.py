from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.folder_synchronization_status import FolderSynchronizationStatus
from ..types import UNSET, Unset

T = TypeVar("T", bound="CloudSynchronizationFoldersStatusModel")


@_attrs_define
class CloudSynchronizationFoldersStatusModel:
    """
    Attributes:
        sources (Union[Unset, FolderSynchronizationStatus]):
        sensors (Union[Unset, FolderSynchronizationStatus]):
        rules (Union[Unset, FolderSynchronizationStatus]):
        checks (Union[Unset, FolderSynchronizationStatus]):
        settings (Union[Unset, FolderSynchronizationStatus]):
        credentials (Union[Unset, FolderSynchronizationStatus]):
        dictionaries (Union[Unset, FolderSynchronizationStatus]):
        patterns (Union[Unset, FolderSynchronizationStatus]):
        data_sensor_readouts (Union[Unset, FolderSynchronizationStatus]):
        data_check_results (Union[Unset, FolderSynchronizationStatus]):
        data_statistics (Union[Unset, FolderSynchronizationStatus]):
        data_errors (Union[Unset, FolderSynchronizationStatus]):
        data_incidents (Union[Unset, FolderSynchronizationStatus]):
    """

    sources: Union[Unset, FolderSynchronizationStatus] = UNSET
    sensors: Union[Unset, FolderSynchronizationStatus] = UNSET
    rules: Union[Unset, FolderSynchronizationStatus] = UNSET
    checks: Union[Unset, FolderSynchronizationStatus] = UNSET
    settings: Union[Unset, FolderSynchronizationStatus] = UNSET
    credentials: Union[Unset, FolderSynchronizationStatus] = UNSET
    dictionaries: Union[Unset, FolderSynchronizationStatus] = UNSET
    patterns: Union[Unset, FolderSynchronizationStatus] = UNSET
    data_sensor_readouts: Union[Unset, FolderSynchronizationStatus] = UNSET
    data_check_results: Union[Unset, FolderSynchronizationStatus] = UNSET
    data_statistics: Union[Unset, FolderSynchronizationStatus] = UNSET
    data_errors: Union[Unset, FolderSynchronizationStatus] = UNSET
    data_incidents: Union[Unset, FolderSynchronizationStatus] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        sources: Union[Unset, str] = UNSET
        if not isinstance(self.sources, Unset):
            sources = self.sources.value

        sensors: Union[Unset, str] = UNSET
        if not isinstance(self.sensors, Unset):
            sensors = self.sensors.value

        rules: Union[Unset, str] = UNSET
        if not isinstance(self.rules, Unset):
            rules = self.rules.value

        checks: Union[Unset, str] = UNSET
        if not isinstance(self.checks, Unset):
            checks = self.checks.value

        settings: Union[Unset, str] = UNSET
        if not isinstance(self.settings, Unset):
            settings = self.settings.value

        credentials: Union[Unset, str] = UNSET
        if not isinstance(self.credentials, Unset):
            credentials = self.credentials.value

        dictionaries: Union[Unset, str] = UNSET
        if not isinstance(self.dictionaries, Unset):
            dictionaries = self.dictionaries.value

        patterns: Union[Unset, str] = UNSET
        if not isinstance(self.patterns, Unset):
            patterns = self.patterns.value

        data_sensor_readouts: Union[Unset, str] = UNSET
        if not isinstance(self.data_sensor_readouts, Unset):
            data_sensor_readouts = self.data_sensor_readouts.value

        data_check_results: Union[Unset, str] = UNSET
        if not isinstance(self.data_check_results, Unset):
            data_check_results = self.data_check_results.value

        data_statistics: Union[Unset, str] = UNSET
        if not isinstance(self.data_statistics, Unset):
            data_statistics = self.data_statistics.value

        data_errors: Union[Unset, str] = UNSET
        if not isinstance(self.data_errors, Unset):
            data_errors = self.data_errors.value

        data_incidents: Union[Unset, str] = UNSET
        if not isinstance(self.data_incidents, Unset):
            data_incidents = self.data_incidents.value

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
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
            field_dict["data_sensor_readouts"] = data_sensor_readouts
        if data_check_results is not UNSET:
            field_dict["data_check_results"] = data_check_results
        if data_statistics is not UNSET:
            field_dict["data_statistics"] = data_statistics
        if data_errors is not UNSET:
            field_dict["data_errors"] = data_errors
        if data_incidents is not UNSET:
            field_dict["data_incidents"] = data_incidents

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        _sources = d.pop("sources", UNSET)
        sources: Union[Unset, FolderSynchronizationStatus]
        if isinstance(_sources, Unset):
            sources = UNSET
        else:
            sources = FolderSynchronizationStatus(_sources)

        _sensors = d.pop("sensors", UNSET)
        sensors: Union[Unset, FolderSynchronizationStatus]
        if isinstance(_sensors, Unset):
            sensors = UNSET
        else:
            sensors = FolderSynchronizationStatus(_sensors)

        _rules = d.pop("rules", UNSET)
        rules: Union[Unset, FolderSynchronizationStatus]
        if isinstance(_rules, Unset):
            rules = UNSET
        else:
            rules = FolderSynchronizationStatus(_rules)

        _checks = d.pop("checks", UNSET)
        checks: Union[Unset, FolderSynchronizationStatus]
        if isinstance(_checks, Unset):
            checks = UNSET
        else:
            checks = FolderSynchronizationStatus(_checks)

        _settings = d.pop("settings", UNSET)
        settings: Union[Unset, FolderSynchronizationStatus]
        if isinstance(_settings, Unset):
            settings = UNSET
        else:
            settings = FolderSynchronizationStatus(_settings)

        _credentials = d.pop("credentials", UNSET)
        credentials: Union[Unset, FolderSynchronizationStatus]
        if isinstance(_credentials, Unset):
            credentials = UNSET
        else:
            credentials = FolderSynchronizationStatus(_credentials)

        _dictionaries = d.pop("dictionaries", UNSET)
        dictionaries: Union[Unset, FolderSynchronizationStatus]
        if isinstance(_dictionaries, Unset):
            dictionaries = UNSET
        else:
            dictionaries = FolderSynchronizationStatus(_dictionaries)

        _patterns = d.pop("patterns", UNSET)
        patterns: Union[Unset, FolderSynchronizationStatus]
        if isinstance(_patterns, Unset):
            patterns = UNSET
        else:
            patterns = FolderSynchronizationStatus(_patterns)

        _data_sensor_readouts = d.pop("data_sensor_readouts", UNSET)
        data_sensor_readouts: Union[Unset, FolderSynchronizationStatus]
        if isinstance(_data_sensor_readouts, Unset):
            data_sensor_readouts = UNSET
        else:
            data_sensor_readouts = FolderSynchronizationStatus(_data_sensor_readouts)

        _data_check_results = d.pop("data_check_results", UNSET)
        data_check_results: Union[Unset, FolderSynchronizationStatus]
        if isinstance(_data_check_results, Unset):
            data_check_results = UNSET
        else:
            data_check_results = FolderSynchronizationStatus(_data_check_results)

        _data_statistics = d.pop("data_statistics", UNSET)
        data_statistics: Union[Unset, FolderSynchronizationStatus]
        if isinstance(_data_statistics, Unset):
            data_statistics = UNSET
        else:
            data_statistics = FolderSynchronizationStatus(_data_statistics)

        _data_errors = d.pop("data_errors", UNSET)
        data_errors: Union[Unset, FolderSynchronizationStatus]
        if isinstance(_data_errors, Unset):
            data_errors = UNSET
        else:
            data_errors = FolderSynchronizationStatus(_data_errors)

        _data_incidents = d.pop("data_incidents", UNSET)
        data_incidents: Union[Unset, FolderSynchronizationStatus]
        if isinstance(_data_incidents, Unset):
            data_incidents = UNSET
        else:
            data_incidents = FolderSynchronizationStatus(_data_incidents)

        cloud_synchronization_folders_status_model = cls(
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
        )

        cloud_synchronization_folders_status_model.additional_properties = d
        return cloud_synchronization_folders_status_model

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
