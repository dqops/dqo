from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

T = TypeVar("T", bound="WithinChange7DaysRuleParametersSpec")


@attr.s(auto_attribs=True)
class WithinChange7DaysRuleParametersSpec:
    """
    Attributes:
        max_within (Union[Unset, float]): Maximal accepted absolute change with regards to the previous readout
            (inclusive).
        exact (Union[Unset, bool]): Whether to compare the actual value to the readout exactly 7 days in the past. If
            the flag is false, the rule searches for the newest readout, 60 days in the past, having skipped the readouts
            for the last 7 days.
    """

    max_within: Union[Unset, float] = UNSET
    exact: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        max_within = self.max_within
        exact = self.exact

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if max_within is not UNSET:
            field_dict["max_within"] = max_within
        if exact is not UNSET:
            field_dict["exact"] = exact

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        max_within = d.pop("max_within", UNSET)

        exact = d.pop("exact", UNSET)

        within_change_7_days_rule_parameters_spec = cls(
            max_within=max_within,
            exact=exact,
        )

        within_change_7_days_rule_parameters_spec.additional_properties = d
        return within_change_7_days_rule_parameters_spec

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
