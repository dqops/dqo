#  Copyright © 2024 DQOps (support@dqops.com)
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

import numpy as np
import scipy
import scipy.stats
from .data_types import RuleExecutionRunParameters


t_dist_dict = {}


def get_t_distribution(degrees_of_freedom):
    global t_dist_dict

    if degrees_of_freedom in t_dist_dict:
        return t_dist_dict[degrees_of_freedom]

    t_dist = scipy.stats.t(df=degrees_of_freedom)
    t_dist_dict[degrees_of_freedom] = t_dist

    return t_dist


def find_tail(degrees_of_freedom, values_median, values_std, tail):
    t_dist = get_t_distribution(degrees_of_freedom)
    return float(t_dist.ppf(tail)) * float(values_std) + float(values_median)


def detect_upper_bound_anomaly(values: list[float], median: float, tail: float,
                               parameters: RuleExecutionRunParameters):
    values_above_median = [value for value in values if value >= median]
    values_array = np.array(values_above_median, dtype=float)
    values_median = np.median(values_array)
    values_std = scipy.stats.tstd(values_array)
    df = float(parameters.configuration_parameters.degrees_of_freedom)

    if float(values_std) == 0:
        return float(values_median)
    else:
        # Assumption: the historical data follows t-student distribution
        return find_tail(df, values_median, values_std, 1 - tail)


def detect_lower_bound_anomaly(values: list[float], median: float, tail: float,
                               parameters: RuleExecutionRunParameters):
    values_below_median = [value for value in values if value <= median]
    values_array = np.array(values_below_median, dtype=float)
    values_median = np.median(values_array)
    values_std = scipy.stats.tstd(values_array)
    df = float(parameters.configuration_parameters.degrees_of_freedom)

    if float(values_std) == 0:
        return float(values_median)
    else:
        # Assumption: the historical data follows t-student distribution
        return find_tail(df, values_median, values_std, tail)
