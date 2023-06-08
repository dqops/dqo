from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..types import UNSET, Unset

T = TypeVar("T", bound="ChangePercentileMovingWithin7DaysRuleParametersSpec")


@attr.s(auto_attribs=True)
class ChangePercentileMovingWithin7DaysRuleParametersSpec:
    """
    Attributes:
        percentile_within (Union[Unset, float]): Probability that the current sensor readout will achieve values within
            the mean according to the distribution of the previous values gathered within the time window. In other words,
            the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the
            threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days,
            etc.) time window, but at least 3 readouts must exist to run the calculation.
    """

    percentile_within: Union[Unset, float] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        percentile_within = self.percentile_within

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if percentile_within is not UNSET:
            field_dict["percentile_within"] = percentile_within

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        percentile_within = d.pop("percentile_within", UNSET)

        change_percentile_moving_within_7_days_rule_parameters_spec = cls(
            percentile_within=percentile_within,
        )

        change_percentile_moving_within_7_days_rule_parameters_spec.additional_properties = (
            d
        )
        return change_percentile_moving_within_7_days_rule_parameters_spec

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
