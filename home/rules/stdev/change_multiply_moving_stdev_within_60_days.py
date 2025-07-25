#  Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
#
#  This file is licensed under the Business Source License 1.1,
#  which can be found in the root directory of this repository.
#
#  Change Date: This file will be licensed under the Apache License, Version 2.0,
#  four (4) years from its last modification date.

from datetime import datetime
from typing import Sequence
import numpy as np
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class MultiplyMovingStdevWithinRuleParametersSpec:
    multiply_stdev: float


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
    parameters: MultiplyMovingStdevWithinRuleParametersSpec
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
    if not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'multiply_stdev'):
        return RuleExecutionResult()

    extracted = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]

    if len(extracted) == 0:
        return RuleExecutionResult()

    filtered = np.array(extracted, dtype=float)
    differences = np.diff(filtered)
    differences_std = float(scipy.stats.tstd(differences))
    differences_mean = float(np.mean(differences))

    last_readout = float(filtered[-1])
    actual_difference = rule_parameters.actual_value - last_readout

    stdev_span = rule_parameters.parameters.multiply_stdev * differences_std
    threshold_lower = differences_mean - stdev_span / 2
    threshold_upper = differences_mean + stdev_span / 2

    passed = threshold_lower <= actual_difference <= threshold_upper

    expected_value = last_readout + differences_mean
    lower_bound = last_readout + threshold_lower
    upper_bound = last_readout + threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
