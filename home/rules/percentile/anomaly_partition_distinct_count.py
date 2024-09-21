#
# Copyright © 2023 DQOps (support@dqops.com)
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
class AnomalyPartitionDistinctCountRuleParametersSpec:
    anomaly_percent: float


class HistoricDataPoint:
    timestamp_utc: datetime
    local_datetime: datetime
    back_periods_index: int
    sensor_readout: float
    expected_value: float


class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_readouts: int


class AnomalyConfigurationParameters:
    degrees_of_freedom: float


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: AnomalyPartitionDistinctCountRuleParametersSpec
    time_period_local: datetime
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec
    configuration_parameters: AnomalyConfigurationParameters


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

    extracted = [(float(readouts.sensor_readout) if hasattr(readouts, 'sensor_readout') else None) for readouts in rule_parameters.previous_readouts if readouts is not None]
    positive = [readout for readout in extracted if readout > 0]

    if len(positive) < 30:
        return RuleExecutionResult()

    filtered = np.array(positive, dtype=float)
    filtered_median = np.median(filtered)
    filtered_median_float = float(filtered_median)
    filtered_std = scipy.stats.tstd(filtered)

    above_median_multiples_array = [(readout / filtered_median_float - 1.0) for readout in positive if readout >= filtered_median_float]
    above_multiples = np.array(above_median_multiples_array, dtype=float)
    above_multiples_median = np.median(above_multiples)
    above_multiples_std = scipy.stats.tstd(above_multiples)

    below_median_multiples_array = [(-1.0 / (readout / filtered_median_float)) for readout in positive if readout < filtered_median_float if readout != 0]
    below_multiples = np.array(below_median_multiples_array, dtype=float)
    below_multiples_median = np.median(below_multiples)
    below_multiples_std = scipy.stats.tstd(below_multiples)

    degrees_of_freedom = float(rule_parameters.configuration_parameters.degrees_of_freedom)

    if float(filtered_std) == 0:
        threshold_lower = float(filtered_mean)
        threshold_upper = float(filtered_mean)
    else:
        # Assumption: the historical data follows t-student distribution
        above_readout_distribution = scipy.stats.t(df=degrees_of_freedom, loc=above_multiples_median, scale=above_multiples_std)
        below_readout_distribution = scipy.stats.t(df=degrees_of_freedom, loc=below_multiples_median, scale=below_multiples_std)
        one_sided_tail = rule_parameters.parameters.anomaly_percent / 100.0 / 2

        threshold_upper_multiple = float(above_readout_distribution.ppf(1 - one_sided_tail))
        threshold_lower_multiple = float(below_readout_distribution.ppf(one_sided_tail))

        threshold_upper = (threshold_upper_multiple + 1.0) * filtered_median_float
        threshold_lower = filtered_median_float * (-1.0 / threshold_lower_multiple)

    passed = threshold_lower <= rule_parameters.actual_value <= threshold_upper

    expected_value = filtered_median_float
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
