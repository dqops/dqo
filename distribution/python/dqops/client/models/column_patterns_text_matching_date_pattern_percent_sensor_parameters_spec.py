from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..models.text_built_in_date_formats import TextBuiltInDateFormats
from ..types import UNSET, Unset

T = TypeVar(
    "T", bound="ColumnPatternsTextMatchingDatePatternPercentSensorParametersSpec"
)


@_attrs_define
class ColumnPatternsTextMatchingDatePatternPercentSensorParametersSpec:
    """
    Attributes:
        filter_ (Union[Unset, str]): SQL WHERE clause added to the sensor query. Both the table level filter and a
            sensor query filter are added, separated by an AND operator.
        date_format (Union[Unset, TextBuiltInDateFormats]):
    """

    filter_: Union[Unset, str] = UNSET
    date_format: Union[Unset, TextBuiltInDateFormats] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        filter_ = self.filter_
        date_format: Union[Unset, str] = UNSET
        if not isinstance(self.date_format, Unset):
            date_format = self.date_format.value

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if filter_ is not UNSET:
            field_dict["filter"] = filter_
        if date_format is not UNSET:
            field_dict["date_format"] = date_format

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        filter_ = d.pop("filter", UNSET)

        _date_format = d.pop("date_format", UNSET)
        date_format: Union[Unset, TextBuiltInDateFormats]
        if isinstance(_date_format, Unset):
            date_format = UNSET
        else:
            date_format = TextBuiltInDateFormats(_date_format)

        column_patterns_text_matching_date_pattern_percent_sensor_parameters_spec = cls(
            filter_=filter_,
            date_format=date_format,
        )

        column_patterns_text_matching_date_pattern_percent_sensor_parameters_spec.additional_properties = (
            d
        )
        return column_patterns_text_matching_date_pattern_percent_sensor_parameters_spec

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
