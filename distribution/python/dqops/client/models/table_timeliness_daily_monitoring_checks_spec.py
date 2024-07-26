from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_data_freshness_anomaly_check_spec import (
        TableDataFreshnessAnomalyCheckSpec,
    )
    from ..models.table_data_freshness_check_spec import TableDataFreshnessCheckSpec
    from ..models.table_data_ingestion_delay_check_spec import (
        TableDataIngestionDelayCheckSpec,
    )
    from ..models.table_data_staleness_check_spec import TableDataStalenessCheckSpec
    from ..models.table_timeliness_daily_monitoring_checks_spec_custom_checks import (
        TableTimelinessDailyMonitoringChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="TableTimelinessDailyMonitoringChecksSpec")


@_attrs_define
class TableTimelinessDailyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, TableTimelinessDailyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        daily_data_freshness (Union[Unset, TableDataFreshnessCheckSpec]):
        daily_data_freshness_anomaly (Union[Unset, TableDataFreshnessAnomalyCheckSpec]):
        daily_data_staleness (Union[Unset, TableDataStalenessCheckSpec]):
        daily_data_ingestion_delay (Union[Unset, TableDataIngestionDelayCheckSpec]):
    """

    custom_checks: Union[
        Unset, "TableTimelinessDailyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    daily_data_freshness: Union[Unset, "TableDataFreshnessCheckSpec"] = UNSET
    daily_data_freshness_anomaly: Union[Unset, "TableDataFreshnessAnomalyCheckSpec"] = (
        UNSET
    )
    daily_data_staleness: Union[Unset, "TableDataStalenessCheckSpec"] = UNSET
    daily_data_ingestion_delay: Union[Unset, "TableDataIngestionDelayCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        daily_data_freshness: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_data_freshness, Unset):
            daily_data_freshness = self.daily_data_freshness.to_dict()

        daily_data_freshness_anomaly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_data_freshness_anomaly, Unset):
            daily_data_freshness_anomaly = self.daily_data_freshness_anomaly.to_dict()

        daily_data_staleness: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_data_staleness, Unset):
            daily_data_staleness = self.daily_data_staleness.to_dict()

        daily_data_ingestion_delay: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_data_ingestion_delay, Unset):
            daily_data_ingestion_delay = self.daily_data_ingestion_delay.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if daily_data_freshness is not UNSET:
            field_dict["daily_data_freshness"] = daily_data_freshness
        if daily_data_freshness_anomaly is not UNSET:
            field_dict["daily_data_freshness_anomaly"] = daily_data_freshness_anomaly
        if daily_data_staleness is not UNSET:
            field_dict["daily_data_staleness"] = daily_data_staleness
        if daily_data_ingestion_delay is not UNSET:
            field_dict["daily_data_ingestion_delay"] = daily_data_ingestion_delay

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_data_freshness_anomaly_check_spec import (
            TableDataFreshnessAnomalyCheckSpec,
        )
        from ..models.table_data_freshness_check_spec import TableDataFreshnessCheckSpec
        from ..models.table_data_ingestion_delay_check_spec import (
            TableDataIngestionDelayCheckSpec,
        )
        from ..models.table_data_staleness_check_spec import TableDataStalenessCheckSpec
        from ..models.table_timeliness_daily_monitoring_checks_spec_custom_checks import (
            TableTimelinessDailyMonitoringChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, TableTimelinessDailyMonitoringChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                TableTimelinessDailyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _daily_data_freshness = d.pop("daily_data_freshness", UNSET)
        daily_data_freshness: Union[Unset, TableDataFreshnessCheckSpec]
        if isinstance(_daily_data_freshness, Unset):
            daily_data_freshness = UNSET
        else:
            daily_data_freshness = TableDataFreshnessCheckSpec.from_dict(
                _daily_data_freshness
            )

        _daily_data_freshness_anomaly = d.pop("daily_data_freshness_anomaly", UNSET)
        daily_data_freshness_anomaly: Union[Unset, TableDataFreshnessAnomalyCheckSpec]
        if isinstance(_daily_data_freshness_anomaly, Unset):
            daily_data_freshness_anomaly = UNSET
        else:
            daily_data_freshness_anomaly = TableDataFreshnessAnomalyCheckSpec.from_dict(
                _daily_data_freshness_anomaly
            )

        _daily_data_staleness = d.pop("daily_data_staleness", UNSET)
        daily_data_staleness: Union[Unset, TableDataStalenessCheckSpec]
        if isinstance(_daily_data_staleness, Unset):
            daily_data_staleness = UNSET
        else:
            daily_data_staleness = TableDataStalenessCheckSpec.from_dict(
                _daily_data_staleness
            )

        _daily_data_ingestion_delay = d.pop("daily_data_ingestion_delay", UNSET)
        daily_data_ingestion_delay: Union[Unset, TableDataIngestionDelayCheckSpec]
        if isinstance(_daily_data_ingestion_delay, Unset):
            daily_data_ingestion_delay = UNSET
        else:
            daily_data_ingestion_delay = TableDataIngestionDelayCheckSpec.from_dict(
                _daily_data_ingestion_delay
            )

        table_timeliness_daily_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            daily_data_freshness=daily_data_freshness,
            daily_data_freshness_anomaly=daily_data_freshness_anomaly,
            daily_data_staleness=daily_data_staleness,
            daily_data_ingestion_delay=daily_data_ingestion_delay,
        )

        table_timeliness_daily_monitoring_checks_spec.additional_properties = d
        return table_timeliness_daily_monitoring_checks_spec

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
