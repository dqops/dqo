import datetime
from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field
from dateutil.parser import isoparse

from ..types import UNSET, Unset

T = TypeVar("T", bound="ColumnDateInRangePercentSensorParametersSpec")


@_attrs_define
class ColumnDateInRangePercentSensorParametersSpec:
    """
    Attributes:
        filter_ (Union[Unset, str]): SQL WHERE clause added to the sensor query. Both the table level filter and a
            sensor query filter are added, separated by an AND operator.
        min_date (Union[Unset, datetime.date]): The earliest accepted date.
        max_date (Union[Unset, datetime.date]): The latest accepted date.
    """

    filter_: Union[Unset, str] = UNSET
    min_date: Union[Unset, datetime.date] = UNSET
    max_date: Union[Unset, datetime.date] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        filter_ = self.filter_
        min_date: Union[Unset, str] = UNSET
        if not isinstance(self.min_date, Unset):
            min_date = self.min_date.isoformat()

        max_date: Union[Unset, str] = UNSET
        if not isinstance(self.max_date, Unset):
            max_date = self.max_date.isoformat()

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if filter_ is not UNSET:
            field_dict["filter"] = filter_
        if min_date is not UNSET:
            field_dict["min_date"] = min_date
        if max_date is not UNSET:
            field_dict["max_date"] = max_date

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        filter_ = d.pop("filter", UNSET)

        _min_date = d.pop("min_date", UNSET)
        min_date: Union[Unset, datetime.date]
        if isinstance(_min_date, Unset):
            min_date = UNSET
        else:
            min_date = isoparse(_min_date).date()

        _max_date = d.pop("max_date", UNSET)
        max_date: Union[Unset, datetime.date]
        if isinstance(_max_date, Unset):
            max_date = UNSET
        else:
            max_date = isoparse(_max_date).date()

        column_date_in_range_percent_sensor_parameters_spec = cls(
            filter_=filter_,
            min_date=min_date,
            max_date=max_date,
        )

        column_date_in_range_percent_sensor_parameters_spec.additional_properties = d
        return column_date_in_range_percent_sensor_parameters_spec

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
