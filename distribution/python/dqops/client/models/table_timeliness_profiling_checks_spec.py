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


T = TypeVar("T", bound="TableTimelinessProfilingChecksSpec")


@attr.s(auto_attribs=True)
class TableTimelinessProfilingChecksSpec:
    """
    Attributes:
        days_since_most_recent_event (Union[Unset, TableDaysSinceMostRecentEventCheckSpec]):
        data_ingestion_delay (Union[Unset, TableDataIngestionDelayCheckSpec]):
        days_since_most_recent_ingestion (Union[Unset, TableDaysSinceMostRecentIngestionCheckSpec]):
    """

    days_since_most_recent_event: Union[
        Unset, "TableDaysSinceMostRecentEventCheckSpec"
    ] = UNSET
    data_ingestion_delay: Union[Unset, "TableDataIngestionDelayCheckSpec"] = UNSET
    days_since_most_recent_ingestion: Union[
        Unset, "TableDaysSinceMostRecentIngestionCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        days_since_most_recent_event: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.days_since_most_recent_event, Unset):
            days_since_most_recent_event = self.days_since_most_recent_event.to_dict()

        data_ingestion_delay: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.data_ingestion_delay, Unset):
            data_ingestion_delay = self.data_ingestion_delay.to_dict()

        days_since_most_recent_ingestion: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.days_since_most_recent_ingestion, Unset):
            days_since_most_recent_ingestion = (
                self.days_since_most_recent_ingestion.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if days_since_most_recent_event is not UNSET:
            field_dict["days_since_most_recent_event"] = days_since_most_recent_event
        if data_ingestion_delay is not UNSET:
            field_dict["data_ingestion_delay"] = data_ingestion_delay
        if days_since_most_recent_ingestion is not UNSET:
            field_dict[
                "days_since_most_recent_ingestion"
            ] = days_since_most_recent_ingestion

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
        _days_since_most_recent_event = d.pop("days_since_most_recent_event", UNSET)
        days_since_most_recent_event: Union[
            Unset, TableDaysSinceMostRecentEventCheckSpec
        ]
        if isinstance(_days_since_most_recent_event, Unset):
            days_since_most_recent_event = UNSET
        else:
            days_since_most_recent_event = (
                TableDaysSinceMostRecentEventCheckSpec.from_dict(
                    _days_since_most_recent_event
                )
            )

        _data_ingestion_delay = d.pop("data_ingestion_delay", UNSET)
        data_ingestion_delay: Union[Unset, TableDataIngestionDelayCheckSpec]
        if isinstance(_data_ingestion_delay, Unset):
            data_ingestion_delay = UNSET
        else:
            data_ingestion_delay = TableDataIngestionDelayCheckSpec.from_dict(
                _data_ingestion_delay
            )

        _days_since_most_recent_ingestion = d.pop(
            "days_since_most_recent_ingestion", UNSET
        )
        days_since_most_recent_ingestion: Union[
            Unset, TableDaysSinceMostRecentIngestionCheckSpec
        ]
        if isinstance(_days_since_most_recent_ingestion, Unset):
            days_since_most_recent_ingestion = UNSET
        else:
            days_since_most_recent_ingestion = (
                TableDaysSinceMostRecentIngestionCheckSpec.from_dict(
                    _days_since_most_recent_ingestion
                )
            )

        table_timeliness_profiling_checks_spec = cls(
            days_since_most_recent_event=days_since_most_recent_event,
            data_ingestion_delay=data_ingestion_delay,
            days_since_most_recent_ingestion=days_since_most_recent_ingestion,
        )

        table_timeliness_profiling_checks_spec.additional_properties = d
        return table_timeliness_profiling_checks_spec

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
