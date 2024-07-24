from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ColumnDatetimeDateValuesInFuturePercentSensorParametersSpec")


@_attrs_define
class ColumnDatetimeDateValuesInFuturePercentSensorParametersSpec:
    """
    Attributes:
        filter_ (Union[Unset, str]): SQL WHERE clause added to the sensor query. Both the table level filter and a
            sensor query filter are added, separated by an AND operator.
        max_future_days (Union[Unset, float]): Maximum accepted number of days from now that are not treated as days
            from future. If value is not defined by user then default value is 0.0.
    """

    filter_: Union[Unset, str] = UNSET
    max_future_days: Union[Unset, float] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        filter_ = self.filter_
        max_future_days = self.max_future_days

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if filter_ is not UNSET:
            field_dict["filter"] = filter_
        if max_future_days is not UNSET:
            field_dict["max_future_days"] = max_future_days

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        filter_ = d.pop("filter", UNSET)

        max_future_days = d.pop("max_future_days", UNSET)

        column_datetime_date_values_in_future_percent_sensor_parameters_spec = cls(
            filter_=filter_,
            max_future_days=max_future_days,
        )

        column_datetime_date_values_in_future_percent_sensor_parameters_spec.additional_properties = (
            d
        )
        return column_datetime_date_values_in_future_percent_sensor_parameters_spec

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
