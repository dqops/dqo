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
class PercentileMovingRuleParametersSpec:
    percentile_above: float
    percentile_below: float


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
    parameters: PercentileMovingRuleParametersSpec
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
    if (not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'percentile_below')
            or not hasattr(rule_parameters.parameters, 'percentile_above')):
        return RuleExecutionResult()

    extracted = [(readouts.sensor_readout if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]

    if len(extracted) == 0:
        return RuleExecutionResult()

    filtered = np.array(extracted, dtype=float)
    filtered_std = scipy.stats.tstd(filtered)
    filtered_mean = np.mean(filtered)

    if filtered_std == 0:
        threshold_lower = float(filtered_mean) if rule_parameters.parameters.percentile_below is not None else None
        threshold_upper = float(filtered_mean) if rule_parameters.parameters.percentile_above is not None else None
    else:
        # Assumption: the historical data follows normal distribution
        readout_distribution = scipy.stats.norm(loc=filtered_mean, scale=filtered_std)

        threshold_lower = float(readout_distribution.ppf(rule_parameters.parameters.percentile_below / 100.0)) \
            if rule_parameters.parameters.percentile_below is not None else None
        threshold_upper = float(readout_distribution.ppf(1 - (rule_parameters.parameters.percentile_above / 100.0))) \
            if rule_parameters.parameters.percentile_above is not None else None

    if threshold_lower is not None and threshold_upper is not None:
        passed = threshold_lower <= rule_parameters.actual_value <= threshold_upper
    elif threshold_upper is not None:
        passed = rule_parameters.actual_value <= threshold_upper
    elif threshold_lower is not None:
        passed = threshold_lower <= rule_parameters.actual_value
    else:
        raise ValueError("At least one threshold is required.")

    expected_value = float(filtered_mean)
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
