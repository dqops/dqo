from typing import Any, Dict, List, Type, TypeVar, Union, cast

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ColumnStringsExpectedTextsInTopValuesCountSensorParametersSpec")


@_attrs_define
class ColumnStringsExpectedTextsInTopValuesCountSensorParametersSpec:
    """
    Attributes:
        filter_ (Union[Unset, str]): SQL WHERE clause added to the sensor query. Both the table level filter and a
            sensor query filter are added, separated by an AND operator.
        expected_values (Union[Unset, List[str]]): List of expected string values that should be found in the tested
            column among the TOP most popular (highest distinct count) column values.
        top (Union[Unset, int]): The number of the most popular values (with the highest distinct count) that are
            analyzed to find the expected values.
    """

    filter_: Union[Unset, str] = UNSET
    expected_values: Union[Unset, List[str]] = UNSET
    top: Union[Unset, int] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        filter_ = self.filter_
        expected_values: Union[Unset, List[str]] = UNSET
        if not isinstance(self.expected_values, Unset):
            expected_values = self.expected_values

        top = self.top

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if filter_ is not UNSET:
            field_dict["filter"] = filter_
        if expected_values is not UNSET:
            field_dict["expected_values"] = expected_values
        if top is not UNSET:
            field_dict["top"] = top

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        filter_ = d.pop("filter", UNSET)

        expected_values = cast(List[str], d.pop("expected_values", UNSET))

        top = d.pop("top", UNSET)

        column_strings_expected_texts_in_top_values_count_sensor_parameters_spec = cls(
            filter_=filter_,
            expected_values=expected_values,
            top=top,
        )

        column_strings_expected_texts_in_top_values_count_sensor_parameters_spec.additional_properties = (
            d
        )
        return column_strings_expected_texts_in_top_values_count_sensor_parameters_spec

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
