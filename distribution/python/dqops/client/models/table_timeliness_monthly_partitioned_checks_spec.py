from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_data_ingestion_delay_check_spec import (
        TableDataIngestionDelayCheckSpec,
    )
    from ..models.table_partition_reload_lag_check_spec import (
        TablePartitionReloadLagCheckSpec,
    )
    from ..models.table_timeliness_monthly_partitioned_checks_spec_custom_checks import (
        TableTimelinessMonthlyPartitionedChecksSpecCustomChecks,
    )


T = TypeVar("T", bound="TableTimelinessMonthlyPartitionedChecksSpec")


@_attrs_define
class TableTimelinessMonthlyPartitionedChecksSpec:
    """
    Attributes:
        custom_checks (Union[Unset, TableTimelinessMonthlyPartitionedChecksSpecCustomChecks]): Dictionary of additional
            custom checks within this category. The keys are check names defined in the definition section. The sensor
            parameters and rules should match the type of the configured sensor and rule for the custom check.
        monthly_partition_data_ingestion_delay (Union[Unset, TableDataIngestionDelayCheckSpec]):
        monthly_partition_reload_lag (Union[Unset, TablePartitionReloadLagCheckSpec]):
    """

    custom_checks: Union[
        Unset, "TableTimelinessMonthlyPartitionedChecksSpecCustomChecks"
    ] = UNSET
    monthly_partition_data_ingestion_delay: Union[
        Unset, "TableDataIngestionDelayCheckSpec"
    ] = UNSET
    monthly_partition_reload_lag: Union[Unset, "TablePartitionReloadLagCheckSpec"] = (
        UNSET
    )
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        custom_checks: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.custom_checks, Unset):
            custom_checks = self.custom_checks.to_dict()

        monthly_partition_data_ingestion_delay: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_data_ingestion_delay, Unset):
            monthly_partition_data_ingestion_delay = (
                self.monthly_partition_data_ingestion_delay.to_dict()
            )

        monthly_partition_reload_lag: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_partition_reload_lag, Unset):
            monthly_partition_reload_lag = self.monthly_partition_reload_lag.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if custom_checks is not UNSET:
            field_dict["custom_checks"] = custom_checks
        if monthly_partition_data_ingestion_delay is not UNSET:
            field_dict["monthly_partition_data_ingestion_delay"] = (
                monthly_partition_data_ingestion_delay
            )
        if monthly_partition_reload_lag is not UNSET:
            field_dict["monthly_partition_reload_lag"] = monthly_partition_reload_lag

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_data_ingestion_delay_check_spec import (
            TableDataIngestionDelayCheckSpec,
        )
        from ..models.table_partition_reload_lag_check_spec import (
            TablePartitionReloadLagCheckSpec,
        )
        from ..models.table_timeliness_monthly_partitioned_checks_spec_custom_checks import (
            TableTimelinessMonthlyPartitionedChecksSpecCustomChecks,
        )

        d = src_dict.copy()
        _custom_checks = d.pop("custom_checks", UNSET)
        custom_checks: Union[
            Unset, TableTimelinessMonthlyPartitionedChecksSpecCustomChecks
        ]
        if isinstance(_custom_checks, Unset):
            custom_checks = UNSET
        else:
            custom_checks = (
                TableTimelinessMonthlyPartitionedChecksSpecCustomChecks.from_dict(
                    _custom_checks
                )
            )

        _monthly_partition_data_ingestion_delay = d.pop(
            "monthly_partition_data_ingestion_delay", UNSET
        )
        monthly_partition_data_ingestion_delay: Union[
            Unset, TableDataIngestionDelayCheckSpec
        ]
        if isinstance(_monthly_partition_data_ingestion_delay, Unset):
            monthly_partition_data_ingestion_delay = UNSET
        else:
            monthly_partition_data_ingestion_delay = (
                TableDataIngestionDelayCheckSpec.from_dict(
                    _monthly_partition_data_ingestion_delay
                )
            )

        _monthly_partition_reload_lag = d.pop("monthly_partition_reload_lag", UNSET)
        monthly_partition_reload_lag: Union[Unset, TablePartitionReloadLagCheckSpec]
        if isinstance(_monthly_partition_reload_lag, Unset):
            monthly_partition_reload_lag = UNSET
        else:
            monthly_partition_reload_lag = TablePartitionReloadLagCheckSpec.from_dict(
                _monthly_partition_reload_lag
            )

        table_timeliness_monthly_partitioned_checks_spec = cls(
            custom_checks=custom_checks,
            monthly_partition_data_ingestion_delay=monthly_partition_data_ingestion_delay,
            monthly_partition_reload_lag=monthly_partition_reload_lag,
        )

        table_timeliness_monthly_partitioned_checks_spec.additional_properties = d
        return table_timeliness_monthly_partitioned_checks_spec

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
