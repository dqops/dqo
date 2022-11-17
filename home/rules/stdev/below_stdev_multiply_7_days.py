#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from datetime import datetime
from typing import Sequence
import scipy
import scipy.stats


# rule specific parameters object, contains values received from the quality check threshold configuration
class BelowStdevMultiply7DaysRuleParametersSpec:
    stdev_multiplier_below: float


class HistoricDataPoint:
    timestamp_utc: datetime
    local_datetime: datetime
    back_periods_index: int
    sensor_reading: float


class RuleTimeWindowSettingsSpec:
    prediction_time_window: int
    min_periods_with_reading: int


# rule execution parameters, contains the sensor value (actual_value) and the rule parameters
class RuleExecutionRunParameters:
    actual_value: float
    parameters: BelowStdevMultiply7DaysRuleParametersSpec
    time_period_local: datetime
    previous_readings: Sequence[HistoricDataPoint]
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
    filtered = [readings.sensor_reading for readings in rule_parameters.previous_readings if readings is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    multiple_stdev_below = abs(float(scipy.stats.norm.ppf(rule_parameters.parameters.stdev_multiplier_below/100.0 / 2, 0, 1)))

    threshold_lower = filtered_mean - multiple_stdev_below * filtered_std

    if multiple_stdev_below != None:
        passed = (threshold_lower <= rule_parameters.actual_value)
    elif threshold_lower == None:
        passed = rule_parameters.actual_value


    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)
