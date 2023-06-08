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


T = TypeVar("T", bound="TableTimelinessMonthlyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class TableTimelinessMonthlyRecurringChecksSpec:
    """
    Attributes:
        monthly_days_since_most_recent_event (Union[Unset, TableDaysSinceMostRecentEventCheckSpec]):
        monthly_data_ingestion_delay (Union[Unset, TableDataIngestionDelayCheckSpec]):
        monthly_days_since_most_recent_ingestion (Union[Unset, TableDaysSinceMostRecentIngestionCheckSpec]):
    """

    monthly_days_since_most_recent_event: Union[
        Unset, "TableDaysSinceMostRecentEventCheckSpec"
    ] = UNSET
    monthly_data_ingestion_delay: Union[
        Unset, "TableDataIngestionDelayCheckSpec"
    ] = UNSET
    monthly_days_since_most_recent_ingestion: Union[
        Unset, "TableDaysSinceMostRecentIngestionCheckSpec"
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        monthly_days_since_most_recent_event: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_days_since_most_recent_event, Unset):
            monthly_days_since_most_recent_event = (
                self.monthly_days_since_most_recent_event.to_dict()
            )

        monthly_data_ingestion_delay: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_data_ingestion_delay, Unset):
            monthly_data_ingestion_delay = self.monthly_data_ingestion_delay.to_dict()

        monthly_days_since_most_recent_ingestion: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monthly_days_since_most_recent_ingestion, Unset):
            monthly_days_since_most_recent_ingestion = (
                self.monthly_days_since_most_recent_ingestion.to_dict()
            )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if monthly_days_since_most_recent_event is not UNSET:
            field_dict[
                "monthly_days_since_most_recent_event"
            ] = monthly_days_since_most_recent_event
        if monthly_data_ingestion_delay is not UNSET:
            field_dict["monthly_data_ingestion_delay"] = monthly_data_ingestion_delay
        if monthly_days_since_most_recent_ingestion is not UNSET:
            field_dict[
                "monthly_days_since_most_recent_ingestion"
            ] = monthly_days_since_most_recent_ingestion

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
        _monthly_days_since_most_recent_event = d.pop(
            "monthly_days_since_most_recent_event", UNSET
        )
        monthly_days_since_most_recent_event: Union[
            Unset, TableDaysSinceMostRecentEventCheckSpec
        ]
        if isinstance(_monthly_days_since_most_recent_event, Unset):
            monthly_days_since_most_recent_event = UNSET
        else:
            monthly_days_since_most_recent_event = (
                TableDaysSinceMostRecentEventCheckSpec.from_dict(
                    _monthly_days_since_most_recent_event
                )
            )

        _monthly_data_ingestion_delay = d.pop("monthly_data_ingestion_delay", UNSET)
        monthly_data_ingestion_delay: Union[Unset, TableDataIngestionDelayCheckSpec]
        if isinstance(_monthly_data_ingestion_delay, Unset):
            monthly_data_ingestion_delay = UNSET
        else:
            monthly_data_ingestion_delay = TableDataIngestionDelayCheckSpec.from_dict(
                _monthly_data_ingestion_delay
            )

        _monthly_days_since_most_recent_ingestion = d.pop(
            "monthly_days_since_most_recent_ingestion", UNSET
        )
        monthly_days_since_most_recent_ingestion: Union[
            Unset, TableDaysSinceMostRecentIngestionCheckSpec
        ]
        if isinstance(_monthly_days_since_most_recent_ingestion, Unset):
            monthly_days_since_most_recent_ingestion = UNSET
        else:
            monthly_days_since_most_recent_ingestion = (
                TableDaysSinceMostRecentIngestionCheckSpec.from_dict(
                    _monthly_days_since_most_recent_ingestion
                )
            )

        table_timeliness_monthly_recurring_checks_spec = cls(
            monthly_days_since_most_recent_event=monthly_days_since_most_recent_event,
            monthly_data_ingestion_delay=monthly_data_ingestion_delay,
            monthly_days_since_most_recent_ingestion=monthly_days_since_most_recent_ingestion,
        )

        table_timeliness_monthly_recurring_checks_spec.additional_properties = d
        return table_timeliness_monthly_recurring_checks_spec

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
