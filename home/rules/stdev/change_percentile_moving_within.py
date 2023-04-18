#
# Copyright © 2023 DQO.ai (support@dqo.ai)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

from datetime import datetime
from typing import Sequence
import numpy as np
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class PercentileMovingWithinRuleParametersSpec:
    percentile_within: float


class HistoricDataPoint:
    timestamp_utc: datetime
    local_datetime: datetime
    back_periods_index: int
    sensor_readout: float


class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_readouts: int


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: PercentileMovingWithinRuleParametersSpec
    time_period_local: datetime
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec


# default object that should be returned to the dqo.io engine, specifies if the rule was passed or failed,
# what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional)
class RuleExecutionResult:
    passed: bool
    expected_value: float
    lower_bound: float
    upper_bound: float

    def __init__(self, passed=True, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    if not hasattr(rule_parameters, 'actual_value'):
        return RuleExecutionResult(True, None, None, None)

    extracted = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered = np.array(extracted, dtype=float)
    differences = np.diff(filtered)
    differences_std = float(scipy.stats.tstd(differences))
    differences_mean = float(np.mean(differences))

    last_readout = filtered[-1]
    actual_difference = rule_parameters.actual_value - last_readout

    # Assumption: the historical data follows normal distribution
    readout_distribution = scipy.stats.norm(loc=differences_mean, scale=differences_std)
    one_sided_tail = (1 - rule_parameters.parameters.percentile_within / 100.0) / 2

    threshold_lower = float(readout_distribution.ppf(one_sided_tail))
    threshold_upper = float(readout_distribution.ppf(1 - one_sided_tail))

    passed = threshold_lower <= actual_difference <= threshold_upper

    expected_value = last_readout + differences_mean
    lower_bound = last_readout + threshold_lower
    upper_bound = last_readout + threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
