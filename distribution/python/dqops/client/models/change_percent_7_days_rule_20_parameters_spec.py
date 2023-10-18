from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="ChangePercent7DaysRule20ParametersSpec")


@_attrs_define
class ChangePercent7DaysRule20ParametersSpec:
    """
    Attributes:
        max_percent (Union[Unset, float]): Percentage of maximum accepted change compared to a readout 7 days ago
            (inclusive).
        exact_day (Union[Unset, bool]): When the exact_day parameter is unchecked (exact_day: false), rule searches for
            the most recent sensor readouts from the past 60 days and compares them. If the parameter is selected
            (exact_day: true), the rule compares only with the results from the past 7 days. If no results are found from
            that time, no results or errors will be generated.
    """

    max_percent: Union[Unset, float] = UNSET
    exact_day: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        max_percent = self.max_percent
        exact_day = self.exact_day

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if max_percent is not UNSET:
            field_dict["max_percent"] = max_percent
        if exact_day is not UNSET:
            field_dict["exact_day"] = exact_day

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        max_percent = d.pop("max_percent", UNSET)

        exact_day = d.pop("exact_day", UNSET)

        change_percent_7_days_rule_20_parameters_spec = cls(
            max_percent=max_percent,
            exact_day=exact_day,
        )

        change_percent_7_days_rule_20_parameters_spec.additional_properties = d
        return change_percent_7_days_rule_20_parameters_spec

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
