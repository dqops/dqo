from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.recurring_schedule_spec import RecurringScheduleSpec


T = TypeVar("T", bound="RecurringSchedulesSpec")


@attr.s(auto_attribs=True)
class RecurringSchedulesSpec:
    """
    Attributes:
        profiling (Union[Unset, RecurringScheduleSpec]):
        recurring_daily (Union[Unset, RecurringScheduleSpec]):
        recurring_monthly (Union[Unset, RecurringScheduleSpec]):
        partitioned_daily (Union[Unset, RecurringScheduleSpec]):
        partitioned_monthly (Union[Unset, RecurringScheduleSpec]):
    """

    profiling: Union[Unset, "RecurringScheduleSpec"] = UNSET
    recurring_daily: Union[Unset, "RecurringScheduleSpec"] = UNSET
    recurring_monthly: Union[Unset, "RecurringScheduleSpec"] = UNSET
    partitioned_daily: Union[Unset, "RecurringScheduleSpec"] = UNSET
    partitioned_monthly: Union[Unset, "RecurringScheduleSpec"] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        profiling: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profiling, Unset):
            profiling = self.profiling.to_dict()

        recurring_daily: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.recurring_daily, Unset):
            recurring_daily = self.recurring_daily.to_dict()

        recurring_monthly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.recurring_monthly, Unset):
            recurring_monthly = self.recurring_monthly.to_dict()

        partitioned_daily: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.partitioned_daily, Unset):
            partitioned_daily = self.partitioned_daily.to_dict()

        partitioned_monthly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.partitioned_monthly, Unset):
            partitioned_monthly = self.partitioned_monthly.to_dict()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if profiling is not UNSET:
            field_dict["profiling"] = profiling
        if recurring_daily is not UNSET:
            field_dict["recurring_daily"] = recurring_daily
        if recurring_monthly is not UNSET:
            field_dict["recurring_monthly"] = recurring_monthly
        if partitioned_daily is not UNSET:
            field_dict["partitioned_daily"] = partitioned_daily
        if partitioned_monthly is not UNSET:
            field_dict["partitioned_monthly"] = partitioned_monthly

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.recurring_schedule_spec import RecurringScheduleSpec

        d = src_dict.copy()
        _profiling = d.pop("profiling", UNSET)
        profiling: Union[Unset, RecurringScheduleSpec]
        if isinstance(_profiling, Unset):
            profiling = UNSET
        else:
            profiling = RecurringScheduleSpec.from_dict(_profiling)

        _recurring_daily = d.pop("recurring_daily", UNSET)
        recurring_daily: Union[Unset, RecurringScheduleSpec]
        if isinstance(_recurring_daily, Unset):
            recurring_daily = UNSET
        else:
            recurring_daily = RecurringScheduleSpec.from_dict(_recurring_daily)

        _recurring_monthly = d.pop("recurring_monthly", UNSET)
        recurring_monthly: Union[Unset, RecurringScheduleSpec]
        if isinstance(_recurring_monthly, Unset):
            recurring_monthly = UNSET
        else:
            recurring_monthly = RecurringScheduleSpec.from_dict(_recurring_monthly)

        _partitioned_daily = d.pop("partitioned_daily", UNSET)
        partitioned_daily: Union[Unset, RecurringScheduleSpec]
        if isinstance(_partitioned_daily, Unset):
            partitioned_daily = UNSET
        else:
            partitioned_daily = RecurringScheduleSpec.from_dict(_partitioned_daily)

        _partitioned_monthly = d.pop("partitioned_monthly", UNSET)
        partitioned_monthly: Union[Unset, RecurringScheduleSpec]
        if isinstance(_partitioned_monthly, Unset):
            partitioned_monthly = UNSET
        else:
            partitioned_monthly = RecurringScheduleSpec.from_dict(_partitioned_monthly)

        recurring_schedules_spec = cls(
            profiling=profiling,
            recurring_daily=recurring_daily,
            recurring_monthly=recurring_monthly,
            partitioned_daily=partitioned_daily,
            partitioned_monthly=partitioned_monthly,
        )

        recurring_schedules_spec.additional_properties = d
        return recurring_schedules_spec

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
