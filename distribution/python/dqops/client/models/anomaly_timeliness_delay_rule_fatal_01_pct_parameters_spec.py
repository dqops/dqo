from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="AnomalyTimelinessDelayRuleFatal01PctParametersSpec")


@_attrs_define
class AnomalyTimelinessDelayRuleFatal01PctParametersSpec:
    """
    Attributes:
        anomaly_percent (Union[Unset, float]): The probability (in percent) that the current data delay is an anomaly
            because the value is outside the regular range of previous delays. The default time window of 90 time periods
            (days, etc.) is used, but at least 30 readouts must exist to run the calculation.
        use_ai (Union[Unset, bool]): Use an AI model to predict anomalies. WARNING: anomaly detection by AI models is
            not supported in an open-source distribution of DQOps. Please contact DQOps support to upgrade your instance to
            a closed-source DQOps distribution.
    """

    anomaly_percent: Union[Unset, float] = UNSET
    use_ai: Union[Unset, bool] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        anomaly_percent = self.anomaly_percent
        use_ai = self.use_ai

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if anomaly_percent is not UNSET:
            field_dict["anomaly_percent"] = anomaly_percent
        if use_ai is not UNSET:
            field_dict["use_ai"] = use_ai

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        anomaly_percent = d.pop("anomaly_percent", UNSET)

        use_ai = d.pop("use_ai", UNSET)

        anomaly_timeliness_delay_rule_fatal_01_pct_parameters_spec = cls(
            anomaly_percent=anomaly_percent,
            use_ai=use_ai,
        )

        anomaly_timeliness_delay_rule_fatal_01_pct_parameters_spec.additional_properties = (
            d
        )
        return anomaly_timeliness_delay_rule_fatal_01_pct_parameters_spec

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
