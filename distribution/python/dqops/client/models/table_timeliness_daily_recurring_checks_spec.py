from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.table_data_freshness_check_spec import TableDataFreshnessCheckSpec
    from ..models.table_data_ingestion_delay_check_spec import (
        TableDataIngestionDelayCheckSpec,
    )
    from ..models.table_data_staleness_check_spec import TableDataStalenessCheckSpec


T = TypeVar("T", bound="TableTimelinessDailyRecurringChecksSpec")


@attr.s(auto_attribs=True)
class TableTimelinessDailyRecurringChecksSpec:
    """
    Attributes:
        daily_data_freshness (Union[Unset, TableDataFreshnessCheckSpec]):
        daily_data_staleness (Union[Unset, TableDataStalenessCheckSpec]):
        daily_data_ingestion_delay (Union[Unset, TableDataIngestionDelayCheckSpec]):
    """

    daily_data_freshness: Union[Unset, "TableDataFreshnessCheckSpec"] = UNSET
    daily_data_staleness: Union[Unset, "TableDataStalenessCheckSpec"] = UNSET
    daily_data_ingestion_delay: Union[Unset, "TableDataIngestionDelayCheckSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        daily_data_freshness: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_data_freshness, Unset):
            daily_data_freshness = self.daily_data_freshness.to_dict()

        daily_data_staleness: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_data_staleness, Unset):
            daily_data_staleness = self.daily_data_staleness.to_dict()

        daily_data_ingestion_delay: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.daily_data_ingestion_delay, Unset):
            daily_data_ingestion_delay = self.daily_data_ingestion_delay.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if daily_data_freshness is not UNSET:
            field_dict["daily_data_freshness"] = daily_data_freshness
        if daily_data_staleness is not UNSET:
            field_dict["daily_data_staleness"] = daily_data_staleness
        if daily_data_ingestion_delay is not UNSET:
            field_dict["daily_data_ingestion_delay"] = daily_data_ingestion_delay

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.table_data_freshness_check_spec import TableDataFreshnessCheckSpec
        from ..models.table_data_ingestion_delay_check_spec import (
            TableDataIngestionDelayCheckSpec,
        )
        from ..models.table_data_staleness_check_spec import TableDataStalenessCheckSpec

        d = src_dict.copy()
        _daily_data_freshness = d.pop("daily_data_freshness", UNSET)
        daily_data_freshness: Union[Unset, TableDataFreshnessCheckSpec]
        if isinstance(_daily_data_freshness, Unset):
            daily_data_freshness = UNSET
        else:
            daily_data_freshness = TableDataFreshnessCheckSpec.from_dict(
                _daily_data_freshness
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

        table_timeliness_daily_recurring_checks_spec = cls(
            daily_data_freshness=daily_data_freshness,
            daily_data_staleness=daily_data_staleness,
            daily_data_ingestion_delay=daily_data_ingestion_delay,
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
