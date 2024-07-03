from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_data_freshness_check_spec import TableDataFreshnessCheckSpec
    from ..models.table_data_ingestion_delay_check_spec import (
        TableDataIngestionDelayCheckSpec,
    )
    from ..models.table_data_staleness_check_spec import TableDataStalenessCheckSpec
    from ..models.table_timeliness_monthly_monitoring_checks_spec_custom_checks import (
        TableTimelinessMonthlyMonitoringChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="TableTimelinessMonthlyMonitoringChecksSpec")


@_attrs_define
class TableTimelinessMonthlyMonitoringChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, TableTimelinessMonthlyMonitoringChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_data_freshness (Union[Unset, TableDataFreshnessCheckSpec]):
        monthly_data_staleness (Union[Unset, TableDataStalenessCheckSpec]):
        monthly_data_ingestion_delay (Union[Unset, TableDataIngestionDelayCheckSpec]):
    """

    custom_checks: Union[
        Unset, "TableTimelinessMonthlyMonitoringChecksSpecCustomChecks"
    ] = UNSET
    monthly_data_freshness: Union[Unset, "TableDataFreshnessCheckSpec"] = UNSET
    monthly_data_staleness: Union[Unset, "TableDataStalenessCheckSpec"] = UNSET
    monthly_data_ingestion_delay: Union[Unset, "TableDataIngestionDelayCheckSpec"] = (
        UNSET
    )
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        monthly_data_freshness: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_data_freshness, Unset):
            monthly_data_freshness = self.monthly_data_freshness.to_dict()

        monthly_data_staleness: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_data_staleness, Unset):
            monthly_data_staleness = self.monthly_data_staleness.to_dict()

        monthly_data_ingestion_delay: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_data_ingestion_delay, Unset):
            monthly_data_ingestion_delay = self.monthly_data_ingestion_delay.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_data_freshness is not UNSET:
            field_dict["monthly_data_freshness"] = monthly_data_freshness
        if monthly_data_staleness is not UNSET:
            field_dict["monthly_data_staleness"] = monthly_data_staleness
        if monthly_data_ingestion_delay is not UNSET:
            field_dict["monthly_data_ingestion_delay"] = monthly_data_ingestion_delay

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_data_freshness_check_spec import TableDataFreshnessCheckSpec
        from ..models.table_data_ingestion_delay_check_spec import (
            TableDataIngestionDelayCheckSpec,
        )
        from ..models.table_data_staleness_check_spec import TableDataStalenessCheckSpec
        from ..models.table_timeliness_monthly_monitoring_checks_spec_custom_checks import (
            TableTimelinessMonthlyMonitoringChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, TableTimelinessMonthlyMonitoringChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                TableTimelinessMonthlyMonitoringChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _monthly_data_freshness = d.pop("monthly_data_freshness", UNSET)
        monthly_data_freshness: Union[Unset, TableDataFreshnessCheckSpec]
        if isinstance(_monthly_data_freshness, Unset):
            monthly_data_freshness = UNSET
        else:
            monthly_data_freshness = TableDataFreshnessCheckSpec.from_dict(
                _monthly_data_freshness
            )

        _monthly_data_staleness = d.pop("monthly_data_staleness", UNSET)
        monthly_data_staleness: Union[Unset, TableDataStalenessCheckSpec]
        if isinstance(_monthly_data_staleness, Unset):
            monthly_data_staleness = UNSET
        else:
            monthly_data_staleness = TableDataStalenessCheckSpec.from_dict(
                _monthly_data_staleness
            )

        _monthly_data_ingestion_delay = d.pop("monthly_data_ingestion_delay", UNSET)
        monthly_data_ingestion_delay: Union[Unset, TableDataIngestionDelayCheckSpec]
        if isinstance(_monthly_data_ingestion_delay, Unset):
            monthly_data_ingestion_delay = UNSET
        else:
            monthly_data_ingestion_delay = TableDataIngestionDelayCheckSpec.from_dict(
                _monthly_data_ingestion_delay
            )

        table_timeliness_monthly_monitoring_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_data_freshness=monthly_data_freshness,
            monthly_data_staleness=monthly_data_staleness,
            monthly_data_ingestion_delay=monthly_data_ingestion_delay,
        )

        table_timeliness_monthly_monitoring_checks_spec.additional_properties = d
        return table_timeliness_monthly_monitoring_checks_spec

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
