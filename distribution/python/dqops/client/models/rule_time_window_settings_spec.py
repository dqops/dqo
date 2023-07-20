from typing import Any, Dict, List, Type, TypeVar, Union

import attr

from ..models.rule_time_window_settings_spec_historic_data_point_grouping import (
    RuleTimeWindowSettingsSpecHistoricDataPointGrouping,
)
from ..types import UNSET, Unset

T = TypeVar("T", bound="RuleTimeWindowSettingsSpec")


@attr.s(auto_attribs=True)
class RuleTimeWindowSettingsSpec:
    """
    Attributes:
        prediction_time_window (Union[Unset, int]): Number of historic time periods to look back for results. Returns
            results from previous time periods before the sensor readout timestamp to be used in a rule. Time periods are
            used in rules that need historic data to calculate an average to detect anomalies. e.g. when the sensor is
            configured to use a 'day' time period, the rule will receive results from the time_periods number of days before
            the time period in the sensor readout. The default is 14 (days).
        min_periods_with_readouts (Union[Unset, int]): Minimum number of past time periods with a sensor readout that
            must be present in the data in order to call the rule. The rule is not called and the sensor readout is
            discarded as not analyzable (not enough historic data to perform prediction) when the number of past sensor
            readouts is not met. The default is 7.
        historic_data_point_grouping (Union[Unset, RuleTimeWindowSettingsSpecHistoricDataPointGrouping]): Time period
            grouping for collecting previous data quality sensor results for the data quality rules that use historic data
            for prediction. For example, when the default time period grouping 'day' is used, DQO will find the most recent
            data quality sensor readout for each day and pass an array of most recent days per day in an array of historic
            sensor readout data points to a data quality rule for prediction.
    """

    prediction_time_window: Union[Unset, int] = UNSET
    min_periods_with_readouts: Union[Unset, int] = UNSET
    historic_data_point_grouping: Union[
        Unset, RuleTimeWindowSettingsSpecHistoricDataPointGrouping
    ] = UNSET
    additional_properties: Dict[str, Any] = attr.ib(init=False, factory=dict)

    def to_dict(self) -> Dict[str, Any]:
        prediction_time_window = self.prediction_time_window
        min_periods_with_readouts = self.min_periods_with_readouts
        historic_data_point_grouping: Union[Unset, str] = UNSET
        if not isinstance(self.historic_data_point_grouping, Unset):
            historic_data_point_grouping = self.historic_data_point_grouping.value

        field_dict: Dict[str, Any] = {}
        field_dict.update(self.additional_properties)
        field_dict.update({})
        if prediction_time_window is not UNSET:
            field_dict["prediction_time_window"] = prediction_time_window
        if min_periods_with_readouts is not UNSET:
            field_dict["min_periods_with_readouts"] = min_periods_with_readouts
        if historic_data_point_grouping is not UNSET:
            field_dict["historic_data_point_grouping"] = historic_data_point_grouping

        return field_dict

    @classmethod
    def from_dict(cls: Type[T], src_dict: Dict[str, Any]) -> T:
        d = src_dict.copy()
        prediction_time_window = d.pop("prediction_time_window", UNSET)

        min_periods_with_readouts = d.pop("min_periods_with_readouts", UNSET)

        _historic_data_point_grouping = d.pop("historic_data_point_grouping", UNSET)
        historic_data_point_grouping: Union[
            Unset, RuleTimeWindowSettingsSpecHistoricDataPointGrouping
        ]
        if isinstance(_historic_data_point_grouping, Unset):
            historic_data_point_grouping = UNSET
        else:
            historic_data_point_grouping = (
                RuleTimeWindowSettingsSpecHistoricDataPointGrouping(
                    _historic_data_point_grouping
                )
            )

        rule_time_window_settings_spec = cls(
            prediction_time_window=prediction_time_window,
            min_periods_with_readouts=min_periods_with_readouts,
            historic_data_point_grouping=historic_data_point_grouping,
        )

        rule_time_window_settings_spec.additional_properties = d
        return rule_time_window_settings_spec

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
