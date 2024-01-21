from typing import Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ColumnStringsExpectedTextValuesInUseCountSensorParametersSpec")


@_attrs_define
class ColumnStringsExpectedTextValuesInUseCountSensorParametersSpec:
    """
    Attributes:
        filter_ (Union[Unset, str]): SQL WHERE clause added to the sensor query. Both the table level filter and a
            sensor query filter are added, separated by an AND operator.
        expected_values (Union[Unset, List[str]]): List of expected string values that should be found in the tested
            column.
    """

    filter_: Union[Unset, str] = UNSET
    expected_values: Union[Unset, List[str]] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        filter_ = self.filter_
        expected_values: Union[Unset, List[str]] = UNSET
        if not isinstance(self.expected_values, Unset):
            expected_values = self.expected_values

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if filter_ is not UNSET:
            field_dict["filter"] = filter_
        if expected_values is not UNSET:
            field_dict["expected_values"] = expected_values

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        filter_ = d.pop("filter", UNSET)

        expected_values = cast(List[str], d.pop("expected_values", UNSET))

        column_strings_expected_text_values_in_use_count_sensor_parameters_spec = cls(
            filter_=filter_,
            expected_values=expected_values,
        )

        column_strings_expected_text_values_in_use_count_sensor_parameters_spec.additional_properties = (
            d
        )
        return column_strings_expected_text_values_in_use_count_sensor_parameters_spec

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
