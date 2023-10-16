from typing import Any, Dict, List, Type, TypeVar, Union

from attrs import define as _attrs_define
from attrs import field as _attrs_field

from ..types import UNSET, Unset

T = TypeVar("T", bound="AnomalyStationaryPercentileMovingAverageRule1ParametersSpec")


@_attrs_define
class AnomalyStationaryPercentileMovingAverageRule1ParametersSpec:
    """
    Attributes:
        anomaly_percent (Union[Unset, float]): Probability that the current sensor readout will achieve values within
            the mean according to the distribution of the previous values gathered within the time window. In other words,
            the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the
            threshold level for all severity levels (warning, error, fatal) at once. The default is a time window of 90
            periods (days, etc.), but at least 30 readouts must exist to run the calculation. You can change the default
            value by modifying prediction_time_window parameterin Definitions section.
    """

    anomaly_percent: Union[Unset, float] = UNSET
    additional_properties: Dict[str, Any] = _attrs_field(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        anomaly_percent = self.anomaly_percent

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if anomaly_percent is not UNSET:
            field_dict["anomaly_percent"] = anomaly_percent

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        anomaly_percent = d.pop("anomaly_percent", UNSET)

        anomaly_stationary_percentile_moving_average_rule_1_parameters_spec = cls(
            anomaly_percent=anomaly_percent,
        )

        anomaly_stationary_percentile_moving_average_rule_1_parameters_spec.additional_properties = (
            d
        )
        return anomaly_stationary_percentile_moving_average_rule_1_parameters_spec

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
