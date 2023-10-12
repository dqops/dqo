from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar(
    "T", bound="ColumnStringsStringLengthBelowMinLengthPercentSensorParametersSpec"
)


@_attrs_define
class ColumnStringsStringLengthBelowMinLengthPercentSensorParametersSpec:
    """
    Attributes:
        filter_ (Union[Unset, str]): SQL WHERE clause added to the sensor query. Both the table level filter and a
            sensor query filter are added, separated by an AND operator.
        min_length (Union[Unset, int]): This field can be used to define custom length. In order to define custom
            length, user should write correct length as a integer. If length is not defined by user then default length is 0
    """

    filter_: Union[Unset, str] = UNSET
    min_length: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        filter_ = self.filter_
        min_length = self.min_length

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if filter_ is not UNSET:
            field_dict["filter"] = filter_
        if min_length is not UNSET:
            field_dict["min_length"] = min_length

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        filter_ = d.pop("filter", UNSET)

        min_length = d.pop("min_length", UNSET)

        column_strings_string_length_below_min_length_percent_sensor_parameters_spec = (
            cls(
                filter_=filter_,
                min_length=min_length,
            )
        )

        column_strings_string_length_below_min_length_percent_sensor_parameters_spec.additional_properties = (
            d
        )
        return (
            column_strings_string_length_below_min_length_percent_sensor_parameters_spec
        )

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
