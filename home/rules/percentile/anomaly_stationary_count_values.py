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
from lib.anomalies.anomaly_detection import detect_upper_bound_anomaly, detect_lower_bound_anomaly


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

    if not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'anomaly_percent'):
        return RuleExecutionResult()

    all_extracted = [(float(readouts.sensor_readout) if hasattr(readouts, 'sensor_readout') else None) for readouts in
                     rule_parameters.previous_readouts if readouts is not None]
    extracted = [readout for readout in all_extracted if readout > 0]

    if len(extracted) < 30:
        return RuleExecutionResult()

    filtered = np.array(extracted, dtype=float)
    filtered_median = np.median(filtered)
    filtered_median_float = float(filtered_median)
    filtered_std = scipy.stats.tstd(filtered)

    if float(filtered_std) == 0:
        return RuleExecutionResult(None if (rule_parameters.actual_value == filtered_median_float or
                                   (rule_parameters.actual_value == 0 and 0 in all_extracted)) else False,
                                   filtered_median_float, filtered_median_float if 0 not in all_extracted else 0,
                                   filtered_median_float)

    tail = rule_parameters.parameters.anomaly_percent / 100.0 / 2.0

    scaled_multiples_array = [(readout / filtered_median_float - 1.0 if readout >= filtered_median_float else
                               (-1.0 / (readout / filtered_median_float)) + 1.0) for readout in extracted]

    threshold_upper_multiple = detect_upper_bound_anomaly(values=scaled_multiples_array, median=0.0,
                                                          tail=tail, parameters=rule_parameters)

    passed = True
    if threshold_upper_multiple is not None:
        threshold_upper = (threshold_upper_multiple + 1.0) * filtered_median_float
        passed = rule_parameters.actual_value <= threshold_upper
    else:
        threshold_upper = None

    threshold_lower_multiple = detect_lower_bound_anomaly(values=scaled_multiples_array, median=0.0,
                                                          tail=tail, parameters=rule_parameters)

    if threshold_lower_multiple is not None:
        threshold_lower = filtered_median_float * (-1.0 / (threshold_lower_multiple - 1.0))
        passed = passed and threshold_lower <= rule_parameters.actual_value
    else:
        threshold_lower = None

    expected_value = filtered_median_float
    lower_bound = threshold_lower
    upper_bound = threshold_upper
    return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
