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
class AnomalyPercentageValueRuleParametersSpec:
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
    parameters: AnomalyPercentageValueRuleParametersSpec
    time_period_local: datetime
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec
    configuration_parameters: AnomalyConfigurationParameters


class RuleExecutionResult:
    """
    The default object that should be returned to the DQOps engine, specifies if the rule has passed or failed,
    what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional).
    """

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
    """
    Rule evaluation method that validates the rule result.
    :param rule_parameters: Rule parameters, containing the current value to assess and optionally
                            an array of historical measures.
    :return: Object with the decision to accept or reject the value.
    """

    if not hasattr(rule_parameters, 'actual_value'):
        return RuleExecutionResult()

    all_extracted = [(float(readouts.sensor_readout) if hasattr(readouts, 'sensor_readout') else None) for readouts in
                     rule_parameters.previous_readouts if readouts is not None]
    extracted = [readout for readout in all_extracted if 0.0 < readout < 100.0]

    if len(extracted) < 30:
        return RuleExecutionResult()

    filtered = np.array(extracted, dtype=float)
    filtered_median = np.median(filtered)
    filtered_median_float = float(filtered_median)
    filtered_std = scipy.stats.tstd(filtered)

    if float(filtered_std) == 0.0:
        return RuleExecutionResult(rule_parameters.actual_value == filtered_median_float or
                                   (rule_parameters.actual_value == 0.0 and 0.0 in all_extracted) or
                                   (rule_parameters.actual_value == 100.0 and 100.0 in all_extracted),
                                   filtered_median_float,
                                   filtered_median_float if 0.0 not in all_extracted else 0.0,
                                   filtered_median_float if 100.0 not in all_extracted else 100.0)

    degrees_of_freedom = float(rule_parameters.configuration_parameters.degrees_of_freedom)
    tail = rule_parameters.parameters.anomaly_percent / 100.0

    if 100.0 in all_extracted:
        threshold_upper = 100.0
    else:
        upper_median_multiples_array = [1.0 / (1.0 - readout / 100.0) for readout in extracted
                                        if readout >= filtered_median_float]
        upper_multiples = np.array(upper_median_multiples_array, dtype=float)
        upper_multiples_median = np.median(upper_multiples)
        upper_multiples_std = scipy.stats.tstd(upper_multiples)

        if float(upper_multiples_std) == 0.0:
            threshold_upper = filtered_median_float
        else:
            # Assumption: the historical data follows t-student distribution
            upper_readout_distribution = scipy.stats.t(df=degrees_of_freedom, loc=upper_multiples_median,
                                                       scale=upper_multiples_std)
            threshold_upper_multiple = float(upper_readout_distribution.ppf(1 - tail))
            threshold_upper = 100.0 - 100.0 * (1.0 / threshold_upper_multiple)

    if 0.0 in all_extracted:
        threshold_lower = 0.0
    else:
        lower_median_multiples_array = [(-1.0 / (readout / filtered_median_float)) for readout in extracted
                                        if readout <= filtered_median_float]
        lower_multiples = np.array(lower_median_multiples_array, dtype=float)
        lower_multiples_median = np.median(lower_multiples)
        lower_multiples_std = scipy.stats.tstd(lower_multiples)

        if float(lower_multiples_std) == 0.0:
            threshold_lower = filtered_median_float
        else:
            # Assumption: the historical data follows t-student distribution
            lower_readout_distribution = scipy.stats.t(df=degrees_of_freedom, loc=lower_multiples_median,
                                                       scale=lower_multiples_std)
            threshold_lower_multiple = float(lower_readout_distribution.ppf(tail))
            threshold_lower = filtered_median_float * (-1.0 / threshold_lower_multiple)

    passed = threshold_lower <= rule_parameters.actual_value <= threshold_upper

    expected_value = filtered_median_float
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
