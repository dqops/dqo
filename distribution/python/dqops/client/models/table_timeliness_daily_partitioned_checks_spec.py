from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_data_freshness_check_spec import TableDataFreshnessCheckSpec
    from ..models.table_data_ingestion_delay_check_spec import (
        TableDataIngestionDelayCheckSpec,
    )
    from ..models.table_data_staleness_check_spec import TableDataStalenessCheckSpec
    from ..models.table_partition_reload_lag_check_spec import (
        TablePartitionReloadLagCheckSpec,
    )


T = TypeVar("T", bound="TableTimelinessDailyPartitionedChecksSpec")


@attr.s(auto_attribs=True)
class TableTimelinessDailyPartitionedChecksSpec:
    """
    Attributes:
        daily_partition_data_freshness (Union[Unset, TableDataFreshnessCheckSpec]):
        daily_partition_data_staleness (Union[Unset, TableDataStalenessCheckSpec]):
        daily_partition_data_ingestion_delay (Union[Unset, TableDataIngestionDelayCheckSpec]):
        daily_partition_reload_lag (Union[Unset, TablePartitionReloadLagCheckSpec]):
    """

    daily_partition_data_freshness: Union[Unset, "TableDataFreshnessCheckSpec"] = UNSET
    daily_partition_data_staleness: Union[Unset, "TableDataStalenessCheckSpec"] = UNSET
    daily_partition_data_ingestion_delay: Union[
        Unset, "TableDataIngestionDelayCheckSpec"
    ] = UNSET
    daily_partition_reload_lag: Union[Unset, "TablePartitionReloadLagCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        daily_partition_data_freshness: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_data_freshness, Unset):
            daily_partition_data_freshness = (
                self.daily_partition_data_freshness.to_dict()
            )

        daily_partition_data_staleness: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_data_staleness, Unset):
            daily_partition_data_staleness = (
                self.daily_partition_data_staleness.to_dict()
            )

        daily_partition_data_ingestion_delay: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_data_ingestion_delay, Unset):
            daily_partition_data_ingestion_delay = (
                self.daily_partition_data_ingestion_delay.to_dict()
            )

        daily_partition_reload_lag: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_reload_lag, Unset):
            daily_partition_reload_lag = self.daily_partition_reload_lag.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if daily_partition_data_freshness is not UNSET:
            field_dict[
                "daily_partition_data_freshness"
            ] = daily_partition_data_freshness
        if daily_partition_data_staleness is not UNSET:
            field_dict[
                "daily_partition_data_staleness"
            ] = daily_partition_data_staleness
        if daily_partition_data_ingestion_delay is not UNSET:
            field_dict[
                "daily_partition_data_ingestion_delay"
            ] = daily_partition_data_ingestion_delay
        if daily_partition_reload_lag is not UNSET:
            field_dict["daily_partition_reload_lag"] = daily_partition_reload_lag

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_data_freshness_check_spec import TableDataFreshnessCheckSpec
        from ..models.table_data_ingestion_delay_check_spec import (
            TableDataIngestionDelayCheckSpec,
        )
        from ..models.table_data_staleness_check_spec import TableDataStalenessCheckSpec
        from ..models.table_partition_reload_lag_check_spec import (
            TablePartitionReloadLagCheckSpec,
        )

        d = src_dict.copy()
        _daily_partition_data_freshness = d.pop("daily_partition_data_freshness", UNSET)
        daily_partition_data_freshness: Union[Unset, TableDataFreshnessCheckSpec]
        if isinstance(_daily_partition_data_freshness, Unset):
            daily_partition_data_freshness = UNSET
        else:
            daily_partition_data_freshness = TableDataFreshnessCheckSpec.from_dict(
                _daily_partition_data_freshness
            )

        _daily_partition_data_staleness = d.pop("daily_partition_data_staleness", UNSET)
        daily_partition_data_staleness: Union[Unset, TableDataStalenessCheckSpec]
        if isinstance(_daily_partition_data_staleness, Unset):
            daily_partition_data_staleness = UNSET
        else:
            daily_partition_data_staleness = TableDataStalenessCheckSpec.from_dict(
                _daily_partition_data_staleness
            )

        _daily_partition_data_ingestion_delay = d.pop(
            "daily_partition_data_ingestion_delay", UNSET
        )
        daily_partition_data_ingestion_delay: Union[
            Unset, TableDataIngestionDelayCheckSpec
        ]
        if isinstance(_daily_partition_data_ingestion_delay, Unset):
            daily_partition_data_ingestion_delay = UNSET
        else:
            daily_partition_data_ingestion_delay = (
                TableDataIngestionDelayCheckSpec.from_dict(
                    _daily_partition_data_ingestion_delay
                )
            )

        _daily_partition_reload_lag = d.pop("daily_partition_reload_lag", UNSET)
        daily_partition_reload_lag: Union[Unset, TablePartitionReloadLagCheckSpec]
        if isinstance(_daily_partition_reload_lag, Unset):
            daily_partition_reload_lag = UNSET
        else:
            daily_partition_reload_lag = TablePartitionReloadLagCheckSpec.from_dict(
                _daily_partition_reload_lag
            )

        table_timeliness_daily_partitioned_checks_spec = cls(
            daily_partition_data_freshness=daily_partition_data_freshness,
            daily_partition_data_staleness=daily_partition_data_staleness,
            daily_partition_data_ingestion_delay=daily_partition_data_ingestion_delay,
            daily_partition_reload_lag=daily_partition_reload_lag,
        )

        table_timeliness_daily_partitioned_checks_spec.additional_properties = d
        return table_timeliness_daily_partitioned_checks_spec

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
