from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="PartitionIncrementalTimeWindowSpec")


@_attrs_define
class PartitionIncrementalTimeWindowSpec:
    """
    Attributes:
        daily_partitioning_recent_days (Union[Unset, int]): Number of recent days that are analyzed by daily partitioned
            checks in incremental mode. The default value is 7 days back.
        daily_partitioning_include_today (Union[Unset, bool]): Analyze also today's data by daily partitioned checks in
            incremental mode. The default value is false, which means that the today's and the future partitions are not
            analyzed, only yesterday's partition and earlier daily partitions are analyzed because today's data could be
            still incomplete. Change the value to 'true' if the current day should be also analyzed. The change may require
            configuring the schedule for daily checks correctly, to run after the data load.
        monthly_partitioning_recent_months (Union[Unset, int]): Number of recent months that are analyzed by monthly
            partitioned checks in incremental mode. The default value is 1 month back which means the previous calendar
            month.
        monthly_partitioning_include_current_month (Union[Unset, bool]): Analyze also this month's data by monthly
            partitioned checks in incremental mode. The default value is false, which means that the current month is not
            analyzed and future data is also filtered out because the current month could be incomplete. Set the value to
            'true' if the current month should be analyzed before the end of the month. The schedule for running monthly
            checks should be also configured to run more frequently (daily, hourly, etc.).
    """

    daily_partitioning_recent_days: Union[Unset, int] = UNSET
    daily_partitioning_include_today: Union[Unset, bool] = UNSET
    monthly_partitioning_recent_months: Union[Unset, int] = UNSET
    monthly_partitioning_include_current_month: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        daily_partitioning_recent_days = self.daily_partitioning_recent_days
        daily_partitioning_include_today = self.daily_partitioning_include_today
        monthly_partitioning_recent_months = self.monthly_partitioning_recent_months
        monthly_partitioning_include_current_month = (
            self.monthly_partitioning_include_current_month
        )

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if daily_partitioning_recent_days is not UNSET:
            field_dict[
                "daily_partitioning_recent_days"
            ] = daily_partitioning_recent_days
        if daily_partitioning_include_today is not UNSET:
            field_dict[
                "daily_partitioning_include_today"
            ] = daily_partitioning_include_today
        if monthly_partitioning_recent_months is not UNSET:
            field_dict[
                "monthly_partitioning_recent_months"
            ] = monthly_partitioning_recent_months
        if monthly_partitioning_include_current_month is not UNSET:
            field_dict[
                "monthly_partitioning_include_current_month"
            ] = monthly_partitioning_include_current_month

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        daily_partitioning_recent_days = d.pop("daily_partitioning_recent_days", UNSET)

        daily_partitioning_include_today = d.pop(
            "daily_partitioning_include_today", UNSET
        )

        monthly_partitioning_recent_months = d.pop(
            "monthly_partitioning_recent_months", UNSET
        )

        monthly_partitioning_include_current_month = d.pop(
            "monthly_partitioning_include_current_month", UNSET
        )

        partition_incremental_time_window_spec = cls(
            daily_partitioning_recent_days=daily_partitioning_recent_days,
            daily_partitioning_include_today=daily_partitioning_include_today,
            monthly_partitioning_recent_months=monthly_partitioning_recent_months,
            monthly_partitioning_include_current_month=monthly_partitioning_include_current_month,
        )

        partition_incremental_time_window_spec.additional_properties = d
        return partition_incremental_time_window_spec

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
