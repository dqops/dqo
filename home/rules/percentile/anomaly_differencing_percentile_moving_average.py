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
from typing import Sequence, Dict
import numpy as np
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class AnomalyDifferencingPercentileMovingAverageRuleParametersSpec:
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
    parameters: AnomalyDifferencingPercentileMovingAverageRuleParametersSpec
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


def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    """
    Rule evaluation method that validates the rule result.
    :param rule_parameters: Rule parameters, containing the current value to assess and optionally
                            an array of historical measures.
    :return: Object with the decision to accept or reject the value.
    """

    if not hasattr(rule_parameters, 'actual_value') or not hasattr(rule_parameters.parameters, 'anomaly_percent'):
        return RuleExecutionResult()

    extracted = [(float(readouts.sensor_readout) if hasattr(readouts, 'sensor_readout') else None) for readouts in
                 rule_parameters.previous_readouts if readouts is not None]

    if len(extracted) == 0:
        return RuleExecutionResult()

    filtered = np.array(extracted, dtype=float)
    differences = np.diff(filtered)
    differences_list = differences.tolist()
    differences_std = float(scipy.stats.tstd(differences))
    differences_median = np.median(differences)
    differences_median_float = float(differences_median)
    degrees_of_freedom = float(rule_parameters.configuration_parameters.degrees_of_freedom)
    tail = rule_parameters.parameters.anomaly_percent / 100.0

    last_readout = float(filtered[-1])
    actual_difference = rule_parameters.actual_value - last_readout

    if float(differences_std) == 0:
        return RuleExecutionResult(None if actual_difference == differences_median_float else False,
                                   last_readout + differences_median_float, last_readout + differences_median_float,
                                   last_readout + differences_median_float)

    if all(difference > 0 for difference in differences_list):
        # using a 0-based calculation (scale from 0)
        upper_median_multiples_array = [(difference / differences_median_float - 1.0) for difference
                                        in differences_list if difference >= differences_median_float]
        upper_multiples = np.array(upper_median_multiples_array, dtype=float)
        upper_multiples_median = np.median(upper_multiples)
        upper_multiples_std = scipy.stats.tstd(upper_multiples)

        if float(upper_multiples_std) == 0:
            threshold_upper = differences_median_float
        else:
            # Assumption: the historical data follows t-student distribution
            upper_readout_distribution = scipy.stats.t(df=degrees_of_freedom, loc=upper_multiples_median, scale=upper_multiples_std)
            threshold_upper_multiple = float(upper_readout_distribution.ppf(1 - tail))
            threshold_upper = (threshold_upper_multiple + 1.0) * differences_median_float

        lower_median_multiples_array = [(-1.0 / (difference / differences_median_float)) for difference
                                        in differences_list if difference <= differences_median_float if difference != 0]
        lower_multiples = np.array(lower_median_multiples_array, dtype=float)
        lower_multiples_median = np.median(lower_multiples)
        lower_multiples_std = scipy.stats.tstd(lower_multiples)

        if float(lower_multiples_std) == 0:
            threshold_lower = differences_median_float
        else:
            # Assumption: the historical data follows t-student distribution
            lower_readout_distribution = scipy.stats.t(df=degrees_of_freedom, loc=lower_multiples_median, scale=lower_multiples_std)
            threshold_lower_multiple = float(lower_readout_distribution.ppf(tail))
            threshold_lower = differences_median_float * (-1.0 / threshold_lower_multiple)

        passed = threshold_lower <= actual_difference <= threshold_upper

        expected_value = last_readout + differences_median_float
        lower_bound = last_readout + threshold_lower
        upper_bound = last_readout + threshold_upper
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)

    else:
        # using unrestricted method for both positive and negative values
        upper_half_filtered = [difference for difference in differences_list if difference >= differences_median_float]
        upper_half = np.array(upper_half_filtered, dtype=float)
        upper_half_median = np.median(upper_half)
        upper_half_std = scipy.stats.tstd(upper_half)

        if float(upper_half_std) == 0:
            threshold_upper = differences_median_float
        else:
            # Assumption: the historical data follows t-student distribution
            upper_readout_distribution = scipy.stats.t(df=degrees_of_freedom, loc=upper_half_median, scale=upper_half_std)
            threshold_upper = float(upper_readout_distribution.ppf(1 - tail))

        lower_half_list = [difference for difference in differences_list if difference <= differences_median_float]
        lower_half = np.array(lower_half_list, dtype=float)
        lower_half_median = np.median(lower_half)
        lower_half_std = scipy.stats.tstd(lower_half)

        if float(lower_half_std) == 0:
            threshold_lower = differences_median_float
        else:
            # Assumption: the historical data follows t-student distribution
            lower_readout_distribution = scipy.stats.t(df=degrees_of_freedom, loc=lower_half_median, scale=lower_half_std)
            threshold_lower = float(lower_readout_distribution.ppf(tail))

        passed = threshold_lower <= actual_difference <= threshold_upper

        expected_value = last_readout + differences_median_float
        lower_bound = last_readout + threshold_lower
        upper_bound = last_readout + threshold_upper
        return RuleExecutionResult(passed, expected_value, lower_bound, upper_bound)
