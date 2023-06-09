from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_data_ingestion_delay_check_spec import (
        TableDataIngestionDelayCheckSpec,
    )
    from ..models.table_days_since_most_recent_event_check_spec import (
        TableDaysSinceMostRecentEventCheckSpec,
    )
    from ..models.table_days_since_most_recent_ingestion_check_spec import (
        TableDaysSinceMostRecentIngestionCheckSpec,
    )
    from ..models.table_partition_reload_lag_check_spec import (
        TablePartitionReloadLagCheckSpec,
    )


T = TypeVar("T", bound="TableTimelinessDailyPartitionedChecksSpec")


@attr.s(auto_attribs=True)
class TableTimelinessDailyPartitionedChecksSpec:
    """
    Attributes:
        daily_partition_days_since_most_recent_event (Union[Unset, TableDaysSinceMostRecentEventCheckSpec]):
        daily_partition_data_ingestion_delay (Union[Unset, TableDataIngestionDelayCheckSpec]):
        daily_partition_days_since_most_recent_ingestion (Union[Unset, TableDaysSinceMostRecentIngestionCheckSpec]):
        daily_partition_reload_lag (Union[Unset, TablePartitionReloadLagCheckSpec]):
    """

    daily_partition_days_since_most_recent_event: Union[
        Unset, "TableDaysSinceMostRecentEventCheckSpec"
    ] = UNSET
    daily_partition_data_ingestion_delay: Union[
        Unset, "TableDataIngestionDelayCheckSpec"
    ] = UNSET
    daily_partition_days_since_most_recent_ingestion: Union[
        Unset, "TableDaysSinceMostRecentIngestionCheckSpec"
    ] = UNSET
    daily_partition_reload_lag: Union[Unset, "TablePartitionReloadLagCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        daily_partition_days_since_most_recent_event: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.daily_partition_days_since_most_recent_event, Unset):
            daily_partition_days_since_most_recent_event = (
                self.daily_partition_days_since_most_recent_event.to_dict()
            )

        daily_partition_data_ingestion_delay: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_data_ingestion_delay, Unset):
            daily_partition_data_ingestion_delay = (
                self.daily_partition_data_ingestion_delay.to_dict()
            )

        daily_partition_days_since_most_recent_ingestion: Union[
            Unset, Dict[str, Any]
        ] = UNSET
        if not isinstance(self.daily_partition_days_since_most_recent_ingestion, Unset):
            daily_partition_days_since_most_recent_ingestion = (
                self.daily_partition_days_since_most_recent_ingestion.to_dict()
            )

        daily_partition_reload_lag: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_partition_reload_lag, Unset):
            daily_partition_reload_lag = self.daily_partition_reload_lag.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if daily_partition_days_since_most_recent_event is not UNSET:
            field_dict[
                "daily_partition_days_since_most_recent_event"
            ] = daily_partition_days_since_most_recent_event
        if daily_partition_data_ingestion_delay is not UNSET:
            field_dict[
                "daily_partition_data_ingestion_delay"
            ] = daily_partition_data_ingestion_delay
        if daily_partition_days_since_most_recent_ingestion is not UNSET:
            field_dict[
                "daily_partition_days_since_most_recent_ingestion"
            ] = daily_partition_days_since_most_recent_ingestion
        if daily_partition_reload_lag is not UNSET:
            field_dict["daily_partition_reload_lag"] = daily_partition_reload_lag

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_data_ingestion_delay_check_spec import (
            TableDataIngestionDelayCheckSpec,
        )
        from ..models.table_days_since_most_recent_event_check_spec import (
            TableDaysSinceMostRecentEventCheckSpec,
        )
        from ..models.table_days_since_most_recent_ingestion_check_spec import (
            TableDaysSinceMostRecentIngestionCheckSpec,
        )
        from ..models.table_partition_reload_lag_check_spec import (
            TablePartitionReloadLagCheckSpec,
        )

        d = src_dict.copy()
        _daily_partition_days_since_most_recent_event = d.pop(
            "daily_partition_days_since_most_recent_event", UNSET
        )
        daily_partition_days_since_most_recent_event: Union[
            Unset, TableDaysSinceMostRecentEventCheckSpec
        ]
        if isinstance(_daily_partition_days_since_most_recent_event, Unset):
            daily_partition_days_since_most_recent_event = UNSET
        else:
            daily_partition_days_since_most_recent_event = (
                TableDaysSinceMostRecentEventCheckSpec.from_dict(
                    _daily_partition_days_since_most_recent_event
                )
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

        _daily_partition_days_since_most_recent_ingestion = d.pop(
            "daily_partition_days_since_most_recent_ingestion", UNSET
        )
        daily_partition_days_since_most_recent_ingestion: Union[
            Unset, TableDaysSinceMostRecentIngestionCheckSpec
        ]
        if isinstance(_daily_partition_days_since_most_recent_ingestion, Unset):
            daily_partition_days_since_most_recent_ingestion = UNSET
        else:
            daily_partition_days_since_most_recent_ingestion = (
                TableDaysSinceMostRecentIngestionCheckSpec.from_dict(
                    _daily_partition_days_since_most_recent_ingestion
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
            daily_partition_days_since_most_recent_event=daily_partition_days_since_most_recent_event,
            daily_partition_data_ingestion_delay=daily_partition_data_ingestion_delay,
            daily_partition_days_since_most_recent_ingestion=daily_partition_days_since_most_recent_ingestion,
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
