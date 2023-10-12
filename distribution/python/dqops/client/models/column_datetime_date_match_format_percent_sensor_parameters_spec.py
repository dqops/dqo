from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.datetime_built_in_date_formats import DatetimeBuiltInDateFormats
from ..types import UNSET, Unset

T = TypeVar("T", bound="ColumnDatetimeDateMatchFormatPercentSensorParametersSpec")


@_attrs_define
class ColumnDatetimeDateMatchFormatPercentSensorParametersSpec:
    """
    Attributes:
        filter_ (Union[Unset, str]): SQL WHERE clause added to the sensor query. Both the table level filter and a
            sensor query filter are added, separated by an AND operator.
        date_formats (Union[Unset, DatetimeBuiltInDateFormats]):
    """

    filter_: Union[Unset, str] = UNSET
    date_formats: Union[Unset, DatetimeBuiltInDateFormats] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        filter_ = self.filter_
        date_formats: Union[Unset, str] = UNSET
        if not isinstance(self.date_formats, Unset):
            date_formats = self.date_formats.value

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if filter_ is not UNSET:
            field_dict["filter"] = filter_
        if date_formats is not UNSET:
            field_dict["date_formats"] = date_formats

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        filter_ = d.pop("filter", UNSET)

        _date_formats = d.pop("date_formats", UNSET)
        date_formats: Union[Unset, DatetimeBuiltInDateFormats]
        if isinstance(_date_formats, Unset):
            date_formats = UNSET
        else:
            date_formats = DatetimeBuiltInDateFormats(_date_formats)

        column_datetime_date_match_format_percent_sensor_parameters_spec = cls(
            filter_=filter_,
            date_formats=date_formats,
        )

        column_datetime_date_match_format_percent_sensor_parameters_spec.additional_properties = (
            d
        )
        return column_datetime_date_match_format_percent_sensor_parameters_spec

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
