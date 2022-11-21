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
class BelowPercentPopulationStdev60DaysRuleParametersSpec:
    percent_population_below: float


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
    parameters: BelowPercentPopulationStdev60DaysRuleParametersSpec
    time_period_local: datetime
    previous_readouts: Sequence[HistoricDataPoint]
    time_window: RuleTimeWindowSettingsSpec


# default object that should be returned to the dqo.io engine, specifies if the rule was passed or failed,
# what is the expected value for the rule and what are the upper and lower boundaries of accepted values (optional)
class RuleExecutionResult:
    passed: bool
    expected_value: float
    lower_bound: float

    def __init__(self, passed=True, expected_value=None, lower_bound=None):
        self.passed = passed
        self.expected_value = expected_value
        self.lower_bound = lower_bound


# rule evaluation method that should be modified for each type of rule
def evaluate_rule(rule_parameters: RuleExecutionRunParameters) -> RuleExecutionResult:
    filtered = [readouts.sensor_readout for readouts in rule_parameters.previous_readouts if readouts is not None]
    filtered_std = float(scipy.stats.tstd(filtered))
    filtered_mean = float(scipy.mean(filtered))

    threshold_lower = filtered_mean - rule_parameters.parameters.percent_population_below * filtered_std

    if rule_parameters.parameters.percent_population_below != None:
        passed = threshold_lower <= rule_parameters.actual_value
    elif threshold_lower == None:
        passed = rule_parameters.actual_value

    expected_value = filtered_mean
    lower_bound = threshold_lower
    return RuleExecutionResult(passed, expected_value, lower_bound)