from typing import TYPE_CHECKING, Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

if TYPE_CHECKING:
    from ..models.cron_schedule_spec import CronScheduleSpec


T = TypeVar("T", bound="CronSchedulesSpec")


@_attrs_define
class CronSchedulesSpec:
    """
    Attributes:
        profiling (Union[Unset, CronScheduleSpec]):
        monitoring_daily (Union[Unset, CronScheduleSpec]):
        monitoring_monthly (Union[Unset, CronScheduleSpec]):
        partitioned_daily (Union[Unset, CronScheduleSpec]):
        partitioned_monthly (Union[Unset, CronScheduleSpec]):
    """

    profiling: Union[Unset, "CronScheduleSpec"] = UNSET
    monitoring_daily: Union[Unset, "CronScheduleSpec"] = UNSET
    monitoring_monthly: Union[Unset, "CronScheduleSpec"] = UNSET
    partitioned_daily: Union[Unset, "CronScheduleSpec"] = UNSET
    partitioned_monthly: Union[Unset, "CronScheduleSpec"] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        profiling: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.profiling, Unset):
            profiling = self.profiling.to_dict()

        monitoring_daily: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monitoring_daily, Unset):
            monitoring_daily = self.monitoring_daily.to_dict()

        monitoring_monthly: Union[Unset, Dict[str, Any]] = UNSET
        if not isinstance(self.monitoring_monthly, Unset):
            monitoring_monthly = self.monitoring_monthly.to_dict()

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
        if monitoring_daily is not UNSET:
            field_dict["monitoring_daily"] = monitoring_daily
        if monitoring_monthly is not UNSET:
            field_dict["monitoring_monthly"] = monitoring_monthly
        if partitioned_daily is not UNSET:
            field_dict["partitioned_daily"] = partitioned_daily
        if partitioned_monthly is not UNSET:
            field_dict["partitioned_monthly"] = partitioned_monthly

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        from ..models.cron_schedule_spec import CronScheduleSpec

        d = src_dict.copy()
        _profiling = d.pop("profiling", UNSET)
        profiling: Union[Unset, CronScheduleSpec]
        if isinstance(_profiling, Unset):
            profiling = UNSET
        else:
            profiling = CronScheduleSpec.from_dict(_profiling)

        _monitoring_daily = d.pop("monitoring_daily", UNSET)
        monitoring_daily: Union[Unset, CronScheduleSpec]
        if isinstance(_monitoring_daily, Unset):
            monitoring_daily = UNSET
        else:
            monitoring_daily = CronScheduleSpec.from_dict(_monitoring_daily)

        _monitoring_monthly = d.pop("monitoring_monthly", UNSET)
        monitoring_monthly: Union[Unset, CronScheduleSpec]
        if isinstance(_monitoring_monthly, Unset):
            monitoring_monthly = UNSET
        else:
            monitoring_monthly = CronScheduleSpec.from_dict(_monitoring_monthly)

        _partitioned_daily = d.pop("partitioned_daily", UNSET)
        partitioned_daily: Union[Unset, CronScheduleSpec]
        if isinstance(_partitioned_daily, Unset):
            partitioned_daily = UNSET
        else:
            partitioned_daily = CronScheduleSpec.from_dict(_partitioned_daily)

        _partitioned_monthly = d.pop("partitioned_monthly", UNSET)
        partitioned_monthly: Union[Unset, CronScheduleSpec]
        if isinstance(_partitioned_monthly, Unset):
            partitioned_monthly = UNSET
        else:
            partitioned_monthly = CronScheduleSpec.from_dict(_partitioned_monthly)

        cron_schedules_spec = cls(
            profiling=profiling,
            monitoring_daily=monitoring_daily,
            monitoring_monthly=monitoring_monthly,
            partitioned_daily=partitioned_daily,
            partitioned_monthly=partitioned_monthly,
        )

        cron_schedules_spec.additional_properties = d
        return cron_schedules_spec

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
