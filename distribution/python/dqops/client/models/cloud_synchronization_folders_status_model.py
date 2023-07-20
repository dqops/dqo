from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..models.cloud_synchronization_folders_status_model_checks import (
    CloudSynchronizationFoldersStatusModelChecks,
)
from ..models.cloud_synchronization_folders_status_model_data_check_results import (
    CloudSynchronizationFoldersStatusModelDataCheckResults,
)
from ..models.cloud_synchronization_folders_status_model_data_errors import (
    CloudSynchronizationFoldersStatusModelDataErrors,
)
from ..models.cloud_synchronization_folders_status_model_data_incidents import (
    CloudSynchronizationFoldersStatusModelDataIncidents,
)
from ..models.cloud_synchronization_folders_status_model_data_sensor_readouts import (
    CloudSynchronizationFoldersStatusModelDataSensorReadouts,
)
from ..models.cloud_synchronization_folders_status_model_data_statistics import (
    CloudSynchronizationFoldersStatusModelDataStatistics,
)
from ..models.cloud_synchronization_folders_status_model_rules import (
    CloudSynchronizationFoldersStatusModelRules,
)
from ..models.cloud_synchronization_folders_status_model_sensors import (
    CloudSynchronizationFoldersStatusModelSensors,
)
from ..models.cloud_synchronization_folders_status_model_sources import (
    CloudSynchronizationFoldersStatusModelSources,
)
from ..types import UNSET, Unset

T = TypeVar("T", bound="CloudSynchronizationFoldersStatusModel")


@attr.s(auto_attribs=True)
class CloudSynchronizationFoldersStatusModel:
    """
    Attributes:
        sources (Union[Unset, CloudSynchronizationFoldersStatusModelSources]): The synchronization status of the
            "sources" folder.
        sensors (Union[Unset, CloudSynchronizationFoldersStatusModelSensors]): The synchronization status of the
            "sensors" folder.
        rules (Union[Unset, CloudSynchronizationFoldersStatusModelRules]): The synchronization status of the "rules"
            folder.
        checks (Union[Unset, CloudSynchronizationFoldersStatusModelChecks]): The synchronization status of the "checks"
            folder.
        data_sensor_readouts (Union[Unset, CloudSynchronizationFoldersStatusModelDataSensorReadouts]): The
            synchronization status of the ".data/sensor_readouts" folder.
        data_check_results (Union[Unset, CloudSynchronizationFoldersStatusModelDataCheckResults]): The synchronization
            status of the ".data/check_results" folder.
        data_statistics (Union[Unset, CloudSynchronizationFoldersStatusModelDataStatistics]): The synchronization status
            of the ".data/statistics" folder.
        data_errors (Union[Unset, CloudSynchronizationFoldersStatusModelDataErrors]): The synchronization status of the
            ".data/errors" folder.
        data_incidents (Union[Unset, CloudSynchronizationFoldersStatusModelDataIncidents]): The synchronization status
            of the ".data/incidents" folder.
    """

    sources: Union[Unset, CloudSynchronizationFoldersStatusModelSources] = UNSET
    sensors: Union[Unset, CloudSynchronizationFoldersStatusModelSensors] = UNSET
    rules: Union[Unset, CloudSynchronizationFoldersStatusModelRules] = UNSET
    checks: Union[Unset, CloudSynchronizationFoldersStatusModelChecks] = UNSET
    data_sensor_readouts: Union[
        Unset, CloudSynchronizationFoldersStatusModelDataSensorReadouts
    ] = UNSET
    data_check_results: Union[
        Unset, CloudSynchronizationFoldersStatusModelDataCheckResults
    ] = UNSET
    data_statistics: Union[
        Unset, CloudSynchronizationFoldersStatusModelDataStatistics
    ] = UNSET
    data_errors: Union[Unset, CloudSynchronizationFoldersStatusModelDataErrors] = UNSET
    data_incidents: Union[
        Unset, CloudSynchronizationFoldersStatusModelDataIncidents
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

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
        sources: Union[Unset, CloudSynchronizationFoldersStatusModelSources]
        if isinstance(_sources, Unset):
            sources = UNSET
        else:
            sources = CloudSynchronizationFoldersStatusModelSources(_sources)

        _sensors = d.pop("sensors", UNSET)
        sensors: Union[Unset, CloudSynchronizationFoldersStatusModelSensors]
        if isinstance(_sensors, Unset):
            sensors = UNSET
        else:
            sensors = CloudSynchronizationFoldersStatusModelSensors(_sensors)

        _rules = d.pop("rules", UNSET)
        rules: Union[Unset, CloudSynchronizationFoldersStatusModelRules]
        if isinstance(_rules, Unset):
            rules = UNSET
        else:
            rules = CloudSynchronizationFoldersStatusModelRules(_rules)

        _checks = d.pop("checks", UNSET)
        checks: Union[Unset, CloudSynchronizationFoldersStatusModelChecks]
        if isinstance(_checks, Unset):
            checks = UNSET
        else:
            checks = CloudSynchronizationFoldersStatusModelChecks(_checks)

        _data_sensor_readouts = d.pop("data_sensor_readouts", UNSET)
        data_sensor_readouts: Union[
            Unset, CloudSynchronizationFoldersStatusModelDataSensorReadouts
        ]
        if isinstance(_data_sensor_readouts, Unset):
            data_sensor_readouts = UNSET
        else:
            data_sensor_readouts = (
                CloudSynchronizationFoldersStatusModelDataSensorReadouts(
                    _data_sensor_readouts
                )
            )

        _data_check_results = d.pop("data_check_results", UNSET)
        data_check_results: Union[
            Unset, CloudSynchronizationFoldersStatusModelDataCheckResults
        ]
        if isinstance(_data_check_results, Unset):
            data_check_results = UNSET
        else:
            data_check_results = CloudSynchronizationFoldersStatusModelDataCheckResults(
                _data_check_results
            )

        _data_statistics = d.pop("data_statistics", UNSET)
        data_statistics: Union[
            Unset, CloudSynchronizationFoldersStatusModelDataStatistics
        ]
        if isinstance(_data_statistics, Unset):
            data_statistics = UNSET
        else:
            data_statistics = CloudSynchronizationFoldersStatusModelDataStatistics(
                _data_statistics
            )

        _data_errors = d.pop("data_errors", UNSET)
        data_errors: Union[Unset, CloudSynchronizationFoldersStatusModelDataErrors]
        if isinstance(_data_errors, Unset):
            data_errors = UNSET
        else:
            data_errors = CloudSynchronizationFoldersStatusModelDataErrors(_data_errors)

        _data_incidents = d.pop("data_incidents", UNSET)
        data_incidents: Union[
            Unset, CloudSynchronizationFoldersStatusModelDataIncidents
        ]
        if isinstance(_data_incidents, Unset):
            data_incidents = UNSET
        else:
            data_incidents = CloudSynchronizationFoldersStatusModelDataIncidents(
                _data_incidents
            )

        cloud_synchronization_folders_status_model = cls(
            sources=sources,
            sensors=sensors,
            rules=rules,
            checks=checks,
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
