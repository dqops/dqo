import datetime
from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field
from dateutil.parser import isoparse

from ..types import UNSET, Unset

T = TypeVar("T", bound="IncidentCountsModel")


@_attrs_define
class IncidentCountsModel:
    """
    Attributes:
        total_count (Union[Unset, int]): Number of incidents in total.
        count_from_last_24_h (Union[Unset, int]): Number of incidents from the last 24h from now.
        count_from_last_7_days (Union[Unset, int]): Number of incidents from the last 7 days, the number of 7 * 24 hours
            from now.
        current_month_count (Union[Unset, int]): Number of incidents from the complete current month.
        current_month_date (Union[Unset, datetime.date]): The first day of the current month date.
        previous_month_count (Union[Unset, int]): Number of incidents from the complete previous month.
        previous_month_date (Union[Unset, datetime.date]): The first day of the previous month date.
    """

    total_count: Union[Unset, int] = UNSET
    count_from_last_24_h: Union[Unset, int] = UNSET
    count_from_last_7_days: Union[Unset, int] = UNSET
    current_month_count: Union[Unset, int] = UNSET
    current_month_date: Union[Unset, datetime.date] = UNSET
    previous_month_count: Union[Unset, int] = UNSET
    previous_month_date: Union[Unset, datetime.date] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        total_count = self.total_count
        count_from_last_24_h = self.count_from_last_24_h
        count_from_last_7_days = self.count_from_last_7_days
        current_month_count = self.current_month_count
        current_month_date: Union[Unset, str] = UNSET
        if not isinstance(self.current_month_date, Unset):
            current_month_date = self.current_month_date.isoformat()

        previous_month_count = self.previous_month_count
        previous_month_date: Union[Unset, str] = UNSET
        if not isinstance(self.previous_month_date, Unset):
            previous_month_date = self.previous_month_date.isoformat()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if total_count is not UNSET:
            field_dict["totalCount"] = total_count
        if count_from_last_24_h is not UNSET:
            field_dict["countFromLast24h"] = count_from_last_24_h
        if count_from_last_7_days is not UNSET:
            field_dict["countFromLast7days"] = count_from_last_7_days
        if current_month_count is not UNSET:
            field_dict["currentMonthCount"] = current_month_count
        if current_month_date is not UNSET:
            field_dict["currentMonthDate"] = current_month_date
        if previous_month_count is not UNSET:
            field_dict["previousMonthCount"] = previous_month_count
        if previous_month_date is not UNSET:
            field_dict["previousMonthDate"] = previous_month_date

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        total_count = d.pop("totalCount", UNSET)

        count_from_last_24_h = d.pop("countFromLast24h", UNSET)

        count_from_last_7_days = d.pop("countFromLast7days", UNSET)

        current_month_count = d.pop("currentMonthCount", UNSET)

        _current_month_date = d.pop("currentMonthDate", UNSET)
        current_month_date: Union[Unset, datetime.date]
        if isinstance(_current_month_date, Unset):
            current_month_date = UNSET
        else:
            current_month_date = isoparse(_current_month_date).date()

        previous_month_count = d.pop("previousMonthCount", UNSET)

        _previous_month_date = d.pop("previousMonthDate", UNSET)
        previous_month_date: Union[Unset, datetime.date]
        if isinstance(_previous_month_date, Unset):
            previous_month_date = UNSET
        else:
            previous_month_date = isoparse(_previous_month_date).date()

        incident_counts_model = cls(
            total_count=total_count,
            count_from_last_24_h=count_from_last_24_h,
            count_from_last_7_days=count_from_last_7_days,
            current_month_count=current_month_count,
            current_month_date=current_month_date,
            previous_month_count=previous_month_count,
            previous_month_date=previous_month_date,
        )

        incident_counts_model.additional_properties = d
        return incident_counts_model

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
