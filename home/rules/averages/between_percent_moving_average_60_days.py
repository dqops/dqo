#  Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
#
#  This file is licensed under the Business Source License 1.1,
#  which can be found in the root directory of this repository.
#
#  Change Date: This file will be licensed under the Apache License, Version 2.0,
#  four (4) years from its last modification date.

from datetime import datetime
from typing import Sequence
import scipy
import numpy as np


# rule specific parameters object, contains values received from the quality check threshold configuration
class BetweenPercentMovingAverage60DaysRuleParametersSpec:
    max_percent_above: float
    max_percent_below: float


class HistoricDataPoint:
    timestamp_utc_epoch: int
    local_datetime_epoch: int
    back_periods_index: int
    sensor_readout: float
    expected_value: float


class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_readout: int


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: BetweenPercentMovingAverage60DaysRuleParametersSpec
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
    if (not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'max_percent_above') or
            not hasattr(rule_parameters.parameters, 'max_percent_below')):
        return RuleExecutionResult()

    filtered = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]
    if len(filtered) == 0:
        return RuleExecutionResult()

    filtered_mean = float(np.mean(filtered))

    upper_bound = filtered_mean * (1.0 + rule_parameters.parameters.max_percent_above / 100.0)
    lower_bound = filtered_mean * (1.0 - rule_parameters.parameters.max_percent_below / 100.0)

    passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    expected_value = filtered_mean

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
