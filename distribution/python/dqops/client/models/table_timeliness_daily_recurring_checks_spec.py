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


T = TypeVar("T", bound="TableTimelinessDailyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class TableTimelinessDailyRecurringChecksSpec:
    """
    Attributes:
        daily_days_since_most_recent_event (Union[Unset, TableDaysSinceMostRecentEventCheckSpec]):
        daily_data_ingestion_delay (Union[Unset, TableDataIngestionDelayCheckSpec]):
        daily_days_since_most_recent_ingestion (Union[Unset, TableDaysSinceMostRecentIngestionCheckSpec]):
    """

    daily_days_since_most_recent_event: Union[
        Unset, "TableDaysSinceMostRecentEventCheckSpec"
    ] = UNSET
    daily_data_ingestion_delay: Union[Unset, "TableDataIngestionDelayCheckSpec"] = UNSET
    daily_days_since_most_recent_ingestion: Union[
        Unset, "TableDaysSinceMostRecentIngestionCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        daily_days_since_most_recent_event: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_days_since_most_recent_event, Unset):
            daily_days_since_most_recent_event = (
                self.daily_days_since_most_recent_event.to_dict()
            )

        daily_data_ingestion_delay: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_data_ingestion_delay, Unset):
            daily_data_ingestion_delay = self.daily_data_ingestion_delay.to_dict()

        daily_days_since_most_recent_ingestion: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_days_since_most_recent_ingestion, Unset):
            daily_days_since_most_recent_ingestion = (
                self.daily_days_since_most_recent_ingestion.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if daily_days_since_most_recent_event is not UNSET:
            field_dict[
                "daily_days_since_most_recent_event"
            ] = daily_days_since_most_recent_event
        if daily_data_ingestion_delay is not UNSET:
            field_dict["daily_data_ingestion_delay"] = daily_data_ingestion_delay
        if daily_days_since_most_recent_ingestion is not UNSET:
            field_dict[
                "daily_days_since_most_recent_ingestion"
            ] = daily_days_since_most_recent_ingestion

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

        d = src_dict.copy()
        _daily_days_since_most_recent_event = d.pop(
            "daily_days_since_most_recent_event", UNSET
        )
        daily_days_since_most_recent_event: Union[
            Unset, TableDaysSinceMostRecentEventCheckSpec
        ]
        if isinstance(_daily_days_since_most_recent_event, Unset):
            daily_days_since_most_recent_event = UNSET
        else:
            daily_days_since_most_recent_event = (
                TableDaysSinceMostRecentEventCheckSpec.from_dict(
                    _daily_days_since_most_recent_event
                )
            )

        _daily_data_ingestion_delay = d.pop("daily_data_ingestion_delay", UNSET)
        daily_data_ingestion_delay: Union[Unset, TableDataIngestionDelayCheckSpec]
        if isinstance(_daily_data_ingestion_delay, Unset):
            daily_data_ingestion_delay = UNSET
        else:
            daily_data_ingestion_delay = TableDataIngestionDelayCheckSpec.from_dict(
                _daily_data_ingestion_delay
            )

        _daily_days_since_most_recent_ingestion = d.pop(
            "daily_days_since_most_recent_ingestion", UNSET
        )
        daily_days_since_most_recent_ingestion: Union[
            Unset, TableDaysSinceMostRecentIngestionCheckSpec
        ]
        if isinstance(_daily_days_since_most_recent_ingestion, Unset):
            daily_days_since_most_recent_ingestion = UNSET
        else:
            daily_days_since_most_recent_ingestion = (
                TableDaysSinceMostRecentIngestionCheckSpec.from_dict(
                    _daily_days_since_most_recent_ingestion
                )
            )

        table_timeliness_daily_recurring_checks_spec = cls(
            daily_days_since_most_recent_event=daily_days_since_most_recent_event,
            daily_data_ingestion_delay=daily_data_ingestion_delay,
            daily_days_since_most_recent_ingestion=daily_days_since_most_recent_ingestion,
        )

        table_timeliness_daily_recurring_checks_spec.additional_properties = d
        return table_timeliness_daily_recurring_checks_spec

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
