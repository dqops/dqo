import datetime
from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field
from dateutil.parser import isoparse

from ..types import UNSET, Unset

T = TypeVar("T", bound="TimeWindowFilterParameters")


@_attrs_define
class TimeWindowFilterParameters:
    """Time window configuration for partitioned checks (the number of recent days or months to analyze in an incremental
    mode) or an absolute time range to analyze.

        Attributes:
            daily_partitioning_recent_days (Union[Unset, int]): The number of recent days to analyze incrementally by daily
                partitioned data quality checks.
            daily_partitioning_include_today (Union[Unset, bool]): Analyze also today and later days when running daily
                partitioned checks. By default, daily partitioned checks will not analyze today and future dates. Setting true
                will disable filtering the end dates.
            monthly_partitioning_recent_months (Union[Unset, int]): The number of recent months to analyze incrementally by
                monthly partitioned data quality checks.
            monthly_partitioning_include_current_month (Union[Unset, bool]): Analyze also the current month and later months
                when running monthly partitioned checks. By default, monthly partitioned checks will not analyze the current
                month and future months. Setting true will disable filtering the end dates.
            from_date (Union[Unset, datetime.date]): Analyze the data since the given date (inclusive). The date should be
                an ISO 8601 date (yyyy-MM-dd). The analyzed table must have the timestamp column properly configured, it is the
                column that is used for filtering the date and time ranges. Setting the beginning date overrides recent days and
                recent months.
            from_date_time (Union[Unset, datetime.datetime]): Analyze the data since the given date and time (inclusive).
                The date and time should be an ISO 8601 local date and time without the time zone (yyyy-MM-dd HH:mm:ss). The
                analyzed table must have the timestamp column properly configured, it is the column that is used for filtering
                the date and time ranges. Setting the beginning date and time overrides recent days and recent months.
            from_date_time_offset (Union[Unset, datetime.datetime]): Analyze the data since the given date and time with a
                time zone offset (inclusive). The date and time should be an ISO 8601 date and time followed by a time zone
                offset (yyyy-MM-dd HH:mm:ss). For example: 2023-02-20 14:10:00+02. The analyzed table must have the timestamp
                column properly configured, it is the column that is used for filtering the date and time ranges. Setting the
                beginning date and time overrides recent days and recent months.
            to_date (Union[Unset, datetime.date]): Analyze the data until the given date (exclusive, the given date and the
                following dates are not analyzed). The date should be an ISO 8601 date (YYYY-MM-DD). The analyzed table must
                have the timestamp column properly configured, it is the column that is used for filtering the date and time
                ranges. Setting the end date overrides the parameters to disable analyzing today or the current month.
            to_date_time (Union[Unset, datetime.datetime]): Analyze the data until the given date and time (exclusive). The
                date should be an ISO 8601 date (yyyy-MM-dd). The analyzed table must have the timestamp column properly
                configured, it is the column that is used for filtering the date and time ranges. Setting the end date and time
                overrides the parameters to disable analyzing today or the current month.
            to_date_time_offset (Union[Unset, datetime.datetime]): Analyze the data until the given date and time with a
                time zone offset (exclusive). The date and time should be an ISO 8601 date and time followed by a time zone
                offset (yyyy-MM-dd HH:mm:ss). For example: 2023-02-20 14:10:00+02. The analyzed table must have the timestamp
                column properly configured, it is the column that is used for filtering the date and time ranges. Setting the
                end date and time overrides the parameters to disable analyzing today or the current month.
            where_filter (Union[Unset, str]): An additional filter which must be a valid SQL predicate (an SQL expression
                that returns 'true' or 'false') that is added to the WHERE clause of the SQL query that DQOps will run on the
                data source. The purpose of a custom filter is to analyze only a subset of data, for example, when a new batch
                of records is loaded, and the data quality checks are evaluated as a data contract. All the records in that
                batch must tagged with the same value, and the passed predicate to find records from that batch would use the
                filter in the form: "{alias}.batch_id = 1". The filter can use replacement tokens {alias} to reference the
                analyzed table.
    """

    daily_partitioning_recent_days: Union[Unset, int] = UNSET
    daily_partitioning_include_today: Union[Unset, bool] = UNSET
    monthly_partitioning_recent_months: Union[Unset, int] = UNSET
    monthly_partitioning_include_current_month: Union[Unset, bool] = UNSET
    from_date: Union[Unset, datetime.date] = UNSET
    from_date_time: Union[Unset, datetime.datetime] = UNSET
    from_date_time_offset: Union[Unset, datetime.datetime] = UNSET
    to_date: Union[Unset, datetime.date] = UNSET
    to_date_time: Union[Unset, datetime.datetime] = UNSET
    to_date_time_offset: Union[Unset, datetime.datetime] = UNSET
    where_filter: Union[Unset, str] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        daily_partitioning_recent_days = self.daily_partitioning_recent_days
        daily_partitioning_include_today = self.daily_partitioning_include_today
        monthly_partitioning_recent_months = self.monthly_partitioning_recent_months
        monthly_partitioning_include_current_month = (
            self.monthly_partitioning_include_current_month
        )
        from_date: Union[Unset, str] = UNSET
        if not isinstance(self.from_date, Unset):
            from_date = self.from_date.isoformat()

        from_date_time: Union[Unset, str] = UNSET
        if not isinstance(self.from_date_time, Unset):
            from_date_time = self.from_date_time.isoformat()

        from_date_time_offset: Union[Unset, str] = UNSET
        if not isinstance(self.from_date_time_offset, Unset):
            from_date_time_offset = self.from_date_time_offset.isoformat()

        to_date: Union[Unset, str] = UNSET
        if not isinstance(self.to_date, Unset):
            to_date = self.to_date.isoformat()

        to_date_time: Union[Unset, str] = UNSET
        if not isinstance(self.to_date_time, Unset):
            to_date_time = self.to_date_time.isoformat()

        to_date_time_offset: Union[Unset, str] = UNSET
        if not isinstance(self.to_date_time_offset, Unset):
            to_date_time_offset = self.to_date_time_offset.isoformat()

        where_filter = self.where_filter

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if daily_partitioning_recent_days is not UNSET:
            field_dict["daily_partitioning_recent_days"] = (
                daily_partitioning_recent_days
            )
        if daily_partitioning_include_today is not UNSET:
            field_dict["daily_partitioning_include_today"] = (
                daily_partitioning_include_today
            )
        if monthly_partitioning_recent_months is not UNSET:
            field_dict["monthly_partitioning_recent_months"] = (
                monthly_partitioning_recent_months
            )
        if monthly_partitioning_include_current_month is not UNSET:
            field_dict["monthly_partitioning_include_current_month"] = (
                monthly_partitioning_include_current_month
            )
        if from_date is not UNSET:
            field_dict["from_date"] = from_date
        if from_date_time is not UNSET:
            field_dict["from_date_time"] = from_date_time
        if from_date_time_offset is not UNSET:
            field_dict["from_date_time_offset"] = from_date_time_offset
        if to_date is not UNSET:
            field_dict["to_date"] = to_date
        if to_date_time is not UNSET:
            field_dict["to_date_time"] = to_date_time
        if to_date_time_offset is not UNSET:
            field_dict["to_date_time_offset"] = to_date_time_offset
        if where_filter is not UNSET:
            field_dict["where_filter"] = where_filter

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

        _from_date = d.pop("from_date", UNSET)
        from_date: Union[Unset, datetime.date]
        if isinstance(_from_date, Unset):
            from_date = UNSET
        else:
            from_date = isoparse(_from_date).date()

        _from_date_time = d.pop("from_date_time", UNSET)
        from_date_time: Union[Unset, datetime.datetime]
        if isinstance(_from_date_time, Unset):
            from_date_time = UNSET
        else:
            from_date_time = isoparse(_from_date_time)

        _from_date_time_offset = d.pop("from_date_time_offset", UNSET)
        from_date_time_offset: Union[Unset, datetime.datetime]
        if isinstance(_from_date_time_offset, Unset):
            from_date_time_offset = UNSET
        else:
            from_date_time_offset = isoparse(_from_date_time_offset)

        _to_date = d.pop("to_date", UNSET)
        to_date: Union[Unset, datetime.date]
        if isinstance(_to_date, Unset):
            to_date = UNSET
        else:
            to_date = isoparse(_to_date).date()

        _to_date_time = d.pop("to_date_time", UNSET)
        to_date_time: Union[Unset, datetime.datetime]
        if isinstance(_to_date_time, Unset):
            to_date_time = UNSET
        else:
            to_date_time = isoparse(_to_date_time)

        _to_date_time_offset = d.pop("to_date_time_offset", UNSET)
        to_date_time_offset: Union[Unset, datetime.datetime]
        if isinstance(_to_date_time_offset, Unset):
            to_date_time_offset = UNSET
        else:
            to_date_time_offset = isoparse(_to_date_time_offset)

        where_filter = d.pop("where_filter", UNSET)

        time_window_filter_parameters = cls(
            daily_partitioning_recent_days=daily_partitioning_recent_days,
            daily_partitioning_include_today=daily_partitioning_include_today,
            monthly_partitioning_recent_months=monthly_partitioning_recent_months,
            monthly_partitioning_include_current_month=monthly_partitioning_include_current_month,
            from_date=from_date,
            from_date_time=from_date_time,
            from_date_time_offset=from_date_time_offset,
            to_date=to_date,
            to_date_time=to_date_time,
            to_date_time_offset=to_date_time_offset,
            where_filter=where_filter,
        )

        time_window_filter_parameters.additional_properties = d
        return time_window_filter_parameters

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
