#
# Copyright © 2021 DQOps (support@dqops.com)
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


# rule specific parameters object, contains values received from the quality check threshold configuration
class DiffPercentRuleParametersSpec:
    max_diff_percent: float


class HistoricDataPoint:
    timestamp_utc: datetime
    local_datetime: datetime
    back_periods_index: int
    sensor_readout: float
    expected_value: float


class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_readouts: int


# rule execution parameters, contains the sensor value (expected_value, actual_value) and the rule parameters
class RuleExecutionRunParameters:
    expected_value: float
    actual_value: float
    parameters: DiffPercentRuleParametersSpec
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

    def __init__(self, passed=None, expected_value=None, lower_bound=None, upper_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    has_expected_value = hasattr(rule_parameters, 'expected_value')
    has_actual_value = hasattr(rule_parameters, 'actual_value')
    if not has_expected_value and not has_actual_value:
        return RuleExecutionResult()

    if not has_expected_value:
        return RuleExecutionResult(False, None, None, None)

    expected_value = rule_parameters.expected_value
    lower_bound = rule_parameters.expected_value - (rule_parameters.parameters.max_diff_percent/100 * rule_parameters.expected_value)
    upper_bound = rule_parameters.expected_value + (rule_parameters.parameters.max_diff_percent/100 * rule_parameters.expected_value)
    if has_actual_value:
        passed = lower_bound <= rule_parameters.actual_value <= upper_bound
    else:
        passed = False

    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)