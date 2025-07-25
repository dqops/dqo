#  Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
#
#  This file is licensed under the Business Source License 1.1,
#  which can be found in the root directory of this repository.
#
#  Change Date: This file will be licensed under the Apache License, Version 2.0,
#  four (4) years from its last modification date.

from datetime import datetime
from typing import Sequence


# rule specific parameters object, contains values received from the quality check threshold configuration
class BetweenPercentChange30DaysRuleParametersSpec:
    from_percent: float
    to_percent: float
    exact_day: bool = False


class HistoricDataPoint:
    timestamp_utc_epoch: int
    local_datetime_epoch: int
    back_periods_index: int
    sensor_readout: float
    expected_value: float


class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_readouts: int


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: BetweenPercentChange30DaysRuleParametersSpec
    time_period_local_epoch: int
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec


# default object that should be returned to the dqo.io engine, specifies if the rule was passed or failed,
# what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional)
class RuleExecutionResult:
    passed: bool
    expected_value: float
    lower_bound: float
    upper_bound: float

    def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters, 'actual_value'):
        return RuleExecutionResult()

    past_readouts = rule_parameters.previous_readouts[:-29]
    if rule_parameters.parameters.exact_day:
        last_readout = past_readouts[-1]
        if last_readout is None:
            return RuleExecutionResult()

        previous_readout = last_readout.sensor_readout
    else:
        filtered_readouts = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in past_readouts if readouts is not None]
        previous_readout = filtered_readouts[-1] if len(filtered_readouts) > 0 else None
        if previous_readout is None:
            return RuleExecutionResult()

    lower_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.from_percent / 100.0)
    upper_bound = previous_readout + abs(previous_readout) * (rule_parameters.parameters.to_percent / 100.0)

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = (lower_bound + upper_bound) / 2

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
